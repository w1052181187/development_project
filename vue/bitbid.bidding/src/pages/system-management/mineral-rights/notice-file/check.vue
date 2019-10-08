<template>
  <div id="subdetailindexbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>矿权交易备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/mineral-rights/notice-file' }">出让公告备案</el-breadcrumb-item>
        <el-breadcrumb-item>出让公告审核</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="12">
                <el-form-item label="公告名称：">
                  <span>{{ruleForm.annoNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="出让方式：">
                  <span v-if="ruleForm.landUse === 1">拍卖</span>
                  <span v-if="ruleForm.landUse === 2">挂牌</span>
                  <span v-if="ruleForm.landUse === 3">其他</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="发布时间：">
                  <span>{{ruleForm.publishedStartTime}}</span> 至 <span>{{ruleForm.publishedEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="出让文件获取时间：">
                  <span>{{ruleForm.fileObtainStartTime}}</span> 至 <span>{{ruleForm.fileObtainEndTime}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="报名时间：">
                  <span>{{ruleForm.applyStartTime}}</span> 至 <span>{{ruleForm.applyEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="是否允许联合体竞买：" style="text-align: left">
                  <span v-if="ruleForm.isAllowedBidding === 0">否</span>
                  <span v-if="ruleForm.isAllowedBidding === 1">是</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="拍卖开始时间：" v-if="Number(this.$route.query.landUse) === 1">
                  <span>{{ruleForm.saleStartTime}}</span>
                </el-form-item>
                <el-form-item label="挂牌时间：" v-else>
                  <span>{{ruleForm.hangStartTime}}</span> 至 <span>{{ruleForm.hangEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="保证金到账截止时间：">
                  <span>{{ruleForm.marginEndTime}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--相关项目信息-->
        <div class="proinfobox" v-if="Number(this.$route.query.landUse) === 1">
          <span class="title" style="border-left: 3px solid #2d9631">相关项目信息</span>
          <div class="formmian" v-for="(sectionlist,index) in ruleForm.sections" :key="index" >
            <div class="contenttitle">矿权 {{index + 1 }}</div>
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：">
                  <span>{{sectionlist.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：">
                  <span>{{sectionlist.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让年限(年)：">
                  <span>{{sectionlist.ageLimit}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="矿区位置：">
                  <span>{{sectionlist.allAddress}}</span>
                  <span>{{sectionlist.miningLocation}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="竞买保证金(万元)：" prop="bidBond">
                  <span>{{sectionlist.bidBond}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="起始价(万元)：" prop="startingPrice">
                  <span>{{sectionlist.startingPrice}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="加价幅度(万元)：" prop="bidIncrement">
                  <span>{{sectionlist.bidIncrement}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="拍卖时长(分)：" prop="auctionTime">
                  <span>{{sectionlist.auctionTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="自动延长时间(分)：" prop="extensionTime">
                  <span>{{sectionlist.extensionTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="是否存在底价：" style="text-align: left" >
                  <span v-if="sectionlist.isFloorPrice === 0">否</span>
                  <span v-if="sectionlist.isFloorPrice === 1">是</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <div class="proinfobox" v-if="Number(this.$route.query.landUse) === 2">
          <span class="title" style="border-left: 3px solid #2d9631">相关项目信息</span>
          <div class="formmian" v-for="(sectionlist,index) in ruleForm.sections" :key="index" >
            <div class="contenttitle">矿权 {{index + 1 }}</div>
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：">
                  <span>{{sectionlist.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：">
                  <span>{{sectionlist.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让年限(年)：">
                  <span>{{sectionlist.ageLimit}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="矿区位置：">
                  <span>{{sectionlist.allAddress}}</span>
                  <span>{{sectionlist.miningLocation}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="竞买保证金(万元)：" prop="bidBond">
                  <span>{{sectionlist.bidBond}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="起始价(万元)：" prop="startingPrice">
                  <span>{{sectionlist.startingPrice}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
              <el-form-item label="加价幅度(万元)：" prop="bidIncrement">
                <span>{{sectionlist.bidIncrement}}</span>
              </el-form-item>
            </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="限时竞价开始时间：" prop="limitBiddingStartTime">
                  <span>{{sectionlist.limitBiddingStartTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="限时竞价时间(分)：" prop="limitBiddingTime">
                  <span>{{sectionlist.limitBiddingTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="是否存在底价：" style="text-align: left">
                  <span v-if="sectionlist.isFloorPrice === 0">否</span>
                  <span v-if="sectionlist.isFloorPrice === 1">是</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--相关项目信息-->
        <!--公告内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">公告内容</span>
          <div class="formmain">
            <el-row>
              <el-col :span="24" class="area">
                <el-form-item label="公告内容：">
                  <div class="editor">
                    <editor ref="ueditor" class="ueditor" :editread="editread" :content="content"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24" class="area">
                <el-form-item label="附件：" class="filebigbox">
                  <!--文件列表展示组件-->
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                  <!--文件列表展示组件-->
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--公告内容-->
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
          <span class="title" style="border-left: 3px solid #b94aff">审批记录</span>
          <el-table
            :data="approveRecords"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="approvrResult"
              label="审批结果"
              align="left">
              <template slot-scope="scope">
                <div v-if="parseInt(scope.row.approvrResult) === 1">
                  <span class="statusbox">审批通过</span>
                </div>
                <div v-if="parseInt(scope.row.approvrResult) === 0">
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
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm()">提交</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/system-management/ueditor/ueditor.vue'
import examineApprove from '@/components/system-management/examine-approve.vue'
import {sanno, sApproveRecord} from '@/api'
import commonFileDownload from '@/components/system-management/file-download.vue'
import * as citydata from '@/assets/js/citydata'
export default {
  components: {
    editor,
    commonFileDownload,
    examineApprove
  },
  data () {
    return {
      // 三级联动
      addressOptions: citydata.CityInfo,
      cityselectedOptions: [],
      // 富文本
      editread: true,
      content: '',
      ruleForm: {},
      radio: '1',
      fileList: [],
      dataRange: [], // 时间
      objectId: '',
      approvaltableData: [],
      sectionForm: {},
      approveRecords: [],
      // 文件
      fileObjecit: {}
    }
  },
  methods: {
    approveForm (val) {
      this.checkResult = val
      this.checkResult.approvalId = this.$route.query.objectId
      this.checkResult.approvalType = 2
    },
    // 内容获取
    list () {
      sanno.announcementDetail(this.$route.query.objectId).then((res) => {
        if (res.data.Announcement.fileInformations) {
          this.fileObjecit = res.data.Announcement.fileInformations
        }
        this.ruleForm = res.data.Announcement
        res.data.Announcement.sections.map((ite) => {
          ite.allAddress = ''
          citydata.CityInfo.map((item) => {
            if (ite.provinceId == item.value) {
              ite.allAddress += item.label
              item.children.map((shi) => {
                if (ite.cityId == shi.value) {
                  ite.allAddress += shi.label
                  shi.children.map((county) => {
                    if (ite.countyId == county.value) {
                      ite.allAddress += county.label
                    }
                  })
                }
              })
            }
          })
          return ite
        })
        this.sectionsFrom = res.data.Announcement.sections
        this.approveRecords = res.data.Announcement.approveRecords
        this.approvaltableData = res.data.Announcement.approveRecord
        this.content = res.data.Announcement.content
        this.$refs.ueditor.setContent(this.content)
      })
    },
    // 提交
    submitForm () {
      if (this.checkResult === undefined || this.checkResult.approvrResult === undefined) {
        this.$message({
          type: 'warning',
          message: '请选择审批结果'
        })
        return false
      }
      sApproveRecord.save(this.checkResult).then((res) => {
        if (this.checkResult.approvrResult === 0) {
          this.sectionForm.status = 3
        } else {
          this.sectionForm.status = 2
        }
        this.sectionForm.objectId = this.$route.query.objectId
        sanno.announcementUpdate(this.sectionForm).then((res) => {
          this.$router.push({path: '/system-management/mineral-rights/notice-file'})
        })
      })
    },
    cancelBtn () {
      this.$router.push({path: '/system-management/mineral-rights/notice-file'})
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
</style>
