<template>
  <div>
    <el-row class="list-row">
      <el-col :span="4" :gutter="1" class="list-col">
        <el-card class="box-card list-card">
          <el-tree :data="data" :props="defaultProps" @node-click="handleNodeClick" />
        </el-card>
      </el-col>

      <el-col :span="16" :gutter="1">
        <el-row style="height:500px">
          <el-card class="box-card">
            <div slot="header" class="clearfix header">
              <el-button type="primary" icon="el-icon-document" size="mini" circle />
              <el-button type="primary" icon="el-icon-caret-right" size="mini" circle />
            </div>
            <div class="text item">
              <el-input
                v-model="textarea"
                type="textarea"
                :rows="18"
                placeholder="hiveSql"
              />
            </div>
          </el-card>
        </el-row>
        <el-row style="height:300px">
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
      </el-col>
      <el-col :span="4" :gutter="1" class="list-col">
        <el-card class="box-card list-card">
          <el-tree :data="data" :props="defaultProps" @node-click="handleNodeClick" />
        </el-card>
      </el-col>
    </el-row>
  </div>

</template>

<style lang="scss">

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  margin: 5px 10px;
  height: 95%;
}

.list-col{
  height:calc(100% - 50px);
}

.list-card{
  height: 90%;
}

.list-row{
   height:900px
}

.el-card__header {
    padding: 8px 20px;
}
</style>

<script>
export default {
  data() {
    return {
    data: [{
          label: '一级 1',
          children: [{
            label: '二级 1-1',
            children: [{
              label: '三级 1-1-1'
            }]
          }]
        }],
        textarea: '',
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
  },
  methods: {
  handleNodeClick(data) {
          console.log(data)
        }
  }
}
</script>
