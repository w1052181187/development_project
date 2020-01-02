<template>
  <!-- 答疑补疑 -->
  <div class="admin-layout" id="answerques-page">
    <div class="answerques-cont">
      <div class="answerques-tit">
        <span>答疑补疑</span>
      </div>
      <div class="amswerques-addbtn">
        <el-button @click="addBtnClick" type="warning" size="medium" icon="el-icon-plus" v-if="dialogSubmit">新 增</el-button>
      </div>
      <div class="answerques-table">
        <el-table
          class="table-box"
          border
          :data="tableData"
          :row-class-name="tableRowClassName"
          style="width: 100%">
          <el-table-column
            type="index"
            label="序号"
            align="center"
            :index="indexMethod"
            width="55">
          </el-table-column>
          <el-table-column
            label="标段名称"
            prop="section.name"
            header-align="center"
            show-overflow-tooltip
            align="left"
            width="200">
          </el-table-column>
          <el-table-column
            prop="clarifyType"
            label="类型"
            align="center"
            :formatter="filterType">
          </el-table-column>
          <el-table-column
            prop="bidderName"
            label="质疑企业"
            header-align="center"
            align="left">
            <template slot-scope="scope">
              <p v-for="(item, index) in scope.row.bidderName" :key="index">{{item.bidderName}}</p>
            </template>
          </el-table-column>
          <el-table-column
            prop="userName"
            label="提交人"
            align="center">
          </el-table-column>
          <el-table-column
            prop="createDate"
            label="提交时间"
            align="center"
            :formatter="filterDate">
          </el-table-column>
          <el-table-column
            label="操作"
            align="center">
            <template slot-scope="scope">
              <el-button @click="handleOneTableClick(scope.row,'look')" type="text" size="medium">查看</el-button>
              <el-button @click="handleOneTableClick(scope.row,'edit')" type="text" size="medium" v-if="(scope.row.qualifiPreClariStatus == 1 || scope.row.qualifiPreClariStatus == 5) && dialogSubmit ">编辑</el-button>
              <el-button @click="handleOneTableClick(scope.row,'del')" type="text" size="medium"  v-if="(scope.row.qualifiPreClariStatus == 1 || scope.row.qualifiPreClariStatus == 5) && dialogSubmit ">删除</el-button>
              <el-button
                @click="handleOneTableClick(scope.row,'exam')"
                type="text"
                size="medium"
                v-if="currentTenderProjectInfo.projectEnterpriseType !== 1 &&(currentTenderProjectInfo.selectApply.enterpriseId === $store.getters.authUser.enterpriseId ||
                $store.getters.authUser.enterpriseType === 1)
                 && (scope.row.qualifiPreClariStatus === 2 || scope.row.qualifiPreClariStatus === 3)">
              {{($store.getters.authUser.enterpriseType === 1 && scope.row.qualifiPreClariStatus === 3) ? '备案' : '审批'}}
              </el-button>
              <el-button
                @click="handleOneTableClick(scope.row,'exam')"
                type="text"
                size="medium"
                v-if="currentTenderProjectInfo.projectEnterpriseType === 1 &&(currentTenderProjectInfo.selectApply.enterpriseId === $store.getters.authUser.enterpriseId ||
                $store.getters.authUser.enterpriseType === 1)
                 && scope.row.qualifiPreClariStatus === 2">
                备案
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <!-- 弹窗区域start -->
    <div class="answques-dialog">
      <!-- 新增和编辑弹窗start -->
      <el-dialog
        @close="closeDialog('ruleForm')"
        :title="dialogTitle"
        :visible.sync="dialogVisible"
        width="40%">
        <div class="bidpro-addbtn">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
            <el-form-item label="澄清类型:" prop="clarifyType">
              <el-select v-model="ruleForm.clarifyType" placeholder="请选择类型">
                <el-option
                  v-for="item in clarifyTypeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="澄清标段:" prop="name">
              <el-select v-model="ruleForm.name" placeholder="请选择标段" @change="changeSection">
                <el-option
                  v-for="item in sectionList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item
              label="质疑单位:"
              :prop="(ruleForm.clarifyType === 1)?'bidderNameArr':'false'">
              <el-select
                v-model="bidderNameArr"
                multiple
                placeholder="请选择单位"
                @change="changeBidder"
                @visible-change="visibleChange">
                <el-option
                  v-for="item in bidderInfoList"
                  :key="item.code"
                  :label="item.bidderName"
                  :value="item.code">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="澄清文件:" prop="fileList" ref="fileList">
              <el-upload
                class="upload-demo"
                :action="upLoad"
                :on-success="handleSuccess"
                :file-list="fileList"
                :show-file-list="false">
                <el-button size="small" type="success" class="upload-btn" >上传附件</el-button>
              </el-upload>
              <!-- 上传附件列表 -->
              <div class="apply-upload">
                <el-row>
                  <el-col :span="24">
                  <span class="file-list" v-for="(item, index) in  fileList" :key="index">
                    <el-button
                      type="text"
                      style="padding-right: 20px;width: 80%;"
                      @click="downloadFile(item.fileName,item.relativePath)">
                      <span
                        style="width:100%;display: inline-block;overflow: hidden;text-align: left;text-overflow: ellipsis; white-space: nowrap;">
                        {{item.fileName}}
                      </span>
                    </el-button>
                    <i class="el-icon-error upload-file" @click="clickDel(index)"></i>
                  </span>
                  </el-col>
                </el-row>
              </div>
            </el-form-item>
            <div class="form-ueditor">
              <el-form-item label="澄清内容:" prop="content" ref="content">
                <ueditor ref="ueditor" :content="ueditorContent"></ueditor>
              </el-form-item>
            </div>
            <el-form-item label="开标时间:" prop="bidOpenTime" ref="bidOpenTime">
              <div class="flex">
                <div style="height:0;border:none;">
                  <input type="text" @click="openByDrop($event)" v-model="calendar.display" style="margin-top:0;" @blur="validTime">
                </div>
              </div>
              <transition name="fade">
                <div
                  class="calendar-dropdown"
                  :style="{'left':calendar.left+'px','top':calendar.top+'px'}"
                  v-if="calendar.show">
                  <calendar
                    ref="sonV"
                    :zero="calendar.zero"
                    :events="calendar.events"
                    :lunar="calendar.lunar"
                    :value="calendar.value"
                    :begin="calendar.begin"
                    :end="calendar.end"
                    @selectMonth="calendar.selectMonth"
                    @select="calendar.select">
                  </calendar>
                </div>
              </transition>
            </el-form-item>
          </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button type="success" @click="submitClick('ruleForm')" >提 交</el-button>
          <el-button type="primary" @click="saveClick ('ruleForm')" >保 存</el-button>
          <el-button @click="dialogVisible = false">取 消</el-button>
        </span>
      </el-dialog>
      <!-- 新增和编辑弹窗end -->

      <!-- 查看弹窗start -->
      <el-dialog
        title="查看"
        @close="closeDialog('lookRuleForm')"
        :visible.sync="lookDialogVisible"
        width="50%">
        <div class="bidpro-addbtn">
          <el-form :model="lookRuleForm" label-width="100px">
            <el-form-item label="澄清类型:">
              <span>{{lookRuleForm.clarifyType | filterType}}</span>
            </el-form-item>
            <el-form-item label="澄清标段:">
              <span>{{lookRuleForm.name}}</span>
            </el-form-item>
            <el-form-item label="质疑单位:" >
              <span v-for="(item,index) in bidderInfoNameList" :key="index" class="dresult-span">{{item}}<br></span>
            </el-form-item>
            <el-form-item label="澄清文件:">
              <span class="file-list" v-for="(item, index) in lookRuleForm.fileInformationList" :key="index">
              <span><el-button type="text" @click="downloadFile(item.fileName,item.relativePath)" style="padding-right: 30px;">{{item.fileName}}</el-button></span>
            </span>
            </el-form-item>
            <div class="form-ueditor">
              <el-form-item label="澄清内容:" >
                <ueditor ref="ueditor_detail" :content="ueditorContent"></ueditor>
              </el-form-item>
            </div>
            <el-form-item label="开标时间:">
              <span>{{lookRuleForm.bidOpenTime |filterDate}}</span>
            </el-form-item>
            <!-- 审批 -->
            <div class="review-pass" v-if="examShow">
              <el-form :model="approveForm" :rules="approveFormRules" ref="approveForm" label-width="100px">
                <el-form-item :label="($store.getters.authUser.enterpriseType === 1) ? '备案状态' : '审批状态'" prop="approveResult">
                  <el-select v-model="approveForm.approveResult">
                    <el-option
                      v-for="item in stateList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item :label="($store.getters.authUser.enterpriseType === 1) ? '备案意见' : '审批意见'" v-if="approveForm.approveResult !== 1" prop="approveOpinion">
                  <el-input type="textarea" v-model="approveForm.approveOpinion" style="width: 280px;">{{approveForm.approveResult}}</el-input>
                </el-form-item>
                <el-form-item :label="($store.getters.authUser.enterpriseType === 1) ? '备案意见' : '审批意见'" v-if="approveForm.approveResult === 1" >
                  <el-input type="textarea" v-model="approveForm.approveOpinion" style="width: 280px;">{{approveForm.approveResult}}</el-input>
                </el-form-item>
              </el-form>
            </div>
            <el-form-item label="审批记录:" class="el-form-last">
              <div class="app-record" style="height: 300px">
                <el-steps
                  direction="vertical"
                  :active="1"
                  :space="100">
                  <el-step
                    v-for="(item,index) in approveRecordList"
                    :key="index"
                    :status='fliteredStatus(item.approveResult)'>
                    <span slot="title" class="step-span span-color span-first" v-if="item.approveResult === 0">{{item.applyName}}</span>
                    <span slot="title" class="step-span span-color span-first" v-if="item.approveResult !== 0">{{item.approveName}}</span>
                    <span slot="title" class="step-span span-color span-second" v-if="item.approveResult === 0">{{item.applyOrganization}}</span>
                    <span slot="title" class="step-span span-color span-second" v-if="item.approveResult !== 0">{{item.approveOrganization}}</span>
                    <span slot="title" class="step-span span-third">{{item.approveResult | filterResult}}</span>
                    <span slot="title" class="step-span span-color span-last">{{item.approveTime |filterDate }}</span>
                    <div slot="title" class="apply-option" v-if="item.approveOpinion">
                      <span>审批意见:{{item.approveOpinion}}</span>
                    </div>
                  </el-step>
                </el-steps>
              </div>
             </el-form-item>
            <!-- 按钮 -->
            <div class="review-sub-btn" v-if="examShow">
              <el-button @click="$router.go(-1)">取消</el-button>
              <el-button type="primary" @click="submitData('approveForm')">提交</el-button>
            </div>
          </el-form>
        </div>
      </el-dialog>

      <!-- 查看弹窗end -->
    </div>
    <!-- 弹窗区域end -->
  </div>
