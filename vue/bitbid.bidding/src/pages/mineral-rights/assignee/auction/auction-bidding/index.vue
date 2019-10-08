<template>
    <div id="auctionlist">
      <div class="headertitle">
        <!--面包屑-->
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>拍卖</el-breadcrumb-item>
          <el-breadcrumb-item>拍卖竞价</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
      </div>
      <div class="contentbigbox">
        <template>
          <!--搜索框-->
          <el-row>
            <div class="seacher_box">
              <el-select v-model="seacher_type" class="select">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
              <el-input class="search" style="vertical-align: top"  :placeholder="'请输入' + seacherName" v-model="seacher_input">
                <el-button  slot="append" icon="el-icon-search" @click="search"></el-button>
              </el-input>
            </div>
          </el-row>
          <!--搜索框-->
          <!--主要内容 table-->
          <el-table
            :data="auctiontableData"
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
              prop="announcement.saleStartTime"
              label="拍卖开始时间"
              align="left" >
            </el-table-column>
            <el-table-column
              prop="pricestatus"
              label="竞价状态"
              align="left">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.pricestatus === 0">
                  <span class="statusbox gray"><i></i>未开始</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.pricestatus === 1">
                  <span class="statusbox blue"><i></i>竞价中</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.pricestatus === 2">
                  <span class="statusbox green"><i></i>已结束</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作" width="140" align="center">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.pricestatus === 0">
                  <el-button type="text" size="small" @click="biddingBtn(scope)">
                    进入竞价室
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.pricestatus === 1">
                  <el-button type="text" size="small" @click="biddingBtn(scope)">
                    进入竞价室
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.pricestatus === 2">
                  <el-button type="text" size="small" @click="detailBtn(scope)">
                    查看拍卖记录
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
import {prProject} from '@/api'
export default {
  data () {
    return {
      seacher_input: '',
      seacher_type: 1,
      seacherName: '项目名称',
      options: [{
        value: 1,
        label: '项目名称'
      }, {
        value: 2,
        label: '项目编号'
      }],
      auctiontableData: [],
      // 当前页
      landUse: 1,
      currentPage: 1,
      pageNo: 0,
      total: 50, // 总条数
      pageSize: 10, // 每页展示条数
      searchIs: false
    }
  },
  methods: {
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list(this.searchIs)
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list(this.searchIs)
    },
    // 查看
    detailBtn (scope) {
      this.$router.push({path: '/mr/assignee/auction/auction-bids/detail',
        query: {
          sectionName: scope.row.sectionName,
          sectionNumber: scope.row.sectionNumber,
          annoNumber: scope.row.announcement.annoNumber,
          roomId: scope.row.roomId,
          roledsType: 2}
      })
    },
    // 进入竞价室
    biddingBtn (scope) {
      this.$router.push({path: '/mr/assignee/auction/auction-bids/bidding-room',
        query: {
          status: scope.row.Status,
          roomId: scope.row.roomId,
          code: scope.row.code,
          startTime: scope.row.announcement.saleStartTime,
          roledsType: 2
        }
      })
    },
    list () {
      let params = {
        isDelete: 0,
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        bidder: this.$store.getters.authUser.userId,
        landUse: 1,
        isSend: 1,
        auctionOrderBy: 0 // 拍卖排序标记，有值就生效
      }
      if (this.seacher_input.length > 0) {
        if (this.seacher_type == 1) {
          params.sectionName = this.seacher_input
        } else if (this.seacher_type == 2) {
          params.sectionNumber = this.seacher_input
        }
      }
      prProject.queryAuctionList(params).then((res) => {
        this.auctiontableData = res.data.sectionPageInfo.list
        this.total = res.data.sectionPageInfo.total
      })
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.list()
    }
  },
  watch: {
    seacher_type: function (newVal, oldVal) {
      if (newVal == 1) {
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
  #auctionlist{
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
    /*新增按钮*/
    .addbutton{
      float: left;
      background: #2c972f;
      border-color: #2c972f;
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
    /*弹出框*/
    .el-form-item__content .el-select{
      float: left;
    }
    .el-dialog__header{
      overflow: hidden;
      border-bottom: 1px solid #f4f4f4;
    }
    .el-dialog__header .el-dialog__title{
      float: left;
    }
    .contentbigbox{
      width: 100%;
      min-height: 770px;
      background:#fff;
      padding:20px;
      box-sizing: border-box;
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
    .pagebox{
      margin-top: 15px;
      text-align: right;
    }
  }
</style>
