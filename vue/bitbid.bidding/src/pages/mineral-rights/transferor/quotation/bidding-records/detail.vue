<template>
    <div id="auctionrecorddetail">
      <div class="headertitle">
        <!--面包屑-->
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>挂牌</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/mr/transferor/quotation/bidding-records?roledsType=1' }">查看挂牌竞价记录</el-breadcrumb-item>
          <el-breadcrumb-item>查看竞价纪录</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
      </div>
      <div class="contentbigbox detailcontentbigbox">
        <template>
          <!--主要内容 table-->
          <el-form :model="ruleForm"  label-width="100px" class="demo-ruleForm">
            <!--项目信息-->
            <div class="proinfobox">
              <span class="title">项目信息</span>
              <div class="formmian">
                <el-row>
                  <el-col :span="8">
                    <el-form-item label="项目名称：" prop="name">
                      <span>{{ruleForm.sectionName}}</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="项目编号：" prop="name">
                      <span>{{ruleForm.sectionNumber}}</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="公告名称：" prop="name">
                      <span>{{ruleForm.annoNumber}}</span>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <!--项目信息-->
            <!--竞价纪录-->
            <div class="proinfobox">
              <span class="title" style="border-left: 3px solid #2d9631">竞价纪录</span>
              <el-tabs type="border-card">
                <el-tab-pane label="限时竞价纪录">
                  <auctionRecord :roomId="hangRoomId"></auctionRecord>
                </el-tab-pane>
                <el-tab-pane label="挂牌竞价纪录">
                  <auctionRecord :roomId="limitRoomId"></auctionRecord>
                </el-tab-pane>
              </el-tabs>
            </div>
            <!--竞价纪录-->
          </el-form>
          <!--主要内容 table-->
        </template>
      </div>
    </div>
</template>
<script>
import {prProject} from '@/api'
import auctionRecord from '@/components/public-resource/mineral-rights/common/auction-record.vue'
export default {
  components: {
    auctionRecord
  },
  data () {
    let hangRoomId, limitRoomId
    if (this.$route.query.roomId) {
      hangRoomId = this.$route.query.roomId.split(',')[0]
      limitRoomId = this.$route.query.roomId.split(',')[1]
    } else {
      hangRoomId = ''
      limitRoomId = ''
    }
    return {
      ruleForm: {},
      auctiontableData: [],
      hangRoomId: hangRoomId,
      limitRoomId: limitRoomId
    }
  },
  methods: {
    setSection () {
      prProject.queryOne(this.$route.query.sectionId).then(data => {
        this.ruleForm = {
          sectionName: data.data.sectionOneInfo.sectionName,
          sectionNumber: data.data.sectionOneInfo.sectionNumber,
          annoNumber: data.data.sectionOneInfo.announcement.annoNumber
        }
      })
    }
  },
  mounted () {
    this.setSection()
  }
}
</script>
<style lang="less">
  #auctionrecorddetail{
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
    margin-top: 50px;
    .headertitle{
      width: 100%;
      height: 60px;
      line-height: 60px;
      color:#333333;
      font-size: 16px;
      text-align: left;
      padding: 0 20px;
      box-sizing: border-box;
    }
    .el-breadcrumb{
      height: 60px;
      line-height: 60px;
    }
    .proinfobox{
      padding: 20px;
      background: #ffffff;
      margin-bottom: 20px;
      overflow: hidden;
    }
    .proinfobox span.title{
      display: block;
      height: 20px;
      border-left: 3px solid #fe8900;
      padding-left: 20px;
      text-align: left;
      margin-bottom: 20px;
    }
    .el-form-item__label{
      width: 168px!important;
    }
    .el-form-item__content{
      margin-left: 168px!important;
    }
    .el-form-item__content .el-input{
      float: left;
    }
    .el-form-item__content .el-select{
      float: left;
      width: 100%;
    }
    .el-form-item__content .el-select .el-input{
      width: 100%;
    }
    .el-upload{
      float: left;
    }
    .el-button--primary{
      background-color: #2c972f;
      border-color: #2c972f;
    }
    .el-upload-list{
      width: 60%;
      float: right;
    }
    .upload{
      position: relative;
    }
    .upload input[type="file"]{
      position: absolute;
      left: 0;
      top: 0;
      width: 100px;
      height: 38px;
      font-size: 50px;
      opacity: 0;
      cursor: pointer;
    }
    .upload_btn{
      display: inline-block;
      line-height: 1;
      white-space: nowrap;
      cursor: pointer;
      -webkit-appearance: none;
      text-align: center;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      outline: 0;
      margin: 0;
      -webkit-transition: .1s;
      transition: .1s;
      padding: 12px 20px;
      font-size: 14px;
      border-radius: 4px;
      background-color: #2c972f;
      border-color: #2c972f;
      float: left;
      color: #ffffff;
    }
    .upload_file{
      display: inline-block;
      float: right;
      max-width: 60%;
    }
    .position .el-form-item__content{
      width: 100%;
    }
    .position .el-cascader{
      width: 20%;
      float: left;
      margin-right: 2%;
    }
    .position .el-cascader .el-input{
      width: 100%;
    }
    .position .el-input{
      width: 65%;
      float: left;
    }
    .area .el-form-item__content{
      width: 87%;
    }
    .submit-radio{
      width: 100%;
      text-align: left;
    }
    .submit-radio .el-form-item__content{
      margin-left: 0px!important;
    }
    .submit-radio .el-form-item__content .el-button{
      width: 83px;
      height: 32px;
      line-height: 32px;
      padding: 0;
    }
    .submit-radio .el-form-item__content .save{
      background: #ff8a00;
      border-color: #ff8a00;
    }
    .gray th{
      background: #f7f8fa!important;
    }
    .el-form span{
      color: #353535;
    }
    .el-form span.file{
      color: #00aaff;
      cursor: pointer;
    }
    .el-form .blue span{
      color: #00aaff!important;
    }
    .gray th{
      background: #f7f8fa!important;
    }
    .el-tabs--border-card{
      border: 0px!important;
      -webkit-box-shadow: 0 0 0 0 rgba(0,0,0,0) !important;
      -moz-box-shadow:  0 0 0 0 rgba(0,0,0,0) !important;
      box-shadow:  0 0 0 0 rgba(0,0,0,0) !important;
    }
    .el-tabs--border-card>.el-tabs__header{
      background-color: #ffffff;
      border-bottom: 1px solid #ffffff;
    }
    .el-tabs--border-card>.el-tabs__header .el-tabs__item{
      background: #ffffff;
      border: 1px solid #2c972f;
      color: #636363;
      margin: 0;
    }
    .el-tabs--border-card>.el-tabs__header .el-tabs__item.is-active{
      background: #2c972f;
      border: 1px solid #2c972f;
      color: #ffffff;
    }
    .el-tabs--border-card>.el-tabs__content{
      padding: 20px 0;
    }
  }
</style>
