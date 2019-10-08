<template>
  <div class="cloudcontent" id="cloud_processtype">
    <div class="box" v-show="signUpVisable">
      <div class="topmain">
        投标报名受理信息
        <!--按钮-->
        <el-button  type="primary" class="addbutton" @click="handleAdd" v-if="operationFlag">
          <span> + 添加</span>
        </el-button>
        <!--按钮-->
      </div>
      <el-table
        :data="tableData"
        header-cell-class-name="tableheader"
        border>
        <el-table-column
          type="index"
          label="序号"
          :index="indexMethod"
          width="50">
        </el-table-column>
        <el-table-column
          prop="bidderName"
          label="投标人"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="contactName"
          label="联系人"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="contactNumber"
          label="联系方式"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="contactEmail"
          label="电子邮箱"
          :formatter="simpleFormatData"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="signUpTime"
          label="报名时间"
          :formatter="formatDate"
          width="160">
        </el-table-column>
        <el-table-column
          prop="auditStatus"
          label="提交状态"
          width="120">
          <template slot-scope="scope">
            <span  v-if="scope.row.auditStatus === 0">已保存</span>
            <span  v-if="scope.row.auditStatus === 1">待审批</span>
            <span  v-if="scope.row.auditStatus === 2">已审批</span>
            <span  v-if="scope.row.auditStatus === 3">已驳回</span>
            <span  v-if="scope.row.auditStatus === 4">已提交</span>
            <span  v-if="scope.row.auditStatus === 5">已撤回</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="submitter"
          label="提交人"
          :formatter="simpleFormatData"
          width="140">
        </el-table-column>
        <el-table-column
          prop="submitTime"
          label="提交时间"
          :formatter="formatDate"
          width="160">
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="260">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="handleDetail(scope)">查看</el-button>
            <el-button type="text" size="small" v-if="(scope.row.auditStatus === 0 || scope.row.auditStatus === 3 || scope.row.auditStatus === 5) && operationFlag" @click="handleEdit(scope)">编辑</el-button>
            <el-button type="text" size="small" v-if="(scope.row.auditStatus === 0) && operationFlag" @click="handleDel(scope, 'bond')">删除</el-button>
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
      <apply-attachment v-show="signUpVisable" :tenderSystemCode="tenderSystemCode" :sectionSystemCode="sectionSystemCode"
                        :operationFlag="operationFlag" @call="applyCall"></apply-attachment>
    </div>
    <div class="box">
      <div class="topmain">
        资格预审文件发售记录
        <!--按钮-->
        <el-button  type="primary" class="addbutton" @click="handleAddCost" v-if="operationFlag">
          <span> + 添加</span>
        </el-button>
        <!--按钮-->
      </div>
      <el-table
        :data="costInfoData"
        header-cell-class-name="tableheader"
        border>
        <el-table-column
          type="index"
          label="序号"
          :index="costIndexMethod"
          width="50">
        </el-table-column>
        <el-table-column
          prop="bidderName"
          label="投标人"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="amount"
          label="缴费金额(元)"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="paymentMethod"
          label="缴费方式"
          width="120">
          <template slot-scope="scope">
            <span  v-if="scope.row.paymentMethod === 1">支付宝</span>
            <span  v-if="scope.row.paymentMethod === 2">微信</span>
            <span  v-if="scope.row.paymentMethod === 3">现金</span>
            <span  v-if="scope.row.paymentMethod === 4">银联</span>
            <span  v-if="scope.row.paymentMethod === 5">支票</span>
            <span  v-if="scope.row.paymentMethod === 6">保函</span>
            <span  v-if="scope.row.paymentMethod === 9">其他</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="auditStatus"
          label="提交状态"
          width="120">
          <template slot-scope="scope">
            <span  v-if="scope.row.auditStatus === 0">已保存</span>
            <span  v-if="scope.row.auditStatus === 1">待审批</span>
            <span  v-if="scope.row.auditStatus === 2">已审批</span>
            <span  v-if="scope.row.auditStatus === 3">已驳回</span>
            <span  v-if="scope.row.auditStatus === 4">已提交</span>
            <span  v-if="scope.row.auditStatus === 5">已撤回</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="paymentTime"
          label="缴费时间"
          :formatter="formatDate"
          width="160">
        </el-table-column>
        <el-table-column
          prop="submitter"
          label="提交人"
          :formatter="simpleFormatData"
          width="140">
        </el-table-column>
        <el-table-column
          prop="submitTime"
          label="提交时间"
          :formatter="formatDate"
          width="160">
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="260">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="handleDetailCost(scope)">查看</el-button>
            <el-button type="text" size="small" v-if="(scope.row.auditStatus === 0 || scope.row.auditStatus === 3 || scope.row.auditStatus === 5) && operationFlag" @click="handleEditCost(scope)">编辑</el-button>
            <el-button type="text" size="small" v-if="(scope.row.auditStatus === 0) && operationFlag" @click="handleDel(scope, 'cost')">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分页-->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="costTotal"
        :page-size='costPageSize'
        :current-page.sync="currentPage"
        @current-change="handleCostCurrentChange">
      </el-pagination>
      <!--分页-->
    </div>
    <div class="box" v-show="explorationVisable">
      <div class="topmain">
        组织踏勘记录
        <!--按钮-->
        <el-button  type="primary" class="addbutton" @click="handleAddExploration" v-if="operationFlag">
          <span> + 添加</span>
        </el-button>
        <!--按钮-->
      </div>
      <el-table
        :data="explorationData"
        header-cell-class-name="tableheader"
        border>
        <el-table-column
          type="index"
          label="序号"
          :index="explIndexMethod"
          width="50">
        </el-table-column>
        <el-table-column
          prop="reconnaissanceSite"
          label="踏勘地点"
          :formatter="simpleFormatData"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="reconnaissanceTime"
          label="踏勘时间"
          :formatter="formatDate"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="auditStatus"
          label="提交状态"
          width="120">
          <template slot-scope="scope">
            <span  v-if="scope.row.auditStatus === 0">已保存</span>
            <span  v-if="scope.row.auditStatus === 1">待审批</span>
            <span  v-if="scope.row.auditStatus === 2">已审批</span>
            <span  v-if="scope.row.auditStatus === 3">已驳回</span>
            <span  v-if="scope.row.auditStatus === 4">已提交</span>
            <span  v-if="scope.row.auditStatus === 5">已撤回</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="submitter"
          label="提交人"
          :formatter="simpleFormatData"
          width="140">
        </el-table-column>
        <el-table-column
          prop="submitTime"
          label="提交时间"
          :formatter="formatDate"
          width="160">
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="260">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="handleDetailExploration(scope)">查看</el-button>
            <el-button type="text" size="small" v-if="(scope.row.auditStatus === 0 || scope.row.auditStatus === 3 || scope.row.auditStatus === 5) && operationFlag" @click="handleEditExploration(scope)">编辑</el-button>
            <el-button type="text" size="small" v-if="(scope.row.auditStatus === 0) && operationFlag" @click="handleDelExploration(scope)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分页-->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="explorationTotal"
        :page-size='explorationPageSize'
        :current-page.sync="explorationCurrentPage"
        @current-change="handleExplorationCurrentChange">
      </el-pagination>
      <!--分页-->
    </div>
    <div class="box">
      <div class="topmain">
        资格预审文件疑义及澄清
        <!--按钮-->
        <el-button  type="primary" class="addbutton" @click="handleAddDoubt" v-if="operationFlag">
          <span> + 添加</span>
        </el-button>
        <!--按钮-->
      </div>
      <el-table
        :data="quesAndAnsInfoData"
        header-cell-class-name="tableheader"
        border>
        <el-table-column
          type="index"
          label="序号"
          :index="doubtIndexMethod"
          width="50">
        </el-table-column>
        <el-table-column
          prop="objectionTitle"
          label="疑义标题"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="bidderName"
          label="投标人"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="auditStatus"
          label="提交状态"
          width="120">
          <template slot-scope="scope">
            <span  v-if="scope.row.auditStatus === 0">已保存</span>
            <span  v-if="scope.row.auditStatus === 1">待审批</span>
            <span  v-if="scope.row.auditStatus === 2">已审批</span>
            <span  v-if="scope.row.auditStatus === 3">已驳回</span>
            <span  v-if="scope.row.auditStatus === 4">已提交</span>
            <span  v-if="scope.row.auditStatus === 5">已撤回</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="submitter"
          label="提交人"
          :formatter="simpleFormatData"
          width="140">
        </el-table-column>
        <el-table-column
          prop="submitTime"
          label="提交时间"
          :formatter="formatDate"
          width="160">
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="260">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="handleDetailDoubt(scope)">查看</el-button>
            <el-button type="text" size="small" v-if="(scope.row.auditStatus === 0 || scope.row.auditStatus === 3 || scope.row.auditStatus === 5) && operationFlag" @click="handleEditDoubt(scope)">编辑</el-button>
            <el-button type="text" size="small" v-if="(scope.row.auditStatus === 0) && operationFlag" @click="handleDelDoubt(scope)">删除</el-button>
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
        资格预审澄清文件
        <!--按钮-->
        <el-button  type="primary" class="addbutton" @click="handleAddClarify" v-if="operationFlag">
          <span> + 添加</span>
        </el-button>
        <!--按钮-->
      </div>
      <el-table
        :data="documentInfoData"
        header-cell-class-name="tableheader"
        border>
        <el-table-column
          type="index"
          label="序号"
          :index="clarifyIndexMethod"
          width="50">
        </el-table-column>
        <el-table-column
          prop="fileName"
          label="资格预审澄清文件"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <p v-for="(item, index) in scope.row.fileInformationList" :key="index">{{item.fileName}}</p>
            <p v-if="scope.row.fileInformationList.length === 0">---</p>
          </template>
        </el-table-column>
        <el-table-column
          prop="documentReleaseTime"
          label="文件发布时间"
          :formatter="formatDate"
          width="160">
        </el-table-column>
        <el-table-column
          prop="auditStatus"
          label="提交状态"
          width="120">
          <template slot-scope="scope">
            <span  v-if="scope.row.auditStatus === 0">已保存</span>
            <span  v-if="scope.row.auditStatus === 1">待审批</span>
            <span  v-if="scope.row.auditStatus === 2">已审批</span>
            <span  v-if="scope.row.auditStatus === 3">已驳回</span>
            <span  v-if="scope.row.auditStatus === 4">已提交</span>
            <span  v-if="scope.row.auditStatus === 5">已撤回</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="submitter"
          label="提交人"
          :formatter="simpleFormatData"
          width="140">
        </el-table-column>
        <el-table-column
          prop="submitTime"
          label="提交时间"
          :formatter="formatDate"
          width="160">
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="260">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="handleDetailClarify(scope)">查看</el-button>
            <el-button type="text" size="small" v-if="(scope.row.auditStatus === 0 || scope.row.auditStatus === 3 || scope.row.auditStatus === 5) && operationFlag" @click="handleEditClarify(scope)">编辑</el-button>
            <el-button type="text" size="small" v-if="(scope.row.auditStatus === 0) && operationFlag" @click="handleDelClarify(scope)">删除</el-button>
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
    <div class="box">
      <div class="topmain">
        资格预审申请文件递交记录
        <!--按钮-->
        <el-button  type="primary" class="addbutton" @click="handleAddFile" v-if="operationFlag">
          <span> + 添加</span>
        </el-button>
        <!--按钮-->
      </div>
      <el-table
        :data="resultInfoData"
        header-cell-class-name="tableheader"
        border>
        <el-table-column
          type="index"
          label="序号"
          :index="resultIndexMethod"
          width="50">
        </el-table-column>
        <el-table-column
          prop="fileName"
          label="资格预审申请文件名称"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <p v-for="(item, index) in scope.row.fileInformationList" :key="index">{{item.fileName}}</p>
          </template>
        </el-table-column>
        <el-table-column
          prop="bidderName"
          label="投标人"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="auditStatus"
          label="提交状态"
          width="120">
          <template slot-scope="scope">
            <span  v-if="scope.row.auditStatus === 0">已保存</span>
            <span  v-if="scope.row.auditStatus === 1">待审批</span>
            <span  v-if="scope.row.auditStatus === 2">已审批</span>
            <span  v-if="scope.row.auditStatus === 3">已驳回</span>
            <span  v-if="scope.row.auditStatus === 4">已提交</span>
            <span  v-if="scope.row.auditStatus === 5">已撤回</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="submitter"
          label="提交人"
          :formatter="simpleFormatData"
          width="140">
        </el-table-column>
        <el-table-column
          prop="submitTime"
          label="提交时间"
          :formatter="formatDate"
          width="160">
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="260">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="handleDetailFile(scope)">查看</el-button>
            <el-button type="text" size="small" v-if="(scope.row.auditStatus === 0 || scope.row.auditStatus === 3 || scope.row.auditStatus === 5) && operationFlag" @click="handleEditFile(scope)">编辑</el-button>
            <el-button type="text" size="small" v-if="(scope.row.auditStatus === 0) && operationFlag" @click="handleDel(scope, 'file')">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分页-->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="resultTotal"
        :page-size='resultPageSize'
        :current-page.sync="resultCurrentPage"
        @current-change="handleResultCurrentChange">
      </el-pagination>
      <!--分页-->
    </div>
  </div>
