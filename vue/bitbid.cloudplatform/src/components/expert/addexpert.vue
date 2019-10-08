<template>
  <div>
    <!--内容开始-->
    <div class="content_bigbox" id="expertcontent">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/expert' }">专家管理</el-breadcrumb-item>
        <el-breadcrumb-item class="active_bread">新增专家</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <!--新增归档信息内容-->
      <div class="ckdan_contentbox">
        <el-row type="flex" class="row-bg">
          <el-form :model="form" :rules="rules" ref="form">
            <el-col :span="12">
              <el-form-item label="专家姓名：" prop="name" :label-width="formLabelWidth">
                <el-input v-model="form.name"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <div class="grid-content bg-purple">
                <el-form-item label="性别：" :label-width="formLabelWidth">
                  <el-select class="zzt_zbfs_selectbox sfwtdl_btn" v-model="form.sex" placeholder="请选择">
                    <el-option
                      v-for="item in sexform"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="12">
              <el-form-item label="身份证号码：" prop="idNumber"  :label-width="formLabelWidth">
                <el-input v-model="form.idNumber"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <div class="grid-content bg-purple">
                <el-form-item label="出生年月日：" :label-width="formLabelWidth">
                    <el-date-picker
                      v-model="form.birth"
                      type="date"
                      :picker-options="pickerOptions"
                      value-format="yyyy-MM-dd hh:mm:ss"
                      placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="24">
              <el-col :span="12">
                <el-form-item label="专业分类：" prop="expertClassifyStandard" v-model="form" class="bdlxtitle2" :label-width="formLabelWidth">
                  <el-cascader class="bdlxtitle2two"
                    expand-trigger="hover"
                    :options="categoryOptions"
                    v-model="selectedOptions"
                    @change="handleChange">
                  </el-cascader>
                </el-form-item>
              </el-col>
            </el-col>
            <div class="bdlx_box">
              <el-col :span="18">
                <el-form-item label="专家分类：" prop="expertClassifySelf"  class="bdlxtitle" :label-width="formLabelWidth">
                  <el-select class="zzt_zbfs_selectbox sfwtdl_btn" v-model="form.expertClassifySelf" placeholder="请选择">
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </div>
            <el-col :span="12">
              <el-form-item label="联系电话：" prop="mobile" :label-width="formLabelWidth">
                <el-input v-model="form.mobile"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <div class="grid-content bg-purple">
                <el-form-item label="电子邮箱：" prop="email" :label-width="formLabelWidth"
                :rules="[
                  { required: false, message: '请输入邮箱地址', trigger: 'blur' },
                  { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                ]">
                  <el-input v-model="form.email"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="24">
              <el-form-item label="所在行政区域："  prop="provinceId"  class="bdlxtitle2" :label-width="formLabelWidth">
                <el-cascader class="zcdz"
                   expand-trigger="hover"
                   :options="addressOptions"
                   v-model="selectedcityOptions"
                   @change="handlecityChange">
                </el-cascader>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <div class="grid-content bg-purple">
                <el-form-item label="通讯地址：" prop="address" :label-width="formLabelWidth" :rules="[
                  { required: false, message: '', trigger: 'blur' },
                  { min: 1, max: 100, message: '请输入1~100个字符', trigger: ['blur', 'change']}
                ]">
                  <el-input v-model="form.address"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="12">
              <el-form-item label="邮政编码：" prop="zipCode"  :label-width="formLabelWidth">
                <el-input v-model="form.zipCode"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="最后毕业院校：" prop="school"  class="bdlxtitle2" :label-width="formLabelWidth" :rules="[
                  { required: false, message: '', trigger: 'blur' },
                  { min: 1, max: 100, message: '请输入1~100个字符', trigger: ['blur', 'change']}
                ]">
                <el-input v-model="form.school"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <div class="grid-content bg-purple">
                <el-form-item label="最高学历：" prop="education"   :label-width="formLabelWidth" :rules="[
                  { required: false, message: '', trigger: 'blur' },
                  { min: 1, max: 30, message: '请输入1~30个字符', trigger: ['blur', 'change']}
                ]">
                  <el-input v-model="form.education"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="grid-content bg-purple">
                <el-form-item label="所在单位：" prop="company"    :label-width="formLabelWidth" :rules="[
                  { required: false, message: '', trigger: 'blur' },
                  { min: 1, max: 100, message: '请输入1~100个字符', trigger: ['blur', 'change']}
                ]">
                  <el-input v-model="form.company"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="12">
              <el-form-item label="是否在职：" :label-width="formLabelWidth">
                <el-select class="zzt_zbfs_selectbox sfwtdl_btn" v-model="form.isJob" placeholder="请选择">
                  <el-option
                    v-for="item in job"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <div class="grid-content bg-purple">
                <el-form-item label="职务：" prop="job" :label-width="formLabelWidth" :rules="[
                  { required: false, message: '', trigger: 'blur' },
                  { min: 1, max: 50, message: '请输入1~50个字符', trigger: ['blur', 'change']}
                ]">
                  <el-input v-model="form.job"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="grid-content bg-purple">
                <el-form-item label="从业年限：" prop="workAge" :label-width="formLabelWidth">
                  <el-input v-model="form.workAge"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="grid-content bg-purple">
                <el-form-item label="技术职称：" prop="jobTitle"  :label-width="formLabelWidth" :rules="[
                  { required: false, message: '', trigger: 'blur' },
                  { min: 1, max: 50, message: '请输入1~50个字符', trigger: ['blur', 'change']}
                ]">
                  <el-input v-model="form.jobTitle"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <div class="bdlx_box">
              <el-col :span="24">
                <el-form-item label="职业资格：" class="bdneirong bdneirongzhiye" :label-width="formLabelWidth">
                  <el-tag
                    v-model="form.dynamicTags"
                    :key="index"
                    v-for="(tag,index) in dynamicTags"
                    closable
                    :disable-transitions="false"
                    @close="handleClose(tag)">
                    <span>{{tag.name}}</span>
                    <span style="margin-left: 10px">{{tag.grade}}</span>
                  </el-tag>
                  <!--添加职业资格-->
                  <el-button type="text" class="da_xzgd_btn"  @click="dialogFormVisible = true">
                    <span class="jia_zzt">+</span>
                    <span>添加职业资格</span>
                  </el-button>
                  <el-dialog title="职业资格" :visible.sync="dialogFormVisible">
                    <el-form :model="qualificationsform" :rules="zhiyerules" ref="qualificationsform">
                      <el-form-item label="职业资格名称：" prop="name" :label-width="formLabelWidth" :rules="[
                  { required: true, message: '职业资格名称不能为空', trigger: 'blur' },
                  { min: 1, max: 50, message: '请输入1~50个字符', trigger: ['blur', 'change']}
                ]">
                        <el-input v-model="qualificationsform.name" auto-complete="off"></el-input>
                      </el-form-item>
                      <el-form-item label="职业资格等级：" prop="grade" :label-width="formLabelWidth" :rules="[
                  { required: true, message: '职业资格等级不能为空', trigger: 'blur' },
                  { min: 1, max: 50, message: '请输入1~50个字符', trigger: ['blur', 'change']}
                ]">
                        <el-input v-model="qualificationsform.grade" auto-complete="off"></el-input>
                      </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                      <el-button @click="quxiaobtn">取 消</el-button>
                      <el-button type="primary" @click="addzhiyezhige('qualificationsform')">确 定</el-button>
                    </div>
                  </el-dialog>
                  <!--添加职业资格-->
                </el-form-item>
              </el-col>
            </div>
            <div class="bdlx_box">
              <el-col :span="24">
                <el-form-item label="工作简历：" class="bdneirong" :label-width="formLabelWidth">
                  <el-input
                    type="textarea"
                    :autosize="{ minRows: 2, maxRows: 6}"
                    placeholder=""
                    v-model="form.workResume">
                  </el-input>
                </el-form-item>
              </el-col>
            </div>
            <div class="bdlx_box">
              <el-col :span="24">
                <el-form-item label="专家备注：" class="bdneirong" :label-width="formLabelWidth">
                  <el-input
                    type="textarea"
                    :autosize="{ minRows: 2, maxRows: 6}"
                    placeholder=""
                    v-model="form.expertRemark">
                  </el-input>
                </el-form-item>
              </el-col>
            </div>
          </el-form>
        </el-row>
      </div>
      <!--新增归档信息内容-->
      <!--按钮-->
      <el-row class="gongneng_btnbox">
        <el-button class="gongneng_btn" type="primary" @click="addExpert('form')">
          添加
        </el-button>
        <el-button class="gongneng_btn" type="info" @click="quxiao">
          取消
        </el-button>
      </el-row>
      <!--按钮-->
    </div>
    <!--内容结束-->
  </div>
