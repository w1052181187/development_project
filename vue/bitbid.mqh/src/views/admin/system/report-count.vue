
<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="report-count-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system/report' }"><span>报表统计开始日期设置</span></el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="maincont-box">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="140px" class="demo-ruleForm report-form">
        <el-form-item label="报表统计开始日期:" prop= "description">&nbsp;每月&nbsp;
          <el-select v-model="ruleForm.description" placeholder="请选择" style="width:130px;">
            <el-option
              v-for="item in reportTimeList"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>&nbsp;日
        </el-form-item>
        <p class="note-p">例如：选择每月25日，表示当月25日到次月25日为一个统计周期。</p>
        <el-form-item>
          <el-button type="primary" @click="onSubmit('ruleForm')">保 存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import {constantMaintenance} from 'api/index'
// 实例
export default {
  data () {
    return {
      ruleForm: {
        description: ''
      },
      rules: {
        description: [
          { required: true, message: '请选择报表统计开始日期', trigger: ['blur', 'change'] }
        ]
      },
      reportTimeList: this.getReportTimeList()
    }
  },
  created () {
    this.getCurrentInfo()
  },
  methods: {
    getCurrentInfo () {
      constantMaintenance.getList({
        type: 3
      }).then((res) => {
        if (res.data.constantMaintenanceList[0]) {
          this.ruleForm = res.data.constantMaintenanceList[0]
        }
      })
    },
    /** 获取下拉选项的数据 */
    getReportTimeList () {
      let arr = []
      for (let i = 1; i < 32; i++) {
        let obj = {
          label: i,
          value: i
        }
        arr.push(obj)
      }
      return arr
    },
    /** 保存 */
    onSubmit (formName) {
      this.$refs[formName].validate((vaild) => {
        if (vaild) {
          constantMaintenance.save(this.ruleForm).then(() => {
            this.getCurrentInfo()
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>
<style lang="less">
#report-count-page {
  .report-form .el-form-item__content {
    text-align: left;
  }
  .report-form {
    .note-p {
      text-align: left;
      margin-left: 8px;
      color: #999;
      font-size: 12px;
    }
  }
}
</style>
