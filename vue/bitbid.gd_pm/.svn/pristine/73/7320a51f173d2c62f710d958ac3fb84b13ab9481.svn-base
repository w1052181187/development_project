<template>
  <div class="center_box contract-add-box" id="enterprise_box">
      <div class="coninfor_box">
        <div class="coninfor_title">企业信息</div>
        <div class="coninfor_con">
          <div class="infor_side_conbigbox">
            <!--审核通过-->
            <el-form :model="projectForm" :rules="rules" ref="projectForm" label-width="140px" class="demo-ruleForm" v-if="!isAudit">
              <div class="dialog_cancel_infoson_father">
                <div class="dialog_cancel_infoson_title">
                  <div class="dialog_cancel_infoson_titlewenzi">企业信息</div>
                  <div class="dialog_cancel_infoson_titlexian"></div>
                </div>
                <div class="dialog_detail">
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="企业名称：" prop="namezh">
                         <span>{{projectForm.namezh}}</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row :gutter="20">
                    <el-col :span="12">
                      <el-form-item label="注册地址：" prop="province">
                        <el-cascader
                          class="industry_box"
                          expand-trigger="hover"
                          :options="addressOptions"
                          v-model="selectedcityOptions"
                          @change="handlecityChange" disabled>
                        </el-cascader>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="12">
                      <el-form-item prop="address" label="详细地址：">
                        <!--<el-input placeholder="请输入详细地址" v-model="projectForm.zucheAddress"></el-input>-->
                        <span>{{projectForm.zucheAddress}}</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="所属行业：" prop="industryCategory">
                        <el-cascader
                          class="industry_box"
                          expand-trigger="hover"
                          :options="industryOptions"
                          v-model="selecIndustry"
                          @change="selectIndusChange" disabled>
                        </el-cascader>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <div class="dialog_cancel_infoson_father">
                <div class="dialog_cancel_infoson_title">
                  <div class="dialog_cancel_infoson_titlewenzi">联系人信息</div>
                  <div class="dialog_cancel_infoson_titlexian"></div>
                </div>
                <div class="dialog_detail">
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="姓名：" prop="lianxiren1">
                        <!--<el-input placeholder="请输入姓名" v-model="projectForm.lianxiren1"></el-input>-->
                        <span>{{projectForm.lianxiren1}}</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="24">
                      <el-form-item label="移动电话：" prop="lianxiren1Yidongdianhua">
                        <!--<el-input placeholder="请输入移动电话" v-model="projectForm.lianxiren1Yidongdianhua"></el-input>-->
                        <span>{{projectForm.lianxiren1Yidongdianhua}}</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="24">
                      <el-form-item label="电子邮箱：" prop="lianxiren1Email">
                        <!--<el-input placeholder="请输入电子邮箱" v-model="projectForm.lianxiren1Email"></el-input>-->
                        <span>{{projectForm.lianxiren1Email}}</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <!--<el-form-item class="operation_btn_box">-->
                <!--<el-button type="primary" @click="submitForm('projectForm')" class="save_btn" :loading="subStatus">提交</el-button>-->
              <!--</el-form-item>-->
            </el-form>
            <!--审核通过-->
            <!--审核中-->
            <div class="audit-bigbox" v-else>
              <img src="../../../../static/img/audit.png" alt="">
            </div>
            <!--审核中-->
          </div>
        </div>
      </div>
  </div>
</template>

