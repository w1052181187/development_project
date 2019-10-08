<template>
  <div class="center_box" id="settlement">
    <div class="con_title">结算</div>
    <div class="con_table">
      <!--上边功能-->
      <div class="con_top_box">
        <el-form :model="searchForm" label-width="120px" class="demo-ruleForm">
          <div class="seacher_box_fatherzzt">
            <el-form-item label="提交时间：">
              <el-date-picker
                v-model="hangDataRange"
                type="datetimerange"
                value-format="yyyy-MM-dd HH:mm:ss"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                align="right">
              </el-date-picker>
            </el-form-item>
          </div>
          <div class="seacher_box_fatherzzt">
            <el-button type="primary" class="screeningBtn inquire_seacherbtn" icon="el-icon-search" @click="searchBtn">查询</el-button>
          </div>
        </el-form>
      </div>
      <!--上边功能-->
      <!--列表数据-->
      <el-table
        :data="projecttableData"
        border
        header-cell-class-name="table_header">
        <el-table-column
          type="index"
          label="序号"
          width="50"
          align="left"
          :index="indexMethod">
        </el-table-column>
        <el-table-column
          prop="agencyEnterprise.enterpriseName"
          label="合作单位"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="createrUser"
          label="操作人"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="price"
          label="缴费金额（元）"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="createtime"
          label="提交时间"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="status"
          label="结算状态"
          align="left"
          :formatter="paymentChannelFormatter"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          label="操作" width="100" align="left">
          <template slot-scope="scope">
            <el-button v-if="scope.row.status !== 3 && scope.row.status !== 2 && scope.row.status !== 4" type="text" size="small" class="handle_btn" @click="confirmBtn(scope)">
              确认
            </el-button>
            <el-button type="text" size="small" class="handle_btn" @click="detailBtn(scope)">
              详情
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--列表数据-->
      <!--分页-->
      <div class="page_box">
        <el-pagination
          class="pagebox"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :total="total"
          :page-size='pageSize'
          layout="prev, pager, next, jumper">
        </el-pagination>
      </div>
      <!--分页-->
    </div>
  </div>
</template>

<script>
import {settlement} from '@/api'
export default {
  name: '',
  data () {
    return {
      hangDataRange: [], // 续费日期
      searchForm: {}, // 搜索对象
      isTrue: '',
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      indexMethod: 1, // 序号
      projecttableData: []
    }
  },
  methods: {
    // ***************************************************************接口方法**********************************************************************
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    // 列表显示
    list () {
      // 根据登录的用户id查询出 这个用户所关联的合作代理机构id
      settlement.queryByUserId(this.$store.getters.authUser.userId).then((res) => {
        if (res.data.anencyId) {
          let url = {
            pageNo: this.pageNo,
            pageSize: this.pageSize,
            notStatus: 0,
            isvalid: 1,
            agencyenterpriseid: res.data.anencyId
          }
          if (this.hangDataRange !== null && this.hangDataRange.length > 0) { // 续费日期
            this.searchForm.submitStartDate = this.hangDataRange[0]
            this.searchForm.submitEndDate = this.hangDataRange[1]
          }
          if (this.hangDataRange === null) {
            this.searchForm.submitStartDate = null
            this.searchForm.submitEndDate = null
          }
          let isSeacher = (JSON.stringify(this.searchForm) === '{}')
          if (!isSeacher) { // 是否为搜索
            url = Object.assign(this.searchForm, url)
          }
          settlement.queryList(url).then((res) => {
            this.projecttableData = res.data.settlementSummaryPageInfo.list
            this.projecttableData.map((ite) => {
              if (ite.price) { // 保留两位小数
                this.$set(ite, 'price', Number(ite.price).toFixed(2))
              }
            })
            this.total = res.data.settlementSummaryPageInfo.total
          })
        }
      })
    },
    searchBtn () {
      this.currentPage = 1
      this.pageNo = 0
      this.list()
    },
    // ***************************************************************自己方法**********************************************************************
    // 查看
    detailBtn (scope) {
      this.$router.push({path: `/admin/settlement/detail/${scope.row.id}`})
    },
    // 确认
    confirmBtn (scope) {
      this.$router.push({path: `/admin/settlement/confirm`, query: {id: scope.row.id}})
    },
    // 结算状态
    paymentChannelFormatter (row, column) {
      if (row) {
        switch (row.status) {
          case 0 :
            return '未提交'
          case 1 :
            return '确认中'
          case 2 :
            return '驳回'
          case 3 :
            return '审核中'
          case 4 :
            return '审核通过'
          case 5 :
            return '审核不通过'
        }
      } else {
        return '------'
      }
    }
  },
  mounted () {
    this.list()
  }
}
</script>

<style lang="less">
  #settlement{
    /*表格合计*/
    .el-table__footer-wrapper tr td:nth-child(1){
      color: #333333;
      font-weight: bold;
    }
    .search_bigbox .el-form-item{
      width: 76%;
    }
    .screeningBtn{
      padding: 0;
      width: 85px;
      height: 40px;
      background:#009dff;
      color: #fff;
      border: none;
      margin-left: 20px;
    }
    .screeningBtn:focus, .screeningBtn:hover {
      color: #fff;
      background:#009dff;
      border: none;
    }
    .guapaishi .el-date-editor{
      width: 70%;
    }
    .zongheji_title_box{
      float: left;
      height: 100%;
      width: 15%;
      float: left;
      margin-left: 3.7%;
      padding-left: 10px;
      box-sizing: border-box;
      text-align: left;
      font-size: 14px;
      font-weight: bold;
    }
    .zongheji_box{
      height: 100%;
      width: 17.5%;
      float: right;
      padding-left: 10px;
      box-sizing: border-box;
      text-align: left;
      font-size: 14px;
      font-weight: bold;
    }
    .check_timebox:nth-child(1){
      margin-left: 0;
    }
    .el-range-editor .el-range-input {
      display: inline-block;
      height: 28px;
      line-height: 28px;
    }
  }
</style>
