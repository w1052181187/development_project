<template>
  <!-- 后台工作台-通知 -->
  <div class="index-layout" id="calendar-page">
    <div class="workdesk-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">当前位置:首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/calendar' }">工作日历</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="calendar-cont">
      <div class="calendar-title">
        <span class="tit-span">工作日历</span>
        <el-button type="text" @click="$router.go(-1)" class="workdesk-backbtn">
          返回
        </el-button>
      </div>
      <div class="tabs-warp">
        <el-tabs v-model="activeName" type="border-card"  @tab-click="handleTabsClick">
          <!--<el-tab-pane label="待审批" name="first">-->
            <!--<div class="notice-list" v-for="(item, index) in agendaMessageList" :key="index">-->
              <!--<div class="notice-sigle">-->
                <!--<el-row class="row-cont">-->
                  <!--<el-col :span="3" class="col-l">-->
                    <!--<span>{{item.approveType |filterType}}</span>-->
                  <!--</el-col>-->
                  <!--<el-col :span="15" class="col-m col-sp">-->
                    <!--<span>{{item.content}}</span>-->
                  <!--</el-col>-->
                  <!--<el-col :span="4">-->
                    <!--<span>{{item.createDate |filterDate}}</span>-->
                  <!--</el-col>-->
                  <!--<el-col :span="2">-->
                    <!--<el-button-->
                      <!--type="text"-->
                      <!--size="mini"-->
                      <!--@click.stop="handleBtnCheck(item)" v-if="item.type === 1">-->
                      <!--去审批-->
                    <!--</el-button>-->
                  <!--</el-col>-->
                <!--</el-row>-->
              <!--</div>-->
            <!--</div>-->
          <!--</el-tab-pane>-->
          <el-tab-pane label="抽签" name="second">
            <div class="notice-list" v-for="(item, index) in agendaMessageList" :key="index">
              <div class="notice-sigle">
                <el-row class="row-cont">
                  <el-col :span="3" class="col-l">
                    <span>{{item.approveType |filterType}}</span>
                  </el-col>
                  <el-col :span="19" class="col-m draw-col">
                    <span>{{item.content}}</span>
                  </el-col>
                  <el-col :span="2">
                    <span>{{item.createDate |filterDate}}</span>
                  </el-col>
                  <el-col :span="2">
                    <el-button
                      type="text"
                      size="mini"
                      @click.stop="handleBtnDraw(item)" v-if="item.type === 5 && item.consumedStatus === 0">
                      去抽签
                    </el-button>
                    <el-button
                      type="text"
                      size="mini"
                      @click.stop="handleBtnDraw(item)" v-if="item.type === 5 && item.consumedStatus === 1">
                      去查看
                    </el-button>
                  </el-col>
                </el-row>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="开标" name="third">
            <div class="notice-list" v-for="(item, index) in agendaMessageList" :key="index">
              <div class="notice-sigle">
                <el-row class="row-cont">
                  <el-col :span="3" class="col-l">
                    <span>{{item.approveType |filterType}}</span>
                  </el-col>
                  <el-col :span="19" class="col-m draw-col">
                    <span>{{item.content}}</span>
                  </el-col>
                  <el-col :span="2">
                    <span>{{item.createDate |filterDate}}</span>
                  </el-col>
                  <el-col :span="2">
                    <el-button
                      type="text"
                      size="mini"
                      @click.stop="handleBtnBidOpen(item)" v-if="item.type === 6 && item.consumedStatus === 0 && $store.getters.authUser.enterpriseType === 4 " >
                      去开标
                    </el-button>
                    <el-button
                      type="text"
                      size="mini"
                      @click.stop="handleBtnBidOpen(item)" v-if="item.type === 6 && item.consumedStatus === 1" >
                      去查看
                    </el-button>
                  </el-col>
                </el-row>
              </div>
            </div>
          </el-tab-pane>
        </el-tabs>
        <!--分页-->
        <div class="block-page">
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
      </div>
    </div>
  </div>
