<template>
  <div id="indexbox" class="smaincontent" >
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>矿权交易备案</el-breadcrumb-item>
        <el-breadcrumb-item>出让文件备案</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--搜索-->
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
        <!--搜索-->
        <el-table
          :data="tranDocTableData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="announcement.annoNumber"
            label="公告名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="fileInformations.fileName"
            label="出让文件"
            align="left">
          </el-table-column>
          <el-table-column
            prop="publishedTime"
            label="发布时间"
            align="left">
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" width="140" align="center">
            <template slot-scope="scope">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          class="pagebox"
          @size-change="handleSizeChange"
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
import { stdocuments } from '@/api'
export default {
  data () {
    return {
      seacher_input: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '公告名称',
      options: [{
        value: 1,
        label: '公告名称'
      }, {
        value: 2,
        label: '文件名称'
      }],
      value: 1,
      selectCondition: 1,
      tranDocTableData: [],
      // 当前页
      currentPage: 1,
      total: 0, // 总条数
      pageNo: 0,
      pageSize: 10, // 每页展示条数
      searchIs: false
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
        this.seacherName = '文件名称'
        this.selectCondition = 2
      }
    },
    // 列表显示
    queryList () {
      let url = null
      if (this.searchIs) {
        url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0,
          status: 2,
          _t: new Date().getTime()
        }
        if (this.selectCondition === 1) {
          url.annoName = this.seacher_input
        } else if (this.selectCondition === 2) {
          url.fileName = this.seacher_input
        }
      } else {
        url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0,
          status: 2,
          _t: new Date().getTime()
        }
      }
      stdocuments.queryList(url).then((res) => {
        this.tranDocTableData = res.data.transferDocumentsList.list
        this.tranDocTableData.map((ite) => {
          if (ite.announcement.releaseTime === null) {
            ite.announcement.releaseTime = '-------------'
          }
        })
        this.total = res.data.transferDocumentsList.total
      })
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      let searchs = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        status: 2,
        isDelete: 0
      }
      if (this.selectCondition === 1) {
        searchs.annoName = this.seacher_input
      } else if (this.selectCondition === 2) {
        searchs.fileName = this.seacher_input
      }
      stdocuments.queryList(searchs).then((res) => {
        this.tranDocTableData = res.data.transferDocumentsList.list
        this.tranDocTableData.map((ite) => {
          if (ite.announcement.releaseTime === null) {
            ite.announcement.releaseTime = '-------------'
          }
        })
        this.total = res.data.transferDocumentsList.total
      })
      this.currentPage = 1
      this.pageNo = (this.currentPage - 1) * this.pageSize
      this.searchIs = true
    },
    // 查看
    detailBtn (scope) {
      this.$router.push({path: `/system-management/mineral-rights/transfer-documents-file/detail/${scope.row.objectId}`, query: {name: scope.row.Sellmethods}})
    }
  },
  mounted () {
    this.queryList()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
</style>
