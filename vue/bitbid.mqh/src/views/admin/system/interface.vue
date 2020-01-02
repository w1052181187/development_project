<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="interface-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system/interface' }"><span>接口授权管理</span></el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="maincont-box">
      <el-form :model="ruleForm" label-width="130px" class="demo-ruleForm">
        <el-form-item label="办公OA:" prop="resource">
          <el-radio-group v-model="ruleForm.resource">
            <el-radio label="是"></el-radio>
            <el-radio label="否"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="合同管理系统:" prop="system">
          <el-radio-group v-model="ruleForm.system">
            <el-radio label="是"></el-radio>
            <el-radio label="否"></el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
// 实例
export default {
  data () {
    return {
      ruleForm: {}
    }
  },
  computed: {
  },
  created () {
  },
  methods: {
  },
  /**
  *实例创建完成
  */
  mounted () {

  }
}
</script>
<style lang="less">
#interface-page {
  .demo-ruleForm {
    .el-form-item__content {
      text-align: left;
    }
  }
}
</style>
