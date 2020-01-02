<template>
  <div class="admin-layout" id="default-page">
    <!--<img src="./images/default.png" alt="">-->
    <div class="default-box">
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {}
  },
  created () {},
  methods: {},
  mounted () {}
}
</script>
<style lang="less">
  #default-page {
    .default-box {
      width: 100%;
      height: 450px;
      margin-top: 200px;
      background-image: url("./images/default.png");
      background-repeat: no-repeat;
    }
    .go-back-btn {
      margin-top: -180px;
    }
  }
</style>
