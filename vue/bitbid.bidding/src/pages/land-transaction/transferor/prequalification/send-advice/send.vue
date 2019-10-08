<template>
  <div id="marginappaddbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>资格预审会</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/prequalification/send-advice/index?roledsType=1&showStatus=false' }">发出资格预审结果通知书</el-breadcrumb-item>
        <el-breadcrumb-item>发出通知书</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="refundedSecurityForm" :rules="rules" ref="refundedSecurityForm" label-width="200px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="12">
                <el-form-item label="地块编号：">
                  <span>{{refundedSecurityForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="公告名称：">
                  <span>{{refundedSecurityForm.annoNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="项目名称：">
                  <span>{{refundedSecurityForm.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="项目编号：">
                  <span>{{refundedSecurityForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--报告信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">报告信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="24" class="filebigbox">
                <el-form-item label="资格审查报告：">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="提交人：">
                  <span>{{refundedSecurityForm.subName}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="提交时间：">
                  <span>{{refundedSecurityForm.subTime}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--报告信息-->
        <!--时间信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">时间信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="12">
                <el-form-item label="招标文件获取时间：" class="bitianicon timesbox">
                  <el-date-picker
                    v-model="biddingDataRange"
                    type="datetimerange"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    range-separator="至"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                    align="right">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="答疑澄清时间：" class="bitianicon timesbox">
                  <el-date-picker
                    v-model="answerDataRange"
                    type="datetimerange"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    range-separator="至"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                    align="right">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="递交投标文件截止时间：" prop="tenderDocEndTime" class="hourbox">
                  <el-date-picker
                    v-model="refundedSecurityForm.tenderDocEndTime"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="请输入递交投标文件截止时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="投标文件递交方法：" prop="tenderDocEndMethod">
                  <el-input v-model="refundedSecurityForm.tenderDocEndMethod" placeholder="请输入投标文件获取方法"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="投标有效期（天）：" prop="validityOfBid">
                  <el-input v-model="refundedSecurityForm.validityOfBid" placeholder="请输入投标有效期"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="评标办法：" prop="bidEvaluationMethod">
                  <el-select v-model="refundedSecurityForm.bidEvaluationMethod">
                    <el-option
                      v-for="item in bidEvaluationData"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8" class="hourbox">
                <el-form-item label="开标时间：" prop="bidOpeningTime">
                  <el-date-picker
                    v-model="refundedSecurityForm.bidOpeningTime"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="请输入开标时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="开标地点：" prop="placeOfBidOpening">
                  <el-input v-model="refundedSecurityForm.placeOfBidOpening" placeholder="请输入开标地点"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="开标方式：" prop="bidOpeningWay">
                  <el-select v-model="refundedSecurityForm.bidOpeningWay">
                    <el-option
                      v-for="item in openingData"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="评标方式：" prop="bidEvaluationMethods">
                  <el-select v-model="refundedSecurityForm.bidEvaluationMethods">
                    <el-option
                      v-for="item in evaluationMethodData"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--时间信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('refundedSecurityForm')">发出</el-button>
          <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import {sumType} from '@/assets/js/validate'
export default {
  components: {
    commonFileDownload,
    editor
  },
  data () {
    // 数字
    var sumTypes = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入数字'))
      } else if (!sumType(value)) {
        callback(new Error('请输入数字'))
      } else {
        callback()
      }
    }
    return {
      refundedSecurityForm: {},
      content: '',
      biddingDataRange: [], // 招标文件获取时间
      answerDataRange: [], // 答疑澄清时间
      // 评标办法
      bidEvaluationData: [
        {
          value: 1,
          label: '综合评估法'
        }, {
          value: 2,
          label: '经评审最高价中标法'
        }
      ],
      // 开标方式
      openingData: [
        {
          value: 1,
          label: '在线开标'
        }, {
          value: 2,
          label: '线下开标'
        }
      ],
      // 评标方式
      evaluationMethodData: [
        {
          value: 1,
          label: '在线评标'
        }, {
          value: 2,
          label: '线下评标'
        }
      ],
      rules: {
        tenderDocEndMethod: [
          { required: true, message: '请输入投标文件递交方法', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] }
        ],
        bidOpeningWay: [
          {required: true, message: '请选择开标方式', trigger: ['blur', 'change']}
        ],
        bidEvaluationMethods: [
          {required: true, message: '请选择评标方式', trigger: ['blur', 'change']}
        ],
        bidOpeningTime: [
          {required: true, message: '请选择开标时间', trigger: ['blur', 'change']}
        ],
        bidEvaluationMethod: [
          {required: true, message: '请选择评标办法', trigger: ['blur', 'change']}
        ],
        placeOfBidOpening: [
          { required: true, message: '请输入开标地点', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在 1~200个字符', trigger: ['blur', 'change'] }
        ],
        tenderDocEndTime: [
          {required: true, message: '请选择递交投标文件截止时间', trigger: ['blur', 'change']}
        ],
        validityOfBid: [
          { required: true, message: '请输入投标有效期', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] },
          { validator: sumTypes, trigger: ['blur', 'change'] }
        ]
      },
      fileObjecit: {
        fileName: '测试数据'
      }
    }
  },
  methods: {
    list () {},
    // 发送
    submitForm (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          console.log(this.refundedSecurityForm)
        } else {
          return false
        }
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
  #marginappaddbox{
    .timesbox .el-form-item__content{
      width: 70%;
    }
    .hourbox .el-date-editor{
      width: 100%;
    }
  }
</style>
