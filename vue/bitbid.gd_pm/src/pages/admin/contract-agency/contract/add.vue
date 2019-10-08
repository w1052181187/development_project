<template>
  <div class="center_box contract-add-box" id="contract_box">
      <div class="coninfor_box">
        <div class="coninfor_title">创建</div>
        <div class="coninfor_con">
          <div class="infor_side_navbigbox">
            <div v-for="(sideName,index) in sideData" class="infor_side_navbox" :key="index" :class="{infor_side_nav_active:isTrue==index}"
                 @click="chooseAddr(index, sideName)">
              {{sideName.name}}
            </div>
          </div>
          <div class="infor_side_conbigbox" v-if="isTrue === 0">
            <el-form :model="projectForm" :rules="rules" ref="projectForm" label-width="140px" class="demo-ruleForm">
              <div class="dialog_cancel_infoson_father">
                <div class="dialog_cancel_infoson_title">
                  <div class="dialog_cancel_infoson_titlewenzi">企业信息</div>
                  <div class="dialog_cancel_infoson_titlexian"></div>
                </div>
                <div class="dialog_detail">
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="企业名称：" prop="enterpriseName">
                        <el-input placeholder="请输入企业名称" v-model="projectForm.enterpriseName" :disabled="isEnterpriseId !== ''"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row :gutter="20">
                    <el-col :span="12">
                      <el-form-item label="注册地址：" prop="provinceId">
                        <el-cascader
                          :disabled="isEnterpriseId !== ''"
                          expand-trigger="hover"
                          :options="addressOptions"
                          v-model="selectedcityOptions"
                          @change="handlecityChange">
                        </el-cascader>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item prop="address" label-width="0">
                        <el-input placeholder="请输入详细地址" v-model="projectForm.address" :disabled="isEnterpriseId !== ''"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="所属行业：" prop="industryCategory">
                        <el-cascader
                          :disabled="isEnterpriseId !== ''"
                          class="industry_box"
                          expand-trigger="hover"
                          :options="industryOptions"
                          v-model="selecIndustry"
                          @change="selectIndusChange">
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
                      <el-form-item label="姓名：" prop="contactName">
                        <el-input placeholder="请输入姓名" v-model="projectForm.contactName" :disabled="isEnterpriseId !== ''"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="24">
                      <el-form-item label="移动电话：" prop="contactPhone">
                        <el-input placeholder="请输入移动电话" v-model="projectForm.contactPhone" :disabled="isEnterpriseId !== ''"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="24">
                      <el-form-item label="电子邮箱：" prop="contactEmail">
                        <el-input placeholder="请输入电子邮箱" v-model="projectForm.contactEmail" :disabled="isEnterpriseId !== ''"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <el-form-item class="operation_btn_box" v-if="!isEnterpriseId">
                <el-button type="primary" @click="submitForm('projectForm')" class="save_btn" :loading="subStatus">提交</el-button>
                <el-button @click="$router.go(-1)" class="cancel_btn" :loading="subStatus">取消</el-button>
              </el-form-item>
            </el-form>
          </div>
          <div class="infor_side_conbigbox" v-if="isTrue === 1">
            <el-form :model="contractForm" :rules="rules" ref="contractForm" label-width="140px" class="demo-ruleForm">
              <div class="infor_side_con_contractbox">
                <div class="add_contract_btnbox">
                  <el-button type="primary" class="add_contract_btn" @click="addContract">新增合同</el-button>
                </div>
                <div class="dialog_cancel_infoson_father" v-for="(contract, index) in contractForm.contractArray" :key="index">
                  <div class="dialog_cancel_infoson_title">
                    <div class="dialog_cancel_infoson_titlewenzi">合同 {{index + 1}}</div>
                    <div class="shanchun_icon" @click="deleteContract(index)">
                      <img src="../../../../../static/img/shanchu.png" alt="">
                    </div>
                    <div class="dialog_cancel_infoson_titlexian"></div>
                  </div>
                  <div class="dialog_detail contact_detailbox">
                    <el-row>
                      <el-col :span="12">
                        <el-form-item label="合同名称：" :prop="'contractArray.'+index+'.contractName'" :rules="rules.contractRules">
                          <el-input placeholder="请输入合同名称" v-model="contract.contractName"></el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :span="12">
                        <el-form-item label="合同编号：" :prop="'contractArray.'+index+'.contractNumber'" :rules="rules.contractNumberRules">
                          <el-input placeholder="请输入合同编号" v-model="contract.contractNumber"></el-input>
                        </el-form-item>
                      </el-col>
                    </el-row>
                    <el-row>
                      <el-col :span="12">
                        <el-form-item label="合同期限：" prop="contractTime" class="mandatory">
                          <el-date-picker
                            class="contract_bigbox"
                            v-model="contract.contractDataRange"
                            type="datetimerange"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            range-separator="至"
                            start-placeholder="开始时间"
                            end-placeholder="结束时间"
                            align="right">
                          </el-date-picker>
                        </el-form-item>
                      </el-col>
                      <el-col :span="12">
                        <el-form-item label="附件：" class="mandatory">
                          <upload-file @uploadSuccess="uploadSuccess" @deleteSuccess="deleteSuccess" :fileArrays="contract.fileInformations"
                           fileType="1" isImage="0" fileMaxNum="5" :fileObject="contract"></upload-file>
                        </el-form-item>
                      </el-col>
                    </el-row>
                  </div>
                </div>
              </div>
              <el-form-item class="operation_btn_box">
                <el-button type="primary" @click="submitContract('contractForm')" class="save_btn" :loading="subStatus">提交</el-button>
                <el-button @click="$router.go(-1)" class="cancel_btn" :loading="subStatus">取消</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
