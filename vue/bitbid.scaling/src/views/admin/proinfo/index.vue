<template>
  <div class="project-list" id="project-list">
    <div class="add-btn">
      <el-button type="warning" @click="handleClick(null, 'add')">新增</el-button>
    </div>
    <el-table
      :data="tableData"
      border
      style="width: 100%">
      <el-table-column
        prop="projectCode"
        label="项目编号">
      </el-table-column>
      <el-table-column
        prop="projectName"
        label="项目名称"
        width="180">
      </el-table-column>
      <el-table-column
        prop="sectionName"
        label="包组名称">
      </el-table-column>
      <el-table-column
        prop="sectionCode"
        label="包组编号">
      </el-table-column>
      <el-table-column
        prop="sectionStr"
        label="标识码">
      </el-table-column>
      <el-table-column
        label="操作">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row, 'edit')" type="text" size="small" v-if="scope.row.projectStatus !== 1">修改</el-button>
          <el-button @click="handleClick(scope.row, 'look')" type="text" size="small">查看</el-button>
          <el-button @click="handleClick(scope.row, 'scal')" type="text" size="small">定标</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      class="pagebox"
      @current-change="handleCurrentChange"
      :current-page.sync="currentPage"
      :total="total"
      :page-size='pageSize'
      layout="prev, pager, next, jumper">
    </el-pagination>
  </div>
</template>
<script>
import {projectInfo} from '@/api'
export default {
  name: 'project-list',
  components: {
  },
  data () {
    return {
      currentUser: this.$store.getters.authUser,
      projectId: this.$store.getters.projectId,
      visibleBIdding: false, // 定标-提示
      tableData: [],
      // 当前页
      currentPage: 1,
      pageNo: 1,
      total: 0, // 总条数
      pageSize: 10 // 每页展示条数
    }
  },
  created () {},
  methods: {
    // 分页
    handleCurrentChange (nowNum) {
      this.pageNo = nowNum
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this.list()
    },
    list () {
      let params = {
        pageNo: this.pageNo,
        pageSize: 10,
        creator: this.currentUser.userId
      }
      projectInfo.queryList(params).then((res) => {
        if (res.data.resCode === '0000') {
          this.tableData = res.data.ProjectList
          this.total = res.data.total
        }
      })
    },
    handleClick (row, type) {
      if (type === 'add') {
        this.$router.push({path: '/admin/proinfo/update/add'})
      } else if (type === 'edit') {
        this.$router.push({path: '/admin/proinfo/update/edit', query: {objectId: row.objectId}})
      } else if (type === 'look') {
        this.$router.push({path: '/admin/proinfo/detail', query: {projectId: row.objectId}})
      } else if (type === 'scal') {
        this.$confirm('您确定要去定标吗?定标后无法修改信息，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.updateBidStatus(row)
          this.$store.commit('SET_PROJECT_ID', row.objectId)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消定标'
          })
        })
      }
    },
    // 更改定标状态
    updateBidStatus (row) {
      projectInfo.updateBidStatus(row.objectId).then(res => {
        this.$store.commit('SET_PROJECT_ID', row.objectId)
        this.list()
      })
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="scss">
#project-list {
  padding: 10px;
  margin-top: 15px;
  background: #fff;
  .add-btn {
    padding-bottom: 10px;
    text-align: left;
  }
}
</style>
