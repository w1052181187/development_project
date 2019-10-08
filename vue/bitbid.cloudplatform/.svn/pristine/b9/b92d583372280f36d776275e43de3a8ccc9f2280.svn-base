<template>
  <div class="fillType">
    <!--资格预审文件-->
    <div class="zztbdzs_sonbigbox" style="height:130px">
      <div class="zhankai_con_sontitlebox">
        <span>开标信息</span>
      </div>
      <el-form :disabled='!btn' :model="form" ref="form">
        <el-row>
          <el-button v-if='btn' type="text" @click="submitbtnFun('form')">
            <div class="tianjia_btn">
              <span>保存信息</span>
            </div>
          </el-button>
        </el-row>
        <el-col :span="12">
          <div class="grid-content bg-purple">
            <el-form-item label="开标时间："  prop="bidOpeningTime"  :label-width="formLabelWidth">
              <el-date-picker
                :picker-options="pickerOptions"
                v-model="form.bidOpeningTime"
                value-format="yyyy-MM-dd HH:mm:ss"
                type="datetime"
                start-placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item label="开标地点：" prop="placeOpeningBid" :label-width="formLabelWidth" :rules="[
            { required: false, message: '', trigger: 'blur' },
            {min: 1, max: 100, message: '请输入1~100个字符',trigger: ['blur', 'change'] }
          ]">
            <el-input v-model="form.placeOpeningBid"></el-input>
          </el-form-item>
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
export default {
  components: {
    fileUpload
  },
  props: ['projectInfo', 'businessType', 'btn', 'fileName'],
  data () {
    return {
      title: '开标',
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7
        }
      },
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
      loading: false,
      appendix: false,
      save: [],
      // 开标信息
      form: {
        bidOpeningTime: '',
        placeOpeningBid: '',
        sectionNumber: this.projectInfo.objectId
      },
      isSave: false,
      isSaveFlag: false,
      formLabelWidth: '135px',
      rules: {
        bidOpeningTime: [
          {required: true, message: '开标时间能为空', trigger: 'blur'}
        ],
        placeOpeningBid: [
          {required: true, message: '开标地点不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    // 查看
    Seeform (boolean) {
      this.axios.get(`bid-openings?sectionNumber=${this.projectInfo.objectId}&_t=${new Date().getTime()}`).then((res) => {
        if (res.data.bidOpening != null) {
          this.form = res.data.bidOpening
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
    // 提交資格资格预审文件數據
    submitbtnFun (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios.post('bid-openings', this.form).then((res) => {
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
    // *******************************************************点击附件***********************************************
  },
  mounted () {
    this.Seeform(true)
  }
}
</script>
