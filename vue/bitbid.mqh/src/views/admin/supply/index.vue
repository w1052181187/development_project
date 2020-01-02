<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="supply-page">
    <div v-if="this.$route.path == '/admin/supply/supplypool'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>供应商库</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/supply/supplypool' }">供应商库</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!-- 搜索区域start -->
      <div class="search-box search-warp">
        <el-form :model="searchForm" label-width="100px">
          <el-row :gutter="20">
            <el-col :span="6" style="width:300px">
              <el-form-item label="供应商:">
                <el-input v-model="searchForm.messageLike" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="2" style="padding-left:30px;">
              <el-form-item>
                <el-button type="primary" @click="onSearch('searchForm')" icon="el-icon-search" style="margin-left:-70px;">搜索</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <!-- 搜索区域end -->
      <!-- 主体内容区域start -->
      <div class="maincont-box intermange-cont">
        <div class="add-btn">
          <el-button type="warning" icon="el-icon-plus" @click="dialogVisible=true">新 增</el-button>
        </div>
        <el-table
          :data="tableData"
          :row-class-name="tableRowClassName"
          border
          style="width: 100%">
          <el-table-column
            type="index"
            label="序号"
            align="center"
            :index="indexMethod"
            width="55">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="supplierName"
            label="供应商">
          </el-table-column>
          <el-table-column
            align="center"
            prop="supplierAddress"
            show-overflow-tooltip
            label="地址">
          </el-table-column>
          <el-table-column
            align="center"
            prop="bidCount"
            label="参与投标数量">
          </el-table-column>
          <el-table-column
            align="center"
            prop="winBidCount"
            label="中标数量">
          </el-table-column>
          <el-table-column
            align="center"
            prop="winRate"
            label="中标率">
          </el-table-column>
          <el-table-column
            align="center"
            label="操作">
            <template slot-scope="scope">
              <el-button
                @click="handleTableClick(scope.row,'look')"
                type="text" size="medium" v-if="$store.getters.permissions.includes('/supply/supplypool/detail')">
                查看
              </el-button>
              <el-button
                @click="handleTableClick(scope.row,'black')"
                type="text" size="medium">
                移入黑名单
              </el-button>
              <el-button
                v-if="scope.row.bidCount === 0"
                @click="handleTableClick(scope.row,'del')"
                type="text" size="medium">
                删除
              </el-button>
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
      </div>
      <!-- 主体内容区域end -->
      <div class="dialog-box">
        <el-dialog
          title="新增"
          @close="cancelBtnClick"
          :visible.sync="dialogVisible"
          width="30%"
          center>
          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">
            <el-form-item label="供应商名称:" prop="supplierName">
              <el-input v-model="ruleForm.supplierName"></el-input>
            </el-form-item>
            <el-form-item label="供应商地址:" prop="supplierAddress">
              <el-input v-model="ruleForm.supplierAddress"></el-input>
            </el-form-item>
            <el-form-item label="统一社会信用代码:" prop="supplierCredicCode">
              <el-input v-model="ruleForm.supplierCredicCode"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="cancelBtnClick">取 消</el-button>
            <el-button type="primary" @click="confirmBtnClick('ruleForm')">确 定</el-button>
          </span>
        </el-dialog>
        <el-dialog
          title="移入黑名单"
          @close="cancelBtnblack"
          :visible.sync="blackDialogVisible"
          width="30%"
          center>
          <el-form :model="ruleBlackForm" status-icon :rules="rulesBlack" ref="ruleBlackForm" label-width="130px" class="demo-ruleForm">
            <el-form-item label="黑名单有效期至:" prop="blacklistEndDate">
              <el-date-picker
                v-model="ruleBlackForm.blacklistEndDate"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="cancelBtnblack">取 消</el-button>
            <el-button type="primary" @click="confirmBtnBlack('ruleBlackForm')">确 定</el-button>
          </span>
        </el-dialog>
      </div>
    </div>
    <div v-else>
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
import {supplierBase} from 'api/index'
import {getCurrentDateAfter} from 'common/js/getCurrentDateAfter'
export default {
  data () {
    return {
      dialogVisible: false,
      ruleForm: {},
      rules: {
        supplierName: [
          { required: true, message: '请填写供应商名称', trigger: 'blur' }
        ],
        supplierCredicCode: [
          { required: true, message: '请填写统一社会信用代码', trigger: 'blur' }
        ],
        supplierAddress: [
          { required: true, message: '请填写供应商地址', trigger: 'blur' }
        ]
      }, // 校验规则
      blackDialogVisible: false, // 黑名单-弹窗
      ruleBlackForm: {},
      rulesBlack: {
        blacklistEndDate: [
          { required: true, message: '请选择日期', trigger: 'change' }
        ]
      },
      searchForm: {}, // 搜索
      tableData: [], // 表格数据
      pageNo: 0,
      currentPage: 1,
      pageSize: 10,
      total: null, // 总条数
      pageSizeList: [10, 20, 30, 40, 50]
    }
  },
  computed: {},
  created () {
  },
  watch: {
  },
  /*
  * 方法集
  */
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * this.pageSize + 1
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 搜索 */
    onSearch () {
      this.getSupplierBaseList(0)
    },
    /** 表单分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.getSupplierBaseList(this.pageNo)
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getSupplierBaseList(this.pageNo)
    },
    /** 表格操作 */
    handleTableClick (row, type) {
      if (Object.is(type, 'look')) {
        this.$router.push({path: '/admin/supply/supplypool/details', query: {code: row.code, operate: 'white', currentPage: this.currentPage, pageNo: this.pageNo, pageSize: this.pageSize}})
      } else if (Object.is(type, 'black')) {
        this.ruleBlackForm = row
        let date = new Date()
        this.ruleBlackForm.blacklistEndDate = new Date(getCurrentDateAfter(3, '-', date.getDate()))
        this.blackDialogVisible = true
      } else if (Object.is(type, 'del')) {
        this.deleteClick(row)
      }
    },
    // 删除
    deleteClick (row) {
      this.$confirm('此操作永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        supplierBase.delete(row.objectId).then(() => {
          this.getSupplierBaseList(this.pageNo)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    getSupplierBaseList (pageNo) {
      let query = {
        pageNo: pageNo,
        pageSize: this.pageSize,
        isInBlacklist: 0,
        messageLike: this.searchForm.messageLike || null
      }
      supplierBase.getList(query).then(res => {
        let data = res.data.supplierBaseList
        if (data) {
          this.tableData = data.list
          this.tableData.map(supplierBase => {
            if (!supplierBase.bidCount) {
              supplierBase.winRate = 0 + '%'
            } else {
              supplierBase.winRate =
                Math.round(supplierBase.winBidCount / supplierBase.bidCount * 10000) / 100.00 + '%'
            }
          })
          this.total = data.total
        }
      })
    },
    /** 新增-弹窗-确定 */
    confirmBtnClick (name) {
      this.$refs[name].validate((vaild) => {
        if (vaild) {
          supplierBase.update(this.ruleForm).then(res => {
            this.getSupplierBaseList(this.pageNo)
            this.dialogVisible = false
          })
        } else {
          return false
        }
      })
    },
    /** 新增-弹窗-取消 */
    cancelBtnClick () {
      this.dialogVisible = false
      this.ruleForm = {}
      this.$refs['ruleForm'].resetFields()
    },
    /** 黑名单-确认 */
    confirmBtnBlack (name) {
      this.$refs[name].validate((vaild) => {
        if (vaild) {
          this.ruleBlackForm.isInBlacklist = 1
          supplierBase.update(this.ruleBlackForm).then(res => {
            this.getSupplierBaseList(this.pageNo)
            this.blackDialogVisible = false
          })
        } else {
          return false
        }
      })
    },
    /** 黑名单-取消 */
    cancelBtnblack () {
      this.blackDialogVisible = false
      this.ruleBlackForm = {}
      this.$refs['ruleBlackForm'].resetFields()
    }
  },
  /*
  * 实例创建完成
  */
  mounted () {
    if (this.$route.query.pageNo && this.$route.query.pageSize && this.$route.query.currentPage) {
      this.currentPage = parseInt(this.$route.query.currentPage)
      this.pageNo = parseInt(this.$route.query.pageNo)
      this.pageSize = parseInt(this.$route.query.pageSize)
    }
    this.getSupplierBaseList(this.pageNo)
  },
  /*
  * 过滤
  */
  filters: {}
}
</script>
<style lang="less">
  #supply-page {
    /*搜索*/
    .search-warp {
      .el-form-item {
        margin-bottom: 0;
      }
    }
    .intermange-cont {
      .add-btn {
        text-align: left;
        padding-bottom: 15px;
      }
    }
    .block-page {
      padding-top: 20px;
    }
  }
</style>
