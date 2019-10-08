<template>
  <div id="projectfileindexbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/pre-qualification-documents-file' }">资格预审文件备案</el-breadcrumb-item>
        <el-breadcrumb-item>查看下载情况</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--搜索-->
        <!--<el-row>-->
          <!--<div class="seacher_box">-->
            <!--<el-select v-model="value" class="select" @change="selectSeacher">-->
              <!--<el-option-->
                <!--v-for="item in options"-->
                <!--:key="item.value"-->
                <!--:label="item.label"-->
                <!--:value="item.value">-->
              <!--</el-option>-->
            <!--</el-select>-->
            <!--<el-input class="search" style="vertical-align: top"  :placeholder="'请输入' + seacherName" v-model="seacher_input">-->
              <!--<el-button  slot="append" icon="el-icon-search" type="" @click="search"></el-button>-->
            <!--</el-input>-->
          <!--</div>-->
        <!--</el-row>-->
        <!--搜索-->
        <el-table
          :data="projecttableData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="bianHao"
            label="包组编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="mingCheng"
            label="包组名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="type"
            label="文件类型"
            align="left"
            show-overflow-tooltip>
            <template  slot-scope="scope">
              <span v-if="scope.row.type === 2">
              资格预审文件
            </span>
            </template>
          </el-table-column>
          <el-table-column
            prop="fileName_local"
            label="文件名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="downloadTime"
            label="下载时间"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="nameZH"
            label="下载人"
            align="left"
            show-overflow-tooltip>
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
      seacher_input: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '文件名称',
      options: [{
        value: 1,
        label: '文件名称'
      }],
      value: 1,
      projecttableData: [],
      // 当前页
      currentPage: 1,
      pageNo: 1, // 第几页
      total: 0, // 总条数
      pageSize: 10, // 每页展示条数
      searchIs: false
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
      if (this.searchIs) {
      }
      pushData.ysFileDownload(url).then((res) => {
        this.projecttableData = JSON.parse(res.data.ysFileDownloadString)
        this.total = JSON.parse(res.data.total)
      })
    },
    search () {
      this.currentPage = 1
      this.pageNo = 1
      this.searchIs = true
      this.list()
    },
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '文件名称'
      }
    },
    handleCurrentChange (nowNum) {
      this.pageNo = nowNum
      this.list()
    },
    // 采购邀请书
    detailBtn (scope) {
      this.$router.push({path: `/system-management/government-procurement/procurement-invite-file/check-confirm/purchase-invitation/detail/${scope.row.objectId}`, query: {type: scope.row.transferType}})
    },
    // 供应商确认情况
    supplierConfirmation (scope) {
      this.$router.push({path: `/system-management/government-procurement/procurement-invite-file/check-confirm/supplier-confirmation`})
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
