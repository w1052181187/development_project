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
      <el-menu-item index="/lt/index?roledsType=1&showStatus=false" class="indexs">
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
        <el-menu-item index="/lt/transferor/project/massif-register?roledsType=1&showStatus=false" :title="'地块登记'">地块登记</el-menu-item>
        <el-menu-item index="/lt/transferor/project/look-massif?roledsType=1&showStatus=false" :title="'查看地块'">查看地块</el-menu-item>
      </el-submenu>
      <el-submenu index="3">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/xmgl.png" alt="">
          </span>
          <span class="titlesbox" :title="'出让邀请'">出让邀请</span>
        </template>
        <el-menu-item index="/lt/transferor/remise-invite/submit-invite?roledsType=1&showStatus=false" :title="'提交出让邀请书'">提交出让邀请书</el-menu-item>
        <el-menu-item index="/lt/transferor/remise-invite/send-invite?roledsType=1&showStatus=false" :title="'发出出让邀请书'">发出出让邀请书</el-menu-item>
        <el-menu-item index="/lt/transferor/remise-invite/look-invite?roledsType=1&showStatus=false" :title="'查看出让邀请确认情况'">查看出让邀请确认情况</el-menu-item>
      </el-submenu>
      <el-submenu index="4">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/crgg.png" alt="">
          </span>
          <span class="titlesbox" :title="'出让公告'">出让公告</span>
        </template>
        <el-menu-item index="/lt/transferor/announcement/sub-announ?roledsType=1&showStatus=false" :title="'提交出让公告'">提交出让公告</el-menu-item>
        <el-menu-item index="/lt/transferor/announcement/look-announ?roledsType=1&showStatus=false" :title="'查看出让公告'">查看出让公告</el-menu-item>
        <el-menu-item index="/lt/transferor/announcement/supp-announ?roledsType=1&showStatus=false" :title="'提交补充公告'">提交补充公告</el-menu-item>
        <el-menu-item index="/lt/transferor/announcement/look-supp-announ?roledsType=1&showStatus=false" :title="'查看补充公告'">查看补充公告</el-menu-item>
      </el-submenu>
      <el-submenu index="5">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/crwj.png" alt="">
          </span>
          <span class="titlesbox" :title="'拍卖/挂牌文件'">拍卖/挂牌文件</span>
        </template>
        <el-menu-item index="/lt/transferor/auction-listed-file/sub-auc-listed?roledsType=1&showStatus=false" :title="'提交出让文件'">提交出让文件</el-menu-item>
        <el-menu-item index="/lt/transferor/auction-listed-file/look-auc-listed?roledsType=1&showStatus=false" :title="'查看出让文件'">查看出让文件</el-menu-item>
        <el-menu-item index="/lt/transferor/auction-listed-file/sub-clarify?roledsType=1&showStatus=false" :title="'提交澄清/修改文件'">提交澄清/修改文件</el-menu-item>
        <el-menu-item index="/lt/transferor/auction-listed-file/look-clarify?roledsType=1&showStatus=false" :title="'查看澄清/修改文件'">查看澄清/修改文件</el-menu-item>
      </el-submenu>
      <el-submenu index="6">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/wsbm.png" alt="">
          </span>
          <span class="titlesbox" :title="'网上报名'">网上报名</span>
        </template>
        <el-menu-item index="/lt/transferor/online-registration/enrolment?roledsType=1&showStatus=false" :title="'报名受理'">报名受理</el-menu-item>
        <el-menu-item index="/lt/transferor/online-registration/look-registration?roledsType=1&showStatus=false" :title="'查看报名情况'">查看报名情况</el-menu-item>
        <el-menu-item index="/lt/transferor/online-registration/send-confirmation?roledsType=1&showStatus=false" :title="'发送资格确认书'">发送资格确认书</el-menu-item>
      </el-submenu>
      <el-submenu index="7">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/fyjn.png" alt="">
          </span>
          <span class="titlesbox" :title="'费用缴纳情况查询'">费用缴纳情况查询</span>
        </template>
        <el-menu-item index="/lt/transferor/fee-payment?roledsType=1&showStatus=false" :title="'查看竞买保证金缴纳情况'">查看竞买保证金缴纳情况</el-menu-item>
      </el-submenu>
      <el-submenu index="8">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/pm.png" alt="">
          </span>
          <span class="titlesbox" :title="'拍卖'">拍卖</span>
        </template>
        <el-menu-item index="/lt/transferor/auction/look-auction-bid?roledsType=1&showStatus=false" :title="'查看拍卖竞价情况'">查看拍卖竞价情况</el-menu-item>
        <el-menu-item index="/lt/transferor/auction/look-auction-record?roledsType=1&showStatus=false" :title="'查看竞价纪录'">查看竞价纪录</el-menu-item>
      </el-submenu>
      <el-submenu index="9">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/gp.png" alt="">
          </span>
          <span class="titlesbox" :title="'挂牌'">挂牌</span>
        </template>
        <el-menu-item index="/lt/transferor/listed/look-listed-bid?roledsType=1&showStatus=false" :title="'查看挂牌竞价情况'">查看挂牌竞价情况</el-menu-item>
        <el-menu-item index="/lt/transferor/listed/look-listed-record?roledsType=1&showStatus=false" :title="'查看竞价纪录'">查看竞价纪录</el-menu-item>
      </el-submenu>
      <el-submenu index="10">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/crwj.png" alt="">
          </span>
          <span class="titlesbox" :title="'资格预审文件'">资格预审文件</span>
        </template>
        <el-menu-item index="/lt/transferor/prequalification-file/sub-tender-file/index?roledsType=1&showStatus=false" :title="'提交资格预审文件'">提交资格预审文件</el-menu-item>
        <el-menu-item index="/lt/transferor/prequalification-file/look-tender-file/index?roledsType=1&showStatus=false" :title="'查看资格预审文件'">查看资格预审文件</el-menu-item>
        <el-menu-item index="/lt/transferor/prequalification-file/sub-clarify-file/index?roledsType=1&showStatus=false" :title="'提交资格预审文件'">提交澄清/修改文件</el-menu-item>
        <el-menu-item index="/lt/transferor/prequalification-file/look-clarify-file/index?roledsType=1&showStatus=false" :title="'提交澄清/修改文件'">查看澄清/修改文件</el-menu-item>
        <el-menu-item index="/lt/transferor/prequalification-file/look-dissent/index?roledsType=1&showStatus=false" :title="'查看异议'">查看异议</el-menu-item>
        <el-menu-item index="/lt/transferor/prequalification-file/tender-submit/index?roledsType=1&showStatus=false" :title="'查看申请文件递交情况'">查看申请文件递交情况</el-menu-item>
      </el-submenu>
      <el-submenu index="11">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/crwj.png" alt="">
          </span>
          <span class="titlesbox" :title="'资格预审会'">资格预审会</span>
        </template>
        <el-menu-item index="/lt/transferor/prequalification/prepare?roledsType=1&showStatus=false" :title="'资格预审准备'">资格预审准备</el-menu-item>
        <el-menu-item index="/lt/transferor/prequalification/file-dissent/index?roledsType=1&showStatus=false" :title="'资格预审申请文件异议'">资格预审申请文件异议</el-menu-item>
        <el-menu-item index="/lt/transferor/prequalification/pre-clarify/index?roledsType=1&showStatus=false" :title="'查看资格预审申请文件澄清'">查看资格预审申请文件澄清</el-menu-item>
        <el-menu-item index="/lt/transferor/prequalification/qual-report/index?roledsType=1&showStatus=false" :title="'提交资格审查报告'">提交资格审查报告</el-menu-item>
        <el-menu-item index="/lt/transferor/prequalification/send-advice/index?roledsType=1&showStatus=false" :title="'发出资格预审结果通知书'">发出资格预审结果通知书</el-menu-item>
        <el-menu-item index="/lt/transferor/prequalification/look-advice/index?roledsType=1&showStatus=false" :title="'查看资格预审结果通知书'">查看资格预审结果通知书</el-menu-item>
      </el-submenu>
      <el-submenu index="12">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/crwj.png" alt="">
          </span>
          <span class="titlesbox" :title="'招标、投标文件'">招标、投标文件</span>
        </template>
        <el-menu-item index="/lt/transferor/tender-file/sub-tender-file/index?roledsType=1&showStatus=false" :title="'提交招标文件'">提交招标文件</el-menu-item>
        <el-menu-item index="/lt/transferor/tender-file/look-tender-file/index?roledsType=1&showStatus=false" :title="'查看招标文件'">查看招标文件</el-menu-item>
        <el-menu-item index="/lt/transferor/tender-file/sub-clarify-file/index?roledsType=1&showStatus=false" :title="'提交澄清/修改文件'">提交澄清/修改文件</el-menu-item>
        <el-menu-item index="/lt/transferor/tender-file/look-clarify-file/index?roledsType=1&showStatus=false" :title="'查看澄清/修改文件'">查看澄清/修改文件</el-menu-item>
        <el-menu-item index="/lt/transferor/tender-file/look-dissent/index?roledsType=1&showStatus=false" :title="'查看异议'">查看异议</el-menu-item>
        <el-menu-item index="/lt/transferor/tender-file/tender-submit/index?roledsType=1&showStatus=false" :title="'查看申请文件递交情况'">查看申请文件递交情况</el-menu-item>
      </el-submenu>
      <el-submenu index="13">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/crwj.png" alt="">
          </span>
          <span class="titlesbox" :title="'开标、评标'">开标、评标</span>
        </template>
        <el-menu-item index="/lt/transferor/open-criticize-mark/bid-opening?roledsType=1&showStatus=false" :title="'开标'">开标</el-menu-item>
        <el-menu-item index="/lt/transferor/open-criticize-mark/tender-file/index?roledsType=1&showStatus=false" :title="'投标文件异议'">投标文件异议</el-menu-item>
        <el-menu-item index="/lt/transferor/open-criticize-mark/opening-recorder?roledsType=1&showStatus=false" :title="'提交开标记录表'">提交开标记录表</el-menu-item>
        <el-menu-item index="/lt/transferor/open-criticize-mark/look-recorder?roledsType=1&showStatus=false" :title="'查看开标记录表'">查看开标记录表</el-menu-item>
        <el-menu-item index="/lt/transferor/open-criticize-mark/bid-report?roledsType=1&showStatus=false" :title="'查看申请文件递交情况'">提交评标报告及中标候选人</el-menu-item>
        <el-menu-item index="/lt/transferor/open-criticize-mark/look-report?roledsType=1&showStatus=false" :title="'查看申请文件递交情况'">查看评标报告</el-menu-item>
      </el-submenu>
      <el-submenu index="14">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/cjqr.png" alt="">
          </span>
          <span class="titlesbox" :title="'成交确认'">成交确认</span>
        </template>
        <el-menu-item index="/lt/transferor/business-confirmation/confirm-people?roledsType=1&showStatus=false" :title="'确认竞得人'">确认竞得人</el-menu-item>
        <el-menu-item index="/lt/transferor/business-confirmation/issue-sales-confirmation?roledsType=1&showStatus=false" :title="'发出成交确认书'">发出成交确认书</el-menu-item>
        <el-menu-item index="/lt/transferor/business-confirmation/sub-results-transaction?roledsType=1&showStatus=false" :title="'提交成交结果公示'">提交成交结果公示</el-menu-item>
        <el-menu-item index="/lt/transferor/business-confirmation/look-results-transaction?roledsType=1&showStatus=false" :title="'查看成交结果公示'">查看成交结果公示</el-menu-item>
      </el-submenu>
      <el-submenu index="15">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/bhfw.png" alt="">
          </span>
          <span class="titlesbox" :title="'标后业务'">标后业务</span>
        </template>
        <el-menu-item index="/lt/transferor/after-business/margin-return?roledsType=1&showStatus=false" :title="'保证金退回'">保证金退回</el-menu-item>
        <el-menu-item index="/lt/transferor/after-business/contract-view?roledsType=1&showStatus=false" :title="'合同查看'">合同查看</el-menu-item>
      </el-submenu>
      <el-submenu index="16">
        <template slot="title">
          <span class="iconimg">
            <img src="../../../../static/images/mineral/public/bhfw.png" alt="">
          </span>
          <span class="titlesbox" :title="'协议出让公告'">协议出让公告</span>
        </template>
        <el-menu-item index="/lt/transferor/sub-agree-ann?roledsType=1&showStatus=false" :title="'提交协议出让公告'">提交协议出让公告</el-menu-item>
        <el-menu-item index="/lt/transferor/look-agree-ann?roledsType=1&showStatus=false" :title="'查看协议出让公告'">查看协议出让公告</el-menu-item>
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
    this.activeNav = this.$route.matched[0].path + '?roledsType=1&showStatus=false'
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
    overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
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
