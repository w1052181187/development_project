<template>
  <div>
    <el-dialog title="添加角色" width="600px" :before-close="cancel" :visible.sync="showVisible">
      <el-form :model="roleForm" :rules="rules" ref="roleForm" v-loading="loading">
        <el-form-item label="角色名称：" prop="name" :label-width="formLabelWidth">
          <el-input v-model.trim="roleForm.name" placeholder="请输入角色名称" auto-complete="off" clearable>
            <i class="el-icon-edit el-input__icon" slot="suffix"></i>
          </el-input>
        </el-form-item>
        <el-form-item label="角色描述：" prop="description" :label-width="formLabelWidth">
          <el-input
            placeholder="请输入角色描述"
            type="textarea"
            :autosize="{ minRows: 3, maxRows: 5}"
            v-model.trim="roleForm.description">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer operationbtn">
        <el-button type="info" @click="cancel()">取 消</el-button>
        <el-button type="primary" @click="save()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {role} from '@/api/system'
import {trimStr} from '@/assets/js/common'
export default {
  data () {
    // 验证账号重复
    let validName = (rule, value, callback) => {
      if (value && trimStr(value)) {
        const name = trimStr(value)
        if (name.length === 0 || name.length > 20) {
          callback(new Error('长度在1~20个字符'))
        } else {
          role.isNoRepeated({
            enterpriseId: this.$store.getters.authUser.enterpriseId,
            excludeId: this.editObjectId,
            name: value,
            isDelete: 0
          }).then(res => {
            if (!res.data.data) {
              callback(new Error('角色名称重复，请重新填写'))
            } else {
              callback()
            }
          })
        }
      }
    }
    return {
      // 增加角色数据
      roleForm: {
        enterpriseId: this.$store.getters.authUser.enterpriseId
      },
      formLabelWidth: '120px',
      loading: false,
      // 角色提交验证
      rules: {
        name: [
          { required: true, message: '角色名称不能为空', trigger: 'blur' },
          { validator: validName, trigger: 'blur' }
        ],
        description: [
          { required: true, message: '角色描述不能为空', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在1~200个字符', trigger: 'blur' }
        ]
      }
    }
  },
  props: ['isEdit', 'editObjectId', 'showVisible'],
  watch: {
    showVisible (value) {
      if (value) {
        if (this.isEdit) {
          this.titleName = '编辑角色'
          this.queryOne()
        }
      } else {
        this.titleName = '新增角色'
        this.$refs['roleForm'].resetFields()
      }
    }
  },
  methods: {
    queryOne () {
      this.loading = true
      role.queryOne(this.editObjectId).then(res => {
        this.loading = false
        this.roleForm = res.data.role
      })
    },
    cancel () {
      this.roleForm.objectId = null
      this.$emit('showEditDialog', false)
    },
    save () {
      this.$refs['roleForm'].validate((valid) => {
        if (valid) {
          this.loading = true
          role.update(this.roleForm).then(res => {
            this.loading = false
            if (res.data.resCode === '0000') {
              this.cancel()
              this.$emit('saveSuccess')
            }
          }).catch(() => {
            this.loading = false
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>
<style>
</style>
