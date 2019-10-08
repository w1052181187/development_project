<template>
  <div id="recesalbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>成交确认</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/business-confir/receive-sales/index?roledsType=2&showStatus=false' }">接收成交确认书</el-breadcrumb-item>
        <el-breadcrumb-item>确认竞得人</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="salesConfirmation" :rules="rules" ref="sectionForm"  label-width="150px" class="demo-ruleForm">
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
          <span class="title" style="border-left: 3px solid #099cff">矿权信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="竞得人名称：" prop="sectionName">
                  <span>{{salesConfirmation.bidderName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="成交价(万元)：" prop="sectionNumber">
                  <span>{{salesConfirmation.strikePrice}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="竞得人公司地址：" prop="comPersonAddress">
                  <span>{{salesConfirmation.comPersonAddress}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="竞得人类别：">
                  <span v-if="salesConfirmation.personCategory === 1">自然人</span>
                  <span v-if="salesConfirmation.personCategory === 2">法人</span>
                  <span v-if="salesConfirmation.personCategory === 3">联合体</span>
                  <span v-if="salesConfirmation.personCategory === 4">部队</span>
                  <span v-if="salesConfirmation.personCategory === 5">港澳企业</span>
                  <span v-if="salesConfirmation.personCategory === 6">境外企业</span>
                  <span v-if="salesConfirmation.personCategory === 7">二级单位（非法人）</span>
                  <span v-if="salesConfirmation.personCategory === 8">其他</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="身份证：" prop="idNumber">
                  <span>{{salesConfirmation.idNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="成交地点：" prop="unifiedTransCode">
                  <span>{{salesConfirmation.dealAdderss}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="成交时间：" prop="unifiedTransCode">
                  <span>{{salesConfirmation.dealTime}}</span>
                </el-form-item>
              </el-col>
              <!--<el-col :span="8">-->
                <!--<el-form-item label="价款缴纳方式：" prop="payMethod">-->
                  <!--<el-select v-model="salesConfirmation.payMethod" placeholder="请选择价款缴纳方式">-->
                    <!--<el-option-->
                      <!--v-for="item in options"-->
                      <!--:key="item.value"-->
                      <!--:label="item.label"-->
                      <!--:value="item.value">-->
                    <!--</el-option>-->
                  <!--</el-select>-->
                <!--</el-form-item>-->
              <!--</el-col>-->
              <el-col :span="8">
                <el-form-item label="价款缴纳方式：" prop="unifiedTransCode">
                  <span v-if="salesConfirmation.payMethod === 1">一次性缴纳</span>
                  <span v-if="salesConfirmation.payMethod === 2">分期缴纳</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="价款缴纳时间：" prop="unifiedTransCode">
                  <span>{{salesConfirmation.payTime}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="确认成交书：" prop="PayStatus">
                  <div class="editor">
                    <editor ref="confCon" class="ueditor" :editread="editread" :content="content"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--退回申请信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('salesConfirmation')" :loading="subStatus">签名确认</el-button>
          <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
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
      editread: true, // 富文本状态
      content: '', // 富文本内容
      salesConfirmation: {},
      sectionForm: {},
      subStatus: false,
      projectNumber: '',
      projectName: '',
      annoNumber: '',
      value: '',
      options: [{
        value: 0,
        label: '一次性缴纳'
      }, {
        value: 1,
        label: '分期缴纳'
      }],
      rules: {
        idNumber: [
          {required: true, message: '专家身份证号不能为空', trigger: ['blur', 'change']},
          {validator: validID, trigger: ['blur', 'change']}
        ],
        comPersonAddress: [
          {required: true, message: '请输入竞得人公司地址', trigger: ['blur', 'change']},
          {min: 1, max: 100, message: '长度在 1~100个字符', trigger: ['blur', 'change']}
        ],
        payMethod: [
          {required: true, message: '价款缴纳方式不能为空', trigger: ['blur', 'change']}
        ]
      }
    }
  },
  methods: {
    list () {
      salesCon.detail(this.$route.params.objectId).then((res) => {
        this.sectionForm = res.data.salesConfirmation.landInformation
        this.salesConfirmation = res.data.salesConfirmation
        this.content = res.data.salesConfirmation.salesConfirmation
        this.projectName = res.data.salesConfirmation.landInformation.landAnnoMation.projectName
        this.projectNumber = res.data.salesConfirmation.landInformation.landAnnoMation.projectNumber
        this.annoNumber = res.data.salesConfirmation.landInformation.landAnnoMation.annoNumber
        this.$refs.confCon.setContent(this.content)
      })
    },
    // 提交
    submitForm (salesConfirmation) {
      this.subStatus = true
      this.salesConfirmation.isAffirm = 1
      this.sectionForm.affirmTime = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate() + ' ' + new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds()
      salesCon.salesConUpdate(this.salesConfirmation).then((res) => {
        this.subStatus = false
        this.$router.push({path: '/lt/assignee/business-confir/receive-sales/index?roledsType=2'})
      })
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
