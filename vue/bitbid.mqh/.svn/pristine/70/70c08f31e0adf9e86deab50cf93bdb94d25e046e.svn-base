<template>
  <!-- 投标报名 -->
  <div class="admin-layout" id="bidsignup-page">
    <div class="bidsignup-box">
      <div class="bidsignup-tit">
        <span>投标报名</span>
      </div>
      <div class="add-signup-btn">
        <el-button @click="handleAddBtn" type="warning" size="medium" icon="el-icon-plus"  v-if="this.operationCheckFlag && this.$store.getters.authUser.enterpriseType === 4 && Number(this.isLocked) === 0 && ((Number(this.isPrequalification) === 0 && Number(this.progressStage) < 5) || (Number(this.isPrequalification) === 1 && Number(this.progressStage) < 3))">添加投标人</el-button>
      </div>
      <div class="bidsignup-table">
        <!-- 折叠面板start -->
        <el-collapse accordion @change="getActiveSection">
          <el-collapse-item :name="''+index" v-for="(item, index) in sectionList" :key="index">
            <template slot="title">
              <span class="collapse-span">{{item.name}}</span>
            </template>
            <el-table
              border
              max-height="300"
              :data="bidderInfoList"
              :row-class-name="tableRowClassName"
              style="width: 100%; margin-top: 10px;">
              <el-table-column
                type="index"
                label="序号"
                align="center"
                :index="indexMethod"
                width="55">
              </el-table-column>
              <el-table-column
                label="投标人名称"
                prop="bidderName"
                header-align="center"
                align="left">
              </el-table-column>
              <el-table-column
                prop="bidderCreditCode"
                label="投标人代码"
                header-align="center"
                align="left">
              </el-table-column>
              <el-table-column
                prop="contactsName"
                label="联系人"
                align="center">
              </el-table-column>
              <el-table-column
                prop="contactsPhone"
                label="联系电话"
                align="center">
              </el-table-column>
              <el-table-column
                prop="registrationTime"
                label="报名时间"
                align="center"
                :formatter="filterDate">
              </el-table-column>
              <el-table-column
                prop="lastUpdateDate"
                label="维护时间"
                align="center"
                :formatter="filterDate">
              </el-table-column>
              <el-table-column
                label="操作"
                align="center"
                width="180">
                <template slot-scope="scope">
                  <el-button @click="handleTableClick(scope.row,'look')" type="text" size="medium">查看</el-button>
                  <el-button @click="handleTableClick(scope.row,'edit')" type="text" size="medium" v-if="operationCheckFlag && ($store.getters.authUser.enterpriseType === 4 && item.isLocked === 0 && Number(isLocked) === 0 && ((Number(isPrequalification) === 0 && Number(progressStage) < 5) || (Number(isPrequalification) === 1 && Number(progressStage) < 3)))">修改</el-button>
                  <el-button @click="handleTableClick(scope.row,'del')" type="text" size="medium" v-if="operationCheckFlag && ($store.getters.authUser.enterpriseType === 4 && item.isLocked === 0 && Number(isLocked) === 0 && ((Number(isPrequalification) === 0 && Number(progressStage) < 5) || (Number(isPrequalification) === 1 && Number(progressStage) < 3)))">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-collapse-item>
        </el-collapse>
        <!-- 折叠面板end -->
      </div>
    </div>

    <!-- 弹窗区域start -->
    <div class="bidup-dialog">
      <!-- 编辑/新增-弹窗start -->
      <el-dialog
        :title="handleTitle"
        :visible.sync="dialogVisible"
        width="40%"
        :before-close="handleClose">
        <div class="dialog-form">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
            <el-form-item label="标段选择:" prop="phaseSelect">
              <el-table
                ref="multipleTable"
                :data="phaseSelectTableData"
                border
                style="width: 100%"
                @selection-change="handleSelectionChange">
                <el-table-column
                  type="selection"
                  width="55"
                  :reserve-selection="true">
                </el-table-column>
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
            <div class="enterprise-info">
              <span>企业基本信息</span>
            </div>
            <el-form-item label="投标人名称:" prop="bidderName">
              <el-input v-model="ruleForm.bidderName" style="width: 260px"></el-input>
            </el-form-item>
            <el-form-item label="投标人地址:" prop="bidderAddress">
              <el-input v-model="ruleForm.bidderAddress" style="width: 260px"></el-input>
            </el-form-item>
            <el-form-item label="投标人邮箱:" prop="bidderEmail">
              <el-input v-model="ruleForm.bidderEmail" style="width: 260px"></el-input>
            </el-form-item>
            <el-form-item label="投标人代码:" prop="bidderCreditCode">
              <el-input v-model="ruleForm.bidderCreditCode" style="width: 260px"></el-input>
            </el-form-item>
            <el-form-item label="联系人:" prop="contactsName">
              <el-input v-model="ruleForm.contactsName" style="width: 260px"></el-input>
            </el-form-item>
            <el-form-item label="联系方式:" prop="contactsPhone">
              <el-input v-model="ruleForm.contactsPhone" style="width: 260px"></el-input>
            </el-form-item>
            <el-form-item label="附件:" prop="bidderFileInformationList">
              <el-upload
                multiple
                class="upload-demo"
                :action="upLoad"
                :auto-upload="true"
                :on-success="bidderHandleSuccess"
                :on-remove="bidderHandleRemove"
                :before-remove="bidderBeforeRemove"
                :file-list="bidderFileList">
                <el-button size="small" type="success" class="upload-btn">上传附件</el-button>
              </el-upload>
            </el-form-item>
            <div v-if="tenderProjectType === 1">
            <div class="enterprise-info">
              <span>项目经理（工程类）</span>
            </div>
            <el-form-item label="姓名:" prop="name">
              <el-input v-model="ruleForm.name" style="width: 260px"></el-input>
            </el-form-item>
            <el-form-item label="身份证号码:" prop="idCard">
              <el-input v-model="ruleForm.idCard" style="width: 260px" @blur="loseBlur"></el-input>
              <span v-if="showLookBtn">有在建项目,点击<el-button type="text" @click.native="clickLook">查看</el-button></span>
            </el-form-item>
            <el-form-item label="证书编号:" prop="certificateNumber">
              <el-input v-model="ruleForm.certificateNumber" style="width: 260px"></el-input>
            </el-form-item>
            <el-form-item label="专业:" prop="profession">
              <el-input v-model="ruleForm.profession" style="width: 260px"></el-input>
            </el-form-item>
            <el-form-item label="专业等级:" prop="professionalLevel">
              <el-input v-model="ruleForm.professionalLevel" style="width: 260px"></el-input>
            </el-form-item>
            <el-form-item label="附件:" prop="fileInformationList">
              <el-upload
                multiple
                class="upload-demo"
                :action="upLoad"
                :auto-upload="true"
                :on-success="handleSuccess"
                :on-remove="handleRemove"
                :before-remove="beforeRemove"
                :file-list="fileList">
                <el-button size="small" type="success" class="upload-btn">上传附件</el-button>
              </el-upload>
            </el-form-item>
            </div>
          </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="saveBidderInfo('ruleForm')">保 存</el-button>
          <el-button @click="cancel('ruleForm')">取 消</el-button>
        </span>
      </el-dialog>
      <!-- 编辑/新增-弹窗end -->

      <!-- 查看弹窗start -->
      <el-dialog
        title="查看"
        :visible.sync="lookDialogVisible"
        width="40%">
        <div class="dialog-form">
          <el-form :model="lookRuleForm" label-width="100px">
            <el-form-item label="标段选择:" prop="hasSelectedSection">
              <el-table
                :data="hasSelectedSection"
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
            <div class="enterprise-info">
              <span>企业基本信息</span>
            </div>
            <el-form-item label="投标人名称:" prop="bidderName">
              <span>{{lookRuleForm.bidderName}}</span>
            </el-form-item>
            <el-form-item label="投标人地址:" prop="bidderAddress">
              <span>{{lookRuleForm.bidderAddress}}</span>
            </el-form-item>
            <el-form-item label="投标人邮箱:" prop="bidderEmail">
              <span>{{lookRuleForm.bidderEmail}}</span>
            </el-form-item>
            <el-form-item label="投标人代码:" prop="bidderCreditCode">
              <span>{{lookRuleForm.bidderCreditCode}}</span>
            </el-form-item>
            <el-form-item label="联系人:" prop="contactsName">
              <span>{{lookRuleForm.contactsName}}</span>
            </el-form-item>
            <el-form-item label="联系方式:" prop="contactsPhone">
              <span>{{lookRuleForm.contactsPhone}}</span>
            </el-form-item>
            <el-form-item label="附件:" prop="fileInformationList">
              <span class="file-list" v-for="(item, index) in lookRuleForm.bidderFileInformationList" :key="index">
                <span>
                  <el-button
                    style="width: 80%"
                    type="text"
                    @click="downloadFile(item.fileName,item.relativePath)">
                    <span style="display: inline-block;width: 100%;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
                      {{item.fileName}}
                    </span>
                  </el-button>
                </span>
              </span>
            </el-form-item>
            <div v-if="tenderProjectType === 1">
            <div class="enterprise-info">
              <span>项目经理（工程类）</span>
            </div>
            <el-form-item label="姓名:" prop="name">
              <span>{{lookRuleForm.name}}</span>
            </el-form-item>
            <el-form-item label="身份证号码:" prop="idCard">
              <span>{{lookRuleForm.idCard}}</span>
            </el-form-item>
            <el-form-item label="证书编号:" prop="certificateNumber">
              <span>{{lookRuleForm.certificateNumber}}</span>
            </el-form-item>
            <el-form-item label="专业:" prop="profession">
              <span>{{lookRuleForm.profession}}</span>
            </el-form-item>
            <el-form-item label="专业等级:" prop="professionalLevel">
              <span>{{lookRuleForm.professionalLevel}}</span>
            </el-form-item>
            <el-form-item label="附件:" prop="fileInformationList">
              <span class="file-list" v-for="(item, index) in lookRuleForm.fileInformationList" :key="index">
                <span>
                  <el-button
                    style="width: 80%"
                    type="text"
                    @click="downloadFile(item.fileName,item.relativePath)">
                    <span style="display: inline-block;width: 100%;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
                      {{item.fileName}}
                    </span>
                  </el-button>
                </span>
              </span>
            </el-form-item>
            </div>
          </el-form>
        </div>
      </el-dialog>
      <!-- 查看弹窗end -->

      <!-- 在建项目-点击查看start -->
      <el-dialog
        title="提醒"
        :visible.sync="remindDialogVisible"
        width="40%">
        <div class="dialog-form">
          <el-form label-width="100px">
            <el-form-item label="参与项目情况:">
              <el-table
                :data="partProTableData"
                border
                style="width: 100%">
                <el-table-column
                  prop="projectName"
                  label="招标项目名称"
                  align="left">
                </el-table-column>
                <el-table-column
                  prop="sectionName"
                  label="标段名称"
                  align="left">
                </el-table-column>
                <el-table-column
                  prop="confirmTime"
                  label="结果确认日期"
                  align="left">
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
      <!-- 在建项目-点击查看start -->
    </div>
    <!-- 弹窗区域end -->
  </div>
