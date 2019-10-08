<template>
  <div class="cloudcontent" id="otherIncomeAdd">
    <div class="main">
      <el-form :model="addForm" :rules="rules" ref="addForm" :validate-on-rule-change="true">
        <el-row :span="12">
          <el-col>
            <el-form-item prop="bidderName" label="收入项目：">
              <!--<el-input v-model="addForm.bidderName"></el-input>-->
              <span>{{addForm.bidderName}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="金额（元）："  prop="amount">
              <el-input v-model="addForm.amount"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="收费方式："  prop="paymentMethod">
              <el-select v-model="addForm.paymentMethod" class="select">
                <el-option
                  v-for="item in chargeModeOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="收费时间：" prop="paymentTime">
              <el-date-picker
                v-model="addForm.paymentTime"
                type="date"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="相关标段：" prop="bidSectionList">
              <el-button type="primary" @click="choiceBtn">选择</el-button>
              <el-table
                :data="addForm.bidSectionList"
                border
                style="width: 100%; margin-top: 10px;" header-cell-class-name="tableheader" >
                <el-table-column
                  type="index"
                  label="序号"
                  width="60"
                  :index="indexMethod"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="bidSectionCode"
                  label="标段编号"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="bidSectionName"
                  label="标段名称"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  label="操作" align="center" width="200">
                  <template slot-scope="scope">
                    <el-button type="text" size="small" @click="delBtn(scope.$index, addForm.bidSectionList)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注：">
          <el-input type="textarea" v-model="addForm.remarks"></el-input>
        </el-form-item>
        <el-form-item class="submit-radio">
          <el-button type="primary" v-if="Number(addForm.status) !== 1" @click="handleClickEvent('addForm', 0)">保存</el-button>
          <el-button type="primary" class="submitBtn" @click="handleClickEvent('addForm', 1)">提交</el-button>
          <el-button class="cancal" @click="cancelSave">取消</el-button>
        </el-form-item>
      </el-form>
      <el-dialog title="选择缴费标段" :visible.sync="dialogTableVisible">
        <div class="topmain">
          <div class="seacher_box">
            <el-input class="search" placeholder="请输入标段名称" style="vertical-align: top"  v-model="bidSectionNameLike">
              <el-button  slot="append" icon="el-icon-search" type="" @click="search"></el-button>
            </el-input>
          </div>
        </div>
        <el-table
          :data="dialogTableData"
          border
          style="width: 100%" header-cell-class-name="tableheader" @selection-change="getNewTenderTableData">
          <el-table-column
            type="selection"
            width="50">
          </el-table-column>
          <el-table-column
            prop="bidSectionCode"
            label="标段编号"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="bidSectionName"
            label="标段名称"
            show-overflow-tooltip>
          </el-table-column>
        </el-table>
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
        <div  style="text-align: center">
          <el-button type="primary" @click="dilogConfirm()">确认</el-button>
          <el-button class="cancal" @click="dilogCancel()">取消</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import {validateMoney} from '@/assets/js/validate'
import {bidSection, settlementItem} from '@/api/project'
const validMoney = (rule, value, callback) => {
  if (!validateMoney(value)) {
    callback(new Error('请输入正确的金额'))
  } else {
    callback()
  }
}
const validArray = (rule, value, callback) => {
  if (!value || value.length <= 0) {
    callback(new Error('请选择标段'))
  } else {
    callback()
  }
}
export default {
  components: {
  },
  data () {
    return {
      addForm: {
        otherIncometext: '',
        amount: '',
        chargeModeType: null,
        bidSectionList: [], // 页面上展示出来的可选择的标段信息
        desc: ''
      },
      tenderProjectName: '',
      newTenderTableData: [],
      bidSectionNameLike: '',
      dialogTableVisible: false,
      paymentTenders: [],
      bidSectionNameNumber: '',
      bidSectionNameNumberOptions: [{
        value: 1,
        label: '标段名称'
      }, {
        value: 2,
        label: '标段编号'
      }],
      // 收费方式
      chargeModeOptions: [
        {value: null, label: '--请选择--'},
        {value: 1, label: '支付宝'},
        {value: 2, label: '微信'},
        {value: 3, label: '现金'},
        {value: 4, label: '银联'},
        {value: 5, label: '支票'},
        {value: 6, label: '保函'},
        {value: 9, label: '其他'}],
      dialogTableData: [],
      rules: {
        bidderName: [
          { required: true, message: '项目名称不能为空', trigger: ['blur', 'change'] }
        ],
        amount: [
          { required: true, message: '金额不能为空', trigger: ['blur', 'change'] },
          { required: true, trigger: 'change', validator: validMoney }
        ],
        paymentMethod: [
          { required: true, message: '收费方式不能为空', trigger: ['change'] }
        ],
        paymentTime: [
          { required: true, message: '收费时间不能为空', trigger: ['change'] }
        ],
        bidSectionList: [
          { required: true, trigger: ['blur', 'change'], validator: validArray }
        ]
      },
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 0, // 总条数
      pageSize: 10, // 每页展示条数
      dataIndex: 1
    }
  },
  methods: {
    cancelSave () {
      this.$router.go(-1)
    },
    getNewTenderTableData (data) {
      this.newTenderTableData = data
    },
    handleClickEvent (formName, payStatus) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let message = ''
          if (payStatus === 0) {
            message = '确认要保存吗?'
          } else {
            message = '提交后数据将锁定，不允许修改，确认要提交吗?'
          }
          this.$confirm(message, '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
            beforeClose: (action, instance, done) => {
              done()
            }
          }).then(() => {
            // 同步标段
            let sectionSystemCode = []
            this.addForm.bidSectionList.forEach(item => {
              sectionSystemCode.push(item.code)
            })
            this.addForm.sectionSystemCode = sectionSystemCode.join()
            this.addForm.status = payStatus
            this.addForm.itemType = 6
            this.addForm.tenderSystemCode = this.$route.query.tenderSystemCode
            this.addForm.groupCode = this.$route.query.settlementGroupCode
            if (!this.addForm.objectId) {
              settlementItem.saveSettlementItem(this.addForm).then((res) => {
                if (res.data.resCode === '0000') {
                  this.$router.go(-1)
                }
              })
            } else {
              settlementItem.updateSettlementItem(this.addForm).then((res) => {
                if (res.data.resCode === '0000') {
                  this.$router.go(-1)
                }
              })
            }
          })
        } else {
          return false
        }
      })
    },
    choiceBtn () {
      this.dialogTableVisible = true
      // 获取标段
      this.getBidSetion()
    },
    getBidSetion () {
      bidSection.getList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        tenderSystemCode: this.$route.query.tenderSystemCode,
        messageLike: this.bidSectionNameLike,
        auditStatusList: '2,4'
      }).then((res) => {
        if (res.data.resCode === '0000') {
          this.dialogTableData = res.data.bidSectionList.list
          this.total = res.data.bidSectionList.total
        }
      })
    },
    dilogConfirm () {
      this.addForm.bidSectionList = []
      this.newTenderTableData.forEach(x => {
        this.addForm.bidSectionList.push(x)
      })
      this.dialogTableVisible = false
    },
    dilogCancel () {
      this.dialogTableVisible = false
    },
    search () {
      this.pageNo = 0
      this.currentPage = 1
      this.getBidSetion()
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    delBtn (index, data) {
      data.splice(index, 1)
      this.addForm.bidSectionList = []
      data.forEach(x => {
        this.addForm.bidSectionList.push(x.value)
      })
    },
    // 表单分页
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getBidSetion()
    },
    handleCurrentNext (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getBidSetion()
    },
    getDetail () {
      if (this.$route.params.objectId) {
        settlementItem.getSettlementItem(this.$route.params.objectId).then((res) => {
          if (res.data.resCode === '0000') {
            this.addForm = res.data.settlementItem
            this.addForm.bidSectionList = res.data.settlementItem.bidSectionList
            this.addForm.paymentTenders = this.paymentTendersTable
          }
        })
      } else {
        this.$set(this.addForm, 'bidderName', this.$route.query.settlementGroupName)
      }
    }
  },
  mounted () {
    this.getDetail()
  }
}
</script>
<style lang="less">
  #otherIncomeAdd {
    .el-cascader, .el-select{
      width: 100%;
    }
    .el-radio__label{
      display: none;
    }
    .choiceBtn{
      border: 1px solid #3f63f6;
      height: 32px;
      padding: 7px 12px;
      margin-bottom: 12px;
    }
    .bidSectionNameNumberSelect{
      width: 109px;
    }
  }
</style>
