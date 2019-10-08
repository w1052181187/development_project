<template>
  <div class="cloudcontent" id="cloud_processtype">
    <div class="box">
      <div class="topmain">
        投标邀请书
        <!--按钮-->
        <el-button  type="primary" class="addbutton" @click="handleAdd" v-if="operationFlag">
          <span> + 添加</span>
        </el-button>
        <!--按钮-->
      </div>
      <el-table
        :data="tableData"
        header-cell-class-name="tableheader"
        border>
        <el-table-column
          type="index"
          label="序号"
          :index='indexMethod'
          width="50">
        </el-table-column>
        <el-table-column
          prop="title"
          label="邀请公告名称"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="type"
          label="邀请书类型"
          width="160"
          :formatter="filterType"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="auditStatus"
          label="提交状态"
          width="120"
          :formatter="filterAuditStatus">
        </el-table-column>
        <el-table-column
          prop="submitterName"
          label="提交人"
          width="140">
        </el-table-column>
        <el-table-column
          prop="submitTime"
          label="提交时间"
          :formatter="formatDate"
          width="160">
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="380">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="handleDetail(scope)">查看</el-button>
            <el-button type="text" size="small" v-if="(scope.row.auditStatus === 0 || scope.row.auditStatus === 3 || scope.row.auditStatus === 5) && operationFlag" @click="handleEdit(scope)">编辑</el-button>
            <el-button type="text" size="small" v-if="scope.row.auditStatus === 0 && operationFlag" @click="handleDel(scope)">删除</el-button>
            <el-button type="text" size="small" v-if="(scope.row.auditStatus === 2 || scope.row.auditStatus === 4) && operationFlag && scope.$index === 0" @click="handleTender(scope)">投标人确认情况</el-button>
            <el-button type="text" size="small" v-if="(scope.row.auditStatus === 2 || scope.row.auditStatus === 4) && scope.row.type === 4 && changeBullinFlag" @click="addNoticeBtn(scope)">添加更正公告</el-button>
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
import {bulletinInfo} from '@/api/project/index'
import {dateFormat} from '@/assets/js/common'
export default {
  components: {
  },
  props: ['tenderSystemCode', 'sectionSystemCode', 'operationFlag', 'type'],
  data () {
    return {
      tableData: [],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 100, // 总条数
      pageSize: 10, // 每页展示条数
      changeBullinFlag: false // 判断是否允许添加更正公告
    }
  },
  methods: {
    // 格式化时间
    formatDate (row, col, cellValue) {
      return cellValue ? dateFormat(cellValue, 'yyyy-MM-dd') : ''
    },
    /** 公告信息：1.招标公告；2.资格预审公告；3.更正公告；4.投标邀请书；5.废标公告；6.中标候选人公示；7.中标公告 */
    filterType (row) {
      if (row.type === 3) {
        return '更正邀请书'
      } else {
        return '原始邀请书'
      }
    },
    /** 审批状态(0.未提交；1.待审批；2.已审批；3.已驳回；4.已提交（不需要审批）；5.已撤回)  */
    filterAuditStatus (row) {
      if (row.auditStatus === 0) {
        return '已保存'
      } else if (row.auditStatus === 1) {
        return '待审批'
      } else if (row.auditStatus === 2) {
        return '已审批'
      } else if (row.auditStatus === 3) {
        return '已驳回'
      } else if (row.auditStatus === 4) {
        return '已提交'
      } else if (row.auditStatus === 5) {
        return '已撤回'
      }
    },
    /**
     * 增加邀请公告
     */
    addNoticeBtn (scope) {
      this.$router.push({path: `/project/projectProcess/invite/add_notice`, query: {bulletinCode: scope.row.code, sectionSystemCode: this.sectionSystemCode, tenderSystemCode: this.tenderSystemCode}})
    },
    handleNoticeEdit (scope) {
      this.$router.push({path: `/project/projectProcess/invite/update_notice`, query: {code: scope.row.code, sectionSystemCode: this.sectionSystemCode, tenderSystemCode: this.tenderSystemCode}})
    },
    handleNoticeDetail (scope) {
      this.$router.push({path: `/project/projectProcess/invite/notice_detail/${scope.row.code}`, query: {sectionSystemCode: this.sectionSystemCode}})
    },
    // 编辑
    handleEdit (scope) {
      let type = scope.row.type
      switch (type) {
        case 3:
          this.handleNoticeEdit(scope)
          break
        case 4:
          this.$router.push({path: `/project/projectProcess/invite/update`, query: {code: scope.row.code, sectionSystemCode: this.sectionSystemCode, tenderSystemCode: this.tenderSystemCode}})
          break
      }
    },
    handleAdd () {
      this.$router.push({path: `/project/projectProcess/invite/add`, query: {tenderSystemCode: this.tenderSystemCode, sectionSystemCode: this.sectionSystemCode}})
    },
    handleDetail (scope) {
      let type = scope.row.type
      switch (type) {
        case 3:
          this.handleNoticeDetail(scope)
          break
        case 4:
          this.$router.push({path: `/project/projectProcess/invite/detail/${scope.row.code}`, query: {sectionSystemCode: this.sectionSystemCode}})
          break
      }
    },
    handleDel (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        // **
        bulletinInfo.delete(scope.row.objectId).then(() => {
          this.getTableData()
        })
      }).catch(() => {
        return false
      })
    },
    handleTender (scope) {
      this.$router.push({path: `/project/projectProcess/invite/tender_confirm`, query: {code: scope.row.code, sectionSystemCode: this.sectionSystemCode}})
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getTableData(parseInt(this.pageNo), parseInt(this.pageSize))
    },
    /** 获取列表数据 */
    getTableData () {
      bulletinInfo.getByRelaSection({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        tenderSystemCode: this.tenderSystemCode,
        sectionSystemCode: this.sectionSystemCode,
        typeList: '4,3'
      }).then((res) => {
        let infos = res.data.bulletinInfoList
        if (infos && infos.list) {
          this.tableData = infos.list
          this.total = infos.total
        }
        if (res.data.bidSection) {
          if (sessionStorage.getItem('isOverview') === 'true') { // 从项目管理中进入
            if (res.data.bidSection.flowStatus !== 15 && res.data.bidSection.flowStatus !== 17) {
              this.changeBullinFlag = true
            } else {
              this.changeBullinFlag = false
            }
          } else {
            this.changeBullinFlag = false
          }
        }
        this.$emit('checkFlowStatusFlag', this.type)
      })
    }
  },
  watch: {
    '$route': 'getTableData'
  },
  mounted () {
    this.getTableData()
  }
}
</script>
<style lang="less">
  #cloud_processtype{
  }
</style>
