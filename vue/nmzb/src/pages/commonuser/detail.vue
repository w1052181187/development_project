<template>
  <div id="commonuser_two">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/index/commonuser' }">会员管理</el-breadcrumb-item>
        <el-breadcrumb-item>查看会员</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <template>
          <el-button @click="$router.go(-1)" style="margin-right: 15px;">返回</el-button>
        </template>
      </div>
    </div>
    <div class="contentbigbox">
      <span class="title">企业信息</span>
      <el-form  label-width="180px" :model="ruleForm" ref="ruleForms" :label-position="'right'">
        <el-form-item label="公司类型 :">
          <span style="text-align: left;display: block;padding-left: 30px" v-if="ruleForm.enterprise.enterType === '1'">招标人</span>
          <span style="text-align: left;display: block;padding-left: 30px" v-if="ruleForm.enterprise.enterType === '2'">投标人</span>
          <span style="text-align: left;display: block;padding-left: 30px" v-if="ruleForm.enterprise.enterType === '3'">代理机构</span>
          <span style="text-align: left;display: block;padding-left: 30px" v-if="ruleForm.enterprise.enterType === '4'">其他</span>
        </el-form-item>
        <el-form-item label=" 企业名称 :">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.enterprise.name}}</span>
        </el-form-item>
        <el-form-item label=" 所属区域 : " style="text-align: left">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.registerAddress}}</span>
        </el-form-item>
        <el-form-item label=" 公司地址 : ">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.enterprise.address}}</span>
        </el-form-item>
        <el-form-item label=" 所属行业 : " style="text-align: left">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.industry}}</span>
        </el-form-item>
        <el-form-item label=" 营业执照附件 : " style="text-align: left">
          <file-download :fileArrays="ruleForm.enterprise.fileInformations" fileType="1"></file-download>
        </el-form-item>
        <el-form-item label=" 银行开户证明 : " style="text-align: left" >
          <file-download :fileArrays="ruleForm.enterprise.fileInformations" fileType="1"></file-download>
        </el-form-item>
        <span class="title"  style=" border-left: 2px solid #00a0e9;">联系人信息</span>
        <el-form-item label=" 姓名 : ">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.name}}</span>
        </el-form-item>
        <el-form-item label=" 移动电话 : ">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.registeredCellPhone}}</span>
        </el-form-item>
        <el-form-item label=" 固定电话 : ">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.fixedTelephone}}</span>
        </el-form-item>
        <el-form-item label=" 电子邮箱 : ">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.email}}</span>
        </el-form-item>
        <span class="title" style=" border-left: 2px solid #0000e9;" v-if="ruleForm.status === 1 || ruleForm.status === 2">审批记录</span>
        <div class="approveRecord"><approveRecord v-if="ruleForm.status === 1 || ruleForm.status === 2" :code="ruleForm.code"></approveRecord></div>
      </el-form>
    </div>
  </div>
</template>
<script>
import * as industry from '../../assets/js/industry'
import * as region from '../../assets/js/regionThird'
import {commonUser} from '@/api'
import approveRecord from '@/components/approve-record.vue'
import fileDownload from '@/components/file-download'
export default {
  components: {
    approveRecord,
    fileDownload
  },
  data () {
    return {
      ruleForm: {
        enterprise: {
          enterType: '',
          name: '',
          address: '',
          provinceId: '',
          cityId: '',
          industryTypeFirst: '',
          uscCode: '',
          fileInformations: []
        },
        name: '',
        registeredCellPhone: '',
        fixedTelephone: '',
        email: '',
        recordOfApprovals: []
      },
      addressArr: region.regionInfo,
      industryArr: industry.industry,
      // 营业执照
      businessFile: {},
      // 银行开户证明
      bankOpenFile: {}
    }
  },
  methods: {
    detail () {
      commonUser.detail(this.$route.query.objectId).then((res) => {
        this.ruleForm = res.data.user
        // 注册地址
        this.ruleForm.registerAddress = ''
        this.addressArr.map((item) => {
          if (item.value === this.ruleForm.enterprise.provinceId) {
            this.ruleForm.registerAddress += item.label
            item.children.map((ite) => {
              if (ite.value === this.ruleForm.enterprise.cityId) {
                this.ruleForm.registerAddress += ite.label
              }
            })
          }
        })
        // 行业
        this.ruleForm.industry = ''
        this.industryArr.map((item) => {
          if (item.value === this.ruleForm.enterprise.industryTypeFirst) {
            this.ruleForm.industry += item.label
          }
        })
        // 文件
        this.ruleForm.enterprise.fileInformations.map((file) => {
          if (file.businessType === '1') {
            this.businessFile = file
          } else if (file.businessType === '2') {
            this.bankOpenFile = file
          }
        })
      })
    }
  },
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  #commonuser_two{
    .el-form-item{
      padding-left: 50px;
    }
    .el-input{
      width: 500px;
    }
    .el-button--primary{
      margin-left: 180px;
    }
    .el-button--success{
      float: none;
    }
    span.title{
      height: 30px;
      line-height: 30px;
      text-align: left;
      display: block;
      padding-left: 20px;
      margin-bottom: 20px;
      font-size: 16px;
      font-weight: bold;
      border-left: 2px solid #2c972f;
    }
    span.smalltitle{
      height: 30px;
      line-height: 30px;
      text-align: left;
      display: block;
      margin-bottom: 20px;
      font-size: 16px;
      font-weight: bold;
    }
    span.intitle{
      padding-left: 20px;
    }
    .tit{
      display: none;
    }
    .approveRecord>tr>td:first-child{
      display: none;
    }
    .fivecolumn{
      width: 80%;
    }
  }
</style>
