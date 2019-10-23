<template>
  <el-row class="list-container">
    <el-col class="list-left" :span="4">
      <el-card class="list-card">
        <el-tree :data="data" :props="defaultProps" @node-click="handleNodeClick" />
      </el-card>
    </el-col>
    <el-col class="list-content" :span="19">
      <el-row class="list-header">
        <el-card class="list-card">
          <el-col :span="10" style="margin:2px;5px;">
            <el-row><h1>student</h1></el-row>
            <el-row><strong>tb_id: jkl34534533535</strong></el-row>
          </el-col>
          <el-col :span="10" style="margin-top: 3%;">
            <el-row class="list-font">创建时间: 2019-10-23 19:34:42</el-row>
            <el-row class="list-font">修改时间: 2019-10-23 19:34:42</el-row>
            <el-row class="list-font">数据数量: 19890</el-row>
          </el-col>
          <el-col :span="3" style="margin-top: 3%;">
            <el-button type="primary" icon="el-icon-plus" round>创建合表</el-button>
          </el-col>
        </el-card>
      </el-row>
      <el-row class="list-data">
        <el-card class="list-card">
          <el-row :gutter="20">
            <el-tabs v-model="activeName" @tab-click="handleClick">
              <el-tab-pane label="数据视图" name="first">
                <el-row class="list-main" :gutter="20">
                  <el-card class="box-card">
                    <el-table
                      :data="tableData"
                      border
                      style="width: 100%"
                    >
                      <el-table-column
                        prop="date"
                        label="日期"
                        width="180"
                      />
                      <el-table-column
                        prop="name"
                        label="姓名"
                        width="180"
                      />
                      <el-table-column
                        prop="address"
                        label="地址"
                      />
                    </el-table>
                  </el-card>
                </el-row>
              </el-tab-pane>
              <el-tab-pane label="建模过程" name="second">
                <el-row style="height:500px">
                  <el-card class="box-card">
                    <div slot="header" class="clearfix header">
                      <el-button type="primary" icon="el-icon-document" size="mini" circle />
                      <el-button type="primary" icon="el-icon-caret-right" size="mini" circle />
                      <el-button type="primary" icon="el-icon-edit-outline" size="mini" circle />
                    </div>
                    <div class="text item">
                      <el-input
                        v-model="textarea"
                        disabled
                        type="textarea"
                        :rows="18"
                        placeholder="hiveSql"
                      />
                    </div>
                  </el-card>
                </el-row>
              </el-tab-pane>
              <el-tab-pane label="数据依赖" name="third">数据依赖</el-tab-pane>
              <el-tab-pane label="定时任务" name="fourth">定时任务</el-tab-pane>
            </el-tabs>
          </el-row>

        </el-card>
      </el-row>

    </el-col>
  </el-row>
</template>

<style lang="scss">
.list-container{
   height:900px;
   padding: 10px;
}
.list-left{
  height:calc(100% - 50px);
}
.list-card{
  height: 90%;
}
.list-header{
  height: 20%;
}
.list-data{
  height: 80%;
}
.list-content{
  margin-left: 10px;
  height: 100%;
}
.list-font{
  font-size: 14px;
  margin:5px 0;
}

.el-tabs__nav-scroll{
      margin-left: 15px;
}

.el-card__header{
  padding: 5px 10px;
  margin-left: 10px;
}
</style>

<script>
export default {
  data() {
    return {
      activeName: 'first',
      data: [{
          label: '一级 1',
          children: [{
            label: '二级 1-1',
            children: [{
              label: '三级 1-1-1'
            }]
          }]
        }],
        tableData: [{
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        }, {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄'
        }, {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄'
        }]
    }
  }
}
</script>
