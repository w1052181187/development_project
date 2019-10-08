<template>
  <div id="assmargindetailebox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让项目</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/assignee/look-transfer-project?roledsType=2' }">查看出让项目</el-breadcrumb-item>
        <el-breadcrumb-item>出让项目详情</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <router-link tag='span' to="/mr/assignee/look-transfer-project">返回</router-link>
      </div>
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" ref="sectionForm" label-width="100px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：" prop="sectionName">
                  <span>{{sectionForm.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：" prop="sectionNumber">
                  <span>{{sectionForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="parseInt(this.$route.query.types) === 2">
                <el-form-item label="矿山名称：" prop="unifiedTransCode">
                  <span>{{sectionForm.mineName}}</span>
                </el-form-item>
              </el-col>
              </el-row>
              <el-row>
              <el-col :span="8">
                <el-form-item label="矿种：" prop="sectionName">
                  <el-select v-model="sectionForm.minerals" disabled>
                    <el-option
                      v-for="item in speciesoptions"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="面积(km2)：" prop="sectionNumber">
                  <span>{{sectionForm.area}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让年限(年)：" prop="unifiedTransCode">
                  <span>{{sectionForm.ageLimit}}</span>
                </el-form-item>
              </el-col>
              </el-row>
              <el-row>
              <el-col :span="24">
                <el-form-item label="矿区位置：" prop="unifiedTransCode">
                  <span>{{sectionForm.allAddress}}</span>
                  <span>{{sectionForm.miningLocation}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="区域坐标：" prop="unifiedTransCode">
                  <span>{{sectionForm.areaCoordinates}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row v-if="parseInt(this.$route.query.types) === 2">
              <el-col :span="8">
                <el-form-item label="设计生产规模：">
                  <span>{{sectionForm.processScale}}</span>
                  <span v-if="sectionForm.region === 1">万吨/年</span>
                  <span v-if="sectionForm.region === 2">万立方米/年</span>
                  <span v-if="sectionForm.region === 3">亿立方米/年</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="开采深度：" prop="sectionNumber">
                  <span>{{sectionForm.miningDepth}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="开采方法：" prop="unifiedTransCode">
                  <span>{{sectionForm.miningMethod}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="资源储量情况：" prop="unifiedTransCode">
                  <span>{{sectionForm.resourceReserves}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--退回申请信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">公告信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="公告名称：">
                  <span>{{sectionForm.annoNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让文件获取时间：">
                  <span>{{sectionForm.fileObtainStartTime}}</span> 至 <span>{{sectionForm.fileObtainEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="报名时间：">
                  <span>{{sectionForm.applyStartTime}}</span> 至 <span>{{sectionForm.applyEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="是否允许联合体竞买：">
                  <span v-if="sectionForm.isAllowedBidding === 0">否</span>
                  <span v-if="sectionForm.isAllowedBidding === 1">是</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="保证金到账截止时间：" prop="">
                  <span>{{sectionForm.marginEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="parseInt(this.$route.query.landUse) === 2">
                <el-form-item label="挂牌时间：" prop="">
                  <span>{{sectionForm.hangStartTime}}</span> 至 <span>{{sectionForm.hangEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="parseInt(this.$route.query.landUse) === 1">
                <el-form-item label="拍卖开始时间：" prop="">
                  <span>{{sectionForm.saleStartTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="竞买保证金(万元)：" prop="">
                  <span>{{sectionForm.bidBond}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="起始价(万元)：" prop="">
                  <span>{{sectionForm.startingPrice}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="加价幅度(万元)：" prop="">
                  <span>{{sectionForm.bidIncrement}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="parseInt(this.$route.query.landUse) === 2">
                <el-form-item label="限时竞价开始时间：" prop="">
                  <span>{{sectionForm.limitBiddingStartTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="parseInt(this.$route.query.landUse) === 2">
                <el-form-item label="限时竞价时间(分)：" prop="">
                  <span>{{sectionForm.limitBiddingTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="parseInt(this.$route.query.landUse) === 1">
                <el-form-item label="拍卖时长(分)：" prop="">
                  <span>{{sectionForm.auctionTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="parseInt(this.$route.query.landUse) === 1">
                <el-form-item label="自动延长时间(分)：" prop="">
                  <span>{{sectionForm.extensionTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="是否存在底价：" prop="">
                  <span v-if="sectionForm.isFloorPrice === 0">否</span>
                  <span v-if="sectionForm.isFloorPrice === 1">是</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--退回申请信息-->
        <!--相关公告-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #fe8900">相关公告</span>
          <el-table
            :data="approvaltableData"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="suppleAnnoNumber"
              label="公告名称"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="type"
              label="公告类型"
              align="left">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="parseInt(scope.row.type) === 2">
                  <span class="statusbox">挂牌</span>
                </div>
                <div class="statusbigbox" v-if="parseInt(scope.row.type) === 1">
                  <span class="statusbox">拍卖</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="publishedStartTime"
              label="发布时间"
              align="left">
            </el-table-column>
            <el-table-column
              label="操作"
              align="center"
              width="120">
              <template slot-scope="scope">
                <el-button type="text" size="small">
                  查看
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!--相关公告-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="downloadFile">下载出让文件</el-button>
          <el-button class="cancel" @click="enrollBtn" v-if="userStatus === false">立即报名</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import { prProject, apply, clarifyFile, suppAnno, tdocuments } from '@/api'
import * as citydata from '@/assets/js/citydata'
import { mineTypes } from '@/assets/js/mineral/mineTypes'
export default {
  data () {
    return {
      // 三级联动
      addressOptions: citydata.CityInfo,
      cityselectedOptions: [],
      sectionForm: {},
      approvaltableData: [],
      // 矿种
      speciesoptions: mineTypes,
      // 出让人和受让人是否是同一个人
      userStatus: false
    }
  },
  methods: {
    // 内容获取
    contentFrom () {
      prProject.queryOne(this.$route.params.objectId).then((res) => {
        if (res.data.sectionOneInfo.creator === this.$store.getters.authUser.userId) {
          this.userStatus = true
        }
        this.sectionForm = res.data.sectionOneInfo
        citydata.CityInfo.map((item) => {
          if (this.sectionForm.provinceId == item.value) {
            this.sectionForm.allAddress = ''
            this.sectionForm.allAddress += item.label
            item.children.map((shi) => {
              if (this.sectionForm.cityId == shi.value) {
                this.sectionForm.allAddress += shi.label
                shi.children.map((county) => {
                  if (this.sectionForm.countyId == county.value) {
                    this.sectionForm.allAddress += county.label
                  }
                })
              }
            })
          }
        })
        let a = {}
        let b = {}
        a = res.data.sectionOneInfo
        b = res.data.sectionOneInfo.announcement
        b.objectId = res.data.sectionOneInfo.objectId
        this.sectionForm = Object.assign(a, b)
        // 相关公告（公告 补充公告）
        res.data.sectionOneInfo.announcement.suppleAnnoNumber = res.data.sectionOneInfo.announcement.annoNumber
        this.approvaltableData.push(res.data.sectionOneInfo.announcement) // 公告信息
        suppAnno.suppAnnoList(this.$route.params.objectId).then((res) => {
          if (res.data.refSuppAnnoSections.length !== 0) {
            res.data.refSuppAnnoSections.map((item) => {
              this.approvaltableData.push(item.suppAnno)
            })
          }
        })
      })
    },
    // 立即下载
    downloadFile () {
      let date = new Date()
      var sign1 = '-'
      var sign2 = ':'
      let year = date.getFullYear()
      let month = date.getMonth() + 1
      let day = date.getDate()
      let hour = date.getHours()
      let minutes = date.getMinutes()
      let seconds = date.getSeconds()
      // 给一位数数据前面加 “0”
      if (month >= 1 && month <= 9) {
        month = '0' + month
      }
      if (day >= 0 && day <= 9) {
        day = '0' + day
      }
      if (hour >= 0 && hour <= 9) {
        hour = '0' + hour
      }
      if (minutes >= 0 && minutes <= 9) {
        minutes = '0' + minutes
      }
      if (seconds >= 0 && seconds <= 9) {
        seconds = '0' + seconds
      }
      var currentdate = year + sign1 + month + sign1 + day + ' ' + hour + sign2 + minutes + sign2 + seconds
      if (this.sectionForm.fileObtainStartTime > currentdate || this.sectionForm.fileObtainEndTime < currentdate) {
        this.$message({
          type: 'warning',
          message: '下载时间不在出让文件获取范围时间内，不能下载'
        })
        return false
      }
      let url = {
        transDocSectionid: this.$route.params.objectId,
        isSend: 1
      }
      clarifyFile.projectDownlod(url).then((res) => {
        if (res.data.count === 0) {
          this.$message({
            type: 'warning',
            message: '该项目还未发布出让文件，不能下载'
          })
          return false
        } else {
          tdocuments.detailByAssignee(this.$route.params.objectId).then((res) => {
            this.$router.push({path: '/mr/assignee/transfer-documents/download-sell-file/download', query: {transDocSectionid: this.$route.params.objectId, roledsType: 2}})
          })
        }
      })
    },
    // 立即报名按钮
    enrollBtn () {
      let date = new Date()
      var sign1 = '-'
      var sign2 = ':'
      let year = date.getFullYear()
      let month = date.getMonth() + 1
      let day = date.getDate()
      let hour = date.getHours()
      let minutes = date.getMinutes()
      let seconds = date.getSeconds()
      // 给一位数数据前面加 “0”
      if (month >= 1 && month <= 9) {
        month = '0' + month
      }
      if (day >= 0 && day <= 9) {
        day = '0' + day
      }
      if (hour >= 0 && hour <= 9) {
        hour = '0' + hour
      }
      if (minutes >= 0 && minutes <= 9) {
        minutes = '0' + minutes
      }
      if (seconds >= 0 && seconds <= 9) {
        seconds = '0' + seconds
      }
      var currentdate = year + sign1 + month + sign1 + day + ' ' + hour + sign2 + minutes + sign2 + seconds
      if (this.sectionForm.applyStartTime > currentdate || this.sectionForm.applyEndTime < currentdate) {
        this.$message({
          type: 'warning',
          message: '不在报名时间，不能报名'
        })
        return false
      }
      let url = {
        isDelete: 0,
        sectionId: this.$route.params.objectId,
        assigneeId: this.$store.getters.authUser.userId
      }
      apply.applyProject(url).then((res) => {
        if (res.data.count !== 0) {
          this.$message({
            type: 'warning',
            message: '该项目已报名，不能重复报名'
          })
          return false
        } else {
          this.$router.push({path: '/mr/assignee/online-registration/look-registration/submit-registration', query: {ObjectId: this.$route.params.objectId, roledsType: 2}})
        }
      })
    }
  },
  mounted () {
    this.contentFrom()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/mineral/common.css';
  #assmargindetailebox{
    .el-icon-arrow-up:before {
      content: "\E605";
      display: none;
    }
    .el-icon-arrow-down:before {
      content: "\E603";
      display: none;
    }
    .el-select .el-input.is-disabled .el-input__inner:hover {
      border-color: transparent;
    }
    .el-input__inner {
      color: #353535!important;
      font-size: 12px!important;
      vertical-align: middle;
    }
  }
</style>
