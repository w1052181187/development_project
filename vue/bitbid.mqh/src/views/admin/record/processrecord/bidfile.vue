<template>
  <!-- 招标文件 -->
  <div class="admin-layout" id="bidfile-page">
    <div class="bidfile-cont">
      <div class="bidfile-tit">
        <span>招标文件</span>
      </div>
      <div class="bidfile-form">
        <div class="btn-box"  v-if="dialogSubmit">
          <el-button @click="addBtnClick" type="warning" size="medium" icon="el-icon-plus" style="float: left;" :disabled="addBtnDisabled">添 加</el-button>
          <el-button @click="$router.go(-1)" type="info" size="medium" style="float: right;">取 消</el-button>
          <el-button @click="submitResult" :disabled="btnDisabled" type="success" size="medium" style="float: right;">提 交</el-button>
        </div>
        <el-collapse v-model="activeName" accordion>
          <el-collapse-item v-for="(tenderDoc,index) in tenderDocList" :key="index" :name=tenderDoc.objectId>
            <template slot="title">
              <span @click="getTenderDoc(tenderDoc.objectId, index)" style="display:block;width:100%;position:relative;z-index:1;">
                <span class="title-span" v-for="(section, index) in tenderDoc.sectionList" :key="index">{{section.name}}</span>
                <el-button type="text" @click.stop="deleteCilck(tenderDoc.code)" style="float: right;margin-right: 40px;" v-if="dialogSubmit">删除</el-button>
              </span>
            </template>
            <div class="form-box">
              <el-form :model="bidfileForm" :rules="bidfileRules" ref="bidfileForm" label-width="120px" class="demo-ruleForm">
                <el-form-item label="标段信息:" prop="bidPhaseInfo">
                  <el-table
                    border
                    :data="tableData"
                    :row-class-name="tableRowClassName"
                    style="width: 100%">
                    <el-table-column
                      type="index"
                      label="序号"
                      :index="indexMethod"
                      align="center"
                      width="55">
                    </el-table-column>
                    <el-table-column
                      label="标段名称"
                      prop="name"
                      align="center">
                    </el-table-column>
                    <el-table-column
                      prop="planAmount"
                      label="计划金额(万元)"
                      align="center">
                    </el-table-column>
                  </el-table>
                </el-form-item>
                <el-form-item label="招标文件:" prop="fileList" ref="fileList">
                  <el-upload
                    v-if="dialogSubmit"
                    class="upload-demo"
                    :action="upLoad"
                    :on-success="uploadSuccess"
                    :file-list="fileList"
                    :show-file-list="false">
                    <el-button size="small" type="primary" class="upload-btn" v-if="dialogSubmit">点击上传</el-button>
                  </el-upload>
                  <!-- 上传附件列表 -->
                  <div class="apply-upload">
                    <el-row>
                      <el-col :span="24">
                        <span class="file-list" v-for="(item, index) in fileList" :key="index">
                          <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                          <i class="el-icon-error upload-file" @click="clickDel(index)" v-if="dialogSubmit"></i>
                        </span>
                      </el-col>
                    </el-row>
                  </div>
                </el-form-item>
                <el-form-item label="发售起止时间:" prop="sellTime">
                  <el-date-picker
                    v-model="bidfileForm.sellTime"
                    type="datetimerange"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    v-if="dialogSubmit">
                  </el-date-picker>
                  <span v-else>{{bidfileForm.sellTime | filterSellTime}}</span>
                </el-form-item>
                <el-form-item label="答疑起止时间:" prop="clarifyTime">
                  <el-date-picker
                    v-model="bidfileForm.clarifyTime"
                    type="datetimerange"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    v-if="dialogSubmit">
                  </el-date-picker>
                  <span v-else>{{bidfileForm.clarifyTime | filterClarifyTime}}</span>
                </el-form-item>
                <el-form-item label="开标时间:" prop="bidOpenTime" ref="bidOpenTime">
                  <!--<el-date-picker
                    style="width:400px"
                    v-model="bidfileForm.bidOpenTime"
                    type="date"
                    placeholder="选择日期"
                    v-if="dialogSubmit">
                  </el-date-picker>-->
                  <div class="flex" v-if="dialogSubmit">
                    <div style="height:0;border:none;">
                      <input type="text" @click="openByDrop($event)" v-model="calendar.display" style="margin-top:0;" @blur="validTime">
                    </div>
                  </div>
                  <transition name="fade" v-if="dialogSubmit">
                    <div
                      class="calendar-dropdown"
                      :style="{'left':calendar.left+'px','top':calendar.top+'px'}"
                      v-if="calendar.show">
                      <calendar
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
                  <span v-else>{{bidfileForm.bidOpenTime | filterBidOpenTime}}</span>
                </el-form-item>
                <el-form-item label="开标地点:" prop="bidOpenPlace">
                  <el-input v-model="bidfileForm.bidOpenPlace" style="width:400px" clearable v-if="dialogSubmit"></el-input>
                  <span v-else>{{bidfileForm.bidOpenPlace}}</span>
                </el-form-item>
                <!--<el-form-item label="评标办法:" prop="evaluatingMethod">
                  <el-input v-model="bidfileForm.evaluatingMethod" style="width:400px" clearable v-if="dialogSubmit"></el-input>
                  <span v-else>{{bidfileForm.evaluatingMethod}}</span>
                </el-form-item>-->
                <el-form-item class="last-form-btn">
                  <el-button type="primary" @click="saveForm('bidfileForm')" v-if="dialogSubmit">保 存</el-button>
                  <el-button @click="$router.go(-1)" v-if="dialogSubmit">取消</el-button>
                </el-form-item>
              </el-form>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>

      <!--审批start-->
      <div class="exam-box" v-if="dialogApprove">
        <el-form :model="bidExamForm" :rules="bidExamRules" ref="bidExamForm" label-width="120px" class="demo-ruleForm">
          <el-form-item :label="($store.getters.authUser.enterpriseType === 1)?'备案结果:':'审批结果:'" prop="approveResult">
            <el-select v-model="bidExamForm.approveResult" placeholder="请选择" style="width: 400px" clearable>
              <el-option
                v-for="item in stateList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item :label="($store.getters.authUser.enterpriseType === 1)?'备案意见:':'审批意见:'"
                        prop="approveOpinion" v-if="bidExamForm.approveResult !== 1">
            <el-input
              style="width: 400px"
              v-model="bidExamForm.approveOpinion"
              type="textarea"
              :rows="4"
              placeholder="请输入内容">
            </el-input>
          </el-form-item>
          <el-form-item label="审批意见:" v-if="bidExamForm.approveResult === 1">
            <el-input
              style="width: 400px"
              v-model="bidExamForm.approveOpinion"
              type="textarea"
              :rows="4"
              placeholder="请输入内容">
            </el-input>
          </el-form-item>
          <el-form-item label="附件:" prop="fileApproveList" ref="fileApproveList">
            <el-upload
              class="upload-demo"
              :action="upLoad"
              :on-success="uploadApproveSuccess"
              :file-list="fileApproveList"
              :show-file-list="false">
              <el-button size="small" type="primary" class="upload-btn">点击上传</el-button>
            </el-upload>
            <!-- 上传附件列表 -->
            <div class="apply-upload">
              <el-row>
                <el-col :span="24">
                        <span class="file-list" v-for="(item, index) in fileApproveList" :key="index">
                          <el-button
                            type="text"
                            style="padding-right: 20px;width: 80%;"
                            @click="downloadFile(item.fileName,item.relativePath)">
                            <span
                              style="width:100%;display: inline-block;overflow: hidden;text-align: left;text-overflow: ellipsis; white-space: nowrap;">
                              {{item.fileName}}
                            </span>
                          </el-button>
                          <i class="el-icon-error upload-file" @click="clickApproveDel(index)"></i>
                        </span>
                </el-col>
              </el-row>
            </div>
          </el-form-item>
        </el-form>
        <div class="result-savebtn">
          <el-button type="primary" @click="saveApproveForm('bidExamForm')">确 定</el-button>
        </div>
      </div>
      <!--审批end-->
    </div>

    <!-- 审批记录start -->
    <div class="app-record">
      <div class="division-line">
      </div>
      <div class="bidfile-tit">
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
                <span slot="title" v-for="(file, index) in item.fileInformationList" :key="index">
                  <el-button type="text" style="padding-right: 20px;" @click="downloadFile(file.fileName,file.relativePath)">{{file.fileName}}</el-button>
                </span>
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

    <!--弹窗区域start-->
    <div class="dialog-box">
      <el-dialog
        title="招标文件"
        :visible.sync="addDialogVisible"
        @close="cancelClick"
        width="40%">
        <div class="change-query">
          <div class="form-box">
            <el-form :model="bidfileAddForm" :rules="bidfileAddRules" ref="bidfileAddForm" label-width="120px" class="demo-ruleForm">
              <el-form-item label="标段信息:" prop="bidPhaseInfo">
                <el-table
                  border
                  :data="tableAddData"
                  :row-class-name="tableRowClassName"
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
              <el-form-item label="招标文件:" prop="fileAddList" ref="fileAddList">
                <el-upload
                  class="upload-demo"
                  :action="upLoad"
                  :on-success="uploadAddSuccess"
                  :file-list="fileAddList"
                  :show-file-list="false">
                  <el-button size="small" type="primary" class="upload-btn">点击上传</el-button>
                </el-upload>
                <!-- 上传附件列表 -->
                <div class="apply-upload">
                  <el-row>
                    <el-col :span="24">
                        <span class="file-list" v-for="(item, index) in fileAddList" :key="index">
                          <el-button
                            type="text"
                            style="padding-right: 20px;width: 80%;"
                            @click="downloadFile(item.fileName,item.relativePath)">
                            <span
                              style="width:100%;display: inline-block;overflow: hidden;text-align: left;text-overflow: ellipsis; white-space: nowrap;">
                              {{item.fileName}}
                            </span>
                          </el-button>
                          <i class="el-icon-error upload-file" @click="clickAddDel(index)"></i>
                        </span>
                    </el-col>
                  </el-row>
                </div>
              </el-form-item>
              <el-form-item label="发售起止时间:" prop="sellTime">
                <el-date-picker
                  v-model="bidfileAddForm.sellTime"
                  type="datetimerange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="答疑起止时间:" prop="clarifyTime">
                <el-date-picker
                  v-model="bidfileAddForm.clarifyTime"
                  type="datetimerange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="开标时间:" prop="bidOpenTimeAdd" ref="bidOpenTimeAdd">
                <!--<el-date-picker
                  style="width:400px"
                  v-model="bidfileAddForm.bidOpenTime"
                  type="date"
                  placeholder="选择日期">
                </el-date-picker>-->
                <div class="flex">
                  <div style="height:0;border:none;">
                    <input type="text" @click="openByDropAdd($event)" v-model="calendarAdd.display" style="margin-top:0;" @blur="validAddTime">
                  </div>
                </div>
                <transition name="fade">
                  <div
                    class="calendar-dropdown"
                    :style="{'left':calendarAdd.left+'px','top':calendarAdd.top+'px'}"
                    v-if="calendarAdd.show">
                    <calendar
                      :zero="calendarAdd.zero"
                      :events="calendarAdd.events"
                      :lunar="calendarAdd.lunar"
                      :value="calendarAdd.value"
                      :begin="calendarAdd.begin"
                      :end="calendarAdd.end"
                      @selectMonth="calendarAdd.selectMonth"
                      @select="calendarAdd.select">
                    </calendar>
                  </div>
                </transition>
              </el-form-item>
              <el-form-item label="开标地点:" prop="bidOpenPlace">
                <el-input v-model="bidfileAddForm.bidOpenPlace" style="width:400px" clearable></el-input>
              </el-form-item>
              <!--<el-form-item label="评标办法:" prop="evaluatingMethod">
                <el-input v-model="bidfileAddForm.evaluatingMethod" style="width:400px" clearable></el-input>
              </el-form-item>-->
              <el-form-item class="last-form-btn">
                <el-button type="primary" @click="saveAddForm('bidfileAddForm')">保 存</el-button>
                <el-button @click="cancelClick">取消</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </el-dialog>
    </div>
    <!--弹窗结束end-->
  </div>
