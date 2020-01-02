<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="roombar-page">
    <div class="droom-left">
      <!-- 项目基本信息 -->
      <div class="project-info">
        <div class="project-tit">项目基本信息</div>
        <el-row :gutter="20">
          <el-col :span="12">
            <div class="droom-tab">
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
          <el-col :span="6">
            <el-form ref="form" :model="formProjectInfo" label-width="130px">
              <div class="pro-right-tit"><p><span class="pro-color-tit">抽签中介类型:</span><span>{{formProjectInfo.type | filterType}}</span></p></div>
              <div><p><span class="pro-color-tit">抽签数量:</span><span>{{formProjectInfo.extractCount}}</span></p></div>
            </el-form>
          </el-col>
        </el-row>
      </div>
      <!-- 项目基本信息结束 -->
      <!-- 备选中介机构和开始抽签 -->
      <div class="agency-draw-info">
        <el-row :gutter="20">
          <!-- 备选中介机构 -->
          <el-col :span="12">
            <div class="d-agency-l">
              <div class="agency-tit">备选中介机构</div>
              <div class="agency-tab">
                <el-table
                  height="275"
                  :show-header="false"
                  border
                  :data="optionalEnterpriseList"
                  style="width: 100%;">
                  <el-table-column
                    align="left"
                    prop="enterpriseName"
                    label="备选中介机构">
                  </el-table-column>
                </el-table>
              </div>
            </div>
          </el-col>
          <!-- 开始抽签 -->
          <el-col :span="12">
            <!--霓虹灯闪烁边框-->
            <canvasom></canvasom>
            <div class="d-start-r d-border">
              <div class="scroll-ani">
                <ul ref="con1" :class="{anim:animate==true}" id="scrollUlId">
                  <li v-for='(item, index) in drawLotsEnterpriseList' :key="index" :data-id="item.enterpriseId" :data-name="item.enterpriseName">{{item.enterpriseName}}</li>
                </ul>
              </div>
              <div class="start-btn">
                <el-button type="default" class="start-draw-btn" @click="clickStartDraw(startWord)" :disabled= "startWordFlag" :plain="startWordFlag" v-if="!startWordFlag">{{startWord}}</el-button>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
      <!-- 备选中介机构和开始抽签结束 -->
      <!-- 抽签结果和签字区 -->
      <div class="result-sign">
        <el-row :gutter="20">
          <!-- 备选中介机构 -->
          <el-col :span="12">
            <div class="d-result-l">
              <div class="result-tit">抽签结果</div>
              <div class="result-cont">
                <p v-for='(item, index) in afterDrawLotsEnterpriseList'  :key ="index">
                  <span class="result-num result-f-bg" v-if="index === 0">{{index+1}}</span>
                  <span class="result-num result-s-bg" v-else-if="index === 1">{{index+1}}</span>
                  <span class="result-num result-t-bg" v-else-if="index === 2">{{index+1}}</span>
                  <span>{{item.enterpriseName}}</span>
                </p>
              </div>
            </div>
          </el-col>
          <!-- 开始抽签 -->
          <el-col :span="12">
            <div class="d-sign-r">
              <div class="sign-tit">签字区</div>
              <div class="sign-cont">
                <el-form ref="formSign" :model="formSign" label-width="130px">
                  <!--<el-form-item label="监督人:">
                    <span>{{formSign.supervisorName}}</span>
                  </el-form-item>-->
                  <el-form-item label="工作人员:">
                    <span>{{formSign.staffName}}</span>
                  </el-form-item>
                  <el-form-item label="抽签地点:">
                    <!--<el-input v-model="drawLotsPlace" :disabled="inputFlag" @change="saveDrawLotsPlace"></el-input>-->
                    <el-select v-model="drawLotsPlace" placeholder="请选择" :disabled="inputFlag" @change="saveDrawLotsPlace">
                      <el-option
                        v-for="item in drawLotsPlaceList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-form>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="droom-upload">
        <el-upload
          class="upload-demo"
          :before-upload="beforeAvatarUpload"
          :action="upload"
          :on-change="handleChange"
          :on-success="uploadSuccess"
          :on-error="uploadError"
          :file-list="fileList">
          <el-button type="success" v-if="this.btnFlag">上传截屏</el-button>
        </el-upload>
      </div>
      <!-- 抽签结果和签字区结束 -->
      <!-- 按钮start -->
      <div class="droom-save-btn">
        <el-button type="primary" @click="clickSaveBtn" v-if="this.btnFlag">保存</el-button>
        <el-button type="warning" @click="clickResetBtn" v-if="this.btnFlag">重新抽签</el-button>
      </div>
      <!-- 按钮end -->
    </div>
  </div>
