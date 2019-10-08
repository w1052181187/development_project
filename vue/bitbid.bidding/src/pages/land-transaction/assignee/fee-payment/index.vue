<template>
  <div id="assindexpayindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>费用缴纳</el-breadcrumb-item>
        <el-breadcrumb-item>缴纳竞买保证金</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--添加按钮-->
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
            <el-input class="search" style="vertical-align: top"  :placeholder="'请输入' + seacherName" v-model="seacherInput">
              <el-button  slot="append" icon="el-icon-search" @click="search"></el-button>
            </el-input>
          </div>
        </el-row>
        <!--添加按钮-->
        <!--主要内容 table-->
        <el-table
          :data="applytableData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="landInformation.sectionNumber"
            label="地块编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="landInformation.landAnnoMation.annoNumber"
            label="公告名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="landInformation.bidBond"
            label="缴费金额（万元）"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="payStatus"
            label="缴费状态"
            align="left"
            width="160"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.payStatus === 0">
                <span class="statusbox">未支付</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.payStatus === 1">
                <span class="statusbox">已支付</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="payMethod"
            label="缴费方式"
            align="left"
            width="160"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.payMethod === ''">
                <span class="statusbox">------</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.payMethod === 0">
                <span class="statusbox">在线缴纳</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.payMethod === 1">
                <span class="statusbox">保函</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.payMethod === 2">
                <span class="statusbox">其他</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.payMethod === 3">
                <span class="statusbox">支票</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.payMethod === 4">
                <span class="statusbox">线下汇款</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="lastUpdateDate"
            label="到账时间"
            align="left"
            width="160"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            label="操作" width="160" align="center">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.payStatus === 0">
                <el-button type="text" size="small" @click="paymentBtn(scope)">
                  支付
                </el-button>
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  详情
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.payStatus === 1">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  详情
                </el-button>
                <div class="statusbigbox" v-if="scope.row.payMethod === 0">
                <el-button type="text" size="small" @click="lookReceipt(scope)">
                  查看回执
                </el-button>
              </div>
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
import { apply } from '@/api'
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
      value: 1,
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      applytableData: []
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
        type: 2
      }
      if (this.seacherInput !== '') { // 当搜索内容不为空时执行
        if (this.seacherName === '公告名称') {
          url.annoName = this.seacherInput
        } else if (this.seacherName === '地块编号') {
          url.sectionNumber = this.seacherInput
        }
      }
      apply.applyList(url).then((res) => {
        console.log(res.data.ApplyList.list)
        this.applytableData = res.data.ApplyList.list
        this.total = res.data.ApplyList.total
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
    // 详情
    detailBtn (scope) {
      this.$router.push({path: `/lt/assignee/fee-payment/detail/${scope.row.objectId}`, query: {sectionId: scope.row.sectionId, roledsType: 2}})
    },
    // 查看回执
    lookReceipt (scope) {
      this.$router.push({path: `/lt/assignee/fee-payment/receipt/${scope.row.objectId}`, query: {objectId: scope.row.objectId, sectionId: scope.row.sectionId, roledsType: 2}})
    },
    // 支付
    paymentBtn (scope) {
      let times = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate() + ' ' + new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds()
      this.$router.push({path: '/lt/assignee/fee-payment/payment', query: {objectId: scope.row.objectId, bidBond: scope.row.landInformation.bidBond, times: times, roledsType: 2}})
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/land-register/common.css';
</style>
