<template>
  <div class="cloudcontent" id="cloud_addsection">
    <div class="main">
      <el-form :model="updateForm" :rules="rules" ref= "updateForm" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目名称：">
              <span>{{updateForm.tenderProjectName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目编号：">
              <span>{{updateForm.tenderProjectCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="标段名称："  prop="bidSectionName">
              <el-input v-model="updateForm.bidSectionName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标段编号："  prop="bidSectionCode">
              <el-input v-model="updateForm.bidSectionCode"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="标段类型："  prop="selectBidsTypeOptions">
              <el-cascader class=""
                           :props="{ expandTrigger: 'hover' }"
                           :options="bidsTypeOptions"
                           v-model="updateForm.selectBidsTypeOptions"
                           @change="handleCityChange">
              </el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标段合同估算价(元)：" prop="contractReckonPrice">
              <el-input v-model="updateForm.contractReckonPrice"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="招标文件售价(元)：" prop="docGetFee">
              <el-input v-model="updateForm.docGetFee"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24" class="ueditor_box" >
            <el-form-item label="标段内容：" prop="sectionContent" ref="sectionContent">
              <editor ref="sectionEditor" class="ueditor" :editread="editread" :content="updateForm.sectionContent" v-model="updateForm.sectionContent"></editor>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24" class="ueditor_box">
            <el-form-item label="投标人资格条件：" ref="bidderContent">
              <editor ref="bidderEditor" class="ueditor" :editread="editread" :content="bidderContent" v-model="updateForm.bidderContent"></editor>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="handleClickEvent('updateForm','save')" :loading="loading">保存</el-button>
          <el-button type="primary" class="submitBtn" @click="handleClickEvent('updateForm','submit')" :loading="loading">提交</el-button>
          <el-button class="cancal" @click="handleClickEvent('updateForm','cancel')">取消</el-button>
        </el-form-item>
      </el-form>
      <submitApproveDialog :flowStatus="flowStatus" :tenderSystemCode="$route.query.code" :showVisible="approveDialogVisable" @confirmSubmit="confirmSubmit" @handleCancel="cancelApprove"></submitApproveDialog>
    </div>
  </div>
</template>
<script>
import editor from '@/components/ueditor/ueditor.vue'
import * as bidsType from '@/assets/js/constants'
import {tenderProject, bidSection} from '@/api/project'
import {validWhoInteger} from '@/assets/js/validate'
import submitApproveDialog from '@/pages/project/commonCompents/submitApproveDialog'
export default {
  components: {
    editor,
    submitApproveDialog
  },
  data () {
    return {
      flowStatus: 2,
      updateForm: {
        tenderProjectName: '',
        selectBidsTypeOptions: [],
        approvalFlowExecutorList: [],
        noticeList: []
      },
      rules: {
        bidSectionName: [
          { required: true, message: '标段名称不能为空', trigger: ['blur', 'change'] }
        ],
        bidSectionCode: [
          { required: true, message: '标段编号不能为空', trigger: ['blur', 'change'] }
        ],
        selectBidsTypeOptions: [
          { required: true, message: '请选择标段类型', trigger: ['blur', 'change'] }
        ],
        contractReckonPrice: [
          {validator: validWhoInteger, trigger: ['blur', 'change']}
        ],
        docGetFee: [
          { required: true, message: '招标文件售价不能为空', trigger: ['blur', 'change'] },
          {validator: validWhoInteger, trigger: ['blur', 'change']}
        ],
        sectionContent: [
          {required: true, message: '标段内容不能为空', trigger: ['blur', 'change']}
        ],
        bidderContent: [
          { required: true, message: '投标人资格条件不能为空', trigger: ['blur', 'change'] }
        ]
      },
      // 标段类型
      bidsTypeOptions: bidsType.bidsType,
      // 富文本
      editread: false,
      sectionContent: '',
      bidderContent: '',
      loading: false,
      approveDialogVisable: false
    }
  },
  watch: {},
  created () {
    this.getChooseTenderProjectInfo()
  },
  methods: {
    /** 获取当前招标项目信息 */
    getChooseTenderProjectInfo () {
      tenderProject.getBaseByCode(this.$route.query.code).then((res) => {
        if (res.data.tenderProject) {
          // 页面展示
          this.updateForm.tenderProjectName = res.data.tenderProject.tenderProjectName
          this.updateForm.tenderProjectCode = res.data.tenderProject.projectCode
          // 用于保存关联关系
          this.updateForm.enterpriseId = res.data.tenderProject.enterpriseId
          this.updateForm.departmentId = res.data.tenderProject.departmentId
          this.updateForm.tenderSystemCode = res.data.tenderProject.code
          this.updateForm.businessSource = res.data.tenderProject.businessSource
        }
      })
    },
    /** 校验富文本框内容 */
    validContent () {
      let sectionContent = this.$refs.sectionEditor.getContent()
      if (sectionContent) {
        this.$set(this.updateForm, 'sectionContent', sectionContent)
      } else {
        this.$set(this.updateForm, 'sectionContent', '')
      }
      if (sectionContent) {
        this.$refs['sectionContent'].clearValidate()
      }
      let bidderContent = this.$refs.bidderEditor.getContent()
      if (bidderContent) {
        this.$set(this.updateForm, 'bidderContent', bidderContent)
      } else {
        this.$set(this.updateForm, 'biddernContent', '')
      }
      if (bidderContent) {
        this.$refs['bidderContent'].clearValidate()
      }
    },
    /* 按钮点击事件：提交、保存、取消 */
    handleClickEvent (formName, type) {
      if (Object.is(type, 'submit') || Object.is(type, 'save')) {
        if (!this.$refs.sectionEditor.getContent()) {
          this.$message.warning('标段内容需要进行输入！')
          return false
        }
        this.updateForm.sectionContent = this.$refs.sectionEditor.getContent()
        this.updateForm.bidderContent = this.$refs.bidderEditor.getContent()
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (Object.is(type, 'save')) {
              this.approveDialogVisable = false
              this.submitData(type)
            } else {
              this.approveDialogVisable = true
            }
          } else {
            this.loading = false
            return false
          }
        })
      } else if (Object.is(type, 'cancel')) {
        this.$store.commit('delete_tabs', this.$route.fullPath)
        this.$router.push({path: `/project/projectProcess/project/process/${this.$route.query.code}`})
      }
    },
    submitData (type) {
      let msg = Object.is(type, 'submit') ? '提交后数据将锁定，不允许修改，确认要提交吗?' : '确认要保存吗?'
      this.$confirm(msg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        beforeClose: (action, instance, done) => {
          done()
        }
      }).then(() => {
        this.loading = true
        // 获取文本编辑器中的内容
        if (this.$refs.sectionEditor) {
          this.updateForm.bidSectionContent = this.$refs.sectionEditor.getContent()
        }
        if (this.$refs.bidderEditor) {
          this.updateForm.bidQualification = this.$refs.bidderEditor.getContent()
        }
        // 根据type设置当前招标项目的状态
        if (Object.is(type, 'submit')) {
          this.updateForm.auditStatus = 4 // 暂时不考虑审批
          this.updateForm.flowStatus = 5 // 暂时不考虑审批
        } else {
          this.updateForm.auditStatus = 0
          this.updateForm.flowStatus = 1 // 未开始
        }
        // 路由地址的设定
        this.updateForm.routingPath = '/project/projectProcess/project/detail/'
        bidSection.save(this.updateForm).then((res) => {
          this.loading = false
          if (res.data.resCode === '0000') {
            this.$store.commit('delete_tabs', this.$route.fullPath)
            this.$router.push({path: `/project/projectProcess/project/process/${this.$route.query.code}`})
          }
        })
      })
    },
    // 确认提交
    confirmSubmit (record) {
      this.updateForm.approvalFlowExecutorList = []
      this.updateForm.noticeList = []
      for (let i = 0; i < record.approveList.length; i++) {
        let obj = {
          usersId: record.approveList[i].objectId,
          flowLevel: i + 1
        }
        this.updateForm.approvalFlowExecutorList.push(obj)
      }
      if (record.noticeList && record.noticeList.length > 0) {
        this.updateForm.noticeList = record.noticeList
      }
      this.submitData('submit')
    },
    cancelApprove () {
      this.approveDialogVisable = false
    },
    /** 标段类型的选择 */
    handleCityChange () {
      if (this.updateForm.selectBidsTypeOptions) {
        let str = ''
        for (let i = 0; i < this.updateForm.selectBidsTypeOptions.length; i++) {
          if (i === this.updateForm.selectBidsTypeOptions.length - 1) {
            str += this.updateForm.selectBidsTypeOptions[i]
          } else {
            str += this.updateForm.selectBidsTypeOptions[i]
            str += '-'
          }
        }
        this.updateForm.tenderProjectClassifyCode = str
      }
    }
  }
}
</script>
<style lang="less">
  #cloud_addsection {
    .el-cascader{
      width: 100%;
    }
  }
</style>
