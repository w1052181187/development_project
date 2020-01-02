<template>
  <div class="greenCon" id="sdirectPurchase">
    <el-form class="top-search" :model="searchForm" ref="searchForm" label-width="90px">
      <el-row>
        <el-col :span="8">
          <el-form-item label="项目名称">
            <el-input placeholder="请输入项目名称关键字" v-model="searchForm.projectNameLike"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="发布时间">
            <el-date-picker
              v-model="searchForm.publishTimeRange"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :default-time="['00:00:00', '23:59:59']">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="采购预算">
            <el-input-number v-model="searchForm.budgetMin" :controls="false"></el-input-number>
            <span>-</span>
            <el-input-number v-model="searchForm.budgetMax" :controls="false"></el-input-number>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="物料名称">
            <el-input placeholder="请输入物料名称关键字" v-model="searchForm.subjectMattersNameLike"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="截止时间">
            <el-date-picker
              v-model="searchForm.bidEndDateRange"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :default-time="['00:00:00', '23:59:59']">
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="项目状态">
            <span v-for="(item,index) in projectStatusOption"
                  :key="index"
                  class="price-status"
                  :class="{'curr' : index === addrSelected}"
                  @click="chooseAddr(index, item)">
                   {{item.name}}<span class="price-num" :class="{'curr' : index === addrSelected}">({{item.num}})</span>
            </span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <div class="handle-btn">
            <el-button @click="reset">重置</el-button>
            <el-button type="primary" @click="search">查询</el-button>
          </div>
        </el-col>
      </el-row>
    </el-form >
    <div class="mainContent">
      <div class="top">
        <span class="title">项目列表</span>
        <div class="add">
          <span class="draft" @click="handleDraftClick"><i></i><span>草稿箱({{draftCount}})</span></span>
          <el-button class="addButton" type="primary">最新直采信息</el-button>
        </div>
      </div>
      <div class="list-box">
        <el-table
          border
          header-cell-class-name="table_header"
          :data="tableData">
          <el-table-column
            prop="publishTime"
            :formatter="simpleFormatData"
            label="发布时间">
          </el-table-column>
          <el-table-column
            prop="name"
            label="项目名称">
          </el-table-column>
          <el-table-column
            prop="subjectMatterNames"
            label="采购物料">
          </el-table-column>
          <el-table-column
            prop="budget"
            label="采购预算">
          </el-table-column>
          <el-table-column
            prop="bidEndDate"
            label="截止日期">
          </el-table-column>
          <el-table-column
            prop="status"
            label="业务状态">
            <template slot-scope="scope">
              <span class="status-basic" v-if="scope.row.status === 1 && scope.row.responseStatus === 0">发布中</span>
              <span class="status-basic status-end" v-if="scope.row.status === 2 && scope.row.responseStatus === 0">已截止</span>
              <span class="status-basic status-alreadyOffer" v-if="scope.row.responseStatus === 2">已报价</span>
              <span class="status-basic status-returned" v-if="scope.row.responseStatus === 3">被退回</span>
              <span class="status-basic status-accepted" v-if="scope.row.responseStatus === 4">被接受</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作" align="center" width="300">
            <template slot-scope="scope">
              <el-button class="addButton" type="primary" v-if="scope.row.status === 1 && scope.row.responseStatus === 0" @click="handleOfferBtn(scope)"><i class="basic offer"></i>报价</el-button>
              <el-button class="addButton" type="primary" v-if="scope.row.responseStatus === 2" @click="handleDraftEditBtn(scope)">查看报价</el-button>
              <el-button class="addButton" type="primary" v-if="scope.row.status !== 1 && (scope.row.responseStatus === 0 || scope.row.responseStatus === 1)">停止报价</el-button>
              <el-button class="addButton" type="primary" @click="handleProjectBtn(scope)"><i class="basic project"></i>详情</el-button>
              <el-dropdown trigger="click">
                <el-button class="addButton" type="primary">更多<i class="basic more"></i></el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item><div @click="handleDelBtn(scope)">删除</div></el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </el-table>
        <div class="page-box">
          <el-pagination
            background
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="page.currentPage"
            :page-size="page.pageSize"
            layout="total, prev, pager, next, jumper"
            :total="page.total">
          </el-pagination>
        </div>
      </div>
    </div>
    <!-- 草稿箱弹框-->
    <el-dialog
      title="草稿箱"
      :before-close="cancelDraft"
      :visible.sync="showDraftVisible"
      width="60%">
      <el-table
          border
          header-cell-class-name="table_header"
          :data="draftTableData">
          <el-table-column
            prop="publishTime"
            :formatter="simpleFormatData"
            label="发布时间">
          </el-table-column>
          <el-table-column
            prop="name"
            label="项目名称">
          </el-table-column>
          <el-table-column
            prop="subjectMatterNames"
            label="采购商品">
          </el-table-column>
          <el-table-column
            prop="budget"
            label="采购预算">
          </el-table-column>
          <el-table-column
            prop="bidEndDate"
            label="截止日期">
          </el-table-column>
          <el-table-column
            prop="bidEndDate"
            label="修改日期">
          </el-table-column>
          <el-table-column
            label="操作" align="center" width="120">
            <template slot-scope="scope">
              <template>
                <el-button class="addButton" type="primary" @click="handleDraftEditBtn(scope)"><i class="basic edit"></i>修改</el-button>
                <el-button class="addButton delButton" type="primary" @click="handleDraftDelBtn(scope)"><i class="basic del"></i>删除</el-button>
              </template>
            </template>
          </el-table-column>
        </el-table>
        <div class="page-box">
          <el-pagination
            background
            @size-change="handleDraftSizeChange"
            @current-change="handleDraftCurrentChange"
            :current-page.sync="draftPage.currentPage"
            :page-size="draftPage.pageSize"
            layout="total, prev, pager, next, jumper"
            :total="draftPage.total">
          </el-pagination>
        </div>
    </el-dialog>
  </div>
