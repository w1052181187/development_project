<template>
  <div id="situation" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>矿权交易备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/mineral-rights/qualification-confirmation' }">资格确认情况查看</el-breadcrumb-item>
        <el-breadcrumb-item>资格确认详情</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <router-link tag='span' to="/system-management/mineral-rights/qualification-confirmation">返回</router-link>
      </div>
    </div>
    <div class="contentbigbox">
      <template>
        <!--竞买人名称搜索-->
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
            align="left">
          </el-table-column>
          <el-table-column
            prop="contacts"
            label="联系人"
            align="left">
          </el-table-column>
          <el-table-column
            prop="contactsNumber"
            label="联系电话"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="email"
            label="邮箱"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="lastUpdateDate"
            label="报名时间"
            align="left"
            width="160">
          </el-table-column>
          <el-table-column
            prop="payStatus"
            label="竞买保证金缴纳情况"
            align="left"
            width="160">
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
            prop="qualiConfirmation.isSend"
            label="资格确认书发送情况"
            align="left"
            width="160">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.qualiConfirmation.isSend === 0">
                <span class="statusbox red"><i></i>未发送</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.qualiConfirmation.isSend === 1">
                <span class="statusbox green"><i></i>已发送</span>
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
import {apply} from '@/api/public-resource/mineral-rights/apply/index'
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
      total: 50, // 总条数
      pageSize: 10 // 每页展示条数
    }
  },
  methods: {
    list () {
      let url = null
      url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0,
        sectionId: this.$route.params.objectId
      }
      apply.applyListConQua(url).then((res) => {
        this.sendtableData = res.data.ApplyList.list
        this.sendtableData.map((ite) => {
          if (ite.email === null) {
            ite.email = '---'
          }
        })
        this.total = res.data.ApplyList.total
      })
    },
    search () {
      let searchs = null
      searchs = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        _t: new Date().getTime(),
        sectionId: this.$route.params.objectId
      }
      // 通过不同的查询进行参数赋值
      if (this.seacherName == '') {
        this.$message({
          type: 'warning',
          message: '请选择查询方式'
        })
        return false
      } else if (this.seacherName == '竞买人名称') {
        searchs.bidderName = this.seacher_input
      }
      apply.applyListConQua(searchs).then((res) => {
        this.sendtableData = res.data.ApplyList.list
        this.total = res.data.ApplyList.total
      })
    },
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '竞买人名称'
      }
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
</style>
