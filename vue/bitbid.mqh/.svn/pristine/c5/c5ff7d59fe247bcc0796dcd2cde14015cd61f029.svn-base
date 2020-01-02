<template>
  <div class="agent-calculator-box" id="agent-calculator">
    <div class="show-warp">
      <el-row :gutter="20">
        <el-col :span="12">
          <div class="bid-agent-box">
            <p class="bid-p">
              <img src="./images/1.png" alt="招标代理费">
              <span class="bid-span span-left">招标代理费</span>
            </p>
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" style="margin-top: 20px;">
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
              <el-form-item label="项目类型:" prop="calculatorType">
                <el-select v-model="ruleForm.calculatorType" placeholder="请选择项目类型" style="width: 100%;">
                  <el-option label="工程招标" value="1"></el-option>
                  <el-option label="货物招标" value="2"></el-option>
                  <el-option label="服务招标" value="3"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="项目价格:" prop="calculatorPrice">
                <el-input placeholder="请输入金额" v-model="ruleForm.calculatorPrice">
                  <template slot="append">万元</template>
                </el-input>
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
                  <el-button type="info" @click="lookDiscountData()" >查看优惠说明</el-button>
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
              <img src="./images/2.png" alt="招标代理费计算结果">
              <span class="bid-span span-right">招标代理费计算结果</span>
            </p>
            <el-form ref="form" :model="bidAgentResultForm" label-width="125px" style="margin-top: 20px;">
              <el-form-item label="折前招标代理费:">
                {{projectComputProcress.originalPrice}}万元
              </el-form-item>
              <el-form-item label="折后招标代理费:">
                {{projectComputProcress.discountPrice}}万元
              </el-form-item>
              <el-form-item label="计算过程:" style="background-color: #eee;line-height: 48px;height: 220px;overflow: auto;">
                <div class="compute-process">
                  <ul>
                    <li>1. 项目名称：{{projectComputProcress.projectName}}</li>
                    <li>2. 项目归属：{{this.$store.getters.authUser.enterpriseName}}</li>
                    <li>3. 项目类型：{{projectComputProcress.calculatorType === '1' ? '工程招标' : projectComputProcress.calculatorType === '2' ? '货物招标' : projectComputProcress.calculatorType === '3' ? '服务招标' : ''}}</li>
                    <li>4. 项目价格：{{projectComputProcress.calculatorPrice}}万元</li>
                    <li>5. 招标代理费用：{{projectComputProcress.originalPriceDetail}}万元(依据差额定律累进法)</li>
                    <li>6. 折后价格：{{projectComputProcress.discountPriceDetail}}万元</li>
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
        <el-form-item label="项目类型:">
          <el-select v-model="searchForm.calculatorType" placeholder="项目类型">
            <el-option label="所有类型" value=""></el-option>
            <el-option label="工程招标" value="1"></el-option>
            <el-option label="货物招标" value="2"></el-option>
            <el-option label="服务招标" value="3"></el-option>
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
          :formatter="formatter"
          label="项目类型"
          header-align="center">
        </el-table-column>
        <el-table-column
          prop="calculatorPrice"
          label="项目价格"
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
        <el-form label-width="195px" :model="calculatorHistory">
          <el-form-item label="项目名称:">
            <span>{{calculatorHistory.projectName}}</span>
          </el-form-item>
          <el-form-item label="项目归属:">
            <span>{{calculatorHistory.enterpriseName}}</span>
          </el-form-item>
          <el-form-item label="项目类型:">
            <span>{{calculatorHistory.calculatorType === '1' ? '工程招标' : calculatorHistory.calculatorType === '2' ? '货物招标' : '服务招标'}}</span>
          </el-form-item>
          <el-form-item label="项目价格(万元):">
            <span>{{calculatorHistory.calculatorPrice}}</span>
          </el-form-item>
          <el-form-item label="招标代理费用计算(万元):">
            <span>{{calculatorHistory.originalPriceDetail}}</span>
          </el-form-item>
          <el-form-item label="折后招标代理费用计算(万元):">
            <span>{{calculatorHistory.discountPriceDetail}}</span>
          </el-form-item>
          <el-form-item label="折前招标代理费(万元):">
            <span>{{calculatorHistory.originalPrice}}</span>
          </el-form-item>
          <el-form-item label="折后招标代理费(万元):">
            <span>{{calculatorHistory.discountPrice}}</span>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
        </span>
      </el-dialog>

      <!-- 优惠折扣弹窗start -->
      <el-dialog
        title="太原煤气化（集团）公司招标代理费现行计算规则"
        :visible.sync="lookDiscountDialog"
        width="50%"
        center>
        <el-table
          :data="discountTableData"
          border
          style="width: 100%">
          <el-table-column
            prop="date"
            label="中标金额（万元）"
            align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.type===1">小于{{scope.row.priceMax}}</span>
              <span v-else-if="scope.row.type===2">{{scope.row.priceMin}}-{{scope.row.priceMax}}</span>
              <span v-else>大于{{scope.row.priceMin}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="goodsRate"
            align="center"
            label="货物">
            <template slot-scope="scope">
              <span>{{scope.row.goodsRate}}%</span>
            </template>
          </el-table-column>
           <el-table-column
            prop="serviceRate"
            align="center"
            label="服务">
             <template slot-scope="scope">
               <span>{{scope.row.serviceRate}}%</span>
             </template>
          </el-table-column>
           <el-table-column
            prop="engineeringRate"
            align="center"
            label="工程">
             <template slot-scope="scope">
               <span>{{scope.row.engineeringRate}}%</span>
             </template>
          </el-table-column>
           <el-table-column
            prop="discount"
            align="center"
            label="折扣优惠">
             <template slot-scope="scope">
               <span>{{scope.row.discount}}%</span>
             </template>
          </el-table-column>
        </el-table>
        <div class="data-source">
          <p>{{dataSource}}</p>
        </div>
      </el-dialog>
      <!-- 优惠折扣弹窗end -->
    </div>
    <!-- 弹窗区域end -->
  </div>
</template>
<script>
import { bidagent, computer } from 'api/index'
import { Message } from 'element-ui'
export default {
  name: 'agent-calculator',
  data () {
    var validatePrice = (rule, value, callback) => {
      if (value && (!(/^\d{1,14}(\.?\d{0,2})?$/).test(value))) {
        callback(new Error('请输入正确项目价格'))
      } else {
        callback()
      }
    }
    var validatRate = (rule, value, callback) => {
      if (value && (!(/^\d{1,14}(\.?\d{0,2})?$/).test(value))) {
        callback(new Error('请输入正确折扣优惠'))
      } else {
        callback()
      }
    }
    return {
      dataSource: '', // 数据来源
      discountTableData: [], // 折扣查询表格
      lookDiscountDialog: false, // 优惠折扣弹窗
      saveDisabledFlag: true, // 保存按钮是否可用标识符
      ruleForm: {
        projectName: '',
        calculatorType: '',
        calculatorPrice: '',
        rate: ''
      },
      searchForm: {}, // 搜索表单
      projectList: [], // 模糊查询项目列表
      calculatorHistory: {}, // 单条计算记录
      calculatorHistoryList: [], // 计算记录分页数据
      projectComputProcress: {}, // 当前项目计算数据
      dialogVisible: false, // 弹窗是否展示标识符
      bidAgentResultForm: {}, // 招标代理费计算结果-表单
      rules: {
        projectName: [
          { required: true, message: '项目名称不能为空', trigger: 'blur' },
          { max: 100, message: '长度不能大于100个字符', trigger: 'blur' }
        ],
        calculatorType: [
          { required: true, message: '项目类型不能为空', trigger: 'change' }
        ],
        calculatorPrice: [
          { required: true, message: '项目价格不能为空', trigger: 'blur' },
          { validator: validatePrice }
        ],
        rate: [
          { required: true, message: '优惠折扣不能为空', trigger: 'blur' },
          { validator: validatRate }
        ]
      },
      currentPage: 1,
      pageNo: 0,
      pageSize: 10,
      total: 0, // 总条数
      pageSizeList: [10, 20, 30, 40, 50],
      bidAgentRateList: [] // 代理机构费计算依据费率表
    }
  },
  props: {
    parentsToChild: String
  },
  watch: {
    parentsToChild: function (newV, oldV) {
      if (newV) {
        // this.$refs.ruleForm.clearValidate()
      }
    }
  },
  // 实例创建
  created () {
    this.init()
    this.getComputerHistory()
  },
  methods: {
    /** 查看折扣优惠 */
    lookDiscountData () {
      this.lookDiscountDialog = true
      computer.getList().then((res) => {
        if (res.data.biddingAgencyRateList.length > 0) {
          this.discountTableData = res.data.biddingAgencyRateList
        }
        this.dataSource = ''
        if (res.data.calculatorInfo) {
          this.dataSource = res.data.calculatorInfo.dataSource
        }
      })
    },
    /** 类型常量转换为中文 */
    formatter (row, column) {
      return row.calculatorType === 1 ? '工程招标' : row.calculatorType === 2 ? '货物招标' : '服务招标'
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 查询项目 */
    searchProject (projectNameLike, cb) {
      if (projectNameLike) {
        this.calculatorType = ''
        this.calculatorPrice = ''
        this.projectList = []
        bidagent.getProjectList({
          messageLike: projectNameLike,
          enterpriseId: this.$store.getters.authUser.enterpriseId
        }).then((res) => {
          if (res.data.selectApplyList.list.length > 0) {
            res.data.selectApplyList.list.forEach((item, index) => {
              let projectResult = {}
              projectResult.value = item.projectName
              projectResult.calculatorType = item.projectPlan.projectType
              projectResult.calculatorPrice = item.planQuota
              this.projectList.push(projectResult)
            })
          }
        })
      }
      cb(this.projectList)
    },
    /** 选择项目 */
    handleSelect (iteam) {
      this.projectName = iteam.value
      this.ruleForm.calculatorType = String(iteam.calculatorType)
      this.ruleForm.calculatorPrice = Number(iteam.calculatorPrice)
    },
    /** 点击计算 */
    clickCompute (formName, parentsToChild) {
      if (parentsToChild === '1') {
        delete this.$refs[formName].rules.rate
      } else {
        this.$refs[formName].rules.rate = [{ required: true, message: '优惠折扣不能为空', trigger: 'blur' },
          { validator: this.validatRate }]
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.projectComputProcress = {}
          this.projectComputProcress.projectName = this.ruleForm.projectName
          this.projectComputProcress.calculatorType = this.ruleForm.calculatorType
          this.projectComputProcress.calculatorPrice = Number(this.ruleForm.calculatorPrice).toFixed(4)
          this.computerPrice(parentsToChild)
          this.saveDisabledFlag = false
        }
      })
    },
    /** 通过项目类型返回对应费率 */
    getRateByType (type, item) {
      let rate = ''
      switch (Number(type)) {
        case 1:
          rate = item.engineeringRate
          break
        case 2:
          rate = item.goodsRate
          break
        case 3:
          rate = item.serviceRate
          break
      }
      return rate
    },
    // 计算价格
    computerPrice (parentsToChild) {
      let percent = 0.01
      let percentTag = '%'
      let mulTag = '×'
      if (this.bidAgentRateList.length > 0) {
        let originalPrice = '' // 代理机构费用原价
        let originalPriceDetail = '' // 代理机构费用原价明细
        let disconutPrice = '' // 优惠价
        let discountPriceDetail = '' // 优惠价明细
        let comparedPrice = this.ruleForm.calculatorPrice
        this.bidAgentRateList.forEach((item, index) => {
          if (item.type === 1) { // 最小值
            if (Number(item.priceMax) >= Number(comparedPrice)) { // 在最小区间内
              originalPrice = comparedPrice * this.getRateByType(this.ruleForm.calculatorType, item) * percent
              disconutPrice = comparedPrice * this.getRateByType(this.ruleForm.calculatorType, item) * percent * (parentsToChild === '1' ? item.discount : this.ruleForm.rate) * percent
              originalPriceDetail = comparedPrice + mulTag + this.getRateByType(this.ruleForm.calculatorType, item) + percentTag
              discountPriceDetail = comparedPrice + mulTag + this.getRateByType(this.ruleForm.calculatorType, item) + percentTag + mulTag + (parentsToChild === '1' ? item.discount : this.ruleForm.rate) + percentTag
            }
          } else { // 中间行
            if ((Number(item.priceMin) < Number(comparedPrice) && Number(comparedPrice) <= Number(item.priceMax)) ||
              (Number(item.priceMin) < Number(comparedPrice) && item.type === 3)
            ) {
              originalPrice = (comparedPrice - item.priceMin) * this.getRateByType(this.ruleForm.calculatorType, item) * percent
              disconutPrice = (comparedPrice - item.priceMin) * this.getRateByType(this.ruleForm.calculatorType, item) * percent * (parentsToChild === '1' ? item.discount : this.ruleForm.rate) * percent
              originalPriceDetail = '(' + (comparedPrice + '-' + item.priceMin) + ')' + mulTag + this.getRateByType(this.ruleForm.calculatorType, item) + percentTag
              discountPriceDetail = '(' + (comparedPrice + '-' + item.priceMin) + ')' + mulTag + this.getRateByType(this.ruleForm.calculatorType, item) + percentTag + mulTag + (parentsToChild === '1' ? item.discount : this.ruleForm.rate) + percentTag
              let currentRow = item.rank
              while (currentRow > 1) {
                let computerIteam = this.bidAgentRateList[currentRow - 2]
                originalPrice += (computerIteam.priceMax - computerIteam.priceMin) * this.getRateByType(this.ruleForm.calculatorType, computerIteam) * percent
                disconutPrice += (computerIteam.priceMax - computerIteam.priceMin) * this.getRateByType(this.ruleForm.calculatorType, computerIteam) * percent * (parentsToChild === '1' ? computerIteam.discount : this.ruleForm.rate) * percent
                originalPriceDetail += '+(' + computerIteam.priceMax + '-' + computerIteam.priceMin + ')' + mulTag + this.getRateByType(this.ruleForm.calculatorType, computerIteam) + percentTag
                discountPriceDetail += '+(' + computerIteam.priceMax + '-' + computerIteam.priceMin + ')' + mulTag + this.getRateByType(this.ruleForm.calculatorType, computerIteam) + percentTag + mulTag + (parentsToChild === '1' ? computerIteam.discount : this.ruleForm.rate) + percentTag
                currentRow-- // 减一
              }
            }
          }
        })
        this.projectComputProcress.originalPriceDetail = originalPriceDetail + '=' + Number(originalPrice).toFixed(4)
        this.projectComputProcress.discountPriceDetail = discountPriceDetail + '=' + Number(disconutPrice).toFixed(4)
        this.projectComputProcress.originalPrice = Number(originalPrice).toFixed(4)
        this.projectComputProcress.discountPrice = Number(disconutPrice).toFixed(4)
      } else {
        Message({
          message: '管理员未设置代理机构费率规则，无法计算！',
          type: 'error',
          duration: 3 * 1000
        })
      }
    },
    /** 保存记录 */
    saveRecord (parentsToChild) {
      if (this.projectComputProcress.originalPriceDetail) { // 如果存在记录
        this.projectComputProcress.projectEnterpriseId = this.$store.getters.authUser.enterpriseId
        this.projectComputProcress.discountType = parentsToChild
        this.projectComputProcress.discount = this.ruleForm.rate
        this.projectComputProcress.objectId = ''
        this.projectComputProcress.type = 1
        bidagent.save(this.projectComputProcress).then((res) => {
          if (res.data.resCode === '0000') {
            this.getComputerHistory()
            this.saveDisabledFlag = true
            this.ruleForm = {}
            // 重置表单
            this.$refs['ruleForm'].resetFields()
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
      computer.getList().then((res) => {
        if (res.data.biddingAgencyRateList.length > 0) {
          this.bidAgentRateList = res.data.biddingAgencyRateList
        }
      })
    },
    /** 获取计算历史数据 */
    getComputerHistory () {
      let query = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        type: 1,
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
  #agent-calculator {
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
      height: 467px;
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
  }
</style>
