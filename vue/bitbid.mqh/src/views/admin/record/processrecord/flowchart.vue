<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="flowchart-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招标备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/record/processrecord' }">招标过程备案</el-breadcrumb-item>
        <el-breadcrumb-item>招标项目名称</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <!-- 流程图区域start -->
    <div class="flowchart-box">
      <div class="flow-step">
        <div class="step-btn">
          <el-row type="flex" justify="space-between">
            <el-col :span="2">
              <el-button type="primary" @click="clickBtnStep('bidProject', colorValue[12], 12)"
                :class="{'color-btnbg':colorValue[12].colorBg}">
                招标项目
              </el-button>
            </el-col>
            <el-col :span="2">
              <el-button
                @click="clickBtnStep('bidPhase', colorValue[0], 0)"
                :type="colorValue[0].bidPhase?'primary':'info'"
                :disabled="colorValue[0].disabledValue"
                :class="{'color-btnbg':colorValue[0].colorBg}">
                标段
              </el-button>
            </el-col>
            <el-col :span="2">
              <div class="up-bidsign">
                <!-- 垂直线 -->
                <div class="line-vertical"></div>
                <el-button
                  @click="clickBtnStep('bidRegist', colorValue[2], 2)"
                  :type="colorValue[2].bidRegist?'primary':'info'"
                  :disabled="colorValue[2].disabledValue"
                  :class="{'color-btnbg':colorValue[2].colorBg}">
                  投标报名
                </el-button>
              </div>
              <el-dropdown @command="handleCommand">
                <el-button
                  style="margin-left: -15px;"
                  @click="clickBtnStep('notice', colorValue[1], 1)"
                  :type="colorValue[1].notice?'primary':'info'"
                  :disabled="colorValue[1].disabledValue"
                  :class="{'color-btnbg':colorValue[1].colorBg}">
                  {{noticeType}}<i class="el-icon-arrow-down el-icon--right"></i>
                </el-button>
               <el-dropdown-menu slot="dropdown" style="position:absolute; z-index: 9999999;">
                  <el-dropdown-item :command="item" v-for="(item, index) in noticeList" :key="index">{{item.noticeName}}</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </el-col>
            <el-col :span="2" v-show="noticeType ==='资格预审公告'">
              <div class="up-bidsign">
                <!-- 垂直线 -->
                <div class="line-vertical"></div>
                <el-button
                  @click="clickBtnStep('qualAnswer', colorValue[3], 3)"
                  :type="colorValue[3].qualAnswer?'primary':'info'"
                  :disabled="colorValue[3].disabledValue"
                  :class="{'color-btnbg':colorValue[3].colorBg}">
                 答疑补遗
                 </el-button>
              </div>
              <el-button
                @click="clickBtnStep('qualFile', colorValue[4], 4)"
                :type="colorValue[4].qualFile?'primary':'info'"
                :disabled="colorValue[4].disabledValue"
                :class="{'color-btnbg':colorValue[4].colorBg}">
               资格预审文件
              </el-button>
            </el-col>
            <el-col :span="2" v-show="noticeType ==='资格预审公告'">
              <el-button
                 @click="clickBtnStep('qualPre', colorValue[5], 5)"
                 :type="colorValue[5].qualPre?'primary':'info'"
                 :disabled="colorValue[5].disabledValue"
                 :class="{'color-btnbg':colorValue[5].colorBg}">
               资格预审会
               </el-button>
            </el-col>
            <el-col :span="2">
              <div class="up-bidsign">
                <!-- 垂直线 -->
                <div class="line-vertical" style="margin-left: 50px;height: 50px;"></div>
                <div class="line-ver-leavl"></div>
                <el-button
                  style="margin-left: -55px;"
                  @click="clickBtnStep('bidAnswer', colorValue[7], 7)"
                  :type="colorValue[7].bidAnswer?'primary':'info'"
                  :disabled="colorValue[7].disabledValue"
                  :class="{'color-btnbg':colorValue[7].colorBg}">
                  答疑补遗
                </el-button>
                <el-button
                  @click="clickBtnStep('bidPrice', colorValue[6], 6)"
                  :type="colorValue[6].bidPrice?'primary':'info'"
                  :disabled="colorValue[6].disabledValue"
                  :class="{'color-btnbg':colorValue[6].colorBg}">
                  招标控制价
                </el-button>
              </div>
              <el-button
                @click="clickBtnStep('bidFile', colorValue[8], 8)"
                :type="colorValue[8].bidFile?'primary':'info'"
                :disabled="colorValue[8].disabledValue"
                :class="{'color-btnbg':colorValue[8].colorBg}">
               招标文件
              </el-button>
            </el-col>
            <el-col :span="2">
              <el-button
                @click="clickBtnStep('openBid', colorValue[9], 9)"
                :type="colorValue[9].openBid?'primary':'info'"
                :disabled="colorValue[9].disabledValue"
                :class="{'color-btnbg':colorValue[9].colorBg}">
              开评标
              </el-button>
            </el-col>
            <el-col :span="2">
              <el-button
                @click="clickBtnStep('candNotice', colorValue[10], 10)"
                :type="colorValue[10].candNotice?'primary':'info'"
                :disabled="colorValue[10].disabledValue"
                :class="{'color-btnbg':colorValue[10].colorBg}">
               候选人公示
              </el-button>
            </el-col>
            <el-col :span="2">
              <el-button
                @click="clickBtnStep('resNotice', colorValue[11], 11)"
                :type="colorValue[11].resNotice?'primary':'info'"
                :disabled="colorValue[11].disabledValue"
                :class="{'color-btnbg':colorValue[11].colorBg}">
              结果公示
              </el-button>
            </el-col>
            <!-- 水平线 -->
            <div class="line-level"></div>
            <el-col :span="2">
              <el-button
                @click="clickBtnStep('resChange', colorValue[13], 13)"
                :type="colorValue[13].resChange?'primary':'info'"
                :disabled="colorValue[13].disabledValue"
                :class="{'color-btnbg':colorValue[13].colorBg}">
                结果变更
              </el-button>
            </el-col>
            <!-- 水平线 -->
            <div class="line-level"></div>
          </el-row>
        </div>
      </div>
    </div>
    <!-- 流程图区域end -->

    <!-- 内容区域start -->
    <div class="maincont-box">
      <transition name="slide-fade" mode="out-in">
        <component :is="currentView" :callbackdata="callbackdata" :noticeCode="noticeCode" :relatedType="relatedType" :sectionOperationFlag="sectionOperationFlag"
                   :operationCheckFlag = "operationCheckFlag" :dialogCheckVisible="this.$route.query.key" @sectionlist="sectionlist"  @checkNoticeFlag="checkNoticeFlag" @noticeProgress="noticeProgress">
          <!-- 组件在 vm.currentview 变化时改变！ -->
        </component>
      </transition>
    </div>
    <!-- 内容区域end -->
  </div>
