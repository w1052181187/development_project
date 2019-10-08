<template>
  <div class="cloudcontent" id="cloud_processtype">
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item class="active_bread">在线审批</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="main">
      <div class="content" @click="handleApprove('need')">
        <div class="root">
          <img src="../../../assets/need.png"/>
          <span class="badge">23</span>
        </div>
        <span>待我审批</span>
      </div>
      <div class="content" @click="handleApprove('initiate')">
        <div class="root">
          <img src="../../../assets/initiate.png"/>
          <!--<span class="badge">23</span>-->
        </div>
        <span>我发起的</span>
      </div>
      <div class="content" @click="handleApprove('config')">
        <div class="root">
          <img src="../../../assets/config.png"/>
          <!--<span class="badge">23</span>-->
        </div>
        <span>审批配置</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: '',
  data () {
    return {}
  },
  methods: {
    handleApprove (type) {
      console.log(type)
      switch (type) {
        case 'need':
          // 待我审批
          this.$router.push({path: '/dailyManage/onlineapprove/need'})
          break
        case 'initiate':
          // 我发起的
          this.$router.push({path: '/dailyManage/onlineapprove/initiate'})
          break
        case 'config':
          // 审批配置
          this.$router.push({path: '/dailyManage/onlineapprove/config'})
          break
      }
    }
  },
  mounted () {
  }
}
</script>

<style scoped>
  .main {
    display: flex;
    flex-direction: row;
    margin: 24px 40px;
  }
  .root {
    width: 70px;
    height: 70px;
    position: relative;
  }
  .root .badge {
    color: white;
    font-size: 10px;
    background-color: red;
    position: absolute;
    top: -36px;
    right: -12px;
    padding: 2px 3px;
    box-sizing: border-box;
  }
  .content {
    width: 130px;
    height: 170px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-left: 24px;
    margin-right: 24px;
    border: 1px solid #cccccc;
    border-radius: 10px;
  }
  .content:hover{
    border: 1px solid #3f63f6;
  }
  .content img {
    width: 70px;
    height: 70px;
    border-radius: 50%;
  }
  .content span {
    margin-top: 24px;
    font-size: 14px;
    color: #333333;
  }
</style>
