<template>
  <div>
    <el-dialog :title="titleName" :before-close="cancel" :visible.sync="showVisible" width="600px">
      <el-form :model="submitForm" :rules="rules" ref="submitForm" :label-width="formLabelWidth" :validate-on-rule-change="true" v-loading="loading">
        <el-form-item label="评估项：" prop="name" >
          <el-input v-model="submitForm.name" clearable placeholder="请输入评估项">
            <i class="el-icon-edit el-input__icon" slot="suffix"></i>
          </el-input>
        </el-form-item>
        <el-form-item label="评估项分组：" prop="parent.name">
          <el-row>
            <el-col :span="16">
              <el-input v-model="submitForm.parent.name" clearable placeholder="请输入评估项分组">
                <i class="el-icon-edit el-input__icon" slot="suffix"></i>
              </el-input>
            </el-col>
            <el-col :span="8">
              <span class="group" @click='showGroup'>选择已有分组</span>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="">
          <el-row v-if="isShowGroup">
            <div class="group">已有分组：</div>
            <el-select v-model="selectedGroupName">
              <el-option v-loading="groupLoading"
                         v-for="item in itemGroup"
                         :key="item.name"
                         :label="item.name"
                         :value="item.name">
              </el-option>
            </el-select>
          </el-row>
        </el-form-item>
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="save">保存</el-button>
          <el-button class="cancal" @click="cancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import {templateItem} from '@/api/customer'
import {trimStr} from '@/assets/js/common'
export default {
  data () {
    // 验证名称
    let validName = (rule, value, callback) => {
      if (value && trimStr(value)) {
        const name = trimStr(value)
        if (name.length < 1 || name.length > 200) {
          callback(new Error('长度在1~200个字符'))
        } else {
          let query = {
            enterpriseId: this.$store.getters.authUser.enterpriseId,
            name: name,
            excludeParentId: 0,
            isDelete: 0
          }
          if (this.editObjectId) {
            query.excludeId = this.editObjectId
          }
          templateItem.isNoRepeated(query).then(res => {
            if (!res.data.data) {
              callback(new Error('评估项重复，请重新填写'))
            } else {
              callback()
            }
          })
        }
      } else {
        callback(new Error('评估项不能为空'))
      }
    }
    return {
      submitForm: {
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        parent: {
          enterpriseId: this.$store.getters.authUser.enterpriseId
        }
      },
      selectedGroupName: '',
      titleName: '新增评估项',
      loading: false,
      // 表单label长度
      formLabelWidth: '120px',
      // 分组集合
      itemGroup: [],
      // 是否显示已存在分组
      isShowGroup: false,
      // 分组查询loading
      groupLoading: false,
      // 表单验证
      rules: {
        name: [
          { required: true, message: '评估项不能为空', trigger: 'blur' },
          {validator: validName, trigger: 'blur'}
        ],
        'parent.name': [
          { required: true, message: '评估项分组不能为空', trigger: ['blur', 'change'] },
          { min: 1, max: 200, message: '长度在1~200个字符', trigger: 'blur' }
        ]
      }
    }
  },
  props: ['isEdit', 'editObjectId', 'showVisible'],
  watch: {
    showVisible (value) {
      this.isShowGroup = false
      this.selectedGroupName = ''
      if (value) {
        if (this.isEdit) {
          this.titleName = '修改评估项'
          this.queryOne()
        }
      } else {
        this.titleName = '新增评估项'
        this.$refs['submitForm'].resetFields()
      }
    },
    selectedGroupName (value) {
      if (value) {
        this.submitForm.parent.name = value
      }
    }
  },
  methods: {
    queryOne () {
      this.loading = true
      templateItem.queryOne(this.editObjectId).then(res => {
        this.loading = false
        this.submitForm = res.data.data
      })
    },
    // 保存
    save () {
      this.$refs['submitForm'].validate((valid) => {
        if (valid) {
          this.loading = true
          templateItem.update(this.submitForm).then(res => {
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
    },
    // 展示/关闭已有分组
    showGroup () {
      this.isShowGroup = true
      this.groupLoading = true
      templateItem.queryAllGroup(this.$store.getters.authUser.enterpriseId).then(res => {
        this.itemGroup = res.data.data
        this.groupLoading = false
      })
    },
    // 取消
    cancel () {
      this.submitForm.objectId = null
      this.$emit('showEditDialog', false)
    }
  }
}
</script>
<style scoped>
  .group{
    color: #108cee;
    font-size: 12px;
    margin-left: 12px;
    cursor: pointer;
  }
</style>
