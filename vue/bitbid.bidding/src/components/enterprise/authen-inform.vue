<template>
  <div id="homeautheninf">
    <div class="homepagecontent">
      <publicside></publicside>
      <div class="homepageconright">
        <!--面包屑-->
        <el-breadcrumb class="breadcrumbbox" separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/authentication' }">企业认证</el-breadcrumb-item>
          <el-breadcrumb-item>详情页面</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
        <!--内容开始-->
        <div class="penserviceconbox">
          <div class="Authentication_statebigbox" v-show="LookStatus == true">
            <div class="Authentication_statebox">
              <div class="iconimgstatus">
                <img src="../../../static/images/dui.png" alt="" v-show="inForm.isApprove==2">
                <img src="../../../static/images/kema.png" alt="" v-show="inForm.isApprove==1">
                <img src="../../../static/images/cuo.png" alt="" v-show="inForm.isApprove==3">
              </div>
              <div class="Authenxinxibox">
                <div class="Authenxinxitop">
                  <span style="color: #43bf2b" v-show="inForm.isApprove==2">恭喜您，认证成功！</span>
                  <span style="color: #3f63f6" v-show="inForm.isApprove==1" class="kema">数据已提交审核！</span>
                  <span style="color: #ff6363" v-show="inForm.isApprove==3">很遗憾，认证失败！</span>
                </div>
                <div class="Authenxinxibottom" v-show="inForm.isApprove!=1">
                  <div class="Authenbottomson">
                    <span>审核人：</span>
                    <span>{{inForm.auditPersonName}}</span>
                  </div>
                  <div class="Authenbottomsontwo">
                    <span>审核时间：</span>
                    <span>{{inForm.lastUpdateDate}}</span>
                  </div>
                  <div class="Authenbottomsontwo">
                    <span>审核意见：</span>
                    <span>{{inForm.approveReason}}</span>
                  </div>
                </div>
                <!--<div class="chongxin_btn" v-show="inForm.isApprove==3">重新认证</div>-->
              </div>
            </div>
          </div>
          <el-collapse v-model="activeName" accordion>
            <el-row type="flex" class="row-bg">
              <el-form :model="inForm" ref="inForm" class="demo-ruleForm">
                <el-collapse-item title="基本信息" name="1" class="contitlebox">
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="企业名称：" prop="enterpriseName" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseName" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="法定代表人姓名：" prop="legalPersonName" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.legalPersonName" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="所属行业：" prop="industry" :label-width="formLabelWidth">
                      <el-cascader class=""
                                   expand-trigger="hover"
                                   :options="industryOptions"
                                   v-model="selectedOptions"
                                   disabled>
                      </el-cascader>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="组织机构代码：" prop="orgCode" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.orgCode" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"   label="加盖公章的身份证复印件：" :label-width="formLabelWidth">
                      <el-button type="success" size="mini" @click="handleEdit(inForm.idCardFileName,inForm.idCardFileUrl)">下载查看</el-button>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon" label="注册地址：" :label-width="formLabelWidth">
                      <el-cascader class=""
                                   expand-trigger="hover"
                                   :options="addressOptions"
                                   v-model="selectedcityAddress"
                                   disabled>
                      </el-cascader>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="详细地址：" prop="detailAddress" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.detailAddress" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"   label="身份证号码：" prop="legalPersonCardNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.legalPersonCardNum" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="注册资本:" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.registeredCapital" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="营业执照附件：" :label-width="formLabelWidth">
                      <el-button type="success" size="mini" @click="handleEdit(inForm.businessLicenseFileName,inForm.businessLicenseFileUrl)">下载查看</el-button>
                    </el-form-item>
                  </el-col>
                </el-collapse-item>
                <el-collapse-item title="人员信息" name="2" class="contitlebox" v-show="flag==true">
                  <el-col :span="12">
                    <el-form-item  label="全部人员数目：" prop="personalInfo.allPersonNum" :label-width="formLabelWidth">
                      <el-input v-model="inForm.personalInfo.allPersonNum" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="全部硕士以上学历数目：" prop="personalInfo.allMasterNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.allMasterNum" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="全部高级职称数目：" prop="personalInfo.allTopTitleNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.allTopTitleNum" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="招标从业人员数目：" prop="personalInfo.allBidNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.allBidNum" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="全部中级职称数目：" prop="personalInfo.allMiddleTitleNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.allMiddleTitleNum" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="招标从业人员中级及以上职数目：" prop="personalInfo.bidMiddleUpNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.bidMiddleUpNum" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="招标从业人员高级职称数目：" prop="personalInfo.bidTopTitleNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.bidTopTitleNum" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="招标从业人员中级职称数目：" prop="personalInfo.bidMiddleNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.bidMiddleNum" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="招标从业人员中级及以上职占比：" prop="personalInfo.bidMiddleTitlePro" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.bidMiddleTitlePro" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="其他职业资格人员数量：" prop="personalInfo.otherQuaNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.otherQuaNum" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="全部具备职业资格数目：" prop="personalInfo.allTitleQuaNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.allTitleQuaNum" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="其他职业资格人员备注：" prop="personalInfo.otherQuaRem" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.otherQuaRem" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="已登记招标师数量：" prop="personalInfo.tendererNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.tendererNum" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="造价工程师数量：" prop="personalInfo.costEngineerNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.costEngineerNum" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="已登记招标师占比：" prop="personalInfo.tendererPro" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.tendererPro" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="监理工程师数量：" prop="personalInfo.supEngineerNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.supEngineerNum" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="工程建设类职业资格人员数量：" prop="personalInfo.projectQuaNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.projectQuaNum" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="一级造价师数量：" prop="personalInfo.oneCostPersonnelNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.oneCostPersonnelNum" disabled></el-input>
                    </el-form-item>
                  </el-col>
                </el-collapse-item>
                <el-collapse-item title="扩展信息" name="3" class="contitlebox">
                  <el-col :span="12">
                    <el-form-item  label="分公司数量：" prop="enterpriseExpand.filialeNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.filialeNum" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="办事处数量：" prop="enterpriseExpand.officeNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.officeNum" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="办公设备信息网络：" prop="enterpriseExpand.workEquipmentNet" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.workEquipmentNet" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="企业近三年的财务状况：" prop="enterpriseExpand.financialCondition" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.financialCondition" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="机构名称变化情况：" prop="enterpriseExpand.organNameChange" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.organNameChange" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="基本户开户支行号：" prop="enterpriseExpand.accountOpenBranch" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.accountOpenBranch" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="基本户开户银行：" prop="enterpriseExpand.depositBank" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.depositBank" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="基本户账户名称：" prop="enterpriseExpand.accountName" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.accountName" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="基本户账户：" prop="enterpriseExpand.depositAccount" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.depositAccount" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="资质证书编号：" prop="enterpriseExpand.aptitudeCreNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.aptitudeCreNum" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="资质名称:" prop="enterpriseExpand.aptitudeName" :label-width="formLabelWidth">
                      <el-select v-model="inForm.enterpriseExpand.aptitudeName" placeholder="请选择">
                        <el-option
                          v-for="item in AptitudeValue"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value" disabled>
                        </el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="资质证书失效日期：" prop="enterpriseExpand.aptitudeCreLoseDate" :label-width="formLabelWidth">
                      <el-date-picker
                        v-model="inForm.enterpriseExpand.aptitudeCreLoseDate"
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="选择日期时间" disabled>
                      </el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="资质等级：" prop="enterpriseExpand.aptitudeGrade" :label-width="formLabelWidth">
                      <el-select v-model="inForm.enterpriseExpand.aptitudeGrade" placeholder="请选择">
                        <el-option
                          v-for="item in gradeValue"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value" disabled>
                        </el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="审批日期：" prop="enterpriseExpand.approveDate" :label-width="formLabelWidth">
                      <el-date-picker
                        v-model="inForm.enterpriseExpand.approveDate"
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="选择日期时间" disabled>
                      </el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="资质序列：" prop="enterpriseExpand.aptitudeSequence" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.aptitudeSequence" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="证书附件：" class="bitianicon" :label-width="formLabelWidth">
                      <el-button type="success" size="mini" @click="handleEdit(certificateFileName,certificateFileUrl)">下载查看</el-button>
                    </el-form-item>
                  </el-col>
                  <div style="width: 100%;display:flow-root" v-if="inForm.enterpriseExpand.isDutyLandTax === 0">
                    <el-col :span="12">
                      <el-form-item  label="国税税务登记证失效日期：" prop="enterpriseExpand.nationalTaxLoseDate" :label-width="formLabelWidth">
                        <el-date-picker
                          v-model="inForm.enterpriseExpand.nationalTaxLoseDate"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="选择日期时间" disabled>
                        </el-date-picker>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item  label="国税税务登记附件：" :label-width="formLabelWidth">
                        <el-button type="success" size="mini" @click="handleEdit(irsTaxFileName,irsTaxFileUrl)">下载查看</el-button>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item  label="国税税务登记证证号：" prop="enterpriseExpand.nationalTaxNum" :label-width="formLabelWidth">
                        <el-input  v-model="inForm.enterpriseExpand.nationalTaxNum" disabled></el-input>
                      </el-form-item>
                    </el-col>
                  </div>
                  <div style="width: 100%;display:flow-root" v-if="inForm.enterpriseExpand.isDutyLandTax === 1">
                    <el-col :span="12">
                      <el-form-item  label="地税税务登记证失效日期：" prop="enterpriseExpand.goverTaxLoseDate" :label-width="formLabelWidth">
                        <el-date-picker
                          v-model="inForm.enterpriseExpand.goverTaxLoseDate"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="选择日期时间" disabled>
                        </el-date-picker>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item  label="地税税务登记证附件：" :label-width="formLabelWidth">
                        <el-button type="success" size="mini" @click="handleEdit(localTaxFileName,localTaxFileUrl)">下载查看</el-button>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item  label="地税税务登记证证号：" prop="enterpriseExpand.goverTaxNum" :label-width="formLabelWidth">
                        <el-input  v-model="inForm.enterpriseExpand.goverTaxNum" disabled></el-input>
                      </el-form-item>
                    </el-col>
                  </div>
                </el-collapse-item>
                <el-collapse-item title="联系方式" name="4" class="contitlebox">
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="联系人：" prop="contactName" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.contactName" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="联系地址：" :label-width="formLabelWidth" prop="contactCelladdress">
                      <el-input  v-model="inForm.contactCelladdress" disabled v-bind:title="inForm.contactCelladdress"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="联系人电话：" prop="contactCellphone" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.contactCellphone" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="电子邮箱：" prop="contactCellemail"  :label-width="formLabelWidth">
                      <el-input  v-model="inForm.contactCellemail" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="单位网站：" :label-width="formLabelWidth" prop="unitPortal">
                      <el-input  v-model="inForm.unitPortal" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="法人代表联系电话：" prop="businessLicenseCellphone"  :label-width="formLabelWidth">
                      <el-input  v-model="inForm.businessLicenseCellphone" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon" label="资信等级：" prop="creditGrade" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.creditGrade" disabled></el-input>
                    </el-form-item>
                  </el-col>
                </el-collapse-item>
              </el-form>
            </el-row>
          </el-collapse>
        </div>
        <!--内容开始-->
      </div>
    </div>
  </div>
