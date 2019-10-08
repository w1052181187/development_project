<template>
  <div id="profilelist" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item>采购文件备案</el-breadcrumb-item>
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
          <!--<el-table-column-->
            <!--type="index"-->
            <!--width="20">-->
          <!--</el-table-column>-->
          <el-table-column
            prop="noticeName"
            label=""
            align="center"
            width="50">
            <template slot-scope="scope">
              <i class="morebtn el-icon-arrow-right" @click="btnOpen($event)"></i>
            </template>
          </el-table-column>
          <el-table-column
            prop="faBaoMingCheng"
            label="采购公告名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="bdBianHao"
            label="包组编号"
            align="left"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span class="jfbdbox" v-for="(item,index) in scope.row.sections" :key="index">
                <span class="xmbh_box" :title="item.secNumber">{{item.bdBianHao}}</span>
              </span>
            </template>
          </el-table-column>
          <el-table-column
            prop="bdMingCheng"
            label="包组名称"
            align="left"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span class="jfbdbox" v-for="(item,index) in scope.row.sections" :key="index">
                <span class="xmbh_box" :title="item.secName">{{item.bdMingCheng}}</span>
              </span>
            </template>
          </el-table-column>
          <el-table-column
            prop="faBuTime"
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
            prop="fabuZhuangTai"
            label="发布状态"
            align="left"
            width="100">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.fabuZhuangTai === 1">
                <span class="statusbox ">已发布</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.fabuZhuangTai === 3">
                <span class="statusbox ">备案审批中</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.fabuZhuangTai === 4">
                <span class="statusbox ">审批通过</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.fabuZhuangTai === 5">
                <span class="statusbox ">审批不通过</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="操作" width="160" align="center">
            <template  slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.fabuZhuangTai === 1">
                <el-row :gutter="20">
                  <el-col :span="12">
                    <span class="jfbdbox" v-for="(item,index) in scope.row.sections" :key="index">
                      <el-button type="text" size="small" @click="downloadBtn(item)">
                        查看下载情况
                      </el-button>
                    </span>
                  </el-col>
                  <el-col :span="12">
                    <el-button type="text" size="small" @click="detailBtn(scope, 'detail')">
                      查看
                    </el-button>
                  </el-col>
                </el-row>
              </div>
              <div class="statusbigbox" v-if="scope.row.fabuZhuangTai === 3">
                <el-button type="text" size="small" @click="detailBtn(scope, 'detail')">
                  查看
                </el-button>
                <el-button type="text" size="small" @click="detailBtn(scope, 'check')">
                  审批
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.fabuZhuangTai === 4">
                <el-button type="text" size="small" @click="detailBtn(scope, 'detail')">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.fabuZhuangTai === 5">
                <el-button type="text" size="small" @click="detailBtn(scope, 'detail')">
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
import $ from 'jquery'
import { pushData } from '@/api'
export default {
  data () {
    return {
      seacher_input: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '采购公告名称',
      options: [{
        value: 1,
        label: '采购公告名称'
      }, {
        value: 2,
        label: '包组名称'
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
      }
      pushData.zbwjFile(url).then((res) => {
        this.projecttableData = JSON.parse(res.data.zbwjFileString)
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
        this.seacherName = '采购公告名称'
      } else {
        this.seacherName = '包租名称'
      }
    },
    handleCurrentChange (nowNum) {
      this.pageNo = nowNum
      this.list()
    },
    // 展开隐藏信息
    btnOpen (event) {
      if ($(event.currentTarget).hasClass('el-icon-arrow-down')) {
        $(event.currentTarget).removeClass('el-icon-arrow-down').addClass('el-icon-arrow-right')
        $(event.currentTarget).parents('tr.el-table__row').find('.cell').css('height', '30px')
      } else {
        $(event.currentTarget).parents('tr.el-table__row').find('.cell').css('height', 'auto')
        $(event.currentTarget).removeClass('el-icon-arrow-right').addClass('el-icon-arrow-down')
      }
    },
    // 查看
    detailBtn (scope, type) {
      this.$router.push({path: `/system-management/government-procurement/procurement-documents-file/detail-or-check/${type}/${scope.row.id}`})
    },
    // 查看下载情况
    downloadBtn (item) {
      this.$router.push({path: `/system-management/government-procurement/procurement-documents-file/check-download/${item.bianDuanId}`})
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
  #profilelist {
    .select {
      width: 30%;
    }
    .search {
      width: 69%;
    }
    .el-table .cell {
      height: 30px;
    }
    .cellopen{
      box-sizing: border-box;
      word-break: break-all;
      line-height: 23px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .el-tooltip{
      padding: 0 20px;
    }
    .xmbh_box{
      display: block;
      margin-bottom: 10px;
      overflow: hidden;
      text-overflow:ellipsis;
      white-space: nowrap;
    }
    .el-icon-arrow-right{
      cursor: pointer;
    }
  }
</style>
