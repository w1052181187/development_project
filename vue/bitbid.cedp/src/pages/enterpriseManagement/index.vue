<template>
    <div :class="$route.meta.layout === 'purchaser'? 'blueCon enterInfo' : 'greenCon enterInfo'" id="enterpriseMan">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item><a href="/">企业管理</a></el-breadcrumb-item>
        <el-breadcrumb-item>信息管理</el-breadcrumb-item>
      </el-breadcrumb>
      <el-row :gutter="20">
        <el-col :span="10">
          <div class="base_info">
            <div class="fl">
              <div class="logo">
                <img src="../../../static/images/userlogo.png"/>
              </div>
              <upload-file @uploadSuccess="uploadSuccess" @deleteSuccess="deleteSuccess" fileType="1" :fileArrays="fileInformations" isImage="1"></upload-file>
            </div>
            <div class="fr">
              <h3>{{enterprise.enterpriseName}}</h3>
              <span v-if="enterprise.status === 2"><img src="../../../static/images/enterprise/certified.png"/> 已认证</span>
              <span v-else><img src="../../../static/images/enterprise/uncertified.png"/> 未认证</span>
              <p>完成实名认证后，可享受更多服务！</p>
            </div>
          </div>
        </el-col>
        <el-col :span="14">
          <div class="data_percent">
            <p class="bgColor">更完整的资料有利于采购商找到你哦~</p>
            <div class="fl">
              <ul>
                <li>
                  <span>基本信息</span>
                  <el-progress v-if="integrity.baseScore || integrity.baseScore === 0" :percentage="Number(integrity.baseScore)"></el-progress>
                </li>
                <li>
                  <span>产品信息</span>
                  <el-progress v-if="integrity.productScore || integrity.productScore === 0" :percentage="Number(integrity.productScore)"></el-progress>
                </li>
                <li>
                  <span>资质信息</span>
                  <el-progress v-if="integrity.qualificationScore || integrity.qualificationScore === 0" :percentage="Number(integrity.qualificationScore)"></el-progress>
                </li>
                <li>
                  <span>附加信息</span>
                  <el-progress v-if="integrity.extendScore || integrity.extendScore === 0" :percentage="Number(integrity.extendScore)"></el-progress>
                </li>
                <li>
                  <span>获奖信息</span>
                  <el-progress v-if="integrity.performanceScore || integrity.performanceScore === 0" :percentage="Number(integrity.performanceScore)"></el-progress>
                </li>
                <li>
                  <span>企业介绍</span>
                  <el-progress v-if="integrity.companyProfileScore || integrity.companyProfileScore === 0" :percentage="Number(integrity.companyProfileScore)"></el-progress>
                </li>
              </ul>
            </div>
            <div class="fr">
              <el-progress type="circle" v-if="integrity.totalScore || integrity.totalScore === 0" :percentage="Number(integrity.totalScore)"></el-progress>
              <p>完整度</p>
            </div>
          </div>
        </el-col>
      </el-row>
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="认证信息" name="first"></el-tab-pane>
        <el-tab-pane label="附加信息" name="second"></el-tab-pane>
        <el-tab-pane label="业绩信息" name="third"></el-tab-pane>
        <el-tab-pane label="产品信息" name="fourth"></el-tab-pane>
        <el-tab-pane label="资质信息" name="fifth"></el-tab-pane>
        <el-tab-pane label="企业介绍" name="sixth"></el-tab-pane>
      </el-tabs>
      <div class="tab-content">
        <component :is="currentView" @childFn="isCertificated"></component>
      </div>
    </div>
</template>

