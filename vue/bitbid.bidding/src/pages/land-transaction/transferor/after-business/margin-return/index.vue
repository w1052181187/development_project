<template>
  <div id="marginretmissionindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>标后业务</el-breadcrumb-item>
        <el-breadcrumb-item>保证金退回</el-breadcrumb-item>
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
          :data="projecttableData"
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
            prop="bidderName"
            label="竞买人"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="selectSectionWinStatus"
            label="中标状态"
            align="left"
            width="160"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.SectionWinStatus === 0">
                <span class="statusbox">暂无结果</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.SectionWinStatus === 1">
                <span class="statusbox">是</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.SectionWinStatus === 2">
                <span class="statusbox">否</span>
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
            prop="status"
            label="退回状态"
            align="left"
            width="160"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === ''">
                <span class="statusbox">未申请</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <span class="statusbox">已申请</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
                <span class="statusbox">已退款</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 3">
                <span class="statusbox">审批不通过</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="操作" width="160" align="center">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === ''">
                <el-button type="text" size="small" @click="refundBtn(scope)">
                  退款
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <el-button type="text" size="small" @click="approveBtn(scope)">
                  审批
                </el-button>
                <el-button type="text" size="small" @click="lookeBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
                <el-button type="text" size="small" @click="lookeBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 3">
                <el-button type="text" size="small" @click="lookeBtn(scope)">
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
import { refundedSecurity } from '@/api'
export default {
  data () {
    return {
      seacherInput: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '公告名称',
      options: [ {
        value: 1,
        label: '公告名称'
      }, {
        value: 2,
        label: '地块编号'
      }],
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      value: 1,
      projecttableData: [],
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
        type: 2
      }
      if (this.seacherInput !== '') { // 当搜索内容不为空时执行
        if (this.seacherName === '公告名称') {
          url.annoName = this.seacherInput
        } else if (this.seacherName === '地块编号') {
          url.sectionNumber = this.seacherInput
        }
      }
      refundedSecurity.queryList(url).then((res) => {
        this.projecttableData = res.data.selectListApplyPageInfo.list.map((itm) => {
          itm.status = itm.refundedSecurity === null ? '' : itm.refundedSecurity.status
          return itm
        })
        this.projecttableData.map((ite) => {
          if (ite.landInformation.salesConfirmation === null) {
            ite.SectionWinStatus = 0
          } else if (ite.landInformation.salesConfirmation.bidderName === ite.bidderName) {
            ite.SectionWinStatus = 1
          } else {
            ite.SectionWinStatus = 2
          }
        })
        this.total = res.data.selectListApplyPageInfo.total
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
    // -----------------------------------------搜索-------------------------------------------
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.list()
    },
    // ***************************************************************自己方法**********************************************************************
    // 退款
    refundBtn (scope) {
      this.$router.push({path: '/lt/transferor/after-business/margin-return/refund', query: {objectId: scope.row.objectId, sectionId: scope.row.landInformation.objectId, assigneeId: scope.row.assigneeId, roledsType: 1}})
    },
    // 审批
    approveBtn (scope) {
      this.$router.push({path: '/lt/transferor/after-business/margin-return/approve', query: {objectId: scope.row.refundedSecurity.objectId, payMethod: scope.row.payMethod, roledsType: 1}})
    },
    // 查看
    lookeBtn (scope) {
      this.$router.push({path: `/lt/transferor/after-business/margin-return/detail/${scope.row.refundedSecurity.objectId}`, query: {payMethod: scope.row.payMethod, roledsType: 1}})
    },
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '公告名称'
      } else {
        this.seacherName = '地块编号'
      }
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
</style>
