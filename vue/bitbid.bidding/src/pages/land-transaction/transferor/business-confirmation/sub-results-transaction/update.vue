<template>
  <div id="subresaddindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>成交确认</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/business-confirmation/sub-results-transaction?roledsType=1' }">提交成交结果公示</el-breadcrumb-item>
        <el-breadcrumb-item>修改成交结果公示</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm" ref="ruleForm" :rules="rules" label-width="100px" class="demo-ruleForm">
        <!--公告内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">公示信息</span>
          <el-row>
            <el-col :span="12">
              <el-form-item label="公示名称：" prop="publicityName">
                <el-input v-model="ruleForm.publicityName" placeholder="请输入公示名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="公示时间：" prop="publicityDataRange">
                <el-date-picker
                  v-model="ruleForm.publicityDataRange"
                  type="datetimerange"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  range-separator="至"
                  start-placeholder="开始时间"
                  end-placeholder="结束时间"
                  align="right">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="公示正文：" class="bitianicon">
                <div class="editor">
                  <editor ref="ueditor" class="ueditor" :editread="editread" :content="content"></editor>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="附件：" prop="">
                <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName" :type="typeFile"></commonFileUpload>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <!--公告内容-->
        <!--关联项目信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">相关项目信息</span>
          <el-table
            ref="multipleTable"
            :data="tableDataSeletion"
            tooltip-effect="dark"
            style="width: 100%"
            border
            header-cell-class-name="tabletitles"
            @selection-change="handleSelectionChange"
            @select-all="handleSelectionAll">
            <el-table-column
              type="selection"
              width="55">
            </el-table-column>
            <el-table-column
              prop="landInformation.sectionNumber"
              align="left"
              label="地块编号"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="landInformation.landAnnoMation.annoNumber"
              align="left"
              label="公告名称"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="type"
              align="left"
              label="出让方式"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.landInformation.landAnnoMation.type === 1">
                  <span class="statusbox">拍卖</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.landInformation.landAnnoMation.type === 2">
                  <span class="statusbox">挂牌</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.landInformation.landAnnoMation.type === 3">
                  <span class="statusbox">资格后审</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.landInformation.landAnnoMation.type === 4">
                  <span class="statusbox">资格预审</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.landInformation.landAnnoMation.annoType === 2">
                  <span class="statusbox">邀请招标</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="landInformation.ageLimit"
              align="left"
              label="出让年限"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="landInformation.salesConfirmation.bidderName"
              align="left"
              label="竞得人"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="landInformation.salesConfirmation.strikePrice"
              align="left"
              label="成交价(万元)"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="landInformation.lastUpdateDate"
              align="left"
              label="成交时间"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </div>
        <!--关联项目信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('ruleForm', 1)" :loading="isSubmited">提交</el-button>
          <el-button type="primary" class="save" @click="submitForm('ruleForm', 0)" :loading="isSubmited">保存</el-button>
          <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import {transResAnno, fileInfo} from '@/api'
