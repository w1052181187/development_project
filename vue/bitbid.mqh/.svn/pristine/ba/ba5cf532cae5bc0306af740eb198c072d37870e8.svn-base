<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="papply-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>计划管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/planmanage/bidpurcheck' }">招采建议计划审核</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/planmanage/bidpurcheck/bidpurapply' }">审核</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <div class="papply-cont">
      <div class="papply-cont-tit">
        <h3>{{year}}年招标项目计划表</h3>
        <el-row :gutter="20">
          <el-col :span="12" style="text-align: left;">
            <span class="pre-dowm-l">单位名称:</span><span>{{enterpriseName}}</span>
          </el-col>
          <el-col :span="12" style="text-align: right;">
            <span class="pre-dowm-l">投资单位:</span><span>万元</span>
          </el-col>
        </el-row>
      </div>
      <!-- 表格区域start -->
      <el-table
        :data="tableData"
        border
        style="width: 100%">
        <el-table-column
          type="index"
          label="序号"
          align="center"
          :index="indexMethod"
          width="55">
        </el-table-column>
        <el-table-column
          prop="projectPlanName"
          label="招标计划名称"
          align="left">
        </el-table-column>
        <el-table-column
          prop="projectName"
          label="项目名称"
          align="left">
          <template slot-scope="scope">
            <el-tooltip effect="dark" :content="item.projectName" placement="top" v-for="(item,index) in scope.row.projectList" :key="index" v-if="item.projectName">
              <p class="dresult-span">{{item.projectName}}</p>
            </el-tooltip>
            <p class="dresult-blank-span" v-else></p>
          </template>
        </el-table-column>
        <el-table-column
          prop="content"
          label="项目主要内容"
          align="left">
          <template slot-scope="scope">
            <el-tooltip effect="dark" :content="item.content" placement="top" v-for="(item,index) in scope.row.projectList" :key="index" v-if="item.content">
              <p class="dresult-span">{{item.content}}</p>
            </el-tooltip>
            <p class="dresult-blank-span" v-else></p>
          </template>
        </el-table-column>
        <el-table-column
          prop="currentPlanQuantities"
          label="工程量"
          align="left">
          <template slot-scope="scope">
            <p v-for="(item,index) in scope.row.projectList" :key="index" class="dresult-span" v-if="item.currentPlanQuantities">{{item.currentPlanQuantities}}</p>
            <p class="dresult-blank-span" v-else></p>
          </template>
        </el-table-column>
        <el-table-column
          prop="capitalSourceName"
          label="资金来源"
          align="left">
          <template slot-scope="scope">
            <el-tooltip effect="dark" :content="item.capitalSourceName?item.capitalSourceName:capitalSourceName" v-for="(item,index) in scope.row.projectList" :key="index" v-if="item.capitalSource==0 || item.capitalSourceName" placement="top">
              <p class="dresult-span" >{{item.capitalSourceName?item.capitalSourceName:capitalSourceName}}</p>
            </el-tooltip>
            <p class="dresult-blank-span" v-else></p>
          </template>
        </el-table-column>
        <el-table-column
          prop="planTotalAmount"
          label="计划金额"
          align="right">
        </el-table-column>
        <el-table-column
          prop="planLimit"
          label="计划工期"
          align="left"
          :formatter="filterPlanLimit">
        </el-table-column>
        <el-table-column
          prop="bidTime"
          label="拟招标时间"
          align="left">
        </el-table-column>
        <el-table-column
          prop="executeTime"
          label="拟实施时间"
          align="left">
        </el-table-column>
        <el-table-column
          prop="bidType"
          label="拟招标方式"
          align="left"
          :formatter="filterBidType">
        </el-table-column>
        <el-table-column
          prop="projectType"
          label="项目类型"
          align="left"
          :formatter="filterProjectType">
        </el-table-column>
        <el-table-column
          prop="enterpriseName"
          label="需求方"
          align="left">
        </el-table-column>
        <el-table-column
          prop="isAgain"
          label="是否多次招标"
          align="left">
          <template slot-scope="scope">
            <el-select v-model="scope.row.isAgain" placeholder="请选择" @change="changeSwitch(scope.row)">
              <el-option
                v-for="item in tableOption"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column
          prop="remark"
          label="备注"
          align="left">
        </el-table-column>
      </el-table>
      <!-- 表格区域end -->
    </div>

    <!-- 审批结果start -->
    <div class="papply-shenpi">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-row>
          <el-col :span="6">
            <el-form-item label="审批结果:" prop="approveResult">
              <el-select v-model="ruleForm.approveResult" placeholder="请选择" style="width: 300px">
                <el-option
                  v-for="item in stateList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="审批意见:" v-if="ruleForm.approveResult !== 1" prop="approveOpinion">
              <el-input type="textarea" v-model="ruleForm.approveOpinion"  style="width: 300px;">{{ruleForm.approveResult}}</el-input>
            </el-form-item>
            <el-form-item label="审批意见:" v-if="ruleForm.approveResult === 1">
              <el-input type="textarea" v-model="ruleForm.approveOpinion"  style="width: 300px;">{{ruleForm.approveResult}}</el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">确定</el-button>
          <el-button @click="$router.go(-1)">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!-- 审批结果end -->
  </div>