<script>
import {isvalidPhone} from '@/assets/js/validate'
import * as cityData from '@/assets/js/region'
import * as induData from '@/assets/js/industry'
import {enterprise} from '@/api'
export default {
  name: '',
  data () {
    // 手机号
    var validNumber = (rule, value, callback) => {
      if (value === '' || value === undefined || value === null) {
        callback()
      } else if (!isvalidPhone(value)) {
        callback(new Error('请输入正确的格式'))
      } else {
        callback()
      }
    }
    return {
      projectForm: {}, // 企业信息
      subStatus: false, // 是否保存
      // 表单验证
      rules: {
        namezh: [
          { required: true, message: '请输入企业名称', trigger: ['blur', 'change'] },
          {min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: ['blur', 'change']}
        ],
        province: [
          { required: true, message: '请选择注册地址', trigger: ['blur', 'change'] }
        ],
        zucheAddress: [
          { required: true, message: '请输入详细地址', trigger: ['blur', 'change'] },
          {min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: ['blur', 'change']}
        ],
        industryCategory: [
          { required: true, message: '请选择所属行业', trigger: ['blur', 'change'] }
        ],
        lianxiren1: [
          { required: true, message: '请输入姓名', trigger: ['blur', 'change'] },
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: ['blur', 'change']}
        ],
        lianxiren1Yidongdianhua: [
          { required: true, message: '请输入手机号', trigger: ['blur', 'change'] },
          { trigger: ['blur', 'change'], validator: validNumber }
        ],
        lianxiren1Email: [
          { required: false, message: '请输入邮箱地址', trigger: 'blur' },
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: ['blur', 'change']},
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ]
      },
      // 三级联动
      addressOptions: cityData.CityInfo,
      selectedcityOptions: [],
      // 所属行业
      industryOptions: induData.industry,
      selecIndustry: [],
      isAudit: false
    }
  },
  methods: {
    // 查询企业信息
    queryEnterprise () {
      enterprise.enterpriseInfo(this.$store.getters.authUser.userId).then((res) => {
        if (res.data.resCode === '0000') {
          if (res.data.sysUserExpand) {
            this.projectForm = res.data.sysUserExpand
          }
          if (res.data.sysUserExpand !== null && res.data.sysUserExpand !== undefined) {
            if (res.data.sysUserExpand.status !== null) {
              if (res.data.sysUserExpand.status === 4) {
                this.isAudit = true
              } else {
                this.isAudit = false
              }
            }
            this.selectedcityOptions = [Number(this.projectForm.province), Number(this.projectForm.city), Number(this.projectForm.district)]
            this.selecIndustry = [this.projectForm.industryCategories, this.projectForm.industryCategory]
          }
          this.$set(this.projectForm, 'namezh', res.data.sysUser.namezh)
        }
      })
    },
    // 添加企业信息
    submitForm (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.projectForm.status = 4
          this.projectForm.userid = this.$store.getters.authUser.userId
          enterprise.enterpriseUpdate(this.projectForm).then((res) => {
            this.queryEnterprise()
          })
        } else {
          return false
        }
      })
    },
    // 三级联动选择
    handlecityChange (value) {
      this.projectForm.province = value[0]
      this.projectForm.city = value[1]
      this.projectForm.district = value[2]
    },
    selectIndusChange () {
      let industry = this.selecIndustry.toString().split(',')
      this.projectForm.industryCategories = industry[0]
      this.projectForm.industryCategory = industry[1]
    }
  },
  mounted () {
    // 查询企业信息
    this.queryEnterprise()
  }
}
</script>
<style lang="less">
  #enterprise_box{
    .contract-add-box{
      .industry_box{
        width: 100%;
      }
      .contract_bigbox{
        width: 100%;
      }
      .shanchun_icon{
        img{
          width: 20px;
          height: 20px;
        }
      }
      .el-date-editor.el-range-editor{
        .el-range-input{
          height: 30px;
        }
      }
    }
    .el-cascader-panel {
      display: block;
      .el-scrollbar {
        float: left;
      }
    }
    .dialog_detail{
      width: 60%;
    }
    .audit-bigbox{
      width: 100%;
      height: 75vh;
      position: relative;
    }
    .audit-bigbox>img{
      position: absolute;
      top:0;
      bottom: 0;
      left: 0;
      right: 0;
      margin: auto;
    }
    .industry_box{
      width: 100%;
    }
    .industry_box .el-input__inner{
      background-color: transparent;
      border-color: transparent;
      color: #000000;
    }
    .industry_box  .el-cascader__label{
      color: #000000;
    }
    .industry_box  .el-input__suffix{
      display: none;
    }
  }
</style>
