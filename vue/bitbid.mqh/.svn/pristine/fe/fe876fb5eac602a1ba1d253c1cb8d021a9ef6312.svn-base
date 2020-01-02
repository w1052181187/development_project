<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="agent-check-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>考评系统</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/evaluate/agentcheck' }">代理机构考评审核</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/evaluate/agentcheck/check' }">审核</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <!--项目基本信息start-->
    <div class="pro-info">
      <div class="pro-title">
        <span>项目基本信息</span>
        <el-button type="primary" size="mini" class="pro-tit-btn" @click="handleBtnFun('files')">档案查询</el-button>
        <el-button type="primary" size="mini" class="pro-tit-btn" @click="handleBtnFun('bid')">招标备案</el-button>
      </div>
      <div class="pro-form">
        <el-form :model="projectInfoForm" label-width="100px">
          <el-row :gutter="20">
            <el-col :span="7">
              <el-form-item label="项目名称:">
                <span>{{projectInfoForm.tenderProjectName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="项目编号:">
                <span>{{projectInfoForm.codeUser}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="项目单位:">
                <span>{{projectInfoForm.projectEnterpriseName}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="7">
              <el-form-item label="代理机构:">
                <span>{{projectInfoForm.agencyEnterpriseName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="项目类型:">
                <span>{{projectInfoForm.tenderProjectType | filterType}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="采购方式:">
                <span>{{projectInfoForm.biddingType | filterBiddingType}}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </div>
    <!--项目基本信息end-->
    <!--工作考评start-->
    <div class="work-eval">
      <div class="pro-title">
        <span>工作考评</span>
      </div>
      <div class="eval-cont">
        <el-form :model="workEvalForm" label-width="140px" class="demo-dynamic" ref="workEvalForm">
          <el-form-item :label="domain.labelName+':'" v-for="(domain, index) in workEvalForm.workEvalList" :key="index">
            <el-radio-group v-model="domain.evaluateResult">
              <el-radio-button :label="1" class="radio-first" v-if="domain.evaluateResult==1">好</el-radio-button>
              <el-radio-button :label="2" class="radio-second" v-if="domain.evaluateResult==2">一般</el-radio-button>
              <el-radio-button :label="3" class="radio-third" v-if="domain.evaluateResult==3">差</el-radio-button>
            </el-radio-group>
            <span class="eval-span" v-if="domain.evaluateResult==3">{{domain.evaluateRemark}}</span>
            <el-select v-model="domain.isTrue" placeholder="请选择" v-if="domain.evaluateResult==3" @change="changeIsTrue">
              <el-option
                v-for="item in isTrueList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="备注:" v-if="!auditRemarkFlag" prop="auditRemark" :rules="[
                  {required: false, message: '请输入备注信息！', trigger: ['blur', 'change']}
                ]">
            <el-input type="textarea" v-model="workEvalForm.auditRemark" :rows="3" style="width: 50%;"></el-input>
          </el-form-item>
          <el-form-item label="备注:" v-if="auditRemarkFlag" prop="auditRemark" :rules="[
                  {required: true, message: '请输入备注信息！', trigger: ['blur', 'change']}
                ]">
            <el-input type="textarea" v-model="workEvalForm.auditRemark" :rows="3" style="width: 50%;"></el-input>
          </el-form-item>
          <el-form-item class="last-form-item">
            <el-button type="success" @click="onSubmitBtn('workEvalForm')">提 交</el-button>
            <el-button type="primary" @click="onSaveBtn('workEvalForm')">保 存</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <!--工作考评end-->
  </div>
</template>
<script>
import {tenderproject, evaluateLabel, projectEvaluate} from 'api/index'
// 实例
export default {
  data () {
    return {
      projectInfoForm: {}, // 项目基本信息-表单
      selectValue: {}, // 属实与否
      workEvalForm: {
        workEvalList: [],
        auditRemark: ''
      }, // 工作考评-表单
      projectEvaluateInfo: {},
      isTrueList: [
        {
          label: '属实',
          value: 1
        },
        {
          label: '不属实',
          value: 0
        }
      ],
      auditRemarkFlag: false // 判断是否需要输入备注信息
    }
  },
  created () {
    this.getProjectInfo()
    this.getEvaluateLabelList()
    this.getProjectEvaluateInfo()
  },
  filters: {
    filterType (type) {
      if (type === 1) {
        return '工程'
      } else if (type === 2) {
        return '货物'
      } else if (type === 3) {
        return '服务'
      }
    },
    filterBiddingType (type) {
      if (type === 1) {
        return '公开招标'
      } else if (type === 2) {
        return '邀请招标'
      } else if (type === 3) {
        return '谈判采购'
      } else if (type === 4) {
        return '询比采购'
      } else if (type === 5) {
        return '竞价采购'
      } else if (type === 6) {
        return '直接采购'
      } else if (type === 7) {
        return '框架协议'
      }
    }
  },
  methods: {
    /** 获取当前招标项目信息 */
    getProjectInfo () {
      tenderproject.getOneInEvaluate(this.$route.query.code).then((res) => {
        if (res.data.tenderProject) {
          this.projectInfoForm = res.data.tenderProject
        }
      })
    },
    /** 获取对应的标签信息 */
    getEvaluateLabelList () {
      evaluateLabel.getListByRelatedCode(this.$route.query.code, 1).then((res) => {
        if (res.data.evaluateInfos) {
          let evaluateInfos = res.data.evaluateInfos
          evaluateInfos.forEach(item => {
            if (item.evaluateResult === 3) {
              item.showFlag = true
            } else {
              item.showFlag = false
            }
            if (item.isTrue === 0) {
              this.auditRemarkFlag = true
            }
            this.workEvalForm.workEvalList.push(item)
          })
        }
      })
    },
    /** 取出评价信息 */
    /* getEvaluateInfo (evaluateInfos, evaluateLabel) {
      for (let i = 0; i < evaluateInfos.length; i++) {
        if (evaluateLabel.code === evaluateInfos[i].relatedLabelCode) {
          evaluateLabel.evaluateResult = evaluateInfos[i].evaluateResult
          evaluateLabel.evaluateRemark = evaluateInfos[i].evaluateRemark
          evaluateLabel.isTrue = evaluateInfos[i].isTrue
          if (evaluateLabel.evaluateResult === 3) {
            evaluateLabel.showFlag = true
          } else {
            evaluateLabel.showFlag = false
          }
          if (evaluateInfos[i].isTrue === 0) {
            this.auditRemarkFlag = true
          }
          evaluateLabel.evaluateInfoId = evaluateInfos[i].objectId // 用于判断当前操作是更新还是新增
          break
        }
      }
      return evaluateLabel
    }, */
    /** 获取项目评价信息 */
    getProjectEvaluateInfo () {
      projectEvaluate.getOne(this.$route.query.code, 1).then((res) => {
        this.projectEvaluateInfo = res.data.projectEvaluate
        if (res.data.projectEvaluate.auditRemark) {
          this.workEvalForm.auditRemark = res.data.projectEvaluate.auditRemark
        } else {
          this.workEvalForm.auditRemark = ''
        }
      })
    },
    /** [档案管理] | [招标备案] 查询 */
    handleBtnFun (type) {
      if (Object.is(type, 'files')) {
        this.$router.push({path: '/admin/files/filemanage/editfile', query: {type: false, operation: 'look', bidType: this.projectInfoForm.biddingType, code: this.projectInfoForm.archivesCode}})
      } else if (Object.is(type, 'bid')) {
        this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: this.projectInfoForm.code}})
      }
    },
    /** 提交 */
    onSubmitBtn (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          let projectEvaluateInfo = {
            objectId: (this.projectEvaluateInfo && this.projectEvaluateInfo.objectId) ? this.projectEvaluateInfo.objectId : '',
            status: 1,
            auditStatus: 1,
            auditRemark: this.workEvalForm.auditRemark,
            projectEvaluateType: 1,
            evaluateInfoList: []
          }
          if (this.workEvalForm.workEvalList.length > 0) {
            projectEvaluateInfo.evaluateInfoList = this.workEvalForm.workEvalList
          }
          projectEvaluate.save(projectEvaluateInfo).then((res) => {
            if (res.data.resCode === '0000') {
              this.$router.push({path: '/admin/evaluate/agentcheck'})
            }
          })
        } else {
          return false
        }
      })
    },
    /** 保存 */
    onSaveBtn (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          let projectEvaluateInfo = {
            objectId: (this.projectEvaluateInfo && this.projectEvaluateInfo.objectId) ? this.projectEvaluateInfo.objectId : '',
            status: 1,
            auditStatus: 0,
            auditRemark: this.workEvalForm.auditRemark,
            projectEvaluateType: 1,
            evaluateInfoList: []
          }
          if (this.workEvalForm.workEvalList.length > 0) {
            projectEvaluateInfo.evaluateInfoList = this.workEvalForm.workEvalList
          }
          projectEvaluate.save(projectEvaluateInfo).then((res) => {
            if (res.data.resCode === '0000') {
              this.$router.push({path: '/admin/evaluate/agentcheck'})
            }
          })
        } else {
          return false
        }
      })
    },
    /** 判断备注是否为必填项 */
    changeIsTrue () {
      if (this.workEvalForm.workEvalList.length > 0) {
        for (let i = 0; i < this.workEvalForm.workEvalList.length; i++) {
          if (this.workEvalForm.workEvalList[i].isTrue === 0) {
            this.auditRemarkFlag = true
            break
          } else {
            if (i === this.workEvalForm.workEvalList.length - 1) {
              this.auditRemarkFlag = false
            }
          }
        }
      }
      this.$refs['workEvalForm'].clearValidate()
    }
  },
  computed: {},
  // 实例创建完成
  mounted () {
  }
}
</script>
<style lang="less">
  #agent-check-page {
    /** 项目基本信息-工作考评css */
    .pro-info, .work-eval {
      margin: 0 20px;
      .pro-title {
        text-align: left;
        padding-left: 20px;
        font-weight: 800;
        height: 40px;
        line-height: 40px;
        background: #F7F6FB;
      }
      .pro-tit-btn {
        float: right;
        margin-top: 5px;
        margin-right: 20px;
      }
      .pro-form {
        padding-top: 15px;
        background-color: #fff;
        .el-form-item__content {
          text-align: left;
        }
      }
    }
    /** 工作考评css */
    .work-eval {
      margin-top: 20px;
      .eval-cont {
        margin-bottom: 130px;
        padding: 15px 0;
        background-color: #fff;
        .el-form-item__content {
          text-align: left;
        }
        .last-form-item {
          .el-form-item__content {
            text-align: center;
          }
        }
        .eval-span {
          max-width: 500px;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
          display: inline-block;
          margin-right: 30px;
          vertical-align: middle;
        }
        .radio-first, .radio-second, .radio-third {
          margin-right: 40px;
          .el-radio-button__inner {
            width: 65px;
            padding: 8px;
            border-left: none;
            border-radius: 25px;
            border: 1px solid #dcdfe6;
            box-shadow: none;
          }
        }
        .radio-first.is-active
        .el-radio-button__inner {
          background-color: #409eff;
        }
        .radio-second.is-active
        .el-radio-button__inner {
          background-color: #67c23a;
        }
        .radio-third.is-active
        .el-radio-button__inner {
          background-color: #e6a23c;
        }
      }
    }
  }
</style>
