<template>
  <div id="submissionindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让公告</el-breadcrumb-item>
        <el-breadcrumb-item>提交出让公告</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--添加按钮-->
        <el-row>
          <el-button type="success" @click="addBtn" class="addbutton"> + 添加</el-button>
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
          :data="projecttableData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="noticeName"
            label=""
            align="center"
            width="50">
            <template slot-scope="scope">
              <i class="morebtn el-icon-arrow-right" @click="btnOpen($event)"></i>
            </template>
          </el-table-column>
          <el-table-column
            prop="annoNumber"
            label="公告名称"
            align="left"
            width="350"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="sectionNumber"
            label="项目编号"
            align="left"
            width="200"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span class="jfbdbox" v-for="(item,index) in scope.row.sections" :key="index">
                <span class="xmbh_box" :title="item.sectionNumber">{{item.sectionNumber}}</span>
              </span>
            </template>
          </el-table-column>
          <el-table-column
            prop="sectionName"
            label="项目名称"
            align="left"
            width="350"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span class="jfbdbox" v-for="(item,index) in scope.row.sections" :key="index">
                <span class="xmbh_box" :title="item.sectionName">{{item.sectionName}}</span>
              </span>
            </template>
          </el-table-column>
          <el-table-column
            prop="landUse"
            label="出让方式"
            align="left"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.landUse === 1">
                <span class="statusbox">拍卖</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.landUse === 2">
                <span class="statusbox">挂牌</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.landUse === 3">
                <span class="statusbox">其他</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="approvrResult"
            label="公告状态"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 0">
                <span class="statusbox">未提交</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <span class="statusbox">备案审批中</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
                <span class="statusbox">审批通过</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 3">
                <span class="statusbox">审批不通过</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 4">
                <span class="statusbox">已发布</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="操作" width="140" align="center">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 0">
                <el-button type="text" size="small" @click="updateBtn(scope)">
                  修改
                </el-button>
                <el-button type="text" size="small" @click="deleteBtn(scope)">
                  删除
                </el-button>
                <el-button type="text" size="small" @click="lookBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <el-button type="text" size="small" @click="lookBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 3">
                <el-button type="text" size="small" @click="updateBtn(scope)">
                  修改
                </el-button>
                <el-button type="text" size="small" @click="lookBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
                <el-button type="text" size="small" @click="releaseBtn(scope)">
                  发布
                </el-button>
                <el-button type="text" size="small" @click="updateBtn(scope)">
                  变更
                </el-button>
                <el-button type="text" size="small" @click="lookBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 4">
                <el-button type="text" size="small" @click="lookBtn(scope)">
                  查看
                </el-button>
                <el-button type="text" size="small" @click="addSuppBtn(scope)">
                  补充公告
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
import $ from 'jquery'
import {anno} from '@/api'
export default {
  data () {
    return {
      seacher_input: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '公告名称',
      options: [{
        value: 1,
        label: '公告名称'
      }, {
        value: 2,
        label: '项目名称'
      }, {
        value: 3,
        label: '项目编号'
      }],
      value: 1, // 默认搜索方式
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      searchIs: false,
      projecttableData: []
    }
  },
  methods: {
    list () {
      let url = null
      if (this.searchIs) {
        url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isSupplement: 0,
          isDelete: 0
        }
        if (this.seacherName === '公告名称') {
          url.annoNumbers = this.seacher_input
        } else if (this.seacherName === '项目名称') {
          url.sectionName = this.seacher_input
        } else if (this.seacherName === '项目编号') {
          url.sectionNumber = this.seacher_input
        }
      } else {
        url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isSupplement: 0,
          isDelete: 0
        }
      }
      anno.announcementList(url).then((res) => {
        this.projecttableData = res.data.AnnouncementList.list
        this.total = res.data.AnnouncementList.total
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
        isSupplement: 0
      }
      // 通过不同的查询进行参数赋值
      if (this.seacherName == '') {
        this.$message({
          type: 'warning',
          message: '请选择查询方式'
        })
        return false
      } else if (this.seacherName == '公告名称') {
        searchs.annoNumbers = this.seacher_input
      } else if (this.seacherName == '项目名称') {
        searchs.sectionName = this.seacher_input
      } else if (this.seacherName === '项目编号') {
        searchs.sectionNumber = this.seacher_input
      }
      anno.announcementList(searchs).then((res) => {
        this.projecttableData = res.data.AnnouncementList.list
        this.total = res.data.AnnouncementList.total
        this.searchIs = true
      })
    },
    deleteBtn (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        anno.announcementDelete(scope.row.objectId).then((res) => {
          this.list()
        })
      }).catch(() => {
        return false
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
        this.seacherName = '公告名称'
      } else if (val === 2) {
        this.seacherName = '项目名称'
      } else {
        this.seacherName = '项目编号'
      }
    },
    // 展开隐藏信息
    btnOpen (event) {
      if ($(event.currentTarget).hasClass('el-icon-arrow-down')) {
        $(event.currentTarget).removeClass('el-icon-arrow-down').addClass('el-icon-arrow-right')
        $(event.currentTarget).parents('tr.el-table__row').find('.cell').css('height', '30px')
      } else {
        $(event.currentTarget).parents('tr.el-table__row').find('.cell').css('height', 'auto')
        $(event.currentTarget).removeClass('el-icon-arrow-right').addClass('el-icon-arrow-down')
      }
    },
    // 添加
    addBtn () {
      this.$router.push({path: '/mr/transferor/submission/subminssion-notice/selection-project'})
    },
    // 编辑
    updateBtn (scope) {
      this.$router.push({path: '/mr/transferor/submission/subminssion-notice/update', query: {landUse: scope.row.landUse, ObjectId: scope.row.objectId, roledsType: 1}})
    },
    // 查看
    lookBtn (scope) {
      this.$router.push({path: `/mr/transferor/submission/subminssion-notice/detail/${scope.row.objectId}`, query: {ObjectId: scope.row.objectId, landUse: scope.row.landUse, roledsType: 1}})
    },
    releaseBtn (scope) {
      this.$router.push({path: '/mr/transferor/submission/subminssion-notice/release', query: {ObjectId: scope.row.objectId, landUse: scope.row.landUse, roledsType: 1}})
    },
    // 补充公告
    addSuppBtn (scope) {
      let date = new Date()
      var sign1 = '-'
      var sign2 = ':'
      let year = date.getFullYear()
      let month = date.getMonth() + 1
      let day = date.getDate()
      let hour = date.getHours()
      let minutes = date.getMinutes()
      let seconds = date.getSeconds()
      // 给一位数数据前面加 “0”
      if (month >= 1 && month <= 9) {
        month = '0' + month
      }
      if (day >= 0 && day <= 9) {
        day = '0' + day
      }
      if (hour >= 0 && hour <= 9) {
        hour = '0' + hour
      }
      if (minutes >= 0 && minutes <= 9) {
        minutes = '0' + minutes
      }
      if (seconds >= 0 && seconds <= 9) {
        seconds = '0' + seconds
      }
      var currentdate = year + sign1 + month + sign1 + day + ' ' + hour + sign2 + minutes + sign2 + seconds
      if (scope.row.saleStartTime === null) {
        let hangTimes = new Date(Date.parse(scope.row.hangStartTime.replace(/-/g, '/'))).getTime() // 挂牌开始时间
        let currentdateTime = new Date(Date.parse(currentdate.replace(/-/g, '/'))).getTime() // 当前时间
        if (hangTimes - currentdateTime < 0) {
          this.$message({
            type: 'warning',
            message: '在竞价开始时间一个小时前不能增加补充公告'
          })
          return false
        } else {
          let dates = Math.abs((hangTimes - currentdateTime)) / (1000 * 60 * 60)
          if (dates < 1) {
            this.$message({
              type: 'warning',
              message: '在竞价开始时间一个小时前不能增加补充公告'
            })
            return false
          }
        }
      } else {
        let saleStartTimes = new Date(Date.parse(scope.row.saleStartTime.replace(/-/g, '/'))).getTime() // 拍卖开始时间
        let currentdateTime = new Date(Date.parse(currentdate.replace(/-/g, '/'))).getTime() // 当前时间
        if (saleStartTimes - currentdateTime < 0) {
          this.$message({
            type: 'warning',
            message: '在竞价开始时间一个小时前不能增加补充公告'
          })
          return false
        } else {
          let dates = Math.abs((saleStartTimes - currentdateTime)) / (1000 * 60 * 60)
          if (dates < 1) {
            this.$message({
              type: 'warning',
              message: '在竞价开始时间一个小时前不能增加补充公告'
            })
            return false
          }
        }
      }
      anno.annoQueryIsAddSupp(scope.row.objectId).then((res) => {
        if (res.data.resCode != '1111') {
          let idArry = []
          scope.row.sections.map((ite) => {
            idArry.push(ite.objectId)
          })
          this.$router.push({path: '/mr/transferor/submission/supplement-notice/add', query: {landUse: scope.row.landUse, idArry: idArry.toString(), objectId: scope.row.objectId}})
        }
      })
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
  #submissionindexbox{
    .select{
      width: 20%;
    }
    .search{
      width: 79%;
    }
    .el-table .cell {
      height: 30px;
    }
    .cellopen{
      box-sizing: border-box;
      word-break: break-all;
      line-height: 23px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .el-tooltip{
      padding: 0 20px;
    }
    .xmbh_box{
      display: block;
      margin-bottom: 10px;
      overflow: hidden;
      text-overflow:ellipsis;
      white-space: nowrap;
    }
    .el-icon-arrow-right,el-icon-arrow-down{
      cursor: pointer;
    }
  }
</style>
