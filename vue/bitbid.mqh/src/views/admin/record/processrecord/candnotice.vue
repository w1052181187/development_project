<template>
  <!-- 候选人公示 -->
  <div class="admin-layout" id="candnotice-page">
    <div class="candnotice-cont">
      <div class="candnotice-tit">
        <span>候选人公示</span>
      </div>
      <div class="candnotice-box">
        <!-- 折叠面板 -->
        <!--<el-collapse v-model="collapseActiveName" accordion @change="getPublicityBySection">-->
        <el-collapse v-model="collapseActiveName" accordion>
          <el-collapse-item v-for="(section,index) in sectionList" :key="index" :name="section.code">
              <!--<div class="head-self" >-->
                <template slot="title">
                  <span @click="getPublicityBySection(section.code, index)" class="collapse-span" style="width:100%;display:inherit;position:relative;z-index:1;">{{section.name}}</span>
                </template>
              <!--</div>-->
              <el-form :model="publicity" :rules="rules" ref="publicity" label-width="120px">
                <el-form-item label="中标候选人:" prop="winCandidate">
                  <el-button type="warning" @click="confirmBidWinner(section.name, section.code)" class="confirm-btn" v-if="!disabled">确定候选人</el-button>
                  <el-table
                    border
                    :data="winCandidateList"
                    :row-class-name="tableRowClassName"
                    style="width: 100%;margin-top: 15px;">
                    <el-table-column
                      type="index"
                      label="序号"
                      align="center"
                      :index="indexMethod"
                      width="55">
                    </el-table-column>
                    <el-table-column
                      label="中标候选人名称"
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
                <el-form-item label="公示时间:" prop="publicityTime">
                  <el-date-picker
                    v-model="publicity.publicityTime"
                    type="date"
                    :disabled="disabled"
                    placeholder="选择日期">
                  </el-date-picker>
                </el-form-item>
                <el-form-item label="公示附件:">
                  <el-upload
                    class="upload-demo"
                    :action="upLoad"
                    :on-success="uploadSuccess"
                    :file-list="fileList"
                    :before-upload="beforeUpload"
                    v-if="!disabled"
                    :show-file-list="false">
                    <el-button size="small" type="success" class="upload-btn" >上传附件</el-button>
                  </el-upload>
                  <!-- 上传附件列表 -->
                  <div class="apply-upload">
                    <el-row>
                      <el-col :span="12">
                        <span class="file-list" v-for="(item, index) in fileList" :key="index">
                          <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                          <i class="el-icon-error upload-file"  v-if="!disabled" @click="clickDel(index)"></i>
                        </span>
                      </el-col>
                    </el-row>
                  </div>
                </el-form-item>
                <div class="ueditor-box">
                  <el-form-item label="公示内容:" prop="publicityContent" ref="publicityContent">
                    <ueditor ref="ueditor" :content="publicityContent" class="ueditor" ></ueditor>
                  </el-form-item>
                </div>
                <el-form-item label="发布媒介:" prop="publicityMedia">
                  <el-input v-model="publicity.publicityMedia" :disabled="disabled" style="width: 57.5%;"></el-input>
                </el-form-item>
                <el-form-item class="form-last" v-if="!disabled">
                  <el-button type="primary" @click="submitForm('publicity',index,section.code)">保 存</el-button>
                </el-form-item>
              </el-form>
            </el-collapse-item>
        </el-collapse>
        <div class="bottom-submit-btn" v-if="!disabled">
          <el-button type="success" @click="submitPublicity">提 交</el-button>
        </div>
      </div>
    </div>

    <!-- 弹窗区域start -->
    <div class="dialog-box">
      <el-dialog
        title="候选人设置"
        :visible.sync="dialogVisible"
        width="50%">
        <div class="dialog-box-form">
          <el-form :model="dialogRuleForm" :rules="dialogRules" ref="dialogRuleForm" label-width="100px">
            <el-form-item label="招标项目名称:" prop="projectName">
              <span>{{selectApply.projectName}}</span>
            </el-form-item>
            <el-form-item label="招标项目编号:" prop="codeUser">
              <span>{{selectApply.codeUser}}</span>
            </el-form-item>
            <el-form-item label="标段名称:" prop="sectionName">
              <span>{{this.currentSectionName}}</span>
            </el-form-item>
            <el-form-item label="候选人数量:" prop="candPerNum">
              <el-select v-model="publicity.publicityNumber" placeholder="请选择">
                <el-option label="1" value="1"></el-option>
                <el-option label="2" value="2"></el-option>
                <el-option label="3" value="3"></el-option>
              </el-select>
            </el-form-item>
            <el-table
              border
              :data="evaluationResult.evaluationResultSublists"
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
                label="是否候选"
                prop="isCandidate"
                align="left">
                <template slot-scope="scope">
                  <el-checkbox true-label="1" false-label="0" v-model="scope.row.isCandidate"></el-checkbox>
                </template>
              </el-table-column>
            </el-table>
          </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="saveWinCandidateList">保 存</el-button>
          <el-button @click="dialogVisible = false">取 消</el-button>
        </span>
      </el-dialog>
    </div>
    <!-- 弹窗区域end -->
  </div>
