<template>
  <div class="todo-box" id="todo-box">
    <div class="approval-tit">
      <span class="approval-l">我的待办</span>
      <span class="approval-r" @click="handleMoreBtn('todo')" v-if="agendaMessageList.length > 0">更多>></span>
      <!--<span class="approval-m"><i class="el-icon-refresh"></i></span>-->
    </div>
    <div class="message-cont" v-for="(item, index) in agendaMessageList" :key="index">
      <el-row :gutter="20">
        <el-col :span="7">
          <p style="font-weight: 800;">{{item.approveType |filterType}}</p>
          <span class="mes-span" style="color: #999;">{{item.createDate |filterDate}}</span>
        </el-col>
        <el-col :span="14" style="text-align: left;">
          <p><span>{{item.content}}</span></p>
        </el-col>
        <el-col :span="3">
          <el-button
            style="padding:7px"
            type="text"
            round
            size="mini"
            @click.stop="checkBtn(item)" v-if="item.type === 1">
            去审批</el-button>
        </el-col>
        <el-col :span="3">
          <el-button
            style="padding:7px"
            type="text"
            round
            size="mini"
            @click.stop="checkBtn(item)" v-if="item.type === 2">
            去确认</el-button>
        </el-col>
        <el-col :span="3">
          <el-button
            style="padding:7px"
            type="text"
            round
            size="mini"
            @click.stop="checkBtn(item)" v-if="item.type === 3">
            去归档</el-button>
        </el-col>
        <el-col :span="3">
          <el-button
            style="padding:7px"
            type="text"
            round
            size="mini"
            @click.stop="checkBtn(item)" v-if="item.type === 4">
            去评价</el-button>
        </el-col>
        <el-button
          style="padding:7px"
          type="text"
          round
          size="mini"
          @click.stop="checkBtn(item)" v-if="item.type === 5">
          去抽签
        </el-button>
        <el-button
          style="padding:7px"
          type="text"
          round
          size="mini"
          @click.stop="checkBtn(item)" v-if="item.type === 6 && $store.getters.authUser.enterpriseType === 4">
          去开标
        </el-button>
      </el-row>
    </div>
    <div class="no-data" v-if="agendaMessageList.length === 0">
      <img src="../image/shenpi.png" alt="">
      <p>~暂无待办~</p>
    </div>
  </div>
