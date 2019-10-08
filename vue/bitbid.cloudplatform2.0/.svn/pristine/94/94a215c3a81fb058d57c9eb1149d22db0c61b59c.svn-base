<template>
  <div class="cloudcontent" id="cloud_information">
    <div class="main">
      <div class="topmain">
        <!--按钮-->
        <div class="btnbigbox">
          <el-button class="addbutton" @click="addBtn">
            <span> + 添加综合信息</span>
          </el-button>
        </div>
        <!--按钮-->
        <div class="seacher_box">
          <span>标题：</span>
          <el-input class="search" placeholder="请输入标题" style="vertical-align: top"  v-model="search">
            <el-button  slot="append" icon="el-icon-search" type="" @click="search"></el-button>
          </el-input>
        </div>
      </div>
      <el-table
        :data="tableData"
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
          prop="tenderProjectName"
          label="信息名称"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="tenderProjectCode"
          label="信息类型"
          :formatter="transition"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <div class="statusbigbox" v-if="Number(scope.row.tenderProjectCode) === 1">
              <span class="statusbox">企业动态</span>
            </div>
            <div class="statusbigbox" v-if="Number(scope.row.tenderProjectCode) === 2">
              <span class="statusbox">通知公告</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="tenderProjectName"
          label="发布时间"
          width="160"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="200">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="updateBtn(scope)">修改</el-button>
            <el-button type="text" size="small" @click="lookBtn(scope)">查看</el-button>
            <el-button type="text" size="small" @click="delBtn(scope)">删除</el-button>
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
export default {
  components: {
  },
  data () {
    return {
      search: '',
      tableData: [
        {
          tenderProjectCode: '1',
          tenderProjectName: '王小虎',
          status: 0
        },
        {
          tenderProjectCode: '2',
          tenderProjectName: '王小虎',
          status: 1
        },
        {
          tenderProjectCode: '1',
          tenderProjectName: '王小虎',
          status: 0
        },
        {
          tenderProjectCode: '2',
          tenderProjectName: '王小虎',
          status: 1
        },
        {
          tenderProjectCode: '1',
          tenderProjectName: '王小虎',
          status: 0
        }
      ],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 100, // 总条数
      pageSize: 10, // 每页展示条数
      dataIndex: 1
    }
  },
  methods: {
    // 添加
    addBtn () {
      this.$router.push({path: '/websiteManage/information/add'})
    },
    // 修改
    updateBtn (scope) {
      this.$router.push({path: `/websiteManage/information/update`, query: {objectId: scope.row.objectId}})
    },
    // 查看
    lookBtn (scope) {
      this.$router.push({path: `/websiteManage/information/detail/${scope.row.objectId}`})
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
    handleCurrentChange () {},
    // 数据为空时显示 ---
    transition (val) {
      if (val.url) {
        return val.url
      } else {
        return '------'
      }
    }
  }
}
</script>
<style lang="less">
  #cloud_information {
  }
</style>
