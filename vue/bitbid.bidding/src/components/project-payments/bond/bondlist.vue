<template>
  <div>
    <div id="bondlist" class="content_bigbox">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/projectpayments' }">项目收支管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/projectpayments/manage', query: {objectIds: this.$route.query.incExpenseIds} }">费用管理</el-breadcrumb-item>
        <el-breadcrumb-item>{{this.$route.query.name}}</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="da_xz_ckbigbox">
        <el-row>
          <el-col :span="12" v-if="$store.getters.permissions.includes('/projectpayments/charge/add')">
            <el-button icon="el-icon-plus" size='medium' type="primary" class="gongneng_btn" @click="addCharge">新增收费</el-button>
          </el-col>
          <div class="seacher_box">
            <el-input placeholder="缴费单位关键字" v-model="deptseainput"></el-input>
            <el-input placeholder="标段名称关键词" v-model="biaoduanseainput"></el-input>
            <el-button slot="append" icon="el-icon-search" type="" @click="search()"></el-button>
          </div>
        </el-row>
      </div>
      <el-table
        ref="multipleTable"
        tooltip-effect="dark"
        header-row-class-name='header'
        :data='dataTable'
        style="width: 100%"
        @selection-change="handleSelectionChange"
        @select-all="handleSelectionAll"
        border>
        <el-table-column
          type="selection"
          width="70"  align="center"
          :selectable="checkSelectable">
        </el-table-column>
        <el-table-column
          prop='paymentUnitName'
          label="缴费单位">
        </el-table-column>
        <el-table-column
          prop='paymentAmount'
          label="缴费金额"
          width="80">
        </el-table-column>
        <el-table-column
          prop='paymentTypes'
          label="缴费方式"
          width="80">
        </el-table-column>
        <el-table-column
          prop='paymentTime'
          label="缴费时间"
          width="160">
        </el-table-column>
        <el-table-column
          prop='SectionInformations'
          label="缴费标段"
          width="230">
          <template slot-scope="scope">
            <span class="jfbdbox" v-for="(item,index) in scope.row.sectionInformations" :key="index">
              <span>{{item.sectionName}}</span>
              <span style="margin-left: 10px">({{item.sectionNumber}})</span>
            </span>
          </template>
        </el-table-column>
        <el-table-column
          prop='surrenderTime'
          label="退还时间">
          <template slot-scope="scope">
            {{scope.row.surrenderTime===null?'-----------':scope.row.surrenderTime}}
          </template>
        </el-table-column>
        <el-table-column
          prop='paymentProjress'
          label="缴费进度"
          width="80">
          <template slot-scope="scope">
            {{scope.row.paymentProjress===0?'已缴费':'已退还'}}
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="center" width="180">
          <template slot-scope="scope">
            <el-button
              type="text"
              size="small" @click="editBtn(scope)" v-if="(scope.row.paymentProjress === 0 && $store.getters.permissions.includes('/projectpayments/charge/update'))">
              修改
            </el-button>
            <el-button
              type="text"
              size="small" @click="lookBtn(scope)" v-if="$store.getters.permissions.includes('/projectpayments/charge/detail')">
              查看
            </el-button>
            <el-button
              type="text"
              size="small" @click="returnOne(scope)" v-if="(scope.row.paymentProjress === 0 && $store.getters.permissions.includes('/projectpayments/charge/update'))">
              退还
            </el-button>
            <el-button
              type="text"
              size="small" @click="deleteObject(scope)" v-if="(scope.row.paymentProjress === 0 && $store.getters.permissions.includes('/projectpayments/charge/delete'))">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--批量退还-->
      <div class="piliangbox" v-if="$store.getters.permissions.includes('/projectpayments/charge/update')">
        <el-button type="primary" @click="batchReturn">批量退还</el-button>
      </div>
      <!--批量退还-->
      <el-dialog title="批量退还" :visible.sync="dialogFormVisible" width="30%">
        <div class="pltbfsbox">设置退保方式为：</div>
        <el-form :model="ruleForm" ref="ruleForm" :rules="rules"  >
          <el-form-item label="退还方式：" prop="surrenderType" :label-width="formLabelWidth">
            <el-select class="el-selectbox" v-model="ruleForm.surrenderType" placeholder="请选择" :disabled="disabledStatus">
              <el-option
                v-for="item in Paymodefrom"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="退还时间：" prop="surrenderTime" :label-width="formLabelWidth">
            <el-date-picker class="releaseTimebox"
              v-model="ruleForm.surrenderTime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              :picker-options="pickerOptions"
              placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="备注：" class="expertRemarkbox" :label-width="formLabelWidth" style="margin-bottom: 0">
            <el-input
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 6}"
              placeholder=""
              v-model="ruleForm.remark">
            </el-input>
            <span style="float: right;font-size: 12px;color: red">* 批量退还金额与缴纳金额相同</span>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="quxiaoanBtn">取 消</el-button>
          <el-button type="primary" @click="quedingBtn('ruleForm')">确 定</el-button>
        </div>
      </el-dialog>
      <!--分页-->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size='pageSize'
        :current-page.sync="currentPage"
        @current-change="handleCurrentChange"
        @next-click="handleCurrentNext">
      </el-pagination>
      <!--分页-->
    </div>
  </div>
