<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="tendrecord-page">
    <div v-if="this.$route.path == '/admin/record/processrecord'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>招标备案</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/record/processrecord' }">招标过程备案</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!-- 搜索区域start -->
      <div class="search-box">
        <el-form :model="searchForm" ref="searchForm" label-width="130px">
          <el-row :gutter="20">
            <el-col :span="6" style="width:300px">
              <el-form-item label="招标项目名称:">
                <el-input v-model="searchForm.projectNameLike" style="width: 180px;"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="width:300px">
              <el-form-item label="招标项目编号:">
                <el-input v-model="searchForm.codeUser" style="width: 180px;"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="width:255px">
              <el-form-item label="代理机构名称:">
                <el-input v-model="searchForm.entrustEnterpriseNameLike" style="width: 180px;"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="width:300px" :push="1">
              <el-form-item label="项目编号（代理）:">
                <el-input v-model="searchForm.agencyProjectCodeLike" style="width: 180px;"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="5" style="width:300px">
              <el-form-item label="招标方:">
                <el-input v-model="searchForm.enterpriseNameLike" style="width: 180px;"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5" style="width:300px">
              <el-form-item label="当前环节:">
                <el-select v-model="searchForm.progressStage" clearable placeholder="请选择环节" style="width:180px">
                  <el-option
                    v-for="item in linkList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="width:300px">
              <el-form-item label="当前状态:">
                <el-select v-model="searchForm.status" clearable placeholder="请选择状态" style="width:180px">
                  <el-option
                    v-for="item in statusList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="2" style="padding-left:30px;">
              <el-form-item>
                <el-button type="primary" @click="onSearch('searchForm')" icon="el-icon-search" style="margin-left:-100px;">搜索</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <!-- 搜索区域end -->

      <!-- 主体内容区域start -->
      <div class="maincont-box tend-box">
        <div
          class="tendrecord-btn"
          v-if="this.$store.getters.authUser.enterpriseType === 4 &&
          $store.getters.permissions.includes('/record/processrecord/operation')">
          <el-button
            type="warning"
            @click="registerClickBtn"
            icon="el-icon-plus"
             >招标项目登记
          </el-button>
        </div>
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
            show-overflow-tooltip
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
            show-overflow-tooltip
            prop="agencyName"
            label="代理机构">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            show-overflow-tooltip
            prop="enterpriseName"
            label="招标方">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="noticeProgressList"
            label="公告名称"
            width="260">
            <template slot-scope="scope">
              <p v-for="(item, index) in scope.row.noticeProgressList" :key="index" class="dresult-span">{{item.noticeName}}</p>
            </template>
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="noticeProgressList"
            label="当前环节">
            <template slot-scope="scope">
              <p v-for="(item, index) in scope.row.noticeProgressList" :key="index" class="dresult-span">{{item.progressStage | filterProgressStage}}</p>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            prop="noticeProgressList"
            label="当前状态">
            <template slot-scope="scope">
              <p v-for="(item, index) in scope.row.noticeProgressList" :key="index" class="dresult-span">{{item.progressStage | filterStatus}}</p>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
            width="100">
            <template slot-scope="scope">
              <p v-if="scope.row.noticeProgressList !== 0 && $store.getters.permissions.includes('/record/processrecord/operation')" v-for="(item, index) in scope.row.noticeProgressList" :key="index">
                <el-button @click="handleTableClick(scope.row, item,'goin')" type="text" size="medium" class="dresult-span">进入
                </el-button>
              </p>
              <p v-if="scope.row.noticeProgressList.length === 0 && $store.getters.permissions.includes('/record/processrecord/operation')">
               <el-button @click="handleTableClick(scope.row, undefined,'goin')" type="text" size="medium" class="dresult-span">进入
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
import {tenderproject} from 'api/index'
export default {
  data () {
    return {
      searchForm: {}, // 搜索条件表单
      linkList: [ // 当前环节list
        {
          label: '公告阶段',
          value: 1
        },
        {
          label: '资格预审文件阶段',
          value: 2
        },
        {
          label: '资格预审会阶段',
          value: 3
        },
        {
          label: '招标文件阶段',
          value: 4
        },
        {
          label: '开评标阶段',
          value: 5
        },
        {
          label: '候选人公示阶段',
          value: 6
        },
        {
          label: '结果公示阶段',
          value: 7
        },
        {
          label: '完结',
          value: 8
        }
      ],
      statusList: [ // 状态list
        {
          label: '进行中',
          value: '进行中'
        },
        {
          label: '完成',
          value: '完成'
        }
      ],
      tableData: [], // 表格数据
      pageNo: 0,
      currentPage: 1,
      pageSize: 10,
      total: null, // 总条数
      pageSizeList: [10, 20, 30, 40, 50],
      statuses: []
    }
  },
  computed: {},
  watch: {
    '$route': 'getAllTenderProjectInfo'
  },
  created () {
    this.getAllTenderProjectInfo()
  },
  filters: {
    filterProgressStage (progressStage) {
      if (progressStage === 1) {
        return '公告阶段'
      } else if (progressStage === 2) {
        return '资格预审文件阶段'
      } else if (progressStage === 3) {
        return '资格预审会阶段'
      } else if (progressStage === 4) {
        return '招标文件阶段'
      } else if (progressStage === 5) {
        return '开评标阶段'
      } else if (progressStage === 6) {
        return '候选人公示阶段'
      } else if (progressStage === 7) {
        return '结果公示阶段'
      } else if (progressStage === 8) {
        return '完成'
      }
    },
    filterStatus (progressStage) {
      if (progressStage === 8) {
        return '完成'
      } else {
        return '进行中'
      }
    }
  },
  /** 方法集 */
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 获取全部的招标项目信息 */
    getAllTenderProjectInfo () {
      tenderproject.getList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        biddingType: 1,
        historySourceType: 1,
        projectNameLike: this.searchForm.projectNameLike || null,
        codeUser: this.searchForm.codeUser || null,
        entrustEnterpriseNameLike: this.searchForm.entrustEnterpriseNameLike || null,
        enterpriseNameLike: this.searchForm.enterpriseNameLike || null,
        projectEnterpriseName: (this.$store.getters.authUser.enterpriseType === 2 || this.$store.getters.authUser.enterpriseType === 3) ? this.$store.getters.authUser.enterpriseName : '',
        creator: (this.$store.getters.authUser.enterpriseType === 4) ? this.$store.getters.authUser.userId : '',
        progressStage: this.searchForm.progressStage || null,
        statusList: this.statuses || '',
        agencyProjectCodeLike: this.searchForm.agencyProjectCodeLike || null
        /* statusList: ((this.searchForm.status !== undefined && this.searchForm.status === '进行中') ? '1, 2, 3, 4, 5, 6, 7' : '8') || '' */
      }).then((res) => {
        if (res.data.tenderProjectList && res.data.tenderProjectList.list) {
          this.tableData = res.data.tenderProjectList.list
          this.total = res.data.tenderProjectList.total
          this.tableData.map(item => {
            item.name = item.selectApply.projectName
            item.codeUser = item.selectApply.codeUser
            item.agencyName = item.selectApply.entrustEnterprise.name
            item.enterpriseName = item.selectApply.enterpriseName
          })
        }
      })
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
    /** 搜索 */
    onSearch () {
      this.statuses = ''
      if (this.searchForm.status) {
        if (this.searchForm.status === '进行中') {
          this.statuses = '1, 2, 3, 4, 5, 6, 7'
        } else {
          this.statuses = '8'
        }
      }
      this.getAllTenderProjectInfo()
    },
    /** 招标项目登记 */
    registerClickBtn () {
      this.$router.push({path: '/admin/record/processrecord/proregister'})
    },
    /** 表格操作 */
    handleTableClick (tenderProject, notice, type) {
      // 跳转
      if (notice) {
        this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: tenderProject.code, noticeCode: notice.noticeCode}})
      } else {
        this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: tenderProject.code}})
      }
    }
  },
  /** 实例创建完成 */
  mounted () {
  }
}
</script>
<style lang="less">
#tendrecord-page {
  .maincont-box {
    .tendrecord-btn {
      text-align: left;
      padding-bottom: 20px;
    }
    /** 表格样式 */
    .table-box tr{
      td:nth-child(6),
      td:nth-child(7),
      td:nth-child(8),
      td:nth-child(9) {
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
      td:nth-child(9) {
        p > button {
          padding-top: 17px;
          padding-bottom: 0;
        }
        p button {
          padding-top: 0;
        }
      }
    }
  }
  .block-page {
    padding: 20px;
  }
}
</style>