export default {
  components: {
    editor,
    commonFileUpload,
    commonFileDownload
  },
  data () {
    return {
      isSubmited: false,
      // 富文本
      editread: false,
      content: '',
      ruleForm: {
        publicityDataRange: [] // 公示时间
      },
      // 关联相关信息
      tableDataSeletion: [],
      multipleSelection: [], // 关联信息提交时的数组
      // 业务类型 （1.项目 2.公告 3.出让文件 4.澄清修改文件 5.成交结果公示）
      fileType: 5,
      // 文件类型：土地
      typeFile: 2,
      upLoadStatus: false,
      // 文件上传保存的数组
      fileArry: [],
      fileObjecit: {},
      originalId: '',
      oldFileName: '',
      rules: {
        publicityName: [
          { required: true, message: '请输入公示名称', trigger: 'blur' },
          { min: 1, max: 600, message: '长度在 1~600个字符', trigger: ['blur', 'change'] }
        ],
        publicityDataRange: [
          { required: true, message: '请选择公示时间', trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  methods: {
    // 关联项目信息选择
    handleSelectionChange (selection, row) {
      if (selection.length === 0) {
        this.multipleSelection = []
      } else {
        let temp = []
        for (let i = 0; i < selection.length; i++) {
          if (!temp.includes(selection[i].objectId)) {
            temp.push(selection[i])
          }
        }
        this.multipleSelection = temp
      }
    },
    handleSelectionAll (selection) {
      if (selection.length === 0) {
        this.multipleSelection = []
      } else {
        this.multipleSelection = selection
      }
    },
    // ****************************************************文件上传**********************************
    // 查询关联的项目信息
    querySectionList () {
      let url = {
        annoId: this.$route.query.annoId,
        flag: 1,
        transResultsId: this.$route.query.objectId,
        type: 2
      }
      // 关联关系
      transResAnno.selectRelation(url).then((res) => {
        this.tableDataSeletion = res.data.refTranresSectionList
        // 查询公示
        transResAnno.queryOne(this.$route.query.objectId).then((res) => {
          this.ruleForm = res.data.transResultsAnno
          if (res.data.transResultsAnno.fileInformations) {
            this.fileObjecit = res.data.transResultsAnno.fileInformations
            // this.oldFileName = res.data.transResultsAnno.fileInformations.fileName
            this.originalId = res.data.transResultsAnno.fileInformations.objectId
          }
          this.$refs.ueditor.setContent(this.ruleForm.publicityText)
          this.ruleForm.publicityDataRange = [this.ruleForm.publicityStartTime, this.ruleForm.publicityEndTime]
          let url = {
            transResultsId: res.data.transResultsAnno.objectId,
            _t: new Date().getTime()
          }
          transResAnno.detailRelation(url).then((res) => {
            this.tableDataSeletion.map((tableItem, index) => {
              if (tableItem.isChecked === 1) {
                this.$refs.multipleTable.toggleRowSelection(this.tableDataSeletion[index], true)
              } else {
                this.$refs.multipleTable.toggleRowSelection(this.tableDataSeletion[index], false)
              }
            })
          })
        })
      })
    },
    upLoadFile (file) {
      this.upLoadStatus = true
      this.fileObjecit = []
      this.fileArry = file
    },
    submitForm (ruleForm, status) {
      if (this.ruleForm.publicityDataRange) {
        this.ruleForm.publicityStartTime = this.ruleForm.publicityDataRange[0]
        this.ruleForm.publicityEndTime = this.ruleForm.publicityDataRange[1]
      }
      if (this.ruleForm.publicityDataRange.length !== 0) {
        let salestartTime = new Date(Date.parse(this.ruleForm.publicityStartTime.replace(/-/g, '/'))).getTime()
        let publishedSTime = new Date(Date.parse(this.ruleForm.publicityEndTime.replace(/-/g, '/'))).getTime()
        let saledates = Math.abs((salestartTime - publishedSTime)) / (1000 * 60 * 60 * 24)
        if (saledates < 10) {
          this.$message({
            type: 'warning',
            message: '公示时间不得少于10个工作日'
          })
          return false
        }
      }
      if (this.$refs.ueditor.getContent() === '') {
        this.$message({
          type: 'warning',
          message: '公示正文不能为空'
        })
        return false
      }
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择关联项目信息'
        })
        return false
      }
      this.$refs[ruleForm].validate((valid) => {
        if (valid) {
          this.isSubmited = true
          delete this.ruleForm.publicityDataRange
          this.ruleForm.publicityText = this.$refs.ueditor.getContent()
          this.ruleForm.status = status
          transResAnno.update(this.ruleForm).then((res) => {
            // 关联关系
            this.multipleSelection = this.multipleSelection.map(function (item) {
              item.transResultsId = res.data.transResultsAnno.objectId
              item.isChecked = 1
              return item
            })
            transResAnno.saveRelation(this.multipleSelection).then((res) => {
              transResAnno.updateStatus(this.ruleForm).then((res) => {
                // 将附件信息入库
                if (this.fileArry.length !== 0) {
                  this.fileArry[0].relatedCode = this.ruleForm.code
                  if (this.originalId != '') {
                    this.fileArry[0].objectId = this.originalId
                  }
                  fileInfo.saveFile(this.fileArry).then((res) => {
                    this.fileArry = []
                  })
                }
                this.isSubmited = false
                this.$router.push({path: '/lt/transferor/business-confirmation/sub-results-transaction?roledsType=1&showStatus=false'})
              })
            })
          })
        } else {
          return false
        }
      })
    }
  },
  mounted () {
    this.querySectionList()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
