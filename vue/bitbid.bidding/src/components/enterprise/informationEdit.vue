<template>
  <div id="homeinformsss">
    <div class="homepagecontent">
      <publicside></publicside>
      <div class="homepageconright">
        <!--面包屑-->
        <el-breadcrumb class="breadcrumbbox" separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/authentication' }">企业认证</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/subject', query: {subjectForm: this.$route.query.SelectFrom, flangshow: true, approveState: this.$route.query.approveState}}">主体类型</el-breadcrumb-item>
          <el-breadcrumb-item>重新认证</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
        <!--内容开始-->
        <div class="penserviceconbox">
          <el-collapse v-model="activeName" accordion>
            <el-row type="flex" class="row-bg">
              <el-form :model="inForm" ref="inForm" :rules="rules" class="demo-ruleForm">
                <el-collapse-item title="基本信息" name="1" class="contitlebox">
                  <el-row>
                    <el-col :span="12">
                      <el-form-item  label="企业名称：" prop="enterpriseName" :label-width="formLabelWidth">
                        <el-input  v-model="inForm.enterpriseName" disabled></el-input>
                        <el-input  v-model="inForm.objectId" type="hidden"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item  label="法定代表人姓名：" prop="legalPersonName" :label-width="formLabelWidth">
                        <el-input  v-model="inForm.legalPersonName" disabled></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="12">
                      <el-form-item  label="所属行业：" class="bitianicon" prop="industry" :label-width="formLabelWidth">
                        <el-cascader class=""
                                     expand-trigger="hover"
                                     :options="industryOptions"
                                     v-model="selectedOptions"
                                     disabled>
                        </el-cascader>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item  label="组织机构代码：" prop="orgCode" :label-width="formLabelWidth">
                        <el-input  v-model="inForm.orgCode" disabled></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="12">
                      <el-form-item  label="注册地址：" class="bitianicon" prop="privinceId" :label-width="formLabelWidth">
                        <el-cascader class=""
                                     expand-trigger="hover"
                                     :options="addressOptions"
                                     v-model="selectedcityAddress"
                                     disabled>
                        </el-cascader>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item  label="详细地址：" prop="detailAddress" :label-width="formLabelWidth">
                        <el-input v-model="inForm.detailAddress" disabled></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="12">
                      <el-form-item  label="身份证号码：" prop="legalPersonCardNum" :label-width="formLabelWidth">
                        <el-input  v-model="inForm.legalPersonCardNum" disabled></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="注册资本（万元）:" prop="registeredCapital" :label-width="formLabelWidth">
                        <el-input  v-model="inForm.registeredCapital"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="12">
                      <el-form-item class="bitianicon"   label="加盖公章的身份证复印件：" :label-width="formLabelWidth">
                        <el-input type="hidden" v-model="inForm.idCardFileName"></el-input>
                        <el-input type="hidden" v-model="inForm.idCardFileUrl"></el-input>
                        <el-button v-if="inForm.idCardFileUrl" type="success" size="mini" @click="handleEdit(inForm.idCardFileName,inForm.idCardFileUrl)">下载查看</el-button>
                        <el-button v-else type="success" size="mini" @click="skipBibenet">完善信息</el-button>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item class="bitianicon"  label="营业执照附件：" :label-width="formLabelWidth">
                        <el-input type="hidden" v-model="inForm.businessLicenseFileName"></el-input>
                        <el-input type="hidden" v-model="inForm.businessLicenseFileUrl"></el-input>
                        <el-button v-if="inForm.businessLicenseFileUrl" type="success" size="mini" @click="handleEdit(inForm.businessLicenseFileName,inForm.businessLicenseFileUrl)">下载查看</el-button>
                        <el-button v-else type="success" size="mini" @click="skipBibenet">完善信息</el-button>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12" style="color: #f66c6c">
                      *注：以上信息如有不符，请到比比招标网修改<el-button type="text" size="small" @click="skipBibenet" class="updateBtn">去修改</el-button>
                    </el-col>
                  </el-row>
                </el-collapse-item>
                <el-collapse-item title="人员信息" name="2" class="contitlebox" v-if="flagsShow">
                  <el-col :span="12">
                    <el-form-item  label="全部人员数目：" prop="personalInfo.allPersonNum" :label-width="formLabelWidth">
                      <el-input v-model="inForm.personalInfo.allPersonNum"></el-input>
                      <el-input v-model="inForm.personalInfo.objectId" type="hidden"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="全部硕士以上学历数目：" prop="personalInfo.allMasterNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.allMasterNum"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="全部高级职称数目：" prop="personalInfo.allTopTitleNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.allTopTitleNum"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="招标从业人员数目：" prop="personalInfo.allBidNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.allBidNum"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="全部中级职称数目：" prop="personalInfo.allMiddleTitleNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.allMiddleTitleNum"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="招标从业人员中级及以上职数目：" prop="personalInfo.bidMiddleUpNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.bidMiddleUpNum"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="招标从业人员高级职称数目：" prop="personalInfo.bidTopTitleNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.bidTopTitleNum"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="招标从业人员中级职称数目：" prop="personalInfo.bidMiddleNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.bidMiddleNum"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="招标从业人员中级及以上职占比：" prop="personalInfo.bidMiddleTitlePro" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.bidMiddleTitlePro"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="其他职业资格人员数量：" prop="personalInfo.otherQuaNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.otherQuaNum"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="全部具备职业资格数目：" prop="personalInfo.allTitleQuaNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.allTitleQuaNum"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="其他职业资格人员备注：" prop="personalInfo.otherQuaRem" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.otherQuaRem"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="已登记招标师数量：" prop="personalInfo.tendererNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.tendererNum"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="造价工程师数量：" prop="personalInfo.costEngineerNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.costEngineerNum"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="已登记招标师占比：" prop="personalInfo.tendererPro" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.tendererPro"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="监理工程师数量：" prop="personalInfo.supEngineerNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.supEngineerNum"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="工程建设类职业资格人员数量：" prop="personalInfo.projectQuaNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.projectQuaNum"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="一级造价师数量：" prop="personalInfo.oneCostPersonnelNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.personalInfo.oneCostPersonnelNum"></el-input>
                    </el-form-item>
                  </el-col>
                </el-collapse-item>
                <el-collapse-item title="扩展信息" name="3" class="contitlebox">
                  <el-col :span="12">
                    <el-form-item  label="分公司数量：" prop="enterpriseExpand.filialeNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.filialeNum"></el-input>
                      <el-input  v-model="inForm.enterpriseExpand.objectId" type="hidden"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="办事处数量：" prop="enterpriseExpand.officeNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.officeNum"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="办公设备信息网络：" prop="enterpriseExpand.workEquipmentNet" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.workEquipmentNet"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="企业近三年的财务状况：" prop="enterpriseExpand.financialCondition" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.financialCondition"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="机构名称变化情况：" prop="enterpriseExpand.organNameChange" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.organNameChange"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="基本户开户支行号：" prop="enterpriseExpand.accountOpenBranch" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.accountOpenBranch"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="基本户开户银行：" prop="enterpriseExpand.depositBank" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.depositBank"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="基本户账户名称：" prop="enterpriseExpand.accountName" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.accountName"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="基本户账户：" prop="enterpriseExpand.depositAccount" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.depositAccount"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="资质证书编号：" prop="enterpriseExpand.aptitudeCreNum" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.aptitudeCreNum"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="资质名称:" prop="enterpriseExpand.aptitudeName" :label-width="formLabelWidth">
                      <el-select v-model="inForm.enterpriseExpand.aptitudeName" clearable placeholder="请选择">
                        <el-option
                          v-for="item in AptitudeValue"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
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
                        :picker-options="pickerOptions"
                        placeholder="选择日期时间">
                      </el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="资质等级：" prop="enterpriseExpand.aptitudeGrade" :label-width="formLabelWidth">
                      <el-select v-model="inForm.enterpriseExpand.aptitudeGrade" clearable placeholder="请选择">
                        <el-option
                          v-for="item in gradeValue"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
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
                        :picker-options="pickerOptions"
                        placeholder="选择日期时间">
                      </el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="资质序列：" prop="enterpriseExpand.aptitudeSequence" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseExpand.aptitudeSequence"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="证书附件：" class="bitianicon" :label-width="formLabelWidth">
                      <el-button type="success" size="mini" v-if="localTaxFlag" @click="handleEdit(localTaxFileName,localTaxFileUrl)">下载查看</el-button>
                      <el-button type="success" size="mini" v-if="localTaxFlag" @click="removeLocalTax">删除文件</el-button>
                      <el-upload
                        v-if="localTaxFlagElse"
                        class="upload-demo"
                        ref="upload"
                        :before-upload='before'
                        :before-remove="beforeRemove"
                        :multiple="false"
                        :limit="1"
                        :action="upLoad"
                        :on-remove="handleRemove"
                        :auto-upload="true"
                        :on-success='certificateSuccess'
                        :on-exceed="handleExceed">
                        <el-button  size="small" class="uploadbtn" type="primary">点击上传</el-button>
                      </el-upload>
                    </el-form-item>
                  </el-col>
                  <el-col :span="24">
                    <el-form-item  label="国税/地税：" class="bitianicon" :label-width="formLabelWidth">
                      <el-select v-model="inForm.enterpriseExpand.isDutyLandTax" placeholder="请选择" class="nationalTaxbox">
                        <el-option
                          v-for="item in nationalTaxSelect"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                        </el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <div style="width: 100%;display:flow-root" v-if="inForm.enterpriseExpand.isDutyLandTax === 0">
                    <el-col :span="12">
                      <el-form-item  label="国税税务登记证失效日期：" prop="enterpriseExpand.nationalTaxLoseDate" :label-width="formLabelWidth">
                        <el-date-picker
                          v-model="inForm.enterpriseExpand.nationalTaxLoseDate"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          :picker-options="pickerOptions"
                          placeholder="选择日期时间">
                        </el-date-picker>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item  label="国税税务登记附件：" :label-width="formLabelWidth">
                        <el-button type="success" size="mini" v-if="irsTaxFlag" @click="handleEdit(irsTaxFileName,irsTaxFileUrl)">下载查看</el-button>
                        <el-button type="success" size="mini" v-if="irsTaxFlag" @click="removeIrsTax">删除文件</el-button>
                        <el-upload
                          v-if="irsTaxFlagElse"
                          class="upload-demo"
                          ref="upload"
                          :before-upload='before'
                          :before-remove="beforeRemove"
                          :multiple="false"
                          :limit="1"
                          :action="upLoad"
                          :on-remove="handleRemove"
                          :auto-upload="true"
                          :on-success='irsTaxSuccess'
                          :on-exceed="handleExceed">
                          <el-button  size="small" class="uploadbtn" type="primary">点击上传</el-button>
                        </el-upload>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item  label="国税税务登记证证号：" prop="enterpriseExpand.nationalTaxNum" :label-width="formLabelWidth">
                        <el-input  v-model="inForm.enterpriseExpand.nationalTaxNum"></el-input>
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
                          :picker-options="pickerOptions"
                          placeholder="选择日期时间">
                        </el-date-picker>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item  label="地税税务登记证附件：" :label-width="formLabelWidth">
                        <el-button type="success" size="mini" v-if="certificateFlag" @click="handleEdit(certificateFileName,certificateFileUrl)">下载查看</el-button>
                        <el-button type="success" size="mini" v-if="certificateFlag" @click="removeCertificate">删除文件</el-button>
                        <el-upload
                          v-if="certificateFlagElse"
                          class="upload-demo"
                          ref="upload"
                          :before-upload='before'
                          :before-remove="beforeRemove"
                          :multiple="false"
                          :limit="1"
                          :action="upLoad"
                          :on-remove="handleRemove"
                          :auto-upload="true"
                          :on-success='localTaxSuccess'
                          :on-exceed="handleExceed">
                          <el-button  size="small" class="uploadbtn" type="primary">点击上传</el-button>
                        </el-upload>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item  label="地税税务登记证证号：" prop="enterpriseExpand.goverTaxNum" :label-width="formLabelWidth">
                        <el-input  v-model="inForm.enterpriseExpand.goverTaxNum"></el-input>
                      </el-form-item>
                    </el-col>
                  </div>
                </el-collapse-item>
                <el-collapse-item title="联系方式" name="4" class="contitlebox">
                  <el-col :span="12">
                    <el-form-item  label="联系人：" prop="contactName" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.contactName"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="联系地址：" prop="contactCelladdress" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.contactCelladdress"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="联系人电话：" prop="contactCellphone" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.contactCellphone"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="电子邮箱：" prop="contactCellemail"  :label-width="formLabelWidth">
                      <el-input  v-model="inForm.contactCellemail"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="单位网站：" prop="unitPortal" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.unitPortal"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="法人代表联系电话：" prop="businessLicenseCellphone"  :label-width="formLabelWidth">
                      <el-input  v-model="inForm.businessLicenseCellphone"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="资信等级：" prop="creditGrade" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.creditGrade"></el-input>
                    </el-form-item>
                  </el-col>
                </el-collapse-item>
              </el-form>
            </el-row>
          </el-collapse>
          <div class="caozuo_btnbox">
            <el-button type="info" @click="cancelBtn()">上一步</el-button>
            <el-button type="primary" @click="AuthenticationBtn('inForm')">立即认证</el-button>
          </div>
        </div>
        <!--内容开始-->
        <el-dialog
          title="提示"
          :visible.sync="dialogVisible"
          width="30%">
          <span>{{alertMessage}}</span>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="skipBibenet">去完善</el-button>
          </span>
        </el-dialog>
      </div>
    </div>
  </div>
