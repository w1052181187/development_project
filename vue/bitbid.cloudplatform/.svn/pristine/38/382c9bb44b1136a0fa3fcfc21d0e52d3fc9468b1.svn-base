<template>
  <div class="cloudcontent" id="cloud_process">
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/processManage/projectProcess' }">项目进度管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/processManage/projectProcess/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item class="active_bread">流程管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!--面包屑-->
    <div class="main">
      <div class="process_top">
        <p>
            <el-select v-model="value" placeholder="标段切换" @change="changeBidSection(value)">
              <el-option
                v-for="item in options"
                :key="item.code"
                :label="item.bidSectionName"
                :value="item.code">
              </el-option>
            </el-select>
        </p>
        <div class="bid">
          <table class="bidtable">
            <tr>
              <td class="tit">项目编号：</td>
              <td><span>{{updateForm.tenderProjectCode}}</span></td>
              <td class="tit">项目名称：</td>
              <td><span>{{updateForm.tenderProjectName}}</span></td>
            </tr>
            <tr>
              <td class="tit">标段编号：</td>
              <td><span>{{updateForm.bidSectionCode}}</span></td>
              <td class="tit">标段名称：</td>
              <td><span>{{updateForm.bidSectionName}}</span></td>
            </tr>
          </table>
        </div>
        <div class="process" v-if="processFlag">
          <ul>
           <li v-for="item in process" :key="item.value" :class="item.colorFlag ? 'active' :''">
            <!-- <el-button  @click="processBtn(item)" v-if="Number(item.check) === 0" :disabled="!item.clickFlag">{{item.name}}</el-button>-->
             <el-button  @click="processBtn(item)" v-if="Number(item.check) === 0">{{item.name}}</el-button>
           </li>
          </ul>
        </div>
      </div>
      <div class="process_main" v-if="processFlag">
        <invitation :tenderSystemCode="tenderSystemCode" :sectionSystemCode="sectionSystemCode" :operationFlag="operationFlag" :chooseSectionSystemCode= "chooseSectionSystemCode" v-if="currentView === 'invitation'"></invitation>
        <announcement :tenderSystemCode="tenderSystemCode" :sectionSystemCode="sectionSystemCode" :operationFlag="operationFlag" :chooseSectionSystemCode= "chooseSectionSystemCode" v-if="currentView === 'announcement'"></announcement>
        <prequalificationFile :tenderSystemCode="tenderSystemCode" :sectionSystemCode="sectionSystemCode" :operationFlag="operationFlag" :chooseSectionSystemCode= "chooseSectionSystemCode" v-if="currentView === 'prequalificationFile'"></prequalificationFile>
        <prequalificationResponse :tenderSystemCode="tenderSystemCode" :sectionSystemCode="sectionSystemCode" :operationFlag="operationFlag" :chooseSectionSystemCode= "chooseSectionSystemCode" v-if="currentView === 'prequalificationResponse'"></prequalificationResponse>
        <checkResult :tenderSystemCode="tenderSystemCode" :sectionSystemCode="sectionSystemCode" :operationFlag="operationFlag" :chooseSectionSystemCode= "chooseSectionSystemCode" v-if="currentView === 'checkResult'"></checkResult>
        <bidDocument :tenderSystemCode="tenderSystemCode" :sectionSystemCode="sectionSystemCode" :operationFlag="operationFlag" :chooseSectionSystemCode= "chooseSectionSystemCode" v-if="currentView === 'bidDocument'"></bidDocument>
        <bidResponse :tenderSystemCode="tenderSystemCode" :sectionSystemCode="sectionSystemCode" :operationFlag="operationFlag" :chooseSectionSystemCode= "chooseSectionSystemCode" v-if="currentView === 'bidResponse'"></bidResponse>
        <bidOpening :tenderSystemCode="tenderSystemCode" :sectionSystemCode="sectionSystemCode" :operationFlag="operationFlag" :chooseSectionSystemCode= "chooseSectionSystemCode" v-if="currentView === 'bidOpening'"></bidOpening>
        <wasteBidResult :tenderSystemCode="tenderSystemCode" :sectionSystemCode="sectionSystemCode" :operationFlag="operationFlag" :chooseSectionSystemCode= "chooseSectionSystemCode" v-if="currentView === 'wasteBidResult'"></wasteBidResult>
        <calibrationResult :tenderSystemCode="tenderSystemCode" :sectionSystemCode="sectionSystemCode" :operationFlag="operationFlag" :chooseSectionSystemCode= "chooseSectionSystemCode" v-if="currentView === 'calibrationResult'"></calibrationResult>
      </div>
    </div>
  </div>
