<template>
  <div class="cloudcontent" id="cloud_registProject">
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item class="active_bread">代理合同管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!--面包屑-->
    <div class="main">
      <div class="topmain">
        <!--按钮-->
        <div class="btnbigbox">
          <!--添加角色-->
          <div class="addrole">
            <el-button class="addbutton" @click="addBtn">
              <span> + 添加</span>
            </el-button>
          </div>
          <!--添加角色-->
        </div>
        <!--按钮-->
        <div class="seacher_box">
          <span>代理合同名称：</span>
          <el-input class="search" placeholder="请输入代理合同名称关键字" style="vertical-align: top"  v-model="messageLike" clearable>
            <el-button  slot="append" icon="el-icon-search" type="" @click="searchBtn"></el-button>
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
          :index='dataIndex'
          align="center">
        </el-table-column>
        <el-table-column
          prop="agencyContractCode"
          label="代理合同编号"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="agencyContractName"
          label="代理合同名称"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="entrustEnterpriseName"
          label="委托单位"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="status"
          label="提交状态"
          width="100"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <div class="statusbigbox" v-if="scope.row.status === 1">
              <span class="statusbox">已保存</span>
            </div>
            <div class="statusbigbox" v-if="scope.row.status === 2">
              <span class="statusbox">已提交</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="submitTime"
          label="提交时间"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="200">
          <template slot-scope="scope">
            <div v-if="scope.row.status === 1">
              <el-button type="text" size="small" @click="lookBtn(scope)">查看</el-button>
              <el-button type="text" size="small" @click="updateBtn(scope)">编辑</el-button>
              <el-button type="text" size="small" @click="delBtn(scope)">删除</el-button>
            </div>
            <div v-if="scope.row.status === 2">
              <el-button type="text" size="small" @click="lookBtn(scope)">查看</el-button>
            </div>
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
import {agencyContract} from '@/api/cloudplatform'
export default {
  components: {
  },
  data () {
    return {
      messageLike: '',
      registProjectData: [],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: null, // 总条数
      pageSize: 10, // 每页展示条数
      dataIndex: 1
    }
  },
  watch: {
    $route: 'resetView'
  },
  methods: {
    resetView () {
      this.getAgencyContractList(this.pageNo)
    },
    // 搜索
    searchBtn () {
      this.getAgencyContractList(0)
    },
    // 添加
    addBtn (scope) {
      this.$router.push({path: `/processManage/agencyContract/add`})
    },
    // 查看
    lookBtn (scope) {
      this.$router.push({path: `/processManage/agencyContract/detail/${scope.row.code}`})
    },
    // 修改
    updateBtn (scope) {
      this.$router.push({path: `/processManage/agencyContract/update/${scope.row.code}`})
    },
    // 删除
    delBtn (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        agencyContract.delete(scope.row.objectId).then(() => {
          this.getAgencyContractList(this.pageNo)
        })
      }).catch(() => {
        return false
      })
    },
    // 分页
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getAgencyContractList(this.pageNo)
    },
    // 代理合同列表查询
    getAgencyContractList (pageNo) {
      let query = {
        pageNo: pageNo,
        pageSize: this.pageSize,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        messageLike: this.messageLike || null
      }
      agencyContract.getList(query).then(res => {
        let data = res.data.agencyContractList
        if (data) {
          this.registProjectData = data.list
          this.total = data.total
        }
      })
    }
  },
  mounted () {
    this.getAgencyContractList(this.pageNo)
  }
}
</script>
