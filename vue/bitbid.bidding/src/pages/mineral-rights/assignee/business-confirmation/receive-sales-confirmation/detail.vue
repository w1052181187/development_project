<template>
  <div id="recesaldetailboxbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>成交确认</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/assignee/business-confirmation/receive-sales-confirmation?roledsType=2' }">接收成交确认书</el-breadcrumb-item>
        <el-breadcrumb-item>查看成交确认书</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" label-width="100px" class="demo-ruleForm">
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
                <el-form-item label="竞得人公司地址：" prop="unifiedTransCode">
                  <span>{{salesConfirmation.comPersonAddress}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="成交地点：" prop="unifiedTransCode">
                  <span>{{salesConfirmation.dealAdderss}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="成交时间：" prop="unifiedTransCode">
                  <span>{{salesConfirmation.dealTime}}</span>
                </el-form-item>
              </el-col>
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
      editread: true, // 富文本状态
      content: '', // 富文本内容
      sectionForm: {},
      salesConfirmation: {},
      announcement: '',
      value: '',
      fileList: [],
      options: [{
        value: 0,
        label: '一次性缴纳'
      }, {
        value: 1,
        label: '分期缴纳'
      }]
    }
  },
  methods: {
    list () {
      salesCon.detail(this.$route.params.objectId).then((res) => {
        this.sectionForm = res.data.salesConfirmation.section
        this.announcement = res.data.salesConfirmation.section.announcement.annoNumber
        this.salesConfirmation = res.data.salesConfirmation
        this.content = res.data.salesConfirmation.salesConfirmation
        this.setContent()
      })
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
