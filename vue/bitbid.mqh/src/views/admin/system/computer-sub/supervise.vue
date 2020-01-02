<template>
  <div class="supervise-computer" id="supervise-computer">
    <el-tabs type="card" v-model="activeName" @tab-click="handleTabClick">
      <el-tab-pane label="厂区施工监理费用" name="factoryArea">
        <el-form :model="tableFormFactory" :rules="rules" ref="tableFormFactory">
          <el-table
            class="bid-table Factory"
            :data="tableFormFactory.bidAgentTableData"
            border
            style="width: 100%">
            <el-table-column
              prop="chargeAmount"
              label="计费额（万元）"
              align="center"
              width="260">
              <template slot-scope="scope">
                <el-form-item :prop="'bidAgentTableData.' + scope.$index + '.chargeAmount'" :rules='rules.chargeAmount'>
                  <el-input
                    placeholder="请输入内容"
                    v-model="scope.row.chargeAmount"
                    v-if="scope.row.editFlag">
                  </el-input>
                  <span style="display:block;" v-else>{{scope.row.chargeAmount}}</span>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column
              prop="chargeBasePrice"
              label="国家收费基价（万元）"
              align="center">
              <template slot-scope="scope">
                <el-form-item :prop="'bidAgentTableData.' + scope.$index + '.chargeBasePrice'" :rules='rules.chargeBasePrice'>
                  <el-input placeholder="请输入内容" v-model="scope.row.chargeBasePrice" v-if="scope.row.editFlag">
                  </el-input>
                  <span v-else>{{scope.row.chargeBasePrice}}</span>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column
              prop="discount"
              align="center"
              label="优惠折扣">
              <template slot-scope="scope">
                <el-form-item :prop="'bidAgentTableData.' + scope.$index + '.discount'" :rules='rules.discount'>
                  <el-input placeholder="请输入内容" v-model="scope.row.discount" v-if="scope.row.editFlag">
                    <template slot="append">%</template>
                  </el-input>
                  <span v-else>{{scope.row.discount}}%</span>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              align="center">
              <template slot-scope="scope">
                <el-button type="text" @click="handleTableBtn(scope.row, 'edit', scope.$index)">编辑</el-button>
                <el-button type="text" @click="handleTableBtn(scope.row, 'del', scope.$index)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="矿区施工监理费用" name="miningArea">
        <el-form :model="tableFormMining" :rules="rulesMining" ref="tableFormMining">
          <el-table
            class="bid-table Mining"
            :data="tableFormMining.bidAgentMiningTableData"
            border
            style="width: 100%">
            <el-table-column
              prop="chargeAmount"
              label="计费额（万元）"
              align="center"
              width="260">
              <template slot-scope="scope">
                <el-form-item :prop="'bidAgentMiningTableData.' + scope.$index + '.chargeAmount'" :rules='rulesMining.chargeAmount'>
                  <el-input
                    placeholder="请输入内容"
                    v-model="scope.row.chargeAmount"
                    v-if="scope.row.editFlag">
                  </el-input>
                  <span style="display:block;" v-else>{{scope.row.chargeAmount}}</span>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column
              prop="chargeBasePrice"
              label="国家收费基价（万元）"
              align="center">
              <template slot-scope="scope">
                <el-form-item :prop="'bidAgentMiningTableData.' + scope.$index + '.chargeBasePrice'" :rules='rulesMining.chargeBasePrice'>
                  <el-input placeholder="请输入内容" v-model="scope.row.chargeBasePrice" v-if="scope.row.editFlag">
                  </el-input>
                  <span v-else>{{scope.row.chargeBasePrice}}</span>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column
              prop="discount"
              align="center"
              label="优惠折扣">
              <template slot-scope="scope">
                <el-form-item :prop="'bidAgentMiningTableData.' + scope.$index + '.discount'" :rules='rulesMining.discount'>
                  <el-input placeholder="请输入内容" v-model="scope.row.discount" v-if="scope.row.editFlag">
                    <template slot="append">%</template>
                  </el-input>
                  <span v-else>{{scope.row.discount}}%</span>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              align="center">
              <template slot-scope="scope">
                <el-button type="text" @click="handleTableBtn(scope.row, 'edit', scope.$index)">编辑</el-button>
                <el-button type="text" @click="handleTableBtn(scope.row, 'del', scope.$index)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form>
      </el-tab-pane>
    </el-tabs>
    <div class="add-row-btn">
      <el-button icon="el-icon-plus" class="add-button" @click="addTableRow"></el-button>
    </div>
    <div style="margin-top: 10px;">
      <el-input
        type="textarea"
        :rows="2"
        placeholder="请注明数据来源"
        v-model="dataSource">
      </el-input>
    </div>
    <div class="btns-box">
      <el-button type="primary" @click="submitForm">保 存</el-button>
    </div>
  </div>
