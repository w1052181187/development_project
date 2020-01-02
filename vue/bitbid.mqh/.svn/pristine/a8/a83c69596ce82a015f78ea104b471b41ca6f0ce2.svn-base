<template>
  <!-- 后台工作台-通知 -->
  <div class="index-layout" id="project-page">
    <div class="workdesk-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">当前位置:首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/project' }">招标项目</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="notice-cont">
      <div class="notice-title">
        <span class="tit-span">招标项目</span>
        <el-button type="text" @click="$router.go(-1)" class="workdesk-backbtn">
          返回
        </el-button>
      </div>
      <div class="search-warp">
        <el-form :inline="true" :model="searchForm" class="demo-form-inline">
          <el-form-item>
            <el-select v-model="searchForm.status" placeholder="请选择">
              <el-option label="所有项目" value="first"></el-option>
             <!-- <el-option label="备案中" value="second"></el-option>-->
              <el-option label="公告中" value="third"></el-option>
              <el-option label="报名中" value="fourth"></el-option>
              <el-option label="文件发售中" value="fifth"></el-option>
              <el-option label="待开标" value="sixth"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-input type="text" v-model="searchForm.projectName" placeholder="请输入项目名称" style="width:230px;"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="onSearch"></el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="notice-list" v-for="(item, index) in tableData" :key="index" @click="onclickBtn(item)">
        <el-row class="todo-hover">
          <el-col :span="20" class="col-l">
            <span>{{item.tenderProjectName}}</span>
          </el-col>
          <el-col :span="2" class="col-m">
            <span>当前阶段</span>
          </el-col>
          <el-col :span="2">
            <span class="list-span" v-show="(Number(item.progressStatus) === 1)">
              <span class="span-slot-blue"></span>完成
            </span>
            <span class="list-span" v-show="(Number(item.progressStatus) === 2)">
              <span class="span-slot-gree"></span>{{tenderStage}}
            </span>
            <span class="list-span" v-show="(Number(item.progressStatus) === 3)">
              <span class="span-slot-red"></span>未开始
            </span>
          </el-col>
        </el-row>
      </div>
      <!--分页start-->
      <div class="page-box">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :page-sizes="pageSizeList"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
      <!--分页end-->
    </div>
  </div>
