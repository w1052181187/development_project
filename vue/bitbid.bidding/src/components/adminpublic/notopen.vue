<template>
  <div id="homeopenservice">
    <div class="homepagecontent">
      <publicside></publicside>
      <div class="homepageconright">
        <!--面包屑-->
        <el-breadcrumb class="breadcrumbbox" separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>开通服务</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
        <!--内容开始-->
        <div class="penserviceconbox">
          <div class="penservicecontitle">开通服务</div>
          <div class="penserviceconconbox">
            <div class="notopen_bigbox">
              <img src="../../../static/images/suo.png" alt="">
              <div>该服务暂未开通</div>
              <div>如有需要请拨打客服电话：400-000-0388</div>
            </div>
          </div>
        </div>
        <!--内容开始-->
      </div>
    </div>
  </div>
</template>
<script>
import publicside from '../adminpublic/side.vue'
export default {
  components: {
    publicside
  },
  data () {
    return {}
  },
  methods: {},
  mounted () {}
}
</script>
<style lang="less"></style>
