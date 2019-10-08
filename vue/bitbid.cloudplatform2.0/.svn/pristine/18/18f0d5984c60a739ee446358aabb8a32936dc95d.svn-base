<template>
  <div class="cloudcontent">
    <div class="main">
      <el-form :model="updateForm" :rules="rules" ref="updateForm" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目编号："  prop="tenderProjectCode">
              <el-input v-model="bidSection.tenderProjectCode" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目名称："  prop="tenderProjectName">
              <el-input v-model="bidSection.tenderProjectName" disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="标段编号："  prop="bidSectionCode">
              <el-input v-model="bidSection.bidSectionCode" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标段名称："  prop="bidSectionName">
              <el-input v-model="bidSection.bidSectionName" disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="上传资格审查报告：" class="bitianicon">
              <upload-file @uploadSuccess="uploadOtherSuccess" @deleteSuccess="deleteSuccess"
                           :fileArrays="updateForm.fileInformationList"
                           fileType="9"
                           isImage="0">
              </upload-file>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24" class="ueditor_box">
            <el-form-item label="资格审查结果：" class="bitianicon">
              <el-button class="addbtn" @click="addResultBtn">+ 新增</el-button>
              <el-table
                :data="resultList"
                border
                style="width: 50%" header-cell-class-name="tableheader">
                <el-table-column
                  prop="bidderName"
                  label="投标人">
                  <template slot-scope='scope'>
                    <el-input v-model="scope.row.bidderName" readonly>
                      <el-button type="primary" slot="append" size="mini" @click="btnChoose(scope.$index)">选择</el-button>
                    </el-input>
                  </template>
                </el-table-column>
                <el-table-column
                  label="是否通过资格审查"
                  align="center"
                  width="150">
                  <template slot-scope="scope">
                    <el-radio-group v-model="scope.row.isPassQualificationExamine">
                      <el-radio :label="0">否</el-radio>
                      <el-radio :label="1">是</el-radio>
                    </el-radio-group>
                  </template>
                </el-table-column>
                <el-table-column
                  label="操作"
                  align="center"
                  width="80">
                  <template slot-scope="scope">
                    <el-button type="text" size="small" @click="handleDel(scope)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submit('updateForm', 0)" :loading="isSaving">保存</el-button>
          <el-button type="primary" class="submitBtn" @click="submit('updateForm', 1)" :loading="isSubmiting">提交</el-button>
          <el-button class="cancal" @click="close">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
    <bidder-dialog :tenderSystemCode="tenderSystemCode" :showVisible="showBidderVisible" @selected="selectedBidder" @showDialog="showBidderDialog"></bidder-dialog>
    <submitApproveDialog :flowStatus="flowStatus" :tenderSystemCode="tenderSystemCode" :showVisible="approveDialogVisable" @confirmSubmit="confirmSubmit" @handleCancel="cancelApprove"></submitApproveDialog>
  </div>
