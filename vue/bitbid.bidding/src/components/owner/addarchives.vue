<template>
  <div>
    <!--内容开始-->
    <div class="content_bigbox" id="zztcontent">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/owner' }">业主管理</el-breadcrumb-item>
        <el-breadcrumb-item class="active_bread">新增业主</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <!--新增归档信息内容-->
      <div class="ckdan_contentbox">
        <el-row type="flex" class="row-bg" style="overflow: hidden">
          <el-form :model="form" :rules="rules" ref="form">
            <el-col :span="24">
              <el-col :span="12">
                <el-form-item label="业主名称：" prop="enterpriseName" :label-width="formLabelWidth">
                  <el-input v-model="form.enterpriseName"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <div class="grid-content bg-purple">
                  <el-form-item label="企业证件号码：" prop="enterpriseIdNumber" :label-width="formLabelWidth" :rules="[
                { required: false, message: '', trigger: 'blur' },
                {min: 1, max: 100, message: '请输入1~100个字符',trigger: ['blur', 'change'] }
              ]">
                    <el-input v-model="form.enterpriseIdNumber"></el-input>
                  </el-form-item>
                </div>
              </el-col>
            </el-col>
            <el-col :span="24">
              <el-col :span="12">
                <el-form-item label="法人姓名：" prop="legalPersonName"  :label-width="formLabelWidth" :rules="[
                { required: false, message: '', trigger: 'blur' },
                {min: 1, max: 100, message: '请输入1~100个字符',trigger: ['blur', 'change'] }
              ]">
                  <el-input v-model="form.legalPersonName"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <div class="grid-content bg-purple">
                  <el-form-item label="法人身份证号：" prop="legalPersonId" :label-width="formLabelWidth">
                    <el-input v-model="form.legalPersonId"></el-input>
                  </el-form-item>
                </div>
              </el-col>
            </el-col>
            <el-col :span="24">
              <el-col :span="12">
                <div class="grid-content bg-purple">
                  <el-form-item label="注册地址：" :label-width="formLabelWidth">
                    <el-cascader class="zcdz"
                                 expand-trigger="hover"
                                 :options="addressOptions"
                                 v-model="selectedOptions"
                                 @change="handleChange">
                    </el-cascader>
                  </el-form-item>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="grid-content bg-purple">
                  <el-form-item label="详细地址：" prop="address"   :label-width="formLabelWidth" :rules="[
                  { required: false, message: '', trigger: 'blur' },
                  { min: 1, max: 200, message: '请输入1~200个字符', trigger: ['blur', 'change']}
                ]">
                    <el-input v-model="form.address" v-bind:title="form.address"></el-input>
                  </el-form-item>
                </div>
              </el-col>
            </el-col>
            <el-col :span="24">
              <el-col :span="12">
                <el-form-item label="联系人：" prop="contactName" :label-width="formLabelWidth">
                  <el-input v-model="form.contactName"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <div class="grid-content bg-purple">
                  <el-form-item label="手机号：" prop="contactPhone" :label-width="formLabelWidth">
                    <el-input v-model="form.contactPhone"></el-input>
                  </el-form-item>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <div class="grid-content bg-purple">
                <el-form-item label="电子邮件：" prop="email" :label-width="formLabelWidth"
                :rules="[
                  { required: false, message: '请输入邮箱地址', trigger: 'blur' },
                  { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                ]">
                  <el-input v-model="form.email"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <div class="bdlx_box">
              <el-col :span="18">
                <el-form-item label="业主分类：" class="bdlxtitle" :label-width="formLabelWidth">
                  <el-select class="zzt_zbfs_selectbox sfwtdl_btn" v-model="form.ownerClassify" placeholder="请选择">
                    <el-option
                      v-for="item in options"
                      :key="item.classifyName"
                      :label="item.classifyName"
                      :value="item.classifyName">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </div>
            <div class="bdlx_box">
              <el-col :span="24">
                <el-form-item label="业主备注：" class="bdneirong" :label-width="formLabelWidth">
                  <el-input
                    type="textarea"
                    :autosize="{ minRows: 2, maxRows: 6}"
                    placeholder=""
                    v-model="form.remark">
                  </el-input>
                </el-form-item>
              </el-col>
            </div>
          </el-form>
        </el-row>
        <!--按钮-->
        <el-row class="gongneng_btnbox">
          <el-button class="gongneng_btn" type="primary" @click="addOwner('form')">
            添加
          </el-button>
          <el-button class="gongneng_btn" type="info" @click="cancelFun">
            取消
          </el-button>
        </el-row>
        <!--按钮-->
      </div>
      <!--新增归档信息内容-->
    </div>
    <!--内容结束-->
  </div>
