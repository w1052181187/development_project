<template>
  <div class="cloudcontent">
    <div class="main">
      <el-form :model="updateForm" :rules="rules" ref="updateForm" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目编号：">
              <el-input v-model="bidSection.tenderProjectCode" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目名称：">
              <el-input v-model="bidSection.tenderProjectName" disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="标段编号：">
              <el-input v-model="bidSection.bidSectionCode" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标段名称：">
              <el-input v-model="bidSection.bidSectionName" disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="投标人："  prop="bidderName">
              <el-input v-model="updateForm.bidderName" readonly>
                <el-button type="primary" slot="append" size="mini" @click="btnChoose">选择</el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="缴费金额(元)："  prop="amount">
              <el-input v-model="updateForm.amount"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="缴费方式："  prop="paymentMethod">
              <el-select v-model="updateForm.paymentMethod" placeholder="请选择">
                <el-option
                  v-for="item in costMethods"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="缴费时间："  prop="paymentTime">
              <el-date-picker
                v-model="updateForm.paymentTime"
                type="date"
                value-format="yyyy-MM-dd HH:mm:ss">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submit('updateForm', 0)" :loading="isSaving">保存</el-button>
          <el-button type="primary" class="submitBtn" @click="submit('updateForm', 1)" :loading="isSubmiting">提交</el-button>
          <el-button class="cancal" @click="close">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
    <bidder-dialog :tenderSystemCode="tenderSystemCode" :showVisible="showBidderVisible" @selected="selectedBidder" @showDialog="showBidderDialog"></bidder-dialog>
    <submitApproveDialog :tenderSystemCode="tenderSystemCode" :showVisible="approveDialogVisable" @confirmSubmit="confirmSubmit" @handleCancel="cancelApprove"></submitApproveDialog>
  </div>
</template>
<script>
import {costInfo, bidSection} from '@/api/project'
import {validMoney} from '@/assets/js/validate'
import bidderDialog from '@/pages/project/projectProcess/common/bidder_dialog.vue'
import submitApproveDialog from '@/pages/project/commonCompents/submitApproveDialog'
export default {
  components: {
    bidderDialog,
    submitApproveDialog
  },
  name: '',
  data () {
    return {
      tenderSystemCode: this.$route.query.tenderSystemCode,
      sectionSystemCode: this.$route.query.sectionSystemCode,
      isSaving: false,
      isSubmiting: false,
      updateForm: {
        bidderName: ''
      },
      bidSection: {},
      rules: {
        bidderName: {required: true, message: '请填写投标人名称', trigger: ['blur', 'change']},
        amount: [
          {required: true, message: '请填写缴费金额', trigger: ['blur', 'change']},
          {validator: validMoney, trigger: 'blur'}
        ],
        paymentMethod: {required: true, message: '请选择缴费方式', trigger: 'change'}
      },
      isPayOptions: [
        {
          value: 1,
          label: '是'
        },
        {
          value: 0,
          label: '否'
        }
      ],
      costMethods: [
        {
          value: 1,
          label: '支付宝'
        },
        {
          value: 2,
          label: '微信'
        },
        {
          value: 3,
          label: '现金'
        },
        {
          value: 4,
          label: '银联'
        },
        {
          value: 5,
          label: '支票'
        },
        {
          value: 6,
          label: '保函'
        },
        {
          value: 9,
          label: '其他'
        }
      ],
      showBidderVisible: false,
      approveDialogVisable: false
    }
  },
  methods: {
    // 提交/保存
    submit (form, status) {
      this.$refs[form].validate((vaild) => {
        if (vaild) {
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
      let msg = Object.is(status, 1) ? '提交后数据将锁定，不允许修改，确认要提交吗?' : '确定要保存吗?'
      this.$confirm(msg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        beforeClose: (action, instance, done) => {
          done()
        }
      }).then(() => {
        if (Object.is(status, 0)) {
          // 保存
          this.isSaving = true
          this.updateForm.auditStatus = 0
        } else if (Object.is(status, 1)) {
          // 提交 (判断是否需要审批：审批：1 不审批：4)
          this.updateForm.auditStatus = 4
          this.isSubmiting = true
        }
        this.updateForm.tenderSystemCode = this.tenderSystemCode
        this.updateForm.sectionSystemCode = this.sectionSystemCode
        this.updateForm.type = 3
        costInfo.update(this.updateForm).then((res) => {
          this.isSaving = false
          this.isSubmiting = false
          if (res.data.resCode === '0000') {
            this.close()
          }
        })
      }).catch(() => {
        this.isSaving = false
        this.isSubmiting = false
        return false
      })
    },
    // 确认提交
    confirmSubmit (record) {
      this.updateForm.approvalFlowExecutorList = []
      this.updateForm.manualAdditionList = []
      for (let i = 0; i < record.approveList.length; i++) {
        let obj = {
          usersId: record.approveList[i].objectId,
          flowLevel: i + 1
        }
        this.updateForm.approvalFlowExecutorList.push(obj)
      }
      if (record.noticeList && record.noticeList.length > 0) {
        this.updateForm.manualAdditionList = record.noticeList
      }
      this.submitData(1)
    },
    cancelApprove () {
      this.approveDialogVisable = false
    },
    // 取消
    close () {
      this.$store.commit('delete_tabs', this.$route.fullPath)
      this.$router.go(-1)
    },
    // 查询当前标段信息
    getBidSection () {
      bidSection.getOne(this.sectionSystemCode).then(res => {
        this.bidSection = res.data.bidSection
      })
    },
    init () {
      this.getBidSection()
    },
    // 选择投标人
    btnChoose () {
      this.showBidderVisible = true
    },
    // 选择投标人弹窗 打开/关闭
    showBidderDialog () {
      this.showBidderVisible = !this.showBidderVisible
    },
    // 已选择投标人信息
    selectedBidder (obj) {
      this.updateForm.bidderName = obj.name
    }
  },
  watch: {
    '$route': 'init'
  },
  mounted () {
    this.init()
  }
}
</script>

<style scoped>

</style>
