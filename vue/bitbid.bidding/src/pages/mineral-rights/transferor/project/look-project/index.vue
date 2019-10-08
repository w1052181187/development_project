<template>
  <div id="indexbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>项目管理</el-breadcrumb-item>
        <el-breadcrumb-item>查看项目</el-breadcrumb-item>
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
              <el-button  slot="append" icon="el-icon-search" type="" @click="search" ></el-button>
            </el-input>
          </div>
        </el-row>
        <!--搜索-->
        <!--主要内容 table-->
        <el-table
          :data="projecttableData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="sectionNumber"
            label="项目编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="sectionName"
            label="项目名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="transferType"
            label="出让类型"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.transferType === 1">
                <span class="statusbox">探矿权</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.transferType === 2">
                <span class="statusbox">采矿权</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="landUse"
            label="出让方式"
            align="left">
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
            prop="status"
            label="项目状态"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === '0'">
                <span class="statusbox gray"><i></i>未提交</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === '1'">
                <span class="statusbox blue"><i></i>备案审批中</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === '2'">
                <span class="statusbox green"><i></i>审批通过</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === '3'">
                <span class="statusbox red"><i></i>审批不通过</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" width="120" align="center">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="detailBtn(scope)">
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
import { prProject } from '@/api'
export default {
  data () {
    return {
      seacher_input: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '项目名称',
      options: [{
        value: 1,
        label: '项目名称'
      }, {
        value: 2,
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
          selectStatus: 0,
          isDelete: 0
        }
        if (this.seacherName == '项目名称') {
          url.sectionNames = this.seacher_input
        } else if (this.seacherName == '项目编号') {
          url.sectionNumbers = this.seacher_input
        }
      } else {
        url = {
          isDelete: 0,
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          selectStatus: 0
        }
      }
      prProject.queryList(url).then((res) => {
        this.projecttableData = res.data.sectionPageInfo.list
        this.total = res.data.sectionPageInfo.total
      })
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      let searchs = null
      searchs = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        selectStatus: 0,
        isDelete: 0
      }
      if (this.seacherName == '项目名称') {
        searchs.sectionNames = this.seacher_input
      } else if (this.seacherName == '项目编号') {
        searchs.sectionNumbers = this.seacher_input
      }
      prProject.queryList(searchs).then((res) => {
        this.projecttableData = res.data.sectionPageInfo.list
        this.total = res.data.sectionPageInfo.total
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
        this.seacherName = '项目名称'
      } else {
        this.seacherName = '项目编号'
      }
    },
    // 查看
    detailBtn (scope) {
      this.$router.push({path: `/mr/transferor/project/look-project/detail/${scope.row.objectId}`, query: {type: scope.row.transferType, roledsType: 1}})
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
