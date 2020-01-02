<template>
  <!-- 后台工作台-通知 -->
  <div class="index-layout" id="todo-page">
    <div class="workdesk-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">当前位置:首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/todo' }">我的待办</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="notice-cont">
      <div class="notice-title">
        <span class="tit-span">我的待办</span>
        <el-button type="text" @click="goBack()" class="workdesk-backbtn">
          返回
        </el-button>
      </div>
      <div class="notice-list" v-for="(item, index) in agendaMessageList" :key="index">
        <el-row class="todo-hover">
          <el-col :span="3" class="col-l">
            <span>{{item.approveType |filterType}}</span>
          </el-col>
          <el-col :span="15" class="col-m work-cont">
            <span>{{item.content}}</span>
          </el-col>
          <el-col :span="4">
            <span>{{item.createDate |filterDate}}</span>
          </el-col>
          <el-col :span="2">
            <el-button
              type="text"
              round
              size="mini"
              @click.stop="checkBtn(item)" v-if="item.type === 1">
              去审批
            </el-button>
            <el-button
              type="text"
              round
              size="mini"
              @click.stop="checkBtn(item)" v-if="item.type === 2">
              去确认
            </el-button>
            <el-button
              type="text"
              round
              size="mini"
              @click.stop="checkBtn(item)" v-if="item.type === 3">
              去归档
            </el-button>
            <el-button
            type="text"
            round
            size="mini"
            @click.stop="checkBtn(item)" v-if="item.type === 4">
            去评价
           </el-button>
            <el-button
              type="text"
              round
              size="mini"
              @click.stop="checkBtn(item)" v-if="item.type === 5">
              去抽签
            </el-button>
            <el-button
              type="text"
              round
              size="mini"
              @click.stop="checkBtn(item)" v-if="item.type === 6 && $store.getters.authUser.enterpriseType === 4 ">
              去开标
            </el-button>
          </el-col>
        </el-row>
      </div>
      <!--分页start-->
      <div class="page-box">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :page-sizes="pageSizeList"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
      <!--分页end-->
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
      agendaMessageList: [],
      agendaMessage: '',
      total: null,
      pageNo: 0,
      pageSize: 10,
      currentPage: 1,
      pageSizeList: [10, 20, 30, 40, 50]
    }
  },
  watch: {
    $route: 'refreshData'
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
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return ''
      }
    }
  },
  created () {
  },
  mounted () {
    this.getAdengaMessageList()
  },
  methods: {
    /** 分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.getAdengaMessageList()
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getAdengaMessageList()
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
            this.$router.push({path: '/drawroom/dready', query: {roomId: String(projectGroupData.objectId), name: 'dready'}})
          })
          break
        case 6 && 15:
          // 招标备案-开标
          notice.getByCode(row.relatedCode).then((res) => {
            let noticeData = res.data.notice
            if (noticeData.progressStage === 4 || noticeData.progressStage === 8) {
              this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: noticeData.tenderProjectCode, noticeCode: noticeData.code, key: true, currentView: 'qualPre'}})
            } else if (noticeData.progressStage === 5 || noticeData.progressStage === 8) {
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
    /** 列表数据展示 */
    getAdengaMessageList () {
      let query = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        targetUser: this.$store.getters.authUser.userId,
        consumedStatus: 0,
        showList: 2
      }
      agendaMessage.getList(query).then((res) => {
        if (res) {
          this.agendaMessageList = res.data.agendaMessageList.list
          this.total = res.data.agendaMessageList.total
        }
      })
    },
    /** 重新加载页面 */
    refreshData () {
      this.getAdengaMessageList()
    },
    goBack () {
      this.$router.push({path: '/admin'})
    }
  }
}
</script>
<style lang="less">
  #todo-page {
    position: relative;
    top: 100px;
    .workdesk-mqh-tit {
      width: 1200px;
      height: 40px;
      line-height: 40px;
      margin: 0 auto;
    }
    .notice-cont {
      width: 1200px;
      height: 720px;
      margin: 0 auto;
      margin-bottom: 80px;
      padding-bottom: 15px;
      background-color: #fff;
      overflow: auto;
      .notice-title {
        height: 40px;
        background-color: #F7F6FB;
        .tit-span {
          display: inline-block;
          margin-top: 10px;
          padding-left: 15px;
          font-size: 16px;
          font-weight: 800;
          float: left;
        }
        .workdesk-backbtn {
          float: right;
          padding-right: 15px;
        }
      }
      .notice-list {
        height: 50px;
        line-height: 50px;
        margin: 15px;
        background-color: #F7F7F7;
        .todo-hover:hover {
          border: 1px solid #6E8AF7;
        }
        .todo-hover .work-cont {
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
        .col-l {
          border-right: 2px solid #fff;
          span {
            font-weight: 800;
          }
        }
        .col-m {
          text-align: left;
          padding-left: 25px;
        }
      }
    }
    .page-box {
      position: absolute;
      bottom: 0;
      left: 40%;
      padding-top: 10px;
    }
  }
</style>
