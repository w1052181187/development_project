<template>
  <div id="enrolmentindex"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>网上报名</el-breadcrumb-item>
        <el-breadcrumb-item>发送资格确认书</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--项目名称  项目编号搜索-->
        <el-row>
          <div class="seacher_box">
            <el-select v-model="inquiryValue" class="select" @change="selectSeacher">
              <el-option
                v-for="item in options"
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
        <!--项目名称  项目编号搜索-->
        <!--主要内容 table-->
        <el-table
          :data="regisTableData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="landAnnoMation.annoNumber"
            label="公告名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="sectionNumber"
            label="地块编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="methodOfTransfer"
            label="出让方式"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.methodOfTransfer === null">
                <span class="statusbox">----</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 1">
                <span class="statusbox">拍卖</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 2">
                <span class="statusbox">挂牌</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 3">
                <span class="statusbox">资格后审</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 4">
                <span class="statusbox">资格预审</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="count"
            label="报名数量"
            align="left">
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" width="140" align="center">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="detailBtn(scope)">
                查看发送情况
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
import {landProject} from '@/api'
export default {
  data () {
    return {
      seacherInput: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '公告名称',
      options: [{
        value: 1,
        label: '公告名称'
      }, {
        value: 2,
        label: '地块编号'
      }],
      inquiryValue: 1,
      regisTableData: [],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 20, // 总条数
      pageSize: 10 // 每页展示条数
    }
  },
  methods: {
    // ***************************************************************数据接口方法**********************************************************************
    list () {
      let url = {}
      url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0,
        biddingType: 1
      }
      if (this.seacherInput !== '') {
        if (this.seacherName === '公告名称') {
          url.annoName = this.seacherInput
        } else if (this.seacherName === '地块编号') {
          url.sectionNumber = this.seacherInput
        }
      }
      landProject.queryList(url).then((res) => {
        this.regisTableData = res.data.landInformationPageInfo.list
        this.total = res.data.landInformationPageInfo.total
      })
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.list()
    },
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    // ***************************************************************自己方法**********************************************************************
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '公告名称'
      } else {
        this.seacherName = '地块编号'
      }
    },
    // 查看发送情况
    detailBtn (scope) {
      this.$router.push({path: `/lt/transferor/online-registration/send-confirmation/situation/${scope.row.objectId}`, query: {roledsType: 1, showStatus: false, methodOfTransfer: scope.row.methodOfTransfer}})
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
