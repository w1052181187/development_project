<template>
  <div>
    <div id="zyfl" class="content_bigbox">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item><router-link :to="{ path: '/index' }">首页</router-link></el-breadcrumb-item>
        <el-breadcrumb-item class="active_bread"><router-link to="">资源分类设置</router-link></el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <el-table
        header-cell-class-name="header"
        :data='dataTable'
        style="width: 100%"
        border>
        <el-table-column
          type="index"
          label="序号"
          width="50"
          :index='dataIndex' align="center">
        </el-table-column>
        <el-table-column
          prop='moduleName'
          label="业务模块">
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          width="120">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="seeNext(scope)" v-if="$store.getters.permissions.includes('/owner-set/detail') || (scope.row.fileStatus == 0)">
              查看
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分页-->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="inAll"
        :page-size="pageSize"
        @current-change="handleCurrentChange"
        style="text-align:center;padding:50px 0">
      </el-pagination>
      <!--分页-->
    </div>
  </div>
</template>
<script>
export default{
  data () {
    return {
      dataTable: [],
      dataIndex: 1,
      pageSize: 10,
      inAll: 0
    }
  },
  methods: {
    getData () {
      this.axios.get(`modules?pageNo=${this.dataIndex - 1}&pageSize=${this.pageSize}`).then((res) => {
        this.dataTable = res.data.moduleInformationInfo.list
        this.inAll = res.data.moduleInformationInfo.total
      })
    },
    handleCurrentChange (currentpage) {
      this.getData(currentpage)
      this.dataIndex = (currentpage - 1) * this.pageSize + 1
    },
    seeNext (scope) {
      this.$router.push({path: '/owner-set/resource-set', query: {moduleType: scope.row.moduleType, nav: scope.row.moduleName}})
    }
  },
  mounted () {
    this.getData()
  }
}
</script>
<style lang='less'>
#zyfl{
    .header{
      background: #f5f7f8;
    }
    .breadcrumb_box{
        width: 100%;
        height: 60px;
        border-bottom: 1px solid #eeeeee;
        padding: 0 20px;
        box-sizing: border-box;
        line-height: 60px;
    }
    .el-breadcrumb__inner{
        color: #999999!important;
    }
    .active_bread>.el-breadcrumb__inner{
        color:#333333!important;
    }
    .da_xz_ckbigbox{
        border: none;
        line-height:60px;
        padding:0 0 0 20px;
        .el-form-item{padding: 0px}
        .el-dialog__header{padding-top: 0;padding-bottom: 0px}
        .el-dialog__body{padding-bottom: 0px}
    }
    .content_bigbox{margin: 0px auto;width: 1200px;background: #fff}
}
</style>
