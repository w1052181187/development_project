<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="contract-edit-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>合同备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/contract/contractrecord' }">合同备案编辑</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/contract/contractrecord/edit' }">{{this.$route.query.operate === 'edit'?'编辑':'查看'}}</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="goBack" class="go-back-btn">
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
              <td colspan="3">{{bidInfoConfirm.tenderProjectName}}</td>
            </tr>
            <tr>
              <td>项目编号</td>
              <td>{{bidInfoConfirm.codeUser}}</td>
              <td>项目类型</td>
              <td>{{bidInfoConfirm.tenderProjectType | filterType}}</td>
            </tr>
            <tr>
              <td>标段名称</td>
              <td colspan="3">{{bidInfoConfirm.sectionName}}</td>
            </tr>
             <tr>
              <td>项目单位</td>
              <td colspan="3">{{bidInfoConfirm.projectEnterpriseName}}</td>
            </tr>
             <tr>
              <td>需求方</td>
              <td colspan="3">{{bidInfoConfirm.requiresEnterpriseName}}</td>
            </tr>
             <tr>
              <td>招标代理机构</td>
              <td>{{bidInfoConfirm.agentEnterpriseName}}</td>
              <td>公告时间</td>
              <td>{{bidInfoConfirm.noticeTime | filterDate}}</td>
            </tr>
            <tr>
              <td>中标单位</td>
              <td>{{bidInfoConfirm.winnerBidderName}}</td>
              <td>中标金额(万元)</td>
              <td>{{bidInfoConfirm.winPrice}}</td>
            </tr>
            <tr>
              <td>开标时间</td>
              <td>{{bidInfoConfirm.bidOpenTime | filterDate}}</td>
              <td>公示时间</td>
              <td>{{bidInfoConfirm.publicityTime | filterDate}}</td>
            </tr>
          </tbody>
        </table>
        <!-- 合同信息start -->
        <el-form ref="contractInfoForm" :model="contractInfoForm" :rules="rules">
          <table class="table-cont">
            <tbody>
              <tr class="fisrt-tr">
                <td colspan="4"><b>合同信息</b></td>
              </tr>
              <tr>
                <td>质保金（万元）</td>
                <td>
                  <el-form-item prop="warrantyGold">
                    <el-input v-if="dialogEdit" v-model="contractInfoForm.warrantyGold"></el-input>
                    <span v-else>{{contractInfoForm.warrantyGold}}</span>
                  </el-form-item>
                </td>
                <td>质保期</td>
                <td>
                  <el-form-item prop="warrantyPeriod">
                    <el-input v-if="dialogEdit" v-model="contractInfoForm.warrantyPeriod"></el-input>
                    <span v-else>{{contractInfoForm.warrantyPeriod}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>合同价（万元）</td>
                <td>
                  <el-form-item prop="contractPrice">
                    <el-input v-if="dialogEdit" v-model="contractInfoForm.contractPrice"></el-input>
                    <span v-else>{{contractInfoForm.contractPrice}}</span>
                  </el-form-item>
                </td>
                <td>付款方式</td>
                <td>
                  <el-form-item prop="payMethod">
                    <el-input v-if="dialogEdit" v-model="contractInfoForm.payMethod"></el-input>
                    <span v-else>{{contractInfoForm.payMethod}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr v-if="bidInfoConfirm.tenderProjectType === 1">
                <td>项目经理</td>
                <td>
                  <el-form-item prop="projectManager">
                    <el-input v-if="dialogEdit" v-model="contractInfoForm.projectManager"></el-input>
                    <span v-else>{{contractInfoForm.projectManager}}</span>
                  </el-form-item>
                </td>
                <td>合同编号</td>
                <td>
                  <el-form-item prop="contractNo">
                    <el-input v-if="dialogEdit" v-model="contractInfoForm.contractNo"></el-input>
                    <span v-else>{{contractInfoForm.contractNo}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr v-if="bidInfoConfirm.tenderProjectType === 2">
                <td>供货期</td>
                <td>
                  <el-form-item prop="supplyPeriod">
                    <el-input v-if="dialogEdit" v-model="contractInfoForm.supplyPeriod"></el-input>
                    <span v-else>{{contractInfoForm.supplyPeriod}}</span>
                  </el-form-item>
                </td>
                <td>合同编号</td>
                <td>
                  <el-form-item prop="contractNo">
                    <el-input v-if="dialogEdit" v-model="contractInfoForm.contractNo"></el-input>
                    <span v-else>{{contractInfoForm.contractNo}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr v-if="bidInfoConfirm.tenderProjectType === 2">
                <td>规格、型号、数量</td>
                <td colspan="3">
                  <el-form-item prop="goodsExplain">
                    <el-input v-if="dialogEdit" v-model="contractInfoForm.goodsExplain"></el-input>
                    <span v-else>{{contractInfoForm.goodsExplain}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr v-if="bidInfoConfirm.tenderProjectType === 3">
                <td>服务期</td>
                <td>
                  <el-form-item prop="serverPeriod">
                    <el-input v-if="dialogEdit" v-model="contractInfoForm.serverPeriod"></el-input>
                    <span v-else>{{contractInfoForm.serverPeriod}}</span>
                  </el-form-item>
                </td>
                <td>项目负责人</td>
                <td>
                  <el-form-item prop="projectManager">
                    <el-input v-if="dialogEdit" v-model="contractInfoForm.projectManager"></el-input>
                    <span v-else>{{contractInfoForm.projectManager}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr v-if="bidInfoConfirm.tenderProjectType === 3">
                <td>合同编号</td>
                <td colspan="3">
                  <el-form-item prop="contractNo">
                    <el-input v-if="dialogEdit" v-model="contractInfoForm.contractNo"></el-input>
                    <span v-else>{{contractInfoForm.contractNo}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>结算方式及依据</td>
                <td colspan="3">
                  <el-form-item prop="basisSettlement">
                    <el-input v-if="dialogEdit" v-model="contractInfoForm.basisSettlement"></el-input>
                    <span v-else>{{contractInfoForm.basisSettlement}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>项目单位签字</td>
                <td colspan="3">{{projectSign}}</td>
              </tr>
              <tr>
                <td>集团招标办签字</td>
                <td colspan="3">{{tenderSign}}</td>
              </tr>
              <tr>
                <td>集团法律处签字</td>
                <td colspan="3">{{lawSign}}</td>
              </tr>
            </tbody>
          </table>
        </el-form>
        <!-- 合同信息end -->
        <div class="record-btn"  v-if="dialogEdit">
          <el-button type="success" @click="submitBtnClick('contractInfoForm')">提 交</el-button>
          <el-button type="primary" @click="saveBtnClick('contractInfoForm')">保 存</el-button>
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
                <span slot="title" class="step-span span-second span-color" v-if="item.approveResult === 0">{{item.applyOrganization}}</span>
                <span slot="title" class="step-span span-second span-color" v-if="item.approveResult !== 0">{{item.approveOrganization}}</span>
                <span slot="title" class="step-span span-third" >
                  {{item.approveResult | filterResult}}
                </span>
                <span slot="title" class="step-span span-color span-last">{{item.approveTime | filterApproveTime}}</span>
                <div slot="title" class="apply-option" v-if="item.approveOpinion">
                  备案意见:<span>{{item.approveOpinion}}</span>
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
    var validatePrice = (rule, value, callback) => {
      if (value && (!(/^\d{1,14}(\.\d{1,6})?$/).test(value))) {
        callback(new Error('质保金只能输入小数点前不超过14位且小数点后不超过6位的数字'))
      } else {
        callback()
      }
    }
    return {
      bidInfoConfirm: {},
      contractInfoForm: {},
      rules: {
        warrantyGold: [
          {required: true, message: '请填写质保金', trigger: 'blur'},
          {validator: validatePrice}
        ],
        warrantyPeriod: [
          {required: true, message: '请填写质保期', trigger: 'blur'},
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
        ],
        contractPrice: [
          {required: true, message: '请填写合同价', trigger: 'blur'},
          {validator: validatePrice}
        ],
        payMethod: [
          {required: true, message: '请填写付款方式', trigger: 'blur'},
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
        ],
        projectManager: [
          {required: true, message: '请填写项目经理', trigger: 'blur'},
          { min: 1, max: 20, message: '请输入1~20个字符', trigger: 'blur' }
        ],
        contractNo: [
          {required: true, message: '请填写合同编号', trigger: 'blur'},
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
        ],
        basisSettlement: [
          {required: true, message: '请填写结算依据', trigger: 'blur'},
          { min: 1, max: 100, message: '请输入1~100个字符', trigger: 'blur' }
        ],
        supplyPeriod: [
          {required: true, message: '请填写供货期', trigger: 'blur'},
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
        ],
        goodsExplain: [
          {required: true, message: '请填写规格、型号、数量（货物）', trigger: 'blur'},
          { min: 1, max: 500, message: '请输入1~500个字符', trigger: 'blur' }
        ],
        serverPeriod: [
          {required: true, message: '请填写服务期', trigger: 'blur'},
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
        ]
      },
      approveRecordList: [], // 备案记录
      dialogEdit: false,
      projectSign: '', // 项目单位签字
      tenderSign: '', // 集团招标办签字
      lawSign: '' // 集团法律处签字
    }
  },
  computed: {},
  created () {
  },
  /** 方法集 */
  methods: {
    /** 提交 */
    submitBtnClick (val) {
      this.$refs[val].validate((vaild) => {
        if (vaild) {
          this.contractInfoForm.flag = 2
          contractInfo.save(this.contractInfoForm).then(res => {
            this.goBack()
          })
        } else {
          return false
        }
      })
    },
    /** 保存 */
    saveBtnClick (val) {
      this.$refs[val].validate((vaild) => {
        if (vaild) {
          this.contractInfoForm.flag = 1
          contractInfo.save(this.contractInfoForm).then(res => {
            this.goBack()
          })
        } else {
          return false
        }
      })
    },
    /** 设置审批状态颜色 */
    fliteredStatus (status) {
      if (status === 1) {
        return 'success'
      } else if (status === 2) {
        return 'error'
      } else if (status === 0) {
        return 'wait'
      } else if (status === 3) {
        return 'finish'
      }
    },
    goBack () {
      this.$router.push({path: '/admin/contract/contractrecord', query: {currentPage: this.$route.query.currentPage, pageNo: this.$route.query.pageNo, pageSize: this.$route.query.pageSize}})
    },
    getById () {
      contractInfo.getOne(this.$route.query.objectId).then(res => {
        this.contractInfoForm = res.data.contractInfo
        if (this.contractInfoForm) {
          this.bidInfoConfirm = this.contractInfoForm.bidInfoConfirm
          if ((this.contractInfoForm.contractStatus === 0 || this.contractInfoForm.contractStatus === 3)
            && Object.is(this.$route.query.operate, 'edit')) {
            this.dialogEdit = true
          } else {
            this.dialogEdit = false
          }
          if (this.bidInfoConfirm) {
            if (this.bidInfoConfirm.tenderProject) {
              let tenderProject = this.bidInfoConfirm.tenderProject
              this.bidInfoConfirm.tenderProjectName = tenderProject.tenderProjectName
              this.bidInfoConfirm.codeUser = tenderProject.codeUser
              this.bidInfoConfirm.tenderProjectType = tenderProject.tenderProjectType
            }
          }
          // 审批记录
          this.approveRecordList = this.contractInfoForm.approveRecordList
          if (this.approveRecordList) {
            this.approveRecordList.map(item => {
              if (item.approveResult === 0) {
                this.projectSign = item.applyName + '  ' + formatDate(new Date(item.approveTime), 'yyyy-MM-dd hh:mm:ss')
              }
              if (item.approveResult === 1) {
                this.tenderSign = item.approveName + '  ' + formatDate(new Date(item.approveTime), 'yyyy-MM-dd hh:mm:ss')
              }
            })
          }
        }
      })
    }
  },
  /** 实例创建完成 */
  mounted () {
    this.getById()
  },
  /** 过滤 */
  filters: {
    filterType (val) {
      if (val === 1) {
        return '工程'
      } else if (val === 2) {
        return '货物'
      } else if (val === 3) {
        return '服务'
      }
    },
    filterDate (val) {
      if (val) {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return ''
      }
    },
    filterApproveTime (val) {
      if (val) {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return ''
      }
    },
    filterResult (val) {
      if (val === 0) {
        return '发起申请'
      } else if (val === 1) {
        return '已登记'
      } else if (val === 2) {
        return '退回'
      } else if (val === 3) {
        return '终止'
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
        td:nth-child(even) {
          .el-form-item {
            .el-form-item__content {
              text-align: left;
              padding-left: 10px;
            }
          }
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
        .el-form-item__error {
          display: none;
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
}
</style>
