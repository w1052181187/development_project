<template>
  <div id="procurementproject" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item>采购项目备案</el-breadcrumb-item>
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
            prop="faBaoBianHao"
            label="采购项目编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="faBaoMingCheng"
            label="采购项目名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="projectName"
            label="所属项目"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="shiFouWeiTuo"
            label="委托"
            align="left"
            width="80" >
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.shiFouWeiTuo === 1">
                <span class="statusbox ">是</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.shiFouWeiTuo === 0">
                <span class="statusbox">否</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="creator"
            label="提交人"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="fabaoStatus"
            label="项目状态"
            align="left">
            <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.fabaoStatus === 2">
                  <span class="statusbox">审批通过</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.fabaoStatus === 3">
                  <span class="statusbox">审批不通过</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.fabaoStatus === 1">
                  <span class="statusbox">备案审批中</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.fabaoStatus === 0">
                  <span class="statusbox">未提交</span>
                </div>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" width="160" align="center">
            <template slot-scope="scope">
              <div class="statusbigbox">
                <el-button type="text" size="small" @click="packaginggroupBtn(scope)">
                  包组信息
                </el-button>
                <el-button type="text" size="small" @click="detailBtn(scope,'check')" v-if="scope.row.fabaoStatus === 1">
                  审批
                </el-button>
                <el-button type="text" size="small" @click="detailBtn(scope,'detail')">
                  查看
                </el-button>
              </div>
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
      seacherName: '采购项目名称',
      options: [{
        value: 1,
        label: '采购项目名称'
      }, {
        value: 2,
        label: '采购项目编号'
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
        } else if (this.selectData === 2) {
          url.code = this.seacher_input
        }
      }
      pushData.fabaoList(url).then((res) => {
        this.projecttableData = JSON.parse(res.data.projectList)
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
        this.seacherName = '采购项目名称'
        this.selectData = 1
      } else {
        this.seacherName = '采购项目编号'
        this.selectData = 2
      }
    },
    handleCurrentChange (nowNum) {
      this.pageNo = nowNum
      this.list()
    },
    // 查看
    detailBtn (scope, type) {
      this.$router.push({path: `/system-management/government-procurement/procurement-project-file/detail-or-check/${type}/${scope.row.id}`, query: {type: scope.row.transferType}})
    },
    // 包组信息
    packaginggroupBtn (scope) {
      this.$router.push({path: '/system-management/government-procurement/procurement-project-file/package-group', query: {fabaoId: scope.row.id}})
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
  #procurementproject {
    .select {
      width: 30%;
    }
    .search {
      width: 69%;
    }
  }
</style>
