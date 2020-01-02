<template>
  <!-- 后台工作台-通知 -->
  <div class="index-layout" id="notice-work-page">
    <div class="workdesk-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">当前位置:首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/notice' }">通知消息</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="notice-cont">
      <div class="notice-title">
        <span class="tit-span">通知消息</span>
        <el-button type="primary" size="mini" @click="signInfo" class="workdesk-signbtn">
          全部标记为已读
        </el-button>
        <el-button type="text" @click="$router.go(-1)" class="workdesk-backbtn">
          返回
        </el-button>
      </div>
      <el-tabs v-model="activeName" type="border-card"  @tab-click="handleTabsClick">
        <el-tab-pane label="未 读" name="unread">
          <div class="notice-list" v-for="(item, index) in notConsumedList" :key="index">
            <el-row class="row-cont">
              <div class="notice-sigle" @click.stop="handleRowClick(item, 0)">
                <el-col :span="3" class="col-l">
                  <span>{{item.messageType | filterType}}</span>
                </el-col>
                <el-col :span="17" class="col-m col-warp">
                  <span>{{item.content}}</span>
                </el-col>
                <el-col :span="4">
                  <span>{{item.senderDate | filterDate}}</span>
                </el-col>
              </div>
              <div class="handle-btn">
                <!-- <el-dropdown size="mini" class="select-drop" split-button type="primary" @command="handleDropdown">
                  附件
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item v-for="(file, index) in item.fileInformationList" :key="index" :command="file">{{file.fileName}}</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown> -->
                <el-button
                  class="del-btn"
                  type="danger"
                  round
                  size="mini"
                  icon="el-icon-delete"
                  v-if="item.messageType != 1"
                  @click.stop="deleteBtn(item.objectId, 0)">
                </el-button>
              </div>
            </el-row>
          </div>
        </el-tab-pane>
        <el-tab-pane label="已 读" name="read">
          <div class="notice-list" v-for="(item, index) in consumedList" :key="index">
              <el-row class="row-cont">
                <div class="notice-sigle" @click.stop="handleRowClick(item, 1)">
                  <el-col :span="3" class="col-l">
                    <span>{{item.messageType | filterType}}</span>
                  </el-col>
                  <el-col :span="17" class="col-m col-warp">
                    <span>{{item.content}}</span>
                  </el-col>
                  <el-col :span="4">
                    <span>{{item.senderDate | filterDate}}</span>
                  </el-col>
                </div>
                <div class="handle-btn">
                  <!-- <el-dropdown size="mini" class="select-drop" split-button type="primary" @command="handleDropdown">
                    附件
                    <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item v-for="(file, index) in item.fileInformationList" :key="index" :command="file">{{file.fileName}}</el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown> -->
                  <el-button
                    class="del-btn"
                    type="danger"
                    round
                    size="mini"
                    icon="el-icon-delete"
                    v-if="item.messageType != 1"
                    @click.stop="deleteBtn(item.objectId, 1)">
                  </el-button>
                </div>
              </el-row>
          </div>
        </el-tab-pane>
      </el-tabs>
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
      <div class="dialog-box">
        <el-dialog
          :title="noticeTitle"
          :visible.sync="dialogVisible"
          width="30%"
          center>
          <span>{{content}}</span>
          <div style="margin: 10px 0;">
            <span>附件：</span>
            <el-button type="text" v-for="(item, index) in fileInformationList" :key="index" @click="handleDropdown(item)" v-if="fileInformationList">
              {{item.fileName}}
            </el-button>
            <span v-if="!fileInformationList.length">无</span>
          </div>
        </el-dialog>
      </div>
    </div>
  </div>
