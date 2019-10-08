<template>
  <div id="looksuppmissionindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让公告</el-breadcrumb-item>
        <el-breadcrumb-item>查看补充公告</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--添加按钮-->
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
        <!--添加按钮-->
        <!--主要内容 table-->
        <el-table
          :data="projecttableData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="noticeName"
            label=""
            align="center"
            width="50">
            <template slot-scope="scope">
              <i class="morebtn el-icon-arrow-right" @click="btnOpen($event)"></i>
            </template>
          </el-table-column>
          <el-table-column
            prop="suppleAnnoNumber"
            label="补充公告名称"
            align="left"
            width="350"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="sectionNumber"
            label="项目编号"
            align="left"
            width="200"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span class="jfbdbox" v-for="(item,index) in scope.row.refSuppAnnoSections" :key="index">
                <span class="xmbh_box" :title="item.section.sectionNumber">{{item.section.sectionNumber}}</span>
              </span>
            </template>
          </el-table-column>
          <el-table-column
            prop="sectionName"
            label="项目名称"
            align="left"
            width="350"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span class="jfbdbox" v-for="(item,index) in scope.row.refSuppAnnoSections" :key="index">
                <span class="xmbh_box" :title="item.section.sectionName">{{item.section.sectionName}}</span>
              </span>
            </template>
          </el-table-column>
          <el-table-column
            prop="landUse"
            label="出让方式"
            align="left"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.landUse === 1">
                <span class="statusbox">拍卖</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.landUse === 2">
                <span class="statusbox">挂牌</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.landUse === 3">
                <span class="statusbox">其他</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="approvrResult"
            label="公告状态"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 0">
                <span class="statusbox">未提交</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <span class="statusbox">备案审批中</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
                <span class="statusbox">审批通过</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 3">
                <span class="statusbox">审批不通过</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 4">
                <span class="statusbox">已发布</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="操作" width="140" align="center">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="lookBtn(scope)">
                查看
              </el-button>
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
import $ from 'jquery'
import {anno} from '@/api'
export default {
  data () {
    return {
      seacher_input: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '补充公告名称',
      options: [{
        value: 1,
        label: '补充公告名称'
      }, {
        value: 2,
        label: '项目名称'
      }, {
        value: 3,
        label: '项目编号'
      }],
      value: 1,
      projecttableData: [],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 0, // 总条数
      pageSize: 10, // 每页展示条数
      searchIs: false
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
          isSupplement: 1,
          selectStatus: 0
        }
        if (this.seacherName == '补充公告名称') {
          url.supAnnoNumbers = this.seacher_input
        } else if (this.seacherName == '项目名称') {
          url.sectionName = this.seacher_input
        } else if (this.seacherName == '项目编号') {
          url.sectionNumber = this.seacher_input
        }
      } else {
        url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0,
          isSupplement: 1,
          selectStatus: 0
        }
      }
      anno.announcementSuupList(url).then((res) => {
        this.projecttableData = res.data.announcementPageInfo.list
        this.total = res.data.announcementPageInfo.total
      })
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      let searchs = null
      searchs = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0,
        isSupplement: 1,
        selectStatus: 0
      }
      if (this.seacherName == '补充公告名称') {
        searchs.supAnnoNumbers = this.seacher_input
      } else if (this.seacherName == '项目名称') {
        searchs.sectionName = this.seacher_input
      } else if (this.seacherName == '项目编号') {
        searchs.sectionNumber = this.seacher_input
      }
      anno.announcementSuupList(searchs).then((res) => {
        this.projecttableData = res.data.announcementPageInfo.list
        this.total = res.data.announcementPageInfo.total
        this.searchIs = true
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
        this.seacherName = '补充公告名称'
      } else if (val === 2) {
        this.seacherName = '项目名称'
      } else {
        this.seacherName = '项目编号'
      }
    },
    // 展开隐藏信息
    btnOpen (event) {
      if ($(event.currentTarget).hasClass('el-icon-arrow-down')) {
        $(event.currentTarget).removeClass('el-icon-arrow-down').addClass('el-icon-arrow-right')
        $(event.currentTarget).parents('tr.el-table__row').find('.cell').css('height', '30px')
      } else {
        $(event.currentTarget).parents('tr.el-table__row').find('.cell').css('height', 'auto')
        $(event.currentTarget).removeClass('el-icon-arrow-right').addClass('el-icon-arrow-down')
      }
    },
    // 查看
    lookBtn (scope) {
      this.$router.push({path: `/mr/transferor/submission/supplement-notice/detail/${scope.row.objectId}`, query: {ObjectId: scope.row.objectId, landUse: scope.row.landUse, roledsType: 1}})
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
  #looksuppmissionindexbox{
    .select{
      width: 20%;
    }
    .search{
      width: 79%;
    }
    .el-table .cell {
      height: 30px;
    }
    .cellopen{
      box-sizing: border-box;
      word-break: break-all;
      line-height: 23px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .el-tooltip{
      padding: 0 20px;
    }
    .xmbh_box{
      display: block;
      margin-bottom: 10px;
      overflow: hidden;
      text-overflow:ellipsis;
      white-space: nowrap;
    }
    .el-icon-arrow-right{
      cursor: pointer;
    }
  }
</style>
