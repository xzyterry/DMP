<template>
  <el-row class="list-container">
    <el-col class="list-left" :span="4">
      <el-card class="list-card">
        <el-tree :data="tbTree" :props="defaultProps" @node-click="handleNodeClick" />
      </el-card>
    </el-col>
    <el-col class="list-content" :span="19">
      <el-row class="list-header">
        <el-card class="list-card">
          <el-col :span="10" style="margin:2px;5px;">
            <el-row><h1>{{ tbDetail.tb_name }}</h1></el-row>
            <el-row><strong>tb_id: {{ tbDetail.tb_id }}</strong></el-row>
          </el-col>
          <el-col :span="10" style="margin-top: 3%;">
            <el-row class="list-font">创建时间: {{ tbDetail.tb_create_time }}</el-row>
            <el-row class="list-font">修改时间: {{ tbDetail.tb_modified_time }}</el-row>
            <el-row class="list-font">数据数量: {{ tbDetail.total_cnt }}</el-row>
          </el-col>
          <el-col :span="3" style="margin-top: 3%;">
            <el-button type="primary" icon="el-icon-plus" circle @click="showChooose = true" />
          </el-col>
          <el-dialog
            title="选择功能"
            :visible.sync="showChooose"
            width="30%"
            left
          >
            <el-row>
              <el-col>
                <el-button class="fun">上传数据</el-button>
                <el-button class="fun" @click="createTb">创建合表</el-button>
              </el-col>
            </el-row>

          </el-dialog>
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
                      <template v-for="(col) in tbDetail.tb_fields">
                        <el-table-column
                          :key="col.col_name"
                          sortable
                          :show-overflow-tooltip="true"
                          :prop="col.col_name"
                          :label="col.col_name"
                          width="124px"
                        />
                      </template>

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
                        v-model="tbDetail.tb_sql"
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
.fun{
  width:10em;
  height:10em;
  background-color:bisque;
  color: white
}
</style>

<script>

import { fetchList, fetchTbDetail } from '@/api/tb.js'

export default {
  data() {
    return {
      showChooose: false,
      tbList: [],
      activeName: 'first',
      tbTree: [],
      tableData: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      tbDetail: {}
    }
  },
  mounted() {
    // 加载合表列表
    fetchList().then(resolve => {
        this.tbList = resolve.result
        if (this.tbList.length > 0) {
          var tb_id = this.tbList[0].tbId
          fetchTbDetail({ tb_id }).then(resolve => {
            this.tbDetail = resolve.result
            this.tableData = this.tbDetail.data
          })
        }
        const c = this.tbList.map(item => {
          return {
            id: item.tbId,
            label: item.tbName
          }
        })
        this.tbTree = [{ label: '列表', children: c }]
    }).catch(err => {
      console.log(err)
    })

    // 加载第一个合表的信息
  },
  methods: {
     handleClick(tab, event) {
        console.log(tab, event)
      },
      handleNodeClick(event) {
        console.log(event)
      },
      createTb() {
        this.$router.push({ path: '/tb/create/index' })
      }
  }
}
</script>