</template>
<script>
import {message} from 'api/index'
import {formatDate} from 'common/js/date'
import {downloadFile} from 'common/js/common'
// 实例
export default {
  data () {
    return {
      fileInformationList: [],
      dialogVisible: false,
      noticeTitle: '',
      content: '',
      currentPage: 1,
      pageSizeList: [10, 20, 30, 40, 50],
      pageNo: 0,
      pageSize: 10,
      total: 0,
      activeName: 'unread',
      notConsumedList: [], // 未读
      consumedList: [], // 已读
      consumedStatus: 0 // 消费状态
    }
  },
  created () {
  },
  mounted () {
    this.getMessageList(0)
  },
  filters: {
    filterType (val) {
      if (val === 1) {
        return '通知公告'
      } else if (val === 2) {
        return '消息通知'
      } else if (val === 3) {
        return '信息变更'
      }
    },
    filterDate (val) {
      if (val) {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return ''
      }
    }
  },
  methods: {
    handleDropdown (value) {
      downloadFile(value.fileName, value.relativePath)
    },
    /** 分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.getMessageList(this.consumedStatus)
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getMessageList(this.consumedStatus)
    },
    /** tabs切换 */
    handleTabsClick (tab, event) {
      this.pageNo = 0
      this.pageSize = 10
      if (Object.is(tab.name, 'unread')) {
        this.consumedStatus = 0
        this.getMessageList(0)
      } else if (Object.is(tab.name, 'read')) {
        this.consumedStatus = 1
        this.getMessageList(1)
      }
    },
    /** 操作行 */
    handleRowClick (item, consumedStatus) {
      if (item.messageType === 1) {
        this.noticeTitle = '通知公告'
      } else if (item.messageType === 2) {
        this.noticeTitle = '消息通知'
      } else if (item.messageType === 3) {
        this.noticeTitle = '信息变更'
      }
      this.content = item.content
      this.fileInformationList = item.fileInformationList
      if (consumedStatus !== 1) {
        item.consumedStatus = 1
        item.consumedDate = new Date()
        message.update(item).then(res => {
          this.getMessageList(consumedStatus)
        })
      }
      this.dialogVisible = true
    },
    /** 标记为已读 */
    signInfo () {
      this.$confirm('此操作将全部标记为已读, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then((res) => {
        message.updateConsumedStatus(1).then(res => {
          this.getMessageList(this.consumedStatus)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    /** 删除 */
    deleteBtn (objectId, consumedStatus) {
      this.$confirm('此操作永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then((res) => {
        message.delete(objectId).then(res => {
          this.getMessageList(consumedStatus)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    getMessageList (consumedStatus) {
      let query = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        consumedStatus: consumedStatus,
        targetUser: this.$store.getters.authUser.userId,
        operate: 2
      }
      message.getList(query).then(res => {
        this.total = res.data.messageList.total
        if (Object.is(consumedStatus, 0)) {
          this.notConsumedList = res.data.messageList.list
        } else if (Object.is(consumedStatus, 1)) {
          this.consumedList = res.data.messageList.list
          console.log(this.consumedList)
        }
      })
    }
  }
}
</script>
<style lang="less">
  #notice-work-page {
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
      margin: 0 auto;
      margin-bottom: 80px;
      padding-bottom: 15px;
      background-color: #fff;
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
        .workdesk-signbtn {
          float: left;
          margin-top: 6px;
          margin-left: 20px;
          padding-left: 15px;
        }
      }
      .notice-list {
        height: 50px;
        line-height: 50px;
        margin: 15px;
        background-color: #F7F7F7;
        .notice-sigle {
          height: 50px;
          width: 85%;
        }
        .handle-btn {
          width: 20%;
          float: right;
          margin-top: -52px;
        }
        .del-btn {
          position: absolute;
          right: 0;
          margin-top: 10px;
          margin-right: 40px;
        }
        .row-cont {
          cursor: pointer;
        }
        .select-drop {
          position: absolute;
          right: 100px;
          margin-top: -2px;
        }
        .col-warp {
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
        .col-l {
          border-right: 2px solid #fff;
          span {
            font-weight: 800;
          }
        }
        .col-m {
          text-align: left;
          padding-left: 25px;
        }
      }
      .el-tabs__content {
        min-height: 500px;
      }
    }
    .page-box {
      padding-top: 10px;
    }
  }
</style>
