<template>
  <div class="supervise-calculator-box" id="supervise-calculator">
    <div class="show-warp">
      <el-row :gutter="20">
        <el-col :span="12">
          <div class="bid-agent-box">
            <p class="bid-p">
              <img src="./images/1.png" alt="施工监理费">
              <span class="bid-span span-left">施工监理费</span>
            </p>
            <el-form :rules="rules" :model="ruleForm" ref="ruleForm" label-width="140px" style="margin-top: 20px;">
              <el-form-item label="项目名称:" prop="projectName">
                <el-autocomplete
                  class="inline-input"
                  v-model="ruleForm.projectName"
                  :fetch-suggestions="searchProject"
                  placeholder="请输入内容"
                  :trigger-on-focus="false"
                  @select="handleSelect"
                  style="width: 100%;"
                ></el-autocomplete>
              </el-form-item>
              <el-form-item label="厂区/矿区:" prop="calculatorType">
                <el-select v-model="ruleForm.calculatorType" placeholder="请选择" style="width: 100%;" :disabled="parentsToChild==='1' ? false : true" @change="changeCalculatorType">
                  <el-option label="厂区施工监理费用" value="0"></el-option>
                  <el-option label="矿区施工监理费用" value="1"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="计费额:" prop="chargeAmount">
                <el-input placeholder="请输入金额" v-model="ruleForm.chargeAmount" @change="calChargeSupervise(ruleForm, parentsToChild)">
                  <template slot="append">万元</template>
                </el-input>
              </el-form-item>
              <el-form-item label="施工监理收费基价:" prop="chargeSupervise">
                <el-input placeholder="请输入金额" v-model="ruleForm.chargeSupervise" :disabled="true">
                  <template slot="append">万元</template>
                </el-input>
              </el-form-item>
              <el-form-item label="专业调整系数:" prop="professional">
                <el-input placeholder="请选择" v-model="ruleForm.professional" style="width: 83%;" :disabled="true">
                </el-input>
                <el-button type="text" @click="getInfo(1)" style="margin-left:8px;">请选择</el-button>
              </el-form-item>
              <el-form-item label="复杂程度调整系数:" prop="complexity">
                <el-input placeholder="请选择" v-model="ruleForm.complexity" style="width: 83%;" :disabled="true">
                </el-input>
                <el-button type="text" @click="getInfo(2)" style="margin-left:8px;">请选择</el-button>
              </el-form-item>
              <el-form-item label="高程调整系数:" prop="altitude">
                <el-input placeholder="请选择" v-model="ruleForm.altitude" style="width: 83%;" :disabled="true">
                </el-input>
                <el-button type="text" @click="getInfo(3)" style="margin-left:8px;">请选择</el-button>
              </el-form-item>
              <div v-show="parentsToChild==='2'">
                <el-form-item label="优惠折扣:" prop="rate">
                  <el-input placeholder="请输入折扣" v-model="ruleForm.rate" >
                    <template slot="append">%</template>
                  </el-input>
                </el-form-item>
              </div>
              <div v-show="parentsToChild==='1'">
                <el-form-item label="优惠折扣:">
                  <el-button type="info" @click="lookdiscoutData(ruleForm, parentsToChild)" >查看优惠说明</el-button>
                </el-form-item>
              </div>
              <div>
                <el-button type="primary" round @click="clickCompute('ruleForm',parentsToChild)" style="background-color: #3C61F2;border-color:#3C61F2;">点击计算</el-button>
              </div>
            </el-form>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="compute-result-box">
            <p class="bid-p">
              <img src="./images/2.png" alt="施工监理费计算结果">
              <span class="bid-span span-right">施工监理费计算结果</span>
            </p>
            <el-form ref="form" :model="costConstResultForm" label-width="160px" style="margin-top: 20px;">
              <el-form-item label="折前施工监理服务收费:">
                {{projectComputProcress.originalPrice}}万元
              </el-form-item>
              <el-form-item label="折后施工监理服务收费:">
                {{projectComputProcress.discountPrice}}万元
              </el-form-item>
              <el-form-item label="计算过程:" style="background-color: #eee;line-height: 48px;height: 300px;overflow: auto;">
                <div class="compute-process">
                  <ul>
                    <li>1. 项目名称：{{projectComputProcress.projectName}}</li>
                    <li>2. 项目归属：{{this.$store.getters.authUser.enterpriseName}}</li>
                    <li>3. 监理费用类型：{{projectComputProcress.calculatorType === '0' ? '厂区施工监理费用': (projectComputProcress.calculatorType === '1' ? '矿区施工监理费用': '')}}</li>
                    <li>4. 计费额：{{projectComputProcress.calculatorPrice}}万元</li>
                    <li>5. 施工监理收费基价计算: {{projectComputProcress.supervisionBasePriceDetail}}万元</li>
                    <li>6. 折前施工监理服务收费计算：{{projectComputProcress.originalPriceDetail}}万元</li>
                    <li>7. 折后使用监理服务费用计算：{{projectComputProcress.discountPriceDetail}}万元</li>
                  </ul>
                </div>
              </el-form-item>
              <div>
                <el-button type="primary" round @click="saveRecord(parentsToChild)" :disabled="saveDisabledFlag" style="background-color: #84CE95;border-color:#84CE95;">保存记录</el-button>
              </div>
            </el-form>
          </div>
        </el-col>
      </el-row>
    </div>
    <!-- 搜索satrt -->
    <div class="show-warp">
      <el-form :inline="true" :model="searchForm" class="demo-form-inline search-form">
        <el-form-item label="项目名称:">
          <el-input v-model="searchForm.messageLike" clearable placeholder="项目名称"></el-input>
        </el-form-item>
        <el-form-item label="监理费用类型:">
           <el-select v-model="searchForm.calculatorType" clearable placeholder="请选择项目类型" style="width: 100%;">
            <el-option label="厂区施工监理费用" value="0"></el-option>
            <el-option label="矿区施工监理费用" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getComputerHistory">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!-- 搜索end -->
    <div class="show-warp history-box">
      <p class="history-p">
        <span class="span-l">计算历史:</span>
        <span class="span-r">单位: 万元</span>
      </p>
      <el-table
        :data="calculatorHistoryList"
        border
        style="width: 100%">
        <el-table-column
          type="index"
          label="序号"
          align="center"
          width="55"
          :index="indexMethod">
        </el-table-column>
        <el-table-column
          prop="projectName"
          label="项目名称"
          header-align="center">
        </el-table-column>
        <el-table-column
          prop="calculatorType"
          label="厂区/矿区"
          header-align="center"
          :formatter="formatter">
        </el-table-column>
        <el-table-column
          prop="calculatorPrice"
          label="计费额（万元）"
          header-align="center">
        </el-table-column>
        <el-table-column
          prop="originalPrice"
          label="折前价格"
          header-align="center">
        </el-table-column>
        <el-table-column
          prop="discountPrice"
          label="折后价格"
          header-align="center">
        </el-table-column>
        <el-table-column
          label="操作"
          header-align="center">
          <template slot-scope="scope">
            <el-button type="text" @click="handleTableBtn('look', scope.row)">查看</el-button>
            <el-button type="text" @click="handleTableBtn('del', scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
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
      <div class="rules-box">
        <p class="rule-tit">规则:</p>
        <ul>
          <li>1. 采用差价累积法进行计算，先行标准如右侧列表所示。</li>
          <li>2. 整体采用两种模式：煤气化模式、其他模式；煤气化模式时优惠折扣不可编辑（固定值），其他模式可以自行编辑折扣值。</li>
          <li>3. 计算结果不可编辑。</li>
          <li>4. 计算结果可进行保存，保存的记录可查看，可删除。</li>
          <li>5. 可对计算的价格区间进行配置化管理，配置化管理详见“系统管理”模块下的“计算器配置”。</li>
        </ul>
      </div>
    </div>
    <!-- 弹窗区域start -->
    <div class="dialog-box">
      <el-dialog
        title="计算过程"
        :visible.sync="dialogVisible"
        width="40%"
        center>
        <el-form label-width="238px" :model="calculatorHistory">
          <el-form-item label="项目名称:">
            <span>{{calculatorHistory.projectName}}</span>
          </el-form-item>
          <el-form-item label="项目归属:">
            <span>{{calculatorHistory.enterpriseName}}</span>
          </el-form-item>
          <el-form-item label="监理费用类型:">
            <span>{{calculatorHistory.calculatorType === 0 ? '厂区施工监理费用': (calculatorHistory.calculatorType === 1 ? '矿区施工监理费用': '')}}</span>
          </el-form-item>
          <el-form-item label="计费额(万元):">
            <span>{{calculatorHistory.calculatorPrice}}</span>
          </el-form-item>
          <el-form-item label="施工监理收费基价计算(万元):">
            <span>{{calculatorHistory.supervisionBasePriceDetail}}</span>
          </el-form-item>
          <el-form-item label="折前招施工监理服务收费计算(万元):">
            <span>{{calculatorHistory.originalPriceDetail}}</span>
          </el-form-item>
          <el-form-item label="折后招施工监理服务收费计算(万元):">
            <span>{{calculatorHistory.discountPriceDetail}}</span>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
        </span>
      </el-dialog>
      <!-- 优惠折扣弹窗start -->
      <el-dialog
        :title="discountStr"
        :visible.sync="lookdiscoutDialog"
        width="50%"
        center>
        <el-table
          :data="costConstRateList"
          border
          style="width: 100%">
          <el-table-column
            prop="chargeAmount"
            label="计费额（万元）"
            align="center">
           <!-- <template slot-scope="scope">
              <span v-if="scope.row.type===1">小于{{scope.row.priceMax}}</span>
              <span v-else-if="scope.row.type===2">{{scope.row.priceMin}}-{{scope.row.priceMax}}</span>
              <span v-else>大于{{scope.row.priceMin}}</span>
            </template> -->
          </el-table-column>
          <el-table-column
            prop="chargeBasePrice"
            label="国家收费基价（万元）"
            align="center">
          </el-table-column>
          <el-table-column
            prop="discount"
            label="优惠折扣"
            align="center">
            <template slot-scope="scope">
              <span>{{scope.row.discount}}%</span>
            </template>
          </el-table-column>
        </el-table>
        <!-- <div class="note-box">
          <p>注:计费额大于1000000万元的，以计费额乘以1.039%的收费率计算收费基价，其他未包含的其他收费有双方协商议定。</p>
        </div> -->
        <div class="data-source">
          <p>{{dataSource}}</p>
        </div>
      </el-dialog>
      <!-- 优惠折扣弹窗end -->

      <!-- 专业调整系数弹窗start -->
      <el-dialog
        title="专业调整系数选择"
        :visible.sync="professionalDialog"
        width="50%"
        center>
        <div class="professional-table">
          <el-table
            :show-header="false"
            :data="professionalTableData"
            @expand-change="toggleRowExpansion"
            @row-click="toggleRowExpansion"
            :row-key="getRowKeys"
            :expand-row-keys="expendRowKeys"
            style="width: 100%">
            <el-table-column type="expand">
              <template slot-scope="props">
                <el-form label-position="left" class="demo-table-expand">
                  <el-form-item :label="item.label" v-for="(item, index) in props.row.elementTreeModels" :key="index">
                   <!-- <span
                      v-for="(level, index) in item.levels"
                      :key="index"
                      @mouseover="onmouseoverProfessional(level)"
                      @click="clickProfessionalLevel(level)"
                      class="level-name">
                      {{ level.nameL }}
                    </span>-->
                    <span @mouseover="onmouseoverProfessional(item)" @click="clickProfessionalLevel(item)" class="level-name">{{ item.value }}</span>
                  </el-form-item>
                </el-form>
              </template>
            </el-table-column>
            <el-table-column
              label="名称"
              prop="name">
            </el-table-column>
          </el-table>
        </div>
      </el-dialog>
      <!-- 专业调整系数弹窗end -->
      <!-- 复杂程度调整系数弹窗start -->
      <el-dialog
        title=" 复杂程度调整系数选择"
        :visible.sync="complexityDialog"
        width="50%"
        center>
        <div class="complexity-table">
          <el-table
            :show-header="false"
            :data="complexityTableData"
            @expand-change="toggleRowExpansion"
            @row-click="toggleRowExpansion"
            :row-key="getRowKeys"
            :expand-row-keys="expendRowKeys"
            style="width: 100%">
            <el-table-column type="expand">
              <template slot-scope="props">
                <el-form label-position="left" class="demo-table-expand">
                  <el-form-item :label="item.label" v-for="(item, index) in props.row.elementTreeModels" :key="index">
                    <span
                      v-for="(level, index) in item.children"
                      :key="index"
                      @mouseover="onmouseoverLevel(level)"
                      @click="clickLevel(level)"
                      class="level-name">
                      {{ level.label }}
                    </span>
                  </el-form-item>
                </el-form>
              </template>
            </el-table-column>
            <el-table-column
              label="名称"
              prop="name">
            </el-table-column>
          </el-table>
          <div class="details-cont" v-html="detailsContComplexity">
            {{detailsContComplexity}}
          </div>
        </div>
      </el-dialog>
      <!-- 复杂程度调整系数弹窗end -->

      <!-- 高程调整系数弹窗start -->
      <el-dialog
        title="高程调整系数选择"
        :visible.sync="altitudeyDialog"
        width="30%"
        center>
        <div>
          <el-radio-group v-model="altitudeyRadio" class="altitudey-radio" @change="changeAltitudeyRadio">
            <el-radio :label="item.value" v-for="(item, index) in altitudeyRadioData" :key="index">{{item.name}}</el-radio>
            <!-- <el-radio :label="0.1">2001米以下</el-radio>
            <el-radio :label="1.1">2001~3000米</el-radio>
            <el-radio :label="1.2">3001~3500米</el-radio>
            <el-radio :label="1.3">3501~4000米</el-radio> -->
            <el-radio :label="1.4" class="custom-radio">自定义</el-radio>
            <el-input type="text" v-model="myAltitudeyRadio" size="medium" style="width:30%;"></el-input>
          </el-radio-group>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="altitudeyDialog = false">取 消</el-button>
          <el-button type="primary" @click="clickAltitudeyDialog">确 定</el-button>
        </span>
      </el-dialog>
      <!-- 高程调整系数弹窗end -->
    </div>
    <!-- 弹窗区域end -->
  </div>
