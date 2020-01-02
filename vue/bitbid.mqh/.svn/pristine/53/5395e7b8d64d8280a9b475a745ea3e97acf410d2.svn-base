<template>
  <!-- 标段文件-邀请招标组件 -->
  <div class="admin-layout" id="invitefile-page">
    <div class="collapse-warp">
      <el-collapse v-model="sectionCode" accordion>
        <el-collapse-item v-for="(section,index) in sectionList" :key="index" :name=section.code>
          <template slot="title">
            <span class="title-span" @click="getDataBySection(section, index)" style="width: 100%; display: block">
              <span>{{index + 1}}</span>
              <span class="middle-span">{{section.name}}</span>
              <span>{{section.archivingStatus | filterStatus}}</span>
              <el-button
                type="text"
                @click.stop="submitCilck(section)"
                style="float: right;margin-right: 40px;line-height: 25px;"
                v-if="section.archivingStatus !== 2 && $route.query.operation !== 'look'">
                提交
              </el-button>
            </span>
          </template>
          <div class="collspase-cont">
            <el-tabs v-model="activeTabName" type="card" @tab-click="handleTabsClick">
              <el-tab-pane label="邀请招标文件" name="first">
                <el-form :model="tenderRuleForm" :rules="tenderRule" ref="tenderRuleForm" class="demo-ruleForm tabs-pane-form">
                  <el-form-item label="邀请招标文件:"  prop="tenderFile" ref="tenderFile">
                    <el-upload
                      class="upload-demo"
                      :action="upLoad"
                      :on-success="uploadTenderFileSuccess"
                      :show-file-list="false"
                      :before-upload="beforeUpload"
                      :file-list="tenderFileList">
                      <el-button size="small" type="success" v-if="dialogEdit">上传附件</el-button>
                    </el-upload>
                    <span class="file-list" v-for="(item, index) in tenderFileList" :key="index">
                      <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                      <i class="el-icon-error upload-file" @click="tenderFileDel(index)" v-if="dialogEdit"></i>
                    </span>
                  </el-form-item>
                </el-form>
                <div class="tabpane-btn">
                  <el-button type="primary" @click="tenderFileSave('tenderRuleForm', index)" v-if="dialogEdit">保 存</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane label="招标控制价" name="second">
                <el-form :model="bidPriceForm" :rules="bidPriceRule" ref="bidPriceForm" label-width="120px" class="demo-ruleForm tabs-pane-form">
                  <el-form-item label="控制价:" prop="controlPrice">
                    <el-input v-model="bidPriceForm.controlPrice" style="width: 300px;" :disabled="!dialogEdit"></el-input>万元
                  </el-form-item>
                  <el-form-item label="招标控制价:">
                    <el-upload
                      class="upload-demo"
                      :action="upLoad"
                      :on-success="uploadPriceSuccess"
                      :file-list="priceFileList"
                      :show-file-list="false">
                      <el-button size="small" type="success" v-if="dialogEdit">上传附件</el-button>
                    </el-upload>
                    <span class="file-list" v-for="(item, index) in priceFileList" :key="index">
                      <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                      <i class="el-icon-error upload-file" @click="priceFileDel(index)" v-if="dialogEdit"></i>
                    </span>
                  </el-form-item>
                </el-form>
                <div class="tabpane-btn">
                  <el-button type="primary" @click="priceFileSave('bidPriceForm', index)" v-if="dialogEdit">保 存</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane label="答疑补遗" name="third">
                <el-form label-width="120px" class="demo-ruleForm tabs-pane-form">
                  <el-form-item label="质疑文件:">
                    <el-upload
                      class="upload-demo"
                      :action="upLoad"
                      :on-success="uploadQuesFileSuccess"
                      :show-file-list="false"
                      :file-list="quesFileList">
                      <el-button size="small" type="success" v-if="dialogEdit">上传附件</el-button>
                    </el-upload>
                    <span class="file-list" v-for="(item, index) in quesFileList" :key="index">
                      <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                      <i class="el-icon-error upload-file" @click="quesFileDel(index)" v-if="dialogEdit"></i>
                    </span>
                  </el-form-item>
                  <el-form-item label="澄清文件:">
                    <el-upload
                      class="upload-demo"
                      :action="upLoad"
                      :on-success="uploadClarifyFileSuccess"
                      :show-file-list="false"
                      :file-list="clarifyFileList">
                      <el-button size="small" type="success" v-if="dialogEdit">上传附件</el-button>
                    </el-upload>
                    <span class="file-list" v-for="(item, index) in clarifyFileList" :key="index">
                      <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                      <i class="el-icon-error upload-file" @click="clarifyFileDel(index)" v-if="dialogEdit"></i>
                    </span>
                  </el-form-item>
                </el-form>
                <div class="tabpane-btn">
                  <el-button type="primary" @click="clarifyFileSave" v-if="dialogEdit">保 存</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane label="开评定标" name="fourth">
                <el-form :model="bidRuleForm" :rules="bidRule" ref="bidRuleForm" label-width="120px" class="demo-ruleForm tabs-pane-form">
                  <table class="info-table">
                  <tr>
                    <td>开标时间</td>
                    <td>{{openingTime | filterTime}}</td>
                    <td>开标地点</td>
                    <td>{{biddingPlace}}</td>
                  </tr>
                  <tr>
                    <td>开标一览表</td>
                    <td>
                      <el-form-item prop="openRecordFile" ref="openRecordFile">
                        <el-upload
                          class="upload-demo"
                          :action="upLoad"
                          :on-success="uploadOpenFileSuccess"
                          :file-list="openRecordFileList"
                          :show-file-list="false">
                          <el-button size="small" type="success" v-if="dialogEdit">上传附件</el-button>
                        </el-upload>
                        <span class="file-list" v-for="(item, index) in openRecordFileList" :key="index">
                          <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                          <i class="el-icon-error upload-file" @click="openBidFileDel(index)" v-if="dialogEdit"></i>
                        </span>
                      </el-form-item>
                    </td>
                    <td>评标专家表</td>
                    <td>
                      <el-form-item prop="evalExportFile" ref="evalExportFile">
                        <el-upload
                          class="upload-demo"
                          :action="upLoad"
                          :on-success="uploadEvalExpFileSuccess"
                          :file-list="evalExportFileList"
                          :show-file-list="false">
                          <el-button size="small" type="success" v-if="dialogEdit">上传附件</el-button>
                        </el-upload>
                        <span class="file-list" v-for="(item, index) in evalExportFileList" :key="index">
                          <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                          <i class="el-icon-error upload-file" @click="evalExpFileDel(index)" v-if="dialogEdit"></i>
                        </span>
                      </el-form-item>
                    </td>
                  </tr>
                  <tr>
                    <td>评标报告</td>
                    <td>
                      <el-form-item prop="evalReportFile" ref="evalReportFile">
                        <el-upload
                          class="upload-demo"
                          :action="upLoad"
                          :on-success="uploadEvalRepFileSuccess"
                          :before-upload="beforeUpload"
                          :file-list="evalReportFileList"
                          :show-file-list="false">
                          <el-button size="small" type="success" v-if="dialogEdit">上传附件</el-button>
                        </el-upload>
                        <span class="file-list" v-for="(item, index) in evalReportFileList" :key="index">
                          <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                          <i class="el-icon-error upload-file" @click="evalRepFileDel(index)" v-if="dialogEdit"></i>
                        </span>
                      </el-form-item>
                    </td>
                    <td>投标文件（中标人）</td>
                    <td>
                      <el-form-item prop="bidWinnerFile" ref="bidWinnerFile">
                        <el-upload
                          class="upload-demo"
                          :action="upLoad"
                          :on-success="uploadbidWinFileSuccess"
                          :before-upload="beforeUpload"
                          :file-list="bidWinnerFileList"
                          :show-file-list="false">
                          <el-button size="small" type="success" v-if="dialogEdit">上传附件</el-button>
                        </el-upload>
                        <span class="file-list" v-for="(item, index) in bidWinnerFileList" :key="index">
                          <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                          <i class="el-icon-error upload-file" @click="bidWinnerFileDel(index)" v-if="dialogEdit"></i>
                        </span>
                      </el-form-item>
                    </td>
                  </tr>
                  <tr>
                    <td>中标通知书</td>
                    <td colspan="3">
                      <el-form-item prop="noticeFile" ref="noticeFile">
                        <el-upload
                          class="upload-demo"
                          :action="upLoad"
                          :on-success="uploadnoticeFileSuccess"
                          :before-upload="beforeUpload"
                          :file-list="noticeFileList"
                          :show-file-list="false">
                          <el-button size="small" type="success" v-if="dialogEdit">上传附件</el-button>
                        </el-upload>
                        <span class="file-list" v-for="(item, index) in noticeFileList" :key="index">
                          <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                          <i class="el-icon-error upload-file" @click="noticeFileDel(index)" v-if="dialogEdit"></i>
                        </span>
                      </el-form-item>
                    </td>
                  </tr>
                  <tr>
                    <td>中标候选人公示</td>
                    <td>
                      <el-form-item prop="publicityFile" ref="publicityFile">
                        <el-upload
                          class="upload-demo"
                          :action="upLoad"
                          :on-success="uploadPubFileSuccess"
                          :before-upload="beforeUpload"
                          :file-list="publicityFileList"
                          :show-file-list="false">
                          <el-button size="small" type="success" v-if="dialogEdit">上传附件</el-button>
                        </el-upload>
                        <span class="file-list" v-for="(item, index) in publicityFileList" :key="index">
                          <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                          <i class="el-icon-error upload-file" @click="publicityFileDel(index)" v-if="dialogEdit"></i>
                        </span>
                        <el-button type="text" @click="editPubDetals" class="look-details">
                          {{$route.query.operation === 'look'?'查看':'编辑'}}
                        </el-button>
                      </el-form-item>
                    </td>
                    <td>中标结果公示</td>
                    <td>
                      <el-form-item prop="resultFile" ref="resultFile">
                        <el-upload
                          class="upload-demo"
                          :action="upLoad"
                          :on-success="uploadResFileSuccess"
                          :before-upload="beforeUpload"
                          :file-list="resultFileList"
                          :show-file-list="false">
                          <el-button size="small" type="success" v-if="dialogEdit">上传附件</el-button>
                        </el-upload>
                        <span class="file-list" v-for="(item, index) in resultFileList" :key="index">
                          <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                          <i class="el-icon-error upload-file" @click="resultFileDel(index)" v-if="dialogEdit"></i>
                        </span>
                        <el-button type="text" @click="editResDetals" class="look-details">
                          {{$route.query.operation === 'look'?'查看':'编辑'}}
                        </el-button>
                      </el-form-item>
                    </td>
                  </tr>
                  <tr>
                    <td>补充文件</td>
                    <td colspan="3">
                      <el-upload
                        class="upload-demo"
                        :action="upLoad"
                        :on-success="uploadOpenBidSuccess"
                        :file-list="openBidFileMoreList"
                        :show-file-list="false">
                        <el-button size="small" type="success" v-if="dialogEdit">上传附件</el-button>
                      </el-upload>
                      <span class="file-list" v-for="(item, index) in openBidFileMoreList" :key="index">
                        <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                        <i class="el-icon-error upload-file" @click="openBidFileMoreDel(index)" v-if="dialogEdit"></i>
                      </span>
                    </td>
                  </tr>
                </table>
                </el-form>
                <div class="tabpane-btn">
                  <el-button type="primary" @click="openEvalSave('bidRuleForm', index)" v-if="dialogEdit">保 存</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane label="其他文件" name="fifth">
                <el-form label-width="120px" class="demo-ruleForm tabs-pane-form">
                  <el-form-item label="其他附件:">
                    <el-upload
                      class="upload-demo"
                      :action="upLoad"
                      :on-success="handleSuccess"
                      :file-list="otherFileList"
                      :show-file-list="false">
                      <el-button size="small" type="success" v-if="dialogEdit">上传附件</el-button>
                    </el-upload>
                    <span class="file-list" v-for="(item, index) in otherFileList" :key="index">
                      <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                      <i class="el-icon-error upload-file" @click="otherFileDel(index)" v-if="dialogEdit"></i>
                    </span>
                  </el-form-item>
                </el-form>
                <div class="tabpane-btn">
                  <el-button type="primary" @click="otherFileSave" v-if="dialogEdit">保 存</el-button>
                </div>
              </el-tab-pane>
            </el-tabs>
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>
    <!--弹窗区域start-->
    <div class="dialog-box">
      <el-dialog
        :visible.sync="dialogVisible"
        width="50%">
        <div class="dialog-cont">
          <!--编辑器组件-->
          <ueditor ref="ueditor" :readonly="readonly"></ueditor>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelBtn" v-if="dialogEdit">取 消</el-button>
          <el-button type="primary" @click="confirmBtn" v-if="dialogEdit">确 定</el-button>
        </div>
      </el-dialog>

      <el-dialog
        :visible.sync="dialogResVisible"
        width="50%">
        <div class="dialog-cont">
          <!--编辑器组件-->
          <ueditor ref="ueditorRes" :readonly="readonlyRes"></ueditor>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelResBtn" v-if="dialogEdit">取 消</el-button>
          <el-button type="primary" @click="confirmResBtn" v-if="dialogEdit">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    <!--弹窗区域end-->
  </div>
