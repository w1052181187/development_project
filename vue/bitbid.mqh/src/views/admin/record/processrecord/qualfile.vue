<template>
  <!-- 资格预审文件 -->
  <div class="admin-layout" id="qualfile-page">
    <div class="qualfile-cont">
      <div class="qualfile-tit">
        <span>资格预审文件</span>
      </div>
      <div class="qualfile-form" >
        <div class="btn-box">
          <el-button @click="addBtnClick" type="warning" size="medium" icon="el-icon-plus" style="float: left;" :disabled="addBtnDisabled" v-if="dialogSubmit && progressStage === 2">添 加</el-button>
          <el-button @click="$router.go(-1)" type="info" size="medium" style="float: right;"  v-if="dialogSubmit && progressStage === 2" >取 消</el-button>
          <el-button @click="submitResult" type="success" size="medium" style="float: right;" v-if="dialogSubmit && progressStage === 2">提 交</el-button>
        </div>
        <el-collapse v-model="activeName" accordion>
          <el-collapse-item v-for="(qualifiPreFile,index) in qualifiPreFileList" :key="index" :name=qualifiPreFile.objectId>
            <template slot="title">
              <span @click="getQualifiPreFile(qualifiPreFile.objectId, index)" style="display:block;width:100%;position:relative;z-index:1;">
                <span class="title-span" v-for="(section, index) in qualifiPreFile.sectionList" :key="index">{{section.name}}</span>
                <el-button type="text" @click.stop="deleteCilck(qualifiPreFile.code)" style="float: right;margin-right: 40px;line-height: 25px;" v-if="dialogSubmit && progressStage === 2">删除</el-button>
              </span>
            </template>
            <div class="form-box">
              <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="140px">
                <el-form-item label="标段信息:" prop="bidphaseInfo">
                  <el-table
                    border
                    :data="tableData"
                    style="width: 100%">
                    <el-table-column
                      type="index"
                      label="序号"
                      :index="indexMethod"
                      width="55"
                      align="center">
                    </el-table-column>
                    <el-table-column
                      label="标段名称"
                      prop="name"
                      align="center">
                    </el-table-column>
                    <el-table-column
                      prop="planAmount"
                      label="计划金额（万元）"
                      align="center">
                    </el-table-column>
                  </el-table>
                </el-form-item>
                <el-form-item label="资格预审文件:" prop="fileList" ref="fileList">
                  <el-upload
                    v-if="dialogSubmit && progressStage === 2"
                    class="upload-demo"
                    :action="upLoad"
                    :on-success="uploadSuccess"
                    :file-list="fileList"
                    :show-file-list="false">
                    <el-button size="small" type="success" class="upload-btn" v-if="dialogSubmit && progressStage === 2">上传文件</el-button>
                  </el-upload>
                  <!-- 上传附件列表 -->
                  <div class="apply-upload">
                    <el-row>
                      <el-col :span="24">
                        <span class="file-list" v-for="(item, index) in fileList" :key="index">
                          <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                          <i class="el-icon-error upload-file" @click="clickDel(index)" v-if="dialogSubmit && progressStage === 2"></i>
                        </span>
                      </el-col>
                    </el-row>
                  </div>
                </el-form-item>
                <el-form-item label="文件发售起止时间:" prop="sellTime">
                  <el-date-picker
                    v-if="dialogSubmit && progressStage === 2"
                    v-model="ruleForm.sellTime"
                    type="datetimerange"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期">
                  </el-date-picker>
                  <span v-else>{{ruleForm.sellTime | filterSellTime}}</span>
                </el-form-item>
                <el-form-item label="答疑澄清起止时间:" prop="clarifyTime">
                  <el-date-picker
                    v-if="dialogSubmit && progressStage === 2"
                    v-model="ruleForm.clarifyTime"
                    type="datetimerange"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期">
                  </el-date-picker>
                  <span v-else>{{ruleForm.clarifyTime | filterClarifyTime}}</span>
                </el-form-item>
                <el-form-item label="资格预审会时间:" prop="qualifiPreTime" ref="qualifiPreTime">
                  <div class="flex"  v-if="dialogSubmit && progressStage === 2" >
                    <div style="height:0;border:none;">
                      <input type="text" @click="openByDropMain($event)" v-model="calendarMain.display" style="margin-top:0;" @blur="validTime">
                    </div>
                  </div>
                  <transition name="fade"  v-if="dialogSubmit && progressStage === 2" >
                    <div
                      class="calendar-dropdown"
                      :style="{'left':calendarMain.left+'px','top':calendarMain.top+'px'}"
                      v-if="calendarMain.show">
                      <calendar
                        ref="sonV"
                        :zero="calendarMain.zero"
                        :events="calendarMain.events"
                        :lunar="calendarMain.lunar"
                        :value="calendarMain.value"
                        :begin="calendarMain.begin"
                        :end="calendarMain.end"
                        @selectMonth="calendarMain.selectMonth"
                        @select="calendarMain.select">
                      </calendar>
                    </div>
                  </transition>
                  <span v-else>{{ruleForm.qualifiPreTime | filterQualifiPreTime}}</span>
                </el-form-item>
                <el-form-item label="资格预审会地点:" prop="qualifiPreAddress">
                  <el-input v-model="ruleForm.qualifiPreAddress" style="width: 400px;" v-if="dialogSubmit && progressStage === 2"></el-input>
                  <span v-else>{{ruleForm.qualifiPreAddress}}</span>
                </el-form-item>
                <el-form-item class="handle-btn">
                  <el-button type="primary" @click="onFormSubmit('ruleForm')" v-if="dialogSubmit && progressStage === 2">保 存</el-button>
                  <el-button @cilck="$router.go(-1)" v-if="dialogSubmit && progressStage === 2">取消</el-button>
                </el-form-item>
              </el-form>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>

      <!--审批start-->
      <div class="exam-box" v-if="dialogApprove">
        <el-form :model="examForm" :rules="examRules" ref="examForm" label-width="140px">
          <el-form-item :label="($store.getters.authUser.enterpriseType === 1) ? '备案状态' : '审批状态'" prop="approveResult">
            <el-select v-model="examForm.approveResult" clearable placeholder="请选择" style="width: 400px">
              <el-option
                v-for="item in stateList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item :label="($store.getters.authUser.enterpriseType === 1) ? '备案意见' : '审批意见'"  prop="approveOpinion" v-if="examForm.approveResult !== 1">
            <el-input
              style="width: 400px"
              v-model="examForm.approveOpinion"
              type="textarea"
              :rows="4"
              placeholder="请输入内容">
            </el-input>
          </el-form-item>
          <el-form-item :label="($store.getters.authUser.enterpriseType === 1) ? '备案意见' : '审批意见'"  v-if="examForm.approveResult === 1">
            <el-input
              style="width: 400px"
              v-model="examForm.approveOpinion"
              type="textarea"
              :rows="4"
              placeholder="请输入内容">
            </el-input>
          </el-form-item>
        </el-form>
        <div class="result-savebtn">
          <el-button type="primary" @click="saveExamForm('examForm')" v-if="dialogApprove">保 存</el-button>
        </div>
      </div>
      <!--审批end-->
    </div>

    <!-- 审批记录start -->
    <div class="app-record" >
      <div class="division-line">
      </div>
      <div class="qualfile-tit">
        <span>审批记录</span>
      </div>
      <div class="app-cont">
        <el-row :gutter="20">
          <el-col :span="1">&nbsp;</el-col>
          <el-col :span="22">
            <el-steps direction="vertical" :active="1" :space="100">
              <el-step
                v-for="(item,index) in approveRecordList"
                :key="index"
                :status='fliteredStatus(item.approveResult)'>
                <span slot="title" class="step-span span-color span-first" v-if="item.approveResult === 0">{{item.applyName}}</span>
                <span slot="title" class="step-span span-color span-first" v-if="item.approveResult !== 0">{{item.approveName}}</span>
                <span slot="title" class="step-span span-second span-color" v-if="item.approveResult === 0">{{item.applyOrganization}}</span>
                <span slot="title" class="step-span span-second span-color" v-if="item.approveResult !== 0">{{item.approveOrganization}}</span>
                <span slot="title" class="step-span span-third" >{{item.approveResult | filterResult}}</span>
                <span slot="title" class="step-span span-color span-last">{{item.approveTime | filterApproveTime}}</span>
                <div slot="title" class="apply-option" v-if="item.approveOpinion">
                  <span>审批意见:{{item.approveOpinion}}</span>
                </div>
              </el-step>
            </el-steps>
          </el-col>
        </el-row>
      </div>
    </div>
    <!-- 审批记录end -->

    <div class="dialog-box">
      <el-dialog
        title="资格预审文件名称"
        :visible.sync="addDialogVisible"
        width="40%">
        <div class="change-query">
          <div class="form-box">
            <el-form :model="bidfileAddForm" :rules="bidfileAddRules" ref="bidfileAddForm" label-width="140px" class="demo-ruleForm">
              <el-form-item label="标段信息:" prop="bidPhaseInfo">
                <el-table
                  border
                  :data="tableAddData"
                  style="width: 100%">
                  <el-table-column
                    label="选择"
                    prop="select"
                    align="center">
                    <template slot-scope="scope">
                      <el-checkbox v-model="scope.row.select"></el-checkbox>
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="标段名称"
                    prop="name"
                    align="left">
                  </el-table-column>
                  <el-table-column
                    prop="planAmount"
                    label="计划金额(万元)"
                    align="left">
                  </el-table-column>
                </el-table>
              </el-form-item>
              <el-form-item label="资格预审文件:" prop="fileAddList" ref="fileAddList">
                <el-upload
                  class="upload-demo"
                  :action="upLoad"
                  :on-success="handleSuccess"
                  :show-file-list="false"
                  :file-list="fileAddList">
                  <el-button size="small" type="primary" class="upload-btn">点击上传</el-button>
                </el-upload>
                <div class="apply-upload">
                  <el-row>
                    <el-col :span="24">
                      <span class="file-list" v-for="(item, index) in fileAddList" :key="index">
                        <el-button
                          type="text"
                          style="padding-right: 20px;text-align: left;width: 80%;"
                          @click="downloadFile(item.fileName,item.relativePath)">
                          <span style="width:100%;overflow: hidden;display: inline-block;text-overflow: ellipsis;white-space: nowrap;">
                            {{item.fileName}}
                          </span>
                        </el-button>
                        <i class="el-icon-error upload-file" @click="clickDels(index)"></i>
                      </span>
                    </el-col>
                  </el-row>
                </div>
              </el-form-item>
              <el-form-item label="文件发售起止时间:" prop="sellTime">
                <el-date-picker
                  v-model="bidfileAddForm.sellTime"
                  type="datetimerange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="答疑澄清起止时间:" prop="clarifyTime">
                <el-date-picker
                  v-model="bidfileAddForm.clarifyTime"
                  type="datetimerange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="资格预审会时间:" prop="qualifiPreTimeQualfile" ref="qualifiPreTimeQualfile">
                <div class="flex">
                  <div style="height:0;border:none;">
                    <input type="text" @click="openByDrop($event)" v-model="calendarQualfile.display" style="margin-top:0;" @blur="validQualfileTime">
                  </div>
                </div>
                <transition name="fade">
                  <div
                    class="calendar-dropdown"
                    :style="{'left':calendarQualfile.left+'px','top':calendarQualfile.top+'px'}"
                    v-if="calendarQualfile.show">
                    <calendar
                      ref="sonV"
                      :zero="calendarQualfile.zero"
                      :events="calendarQualfile.events"
                      :lunar="calendarQualfile.lunar"
                      :value="calendarQualfile.value"
                      :begin="calendarQualfile.begin"
                      :end="calendarQualfile.end"
                      @selectMonth="calendarQualfile.selectMonth"
                      @select="calendarQualfile.select">
                    </calendar>
                  </div>
                </transition>
               <!-- <el-date-picker
                  style="width:400px"
                  v-model="bidfileAddForm.qualifiPreTime"
                  type="date"
                  placeholder="选择日期">
                </el-date-picker>-->
              </el-form-item>
              <el-form-item label="资格预审会地点:" prop="qualifiPreAddress">
                <el-input v-model="bidfileAddForm.qualifiPreAddress" style="width:400px"></el-input>
              </el-form-item>
              <el-form-item class="last-form-btn">
                <el-button type="primary" @click="saveAddForm('bidfileAddForm')">保 存</el-button>
                <el-button @click="cancelClick">取消</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
