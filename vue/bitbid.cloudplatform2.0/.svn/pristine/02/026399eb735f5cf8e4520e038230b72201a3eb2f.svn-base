<template>
  <el-dialog
    title="增加职业资格"
    :visible.sync="showVisible"
    width="30%"
    center>
    <el-form :model="submitForm" :rules="rules" ref="submitForm" label-width="140px" class="demo-submitForm">
      <el-form-item label="职业资格名称:" :rules="rules.simpleRequiredText" prop="name">
        <el-input v-model="submitForm.name" clearable placeholder="请输入职业资格名称">
          <i class="el-icon-edit el-input__icon" slot="suffix"></i>
        </el-input>
      </el-form-item>
      <el-form-item label="职业资格等级:" :rules="rules.simpleRequiredText" prop="level">
        <el-input v-model="submitForm.level" clearable placeholder="请输入职业资格等级">
          <i class="el-icon-edit el-input__icon" slot="suffix"></i>
        </el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
          <el-button type="primary" @click="submit" v-loading="submitLoading">确 定</el-button>
        </span>
  </el-dialog>
</template>
<script>
export default {
  data () {
    return {
      submitLoading: false,
      submitForm: {},
      rules: {
        simpleRequiredText: [
          { required: true, message: '输入不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在1~20个字符', trigger: 'blur' }
        ]
      }
    }
  },
  props: ['showVisible'],
  watch: {
    showVisible (value) {
      if (!value) {
        this.$refs['submitForm'].resetFields()
        this.submitForm = {}
      }
    }
  },
  methods: {
    submit () {
      this.$refs['submitForm'].validate((valid) => {
        if (valid) {
          this.$emit('saveSuccess', Object.assign({}, this.submitForm))
          this.cancel()
        }
      })
    },
    // 关闭
    cancel () {
      this.$emit('showDialog')
    }
  }
}
</script>
<style scoped>
</style>
