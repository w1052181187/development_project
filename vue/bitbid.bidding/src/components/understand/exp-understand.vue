<template>
  <div id="homeopenserviceexp">
    <div class="homepagecontent">
      <div class="homepageconright" style="width: 100%;margin-top: 18px">
        <!--面包屑-->
        <el-breadcrumb class="breadcrumbbox" separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>服务介绍</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
        <!--内容开始-->
        <div class="penserviceconbox">
          <img src="../../../static/images/3.png" alt="">
        </div>
        <!--内容开始-->
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {}
  },
  methods: {},
  mounted () {}
}
</script>
<style lang="less">
  #homeopenserviceexp{
    .penserviceconbox{
      padding: 0;
      padding-top: 10px;
    }
    .penserviceconbox>img{
      width: 100%;
      vertical-align: top;
    }
    .homepagecontent {
      height: auto!important;
      margin: 0 auto;
    }
    .el-breadcrumb {
      font-size: 14px;
      line-height: 1;
      height: 45px;
    }
  }
</style>
