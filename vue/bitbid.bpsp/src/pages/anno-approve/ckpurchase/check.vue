<template>
  <div id="ckpurchasedetail" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>公告审核</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/annoapprove/ckpurchase' }">采购公告审核</el-breadcrumb-item>
        <el-breadcrumb-item>审核采购公告</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <template>
          <el-button @click="close">返回</el-button>
        </template>
      </div>
    </div>
    <div class="contentbigbox">
      <template>
        <el-form :model="tenderBulletin" ref="tenderBulletins">
          <table class="detailtable">
            <tr>
              <td>公告名称：</td>
              <td  colspan="2">
                  <span>{{tenderBulletin.noticeName}}</span>
              </td>
              <td>公告类型：</td>
              <td  colspan="2">
                <span v-if="tenderBulletin.bulletinType === 1">资格预审公告</span>
                <span v-if="tenderBulletin.bulletinType === 2">招标公告</span>
                <span v-if="tenderBulletin.bulletinType === 3">询价公告</span>
                <span v-if="tenderBulletin.bulletinType === 4">竞争性谈判公告</span>
                <span v-if="tenderBulletin.bulletinType === 5">竞争性磋商公告</span>
              </td>
            </tr>
            <tr>
              <td>公告性质：</td>
              <td  colspan="2">
                <span v-if="tenderBulletin.noticeNature === 1">正常公告</span>
                <span v-if="tenderBulletin.noticeNature === 2">更正公告</span>
                <span v-if="tenderBulletin.noticeNature === 3">重发公告</span>
                <span v-if="tenderBulletin.noticeNature === 9">其他</span>
              </td>
              <td>交易分类：</td>
              <td colspan="2" style="background: #ffffff">
                <span v-if="tenderBulletin.tenderExpandsInfo.dealClassify === 1">工程建设项目招标投标</span>
                <span v-if="tenderBulletin.tenderExpandsInfo.dealClassify === 2">政府采购</span>
                <span v-if="tenderBulletin.tenderExpandsInfo.dealClassify === 3">企业采购</span>
                <span v-if="tenderBulletin.tenderExpandsInfo.dealClassify === 4">其他交易</span>
              </td>
            </tr>
            <tr>
              <td>采购项目编号：</td>
              <td  colspan="2">
                <span>{{tenderBulletin.tenderProjectCode}}</span>
              </td>
              <td>采购项目名称：</td>
              <td  colspan="2">
                <span>{{tenderBulletin.tenderExpandsInfo.tenderProjectName}}</span>
              </td>
            </tr>
            <tr>
              <td>项目行业：</td>
              <td  colspan="2">
                <span>{{industryStr}}</span>
              </td>
              <td>项目类型：</td>
              <td  colspan="2">
                <span v-if="tenderBulletin.tenderExpandsInfo.projectType === 1">工程</span>
                <span v-if="tenderBulletin.tenderExpandsInfo.projectType === 2">货物</span>
                <span v-if="tenderBulletin.tenderExpandsInfo.projectType === 3">服务</span>
              </td>
            </tr>
            <tr>
              <td>行政区域：</td>
              <td  colspan="2">
                <span>{{regionStr}}</span>
              </td>
              <td>采购方式：</td>
              <td  colspan="2">
                <span v-if="tenderBulletin.tenderExpandsInfo.tenderMode === 1">公开招标</span>
                <span v-if="tenderBulletin.tenderExpandsInfo.tenderMode === 2">邀请招标</span>
                <span v-if="tenderBulletin.tenderExpandsInfo.tenderMode === 3">竞争性磋商</span>
                <span v-if="tenderBulletin.tenderExpandsInfo.tenderMode === 4">竞争性谈判</span>
                <span v-if="tenderBulletin.tenderExpandsInfo.tenderMode === 5">单一来源</span>
                <span v-if="tenderBulletin.tenderExpandsInfo.tenderMode === 6">询价</span>
              </td>
            </tr>
            <tr>
              <td>业主名称：</td>
              <td  colspan="2">
                <span>{{tenderBulletin.tenderExpandsInfo.ownerName}}</span>
              </td>
              <td>采购组织形式：</td>
              <td  colspan="2">
                <span v-if="tenderBulletin.tenderExpandsInfo.tenderOrganizeForm === 1">自行招标</span>
                <span v-if="tenderBulletin.tenderExpandsInfo.tenderOrganizeForm === 2">委托招标</span>
              </td>
            </tr>
            <tr v-if="tenderBulletin.tenderExpandsInfo.tenderOrganizeForm !== 1">
              <td>招标代理机构名称：</td>
              <td  colspan="2">
                <span>{{tenderBulletin.tenderExpandsInfo.tenderAgencyName}}</span>
              </td>
              <td>代理机构联系人：</td>
              <td  colspan="2">
                <span>{{tenderBulletin.tenderExpandsInfo.tenderAgencyContacts}}</span>
              </td>
            </tr>
            <tr>
              <td>联系方式：</td>
              <td  colspan="2">
                <span>{{tenderBulletin.tenderExpandsInfo.contactInformation}}</span>
              </td>
              <td>发布频道：</td>
              <td  colspan="2">
                <span v-if="tenderBulletin.tenderExpandsInfo.type === 3">采购公告</span>
                <span v-if="tenderBulletin.tenderExpandsInfo.type === 5">山东水务招标公司频道</span>
                <span v-if="tenderBulletin.tenderExpandsInfo.type === 6">山东鲁成招标公司频道</span>
                <span v-if="tenderBulletin.tenderExpandsInfo.type === 7">邹城市公共资源招投标频道</span>
                <span v-if="tenderBulletin.tenderExpandsInfo.type === 8">滨州市公共资源招投标频道</span>
              </td>
            </tr>
            <tr>
              <td>开标时间：</td>
              <td colspan="2">
                <span>{{tenderBulletin.bidOpenTime}}</span>
              </td>
              <td>是否接受联合体：</td>
              <td  colspan="2">
                <span v-if="tenderBulletin.syndicatedFlag === '0'">否</span>
                <span v-else>是</span>
              </td>
            </tr>
            <tr>
              <td>公告内容：</td>
              <td  colspan="5">
                <template prop="noticeContent">
                  <editor ref="ueditor" class="ueditor" :editread="true"></editor>
                </template>
              </td>
            </tr>
            <tr v-if="fileObject !== null">
              <td>公告附件：</td>
              <td  colspan="5">
                <filedownload :fileObject="fileObject"></filedownload>
              </td>
            </tr>
            <approve @approveForm="approveForm" ref="approveForm"></approve>
          </table>
          <el-form-item class="submit-radio">
            <el-button type="primary" @click="submitForm()">提交</el-button>
            <el-button class="cancel" @click="close">取消</el-button>
          </el-form-item>
        </el-form>
      </template>
    </div>
  </div>
