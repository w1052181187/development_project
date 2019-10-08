<template>
  <div class="cloudcontent">
    <div class="main">
      <el-form :model="updateForm" :rules="rules" ref="updateForm" :validate-on-rule-change="true" :loading = "formLoding">
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目名称：">
              <el-input v-model="updateForm.tenderProjectName" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目编号：">
              <el-input v-model="updateForm.tenderProjectCode" disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="标段名称：">
              <el-input v-model="updateForm.currentBidSectionName" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标段编号：">
              <el-input v-model="updateForm.currentBidSectionCode" disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="邀请公告名称："  prop="title">
              <el-input v-model="updateForm.title"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="递交投标文件方法："  prop="bidDocReferMethod">
              <el-input v-model="updateForm.bidDocReferMethod"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="开标时间：" prop="bidOpeningTime">
              <el-date-picker
                v-model="updateForm.bidOpeningTime"
                value-format="yyyy-MM-dd HH:mm:ss"
                type="datetime"
                start-placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开标地点：" prop="bidOpeningAddress">
              <el-input v-model="updateForm.bidOpeningAddress"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="公告链接：">
              <el-input v-model="updateForm.bulletinUrl"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发布网站：">
              <el-input v-model="updateForm.publishUrl"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="标段信息："  prop="bidSectionList">
              <el-table
                ref="multipleTable"
                :data="bidSectionList"
                border
                style="width: 100%" header-cell-class-name="tableheader"
                :row-key="getRowKeys"
                @selection-change="handleSelectionChange">
                <el-table-column
                  type="selection"
                  :reserve-selection="true"
                  width="50">
                </el-table-column>
                <el-table-column
                  prop="bidSectionCode"
                  label="标段编号"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="bidSectionName"
                  label="标段名称"
                  show-overflow-tooltip>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="供应商信息："  prop="supplierBaseList">
              <el-button class="addbtn" @click="addSupplierBtn">+ 新增</el-button>
              <el-table
                :data="updateForm.supplierBaseList"
                border
                style="width: 100%" header-cell-class-name="tableheader">
                <el-table-column
                  prop="supplierName"
                  label="供应商名称">
                  <template slot-scope="scope">
                    <el-input v-model="scope.row.supplierName" placeholder="请输入内容" readonly></el-input>
                    <el-button type="primary" style="position: absolute; right: 10px; top: 4px;" @click="btnChoose(scope.$index)">选择</el-button>
                  </template>
                </el-table-column>
                <el-table-column
                  label="操作" align="center" width="100">
                  <template slot-scope="scope">
                    <el-button type="text" size="small" @click="delSupplierBtn(scope.$index)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24" class="ueditor_box">
            <el-form-item label="邀请书内容：">
              <editor ref="ueditor" class="ueditor" :editread="editread" :content = "content"></editor>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="附件：" class="bitianicon">
              <upload-file @uploadSuccess="uploadOtherSuccess" @deleteSuccess="deleteSuccess"
                           :fileArrays="updateForm.fileInformationList"
                           fileType="9"
                           isImage="0">
              </upload-file>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submit('updateForm', 0)" :loading="isSubmiting">保存</el-button>
          <el-button type="primary" class="submitBtn" @click="submit('updateForm', 1)" :loading="isSubmiting">提交</el-button>
          <el-button class="cancal" @click="close">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
    <bidder-dialog :tenderSystemCode="tenderSystemCode" :showVisible="showBidderVisible" @selected="selectedBidder" @showDialog="showBidderDialog"></bidder-dialog>
    <submitApproveDialog :flowStatus="flowStatus" :tenderSystemCode="updateForm.tenderSystemCode" :showVisible="approveDialogVisable" @confirmSubmit="confirmSubmit" @handleCancel="cancelApprove"></submitApproveDialog>
  </div>
</template>

