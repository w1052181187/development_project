<template>
  <div>
    <div id="list" class="content_bigbox">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item :to="{ path: '/index'}">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/projectpayments' }">项目收支管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/projectpayments/manage',query: {objectIds: this.$route.query.incExpenseIds, bitbidObjectId: this.$route.query.bitbidObjectId}}">费用管理</el-breadcrumb-item>
        <el-breadcrumb-item>{{this.$route.query.name}}</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="da_xz_ckbigbox">
        <el-row>
          <div class="seacher_box">
            <el-input placeholder="收入项目关键字" v-model="deptseainput" v-if="this.$route.query.incomeStatus"></el-input>
            <el-input placeholder="缴费单位关键字" v-model="deptseainput" v-else></el-input>
            <el-input placeholder="标段名称关键词" v-model="biaoduanseainput"></el-input>
            <el-button slot="append" icon="el-icon-search" type="" @click="search"></el-button>
          </div>
        </el-row>
      </div>
      <el-table
        header-row-class-name='header'
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
          prop='paymentUnitName'
          label="收入项目" v-if="this.$route.query.incomeStatus">
        </el-table-column>
        <el-table-column
          prop='paymentUnitName'
          label="缴费单位" v-else>
        </el-table-column>
        <el-table-column
          prop='paymentAmount'
          label="缴费金额">
        </el-table-column>
        <el-table-column
          prop='paymentTypes'
          label="缴费方式">
        </el-table-column>
        <el-table-column
          prop='paymentTime'
          label="缴费时间">
        </el-table-column>
        <el-table-column
          prop='SectionInformations'
          label="缴费标段">
          <template slot-scope="scope">
            <span class="jfbdbox" v-for="(item,index) in scope.row.sectionInformations" :key="index">
              <span>{{item.sectionName}}</span>
              <span style="margin-left: 10px">({{item.sectionNumber}})</span>
            </span>
          </template>
        </el-table-column>
        <el-table-column
          prop='tenderType'
          label="标书类型"
          v-if="this.$route.query.name === '标书费'">
          <template slot-scope="scope">
            {{scope.row.tenderType===0?'招标文件':'资格预审文件'}}
          </template>
        </el-table-column>
        <el-table-column
          prop='paymentProjress'
          label="缴费进度">
          <template slot-scope="scope">
            {{scope.row.paymentProjress===0?'已缴费':'已退还'}}
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          width="240">
          <template slot-scope="scope">
            <el-button
              type="text"
              size="small" @click="lookBtn(scope)"  v-if="$store.getters.permissions.includes('/projectpayments/charge/detail')">
              查看
            </el-button>
          </template>
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
    </div>
  </div>
</template>
<script>
import {addtitle} from '../../../assets/js/common'
export default{
  data () {
    return {
      dataTable: [],
      dialogFormVisible: false,
      changeVisible: false,
      biaoduanseainput: '',
      deptseainput: '',
      searchIs: false,
      formLabelWidth: '80px',
      total: 0, // 总条数
      pageSize: 15, // 每页展示条数
      pageNo: 0,
      currentPage: 1,
      dataIndex: 1,
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
    biddocumentList () {
      let url = null
      if (this.searchIs) {
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
        this.dataIndex = 1
        this.pageNo = (this.currentPage - 1) * this.pageSize
        this.searchIs = true
      })
    },
    // 表单分页
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this.biddocumentList(this.searchIs)
    },
    addCharge () {
      this.$router.push({path: '/projectpayments/manage/biddocumentlist/add', query: {name: this.$route.query.name, incExpenseIds: this.$route.query.incExpenseIds, costTypes: this.$route.query.costTypes, expenseId: this.$route.query.expenseId, incomeStatus: this.$route.query.incomeStatus, expensesType: this.$route.query.expensesType}})
    },
    editBtn (scope) {
      this.$router.push({path: '/projectpayments/manage/biddocumentlist/update', query: {name: this.$route.query.name, incExpenseIds: this.$route.query.incExpenseIds, costTypes: this.$route.query.costTypes, objectIds: scope.row.objectId, expenseId: this.$route.query.expenseId, incomeStatus: this.$route.query.incomeStatus, expensesType: this.$route.query.expensesType}})
    },
    lookBtn (scope) {
      this.$router.push({path: `/projectpayments/manage/biddocumentlist/detail/${scope.row.objectId}`, query: {name: this.$route.query.name, incExpenseIds: this.$route.query.incExpenseIds, bitbidObjectId: this.$route.query.bitbidObjectId, costTypes: this.$route.query.costTypes, objectIds: scope.row.objectId, expenseId: this.$route.query.expenseId, incomeStatus: this.$route.query.incomeStatus, expensesType: this.$route.query.expensesType}})
    },
    // 删除
    deleteObject (scope) {
      this.$confirm('确定要删除吗?删除后不可恢复，是否继续；确认后删除记录?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        this.axios.delete(`charge/${scope.row.objectId}`).then((res) => {
          this.biddocumentList()
        })
      }).catch(() => {
        return false
      })
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
#list{
    .breadcrumb_box{
        width: 100%;
        height: 60px;
        border-bottom: 1px solid #eeeeee;
        padding: 0 20px;
        box-sizing: border-box;
        line-height: 60px;
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
  .el-input {
    position: relative;
    font-size: 12px;
    display: inline-block;
    width: 40%;
  }
  .jfbdbox{
    display: block;
    overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
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
}
</style>
