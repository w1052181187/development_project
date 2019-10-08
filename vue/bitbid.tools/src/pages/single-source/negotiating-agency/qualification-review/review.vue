<template>
  <div id="quaReview" class="content_bgibox">
    <el-row>
      <el-col :span="24">
        <!--评审-->
        <div class="setup_bigbox">
          <div class="proinfobox">
            <span class="line_bigbox"></span>
            <span class="title_bigbox">评审</span>
          </div>
          <!--谈判供应商选择-->
          <div class="type_selectbox">
            <div class="type_selecttitle">谈判供应商：</div>
            <el-select v-model="reviewSelId" placeholder="请选择" @change="reviewSelects">
              <el-option
                v-for="item in reviewSelect"
                :key="item.objectId"
                :label="item.userName"
                :value="item.objectId">
              </el-option>
            </el-select>
            <div class="create_box">
              <el-button type="primary" @click="contrastBtn" size="medium">查看对比投标文件</el-button>
            </div>
          </div>
          <!--谈判供应商选择-->
          <!--供应商选择数据-->
          <div class="tableDataSupplier_box">
            <el-table
              :data="tableDataSupplier"
              border
              style="width: 60%">
              <el-table-column
                prop="checkIndex"
                label="序号"
                width="80"
                align="left">
              </el-table-column>
              <el-table-column
                prop="itemName"
                label="评审因素"
                width="180"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="itemDesc"
                label="评审标准"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="checkDesc"
                label="是否合格" align="left" width="180">
                <template slot-scope="scope">
                  <el-switch
                    v-model="scope.row.checkDesc"
                    active-color="#13ce66"
                    active-value="合格"
                    inactive-value="不合格"
                    :disabled="subStatus"
                    inactive-color="#ff4949" :title="scope.row.checkReason" @change="changeStatusQualified(scope)" v-if="scope.row.children === null">
                  </el-switch>
                  <!--不合格理由填写-->
                  <el-dialog title="不合格原因录入" :visible.sync="dialogFormVisible" width="30%" :before-close="cancelBtn">
                    <el-form :model="unqualifiedForm" :rules="formUnqualifiedRules" ref="unqualifiedForm">
                      <el-form-item label="不合格原因" prop="checkReason" :label-width="formLabelWidth">
                        <el-input type="textarea" v-model.trim="unqualifiedForm.checkReason"></el-input>
                      </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer unqualifiedbox">
                      <el-button type="primary" class="ConfirmationBtn" @click="confirmationBtns('unqualifiedForm')">确 认</el-button>
                      <el-button class="closeBtn" @click="closeBtn('unqualifiedForm')">关 闭</el-button>
                    </div>
                  </el-dialog>
                  <!--不合格理由填写-->
                </template>
              </el-table-column>
            </el-table>
          </div>
          <!--供应商选择数据-->
        </div>
        <!--评审-->
      </el-col>
    </el-row>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="95%"
      top="auto" class="dialog_box">
      <el-row>
        <el-col :span="12" style="padding-right: 5px;box-sizing: border-box">
          <div class="pdf_bigbox">
            <div class="pdf_select_box">
              <el-tabs v-model="fileValueTender" @tab-click="filePdfSelect(fileValueTender)" style="width: 95%;">
                <el-tab-pane
                  v-for="item in fileListTender"
                  :key="item.objectId"
                  :name="item.objectId"
                  :label="item.label">
                </el-tab-pane>
              </el-tabs>
            </div>
            <div class="pdf_div">
              <iframe id="pdf_file1" :src="pdfUrl" frameborder="0" class="pdf_iframe"></iframe>
            </div>
          </div>
        </el-col>
        <el-col :span="12" style="padding-left: 5px;box-sizing: border-box">
          <div class="pdf_bigbox">
            <div class="pdf_select_box">
              <el-tabs v-model="fileValueBid" @tab-click="filePdfSelect2(fileValueBid)" style="width: 95%;">
                <el-tab-pane
                  v-for="item in fileListBid"
                  :key="item.objectId"
                  :name="item.objectId"
                  :label="item.label">
                </el-tab-pane>
              </el-tabs>
            </div>
            <div class="pdf_div">
              <iframe id="pdf_file2" :src="pdfUrl2" frameborder="0" class="pdf_iframe"></iframe>
            </div>
          </div>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>
