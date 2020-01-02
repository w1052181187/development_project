<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="balancemanage-page">
    <div v-if="this.$route.path == '/admin/planmanage/balancemanage'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>计划管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/planmanage/balancemanage' }">资金平衡表管理</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!--搜索区域start-->
      <div class="search-box balance-search">
        <el-form :model="searchForm" :inline="true" class="demo-form-inline" style="text-align: left;">
          <el-form-item label="年度:">
            <el-select v-model="searchForm.year" clearable placeholder="请选择">
              <el-option :label="item" :value="item" v-for="(item, index) in yearList" :key="index"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="项目单位:">
            <el-input v-model="searchForm.projectEnterpriseName" placeholder="请输入项目单位名称"></el-input>
          </el-form-item>
          <el-form-item label="状态:">
            <el-select v-model="searchForm.status" clearable placeholder="请选择">
              <el-option
                v-for="item in statusList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchFun" icon="el-icon-search">搜索</el-button>
          </el-form-item>
        </el-form>
      </div>
      <!--搜索区域end-->
      <div class="maincont-box planaccept-cont">
        <div class="balance-add">
          <el-button type="warning" @click="addClickBtn" icon="el-icon-plus" v-if="$store.getters.permissions.includes('/planmanage/balancemanage/operation')">新增</el-button>
        </div>
        <!-- 表格区域start -->
        <el-table
          :data="tableData"
          :row-class-name="tableRowClassName"
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
            prop="projectEnterpriseName"
            label="项目单位"
            align="center">
          </el-table-column>
          <el-table-column
            prop="year"
            label="年度"
            align="center"
            width="80">
          </el-table-column>
          <el-table-column
            prop="fixedQuota"
            label="专项资金额度（万元）"
            align="right">
          </el-table-column>
          <el-table-column
            prop="constructionQuota"
            label="工程建设额度（万元）"
            align="right">
          </el-table-column>
          <el-table-column
            prop="submitterName"
            label="提交人"
            align="center">
          </el-table-column>
          <el-table-column
            prop="submitDate"
            label="提交时间"
            align="center"
            :formatter="filterDate">
          </el-table-column>
          <el-table-column
            prop="status"
            label="状态"
            align="center"
            :formatter="filterStatus">
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
            width="160">
            <template slot-scope="scope">
              <el-button @click="handleTableClick(scope.row,'edit')" type="text" size="medium" v-if="scope.row.operationFlag && $store.getters.permissions.includes('/planmanage/balancemanage/operation')">编辑</el-button>
              <el-button @click="handleTableClick(scope.row,'look')" type="text" size="medium" v-if="$store.getters.permissions.includes('/planmanage/balancemanage/detail')">查看</el-button>
              <el-button @click="handleTableClick(scope.row,'del')" type="text" size="medium" v-if="scope.row.operationFlag && $store.getters.permissions.includes('/planmanage/balancemanage/operation')">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 表格区域end -->
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
      <!--弹窗区域start-->
      <div class="dialog-box">
        <el-dialog
          title="新增"
          :visible.sync="dialogVisible"
          :before-close="handleClose"
          width="30%"
          center>
          <el-form :model="dialogForm" :rules= "dialogForm.rules" ref="dialogForm"  label-width="100px">
            <el-form-item label="创建年度:" prop="year">
              <el-select v-model="dialogForm.year" placeholder="请选择">
                <el-option :label="item" :value="item" v-for="(item, index) in createYearList" :key="index"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="saveBtn('dialogForm')">保存</el-button>
            <el-button @click="cancel()">取消</el-button>
            </span>
        </el-dialog>
        <!--新增、编辑弹窗start-->
        <el-dialog
          title="创建"
          :visible.sync="dialogCreateVisible"
          :before-close="handleCloseDialog"
          width="60%"
          center>
          <p><strong>{{this.dialogForm.year - 1}}年项目预计完成情况</strong></p>
          <el-form :inline="true" :model="projectExpectedForm" ref="projectExpectedForm" label-width="150px">
            <el-row :gutter="20">
              <el-col :span="11">
                <el-form-item
                  v-for="(domain, index) in projectExpectedForm.specialFundsForm.domains"
                  :label="domain.label"
                  :key="domain.key"
                  :prop="'specialFundsForm.domains.' + index + '.value'"
                  :rules="domain.label !=='未分配利润' ? ((domain.label === '预计煤炭生产量'|| domain.label === '计划煤炭生产量') ? rules.value3 : rules.value1) : rules.value2">
                  <el-input v-model="domain.value" style="width:190px" v-if="domain.label !== '预计煤炭生产量' && domain.label !== '计划煤炭生产量'"></el-input><span v-if="domain.label !== '预计煤炭生产量'&& domain.label !== '计划煤炭生产量'">万元</span>
                  <el-input v-model="domain.value" style="width:190px" v-if="domain.label === '预计煤炭生产量'|| domain.label === '计划煤炭生产量'"></el-input><span v-if="domain.label === '预计煤炭生产量' || domain.label === '计划煤炭生产量'">万吨</span>
                </el-form-item>
              </el-col>
              <el-col :span="11">
                <el-form-item
                  v-for="(domain, index) in projectExpectedForm.constructionFundsForm.domains"
                  :label="domain.label"
                  :key="domain.key"
                  :prop="'constructionFundsForm.domains.' + index + '.value'"
                  :rules="rules.value1">
                  <el-input v-model="domain.value" style="width:190px" ></el-input><span>万元</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20" v-if="isShowFlag">
              <el-col :span="11">
                <el-form-item
                  label="矿业权"
                  prop="miningRightsType"
                  :rules="rules.miningRightsType">
                  <el-select v-model="projectExpectedForm.miningRightsType" placeholder="请选择" @change="changeMinRight" style="width:190px">
                    <el-option label="探矿权" value="0"></el-option>
                    <el-option label="采矿权" value="1"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="11">
                <el-form-item
                  v-if="!isShowMineral"
                  label="探矿权出让收益"
                  prop="prospectingRightsBenefits"
                  :rules="rules.prospectingRightsBenefits">
                  <el-input v-model="projectExpectedForm.prospectingRightsBenefits" style="width:190px" ></el-input><span>万元</span>
                </el-form-item>
                <el-form-item
                  v-if="isShowMineral"
                  label="深加工产品销售收入"
                  prop="deepProcessingProductsIncome"
                  :rules="rules.deepProcessingProductsIncome">
                  <el-input v-model="projectExpectedForm.deepProcessingProductsIncome" style="width:190px" ></el-input><span>万元</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20"  v-if="isShowFlag && isShowMineral">
              <el-col :span="11">
                <el-form-item
                  label="矿种系数"
                  prop="mineralTypeCoefficient"
                  :rules="rules.mineralTypeCoefficient">
                  <el-input v-model="projectExpectedForm.mineralTypeCoefficient" style="width:190px" ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="11">
                <el-form-item
                  label="影响系数"
                  prop="influenceCoefficient"
                  :rules="rules.influenceCoefficient">
                  <el-input v-model="projectExpectedForm.influenceCoefficient" style="width:190px" ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <p><strong>{{this.dialogForm.year}}年项目计划情况（本年度专项资金风险比例为{{this.riskRatio}}%）</strong></p>
          <el-form :model="projectPlanForm" :rules="rules" ref="projectPlanForm" label-width="150px">
            <el-row :gutter="20">
              <el-col :span="11">
                <el-form-item
                  v-for="(domain, index) in projectPlanForm.specialFundsForm.domains"
                  :label="domain.label"
                  :key="domain.key"
                  :prop="'specialFundsForm.domains.' + index + '.value'"
                  :rules="domain.label !=='未分配利润' ? ((domain.label === '预计煤炭生产量'|| domain.label === '计划煤炭生产量') ? rules.value3 : rules.value1) : rules.value2">
                  <el-input v-model="domain.value" style="width:190px" v-if="domain.label !== '预计煤炭生产量' && domain.label !== '计划煤炭生产量'"></el-input><span v-if="domain.label !== '预计煤炭生产量' && domain.label !== '计划煤炭生产量'">万元</span>
                  <el-input v-model="domain.value" style="width:190px" v-if="domain.label === '预计煤炭生产量' || domain.label === '计划煤炭生产量'"></el-input><span v-if="domain.label === '预计煤炭生产量' || domain.label === '计划煤炭生产量'">万吨</span>
                </el-form-item>
              </el-col>
              <el-col :span="11">
                <el-form-item
                  v-for="(domain, index) in projectPlanForm.constructionFundsForm.domains"
                  :label="domain.label"
                  :key="domain.key"
                  :prop="'constructionFundsForm.domains.' + index + '.value'"
                  :rules="rules.value1">
                  <el-input v-model="domain.value" style="width:190px" ></el-input><span>万元</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20" v-if="isShowSecondFlag">
              <el-col :span="11">
                <el-form-item
                  label="矿业权"
                  prop="miningRightsType"
                  :rules="rules.miningRightsType">
                  <el-select v-model="projectPlanForm.miningRightsType" placeholder="请选择" @change="changeSecondMinRight" style="width:190px">
                    <el-option label="探矿权" value="0"></el-option>
                    <el-option label="采矿权" value="1"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="11">
                <el-form-item
                  v-if="!isShowSecondMineral"
                  label="探矿权出让收益"
                  prop="prospectingRightsBenefits"
                  :rules="rules.prospectingRightsBenefits">
                  <el-input v-model="projectPlanForm.prospectingRightsBenefits" style="width:190px" ></el-input><span>万元</span>
                </el-form-item>
                <el-form-item
                  v-if="isShowSecondMineral"
                  label="深加工产品销售收入"
                  prop="deepProcessingProductsIncome"
                  :rules="rules.deepProcessingProductsIncome">
                  <el-input v-model="projectPlanForm.deepProcessingProductsIncome" style="width:190px" ></el-input><span>万元</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20"  v-if="isShowSecondFlag && isShowSecondMineral">
              <el-col :span="11">
                <el-form-item
                  label="矿种系数"
                  prop="mineralTypeCoefficient"
                  :rules="rules.mineralTypeCoefficient">
                  <el-input v-model="projectPlanForm.mineralTypeCoefficient" style="width:190px" ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="11">
                <el-form-item
                  label="影响系数"
                  prop="influenceCoefficient"
                  :rules="rules.influenceCoefficient">
                  <el-input v-model="projectPlanForm.influenceCoefficient" style="width:190px" ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="saveProjectForm('projectExpectedForm', 'projectPlanForm')">保存</el-button>
            <el-button @click="cancelDialog()">取消</el-button>
            </span>
        </el-dialog>
        <!--新增、编辑弹窗end-->
      </div>
      <!--弹窗区域end-->
    </div>
    <div v-else>
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
// 实例
import {enterprise, balanceSheet, investmanage, constantMaintenance} from 'api/index'
import {isvalidPrice, isvalidPriceForNegative, isvalidPriceForCoal, isvalidPriceForCoefficient} from '../../../../../assets/js/validate'
import {formatDate} from 'common/js/date.js'
import {capitalSourceShortNameList} from 'common/js/balanceSheet'
export default {
  data () {
    let validPrice = (rule, value, callback) => {
      if (!value) {

      } else if (!isvalidPrice(value)) {
        callback(new Error('请输入正数且最多两位小数'))
      } else {
        callback()
      }
    }
    let validPriceForNegative = (rule, value, callback) => {
      if (!value) {

      } else if (!isvalidPriceForNegative(value)) {
        callback(new Error('请输入数字且最多两位小数'))
      } else {
        callback()
      }
    }
    let validPriceForCoal = (rule, value, callback) => {
      if (!value) {

      } else if (!isvalidPriceForCoal(value)) {
        callback(new Error('请输入正数且最多六位小数'))
      } else {
        callback()
      }
    }
    let validPriceForCoefficient = (rule, value, callback) => {
      if (!value) {

      } else if (!isvalidPriceForCoefficient(value)) {
        callback(new Error('请输入正数且最多两位小数'))
      } else {
        callback()
      }
    }
    return {
      yearList: this.getYearMethed(),
      createYearList: this.getYearMethed(),
      projectUnitList: [], // 项目单位
      statusList: [
        {label: '已提交', value: 1},
        {label: '未提交', value: 0}
      ],
      dialogCreateVisible: false, // 创建弹窗
      isShowMineral: false,
      projectExpectedForm: {
        miningRightsType: '0',
        specialFundsForm: {
          domains: []
        },
        constructionFundsForm: {
          domains: []
        }
      }, // 项目预计
      expectedRulesForm: {},
      isShowSecondMineral: false,
      projectPlanForm: {
        miningRightsType: '0',
        specialFundsForm: {
          domains: []
        },
        constructionFundsForm: {
          domains: []
        }
      }, // 项目计划
      projectPlanRulesForm: {},
      dialogVisible: false, // 年份-弹窗
      dialogForm: {
        year: '',
        rules: {
          year: [
            {required: true, message: '请选择年份', trigger: ['blur', 'change']}
          ]
        }
      },
      rules: {
        value1: [
          {required: true, message: '请输入正数且最多两位小数', trigger: ['blur', 'change']},
          {validator: validPrice, trigger: ['blur', 'change']}
        ],
        value2: [
          {required: true, message: '请输入数字且最多两位小数', trigger: ['blur', 'change']},
          {validator: validPriceForNegative, trigger: ['blur', 'change']}
        ],
        value3: [
          {required: true, message: '请输入正数且最多六位小数', trigger: ['blur', 'change']},
          {validator: validPriceForCoal, trigger: ['blur', 'change']}
        ],
        miningRightsType: [
          {required: true, message: '请选择矿业权', trigger: ['blur', 'change']}
        ],
        prospectingRightsBenefits: [
          {required: true, message: '请输入数字且最多两位小数', trigger: 'blur'},
          {validator: validPriceForNegative, trigger: 'blur'}
        ],
        deepProcessingProductsIncome: [
          {required: true, message: '请输入正数且最多两位小数', trigger: ['blur', 'change']},
          {validator: validPrice, trigger: ['blur', 'change']}
        ],
        mineralTypeCoefficient: [
          {required: true, message: '请输入正数且最多两位小数', trigger: ['blur', 'change']},
          {validator: validPriceForCoefficient, trigger: ['blur', 'change']}
        ],
        influenceCoefficient: [
          {required: true, message: '请输入正数且最多两位小数', trigger: ['blur', 'change']},
          {validator: validPriceForCoefficient, trigger: ['blur', 'change']}
        ]
      },
      searchForm: {}, // 搜索
      tableData: [],
      total: null,
      pageNo: 0,
      pageSize: 10,
      currentPage: 1,
      pageSizeList: [10, 20, 30, 40, 50],
      isEmpty: false,
      yearCheckFlag: false, // 判断所选年份是否可以添加资金平衡表
      currentEnterpriseClassify: '', // 当前用户所在企业的企业类型
      riskRatio: '',
      isShowFlag: false, // 采矿权等信息是否展示
      isShowSecondFlag: false
    }
  },
  watch: {
    '$route': 'getTableData'
  },
  created () {
    this.getRiskRatio()
    this.getTableData()
    this.getEnterpriseClassify()
  },
  filters: {
  },
  methods: {
    /** 矿业权-chang事件 */
    changeMinRight (val) {
      if (val !== '0') {
        this.isShowMineral = true
      } else {
        this.isShowMineral = false
      }
    },
    changeSecondMinRight (val) {
      if (val !== '0') {
        this.isShowSecondMineral = true
      } else {
        this.isShowSecondMineral = false
      }
    },
    /** 获取专项资金风险控制比例  */
    getRiskRatio () {
      constantMaintenance.getList({
        name: '专项资金风险控制比例',
        type: 1
      }).then((res) => {
        this.riskRatio = res.data.constantMaintenanceList[0].description
      })
    },
    /** 格式化申请时间 */
    filterDate (row, column) {
      if (row.submitDate) {
        let date = new Date(row.submitDate)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return null
      }
    },
    filterStatus (row, column) {
      if (row.status === 0) {
        return '未提交'
      } else {
        return '已提交'
      }
    },
    /** 获取页面数据 */
    getTableData () {
      balanceSheet.getList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        year: this.searchForm.year || null,
        messageLike: this.searchForm.projectEnterpriseName || null,
        status: (this.searchForm.status === 0 || this.searchForm.status === 1) ? this.searchForm.status : null
      }).then((res) => {
        this.tableData = res.data.balanceSheetList.list
        this.total = res.data.balanceSheetList.total
        if (this.tableData.length > 0) {
          this.tableData.forEach(item => {
            if (item.status === 0) {
              item.operationFlag = true
            } else {
              item.operationFlag = false
            }
          })
        }
      })
    },
    /** 得到当前用户所在企业的企业分类（1.煤矿；2.燃气；3.煤化工；4.其他） */
    getEnterpriseClassify () {
      enterprise.getOne(this.$store.getters.authUser.enterpriseId).then((res) => {
        this.currentEnterpriseClassify = res.data.enterprise.enterpriseClassify
      })
    },
    /** 获取对应企业类型下的所有资金来源 */
    getAllCapitalSourceByEnterpriseType () {
      if (this.currentEnterpriseClassify !== null && this.currentEnterpriseClassify !== '') {
        investmanage.selectCapitalSourceByEnterpriseType(this.currentEnterpriseClassify).then((res) => {
          this.getDialogInfo(res.data.enterpriseTypeCapitalSourceList, this.currentEnterpriseClassify)
        })
      } else {
        investmanage.getCapitalSourceList({}).then((res) => {
          let capitalSourceList = []
          res.data.capitalSourceList.forEach(item => {
            let obj = {
              capitalSourceId: item.objectId,
              capitalSourceName: item.name,
              capitalSourceShortName: item.shortName,
              classify: item.classify
            }
            capitalSourceList.push(obj)
          })
          this.getDialogInfo(capitalSourceList, this.currentEnterpriseClassify)
        })
      }
    },
    /** 构建弹框中专项资金部分 */
    createFormInfoInDialog (capitalSourceList, form, enterpriseClassify, formName) {
      let capitalSourceArr1 = ''
      let capitalSourceArr2 = ''
      capitalSourceList.forEach(item => {
        let obj = {
          label: item.capitalSourceName,
          capitalSourceShortName: item.capitalSourceShortName,
          value: '',
          key: item.capitalSourceId
        }
        if (item.classify === 1) {
          if (item.capitalSourceShortName === capitalSourceShortNameList[2] || item.capitalSourceShortName === capitalSourceShortNameList[3] || item.capitalSourceShortName === capitalSourceShortNameList[4] || item.capitalSourceShortName === capitalSourceShortNameList[5]) {
            let str = item.capitalSourceShortName + ':' + item.capitalSourceId + '-'
            capitalSourceArr1 += str
          }
          if (item.capitalSourceShortName === capitalSourceShortNameList[6]) {
            let str = item.capitalSourceShortName + ':' + item.capitalSourceId
            capitalSourceArr2 += str
          }
          if (item.capitalSourceShortName === capitalSourceShortNameList[0] || item.capitalSourceShortName === capitalSourceShortNameList[1]) {
            form.specialFundsForm.domains.push(obj)
          }
        } else if (item.classify === 2) {
          form.constructionFundsForm.domains.push(obj)
        }
      })
      if (formName === 'projectExpectedForm') {
        this.isShowFlag = false
        if (enterpriseClassify === 1) {
          form.specialFundsForm.domains.push({label: '预计煤炭生产量', value: '', key: capitalSourceArr1})
          this.isShowFlag = true
        } else if (enterpriseClassify === 3) {
          form.specialFundsForm.domains.push({label: '预计销售额度', value: '', key: capitalSourceArr2})
        } else if (enterpriseClassify === 4) {
          form.specialFundsForm.domains.push({label: '预计煤炭生产量', value: '', key: capitalSourceArr1})
          form.specialFundsForm.domains.push({label: '预计销售额度', value: '', key: capitalSourceArr2})
          this.isShowFlag = true
        }
      } else {
        this.isShowSecondFlag = false
        if (enterpriseClassify === 1) {
          form.specialFundsForm.domains.push({label: '计划煤炭生产量', value: '', key: capitalSourceArr1})
          this.isShowSecondFlag = true
        } else if (enterpriseClassify === 3) {
          form.specialFundsForm.domains.push({label: '计划销售额度', value: '', key: capitalSourceArr2})
        } else if (enterpriseClassify === 4) {
          form.specialFundsForm.domains.push({label: '计划煤炭生产量', value: '', key: capitalSourceArr1})
          form.specialFundsForm.domains.push({label: '计划销售额度', value: '', key: capitalSourceArr2})
          this.isShowSecondFlag = true
        }
      }
    },
    getDialogInfo (capitalSourceList, enterpriseClassify) {
      this.createFormInfoInDialog(capitalSourceList, this.projectExpectedForm, enterpriseClassify, 'projectExpectedForm')
      this.createFormInfoInDialog(capitalSourceList, this.projectPlanForm, enterpriseClassify, 'projectPlanForm')
      this.dialogCreateVisible = true
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 进行查看操作后返回当前页 */
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 计算年份-初始值为2017 */
    getYearMethed () {
      // 返回结果
      let arr = []
      // 获得当前年份
      let date = new Date()
      let nextYear = date.getFullYear() + 1
      let previousYear = date.getFullYear() - 1
      for (let i = previousYear; i <= nextYear; i++) {
        arr.push(i)
      }
      arr = arr.reverse()
      return arr
    },
    /** 搜索 */
    searchFun () {
      this.getTableData()
    },
    /** 新增 */
    addClickBtn () {
      this.dialogVisible = true
    },
    /** 表单分页 */
    handleSizeChange (val) {
      var likeThis = this
      var pageSize = `${val}`
      this.pageNo = 0
      this.currentPage = 1
      this.pageSize = parseInt(pageSize)
      this.getTableData(this.pageNo, this.pageSize, function (resp) {
        likeThis.total = resp.data.Data.Total
      })
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getTableData(parseInt(this.pageNo), parseInt(this.pageSize))
    },
    /** 取消操作 */
    cancel () {
      this.dialogVisible = false
      this.dialogForm.year = ''
      this.$refs['dialogForm'].resetFields()
    },
    cancelDialog () {
      this.dialogCreateVisible = false
      this.projectExpectedForm.specialFundsForm.domains = []
      this.projectPlanForm.specialFundsForm.domains = []
      this.projectExpectedForm.constructionFundsForm.domains = []
      this.projectPlanForm.constructionFundsForm.domains = []
      // 重置表单
      this.$refs['projectExpectedForm'].fields.forEach(function (e) {
        if (e.prop === 'prospectingRightsBenefits') {
          e.resetField()
        }
      })
      // 重置表单
      this.$refs['projectPlanForm'].fields.forEach(function (e) {
        if (e.prop === 'prospectingRightsBenefits') {
          e.resetField()
        }
      })
      this.projectExpectedForm.miningRightsType = '0'
      this.projectExpectedForm.prospectingRightsBenefits = ''
      this.projectExpectedForm.deepProcessingProductsIncome = ''
      this.projectExpectedForm.mineralTypeCoefficient = ''
      this.projectExpectedForm.influenceCoefficient = ''
      this.projectPlanForm.miningRightsType = '0'
      this.projectPlanForm.prospectingRightsBenefits = ''
      this.projectPlanForm.deepProcessingProductsIncome = ''
      this.projectPlanForm.mineralTypeCoefficient = ''
      this.projectPlanForm.influenceCoefficient = ''
      this.isShowMineral = false
      this.isShowSecondMineral = false
      this.cancel()
    },
    /** 一年只能有一个资金平衡表 */
    checkYear (year) {
      let flag = false
      if (this.tableData.length > 0) {
        for (let i = 0; i < this.tableData.length; i++) {
          if (Number(this.tableData[i].year) === Number(year)) {
            flag = true
            break
          }
        }
      }
      return flag
    },
    handleClose (done) {
      this.cancel()
      done()
    },
    handleCloseDialog (done) {
      this.cancelDialog()
      done()
    },
    /** 保存-年份-弹窗 */
    saveBtn () {
      this.$refs['dialogForm'].validate((valid) => {
        if (valid) {
          if (!this.checkYear(this.dialogForm.year)) {
            this.getAllCapitalSourceByEnterpriseType()
          } else {
            let msg = '该企业' + this.dialogForm.year + '年已存在资金平衡表，不可重复添加！'
            this.$message.error(msg)
          }
        } else {
          return false
        }
      })
    },
    /** 保存-创建表单-弹窗 */
    saveProjectForm (form1, form2) {
      let flag = ''
      this.$refs[form1].validate((valid) => {
        if (valid) {
          flag++
        } else {
          return false
        }
      })
      this.$refs[form2].validate((valid) => {
        if (valid) {
          flag++
        } else {
          return false
        }
      })
      if (flag === 2) {
        let fundsList = this.calcVariousAmounts()
        let specialFundsList = fundsList.slice(0, fundsList.length - 3)
        let constructionFundsList = fundsList.slice(fundsList.length - 3, fundsList.length)
        this.createBalanceSheet(specialFundsList, constructionFundsList)
      }
    },
    /** 建立资金平衡表 */
    createBalanceSheet (specialFundsArr, constructionFundsArr) {
      let balanceSheetInfo = {}
      let fixedQuota = 0
      let constructionQuota = 0
      let specialFundsList = specialFundsArr
      let constructionFundsList = constructionFundsArr
      if (specialFundsList.length > 0) {
        specialFundsList.forEach(item => {
          fixedQuota += Number(item.surplusFunds)
        })
      }
      if (constructionFundsList.length > 0) {
        constructionFundsList.forEach(item => {
          constructionQuota += Number(item.surplusFunds)
        })
      }
      let previousYearBalanceInfoList = [this.createPreviousYearBalanceInfo(this.projectExpectedForm, 1), this.createPreviousYearBalanceInfo(this.projectPlanForm, 2)]
      balanceSheetInfo = {
        year: this.dialogForm.year,
        riskRatio: this.riskRatio,
        status: 0, // 未提交
        fixedQuota: Number(fixedQuota).toFixed(2), // 专项资金额度
        constructionQuota: Number(constructionQuota).toFixed(2), // 工程建设资金额度
        previousYearBalanceInfoList: previousYearBalanceInfoList
      }
      balanceSheetInfo.specialFundsList = specialFundsList
      balanceSheetInfo.constructionFundsList = constructionFundsList
      balanceSheet.save(balanceSheetInfo).then(() => {
        this.$router.push({path: '/admin/planmanage/balancemanage/balancedit', query: {handleType: 'add', chooseYear: this.dialogForm.year, projectEnterpriseId: this.$store.getters.authUser.enterpriseId, currentEnterpriseClassify: this.currentEnterpriseClassify, riskRatio: this.riskRatio}})
        this.cancel()
        this.cancelDialog()
      })
    },
    /** 针对上一年度资金信息的总结 */
    createPreviousYearBalanceInfo (form, sourceType) {
      let previousYearBalanceInfo = {
        undistributedProfit: 0, // 未分配利润
        depreciationFee: 0, // 折旧（更改）费
        wellRoadwayEngineering: 0, // 井巷工程'
        dimensionalityFee: 0, // 维简费
        coalSafetyFund: 0, // 煤炭安全资金
        environmentalControlDeposit: 0, // 环境治理保证金
        chemicalsSafetyFund: 0, // 危化品安全资金
        shareholdersInjection: 0, // 股东注资
        governmentGrants: 0, // 政府补助
        projectLoan: 0, // 项目借款
        estimatedCoalProduction: 0, // 预计煤炭生产量
        plannedCoalProduction: 0, // 计划煤炭生产量
        projectedSalesQuota: 0, // 预计销售额度
        plannedSalesQuota: 0, // 计划销售额度
        sourceType: sourceType,
        miningRightsType: form.miningRightsType, // 矿业权（0.探矿权 1.采矿权）
        prospectingRightsBenefits: form.miningRightsType === '1' ? '' : Number(form.prospectingRightsBenefits).toFixed(2), // 探矿权出让收益
        deepProcessingProductsIncome: form.miningRightsType === '1' ? Number(form.deepProcessingProductsIncome).toFixed(2) : '', // 深加工产品销售收入
        mineralTypeCoefficient: form.miningRightsType === '1' ? Number(form.mineralTypeCoefficient).toFixed(2) : '', // 矿种系数
        influenceCoefficient: form.miningRightsType === '1' ? Number(form.influenceCoefficient).toFixed(2) : '' // 影响系数
      }
      form.specialFundsForm.domains.forEach(item => {
        if (item.label === '未分配利润') {
          previousYearBalanceInfo.undistributedProfit = Number(item.value).toFixed(2)
        } else if (item.label === '折旧（更改）') {
          previousYearBalanceInfo.depreciationFee = Number(item.value).toFixed(2)
        } else if (item.label === '预计煤炭生产量') {
          previousYearBalanceInfo.estimatedCoalProduction = Number(item.value).toFixed(2)
          previousYearBalanceInfo.wellRoadwayEngineering = (Number(item.value) * 2.5).toFixed(2)
          previousYearBalanceInfo.dimensionalityFee = (Number(item.value) * 6).toFixed(2)
          previousYearBalanceInfo.coalSafetyFund = (Number(item.value) * 15).toFixed(2)
          previousYearBalanceInfo.environmentalControlDeposit = form.miningRightsType === '1' ? (Number(form.deepProcessingProductsIncome) * Number(form.mineralTypeCoefficient) * Number(form.influenceCoefficient) * 0.7).toFixed(2) : (Number(form.prospectingRightsBenefits) * 0.05).toFixed(2)
        } else if (item.label === '计划煤炭生产量') {
          previousYearBalanceInfo.plannedCoalProduction = Number(item.value).toFixed(2)
          previousYearBalanceInfo.wellRoadwayEngineering = (Number(item.value) * 2.5).toFixed(2)
          previousYearBalanceInfo.dimensionalityFee = (Number(item.value) * 6).toFixed(2)
          previousYearBalanceInfo.coalSafetyFund = (Number(item.value) * 15).toFixed(2)
          previousYearBalanceInfo.environmentalControlDeposit = form.miningRightsType === '1' ? (Number(form.deepProcessingProductsIncome) * Number(form.mineralTypeCoefficient) * Number(form.influenceCoefficient) * 0.7).toFixed(2) : (Number(form.prospectingRightsBenefits) * 0.05).toFixed(2)
        } else if (item.label === '预计销售额度') {
          previousYearBalanceInfo.projectedSalesQuota = Number(item.value).toFixed(2)
          previousYearBalanceInfo.chemicalsSafetyFund = (Number(item.value) * 0.04).toFixed(2)
        } else if (item.label === '计划销售额度') {
          previousYearBalanceInfo.plannedSalesQuota = Number(item.value).toFixed(2)
          previousYearBalanceInfo.chemicalsSafetyFund = (Number(item.value) * 0.04).toFixed(2)
        }
      })
      form.constructionFundsForm.domains.forEach(item => {
        if (item.label === '股东注资') {
          previousYearBalanceInfo.shareholdersInjection = Number(item.value).toFixed(2)
        } else if (item.label === '政府补助') {
          previousYearBalanceInfo.governmentGrants = Number(item.value).toFixed(2)
        } else if (item.label === '项目借款') {
          previousYearBalanceInfo.projectLoan = Number(item.value).toFixed(2)
        }
      })
      return previousYearBalanceInfo
    },
    /**
     * 按照专项资金风险控制比例进行计算
     * 井巷工程费：2.5元/吨
     * 维简费：6元/吨
     * 煤炭安全资金：15元/吨
     * 矿山环境恢复治理保证金：
     * 在煤矿企业创建资金平衡表页面，增加‘矿业权’，按照矿业权类型分别显示不同的输入文本。
     * 探矿权=探矿权出让收益x5%
     * 采矿权=深加工产品销售收入x矿种系数x影响系数x70%
     * 危化品安全资金：年销售的4%
     */
    calcVariousAmounts () {
      let fundsList = []
      this.projectPlanForm.specialFundsForm.domains.forEach(item => {
        if (item.capitalSourceShortName === capitalSourceShortNameList[0] || item.capitalSourceShortName === capitalSourceShortNameList[1]) {
          fundsList.push(Object.assign(item, this.cal(item, item.key, 1)))
        } else if (item.label === '计划煤炭生产量') {
          let arr = item.key.split('-')
          arr.forEach(capitalSource => {
            let obj = {
              capitalSourceShortName: capitalSource.split(':')[0],
              key: capitalSource.split(':')[1]
            }
            if (obj.capitalSourceShortName === capitalSourceShortNameList[2]) {
              fundsList.push(Object.assign({label: '井巷工程费'}, this.cal(item, obj.key, 2.5)))
            } else if (obj.capitalSourceShortName === capitalSourceShortNameList[3]) {
              fundsList.push(Object.assign({label: '维简费'}, this.cal(item, obj.key, 6)))
            } else if (obj.capitalSourceShortName === capitalSourceShortNameList[4]) {
              fundsList.push(Object.assign({label: '煤炭安全资金'}, this.cal(item, obj.key, 15)))
            } else if (obj.capitalSourceShortName === capitalSourceShortNameList[5]) {
              let objValue = { // this.isShowMineral = false时候为第一种
                balanceSheetCode: '',
                capitalSourceId: obj.key,
                miningRightsType: this.projectPlanForm.miningRightsType, // 矿业权（0.探矿权 1.采矿权）
                prospectingRightsBenefits: this.projectPlanForm.miningRightsType === '1' ? '' : Number(this.projectPlanForm.prospectingRightsBenefits).toFixed(2), // 探矿权出让收益
                deepProcessingProductsIncome: this.projectPlanForm.miningRightsType === '1' ? Number(this.projectPlanForm.deepProcessingProductsIncome).toFixed(2) : '', // 深加工产品销售收入
                mineralTypeCoefficient: this.projectPlanForm.miningRightsType === '1' ? Number(this.projectPlanForm.mineralTypeCoefficient).toFixed(2) : '', // 矿种系数
                influenceCoefficient: this.projectPlanForm.miningRightsType === '1' ? Number(this.projectPlanForm.influenceCoefficient).toFixed(2) : '', // 影响系数
                surplusFunds: this.projectPlanForm.miningRightsType === '1' ? (Number(this.projectPlanForm.deepProcessingProductsIncome) * Number(this.projectPlanForm.mineralTypeCoefficient) * Number(this.projectPlanForm.influenceCoefficient) * 0.7).toFixed(2) : (Number(this.projectPlanForm.prospectingRightsBenefits) * 0.05).toFixed(2),
                riskFunds: this.projectPlanForm.miningRightsType === '1' ? (Number(this.projectPlanForm.deepProcessingProductsIncome) * Number(this.projectPlanForm.mineralTypeCoefficient) * Number(this.projectPlanForm.influenceCoefficient) * 0.7 * (Number(this.riskRatio) / 100)).toFixed(2) : (Number(this.projectPlanForm.prospectingRightsBenefits) * 0.05 * (Number(this.riskRatio) / 100)).toFixed(2),
                availableFunds: this.projectPlanForm.miningRightsType === '1' ? (Number(this.projectPlanForm.deepProcessingProductsIncome) * Number(this.projectPlanForm.mineralTypeCoefficient) * Number(this.projectPlanForm.influenceCoefficient) * 0.7 * (1 - (Number(this.riskRatio) / 100))).toFixed(2) : (Number(this.projectPlanForm.prospectingRightsBenefits) * 0.05 * (1 - (Number(this.riskRatio) / 100))).toFixed(2)
              }
              fundsList.push(Object.assign({label: '环境治理保证金'}, objValue))
            }
          })
        } else if (item.label === '计划销售额度') {
          fundsList.push(Object.assign(item, this.cal(item, item.key.split(':')[1], 0.04)))
        }
      })
      this.projectPlanForm.constructionFundsForm.domains.forEach(item => {
        fundsList.push(Object.assign(item, this.cal(item, item.key, 1)))
      })
      return fundsList
    },
    cal (item, capitalSourceId, unitPrice) {
      let obj = {
        balanceSheetCode: '',
        capitalSourceId: capitalSourceId,
        inputData: item.value,
        surplusFunds: (Number(item.value) * Number(unitPrice)).toFixed(2),
        riskFunds: (Number(item.value) * Number(unitPrice) * (Number(this.riskRatio) / 100)).toFixed(2),
        availableFunds: (Number(item.value) * Number(unitPrice) * (1 - (Number(this.riskRatio) / 100))).toFixed(2)
        /* enterpriseId: this.$store.getters.authUser.enterpriseId */
      }
      return obj
    },
    /** 表格操作 */
    handleTableClick (row, type) {
      if (Object.is(type, 'look')) {
        this.$router.push({path: '/admin/planmanage/balancemanage/balancedetails', query: {code: row.code, year: row.year, projectEnterpriseId: row.enterpriseId, currentEnterpriseClassify: row.enterpriseClassify, riskRatio: row.riskRatio}})
      } else if (Object.is(type, 'del')) {
        this.deleteClick(row.objectId)
      } else if (Object.is(type, 'edit')) {
        // 编辑
        this.$router.push({path: '/admin/planmanage/balancemanage/balancedit', query: {handleType: 'edit', chooseYear: row.year, projectEnterpriseId: row.enterpriseId, currentEnterpriseClassify: row.enterpriseClassify, riskRatio: row.riskRatio}})
      }
    },
    // 删除
    deleteClick (objectId) {
      this.$confirm('此操作永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 删除
        balanceSheet.delete(objectId).then((res) => {
          if (res.data.resCode === '0000') {
            this.getTableData()
          } else {
            return false
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    }
  },
  computed: {},
  // 实例创建完成
  mounted () {
  }
}
</script>
<style lang="less">
#balancemanage-page {
  .balance-search {
    .el-form .el-form-item {
      margin-bottom: 0;
    }
  }
  .balance-add {
    text-align: left;
    padding-bottom: 20px;
  }
  // 分页
  .block-page {
    padding-top:15px;
  }
}
</style>
