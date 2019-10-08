<template>
  <div id="issuemissionindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>成交确认</el-breadcrumb-item>
        <el-breadcrumb-item>发出成交确认书</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--搜索按钮-->
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
        <!--搜索按钮-->
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
            label="竞得人"
            align="left"
            width="200"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="isSend"
            label="发送状态"
            align="left"
            width="200"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.isCommit === 0">
                <span class="statusbox">未提交</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.isCommit === 1 && scope.row.isSend === 0">
                <span class="statusbox">未发送</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.isCommit === 1 && scope.row.isSend === 1 && scope.row.isAffirm === 0">
                <span class="statusbox">待确认</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.isCommit === 1 && scope.row.isSend === 1 && scope.row.isAffirm === 1">
                <span class="statusbox">已确认</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="sendTime"
            label="提交时间"
            align="left"
            width="200"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            label="操作" width="160" align="center">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.isCommit === 0">
                <el-button type="text" size="small" @click="submissionBtn(scope)">
                  提交
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.isCommit === 1 && scope.row.isSend === 0">
                <el-button type="text" size="small" @click="sendOutBtn(scope)">
                  发送
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.isCommit === 1 && scope.row.isSend === 1 && scope.row.isAffirm === 0">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.isCommit === 1 && scope.row.isSend === 1 && scope.row.isAffirm === 1">
                <el-button type="text" size="small" @click="detailBtn(scope)">
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
import {salesCon} from '@/api'
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
      value: 1,
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      projecttableData: []
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
        isPersonAffirm: 1,
        isAffirmPerson: 1,
        type: 2
      }
      if (this.seacherInput !== '') { // 当搜索内容不为空时执行
        if (this.seacherName === '公告名称') {
          url.annoName = this.seacherInput
        } else if (this.seacherName === '地块编号') {
          url.sectionNumber = this.seacherInput
        }
      }
      salesCon.confirmList(url).then((res) => {
        this.projecttableData = res.data.salesConfirmationPageInfo.list
        this.total = res.data.salesConfirmationPageInfo.total
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
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '公告名称'
      } else {
        this.seacherName = '地块编号'
      }
    },
    // 提交
    submissionBtn (scope) {
      this.$router.push({path: `/lt/transferor/business-confirmation/issue-sales-confirmation/submission/${scope.row.objectId}`, query: {roledsType: 1}})
    },
    // 发送
    sendOutBtn (scope) {
      let times = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate() + ' ' + new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds()
      this.$router.push({path: `/lt/transferor/business-confirmation/issue-sales-confirmation/send-out/${scope.row.objectId}`, query: {times: times, roledsType: 1}})
    },
    // 查看
    detailBtn (scope) {
      this.$router.push({path: `/lt/transferor/business-confirmation/issue-sales-confirmation/detail/${scope.row.objectId}`, query: {roledsType: 1}})
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
