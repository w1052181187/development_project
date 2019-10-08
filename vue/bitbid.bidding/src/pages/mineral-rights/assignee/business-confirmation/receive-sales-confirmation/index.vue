<template>
  <div id="receiveindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>成交确认</el-breadcrumb-item>
        <el-breadcrumb-item>接收成交确认书</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--搜索按钮-->
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
              <el-button  slot="append" icon="el-icon-search" @click="search"></el-button>
            </el-input>
          </div>
        </el-row>
        <!--搜索按钮-->
        <!--主要内容 table-->
        <el-table
          :data="projecttableData"
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
            prop="isSend"
            label="发送状态"
            align="left"
            width="200"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.isAffirm === 0">
                <span class="statusbox">待确认</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.isAffirm === 1">
                <span class="statusbox">已确认</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="lastUpdateDate"
            label="提交时间"
            align="left"
            width="200"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            label="操作" width="160" align="center">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.isAffirm === 0">
                <el-button type="text" size="small" @click="confirmBtn(scope)">
                  确认
                </el-button>
                <el-button type="text" size="small" @click="lookBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.isAffirm === 1">
                <el-button type="text" size="small" @click="lookBtn(scope)">
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
import {salesCon} from '@/api'
export default {
  data () {
    return {
      seacher_input: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '项目名称',
      options: [ {
        value: 1,
        label: '项目名称'
      }, {
        value: 2,
        label: '项目编号'
      }],
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      value: 1,
      searchIs: false,
      projecttableData: []
    }
  },
  methods: {
    list () {
      let url = null
      if (this.searchIs) {
        url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0,
          isPersonAffirm: 1,
          isSend: 1,
          bidderName: this.$store.getters.authUser.userName
        }
        if (this.seacherName == '项目名称') {
          url.sectionName = this.seacher_input
        } else if (this.seacherName == '项目编号') {
          url.sectionNumber = this.seacher_input
        }
      } else {
        url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0,
          isPersonAffirm: 1,
          isSend: 1,
          bidderName: this.$store.getters.authUser.userName
        }
      }
      salesCon.confirmList(url).then((res) => {
        this.projecttableData = res.data.salesConfirmationPageInfo.list
        this.total = res.data.salesConfirmationPageInfo.total
      })
    },
    search () {
      let searchs = null
      searchs = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0,
        isPersonAffirm: 1,
        isSend: 1,
        bidderName: this.$store.getters.authUser.userName
      }
      if (this.seacherName == '项目名称') {
        searchs.sectionName = this.seacher_input
      } else if (this.seacherName == '项目编号') {
        searchs.sectionNumber = this.seacher_input
      }
      salesCon.confirmList(searchs).then((res) => {
        this.projecttableData = res.data.salesConfirmationPageInfo.list
        this.total = res.data.salesConfirmationPageInfo.total
      })
    },
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list(this.searchIs)
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list(this.searchIs)
    },
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '项目名称'
      } else {
        this.seacherName = '项目编号'
      }
    },
    lookBtn (scope) {
      this.$router.push({path: `/mr/assignee/business-confirmation/receive-sales-confirmation/detail/${scope.row.objectId}`, query: {roledsType: 2}})
    },
    confirmBtn (scope) {
      this.$router.push({path: `/mr/assignee/business-confirmation/receive-sales-confirmation/confirm/${scope.row.objectId}`, query: {roledsType: 2}})
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