</template>
<script>
import {isvalidPhone, isvalidId, Zipcode, Numbertype} from '../../assets/js/validate'
import {checkResponse} from '../../assets/js/common'
import * as expertClassify from '../../assets/js/expertClassify'
import * as citydata from '../../assets/js/citydata'
export default {
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
    // 邮编
    var validZipcode = (rule, value, callback) => {
      if (value === '' || value === undefined) {
        callback()
      } else if (!Zipcode(value)) {
        callback(new Error('请输入正确的格式'))
      } else {
        callback()
      }
    }
    // 身份证验证
    var validID = (rule, value, callback) => {
      if (!value) {

      } else if (!isvalidId(value)) {
        callback(new Error('请输入正确的18位身份证号码'))
      } else {
        callback()
      }
    }
    // 数字
    var Numbertypes = (rule, value, callback) => {
      if (value === '') {
        callback()
      } else if (!Numbertype(value)) {
        callback(new Error('请输入1~3个数字'))
      } else {
        callback()
      }
    }
    return {
      // 填写内容信息数据
      form: {
        ...this.$route.query.data, // 企业名称
        jobTitle: '',
        isJob: '',
        sex: '',
        workAge: '',
        job: '',
        workResume: '',
        expertRemark: '',
        company: '',
        education: '',
        address: '',
        countyId: '',
        zipCode: '',
        school: '',
        provinceId: '',
        birth: '',
        expertClassifyStandard: '',
        expertClassifySelf: '',
        professionalQualifications: []
      },
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7
        }
      },
      // 职业资格数据提交
      qualificationsform: {
        name: '',
        grade: ''
      },
      dynamicTags: [],
      // 级联选择数据
      categoryOptions: expertClassify.expertClassify,
      selectedOptions: [],
      // 三级联动
      addressOptions: citydata.CityInfo,
      selectedcityOptions: [],
      // select 选择框
      options: [{
        value: '重要',
        label: '重要'
      }, {
        value: '一般',
        label: '一般'
      }
      ],
      // select 选择框
      job: [{
        value: 0,
        label: '否'
      }, {
        value: 1,
        label: '是'
      }],
      sexform: [{
        value: 0,
        label: '保密'
      },
      {
        value: 1,
        label: '男'
      },
      {
        value: 2,
        label: '女'
      }
      ],
      formLabelWidth: '120px',
      // 添加职业资格
      dialogFormVisible: false,
      // 提交时验证
      rules: {
        name: [
          { required: true, message: '专家姓名不能为空', trigger: 'blur' },
          { min: 1, max: 100, message: '请输入1~100个字符', trigger: ['blur', 'change'] }
        ],
        idNumber: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { required: true, trigger: 'blur', validator: validID }
        ],
        expertClassifyStandard: [
          { required: true, message: '请填写', trigger: 'blur' }
        ],
        mobile: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { required: true, trigger: 'blur', validator: validPhone }
        ],
        provinceId: [
          { required: true, message: '请填写', trigger: 'blur' }
        ],
        expertClassifySelf: [
          { required: true, message: '请填写', trigger: 'blur' }
        ],
        zipCode: [
          { required: false, message: '', trigger: 'blur' },
          {min: 1, max: 6, message: '请输入1~6个字符', trigger: ['blur', 'change']},
          { required: false, trigger: 'blur', validator: validZipcode }
        ],
        workAge: [
          { required: false, message: '', trigger: 'blur' },
          {min: 1, max: 3, message: '请输入1~2位整数', trigger: ['blur', 'change']},
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ]
      },
      zhiyerules: {
        name: [
          { required: true, message: '职业资格名称不能为空', trigger: 'blur' },
          {min: 1, max: 50, message: '请输入1~50个字符', trigger: ['blur', 'change']}
        ],
        grade: [
          { required: true, message: '职业资格等级不能为空', trigger: 'blur' },
          {min: 1, max: 50, message: '请输入1~50个字符', trigger: ['blur', 'change']}
        ]
      }
    }
  },
  methods: {
    addExpert (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.form.professionalQualifications = this.dynamicTags
          this.axios.post('expert-infos', this.form).then((res) => {
            checkResponse(this, res, () => {
              this.$router.push({path: '/expert'})
            })
          }, () => {
            this.$message.error('Not found')
          })
        } else {
          return false
        }
      })
    },
    quxiao () {
      this.$router.push({path: '/expert'})
    },
    // 删除
    handleClose (tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1)
    },
    // 级联选择
    handleChange (value) {
      this.form.expertClassifyStandard = this.selectedOptions.toString()
    },
    // 添加职业职格
    addzhiyezhige (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.dynamicTags.push(this.qualificationsform)
          this.dialogFormVisible = false
          this.qualificationsform = {}
        } else {
          return false
        }
      }
      )
    },
    quxiaobtn () {
      this.dialogFormVisible = false
      this.qualificationsform = {}
    },
    // 三级联动选择
    handlecityChange (value) {
      this.form.provinceId = value[0]
      this.form.cityId = value[1]
      this.form.countyId = value[2]
    }
  }
}
</script>
<style lang="less">
  #expertcontent {
    /*查看档案*/
    width: 1200px;
    background: #fff;
    min-height: 50px;
    margin: 0 auto;
    margin-top: 23px;
    .content_tiltebox {
      width: 100%;
      height: 60px;
      /* background: red; */
      line-height: 60px;
      padding: 0 20px;
      box-sizing: border-box;
      font-size: 15px;
    }
    .gsmczzt {
      color: #666666;
    }
    .nav_switchbox {
      width: 100%;
      background: #ebeffe;
      padding: 0 10px;
      box-sizing: border-box;
    }
    .el-menu-item {
      width: 97px;
      height: 47px;
      /* background: red; */
      float: left;
      margin-top: 5px;
      text-align: center;
      line-height: 47px;
      font-size: 14px;
      color: #111111;
      cursor: pointer;
    }
    .is-active {
      background: #fff;
      color: #3f63f6;
    }
    .ckdan_contentbox {
      padding: 0px 18px;
      box-sizing: border-box;
      margin-top: 22px;
    }
    .gdwjnrbox_contentbox {
      padding: 0px 18px;
      box-sizing: border-box;
      margin-top: 22px;
      /*background: red;*/
      display: none;
    }
    form {
      display: block;
      margin-top: 0em;
      width: 100%;
    }
    .el-form-item__label {
      width: 25% !important;;
      /* background: teal; */
      height: 100%;
      float: left;
      text-align: right;
      color: #666666;
      font-size: 14px;
      line-height: 40px;
    }
    .el-form-item__content {
      width: 75% !important;;
      /* background: yellow; */
      height: 100%;
      float: left;
      padding: 0 5px;
      box-sizing: border-box;
      font-size: 14px;
      margin-left: 0 !important;
      color: #666666 !important;
    }
    .el-input__inner {
      width: 100%;
      height: 40px;
      border: 1px solid #eeeeee;
      box-sizing: border-box;
      padding: 0 10px;
      color: #111111 !important;
      outline: none;
      background: transparent !important;
      text-align: left;
    }
    .bdlxtitle > .el-form-item__label {
      width: 17% !important;
    }
    .bdlxinput {
      width: 105px;
      height: 100%;
      /*border: 1px solid #eeeeee;*/
      color: #111111;
      float: left;
      margin-right: 16px;
      letter-spacing: 0px;
      text-align: center;
      line-height: 40px;
      font-size: 14px;
      box-sizing: border-box;
    }
    .sfwt_bdlxinput {
      width: 66% !important;
    }
    .bdneirong > .el-form-item__label {
      width: 12.5% !important;
    }
    .bdneirong > .el-form-item__content {
      width: 87.5% !important;
      height: 100px;
      word-wrap: break-word
    }
    .bdnrong_bdlxinput {
      height: 100%;
    }
    .bdnrong_bdlxinput > .el-input__inner {
      height: 100%;
      word-wrap: break-word
    }
    .bdnrong_bdlxinput > .el-textarea__inner {
      background: transparent !important;
      color: #111111 !important;
      resize: none;
    }
    /*新增归档样式*/
    .zbfs_bigbox {
      width: 130px;
    }
    .zzt_zbfs_selectbox {
      width: 100%;
    }
    .el-form-item__content > .el-textarea {
      height: 100%;
    }
    .el-form-item__content > .el-textarea > .el-textarea__inner {
      height: 100% !important;
      resize: none;
    }
    .jdxx_bigbox {
      width: 100%;
      min-height: 20px;
      margin-top: 30px;
      margin-bottom: 20px;
      text-align: center;
    }
    .jdxx_sonbox {
      display: inline-block;
      min-width: 50px;
      height: 30px;
      vertical-align: top;
      line-height: 30px;
      font-size: 14px;
      color: rgb(51, 51, 51);
    }
    .hengxian_box {
      display: inline-block;
      width: 105px;
      height: 30px;
      vertical-align: top;
      padding: 0px 15px;
    }
    .dangqian_jin {
      display: inline-block;
      width: 30px;
      height: 30px;
      vertical-align: top;
      color: rgb(255, 255, 255);
      text-align: center;
      line-height: 30px;
      margin-right: 5px;
      background: rgb(187, 187, 187);
      border-radius: 50%;
    }
    .hengxian {
      width: 100%;
      height: 2px;
      margin-top: 14px;
      background: rgb(187, 187, 187);
    }
    .dangqian_dangqian {
      background: rgb(63, 99, 246);
    }
    .gongneng_btnbox {
      text-align: center;
      padding-bottom: 30px;
    }
    .gongneng_btn {
      width: 120px;
    }
    .sfwtdl_btn {
      width: 66%;
    }
    .bdnrxxlb_tabbox > div > div > i {
      background: transparent !important;
    }
    /*查看*/
    .dabao_xiazai_btnbox {
      display: inline-block;
      width: 120px;
      height: 36px;
      background: #6582f8;
      color: #fff;
      font-size: 14px;
      text-align: center;
      line-height: 36px;
      cursor: pointer;
      margin-left: 5px;
    }
    .dabaooxia_icon {
      display: inline-block;
      width: 18px;
      vertical-align: top;
      margin-top: 9px;
    }
    .ckgdwjbox {
      padding-bottom: 30px;
    }
    .xxdzinput {
      width: 300px;
    }
    .xxdzinput input {
      width: 230px;
    }
    .zcdzfatherbox {
      width: 100%;
    }
    .bdlxtitle2 > label {
      width: 145px !important;
    }
    .bdlxtitle2 .el-select {
      display: inline-block;
      position: relative;
      width:130px;
    }
    .bdneirongzhiye .el-form-item__content{
      border: 1px solid #dcdfe6;
      border-radius: 5px;
    }
    .da_xzgd_btn {
      display: inline-block;
      width: 120px;
      height:36px;
      background: #6582f8;
      opacity: 0.8;
      color: #fff;
      font-size: 14px;
      text-align: center;
      vertical-align: middle;
      cursor: pointer;
      -webkit-transition: all .8s ease;
      transition: all .8s ease;
    }
    .el-form-item .el-form-item {
      margin-bottom: 35px;
    }
    .el-icon-date:before {
      content: "\E608";
      display: none;
    }
    .bdlxtitle2two{
      width: 100%;
    }
    .el-button {
      border-radius: 0;
      width: 110px!important;
      height: 35px;
      line-height: 35px;
      padding: 0;
    }
  }
</style>
