<template>
  <div id="clarupsubaddindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让文件</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/transferor/transfer-documents/sub-clarify-documents?roledsType=1' }">提交澄清/修改文件</el-breadcrumb-item>
        <el-breadcrumb-item>修改澄清/修改文件</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm" ref="ruleForm" label-width="140px" class="demo-ruleForm">
        <!--公告内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">澄清/修改文件信息</span>
          <el-col :span="24">
            <el-form-item label="澄清/修改文件：" prop="name" class="filetitle bitianicon">
              <commonFileUpload ref="cfuTransferClar" @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></commonFileUpload>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="澄清/修改内容：" prop="name" class="filetitle bitianicon">
              <el-input
                type="textarea"
                class="remarksbox"
                v-model="ruleForm.content">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注：" prop="name" class="filetitle">
              <el-input
                type="textarea"
                class="remarksbox"
                v-model="ruleForm.remark">
              </el-input>
            </el-form-item>
          </el-col>
        </div>
        <!--公告内容-->
        <!--关联项目信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">关联项目信息</span>
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
          </el-table>
        </div>
        <!--关联项目信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
          <el-button type="primary" class="save" @click="updateDocument('ruleForm')">保存</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import {clarifyFile, tdocuments, fileInfo} from '@/api'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
export default {
  components: {
    commonFileUpload
  },
  data () {
    return {
      ruleForm: {
        objectId: this.$route.query.objectId
      },
      // 业务类型 （1.项目 2.公告 3.出让文件 4.澄清修改文件）
      fileType: 4,
      // 文件上传保存的数组
      fileArry: [],
      establish: false, // 是否可以添加
      // 关联相关信息
      tableDataSeletion: [],
      multipleSelection: [], // 关联信息提交时的数组
      updateStatus: false,
      subStatus: 0,
      originalId: '',
      oldFileName: ''
    }
  },
  methods: {
    cancelBtn () {
      this.$router.push({path: '/mr/transferor/transfer-documents/sub-clarify-documents?roledsType=1'})
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
        this.establish = false
      }
    },
    handleSelectionAll (selection) {
      if (selection.length === 0) {
        this.multipleSelection = []
      } else {
        this.multipleSelection = selection
      }
      this.establish = false
    },
    // ****************************************************文件上传**********************************
    // 查看详情
    detail () {
      // 查询关联的项目信息
      let url = {
        annoId: this.$route.query.annoId,
        isSend: 1
      }
      tdocuments.selectclarifyRelation(url).then((res) => {
        this.tableDataSeletion = res.data.refTrandocSectionList
      })
      clarifyFile.detail(this.$route.query.objectId).then((res) => {
        this.ruleForm = res.data.clarifyFile
        if (res.data.clarifyFile.fileInformations) {
          this.oldFileName = res.data.clarifyFile.fileInformations.fileName
          this.originalId = res.data.clarifyFile.fileInformations.objectId
        }
        let url = {
          clarifyFileId: res.data.clarifyFile.objectId
        }
        clarifyFile.detailRelation(url).then((res) => {
          res.data.refClarifyFileList.map((tableItem, index) => {
            if (tableItem.isChecked === 1) {
              this.$refs.multipleTable.toggleRowSelection(this.tableDataSeletion[index], true)
            } else {
              this.$refs.multipleTable.toggleRowSelection(this.tableDataSeletion[index], false)
            }
          })
        })
      })
    },
    // 将子组件获取到的数据传给父组件
    upLoadFile (file) {
      this.fileArry = file
      this.updateStatus = true
    },
    // ie9 获取删除信息
    ieDelectFile (array) {
      this.ieDelectArray = array
    },
    // 修改出让文件信息
    updateDocument () {
      if (this.updateStatus) {
        if (this.fileArry.length === 0) {
          this.$message({
            type: 'warning',
            message: '请上传文件'
          })
          return false
        }
      }
      if (this.ruleForm.content === '') {
        this.$message({
          type: 'warning',
          message: '请填写澄清/修改内容'
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
      this.multipleSelection.map((its) => {
        if (its.section.roomId !== null) {
          this.$message({
            type: 'warning',
            message: '已创建竞价室，不能添加'
          })
          this.establish = true
        }
      })
      if (this.subStatus === 1) {
        this.$message({
          type: 'warning',
          message: '不能重复提交数据'
        })
        return false
      }
      this.subStatus = 1
      if (!this.establish) {
        let that = this
        let refclardocSections = []
        clarifyFile.update(this.ruleForm).then((res) => {
          // 将附件信息入库
          if (that.fileArry.length !== 0) {
            that.fileArry[0].relatedCode = that.ruleForm.code
            fileInfo.deleteFile(this.originalId).then((res) => {
              fileInfo.saveFile(this.fileArry).then((res) => {
                this.fileArry = []
              })
            })
          }
          refclardocSections = this.multipleSelection.map(function (item) {
            let refclardocSection = {}
            refclardocSection.clarifyFileId = res.data.clarifyFiles.objectId
            refclardocSection.clarifyFileSectionid = item.section.objectId
            refclardocSection.isChecked = 1
            return refclardocSection
          })
          clarifyFile.saveRelation(refclardocSections).then((res) => {
            // 跳转到出让文件列表页
            this.$router.push({path: '/mr/transferor/transfer-documents/sub-clarify-documents?roledsType=1'})
          })
        })
      }
    },
    submitForm (formName) {
      if (this.updateStatus) {
        if (this.fileArry.length === 0) {
          this.$message({
            type: 'warning',
            message: '请上传文件'
          })
          return false
        }
      }
      if (this.ruleForm.content === '') {
        this.$message({
          type: 'warning',
          message: '请填写澄清/修改内容'
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
      this.multipleSelection.map((its) => {
        if (its.section.roomId !== null) {
          this.$message({
            type: 'warning',
            message: '已创建竞价室，不能添加'
          })
          this.establish = true
        }
      })
      if (this.subStatus === 1) {
        this.$message({
          type: 'warning',
          message: '不能重复提交数据'
        })
        return false
      }
      this.subStatus = 1
      let refclardocSections = []
      let that = this
      if (!this.establish) {
        clarifyFile.update(this.ruleForm).then((res) => {
          // 将附件信息入库
          if (that.fileArry.length !== 0) {
            that.fileArry[0].relatedCode = that.ruleForm.code
            fileInfo.deleteFile(this.originalId).then((res) => {
              fileInfo.saveFile(this.fileArry).then((res) => {
                this.fileArry = []
              })
            })
          }
          refclardocSections = this.multipleSelection.map(function (item) {
            let refclardocSection = {}
            refclardocSection.clarifyFileId = res.data.clarifyFiles.objectId
            refclardocSection.clarifyFileSectionid = item.section.objectId
            refclardocSection.isChecked = 1
            return refclardocSection
          })
          clarifyFile.saveRelation(refclardocSections).then((res) => {
            this.ruleForm.status = 1
            clarifyFile.updateStatus(this.ruleForm).then((res) => {
              // 跳转到出让文件列表页
              this.$router.push({path: '/mr/transferor/transfer-documents/sub-clarify-documents?roledsType=1'})
            })
          })
        })
      }
    }
  },
  created () {
    this.detail()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
</style>
