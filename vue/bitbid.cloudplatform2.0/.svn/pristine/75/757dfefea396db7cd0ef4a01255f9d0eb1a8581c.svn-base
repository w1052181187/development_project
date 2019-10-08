<template>
  <div class="cloudcontent">
    <approve-flow :approvalTaskCode="approvalTaskCode" :subjectCode="addForm.code" v-if="tableFlag"></approve-flow>
    <div class="project-info-line" v-if="tableFlag"></div>
    <div class="main viewdetails">
      <div class="basic-approve-title">投标单位报名表</div>
      <el-form :model="addForm" label-width="100px" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="24">
            <el-form-item label="附件：">
              <download-file :fileArrays="addForm.fileInformationList" fileType="9"></download-file>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <template>
      <div class="project-info-line" v-if="tableFlag"></div>
      <communicate-record v-if="tableFlag" :relatedCode="addForm.code" :flowType='flowType'
                          :creator="addForm.submitterId" :createName="addForm.submitter"
                          :routingPath="routingPath" :nodeContent="nodeContent"></communicate-record>
      <div class="project-info-line"></div>
      <approve-record :tableFlag="tableFlag" :approvalTaskCode="approvalTaskCode" :flowStatus='flowStatus' :subjectCode="addForm.code"></approve-record>
    </template>
    <approve-handle :isApproved="isApproved" :approvalTaskCode="approvalTaskCode" :relatedCode="addForm.code"></approve-handle>
  </div>
</template>
<script>
import downloadFile from '@/components/download/file-download'
import {documentInfo} from '@/api/project'
import approveFlow from '@/pages/todoList/commonComponents/approveFlow.vue'
import communicateRecord from '@/pages/todoList/commonComponents/communicateRecord.vue'
import approveRecord from '@/pages/todoList/commonComponents/approveRecord.vue'
import approveHandle from '@/pages/todoList/commonComponents/approveHandle.vue'
import {approvalTask} from '@/api/todoList/flow/approval-task'
export default {
  components: {
    downloadFile,
    approveFlow,
    approveRecord,
    communicateRecord,
    approveHandle
  },
  data () {
    return {
      isSubmiting: false,
      // 是否显示审批（0:详情 1：审批 2：审批详情 3：我发起的详情）
      isApproved: 0,
      auditStatus: 0,
      addForm: {
        fileInformationList: ''
      },
      approvalTaskCode: '',
      tableFlag: false,
      flowStatus: 6,
      flowType: 'tenderProject', // 审批类型
      routingPath: '', // 路由地址
      nodeContent: ''
    }
  },
  props: [],
  watch: {
    '$route': 'init'
  },
  created () {
    this.init()
  },
  methods: {
    // 查询当前主体是否包含审批任务,有则展示审批记录等信息
    getTaskByRelatedCode (code, flowStatus) {
      approvalTask.getByRelatedCode({
        relatedCode: code,
        flowStatus: flowStatus
      }).then((res) => {
        if (res.data.resCode === '0000') {
          if (res.data.approvalTask) {
            this.tableFlag = true
          }
        }
      })
    },
    init () {
      this.isApproved = this.$route.query.isApproved
      this.auditStatus = this.$route.query.auditStatus
      this.approvalTaskCode = this.$route.query.code
      this.tableFlag = false
      this.getDocumentInfo()
    },
    getDocumentInfo () {
      documentInfo.getById(this.$route.params.objectId).then(res => {
        this.addForm = res.data.documentInfo
        this.routingPath = this.addForm.routingPath
        let bidSection = this.addForm.bidSection
        this.nodeContent = bidSection.tenderProjectName + bidSection.bidSectionName + '投标单位报名表附件'
        if (Number(this.addForm.auditStatus) !== 0) {
          this.getTaskByRelatedCode(this.addForm.code, this.flowStatus)
        }
      })
    }
  },
  mounted () {
  }
}
</script>
<style scoped>
</style>
