<template>
  <div class="flow-box">
    <div class="basic-approve-title">审批流程图</div>
    <ul>
      <li class="flow">
        <div class="flow-person">开始</div>
        <img class="flow-arrow" src="../../../../static/images/approve/jiantou.png">
      </li>
      <li class="flow" v-for="(item, index) in approvalFlowExecutorList" :key="index">
        <div :class="['flow-person', currentLevel === index ? 'flow-cur' : currentLevel < index ? 'flow-last' : '']" :title="item.allUserName">{{item.usersNameStr}}</div>
        <img class="flow-arrow" src="../../../../static/images/approve/jiantou.png">
      </li>
      <li class="flow">
        <div :class="['flow-person', currentLevel < approvalFlowExecutorList.length ? 'flow-last' : '']">结束</div>
      </li>
    </ul>
  </div>
</template>

<script>
import {approvalTask} from '@/api/todoList/flow/approval-task'

export default {
  name: 'approveFlow',
  props: ['approvalTaskCode', 'subjectCode', 'flowStatus'],
  data () {
    return {
      approvalFlowExecutorList: [],
      currentLevel: 0
    }
  },
  watch: {
    '$route': 'init',
    approvalTaskCode: {
      handler () {
      },
      immediate: true,
      deep: true
    },
    subjectCode: {
      handler () {
        this.init()
      },
      immediate: true,
      deep: true
    }
  },
  methods: {
    getApprovalFlowExecutorByApprovalTaskCode (approvalTaskCode) {
      approvalTask.getApprovalFlowExecutorList(approvalTaskCode).then((res) => {
        if (res.data.resCode === '0000') {
          this.approvalFlowExecutorList = res.data.approvalFlowExecutorList
          this.approvalFlowExecutorList.forEach(item => {
            let userArr = item.usersNameStr.split(',')
            item.allUserName = item.usersNameStr
            if (userArr.length > 1) {
              item.usersNameStr = userArr[0] + '等'
            }
          })
          this.currentLevel = res.data.currentLevel - 1
        }
      })
    },
    getApprovalFlowExecutorBySubjectCode (subjectCode, flowStatus) {
      approvalTask.getApprovalFlowExecutorListBySubjectCode({
        relatedCode: subjectCode,
        flowStatus: flowStatus
      }).then((res) => {
        if (res.data.resCode === '0000') {
          this.approvalFlowExecutorList = res.data.approvalFlowExecutorList
          this.approvalFlowExecutorList.forEach(item => {
            let userArr = item.usersNameStr.split(',')
            item.allUserName = item.usersNameStr
            if (userArr.length > 1) {
              item.usersNameStr = userArr[0] + '等'
            }
          })
          this.currentLevel = res.data.currentLevel - 1
        }
      })
    },
    init () {
      if (this.approvalTaskCode) {
        this.getApprovalFlowExecutorByApprovalTaskCode(this.approvalTaskCode)
      } else {
        if (!this.subjectCode) {
          return
        }
        this.getApprovalFlowExecutorBySubjectCode(this.subjectCode, this.flowStatus)
      }
    }
  }
}
</script>

<style scoped>
  .flow-box{
    padding: 16px 20px;
    box-sizing: border-box;
  }
  .flow-box ul{
    margin-top: 12px;
    margin-left: 12px;
    overflow: hidden;
  }
  .flow{
    display: inline-block;
    float: left;
    height: 56px;
    line-height: 56px;
  }
  .flow-person{
    width: 50px;
    height: 50px;
    line-height: 50px;
    text-align: center;
    border-radius: 100%;
    background-color: #3bb072;
    font-size: 12px;
    color: white;
    display: inline-block;
    overflow: hidden;
    vertical-align: middle;
  }
  .flow-arrow{
    display: inline-block;
    width: 12px;
    height: 8px;
    vertical-align: middle;
    margin: 0 12px;
  }
  .flow-cur{
    background-color: #eafff4;
    border: 1px #3bb072 solid;
    color: #333;
  }
  .flow-last{
    background-color: #bbbbbb;
  }
</style>
