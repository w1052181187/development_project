<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="supply-details">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>供应商库</el-breadcrumb-item>
        <el-breadcrumb-item>{{this.$route.query.operate === 'white' || platFlag ?'供应商库':'供应商黑名单'}}</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/supply/supplypool/details' }">查看详情</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="goBack" class="go-back-btn">
        返回
      </el-button>
    </div>
    <!-- 主体内容区域start -->
    <div class="maincont-box supply-cont">
      <!--基本信息start-->
      <div class="supply-info supply-tit">
        <h3>基本信息</h3>
        <el-form ref="infoForm" :model="infoForm" label-width="130px">
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="供应商名称:">
                <span>{{infoForm.supplierName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="供应商地址:">
                <span>{{infoForm.supplierAddress}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="中标项目数:">
                <span>{{infoForm.winBidCount}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="参与项目数:">
                <span>{{infoForm.bidCount}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="统一社会信用代码:">
                <span>{{infoForm.supplierCredicCode}}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <!--基本信息end-->
      <!--参与项目start-->
      <div class="part-pro supply-tit">
        <h3>参与项目</h3>
        <el-tabs v-model="activeName" type="card" @tab-click="handleTabsClick">
          <el-tab-pane label="中标项目" name="winBid">
            <el-table
              :data="winBidTableData"
              :row-class-name="tableRowClassName"
              border
              height="300"
              style="width: 100%">
              <el-table-column
               type="index"
                label="序号"
               :index="indexMethod"
                width="55">
              </el-table-column>
              <el-table-column
                prop="tenderProjectName"
                align="center"
                label="项目名称">
              </el-table-column>
              <el-table-column
                prop="tenderProjectType"
                :formatter="filterType"
                align="center"
                label="项目类型">
              </el-table-column>
              <el-table-column
                prop="sectionName"
                align="center"
                label="参与标段">
              </el-table-column>
              <el-table-column
                prop="ownerEnterpriseName"
                align="center"
                label="项目单位">
              </el-table-column>
              <el-table-column
                prop="agencyEnterpriseName"
                align="center"
                label="招标代理机构">
              </el-table-column>
              <el-table-column
                prop="recordTime"
                :formatter="filterTime"
                align="center"
                label="备案时间">
              </el-table-column>
              <el-table-column
                prop="bidPrice"
                align="center"
                label="投标报价">
              </el-table-column>
              <el-table-column
                align="center"
                label="操作">
                <template slot-scope="scope">
                  <el-button @click="handleTableClick(scope.row,'look')" type="text" size="medium">查看</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
          <el-tab-pane label="未中标项目" name="unwinBid">
            <el-table
              :data="unwinBidTableData"
              :row-class-name="tableRowClassName"
              border
              height="300"
              style="width: 100%">
              <el-table-column
                type="index"
                label="序号"
                :index="indexMethod"
                width="55">
              </el-table-column>
              <el-table-column
                prop="tenderProjectName"
                align="center"
                label="项目名称">
              </el-table-column>
              <el-table-column
                prop="tenderProjectType"
                :formatter="filterType"
                align="center"
                label="项目类型">
              </el-table-column>
              <el-table-column
                prop="sectionName"
                align="center"
                label="参与标段">
              </el-table-column>
              <el-table-column
                prop="ownerEnterpriseName"
                align="center"
                label="项目单位">
              </el-table-column>
              <el-table-column
                prop="agencyEnterpriseName"
                align="center"
                label="招标代理机构">
              </el-table-column>
              <el-table-column
                prop="recordTime"
                :formatter="filterTime"
                align="center"
                label="备案时间">
              </el-table-column>
              <el-table-column
                prop="bidPrice"
                align="center"
                label="投标报价">
              </el-table-column>
              <el-table-column
                align="center"
                label="操作">
                <template slot-scope="scope">
                  <el-button @click="handleTableClick(scope.row,'look')" type="text" size="medium">查看</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
          <!--分页start-->
          <div class="page-box">
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
        </el-tabs>
      </div>
      <!--参与项目end-->
    </div>
    <!-- 主体内容区域end -->
  </div>
</template>
<script>
import {supplierBase, contractInfo} from 'api/index'
import {formatDate} from 'common/js/date'
export default {
  data () {
    return {
      platFlag: this.$route.query.platFlag,
      infoForm: {}, // 基本信息-表单
      activeName: 'winBid',
      winBidTableData: [], // 中标项目
      unwinBidTableData: [], // 未中标项目
      currentPage: 1,
      pageSizeList: [10, 20, 30, 40, 50],
      pageNo: 0,
      pageSize: 10,
      total: null,
      isWin: 1 // 是否中标
    }
  },
  computed: {},
  created () {
  },
  /*
  * 方法集
  */
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * this.pageSize + 1
    },
    filterType (row, column) {
      if (Object.is(row.tenderProjectType, 1)) {
        return '工程'
      } else if (Object.is(row.tenderProjectType, 2)) {
        return '货物'
      } else if (Object.is(row.tenderProjectType, 3)) {
        return '服务'
      }
    },
    filterTime (row, column) {
      if (row.recordTime) {
        let date = new Date(row.recordTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return ''
      }
    },
    /** 分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.getSupplierJoinProjects(this.isWin)
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getSupplierJoinProjects(this.isWin)
    },
    /** 表格操作 */
    handleTableClick (row, type) {
      if (Object.is(type, 'look')) {
        contractInfo.getBySupplier(row.tenderProjectCode, row.sectionCode).then(res => {
          let contractInfo = res.data.contractInfo
          if (contractInfo && Object.is(contractInfo.contractStatus, 2)) {
            this.$router.push({path: '/admin/contract/contractrecord/details', query: {objectId: contractInfo.objectId}})
          } else {
            this.$message('该项目暂无合同信息！')
          }
        })
      }
    },
    handleTabsClick (tab, event) {
      this.pageNo = 0
      this.pageSize = 10
      if (Object.is(tab.name, 'winBid')) {
        this.isWin = 1
      } else if (Object.is(tab.name, 'unwinBid')) {
        this.isWin = 0
      }
      this.getSupplierJoinProjects(this.isWin)
    },
    goBack () {
      if (this.platFlag === 'platFlag') {
        this.$router.go(-1)
      } else {
        let path = ''
        if (Object.is(this.$route.query.operate, 'white')) {
          // 供应商库
          path = '/admin/supply/supplypool'
        } else if (Object.is(this.$route.query.operate, 'black')) {
          // 供应商黑名单
          path = '/admin/supply/supplyblack'
        }
        this.$router.push({path: path, query: {currentPage: this.$route.query.currentPage, pageNo: this.$route.query.pageNo, pageSize: this.$route.query.pageSize}})
      }
    },
    getSupplierBase () {
      let query = {
        code: this.$route.query.code
      }
      supplierBase.getOne(query).then(res => {
        this.infoForm = res.data.supplierBase
      })
    },
    getSupplierJoinProjects (isWin) {
      let query = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        supplierCode: this.$route.query.code,
        isWin: isWin
      }
      supplierBase.getSupplierJoinProjects(query).then(res => {
        let data = res.data.supplierJoinProjects
        if (data) {
          let supplierJoinProjects = data.list
          supplierJoinProjects.map(item => {
            if (item.tenderProject) {
              item.tenderProjectName = item.tenderProject.tenderProjectName
              item.tenderProjectType = item.tenderProject.tenderProjectType
            }
          })
          if (Object.is(isWin, 1)) {
            this.winBidTableData = supplierJoinProjects
          } else if (Object.is(isWin, 0)) {
            this.unwinBidTableData = supplierJoinProjects
          }
          this.total = data.total
        }
      })
    }
  },
  /*
  * 实例创建完成
  */
  mounted () {
    this.getSupplierBase()
    this.getSupplierJoinProjects(this.isWin)
  }
}
</script>
<style lang="less">
#supply-details {
  .supply-info {
    .el-form .el-form-item__content {
      text-align: left;
    }
  }
  .supply-tit {
    h3 {
      text-align: left;
      border-bottom: 1px solid #ddd;
      padding-bottom: 10px;
      margin-top: 0;
    }
  }
  .page-box {
    padding-top: 10px;
  }
}
</style>