<script>
import editor from '@/components/ueditor/ueditor.vue'
import uploadFile from '@/components/upload/publicFileUpload'
import {bulletinInfo} from '@/api/project/index'
import bidderDialog from '@/pages/project/projectProcess/common/bidder_dialog.vue'
import submitApproveDialog from '@/pages/project/commonCompents/submitApproveDialog'
export default {
  components: {
    editor,
    uploadFile,
    bidderDialog,
    submitApproveDialog
  },
  data () {
    return {
      flowStatus: 4,
      formLoding: false,
      isSubmiting: false,
      updateForm: {
        tenderProjectName: '',
        fileInformationList: [],
        approvalFlowExecutorList: [],
        noticeList: [],
        bidSectionList: []
      },
      rules: {
        title: [
          { required: true, message: '公告名称不能为空', trigger: ['blur', 'change'] }
        ],
        bidDocReferMethod: [
          { required: true, message: '递交投标文件方法不能为空', trigger: ['blur', 'change'] }
        ],
        bidSectionList: [
          { required: true, message: '请选择标段', trigger: ['blur', 'change'] }
        ],
        supplierBaseList: [
          { required: true, message: '供应商不能为空', trigger: ['blur', 'change'] }
        ],
        bidOpeningTime: [
          {required: true, message: '开标时间不能为空', trigger: ['blur', 'change']}
        ],
        bidOpeningAddress: [
          {required: true, message: '开标地点不能为空', trigger: ['blur', 'change']}
        ],
        prequalificationTime: [
          {required: true, message: '资格预审会时间不能为空', trigger: ['blur', 'change']}
        ],
        prequalificationAddress: [
          {required: true, message: '资格预审会地点不能为空', trigger: ['blur', 'change']}
        ]
      },
      // 富文本
      editread: false,
      bidSectionList: [], // 页面上展示出来的可选择的标段信息
      tenderProjectInfo: {}, // 用于查询是否为资格预审的项目（公告信息、招标项目类型、招标组织形式）
      tenderSystemCode: this.$route.query.tenderSystemCode,
      sectionSystemCode: this.$route.query.sectionSystemCode,
      bulletinInfoSystemCode: this.$route.query.code,
      showBidderVisible: false,
      clickRowIndex: 0,
      content: '',
      approveDialogVisable: false
    }
  },
  methods: {
    handleSelectionChange (val) {
      this.updateForm.bidSectionList = val
    },
    /**
     * 增加供应商
     */
    addSupplierBtn () {
      this.updateForm.supplierBaseList.push({
        supplierName: '',
        contactName: '',
        contactNumber: '',
        contactEmail: ''
      })
    },
    delSupplierBtn (index) {
      this.updateForm.supplierBaseList.splice(index, 1)
    },
    // 上传附件
    uploadOtherSuccess (file) {
      this.updateForm.fileInformationList.push(file)
    },
    deleteSuccess (fileId) {
      this.updateForm.fileInformationList = this.updateForm.fileInformationList.filter(item => item.relativePath !== fileId)
    },
    /** 获取当前公告信息 */
    getChooseBulletinInfoInfo () {
      this.formLoding = true
      bulletinInfo.getOneByRelatedCode(this.bulletinInfoSystemCode, this.sectionSystemCode).then((res) => {
        if (res.data.bulletinInfo) {
          this.updateForm = res.data.bulletinInfo
          // 获取文本编辑器的内容
          if (this.updateForm.content) {
            this.$refs.ueditor.setContent(this.content = this.updateForm.content)
          }
          // 设置标段信息
          this.bidSectionList = (res.data.chooseBidSectionList).concat(res.data.notUsedBidSectionList)
          // 为该公告绑定的公告进行选中展示
          this.$refs.multipleTable.clearSelection() // 清空上次的标段选择信息
          // 给已选择的标段设置值
          let sectionIndexList = this.getCheckedSectionIndexList(res.data.chooseBidSectionList)
          let checkedSectionList = []
          sectionIndexList.forEach(index => checkedSectionList.push(this.bidSectionList[index]))
          this.toggleSelection(checkedSectionList)
          // 设置附件信息
          if (this.updateForm.fileInformationList.length > 0) {
            this.updateForm.fileInformationList.forEach(item => {
              item.objectId = null
            })
          }
          this.updateForm.supplierBaseList = res.data.supplierBaseList
          if (this.updateForm.supplierBaseList.length > 0) {
            this.updateForm.supplierBaseList.forEach(item => {
              item.objectId = null
            })
          }
        }
        this.formLoding = false
      })
    },
    /** 得到公告中所包含标段的下标索引 */
    getCheckedSectionIndexList (chooseSectionList) {
      let results = []
      for (let i = 0; i < this.bidSectionList.length; i++) {
        for (let j = 0; j < chooseSectionList.length; j++) {
          if (this.bidSectionList[i].objectId === chooseSectionList[j].objectId) {
            results.push(i)
          }
        }
      }
      return results
    },
    /** 弹窗-标段的默认选择 */
    toggleSelection (rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row, true)
        })
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    },
    // 获取row的key值
    getRowKeys (row) {
      return row.objectId
    },
    validLinks (str) {
      const reg = /^http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- ./?%&=]*)?$/
      return reg.test(str)
    },
    // 提交/保存
    submit (form, status) {
      // 公告链接格式的判断
      if (this.updateForm.bulletinUrl && !this.validLinks(this.updateForm.bulletinUrl)) {
        this.$message({
          type: 'warning',
          message: '公告链接格式有误！'
        })
        return false
      }
      this.$refs[form].validate((valid) => {
        if (valid) {
          if (Object.is(status, 0)) {
            this.approveDialogVisable = false
            this.submitData(status)
          } else {
            this.approveDialogVisable = true
          }
        } else {
          return false
        }
      })
    },
    submitData (status) {
      // 判断存在未录入的供应商信息
      if (!this.checkTableData(this.updateForm.supplierBaseList)) {
        this.$message.warning('存在未录入的供应商信息！')
        return false
      }
      // 判断供应商信息是否有重复
      let result = this.checkSupplierName(this.updateForm.supplierBaseList)
      let resultMsg = ''
      if (result) {
        resultMsg = '供应商信息有重复!'
        this.$message({
          type: 'warning',
          message: resultMsg
        })
        return false
      }
      if (this.updateForm.fileInformationList.length === 0) {
        this.$message.warning('附件不能为空！')
        return false
      }
      // 获取文本编辑器中的内容
      if (this.$refs.ueditor.getContent()) {
        this.updateForm.content = this.$refs.ueditor.getContent()
      }
      let msg = Object.is(status, 1) ? '提交后数据将锁定，不允许修改，确认要提交吗?' : '确认要保存吗?'
      this.$confirm(msg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        beforeClose: (action, instance, done) => {
          done()
        }
      }).then(() => {
        this.isSubmiting = true
        // 根据type设置当前招标项目的状态
        if (Object.is(status, 1)) {
          this.updateForm.auditStatus = 4 // 暂时不考虑审批
          this.updateForm.currentBidSectionSystemCode = this.$route.query.sectionSystemCode
        } else {
          this.updateForm.auditStatus = 0
        }
        this.updateForm.fileInformationList.map(item => {
          item.objectId = null
        })
        bulletinInfo.save(this.updateForm).then((res) => {
          this.isSubmiting = false
          if (res.data.resCode === '0000') {
            this.close()
          }
        })
      }).catch(() => {
        this.isSubmiting = false
        return false
      })
    },
    // 确认提交
    confirmSubmit (record) {
      this.updateForm.approvalFlowExecutorList = []
      this.updateForm.noticeList = []
      for (let i = 0; i < record.approveList.length; i++) {
        let obj = {
          usersId: record.approveList[i].objectId,
          flowLevel: i + 1
        }
        this.updateForm.approvalFlowExecutorList.push(obj)
      }
      if (record.noticeList && record.noticeList.length > 0) {
        this.updateForm.noticeList = record.noticeList
      }
      this.submitData(1)
    },
    cancelApprove () {
      this.approveDialogVisable = false
    },
    /** 判断是否存在信息不完全的供应商信息 */
    checkTableData (supplierBaseList) {
      let i = 0
      for (; i < supplierBaseList.length; i++) {
        if (supplierBaseList[i].supplierName === '') {
          return false
        }
      }
      return true
    },
    /** 判断供应商信息是否有重复 */
    checkSupplierName (supplierBaseList) {
      let flag = false
      for (let i = 0; i < supplierBaseList.length; i++) {
        for (let j = i + 1; j < supplierBaseList.length; j++) {
          if (supplierBaseList[i].supplierName === supplierBaseList[j].supplierName) {
            flag = true
            break
          }
        }
      }
      return flag
    },
    // 取消
    close () {
      this.$store.commit('delete_tabs', this.$route.fullPath)
      this.$router.go(-1)
    },
    // 选择投标人
    btnChoose (index) {
      this.clickRowIndex = index
      this.showBidderVisible = true
    },
    // 选择投标人弹窗 打开/关闭
    showBidderDialog () {
      this.showBidderVisible = !this.showBidderVisible
    },
    // 已选择投标人信息
    selectedBidder (obj) {
      this.$set(this.updateForm.supplierBaseList[this.clickRowIndex], 'supplierName', obj.name)
      // this.updateForm.supplierBaseList[this.clickRowIndex].supplierName = obj.name
      this.updateForm.supplierBaseList[this.clickRowIndex].contactName = obj.contactName
      this.updateForm.supplierBaseList[this.clickRowIndex].contactNumber = obj.contactNumber
      this.updateForm.supplierBaseList[this.clickRowIndex].contactEmail = obj.contactEmail
    }
  },
  watch: {
    '$route': 'getChooseBulletinInfoInfo'
  },
  mounted () {
    this.getChooseBulletinInfoInfo()
  }
}
</script>

<style scoped>
  .addbtn{
    border: 1px solid #3f63f6;
    height: 32px;
    padding: 7px 12px;
    margin-bottom: 12px;
  }
  .btn_right{
    float: right;
  }
</style>