</template>
<script>
// 引入日历
import calendar from '@/components/calendar/calendar.vue'
// 引入编辑器
import ueditor from '@/components/ueditor/ueditor.vue'
import { formatDate } from 'common/js/date'
import {answerques, qualfile, bidderInfo, tenderproject, bidfile, qualfileResult, openbid, section, notice} from 'api/index'
import {commonJs, downloadFile} from 'common/js/common'
export default {
  data () {
    return {
      calendar: {
        display: '',
        show: false,
        zero: true,
        value: [], // 默认日期
        lunar: true, // 显示农历
        events: {},
        selectMonth: (month, year) => {
          this.selectMonthInfo = year + '-' + month
          if (this.selectMonthInfo) {
            this.getOptionalNoticeList()
          }
        },
        select: (value) => {
          this.calendar.show = false
          this.calendar.value = value
          this.calendar.display = value.join('-')
          this.validTime()
        }
      },
      selectMonthInfo: '',
      // showUeditor: false, // 演示加载ueditor
      dialogTitle: '新增',
      dialogVisible: false,
      dialogSubmit: false, // 是否显示
      examShow: false, // 是否审批
      isEdit: true, // 是否编辑
      ruleForm: {
        content: '',
        name: '',
        bidOpenTime: ''
      },
      bidderNameArr: [], // 质疑单位-选中值
      approveForm: {},
      approveResult: [],
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
      bidderInfoList: [], // 质疑单位-下拉数据
      clarifyTypeList: [
        {
          label: '答疑',
          value: 1
        },
        {
          label: '补遗',
          value: 2
        }
      ],
      sectionList: [],
      statusList: [
        {
          label: '通过',
          value: 1
        },
        {
          label: '退出',
          value: 2
        }
      ],
      rules: {
        clarifyType: [
          {required: true, message: '请选择澄清类型', trigger: 'blur'}
        ],
        name: [
          {required: true, message: '请选择澄清标段', trigger: 'change'}
        ],
        bidderNameArr: [
          {required: true, message: '请选择质疑单位', trigger: 'change'}
        ],
        fileList: [
          {required: true, message: '请上传附件', trigger: 'blur'}
        ],
        content: [
          {required: true, message: '请输入澄清内容', trigger: 'blur'},
          { min: 1, max: 200, message: '请输入1~500个字符', trigger: 'blur' }
        ],
        bidOpenTime: [
          {required: true, message: '请选择开标时间', trigger: 'change'}
        ]
      },
      approveFormRules: {
        approveResult: [
          { required: true, message: '请选择审批状态', trigger: 'change' }],
        approveOpinion: [
          {required: true, message: '请输入审批意见', trigger: 'change'}]
      },
      lookDialogVisible: false, // 查看弹窗
      lookRuleForm: {
        content: ''
      }, // 查看表单
      ueditorContent: '', // 编辑器内容-查看
      fileList: [], // 上传文件列表数据
      upLoad: commonJs.fileUploadUrl,
      approveRecordList: [], // 审批记录数据
      bidderInfoNameList: [],
      currentPage: 1,
      tableData: [],
      currentTenderProjectInfo: {
        selectApply: {
          enterpriseId: ''
        }
      },
      code: '',
      isPrequalification: null
    }
  },
  computed: {},
  props: ['callbackdata', 'noticeCode', 'relatedType', 'operationCheckFlag', 'dialogCheckVisible'],
  watch: {
    relatedType: function (newVal, oldVal) {
      if (newVal) {
        this.clarifyData()
        this.getAllSenstion()
      }
    }
  },
  created () {
    if (this.dialogCheckVisible === true && this.$route.query.currentView === 'answerQues') {
      this.examShow = this.dialogCheckVisible
      this.lookDialogVisible = this.dialogCheckVisible
      this.clarifyOneData(this.$route.query.row, 'exam')
    }
    this.getTenderProjectInfo()
  },
  filters: {
    filterType (clarifyType) {
      if (clarifyType === 1) {
        return '答疑'
      } else if (clarifyType === 2) {
        return '补遗'
      }
    },
    filterDate (value) {
      if (value) {
        let date = new Date(value)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return null
      }
    },
    filterApproveTime (val) {
      if (val) {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd ')
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
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 日期组件--弹窗 */
    openByDrop (e) {
      this.calendar.show = true
      this.calendar.left = e.target.offsetLeft + 19
      this.calendar.top = e.target.offsetTop + 70
      e.stopPropagation()
      window.setTimeout(() => {
        document.addEventListener('click', (e) => {
          this.calendar.show = false
          document.removeEventListener('click', () => {}, false)
        }, false)
      }, 1000)
    },
    /** 日期组件--验证时间 */
    validTime () {
      if (this.calendar.display) {
        this.$set(this.ruleForm, 'bidOpenTime', 'bidOpenTime')
      } else {
        this.$set(this.ruleForm, 'bidOpenTime', '')
      }
      if (this.ruleForm.bidOpenTime) {
        this.$refs['bidOpenTime'].clearValidate()
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
        this.$set(this.calendar, 'events', resultMap)
      }
    },
    /** 显示当前日期-默认日期 */
    getDate () {
      let date = new Date(this.calendar.display)
      this.calendar.display = formatDate(date, 'yyyy-MM-dd')
    },
    /** 格式化 */
    filterType (row, cloumn) {
      let clarifyType = row.clarifyType
      if (clarifyType === 1) {
        return '答疑'
      } else if (clarifyType === 2) {
        return '补遗'
      }
    },
    filterDate (row, column) {
      if (!row.createDate) {
        return ''
      } else {
        let date = new Date(row.createDate)
        return formatDate(date, 'yyyy-MM-dd ')
      }
    },
    filterDates (row, column) {
      if (!row.bidOpenTime) {
        return ''
      } else {
        let date = new Date(row.bidOpenTime)
        return formatDate(date, 'yyyy-MM-dd ')
      }
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 查询列表 */
    clarifyData () {
      /* let  enterpriseType = this.$store.getters.authUser.enterpriseType */
      let obj = {
        noticeCode: this.noticeCode,
        relatedType: this.relatedType
      }
      answerques.getList(obj).then((res) => {
        this.tableData = []
        if (this.currentTenderProjectInfo.selectApply.enterpriseId === this.$store.getters.authUser.enterpriseId) {
          res.data.clarifyList.forEach(clarify => {
            if (clarify.qualifiPreClariStatus === 2 || clarify.qualifiPreClariStatus === 4) {
              this.tableData.push(clarify)
              this.dialogSubmit = false
            }
          })
        } else if (this.$store.getters.authUser.enterpriseType === 1) {
          if (this.currentTenderProjectInfo.projectEnterpriseType !== 1) {
            res.data.clarifyList.forEach(clarify => {
              if (clarify.qualifiPreClariStatus === 3 || clarify.qualifiPreClariStatus === 4) {
                this.tableData.push(clarify)
                this.dialogSubmit = false
              }
            })
          } else {
            res.data.clarifyList.forEach(clarify => {
              if (clarify.qualifiPreClariStatus === 2 || clarify.qualifiPreClariStatus === 4) {
                this.tableData.push(clarify)
                this.dialogSubmit = false
              }
            })
          }
        } else if (this.$store.getters.authUser.enterpriseType === 4) {
          this.tableData = res.data.clarifyList
        }
        let newArr = this.tableData
        let newChild = newArr.map((item, i) => {
          let obj = {
            bidderName: item.bidderInfoList.map((item) => {
              return {bidderName: item.bidderName}
            })
          }
          // 合并
          let result = Object.assign(item, obj)
          return result
        })
        this.tableData = newChild
      })
    },
    /** 查询单条 */
    clarifyOneData (row, type) {
      // 查看表单
      this.lookRuleForm = {}
      // 编辑表单
      this.ruleForm = {}
      this.sectionList = []
      // this.$refs.ueditor.setContent(this.ueditorContent)
      answerques.getOne(row.objectId).then((res) => {
        let data = res.data.clarify
        // 编辑器赋值
        let ueCont = data.content
        this.approveRecordList = res.data.clarify.approveRecordList
        // 查看表单数据
        if (Object.is(type, 'look') || Object.is(type, 'exam')) {
          this.$refs.ueditor_detail.setContent(ueCont)
          this.lookRuleForm = data
          this.lookRuleForm.name = data.section.name
          this.lookRuleForm.fileInformationList = data.fileInformationList
          this.bidderInfoNameList = []
          if (data.bidderInfoList.length > 0) {
            for (let i = 0; i < data.bidderInfoList.length; i++) {
              this.bidderInfoNameList.push(data.bidderInfoList[i].bidderName)
            }
          }
          if (this.lookRuleForm.qualifiPreStatus === 4 || Object.is(type, 'look')) {
            this.examShow = false
          }
        }
        // 编辑表单数据
        if (Object.is(type, 'edit')) {
          this.$refs.ueditor.setContent(ueCont)
          this.ruleForm = data
          // 开标时间
          this.calendar.display = this.ruleForm.bidOpenTime
          this.getDate()
          this.ruleForm.name = data.section.name
          // 上传数据列表
          this.fileList = data.fileInformationList
          // 根据澄清标段Code 获得质疑单位下拉数据
          this.changeSection(row.sectionCode)
          // 获得所有澄清标段
          this.getAllSenstion()
          if (data.bidderInfoList.length > 0) {
            this.bidderNameArr = []
            for (let i = 0; i < data.bidderInfoList.length; i++) {
              this.bidderNameArr.push(data.bidderInfoList[i].code)
            }
          } else {
            this.bidderNameArr = []
          }
        }
      })
    },
    /** 新增 */
    addBtnClick () {
      // 打开弹窗
      this.ruleForm = {}
      this.fileList = []
      this.bidderNameArr = []
      this.sectionList = []
      this.bidderInfoList = []
      if (this.$refs.ueditor) {
        this.$refs.ueditor.setContent(this.ueditorContent = '')
      }
      // 获得澄清标段
      this.getAllSenstion()
      this.dialogVisible = true
      this.dialogTitle = '新增'
      this.isEdit = false
    },
    /** 表格操作 */
    handleOneTableClick (row, type) {
      if (Object.is(type, 'edit')) {
        this.dialogVisible = true
        this.dialogTitle = '编辑'
        // 获得编辑内容
        this.clarifyOneData(row, type)
      } else if (Object.is(type, 'look')) {
        this.lookDialogVisible = true
        // this.showUeditor = true
        this.clarifyOneData(row, type)
      } else if (Object.is(type, 'exam')) {
        this.approveForm = {}
        this.lookDialogVisible = true
        this.examShow = true
        this.clarifyOneData(row, type)
      } else if (Object.is(type, 'del')) {
        this.deleteClick(row)
      }
    },
    /** 删除 */
    deleteClick (data) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        answerques.delete(data.objectId).then((res) => {
          this.clarifyData()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    /** 上传附件 */
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
        this.fileList = fileInformations // 获得目前项目中所有的附件信息
      }
    },
    /** 删除上传附件 */
    clickDel (index) {
      this.fileList.splice(index, 1)
      this.validFile()
    },
    /** 校验附件 */
    validFile () {
      if (this.fileList.length > 0) {
        this.$set(this.ruleForm, 'fileList', 'fileList')
      } else {
        this.$set(this.ruleForm, 'fileList', '')
      }
      if (this.ruleForm.fileList) {
        this.$refs['fileList'].clearValidate()
      }
    },
    /** 校验公示内容 */
    validContent () {
      let content = this.$refs.ueditor.getContent()
      if (content) {
        this.$set(this.ruleForm, 'content', content)
      } else {
        this.$set(this.ruleForm, 'content', '')
      }
      if (this.ruleForm.content) {
        this.$refs['content'].clearValidate()
      }
    },
    /** 提交 */
    submitClick (val) {
      this.ruleForm.flag = 2
      let arr = []
      if (this.bidderNameArr) {
        arr = this.bidderNameArr.map((item) => { return {code: item} })
        this.$set(this.ruleForm, 'bidderNameArr', this.bidderNameArr)
      }
      this.ruleForm.bidderInfoList = arr
      this.ruleForm.content = this.$refs.ueditor.getContent()
      this.ruleForm.fileInformationList = this.fileList
      this.ruleForm.bidOpenTime = this.calendar.display
      this.save(this.ruleForm, val)
    },
    /** 保存 */
    saveClick (val) {
      this.ruleForm.flag = 1
      let arr = []
      if (this.bidderNameArr) {
        arr = this.bidderNameArr.map((item) => { return {code: item} })
        this.$set(this.ruleForm, 'bidderNameArr', this.bidderNameArr)
      }
      this.ruleForm.bidderInfoList = arr
      this.ruleForm.content = this.$refs.ueditor.getContent()
      this.ruleForm.fileInformationList = this.fileList
      this.ruleForm.bidOpenTime = this.calendar.display
      this.save(this.ruleForm, val)
    },
    /** 关闭弹窗 */
    closeDialog (val) {
      if (Object.is(val, 'ruleForm')) {
        this.$refs[val].resetFields()
        this.$refs.ueditor.setContent(this.ruleForm.content = '')
      }
      if (Object.is(val, 'lookRuleForm')) {
        this.$refs.ueditor.setContent(this.ueditorContent = '')
      }
    },
    /** 根据公告编号获取标段信息 */
    getAllSenstion () {
      section.getNotLockedSections(this.noticeCode).then((res) => {
        let result = res.data.sectionList
        if (result.length > 0) {
          let info = []
          for (let i = 0; i < result.length; i++) {
            let obj = {}
            obj.value = result[i].code
            obj.label = result[i].name
            info[i] = obj
          }
          this.sectionList = info
        }
      })
    },
    /** 澄清标段改变事件 */
    changeSection (code) {
      // 质疑单位清空-防止code重复
      this.bidderNameArr = []
      if (code) {
        // 根据澄清标段code获取质疑单位数据
        this.getByRelatedInfo(code)
        if (!this.isEdit) {
          // 开标时间
          this.getTime(code)
        }
      }
      this.ruleForm.sectionCode = code
    },
    /** 根据澄清标段code获取投标人信息 */
    getByRelatedInfo (code) {
      this.bidderInfoList = []
      let obj = {
        noticeCode: this.noticeCode,
        sectionCode: code
      }
      // 预审
      if (this.relatedType === 1) {
        bidderInfo.getByRelatedCode(this.noticeCode, code).then((res) => {
          // 质疑单位下拉数据
          this.bidderInfoList = res.data.bidderInfoList
        })
      } else if (this.relatedType === 2) {
        // 候审
        // 没有走预审，直接走候审
        if (this.isPrequalification === 0) {
          bidderInfo.getByRelatedCode(this.noticeCode, code).then((res) => {
            // 质疑单位下拉数据
            this.bidderInfoList = res.data.bidderInfoList
          })
        } else if (this.isPrequalification === 1) {
          // 走过预审，再走候审
          qualfileResult.getBidderInfo(obj).then((res) => {
            let qualifiPreResult = res.data.qualifiPreResult
            if (qualifiPreResult) {
              let result = qualifiPreResult.qualifiPreResultSublistList
              if (result) {
                this.bidderInfoList = result.map((item) => {
                  let bidderInfo = {}
                  bidderInfo.code = item.bidderCode
                  bidderInfo.bidderName = item.bidderName
                  return bidderInfo
                })
              }
            }
          })
        }
      }
    },
    /** 质疑单位change事件 */
    changeBidder (arr) {
      this.ruleForm.bidderNameArr = arr
    },
    /** 质疑单位清空 */
    visibleChange (val) {
      // 防止多个code
      if (val) {
        this.ruleForm.bidderNameArr = []
      }
    },
    /** 新增/修改 */
    save (obj, val) {
      this.validFile()
      this.validContent()
      obj.relatedType = this.relatedType
      this.$refs[val].validate((vaild) => {
        if (vaild) {
          this.ruleForm.noticeCode = this.noticeCode
          answerques.save(obj).then(() => {
            this.clarifyData()
            this.clarifyOneData()
          })
          this.dialogVisible = false
        } else {
          return false
        }
      })
    },
    /** 获取选中项目的详细信息 */
    getTenderProjectInfo () {
      tenderproject.getOneByCode(this.callbackdata).then((res) => {
        if (res) {
          this.currentTenderProjectInfo = res.data.tenderProject
          this.isPrequalification = this.currentTenderProjectInfo.isPrequalification
        }
        this.clarifyData()
      })
    },
    /** 保存审批记录 */
    submitData (data) {
      this.$refs[data].validate((valid) => {
        if (valid) {
          this.lookRuleForm.approveResult = this.approveForm.approveResult
          this.lookRuleForm.approveOpinion = this.approveForm.approveOpinion || ''
          answerques.save(this.lookRuleForm).then(() => {
            this.examShow = false
            this.lookDialogVisible = false
            this.clarifyData()
          })
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
    // 下载附件
    downloadFile,
    /** 查询资格预审文件的开标时间/资格预审时间 */
    getTime (code) {
      if (this.relatedType === 1) {
        qualfile.getByCode(this.noticeCode, code).then((res) => {
          let qualifiPreTime = res.data.qualifiPreFile.qualifiPreTime
          if (qualifiPreTime) {
            this.$set(this.ruleForm, 'bidOpenTime', qualifiPreTime)
            this.calendar.display = res.data.qualifiPreFile.qualifiPreTime
            this.getDate()
          }
        })
      } else if (this.relatedType === 2) {
        bidfile.getByCode(this.noticeCode, code).then((res) => {
          let bidOpenTime = res.data.tenderDoc.bidOpenTime
          if (bidOpenTime) {
            this.$set(this.ruleForm, 'bidOpenTime', bidOpenTime)
            this.calendar.display = res.data.tenderDoc.bidOpenTime
            this.getDate()
          }
        })
      }
    },
    /** 查询资格预审文件/招标文件，如有数据则可以编辑答疑补遗 */
    getQualifileData () {
      if (this.relatedType === 1) {
        let obj = {
          noticeCode: this.noticeCode,
          qualifiPreStatus: 4
        }
        qualfile.getList(obj).then((res) => {
          let qualifiPreFileList = res.data.qualifiPreFileList
          if (qualifiPreFileList.length > 0) {
            this.dialogSubmit = true
          } else {
            this.dialogSubmit = false
          }
        }).then((res) => {
          if (this.dialogSubmit === true) {
            this.getQualpre()
          }
        })
      } else if (this.relatedType === 2) {
        let obj = {
          noticeCode: this.noticeCode,
          tenderDocStatus: 4
        }
        bidfile.getList(obj).then((res) => {
          let tenderDocList = res.data.tenderDocList
          if (tenderDocList.length > 0) {
            this.dialogSubmit = true
          } else {
            this.dialogSubmit = false
          }
        }).then((res) => {
          if (this.dialogSubmit === true) {
            this.getQualpre()
          }
        })
      }
    },
    /** 查询资格预审会/开评标是否存在数据，如果有则不可编辑 */
    getQualpre () {
      let qualifiPreResult = null
      let obj = {
        noticeCode: this.noticeCode
      }
      if (this.relatedType === 1) {
        qualfileResult.getQualifiPreResult(obj).then((res) => {
          qualifiPreResult = res.data.qualifiPreResult
          if (qualifiPreResult) {
            this.dialogSubmit = false
          }
        })
      } else if (this.relatedType === 2) {
        openbid.getOpenRecord(obj).then((res) => {
          qualifiPreResult = res.data.openingRecord
          openbid.getEvaluationResult(obj).then((res) => {
            let evaluationResult = res.data.evaluationResult
            if (qualifiPreResult || evaluationResult) {
              this.dialogSubmit = false
            }
          })
        })
      }
    },
    /** 查询未锁定的标段，若标段均被锁定，招答疑补遗只可查看 */
    getNotLockedSections () {
      let sectionList = []
      section.getNotLockedSections(this.noticeCode).then((res) => {
        sectionList = res.data.sectionList
      }).then((res) => {
        if (sectionList) {
          if (sectionList.length < 1) {
            this.dialogSubmit = false
          } else {
            if (!this.operationCheckFlag) {
              this.dialogSubmit = false
            } else {
              this.getQualifileData()
            }
          }
        }
      })
    }
  },
  /** 实例创建完成 */
  mounted () {
    let enterpriseType = this.$store.getters.authUser.enterpriseType
    if (enterpriseType === 4) {
      this.getNotLockedSections()
    } else {
      this.dialogSubmit = false
    }
    this.getAllSenstion()
    this.selectMonthInfo = formatDate(new Date(), 'yyyy-MM')
    if (this.selectMonthInfo) {
      this.getOptionalNoticeList()
    }
  },
  /** 组件注册 */
  components: {
    // 编辑器
    ueditor,
    // 日历
    calendar
  }
}
</script>
<style lang="less">
/** 日历样式引入 */
@import url('./../../../../components/calendar/calendar.less');
#answerques-page {
  .answerques-cont {
    .answerques-tit {
      height: 50px;
      line-height: 50px;
      text-align:left;
      padding: 0 20px;
      background:#F7F6FB;
      span {
        font-weight: 800;
      }
    }
    .amswerques-addbtn {
      text-align: left;
      padding: 10px 20px 10px;
    }
    .answerques-table {
      padding: 0 20px 20px;
    }
  }
  /** 表格样式 */
  .table-box tr{
    td:nth-child(4) {
      div.cell {
        padding:0;
      }
      p {
        margin: 0;
        padding: 10px 8px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
      p:not(:last-child) {
        border-bottom: 1px solid #eee;
      }
    }
  }
  /** 弹窗 */
  .answques-dialog {
    .el-form-item__content {
      text-align: left;
    }
    .form-ueditor {
      .el-form-item__content {
        line-height: 0;
      }
      .ueditor-wrap {
        padding: 0;
      }
    }
    .el-dialog__footer {
      text-align: center;
    }
  }
  /** 审批记录 */
  .el-form-last {
    .el-form-item__content {
      line-height: 0;
    }
  }
  .app-record {
    margin-top: 20px;
    padding-top: 20px;
    border-top: 1px solid #eeeeee;
    overflow: hidden;
    overflow-y: auto;
    .step-span {
      width: 140px;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
      padding-right: 20px;
      box-sizing: border-box;
      display: inline-block;
      font-weight: normal;
      font-size: 14px;
    }
    .span-first {
      width: 140px;
    }
    .span-second {
      width: 160px;
    }
    .span-third {
      width: 100px;
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
</style>