</template>
<script>
import {bidfile, openbid, section, notice, tenderproject} from 'api/index'
import {commonJs, downloadFile} from 'common/js/common'
import {formatDate} from 'common/js/date'
import calendar from '@/components/calendar/calendar.vue'
export default {
  data () {
    return {
      calendar: {
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
          this.calendar.show = false
          this.calendar.value = value
          this.calendar.display = value.join('-')
          this.validTime()
        }
      },
      selectMonthInfo: '',
      calendarAdd: {
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
          this.calendarAdd.show = false
          this.calendarAdd.value = value
          this.calendarAdd.display = value.join('-')
          this.validAddTime()
        }
      },
      btnDisabled: false, // 提交按钮禁用
      bidfileAddForm: {}, // 新增-表单数据
      bidfileAddRules: {
        fileAddList: [
          {required: true, message: '请上传招标文件', trigger: 'blur'}
        ],
        sellTime: [
          {required: true, message: '请选择发售起止时间', trigger: 'change'}
        ],
        clarifyTime: [
          {required: true, message: '请选择答疑起止时间', trigger: 'change'}
        ],
        bidOpenTimeAdd: [
          {required: true, message: '请选择开标时间', trigger: 'change'}
        ],
        bidOpenPlace: [
          {required: true, message: '请填写开标地点', trigger: 'blur'},
          { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }
        ],
        evaluatingMethod: [
          {required: true, message: '请填写评标办法', trigger: 'blur'},
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
        ]
      }, // 新增-表单规则
      fileAddList: [], // 新增上传列表
      fileApproveList: [], // 审批上传列表
      tableAddData: [], // 新增表格数据
      activeName: null, // 折叠面板默认选中项
      addDialogVisible: false, // 新增弹窗
      tenderDocList: [], // 折叠面板数据
      bidExamForm: {}, // 审批表单
      bidExamRules: {
        approveResult: [
          {required: true, message: '请选择结果', trigger: 'change'}
        ],
        approveOpinion: [
          {required: true, message: '请填写意见', trigger: 'blur'},
          { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }
        ]
      }, // 审批规则
      upLoad: commonJs.fileUploadUrl,
      tableData: [],
      dialogSubmit: false,
      dialogApprove: false,
      bidfileForm: {}, // 表单
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
      bidfileRules: {
        fileList: [
          {required: true, message: '请上传招标文件', trigger: 'blur'}
        ],
        sellTime: [
          {required: true, message: '请选择发售起止时间', trigger: 'change'}
        ],
        clarifyTime: [
          {required: true, message: '请选择答疑起止时间', trigger: 'change'}
        ],
        bidOpenTime: [
          {required: true, message: '请选择开标时间', trigger: 'change'}
        ],
        bidOpenPlace: [
          {required: true, message: '请填写开标地点', trigger: 'blur'},
          { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }
        ],
        evaluatingMethod: [
          {required: true, message: '请填写评标办法', trigger: 'blur'},
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
        ]
      }, // 规则
      currentPage: 1,
      approveRecordList: [], // 审批记录数据
      fileList: [], // 上传列表数据
      selectSectionData: [], // 添加招标文件时所选标段
      index: 0, // 展开的招标文件的index
      addBtnDisabled: false, // 当无可选的标段时，添加按钮置灰
      operate: 1, // 1.页面开标时间 2.弹框开标时间
      projectEnterpriseType: null
    }
  },
  props: {
    noticeCode: String,
    operationCheckFlag: Boolean,
    dialogCheckVisible: Boolean
  },
  computed: {},
  created () {
    this.dialogApprove = this.dialogCheckVisible
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
    filterBidOpenTime (val) {
      if (val) {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd')
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
      if (val === 0) {
        return '发起审批申请'
      } else if (val === 1) {
        return '通过'
      } else if (val === 2) {
        return '退回'
      } else if (val === 3) {
        return '终止'
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
    /** 设置审批状态颜色 */
    fliteredStatus (status) {
      if (status === 1) {
        return 'success'
      } else if (status === 2) {
        return 'error'
      } else if (status === 0) {
        return 'wait'
      } else if (status === 3) {
        return 'finish'
      }
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
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
          this.$set(this.calendar, 'events', resultMap)
        } else if (Object.is(this.operate, 2)) {
          this.$set(this.calendarAdd, 'events', resultMap)
        }
      }
    },
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
    openByDropAdd (e) {
      this.calendarAdd.show = true
      this.calendarAdd.left = e.target.offsetLeft + 19
      this.calendarAdd.top = e.target.offsetTop + 70
      e.stopPropagation()
      window.setTimeout(() => {
        document.addEventListener('click', (e) => {
          this.calendarAdd.show = false
          document.removeEventListener('click', () => {}, false)
        }, false)
      }, 1000)
    },
    /** 新增 */
    addBtnClick () {
      this.getSections()
      this.operate = 2
      this.selectMonthInfo = formatDate(new Date(), 'yyyy-MM')
      if (this.selectMonthInfo) {
        this.getOptionalNoticeList()
      }
      this.addDialogVisible = true
    },
    /** 新增-取消 */
    cancelClick () {
      this.bidfileAddForm = {}
      this.tableAddData = []
      this.fileAddList = []
      this.addDialogVisible = false
    },
    /** 新增-保存 */
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
        this.$message.error('请选择标段信息！')
        return false
      }
      this.save(val)
    },
    /** 保存 */
    saveForm (val) {
      this.update(val)
    },
    save (val) {
      this.validAddFile()
      this.$refs[val].validate((vaild) => {
        if (vaild) {
          this.bidfileAddForm.noticeCode = this.noticeCode
          this.bidfileAddForm.bidOpenTime = this.calendarAdd.display
          this.bidfileAddForm.actualBidOpenTime = this.bidfileAddForm.bidOpenTime
          this.bidfileAddForm.sellStartTime = this.bidfileAddForm.sellTime[0]
          this.bidfileAddForm.sellEndTime = this.bidfileAddForm.sellTime[1]
          this.bidfileAddForm.clarifyStartTime = this.bidfileAddForm.clarifyTime[0]
          this.bidfileAddForm.clarifyEndTime = this.bidfileAddForm.clarifyTime[1]
          this.bidfileAddForm.fileInformationList = this.fileAddList
          this.bidfileAddForm.sectionList = this.selectSectionData
          bidfile.save(this.bidfileAddForm).then(() => {
            this.getTenderDocList()
            this.cancelClick()
            this.getSections()
            this.getApproveRecodes()
          })
        } else {
          return false
        }
      })
    },
    update (val) {
      this.validFile()
      this.$refs[val][this.index].validate((vaild) => {
        if (vaild) {
          this.bidfileForm.noticeCode = this.noticeCode
          this.bidfileForm.bidOpenTime = this.calendar.display
          this.bidfileForm.sellStartTime = this.bidfileForm.sellTime[0]
          this.bidfileForm.sellEndTime = this.bidfileForm.sellTime[1]
          this.bidfileForm.clarifyStartTime = this.bidfileForm.clarifyTime[0]
          this.bidfileForm.clarifyEndTime = this.bidfileForm.clarifyTime[1]
          this.bidfileForm.fileInformationList = this.fileList
          bidfile.update(this.bidfileForm).then(() => {
            this.getApproveRecodes()
          })
        } else {
          return false
        }
      })
    },
    /** 提交 */
    submitResult (val) {
      this.btnDisabled = true
      let obj = {
        noticeCode: this.noticeCode
      }
      bidfile.getList(obj).then((res) => {
        let tenderDocList = res.data.tenderDocList
        return tenderDocList
      }).then((result) => {
        if (result) {
          // 判断是否已有添加的招标文件，若无，则不可提交
          if (result.length > 0) {
            bidfile.submit(this.noticeCode, 2).then(() => {
              this.btnDisabled = false
              this.getTenderDocList()
              this.getApproveRecodes()
            })
          } else {
            this.$message.error('请先添加招标文件！')
            this.btnDisabled = false
            return false
          }
        } else {
          this.$message.error('请先添加招标文件！')
          this.btnDisabled = false
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
        bidfile.delete(code).then(() => {
          this.getTenderDocList()
          this.getSections()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    /** 审批 */
    saveApproveForm (val) {
      this.$refs[val].validate((vaild) => {
        if (vaild) {
          setTimeout(this.approveRecord(), 1000) // 延迟1秒
        } else {
          return false
        }
      })
    },
    approveRecord () {
      this.bidExamForm.relatedCode = this.noticeCode
      this.bidExamForm.approveOpinion = this.bidExamForm.approveOpinion || ''
      this.bidExamForm.fileInformationList = this.fileApproveList
      bidfile.approve(this.bidExamForm).then(() => {
        this.getTenderDocList()
        this.getApproveRecodes()
        this.getNoticeProgress()
      })
    },
    /**
     * 删除上传附件
     */
    clickDel (index) {
      this.fileList.splice(index, 1)
      this.validFile()
    },
    /**
     * 删除上传附件
     */
    clickAddDel (index) {
      this.fileAddList.splice(index, 1)
      this.validAddFile()
    },
    /**
     * 删除上传附件
     */
    clickApproveDel (index) {
      this.fileApproveList.splice(index, 1)
    },
    /*
     * 下载
     */
    downloadFile,
    /**
     * 上传附件
     */
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
    uploadAddSuccess (response, file, fileList) {
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
      this.validAddFile()
    },
    uploadApproveSuccess (response, file, fileList) {
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
        this.fileApproveList = fileInformations // 获得目前项目中所有的附件信息
      }
    },
    validFile () {
      if (this.fileList.length > 0) {
        this.$set(this.bidfileForm, 'fileList', 'fileList')
      } else {
        this.$set(this.bidfileForm, 'fileList', '')
      }
      if (this.bidfileForm.fileList) {
        this.$refs['fileList'][this.index].clearValidate()
      }
    },
    validAddFile () {
      if (this.fileAddList.length > 0) {
        this.$set(this.bidfileAddForm, 'fileAddList', 'fileAddList')
      } else {
        this.$set(this.bidfileAddForm, 'fileAddList', '')
      }
      if (this.bidfileAddForm.fileAddList) {
        this.$refs['fileAddList'].clearValidate()
      }
    },
    validTime () {
      if (this.calendar.display) {
        this.$set(this.bidfileForm, 'bidOpenTime', 'bidOpenTime')
      } else {
        this.$set(this.bidfileForm, 'bidOpenTime', '')
      }
      if (this.bidfileForm.bidOpenTime) {
        this.$refs['bidOpenTime'][this.index].clearValidate()
      }
    },
    validAddTime () {
      if (this.calendarAdd.display) {
        this.$set(this.bidfileAddForm, 'bidOpenTimeAdd', 'bidOpenTimeAdd')
      } else {
        this.$set(this.bidfileAddForm, 'bidOpenTimeAdd', '')
      }
      if (this.bidfileAddForm.bidOpenTimeAdd) {
        this.$refs['bidOpenTimeAdd'].clearValidate()
      }
    },
    /** 查询招标文件 */
    getTenderDocList () {
      let enterpriseType = this.$store.getters.authUser.enterpriseType
      let obj = {
        noticeCode: this.noticeCode
      }
      if (enterpriseType !== 4) {
        obj.statusList = '2,3,4,5'
      }
      bidfile.getList(obj).then((res) => {
        let tenderDocList = res.data.tenderDocList
        if (tenderDocList) {
          this.tenderDocList = tenderDocList
          if (this.tenderDocList.length > 0) {
            // 默认展开第一个招标文件信息
            this.activeName = this.tenderDocList[0].objectId
            this.getTenderDoc(this.tenderDocList[0].objectId, 0)
            // 根据登录用户企业类型和招标文件状态的不同，展示不同的信息
            if (enterpriseType === 4) {
              this.dialogSubmit = true
              this.dialogApprove = false
              if (this.tenderDocList[0].tenderDocStatus !== 1 && this.tenderDocList[0].tenderDocStatus !== 5) {
                this.dialogSubmit = false
                this.dialogApprove = false
              }
            } else if (enterpriseType === 3 || enterpriseType === 2) {
              this.dialogSubmit = false
              this.dialogApprove = false
              if (this.projectEnterpriseType === 2 || this.projectEnterpriseType === 3) {
                if (this.tenderDocList[0].tenderDocStatus === 2) {
                  this.dialogApprove = true
                }
              }
            } else if (enterpriseType === 1) {
              this.dialogSubmit = false
              this.dialogApprove = true
              if ((this.projectEnterpriseType !== 1 && this.tenderDocList[0].tenderDocStatus !== 3) ||
                (this.projectEnterpriseType === 1 && this.tenderDocList[0].tenderDocStatus !== 2)) {
                this.dialogApprove = false
              }
            }
          } else {
            if (enterpriseType === 4) {
              this.dialogSubmit = true
              this.dialogApprove = false
            } else {
              this.dialogSubmit = false
              this.dialogApprove = false
            }
          }
          // 招标变更查询（只可查看）
          if (!this.operationCheckFlag) {
            this.dialogSubmit = false
            this.dialogApprove = false
          }
        } else {
          return false
        }
      })
    },
    /** 查询展开的一条招标文件信息 */
    getTenderDoc (objectId, index) {
      this.bidfileForm = {}
      this.fileList = []
      this.tableData = []
      this.index = index
      bidfile.getOne(objectId).then((res) => {
        let tenderDoc = res.data.tenderDoc
        if (tenderDoc) {
          this.bidfileForm = tenderDoc
          let sellTime = []
          sellTime[0] = this.bidfileForm.sellStartTime
          sellTime[1] = this.bidfileForm.sellEndTime
          this.bidfileForm.sellTime = sellTime
          let clarifyTime = []
          clarifyTime[0] = this.bidfileForm.clarifyStartTime
          clarifyTime[1] = this.bidfileForm.clarifyEndTime
          this.bidfileForm.clarifyTime = clarifyTime
          this.bidfileForm = Object.assign({}, this.bidfileForm)
          this.calendar.display = this.bidfileForm.bidOpenTime
          this.getDate()
          this.fileList = this.bidfileForm.fileInformationList
          this.tableData = this.bidfileForm.sectionList
        }
      })
    },
    /** 查询标段信息 */
    getSections () {
      this.tableAddData = []
      bidfile.getSectionList(this.noticeCode).then((res) => {
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
      bidfile.getApproveRecords(this.noticeCode, 6).then((res) => {
        this.approveRecordList = res.data.approveRecordList
      })
    },
    /** 查询项目进行状态 */
    getNoticeProgress () {
      let obj = {
        tenderProjectCode: this.$route.query.tenderProjectCode,
        noticeCode: this.noticeCode
      }
      openbid.getNoticeProgress(obj).then((res) => {
        let result = res.data.noticeProgress
        if (result) {
          let progressStage = result.progressStage
          // 若项目进行状态为开评标阶段，则开评标按钮高亮
          if (progressStage === 5) {
            this.$emit('noticeProgress', 5)
          }
          this.getNotLockedSections(progressStage)
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
    // 显示当前日期-默认日期
    getDate () {
      let date = new Date(this.calendar.display)
      this.calendar.display = formatDate(date, 'yyyy-MM-dd')
    },
    getList () {
      tenderproject.getOneByCode(this.$route.query.tenderProjectCode).then(res => {
        if (res.data.tenderProject) {
          this.projectEnterpriseType = res.data.tenderProject.projectEnterpriseType
        }
      }).then(res => {
        this.getTenderDocList()
      })
    }
  },
  /** 实例创建完成 */
  mounted () {
    this.getList()
    this.getSections()
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
#bidfile-page {
  .bidfile-cont, .app-record {
    .bidfile-tit {
      height: 50px;
      line-height: 50px;
      text-align:left;
      padding: 0 20px;
      background:#F7F6FB;
      span {
        font-weight: 800;
      }
    }
    .bidfile-form {
      padding: 20px;
      .btn-box {
        height: 60px;
      }
      .title-span {
        float: left;
        display: block;
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
      }
      /** 折叠面板 */
      .el-collapse-item {
        margin-bottom: 10px;
        .el-collapse-item__wrap {
          overflow: visible;
        }
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
      .apply-upload {
        .file-list {
          i {
            display: none;
          }
        }
        .file-list:hover {
          i {
            display: inline-block;
            color: red;
            cursor: pointer;
          }
        }
      }
      .last-form-btn {
        .el-form-item__content {
          text-align: center;
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
  }
  /** 审批记录 */
  .app-record {
    .division-line {
      height: 20px;
      background: #EAEDF2;
    }
    .app-cont {
      width: 100%;
      height: 300px;
      padding-top: 30px;
      overflow-y: auto;
      overflow-X: hidden;
      .step-span {
        display: inline-block;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        padding-right: 20px;
      }
      .span-first {
        width: 220px;
      }
      .span-second {
        width: 240px;
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
          margin-left: 0;
        }
      }
    }
  }
}
</style>
