<template>
  <!-- 公告 -->
  <div class="admin-layout" id="notice-page">
    <div class="notice-box">
      <div class="notice-tit">
        <span>招标公告</span>
      </div>
      <div class="notice-addbtn">
        <el-button
          type="warning"
          icon="el-icon-plus"
          @click="addNotice"
          v-if="this.$store.getters.authUser.enterpriseType === 4 && this.operationCheckFlag">
          添加公告</el-button>
      </div>
      <div class="notice-table">
        <el-table
          border
          max-height="400"
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
            label="公告标题"
            prop="noticeName"
            show-overflow-tooltip
            header-align="center"
            align="left">
          </el-table-column>
          <el-table-column
            prop="creatorName"
            label="提交人"
            align="center">
          </el-table-column>
          <el-table-column
            prop="createDate"
            label="提交时间"
            :formatter="filterSubDate"
            align="center">
          </el-table-column>
          <el-table-column
            prop="noticeStatus"
            label="审批状态"
            :formatter="filterNoticeStatus"
            align="center">
          </el-table-column>
          <el-table-column
            label="操作"
            align="center">
            <template slot-scope="scope">
              <el-button
                @click="handleTableClick(scope.row,'edit')"
                type="text" size="medium"
                v-if="(((scope.row.noticeStatus === 1 ||
                scope.row.noticeStatus === 5) &&
                scope.row.isLocked === 0) ||
                ((scope.row.noticeStatus === 1 ||
                scope.row.noticeStatus === 5) &&
                scope.row.noticeType === 7)) && operationCheckFlag">
                编辑
              </el-button>
              <el-button
                @click="handleTableClick(scope.row,'del')"
                type="text" size="medium"
                v-if="(((scope.row.noticeStatus === 1 ||
                scope.row.noticeStatus === 5) &&
                scope.row.isLocked === 0) ||
                ((scope.row.noticeStatus === 1 ||
                scope.row.noticeStatus === 5) &&
                (scope.row.noticeType === 7 ||
                scope.row.noticeType === 7))) && operationCheckFlag">
                删除
              </el-button>
              <el-button
                @click="handleTableClick(scope.row,'look')"
                type="text" size="medium">
                查看
              </el-button>
              <el-button
                @click="handleTableClick(scope.row,'exam')"
                type="text" size="medium"
                v-if="(($store.getters.authUser.enterpriseType === 2 ||
                $store.getters.authUser.enterpriseType === 3) &&
                scope.row.noticeStatus === 2) && operationCheckFlag">
                审批
              </el-button>
              <el-button
                @click="handleTableClick(scope.row,'exam')"
                type="text" size="medium"
                v-if="($store.getters.authUser.enterpriseType === 1 &&
                scope.row.noticeStatus === 3) && operationCheckFlag">
                备案
              </el-button>
              <el-dropdown
                size="mini"
                split-button
                type="primary"
                @command="handleCommand"
                style="margin-left: 15px;"
                v-if="scope.row.noticeStatus === 4 &&
                ((scope.row.isLocked === 0 &&
                scope.row.noticeType !== 3) ||
                scope.row.noticeType === 3 ) &&
                scope.row.noticeType !== 7 && operationCheckFlag
                && $store.getters.authUser.enterpriseType === 4">
                添加其他
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item :command="{name: '变更', rowCode: scope.row.code}" v-if="scope.row.noticeType !== 3 && scope.row.progressStage != 8">变更</el-dropdown-item>
                  <el-dropdown-item :command="{name: '再次', rowCode: scope.row.code}" >再次</el-dropdown-item>
                  <el-dropdown-item :command="{name: '延期', rowCode: scope.row.code}" v-if="scope.row.noticeType !== 3 && scope.row.progressStage != 8">延期</el-dropdown-item>
                  <el-dropdown-item :command="{name: '补充', rowCode: scope.row.code}" v-if="scope.row.noticeType !== 3 && scope.row.progressStage != 8">补充</el-dropdown-item>
                  <el-dropdown-item :command="{name: '撤销', rowCode: scope.row.code}" v-if="scope.row.noticeType !== 3 && scope.row.progressStage != 8">撤销</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <!-- 弹窗区域start -->
    <div class="notice-dialog">
      <!-- 编辑弹窗start -->
      <el-dialog
        :title="titleName"
        :visible.sync="dialogVisible"
        :before-close="handleClose"
        width="40%">
        <div class="doalog-form">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px">
            <el-form-item label="标段选择:" prop="phaseSelectTableData">
              <el-table
                ref="multipleTable"
                :data="phaseSelectTableData"
                border
                style="width: 100%"
                @selection-change="handleSelectionChange"
                :row-key="getRowKeys">
                <el-table-column
                  type="selection"
                  width="55"
                  prop="objectId"
                  :reserve-selection="true">
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="标段名称"
                  align="left">
                </el-table-column>
                <el-table-column
                  prop="planAmount"
                  label="计划金额(万元)"
                  align="left">
                </el-table-column>
              </el-table>
            </el-form-item>
            <el-form-item label="公告名称:" prop="noticeName">
              <span>{{ruleForm.noticeName}}</span>
            </el-form-item>
            <el-form-item label="公告类型:" prop="noticeType">
              <span>{{ruleForm.noticeType | filterNoticeType}}</span>
            </el-form-item>
            <el-form-item label="报名起止时间:" prop="noticeTime">
              <el-date-picker
                v-model="ruleForm.noticeTime"
                type="datetimerange"
                @change = "filterSubmitTime"
                format="yyyy-MM-dd hh:mm:ss"
                value-format="yyyy-MM-dd hh:mm:ss"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="资格预审时间:" :rules="rules.prequalificationTime" prop="prequalificationTime" ref="prequalificationTime" v-if="!prequalificationFlag">
              <div class="flex">
                <div style="height:0;border:none;">
                  <input type="text" @click="openByDrop($event)" v-model="calendarNotice.display" style="margin-top:0;" @blur="validTime">
                </div>
              </div>
              <transition name="fade">
                <div
                  class="calendar-dropdown"
                  :style="{'left':calendarNotice.left+'px','top':calendarNotice.top+'px'}"
                  v-if="calendarNotice.show">
                  <calendar
                    ref="sonV"
                    :zero="calendarNotice.zero"
                    :events="calendarNotice.events"
                    :lunar="calendarNotice.lunar"
                    :value="calendarNotice.value"
                    :begin="calendarNotice.begin"
                    :end="calendarNotice.end"
                    @selectMonth="calendarNotice.selectMonth"
                    @select="calendarNotice.select">
                  </calendar>
                </div>
              </transition>
              <!--<el-date-picker
                v-model="ruleForm.prequalificationTime"
                type="date"
                format="yyyy-MM-dd hh:mm:ss"
                value-format="yyyy-MM-dd hh:mm:ss"
                placeholder="选择日期">
              </el-date-picker>-->
            </el-form-item>
            <el-form-item label="开标时间:" :rules="rules.bidOpenTime" prop="bidOpenTime" ref ="bidOpenTime" v-if="prequalificationFlag">
              <div class="flex">
                <div style="height:0;border:none;">
                  <input type="text" @click="openByDrop($event)" v-model="calendarNotice.display" style="margin-top:0;" @blur="validTime">
                </div>
              </div>
              <transition name="fade">
                <div
                  class="calendar-dropdown"
                  :style="{'left':calendarNotice.left+'px','top':calendarNotice.top+'px'}"
                  v-if="calendarNotice.show">
                  <calendar
                    ref="sonV"
                    :zero="calendarNotice.zero"
                    :events="calendarNotice.events"
                    :lunar="calendarNotice.lunar"
                    :value="calendarNotice.value"
                    :begin="calendarNotice.begin"
                    :end="calendarNotice.end"
                    @selectMonth="calendarNotice.selectMonth"
                    @select="calendarNotice.select">
                  </calendar>
                </div>
              </transition>
              <!--<el-date-picker-->
                <!--v-model="ruleForm.bidOpenTime"-->
                <!--type="date"-->
                <!--format="yyyy-MM-dd hh:mm:ss"-->
                <!--value-format="yyyy-MM-dd hh:mm:ss"-->
                <!--placeholder="选择日期">-->
              <!--</el-date-picker>-->
            </el-form-item>
            <div class="ueditor-box">
              <el-form-item label="公告内容:" prop="noticeContent">
                <ueditor ref="ueditor" :content="fileContent" class="ueditor"></ueditor>
              </el-form-item>
            </div>
            <el-form-item label="发布媒介:" prop="noticeMedia">
              <el-input v-model="ruleForm.noticeMedia" style="width: 220px;"></el-input>
            </el-form-item>
            <el-form-item label="附件:">
              <el-upload
                class="upload-demo"
                :action="upLoad"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :before-remove="beforeRemove"
                :auto-upload="true"
                :on-success="uploadSuccess"
                :limit="1"
                :on-exceed="handleExceed"
                :file-list="fileList">
                <el-button size="small" type="primary" class="upload-btn">点击上传</el-button>
                <span>只可上传一个附件</span>
              </el-upload>
            </el-form-item>
          </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button type="success" @click="saveOrSubmitNotice('ruleForm', 'submit')" v-loading="btnLoading">提 交</el-button>
          <el-button type="primary" @click="saveOrSubmitNotice('ruleForm', 'save')">保 存</el-button>
          <el-button @click="cancel('ruleForm')">取 消</el-button>
        </span>
      </el-dialog>
      <!-- 编辑弹窗end -->

      <!-- 查看弹窗start -->
      <el-dialog
        title="查看公告"
        :visible.sync="lookDialogVisible"
        :before-close="handleClose"
        width="50%">
        <div class="dialog-look-form">
          <el-form :model="lookNoticeForm" label-width="100px">
            <el-form-item label="包含标段:" prop="phaseSelect">
              <el-table
                ref="multipleTable_hasSelect"
                :data="hasSelectTableData"
                border
                style="width: 100%">
                <el-table-column
                  prop="name"
                  label="标段名称"
                  align="left">
                </el-table-column>
                <el-table-column
                  prop="planAmount"
                  label="合同估价(万元)"
                  align="left">
                </el-table-column>
              </el-table>
            </el-form-item>
            <el-form-item label="公告名称:" prop="noticeName">
              <span>{{lookNoticeForm.noticeName}}</span>
            </el-form-item>
            <el-form-item label="公告类型:" prop="noticeType">
              <span>{{lookNoticeForm.noticeType | filterNoticeType}}</span>
            </el-form-item>
            <el-form-item label="报名起止时间:" prop="noticeTime">
              <span>{{lookNoticeForm.noticeTime}}</span>
            </el-form-item>
            <el-form-item label="资格预审时间:" prop="prequalificationTime" v-if="!prequalificationFlag">
              <span>{{lookNoticeForm.prequalificationTime | filterDate}}</span>
            </el-form-item>
            <el-form-item label="开标时间:" prop="bidOpenTime" v-if="prequalificationFlag">
              <span>{{lookNoticeForm.bidOpenTime | filterDate}}</span>
            </el-form-item>
            <div class="ueditor-box">
              <el-form-item label="公告内容:" prop="noticeContent">
                <!--<span v-html="lookNoticeForm.noticeContent">{{lookNoticeForm.noticeContent}}</span>-->
                <ueditor ref="ueditor_detail" :content="fileContent" class="ueditor"></ueditor>
              </el-form-item>
            </div>
            <el-form-item label="发布媒介:" prop="noticeMedia">
              <span>{{lookNoticeForm.noticeMedia}}</span>
            </el-form-item>
            <el-form-item label="附件:" prop="fileInformation">
              <span>
                <el-button
                  type="text"
                  @click="downloadFile(lookNoticeForm.fileInformation.fileName,lookNoticeForm.fileInformation.relativePath)">
                  <span v-if="lookNoticeForm.fileInformation">{{lookNoticeForm.fileInformation.fileName}}</span>
                </el-button>
              </span>
            </el-form-item>
            <el-form-item label="审批记录:" prop="approveRecordList" class="el-form-last" v-if="!examShow">
              <div class="app-record" style="height: 300px">
                <el-steps
                  direction="vertical"
                  :active="1"
                  :space="100">
                  <el-step
                    v-for="(item,index) in lookNoticeForm.approveRecordList"
                    :key="index"
                    :status='fliteredStatus(item.approveResult)'>
                    <span slot="title" class="step-span span-color span-first" v-if="item.approveResult === 0">{{item.applyName}}</span>
                    <span slot="title" class="step-span span-color span-second" v-if="item.approveResult === 0">{{item.applyOrganization}}</span>
                    <span slot="title" class="step-span span-color span-first" v-if="item.approveResult !== 0">{{item.approveName}}</span>
                    <span slot="title" class="step-span span-color span-second" v-if="item.approveResult !== 0">{{item.approveOrganization}}</span>
                    <span slot="title" class="step-span span-third">{{item.approveResult | filterApproveResult}} </span>
                    <span slot="title" class="step-span span-color span-last">{{item.approveTime | filterDate}}</span>
                    <div slot="title" class="apply-option" v-if="item.approveOpinion">
                      <span>审批意见:{{item.approveOpinion}}</span>
                    </div>
                  </el-step>
                </el-steps>
              </div>
              <!-- 按钮 -->
            </el-form-item>
          </el-form>
          <!-- 审批 -->
          <div class="review-pass" v-if="examShow && ($store.getters.authUser.enterpriseType === 2 ||
                    $store.getters.authUser.enterpriseType === 3)">
            <el-form :model="approveForm" :rules="approveFormRules" ref="approveForm" label-width="100px">
              <el-form-item label="审批状态:" prop="approveResult">
                <el-select v-model="approveForm.approveResult">
                  <el-option
                    v-for="item in stateList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="审批意见:" v-if="approveForm.approveResult !== 4" prop="approveOpinion" :rules="[
                  {required: true, message: '请输入审批意见', trigger: 'change'}
                ]">
                <el-input type="textarea" v-model="approveForm.approveOpinion" style="width: 280px;">{{approveForm.approveResult}}</el-input>
              </el-form-item>
              <el-form-item label="审批意见:" v-if="approveForm.approveResult === 4" prop="approveOpinion" :rules="[
                  {required: false, message: '请输入审批意见', trigger: 'change'}
                ]">
                <el-input type="textarea" v-model="approveForm.approveOpinion" style="width: 280px;">{{approveForm.approveResult}}</el-input>
              </el-form-item>
            </el-form>
          </div>
          <div class="review-pass" v-if="examShow && $store.getters.authUser.enterpriseType === 1">
            <el-form :model="approveForm" :rules="approveFormRules" ref="approveForm" label-width="100px">
              <el-form-item label="备案状态:" prop="approveResult">
                <el-select v-model="approveForm.approveResult">
                  <el-option
                    v-for="item in stateList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="备案意见:" v-if="approveForm.approveResult !== 4" prop="approveOpinion" :rules="[
                  {required: true, message: '请输入备案意见', trigger: 'change'}
                ]">
                <el-input type="textarea" v-model="approveForm.approveOpinion" style="width: 280px;">{{approveForm.approveResult}}</el-input>
              </el-form-item>
              <el-form-item label="备案意见:" v-if="approveForm.approveResult === 4" prop="approveOpinion" :rules="[
                  {required: false, message: '请输入备案意见', trigger: 'change'}
                ]">
                <el-input type="textarea" v-model="approveForm.approveOpinion" style="width: 280px;">{{approveForm.approveResult}}</el-input>
              </el-form-item>
            </el-form>
          </div>
          <!-- 按钮 -->
          <div class="review-sub-btn" v-if="examShow">
            <el-button @click="cancelOperation('approveForm')">取消</el-button>
            <el-button type="primary" @click="submitData('approveForm')">提交</el-button>
          </div>
        </div>
      </el-dialog>
      <!-- 查看弹窗end -->
    </div>
    <!-- 弹窗区域end -->
  </div>
