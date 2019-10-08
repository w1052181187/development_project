<template>
  <div class="cloudcontent" id="cloud_processedit">
    <div class="main">
      <el-form :model="updateForm" :rules="rules" ref="updateForm" :validate-on-rule-change="true">
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
            <el-form-item label="标段名称：">
              <span>{{updateForm.bidSectionName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标段编号：">
              <span>{{updateForm.bidSectionCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="废标结果公告名称："  prop="title">
              <el-input v-model="updateForm.title"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否重新招标：" prop="isRetender">
              <el-select v-model="updateForm.isRetender" placeholder="请选择">
                <el-option label="是" value="1"></el-option>
                <el-option label="否" value="0"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="公告链接：">
              <el-input v-model="updateForm.bulletinUrl"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发布网站：">
              <el-input v-model="updateForm.publishUrl"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="废标原因：">
              <el-input type="textarea" v-model="updateForm.abolitionReason" class="areaBox"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24" class="ueditor_box">
            <el-form-item label="公告内容：" >
              <editor ref="notice_ueditor" class="ueditor" :editread="editread" :content = "content"></editor>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="附件：" class="bitianicon">
              <upload-file @uploadSuccess="uploadOtherSuccess" @deleteSuccess="deleteSuccess"
                           :fileArrays="updateForm.fileInformationList"
                           fileType="9"
                           isImage="0">
              </upload-file>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submit('updateForm', 0)" :loading="isSubmiting">保存</el-button>
          <el-button type="primary" class="submitBtn" @click="submit('updateForm', 1)" :loading="isSubmiting">提交</el-button>
          <el-button class="cancal" @click="close">取消</el-button>
        </el-form-item>
      </el-form>
      <submitApproveDialog :flowStatus="flowStatus" :tenderSystemCode="updateForm.tenderSystemCode" :showVisible="approveDialogVisable" @confirmSubmit="confirmSubmit" @handleCancel="cancelApprove"></submitApproveDialog>
    </div>
  </div>
</template>
<script>
import editor from '@/components/ueditor/ueditor.vue'
import uploadFile from '@/components/upload/publicFileUpload'
import {bulletinInfo} from '@/api/project'
import submitApproveDialog from '@/pages/project/commonCompents/submitApproveDialog'
export default {
  components: {
    editor,
    uploadFile,
    submitApproveDialog
  },
  data () {
    return {
      flowStatus: 16,
      isSubmiting: false,
      updateForm: {
        tenderProjectCode: '',
        fileInformationList: [],
        approvalFlowExecutorList: [],
        noticeList: []
      },
      rules: {
        title: [
          { required: true, message: '公告名称不能为空', trigger: ['blur', 'change'] }
        ],
        isRetender: [
          { required: true, message: '请选择是否重新招标', trigger: ['blur', 'change'] }
        ]
      },
      // 富文本
      editread: false,
      content: '',
      approveDialogVisable: false
    }
  },
  methods: {
    // 上传附件
    uploadOtherSuccess (file) {
      this.updateForm.fileInformationList.push(file)
    },
    deleteSuccess (fileId) {
      this.updateForm.fileInformationList = this.updateForm.fileInformationList.filter(item => item.relativePath !== fileId)
    },
    detail () {
      bulletinInfo.getOneByRelatedCode(this.$route.query.code, this.$route.query.sectionSystemCode).then((res) => {
        if (res.data.resCode === '0000') {
          this.updateForm = res.data.bulletinInfo
          this.updateForm.isRetender = this.updateForm.isRetender.toString()
          if (this.updateForm.content) {
            this.$refs.notice_ueditor.setContent(this.content = this.updateForm.content)
          }
        }
      })
    },
    validLinks (str) {
      const reg = /^http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- ./?%&=]*)?$/
      return reg.test(str)
    },
    // 提交/保存
    submit (form, status) {
      this.$refs[form].validate((valid) => {
        if (valid) {
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
      // 公告链接格式的判断
      if (this.updateForm.bulletinUrl && !this.validLinks(this.updateForm.bulletinUrl)) {
        this.$message({
          type: 'warning',
          message: '公告链接格式有误！'
        })
        return false
      }
      if (this.updateForm.fileInformationList.length === 0) {
        this.$message.warning('附件不能为空！')
        return false
      }
      let msg = Object.is(status, 1) ? '提交后数据将锁定，不允许修改，确认要提交吗?' : '确认要保存吗?'
      this.$confirm(msg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        beforeClose: (action, instance, done) => {
          done()
        }
      }).then(() => {
        this.isSubmiting = true
        // 根据type设置当前招标项目的状态
        if (Object.is(status, 1)) {
          this.updateForm.auditStatus = 4 // 暂时不考虑审批
          this.updateForm.currentBidSectionSystemCode = this.$route.query.sectionSystemCode
        } else {
          this.updateForm.auditStatus = 0
        }
        this.updateForm.content = this.$refs.notice_ueditor.getContent()
        this.updateForm.fileInformationList.map(item => {
          item.objectId = null
        })
        let arr = []
        arr.push({
          code: this.$route.query.sectionSystemCode
        })
        this.updateForm.bidSectionList = arr
        bulletinInfo.save(this.updateForm).then((res) => {
          if (res.data.resCode === '0000') {
            this.isSubmiting = false
            this.$router.go(-1)
          }
        })
      }).catch(() => {
        this.isSubmiting = false
        return false
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
    init () {
      this.detail()
    }
  },
  watch: {
    '$route': 'init'
  },
  mounted () {
    this.init()
  }
}
</script>
<style lang="less">
  #cloud_processedit{
    .areaBox textarea{
      min-height: 100px !important;
    }
  }
</style>
