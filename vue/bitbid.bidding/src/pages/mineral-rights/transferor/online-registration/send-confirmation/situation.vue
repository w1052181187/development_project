<template>
    <div id="sendsituation" class="maincontent">
      <div class="headertitle">
        <!--面包屑-->
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/mr/transferor/online-registration/send-confirmation?roledsType=1' }">发送资格确认书</el-breadcrumb-item>
          <el-breadcrumb-item>查看发送情况</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
      </div>
      <div class="contentbigbox">
        <template>
          <!--竞买人名称搜索-->
          <el-row>
            <div class="seacher_box">
              <el-select v-model="value" class="select">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
              <el-input class="search" style="vertical-align: top"  :placeholder="'请输入' + seacherName" v-model="seacher_input">
                <el-button  slot="append" icon="el-icon-search" type="" @click="search"></el-button>
              </el-input>
            </div>
          </el-row>
          <!--项目名称  项目编号搜索-->
          <!--主要内容 table-->
          <el-table
            :data="sendtableData"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="bidderName"
              label="竞买人"
              align="left"
              width="400">
            </el-table-column>
            <el-table-column
              prop="payStatus"
              label="竞买保证金缴纳情况"
              align="left">
              <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.payStatus === 0">
                <span class="statusbox red"><i></i>未缴纳</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.payStatus === 1">
                <span class="statusbox green"><i></i>已缴纳</span>
              </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="isSend"
              label="发送状态"
              align="left">
              <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.isSend === 0">
                <span class="statusbox red"><i></i>未发送</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.isSend === 1">
                <span class="statusbox green"><i></i>已发送</span>
              </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="qualiConfirmation.sendTime"
              label="发送时间"
              align="left">
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作" width="140" align="center">
              <template slot-scope="scope">
                  <el-button type="text" size="small" v-if="scope.row.payStatus === 0">
                    -------
                  </el-button>
                  <el-button type="text" size="small"  @click="sendBtn(scope)" v-if="scope.row.payStatus === 1 && scope.row.isSend === 0">
                    发送
                  </el-button>
                  <el-button type="text" size="small" @click="detailBtn(scope)" v-if="scope.row.payStatus === 1 && scope.row.isSend === 1">
                    查看
                  </el-button>
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
import {apply} from '@/api'
export default {
  data () {
    return {
      seacher_input: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '竞买人名称',
      options: [{
        value: 1,
        label: '竞买人名称'
      }],
      value: 1,
      sendtableData: [],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 0, // 总条数
      pageSize: 10, // 每页展示条数
      searchIs: false
    }
  },
  methods: {
    list () {
      let url = null
      if (this.searchIs) {
        url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0,
          sectionId: this.$route.params.objectId
        }
        if (this.seacherName == '竞买人名称') {
          url.bidderNames = this.seacher_input
        }
      } else {
        url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0,
          sectionId: this.$route.params.objectId
        }
      }
      apply.applyListConQua(url).then((res) => {
        this.sendtableData = res.data.ApplyList.list.map((itm) => {
          if (itm.qualiConfirmation !== null) {
            itm.isSend = itm.qualiConfirmation.isSend
          }
          return itm
        })
        this.total = res.data.ApplyList.total
      })
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      let searchs = null
      searchs = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        sectionId: this.$route.params.objectId
      }
      if (this.seacherName == '竞买人名称') {
        searchs.bidderNames = this.seacher_input
      }
      apply.applyListConQua(searchs).then((res) => {
        this.sendtableData = res.data.ApplyList.list.map((itm) => {
          if (itm.qualiConfirmation !== null) {
            itm.isSend = itm.qualiConfirmation.isSend
          }
          return itm
        })
        this.total = res.data.ApplyList.total
        this.searchIs = true
      })
    },
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list(this.searchIs)
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list(this.searchIs)
    },
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '竞买人名称'
      }
    },
    // 发送
    sendBtn (scope) {
      this.$router.push({path: '/mr/transferor/online-registration/send-confirmation/send', query: {ObjectId: scope.row.section.objectId, Bidder: scope.row.qualiConfirmation.bidder, situationObjectId: this.$route.params.objectId, roledsType: 1}})
    },
    // 查看
    detailBtn (scope) {
      this.$router.push({path: '/mr/transferor/online-registration/send-confirmation/senddetail', query: {ObjectId: scope.row.section.objectId, Bidder: scope.row.qualiConfirmation.bidder, situationObjectId: this.$route.params.objectId, roledsType: 1}})
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
