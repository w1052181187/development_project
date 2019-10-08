<template>
  <div id="commonuser_one">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>审核会员</el-breadcrumb-item>
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
      </el-form>
      <span class="title"  style=" border-left: 2px solid #00a0e9;">联系人信息</span>
      <el-form  label-width="180px" :label-position="'right'" >
        <el-form-item label=" 姓名 : ">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.user.name}}</span>
        </el-form-item>
        <el-form-item label=" 移动电话 : ">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.user.cellphone}}</span>
        </el-form-item>
        <el-form-item label=" 电子邮箱 : ">
          <span style="text-align: left;display: block;padding-left: 30px">{{ruleForm.user.email}}</span>
        </el-form-item>
        <span class="title"  style=" border-left: 2px solid #0000e9;">审批结果</span>
        <approve @approveForm="approveForm" ref="approveForm"></approve>
        <el-form-item class="btnbox">
          <el-button type="primary" size="medium " class="btn" @click="submit">确定</el-button>
          <el-button type="success" size="medium " class="btn" @click="cancel">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import approve from '@/components/approve.vue'
import * as industry from '../../assets/js/industry'
import * as region from '../../assets/js/regionThird'
import {enterprise} from '@/api'
export default {
  components: {
    approve
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
      childForm: {},
      addressArr: region.regionInfo,
      industryArr: industry.industry
    }
  },
  methods: {
    // 详情
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
    },
    approveForm (formV) {
      this.childForm = formV
      this.childForm.relatedCode = this.ruleForm.code
    },
    // 提交
    submit () {
      // 必填验证
      this.$refs.approveForm.handleValidate()
      if (this.childForm.auditStatus === 0 && !this.childForm.opinion.trim()) {
        this.$message({
          message: '请填写审批意见',
          type: 'warning'
        })
        return false
      }
      this.ruleForm.recordOfApprovals[0] = this.childForm
      // 同步审批结果
      this.ruleForm.status = this.childForm.auditStatus === 1 ? 2 : 3
      enterprise.audit(this.ruleForm).then((res) => {
        this.$router.push({path: '/index/commonuser'})
      })
    },
    // 取消
    cancel () {
      this.$router.push({path: '/index/commonuser'})
    }
  },
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
   #commonuser_one{
     .el-form-item{
       padding-left: 50px;
     }
     .el-input{
       width: 500px;
     }
     .el-button--primary{
       margin-left: 182px;
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
     .btnbox .el-form-item__content{
       margin-left: 0!important;
       text-align: left;
     }
     .examine{
       display: none;
     }
     .tr_examine{
       width: 800px!important;
     }
     .tr_examine .el-form{
       width: 800px;
     }
     .tr_examine .el-form .el-form-item__label{
       width: 182px!important;
     }
     .tr_examine .el-form .el-form-item__content{
       margin-left: 182px!important;
     }
     .tr_examine .el-form .el-form-item__content{
       text-align: left;
     }
     .tr_examine .el-form .el-textarea__inner{
       min-height: 100px!important;
     }
   }
</style>
