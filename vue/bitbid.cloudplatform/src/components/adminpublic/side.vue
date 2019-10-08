<template>
  <div class="homepagcon_leftbox" id="homepagcon_leftbox">
    <el-row class="tac">
      <el-col :span="24">
        <el-menu
          :default-active="$route.path"
          :router="true"
          class="el-menu-vertical-demo">
          <el-menu-item index="/admin">
            <span class="iconimg">&#xe62f;</span>
            <span slot="title">首页</span>
          </el-menu-item>
          <!-- <el-menu-item index="/authentication">
            <span class="iconimg">&#xe6c3;</span>
            <span slot="title">企业认证</span>
          </el-menu-item> -->
          <!--<el-menu-item index="/openservice">-->
            <!--<span class="iconimg">&#xe64b;</span>-->
            <!--<span slot="title">开通服务</span>-->
          <!--</el-menu-item>-->
          <el-menu-item index="/organ/department">
            <span class="iconimg">&#xe681;</span>
            <span slot="title">组织结构</span>
          </el-menu-item>
          <!-- <el-menu-item index="5">
            <span class="iconimg">&#xe602;</span>
            <span slot="title">订单管理</span>
          </el-menu-item>
          <el-menu-item index="6">
            <span class="iconimg">&#xe640;</span>
            <span slot="title">证书业务</span>
          </el-menu-item> -->
        </el-menu>
      </el-col>
    </el-row>
  </div>
</template>
<script>
export default {
  components: {},
  data () {
    return {}
  },
  methods: {},
  mounted () {
  }
}
</script>
<style lang="less">
  .homepagecontent{
    width: 1200px;
    min-height:750px;
    margin: 0 auto;
    /*background: red;*/
  }
  /*左侧边样式*/
  .homepagcon_leftbox{
    display: inline-block;
    width: 167px;
    background:#fff;
    vertical-align: top;
    box-sizing: border-box;
    margin-top:5vh;
  }
  /*右侧边样式*/
  .homepageconright{
    display: inline-block;
    width: 1020px;
    min-height:100vh;
    background:#fff;
    vertical-align: top;
    margin-left:5px;
    border: 1px solid #f4f4f4;
    box-sizing: border-box;
  }
  /*面包屑样式*/
  .breadcrumbbox{
    width:100%;
    height: 40px;
    line-height: 40px!important;
    padding: 0 20px;
    box-sizing: border-box;
  }
  .penserviceconbox{
    padding: 15px 20px;
    box-sizing: border-box;
  }
  .penservicecontitle{
    font-size: 15px;
    font-weight: bold;
    color:#4a4a4a;
    margin-bottom: 40px;
    height: 54px;
    line-height: 54px;
    border-bottom:1px solid #eeeeee;
  }
  #homepagcon_leftbox{
    @font-face {
      font-family: "iconfont";
      src: url("../../../static/font/iconfont.woff");
    }
    /*表格样式*/
    .tableheader{
      background: #f5f7f8;
      color:#333333;
    }
    /*修改选中样式*/
    .el-menu-item.is-active {
      color: #fff!important;
      background:#3f63f6!important;
    }
    .iconimg{
      font-family:"iconfont";
      margin-right:10px;
      font-size: 18px;
    }
  }
</style>
