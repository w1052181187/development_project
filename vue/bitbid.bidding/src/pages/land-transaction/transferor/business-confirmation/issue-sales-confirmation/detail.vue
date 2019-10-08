<template>
  <div id="issuedetailbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>成交确认</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/business-confirmation/issue-sales-confirmation?roledsType=1' }">发出成交确认书</el-breadcrumb-item>
        <el-breadcrumb-item>查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="salesConfirmation" label-width="100px" class="demo-ruleForm">
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
                <el-form-item label="竞得人公司地址：" prop="unifiedTransCode">
                  <span>{{salesConfirmation.comPersonAddress}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="竞得人类别：" prop="personCategory">
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
                <el-form-item label="身份证号码：" prop="idNumber">
                  <span>{{salesConfirmation.idNumber}}</span>
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
                    <editor ref="confCon" class="ueditor" :editread="editread" :content="content"></editor>
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
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import {salesCon} from '@/api'
export default {
  components: {
    editor
  },
  data () {
    return {
      editread: true, // 富文本状态
      content: '', // 富文本内容
      salesConfirmation: {},
      sectionForm: {},
      subStatus: false,
      projectNumber: '',
      projectName: '',
      annoNumber: '',
      value: ''
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
