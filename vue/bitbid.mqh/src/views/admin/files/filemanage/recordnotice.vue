<template>
  <!-- 备案及公告组件 -->
  <div class="admin-layout" id="recordnotice-page">
    <div class="card-warp">
      <!--卡片1-->
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>选聘申请备案表</span>
        </div>
        <div class="card-content">
          <el-form :model="proRecordForm" label-width="130px" class="demo-ruleForm">
            <el-form-item label="选聘备案时间:">
              <span>{{proRecordForm.approvePassTime |filterDate}}</span>
            </el-form-item>
            <el-form-item label="选聘备案表:">
             <span class="file-list" v-for="(item, index) in selectApplyFile" :key="index">
              <span><el-button type="text" @click="downloadFile(item.fileName,item.relativePath)" style="padding-right: 30px;">{{item.fileName}}</el-button></span>
            </span>
            </el-form-item>
           <el-form-item label="审批文件:">
               <span class="file-list" v-for="(item, index) in approveFile" :key="index">
              <span><el-button type="text" @click="downloadFile(item.fileName,item.relativePath)" style="padding-right: 30px;">{{item.fileName}}</el-button></span>
            </span>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
      <!--卡片2-->
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>选聘结果备案表</span>
        </div>
        <div class="card-content">
          <el-form :model="resultRecordForm" label-width="130px" class="demo-ruleForm">
            <el-form-item label="选聘结果备案时间:">
              <span>{{resultRecordForm.createDate |filterDate}}</span>
            </el-form-item>
            <el-form-item label="中签代理机构:">
              <span>{{resultRecordForm.entrustEnterpriseName}}</span>
            </el-form-item>
            <el-form-item label="选聘结果备案文件:">
              <span class="file-list">
              <span><el-button type="text" @click="downloadFile(resultFile.fileName,resultFile.relativePath)" style="padding-right: 30px;">
                {{resultFile.fileName}}</el-button></span>
            </span>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
      <!--卡片3-->
      <el-card class="box-card" v-if="Number(this.$route.query.bidType) === 1">
        <div slot="header" class="clearfix">
          <span>招标/资格预审公告</span>
        </div>
        <div class="card-content">
          <el-table
            border
            :data="tableData"
            :row-class-name="tableRowClassName"
            style="width: 100%">
            <el-table-column
              type="index"
              label="序号"
              :index="indexMethod"
              width="55">
            </el-table-column>
            <el-table-column
              prop="noticeName"
              label="公告名称"
              align="center">
            </el-table-column>
            <el-table-column
              prop="noticeType"
              label="公告性质"
              align="center"
              :formatter="filterType">
            </el-table-column>
            <el-table-column
              prop="createDate"
              label="发布时间"
              align="center"
             :formatter="filterDate">
            </el-table-column>
            <el-table-column
              prop="name"
              label="关联标段"
              align="center">
              <template slot-scope="scope">
                <el-tooltip class="item" effect="dark" :content="item.name" v-for="(item,index) in scope.row.sectionList" :key="index" v-if="item.name">
                  <p class="dresult-span">{{item.name}}</p>
                </el-tooltip>
                <p class="dresult-blank-span" v-else></p>
              </template>
            </el-table-column>
            <el-table-column
              prop="fileInformation.fileName"
              label="附件"
              align="center">
              <template slot-scope="scope">
                <el-button type="text" @click="downloadFile(scope.row.fileInformation.fileName,scope.row.fileInformation.relativePath)" style="padding-right: 30px;">{{scope.row.fileName}}</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-card>
    </div>
  </div>
</template>
<script>
import {tenderproject, notice} from 'api/index'
import { formatDate } from 'common/js/date'
import {downloadFile} from 'common/js/common'
export default {
  data () {
    return {
      // dialogSubmit: true,
      proRecordForm: {}, // 项目备案表-表单
      resultRecordForm: {}, // 选聘结果备案表-表单
      tableData: [{noticeName: '公告名称'}], // 招标/资格预审公告-表格
      currentPage: 1,
      businessType: '', // 附件类型
      approveFile: [], // 审批附件
      selectApplyFile: [], // 选聘备案附件
      resultFile: [] // 抽签结果附件
    }
  },
  props: {
    tenderProjectCode: String,
    bidType: Number
  },
  watch: {
    bidType: function (newVal, oldVal) {
      if (newVal) {
        this.getNoticeData()
      }
    }
  },
  computed: {},
  created () {
  },
  filters: {
    filterDate (value) {
      if (value) {
        let date = new Date(value)
        return formatDate(date, 'yyyy-MM-dd hh:mm')
      } else {
        return null
      }
    }
  },
  /** 方法集 */
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 隔行加深颜色 */
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 格式化 */
    filterType (row, column) {
      let noticeType = row.noticeType
      if (noticeType === 1) {
        return '首次公告'
      } else if (noticeType === 2) {
        return '更正公告'
      } else if (noticeType === 3) {
        return '撤销公告'
      } else if (noticeType === 4) {
        return '延期公告'
      } else if (noticeType === 5) {
        return '再次公告'
      } else if (noticeType === 6) {
        return '控制价'
      } else if (noticeType === 7) {
        return '补充公告'
      } else if (noticeType === 8) {
        return '其他公告'
      }
    },
    filterDate (row, column) {
      if (!row.createDate) {
        return ''
      } else {
        let date = new Date(row.createDate)
        return formatDate(date, 'yyyy-MM-dd hh:mm')
      }
    },
    /** 下载附件 */
    downloadFile,
    /** 根据招标项目code选聘备案申请表 */
    getProjectData () {
      tenderproject.getOneByCode(this.tenderProjectCode).then((res) => {
        let data = res.data.tenderProject
        if (data) {
          this.proRecordForm = data.selectApply
          let fileData = this.proRecordForm.fileInformationList
          // 审批附件
          let approveFile = fileData.filter((item) => {
            if (item.businessType !== '2') {
              return item
            }
          })
          this.approveFile = approveFile
          // 选聘备案表
          let selectApplyFile = fileData.filter((item) => {
            if (item.businessType === '2') {
              return item
            }
          })
          this.selectApplyFile = selectApplyFile
          this.resultRecordForm = data.selectApply.drawLotsResult
          if (this.resultRecordForm) {
            if (this.resultRecordForm.fileInformation) {
              this.resultFile = this.resultRecordForm.fileInformation
            }
          }
        }
      })
    },
    /** 根据招标项目code查公告表 */
    getNoticeData () {
      notice.selectListByCode(this.tenderProjectCode).then((res) => {
        let data = res.data.noticeList
        if (data) {
          this.tableData = data
          this.tableData.map((its) => {
            if (its.fileInformation) {
              its.fileName = its.fileInformation.fileName
            }
          })
        }
      })
    }
  },
  /** 实例创建完成 */
  mounted () {
    this.getProjectData()
    this.getNoticeData()
  }
}
</script>
<style lang="less">
#recordnotice-page {
  .card-warp {
    .box-card {
      margin-bottom: 20px;
      .el-card__header {
        background-color: #f7f8f9;
      }
      .clearfix {
        text-align: left;
        font-size: 16px;
        font-weight: 800;
      }
      .el-form .el-form-item__content {
        text-align: left;
      }
    }
  }
}
</style>
