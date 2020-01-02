<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="balanceedit-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>计划管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/planmanage/balancemanage' }">资金平衡表管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/planmanage/balancemanage/balancedit' }">
          {{(this.$route.query.handleType =='add')?'创建':'编辑'}}
        </el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <div class="maincont-box">
      <div class="warp-balance">
        <h2>{{this.$route.query.chooseYear}}年资金平衡表</h2>
        <div>
          <span class="balance-title right-title">专项资金风险控制比例：{{this.riskRatio}}%</span>
        </div>
        <el-table
          v-loading="loading"
          :data="specialFundsTableData"
          border
          :summary-method="getSummaries"
          show-summary
          style="width: 100%">
          <el-table-column
            prop="fundsFrom"
            label="资金来源"
            align="center">
          </el-table-column>
          <el-table-column
            prop="inputData"
            :label="(this.$route.query.chooseYear - 1) + '年完成情况（万元）'"
            align="center">
          </el-table-column>
          <el-table-column
            :label="this.$route.query.chooseYear + '年资金平衡表'"
            align="center">
            <el-table-column
              prop="surplusFunds"
              :label= "'累计结余及' + (this.$route.query.chooseYear - 1) + '年提取数（万元）'"
              align="center">
            </el-table-column>
            <el-table-column
              prop="riskFunds"
              :label="'减' + this.riskRatio + '%风险资金（万元）'"
              align="center">
            </el-table-column>
            <el-table-column
              prop="availableFunds"
              :label="this.$route.query.chooseYear + '年建议计划可用资金（万元）'"
              align="center">
            </el-table-column>
          </el-table-column>
        </el-table>
      </div>
      <div class="handle-btn">
        <el-button type="success" @click="handleBtnEvents('submit')">提 交</el-button>
        <el-button type="warning" @click="handleBtnEvents('edit')">修 改</el-button>
        <el-button type="primary" @click="handleBtnEvents('look')">查 看</el-button>
        <el-button type="info" @click="$router.go(-1)">取 消</el-button>
      </div>
    </div>
    <!--弹窗区域start-->
    <div class="dialog-box">
      <el-dialog
        :title="(dialogTitle)? '编辑': '查看'"
        :visible.sync="dialogVisible"
        width="60%"
        center>
        <p><strong>{{this.$route.query.chooseYear - 1}}年项目预计完成情况</strong></p>
        <el-form :model="projectExpectedForm" ref="projectExpectedForm" label-width="150px">
          <el-row :gutter="20">
            <el-col :span="11">
              <el-form-item
                v-for="(domain, index) in projectExpectedForm.specialFundsForm.domains"
                :label="domain.label"
                :key="domain.key"
                :prop="'specialFundsForm.domains.' + index + '.value'"
                :rules="domain.capitalSourceShortName !== 'WFPLR'  ? ((domain.label === '预计煤炭生产量'|| domain.label === '计划煤炭生产量') ? rules.value3 : rules.value1) : rules.value2">
                <el-input v-model="domain.value" style="width:190px" v-if="(domain.label !== '预计煤炭生产量' && domain.label !== '计划煤炭生产量') && !lookFlag"></el-input><span v-if="(domain.label !== '预计煤炭生产量'&& domain.label !== '计划煤炭生产量') && !lookFlag">万元</span>
                <el-input v-model="domain.value" style="width:190px" v-if="(domain.label === '预计煤炭生产量' || domain.label === '计划煤炭生产量') && !lookFlag"></el-input><span v-if="(domain.label === '预计煤炭生产量' || domain.label === '计划煤炭生产量') && !lookFlag">万吨</span>
                <span v-if="(domain.label !== '预计煤炭生产量' && domain.label !== '计划煤炭生产量') && lookFlag">{{domain.value}}&nbsp;&nbsp;万元</span>
                <span v-if="(domain.label === '预计煤炭生产量' || domain.label === '计划煤炭生产量') && lookFlag">{{domain.value}}&nbsp;&nbsp;万吨</span>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item
                v-for="(domain, index) in projectExpectedForm.constructionFundsForm.domains"
                :label="domain.label"
                :key="domain.key"
                :prop="'constructionFundsForm.domains.' + index + '.value'"
                :rules="rules.value1">
                <el-input v-model="domain.value" style="width:190px" v-if="!lookFlag"></el-input><span v-if="!lookFlag">万元</span>
                <span v-if="lookFlag">{{domain.value}}&nbsp;&nbsp;万元</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20" v-if="!lookFlag && isShowFlag">
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
          <el-row :gutter="20"  v-if="isShowMineral && !lookFlag && isShowFlag">
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
          <el-row :gutter="20" v-if="lookFlag && isShowFlag">
            <el-col :span="11">
              <el-form-item
                label="矿业权"
                prop="miningRightsType"
                :rules="rules.miningRightsType">
                {{projectExpectedForm.miningRightsType === '0' ? '探矿权': '采矿权'}}
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item
                v-if="!isShowMineral"
                label="探矿权出让收益"
                prop="prospectingRightsBenefits"
                :rules="rules.prospectingRightsBenefits">
                {{projectExpectedForm.prospectingRightsBenefits}}<span>&nbsp;&nbsp;万元</span>
              </el-form-item>
              <el-form-item
                v-if="isShowMineral"
                label="深加工产品销售收入"
                prop="deepProcessingProductsIncome"
                :rules="rules.deepProcessingProductsIncome">
                {{projectExpectedForm.deepProcessingProductsIncome}}<span>万元</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20"  v-if="isShowMineral && lookFlag && isShowFlag">
            <el-col :span="11">
              <el-form-item
                label="矿种系数"
                prop="mineralTypeCoefficient"
                :rules="rules.mineralTypeCoefficient">
                {{projectExpectedForm.mineralTypeCoefficient}}
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item
                label="影响系数"
                prop="influenceCoefficient"
                :rules="rules.influenceCoefficient">
                {{projectExpectedForm.influenceCoefficient}}
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <p><strong>{{this.$route.query.chooseYear}}年项目计划情况（本年度专项资金风险比例为{{this.riskRatio}}%）</strong></p>
        <el-form :model="projectPlanForm" ref="projectPlanForm" label-width="150px">
          <el-row :gutter="20">
            <el-col :span="11">
              <el-form-item
                v-for="(domain, index) in projectPlanForm.specialFundsForm.domains"
                :label="domain.label"
                :key="domain.key"
                :prop="'specialFundsForm.domains.' + index + '.value'"
                :rules="domain.capitalSourceShortName !== 'WFPLR' ? ((domain.label === '预计煤炭生产量'|| domain.label === '计划煤炭生产量') ? rules.value3 : rules.value1) : rules.value2">
                <el-input v-model="domain.value" style="width:190px" v-if="(domain.label !== '预计煤炭生产量' && domain.label !== '计划煤炭生产量') && !lookFlag"></el-input><span v-if="(domain.label !== '预计煤炭生产量'&& domain.label !== '计划煤炭生产量') && !lookFlag">万元</span>
                <el-input v-model="domain.value" style="width:190px" v-if="(domain.label === '预计煤炭生产量' || domain.label === '计划煤炭生产量') && !lookFlag"></el-input><span v-if="(domain.label === '预计煤炭生产量' || domain.label === '计划煤炭生产量') && !lookFlag">万吨</span>
                <span v-if="(domain.label !== '预计煤炭生产量' && domain.label !== '计划煤炭生产量') && lookFlag">{{domain.value}}&nbsp;&nbsp;万元</span>
                <span v-if="(domain.label === '预计煤炭生产量' || domain.label === '计划煤炭生产量') && lookFlag">{{domain.value}}&nbsp;&nbsp;万吨</span>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item
                v-for="(domain, index) in projectPlanForm.constructionFundsForm.domains"
                :label="domain.label"
                :key="domain.key"
                :prop="'constructionFundsForm.domains.' + index + '.value'"
                :rules="rules.value1">
                <el-input v-model="domain.value" style="width:190px" v-if="!lookFlag"></el-input><span v-if="!lookFlag">万元</span>
                <span v-if="lookFlag">{{domain.value}}&nbsp;&nbsp;万元</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20" v-if="!lookFlag && isShowSecondFlag">
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
            <el-row :gutter="20"  v-if="isShowSecondMineral && !lookFlag && isShowSecondFlag">
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
          <el-row :gutter="20" v-if="lookFlag && isShowSecondFlag">
            <el-col :span="11">
              <el-form-item
                label="矿业权"
                prop="miningRightsType"
                :rules="rules.miningRightsType">
                {{projectPlanForm.miningRightsType === '0' ? '探矿权': '采矿权'}}
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item
                v-if="!isShowSecondMineral"
                label="探矿权出让收益"
                prop="prospectingRightsBenefits"
                :rules="rules.prospectingRightsBenefits">
                {{projectPlanForm.prospectingRightsBenefits}}<span>&nbsp;&nbsp;万元</span>
              </el-form-item>
              <el-form-item
                v-if="isShowSecondMineral"
                label="深加工产品销售收入"
                prop="deepProcessingProductsIncome"
                :rules="rules.deepProcessingProductsIncome">
                {{projectPlanForm.deepProcessingProductsIncome}}<span>&nbsp;&nbsp;万元</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20"  v-if="isShowSecondMineral && lookFlag && isShowSecondFlag">
            <el-col :span="11">
              <el-form-item
                label="矿种系数"
                prop="mineralTypeCoefficient"
                :rules="rules.mineralTypeCoefficient">
                {{projectPlanForm.mineralTypeCoefficient}}
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item
                label="影响系数"
                prop="influenceCoefficient"
                :rules="rules.influenceCoefficient">
                {{projectPlanForm.influenceCoefficient}}
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="saveProjectForm('projectExpectedForm', 'projectPlanForm')" v-if="!lookFlag">保存</el-button>
          <el-button @click="cancel()" v-if="!lookFlag">取消</el-button>
          <el-button @click="dialogVisible = false" v-if="lookFlag" type="primary">确定</el-button>
        </span>
      </el-dialog>
    </div>
    <!--弹窗区域end-->
  </div>
