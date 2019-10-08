<template>
  <div class="cloudcontent"  id="cloud_workApprovalAdd">
    <div class="main" v-loading="formLoading">
      <h3 class="submitFormTitle">{{templateName}}</h3>
      <el-form class="submitForm" :model="submitForm" label-width="120px" ref="submitForm">
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目名称：">
              <el-input v-model="submitForm.tenderProjectName" readonly placeholder="请选择项目名称">
                <el-button slot="append" icon="el-icon-search" @click="chooseTenderProject"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-for="(item, index) in submitForm.item" :key="index">
          <el-col :span="item[0].type === 5 || item[0].type === 6 ? 24 : 12">
            <el-form-item :label="item[0].name + '：'" :class="item[0].isFillIn === '1' ? 'bitianicon' : ''"
                          :prop="'item.' + index + '[0].value'"
                          :rules="handleRules(item[0].isFillIn === '1', item[0].type, item[0].name)"
            >
              <el-input autocomplete="off"
                        v-if="item[0].type === 1"
                        v-model="item[0].value">
              </el-input>
              <el-select v-model="item[0].value" placeholder="请选择" v-if="item[0].type === 2">
                <el-option
                  v-for="opItem in item[0].optional"
                  :key="opItem.value"
                  :label="opItem.label"
                  :value="opItem.value">
                </el-option>
              </el-select>
              <el-radio-group v-model="item[0].value" v-if="item[0].type === 3">
                <el-radio v-for="raItem in item[0].optional" :label="raItem.value" :key="raItem.value" >{{raItem.label}}</el-radio>
              </el-radio-group>
              <el-checkbox-group v-model="item[0].value" v-if="item[0].type === 4">
                <el-checkbox v-for="raItem in item[0].optional" :label="raItem.value" :key="raItem.value" >{{raItem.label}}</el-checkbox>
              </el-checkbox-group>
              <el-date-picker
                v-if="item[0].type === 7"
                v-model="item[0].value"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
              <el-date-picker
                v-if="item[0].type === 8"
                v-model="item[0].value"
                type="datetime"
                placeholder="选择日期时间">
              </el-date-picker>
              <editor  v-if="item[0].type === 5" :ref="'ueditor' + index" class="ueditor" :editread="editread" :content="content" @contentChange="handleContentChange(index, item[0].order)"></editor>
              <upload-file
                v-if="item[0].type === 6"
                @uploadSuccess="uploadOtherSuccess"
                @deleteSuccess="deleteSuccess"
                :fileArrays="item[0].fileInformations"
                :fileType="item[0].businessType"
                isImage="0">
              </upload-file>
            </el-form-item>
          </el-col>
          <el-col :span="item[1].type === 5 || item[1].type === 6 ? 24 : 12" v-if="item.length === 2">
            <el-form-item :label="item[1].name + '：'" :class="item[1].isFillIn === '1' ? 'bitianicon' : ''"
                          :prop="'item.' + index + '[1].value'"
                          :rules="handleRules(item[1].isFillIn === '1',item[1].type, item[1].name)"
            >
              <el-input autocomplete="off"
                        v-if="item[1].type === 1"
                        v-model.number="item[1].value">
              </el-input>
              <el-select v-model="item[1].value" placeholder="请选择" v-if="item[1].type === 2">
                <el-option
                  v-for="opItem in item[1].optional"
                  :key="opItem.value"
                  :label="opItem.label"
                  :value="opItem.value">
                </el-option>
              </el-select>
              <el-radio-group v-model="item[1].value" v-if="item[1].type === 3">
                <el-radio v-for="raItem in item[1].optional" :label="raItem.value" :key="raItem.value" >{{raItem.label}}</el-radio>
              </el-radio-group>
              <el-checkbox-group v-model="item[1].value" v-if="item[1].type === 4">
                <el-checkbox v-for="raItem in item[1].optional" :label="raItem.value" :key="raItem.value" >{{raItem.label}}</el-checkbox>
              </el-checkbox-group>
              <el-date-picker
                v-if="item[1].type === 7"
                v-model="item[1].value"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
              <el-date-picker
                v-if="item[1].type === 8"
                v-model="item[1].value"
                type="datetime"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item class="submit-radio">
            <el-button type="primary" @click="handleClickEvent('submitForm','save')" :loading="loading">保存</el-button>
            <el-button type="primary" class="submitBtn" @click="handleClickEvent('submitForm', 'submit')" :loading="loading">提交</el-button>
            <el-button class="cancal" @click="handleClickEvent('submitForm','cancel')">取消</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </div>
    <submit-approve-dialog :approveMethod="approveMethod" :flowStatus="99" :showVisible="approveDialogVisable" @confirmSubmit="confirmSubmit" @handleCancel="cancelApprove"></submit-approve-dialog>
    <el-dialog title="选择项目" width="600px" :visible.sync="showVisible">
      <div class="selectbox">
        <el-form :model="queryModel"  label-width="100px" :validate-on-rule-change="true">
          <el-row :gutter="20">
            <el-col :span="16">
              <el-form-item label="项目名称：">
                <el-input class="search" v-model="queryModel.messageLike" placeholder="请输入项目名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-button type="primary" @click="search">查询</el-button>
              <el-button  @click="reset">重置</el-button>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <el-table
        :data="proTableData"
        border
        style="width: 100%" header-cell-class-name="tableheader">
        <el-table-column
          type="index"
          label="序号"
          width="60"
          :index="computedIndex"
          align="center">
        </el-table-column>
        <el-table-column
          prop="tenderProjectName"
          label="项目名称"
          :formatter="simpleFormatData"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="radio"
          label="操作"
          align="center"
          width="90">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="selected(scope.row)">选择</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分页-->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="page.total"
        :page-size='page.pageSize'
        :current-page.sync="page.currentPage"
        @current-change="handlePage"
        @next-click="handlePage">
      </el-pagination>
      <!--分页-->
    </el-dialog>
  </div>