</template>
<script>
import {notice, section, bidderInfo, bidWinner, tenderproject, noticeProgress} from 'api/index'
import {isvalidPhone, isvalidId} from '../../../../assets/js/validate'
import {downloadFile, commonJs} from 'common/js/common'
import {formatDate} from 'common/js/date.js'
export default {
  data () {
    let validPhone = (rule, value, callback) => {
      if (!value) {

      } else if (!isvalidPhone(value)) {
        callback(new Error('请输入正确的11位手机号码'))
      } else {
        callback()
      }
    }
    let validId = (rule, value, callback) => {
      if (!value) {

      } else if (!isvalidId(value)) {
        callback(new Error('请输入正确的身份证号'))
      } else {
        callback()
      }
    }
    return {
      remindDialogVisible: false, // 提醒弹窗
      partProTableData: [], // 参与项目数据-弹窗
      showLookBtn: false,
      handleTitle: '添加',
      collapseActiveName: '1',
      tableData: [{bidName: '投标人姓名'}],
      dialogVisible: false,
      phaseSelectTableData: [], // 标段选择数据
      ruleForm: {},
      rules: {
        bidderName: [
          { required: true, message: '请输入投标人名称', trigger: 'change' },
          {min: 1, max: 200, message: '请输入1~200个字符', trigger: ['blur', 'change']}
        ],
        bidderAddress: [
          { required: true, message: '请输入投标人地址', trigger: 'change' },
          {min: 1, max: 200, message: '请输入1~200个字符', trigger: ['blur', 'change']}
        ],
        bidderEmail: [
          { required: true, message: '请输入投标人邮箱', trigger: 'change' },
          {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
        ],
        bidderCreditCode: [
          { required: true, message: '请输入投标人代码', trigger: 'change' },
          {min: 1, max: 18, message: '请输入1~18个字符', trigger: ['blur', 'change']}
        ],
        contactsName: [
          { required: true, message: '请输入联系人名称', trigger: 'blur' },
          {min: 1, max: 20, message: '请输入1~20个字符', trigger: ['blur', 'change']}
        ],
        contactsPhone: [
          { required: true, message: '请输入联系方式', trigger: 'blur' },
          {validator: validPhone, trigger: 'blur'}
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'change' },
          {min: 1, max: 200, message: '请输入1~200个字符', trigger: ['blur', 'change']}
        ],
        idCard: [
          { required: true, message: '请输入身份证号', trigger: 'change' },
          {validator: validId, trigger: 'change'}
        ],
        certificateNumber: [
          { required: true, message: '请输入证书编号', trigger: 'change' }
        ],
        profession: [
          { required: true, message: '请输入专业', trigger: 'change' }
        ],
        professionalLevel: [
          { required: true, message: '请输入专业等级', trigger: 'change' }
        ]
      },
      upLoad: commonJs.fileUploadUrl,
      fileList: [], // 上传列表-数据
      bidderFileList: [], // 上传列表-数据
      lookDialogVisible: false, // 查看弹窗
      lookRuleForm: {}, // 查看表单
      currentPage: 1,
      sectionList: [], // 标段信息
      hasSelectedSection: [], // 选中的标段信息
      bidderInfoList: [],
      previousBidderInfo: {}, // 上次选中的投标人信息
      currentDisplaySection: {}, // 当前选中的标段信息
      tenderProjectType: '',
      isPrequalification: '',
      isLocked: '',
      progressStage: ''
    }
  },
  props: ['noticeCode', 'callbackdata', 'operationCheckFlag'],
  filters: {
  },
  computed: {
    getNoticeCode () {
      return this.noticeCode
    }
  },
  watch: {
    getNoticeCode () {
      this.getSectionsInfo()
      this.getNoticeInfo()
      this.getTenderProjectInfo()
    }
  },
  created () {
    this.getSectionsInfo()
    this.getNoticeInfo()
    this.getTenderProjectInfo()
  },
  /** 方法集 */
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 获取选中项目的详细信息 */
    getTenderProjectInfo () {
      tenderproject.getOneByCode(this.callbackdata).then((res) => {
        this.tenderProjectType = res.data.tenderProject.tenderProjectType
        this.isPrequalification = res.data.tenderProject.isPrequalification
      })
    },
    /** 获取公告是否上锁 */
    getNoticeInfo () {
      notice.getByCode(this.noticeCode).then((res) => {
        this.isLocked = res.data.notice.isLocked
      })
      noticeProgress.getOne({
        tenderProjectCode: this.callbackdata,
        noticeCode: this.noticeCode
      }).then((res) => {
        this.progressStage = res.data.noticeProgress.progressStage
      })
    },
    getActiveSection (index) {
      if (index) {
        this.currentDisplaySection = this.sectionList[index]
        this.getAllBidderInfo(this.sectionList[index])
      }
    },
    /** 格式化申请时间 */
    filterDate (row, column) {
      let date = ''
      if (row.registrationTime && column.property === 'registrationTime') {
        date = new Date(row.registrationTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm')
      } else if (row.lastUpdateDate && column.property === 'lastUpdateDate') {
        date = new Date(row.lastUpdateDate)
        return formatDate(date, 'yyyy-MM-dd hh:mm')
      }
    },
    /** 获取选中项目的详细信息 */
    getSectionsInfo () {
      this.sectionList = []
      this.phaseSelectTableData = []
      notice.getByCode(this.noticeCode).then((res) => {
        if (res.data.notice.sectionList) {
          res.data.notice.sectionList.forEach(section => {
            section.isLocked = 0
          })
        }
        if (res.data.notice.lockedSectionList) {
          res.data.notice.lockedSectionList.forEach(section => {
            section.isLocked = 1
          })
        }
        this.sectionList = res.data.notice.sectionList.concat(res.data.notice.lockedSectionList)
        this.phaseSelectTableData = res.data.notice.sectionList
      })
    },
    /** 新增投标人 */
    saveBidderInfo (formName) {
      if (this.hasSelectedSection.length < 1) {
        this.$message.error('选择标段信息！')
        return false
      }
      // 投标人代码验证
      bidderInfo.getByByCreditCode(this.ruleForm.bidderCreditCode).then((res) => {
        if (res.data.isInBlacklist === 1) {
          this.$message.error('该投标人处于黑名单中，不能进行添加！')
          return false
        } else {
          this.$refs[formName].validate((vaild) => {
            if (vaild) {
              this.ruleForm.noticeCode = this.noticeCode
              this.ruleForm.sectionList = this.hasSelectedSection
              let infos = []
              this.fileList.forEach(file => infos.push(file.response.fileInformation))
              if (this.tenderProjectType === 1) {
                let obj = {
                  name: this.ruleForm.name,
                  idCard: this.ruleForm.idCard,
                  certificateNumber: this.ruleForm.certificateNumber,
                  profession: this.ruleForm.profession,
                  professionalLevel: this.ruleForm.professionalLevel,
                  fileInformationList: infos
                }
                this.ruleForm.bidderProjectManager = obj
              }
              let bidderInfos = []
              this.bidderFileList.forEach(file => bidderInfos.push(file.response.fileInformation))
              this.ruleForm.fileInformationList = bidderInfos
              bidderInfo.save(this.ruleForm).then(() => {
                this.dialogVisible = false
                this.getAllBidderInfo(this.currentDisplaySection)
                this.cancel('ruleForm')
              })
            } else {
              return false
            }
          })
        }
      })
    },
    /** 获取指定标段下全部投标人信息 */
    getAllBidderInfo (data) {
      bidderInfo.getByRelatedCode(this.noticeCode, data.code).then((res) => {
        this.bidderInfoList = res.data.bidderInfoList
      })
    },
    handleClose () {
      this.cancel('ruleForm')
    },
    /** 关闭弹框 */
    cancel (formName) {
      this.dialogVisible = false
      this.$refs[formName].resetFields()
      this.$refs.multipleTable.clearSelection()
      this.fileList = []
      this.bidderFileList = []
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 添加投标人 */
    handleAddBtn () {
      this.handleTitle = '添加'
      this.dialogVisible = true
      this.ruleForm = {}
      // this.$refs['ruleForm'].resetFields()
      this.fileList = []
      this.getTenderProjectInfo()
    },
    /** 表格操作 */
    handleTableClick (row, type) {
      if (Object.is(type, 'edit')) {
        this.handleTitle = '编辑'
        this.dialogVisible = true
        this.getBidderInfo(row, type)
      } else if (Object.is(type, 'look')) {
        this.lookDialogVisible = true
        this.getBidderInfo(row, type)
      } else if (Object.is(type, 'del')) {
        this.deleteClick(row)
      }
    },
    // 设置表格上展示的项目经理数据（编辑、查看）
    getBidderProjectManagerInfo (res, type) {
      let obj = {
        name: res.data.bidderInfo.bidderProjectManager.name,
        idCard: res.data.bidderInfo.bidderProjectManager.idCard,
        certificateNumber: res.data.bidderInfo.bidderProjectManager.certificateNumber,
        profession: res.data.bidderInfo.bidderProjectManager.profession,
        professionalLevel: res.data.bidderInfo.bidderProjectManager.professionalLevel
      }
      if (Object.is(type, 'edit')) {
        if (res.data.bidderInfo.bidderProjectManager.fileInformationList) {
          this.fileList = []
          let fileInformationList = res.data.bidderInfo.bidderProjectManager.fileInformationList
          fileInformationList.forEach(fileInformation => {
            let file = {
              name: fileInformation.fileName,
              response: {
                fileInformation: fileInformation
              }
            }
            this.fileList.push(file)
          })
        }
      } else if (Object.is(type, 'look')) {
        obj.fileInformationList = res.data.bidderInfo.bidderProjectManager.fileInformationList
      }
      return obj
    },
    /** 获取单条投标人信息 */
    getBidderInfo (row, type) {
      if (Object.is(type, 'edit')) {
        bidderInfo.getOne(row.objectId).then((res) => {
          this.ruleForm = res.data.bidderInfo
          this.ruleForm.objectId = row.objectId
          if (res.data.bidderInfo.fileInformationList) {
            this.bidderFileList = []
            let fileInformationList = res.data.bidderInfo.fileInformationList
            fileInformationList.forEach(fileInformation => {
              let file = {
                name: fileInformation.fileName,
                response: {
                  fileInformation: fileInformation
                }
              }
              this.bidderFileList.push(file)
            })
          }
          // 工程类招标项目报名信息必须填写项目经理信息，货物类、服务类不用填写项目经理信息
          if (this.tenderProjectType === 1) {
            Object.assign(this.ruleForm, this.getBidderProjectManagerInfo(res, 'edit'))
          }
          // 通过公告编号和投标人编号查询标段信息
          let selectedSections = []
          section.getByRelatedCode(this.noticeCode, row.code).then((res) => {
            selectedSections = res.data.sectionList
          })
          // 给已选择的标段设置值
          let sectionIndexList = this.getCheckedSectionIndexList(selectedSections)
          let checkedSectionList = []
          sectionIndexList.forEach(index => checkedSectionList.push(this.phaseSelectTableData[index]))
          this.toggleSelection(checkedSectionList)
        })
      } else if (Object.is(type, 'look')) {
        bidderInfo.getOne(row.objectId).then((res) => {
          this.lookRuleForm = res.data.bidderInfo
          this.lookRuleForm.bidderFileInformationList = res.data.bidderInfo.fileInformationList
          this.lookRuleForm.fileInformationList = []
          if (this.tenderProjectType === 1) {
            Object.assign(this.lookRuleForm, this.getBidderProjectManagerInfo(res, 'look'))
          }
          // 通过公告编号和投标人编号查询标段信息
          section.getByRelatedCode(this.noticeCode, row.code).then((res) => {
            this.hasSelectedSection = res.data.sectionList
          })
        })
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
    /** 标段选择-多选 */
    handleSelectionChange (data) {
      this.hasSelectedSection = []
      this.hasSelectedSection = data
    },
    /** 上传 */
    handleSuccess (response, file, fileList) {
      this.fileList = fileList
    },
    handleRemove (file, fileList) {
      this.fileList = fileList
    },
    beforeRemove (file, fileList) {
      return this.$confirm(`确定移除${file.name}？`)
    },
    /** 上传 */
    bidderHandleSuccess (response, file, fileList) {
      this.bidderFileList = fileList
    },
    bidderHandleRemove (file, fileList) {
      this.bidderFileList = fileList
    },
    bidderBeforeRemove (file, fileList) {
      return this.$confirm(`确定移除${file.name}？`)
    },
    /** 文件下载 */
    downloadFile,
    /** 失去焦点-事件 */
    loseBlur ({event: Event}) {
      this.showLookBtn = false
      let pass = false
      bidWinner.getOne(this.ruleForm.idCard).then((res) => {
        this.partProTableData = res.data.bidWinnerList
        this.partProTableData.forEach(item => {
          item.projectName = item.tenderProject.selectApply.projectName
        })
        if (this.partProTableData.length > 0) {
          pass = true
        }
        // 如果有在建项目，返回true
        if (pass) {
          this.showLookBtn = true
        }
      })
    },
    /** 在建项目点击-查看 */
    clickLook () {
      // 打开弹窗
      this.remindDialogVisible = true
    },
    /** 删除-提示 */
    deleteClick (row) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 通过公告编号和投标人编号查询标段信息
        let selectedSections = []
        section.getByRelatedCode(this.noticeCode, row.code).then((res) => {
          selectedSections = res.data.sectionList
          // 判断该投标人下是否还有其他标段项目，如果有则要先保存，再删除
          if (selectedSections.length > 1) {
            for (let i = 0; i < selectedSections.length; i++) {
              if (selectedSections[i].objectId === this.currentDisplaySection.objectId) {
                selectedSections.splice(i, 1)
              }
            }
            let bidder = {
              objectId: row.objectId,
              code: row.code,
              noticeCode: this.noticeCode,
              sectionList: selectedSections
            }
            bidderInfo.save(bidder).then(() => {
              this.getAllBidderInfo(this.currentDisplaySection)
            })
          } else {
            bidderInfo.delete(row.objectId).then(() => {
              this.getAllBidderInfo(this.currentDisplaySection)
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  },
  /** 实例创建完成 */
  mounted () {
  }
}
</script>
<style lang="less">
#bidsignup-page {
  .bidsignup-box {
    .bidsignup-tit {
      height: 50px;
      line-height: 50px;
      text-align:left;
      padding: 0 20px;
      background:#F7F6FB;
      span {
        font-weight: 800;
      }
    }
    .add-signup-btn {
      text-align: left;
      padding: 10px 20px 10px;
    }
    .bidsignup-table {
      padding: 0 20px 20px;
      /** 折叠面板 */
      .el-collapse-item {
        margin-bottom: 10px;
      }
      .el-collapse-item__header {
        padding: 0 20px;
        text-align: left;
        background: #F7F6FB;
        .collapse-span {
          display: block;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
      }
      .el-collapse-item__header.is-active {
        background: #ebeffe;
      }
    }
  }
  /** 弹窗 */
  .bidup-dialog {
    .dialog-form {
      .enterprise-info {
        height: 30px;
        line-height: 30px;
        background: #eee;
        text-align: left;
        padding-left: 25px;
        margin-bottom: 20px;
        font-weight: 800;
      }
      .el-form-item__content {
        text-align: left;
      }
    }
    .el-dialog__footer {
      text-align: center;
    }
  }
}
</style>