</template>
<script>
import {agendaMessage, planapp, planproject, balanceSheet, drawrecord, notice, answerques, tenderproject, archivesManagement, drawproject,investmanage} from 'api/index'
import {formatDate} from 'common/js/date'
// 实例
export default {
  data () {
    return {
      agendaMessageList: []
    }
  },
  watch: {
    $route: 'refreshData'
  },
  created () {
    this.getAdengaMessageList()
  },
  mounted () {
  },
  filters: {
    filterType (val) {
      if (val === 11 || val === 12 || val === 13 || val === 14 || val === 15) {
        return '招标备案'
      } else if (val === 21 || val === 22 || val === 23) {
        return '抽签系统'
      } else if (val === 31 || val === 32 || val === 33 || val === 34) {
        return '计划管理'
      } else if (val === 41) {
        return '档案管理'
      } else if (val === 61 || val === 62 || val === 63) {
        return '考评系统'
      }
    },
    filterDate (val) {
      if (val) {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return ''
      }
    }
  },
  methods: {
    /** 更多 */
    handleMoreBtn (type) {
      if (Object.is(type, 'todo')) {
        this.$router.push({path: '/admin/todo'})
      }
    },
    /** 列表数据展示 */
    getAdengaMessageList () {
      let query = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        targetUser: this.$store.getters.authUser.userId,
        consumedStatus: 0,
        showList: 1
      }
      agendaMessage.getList(query).then((res) => {
        if (res) {
          this.agendaMessageList = res.data.agendaMessageList.list
          this.total = res.data.agendaMessageList.total
        }
      })
    },
    /** 审批 */
    checkBtn (row) {
      switch (row.type && row.approveType) {
        case 1 && 11:
          // 招标备案-公告
          notice.getByCode(row.relatedCode).then((res) => {
            let noticeData = res.data.notice
            this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: noticeData.tenderProjectCode, noticeCode: noticeData.code, key: true, currentView: 'notice'}})
          })
          break
        case 1 && 12:
          // 招标备案-招标文件
          notice.getByCode(row.relatedCode).then((res) => {
            let noticeData = res.data.notice
            this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: noticeData.tenderProjectCode, noticeCode: noticeData.code, key: true, currentView: 'bidFile'}})
          })
          break
        case 1 && 13:
          // 招标备案-资格预审文件
          notice.getByCode(row.relatedCode).then((res) => {
            let noticeData = res.data.notice
            this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: noticeData.tenderProjectCode, noticeCode: noticeData.code, key: true, currentView: 'qualFile'}})
          })
          break
        case 1 && 14:
          // 招标备案-答疑补遗
          answerques.getByCode(row.relatedCode).then((res) => {
            let clarifyData = res.data.clarify
            notice.getByCode(clarifyData.noticeCode).then((res) => {
              let noticeData = res.data.notice
              this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: noticeData.tenderProjectCode, noticeCode: noticeData.code, key: true, row: clarifyData, currentView: 'answerQues'}})
            })
          })
          break
        case 1 && 21:
          // 抽签系统-选聘备案审批
          drawrecord.getOneByCode(row.relatedCode).then((res) => {
            if (res.data.selectApply.type === 1) {
              this.$router.push({path: '/drawreview/indreview', query: {objectId: res.data.selectApply.objectId, enterpriseName: res.data.selectApply.enterpriseName}})
            } else {
              this.$router.push({path: '/drawreview/dreview', query: {objectId: res.data.selectApply.objectId, enterpriseName: res.data.selectApply.enterpriseName}})
            }
          })
          break
        case 1 && 31:
          // 计划管理-资金平衡表
          let obj = {
            firstBalanceCode: row.relatedCode
          }
          let balanceSheetData = {}
          balanceSheet.getList(obj).then((res) => {
            let balanceSheetList = res.data.balanceSheetList.list
            for (let i = 0; i < balanceSheetList.length; i++) {
              if (balanceSheetList[i].isLocked === 0) {
                balanceSheetData = balanceSheetList[i]
              }
            }
            this.$router.push({path: '/admin/planmanage/balancecheck/check', query: { relatedCode: row.relatedCode, code: balanceSheetData.code, year: balanceSheetData.year, projectEnterpriseId: balanceSheetData.enterpriseId, enterpriseClassify: balanceSheetData.enterpriseClassify, riskRatio: balanceSheetData.riskRatio, flag: 1 }})
          })
          break
        case 1 && 32:
          // 计划管理-投资建议表
          let objOne = {
            code: row.relatedCode
          }
          investmanage.getNewestOne(objOne).then((res) => {
            let investmanageData = res.data.investmentProposalScheme
            this.$router.push({path: '/admin/planmanage/investcheck/checkmain', query: {code: investmanageData.code, year: investmanageData.year, flag: true}})
          })
          break
        case 1 && 33:
          // 计划管理-招采建议计划
          planapp.queryOne({code: row.relatedCode}).then((res) => {
            let annualData = res.data.annualProjectPlan
            this.$router.push({path: '/admin/planmanage/bidpurcheck/bidpurapply', query: {code: row.relatedCode, year: annualData.year, enterpriseName: annualData.enterpriseName}})
          })
          break
        case 1 && 34:
          // 计划管理-项目计划审核
          planproject.getByCode(row.relatedCode).then((res) => {
            let planProject = res.data.projectPlan
            this.$router.push({path: '/admin/planmanage/proplancheck', query: {objectId: planProject.objectId, key: true}})
          })
          break
        case 2 && 22:
          // 抽签系统-抽签结果确认
          this.$router.push({path: '/admin/agency', query: {code: row.relatedCode}})
          break
        case 3 && 41:
          // 档案管理-归档操作
          this.$router.push({path: '/admin/files/filemanage', query: {key: true}})
          break
        case 5 && 23:
          // 抽签系统-抽签
          drawproject.getOneByCode(row.relatedCode).then((res) => {
            let projectGroupData = res.data.projectGroup
            this.$router.push({path: '/drawroom/dready', query: {roomId: projectGroupData.objectId, name: 'dready'}})
          })
          break
        case 6 && 15:
          // 招标备案-开标
          notice.getByCode(row.relatedCode).then((res) => {
            let noticeData = res.data.notice
            if (noticeData.progressStage === 3) {
              this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: noticeData.tenderProjectCode, noticeCode: noticeData.code, key: true, currentView: 'qualPre'}})
            } else if (noticeData.progressStage === 5) {
              this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: noticeData.tenderProjectCode, noticeCode: noticeData.code, key: true, currentView: 'openBid'}})
            }
          })
          break
        case 4 && 61:
          // 考评系统-代理机构评价
          this.$router.push({path: '/admin/evaluate/agenteval/evalpage', query: {code: row.relatedCode}})
          break
        case 4 && 62:
          // 考评系统-专家评价
          this.$router.push({path: '/admin/evaluate/experteval/check', query: {code: row.relatedCode}})
          break
        case 4 && 63:
          // 考评系统-项目单位评价
          this.$router.push({path: '/admin/evaluate/agenteval/evalpage', query: {code: row.relatedCode}})
          break
      }
    },
    /** 重新加载页面 */
    refreshData () {
      this.getAdengaMessageList()
    }
  }
}
</script>
<style lang="less">
#todo-box {
  .no-data {
    padding-top: 65px;
    text-align: center;
  }
}
</style>