</template>
<script>
import * as industry from '../../../assets/js/industry'
import * as region from '../../../assets/js/region'
import editor from '@/components/ueditor/ueditor.vue'
import filedownload from '@/components/file-download'
import approve from '@/components/approve.vue'
import {purchase} from '@/api'
export default {
  components: {
    editor,
    filedownload,
    approve
  },
  data () {
    return {
      industryList: industry.industry,
      regionStr: '',
      industryStr: '',
      addressOptions: region.CityInfo,
      fileObject: {},
      tenderBulletin: {
        noticeName: '',
        bulletinType: '',
        noticeNature: '',
        tenderProjectCode: '',
        tenderExpandsInfo: {
          tenderProjectName: '',
          industryTypeFirst: '',
          projectType: '',
          provinceId: '',
          tenderMode: '',
          ownerName: '',
          tenderOrganizeForm: '',
          tenderAgencyName: '',
          tenderAgencyContacts: '',
          contactInformation: ''
        },
        bidOpenTime: '',
        syndicatedFlag: ''
      },
      childForm: {},
      status: null
    }
  },
  methods: {
    detail () {
      purchase.detail(this.$route.query.objectId).then((res) => {
        // 行业
        this.industryList.map((item) => {
          if (item.value === res.data.purchaseBulletin.tenderExpandsInfo.industryTypeFirst) {
            this.industryStr = item.label
          }
        })
        // 行政区域
        this.addressOptions.map((item) => {
          if (item.value === res.data.purchaseBulletin.tenderExpandsInfo.provinceId) {
            this.regionStr += item.label
            item.children.map((ite) => {
              if (ite.value === res.data.purchaseBulletin.tenderExpandsInfo.cityId) {
                this.regionStr += ite.label
              }
            })
          }
        })
        this.tenderBulletin = res.data.purchaseBulletin
        this.fileObject = res.data.purchaseBulletin.fileInformations.length === 0 ? null : res.data.purchaseBulletin.fileInformations[0]
        return this.tenderBulletin
      }).then((result) => {
        this.$refs.ueditor.setContent(result.noticeContent)
      })
    },
    close () {
      this.$router.push({path: '/annoapprove/ckpurchase', query: {status: this.status}})
    },
    // 时间戳转换
    timestampToTime (row) {
      var date = new Date(row) // 时间戳为10位需*1000，时间戳为13位的话不需乘1000
      var Y = date.getFullYear() + '-'
      var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-'
      var D = date.getDate() + ' '
      var h = date.getHours() + ':'
      var m = date.getMinutes() + ':'
      var s = date.getSeconds()
      return Y + M + D + h + m + s
    },
    approveForm (formV) {
      this.childForm = formV
      this.childForm.relatedCode = this.tenderBulletin.code
    },
    submitForm () {
      // 必填验证
      this.$refs.approveForm.handleValidate()
      if (this.childForm.auditStatus === 0 && !this.childForm.opinion.trim()) {
        this.$message({
          message: '请填写审批意见',
          type: 'warning'
        })
        return false
      }
      // 提交数据
      this.tenderBulletin.recordOfApprovals = []
      this.tenderBulletin.recordOfApprovals.push(this.childForm)
      // 同步审批结果
      this.tenderBulletin.status = this.childForm.auditStatus === 1 ? 2 : 3
      purchase.approve(this.tenderBulletin).then((res) => {
        this.$router.push({path: '/annoapprove/ckpurchase', query: {status: this.status}})
      })
    }
  },
  mounted () {
    this.status = this.$route.query.status ? this.$route.query.status : null
    this.detail()
  }
}
</script>
<style lang="less">
  #ckpurchasedetail{
    .select {
      float: left;
      width: 100%;
    }
    .el-cascader{
      float: left;
      width: 100%;
    }
    .el-date-editor{
      float: left;
      width: 100%;
    }
    .el-date-editor.el-input, .el-date-editor.el-input__inner{
      width: 100%;
    }
    .el-radio{
      float: left;
      line-height: 40px;
    }
    span.el-radio__input,span.el-radio__label{
      float: none;
    }
    .el-textarea__inner{
      height: 120px;
    }
  }
</style>