</template>
<script>
import {costInfo, signUpInfo, resultInfo, questionAndAnswerInfo, documentInfo, tenderProject} from '@/api/project'
import applyAttachment from '@/pages/project/projectProcess/common/apply_attachment.vue'
import {dateFormat} from '@/assets/js/common'
export default {
  components: {
    applyAttachment
  },
  props: ['tenderSystemCode', 'sectionSystemCode', 'operationFlag', 'type'],
  data () {
    return {
      tableData: [],
      costInfoData: [],
      resultInfoData: [],
      quesAndAnsInfoData: [],
      documentInfoData: [],
      explorationData: [],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: null, // 总条数
      pageSize: 10, // 每页展示条数
      costCurrentPage: 1,
      costPageNo: 0,
      costTotal: null, // 总条数
      costPageSize: 10, // 每页展示条数
      explorationCurrentPage: 1,
      explorationPageNo: 0,
      explorationTotal: null, // 总条数
      explorationPageSize: 10, // 每页展示条数
      doubtCurrentPage: 1, // 当前页
      doubtPageNo: 0,
      doubtTotal: null, // 总条数
      doubtPageSize: 10, // 每页展示条数
      clarifyCurrentPage: 1, // 当前页
      clarifyPageNo: 0,
      clarifyTotal: null, // 总条数
      clarifyPageSize: 10, // 每页展示条数
      resultCurrentPage: 1,
      resultPageNo: 0,
      resultTotal: null, // 总条数
      resultPageSize: 10, // 每页展示条数
      explorationVisable: false,
      signUpVisable: false
    }
  },
  watch: {
    $route: 'resetView'
  },
  methods: {
    resetView () {
      this.getCostInfoList()
      this.getSignUpInfoList()
      this.getResultInfoList()
      this.getDocumentInfoList()
      this.getQuesAndAnsInfoList()
      this.getExplorationAddSignUps()
    },
    // 普通格式化数据，空的时候展示"---"
    simpleFormatData (row, col, cellValue) {
      return cellValue || '---'
    },
    // 格式化时间
    formatDate (row, col, cellValue) {
      return cellValue ? dateFormat(cellValue, 'yyyy-MM-dd') : '---'
    },
    // 编辑
    handleEdit (scope) {
      this.$router.push({path: `/project/projectProcess/prequalification_response/update`,
        query: {objectId: scope.row.objectId, tenderSystemCode: this.tenderSystemCode}})
    },
    handleAdd () {
      this.$router.push({path: `/project/projectProcess/prequalification_response/add`,
        query: {sectionSystemCode: this.sectionSystemCode,
          tenderSystemCode: this.tenderSystemCode}})
    },
    handleDetail (scope) {
      this.$router.push({path: `/project/projectProcess/prequalification_response/detail/${scope.row.objectId}`})
    },
    // 编辑
    handleEditCost (scope) {
      this.$router.push({path: `/project/projectProcess/prequalification_response/update_cost`,
        query: {objectId: scope.row.objectId, tenderSystemCode: this.tenderSystemCode}})
    },
    handleAddCost () {
      this.$router.push({path: `/project/projectProcess/prequalification_response/add_cost`,
        query: {sectionSystemCode: this.sectionSystemCode,
          tenderSystemCode: this.tenderSystemCode}})
    },
    handleDetailCost (scope) {
      this.$router.push({path: `/project/projectProcess/prequalification_response/cost_detail/${scope.row.objectId}`})
    }, // 编辑
    handleEditFile (scope) {
      this.$router.push({path: `/project/projectProcess/prequalification_response/update_file`,
        query: {objectId: scope.row.objectId, tenderSystemCode: this.tenderSystemCode}})
    },
    handleAddFile () {
      this.$router.push({path: `/project/projectProcess/prequalification_response/add_file`,
        query: {sectionSystemCode: this.sectionSystemCode,
          tenderSystemCode: this.tenderSystemCode}})
    },
    handleDetailFile (scope) {
      this.$router.push({path: `/project/projectProcess/prequalification_response/file_detail/${scope.row.objectId}`})
    },
    handleDel (scope, type) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        if (Object.is(type, 'cost')) {
          costInfo.deleteById(scope.row.objectId).then(res => {
            this.getCostInfoList()
          })
        } else if (Object.is(type, 'bond')) {
          signUpInfo.deleteById(scope.row.objectId).then(res => {
            this.getSignUpInfoList()
          })
        } else if (Object.is(type, 'file')) {
          resultInfo.deleteById(scope.row.objectId).then(res => {
            this.getResultInfoList()
          })
        }
      }).catch(() => {
        return false
      })
    },
    // 编辑
    handleEditDoubt (scope) {
      this.$router.push({path: `/project/projectProcess/prequalification_response/update_doubt`,
        query: {objectId: scope.row.objectId, tenderSystemCode: this.tenderSystemCode}})
    },
    handleAddDoubt () {
      this.$router.push({path: `/project/projectProcess/prequalification_response/add_doubt`,
        query: {sectionSystemCode: this.sectionSystemCode,
          tenderSystemCode: this.tenderSystemCode}})
    },
    handleDetailDoubt (scope) {
      this.$router.push({path: `/project/projectProcess/prequalification_response/doubt_detail/${scope.row.objectId}`})
    },
    handleDelDoubt (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        questionAndAnswerInfo.deleteById(scope.row.objectId).then(() => {
          this.getQuesAndAnsInfoList()
        })
      }).catch(() => {
        return false
      })
    },
    handleDelClarify (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        documentInfo.deleteByIdAndSectionSystemCode(scope.row.objectId,
          this.sectionSystemCode).then(() => {
          this.getDocumentInfoList()
        })
      }).catch(() => {
        return false
      })
    },
    // 编辑
    handleEditClarify (scope) {
      this.$router.push({path: `/project/projectProcess/prequalification_response/update_clarify`,
        query: {objectId: scope.row.objectId, sectionSystemCode: this.sectionSystemCode, tenderSystemCode: this.tenderSystemCode}})
    },
    handleAddClarify () {
      this.$router.push({path: `/project/projectProcess/prequalification_response/add_clarify`,
        query: {sectionSystemCode: this.sectionSystemCode, tenderSystemCode: this.tenderSystemCode}})
    },
    handleDetailClarify (scope) {
      this.$router.push({path: `/project/projectProcess/prequalification_response/clarify_detail/${scope.row.objectId}`,
        query: {sectionSystemCode: this.sectionSystemCode}})
    },
    // 编辑
    handleEditExploration (scope) {
      this.$router.push({path: `/project/projectProcess/exploration/update`,
        query: {objectId: scope.row.objectId}})
    },
    handleAddExploration () {
      this.$router.push({path: `/project/projectProcess/exploration/add`,
        query: {sectionSystemCode: this.sectionSystemCode,
          tenderSystemCode: this.tenderSystemCode}})
    },
    handleDetailExploration (scope) {
      this.$router.push({path: `/project/projectProcess/exploration/detail/${scope.row.objectId}`})
    },
    handleDelExploration (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        questionAndAnswerInfo.deleteById(scope.row.objectId).then(() => {
          this.getExplorationList()
        })
      }).catch(() => {
        return false
      })
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getSignUpInfoList()
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * this.pageSize + 1
    },
    handleCostCurrentChange (nowNum) {
      this.costCurrentPage = nowNum
      this.costPageNo = (nowNum - 1) * this.costPageSize
      this.getCostInfoList()
    },
    costIndexMethod (index) {
      return index + (this.costCurrentPage - 1) * this.costPageSize + 1
    },
    handleExplorationCurrentChange (nowNum) {
      this.explorationCurrentPage = nowNum
      this.explorationPageNo = (nowNum - 1) * this.explorationPageSize
      this.getExplorationList()
    },
    explIndexMethod (index) {
      return index + (this.explorationCurrentPage - 1) * this.explorationPageSize + 1
    },
    handleResultCurrentChange (nowNum) {
      this.resultCurrentPage = nowNum
      this.resultPageNo = (nowNum - 1) * this.resultPageSize
      this.getResultInfoList()
    },
    resultIndexMethod (index) {
      return index + (this.resultCurrentPage - 1) * this.resultPageSize + 1
    },
    handleDoubtCurrentChange (nowNum) {
      this.doubtCurrentPage = nowNum
      this.doubtPageNo = (nowNum - 1) * this.doubtPageSize
      this.getQuesAndAnsInfoList()
    },
    doubtIndexMethod (index) {
      return index + (this.doubtCurrentPage - 1) * this.doubtPageSize + 1
    },
    handleClarifyCurrentChange (nowNum) {
      this.clarifyCurrentPage = nowNum
      this.clarifyPageNo = (nowNum - 1) * this.clarifyPageSize
      this.getDocumentInfoList()
    },
    clarifyIndexMethod (index) {
      return index + (this.clarifyCurrentPage - 1) * this.clarifyPageSize + 1
    },
    getCostInfoList () {
      let query = {
        pageNo: this.costPageNo,
        pageSize: this.costPageSize,
        tenderSystemCode: this.tenderSystemCode,
        sectionSystemCode: this.sectionSystemCode,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        type: 1
      }
      costInfo.getList(query).then(res => {
        let data = res.data.costInfoList
        if (data) {
          this.costInfoData = data.list
          this.costTotal = data.total
        }
        this.$emit('checkFlowStatusFlag', this.type)
      })
    },
    getSignUpInfoList () {
      let query = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        tenderSystemCode: this.tenderSystemCode,
        sectionSystemCode: this.sectionSystemCode,
        enterpriseId: this.$store.getters.authUser.enterpriseId
      }
      signUpInfo.getList(query).then(res => {
        let data = res.data.signUpInfoList
        if (data) {
          this.tableData = data.list
          this.total = data.total
        }
        this.$emit('checkFlowStatusFlag', this.type)
      })
    },
    getResultInfoList () {
      let query = {
        pageNo: this.resultPageNo,
        pageSize: this.resultPageSize,
        tenderSystemCode: this.tenderSystemCode,
        sectionSystemCode: this.sectionSystemCode,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        type: 1
      }
      resultInfo.getList(query).then(res => {
        let data = res.data.resultInfoList
        if (data) {
          this.resultInfoData = data.list
          this.resultTotal = data.total
        }
        this.$emit('checkFlowStatusFlag', this.type)
      })
    },
    // 资格预审文件疑义与澄清
    getQuesAndAnsInfoList () {
      let query = {
        pageNo: this.doubtPageNo,
        pageSize: this.doubtPageSize,
        tenderSystemCode: this.tenderSystemCode,
        sectionSystemCode: this.sectionSystemCode,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        type: 1
      }
      questionAndAnswerInfo.getList(query).then(res => {
        let data = res.data.questionAndAnswerInfoList
        if (data) {
          this.quesAndAnsInfoData = data.list
          this.doubtTotal = data.total
        }
        this.$emit('checkFlowStatusFlag', this.type)
      })
    },
    // 资格预审澄清文件
    getDocumentInfoList () {
      let query = {
        pageNo: this.clarifyPageNo,
        pageSize: this.clarifyPageSize,
        tenderSystemCode: this.tenderSystemCode,
        sectionSystemCode: this.sectionSystemCode,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        type: 2
      }
      documentInfo.getByRelaSection(query).then(res => {
        let data = res.data.documentInfoList
        if (data) {
          this.documentInfoData = data.list
          this.clarifyTotal = data.total
        }
        this.$emit('checkFlowStatusFlag', this.type)
      })
    },
    getExplorationList () {
      // 获取踏勘记录
      let query = {
        pageNo: this.explorationPageNo,
        pageSize: this.explorationPageSize,
        tenderSystemCode: this.tenderSystemCode,
        sectionSystemCode: this.sectionSystemCode,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        type: 6
      }
      questionAndAnswerInfo.getList(query).then(res => {
        let data = res.data.questionAndAnswerInfoList
        if (data) {
          this.explorationData = data.list
          this.explorationTotal = data.total
        }
        this.$emit('checkFlowStatusFlag', this.type)
      })
    },
    getExplorationAddSignUps () {
      tenderProject.getBaseByCode(this.tenderSystemCode).then(res => {
        let tenderProject = res.data.tenderProject
        // 组织踏勘且为资格预审，则展示组织踏勘记录列表
        if (Object.is(tenderProject.isToExplore, 1) && Object.is(tenderProject.isPreBid, 1)) {
          this.explorationVisable = true
          this.getExplorationList()
        }
        // 资格预审，则展示投标报名受理信息
        if (Object.is(tenderProject.isPreBid, 1)) {
          this.signUpVisable = true
          this.getSignUpInfoList()
        }
      })
    },
    applyCall () {
      this.$emit('checkFlowStatusFlag', this.type)
    }
  },
  mounted () {
    this.getCostInfoList()
    this.getResultInfoList()
    this.getDocumentInfoList()
    this.getQuesAndAnsInfoList()
    this.getExplorationAddSignUps()
  }
}
</script>
<style lang="less">
  #cloud_processtype{
  }
</style>