</template>
<script>
import Sortable from 'sortablejs'
import { supervisionRate, calculatorInfo } from 'api/index'
export default {
  name: 'bid-agent',
  data () {
    let validatePrice = (rule, value, callback) => {
      if (value && (!(/^\d{1,14}(\.?\d{0,2})?$/).test(value))) {
        callback(new Error('请输入正确的价格信息'))
      } else {
        callback()
      }
    }
    let validateDiscount = (rule, value, callback) => {
      if (value && (!(/^100$|^(\d|[1-9]\d)(\.\d+)*$/).test(value))) {
        callback(new Error('请输入正确优惠折扣'))
      } else {
        callback()
      }
    }
    return {
      activeName: 'factoryArea',
      constantMaintenance: {}, // 优惠比例
      isEditFlag: false,
      tableFormFactory: { bidAgentTableData: [] },
      rules: {
        chargeAmount: [
          { required: true, message: '请输入计费额', trigger: 'blur' },
          {validator: validatePrice}
        ],
        chargeBasePrice: [
          { required: true, message: '请输入国家收费基价称', trigger: 'blur' },
          {validator: validatePrice}
        ],
        discount: [
          { required: true, message: '请输入优惠折扣', trigger: 'blur' },
          {validator: validateDiscount}
        ]
      },
      tableFormMining: {
        bidAgentMiningTableData: []
      },
      rulesMining: {
        chargeAmount: [
          { required: true, message: '请输入计费额', trigger: 'blur' },
          {validator: validatePrice}
        ],
        chargeBasePrice: [
          { required: true, message: '请输入国家收费基价称', trigger: 'blur' },
          {validator: validatePrice}
        ],
        discount: [
          { required: true, message: '请输入优惠折扣', trigger: 'blur' },
          {validator: validateDiscount}
        ]
      },
      dataSource: ''
    }
  },
  created () {
    this.init(0)
  },
  mounted () {
    this.rowFactoryDrop(this.tableFormFactory.bidAgentTableData)
    this.rowMiningDrop(this.tableFormFactory.bidAgentMiningTableData)
  },
  methods: {
    /** 初始化加载数据 */
    init (type) {
      supervisionRate.getList({
        type: type
      }).then((res) => {
        if (res.data.constructionSupervisionRateList.length > 0) {
          if (type === 0) {
            this.tableFormFactory.bidAgentTableData = res.data.constructionSupervisionRateList
            this.rowFactoryDrop(this.tableFormFactory.bidAgentTableData)
          } else if (type === 1) {
            this.tableFormMining.bidAgentMiningTableData = res.data.constructionSupervisionRateList
            this.rowMiningDrop(this.tableFormMining.bidAgentMiningTableData)
          }
        }
        this.dataSource = ''
        if (res.data.calculatorInfo) {
          this.dataSource = res.data.calculatorInfo.dataSource
        }
      })
    },
    /** 表格操作 */
    handleTableBtn (row, type, index) {
      if (type === 'edit' && this.activeName === 'factoryArea') {
        row.editFlag = true
        this.tableFormFactory.bidAgentTableData.splice(index, 1, row)
        // this.isEditFlag = true
      } else if (type === 'edit' && this.activeName === 'miningArea') {
        row.editFlag = true
        this.tableFormMining.bidAgentMiningTableData.splice(index, 1, row)
        // this.isEditFlag = true
      } else if (type === 'del' && this.activeName === 'factoryArea') {
        this.deleteClick(index, this.tableFormFactory.bidAgentTableData)
      } else if (type === 'del' && this.activeName === 'miningArea') {
        this.deleteClick(index, this.tableFormMining.bidAgentMiningTableData)
      }
    },
    // 行拖拽
    rowFactoryDrop (data) {
      let that = this
      let tbody = document.querySelector('.Factory .el-table__body-wrapper tbody')
      if (data && data.length) {
        Sortable.create(tbody, {
          onEnd ({newIndex, oldIndex}) {
            let currRow = data.splice(oldIndex, 1)[0]
            data.splice(newIndex, 0, currRow)
            that.$set(data[oldIndex], 'flagBlock', '99')
          }
        })
      }
    },
    // 行拖拽
    rowMiningDrop (data) {
      let tbody = document.querySelector('.Mining .el-table__body-wrapper tbody')
      let _this = this
      if (data && data.length) {
        Sortable.create(tbody, {
          onEnd ({newIndex, oldIndex}) {
            let currRow = _this.tableFormMining.bidAgentMiningTableData.splice(oldIndex, 1)[0]
            _this.tableFormMining.bidAgentMiningTableData.splice(newIndex, 0, currRow)
          }
        })
      }
    },
    /** 厂区和矿区切换 */
    handleTabClick (tab, event) {
      this.activeName = tab.name
      this.init(this.activeName === 'factoryArea' ? 0 : 1)
    },
    /** 新增表格行 */
    addTableRow () {
      if (this.activeName === 'factoryArea') {
        let obj = {
          chargeAmount: '',
          chargeBasePrice: '',
          discount: '',
          type: 0,
          editFlag: true
        }
        this.tableFormFactory.bidAgentTableData.push(obj)
      } else if (this.activeName === 'miningArea') {
        let obj = {
          chargeAmount: '',
          chargeBasePrice: '',
          discount: '',
          type: 1,
          editFlag: true
        }
        this.tableFormMining.bidAgentMiningTableData.push(obj)
      }
      // this.isEditFlag = true
    },
    /** 保存 */
    submitForm () {
      if (this.activeName === 'factoryArea') {
        let isRepeat = this.isRepeat(this.tableFormFactory.bidAgentTableData)
        if (isRepeat) {
          this.$message.error('计费额' + isRepeat + '已存在!')
          return false
        } else {
          let result = this.vlidateForm('tableFormFactory')
          if (result) {
            if (this.tableFormFactory.bidAgentTableData.length > 0) {
              this.tableFormFactory.bidAgentTableData.forEach(item => {
                item.OBJECT_ID = null
                return item
              })
            }
            let obj = {
              type: 3,
              dataSource: this.dataSource,
              constructionSupervisionRates: this.tableFormFactory.bidAgentTableData
            }
            calculatorInfo.save(obj).then((res) => {
              if (res.data.resCode === '0000') {
                this.init(0)
                this.$nextTick(() => {
                  this.rowFactoryDrop(this.tableFormFactory.bidAgentTableData)
                })
              }
            })
          }
        }
      } else if (this.activeName === 'miningArea') {
        let isRepeat = this.isRepeat(this.tableFormMining.bidAgentMiningTableData)
        if (isRepeat) {
          this.$message.error('计费额' + isRepeat + '已存在!')
          return false
        } else {
          let result = this.vlidateForm('tableFormMining')
          if (result) {
            if (this.tableFormMining.bidAgentMiningTableData.length > 0) {
              this.tableFormMining.bidAgentMiningTableData.forEach(item => {
                item.OBJECT_ID = null
                return item
              })
            }
            let obj = {
              type: 4,
              dataSource: this.dataSource,
              constructionSupervisionRates: this.tableFormMining.bidAgentMiningTableData
            }
            calculatorInfo.save(obj).then((res) => {
              if (res.data.resCode === '0000') {
                this.init(1)
              }
            })
          }
        }
      }
    },
    /** 判断是否存在相同的计费额 */
    isRepeat (arr) {
      for (let i in arr) {
        for (let j in arr) {
          if (i !== j && arr[i].chargeAmount === arr[j].chargeAmount) {
            return arr[i].chargeAmount
          }
        }
      }
      return ''
    },
    /** 校验 */
    vlidateForm (formName) {
      let result = false
      this.$refs[formName].validate((valid) => {
        if (valid) {
          result = true
        }
      })
      return result
    },
    // 删除方法
    deleteClick (index, rows) {
      this.$confirm('此操作永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        rows.splice(index, 1)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    }
  }
}
</script>
<style lang="less">
#supervise-computer {
  .add-row-btn {
    .add-button {
      width: 100%;
    }
  }
  .bid-table {
    .el-form-item {
      margin-bottom: 0;
    }
  }
  .btns-box {
    margin: 20px;
  }
}
</style>