</template>
<script>
import {formatDate} from 'common/js/date.js'
import {planproject, plansubmit, investmanage} from 'api/index'
// 实例
export default {
  data () {
    return {
      year: '',
      enterpriseName: '',
      capitalSourceName: '',
      tableOption: [ // 是否多次招标
        {
          label: '是',
          value: 1
        },
        {
          label: '否',
          value: 0
        }
      ],
      stateList: [
        {
          label: '通过',
          value: 1
        },
        {
          label: '退回',
          value: 2
        }
      ],
      yesOrNoId: 0,
      tableData: [
        {projectName: '项目'}
      ],
      currentPage: 1,
      ruleForm: {
        approveResult: '',
        approveOpinion: ''
      },
      rules: {
        approveResult: [
          { required: true, message: '请选择审批状态', trigger: 'change' }],
        approveOpinion: [
          { required: true, message: '请输入审批意见', trigger: 'change' }]
      }
    }
  },
  created () {
    this.getCapitalSource()
  },
  methods: {
    /** 进行操作后返回当前页 */
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 格式化 */
    // 采购方式
    filterBidType (row, column) {
      let bidType = row.bidType
      if (bidType === 1) {
        return '公开招标'
      } else if (bidType === 2) {
        return '邀请招标'
      } else if (bidType === 3) {
        return '谈判采购'
      } else if (bidType === 4) {
        return '询比采购'
      } else if (bidType === 5) {
        return '竞价采购'
      } else if (bidType === 6) {
        return '直接采购'
      } else if (bidType === 7) {
        return '框架协议'
      }
    },
    // 项目类型
    filterProjectType (row, column) {
      let project = row.projectType
      if (project === 1) {
        return '工程'
      } else if (project === 2) {
        return '货物'
      } else if (project === 3) {
        return '服务'
      }
    },
    /** 计划工期 */
    filterPlanLimit (row, column) {
      let planLimit = row.planLimit
      if (planLimit !== '' && planLimit !== null) {
        return planLimit + '个月'
      }
    },
    /** 提交 */
    submitForm (data) {
      this.$refs[data].validate((vaild) => {
        if (vaild) {
          this.ruleForm.approveName = this.$store.getters.authUser.userName
          this.ruleForm.approveOrganization = this.$store.getters.authUser.enterpriseName
          this.ruleForm.relatedCode = this.$route.query.code
          this.ruleForm.approveType = 2
          planproject.saveApprovalRecords(this.ruleForm).then(() => {
            this.$router.push({path: '/admin/planmanage/bidpurcheck'})
          })
        } else {
          return false
        }
      })
    },
    /** 列表数据展示 */
    getPlanProject () {
      this.year = this.$route.query.year
      this.enterpriseName = this.$route.query.enterpriseName
      let obj = {
        annualProjectPlanCode: this.$route.query.code,
        projectPlanTypeList: '1,2,4'
      }
      plansubmit.getProjectPlan(obj).then((res) => {
        let arr = res.data.projectPlanList.list
        if (arr.length > 0) {
          arr.map((item, index) => {
            let date1 = new Date(item.bidStartTime)
            let date2 = new Date(item.bidEndTime)
            let date3 = new Date(item.executeStartTime)
            let date4 = new Date(item.executeEndTime)
            arr[index].bidTime = `${formatDate(date1, 'yyyy-MM-dd')}--${formatDate(date2, 'yyyy-MM-dd')}`
            arr[index].executeTime = `${formatDate(date3, 'yyyy-MM-dd')}--${formatDate(date4, 'yyyy-MM-dd')}`
          })
        }
        this.tableData = arr
      })
    },
    changeSwitch (row) {
      planproject.update(row).then((res) => {
        this.getPlanProject()
      })
    },
    /** 查询工程建设项目资金来源 */
    getCapitalSource () {
      this.capitalSourceName = ''
      let query = {
        classify: 2,
        isAvailableFunds: 1
      }
      investmanage.getCapitalSourceList(query).then(res => {
        let capitalSourceList = res.data.capitalSourceList
        capitalSourceList.map(capitalSource => {
          this.capitalSourceName += capitalSource.name + '、'
        })
        this.capitalSourceName = this.capitalSourceName.substring(0, this.capitalSourceName.length - 1)
      })
    }
  },
  computed: {},
  // 实例创建完成
  mounted () {
    this.getPlanProject()
  }
}
</script>
<style lang="less">
#papply-page {
  /*表格区域css*/
  .papply-cont {
    margin: 20px;
    padding: 20px;
    background: #fff;
    .papply-cont-tit {
      padding-bottom: 5px;
    }
    .pre-dowm-l {
      color: #999;
      padding-right: 10px;
    }
    .el-table__body .el-table__row {
      td:nth-child(3),
      td:nth-child(4),
      td:nth-child(5),
      td:nth-child(6){
        div.cell {
          padding:0;
        }
        p {
          margin: 0;
          padding: 10px;
          overflow: hidden;
          white-space: nowrap;
          text-overflow: ellipsis;
        }
        .dresult-blank-span {
          height: 22px;
        }
        p:not(:last-child) {
          border-bottom: 1px solid #eee;
        }
      }
    }
  }
  /*审批结果css*/
  .papply-shenpi {
    margin: 20px 20px 130px;
    padding: 20px;
    background: #fff;
  }
  .el-form-item {
    margin-left: -30px;
  }
}
</style>
