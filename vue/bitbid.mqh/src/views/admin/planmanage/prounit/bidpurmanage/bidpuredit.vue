<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="planedit-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>计划管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/planmanage/bidpurmanage' }">招采建议计划管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/planmanage/bidpurmanage/bidpuredit' }">招采建议计划管理维护</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <div class="maincont-box planedit-cont">
      <el-tabs v-model="activeName" type="card" @tab-click="handleTabsClick">
        <!-- 建议计划start -->
        <el-tab-pane label="建议计划" name="first" class="planedit-first">
          <div class="sug-plan-btn">
            <!--<el-button type="warning" icon="el-icon-plus" @click="addClickBtn">新增项目</el-button>-->
            <el-button type="warning" icon="el-icon-tickets" @click="batchClickBtn" :disabled="disBatchValue">批量制定计划</el-button>
          </div>
          <div class="sug-plan-title">
            <h2>{{this.$route.query.year}}年采购建议计划表</h2>
            <span class="sug-tit-l">项目单位：{{enterpriseName}}</span>
            <span class="sug-tit-r">投资单位：万元</span>
          </div>
          <div class="sug-plan-table">
            <el-table
              :data="sugTableData"
              border
              style="width: 100%"
              :row-class-name="sugRowClassName"
              @selection-change="handleSelectionChange">
              <el-table-column
                type="index"
                label="序号"
                align="center"
                :index="indexEditMethod"
                width="50px">
              </el-table-column>
              <el-table-column
                align="center"
                prop="projectName"
                label="项目名称">
              </el-table-column>
              <el-table-column
                align="left"
                prop="content"
                label="工程简要内容及设备型号">
              </el-table-column>
              <el-table-column
                align="left"
                prop="quantitiesUnit"
                label="工程量单位">
              </el-table-column>
              <el-table-column
                label="概算"
                align="center">
                <el-table-column
                  prop="budgetQuantities"
                  label="工程量"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="budgetAmount"
                  label="金额"
                  align="right">
                </el-table-column>
              </el-table-column>
              <el-table-column
                label="本年计划"
                align="center">
                <el-table-column
                  prop="currentPlanQuantities"
                  label="工程量"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="currentPlanAmount"
                  label="金额"
                  align="right">
                </el-table-column>
              </el-table-column>
              <el-table-column
                align="center"
                prop="capitalSourceName"
                label="资金来源">
              </el-table-column>
              <el-table-column
                align="center"
                prop="fundPurpose"
                label="资金投向">
              </el-table-column>
              <el-table-column
                align="center"
                prop="projectType"
                label="类型"
                :formatter="filterProjectType">
              </el-table-column>
              <el-table-column
                align="left"
                prop="handle"
                label="批量操作"
                type="selection">
              </el-table-column>
              <el-table-column
                align="center"
                label="操作">
                <template slot-scope="scope">
                  <el-button @click="handleTableClick(scope.row,'plan')" type="text" size="medium" icon="el-icon-edit-outline">制定计划</el-button><!--
                  <el-button @click="handleTableClick(scope.row,'edit')" type="text" size="medium" icon="el-icon-edit" v-if="scope.row.projectCreateType === 2">修改</el-button>
                  <el-button @click="handleTableClick(scope.row,'del')" type="text" size="medium" icon="el-icon-delete" v-if="scope.row.projectCreateType === 2">删除</el-button>-->
                </template>
              </el-table-column>
            </el-table>
            <!--分页-->
            <div class="block-page">
              <el-pagination
                @size-change="handleSizeEditChange"
                @current-change="handleCurrentEditChange"
                :current-page.sync="currentEditPage"
                :pagee-sizs="pageSizeEditList"
                :page-size="pageSizeEdit"
                layout="total, sizes, prev, pager, next, jumper"
                :total="totalEdit">
              </el-pagination>
            </div>
          </div>
        </el-tab-pane>
        <!-- 建议计划end -->

        <!-- 招标项目计划start -->
        <el-tab-pane label="招采计划" name="second" class="planedit-second">
          <div class="pro-cont-tit">
            <el-row :gutter="20">
              <el-col :span="12" style="text-align: left;">
                <span class="pre-dowm-l">项目单位:</span><span>{{enterpriseName}}</span>
              </el-col>
              <el-col :span="12" style="text-align: right;">
                <span class="pre-dowm-l">投资单位:</span><span>万元</span>
              </el-col>
            </el-row>
          </div>
          <el-table
            :data="projectTableData"
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
              align="left"
              header-align="center"
              prop="projectPlanName"
              show-overflow-tooltip
              label="招采项目">
            </el-table-column>
            <el-table-column
              align="left"
              header-align="center"
              prop="projectName"
              label="项目名称"
              width="160">
              <template slot-scope="scope">
                <el-tooltip effect="dark" :content="item" placement="top-start" v-for="(item,index) in scope.row.projectName" :key="index"  v-if="item">
                  <p class="dresult-span">{{item}}</p>
                </el-tooltip>
                <p class="dresult-blank-span" v-else></p>
              </template>
            </el-table-column>
            <el-table-column
              align="left"
              header-align="center"
              prop="content"
              label="项目主要内容"
              width="200">
              <template slot-scope="scope">
                <el-tooltip effect="dark" :content="item" placement="top-start" v-for="(item,index) in scope.row.content" :key="index" v-if="item">
                  <p class="dresult-span">{{item}}</p>
                </el-tooltip>
                <p class="dresult-blank-span" v-else></p>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="currentPlanQuantities"
              label="工程量">
              <template slot-scope="scope">
                <p v-for="(item,index) in scope.row.currentPlanQuantities" :key="index" class="dresult-span" v-if="item">{{item}}</p>
                <p class="dresult-blank-span" v-else></p>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="capitalSourceName"
              label="资金来源">
              <template slot-scope="scope">
                <p v-for="(item,index) in scope.row.capitalSourceName" :title="item" :key="index" class="dresult-span" v-if="item">{{item}}</p>
                <p class="dresult-blank-span" v-else></p>
              </template>
            </el-table-column>
            <el-table-column
              align="left"
              header-align="center"
              prop="fundPurpose"
              label="资金投向">
              <template slot-scope="scope">
                <p v-for="(item,index) in scope.row.fundPurpose" :title="item" :key="index" class="dresult-span" v-if="item">{{item}}</p>
                <p class="dresult-blank-span" v-else></p>
              </template>
            </el-table-column>
            <el-table-column
              align="right"
              prop="planTotalAmount"
              label="计划金额（万元）"
              width="170">
            </el-table-column>
            <el-table-column
              align="center"
              prop="planLimit"
              :formatter="filterPlanLimit"
              label="计划工期">
            </el-table-column>
            <el-table-column
              align="left"
              header-align="center"
              prop="bidTime"
              :formatter="filterBidTime"
              label="拟招标时间"
              width="200">
            </el-table-column>
            <el-table-column
              align="left"
              prop="executeTime"
              :formatter="filterExecuteTime"
              label="拟实施时间"
              width="200">
            </el-table-column>
            <el-table-column
              align="center"
              prop="bidType"
              label="拟招标方式"
              width="150"
             :formatter="filterBidType">
            </el-table-column>
            <el-table-column
              align="center"
              prop="projectType"
              :formatter="filterProjectType"
              label="项目类型">
            </el-table-column>
            <el-table-column
              align="left"
              header-align="center"
              prop="enterpriseName"
              label="需求方">
            </el-table-column>
            <el-table-column
              align="center"
              label="操作"
              width="150px">
              <template slot-scope="scope">
                <el-button @click="handleTableClick(scope.row,'back')" type="text" size="small">撤销</el-button>
                <el-button @click="handleTableClick(scope.row,'pedit')" type="text" size="small">修改</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="pro-submit-btn">
             <el-button type="primary" v-if="disValue" @click="submitProPlan" >提交</el-button>
             <el-button type="primary" v-if="!disValue" disabled>提交</el-button>
          </div>
          <!--分页-->
          <div class="block-page">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page.sync="currentPage"
              :pagee-sizs="pageSizeList"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
            </el-pagination>
          </div>
        </el-tab-pane>
        <!-- 招标项目计划end -->
      </el-tabs>

      <!-- 弹窗区域start -->
      <div class="planedit-dialog">
        <!-- 制定计划弹窗start -->
        <el-dialog
          title="制定计划"
          :visible.sync="dialogPlanVisible"
          @close="closeWindow"
          width="50%">
          <el-form ref="ruleDoPLanForm" :model="rulePlanForm" :rules="planRules" label-width="130px" class="dialog-form-s">
            <el-form-item label="招标计划名称:" prop="projectPlanName">
              <el-input v-model="rulePlanForm.projectPlanName" style="width:200px;"></el-input>
            </el-form-item>
            <el-form-item label="包含项目详情:" prop="contDesc">
              <el-table
                :data="doPlanTableData"
                border
                style="width: 100%">
                <el-table-column
                  align="left"
                  prop="projectName"
                  label="项目名称">
                </el-table-column>
                <el-table-column
                  align="left"
                  prop="content"
                  label="项目主要内容">
                </el-table-column>
                <el-table-column
                  align="left"
                  prop="currentPlanQuantities"
                  label="工程量">
                  <template slot-scope="scope">
                    <p class="dresult-span" >{{scope.row.currentPlanQuantities?scope.row.currentPlanQuantities:scope.row.budgetQuantities}}</p>
                  </template>
                </el-table-column>
                <el-table-column
                  align="left"
                  prop="currentPlanAmount"
                  label="计划金额（万元）">
                  <template slot-scope="scope">
                    <p class="dresult-span" >{{scope.row.currentPlanAmount?scope.row.currentPlanAmount:scope.row.budgetAmount}}</p>
                  </template>
                </el-table-column>
                <el-table-column
                  align="left"
                  prop="capitalSourceName"
                  label="资金来源">
                  <template slot-scope="scope">
                    <p class="dresult-span" >{{scope.row.capitalSourceName?scope.row.capitalSourceName:capitalSourceName}}</p>
                  </template>
                </el-table-column>
              </el-table>
            </el-form-item>
            <el-form-item label="计划总金额(万元):">
              <span>{{rulePlanForm.planTotalAmount}}</span>
            </el-form-item>
            <el-form-item label="计划工期:" prop="planLimit">
              <el-select v-model="rulePlanForm.planLimit" placeholder="请选择">
                <el-option
                  v-for="item in planLimitList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="拟招标时间:" prop="bidTime">
              <el-date-picker
                v-model="bidTime"
                type="daterange"
                @change="bidTimePicker"
                align="right"
                unlink-panels
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                style="width:50%;">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="拟实施时间:" prop="executeTime">
              <el-date-picker
                v-model="executeTime"
                type="daterange"
                @change="executeTimePicker"
                align="right"
                unlink-panels
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                style="width:50%;">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="采购方式:" prop="bidType">
              <el-select v-model="rulePlanForm.bidType" placeholder="请选择" @change="bidTypeSelect">
                <el-option
                  v-for="item in bidTypeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="项目类型:" prop="projectType">
              <span>{{rulePlanForm.projectType |filterProjectType}}</span>
             <!-- <el-select v-model="rulePlanForm.projectType" placeholder="请选择" @change="projectTypeSelect">
                <el-option
                  v-for="item in projectTypeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>-->
            </el-form-item>
            <el-form-item label="需求方:" prop="requiresEnterpriseId">
              <span>{{rulePlanForm.enterpriseName}}</span>
            </el-form-item>
            <el-form-item label="项目单位负责人:" prop="projectLeader">
              <el-input v-model="rulePlanForm.projectLeader" style="width:200px;"></el-input>
            </el-form-item>
            <el-form-item label="负责人所属部门:" prop="projectLeaderDept">
              <el-input v-model="rulePlanForm.projectLeaderDept" style="width:200px;"></el-input>
            </el-form-item>
            <el-form-item label="集团公司负责人:" prop="groupLeader">
              <el-input v-model="rulePlanForm.groupLeader" style="width:200px;"></el-input>
            </el-form-item>
            <el-form-item label="负责人所属部门:" prop="groupLeaderDept">
              <el-input v-model="rulePlanForm.groupLeaderDept" style="width:200px;"></el-input>
            </el-form-item>
            <el-form-item label="备注:" prop="remark">
              <el-input type="textarea" v-model="rulePlanForm.remark"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitPlanForm('ruleDoPLanForm')">确定</el-button>
              <el-button @click="planCancelForm('ruleDoPLanForm')">取消</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>
        <!-- 制定计划弹窗end -->
      </div>
      <!-- 弹窗end -->
    </div>
  </div>
