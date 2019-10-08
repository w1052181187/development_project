<template>
  <div id="land_pro_patchReg" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让邀请</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/remise-invite/look-invite?roledsType=1' }">查看出让邀请确认情况</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: `/lt/transferor/remise-invite/look-invite/look-confirm/${this.$route.params.objectId}`, query: {roledsType: 1, showStatus: false}}">查看确认情况</el-breadcrumb-item>
        <el-breadcrumb-item>受让人确认情况</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--主要内容 table-->
        <el-table
          :data="projectData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="sectionNumber"
            label="地块编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="assigneeName"
            label="受让人名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="contacts"
            label="联系人"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="contactsNumber"
            label="联系电话"
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
            prop="isConfirm"
            label="状态"
            align="left"
            width="240">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.isConfirm === 0">
                <span class="statusbox gray"><i></i>未确认</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.isConfirm === 1">
                <span class="statusbox blue"><i></i>已确认</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.isConfirm === 2">
                <span class="statusbox green"><i></i>已拒绝</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="操作" width="180" align="center">
            <template slot-scope="scope">
              <div class="statusbigbox">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <!--主要内容 table-->
        <!--分页-->
        <el-pagination
          class="pagebox"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :total="total"
          :page-size='pageSize'
          layout="prev, pager, next, jumper">
        </el-pagination>
        <!--分页-->
      </template>
    </div>
  </div>
</template>
<script>
import {landProject} from '@/api'
export default {
  data () {
    return {
      // 列表表格
      projectData: [],
      currentPage: 1, // 当前页
      pageNo: 0, // 页数
      total: 10, // 总条数
      pageSize: 10 // 每页展示条数
    }
  },
  methods: {
    // ***************************************************************数据接口方法**********************************************************************
    // -----------------------------------------列表-----------------------------------------
    list () {
      landProject.queryInfosByAnnoId(this.$route.params.objectId).then((res) => {
        console.log(res)
        this.projectData = res.data.landInformationPageInfo.list
      })
    },
    // -----------------------------------------分页方法-----------------------------------------
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    // -----------------------------------------查看----------------------------------------------
    detailBtn (scope) {
      this.$router.push({path: `/lt/transferor/remise-invite/look-invite/assignee-situation/detail/${scope.row.objectId}`, query: {roledsType: 1, showStatus: false}})
    }
    // ***************************************************************自己方法**********************************************************************
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