</template>
<script>
import publicside from '../adminpublic/side.vue'
import {isvalidPhone, Numbertype, Decimal, links} from '../../assets/js/validate'
import * as region from '../../assets/js/region'
import * as industry from '../../assets/js/industry'
import {enterprise} from '../../api/index'
import {loadUrl, fileSize, downloadFile} from '../../assets/js/common'
export default {
  components: {
    publicside
  },
  data () {
    // 电话验证
    var validPhone = (rule, value, callback) => {
      if (!value && value !== null) {
        callback(new Error('请输入电话号码'))
      } else if (!isvalidPhone(value) && value !== null) {
        callback(new Error('请输入正确的11位手机号码'))
      } else {
        callback()
      }
    }
    var SelectionPhone = (rule, value, callback) => {
      if (value === '' || value === null) {
        callback()
      } else if (!isvalidPhone(value)) {
        callback(new Error('请输入正确的11位手机号码'))
      } else {
        callback()
      }
    }
    // 数字
    var Numbertypes = (rule, value, callback) => {
      if (value === '' || value === null) {
        callback()
      } else if (!Numbertype(value)) {
        callback(new Error('请输入数字'))
      } else {
        callback()
      }
    }
    // 网址验证
    var validUrl = (rule, value, callback) => {
      if (value === '' || value === undefined || value === null) {
        callback(new Error('不能为空'))
      } else if (!links(value)) {
        callback(new Error('请输入正确的格式'))
      } else {
        callback()
      }
    }
    // 百分比
    var Decimaltypes = (rule, value, callback) => {
      if (!value) {
        callback(new Error('不能为空'))
      } else if (!Decimal(value)) {
        callback(new Error('请输入正确的格式'))
      } else {
        callback()
      }
    }
    return {
      // 默认展开
      activeName: '1',
      // 提交数据时的表单
      inForm: {
        personalInfo: {
          objectId: '',
          code: '',
          allPersonNum: '',
          allMasterNum: '',
          allTopTitleNum: '',
          allMiddleTitleNum: '',
          allBidNum: '',
          bidMiddleUpNum: '',
          bidTopTitleNum: '',
          bidMiddleNum: '',
          bidMiddleTitlePro: '',
          otherQuaNum: '',
          allTitleQuaNum: '',
          otherQuaRem: '',
          tendererNum: '',
          costEngineerNum: '',
          tendererPro: '',
          supEngineerNum: '',
          projectQuaNum: '',
          oneCostPersonnelNum: '',
          enterpriseId: ''
        },
        enterpriseExpand: {
          objectId: '',
          code: '',
          filialeNum: '',
          officeNum: '',
          workEquipmentNet: '',
          financialCondition: '',
          organNameChange: '',
          nationalTaxLoseDate: '',
          nationalTaxNum: '',
          goverTaxNum: '',
          goverTaxLoseDate: '',
          accountOpenBranch: '',
          depositBank: '',
          accountName: '',
          depositAccount: '',
          aptitudeCreNum: '',
          aptitudeName: '',
          aptitudeCreLoseDate: '',
          aptitudeGrade: '',
          approveDate: '',
          aptitudeSequence: '',
          enterpriseId: '',
          isDutyLandTax: 0,
          fileInformations: []
        },
        objectId: '',
        code: '',
        contactName: '',
        contactCelladdress: '',
        contactCellphone: '',
        contactCellemail: '',
        unitPortal: '',
        businessLicenseCellphone: '',
        creditGrade: '',
        IntermediateNumberzjproportion: '',
        enterpriseName: '',
        legalPersonName: '',
        orgCode: '',
        industry: '',
        detailAddress: '',
        legalPersonCardNum: '',
        businessLicenseOffice: '',
        businessLicenseExpiry: '',
        legalPersonCategory: '',
        registeredCapital: '',
        orgCodeFileName: '',
        orgCodeFileUrl: '',
        idCardFileName: '',
        idCardFileUrl: '',
        businessLicenseFileName: '',
        businessLicenseFileUrl: '',
        enterpriseId: '',
        enterpriseAndSubjects: [],
        flag: false
      },
      // 上传文件地址
      upLoad: `${loadUrl}file/upload`,
      // 国税附件下载标记
      irsTaxFlag: true,
      // 国税附件上传标记
      irsTaxFlagElse: false,
      // 地税附件下载标记
      certificateFlag: true,
      // 地税附件上传标记
      certificateFlagElse: false,
      // 其他附件下载标记
      localTaxFlag: true,
      // 其他附件上传标记
      localTaxFlagElse: false,
      formLabelWidth: '240px',
      // 行业联级选择
      industryOptions: industry.industry,
      selectedOptions: [],
      // 提示信息弹框标记
      dialogVisible: false,
      alertMessage: '',
      // 日期选择
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7
        }
      },
      // 国税地税选择
      nationalTaxSelect: [
        {
          value: 0,
          label: '国税'
        }, {
          value: 1,
          label: '地税'
        }
      ],
      // 三级联动
      addressOptions: region.CityInfo,
      selectedcityAddress: [],
      // 注册资本
      registeredOptions: [{
        value: 'zhinan',
        label: '指南',
        children: [{
          value: 'shejiyuanze',
          label: '设计原则',
          children: [{
            value: 'yizhi',
            label: '一致'
          }, {
            value: 'fankui',
            label: '反馈'
          }, {
            value: 'xiaolv',
            label: '效率'
          }, {
            value: 'kekong',
            label: '可控'
          }]
        }]
      }],
      selecteregistered: [],
      IndustrySelect: [
        {
          value: '普通行业',
          label: '普通行业'
        }],
      // 资质名称数据
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
      // 法人机构类别
      mechanismTypeValue: [
        {
          value: '企业',
          label: '企业'
        }, {
          value: '机关法人',
          label: '机关法人'
        }, {
          value: '事业单位',
          label: '事业单位'
        }, {
          value: '社会组织',
          label: '社会组织'
        }, {
          value: '其他',
          label: '其他'
        }],
      // 人员信息状态
      flagsShow: false,
      irsTaxFileName: '',
      irsTaxFileUrl: '',
      localTaxFileName: '',
      localTaxFileUrl: '',
      certificateFileName: '',
      certificateFileUrl: '',
      submitCount: 0,
      // 提交时验证
      rules: {
        'personalInfo.allPersonNum': [
          { required: true, message: '全部人员数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        'personalInfo.allMasterNum': [
          { required: true, message: '全部硕士以上学历数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        'personalInfo.allTopTitleNum': [
          { required: true, message: '全部高级职称数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        'personalInfo.allBidNum': [
          { required: true, message: '招标从业人员数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        'personalInfo.allMiddleTitleNum': [
          { required: true, message: '全部中级职称数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        'personalInfo.bidMiddleUpNum': [
          { required: true, message: '招标从业人员中级及以上职数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        'personalInfo.bidTopTitleNum': [
          { required: true, message: '招标从业人员高级职称数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        'personalInfo.bidMiddleNum': [
          { required: true, message: '招标从业人员中级职称数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        'personalInfo.bidMiddleTitlePro': [
          { required: true, message: '招标从业人员中级及以上职占比不能为空', trigger: 'blur' },
          { min: 4, max: 6, message: '小数位最长为4位，长度在 4~6个字符', trigger: ['blur', 'change'] },
          { validator: Decimaltypes, trigger: ['blur', 'change'] }
        ],
        'personalInfo.otherQuaNum': [
          { required: true, message: '其他职业资格人员数量不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        'personalInfo.allTitleQuaNum': [
          { required: true, message: '全部具备职业资格数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        'personalInfo.otherQuaRem': [
          { required: true, message: '其他职业资格人员备注不能为空', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在 1~200个字符', trigger: ['blur', 'change'] }
        ],
        'personalInfo.tendererNum': [
          { required: true, message: '已登记招标师数量不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        'personalInfo.costEngineerNum': [
          { required: true, message: '造价工程师数量不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        'personalInfo.tendererPro': [
          { required: true, message: '已登记招标师占比不能为空', trigger: 'blur' },
          { min: 4, max: 6, message: '小数位最长为4位，长度在 4~6个字符', trigger: ['blur', 'change'] },
          { validator: Decimaltypes, trigger: ['blur', 'change'] }
        ],
        'personalInfo.supEngineerNum': [
          { required: true, message: '监理工程师数量不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        'personalInfo.projectQuaNum': [
          { required: true, message: '工程建设类职业资格人员数量不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        'personalInfo.oneCostPersonnelNum': [
          { required: true, message: '一级造价师数量不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        'enterpriseExpand.filialeNum': [
          { validator: Numbertypes, trigger: ['blur', 'change'] },
          { min: 1, max: 5, message: '长度在 1~5个字符', trigger: ['blur', 'change'] }
        ],
        'enterpriseExpand.officeNum': [
          { validator: Numbertypes, trigger: ['blur', 'change'] },
          { min: 1, max: 5, message: '长度在 1~5个字符', trigger: ['blur', 'change'] }
        ],
        'enterpriseExpand.aptitudeCreNum': [
          { required: true, message: '资质证书编号不能为空', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1~50个字符', trigger: ['blur', 'change'] }
        ],
        'enterpriseExpand.aptitudeName': [
          { required: true, message: '资质名称不能为空', trigger: ['blur', 'change'] }
        ],
        'enterpriseExpand.aptitudeCreLoseDate': [
          { required: true, message: '资质证书失效日期不能为空', trigger: 'blur' }
        ],
        'enterpriseExpand.aptitudeGrade': [
          { required: true, message: '资质等级不能为空', trigger: ['blur', 'change'] }
        ],
        'enterpriseExpand.approveDate': [
          { required: true, message: '审批日期不能为空', trigger: 'blur' }
        ],
        'enterpriseExpand.aptitudeSequence': [
          { required: true, message: '资质序列不能为空', trigger: 'blur' },
          { min: 1, max: 6, message: '长度在 1~6个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        'enterpriseExpand.nationalTaxLoseDate': [
          { required: true, message: '国税税务登记证失效日期不能为空', trigger: 'blur' }
        ],
        'enterpriseExpand.nationalTaxNum': [
          { required: true, message: '国税税务登记证证号不能为空', trigger: 'blur' },
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: ['blur', 'change'] }
        ],
        'enterpriseExpand.goverTaxLoseDate': [
          { required: true, message: '地税税务登记证失效日期不能为空', trigger: 'blur' }
        ],
        'enterpriseExpand.goverTaxNum': [
          { required: true, message: '地税税务登记证证号不能为空', trigger: 'blur' },
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: ['blur', 'change'] }
        ],
        contactCellemail: [
          { required: false, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ],
        industry: [
          { required: true, message: '所属行业不能为空', trigger: 'blur' }
        ],
        privinceId: [
          { required: true, message: '注册地址不能为空', trigger: 'blur' }
        ],
        contactName: [
          { required: true, message: '联系人不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] }
        ],
        contactCellphone: [
          { required: true, message: '联系人电话不能为空', trigger: 'blur' },
          {required: true, validator: validPhone, trigger: ['blur', 'change']}
        ],
        businessLicenseCellphone: [
          {required: false, validator: SelectionPhone, trigger: ['blur', 'change']}
        ],
        creditGrade: [
          { required: true, message: '资信等级不能为空', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1~50个字符', trigger: ['blur', 'change'] }
        ],
        certificateFile: [
          { required: true, message: '附件不能为空', trigger: 'blur' }
        ],
        enterpriseName: [
          { required: true, message: '企业名称不能为空', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在 1~200个字符', trigger: ['blur', 'change'] }
        ],
        legalPersonName: [
          { required: true, message: '法定代表人姓名不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] }
        ],
        orgCode: [
          { required: true, message: '组织机构代码不能为空', trigger: 'blur' },
          { min: 1, max: 18, message: '长度在 1~18个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        registeredCapital: [
          { required: true, message: '注册资本不能为空', trigger: 'blur' },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        legalPersonCardNum: [
          { required: true, message: '身份证号码不能为空', trigger: 'blur' }
        ],
        detailAddress: [
          { required: true, message: '详细地址不能为空', trigger: 'blur' }
        ],
        unitPortal: [
          { validator: validUrl, trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  methods: {
    // 读取企业信息
    enterpriseData () {
      enterprise.enterpriseData(this.$store.getters.authUser.userId).then(item => {
        this.inForm = item.data.enterprise
        this.selectedOptions.push(item.data.enterprise.industry)
        this.selectedOptions.push(item.data.enterprise.industrySecond)
        this.selectedOptions.push(item.data.enterprise.industryThird)
        this.selectedcityAddress.push(item.data.enterprise.privinceId)
        this.selectedcityAddress.push(item.data.enterprise.cityId)
        this.selectedcityAddress.push(item.data.enterprise.countyId)
        this.inForm.idCardFileName = item.data.enterprise.idCardFileName === null ? 'idCard' + Date.now() + '.jpg' : item.data.enterprise.idCardFileName
        this.inForm.businessLicenseFileName = item.data.enterprise.businessLicenseFileName === null ? 'business' + Date.now() + '.jpg' : item.data.enterprise.businessLicenseFileName
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
    },
    // 删除国税附件
    removeIrsTax () {
      this.inForm.enterpriseExpand.fileInformations.map((item, index) => {
        if (item.businessType === '1') {
          this.inForm.enterpriseExpand.fileInformations.splice(index, 1)
          this.irsTaxFlag = false
          this.irsTaxFlagElse = true
        }
      })
    },
    // 删除地税附件
    removeCertificate () {
      this.inForm.enterpriseExpand.fileInformations.map((item, index) => {
        if (item.businessType === '2') {
          this.inForm.enterpriseExpand.fileInformations.splice(index, 1)
          this.certificateFlag = false
          this.certificateFlagElse = true
        }
      })
    },
    // 删除证书附件
    removeLocalTax () {
      this.inForm.enterpriseExpand.fileInformations.map((item, index) => {
        if (item.businessType === '3') {
          this.inForm.enterpriseExpand.fileInformations.splice(index, 1)
          this.localTaxFlag = false
          this.localTaxFlagElse = true
        }
      })
    },
    // 下载文件
    handleEdit (name, path) {
      downloadFile(name, path)
    },
    // 检查文件大小
    before (file) {
      return fileSize(this, file)
    },
    // 移除文件
    handleRemove (file, fileList) {
      this.inForm.enterpriseExpand.fileInformations.map((item, index) => {
        if (item.fileName === file.name) {
          this.inForm.enterpriseExpand.fileInformations.splice(index, 1)
        }
      })
    },
    // 移除文件之前的钩子
    beforeRemove (file, fileList) {
      return this.$confirm(`确定移除${file.name}？`)
    },
    // 国税税务证件上传成功回调函数
    irsTaxSuccess (res) {
      res.fileInformation.businessType = 1
      this.inForm.enterpriseExpand.fileInformations.push(res.fileInformation)
    },
    // 地税税务证件上传成功回调函数
    localTaxSuccess (res) {
      res.fileInformation.businessType = 2
      this.inForm.enterpriseExpand.fileInformations.push(res.fileInformation)
    },
    // 资质证书附件上传成功回调函数
    certificateSuccess (res) {
      res.fileInformation.businessType = 3
      this.inForm.enterpriseExpand.fileInformations.push(res.fileInformation)
    },
    // 附件数据
    handleExceed (files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    // 数据提交
    AuthenticationBtn (formName) {
      this.$confirm('确认进行认证吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.submitCount === 0) {
              let subjectArr = this.$route.query.SelectFrom.split(',')
              this.inForm.enterpriseAndSubjects = []
              subjectArr.map(ite => {
                let subjects = {
                  userId: this.$store.getters.authUser.userId,
                  subjectId: ite
                }
                this.inForm.enterpriseAndSubjects.push(subjects)
              })
              this.submitCount++
            }
            // 身份证文件信息
            if (this.inForm.idCardFileUrl === null || this.inForm.idCardFileUrl === '' || this.inForm.idCardFileUrl === undefined) {
              this.alertMessage = '请完善身份证文件信息'
              this.dialogVisible = true
              return false
            }
            // 营业执照文件信息
            if (this.inForm.businessLicenseFileUrl === null || this.inForm.businessLicenseFileUrl === '' || this.inForm.businessLicenseFileUrl === undefined) {
              this.alertMessage = '请完善营业执照文件信息'
              this.dialogVisible = true
              return false
            }
            if (this.inForm.enterpriseExpand.fileInformations.length < 2) {
              this.$message({
                message: '请查看文件是否上传完毕',
                type: 'warning'
              })
              return false
            }
            if (this.flagsShow === true) {
              this.inForm.flag = true
            }
            enterprise.update(this.inForm).then(item => {
              this.$router.push({path: '/authen-inform', query: {flags: this.flagsShow}})
            })
          } else {
            this.$message({
              message: '请查看必填选是否填写完毕',
              type: 'warning'
            })
            return false
          }
        })
      }).catch(() => {
        return false
      })
    },
    // 上一步
    cancelBtn (formName) {
      this.$confirm('确认进行上一步操作吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$router.push({path: '/subject', query: {subjectForm: this.$route.query.SelectFrom, flangshow: true, approveState: this.$route.query.approveState}})
      }).catch(() => {
        return false
      })
    },
    // 注册资本选择
    handleChange (value) {
      this.inForm.registeredCapital = this.selecteregistered.toString()
    },
    skipBibenet () {
      window.open('https://www.bibenet.com/login.jsp')
    }
  },
  mounted () {
    let selectArrr = this.$route.query.SelectFrom.split(',')
    selectArrr.map((ite) => {
      if (ite === '10001') {
        this.flagsShow = true
      }
    })
    this.enterpriseData()
  }
}
</script>
<style lang="less">
  #homeinformsss{
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
      width: 70%;
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
    .el-form-item__label {
      width: 240px!important;
    }
    .el-form-item__content {
      margin-left: 240px!important;
      width: auto;
    }
    .updateBtn>span{
      color: #3f63f6!important;
    }
    .nationalTaxbox{
      width: 33.5%;
    }
  }
</style>
