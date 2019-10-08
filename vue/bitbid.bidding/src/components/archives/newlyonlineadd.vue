<template>
  <div>
    <!--内容开始-->
    <div class="content_bigbox" id="content-add">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/archives' }">档案管理</el-breadcrumb-item>
        <el-breadcrumb-item v-if="$route.query.flangshow!=true" class="active_bread">新增归档</el-breadcrumb-item>
        <el-breadcrumb-item v-else class="active_bread">编辑归档</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <!--进度信息-->
      <div class="jdxx_bigbox">
        <div class="jdxx_sonbox">
          <div class="dangqian_jin dangqian_dangqian">1</div>
          <span>基本信息</span>
        </div>
        <div class="hengxian_box">
          <div class="hengxian"></div>
        </div>
        <div class="jdxx_sonbox">
          <div class="dangqian_jin">2</div>
          <span>归档文件</span>
        </div>
      </div>
      <!--进度信息-->
      <!--新增归档信息内容-->
      <div class="ckdan_contentbox">
        <el-row type="flex" class="row-bg">
          <el-form :model="form" :rules="rules" ref="form" status-icon class="demo-ruleForm">
            <el-col :span="12">
              <el-form-item  label="标段编号：" prop="sectionNumber" :label-width="formLabelWidth" class="zzt_zbfs_selectbox">
                <el-input v-model="form.sectionNumber" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <div class="grid-content bg-purple">
                <el-form-item  label="标段名称：" prop="sectionName" :label-width="formLabelWidth" class="zzt_zbfs_selectbox">
                  <el-input :title="form.sectionName" v-model="form.sectionName" readonly></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="12">
              <el-form-item label="招标项目编号：" prop='proTenderNumber' :label-width="formLabelWidth" class="zzt_zbfs_selectbox">
                <el-input v-model="form.proTenderNumber" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <div class="grid-content bg-purple">
                <el-form-item label="招标项目名称：" prop='proTenderName' :label-width="formLabelWidth" class="zzt_zbfs_selectbox">
                  <el-input v-model="form.proTenderName" readonly></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="24">
              <el-col :span="12">
                <el-form-item prop="sectionType" :model="form" label="标段类型："  :label-width="formLabelWidth" class="zzt_zbfs_selectbox">
                  <el-cascader
                    disabled
                    expand-trigger="hover"
                    :options="BidsType"
                    v-model="biaoduanType"
                    :title="biaoduanTypeToTitle"
                    @change="handleChange" readonly>
                  </el-cascader>
                </el-form-item>
              </el-col>
            </el-col>
            <el-col :span="24">
              <el-col :span="12">
                <div class="grid-content bg-purple">
                  <el-form-item :model="form" prop="bidsMethod" label="招标方式：" :label-width="formLabelWidth">
                    <el-select class="zzt_zbfs_selectbox" v-model="form.bidsMethod" placeholder="请选择招标方式" disabled>
                      <el-option
                        v-for="item in bidsMethodSelet"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        :disabled="item.disabled">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </div>
              </el-col>
              <el-col :span="12">
                <el-form-item v-show='form.bidsMethod=="0"' ref="inforOnMethod" label="资审方式：" prop="inforOnMethod" :label-width="formLabelWidth">
                  <el-select class="zzt_zbfs_selectbox" v-model="form.inforOnMethod" placeholder="请选择资审方式" disabled>
                    <el-option
                      v-for="item in inforOnMethodSelet"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                      :disabled="item.disabled">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-col>
            <el-col :span="24">
              <el-col :span="12">
                <div class="grid-content bg-purple">
                  <el-form-item label="招标人：" prop="bidder" :label-width="formLabelWidth">
                    <el-input v-model="form.bidder" readonly class="zzt_zbfs_selectbox"></el-input>
                  </el-form-item>
                </div>
              </el-col>
            </el-col>
            <el-col :span="24">
              <el-col :span="12">
                <el-form-item label="是否委托招标代理：" class="bdlxtitle" :label-width="formLabelWidth">
                  <el-select class="zzt_zbfs_selectbox" v-model="form.isEntrustAgency" placeholder="请选择是否委托" @change="isEntrustAgencySelect" disabled>
                    <el-option
                      v-for="item in isEntrustAgencySelet"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                      :disabled="item.disabled">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item v-show="form.isEntrustAgency=='1'" ref="agentStructure" label="代理机构：" prop="agentStructure" :label-width="formLabelWidth">
                  <el-input v-model="form.agentStructure" readonly class="zzt_zbfs_selectbox"></el-input>
                </el-form-item>
              </el-col>
            </el-col>
            <el-col :span="12">
              <div class="grid-content bg-purple">
                <el-form-item label="项目经理：" prop="projectManagerName" :label-width="formLabelWidth">
                  <el-select class="zzt_zbfs_selectbox"  v-model="form.projectManagerName" placeholder="请选择" disabled>
                    <el-input v-model="form.projectManagerName" readonly></el-input>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="12">
              <el-form-item label="所属部门：" prop="departmentName" :label-width="formLabelWidth">
                <el-select class="zzt_zbfs_selectbox"  v-model="form.departmentName" placeholder="请选择" disabled>
                  <el-input v-model="form.departmentName" readonly></el-input>
                </el-select>
              </el-form-item>
            </el-col>
            <div class="bdlx_box">
              <el-col :span="24">
                <el-form-item label="标段内容：" class="bdneirong" :label-width="formLabelWidth">
                  <el-input
                    type="textarea"
                    :autosize="{ minRows: 2, maxRows: 6}"
                    v-model="form.sectionContent" disabled>
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
        <el-button @click="postData('form','0')" class="gongneng_btn" type="primary">下一步</el-button>
        <el-button @click="postData('form','1')" class="gongneng_btn" type="primary">保 存</el-button>
        <el-button class="gongneng_btn" type="info" @click="cancelFun">取 消</el-button>
      </el-row>
      <!--按钮-->
    </div>
    <!--内容结束-->
  </div>
