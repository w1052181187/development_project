<template>
  <div class="draw-room" id="draw-room">
     <div class="proinfo-info-box">
      <div class="title-box">
        <p>项目信息</p>
      </div>
      <el-form :model="projectInfoForm" label-width="100px" class="pro-form">
        <el-row>
          <el-col :span="8">
            <el-form-item label="项目名称:">
              <span>{{projectInfoForm.projectName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="项目编号:">
              <span>{{projectInfoForm.projectCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="包组名称:">
              <span>{{projectInfoForm.sectionName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="包组编号:">
              <span>{{projectInfoForm.sectionCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="抽签数量:">
              <span>{{personNum}}家</span>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <el-row :gutter="15" style="margin-left:0;margin-right:0;">
      <el-col :span="12" style="padding-left:0;">
         <!-- 抽签展示区start -->
         <div class="draw-show-box draw-height">
          <div class="title-box">
            <p>抽签展示区</p>
          </div>
          <div class="scroll-box">
            <!-- 霓虹灯效果组件 -->
            <canvasom v-if="!showCanvas"></canvasom>
            <div :class="{'scroll-cont':true,'scroll-border': showCanvas}">
              <div class="scroll-ani">
                <ul ref="con1" :class="{anim:animate==true}" id="scrollUlId">
                  <li v-for='(item, index) in drawEnterpriseList' :key="index" :data-id="item.enterpriseId" :data-name="item.userName">{{item.userName}}</li>
                </ul>
              </div>
              <div class="start-draw" v-if="currentUser.roleIds[0] === 1 && drawRecordList.length <= 0">
                <el-button class="btn-warning-bg" round size="small" @click="startDrawClick" v-if="!isStart">开始抽签</el-button>
                <el-button class="btn-warning-bg" round size="small" @click="stopDrawClick" v-else>暂停抽签</el-button>
              </div>
            </div>
          </div>
        </div>
        <!-- 抽签展示区end -->
      </el-col>
      <el-col :span="12" style="padding-right:0;">
        <div class="draw-show-box">
          <div class="title-box">
            <p>候选中标供应商</p>
          </div>
          <el-table
            :data="candSupplyTableData"
            border
            :show-header="false"
            height="250"
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
              label="名称">
            </el-table-column>
          </el-table>
        </div>
        <div class="draw-show-box draw-result-box">
          <div class="title-box">
            <p>抽签结果</p>
          </div>
          <div v-for="(item, index) in drawRecordList" :key="index">
            <div class="draw-result">
              <span class="flag-num">{{index + 1}}</span>
              <span>{{item.userName}}</span>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
    <div class="room-btns" v-if="currentUser.roleIds[0] === 1 && drawRecordList.length > 0">
      <template>
        <el-button size="small" class="btn-blue-bg" :loading="isLoading" @click="submitClick"
                   v-if="!isSubmited" :disabled="isGoInRoom">提交结果</el-button>
        <el-button size="small" class="btn-warning-bg" @click="resetDrawClick"
                   v-if="isSubmited && !isConfirm" :disabled="isGoInRoom">重新抽签</el-button>
        <el-button size="small" class="btn-blue-bg" :loading="isConfirmLoading" @click="confirmClick"
                   v-if="isSubmited && !isConfirm" :disabled="isGoInRoom">确认结果</el-button>
      </template>
    </div>
    <!-- 撤回原因-弹窗 -->
    <div class="dialog-box">
      <el-dialog
        title="撤回"
        :visible.sync="gobackDialogVisible"
        width="30%"
        @close="closeDialog('formRecall')"
        center>
        <el-form ref="formRecall" :model="recallForm" :rules="recallRules" label-width="90px">
          <el-form-item label="撤回原因" prop="reason">
            <el-input type="textarea" v-model="recallForm.reason" :rows="4"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="gobackDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="recallClickBtn('formRecall')">确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <div class="foot-btn">
      <!--底部-->
      <footers></footers>
      <!--底部-->
    </div>
  </div>
</template>
<script>
import canvasom from './canvas.vue'
import footers from '@/views/admin/footer/footer.vue'
import {projectInfo, drawRecord, scalingEnd} from '@/api'
import {WebPushSocket} from '../../../assets/js/websocket-inst.js'
// 状态
import {mapState} from 'vuex'
export default {
  name: 'draw-room',
  components: {
    canvasom,
    footers
  },
  data () {
    return {
      gobackDialogVisible: false,
      recallForm: {}, // 撤回原因-表单
      recallRules: {
        reason: [
          { required: true, message: '请输入撤回原因', trigger: ['blur', 'change'] },
          {min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: ['blur', 'change']}
        ]
      },
      num: this.$route.query.num || 1,
      isLoading: false,
      isConfirmLoading: false,
      isSubmited: false, // 是否提交结果
      isStart: false, // 是否开始抽签
      animate: false,
      projectInfoForm: {},
      currentPage: 1,
      candSupplyTableData: [], // 候选中标供应商
      drawEnterpriseList: [],
      drawRecordList: [],
      timeStamp: null, // 控制滚动停止
      showCanvas: false,
      currentUser: this.$store.getters.authUser,
      projectId: this.$store.getters.projectId ? this.$store.getters.projectId : this.$route.query.projectId,
      methodType: this.$route.query.methodType,
      // 是否监标人
      isSurveillance: this.$store.getters.authUser.roleIds[0] === 4,
      personNum: 0,
      webPushSocket: null,
      isGoInRoom: true, // 流程（是否已确认结果（流程结束））
      isConfirm: false // 是否确认提交成功（点击确认提交成功后用来隐藏确认提交按钮跟撤回按钮）
    }
  },
  created () {
    let result = this.getBrowserInfo()
    if (result.hasOwnProperty('ver') && result.ver === '9.0') {
      this.showCanvas = true
    } else {
      this.showCanvas = false
    }
  },
  computed: {
    // 获取状态
    ...mapState({
      drawMessages: (state) => {
        return state.messages
      }
    })
  },
  watch: {
    drawMessages: function (newV, oldV) {
      if (newV.message === '开始抽签' && String(newV.userId) !== String(this.currentUser.userId)) {
        this.setInTime()
      } else if (newV.message === '结束抽签') {
        this.stopScroll()
        this.getRecord()
      } else if (newV.message === '重新抽签') {
        this.drawRecordList = []
        this.stopScroll()
      }
    }
  },
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    scroll () {
      this.animate = true// 因为在消息向上滚动的时候需要添加css3过渡动画，所以这里需要设置true
      this.drawEnterpriseList.push(this.drawEnterpriseList[0]) // 将数组的第一个添加到数组
      this.drawEnterpriseList.shift()
      this.animate = false // margin-top 为0 的时候取消过渡动画，实现无缝滚动
    },
    /** 开始抽签 */
    startDrawClick () {
      this.stopScroll()
      this.isStart = true
      // 将开始抽签的状态推送给其他用户-其他用户页面开始滚动
      this.webPushSocket.sendMsg('开始抽签')
      this.setInTime()
    },
    /** 暂停抽签 */
    stopDrawClick () {
      this.isStart = false
      this.stopScroll()
      let result = this.randomNum(this.personNum, 0, this.drawEnterpriseList.length - 1)
      this.handleAddRecord(result)
    },
    handleAddRecord (result) {
      let resultList = []
      let data = {}
      for (let i = 0; i < result.length; i++) {
        data = {
          projectId: this.projectId,
          userId: this.drawEnterpriseList[result[i]].objectId,
          userName: this.drawEnterpriseList[result[i]].userName
        }
        resultList.push(data)
      }
      let params = {
        methodType: this.methodType,
        num: this.num
      }
      drawRecord.addRecord(resultList, params).then((res) => {
        if (res.data.resCode === '0000') {
          // 将结束抽签的状态推送给其他用户-其他用户页面停止滚动
          this.webPushSocket.sendMsg('结束抽签')
          this.getRecord()
        }
      })
    },
    // 从min到max中生成num个不重复随机数
    randomNum (num, min, max) {
      let res = []
      let tempNum
      do {
        tempNum = Math.floor(Math.random() * (max - min + 1) + min)
        if (res.indexOf(tempNum) === -1) {
          res.push(tempNum)
        }
      } while (res.length < num)
      return res
    },
    // 设置滚动
    setInTime () {
      this.timeStamp = setInterval(this.scroll, 50)
    },
    /** 重新抽签 */
    resetDrawClick () {
      this.gobackDialogVisible = true
    },
    /** 关闭弹窗-撤回 */
    closeDialog () {
      this.recallForm = {}
      this.$refs['formRecall'].resetFields()
    },
    /** 停止滚动 */
    stopScroll () {
      window.clearInterval(this.timeStamp)
    },
    /** 判断浏览器是否是ie9 */
    getBrowserInfo () {
      let Sys = {}
      let ua = navigator.userAgent.toLowerCase()
      let re = /(msie|firefox|chrome|opera|version).*?([\d.]+)/
      let m = ua.match(re)
      if (m && m.length > 2) {
        Sys.browser = m[1].replace(/version/, "'safari")
        Sys.ver = m[2]
      }
      return Sys
    },
    // 获取项目信息以及定标候选人
    projectInfo () {
      if (!this.projectId) {
        return
      }
      let obj = {
        methodType: this.methodType,
        num: this.num,
        isNext: 1
      }
      projectInfo.scalingEndInfo(this.projectId, obj).then((res) => {
        if (res.data.resCode === '0000') {
          this.projectInfoForm = res.data.ProjectView.project
          let methods = res.data.ProjectView.scalingSystemMethods
          if (methods) {
            methods.map((item) => {
              if (item.methodType === 1) {
                this.personNum = item.number
              }
            })
          }
          this.candSupplyTableData = res.data.ProjectView.users
          res.data.ProjectView.users.map((item) => {
            this.drawEnterpriseList.push(item)
          })
          this.getRecord()
        }
      })
    },
    getRecord () {
      let params = {
        pageNo: 0,
        pageSize: 1000,
        methodType: this.methodType,
        num: this.num,
        projectId: this.projectId
      }
      drawRecord.getRecord(params).then((res) => {
        if (res.data.resCode === '0000') {
          this.drawRecordList = res.data.DrawRecordList
          this.isSubmited = res.data.isSubmit === 1
        }
      })
    },
    submitClick () {
      this.isLoading = true
      let params = {
        methodType: this.methodType,
        num: this.num
      }
      drawRecord.submitRecord(this.projectId, params).then((res) => {
        this.isLoading = false
        if (res.data.resCode === '0000') {
          this.isSubmited = true
        }
      })
    },
    confirmClick () {
      // 没有提交，先提交再确认
      if (!this.isSubmited) {
        this.submitClick()
      }
      this.isConfirmLoading = true
      let params = {
        methodType: this.methodType,
        num: this.num
      }
      drawRecord.confirmRecord(this.projectId, params).then((res) => {
        this.isConfirmLoading = false
        if (res.data.resCode === '0000') {
          this.isGoInRoom = true
          this.isConfirm = true
        }
      })
    },
    /** 撤回-确定 */
    recallClickBtn (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          let data = {
            projectId: this.projectId,
            reason: this.recallForm.reason
          }
          let params = {
            methodType: this.methodType,
            num: this.num,
            projectId: this.projectId
          }
          drawRecord.revokeRecord(data, params).then((res) => {
            this.webPushSocket.sendMsg('重新抽签')
            if (res.data.resCode === '0000') {
              this.drawEnterpriseList = []
              this.drawRecordList = []
              this.candSupplyTableData.map(item => {
                this.drawEnterpriseList.push(item)
              })
              this.gobackDialogVisible = false
              this.isSubmited = false
              this.recallForm = {}
              this.$refs[form].clearValidate()
            }
          })
        } else {
          return false
        }
      })
    },
    // 流程控制
    getVoteRoomPage () {
      scalingEnd.getVoteRoomPage(this.projectId, {methodType: this.methodType, num: this.num}).then(res => {
        // 1已经执行-不可操作
        // 0未执行-可操作
        if (res.data.resCode === '0000') {
          this.isGoInRoom = res.data.isHide === '1'
        }
      })
    },
    init () {
      this.webPushSocket = WebPushSocket.getInstance(this.$store.getters.projectId, this.$store.getters.authUser.userId, this.$store.getters.authUser.userName, 'All', this.methodType, this.num)
      this.webPushSocket.init()
    }
  },
  mounted () {
    this.projectInfo()
    this.init()
    // 流程
    this.getVoteRoomPage()
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
#draw-room {
  .proinfo-info-box {
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
  .draw-height {
    height: 403px;
  }
  .draw-show-box {
    padding: 10px;
    margin-top: 15px;
    background: #fff;
    .scroll-box {
      position: relative;
    }
    .draw-result {
      text-align: left;
      padding-left: 20px;
      margin-bottom: 15px;
      .flag-num {
        display: inline-block;
        width: 20px;
        height: 20px;
        margin-right: 15px;
        background: red;
        text-align: center;
        line-height: 20px;
        color: #fff;
      }
    }
  }
  .draw-result-box {
    min-height: 70px;
  }
  .scroll-cont {
    position: absolute;
    top: 20px;
    width: 100%;
    // 轮播
    .scroll-ani {
      height: 230px;
      opacity: 0.9;
      margin: 5px 30px;;
      overflow: hidden;
      transition: all 0.1s;
      ul {
        padding-left: 0;
        background: rgba(225,225,225,.4);
        li {
          line-height: 35px;
          height: 45px;
          list-style: none;
          font-size: 20px;
          color: #FC8F54;
        }
      }
      .scroll-li0, .scroll-li4 {
        opacity: 0.4;
        font-size: 20px;
      }
      .scroll-li1, .scroll-li3 {
        opacity: 0.6;
        font-size: 22px;
      }
      .scroll-li2 {
        background: #FFF0CF;
        font-size: 26px;
      }
    }
    .start-draw {
      margin-top: 20px;
    }
  }
  .scroll-border {
    border: 1px solid #ddd;
  }
  .room-btns {
    padding: 20px 0;
  }
}
</style>
