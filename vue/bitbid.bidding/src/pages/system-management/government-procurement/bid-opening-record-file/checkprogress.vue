<template>
  <div id="projectfileindexbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/bid-opening-record-file'}">开标记录表备案</el-breadcrumb-item>
        <el-breadcrumb-item>确认进度查看</el-breadcrumb-item>
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
            prop="bidderName"
            label="供应商名称"
            align="left">
          </el-table-column>
          <el-table-column
            prop="confirmDate"
            label="确认时间"
            align="left">
          </el-table-column>
          <el-table-column
            prop="confirmStatus"
            label="确认状态"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.confirmStatus === 0">
                <span class="statusbox ">未确认</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.confirmStatus === 1">
                <span class="statusbox ">已确认</span>
              </div>
            </template>
          </el-table-column>
        </el-table>
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
      searchIs: false
    }
  },
  methods: {
    // 列表
    list () {
      pushData.kbLogProgress(this.$route.params.objectId, this.$route.params.biaoDuanId).then((res) => {
        this.projecttableData = JSON.parse(res.data.kbLogConProList)
      })
    },
    search () {},
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '项目名称'
      } else {
        this.seacherName = '项目编号'
      }
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
