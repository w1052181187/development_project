<template>
  <div id="tranaddindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>资格预审文件</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/prequalification-file/sub-clarify-file/index?roledsType=1&showStatus=false' }">提交澄清/修改文件</el-breadcrumb-item>
        <el-breadcrumb-item>修改澄清/修改文件</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm" label-width="160px" class="demo-ruleForm">
        <!--出让文件信息-->
        <div class="proinfobox">
          <span class="title">出让文件信息</span>
          <el-row>
            <el-col :span="24">
              <el-form-item label="是否变更预审文件：" prop="isAllowedBidding" class="filetitle bitianicon">
                <el-radio v-model="ruleForm.isFileRadio" :label="isFile.label" v-for="(isFile,index) in isFileData" :key="index">{{isFile.name}}</el-radio>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24" v-if="ruleForm.isFileRadio === 1">
              <el-form-item label="预审文件：" class="filetitle bitianicon">
                <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></commonFileUpload>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="澄清修改文件：" class="filetitle bitianicon">
                <commonFileUpload @upLoadFile="upClarifyLoadFile" :fileType="fileClarifyType" :oldFileName="fileClarifyName"></commonFileUpload>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="澄清修改内容：" class="bitianicon">
                <div class="editor">
                  <editor ref="ueditor" class="ueditor" :editread="false" :content="content"></editor>
                </div>
              </el-form-item>
            </el-col>
          </el-row>
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
        <!--出让文件信息-->
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
              prop="landInformation.sectionNumber"
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
              prop="landInformation.methodOfTransfer"
              align="left"
              label="出让方式"
              width="200"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.landInformation.methodOfTransfer === 3">
                  <span class="statusbox">资格后审</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.landInformation.methodOfTransfer === 4">
                  <span class="statusbox">资格预审</span>
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
          <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import { landClarifyFile } from '@/api'
export default {
  components: {
    commonFileUpload,
    editor
  },
  data () {
    return {
      ruleForm: {},
      // 业务类型
      fileType: 3,
      fileClarifyType: 4,
      oldFileName: '',
      fileClarifyName: '',
      // 文件上传保存的数组
      fileArry: [],
      fileClarifyArry: [],
      content: '',
      // 关联相关信息
      tableDataSeletion: [],
      isFileData: [
        {
          label: 1,
          name: '是'
        }, {
          label: 0,
          name: '否'
        }
      ],
      multipleSelection: [], // 关联信息提交时的数组
      // 提交保存状态
      subStatus: false
    }
  },
  methods: {
    submitForm (ruleForm, flag) {
    //   if (this.fileArry.length === 0) {
    //     this.$message({
    //       type: 'warning',
    //       message: '请先上传文件'
    //     })
    //     return false
    //   }
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择关联项目'
        })
        return false
      }
      this.subStatus = true
      this.ruleForm.status = flag
      let that = this
      this.ruleForm.content = this.$refs.ueditor.getContent() // 富文本内容获取
      let refclardocSections = []
      landClarifyFile.update(this.ruleForm).then((res) => {
        refclardocSections = that.multipleSelection.map(function (item) {
          let refclardocSection = {}
          refclardocSection.landClarifyFileId = res.data.landClarifyFile.objectId
          refclardocSection.landClarifyFileSectionid = item.landInformation.objectId
          refclardocSection.isChecked = 1
          refclardocSection.isSend = 0
          return refclardocSection
        })
        landClarifyFile.updateRelation(refclardocSections).then((res) => {
          // 跳转到出让文件列表页
          this.$router.push({path: '/lt/transferor/prequalification-file/sub-clarify-file/index?roledsType=1&showStatus=false'})
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
      this.fileArry = file
    },
    upClarifyLoadFile (file) {
      this.fileClarifyArry = file
    },
    detail () {
      let url = {
        annoId: this.$route.query.annoId
      }
      console.log(url)
      landClarifyFile.selectByAnnoId(url).then((res) => {
        this.tableDataSeletion = res.data.refLandClarifyFiles
      })
      console.log(this.tableDataSeletion)
      landClarifyFile.detail(this.$route.query.objectId).then((res) => {
        this.ruleForm = res.data.landClarifyFile
        this.content = res.data.landClarifyFile.content
        this.$refs.ueditor.setContent(this.content) // 设置富文本内容
        let url = {
          landClarifyFileId: res.data.landClarifyFile.objectId
        }
        landClarifyFile.detailRelation(url).then((res) => {
          this.tableDataSeletion.map((tableItem, index) => {
            if (tableItem.isChecked === 1) {
              this.$refs.multipleTable.toggleRowSelection(this.tableDataSeletion[index], true)
            } else {
              this.$refs.multipleTable.toggleRowSelection(this.tableDataSeletion[index], false)
            }
          })
        })
      })
    }
  },
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
