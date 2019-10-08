<template>
  <div class="cloudcontent" id="cloud_notice">
    <div class="main">
      <div class="topmain">
        <!--按钮-->
        <div class="btnbigbox">
          <el-button class="addbutton" @click="addBtn">
            <span> + 添加结果公示</span>
          </el-button>
        </div>
        <!--按钮-->
        <div class="seacher_box searchselect_box">
          <span>招标方式：</span>
          <el-select v-model="value" class="select">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-input class="search" placeholder="请输入公示名称" style="vertical-align: top"  v-model="search">
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
          label="公示名称"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="tenderProjectCode"
          label="项目名称"
          :formatter="transition"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="tenderProjectName"
          label="招标方式"
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
              <span class="statusbox">已发布</span>
            </div>
            <div class="statusbigbox" v-if="scope.row.status === 1">
              <span class="statusbox">未发布</span>
            </div>
            <div class="statusbigbox" v-if="scope.row.status === 3">
              <span class="statusbox">已撤回</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="200">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="updateBtn(scope)" v-if="scope.row.status !== 0">修改</el-button>
            <el-button type="text" size="small" @click="lookBtn(scope)">查看</el-button>
            <el-button type="text" size="small" v-if="scope.row.status === 0">撤回</el-button>
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
          objectId: '7777',
          tenderProjectCode: '259874563',
          tenderProjectName: '王小虎',
          status: 0
        },
        {
          objectId: '7777',
          tenderProjectCode: '259874563',
          tenderProjectName: '王小虎',
          status: 1
        },
        {
          objectId: '7777',
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
          objectId: '7777',
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
      options: [{
        value: 1,
        label: '公开招标'
      }, {
        value: 2,
        label: '询价'
      }, {
        value: 3,
        label: '邀请招标'
      }, {
        value: 4,
        label: '竞争性谈判'
      }, {
        value: 5,
        label: '竞争性磋商'
      }, {
        value: 6,
        label: '单一来源'
      }, {
        value: 7,
        label: '全部'
      }],
      value: 1
    }
  },
  methods: {
    // 添加
    addBtn () {
      this.$router.push({path: '/websiteManage/notice/public/add'})
    },
    // 修改
    updateBtn (scope) {
      this.$router.push({path: `/websiteManage/notice/public/update`, query: {objectId: scope.row.objectId}})
    },
    // 查看
    lookBtn (scope) {
      this.$router.push({path: `/websiteManage/notice/public/detail/${scope.row.objectId}`})
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
  #cloud_notice {
  }
</style>
