<template>
  <div class="cloudcontent" id="cloud_processtype">
    <div class="box">
      <div class="topmain">
        招标文件
        <!--按钮-->
        <el-button class="addbutton" @click="handleAdd" v-if="operationFlag">
          <span> + 添加</span>
        </el-button>
        <!--按钮-->
      </div>
      <el-table
        :data="tableData"
        border>
        <el-table-column
          type="index"
          label="序号"
          width="50">
        </el-table-column>
        <el-table-column
          prop="fileName"
          label="招标文件"
          width="180">
          <template slot-scope="scope">
            <p v-for="(item, index) in scope.row.fileInformationList" :key="index">{{item.fileName}}</p>
          </template>
        </el-table-column>
        <el-table-column
          prop="documentReleaseTime"
          label="发布时间"
          width="180">
        </el-table-column>
        <el-table-column
          prop="auditStatus"
          label="提交状态"
          width="180">
          <template slot-scope="scope">
            <span  v-if="scope.row.auditStatus === 0">已保存</span>
            <span  v-if="scope.row.auditStatus === 1">待审批</span>
            <span  v-if="scope.row.auditStatus === 2">已审批</span>
            <span  v-if="scope.row.auditStatus === 3">已驳回</span>
            <span  v-if="scope.row.auditStatus === 4">已提交</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="submitTime"
          label="提交时间">
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="260">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="handleDetail(scope)">查看</el-button>
            <el-button type="text" size="small" v-if="scope.row.auditStatus === 0 || scope.row.auditStatus === 3" @click="handleEdit(scope)">编辑</el-button>
            <el-button type="text" size="small" v-if="scope.row.auditStatus === 0 || scope.row.auditStatus === 3" @click="handleDel(scope, 'file')">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
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
    <div class="box">
      <div class="topmain">
        招标文件疑义及澄清
        <!--按钮-->
        <el-button class="addbutton" @click="handleAddDoubt" v-if="operationFlag">
          <span> + 添加</span>
        </el-button>
        <!--按钮-->
      </div>
      <el-table
        :data="quesAndAnsInfoData"
        border>
        <el-table-column
          type="index"
          label="序号"
          width="50">
        </el-table-column>
        <el-table-column
          prop="objectionTitle"
          label="疑义标题"
          width="180">
        </el-table-column>
        <el-table-column
          prop="bidderName"
          label="投标人"
          width="180">
        </el-table-column>
        <el-table-column
          prop="auditStatus"
          label="提交状态"
          width="180">
          <template slot-scope="scope">
            <span  v-if="scope.row.auditStatus === 0">已保存</span>
            <span  v-if="scope.row.auditStatus === 1">待审批</span>
            <span  v-if="scope.row.auditStatus === 2">已审批</span>
            <span  v-if="scope.row.auditStatus === 3">已驳回</span>
            <span  v-if="scope.row.auditStatus === 4">已提交</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="submitTime"
          label="提交时间">
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="260">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="handleDetailDoubt(scope)">查看</el-button>
            <el-button type="text" size="small" v-if="scope.row.auditStatus === 0 || scope.row.auditStatus === 3" @click="handleEditDoubt(scope)">编辑</el-button>
            <el-button type="text" size="small" v-if="scope.row.auditStatus === 0 || scope.row.auditStatus === 3" @click="handleDel(scope, 'doubt')">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分页-->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="doubtTotal"
        :page-size='doubtPageSize'
        :current-page.sync="doubtCurrentPage"
        @current-change="handleDoubtCurrentChange">
      </el-pagination>
      <!--分页-->
    </div>
    <div class="box">
      <div class="topmain">
        招标澄清修改文件
        <!--按钮-->
        <el-button class="addbutton" @click="handleAddClarify" v-if="operationFlag">
          <span> + 添加</span>
        </el-button>
        <!--按钮-->
      </div>
      <el-table
        :data="documentInfoData"
        border>
        <el-table-column
          type="index"
          label="序号"
          width="50">
        </el-table-column>
        <el-table-column
          prop="fileName"
          label="澄清文件"
          width="180">
          <template slot-scope="scope">
            <p v-for="(item, index) in scope.row.fileInformationList" :key="index">{{item.fileName}}</p>
          </template>
        </el-table-column>
        <el-table-column
          prop="documentReleaseTime"
          label="发布时间"
          width="180">
        </el-table-column>
        <el-table-column
          prop="auditStatus"
          label="提交状态"
          width="180">
          <template slot-scope="scope">
            <span  v-if="scope.row.auditStatus === 0">已保存</span>
            <span  v-if="scope.row.auditStatus === 1">待审批</span>
            <span  v-if="scope.row.auditStatus === 2">已审批</span>
            <span  v-if="scope.row.auditStatus === 3">已驳回</span>
            <span  v-if="scope.row.auditStatus === 4">已提交</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="submitTime"
          label="提交时间">
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="260">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="handleDetailClarify(scope)">查看</el-button>
            <el-button type="text" size="small" v-if="scope.row.auditStatus === 0 || scope.row.auditStatus === 3" @click="handleEditClarify(scope)">编辑</el-button>
            <el-button type="text" size="small" v-if="scope.row.auditStatus === 0 || scope.row.auditStatus === 3" @click="handleDel(scope, 'clarify')">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分页-->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="clarifyTotal"
        :page-size='clarifyPageSize'
        :current-page.sync="clarifyCurrentPage"
        @current-change="handleClarifyCurrentChange">
      </el-pagination>
      <!--分页-->
    </div>
  </div>
