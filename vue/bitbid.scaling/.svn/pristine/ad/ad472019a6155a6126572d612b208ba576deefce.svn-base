<template>
  <div class="accu-leader-sum" id="accu-leader-sum">
    <div class="proinfo-info-box">
      <div class="title-box">
        <p>投票汇总</p>
      </div>
      <el-table
        :data="voteSumTableData"
        border
        style="width: 50%">
        <el-table-column
          type="index"
          label="排序"
          align="center"
          width="55">
        </el-table-column>
        <el-table-column
          prop="userName"
          label="名称">
        </el-table-column>
        <el-table-column
          prop="score"
          label="分值">
        </el-table-column>
      </el-table>
      <div class="tips_data" v-if="isShowTips">
        注：{{tips}}
      </div>
      <div class="confirm-btn"  v-if="isShowed">
        <el-button
          type="primary"
          @click="confirmClick"
          size="small"
          :disabled="isDisabled"
          :loading="isLoading"
          v-if="currentUser.roleIds[0] === 1 && voteSumTableData.length > 0">
          确 认
        </el-button>
        <el-button
          type="primary"
          @click="handleAddVote"
          size="small"
          :disabled="isDisabled"
          v-if="currentUser.roleIds[0] === 1 && isShowAddVote"
          :loading="isAddVoteLoading">
          加轮
        </el-button>
      </div>
    </div>
    <div class="bid-report-box">
      <div class="title-box">
        <p>定标人投票情况</p>
      </div>
      <el-row :gutter="20">
        <div class="group-box" v-for="(item, index) in bidVoteTableData" :key="index">
          <el-col :span="2" class="bid-title">{{index}}:</el-col>
          <el-col :span="10" style="margin-bottom: 20px;">
            <el-table
              :data="item"
              border
              style="width: 100%">
              <el-table-column
                type="index"
                label="序号"
                align="center"
                width="55">
              </el-table-column>
              <el-table-column
                prop="resultName"
                label="名称">
              </el-table-column>
              <el-table-column
                prop="score"
                label="分值">
              </el-table-column>
            </el-table>
          </el-col>
        </div>
      </el-row>
    </div>
    <div class="foot-btn">
      <!--底部-->
      <footers></footers>
      <!--底部-->
    </div>
  </div>
</template>
<script>
// 状态
import {mapState} from 'vuex'
import footers from '@/views/admin/footer/footer.vue'
import {scalingEnd} from '@/api'
import {WebPushSocket} from '../../../assets/js/websocket-inst.js'
export default {
  name: 'accu-leader-sum',
  data () {
    return {
      currentUser: this.$store.getters.authUser,
      projectId: this.$store.getters.projectId,
      methodType: this.$route.query.methodType,
      num: this.$route.query.num || 1,
      isDisabled: false,
      voteSumTableData: [], // 投票汇总
      bidVoteTableData: [], // 定标人投票情况
      webPushSocket: null,
      isLoading: false,
      isShowed: false, // 默认不显示确认按钮
      isShowAddVote: false, // 是否显示加轮按钮（投票相同时）
      isAddVoteLoading: false,
      isShowTips: false, // 是否显示提示
      tips: ''
    }
  },
  computed: {
    // 获取状态
    ...mapState({
      submitData: (state) => {
        return state.messages
      }
    })
  },
  watch: {
    submitData: function (newV, oldV) {
      if (newV.message === '撤销提交' || newV.message === '提交数据') {
        this.getVoteResult()
        this.getVoteReport()
      }
    },
    $route: function () {
      this.num = this.$route.query.num || 1
      this.methodType = this.$route.query.methodType
      this.init()
    }
  },
  methods: {
    // 定标人投票情况
    getVoteResult () {
      let params = {
        methodType: this.methodType,
        num: this.num
      }
      scalingEnd.getScallingVoteResult(this.projectId, params).then(res => {
        this.bidVoteTableData = res.data.GroupMap
      })
    },
    // 投票汇总
    getVoteReport () {
      let params = {
        methodType: this.methodType,
        num: this.num
      }
      scalingEnd.getScallingVoteReport(this.projectId, params).then((res) => {
        let tempResult = res.data.userList
        if (tempResult && tempResult.length > 0) {
          this.voteSumTableData = tempResult
        } else {
          this.voteSumTableData = []
        }
        this.isShowTips = !!res.data.tips
        this.tips = this.isShowTips ? res.data.tips.toString() : ''
        if (this.isShowTips && res.data.isEnd) {
          this.isShowAddVote = true
        } else {
          this.isShowAddVote = false
        }
      })
    },
    // 点击加轮
    handleAddVote () {
      this.isAddVoteLoading = true
      let obj = {
        methodType: this.methodType,
        num: this.num
      }
      scalingEnd.addVote(this.projectId, obj).then((res) => {
        this.isAddVoteLoading = false
        if (res.data.resCode === '0000') {
          this.isShowAddVote = false
          this.$store.commit('SET_IS_REFRESH_MENU', true)
          this.isDisabled = true
          this.webPushSocket.sendMsg('确认提交')
          this.getControlVoteRoomPage()
          let obj1 = {
            methodType: this.methodType,
            num: this.num,
            noActivity: 1
          }
          scalingEnd.confirmResult(this.projectId, obj1).then(res => {
          })
        }
      })
    },
    // 确认-投票结果
    confirmClick () {
      this.isLoading = true
      let obj = {
        methodType: this.methodType,
        num: this.num
      }
      scalingEnd.confirmResult(this.projectId, obj).then(res => {
        this.isLoading = false
        if (res.data.resCode === '0000') {
          this.getControlVoteRoomPage()
          this.webPushSocket.sendMsg('确认提交')
        }
      })
    },
    // 流程控制
    getControlVoteRoomPage () {
      scalingEnd.getVoteRoomPage(this.projectId, {methodType: this.methodType, num: this.num}).then(res => {
        // 1已经执行-不可操作
        // 0未执行-可操作
        this.isDisabled = res.data.isHide === '1'
        this.isShowed = true
      })
    },
    init () {
      this.getVoteReport()
      this.getVoteResult()
      // 流程控制
      this.getControlVoteRoomPage()
      this.webPushSocket = WebPushSocket.getInstance(this.$store.getters.projectId, this.$store.getters.authUser.userId, this.$store.getters.authUser.userName, 'All', this.methodType, this.num)
      this.webPushSocket.init()
    }
  },
  components: {
    footers
  },
  mounted () {
    this.init()
  },
  destroyed () {
    if (this.webPushSocket) {
      this.webPushSocket.close()
      this.webPushSocket = null
    }
  }
}
</script>
<style lang="scss">
#accu-leader-sum {
  .proinfo-info-box,
  .bid-report-box {
    padding: 10px;
    margin-top: 15px;
    background: #fff;
    .pro-form {
      .el-form-item {
        margin-bottom: 0;
      }
      .el-form-item__content {
        text-align: left;
      }
    }
  }
  .confirm-btn {
    margin-top: 20px;
  }
  .bid-report-box {
    .bid-title {
      color: #2E76EC;
    }
  }
  .tips_data {
    margin-top: 20px;
    color: #e8ab4e;
    background-color: #fdf6ec;
    size: 12px;
    width: 50%;
    text-align: left;
    padding:  6px 15px;
    box-sizing: border-box;
  }
}
</style>