</template>
<script>
// 引入编辑器
import ueditor from '@/components/ueditor/ueditor.vue'
import {section, archivesFile, bidprice, openbid, resnotice, candNotice, inviteOpenEvalFile, tenderproject} from 'api/index'
import {commonJs, downloadFile} from 'common/js/common'
import {formatDate} from 'common/js/date'
export default {
  data () {
    var validPrice = (rule, value, callback) => {
      if (value && (!(/^\d{1,14}(\.\d{1,6})?$/).test(value))) {
        callback(new Error('控制价只能输入小数点前不超过14位且小数点后不超过6位的数字'))
      } else {
        callback()
      }
    }
    return {
      upLoad: commonJs.fileUploadUrl,
      activeTabName: 'first', // tabs默认展开项
      sectionList: [], // 标段
      sectionCode: null, // 当前展开面板
      dialogVisible: false,
      dialogResVisible: false, // 中标结果公示-弹窗
      dialogEdit: false, // 是否可编辑
      ueditorCont: '', // 编辑器内容
      fileList: [],
      tenderFileList: [], // 邀请招标文件
      priceFileList: [], // 控制价文件
      quesFileList: [], // 质疑文件
      clarifyFileList: [], // 澄清文件
      otherFileList: [], // 其他附件
      openRecordFileList: [], // 开标一览表
      evalExportFileList: [], // 评标专家表
      evalReportFileList: [], // 评标报告
      bidWinnerFileList: [], // 投标文件（中标人）
      noticeFileList: [], // 中标通知书
      resultFileList: [], // 中标结果公示
      publicityFileList: [], // 中标候选人公示
      openBidFileMoreList: [], // 开评定标补充文件
      archivesFile: {}, // 档案管理归档附件
      quesArchivesFile: {}, // 档案管理归档附件（质疑文件）
      clarArchivesFile: {}, // 档案管理归档附件（澄清文件）
      openingRecord: {}, // 开标记录
      evaluationResult: {}, // 评标结果
      resultNotice: {}, // 中标结果公示
      publicity: {}, // 中标候选人公示
      fileType: null, // 文件类型
      fileTypes: null, // 包含的文件类型
      bidPriceForm: {}, // 招标控制价
      openingTime: null, // 开标时间
      biddingPlace: null, // 开标地点
      tenderRuleForm: {}, // 邀请招标文件页面
      bidRuleForm: {
        openRecordFile: '',
        evalExportFile: '',
        evalReportFile: '',
        bidWinnerFile: '',
        noticeFile: '',
        publicityFile: '',
        resultFile: ''
      }, // 开评定标页面
      tenderRule: {
        tenderFile: [
          {required: true, message: '请上传邀请招标文件', trigger: 'blur'}
        ]
      }, // 邀请招标文件校验
      bidRule: {
        openRecordFile: [
          {required: true, message: '请上传开标一览表', trigger: 'blur'}
        ],
        evalExportFile: [
          {required: true, message: '请上传评标专家表', trigger: 'blur'}
        ],
        evalReportFile: [
          {required: true, message: '请上传评标结果', trigger: 'blur'}
        ],
        bidWinnerFile: [
          {required: true, message: '请上传投标文件（中标人）', trigger: 'blur'}
        ],
        noticeFile: [
          {required: true, message: '请上传中标通知书', trigger: 'blur'}
        ],
        publicityFile: [
          {required: true, message: '请上传中标候选人公示', trigger: 'blur'}
        ],
        resultFile: [
          {required: true, message: '请上传中标结果公示', trigger: 'blur'}
        ]
      }, // 开评定标校验
      bidPriceRule: {
        controlPrice: [
          {validator: validPrice}
        ]
      }, // 控制价校验
      index: 0, // 当前操作的折叠面板序号
      readonly: false,
      readonlyRes: false,
      publicityContent: '', // 中标候选人公示内容
      resultNoticeContent: '', // 中标结果公示内容
      inviteOpenEvalFile: {} // 开评定标数据
    }
  },
  props: {
    tenderProjectCode: String
  },
  computed: {},
  created () {
  },
  filters: {
    /** 标段是否归档 */
    filterStatus (val) {
      if (val === 2) {
        return '已归档'
      } else {
        return '未归档'
      }
    },
    /** 时间格式化 */
    filterTime (val) {
      if (val) {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return ''
      }
    }
  },
  /** 方法集 */
  methods: {
    confirmBtn () {
      // 获得编辑器中的内容
      let publicityContent = this.$refs.ueditor.getContent()
      this.publicityContent = publicityContent
      // 关闭弹窗
      this.dialogVisible = false
    },
    confirmResBtn () {
      // 获得编辑器中的内容
      let resultNoticeContent = this.$refs.ueditorRes.getContent()
      this.resultNoticeContent = resultNoticeContent
      // 关闭弹窗
      this.dialogResVisible = false
    },
    cancelBtn () {
      this.dialogVisible = false
      this.$refs.ueditor.setContent('')
      this.publicityContent = ''
    },
    cancelResBtn () {
      this.dialogResVisible = false
      this.$refs.ueditorRes.setContent('')
      this.resultNoticeContent = ''
    },
    /* tabs切换 */
    handleTabsClick (tab, event) {
      if (tab.name === 'first') {
        this.$refs['tenderRuleForm'][this.index].resetFields()
        this.fileType = 9
        this.getArchivesFile()
      } else if (tab.name === 'second') {
        this.getControlPrice()
      } else if (tab.name === 'third') {
        this.fileTypes = '10, 11'
        this.getArchivesFiles()
      } else if (tab.name === 'fourth') {
        this.$refs['bidRuleForm'][this.index].resetFields()
        this.fileType = 7
        this.getTenderProject()
        this.getInviteOpenEvalFile()
      } else if (tab.name === 'fifth') {
        this.fileType = 8
        this.getArchivesFile()
      }
    },
    /** 邀请招标文件校验 */
    validTenderFile () {
      if (this.tenderFileList.length > 0) {
        this.$set(this.tenderRuleForm, 'tenderFile', 'tenderFile')
      } else {
        this.$set(this.tenderRuleForm, 'tenderFile', '')
      }
      if (this.tenderRuleForm.tenderFile) {
        this.$refs['tenderFile'][this.index].clearValidate()
      }
    },
    /** 开评定标页面文件校验 */
    validBidFile () {
      // 开标一览表
      if (this.openRecordFileList.length > 0) {
        this.$set(this.bidRuleForm, 'openRecordFile', 'openRecordFile')
      } else {
        this.$set(this.bidRuleForm, 'openRecordFile', '')
      }
      if (this.bidRuleForm.openRecordFile) {
        this.$refs['openRecordFile'][this.index].clearValidate()
      }
      // 评标专家表
      if (this.evalExportFileList.length > 0) {
        this.$set(this.bidRuleForm, 'evalExportFile', 'evalExportFile')
      } else {
        this.$set(this.bidRuleForm, 'evalExportFile', '')
      }
      if (this.bidRuleForm.evalExportFile) {
        this.$refs['evalExportFile'][this.index].clearValidate()
      }
      // 评标报告
      if (this.evalReportFileList.length > 0) {
        this.$set(this.bidRuleForm, 'evalReportFile', 'evalReportFile')
      } else {
        this.$set(this.bidRuleForm, 'evalReportFile', '')
      }
      if (this.bidRuleForm.evalReportFile) {
        this.$refs['evalReportFile'][this.index].clearValidate()
      }
      // 投标文件（中标人）
      if (this.bidWinnerFileList.length > 0) {
        this.$set(this.bidRuleForm, 'bidWinnerFile', 'bidWinnerFile')
      } else {
        this.$set(this.bidRuleForm, 'bidWinnerFile', '')
      }
      if (this.bidRuleForm.bidWinnerFile) {
        this.$refs['bidWinnerFile'][this.index].clearValidate()
      }
      // 中标通知书
      if (this.noticeFileList.length > 0) {
        this.$set(this.bidRuleForm, 'noticeFile', 'noticeFile')
      } else {
        this.$set(this.bidRuleForm, 'noticeFile', '')
      }
      if (this.bidRuleForm.noticeFile) {
        this.$refs['noticeFile'][this.index].clearValidate()
      }
      // 中标候选人公示
      if (this.publicityFileList.length > 0) {
        this.$set(this.bidRuleForm, 'publicityFile', 'publicityFile')
      } else {
        this.$set(this.bidRuleForm, 'publicityFile', '')
      }
      if (this.bidRuleForm.publicityFile) {
        this.$refs['publicityFile'][this.index].clearValidate()
      }
      // 中标结果公示
      if (this.resultFileList.length > 0) {
        this.$set(this.bidRuleForm, 'resultFile', 'resultFile')
      } else {
        this.$set(this.bidRuleForm, 'resultFile', '')
      }
      if (this.bidRuleForm.resultFile) {
        this.$refs['resultFile'][this.index].clearValidate()
      }
    },
    downloadFile,
    /** 邀请招标文件上传 */
    uploadTenderFileSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.tenderFileList = []
        this.tenderFileList.push(file.response.fileInformation)
      }
      this.validTenderFile()
    },
    /** 控制价文件上传 */
    uploadPriceSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.priceFileList = []
        this.priceFileList.push(file.response.fileInformation)
      }
    },
    /** 质疑文件上传 */
    uploadQuesFileSuccess (response, file, fileList) {
      this.quesFileList = this.uploadSuccess(response, file, fileList)
    },
    /** 澄清文件上传 */
    uploadClarifyFileSuccess (response, file, fileList) {
      this.clarifyFileList = this.uploadSuccess(response, file, fileList)
    },
    /** 开标一览表上传 */
    uploadOpenFileSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.openRecordFileList = []
        this.openRecordFileList.push(file.response.fileInformation)
      }
      this.validBidFile()
    },
    /** 评标专家表上传 */
    uploadEvalExpFileSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.evalExportFileList = []
        this.evalExportFileList.push(file.response.fileInformation)
      }
      this.validBidFile()
    },
    /** 评标报告上传 */
    uploadEvalRepFileSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.evalReportFileList = []
        this.evalReportFileList.push(file.response.fileInformation)
      }
      this.validBidFile()
    },
    /** 投标文件（中标人）上传 */
    uploadbidWinFileSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.bidWinnerFileList = []
        this.bidWinnerFileList.push(file.response.fileInformation)
      }
      this.validBidFile()
    },
    /** 中标通知书上传 */
    uploadnoticeFileSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.noticeFileList = []
        file.response.fileInformation.businessType = '2'
        this.noticeFileList.push(file.response.fileInformation)
      }
      this.validBidFile()
    },
    /** 中标结果公示上传 */
    uploadResFileSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.resultFileList = []
        file.response.fileInformation.businessType = '1'
        this.resultFileList.push(file.response.fileInformation)
      }
      this.validBidFile()
    },
    /** 中标候选人公示上传 */
    uploadPubFileSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.publicityFileList = []
        this.publicityFileList.push(file.response.fileInformation)
      }
      this.validBidFile()
    },
    /** 开评定标补充文件上传 */
    uploadOpenBidSuccess (response, file, fileList) {
      this.openBidFileMoreList = this.uploadSuccess(response, file, fileList)
    },
    /** 其他文件上传 */
    handleSuccess (response, file, fileList) {
      this.otherFileList = this.uploadSuccess(response, file, fileList)
    },
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
        return fileInformations
      }
    },
    /** 邀请招标文件删除 */
    tenderFileDel (index) {
      this.tenderFileList.splice(index, 1)
      this.validTenderFile()
    },
    /** 控制价文件删除 */
    priceFileDel (index) {
      this.priceFileList.splice(index, 1)
    },
    /** 质疑文件删除 */
    quesFileDel (index) {
      this.quesFileList.splice(index, 1)
    },
    /** 澄清文件删除 */
    clarifyFileDel (index) {
      this.clarifyFileList.splice(index, 1)
    },
    /** 开标一览表删除 */
    openBidFileDel (index) {
      this.openRecordFileList.splice(index, 1)
      this.validBidFile()
    },
    /** 评标专家表删除 */
    evalExpFileDel (index) {
      this.evalExportFileList.splice(index, 1)
      this.validBidFile()
    },
    /** 评标报告删除 */
    evalRepFileDel (index) {
      this.evalReportFileList.splice(index, 1)
      this.validBidFile()
    },
    /** 投标文件（中标人）删除 */
    bidWinnerFileDel (index) {
      this.bidWinnerFileList.splice(index, 1)
      this.validBidFile()
    },
    /** 中标通知书删除 */
    noticeFileDel (index) {
      this.noticeFileList.splice(index, 1)
      this.validBidFile()
    },
    /** 中标结果公示删除 */
    resultFileDel (index) {
      this.resultFileList.splice(index, 1)
      this.validBidFile()
    },
    /** 中标候选人公示删除 */
    publicityFileDel (index) {
      this.publicityFileList.splice(index, 1)
      this.validBidFile()
    },
    /** 开评定标补充文件删除 */
    openBidFileMoreDel (index) {
      this.openBidFileMoreList.splice(index, 1)
    },
    /** 其他文件删除 */
    otherFileDel (index) {
      this.otherFileList.splice(index, 1)
    },
    /** 附件格式为pdf */
    beforeUpload (file) {
      const isPDF = file.type === 'application/pdf'
      if (!isPDF) {
        this.$message.error('上传文件只能为PDF格式！')
      }
      return isPDF
    },
    /** 邀请招标文件保存 */
    tenderFileSave (val, index) {
      this.validTenderFile()
      this.$refs[val][index].validate((valid) => {
        if (valid) {
          this.saveFiles()
        }
      })
    },
    /** 控制价文件保存 */
    priceFileSave (val, index) {
      this.$refs[val][index].validate((valid) => {
        if (valid) {
          if (!this.bidPriceForm.objectId) {
            this.bidPriceForm.sectionCode = this.sectionCode
          }
          this.bidPriceForm.fileInformation = null
          if (this.priceFileList) {
            if (this.priceFileList.length > 0) {
              this.bidPriceForm.fileInformation = this.priceFileList[0]
            }
          }
          bidprice.savePriceSublist(this.bidPriceForm).then((res) => {
            this.getControlPrice()
          })
        }
      })
    },
    /** 答疑补遗保存 */
    clarifyFileSave () {
      this.fileTypes = '10, 11'
      let that = this
      this.quesFileSave().then(this.clarFileSave()).then(function () {
        setTimeout(that.getArchivesFiles, 500)
      })
    },
    /** 质疑文件保存 */
    quesFileSave () {
      if (!this.quesArchivesFile.objectId) {
        this.quesArchivesFile.archivesManagementCode = this.$route.query.code
        this.quesArchivesFile.sectionCode = this.sectionCode
        this.quesArchivesFile.fileType = 10
      }
      this.quesArchivesFile.fileInformationList = this.quesFileList
      return archivesFile.save(this.quesArchivesFile)
    },
    /** 澄清文件保存 */
    clarFileSave () {
      if (!this.clarArchivesFile.objectId) {
        this.clarArchivesFile.archivesManagementCode = this.$route.query.code
        this.clarArchivesFile.sectionCode = this.sectionCode
        this.clarArchivesFile.fileType = 11
      }
      this.clarArchivesFile.fileInformationList = this.clarifyFileList
      return archivesFile.save(this.clarArchivesFile)
    },
    /** 开评定标保存 */
    openEvalSave (val, index) {
      this.validBidFile()
      if (((!this.$refs.ueditor || !this.$refs.ueditor.getContent()) &&
        (!this.publicity || !this.publicity.publicityContent)) ||
        ((!this.$refs.ueditorRes || !this.$refs.ueditorRes.getContent()) &&
        (!this.resultNotice || !this.resultNotice.resultNoticeContent))) {
        this.$message.error('请填写中标候选人公示与中标结果公示内容')
        return
      }
      this.$refs[val][index].validate((valid) => {
        if (valid) {
          // 开标记录
          this.openRecordSave()
          // 评标结果
          this.evaluationSave()
          // 中标通知书、中标结果公示
          this.resultNoitceSave()
          // 中标候选人公示
          this.publicitySave()
          // 开评定标补充文件
          this.saveFiles()
          let inviteFile = {}
          inviteFile.openingRecord = this.openingRecord
          inviteFile.evaluationResult = this.evaluationResult
          inviteFile.resultNotice = this.resultNotice
          inviteFile.publicity = this.publicity
          inviteFile.archivesFile = this.archivesFile
          inviteOpenEvalFile.saveInviteFile(inviteFile).then((res) => {
            this.getInviteOpenEvalFile()
          })
        }
      })
    },
    /** 开标记录保存 */
    openRecordSave () {
      if (!this.openingRecord) {
        this.openingRecord = {}
        this.openingRecord.sectionCode = this.sectionCode
        this.openingRecord.bidOpeningTime = this.openingTime
      }
      this.openingRecord.fileInformation = this.openRecordFileList[0]
    },
    /** 评标结果保存 */
    evaluationSave () {
      let evaluationResultFileList = []
      let evaluationResultSublists = []
      if (!this.evaluationResult) {
        this.evaluationResult = {}
        this.evaluationResult.sectionCode = this.sectionCode
        // 评标专家
        let obj = {
          evaluationResultFileType: 1,
          fileInformationList: this.evalExportFileList
        }
        // 评标报告
        let obj2 = {
          evaluationResultFileType: 2,
          fileInformationList: this.evalReportFileList
        }
        evaluationResultFileList.push(obj)
        evaluationResultFileList.push(obj2)
        // 投标文件（中标人）
        let obj3 = {
          fileInformation: this.bidWinnerFileList[0]
        }
        evaluationResultSublists.push(obj3)
      } else {
        evaluationResultFileList = this.evaluationResult.evaluationResultFileList
        evaluationResultFileList.map((evaluationResultFile) => {
          if (evaluationResultFile.evaluationResultFileType === 1) {
            evaluationResultFile.fileInformationList = this.evalExportFileList
          } else if (evaluationResultFile.evaluationResultFileType === 2) {
            evaluationResultFile.fileInformationList = this.evalReportFileList
          }
        })
        evaluationResultSublists = this.evaluationResult.evaluationResultSublists
        evaluationResultSublists.map((evaluationResultSublist) => {
          evaluationResultSublist.fileInformation = this.bidWinnerFileList[0]
        })
      }
      this.$set(this.evaluationResult, 'evaluationResultFileList', evaluationResultFileList)
      this.$set(this.evaluationResult, 'evaluationResultSublists', evaluationResultSublists)
    },
    /** 中标结果公示保存 */
    resultNoitceSave () {
      if (!this.resultNotice) {
        this.resultNotice = {}
        this.resultNotice.sectionCode = this.sectionCode
      }
      this.resultNotice.resultNoticeType = 1
      this.resultNotice.resultNoticeChangeCount = 0
      this.resultNotice.isAllowOperate = 0
      this.resultNotice.resultNoticeContent = this.resultNoticeContent
      this.resultNotice.fileInformationList = []
      this.resultNotice.fileInformationList.push(this.resultFileList[0])
      this.resultNotice.fileInformationList.push(this.noticeFileList[0])
    },
    /** 中标候选人公示保存 */
    publicitySave () {
      if (!this.publicity) {
        this.publicity = {}
        this.publicity.sectionCode = this.sectionCode
      }
      this.publicity.publicityContent = this.publicityContent
      this.publicity.fileInformationList = []
      this.publicity.fileInformationList = this.publicityFileList
    },
    /** 其他文件保存 */
    otherFileSave () {
      this.saveFiles()
    },
    saveFiles () {
      // 当前table页
      let tab = {}
      let event = {}
      if (!this.archivesFile.objectId) {
        this.archivesFile.archivesManagementCode = this.$route.query.code
        this.archivesFile.sectionCode = this.sectionCode
        this.archivesFile.fileType = this.fileType
      }
      if (this.fileType === 9) {
        tab.name = 'first'
        // 邀请招标文件
        this.archivesFile.fileInformationList = this.tenderFileList
      } else if (this.fileType === 8) {
        tab.name = 'fifth'
        // 其他文件
        this.archivesFile.fileInformationList = this.otherFileList
      } else if (this.fileType === 7) {
        tab.name = 'fourth'
        // 开评定标补充文件
        this.archivesFile.fileInformationList = this.openBidFileMoreList
      }
      if (this.fileType !== 7) {
        archivesFile.save(this.archivesFile).then((res) => {
          this.handleTabsClick(tab, event)
        })
      }
    },
    submitCilck (sectionMsg) {
      this.$confirm('此操作将对该标段提交归档, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let isTenderFileFlag = false
        let isOpenFileFlag = false
        let query = {
          archivesManagementCode: this.$route.query.code,
          sectionCode: this.sectionCode,
          fileType: 9
        }
        archivesFile.getOne(query).then((res) => {
          if (res.data.archivesFile) {
            isTenderFileFlag = true
          }
        }).then((res) => {
          let query2 = {
            sectionCode: this.sectionCode
          }
          openbid.getOpenRecord(query2).then((res) => {
            if (res.data.openingRecord) {
              isOpenFileFlag = true
            }
          }).then((res) => {
            // 邀请招标文件与开评定标信息为必填项，如未填写，不可提交
            if (isTenderFileFlag && isOpenFileFlag) {
              sectionMsg.archivingStatus = 2
              section.save(sectionMsg).then((res) => {
                this.dialogEdit = false
                this.readonly = true
              })
            } else {
              this.$message.error('未填写邀请招标文件信息或开评定标信息，不能提交')
            }
          })
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    /** 编辑（中标候选人公示） */
    editPubDetals () {
      this.dialogVisible = true
      if (this.publicityContent) {
        this.$refs.ueditor.setContent(this.publicityContent)
      } else {
        this.getPublicityContent()
      }
    },
    /** 编辑（中标结果公示） */
    editResDetals () {
      this.dialogResVisible = true
      if (this.resultNoticeContent) {
        this.$refs.ueditor.setContent(this.resultNoticeContent)
      } else {
        this.getResultNoticeContent()
      }
    },
    /** 标段信息 */
    getSections () {
      let query = {
        tenderProjectCode: this.tenderProjectCode
      }
      section.getList(query).then((res) => {
        let sectionList = res.data.sectionList
        if (sectionList) {
          this.sectionList = sectionList
        }
      }).then((res) => {
        if (this.sectionList.length > 0) {
          // 默认展示第一个标段的信息
          this.sectionCode = this.sectionList[0].code
          this.openingTime = this.sectionList[0].openingTime
          if (this.sectionList[0].archivingStatus === 2 ||
            this.$route.query.operation === 'look') {
            this.dialogEdit = false
            this.readonly = true
            this.readonlyRes = true
          } else {
            this.dialogEdit = true
            this.readonly = false
            this.readonlyRes = false
          }
          this.fileType = 9
          this.getArchivesFile()
        }
      })
    },
    /** 根据标段查询折叠面板信息 */
    getDataBySection (section, index) {
      this.index = index
      this.activeTabName = 'first'
      this.fileType = 9
      this.sectionCode = section.code
      if (section.archivingStatus === 2 ||
        this.$route.query.operation === 'look') {
        this.dialogEdit = false
        this.readonly = true
        this.readonlyRes = true
      } else {
        this.dialogEdit = true
        this.readonly = false
        this.readonlyRes = false
      }
      this.getArchivesFile()
    },
    /** 查询补充附件信息 */
    getArchivesFile () {
      this.archivesFile = {}
      this.tenderFileList = []
      this.otherFileList = []
      this.openBidFileMoreList = []
      if (this.fileType === 7) {
        this.archivesFile = this.inviteOpenEvalFile.archivesFile
        // 开评定标补充文件
        this.openBidFileMoreList = this.archivesFile.fileInformationList
      } else {
        let query = {
          archivesManagementCode: this.$route.query.code,
          sectionCode: this.sectionCode,
          fileType: this.fileType
        }
        archivesFile.getOne(query).then((res) => {
          let archivesFile = res.data.archivesFile
          if (archivesFile) {
            this.archivesFile = archivesFile
            if (this.fileType === 9) {
              // 邀请招标文件
              this.tenderFileList = this.archivesFile.fileInformationList
            } else if (this.fileType === 8) {
              // 其他文件
              this.otherFileList = this.archivesFile.fileInformationList
            }
          } else {
            return false
          }
        })
      }
    },
    /** 查询答疑补遗文件信息 */
    getArchivesFiles () {
      this.quesArchivesFile = {}
      this.clarArchivesFile = {}
      this.quesFileList = []
      this.clarifyFileList = []
      let query = {
        archivesManagementCode: this.$route.query.code,
        sectionCode: this.sectionCode,
        fileTypes: this.fileTypes
      }
      archivesFile.getList(query).then((res) => {
        let archivesFiles = res.data.archivesFiles
        if (archivesFiles) {
          archivesFiles.map((archivesFile) => {
            if (archivesFile.fileType === 10) {
              // 质疑文件
              this.quesArchivesFile = archivesFile
              this.quesFileList = archivesFile.fileInformationList
            } else if (archivesFile.fileType === 11) {
              // 澄清文件
              this.clarArchivesFile = archivesFile
              this.clarifyFileList = archivesFile.fileInformationList
            }
          })
        } else {
          return false
        }
      })
    },
    /** 查询控制价信息 */
    getControlPrice () {
      this.bidPriceForm = {}
      this.priceFileList = []
      let query = {
        sectionCode: this.sectionCode
      }
      bidprice.getPriceSublist(query).then((res) => {
        if (res.data.controlPriceSublist) {
          this.bidPriceForm = res.data.controlPriceSublist
          if (this.bidPriceForm.fileInformation) {
            this.priceFileList.push(this.bidPriceForm.fileInformation)
          }
        }
      })
    },
    /** 查询开标信息 */
    getOpenRecord () {
      this.openRecordFileList = []
      this.openingRecord = this.inviteOpenEvalFile.openingRecord
      if (this.openingRecord) {
        this.openRecordFileList[0] = this.openingRecord.fileInformation
      }
    },
    /** 查询评标信息 */
    getEvaluationResult () {
      this.evalExportFileList = []
      this.evalReportFileList = []
      this.bidWinnerFileList = []
      this.evaluationResult = this.inviteOpenEvalFile.evaluationResult
      if (this.evaluationResult) {
        let evaluationResultFileList = this.evaluationResult.evaluationResultFileList
        if (evaluationResultFileList) {
          evaluationResultFileList.map((evaluationResultFile) => {
            if (evaluationResultFile.evaluationResultFileType === 1) {
              this.evalExportFileList = evaluationResultFile.fileInformationList
            } else if (evaluationResultFile.evaluationResultFileType === 2) {
              this.evalReportFileList = evaluationResultFile.fileInformationList
            }
          })
        }
        let evaluationResultSublists = this.evaluationResult.evaluationResultSublists
        if (evaluationResultSublists) {
          evaluationResultSublists.map((evaluationResultSublist) => {
            this.bidWinnerFileList[0] = evaluationResultSublist.fileInformation
          })
        }
      }
    },
    /** 查询中标结果公示信息 */
    getResultNotice () {
      this.resultFileList = []
      this.noticeFileList = []
      this.resultNotice = this.inviteOpenEvalFile.resultNotice
      if (this.resultNotice) {
        this.resultNotice.fileInformationList.map((file) => {
          // 公示附件
          if (file.businessType === '1') {
            this.resultFileList[0] = file
          }
          // 中标通知书
          if (file.businessType === '2') {
            this.noticeFileList[0] = file
          }
        })
      }
    },
    /** 查询中标结果公示内容 */
    getResultNoticeContent () {
      let obj = {
        sectionCode: this.sectionCode,
        resultNoticeType: 1
      }
      resnotice.getResultNotice(obj).then((res) => {
        this.resultNotice = res.data.resultNotice
        if (this.resultNotice) {
          if (this.$refs.ueditorRes && this.resultNotice.resultNoticeContent) {
            this.$refs.ueditorRes.setContent(this.resultNotice.resultNoticeContent)
          }
        }
      })
    },
    /** 查询中标候选人公示信息 */
    getPublicity () {
      this.publicity = {}
      this.publicityFileList = []
      this.publicity = this.inviteOpenEvalFile.publicity
      if (this.publicity) {
        this.publicityFileList = this.publicity.fileInformationList
      }
    },
    /** 查询中标候选人公示内容 */
    getPublicityContent () {
      this.publicity = {}
      let query = {
        sectionCode: this.sectionCode
      }
      candNotice.getOne(query).then((res) => {
        this.publicity = res.data.publicity
        if (this.publicity) {
          if (this.$refs.ueditor && this.publicity.publicityContent) {
            this.$refs.ueditor.setContent(this.publicity.publicityContent)
          }
        }
      })
    },
    getInviteOpenEvalFile () {
      this.inviteOpenEvalFile = {}
      let query = {
        archivesManagementCode: this.$route.query.code,
        sectionCode: this.sectionCode,
        fileType: this.fileType
      }
      inviteOpenEvalFile.getInviteFile(query).then((res) => {
        this.inviteOpenEvalFile = res.data.inviteOpenEvalFile
        if (this.inviteOpenEvalFile) {
          this.getOpenRecord()
          this.getEvaluationResult()
          this.getResultNotice()
          this.getPublicity()
          this.getArchivesFile()
        }
      })
    },
    /** 查询招标项目信息 */
    getTenderProject () {
      tenderproject.getOneByCode(this.tenderProjectCode).then((res) => {
        let tenderProject = res.data.tenderProject
        if (tenderProject) {
          this.biddingPlace = tenderProject.biddingPlace
        } else {
          return false
        }
      })
    }
  },
  /** 实例创建完成 */
  mounted () {
    this.getSections()
  },
  /** 组件注册 */
  components: {
    // 编辑器
    ueditor
  }
}
</script>
<style lang="less">
#invitefile-page {
  /** 折叠面板 */
  .collapse-warp {
    .el-collapse-item__header {
      font-size: 14px;
      font-weight: 800;
      padding-left: 20px;
      text-align: left;
      background: #F7F6FB;
    }
    .el-collapse-item__header.is-active {
      background: #ebeffe;
    }
    .el-collapse-item {
      margin-bottom: 10px;
    }
    .collspase-cont {
      padding-top: 20px;
      .el-icon-error {
        color: red;
      }
    }
    /** 折叠面板内容中的样式 */
    .collspase-cont {
      padding-top: 20px;
      .tabpane-btn {
        padding: 20px;
      }
      .tabs-pane-form {
        .el-form-item__content {
          text-align: left;
        }
      }
      .upload-demo {
        float: left;
      }
      .look-details, .down-details {
        margin-left: 20px;
      }
    }
    .info-table {
      width: 100%;
      border-collapse: collapse;
      border: 1px solid #eee;
      tr td {
        min-height: 50px;
        line-height: 50px;
        border: 1px solid #eee;
      }
      .el-form-item {
        margin-bottom: 0;
        .el-form-item__content {
          margin-left: 0 !important;
          text-align: left;
        }
      }
      tr td:nth-child(odd) {
        min-width: 100px;
        background: #F6F7F9;
      }
      tr td:nth-child(even) {
        min-width: 320px;
        text-align: left;
        padding: 0 10px;
      }
    }
  }
}
</style>
