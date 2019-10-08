<template>
  <div id="homeautheninf">
    <div class="homepagecontent">
      <publicside></publicside>
      <div class="homepageconright">
        <!--面包屑-->
        <el-breadcrumb class="breadcrumbbox" separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/authentication' }">企业认证</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/subject' }">主体类型</el-breadcrumb-item>
          <el-breadcrumb-item>认证成功</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
        <!--内容开始-->
        <div class="penserviceconbox">
          <div class="Authentication_statebigbox" v-show="LookStatus == true">
            <div class="Authentication_statebox">
              <div class="iconimgstatus">
                <img src="../../../static/images/dui.png" alt="" v-show="states==true">
                <img src="../../../static/images/cuo.png" alt="" v-show="states!=true">
              </div>
              <div class="Authenxinxibox">
                <div class="Authenxinxitop">
                  <span style="color: #43bf2b" v-show="states==true">恭喜您，认证成功！</span>
                  <span style="color: #ff6363" v-show="states!=true">很遗憾，认证失败！</span>
                </div>
                <div class="Authenxinxibottom">
                  <div class="Authenbottomson">
                    <span>审核人：</span>
                    <span>张三三</span>
                  </div>
                  <div class="Authenbottomsontwo">
                    <span>审核时间：</span>
                    <span>2018-05-03 14:35</span>
                  </div>
                </div>
                <div class="chongxin_btn" v-show="states!=true">重新认证</div>
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
                    <el-form-item class="bitianicon"  label="法定代表人姓名：" prop="representativeName" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.representativeName" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="所属行业：" prop="Industry" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.Industry" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="组织机构代码：" prop="Organization" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.Organization" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="加盖公章的组织机构代码复印件：" :label-width="formLabelWidth">
                      <el-upload
                        class="upload-demo"
                        action="https://jsonplaceholder.typicode.com/posts/"
                        multiple
                        :limit="1"
                        disabled
                        :file-list="OrganizationfileList" v-bind:title="OrganizationfileList[0].name">
                      </el-upload>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="加盖公章的税务登记证：" :label-width="formLabelWidth">
                      <el-upload
                        class="upload-demo"
                        action="https://jsonplaceholder.typicode.com/posts/"
                        multiple
                        :limit="1"
                        disabled
                        :file-list="RegistrationfileList" v-bind:title="RegistrationfileList[0].name">
                      </el-upload>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"   label="加盖公章的身份证正面复印件：" :label-width="formLabelWidth">
                      <el-upload
                        class="upload-demo"
                        action="https://jsonplaceholder.typicode.com/posts/"
                        multiple
                        :limit="1"
                        disabled
                        :file-list="IDjustfileList" v-bind:title="IDjustfileList[0].name">
                      </el-upload>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="加盖公章的身份证反面复印件：" :label-width="formLabelWidth">
                      <el-upload
                        class="upload-demo"
                        action="https://jsonplaceholder.typicode.com/posts/"
                        multiple
                        :limit="1"
                        disabled
                        :file-list="IDbackfileList" v-bind:title="IDbackfileList[0].name">
                      </el-upload>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"   label="注册地址：" :label-width="formLabelWidth">
                      <el-cascader class="zcdz"
                         expand-trigger="hover" disabled
                         :options="addressOptions"
                         v-model="selectedcityAddress">
                      </el-cascader>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="详细地址：" prop="AddressName" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.AddressName" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"   label="身份证号码：" prop="IDname" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.IDname" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="营业执照登记机关：" prop="Businesslicense" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.Businesslicense" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="营业执照失效日期：" prop="BusinessStateTimes" :label-width="formLabelWidth">
                      <el-date-picker
                        v-model="inForm.BusinessStateTimes"
                        disabled
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="选择日期时间">
                      </el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="法人机构类别:" prop="mechanismType" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.mechanismType" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="注册资本:" :label-width="formLabelWidth">
                      <el-cascader
                        disabled
                        expand-trigger="hover" class="zcdz"
                        :options="registeredOptions"
                        v-model="selecteregistered">
                      </el-cascader>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="营业执照附件：" :label-width="formLabelWidth">
                      <el-upload
                        disabled
                        class="upload-demo"
                        action="https://jsonplaceholder.typicode.com/posts/"
                        multiple
                        :limit="1"
                        :file-list="BusinessfileList">
                      </el-upload>
                    </el-form-item>
                  </el-col>
                </el-collapse-item>
                <el-collapse-item title="人员信息" name="2" class="contitlebox" v-show="flagsL==true">
                  <el-col :span="12">
                    <el-form-item class="bitianicon"   label="全部人员数目：" prop="wholeNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.wholeNumber" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"   label="全部硕士以上学历数目：" prop="wholeEducationNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.wholeEducationNumber" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"   label="全部高级职称数目：" prop="SeniorNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.SeniorNumber" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"   label="招标从业人员数目：" prop="EmploymentNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.EmploymentNumber" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"   label="全部中级职称数目：" prop="IntermediateNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.IntermediateNumber" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"   label="招标从业人员中级及以上职数目：" prop="EmploymentAboveNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.EmploymentAboveNumber" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="招标从业人员高级职称数目：" prop="IntermediateNumbergj" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.IntermediateNumbergj" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="招标从业人员中级职称数目：" prop="IntermediateNumberzj" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.IntermediateNumberzj" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="招标从业人员中级及以上职占比：" prop="Employmentproportion" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.Employmentproportion" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="其他职业资格人员数量：" prop="OthervocationalNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.OthervocationalNumber" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="全部具备职业资格数目：" prop="AllhaveNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.AllhaveNumber" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="其他职业资格人员备注：" prop="OtherRemarks" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.OtherRemarks" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="已登记招标师数量：" prop="RegisteredNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.RegisteredNumber" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="造价工程师数量：" prop="CostengineerNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.CostengineerNumber" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="已登记招标师占比：" prop="Registeredproportion" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.Registeredproportion" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="监理工程师数量：" prop="SupervisorNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.SupervisorNumber" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="工程建设类职业资格人员数量：" prop="EngineeringNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.EngineeringNumber" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="一级造价师数量：" prop="onelevelQuantityNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.onelevelQuantityNumber" disabled></el-input>
                    </el-form-item>
                  </el-col>
                </el-collapse-item>
                <el-collapse-item title="扩展信息" name="3" class="contitlebox">
                  <el-col :span="12">
                    <el-form-item  label="分公司数量：" prop="BranchOfficeNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.BranchOfficeNumber" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="办事处数量：" prop="OfficeNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.OfficeNumber" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="办公设备信息网络：" prop="informationNetwork" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.informationNetwork" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="企业近三年的财务状况：" prop="Finance" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.Finance" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="机构名称变化情况：" prop="mechanismName" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.mechanismName" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="国税税务登记证失效日期：" prop="InvalidTimesGuo" :label-width="formLabelWidth">
                      <el-date-picker
                        disabled
                        v-model="inForm.InvalidTimesGuo"
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="选择日期时间">
                      </el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="国税税务登记证证号：" prop="Statetax" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.Statetax" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="地税税务登记证证号：" prop="Landtax" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.Landtax" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="国税税务登记附件：" :label-width="formLabelWidth">
                      <el-upload
                        disabled
                        class="upload-demo"
                        action="https://jsonplaceholder.typicode.com/posts/"
                        multiple
                        :limit="1"
                        :file-list="GuoshufileList" v-bind:title="GuoshufileList[0].name">
                      </el-upload>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="地税税务登记证附件：" :label-width="formLabelWidth">
                      <el-upload
                        disabled
                        class="upload-demo"
                        action="https://jsonplaceholder.typicode.com/posts/"
                        multiple
                        :limit="1"
                        :file-list="DishufileList" v-bind:title="DishufileList[0].name">
                      </el-upload>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="地税税务登记证失效日期：" prop="InvalidLandtaxTimes" :label-width="formLabelWidth">
                      <el-date-picker
                        disabled
                        v-model="inForm.InvalidTimesDi"
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="选择日期时间">
                      </el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="基本户开户支行号：" prop="Accountopening" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.Accountopening" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="基本户开户银行：" prop="basicBank" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.basicBank" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="基本户账户名称：" prop="accounttitle" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.accounttitle" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="基本户账户：" prop="basicAccount" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.basicAccount" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="资质证书编号：" prop="QualificationNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.QualificationNumber" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon" label="资质名称:" prop="Aptitudename" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.Aptitudename" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="资质证书失效日期：" prop="QualificationTimes" :label-width="formLabelWidth">
                      <el-date-picker
                        disabled
                        v-model="inForm.QualificationTimes"
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="选择日期时间">
                      </el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon" label="资质等级：" prop="Qualificationgrade" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.Qualificationgrade" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="审批日期：" prop="examineTimes" :label-width="formLabelWidth">
                      <el-date-picker
                        disabled
                        v-model="inForm.examineTimes"
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="选择日期时间">
                      </el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="资质序列：" prop="sequence" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.sequence" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item   label="证书附件：" class="bitianicon" :label-width="formLabelWidth">
                      <el-upload
                        disabled
                        class="upload-demo"
                        action="https://jsonplaceholder.typicode.com/posts/"
                        multiple
                        :limit="1"
                        :file-list="CertificatefileList" v-bind:title="CertificatefileList[0].name">
                      </el-upload>
                    </el-form-item>
                  </el-col>
                </el-collapse-item>
                <el-collapse-item title="联系方式" name="4" class="contitlebox">
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="联系人：" prop="Contacts" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.Contacts" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="联系地址：" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.Contactaddress" disabled v-bind:title="inForm.Contactaddress"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="联系人电话：" prop="ContactsTelephone" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.ContactsTelephone" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="电子邮箱：" prop="email"  :label-width="formLabelWidth">
                      <el-input  v-model="inForm.email" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="单位网站：" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.website" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="法人代表联系电话：" prop="corporationTelephone"  :label-width="formLabelWidth">
                      <el-input  v-model="inForm.corporationTelephone" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon" label="资信等级：" prop="Creditworthiness" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.Creditworthiness" disabled></el-input>
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
import * as citydata from '../../assets/js/citydata'
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
        Contacts: '张三',
        Contactaddress: '山西省太原市小店区赛鼎路万立科技',
        ContactsTelephone: '12234455555',
        email: '123456@qq.com',
        website: '123456@qq.com',
        corporationTelephone: '12234455555',
        Creditworthiness: '一级',
        BranchOfficeNumber: '258',
        OfficeNumber: '52',
        informationNetwork: '移动',
        Finance: '优',
        mechanismName: '暂无',
        InvalidTimesGuo: '2018-05-03',
        InvalidTimesDi: '2018-05-03',
        Statetax: '123567899999',
        Landtax: '08888',
        InvalidLandtaxTimes: '2018-05-03',
        Accountopening: '333 3333 33333 333333',
        basicBank: '建设银行',
        accounttitle: '比比网络科技有限公司',
        basicAccount: '123 3344 4444 44444',
        QualificationNumber: '08888',
        Aptitudename: '机电产品国际招标代理',
        QualificationTimes: '2018-05-03',
        examineTimes: '2018-05-03',
        Qualificationgrade: '一级',
        sequence: '08',
        wholeNumber: '14353',
        wholeEducationNumber: '14353',
        SeniorNumber: '18',
        IntermediateNumber: '17',
        EmploymentNumber: '16',
        EmploymentAboveNumber: '13',
        IntermediateNumbergj: '12',
        IntermediateNumberzj: '23',
        IntermediateNumberzjproportion: '34',
        OthervocationalNumber: '44',
        onelevelQuantityNumber: '23',
        EngineeringNumber: '56',
        SupervisorNumber: '23',
        CostengineerNumber: '12',
        RegisteredNumber: '12',
        OtherRemarks: '备注',
        AllhaveNumber: '13',
        Employmentproportion: '0.234',
        Registeredproportion: '0.456',
        enterpriseName: '巧醉有限公司',
        representativeName: '张三',
        Organization: '123456789',
        Industry: '传统行业',
        AddressName: '赛鼎路万立科技大厦1906',
        IDname: '1112223341678333333333',
        Businesslicense: '事业',
        BusinessStateTimes: '2018-05-03',
        mechanismType: '企业'
      },
      formLabelWidth: '240px',
      // 附件列表
      fileList: [],
      // 营业执照附件
      BusinessfileList: [{
        name: 'food.jpeg',
        url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}
      ],
      // 证书附件
      CertificatefileList: [{
        name: '证书.jpeg',
        url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}
      ],
      // 加盖公章的组织机构代码复印件附件读取数据
      OrganizationfileList: [
        {
          name: 'food.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}
      ],
      // 加盖公章的税务登记证
      RegistrationfileList: [
        {
          name: 'food2.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}
      ],
      // 加盖公章的身份证正面复印件
      IDjustfileList: [
        {
          name: '身份证正面.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}
      ],
      // 加盖公章的身份证反面复印件
      IDbackfileList: [
        {
          name: '身份证反面.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}
      ],
      // 国税税务登记附件
      GuoshufileList: [
        {
          name: '国税.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}
      ],
      // 地税税务登记附件
      DishufileList: [
        {
          name: '地税.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}
      ],
      // 三级联动
      addressOptions: citydata.CityInfo,
      selectedcityAddress: [1, 1, 1],
      // 初始定义认证状态
      states: true,
      // 页面展示状态
      LookStatus: true,
      // 人员信息状态
      flagsL: false,
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
      selecteregistered: ['zhinan', 'shejiyuanze', 'yizhi']
    }
  },
  methods: {},
  mounted () {
    if (this.$route.params.LookStatus == false) {
      this.LookStatus = false
    }
    this.flagsL = this.$route.params.flags
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
      padding: 4px 10px;
      border-radius: 5px;
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
  }
</style>
