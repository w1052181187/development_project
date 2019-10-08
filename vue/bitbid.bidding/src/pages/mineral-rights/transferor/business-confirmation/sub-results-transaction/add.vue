<template>
  <div id="subresaddindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>成交确认</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/transferor/business-confirmation/sub-results-transaction?roledsType=1' }">提交成交结果公示</el-breadcrumb-item>
        <el-breadcrumb-item>添加成交结果公示</el-breadcrumb-item>
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
              <el-form-item label="公示时间：" prop="sectionNumber" class="bitianicon">
                <el-date-picker
                  v-model="publicityDataRange"
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
                <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></commonFileUpload>
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
              prop="section.sectionNumber"
              align="left"
              label="项目编号">
            </el-table-column>
            <el-table-column
              prop="section.sectionName"
              align="left"
              label="项目名称"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="section.transferType"
              align="left"
              label="出让类型"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.section.transferType === 1">
                  <span class="statusbox">探矿权</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.section.transferType === 2">
                  <span class="statusbox">采矿权</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="section.ageLimit"
              align="left"
              label="出让年限"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="section.salesConfirmation.bidderName"
              align="left"
              label="竞得人"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="section.salesConfirmation.strikePrice"
              align="left"
              label="成交价(万元)"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="section.lastUpdateDate"
              align="left"
              label="成交时间"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </div>
        <!--关联项目信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('ruleForm')" :disabled="isDisable">提交</el-button>
          <el-button type="primary" class="save" @click="saveData">保存</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/public-resource/mineral-rights/common/ueditor/ueditor.vue'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
import {transResAnno, fileInfo} from '@/api'
export default {
  components: {
    editor,
    commonFileUpload
  },
  data () {
    return {
      isDisable: false,
      // 富文本
      editread: false,
      content: '',
      ruleForm: {
        annoId: this.$route.query.objectId
      },
      fileList: [], // 文件列表
      EnclosuretableData: [], // 表格
      appendix: false, // 弹框定义
      Enclosure: {}, // 文件上传绑定
      loading: false, // 文件上传过程中
      dataRange: [], // 时间
      // 关联相关信息
      tableDataSeletion: [],
      multipleSelection: [], // 关联信息提交时的数组
      publicityDataRange: [], // 公示时间
      // 业务类型 （1.项目 2.公告 3.出让文件 4.澄清修改文件 5.成交结果公示）
      fileType: 5,
      // 文件上传保存的数组
      fileArry: [],
      subStatus: 0,
      oldFileName: '',
      rules: {
        publicityName: [
          { required: true, message: '请输入公示名称', trigger: 'blur' },
          { min: 1, max: 600, message: '长度在 1~600个字符', trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  methods: {
    submitForm (formName) {
      let objectId
      if (this.publicityDataRange) {
        this.ruleForm.publicityStartTime = this.publicityDataRange[0]
        this.ruleForm.publicityEndTime = this.publicityDataRange[1]
      }
      if (this.ruleForm.publicityName === undefined) {
        this.$message({
          type: 'warning',
          message: '公示名称不能为空'
        })
        return false
      }
      if (this.publicityDataRange.length === 0) {
        this.$message({
          type: 'warning',
          message: '公示时间不能为空'
        })
        return false
      }
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
      if (this.subStatus === 1) {
        this.$message({
          type: 'warning',
          message: '不能重复提交数据'
        })
        return false
      }
      this.subStatus = 1
      this.ruleForm.publicityText = this.$refs.ueditor.getContent()
      transResAnno.save(this.ruleForm).then((res) => {
        this.isDisable = true
        objectId = res.data.transResultsAnno.objectId
        if (this.fileArry.length !== 0) {
          this.fileArry[0].relatedCode = res.data.transResultsAnno.code
          // 将附件信息入库
          fileInfo.saveFile(this.fileArry).then((res) => {
            this.fileArry = []
          })
        }
        // 将关联关系入库
        this.multipleSelection = this.multipleSelection.map(function (item) {
          item.transResultsId = res.data.transResultsAnno.objectId
          item.isChecked = 1
          return item
        })
        transResAnno.saveRelation(this.multipleSelection).then((res) => {
          this.ruleForm.status = 1
          this.ruleForm.objectId = objectId
          transResAnno.updateStatus(this.ruleForm).then((res) => {
            // 跳转到出让文件列表页
            this.$router.push({path: '/mr/transferor/business-confirmation/sub-results-transaction?roledsType=1'})
          })
        })
      })
    },
    cancelBtn () {
      this.$router.push({path: '/mr/transferor/business-confirmation/sub-results-transaction/select-publicity?roledsType=1'})
    },
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
        annoId: this.$route.query.objectId,
        isChecked: 0,
        flag: 0,
        winStatus: 1
      }
      transResAnno.selectRelation(url).then((res) => {
        this.tableDataSeletion = res.data.refTranresSectionList
      })
    },
    upLoadFile (file) {
      this.fileArry = file
    },
    saveData () {
      if (this.publicityDataRange) {
        this.ruleForm.publicityStartTime = this.publicityDataRange[0]
        this.ruleForm.publicityEndTime = this.publicityDataRange[1]
      }
      if (this.ruleForm.publicityName === undefined) {
        this.$message({
          type: 'warning',
          message: '公示名称不能为空'
        })
        return false
      }
      if (this.publicityDataRange.length === 0) {
        this.$message({
          type: 'warning',
          message: '公示时间不能为空'
        })
        return false
      }
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
      if (this.subStatus === 1) {
        this.$message({
          type: 'warning',
          message: '不能重复提交数据'
        })
        return false
      }
      this.subStatus = 1
      this.ruleForm.publicityText = this.$refs.ueditor.getContent()
      transResAnno.save(this.ruleForm).then((res) => {
        if (this.fileArry.length !== 0) {
          this.fileArry[0].relatedCode = res.data.transResultsAnno.code
          // 将附件信息入库
          fileInfo.saveFile(this.fileArry).then((res) => {
            this.fileArry = []
          })
        }
        if (res.data.transResultsAnno !== undefined) {
          // 将关联关系入库
          this.multipleSelection = this.multipleSelection.map(function (item) {
            item.transResultsId = res.data.transResultsAnno.objectId
            item.isChecked = 1
            return item
          })
          transResAnno.saveRelation(this.multipleSelection).then((res) => {
            // 跳转到出让文件列表页
            this.$router.push({path: '/mr/transferor/business-confirmation/sub-results-transaction?roledsType=1'})
          })
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
  @import '../../../../../assets/css/mineral/common.css';
</style>