<script>
import { qualificationReview } from '@/api'
import {pdfUrl} from '@/assets/js/common'
import { handlePdfDisplay } from '@/utils/common'
export default {
  watch: {
    dialogFormVisible: function (value) {
      handlePdfDisplay(value)
    }
  },
  data () {
    return {
      pageSize: 1000, // 每页展示条数
      pageNo: 0,
      // 自定义序号
      dataIndex: 1,
      // 谈判供应商选择
      reviewSelect: [],
      // 默认选中
      reviewSelId: '',
      // 自定义objectId
      checkObjectId: '',
      // pdf地址
      basePdfUrl: `${pdfUrl}`,
      pdfUrl: '',
      pdfUrl2: '',
      // 供应商选择后数据
      tableDataSupplier: [],
      // 不合格理由弹框
      formLabelWidth: '120px',
      dialogFormVisible: false,
      unqualifiedForm: {},
      formUnqualifiedRules: {
        checkReason: [
          { required: true, message: '请输入不合格理由', trigger: ['blur', 'change'] },
          {min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: ['blur', 'change']}
        ]
      },
      dialogVisible: false, // 弹框自定义
      // pdf列表数据
      fileListBid: [], // 招标
      fileListTender: [], // 投标
      fileValueTender: '',
      fileValueBid: '',
      subStatus: false,
      pdfWidth: '',
      pdfHeight: ''
    }
  },
  methods: {
    // 供应商数据
    negotiatingSuppliersSeleList () {
      let url = {
        checkType: 'ZGXSH'
      }
      qualificationReview.negotiatingSuppliersSele(url).then((res) => {
        this.reviewSelect = res.data.BidOpenUserList
        this.reviewSelId = this.reviewSelect[0].objectId
        this.negotiatingSuppliersTable()
      })
    },
    // 供应商表格数据
    negotiatingSuppliersTable () {
      let url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        tenderId: this.reviewSelId,
        checkType: 'ZGXSH'
      }
      qualificationReview.negotiatingSuppliersList(url).then((res) => {
        this.tableDataSupplier = res.data.BidOpenCheckList
        this.tableDataSupplier.map((its) => {
          if (its.checkStatus !== 0) {
            this.subStatus = true
          }
          if (its.checkReason !== null) {
            its.checkReason = '不合格理由：' + its.checkReason
          }
        })
      })
    },
    // 选择切换数据
    reviewSelects (objectId) {
      this.reviewSelId = objectId
      this.negotiatingSuppliersTable()
      let tender
      this.reviewSelect.map((item) => {
        if (Object.is(item.objectId, objectId)) {
          tender = item.userName
        }
      })
      if (!this.fileListTender) {
        return
      }
      this.fileListTender.map((item) => {
        if (Object.is(item.label, tender)) {
          this.fileValueTender = item.objectId
          this.filePdfSelect(this.fileValueTender)
        }
      })
    },
    // 选择是否合格
    changeStatusQualified (scope) {
      this.checkObjectId = scope.row.objectId
      if (scope.row.checkDesc === '不合格') {
        this.dialogFormVisible = true
      } else {
        let reasonData = {
          checkReason: '',
          checkDesc: '合格',
          objectId: this.checkObjectId
        }
        qualificationReview.preservationUnqualified(reasonData).then((res) => {
          this.negotiatingSuppliersTable()
        })
      }
    },
    // 点击关闭按钮
    cancelBtn () {
      this.$confirm(`关闭后将恢复到合格状态, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.negotiatingSuppliersTable()
        this.dialogFormVisible = false
      }).catch(() => {
        return false
      })
    },
    // 点击下边关闭按钮
    closeBtn () {
      this.$confirm(`关闭后将恢复到合格状态, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.negotiatingSuppliersTable()
        this.dialogFormVisible = false
      }).catch(() => {
        return false
      })
    },
    // 确认
    confirmationBtns (unqualifiedForm) {
      this.$refs[unqualifiedForm].validate((valid) => {
        if (valid) {
          let reasonData = {
            checkReason: this.unqualifiedForm.checkReason,
            checkDesc: '不合格',
            objectId: this.checkObjectId
          }
          qualificationReview.preservationUnqualified(reasonData).then((res) => {
            this.negotiatingSuppliersTable()
            this.dialogFormVisible = false
            this.$refs['unqualifiedForm'].resetFields()
          })
        } else {
          return false
        }
      })
    },
    // pdf文件选择列表
    pdfSelectLists () {
      let url = {
        checkType: 'ZGXSH'
      }
      qualificationReview.pdfSelectList(url).then((res) => {
        this.fileListTender = res.data.FileList
        if (!this.fileListTender) {
          return
        }
        if (this.fileListTender.length > 1) {
          this.fileValueTender = this.fileListTender[1].objectId
        } else {
          this.fileValueTender = this.fileListTender[0].objectId
        }
        this.filePdfSelect(this.fileValueTender)
        this.fileListBid = this.fileListTender
        this.fileValueBid = this.fileListBid[0].objectId
        this.filePdfSelect2(this.fileValueBid)
      })
    },
    // 上边pdf列表选择
    filePdfSelect (doc) {
      this.pdfUrl = this.basePdfUrl + '?doc=' + doc + '&width=' + this.pdfWidth + '&height=' + this.pdfHeight
    },
    filePdfSelect2 (doc) {
      this.pdfUrl2 = this.basePdfUrl + '?doc=' + doc + '&width=' + this.pdfWidth + '&height=' + this.pdfHeight
    },
    contrastBtn () {
      this.dialogVisible = true
    }
  },
  mounted () {
    let o = document.getElementById('pdf_file1') || document.getElementById('pdf_file2')
    if (o) {
      this.pdfWidth = (o.clientWidth || o.offsetWidth) - 20
      this.pdfHeight = (o.clientHeight || o.offsetHeight) - 10
    }
    this.negotiatingSuppliersSeleList()
    this.pdfSelectLists()
  }
}
</script>
<style lang="less">
  #quaReview{
    margin-top: 20px;
    .setup_bigbox{
      box-shadow: 0 2px 0px 0px rgba(0,0,0,0.08) inset;
    }
    .unqualifiedbox{
      text-align: center;
    }
    .dialog_box>.el-dialog{
      background: transparent;
    }
    .pdf_iframe{
      height: 90%;
    }
    .dialog_box .el-dialog__headerbtn{
      background-color: rgb(57, 60, 79);
      border-radius: 50%;
      height: 25px;
      width: 25px;
    }
    .el-dialog__body{
      padding: 15px 20px;
    }
    .dialog_box .el-icon-close:before {
      content: "\E60F";
      color: #fff;
      font-weight: bold;
    }
    .create_box{
      float: right;
      margin-right: 50px;
    }
  }
</style>
