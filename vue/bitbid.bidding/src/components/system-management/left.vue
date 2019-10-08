<template>
  <el-col :span="3" style="height: 100%;background-color: rgb(25, 35, 60);" id="">
    <div class="logobigbox">
      <img src="../../../static/images/systemmanagement/logo.png" alt="">
    </div>
    <el-scrollbar style="height: 100%;">
    <el-menu
      ref="menu"
      :default-active="activeNav"
      class="el-menu-vertical-demo sidebox"
      background-color="#19233c"
      text-color="#cccccc"
      :router="true"
      :unique-opened="true"
      @close="handleClose"
      @open="handleOpen">
      <el-menu-item index="/system-management" class="indexs">
        <span class="iconimg">
          <img src="../../../static/images/systemmanagement/shouye.png" alt="">
        </span>
        <span slot="title" class="titlesbox" :title="'首页'">首页</span>
      </el-menu-item>
      <el-submenu index="2">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../static/images/systemmanagement/yypt.png" alt="">
          </span>
          <span class="titlesbox" :title="'矿权交易备案'">矿权交易备案</span>
        </template>
        <el-menu-item index="/system-management/mineral-rights/project-file" :title="'项目备案'">项目备案</el-menu-item>
        <el-menu-item index="/system-management/mineral-rights/notice-file" :title="'出让公告备案'">出让公告备案</el-menu-item>
        <el-menu-item index="/system-management/mineral-rights/supplementary-notice-file" :title="'补充公告备案'">补充公告备案</el-menu-item>
        <el-menu-item index="/system-management/mineral-rights/transfer-documents-file" :title="'出让文件备案'">出让文件备案</el-menu-item>
        <el-menu-item index="/system-management/mineral-rights/clarify-documents-file" :title="'澄清/修改文件备案'">澄清/修改文件备案</el-menu-item>
        <el-menu-item index="/system-management/mineral-rights/qualification-confirmation" :title="'资格确认情况备案'">资格确认情况备案</el-menu-item>
        <el-menu-item index="/system-management/mineral-rights/auction-bidding" :title="'拍卖竞价情况查看'">拍卖竞价情况查看</el-menu-item>
        <el-menu-item index="/system-management/mineral-rights/listing-bidding" :title="'挂牌竞价情况查看'">挂牌竞价情况查看</el-menu-item>
        <el-menu-item index="/system-management/mineral-rights/result-disclosure-file" :title="'成交结果公示备案'">成交结果公示备案</el-menu-item>
        <el-menu-item index="/system-management/mineral-rights/record-the-contract-file" :title="'合同备案'">合同备案</el-menu-item>
        <el-menu-item index="/system-management/mineral-rights/other-way-exploration-file" :title="'其他方式探矿权出让公示备案'">其他方式探矿权出让公示备案</el-menu-item>
        <el-menu-item index="/system-management/mineral-rights/other-way-mining-file" :title="'其他方式采矿权出让公示备案'">其他方式采矿权出让公示备案</el-menu-item>
        <el-menu-item index="/system-management/mineral-rights/delimit-mining-area-file" :title="'划定矿区范围公示备案'">划定矿区范围公示备案</el-menu-item>
        <el-menu-item index="/system-management/mineral-rights/mining-right-registration-file" :title="'矿业权登记公告备案'">矿业权登记公告备案</el-menu-item>
      </el-submenu>
      <el-submenu index="3">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../static/images/systemmanagement/yypt.png" alt="">
          </span>
          <span class="titlesbox" :title="'政府采购备案'">政府采购备案</span>
        </template>
        <el-menu-item index="/system-management/government-procurement/project-file" :title="'项目备案'">项目备案</el-menu-item>
        <el-menu-item index="/system-management/government-procurement/procurement-project-file" :title="'采购项目备案'">采购项目备案</el-menu-item>
        <el-menu-item index="/system-management/government-procurement/documents-file" :title="'文件公示备案'">文件公示备案</el-menu-item>
        <el-menu-item index="/system-management/government-procurement/procurement-notice-file" :title="'采购公告备案'">采购公告备案</el-menu-item>
        <el-menu-item index="/system-management/government-procurement/correct-notice-file" :title="'更正公告备案'">更正公告备案</el-menu-item>
        <el-menu-item index="/system-management/government-procurement/procurement-invite-file" :title="'采购邀请备案'">采购邀请备案</el-menu-item>
        <el-menu-item index="/system-management/government-procurement/pre-qualification-documents-file" :title="'资格预审文件备案'">资格预审文件备案</el-menu-item>
        <el-menu-item index="/system-management/government-procurement/pre-qualification-clarification-documents-file" :title="'资格预审澄清修改文件'">资格预审澄清修改文件</el-menu-item>
        <el-menu-item index="/system-management/government-procurement/pre-qualification-results-file" :title="'资格预审结果通知书备案'">资格预审结果通知书备案</el-menu-item>
        <el-menu-item index="/system-management/government-procurement/procurement-documents-file" :title="'采购文件备案'">采购文件备案</el-menu-item>
        <el-menu-item index="/system-management/government-procurement/procurement-clarification-documents-file" :title="'采购澄清修改文件备案'">采购澄清修改文件备案</el-menu-item>
        <el-menu-item index="/system-management/government-procurement/pre-qualification-purchases-file" :title="'资格预审文件购买情况备案'">资格预审文件购买情况备案</el-menu-item>
        <!--<el-menu-item index="/system-management/government-procurement/system-usage-fee-file" :title="'系统使用费缴纳情况备案'">系统使用费缴纳情况备案</el-menu-item>-->
        <el-menu-item index="/system-management/government-procurement/procurement-documents-purchases-file" :title="'采购文件购买情况情况备案'">采购文件购买情况情况备案</el-menu-item>
        <el-menu-item index="/system-management/government-procurement/bid-bond-payment-file" :title="'投标保证金缴纳情况备案'">投标保证金缴纳情况备案</el-menu-item>
        <el-menu-item index="/system-management/government-procurement/bid-opening-record-file" :title="'开标记录表备案'">开标记录表备案</el-menu-item>
        <el-menu-item index="/system-management/government-procurement/bidreport-winning-file" :title="'评标报告及中标候选人备案'">评标报告及中标候选人备案</el-menu-item>
        <el-menu-item index="/system-management/government-procurement/letter-of-acceptance-file" :title="'中标确认函备案'">中标确认函备案</el-menu-item>
        <el-menu-item index="/system-management/government-procurement/bid-result-file" :title="'中标结果公告备案'">中标结果公告备案</el-menu-item>
        <el-menu-item index="/system-management/government-procurement/bid-winning-notice-file" :title="'中标通知书备案'">中标通知书备案</el-menu-item>
        <el-menu-item index="/system-management/government-procurement/contract-file" :title="'合同备案'">合同备案</el-menu-item>
        <el-menu-item index="/system-management/government-procurement/buying-situation-file" :title="'采购情况报告备案'">采购情况报告备案</el-menu-item>
      </el-submenu>
      <el-submenu index="4">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../static/images/systemmanagement/yypt.png" alt="">
          </span>
          <span class="titlesbox" :title="'土地交易备案'">土地交易备案</span>
        </template>
        <el-menu-item index="/system-management/land-transaction/project/index" :title="'项目备案'">项目备案</el-menu-item>
        <el-menu-item index="/system-management/land-transaction/remise-invite/index" :title="'出让邀请书备案'">出让邀请书备案</el-menu-item>
        <el-menu-item index="/system-management/land-transaction/transfer-announ/index" :title="'出让公告备案'">出让公告备案</el-menu-item>
        <el-menu-item index="/system-management/land-transaction/supp-announ/index" :title="'补充公告备案'">补充公告备案</el-menu-item>
        <el-menu-item index="/system-management/land-transaction/qualification/index" :title="'资格确认情况备案'">资格确认情况备案</el-menu-item>
        <el-menu-item index="/system-management/land-transaction/auction-listed/index" :title="'拍卖/挂牌出让文件备案'">拍卖/挂牌出让文件备案</el-menu-item>
        <el-menu-item index="/system-management/land-transaction/auction/index" :title="'拍卖竞价情况备案'">拍卖竞价情况备案</el-menu-item>
        <el-menu-item index="/system-management/land-transaction/listed/index" :title="'挂牌竞价情况备案'">挂牌竞价情况备案</el-menu-item>
        <el-menu-item index="/system-management/land-transaction/prequalification-file/index" :title="'资格预审文件备案'">资格预审文件备案</el-menu-item>
        <el-menu-item index="/system-management/land-transaction/clarify/index" :title="'资格预审澄清/修改文件备案'">资格预审澄清/修改文件备案</el-menu-item>
        <el-menu-item index="/system-management/land-transaction/preliminary-results/index" :title="'资格预审结果通知书备案'">资格预审结果通知书备案</el-menu-item>
        <el-menu-item index="/system-management/land-transaction/bid-docu/index" :title="'招标文件备案'">招标文件备案</el-menu-item>
        <el-menu-item index="/system-management/land-transaction/tender-clarify/index" :title="'招标澄清/修改文件备案'">招标澄清/修改文件备案</el-menu-item>
        <el-menu-item index="/system-management/land-transaction/opening-record/index" :title="'开标记录表备案'">开标记录表备案</el-menu-item>
        <el-menu-item index="/system-management/land-transaction/evaluation-report/index" :title="'招标评标报告备案'">招标评标报告备案</el-menu-item>
        <el-menu-item index="/system-management/land-transaction/make-result/index" :title="'成交结果公示备案'">成交结果公示备案</el-menu-item>
        <el-menu-item index="/system-management/land-transaction/contract/index" :title="'合同备案'">合同备案</el-menu-item>
        <el-menu-item index="/system-management/land-transaction/agreement/index" :title="'协议出让公告备案'">协议出让公告备案</el-menu-item>
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
    },
    handleMenuChange () {
      let curPath = this.$route.path
      let keys = Object.keys(this.$refs.menu.items)
      let len = keys.length
      if (len <= 0) {
        return
      }
      if (curPath === keys[0]) {
        this.activeNav = curPath
        return
      }
      for (let i = 1; i < len; i++) {
        if (curPath.indexOf(keys[i]) !== -1) {
          this.activeNav = keys[i]
          return
        }
      }
    }
  },
  watch: {
    $route: function () {
      this.handleMenuChange()
    }
  },
  mounted () {
    this.setLeftMenu()
    this.handleMenuChange()
  }
}
</script>
<style lang="less">
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
  .el-submenu .el-menu-item {
    height: 40px;
    line-height: 40px;
    padding: 0 45px;
    min-width: 190px!important;
    box-sizing: border-box;
    overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
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