</template>
<script>
import {isvalidPhone, isvalidId, Spacetype} from '../../assets/js/validate'
import {checkResponse} from '../../assets/js/common'
import * as citydata from '../../assets/js/region'
export default {
  data () {
    // 身份证验证
    var validID = (rule, value, callback) => {
      if (value === '') {
        callback()
      } else if (!isvalidId(value)) {
        callback(new Error('请输入正确的18位身份证号码'))
      } else {
        callback()
      }
    }
    // 电话号码
    var validPhone = (rule, value, callback) => {
      if (value === '' || value === undefined || value === null) {
        callback()
      } else if (!isvalidPhone(value)) {
        callback(new Error('请输入正确的11位手机号码'))
      } else {
        callback()
      }
    }
    // 空格验证
    var Space = (rule, value, callback) => {
      if (!value) {} else if (Spacetype(value)) {
        callback(new Error('不能输入空格'))
      } else {
        callback()
      }
    }
    return {
      // 填写内容信息数据
      form: {
        ...this.$route.query.data, // 企业名称
        enterpriseIdNumber: '',
        legalPersonName: '',
        contactName: '',
        remark: '',
        ownerClassify: '',
        email: '',
        contactPhone: '',
        legalPersonId: '',
        provinceId: '',
        cityId: '',
        countyId: '',
        address: '',
        status: '1'
      },
      // select 选择框
      options: [],
      formLabelWidth: '120px',
      addressOptions: citydata.CityInfo,
      selectedOptions: [],
      // 提交时验证
      rules: {
        enterpriseName: [
          { required: true, message: '业主名称不能为空', trigger: 'blur' },
          { min: 1, max: 200, message: '请输入1~200个字符', trigger: ['blur', 'change'] }
        ],
        contactName: [
          { required: true, message: '联系人不能为空', trigger: 'blur' },
          { min: 1, max: 100, message: '请输入1~100个字符', trigger: ['blur', 'change'] },
          {required: true, validator: Space, trigger: ['blur', 'change']}
        ],
        legalPersonId: [
          { validator: validID, trigger: 'blur' }
        ],
        contactPhone: [
          { trigger: 'blur', validator: validPhone }
        ]
      }
    }
  },
  methods: {
    // 分类数据获取
    selectClassify () {
      this.axios.get(`classify-infos/getClassify?relatedModuleType=${1}&enterpriseId=${this.$store.getters.authUser.ownerUserId}&_t=${new Date().getTime()}`).then((res) => {
        this.options = res.data.classifyInfos
      })
    },
    addOwner (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.form.enterpriseId = this.$store.getters.authUser.ownerUserId
          this.form.creator = this.$store.getters.authUser.userId
          this.axios.post('owner-infos', this.form).then((res) => {
            checkResponse(this, res, () => {
              this.$router.push({path: '/owner'})
            })
          })
        } else {
          return false
        }
      })
    },
    cancelFun () {
      this.$router.push({path: '/owner'})
    },
    // 三级联动选择
    handleChange (value) {
      this.form.provinceId = value[0]
      this.form.cityId = value[1]
      this.form.countyId = value[2]
    }
  },
  mounted () {
    this.selectClassify()
  }
}
</script>
<style lang="less">
  #zztcontent {
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
    .bdlxtitle2 > div {
      width: 80% !important;
    }
    .zcdz{
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