</template>
<script>
import {addtitle} from '../../../assets/js/common'
export default{
  data () {
    return {
      ruleForm: {
        surrenderType: '',
        surrenderTime: '',
        remark: ''
      },
      dataTable: [],
      biaoduanseainput: '',
      deptseainput: '',
      // 日期选择
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7
        }
      },
      Paymodefrom: [{
        value: 3,
        label: '现金'
      }, {
        value: 4,
        label: '汇款'
      }, {
        value: 2,
        label: '银联'
      }, {
        value: 0,
        label: '支付宝'
      }, {
        value: 1,
        label: '微信'
      }, {
        value: 5,
        label: '支票'
      }, {
        value: 6,
        label: '保函'
      }, {
        value: 7,
        label: '其他'
      }],
      form: {
        Paymode: '',
        releaseTime: '',
        expertRemark: ''
      },
      disabledStatus: false,
      dialogFormVisible: false,
      searchIs: false,
      value: '',
      formLabelWidth: '120px',
      total: 0, // 总条数
      pageSize: 15, // 每页展示条数
      pageNo: 0,
      currentPage: 1,
      multipleSelection: [],
      rules: {
        surrenderType: [
          { required: true, message: '请选择退还方式', trigger: 'change' }
        ],
        surrenderTime: [
          { required: true, message: '请选择退还时间', trigger: 'change' }
        ]
      },
      Paytypefrom: [
        {
          value: 0,
          label: '招标文件'
        }, {
          value: 1,
          label: '资格预审文件'
        }
      ],
      Speed: [
        {
          value: 0,
          label: '已缴费'
        }, {
          value: 1,
          label: '已退还'
        }
      ]
    }
  },
  methods: {
    // 列表展示
    biddocumentList (search) {
      let url = null
      if (search) {
        url = `charge?pageNo=${this.pageNo}&pageSize=${this.pageSize}&incExpenseId=${this.$route.query.incExpenseIds}&expenseId=${this.$route.query.expenseId}&costType=${this.$route.query.costTypes}&isDelete=${0}&paymentUnitName=${encodeURIComponent(this.deptseainput)}&fuzzyQuery=${encodeURIComponent(this.biaoduanseainput)}&_t=${new Date().getTime()}`
      } else {
        url = `charge?pageNo=${this.pageNo}&pageSize=${this.pageSize}&incExpenseId=${this.$route.query.incExpenseIds}&expenseId=${this.$route.query.expenseId}&costType=${this.$route.query.costTypes}&isDelete=${0}&_t=${new Date().getTime()}`
      }
      this.axios.get(url).then((res) => {
        this.dataTable = res.data.chargeManagementPageInfo.list
        this.total = res.data.chargeManagementPageInfo.total
        this.dataTable.map((ite) => {
          ite.paymentTypes = ''
          let paymentTypes = ite.paymentType
          this.Paymodefrom.map((itm) => {
            if (paymentTypes === itm.value) {
              ite.paymentTypes = itm.label
            }
          })
        })
        addtitle(this)
      })
    },
    // 搜索
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.axios.get(`charge?pageNo=${this.pageNo}&pageSize=${this.pageSize}&incExpenseId=${this.$route.query.incExpenseIds}&expenseId=${this.$route.query.expenseId}&costType=${this.$route.query.costTypes}&isDelete=${0}&paymentUnitName=${encodeURIComponent(this.deptseainput)}&fuzzyQuery=${encodeURIComponent(this.biaoduanseainput)}&_t=${new Date().getTime()}`).then((res) => {
        this.dataTable = res.data.chargeManagementPageInfo.list
        this.total = res.data.chargeManagementPageInfo.total
        this.dataTable.map((ite) => {
          ite.paymentTypes = ''
          let paymentTypes = ite.paymentType
          this.Paymodefrom.map((itm) => {
            if (paymentTypes === itm.value) {
              ite.paymentTypes = itm.label
            }
          })
        })
        addtitle(this)
        this.currentPage = 1
        this.pageNo = (this.currentPage - 1) * this.pageSize
        this.searchIs = true
      })
    },
    // 表单分页
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.biddocumentList(this.searchIs)
    },
    handleCurrentNext (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.biddocumentList(this.searchIs)
    },
    addCharge () {
      this.$router.push({path: '/projectpayments/manage/bondlist/add', query: {name: this.$route.query.name, incExpenseIds: this.$route.query.incExpenseIds, costTypes: this.$route.query.costTypes, expenseId: this.$route.query.expenseId, expensesType: this.$route.query.expensesType}})
    },
    editBtn (scope) {
      this.$router.push({path: '/projectpayments/manage/bondlist/update', query: {name: this.$route.query.name, incExpenseIds: this.$route.query.incExpenseIds, costTypes: this.$route.query.costTypes, objectIds: scope.row.objectId, expenseId: this.$route.query.expenseId, expensesType: this.$route.query.expensesType}})
    },
    lookBtn (scope) {
      this.$router.push({path: `/projectpayments/manage/bondlist/detail/${scope.row.objectId}`, query: {name: this.$route.query.name, incExpenseIds: this.$route.query.incExpenseIds, costTypes: this.$route.query.costTypes, objectIds: scope.row.objectId, expenseId: this.$route.query.expenseId, expensesType: this.$route.query.expensesType, paymentProjress: scope.row.paymentProjress}})
    },
    returnOne (scope) {
      this.$router.push({path: '/projectpayments/manage/bondlist/return', query: {name: this.$route.query.name, incExpenseIds: this.$route.query.incExpenseIds, costTypes: this.$route.query.costTypes, objectIds: scope.row.objectId, expenseId: this.$route.query.expenseId, expensesType: this.$route.query.expensesType}})
    },
    // 删除
    deleteObject (scope) {
      this.$confirm('确定要删除吗?删除后不可恢复，是否继续；确认后删除记录?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        this.axios.delete(`charge/${scope.row.objectId}`).then(() => {
          this.biddocumentList()
        })
      }).catch(() => {
        return false
      })
    },
    // 批量退还
    batchReturn () {
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请先选择一项进行操作'
        })
        return false
      } else {
        this.dialogFormVisible = true
        this.titles = ''
        this.disabledStatus = false
        this.ruleForm.surrenderType = ''
      }
    },
    quxiaoanBtn () {
      this.dialogFormVisible = false
      this.ruleForm = {}
    },
    // 退还
    quedingBtn (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.ruleForm.chargeManagements = this.multipleSelection
          this.axios.put('charge/massSurrender', this.ruleForm).then(() => {
            this.biddocumentList()
          })
          this.dialogFormVisible = false
        } else {
          return false
        }
      })
    },
    handleSelectionChange (selection) {
      if (selection.length === 0) {
        this.multipleSelection = []
      } else {
        this.multipleSelection = selection
      }
    },
    handleSelectionAll (selection) {
      if (selection.length === 0) {
        this.multipleSelection = []
      } else {
        this.multipleSelection = selection
      }
    },
    checkSelectable (row, index) {
      return row.paymentProjress !== 1
    }
  },
  mounted () {
    this.biddocumentList()
  }
}
</script>
<style lang='less'>
  body{
    background:#eaedf1;
  }
