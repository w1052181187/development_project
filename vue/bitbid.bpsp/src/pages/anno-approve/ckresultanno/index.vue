<template>
  <div id="ckresultanno" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>公告审核</el-breadcrumb-item>
        <el-breadcrumb-item>成交公示审核</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--搜索 & 添加按钮-->
        <el-row>
          <div class="seacher_box">
            <span>审核状态：</span>
            <el-select v-model="status" class="select">
              <el-option
                v-for="item in statusOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-input class="search" style="vertical-align: top"  placeholder="请输入公示名称" v-model="search_input"></el-input>
            <el-button  slot="append" icon="el-icon-search" type="" @click="search"></el-button>
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
            prop="tender_project_code"
            label="招标项目编号"
            :formatter="formatStr"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="title"
            label="公示名称"
            :formatter="formatStr"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="project_type"
            label="项目类型"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.project_type === 1">
                <span class="statusbox">工程</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.project_type === 2">
                <span class="statusbox">货物</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.project_type === 3">
                <span class="statusbox">服务</span>
              </div>
              <div class="statusbigbox" v-else>
                <span class="statusbox">---</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="enterprise_name"
            label="企业名称"
            :formatter="formatStr"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="account"
            label="用户名"
            :formatter="formatStr"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="publish_time"
            :formatter="formatDate"
            label="提交时间"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="status"
            label="状态"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <span class="statusbox">审核中</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
                <span class="statusbox">审核通过</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 3">
                <span class="statusbox">审核不通过</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 4">
                <span class="statusbox">已撤回</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" width="150" align="center">
            <template slot-scope="scope">
              <div class="statusbigbox">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
                <el-button type="text" size="small" v-if="scope.row.status === 1 || scope.row.status === 4" @click="checkBtn(scope)">
                  审核
                </el-button>
                <el-button type="text" size="small" v-if="scope.row.status === 2" @click="withdraw(scope)">
                  撤回
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
      </template>
    </div>
  </div>
</template>
<script>
import {purchase} from '@/api'
import {dateFormat} from '@/assets/js/common'
export default {
  data () {
    return {
      search_input: '',
      statusOptions: [{
        value: null,
        label: '全部'
      }, {
        value: 1,
        label: '审核中'
      }, {
        value: 2,
        label: '审核通过'
      }, {
        value: 3,
        label: '审核不通过'
      }, {
        value: 4,
        label: '已撤回'
      }],
      status: null,
      tableData: [],
      // 当前页
      currentPage: 1,
      pageNo: 1,
      total: 0, // 总条数
      pageSize: 10, // 每页展示条数
      dataIndex: 1
    }
  },
  methods: {
    list () {
      let params = {
        pageNo: this.pageNo,
        pageSize: 10,
        isDelete: 0,
        type: 2,
        infoType: 14,
        approveFlag: 1,
        status: this.status,
        orderBy: 'publish_time',
        titleLike: this.search_input === '' ? null : this.search_input
      }
      purchase.queryList(params).then((res) => {
        this.tableData = res.data.list
        this.total = res.data.totalNum
      })
    },
    // 转换UTC时间格式
    formatDate (row, col, cellValue) {
      return dateFormat(new Date(cellValue), 'yyyy-MM-dd hh:mm:ss')
    },
    // 格式化字符串，如果是''则为---
    formatStr (row, col, cellValue) {
      return cellValue || '---'
    },
    // 搜索
    search () {
      this.currentPage = 1
      this.pageNo = 1
      this.dataIndex = 1
      this.list()
    },
    // 审核
    checkBtn (scope) {
      this.$router.push({path: '/annoapprove/ckresultanno/check', query: {objectId: scope.row.object_id, status: this.status}})
    },
    // 查看
    detailBtn (scope) {
      this.$router.push({path: `/annoapprove/ckresultanno/detail/${scope.row.object_id}`, query: {status: this.status}})
    },
    // 撤回
    withdraw (scope) {
      this.$confirm('确定要撤回吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 执行撤回
        purchase.withdraw(scope.row.object_id, scope.row.code).then((res) => {
          this.list()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消撤回'
        })
      })
    },
    // 分页
    handleCurrentChange (nowNum) {
      this.pageNo = nowNum
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this.list()
    }
  },
  mounted () {
    this.status = this.$route.query.status ? this.$route.query.status : null
    this.list()
  }
}
</script>
<style lang="less">
  #ckresultanno{
    .select{
      width: 24%;
    }
    .search{
      width: 48%;
    }
    .el-form-item__label{
      width: 168px !important;
    }
    .el-form-item__content{
      margin-left: 168px !important;
      width: 60%;
    }
  }
</style>
