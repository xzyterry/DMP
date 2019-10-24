package com.jawnho.douyuspringboot.controller;


import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.jawnho.douyuspringboot.entity.model.SaveSql;
import com.jawnho.douyuspringboot.entity.model.TestSql;
import com.jawnho.douyuspringboot.entity.vo.TbDetails;
import com.jawnho.douyuspringboot.hive.HiveJDBC;
import com.jawnho.douyuspringboot.response.DaoStatus;
import com.jawnho.douyuspringboot.response.JDBCStatus;
import com.jawnho.douyuspringboot.response.ResultEntity;
import com.jawnho.douyuspringboot.util.ResultUtils;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hive")
public class HiveController extends BaseController {

    @PostMapping("/testSql")
    @ResponseBody
    @ApiOperation(value = "/testSql", notes = "测试运行sql")
    public ResultEntity testSql(@RequestBody TestSql testSql) {
        JDBCStatus status = HiveJDBC.query(testSql.getSql());
        Map<String, Object> result = new HashMap<>();
        if (status.getException().equals("")) {

            result.put("data", processFieldName(status));
            return new ResultEntity(0, result);
        } else {
            result.put("data", status.getException());
            return new ResultEntity(500, result);
        }

    }

    private List<Map<String, Object>> processFieldName(JDBCStatus status) {
        Preconditions.checkNotNull(status);

        List<Map<String, Object>> newMapList = new LinkedList<>();
        List<Map<String, Object>> mapList = status.getResult();
        for (Map<String, Object> map : mapList) {
            if (map == null) {
                continue;
            }

            Map<String, Object> newMap = new HashMap<>();
            for (Entry<String, Object> entry : map.entrySet()) {
                if (entry == null) {
                    continue;
                }

                String key = entry.getKey();
                if (Strings.isNullOrEmpty(key)) {
                    continue;
                }

                String newKey = key.substring(key.lastIndexOf('.') + 1);
                newMap.put(newKey, entry.getValue());
            }
            newMapList.add(newMap);
        }
        return newMapList;
    }

    /**
     * 保存sql 并且结果存为一张 tb
     */
    @PostMapping("/saveSql")
    @ResponseBody
    @ApiOperation(value = "/saveSql", notes = "保存sql")
    public ResultEntity saveSql(@RequestBody SaveSql saveSql) {

        String tb_id = UUID.randomUUID().toString().replace("-", "_");
        saveSql.setTb_id(tb_id);
        String tb_sql = "create table if not exists " + tb_id + " as " + saveSql.getTb_sql();
        JDBCStatus status = HiveJDBC.createTable(tb_sql);
        DaoStatus daoStatus = null;
        if (status.getException().equals("")) {
            daoStatus = hiveService.save(saveSql);
        } else {
            daoStatus = new DaoStatus(status.getException(), null);
        }

        if (daoStatus == null) {
            return new ResultEntity(0, "success", "保存成功");
        } else {
            return new ResultEntity(500, "failed", daoStatus.getException());
        }
    }


    /**
     * 查询tb 及其数据预览
     */
    @PostMapping("/queryTb/{tb_id}")
    @ResponseBody
    @ApiOperation(value = "/queryTb/{tb_id}", notes = "查看合表")
    public ResultEntity queryTb(@PathVariable String tb_id) {

        TbDetails tbDetails = hiveService.findByTbId(tb_id);

        return new ResultEntity(0, tbDetails);
    }

    /**
     * 查询tb list
     */
    @PostMapping("/queryTbList")
    @ResponseBody
    @ApiOperation(value = "/queryTbList", notes = "查看合表")
    public ResultEntity queryTbList() {

        List<Map<String, Object>> result = hiveService.findAllTbList();

        return new ResultEntity(0, "success", result);
    }


    /**
     * 查询tb fields
     */
    @PostMapping("/queryTbFields/{tb_id}")
    @ResponseBody
    @ApiOperation(value = "/queryTbFields/{tb_id}", notes = "查看合表所有字段")
    public ResultEntity queryTbFields(@PathVariable String tb_id) {

        List<Map<String, Object>> result = hiveService.findFieldsByTbId(tb_id);

        return new ResultEntity(0, "success", ResultUtils.toJson(result));
    }

    /**
     * 触发单表合表
     */
    @PostMapping("/reRun/{tb_id}")
    @ResponseBody
    @ApiOperation(value = "/reRun/{tb_id}", notes = "查看合表")
    public ResultEntity reRun(@PathVariable String tb_id) {

        List<Map<String, Object>> result = hiveService.reRun(tb_id);

        return new ResultEntity(0, "success", ResultUtils.toJson(result));
    }

    /**
     * 触发级联合表
     */
    @PostMapping("/reCascadeRun")
    @ResponseBody
    @ApiOperation(value = "/reCascadeRun", notes = "查看合表")
    public ResultEntity reCascadeRun(@RequestBody TestSql testSql) {

        return new ResultEntity(0, "", "");
    }


}
