<template>
  <div class="cloudcontent" id="cloud_enterprise">
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item class="active_bread">查看企业信息</el-breadcrumb-item>
    </el-breadcrumb>
    <!--面包屑-->
    <div class="main">
      <p class="title">
        企业信息
        <span>注：完善资料后即可获得更多服务，*号为必填项。</span>
        <img src="../../../../static/images/check.png"/>
        <img src="../../../../static/images/check_no.png"/>
        <img src="../../../../static/images/check_ok.png"/>
      </p>
      <el-form :model="enterpriseForm" :validate-on-rule-change="true">
        <div class="part">
          <h3>企业基本信息</h3>
          <div class="formmain">
            <el-row>
              <el-col :span="12">
                <el-form-item label="企业名称：">
                  <span>{{enterpriseForm.name}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="统一信用代码：">
                  <span>{{enterpriseForm.uscCode}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="法定代表人：">
                  <span>{{enterpriseForm.businessLicenseName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="法人身份证号："  prop="businessLicenseCardNo">
                  <span>{{enterpriseForm.businessLicenseCardNo}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="所属行业：">
                  <span>{{enterpriseForm.industry}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="所在地区：">
                  <span>{{enterpriseForm.privinceId}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="详细地址：">
                  <span>{{enterpriseForm.address.addr}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <div class="part">
          <h3>联系人信息</h3>
          <div class="formmain">
            <el-row>
              <el-col :span="12">
                <el-form-item label="联系人：">
                  <span>{{enterpriseForm.contactName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="手机号码：">
                  <span>{{enterpriseForm.contactCellphone}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="邮箱：">
                  <span>{{enterpriseForm.email}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <div class="part">
          <h3>企业证件</h3>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="营业执照：">
                  <uploadlFile @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></uploadlFile>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="组织机构代码证：" v-if="Number(this.ctype) === 1">
                  <uploadlFile @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></uploadlFile>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="税务登记证：" v-if="Number(this.ctype) === 1">
                  <uploadlFile @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></uploadlFile>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="法人身份证（正）：" >
                  <uploadlFile @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></uploadlFile>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="法人身份证（反）：" >
                  <uploadlFile @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></uploadlFile>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="开户许可：">
                  <uploadlFile @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></uploadlFile>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <el-form-item class="submit-radio">
          <el-button type="primary">修改</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import uploadlFile from '../../../components/upload/publicFileUpload'
import * as region from '@/assets/js/region'
import * as industry from '@/assets/js/industry_three'
export default {
  components: {
    uploadlFile
  },
  data () {
    return {
      enterpriseForm: {
        name: '',
        uscCode: '',
        address: {
          privinceId: '',
          cityId: '',
          countyId: '',
          addr: ''
        },
        businessLicenseName: '',
        businessLicenseCardNo: '',
        industry: '',
        industrySecond: '',
        industryThird: ''
      },
      // 地址三级联动
      addressOptions: region.CityInfo,
      selectedcityOptions: [],
      // 行业三级联动
      industryOptions: industry.industry,
      selectedIndustryOptions: [],
      fileType: 1,
      fileArry: [],
      oldFileName: '',
      ctype: '1'
    }
  },
  methods: {
    // 将子组件获取到的数据传给父组件
    upLoadFile (file) {
      this.fileArry = file
    },
    // 所属行业三级联动赋值
    handleIndustryChange (value) {
      this.enterpriseForm.industry = value[0]
      this.enterpriseForm.industrySecond = value[1]
      this.enterpriseForm.industryThird = value[2]
    },
    // 所在地区三级联动赋值
    handlecityChange (value) {
      this.enterpriseForm.address.privinceId = value[0]
      this.enterpriseForm.address.cityId = value[1]
      this.enterpriseForm.address.countyId = value[2]
    }
  }
}
</script>
<style lang="less">
  #cloud_enterprise {
    .main p.title{
      padding: 0 20px;
      font-size: 16px;
      font-weight: bold;
      color: #111111;
      line-height: 32px;
      position: relative;
    }
    .main p.title span{
      display: inline-block;
      width: 100%;
      line-height: 28px;
      color: #999999;
      font-size: 14px;
    }
    .main p.title img{
      position: absolute;
      right: 10px;
      top: 10px;
    }
    .part{
      overflow: hidden;
      margin: 12px auto;
    }
    .part h3{
      height: 36px;
      line-height: 36px;
      background: #f5f5f5;
      color: #111111;
      font-size: 14px;
      padding: 0 20px;
    }
    .formmain{
      overflow: hidden;
      width: 90%;
      margin: 30px auto;
    }
    .el-cascader{
      width: 41%;
    }
    .ctype{
      margin: 20px 0 30px 60px;
    }
  }
</style>
