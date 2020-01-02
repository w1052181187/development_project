<template>
  <div class="station" id="station-inner-page">
    <div class="breadcrumb-mqh-tit" >
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system/industry' }"><span>行业资讯管理</span></el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system/industry/stationin' }"><span>站内资讯</span></el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <div class="maincont-box">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="标题:" prop="title">
          <el-input v-model="ruleForm.title" :disabled="!editFlag"></el-input>
        </el-form-item>
        <el-form-item label="资讯链接:" prop="url">
          <el-input placeholder="链接地址请携带前缀http://或https://" style="cursor:pointer" v-model="ruleForm.url" :disabled="!editFlag"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-if="editFlag" @click="submitForm('ruleForm')">确定</el-button>
          <el-button @click="$router.go(-1)">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import { industry } from 'api/index'
export default {
  name: 'station-inner',
  data () {
    return {
      editFlag: true,
      ruleForm: {},
      rules: {
        title: [
          { required: true, message: '请填写标题', trigger: 'blur' }
        ],
        url: [
          { required: true, message: '请填写资讯链接', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    if (this.$route.query && this.$route.query.objectId) {
      industry.selectOne(this.$route.query.objectId).then((res) => {
        if (res.data.resCode === '0000') {
          this.ruleForm = res.data.industryNews
          if (Object.is(this.$route.query.type, 'look')) {
            this.editFlag = false
          } else if (Object.is(this.$route.query.type, 'edit')) {
            this.editFlag = true
          }
        }
      })
    }
  },
  methods: {
    // 保存
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.ruleForm.type = 1
          industry.save(this.ruleForm).then((res) => {
            if (res.data.resCode === '0000') {
              this.$router.push({path: '/system/industry'})
            }
          })
        } else {
          return false
        }
      })
    },
    // 重置
    resetForm (formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>
<style lang="less">
#station-inner-page {
}
</style>
