<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="contract-details-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/contract/contractrecord' }">合同备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/contract/contractrecord/details' }">查看</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>

    <!-- 详情表格 start -->
    <div class="table-box">
      <div id="print">
      <div class="title-box" style="text-align:center;">
        <h2>太原煤炭气化（集团）有限责任公司合同备案表</h2>
      </div>
      <div class="record-info" style="margin: 0 auto;">
        <!-- 项目信息start -->
        <table class="table-cont" style="width: 100%;margin-bottom: 30px;border-collapse: collapse;border: 1px solid #ddd;">
          <tbody>
            <tr class="fisrt-tr" style="border: 1px solid #ddd;height: 40px;line-height: 40px;">
              <td colspan="4" style="border: 1px solid #ddd;background: #fff;text-align:center;"><b style="font-size: 18px;">项目信息</b></td>
            </tr>
            <tr style="border: 1px solid #ddd;height: 40px;line-height: 40px;">
              <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">项目名称</td>
              <td colspan="3" style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateProject.tenderProject.tenderProjectName}}</td>
            </tr>
            <tr style="border: 1px solid #ddd;height: 40px;line-height: 40px;">
              <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">项目编号</td>
              <td style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateProject.tenderProject.codeUser}}</td>
              <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">项目类型</td>
              <td style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateProject.tenderProject.tenderProjectType |filterProjectType }}</td>
            </tr>
            <tr style="border: 1px solid #ddd;height: 40px;line-height: 40px;">
              <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">标段名称</td>
              <td colspan="3" style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateProject.sectionName}}</td>
            </tr>
            <tr style="border: 1px solid #ddd;height: 40px;line-height: 40px;">
              <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">项目单位</td>
              <td colspan="3" style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateProject.projectEnterpriseName}}</td>
            </tr>
            <tr style="border: 1px solid #ddd;height: 40px;line-height: 40px;">
              <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">需求方</td>
              <td colspan="3" style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateProject.requiresEnterpriseName}}</td>
            </tr>
            <tr style="border: 1px solid #ddd;height: 40px;line-height: 40px;">
              <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">招标代理机构</td>
              <td style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateProject.agentEnterpriseName}}</td>
              <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">公告时间</td>
              <td style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateProject.noticeTime |filterTime}}</td>
            </tr>
            <tr style="border: 1px solid #ddd;height: 40px;line-height: 40px;">
              <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">中标单位</td>
              <td style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateProject.winnerBidderName}}</td>
              <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">中标金额(万元)</td>
              <td style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateProject.winPrice}}</td>
            </tr>
            <tr style="border: 1px solid #ddd;height: 40px;line-height: 40px;">
              <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">开标时间</td>
              <td style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateProject.bidOpenTime |filterTime}}</td>
              <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">公示时间</td>
              <td style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateProject.publicityTime |filterTime}}</td>
            </tr>
          </tbody>
        </table>
        <!-- 项目信息end -->
        <!-- 合同信息start -->
        <table class="table-cont" style="width: 100%;margin-bottom: 30px;border-collapse: collapse;border: 1px solid #ddd;">
          <tbody>
          <tr class="fisrt-tr" style="border: 1px solid #ddd;height: 40px;line-height: 40px;">
            <td colspan="4" style="border: 1px solid #ddd;background: #fff;text-align:center;"><b style="font-size: 18px;">合同信息</b></td>
          </tr>
          <tr style="border: 1px solid #ddd;height: 40px;line-height: 40px;">
            <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">质保金（万元）</td>
            <td style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateContract.warrantyGold}}</td>
            <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">质保期</td>
            <td style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateContract.warrantyPeriod}}</td>
          </tr>
          <tr style="border: 1px solid #ddd;height: 40px;line-height: 40px;">
            <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">合同价（万元）</td>
            <td style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateContract.contractPrice}}</td>
            <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">付款方式</td>
            <td style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateContract.payMethod}}</td>
          </tr>
          <tr v-if="tableDateProject.tenderProject.tenderProjectType === 1" style="border: 1px solid #ddd;height: 40px;line-height: 40px;">
            <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">项目经理</td>
            <td style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateContract.projectManager}}</td>
            <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">合同编号</td>
            <td style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateContract.contractNo}}</td>
          </tr>
          <tr v-if="tableDateProject.tenderProject.tenderProjectType === 2" style="border: 1px solid #ddd;height: 40px;line-height: 40px;">
            <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">供货期</td>
            <td style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateContract.supplyPeriod}}</td>
            <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">合同编号</td>
            <td style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateContract.contractNo}}</td>
          </tr>
          <tr v-if="tableDateProject.tenderProject.tenderProjectType === 2" style="border: 1px solid #ddd;height: 40px;line-height: 40px;">
            <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">规格、型号、数量</td>
            <td colspan="3" style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateContract.goodsExplain}}</td>
          </tr>
          <tr v-if="tableDateProject.tenderProject.tenderProjectType === 3" style="border: 1px solid #ddd;height: 40px;line-height: 40px;">
            <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">服务期</td>
            <td style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateContract.serverPeriod}}</td>
            <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">项目负责人</td>
            <td style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateContract.projectName}}</td>
          </tr>
          <tr v-if="tableDateProject.tenderProject.tenderProjectType === 3" style="border: 1px solid #ddd;height: 40px;line-height: 40px;">
            <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">合同编号</td>
            <td colspan="3" style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateContract.contractNo}}</td>
          </tr>
          <tr style="border: 1px solid #ddd;height: 40px;line-height: 40px;">
            <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">结算方式及依据</td>
            <td colspan="3" style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{tableDateContract.basisSettlement}}</td>
          </tr>
          <tr style="border: 1px solid #ddd;height: 40px;line-height: 40px;">
            <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">项目单位签字</td>
            <td colspan="3" style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{submitUnitData.applyName}}-{{submitUnitData.approveTime | filterTime}}</td>
          </tr>
          <tr style="border: 1px solid #ddd;height: 40px;line-height: 40px;">
            <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">集团招标办签字</td>
            <td colspan="3" style="border: 1px solid #ddd;background: #fff;padding-left:10px;">{{approveData.approveName}}-{{approveData.approveTime | filterTime}}</td>
          </tr>
          <tr style="border: 1px solid #ddd;height: 40px;line-height: 40px;">
            <td style="border: 1px solid #ddd;width: 20%;background: #eee;text-align:center;">集团法律处签字</td>
            <td colspan="3" style="border: 1px solid #ddd;background: #fff;padding-left:10px;"></td>
          </tr>
          </tbody>
        </table>
       </div>
        <!-- 合同信息end -->
      </div>
      <div class="record-btn" v-if="tableDateContract.contractStatus === 2">
        <el-button type="primary" @click="doPrint">打 印</el-button>
      </div>
      <!-- 备案记录start -->
      <div class="record-log">
        <el-row :gutter="20">
          <el-col :span="3"><span class="apply-name-color">备案记录:</span></el-col>
          <el-col :span="21">
            <el-steps direction="vertical" :active="1" :space="100">
              <el-step
                v-for="(item,index) in approveRecordList"
                :key="index"
                :status='fliteredStatus(item.approveResult)'>
                <span slot="title" class="step-span span-color span-first" v-if="item.approveResult === 0">{{item.applyName}}</span>
                <span slot="title" class="step-span span-color span-first" v-if="item.approveResult !== 0">{{item.approveName}}</span>
                <span slot="title" class="step-span span-color span-second" v-if="item.approveResult === 0">{{item.applyOrganization}}</span>
                <span slot="title" class="step-span span-color span-second" v-if="item.approveResult !== 0">{{item.approveOrganization}}</span>
                <span slot="title" class="step-span span-third" >
                  {{item.approveResult |filterResult}}
                </span>
                <span slot="title" class="step-span span-color span-last">{{item.approveTime |filterTime}}</span>
                <div slot="title" class="apply-option" v-if="item.approveOpinion">
                  审批意见:<span>{{item.approveOpinion}}</span>
                </div>
              </el-step>
            </el-steps>
          </el-col>
        </el-row>
      </div>
      <!-- 备案记录end -->
    </div>
    <!-- 详情表格 end -->
  </div>
