<template>
  <div class="fillType">
    <!--资格预审文件-->
    <div class="zztbdzs_sonbigbox" style="height:150px">
      <div class="zhankai_con_sontitlebox">
        <span>评价信息</span>
      </div>
      <el-form :disabled='!btn' :model="form" ref="form">
        <el-row>
          <el-button v-if='btn' type="text" @click="submitbtnFun('form')">
            <div class="tianjia_btn">
              <span>保存信息</span>
            </div>
          </el-button>
        </el-row>
        <el-col :span="24">
          <el-form-item label="评价信息：" :label-width="formLabelWidth">
            <el-input type="textarea" v-model="form.evaMessage"></el-input>
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
// 引入自己需要样式和js
import {checkResponse} from '../../../assets/js/common'
import fileUpload from './fileUpload'
export default {
  components: {
    fileUpload
  },
  data () {
    return {
      title: '代理工作评价',
      // 评价信息
      form: {
        evaMessage: '',
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
      loading: false,
      appendix: false,
      save: [],
      formLabelWidth: '135px'
    }
  },
  props: ['projectInfo', 'businessType', 'btn', 'fileName'],
  methods: {
    // 查看
    Seeform (boolean) {
      this.axios.get(`agencywork-evas?sectionNumber=${this.projectInfo.objectId}&_t=${new Date().getTime()}`).then((res) => {
        if (res.data.agencyWorkEva != null) {
          this.form = res.data.agencyWorkEva
          this.loading = false
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
          this.axios.post('agencywork-evas', this.form).then((res) => {
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
  // *******************************************************上传附件*****************************************************************
  },
  mounted () {
    this.Seeform(true)
  }
}
</script>
