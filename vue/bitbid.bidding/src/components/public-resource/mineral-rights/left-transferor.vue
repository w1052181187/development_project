<template>
  <el-col :span="3" style="height: 100%;background-color: rgb(25, 35, 60);" id="">
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
      :unique-opened="true"
      @close="handleClose"
      @open="handleOpen">
      <el-menu-item index="/mr/index?roledsType=1&showStatus=false" class="indexs">
        <span class="iconimg">
          <img src="../../../../static/images/mineral/public/shouye.png" alt="">
        </span>
        <span slot="title" class="titlesbox" :title="'首页'">出让人首页</span>
      </el-menu-item>
      <el-submenu index="2">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/xmgl.png" alt="">
          </span>
          <span class="titlesbox" :title="'项目管理'">项目管理</span>
        </template>
        <el-menu-item index="/mr/transferor/project/registration-project?roledsType=1">登记项目</el-menu-item>
        <el-menu-item index="/mr/transferor/project/look-project?roledsType=1">查看项目</el-menu-item>
      </el-submenu>
      <el-submenu index="3">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/crgg.png" alt="">
          </span>
          <span class="titlesbox" :title="'出让公告'">出让公告</span>
        </template>
        <el-menu-item index="/mr/transferor/submission/subminssion-notice?roledsType=1">提交出让公告</el-menu-item>
        <el-menu-item index="/mr/transferor/submission/see-remise-notice?roledsType=1">查看出让公告</el-menu-item>
        <el-menu-item index="/mr/transferor/submission/supplement-notice?roledsType=1">提交补充公告</el-menu-item>
        <el-menu-item index="/mr/transferor/submission/look-supplement-notice?roledsType=1">查看补充公告</el-menu-item>
      </el-submenu>
      <el-submenu index="4">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/crwj.png" alt="">
          </span>
          <span class="titlesbox" :title="'出让文件'">出让文件</span>
        </template>
        <el-menu-item index="/mr/transferor/transfer-documents/sub-transfer-documents?roledsType=1">提交出让文件</el-menu-item>
        <el-menu-item index="/mr/transferor/transfer-documents/look-transfer-documents?roledsType=1">查看出让文件</el-menu-item>
        <el-menu-item index="/mr/transferor/transfer-documents/sub-clarify-documents?roledsType=1">提交澄清/修改文件</el-menu-item>
        <el-menu-item index="/mr/transferor/transfer-documents/look-clarify-documents?roledsType=1">查看澄清/修改文件</el-menu-item>
      </el-submenu>
      <el-submenu index="5">
        <template slot="title">
                  <span class="iconimg">
                    <img src="../../../../static/images/mineral/public/wsbm.png" alt="">
                  </span>
          <span class="titlesbox" :title="'网上报名'">网上报名</span>
        </template>
        <el-menu-item index="/mr/transferor/online-registration/enrolment?roledsType=1">报名受理</el-menu-item>
        <el-menu-item index="/mr/transferor/online-registration/look-registration?roledsType=1">查看报名情况</el-menu-item>
        <el-menu-item index="/mr/transferor/online-registration/send-confirmation?roledsType=1">发送资格确认书</el-menu-item>
      </el-submenu>
      <el-submenu index="6">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/fyjn.png" alt="">
          </span>
          <span class="titlesbox" :title="'费用缴纳'">费用缴纳</span>
        </template>
        <el-menu-item index="/mr/transferor/fee-payment">查看竞买保证金缴纳情况</el-menu-item>
      </el-submenu>
      <el-submenu index="7">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/pm.png" alt="">
          </span>
          <span class="titlesbox" :title="'拍卖'">拍卖</span>
        </template>
        <el-menu-item index="/mr/transferor/auction/check-auction-bids?roledsType=1">查看拍卖竞价情况</el-menu-item>
        <el-menu-item index="/mr/transferor/auction/auction-bidding-records?roledsType=1">查看拍卖竞价纪录</el-menu-item>
      </el-submenu>
      <el-submenu index="8">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/gp.png" alt="">
          </span>
          <span class="titlesbox" :title="'挂牌'">挂牌</span>
        </template>
        <el-menu-item index="/mr/transferor/quotation/check-listed-bids?roledsType=1">查看挂牌竞价情况</el-menu-item>
        <el-menu-item index="/mr/transferor/quotation/bidding-records?roledsType=1">查看竞价纪录</el-menu-item>
      </el-submenu>
      <el-submenu index="9">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/cjqr.png" alt="">
          </span>
          <span class="titlesbox" :title="'成交确认'">成交确认</span>
        </template>
        <el-menu-item index="/mr/transferor/business-confirmation/confirm-people?roledsType=1">确认竞得人</el-menu-item>
        <el-menu-item index="/mr/transferor/business-confirmation/issue-sales-confirmation?roledsType=1">发出成交确认书</el-menu-item>
        <el-menu-item index="/mr/transferor/business-confirmation/sub-results-transaction?roledsType=1">提交成交结果公示</el-menu-item>
        <el-menu-item index="/mr/transferor/business-confirmation/look-results-transaction?roledsType=1">查看成交结果公示</el-menu-item>
      </el-submenu>
      <el-submenu index="10">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/bhfw.png" alt="">
          </span>
          <span class="titlesbox" :title="'标后服务'">标后服务</span>
        </template>
        <el-menu-item index="/mr/transferor/post-bid-business/margin-return?roledsType=1">保证金退回</el-menu-item>
        <el-menu-item index="/mr/transferor/post-bid-business/contract-view?roledsType=1">合同查看</el-menu-item>
      </el-submenu>
      <el-submenu index="11">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/qtfs.png" alt="">
          </span>
          <span class="titlesbox" :title="'其他方式矿业权出让'">其他方式矿业权出让</span>
        </template>
        <el-menu-item index="/mr/transferor/other-mining-sell/sub-other-prospect?type=1&name=其他方式探矿权出让&roledsType=1" class="titlesbox" :title="'提交其他方式探矿权出让公示'">提交其他方式探矿权出让公示</el-menu-item>
        <el-menu-item index="/mr/transferor/other-mining-sell/look-other-prospect?type=1&name=其他方式探矿权出让&roledsType=1" class="titlesbox" :title="'查看其他方式探矿权出让公示'">查看其他方式探矿权出让公示</el-menu-item>
        <el-menu-item index="/mr/transferor/other-mining-sell/sub-other-prospect?type=2&name=其他方式采矿权出让&roledsType=1" class="titlesbox" :title="'提交其他方式采矿权出让公示'">提交其他方式采矿权出让公示</el-menu-item>
        <el-menu-item index="/mr/transferor/other-mining-sell/look-other-prospect?type=2&name=其他方式采矿权出让&roledsType=1" class="titlesbox" :title="'查看其他方式采矿权出让公示'">查看其他方式采矿权出让公示</el-menu-item>
        <el-menu-item index="/mr/transferor/other-mining-sell/sub-other-prospect?type=3&name=划定矿区范围&roledsType=1" class="titlesbox" :title="'提交划定矿区范围公示'">提交划定矿区范围公示</el-menu-item>
        <el-menu-item index="/mr/transferor/other-mining-sell/look-other-prospect?type=3&name=划定矿区范围&roledsType=1" class="titlesbox" :title="'查看划定矿区范围公示'">查看划定矿区范围公示</el-menu-item>
      </el-submenu>
      <el-submenu index="12">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/kyq.png" alt="">
          </span>
          <span class="titlesbox" :title="'矿业权登记公告'">矿业权登记公告</span>
        </template>
        <el-menu-item index="/mr/transferor/mining-register-announcement/sub-mining-register?roledsType=1">提交矿业权登记公告</el-menu-item>
        <el-menu-item index="/mr/transferor/mining-register-announcement/look-mining-register?roledsType=1">查看矿业权登记公告</el-menu-item>
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
    handleClose (key, keyPath) {},
    handleOpen (key, keyPath) {},
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
    this.activeNav = this.$route.matched[0].path + '?roledsType=1'
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
  .el-menu-item, .el-submenu__title{
    height: 40px!important;
    line-height: 40px!important;
  }
  .el-submenu__title{
    text-align: left;
    height: 40px!important;
    line-height: 40px!important;
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
  .el-submenu .el-menu-item {
    height: 36px!important;
    line-height: 36px!important;
    padding: 0 45px;
    min-width: 190px!important;
    box-sizing: border-box;
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
