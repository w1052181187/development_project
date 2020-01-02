<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="eval-agent-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>考评系统</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/evaluate/agenteval' }" v-if="this.$store.getters.authUser.enterpriseType !== 4">代理机构考评</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/evaluate/agenteval' }" v-if="this.$store.getters.authUser.enterpriseType === 4">项目单位考评</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/evaluate/agenteval/evalpage' }">考评</el-breadcrumb-item>
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
          <el-form-item
            :label="domain.labelName+':'"
            v-for="(domain, index) in workEvalForm.workEvalList"
            :key="index"
            :prop="'workEvalList.' + index + '.evaluateResult'"
            :rules="workEvalForm.rules.evaluateResult"
            v-if="domain.isDelete === 0">
            <el-radio-group v-model="domain.evaluateResult" @change="changRadioBtn(domain)">
              <el-radio-button :label="1" class="radio-first">好</el-radio-button>
              <el-radio-button :label="2" class="radio-second">一般</el-radio-button>
              <el-radio-button :label="3" class="radio-third">差</el-radio-button>
            </el-radio-group>
            <el-input v-model="domain.evaluateRemark" style="width: 400px" v-if="domain.showFlag"></el-input>
            <el-button type="danger" @click.prevent="removeDomain(domain)" v-if="domain.addFlag">删除</el-button>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-plus" @click="addOtherEval">新增其他评价</el-button>
            <el-button type="success" @click="submitForm('workEvalForm')">提 交</el-button>
            <el-button type="primary" @click="saveForm('workEvalForm')">保 存</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <!--工作考评end-->
    <div class="dialog-box">
      <el-dialog
        title="新增"
        :visible.sync="dialogVisible"
        width="30%"
        center>
        <el-form ref="dialogAddNameForm" :model="dialogAddNameForm"  :rules="dialogAddNameForm.rules" class="demo-form-inline">
          <el-form-item label="评价类型名称:" prop="labelName">
            <el-input v-model="dialogAddNameForm.labelName" placeholder="评价类型名称"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
          <el-button type="primary" @click="confirmDialogBtn('dialogAddNameForm')">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import {tenderproject, evaluateLabel, projectEvaluate} from 'api/index'
