<template>
    <!-- 后台主容器 -->
    <div class="admin-layout" id="auth-page">
      <div class="auth-img">
        <img src="./401.png" alt="">
         <div class="auth-cont">
          <p>啊哦~不小心闯入了未知领域，请点击下面按钮返回... ...</p>
          <el-button type="primary" @click="$router.go(-1)">返回</el-button>
        </div>
      </div>
    </div>
</template>

<script>
// 实例
export default {
  data () {
    return {
    }
  },
  computed: {
  },
  created () {
  },
  methods: {
  },
  // 实例创建完成
  mounted () {
  }
}
</script>

<style lang="less">
#auth-page {
  .auth-img {
    width: 1200px;
    margin: 40px auto;
    background: #fff;
  }
  .auth-cont {
    height: 100px;
    margin: -18px auto;
  }
}
</style>