</template>

<script>
import {projectResponse} from '@/api/project/directPurchase/projectResponse'
import {dateFormat} from '@/assets/js/common'
export default {
  data () {
    return {
      searchForm: {
        projectNameLike: '',
        publishTimeRange: '',
        budgetMin: undefined,
        budgetMax: undefined,
        subjectMattersNameLike: '',
        bidEndDateRange: '',
        includeProjectResponseStatus: [0, 2, 3, 4],
        includeProjectStatus: [1, 2, 3, 4, 5]
      },
      // 默认选中
      addrSelected: null,
      projectStatusOption: [
        {
          name: '全部',
          num: 0,
          includeStatus: [1, 2, 3, 4, 5]
        },
        {
          name: '发布中',
          num: 0,
          includeStatus: [1]
        },
        {
          name: '已报价',
          num: 0,
          includeStatus: [1]
        },
        {
          name: '已截止',
          num: 0,
          includeStatus: [2]
        },
        {
          name: '被退回',
          num: 0,
          includeStatus: [2]
        },
        {
          name: '被接受',
          num: 0,
          includeStatus: [2]
        }
      ],
      draftCount: '', // 报价草稿数量
      showDraftVisible: false,
      tableData: [],
      draftTableData: [],
      page: {
        pageNo: 10,
        pageSize: 20,
        total: 200
      },
      draftPage: {
        pageNo: 10,
        pageSize: 20,
        total: 200
      }
    }
  },
  methods: {
    search () {
      if (this.validatePrice()) {
        this.getTableData()
      }
    },
    reset () {
      this.searchForm.bidEndDateRange = ''
      this.searchForm.publishTimeRange = ''
      this.searchForm.projectNameLike = ''
      this.searchForm.subjectMattersNameLike = ''
      this.searchForm.budgetMax = undefined
      this.searchForm.budgetMin = undefined
      this.getTableData()
    },
    /**
     * 验证项目预算
     * @returns {boolean}
     */
    validatePrice () {
      let start = Number(this.searchForm.budgetMin)
      let end = Number(this.searchForm.budgetMax)
      if ((start !== 0 || end !== 0) && start > end) {
        this.$message({
          type: 'warning',
          message: '最高预算金额不能小于最低预算金额'
        })
        return false
      }
      return true
    },
    handleDraftClick () {
      this.showDraftVisible = true
      let url = {
        pageNo: 0,
        pageSize: 20,
        includeProjectResponseStatus: [1]
      }
      this.getTableData(url)
    },
    handleDraftDelBtn (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        let url = {
          objectId: scope.row.projectResponseId,
          enterpriseId: this.$store.getters.authUser.enterpriseId,
          projectId: scope.row.objectId
        }
        projectResponse.rebuild(url).then((res) => {
          if (res.data.resCode === '0000') {
            this.draftFlag = false
            this.getTableData()
          }
        })
      }).catch(() => {
        return false
      })
    },
    handleProjectBtn (scope) {
      this.$router.push({path: `/supplier/directPurchase/detail/${scope.row.objectId}`})
    },
    handleOfferBtn (scope) {
      this.$router.push({path: `/supplier/directPurchase/offer/${scope.row.objectId}`})
    },
    handleDraftEditBtn (scope) {
      this.$router.push({path: `/supplier/directPurchase/offerDetail/${scope.row.objectId}`})
    },
    handleDelBtn (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        let url = {
          objectId: scope.row.projectResponseId,
          enterpriseId: this.$store.getters.authUser.enterpriseId,
          projectId: scope.row.objectId
        }
        projectResponse.delete(url).then((res) => {
          if (res.data.resCode === '0000') {
            this.getTableData()
          }
        })
      }).catch(() => {
        return false
      })
    },
    // 普通格式化数据，空的时候展示"---"
    simpleFormatData (row, col, cellValue) {
      return cellValue || '---'
    },
    // 分页
    handleCurrentChange (nowNum) {
      this.page.currentPage = nowNum
      this.page.pageNo = (nowNum - 1) * this.page.pageSize
    },
    handleSizeChange (size) {
      this.page.pageSize = size
    },
    // 点击切换搜索
    chooseAddr (index, item) {
      this.addrSelected = index
      this.searchForm.includeStatus = item.code
      this.draftFlag = false
      this.getTableData()
    },
    /** 获取列表数据 */
    getTableData (url) {
      if (!url) {
        url = {
          pageNo: this.page.pageNo,
          pageSize: this.page.pageSize
        }
        // 发布时间范围
        if (this.searchForm.publishTimeRange) {
          this.$set(this.searchForm, 'publishTimeStart', dateFormat(this.searchForm.publishTimeRange[0], 'yyyy-MM-dd hh:mm:ss'))
          this.$set(this.searchForm, 'publishTimeEnd', dateFormat(this.searchForm.publishTimeRange[1], 'yyyy-MM-dd hh:mm:ss'))
        } else {
          this.$set(this.searchForm, 'publishTimeStart', '')
          this.$set(this.searchForm, 'publishTimeEnd', '')
        }
        // 截止时间范围
        if (this.searchForm.bidEndDateRange) {
          this.$set(this.searchForm, 'bidEndDateStart', dateFormat(this.searchForm.bidEndDateRange[0], 'yyyy-MM-dd hh:mm:ss'))
          this.$set(this.searchForm, 'bidEndDateEnd', dateFormat(this.searchForm.bidEndDateRange[0], 'yyyy-MM-dd hh:mm:ss'))
        } else {
          this.$set(this.searchForm, 'bidEndDateStart', '')
          this.$set(this.searchForm, 'bidEndDateEnd', '')
        }
        url = Object.assign(this.searchForm, url)
      }
      projectResponse.directList(url).then((res) => {
        this.tableData = []
        res.data.projectList.list.forEach(item => {
          this.$set(item.project, 'responseStatus', item.status)
          this.$set(item.project, 'projectResponseId', item.objectId)
          this.tableData.push(item.project)
        })
        this.page.pageNo = res.data.projectList.pageNum
        this.page.pageSize = res.data.projectList.pageSize
        this.page.total = res.data.projectList.total
        this.projectStatusOption[0].num = res.data.totalCount
        this.projectStatusOption[1].num = res.data.publishingCount
        this.projectStatusOption[2].num = res.data.offeringCount
        this.projectStatusOption[3].num = res.data.timeEndingCount
        this.projectStatusOption[4].num = res.data.sendBackCount
        this.projectStatusOption[5].num = res.data.offerSuccessCount
        this.draftCount = res.data.draftCount
      })
    },
    // 分页
    handleDraftCurrentChange (nowNum) {
      this.draftPage.currentPage = nowNum
      this.draftPage.pageNo = (nowNum - 1) * this.draftPage.pageSize
    },
    handleDraftSizeChange (size) {
      this.draftPage.pageSize = size
    },
    cancelDraft () {
      this.showDraftVisible = false
      this.draftPage.currentPage = 1
    }
  },
  mounted () {
    this.getTableData()
  }
}
</script>