<script>
import uploadFile from '@/components/upload/publicFileUpload'
import unCertificated from './unCertificated'
import certificated from './certificated'
import attachInfo from './attachInfo'
import honerInfo from './honerInfo'
import productInfo from './productInfo'
import qualificationInfo from './qualificationInfo'
import enterpriseInfo from './enterpriseInfo'
import {attachinfo, enterprise} from '@/api/system'
export default {
  components: {
    uploadFile,
    unCertificated,
    certificated,
    attachInfo,
    honerInfo,
    productInfo,
    qualificationInfo,
    enterpriseInfo
  },
  data () {
    return {
      fileInformations: [],
      activeName: 'first',
      currentView: 'unCertificated',
      isCertificatedBar: false,
      integrity: {}, // 企业完整度得分
      enterprise: {}, // 企业信息
      dataExpandForm: {'1': 1} // 企业附加信息
    }
  },
  methods: {
    uploadSuccess (file, fileType) {
      this.fileInformations = this.fileInformations.filter(item => Number(item.businessType) !== Number(fileType))
      this.fileInformations.push(file)
    },
    // 删除文件
    deleteSuccess (fileId, fileType) {
      this.fileInformations = this.fileInformations.filter(item => item.relativePath !== fileId)
    },
    // tab切换
    handleClick (tab, event) {
      if (tab.name === 'first') {
        if (this.isCertificatedBar) {
          this.currentView = 'certificated'
        } else {
          this.currentView = 'unCertificated'
        }
      } else if (tab.name === 'second') {
        this.currentView = 'attachInfo'
      } else if (tab.name === 'third') {
        this.currentView = 'honerInfo'
      } else if (tab.name === 'fourth') {
        this.currentView = 'productInfo'
      } else if (tab.name === 'fifth') {
        this.currentView = 'qualificationInfo'
      } else if (tab.name === 'sixth') {
        this.currentView = 'enterpriseInfo'
      }
    },
    // 当企业 认证信息未填写时，点击去认证
    isCertificated (val) {
      this.isCertificatedBar = val
    },
    detail () {
      let url = {
        enterpriseId: this.$store.getters.authUser.enterpriseId
      }
      attachinfo.queryOne(url).then((res) => {
        this.integrity = res.data.integrity
      })
      enterprise.queryOne(this.$store.getters.authUser.enterpriseId).then((res) => {
        this.$set(this.enterprise, 'status', res.data.enterprise.status)
        if (this.enterprise.status !== 0) {
          this.isCertificatedBar = true
        }
        this.$set(this.enterprise, 'enterpriseName', res.data.enterprise.name)
      })
    }
  },
  mounted () {
    this.detail()
  },
  watch: {
    'isCertificatedBar': {
      handler (val) {
        if (val) {
          this.currentView = 'certificated'
        } else {
          this.currentView = 'unCertificated'
        }
      }
    },
    '$store.getters.enterpriseSave': function () {
      console.log(this.$store.getters.enterpriseSave)
      this.detail()
    }
  }
}
</script>

<style lang="less">
  #enterpriseMan{
    .base_info{
      width: 100%;
      overflow: hidden;
      background: #ffffff;
    }
    .base_info .fl{
      width: 20%;
      height: 100px;
      margin: 10px 0;
      text-align: center;
    }
    .base_info .fl .logo{
      width: 100%;
      height: 69px;
      text-align: center;
      overflow: hidden;
    }
    .base_info .fl .logo img{
      max-height: 69px;
    }
    .base_info .fr{
      width: 78%;
      margin: 10px 0;
    }
    .base_info .fr h3{
      width: 100%;
      overflow: hidden;
      -ms-text-overflow: ellipsis;
      text-overflow: ellipsis;
      white-space: nowrap;
      font-size: 16px;
      font-weight: bold;
      line-height: 36px;
    }
    .base_info .fr span{
      margin: 5px 0;
      height: 20px;
      line-height: 20px;
      display: inline-block;
    }
    .base_info .fr span img{
      vertical-align: middle;
    }
    .base_info .fr p{
      color: #999999;
      line-height: 26px;
    }
    .base_info .webuploader-container{
      float: none;
    }
    .base_info .webuploader-pick{
      width: 56px;
      height: 20px;
      line-height: 20px;
      display: block;
      margin: 5px auto;
      text-align: center;
      color: #ffffff;
      font-size: 12px;
      padding: 0;
      float: none;
    }
    .base_info #picker{
      width: 56px;
      height: 20px;
      line-height: 20px;
      font-size: 10px;
      float: none;
    }
    .data_percent{
      height: 120px;
      background: #ffffff;
      position:relative;
    }
    .data_percent > p {
      position: absolute;
      width: 314px;
      height: 26px;
      line-height: 26px;
      color: #ffffff;
      left: 50%;
      top: 7px;
      margin-left: -157px;
      -webkit-border-radius: 3px;
      -moz-border-radius: 3px;
      border-radius: 3px;
      letter-spacing: 2px;
      text-align: center;
    }
    .data_percent .fl{
      width: 80%;
      overflow: hidden;
      margin-top: 40px;
    }
    .data_percent .fl ul{
      overflow: hidden;
    }
    .data_percent .fl ul li{
      width: 48%;
      float: left;
      margin: 2px 1%;
      font-size: 12px;
    }
    .data_percent .fl ul li span{
      width: 70px;
      text-align: right;
      float: left;
      display: inline-block;
    }
    .data_percent .fl ul .el-progress{
      width: 70%;
      float: left;
      margin-left: 10px;
    }
    .data_percent .fr{
      width: 18%;
      margin: 6px 0;
      text-align: center;
    }
    // tab切换
    .el-tabs__header{
      margin: 10px 0 0 0;
      background: #ffffff;
    }
    .el-tabs__nav-scroll{
      padding-left: 20px;
    }
  }
</style>
