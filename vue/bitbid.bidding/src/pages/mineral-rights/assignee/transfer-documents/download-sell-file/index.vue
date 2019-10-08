<template>
  <div id="dowloadsellindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让文件</el-breadcrumb-item>
        <el-breadcrumb-item>下载出让文件</el-breadcrumb-item>
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
            prop="fileInformations.fileName"
            label="出让文件"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            label="操作"
            width="120"
            align="center">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="downloadBtn(scope)">
                下载
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
import {tdocuments} from '@/api'
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
        label: '项目名称'
      }, {
        value: 3,
        label: '项目编号'
      }],
      // 本页矿权类型
      MineralRights: this.$route.query.type,
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      selectCondition: 1,
      tranDocTableData: [],
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
        this.seacherName = '项目名称'
        this.selectCondition = 2
      } else {
        this.seacherName = '项目编号'
        this.selectCondition = 3
      }
    },
    downloadBtn (scope) {
      this.$router.push({path: '/mr/assignee/transfer-documents/download-sell-file/download', query: {transDocSectionid: scope.row.transDocSectionid, roledsType: 2}})
    },
    // 列表显示
    queryList () {
      let url = null
      if (this.searchIs) {
        url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0,
          annoTimeLimit: new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate() + ' ' + new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds(),
          status: 2
        }
        if (this.selectCondition === 1) {
          url.annoName = this.seacher_input
        } else if (this.selectCondition === 2) {
          url.sectionName = this.seacher_input
        } else if (this.selectCondition === 3) {
          url.sectionNumber = this.seacher_input
        }
      } else {
        url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          annoTimeLimit: new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate() + ' ' + new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds(),
          isDelete: 0,
          status: 2
        }
      }
      tdocuments.queryList(url).then((res) => {
        let sectionLists = []
        res.data.transferDocumentsList.list.forEach(function (item) {
          let sectionList = []
          sectionList = item.refTransDocuList.map(function (refTransDocu) {
            refTransDocu.announcement = item.announcement
            refTransDocu.fileInformations = item.fileInformations
            return refTransDocu
          })
          sectionList.forEach(function (item, index) {
            sectionLists.push(item)
          })
        })
        this.tranDocTableData = sectionLists
        this.total = res.data.transferDocumentsList.total
      })
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      let searchs = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        annoTimeLimit: new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate() + ' ' + new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds(),
        isDelete: 0,
        status: 2
      }
      if (this.selectCondition === 1) {
        searchs.annoName = this.seacher_input
      } else if (this.selectCondition === 2) {
        searchs.sectionName = this.seacher_input
      } else if (this.selectCondition === 3) {
        searchs.sectionNumber = this.seacher_input
      }
      tdocuments.queryList(searchs).then((res) => {
        let sectionLists = []
        res.data.transferDocumentsList.list.forEach(function (item) {
          let sectionList = []
          sectionList = item.refTransDocuList.map(function (refTransDocu) {
            refTransDocu.announcement = item.announcement
            refTransDocu.fileInformations = item.fileInformations
            return refTransDocu
          })
          sectionList.forEach(function (item, index) {
            sectionLists.push(item)
          })
        })
        this.tranDocTableData = sectionLists
        this.total = res.data.transferDocumentsList.total
      })
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
