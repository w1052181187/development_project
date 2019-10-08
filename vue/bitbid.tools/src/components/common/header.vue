<template>
  <div id="header">
    <div class="bottom_bigbox">
      <div class="bottom_rightbox">
        <div class="return_btnbox" @click="logout" :title="'退出'">
          <div class="return_btn"></div>
        </div>
        <div class="company_box">
          <span>{{$store.getters.authUser.userName}}</span>，<span>山西比比公司欢迎您！</span>
        </div>
      </div>
      <div class="bottom_leftbox">
        <span class="stage_title">当前阶段：</span>
        <span>{{curStep}}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      curStep: this.$route.meta.title
    }
  },
  methods: {
    logout () {
      this.$store.dispatch('Logout').then(() => {
        this.$router.push({path: '/login'})
      })
    }
  },
  watch: {
    $route: function () {
      this.curStep = this.$route.meta.title
    }
  }
}
</script>