</template>
<script>
import {drawproject, drawroom, enterprise} from '../../../../api/index'
import {commonJs} from '../../../../common/js/common'
import canvasom from './canvas'
// import {WebPushSocket} from '@/common/js/websocket'
// 实例
export default {
  data () {
    return {
      animate: false,
      projectTabData: [],
      formSign: {},
      optionalEnterpriseList: [],
      optionalEnterpriseLength: 0,
      timeStamp: 0,
      newLIst: [],
      startWord: '开始抽签',
      startWordFlag: false,
      drawLotsEnterpriseList: [], // 参与抽签的中介机构信息
      afterDrawLotsEnterpriseList: [], // 抽签后的中介机构信息
      formProjectInfo: {},
      drawlotsResult: {}, // 抽签结果备案信息
      inputFlag: true, // 是否可输入抽签地址,默认不可填写
      btnFlag: false, // 参与抽签的单位是否有保存抽签结果和重新抽签的权利，默认没有
      drawLotsPlace: '', // 抽签地址
      startEnterpriseId: '', // 开始抽签的企业id
      endEnterpriseId: '', // 结束抽签的企业id
      upload: commonJs.fileUploadUrl,
      fileList: [], // 上传文件列表
      fileScreenCapture: [],
      drawLotsPlaceList: [
        {
          label: '在线抽签',
          value: 1
        },
        {
          label: '集团公司招标办',
          value: 2
        }
      ],
      drawResultFirst: [],
      drawResultSecond: [],
      drawResultThird: []
    }
  },
  props: ['roomId'],
  computed: {
    getRoomMessages () {
      return this.$store.getters.messages
    }
  },
  watch: {
    getRoomMessages: function (newV, oldV) {
      this.getDrawStatus(this.$store.getters.messages)
    }
  },
  methods: {
    /** 截屏上传成功 */
    uploadSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.fileScreenCapture.push(file.response.fileInformation)
      }
    },
    // 上传失败
    uploadError (err, file, fileList) {
      // console.log(err)
    },
    handleChange () {
    },
    /** 上传限制 */
    beforeAvatarUpload (file) {
      const isLtM = file.size / 1024 / 1024 <= 10
      if (!isLtM) {
        this.$message.error('上传文件大小不能超过 10M!')
      }
      return isLtM
    },
    /** 保存抽签地址信息 */
    saveDrawLotsPlace () {
      if (this.drawLotsPlace === '' || this.drawLotsPlace === undefined) {
        this.$confirm('抽签地址不能为空', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {}).catch(() => {
          return false
        })
      } else {
        let drawRoomInfo = {}
        drawRoomInfo.drawLotsPlace = this.drawLotsPlace === 1 ? '在线抽签' : '集团公司招标办'
        drawRoomInfo.drawStatus = this.$store.getters.messages.drawStatus
        this.saveCurrentDrawResults(drawRoomInfo)
      }
    },
    /** 是否开始进行抽签 */
    getDrawStatus (drawInfo) {
      // 更新抽签地址信息
      if (drawInfo.drawLotsPlace !== undefined) {
        this.drawLotsPlace = drawInfo.drawLotsPlace === '在线抽签' ? 1 : 2
      } else {
        this.drawLotsPlace = ''
      }
      if (drawInfo.drawStatus === 1) {
        // 清空抽签结果信息
        this.afterDrawLotsEnterpriseList = []
        // 停止动画效果
        // this.stopScroll()
        this.startWord = '开始抽签'
        if (this.startEnterpriseId !== this.$store.getters.authUser.enterpriseId) {
          this.startWordFlag = true
        } else {
          this.startWordFlag = false
        }
        // this.formSign.supervisorName = drawInfo.supervisorName
      } else if (drawInfo.drawStatus === 2) {
        if (drawInfo.senderId !== this.$store.getters.authUser.userId) {
          this.setInTime()
        }
        this.startWordFlag = true
      } else if (drawInfo.drawStatus === 3) {
        // 设置抽签结果信息
        this.afterDrawLotsEnterpriseList = this.$store.getters.messages.results
      } else if (drawInfo.drawStatus === 4) {
        // 设置抽签结果信息
        this.afterDrawLotsEnterpriseList = this.$store.getters.messages.results
      } else if (drawInfo.drawStatus === 5) {
        // 设置抽签结果信息
        this.afterDrawLotsEnterpriseList = this.$store.getters.messages.results
      } else if (drawInfo.drawStatus === 6) {
        this.stopScroll()
      } else if (drawInfo.drawStatus === 7) {
        this.$router.push({path: '/draw/drawroom'}) // 抽签结果已完成，返回到主页面
      }
    },
    /** 点击开始抽签 */
    clickStartDraw (name) {
      if (name === '开始抽签') {
        this.startWordFlag = true
        this.stopScroll()
        // 开始滚动
        this.setInTime()
        setTimeout(this.drawProcess0, 1000)
        setTimeout(this.drawProcess1, 3000)
        setTimeout(this.drawProcess2, 6000)
        setTimeout(this.drawProcess3, 9000)
        setTimeout(this.drawProcess4, 10000)
      }
    },
    setInTime () {
      this.timeStamp = setInterval(this.scroll, 50)
    },
    /** 新建websocket对象 */
    createDraw (roomInfo) {
      let drawRoomInfo = {
        drawStatus: '',
        roomStatus: '',
        roomId: '',
        supervisorName: '',
        staffName: '',
        staffId: '',
        drawLotsPlace: '',
        enterprises: [],
        results: []
      }
      if (roomInfo.drawStatus) {
        drawRoomInfo.drawStatus = roomInfo.drawStatus
      } else {
        drawRoomInfo.drawStatus = this.$store.getters.messages.drawStatus
      }
      if (roomInfo.roomStatus) {
        drawRoomInfo.roomStatus = roomInfo.roomStatus
      } else {
        drawRoomInfo.roomStatus = this.$store.getters.messages.roomStatus
      }
      drawRoomInfo.roomId = this.roomId
      drawRoomInfo.supervisorName = roomInfo.supervisorName
      if (this.$store.getters.authUser.enterpriseType === 1) {
        drawRoomInfo.staffId = this.$store.getters.authUser.userId
        drawRoomInfo.staffName = this.$store.getters.authUser.userName
      }
      drawRoomInfo.drawLotsPlace = roomInfo.drawLotsPlace
      drawRoomInfo.enterprises.push({
        userId: this.$store.getters.authUser.userId,
        userName: this.$store.getters.authUser.userName,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        enterpriseName: this.$store.getters.authUser.enterpriseName,
        enterpriseType: this.$store.getters.authUser.enterpriseType,
        headImgUrl: `${commonJs.imageReviewUrl}?filePath=${this.$store.getters.authUser.filePath}`,
        status: 2
      })
      /* if (roomInfo.drawResults && roomInfo.drawResults.length > 0) {
        drawRoomInfo.results = roomInfo.drawResults
      } else {
        drawRoomInfo.results = this.$store.getters.messages.results
      } */
      drawRoomInfo.results = roomInfo.drawResults
      return drawRoomInfo
    },
    /** 保存websocket对象 */
    saveCurrentDrawResults (roomInfo) {
      let drawRoomInfo = this.createDraw(roomInfo)
      drawroom.saveDrawRoomInfo(drawRoomInfo).then(() => {
      })
    },
    scroll () {
      this.animate = true// 因为在消息向上滚动的时候需要添加css3过渡动画，所以这里需要设置true
      this.drawLotsEnterpriseList.push(this.drawLotsEnterpriseList[0]) // 将数组的第一个添加到数组
      this.drawLotsEnterpriseList.shift()
      this.animate = false // margin-top 为0 的时候取消过渡动画，实现无缝滚动
    },
    stopScroll () {
      window.clearInterval(this.timeStamp)
    },
    /** 保存 */
    clickSaveBtn () {
      if (this.afterDrawLotsEnterpriseList.length === 0 || this.drawLotsPlace === '') {
        this.$confirm('抽签结果以及抽签地点均不能为空, 请核实', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {}).catch(() => {
          return false
        })
      } else {
        // 关联业务编号
        this.drawlotsResult.relatedCode = this.formProjectInfo.code
        // 抽签地点
        this.drawlotsResult.drawLotsPlace = this.$store.getters.messages.drawLotsPlace
        // 监督人信息
        /* this.drawlotsResult.superintendent = this.$store.getters.messages.supervisorName */
        // 参与人员信息
        this.drawlotsResult.participant = this.$store.getters.messages.participant
        // 选聘类型
        this.drawlotsResult.type = this.formProjectInfo.type
        // 工作人员信息
        this.drawlotsResult.staffId = this.$store.getters.messages.staffId
        // 图片上传信息
        this.drawlotsResult.fileScreenCapture = this.fileScreenCapture
        //  抽签后的中介机构信息
        if (this.afterDrawLotsEnterpriseList.length > 0) {
          let info = []
          for (let i = 0; i < this.afterDrawLotsEnterpriseList.length; i++) {
            let obj = {}
            obj.rank = i + 1
            obj.enterpriseId = this.afterDrawLotsEnterpriseList[i].enterpriseId
            obj.enterpriseName = this.afterDrawLotsEnterpriseList[i].enterpriseName
            info[i] = obj
          }
          this.drawlotsResult.biddingEntrustList = info
        }
        drawroom.save(this.drawlotsResult).then((res) => {
          if (res.data.resCode === '0000') {
            // 将抽签结果信息保存到redis中
            let roomInfo = {}
            roomInfo.drawStatus = 7
            roomInfo.roomStatus = 3 // 抽签结束退出房间
            this.saveCurrentDrawResults(roomInfo)
            this.$router.push({path: '/draw/drawroom'})
          } else {
            return false
          }
        })
      }
    },
    /** 重新抽签 */
    clickResetBtn () {
      this.drawLotsPlace = '' // 清空抽签地址信息
      this.afterDrawLotsEnterpriseList = []
      // this.getCurrentGroupInfo()
      let roomInfo = {}
      roomInfo.drawStatus = 1
      roomInfo.drawLotsPlace = this.drawLotsPlace
      roomInfo.drawResults = []
      // 清空截屏文件_bug24180
      this.fileList = []
      this.fileScreenCapture = []
      this.saveCurrentDrawResults(roomInfo)
    },
    /** 得到当前分组信息 */
    getCurrentGroupInfo () {
      if (this.$route.query.roomId) {
        drawproject.getOne(this.$route.query.roomId).then((res) => {
          if (res) {
            this.drawLotsEnterpriseList = []
            this.formProjectInfo = res.data.projectGroup
            this.endEnterpriseId = res.data.projectGroup.endEnterpriseId
            this.startEnterpriseId = res.data.projectGroup.startEnterpriseId
            // 判断当前用户所在企业是否有点击抽签开始按钮的权利
            if (this.startEnterpriseId !== this.$store.getters.authUser.enterpriseId) {
              this.startWordFlag = true
            }
            this.getIsOptionedEnterprise(res.data.projectGroup.selectEnterpriseIds)
            this.projectTabData = res.data.projectGroup.selectApplyList
            let roomInfo = {}
            roomInfo.roomStatus = 2 // 已进入抽签界面
            this.saveCurrentDrawResults(roomInfo)
          } else {
            return false
          }
        })
        // this.formSign.supervisorName = this.$store.getters.messages.supervisorName
        this.formSign.staffName = this.$store.getters.messages.staffName
        // 是否可输入抽签地址：只有招标办才可以输入该地址
        if (this.$store.getters.authUser.enterpriseType === 1) {
          this.inputFlag = false
          this.btnFlag = true
        }
        // 观测抽签地点是否有值，若有值则进行赋值
        if (this.$store.getters.messages.drawLotsPlace) {
          this.drawLotsPlace = this.$store.getters.messages.drawLotsPlace === '在线抽签' ? 1 : 2
        }
      }
    },
    /** 得到已经选择的备选机构的名称 */
    getIsOptionedEnterprise (selectEnterpriseIds) {
      let includeIds = ''
      this.optionalEnterpriseList = []
      this.drawLotsEnterpriseList = []
      if (selectEnterpriseIds.length > 0) {
        for (let i = 0; i < selectEnterpriseIds.length; i++) {
          if (i === selectEnterpriseIds.length - 1) {
            includeIds += selectEnterpriseIds[i]
          } else {
            includeIds += (selectEnterpriseIds[i] + ',')
          }
        }
      }
      enterprise.getAll({
        includeIds: includeIds
      }).then((res) => {
        if (res.data.enterprises.length > 0) {
          for (let j = 0; j < res.data.enterprises.length; j++) {
            this.optionalEnterpriseList.push({
              enterpriseId: res.data.enterprises[j].objectId,
              enterpriseName: res.data.enterprises[j].name
            })
            this.drawLotsEnterpriseList.push({
              enterpriseId: res.data.enterprises[j].objectId,
              enterpriseName: res.data.enterprises[j].name
            })
          }
        }
      })
      // 观测是否有中签结果，如果有结果则进行赋值
      if (this.$store.getters.messages.results) {
        this.afterDrawLotsEnterpriseList = []
        this.afterDrawLotsEnterpriseList = this.$store.getters.messages.results
      }
    },
    /** 抽签过程结果
     * unitNum [参与抽签单位的数组]
     * 条件1.第一次抽中单位不参与下一次抽签
     * 条件2.每次抽中一个结果推送数据给其他用户
     * 条件3.结果中有三家单位停止抽签
    */
    drawProcess0 () {
      let roomInfo = {}
      roomInfo.drawStatus = 2
      this.saveCurrentDrawResults(roomInfo)
    },
    drawProcess1 (unitNum = this.drawLotsEnterpriseList) {
      this.drawResultFirst = []
      let random = this.getRandomNum(unitNum)
      // 第一次抽签结果
      this.drawResultFirst.push(unitNum[random])
      // 提交的数据
      let roomInfo = {}
      roomInfo.drawStatus = 3
      roomInfo.drawResults = this.drawResultFirst
      this.saveCurrentDrawResults(roomInfo)
    },
    drawProcess2 (unitNum = this.drawLotsEnterpriseList) {
      this.drawResultSecond = []
      let seUnit = unitNum.filter(item => item !== this.drawResultFirst[0])
      let random = this.getRandomNum(seUnit)
      this.drawResultSecond.push(seUnit[random])
      // 提交的数据
      let roomInfo = {}
      roomInfo.drawStatus = 4
      roomInfo.drawResults = this.drawResultSecond
      this.saveCurrentDrawResults(roomInfo)
    },
    drawProcess3 (unitNum = this.drawLotsEnterpriseList) {
      this.drawResultThird = []
      let thUnit = unitNum.filter(item => item !== this.drawResultFirst[0] && item !== this.drawResultSecond[0])
      let random = this.getRandomNum(thUnit)
      this.drawResultThird.push(thUnit[random])
      // 提交的数据
      let roomInfo = {}
      roomInfo.drawStatus = 5
      roomInfo.drawResults = this.drawResultThird
      this.saveCurrentDrawResults(roomInfo)
    },
    drawProcess4 () {
      let roomInfo = {}
      roomInfo.drawStatus = 6
      this.saveCurrentDrawResults(roomInfo)
    },
    // 随机数(根据参加抽签的企业数量生成随机数)
    getRandomNum (unitNum) {
      // 参与抽签项目个数
      let num = unitNum.length
      let count = Math.floor(Math.random() * num)
      return count
    }
    /** 从抽签室退出后要直接返回到抽签室界面 */
  /*  goRoom () {
      const webPushSocket = new WebPushSocket()
      webPushSocket.setSettings(this.$store.getters.authUser.userId, this.$store.getters.authUser.enterpriseId, this.$route.query.roomId || '')
      webPushSocket.init()
      // 将当前企业的信息保存到redis中
      let roomInfo = {}
      this.saveCurrentDrawResults(roomInfo)
      this.webPushSocket = webPushSocket
      this.$emit('webSocket', webPushSocket)
      // this.$emit('roomId', this.$route.query.roomId)
      // 观测是否有中签结果，如果有结果则进行赋值
      if (this.$store.getters.messages.results) {
        // 设置滚动视窗数据-保持数据一致
        this.drawLotsEnterpriseList = this.$store.getters.messages.results
        // 设置抽签结果信息
        this.afterDrawLotsEnterpriseList.push(this.drawLotsEnterpriseList[2])
        this.afterDrawLotsEnterpriseList.push(this.drawLotsEnterpriseList[1])
        this.afterDrawLotsEnterpriseList.push(this.drawLotsEnterpriseList[3])
      }
      // 观测抽签地点是否有值，若有值则进行赋值
      if (this.$store.getters.messages.drawLotsPlace) {
        this.drawLotsPlace = this.$store.getters.messages.drawLotsPlace === '在线抽签' ? 1 : 2
      }
    } */
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
  },
  created () {
    /* if (this.$store.getters.messages.roomStatus === 2) {
      // this.goRoom()
    } else {
      let roomInfo = {}
      roomInfo.roomStatus = 2 // 已进入抽签界面
      this.saveCurrentDrawResults(roomInfo)
    } */
  },
  // 实例创建完成
  mounted () {
    setTimeout(() => {
      this.getCurrentGroupInfo()
    }, 500)
  },
  // 注册组件
  components: {
    // 霓虹灯
    canvasom
  }
}
</script>
<style lang="less">
#roombar-page {
  // 项目基本信息
  .project-info {
    height: 330px;
    background: #fff;
    margin-top: 20px;
    margin-top: 0;
    .pro-color-tit {
      color: #999;
      font-size: 14px;
    }
    .pro-right-tit {
      padding-top: 45px;
    }
  }
  .droom-tab {
    padding: 10px;
  }
  // 备选中介机构
  .agency-draw-info {
    margin-top: 20px;
    .d-agency-l, .d-start-r {
      height: 330px;
      background: #fff;
      box-sizing: border-box;
    }
    .d-start-r {
      position: absolute;
      height: 265px;
      right: 43px;
      border-radius: 5%;
      width: 41%;
      top: 21px;
    }
    /*.d-border {*/
      /*border: 15px solid #FF9C00;*/
    /*}*/
    // 轮播
    .scroll-ani {
      height: 230px;
      opacity: 0.9;
      /*border: 1px solid #000;*/
      margin: 5px;
      overflow: hidden;
      transition: all 0.1s;
      ul {
        padding-left: 0;
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
    .el-carousel__item h3 {
      color: #475669;
      font-size: 14px;
      opacity: 0.75;
      line-height: 200px;
      margin: 0;
    }
    .el-carousel__item:nth-child(2n) {
      background-color: #99a9bf;
    }
    .el-carousel__item:nth-child(2n+1) {
      background-color: #d3dce6;
    }
    .agency-tab {
      padding: 10px;
    }
  }
  // 开始抽签按钮
  .start-draw-btn {
    background: #FD721F;
    color: #fff;
    box-shadow: 5px 5px 5px #888888;
  }
  // 抽签结构和签字区
  .result-sign {
    margin-top: 20px;
    margin-bottom: 30px;
    .d-result-l, .d-sign-r {
      height: 260px;
      background: #fff;
    }
    .result-cont {
      text-align: left;
      padding-left: 20px;
      .result-num {
        display: inline-block;
        width: 20px;
        height: 20px;
        text-align: center;
        margin-right: 20px;
      }
      .result-f-bg {
        background: #FF4442;
      }
      .result-s-bg {
        background: #FE9D00;
      }
      .result-t-bg {
        background: #4064F6;
      }
    }
    .sign-cont {
      padding: 20px 10px;
    }
    .el-form-item__content {
      text-align: left;
    }
  }
  // 按钮-保存-重新抽签
  .droom-save-btn {
    float: right;
    width: 48%;
    text-align: left;
    margin-left: 20px;
    padding-bottom: 100px;
  }
  .droom-upload {
    float: left;
    width: 45%;
    text-align: right;
  }
  // 公共部分
  .project-tit, .agency-tit, .result-tit, .sign-tit, .video-tit {
    height: 30px;
    background: #F7F6FB;
    text-align: left;
    padding-top: 10px;
    padding-left: 20px;
    font-size: 14px;
    color: black;
  }
  .anim{
    transition: all 0.5s;
  }
}
</style>
