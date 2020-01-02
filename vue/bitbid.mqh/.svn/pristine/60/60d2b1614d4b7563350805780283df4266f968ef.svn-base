<template>
  <!-- 后台工作台-通知 -->
  <div class="index-layout" id="agency-page">
    <div class="workdesk-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">当前位置:首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/todo' }">我的待办</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="agency-cont">
      <div class="agency-title">
        <span class="tit-span">我的待办</span>
        <el-button type="text" @click="$router.go(-1)" class="workdesk-backbtn">
          返回
        </el-button>
      </div>
      <div class="agency-table">
        <el-table
          :data="tableData"
          :row-class-name="tableRowClassName"
          height="300"
          border
          style="width: 100%">
          <el-table-column
            prop="projectName"
            label="项目名称"
            align="center">
          </el-table-column>
          <el-table-column
            prop="planQuota"
            label="计划金额"
            align="center">
          </el-table-column>
          <el-table-column
            prop="enterpriseName"
            label="项目单位"
            align="center">
          </el-table-column>
          <el-table-column
            prop="contacts"
            label="联系人"
            align="center">
          </el-table-column>
          <el-table-column
            prop="phone"
            label="联系方式"
            align="center">
          </el-table-column>
        </el-table>
      </div>
      <div class="agency-form">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="委托时间:" prop="entrustDate">
            <span>{{ruleForm.entrustDate |filterDate}}</span>
          </el-form-item>
          <el-form-item label="是否接受:" prop="isAccept">
            <el-select v-model="ruleForm.isAccept" placeholder="请选择">
              <el-option
                v-for="item in isAcceptList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="原因:" prop="reason" v-if="ruleForm.isAccept === 0" >
            <el-input type="textarea" v-model="ruleForm.reason" :rows="4" style="width: 50%;"></el-input>
          </el-form-item>
          <el-form-item class="form-btn">
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
            <el-button @click="goBack">取消</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script>
import {formatDate} from 'common/js/date.js'
import {drawresult} from 'api/index'
// 实例
export default {
  data () {
    return {
      tableData: [],
      ruleForm: {},
      rules: {
        isAccept: [
          { required: true, message: '请选择是否接受', trigger: 'change' }],
        reason: [
          {required: true, message: '请输入原因', trigger: 'change'}]
      },
      isAcceptList: [
        {
          label: '拒绝',
          value: 0
        },
        {
          label: '接受',
          value: 1
        }
      ],
      code: this.$route.query.code,
      isRank: 0
    }
  },
  created () {
  },
  mounted () {
    this.getCode()
  },
  filters: {
    /** 格式化时间 */
    filterDate (value) {
      if (value) {
        let date = new Date(value)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return null
      }
    }
  },
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 列表数据展示 */
    getCode () {
      drawresult.getBiddingByCode(this.code).then((res) => {
        if (res) {
          this.ruleForm = res.data.biddingEntrust
          let relatedResultCode = res.data.biddingEntrust.relatedResultCode
          this.isRank = res.data.biddingEntrust.rank
          this.getSelectApplylist(relatedResultCode)
        }
      })
    },
    getSelectApplylist (code) {
      drawresult.getByCode(code).then((res) => {
        if (res) {
          this.tableData = res.data.drawLotsResult.projectGroup.selectApplyList
        }
      })
    },
    /** 提交 */
    submitForm (from) {
      this.$refs[from].validate((valid) => {
        if (valid) {
          if (this.isRank === 3 && this.ruleForm.isAccept === 0) {
            this.$message({
              title: '提示',
              message: '排名第三的代理机构不能拒绝委托！',
              type: 'warning'
            })
          } else {
            drawresult.saveBiddingEntrust(this.ruleForm).then((res) => {
              this.$router.push({path: '/admin/todo'})
            })
          }
        }
      })
    },
    goBack () {
      this.$router.push({path: '/admin/todo'})
    }
  }
}
</script>
<style lang="less">
  #agency-page {
    position: relative;
    top: 100px;
    .workdesk-mqh-tit {
      width: 1200px;
      height: 40px;
      line-height: 40px;
      margin: 0 auto;
    }
    .agency-cont {
      width: 1200px;
      margin: 0 auto;
      margin-bottom: 80px;
      padding-bottom: 15px;
      background-color: #fff;
      .agency-title {
        height: 40px;
        background-color: #F7F6FB;
        .tit-span {
          display: inline-block;
          margin-top: 10px;
          padding-left: 15px;
          font-size: 16px;
          font-weight: 800;
          float: left;
        }
        .workdesk-backbtn {
          float: right;
          padding-right: 15px;
        }
      }
      .agency-table {
        padding: 15px;
      }
      .agency-form {
        .el-form-item__content {
          text-align: left;
        }
      }
      .form-btn {
        .el-form-item__content {
          text-align: center;
        }
      }
    }
  }
</style>
