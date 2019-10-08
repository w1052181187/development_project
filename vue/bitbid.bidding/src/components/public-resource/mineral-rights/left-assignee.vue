<template>
  <el-col :span="3" style="height: 100%;background-color: rgb(25, 35, 60);">
    <div class="logobigbox">
      <img src="../../../../static/images/mineral/public/logo.png" alt="">
    </div>
    <el-scrollbar style="height: 100%;">
    <el-menu
      :default-active="activeNav"
      class="el-menu-vertical-demo sidebox"
      background-color="#19233c"
      text-color="#cccccc"
      :router="true"
      :unique-opened="true">
      <el-menu-item index="/mr/index?roledsType=2&showStatus=false" class="indexs">
        <span class="iconimg">
          <img src="../../../../static/images/mineral/public/shouye.png" alt="">
        </span>
        <span slot="title" class="titlesbox" :title="'首页'">受让人首页</span>
      </el-menu-item>
      <el-submenu index="2">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/xmgl.png" alt="">
          </span>
          <span slot="title" class="titlesbox" :title="'查看出让项目'">查看出让项目</span>
        </template>
        <el-menu-item index="/mr/assignee/look-transfer-project?roledsType=2">查看出让项目</el-menu-item>
      </el-submenu>
      <el-submenu index="3">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/crwj.png" alt="">
          </span>
          <span class="titlesbox" :title="'出让文件'">出让文件</span>
        </template>
        <el-menu-item index="/mr/assignee/transfer-documents/download-sell-file?roledsType=2">下载出让文件</el-menu-item>
        <el-menu-item index="/mr/assignee/transfer-documents/download-clarify-file?roledsType=2">下载澄清/修改文件</el-menu-item>
      </el-submenu>
      <el-submenu index="4">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/wsbm.png" alt="">
          </span>
          <span class="titlesbox" :title="'网上报名'">网上报名</span>
        </template>
        <el-menu-item index="/mr/assignee/online-registration/online-registration?roledsType=2">网上报名</el-menu-item>
        <el-menu-item index="/mr/assignee/online-registration/look-confirmation?roledsType=2">查看资格确认书</el-menu-item>
      </el-submenu>
      <el-submenu index="5">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/fyjn.png" alt="">
          </span>
          <span class="titlesbox" :title="'费用缴纳'">费用缴纳</span>
        </template>
        <el-menu-item index="/mr/assignee/fee-payment?roledsType=2">缴纳竞买保证金</el-menu-item>
      </el-submenu>
      <el-submenu index="6">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/pm.png" alt="">
          </span>
          <span class="titlesbox" :title="'拍卖'">拍卖</span>
        </template>
        <el-menu-item index="/mr/assignee/auction/auction-bids?roledsType=2">拍卖竞价</el-menu-item>
        <el-menu-item index="/mr/assignee/auction/check-auction-bids?roledsType=2">查看拍卖竞价纪录</el-menu-item>
      </el-submenu>
      <el-submenu index="7">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/gp.png" alt="">
          </span>
          <span class="titlesbox" :title="'挂牌'">挂牌</span>
        </template>
        <el-menu-item index="/mr/assignee/quotation/check-listed-bids?roledsType=2">查看挂牌竞价情况</el-menu-item>
        <el-menu-item index="/mr/assignee/quotation/bidding-records?roledsType=2">查看竞价纪录</el-menu-item>
      </el-submenu>
      <el-submenu index="8">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/cjqr.png" alt="">
          </span>
          <span class="titlesbox" :title="'成交确认'">成交确认</span>
        </template>
        <el-menu-item index="/mr/assignee/business-confirmation/receive-sales-confirmation?roledsType=2">接收成交确认书</el-menu-item>
        <el-menu-item index="/mr/assignee/business-confirmation/look-results-transaction?roledsType=2">查看成交结果公示</el-menu-item>
      </el-submenu>
      <el-submenu index="9">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/bhfw.png" alt="">
          </span>
          <span class="titlesbox" :title="'标后服务'">标后业务</span>
        </template>
        <el-menu-item index="/mr/assignee/post-bid-business/contract-sub?roledsType=2">合同提交</el-menu-item>
        <el-menu-item index="/mr/assignee/post-bid-business/margin-return?roledsType=2">申请保证金退回</el-menu-item>
      </el-submenu>
    </el-menu>
    </el-scrollbar>
  </el-col>
</template>
<script>
export default {
  data () {
    return {
      activeNav: ''
    }
  },
  name: 'left',
  methods: {
    setLeftMenu () {
      // 获得屏幕的高度
      let screenH = document.body.clientHeight
      // 获得左侧ul
      let leftUl = document.getElementsByClassName('el-menu-vertical-demo')
      // 设置左侧ul的高度
      leftUl[0].style.minHeight = (screenH - 50) + 'px'
    }
  },
  mounted () {
    this.setLeftMenu()
    this.activeNav = this.$route.matched[0].path + '?roledsType=2'
  }
}
</script>
<style lang="less">
  .el-scrollbar__wrap {
    overflow-x: hidden;
    overflow-y: auto;
  }
  /*公共侧边导航和头部开始*/
  .el-col-3{
    position: fixed;
  }
  .el-submenu__title{
    text-align: left;
    height: 50px;
    line-height: 50px;
  }
  .iconimg{
    display: inline-block;
    width: 20px;
    height: 100%;
    /*background: red;*/
    position: relative;
    vertical-align: middle;
  }
  .iconimg>img{
    width: 90%;
    position: absolute;
    left: 0;
    right: 0;
    top:0;
    bottom: 0;
    margin: auto;
  }
  .titlesbox{
    display: inline-block;
    width: 115px;
    height: 100%;
    padding: 0 5px;
    overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
    box-sizing: border-box;
    vertical-align: middle;
    height: 40px;
    line-height: 40px;
  }
  .el-aside{
    background:#19233c;
  }
  .el-menu-item.is-active{
    background: rgb(25, 35, 60)!important;
  }
  /*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
  ::-webkit-scrollbar
  {
    width: 8px;
    height: 8px;
    background-color: #F5F5F5;
  }

  /*定义滚动条轨道 内阴影+圆角*/
  ::-webkit-scrollbar-track
  {
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
    border-radius: 10px;
    background-color: #F5F5F5;
  }

  /*定义滑块 内阴影+圆角*/
  ::-webkit-scrollbar-thumb
  {
    border-radius: 10px;
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
    background-color: #cccccc}
</style>
