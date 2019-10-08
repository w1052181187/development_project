<template>
  <div id="header">
    <div class="headerbigbox">
     <el-dropdown trigger="click" class="setbigbox">
        <span class="el-dropdown-link">
          <span class="admin_name">{{$store.getters.authUser.userName}}</span>
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native = "logout">退出帐号</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
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
  mounted () {}
}
</script>
