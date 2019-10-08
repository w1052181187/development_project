<template>
  <div class="cloudcontent" id="cloud_registProject">
    <div class="main">
      <div class="topmain">
        <!--按钮-->
        <div class="btnbigbox">
          <!--添加角色-->
          <div class="addrole">
            <el-button class="addbutton" @click="addBtn">
              <span> + 新增标段</span>
            </el-button>
          </div>
          <!--添加角色-->
        </div>
        <!--按钮-->
        <div class="seacher_box">
          <span>标段名称：</span>
          <el-input class="search" placeholder="请输入标段名称关键字" style="vertical-align: top"  v-model="searchForm.bidSectionNameLike">
            <el-button  slot="append" icon="el-icon-search" type="" @click="search"></el-button>
          </el-input>
        </div>
      </div>
      <el-table
        :data="registProjectData"
        border
        style="width: 100%" header-cell-class-name="tableheader">
        <el-table-column
          type="index"
          label="序号"
          width="60"
          :index='indexMethod'
          align="center">
        </el-table-column>
        <el-table-column
          prop="tenderProjectName"
          label="项目名称"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="bidSectionCode"
          label="标段编号"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="bidSectionName"
          label="标段名称"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="flowStatus"
          label="标段状态"
          :formatter="filterFlowStatus"
          show-overflow-tooltip>
         <!-- <template slot-scope="scope">
            <div class="statusbigbox" v-if="scope.row.status === 0">
              <span class="statusbox">未开始</span>
            </div>
            <div class="statusbigbox" v-if="scope.row.status === 1">
              <span class="statusbox">资格预审公告</span>
            </div>
            <div class="statusbigbox" v-if="scope.row.status === 2">
              <span class="statusbox">开标评标</span>
            </div>
            <div class="statusbigbox" v-if="scope.row.status === 3">
              <span class="statusbox">定标结果</span>
            </div>
            <div class="statusbigbox" v-if="scope.row.status === 4">
              <span class="statusbox">已结束</span>
            </div>
          </template>-->
        </el-table-column>
        <el-table-column
          prop="createDate"
          label="创建时间"
          width="160"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="200">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="detailBtn(scope)">查看</el-button>
            <el-button type="text" size="small" @click="processtBtn(scope)" v-if="scope.row.flowStatus !== 1 && scope.row.flowStatus !== 2 && scope.row.flowStatus !== 4 && scope.row.flowStatus !== 18">流程管理</el-button>
            <el-button type="text" size="small" @click="updateBtn(scope)" v-if="scope.row.flowStatus === 1 || scope.row.flowStatus === 4 || scope.row.flowStatus === 18">编辑</el-button>
            <el-button type="text" size="small" @click="delBtn(scope)" v-if="scope.row.flowStatus === 1">删除</el-button>
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
        @current-change="handleCurrentChange"
        @next-click="handleCurrentNext">
      </el-pagination>
      <!--分页-->
    </div>
  </div>
</template>
<script>
import {bidSection} from '@/api/project'
export default {
  components: {
  },
  data () {
    return {
      searchForm: {},
      registProjectData: [],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 100, // 总条数
      pageSize: 10, // 每页展示条数
      dataIndex: 1,
      tenderSystemCode: ''
    }
  },
  watch: {
    '$route': 'init'
  },
  created () {
    this.init()
  },
  methods: {
    init () {
      this.tenderSystemCode = this.$route.query.code
      this.getTableData()
    },
    // 新增
    addBtn () {
      this.$router.push({path: `/project/projectProcess/project/add`, query: {code: this.tenderSystemCode}})
    },
    // 流程管理
    processtBtn (scope) {
      sessionStorage.setItem('isOverview', this.$store.getters.permissions.includes('1010402')) // 根据正常流程展示操作按钮
      this.$router.push({path: `/project/projectProcess/project/process/${scope.row.code}`, query: {code: this.tenderSystemCode}})
      this.clearCurProjectSession(scope.row.code)
    },
    clearCurProjectSession (code) {
      let progressTemp = sessionStorage.getItem(this.tenderSystemCode)
      if (progressTemp) {
        let curProject = JSON.parse(progressTemp)
        if (curProject.hasOwnProperty(code)) {
          delete curProject[code]
          sessionStorage.setItem(this.tenderSystemCode, JSON.stringify(curProject))
        }
      }
    },
    // 修改
    updateBtn (scope) {
      this.$router.push({path: `/project/projectProcess/project/update`, query: {code: scope.row.code}})
    },
    // 查看
    detailBtn (scope) {
      this.$router.push({path: `/project/projectProcess/project/detail/${scope.row.code}`})
    },
    // 删除
    delBtn (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        bidSection.delete(scope.row.objectId).then(() => {
          this.getTableData()
        })
      }).catch(() => {
        return false
      })
    },
    /** 流程状态:1.已保存；2.待审批；3.已审批；4.已驳回；5.已提交（不需要审批）；6.招标公告; 7.资格预审公告; 8.投标邀请书;  9.资格预审文件; 10.响应资格预审; 11.资格审查结果; 12.招标文件; 13.响应招标; 14.开标评标; 15.废标结果公告; 16.定标结果; 17.完结；18.已撤回 */
    filterFlowStatus (row) {
      if (row.flowStatus === 1) {
        return '已保存'
      } else if (row.flowStatus === 2) {
        return '待审批'
      } else if (row.flowStatus === 3) {
        return '已审批'
      } else if (row.flowStatus === 4) {
        return '已驳回'
      } else if (row.flowStatus === 5) {
        return '已提交'
      } else if (row.flowStatus === 6) {
        return '招标公告'
      } else if (row.flowStatus === 7) {
        return '资格预审公告'
      } else if (row.flowStatus === 8) {
        return '投标邀请书'
      } else if (row.flowStatus === 9) {
        return '资格预审文件'
      } else if (row.flowStatus === 10) {
        return '响应资格预审'
      } else if (row.flowStatus === 11) {
        return '资格审查结果'
      } else if (row.flowStatus === 12) {
        return '招标文件'
      } else if (row.flowStatus === 13) {
        return '响应招标'
      } else if (row.flowStatus === 14) {
        return '开标评标'
      } else if (row.flowStatus === 15) {
        return '废标结果公告'
      } else if (row.flowStatus === 16) {
        return '定标结果'
      } else if (row.flowStatus === 17) {
        return '已结束'
      } else if (row.flowStatus === 18) {
        return '已撤回'
      }
    },
    /** 获取列表数据 */
    getTableData () {
      bidSection.getList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        tenderSystemCode: this.tenderSystemCode,
        messageLike: this.searchForm.bidSectionNameLike || null
      }).then((res) => {
        if (res.data.bidSectionList && res.data.bidSectionList.list) {
          this.registProjectData = res.data.bidSectionList.list
          this.total = res.data.bidSectionList.total
        }
      })
    },
    /** 模糊查询方法 */
    search () {
      this.getTableData()
    },
    // 表单分页
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getTableData(parseInt(this.pageNo), parseInt(this.pageSize))
    },
    handleCurrentNext (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getTableData(parseInt(this.pageNo), parseInt(this.pageSize))
    }
  }
}
</script>
<style>
</style>
