<template>
  <div>
    <!--内容开始-->
    <div class="content_bigbox" id="project-payments">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item class="active_bread">项目收支管理</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <!--新增归档按钮-->
      <div class="da_xz_ckbigbox">
        <el-row>
          <el-col :span="12">
            <el-button icon="el-icon-plus" size='medium' type="primary" class="gongneng_btn" @click="addNewsObject">新增线下项目</el-button>
          </el-col>
          <el-col  :span="8" :offset="4">
            <el-input placeholder="请输入招标项目名称关键字" class="search-box" v-model="seacherInput">
              <el-button slot="append" icon="el-icon-search" type="" @click="search"></el-button>
            </el-input>
          </el-col>
        </el-row>
      </div>
      <!--新增归档按钮-->
      <!--表格内容开始-->
      <div>
        <el-table
          :data="tableData"
          border
          style="width: 100%">
          <el-table-column
            type="index"
            label="序号"
            width="50"
            align="center"
            :index='dataIndex'>
          </el-table-column>
          <el-table-column
            prop="bidProjectNumber"
            label="招标项目编号"
            width="120">
          </el-table-column>
          <el-table-column
            prop="bidProjectName"
            label="招标项目名称"
            width="220">
          </el-table-column>
          <el-table-column
            prop="bidder"
            label="招标人"
            width="110">
          </el-table-column>
          <el-table-column
            prop="managerName"
            label="项目经理"
            width="110">
          </el-table-column>
          <el-table-column
            prop="projectNature"
            label="项目性质"
            width="200">
            <template slot-scope="scope">
              {{scope.row.projectNature===0?'线下项目':'在线项目'}}
            </template>
          </el-table-column>
          <el-table-column
            prop="createDate"
            label="创建时间"
            width="200">
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" align="center">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="expenseManage(scope)">
                费用管理
              </el-button>
              <el-button type="text" size="small" @click="ObjectInformation(scope)">
                项目信息
              </el-button>
              <el-button type="text" size="small" @click="deleteOwner(scope)" v-if="$store.getters.permissions.includes('/projectpayments/delete')">
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <!--表格内容开始-->
      <!--分页-->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size='pageSize'
        :current-page.sync="currentPage"
        @current-change="handleCurrentChange">
      </el-pagination>
      <!--分页-->
    </div>
    <!--内容开始-->
  </div>
</template>
<script>
import {checkResponse, addtitle} from '../../assets/js/common'
export default {
  data () {
    return {
      total: 0, // 总条数
      pageSize: 15, // 每页展示条数
      pageNo: 0,
      currentPage: 1,
      dataIndex: 1,
      seacherInput: '',
      searchIs: false,
      projectNature: [
        {
          value: 0,
          label: '线下项目'
        },
        {
          value: 1,
          label: '在线项目'
        }
      ],
      // 表单数据
      tableData: []
    }
  },
  methods: {
    // 列表
    projectList (search) {
      let url = null
      if (this.searchIs) {
        url = `income-expenses?pageNo=${this.pageNo}&pageSize=${this.pageSize}&isDelete=${0}&bidProjectName=${encodeURIComponent(this.seacherInput)}&_t=${new Date().getTime()}`
      } else {
        url = `income-expenses?pageNo=${this.pageNo}&pageSize=${this.pageSize}&isDelete=${0}&_t=${new Date().getTime()}`
      }
      this.axios.get(url).then((res) => {
        this.tableData = res.data.incomeExpensePageInfo.list
        this.total = res.data.incomeExpensePageInfo.total
        addtitle(this)
      })
    },
    // 搜索
    search () {
      this.axios.get(`income-expenses?pageNo=${this.pageNo}&pageSize=${this.pageSize}&isDelete=${0}&bidProjectName=${encodeURIComponent(this.seacherInput)}&_t=${new Date().getTime()}`).then((res) => {
        this.tableData = res.data.incomeExpensePageInfo.list
        this.total = res.data.incomeExpensePageInfo.total
        addtitle(this)
        this.currentPage = 1
        this.dataIndex = 1
        this.pageNo = (this.currentPage - 1) * this.pageSize
        this.searchIs = true
      })
    },
    // 表单分页
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this.projectList(this.searchIs)
    },
    // 新增线下项目
    addNewsObject () {
      this.$router.push({path: '/projectpayments/add'})
    },
    // 删除
    deleteObject (scope) {
      this.$confirm('确定要删除吗?删除后不可恢复，是否继续；确认后删除记录?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        this.axios.delete(`income-expenses/${scope.row.objectId}`).then((res) => {
          checkResponse(this, res, () => {
            this.projectList()
          })
        })
      }).catch(() => {
        return false
      })
    },
    // 费用管理
    expenseManage (scope) {
      this.$router.push({path: '/projectpayments/manage', query: {objectIds: scope.row.objectId}})
    },
    // 项目信息
    ObjectInformation (scope) {
      this.$router.push({path: '/projectpayments/seeitem', query: {objectIds: scope.row.objectId}})
    }
  },
  mounted () {
    this.projectList()
  }
}
</script>

<style lang="less">
  body{
    background:#eaedf1;
  }
  #project-payments {
    .search-box{
      float: right;
    }
    .el-table__header {
      width: 100% !important;
    }
    .da_xz_ckbigbox {
      padding: 10px 20px;
    }
    .el-table {
      font-size: 12px;
      font-weight: normal !important;
      color: #999999;
    }
    th {
      font-weight: normal !important;
      background: transparent !important;
      width: 100% !important;
    }
    .el-table_1_column_1 {
      text-align: center !important;
    }
    th:last-child {
      text-align: center !important;
    }
    .is-leaf {
      background: #f5f7f8 !important;
    }
    .el-table_1_column_1 {
      text-align: center !important;
    }
    .has-gutter > tr {
      background-color: #f5f7f8 !important;
    }
    .seacher_input {
      display: inline-block;
      width: 382px;
      height: 36px;
      border: 1px solid #cccccc;
      vertical-align: top;
      padding: 0 10px;
      box-sizing: border-box;
      font-size: 13px;
    }
    .seacher_btn {
      display: inline-block;
      width: 36px;
      height: 36px;
      background: #ebeffe;
      vertical-align: top;
      margin-left: -3px;
      position: relative;
      cursor: pointer;
      transition: all .8s ease;
    }
    .seacher_btn > img {
      width: 16px;
      position: absolute;
      left: 0;
      right: 0;
      top: 0;
      bottom: 0;
      margin: auto;
    }
    .el-button--primary {
      color: #fff;
      background-color: #6582f8;
      border-color: #6582f8;
    }
    .el-table .cell {
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      word-break: break-all;
      line-height: 23px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }
</style>
