<template>
  <div>
    <div id="bids-fee-manage" class="content_bigbox">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item>首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/projectpayments' }">项目收支管理</el-breadcrumb-item>
        <el-breadcrumb-item class="active_bread">费用管理</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <!-- table表格 -->
      <div class="da_xz_ckbigbox">
        <el-row>
          <el-col :span="12">
            <el-button icon="el-icon-plus" size='medium' type="primary" class="gongneng_btn" @click="addManage">新增收支项目</el-button>
          </el-col>
          <el-col  :span="8" :offset="4">
            <el-input placeholder="请输入收支项目关键字" class="search-box" v-model="seacherInput">
              <el-button slot="append" icon="el-icon-search" type="" @click="search"></el-button>
            </el-input>
          </el-col>
          <el-dialog title="增加收支项目" :visible.sync="dialogFormVisible" width="40%" :before-close="handleClose">
            <el-form :model="ManageForm" :rules="ManageRules" ref="ManageForm">
              <el-form-item label="收支项目名称" prop="expensesName" :label-width="formLabelWidth">
                <el-input v-model="ManageForm.expensesName" auto-complete="off"></el-input>
              </el-form-item>
              <el-form-item label="收支类型" :label-width="formLabelWidth">
                <el-select v-model="ManageForm.expensesType" placeholder="请选择">
                  <el-option
                    v-for="item in IncometypeName"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="收支方式" :label-width="formLabelWidth">
                <el-select v-model="ManageForm.expensesMethod" placeholder="请选择">
                  <el-option
                    v-for="item in IncomemodeName"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="handleClose">取 消</el-button>
              <el-button type="primary"  @click="Determine('ManageForm')">确 定</el-button>
            </div>
          </el-dialog>
        </el-row>
      </div>
      <el-table
        :data='dataTable'
        style="width: 100%"
        border>
        <el-table-column
          type="index"
          label="序号"
          width="80"
          :index='dataIndex' align="center">
        </el-table-column>
        <el-table-column
          prop='expensesName'
          label="收支项目">
        </el-table-column>
        <el-table-column
          label="操作"
          width="120" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="LookCostList(scope)" v-if="$store.getters.permissions.includes('/projectpayments/delete')">
              查看
            </el-button>
            <el-button type="text" size="small" @click="deleteRow(scope)" v-if="$store.getters.permissions.includes('/projectpayments/delete') && (scope.row.costType > 6)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- table表格 -->
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
    </div>
  </div>
