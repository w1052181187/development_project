<template>
  <div class="cloudcontent" id="cloud_registProject">
    <div class="main">
      <el-table
        :data="processTypeData"
        border
        style="width: 100%" header-cell-class-name="tableheader">
        <el-table-column
          type="index"
          label="序号"
          width="60"
          align="center">
        </el-table-column>
        <el-table-column
          prop="name"
          label="项目流程名称"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="200">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="lookBtn(scope)">查看</el-button>
            <el-button type="text" size="small" @click="updateBtn(scope)">流程管理</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
import {projectFlowInfo} from '../../../api/system/index'
export default {
  components: {
  },
  data () {
    return {
      processTypeData: []
    }
  },
  created () {
    this.getTableData()
  },
  methods: {
    /** 获取项目流程信息 */
    getTableData () {
      projectFlowInfo.getList().then((res) => {
        this.processTypeData = res.data.projectFlowInfoList
      })
    },
    // 查看
    lookBtn (scope) {
      this.$router.push({path: `/system/proProcessSet/detail/${scope.row.flowType}`, query: {flowType: scope.row.flowType}})
    },
    // 流程管理
    updateBtn (scope) {
      this.$router.push({path: `/system/proProcessSet/update`, query: {flowType: scope.row.flowType}})
    },
    // 删除
    delBtn () {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        // **
      }).catch(() => {
        return false
      })
    },
    // 分页
    handleCurrentChange () {}
  }
}
</script>
