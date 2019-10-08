<template>
  <div class="cloudcontent" id="cloud_projectProcess">
    <div class="main">
      <el-form :model="updateForm" :rules="rulesList" ref="updateForm" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目名称："  prop="tenderProjectName">
              <el-input v-model="updateForm.tenderProjectName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目编号："  prop="projectCode">
              <span>{{updateForm.projectCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="招标项目编号："  prop="tenderProjectCode">
              <el-input v-model="updateForm.tenderProjectCode"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目预计收入(元)："  prop="projectIncome">
              <el-input v-model="updateForm.projectIncome"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目类型："  prop="tenderProjectType">
              <el-select v-model="updateForm.tenderProjectType" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目预算（元）：" prop="tenderProjectBudget">
              <el-input v-model="updateForm.tenderProjectBudget"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="index === 0 ? '资金来源：': ''"
                          v-for="(item, index) in updateForm.fundSourceList"
                          :key="item.key"
                          :prop="'fundSourceList.' + index + '.value'"
                          :rules="rules.value">
                <el-input placeholder="请输入内容" v-model="item.value" class="input-with-select" >
                  <el-select v-model="item.sourceOfFunds" slot="prepend" placeholder="请选择" @change="checkSourceOfFunds(index)">
                    <el-option
                      v-for="item in fundSourceOptions"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                      :disabled="item.disabled"></el-option>
                  </el-select>
                  <el-button slot="append">%</el-button>
                </el-input>
                <el-button class="addbutton" v-if="index === 0" @click="addItem">添加</el-button>
                <el-button class="addbutton" v-if="index !== 0" @click="delItem(index)">删除</el-button>
                <div class="radiobox" v-if="Number(item.sourceOfFunds) === 1">
                  <div class="bitian">
                    国有资金控股：
                    <el-radio-group v-model="stateOwnedCapitalHolding" @change="checkHolding(index)">
                      <el-radio :label="1" value="1">是</el-radio>
                      <el-radio :label="0" value="0">否</el-radio>
                    </el-radio-group>
                  </div>
                  <div class="bitian">
                    国有资金占主导地位：
                    <el-radio-group v-model="stateOwnedFundsPredominate" @change="checkStateOwnedFunds(index)">
                      <el-radio :label="1" value="1">是</el-radio>
                      <el-radio :label="0" value="0">否</el-radio>
                    </el-radio-group>
                  </div>
                </div>
              <!--</div>-->
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="批准文号：">
              <el-input v-model="updateForm.approvalNumber"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="招标人："  prop="tendererName">
              <el-input v-model="updateForm.tendererName" readonly placeholder="请选择">
                <el-button slot="append" icon="el-icon-search" @click="showCustomerDialog"></el-button>
              </el-input>
            </el-form-item>
            <customer-radio-dialog :showVisible="showCustomerVisible" @selected="selectedCustomer" @showDialog="showCustomerDialog"></customer-radio-dialog>
          </el-col>
          <el-col :span="12">
            <el-form-item label="招标人单位地址："  prop="tendererUnitAddress">
              <el-input v-model="updateForm.tendererUnitAddress" readonly></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="招标方式："  prop="tenderMode">
              <el-select v-model="updateForm.tenderMode" placeholder="请选择">
                <el-option
                  v-for="item in typeOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="监督部门名称：">
              <el-input v-model="updateForm.supervisionDepName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否组织踏勘：" prop="isToExplore">
              <el-select v-model="updateForm.isToExplore" placeholder="请选择">
                <el-option
                  v-for="item in isAllowOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否资格预审："  prop="isPreBid" v-if="updateForm.tenderMode === 1">
              <el-select v-model="updateForm.isPreBid" placeholder="请选择">
                <el-option
                  v-for="item in isCheckOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24" class="ueditor_box">
            <el-form-item label="招标内容与范围及招标方案说明：">
              <editor ref="ueditor" class="ueditor" :editread="editread" @contentChange="handleContentChange" :content="content"></editor>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="上传批复文件：">
              <upload-file @uploadSuccess="uploadOtherSuccess" @deleteSuccess="deleteSuccess"
                           :fileArrays="updateForm.fileInformationList"
                           fileType="8"
                           isImage="0">
              </upload-file>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="附件：">
              <upload-file @uploadSuccess="uploadOtherSuccess" @deleteSuccess="deleteSuccess"
                           :fileArrays="updateForm.fileInformationList"
                           fileType="9"
                           isImage="0">
              </upload-file>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="handleClickEvent('updateForm','save')" :loading="loading">保存</el-button>
          <el-button type="primary" class="submitBtn" @click="handleClickEvent('updateForm','submit')" :loading="loading">提交</el-button>
          <el-button class="cancal" @click="handleClickEvent('updateForm','cancel')">取消</el-button>
        </el-form-item>
      </el-form>
      <submitApproveDialog :flowStatus="flowStatus" :tenderSystemCode="$route.query.code" :showVisible="approveDialogVisable" @confirmSubmit="confirmSubmit" @handleCancel="cancelApprove"></submitApproveDialog>
    </div>
  </div>
</template>
<script>
import editor from '@/components/ueditor/ueditor.vue'
import {tenderProject} from '@/api/project'
import uploadFile from '@/components/upload/publicFileUpload'
import {validateWhoInteger, validatePercentage} from '@/assets/js/validate'
import CustomerRadioDialog from '@/pages/customer/customer/dialog/radio_dialog.vue'
import * as region from '@/assets/js/region'
import submitApproveDialog from '@/pages/project/commonCompents/submitApproveDialog'
export default {
  components: {
    editor,
    uploadFile,
    CustomerRadioDialog,
    submitApproveDialog
  },
  data () {
    // 百分比
    let ValidPercentage = (rule, value, callback) => {
      if (!value) {
        callback()
      } else if (!validatePercentage(value)) {
        callback(new Error('请输入1-100的整数！'))
      } else {
        callback()
      }
    }
    let validWhoInteger = (rule, value, callback) => {
      if (value === '0' || !value) {
        callback()
      } else if (!validateWhoInteger(value)) {
        callback(new Error('输入有误，请重新录入！'))
      } else {
        callback()
      }
    }
    return {
      flowStatus: 1,
      loading: false,
      isSave: false,
      updateForm: {
        tenderProjectName: '',
        tendererName: '',
        tenderProjectCode: '',
        tenderProjectType: null,
        tenderMode: null,
        isToExplore: null,
        fundSourceList: [
          {key: 0, sourceOfFunds: '', value: ''}
        ],
        fileInformationList: [],
        approvalFlowExecutorList: [],
        noticeList: []
      },
      rules: {
        tenderProjectName: [
          { required: true, message: '项目名称不能为空', trigger: ['blur', 'change'] }
        ],
        tenderProjectCode: [
          { required: true, message: '项目编号不能为空', trigger: ['blur', 'change'] }
        ],
        projectIncome: [
          { required: true, message: '项目预计收入不能为空', trigger: ['blur', 'change'] },
          {validator: validWhoInteger, trigger: ['blur', 'change']}
        ],
        tenderProjectType: [
          { required: true, message: '请选择项目类型', trigger: ['blur', 'change'] }
        ],
        tenderProjectBudget: [
          {validator: validWhoInteger, trigger: ['blur', 'change']}
        ],
        approvalNumber: [
          { required: true, message: '批准文号不能为空', trigger: ['blur', 'change'] }
        ],
        tenderMode: [
          { required: true, message: '请选择招标方式', trigger: ['blur', 'change'] }
        ],
        isPreBid: [
          { required: true, message: '请选择是否资格预审', trigger: ['blur', 'change'] }
        ],
        tendererName: [
          { required: true, message: '招标人名称不能为空', trigger: ['blur', 'change'] }
        ],
        value: [
          { validator: ValidPercentage, trigger: ['blur', 'change'] }
        ],
        isToExplore: [
          { required: true, message: '请选择是否组织踏勘', trigger: ['blur', 'change'] }
        ]
      },
      saveRules: {
        tenderProjectName: [
          { required: false, message: '项目名称不能为空', trigger: ['blur', 'change'] }
        ],
        tenderProjectCode: [
          { required: false, message: '项目编号不能为空', trigger: ['blur', 'change'] }
        ],
        projectIncome: [
          { required: false, message: '项目预计收入不能为空', trigger: ['blur', 'change'] },
          {validator: validWhoInteger, trigger: ['blur', 'change']}
        ],
        tenderProjectType: [
          { required: false, message: '请选择项目类型', trigger: ['blur', 'change'] }
        ],
        tenderProjectBudget: [
          {validator: validWhoInteger, trigger: ['blur', 'change']}
        ],
        approvalNumber: [
          { required: false, message: '批准文号不能为空', trigger: ['blur', 'change'] }
        ],
        tenderMode: [
          { required: false, message: '请选择招标方式', trigger: ['blur', 'change'] }
        ],
        isPreBid: [
          { required: false, message: '请选择是否资格预审', trigger: ['blur', 'change'] }
        ],
        tendererName: [
          { required: false, message: '招标人名称不能为空', trigger: ['blur', 'change'] }
        ],
        value: [
          { validator: ValidPercentage, trigger: ['blur', 'change'] }
        ],
        isToExplore: [
          { required: false, message: '请选择是否组织踏勘', trigger: ['blur', 'change'] }
        ]
      },
      // 项目类型
      options: [{
        value: '1',
        label: '工程'
      }, {
        value: '2',
        label: '货物'
      }, {
        value: '3',
        label: '服务'
      }],
      value: '',
      // 招标方式
      typeOptions: [{
        value: 1,
        label: '公开招标'
      }, {
        value: 2,
        label: '邀请招标'
      }, {
        value: 3,
        label: '竞争性谈判'
      }, {
        value: 4,
        label: '单一来源采购'
      }, {
        value: 5,
        label: '询价采购'
      }, {
        value: 6,
        label: '竞争性磋商'
      }],
      typeValue: '',
      // 是否资格预审
      isCheckOptions: [{
        value: 1,
        label: '是'
      }, {
        value: 0,
        label: '否'
      }],
      isCheckValue: '',
      // 是否允许联合体投标
      isAllowOptions: [{
        value: 1,
        label: '是'
      }, {
        value: 0,
        label: '否'
      }],
      fundSourceOptions: [{
        value: 1,
        label: '自有资金'
      }, {
        value: 2,
        label: '吸收资金'
      }, {
        value: 3,
        label: '专项资金'
      }, {
        value: 4,
        label: '其他资金'
      }],
      isAllowValue: '',
      // 富文本
      editread: false,
      content: '',
      stateOwnedCapitalHolding: 0,
      stateOwnedFundsPredominate: 0,
      // 是否展示客户
      showCustomerVisible: false,
      // 地址三级联动
      addressOptions: region.CityInfo,
      approveDialogVisable: false
    }
  },
  watch: {
    '$route': 'getChooseTenderProjectInfo',
    'updateForm': {
      handler (val) {
        localStorage.setItem(this.$route.fullPath, JSON.stringify(val))
      },
      deep: true
    }
  },
  computed: {
    rulesList: function () {
      if (this.isSave) {
        return this.saveRules
      } else {
        return this.rules
      }
    }
  },
  created () {
    this.getChooseTenderProjectInfo()
  },
  methods: {
    /** 获取当前招标项目信息 */
    getChooseTenderProjectInfo () {
      tenderProject.getBaseByCode(this.$route.query.code).then((res) => {
        if (res.data.tenderProject) {
          this.updateForm = res.data.tenderProject
          if (res.data.fundSourceList.length > 0) {
            this.updateForm.fundSourceList = res.data.fundSourceList
            this.updateForm.fundSourceList.forEach(item => {
              if (item.sourceOfFunds === 1) {
                this.stateOwnedCapitalHolding = item.stateOwnedCapitalHolding
                this.stateOwnedFundsPredominate = item.stateOwnedFundsPredominate
              }
              this.fundSourceOptions[Number(item.sourceOfFunds) - 1].disabled = true
            })
          } else {
            this.updateForm.fundSourceList = [{key: 0, sourceOfFunds: '', value: ''}]
          }
          // 获取文本编辑器的内容
          if (this.updateForm.tenderContent) {
            this.$refs.ueditor.setContent(this.content = this.updateForm.tenderContent)
          }
          // 设置附件信息
          if (this.updateForm.fileInformationList.length > 0) {
            this.updateForm.fileInformationList.forEach(item => {
              item.objectId = null
            })
          }
          if (localStorage.getItem(this.$route.fullPath)) {
            this.updateForm = JSON.parse(localStorage.getItem(this.$route.fullPath))
            this.$refs.ueditor.setContent(this.content = this.updateForm.tenderContent)
          }
        }
      })
    },
    /** 判断资金来源是否可选 */
    checkChooseStatus () {
      this.fundSourceOptions.forEach(item => {
        item.disabled = false
      })
      if (this.updateForm.fundSourceList.length > 0) {
        for (let i = 0; i < this.updateForm.fundSourceList.length; i++) {
          for (let j = 0; j < this.fundSourceOptions.length; j++) {
            if (Number(this.updateForm.fundSourceList[i].sourceOfFunds) === this.fundSourceOptions[j].value) {
              this.fundSourceOptions[j].disabled = true
              break
            }
          }
        }
      }
    },
    /** 判断选择的资金来源类型是否为自有资金时为其设置默认值 */
    checkSourceOfFunds (index) {
      if (Number(this.updateForm.fundSourceList[index].sourceOfFunds) === 1) {
        this.updateForm.fundSourceList[index].stateOwnedCapitalHolding = this.stateOwnedCapitalHolding
        this.updateForm.fundSourceList[index].stateOwnedFundsPredominate = this.stateOwnedFundsPredominate
      }
      this.checkChooseStatus()
    },
    /** 判断资金来源的数据是否正确 */
    checkSourceOfFundsValue () {
      if (this.updateForm.fundSourceList.length === 1 && this.updateForm.fundSourceList[0].sourceOfFunds === '' && this.updateForm.fundSourceList[0].value === '') { // 默认数据
        this.updateForm.fundSourceList = []
        return true
      } else {
        for (let i = 0; i < this.updateForm.fundSourceList.length; i++) {
          if (this.updateForm.fundSourceList[i].sourceOfFunds === '' || this.updateForm.fundSourceList[i].value === '') {
            return false
          }
        }
      }
      return true
    },
    /* 按钮点击事件：提交、保存、取消 */
    handleClickEvent (formName, type) {
      if (Object.is(type, 'submit') || Object.is(type, 'save')) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (Object.is(type, 'save')) {
              this.isSave = true
              this.approveDialogVisable = false
              this.submitData(type)
            } else {
              this.isSave = false
              this.approveDialogVisable = true
            }
          } else {
            return false
          }
        })
      } else if (Object.is(type, 'cancel')) {
        this.$store.commit('delete_tabs', this.$route.fullPath)
        this.$router.go(-1)
        // this.$router.push({path: '/project/projectProcess'})
      }
    },

    submitData (type) {
      this.$nextTick(() => {
        // 设置资金来源信息
        if (Object.is(type, 'submit')) {
          if (!this.checkSourceOfFundsValue()) {
            this.$message.warning('资金来源信息不完整！')
            return false
          }
        }
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
          // 获取文本编辑器中的内容
          if (this.$refs.ueditor) {
            this.updateForm.tenderContent = this.$refs.ueditor.getContent()
          }
          // 根据type设置当前招标项目的状态
          if (Object.is(type, 'submit')) {
            this.updateForm.status = 10
          } else {
            this.updateForm.status = 6
            if (this.updateForm.fundSourceList.length === 1 && this.updateForm.fundSourceList[0].sourceOfFunds === '' && this.updateForm.fundSourceList[0].value === '') { // 默认数据
              this.updateForm.fundSourceList = []
            }
          }
          // 路由地址的设定
          this.updateForm.routingPath = '/project/projectProcess/detail/'
          tenderProject.save(this.updateForm).then((res) => {
            this.loading = false
            if (res.data.resCode === '0000') {
              this.$store.commit('delete_tabs', this.$route.fullPath)
              // this.$router.push({path: '/project/projectProcess'})
              this.$router.go(-1)
            }
          })
        }).catch(() => {
          this.loading = false
          return false
        })
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
      this.submitData('submit')
    },
    cancelApprove () {
      this.approveDialogVisable = false
    },
    addItem () {
      this.updateForm.fundSourceList.push(
        {key: this.updateForm.fundSourceList.length, sourceOfFunds: '', value: ''})
    },
    // 上传附件
    uploadOtherSuccess (file) {
      this.updateForm.fileInformationList.push(file)
    },
    deleteSuccess (fileId) {
      this.updateForm.fileInformationList = this.updateForm.fileInformationList.filter(item => item.relativePath !== fileId)
    },
    delItem (index) {
      if (this.updateForm.fundSourceList[index].sourceOfFunds !== '') {
        this.fundSourceOptions[Number(this.updateForm.fundSourceList[index].sourceOfFunds) - 1].disabled = false
      }
      this.updateForm.fundSourceList.splice(index, 1)
    },
    /** 处理资金来源中的信息 */
    checkHolding (index) {
      this.updateForm.fundSourceList[index].stateOwnedCapitalHolding = this.stateOwnedCapitalHolding
    },
    checkStateOwnedFunds (index) {
      this.updateForm.fundSourceList[index].stateOwnedFundsPredominate = this.stateOwnedFundsPredominate
    },
    // 客户弹框打开
    showCustomerDialog () {
      this.showCustomerVisible = !this.showCustomerVisible
    },
    // 选择客户
    selectedCustomer (obj) {
      this.updateForm.tendererCode = obj.code
      this.updateForm.tendererName = obj.name
      this.updateForm.customerManagementCode = obj.code
      this.updateForm.tendererUnitAddress = this.wrapRegionText(obj)
    },
    // 包装三级联动地址信息
    wrapRegionText (obj) {
      let str = ''
      // 初始化省市县数据
      this.addressOptions.map((item) => {
        if (item.value === obj.provinceId) {
          str += item.label + '/'
          item.children.map((ite) => {
            if (ite.value === obj.cityId) {
              str += ite.label + '/'
              ite.children.map((countyItem) => {
                if (countyItem.value === obj.countyId) {
                  str += countyItem.label
                }
              })
            }
          })
        }
      })
      // 加入详细地址
      if (obj.address) {
        if (str !== '') {
          str += '/'
        }
        str += obj.address
      }
      return str
    },
    // 监控编辑器内容
    handleContentChange () {
      if (this.$refs.ueditor) {
        this.updateForm.tenderContent = this.$refs.ueditor.getContent()
        localStorage.setItem(this.$route.fullPath, JSON.stringify(this.updateForm))
      }
    }
  }
}
</script>
<style lang="less">
  #cloud_projectProcess {
    .el-select{
      width: 100%;
    }
    .zjbox{
      margin-bottom: 20px;
    }
    .input-with-select{
      width: 320px;
    }
    .input-with-select .el-input-group__prepend,.el-input-group__append{
      background-color: #fff;
    }
    .input-with-select .el-input-group__prepend .el-select{
      width: 130px;
    }
    .addbutton{
      background: #6582f8;
      border-color: #6582f8;
      color: #ffffff;
      padding: 8px 20px;
      margin: 5px 0 0 0px;
    }
    .addbutton span{
      color: #ffffff;
    }
  }
</style>