</template>
<script>
import {addtitle} from '../../assets/js/common'
export default{
  data () {
    return {
      dataTable: [],
      dialogFormVisible: false,
      total: 0, // 总条数
      pageSize: 15, // 每页展示条数
      pageNo: 0,
      currentPage: 1,
      dataIndex: 1,
      seacherInput: '',
      searchIs: false,
      // 收支类型
      IncometypeName: [
        {
          value: 0,
          label: '收入，不退还'
        },
        {
          value: 1,
          label: '收入，退还'
        },
        {
          value: 2,
          label: '支出'
        }
      ],
      // 收入方式
      IncomemodeName: [
        {
          value: 0,
          label: '按费用项'
        },
        {
          value: 1,
          label: '按用户'
        },
        {
          value: 2,
          label: '无'
        }
      ],
      formLabelWidth: '120px',
      // 提交时数据
      ManageForm: {
        expensesName: '',
        expensesType: 0,
        expensesMethod: 0
      },
      // 提交时验证
      ManageRules: {
        expensesName: [
          { required: true, message: '收支项目名称不能为空', trigger: 'blur' },
          {min: 1, max: 20, message: '请输入1~20个字符', trigger: ['blur', 'change']}
        ]
      }
    }
  },
  methods: {
    // 列表数据
    managesList () {
      let url = null
      if (this.searchIs) {
        url = `expense?pageNo=${this.pageNo}&pageSize=${this.pageSize}&isDelete=${0}&incExpenseId=${this.$route.query.objectIds}&expensesName=${encodeURIComponent(this.seacherInput)}&_t=${new Date().getTime()}`
      } else {
        url = `expense?pageNo=${this.pageNo}&pageSize=${this.pageSize}&isDelete=${0}&incExpenseId=${this.$route.query.objectIds}&_t=${new Date().getTime()}`
      }
      this.axios.get(url).then((res) => {
        this.dataTable = res.data.expenseManagementPageInfo.list
        this.total = res.data.expenseManagementPageInfo.total
        addtitle(this)
      })
    },
    // 表单分页
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this.managesList(this.searchIs)
    },
    // 搜索
    search () {
      this.axios.get(`expense?pageNo=${this.pageNo}&pageSize=${this.pageSize}&isDelete=${0}&incExpenseId=${this.$route.query.objectIds}&expensesName=${encodeURIComponent(this.seacherInput)}&_t=${new Date().getTime()}`).then((res) => {
        this.dataTable = res.data.expenseManagementPageInfo.list
        this.total = res.data.expenseManagementPageInfo.total
        addtitle(this)
        this.currentPage = 1
        this.dataIndex = 1
        this.pageNo = (this.currentPage - 1) * this.pageSize
        this.searchIs = true
      })
    },
    // 添加按钮
    addManage () {
      this.dialogFormVisible = true
    },
    // 确定提交数据
    Determine (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.ManageForm.incExpenseId = this.$route.query.objectIds
          this.axios.post('expense', this.ManageForm).then((res) => {
            console.log(res)
            this.managesList()
            this.handleClose()
          })
        } else {
          return false
        }
      })
    },
    // 删除
    deleteRow (scope) {
      this.$confirm('确定要删除吗?删除后不可恢复，是否继续；确认后删除记录?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        this.axios.delete(`expense/${scope.row.objectId}`).then((res) => {
          this.managesList()
        })
      }).catch(() => {
        return false
      })
    },
    // 取消和关闭按钮
    handleClose () {
      this.dialogFormVisible = false
      this.ManageForm.expensesName = ''
    },
    // 查看
    LookCostList (scope) {
      if (scope.row.costType === 2) {
        this.$router.push({path: '/projectpayments/manage/bondlist', query: {incExpenseIds: this.$route.query.objectIds, costTypes: scope.row.costType, name: scope.row.expensesName, expenseId: scope.row.objectId}})
      } else if (scope.row.costType === 4) {
        this.$router.push({path: '/projectpayments/manage/other-income', query: {incExpenseIds: this.$route.query.objectIds, costTypes: scope.row.costType, name: scope.row.expensesName, expenseId: scope.row.objectId}})
      } else if (scope.row.costType === 5) {
        this.$router.push({path: '/projectpayments/manage/other-expenditure', query: {incExpenseIds: this.$route.query.objectIds, costTypes: scope.row.costType, name: scope.row.expensesName, expenseId: scope.row.objectId}})
      } else if (scope.row.costType === 6) {
        this.$router.push({path: '/projectpayments/manage/balance-payment', query: {incExpenseIds: this.$route.query.objectIds, costTypes: scope.row.costType, name: scope.row.expensesName, expenseId: scope.row.objectId}})
      } else {
        this.$router.push({path: '/projectpayments/manage/biddocumentlist', query: {incExpenseIds: this.$route.query.objectIds, costTypes: scope.row.costType, name: scope.row.expensesName, expenseId: scope.row.objectId}})
      }
    }
  },
  mounted () {
    this.managesList()
  }
}
</script>
<style lang='less'>
  body{
    background:#eaedf1;
  }
#bids-fee-manage{
  .breadcrumb_box{
      width: 100%;
      height: 60px;
      border-bottom: 1px solid #eeeeee;
      padding: 0 20px;
      box-sizing: border-box;
      line-height: 60px;
  }
  .el-breadcrumb__inner{
      color: #999999!important;
  }
  .el-button--primary {
    color: #fff;
    background-color: #6582f8;
    border-color: #6582f8;
  }
  .da_xz_ckbigbox {
    padding: 10px 20px;
  }
  .active_bread>.el-breadcrumb__inner{
      color:#333333!important;
  }
  .header{background: #f5f7f8}
  .content_bigbox{margin: 0px auto;width: 1200px;background: #fff}
  /* 底部按钮 */
  .gongneng_btnbox{text-align: center;padding: 32px 0 48px}
  .el-select {
    display: inline-block;
    position: relative;
    width: 100%;
  }
}
</style>