</template>
<script>
import {templateInfo} from '@/api/system'
import {tenderProject} from '@/api/project'
import {journalSummary} from '@/api/office'
import editor from '@/components/ueditor/ueditor.vue'
import uploadFile from '@/components/upload/publicFileUpload'
import submitApproveDialog from '@/pages/project/commonCompents/submitApproveDialog'
export default {
  components: {
    editor,
    uploadFile,
    submitApproveDialog
  },
  data () {
    return {
      templateName: '',
      // 富文本
      editread: false,
      content: '',
      submitForm: {
        tenderProjectName: '',
        item: [],
        approvalFlowExecutorList: [],
        noticeList: []
      },
      loading: false,
      approveMethod: 2,
      approveDialogVisable: false,
      templateSystemCode: this.$route.query.templateSystemCode,
      formLoading: false,
      // 选择项目
      showVisible: false,
      queryModel: {
        messageLike: ''
      },
      proTableData: [],
      page: {
        pageSize: 5,
        pageNo: 0,
        total: 0, // 总条数
        currentPage: 1
      },
      flowType: '123' // 审批类型
    }
  },
  created () {
    if (this.$route.query.objectId) {
      this.getJournalSummary()
    } else {
      this.showEdit()
    }
  },
  methods: {
    // rules
    handleRules (isFill, type, value) {
      if (!isFill) {
        return null
      }
      let tempRule
      switch (type) {
        case 1:
          tempRule = {
            required: true, message: `${value}不能为空`, trigger: ['blur', 'change']
          }
          break
        case 2:
          tempRule = {
            required: true, message: `${value}不能为空`, trigger: ['blur', 'change']
          }
          break
        case 3:
          tempRule = {
            required: true, message: `${value}不能为空`, trigger: ['blur', 'change']
          }
          break
        case 4:
          tempRule = {
            required: true, message: `${value}不能为空`, trigger: ['blur', 'change']
          }
          break
        case 7:
          tempRule = {
            required: true, message: `${value}不能为空`, trigger: ['blur', 'change']
          }
          break
        case 8:
          tempRule = {
            required: true, message: `${value}不能为空`, trigger: ['blur', 'change']
          }
          break
      }
      return tempRule
    },
    /** 选择项目 */
    selected (row) {
      this.submitForm.tenderProjectName = row.tenderProjectName
      this.submitForm.tenderSystemCode = row.code
      this.showVisible = false
    },
    /** 项目信息 */
    chooseTenderProject () {
      this.getTenderProjectList()
    },
    search () {
      this.page.pageNo = 0
      this.page.pageSize = 5
      this.page.currentPage = 1
      this.page.total = 0
      this.getTenderProjectList()
    },
    reset () {
      this.queryModel.messageLike = ''
      this.getTenderProjectList()
    },
    /** 获取项目信息 */
    getTenderProjectList () {
      let query = {
        pageNo: this.page.pageNo,
        pageSize: this.page.pageSize,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        statusList: '3,4,5,6,7,8,9,10,11,12,14'
      }
      if (this.queryModel.messageLike) {
        query.messageLike = this.queryModel.messageLike
      }
      tenderProject.getList(query).then((res) => {
        if (res.data.tenderProjectList && res.data.tenderProjectList.list) {
          this.proTableData = res.data.tenderProjectList.list
          this.page.total = res.data.tenderProjectList.total
        }
        this.showVisible = true
      })
    },
    showEdit () {
      this.formLoading = true
      templateInfo.getOne(this.templateSystemCode).then((res) => {
        if (res.data.templateInfo) {
          this.templateName = res.data.templateInfo.name
          this.submitForm.relatedTemplateCode = res.data.templateInfo.code
          this.submitForm.templateName = res.data.templateInfo.name
          this.submitForm.templateItems = res.data.templateInfo.items
          this.submitForm.item = JSON.parse(res.data.templateInfo.items)
        }
        this.formLoading = false
      })
    },
    // 查询工作日志/计划总结单条数据
    getJournalSummary () {
      this.formLoading = true
      journalSummary.getById(this.$route.query.objectId).then(res => {
        let journalSummary = res.data.journalSummary
        if (journalSummary) {
          this.submitForm = journalSummary
          this.$set(this.submitForm, 'item', JSON.parse(journalSummary.content))
          this.$nextTick(function () {
            this.submitForm.item.map((item, index) => {
              if (item[0].type === 5) {
                this.$refs['ueditor' + index][0].setContent(item[0].value)
              }
            })
          })
          this.formLoading = false
        }
      })
    },
    // 上传附件
    uploadOtherSuccess (file) {
      this.submitForm.item.map(item => {
        if (item[0].type === 6 && item[0].businessType === file.businessType) {
          item[0].fileInformations.push(file)
        }
      })
    },
    deleteSuccess (fileId) {
      this.submitForm.item.map(item => {
        if (item[0].type === 6) {
          item[0].fileInformations = item[0].fileInformations.filter(item => item.relativePath !== fileId)
        }
      })
    },
    // 图文
    handleContentChange (index, order) {
      if (this.$refs['ueditor' + index]) {
        this.$nextTick(function () {
          this.submitForm.item.map(item => {
            if (item[0].type === 5 && item[0].order === order) {
              item[0].value = this.$refs['ueditor' + index][0].getContent()
            }
          })
        })
      }
    },
    // 返回
    cancel () {
      this.$store.commit('delete_tabs', this.$route.fullPath)
      this.$router.go(-1)
    },
    handleDel (type, index) {
      switch (type) {
        case 'receive':
          this.receiveList.splice(index, 1)
          break
      }
    },
    // 确认提交
    confirmSubmit (record) {
      this.submitForm.approvalFlowExecutorList = []
      this.submitForm.noticeList = []
      for (let i = 0; i < record.approveList.length; i++) {
        let obj = {
          usersId: record.approveList[i].objectId,
          flowLevel: i + 1
        }
        this.submitForm.approvalFlowExecutorList.push(obj)
      }
      if (record.noticeList && record.noticeList.length > 0) {
        this.submitForm.noticeList = record.noticeList
      }
      this.submitData('submit')
    },
    cancelApprove () {
      this.approveDialogVisable = false
    },
    handleClickEvent (formName, type) {
      if (Object.is(type, 'submit') || Object.is(type, 'save')) {
        this.$nextTick(() => {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              /* if (!this.submitForm.tenderProjectName) {
                this.$message({
                  message: '请选择所属项目！',
                  type: 'warning'
                })
                return false
              } */
              for (let i = 0; i < this.submitForm.item.length; i++) {
                let item = this.submitForm.item[i]
                // 富文本校验
                if (item[0].type === 5 && item[0].isFillIn === '1') {
                  if (!item[0].value) {
                    this.$message({
                      message: item[0].name + '不能为空！',
                      type: 'warning'
                    })
                    return false
                  }
                }
                // 附件校验
                if (item[0].type === 6 && item[0].isFillIn === '1') {
                  if (!item[0].fileInformations || item[0].fileInformations.length < 1) {
                    this.$message({
                      message: item[0].name + '不能为空！',
                      type: 'warning'
                    })
                    return false
                  }
                }
              }
              if (Object.is(type, 'save')) {
                this.approveDialogVisable = false
                this.submitData(type)
              } else {
                this.approveDialogVisable = true
              }
            } else {
              return false
            }
          })
        })
      } else if (Object.is(type, 'cancel')) {
        this.$store.commit('delete_tabs', this.$route.fullPath)
        this.$router.go(-1)
      }
    },
    submitData (type) {
      let msg = Object.is(type, 'submit') ? '提交后数据将锁定，不允许修改，确认要提交吗?' : '确认要保存吗?'
      this.$confirm(msg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        beforeClose: (action, instance, done) => {
          done()
        }
      }).then(() => {
        this.loading = true
        this.submitForm.title = this.$store.getters.authUser.departmentName + this.$store.getters.authUser.userName + '提交的' + this.submitForm.templateName
        this.submitForm.type = this.$route.query.type
        this.submitForm.content = JSON.stringify(this.submitForm.item)
        if (Object.is(type, 'save')) {
          this.submitForm.status = 0 // 已保存
        } else if (Object.is(type, 'submit')) {
          this.submitForm.sendDate = new Date().getTime()
          this.submitForm.status = 2 // 待审批
        }
        journalSummary.update(this.submitForm).then(res => {
          if (res.data.resCode === '0000') {
            this.loading = false
            this.$store.commit('delete_tabs', this.$route.fullPath)
            this.$router.go(-1)
          }
        })
      }).catch(() => {
        this.loading = false
        return false
      })
    },
    // 选择项目弹框中 分页
    handlePage (nowNum) {
      this.page.currentPage = nowNum
      this.page.pageNo = (nowNum - 1) * this.page.pageSize
      this.getTenderProjectList()
    },
    // 序号计算
    computedIndex (index) {
      return index + (this.page.currentPage - 1) * this.page.pageSize + 1
    },
    // 普通格式化数据，空的时候展示"---"
    simpleFormatData (row, col, cellValue) {
      return cellValue || '---'
    }
  },
  mounted () {
    // this.showEdit()
  }
}
</script>
<style lang="less">
  #cloud_cloud_workApprovalAdd{
    .showImg{
      width:100px;
      overflow: hidden;
    }
    .showImg .el-button{
      padding: 8px 11px;
    }
    .showImg img{
      margin-top: 13px;
    }
    .imgDialog .el-dialog__header{
      border-bottom: 1px solid #eeeeee;
    }
    .imgDialog .el-dialog__body{
      padding: 0;
    }
    ul.imgList{
      width: 100%;
      overflow: hidden;
      margin: 20px 0 30px 0;
    }
    ul.imgList li{
      float: left;
      width: 80px;
      height: 80px;
      margin: 16px 30px;
      position: relative;
    }
    ul.imgList li .hover{
      position: absolute;
      left: 0;
      top: 0;
      display: none;
      cursor: pointer;
    }
    ul.imgList li:hover .hover{
      display: block;
    }
    .temTableData {
      margin-top: 20px;
      border: 0!important;
    }
    .optional span:hover{
      cursor: pointer;
      color: #409EFF;
    }
    .optionalDialog .el-dialog__header{
      border-bottom: 1px solid #eeeeee;
    }
    .optionalDialog p{
      color: #999999;
      line-height: 24px;
    }
    .optionalDialog .submit-radio{
      text-align: center;
    }
  }
</style>