</template>
<script>
import * as constants from '../../assets/js/constants'
import { checkResponse } from '../../assets/js/common'
import {useradmin} from '../../api/index'
export default {
  data () {
    return {
      form: {
        sectionNumber: '', // 标段编号
        sectionName: '', // 标段名称
        proTenderNumber: '',
        proTenderName: '',
        sectionType: '',
        bidsMethod: '',
        inforOnMethod: '',
        isEntrustAgency: '',
        bidder: '',
        agentStructure: '',
        projectManager: '',
        department: '',
        sectionContent: '',
        projectManagerName: '',
        departmentName: '',
        saved: true
      },
      bidsMethodSelet: [{
        value: 0,
        label: '公开招标'
      }, {
        value: 1,
        label: '邀请招标'
      }],
      inforOnMethodSelet: [{
        value: 0,
        label: '资格后审'
      }, {
        value: 1,
        label: '资格预审'
      }],
      isEntrustAgencySelet: [{
        value: 0,
        label: '否'
      }, {
        value: 1,
        label: '是'
      }],
      departmentOpfrom: [],
      projectOpfrom: [],
      rules: {
        sectionNumber: [
          { required: true, message: '标段编号不能为空', trigger: 'blur' },
          { min: 1, max: 23, message: '长度在 1~23个字符', trigger: 'change' }
        ],
        sectionName: [
          { required: true, message: '标段名称不能为空', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur' }
        ],
        proTenderNumber: [
          { required: true, message: '招标项目编号不能为空', trigger: 'blur' },
          { min: 1, max: 23, message: '长度在 1~23个字符', trigger: 'change' }
        ],
        proTenderName: [
          { required: true, message: '招标项目名称不能为空', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur' }
        ],
        sectionType: [
          { required: true, message: '请选择标段类型', trigger: 'change' }
        ],
        bidsMethod: [
          { required: true, message: '请选择招标方式', trigger: 'change' }
        ],
        inforOnMethod: [
          { required: true, message: '请选择资审方式', trigger: 'change' }
        ],
        isEntrustAgency: [
          { required: true, message: '请选择是否委托招标代理', trigger: 'change' }
        ],
        bidder: [
          { required: true, message: '招标人名称不能为空', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
        ],
        agentStructure: [
          { required: true, message: '代理机构名称不能为空', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
        ],
        projectManagerName: [
          { required: true, message: '项目经理不能为空', trigger: 'blur' }
        ],
        departmentName: [
          { required: true, message: '所属部门不能为空', trigger: 'blur' }
        ]
      },
      formLabelWidth: '180px',
      addObject: '',
      biaoduanType: [],
      BidsType: constants.bidsType
    }
  },
  methods: {
    listData () {
      this.axios.get(`project-infos/bitbid/${this.$route.query.objectId}`).then((res) => {
        this.form = res.data.projectInformation
        this.biaoduanType = this.form.sectionType.split(',')
      })
    },
    postData (formName, flag) {
      if (!(this.form.isEntrustAgency == '1')) {
        delete this.form.agentStructure
        delete this.rules.agentStructure
        this.form.agentStructure = ''
      } else {
        this.rules.agentStructure = [
          { required: true, message: '代理机构名称不能为空', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
        ]
      }
      if (!(this.form.bidsMethod == '0')) {
        this.rules.inforOnMethod = [
          { required: false, message: '请选择资审方式', trigger: 'change' }
        ]
        this.form.inforOnMethod = ''
      } else {
        this.rules.inforOnMethod = [
          { required: true, message: '请选择资审方式', trigger: 'change' }
        ]
      }
      this.form.enterpriseId = this.$store.getters.authUser.enterpriseId
      this.form.creator = this.$store.getters.authUser.userId
      // 项目经理id
      this.form.projectManager = this.projectOpfrom[0].value
      // 项目经理名称
      this.form.projectManagerName = this.projectOpfrom[0].label
      // 部门id
      this.form.department = this.departmentOpfrom[0].value
      // 部门名称
      this.form.departmentName = this.departmentOpfrom[0].label
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.addObject === '') {
            this.form.objectId = this.$route.query.objectId
          } else {
            this.form.objectId = this.addObject
          }
          this.form.saved = true
          this.axios.get(`project-infos/bitbid/isexist/${this.$route.query.objectId}`).then((res) => {
            if (res.data.projectInformation === null) {
              this.axios.post('project-infos/bitbid', this.form).then((res) => {
                if (flag == 0) {
                  this.addObject = res.data.projectInformation.objectId
                  this.$router.push({path: '/archives/archived-file', query: {objectId: this.addObject, bitbidObjectId: res.data.projectInformation.bitbidObjectId, btn: true, inforOnMethod: this.form.inforOnMethod}})
                } else {
                  this.$router.push({path: '/archives'})
                  checkResponse(this, res, () => {
                    this.addObject = res.data.projectInformation.objectId
                  })
                }
              })
            } else {
              if (flag == 0) {
                this.addObject = res.data.projectInformation.objectId
                this.$router.push({path: '/archives/archived-file', query: {objectId: this.addObject, bitbidObjectId: res.data.projectInformation.bitbidObjectId, btn: true, inforOnMethod: this.form.inforOnMethod}})
              } else {
                this.$router.push({path: '/archives'})
                checkResponse(this, res, () => {
                  this.addObject = res.data.projectInformation.objectId
                })
              }
            }
          })
        }
      })
    },
    cancelFun () {
      this.$router.push({path: '/archives'})
    },
    // 代理机构选择
    isEntrustAgencySelect (val) {
      if (val == 0) {
        this.form.agentStructure = ''
      }
    },
    // 上一步数据展示
    formData () {
      this.axios.get(`project-infos/${this.$route.query.objectId}`).then((res) => {
        this.form = res.data.projectInformation
        this.biaoduanType = this.form.sectionType.split(',')
        this.form.isEntrustAgency = this.form.isEntrustAgency.toString()
        this.form.bidsMethod = this.form.bidsMethod.toString()
      })
    },
    // 部门和项目经理
    deptManager () {
      var managerObj = {
        value: '',
        label: ''
      }
      var deptObj = {
        value: '',
        label: ''
      }
      useradmin.deptManagerlistbibi(this.$store.getters.authUser.userId).then((res) => {
        managerObj.value = res.data.user.id
        managerObj.label = res.data.user.nameZH
        this.projectOpfrom.push(managerObj)
        deptObj.value = res.data.user.dept.id
        deptObj.label = res.data.user.dept.name
        this.departmentOpfrom.push(deptObj)
      })
    },
    handleChange (value) {
      this.form.sectionType = this.biaoduanType.toString()
    }
  },
  watch: {
    form: {
      deep: true,
      handler (newVal) {
        if (newVal.bidsMethod == '1') {
          // newVal.inforOnMethod = ''
          this.$refs.inforOnMethod.resetField()
        }
        if (newVal.isEntrustAgency == '0') {
          this.$refs.agentStructure.resetField()
        }
      }
    }
  },
  computed: {
    biaoduanTypeToTitle () {
      var title = ''
      constants.bidsType.map((item, index) => {
        if (item.value == this.biaoduanType[0]) {
          title += item.label + '/'
          item.children.map((item2, index) => {
            if (item2.value == this.biaoduanType[1]) {
              title += item2.label + '/'
              item2.children.map((item3) => {
                if (item3.value == this.biaoduanType[2]) {
                  title += item3.label + '/'
                  item3.children.map((item4) => {
                    if (item4.value == this.biaoduanType[3]) {
                      title += item4.label
                    }
                  })
                }
              })
            }
          })
        }
      })
      return title
    }
  },
  mounted () {
    if (this.$route.query.flangshow) {
      this.formData()
    }
    this.deptManager()
  },
  created () {
    this.listData()
  }
}
</script>
<style lang="less">
  #content-add {
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
    .el-button--primary {
      background-color: #409eff!important;
      border-color: #409eff!important;
    }
    .nav_switchbox {
      width: 100%;
      background: #ebeffe;
      padding: 0 10px;
      box-sizing: border-box;
    }
    .el-button{
      border-radius: 0;
      width: 110px!important;
      height: 35px;
      line-height: 35px;
      padding: 0;
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
    .el-cascader{
      display: block;
      width: 100%
    }
    .el-form-item__label {
      /*width: 25% !important;;*/
      /* background: teal; */
      height: 100%;
      float: left;
      text-align: right;
      color: #666666;
      font-size: 14px;
      line-height: 40px;
    }
    .el-form-item__content {
      width: 68% !important;;
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
    .zzt_zbfs_selectbox .el-input__inner {
      color: #94969a !important;
    }
    .el-form-item__error{
      padding-left: 10px
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
      width: 100% !important;
    }
    .bdneirong > .el-form-item__content {
      width: 84.5% !important;
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
      width: 100%;
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
    .el-cascader-menu__item--extensible:after{
      font-family: element-icons;
      content: "\E604";
      font-size: 14px;
      color: #bfcbd9;
      position: absolute;
      right: 5px!important;
    }
    .el-form-item {
      margin-bottom: 0;
      padding-bottom: 20px;
    }
    .el-form-item.is-success .el-input__validateIcon {
      color: transparent;
    }
  }
</style>
