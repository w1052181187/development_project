<template>
  <div id="tranaddindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>拍卖/挂牌文件</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/auction-listed-file/sub-auc-listed?roledsType=1&showStatus=false' }">提交出让文件</el-breadcrumb-item>
        <el-breadcrumb-item>添加出让文件</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <!--公告内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">出让文件信息</span>
          <el-col :span="24">
            <el-form-item label="出让文件：" prop="name" class="filetitle bitianicon">
              <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName" :type="typeFile"></commonFileUpload>
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
                <div class="statusbigbox" v-if="scope.row.landInformation.methodOfTransfer === 1">
                  <span class="statusbox">拍卖</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.landInformation.methodOfTransfer === 2">
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
import {tdocuments, fileInfo} from '@/api'
export default {
  components: {
    commonFileUpload
  },
  data () {
    return {
      ruleForm: {},
      // 业务类型
      fileType: 3,
      // 文件类型：土地
      typeFile: 2,
      // 文件上传保存的数组
      fileArry: [],
      // 提交保存状态
      subStatus: false,
      // 关联相关信息
      tableDataSeletion: [],
      multipleSelection: [], // 关联信息提交时的数组
      oldFileName: ''
    }
  },
  methods: {
    submitForm (ruleForm, flag) {
      if (this.fileArry.length === 0) {
        this.$message({
          type: 'warning',
          message: '请先上传文件'
        })
        return false
      }
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择关联项目'
        })
        return false
      }
      this.ruleForm.status = flag
      this.ruleForm.type = this.typeFile
      this.ruleForm.annoId = this.$route.query.objectId
      this.ruleForm.transferType = 1
      this.subStatus = true
      tdocuments.save(this.ruleForm).then((res) => {
        this.fileArry[0].relatedCode = res.data.transDocuments.code
        // 将附件信息入库
        fileInfo.saveFile(this.fileArry).then((res) => {
          this.fileArry = []
        })
        // 将关联关系入库
        this.multipleSelection = this.multipleSelection.map(function (item) {
          item.transDocId = res.data.transDocuments.objectId
          item.isChecked = 1
          return item
        })
        this.subStatus = false
        tdocuments.saveRelation(this.multipleSelection).then((res) => {
          this.$router.push({path: '/lt/transferor/auction-listed-file/sub-auc-listed?roledsType=1'})
        })
      })
    },
    cancelBtn () {
      this.$router.push({path: '/lt/transferor/auction-listed-file/sub-auc-listed?roledsType=1'})
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
    // 查询关联的项目信息
    querySectionList () {
      let url = {
        annoId: this.$route.query.objectId,
        isChecked: 0,
        type: 2,
        flag: 0,
        _t: new Date().getTime()
      }
      tdocuments.selectRelation(url).then((res) => {
        this.tableDataSeletion = res.data.refTrandocSectionList
      })
    },
    handleSelectionAll (selection) {
      if (selection.length === 0) {
        this.multipleSelection = []
      } else {
        this.multipleSelection = selection
      }
      console.log(this.multipleSelection)
    },
    // 将子组件获取到的数据传给父组件
    upLoadFile (file) {
      this.fileArry = file
    },
    detailBtn (scope) {
      this.$router.push({path: `/lt/transferor/project/massif-register/detail/${scope.row.landInformation.objectId}`, query: {roledsType: 1, showStatus: false}})
      // this.$router.push({path: `/lt/transferor/announcement/sub-announ/detail/${scope.row.landInformation.objectId}`, query: {roledsType: 1, showStatus: false, methodOfTransfer: scope.row.type}})
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
