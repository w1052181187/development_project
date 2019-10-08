<template>
  <div class="cloudcontent" id="cloud_processedit">
    <approve-flow :approvalTaskCode="taskCode" :subjectCode="updateForm.code" v-if="tableFlag"></approve-flow>
    <div class="project-info-line" v-if="tableFlag"></div>
    <div class="main viewdetails">
      <div class="basic-approve-title">中标通知书</div>
      <el-form :model="updateForm" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目编号：">
              <span>{{bidSection.tenderProjectCode}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目名称：">
              <span>{{bidSection.tenderProjectName}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="标段编号：">
              <span>{{bidSection.bidSectionCode}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标段名称：">
              <span>{{bidSection.bidSectionName}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="中标人："  prop="successfulBidders">
              <span>{{updateForm.successfulBidders}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="中标价格（元）："  prop="bidAmount">
              <span>{{updateForm.bidAmount}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="附件：">
              <el-table
                :data="updateForm.fileInformationList"
                border
                style="width: 100%" header-cell-class-name="tableheader">
                <el-table-column
                  type="index"
                  label="序号"
                  width="60"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="fileName"
                  label="文件名称"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  label="操作" align="center" width="200">
                  <template slot-scope="scope">
                    <el-button type="text" size="small" @click="lookFile(scope.row)">查看</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <template>
      <div class="project-info-line" v-if="tableFlag"></div>
      <communicate-record v-if="tableFlag" :relatedCode="updateForm.code" :flowType='flowType'
                          :creator="updateForm.submitterId" :createName="updateForm.submitter"
                          :routingPath="routingPath" :nodeContent="nodeContent"></communicate-record>
      <div class="project-info-line"></div>
      <approve-record :tableFlag="tableFlag" :approvalTaskCode="taskCode" :flowStatus='flowStatus' :subjectCode="updateForm.code"></approve-record>
    </template>
    <approve-handle :isApproved="isApproved" :approvalTaskCode="taskCode" :relatedCode="updateForm.code"></approve-handle>
  </div>
</template>
<script>
import editor from '@/components/ueditor/ueditor.vue'
import {downloadFile} from '@/assets/js/common'
import {resultInfo} from '@/api/project'
import approveFlow from '@/pages/todoList/commonComponents/approveFlow.vue'
import communicateRecord from '@/pages/todoList/commonComponents/communicateRecord.vue'
import approveRecord from '@/pages/todoList/commonComponents/approveRecord.vue'
import approveHandle from '@/pages/todoList/commonComponents/approveHandle.vue'
import {approvalTask} from '@/api/todoList/flow/approval-task'
export default {
  components: {
    editor,
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
      objectId: '',
      updateForm: {},
      bidSection: {},
      // 富文本
      editread: true,
      taskCode: '',
      tableFlag: false,
      flowStatus: 19,
      flowType: 'tenderProject', // 审批类型
      routingPath: '', // 路由地址
      nodeContent: ''
    }
  },
  methods: {
    lookFile (file) {
      downloadFile(file.fileName, file.relativePath)
    },
    getResultInfo () {
      resultInfo.getById(this.objectId).then(res => {
        this.updateForm = res.data.resultInfo
        this.routingPath = this.updateForm.routingPath
        if (this.updateForm.content) {
          this.$refs.ueditor.setContent(this.updateForm.content)
        }
        if (this.updateForm.bidSection) {
          this.bidSection = this.updateForm.bidSection
          this.nodeContent = this.bidSection.tenderProjectName + this.bidSection.bidSectionName + '中标通知书'
        }
        if (Number(this.updateForm.auditStatus) !== 0) {
          this.getTaskByRelatedCode(this.updateForm.code, this.flowStatus)
        }
      })
    },
    // 查询当前主体是否包含审批任务,有则展示审批记录等信息
    getTaskByRelatedCode (code, flowStatus) {
      approvalTask.getByRelatedCode({
        relatedCode: code,
        flowStatus: flowStatus
      }).then((res) => {
        if (res.data.resCode === '0000') {
          if (res.data.approvalTask) {
            this.tableFlag = true
            this.$nextTick(() => {
              this.$refs.ueditor.setContent(this.updateForm.content)
            })
          }
        }
      })
    },
    init () {
      this.isApproved = this.$route.query.isApproved
      this.auditStatus = this.$route.query.auditStatus
      this.objectId = this.$route.params.objectId
      this.taskCode = this.$route.query.code
      this.tableFlag = false
      this.getResultInfo()
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
  }
</style>
