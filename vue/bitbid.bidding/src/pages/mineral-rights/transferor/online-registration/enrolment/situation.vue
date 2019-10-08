<template>
    <div id="situation"  class="maincontent">
      <div class="headertitle">
        <!--面包屑-->
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>网上报名</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/mr/transferor/online-registration/enrolment?roledsType=1' }">报名受理</el-breadcrumb-item>
          <el-breadcrumb-item>报名情况</el-breadcrumb-item>
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
            :data="registrationtableData"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="bidderName"
              label="竞买人"
              align="left"
              width="300">
            </el-table-column>
            <el-table-column
              prop="contacts"
              label="联系人"
              align="left"
              width="200">
            </el-table-column>
            <el-table-column
              prop="contactsNumber"
              label="联系电话"
              align="left">
            </el-table-column>
            <el-table-column
              prop="email"
              label="邮箱"
              align="left">
            </el-table-column>
            <el-table-column
              prop="createDate"
              label="报名时间"
              align="left">
            </el-table-column>
            <el-table-column
              prop="acceptResults"
              label="受理状态"
              align="left">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.acceptResults === 0">
                  <span class="statusbox red"><i></i>已驳回</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.acceptResults === 1">
                  <span class="statusbox green"><i></i>已受理</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.acceptResults === 2">
                  <span class="statusbox blue"><i></i>受理中</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作" width="140" align="center">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.acceptResults === 0">
                  <el-button type="text" size="small" @click="detailBtn(scope)">
                    查看
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.acceptResults === 1">
                  <el-button type="text" size="small" @click="detailBtn(scope)">
                    查看
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.acceptResults === 2">
                  <el-button type="text" size="small"  @click="enrolmenetBtn(scope)">
                    受理
                  </el-button>
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
      registrationtableData: [],
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
          messageLike: this.$route.query.ObjectId
        }
        if (this.seacherName == '竞买人名称') {
          url.bidderNames = this.seacher_input
        }
      } else {
        url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0,
          messageLike: this.$route.query.ObjectId
        }
      }
      apply.applyList(url).then((res) => {
        this.registrationtableData = res.data.ApplyList.list
        this.total = res.data.ApplyList.total
      })
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      let searchs = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0,
        messageLike: this.$route.query.ObjectId
      }
      if (this.seacherName == '竞买人名称') {
        searchs.bidderNames = this.seacher_input
      }
      apply.applyList(searchs).then((res) => {
        this.registrationtableData = res.data.ApplyList.list
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
    // 受理
    enrolmenetBtn (scope) {
      this.$router.push({path: `/mr/transferor/online-registration/enrolment/enrolment/${scope.row.objectId}`, query: {sitObjectId: this.$route.query.ObjectId, roledsType: 1}})
    },
    // 查看
    detailBtn (scope) {
      this.$router.push({path: `/mr/transferor/online-registration/enrolment/detail/${scope.row.objectId}`, query: {sitObjectId: this.$route.query.ObjectId, roledsType: 1}})
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
