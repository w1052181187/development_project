<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="readybar-page">
    <div class="dready-left">
      <!-- 项目基本信息 -->
      <div class="project-info">
        <div class="project-tit">项目基本信息</div>
        <el-row :gutter="20">
          <el-col :span="12">
            <div class="dready-tab">
              <el-table
                height="275"
                border
                :data="projectTabData"
                style="width: 100%;">
                <el-table-column
                  align="center"
                  prop="projectName"
                  label="抽签项目名称">
                </el-table-column>
              </el-table>
            </div>
          </el-col>
          <el-col :span="12">
            <el-form ref="form" :model="formProjectInfo" label-width="130px">
              <el-form-item label="抽签中介类型:">
                <span>{{formProjectInfo.type | filterType}}</span>
              </el-form-item>
              <el-form-item label="抽签数量:">
                <span>{{formProjectInfo.extractCount}}</span>
              </el-form-item>
              <el-form-item label="开始抽签单位:">
                <span>{{formProjectInfo.startEnterpriseName}}</span>
              </el-form-item>
              <!--<el-form-item label="结束抽签单位:">
                <span>{{formProjectInfo.endEnterpriseName}}</span>
              </el-form-item>-->
              <!-- <el-form-item label="监督人:">
                  <el-input v-model="supervisorName" style="width: 200px;" :disabled="inputFlag" @blur="saveSupervisorName"></el-input>
               </el-form-item>-->
              <el-form-item label="参与人员:">
                <el-input v-model="participant" style="width: 200px;margin-top: -5px" :disabled="inputFlag" @blur="saveParticipant"></el-input>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </div>
      <!-- 项目基本信息结束 -->

      <!-- 准备列表开始 -->
      <div class="dready-draw-info">
        <el-row :gutter="20" v-model="organEnterprise" v-if="showOrganStatus">
          <el-col :span="24">
            <el-card shadow="always" class="card-draw">
              <span class="card-flag flag-c">组织单位</span>
              <div class="card-avater">
                <img src="./image/bai.png"/>
              </div>
              <div class="card-des">
                <p>{{organEnterprise.enterpriseName}}</p>
                <p class="c-name">{{organEnterprise.userName}}</p>
              </div>
              <div class="card-states">
                <img src="./image/success.svg" alt="" v-if="readyStatue">
                <el-button type="success" round icon="iconfont mqh-icon-ok" style="transform: rotateY(180deg);width: 85px;" v-if="organEnterprise.readyStatue" @click="changeStatus(organEnterprise)" :disabled= "organEnterprise.statusBtnFlag"></el-button>
                <el-button type="primary" round @click="changeStatus(organEnterprise)" :disabled= "organEnterprise.statusBtnFlag" v-if="!organEnterprise.readyStatue">{{organEnterprise.btnName}}</el-button>
              </div>
            </el-card>
          </el-col>
        </el-row>
        <el-row :gutter="20" v-if="showDrawStatus">
          <el-col :span="24" v-for="(item,index) in drawEnterpriseData" :key="index">
            <el-card shadow="always" class="card-draw" >
              <span class="card-flag flag-d">项目单位</span>
              <div class="card-avater">
                <img src="./image/bai.png"/>
              </div>
              <div class="card-des">
                <p>{{item.enterpriseName}}</p>
                <p class="c-name">{{item.userName}}</p>
              </div>
              <div class="card-states">
                <img src="./image/success.svg" alt="" v-if="readyStatue">
                <el-button type="success" round icon="iconfont mqh-icon-ok" style="transform: rotateY(180deg);width: 85px;" @click="changeStatus(item)" :disabled= "item.statusBtnFlag" v-if="item.readyStatue"></el-button>
                <el-button type="primary" round @click="changeStatus(item)" :disabled= "item.statusBtnFlag" v-if="!item.readyStatue">{{item.btnName}}</el-button>
              </div>
            </el-card>
          </el-col>
        </el-row>
        <!-- 招标办显示该按钮 -->
        <el-row :gutter="20" v-if="roleAuthority">
          <el-col :span="24">
            <el-button type="success" plain style="margin: 30px;" @click="goToDroom" :disabled="disabledFlag" v-if="buttonFlag">进入抽签室</el-button>
          </el-col>
        </el-row>
      </div>
      <!-- 准备列表结束 -->
    </div>
  </div>
