<template>
  <div class="fillType">
    <!--资格预审文件-->
    <div class="zztbdzs_sonbigbox" style="overflow: hidden">
      <div class="zhankai_con_sontitlebox">
        <span>定标</span>
      </div>
      <el-form :disabled='!btn' :model="form" ref="form" :rules="rules">
        <el-row>
          <el-button v-if='btn' type="text" @click="submitbtnFun('form')">
            <div class="tianjia_btn">
              <span>保存信息</span>
            </div>
          </el-button>
        </el-row>
        <el-col :span="12">
          <el-form-item label="中标人：" prop="winningBidder" :label-width="formLabelWidth" :rules="[
            { required: false, message: '', trigger: 'blur' },
            {min: 1, max: 200, message: '请输入1~200个字符',trigger: ['blur', 'change'] }
          ]">
            <el-input v-model="form.winningBidder"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <div class="grid-content bg-purple">
            <el-form-item label="中标金额：" prop="winningAmount" :label-width="formLabelWidth">
              <el-input v-model="form.winningAmount"></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item label="代理服务费：" prop="agencyServiceFee" :label-width="formLabelWidth">
            <el-input v-model="form.agencyServiceFee"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <div class="grid-content bg-purple">
            <el-form-item label="候选人公示地址：" prop="candidatePublicAddr"  :label-width="formLabelWidth" :rules="[
            { required: false, message: '', trigger: 'blur' },
            {min: 1, max: 200, message: '请输入1~200个字符',trigger: ['blur', 'change'] }
          ]">
              <el-input v-model="form.candidatePublicAddr"></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="grid-content bg-purple">
            <el-form-item label="中标公示地址：" prop="addrWinningBid" :label-width="formLabelWidth" :rules="[
            { required: false, message: '', trigger: 'blur' },
            {min: 1, max: 200, message: '请输入1~200个字符',trigger: ['blur', 'change'] }
          ]">
              <el-input v-model="form.addrWinningBid"></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="grid-content bg-purple">
            <el-form-item label="项目开支：" prop="projectIncome" :label-width="formLabelWidth">
              <el-input v-model="form.projectIncome"></el-input>
            </el-form-item>
          </div>
        </el-col>
      </el-form>
    </div>
    <!--资格预审文件-->
    <!--资格预审文件附件-->
    <fileUpload :businessType="businessType" :btn='btn' :project-info='projectInfo' :file-name='fileName'></fileUpload>
    <!--资格预审文件告附件-->
  </div>
</template>
<script>
import {checkResponse} from '../../../assets/js/common'
import fileUpload from './fileUpload'
import { Numbertype } from '../../../assets/js/validate'
export default {
  components: {
    fileUpload
  },
  data () {
    // 数字
    var Numbertypes = (rule, value, callback) => {
      if (value === '') {
        callback()
      } else if (!Numbertype(value)) {
        callback(new Error('请输入1~100个数字'))
      } else {
        callback()
      }
    }
    return {
      title: '定标',
      // 提交定標信息
      form: {
        winningBidder: '',
        winningAmount: '',
        agencyServiceFee: '',
        candidatePublicAddr: '',
        addrWinningBid: '',
        projectIncome: '',
        sectionNumber: this.projectInfo.objectId
      },
      isSave: false,
      isSaveFlag: false,
      // 附件查询列表：
      EnclosuretableData: [],
      Enclosure: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      // 提交时验证
      rules: {
        winningAmount: [
          { min: 1, max: 100, message: '请输入1~100个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        agencyServiceFee: [
          { min: 1, max: 100, message: '请输入1~100个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        projectIncome: [
          { min: 1, max: 100, message: '请输入1~100个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ]
      },
      loading: false,
      appendix: false,
      save: [],
      formLabelWidth: '140px'
    }
  },
  props: ['projectInfo', 'businessType', 'btn', 'fileName'],
  methods: {
    // ********************************************************定标信息**************************************************
    // 查看
    Seeform (boolean) {
      this.axios.get(`calibrations?sectionNumber=${this.projectInfo.objectId}&_t=${new Date().getTime()}`).then((res) => {
        if (res.data.calibration != null) {
          this.form = res.data.calibration
        }
        if (this.isSave) {
          this.$emit('getSaveInfo', this.isSave, this.title)
        }
        if (boolean) {
          this.$watch('form', () => {
            if (this.isSaveFlag) {
              this.isSave = true
            } else {
              this.isSave = false
            }
            this.isSaveFlag = false
            this.$emit('getSaveInfo', this.isSave, this.title)
          }, {deep: true})
        }
      })
    },
    // 提交定标信息
    submitbtnFun (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios.post('calibrations', this.form).then((res) => {
            checkResponse(this, res, () => {
              this.isSaveFlag = true
              this.Seeform(false)
            })
          })
        } else {
          return false
        }
      })
    }
    // ************************************************************************点击上传附件**************************************************************
  },
  mounted () {
    this.Seeform(true)
  }
}
</script>