</template>
<script>
import uploadFile from '@/components/upload/publicFileUpload'
import {documentInfo} from '@/api/project'
import bidderDialog from '@/pages/project/projectProcess/common/bidder_dialog.vue'
import submitApproveDialog from '@/pages/project/commonCompents/submitApproveDialog'
export default {
  components: {
    uploadFile,
    bidderDialog,
    submitApproveDialog
  },
  data () {
    return {
      flowStatus: 9,
      isSaving: false,
      isSubmiting: false,
      updateForm: {
        fileInformationList: []
      },
      rules: {},
      // 富文本
      editread: false,
      content: '',
      resultList: [],
      bidSection: {},
      index: 0,
      showBidderVisible: false,
      tenderSystemCode: this.$route.query.tenderSystemCode,
      approveDialogVisable: false
    }
  },
  methods: {
    /**
     * 增加资格审查结果
     */
    addResultBtn () {
      this.resultList.push({})
    },
    /**
     * 删除资格审查结果
     */
    handleDel (scope) {
      this.resultList.splice(scope.$index, 1)
    },
    // 上传附件
    uploadOtherSuccess (file) {
      this.updateForm.fileInformationList.push(file)
    },
    deleteSuccess (fileId) {
      this.updateForm.fileInformationList = this.updateForm.fileInformationList.filter(item => item.relativePath !== fileId)
    },
    // 提交/保存
    submit (form, status) {
      if (this.updateForm.fileInformationList.length < 1) {
        this.$message({
          message: '请上传资格审查报告！',
          type: 'warning'
        })
        return
      }
      if (!this.resultList || this.resultList.length < 1) {
        this.$message({
          message: '请添加资格审查结果！',
          type: 'warning'
        })
        return
      } else {
        let continueFlag = true
        this.resultList.map(result => {
          if (!result.bidderName || (result.isPassQualificationExamine !== 0 && result.isPassQualificationExamine !== 1)) {
            continueFlag = false
            return false
          }
        })
        if (!continueFlag) {
          this.$message({
            message: '请填写投标人及是否通过资格审查信息！',
            type: 'warning'
          })
          return
        }
      }
      let bidderNameList = []
      this.resultList.map(result => {
        if (!bidderNameList.includes(result.bidderName)) {
          bidderNameList.push(result.bidderName)
        }
      })
      if (bidderNameList.length !== this.resultList.length) {
        this.$message({
          message: '投标人重复，请重新填写！',
          type: 'warning'
        })
        return
      }
      this.$refs[form].validate((vaild) => {
        if (vaild) {
          if (Object.is(status, 0)) {
            this.approveDialogVisable = false
            this.submitData(status)
          } else {
            this.approveDialogVisable = true
          }
        } else {
          return false
        }
      })
    },
    submitData (status) {
      let msg = Object.is(status, 1) ? '提交后数据将锁定，不允许修改，确认要提交吗?' : '确定要保存吗?'
      this.$confirm(msg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        beforeClose: (action, instance, done) => {
          done()
        }
      }).then(() => {
        if (Object.is(status, 0)) {
          this.isSaving = true
        } else if (Object.is(status, 1)) {
          this.isSubmiting = true
          this.updateForm.auditStatus = 4
          this.updateForm.submitTime = new Date().getTime()
        }
        let bidderName = ''
        let isPassQualificationExamine = ''
        this.resultList.map(item => {
          bidderName = bidderName + item.bidderName + ';'
          isPassQualificationExamine = isPassQualificationExamine + item.isPassQualificationExamine + ';'
        })
        this.updateForm.bidderName = bidderName.substring(0, bidderName.length - 1)
        this.updateForm.isPassQualificationExamine = isPassQualificationExamine.substring(0, isPassQualificationExamine.length - 1)
        this.updateForm.fileInformationList.map(item => {
          item.objectId = null
        })
        documentInfo.update(this.updateForm).then((res) => {
          this.isSaving = false
          this.isSubmiting = false
          if (res.data.resCode === '0000') {
            this.close()
          }
        })
      }).catch(() => {
        this.isSaving = false
        this.isSubmiting = false
        return false
      })
    },
    // 确认提交
    confirmSubmit (record) {
      this.updateForm.approvalFlowExecutorList = []
      this.updateForm.manualAdditionList = []
      for (let i = 0; i < record.approveList.length; i++) {
        let obj = {
          usersId: record.approveList[i].objectId,
          flowLevel: i + 1
        }
        this.updateForm.approvalFlowExecutorList.push(obj)
      }
      if (record.noticeList && record.noticeList.length > 0) {
        this.updateForm.manualAdditionList = record.noticeList
      }
      this.submitData(1)
    },
    cancelApprove () {
      this.approveDialogVisable = false
    },
    // 取消
    close () {
      this.$store.commit('delete_tabs', this.$route.fullPath)
      this.$router.go(-1)
    },
    getDocumentInfo () {
      documentInfo.getById(this.$route.query.objectId).then(res => {
        this.updateForm = res.data.documentInfo
        if (this.updateForm.bidSection) {
          this.bidSection = this.updateForm.bidSection
        }
        let bidderName = this.updateForm.bidderName.split(';')
        let isPassQualificationExamine = this.updateForm.isPassQualificationExamine.split(';')
        for (let i = 0; i < bidderName.length; i++) {
          let result = {}
          result.bidderName = bidderName[i]
          result.isPassQualificationExamine = Number(isPassQualificationExamine[i])
          this.resultList.push(result)
        }
      })
    },
    // 选择投标人
    btnChoose (index) {
      this.index = index
      this.showBidderVisible = true
    },
    // 选择投标人弹窗 打开/关闭
    showBidderDialog () {
      this.showBidderVisible = !this.showBidderVisible
    },
    // 已选择投标人信息
    selectedBidder (obj) {
      this.$set(this.resultList[this.index], 'bidderName', obj.name)
    }
  },
  watch: {
    '$route': 'getDocumentInfo'
  },
  mounted () {
    this.getDocumentInfo()
  }
}
</script>

<style scoped>
  .addbtn{
    border: 1px solid #3f63f6;
    height: 32px;
    padding: 7px 12px;
    margin-bottom: 12px;
  }
</style>
