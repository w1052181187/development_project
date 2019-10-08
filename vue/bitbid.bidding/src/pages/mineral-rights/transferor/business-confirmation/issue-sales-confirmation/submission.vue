<template>
  <div id="issuesubbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>成交确认</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/transferor/business-confirmation/issue-sales-confirmation?roledsType=1' }">发出成交确认书</el-breadcrumb-item>
        <el-breadcrumb-item>提交</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="salesConfirmation" :rules="rules" ref="sectionForm" label-width="100px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：" prop="sectionName">
                  <span>{{sectionForm.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：" prop="sectionNumber">
                  <span>{{sectionForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="公告名称：" prop="unifiedTransCode">
                  <span>{{announcement}}</span>
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
          <el-button type="primary" @click="submitForm('sectionForm')">提交</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/public-resource/mineral-rights/common/ueditor/ueditor.vue'
import {salesCon} from '@/api'
export default {
  components: {
    editor
  },
  data () {
    return {
      editread: false, // 富文本状态
      content: '', // 富文本内容
      sectionForm: {},
      salesConfirmation: {},
      announcement: '',
      value: '',
      fileList: [],
      options: [{
        value: 1,
        label: '一次性缴纳'
      }, {
        value: 2,
        label: '分期缴纳'
      }],
      rules: {
        comPersonAddress: [
          { required: true, message: '请输入竞得人公司地址', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1~100个字符', trigger: ['blur', 'change'] }
        ],
        dealAdderss: [
          { required: true, message: '请输入成交地点', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1~100个字符', trigger: ['blur', 'change'] }
        ],
        dealTime: [
          { required: true, message: '时间不能为空', trigger: 'blur' }
        ],
        payMethod: [
          { required: true, message: '价款缴纳方式不能为空', trigger: ['blur', 'change'] }
        ],
        payTime: [
          { required: true, message: '时间不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    list () {
      salesCon.detail(this.$route.params.objectId).then((res) => {
        this.sectionForm = res.data.salesConfirmation.section
        this.announcement = res.data.salesConfirmation.section.announcement.annoNumber
        this.salesConfirmation = res.data.salesConfirmation
      })
    },
    // 提交
    submitForm (sectionForm) {
      this.$refs[sectionForm].validate((valid) => {
        if (valid) {
          this.content = this.$refs.ueditor.getContent() // 富文本内容获取
          this.salesConfirmation.isCommit = 1
          this.salesConfirmation.salesConfirmation = this.content
          salesCon.salesConUpdate(this.salesConfirmation).then((res) => {
            this.$router.push({path: '/mr/transferor/business-confirmation/issue-sales-confirmation?roledsType=1'})
          })
        } else {
          return false
        }
      })
    },
    // 缴费方式选择
    selectSeacher (val) {
      if (val === 1) {
        this.sectionForm.payMethod = 1
      } else {
        this.sectionForm.payMethod = 2
      }
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/mr/transferor/business-confirmation/issue-sales-confirmation'})
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
  @import '../../../../../assets/css/mineral/common.css';
</style>