</template>
<script>
// 实例
import {isvalidPrice, isvalidPriceForNegative, isvalidPriceForCoal, isvalidPriceForCoefficient} from '../../../../../assets/js/validate'
import {balanceSheet, investmanage} from 'api/index'
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
      specialFundsTableData: [], // 专项资金
      engineerTableData: [{fundsFrom: '股东注资'}], // 工程建设
      dialogTitle: true,
      dialogVisible: false,
      lookFlag: false,
      isShowMineral: false,
      projectExpectedForm: {
        specialFundsForm: {
          domains: []
        },
        constructionFundsForm: {
          domains: []
        },
        miningRightsType: '', // 矿业权（0.探矿权 1.采矿权）
        prospectingRightsBenefits: '', // 探矿权出让收益
        deepProcessingProductsIncome: '', // 深加工产品销售收入
        mineralTypeCoefficient: '', // 矿种系数
        influenceCoefficient: '' // 影响系数
      }, // 项目预计
      expectedRulesForm: {},
      isShowSecondMineral: false,
      projectPlanForm: {
        specialFundsForm: {
          domains: []
        },
        constructionFundsForm: {
          domains: []
        },
        miningRightsType: '', // 矿业权（0.探矿权 1.采矿权）
        prospectingRightsBenefits: '', // 探矿权出让收益
        deepProcessingProductsIncome: '', // 深加工产品销售收入
        mineralTypeCoefficient: '', // 矿种系数
        influenceCoefficient: '' // 影响系数
      }, // 项目计划
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
      projectPlanRulesForm: {},
      currentBalanceSheetInfo: {},
      currentEnterpriseClassify: this.$route.query.currentEnterpriseClassify, // 当前用户所在企业的企业类型
      projectEnterpriseId: this.$route.query.projectEnterpriseId,
      chooseYear: this.$route.query.chooseYear,
      loading: true,
      riskRatio: this.$route.query.riskRatio,
      isShowFlag: false, // 采矿权等信息是否展示
      isShowSecondFlag: false
    }
  },
  created () {
    // this.getEnterpriseClassify()
    // 获取资金来源信息
    this.createTableFundsType()
  },
  methods: {
    /** 矿业权-chang事件 */
    changeMinRight (val) {
      // 重置表单
      this.$refs['projectExpectedForm'].fields.forEach(function (e) {
        if (e.prop === 'prospectingRightsBenefits') {
          e.resetField()
        }
      })
      // 重置信息
      this.projectExpectedForm.prospectingRightsBenefits = '' // 探矿权出让收益
      this.projectExpectedForm.deepProcessingProductsIncome = '' // 深加工产品销售收入
      this.projectExpectedForm.mineralTypeCoefficient = '' // 矿种系数
      this.projectExpectedForm.influenceCoefficient = '' // 影响系数
      if (val !== '0') {
        this.isShowMineral = true
      } else {
        this.isShowMineral = false
      }
    },
    changeSecondMinRight (val) {
      // 重置表单
      this.$refs['projectPlanForm'].fields.forEach(function (e) {
        if (e.prop === 'prospectingRightsBenefits') {
          e.resetField()
        }
      })
      // 重置信息
      this.projectPlanForm.prospectingRightsBenefits = '' // 探矿权出让收益
      this.projectPlanForm.deepProcessingProductsIncome = '' // 深加工产品销售收入
      this.projectPlanForm.mineralTypeCoefficient = '' // 矿种系数
      this.projectPlanForm.influenceCoefficient = '' // 影响系数
      if (val !== '0') {
        this.isShowSecondMineral = true
      } else {
        this.isShowSecondMineral = false
      }
    },
    /** 获取当前列表上的数据 */
    getCurrentData () {
      balanceSheet.getOne(this.projectEnterpriseId, this.chooseYear).then((res) => {
        this.currentBalanceSheetInfo = res.data.fundsInfo
        this.getFormInfo(res.data.fundsInfo)
        this.loading = false
      })
    },
    /** 获取每行的数据 (资金来源对象,上一年资金平衡表信息，当前年的资金平衡表信息) */
    getRowInfo (item, previousYearBalanceInfo, specialFundsList, constructionFundsList) {
      if (item.capitalSourceShortName === capitalSourceShortNameList[0]) {
        item.inputData = previousYearBalanceInfo.undistributedProfit
        let rowInfo = this.getInfo(specialFundsList, item.capitalSourceId)
        item.surplusFunds = rowInfo.surplusFunds
        item.riskFunds = rowInfo.riskFunds
        item.availableFunds = rowInfo.availableFunds
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[1]) {
        item.inputData = previousYearBalanceInfo.depreciationFee
        let rowInfo = this.getInfo(specialFundsList, item.capitalSourceId)
        item.surplusFunds = rowInfo.surplusFunds
        item.riskFunds = rowInfo.riskFunds
        item.availableFunds = rowInfo.availableFunds
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[2]) {
        item.inputData = previousYearBalanceInfo.wellRoadwayEngineering
        let rowInfo = this.getInfo(specialFundsList, item.capitalSourceId)
        item.surplusFunds = rowInfo.surplusFunds
        item.riskFunds = rowInfo.riskFunds
        item.availableFunds = rowInfo.availableFunds
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[3]) {
        item.inputData = previousYearBalanceInfo.dimensionalityFee
        let rowInfo = this.getInfo(specialFundsList, item.capitalSourceId)
        item.surplusFunds = rowInfo.surplusFunds
        item.riskFunds = rowInfo.riskFunds
        item.availableFunds = rowInfo.availableFunds
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[4]) {
        item.inputData = previousYearBalanceInfo.coalSafetyFund
        let rowInfo = this.getInfo(specialFundsList, item.capitalSourceId)
        item.surplusFunds = rowInfo.surplusFunds
        item.riskFunds = rowInfo.riskFunds
        item.availableFunds = rowInfo.availableFunds
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[5]) {
        item.inputData = previousYearBalanceInfo.environmentalControlDeposit
        let rowInfo = this.getInfo(specialFundsList, item.capitalSourceId)
        item.surplusFunds = rowInfo.surplusFunds
        item.riskFunds = rowInfo.riskFunds
        item.availableFunds = rowInfo.availableFunds
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[6]) {
        item.inputData = previousYearBalanceInfo.chemicalsSafetyFund
        let rowInfo = this.getInfo(specialFundsList, item.capitalSourceId)
        item.surplusFunds = rowInfo.surplusFunds
        item.riskFunds = rowInfo.riskFunds
        item.availableFunds = rowInfo.availableFunds
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[7]) {
        item.inputData = previousYearBalanceInfo.shareholdersInjection
        let rowInfo = this.getInfo(constructionFundsList, item.capitalSourceId)
        item.surplusFunds = rowInfo.surplusFunds
        item.riskFunds = rowInfo.riskFunds
        item.availableFunds = rowInfo.availableFunds
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[8]) {
        item.inputData = previousYearBalanceInfo.governmentGrants
        let rowInfo = this.getInfo(constructionFundsList, item.capitalSourceId)
        item.surplusFunds = rowInfo.surplusFunds
        item.riskFunds = rowInfo.riskFunds
        item.availableFunds = rowInfo.availableFunds
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[9]) {
        item.inputData = previousYearBalanceInfo.projectLoan
        let rowInfo = this.getInfo(constructionFundsList, item.capitalSourceId)
        item.surplusFunds = rowInfo.surplusFunds
        item.riskFunds = rowInfo.riskFunds
        item.availableFunds = rowInfo.availableFunds
      }
      return item
    },
    /** 获取页面上的数据 */
    getFormInfo (fundsInfo) {
      // 上一年资金平衡表信息，当前年的资金平衡表信息
      let previousYearBalanceInfo = {}
      fundsInfo.balanceSheet.previousYearBalanceInfoList.forEach(item => {
        if (item.sourceType === 1) {
          previousYearBalanceInfo = item
        }
      })
      // 专项资金
      let specialFundsList = fundsInfo.specialFundsList
      // 工程建设资金
      let constructionFundsList = fundsInfo.constructionFundsList
      let index = 0
      this.specialFundsTableData.forEach(item => {
        index++
        item = this.getRowInfo(item, previousYearBalanceInfo, specialFundsList, constructionFundsList)
        this.$set(this.specialFundsTableData, this.specialFundsTableData[index], item)
      })
    },
    getInfo (arr, capitalSourceId) {
      let rowInfo = {}
      for (let i = 0; i < arr.length; i++) {
        if (Number(arr[i].capitalSourceId) === Number(capitalSourceId)) {
          rowInfo = arr[i]
          break
        }
      }
      return rowInfo
    },
    /** 构建当前用户的资金来源数据 */
    createTableFundsType () {
      if (this.currentEnterpriseClassify === null || this.enterpriseClassify === '') {
        investmanage.getCapitalSourceList({}).then((res) => {
          res.data.capitalSourceList.forEach(item => {
            let obj = {
              fundsFrom: item.name,
              capitalSourceShortName: item.shortName,
              capitalSourceId: item.objectId
            }
            this.specialFundsTableData.push(obj)
          })
        })
      } else {
        investmanage.selectCapitalSourceByEnterpriseType(this.currentEnterpriseClassify).then((res) => {
          res.data.enterpriseTypeCapitalSourceList.forEach(item => {
            let obj = {
              fundsFrom: item.capitalSourceName,
              capitalSourceShortName: item.capitalSourceShortName,
              capitalSourceId: item.capitalSourceId
            }
            this.specialFundsTableData.push(obj)
          })
        })
      }
      this.getCurrentData()
    },
    /** 获取对应企业类型下的所有资金来源 */
    getAllCapitalSourceByEnterpriseType (enterpriseClassify) {
      if (enterpriseClassify !== null && enterpriseClassify !== '') {
        investmanage.selectCapitalSourceByEnterpriseType(enterpriseClassify).then((res) => {
          this.getDialogInfo(res.data.enterpriseTypeCapitalSourceList, enterpriseClassify)
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
          this.getDialogInfo(capitalSourceList, enterpriseClassify)
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
        if (Number(enterpriseClassify) === 1) {
          form.specialFundsForm.domains.push({label: '预计煤炭生产量', value: '', key: capitalSourceArr1})
          this.isShowFlag = true
        } else if (Number(enterpriseClassify) === 3) {
          form.specialFundsForm.domains.push({label: '预计销售额度', value: '', key: capitalSourceArr2})
        } else if (enterpriseClassify === 4) {
          form.specialFundsForm.domains.push({label: '预计煤炭生产量', value: '', key: capitalSourceArr1})
          form.specialFundsForm.domains.push({label: '预计销售额度', value: '', key: capitalSourceArr2})
          this.isShowFlag = true
        }
      } else {
        this.isShowSecondFlag = false
        if (Number(enterpriseClassify) === 1) {
          form.specialFundsForm.domains.push({label: '计划煤炭生产量', value: '', key: capitalSourceArr1})
          this.isShowSecondFlag = true
        } else if (Number(enterpriseClassify) === 3) {
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
      this.getDialogFormInfo(this.currentBalanceSheetInfo)
    },
    /** 获取弹框中输入框的取值 */
    getInputDialogValue (item, yearBalanceInfo) {
      if (item.capitalSourceShortName === capitalSourceShortNameList[0]) {
        item.value = yearBalanceInfo.undistributedProfit
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[1]) {
        item.value = yearBalanceInfo.depreciationFee
      } else if (item.label === '预计煤炭生产量') {
        item.value = yearBalanceInfo.estimatedCoalProduction
      } else if (item.label === '计划煤炭生产量') {
        item.value = yearBalanceInfo.plannedCoalProduction
      } else if (item.label === '预计销售额度') {
        item.value = yearBalanceInfo.projectedSalesQuota
      } else if (item.label === '计划销售额度') {
        item.value = yearBalanceInfo.plannedSalesQuota
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[7]) {
        item.value = yearBalanceInfo.shareholdersInjection
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[8]) {
        item.value = yearBalanceInfo.governmentGrants
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[9]) {
        item.value = yearBalanceInfo.projectLoan
      }
      return item
    },
    /** 获取弹框上的数据 */
    getDialogFormInfo (fundsInfo) {
      let previousYearBalanceInfoList = fundsInfo.balanceSheet.previousYearBalanceInfoList
      let previousYearBalanceInfo = {}
      let currentYearBalanceInfo = {}
      previousYearBalanceInfoList.forEach(item => {
        if (item.sourceType === 1) {
          previousYearBalanceInfo = item
        } else {
          currentYearBalanceInfo = item
        }
      })
      // 获取矿业权等信息
      if (previousYearBalanceInfo) {
        this.projectExpectedForm.miningRightsType = previousYearBalanceInfo.miningRightsType.toString() // 矿业权（0.探矿权 1.采矿权）
        if (this.projectExpectedForm.miningRightsType === '0') {
          this.isShowMineral = false
        } else if (this.projectExpectedForm.miningRightsType === '1') {
          this.isShowMineral = true
        }
        this.projectExpectedForm.prospectingRightsBenefits = previousYearBalanceInfo.prospectingRightsBenefits // 探矿权出让收益
        this.projectExpectedForm.deepProcessingProductsIncome = previousYearBalanceInfo.deepProcessingProductsIncome // 深加工产品销售收入
        this.projectExpectedForm.mineralTypeCoefficient = previousYearBalanceInfo.mineralTypeCoefficient // 矿种系数
        this.projectExpectedForm.influenceCoefficient = previousYearBalanceInfo.influenceCoefficient // 影响系数
      }
      if (currentYearBalanceInfo) {
        this.projectPlanForm.miningRightsType = currentYearBalanceInfo.miningRightsType.toString() // 矿业权（0.探矿权 1.采矿权）
        if (this.projectPlanForm.miningRightsType === '0') {
          this.isShowSecondMineral = false
        } else if (this.projectPlanForm.miningRightsType === '1') {
          this.isShowSecondMineral = true
        }
        this.projectPlanForm.prospectingRightsBenefits = currentYearBalanceInfo.prospectingRightsBenefits // 探矿权出让收益
        this.projectPlanForm.deepProcessingProductsIncome = currentYearBalanceInfo.deepProcessingProductsIncome // 深加工产品销售收入
        this.projectPlanForm.mineralTypeCoefficient = currentYearBalanceInfo.mineralTypeCoefficient // 矿种系数
        this.projectPlanForm.influenceCoefficient = currentYearBalanceInfo.influenceCoefficient // 影响系数
      }
      let index = 0
      this.projectExpectedForm.specialFundsForm.domains.forEach(item => {
        index++
        item = this.getInputDialogValue(item, previousYearBalanceInfo)
        this.$set(this.projectExpectedForm.specialFundsForm.domains, this.projectExpectedForm.specialFundsForm.domains[index], item)
      })
      index = 0
      this.projectExpectedForm.constructionFundsForm.domains.forEach(item => {
        index++
        item = this.getInputDialogValue(item, previousYearBalanceInfo)
        this.$set(this.projectExpectedForm.constructionFundsForm.domains, this.projectExpectedForm.constructionFundsForm.domains[index], item)
      })
      index = 0
      this.projectPlanForm.specialFundsForm.domains.forEach(item => {
        index++
        item = this.getInputDialogValue(item, currentYearBalanceInfo)
        this.$set(this.projectPlanForm.specialFundsForm.domains, this.projectPlanForm.specialFundsForm.domains[index], item)
      })
      index = 0
      this.projectPlanForm.constructionFundsForm.domains.forEach(item => {
        index++
        item = this.getInputDialogValue(item, currentYearBalanceInfo)
        this.$set(this.projectPlanForm.constructionFundsForm.domains, this.projectPlanForm.constructionFundsForm.domains[index], item)
      })
      if (this.lookFlag) {
        this.dialogTitle = false
      } else {
        this.dialogTitle = true
      }
      this.dialogVisible = true
    },
    /** 保存-提交 */
    handleBtnEvents (name) {
      this.lookFlag = false
      // 清空上次的数据
      this.projectExpectedForm.specialFundsForm.domains = []
      this.projectExpectedForm.constructionFundsForm.domains = []
      this.projectPlanForm.specialFundsForm.domains = []
      this.projectPlanForm.constructionFundsForm.domains = []
      if (Object.is(name, 'look')) {
        this.getAllCapitalSourceByEnterpriseType(this.currentEnterpriseClassify)
        this.lookFlag = true
      } else if (Object.is(name, 'edit')) {
        // 获取弹框中的资金来源信息
        this.getAllCapitalSourceByEnterpriseType(this.currentEnterpriseClassify)
      } else if (Object.is(name, 'submit')) {
        this.$confirm('确定要提交吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          beforeClose: (action, instance, done) => {
            done()
          }
        }).then(() => {
          let balanceSheetInfo = this.currentBalanceSheetInfo.balanceSheet
          balanceSheetInfo.status = 1
          balanceSheetInfo.submitter = this.$store.getters.authUser.userId
          balanceSheetInfo.submitDate = new Date()
          balanceSheet.save(balanceSheetInfo).then((res) => {
            if (res.data.resCode === '0000') {
              this.$router.push({path: '/admin/planmanage/balancemanage'})
            }
          })
        }).catch(() => {
          return false
        })
      }
    },
    cancel () {
      this.lookFlag = false
      this.dialogVisible = false
    },
    /** 保存-弹窗 */
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
      /* let fundsInfo = {
        balanceSheet: {},
        specialFundsList: [],
        constructionFundsList: []
      } */
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
        objectId: this.currentBalanceSheetInfo.balanceSheet.objectId,
        code: this.currentBalanceSheetInfo.balanceSheet.code,
        firstBalanceCode: this.currentBalanceSheetInfo.balanceSheet.firstBalanceCode,
        relatedBalanceCode: this.currentBalanceSheetInfo.balanceSheet.code,
        year: this.$route.query.chooseYear,
        status: 0, // 未提交
        fixedQuota: Number(fixedQuota).toFixed(2), // 专项资金额度
        constructionQuota: Number(constructionQuota).toFixed(2), // 工程建设资金额度
        previousYearBalanceInfoList: previousYearBalanceInfoList
      }
      balanceSheetInfo.specialFundsList = specialFundsList
      balanceSheetInfo.constructionFundsList = constructionFundsList
      balanceSheet.save(balanceSheetInfo).then(() => {
        this.dialogVisible = false
        this.specialFundsTableData = []
        this.createTableFundsType()
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
        if (item.capitalSourceShortName === capitalSourceShortNameList[0]) {
          previousYearBalanceInfo.undistributedProfit = Number(item.value).toFixed(2)
        } else if (item.capitalSourceShortName === capitalSourceShortNameList[1]) {
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
        if (item.capitalSourceShortName === capitalSourceShortNameList[7]) {
          previousYearBalanceInfo.shareholdersInjection = Number(item.value).toFixed(2)
        } else if (item.capitalSourceShortName === capitalSourceShortNameList[8]) {
          previousYearBalanceInfo.governmentGrants = Number(item.value).toFixed(2)
        } else if (item.capitalSourceShortName === capitalSourceShortNameList[9]) {
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
     * 矿山环境恢复治理保证金：10元/吨煤
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
    /** 返回当前页面上该元素存在的下表索引 */
    getArrIndex (itemName) {
      let index = -1
      if (this.specialFundsTableData.length > 0) {
        for (let i = 0; i < this.specialFundsTableData.length; i++) {
          if (this.specialFundsTableData[i].fundsFrom === itemName) {
            index = i
          }
        }
      }
      return index
    },
    /** 表格合计 */
    getSummaries (param) {
      const { columns, data } = param
      const sums = []
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '总计'
          return
        }
        const values = data.map(item => Number(item[column.property]))
        if (!values.every(value => isNaN(value))) {
          sums[index] = values.reduce((prev, curr) => {
            const value = Number(curr)
            if (!isNaN(value)) {
              // return Number(prev + curr).toFixed(2)
              return prev + curr
            } else {
              // return Number(prev).toFixed(2)
              return prev
            }
          }, 0)
          sums[index] = sums[index].toFixed(2)
          // sums[index] += ' 元'
        } else {
          sums[index] = 'N/A'
        }
      })
      return sums
    }
  },
  computed: {},
  // 实例创建完成
  mounted () {
  }
}
</script>
<style lang="less">
  #balanceedit-page {
    /*资金平衡表*/
    .warp-balance, .warp-engineer {
      .balance-title {
        display: inline-block;
        padding-bottom: 10px;
        font-weight: 800;
      }
      .left-title {
        float: left;
      }
      .right-title {
        float: right;
      }
    }
    .warp-engineer {
      padding-top: 30px;
    }
    .handle-btn {
      padding-top: 40px;
    }
  }
</style>
