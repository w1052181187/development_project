<template>
    <div id="onlineregistration"  class="maincontent">
      <div class="headertitle">
        <!--面包屑-->
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>网上报名</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
      </div>
      <div class="contentbigbox">
        <template>
          <!--项目名称  项目编号搜索-->
          <el-row>
            <el-button type="success"  @click="signUpBtn" class="addbutton">报名</el-button>
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
              prop="landInformation.landAnnoMation.annoNumber"
              label="公告名称"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="landInformation.sectionNumber"
              label="地块编号"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="methodOfTransfer"
              label="出让方式"
              align="left">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === null">
                  <span class="statusbox">----</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 1">
                  <span class="statusbox">拍卖</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 2">
                  <span class="statusbox">挂牌</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 3">
                  <span class="statusbox">资格后审</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 4">
                  <span class="statusbox">资格预审</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="acceptResults"
              label="报名状态"
              align="left">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.acceptResults === 0">
                  <span class="statusbox red"><i></i>已驳回</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.acceptResults === 1">
                  <span class="statusbox green"><i></i>已通过</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.acceptResults === 2">
                  <span class="statusbox blue"><i></i>已报名</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作" width="200" align="center">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.acceptResults === 2">
                  <el-button type="text" size="small" @click="updateBtn(scope)">
                    修改
                  </el-button>
                  <el-button type="text" size="small" @click="detailBtn(scope)">
                    查看
                  </el-button>
                  <el-button type="text" size="small" @click="detailReceiptBtn(scope)">
                    查看回执
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.acceptResults === 1">
                  <el-button type="text" size="small" @click="detailBtn(scope)">
                    查看
                  </el-button>
                  <el-button type="text" size="small" @click="detailReceiptBtn(scope)">
                    查看回执
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.acceptResults === 0">
                  <el-button type="text" size="small" @click="updateBtn(scope)">
                    修改
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
import {apply} from '@/api'
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
      registrationtableData: [],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 0, // 总条数
      pageSize: 10 // 每页展示条数
    }
  },
  methods: {
    // ***************************************************************数据接口方法**********************************************************************
    list () {
      let url = null
      url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0,
        messageLike: this.$route.query.ObjectId,
        assigneeId: this.$store.getters.authUser.userId,
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
        this.registrationtableData = res.data.ApplyList.list
        this.registrationtableData.map((ite) => {
          if (ite.landInformation) {
            ite.methodOfTransfer = ite.landInformation.methodOfTransfer
          }
        })
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
    // 报名按钮
    signUpBtn () {
      this.$router.push({path: '/lt/assignee/online-registration/online-registration/choose-project?roledsType=2'})
    },
    // 修改
    updateBtn (scope) {
      this.$router.push({path: '/lt/assignee/online-registration/look-registration/update', query: {roledsType: 2, objectId: scope.row.objectId}})
    },
    // 查看
    detailBtn (scope) {
      this.$router.push({path: `/lt/assignee/online-registration/look-registration/detail/${scope.row.objectId}`, query: {roledsType: 2}})
    },
    // 查看回执
    detailReceiptBtn (scope) {
      this.$router.push({path: '/lt/assignee/online-registration/look-registration/see-receipt', query: {objectId: scope.row.objectId, acceptResults: scope.row.acceptResults, roledsType: 2}})
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