</template>
<script>
import {contractInfo} from 'api/index'
import {formatDate} from 'common/js/date'
export default {
  data () {
    return {
      tableDateProject: {
        tenderProject: {}
      }, // 项目信息
      tableDateContract: {}, // 合同信息
      approveRecordList: [], // 备案记录
      approveData: {}, // 集团签字
      submitUnitData: {} // 项目单位签字
    }
  },
  computed: {},
  created () {
  },
  /** 方法集 */
  methods: {
    /** 备案记录-状态 */
    fliteredStatus (status) {
      if (status === 1) {
        return 'success'
      } else if (status === 2) {
        return 'error'
      } else if (status === 3) {
        return 'wait'
      }
    },
    /** 单条数据展示 */
    getcontractInfo () {
      contractInfo.getOne(this.$route.query.objectId).then((res) => {
        if (res) {
          this.tableDateContract = res.data.contractInfo
          this.tableDateProject = this.tableDateContract.bidInfoConfirm
          this.tableDateProject.tenderProject = this.tableDateContract.bidInfoConfirm.tenderProject
          this.approveRecordList = res.data.contractInfo.approveRecordList
          if (this.approveRecordList) {
            for (let i = 0; i < this.approveRecordList.length; i++) {
              // 项目单位
              if (this.approveRecordList[i].approveResult === 0) {
                this.submitUnitData = this.approveRecordList[i]
              }
              // 招标办
              if (this.approveRecordList[i].approveResult === 1) {
                this.approveData = this.approveRecordList[i]
              }
            }
          }
        }
      })
    },
    doPrint () {
      let subOutputRankPrint = document.getElementById('print')
      let newContent = subOutputRankPrint.innerHTML
      let oldContent = document.body.innerHTML
      document.body.innerHTML = newContent
      window.print()
      window.location.reload()
      document.body.innerHTML = oldContent
      return false
    }
  },
  /** 实例创建完成 */
  mounted () {
    this.getcontractInfo()
  },
  /** 过滤 */
  filters: {
    filterResult (val) {
      if (val === 1) {
        return '已登记'
      } else if (val === 2) {
        return '退回'
      } else if (val === 0) {
        return '发起申请'
      }
    },
    filterTime (val) {
      if (val) {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return ''
      }
    },
    filterProjectType (val) {
      if (val === 1) {
        return '工程'
      } else if (val === 2) {
        return '货物'
      } else if (val === 3) {
        return '服务'
      }
    }
  }
}
</script>
<style lang="less">
#contract-details-page {
  .table-box {
    background: #fff;
    margin: 0 20px;
    margin-bottom: 130px;
    padding: 30px 15px;
    .record-info {
      width: 1200px;
    }
  }
  /*审批记录css*/
  .record-log {
    width: 1200px;
    margin: 20px auto;
    padding: 20px;
    background: #fff;
    .step-span {
      padding-right: 20px;
      box-sizing: border-box;
      display: inline-block;
      font-weight: normal;
      font-size: 14px;
    }
    .span-first {
      width: 100px;
    }
    .span-second {
      width: 200px;
    }
    .span-last {
      float: right;
    }
    .apply-option {
      padding-top: 8px;
      font-weight: normal;
      color: #999;
      font-size: 14px;
      span {
        font-size: 14px;
        color: #999;
      }
    }
    .span-third {
      width: 180px;
    }
    .span-color {
      color: #999;
    }
  }
}
</style>
