<template>
  <div id="tranaddindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>资格预审文件</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/prequalification-file/sub-tender-file/index?roledsType=1&showStatus=false'}">提交资格预审文件</el-breadcrumb-item>
        <el-breadcrumb-item>修改资格预审文件</el-breadcrumb-item>
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
                <div class="statusbigbox">
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
import { landPreDoc } from '@/api'
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
      fileType: 3,
      // 文件上传保存的数组
      fileArry: [],
      // 关联相关信息
      tableDataSeletion: [
        {
          sectionNumber: '地块编号地块编号地块编号',
          methodOfSupply: 1,
          methodOfTransfer: 1
        }
      ],
      multipleSelection: [], // 关联信息提交时的数组
      // 提交保存状态
      subStatus: false,
      oldFileName: ''
    }
  },
  methods: {
    submitForm (ruleForm, flag) {
      //      if (this.fileArry.length === 0) {
      //        this.$message({
      //          type: 'warning',
      //          message: '请先上传文件'
      //        })
      //        return false
      //      }
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择关联项目'
        })
        return false
      }
      this.$refs[ruleForm].validate((valid) => {
        if (valid) {
          this.ruleForm.status = flag
          // let that = this
          landPreDoc.update(this.ruleForm).then((res) => {
            // 将附件信息入库
            //            if (that.fileArry.length !== 0) {
            //              that.fileArry[0].relatedCode = that.ruleForm.code
            //              fileInfo.deleteFile(this.originalId).then((res) => {
            //                fileInfo.saveFile(this.fileArry).then((res) => {
            //                  that.fileArry = []
            //                })
            //              })
            //            }
            this.multipleSelection = this.multipleSelection.map(function (item) {
              item.transDocId = res.data.landPreDoc.objectId
              item.isChecked = 1
              return item
            })
            landPreDoc.saveRelation(this.multipleSelection).then((res) => {
              this.$router.push({path: '/lt/transferor/prequalification-file/sub-tender-file/index?roledsType=1&showStatus=false'})
            })
          })
          this.subStatus = true
        } else {
          return false
        }
      })
    },
    detail () {
      let url = {
        annoId: this.$route.query.annoId,
        flag: 1,
        transDocId: this.$route.query.objectId,
        type: 2
      }
      landPreDoc.selectByAnnoId(url).then((res) => {
        console.log(res)
        this.tableDataSeletion = res.data.refLandPreSectionList
        landPreDoc.detail(this.$route.query.objectId).then((res) => {
          this.ruleForm = res.data.landPreDoc
          if (res.data.landPreDoc.fileInformations) {
            this.oldFileName = res.data.landPreDoc.fileInformations.fileName
          }
          let url = {
            transDocId: res.data.landPreDoc.objectId,
            type: 2
          }
          landPreDoc.detailRelation(url).then((res) => {
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