</template>
<script>
import {tenderproject, noticeProgress, notice, section} from 'api/index'
export default {
  data () {
    return {
      // 流程控制参数
      colorValue: [
        {bidPhase: false, disabledValue: true, colorBg: false},
        {notice: false, disabledValue: true, colorBg: false},
        {bidRegist: false, disabledValue: true, colorBg: false},
        {qualAnswer: false, disabledValue: true, colorBg: false},
        {qualFile: false, disabledValue: true, colorBg: false},
        {qualPre: false, disabledValue: true, colorBg: false},
        {bidPrice: false, disabledValue: true, colorBg: false},
        {bidAnswer: false, disabledValue: true, colorBg: false},
        {bidFile: false, disabledValue: true, colorBg: false},
        {openBid: false, disabledValue: true, colorBg: false},
        {candNotice: false, disabledValue: true, colorBg: false},
        {resNotice: false, disabledValue: true, colorBg: false},
        {bidProject: false, disabledValue: false, colorBg: false},
        {resChange: false, disabledValue: true, colorBg: false}
      ],
      noticeType: '公告',
      currentView: 'bidProject',
      callbackdata: '',
      noticeCode: '', // 选中公告的code
      sectionOperationFlag: true, // 标段中的操作按钮默认展示
      isPrequalification: 0, // 是否资格预审(0.否 1.是)
      noticeList: [], // 当前招标项目中所包含的招标公告信息
      relatedType: null, // 答疑补遗关联类型：资格预审文件澄清：1 招标文件澄清：2
      currentNoticeProgress: '', // 当前公告流程
      operationCheckFlag: true // 所有页面的整体状态，默认按钮可见 (招标变更查询中使用)
    }
  },
  computed: {
    getTenderProjectCode () {
      return this.callbackdata
    }
  },
  watch: {
    getTenderProjectCode () {
      this.getAllNoticeInfo()
    }
  },
  created () {
    this.getPrequalificationStatus()
    this.getSectionInfo()
    this.getNoticeInfo()
    this.getNoticeProgress()
  },

  /** 方法集 */
  methods: {
    checkNoticeFlag (checkNoticeFlag) {
      this.sectionOperationFlag = checkNoticeFlag
    },
    sectionlist (sectionlist) {
      this.colorValue = this.$options.data().colorValue
      if (sectionlist.length > 0) {
        this.currentNoticeProgress = 'notice'
      } else {
        this.currentNoticeProgress = 'bidPhase'
      }
      this.setStepHighlight()
    },
    noticeProgress (noticeProgress) {
      this.colorValue = this.$options.data().colorValue
      if (noticeProgress === 3) {
        this.currentNoticeProgress = 'qualPre'
      } else if (noticeProgress === 4) {
        this.currentNoticeProgress = 'bidFile'
      } else if (noticeProgress === 5) {
        this.currentNoticeProgress = 'openBid'
      } else if (noticeProgress === 6) {
        this.currentNoticeProgress = 'candNotice'
      } else if (noticeProgress === 7) {
        this.currentNoticeProgress = 'resNotice'
      } else if (noticeProgress === 8) {
        this.currentNoticeProgress = 'resChange'
      }
      this.setStepHighlight()
    },
    /** 是否资格预审 */
    getPrequalificationStatus () {
      if (this.$route.query && this.$route.query.tenderProjectCode) {
        this.callbackdata = this.$route.query.tenderProjectCode
        tenderproject.getOneByCode(this.callbackdata).then((res) => {
          this.isPrequalification = res.data.tenderProject.isPrequalification
          // 招标方式
          if (res.data.tenderProject.biddingType !== 1) {
            this.operationCheckFlag = false
          }
          if (this.isPrequalification === 0) {
            this.noticeType = '招标公告'
          } else {
            this.noticeType = '资格预审公告'
          }
        })
      }
    },
    getSectionInfo () {
      section.getAllByTenderCode(this.callbackdata).then((res) => {
        // 判断当前招标项目中是否有标段信息的存在
        if (res.data.sectionList.length > 0) {
          this.currentNoticeProgress = 'notice'
        } else {
          this.currentNoticeProgress = 'bidPhase'
        }
        this.setStepHighlight()
      })
    },
    /** 获取该招标项目下所有的公告信息 */
    getAllNoticeInfo () {
      notice.getList({
        pageNo: 1,
        pageSize: 0,
        tenderProjectCode: this.callbackdata
      }).then((res) => {
        this.noticeList = []
        res.data.noticesList.list.forEach(notice => {
          if (notice.noticeStatus === 4) {
            this.noticeList.push(notice)
          }
        })
      })
    },
    /** 获取点击进入的公告信息 */
    getNoticeInfo () {
      if (this.$route.query && this.$route.query.tenderProjectCode && this.$route.query.noticeCode) {
        this.noticeCode = this.$route.query.noticeCode
        noticeProgress.getOne({
          tenderProjectCode: this.$route.query.tenderProjectCode,
          noticeCode: this.$route.query.noticeCode
        }).then((res) => {
          let progressStage = res.data.noticeProgress.progressStage
          notice.getByCode(this.noticeCode).then((res) => {
            let isLocked = res.data.notice.isLocked
            this.currentNoticeProgress = this.filterProgressStage(progressStage, Number(isLocked))
            this.setStepHighlight()
          })
        })
      }
    },
    filterProgressStage (progressStage, isLocked) {
      let stage = ''
      if ((progressStage === 1) && isLocked === 1) {
        // 针对撤销公告和补充公告
        stage = 1
      } else if (isLocked === 0) {
        stage = progressStage
      } else if (isLocked === 1) {
        stage = progressStage - 1
      }
      if (stage === 1) {
        return 'notice'
      } else if (stage === 2) {
        return 'qualFile'
      } else if (stage === 3) {
        return 'qualPre'
      } else if (stage === 4) {
        return 'bidFile'
      } else if (stage === 5) {
        return 'openBid'
      } else if (stage === 6) {
        return 'candNotice'
      } else if (stage === 7) {
        return 'resNotice'
      } else if (stage === 8) {
        return 'resChange'
      }
    },
    /** 公告类型 */
    handleCommand (command) {
      this.noticeCode = command.code
      this.colorValue = this.$options.data().colorValue
      noticeProgress.getOne({
        tenderProjectCode: this.$route.query.tenderProjectCode,
        noticeCode: this.noticeCode
      }).then((res) => {
        let progressStage = res.data.noticeProgress.progressStage
        notice.getByCode(this.noticeCode).then((res) => {
          let isLocked = res.data.notice.isLocked
          this.currentNoticeProgress = this.filterProgressStage(progressStage, Number(isLocked))
          this.setStepHighlight()
        })
      })
      // this.noticeType = '公告'
      // this.noticeType = `${command}${this.noticeType}`
    },
    /** 点击高亮按钮 */
    clickBtnStep (type, obj, index) {
      this.setStepClickColor(type, obj, index)
      switch (type) {
        case 'bidProject':
          this.currentView = 'bidProject'
          break
        case 'bidPhase':
          this.currentView = 'bidPhase'
          break
        case 'bidRegist':
          this.currentView = 'bidRegist'
          break
        case 'notice':
          this.currentView = 'notice'
          break
        case 'qualAnswer':
          this.currentView = 'answerQues'
          this.relatedType = 1
          break
        case 'qualFile':
          this.currentView = 'qualFile'
          break
        case 'qualPre':
          this.currentView = 'qualPre'
          break
        case 'bidPrice':
          this.currentView = 'bidPrice'
          break
        case 'bidAnswer':
          this.currentView = 'answerQues'
          this.relatedType = 2
          break
        case 'bidFile':
          this.currentView = 'bidFile'
          break
        case 'openBid':
          this.currentView = 'openBid'
          break
        case 'candNotice':
          this.currentView = 'candNotice'
          break
        case 'resNotice':
          this.currentView = 'resNotice'
          break
        case 'resChange':
          this.currentView = 'resChange'
          break
        default:
          // this.colorValue
      }
    },
    /** 根据返回的进度-找到应该高亮的值数组 */
    getHighlightArr (speed) {
      let arr = []
      let obj = {}
      if (speed) {
        obj[speed] = false
        let len = this.colorValue
        // 返回进度数组
        for (let i = 0; i < len.length; i++) {
          let key = this.getBtnKey(len[i])
          // 终止循环
          if (key === speed) {
            break
          }
          arr.push(len[i])
        }
      }
      // 合并
      obj = Object.assign(obj, {disabledValue: true, colorBg: false})
      arr.push(obj)
      return arr
    },
    /** 获得指定的key */
    getBtnKey (obj, index = 0) {
      let str = ''
      let arr = this.transJsonToArr(obj)
      if (arr[index]) {
        str = arr[index].split(':')[0]
        str = str.substring(0, str.length - 1)
      }
      return str
    },
    /** 将对象转成数组 */
    transJsonToArr (jsonData) {
      let str = JSON.stringify(jsonData)
      let arr = str.substring(2, str.length - 1).split(',')
      return arr
    },
    /** 根据进度状态设置按钮高亮 */
    setStepHighlight () {
      // let newArr = this.getHighlightArr('resNotice')
      let newArr = this.getHighlightArr(this.currentNoticeProgress)
      if (newArr) {
        newArr.map((item, index) => {
          let key = this.getBtnKey(item) // 选取的按钮名称
          let disKey = this.getBtnKey(item, 1) // disabledValue属性
          let newKey = disKey.substring(1, disKey.length) // disabledValue属性
          this.colorValue[index][key] = true // this.colorValue[index]['disabledValue']
          this.colorValue[index][newKey] = false
        })
      }
    },
    /** 设置点击颜色 */
    setStepClickColor (btnType, obj, index) {
      // 重置流程图数据
      this.colorValue = this.$options.data().colorValue
      // 设置高亮-可点击的流程按钮
      this.setStepHighlight()
      if (btnType) {
        // 设置当前点击按钮高亮
        obj.colorBg = true
        this.colorValue[index] = obj
      }
    },
    /** 获取当前审批公告的进度设置高亮 */
    getNoticeProgress () {
      notice.getByCode(this.$route.query.noticeCode).then((res) => {
        if (res && res.data.notice) {
          let progressStage = res.data.notice.progressStage
          let isLocked = res.data.notice.isLocked
          this.currentNoticeProgress = this.filterProgressStage(progressStage, Number(isLocked))
          this.currentView = this.$route.query.currentView
          this.setStepHighlight()
        }
      })
    }
  },
  /** 实例创建完成 */
  mounted () {
    // this.setStepHighlight()
  },
  /** 注册组件 */
  components: {
    // 招标项目
    bidProject: resolve => require(['./bidproject'], resolve),
    // 标段
    bidPhase: resolve => require(['./bidphase'], resolve),
    // 公告
    notice: resolve => require(['./notice'], resolve),
    // 投标报名
    bidRegist: resolve => require(['./bidsignup'], resolve),
    // 资格预审文件
    qualFile: resolve => require(['./qualfile'], resolve),
    // 答疑补疑
    answerQues: resolve => require(['./answerques'], resolve),
    // 资格预审会
    qualPre: resolve => require(['./qualpre'], resolve),
    // 招标文件
    bidFile: resolve => require(['./bidfile'], resolve),
    // 招标控制价
    bidPrice: resolve => require(['./bidprice'], resolve),
    // 开评标
    openBid: resolve => require(['./openbid'], resolve),
    // 候选人公示
    candNotice: resolve => require(['./candnotice'], resolve),
    // 结果公示
    resNotice: resolve => require(['./resnotice'], resolve),
    // 结果变更
    resChange: resolve => require(['./reschange'], resolve)
  }
}
</script>
<style lang="less">
#flowchart-page {
  .maincont-box {
    padding: 0;
  }
  .flowchart-box {
    margin: 20px;
    padding: 20px;
    background: #fff;
  }
  /** 步骤条css */
  .flow-step {
    height: 170px;
    text-align:left;
    .step-btn {
      position: relative;
      z-index: 0;
      button {
        min-width: 100px;
      }
      /** 按钮背景色 */
      .color-btnbg {
        background: #3F63F6;
      }
      .up-bidsign {
        position: absolute;
        margin-top: 30px;
        z-index: -1;
        .line-ver-leavl {
          width: 100px;
          height: 30px;
          border-top: 1px solid #ddd;
          border-left: 1px solid #ddd;
          border-right: 1px solid #ddd;
        }
      }
    }
    .line-level {
      position: absolute;
      margin-top: 20px;
      width: 95%;
      left: 0;
      border: 1px solid #ddd;
      z-index: -1;
    }
    .line-vertical {
      width: 0;
      height: 80px;
      border: 1px solid #ddd;
      margin: 0 auto;
    }
  }
}
.el-dropdown-menu{
  z-index: 999999 !important;
}
</style>
