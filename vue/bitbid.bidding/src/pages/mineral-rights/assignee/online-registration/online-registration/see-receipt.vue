<template>
  <div id="receipt"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/assignee/online-registration/online-registration?roledsType=2' }">网上报名</el-breadcrumb-item>
        <el-breadcrumb-item>查看回执</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <div v-if="Number(this.$route.query.acceptResults) === 1">
        <div class="main">
          <h3>报名回执</h3>
          <div class="content">
            <p style="text-indent: 0em">致：<span>{{bidderName}}</span></p>
            <p>贵公司于<span>{{projectTime}}</span>，通过比比网申请的矿权交易项目 <span>{{projectName}}</span>的报名成功。</p>
            <p>特致此函！</p>
          </div>
          <div class="bottom">
            <p>比比网</p>
            <p>2018年05月17日</p>
          </div>
        </div>
        <div class="button" @click="signPassPrintBtn()"><el-button type="success" class="addbutton">下载打印</el-button></div>
      </div>
      <div v-if="Number(this.$route.query.acceptResults) === 2">
        <div class="main">
          <h3>报名申请回执</h3>
          <div class="content">
            <p style="text-indent: 0em">致：<span>{{bidderName}}</span></p>
            <p>贵公司于<span>{{projectTime}}</span>，通过比比网成功提交了关于 <span>{{projectName}}</span>的竞买报名申请。</p>
            <p>特致此函！</p>
            <p>请在《查看报名受理情况》处，查看报名受理情况。</p>
          </div>
          <div class="bottom">
            <p>比比网</p>
            <p>2018年05月17日</p>
          </div>
        </div>
        <div class="button" @click="signUpPrintBtn()"><el-button type="success" class="addbutton">下载打印</el-button></div>
      </div>
    </div>
  </div>
</template>
<script>
import {apply} from '@/api'
import {miningLoadUrl} from '@/assets/js/common'
export default {
  data () {
    return {
      projectName: '',
      projectTime: '',
      bidderName: ''
    }
  },
  methods: {
    list () {
      apply.applyDetail(this.$route.query.objectId).then((res) => {
        this.projectName = res.data.Apply.section.sectionName
        this.projectTime = res.data.Apply.lastUpdateDate
        this.bidderName = res.data.Apply.bidderName
      })
    },
    signUpPrintBtn () {
      window.open(`${miningLoadUrl}ftlDemo/apply/${this.$route.query.objectId}`)
    },
    signPassPrintBtn () {
      window.open(`${miningLoadUrl}ftlDemo/applyPass/${this.$route.query.objectId}`)
    }
  },
  computed: {
  },
  mounted () {
    this.list()
  }
}
</script>
<style scoped src="../../../../../assets/css/mineral/common.css"></style>
<style lang="less">
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
