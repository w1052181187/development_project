<template>
  <div id="systemusage" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>缴费情况查询</el-breadcrumb-item>
        <el-breadcrumb-item>系统使用费缴纳情况备案</el-breadcrumb-item>
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
            prop="sectionNumber"
            label="采购项目编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="sectionName"
            label="采购公告名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="sectionNumber"
            label="包组编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="sectionName"
            label="包组名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="name"
            label="投标人名称"
            align="left">
          </el-table-column>
          <el-table-column
            prop="status"
            label="是否缴费"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <span class="statusbox ">是</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
                <span class="statusbox ">否</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="money"
            label="缴费金额"
            align="left">
          </el-table-column>
          <el-table-column
            prop="methods"
            label="缴费方式"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.methods === 1">
                <span class="statusbox ">其他</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.methods === 2">
                <span class="statusbox ">线下汇款</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.methods === 3">
                <span class="statusbox ">保函</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.methods === 4">
                <span class="statusbox ">支票</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.methods === 5">
                <span class="statusbox ">在线缴纳</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="time"
            label="缴费时间"
            align="left">
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
        label: '投标人名称'
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
    list () {},
    search () {},
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '包组名称'
      } else {
        this.seacherName = '投标人名称'
      }
    },
    handleCurrentChange (nowNum) {
      this.pageNo = nowNum
      this.list()
    },
    // 查看
    detailBtn (scope) {
      this.$router.push({path: `/system-management/government-procurement/system-usage-fee-file/detail/${scope.row.objectId}`, query: {type: scope.row.transferType}})
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
  #systemusage {
    .select {
      width: 30%;
    }
    .search {
      width: 69%;
    }
  }
</style>
