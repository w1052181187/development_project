<template>
  <div id="selectionAnnounindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>拍卖/挂牌文件</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/auction-listed-file/sub-auc-listed?roledsType=1' }">提交出让文件</el-breadcrumb-item>
        <el-breadcrumb-item>选择出让公告</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox ">
      <template>
        <!--搜索按钮-->
        <el-row>
          <div class="seacher_box">
            <el-select v-model="searchValue" class="select" @change="selectSeacher">
              <el-option
                v-for="item in searchData"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-input class="search" style="vertical-align: top"  :placeholder="'请输入' + seacherName" v-model="seacherInput">
              <el-button  slot="append" icon="el-icon-search"  @click="search"></el-button>
            </el-input>
          </div>
        </el-row>
        <!--搜索按钮-->
        <!--主要内容 table-->
        <el-table
          ref="multipleTable"
          :data="projectData"
          tooltip-effect="dark"
          style="width: 100%"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            align="center"
            width="50">
            <template slot-scope="scope">
              <i class="morebtn el-icon-arrow-right" @click="btnOpen($event)"></i>
            </template>
          </el-table-column>
          <el-table-column
            prop="annoNumber"
            label="公告名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="sectionNumber"
            label="地块编号"
            align="left">
            <template slot-scope="scope">
              <span class="jfbdbox" v-for="(item,index) in scope.row.landInformations" :key="index">
                <span class="xmbh_box" :title="item.sectionNumber">{{item.sectionNumber}}</span>
              </span>
            </template>
          </el-table-column>
          <el-table-column
            prop="methodOfSupply"
            label="供应方式"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox">
                <span class="statusbox">招拍挂出让</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="methodOfTransfer"
            label="出让方式"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.type === 1">
                <span class="statusbox">拍卖</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.type === 2">
                <span class="statusbox">挂牌</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="操作" width="140" align="center">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="addBtn(scope)">
                添加文件
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--主要内容 table-->
      </template>
    </div>
  </div>
</template>
<script>
import $ from 'jquery'
import { landAnno, tdocuments } from '@/api'
export default {
  data () {
    return {
      seacherInput: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '地块编号', // 输入框默认值
      multipleSelection: [], // 选择后表格数据
      searchData: [{
        value: 0,
        label: '公告名称'
      }, {
        value: 1,
        label: '地块编号'
      }],
      pageSize: 100,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      searchValue: 1,
      // 项目表格数据
      projectData: []
    }
  },
  methods: {
    // ***************************************************************数据接口方法**********************************************************************
    // ----------------------------------------------------列表--------------------------------------
    list () {
      let url = {}
      url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0,
        isSupplement: 0,
        status: 4
      }
      // 查询出让公告是挂牌及拍卖
      url.types = '1, 2'
      if (this.seacherInput !== '') { // 当搜索内容不为空时执行
        if (this.seacherName === '公告名称') {
          url.annoName = this.seacherInput
        } else if (this.seacherName === '地块编号') {
          url.sectionNumber = this.seacherInput
        }
      }
      landAnno.queryList(url).then((res) => {
        this.projectData = res.data.landAnnoPageInfo.list
        this.total = res.data.landAnnoPageInfo.total
      })
    },
    // ----------------------------------------------------搜索--------------------------------------
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.list()
    },
    // ----------------------------------------------------分页--------------------------------------
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    // ----------------------------------------------------添加--------------------------------------
    addBtn (scope) {
      let url = {
        annoId: scope.row.objectId,
        isChecked: 0,
        type: 2
      }
      tdocuments.selectStatus(url).then((res) => {
        this.addStatus = res.data.status
        if (this.addStatus) {
          this.$message({
            type: 'warning',
            message: '没有要添加出让文件的标段'
          })
          return false
        } else {
          this.$router.push({path: '/lt/transferor/auction-listed-file/sub-auc-listed/add', query: {objectId: scope.row.objectId, roledsType: 1, showStatus: false}})
        }
      })
    },
    // ***************************************************************自己方法**********************************************************************
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '地块编号'
      } else {
        this.seacherName = '公告名称'
      }
    },
    // 展开隐藏信息
    btnOpen (event) {
      if ($(event.currentTarget).hasClass('el-icon-arrow-down')) {
        $(event.currentTarget).removeClass('el-icon-arrow-down').addClass('el-icon-arrow-right')
        $(event.currentTarget).parents('tr.el-table__row').find('.cell').css('height', '25px')
      } else {
        $(event.currentTarget).parents('tr.el-table__row').find('.cell').css('height', 'auto')
        $(event.currentTarget).removeClass('el-icon-arrow-right').addClass('el-icon-arrow-down')
      }
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
  #selectionAnnounindexbox{
    .el-table .cell {
      height: 25px;
    }
    .cellopen{
      box-sizing: border-box;
      word-break: break-all;
      line-height: 23px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }
</style>
