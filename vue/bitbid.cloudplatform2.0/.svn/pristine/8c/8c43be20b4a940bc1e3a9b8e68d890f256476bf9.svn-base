<template>
  <div class="cloudcontent">
    <div class="main viewdetails">
      <el-form :model="updateForm">
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目编号：">
              <span>{{updateForm.tenderProjectCode}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目名称：">
              <span>{{updateForm.tenderProjectName}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="招标人：">
              <span>{{updateForm.tendererName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目经理：">
              <span>{{updateForm.firstProjectLeaderName}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-tabs v-model="activeTab">
            <el-tab-pane label="项目情况总结" name="1">
              <el-form-item label="项目背景与要求：">
                <el-input
                  type="textarea"
                  :autosize="{ minRows: 4}"
                  v-model="updateForm.projectBackgroundRequirements"
                  disabled>
                </el-input>
              </el-form-item>
              <el-form-item label="项目情况说明：" prop="projectDescription">
                <el-input
                  type="textarea"
                  :autosize="{ minRows: 4}"
                  v-model="updateForm.projectDescription"
                  disabled>
                </el-input>
              </el-form-item>
            </el-tab-pane>
            <el-tab-pane label="专家评价" name="2">
              <el-table
                :data="updateForm.expertProjectList"
                border
                style="width: 100%" header-cell-class-name="tableheader">
                <el-table-column
                  label="专家姓名"
                  width="160"
                  :formatter="simpleFormatData"
                  prop="expertName"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  label="评标标段"
                  width="200"
                  :formatter="simpleFormatData"
                  prop="bidSectionName"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  label="评价内容"
                  :formatter="simpleFormatData"
                  prop="evaluationContent"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  label="星级"
                  width="160"
                  show-overflow-tooltip>
                  <template slot-scope='scope'>
                      <el-rate v-model="scope.row.grade" :allow-half="true" disabled="disabled"></el-rate>
                  </template>
                </el-table-column>
              </el-table>
            </el-tab-pane>
            <el-tab-pane label="招标人评价" name="3">
              <el-form-item label="评价内容：">
                <el-input
                  type="textarea"
                  :autosize="{ minRows: 4}"
                  v-model="tendererEvaluateInfo.evaluationContent"
                  disabled>
                </el-input>
              </el-form-item>
            </el-tab-pane>
          </el-tabs>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script>
import {tenderProject, tenderProjectSummary, evaluateInfo} from '@/api/project'
import { expertProject } from '@/api/resource'
export default {
  name: 'detail',
  data () {
    return {
      activeTab: '1',
      updateForm: {
        tenderProjectCode: '',
        projectDescription: '',
        expertProjectList: [] // 专家评价
      },
      tendererEvaluateInfo: {
        evaluationContent: ''
      } // 招标人评价
    }
  },
  methods: {
    detail () {
      if (Number(sessionStorage.getItem('checkSummaryAndAndEvaluate')) === 0) {
        tenderProject.getOne(this.$route.query.tenderSystemCode).then((res) => {
          if (res.data.tenderProject) {
            this.updateForm.code = res.data.tenderProject.code
            this.updateForm.tenderProjectCode = res.data.tenderProject.projectCode
            this.updateForm.tenderProjectName = res.data.tenderProject.tenderProjectName
            this.updateForm.tendererName = res.data.tenderProject.tendererName
            this.updateForm.firstProjectLeaderName = res.data.tenderProject.firstProjectLeaderName
          }
        })
      } else if (Number(sessionStorage.getItem('checkSummaryAndAndEvaluate')) === 1) {
        tenderProjectSummary.getByRelatedCode(this.$route.query.tenderSystemCode).then((res) => {
          if (res.data.tenderProjectSummary) {
            this.updateForm.objectId = res.data.tenderProjectSummary.objectId
            this.updateForm.code = res.data.tenderProjectSummary.code
            this.updateForm.tenderSystemCode = res.data.tenderProjectSummary.tenderSystemCode
            this.updateForm.tenderProjectCode = res.data.tenderProjectSummary.tenderProjectCode
            this.updateForm.tenderProjectName = res.data.tenderProjectSummary.tenderProjectName
            this.updateForm.tendererName = res.data.tenderProjectSummary.tendererName
            this.updateForm.firstProjectLeaderName = res.data.tenderProjectSummary.firstProjectLeaderName
            this.updateForm.projectBackgroundRequirements = res.data.tenderProjectSummary.projectBackgroundRequirements
            this.updateForm.projectDescription = res.data.tenderProjectSummary.projectDescription
          }
        })
      }
    },
    /** 获取招标人评价信息 */
    getTenderEvaluateInfo () {
      let queryModel = {
        tenderSystemCode: this.$route.query.tenderSystemCode,
        evaluationType: 1
      }
      evaluateInfo.getList(queryModel).then((res) => {
        if (res.data.evaluateInfoList.length > 0) {
          this.tendererEvaluateInfo = res.data.evaluateInfoList[0]
        }
      })
    },
    /** 获取专家信息 */
    getExpertProjectList () {
      let queryModel = {
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        isDelete: 0,
        tenderSystemCode: this.$route.query.tenderSystemCode,
        pageNo: 0,
        pageSize: 1000,
        orderByField: 'OBJECT_ID',
        orderByMode: 'ASC'
      }
      expertProject.queryList(queryModel).then((res) => {
        this.updateForm.expertProjectList = res.data.data.list
      })
    },
    // 普通格式化数据，空的时候展示"---"
    simpleFormatData (row, col, cellValue) {
      return cellValue || '---'
    },
    init () {
      // 初始化基本数据
      this.detail()
      // 获取专家项目信息
      this.getExpertProjectList()
      // 获取招标人评价信息
      this.getTenderEvaluateInfo()
    }
  },
  mounted () {
    // 初始化
    this.init()
  }
}
</script>

<style scoped>

</style>
