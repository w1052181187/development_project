<template>
  <div>
    <!--内容开始-->
    <div class="content_bigbox" id="content_bigbox">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item class="active_bread">档案管理</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <!--新增归档按钮-->

      <div class="da_xz_ckbigbox" style="border: none">
        <el-button type="text" class="da_xzgd_btn" @click="dialogTableVisible = true" v-if="$store.getters.permissions.includes('/archives/add')">
          <span class="jia_zzt">+</span>
          <span>新增归档</span>
        </el-button>
        <el-button type="text"  class="da_xzgd_btn" style="background: #ff9421" @click="excelBtn">
          <span>导出Excel</span>
        </el-button>
        <div class="seacher_box">
            <el-input placeholder="请输入标段名称关键词" v-model="biaoduanseainput"></el-input>
            <el-button @click="search" slot="append" icon="el-icon-search" type=""></el-button>
        </div>
      </div>
      <!--新增归档按钮-->
      <!--表格内容开始-->
      <div class="da_contabbigbox">
        <el-table
          :data="tableData"
          border
          ref="table"
          style="width: 100%">
          <el-table-column
            type="index"
            label="序号"
            width="50"
            :index='dataIndex'>
          </el-table-column>
          <el-table-column
            prop="sectionNumber"
            label="标段编号">
          </el-table-column>
          <el-table-column
            prop="sectionName"
            label="标段名称">
          </el-table-column>
          <el-table-column
            prop="proTenderNumber"
            label="所属招标项目">
          </el-table-column>
          <el-table-column
            prop="bidder"
            label="招标人">
          </el-table-column>
          <el-table-column
            prop="departmentName"
            label="所属部门"
            align="left">
          </el-table-column>
          <el-table-column
            prop="projectManagerName"
            label="项目经理">
          </el-table-column>
          <el-table-column
            prop="createDate"
            label="提交时间"
            width="150">
          </el-table-column>
          <el-table-column
            prop="fileStatus"
            label="档案状态"
            width="80">
            <template slot-scope="scope">
              {{scope.row.fileStatus?'已归档':'未归档'}}
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            width="260"
            label="操作" align="center">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="lookFun(scope)" v-if="$store.getters.permissions.includes('/archives/detail') || $store.getters.authUser.userId === scope.row.projectManager">
                查看
              </el-button>
              <el-button type="text" size="small" @click="deleteFun(scope)" v-if="($store.getters.permissions.includes('/archives/update') && (scope.row.fileStatus == 1 && $store.getters.authUser.userId !== scope.row.projectManager)) || (scope.row.fileStatus == 0 && $store.getters.authUser.userId === scope.row.projectManager) || ($store.getters.permissions.includes('/archives/update') && (scope.row.fileStatus == 0 && $store.getters.authUser.userId !== scope.row.projectManager))">
                编辑
              </el-button>
              <el-button v-if="($store.getters.permissions.includes('/archives/delete') && (scope.row.fileStatus == 0 || scope.row.fileStatus == null)) || (scope.row.fileStatus == 0 && $store.getters.authUser.userId === scope.row.projectManager)" type="text" size="small" @click.native.prevent="deleteArchives(scope)">删除
              </el-button>
              <el-button v-if="$store.getters.permissions.includes('/archives/delete') && (scope.row.fileStatus == 1 && $store.getters.authUser.userId !== scope.row.projectManager)" type="text" size="small" @click.native.prevent="deleteArchives(scope)">销毁
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <!--表格内容开始-->
      <!--分页-->
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
    <!--新增文档弹框-->
    <div id="xjwdfather">
      <el-dialog title="选择归档标段" :visible.sync="dialogTableVisible" width="80%" :before-close="cancelBtn">
        <!--线上线下选择-->
        <div class="xingzhi_box">
          <div class="xinzhileft_box">
            <span>归档项目性质：</span>
            <el-select class="xxgdxz" v-model="value" placeholder="请选择" @change="changeValue">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </div>
          <div class="xinzhiright_box" v-show="tab1">
            <el-select class="xxgdxz" v-model="valueSection" style="vertical-align: top" placeholder="请选择" @change="changeValueSearch">
              <el-option
                v-for="item in options2"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-input placeholder="请输入内容" v-model="inputSection" class="input-with-select" v-show="tab1">
              <el-button slot="append" icon="el-icon-search" @click="searchSection"></el-button>
            </el-input>
          </div>
        </div>
        <!--线上线下选择-->
        <!--线上项目数据-->
        <div class="select_tabbox" v-show="tab1">
          <!--表格数据-->
          <el-table :data="gridData" border>
            <el-table-column
              label="单选"
              width="80" align="center">
              <template slot-scope="scope">
                <el-radio v-model="radioOnline" :label="scope.row.objectId">&nbsp;</el-radio>
              </template>
            </el-table-column>
            <el-table-column
              prop="sectionNumber"
              label="标段编号">
            </el-table-column>
            <el-table-column
              prop="sectionName"
              label="标段名称">
            </el-table-column>
            <el-table-column
              prop="proTenderName"
              label="所属招标项目">
            </el-table-column>
          </el-table>
          <!--表格数据-->
          <!--分页-->
          <el-pagination
            background
            layout="prev, pager, next"
            :total="totalSection"
            :page-size='pageSizeSection'
            :current-page.sync="currentPageSection"
            @current-change="handleCurrentChangeSection">
          </el-pagination>
          <div class="que_qubigbox">
          <span slot="footer" class="dialog-footer">
            <el-button @click="cancel">取 消</el-button>
            <el-button type="primary" @click="submDataOnline()">
              确 定
            </el-button>
          </span>
          </div>
        </div>
        <!--线上项目数据-->
        <!--线下项目数据-->
        <div class="selectxxxm_bigbox" v-show="tab2" style="margin-top: 25px">
          <el-form :model="addForm" :rules="rules" ref="addForm">
            <el-form-item label="标段编号：" prop="sectionNumber" :label-width="formLabelWidth">
              <el-input class="xxxm_xinxi" v-model="addForm.sectionNumber" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="标段名称：" prop="sectionName" :label-width="formLabelWidth">
              <el-input class="xxxm_xinxi" v-model="addForm.sectionName" auto-complete="off"></el-input>
            </el-form-item>
          </el-form>
          <!--确认取消按钮-->
          <div class="que_qubigbox">
          <span slot="footer" class="dialog-footer">
            <el-button @click="cancel">取 消</el-button>
            <el-button type="primary" @click="submData('addForm')">
              确 定
            </el-button>
          </span>
          </div>
          <!--确认取消按钮-->
        </div>
        <!--线下项目数据-->
      </el-dialog>
    </div>
    <!--新增文档弹框-->
  </div>
