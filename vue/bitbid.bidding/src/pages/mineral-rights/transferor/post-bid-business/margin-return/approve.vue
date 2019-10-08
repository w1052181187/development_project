<template>
  <div id="marginappaddbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>标后业务</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/transferor/post-bid-business/margin-return?roledsType=1' }">保证金退回</el-breadcrumb-item>
        <el-breadcrumb-item>审批申请</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="refundedSecurityForm" :rules="rules" ref="refundedSecurityForm" label-width="100px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：" prop="sectionName">
                  <span>{{sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：" prop="sectionNumber">
                  <span>{{sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="公告名称：" prop="unifiedTransCode">
                  <span>{{annoNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--退回申请信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">退回申请信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="退回金额(万元)：" prop="companyName">
                  <span>{{bidBond}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="退费方式：" prop="contacts">
                  <span v-if="refundedSecurityForm.payMethod === 0">在线缴纳</span>
                  <span v-if="refundedSecurityForm.payMethod === 1">保函</span>
                  <span v-if="refundedSecurityForm.payMethod === 2">其他</span>
                  <span v-if="refundedSecurityForm.payMethod === 3">支票</span>
                  <span v-if="refundedSecurityForm.payMethod === 4">线下汇款</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="保证金缴费证明：" prop="" class="bitianicon filebigbox">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="说明：" prop="PayStatus">
                  <span>{{content}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--退回申请信息-->
        <!--项目审批-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #0206fd">项目审批</span>
          <!--审批组件-->
          <examineApprove @approveForm="approveForm"></examineApprove>
          <!--审批组件-->
        </div>
        <!--项目审批-->
        <!--审批记录-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #fe8900">审批记录</span>
          <el-table
            :data="approvaltableData"
            border>
            <el-table-column
              prop="approvrResult"
              label="审批结果"
              align="left">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="Number(scope.row.approvrResult) === 0">
                  <span class="statusbox">不通过</span>
                </div>
                <div class="statusbigbox" v-if="Number(scope.row.approvrResult) === 1">
                  <span class="statusbox">通过</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="approverName"
              label="操作人"
              align="left">
            </el-table-column>
            <el-table-column
              prop="lastUpdateDate"
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
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('sectionForm')">提交</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import examineApprove from '@/components/public-resource/mineral-rights/common/examine-approve.vue'
import { refundedSecurity, ApproveRecord } from '@/api'
import commonFileDownload from '@/components/public-resource/mineral-rights/common/file-download.vue'
export default {
  components: {
    examineApprove,
    commonFileDownload
  },
  data () {
    return {
      refundedSecurityForm: {
        payMethod: Number(this.$route.query.payMethod)
      },
      sectionForm: {},
      sectionName: {},
      sectionNumber: {},
      annoNumber: '',
      content: {},
      bidBond: {},
      payTypes: [
        {
          value: 0,
          label: '在线缴纳'
        },
        {
          value: 1,
          label: '保函'
        },
        {
          value: 2,
          label: '其他'
        },
        {
          value: 3,
          label: '支票'
        },
        {
          value: 4,
          label: '线下汇款'
        }
      ],
      payTypesvalue: '',
      approvaltableData: [],
      url: {},
      rules: {
        payTypesvalue: [
          { required: true, message: '请选择缴费方式', trigger: ['blur', 'change'] }
        ]
      },
      fileObjecit: {}
    }
  },
  methods: {
    list () {
      let objectId = this.$route.query.objectId
      refundedSecurity.queryOne(objectId).then((res) => {
        if (res.data.refundedSecurityOneInfo.fileInformation) {
          this.fileObjecit = res.data.refundedSecurityOneInfo.fileInformation
        }
        this.contractForm = res.data.refundedSecurityOneInfo
        this.sectionName = res.data.refundedSecurityOneInfo.section.sectionName
        this.sectionNumber = res.data.refundedSecurityOneInfo.section.sectionNumber
        this.annoNumber = res.data.refundedSecurityOneInfo.section.announcement.annoNumber
        this.bidBond = res.data.refundedSecurityOneInfo.section.bidBond
        this.content = res.data.refundedSecurityOneInfo.content
        this.approvaltableData = res.data.refundedSecurityOneInfo.approveRecords
      })
    },
    // 提交
    approveForm (val) {
      this.checkResult = val
      this.checkResult.approvalId = this.$route.query.objectId
      this.checkResult.approvalType = 8
    },
    submitForm () {
      if (this.checkResult === undefined || this.checkResult.approvrResult === undefined) {
        this.$message({
          type: 'warning',
          message: '请选择审批结果'
        })
        return false
      }
      ApproveRecord.save(this.checkResult).then((res) => {
        if (this.checkResult.approvrResult === 0) {
          this.url.status = 3
        } else {
          this.url.status = 2
        }
        this.url.objectId = this.$route.query.objectId
        refundedSecurity.update(this.url).then((res) => {
          this.$router.push({path: '/mr/transferor/post-bid-business/margin-return?roledsType=1'})
        })
      })
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/mr/transferor/post-bid-business/margin-return?roledsType=1'})
    }
  },
  computed: {},
  created () {},
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
</style>
