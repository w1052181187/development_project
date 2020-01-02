<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="infoconfirm-page">
    <div v-if="this.$route.path == '/admin/record/infoconfirm'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>招标备案</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/record/infoconfirm' }">招标信息确认</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!-- 搜索区域start -->
      <div class="search-box">
        <el-form :model="searchForm" ref="searchForm" label-width="100px">
          <el-row :gutter="20">
            <el-col :span="6" style="width:340px">
              <el-form-item label="招标项目名称:">
                <el-input v-model="searchForm.tenderProjectNameLike" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="width:340px">
              <el-form-item label="招标项目编号:">
                <el-input v-model="searchForm.codeUserLike" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="width:340px">
              <el-form-item label="标段名称:">
                <el-input v-model="searchForm.sectionNameLike" clearable></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="5" style="width:340px">
              <el-form-item label="项目单位:">
                <el-input v-model="searchForm.projectEnterpriseNameLike" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5" style="width:340px">
              <el-form-item label="中标单位:">
                <el-input v-model="searchForm.winnerBidderNameLike" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="width:340px" v-if="this.$store.getters.authUser.enterpriseType === 4">
              <el-form-item label="状态:">
                <el-select v-model="searchForm.isConfirm" clearable placeholder="请选择状态">
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
            prop="sectionName"
            label="标段名称">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="tenderProjectName"
            label="所属项目">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="codeUser"
            label="所属项目编号">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="projectEnterpriseName"
            label="项目单位">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="winnerBidderName"
            label="中标单位">
          </el-table-column>
          <el-table-column
            align="center"
            prop="isConfirm"
            :formatter="filterIsConfirm"
            label="确认状态"
            width="100">
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
            width="80"
            v-if="$store.getters.permissions.includes('/record/infoconfirm/detail')">
            <template slot-scope="scope">
              <el-button
                @click="handleTableClick(scope.row, 'look')"
                type="text"
                size="medium">查看
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
    </div>
    <div v-else>
      <router-view>
      </router-view>
    </div>
  </div>
</template>
<script>
import {bidInfoConfirm} from 'api/index'
export default {
  data () {
    return {
      searchForm: {}, // 搜索条件表单
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
    indexMethod (index) {
      return index + (this.currentPage - 1) * this.pageSize + 1
    },
    /** 表单分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.getBidInfoConfirmList(this.pageNo)
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getBidInfoConfirmList(this.pageNo)
    },
    filterIsConfirm (row, cloumn) {
      if (row.isConfirm === 0) {
        return '未确认'
      } else if (row.isConfirm === 1) {
        return '已确认'
      }
    },
    resetView () {
      if (this.$route.query.pageNo && this.$route.query.pageSize && this.$route.query.currentPage) {
        this.currentPage = parseInt(this.$route.query.currentPage)
        this.pageNo = parseInt(this.$route.query.pageNo)
        this.pageSize = parseInt(this.$route.query.pageSize)
      }
      this.getBidInfoConfirmList(this.pageNo)
    },
    /** 搜索 */
    onSearch () {
      this.getBidInfoConfirmList(0)
    },
    /** 表格操作 */
    handleTableClick (row, type) {
      if (Object.is(type, 'look')) {
        this.$router.push({path: '/admin/record/infoconfirm/details',
          query: {objectId: row.objectId, pageNo: this.pageNo, pageSize: this.pageSize, currentPage: this.currentPage}})
      }
    },
    getBidInfoConfirmList (pageNo) {
      let query = {
        pageNo: pageNo,
        pageSize: this.pageSize,
        tenderProjectNameLike: this.searchForm.tenderProjectNameLike || null,
        codeUserLike: this.searchForm.codeUserLike || null,
        sectionNameLike: this.searchForm.sectionNameLike || null,
        projectEnterpriseNameLike: this.searchForm.projectEnterpriseNameLike || null,
        winnerBidderNameLike: this.searchForm.winnerBidderNameLike || null,
        isConfirm: this.searchForm.isConfirm
      }
      if (this.$store.getters.authUser.enterpriseType === 4) {
        query.agentEnterpriseId = this.$store.getters.authUser.enterpriseId
      } else if (this.$store.getters.authUser.enterpriseType === 2 ||
        this.$store.getters.authUser.enterpriseType === 3) {
        query.projectEnterpriseId = this.$store.getters.authUser.enterpriseId
        query.isConfirm = 1
      } else if (this.$store.getters.authUser.enterpriseType === 1) {
        query.isConfirm = 1
      }
      bidInfoConfirm.getList(query).then(res => {
        let data = res.data.bidInfoConfirmList
        if (data) {
          this.tableData = data.list
          this.total = data.total
          this.tableData.map(item => {
            if (item.tenderProject) {
              item.tenderProjectName = item.tenderProject.tenderProjectName
              item.codeUser = item.tenderProject.codeUser
            }
            return item
          })
        }
      })
    }
  },
  /** 实例创建完成 */
  mounted () {
    this.getBidInfoConfirmList(this.pageNo)
  },
  /** 过滤 */
  filters: {
  }
}
</script>
<style lang="less">
#infoconfirm-page {
  .block-page {
    padding: 20px;
  }
}
</style>
