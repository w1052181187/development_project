<template>
  <div id="ad-manage">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>首页管理</el-breadcrumb-item>
        <el-breadcrumb-item>广告管理</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <!--搜索 & 添加按钮-->
      <el-row>
        <el-button type="success"  class="addbutton" @click="addbutton"> + 添加广告</el-button>
        <div class="seacher_box">
          <span>标题:</span>
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
          prop="url"
          label="url地址"
          align="left"
          :formatter="transition"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="lastUpdateDate"
          label="发布时间"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="status"
          label="状态"
          align="left">
          <template slot-scope="scope">
            <div class="statusbigbox" v-if="scope.row.status === 1">
              <span class="statusbox">已启用</span>
            </div>
            <div class="statusbigbox" v-if="scope.row.status === 0">
              <span class="statusbox">已禁用</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="240"
          align="center">
          <template slot-scope="scope">
            <div class="statusbigbox">
              <el-button type="text" size="small" @click="handleTableBtn(scope.row, 'disable')" v-if="scope.row.status === 1">
                禁用
              </el-button>
              <el-button type="text" size="small" @click="handleTableBtn(scope.row, 'enable')" v-if="scope.row.status === 0">
                启用
              </el-button>
              <el-button type="text" size="small" @click="handleTableBtn(scope.row, 'detail')">
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
    <el-dialog
      title="图片"
      :visible.sync="imgVisible"
      :before-close="handleClose"
      width="50%">
      <img :src="imgUrl" class="dialog_img"/>
    </el-dialog>
  </div>
</template>
<script>
import { homeManage } from '@/api'
import { fileReviewUrl } from '@/assets/js/common'
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
      tableData: [],
      imgVisible: false,
      imgUrl: ''
    }
  },
  methods: {
    list () {
      let params = {
        pageNo: this.pageNo,
        pageSize: 10,
        isDelete: 0,
        type: 2,
        messageLike: this.search_input === '' ? null : this.search_input
      }
      homeManage.queryList(params).then((res) => {
        this.tableData = res.data.pictureInfoPageInfo.list
        this.total = res.data.pictureInfoPageInfo.total
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
      this.$router.push({path: '/index/admanage/add'})
    },
    /** 表格操作按钮 */
    handleTableBtn (row, type) {
      if (Object.is(type, 'del')) {
        this.delete(row)
      } else if (Object.is(type, 'disable') || Object.is(type, 'enable')) {
        this.isEnable(type, row)
      } else if (Object.is(type, 'edit')) {
        this.$router.push({path: '/index/admanage/update', query: {objectId: row.objectId}})
      } else if (Object.is(type, 'detail')) {
        this.imgVisible = true
        this.imgUrl = fileReviewUrl + row.fileInformations[0].relativePath
      }
    },
    handleClose () {
      this.imgVisible = false
    },
    /** 删除 */
    delete (row) {
      this.$confirm('确定要删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 执行删除
        homeManage.remove(row.objectId).then((res) => {
          this.list()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    /** 禁用/启用 */
    isEnable (type, row) {
      let statusV = (type === 'disable') ? '禁用' : '启用'
      let str = `确定要${statusV}该数据吗?`
      let message = `已取消${statusV}`
      this.$confirm(str, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 禁用
        if (type === 'disable') {
          homeManage.disable(row.objectId).then((res) => {
            this.list()
          })
        } else {
          homeManage.enabled(row.objectId, row.type).then((res) => {
            this.list()
          })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: message
        })
      })
    },
    // 分页
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this.list()
    },
    transition (val) {
      if (val.url) {
        return val.url
      } else {
        return '------'
      }
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