// 引入日历
import calendar from '@/components/calendar/calendar.vue'
import {qualfile, openbid, tenderproject, section, notice} from 'api/index'
import {commonJs, downloadFile} from 'common/js/common'
import {formatDate} from 'common/js/date'
export default {
  data () {
    return {
      // 弹窗
      calendarQualfile: {
        display: '',
        show: false,
        zero: true,
        value: [], // 默认日期
        lunar: true, // 显示农历
        events: {},
        selectMonth: (month, year) => {
          this.operate = 2
          this.selectMonthInfo = year + '-' + month
          if (this.selectMonthInfo) {
            this.getOptionalNoticeList()
          }
        },
        select: (value) => {
          this.calendarQualfile.show = false
          this.calendarQualfile.value = value
          this.calendarQualfile.display = value.join('-')
          this.validQualfileTime()
        }
      },
      selectMonthInfo: '',
      // 表单
      calendarMain: {
        display: '',
        show: false,
        zero: true,
        value: [], // 默认日期
        lunar: true, // 显示农历
        events: {},
        selectMonth: (month, year) => {
          this.operate = 1
          this.selectMonthInfo = year + '-' + month
          if (this.selectMonthInfo) {
            this.getOptionalNoticeList()
          }
        },
        select: (value) => {
          this.calendarMain.show = false
          this.calendarMain.value = value
          this.calendarMain.display = value.join('-')
          this.validTime()
        }
      },
      addDialogVisible: false,
      bidfileAddForm: {}, // 新增-表单数据
      bidfileAddRules: {
        fileAddList: [
          {required: true, message: '请上传资格预审文件', trigger: 'blur'}
        ],
        sellTime: [
          {required: true, message: '请选择文件发售起止时间', trigger: 'change'}
        ],
        clarifyTime: [
          {required: true, message: '请选择答疑补遗起止时间', trigger: 'change'}
        ],
        qualifiPreTimeQualfile: [
          {required: true, message: '请选择资格预审会开始时间', trigger: 'change'}
        ],
        qualifiPreAddress: [
          {required: true, message: '请填写资格预审会地点', trigger: 'blur'},
          { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }
        ]
      }, // 新增-表单规则
      fileAddList: [], // 新增上传列表
      tableAddData: [], // 新增表格数据
      activeName: '1', // 折叠面板默认选中项
      tableData: [],
      qualifiPreFileList: [], // 折叠面板数据
      examForm: {}, // 审批表单
      examRules: {
        approveResult: [
          {required: true, message: '请选择审批结果', trigger: 'change'}
        ],
        approveOpinion: [
          {required: true, message: '请填写审批意见', trigger: 'blur'},
          { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }
        ]
      }, // 审批规则
      stateList: [
        {
          label: '通过',
          value: 1
        },
        {
          label: '退回',
          value: 2
        }
      ],
      approveRecordList: [], // 审批记录-数据
      ruleForm: {},
      rules: {
        fileList: [
          {required: true, message: '请上传资格预审文件', trigger: 'blur'}
        ],
        sellTime: [
          {required: true, message: '请选择文件发售起止时间', trigger: 'change'}
        ],
        clarifyTime: [
          {required: true, message: '请选择答疑补遗起止时间', trigger: 'change'}
        ],
        qualifiPreTime: [
          {required: true, message: '请选择资格预审会时间', trigger: 'change'}
        ],
        qualifiPreAddress: [
          {required: true, message: '请填写资格预审会地点', trigger: 'blur'},
          { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }
        ]
      },
      upLoad: commonJs.fileUploadUrl,
      fileList: [], // 上传文件列表数据
      addBtnDisabled: false, // 当没有可选标段时候，设置添加按钮为灰色
      selectSectionData: [], // 添加资格预审文件时所选标段
      index: 0, // 展开的资格预审文件的index
      dialogSubmit: false, // 是否展示
      dialogApprove: false, // 是否审批
      currentPage: 1,
      buttonFlag: false,
      approveFlag: true,
      currentTenderProjectInfo: {
        selectApply: {
          enterpriseId: ''
        }
      },
      operate: 1, // 1.页面开标时间 2.弹框开标时间
      progressStage: null,
      projectEnterpriseType: null
    }
  },
  computed: {},
  props: ['callbackdata', 'noticeCode', 'operationCheckFlag', 'dialogCheckVisible'],
  created () {
    this.dialogApprove = this.dialogCheckVisible
    this.getTenderProjectInfo()
  },
  filters: {
    filterSellTime (val) {
      if (val) {
        let sellStartTime = new Date(val[0])
        let sellEndTime = new Date(val[1])
        return formatDate(sellStartTime, 'yyyy-MM-dd hh:mm:ss') + ' 至 ' + formatDate(sellEndTime, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return ''
      }
    },
    filterClarifyTime (val) {
      if (val) {
        let ClarifyStartTime = new Date(val[0])
        let ClarifyEndTime = new Date(val[1])
        return formatDate(ClarifyStartTime, 'yyyy-MM-dd hh:mm:ss') + ' 至 ' + formatDate(ClarifyEndTime, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return ''
      }
    },
    filterQualifiPreTime (val) {
      if (val) {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd ')
      } else {
        return ''
      }
    },
    filterApproveTime (val) {
      if (val) {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return ''
      }
    },
    filterResult (val) {
      if (val === 1) {
        return '通过'
      } else if (val === 2) {
        return '退回'
      } else if (val === 0) {
        return '发起申请'
      }
    }
  },
  /** 方法集 */
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 弹窗 */
    openByDrop (e) {
      this.calendarQualfile.show = true
      this.calendarQualfile.left = e.target.offsetLeft + 19
      this.calendarQualfile.top = e.target.offsetTop + 70
      e.stopPropagation()
      window.setTimeout(() => {
        document.addEventListener('click', (e) => {
          this.calendarQualfile.show = false
          document.removeEventListener('click', () => {}, false)
        }, false)
      }, 1000)
    },
    /** 弹窗--验证时间 */
    validQualfileTime () {
      if (this.calendarQualfile.display) {
        this.$set(this.bidfileAddForm, 'qualifiPreTimeQualfile', 'qualifiPreTimeQualfile')
      } else {
        this.$set(this.bidfileAddForm, 'qualifiPreTimeQualfile', '')
      }
      if (this.bidfileAddForm.qualifiPreTimeQualfile) {
        this.$refs['qualifiPreTimeQualfile'].clearValidate()
      }
    },
    /** 表单 */
    openByDropMain (e) {
      this.calendarMain.show = true
      this.calendarMain.left = e.target.offsetLeft + 19
      this.calendarMain.top = e.target.offsetTop + 70
      e.stopPropagation()
      window.setTimeout(() => {
        document.addEventListener('click', (e) => {
          this.calendarMain.show = false
          document.removeEventListener('click', () => {}, false)
        }, false)
      }, 1000)
    },
    /** 表单--验证时间 */
    validTime () {
      if (this.calendarMain.display) {
        this.$set(this.ruleForm, 'qualifiPreTime', 'qualifiPreTime')
      } else {
        this.$set(this.ruleForm, 'qualifiPreTime', '')
      }
      if (this.ruleForm.qualifiPreTime) {
        this.$refs['qualifiPreTime'][this.index].clearValidate()
      }
    },
    /**
     * 获取该招标项目下所有符合条件的公告开标时间
     * 非资格预审：开评标阶段（招标文件中的实际开标时间）
     * 是资格预审：分为两种情况，开评标阶段（招标文件中的实际开标时间），资格预审会阶段（资格预审文件中的实际开标时间）
     */
    getOptionalNoticeList () {
      notice.selectBidOpenTimeByCode(this.selectMonthInfo).then((res) => {
        // 待开标
        let tempOpenBid = this.filterFormatDate(res.data.bidOpenTimeList, 6)
        // 转成map数据
        this.transDataToMap(tempOpenBid)
      })
    },
    // 格式化日期并转化数据格式
    filterFormatDate (arr, type) {
      let result = []
      if (arr.length) {
        arr = arr.map(item => {
          // 格式化时间
          item = this.formarDateYMD(item)
          let obj = {
            time: item,
            type: type
          }
          return obj
        })
        return arr
      }
      return result
    },
    /**
     * 格式化时间
     * 将时间格式化为【2018-1-17】
     * 月份天数不满10不补0
     **/
    formarDateYMD (date) {
      let str = ''
      let formatDate = new Date(date)
      let year = `${formatDate.getFullYear()}`
      let month = `${formatDate.getMonth() + 1}`
      let tdate = `${formatDate.getDate()}`
      str = `${year}-${month}-${tdate}`
      return str
    },
    /** 将数据包装成以时间为key，type:1和count:2为value的map集合
     **/
    transDataToMap (arr) {
      if (Array.isArray(arr)) {
        let resultMap = {}
        // 将数组中同一天的数据整合在一起
        let sameTimeArr = arr.map(item => {
          return item.time
        })
        // 去重,获得新数据
        let newArr = []
        for (var i = 0; i < sameTimeArr.length; i++) {
          if (newArr.indexOf(sameTimeArr[i]) === -1) {
            newArr.push(sameTimeArr[i])
          }
        }
        newArr.forEach((item) => {
          let tempArr = []
          arr.forEach(child => {
            let obj = {}
            if (String(item) === String(child.time)) {
              obj = {
                type: child.type
              }
            }
            if (JSON.stringify(obj) !== '{}' && JSON.stringify(tempArr).indexOf(JSON.stringify(obj)) === -1) {
              tempArr.push(obj)
            }
          })
          resultMap[item] = tempArr
        })
        if (Object.is(this.operate, 1)) {
          this.$set(this.calendarMain, 'events', resultMap)
        } else if (Object.is(this.operate, 2)) {
          this.$set(this.calendarQualfile, 'events', resultMap)
        }
      }
    },
    /** 打开弹窗-新增 */
    addBtnClick () {
      this.sectionNotList()
      // 打开弹窗
      this.addDialogVisible = true
    },
    /** 弹窗-保存 */
    saveAddForm (val) {
      this.bidfileAddForm.flag = 1
      this.selectSectionData = []
      if (this.tableAddData.length > 0) {
        for (let i = 0; i < this.tableAddData.length; i++) {
          if (this.tableAddData[i].select === true) {
            this.selectSectionData.push(this.tableAddData[i])
          }
        }
      }
      if (this.selectSectionData.length < 1) {
        this.$message.error('选择标段信息！')
        return false
      }
      this.save(val)
    },
    /** 弹窗-保存方法 */
    save (val) {
      this.validFiles()
      this.$refs[val].validate((vaild) => {
        if (vaild) {
          this.bidfileAddForm.noticeCode = this.noticeCode
          this.bidfileAddForm.qualifiPreTime = this.calendarQualfile.display
          this.bidfileAddForm.actualQualifiPreTime = this.bidfileAddForm.qualifiPreTime
          this.bidfileAddForm.sellStartTime = this.bidfileAddForm.sellTime[0]
          this.bidfileAddForm.sellEndTime = this.bidfileAddForm.sellTime[1]
          this.bidfileAddForm.clarifyStartTime = this.bidfileAddForm.clarifyTime[0]
          this.bidfileAddForm.clarifyEndTime = this.bidfileAddForm.clarifyTime[1]
          this.bidfileAddForm.fileInformationList = this.fileAddList
          this.bidfileAddForm.sectionList = this.selectSectionData
          qualfile.save(this.bidfileAddForm).then(() => {
            this.getQualifiPreFileList()
            this.cancelClick()
            this.sectionNotList()
            this.getApproveRecodes()
          })
        } else {
          return false
        }
      })
    },
    /** 弹窗-取消 */
    cancelClick () {
      this.bidfileAddForm = {}
      this.tableAddData = []
      this.fileAddList = []
      this.addDialogVisible = false
    },
    /** 表单-保存 */
    onFormSubmit (val) {
      this.update(val)
    },
    /** 表单-修改方法 */
    update (val) {
      this.validFile()
      this.$refs[val][this.index].validate((vaild) => {
        if (vaild) {
          this.ruleForm.noticeCode = this.noticeCode
          this.ruleForm.qualifiPreTime = this.calendarMain.display
          this.ruleForm.sellStartTime = this.ruleForm.sellTime[0]
          this.ruleForm.sellEndTime = this.ruleForm.sellTime[1]
          this.ruleForm.clarifyStartTime = this.ruleForm.clarifyTime[0]
          this.ruleForm.clarifyEndTime = this.ruleForm.clarifyTime[1]
          this.ruleForm.fileInformationList = this.fileList
          qualfile.save(this.ruleForm).then(() => {
            this.getQualifiPreFileList()
            this.getApproveRecodes()
          })
        } else {
          return false
        }
      })
    },
    /** 删除 */
    deleteCilck (code) {
      this.$confirm('此操作永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        qualfile.delete(code).then(() => {
          this.getQualifiPreFileList()
          this.sectionNotList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    /** 提交 */
    submitResult (val) {
      let obj = {
        noticeCode: this.noticeCode
      }
      qualfile.getList(obj).then((res) => {
        let qualifiPreFileList = res.data.qualifiPreFileList
        if (qualifiPreFileList) {
          if (qualifiPreFileList.length > 0) {
            qualfile.submit(this.noticeCode, 2).then(() => {
              this.getQualifiPreFileList()
              this.getApproveRecodes()
              this.getNoticeProgress()
            })
          } else {
            this.$message.error('请先添加资格预审文件！')
            return false
          }
        } else {
          this.$message.error('请先添加资格预审文件！')
          return false
        }
      })
    },
    /** 保存审批 */
    saveExamForm (val) {
      this.$refs[val].validate((vaild) => {
        if (vaild) {
          this.examForm.relatedCode = this.noticeCode
          this.examForm.approveOpinion = this.examForm.approveOpinion || ''
          qualfile.approve(this.examForm).then(() => {
            this.getQualifiPreFileList()
            this.getApproveRecodes()
            this.getNoticeProgress()
          })
        } else {
          return false
        }
      })
    },
    /** 表单-删除上传附件 */
    clickDel (index) {
      this.fileList.splice(index, 1)
      this.validFile()
    },
    /** 弹窗-删除上传附件 */
    clickDels (index) {
      this.fileAddList.splice(index, 1)
      this.validFiles()
    },
    /** 下载文件 */
    downloadFile,
    /** 表单-上传附件 */
    uploadSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        let fileInformations = []
        let existFileSize = 0
        //  得到已经保存的附件信息
        for (var i = 0; i < fileList.length; i++) {
          if (!fileList[i].response) {
            existFileSize++
            fileInformations[i] = fileList[i]
          }
        }
        // 添加新的附件信息
        if (existFileSize < fileList.length) {
          for (var j = existFileSize; j < fileList.length; j++) {
            if (fileList[j].response) {
              fileInformations[j] = fileList[j].response.fileInformation
            }
          }
        }
        this.fileList = fileInformations // 获得目前项目中所有的附件信息
      }
      this.validFile()
    },
    /** 表单-校验附件 */
    validFile () {
      if (this.fileList.length > 0) {
        this.$set(this.ruleForm, 'fileList', 'fileList')
      } else {
        this.$set(this.ruleForm, 'fileList', '')
      }
      if (this.ruleForm.fileList) {
        this.$refs['fileList'][this.index].clearValidate()
      }
    },
    /** 弹窗-上传附件 */
    handleSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        let fileInformations = []
        let existFileSize = 0
        //  得到已经保存的附件信息
        for (var i = 0; i < fileList.length; i++) {
          if (!fileList[i].response) {
            existFileSize++
            fileInformations[i] = fileList[i]
          }
        }
        // 添加新的附件信息
        if (existFileSize < fileList.length) {
          for (var j = existFileSize; j < fileList.length; j++) {
            if (fileList[j].response) {
              fileInformations[j] = fileList[j].response.fileInformation
            }
          }
        }
        this.fileAddList = fileInformations // 获得目前项目中所有的附件信息
      }
      this.validFiles()
    },
    /** 弹窗-校验附件 */
    validFiles () {
      if (this.fileAddList.length > 0) {
        this.$set(this.bidfileAddForm, 'fileAddList', 'fileAddList')
      } else {
        this.$set(this.bidfileAddForm, 'fileAddList', '')
      }
      if (this.bidfileAddForm.fileAddList) {
        this.$refs['fileAddList'].clearValidate()
      }
    },
    /** 查询未关联资格预审文件的标段信息 */
    sectionNotList () {
      this.tableAddData = []
      qualfile.getSectionList(this.noticeCode).then((res) => {
        if (res.data.sectionList) {
          this.tableAddData = res.data.sectionList
          if (this.tableAddData.length > 0) {
            this.addBtnDisabled = false
          } else {
            this.addBtnDisabled = true
          }
        } else {
          return false
        }
      })
    },
    /** 查询审批记录 */
    getApproveRecodes () {
      qualfile.getApproveRecords(this.noticeCode, 5).then((res) => {
        this.approveRecordList = res.data.approveRecordList
      })
    },
    /** 查询资格预审文件 */
    getQualifiPreFileList () {
      let enterpriseType = this.$store.getters.authUser.enterpriseType
      let obj = {
        noticeCode: this.noticeCode
      }
      if (enterpriseType !== 4) {
        obj.statusList = '2,3,4,5'
      }
      qualfile.getList(obj).then((res) => {
        let qualifiPreFileList = res.data.qualifiPreFileList
        if (qualifiPreFileList) {
          this.qualifiPreFileList = qualifiPreFileList
          if (this.qualifiPreFileList.length > 0) {
            // 默认展开第一个资格预审文件信息
            this.activeName = this.qualifiPreFileList[0].objectId
            this.getQualifiPreFile(this.qualifiPreFileList[0].objectId, 0)
            // 根据登陆用户类型不同查看不同信息
            if (enterpriseType === 4) {
              this.dialogSubmit = true
              this.dialogApprove = false
              if (this.qualifiPreFileList[0].qualifiPreStatus !== 1 && this.qualifiPreFileList[0].qualifiPreStatus !== 5) {
                this.dialogSubmit = false
                this.dialogApprove = false
              }
            } else if (enterpriseType === 2 || enterpriseType === 3) {
              this.dialogSubmit = false
              this.dialogApprove = false
              if (this.projectEnterpriseType === 2 || this.projectEnterpriseType === 3) {
                if (this.qualifiPreFileList[0].qualifiPreStatus === 2) {
                  this.dialogApprove = true
                }
              }
            } else if (enterpriseType === 1) {
              this.dialogSubmit = false
              this.dialogApprove = true
              if (this.projectEnterpriseType !== 1 && this.qualifiPreFileList[0].qualifiPreStatus !== 3) {
                this.dialogApprove = false
              } else if (this.projectEnterpriseType === 1 && this.qualifiPreFileList[0].qualifiPreStatus !== 2) {
                this.dialogApprove = false
              }
            }
          } else {
            if (enterpriseType !== 4) {
              this.dialogApprove = false
              this.dialogSubmit = false
            } else {
              this.dialogSubmit = true
              this.dialogApprove = false
            }
          }
          // 如果进入的是招标变更查询，则只能进行查看操作
          if (!this.operationCheckFlag) {
            this.dialogApprove = false
            this.dialogSubmit = false
          }
        } else {
          return false
        }
      })
    },
    /** 查询展开的一条资格预审文件信息 */
    getQualifiPreFile (objectId, index) {
      this.ruleForm = {}
      this.fileList = []
      this.tableData = []
      this.index = index
      qualfile.getOne(objectId).then((res) => {
        let qualifiPreFile = res.data.qualifiPreFile
        if (qualifiPreFile) {
          this.ruleForm = qualifiPreFile
          let sellTime = []
          sellTime[0] = this.ruleForm.sellStartTime
          sellTime[1] = this.ruleForm.sellEndTime
          this.ruleForm.sellTime = sellTime
          let clarifyTime = []
          clarifyTime[0] = this.ruleForm.clarifyStartTime
          clarifyTime[1] = this.ruleForm.clarifyEndTime
          this.ruleForm.clarifyTime = clarifyTime
          this.ruleForm = Object.assign({}, this.ruleForm)
          this.calendarMain.display = this.ruleForm.qualifiPreTime
          this.getDate()
          this.fileList = this.ruleForm.fileInformationList
          this.tableData = this.ruleForm.sectionList
        }
      })
    },
    /** 查询项目进行状态 */
    getNoticeProgress () {
      let obj = {
        tenderProjectCode: this.$route.query.code,
        noticeCode: this.noticeCode
      }
      openbid.getNoticeProgress(obj).then((res) => {
        let result = res.data.noticeProgress
        if (result) {
          this.progressStage = result.progressStage
          // 若项目进行状态为资格预审会阶段，则资格预审会按钮高亮
          if (this.progressStage === 3) {
            this.$emit('noticeProgress', 3)
          }
          this.getNotLockedSections(this.progressStage)
        } else {
          return false
        }
      })
    },
    /** 查询未锁定的标段，若标段均被锁定，菜单按钮则只高亮至招标文件 */
    getNotLockedSections (progressStage) {
      section.getNotLockedSections(this.noticeCode).then((res) => {
        let sectionList = res.data.sectionList
        if (sectionList) {
          if (sectionList.length < 1) {
            this.$emit('noticeProgress', progressStage - 1)
          }
        }
      })
    },
    /** 设置审批状态颜色 */
    fliteredStatus (status) {
      if (status === 0) {
        return 'wait'
      } else if (status === 1) {
        return 'success'
      } else if (status === 2) {
        return 'error'
      } else if (status === 3) {
        return 'finish'
      }
    },
    /** 获取选中项目的详细信息 */
    getTenderProjectInfo () {
      tenderproject.getOneByCode(this.callbackdata).then((res) => {
        this.currentTenderProjectInfo = res.data.tenderProject
        this.projectEnterpriseType = this.currentTenderProjectInfo.projectEnterpriseType
        this.getQualifiPreFileList()
      })
    },
    /** 显示当前日期-默认日期 */
    getDate () {
      let date = new Date(this.calendarMain.display)
      this.calendarMain.display = formatDate(date, 'yyyy-MM-dd')
    }
  },
  /** 实例创建完成 */
  mounted () {
    this.sectionNotList()
    this.getApproveRecodes()
    this.getNoticeProgress()
    this.selectMonthInfo = formatDate(new Date(), 'yyyy-MM')
    if (this.selectMonthInfo) {
      this.getOptionalNoticeList()
    }
  },
  /** 组件注册 */
  components: {
    // 日历
    calendar
  }
}
</script>
<style lang="less">
/** 日历样式引入 */
@import url('./../../../../components/calendar/calendar.less');
#qualfile-page {
  .qualfile-cont, .app-record {
    .qualfile-tit {
      height: 50px;
      line-height: 50px;
      text-align: left;
      padding: 0 20px;
      background: #F7F6FB;
      span {
        font-weight: 800;
      }
    }
    .qualfile-form {
      padding: 20px;
      .btn-box {
        height: 50px;
      }
      .title-span {
        max-width: 260px;
        height: 20px;
        line-height: 20px;
        background: #ddd;
        padding: 5px 20px;
        margin-right: 10px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
      .form-box {
        padding-top: 20px;
        .el-icon-error {
          color: red;
        }
      }
      /** 折叠面板 */
      .el-collapse-item {
        margin-bottom: 10px;
      }
      .el-collapse-item__header {
        padding-left: 20px;
        text-align: left;
        background: #F7F6FB;
      }
      .el-collapse-item__header.is-active {
        background: #ebeffe;
      }
      .el-form-item__content {
        text-align: left;
      }
      .handle-btn {
        .el-form-item__content {
          text-align: center;
        }
      }
    }
  }
  /** 审批 */
  .exam-box {
    .el-form-item__content {
      text-align: left;
    }
    .result-savebtn {
      padding-bottom: 20px;
    }
  }
  /** 折叠面板 */
  /** 折叠面板 */
  .el-collapse-item {
    margin-bottom: 10px;
    .el-collapse-item__wrap {
      overflow: visible;
    }
  }
  /** 审批记录 */
  .app-record {
    .division-line {
      height: 20px;
      background: #EAEDF2;
    }
    .app-cont {
      height: 300px;
      margin-top: 30px;
      overflow-x: hidden;
      overflow-y: auto;
      .step-span {
        display: inline-block;
        padding-right: 20px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
      .span-first {
        width: 160px;
      }
      .span-second {
        width: 220px;
      }
      .span-third {
        width: 160px;
      }
      .span-last {
        float: right;
      }
      .apply-option {
        span {
          font-size: 14px;
          color: #999;
        }
      }
      .span-color {
        color: #999;
      }
    }
  }
  /** 弹窗 */
  .dialog-box {
    .form-box {
      .el-form-item__content {
        text-align: left;
      }
      .last-form-btn {
        .el-form-item__content {
          text-align: center;
          margin-left: 0 !important;
        }
      }
    }
  }
}
</style>