</template>
<!--本页css-->
<script>
import {checkResponse, addtitle, loadUrl} from '../../assets/js/common'
export default {
  data () {
    return {
      radioOnline: '',
      // 表单数据
      tableData: [],
      total: 0, // 总条数
      pageSize: 15, // 每页展示条数
      pageNo: 0,
      currentPage: 1,
      dataIndex: 1,
      totalSection: 0, // 总条数
      pageSizeSection: 15, // 每页展示条数
      pageNoSection: 0,
      currentPageSection: 1,
      dataIndexSection: 1,
      // 搜索的input
      biaoduanseainput: '',
      searchIs: false,
      // 弹出框select数据
      addForm: {
        sectionNumber: '',
        sectionName: ''
      },
      dialogTableVisible: false,
      options: [
        {
          value: '在线项目',
          label: '在线项目'
        },
        {
          value: '线下项目',
          label: '线下项目'
        }
      ],
      value: '线下项目',
      options2: [
        {
          value: '标段名称',
          label: '标段名称'
        },
        {
          value: '招标项目名称',
          label: '招标项目名称'
        }
      ],
      valueSection: '标段名称',
      // 弹出框表单数据
      gridData: [],

      radios: '',
      // 提交时验证
      rules: {
        sectionNumber: [
          { required: true, message: '标段编号不能为空', trigger: 'blur' },
          { min: 1, max: 23, message: '请输入1~23个字符', trigger: 'change' }
        ],
        sectionName: [
          { required: true, message: '标段名称不能为空', trigger: 'blur' },
          {min: 1, max: 200, message: '请输入1~200个字符', trigger: ['blur', 'change']}
        ]
      },
      formLabelWidth: '120px',
      // 自定义选项卡值
      tab1: false,
      tab2: true,
      // 弹框
      inputSection: '', // 切换线下线上
      projectOpfrom: {}
    }
  },
  methods: {
    // 表格数据
    getData (search) {
      let url = null
      if (search) {
        url = `project-infos?pageNo=${this.pageNo}&pageSize=${this.pageSize}&enterpriseId=${this.$store.getters.authUser.ownerUserId}&userId=${this.$store.getters.authUser.userId}&isDelete=${0}&sectionName=${encodeURIComponent(this.biaoduanseainput)}&_t=${new Date().getTime()}`
      } else {
        url = `project-infos?pageNo=${this.pageNo}&pageSize=${this.pageSize}&enterpriseId=${this.$store.getters.authUser.ownerUserId}&userId=${this.$store.getters.authUser.userId}&isDelete=${0}&_t=${new Date().getTime()}`
      }
      this.axios.get(url).then((res) => {
        this.tableData = res.data.projectInformationInfo.list
        this.total = res.data.projectInformationInfo.total
        this.tableData.map((item) => {
          if (!item.agentStructure) {
            item.agentStructure = '-----------'
          }
        })
        addtitle(this)
      })
    },
    cancelBtn () {
      this.$refs['addForm'].resetFields()
      this.dialogTableVisible = false
    },
    // 表单分页
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this.getData(this.searchIs)
    },
    // 弹出框分页
    handleCurrentChangeSection (nowNum) {
      this.pageNoSection = nowNum
      this.dataIndexSection = (nowNum - 1) * this.pageSizeSection + 1
      this.searchSection()
    },
    // 档案管理列表页的搜索功能
    search () {
      this.currentPage = 1
      this.dataIndex = 1
      this.pageNo = 0
      this.axios.get(`project-infos?pageNo=${this.pageNo}&pageSize=${this.pageSize}&enterpriseId=${this.$store.getters.authUser.ownerUserId}&userId=${this.$store.getters.authUser.userId}&sectionName=${encodeURIComponent(this.biaoduanseainput)}&_t=${new Date().getTime()}`).then((res) => {
        this.tableData = res.data.projectInformationInfo.list
        this.tableData.map((item) => {
          if (!item.agentStructure) {
            item.agentStructure = '-----------'
          }
        })
        addtitle(this)
        this.total = res.data.projectInformationInfo.total
        this.currentPage = 1
        this.dataIndex = 1
        this.pageNo = (this.currentPage - 1) * this.pageSize
        this.searchIs = true
      })
    },
    // 删除
    deleteArchives (scope) {
      var toDo = scope.row.fileStatus == 1 ? '销毁' : '删除'
      this.$confirm(`此操作将永久${toDo}, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.axios.delete(`project-infos/${scope.row.objectId}`).then((res) => {
          checkResponse(this, res, () => {
            this.getData()
          })
        })
      }).catch(() => {
        return false
      })
    },
    // 查看
    lookFun (scope) {
      this.$router.push({path: `/archives/detail/${scope.row.objectId}`, query: {objectId: scope.row.objectId}})
    },
    // 编辑
    deleteFun (scope) {
      if (scope.row.bitbidObjectId === null) {
        this.$router.push({path: '/archives/update', query: {objectId: scope.row.objectId, btn: true, flangshow: true}})
      } else {
        this.$router.push({path: '/archives/onlineadd', query: {objectId: scope.row.bitbidObjectId, btn: true, flangshow: true}})
      }
    },
    // 线下线上切换
    changeValue (value) {
      this.tab1 = !this.tab1
      this.tab2 = !this.tab2
      let onlineUrl = null
      if (value === '在线项目') {
        onlineUrl = `project-infos/bitbid?pageNo=${this.pageNoSection}&pageSize=${this.pageSizeSection}&userId=${this.$store.getters.authUser.userId}&_t=${new Date().getTime()}`
      }
      this.axios.get(onlineUrl).then((res) => {
        this.gridData = res.data.projectInfomations
        this.totalSection = res.data.total
        addtitle(this)
      })
    },
    changeValueSearch (val) {
      if (val === '标段名称') {
        this.valueSection = '标段名称'
      } else {
        this.valueSection = '招标项目名称'
      }
    },
    // 线上部分搜索条件切换
    searchSection () {
      let onlineUrl = null
      if (this.valueSection === '标段名称') {
        onlineUrl = `project-infos/bitbid?pageNo=${this.pageNoSection}&pageSize=${this.pageSizeSection}&userId=${this.$store.getters.authUser.userId}&sectionName=${encodeURIComponent(this.inputSection)}&_t=${new Date().getTime()}`
      }
      if (this.valueSection === '招标项目名称') {
        onlineUrl = `project-infos/bitbid?pageNo=${this.pageNoSection}&pageSize=${this.pageSizeSection}&userId=${this.$store.getters.authUser.userId}&proTenderName=${encodeURIComponent(this.inputSection)}&_t=${new Date().getTime()}`
      }
      this.axios.get(onlineUrl).then((res) => {
        this.gridData = res.data.projectInfomations
        this.totalSection = res.data.total
        addtitle(this)
      })
    },
    // 确定提交线下数据
    submData (addForm) {
      this.$refs[addForm].validate((valid) => {
        if (valid) {
          this.$router.push({path: '/archives/add', query: {sectionNumber: this.addForm.sectionNumber, sectionName: this.addForm.sectionName, btn: false}})
        } else {
          return false
        }
      })
    },
    // 确定提交线上数据
    submDataOnline () {
      this.$router.push({path: '/archives/onlineadd', query: {objectId: this.radioOnline, btn: false}})
    },
    cancel () {
      this.dialogTableVisible = false
      this.addForm = {}
    },
    // 导出excel
    excelBtn () {
      window.open(`${loadUrl}project-infos/createExcel?enterpriseId=${this.$store.getters.authUser.ownerUserId}&userId=${this.$store.getters.authUser.userId}&isDelete=${0}`)
    }
  },
  mounted () {
    this.getData()
  }
}
</script>
<style lang="less">
  /*内容开始*/
  #content_bigbox {
    width: 1200px;
    background: #fff;
    min-height: 50px;
    margin: 0 auto;
    margin-top: 23px;
    /*新建归档按钮*/
    .da_xz_ckbigbox {
      width: 100%;
      height: 58px;
      border-bottom: 1px solid #eeeeee;
      padding: 0 20px;
      box-sizing: border-box;
    }
    .da_xzgd_btn {
      width: 120px;
      height: 36px;
      background: #6582f8;
      opacity: 0.8;
      color: #fff;
      font-size: 14px;
      text-align: center;
      vertical-align: middle;
      float: left;
      margin-top: 11px;
      cursor: pointer;
      transition: all .8s ease;
      border-radius: 0;
    }
    .da_xzgd_btn:hover {
      background: #6582f8;
      opacity: 1;
      color: #fff;
    }
    .seacher_box {
      height: 36px;
      min-width: 50px;
      float: right;
      /* background: red; */
      margin-top: 11px;
    }
    .el-input {
      position: relative;
      font-size: 14px;
      display: inline-block;
      width: 70%;
    }
    /*表格内容开始*/
    .da_contabbigbox {
      width: 100%;
      min-height: 10px;
    }
    .el-table__header {
      width: 100% !important;
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
    .el-button--small > span {
      color: #647ef7 !important;
    }
    .biaoduanseainput {
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
    .el-table .cell {
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      white-space: normal;
      word-break: break-all;
      height: 23px;
      line-height: 23px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .el-table_1_column_6 {
       text-align: left!important;
    }
  }
  #xjwdfather {
    /*弹框内容样式*/
    .el-dialog__body {
      padding: 0px 0px;
      color: #606266;
      line-height: 24px;
      font-size: 14px;
    }
    .el-dialog__header {
      padding: 20px 20px 10px;
      border-bottom: 1px solid #eee;
    }
    .el-dialog__title {
      line-height: 24px;
      font-size: 16px;
      color: #111111;
    }
    .xingzhi_box {
      width: 100%;
      height: 52px;
      border-bottom: 1px solid #eeeeee;
      padding: 0 20px;
      box-sizing: border-box;
      font-size: 14px;
      color: #111111;
      margin-bottom: 15px;
      margin-top: 15px;
    }
    .xinzhileft_box {
      float: left;
      height: 80%;
    }
    .xinzhiright_box {
      float: right;
    }
    .xxgdxz {
      background: #eceffe;
    }
    .biaoduanseainput {
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
    .el-dialog {
      margin-top: 10vh !important;
    }
    .el-radio-group {
      display: inline-block;
      line-height: 1;
      vertical-align: middle;
      font-size: 0;
      margin-left: 20px;
    }
    .que_qubigbox {
      width: 100%;
      height: 35px;
      margin-top: 20px;
      /* background: red; */
      text-align: center;
      padding-bottom: 20px;
    }
    .queren_btn {
      display: inline-block;
      width: 130px;
      height: 35px;
      background: #3f63f7;
      text-align: center;
      line-height: 35px;
      color: #fff;
      font-size: 14px;
      vertical-align: top;
      margin-right: 45px;
      cursor: pointer;
    }
    .quxiao_btn {
      display: inline-block;
      width: 130px;
      height: 35px;
      background: #ededed;
      text-align: center;
      line-height: 35px;
      color: #676767;
      font-size: 14px;
      vertical-align: top;
      cursor: pointer;
    }
    .selectxxxm_bigbox {
      width: 50%;
      margin: 0 auto;
      margin-bottom: 120px;
    }
    .xxxm_xinxi > .el-input__inner {
      width: 420px;
      height: 35px;
      border: 1px solid #eeeeee;
      box-sizing: border-box;
      text-align: left;
      background: transparent;
    }
    th {
      font-weight: normal !important;
      background: transparent !important;
      width: 100% !important;
    }
    .is-leaf {
      background: #f5f7f8 !important;
    }
    .el-input-group {
      line-height: normal;
      display: inline-table;
      width: 40%;
      border-collapse: separate;
    }
    .el-table .cell {
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      white-space: normal;
      word-break: break-all;
      height: 23px;
      line-height: 23px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .el-table_1_column_6 {
       text-align: left!important;
    }
    .el-radio__label {
      padding-left: 0px;
    }
  }
</style>
