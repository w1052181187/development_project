<template>
  <div id="homeinformsss">
    <div class="homepagecontent">
      <publicside></publicside>
      <div class="homepageconright">
        <!--面包屑-->
        <el-breadcrumb class="breadcrumbbox" separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/authentication' }">企业认证</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/subject' }">主体类型</el-breadcrumb-item>
          <el-breadcrumb-item>立即认证</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
        <!--内容开始-->
        <div class="penserviceconbox">
          <el-collapse v-model="activeName" accordion>
            <el-row type="flex" class="row-bg">
              <el-form :model="inForm" ref="inForm" :rules="rules" class="demo-ruleForm">
                <el-collapse-item title="基本信息" name="1" class="contitlebox">
                  <el-col :span="12">
                    <el-form-item  label="企业名称：" prop="enterpriseName" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.enterpriseName" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="法定代表人姓名：" prop="representativeName" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.representativeName" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="所属行业：" prop="Industry" :label-width="formLabelWidth">
                      <el-select v-model="inForm.Industry" disabled placeholder="请选择">
                        <el-option
                          v-for="item in IndustrySelect"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                        </el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="组织机构代码：" prop="Organization" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.Organization" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="加盖公章的组织机构代码复印件：" :label-width="formLabelWidth">
                      <el-upload
                        class="upload-demo huoquList"
                        action="https://jsonplaceholder.typicode.com/posts/"
                        multiple
                        :limit="1"
                        disabled
                        :on-exceed="handleExceed"
                        :before-remove="beforeRemove"
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
                        :on-exceed="handleExceed"
                        :before-remove="beforeRemove"
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
                        :on-exceed="handleExceed"
                        :before-remove="beforeRemove"
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
                        :on-exceed="handleExceed"
                        :before-remove="beforeRemove"
                        :file-list="IDbackfileList" v-bind:title="IDbackfileList[0].name">
                      </el-upload>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="注册地址：" prop="registeredAddress" :label-width="formLabelWidth">
                      <el-cascader class="zcdz"
                         expand-trigger="hover" disabled
                         :options="addressOptions"
                         v-model="selectedcityOptions"
                         @change="handleChangeAddress">
                      </el-cascader>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="详细地址：" prop="AddressName" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.AddressName" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="身份证号码：" prop="IDname" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.IDname" disabled></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="营业执照登记机关：" prop="Businesslicense" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.Businesslicense"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="营业执照失效日期：" prop="BusinessStateTimes" :label-width="formLabelWidth">
                      <el-date-picker
                        v-model="inForm.BusinessStateTimes"
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        :picker-options="pickerOptions"
                        placeholder="选择日期时间">
                      </el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="法人机构类别:" prop="mechanismType" :label-width="formLabelWidth">
                      <el-select v-model="inForm.mechanismType" clearable placeholder="请选择">
                        <el-option
                          v-for="item in mechanismTypeValue"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                        </el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="注册资本:" prop="registeredcapital" :label-width="formLabelWidth">
                      <el-cascader
                         expand-trigger="hover" class="zcdz"
                         :options="registeredOptions"
                         v-model="selecteregistered"
                         @change="handleChange">
                      </el-cascader>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item class="bitianicon"  label="营业执照附件：" :label-width="formLabelWidth">
                      <el-upload
                        class="upload-demo"
                        action="https://jsonplaceholder.typicode.com/posts/"
                        multiple
                        :limit="1"
                        :on-exceed="handleExceed"
                        :before-remove="beforeRemove"
                        :file-list="BusinessfileList"
                        :on-success='Businesssuccess'>
                        <el-button size="small" class="uploadbtn" type="primary">点击上传</el-button>
                      </el-upload>
                    </el-form-item>
                  </el-col>
                </el-collapse-item>
                <el-collapse-item title="人员信息" name="2" class="contitlebox" v-show="flagsShow==true">
                  <el-col :span="12">
                    <el-form-item  label="全部人员数目：" prop="wholeNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.wholeNumber"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="全部硕士以上学历数目：" prop="wholeEducationNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.wholeEducationNumber"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="全部高级职称数目：" prop="SeniorNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.SeniorNumber"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="招标从业人员数目：" prop="EmploymentNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.EmploymentNumber"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="全部中级职称数目：" prop="IntermediateNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.IntermediateNumber"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="招标从业人员中级及以上职数目：" prop="EmploymentAboveNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.EmploymentAboveNumber"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="招标从业人员高级职称数目：" prop="IntermediateNumbergj" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.IntermediateNumbergj"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="招标从业人员中级职称数目：" prop="IntermediateNumberzj" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.IntermediateNumberzj"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="招标从业人员中级及以上职占比：" prop="Employmentproportion" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.Employmentproportion"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="其他职业资格人员数量：" prop="OthervocationalNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.OthervocationalNumber"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="全部具备职业资格数目：" prop="AllhaveNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.AllhaveNumber"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="其他职业资格人员备注：" prop="OtherRemarks" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.OtherRemarks"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="已登记招标师数量：" prop="RegisteredNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.RegisteredNumber"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="造价工程师数量：" prop="CostengineerNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.CostengineerNumber"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="已登记招标师占比：" prop="Registeredproportion" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.Registeredproportion"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="监理工程师数量：" prop="SupervisorNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.SupervisorNumber"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="工程建设类职业资格人员数量：" prop="EngineeringNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.EngineeringNumber"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="一级造价师数量：" prop="onelevelQuantityNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.onelevelQuantityNumber"></el-input>
                    </el-form-item>
                  </el-col>
                </el-collapse-item>
                <el-collapse-item title="扩展信息" name="3" class="contitlebox">
                  <el-col :span="12">
                    <el-form-item  label="分公司数量：" prop="BranchOfficeNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.BranchOfficeNumber"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="办事处数量：" prop="OfficeNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.OfficeNumber"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="办公设备信息网络：" prop="informationNetwork" :label-width="formLabelWidth" :rules="[
                      { required: false, message: '', trigger: 'blur' },
                      {min: 1, max: 200, message: '请输入1~200个字符',trigger: ['blur', 'change'] }
                    ]">
                      <el-input  v-model="inForm.informationNetwork"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="企业近三年的财务状况：" prop="Finance" :label-width="formLabelWidth" :rules="[
                      { required: false, message: '', trigger: 'blur' },
                      {min: 1, max: 200, message: '请输入1~200个字符',trigger: ['blur', 'change'] }
                    ]">
                      <el-input  v-model="inForm.Finance"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="机构名称变化情况：" prop="mechanismName" :label-width="formLabelWidth" :rules="[
                      { required: false, message: '', trigger: 'blur' },
                      {min: 1, max: 200, message: '请输入1~200个字符',trigger: ['blur', 'change'] }
                    ]">
                      <el-input  v-model="inForm.mechanismName"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="国税税务登记证失效日期：" prop="InvalidStateTimes" :label-width="formLabelWidth">
                      <el-date-picker
                        v-model="inForm.InvalidTimes"
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        :picker-options="pickerOptions"
                        placeholder="选择日期时间">
                      </el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="国税税务登记证证号：" prop="Statetax" :label-width="formLabelWidth" :rules="[
                      { required: false, message: '', trigger: 'blur' },
                      {min: 1, max: 50, message: '请输入1~50个字符',trigger: ['blur', 'change'] }
                    ]">
                      <el-input  v-model="inForm.Statetax"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="地税税务登记证证号：" prop="Landtax" :label-width="formLabelWidth" :rules="[
                      { required: false, message: '', trigger: 'blur' },
                      {min: 1, max: 50, message: '请输入1~50个字符',trigger: ['blur', 'change'] }
                    ]">
                      <el-input  v-model="inForm.Landtax"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="国税税务登记附件：" :label-width="formLabelWidth">
                        <el-upload
                          class="upload-demo"
                          action="https://jsonplaceholder.typicode.com/posts/"
                           multiple
                          :limit="1"
                          :on-exceed="handleExceed"
                          :before-remove="beforeRemove"
                          :file-list="fileList">
                          <el-button size="small" class="uploadbtn" type="primary">点击上传</el-button>
                        </el-upload>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="地税税务登记证附件：" :label-width="formLabelWidth">
                      <el-upload
                        class="upload-demo"
                        action="https://jsonplaceholder.typicode.com/posts/"
                        multiple
                        :limit="1"
                        :on-exceed="handleExceed"
                        :before-remove="beforeRemove"
                        :file-list="fileList">
                        <el-button size="small" class="uploadbtn"  type="primary">点击上传</el-button>
                      </el-upload>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="地税税务登记证失效日期：" prop="InvalidLandtaxTimes" :label-width="formLabelWidth">
                      <el-date-picker
                        v-model="inForm.InvalidTimes"
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        :picker-options="pickerOptions"
                        placeholder="选择日期时间">
                      </el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="基本户开户支行号：" prop="Accountopening" :label-width="formLabelWidth" :rules="[
                      { required: false, message: '', trigger: 'blur' },
                      {min: 1, max: 50, message: '请输入1~50个字符',trigger: ['blur', 'change'] }
                    ]">
                      <el-input  v-model="inForm.Accountopening"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="基本户开户银行：" prop="basicBank" :label-width="formLabelWidth" :rules="[
                      { required: false, message: '', trigger: 'blur' },
                      {min: 1, max: 200, message: '请输入1~200个字符',trigger: ['blur', 'change'] }
                    ]">
                      <el-input  v-model="inForm.basicBank"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="基本户账户名称：" prop="accounttitle" :label-width="formLabelWidth" :rules="[
                      { required: false, message: '', trigger: 'blur' },
                      {min: 1, max: 200, message: '请输入1~200个字符',trigger: ['blur', 'change'] }
                    ]">
                      <el-input  v-model="inForm.accounttitle"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="基本户账户：" prop="basicAccount" :label-width="formLabelWidth" :rules="[
                      { required: false, message: '', trigger: 'blur' },
                      {min: 1, max: 200, message: '请输入1~200个字符',trigger: ['blur', 'change'] }
                    ]">
                      <el-input  v-model="inForm.basicAccount"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="资质证书编号：" prop="QualificationNumber" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.QualificationNumber"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="资质名称:" prop="Aptitudename" :label-width="formLabelWidth">
                      <el-select v-model="inForm.Aptitudename" clearable placeholder="请选择">
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
                    <el-form-item  label="资质证书失效日期：" prop="QualificationTimes" :label-width="formLabelWidth">
                      <el-date-picker
                        v-model="inForm.QualificationTimes"
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        :picker-options="pickerOptions"
                        placeholder="选择日期时间">
                      </el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="资质等级：" prop="Qualificationgrade" :label-width="formLabelWidth">
                      <el-select v-model="inForm.Qualificationgrade" clearable placeholder="请选择">
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
                    <el-form-item  label="审批日期：" prop="examineTimes" :label-width="formLabelWidth">
                      <el-date-picker
                        v-model="inForm.examineTimes"
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        :picker-options="pickerOptions"
                        placeholder="选择日期时间">
                      </el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="资质序列：" prop="sequence" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.sequence"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="证书附件：" class="bitianicon" :label-width="formLabelWidth">
                      <el-upload
                        class="upload-demo"
                        action="https://jsonplaceholder.typicode.com/posts/"
                        multiple
                        :limit="1"
                        :on-exceed="handleExceed"
                        :before-remove="beforeRemove"
                        :file-list="CertificatefileList"
                        :on-success='Certificatesuccess'>
                        <el-button size="small" class="uploadbtn"  type="primary">点击上传</el-button>
                      </el-upload>
                    </el-form-item>
                  </el-col>
                </el-collapse-item>
                <el-collapse-item title="联系方式" name="4" class="contitlebox">
                  <el-col :span="12">
                    <el-form-item  label="联系人：" prop="Contacts" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.Contacts"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="联系地址：" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.Contactaddress"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="联系人电话：" prop="ContactsTelephone" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.ContactsTelephone"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="电子邮箱：" prop="email"  :label-width="formLabelWidth"
                    :rules="[
                      { required: false, message: '请输入邮箱地址', trigger: 'blur' },
                      { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                    ]">
                      <el-input  v-model="inForm.email"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="单位网站：" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.website"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="法人代表联系电话：" prop="corporationTelephone"  :label-width="formLabelWidth">
                      <el-input  v-model="inForm.corporationTelephone"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item  label="资信等级：" prop="Creditworthiness" :label-width="formLabelWidth">
                      <el-input  v-model="inForm.Creditworthiness"></el-input>
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
      </div>
    </div>
  </div>