</template>
<script>
import publicside from '../adminpublic/side.vue'
import * as region from '../../assets/js/region'
import * as industry from '../../assets/js/industry'
import {enterprise} from '../../api/index'
import {downloadFile} from '../../assets/js/common'
export default {
  components: {
    publicside
  },
  data () {
    return {
      // 默认展开
      activeName: '1',
      // 提交数据时的表单
      inForm: {
        enterpriseName: '',
        legalPersonName: '',
        orgCode: '',
        industry: '',
        industrySecond: '',
        industryThird: '',
        registAddress: '',
        detailAddress: '',
        legalPersonCardNum: '',
        registeredCapital: '',
        businessLicenseOffice: '',
        businessLicenseExpiry: '',
        legalPersonCategory: '',
        contactName: '',
        contactCellphone: '',
        contactCelladdress: '',
        contactCellemail: '',
        businessLicenseCellphone: '',
        unitPortal: '',
        creditGrade: '',
        isApprove: '',
        auditPersonName: '',
        approveReason: '',
        orgCodeFileName: '',
        orgCodeFileUrl: '',
        idCardFileName: '',
        idCardFileUrl: '',
        businessLicenseFileName: '',
        businessLicenseFileUrl: '',
        lastUpdateDate: '',
        enterpriseExpand: {
          filialeNum: '',
          officeNum: '',
          workEquipmentNet: '',
          financialCondition: '',
          organNameChange: '',
          nationalTaxNum: '',
          nationalTaxLoseDate: '',
          goverTaxNum: '',
          goverTaxLoseDate: '',
          depositBank: '',
          accountOpenBranch: '',
          depositAccount: '',
          accountName: '',
          aptitudeName: '',
          aptitudeGrade: '',
          aptitudeSequence: '',
          aptitudeCreNum: '',
          aptitudeCreLoseDate: '',
          approveDate: '',
          isDutyLandTax: 0
        },
        personalInfo: {
          allPersonNum: '',
          allTopTitleNum: '',
          allMiddleTitleNum: '',
          allTitleQuaNum: '',
          allMasterNum: '',
          allBidNum: '',
          bidMiddleUpNum: '',
          bidTopTitleNum: '',
          bidMiddleNum: '',
          bidMiddleTitlePro: '',
          tendererNum: '',
          tendererPro: '',
          projectQuaNum: '',
          costEngineerNum: '',
          supEngineerNum: '',
          oneCostPersonnelNum: '',
          otherQuaNum: '',
          otherQuaRem: ''
        }
      },
      formLabelWidth: '240px',
      // 行业联级选择
      industryOptions: industry.industry,
      selectedOptions: [],
      AptitudeValue: [
        {
          value: '中央投资项目招标代理',
          label: '中央投资项目招标代理'
        }, {
          value: '机电产品国际招标代理',
          label: '机电产品国际招标代理'
        }, {
          value: '工程咨询资格专',
          label: '工程咨询资格专'
        }, {
          value: '工程监理资格专业',
          label: '工程监理资格专业'
        }, {
          value: '政府采购代理',
          label: '政府采购代理'
        }, {
          value: '工程建设项目施工招标代理',
          label: '工程建设项目施工招标代理'
        }
      ],
      gradeValue: [
        {
          value: '特级',
          label: '特级'
        }, {
          value: '一级',
          label: '一级'
        }, {
          value: '二级',
          label: '二级'
        }, {
          value: '三级',
          label: '三级'
        }, {
          value: '四级',
          label: '四级'
        }, {
          value: '甲级',
          label: '甲级'
        }, {
          value: '乙级',
          label: '乙级'
        }, {
          value: '丙级',
          label: '丙级'
        }, {
          value: '丁级',
          label: '丁级'
        }, {
          value: '暂定级',
          label: '暂定级'
        }, {
          value: '初级',
          label: '初级'
        }, {
          value: '中级',
          label: '中级'
        }, {
          value: '高级',
          label: '高级'
        }],
      // 三级联动
      addressOptions: region.CityInfo,
      selectedcityAddress: [],
      // 页面展示状态
      LookStatus: true,
      // 人员信息状态
      flag: false,
      irsTaxFileName: '',
      irsTaxFileUrl: '',
      localTaxFileName: '',
      localTaxFileUrl: '',
      certificateFileName: '',
      certificateFileUrl: ''
    }
  },
  methods: {
    handleEdit (name, path) {
      downloadFile(name, path)
    },
    enterpriseData () {
      enterprise.enterpriseData(this.$store.getters.authUser.userId).then(item => {
        this.selectedOptions.push(item.data.enterprise.industry)
        this.selectedOptions.push(item.data.enterprise.industrySecond)
        this.selectedOptions.push(item.data.enterprise.industryThird)
        this.selectedcityAddress.push(item.data.enterprise.privinceId)
        this.selectedcityAddress.push(item.data.enterprise.cityId)
        this.selectedcityAddress.push(item.data.enterprise.countyId)
        this.inForm.idCardFileName = item.data.enterprise.idCardFileName === null ? 'idCard' + Date.now() + '.jpg' : item.data.enterprise.idCardFileName
        this.inForm.businessLicenseFileName = item.data.enterprise.businessLicenseFileName === null ? 'business' + Date.now() + '.jpg' : item.data.enterprise.businessLicenseFileName
        this.inForm = item.data.enterprise
        this.flag = item.data.enterprise.flag
        item.data.enterprise.enterpriseExpand.fileInformations.map(ite => {
          let num = parseInt(ite.businessType)
          switch (num) {
            case 1:
              this.irsTaxFileName = ite.fileName
              this.irsTaxFileUrl = ite.relativePath
              break
            case 2:
              this.localTaxFileName = ite.fileName
              this.localTaxFileUrl = ite.relativePath
              break
            case 3:
              this.certificateFileName = ite.fileName
              this.certificateFileUrl = ite.relativePath
              break
          }
        })
      })
    }
  },
  mounted () {
    if (this.$route.params.LookStatus == false) {
      this.LookStatus = false
    }
    this.enterpriseData()
  }
}
</script>
<style lang="less">
  #homeautheninf{
    .Authentication_statebigbox{
      width: 100%;
      min-height: 45px;
      padding: 40px 0;
    }
    .Authentication_statebox{
      width: 350px;
      margin: 0 auto;
    }
    .iconimgstatus{
      width: 45px;
      height: 45px;
      display: inline-block;
      vertical-align: top;
      position: relative;
    }
    .iconimgstatus>img{
      width: 35px;
      position: absolute;
      left: 0;
      right: 0;
      top:0;
      bottom: 0;
      margin: auto;
    }
    .Authenxinxibox{
      width: 295px;
      display: inline-block;
      vertical-align: top;
    }
    .Authenxinxitop{
      width: 100%;
      height:25px;
      font-size: 16px;
      line-height: 25px;
      font-weight: 700;
    }
    .Authenxinxibottom{
      width: 100%;
      height:20px;
      font-size: 12px;
      color:#bbbbbb;
      margin-top: 3px;
    }
    .Authenbottomson{
      width: 40%;
      height: 100%;
      line-height: 20px;
      float: left;
      overflow: hidden;
      text-overflow:ellipsis;
      white-space: nowrap;
    }
    .Authenbottomsontwo{
      width: 60%;
      height: 100%;
      line-height: 20px;
      float: left;
      overflow: hidden;
      text-overflow:ellipsis;
      white-space: nowrap;
    }
    .contitlebox .el-collapse-item__header{
      font-size: 16px;
    }
    .caozuo_btnbox{
      text-align: center;
      margin-top: 100px;
    }
    .el-button{
      border-radius: 0;
    }
    .el-button--info {
      color: #666666;
      background-color: #ededed;
      border-color: #ededed;
      font-size: 14px;
    }
    .el-button--primary {
      color: #fff;
      background-color: #3f63f6;
      border-color: #3f63f6;
      font-size: 14px;
    }
    .demo-ruleForm{
      width: 100%;
    }
    .el-collapse-item__wrap {
      will-change: height;
      overflow: hidden;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      border-bottom: 1px solid #ebeef5;
      margin-top:20px;
    }
    .el-collapse-item__content {
      padding-bottom: 25px;
      font-size: 13px;
      color: #303133;
      overflow: hidden;
    }
    .el-date-editor{
      width: 100%;
    }
    .el-upload {
      display: inline-block;
      text-align: center;
      cursor: pointer;
      outline: 0;
      float: right;
    }
    .el-upload-list {
      width: 100%;
      height: 40px;
      margin: 0;
      padding: 0;
      line-height: 40px;
      list-style: none;
    }
    .el-upload-list__item:first-child {
      margin-top: 0px;
      vertical-align: middle;
      display: inline-block;
    }
    .upload-demo {
      border: 1px solid #dcdfe6;
      padding: 0px 10px;
      border-radius: 5px;
      overflow: hidden;
    }
    .uploadbtn{
      font-size: 12px;
      background-color: #ff8801;
      border-color: #ff8801;
    }
    .el-select {
      display: inline-block;
      position: relative;
      width: 100%;
    }
    .el-button--small{
      padding: 5px 6px;
    }
    .zcdz{
      width: 100%;
    }
    .bitianicon .el-form-item__label::before{
      content:"*";
      color:#f66c6c;
      margin-right: 5px;
    }
    .el-input.is-disabled .el-input__inner {
      background-color: #f5f7fa;
      border-color: #e4e7ed;
      color: #c0c4cc;
      cursor: not-allowed;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .chongxin_btn{
      width: 80px;
      height: 25px;
      border: 1px solid #3f63f6;
      color:#3f63f6;
      text-align: center;
      line-height: 25px;
      font-size: 12px;
      cursor: pointer;
      margin-top:15px;
    }
    .el-form-item__label {
      width: 240px!important;
    }
    .el-form-item__content {
      margin-left: 240px!important;
      width: auto;
    }
    .kema {
      line-height: 50px;
    }
  }
</style>