</template>
<script>
import {agendaMessage, planapp, planproject, balanceSheet, drawrecord, notice, answerques, drawproject, drawresult} from 'api/index'
import {formatDate} from 'common/js/date'
// 实例
export default {
  data () {
    return {
      activeName: 'first',
      routeParams: this.$route.query.iconType,
      agendaMessageList: [], // 待审批
      messageList: [], // 待抽签/待开标
      pageNo: 0,
      currentPage: 1,
      pageSize: 10,
      total: null, // 总条数
      pageSizeList: [10, 20, 30, 40, 50]
    }
  },
  filters: {
    filterType (val) {
      if (val === 11 || val === 12 || val === 13 || val === 14) {
        return '招标备案'
      } else if (val === 21 || val === 22) {
        return '抽签系统'
      } else if (val === 31 || val === 32 || val === 33 || val === 34) {
        return '计划管理'
      } else if (val === 41) {
        return '档案管理'
      } else if (val === 51) {
        return '考评系统'
      } else if (val === 61) {
        return '合同管理'
      } else if (val === 5) {
        return '抽签系统'
      } else if (val === 6) {
        return '招标备案'
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
    this.changeTabs()
  },
  methods: {
    /**
     * 方法名:changeTabs
     * 从首页工作日历根据不同类型的操作进入不同的tabs
     **/
    changeTabs () {
      if (Object.is(this.routeParams, 'check')) {
        this.activeName = 'first' // 审核
        this.getAdengaMessageList(1)
      } else if (Object.is(this.routeParams, 'draw')) {
        this.activeName = 'second' // 抽签
        this.getAdengaMessageList(5)
      } else if (Object.is(this.routeParams, 'openbid')) {
        this.activeName = 'third' // 开标
        this.getAdengaMessageList(6)
      }
    },
    /** 二级页面tabs切换 */
    handleTabsClick () {
      if (Object.is(this.activeName, 'first')) {
        this.getAdengaMessageList(1)
      } else if (Object.is(this.activeName, 'second')) {
        this.getAdengaMessageList(5)
      } else if (Object.is(this.activeName, 'third')) {
        this.getAdengaMessageList(6)
      }
    },
    /** 分页 */
    handleSizeChange (val) {
      this.pageSize = val
      if (Object.is(this.activeName, 'first')) {
        this.getAdengaMessageList(1)
      } else if (Object.is(this.activeName, 'second')) {
        this.getAdengaMessageList(5)
      } else if (Object.is(this.activeName, 'third')) {
        this.getAdengaMessageList(6)
      }
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      if (Object.is(this.activeName, 'first')) {
        this.getAdengaMessageList(1)
      } else if (Object.is(this.activeName, 'second')) {
        this.getAdengaMessageList(5)
      } else if (Object.is(this.activeName, 'third')) {
        this.getAdengaMessageList(6)
      }
    },
    /** 审批 */
    handleBtnCheck (row) {
      switch (row.type && row.approveType) {
        case 1 && 11:
          // 招标备案-公告
          notice.getByCode(row.relatedCode).then((res) => {
            let noticeData = res.data.notice
            this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: noticeData.tenderProjectCode, noticeCode: noticeData.code, key: true}})
          })
          break
        case 1 && 12:
          // 招标备案-招标文件
          notice.getByCode(row.relatedCode).then((res) => {
            let noticeData = res.data.notice
            this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: noticeData.tenderProjectCode, noticeCode: noticeData.code, key: true}})
          })
          break
        case 1 && 13:
          // 招标备案-资格预审文件
          notice.getByCode(row.relatedCode).then((res) => {
            let noticeData = res.data.notice
            this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: noticeData.tenderProjectCode, noticeCode: noticeData.code, key: true}})
          })
          break
        case 1 && 14:
          // 招标备案-答疑补遗
          answerques.getByCode(row.relatedCode).then((res) => {
            let clarifyData = res.data.clarify
            notice.getByCode(clarifyData.noticeCode).then((res) => {
              let noticeData = res.data.notice
              this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: noticeData.tenderProjectCode, noticeCode: noticeData.code, key: true, row: clarifyData}})
            })
          })
          break
        case 1 && 21:
          // 抽签系统-选聘备案审批
          drawrecord.getOneByCode(row.relatedCode).then((res) => {
            if (res.data.selectApply.type === 1) {
              this.$router.push({path: '/drawreview/indreview', query: {objectId: res.data.selectApply.objectId}})
            } else {
              this.$router.push({path: '/drawreview/dreview', query: {objectId: res.data.selectApply.objectId}})
            }
          })
          break
        case 1 && 31:
          // 计划管理-资金平衡表
          balanceSheet.getByCode(row.relatedCode).then((res) => {
            let balanceSheet = res.data.balanceSheet
            this.$router.push({path: '/admin/planmanage/balancecheck/check', query: {code: balanceSheet.code, year: balanceSheet.year, projectEnterpriseId: balanceSheet.enterpriseId, enterpriseClassify: balanceSheet.enterpriseClassify, riskRatio: balanceSheet.riskRatio, flag: 1 }})
          })
          break
        case 1 && 32:
          // 计划管理-投资建议表
          this.$router.push({path: '/admin/planmanage/investcheck/checkmain', query: {code: row.relatedCode, flag: true}})
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
      }
    },
    /** 抽签 */
    handleBtnDraw (row) {
      if (row.consumedStatus === 0) {
        drawproject.getOneByCode(row.relatedCode).then((res) => {
          let projectGroupData = res.data.projectGroup
          this.$router.push({path: '/drawroom/dready', query: {roomId: projectGroupData.objectId, name: 'dready'}})
        })
      } else {
        drawresult.getByRelatedCode(row.relatedCode).then((res) => {
          let drawLotsResultData = res.data.drawLotsResult
          this.$router.push({path: '/drawresult/details', query: {objectId: drawLotsResultData.objectId, name: 'details'}})
        })
      }

    },
    /** 开标 */
    handleBtnBidOpen (row) {
      // 招标备案-开标
      notice.getByCode(row.relatedCode).then((res) => {
        let noticeData = res.data.notice
        if (noticeData.progressStage === 3 || noticeData.progressStage === 4) {
          this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: noticeData.tenderProjectCode, noticeCode: noticeData.code, key: true, currentView: 'qualPre'}})
        } else if (noticeData.progressStage === 5 || noticeData.progressStage === 8 || noticeData.progressStage === 6 || noticeData.progressStage === 7) {
          this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: noticeData.tenderProjectCode, noticeCode: noticeData.code, key: true, currentView: 'openBid'}})
        }
      })
    },
    /** 列表数据展示 */
    getAdengaMessageList (type) {
      let query = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        targetUser: this.$store.getters.authUser.userId,
        type: type
      }
      agendaMessage.getList(query).then((res) => {
        if (res) {
          this.agendaMessageList = res.data.agendaMessageList.list
          this.total = res.data.agendaMessageList.total
        }
      })
    }
  }
}
</script>
<style lang="less">
  #calendar-page {
    position: relative;
    top: 100px;
    .workdesk-mqh-tit {
      width: 1200px;
      height: 40px;
      line-height: 40px;
      margin: 0 auto;
    }
    .calendar-cont {
      width: 1200px;
      margin: 0 auto;
      margin-bottom: 80px;
      padding-bottom: 15px;
      background-color: #fff;
      .tabs-warp {
        .el-tabs__content {
          height: 500px;
          overflow: auto;
        }
      }
      .calendar-title {
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
        .workdesk-signbtn {
          float: left;
          margin-top: 6px;
          margin-left: 20px;
          padding-left: 15px;
        }
      }
      .notice-list {
        height: 50px;
        line-height: 50px;
        margin: 15px;
        background-color: #F7F7F7;
        .notice-sigle {
          width: 95%;
          .col-sp {
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
          }
        }
        .del-btn {
          position: absolute;
          right: 0;
          margin-top: -40px;
          margin-right: 40px;
        }
        .row-cont {
          cursor: pointer;
          .draw-col {
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
          }
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
    .block-page {
      padding-top: 10px;
    }
  }
</style>
