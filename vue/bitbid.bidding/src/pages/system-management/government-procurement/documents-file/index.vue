<template>
  <div id="documentfilelist" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item>文件公示备案</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--搜索-->
        <!--<el-row>-->
          <!--<div class="seacher_box">-->
            <!--<el-select v-model="selectData" class="select" @change="selectSeacher">-->
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
            prop="faBaoMingCheng"
            label="采购项目编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="publicityName"
            label="公示名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="purchaseMethod"
            label="采购方式"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.purchaseMethod === 1">
                <span class="statusbox">公开招标</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.purchaseMethod === 2">
                <span class="statusbox">邀请招标</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.purchaseMethod === 3">
                <span class="statusbox">竞争性谈判</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.purchaseMethod === 4">
                <span class="statusbox">单一采购来源</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.purchaseMethod === 5">
                <span class="statusbox">询价采购</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.purchaseMethod === 6">
                <span class="statusbox">竞争性磋商</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="lastUpdateDate"
            label="发布时间"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="creator"
            label="提交人"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="status"
            label="文件状态"
            align="left"
            width="100">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <span class="statusbox ">已发布</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" width="100" align="center">
            <template slot-scope="scope">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
            </template>
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
      seacherName: '采购项目编号',
      options: [{
        value: 1,
        label: '公示名称'
      }],
      selectData: 1,
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
        pageSize: this.pageSize
      }
      if (this.searchIs) {
        if (this.selectData === 1) {
          url.name = this.seacher_input
        }
      }
      pushData.filePublicityList(url).then((res) => {
        this.projecttableData = JSON.parse(res.data.filePublicityString)
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
        this.seacherName = '公示名称'
        this.selectData = 1
      }
    },
    handleCurrentChange (nowNum) {
      this.pageNo = nowNum
      this.list()
    },
    // 查看
    detailBtn (scope) {
      this.$router.push({path: `/system-management/government-procurement/documents-file/detail/${scope.row.id}`, query: {type: scope.row.transferType}})
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
  #documentfilelist {
    .select {
      width: 30%;
    }
    .search {
      width: 69%;
    }
  }
</style>
