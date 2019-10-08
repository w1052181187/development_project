<template>
  <div id="trendinfo" class="trendinfo">
    <iframe name= "userInfo" id="userInfo" src="../../../../static/system-quantybill/getUserInfo.html" style="display: none;"></iframe>
    <h3>招标信息</h3>
    <table class="bidtable">
      <tr>
        <td>工程编号</td>
        <td>{{projectCode}}</td>
      </tr>
      <tr>
        <td>工程名称</td>
        <td>{{name}}</td>
      </tr>
      <tr>
        <td>招标人</td>
        <td>{{tenderee}}</td>
      </tr>
      <tr>
        <td>法定代表人</td>
        <td>{{tendereeLegalPerson}}</td>
      </tr>
      <tr>
        <td>编制人</td>
        <td>{{compilePerson}}</td>
      </tr>
    </table>
    <h3>投标信息</h3>
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" :validate-on-rule-change="true">
      <el-row>
        <el-col :span="12">
          <el-form-item label="投标人："  prop="bidder">
            <el-input v-model="ruleForm.bidder"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="法定代表人："  prop="bidderLegalPerson">
            <el-input v-model="ruleForm.bidderLegalPerson"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <!--<el-row>-->
        <!--<el-col :span="12">-->
          <!--<el-form-item label="造价咨询人：">-->
            <!--<el-input v-model="ruleForm.costConsultPerson"></el-input>-->
          <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :span="12">-->
          <!--<el-form-item label="造价工程师：">-->
            <!--<el-input v-model="ruleForm.costEngineer"></el-input>-->
          <!--</el-form-item>-->
        <!--</el-col>-->
      <!--</el-row>-->
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
        <el-col class="lastinfo">
          <el-form-item label="总说明："  prop="totalExplain">
            <el-input type="textarea" v-model="ruleForm.totalExplain"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
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
      <el-row>
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
export default {
  props: ['projectId'],
  data () {
    return {
      ruleForm: {
        networkCardListNumber: '',
        cpuListNumber: '',
        hardDiskListNumber: '',
        networkIp: ''
      },
      rules: {
        bidder: [
          { required: true, message: '请输入投标人', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在 1到 200 个字符', trigger: 'blur' }
        ],
        bidderLegalPerson: [
          { required: true, message: '请输入法定代表人', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1到 20 个字符', trigger: 'blur' }
        ],
        totalExplain: [
          { required: true, message: '请填写总说明', trigger: 'blur' }
        ],
        compilePerson: [
          { min: 1, max: 20, message: '长度在 1到 20 个字符', trigger: 'blur' }
        ]
      },
      // 招标信息
      projectCode: '',
      name: '',
      tenderee: '',
      tendereeLegalPerson: '',
      compilePerson: '',
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
        console.log(json)
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
    validateForm () {
      this.errorMsg = ''
      this.$refs['ruleForm'].validate((valid) => {
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
        this.projectCode = res.data.relateItemInfor.projectCode
        this.name = res.data.relateItemInfor.name
        this.tenderee = res.data.relateItemInfor.tenderee
        this.tendereeLegalPerson = res.data.relateItemInfor.tendereeLegalPerson
        this.compilePerson = res.data.relateItemInfor.compilePerson
        this.ruleForm = res.data.itemInfor
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
 #trendinfo{
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
   .lastinfo .el-form-item__content .el-textarea__inner{
     height: 160px;
   }
   .inselect{
     width: 360px;
   }
   .bidtable{
     width: 88%;
     margin: 0 auto 40px auto;
     border-collapse: collapse;
   }
   .bidtable tr{
     line-height: 26px;
   }
   .bidtable tr td{
     line-height: 48px;
     padding: 0 10px;
     border: 1px solid #ebeef5;
     font-size: 14px;
     color: #606266;
   }
   .el-date-editor.el-input, .el-date-editor.el-input__inner{
     width: 100%;
   }
 }
</style>
