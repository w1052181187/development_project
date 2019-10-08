<template>
  <div id="issuesendoutbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>成交确认</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/transferor/business-confirmation/issue-sales-confirmation?roledsType=1' }">发出成交确认书</el-breadcrumb-item>
        <el-breadcrumb-item>发送</el-breadcrumb-item>
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
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm()">签名确认</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/public-resource/mineral-rights/common/ueditor/ueditor.vue'
// import {prProject} from '@/api/public-resource/mineral-rights/project/index'
import {salesCon} from '@/api/public-resource/mineral-rights/sales-confirmation/index'
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
        value: 1,
        label: '一次性缴纳'
      }, {
        value: 2,
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
    // 提交
    submitForm (sectionForm) {
      this.salesConfirmation.isSend = 1
      this.salesConfirmation.sendTime = this.$route.query.times
      salesCon.salesConUpdate(this.salesConfirmation).then((res) => {
        this.$router.push({path: '/mr/transferor/business-confirmation/issue-sales-confirmation?roledsType=1'})
      })
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/mr/transferor/business-confirmation/issue-sales-confirmation?roledsType=1'})
    },
    // 富文本
    setContent () {
      this.$refs.ueditor.setContent(this.content)
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
