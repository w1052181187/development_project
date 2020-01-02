<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="tendresult-page">
    <div v-if="this.$route.path == '/admin/record/resultquery'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>招标备案</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/record/resultquery' }">招标结果查询</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!-- 搜索区域start -->
      <div class="search-box">
        <el-form :model="searchForm" ref="searchForm" label-width="100px">
          <el-row :gutter="20">
            <el-col :span="6" style="width:400px">
              <el-form-item label="招标项目名称:">
                <el-input v-model="searchForm.projectName" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="width:400px">
              <el-form-item label="招标项目编号:">
                <el-input v-model="searchForm.codeUser" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="width:400px">
              <el-form-item label="代理机构名称:">
                <el-input v-model="searchForm.entrustEnterpriseName" clearable></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="5" style="width:400px">
              <el-form-item label="招标方:">
                <el-input v-model="searchForm.enterpriseName" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="width:400px">
              <el-form-item label="确认状态:">
                <el-select v-model="searchForm.isConfirm" clearable placeholder="请选择状态" style="width:280px">
                  <el-option
                    v-for="item in isConfirmList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
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
      <div class="maincont-box">
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
            align="left"
            header-align="center"
            prop="projectName"
            label="招标项目名称">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="codeUser"
            label="招标项目编号">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="entrustEnterpriseName"
            label="代理机构">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="enterpriseName"
            label="招标方">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="sectionName"
            label="标段名称">
            <template slot-scope="scope">
              <p v-for="(item, index) in scope.row.resultNoticeList" :key="index">{{item.section.name}}</p>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            prop="isConfirm"
            label="确认状态"
            width="100">
            <template slot-scope="scope">
              <p v-for="(item, index) in scope.row.resultNoticeList" :key="index">{{item.isConfirm | filterConfirm}}</p>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
            width="80"
            v-if="$store.getters.permissions.includes('/record/resultquery/detail')">
            <template slot-scope="scope">
              <p v-for="(item, index) in scope.row.resultNoticeList" :key="index">
                <el-button
                  @click="handleTableClick(scope.row, item)"
                  type="text"
                  size="medium">查看
                </el-button>
              </p>
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
    </div>
    <div v-else>
      <router-view>
      </router-view>
    </div>
  </div>
</template>
<script>
import {resultquery} from 'api/index'
export default {
  data () {
    return {
      searchForm: {}, // 搜索条件表单
      linkList: [], // 当前环节list
      isConfirmList: [
        {
          label: '未确认',
          value: 0
        },
        {
          label: '已确认',
          value: 1
        }
      ], // 状态list
      tableData: [], // 表格数据
      pageNo: 0,
      currentPage: 1,
      pageSize: 10,
      total: null, // 总条数
      pageSizeList: [10, 20, 30, 40, 50]
    }
  },
  computed: {},
  watch: {
    $route: 'resetView'
  },
  created () {
  },
  /** 方法集 */
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    resetView () {
      this.getCurrentValue()
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * this.pageSize + 1
    },
    /** 表单分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.getTenderResult(this.pageNo)
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getTenderResult(this.pageNo)
    },
    /** 查看明细页后返回当前页 */
    getCurrentValue () {
      if (this.$route.query.pageNo && this.$route.query.pageSize && this.$route.query.currentPage) {
        this.currentPage = parseInt(this.$route.query.currentPage)
        this.pageNo = parseInt(this.$route.query.pageNo)
        this.pageSize = parseInt(this.$route.query.pageSize)
      }
      this.getTenderResult(this.pageNo)
    },
    /** 搜索 */
    onSearch (pageNo) {
      this.pageNo = 0
      this.currentPage = 1
      this.getTenderResult(this.pageNo)
    },
    /** 招标项目登记 */
    registerClickBtn () {},
    /** 表格操作 */
    handleTableClick (row, resultNotice) {
      this.$router.push({path: '/admin/record/resultquery/details', query: {tenderProjectId: row.objectId, resultNoticeId: resultNotice.objectId, noticeCode: resultNotice.noticeCode, sectionCode: resultNotice.sectionCode, currentPage: this.currentPage, pageNo: this.pageNo, pageSize: this.pageSize}})
    },
    getTenderResult (pageNo) {
      let query = {
        pageNo: pageNo,
        pageSize: this.pageSize,
        tenderResultQueryFlag: 'true',
        projectNameLike: this.searchForm.projectName || null,
        codeUser: this.searchForm.codeUser || null,
        entrustEnterpriseNameLike: this.searchForm.entrustEnterpriseName || null,
        enterpriseNameLike: this.searchForm.enterpriseName || null,
        isConfirm: this.searchForm.isConfirm,
        historySourceType: 1
      }
      this.getTenderProject(query)
    },
    getTenderProject (query) {
      resultquery.getTenderResult(query).then((res) => {
        let data = res.data.tenderProjectList
        if (data.list) {
          this.tableData = data.list
          this.total = data.total
          if (this.tableData.length > 0) {
            for (let i = 0; i < this.tableData.length; i++) {
              this.tableData[i].projectName = this.tableData[i].selectApply.projectName
              this.tableData[i].codeUser = this.tableData[i].selectApply.codeUser
              this.tableData[i].enterpriseName = this.tableData[i].selectApply.enterpriseName
              let drawLotsResult = this.tableData[i].selectApply.drawLotsResult
              if (drawLotsResult) {
                this.tableData[i].entrustEnterpriseName = drawLotsResult.entrustEnterpriseName
              }
            }
          }
        }
      })
    }
  },
  /** 实例创建完成 */
  mounted () {
    this.getCurrentValue()
  },
  /** 过滤 */
  filters: {
    filterConfirm (val) {
      if (val === 0) {
        return '未确认'
      } else if (val === 1) {
        return '已确认'
      }
    }
  }
}
</script>
<style lang="less">
#tendresult-page {
  /** 表格样式 */
  .table-box tr{
    td:nth-child(6),
    td:nth-child(7),
    td:nth-child(8) {
      div.cell {
        padding:0;
      }
      p {
        margin: 0;
        padding: 10px 8px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
      p:not(:last-child) {
        border-bottom: 1px solid #eee;
      }
    }
    td:nth-child(8) {
      p > button {
        padding-top: 17px;
        padding-bottom: 0;
      }
      p button {
        padding-top: 0;
      }
    }
  }
  .block-page {
    padding: 20px;
  }
}
</style>
