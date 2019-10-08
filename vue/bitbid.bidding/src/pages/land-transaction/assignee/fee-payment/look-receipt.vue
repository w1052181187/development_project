<template>
  <div id="asslookpaydetailebox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>费用缴纳</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/fee-payment?roledsType=2' }">缴纳竞买保证金</el-breadcrumb-item>
        <el-breadcrumb-item>缴纳回执</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <div class="main">
        <h3>竞买保证金接收回执</h3>
        <div class="content">
          <p style="text-indent: 0em">致：<span>{{bidderName}}</span></p>
          <p>贵公司于<span>{{projectTime}}</span>缴纳的关于<span>{{projectName}}</span>的竞买保证金已确认接收。</p>
          <p>特致此函！</p>
        </div>
        <div class="bottom">
          <p>比比网</p>
          <p>2018年05月17日</p>
        </div>
      </div>
      <div class="button" @click="printBtn()"><el-button type="success" class="addbutton">下载打印</el-button></div>
    </div>
  </div>
</template>
<script>
import {miningLoadUrl} from '@/assets/js/common'
import { apply, prProject } from '@/api'
export default {
  data () {
    return {
      projectName: '',
      projectTime: '',
      bidderName: ''
    }
  },
  methods: {
    // 显示项目及报名信息详情
    detail () {
      prProject.queryOne(this.$route.query.sectionId).then((res) => {
        if (res.data.sectionOneInfo) {
          this.projectName = res.data.sectionOneInfo.sectionName
        }
      })
      apply.applyDetail(this.$route.query.objectId).then((res) => {
        if (res.data.Apply) {
          this.projectTime = res.data.Apply.lastUpdateDate
          this.bidderName = res.data.Apply.bidderName
        }
      })
    },
    printBtn () {
      window.open(`${miningLoadUrl}ftlDemo/marginFtl/${this.$route.query.objectId}`)
    }
  },
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/land-register/common.css';
  .main{
    width: 80%;
    margin: 30px auto 60px auto;
    background: #f7f7f7;
    padding: 10px 0;
  }
  .main h3{
    text-align: center;
    line-height: 100px;
    font-size: 30px;
  }
  .content{
    width: 94%;
    margin: 0 auto;
  }
  .content p{
    color: #000;
    font-size: 16px;
    line-height: 36px;
    text-indent: 2em
  }
  .content p span{
    text-decoration: underline;
  }
  .bottom{
    width: 94%;
    margin: 30px auto;
    text-align: right;
  }
  .button{
    width: 94%;
    margin: 0px auto 30px auto;
    text-align: center;
    overflow: hidden;
  }
  .addbutton{
    float: none;
  }
</style>
