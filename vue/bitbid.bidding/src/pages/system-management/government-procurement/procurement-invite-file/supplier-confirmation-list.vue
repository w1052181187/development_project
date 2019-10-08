<template>
  <div id="projectfileindexbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/procurement-invite-file' }">采购邀请备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/procurement-invite-file/check-confirm' + handleCheckList(this.$route.query.gongGaoId)}">查看确认情况</el-breadcrumb-item>
        <el-breadcrumb-item>供应商确认情况</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <el-table
          :data="projecttableData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="biaoDuanBianHao"
            label="包组编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="biaoDuanMingCheng"
            label="包组名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="nameZH"
            label="供应商名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="lianXiRen"
            label="联系人"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="phone"
            label="移动电话"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="mobile"
            label="固定电话"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="email"
            label="邮箱"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="queRenZhuangTai"
            label="状态"
            align="left"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.queRenZhuangTai === 1">
                <span class="statusbox ">已确认</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.queRenZhuangTai === 2">
                <span class="statusbox ">已拒绝</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="操作" width="80" align="center">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="detailBtn(scope)">
                查看
              </el-button>
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
import { pushData } from '@/api'
export default {
  data () {
    return {
      projecttableData: [],
      // 当前页
      currentPage: 1,
      pageNo: 1, // 第几页
      total: 0, // 总条数
      pageSize: 10, // 每页展示条数
      searchIs: false,
      gongGaoId: this.$route.query.gongGaoId,
      biaoDuanId: this.$route.params.objectId
    }
  },
  methods: {
    // 列表
    list () {
      let url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        biaoDuanId: this.$route.params.objectId
      }
      pushData.supplierList(url).then((res) => {
        this.projecttableData = JSON.parse(res.data.gongYsAffirmString)
        this.total = JSON.parse(res.data.total)
      })
    },
    handleCurrentChange (nowNum) {
      this.pageNo = nowNum
      this.list()
    },
    // 采购邀请书
    detailBtn (scope) {
      this.$router.push({path: `/system-management/government-procurement/procurement-invite-file/supplier-confirmation/detail/${scope.row.id}`, query: {gongGaoId: this.gongGaoId, biaoDuanId: this.biaoDuanId}})
    },
    handleCheckList (id) {
      return '/' + id
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
</style>
