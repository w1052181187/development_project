<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="risk-ratio-page">
    <div class="breadcrumb-mqh-tit" >
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system/comconfig' }"><span>计算配置管理</span></el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="maincont-box risk-warp">
      <el-form :inline="true" :model="ruleForm" class="demo-form-inline" style="text-align: left">
        <el-form-item label="专项资金风险控制比例(%):">
          <span style="display: inline-block;width: 200px;">{{ruleForm.description}}</span>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleFormBtn">修 改</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--弹窗start-->
    <div class="dialog-box">
      <el-dialog
        title="编辑"
        :visible.sync="dialogVisible"
        width="40%"
        center>
        <el-form :model="dialogRuleForm" :rules="rules" ref="dialogRuleForm" label-width="170px" class="demo-ruleForm">
          <el-form-item label="专项资金风险控制比例:" prop="description">
            <el-input v-model="dialogRuleForm.description" placeholder="请输入内容" clearable style="width: 280px"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
          <el-button type="primary" @click="saveForm('dialogRuleForm')">确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <!--弹窗end-->
  </div>
</template>
<script>
import {isvalidRiskRatio} from '../../../assets/js/validate'
import {constantMaintenance} from 'api/index'
// 实例
export default {
  data () {
    let validRiskRatio = (rule, value, callback) => {
      if (!value) {

      } else if (!isvalidRiskRatio(value)) {
        callback(new Error('请输入0-100的正数'))
      } else {
        callback()
      }
    }
    return {
      ruleForm: {},
      dialogRuleForm: {},
      rules: {
        description: [
          {required: true, message: '请输入0-100的正数', trigger: ['blur', 'change']},
          {validator: validRiskRatio, trigger: ['blur', 'change']}
        ]
      },
      dialogVisible: false
    }
  },
  computed: {
    nowData: function () {
      var obj = {}
      // this.templateData是父组件传递的对象
      obj = JSON.parse(JSON.stringify(this.ruleForm))
      return obj
    }
  },
  created () {
    this.getCurrentInfo()
  },
  methods: {
    getCurrentInfo () {
      constantMaintenance.getList({
        name: '专项资金风险控制比例',
        type: 1
      }).then((res) => {
        this.ruleForm = res.data.constantMaintenanceList[0]
      })
    },
    handleFormBtn () {
      this.dialogVisible = true
      this.dialogRuleForm = this.nowData
    },
    saveForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          constantMaintenance.save(this.dialogRuleForm).then(() => {
            this.getCurrentInfo()
            this.dialogVisible = false
          })
        } else {
          return false
        }
      })
    },
    cancel () {
      this.dialogVisible = false
      this.getCurrentInfo()
      this.dialogRuleForm = this.nowData
    }
  },
  // 实例创建完成
  mounted () {
  }
}
</script>
<style lang="less">
  #risk-ratio-page {
  }
</style>
