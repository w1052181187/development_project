<template>
  <div>
    <div id="addItembid" class="content_bigbox">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/projectpayments' }">
          项目收支管理
        </el-breadcrumb-item>
        <el-breadcrumb-item :to="{path:'/projectpayments/manage/other-income',query: {name: this.$route.query.name, incExpenseIds: this.$route.query.incExpenseIds, costTypes:  this.$route.query.costTypes, expenseId: this.$route.query.expenseId}}">
          {{this.$route.query.name}}
        </el-breadcrumb-item>
        <el-breadcrumb-item class="active_bread">编辑收入</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <!-- form表单 -->
      <el-row style="margin-top:48px">
        <el-form :model="ruleForm"  ref="ruleForm" :rules="rules"  label-width="160px">
          <el-col :span="24">
            <el-form-item label="收入项目：" prop="paymentUnitName">
              <el-input class="el-inputbox" v-model="ruleForm.paymentUnitName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="金额(元)：" prop="paymentAmount">
              <el-input v-model="ruleForm.paymentAmount"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="收入方式：" prop="paymentType">
              <el-select class="el-selectbox" v-model="ruleForm.paymentType" placeholder="请选择">
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
            <el-form-item label="收费时间：" prop="paymentTime">
              <el-date-picker class="releaseTimebox"
                v-model="ruleForm.paymentTime"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                start-placeholder="选择日期时间">
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
              <span class="xuanzhebtn"  @click="biaoduanchoiceBtn">选择</span>
              <el-dialog title="选择相关标段" :visible.sync="dialogTableVisible">
                <div class="seacherbigboix">
                  <el-select class="searchuser" v-model="BidsValue" placeholder="请选择" @change="userChange">
                    <el-option
                      v-for="item in BidsSelect"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                  <el-input placeholder="" v-model="roleseacher" class="input-with-select roleseacher">
                    <el-button class="roleseacherbtn" slot="append" icon="el-icon-search" @click="secherBids">搜索</el-button>
                  </el-input>
                </div>
                <el-table
                  ref="multipleTable"
                  :data="tableData"
                  tooltip-effect="dark"
                  style="width: 100%"
                  border
                  @select="handleSelectionChange"
                  @select-all="handleSelectionAll">
                  <el-table-column
                    type="selection"
                    width="55" reserve-selection align="center">
                  </el-table-column>
                  <el-table-column
                    prop="sectionNumber"
                    label="标段编号">
                  </el-table-column>
                  <el-table-column
                    prop="sectionName"
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
                  @current-change="handleCurrentChange">
                </el-pagination>
                <!--分页-->
                <div slot="footer" class="dialog-footer operationbtn">
                  <el-button type="primary" @click="Determinebtn">确 定</el-button>
                  <el-button type="info" @click="cancelBtn">取 消</el-button>
                </div>
              </el-dialog>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注：">
              <el-input
                type="textarea"
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
import {addtitle} from '../../../assets/js/common'
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
      dialogFormVisible: false,
      dialogTableVisible: false,
      // 标段初始化
      btnStatussubime: true,
      searchIs: false,
      total: 0, // 总条数
      pageSize: 5, // 每页展示条数
      pageNo: 0,
      currentPage: 1,
      dataIndex: 1,
      // 搜索是的选择
      BidsSelect: [{
        value: 'sectionName',
        label: '标段名称'
      }, {
        value: 'sectionNumber',
        label: '标段编号'
      }],
      BidsValue: '',
      formLabelWidth: '120px',
      multipleSelection: [],
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
      value: '未激活用户',
      roleseacher: '',
      // 自定义选择内容
      searchSel: '',
      dynamicTags: [],
      tableData: [],
      // 缴费单位form
      Companyform: {
        name: ''
      },
      rules: {
        paymentUnitName: [
          { required: true, message: '收入项目不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: ['blur', 'change'] }
        ],
        paymentAmount: [
          { required: true, message: '金额不能为空', trigger: 'blur' },
          { min: 1, max: 9, message: '请输入1~9个字符', trigger: ['blur', 'change'] },
          { validator: sumTypes, trigger: ['blur', 'change'] }
        ],
        paymentType: [
          { required: true, message: '请选择缴费方式', trigger: 'change' }
        ],
        paymentTime: [
          { required: true, message: '请选择缴费时间', trigger: 'change' }
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
        this.ruleForm.paymentAmount = res.data.chargeManagement.paymentAmount.toString()
        res.data.chargeManagement.sectionInformations.map((ite) => {
          if (ite.isChecked === 0) {
            this.multipleSelection.push(ite)
          }
        })
        this.tableData = res.data.chargeManagement.sectionInformations
      })
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
            if (this.ruleForm.paymentUnitName === '') {
              this.$message({
                type: 'warning',
                message: '请选择缴费单位'
              })
              return false
            } else if (this.ruleForm.sectionInformations.length === 0) {
              this.$message({
                type: 'warning',
                message: '请选择缴费标段'
              })
              return false
            } else {
              this.axios.put('charge', this.ruleForm).then((res) => {
                this.$router.push({path: '/projectpayments/manage/other-income', query: {name: this.$route.query.name, incExpenseIds: this.$route.query.incExpenseIds, costTypes: this.$route.query.costTypes, expenseId: this.$route.query.expenseId}})
              })
            }
          } else {
            return false
          }
        })
      }).catch(() => {
        return false
      })
    },
    quxiaoBtn () {
      this.$router.push({path: '/projectpayments/manage/other-income', query: {name: this.$route.query.name, incExpenseIds: this.$route.query.incExpenseIds, costTypes: this.$route.query.costTypes, expenseId: this.$route.query.expenseId}})
    },
    biaoduanchoiceBtn () {
      this.dialogTableVisible = true
      if (this.btnStatussubime) {
        this.axios.get(`section-information/listPage?pageNo=${this.pageNo}&pageSize=${this.pageSize}&isEdit=${1}&chargeId=${this.$route.query.objectIds}&incExpenseId=${this.$route.query.incExpenseIds}&_t=${new Date().getTime()}`).then((res) => {
          this.tableData = res.data.sectionInformationPageInfo.list
          this.total = res.data.sectionInformationPageInfo.total
          this.tableData.map((item, index) => {
            if (item.isChecked === 0) {
              this.$refs.multipleTable.toggleRowSelection(this.tableData[index], true)
            } else {
              this.$refs.multipleTable.toggleRowSelection(this.tableData[index], false)
            }
          })
        })
        this.btnStatussubime = false
      }
    },
    // 分页列表接口
    pageList () {
      this.axios.get(`section-information/listPage?pageNo=${this.pageNo}&pageSize=${this.pageSize}&isEdit=${1}&chargeId=${this.$route.query.objectIds}&incExpenseId=${this.$route.query.incExpenseIds}&_t=${new Date().getTime()}`).then((res) => {
        this.tableData = res.data.sectionInformationPageInfo.list
        this.total = res.data.sectionInformationPageInfo.total
        this.tableData.map((item, index) => {
          if (item.isChecked === 0) {
            this.$refs.multipleTable.toggleRowSelection(this.tableData[index], true)
          } else {
            this.$refs.multipleTable.toggleRowSelection(this.tableData[index], false)
          }
        })
      })
    },
    handleSelectionChange (selection, row) {
      if (selection.length === 0) {
        this.multipleSelection = []
      } else {
        let temp = []
        for (let i = 0; i < selection.length; i++) {
          if (!temp.includes(selection[i].objectId)) {
            temp.push(selection[i])
          }
        }
        this.multipleSelection = temp
      }
    },
    handleSelectionAll (selection) {
      if (selection.length === 0) {
        this.multipleSelection = []
      } else {
        this.multipleSelection = selection
      }
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
    cancelBtn () {
      this.dialogTableVisible = false
    },
    // 搜索前选择搜索方式
    userChange (val) {
      this.searchSel = val
    },
    // 标段搜索
    secherBids () {
      let url = null
      if (this.searchSel === '') {
        this.$message({
          type: 'warning',
          message: '请选择查询方式'
        })
        return false
      } else if (this.searchSel === 'sectionName') {
        url = `section-information/listPage?pageNo=${this.pageNo}&pageSize=${this.pageSize}&isEdit=${1}&chargeId=${this.$route.query.objectIds}&incExpenseId=${this.$route.query.incExpenseIds}&sectionName=${encodeURIComponent(this.roleseacher)}&_t=${new Date().getTime()}`
      } else if (this.searchSel === 'sectionNumber') {
        url = `section-information/listPage?pageNo=${this.pageNo}&pageSize=${this.pageSize}&isEdit=${1}&chargeId=${this.$route.query.objectIds}&incExpenseId=${this.$route.query.incExpenseIds}&sectionNumber=${encodeURIComponent(this.roleseacher)}&_t=${new Date().getTime()}`
      }
      this.axios.get(url).then((res) => {
        this.tableData = res.data.sectionInformationPageInfo.list
        this.total = res.data.sectionInformationPageInfo.total
        this.tableData.map((item, index) => {
          if (item.isChecked === 0) {
            this.$refs.multipleTable.toggleRowSelection(this.tableData[index], true)
          } else {
            this.$refs.multipleTable.toggleRowSelection(this.tableData[index], false)
          }
        })
        addtitle(this)
        this.currentPage = 1
        this.dataIndex = 1
        this.pageNo = (this.currentPage - 1) * this.pageSize
        this.searchIs = true
      })
    },
    // 表单分页
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this.pageList(this.searchIs)
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
      display: block!important;
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
    .el-breadcrumb__inner{
      color: #999999!important;
    }
    .el-form-item__content {
      margin-left: 168px !important;
      width: auto;
    }
    .el-form-item__content .el-select {
      float: inherit;
      width: auto;
    }
    .el-button--primary {
      background-color: #409eff;
      border-color: #409eff;
    }
    .el-select {
      vertical-align: middle;
    }
    .el-form-item__content .el-input {
      float: inherit;
    }
    .el-input__suffix {
      line-height: 40px;
    }
  }
</style>
