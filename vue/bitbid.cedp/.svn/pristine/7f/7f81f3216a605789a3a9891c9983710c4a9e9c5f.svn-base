<template>
  <div class="offerDetail blueCon" id="offerDetail">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/'}">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/'}">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/projectExecute'}">项目执行</el-breadcrumb-item>
      <el-breadcrumb-item>直采报价</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="offer-title">{{offerTitle}}</div>
    <el-form class="top-search" :model="searchForm" ref="searchForm" label-width="90px" style="margin-top: 5px">
      <el-row>
        <el-col :span="8">
          <el-form-item label="公司名称">
            <el-input placeholder="请输入公司名称关键字" v-model="searchForm.tenderProjectNameLike"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="报价时间">
            <el-date-picker
              v-model="searchForm.publishTime"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="供货地址" class="gonghuo-dizhi">
            <el-input placeholder="请输入供货地址关键字" v-model="searchForm.tenderProjectNameLike"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="报价物料">
            <el-input placeholder="请输入报价物料关键字" v-model="searchForm.tenderProjectNameLike"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="报价金额">
            <el-input-number v-model="searchForm.priceStart" :controls="false"></el-input-number>
            <span>-</span>
            <el-input-number v-model="searchForm.priceEnd" :controls="false"></el-input-number>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="报价状态">
            <span v-for="(item,index) in projectStatusOption"
                  :key="index"
                  class="price-status"
                  :class="{'curr' : index === addrSelected}"
                  @click="chooseAddr(index, item)">
                   {{item.name}}<span class="price-num" :class="{'curr' : index === addrSelected}">({{item.num}})</span>
            </span>
          </el-form-item>
        </el-col>
        <el-col :span="16">
          <div class="handle-btn">
            <el-button @click="reset">重置</el-button>
            <el-button class="addButton" type="primary" @click="search">查询</el-button>
          </div>
        </el-col>
      </el-row>
    </el-form >
    <div class="mainContent">
      <div class="top">
        <span class="title">{{curName}}</span>
        <div class="add">
          <el-button class="addButton" type="primary" @click="handleReturn">返回项目列表</el-button>
        </div>
      </div>
      <div class="tablemain product-table">
        <el-table
          :data="tableData"
          border
          :row-class-name="tableRowClassName"
          header-cell-class-name="offerList_header"
          style="width: 100%">
          <el-table-column
            prop="name"
            label="公司名称">
          </el-table-column>
          <el-table-column
            prop="name"
            label="供货地址">
          </el-table-column>
          <el-table-column
            prop="name"
            label="报价物料">
          </el-table-column>
          <el-table-column
            prop="name"
            label="报价时间">
          </el-table-column>
          <el-table-column
            prop="status"
            label="报价状态">
            <template slot-scope="scope">
              <span class="status-basic" v-if="scope.row.status === 0">已报价</span>
              <span class="status-basic status-end" v-if="scope.row.status === 1">已退回</span>
              <span class="status-basic status-complete" v-if="scope.row.status === 2">已选择</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作" align="center" width="180">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="detailBtn(scope)">查看</el-button>
              <el-button type="text" size="small" v-if="scope.row.status === 0">退回</el-button>
              <el-button type="text" size="small" v-if="scope.row.status === 1 && scope.row.status !== 2" disabled>退回</el-button>
              <el-button type="text" size="small" v-if="scope.row.status === 2">评价</el-button>
              <el-button type="text" size="small" >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="page-box">
          <el-pagination
            background
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="page.currentPage"
            :page-sizes="[10, 20, 30, 40]"
            :page-size="page.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="page.total">
          </el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      searchForm: {
        priceStart: null,
        priceEnd: null
      },
      // 默认选中
      addrSelected: null,
      curName: '报价列表',
      tableData: [
        {
          name: '公司名称',
          status: 0,
          objectId: 1000
        },
        {
          name: '公司名称',
          status: 1,
          objectId: 1000
        },
        {
          name: '公司名称',
          status: 2,
          objectId: 1000
        },
        {
          name: '公司名称',
          status: 0,
          objectId: 1000
        }
      ],
      projectStatusOption: [
        {
          name: '全部',
          num: 12,
          code: '0',
          objectId: 1000
        },
        {
          name: '已报价',
          num: 12,
          code: '1',
          objectId: 1000
        },
        {
          name: '已退回',
          num: 12,
          code: '2',
          objectId: 1000
        },
        {
          name: '已选择',
          num: 12,
          code: '3',
          objectId: 1000
        }
      ],
      page: {
        pageNo: 10,
        pageSize: 20,
        total: 200
      },
      offerTitle: '中铁十一局六公司乐平红岩旅游APP项目铸铁雨水井盖询价单'
    }
  },
  methods: {
    // 搜索
    search () {
      if (this.validatePrice()) {
      }
    },
    // 重置
    reset () {},
    // 验证
    validatePrice () {
      if ((this.searchForm.priceStart || this.searchForm.priceStart) && this.searchForm.priceStart > this.searchForm.priceEnd) {
        this.$message({
          type: 'warning',
          message: '结束价格不能小于开始价格'
        })
        return false
      }
      return true
    },
    // 分页
    handleCurrentChange (nowNum) {
      this.page.currentPage = nowNum
      this.page.pageNo = (nowNum - 1) * this.page.pageSize
    },
    handleSizeChange (size) {
      this.page.pageSize = size
    },
    // 返回项目列表
    handleReturn () {
      this.$router.push({path: `/projectExecute`})
    },
    // 点击切换搜索
    chooseAddr (index, item) {
      this.addrSelected = index
      this.searchForm.status = item.code
    },
    // 表格隔行换色
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 === 0) {
        return 'warning-row'
      } else {
        return 'success-row'
      }
    },
    // 查看
    detailBtn (scope) {
      this.$router.push({path: `/projectExecute/directPurchase/offer/detail/${scope.row.objectId}`})
    }
  }
}
</script>

<style lang="less">
  #offerDetail{
    .offer-title{
      width: 100%;
      background: #fff;
      height: 34px;
      text-align: center;
      color: #2789ba;
      font-size: 16px;
      line-height: 34px;
    }
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
    .offerList_header{
      height: 45px;
      color: #696c71;
    }
    .product-table{
      padding-bottom: 15px;
    }
    .tablemain .el-table td {
      border-bottom: 1px solid #e6e6e6;
      padding: 6px 0 6px 0;
      color: #333333;
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
    .status-end::before{
      background-color: #ff4100;
    }
    .status-complete::before{
      background-color: #27ba5b;
    }
    .page-box {
      padding: 20px 10px 40px 10px;
      box-sizing: border-box;
      text-align: right;
    }
    .gonghuo-dizhi .el-input{
      width: 95%;
    }
    .el-input-number{
      width: 168px;
    }
    .el-table .warning-row{
      background: #f3f7fb;
    }

    .el-table .success-row{
      background: #ffffff;
    }
  }
</style>
