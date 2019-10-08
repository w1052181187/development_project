<template>
  <div id="lookresultsindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>成交确认</el-breadcrumb-item>
        <el-breadcrumb-item>查看成交结果公示</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--添加按钮-->
        <el-row>
          <div class="seacher_box">
            <el-select v-model="selectCondition" class="select" @change="selectSeacher">
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
          :data="tranResTableData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="publicityName"
            label="公示名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="sectionNumber"
            label="项目编号"
            align="left"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span class="jfbdbox" v-for="(item,index) in scope.row.refTransResuList" :key="index">
                <span class="xmbh_box">{{item.section.sectionNumber}}</span>
              </span>
            </template>
          </el-table-column>
          <el-table-column
            prop="sectionName"
            label="项目名称"
            align="left"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span class="jfbdbox" v-for="(item,index) in scope.row.refTransResuList" :key="index">
                <span class="xmbh_box">{{item.section.sectionName}}</span>
              </span>
            </template>
          </el-table-column>
          <el-table-column
            prop="createDate"
            label="提交时间"
            align="left"
            width="200"
            show-overflow-tooltip>
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
import {transResAnno} from '@/api'
export default {
  data () {
    return {
      seacher_input: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '公告名称',
      options: [{
        value: 1,
        label: '公示名称'
      }, {
        value: 2,
        label: '项目名称'
      }, {
        value: 3,
        label: '项目编号'
      }],
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      selectCondition: 1,
      searchIs: false,
      tranResTableData: []
    }
  },
  methods: {
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.queryList(this.searchIs)
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.queryList(this.searchIs)
    },
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '公告名称'
        this.selectCondition = 1
      } else if (val === 2) {
        this.seacherName = '项目名称'
        this.selectCondition = 2
      } else {
        this.seacherName = '项目编号'
        this.selectCondition = 3
      }
    },
    // 查看
    lookBtn (scope) {
      this.$router.push({path: `/mr/assignee/business-confirmation/look-results-transaction/detail/${scope.row.objectId}`, query: {roledsType: 2}})
    },
    // 列表显示
    queryList () {
      let url = null
      if (this.searchIs) {
        url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0,
          status: 4,
          _t: new Date().getTime()
        }
        if (this.selectCondition === 1) {
          url.publicityNameLike = this.seacher_input
        } else if (this.selectCondition === 2) {
          url.sectionName = this.seacher_input
        } else if (this.selectCondition === 3) {
          url.sectionNumber = this.seacher_input
        }
      } else {
        url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0,
          status: 4,
          _t: new Date().getTime()
        }
      }
      transResAnno.queryList(url).then((res) => {
        this.tranResTableData = res.data.transResultsAnnoList.list
        this.total = res.data.transResultsAnnoList.total
      })
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      let searchs = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0,
        status: 4,
        _t: new Date().getTime()
      }
      if (this.selectCondition === 1) {
        searchs.publicityNameLike = this.seacher_input
      } else if (this.selectCondition === 2) {
        searchs.sectionName = this.seacher_input
      } else if (this.selectCondition === 3) {
        searchs.sectionNumber = this.seacher_input
      }
      transResAnno.queryList(searchs).then((res) => {
        this.tranResTableData = res.data.transResultsAnnoList.list
        this.total = res.data.transResultsAnnoList.total
      })
      this.currentPage = 1
      this.pageNo = (this.currentPage - 1) * this.pageSize
      this.searchIs = true
    }
  },
  mounted () {
    this.queryList()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
</style>
