<template>
  <div id="land_pro_patchReg" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让邀请</el-breadcrumb-item>
        <el-breadcrumb-item>查看出让邀请确认情况</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--搜索按钮-->
        <el-row>
          <div class="seacher_box">
            <el-select v-model="inquiryValue" class="select" @change="selectSeacher">
              <el-option
                v-for="item in selectOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-input class="search" style="vertical-align: top"  :placeholder="'请输入' + seacherName" v-model="seacherInput">
              <el-button  slot="append" icon="el-icon-search" type="" @click="search"></el-button>
            </el-input>
          </div>
        </el-row>
        <!--搜索按钮-->
        <!--主要内容 table-->
        <el-table
          :data="projecttableData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="annoNumber"
            label="邀请书名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="sectionNumber"
            label="地块编号"
            align="left"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span class="jfbdbox" v-for="(item,index) in scope.row.landInformations" :key="index">
                <span class="xmbh_box" :title="item.sectionNumber">{{item.sectionNumber}}</span>
              </span>
            </template>
          </el-table-column>
          <el-table-column
            prop="releaseTime"
            label="发布时间"
            align="left"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.releaseTime === ''">
                <span class="statusbox">-----</span>
              </div>
              <div class="statusbigbox" v-else>
                <span class="statusbox">{{scope.row.releaseTime}}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="status"
            label="发布状态"
            align="left"
            width="240">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 0">
                <span class="statusbox gray"><i></i>未发布</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 4">
                <span class="statusbox yellow"><i></i>已发布</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="confirmation"
            label="确认情况"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" width="180" align="center">
            <template slot-scope="scope">
              <div class="statusbigbox">
                <el-button type="text" size="small" @click="detailConfirmationBtn(scope)">
                  查看确认情况
                </el-button>
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
              </div>
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
import {landAnno} from '@/api'
export default {
  data () {
    return {
      seacherInput: '', // 搜索框内容绑定
      seacherName: '邀请书名称',
      selectOptions: [{
        value: 0,
        label: '邀请书名称'
      }, {
        value: 1,
        label: '地块编号'
      }],
      inquiryValue: 0, // 搜索默认值
      // 列表表格
      projecttableData: [],
      currentPage: 1, // 当前页
      pageNo: 0, // 页数
      total: 10, // 总条数
      pageSize: 10, // 每页展示条数
      searchIs: false
    }
  },
  methods: {
    // ***************************************************************数据接口方法**********************************************************************
    // -----------------------------------------列表-----------------------------------------
    list () {
      let url = {}
      url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0,
        status: 4
      }
      if (this.seacherInput !== '') {
        if (this.seacherName === '邀请书名称') {
          url.messageLike = this.seacherInput
        } else if (this.seacherName === '地块编号') {
          url.sectionNumber = this.seacherInput
        }
      }
      landAnno.queryList(url).then((res) => {
        console.log(res)
        this.projecttableData = res.data.landAnnoPageInfo.list
        this.total = res.data.landAnnoPageInfo.total
      })
    },
    // -----------------------------------------分页方法-----------------------------------------
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    // ----------------------------------------搜索-------------------------------------------
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.list()
    },
    // -----------------------------------------查看----------------------------------------------
    detailBtn (scope) {
      this.$router.push({path: `/lt/transferor/remise-invite/look-invite/detail/${scope.row.objectId}`, query: {roledsType: 1, showStatus: false}})
    },
    // -----------------------------------------查看确认情况----------------------------------------------
    detailConfirmationBtn (scope) {
      this.$router.push({path: `/lt/transferor/remise-invite/look-invite/assignee-situation/${scope.row.objectId}`, query: {roledsType: 1, showStatus: false}})
    },
    // ***************************************************************自己方法**********************************************************************
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '地块编号'
      } else {
        this.seacherName = '邀请书名称'
      }
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
