<template>
  <div class="header_green" id="header_green">
    <div class="topHead_green">
      <div class="fl">
        <img src="../../static/images/logo.png"/>
        <span>比比采购</span>
      </div>
      <div class="fr">
        <el-link href="" :underline="false">首页</el-link>
        <el-link href="" :underline="false">直采商城</el-link>
        <el-link href="" :underline="false">采购招标</el-link>
        <el-link href="" :underline="false">供应商库</el-link>
        <el-link href="" :underline="false">园区直采</el-link>
        <el-link href="" :underline="false">询价信息</el-link>
        <el-link href="" :underline="false">门户导航</el-link>
      </div>
    </div>
    <div class="bottomHead_green greenBg">
      <div class="fl">
        <em><img src="../../static/images/userlogo.png"/></em>
        <strong>比比采购供应商工作平台</strong>
        <span @click="jump">切为采购商</span>
      </div>
      <div class="fr">
        <ul>
          <li>
            <el-popover
              trigger="hover">
              <img slot="reference" src="../../static/images/topicon_map.png"/>
              <div class="psubmenu">
                <ul>
                  <li>
                    <span>门户</span>
                    <el-link href="" :underline="false">门户首页</el-link>
                    <el-link href="" :underline="false">采购商城</el-link>
                    <el-link href="" :underline="false">直采信息</el-link>
                  </li>
                  <li>
                    <span>供应商</span>
                    <el-link href="" :underline="false">首页信息</el-link>
                    <el-link href="" :underline="false">项目管理</el-link>
                    <el-link href="" :underline="false">企业管理</el-link>
                  </li>
                  <li>
                    <span>采购商</span>
                    <el-link href="" :underline="false">首页信息</el-link>
                    <el-link href="" :underline="false">计划管理</el-link>
                    <el-link href="" :underline="false">企业管理</el-link>
                    <el-link href="" :underline="false">企业管理</el-link>
                  </li>
                </ul>
              </div>
            </el-popover>
          </li>
          <li><img src="../../static/images/topicon_tool.png"/> </li>
          <li><img src="../../static/images/topicon_message.png"/> </li>
          <li><img src="../../static/images/topicon_edit.png"/> </li>
          <li>
            <el-dropdown  class="setbigbox">
              <span class="el-dropdown-link" style="color: #fff;">
                <span v-if="new Date().getHours() <= 4">夜深了，</span>
                <span v-else-if="new Date().getHours() <= 8">早上好，</span>
                <span v-else-if="new Date().getHours() <= 11">上午好，</span>
                <span v-else-if="new Date().getHours() <= 13">中午好，</span>
                <span v-else-if="new Date().getHours() <= 17">下午好，</span>
                <span v-else-if="new Date().getHours() <= 22">晚上好，</span>
                <span v-else>夜深了，</span>
                <span v-text="enterpriseName"></span>
                <i class="el-icon-arrow-down el-icon--right" style="cursor: pointer; color: #fff;"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <div class="partment">部门： 采购部</div>
                <span  class="logoutBtn"  @click="logout">退出账号</span>
              </el-dropdown-menu>
            </el-dropdown>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>
<script>
import { toggleClass } from '@/assets/js/theme'
// require('@/assets/css/theme/supplier/index.css')
export default {
  data () {
    return {
      enterpriseName: this.$store.getters.authUser.enterpriseName || 'admin'
    }
  },
  created () {
    toggleClass(document.body, 'custom-supplier')
  },
  methods: {
    jump () {
      this.$router.push({path: '/purchaser'})
      toggleClass(document.body, 'custom-purchase')
    },
    logout () {
      this.$store.dispatch('Logout').then(() => {
        this.$router.push({path: '/login'})
      })
    }
  }
}
</script>

<style lang='less'>
  #header_green{
    .topHead_green{
      width: 98%;
      padding: 0 1%;
      height: 26px;
      background: #fff;
      font-size: 12px;
      overflow: hidden;
    }
    .topHead_green .fl img{
      float: left;
      margin-top: 3px;
    }
    .topHead_green .fl span{
      float: left;
      line-height: 26px;
      margin-left: 10px;
    }
    .topHead_green .fr .el-link{
      margin: 0 5px;
      font-size: 12px;
      line-height: 26px;
    }
    .topHead_green .fr .el-link:hover{
      color: #009688;
    }
    .bottomHead_green{
      width: 98%;
      padding: 0 1%;
      height: 45px;
      margin: 0;
      overflow: hidden;
    }
    .bottomHead_green .fl em{
      -webkit-border-radius: 50%;
      -moz-border-radius: 50%;
      border-radius: 50%;
      margin: 11px 10px 0 0;
      float: left;
    }
    .bottomHead_green .fl em img{
      width: 24px;
      height: 24px;
    }
    .bottomHead_green .fl strong{
      color: #ffffff;
      line-height: 45px;
      font-size: 15px;
    }
    .bottomHead_green .fl span{
      margin-left: 5px;
      height: 19px;
      line-height: 19px;
      padding: 0 8px;
      border: 1px solid #d3d3d3;
      color: #d3d3d3;
      -webkit-border-radius: 10px;
      -moz-border-radius: 10px;
      border-radius: 10px;
      font-size: 12px;
      cursor: pointer;
    }
    .bottomHead_green .fr ul{
      overflow: hidden;
      display: inline-block;
    }
    .bottomHead_green .fr ul li{
      float: left;
      margin: 13px 10px 0 10px;
      color: #ffffff;
      font-size: 12px;
    }
    .bottomHead_green .fr ul li img{
      vertical-align: middle;
    }
    .bottomHead_green .fr ul li:last-child{
      margin-left: 30px;
    }
    .bottomHead_green .fr span{
      display: inline-block;
      float: left;
      margin-left: 10px;
    }
  }
  .partment{
    padding: 0 20px 5px 20px;
    font-size: 12px;
    line-height: 24px;
    border-bottom: 1px solid #dddddd;
    text-align: center;
  }
  .logoutBtn{
    width: 100%;
    display: inline-block;
    color: #ff0000;
    font-size: 12px;
    line-height:20px;
    text-align: center;
    cursor: pointer;
    margin-top: 5px;
  }
  .psubmenu{
    overflow: hidden;
  }
  .psubmenu li{
    width: 70px;
    float: left;
    margin: 0 10px;
  }
  .psubmenu li>span{
    width: 100%;
    display: inline-block;
    text-align: center;
    font-weight: bold;
    margin-bottom: 12px;
  }
  .psubmenu li a{
    width: 100%;
    display: inline-block;
    text-align: center;
    line-height: 24px;
  }
  .psubmenu li a:hover{
    color: #009688!important;
  }
</style>
