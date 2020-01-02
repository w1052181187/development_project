<template>
  <div class="notice-box" id="notice-box">
    <div class="message-tit">
      <span class="message-l">通知消息</span>
      <span class="message-r" @click="handleMoreBtn('notice')" v-if="messageList.length>0">更多>></span>
    </div>
    <div class="message-cont" v-for="(item, index) in messageList" :key="index">
      <el-row :gutter="20">
        <el-col :span="7">
          <p style="font-weight: 800;">{{item.messageType | filterType}}</p>
          <span class="mes-span" style="color: #999;">{{item.senderDate | filterDate}}</span>
        </el-col>
        <el-col :span="14" style="text-align: left;">
          <p><span>{{item.content}}</span></p>
        </el-col>
        <el-col :span="3">
          <el-button type="text" icon="el-icon-error" class="btn-del" v-if="item.messageType != 1" @click="deleteBtn(item.objectId)"></el-button>
        </el-col>
      </el-row>
    </div>
    <div class="no-data" v-if="messageList.length<=0">
      <img src="../image/xiaoxi.png" alt="">
      <p>~暂无消息~</p>
    </div>
  </div>
</template>
<script>
import {message} from 'api/index'
import {formatDate} from 'common/js/date'
// 实例
export default {
  data () {
    return {
      messageList: []
    }
  },
  created () {
  },
  mounted () {
    this.getMessageList()
    this.getMessageCount()
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
    /** 更多 */
    handleMoreBtn (type) {
      if (Object.is(type, 'notice')) {
        this.$router.push({path: '/admin/notice'})
      }
    },
    deleteBtn (objectId) {
      message.delete(objectId).then(res => {
        this.getMessageList()
        this.getMessageCount()
      })
    },
    getMessageList () {
      let query = {
        consumedStatus: 0,
        targetUser: this.$store.getters.authUser.userId,
        operate: 1
      }
      message.getList(query).then(res => {
        this.messageList = res.data.messageList
      })
    },
    /** 未读的通知消息数量 */
    getMessageCount () {
      let query = {
        consumedStatus: 0,
        targetUser: this.$store.getters.authUser.userId
      }
      message.count(query).then(res => {
        this.messageCount = res.data.count
        this.$emit('noticeInfo', this.messageCount)
      })
    }
  }
}
</script>
<style lang="less">
#notice-box {
  .no-data {
    padding-top: 65px;
    text-align: center;
  }
}
</style>
