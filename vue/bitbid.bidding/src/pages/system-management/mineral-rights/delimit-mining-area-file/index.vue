<template>
  <div id="indexbox">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>矿权交易备案</el-breadcrumb-item>
        <el-breadcrumb-item>规定矿区范围公示备案</el-breadcrumb-item>
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
              <el-button  slot="append" icon="el-icon-search" type="" @click="search"></el-button>
            </el-input>
          </div>
        </el-row>
        <!--搜索-->
        <el-table
          :data="projecttableData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="publicityName"
            label="公示名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="projectNumber"
            label="项目编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="projectName"
            label="项目名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="lastUpdateDate"
            label="提交时间"
            align="left">
          </el-table-column>
          <el-table-column
            prop="status"
            label="公示状态"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 4">
                <span class="statusbox gray"><i></i>已发布</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 3">
                <span class="statusbox red"><i></i>审批不通过</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
                <span class="statusbox green"><i></i>审批通过</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <span class="statusbox blue"><i></i>备案审批中</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 0">
                <span class="statusbox"><i></i>未提交</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" width="140" align="center">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <el-button type="text" size="small" @click="checkBtn(scope)">
                  审批
                </el-button>
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 3">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 4">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
              </div>
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
import { sotherProject } from '@/api'
export default {
  data () {
    return {
      seacher_input: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '公示名称',
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
      value: 1,
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      searchIs: false,
      projecttableData: []
    }
  },
  methods: {
    list () {
      let params = null
      if (this.searchIs) {
        params = {
          selectStatus: 0,
          isDelete: 0,
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          type: 3
        }
        if (this.seacherName === '项目名称') {
          params.projectNames = this.seacher_input
        } else if (this.seacherName === '项目编号') {
          params.projectNumbers = this.seacher_input
        } else if (this.seacherName === '公示名称') {
          params.publicityNames = this.seacher_input
        }
      } else {
        params = {
          selectStatus: 0,
          isDelete: 0,
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          type: 3
        }
      }
      sotherProject.queryList(params).then((res) => {
        this.projecttableData = res.data.otherPublicityPageInfo.list
        this.total = res.data.otherPublicityPageInfo.total
      })
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      let searchs = null
      searchs = {
        selectStatus: 0,
        isDelete: 0,
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        type: 3
      }
      if (this.seacherName === '项目名称') {
        searchs.projectNames = this.seacher_input
      } else if (this.seacherName === '项目编号') {
        searchs.projectNumbers = this.seacher_input
      } else if (this.seacherName === '公示名称') {
        searchs.publicityNames = this.seacher_input
      }
      sotherProject.queryList(searchs).then((res) => {
        this.projecttableData = res.data.otherPublicityPageInfo.list
        this.total = res.data.otherPublicityPageInfo.total
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
        this.seacherName = '公示名称'
      } else if (val === 2) {
        this.seacherName = '项目名称'
      } else {
        this.seacherName = '项目编号'
      }
    },
    // 查看
    detailBtn (scope) {
      this.$router.push({path: `/system-management/mineral-rights/delimit-mining-area-file/detail/${scope.row.objectId}`, query: {objectId: scope.row.objectId, name: this.$route.query.name}})
    },
    // 审核
    checkBtn (scope) {
      this.$router.push({path: '/system-management/mineral-rights/delimit-mining-area-file/check', query: {objectId: scope.row.objectId, name: this.$route.query.name}})
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  #indexbox{
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
    margin-top: 50px;
    .headertitle{
      width: 100%;
      height: 60px;
      line-height: 60px;
      color:#333333;
      font-size: 16px;
      text-align: left;
      padding: 0 20px;
      box-sizing: border-box;
    }
    .el-breadcrumb{
      height: 60px;
      line-height: 60px;
    }
    .contentbigbox{
      width: 100%;
      min-height: 770px;
      background:#fff;
      padding:20px;
      box-sizing: border-box;
    }
    /*搜索*/
    .seacher_box {
      width: 540px;
      height: 36px;
      min-width: 50px;
      float: right;
      margin-bottom: 20px;
    }
    .select{
      width: 20%;
    }
    .search{
      width: 79%;
    }
    .tabletitles{
      background:#f7f8fa!important;
    }
    .el-table .cell {
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      word-break: break-all;
      line-height: 23px;
      overflow: hidden;
      text-overflow:ellipsis;
      white-space: nowrap;
    }
    .statusbox{
      display: block;
      width: 100%;
      text-align:left;
      color:#7b7e8b;
    }
    .el-button--text {
      color: #108cee;
      background: 0 0;
      padding-left: 0;
      padding-right: 0;
    }
    .el-table td{
      min-width: 0;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      text-overflow: ellipsis;
      vertical-align: middle;
      position: relative;
      padding-bottom: 5px;
    }
    .pagebox{
      margin-top: 15px;
      text-align: right;
    }
    .statusbox i{
      width: 6px;
      height: 6px;
      display: inline-block;
      margin-right: 5px;
      background: #9a9a9a;
      -webkit-border-radius: 50%;
      -moz-border-radius: 50%;
      border-radius: 50%;
    }
    .blue i{
      background: #118be1;
    }
    .green i{
      background: #2f8d35;
    }
    .red i{
      background: #ff4144;
    }
    .statusbigbox button{
      float: left;
      margin-left: 10px;
    }
  }
</style>
