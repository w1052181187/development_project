<template>
  <div class="cloudcontent" id="cloud_webhome">
    <div class="main">
      <div class="topmain">
        <!--按钮-->
        <div class="btnbigbox">
            <el-button class="addbutton" @click="addBtn">
              <span> + 添加合作单位</span>
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
          prop="tenderProjectCode"
          label="标题"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="tenderProjectCode"
          label="URL地址"
          :formatter="transition"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="tenderProjectName"
          label="发布时间"
          width="200"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="status"
          label="状态"
          width="120"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <div class="statusbigbox" v-if="scope.row.status === 0">
              <span class="statusbox">启用</span>
            </div>
            <div class="statusbigbox" v-if="scope.row.status === 1">
              <span class="statusbox">禁用</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="200">
          <template slot-scope="scope">
            <el-button type="text" size="small" v-if="scope.row.status === 0">禁用</el-button>
            <el-button type="text" size="small" v-if="scope.row.status === 1">启用</el-button>
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
      <el-dialog
        title="图片"
        :visible.sync="imgVisible"
        :before-close="handleClose"
        width="50%">
        <img :src="imgUrl" class="dialog_img"/>
      </el-dialog>
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
          tenderProjectCode: '259874563',
          tenderProjectName: '王小虎',
          status: 0
        },
        {
          tenderProjectCode: '259874563',
          tenderProjectName: '王小虎',
          status: 1
        },
        {
          tenderProjectCode: '259874563',
          tenderProjectName: '王小虎',
          status: 0
        },
        {
          tenderProjectCode: '259874563',
          tenderProjectName: '王小虎',
          status: 1
        },
        {
          tenderProjectCode: '259874563',
          tenderProjectName: '王小虎',
          status: 0
        }
      ],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 100, // 总条数
      pageSize: 10, // 每页展示条数
      dataIndex: 1,
      imgVisible: false,
      imgUrl: ''
    }
  },
  methods: {
    // 添加
    addBtn () {
      this.$router.push({path: '/websiteManage/home/cooperator/add'})
    },
    // 修改
    updateBtn (scope) {
      this.$router.push({path: `/websiteManage/home/cooperator/update`, query: {objectId: scope.row.objectId}})
    },
    // 查看
    lookBtn () {
      this.imgVisible = true
      this.imgUrl = ''
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
  #cloud_webhome {
  }
</style>
