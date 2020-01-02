<template>
  <div class="enterInfo" id="certificated">
    <p class="tips" v-if="isEdit">
      <span>编辑状态下，离开此标签未保存的信息将不会被保存</span>
      <el-button class="handleBtn" type="primary" @click="save('enterpriseForm')">提交审核</el-button>
    </p>
    <!--审核成功 不显示；审核失败显示失败原因及重新修改按钮(待审核，审核中不能提交)-->
    <p class="tips" v-else>
      <span v-if="enterpriseForm.status === 3">审核失败原因</span>
      <el-button class="handleBtn" type="primary" v-if="enterpriseForm.status === 0 || enterpriseForm.status === 2 || enterpriseForm.status === 3" @click="isEdit = true">修改信息</el-button>
    </p>
    <el-form :model="enterpriseForm" :rules="rules" ref="enterpriseForm" label-width="120px" :validate-on-rule-change="true">
      <div class="mainContent">
        <h3 class="title">企业基本信息</h3>
        <div class="formmain">
          <el-row :gutter="60">
            <el-col :span="12">
              <el-form-item label="企业名称" prop="name">
                <el-input v-if="isEdit && enterpriseForm.status !== 2" v-model="enterpriseForm.name" placeholder="请输入企业名称"></el-input>
                <span v-else>{{enterpriseForm.name}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="统一信用代码" prop="uscCode">
                <el-input  v-if="isEdit && enterpriseForm.status !== 2" v-model="enterpriseForm.uscCode" clearable placeholder="请输入统一信用代码"></el-input>
                <span v-else>{{enterpriseForm.uscCode}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="60">
            <el-col :span="12">
              <el-form-item label="法定代表人" prop="businessLicenseName">
                <el-input v-if="isEdit && enterpriseForm.status !== 2" v-model="enterpriseForm.businessLicenseName" placeholder="请输入法定代表人"></el-input>
                <span v-else>{{enterpriseForm.businessLicenseName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="法人身份证号" prop="businessLicenseCardNo">
                <el-input v-if="isEdit && enterpriseForm.status !== 2" v-model="enterpriseForm.businessLicenseCardNo" clearable placeholder="请输入法人身份证号"></el-input>
                <span v-else>{{enterpriseForm.businessLicenseCardNo}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="60">
            <el-col :span="12">
              <el-form-item label="企业性质" prop="characterId">
                <el-select v-if="isEdit && enterpriseForm.status !== 2" v-model="enterpriseForm.characterId" placeholder="请选择企业性质">
                  <el-option
                    v-for="item in characterIdOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
                <span v-else>{{characterValue}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="所属行业" prop="industry" >
                <el-cascader
                  expand-trigger="hover"
                  :options="industryOptions"
                  v-model="selectedIndustryOptions"
                  @change="handleIndustryChange"
                  clearable
                  v-if="isEdit && enterpriseForm.status !== 2">
                </el-cascader>
                <span v-else>{{industryValue}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="60">
            <el-col :span="12">
              <el-form-item label="所在地区" prop="address.privinceId" clearable>
                <el-cascader v-if="isEdit && enterpriseForm.status !== 2" expand-trigger="hover"
                             :options="addressOptions"
                             v-model="selectedcityOptions"
                             @change="handlecityChange"
                             clearable>
                </el-cascader>
                <span v-else>{{addressValue}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="详细地址" prop="address.addr">
                <el-input v-if="isEdit && enterpriseForm.status !== 2" v-model="enterpriseForm.address.addr" placeholder="请输入详细地址"></el-input>
                <span v-else>{{enterpriseForm.address.addr}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <!--              <el-row :gutter="60">-->
          <!--                <el-col :span="12">-->
          <!--                  <el-form-item label="供货类型" prop="supplyType">-->
          <!--                    <el-select v-model="enterpriseForm.supplyType" placeholder="请选择供货类型" clearable>-->
          <!--                      <el-option label="代理商" value="1"></el-option>-->
          <!--                      <el-option label="制造商" value="2"></el-option>-->
          <!--                    </el-select>-->
          <!--                  </el-form-item>-->
          <!--                </el-col>-->
          <!--              </el-row>-->
        </div>
      </div>
      <div class="mainContent">
        <h3 class="title">企业证件</h3>
        <div class="formmain">
          <el-row>
            <el-col :span="24">
              <el-form-item v-if="isEdit && enterpriseForm.status !== 2" label="营业执照" prop="fileValue1" class="certificate bitianicon">
                <img v-if="fileInformations[0]" :src="fileReviewUrl + fileInformations[0].relativePath"/>
                <uploadFile :class="$route.meta.layout === 'purchaser'? 'blueUpload' : 'greenUpload'"  @uploadSuccess="uploadSuccess" @deleteSuccess="deleteSuccess" fileType="1" :fileArrays="fileInformations" isImage="1"></uploadFile>
                <span>提示：请上传JPG/PNG/GIF格式图片，要求内容清晰</span>
              </el-form-item>
              <el-form-item v-else label="营业执照" prop="fileValue1" class="certificate bitianicon">
                <img v-if="fileInformations[0]" :src="fileReviewUrl + fileInformations[0].relativePath"/>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </div>
      <div class="mainContent">
        <h3 class="title">联系人信息</h3>
        <div class="formmain">
          <el-row :gutter="60">
            <el-col :span="12">
              <el-form-item label="联系人名称" prop="contactName">
                <el-input v-if="isEdit" v-model="enterpriseForm.contactName" clearable placeholder="请输入联系人名称"></el-input>
                <span v-else>{{enterpriseForm.contactName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="手机号码" prop="contactCellphone">
                <el-input  v-if="isEdit" v-model="enterpriseForm.contactCellphone" clearable placeholder="请输入手机号码"></el-input>
                <span v-else>{{enterpriseForm.contactCellphone}}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </div>
      <div class="checkStatus">
        <img v-if="enterpriseForm.status === 5" src="../../../static/images/enterprise/checking.png"/>
        <img v-if="enterpriseForm.status === 2" src="../../../static/images/enterprise/checked.png"/>
        <img v-if="enterpriseForm.status === 3" src="../../../static/images/enterprise/unchecked.png"/>
      </div>
    </el-form>
  </div>
</template>

<script>
import uploadFile from '@/components/upload/publicFileUpload'
import * as region from '@/assets/js/region'
import * as industry from '@/assets/js/industry_three'
import {validateMobilePhone, validateIdCard} from '@/assets/js/validate'
import {enterprise} from '@/api/system'
import {fileReviewUrl} from '@/assets/js/common'
export default {
  name: 'certificated',
  components: {
    uploadFile
  },
  data () {
    // 电话号码
    let validPhoneUser = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入联系电话'))
      } else if (!validateMobilePhone(value)) {
        callback(new Error('请输入正确的11位手机号码'))
      } else {
        callback()
      }
    }
    // 文件
    let validateFile = (rule, value, callback) => {
      let fileValue = this.enterpriseForm[rule.field]
      if (!fileValue) {
        callback(new Error('文件不能为空'))
      } else {
        callback()
      }
    }
    // 身份证号
    let validIdCard = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入法人身份证号'))
      } else if (!validateIdCard(value)) {
        callback(new Error('请输入正确的法人身份证号'))
      } else {
        callback()
      }
    }
    return {
      // 判断编辑、查看状态 ；默认应为查看状态，但是未认证时，为编辑状态
      isEdit: false,
      enterpriseForm: {
        name: '',
        uscCode: '',
        address: {
          privinceId: '',
          cityId: '',
          countyId: '',
          addr: ''
        },
        characterId: null,
        supplyType: '',
        industry: '',
        industrySecond: '',
        industryThird: '',
        contactName: '',
        contactCellphone: ''
      },
      characterIdOptions: [
        {
          value: 1,
          label: '国有企业'
        },
        {
          value: 2,
          label: '集体企业'
        },
        {
          value: 3,
          label: '有限责任公司'
        },
        {
          value: 4,
          label: '股份有限公司'
        },
        {
          value: 5,
          label: '私营企业'
        },
        {
          value: 6,
          label: '中外合资企业'
        },
        {
          value: 7,
          label: '外商投资企业'
        },
        {
          value: 8,
          label: '其他'
        }
      ],
      // 地址三级联动
      addressOptions: region.CityInfo,
      selectedcityOptions: [],
      // 行业三级联动
      industryOptions: industry.industry,
      selectedIndustryOptions: [],
      rules: {
        name: [
          { required: true, message: '企业名称不能为空', trigger: 'blur' }
        ],
        uscCode: [
          { required: true, message: '统一信用代码不能为空', trigger: 'blur' }
        ],
        businessLicenseName: [
          { required: true, message: '法定代表人不能为空', trigger: 'blur' }
        ],
        businessLicenseCardNo: [
          { required: true, message: '法人身份证号不能为空', trigger: 'blur' },
          {validator: validIdCard, trigger: 'blur'}
        ],
        characterId: [
          { required: true, message: '企业性质不能为空', trigger: ['blur', 'change'] }
        ],
        industry: [
          { required: true, message: '所属行业不能为空', trigger: ['blur', 'change'] }
        ],
        'address.privinceId': [
          { required: true, message: '所在地区不能为空', trigger: ['blur', 'change'] }
        ],
        contactName: [
          { required: true, message: '联系人不能为空', trigger: 'blur' }
        ],
        contactCellphone: [
          { required: true, message: '手机号码不能为空', trigger: 'blur' },
          {validator: validPhoneUser, trigger: 'blur'}
        ],
        fileValue1: [
          {validator: validateFile, trigger: 'change'}
        ]
      },
      fileInformations: [],
      industryValue: '', // 行业值
      characterValue: '', // 企业性质
      addressValue: '', // 地址
      fileReviewUrl: fileReviewUrl // 图片展示路径
    }
  },
  methods: {
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
    },
    // 上传文件
    uploadSuccess (file, fileType) {
      this.fileInformations = this.fileInformations.filter(item => Number(item.businessType) !== Number(fileType))
      this.fileInformations.push(file)
      this.$set(this.enterpriseForm, `fileValue${fileType}`, fileType)
      this.$refs['enterpriseForm'].validateField(`fileValue${fileType}`)
    },
    // 删除文件
    deleteSuccess (fileId, fileType) {
      this.fileInformations = this.fileInformations.filter(item => item.relativePath !== fileId)
      this.$set(this.enterpriseForm, `fileValue${fileType}`, '')
      this.$refs['enterpriseForm'].validateField(`fileValue${fileType}`)
    },
    // 提交企业认证
    saveEnterprise (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$confirm('确认要提交吗?', '提示', {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.loadingFlag = true
            this.enterpriseForm.status = 1
            this.enterpriseForm.fileInformations = this.fileInformations
            enterprise.update(this.enterpriseForm).then((res) => {
              this.loadingFlag = false
              this.$router.push({path: '/purchaser'})
            })
          }).catch(() => {
            this.loadingFlag = false
            return false
          })
        }
      })
    },
    save (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$confirm('确认要提交吗?', '提示', {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.enterpriseForm.status = 1
            this.enterpriseForm.fileInformations = this.fileInformations
            enterprise.update(this.enterpriseForm).then((res) => {
              this.isEdit = false
              this.$store.commit('SET_ENTERPRISE_SAVE', new Date().getTime())
            })
          }).catch(() => {
            this.loadingFlag = false
            return false
          })
        }
      })
    },
    detail () {
      let enterpriseId = this.$store.getters.authUser.enterpriseId
      enterprise.queryOne(enterpriseId).then((res) => {
        this.enterpriseForm = res.data.enterprise
        this.fileInformations = res.data.fileInformations || []
        if (this.fileInformations.length) {
          this.fileInformations.forEach(item => { this.enterpriseForm[`fileValue${item.businessType}`] = item.businessType })
        }
        if (this.enterpriseForm.address && this.enterpriseForm.address.privinceId) {
          this.selectedcityOptions.push(this.enterpriseForm.address.privinceId)
          this.selectedcityOptions.push(this.enterpriseForm.address.cityId)
          this.selectedcityOptions.push(this.enterpriseForm.address.countyId)
        }
        if (this.enterpriseForm.industry) {
          this.selectedIndustryOptions.push(this.enterpriseForm.industry)
          this.selectedIndustryOptions.push(this.enterpriseForm.industrySecond)
          this.selectedIndustryOptions.push(this.enterpriseForm.industryThird)
        }
        if (!this.isEdit) {
          // 查看状态下读取行业值
          this.setIndustryValue()
          // 企业性质
          this.setCharacterValue()
          // 所在地区
          this.setAddressValue()
        }
      })
    },
    setIndustryValue () {
      if (!this.enterpriseForm.industry) {
        return
      }
      this.industryOptions.forEach((first, index) => {
        if (first.value === this.enterpriseForm.industry) {
          this.industryValue += first.label
          if (first.children) {
            first.children.forEach((second, index) => {
              if (second.value === this.enterpriseForm.industrySecond) {
                this.industryValue += '/' + second.label
                if (second.children) {
                  second.children.forEach((third, index) => {
                    if (third.value === this.enterpriseForm.industryThird) {
                      this.industryValue += '/' + third.label
                    }
                  })
                }
              }
            })
          }
        }
      })
    },
    setCharacterValue () {
      if (!this.enterpriseForm.characterId) {
        return
      }
      this.characterIdOptions.forEach((item) => {
        if (item.value === this.enterpriseForm.characterId) {
          this.characterValue = item.label
        }
      })
    },
    setAddressValue () {
      if (!this.enterpriseForm.address || !this.enterpriseForm.address.privinceId) {
        return
      }
      this.addressOptions.forEach((first, index) => {
        if (first.value === this.enterpriseForm.address.privinceId) {
          this.addressValue += first.label
          if (first.children) {
            first.children.forEach((second, index) => {
              if (second.value === this.enterpriseForm.address.cityId) {
                this.addressValue += '/' + second.label
                if (second.children) {
                  second.children.forEach((third, index) => {
                    if (third.value === this.enterpriseForm.address.countyId) {
                      this.addressValue += '/' + third.label
                    }
                  })
                }
              }
            })
          }
        }
      })
    }
  },
  mounted () {
    this.detail()
  }
}
</script>

<style lang="less">
#certificated{
  .certificate img{
    float: left;
    margin-right: 20px;
  }
  .certificate .webuploader-container{
    margin: 100px 10px 0 0;
    float: left;
  }
  .certificate span{
    float: left;
    margin: 100px 10px 0 0;
    color: #999999;
  }
  .certificate .webuploader-pick{
    width: 72px;
    height: 32px;
    line-height: 32px;
    padding-left: 20px;
  }

  /* 认证信息提交后查看页面  审核状态*/
  .el-form{
    position: relative;
  }
  .checkStatus{
    position: absolute;
    right: 20px;
    top: 60px;
    width: 127px;
    height: 95px;
    overflow: hidden;
  }
  .fileBox{
    display: none;
  }
}
</style>
