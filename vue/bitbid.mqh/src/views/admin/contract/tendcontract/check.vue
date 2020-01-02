<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="contract-edit-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>合同备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/contract/contractcheck' }">登记合同</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/contract/contractcheck/check' }">登记</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>

    <!-- 详情表格 start -->
    <div class="table-box">
      <div class="title-box">
        <h2>太原煤炭气化（集团）有限责任公司合同备案表</h2>
      </div>
      <div class="record-info">
        <table class="table-cont">
          <tbody>
            <tr class="fisrt-tr">
              <td colspan="4"><b>项目信息</b></td>
            </tr>
            <tr>
              <td>项目名称</td>
              <td colspan="3">{{tableDate.tenderProject.tenderProjectName}}</td>
            </tr>
            <tr>
              <td>项目编号</td>
              <td>{{tableDate.tenderProject.codeUser}}</td>
              <td>项目类型</td>
              <td>{{tableDate.tenderProject.tenderProjectType |filterProjectType }}</td>
            </tr>
            <tr>
              <td>标段名称</td>
              <td colspan="3">{{tableDate.sectionName}}</td>
            </tr>
             <tr>
              <td>项目单位</td>
               <td colspan="3">{{tableDate.projectEnterpriseName}}</td>
            </tr>
             <tr>
              <td>需求方</td>
               <td colspan="3">{{tableDate.requiresEnterpriseName}}</td>
            </tr>
             <tr>
              <td>招标代理机构</td>
               <td>{{tableDate.agentEnterpriseName}}</td>
              <td>公告时间</td>
               <td>{{tableDate.noticeTime |filterTime}}</td>
            </tr>
            <tr>
              <td>中标单位</td>
              <td>{{tableDate.winnerBidderName}}</td>
              <td>中标金额(万元)</td>
              <td>{{tableDate.winPrice}}</td>
            </tr>
            <tr>
              <td>开标时间</td>
              <td>{{tableDate.bidOpenTime |filterTime}}</td>
              <td>公示时间</td>
              <td>{{tableDate.publicityTime |filterTime}}</td>
            </tr>
          </tbody>
        </table>
        <!-- 合同信息start -->
        <el-form ref="contractInfoForm" :model="contractInfoForm" :rules="rules" label-width="100px">
          <table class="table-cont">
            <tbody>
          <tr class="fisrt-tr">
            <td colspan="4"><b>合同信息</b></td>
          </tr>
          <tr>
            <td>质保金（万元）</td>
            <td>{{contractInfoForm.warrantyGold}}</td>
            <td>质保期</td>
            <td>{{contractInfoForm.warrantyPeriod}}</td>
          </tr>
          <tr>
            <td>合同价（万元）</td>
            <td>{{contractInfoForm.contractPrice}}</td>
            <td>付款方式</td>
            <td>{{contractInfoForm.payMethod}}</td>
          </tr>
          <tr v-if="tableDate.tenderProject.tenderProjectType === 1">
            <td>项目经理</td>
            <td>{{contractInfoForm.projectManager}}</td>
            <td>合同编号</td>
            <td>{{contractInfoForm.contractNo}}</td>
          </tr>
          <tr v-if="tableDate.tenderProject.tenderProjectType === 2">
            <td>供货期</td>
            <td>{{contractInfoForm.supplyPeriod}}</td>
            <td>合同编号</td>
            <td>{{contractInfoForm.contractNo}}</td>
          </tr>
          <tr v-if="tableDate.tenderProject.tenderProjectType === 2">
            <td>规格、型号、数量</td>
            <td colspan="3">{{contractInfoForm.goodsExplain}}</td>
          </tr>
          <tr v-if="tableDate.tenderProject.tenderProjectType === 3">
            <td>服务期</td>
            <td>{{contractInfoForm.serverPeriod}}</td>
            <td>项目负责人</td>
            <td>{{contractInfoForm.projectName}}</td>
          </tr>
          <tr v-if="tableDate.tenderProject.tenderProjectType === 3">
            <td>合同编号</td>
            <td colspan="3">{{contractInfoForm.contractNo}}</td>
          </tr>
          <tr>
            <td>结算方式及依据</td>
            <td colspan="3">{{contractInfoForm.basisSettlement}}</td>
          </tr>
          <tr>
            <td>项目单位签字</td>
            <td colspan="3">{{submitUnitData.applyName}}-{{submitUnitData.approveTime | filterTime}}</td>
          </tr>
          <tr>
            <td>集团招标办签字</td>
            <td colspan="3">{{approveData.approveName}}-{{approveData.approveTime | filterTime}}</td>
          </tr>
          <tr>
            <td>集团法律处签字</td>
            <td colspan="3"></td>
          </tr>
          </tbody>
          </table>
          <el-form-item label="登记结果:" prop="approveResult" class="shenpi-item" v-if="contractInfoForm.contractStatus === 1">
            <el-select v-model="contractInfoForm.approveResult" placeholder="请选择">
              <el-option
                v-for="item in stateList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="备注:" prop="approveOpinion" class="shenpi-item"  v-if="contractInfoForm.approveResult === 2">
            <el-input type="textarea" v-model="contractInfoForm.approveOpinion" :rows="3"></el-input>
          </el-form-item>
        </el-form>
        <!-- 合同信息end -->
        <div class="record-btn" v-if="contractInfoForm.contractStatus === 1">
          <el-button type="primary" @click="submitBtnClick('contractInfoForm')">提 交</el-button>
          <el-button type="info" @click="$router.go(-1)">取 消</el-button>
        </div>
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
      tableDate: {
        tenderProject: {}
      },
      contractInfoForm: {},
      rules: {
        approveResult: [
          { required: true, message: '请选择审批状态', trigger: 'change' }],
        approveOpinion: [
          {required: true, message: '请输入审批意见', trigger: 'change'}]
      },
      approveRecordList: [], // 备案记录
      approveData: {}, // 集团招标办签字
      submitUnitData: {}, // 项目单位签字
      stateList: [
        {
          label: '登记',
          value: 1
        },
        {
          label: '退回',
          value: 2
        }
      ]
    }
  },
  computed: {},
  created () {
  },
  /** 方法集 */
  methods: {
    /** 提交 */
    submitBtnClick (from) {
      this.$refs[from].validate((valid) => {
        if (valid) {
          contractInfo.save(this.contractInfoForm).then((res) => {
            this.$router.push({path: '/admin/contract/contractcheck'})
          })
        }
      })
    },
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
        if (res.data.contractInfo) {
          this.contractInfoForm = res.data.contractInfo
          this.tableDate = res.data.contractInfo.bidInfoConfirm
          this.tableDate.tenderProject = res.data.contractInfo.bidInfoConfirm.tenderProject
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
        return formatDate(date, 'yyyy-MM-dd ')
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
#contract-edit-page {
  .table-box {
    background: #fff;
    margin: 0 20px;
    margin-bottom: 130px;
    padding: 30px 15px;
    .record-info {
      width: 1200px;
      margin: 0 auto;
      .table-cont {
        width: 100%;
        margin-bottom: 30px;
        border-collapse: collapse;
        border: 1px solid #ddd;
        tr, td {
          border: 1px solid #ddd;
        }
        tr {
          height: 40px;
          line-height: 40px;
        }
        tr .el-form-item {
          margin-bottom: 0;
        }
        td:nth-child(odd) {
          width: 20%;
          background: #eee;
        }
        .fisrt-tr td{
          background: #fff;
        }
        .fisrt-tr b {
          font-size: 18px;
        }
      }
    }
  }
  /*审批记录css*/
  .record-log {
    width: 1200px;
    margin: 20px auto;
    padding: 20px;
    background: #fff;
    border-top: 1px solid #ddd;
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
  .table-form-item {
    .el-form-item__content {
      margin-left: 0 !important;
    }
  }
  .shenpi-item {
    .el-form-item__content {
      text-align: left;
    }
  }
}
</style>
