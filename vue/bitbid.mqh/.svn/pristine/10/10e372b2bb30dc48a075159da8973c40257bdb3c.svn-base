<template>
  <!-- 后台工作台-通知 -->
  <div class="index-layout" id="online-page">
    <div class="workdesk-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">当前位置:首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/online' }">在线人员</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="online-cont">
      <div class="online-title">
        <span class="tit-span">在线人员</span>
        <el-button type="text" @click="$router.go(-1)" class="workdesk-backbtn">
          返回
        </el-button>
      </div>
      <div class="online-list">
        <el-table
          :data="tableData"
          :row-class-name="tableRowClassName"
          height="650"
          border
          style="width: 100%">
          <el-table-column
            type="index"
            label="序号"
            align="center"
            :index="indexMethod"
            width="55">
          </el-table-column>
          <el-table-column
            prop="userName"
            label="姓名"
            align="center">
          </el-table-column>
          <el-table-column
            prop="role"
            label="角色"
            align="center">
          </el-table-column>
          <el-table-column
            prop="enterpriseName"
            label="所属机构"
            align="center">
          </el-table-column>
          <el-table-column
            prop="loginTime"
            label="登录时间"
            align="center"
            :formatter="filterDate">
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>
<script>
// 实例
import {homeIndex} from '../../../../api/index'
import {formatDate} from 'common/js/date'
export default {
  data () {
    return {
      currentPage: 1,
      tableData: []
    }
  },
  created () {
    this.init()
  },
  mounted () {
  },
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    init () {
      homeIndex.getOnlineUserInfo().then((res) => {
        this.tableData = res.data.loginUserInfoList
        this.tableData.map((tableItem) => {
          if (tableItem.roles) {
            tableItem.role = ''
            for (var i = 0; i < tableItem.roles.length; i++) {
              tableItem.role += tableItem.roles[i].name
              if (i !== tableItem.roles.length - 1) {
                tableItem.role += ','
              }
            }
          }
        })
      })
    },
    filterDate (value) {
      if (value) {
        let date = new Date(value.loginTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return null
      }
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    }
  }
}
</script>
<style lang="less">
  #online-page {
    position: relative;
    top: 100px;
    .workdesk-mqh-tit {
      width: 1200px;
      height: 40px;
      line-height: 40px;
      margin: 0 auto;
    }
    .online-cont {
      width: 1200px;
      margin: 0 auto;
      margin-bottom: 80px;
      padding-bottom: 15px;
      background-color: #fff;
      .online-title {
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
    }
    .online-list {
      padding: 15px;
    }
  }
</style>