</template>
<script>
import {questionAndAnswerInfo, documentInfo} from '@/api/cloudplatform'
export default {
  components: {
  },
  props: ['tenderSystemCode', 'sectionSystemCode', 'chooseSectionSystemCode', 'operationFlag'],
  data () {
    return {
      tableData: [],
      documentInfoData: [],
      quesAndAnsInfoData: [],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: null, // 总条数
      pageSize: 10, // 每页展示条数
      doubtCurrentPage: 1, // 当前页
      doubtPageNo: 0,
      doubtTotal: null, // 总条数
      doubtPageSize: 10, // 每页展示条数
      clarifyCurrentPage: 1, // 当前页
      clarifyPageNo: 0,
      clarifyTotal: null, // 总条数
      clarifyPageSize: 10 // 每页展示条数
    }
  },
  watch: {
    $route: 'resetView'
  },
  methods: {
    resetView () {
      this.getTableList()
      this.getDocumentInfoList()
      this.getQuesAndAnsInfoList()
    },
    // 编辑
    handleEdit (scope) {
      this.$router.push({path: `/processManage/projectProcess/biddocument/update`,
        query: {objectId: scope.row.objectId,
          sectionSystemCode: this.chooseSectionSystemCode ? this.chooseSectionSystemCode : this.sectionSystemCode,
          tenderSystemCode: this.tenderSystemCode}})
    },
    handleAdd () {
      this.$router.push({path: `/processManage/projectProcess/biddocument/add`,
        query: {sectionSystemCode: this.chooseSectionSystemCode ? this.chooseSectionSystemCode : this.sectionSystemCode,
          tenderSystemCode: this.tenderSystemCode}})
    },
    handleDetail (scope) {
      this.$router.push({path: `/processManage/projectProcess/biddocument/detail/${scope.row.objectId}`,
        query: {sectionSystemCode: this.chooseSectionSystemCode ? this.chooseSectionSystemCode : this.sectionSystemCode}})
    },
    // 编辑
    handleEditDoubt (scope) {
      this.$router.push({path: `/processManage/projectProcess/biddocument/update_doubt`, query: {objectId: scope.row.objectId}})
    },
    handleAddDoubt () {
      this.$router.push({path: `/processManage/projectProcess/biddocument/add_doubt`,
        query: {sectionSystemCode: this.chooseSectionSystemCode ? this.chooseSectionSystemCode : this.sectionSystemCode,
          tenderSystemCode: this.tenderSystemCode}})
    },
    handleDetailDoubt (scope) {
      this.$router.push({path: `/processManage/projectProcess/biddocument/doubt_detail/${scope.row.objectId}`})
    }, // 编辑
    handleEditClarify (scope) {
      this.$router.push({path: `/processManage/projectProcess/biddocument/update_clarify`,
        query: {objectId: scope.row.objectId,
          sectionSystemCode: this.chooseSectionSystemCode ? this.chooseSectionSystemCode : this.sectionSystemCode,
          tenderSystemCode: this.tenderSystemCode}})
    },
    handleAddClarify () {
      this.$router.push({path: `/processManage/projectProcess/biddocument/add_clarify`,
        query: {sectionSystemCode: this.chooseSectionSystemCode ? this.chooseSectionSystemCode : this.sectionSystemCode,
          tenderSystemCode: this.tenderSystemCode}})
    },
    handleDetailClarify (scope) {
      this.$router.push({path: `/processManage/projectProcess/biddocument/clarify_detail/${scope.row.objectId}`,
        query: {sectionSystemCode: this.chooseSectionSystemCode ? this.chooseSectionSystemCode : this.sectionSystemCode}})
    },
    handleDel (scope, type) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        if (Object.is(type, 'file')) {
          documentInfo.deleteByIdAndSectionSystemCode(scope.row.objectId,
            this.chooseSectionSystemCode ? this.chooseSectionSystemCode : this.sectionSystemCode).then(() => {
            this.getTableList()
          })
        } else if (Object.is(type, 'clarify')) {
          documentInfo.deleteByIdAndSectionSystemCode(scope.row.objectId,
            this.chooseSectionSystemCode ? this.chooseSectionSystemCode : this.sectionSystemCode).then(() => {
            this.getDocumentInfoList()
          })
        } else if (Object.is(type, 'doubt')) {
          questionAndAnswerInfo.deleteById(scope.row.objectId).then(() => {
            this.getQuesAndAnsInfoList()
          })
        }
      }).catch(() => {
        return false
      })
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getTableList()
    },
    handleDoubtCurrentChange (nowNum) {
      this.doubtCurrentPage = nowNum
      this.doubtPageNo = (nowNum - 1) * this.doubtPageSize
      this.getQuesAndAnsInfoList()
    },
    handleClarifyCurrentChange (nowNum) {
      this.clarifyCurrentPage = nowNum
      this.clarifyPageNo = (nowNum - 1) * this.clarifyPageSize
      this.getDocumentInfoList()
    },
    // 招标文件
    getTableList () {
      let query = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        tenderSystemCode: this.tenderSystemCode,
        sectionSystemCode: this.chooseSectionSystemCode ? this.chooseSectionSystemCode : this.sectionSystemCode,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        type: 3
      }
      documentInfo.getByRelaSection(query).then(res => {
        let data = res.data.documentInfoList
        if (data) {
          this.tableData = data.list
          this.total = data.total
        }
      })
    },
    // 招标文件疑义与澄清
    getQuesAndAnsInfoList () {
      let query = {
        pageNo: this.doubtPageNo,
        pageSize: this.doubtPageSize,
        tenderSystemCode: this.tenderSystemCode,
        sectionSystemCode: this.chooseSectionSystemCode ? this.chooseSectionSystemCode : this.sectionSystemCode,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        type: 2
      }
      questionAndAnswerInfo.getList(query).then(res => {
        let data = res.data.questionAndAnswerInfoList
        if (data) {
          this.quesAndAnsInfoData = data.list
          this.doubtTotal = data.total
        }
      })
    },
    // 资格预审澄清文件
    getDocumentInfoList () {
      let query = {
        pageNo: this.clarifyPageNo,
        pageSize: this.clarifyPageSize,
        tenderSystemCode: this.tenderSystemCode,
        sectionSystemCode: this.chooseSectionSystemCode ? this.chooseSectionSystemCode : this.sectionSystemCode,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        type: 4
      }
      documentInfo.getByRelaSection(query).then(res => {
        let data = res.data.documentInfoList
        if (data) {
          this.documentInfoData = data.list
          this.clarifyTotal = data.total
        }
      })
    }
  },
  mounted () {
    this.getTableList()
    this.getDocumentInfoList()
    this.getQuesAndAnsInfoList()
  }
}
</script>
<style lang="less">
  #cloud_processtype{
  }
</style>