</template>
<script>
import {plansubmit, investmanage} from 'api/index'
import {formatDate} from 'common/js/date.js'
export default {
  data () {
    var validatePlanAmount = (rule, value, callback) => {
      if (value && (!(/^\d+(\.\d+)?$/).test(value))) {
        callback(new Error('计划金额必须为正数'))
      } else {
        callback()
      }
    }
    return {
      enterpriseName: '',
      year: '',
      checkedTable: false,
      activeName: 'first',
      sugTableData: [], // 建议计划表格数据
      projectTableData: [],
      doPlanTableData: [], // 制定计划-弹窗表格数据
      yearList: [],
      ruleSearchForm: {}, // 搜索表单
      dialogVisible: false, // 编辑项目
      dialogPlanVisible: false, // 制定计划弹窗
      bidTime: null,
      executeTime: null,
      bidStartTime: null, // 拟招标开始时间
      bidEndTime: null, // 拟招标结束时间
      executeStartTime: null, // 拟实施开始时间
      executeEndTime: null, // 拟实施结束时间
      disValue: false, // 提交按钮是否展示
      disBatchValue: false,
      total: 0,
      pageSize: 10,
      pageNo: 0,
      currentPage: 1,
      pageSizeList: [10, 20, 30, 40, 50],
      totalEdit: 0,
      pageSizeEdit: 10,
      pageNoEdit: 0,
      currentEditPage: 1,
      pageSizeEditList: [10, 20, 30, 40, 50],
      selectData: [],
      ruleForm: {
        capitalSource: '生产成本'
      }, // 编辑表单数据
      rulePlanForm: {
        projectPlanName: ''
      }, // 弹窗表单-制定计划
      bidTypeList: [
        {
          label: '公开招标',
          value: 1
        },
        {
          label: '邀请招标',
          value: 2
        },
        {
          label: '谈判采购',
          value: 3
        },
        {
          label: '询比采购',
          value: 4
        },
        {
          label: '竞价采购',
          value: 5
        },
        {
          label: '直接采购',
          value: 6
        },
        {
          label: '框架协议',
          value: 7
        }],
      /* projectTypeList: [
        {
          label: '工程',
          value: 1
        },
        {
          label: '货物',
          value: 2
        },
        {
          label: '服务',
          value: 3
        }], */
      planLimitList: [
        {
          label: '1个月',
          value: '1'
        },
        {
          label: '2个月',
          value: '2'
        },
        {
          label: '3个月',
          value: '3'
        },
        {
          label: '4个月',
          value: '4'
        },
        {
          label: '5个月',
          value: '5'
        },
        {
          label: '6个月',
          value: '6'
        },
        {
          label: '7个月',
          value: '7'
        },
        {
          label: '8个月',
          value: '8'
        },
        {
          label: '9个月',
          value: '9'
        },
        {
          label: '10个月',
          value: '10'
        },
        {
          label: '11个月',
          value: '11'
        },
        {
          label: '12个月',
          value: '12'
        },
        {
          label: '13个月',
          value: '13'
        },
        {
          label: '14个月',
          value: '14'
        },
        {
          label: '15个月',
          value: '15'
        },
        {
          label: '16个月',
          value: '16'
        },
        {
          label: '17个月',
          value: '17'
        },
        {
          label: '18个月',
          value: '18'
        },
        {
          label: '19个月',
          value: '19'
        },
        {
          label: '20个月',
          value: '20'
        },
        {
          label: '21个月',
          value: '21'
        },
        {
          label: '22个月',
          value: '22'
        },
        {
          label: '23个月',
          value: '23'
        },
        {
          label: '24个月',
          value: '24'
        }
      ],
      editRules: {
        projectName: [
          { required: true, message: '请输入项目名称', trigger: 'blur' },
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '请输入项目主要内容', trigger: 'blur' },
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
        ],
        quantities: [
          { required: true, message: '请输入工程量', trigger: 'blur' },
          { min: 1, max: 10, message: '请输入1~10个字符', trigger: 'blur' }
        ],
        planAmount: [
          { required: true, message: '请输入计划金额', trigger: 'blur' },
          { validator: validatePlanAmount, trigger: 'blur' }
        ]
      },
      planRules: {
        projectPlanName: [
          { required: true, message: '请输入招标项目名称', trigger: 'blur' },
          { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }
        ],
        planLimit: [
          { required: true, message: '请输入计划工期', trigger: 'change' }
        ],
        bidTime: [
          { required: true, message: '请输入拟招标时间', trigger: 'change' }
        ],
        executeTime: [
          { required: true, message: '请输入拟实施时间', trigger: 'change' }
        ],
        bidType: [
          { required: true, message: '请输入采购方式', trigger: 'change' }
        ],
        projectType: [
          { required: true, message: '请输入项目类型', trigger: 'change' }
        ],
        projectLeader: [
          { required: true, message: '请输入项目单位负责人名称', trigger: 'blur' }
        ],
        projectLeaderDept: [
          { required: true, message: '请输入负责人所属部门名称', trigger: 'blur' }
        ],
        groupLeader: [
          { required: true, message: '请输入集团公司负责人名称', trigger: 'blur' }
        ],
        groupLeaderDept: [
          { required: true, message: '请输入负责人所属部门名称', trigger: 'blur' }
        ]
      },
      capitalSourceName: ''
    }
  },
  created () {
    this.getCapitalSource()
    this.submitDisPlay()
  },
  filters: {
    /** 项目类型 */
    filterProjectType (value) {
      let projectType = value
      if (projectType === 1) {
        return '工程'
      } else if (projectType === 2) {
        return '货物'
      } else if (projectType === 3) {
        return '服务'
      }
    }
  },
  methods: {
    onCancelForm () {
      this.dialogVisible = false
      this.$refs['ruleAddForm'].resetFields()
    },
    planCancelForm () {
      this.dialogPlanVisible = false
      this.$refs['ruleDoPLanForm'].resetFields()
    },
    /**
    * 新增行颜色设置
    */
    sugRowClassName ({row, rowIndex}) {
      if (row.projectCreateType === 2) {
        return 'warning-row'
      }
    },
    /* projectTypeSelect (val) {
      this.rulePlanForm.projectType = val
    }, */
    bidTypeSelect (val) {
      this.rulePlanForm.bidType = val
    },
    /**
    * 批量操作
    */
    handleSelectionChange (data) {
      this.selectData = data
    },
    closeWindow () {
      this.dialogPlanVisible = false
      this.rulePlanForm = {
        projectPlanName: ''
      }
      this.doPlanTableData = []
      this.bidTime = []
      this.executeTime = []
    },
    indexEditMethod (index) {
      return index + (this.currentEditPage - 1) * this.pageSizeEdit + 1
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * this.pageSize + 1
    },
    // 每页展示条数
    handleSizeEditChange (val) {
      this.pageSizeEdit = val
      this.getProjectList()
    },
    // 改变当前页码
    handleCurrentEditChange (nowNum) {
      // 前台页面展示当前页
      this.currentEditPage = nowNum
      // 后台需要的参数
      this.pageNoEdit = (nowNum - 1) * this.pageSizeEdit
      this.getProjectList()
    },
    // 每页展示条数
    handleSizeChange (val) {
      this.pageSize = val
      this.getProjectPlanList()
    },
    // 改变当前页码
    handleCurrentChange (nowNum) {
      // 前台页面展示当前页
      this.currentPage = nowNum
      // 后台需要的参数
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getProjectPlanList()
    },
    /** 进行修改操作后返回当前页 */
    // goBack () {
    //   this.$router.push({path: './plansubmit', query: {currentPage: this.$route.query.currentPage, pageNo: this.$route.query.pageNo, pageSize: this.$route.query.pageSize}})
    // },
    bidTimePicker (value) {
      if (value) {
        this.bidTime = value
        this.rulePlanForm.bidTime = value
        this.bidStartTime = value[0]
        this.bidEndTime = value[1]
      } else {
        this.bidStartTime = null
        this.bidEndTime = null
      }
    },
    executeTimePicker (value) {
      if (value) {
        this.executeTime = value
        this.rulePlanForm.executeTime = value
        this.executeStartTime = value[0]
        this.executeEndTime = value[1]
      } else {
        this.executeStartTime = null
        this.executeEndTime = null
      }
    },
    filterPlanLimit (row, column) {
      let planLimit = row.planLimit
      if (planLimit !== '' && planLimit !== null) {
        return planLimit + '个月'
      }
    },
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
    filterProjectType (row, column) {
      let projectType = row.projectType
      if (projectType === 1) {
        return '工程'
      } else if (projectType === 2) {
        return '货物'
      } else if (projectType === 3) {
        return '服务'
      }
    },
    filterBidTime (row, column) {
      if (!row.bidStartTime || !row.bidEndTime) {
        return ''
      } else {
        let stateDate = new Date(row.bidStartTime)
        let endDate = new Date(row.bidEndTime)
        return formatDate(stateDate, 'yyyy-MM-dd') + ' 至 ' + formatDate(endDate, 'yyyy-MM-dd')
      }
    },
    filterExecuteTime (row, column) {
      if (!row.executeStartTime || !row.executeEndTime) {
        return ''
      } else {
        let stateDate = new Date(row.executeStartTime)
        let endDate = new Date(row.executeEndTime)
        return formatDate(stateDate, 'yyyy-MM-dd') + ' 至 ' + formatDate(endDate, 'yyyy-MM-dd')
      }
    },
    // tabs切换
    handleTabsClick () {},
    // 新增项目
    // addClickBtn () {
    //   this.dialogVisible = true
    //   this.ruleForm = {
    //     capitalSource: 5
    //   }
    // },
    /**
     *  提交-新增表单
     */
    submitForm (name) {
      this.$refs[name].validate((vaild) => {
        if (vaild) {
          this.ruleForm.purchaseSuggestedCode = this.$route.query.purchaseSuggestedCode
          this.ruleForm.enterpriseId = this.$route.query.enterpriseId
          this.ruleForm.capitalSource = 5
          this.ruleForm.projectCreateType = 2
          this.ruleForm.isExport = false
          plansubmit.saveProject(this.ruleForm).then(() => {
            this.dialogVisible = false
            this.getProjectList()
          })
        } else {
          return false
        }
      })
    },
    /** 批量制定按钮计划 */
    batchClickBtn () {
      if (this.selectData) {
        if (this.selectData.length > 1) {
          let projectType = []
          let planTotalAmount = 0
          for (let i = 0; i < this.selectData.length; i++) {
            if (i !== this.selectData.length - 1) {
              if (this.selectData[i].projectType !== this.selectData[i + 1].projectType) {
                projectType[i] = this.selectData[i].projectType
                projectType[i + 1] = this.selectData[i + 1].projectType
              }
            }
            if (this.selectData[i].currentPlanAmount || this.selectData[i].budgetAmount) {
              planTotalAmount += Number(this.selectData[i].currentPlanAmount)
              planTotalAmount += Number(this.selectData[i].budgetAmount)
            }
          }
          if (projectType.length > 0) {
            this.$message.error('项目类型不同,不能组合创建招标计划')
          }
          if (projectType.length === 0) {
            this.doPlanTableData = this.selectData
            this.rulePlanForm.projectType = this.selectData[0].projectType
            this.rulePlanForm.enterpriseName = this.$route.query.enterpriseName
            this.rulePlanForm.planTotalAmount = planTotalAmount.toFixed(2)
            this.dialogPlanVisible = true
          }
        } else {
          this.$message.error('请至少选择两条数据')
        }
      }
    },
    /** 制定计划确定按钮 */
    submitPlanForm (name) {
      this.$refs[name].validate((vaild) => {
        if (vaild) {
          this.rulePlanForm.year = this.$route.query.year
          this.rulePlanForm.annualProjectPlanCode = this.$route.query.code
          this.rulePlanForm.bidStartTime = this.bidStartTime
          this.rulePlanForm.bidEndTime = this.bidEndTime
          this.rulePlanForm.executeStartTime = this.executeStartTime
          this.rulePlanForm.executeEndTime = this.executeEndTime
          this.rulePlanForm.requiresEnterpriseId = this.$route.query.enterpriseId
          this.rulePlanForm.executeEnterpriseId = this.$route.query.enterpriseId
          if (!this.rulePlanForm.projectList) {
            this.rulePlanForm.projectList = this.doPlanTableData
            if (this.rulePlanForm.projectList) {
              if (this.rulePlanForm.projectList.length > 0) {
                let planTotalAmount = 0
                let projectType = ''
                let projectPlanType = ''
                for (let i = 0; i < this.rulePlanForm.projectList.length; i++) {
                  if (this.rulePlanForm.projectList[i].currentPlanAmount || this.rulePlanForm.projectList[i].budgetAmount) {
                    planTotalAmount += Number(this.rulePlanForm.projectList[i].currentPlanAmount)
                    planTotalAmount += Number(this.rulePlanForm.projectList[i].budgetAmount)
                  }
                  if (this.rulePlanForm.projectList[i].projectType !== '' && this.rulePlanForm.projectList[i].projectType !== null) {
                    projectType = this.rulePlanForm.projectList[i].projectType
                  }
                  if (this.rulePlanForm.projectList[i].projectCreateType !== '' && this.rulePlanForm.projectList[i].projectCreateType !== null) {
                    projectPlanType = this.rulePlanForm.projectList[i].projectCreateType
                  }
                }
                this.rulePlanForm.planTotalAmount = String(planTotalAmount.toFixed(2))
                this.rulePlanForm.projectType = projectType
                this.rulePlanForm.projectPlanType = projectPlanType
              }
            }
          }
          plansubmit.saveProjectPlan(this.rulePlanForm).then(() => {
            this.dialogPlanVisible = false
            this.getProjectList()
            this.getProjectPlanList()
            this.submitDisPlay()
          })
        } else {
          return false
        }
      })
    },
    /** 提交-招标项目计划 */
    submitProPlan () {
      this.$confirm('此操作将提交, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let obj = {
          code: this.$route.query.code,
          investmentProposalSchemeCode: this.$route.query.investmentProposalSchemeCode
        }
        plansubmit.updateStatus(obj).then(() => {
          this.getProjectList()
          this.getProjectPlanList()
          this.disValue = true
          this.$router.push({path: '/admin/planmanage/bidpurmanage'})
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    /** 表格操作 */
    handleTableClick (row, type) {
      if (type === 'del') {
        this.delClick(row)
      } else if (type === 'plan') {
        this.dialogPlanVisible = true
        this.doPlanTableData.push(row)
        this.rulePlanForm.projectType = row.projectType
        this.rulePlanForm.enterpriseName = this.$route.query.enterpriseName
        this.rulePlanForm.projectPlanName = row.projectName
        this.rulePlanForm.planTotalAmount = row.currentPlanAmount
        if (!row.currentPlanAmount) {
          this.rulePlanForm.planTotalAmount = row.budgetAmount
        }
        this.getCapitalSource()
      } else if (type === 'edit') {
        this.getCapitalSource()
        this.dialogVisible = true
        this.editClick(row)
      } else if (type === 'back') {
        this.backClick(row)
      } else if (type === 'pedit') {
        this.getCapitalSource()
        this.dialogPlanVisible = true
        this.peditClick(row)
      }
    },
    // 建议计划修改按钮
    editClick (data) {
      this.ruleForm = data
      this.ruleForm.planAmount = Number(data.planAmount)
    },
    // 招标项目计划修改按钮
    peditClick (data) {
      this.doPlanTableData = data.projectList
      this.rulePlanForm = data
      this.rulePlanForm.bidType = data.bidType
      this.rulePlanForm.projectType = data.projectType
      this.rulePlanForm.planTotalAmount = data.planTotalAmount
      let bidTime = []
      bidTime[0] = new Date(data.bidStartTime)
      bidTime[1] = new Date(data.bidEndTime)
      this.bidTime = bidTime
      this.rulePlanForm.bidTime = bidTime
      let executeTime = []
      executeTime[0] = new Date(data.executeStartTime)
      executeTime[1] = new Date(data.executeEndTime)
      this.executeTime = executeTime
      this.rulePlanForm.executeTime = executeTime
    },
    delClick (data) {
      this.$confirm('此操作将删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        plansubmit.deleteProject(data.objectId).then(() => {
          this.getProjectList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    backClick (data) {
      this.$confirm('此操作将撤销, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 撤销
        plansubmit.delete(data).then(() => {
          this.getProjectPlanList()
          this.getProjectList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    /** 建议计划 */
    getProjectList () {
      // 查找采购项目信息
      let obj = {
        pageNo: this.pageNoEdit,
        pageSize: this.pageSizeEdit,
        investmentProposalSchemeCode: this.$route.query.investmentProposalSchemeCode,
        isPlaned: 0,
        isInRecycleBin: 0,
        isDelete: 0
      }
      investmanage.getNewestSuggestedPlanList(obj).then((res) => {
        if (res.data.suggestedPlanList) {
          this.sugTableData = res.data.suggestedPlanList.list
          if (this.sugTableData.length > 0) {
            let fundPurpose = []
            for (let i = 0; i < this.sugTableData.length; i++) {
              if (this.sugTableData[i].fundPurposeFirstName) {
                let fundPurposeSecondtName = this.sugTableData[i].fundPurposeSecondtName || ''
                if (fundPurposeSecondtName === '') {
                  fundPurpose = `${this.sugTableData[i].fundPurposeFirstName}`
                } else {
                  fundPurpose = `${this.sugTableData[i].fundPurposeFirstName}/${fundPurposeSecondtName}`
                }
              }
              if (this.sugTableData[i].capitalSource === 0) {
                this.sugTableData[i].capitalSourceName = this.capitalSourceName
              }
              this.sugTableData[i].fundPurpose = fundPurpose
            }
            this.disBatchValue = false
          } else {
            this.disBatchValue = true
          }
          this.totalEdit = res.data.suggestedPlanList.total
        } else {
          return false
        }
        this.submitDisPlay()
      })
    },
    /** 招采计划 */
    getProjectPlanList () {
      this.enterpriseName = this.$route.query.enterpriseName
      // 查找招标计划项目信息
      let obj = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        year: this.$route.query.year,
        requiresEnterpriseId: this.$route.query.enterpriseId,
        annualProjectPlanCode: this.$route.query.code,
        statuses: '1, 4',
        projectPlanTypeList: '1, 4'
      }
      plansubmit.getProjectPlan(obj).then((res) => {
        if (res.data.projectPlanList) {
          this.projectTableData = res.data.projectPlanList.list
          this.total = res.data.projectPlanList.total
          if (this.projectTableData) {
            if (this.projectTableData.length > 0) {
              for (let i = 0; i < this.projectTableData.length; i++) {
                let projectName = []
                let content = []
                let currentPlanQuantities = []
                let capitalSourceName = []
                let fundPurpose = []
                if (this.projectTableData[i].projectList) {
                  if (this.projectTableData[i].projectList.length > 0) {
                    for (let j = 0; j < this.projectTableData[i].projectList.length; j++) {
                      projectName[j] = this.projectTableData[i].projectList[j].projectName
                      content[j] = this.projectTableData[i].projectList[j].content
                      currentPlanQuantities[j] = this.projectTableData[i].projectList[j].currentPlanQuantities
                      if (this.projectTableData[i].projectList[j].capitalSource === 0) {
                        capitalSourceName[j] = this.capitalSourceName
                      } else {
                        capitalSourceName[j] = this.projectTableData[i].projectList[j].capitalSourceName
                      }
                      if (this.projectTableData[i].projectList[j].fundPurposeFirstName) {
                        let fundPurposeSecondtName = this.projectTableData[i].projectList[j].fundPurposeSecondtName || ''
                        if (fundPurposeSecondtName === '') {
                          fundPurpose[j] = `${this.projectTableData[i].projectList[j].fundPurposeFirstName}`
                        } else {
                          fundPurpose[j] = `${this.projectTableData[i].projectList[j].fundPurposeFirstName}/${fundPurposeSecondtName}`
                        }
                      }
                    }
                    this.projectTableData[i].projectName = projectName
                    this.projectTableData[i].content = content
                    this.projectTableData[i].currentPlanQuantities = currentPlanQuantities
                    this.projectTableData[i].capitalSourceName = capitalSourceName
                    this.projectTableData[i].fundPurpose = fundPurpose
                  }
                }
              }
            }
          } else {
            return false
          }
        } else {
          return false
        }
        this.submitDisPlay()
      })
    },
    /** 提交按钮在所有的采购建议计划制定成招标计划后显示 */
    submitDisPlay () {
      let obj = {
        investmentProposalSchemeCode: this.$route.query.investmentProposalSchemeCode,
        isPlaned: 0,
        isInRecycleBin: 0,
        isDelete: 0
      }
      investmanage.getNewestSuggestedPlanList(obj).then((res) => {
        if (res.data.suggestedPlanList.length > 0) {
          this.disValue = false
        } else {
          this.disValue = true
        }
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
        this.capitalSourceName = this.capitalSourceName.substring(0, this.capitalSourceName.length-1)
      })
    }
  },
  computed: {},
  // 实例创建完成
  mounted () {
    this.getProjectList()
    this.getProjectPlanList()
  }
}
</script>
<style lang="less">
#planedit-page {
  .planedit-cont {
    /*按钮*/
    .sug-plan-btn {
      text-align: left;
      padding-bottom: 5px;
    }
    .sug-plan-title {
      span {
        display: inline-block;
        padding-bottom: 5px;
      }
      .sug-tit-l {
        float: left;
      }
      .sug-tit-r {
        float: right;
      }
    }
    .pro-cont-tit {
      padding-top: 20px;
      padding-bottom: 5px;
      .pre-dowm-l {
        color: #999;
        padding-right: 5px;
      }
    }
    .pro-submit-btn {
      margin-top: 20px;
    }
    .planedit-second {
      .el-table__body .el-table__row {
        td:nth-child(3),
        td:nth-child(4),
        td:nth-child(5),
        td:nth-child(6),
        td:nth-child(7){
          div.cell {
            padding:0;
          }
          p {
            margin: 0;
            padding: 10px;
          }
          .dresult-blank-span {
            height: 22px;
          }
          p:not(:last-child) {
            border-bottom: 1px solid #eee;
          }
        }
        td:nth-child(3) p,
        td:nth-child(4) p,
        td:nth-child(5) p,
        td:nth-child(6) p{
          overflow: hidden;
          white-space: nowrap;
          text-overflow: ellipsis;
        }
      }
    }
  }
  .sug-plan-table {
    .el-table__body {
      .warning-row {
        color: red;
      }
    }
  }
  .el-table__body {
    tbody tr td:nth-child(7) .cell{
      overflow: hidden;
      white-space: nowrap;
    }
  }
  .planedit-dialog {
    .dialog-form-s {
      .el-form-item:last-child {
        margin-left: -130px;
        .el-form-item__content{
          text-align: center;
        }
      }
    }
  }
  .el-form-item__content {
    text-align: left;
  }
  .block-page {
    padding-top: 20px;
  }
}
</style>