</template>
<script>
import {tenderProject, projectFlow, bidSection} from '@/api/cloudplatform/index'
export default {
  components: {
    // 招标邀请
    invitation: resolve => require(['./invite/index.vue'], resolve),
    // 招标公告/资格预审公告
    announcement: resolve => require(['./announcement/index.vue'], resolve),
    // 资格预审文件
    prequalificationFile: resolve => require(['./prequalificationFile/index.vue'], resolve),
    // 响应资格预审
    prequalificationResponse: resolve => require(['./prequalificationResponse/index.vue'], resolve),
    // 资格审查结果
    checkResult: resolve => require(['./checkResult/index.vue'], resolve),
    // 招标文件
    bidDocument: resolve => require(['./bidDocument/index.vue'], resolve),
    // 响应招标
    bidResponse: resolve => require(['./bidResponse/index.vue'], resolve),
    // 开标评标
    bidOpening: resolve => require(['./bidOpening/index.vue'], resolve),
    // 废标结果
    wasteBidResult: resolve => require(['./wasteBidResult/index.vue'], resolve),
    // 定标结果
    calibrationResult: resolve => require(['./calibrationResult/index.vue'], resolve)
  },
  data () {
    return {
      tenderSystemCode: this.$route.query.code,
      sectionSystemCode: this.$route.params.code,
      chooseSectionSystemCode: '', // 用于流程切换
      //  标段切换
      options: [],
      value: '',
      process: [
        {
          name: '', // 招标公告/资格预审公告/招标邀请书
          type: '',
          check: '',
          colorFlag: '', // 颜色高亮条件（false.否 true.是）
          clickFlag: '', // 是否可点击（false.否 true.是）
          operationFlag: '' // 该主件下的操作按钮是否展示（false.否 true.是）
        }, {
          name: '资格预审文件',
          type: '9',
          check: '',
          colorFlag: '',
          clickFlag: '',
          operationFlag: ''
        }, {
          name: '响应资格预审',
          type: '10',
          check: '',
          colorFlag: '',
          clickFlag: '',
          operationFlag: ''
        }, {
          name: '资格审查结果',
          type: '11',
          check: '',
          colorFlag: '',
          clickFlag: '',
          operationFlag: ''
        }, {
          name: '招标文件',
          type: '12',
          check: '',
          colorFlag: '',
          clickFlag: '',
          operationFlag: ''
        }, {
          name: '响应招标',
          type: ' 13',
          check: '',
          colorFlag: '',
          clickFlag: '',
          operationFlag: ''
        }, {
          name: '开标评标',
          type: '14',
          check: '',
          colorFlag: '',
          clickFlag: '',
          operationFlag: ''
        }, {
          name: '废标结果公告',
          type: '15',
          check: '',
          colorFlag: '',
          clickFlag: '',
          operationFlag: ''
        }, {
          name: '定标结果',
          type: '16',
          check: '',
          colorFlag: '',
          clickFlag: '',
          operationFlag: ''
        }
      ],
      chooseTenderProjectInfo: {}, // 当前招标项目信息
      chooseBidSectionInfo: {}, // 当前标段信息
      currentView: '', // 按钮点击选择的视图
      processFlag: false, // 流转信息是否展示，默认不展示
      updateForm: {},
      operationFlag: '', // 控制页面操作按钮展示的字段（0.不展示 1.展示）
      curProgress: this.$store.state.curProgress ? JSON.parse(this.$store.state.curProgress) : {} // 当前流程节点，用于刷新后维持原来节点不变
    }
  },
  mounted () {
    this.getChooseTenderProjectInfo(this.sectionSystemCode)
    this.getChooseBidSectionInfo(this.sectionSystemCode)
    this.getBidSectionList()
  },
  methods: {
    /** 获取指定标段的信息 */
    getChooseBidSectionInfo (sectionSystemCode) {
      bidSection.getOne(sectionSystemCode).then((res) => {
        this.updateForm = res.data.bidSection
      })
    },
    /** 获取该招标项目下的所有标段信息 */
    getBidSectionList () {
      bidSection.getList({
        tenderSystemCode: this.tenderSystemCode,
        auditStatusList: '2,4'
      }).then((res) => {
        this.options = res.data.bidSectionList.list
      })
    },
    /** 获取指定标段的信息 */
    changeBidSection (value) {
      this.chooseSectionSystemCode = value
      this.getChooseBidSectionInfo(value)
      this.getChooseTenderProjectInfo(value)
    },
    /** 获取当前招标项目信息 */
    getChooseTenderProjectInfo (sectionSystemCode) {
      tenderProject.getOne(this.tenderSystemCode).then((res) => {
        if (res.data.tenderProject) {
          this.chooseTenderProjectInfo = res.data.tenderProject
          this.getProjectFlow(sectionSystemCode)
        }
      })
    },
    /** 获取项目流程以及节点高亮的状态 */
    getProjectFlow (sectionSystemCode) {
      this.currentView = ''
      projectFlow.getList({
        tenderSystemCode: this.tenderSystemCode,
        sectionSystemCode: sectionSystemCode
      }).then((res) => {
        // 判断项目类型，资格预审的项目是10个节点，其他项目是7个（第一个为招标公告或者是招标邀请）
        if (Number(this.chooseTenderProjectInfo.tenderMode) === 1) { // 1.公开招标
          if (Number(this.chooseTenderProjectInfo.isPreBid) === 0) {
            // 2.资格后审
            this.process[0].name = '招标公告'
            this.process[0].type = '6'
            this.process.splice(1, 3)
            this.currentView = 'announcement'
          } else {
            // 1.资格预审
            this.process[0].name = '资格预审公告'
            this.process[0].type = '7'
            this.currentView = 'announcement'
          }
        } else if (Number(this.chooseTenderProjectInfo.tenderMode) === 2) { // 2.邀请招标
          // 3.邀请招标
          this.process[0].name = '招标邀请书'
          this.process[0].type = '8'
          this.process.splice(1, 3)
          this.currentView = 'invitation'
        } else if (Number(this.chooseTenderProjectInfo.tenderMode) === 3) { // 3.竞争性谈判
          // 5.竞争性谈判
          this.process[0].name = '招标公告'
          this.process.splice(1, 3)
        } else if (Number(this.chooseTenderProjectInfo.tenderMode) === 4) { // 4.单一来源采购
          // 4.单一来源
          this.process[0].name = '招标邀请书'
          this.process.splice(1, 3)
        } else if (Number(this.chooseTenderProjectInfo.tenderMode) === 5) { // 5.询价采购
          // 7.询价
          this.process[0].name = '招标公告'
          this.process.splice(1, 3)
        } else if (Number(this.chooseTenderProjectInfo.tenderMode) === 6) { //  6.竞争性磋商
          // 6.竞争性磋商
          this.process[0].name = '招标公告'
          this.process.splice(1, 3)
        } else { // 9.其他
        }
        // 1.判断该标段目前走到哪个节点了，该节点前只可查看,隐藏操作按钮
        // 2.判断该节点下是否存在审批中的信息，若存在则后续节点不可点击，若不存在则后续节点可点击
        // 3.判断该标段目前走到哪个节点了，该节点前无颜色，该节点及其后续节点是蓝色
        // 判断该标段的流转状态
        if (res.data.bidSection) {
          this.chooseBidSectionInfo = res.data.bidSection
        }
        this.setProcessNodes(res.data.projectFlowList, res.data.bidSection)
        this.processFlag = true
      })
    },
    /** 对流程节点的使用状态进行设定(启用、禁用) */
    setProcessNodes (projectFlowList, bidSectionInfo) {
      if (projectFlowList.length > 0 && projectFlowList.length - 1 === this.process.length) { // 去掉后台给的“完结”状态
        for (let i = 0; i < projectFlowList.length - 1; i++) {
          this.process[i].check = projectFlowList[i]
        }
        this.setProcessNodeInfo(bidSectionInfo)
      }
    },
    /** 设定流程节点操作按钮、高亮的展示 */
    setProcessNodeInfo (chooseBidSectionInfo) {
      if (Number(chooseBidSectionInfo.flowStatus) < 6) {
        this.process.forEach(item => { // 标段刚提交，未进行任何录入
          item.colorFlag = true
          item.clickFlag = true
          item.operationFlag = true
        })
      } else {
        this.process.forEach(item => {
          if (Number(item.type) < Number(chooseBidSectionInfo.flowStatus)) { // 当前节点之前的信息（无颜色，可点击，可查看）
            item.colorFlag = false
            item.clickFlag = true
            item.operationFlag = false
          } else { // 当前节点之后的信息要根据审批进度进行判断
            if (chooseBidSectionInfo.processCodes !== null) { // 存在未提交/待审批的信息，则该节点（高亮，可点击，可操作）；后续节点（高亮，不可点击，不可操作）
              if (Number(item.type) === Number(chooseBidSectionInfo.flowStatus)) {
                item.colorFlag = true
                item.clickFlag = true
                item.operationFlag = true
              } else {
                item.colorFlag = false
                item.clickFlag = false
                item.operationFlag = false
              }
            } else { // 不存在未提交/待审批的信息，则该节点（高亮，可点击，可操作）；后续节点（高亮，可点击，可操作）
              item.colorFlag = true
              item.clickFlag = true
              item.operationFlag = true
            }
          }
        })
      }
      this.process.map((item) => {
        if (this.curProgress && this.curProgress.type === item.type) {
          this.operationFlag = item.operationFlag // 设置流程默认页的操作状态
          this.processBtn(item)
        }
      })
    },
    /**
     * 流程切换
     * @param type
     */
    processBtn (item) {
      if (item.clickFlag === true) {
        this.operationFlag = item.operationFlag
        switch (item.type) {
          case '6':
            this.currentView = 'announcement'
            break
          case '7':
            this.currentView = 'announcement'
            break
          case '8':
            this.currentView = 'invitation'
            break
          case '9':
            this.currentView = 'prequalificationFile'
            break
          case '10':
            this.currentView = 'prequalificationResponse'
            break
          case '11':
            this.currentView = 'checkResult'
            break
          case '12':
            this.currentView = 'bidDocument'
            break
          case '13':
            this.currentView = 'bidResponse'
            break
          case '14':
            this.currentView = 'bidOpening'
            break
          case '15':
            this.currentView = 'wasteBidResult'
            break
          case '16':
            this.currentView = 'calibrationResult'
            break
          default:
        }
        if (!this.curProgress || item.type !== this.curProgress.type) {
          this.$store.commit('SET_PROGRESS', JSON.stringify(item))
        }
      } else {
        this.$message.warning('请先完成上一个信息的提交或审批')
        return false
      }
    }
  }
}
</script>
<style lang="less">
  #cloud_process{
    .process_top{
      padding: 0 20px;
    }
    .bidtable{
      width: 100%;
      padding: 10px 0;
    }
    .bidtable tr td{
      padding: 10px 0;
      text-align: left;
      color: #111111;
    }
    .bidtable tr td.tit{
      width: 80px;
      color: #666666;
    }
    .bidtable tr td span{
      width: 480px;
      line-height: 20px;
      display: inline-block;
    }
    .process ul{
      overflow: hidden;
    }
    .process ul li{
      overflow: hidden;
      float: left;
      padding-right: 30px;
      background: url("../../../../static/images/jt.png") right 3px center no-repeat;
    }
    .process ul li .el-button{
      width: 102px;
      height: 48px;
      background: #ffffff;
      border-color: #3f63f7;
      color: #3f63f7;
      padding: 0;
    }
    .process ul li.active .el-button{
      background: #c5d0fc;
      border-color: #c5d0fc;
      color: #333333;
    }
    .process ul li:last-child{
      background: none;
      padding-right: 0px;
    }
  }
</style>
