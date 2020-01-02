<template>
  <div class="station" id="station-out-page">
    <div class="breadcrumb-mqh-tit" >
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system/industry' }"><span>行业资讯管理</span></el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system/industry/stationout' }"><span>站外资讯</span></el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <div class="maincont-box">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm station-form">
        <el-form-item label="标题:" prop="title">
          <el-input v-model="ruleForm.title" :disabled="!editFlag"></el-input>
        </el-form-item>
        <el-form-item label="内容:" ref="content" prop="content">
          <ueditor ref="ueditor" :content="content" class="ueditor" :disabled="!editFlag">
          </ueditor>
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
// 引入编辑器
import ueditor from '@/components/ueditor/ueditor-all.vue'
import { industry } from 'api/index'
export default {
  name: 'station-out',
  data () {
    return {
      editFlag: true,
      content: '',
      ruleForm: {},
      rules: {
        title: [
          { required: true, message: '请填写标题', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '内容不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    if (this.$route.query && this.$route.query.objectId) {
      industry.selectOne(this.$route.query.objectId).then((res) => {
        if (res.data.resCode === '0000') {
          this.ruleForm = res.data.industryNews
          this.content = res.data.industryNews.content
          this.$refs.ueditor.setContent(this.content)
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
      this.validContent()
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.ruleForm.type = 2
          this.ruleForm.content = this.$refs.ueditor.getContent()
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
    /** 校验公示内容 */
    validContent () {
      let content = this.$refs.ueditor.getContent()
      if (content) {
        this.$set(this.ruleForm, 'content', content)
      } else {
        this.$set(this.ruleForm, 'content', '')
      }
      if (this.ruleForm.content) {
        this.$refs['content'].clearValidate()
      }
    },
    // 重置
    resetForm (formName) {
      this.$refs[formName].resetFields()
    }
  },
  /** 组件注册 */
  components: {
    // 编辑器
    ueditor
  }
}
</script>
<style lang="less">
#station-out-page {
  .station-form {
    .el-form-item__content {
      line-height: 0;
    }
    .ueditor-wrap {
      padding: 0;
    }
  }
}
</style>