</template>
<script>
// 引入编辑器
import ueditor from '@/components/ueditor/ueditor.vue'
// 引入日历
import calendar from '@/components/calendar/calendar.vue'
import {tenderproject, notice, section} from 'api/index'
import {commonJs, downloadFile} from 'common/js/common'
import {formatDate} from 'common/js/date.js'
export default {
  data () {
    return {
      btnLoading: false, // 提交加载动画
      calendarNotice: {
        display: '',
        show: false,
        zero: true,
        value: [], // 默认日期
        lunar: true, // 显示农历
        events: {},
        selectMonth: (month, year) => {
          // console.log(`month:${month},year:${year}`)
          this.selectMonthInfo = year + '-' + month
          this.getOptionalNoticeList()
        },
        select: (value) => {
          this.calendarNotice.show = false
          this.calendarNotice.value = value
          this.calendarNotice.display = value.join('-')
          this.validTime()
        }
      },
      selectMonthInfo: '',
      titleName: '添加公告',
      dropdownValue: '变更',
      tableData: [],
      dialogVisible: false,
      ruleForm: {
        noticeName: '', // 公告名称
        noticeType: 1, // 公告类型
        noticeMedia: '山西招投标网（www.sxbid.com.cn）' // 发布媒介
      },
      lookNoticeForm: {}, // 查看公告-表单
      approveForm: {},
      rules: {
        noticeContent: [
          { required: true, message: '请输入公告内容', trigger: 'blur' }
        ],
        noticeTime: [
          {required: true, message: '请选择报名起止时间', trigger: 'change'}
        ],
        prequalificationTime: [
          { required: true, message: '请选择资格预审时间', trigger: 'change' }
        ],
        bidOpenTime: [
          { required: true, message: '请选择开标时间', trigger: 'change' }
        ],
        noticeMedia: [
          { required: true, message: '请输入发布媒介', trigger: 'blur' },
          {min: 1, max: 80, message: '请输入1~200个字符', trigger: ['blur', 'change']}
        ]
      },
      approveFormRules: {
        approveResult: [
          { required: true, message: '请选择审批状态', trigger: 'change' }]
      },
      phaseSelectTableData: [], // 标段选择-表格数据
      fileList: [], // 上传附件列表
      lookDialogVisible: false, // 查看弹窗
      currentPage: 1,
      registrationStartTime: null,
      registrationEndTime: null,
      upLoad: commonJs.fileUploadUrl,
      prequalificationFlag: false,
      hasSelectedSection: [], // 已选择的标段信息
      multipleSelection: [],
      currentTenderProjectInfo: {
        noticeList: [],
        sectionList: [],
        selectApply: {
          enterpriseId: ''
        }
      },
      fileContent: '', // 编辑器内容
      hasSelectTableData: [], // 该公告已选的标段信息（查看界面）
      noticeName: { // 公告名称的构建
        projectName: '', // 招标项目名称
        prequalificationTimes: '', // '招标'或者是'资格预审'次数
        index1: '次',
        prequalification: '', // '招标'或者是'资格预审'
        prefix: '(',
        sectionNameList: [], // 所选择的标段集合
        suffix: ')',
        noticeTimes: '', // 公告次数
        index2: '次',
        noticeType: '', // 公告类型（1.正常公告 2.变更公告 3.撤销公告 4.延期公告 5.再次公告 6.控制价 7.补充公告 8.其他公告）
        end: '公告'
      },
      examShow: false, // 审批显隐
      isChangeSection: true,
      stateList: [
        {
          label: '通过',
          value: 4
        },
        {
          label: '退回',
          value: 5
        }
      ],
      noticeContent: '', // 弹窗公告内容
      operationFlag: false // 为true时不清空表单数据
    }
  },
  props: ['callbackdata', 'operationCheckFlag', 'dialogCheckVisible'],
  computed: {
    getCurrenTenderProjectName () {
      return this.currentTenderProjectInfo
    }
  },
  watch: {
    getCurrenTenderProjectName () {
      this.getProjectName(this.currentTenderProjectInfo)
    }
  },
  created () {
    if (this.dialogCheckVisible === true) {
      this.lookDialogVisible = this.dialogCheckVisible
      this.examShow = this.dialogCheckVisible
      this.getOneObj(this.$route.query.noticeCode, 'exam')
    }
    this.getAllNoticeInfo()
    this.getTenderProjectInfo()
  },
  filters: {
    filterNoticeType (type) {
      if (type === 1) {
        return '首次公告'
      } else if (type === 2) {
        return '变更公告'
      } else if (type === 3) {
        return '撤销公告'
      } else if (type === 4) {
        return '延期公告'
      } else if (type === 5) {
        return '再次公告'
      } else if (type === 6) {
        return '控制价'
      } else if (type === 7) {
        return '补充公告'
      } else if (type === 8) {
        return '其他公告'
      }
    },
    filterApproveResult (row) {
      let result = row
      if (result === 4) {
        return '通过'
      } else if (result === 5) {
        return '退回'
      } else if (result === 0) {
        return '发起申请'
      }
    },
    // 格式化申请时间
    filterDate (approveTime) {
      if (approveTime) {
        let date = new Date(approveTime)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return null
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
    validTime () {
      let isPrequalification = this.currentTenderProjectInfo.isPrequalification
      if (isPrequalification === 0) {
        if (this.calendarNotice.display) {
          this.$set(this.ruleForm, 'bidOpenTime', 'bidOpenTime')
        } else {
          this.$set(this.ruleForm, 'bidOpenTime', '')
        }
        if (this.ruleForm.bidOpenTime) {
          this.$refs['bidOpenTime'].clearValidate()
        }
      } else if (isPrequalification === 1) {
        if (this.calendarNotice.display) {
          this.$set(this.ruleForm, 'prequalificationTime', 'prequalificationTime')
        } else {
          this.$set(this.ruleForm, 'prequalificationTime', '')
        }
        if (this.ruleForm.prequalificationTime) {
          this.$refs['prequalificationTime'].clearValidate()
        }
      }
    },
    /**
     * 获取该集团下所有提交了的资格预审文件和招标文件的开标时间
     */
    getOptionalNoticeList () {
      if (this.selectMonthInfo !== '' && this.selectMonthInfo !== null) {
        notice.selectBidOpenTimeByCode(this.selectMonthInfo).then((res) => {
          let bidOpenTimeList = []
          res.data.bidOpenTimeList.forEach(item => {
            let date = new Date(item)
            bidOpenTimeList.push(formatDate(date, 'yyyy-MM-dd'))
          })
          let tempObj = {}
          if (bidOpenTimeList.length > 0) {
            for (let i = 0; i < bidOpenTimeList.length; i++) {
              let tempStr = ''
              bidOpenTimeList[i].split('-').forEach((item, index) => {
                if (index === 0) {
                  tempStr += item
                } else {
                  if (item < 10) {
                    tempStr += item.split('0')[1]
                  } else {
                    tempStr += item
                  }
                }
                if (index !== 2) {
                  tempStr += '-'
                }
              })
              let mT = `${tempStr}`
              tempObj[mT] = ['a']
            }
          }
          this.$set(this.calendarNotice, 'events', tempObj)
        })
      }
    },
    openByDrop (e) {
      this.calendarNotice.show = true
      this.calendarNotice.left = e.target.offsetLeft + 19
      this.calendarNotice.top = e.target.offsetTop + 70
      e.stopPropagation()
      window.setTimeout(() => {
        document.addEventListener('click', (e) => {
          this.calendarNotice.show = false
          document.removeEventListener('click', () => {}, false)
        }, false)
      }, 1000)
    },
    // 显示当前日期-默认日期
    getTodayDate () {
      let date = new Date()
      let Yo = date.getFullYear()
      let Mo = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1)
      let Do = date.getDate()
      this.calendarNotice.value.push(Yo)
      this.calendarNotice.value.push(Mo)
      this.calendarNotice.value.push(Do)
      this.selectMonthInfo = formatDate(date, 'yyyy-MM')
      // console.log(this.calendar1.value)
    },
    /** 提交审批结果 */
    submitData (formName) {
      this.$refs[formName].validate((vaild) => {
        if (vaild) {
          // 插入审批表信息
          this.approveForm.relatedCode = this.lookNoticeForm.code
          this.approveForm.applyName = this.lookNoticeForm.lastUpdaterName
          this.approveForm.applyOrganization = this.lookNoticeForm.enterpriseName
          this.approveForm.approveName = this.$store.getters.authUser.userName
          this.approveForm.approveOrganization = this.$store.getters.authUser.enterpriseName
          notice.saveApprovalRecords(this.approveForm).then(() => {
            this.lookDialogVisible = false
            this.examShow = false
            this.approveForm.approveResult = ''
            this.cancelOperation(formName)
            this.getAllNoticeInfo()
          })
        } else {
          return false
        }
      })
    },
    cancelOperation (formName) {
      this.lookDialogVisible = false
      this.$refs[formName].resetFields()
    },
    /** 动态获取公告中所包含的标段信息 */
    getNoticeSectionInfo (hasSelectedSection) {
      this.noticeName.sectionNameList = []
      hasSelectedSection.forEach(item => {
        this.noticeName.sectionNameList.push(item.name)
      })
      this.getProjectName(this.currentTenderProjectInfo)
      // 如果为首次公告，则标段信息展示的是目前没有在任何公告中的标段信息
      // 其他类型的公告中展示的标段信息是当前选中公告中已选择的标段信息
      if (this.titleName === '添加公告') {
        let noticeName = this.spliceNoticeName()
        this.ruleForm.noticeName = noticeName
        section.getUnusedSections(this.callbackdata).then((res) => {
          this.phaseSelectTableData = res.data.unusedSectionList
        })
      } else {
        let noticeName = this.spliceNoticeName()
        this.ruleForm.noticeName = noticeName
      }
    },
    getProjectName (currentTenderProjectInfo) {
      // 获取选聘备案申请表名称
      // 获取是否为资格预审
      let projectName = ''
      if (currentTenderProjectInfo.isPrequalification === 0) {
        projectName = currentTenderProjectInfo.tenderProjectName
        this.noticeName.projectName = projectName
        this.prequalificationFlag = true
      } else {
        projectName = currentTenderProjectInfo.tenderProjectName
        this.noticeName.projectName = projectName
      }
      this.noticeName.prequalification = currentTenderProjectInfo.isPrequalification === 0 ? '招标' : '资格预审'
    },
    /** 拼接公告名称 */
    spliceNoticeName () {
      let sectionNameList = ''
      let prequalificationTimes = ''
      let noticeTimes = ''
      let noticeType = ''
      if (this.noticeName.sectionNameList && this.noticeName.sectionNameList.length > 0) {
        // 全选则不展示标段信息
        if (this.currentTenderProjectInfo.sectionList.length !== this.noticeName.sectionNameList.length) {
          sectionNameList += this.noticeName.prefix
          for (let i = 0; i < this.noticeName.sectionNameList.length; i++) {
            if (i === this.noticeName.sectionNameList.length - 1) {
              sectionNameList += this.noticeName.sectionNameList[i]
              sectionNameList += this.noticeName.suffix
            } else {
              sectionNameList += this.noticeName.sectionNameList[i]
              sectionNameList += ','
            }
          }
        }
      }
      // 再次公告时，要确定是几次变更 --- bidCount（招标次数）
      if (this.noticeName.prequalificationTimes && this.ruleForm.noticeType === 5) {
        prequalificationTimes = this.noticeName.prequalificationTimes + this.noticeName.index1
      }
      // 变更、撤销、延期、补充时要确定是第几次
      if (this.noticeName.noticeTimes && this.ruleForm.noticeType !== 5) {
        noticeTimes = this.noticeName.noticeTimes + this.noticeName.index2
      }
      // 如果是首次公告和再次公告则不需要在名字的最后加上“首次”和“再次”的字段，仅仅只是需要在前面加上再次的次数
      if (this.ruleForm.noticeType === 1) {
        noticeType = ''
      } else if (this.ruleForm.noticeType === 5) {
        noticeType = ''
      } else {
        noticeType = this.noticeName.noticeType
      }
      let noticeName = this.noticeName.projectName + prequalificationTimes + this.noticeName.prequalification + sectionNameList + noticeTimes + noticeType + this.noticeName.end
      return noticeName
    },
    /** 获取该招标项目下所有的公告信息 */
    getAllNoticeInfo () {
      this.tableData = []
      notice.getList({
        pageNo: 1,
        pageSize: 0,
        tenderProjectCode: this.callbackdata
      }).then((res) => {
        // 如果当前用户是对应的项目单位或者是招标办，则只展示对应阶段的公告信息
        if (this.$store.getters.authUser.enterpriseType === 1) {
          res.data.noticesList.list.forEach(notice => {
            if (notice.noticeStatus === 3 || notice.noticeStatus === 4) {
              this.tableData.push(notice)
            }
          })
        } else if (this.$store.getters.authUser.enterpriseType === 2 || this.$store.getters.authUser.enterpriseType === 3) {
          res.data.noticesList.list.forEach(notice => {
            if (notice.noticeStatus === 2 || notice.noticeStatus === 4) {
              this.tableData.push(notice)
            }
          })
        } else {
          this.tableData = res.data.noticesList.list
        }
      })
    },
    /** 获取选中项目的所有标段 */
    getAllSectionInfo () {
      section.getList({
        pageNo: 1,
        pageSize: 0,
        tenderProjectCode: this.callbackdata
      }).then((res) => {
        this.currentTenderProjectInfo.sectionList = res.data.sectionList.list
      })
      // 该招标项目下若还有公告，则不能将已在公告中的标段展示在添加公告页面中
    },
    /** 获取需要的标段信息 */
    getChoosedSectionInfo () {
      if (this.tableData.length > 0) {
        section.getUnusedSections(this.callbackdata).then((res) => {
          this.phaseSelectTableData = res.data.unusedSectionList
        })
      } else {
        section.getAllByTenderCode(this.callbackdata).then((res) => {
          this.phaseSelectTableData = res.data.sectionList
        })
      }
    },
    /** 获取选中项目的详细信息 */
    getTenderProjectInfo () {
      this.phaseSelectTableData = []
      tenderproject.getOneByCode(this.callbackdata).then((res) => {
        this.currentTenderProjectInfo = res.data.tenderProject
        this.getAllSectionInfo()
      })
    },
    /** 保存/提交公告信息 */
    saveOrSubmitNotice (formName, type) {
      if (this.hasSelectedSection.length < 1) {
        this.$message.error('选择标段信息！')
        return false
      }
      if (Object.is(type, 'save')) {
        this.ruleForm.noticeStatus = 1 // 公告状态（1.未提交 2.项目单位审批中 3.招标办审批中 4.通过 5.退回）
      } else if (Object.is(type, 'submit')) {
        this.ruleForm.noticeStatus = 2
      }
      if (this.registrationStartTime && this.registrationStartTime) {
        this.ruleForm.registrationStartTime = this.registrationStartTime
        this.ruleForm.registrationEndTime = this.registrationEndTime
      }
      this.ruleForm.noticeContent = this.$refs.ueditor.getContent()
      this.ruleForm.sectionList = this.hasSelectedSection
      this.ruleForm.tenderProjectCode = this.currentTenderProjectInfo.code
      if (this.currentTenderProjectInfo.isPrequalification === 0) {
        this.ruleForm.bidOpenTime = this.calendarNotice.display
      } else {
        this.ruleForm.prequalificationTime = this.calendarNotice.display
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 加载动画
          this.btnLoading = true
          notice.save(this.ruleForm).then(() => {
            this.dialogVisible = false
            // 关闭动画
            this.btnLoading = false
            this.cancel(formName)
            this.getAllNoticeInfo()
            // 证明该招标项目下已有公告的存在，则向父主件传值，通知招标文件或者资格预审文件按钮高亮
            this.$emit('checkNoticeFlag', false)
          })
        } else {
          return false
        }
      })
    },
    handleClose (done) {
      if (!this.operationFlag) {
        this.ruleForm = {}
        this.ruleForm.noticeType = 1 // 公告类型
        this.ruleForm.noticeMedia = '山西招投标网（www.sxbid.com.cn）' // 发布媒介
        if (this.phaseSelectTableData.length > 0) {
          this.$refs.multipleTable.clearSelection() // 清空上次的标段选择信息
        }
        this.phaseSelectTableData = []
        this.hasSelectedSection = []
        this.fileList = []
        this.fileContent = ''
        if (this.$refs.ueditor) {
          this.$refs.ueditor.setContent(this.fileContent = '')
        }
        this.noticeName = {
          projectName: '', // 招标项目名称
          prequalificationTimes: '', // '招标'或者是'资格预审'次数
          index1: '次',
          prequalification: '', // '招标'或者是'资格预审'
          prefix: '(',
          sectionNameList: [], // 所选择的标段集合
          suffix: ')',
          noticeTimes: '', // 公告次数
          index2: '次',
          noticeType: '', // 公告类型（1.正常公告 2.变更公告 3.撤销公告 4.延期公告 5.再次公告 6.控制价 7.补充公告 8.其他公告）
          end: '公告'
        }
      }
      done()
    },
    cancel (formName) {
      this.dialogVisible = false
      this.$refs[formName].resetFields()
      if (this.phaseSelectTableData.length > 0) {
        this.$refs.multipleTable.clearSelection() // 清空上次的标段选择信息
      }
      this.phaseSelectTableData = []
      this.hasSelectedSection = []
      this.fileList = []
      this.noticeName = {
        projectName: '', // 招标项目名称
        prequalificationTimes: '', // '招标'或者是'资格预审'次数
        index1: '次',
        prequalification: '', // '招标'或者是'资格预审'
        prefix: '(',
        sectionNameList: [], // 所选择的标段集合
        suffix: ')',
        noticeTimes: '', // 公告次数
        index2: '次',
        noticeType: '', // 公告类型（1.正常公告 2.变更公告 3.撤销公告 4.延期公告 5.再次公告 6.控制价 7.补充公告 8.其他公告）
        end: '公告'
      }
    },
    filterSubDate (row, column) {
      if (!row.createDate) {
        return ''
      } else {
        let date = new Date(row.createDate)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      }
    },
    filterSubmitTime (value) {
      if (value) {
        this.registrationStartTime = value[0]
        this.registrationEndTime = value[1]
      } else {
        this.registrationStartTime = null
        this.registrationEndTime = null
      }
    },
    filterNoticeStatus (row) {
      if (row.noticeStatus === 1) {
        return '未提交'
      } else if (row.noticeStatus === 2) {
        return '项目单位审批中'
      } else if (row.noticeStatus === 3) {
        return '招标办备案中'
      } else if (row.noticeStatus === 4) {
        return '通过'
      } else if (row.noticeStatus === 5) {
        return '退回'
      }
    },
    handleRemove (file, fileList) {
      //  .log(file, fileList)
    },
    handlePreview (file) {
      // console.log(file)
    },
    handleExceed (files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    beforeRemove (file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    uploadSuccess (response, file, fileList) {
      this.ruleForm.fileInformation = response.fileInformation
    },
    /** 文件下载 */
    downloadFile,
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 添加公告 */
    addNotice () {
      // this.getOptionalNoticeList()
      this.selectMonthInfo = formatDate(new Date(), 'yyyy-MM') // 设置当前时间用于查询当前月的标志信息
      this.calendarNotice.display = ''
      this.titleName = '添加公告'
      this.fileList = []
      this.ruleForm = {}
      this.ruleForm.noticeType = 1 // 公告类型
      this.ruleForm.noticeMedia = '山西招投标网（www.sxbid.com.cn）' // 发布媒介
      this.dialogVisible = true
      this.isChangeSection = false
      this.noticeName = {
        projectName: '',
        prequalificationTimes: '',
        index1: '次',
        prequalification: '',
        prefix: '(',
        sectionNameList: [],
        suffix: ')',
        noticeTimes: '',
        index2: '次',
        noticeType: '',
        end: '公告'
      }
      if (this.phaseSelectTableData.length > 0) {
        this.$refs.multipleTable.clearSelection() // 清空上次的标段选择信息
      }
      this.hasSelectedSection = []
      this.fileContent = ''
      if (this.$refs.ueditor) {
        this.$refs.ueditor.setContent(this.fileContent = '')
      }
      // 设置表单上默认的公告名称
      let noticeName = ''
      if (this.currentTenderProjectInfo.isPrequalification === 0) {
        noticeName = this.currentTenderProjectInfo.tenderProjectName + '招标公告'
        this.ruleForm.noticeName = noticeName
        this.prequalificationFlag = true
      } else {
        noticeName = this.currentTenderProjectInfo.tenderProjectName + '资格预审公告'
        this.ruleForm.noticeName = noticeName
      }
      this.ruleForm.noticeType = 1
      this.getChoosedSectionInfo()
    },
    /** Dropdown下拉菜单操作 - 点击当前按钮所触发的方法 */
    handleClick (rowCode) {
      notice.getByCode(rowCode).then((res) => {
        if (res.data.notice.sectionList.length <= 0) {
          this.$confirm('该公告已没有可选标段，不可进行再次操作', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {}).catch(() => {
            return false
          })
        } else {
          let nowData = res.data.notice
          this.fileList = []
          this.operationFlag = true
          // 打开弹窗
          this.dialogVisible = true
          // 在编辑操作时调用公告名称拼接的方法
          this.isChangeSection = false
          // 公告名称拼接数据
          // 1.设置标题名称
          this.titleName = this.dropdownValue + '公告'
          // 2.设置公告名称中的公告类型
          this.noticeName.noticeType = this.dropdownValue
          // 3.设置除首次和再次公告外的公告次数
          let noticeType = this.checkNoticeType(this.titleName)
          let noticeTimes = this.getNoticeTimes(noticeType, nowData)
          this.noticeName.noticeTimes = noticeTimes !== 1 ? this.numberToChinese(noticeTimes) : ''
          // 4.设置再次公告中的再次的次数
          this.noticeName.prequalificationTimes = this.numberToChinese(nowData.bidCount + 1)
          // 设置表单内容
          this.ruleForm = {}
          this.ruleForm = nowData
          let noticeTime = []
          noticeTime[0] = this.ruleForm.registrationStartTime
          noticeTime[1] = this.ruleForm.registrationEndTime
          this.ruleForm.noticeTime = noticeTime
          // 设置编辑器的值
          let ueditorVal = nowData.noticeContent
          // if (this.$refs.ueditor) {
          //   this.fileContent = this.$refs.ueditor.setContent(ueditorVal)
          // }
          if (this.phaseSelectTableData.length > 0) {
            this.$refs.multipleTable.clearSelection() // 清空上次的标段选择信息
          }
          this.phaseSelectTableData = nowData.sectionList // 展示父公告中的标段信息
          if (nowData.fileInformation) {
            // 附件信息
            let obj = {
              name: nowData.fileInformation.fileName,
              response: {
                fileInformation: nowData.fileInformation
              }
            }
            this.fileList.push(obj)
          }
          this.ruleForm.noticeType = noticeType
          // 完成名字的拼接，并将公告名称赋值给表单
          let noticeName = this.spliceNoticeName()
          this.ruleForm.noticeName = noticeName
          // 数据备份
          this.ruleForm.objectId = null
          // 设置首次公告code和关联公告code
          this.ruleForm.firstNoticeCode = nowData.firstNoticeCode !== null ? nowData.firstNoticeCode : nowData.code
          this.ruleForm.relateNoticeCode = nowData.code
          // 清空表中原有操作次数
          this.ruleForm.changeCount = ''
          this.ruleForm.revocationCount = ''
          this.ruleForm.delayCount = ''
          this.ruleForm.bidCount = ''
          this.ruleForm.replenishCount = ''
          // 保存操作次数
          if (this.ruleForm.noticeType === 2) { // 变更公告
            this.ruleForm.changeCount = (this.noticeName.noticeTimes === '') ? 1 : this.ChineseToNumber(this.noticeName.noticeTimes)
          } else if (this.ruleForm.noticeType === 3) { // 撤销公告
            this.ruleForm.revocationCount = (this.noticeName.noticeTimes === '') ? 1 : this.ChineseToNumber(this.noticeName.noticeTimes)
          } else if (this.ruleForm.noticeType === 4) { // 延期公告
            this.ruleForm.delayCount = (this.noticeName.noticeTimes === '') ? 1 : this.ChineseToNumber(this.noticeName.noticeTimes)
          } else if (this.ruleForm.noticeType === 5) { // 再次公告
            this.ruleForm.bidCount = (this.noticeName.prequalificationTimes === '') ? 1 : this.ChineseToNumber(this.noticeName.prequalificationTimes)
          } else if (this.ruleForm.noticeType === 7) { // 补充公告
            this.ruleForm.replenishCount = (this.noticeName.noticeTimes === '') ? 1 : this.ChineseToNumber(this.noticeName.noticeTimes)
          }
          return ueditorVal
        }
      }).then(ueResult => {
        if (this.$refs.ueditor) {
          this.fileContent = this.$refs.ueditor.setContent(ueResult)
        }
      })
    },
    /** 数字转换为汉字 */
    numberToChinese (section) {
      let chnNumChar = ['零', '一', '二', '三', '四', '五', '六', '七', '八', '九']
      // let chnUnitSection = ['', '万', '亿', '万亿', '亿亿']
      let chnUnitChar = ['', '十', '百', '千']
      let strIns = ''
      let chnStr = ''
      let unitPos = 0
      let zero = true
      while (section > 0) {
        let v = section % 10
        if (v === 0) {
          if (!zero) {
            zero = true
            chnStr = chnNumChar[v] + chnStr
          }
        } else {
          zero = false
          strIns = chnNumChar[v]
          strIns += chnUnitChar[unitPos]
          chnStr = strIns + chnStr
        }
        unitPos++
        section = Math.floor(section / 10)
      }
      return chnStr
    },
    /** 汉字转化为数字 */
    ChineseToNumber (chnStr) {
      let chnNumChar = {
        零: 0,
        一: 1,
        二: 2,
        三: 3,
        四: 4,
        五: 5,
        六: 6,
        七: 7,
        八: 8,
        九: 9
      }
      let chnNameValue = {
        十: {value: 10, secUnit: false},
        百: {value: 100, secUnit: false},
        千: {value: 1000, secUnit: false},
        万: {value: 10000, secUnit: true},
        亿: {value: 100000000, secUnit: true}
      }
      let rtn = 0
      let section = 0
      let number = 0
      let secUnit = false
      let str = chnStr.split('')

      for (let i = 0; i < str.length; i++) {
        let num = chnNumChar[str[i]]
        if (typeof num !== 'undefined') {
          number = num
          if (i === str.length - 1) {
            section += number
          }
        } else {
          let unit = chnNameValue[str[i]].value
          secUnit = chnNameValue[str[i]].secUnit
          if (secUnit) {
            section = (section + number) * unit
            rtn += section
            section = 0
          } else {
            section += (number * unit)
          }
          number = 0
        }
      }
      return rtn + section
    },
    /** Dropdown下拉菜单操作 - 回调返回 */
    handleCommand (command) {
      this.operationFlag = true
      this.dropdownValue = command.name
      this.handleClick(command.rowCode)
    },
    /** 获取公告操作后的公告类型 */
    getNoticeTimes (command, row) {
      if (command === 2) { // 变更
        return row.changeCount + 1
      } else if (command === 3) { // 撤销
        return row.revocationCount + 1
      } else if (command === 4) { // 延期
        return row.delayCount + 1
      } else if (command === 5) { // 再次
        return row.bidCount + 1
      } else if (command === 7) { // 补充
        return row.replenishCount + 1
      } else {
        return ''
      }
    },
    /** 判断公告类型 */
    checkNoticeType (type) {
      if (type === '首次公告') {
        return 1
      } else if (type === '变更公告') {
        return 2
      } else if (type === '撤销公告') {
        return 3
      } else if (type === '延期公告') {
        return 4
      } else if (type === '再次公告') {
        return 5
      } else if (type === '控制价') {
        return 6
      } else if (type === '补充公告') {
        return 7
      } else {
        return 8
      }
    },
    filterNoticeType (type) {
      if (type === 1) {
        return '首次公告'
      } else if (type === 2) {
        return '变更公告'
      } else if (type === 3) {
        return '撤销公告'
      } else if (type === 4) {
        return '延期公告'
      } else if (type === 5) {
        return '再次公告'
      } else if (type === 6) {
        return '控制价'
      } else if (type === 7) {
        return '补充公告'
      } else if (type === 8) {
        return '其他公告'
      }
    },
    /** 点击操作按钮操作 */
    handleTableClick (row, type) {
      this.hasSelectTableData = []
      this.fileList = []
      if (Object.is(type, 'edit')) {
        this.operationFlag = true
        this.isChangeSection = false
        // 打开弹窗
        this.dialogVisible = true
        this.examShow = false
        this.titleName = this.filterNoticeType(row.noticeType)
        // 调用-获得一条数据
        this.getOneObj(row.code, 'edit')
        // 获取标段信息（首次公告：对应招标项目下的全部标段  其他公告：关联公告下的标段信息）
        this.getPhaseSelectTableData(row)
      } else if (Object.is(type, 'look')) {
        this.operationFlag = false
        this.lookDialogVisible = true
        this.examShow = false
        this.getOneObj(row.code, 'look')
      } else if (Object.is(type, 'del')) {
        this.deleteClick(row.objectId)
      } else if (Object.is(type, 'exam')) {
        this.lookDialogVisible = true
        this.examShow = true
        this.getOneObj(row.code, 'exam')
      } else {
        this.dialogVisible = true
      }
    },
    /** 获取标段信息（首次公告：对应招标项目下的全部标段  其他公告：关联公告下的标段信息） */
    getPhaseSelectTableData (row) {
      this.phaseSelectTableData = []
      let unusedSections = []
      let choosedSections = []
      if (row.noticeType === 1) {
        section.getUnusedSections(this.callbackdata).then((res) => {
          unusedSections = res.data.unusedSectionList
          section.getNotLockedSections(row.code).then((res) => {
            choosedSections = res.data.sectionList
            this.phaseSelectTableData = unusedSections.concat(choosedSections)
            this.$refs.multipleTable.clearSelection() // 清空上次的标段选择信息
            // 给已选择的标段设置值
            let sectionIndexList = this.getCheckedSectionIndexList(choosedSections)
            let checkedSectionList = []
            sectionIndexList.forEach(index => checkedSectionList.push(this.phaseSelectTableData[index]))
            this.toggleSelection(checkedSectionList)
          })
        })
      } else {
        section.getNotLockedSections(row.code).then((res) => {
          this.$refs.multipleTable.clearSelection() // 清空上次的标段选择信息
          this.phaseSelectTableData = res.data.sectionList
          this.toggleSelection(this.phaseSelectTableData)
        })
      }
    },
    /** 获得一个 */
    getOneObj (code, type) {
      notice.getByCode(code).then((res) => {
        if (Object.is(type, 'edit')) {
          this.ruleForm = {}
          this.ruleForm = res.data.notice
          let date = new Date(res.data.notice.bidOpenTime !== null ? res.data.notice.bidOpenTime : res.data.notice.prequalificationTime)
          this.calendarNotice.display = formatDate(date, 'yyyy-MM-dd')
          let noticeName = res.data.notice.noticeName
          this.ruleForm.noticeName = noticeName
          let noticeTime = []
          noticeTime[0] = this.ruleForm.registrationStartTime
          noticeTime[1] = this.ruleForm.registrationEndTime
          this.titleName = this.filterNoticeType(this.ruleForm.noticeType)
          this.ruleForm.noticeTime = noticeTime
          // 设置编辑器的值
          let ueditorVal = res.data.notice.noticeContent
          this.fileContent = this.$refs.ueditor.setContent(ueditorVal)
          // 设置名称拼接字段
          // 1.设置公告名称中的公告类型
          this.noticeName.noticeType = this.titleName.replace('公告', '')
          // 2.设置除首次和再次公告外的公告次数
          let noticeType = this.checkNoticeType(this.titleName)
          let noticeTimes = this.getNoticeTimes(noticeType, res.data.notice) - 1
          this.noticeName.noticeTimes = noticeTimes !== 1 ? this.numberToChinese(noticeTimes) : ''
          // 3.设置再次公告中的再次的次数
          this.noticeName.prequalificationTimes = this.numberToChinese(res.data.notice.bidCount)
        } else if (Object.is(type, 'look') || Object.is(type, 'exam')) {
          // 如果该公告下未上锁标段为空，则展示已上锁的标段信息
          this.hasSelectTableData = res.data.notice.sectionList.concat(res.data.notice.lockedSectionList)
          this.lookNoticeForm = res.data.notice
          let noticeTime = ''
          noticeTime += this.lookNoticeForm.registrationStartTime
          noticeTime += '  -  '
          noticeTime += this.lookNoticeForm.registrationEndTime
          this.lookNoticeForm.noticeTime = noticeTime
          let ueditorVal = res.data.notice.noticeContent
          this.fileContent = this.$refs.ueditor_detail.setContent(ueditorVal)
        }
        // 附件信息
        if (res.data.notice.fileInformation) {
          let obj = {
            name: res.data.notice.fileInformation.fileName,
            response: {
              fileInformation: res.data.notice.fileInformation
            }
          }
          this.fileList.push(obj)
        }
      })
    },
    /** 去除公告中的已上锁标段的信息 */
    deleteLockedSectionsByNotice (lockedSectionList) {
      if (lockedSectionList.length > 0) {
        let indexs = this.getCheckedSectionIndexList(lockedSectionList)
        indexs.forEach(index => this.phaseSelectTableData.splice(index, 1))
      }
    },
    /** 得到公告中所包含标段的下标索引 */
    getCheckedSectionIndexList (sectionList) {
      let results = []
      for (let i = 0; i < this.phaseSelectTableData.length; i++) {
        for (let j = 0; j < sectionList.length; j++) {
          if (this.phaseSelectTableData[i].objectId === sectionList[j].objectId) {
            results.push(i)
          }
        }
      }
      return results
    },
    /** 弹窗-标段的默认选择 */
    toggleSelection (rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row, true)
        })
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    },
    // 获取row的key值
    getRowKeys (row) {
      return row.objectId
    },
    /** 弹窗-标段选择 */
    handleSelectionChange (rows) {
      this.hasSelectedSection = []
      // this.multipleSelection = []
      if (rows) {
        rows.forEach(row => {
          if (row) {
            // this.multipleSelection.push(row.objectId)
            this.hasSelectedSection.push(row)
          }
        })
      }
      if (!this.isChangeSection) {
        this.getNoticeSectionInfo(rows)
      }
      // this.isChangeSection = true
    },
    /** 上传 */
    handleSuccess () {},
    /** 删除上传附件 */
    clickDel () {
      this.ruleForm.fileInformation = {}
    },
    /** 删除弹窗 */
    deleteClick (id) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        notice.delete(id).then(() => {
          this.getAllNoticeInfo()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    /** 审批状态设置 */
    /** 设置审批状态颜色 */
    fliteredStatus (status) {
      if (status === 0) {
        return 'wait'
      } else if (status === 4) {
        return 'success'
      } else if (status === 5) {
        return 'error'
      } else if (status === 3) {
        return 'finish'
      }
    }
  },
  /** 实例创建完成 */
  mounted () {
    this.getTodayDate()
    this.getOptionalNoticeList()
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
#notice-page {
  .notice-box {
    .notice-tit {
      height: 50px;
      line-height: 50px;
      text-align:left;
      padding: 0 20px;
      background:#F7F6FB;
      span {
        font-weight: 800;
      }
    }
    .notice-addbtn {
      text-align: left;
      padding-top: 10px;
      padding-left: 20px;
      padding-bottom: 10px;
    }
    .notice-table {
      padding: 0 20px 20px;
      .el-collapse-item__header {
        padding-left: 20px;
        text-align: left;
      }
      .el-collapse-item__header.is-active {
        background: #eee;
      }
    }
  }
  /** 弹窗 */
  .notice-dialog {
    .el-icon-error .upload-file {
      font-size: 20px;
      color: red;
      cursor: pointer;
    }
    .el-form-item__content {
      text-align: left;
    }
    .el-dialog__footer {
      text-align: center;
    }
    .el-dialog__wrapper {
      z-index: 9;
    }
    .ueditor-box {
      .el-form-item__content {
        line-height: 0;
      }
    }
    .ueditor-wrap {
      padding: 0;
    }
    /** 审批记录 */
    .el-form-last {
      .el-form-item__content {
        line-height: 0;
      }
      .apply-option {
        span {
          font-size: 14px;
          color: #999;
        }
      }
    }
    .app-record {
      margin-top: 20px;
      padding-top: 20px;
      border-top: 1px solid #eeeeee;
      overflow: hidden;
      overflow-y: auto;
      .step-span {
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
        width: 100px;
      }
      .span-second {
        width: 180px;
      }
      .span-last {
        float: right;
      }
      .span-color {
        color: #999;
      }
    }
  }
}
</style>
