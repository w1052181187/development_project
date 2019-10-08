<template>
  <div id="indexbox">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>矿权交易备案</el-breadcrumb-item>
        <el-breadcrumb-item>挂牌竞价情况查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--搜索框-->
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
              <el-button  slot="append" icon="el-icon-search" @click="search"></el-button>
            </el-input>
          </div>
        </el-row>
        <!--搜索框-->
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
            prop="announcement.hangStartTime"
            label="挂牌开始时间"
            align="left">
          </el-table-column>
          <el-table-column
            prop="announcement.hangEndTime"
            label="挂牌结束时间"
            align="left">
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" width="140" align="center">
            <template slot-scope="scope">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
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
import {prProject} from '@/api'
import {factory} from '@/assets/js/auctionHandler'
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
      pageSize: 10, // 每页展示条数
      total: 50, // 总条数
      searchIs: false
    }
  },
  methods: {
    handleSizeChange (val) {
      this.pageNo = (val - 1) * this.pageSize
      this.list()
    },
    handleCurrentChange (val) {
      this.pageNo = (val - 1) * this.pageSize
      this.list()
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
      this.$router.push({path: '/system-management/mineral-rights/listing-bidding/detail', query: {sectionId: scope.row.objectId, roomId: scope.row.roomId, roledsType: 1}})
    },
    list () {
      let params = this.createQuery()
      if (this.searchIs) {
        // 通过不同的查询进行参数赋值
        if (this.seacherName == '项目名称') {
          params.sectionNames = this.seacher_input
        } else if (this.seacherName == '项目编号') {
          params.sectionNumbers = this.seacher_input
        }
      }
      prProject.queryList(params).then((res) => {
        this.projecttableData = res.data.sectionPageInfo.list
        this.listAfter()
        this.total = res.data.sectionPageInfo.total
      })
    },
    search () {
      this.currentPage = 1
      this.dataIndex = 1
      this.pageNo = 0
      this.searchIs = true
      this.list()
    },
    createQuery () {
      return {
        isDelete: 0,
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        landUse: 2
      }
    },
    listAfter () {
      this.projecttableData.forEach(item => {
        let auctionHandler = factory.create({
          auctionType: factory.AuctionType['hang'],
          userJob: factory.UserJobType['auctor'],
          section: item
        })
        item.statusStr = auctionHandler.getAuctionStatusStr()
        item.auctionStatus = auctionHandler.getAuctionStatus()
      })
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
      text-align:center;
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
