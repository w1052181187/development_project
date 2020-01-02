<template>
  <!-- 结果公示 -->
  <div class="admin-layout" id="resnotice-page">
    <div class="resnotice-cont">
      <div class="resnotice-tit">
        <span>结果公示</span>
      </div>
      <div class="resnitice-box">
        <!-- 折叠面板 -->
        <el-collapse v-model="collapseActiveName" accordion>
          <el-collapse-item v-for="(section,index) in sectionList" :key="index" :name=section.code>
            <template slot="title">
              <span @click="getDataBySection(section, index)" class="collapse-span" style="width:100%;display:inherit;position:relative;z-index:1;">
                {{section.name}}
              </span>
            </template>
            <div></div>
            <el-form :model="ruleFormProInfo" :rules="rulesProInfo" ref="ruleFormProInfo" label-width="140px" style="margin-top: 20px;">
              <el-form-item label="项目信息:">
                <tree-grid
                  border
                  :columns="columns"
                  :tree-structure="true"
                  :data="ruleFormProInfo.suggestedPlanTableData">
                </tree-grid>
              </el-form-item>
            </el-form>
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="140px" style="margin-top: 20px;">
              <el-form-item label="中标人:" prop="bidWinner">
                <el-button type="warning" @click="confirmBidWinner" class="confirm-btn" v-if="dialogEdit">确定中标人</el-button>
                <el-table
                  class="form-table"
                  border
                  :data="bidTableData"
                  :row-class-name="tableRowClassName"
                  style="width: 100%">
                  <el-table-column
                    label="中标人名称"
                    prop="bidderName"
                    align="center">
                  </el-table-column>
                  <el-table-column
                    label="投标报价（万元）"
                    prop="bidPrice"
                    align="center">
                  </el-table-column>
                  <el-table-column
                    label="工期/交货期（天）"
                    prop="projectTimeLimit"
                    align="center">
                  </el-table-column>
                  <el-table-column
                    label="得分"
                    prop="score"
                    align="center">
                  </el-table-column>
                </el-table>
              </el-form-item>
              <el-form-item label="中标通知书:" prop="noticeFileList" ref="noticeFileList">
                <el-upload
                  class="upload-demo"
                  :action="upLoad"
                  :on-success="noticeHandleSuccess"
                  :limit="1"
                  :show-file-list="false"
                  :before-upload="beforeUpload"
                  :file-list="noticeFileList"
                  v-if="dialogEdit">
                  <el-button size="small" type="success" class="upload-btn">上传附件</el-button>
                </el-upload>
                <!-- 上传附件列表 -->
                <div class="apply-upload">
                  <el-row>
                    <el-col :span="12">
                      <span class="file-list" v-for="(item, index) in noticeFileList" :key="index">
                        <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                        <i class="el-icon-error upload-file" @click="noticeClickDel(index)" v-if="dialogEdit"></i>
                      </span>
                    </el-col>
                  </el-row>
                </div>
              </el-form-item>
              <el-form-item label="公示附件:" >
                <el-upload
                  class="upload-demo"
                  :action="upLoad"
                  :on-success="handleSuccess"
                  :limit="1"
                  :show-file-list="false"
                  :before-upload="beforeUpload"
                  :file-list="fileList"
                  v-if="dialogEdit">
                  <el-button size="small" type="success" class="upload-btn">上传附件</el-button>
                </el-upload>
                <!-- 上传附件列表 -->
                <div class="apply-upload">
                  <el-row>
                    <el-col :span="12">
                      <span class="file-list" v-for="(item, index) in fileList" :key="index">
                        <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                        <i class="el-icon-error upload-file" @click="clickDel(index)" v-if="dialogEdit"></i>
                      </span>
                    </el-col>
                  </el-row>
                </div>
              </el-form-item>
              <div class="ueditor-box">
                <el-form-item label="公示内容:" prop="resultNoticeContent" ref="resultNoticeContent">
                  <div v-if="lazyRender">
                    <ueditor
                      ref="ueditor"
                      :content="resultNoticeContent"
                      class="ueditor"
                      @eventFromChild="eventFromChild"
                      :readonly="readonly">
                    </ueditor>
                  </div>
                </el-form-item>
              </div>
              <el-form-item label="发布媒介:" prop="resultNoticeMedia">
                <el-input v-model="ruleForm.resultNoticeMedia" style="width: 340px;" v-if="dialogEdit"></el-input>
                <span v-else>{{ruleForm.resultNoticeMedia}}</span>
              </el-form-item>
              <el-row :gutter="100">
                <!--<el-col :span="8">
                  <el-form-item label="质保金(万元):" prop="warrantyGold">
                    <el-input v-model="ruleForm.warrantyGold" style="width: 340px;" v-if="dialogEdit"></el-input>
                    <span v-else>{{ruleForm.warrantyGold}}</span>
                  </el-form-item>
                </el-col>-->
                <el-col :span="8">
                  <el-form-item label="公示时间:" prop="resultNoticeTime">
                    <el-date-picker
                      v-if="dialogEdit"
                      style="width: 340px;"
                      v-model="ruleForm.resultNoticeTime"
                      type="date"
                      placeholder="选择日期">
                    </el-date-picker>
                    <span v-else>{{ruleForm.resultNoticeTime | filterTime}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <!--<el-row :gutter="100">
                <el-col :span="8">
                  <el-form-item label="付款方式:" prop="payMethod">
                    <el-input v-model="ruleForm.payMethod" style="width: 340px;" v-if="dialogEdit"></el-input>
                    <span v-else>{{ruleForm.payMethod}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="质保期:" prop="warrantyPeriod">
                    <el-input v-model="ruleForm.warrantyPeriod" style="width: 340px;" v-if="dialogEdit"></el-input>
                    <span v-else>{{ruleForm.warrantyPeriod}}</span>
                  </el-form-item>
                </el-col>
              </el-row>-->
              <!--<el-row :gutter="100">
                <el-col :span="8">
                  <el-form-item label="项目经理:" prop="projectManager" v-if="tenderProjectType === 1">
                    <el-input v-model="ruleForm.projectManager" style="width: 340px;" v-if="dialogEdit"></el-input>
                    <span v-else>{{ruleForm.projectManager}}</span>
                  </el-form-item>
                  <el-form-item label="结算依据:" prop="basisSettlement" v-if="tenderProjectType === 1">
                    <el-input v-model="ruleForm.basisSettlement" style="width: 340px;" v-if="dialogEdit"></el-input>
                    <span v-else>{{ruleForm.basisSettlement}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="规格、型号、数量:" prop="goodsExplain" v-if="tenderProjectType === 2">
                    <el-input v-model="ruleForm.goodsExplain" style="width: 340px;" v-if="dialogEdit"></el-input>
                    <span v-else>{{ruleForm.goodsExplain}}</span>
                  </el-form-item>
                  <el-form-item label="供货期:" prop="supplyPeriod" v-if="tenderProjectType === 2">
                    <el-input v-model="ruleForm.supplyPeriod" style="width: 340px;" v-if="dialogEdit"></el-input>
                    <span v-else>{{ruleForm.supplyPeriod}}</span>
                  </el-form-item>
                  <el-form-item label="服务期:" prop="serverPeriod" v-if="tenderProjectType === 3">
                    <el-input v-model="ruleForm.serverPeriod" style="width: 340px;" v-if="dialogEdit"></el-input>
                    <span v-else>{{ruleForm.serverPeriod}}</span>
                  </el-form-item>
                </el-col>
              </el-row>-->
              <el-form-item class="form-last">
                <el-button type="primary" @click="saveForm('ruleForm', index)" v-if="dialogEdit">保 存</el-button>
              <el-button @click="$router.go(-1)" v-if="dialogEdit">取 消</el-button>
              </el-form-item>
            </el-form>
          </el-collapse-item>
        </el-collapse>
        <div class="bottom-submit-btn">
          <el-button type="success" @click="submitForm" v-if="dialogEdit">提 交</el-button>
        </div>
      </div>
    </div>

    <!-- 弹窗区域start -->
    <div class="dialog-box">
      <el-dialog
        title="中标人设置"
        :visible.sync="dialogVisible"
        @close="cancelClick"
        width="50%">
        <div class="dialog-box-form">
          <el-form :model="dialogRuleForm" label-width="100px">
            <el-form-item label="招标项目名称:" prop="projectName">
              <span>{{dialogRuleForm.projectName}}</span>
            </el-form-item>
            <el-form-item label="招标项目编号:" prop="codeUser">
              <span>{{dialogRuleForm.codeUser}}</span>
            </el-form-item>
            <el-form-item label="标段名称:" prop="sectionName">
              <span>{{dialogRuleForm.sectionName}}</span>
            </el-form-item>
            <el-table
              border
              :data="dialogTableData"
              style="width: 100%">
              <el-table-column
                type="index"
                label="序号"
                :index="indexDialogMethod"
                width="55">
              </el-table-column>
              <el-table-column
                label="中标候选人名称"
                prop="bidderName"
                align="left">
              </el-table-column>
              <el-table-column
                label="投标报价（万元）"
                prop="bidPrice"
                align="left">
              </el-table-column>
              <el-table-column
                label="工期/交货期（天）"
                prop="projectTimeLimit"
                align="left">
              </el-table-column>
              <el-table-column
                label="得分"
                prop="score"
                align="left">
              </el-table-column>
              <el-table-column
                label="是否中标"
                prop="winBidYn"
                align="left">
                <template slot-scope="scope">
                  <el-radio v-model="winBidYn" :label="scope.$index" @change.native="getCurrentRow(scope.$index)">&nbsp;</el-radio>
                </template>
              </el-table-column>
            </el-table>
          </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="saveClick">保 存</el-button>
          <el-button @click="cancelClick">取 消</el-button>
        </span>
      </el-dialog>
    </div>
    <!-- 弹窗区域end -->
  </div>
</template>
<script>
// 树结构表格
import treeGrid from '@/components/treetable/TreeProInfo.vue'
// 引入编辑器
import ueditor from '@/components/ueditor/ueditor.vue'
import {bidfile, tenderproject, resnotice, candNotice, openbid, section, investmanage, suggestedPlanSectionInfo} from 'api/index'
import {commonJs, downloadFile} from 'common/js/common'
import {formatDate} from 'common/js/date'
export default {
  /** 组件注册 */
  components: {
    // 编辑器
    ueditor,
    // 树结构-表格
    treeGrid
  },
  data () {
    var checkNum = (rule, value, callback) => {
      if (value && (!(/^\d{1,14}(\.\d{1,6})?$/).test(value))) {
        callback(new Error('输入小数点前不超14位且小数点后不超6位的数字'))
      } else {
        callback()
      }
    }
    var checkAmount = (rule, value, callback) => {
      if (value && (!(/^[1-9]\d{0,4}$/).test(value))) {
        callback(new Error('中标数量为不超过五位的数值'))
      } else {
        callback()
      }
    }
    var checkPrice = (rule, value, callback) => {
      if (value && (!(/^\d{1,18}(\.\d{1,4})?$/).test(value))) {
        callback(new Error('输入小数点前不超18位且小数点后不超4位的数字'))
      } else {
        callback()
      }
    }
    return {
      columns: [
        {
          text: '项目名称',
          value: 'label',
          width: 230
        }
      ],
      ruleFormProInfo: {
        suggestedPlanTableData: [],
        rulesProInfo: ''
      },
      rulesProInfo: {
        bidWinningAmount: [
          { required: true, message: '请填写中标数量', trigger: 'blur' },
          {validator: checkAmount}
        ],
        bidWinningPrice: [
          { required: true, message: '请填写中标价', trigger: 'blur' },
          {validator: checkPrice}
        ]
      },
      lazyRender: false,
      upLoad: commonJs.fileUploadUrl,
      tenderProjectType: null, // 招标项目类型（1.工程 2.货物 3.服务）
      resultNoticeContent: '', // 公告内容
      winBidYn: null, // 是否中标-弹窗
      collapseActiveName: '',
      ruleForm: {},
      rules: {
        noticeFileList: [
          {required: true, message: '请上传中标通知书', trigger: 'blur'}
        ],
        resultNoticeContent: [
          {required: true, message: '请输入公示内容', trigger: 'blur'}
        ],
        resultNoticeMedia: [
          {required: true, message: '请填写发布媒介', trigger: 'blur'},
          { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }
        ],
        warrantyGold: [
          {required: true, message: '请填写质保金', trigger: 'blur'},
          {validator: checkNum}
        ],
        resultNoticeTime: [
          {required: true, message: '请选择公示时间', trigger: 'change'}
        ],
        payMethod: [
          {required: true, message: '请填写付款方式', trigger: 'blur'},
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
        ],
        warrantyPeriod: [
          {required: true, message: '请填写质保期', trigger: 'blur'},
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
        ],
        projectManager: [
          {required: true, message: '请填写项目经理（工程）', trigger: 'blur'},
          { min: 1, max: 20, message: '请输入1~20个字符', trigger: 'blur' }
        ],
        basisSettlement: [
          {required: true, message: '请填写结算依据（工程）', trigger: 'blur'},
          { min: 1, max: 100, message: '请输入1~100个字符', trigger: 'blur' }
        ],
        goodsExplain: [
          {required: true, message: '请填写规格、型号、数量（货物）', trigger: 'blur'},
          { min: 1, max: 500, message: '请输入1~500个字符', trigger: 'blur' }
        ],
        supplyPeriod: [
          {required: true, message: '请填写供货期（货物）', trigger: 'blur'},
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
        ],
        serverPeriod: [
          {required: true, message: '请填写服务期（服务）', trigger: 'blur'},
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
        ]
      },
      fileList: [], // 公示附件
      noticeFileList: [], // 中标通知书
      sectionList: [], // 标段-列表
      dialogVisible: false, // 弹窗
      dialogTableData: [], // 弹窗表格数据
      dialogRuleForm: {}, // 弹窗表单
      bidTableData: [], // 中标候选人-表格数据
      currentPage: 1,
      index: 0,
      selectIndex: null,
      dialogEdit: false, // 是否可编辑
      readonly: false,
      currentProgressStage: 7, // 结果公示阶段
      flag: 0 // 0-新增 1-修改
    }
  },
  computed: {
  },
  created () {
    this.getNoticeProgress()
  },
  watch: {
    readonly: function (newV, oldV) {
      if (newV) {
        this.readonly = newV
      }
    }
  },
  filters: {
    filterTime (val) {
      if (val) {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return ''
      }
    }
  },
  props: {
    noticeCode: String,
    operationCheckFlag: Boolean
  },
  /** 方法集 */
  methods: {
    /** 设置按钮切换 */
    setTogglebutton (arr, index, type) {
      let newArr = []
      // 将设置好的数据拷贝一份
      newArr = arr.map((item, current) => {
        if (index === current) {
          if (Object.is(type, 'edit')) {
            item.flag = 1
          } else if (Object.is(type, 'save')) {
            item.flag = 0
          }
          return item
        } else {
          return item
        }
      })
      return newArr
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    indexDialogMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 确定中标人 */
    confirmBidWinner () {
      // 打开弹窗
      this.dialogVisible = true
    },
    /** 校验附件 */
    validFile () {
      if (this.noticeFileList.length > 0) {
        this.$set(this.ruleForm, 'noticeFileList', 'noticeFileList')
      } else {
        this.$set(this.ruleForm, 'noticeFileList', '')
      }
      if (this.ruleForm.noticeFileList) {
        this.$refs['noticeFileList'][this.index].clearValidate()
      }
    },
    /** 失去焦点校验 */
    eventFromChild (val) {
      if (Object.is(val, 'blur')) {
        this.validContent()
      }
    },
    /** 校验公示内容 */
    validContent () {
      let resultNoticeContent = this.$refs.ueditor[this.index].getContent()
      if (resultNoticeContent) {
        this.$set(this.ruleForm, 'resultNoticeContent', resultNoticeContent)
      } else {
        this.$set(this.ruleForm, 'resultNoticeContent', '')
      }
      if (this.ruleForm.resultNoticeContent) {
        this.$refs['resultNoticeContent'][this.index].clearValidate()
      }
    },
    /** 下载附件 */
    downloadFile,
    /** 公示附件格式为pdf */
    beforeUpload (file) {
      const isPDF = file.type === 'application/pdf'
      if (!isPDF) {
        this.$message.error('上传文件只能为PDF格式！')
      }
      return isPDF
    },
    /** 公示附件 */
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
        this.fileList.map((file) => {
          file.businessType = '1'
        })
      }
      this.validFile()
    },
    /** 中标通知书 */
    noticeHandleSuccess (response, file, fileList) {
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
        this.noticeFileList = fileInformations // 获得目前项目中所有的附件信息
        this.noticeFileList.map((file) => {
          file.businessType = '2'
        })
      }
      this.validFile()
    },
    /** 删除公示附件 */
    clickDel (index) {
      this.fileList.splice(index, 1)
      this.validFile()
    },
    /** 删除中标通知书 */
    noticeClickDel (index) {
      this.noticeFileList.splice(index, 1)
      this.validFile()
    },
    /** 折叠面板点击-事件 */
    getDataBySection (section, index) {
      this.ruleForm = {}
      this.bidTableData = []
      this.dialogTableData = []
      this.fileList = []
      this.noticeFileList = []
      this.collapseActiveName = section.code
      this.dialogRuleForm.sectionName = section.name
      this.index = index
      this.getTenderProject()
      this.getResultNoticeBySection(section.code, index)
    },
    /** 保存-表单 */
    saveForm (val, index) {
      this.validFile()
      this.validContent()
      // 校验
      let validResult = this.validForm(val, index)
      // 通过校验
      if (validResult) {
        this.ruleForm.tenderProjectCode = this.$route.query.tenderProjectCode
        this.ruleForm.noticeCode = this.noticeCode
        this.ruleForm.sectionCode = this.collapseActiveName
        this.ruleForm.resultNoticeType = 1
        this.ruleForm.resultNoticeChangeCount = 0
        this.ruleForm.isAllowOperate = 0
        this.ruleForm.resultNoticeContent = this.$refs.ueditor[index].getContent()
        this.ruleForm.fileInformationList = []
        if (this.fileList.length > 0) {
          this.ruleForm.fileInformationList.push(this.fileList[0])
        }
        this.ruleForm.fileInformationList.push(this.noticeFileList[0])
        if (!this.ruleForm.bidWinner) {
          this.ruleForm.bidWinner = {}
          this.ruleForm.bidWinner.projectCode = this.dialogRuleForm.codeUser
        }
        if (this.bidTableData.length > 0) {
          if (this.bidTableData[0]) {
            this.ruleForm.bidWinner.winCandidateCode = this.bidTableData[0].code
            this.ruleForm.bidWinner.bidderCode = this.bidTableData[0].bidderCode
          }
        }
        this.updateSectionInfos()
        resnotice.saveResultNotice(this.ruleForm).then(() => {
          this.getNoticeProgress()
        })
      } else {
        return false
      }
    },
    /** 校验表单 */
    validForm (formName, index) {
      let result = false
      this.$refs[formName][index].validate((valid) => {
        if (valid) {
          result = true
          return result
        }
      })
      return result
    },
    /** 弹窗-取消 */
    cancelClick () {
      this.dialogVisible = false
      this.winBidYn = null
      this.selectIndex = null
    },
    /** 弹窗-保存 */
    saveClick () {
      if (this.selectIndex !== null && this.selectIndex !== '') {
        this.bidTableData = []
        this.bidTableData.push(this.dialogTableData[this.selectIndex])
      }
      this.dialogVisible = false
      this.winBidYn = null
      this.selectIndex = null
    },
    /** 提交 */
    submitForm () {
      resnotice.updateProgressStage(this.noticeCode).then(() => {
        this.sectionList.map((section) => {
          section.biddingStatus = 3
        })
        section.updateBatch(this.sectionList)
      }).then(() => {
        this.lazyRender = false
        this.getNoticeProgress()
      })
    },
    getCurrentRow (value) {
      this.selectIndex = value
    },
    /** 查询标段信息 */
    getSections () {
      this.sectionList = []
      bidfile.getsections(this.noticeCode).then((res) => {
        let sectionList = res.data.sectionList
        if (sectionList) {
          if (sectionList.length > 0) {
            for (let i = 0; i < sectionList.length; i++) {
              // 标段已锁定
              if (sectionList[i].lockedProgressStage) {
                // 当前状态在锁定状态之前，则展示该标段；之后，则不展示
                if (this.currentProgressStage <= sectionList[i].lockedProgressStage) {
                  this.sectionList.push(sectionList[i])
                }
              } else {
                // 标段未锁定
                this.sectionList.push(sectionList[i])
              }
            }
            if (this.sectionList.length > 0) {
              // 默认展示第一个标段的中标结果
              this.getDataBySection(this.sectionList[0], 0)
            }
          }
          this.display()
        } else {
          return false
        }
      })
    },
    /** 查询招标项目信息 */
    getTenderProject () {
      tenderproject.getOneByCode(this.$route.query.tenderProjectCode).then((res) => {
        if (res.data.tenderProject) {
          let tenderProject = res.data.tenderProject
          let selectApply = tenderProject.selectApply
          this.dialogRuleForm.projectName = selectApply.projectName
          this.dialogRuleForm.codeUser = selectApply.codeUser
          this.tenderProjectType = tenderProject.tenderProjectType
        } else {
          return false
        }
      })
    },
    /** 通过标段查询中标候选人公示对应信息 */
    getPublicityBySection (sectionCode, index, isSaved) {
      let query = {
        noticeCode: this.noticeCode,
        sectionCode: sectionCode
      }
      candNotice.getOne(query).then((res) => {
        if (res.data.publicity) {
          if (!this.ruleForm.objectId) {
            this.ruleForm.resultNoticeMedia = res.data.publicity.noticeMedia
          }
          this.dialogTableData = res.data.publicity.winCandidateList
          this.winCandidateRank(this.dialogTableData)
          // 若未保存过结果公示，中标人信息默认展示分数最高的投标人
          if (!isSaved) {
            this.bidTableData = []
            this.bidTableData.push(this.dialogTableData[0])
          }
        } else {
          return false
        }
      })
    },
    /** 依据得分对中标候选人进行排序 */
    winCandidateRank (data) {
      let flag = data.length
      while (flag > 0) {
        let k = flag
        flag = 0
        for (let j = 1; j < k; j++) {
          if (Number(data[j - 1].score) < Number(data[j].score)) {
            let temp = {}
            temp = data[j - 1]
            data[j - 1] = data[j]
            data[j] = temp
            flag = j
          }
        }
      }
    },
    /** 通过标段查询中标结果公示对应信息 */
    getResultNoticeBySection (sectionCode, index) {
      let obj = {
        tenderProjectCode: this.$route.query.tenderProjectCode,
        noticeCode: this.noticeCode,
        sectionCode: sectionCode,
        resultNoticeType: 1
      }
      resnotice.getResultNotice(obj).then((res) => {
        let isSaved = false // 是否保存标志：若未保存过结果公示，中标人信息默认展示分数最高的投标人；若已保存，则展示保存结果
        if (res.data.resultNotice) {
          this.ruleForm = res.data.resultNotice
          if (!this.ruleForm.objectId) {
            this.ruleForm.resultNoticeMedia = this.ruleForm.noticeMedia
          }
          this.ruleForm.fileInformationList.map((file) => {
            // 公示附件
            if (file.businessType === '1') {
              this.fileList.push(file)
            }
            // 中标通知书
            if (file.businessType === '2') {
              this.noticeFileList.push(file)
            }
          })
          this.$refs.ueditor[index].setContent(this.ruleForm.resultNoticeContent)
          // 若已保存，则展示保存的中标人信息
          this.bidTableData.push(this.ruleForm.bidWinner.winCandidate)
          // 项目信息
          this.getSuggestedPlans()
          isSaved = true
        } else {
          // 项目信息
          this.getSuggestedPlans()
        }
        this.getPublicityBySection(sectionCode, index, isSaved)
      })
    },
    /** 查询项目进行状态 */
    getNoticeProgress () {
      let obj = {
        tenderProjectCode: this.$route.query.tenderProjectCode,
        noticeCode: this.noticeCode
      }
      openbid.getNoticeProgress(obj).then((res) => {
        if (res.data.noticeProgress) {
          this.progressStage = res.data.noticeProgress.progressStage
        } else {
          return false
        }
      }).then((res) => {
        let enterpriseType = this.$store.getters.authUser.enterpriseType
        if ((enterpriseType === 4) ||
          (this.progressStage > 7 && enterpriseType !== 4)) {
          this.getSections()
        }
      })
    },
    display () {
      let enterpriseType = this.$store.getters.authUser.enterpriseType
      if (this.progressStage === 7 && enterpriseType === 4) {
        this.dialogEdit = true
        this.readonly = false
      } else {
        this.dialogEdit = false
        this.readonly = true
      }
      // 若项目进行状态为已完成，则结果变更按钮高亮
      if (this.progressStage === 8) {
        this.$emit('noticeProgress', 8)
      }
      // 招标变更查询（只可查看）
      if (!this.operationCheckFlag) {
        this.dialogEdit = false
        this.readonly = true
      }
      // 延迟加载组件判断
      this.lazyRender = true
      // 提交之后dialogEdit改变，项目信息dialogEdit值随之改变
      this.ruleFormProInfo.suggestedPlanTableData.map(item => {
        item.dialogEdit = this.dialogEdit
      })
    },
    getSuggestedPlans () {
      let tempData = []
      if (this.ruleForm.suggestedPlanTree) {
        tempData = this.ruleForm.suggestedPlanTree
        if (tempData) {
          tempData.map(item => {
            item.dialogEdit = this.dialogEdit
          })
          this.ruleFormProInfo.suggestedPlanTableData = this.transTreeData(tempData)
        }
      } else {
        investmanage.getSuggestedPlanBySectionCode(this.collapseActiveName).then(res => {
          tempData = res.data.suggestedPlanList
          if (tempData) {
            tempData.map(item => {
              item.dialogEdit = this.dialogEdit
            })
            this.ruleFormProInfo.suggestedPlanTableData = this.transTreeData(tempData)
          }
        })
      }
    },
    /**
     * 方法 transTreeData
     * param [原始data]
     * return [treeData]
     **/
    transTreeData (data) {
      let treeDate = []
      let orderNumber = 1
      if (data.length) {
        data.map((item) => {
          item.orderNumber = orderNumber++
          // 二级以下
          if (item.children) {
            this.transTreeData(item.children)
          }
        })
      }
      treeDate = data
      return treeDate
    },
    updateSectionInfos () {
      let sectionInfos = []
      let date = new Date(this.ruleForm.resultNoticeTime)
      let time = formatDate(date, 'yyyy-MM-dd').split('-')
      let year = time[0]
      let month = time[1]
      if (month < 10) {
        month = month.substring(1, 2)
      }
      this.ruleFormProInfo.suggestedPlanTableData.map(item => {
        let sectionInfo = {}
        sectionInfo.objectId = item.suggestedPlan.sectionInfoId
        sectionInfo.number = item.suggestedPlan.sectionInfoNumber
        sectionInfo.price = item.suggestedPlan.sectionInfoPrice
        sectionInfo.suggestedPlanCode = item.suggestedPlan.code
        sectionInfo.sectionCode = this.collapseActiveName
        sectionInfo.year = year
        sectionInfo.month = month
        sectionInfos.push(sectionInfo)
        if (item.children) {
          item.children.map(child => {
            let sectionInfo = {}
            sectionInfo.objectId = child.suggestedPlan.sectionInfoId
            sectionInfo.number = child.suggestedPlan.sectionInfoNumber
            sectionInfo.price = child.suggestedPlan.sectionInfoPrice
            sectionInfo.suggestedPlanCode = child.suggestedPlan.code
            sectionInfo.accessoriesCode = child.suggestedPlan.accessoriesCode
            sectionInfo.sectionCode = this.collapseActiveName
            sectionInfo.year = year
            sectionInfo.month = month
            sectionInfos.push(sectionInfo)
          })
        }
      })
      this.ruleForm.planSectionInfoList = sectionInfos
    }
  },
  /** 实例创建完成 */
  mounted () {
  }
}
</script>
<style lang="less">
#resnotice-page {
  .resnotice-cont {
    .resnotice-tit {
      height: 50px;
      line-height: 50px;
      text-align:left;
      padding: 0 20px;
      background:#F7F6FB;
      span {
        font-weight: 800;
      }
    }
    .resnitice-box {
      padding: 20px;
      .form-table {
        th {
          padding: 0;
        }
      }
      .bottom-submit-btn {
        padding: 20px;
        button {
          width: 160px;
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
      .confirm-btn {
        margin: 10px 0;
      }
      .el-form-item__content {
        text-align: left;
      }
      /** 编辑器 */
      .ueditor-box {
        width: 64%;
        .el-form-item__content {
          line-height: 0;
        }
        .ueditor-wrap {
          padding: 0;
        }
      }
      .form-last {
        .el-form-item__content {
          text-align: center;
          margin-left: 0 !important;
        }
      }
    }
  }
  /** 弹窗 */
  .dialog-box {
    .el-form-item__content {
      text-align: left;
    }
    .el-dialog__footer {
      text-align: center;
    }
  }
  .el-form-item__error {
    width: 340px;
  }
}
</style>
