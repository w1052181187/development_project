<template>
  <div id="payindexbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>费用缴纳情况查询</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/transferor/fee-payment?roledsType=1' }">查看竞买保证金缴纳情况</el-breadcrumb-item>
        <el-breadcrumb-item>缴纳情况</el-breadcrumb-item>
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
            <el-input class="search" style="vertical-align: top"  :placeholder="'请输入' + seacherName" v-model="seacher_input">
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
            prop="section.sectionNumber"
            label="项目编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="section.sectionName"
            label="项目名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="bidderName"
            label="竞买人"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="section.bidBond"
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
                <span class="statusbox">---------</span>
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
                <el-button type="text" size="small" @click="approveBtn(scope)">
                  审批
                </el-button>
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  详情
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.payStatus === 1">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  详情
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
import { apply } from '@/api'
export default {
  data () {
    return {
      seacher_input: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '项目名称',
      options: [ {
        value: 1,
        label: '项目名称'
      }, {
        value: 2,
        label: '项目编号'
      }],
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      value: 1,
      searchIs: false,
      applytableData: []
    }
  },
  methods: {
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.queryList(this.searchIs)
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.queryList(this.searchIs)
    },
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '项目名称'
      } else {
        this.seacherName = '项目编号'
      }
    },
    // 详情
    detailBtn (scope) {
      this.$router.push({path: '/mr/transferor/fee-payment/pay-situation/detail', query: {objectId: scope.row.objectId, sectionId: scope.row.sectionId, roledsType: 1}})
    },
    approveBtn (scope) {
      this.$router.push({path: '/mr/transferor/fee-payment/pay-situation/approve', query: {objectId: scope.row.objectId, sectionId: scope.row.sectionId, payStatus: scope.row.payStatus, roledsType: 1}})
    },
    // 根据标段id查询所有报名信息
    queryList (search) {
      let url = null
      if (this.searchIs) {
        url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0
        }
        if (this.seacherName == '项目名称') {
          url.sectionName = this.seacher_input
        } else if (this.seacherName == '项目编号') {
          url.sectionNumber = this.seacher_input
        }
      } else {
        url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0,
          sectionId: this.$route.query.objectId
        }
      }
      apply.applyList(url).then((res) => {
        this.applytableData = res.data.ApplyList.list
        this.total = res.data.ApplyList.total
        this.applytableData.map((ite) => {
          if (ite.payStatus === 0) {
            ite.lastUpdateDate = ''
          }
        })
      })
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      let searchs = null
      searchs = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0,
        sectionId: this.$route.query.objectId
      }
      if (this.seacherName == '项目名称') {
        searchs.sectionName = this.seacher_input
      } else if (this.seacherName == '项目编号') {
        searchs.sectionNumber = this.seacher_input
      }
      apply.applyList(searchs).then((res) => {
        this.applytableData = res.data.ApplyList.list
        this.searchIs = true
      })
    }
  },
  mounted () {
    this.queryList()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
</style>