</template>
<script>
import { bidagent, costConst, supervisionRate } from 'api/index'
export default {
  name: 'cost-calculator',
  data () {
    var validatePrice = (rule, value, callback) => {
      if (value && (!(/^[1-9]\d{0,11}(\.?\d{0,2})?$|(\d\.\d{1,2}$)/).test(value))) {
        callback(new Error('请输入正确的计费额'))
      } else {
        callback()
      }
    }
    var validateRate = (rule, value, callback) => {
      if (value && (!(/^\d{1,14}(\.?\d{0,2})?$/).test(value))) {
        callback(new Error('请输入正确折扣优惠'))
      } else {
        callback()
      }
    }
    return {
      dataSource: '', // 数据来源
      expendRowKeys: [], // 展开行
      getRowKeys (row) {
        return row.objectId
      },
      professionalTableData: [],
      detailsContProfessional: '',
      complexityTableData: [], // 复杂程度调整系数-表格数据
      detailsContComplexity: '',
      altitudeyRadioData: [],
      altitudeyRadio: '',
      myAltitudeyRadio: '', // 自定义-单选
      professionalDialog: false, // 专业调整系数-弹窗
      complexityDialog: false, // 复杂程度调整系数-弹窗
      altitudeyDialog: false, // 高程调整系数-弹窗
      lookdiscoutDialog: false, // 优惠折扣-弹窗
      saveDisabledFlag: true, // 保存按钮是否可用标识符
      ruleForm: {
        projectName: '',
        calculatorType: '',
        chargeAmount: '',
        calculatorPrice: '',
        professional: '',
        complexity: '',
        altitude: '',
        rate: ''
      },
      searchForm: {}, // 搜索表单
      costConstResultForm: {},
      projectList: [], // 模糊查询项目列表
      calculatorHistory: {}, // 单条计算记录
      calculatorHistoryList: [], // 计算记录分页数据
      dialogVisible: false, // 弹窗是否展示标识符
      bidAgentResultForm: {}, // 招标代理费计算结果-表单
      rules: {
        projectName: [
          { required: true, message: '项目名称不能为空', trigger: 'blur' },
          { max: 100, message: '长度不能大于100个字符', trigger: 'blur' }
        ],
        chargeAmount: [
          { required: true, message: '请输入计费额', trigger: 'blur' },
          {validator: validatePrice}
        ],
        calculatorType: [
          {required: true, message: '请选择厂区/矿区信息', trigger: ['blur', 'change']}
        ],
        professional: [
          { required: true, message: '专业调整系数不能为空', trigger: ['blur', 'change'] }
        ],
        complexity: [
          { required: true, message: '复杂程度调整系数不能为空', trigger: ['blur', 'change'] }
        ],
        altitude: [
          { required: true, message: '高程调整系数不能为空', trigger: ['blur', 'change'] }
        ],
        rate: [
          { required: true, message: '优惠折扣不能为空', trigger: 'blur' },
          { validator: validateRate }
        ]
      },
      currentPage: 1,
      pageNo: 0,
      pageSize: 10,
      total: 0, // 总条数
      pageSizeList: [10, 20, 30, 40, 50],
      costConstRateList: [], // 代理机构费计算依据费率表
      discountStr: '',
      lowerInfo: {}, // 计费额下限
      upperInfo: {}, // 计费额上限
      projectComputProcress: { // 计算记录信息
        projectCode: '', // 关联项目编号
        projectName: '', // 项目名称
        projectEnterpriseId: '', // 项目归属企业
        calculatorType: '', // 计算类型(10、厂区施工监理费用 11、矿区施工监理费用)
        calculatorPrice: '', // 计费额
        discountType: '', // 折扣类型（1.煤气化模式 2.其他模式）
        type: 4, // 记录类型（4.施工监理费计算历史）
        supervisionBasePrice: '', // 施工监理收费基价
        supervisionBasePriceDetail: '',
        originalPrice: '', // 折前施工监理服务收费
        originalPriceDetail: '',
        discountPrice: '', // 折后施工监理服务收费
        discountPriceDetail: '',
        discount: '' // 其他模式下录入的值
      }
    }
  },
  props: {
    parentsToChild: String
  },
  watch: {
    parentsToChild: function (newV, oldV) {
      if (newV) {
        if (newV === '1') { // 煤气化模式
          delete this.$refs['ruleForm'].rules.rate
          this.$refs['ruleForm'].rules.calculatorType = [
            {required: true, message: '请选择厂区/矿区信息', trigger: ['blur', 'change']}
          ]
        } else {
          delete this.$refs['ruleForm'].rules.calculatorType
          this.$refs['ruleForm'].rules.rate = [{ required: true, message: '优惠折扣不能为空', trigger: 'blur' },
            { validator: this.validateRate, trigger: 'blur' }]
        }
        this.ruleForm.calculatorType = ''
      }
    }
  },
  // 实例创建
  created () {
    this.init()
    this.getComputerHistory()
  },
  filters: {},
  methods: {
    /** 修改厂区、矿区信息后重新计算收费基价 */
    changeCalculatorType () {
      this.calChargeSupervise(this.ruleForm, this.parentsToChild)
    },
    /** 展开当前行 */
    toggleRowExpansion (row) {
      this.expendRowKeys = []
      this.expendRowKeys.push(row.objectId)
    },
    /** 类型常量转换为中文 */
    formatter (row, column) {
      if (row.calculatorType === 0) {
        return '厂区施工监理费用'
      } else if (row.calculatorType === 1) {
        return '矿区施工监理费用'
      } else {
        return ''
      }
    },
    /** 获取施工监理收费基价 */
    calChargeSupervise (form, parentsToChild) {
      if (parentsToChild === '1' && form && form.calculatorType === '') {
        this.$message({
          type: 'warning',
          message: '请选择厂区/矿区信息'
        })
        return false
      } else if (form.chargeAmount !== '' && (/^[1-9]\d{0,11}(\.?\d{0,2})?$|(\d\.\d{1,2}$)/).test(form.chargeAmount)) {
        supervisionRate.getUpperLowLimit(parentsToChild === '1' ? form.calculatorType : '-1', form.chargeAmount, parentsToChild === '1' ? 0 : 1).then((res) => {
          if (res.data.constructionSupervisionRateList) {
            res.data.constructionSupervisionRateList.forEach(item => {
              item.chargeAmount = Number(item.chargeAmount)
              item.chargeBasePrice = Number(item.chargeBasePrice)
              item.discount = Number(item.discount)
              return item
            })
            this.lowerInfo = res.data.constructionSupervisionRateList[0]
            if (res.data.constructionSupervisionRateList.length > 1) {
              this.upperInfo = res.data.constructionSupervisionRateList[1]
            }
            this.ruleForm.chargeSupervise = this.cal(1)
            this.$set(this.ruleForm, this.ruleForm.chargeSupervise, this.cal(1))
          }
        })
      }
    },
    /** 各类计算操作 */
    cal (type) {
      let result = ''
      let percent = 0.01
      let percentTag = '%'
      let mulTag = '×'
      switch (Number(type)) {
        case 1: // 计算施工监理收费基价(国家收费基价下限 + （国家收费基价上限 - 国家收费基价下限）/（计费额上限 - 计费额下限）*（计费额实际值 - 计费额下限）)
          if (Object.keys(this.upperInfo).length !== 0) {
            result = (this.lowerInfo.chargeBasePrice + (this.upperInfo.chargeBasePrice - this.lowerInfo.chargeBasePrice) / (this.upperInfo.chargeAmount - this.lowerInfo.chargeAmount) * (this.ruleForm.chargeAmount - this.lowerInfo.chargeAmount)).toFixed(4)
            this.projectComputProcress.supervisionBasePriceDetail = '(' + this.lowerInfo.chargeBasePrice + '+(' + this.upperInfo.chargeBasePrice + '-' + this.lowerInfo.chargeBasePrice + ')/(' + this.upperInfo.chargeAmount + '-' + this.lowerInfo.chargeAmount + ')*(' + this.ruleForm.chargeAmount + '-' + this.lowerInfo.chargeAmount + ')=' + result
          } else {
            result = this.lowerInfo.chargeBasePrice
            this.projectComputProcress.supervisionBasePriceDetail = this.lowerInfo.chargeBasePrice + '+0=' + result
          }
          this.projectComputProcress.supervisionBasePrice = result
          break
        case 2: // 计算折前施工监理服务收费
          result = (this.projectComputProcress.supervisionBasePrice * Number(this.ruleForm.professional) * Number(this.ruleForm.complexity) * Number(this.ruleForm.altitude)).toFixed(4)
          this.projectComputProcress.originalPrice = result
          this.projectComputProcress.originalPriceDetail = this.projectComputProcress.supervisionBasePriceDetail.substring(0, this.projectComputProcress.supervisionBasePriceDetail.indexOf('=')) + mulTag + this.ruleForm.professional + mulTag + this.ruleForm.complexity + mulTag + this.ruleForm.altitude + '=' + result
          break
        case 3: // 折后施工监理服务收费
          if (this.parentsToChild === '1') {
            result = (this.projectComputProcress.originalPrice * this.lowerInfo.discount * percent).toFixed(4)
            this.projectComputProcress.discountPriceDetail = this.projectComputProcress.originalPriceDetail.substring(0, this.projectComputProcress.originalPriceDetail.indexOf('=')) + mulTag + this.lowerInfo.discount + percentTag + '=' + result
          } else {
            result = (this.projectComputProcress.originalPrice * this.ruleForm.rate * percent).toFixed(4)
            this.projectComputProcress.discountPriceDetail = this.projectComputProcress.originalPriceDetail.substring(0, this.projectComputProcress.originalPriceDetail.indexOf('=')) + mulTag + this.ruleForm.rate + percentTag + '=' + result
          }
          this.projectComputProcress.discountPrice = result
          break
      }
      return result
    },
    /** 获取系数信息 */
    getInfo (type) {
      supervisionRate.getRatioInfo({
        type: type
      }).then((res) => {
        if (res.data.resCode === '0000') {
          if (type === 1) {
            this.professionalTableData = res.data.constructionSupervisionRatioInfoList
            this.professionalDialog = true
          }
          if (type === 2) {
            this.complexityTableData = res.data.constructionSupervisionRatioInfoList
            this.complexityDialog = true
          }
          if (type === 3) {
            this.altitudeyRadioData = res.data.constructionSupervisionRatioInfoList
            this.altitudeyDialog = true
          }
        }
      })
    },
    handleNodeClick (data) {},
    /** 专业调整系数-悬乎 */
    onmouseoverProfessional (content) {
      this.detailsContProfessional = content.content
    },
    /** 专业调整系数-点击 */
    clickProfessionalLevel (level) {
      this.ruleForm.professional = level.value
      this.professionalDialog = false
    },
    /** 复杂程度调整系数-悬乎  */
    onmouseoverLevel (content) {
      this.detailsContComplexity = content.description
    },
    /** 复杂程度调整系数-点击期数 */
    clickLevel (level) {
      this.ruleForm.complexity = level.value
      this.complexityDialog = false
    },
    /** 高程调整系数选择-事件 */
    changeAltitudeyRadio (val) {
      // console.log(val)
      if (val !== 1.4) {
        this.myAltitudeyRadio = ''
      }
    },
    /** 高程调整系数弹窗-确定 */
    clickAltitudeyDialog () {
      this.ruleForm.altitude = (this.myAltitudeyRadio !== '') ? this.myAltitudeyRadio : this.altitudeyRadio
      if (this.altitudeyRadio === 1.4 && this.myAltitudeyRadio === '') {
        this.$message({
          type: 'warning',
          message: '自定义不能为空'
        })
        return false
      } else {
        this.altitudeyDialog = false
      }
    },
    /** 查看折扣优惠 */
    lookdiscoutData (form, parentsToChild) {
      if (parentsToChild === '1' && form && form.calculatorType === '') {
        this.$message({
          type: 'warning',
          message: '请选择厂区/矿区信息'
        })
        return false
      } else {
        supervisionRate.getList({
          type: form.calculatorType
        }).then((res) => {
          if (Number(form.calculatorType) === 0) {
            this.discountStr = '厂区施工监理费用服务收费基价表'
          } else {
            this.discountStr = '矿区施工监理费用服务收费基价表'
          }
          this.costConstRateList = res.data.constructionSupervisionRateList
          this.dataSource = ''
          if (res.data.calculatorInfo) {
            this.dataSource = res.data.calculatorInfo.dataSource
          }
          this.lookdiscoutDialog = true
        })
      }
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 查询项目 */
    searchProject (projectNameLike, cb) {
      if (projectNameLike) {
        this.projectList = []
        bidagent.getProjectList({
          messageLike: projectNameLike,
          enterpriseId: this.$store.getters.authUser.enterpriseId
        }).then((res) => {
          if (res.data.selectApplyList.list.length > 0) {
            res.data.selectApplyList.list.forEach((item, index) => {
              let projectResult = {}
              projectResult.value = item.projectName
              projectResult.code = item.code
              projectResult.enterpriseId = item.enterpriseId
              this.projectList.push(projectResult)
            })
          }
        })
      }
      cb(this.projectList)
    },
    /** 选择项目 */
    handleSelect (iteam) {
      this.ruleForm.projectName = iteam.value
      this.projectComputProcress.projectCode = iteam.code
      this.projectComputProcress.projectName = iteam.value
      this.projectComputProcress.projectEnterpriseId = iteam.enterpriseId
    },
    /** 点击计算 */
    clickCompute (formName, parentsToChild) {
      if (parentsToChild === '1') { // 煤气化模式
        delete this.$refs[formName].rules.rate
        this.$refs[formName].rules.calculatorType = [
          {required: true, message: '请选择厂区/矿区信息', trigger: ['blur', 'change']}
        ]
      } else {
        delete this.$refs[formName].rules.calculatorType
        this.$refs[formName].rules.rate = [{ required: true, message: '优惠折扣不能为空', trigger: 'blur' },
          { validator: this.validateRate, trigger: 'blur' }]
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.cal(2)
          this.cal(3)
          this.projectComputProcress.projectName = this.ruleForm.projectName
          this.projectComputProcress.calculatorType = this.ruleForm.calculatorType
          this.projectComputProcress.calculatorPrice = this.ruleForm.chargeAmount
          this.projectComputProcress.discount = this.ruleForm.rate
          this.saveDisabledFlag = false
        }
      })
    },
    /** 保存记录 */
    saveRecord (parentsToChild) {
      if (this.projectComputProcress.originalPriceDetail) { // 如果存在记录
        this.projectComputProcress.projectEnterpriseId = this.$store.getters.authUser.enterpriseId
        this.projectComputProcress.discountType = parentsToChild
        this.projectComputProcress.discout = this.ruleForm.rate
        this.projectComputProcress.objectId = ''
        this.projectComputProcress.type = 4
        bidagent.save(this.projectComputProcress).then((res) => {
          if (res.data.resCode === '0000') {
            this.getComputerHistory()
            this.saveDisabledFlag = true
            this.ruleForm = {
              projectName: '',
              calculatorType: '',
              calculatorPrice: '',
              professional: '',
              complexity: '',
              altitude: '',
              rate: ''
            }
            // 重置表单
            this.$refs['ruleForm'].resetFields()
            this.projectComputProcress = { // 计算记录信息
              projectCode: '', // 关联项目编号
              projectName: '', // 项目名称
              projectEnterpriseId: '', // 项目归属企业
              calculatorType: '', // 计算类型(10、厂区施工监理费用 11、矿区施工监理费用)
              calculatorPrice: '', // 计费额
              discountType: '', // 折扣类型（1.煤气化模式 2.其他模式）
              type: 4, // 记录类型（4.施工监理费计算历史）
              supervisionBasePrice: '', // 施工监理收费基价
              supervisionBasePriceDetail: '',
              originalPrice: '', // 折前施工监理服务收费
              originalPriceDetail: '',
              discountPrice: '', // 折后施工监理服务收费
              discountPriceDetail: '',
              discount: '' // 其他模式下录入的值
            }
          }
        })
      }
    },
    /** 表格操作 */
    handleTableBtn (type, row) {
      if (type === 'look') {
        this.calculatorHistory = {}
        this.dialogVisible = true
        bidagent.selectOne(row.objectId).then((res) => {
          if (res.data.calculatorHistory) {
            this.calculatorHistory = res.data.calculatorHistory
          }
        })
      } else if (type === 'del') {
        this.deleteClick(row)
      }
    },
    // 删除方法
    deleteClick (row) {
      this.$confirm('此操作永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 执行删除方法
        bidagent.del(row.objectId).then((res) => {
          if (res.data.resCode === '0000') {
            this.getComputerHistory()
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    /** 初始化加载数据 */
    init () {
      costConst.getList().then((res) => {
        if (res.data.costConsultationRateList.length > 0) {
          this.costConstRateList = res.data.costConsultationRateList
        }
      })
    },
    /** 获取计算历史数据 */
    getComputerHistory () {
      let query = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        type: 4,
        messageLike: this.searchForm.messageLike,
        calculatorType: this.searchForm.calculatorType
      }
      bidagent.getComputerHistory(query).then((res) => {
        if (res.data.calculatorHistoryList) {
          this.calculatorHistoryList = res.data.calculatorHistoryList.list
          this.total = res.data.calculatorHistoryList.total
        }
      })
    },
    /** 分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.getComputerHistory()
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getComputerHistory()
    }
  }
}
</script>
<style lang="less">
#supervise-calculator {
  .show-warp {
    background: #fff;
    margin-top: 20px;
    padding: 0 15px;
    .el-form-item__content {
      text-align: left;
    }
    .search-form {
      padding-top: 22px;
      text-align: left;
    }
    .history-p {
      padding: 10px 0;
      .span-l {
        float: left;
      }
      .span-r {
        float: right;
      }
    }
    .block-page {
      margin-top: 15px;
    }
  }
  .bid-agent-box , .compute-result-box {
    height: 605px;
    padding: 15px 10px;
    margin: 15px 0;
    border: 1px solid #eee;
    .bid-p {
      padding-left: 10px;
      text-align: left;
    }
    img {
      vertical-align: middle;
    }
    .bid-span {
      padding-left: 20px;
      font-size: 16px;
    }
  }
  .bid-agent-box {
    .el-input-group__append {
      width: 30px;
    }
    .el-input__icon {
      display: none;
    }
    .span-left {
      color: #3C61F2;
    }
  }
  .compute-result-box {
    .span-right {
      color: #84CE95;
    }
    .compute-process {
      ul {
        list-style: none;
        margin: 0;
        padding: 10px;
      }
      ul > li {
        line-height: 2;
      }
    }
  }
  .history-box {
    margin-bottom: 20px;
    padding-bottom: 20px;
  }
  .rules-box {
    background: #eee;
    padding-bottom: 10px;
    ul {
      list-style: none;
      margin-top: 0;
    }
    ul >li {
      line-height: 2;
      text-align: left;
    }
    .rule-tit {
      text-align: left;
      font-size: 16px;
      padding: 10px;
      margin-bottom: 0;
    }
  }
  .dialog-box {
    .altitudey-radio {
      .el-radio {
        width: 100%;
        line-height: 3;
      }
      .custom-radio {
        width: auto;
      }
    }
    .professional-table {
      .el-form-item {
        margin-bottom: 0;
      }
      .el-form-item:hover {
        background: #eee;
        cursor: pointer;
      }
      .el-form-item__content {
        text-align: right;
      }
      .level-name {
        display: block;
      }
    }
    .complexity-table {
      .el-form-item {
        margin-bottom: 0;
      }
      .level-name {
        display: inline-block;
        margin: 5px;
        padding: 0 10px;
        background: #eee;
      }
      .level-name:hover {
        background: #1197F1;
        color: #fff;
      }
    }
    .details-cont {
      width: 80%;
      min-height: 100px;
      margin: 0 auto;
      border: 1px solid #ddd;
      padding: 8px;
      margin-top: 20px;
    }
  }
}
</style>
