<template>
    <div id="enrolmentindex"  class="maincontent">
      <div class="headertitle">
        <!--面包屑-->
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/mr/assignee/online-registration/online-registration?roledsType=2' }">网上报名</el-breadcrumb-item>
          <el-breadcrumb-item>查看资格确认书</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
      </div>
      <div class="contentbigbox">
        <template>
          <!--项目名称  项目编号搜索-->
          <el-row>
            <div class="seacher_box">
              <el-select v-model="value" class="select" @change="selectSeacher">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
              <el-input class="search" style="vertical-align: top"  :placeholder="'请输入' + seacherName" v-model="seacher_input">
                <el-button  slot="append" icon="el-icon-search" type="" @click="search"></el-button>
              </el-input>
            </div>
          </el-row>
          <!--项目名称  项目编号搜索-->
          <!--主要内容 table-->
          <el-table
            :data="registrationtableData"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="section.sectionNumber"
              label="项目编号"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="section.sectionName"
              label="项目名称"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="section.announcement.applyStartTime"
              label="报名开始时间"
              align="left"
              width="200">
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作" width="140" align="center">
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
import {quaCon} from '@/api'
export default {
  data () {
    return {
      seacher_input: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '项目名称',
      options: [{
        value: 1,
        label: '项目名称'
      }, {
        value: 2,
        label: '项目编号'
      }],
      value: 1,
      registrationtableData: [],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 0, // 总条数
      pageSize: 10 // 每页展示条数
    }
  },
  methods: {
    list () {
      let url = null
      url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0,
        bidder: this.$store.getters.authUser.userId,
        isSend: 1
      }
      quaCon.queryList(url).then((res) => {
        this.registrationtableData = res.data.qualiConfirmationsInfo.list
        this.total = res.data.qualiConfirmationsInfo.total
      })
    },
    search () {
      this.currentPage = 1
      this.dataIndex = 1
      this.pageNo = 0
      let searchs = null
      searchs = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        _t: new Date().getTime(),
        isDelete: 0,
        assigneeId: this.$store.getters.authUser.userId
      }
      if (this.seacherName == '项目名称') {
        searchs.sectionName = this.seacher_input
      } else if (this.seacherName == '项目编号') {
        searchs.sectionNumber = this.seacher_input
      }
      quaCon.applyList(searchs).then((res) => {
        this.registrationtableData = res.data.qualiConfirmationsInfo.list
        this.total = res.data.qualiConfirmationsInfo.total
      })
    },
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '项目名称'
      } else {
        this.seacherName = '项目编号'
      }
    },
    // 查看
    detailBtn (scope) {
      this.$router.push({path: '/mr/assignee/online-registration/look-confirmation/detail', query: {ObjectId: scope.row.section.objectId, Bidder: scope.row.assigneeId, roledsType: 2}})
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
</style>
