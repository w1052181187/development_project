<template>
  <div id="ckbidwincheck" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>公告审核</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/annoapprove/ckbidwin' }">中标公示审核</el-breadcrumb-item>
        <el-breadcrumb-item>审核中标公示</el-breadcrumb-item>
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
        <el-form :model="winBidForm" ref="winBidForm">
          <table class="detailtable">
            <tr>
              <td>公示名称：</td>
              <td  colspan="2">
                <template>
                  <span>{{winBidForm.noticeName}}</span>
                </template>
              </td>
              <td>公示性质：</td>
              <td  colspan="2">
                <span v-if="winBidForm.noticeNature === '1'">正常公告</span>
                <span v-if="winBidForm.noticeNature === '2'">更正公告</span>
              </td>
            </tr>
            <tr>
              <td>招标项目编号：</td>
              <td  colspan="2">
                <span>{{winBidForm.tenderProjectCode}}</span>
              </td>
              <td>招标项目名称：</td>
              <td  colspan="2">
                <span>{{winBidForm.tenderExpandsInfo.tenderProjectName}}</span>
              </td>
            </tr>
            <tr>
              <td>项目行业：</td>
              <td  colspan="2">
                <span>{{industryStr}}</span>
              </td>
              <td>项目类型：</td>
              <td  colspan="2">
                <span v-if="winBidForm.tenderExpandsInfo.projectType === 1">工程</span>
                <span v-if="winBidForm.tenderExpandsInfo.projectType === 2">货物</span>
                <span v-if="winBidForm.tenderExpandsInfo.projectType === 3">服务</span>
              </td>
            </tr>
            <tr>
              <td>行政区域：</td>
              <td  colspan="2">
                <span>{{regionStr}}</span>
              </td>
              <td>招标组织形式：</td>
              <td  colspan="2">
                <span v-if="winBidForm.tenderExpandsInfo.tenderOrganizeForm === 1">自行招标</span>
                <span v-if="winBidForm.tenderExpandsInfo.tenderOrganizeForm === 2">委托招标</span>
              </td>
            </tr>
            <tr v-if="winBidForm.tenderExpandsInfo.tenderOrganizeForm !== 1">
              <td>招标代理机构名称：</td>
              <td  colspan="2">
                <span>{{winBidForm.tenderExpandsInfo.tenderAgencyName}}</span>
              </td>
              <td>代理机构联系人：</td>
              <td colspan="2">
                <span>{{winBidForm.tenderExpandsInfo.tenderAgencyContacts}}</span>
              </td>
            </tr>
            <tr>
              <td>业主名称：</td>
              <td  colspan="2">
                <span>{{winBidForm.tenderExpandsInfo.ownerName}}</span>
              </td>
              <td>联系方式：</td>
              <td  colspan="2">
                <span>{{winBidForm.tenderExpandsInfo.contactInformation}}</span>
              </td>
            </tr>
            <tr>
              <td>发布频道：</td>
              <td  colspan="2">
                <span v-if="winBidForm.tenderExpandsInfo.type === 2">中标公示</span>
                <span v-if="winBidForm.tenderExpandsInfo.type === 5">山东水务招标公司频道</span>
                <span v-if="winBidForm.tenderExpandsInfo.type === 6">山东鲁成招标公司频道</span>
                <span v-if="winBidForm.tenderExpandsInfo.type === 7">邹城市公共资源招投标频道</span>
                <span v-if="winBidForm.tenderExpandsInfo.type === 8">滨州市公共资源招投标频道</span>
              </td>
              <td  colspan="3" style="background: #ffffff">
              </td>
            </tr>
            <tr>
              <td>相关标段：</td>
              <td  colspan="5">
                <template>
                  <el-table
                    :data="winBidForm.sectionExpandsInfos"
                    border
                    header-cell-class-name="tabletitles"
                    class="expandtable">
                    <el-table-column
                      type="index"
                      width="50"
                      label="序号"
                      align="center">
                    </el-table-column>
                    <el-table-column
                      prop="bidSectionCode"
                      label="标段编号"
                      align="left"
                      show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column
                      prop="bidSectionName"
                      label="标段名称"
                      align="left"
                      show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column
                      prop="winBidderName"
                      label="中标人名称"
                      align="left"
                      show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column
                      prop="priceFormCode"
                      label="价款形式"
                      align="left">
                      <template slot-scope="scope">
                        <span v-if="scope.row.priceFormCode === 1">金额</span>
                        <span v-if="scope.row.priceFormCode === 2">费率/比率/优惠率/合格率等</span>
                        <span v-if="scope.row.priceFormCode === 3">其他形式</span>
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="bidAmount"
                      label="中标金额(元/百分比)"
                      align="left">
                    </el-table-column>
                  </el-table>
                </template>
              </td>
            </tr>
            <tr>
              <td>公示内容：</td>
              <td  colspan="5">
                <template >
                  <editor ref="ueditor" class="ueditor" :editread="true"></editor>
                </template>
              </td>
            </tr>
            <tr v-if="fileObject !== null">
              <td>公示附件：</td>
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
import {bidwin} from '@/api'
export default {
  components: {
    editor,
    filedownload,
    approve
  },
  data () {
    return {
      // 行业
      industryStr: '',
      // 行政区域
      regionStr: '',
      industryList: industry.industry,
      regionList: region.CityInfo,
      fileObject: {},
      winBidForm: {
        noticeName: '',
        noticeNature: '',
        tenderProjectCode: '',
        tenderExpandsInfo: {
          tenderProjectName: '',
          industryTypeFirst: '',
          projectType: '',
          provinceId: '',
          tenderOrganizeForm: '',
          ownerName: '',
          tenderAgencyName: '',
          tenderAgencyContacts: '',
          contactInformation: ''
        },
        sectionExpandsInfos: []
      },
      childForm: {},
      status: null
    }
  },
  methods: {
    detail () {
      bidwin.detail(this.$route.query.objectId).then((res) => {
        this.industryList.map((item) => {
          if (item.value === res.data.winBidBulletin.tenderExpandsInfo.industryTypeFirst) {
            this.industryStr += item.label
          }
        })
        this.regionList.map((item) => {
          if (item.value === res.data.winBidBulletin.tenderExpandsInfo.provinceId) {
            this.regionStr += item.label
            item.children.map((ite) => {
              if (ite.value === res.data.winBidBulletin.tenderExpandsInfo.cityId) {
                this.regionStr += ite.label
              }
            })
          }
        })
        this.winBidForm = res.data.winBidBulletin
        this.fileObject = res.data.winBidBulletin.fileInformations.length === 0 ? null : res.data.winBidBulletin.fileInformations[0]
        return this.winBidForm
      }).then((result) => {
        this.$refs.ueditor.setContent(result.noticeContent)
      })
    },
    close () {
      this.$router.push({path: '/annoapprove/ckbidwin', query: {status: this.status}})
    },
    approveForm (formV) {
      this.childForm = formV
      this.childForm.relatedCode = this.winBidForm.code
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
      this.winBidForm.recordOfApprovals = []
      this.winBidForm.recordOfApprovals.push(this.childForm)
      // 同步审批结果
      this.winBidForm.status = this.childForm.auditStatus === 1 ? 2 : 3
      bidwin.approve(this.winBidForm).then((res) => {
        this.$router.push({path: '/annoapprove/ckbidwin', query: {status: this.status}})
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
  #ckbidwincheck{
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
    .expandtable{
      margin-bottom: 10px;
    }
    .expandtable  .cell{
      height: 40px;
      line-height: 40px;
    }
    .expandtable  .cell span{
      color: #66b1ff;
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