#bondlist{
    .breadcrumb_box{
        width: 100%;
        height: 60px;
        border-bottom: 1px solid #eeeeee;
        padding: 0 20px;
        box-sizing: border-box;
        line-height: 60px;
    }
  .pltbfsbox{
    padding-bottom: 15px;
    padding-top: 25px;
    border-top:1px solid #CCCCCC;
    margin-bottom: 10px;
  }
  .piliangbox{
    width: 100%;
    margin-top: 20px;
    padding: 0 15px;
    box-sizing: border-box;
  }
  .da_xz_ckbigbox {
    padding: 10px 20px;
  }
    .el-breadcrumb__inner{
        color: #999999!important;
    }
    .active_bread>.el-breadcrumb__inner{
        color:#333333!important;
    }
    .el-button--primary {
      color: #fff;
      background-color: #6582f8;
      border-color: #6582f8;
    }
    .header{background: #f5f7f8}
    .da_xz_ckbigbox{
        border: none;
        line-height:60px;
        padding:0 20px;
        box-sizing: border-box;
        .el-form-item{padding: 0px}
        .el-dialog__header{padding-top: 0;padding-bottom: 0px}
        .el-dialog__body{padding-bottom: 0px}
    }
    .content_bigbox{margin: 0px auto;width: 1200px;background: #fff}
  .seacher_box {
    height: 100%;
    min-width: 50px;
    float: right;
  }
  .el-button{
    border-radius: 0;
  }
  .el-dialog__body {
    padding: 10px 20px;
    color: #606266;
    line-height: 24px;
    font-size: 14px;
  }
  .el-selectbox{
    width: 100%;
  }
  .releaseTimebox{
    width: 100%!important;
  }
  .el-selectbox .el-input--suffix{
    width: 100%;
  }
  .el-input {
    position: relative;
    font-size: 12px;
    display: inline-block;
    width: 40%;
  }
  .el-table .cell {
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    word-break: break-all;
    height: auto;
    line-height: 23px;
    overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
  }
  .jfbdbox{
    display: block;
    margin-bottom:5px;
  }
  .jfbdbox>span{
    width: 45%;
    display: inline-block;
    vertical-align: top;
    overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
  }
  .el-form-item__label {
    width: 120px!important;
  }
  .el-form-item__content {
    margin-left: 120px;
  }
}
</style>