<style lang="less">
  #sdirectPurchase{
    .top {
      box-sizing: border-box;
      padding: 10px;
      border-bottom: 1px solid #e6e6e6;
      margin-bottom: 18px;
    }
    .top .title {
      font-size: 14px;
      font-weight: bold;
      padding: 0 10px;
      margin: 0 auto;
      height: 30px;
      line-height: 30px;
    }
    .add {
      float: right;
    }
    .add .draft {
      height: 30px;
      line-height: 30px;
      margin-right: 12px;
      text-align: center;
      cursor: pointer;
    }
    .add .draft i{
      width: 12px;
      height: 12px;
      display: inline-block;
      margin-right: 6px;
      vertical-align: middle;
      background: url("../../../../../static/images/directPurchase/draft.png") center center no-repeat;
    }
    .add .draft .list{
      width: 12px;
      height: 12px;
      display: inline-block;
      margin-right: 6px;
      vertical-align: middle;
      background: url("../../../../../static/images/directPurchase/liebiao.png") center center no-repeat;
    }
    .add .draft span{
      display: inline-block;
      vertical-align: middle;
    }
    .status-basic::before{
      display: inline-block;
      content: '';
      height: 6px;
      width: 6px;
      border-radius: 6px;
      background-color: #2789ba;
      margin-right: 6px;
      vertical-align: middle;
    }
    .status-alreadyOffer::before{
      background-color: #27ba5b;
    }
    .status-end::before{
      background-color: #999999;
    }
    .status-returned:before{
      background-color: #ff0000;
    }
    .status-accepted:before{
      background-color: #ffae00;
    }
    .basic {
      width: 12px;
      height: 12px;
      display: inline-block;
      margin-right: 6px;
      vertical-align: middle;
    }
    .edit {
      background: url("../../../../../static/images/directPurchase/edit.png") center center no-repeat;
    }
    .del {
      height: 14px;
      background: url("../../../../../static/images/directPurchase/del.png") center center no-repeat;
    }
    .project {
      background: url("../../../../../static/images/directPurchase/project.png") center center no-repeat;
    }
    .offer {
      background: url("../../../../../static/images/directPurchase/offer.png") center center no-repeat;
      margin-right: 10px;
    }
    .more {
      height: 6px;
      background: url("../../../../../static/images/directPurchase/more.png") center center no-repeat;
      margin-right: 0;
      margin-left: 6px;
    }
    .list-box {
      box-sizing: border-box;
      padding: 0 10px;
    }
    .page-box {
      padding: 20px 10px 40px 10px;
      box-sizing: border-box;
      text-align: right;
    }
    .delButton{
      background: #ff4100;
      border-color: #ff4100;
    }
    .el-button+.el-button{
      margin-left: 0;
    }
  }
  .el-dropdown-menu__item:hover{
    background: none!important;
    color: #666666!important;
  }
</style>
