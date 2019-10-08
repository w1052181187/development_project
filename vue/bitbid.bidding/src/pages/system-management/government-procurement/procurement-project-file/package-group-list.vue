<template>
  <div id="projectfileindexbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/procurement-project-file' }">采购项目备案</el-breadcrumb-item>
        <el-breadcrumb-item>包组信息</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--搜索-->
        <!--<el-row >-->
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
            prop="biaoDuanBianHao"
            label="包组编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="biaoDuanMingCheng"
            label="包组名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="sectionStatus"
            label="包组状态"
            align="left">
            <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.sectionStatus === 0">
                  <span class="statusbox">未进行</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.sectionStatus === 1">
                  <span class="statusbox">已进行</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.sectionStatus === 3">
                  <span class="statusbox">重新进行</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.sectionStatus === 2">
                  <span class="statusbox">已结束</span>
                </div>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" width="160" align="center">
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
      seacherName: '包组名称',
      options: [{
        value: 1,
        label: '包组名称'
      }, {
        value: 2,
        label: '包组编号'
      }],
      selectData: 1,
      projecttableData: [],
      // 当前页
      currentPage: 1,
      pageNo: 1, // 第几页
      total: 0, // 总条数
      pageSize: 10, // 每页展示条数
      searchIs: false,
      projectId: this.$route.query.fabaoId
    }
  },
  methods: {
    // 列表
    list () {
      let url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        fabaoId: this.projectId
      }
      if (this.searchIs) {
        if (this.selectData === 1) {
          url.name = this.seacher_input
        } else if (this.selectData === 2) {
          url.code = this.seacher_input
        }
      }
      pushData.sectionList(url).then((res) => {
        this.projecttableData = JSON.parse(res.data.sectionString)
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
        // this.seacherName = '采购项目名称'
        this.selectData = 1
      } else {
        // this.seacherName = '采购项目编号'
        this.selectData = 2
      }
    },
    handleCurrentChange (nowNum) {
      this.pageNo = nowNum
      this.list()
    },
    // 查看
    detailBtn (scope) {
      this.$router.push({path: `/system-management/government-procurement/procurement-project-file/package-group/detail/${scope.row.id}`, query: {faBaoId: this.projectId}})
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
