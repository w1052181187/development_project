<template>
  <div>
    <div id="addItembid" class="content_bigbox">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/projectpayments' }">
          项目收支管理
        </el-breadcrumb-item>
        <el-breadcrumb-item :to="{path:'/projectpayments/manage/bondlist',query: {name: this.$route.query.name, incExpenseIds: this.$route.query.incExpenseIds, costTypes:  this.$route.query.costTypes, expenseId: this.$route.query.expenseId, expensesType: this.$route.query.expensesType}}">
          {{this.$route.query.name}}
        </el-breadcrumb-item>
        <el-breadcrumb-item class="active_bread">退还保证金</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <!-- form表单 -->
      <el-row style="margin-top:48px">
        <el-form :model="ruleForm"  ref="ruleForm" :rules="rules"  label-width="160px">
          <el-col :span="24">
            <el-form-item label="缴费单位：" prop="Company">
              <el-input class="el-inputbox" v-model="ruleForm.paymentUnitName" readonly></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="应缴金额(元)：" prop="shouldPayment">
              <el-input v-model="ruleForm.shouldPayment" readonly></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="缴纳金额(元)：" prop="paymentAmount">
              <el-input v-model="ruleForm.paymentAmount" readonly></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="缴费方式：" prop="paymentType">
              <el-select class="el-selectbox" v-model="ruleForm.paymentType" placeholder="请选择" disabled>
                <el-option
                  v-for="item in Paymodefrom"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="缴费时间：" prop="paymentTime">
              <el-date-picker class="releaseTimebox"
                v-model="ruleForm.paymentTime"
                type="datetime"
                disabled
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="相关标段：" class="jfbd_box bitianicon">
              <el-tag
                v-model="ruleForm.dynamicTags"
                :key="index"
                v-for="(tag,index) in dynamicTags"
                :disable-transitions="false">
                <span class="jfbdtitlebox" :title="tag.sectionName">{{tag.sectionName}}</span>
                <span class="jfbdtitlebox" :title="tag.sectionNumber" style="margin-left: 10px">({{tag.sectionNumber}})</span>
              </el-tag>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="退还金额(元)：" prop="surrenderValue">
              <el-input v-model="ruleForm.surrenderValue"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="退还时间：" prop="surrenderTime">
              <el-date-picker class="releaseTimebox"
                v-model="ruleForm.surrenderTime"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="退还方式：" prop="surrenderType">
              <el-select class="el-selectbox" v-model="ruleForm.surrenderType" placeholder="请选择">
                <el-option
                  v-for="item in Paymodefrom"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注：">
              <el-input
                type="textarea"
                readonly
                :autosize="{ minRows: 2, maxRows: 6}"
                placeholder=""
                v-model="ruleForm.remark">
              </el-input>
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <!-- form表单 -->
      <el-row class="gongneng_btnbox">
        <el-button @click="submitpostData('ruleForm')" class="gongneng_btn" size='medium' type="primary">提交</el-button>
        <el-button class="gongneng_btn" size='medium' type="info" @click="quxiaoBtn">
          取消
        </el-button>
      </el-row>
    </div>
  </div>
