<template>
  <div id="ad-manage">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>信息管理</el-breadcrumb-item>
        <el-breadcrumb-item>下载中心</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <!--搜索 & 添加按钮-->
      <el-row>
        <el-button type="success"  class="addbutton" @click="addbutton"> + 添加下载</el-button>
        <div class="seacher_box">
          <span>标题：</span>
          <el-input class="search" style="vertical-align: top"  placeholder="请输入标题" v-model="search_input"></el-input>
          <el-button slot="append" icon="el-icon-search" type="" @click="search"></el-button>
        </div>
      </el-row>
      <!--搜索 & 添加按钮-->
      <el-table
        :data="tableData"
        border
        header-cell-class-name="tabletitles">
        <el-table-column
          type="index"
          width="50"
          label="序号"
          :index='dataIndex'
          align="center">
        </el-table-column>
        <el-table-column
          prop="title"
          label="标题"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="type"
          label="下载类型"
          align="left"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <div class="statusbigbox">
              <span v-if="scope.row.type === 1">协会</span>
              <span v-if="scope.row.type === 2">专家库</span>
              <span v-if="scope.row.type === 3">交易平台</span>
              <span v-if="scope.row.type === 4">其他</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="createDate"
          label="上传时间"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="downloads"
          label="下载量"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="240"
          align="center">
          <template slot-scope="scope">
            <div class="statusbigbox">
              <el-button type="text" size="small" @click="handleTableBtn(scope.row, 'review')">
                查看
              </el-button>
              <el-button type="text" size="small" @click="handleTableBtn(scope.row, 'edit')">
                修改
              </el-button>
              <el-button type="text" size="small" @click="handleTableBtn(scope.row, 'del')">
                删除
              </el-button>
            </div>
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
  </div>
</template>
<script>
import {download} from '@/api'
export default {
  data () {
    return {
      search_input: '',
      dataIndex: 1,
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 0, // 总条数
      pageSize: 10, // 每页展示条数
      tableData: []
    }
  },
  methods: {
    list () {
      let params = {
        pageNo: this.pageNo,
        pageSize: 10,
        isDelete: 0,
        messageLike: this.search_input === '' ? null : this.search_input
      }
      download.queryList(params).then((res) => {
        this.tableData = res.data.downloadInfoPageInfo.list
        this.total = res.data.downloadInfoPageInfo.total
      })
    },
    // 搜索
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.dataIndex = 1
      this.list()
    },
    /** 新增 */
    addbutton () {
      this.$router.push({path: '/index/download/add'})
    },
    /** 表格操作按钮 */
    handleTableBtn (row, type) {
      if (Object.is(type, 'del')) {
        this.delete(row)
      } else if (Object.is(type, 'edit')) {
        this.$router.push({path: '/index/download/update', query: {objectId: row.objectId}})
      } else if (Object.is(type, 'review')) {
        this.$router.push({path: '/index/download/detail', query: {objectId: row.objectId}})
      }
    },
    /** 删除 */
    delete (row) {
      this.$confirm('确定要删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 执行删除
        download.remove(row.objectId).then((res) => {
          this.list()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 分页
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this.list()
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  #ad-manage {
  }
</style>
