<template>
  <div id="bidinfo" class="bidinfo">
    <iframe name= "userInfo" id="userInfo" src="../../../../static/system-quantybill/getUserInfo.html" style="display: none;"></iframe>
    <h3>招标信息</h3>
    <el-form :model="ruleForm" :rules="rules" ref="aForm" :validate-on-rule-change="true" :error="errorMsg">
      <el-row>
        <el-col :span="12">
          <el-form-item label="工程编号："  prop="projectCode">
            <el-input v-model="ruleForm.projectCode"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="工程名称："  prop="name">
            <el-input v-model="ruleForm.name" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="招标人(建设单位)："  prop="tenderee">
            <el-input v-model="ruleForm.tenderee"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="招标单位法定代表人："  prop="tendereeLegalPerson">
            <el-input v-model="ruleForm.tendereeLegalPerson"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="造价咨询人：" prop="costConsultPerson">
            <el-input v-model="ruleForm.costConsultPerson"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="造价咨询法定代表人：" prop="projectCostLegalPerson">
            <el-input v-model="ruleForm.projectCostLegalPerson"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="编制人：" prop="compilePerson">
            <el-input v-model="ruleForm.compilePerson"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="编制时间：">
            <!--<el-input v-model="ruleForm.compileTime"></el-input>-->
            <el-date-picker
              v-model="ruleForm.compileTime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss">
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="复核人：" prop="reviewPerson">
            <el-input v-model="ruleForm.reviewPerson"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="复核时间：" >
            <!--<el-input v-model="ruleForm.reviewTime"></el-input>-->
            <el-date-picker
              v-model="ruleForm.reviewTime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss">
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col class="lastinfo">
          <el-form-item label="总说明："  prop="totalExplain">
            <el-input type="textarea" v-model="ruleForm.totalExplain"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="envTrue">
        <el-col :span="12">
          <el-form-item label="硬盘序列号：">
            <el-input v-model="ruleForm.hardDiskListNumber"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="网卡序列号：">
            <el-input v-model="ruleForm.networkCardListNumber"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="envTrue">
        <el-col :span="12">
          <el-form-item label="芯片序列号：">
            <el-input v-model="ruleForm.cpuListNumber"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="网络IP：">
            <el-input v-model="ruleForm.networkIp"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%">
      <p>为不影响本系统正常使用，请使用IE9及以上版本的浏览器。下载“IE设置方法”到本地电脑，根据提示设置完成后重启浏览器即可。</p>
      <el-button type="text" style="float: right;" @click="downImg">IE设置方法</el-button>
    </el-dialog>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible2"
      width="30%">
      <p>您还未运行成功。请使用IE9及以上版本的浏览器。下载“IE设置方法”到本地电脑，根据提示设置完成后重启浏览器即可。</p>
      <el-button type="text" style="float: right;" @click="downImg">IE设置方法</el-button>
    </el-dialog>
  </div>
</template>
<script>
import { billPushData } from '@/api'
import {loadUrl} from '@/assets/js/common'
export default {
  name: 'bid-info',
  props: ['projectId'],
  data () {
    return {
      ruleForm: {
        networkCardListNumber: '',
        cpuListNumber: '',
        hardDiskListNumber: '',
        networkIp: ''
      },
      compileTime: '',
      rules: {
        projectCode: [
          { required: true, message: '请输入工程编号', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1到 50 个字符', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入工程名称', trigger: 'blur' }
        ],
        tenderee: [
          { required: true, message: '请输入招标人', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在 1到 200 个字符', trigger: 'blur' }
        ],
        tendereeLegalPerson: [
          { required: true, message: '请输入招标单位法定代表人', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1到 20 个字符', trigger: 'blur' }
        ],
        totalExplain: [
          { required: true, message: '请填写总说明', trigger: 'blur' }
        ],
        costConsultPerson: [
          { min: 1, max: 20, message: '长度在 1到 20 个字符', trigger: 'blur' }
        ],
        projectCostLegalPerson: [
          { min: 1, max: 20, message: '长度在 1到 20 个字符', trigger: 'blur' }
        ],
        compilePerson: [
          { min: 1, max: 20, message: '长度在 1到 20 个字符', trigger: 'blur' }
        ],
        reviewPerson: [
          { min: 1, max: 20, message: '长度在 1到 20 个字符', trigger: 'blur' }
        ]
      },
      errorMsg: [],
      ruleFormDisabled: {},
      userInfo: null,
      envTrue: true,
      dialogVisible: false,
      dialogVisible2: false
    }
  },
  methods: {
    getUserInfo () {
      let oIframe = document.getElementById('userInfo')
      if (oIframe) {
        let jsonString = oIframe.contentWindow.getSystemInfo()
        let json = JSON.parse(jsonString)
        if (json) {
          this.ruleForm.networkCardListNumber = json.networkSerialNumber
          this.ruleForm.cpuListNumber = json.chipSerialNumber
          this.ruleForm.hardDiskListNumber = json.diskSerialNumber
          this.ruleForm.networkIp = json.ipAdress
        } else {
          this.dialogVisible = true
        }
      }
    },
    downImg () {
      let url
      url = loadUrl + 'comptend/project/download/localFile/operation'
      window.open(url)
    },
    validateForm () {
      this.errorMsg = ''
      this.$refs['aForm'].validate((valid) => {
        if (valid) {
          if (this.ruleForm.networkCardListNumber) {
            this.$emit('childSubValue', 'true')
            this.$emit('childFormData', this.ruleForm)
          } else {
            this.dialogVisible2 = true
          }
        } else {
          this.errorMsg = valid
          return false
        }
      })
    },
    _getFormData () {
      if (process.env.ENV_CONFIG === 'prod') {
        this.envTrue = false
      }
      billPushData.getItemInfor(this.projectId).then((res) => {
        this.ruleForm = res.data.itemInfor
        this.compileTime = res.data.itemInfor.compileTime
      }).then(() => {
        this.getUserInfo()
      })
    }
  },
  mounted () {
    this._getFormData()
  }
}
</script>
<style lang="less">
 #bidinfo{
   width: 100%;
   overflow: hidden;
   margin: 20px auto;
    h3{
     height: 50px;
     line-height: 50px;
     padding: 0 30px;
     border-bottom: 1px solid #dddddd;
     font-size: 16px;
     color: #111111;
     margin: 20px 0 30px 0;
   }
   .lastinfo .el-form-item__content{
     width: 80%;
   }
   .el-date-editor.el-input, .el-date-editor.el-input__inner{
     width: 100%;
   }
 }
</style>