// 实例
export default {
  data () {
    return {
      projectInfoForm: {}, // 项目基本信息-表单
      workEvalForm: {
        workEvalList: [],
        rules: {
          evaluateResult: [
            {required: true, message: '请进行评价！', trigger: ['blur', 'change']}
          ]
        }
      }, // 工作考评-表单
      dialogVisible: false,
      dialogAddNameForm: {
        labelName: '',
        rules: {
          labelName: [
            {required: true, message: '请输入标签名称！', trigger: ['blur', 'change']}
          ]
        }
      },
      AddNameFormRules: {},
      projectEvaluateInfo: {},
      enterpriseType: this.$store.getters.authUser.enterpriseType
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
        this.projectInfoForm = res.data.tenderProject
      })
    },
    /** 获取对应的标签信息 */
    getEvaluateLabelList () {
      this.workEvalForm.workEvalList = []
      evaluateLabel.getListByRelatedCode(this.$route.query.code, Number(this.enterpriseType === 4 ? 2 : 1)).then((res) => {
        let evaluateInfos = res.data.evaluateInfos
        let evaluateLabels = res.data.evaluateLabels
        if (evaluateInfos.length === 0 && evaluateLabels.length > 0) {
          evaluateLabels.forEach(item => {
            if (item.labelName === '造价服务') {
              if (this.projectInfoForm.tenderProjectType === 1) {
                item.objectId = ''
                item.relatedLabelCode = item.code
                item.evaluateResult = ''
                item.evaluateRemark = ''
                item.showFlag = false
                item.isDelete = 0
                this.workEvalForm.workEvalList.push(item)
              }
            } else {
              item.objectId = ''
              item.relatedLabelCode = item.code
              item.evaluateResult = ''
              item.evaluateRemark = ''
              item.showFlag = false
              item.isDelete = 0
              this.workEvalForm.workEvalList.push(item)
            }
          })
        } else if (evaluateInfos.length > 0 && evaluateLabels.length > 0) {
          evaluateInfos.forEach(item => {
            item.isDelete = 0
            if (item.relatedLabelCode === '') {
              item.addFlag = '1'
            }
            if (item.evaluateResult === 3) {
              item.showFlag = true
            } else {
              item.showFlag = false
            }
            this.workEvalForm.workEvalList.push(item)
          })
        }
      })
    },
    /** 取出评价信息 */
    getEvaluateInfo (evaluateInfo, evaluateLabels) {
      for (let i = 0; i < evaluateLabels.length; i++) {
        if (evaluateInfo.relatedLabelCode === evaluateLabels[i].code) {
          if (evaluateInfo.evaluateResult === 3) {
            evaluateInfo.showFlag = true
          } else {
            evaluateInfo.showFlag = false
          }
          evaluateInfo.evaluateInfoId = evaluateInfo.objectId // 用于判断当前操作是更新还是新增
          break
        }
      }
      return evaluateLabel
    },
    /** 获取项目评价信息 */
    getProjectEvaluateInfo () {
      projectEvaluate.getOne(this.$route.query.code, Number(this.enterpriseType === 4 ? 2 : 1)).then((res) => {
        this.projectEvaluateInfo = res.data.projectEvaluate
      })
    },
    /** [档案管理]|[招标备案]-操作按钮 */
    handleBtnFun (type) {
      if (Object.is(type, 'bid')) { // 招标备案
        this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: this.projectInfoForm.code}})
      } else if (Object.is(type, 'files')) { // 档案管理
        this.$router.push({path: '/admin/files/filemanage/editfile', query: {type: false, operation: 'look', bidType: this.projectInfoForm.biddingType, code: this.projectInfoForm.archivesCode}})
      }
    },
    /** 添加其他考评 */
    addOtherEval () {
      this.dialogVisible = true
    },
    cancel () {
      this.dialogVisible = false
      this.$refs['dialogAddNameForm'].resetFields()
      this.dialogAddNameForm.labelName = ''
    },
    confirmDialogBtn (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          if (!this.checkIsHasSameName(this.dialogAddNameForm.labelName)) {
            this.dialogVisible = false
            let obj = {
              objectId: null,
              labelName: this.dialogAddNameForm.labelName,
              relatedLabelCode: '',
              evaluateResult: '',
              evaluateRemark: '',
              labelType: 2,
              targetEnterpriseId: this.projectInfoForm.agencyEnterpriseId,
              addFlag: '1',
              isDelete: 0
            }
            this.workEvalForm.workEvalList.push(obj)
            this.$refs['dialogAddNameForm'].resetFields()
            this.dialogAddNameForm.labelName = ''
          } else {
            this.$message.error('标签名重复！')
            this.$refs['dialogAddNameForm'].resetFields()
            this.dialogAddNameForm.labelName = ''
            return false
          }
        } else {
          return false
        }
      })
    },
    /** 判断是否有相同标签的存在 */
    checkIsHasSameName (labelName) {
      let flag = false
      if (this.workEvalForm.workEvalList.length > 0) {
        for (let i = 0; i < this.workEvalForm.workEvalList.length; i++) {
          if (this.workEvalForm.workEvalList[i].labelName === labelName && this.workEvalForm.workEvalList[i].isDelete === 0) {
            flag = true
            break
          }
        }
      }
      return flag
    },
    /** 删除新增项 */
    removeDomain (item) {
      if (this.dialogAddNameForm.labelName) {
        this.$refs['dialogAddNameForm'].resetFields()
        this.dialogAddNameForm.labelName = ''
      }
      let index = this.workEvalForm.workEvalList.indexOf(item)
      if (index !== -1) {
        // 删除操作
        if (item.objectId) {
          this.workEvalForm.workEvalList[index].isDelete = 1
          this.$set(this.workEvalForm.workEvalList, index, item)
          /* evaluateInfo.delete(item.objectId).then((res) => {
            if (res.data.resCode === '0000') {
              this.workEvalForm.workEvalList.splice(index, 1)
            }
          }) */
        } else {
          this.workEvalForm.workEvalList.splice(index, 1)
        }
      }
    },
    /** 评价值（好/一般/差）改变 */
    changRadioBtn (item) {
      let index = this.workEvalForm.workEvalList.indexOf(item)
      if (item.evaluateResult === 3) {
        item.showFlag = true
        item.evaluateRemark = ''
        this.$set(this.workEvalForm.workEvalList, index, item)
        // this.workEvalForm.workEvalList.splice(index, 1, item)
      } else {
        item.showFlag = false
        // this.workEvalForm.workEvalList.splice(index, 1, item)
        // this.workEvalForm.workEvalList[index].evaluateResult = item.evaluateResult
        this.$set(this.workEvalForm.workEvalList, index, item)
      }
    },
    /** 提交 */
    submitForm (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          let flag = false
          let auditStatus = ''
          if (this.enterpriseType === 1 || this.enterpriseType === 4) {
            auditStatus = 2
          } else {
            auditStatus = 0
          }
          let projectEvaluateInfo = {
            objectId: (this.projectEvaluateInfo && this.projectEvaluateInfo.objectId) ? this.projectEvaluateInfo.objectId : '',
            code: (this.projectEvaluateInfo && this.projectEvaluateInfo.code) ? this.projectEvaluateInfo.code : '',
            relatedProjectCode: this.projectInfoForm.code,
            projectEvaluateType: this.enterpriseType !== 4 ? 1 : 2,
            status: 1,
            auditStatus: auditStatus,
            targetEnterpriseId: this.projectInfoForm.agencyEnterpriseId,
            submitEnterpriseId: this.$store.getters.authUser.enterpriseId,
            // evaluateLabelList: this.workEvalForm.workEvalList,
            evaluateInfoList: []
          }
          if (this.workEvalForm.workEvalList.length > 0) {
            this.workEvalForm.workEvalList.forEach(item => {
              if (Number(item.evaluateResult) === 3 && item.evaluateRemark === '') {
                flag = true
                let info = '请输入关于' + item.labelName + '的差评原因'
                this.$message.error(info)
                return false
              }
              let obj = {
                objectId: item.objectId,
                labelName: item.labelName,
                relatedLabelCode: item.relatedLabelCode,
                evaluateResult: item.evaluateResult,
                evaluateRemark: item.evaluateRemark,
                evaluateType: item.relatedLabelCode ? 1 : 2,
                targetEnterpriseId: this.projectInfoForm.agencyEnterpriseId,
                isDelete: item.isDelete ? item.isDelete : ''
              }
              projectEvaluateInfo.evaluateInfoList.push(obj)
            })
          }
          if (!flag) {
            projectEvaluate.save(projectEvaluateInfo).then((res) => {
              if (res.data.resCode === '0000') {
                if (this.enterpriseType !== 4) {
                  this.$router.push({path: '/admin/evaluate/agenteval'})
                } else {
                  this.$router.push({path: '/admin/evaluate/projecteval'})
                }
              }
            })
          }
        } else {
          return false
        }
      })
    },
    /** 保存 */
    saveForm (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          let flag = false
          let auditStatus = ''
          if (this.enterpriseType === 1 || this.enterpriseType === 4) {
            auditStatus = 2
          } else {
            auditStatus = 0
          }
          let projectEvaluateInfo = {
            objectId: (this.projectEvaluateInfo && this.projectEvaluateInfo.objectId) ? this.projectEvaluateInfo.objectId : '',
            code: (this.projectEvaluateInfo && this.projectEvaluateInfo.code) ? this.projectEvaluateInfo.code : '',
            relatedProjectCode: this.projectInfoForm.code,
            projectEvaluateType: this.enterpriseType !== 4 ? 1 : 2,
            status: 0,
            auditStatus: auditStatus,
            targetEnterpriseId: this.projectInfoForm.agencyEnterpriseId,
            submitEnterpriseId: this.$store.getters.authUser.enterpriseId,
            // evaluateLabelList: this.workEvalForm.workEvalList,
            evaluateInfoList: []
          }
          if (this.workEvalForm.workEvalList.length > 0) {
            this.workEvalForm.workEvalList.forEach(item => {
              if (Number(item.evaluateResult) === 3 && item.evaluateRemark === '') {
                flag = true
                let info = '请输入关于' + item.labelName + '的差评原因'
                this.$message.error(info)
                return false
              }
              let obj = {
                objectId: item.objectId,
                labelName: item.labelName,
                relatedLabelCode: item.relatedLabelCode,
                evaluateResult: item.evaluateResult,
                evaluateRemark: item.evaluateRemark,
                evaluateType: item.relatedLabelCode ? 1 : 2,
                targetEnterpriseId: this.projectInfoForm.agencyEnterpriseId,
                isDelete: item.isDelete ? item.isDelete : ''
              }
              projectEvaluateInfo.evaluateInfoList.push(obj)
            })
          }
          if (!flag) {
            projectEvaluate.save(projectEvaluateInfo).then((res) => {
              if (res.data.resCode === '0000') {
                if (this.enterpriseType !== 4) {
                  this.$router.push({path: '/admin/evaluate/agenteval'})
                } else {
                  this.$router.push({path: '/admin/evaluate/projecteval'})
                }
              }
            })
          }
        } else {
          return false
        }
      })
    }
  },
  computed: {},
  // 实例创建完成
  mounted () {
  }
}
</script>
<style lang="less">
  #eval-agent-page {
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
        .radio-first
        .el-radio-button__inner {
          background-color: #ecf5ff;
        }
        .radio-second
        .el-radio-button__inner {
          background-color: #f0f9eb;
        }
        .radio-third
        .el-radio-button__inner {
          background-color: #fdf6ec;
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
        .el-form-item:last-child {
          .el-form-item__content {
            margin-left: 0 !important;
            text-align: center;
          }
        }
      }
    }
  }
</style>
