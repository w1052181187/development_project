<template>
  <div class="center_box" id="contract_box_detail">
      <div class="coninfor_box">
        <div class="coninfor_title">查看</div>
        <div class="coninfor_con">
          <div class="infor_side_navbigbox">
            <div v-for="(sideName,index) in sideData" class="infor_side_navbox" :key="index" :class="{infor_side_nav_active:isTrue==index}"
                 @click="chooseAddr(index, sideName)">
              {{sideName.name}}
            </div>
          </div>
          <div class="infor_side_conbigbox" v-if="isTrue === 0">
            <el-form :model="projectForm" label-width="140px" class="demo-ruleForm">
              <div class="dialog_cancel_infoson_father">
                <div class="dialog_cancel_infoson_title">
                  <div class="dialog_cancel_infoson_titlewenzi">项目信息</div>
                  <div class="dialog_cancel_infoson_titlexian"></div>
                </div>
                <div class="dialog_detail">
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="企业名称：">
                        <span>{{projectForm.enterpriseName}}</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="注册地址：" prop="provinceId">
                        <el-cascader
                          disabled class="industry_box detail_jilian"
                          expand-trigger="hover"
                          :options="addressOptions"
                          v-model="selectedcityOptions">
                        </el-cascader>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="24">
                      <el-form-item prop="address" label="" >
                        <span>{{projectForm.address}}</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="所属行业：" prop="industry">
                        <el-cascader
                          disabled
                          class="industry_box detail_jilian"
                          expand-trigger="hover"
                          :options="industryOptions"
                          v-model="selecIndustry">
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
                      <el-form-item label="姓名：">
                        <span>{{projectForm.contactName}}</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="24">
                      <el-form-item label="移动电话：">
                        <span>{{projectForm.contactPhone}}</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="24">
                      <el-form-item label="电子邮箱：">
                        <span>{{projectForm.contactEmail}}</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <el-form-item class="operation_btn_box">
                <el-button @click="$router.go(-1)" class="cancel_btn">返回</el-button>
              </el-form-item>
            </el-form>
          </div>
          <div class="infor_side_conbigbox" v-if="isTrue === 1">
            <el-form :model="contractForm" label-width="140px" class="demo-ruleForm">
              <div class="infor_side_con_contractbox">
                <div class="dialog_cancel_infoson_father" v-for="(contract, index) in contractForm.contractArray" :key="index">
                  <div class="dialog_cancel_infoson_title">
                    <div class="dialog_cancel_infoson_titlewenzi">合同 {{index + 1}}</div>
                    <div class="dialog_cancel_infoson_titlexian"></div>
                  </div>
                  <div class="dialog_detail contact_detailbox">
                    <el-row>
                      <el-col :span="12">
                        <el-form-item label="合同名称：" prop="contractName">
                          <span>{{contract.contractName}}</span>
                        </el-form-item>
                      </el-col>
                      <el-col :span="12">
                        <el-form-item label="合同编号：" prop="contractNumber">
                          <span>{{contract.contractNumber}}</span>
                        </el-form-item>
                      </el-col>
                    </el-row>
                    <el-row>
                      <el-col :span="12">
                        <el-form-item label="合同期限：" prop="contractTime">
                          <span>{{contract.contractStartDate}}</span> 至 <span>{{contract.contractEndDate}}</span>
                        </el-form-item>
                      </el-col>
                      <el-col :span="12">
                        <el-form-item label="附件：">
                          <file-download :fileArrays="contract.fileInformations" fileType="1"></file-download>
                        </el-form-item>
                      </el-col>
                    </el-row>
                  </div>
                </div>
              </div>
              <el-form-item class="operation_btn_box">
                <el-button @click="$router.go(-1)" class="cancel_btn">返回</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
import * as cityData from '@/assets/js/region'
import * as induData from '@/assets/js/industry'
import fileDownload from '@/components/common/file-download'
import {agencyEnterprise} from '@/api'
export default {
  name: '',
  components: {
    fileDownload
  },
  data () {
    return {
      projectForm: {}, // 企业信息
      contractForm: { // 合同信息
        contractArray: [
          {
            fileInformations: []
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
      isTrue: 0, // 默认为企业信息
      // 三级联动
      addressOptions: cityData.CityInfo,
      selectedcityOptions: [],
      // 所属行业
      industryOptions: induData.industry,
      selecIndustry: []
    }
  },
  methods: {
    // 点击切换
    chooseAddr (index, sideName) {
      this.isTrue = index
    },
    detail () {
      agencyEnterprise.detail(this.$route.params.objectId).then((res) => {
        this.projectForm = res.data.agencyEnterprise
        this.selectedcityOptions = [this.projectForm.provinceId, this.projectForm.cityId, this.projectForm.countyId]
        this.selecIndustry = [this.projectForm.industryCategory, this.projectForm.industryClass]
        this.contractForm.contractArray = res.data.agencyEnterprise.contracts
      })
    }
  },
  mounted () {
    this.detail()
  }
}
</script>

<style lang="less">
  #contract_box_detail{
    .industry_box{
      width: 100%;
    }
    .contract_bigbox{
      width: 100%;
    }
    .detail_jilian .el-input__inner{
      background: transparent;
      border: none;
    }
    .detail_jilian .el-input__suffix{
      display: none;
    }
    .el-cascader.is-disabled .el-cascader__label {
      color: #333!important;
    }
  }
</style>
