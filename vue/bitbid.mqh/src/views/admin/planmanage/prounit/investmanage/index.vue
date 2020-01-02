<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="investmanage-page">
    <div v-if="this.$route.path == '/admin/planmanage/investmanage'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>计划管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/planmanage/investmanage' }">投资建议计划管理</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!--搜索区域start-->
      <div class="search-box balance-search">
        <el-form :model="searchForm" :inline="true" class="demo-form-inline" style="text-align: left;">
          <el-form-item label="年度:">
            <el-select v-model="searchForm.year" clearable placeholder="请选择">
              <el-option :label="item" :value="item" v-for="(item, index) in yearList" :key="index"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="项目单位:">
            <el-input v-model="searchForm.messageLike" placeholder="请填写项目单位" clearable></el-input>
          </el-form-item>
          <el-form-item label="状态:">
            <el-select v-model="searchForm.status" clearable placeholder="请选择">
              <el-option
                v-for="item in statusList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchFun" icon="el-icon-search">搜索</el-button>
          </el-form-item>
        </el-form>
      </div>
      <!--搜索区域end-->
      <div class="maincont-box planaccept-cont">
        <div class="invest-add">
          <el-button type="warning" @click="addClickBtn" icon="el-icon-plus" v-if="$store.getters.permissions.includes('/planmanage/investmanage/operation')">新 增</el-button>
        </div>
        <!-- 表格区域start -->
        <el-table
          class="table-box"
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
            prop="enterpriseName"
            label="项目单位"
            align="center">
          </el-table-column>
          <el-table-column
            prop="year"
            label="年度"
            align="center"
            width="80">
          </el-table-column>
          <el-table-column
            prop="stagingNumber"
            :formatter="filterNumber"
            label="分期"
            align="center">
          </el-table-column>
          <el-table-column
            prop="fixedQuota"
            label="可用投资额度（万元）"
            align="right"
            width="180">
          </el-table-column>
          <el-table-column
            prop="usedQuota"
            label="已用投资额度（万元）"
            align="right"
            width="180">
          </el-table-column>
          <el-table-column
            prop="submitName"
            label="提交人"
            align="center">
          </el-table-column>
          <el-table-column
            prop="submitTime"
            :formatter="filterSubmitTime"
            label="提交时间"
            align="center">
          </el-table-column>
          <el-table-column
            prop="status"
            label="状态"
            :formatter="filterStatus"
            align="center">
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
            width="160">
            <template slot-scope="scope">
              <el-button
                style="line-height: 0"
                @click="handleTableClick(scope.row,'edit')"
                type="text" size="medium"
                v-if="(scope.row.status === 0 || scope.row.status === 3) && !scope.row.annualProjectPlan && $store.getters.permissions.includes('/planmanage/investmanage/operation')">
                编辑
              </el-button>
              <el-button
                @click="handleTableClick(scope.row,'look')"
                type="text"
                size="medium"
                style="line-height: 0"
                v-if="$store.getters.permissions.includes('/planmanage/investmanage/detail')">
                查看
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 表格区域end -->
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
      <div class="dialog-box">
        <el-dialog
          title="新增"
          @close="cancelForm"
          :visible.sync="dialogVisible"
          width="30%"
          center>
          <el-form :model="dialogForm" :rules= "dialogFormRules" ref="dialogForm"  label-width="100px">
            <el-form-item label="创建年度:" prop="year">
              <el-select v-model="dialogForm.year" placeholder="请选择" clearable @change="getNewestOne">
                <el-option :label="item" :value="item" v-for="(item, index) in createYearList" :key="index"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="分期:" prop="stagingNumber">
              <span>{{dialogForm.stagingNumber | filterStage}}</span>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="cnfirmDialogForm('dialogForm')">保存</el-button>
            <el-button @click="cancelForm">取消</el-button>
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
import {investmanage, enterprise, planapp} from 'api/index'
import {formatDate} from 'common/js/date'
// 实例
export default {
  data () {
    return {
      yearList: this.getYearMethed(),
      statusList: [
        {label: '未提交', value: 0},
        {label: '已提交', value: 1},
        {label: '已核查', value: 2},
        {label: '驳回', value: 3}
      ],
      searchForm: {}, // 搜索
      tableData: [],
      total: null,
      pageNo: 0,
      pageSize: 10,
      currentPage: 1,
      pageSizeList: [10, 20, 30, 40, 50],
      dialogVisible: false, // 弹窗-显隐
      dialogForm: {},
      dialogFormRules: {
        year: [
          { required: true, message: '请选择年度', trigger: 'change' }
        ]
      },
      createYearList: this.getYearMethed(),
      newestScheme: {}, // 同一年度同一项目单位下最新一期的投资建议计划
      annualProjectPlan: {} // 年度招标计划
    }
  },
  watch: {
    $route: 'resetView'
  },
  created () {
  },
  filters: {
    filterStage (val) {
      if (val) {
        return '第' + val + '期'
      }
    }
  },
  methods: {
    resetView () {
      this.getInvestManageList(this.pageNo)
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 进行查看操作后返回当前页 */
    indexMethod (index) {
      return index + (this.currentPage - 1) * this.pageSize + 1
    },
    /** 计算年份-当前年的前一年后一年 */
    getYearMethed () {
      // 返回结果
      let arr = []
      // 获得当前年份
      let date = new Date()
      let nextYear = date.getFullYear() + 1
      let previousYear = date.getFullYear() - 1
      for (let i = previousYear; i <= nextYear; i++) {
        arr.unshift(i)
      }
      return arr
    },
    /** 新增 */
    addClickBtn () {
      this.dialogVisible = true
    },
    /** 取消 */
    cancelForm () {
      this.dialogVisible = false
      this.dialogForm = {}
    },
    /** 弹框确认 */
    cnfirmDialogForm (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          if (this.newestScheme.objectId) {
            if (this.annualProjectPlan.objectId) {
              if (this.annualProjectPlan.status !== 3) {
                this.$message.error('第' + this.newestScheme.stagingNumber + '期招采建议计划未审批通过，不可新增！')
                this.cancelForm()
              } else {
                let newScheme = this.newestScheme
                newScheme.objectId = null
                newScheme.previousCode = newScheme.code
                newScheme.enterpriseId = this.$store.getters.authUser.enterpriseId
                newScheme.stagingNumber = this.dialogForm.stagingNumber
                let fixedQuota = (Number(newScheme.fixedQuota) - Number(newScheme.usedQuota)).toFixed(2)
                newScheme.fixedQuota = fixedQuota
                newScheme.usedQuota = 0
                newScheme.status = 0
                newScheme.submintter = null
                newScheme.submitTime = null
                if (fixedQuota > 0) {
                  investmanage.saveScheme(newScheme).then(res => {
                    let row = res.data.investmentProposalScheme
                    this.$router.push({path: '/admin/planmanage/investmanage/investmain', query: {handleType: 'edit', code: row.code, year: row.year, enterpriseName: row.enterpriseName}})
                  })
                } else {
                  if (newScheme.stagingNumber > 2) {
                    this.$message.error('无可用投资额度，不可新增！')
                    this.cancelForm()
                  } else {
                    investmanage.saveScheme(newScheme).then(res => {
                      let row = res.data.investmentProposalScheme
                      this.$router.push({path: '/admin/planmanage/investmanage/investmain', query: {handleType: 'edit', code: row.code, year: row.year, enterpriseName: row.enterpriseName}})
                    })
                  }
                }
              }
            } else {
              this.$message.error('第' + this.newestScheme.stagingNumber + '期招采建议计划未提交，不可新增！')
              this.cancelForm()
            }
          } else {
            this.$message.error('用户不可新建第一期的投资建议计划')
            this.cancelForm()
          }
        }
      })
    },
    filterSubmitTime (row, column) {
      if (row.submitTime) {
        let date = new Date(row.submitTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return ''
      }
    },
    filterNumber (row, column) {
      if (row.stagingNumber) {
        return '第' + row.stagingNumber + '期'
      }
    },
    filterStatus (row, column) {
      let status = row.status
      if (status === 0) {
        return '未提交'
      } else if (status === 1) {
        return '已提交'
      } else if (status === 2) {
        return '已核查'
      } else if (status === 3) {
        return '退回'
      }
    },
    /** 搜索 */
    searchFun () {
      this.pageNo = 0
      this.currentPage = 1
      this.getInvestManageList(this.pageNo)
    },
    /** 分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.getInvestManageList(this.pageNo)
    },
    handleCurrentChange (nowNum) {
      // 前台页面展示当前页
      this.currentPage = nowNum
      // 后台需要的参数
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getInvestManageList(this.pageNo)
    },
    /** 表格操作 */
    handleTableClick (row, type) {
      if (Object.is(type, 'look')) {
        this.$router.push({path: '/admin/planmanage/investmanage/investlook',
          query: {operate: 'approve', type: 'manage', fixedQuota: row.fixedQuota, usedQuota: row.usedQuota, code: row.code, year: row.year}})
      } else if (Object.is(type, 'edit')) {
        // 编辑
        this.$router.push({path: '/admin/planmanage/investmanage/investmain', query: {handleType: 'edit', code: row.code, year: row.year, enterpriseName: row.enterpriseName}})
      }
    },
    /** 查询投资建议计划信息 */
    getInvestManageList (pageNo) {
      let obj = {
        pageNo: pageNo,
        pageSize: this.pageSize,
        year: this.searchForm.year || null,
        status: this.searchForm.status || null,
        messageLike: this.searchForm.messageLike || null,
        enterpriseId: this.$store.getters.authUser.enterpriseId
      }
      this.getList(obj)
    },
    getList (obj) {
      investmanage.getList(obj).then((res) => {
        if (res.data.investmentProposalSchemeList) {
          this.tableData = res.data.investmentProposalSchemeList.list
          this.total = res.data.investmentProposalSchemeList.total
        } else {
          return false
        }
      })
    },
    /** 查询同一年度同一项目单位下最新一期的投资建议计划以及它对应的年度项目计划 */
    getNewestOne () {
      this.newestScheme = {}
      this.annualProjectPlan = {}
      this.$set(this.dialogForm, 'stagingNumber', '')
      let query = {
        year: this.dialogForm.year,
        enterpriseId: this.$store.getters.authUser.enterpriseId
      }
      investmanage.getNewestOne(query).then(res => {
        if (res.data.investmentProposalScheme) {
          this.newestScheme = res.data.investmentProposalScheme
          this.$set(this.dialogForm, 'stagingNumber', this.newestScheme.stagingNumber + 1)
        }
      }).then(res => {
        if (this.newestScheme.objectId) {
          let query = {
            investmentProposalSchemeCode: this.newestScheme.code,
            year: this.dialogForm.year
          }
          planapp.getList(query).then(res => {
            let annualProjectPlanList = res.data.annualProjectPlanList.list
            if (annualProjectPlanList) {
              if (annualProjectPlanList.length) {
                this.annualProjectPlan = annualProjectPlanList[0]
              }
            }
          })
        }
      })
    }
  },
  computed: {},
  // 实例创建完成
  mounted () {
    this.getInvestManageList(this.pageNo)
  }
}
</script>
<style lang="less">
  #investmanage-page {
    .balance-search {
      .el-form .el-form-item {
        margin-bottom: 0;
      }
    }
    .invest-add {
      text-align: left;
      padding-bottom: 15px;
    }
    // 分页
    .block-page {
      padding-top:15px;
    }
  }
</style>
