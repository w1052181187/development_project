<template>
  <div class="approve-box">
    <div class="basic-approve-title" v-if="tableFlag && !isHistory">审批记录</div>
    <el-table
      :data="recordList"
      border
      style="width: 100%" header-cell-class-name="tableheader" v-if="tableFlag && !isHistory">
      <el-table-column
        type="index"
        label="序号"
        width="60"
        :index="indexMethod"
        align="center">
      </el-table-column>
      <el-table-column
        prop="auditorName"
        label="审批人"
        width="120"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="auditStatus"
        label="审批结果"
        width="100"
        show-overflow-tooltip>
        <template slot-scope="scope">
          <span  v-if="scope.row.auditStatus === 1">通过</span>
          <span  v-if="scope.row.auditStatus === 2">不通过</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="createDate"
        label="审批时间"
        width="160"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="opinion"
        label="审批意见"
        show-overflow-tooltip>
      </el-table-column>
    </el-table>
    <div class="basic-approve-title">知会人：{{noticeUser}}</div>
  </div>
</template>
<script>
import {approvalRecord, approvalTask} from '@/api/todoList'
export default {
  name: 'approveRecord',
  components: {
  },
  props: ['approvalTaskCode', 'subjectCode', 'flowStatus', 'tableFlag', 'isHistory'],
  data () {
    return {
      recordList: [],
      noticeUser: ''
    }
  },
  watch: {
    '$route': 'init',
    subjectCode: {
      handler () {
        this.init()
      },
      immediate: true,
      deep: true
    },
    tableFlag: {
      handler () {
        this.approveRecord()
      },
      immediate: true,
      deep: true
    },
    isHistory: {
      handler () {
        this.setNoticeUser()
      },
      immediate: true,
      deep: true
    }
  },
  methods: {
    indexMethod (index) {
      return index + 1
    },
    setNoticeUser () {
      if (this.isHistory) {
        this.noticeUser = '项目组其他成员'
      }
    },
    approveRecord () {
      if (!this.subjectCode || !this.tableFlag) {
        return
      }
      let query = {
        subjectCode: this.subjectCode,
        relatedCode: this.approvalTaskCode
      }
      if (Number(this.flowStatus) === 0 || Number(this.flowStatus) === 1) {
        query.flowStatus = this.flowStatus
      }
      approvalRecord.approvalRecordList(query).then((res) => {
        if (res.data.resCode === '0000') {
          this.recordList = res.data.recordOfApprovalList
        }
      })
    },
    getNotifyPeopleList () {
      if (Number(this.flowStatus) !== 0 && Number(this.flowStatus) !== 99 && Number(this.flowStatus) !== 21) {
        this.noticeUser = '项目组其他成员'
      } else {
        this.noticeUser = ''
      }
      if (!this.subjectCode) {
        return
      }
      let query = {
        relatedCode: this.subjectCode
      }
      if ((Number(this.flowStatus) === 0 || Number(this.flowStatus) === 1) && Number(this.flowStatus) !== 99) {
        query.flowStatus = this.flowStatus
      }
      approvalTask.getNotifyPeopleList(query).then((res) => {
        if (res.data.resCode === '0000') {
          if (res.data.notifyPeopleList && res.data.notifyPeopleList.length > 0) {
            let tempUser = ''
            if (Number(this.flowStatus) !== 0 && Number(this.flowStatus) !== 99 && Number(this.flowStatus) !== 21) {
              tempUser = '项目组其他成员'
            }
            res.data.notifyPeopleList.forEach((item, index) => {
              if (index === 0) {
                tempUser += this.noticeUser ? ', ' + item.notifyUserName : item.notifyUserName
              } else {
                tempUser += ', ' + item.notifyUserName
              }
            })
            this.noticeUser = tempUser
          } else {
            if (!this.noticeUser) {
              this.noticeUser = '无'
            }
          }
        }
      })
    },
    init () {
      this.approveRecord()
      this.getNotifyPeopleList()
    }
  },
  mounted () {
  }
}
</script>

<style scoped>
  .approve-box {
    padding: 16px 20px;
    box-sizing: border-box;
  }
  .footer-box{
    text-align: center;
    margin-top: 30px;
  }
  .addbutton{
    color: #fff;
    background-color: #409EFF;
    border-color: #409EFF;
    float: right;
  }
</style>