</template>
<script>
import {tenderproject} from 'api/index'
// 实例
export default {
  data () {
    return {
      currentPage: 1,
      pageSizeList: [10, 20, 30, 40, 50],
      pageNo: 0,
      pageSize: 10,
      total: 0,
      searchForm: {
        status: this.$route.query.activeName
      },
      tableData: [],
      activeName: this.$route.query.activeName,
      tenderStage: '进行中'
    }
  },
  created () {
    this.getCurrentPage(this.activeName)
  },
  mounted () {
  },
  methods: {
    onclickBtn (tenderProjectInfo) {
      if (tenderProjectInfo.noticeProgressList.length > 0) {
        this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: tenderProjectInfo.code, noticeCode: tenderProjectInfo.noticeProgressList[0].noticeCode}})
      } else {
        this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: tenderProjectInfo.code}})
      }
    },
    getCurrentPage (activeName) {
      let condition = {
        messageLike: this.searchForm.projectName
      }
      condition.numCondition = 0
      if (activeName === 'first') {
        condition.stage = 1
        this.tenderStage = '进行中'
      } else if (activeName === 'second') {
      } else if (activeName === 'third') { // 公告中
        condition.stage = 2
        this.tenderStage = '公告中'
      } else if (activeName === 'fourth') { // 报名中
        condition.stage = 3
        this.tenderStage = '报名中'
      } else if (activeName === 'fifth') { // 文件发售中
        condition.stage = 4
        this.tenderStage = '文件发售中'
      } else if (activeName === 'sixth') { // 待开标
        condition.stage = 5
        this.tenderStage = '待开标'
      }
      this.getAllTenderProjectInfo(condition)
    },
    /** 获取全部的招标项目信息 */
    getAllTenderProjectInfo (condition) {
      tenderproject.getListOnMainPage({
        stage: condition.stage,
        numCondition: condition.numCondition
      }).then((res) => {
        if (res.data.tenderProjectList && res.data.tenderProjectList.list) {
          this.tableData = res.data.tenderProjectList.list
          this.total = res.data.tenderProjectList.total
          this.tableData.forEach(tenderProject => {
            tenderProject.progressStatus = ''
            if (tenderProject.noticeProgressList.length === 0) {
              tenderProject.progressStatus = 3
            } else {
              if (this.checkTenderProjectStatus(tenderProject.noticeProgressList).length > 0) {
                // tenderProject.progressStatusStr = this.getProgressStageStr(tenderProject.latestNotice)
                tenderProject.progressStatus = 2
              } else {
                tenderProject.progressStatus = 1
              }
            }
          })
          if (this.total <= this.pageSize) {
            this.pageNo = 0
          }
        }
      })
    },
    /** 判断招标项目是否已完成（即已有的公告全部完成） */
    checkTenderProjectStatus (noticeProgressList) {
      let results = []
      let flag = false
      if (noticeProgressList) {
        for (let i = 0; i < noticeProgressList.length; i++) {
          if (noticeProgressList[i].progressStage < 7) {
            flag = true
            break
          } else {
            flag = false
          }
        }
        if (flag) {
          results = noticeProgressList
        }
      }
      return results
    },
    /** 表单分页 */
    handleSizeChange (val) {
      var likeThis = this
      var pageSize = `${val}`
      this.pageNo = 0
      this.currentPage = 1
      this.pageSize = parseInt(pageSize)
      this.$nextTick(() =>
        this.getAllTenderProjectInfo(this.pageNo, this.pageSize, function (resp) {
          likeThis.total = resp.data.tenderProjectList.list.total
        })
      )
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getAllTenderProjectInfo(parseInt(this.pageNo), parseInt(this.pageSize))
    },
    /** 查询 */
    onSearch () {
      if (this.searchForm.status) {
        this.getCurrentPage(this.searchForm.status)
      } else {
        this.getCurrentPage(this.activeName)
      }
    }
  }
}
</script>
<style lang="less">
  #project-page {
    position: relative;
    top: 100px;
    .workdesk-mqh-tit {
      width: 1200px;
      height: 40px;
      line-height: 40px;
      margin: 0 auto;
    }
    .notice-cont {
      width: 1200px;
      height: 720px;
      margin: 0 auto;
      margin-bottom: 80px;
      padding-bottom: 15px;
      background-color: #fff;
      overflow: auto;
      .search-warp {
        text-align: left;
        padding-top: 15px;
        padding-left: 15px;
        .el-form-item {
          margin-bottom: 0;
          margin-right: 0;
          .el-input__inner, .el-button {
            border-radius: 0;
          }
        }
        .el-form-item:nth-child(1) {
          margin: 1px 0;
        }
        .el-form-item:nth-child(2) {
          margin: 0 -4px;
        }
      }
      .notice-title {
        height: 40px;
        background-color: #F7F6FB;
        .tit-span {
          display: inline-block;
          margin-top: 10px;
          padding-left: 15px;
          font-size: 16px;
          font-weight: 800;
          float: left;
        }
        .workdesk-backbtn {
          float: right;
          padding-right: 15px;
        }
      }
      .notice-list {
        height: 50px;
        line-height: 50px;
        margin: 15px;
        background-color: #F7F7F7;
        .todo-hover:hover {
          border: 1px solid #6E8AF7;
        }
        .todo-hover .col-l {
          text-align: left;
          padding-left: 15px;
          border-right: 2px solid #fff;
        }
        .todo-hover .list-span {
          .span-slot-blue,
          .span-slot-gree,
          .span-slot-red {
            display: inline-block;
            width: 5px;
            height: 5px;
            border-radius: 50%;
            margin-right: 20px;
          }
          .span-slot-blue {
            background: #43BF2D;
          }
          .span-slot-gree {
            background: #3F62F4;
          }
          .span-slot-red {
            background: red;
          }
        }
        .col-m {
          text-align: left;
          padding-left: 25px;
        }
      }
    }
    .page-box {
      position: absolute;
      bottom: 0;
      left: 40%;
      padding-top: 10px;
    }
  }
</style>
