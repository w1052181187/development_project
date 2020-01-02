<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="check-card">
    <div class="check-head">
      <h2>{{this.$route.query.year}}年投资建议计划</h2>
      <span class="span-tit span-l">{{this.$route.query.enterpriseName}}</span>
      <span class="span-tit span-r">专项资金风险控制比例：{{riskRatio}}%</span>
    </div>
    <el-row :gutter="20">
      <el-col :span="8" v-for="(item, index) in classifyList" :key="index" class="card-col">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span class="card-title">{{item.capitalSourceName? item.capitalSourceName:'工程建设'}}</span>
          </div>
          <div class="text item">
            <el-form label-width="140px" class="demo-ruleForm">
              <el-form-item label="可使用额度(万元):">
                <span :class="{'color-red': item.surplusUsageQuota < 0}">{{item.availableQuota}}</span>
              </el-form-item>
              <el-form-item label="已使用额度(万元):">
                <span :class="{'color-red': item.surplusUsageQuota < 0}">{{item.usedQuota}}</span>
              </el-form-item>
              <el-form-item label="剩余使用额度(万元):">
                <span :class="{'color-red': item.surplusUsageQuota < 0}">{{item.surplusUsageQuota}}</span>
              </el-form-item>
              <el-form-item label="状态:">
                <span>{{item.status | filterStats}}</span>
              </el-form-item>
            </el-form>
          </div>
          <div class="bottom clearfix">
            <el-button type="primary" class="button" @click="cardBtnClick(item, 'edit')">编辑</el-button>
            <el-button type="info" class="button" @click="cardBtnClick(item, 'look')">查看</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import {investmanage, constantMaintenance, agendaMessage} from 'api/index'
// 实例
export default {
  data () {
    return {
      classifyList: [], // 卡片list
      riskRatio: null, // 专项资金风险控制比例
      flag: false
    }
  },
  computed: {
  },
  watch: {
  },
  created () {
    this.flag = this.$route.query.flag
  },
  filters: {
    filterStats (val) {
      if (val === 0) {
        return '未提交'
      } else if (val === 1) {
        return '已提交'
      } else if (val === 2) {
        return '已核查'
      } else if (val === 3) {
        return '驳回'
      }
    }
  },
  methods: {
    /** 卡片操作 */
    cardBtnClick (item, type) {
      if (Object.is(type, 'edit')) {
        item.checkEdit = 'checkEdit'
        this.$emit('cardToMain', item)
      } else if (Object.is(type, 'look')) {
        this.$router.push({path: '/admin/planmanage/investmanage/investlook', query: {operate: 'manage', code: item.code, year: this.$route.query.year}})
      }
    },
    getClassifyList () {
      let query = {
        investmentProposalSchemeCode: this.$route.query.code,
        operate: 6
      }
      investmanage.getClassifyList(query).then((res) => {
        this.classifyList = res.data.capitalSourceClassifyList
        if (this.flag === true) {
          agendaMessage.updateConsumedStatusByrealatedCode(this.$route.query.code).then((res) => {
          })
        }
      })
    },
    /** 获取专项资金风险控制比例  */
    getRiskRatio () {
      constantMaintenance.getList({
        name: '专项资金风险控制比例',
        type: 1
      }).then((res) => {
        this.riskRatio = res.data.constantMaintenanceList[0].description
      })
    }
  },
  // 实例创建完成
  mounted () {
    this.getClassifyList()
    this.getRiskRatio()
  },
  components: {
  }
}
</script>
<style lang="less">
  #check-card {
    .check-head {
      margin-bottom: 20px;
      border-bottom: 1px solid #ddd;
      overflow: hidden;
      .span-tit {
        position: relative;
        display: inline-block;
        padding-bottom: 5px;
      }
      .span-l {
        float: left;
      }
      .span-r {
        float: right;
      }
    }
    .card-col {
      margin-bottom: 20px;
    }
    .box-card {
      .card-title {
        font-weight: 800;
        font-size: 16px;
      }
      .el-card__header {
        padding: 5px 20px;
      }
      .el-form-item {
        margin-bottom: 0;
      }
      .text .el-form-item__content {
        text-align: left;
      }
      .bottom {
        padding-top: 10px;
        border-top: 1px solid #eee;
      }
      .color-red {
        color: red;
      }
    }
  }
</style>
