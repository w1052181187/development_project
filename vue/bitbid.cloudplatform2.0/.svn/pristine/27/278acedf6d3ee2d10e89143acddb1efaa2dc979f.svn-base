<template>
  <div>
    <el-dialog title="分配角色" width="600px" :before-close="cancel" :visible.sync="showVisible">
      <el-form :model="limitsJurisdictionForm" :rules="rules" ref="limitsJurisdictionForm" v-loading="loading">
        <el-form-item label="角色：" prop="roleIds" :label-width="formLabelWidth">
          <el-select v-model="limitsJurisdictionForm.roleIds" multiple placeholder="请选择角色" :multiple-limit="selectedRolesLimit">
            <el-option
              v-for="item in roles"
              :key="item.objectId"
              :label="item.name"
              :value="item.objectId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="查询范围：" prop="jurisdictionType" :label-width="formLabelWidth">
          <el-radio-group v-model="limitsJurisdictionForm.jurisdictionType">
            <el-radio :label="1">个人</el-radio>
            <el-radio :label="2">多个部门</el-radio>
            <el-radio :label="3">整个企业</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="查询的部门：" prop="jurisdictionDepartmentIds" :label-width="formLabelWidth" v-if="limitsJurisdictionForm.jurisdictionType === 2">
          <tree-checkbox :initData="limitsJurisdictionForm.jurisdictionDepartmentIds" @changeChecked="selectedJursidictionDepatments" :isShow="isShowJurisdictionDptTree"></tree-checkbox>
        </el-form-item>
        <el-form-item label="管辖范围：" prop="scopeOfControlType" :label-width="formLabelWidth">
          <el-radio-group v-model="limitsJurisdictionForm.scopeOfControlType">
            <el-radio :label="1">个人</el-radio>
            <el-radio :label="2">多个部门</el-radio>
            <el-radio :label="3">整个企业</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="管辖的部门：" prop="controlDepartmentIds" :label-width="formLabelWidth" v-if="limitsJurisdictionForm.scopeOfControlType === 2">
          <tree-checkbox :initData="limitsJurisdictionForm.controlDepartmentIds" @changeChecked="selectedControlDepatments" :isShow="isShowControlDptTree"></tree-checkbox>
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
import TreeCheckbox from '../department/tree/tree_checkbox'
import {role, limitJurisdiction} from '@/api/system'
export default {
  components: {
    TreeCheckbox
  },
  data () {
    let validJurisdiction = (rule, value, callback) => {
      if (this.limitsJurisdictionForm.jurisdictionType === 2) {
        if (this.limitsJurisdictionForm.jurisdictionDepartmentIds.length) {
          callback()
        } else {
          callback(new Error('查询的部门不能为空'))
        }
      } else {
        callback()
      }
    }
    let validScopeOfControl = (rule, value, callback) => {
      if (this.limitsJurisdictionForm.scopeOfControlType === 2) {
        if (this.limitsJurisdictionForm.controlDepartmentIds.length) {
          callback()
        } else {
          callback(new Error('管辖的部门不能为空'))
        }
      } else {
        callback()
      }
    }
    return {
      limitsJurisdictionForm: {
        jurisdictionType: 1,
        scopeOfControlType: 1,
        roleIds: []
      },
      selectedRolesLimit: 5,
      loading: false,
      formLabelWidth: '120px',
      roles: [],
      isShowControlDptTree: true,
      isShowJurisdictionDptTree: true,
      rules: {
        jurisdictionType: [
          { required: true, message: '管辖范围不能为空', trigger: 'change' }
        ],
        jurisdictionDepartmentIds: [
          {validator: validJurisdiction, trigger: 'change'}
        ],
        scopeOfControlType: [
          { required: true, message: '查询范围不能为空', trigger: 'change' }
        ],
        controlDepartmentIds: [
          {validator: validScopeOfControl, trigger: 'change'}
        ],
        roleIds: [
          { required: true, message: '角色不能为空', trigger: 'change' }
        ]
      }
    }
  },
  props: ['userId', 'showVisible'],
  watch: {
    userId (value) {
      if (value) {
        this.initData()
      }
    },
    showVisible (value) {
      if (!value) {
        this.$refs['limitsJurisdictionForm'].resetFields()
      }
    }
  },
  methods: {
    initRoles () {
      role.queryAll(this.$store.getters.authUser.enterpriseId).then(res => {
        this.roles = res.data.roleList
      })
    },
    initData () {
      limitJurisdiction.queryByUserId(this.userId).then(res => {
        this.limitsJurisdictionForm = res.data.data
        this.limitsJurisdictionForm.jurisdictionType = this.limitsJurisdictionForm.jurisdictionType || 1
        this.limitsJurisdictionForm.scopeOfControlType = this.limitsJurisdictionForm.scopeOfControlType || 1
        this.limitsJurisdictionForm.jurisdictionDepartmentIds = this.limitsJurisdictionForm.jurisdiction ? this.limitsJurisdictionForm.jurisdiction.split(',') : []
        this.limitsJurisdictionForm.controlDepartmentIds = this.limitsJurisdictionForm.scopeOfControl ? this.limitsJurisdictionForm.scopeOfControl.split(',') : []
      })
    },
    selectedControlDepatments (departmengIds) {
      this.limitsJurisdictionForm.controlDepartmentIds = departmengIds
    },
    selectedJursidictionDepatments (departmengIds) {
      this.limitsJurisdictionForm.jurisdictionDepartmentIds = departmengIds
    },
    save () {
      this.$refs['limitsJurisdictionForm'].validate((valid) => {
        if (valid) {
          this.loading = true
          this.limitsJurisdictionForm.userId = this.userId
          if (this.limitsJurisdictionForm.jurisdictionType !== 2) {
            this.limitsJurisdictionForm.jurisdiction = null
          } else {
            this.limitsJurisdictionForm.jurisdiction = this.limitsJurisdictionForm.jurisdictionDepartmentIds.join(',')
          }
          if (this.limitsJurisdictionForm.scopeOfControlType !== 2) {
            this.limitsJurisdictionForm.scopeOfControl = null
          } else {
            this.limitsJurisdictionForm.scopeOfControl = this.limitsJurisdictionForm.controlDepartmentIds.join(',')
          }
          limitJurisdiction.update(this.limitsJurisdictionForm).then(res => {
            this.loading = false
            if (res.data.resCode === '0000') {
              this.$emit('submitSuccess')
            }
          }).catch(() => {
            this.loading = false
          })
        } else {
          return false
        }
      })
    },
    cancel () {
      this.$emit('showAssignRolesDialog')
    }
  },
  mounted () {
    this.initRoles()
  }
}
</script>
<style>
</style>