import {isvalidPhone} from '@/assets/js/validate'
import * as cityData from '@/assets/js/region'
import * as induData from '@/assets/js/industry'
import uploadFile from '@/components/upload/publicFileUpload'
import {agencyEnterprise} from '@/api'
export default {
  name: '',
  components: {
    uploadFile
  },
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
      contractForm: { // 合同信息
        contractArray: [
          {
            fileInformations: [],
            contractDataRange: [] // 合同期限
          }
        ]
      },
      sideData: [
        {
          name: '企业信息'
        },
        {
          name: '合同信息'
        }
      ],
      isEnterpriseId: '', // 是否提交企业信息
      isTrue: 0, // 默认为企业信息
      subStatus: false, // 是否保存
      // 表单验证
      rules: {
        enterpriseName: [
          { required: true, message: '请输入企业名称', trigger: ['blur', 'change'] },
          {min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: ['blur', 'change']}
        ],
        provinceId: [
          { required: true, message: '请选择注册地址', trigger: ['blur', 'change'] }
        ],
        address: [
          { required: true, message: '请输入详细地址', trigger: ['blur', 'change'] },
          {min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: ['blur', 'change']}
        ],
        industryCategory: [
          { required: true, message: '请选择所属行业', trigger: ['blur', 'change'] }
        ],
        contactName: [
          { required: true, message: '请输入姓名', trigger: ['blur', 'change'] },
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: ['blur', 'change']}
        ],
        contactPhone: [
          { required: true, message: '请输入手机号', trigger: ['blur', 'change'] },
          { trigger: ['blur', 'change'], validator: validNumber }
        ],
        contactEmail: [
          { required: false, message: '请输入邮箱地址', trigger: 'blur' },
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: ['blur', 'change']},
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ],
        contractRules: [
          { required: true, message: '请输入合同名称', trigger: ['blur', 'change'] },
          {min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: ['blur', 'change']}
        ],
        contractNumberRules: [
          { required: true, message: '请输入合同编号', trigger: ['blur', 'change'] },
          {min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: ['blur', 'change']}
        ]
      },
      // 三级联动
      addressOptions: cityData.CityInfo,
      selectedcityOptions: [],
      // 所属行业
      industryOptions: induData.industry,
      selecIndustry: []
    }
  },
  methods: {
    // 上传文件
    uploadSuccess (contract, file, fileType) {
      contract.fileInformations.splice(0, 1, file)
    },
    deleteSuccess (contract, fileId) {
      contract.fileInformations = contract.fileInformations.filter(item => item.relativePath !== fileId)
    },
    // 新增一条合同
    addContract () {
      let newObject = {
        contractDataRange: [], // 合同期限
        fileInformations: []
      }
      this.contractForm.contractArray.push(newObject)
    },
    // 删除一条合同
    deleteContract (index) {
      this.contractForm.contractArray.splice(index, 1)
    },
    // 添加企业信息
    submitForm (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          agencyEnterprise.save(this.projectForm).then((res) => {
            if (res.data.resCode === '0000') {
              this.isEnterpriseId = res.data.agencyEnterprise.id // 提交信息后返回id
              this.isTrue = 1
            }
          })
        } else {
          return false
        }
      })
    },
    // 检查必填项是否填写完毕（当返回true则表示没有填写完毕）
    checkSample () {
      let reResult = false
      let resultTime = this.contractForm.contractArray.filter(function (item) {
        return item.contractDataRange !== null && item.contractDataRange.length !== 0
      })
      let resultFile = this.contractForm.contractArray.filter(function (item) {
        return item.fileInformations !== null && item.fileInformations.length !== 0
      })
      if (resultTime.length !== this.contractForm.contractArray.length || resultFile.length !== this.contractForm.contractArray.length) {
        reResult = true
      }
      return reResult
    },
    // 添加合同信息
    submitContract (form) {
      if (!this.isEnterpriseId) {
        this.$message({
          type: 'warning',
          message: '请先提交企业信息'
        })
        return false
      }
      let backV = this.checkSample()
      if (!backV) {
        this.$refs[form].validate((valid) => {
          if (valid) {
            let newArray = this.contractForm.contractArray.map((ite) => {
              ite.contractStartDate = ite.contractDataRange[0]
              ite.contractEndDate = ite.contractDataRange[1]
              ite.agencyId = this.isEnterpriseId
              delete ite.contractDataRange
              return ite
            })
            agencyEnterprise.contractSave(newArray).then((res) => {
              this.$router.push({path: '/admin/contract-agency/contract/index'})
            })
          } else {
            return false
          }
        })
      } else {
        this.$message({
          type: 'warning',
          message: '请检查必填项是否填写完毕'
        })
      }
    },
    // 点击切换
    chooseAddr (index, sideName) {
      this.isTrue = index
    },
    // 三级联动选择
    handlecityChange (value) {
      this.projectForm.provinceId = value[0]
      this.projectForm.cityId = value[1]
      this.projectForm.countyId = value[2]
    },
    selectIndusChange () {
      let industry = this.selecIndustry.toString().split(',')
      this.projectForm.industryCategory = industry[0]
      this.projectForm.industryClass = industry[1]
    }
  },
  mounted () {
  }
}
</script>

<style scoped lang="less">
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
  }
</style>
<style lang="less">
.contract-add-box{
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
</style>
