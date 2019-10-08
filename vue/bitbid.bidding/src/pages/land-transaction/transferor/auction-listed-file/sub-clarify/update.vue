<template>
  <div id="tranaddindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>拍卖/挂牌文件</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/auction-listed-file/sub-clarify?roledsType=1&showStatus=false' }">提交澄清修改文件</el-breadcrumb-item>
        <el-breadcrumb-item>修改出让文件</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="130px" class="demo-ruleForm">
        <!--公告内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">出让文件信息</span>
          <el-col :span="24">
            <el-form-item label="出让文件：" prop="name" class="filetitle bitianicon">
              <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
              <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></commonFileUpload>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="澄清/修改内容：" prop="content">
              <el-input
                type="textarea"
                class="remarksbox"
                v-model="ruleForm.content">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注：" prop="remark" class="filetitle">
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
              prop="sectionNumber"
              align="left"
              label="地块编号">
            </el-table-column>
            <el-table-column
              prop="methodOfSupply"
              align="left"
              label="供应方式"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <div class="statusbigbox">
                  <span class="statusbox">招拍挂出让</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="methodOfTransfer"
              align="left"
              label="出让方式"
              width="200"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 1">
                  <span class="statusbox">拍卖</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 2">
                  <span class="statusbox">挂牌</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              label="操作" width="140" align="center">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  详情
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!--关联项目信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('ruleForm', 1)" :loading="subStatus">提交</el-button>
          <el-button type="primary" class="save" @click="submitForm('ruleForm', 0)" :loading="subStatus">保存</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import {clarifyFile, fileInfo, tdocuments} from '@/api'
export default {
  components: {
    commonFileDownload,
    commonFileUpload
  },
  data () {
    return {
      ruleForm: {},
      // 业务类型
      fileType: 4,
      typeFile: 2,
      // 文件上传保存的数组
      fileArry: [],
      // 关联相关信息
      tableDataSeletion: [],
      multipleSelection: [], // 关联信息提交时的数组
      refclardocSections: [],
      // 提交保存状态
      subStatus: false,
      oldFileName: '',
      originalId: '',
      fileObjecit: {},
      // 附件是否修改
      updateStatus: false,
      rules: {
        content: [
          { required: true, message: '请输入澄清/修改内容', trigger: 'blur' },
          { min: 1, max: 500, message: '长度在 1~500个字符', trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  methods: {
    submitForm (ruleForm, flag) {
      if (this.updateStatus) {
        if (this.fileArry.length === 0) {
          this.$message({
            type: 'warning',
            message: '请上传文件'
          })
          return false
        }
      }
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择关联项目'
        })
        return false
      }
      this.subStatus = true
      this.ruleForm.status = flag
      // 修改出让文件的信息并且修改原有时问关联关系
      clarifyFile.update(this.ruleForm).then((res) => {
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
        this.refclardocSections = this.multipleSelection.map(function (item) {
          let refclardocSection = {}
          refclardocSection.clarifyFileId = res.data.clarifyFiles.objectId
          refclardocSection.clarifyFileSectionid = item.objectId
          refclardocSection.isChecked = 1
          refclardocSection.isSend = 0
          refclardocSection.type = 2
          return refclardocSection
        })
        clarifyFile.saveRelation(this.refclardocSections).then((res) => {
          // 跳转到出让文件列表页
          this.$router.push({path: '/lt/transferor/auction-listed-file/sub-clarify?roledsType=1'})
        })
      })
    },
    cancelBtn () {
      this.$router.push({path: '/lt/transferor/auction-listed-file/sub-clarify?roledsType=1'})
    },
    detailBtn (scope) {
      this.$router.push({path: `/lt/transferor/project/massif-register/detail/${scope.row.objectId}`, query: {roledsType: 1, showStatus: false}})
      // this.$router.push({path: `/lt/transferor/announcement/sub-announ/detail/${scope.row.landInformation.objectId}`, query: {roledsType: 1, showStatus: false, methodOfTransfer: scope.row.type}})
    },
    // 查询关联的项目信息
    querySectionList () {
      let url = {
        annoId: this.$route.query.annoId,
        isSend: 1,
        type: this.typeFile
      }
      // 查询符合条件的土地
      tdocuments.selectclarifyRelation(url).then((res) => {
        this.tableDataSeletion = res.data.landInformationList
      })
      clarifyFile.detail(this.$route.query.objectId).then((res) => {
        this.ruleForm = res.data.clarifyFile
        if (res.data.clarifyFile.fileInformations) {
          this.fileObjecit = res.data.clarifyFile.fileInformations
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
    // ***************************************************************自己方法**********************************************************************
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
    // 将子组件获取到的数据传给父组件
    upLoadFile (file) {
      this.updateStatus = true
      this.fileObjecit = []
      this.fileArry = file
    }
  },
  created () {
    this.querySectionList()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
