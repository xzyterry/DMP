<template>
  <div>
    <el-row class="list-row">
      <el-col :span="4" :gutter="1" class="list-col">
        <el-card class="box-card list-card">
          <el-tree :data="tbTree" :props="defaultProps" @node-click="handleNodeClick" />
        </el-card>
      </el-col>

      <el-col :span="16" :gutter="1">
        <el-row style="height:500px">
          <el-card class="box-card">
            <div slot="header" class="clearfix header">
              <el-col :span="4">
                <el-button type="primary" icon="el-icon-document" size="mini" circle @click="handleSaveSql" />
                <el-button type="primary" icon="el-icon-caret-right" size="mini" circle @click="handleTestSql" />
              </el-col>
              <el-col :span="10">
                <el-form ref="dataForm" :model="sqlForm" label-position="left" label-width="160px" style="margin-left:50px;">
                  <el-form-item
                    label="表名"
                    prop="tb_name"
                    label-width="60px"
                    :rules="[
                      { type: 'string', required: true, message: '表名不能为空', trigger: 'blur,change' }
                    ]"
                  >
                    <el-input v-model="sqlForm.tb_name" placeholder="..." />
                  </el-form-item>
                </el-form>
              </el-col>

            </div>
            <div class="text item">
              <el-input
                v-model="sqlForm.tb_sql"
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
              :data="tmpResult.dataList"
              border
              style="width: 100%"
            >
              <template v-for="(col) in tmpResult.fieldList">
                <el-table-column
                  :key="col.col_name"
                  sortable
                  :show-overflow-tooltip="true"
                  :prop="col.col_name"
                  :label="col.col_name"
                />
              </template>

            </el-table>
          </el-card>
        </el-row>
      </el-col>
      <el-col :span="4" :gutter="1" class="list-col">
        <el-card class="box-card list-card">
          <el-tree :data="tbFieldTree" :props="defaultProps" @node-click="handleNodeClick" />
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

.el-table .cell.el-tooltip{
    text-align:left;
    word-wrap:break-word;
    word-break:break-all;
    white-space:normal;
    max-width:1000px;
}

.el-form-item {
    margin-bottom: 2px;
}
</style>

<script>
import { fetchList, fetchTbDetail, testSql, saveSql } from '@/api/tb.js'

export default {
  data() {
    return {
        tbTree: [],
        textarea: '',
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        tbDetail: {},
        tbFieldTree: [],
        tmpResult: {
          fieldList: [],
          dataList: []
        },
        sqlForm: {
          tb_sql: '',
          tb_name: ''
        }
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
            const fields = this.tbDetail.tb_fields.map(item => {
              return {
                id: item.col_name,
                label: item.col_name
              }
            })
            this.tbFieldTree = [{ label: '字段列表', children: fields }]
          })
        }
        const c = this.tbList.map(item => {
          return {
            id: item.tbId,
            label: item.tbName
          }
        })
        this.tbTree = [{ label: '合表列表', children: c }]
    }).catch(err => {
      console.log(err)
    })
  },
  methods: {
    handleNodeClick(data) {

    },
    handleTestSql() {
        this.tmpResult = { fieldList: [], dataList: [] }
        testSql({ sql: this.sqlForm.tb_sql }).then(resolve => {
            var message = resolve.message
            var tableData = resolve.result.data
            if (message === 'failed') {
              this.tmpResult.fieldList.push({ col_name: 'errorMsg' })
              this.tmpResult.dataList.push({ 'errorMsg': tableData })
            } else {
              this.tmpResult.dataList = tableData
              for (const f in tableData[0]) {
                  this.tmpResult.fieldList.push({ col_name: f })
              }
            }
        })
    },
    handleSaveSql() {
       this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          saveSql(this.sqlForm).then(resolve => {
                  console.log(resolve.result)
          })
        }
      })
     }

  }
}
</script>
