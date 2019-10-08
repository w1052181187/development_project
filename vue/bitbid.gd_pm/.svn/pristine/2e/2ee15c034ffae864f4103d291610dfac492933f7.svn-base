<template>
  <div class="center_box" id="result_settlement">
    <div class="con_title">交易记录</div>
    <div class="con_table">
      <!--上边功能-->
      <div class="con_top_box">
        <el-form :model="searchForm" label-width="120px" class="demo-ruleForm">
          <div class="seacher_box_fatherzzt">
            <div class="seacher_box record_seacherbox">
              <el-select v-model="seacherSelect" class="select_box" @change="currentSel">
                <el-option
                  v-for="item in seacherData"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
              <el-input class="search" style="vertical-align: top"  :placeholder="'请输入' + seacherName" v-model.trim="searchForm.searchInput"></el-input>
            </div>
          </div>
          <div class="seacher_box_fatherzzt">
            <el-form-item label="费项：">
              <el-select v-model="searchForm.chargeitem" placeholder="请选择" class="el_select">
                <el-option
                  v-for="item in paymentchannel"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </div>
          <div class="seacher_box_fatherzzt">
            <el-form-item label="缴费日期：">
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
            <div v-for="(dateTime, index) in dateList" :key="index" class="check_timebox" @click="chooseAddr(index, dateTime)">
              <div :class="{'check_timebox_active' : index === isTrue}">{{dateTime.time}}</div>
            </div>
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
        show-summary
        :summary-method="getSummaries"
        header-cell-class-name="table_header">
        <el-table-column
          type="index"
          label="序号"
          width="50"
          align="left"
          :index="indexMethod">
        </el-table-column>
        <el-table-column v-if="this.$store.getters.authUser.userType === 1"
          prop="agencyEnterprise.enterpriseName"
          label="合作单位"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <!--<el-table-column-->
          <!--prop="ordercode"-->
          <!--label="订单号"-->
          <!--align="left"-->
          <!--width="300"-->
          <!--show-overflow-tooltip>-->
        <!--</el-table-column>-->
        <!--<el-table-column-->
          <!--prop="threepartyorderflowcode"-->
          <!--label="支付流水号"-->
          <!--align="left"-->
          <!--width="300"-->
          <!--show-overflow-tooltip>-->
        <!--</el-table-column>-->
        <el-table-column v-if="this.$store.getters.authUser.userType === 1"
          prop="appForChaVerMana.versionName"
          label="申请渠道"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="payUser.namezh"
          label="缴费单位"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="chargeitem"
          label="费用类型"
          align="left"
          :formatter="catypeFormatter"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="paymentchannel"
          label="缴费方式"
          align="left"
          :formatter="paymentChannelFormatter"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="createdate"
          label="缴费时间"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <!--<el-table-column-->
          <!--prop="operatorUser.namezh"-->
          <!--label="操作人"-->
          <!--align="left"-->
          <!--width="300"-->
          <!--show-overflow-tooltip>-->
        <!--</el-table-column>-->
        <el-table-column
          prop="price"
          label="缴费金额（元）"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          label="操作" width="100" align="center" fixed="right">
          <template slot-scope="scope">
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
import {zbOrderFlow} from '@/api'
export default {
  name: '',
  data () {
    return {
      hangDataRange: [], // 续费日期
      seacherInput: '', // 搜索input值
      seacherSelect: '', // 选择默认值
      // 搜索框选择内容
      seacherData: [],
      // 选择不同的搜索方式切换不同的提示
      seacherName: '',
      searchForm: {
        searchInput: ''
      }, // 搜索对象
      // 项目类型
      projectData: [
        {
          value: 1,
          label: '企业'
        },
        {
          value: 2,
          label: '个人'
        }
      ],
      paymentchannel: [
        {
          value: 0,
          label: 'CA记录'
        }, {
          value: 4,
          label: '平台使用费'
        }, {
          value: 2,
          label: '标书费'
        }, {
          value: 5,
          label: '中标服务费'
        }
      ],
      dateList: [
        {
          time: '昨天'
        },
        {
          time: '近一周'
        },
        {
          time: '近一月'
        },
        {
          time: '近一年'
        }
      ],
      isTrue: '',
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      indexMethod: 1, // 序号
      projecttableData: [],
      searchType: ''
    }
  },
  methods: {
    // ***************************************************************自己方法**********************************************************************
    // 合计方法
    getSummaries (param) {
      const { columns, data } = param
      const sums = []
      let sumPrice = 0
      data.map((item) => {
        sumPrice += Number(item.price)
      })
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '合计'
          return
        }
        if (this.$store.getters.authUser.userType === 1) { // 运营
          if (index === 7) {
            sums[index] = sumPrice.toFixed(2)
          } else {
            sums[index] = ''
          }
        }
        if (this.$store.getters.authUser.userType === 2) { // 代理
          if (index === 5) {
            sums[index] = sumPrice.toFixed(2)
          } else {
            sums[index] = ''
          }
        }
      })
      return sums
    },
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    // 点击切换
    chooseAddr (index, dateTime) {
      this.isTrue = index
      if (dateTime.time === '昨天') {
        this.searchForm.publishedStartTime = this.format('start', 1)
        this.searchForm.publishedEndTime = this.format('end', 1)
      } else if (dateTime.time === '近一周') {
        this.searchForm.publishedStartTime = this.format('start', 7)
        this.searchForm.publishedEndTime = this.format('end', 0)
      } else if (dateTime.time === '近一月') {
        this.searchForm.publishedStartTime = this.format('start', 31)
        this.searchForm.publishedEndTime = this.format('end', 0)
      } else if (dateTime.time === '近一年') {
        this.searchForm.publishedStartTime = this.format('start', 365)
        this.searchForm.publishedEndTime = this.format('end', 0)
      }
      this.searchForm.dateTime = dateTime.time
      this.searchBtn(1)
    },
    format (flag, day) {
      let curTime = new Date()
      if (flag === 'start') {
        curTime.setHours(0)
        curTime.setMinutes(0)
        curTime.setSeconds(0)
      } else {
        curTime.setHours(23)
        curTime.setMinutes(59)
        curTime.setSeconds(59)
      }
      let duration = 24 * 60 * 60 * 1000 * day
      var time = new Date(curTime - duration)
      var y = time.getFullYear()
      var m = time.getMonth() + 1
      var d = time.getDate()
      var h = time.getHours()
      var mm = time.getMinutes()
      var s = time.getSeconds()
      return y + '-' + m + '-' + d + ' ' + h + ':' + mm + ':' + s
    },
    // 列表显示
    list (type) {
      if (type === 1) {
        this.hangDataRange = []
      }
      let url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        searchType: this.searchType,
        chargeitems: 1
      }
      // if (this.$store.getters.authUser.userType === 2) {
      // this.$set(url, 'operator', this.$store.getters.authUser.userId)
      // }
      let roleArrar = this.$store.getters.authUser.roleIds
      if (this.$store.getters.authUser.roleIds) {
        if (roleArrar.filter(item => Number(item) === 102 || Number(item) === 1007).length === 0) {
          url.creator = this.$store.getters.authUser.userId

          if (url.chargeitem === 0 || !url.chargeitem) {
            this.$set(url, 'ownerUserId1', this.$store.getters.authUser.ownerUserID)
          }
        } else {
          if (url.chargeitem === 0 || !url.chargeitem) {
            this.$set(url, 'ownerUserId2', this.$store.getters.authUser.userId)
          }
          console.log(url)
          let roleArrarNew = this.$store.getters.authUser.roleIds
          if (roleArrarNew.filter(item => Number(item) === 102).length !== 0) {
            url.creatorQy = this.$store.getters.authUser.userId
          }
          if (roleArrarNew.filter(item => Number(item) === 1007).length !== 0) {
            url.creatorQy = this.$store.getters.authUser.ownerUserID
          }
        }
      }
      if (this.hangDataRange !== null && this.hangDataRange.length > 0) { // 续费日期
        this.isTrue = -1
        this.searchForm.publishedStartTime = this.hangDataRange[0]
        this.searchForm.publishedEndTime = this.hangDataRange[1]
      }
      if (this.hangDataRange === null) {
        this.searchForm.publishedStartTime = null
        this.searchForm.publishedEndTime = null
      }
      let isSeacher = (JSON.stringify(this.searchForm) === '{}')
      if (!isSeacher) { // 是否为搜索
        url = Object.assign(this.searchForm, url)
      }
      console.log(url)
      zbOrderFlow.queryList(url).then((res) => {
        this.projecttableData = res.data.zbOrderFlowPageInfo.list
        console.log(this.projecttableData)

        this.projecttableData.map((ite) => {
          ite.price = Number(ite.price).toFixed(2)
        })
        this.total = res.data.zbOrderFlowPageInfo.total
        console.log(this.total)
      })
    },
    userType () {
      if (this.$store.getters.authUser.userType === 1) { // 运营
        this.seacherData.push({value: 0, label: '合作单位'})
        this.seacherData.push({value: 2, label: '申请渠道'})
        this.seacherSelect = 0
        this.searchType = 0
        this.seacherName = '合作单位'
      }
      if (this.$store.getters.authUser.userType === 2) { // 代理
        this.seacherData.push({value: 5, label: '缴费单位'})
        this.seacherData.push({value: 6, label: '包组编号'})
        this.seacherSelect = 7
        this.searchType = 7
        this.seacherName = '缴费单位'
      }
    },
    searchBtn (type) {
      this.currentPage = 1
      this.pageNo = 0
      this.list(type)
    },
    // ***************************************************************自己方法**********************************************************************
    // 查看
    detailBtn (scope) {
      if (scope.row.chargeitem === 0) {
        this.$router.push({path: `/admin/record/ca/detail/${scope.row.id}`})
      } else if (scope.row.chargeitem === 1) {
        this.$router.push({path: `/admin/record/tender-fee/detail/${scope.row.id}`})
      } else if (scope.row.chargeitem === 2) {
        this.$router.push({path: `/admin/record/tender-fee/detail/${scope.row.id}`})
      } else if (scope.row.chargeitem === 3) {
        this.$router.push({path: `/admin/record/tender-fee/detail/${scope.row.id}`})
      } else if (scope.row.chargeitem === 4) {
        this.$router.push({path: `/admin/record/platform/detail/${scope.row.id}`})
      } else if (scope.row.chargeitem === 5) {
        this.$router.push({path: `/admin/record/winning/detail/${scope.row.id}`})
      }
    },
    catypeFormatter (row, column) {
      switch (row.chargeitem) {
        case 0 :
          return 'CA记录'
        case 1 :
          return '标书费'
        case 2 :
          return '标书费'
        case 4 :
          return '平台使用费'
        case 5 :
          return '中标服务费'
      }
    },
    paymentChannelFormatter (row, column) {
      if (row.zbOrder || row.sysUserOrderCa) {
        if (row.chargeitem === 0) { // 就你特殊
          switch (row.sysUserOrderCa.zhifufangshi) {
            case 1 :
              return '转账'
            case 2 :
              return '支付宝'
            case 3 :
              return '网银'
            case 4 :
              return '现金'
            case 5 :
              return '汇款'
            case 6 :
              return '其他'
          }
        } else {
          switch (row.paymentchannel) {
            case 1 :
              return '线下支付'
            case 2 :
              return '线上支付'
            case 3 :
              return '其他支付'
            case 4 :
              return '支付宝'
            case 5 :
              return '线下汇款'
            case 6 :
              return '投标保函'
            case 7 :
              return '支票'
            case 8 :
              return '其他'
          }
        }
      } else {
        return '------'
      }
    },
    currentSel (item) {
      this.searchType = item
      switch (item) {
        case 0:
          this.seacherName = '合作单位'
          break
        case 1:
          this.seacherName = '订单号'
          break
        case 2:
          this.seacherName = '申请渠道'
          break
        case 3:
          this.seacherName = '申请单位'
          break
        case 4:
          this.seacherName = '操作人'
          break
        case 5:
          this.seacherName = '公告名称'
          break
        case 6:
          this.seacherName = '包组编号'
          break
      }
    }
  },
  mounted () {
    this.userType()
    this.list()
  }
}
</script>

<style lang="less">
  #result_settlement{
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
    .el-range-editor .el-range-input {
      display: inline-block;
      height: 28px;
      line-height: 28px;
    }
  }
</style>