</template>
<script>
import publicside from '../adminpublic/side.vue'
import {isvalidPhone, Numbertype, Decimal} from '../../assets/js/validate'
import * as citydata from '../../assets/js/citydata'
export default {
  components: {
    publicside
  },
  data () {
    // 电话验证
    var validPhone = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入电话号码'))
      } else if (!isvalidPhone(value)) {
        callback(new Error('请输入正确的11位手机号码'))
      } else {
        callback()
      }
    }
    var SelectionPhone = (rule, value, callback) => {
      if (value == '') {
        callback()
      } else if (!isvalidPhone(value)) {
        callback(new Error('请输入正确的11位手机号码'))
      } else {
        callback()
      }
    }
    // 数字
    var Numbertypes = (rule, value, callback) => {
      if (value === '') {
        callback()
      } else if (!Numbertype(value)) {
        callback(new Error('请输入数字'))
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
        Contacts: '',
        Contactaddress: '',
        ContactsTelephone: '',
        email: '',
        website: '',
        corporationTelephone: '',
        Creditworthiness: '',
        BranchOfficeNumber: '',
        OfficeNumber: '',
        informationNetwork: '',
        Finance: '',
        InvalidStateTimes: '',
        Statetax: '',
        Landtax: '',
        InvalidLandtaxTimes: '',
        Accountopening: '',
        basicBank: '',
        accounttitle: '',
        basicAccount: '',
        QualificationNumber: '',
        Aptitudename: '',
        QualificationTimes: '',
        examineTimes: '',
        Qualificationgrade: '',
        sequence: '',
        wholeNumber: '',
        wholeEducationNumber: '',
        SeniorNumber: '',
        IntermediateNumber: '',
        EmploymentNumber: '',
        EmploymentAboveNumber: '',
        IntermediateNumbergj: '',
        IntermediateNumberzj: '',
        IntermediateNumberzjproportion: '',
        OthervocationalNumber: '',
        onelevelQuantityNumber: '',
        EngineeringNumber: '',
        SupervisorNumber: '',
        CostengineerNumber: '',
        RegisteredNumber: '',
        OtherRemarks: '',
        AllhaveNumber: '',
        Employmentproportion: '',
        Registeredproportion: '',
        enterpriseName: '巧醉有限公司',
        representativeName: '张三',
        Organization: '123456789',
        Industry: '传统行业',
        AddressName: '赛鼎路万立科技大厦1906',
        IDname: '1112223341678333333333',
        Businesslicense: '',
        BusinessStateTimes: '',
        mechanismType: '',
        registeredcapital: ''
      },
      formLabelWidth: '240px',
      // 附件列表
      fileList: [],
      // 营业执照附件
      BusinessfileList: [],
      // 证书附件
      CertificatefileList: [],
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
      // 日期选择
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7
        }
      },
      // 三级联动
      addressOptions: citydata.CityInfo,
      selectedcityOptions: [1, 1, 1],
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
        }, {
          value: '传统行业',
          label: '传统行业'
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
      // 提交时验证
      rules: {
        Contacts: [
          { required: true, message: '联系人不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] }
        ],
        BranchOfficeNumber: [
          { validator: Numbertypes, trigger: ['blur', 'change'] },
          { min: 1, max: 5, message: '长度在 1~5个字符', trigger: ['blur', 'change'] }
        ],
        OfficeNumber: [
          { validator: Numbertypes, trigger: ['blur', 'change'] },
          { min: 1, max: 5, message: '长度在 1~5个字符', trigger: ['blur', 'change'] }
        ],
        ContactsTelephone: [
          { required: true, message: '联系人电话不能为空', trigger: 'blur' },
          {required: true, validator: validPhone, trigger: ['blur', 'change']}
        ],
        corporationTelephone: [
          {required: false, validator: SelectionPhone, trigger: ['blur', 'change']}
        ],
        Creditworthiness: [
          { required: true, message: '资信等级不能为空', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1~50个字符', trigger: ['blur', 'change'] }
        ],
        QualificationNumber: [
          { required: true, message: '资质证书编号不能为空', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1~50个字符', trigger: ['blur', 'change'] }
        ],
        Aptitudename: [
          { required: true, message: '资质名称不能为空', trigger: 'blur' }
        ],
        QualificationTimes: [
          { required: true, message: '资质证书失效日期不能为空', trigger: 'blur' }
        ],
        Qualificationgrade: [
          { required: true, message: '资质等级不能为空', trigger: 'blur' }
        ],
        examineTimes: [
          { required: true, message: '审批日期不能为空', trigger: 'blur' }
        ],
        sequence: [
          { required: true, message: '资质序列不能为空', trigger: 'blur' },
          { min: 1, max: 6, message: '长度在 1~6个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        certificateFile: [
          { required: true, message: '附件不能为空', trigger: 'blur' }
        ],
        wholeNumber: [
          { required: true, message: '全部人员数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        wholeEducationNumber: [
          { required: true, message: '全部硕士以上学历数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        SeniorNumber: [
          { required: true, message: '全部高级职称数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        IntermediateNumber: [
          { required: true, message: '全部中级职称数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        EmploymentNumber: [
          { required: true, message: '招标从业人员数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        EmploymentAboveNumber: [
          { required: true, message: '招标从业人员中级及以上职数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        IntermediateNumbergj: [
          { required: true, message: '招标从业人员高级职称数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        IntermediateNumberzj: [
          { required: true, message: '招标从业人员中级职称数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        OthervocationalNumber: [
          { required: true, message: '其他职业资格人员数量不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        onelevelQuantityNumber: [
          { required: true, message: '一级造价师数量不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        EngineeringNumber: [
          { required: true, message: '工程建设类职业资格人员数量不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        SupervisorNumber: [
          { required: true, message: '监理工程师数量不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        CostengineerNumber: [
          { required: true, message: '造价工程师数量不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        RegisteredNumber: [
          { required: true, message: '已登记招标师数量不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        OtherRemarks: [
          { required: true, message: '其他职业资格人员备注不能为空', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在 1~200个字符', trigger: ['blur', 'change'] }
        ],
        AllhaveNumber: [
          { required: true, message: '全部具备职业资格数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        Employmentproportion: [
          { required: true, message: '招标从业人员中级及以上职占比不能为空', trigger: 'blur' },
          { min: 4, max: 6, message: '小数位最长为4位，长度在 4~6个字符', trigger: ['blur', 'change'] },
          { validator: Decimaltypes, trigger: ['blur', 'change'] }
        ],
        Registeredproportion: [
          { required: true, message: '已登记招标师占比不能为空', trigger: 'blur' },
          { min: 4, max: 6, message: '小数位最长为4位，长度在 4~6个字符', trigger: ['blur', 'change'] },
          { validator: Decimaltypes, trigger: ['blur', 'change'] }
        ],
        enterpriseName: [
          { required: true, message: '企业名称不能为空', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在 1~200个字符', trigger: ['blur', 'change'] }
        ],
        representativeName: [
          { required: true, message: '法定代表人姓名不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] }
        ],
        Organization: [
          { required: true, message: '法定代表人姓名不能为空', trigger: 'blur' },
          { min: 1, max: 18, message: '长度在 1~18个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        Industry: [
          { required: true, message: '所属行业不能为空', trigger: 'blur' }
        ],
        Businesslicense: [
          { required: true, message: '营业执照登记机关不能为空', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1~50个字符', trigger: ['blur', 'change'] }
        ],
        BusinessStateTimes: [
          { required: true, message: '营业执照失效日期不能为空', trigger: 'blur' }
        ],
        mechanismType: [
          { required: true, message: '法人机构类别不能为空', trigger: 'blur' }
        ],
        registeredcapital: [
          { required: true, message: '注册资本不能为空', trigger: 'blur' }
        ],
        IDname: [
          { required: true, message: '身份证不能为空', trigger: 'blur' }
        ],
        AddressName: [
          { required: true, message: '详细地址不能为空', trigger: 'blur' }
        ],
        registeredAddress: [
          { required: true, message: '注册地址不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 附件数据
    handleExceed (files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    beforeRemove (file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    // 营业执照文件上传成功回调函数：
    Businesssuccess (res, file, fileList) {
      this.BusinessfileList = fileList
    },
    // 证书文件上传成功回调函数：
    Certificatesuccess (res, file, fileList) {
      this.CertificatefileList = fileList
    },
    // 判断是否显示人员信息
    isRepeat (Numbertypes, Decimaltypes) {
      if (this.flagsShow == false) {
        delete this.inForm.wholeNumber
        delete this.rules.wholeNumber
        delete this.inForm.wholeEducationNumber
        delete this.rules.wholeEducationNumber
        delete this.inForm.SeniorNumber
        delete this.rules.SeniorNumber
        delete this.inForm.EmploymentNumber
        delete this.rules.EmploymentNumber
        delete this.inForm.IntermediateNumber
        delete this.rules.IntermediateNumber
        delete this.inForm.EmploymentAboveNumber
        delete this.rules.EmploymentAboveNumber
        delete this.inForm.IntermediateNumbergj
        delete this.rules.IntermediateNumbergj
        delete this.inForm.IntermediateNumberzj
        delete this.rules.IntermediateNumberzj
        delete this.inForm.Employmentproportion
        delete this.rules.Employmentproportion
        delete this.inForm.OthervocationalNumber
        delete this.rules.OthervocationalNumber
        delete this.inForm.AllhaveNumber
        delete this.rules.AllhaveNumber
        delete this.inForm.OtherRemarks
        delete this.rules.OtherRemarks
        delete this.inForm.RegisteredNumber
        delete this.rules.RegisteredNumber
        delete this.inForm.CostengineerNumber
        delete this.rules.CostengineerNumber
        delete this.inForm.Registeredproportion
        delete this.rules.Registeredproportion
        delete this.inForm.SupervisorNumber
        delete this.rules.SupervisorNumber
        delete this.inForm.EngineeringNumber
        delete this.rules.EngineeringNumber
        delete this.inForm.onelevelQuantityNumber
        delete this.rules.onelevelQuantityNumber
      } else {
        this.rules.wholeNumber = [
          { required: true, message: '全部人员数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ]
        this.rules.wholeEducationNumber = [
          { required: true, message: '全部硕士以上学历数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ]
        this.rules.SeniorNumber = [
          { required: true, message: '全部高级职称数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ]
        this.rules.EmploymentNumber = [
          { required: true, message: '招标从业人员数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ]
        this.rules.IntermediateNumber = [
          { required: true, message: '全部中级职称数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ]
        this.rules.EmploymentAboveNumber = [
          { required: true, message: '招标从业人员中级及以上职数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ]
        this.rules.IntermediateNumbergj = [
          { required: true, message: '招标从业人员高级职称数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ]
        this.rules.IntermediateNumberzj = [
          { required: true, message: '招标从业人员中级职称数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ]
        this.rules.Employmentproportion = [
          { required: true, message: '招标从业人员中级及以上职占比不能为空', trigger: 'blur' },
          { min: 4, max: 6, message: '小数位最长为4位，长度在 4~6个字符', trigger: ['blur', 'change'] },
          { validator: Decimaltypes, trigger: ['blur', 'change'] }
        ]
        this.rules.OthervocationalNumber = [
          { required: true, message: '其他职业资格人员数量不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ]
        this.rules.AllhaveNumber = [
          { required: true, message: '全部具备职业资格数目不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ]
        this.rules.OtherRemarks = [
          { required: true, message: '其他职业资格人员备注不能为空', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在 1~200个字符', trigger: ['blur', 'change'] }
        ]
        this.rules.RegisteredNumber = [
          { required: true, message: '已登记招标师数量不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ]
        this.rules.CostengineerNumber = [
          { required: true, message: '造价工程师数量不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ]
        this.rules.Registeredproportion = [
          { required: true, message: '已登记招标师占比不能为空', trigger: 'blur' },
          { min: 4, max: 6, message: '小数位最长为4位，长度在 4~6个字符', trigger: ['blur', 'change'] },
          { validator: Decimaltypes, trigger: ['blur', 'change'] }
        ]
        this.rules.SupervisorNumber = [
          { required: true, message: '监理工程师数量不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ]
        this.rules.EngineeringNumber = [
          { required: true, message: '工程建设类职业资格人员数量不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ]
        this.rules.onelevelQuantityNumber = [
          { required: true, message: '一级造价师数量不能为空', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1~7个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ]
      }
    },
    // 数据提交
    AuthenticationBtn (formName) {
      this.$confirm('确认进行认证吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.isRepeat()
        this.$refs[formName].validate((valid) => {
          if (valid && this.BusinessfileList.length > 0 && this.CertificatefileList.length > 0) {
            this.$router.push({name: 'authen-inform', params: {flags: this.flagsShow}})
          } else {
            this.$message({
              message: '请查看必填选是否填写完毕',
              type: 'warning'
            })
            if (this.BusinessfileList.length == 0) {
              this.$message.error('请上传营业执照附件')
              return false
            }
            if (this.CertificatefileList.length == 0) {
              this.$message.error('请上传证书附件')
              return false
            }
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
        this.$router.push({name: 'subject', params: {subjectForm: this.$route.params.SelectFrom, flangshow: true}})
      }).catch(() => {
        return false
      })
    },
    // 注册资本选择
    handleChange (value) {
      this.inForm.registeredcapital = this.selecteregistered.toString()
    },
    // 地址选择
    handleChangeAddress (value) {
      this.inForm.registeredAddress = this.selectedcityOptions.toString()
    }
  },
  mounted () {
    this.inForm.registeredAddress = this.selectedcityOptions.toString()
    this.$route.params.SelectFrom.subjectype.map((ite) => {
      if (ite == '招标代理') {
        this.flagsShow = true
      }
    })
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

  }
</style>
