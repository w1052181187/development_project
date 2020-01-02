<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="filemanage-page">
    <div v-if="this.$route.path == '/admin/files/filequery'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
           <el-breadcrumb-item>档案管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/files/filequery' }">档案查询</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!-- 搜索区域start -->
      <div class="search-box">
        <el-form :model="searchForm" label-width="110px">
          <el-row :gutter="20">
            <el-col :span="5" style="width:280px">
              <el-form-item label="项目名称:">
                <el-input v-model="searchForm.projectNameLike" style="width:200px"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5" style="width:280px">
              <el-form-item label="项目编号:">
                <el-input v-model="searchForm.codeUserLike" style="width:200px"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5" style="width:300px">
              <el-form-item label="项目类型:">
                <el-select v-model="searchForm.tenderProjectType" clearable placeholder="请选择项目类型" style="width:200px">
                  <el-option label="工程" value="1"></el-option>
                  <el-option label="货物" value="2"></el-option>
                  <el-option label="服务" value="3"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="5" style="width:345px">
              <el-form-item label="采购方式:">
                <el-select v-model="searchForm.biddingType" clearable placeholder="请选择采购方式" style="width:200px">
                  <el-option label="公开招标" value="1"></el-option>
                  <el-option label="邀请招标" value="2"></el-option>
                  <el-option label="谈判采购" value="3"></el-option>
                  <el-option label="询比采购" value="4"></el-option>
                  <el-option label="竞价采购" value="5"></el-option>
                  <el-option label="直接采购" value="6"></el-option>
                  <el-option label="框架协议" value="7"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="5" style="width:280px">
              <el-form-item label="代理机构编号:">
                <el-input v-model="searchForm.userProjectCodeLike" style="width:200px"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5" style="width:280px">
              <el-form-item label="档案编号:">
                <el-input v-model="searchForm.archivesCodeLike" style="width:200px"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5" style="width:305px">
              <el-form-item label="项目单位:">
                <el-input v-model="searchForm.projectEnterpriseNameLike" style="width:200px"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5" style="width:340px">
              <el-form-item label="招标代理机构:">
                <el-input v-model="searchForm.entrustEnterpriseNameLike" style="width:200px"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="2" style="padding-left:30px;">
              <el-button type="primary" @click="onSearch" icon="el-icon-search" style="margin-left:-35px;">搜索</el-button>
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
            prop="tenderProjectName"
            label="项目名称">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="codeUser"
            label="项目编号">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="archivesCode"
            label="档案编号">
          </el-table-column>
          <el-table-column
            align="center"
            prop="tenderProjectType"
            label="项目类型"
            :formatter="filterTenderProjectType">
          </el-table-column>
          <el-table-column
            align="center"
            prop="biddingType"
            label="采购方式"
            :formatter="filterBiddingType">
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
            prop="userProjectCode"
            label="代理机构编号">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="projectEnterpriseName"
            label="项目单位">
          </el-table-column>
          <el-table-column
            align="center"
            prop="fileFrom"
            label="档案来源">
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
            width="80"
            v-if="$store.getters.permissions.includes('/files/filequery/detail')">
            <template slot-scope="scope">
              <el-button
                @click="handleTableClick(scope.row,'look')"
                type="text"
                size="medium"
                class="dresult-span">
                查看
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
import {archivesManagement} from 'api/index'
export default {
  data () {
    return {
      searchForm: {}, // 搜索表单
      tableData: [{name: '名称1'}],
      pageNo: 0,
      currentPage: 1,
      pageSize: 10,
      total: null, // 总条数
      pageSizeList: [10, 20, 30, 40, 50]
    }
  },
  computed: {},
  created () {
    this.getTableData()
  },
  filters: {
  },
  /** 方法集 */
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 得到列表上的数据 */
    getTableData () {
      let obj = {}
      let entrustEnterpriseId = ''
      let projectEnterpriseId = ''
      if (this.$store.getters.authUser.enterpriseType === 2 || this.$store.getters.authUser.enterpriseType === 3) {
        projectEnterpriseId = this.$store.getters.authUser.enterpriseId
      } else if (this.$store.getters.authUser.enterpriseType === 4) {
        entrustEnterpriseId = this.$store.getters.authUser.enterpriseId
      }
      if (this.searchForm.userProjectCode) {
        obj = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          projectEnterpriseId: projectEnterpriseId,
          entrustEnterpriseId: entrustEnterpriseId,
          messageLike: this.searchForm.projectNameLike || '',
          codeUserLike: this.searchForm.codeUserLike || '',
          tenderProjectType: this.searchForm.tenderProjectType || '',
          biddingType: this.searchForm.biddingType || '',
          userProjectCode: this.searchForm.userProjectCodeLike || '',
          archivesCodeLike: this.searchForm.archivesCodeLike || '',
          projectEnterpriseNameLike: this.searchForm.projectEnterpriseNameLike || '',
          entrustEnterpriseNameLike: this.searchForm.entrustEnterpriseNameLike || ''
        }
      } else {
        obj = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          projectEnterpriseId: projectEnterpriseId,
          entrustEnterpriseId: entrustEnterpriseId,
          messageLike: this.searchForm.projectNameLike || '',
          codeUserLike: this.searchForm.codeUserLike || '',
          tenderProjectType: this.searchForm.tenderProjectType || '',
          biddingType: this.searchForm.biddingType || '',
          archivesCodeLike: this.searchForm.archivesCodeLike || '',
          projectEnterpriseNameLike: this.searchForm.projectEnterpriseNameLike || '',
          entrustEnterpriseNameLike: this.searchForm.entrustEnterpriseNameLike || ''
        }
      }
      archivesManagement.getList(obj).then((res) => {
        if (res.data.archivesList && res.data.archivesList.list) {
          this.tableData = res.data.archivesList.list
          this.total = res.data.archivesList.total
          this.tableData.map(item => {
            if (item.tenderProject) {
              item.biddingType = item.tenderProject.biddingType
              item.tenderProjectName = item.tenderProject.tenderProjectName
              item.tenderProjectType = item.tenderProject.tenderProjectType
              item.projectEnterpriseName = item.tenderProject.projectEnterpriseName
              if (item.tenderProject.selectApply || item.tenderProject.projectPlan) {
                item.fileFrom = '业务系统'
                item.codeUser = item.tenderProject.selectApply ? item.tenderProject.selectApply.codeUser : item.tenderProject.customProjectCode
                item.entrustEnterpriseName = item.tenderProject.selectApply ? item.tenderProject.selectApply.entrustEnterprise.name : ''
              } else {
                item.fileFrom = '新增'
                item.codeUser = item.tenderProject.customProjectCode
              }
            }
          })
        }
      })
    },
    onSearch () {
      this.getTableData()
    },
    filterTenderProjectType (row, column) {
      if (row.tenderProjectType === 1) {
        return '工程'
      } else if (row.tenderProjectType === 2) {
        return '货物'
      } else if (row.tenderProjectType === 3) {
        return '服务'
      }
    },
    filterBiddingType (row, column) {
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
    /** 分页 */
    handleSizeChange (val) {
      var likeThis = this
      var pageSize = `${val}`
      this.pageNo = 0
      this.currentPage = 1
      this.pageSize = parseInt(pageSize)
      this.$nextTick(() =>
        this.getTableData(this.pageNo, this.pageSize, function (resp) {
          likeThis.total = resp.data.archivesList.list.total
        })
      )
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getTableData(parseInt(this.pageNo), parseInt(this.pageSize))
    },
    /** 表格操作-查看 */
    handleTableClick (row, type) {
      this.$router.push({path: '/admin/files/filemanage/editfile', query: {type: false, operation: 'look', bidType: row.biddingType, code: row.code}})
    }
  },
  /** 实例创建完成 */
  mounted () {
  }
}
</script>
<style lang="less">
</style>
