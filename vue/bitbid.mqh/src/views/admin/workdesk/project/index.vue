<template>
  <div class="bidpro-box" id="bidpro-box">
    <div class="more-btn">
      <span @click="handleMoreBtn('project')" >更多>></span>
    </div>
    <div class="project-list" v-for="(item, index) in tableData" :key="index" @click="onclickBtn(item)">
      <span class="list-fspan">{{item.tenderProjectName}}</span>
      <span class="list-tspan" v-show="(Number(item.progressStatus) === 1)">
        <span class="span-slot-gree"></span>完成
      </span>
      <span class="list-tspan" v-show="(Number(item.progressStatus) === 2)">
        <span class="span-slot-blue"></span>{{tenderStage}}
      </span>
      <span class="list-tspan" v-show="(Number(item.progressStatus) === 3)">
        <span class="span-slot-red"></span>未开始
      </span>
      <span class="list-sspan">当前阶段</span>
    </div>
    <div class="no-data" v-if="tableData.length<=0">
      <img src="../image/xiangmu.png" alt="">
      <p>~暂无数据~</p>
    </div>
  </div>
</template>
<script>
import {tenderproject} from 'api/index'
// 实例
export default {
  data () {
    return {
      tableData: [],
      tenderStage: '进行中'
    }
  },
  props: ['activeName'],
  watch: {
    activeName: function (newV, oldV) {
      if (newV) {
        this.getCurrentPage(newV)
      }
    }
  },
  created () {
    this.getCurrentPage(this.activeName)
  },
  mounted () {
  },
  methods: {
    onclickBtn (tenderProjectInfo) {
      if (tenderProjectInfo.noticeProgressList.length > 0) {
        this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: tenderProjectInfo.code, noticeCode: tenderProjectInfo.noticeProgressList[0].noticeCode}})
      } else {
        this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: tenderProjectInfo.code}})
      }
    },
    getCurrentPage (activeName) {
      let condition = {}
      condition.numCondition = 0
      if (activeName === 'first') {
        condition.stage = 1
        this.tenderStage = '进行中'
      } else if (activeName === 'second') {
      } else if (activeName === 'third') { // 公告中
        condition.stage = 2
        this.tenderStage = '公告中'
      } else if (activeName === 'fourth') { // 报名中
        condition.stage = 3
        this.tenderStage = '报名中'
      } else if (activeName === 'fifth') { // 文件发售中
        condition.stage = 4
        this.tenderStage = '文件发售中'
      } else if (activeName === 'sixth') { // 待开标
        condition.stage = 5
        this.tenderStage = '待开标'
      }
      this.getAllTenderProjectInfo(condition)
    },
    /** 获取全部的招标项目信息 */
    getAllTenderProjectInfo (condition) {
      tenderproject.getListOnMainPage({
        stage: condition.stage,
        numCondition: condition.numCondition
      }).then((res) => {
        if (res.data.tenderProjectList && res.data.tenderProjectList.list) {
          this.tableData = res.data.tenderProjectList.list
          this.total = res.data.tenderProjectList.total
          this.tableData.forEach(tenderProject => {
            tenderProject.progressStatus = ''
            if (tenderProject.noticeProgressList.length === 0) {
              tenderProject.progressStatus = 3
            } else {
              if (this.checkTenderProjectStatus(tenderProject.noticeProgressList).length > 0) {
                // tenderProject.progressStatusStr = this.getProgressStageStr(tenderProject.latestNotice)
                tenderProject.progressStatus = 2
              } else {
                tenderProject.progressStatus = 1
              }
            }
          })
          if (this.total <= this.pageSize) {
            this.pageNo = 0
          }
        }
      })
    },
    /** 判断招标项目是否已完成（即已有的公告全部完成） */
    checkTenderProjectStatus (noticeProgressList) {
      let results = []
      let flag = false
      if (noticeProgressList) {
        for (let i = 0; i < noticeProgressList.length; i++) {
          if (noticeProgressList[i].progressStage < 7) {
            flag = true
            break
          } else {
            flag = false
          }
        }
        if (flag) {
          results = noticeProgressList
        }
      }
      return results
    },
    /** 获取公告进度 */
    getProgressStageStr (latestNotice) {
      if (latestNotice.noticeStatus === 1) { // 未审批
        return '公告中'
      } else {
        if (latestNotice.progressStage === 1) {
          return '报名中'
        } else if (latestNotice.progressStage === 3 || latestNotice.progressStage === 5) {
          return '文件发售中'
        } else if (latestNotice.progressStage > 5) {
          return '待开标'
        }
      }
    },
    /** 更多 */
    handleMoreBtn (type) {
      if (Object.is(type, 'project')) {
        this.$router.push({path: '/admin/project', query: {activeName: this.activeName}})
      }
    }
  }
}
</script>
<style lang="less">
#bidpro-box {
  .no-data {

  }
}
</style>
