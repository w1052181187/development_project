<template>
  <div class="cost-const" id="cost-const">
    <el-form :model="tableForm" :rules="rules" ref="tableForm">
      <!--<el-form-item label="煤气化模式优惠比例：" prop="ratio">-->
        <!--<el-input placeholder="请输入内容" v-model="constantMaintenance.description" style="float: left;width: 15%;">-->
          <!--<template slot="append">%</template>-->
        <!--</el-input>-->
        <!--<el-button type="primary" @click="saveRatio" style="float: left; margin-left: 20px;">保 存</el-button>-->
      <!--</el-form-item>-->
      <el-table
        class="bid-table"
        :data="tableForm.costConstData"
        border
        style="width: 100%">
        <el-table-column
          prop="price"
          label="建安工程费（万元）"
          align="center"
          width="260">
          <template slot-scope="scope">
            <el-row :gutter="20" v-if="scope.row.rowType === 'middle' ">
              <el-col :span="11">
                <el-form-item :prop="'costConstData.' + scope.$index + '.priceMin'" :rules='rules.price'>
                  <el-input
                    placeholder="请输入内容"
                    v-model="scope.row.priceMin"
                    v-if="scope.row.editFlag">
                  </el-input>
                  <span v-else style="display:block;float:right;">{{scope.row.priceMin}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="1" style="line-height: 35px;">-</el-col>
              <el-col :span="11">
                <el-form-item :prop="'costConstData.' + scope.$index + '.priceMax'" :rules='rules.price'>
                  <el-input
                    placeholder="请输入内容"
                    v-model="scope.row.priceMax"
                    v-if="scope.row.editFlag">
                  </el-input>
                  <span v-else style="display:block;float:left;">{{scope.row.priceMax}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row v-else-if="scope.row.rowType === 'firstRow' ">
              <el-form-item :prop="'costConstData.' + scope.$index + '.priceMax'" :rules='rules.price'>
                <span style="display:inline-block;">小于</span>
                <el-input
                  style="float:right;width:80%;"
                  placeholder="请输入内容"
                  v-model="scope.row.priceMax"
                  v-if="scope.row.editFlag ">
                </el-input>
                <span v-else>{{scope.row.priceMax}}</span>
              </el-form-item>
            </el-row>
            <el-row v-else-if="scope.row.rowType === 'lastRow' ">
              <el-form-item :prop="'costConstData.' + scope.$index + '.priceMin'" :rules='rules.price'>
                <span style="display:inline-block;">大于</span>
                <el-input
                  style="float:right;width:80%;"
                  placeholder="请输入内容"
                  v-model="scope.row.priceMin"
                  v-if="scope.row.editFlag ">
                </el-input>
                <span v-else>{{scope.row.priceMin}}</span>
              </el-form-item>
            </el-row>
          </template>
        </el-table-column>
        <el-table-column
          prop="rate"
          label="工程概算编制"
          align="center">
          <template slot-scope="scope">
            <el-form-item :prop="'costConstData.' + scope.$index + '.projectBudgetRate'" :rules='rules.rate'>
              <el-input placeholder="请输入内容" v-model="scope.row.projectBudgetRate" v-if="scope.row.editFlag">
                <template slot="append">‰</template>
              </el-input>
              <span v-else>{{scope.row.projectBudgetRate}}‰</span>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column
          prop="rate"
          label="工程量清单编制"
          align="center">
          <template slot-scope="scope">
            <el-form-item :prop="'costConstData.' + scope.$index + '.billOfQuantitiesRate'" :rules='rules.rate'>
              <el-input placeholder="请输入内容" v-model="scope.row.billOfQuantitiesRate" v-if="scope.row.editFlag">
                <template slot="append">‰</template>
              </el-input>
              <span v-else>{{scope.row.billOfQuantitiesRate}}‰</span>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column
          prop="rate"
          label="招标控制价编制"
          align="center">
          <template slot-scope="scope">
            <el-form-item :prop="'costConstData.' + scope.$index + '.tenderControlPriceRate'" :rules='rules.rate'>
              <el-input placeholder="请输入内容" v-model="scope.row.tenderControlPriceRate" v-if="scope.row.editFlag">
                <template slot="append">‰</template>
              </el-input>
              <span v-else>{{scope.row.tenderControlPriceRate}}‰</span>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column
          prop="rate"
          label="工程预算编制"
          align="center">
          <template slot-scope="scope">
            <el-form-item :prop="'costConstData.' + scope.$index + '.constructionBudgetRate'" :rules='rules.rate'>
              <el-input placeholder="请输入内容" v-model="scope.row.constructionBudgetRate" v-if="scope.row.editFlag">
                <template slot="append">‰</template>
              </el-input>
              <span v-else>{{scope.row.constructionBudgetRate}}‰</span>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column
          prop="rate"
          label="工程结算审核-基本收费"
          align="center">
          <template slot-scope="scope">
            <el-form-item :prop="'costConstData.' + scope.$index + '.engineeringSettlementRate'" :rules='rules.rate'>
              <el-input placeholder="请输入内容" v-model="scope.row.engineeringSettlementRate" v-if="scope.row.editFlag">
                <template slot="append">‰</template>
              </el-input>
              <span v-else>{{scope.row.engineeringSettlementRate}}‰</span>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column
          prop="rate"
          label="竣工决算编制"
          align="center">
          <template slot-scope="scope">
            <el-form-item :prop="'costConstData.' + scope.$index + '.finalAccountsRate'" :rules='rules.rate'>
              <el-input placeholder="请输入内容" v-model="scope.row.finalAccountsRate" v-if="scope.row.editFlag">
                <template slot="append">‰</template>
              </el-input>
              <span v-else>{{scope.row.finalAccountsRate}}‰</span>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column
          prop="discout"
          label="优惠折扣"
          align="center">
          <template slot-scope="scope">
            <el-form-item :prop="'costConstData.' + scope.$index + '.discout'" :rules='rules.discout'>
              <el-input placeholder="请输入内容" v-model="scope.row.discout" v-if="scope.row.editFlag">
                <template slot="append">%</template>
              </el-input>
              <span v-else>{{scope.row.discout}}%</span>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="center">
          <template slot-scope="scope">
            <el-button type="text" @click="handleTableBtn(scope.row, 'edit', scope.$index)" v-if="scope.row.editFlag === false">编辑</el-button>
            <el-button type="text" @click="handleTableBtn(scope.row, 'del', scope.$index)" v-if="scope.row.rowType === 'middle'">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-form>
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
    <!-- <div class="note-box">
      <p>
        注：单项咨询项目收费标准计算收费额不足2000元的，按2000元收费
      </p>
    </div> -->
    <div class="btns-box">
      <el-button type="primary" @click="submitForm('tableForm')">保 存</el-button>
    </div>
  </div>
</template>
<script>
import { costConst, calculatorInfo } from 'api/index'
export default {
  name: 'cost-const',
  data () {
    var validateRate = (rule, value, callback) => {
      if (value && (!(/^100$|^(\d|[1-9]\d)(\.\d+)*$/).test(value))) {
        callback(new Error('请输入正确费率'))
      } else {
        callback()
      }
    }
    var validatePrice = (rule, value, callback) => {
      if (value && (!(/^\d{1,14}(\.?\d{0,2})?$/).test(value))) {
        callback(new Error('请输入正确价格'))
      } else {
        callback()
      }
    }
    var validatediscout = (rule, value, callback) => {
      if (value && (!(/^100$|^(\d|[1-9]\d)(\.\d+)*$/).test(value))) {
        callback(new Error('请输入正确优惠折扣'))
      } else {
        callback()
      }
    }
    return {
      dataSource: '', // 数据来源
      constantMaintenance: {}, // 优惠比例
      isEditFlag: false,
      tableForm: { costConstData: [{priceMax: 100, rowType: 'firstRow', editFlag: true}, {priceMin: 100000, rowType: 'lastRow', editFlag: true}] },
      rules: {
        rate: [
          {required: true, message: '请输入费率', trigger: 'blur'},
          {validator: validateRate}
        ],
        discout: [
          {required: true, message: '请输入优惠折扣', trigger: 'blur'},
          {validator: validatediscout}
        ],
        price: [
          {required: true, message: '请输入中标金额', trigger: 'blur'},
          {validator: validatePrice}
        ]
      }
    }
  },
  created () {
    this.init()
  },
  methods: {
    /** 比例保存 */
    saveRatio () {},
    /** 表格操作 */
    handleTableBtn (row, type, index) {
      if (type === 'edit') {
        row.editFlag = true
        this.tableForm.costConstData.splice(index, 1, row)
        this.isEditFlag = true
      } else if (type === 'del') {
        this.deleteClick(index, row)
      }
    },
    /** 初始化加载数据 */
    init () {
      costConst.getList().then((res) => {
        this.dataSource = ''
        if (res.data.calculatorInfo) {
          this.dataSource = res.data.calculatorInfo.dataSource
        }
        if (res.data.costConsultationRateList.length > 0) {
          this.tableForm.costConstData = res.data.costConsultationRateList
          this.tableForm.costConstData.forEach((item, index) => {
            if (item.type === 1) { // 第一行
              item.rowType = 'firstRow'
            } else if (item.type === 3) { // 最后一行
              item.rowType = 'lastRow'
            } else { // 中间行
              item.rowType = 'middle'
            }
            item.editFlag = false
          })
        } else {
          this.isEditFlag = true
        }
      })
    },
    /** 新增表格行 */
    addTableRow () {
      let obj = {
        money: 100,
        editFlag: true,
        rowType: 'middle'
      }
      this.tableForm.costConstData.splice(-1, 0, obj)
      this.isEditFlag = true
    },
    /** 弹窗-确定 */
    confirmDialogBtn () {},
    /** 保存 */
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = []
          let validFlag = true
          this.tableForm.costConstData.forEach((item, index) => {
            item.rank = index + 1
            if (index === 0) { // 第一行
              item.type = 1
              item.priceMin = 0
            } else if (index === this.tableForm.costConstData.length - 1) { // 最后一行
              item.type = 3
            } else { // 中间行
              item.type = 2
            }
            if (validFlag) {
              if (index > 0) {
                if (Number(item.priceMin) !== Number(data[data.length - 1].priceMax)) {
                  validFlag = false
                  return false
                } else {
                  data.push(item)
                }
              } else {
                data.push(item)
              }
            }
          })
          if (validFlag) {
            let obj = {
              type: 2,
              dataSource: this.dataSource,
              costConsultationRateList: data
            }
            calculatorInfo.save(obj).then((res) => {
              if (res.data.resCode === '0000') {
                this.init()
              }
            })
          } else {
            this.$message({
              type: 'error',
              message: '请检查中标金额范围是否设置正确！'
            })
          }
        } else {
          return false
        }
      })
    },
    // 删除方法
    deleteClick (index, row) {
      this.$confirm('此操作永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 执行删除方法
        if (row.objectId) {
          costConst.del(row.objectId).then((res) => {
            if (res.data.resCode === '0000') {
              this.init()
              this.isEditFlag = true
            } else {
              this.$message({
                type: 'error',
                message: '操作失败！'
              })
            }
          })
        } else {
          this.tableForm.costConstData.splice(index, 1)
          this.isEditFlag = true
        }
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
#cost-const{
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
