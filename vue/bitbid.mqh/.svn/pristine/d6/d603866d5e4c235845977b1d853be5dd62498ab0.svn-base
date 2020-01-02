<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="tendchange-page">
    <div v-if="this.$route.path == '/admin/record/changequery'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>招标备案</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/record/changequery' }">招标变更查询</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!-- 搜索区域start -->
      <div class="search-box">
        <el-form :model="searchForm" ref="searchForm" label-width="100px">
          <el-row :gutter="20">
            <el-col :span="5" style="width:300px">
              <el-form-item label="招标项目名称:">
                <el-input v-model="searchForm.projectNameLike" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5" style="width:300px">
              <el-form-item label="招标项目编号:">
                <el-input v-model="searchForm.codeUserLike" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5" style="width: 300px">
              <el-form-item label="代理机构名称:">
                <el-input v-model="searchForm.entrustEnterpriseNameLike" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5" style="width:300px">
              <el-form-item label="招标方:">
                <el-input v-model="searchForm.enterpriseNameLike" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="2">
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
            prop="name"
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
            prop="agencyName"
            label="代理机构">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="enterpriseName"
            label="招标方">
          </el-table-column>
          <el-table-column
            align="center"
            prop="biddingType"
            label="招标方式"
            :formatter="filterBiddingType">
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
            width="160"
            v-if="$store.getters.permissions.includes('/record/changequery/detail')">
            <template slot-scope="scope">
              <el-button
                @click="handleTableClick(scope.row, 'detail')"
                type="text"
                size="medium"
                v-if="scope.row.detailFlag">招标详情
              </el-button>
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
import {tenderproject} from 'api/index'
export default {
  data () {
    return {
      searchForm: {}, // 搜索表单
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
    this.getAllTenderProjectInfo()
  },
  /** 方法集 */
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    filterBiddingType (row) {
      if (row.biddingType === 1) {
        return '公开招标'
      } else if (row.biddingType === 2) {
        return '邀请招标'
      } else if (row.biddingType === 3) {
        return '谈判采购'
      } else if (row.biddingType === 4) {
        return '询比采购'
      } else if (row.biddingType === 5) {
        return '竞价采购'
      } else if (row.biddingType === 6) {
        return '直接采购'
      } else if (row.biddingType === 7) {
        return '框架协议'
      }
    },
    /** 获取全部的招标项目信息 */
    getAllTenderProjectInfo () {
      let entrustEnterpriseName = ''
      let projectEnterpriseName = ''
      if (this.$store.getters.authUser.enterpriseType === 2 || this.$store.getters.authUser.enterpriseType === 3) {
        projectEnterpriseName = this.$store.getters.authUser.enterpriseName
      } else if (this.$store.getters.authUser.enterpriseType === 4) {
        entrustEnterpriseName = this.$store.getters.authUser.enterpriseName
      }
      tenderproject.getList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        biddingTypeList: '2,3,4,5,6,7',
        sourceType: 1,
        projectNameLike: this.searchForm.projectNameLike || null,
        codeUserLike: this.searchForm.codeUserLike || null,
        entrustEnterpriseNameLike: this.searchForm.entrustEnterpriseNameLike || null,
        entrustEnterpriseName: entrustEnterpriseName,
        enterpriseNameLike: this.searchForm.enterpriseNameLike || null,
        projectEnterpriseName: projectEnterpriseName
      }).then((res) => {
        if (res.data.tenderProjectList && res.data.tenderProjectList.list) {
          /* res.data.tenderProjectList.list.forEach(item => {
            if (item.selectApply) {
              this.tableData.push(item)
              this.total++
            }
          }) */
          this.tableData = res.data.tenderProjectList.list
          this.total = res.data.tenderProjectList.total
          this.tableData.map(item => {
            item.name = item.selectApply.projectName
            item.codeUser = item.selectApply.codeUser
            item.agencyName = item.selectApply.entrustEnterprise.name
            item.enterpriseName = item.selectApply.enterpriseName
            // 判断是否展示“招标详情”按钮
            if (item.noticeProgressList.length > 0) {
              item.detailFlag = true
            } else {
              item.detailFlag = false
            }
          })
        }
      })
    },
    /** 搜索 */
    onSearch () {
      this.getAllTenderProjectInfo()
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 表单分页 */
    handleSizeChange (val) {
      var likeThis = this
      var pageSize = `${val}`
      this.pageNo = 0
      this.currentPage = 1
      this.pageSize = parseInt(pageSize)
      this.$nextTick(() =>
        this.getAllTenderProjectInfo(this.pageNo, this.pageSize, function (resp) {
          likeThis.total = resp.data.tenderProjectList.list.total
        })
      )
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getAllTenderProjectInfo(parseInt(this.pageNo), parseInt(this.pageSize))
    },
    handleTableClick (row, type) {
      if (type === 'look') {
        this.$router.push({ path: '/admin/record/changequery/details', query: {tenderProjectCode: row.code} })
      } else {
        this.$router.push({ path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: row.code} })
      }
    }
  },
  /** 实例创建完成 */
  mounted () {
  }
}
</script>
<style lang="less">
#tendchange-page {
  /** 分页 */
  .block-page {
    padding-top: 20px;
  }
}
</style>
