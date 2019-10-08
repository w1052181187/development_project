<template>
  <div id="land_ann_lookdetailbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>项目管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/land-transaction/transfer-announ/index' }">出让公告备案</el-breadcrumb-item>
        <el-breadcrumb-item>出让公告审批</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：" prop="projectName">
                  <span>{{sectionForm.projectName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：" prop="projectNumber">
                  <span>{{sectionForm.projectNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 1 || Number(this.$route.query.methodOfTransfer) === 2">
                <el-form-item label="出让文件获取方法：" prop="fileObtainStartMethod">
                  <span>{{sectionForm.fileObtainStartMethod}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="资格预审文件获取方法：" prop="prequalificationStartMethod">
                  <span>{{sectionForm.prequalificationStartMethod}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="招标文件获取方法：" prop="biddingStartMethod">
                  <span>{{sectionForm.biddingStartMethod}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="是否允许联合体竞买：" prop="isAllowedBidding">
                  <span v-if="sectionForm.isAllowedBidding === 0">否</span>
                  <span v-if="sectionForm.isAllowedBidding === 1">是</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="竞买保证金到账截止时间：" prop="marginEndTime" class="hourbox">
                  <span>{{sectionForm.marginEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 1">
                <el-form-item label="拍卖开始时间：" prop="acutionStartTime" class="hourbox">
                  <span>{{sectionForm.acutionStartTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="递交申请文件截止时间：" prop="appliDocStartTime" class="hourbox">
                  <span>{{sectionForm.appliDocStartTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="递交投标文件截止时间：" prop="tenderDocEndTime" class="hourbox">
                  <span>{{sectionForm.tenderDocEndTime}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="申请文件递交方法：" prop="appliDocStartMethod">
                  <span>{{sectionForm.appliDocStartMethod}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="投标文件递交方法：" prop="tenderDocEndMethod">
                  <span>{{sectionForm.tenderDocEndMethod}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="申请有效期（天）：" prop="validityOfApplication">
                  <span>{{sectionForm.validityOfApplication}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="投标有效期（天）：" prop="validityOfBid">
                  <span>{{sectionForm.validityOfBid}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="文件开启时间：" prop="fileOpeningTime">
                  <span>{{sectionForm.fileOpeningTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="评标办法：" prop="bidEvaluationMethod">
                  <span v-if="sectionForm.bidEvaluationMethod === 1">综合评估法</span>
                  <span v-if="sectionForm.bidEvaluationMethod === 2">经评审最高价中标法</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12" v-if="Number(this.$route.query.methodOfTransfer) === 1 || Number(this.$route.query.methodOfTransfer) === 2">
                <el-form-item label="出让文件获取时间：" class="timesbox">
                  <span>{{sectionForm.transDocStartTime}}</span> 至 <span>{{sectionForm.transDocEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="资格预审文件获取时间：" class="timesbox">
                  <span>{{sectionForm.preDocStartTime}}</span> 至 <span>{{sectionForm.preDocEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="招标文件获取时间：" class="timesbox">
                  <span>{{sectionForm.bidDocStartTime}}</span> 至 <span>{{sectionForm.bidDocEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="报名时间：" class="timesbox">
                  <span>{{sectionForm.applyStartTime}}</span> 至 <span>{{sectionForm.applyEndTime}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row v-if="Number(this.$route.query.methodOfTransfer) === 2">
              <el-col :span="12">
                <el-form-item label="挂牌时间：" class="timesbox">
                  <span>{{sectionForm.hangStartTime}}</span> 至 <span>{{sectionForm.hangEndTime}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row v-if="Number(this.$route.query.methodOfTransfer) === 4 || Number(this.$route.query.methodOfTransfer) === 3">
              <el-col :span="12">
                <el-form-item label="答疑澄清时间：" class="timesbox">
                  <span>{{sectionForm.answerStartTime}}</span> 至 <span>{{sectionForm.answerEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="文件开启方式：" class="timesbox" prop="fileOpenMode">
                  <span v-if="sectionForm.fileOpenMode === 1">在线开标</span>
                  <span v-if="sectionForm.fileOpenMode === 2">线下开标</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="文件开启地点：" prop="fileOpeningLocation">
                  <span>{{sectionForm.fileOpeningLocation}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="评审方式：" prop="reviewTheWay">
                  <span v-if="sectionForm.reviewTheWay === 1">在线开标</span>
                  <span v-if="sectionForm.reviewTheWay === 2">线下开标</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="评审办法：" prop="reviewMethod">
                  <span v-if="sectionForm.reviewMethod === 1">合格制</span>
                  <span v-if="sectionForm.reviewMethod === 2">有限数量制</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3" class="hourbox">
                <el-form-item label="开标时间：" prop="bidOpeningTime">
                  <span>{{sectionForm.bidOpeningTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="开标地点：" prop="placeOfBidOpening">
                  <span>{{sectionForm.placeOfBidOpening}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="开标方式：" prop="bidOpeningWay">
                  <span v-if="sectionForm.bidOpeningWay === 1">在线开标</span>
                  <span v-if="sectionForm.bidOpeningWay === 2">线下开标</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4 && (sectionForm.reviewMethod) === 2">
                <el-form-item label="合格申请人数量：" prop="qualifiedNumber">
                  <span>{{sectionForm.qualifiedNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="评标方式：" prop="bidEvaluationMethods">
                  <span v-if="sectionForm.bidEvaluationMethods === 1">在线评标</span>
                  <span v-if="sectionForm.bidEvaluationMethods === 2">线下评标</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--相关地块信息-->
        <!--拍卖类型-->
        <div class="proinfobox" v-if="Number(this.$route.query.methodOfTransfer) === 1">
          <span class="title" style="border-left: 3px solid #2d9631">相关地块信息</span>
          <div class="formmian">
            <div v-for="(relevantMassif,index) in sectionForm.landInformations" :key="index">
              <el-row>
                <span style="margin-left: 80px;margin-bottom: 22px;color: #000;font-weight: 800">块地{{index + 1}}</span>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="地块编号：">
                    <span>{{relevantMassif.sectionNumber}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="估价报告备案号：">
                    <span>{{relevantMassif.valuationReport}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="竞买保证金(万元)：">
                    <span>{{relevantMassif.bidBond}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="是否存在底价：">
                    <span v-if="relevantMassif.isFloorPrice === 1">是</span>
                    <span v-if="relevantMassif.isFloorPrice === 0">否</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="起始价(万元)：">
                    <span>{{relevantMassif.startingPrice}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="加价幅度(万元)：">
                    <span>{{relevantMassif.bidIncrement}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="地块估算价(元)：">
                    <span>{{relevantMassif.conputedPrice}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="拍卖时长(分)：">
                    <span>{{relevantMassif.auctionTime}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="自动延长时间(分)：">
                    <span>{{relevantMassif.extensionTime}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item label="受让人资格条件：">
                    <span>{{relevantMassif.prerequisites}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
        <!--拍卖类型-->
        <!--挂牌类型-->
        <div class="proinfobox" v-if="Number(this.$route.query.methodOfTransfer) === 2">
          <span class="title" style="border-left: 3px solid #2d9631">相关地块信息</span>
          <div class="formmian">
            <div v-for="(relevantMassif,index) in sectionForm.landInformations" :key="index">
              <el-row>
                <span style="margin-left: 80px;margin-bottom: 22px;color: #000;font-weight: 800">块地{{index + 1}}</span>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="地块编号：">
                    <span>{{relevantMassif.sectionNumber}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="估价报告备案号：">
                    <span>{{relevantMassif.valuationReport}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="竞买保证金(万元)：">
                    <span>{{relevantMassif.bidBond}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="是否存在底价：">
                    <span v-if="relevantMassif.isFloorPrice === 1">是</span>
                    <span v-if="relevantMassif.isFloorPrice === 0">否</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="起始价(万元)：">
                    <span>{{relevantMassif.startingPrice}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="加价幅度(万元)：">
                    <span>{{relevantMassif.bidIncrement}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="地块估算价(元)：">
                    <span>{{relevantMassif.conputedPrice}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="限时竞价开始时间：">
                    <span>{{relevantMassif.limitBiddingStartTime}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="限时竞价时间(分)：">
                    <span>{{relevantMassif.limitBiddingTime}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item label="受让人资格条件：">
                    <span>{{relevantMassif.prerequisites}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
        <!--挂牌类型-->
        <!--资格预审和资格后审-->
        <div class="proinfobox" v-if="Number(this.$route.query.methodOfTransfer) === 4 || Number(this.$route.query.methodOfTransfer) === 3">
          <span class="title" style="border-left: 3px solid #2d9631">相关地块信息</span>
          <div class="formmian">
            <div v-for="(relevantMassif,index) in sectionForm.landInformations" :key="index">
              <el-row>
                <span style="margin-left: 80px;margin-bottom: 22px;color: #000;font-weight: 800">块地{{index + 1}}</span>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="地块编号：">
                    <span>{{relevantMassif.sectionNumber}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="估价报告备案号：">
                    <span>{{relevantMassif.valuationReport}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="竞买保证金(万元)：">
                    <span>{{relevantMassif.bidBond}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="是否存在底价：">
                    <span v-if="relevantMassif.isFloorPrice === 1">是</span>
                    <span v-if="relevantMassif.isFloorPrice === 0">否</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="地块估算价(元)：">
                    <span>{{relevantMassif.conputedPrice}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item label="受让人资格条件：">
                    <span>{{relevantMassif.prerequisites}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
        <!--资格预审和资格后审-->
        <!--相关地块信息-->
        <!--公告内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">公告内容</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="公告名称：" prop="annoNumber">
                  <span>{{sectionForm.annoNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="公告类型：" prop="contacts">
                  <span v-if="sectionForm.type === 1">拍卖出让公告</span>
                  <span v-if="sectionForm.type === 2">挂牌出让公告</span>
                  <span v-if="sectionForm.type === 3">资格后审出让公告</span>
                  <span v-if="sectionForm.type === 4">资格预审出让公告</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="公告发布时间：" class="timesbox">
                  <span>{{sectionForm.annStartTime}}</span> 至 <span>{{sectionForm.annEndTime}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="公告内容：">
                  <div class="editor">
                    <editor ref="ueditor" class="ueditor" :editread="true" :content="content"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24" class="fujianbox">
                <el-form-item label="附件：">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--公告内容-->
        <!--审批-->
        <div class="proinfobox landbigbox">
          <span class="title" style="border-left: 3px solid #0206fd">项目审批</span>
          <!--审批组件-->
          <examineApprove @approveForm="approveForm"></examineApprove>
          <!--审批组件-->
        </div>
        <!--审批-->
        <!--审批记录-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #b94aff">审批记录</span>
          <el-table
            :data="recordApprovalTable"
            border>
            <el-table-column
              prop="approvrResult"
              label="审批结果"
              align="left">
              <template slot-scope="scope">
                <div v-if="parseInt(scope.row.approvrResult) === 2">
                  <span class="statusbox">审批通过</span>
                </div>
                <div v-if="parseInt(scope.row.approvrResult) === 3">
                  <span class="statusbox">审批不通过</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="approverName"
              label="操作人"
              align="left">
            </el-table-column>
            <el-table-column
              prop="approvrDate"
              label="操作时间"
              align="left">
            </el-table-column>
            <el-table-column
              prop="approvrOpinion"
              label="审批意见"
              align="left" width="700">
            </el-table-column>
          </el-table>
        </div>
        <!--审批记录-->
        <!--提交审核-->
        <div class="submit-radio">
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
        </div>
        <!--提交审核-->
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import examineApprove from '@/components/system-management/examine-approve.vue'
import { landAnno, sApproveRecord } from '@/api'
export default {
  components: {
    commonFileDownload,
    editor,
    examineApprove
  },
  data () {
    return {
      sectionForm: {},
      content: '', // 公告内容
      // 文件内容
      fileObjecit: {},
      // 审批记录
      recordApprovalTable: [],
      landInfoIds: []
    }
  },
  methods: {
    // 子组件传过的审批信息
    approveForm (val) {
      this.checkResult = val
      this.checkResult.approvalId = this.$route.params.objectId
      this.checkResult.approvalType = 3
      this.checkResult.type = 2
    },
    // 提交数据
    submitForm () {
      if (this.checkResult === undefined || this.checkResult.approvrResult === undefined) {
        this.$message({
          type: 'warning',
          message: '请选择审批结果'
        })
        return false
      }
      sApproveRecord.save(this.checkResult).then((res) => {
        this.sectionForm.status = this.checkResult.approvrResult
        this.sectionForm.landInfoIds = this.landInfoIds
        landAnno.update1(this.sectionForm).then((res) => {
          this.$router.push({path: '/system-management/land-transaction/transfer-announ/index'})
        })
      })
    },
    landAnnoInfo () {
      landAnno.detail(this.$route.params.objectId).then((res) => {
        this.sectionForm = res.data.landAnno
        if (res.data.landAnno.approveRecords3) { // 审批记录
          this.recordApprovalTable = res.data.landAnno.approveRecords3
        }
        if (res.data.landAnno.fileInformations) {
          this.fileObjecit = res.data.landAnno.fileInformations
        }
        this.content = res.data.landAnno.content
        this.$refs.ueditor.setContent(this.content) // 设置富文本内容
        for (var i = 0; i < res.data.landAnno.landInformations.length; i++) {
          this.landInfoIds.push(res.data.landAnno.landInformations[i].objectId)
        }
      })
    }
  },
  mounted () {
    this.landAnnoInfo()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/land-register/common.css';
  #land_ann_lookdetailbox{
    .timesbox .el-form-item__content{
      width: 74%;
    }
    .hourbox .el-date-editor{
      width: 100%;
    }
    .fujianbox .el-form-item__content{
      margin-left: 0!important;
    }
  }
</style>