</template>
<script>
// 引入编辑器
import ueditor from '@/components/ueditor/ueditor.vue'
import {commonJs, downloadFile} from 'common/js/common'
import {candNotice, tenderproject, openbid, notice} from 'api/index'
export default {
  data () {
    return {
      collapseActiveName: '',
      ruleForm: {},
      rules: {
        /* fileList: [
          {required: true, message: '请上传公示附件', trigger: 'blur'}
        ], */
        publicityContent: [
          {required: true, message: '请输入公示内容', trigger: 'blur'}
        ],
        publicityTime: [
          {required: true, message: '请选择公示时间', trigger: 'change'}
        ],
        publicityMedia: [
          {required: true, message: '请填写发布媒介', trigger: 'change'}
        ]
      },
      winCandidateList: [], // 中标候选人-表格数据
      fileList: [], // 上传文件列表数据
      dialogVisible: false, // 弹窗
      dialogRuleForm: {}, // 弹窗-表单
      dialogRules: {}, // 弹窗-规则
      currentPage: 1,
      sectionList: [], // 候标段集合
      upLoad: commonJs.fileUploadUrl,
      publicity: {}, // 候选人公示
      sectionIndex: 0, // 标段列表下标
      selectApply: {}, // 招标项目对应选聘备案申请表
      currentSectionName: '', // 当前选中标段名称
      evaluationResult: {}, // 评标结果信息
      publicityContent: '', // 公示内容
      disabled: true,
      isCandidate: '',
      progressStage: null, // 项目进行状态
      noticeProgress: null, // 标段锁定进行状态
      notLockedCount: 0, // 未锁定的标段数量
      currentProgressStage: 6 // 候选人公示阶段
    }
  },
  computed: {},
  created () {
    this.getNoticeProgress()
  },
  props: {
    noticeCode: String,
    operationCheckFlag: Boolean
  },
  /** 方法集 */
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    indexDialogMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 下载文件 */
    downloadFile,
    /** 删除上传附件 */
    clickDel (index) {
      this.fileList.splice(this.fileList.indexOf(index), 1)
      // this.validFile()
    },
    /** 校验附件 */
    /* validFile () {
      if (this.fileList.length > 0) {
        this.$set(this.publicity, 'fileList', this.fileList)
      } else {
        this.$set(this.publicity, 'fileList', '')
      }
      if (this.publicity.fileList) {
        this.$refs['fileList'][this.sectionIndex].clearValidate()
      }
    }, */
    /** 校验公示内容 */
    validContent () {
      let publicityContent = this.$refs.ueditor[this.sectionIndex].getContent()
      if (publicityContent) {
        this.$set(this.publicity, 'publicityContent', publicityContent)
      } else {
        this.$set(this.publicity, 'publicityContent', '')
      }
      if (this.publicity.publicityContent) {
        this.$refs['publicityContent'][this.sectionIndex].clearValidate()
      }
    },
    /** 上传附件 */
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
        // this.validFile()
      }
    },
    /** 公示附件格式为pdf */
    beforeUpload (file) {
      const isPDF = file.type === 'application/pdf'
      if (!isPDF) {
        this.$message.error('上传文件只能为PDF格式！')
      }
      return isPDF
    },
    /** 保存 */
    submitForm (formName, index, sectionCode) {
      // this.validFile()
      this.validContent()
      this.$refs[formName][index].validate((valid) => {
        if (valid) {
          this.publicity.sectionCode = sectionCode
          this.publicity.noticeCode = this.noticeCode
          this.publicity.publicityContent = this.$refs.ueditor[index].getContent()
          this.publicity.fileInformationList = this.fileList
          if (this.winCandidateList.length === 0) {
            this.$message.error('未选择候选人！')
            return
          }
          this.publicity.winCandidateList = this.winCandidateList
          candNotice.save(this.publicity).then((res) => {
            this.getPublicityBySection(sectionCode, this.sectionIndex)
          })
        } else {
          return false
        }
      })
    },
    /** 确定候选人 */
    confirmBidWinner (currentSectionName, sectionCode) {
      this.dialogVisible = true
      this.currentSectionName = currentSectionName
      this.evaluationResult = {}
      this.getEvaluationResult(sectionCode) // 通过标段查询对应标段投标人信息
    },
    /** 通过标段查询中标候选人公示对应信息 */
    getPublicityBySection (sectionCode, sectionIndex) {
      this.winCandidateList = []
      this.fileList = []
      this.sectionIndex = sectionIndex
      this.publicity = {}
      let query = {
        noticeCode: this.noticeCode,
        sectionCode: sectionCode
      }
      candNotice.getOne(query).then((res) => {
        if (res.data.publicity) {
          this.publicityContent = res.data.publicity.publicityContent
          this.$refs.ueditor[sectionIndex].setContent(this.publicityContent)
          this.publicity = res.data.publicity
          this.fileList = res.data.publicity.fileInformationList
          this.winCandidateList = res.data.publicity.winCandidateList
        } else {
          notice.getByCode(this.noticeCode).then((res) => {
            let notice = res.data.notice
            if (notice) {
              this.$set(this.publicity, 'publicityMedia', notice.noticeMedia)
            }
          })
        }
      })
    },
    /** 上传 */
    handleSuccess () {},
    /** 查询标段信息 */
    getSectionList () {
      this.sectionList = []
      candNotice.getSectionList(this.noticeCode).then((res) => {
        let sectionList = res.data.sectionList
        if (sectionList) {
          if (sectionList.length > 0) {
            for (let i = 0; i < sectionList.length; i++) {
              // 标段已锁定
              if (sectionList[i].lockedProgressStage) {
                // 菜单按钮高亮设置
                if (this.noticeProgress) {
                  if (this.noticeProgress < sectionList[i].lockedProgressStage) {
                    this.noticeProgress = sectionList[i].lockedProgressStage
                  }
                } else {
                  this.noticeProgress = sectionList[i].lockedProgressStage
                }
                // 当前状态在锁定状态之前，则展示该标段；之后，则不展示
                if (this.currentProgressStage <= sectionList[i].lockedProgressStage) {
                  this.sectionList.push(sectionList[i])
                }
              } else {
                // 标段未锁定
                this.notLockedCount = this.notLockedCount + 1
                this.sectionList.push(sectionList[i])
              }
            }
            if (this.sectionList) {
              if (this.sectionList.length > 0) {
                // 设置默认打开第一项
                this.collapseActiveName = this.sectionList[0].code
                if (this.collapseActiveName) {
                  this.getPublicityBySection(this.collapseActiveName, 0)
                }
              }
            }
          }
          this.display()
        } else {
          return false
        }
      })
    },
    /** 查询当前公告所属的招标项目信息 */
    getTenderProject () {
      let data = {code: this.noticeCode}
      tenderproject.queryByNotice(data).then((res) => {
        if (res.data.tenderProject) {
          this.selectApply = res.data.tenderProject.selectApply
        }
      })
    },
    /** 查询标段对应评标结果信息 */
    getEvaluationResult (sectionCode) {
      openbid.getEvaluationResult({'noticeCode': this.noticeCode, 'sectionCode': sectionCode}).then((res) => {
        this.evaluationResult = res.data.evaluationResult
      })
    },
    /** 保存弹窗候选人信息到页面 */
    saveWinCandidateList () {
      let evaluationResultSublists = this.evaluationResult.evaluationResultSublists
      let winCandidateListTemp = []
      for (var i = 0; i < evaluationResultSublists.length; i++) {
        if (evaluationResultSublists[i].isCandidate === '1') { // 如果选中，则添加到候选人数组中
          winCandidateListTemp.push(evaluationResultSublists[i])
        }
      }
      if (winCandidateListTemp.length !== parseInt(this.publicity.publicityNumber)) {
        this.$message.error('候选人数量与选择候选人不一致！')
      } else {
        this.winCandidateList = winCandidateListTemp
        this.dialogVisible = false
      }
    },
    /** 提交候选人公示 */
    submitPublicity () {
      candNotice.submitPublicity(this.noticeCode).then(() => {
        this.collapseActiveName = ''
        this.getNoticeProgress()
      })
    },
    /** 查询项目进行状态 */
    getNoticeProgress () {
      let obj = {
        noticeCode: this.noticeCode
      }
      openbid.getNoticeProgress(obj).then((res) => {
        if (res.data.noticeProgress) {
          this.progressStage = res.data.noticeProgress.progressStage
        } else {
          this.$message.error('获取信息出错！')
        }
        return res
      }).then((res) => {
        let enterpriseType = this.$store.getters.authUser.enterpriseType
        if ((enterpriseType === 4) ||
          (this.progressStage > 6 && enterpriseType !== 4)) {
          this.getSectionList()
        }
      })
    },
    display () {
      // 若标段全部锁定，则使标段中锁定进行状态最靠后的菜单按钮高亮
      let enterpriseType = this.$store.getters.authUser.enterpriseType
      if (this.notLockedCount === 0) {
        this.$emit('noticeProgress', this.noticeProgress)
        this.disabled = true
      } else {
        // 若存在未锁定的标段，项目进行状态处于当前阶段且登录人员为招标机构，则可编辑；否则只可查看
        if (this.progressStage === 6 && enterpriseType === 4) {
          this.disabled = false
        } else {
          this.disabled = true
        }
        if (this.progressStage === 7) {
          this.$emit('noticeProgress', 7)
        }
      }
      // 招标变更查询（只可查看）
      if (!this.operationCheckFlag) {
        this.disabled = true
      }
    }
  },
  /** 实例创建完成 */
  mounted () {
    this.getTenderProject()
  },
  /** 组件注册 */
  components: {
    // 编辑器
    ueditor
  }
}
</script>
<style lang="less">
#candnotice-page {
  .candnotice-cont {
    .candnotice-tit {
      height: 50px;
      line-height: 50px;
      text-align:left;
      padding: 0 20px;
      background:#F7F6FB;
      span {
        font-weight: 800;
      }
    }
    /** 主体内容 */
    .candnotice-box {
      padding: 20px;
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
        width: 60%;
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
}
</style>