</template>
<script>
import { sumType } from '../../../assets/js/validate'
export default{
  data () {
    // 金额
    var sumTypes = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入金额'))
      } else if (!sumType(value)) {
        callback(new Error('请输入正确格式'))
      } else {
        callback()
      }
    }
    return {
      ruleForm: {},
      // 搜索是的选择
      BidsSelect: [{
        value: '0',
        label: '标段名称'
      }, {
        value: '1',
        label: '标段编号'
      }],
      BidsValue: '标段名称',
      formLabelWidth: '120px',
      multipleSelection: [],
      Paymodefrom: [{
        value: 0,
        label: '支付宝'
      }, {
        value: 1,
        label: '微信'
      }, {
        value: 2,
        label: '银联'
      }, {
        value: 3,
        label: '现金'
      }, {
        value: 4,
        label: '汇款'
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
      value: '未激活用户',
      roleseacher: '',
      dynamicTags: [],
      tableData: [],
      // 标段初始化
      btnStatussubime: true,
      // 缴费单位form
      Companyform: {
        name: ''
      },
      rules: {
        shouldPayment: [
          { required: true, message: '应缴金额不能为空', trigger: 'blur' },
          { min: 1, max: 9, message: '请输入1~9个字符', trigger: ['blur', 'change'] },
          { validator: sumTypes, trigger: ['blur', 'change'] }
        ],
        paymentAmount: [
          { required: true, message: '缴纳金额不能为空', trigger: 'blur' },
          { min: 1, max: 9, message: '请输入1~9个字符', trigger: ['blur', 'change'] },
          { validator: sumTypes, trigger: ['blur', 'change'] }
        ],
        paymentType: [
          { required: true, message: '请选择缴费方式', trigger: 'change' }
        ],
        paymentTime: [
          { required: true, message: '请选择缴费时间', trigger: 'change' }
        ],
        surrenderValue: [
          { required: true, message: '退还金额不能为空', trigger: 'blur' },
          { min: 1, max: 9, message: '请输入1~9个字符', trigger: ['blur', 'change'] },
          { validator: sumTypes, trigger: ['blur', 'change'] }
        ],
        surrenderType: [
          { required: true, message: '请选择退还方式', trigger: 'change' }
        ],
        surrenderTime: [
          { required: true, message: '请选择退还时间', trigger: 'change' }
        ]
      },
      Companyrules: {
        name: [
          { required: true, message: '请填写缴费单位', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  methods: {
    // 进入页面后查询数据
    editList () {
      this.axios.get(`charge/${this.$route.query.objectIds}?_t=${new Date().getTime()}`).then((res) => {
        this.ruleForm = res.data.chargeManagement
        res.data.chargeManagement.sectionInformations.map((ite) => {
          if (ite.isChecked === 0) {
            this.dynamicTags.push(ite)
          }
        })
        this.ruleForm.shouldPayment = res.data.chargeManagement.shouldPayment.toString()
        this.ruleForm.paymentAmount = res.data.chargeManagement.paymentAmount.toString()
        this.multipleSelection = res.data.chargeManagement.sectionInformations
        this.ruleForm.surrenderValue = res.data.chargeManagement.paymentAmount
        this.ruleForm.surrenderType = res.data.chargeManagement.paymentType
      })
    },
    // 取消
    jiaofeiQuxiao () {
      this.dialogFormVisible = false
      this.Companyform = {}
    },
    // 提交数据
    submitpostData (formName) {
      this.$confirm('是否提交数据吗？', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.ruleForm.sectionInformations = this.dynamicTags
            this.ruleForm.incExpenseId = this.$route.query.incExpenseIds
            this.ruleForm.costType = this.$route.query.costTypes
            this.ruleForm.expenseId = this.$route.query.expenseId
            this.ruleForm.expensesType = this.$route.query.expensesType
            this.ruleForm.paymentProjress = 1
            if (this.ruleForm.surrenderValue > this.ruleForm.paymentAmount) {
              this.$message({
                type: 'warning',
                message: '退还金额不能大于缴纳金额'
              })
              return false
            }
            if (this.ruleForm.paymentTime > this.ruleForm.surrenderTime) {
              this.$message({
                type: 'warning',
                message: '退还时间不能选择缴费时间之前'
              })
              return false
            }
            this.axios.put('charge', this.ruleForm).then((res) => {
              this.$router.push({path: '/projectpayments/manage/bondlist', query: {name: this.$route.query.name, incExpenseIds: this.$route.query.incExpenseIds, costTypes: this.$route.query.costTypes, expenseId: this.$route.query.expenseId, expensesType: this.$route.query.expensesType}})
            })
          } else {
            return false
          }
        })
      }).catch(() => {
        return false
      })
    },
    quxiaoBtn () {
      this.$router.push({path: '/projectpayments/manage/bondlist', query: {name: this.$route.query.name, incExpenseIds: this.$route.query.incExpenseIds, costTypes: this.$route.query.costTypes, expenseId: this.$route.query.expenseId, expensesType: this.$route.query.expensesType}})
    },
    handleSelectionChange (selection, row) {
      this.multipleSelection = selection
    },
    handleSelectionAll (selection) {
      this.multipleSelection = selection
    },
    Determinebtn () {
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择缴费标段'
        })
        return false
      } else {
        this.dynamicTags = this.multipleSelection
        this.dialogTableVisible = false
      }
    },
    // 删除
    handleClose (tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1)
    },
    cancelBtn () {
      this.dialogTableVisible = false
    }
  },
  mounted () {
    this.editList()
  }
}

</script>
<style lang='less'>
  body{
    background:#eaedf1;
  }
  #addItembid{
    padding: 0 20px;
    padding-bottom: 50px;
    .breadcrumb_box{
      width: 100%;
      height: 60px;
      border-bottom: 1px solid #eeeeee;
      padding: 0 20px;
      box-sizing: border-box;
      line-height: 60px;
    }
    .el-dialog__body {
      padding: 10px 20px;
      color: #606266;
      line-height: 24px;
      font-size: 14px;
    }
    .seacherbigboix{
      margin-bottom:30px;
    }
    .xinzhileft_box{
      padding-bottom: 10px;
      border-bottom: 1px solid #CCCCCC;
      margin-bottom: 25px;
    }
    .da_xz_ckbigbox {
      padding: 10px 20px;
    }
    a{
      text-decoration:none;
    }
    .gongneng_btn a{
      color:#fff;
    }
    .gongneng_btnbox{
      text-align: center;
    }
    .el-inputbox{
      position: relative;
      border: 1px solid #dcdfe6;
      border-radius: 4px;
    }
    .xuanzhebtn{
      color:#409EFF;
      position: absolute;
      right: 20px;
      top: 2px;
      font-size:14px;
      cursor: pointer;
    }
    .el-inputbox input{
      width: 94%;
      border: none;
      margin-left: 1px;
    }
    .el-selectbox{
      width: 100%;
    }
    .releaseTimebox{
      width: 100%;
    }
    .jfbd_box .el-form-item__content{
      width: 86.7%;
      padding:10px;
      border-radius: 4px;
      border: 1px solid #dcdfe6;
      box-sizing: border-box;
    }
    .el-tag {
      display: block;
      background-color:transparent;
      padding: 0 10px;
      height: 32px;
      line-height: 30px;
      font-size: 13px;
      color: #333333;
      border-radius: 4px;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      border: none;
      white-space: nowrap;
    }
    .roleseacher{
      width: 30%;
    }
    .searchuser{
      width: 145px;
    }
    .searchuser>.el-input--suffix{
      width: 100%;
    }
    .el-table--border{
      padding-top: 5px;
    }
    .operationbtn{
      text-align: center;
    }
    .bitianicon .el-form-item__label::before{
      content:"*";
      color:#f66c6c;
      margin-right: 5px;
    }
    .jfbdtitlebox{
      width: 45%;
      display: inline-block;
      vertical-align: top;
      overflow: hidden;
      text-overflow:ellipsis;
      white-space: nowrap;
    }
    .el-input__inner{
      border-color: #dcdfe6;
    }
    .el-breadcrumb__inner{
      color: #999999!important;
    }
  }
</style>
