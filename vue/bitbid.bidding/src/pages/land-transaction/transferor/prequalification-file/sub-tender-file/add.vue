<template>
  <div id="tranaddindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>资格预审文件</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/prequalification-file/sub-tender-file/index?roledsType=1&showStatus=false'}">提交资格预审文件</el-breadcrumb-item>
        <el-breadcrumb-item>添加资格预审文件</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm" ref="ruleForm" label-width="160px" class="demo-ruleForm">
        <!--出让文件信息-->
        <div class="proinfobox">
          <span class="title">出让文件信息</span>
          <el-col :span="24">
            <el-form-item label="资格预审文件：" prop="name" class="filetitle bitianicon">
              <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></commonFileUpload>
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
              prop="landInformation.methodOfSupply"
              align="left"
              label="供应方式"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.landInformation.methodOfSupply === 1">
                  <span class="statusbox">招拍挂出让</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.landInformation.methodOfSupply === 2">
                  <span class="statusbox">协议出让</span>
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
              <div class="statusbigbox" v-if="scope.row.landInformation.methodOfTransfer === 1">
                <span class="statusbox">拍卖</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.landInformation.methodOfTransfer === 2">
                <span class="statusbox">挂牌</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.landInformation.methodOfTransfer === 3">
                <span class="statusbox">资格后审</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.landInformation.methodOfTransfer === 4">
                <span class="statusbox">资格预审</span>
              </div>
              </template>-->
            </el-table-column>
            <el-table-column关联项目信息
              label="操作" width="140" align="center">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  详情
                </el-button>
              </template>
            </el-table-column关联项目信息>
          </el-table>
        </div>
        <!--关联项目信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('ruleForm', 1)" >提交</el-button>
          <el-button type="primary" class="save" @click="submitForm('ruleForm', 0)" :loading="subStatus">保存</el-button>
          <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
import { landPreDoc, fileInfo } from '@/api'
import {fileSize} from '@/assets/js/common'

export default {
  components: {
    commonFileUpload
  },
  data () {
    return {
      ruleForm: {
        bidEvaluationMethod: 1,
        bidOpeningWay: 1,
        bidEvaluationMethods: 1
      },
      // 业务类型
      fileType: 17,
      // 文件上传保存的数组
      fileArry: [],
      // 关联相关信息
      tableDataSeletion: [],
      multipleSelection: [], // 关联信息提交时的数组
      // 提交保存状态
      subStatus: false,
      oldFileName: ''
    }
  },
  methods: {
    submitForm (ruleForm, flag) {
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择关联项目'
        })
        return false
      }
      this.ruleForm.status = flag
      this.ruleForm.annoId = this.$route.query.annoId
      this.$refs[ruleForm].validate((valid) => {
        if (valid) {
          console.log(this.ruleForm)
          landPreDoc.save(this.ruleForm).then((res) => {
            this.fileArry[0].relatedCode = res.data.landPreDoc.code
            // // 将附件信息入库
            fileInfo.saveFile(this.fileArry).then((res) => {
              this.fileArry = []
            })
            // 将关联关系入库
            this.multipleSelection = this.multipleSelection.map(function (item) {
              item.transDocId = res.data.landPreDoc.objectId
              item.isChecked = 1
              item.type = 2
              return item
            })
            console.log(this.multipleSelection)
            landPreDoc.saveRelation(this.multipleSelection).then((res) => {
              this.$router.push({path: '/lt/transferor/prequalification-file/sub-tender-file/index?roledsType=1'})
            })
          })
        } else {
          return false
        }
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
    // ****************************************************文件上传**********************************
    // 文件大小限制
    before (file) {
      return fileSize(this, file)
    },
    // 将子组件获取到的数据传给父组件
    upLoadFile (file) {
      this.fileArry = file
    },
    landInfo () {
      let url = {
        annoId: this.$route.query.annoId,
        isChecked: 0,
        type: 2
      }
      console.log(url)
      landPreDoc.selectByAnnoId(url).then((res) => {
        console.log(res)
        this.tableDataSeletion = res.data.refLandPreSectionList
      })
      console.log(this.tableDataSeletion)
    }
  },
  created () {
    this.landInfo()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
