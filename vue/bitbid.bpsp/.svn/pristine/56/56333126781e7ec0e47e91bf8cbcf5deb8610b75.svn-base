<template>
  <div id="commonuser_two">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
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
        <el-form-item label="角色类型 :">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.role}}</span>
        </el-form-item>
        <el-form-item label=" 企业名称 :">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.name}}</span>
        </el-form-item>
        <el-form-item label=" 注册地址 : " style="text-align: left">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.registerAddress}}</span>
        </el-form-item>
        <el-form-item label=" 详细地址 : ">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.address}}</span>
        </el-form-item>
        <el-form-item label=" 所属行业 : " style="text-align: left" prop="industryTypeFirst">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.industry}}</span>
        </el-form-item>
        <el-form-item label=" 统一社会信用代码 : ">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.uscCode}}</span>
        </el-form-item>
        <el-form-item label=" 法定代表人姓名 : ">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.businessLicenseName}}</span>
        </el-form-item>
        <el-form-item label=" 法定代表人身份证号码 : " prop="businessLicenseCardNo">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.businessLicenseCardNo}}</span>
        </el-form-item>
        <span class="title"  style=" border-left: 2px solid #00a0e9;">联系人信息</span>
        <el-form-item label=" 姓名 : ">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.user.name}}</span>
        </el-form-item>
        <el-form-item label=" 移动电话 : ">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.user.cellphone}}</span>
        </el-form-item>
        <el-form-item label=" 电子邮箱 : ">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.user.email}}</span>
        </el-form-item>
        <span class="title" style=" border-left: 2px solid #0000e9;" v-if="ruleForm.status === 2 || ruleForm.status === 3">审批记录</span>
        <approveRecord v-if="ruleForm.status === 2 || ruleForm.status === 3" :code="ruleForm.code"></approveRecord>
      </el-form>
    </div>
  </div>
</template>
<script>
import * as industry from '../../assets/js/industry'
import * as region from '../../assets/js/regionThird'
import {enterprise} from '@/api'
import approveRecord from '@/components/approve-record.vue'
export default {
  components: {
    approveRecord
  },
  data () {
    return {
      ruleForm: {
        role: [],
        name: '',
        address: '',
        provinceId: '',
        cityId: '',
        countryId: '',
        industryTypeFirst: '',
        industryTypeSecond: '',
        businessLicenseName: '',
        uscCode: '',
        businessLicenseCardNo: '',
        user: {
          name: '',
          cellphone: '',
          email: ''
        },
        recordOfApprovals: []
      },
      addressArr: region.regionInfo,
      industryArr: industry.industry
    }
  },
  methods: {
    detail () {
      enterprise.detail(this.$route.query.objectId).then((res) => {
        this.ruleForm = res.data.enterprise
        // 注册地址
        this.ruleForm.registerAddress = ''
        this.addressArr.map((item) => {
          if (item.value === this.ruleForm.provinceId) {
            this.ruleForm.registerAddress += item.label
            item.children.map((ite) => {
              if (ite.value === this.ruleForm.cityId) {
                this.ruleForm.registerAddress += ite.label
                ite.children.map((it) => {
                  if (it.value === this.ruleForm.countryId) {
                    this.ruleForm.registerAddress += it.label
                  }
                })
              }
            })
          }
        })
        // 行业
        this.ruleForm.industry = ''
        this.industryArr.map((item) => {
          if (item.value === this.ruleForm.industryTypeFirst) {
            this.ruleForm.industry += item.label
            item.children.map((ite) => {
              if (ite.value === this.ruleForm.industryTypeSecond) {
                this.ruleForm.industry += ite.label
              }
            })
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
    .fivecolumn{
      width: 80%;
    }
  }
</style>