</template>
<script>
import {drawproject, drawroom} from '../../../../api/index'
import {commonJs} from '../../../../common/js/common'
import {WebPushSocket} from '../../../../common/js/websocket'
// 实例
export default {
  data () {
    return {
      readyStatue: false, // 就位状态
      roleAuthority: true, // 登录角色权限（如果是招标办显示）
      formProjectInfo: {},
      projectTabData: [],
      applyEnterprise: [], // 选聘备案申请表中对应的项目单位
      drawEnterpriseData: [],
      supervisorName: '', // 监督人信息
      participant: '', // 参与人员
      organEnterprise: {}, // 组织机构的信息
      drawEnterpriseInfo: [],
      showOrganStatus: false,
      showDrawStatus: false,
      disabledFlag: true, // 按钮是否可点击，默认不可点击
      buttonFlag: false, // 按钮状态,默认隐藏
      inputFlag: true, // 是否可输入监督人名称,默认不可填写
      enterpriseStatus: 1 // 参与抽签的单位状态，默认“请就位”
    }
  },
  computed: {
    getRoomMessages () {
      return this.$store.getters.messages
    }
  },
  watch: {
    getRoomMessages: function (newV, oldV) {
      if (newV) {
        // this.supervisorName = this.$store.getters.messages.supervisorName
        /* if (this.$store.getters.messages) {
          this.participant = this.getParticipantInfo(this.$store.getters.messages.enterprises)
        } */
        if (this.$store.getters.messages && this.$store.getters.messages.participant) {
          // this.participant = this.getParticipantInfo(this.$store.getters.messages.enterprises)
          this.participant = this.$store.getters.messages.participant
        }
        this.getDrawEnterpriseInfo(this.$store.getters.messages.enterprises)
        // 确定进入抽签室的按钮是否为灰
        this.checkOrganIfEnterRoom(this.$store.getters.messages.enterprises)
        /**
         * 除招标办外的项目单位进入抽签室的条件：
         * this.$store.getters.messages.enterprises:当前所有参与抽签的单位
         * this.buttonFlag === false：抽签室的按钮可点击
         * this.$store.getters.authUser.enterpriseType !== 1：除招标办外
         * this.$store.getters.messages.roomStatus === 2：房间状态为已进入抽签室
         */
        if (this.$store.getters.messages.enterprises.length > 0 && this.buttonFlag === false && this.$store.getters.authUser.enterpriseType !== 1 && this.$store.getters.messages.roomStatus === 2) {
          for (let i = 0; i < this.$store.getters.messages.enterprises.length; i++) {
            if (this.$store.getters.messages.enterprises[i].status !== 2) {
              break
            } else if (i === this.$store.getters.messages.enterprises.length - 1) {
              this.$emit('roomId', this.formProjectInfo.objectId)
              // this.$router.push({path: '/drawroom/droom', query: {name: 'droom', roomId: this.formProjectInfo.objectId}})
            }
          }
        }
      }
    }
  },
  created () {
    const webPushSocket = new WebPushSocket()
    webPushSocket.setSettings(this.$store.getters.authUser.userId, this.$store.getters.authUser.enterpriseId, this.$route.query.roomId || '')
    webPushSocket.init()
    setInterval(function () {
      webPushSocket.heartCheck.start()
    }, 15000)
    let roomInfo = {}
    roomInfo.roomStatus = 1
    roomInfo.drawStatus = 1
    roomInfo.newUserName = this.$store.getters.authUser.userName
    if (this.$store.getters.messages && this.$store.getters.messages.participant) {
      this.participant = this.$store.getters.messages.participant
    }
    this.saveCurrentGroupInfo(roomInfo)
    this.$emit('webSocket', webPushSocket)
  },
  mounted () {
    setTimeout(() => {
      this.getCurrentGroupInfo()
    }, 500)
  },
  methods: {
    /** 保存房间监督者姓名 */
    /* saveSupervisorName () {
      if (this.supervisorName === '' || this.supervisorName === undefined) {
        this.$confirm('监督人不能为空', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {}).catch(() => {
          return false
        })
      } else {
        let roomInfo = {}
        roomInfo.supervisorName = this.supervisorName
        this.saveCurrentGroupInfo(roomInfo)
      }
    }, */
    /** 保存参与人员信息 */
    saveParticipant () {
      if (this.participant === '' || this.participant === undefined) {
        this.$confirm('参与人员不能为空', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {}).catch(() => {
          return false
        })
      } else {
        let roomInfo = {}
        roomInfo.participant = this.participant
        this.saveCurrentGroupInfo(roomInfo)
      }
    },
    /** 保存当前房间的信息 */
    createDraw (roomInfo) {
      let drawRoomInfo = {
        drawStatus: 1,
        roomStatus: 1,
        roomId: '',
        // supervisorName: '',
        staffName: '',
        newUserName: '',
        staffId: '',
        drawLotsPlace: '',
        enterprises: [],
        results: []
      }
      if (roomInfo.drawStatus) {
        drawRoomInfo.drawStatus = roomInfo.drawStatus // 抽签未开始
      }
      if (roomInfo.roomStatus) {
        drawRoomInfo.roomStatus = roomInfo.roomStatus // 已进入抽签准备界面
      }
      drawRoomInfo.roomId = this.$route.query.roomId
      // drawRoomInfo.supervisorName = this.supervisorName
      drawRoomInfo.participant = this.participant
      if (roomInfo.newUserName) {
        drawRoomInfo.newUserName = roomInfo.newUserName
      }
      if (this.$store.getters.authUser.enterpriseType === 1) {
        drawRoomInfo.staffId = this.$store.getters.authUser.userId
        drawRoomInfo.staffName = this.$store.getters.authUser.userName
      }
      drawRoomInfo.enterprises.push({
        userId: this.$store.getters.authUser.userId,
        userName: this.$store.getters.authUser.userName,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        enterpriseName: this.$store.getters.authUser.enterpriseName,
        enterpriseType: this.$store.getters.authUser.enterpriseType,
        headImgUrl: `${commonJs.imageReviewUrl}?filePath=${this.$store.getters.authUser.filePath}`,
        status: this.enterpriseStatus
      })
      return drawRoomInfo
    },
    /** 保存当前分组的信息 */
    saveCurrentGroupInfo (roomInfo) {
      let drawRoomInfo = this.createDraw(roomInfo)
      drawroom.saveDrawRoomInfo(drawRoomInfo).then((res) => {
      })
    },
    /** 修改当前企业的状态（请就位/已就位） */
    changeStatus (item) {
      if (item.btnName === '已就位') {
        item.btnName = '请就位'
        this.enterpriseStatus = 1
      } else {
        item.btnName = '已就位'
        this.enterpriseStatus = 2
      }
      this.saveCurrentGroupInfo(item)
    },
    /** 判断是否可以进入抽签室 */
    checkOrganIfEnterRoom (drawEnterpriseList) {
      let applyEnterpriseLength = ''
      if (this.checkIsHasGroupCompany(this.applyEnterprise)) {
        applyEnterpriseLength = this.applyEnterprise.length
      } else {
        applyEnterpriseLength = this.applyEnterprise.length + 1
      }
      if (drawEnterpriseList.length === applyEnterpriseLength && this.participant) {
        for (let i = 0; i < drawEnterpriseList.length; i++) {
          if (drawEnterpriseList[i].status === 1) {
            break
          } else {
            if (i === drawEnterpriseList.length - 1) {
              this.disabledFlag = false
            } else {
              continue
            }
          }
        }
      }
    },
    /** 判断当前项目分组中是否包办招标办的项目 */
    checkIsHasGroupCompany (selectApplyList) {
      let flag = false
      for (let i = 0; i < selectApplyList.length; i++) {
        if (selectApplyList[i].enterpriseType === 1) {
          flag = true // 有招标办的项目
          break
        }
      }
      return flag
    },
    /** 进入抽签室 */
    goToDroom () {
      // 跳转到抽签室主件
      this.$emit('roomId', this.formProjectInfo.objectId)
      // 截屏
      // this.$emit('screenCapture')
      // this.$router.push({path: '/drawroom/droom', query: {name: 'droom', roomId: this.formProjectInfo.objectId}})
      /* let roomInfo = {}
      roomInfo.roomStatus = 2 // 已进入抽签界面
      this.saveCurrentGroupInfo(roomInfo) */
    },
    /** 得到当前分组的信息 */
    getCurrentGroupInfo () {
      if (this.$route.query.name === 'dready' && this.$route.query.roomId) {
        drawproject.getOne(this.$route.query.roomId).then((res) => {
          if (res) {
            this.formProjectInfo = res.data.projectGroup
            this.projectTabData = res.data.projectGroup.selectApplyList
            this.getApplyEnterprise(res.data.projectGroup.selectApplyList)
            /* this.getDrawEnterpriseInfo(this.$store.getters.messages.enterprises)
            let roomInfo = {}
            this.participant = this.getParticipantInfo(this.$store.getters.messages.enterprises)
            roomInfo.participant = this.participant
            this.saveCurrentGroupInfo(roomInfo) */
            // 是否可输入监督人名称：只有招标办才可以输入名称
            if (this.$store.getters.authUser.enterpriseType === 1) {
              this.inputFlag = false
              this.buttonFlag = true
            }
          } else {
            return false
          }
        })
      }
    },
    /** 获取项目对应的企业信息 */
    getApplyEnterprise (selectApplyList) {
      for (let i = 0; i < selectApplyList.length; i++) {
        if (this.applyEnterprise.length > 0) {
          let flag = false
          for (let j = 0; j < this.applyEnterprise.length; j++) {
            if (this.applyEnterprise[j].enterpriseId !== selectApplyList[i].enterpriseId) {
              flag = true
            } else {
              flag = false
            }
          }
          if (flag) {
            this.applyEnterprise.push(selectApplyList[i])
          }
        } else {
          this.applyEnterprise.push(selectApplyList[i])
        }
      }
    },
    /** 获取参与人员的信息 */
    getParticipantInfo (enterpriseInfo) {
      if (enterpriseInfo) {
        let participant = ''
        for (let i = 0; i < enterpriseInfo.length; i++) {
          if (i === enterpriseInfo.length - 1) {
            participant += enterpriseInfo[i].userName
          } else {
            participant += enterpriseInfo[i].userName
            participant += ' , '
          }
        }
        return participant
      }
    },
    /** 得到参与抽签单位的信息 */
    getDrawEnterpriseInfo (enterpriseInfo) {
      this.organEnterprise = {}
      this.showOrganStatus = false
      this.drawEnterpriseData = []
      this.showDrawStatus = false
      if (enterpriseInfo) {
        let index = 0
        for (let i = 0; i < enterpriseInfo.length; i++) {
          // 组织单位（招标办）信息的获取
          if (enterpriseInfo[i].enterpriseType === 1) {
            this.showOrganStatus = true
            this.organEnterprise = enterpriseInfo[i]
            this.changeBtnStatus(this.organEnterprise)
          } else {
            // 项目单位信息的获取
            this.showDrawStatus = true
            this.drawEnterpriseData[index] = enterpriseInfo[i]
            this.changeBtnStatus(this.drawEnterpriseData[index])
            index++
          }
        }
      }
    },
    /** 参与抽签的单位状态的改变 */
    changeBtnStatus (item) {
      if (item.status === 1) {
        item.btnName = '请就位'
        item.readyStatue = false
      } else {
        item.btnName = '已就位'
        item.readyStatue = true
      }
      if (item.enterpriseId === this.$store.getters.authUser.enterpriseId) {
        item.statusBtnFlag = false
      } else {
        item.statusBtnFlag = true
      }
    }
  },
  filters: {
    filterType: function (type) {
      if (type === 1) {
        return '代理机构选聘'
      } else if (type === 2) {
        return '咨询造价机构'
      } else if (type === 3) {
        return '审计评估机构'
      } else if (type === 4) {
        return '法律服务机构'
      }
    }
  }
}
</script>
<style lang="less">
#readybar-page {
  .dready-tab {
    padding: 10px 20px;
  }
  .project-info {
    height: 330px;
    background: #fff;
    margin-top: 0;
    .pro-color-tit {
      color: #999;
      font-size: 14px;
    }
    .pro-right-tit {
      padding-top: 45px;
    }
  }
  .dready-draw-info {
    height: 590px;
    background: #fff;
    margin-top: 20px;
    padding: 10px;
    overflow-y: auto;
    .card-draw {
      margin-top:20px;
      margin: 10px;
      height: 100px;
      background: #F7F7F9;
      .el-card__body {
        text-align: center;
        padding: 0;
      }
      .card-flag {
        display: inline-block;
        width: 27px;
        height: 90px;
        padding: 5px 0;
        float: left;
        color: #fff;
      }
      .card-avater {
        padding: 15px 50px;
        float: left;
        img {
          width: 70px;
          height: 70px;
          font-size: 25px;
          border-radius: 5px;
          background: #409eff;
        }
      }
      .card-des {
        display: inline-block;
        float: left;
        text-align: left;
        .c-name {
          color: #999;
        }
      }
      .card-states {
        display: inline-block;
        float: right;
        padding: 30px 50px;
        img {
          width: 30px;
          height: 30px;
          padding-right: 30px;
          border-radius: 5px;
        }
      }
      .flag-c {
        background: #FEB91D;
      }
      .flag-d {
        background: #9AAAFF;
      }
    }
  }
  // 表单样式
  .el-form {
    margin-top: 60px;
    .el-form-item__label {
      line-height: 10px;

      color: #999;
    }
    .el-form-item__content {
      line-height: 10px;
      text-align: left;
    }
  }
}
</style>
