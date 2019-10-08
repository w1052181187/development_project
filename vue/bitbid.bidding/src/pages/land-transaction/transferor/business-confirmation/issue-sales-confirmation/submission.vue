<template>
  <div id="issuesubbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>成交确认</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/business-confirmation/issue-sales-confirmation?roledsType=1' }">发出成交确认书</el-breadcrumb-item>
        <el-breadcrumb-item>提交</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="salesConfirmation" :rules="rules" ref="salesConfirmation" label-width="100px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="12">
                <el-form-item label="项目名称：">
                  <span>{{projectName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="项目编号：">
                  <span>{{projectNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="地块编号：">
                  <span>{{sectionForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="公告名称：">
                  <span>{{annoNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--退回申请信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">土地信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="竞得人名称：" prop="bidderName">
                  <span>{{salesConfirmation.bidderName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="成交价(万元)：" prop="strikePrice">
                  <span>{{salesConfirmation.strikePrice}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="竞得人公司地址：" prop="comPersonAddress">
                  <el-input v-model="salesConfirmation.comPersonAddress" placeholder="请输入竞得人公司地址"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="竞得人类别：" prop="personCategory">
                  <el-select v-model="salesConfirmation.personCategory" placeholder="请选择竞得人类别">
                    <el-option
                      v-for="item in personCategoryData"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="身份证号码：" prop="idNumber">
                  <el-input v-model="salesConfirmation.idNumber" placeholder="请输入身份证号码"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="成交地点：" prop="dealAdderss">
                  <el-input v-model="salesConfirmation.dealAdderss" placeholder="请输入成交地点"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="成交时间：" prop="dealTime">
                  <el-date-picker
                    v-model="salesConfirmation.dealTime"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="选择成交时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="价款缴纳方式：" prop="payMethod">
                  <el-select v-model="salesConfirmation.payMethod" placeholder="请选择价款缴纳方式" @change="selectSeacher">
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="价款缴纳时间：" prop="payTime">
                  <el-date-picker
                    v-model="salesConfirmation.payTime"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="选择价款缴纳时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="确认成交书：" prop="PayStatus">
                  <div class="editor">
                    <editor ref="ueditor" class="ueditor" :editread="editread" :content="content"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--退回申请信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('salesConfirmation')" :loading="subStatus">提交</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import {isvalidId} from '@/assets/js/validate'
import {salesCon} from '@/api'
export default {
  components: {
    editor
  },
  data () {
    // 身份证验证
    var validID = (rule, value, callback) => {
      if (!value) {
        callback(new Error('身份证号码不能为空'))
      } else if (!isvalidId(value)) {
        callback(new Error('请输入正确的18位身份证号码'))
      } else {
        callback()
      }
    }
    return {
      sectionForm: {},
      subStatus: false,
      editread: false, // 富文本状态
      content: '', // 富文本内容
      projectNumber: '',
      projectName: '',
      annoNumber: '',
      salesConfirmation: {
        personCategory: 1
      },
      value: '',
      options: [{
        value: 1,
        label: '一次性缴纳'
      }, {
        value: 2,
        label: '分期缴纳'
      }],
      personCategoryData: [{
        value: 1,
        label: '自然人'
      }, {
        value: 2,
        label: '法人'
      }, {
        value: 3,
        label: '联合体'
      }, {
        value: 4,
        label: '部队'
      }, {
        value: 5,
        label: '港澳企业'
      }, {
        value: 6,
        label: '境外企业'
      }, {
        value: 7,
        label: '二级单位（非法人）'
      }, {
        value: 8,
        label: '其他'
      }],
      rules: {
        comPersonAddress: [
          {required: true, message: '请输入竞得人公司地址', trigger: ['blur', 'change']},
          {min: 1, max: 100, message: '长度在 1~100个字符', trigger: ['blur', 'change']}
        ],
        dealAdderss: [
          {required: true, message: '请输入成交地点', trigger: ['blur', 'change']},
          {min: 1, max: 100, message: '长度在 1~100个字符', trigger: ['blur', 'change']}
        ],
        dealTime: [
          {required: true, message: '时间不能为空', trigger: ['blur', 'change']}
        ],
        payMethod: [
          {required: true, message: '价款缴纳方式不能为空', trigger: ['blur', 'change']}
        ],
        payTime: [
          {required: true, message: '时间不能为空', trigger: ['blur', 'change']}
        ],
        idNumber: [
          {required: true, message: '专家身份证号不能为空', trigger: ['blur', 'change']},
          {validator: validID, trigger: ['blur', 'change']}
        ]
      }
    }
  },
  methods: {
    list () {
      salesCon.detail(this.$route.params.objectId).then((res) => {
        this.sectionForm = res.data.salesConfirmation.landInformation
        this.salesConfirmation = res.data.salesConfirmation
        this.projectName = res.data.salesConfirmation.landInformation.landAnnoMation.projectName
        this.projectNumber = res.data.salesConfirmation.landInformation.landAnnoMation.projectNumber
        this.annoNumber = res.data.salesConfirmation.landInformation.landAnnoMation.annoNumber
      })
    },
    // 提交
    submitForm (salesConfirmation) {
      this.$refs[salesConfirmation].validate((valid) => {
        if (valid) {
          this.subStatus = true
          this.content = this.$refs.ueditor.getContent() // 富文本内容获取
          this.salesConfirmation.isCommit = 1
          this.salesConfirmation.salesConfirmation = this.content
          salesCon.salesConUpdate(this.salesConfirmation).then((res) => {
            this.subStatus = false
            this.$router.push({path: '/lt/transferor/business-confirmation/issue-sales-confirmation?roledsType=1'})
          })
        } else {
          return false
        }
      })
    },
    // 缴费方式选择
    selectSeacher (val) {
      if (val === 1) {
        this.salesConfirmation.payMethod = 1
      } else {
        this.salesConfirmation.payMethod = 2
      }
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/lt/transferor/business-confirmation/issue-sales-confirmation?roledsType=1'})
    },
    // 富文本
    setContent () {
      this.$refs.ueditor.setContent(this.content)
    }
  },
  computed: {
    IEVersion () {
      let browser = navigator.appName
      let bversion = navigator.appVersion
      let version = bversion.split(';')
      let trimVersion = version[1].replace(/[ ]/g, '')
      if (browser === 'Microsoft Internet Explorer' && trimVersion === 'MSIE9.0') {
        return 1
      }
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
