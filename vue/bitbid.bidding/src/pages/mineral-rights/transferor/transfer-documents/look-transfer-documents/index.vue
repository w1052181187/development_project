<template>
  <div id="looktranmissionindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让文件</el-breadcrumb-item>
        <el-breadcrumb-item>查看出让文件</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--搜索-->
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
        <!--搜索-->
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
            prop="fileInformations.fileName"
            label="出让文件"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="status"
            label="文件状态"
            width="150"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 0">
                <span class="statusbox">未提交</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <span class="statusbox">已提交</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
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
import { tdocuments } from '@/api'
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
    // 查看
    lookBtn (scope) {
      this.$router.push({path: `/mr/transferor/transfer-documents/sub-transfer-documents/detail/${scope.row.objectId}`, query: {name: scope.row.Sellmethods, roledsType: 1}})
    },
    // 列表显示
    queryList (search) {
      let url = null
      if (this.searchIs) {
        url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0,
          selectStatus: 0,
          _t: new Date().getTime()
        }
        if (this.selectCondition === 1) {
          url.annoName = this.seacher_input
        } else if (this.selectCondition === 2) {
          url.fileName = this.seacher_input
        }
      } else {
        url = {
          // creator: this.$store.getters.authUser.userId,
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0,
          selectStatus: 0,
          _t: new Date().getTime()
        }
      }
      tdocuments.queryList(url).then((res) => {
        this.tranDocTableData = res.data.transferDocumentsList.list
        this.total = res.data.transferDocumentsList.total
      })
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      let searchs = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0,
        selectStatus: 0,
        _t: new Date().getTime()
      }
      // 通过不同的查询进行参数赋值
      if (this.selectCondition === 1) {
        searchs.annoName = this.seacher_input
      } else if (this.selectCondition === 2) {
        searchs.fileName = this.seacher_input
      }
      tdocuments.queryList(searchs).then((res) => {
        this.tranDocTableData = res.data.transferDocumentsList.list
        this.total = res.data.transferDocumentsList.total
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
