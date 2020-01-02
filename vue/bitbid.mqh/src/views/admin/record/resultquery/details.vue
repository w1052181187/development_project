<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="querydetails-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招标备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/record/resultquery' }">招标结果查询</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/record/resultquery/details' }">招标结果查询</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="goBack" class="go-back-btn">
        返回
      </el-button>
    </div>

    <!-- 项目基本信息start -->
    <div class="project-box">
      <div class="title-box">
        <span>项目基本信息</span>
      </div>
      <div class="project-info">
        <el-form :model="proInfoForm" label-width="130px" class="demo-ruleForm">
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="项目名称:" prop="projectName">
                <span>{{proInfoForm.projectName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="项目编号:" prop="codeUser">
                <span>{{proInfoForm.codeUser}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="项目类型:" prop="tenderProjectType">
                <span>{{proInfoForm.tenderProjectType | filterType}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="标段名称:" prop="sectionName">
                <span>{{proInfoForm.sectionName}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="项目单位:" prop="executeEnterpriseName">
                <span>{{proInfoForm.executeEnterpriseName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="需求方:" prop="enterpriseName">
                <span>{{proInfoForm.enterpriseName}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="招标代理机构:" prop="entrustEnterpriseName">
                <span>{{proInfoForm.entrustEnterpriseName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="公告时间:" prop="approvePassTime">
                <span>{{proInfoForm.approvePassTime | filterTime}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="开标时间:" prop="bidOpenTime">
                <span>{{proInfoForm.bidOpenTime | filterTime}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="候选人公示时间:" prop="publicityTime">
                <span>{{proInfoForm.publicityTime | filterTime}}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </div>
    <!-- 项目基本信息end -->

    <!-- 中标结果start -->
    <div class="winbid-box">
      <div class="title-box">
        <span>中标结果</span>
      </div>
      <div class="winbid-form">
        <el-form :model="winbidForm" label-width="150px" class="demo-ruleForm">
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="中标单位名称:" prop="bidderName">
                <span>{{winbidForm.bidderName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="中标价（万元）:" prop="bidPrice">
                <span>{{winbidForm.bidPrice}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="质保金（万元）:" prop="warrantyGold">
                <span>{{winbidForm.warrantyGold}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="质保期:" prop="warrantyPeriod">
                <span>{{winbidForm.warrantyPeriod}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="付款方式:" prop="payMethod">
                <span>{{winbidForm.payMethod}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="项目经理:" prop="projectManager" v-if="tenderProjectType === 1">
                <span>{{winbidForm.projectManager}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="结算依据:" prop="basisSettlement" v-if="tenderProjectType === 1">
                <span>{{winbidForm.basisSettlement}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="规格、型号、数量:" prop="goodsExplain" v-if="tenderProjectType === 2">
                <span>{{winbidForm.goodsExplain}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="供货期:" prop="supplyPeriod" v-if="tenderProjectType === 2">
                <span>{{winbidForm.supplyPeriod}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="服务期:" prop="serverPeriod" v-if="tenderProjectType === 3">
                <span>{{winbidForm.serverPeriod}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="确定状态:" prop="isConfirm">
                <span v-if="confirmStatus">{{winbidForm.isConfirm | filterConfirm}}</span>
                <el-button type="isConfirm" size="small" @click="clickInfoConfirm" v-if="!confirmStatus">请确认</el-button>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="确认时间:" prop="confirmTime" v-if="confirmStatus">
                <span>{{winbidForm.confirmTime | filterTime}}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </div>
    <!-- 中标结果end -->
  </div>
</template>
<script>
import {resultquery, resnotice, candNotice, bidfile, notice} from 'api/index'
import {formatDate} from 'common/js/date'
export default {
  data () {
    return {
      tenderProjectType: null, // 招标项目类型（1.工程 2.货物 3.服务）
      confirmStatus: false,
      proInfoForm: {}, // 项目基本信息-表单
      winbidForm: {} // 中标结果表单
    }
  },
  computed: {},
  created () {
  },
  /** 方法集 */
  methods: {
    /*
     * 返回
     */
    goBack () {
      this.$router.push({path: '/admin/record/resultquery', query: {currentPage: this.$route.query.currentPage, pageNo: this.$route.query.pageNo, pageSize: this.$route.query.pageSize}})
    },
    // 确认
    clickInfoConfirm () {
      this.$confirm('是否确认该招标结果与合同内容相符合', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.confirmStatus = true
        // 调用接口
        this.updateResultNotice()
      }).catch(() => {
        this.confirmStatus = false
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    updateResultNotice () {
      this.$set(this.winbidForm, 'isConfirm', 1)
      this.$set(this.winbidForm, 'confirmTime', new Date())
      if (this.winbidForm.bidWinner) {
        this.$set(this.winbidForm.bidWinner, 'isConfirm', 1)
        this.$set(this.winbidForm.bidWinner, 'confirmTime', new Date())
      }
      resnotice.updateResultNotice(this.winbidForm).then(() => {
        this.getTenderProject()
      })
    },
    /** 查询招标项目信息 */
    getTenderProject () {
      let query = {
        tenderResultQueryFlag: 'true',
        objectId: this.$route.query.tenderProjectId
      }
      resultquery.getTenderResult(query).then((res) => {
        let data = res.data.tenderProjectList
        if (data.list) {
          this.proInfoForm = data.list[0]
          this.tenderProjectType = this.proInfoForm.tenderProjectType
          let selectApply = this.proInfoForm.selectApply
          if (selectApply) {
            this.proInfoForm.projectName = selectApply.projectName
            this.proInfoForm.codeUser = selectApply.codeUser
          }
          let drawLotsResult = this.proInfoForm.selectApply.drawLotsResult
          if (drawLotsResult) {
            this.proInfoForm.entrustEnterpriseName = drawLotsResult.entrustEnterpriseName
          }
          let projectPlan = this.proInfoForm.selectApply.projectPlan
          if (projectPlan) {
            this.proInfoForm.enterpriseName = projectPlan.enterpriseName
            this.proInfoForm.executeEnterpriseName = projectPlan.executeEnterpriseName
          }
          /** 查询中标公示结果信息 */
          this.getResultNotice()
          /** 查询候选人公示信息:候选人公示时间 */
          this.getPublicity()
          /** 查询招标文件信息:开标时间 */
          this.getTenderDoc()
        }
      })
    },
    /** 查询中标公示结果信息 */
    getResultNotice () {
      resnotice.getById(this.$route.query.resultNoticeId).then((res) => {
        let resultNotice = res.data.resultNotice
        if (resultNotice) {
          this.proInfoForm.sectionName = resultNotice.sectionName
          // 公告时间
          this.$set(this.proInfoForm, 'approvePassTime', resultNotice.approvePassTime)
          this.winbidForm = resultNotice
          if (resultNotice.bidWinner) {
            if (resultNotice.bidWinner.winCandidate) {
              this.$set(this.winbidForm, 'bidPrice', resultNotice.bidWinner.winCandidate.bidPrice)
              this.$set(this.winbidForm, 'bidderName', resultNotice.bidWinner.winCandidate.bidderName)
            }
          }
          if (this.winbidForm.isConfirm === 1) {
            this.confirmStatus = true
          } else {
            this.confirmStatus = false
          }
        }
      })
    },
    /** 查询候选人公示信息:候选人公示时间 */
    getPublicity () {
      let query = {
        noticeCode: this.noticeCode,
        sectionCode: this.$route.query.sectionCode
      }
      candNotice.getOne(query).then((res) => {
        let publicity = res.data.publicity
        if (publicity) {
          this.$set(this.proInfoForm, 'publicityTime', publicity.publicityTime)
        } else {
          return false
        }
      })
    },
    /** 查询招标文件信息:开标时间 */
    getTenderDoc () {
      bidfile.getByCode(this.$route.query.noticeCode, this.$route.query.sectionCode).then((res) => {
        let tenderDoc = res.data.tenderDoc
        if (tenderDoc) {
          this.$set(this.proInfoForm, 'bidOpenTime', tenderDoc.bidOpenTime)
        } else {
          return false
        }
      })
    }
  },
  /** 实例创建完成 */
  mounted () {
    this.getTenderProject()
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
    filterTime (val) {
      if (val) {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return ''
      }
    },
    filterConfirm (val) {
      if (val === 1) {
        return '已确认'
      } else if (val === 0) {
        return '未确认'
      }
    }
  }
}
</script>
<style lang="less">
#querydetails-page {
  .project-box, .winbid-box{
    margin: 20px;
    background: #fff;
    .title-box {
      height: 40px;
      background: #F7F7F9;
      line-height: 40px;
      text-align: left;
      padding-left: 20px;
      font-weight: 800;
    }
    .project-info, .winbid-form {
      .el-form-item__content {
        text-align: left;
      }
    }
  }
  .winbid-box {
    padding-bottom: 30px;
    margin-bottom: 120px;
  }
}
</style>
