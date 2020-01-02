<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="dreault-details-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>抽签系统</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/draw/drawresult' }">抽签结果备案查询</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/drawresult/details' }">详情页</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="goBack()" class="go-back-btn">
        返回
      </el-button>
    </div>
    <div class="dreault-cont">
      <!-- Tab切换 -->
      <el-tabs v-model="activeName" type="card" @tab-click="handleClickTab">
        <el-tab-pane label="备案表" name="first">
            <div class="record-tab">
            <h2>太原煤气化公司项目抽签情况备案表</h2>
            <table>
              <tr>
                <td class="apply-tab-color">项目名称</td>
                <td colspan="3"><span>{{tableObj.projectName}}</span></td>
              </tr>
              <tr>
                <td class="apply-tab-color">项目编号</td>
                <td colspan="3"><span>{{tableObj.projectId}}</span></td>
              </tr>
              <tr>
                <td class="apply-tab-color">建设单位</td>
                <td colspan="3"><span>{{tableObj.enterpriseName}}</span></td>
              </tr>
              <tr>
                <td class="apply-tab-color">计划金额</td>
                <td><span>{{tableObj.planAmnt}}</span></td>
                <td class="apply-tab-color">计划工期</td>
                <td><span>{{tableObj.timeLimit}}</span></td>
              </tr>
              <tr>
                <td class="apply-tab-color">抽签时间</td>
                <td>
                  <span>{{tableObj.drawLotsTime | filterDate}}</span>
                </td>
                <td class="apply-tab-color">抽签地点</td>
                <td><span>{{tableObj.drawLotsPlace}}</span></td>
              </tr>
              <tr>
                <td rowspan="3" class="apply-tab-color draw-result-td">抽签结果</td>
                <td colspan="3" align="left">
                  <span>{{tableObj.enterpriseName1}}</span>
                </td>
              </tr>
              <tr>
                <td colspan="3" align="left" style="padding-left: 10px;">
                  <span>{{tableObj.enterpriseName2}}</span>
                </td>
              </tr>
              <tr>
                <td colspan="3" align="left" style="padding-left: 10px;">
                  <span>{{tableObj.enterpriseName3}}</span>
                </td>
              </tr>
              <tr>
                <td class="apply-tab-color">参与人员</td>
                <td colspan="3"><span>{{tableObj.participant}}</span></td>
              </tr>
            </table>
            <div class="record-per">
              <span class="apply-tab-color">工作人员:</span>{{staffName}}
            </div>
            <div class="record-per">
              <span class="apply-tab-color">抽签截图:
                <span class="file-list" v-for="(item, index) in fileScreenCapture" :key="index">
                  <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                </span>
              </span>
            </div>
          </div>
          <!--<div class="print-btn">
            <el-button type="primary" @click="printClickBtn">导出PDF</el-button>
          </div>-->
        </el-tab-pane>
        <el-tab-pane label="委托详情" name="second" style="text-align:left;">
          <div class="delegate-tab">
            <div class="bidding-tit">招标项目信息</div>
            <!-- 招标项目信息表格 -->
            <div class="bidding-tab">
              <el-table
                :data="biddingTableData"
                :row-class-name="tableRowClassName"
                border
                style="width: 100%">
                <el-table-column
                  prop="projectName"
                  label="项目名称"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="planQuota"
                  label="计划金额"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="enterpriseName"
                  label="项目单位"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="contacts"
                  label="联系人"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="phone"
                  label="联系方式"
                  align="center">
                </el-table-column>
              </el-table>
            </div>
            <!-- 招标项目信息表格结束 -->

            <div class="delegate-tit">委托记录</div>
            <!-- 委托记录表格 -->
            <div class="delegate-tab">
              <el-table
                :data="delegateTableData"
                :row-class-name="tableRowClassName"
                border
                style="width: 100%">
                <el-table-column
                  prop="enterpriseName"
                  label="代理机构名称"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="accept"
                  label="是否接受"
                  align="center">
                  <template slot-scope="scope">
                    <span class="draw-complete" v-show="scope.row.accept">{{scope.row.accept}}</span>
                    <span class="draw-uncomplete" v-show="!scope.row.accept">---</span>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="reason"
                  label="原因"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="contactsName"
                  label="联系人"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="phone"
                  label="联系方式"
                  align="center">
                </el-table-column>
              </el-table>
            </div>
            <!-- 委托记录表格结束 -->
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
import {drawresult} from 'api/index'
import {formatDate} from 'common/js/date.js'
import {commonJs, downloadFile} from 'common/js/common'
// 实例
export default {
  data () {
    return {
      staffName: '',
      tableObj: {},
      activeName: 'first',
      biddingTableData: [],
      delegateTableData: [],
      fileScreenCapture: []
    }
  },
  created () {
  },
  filters: {
    filterDate (value) {
      let date = new Date(value)
      return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
    }
  },
  methods: {
    downloadFile,
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    // tab切换
    handleClickTab () {},
    goBack () {
      this.$router.push({path: '/draw/drawresult', query: {name: 'index', currentPage: this.$route.query.currentPage, pageNo: this.$route.query.pageNo, pageSize: this.$route.query.pageSize}})
    },
    // 打印
    printClickBtn () {
      window.open(`${commonJs.drawResultExportUrl}?objectId=${this.$route.query.objectId}`)
    },
    // 查询选中项目抽签结果信息
    getCurrentDrawResultInfo () {
      drawresult.getOne(this.$route.query.objectId).then((res) => {
        if (res) {
          this.tableObj = res.data.drawLotsResult
          this.fileScreenCapture = this.tableObj.fileScreenCapture.filter((file) => {
            if (file.businessType !== '2') {
              return file
            }
          })
          this.staffName = this.tableObj.staffName
          if (this.tableObj.projectGroup.selectApplyList.length > 0) {
            // 建设单位
            let enterpriseName = []
            // 去重后的建设单位
            let enterpriseNameTrim = []
            // 建设单位
            let enterpriseNameStr = ''
            // 项目名称
            let projectName = ''
            // 项目编号
            let projectId = []
            // 计划额度
            let planAmnt = []
            // 计划工期
            let timeLimit = []
            // 抽签代表
            let drawUserName = []
            // 去重后的抽签代表
            let drawUserNameTrim = []
            // 抽签代表
            let drawUserNameStr = ''
            for (var i = 0; i < this.tableObj.projectGroup.selectApplyList.length; i++) {
              if (this.tableObj.projectGroup.selectApplyList[i].projectName) {
                if (i === this.tableObj.projectGroup.selectApplyList.length - 1) {
                  projectName += this.tableObj.projectGroup.selectApplyList[i].projectName
                } else {
                  projectName += this.tableObj.projectGroup.selectApplyList[i].projectName + '，'
                }
              }
              if (this.tableObj.projectGroup.selectApplyList[i].codeUser) {
                if (i === this.tableObj.projectGroup.selectApplyList.length - 1) {
                  projectId += this.tableObj.projectGroup.selectApplyList[i].codeUser
                } else {
                  projectId += this.tableObj.projectGroup.selectApplyList[i].codeUser + '，'
                }
              }
              if (this.tableObj.projectGroup.selectApplyList[i].planQuota) {
                if (i === this.tableObj.projectGroup.selectApplyList.length - 1) {
                  planAmnt += this.tableObj.projectGroup.selectApplyList[i].planQuota + '万元'
                } else {
                  planAmnt += this.tableObj.projectGroup.selectApplyList[i].planQuota + '万元' + '，'
                }
              }
              if (this.tableObj.projectGroup.selectApplyList[i].timeLimit) {
                if (i === this.tableObj.projectGroup.selectApplyList.length - 1) {
                  timeLimit += this.tableObj.projectGroup.selectApplyList[i].timeLimit + '个月'
                } else {
                  timeLimit += this.tableObj.projectGroup.selectApplyList[i].timeLimit + '个月' + '，'
                }
              }
              if (this.tableObj.projectGroup.selectApplyList[i].enterpriseName) {
                enterpriseName[i] = this.tableObj.projectGroup.selectApplyList[i].enterpriseName || ''
              }
              if (this.tableObj.projectGroup.selectApplyList[i].userName) {
                drawUserName[i] = this.tableObj.projectGroup.selectApplyList[i].drawUserName || ''
              }
            }
            this.tableObj.projectName = projectName
            if (projectId.length === 0) {
              projectId = ''
            }
            this.tableObj.projectId = projectId
            if (planAmnt.length === 0) {
              planAmnt = ''
            }
            this.tableObj.planAmnt = planAmnt
            if (timeLimit.length === 0) {
              timeLimit = ''
            }
            this.tableObj.timeLimit = timeLimit
            if (enterpriseName.length > 0) {
              for (var a = 0; a < enterpriseName.length; a++) {
                if (enterpriseName[a] !== '' && enterpriseNameTrim.indexOf(enterpriseName[a]) === -1) {
                  enterpriseNameTrim.push(enterpriseName[a])
                }
              }
            }
            if (enterpriseNameTrim.length > 0) {
              for (var b = 0; b < enterpriseNameTrim.length; b++) {
                if (b === enterpriseNameTrim.length - 1) {
                  enterpriseNameStr += enterpriseNameTrim[b]
                } else {
                  enterpriseNameStr += enterpriseNameTrim[b] + '，'
                }
              }
            }
            this.tableObj.enterpriseName = enterpriseNameStr
            if (drawUserName.length > 0) {
              for (var k = 0; k < drawUserName.length; k++) {
                if (drawUserName[k] !== '' && drawUserNameTrim.indexOf(drawUserName[k]) === -1) {
                  drawUserNameTrim.push(drawUserName[k])
                }
              }
            }
            if (drawUserNameTrim.length > 0) {
              for (var c = 0; c < drawUserNameTrim.length; c++) {
                if (c === drawUserNameTrim.length - 1) {
                  drawUserNameStr += drawUserNameTrim[c]
                } else {
                  drawUserNameStr += drawUserNameTrim[c] + '，'
                }
              }
            }
            this.tableObj.drawUserName = drawUserNameStr
          }
          if (this.tableObj.biddingEntrustList.length > 0) {
            this.tableObj.enterpriseName1 = this.tableObj.biddingEntrustList[0].rank + '、' + this.tableObj.biddingEntrustList[0].enterpriseName
            this.tableObj.enterpriseName2 = this.tableObj.biddingEntrustList[1].rank + '、' + this.tableObj.biddingEntrustList[1].enterpriseName
            this.tableObj.enterpriseName3 = this.tableObj.biddingEntrustList[2].rank + '、' + this.tableObj.biddingEntrustList[2].enterpriseName
          }
        } else {
          return false
        }
      })
    },
    getCurrentEntrustInfo () {
      drawresult.getEntrust(this.$route.query.objectId).then((res) => {
        if (res) {
          if (res.data.drawLotsResult.projectGroup.selectApplyList.length > 0) {
            this.biddingTableData = res.data.drawLotsResult.projectGroup.selectApplyList
          }
          if (res.data.drawLotsResult.biddingEntrustList.length > 0) {
            this.delegateTableData = res.data.drawLotsResult.biddingEntrustList
            for (var j = 0; j < this.delegateTableData.length; j++) {
              if (this.delegateTableData[j].isAccept === 0) {
                this.delegateTableData[j].accept = '否'
              } else if (this.delegateTableData[j].isAccept === 1) {
                this.delegateTableData[j].accept = '是'
              }
              if (this.delegateTableData[j].enterpriseExpend) {
                this.delegateTableData[j].contactsName = this.delegateTableData[j].enterpriseExpend.contactsName
                this.delegateTableData[j].phone = this.delegateTableData[j].enterpriseExpend.phone
              }
            }
          }
        } else {
          return false
        }
      })
    }
  },
  computed: {},
  // 实例创建完成
  mounted () {
    this.getCurrentDrawResultInfo()
    this.getCurrentEntrustInfo()
  }
}
</script>
<style lang="less">
#dreault-details-page {
  .dreault-cont {
    padding: 20px;
    margin: 20px 20px 100px;
    background-color: #fff;
  }
  .record-per {
    padding: 10px 0;
    text-align: left;
    font-size: 14px;
  }
  .bidding-tit, .delegate-tit {
    font-size: 14px;
    font-weight: bold;
    padding-bottom: 10px;
  }
  .delegate-tit {
    padding-top: 30px;
  }
  .record-tab {
    width: 900px;
    height: auto;
    margin: 0 auto;
  }
  .record-tab table {
    width: 100%;
    line-height: 40px;
    td:nth-child(2n) {
      min-width: 200px;
      padding-left: 10px;
      text-align: left;
      font-size: 14px;
    }
    td:nth-child(2n-1) {
      min-width: 100px;
      padding: 2px;
      font-size: 14px;
    }
  }
  .record-tab table,.record-tab tr,.record-tab td{
    border: 1px solid #999;
    border-collapse:collapse;
  }
  .draw-result-td {
    height: 120px;
  }
  .apply-tab-color {
    background: #F6F7F9;
    font-size: 14px;
    color: #999;
  }
}
</style>
