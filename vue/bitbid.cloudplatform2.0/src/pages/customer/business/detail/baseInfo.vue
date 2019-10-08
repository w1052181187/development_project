<template>
  <div class="cloudcontent" id="cloud_customer_add">
    <el-form :model="submitForm" ref="submitForm" :validate-on-rule-change="true">
        <div class="part">
          <el-row>
            <el-col :span="12">
              <el-form-item label="项目名称：">
                <span>{{submitForm.projectName | formatText}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="商机来源：">
                <span>{{submitForm.dicSourceCode | formatText}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="项目编号：">
                <span>{{submitForm.projectCode | formatText}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="商机阶段：">
                <span>{{submitForm.dicStageCode | formatText}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="客户名称：">
                <span>{{submitForm.customerManagementName | formatText}}</span>
                <a @click="lookCustomerDetail()" href="javascript:void(0);">查看客户详情</a>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="商机状态：">
                <span>{{submitForm.status | formatStatus}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="项目所在地：">
                <span>{{submitForm.regionText | formatText}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="招标类型：">
                <span>{{submitForm.tenderType | formatTenderType}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="项目详细地址：">
                <span>{{submitForm.address | formatText}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="发布时间：">
                <span>{{submitForm.publishTime | formatDate}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="项目规模：">
                <span>{{submitForm.projectScale | formatText}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="报名截止时间：">
                <span>{{submitForm.signUpEndTime | formatDate}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="项目金额估计（元）：">
                <span>{{submitForm.projectAmount | formatText}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="项目预计收入（元）：">
                <span>{{submitForm.projectedRevenue | formatText}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="负责人：">
                <span>{{submitForm.chargePersonName | formatText}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系人：">
                <span>{{submitForm.contacts | formatText}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="所属部门：">
                <span>{{submitForm.departmentName | formatText}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label=" 联系电话：">
                <span>{{submitForm.contactNum | formatText}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-form-item label="项目内容：">
              <editor ref="ueditor" class="ueditor" :editread="editread"></editor>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="附件：">
              <file-download :fileArrays="submitForm.fileInformations" fileType="9"></file-download>
            </el-form-item>
          </el-row>
        </div>
      </el-form>
  </div>
</template>
<script>
import editor from '@/components/ueditor/ueditor.vue'
import FileDownload from '@/components/download/file-download.vue'
import {dateFormat} from '@/assets/js/common'
import * as region from '@/assets/js/region'
export default {
  components: {
    editor,
    FileDownload
  },
  data () {
    return {
      submitForm: {
        fileInformations: [],
        regionText: ''
      },
      // 招标类型
      tenderTypes: ['', '工程', '货物', '服务'],
      opportunityStates: ['未评估', '评估中', '跟进中', '赢单', '输单', '商机关闭'],
      // 地址三级联动
      longTextOptions: region.CityInfo,
      // 富文本编辑器
      editread: true
    }
  },
  props: ['detailData'],
  watch: {
    'detailData.code' (value) {
      if (value) {
        this.initData()
      }
    }
  },
  methods: {
    initData () {
      // 获取数据
      this.submitForm = this.detailData
      // 初始化地区信息
      this.wrapRegionText()
      // 绑定富文本框数据
      this.$refs.ueditor.setContent(this.submitForm.content || '')
    },
    // 包装三级联动地址信息
    wrapRegionText () {
      // 初始化省市县数据
      this.submitForm.regionText = ''
      this.longTextOptions.map((item) => {
        if (item.value === this.submitForm.provinceId) {
          this.submitForm.regionText += item.label + '/'
          item.children.map((ite) => {
            if (ite.value === this.submitForm.cityId) {
              this.submitForm.regionText += ite.label + '/'
              ite.children.map((countyItem) => {
                if (countyItem.value === this.submitForm.countyId) {
                  this.submitForm.regionText += countyItem.label
                }
              })
            }
          })
        }
      })
    },
    // 查看客户详情
    lookCustomerDetail () {
      this.$router.push({path: `/customerMan/customer/detail/${this.detailData.customerManagementCode}`})
    }
  },
  filters: {
    // 格式化商机状态
    formatStatus (value) {
      let opportunityStates = ['未评估', '评估中', '跟进中', '赢单', '输单', '商机关闭']
      return typeof value !== 'undefined' ? opportunityStates[value] : '---'
    },
    // 格式化招标类型
    formatTenderType (value) {
      let tenderTypes = ['', '工程', '货物', '服务']
      return value ? tenderTypes[value] : '---'
    },
    // 格式化时间
    formatDate (value) {
      return value ? dateFormat(value, 'yyyy-MM-dd') : '---'
    },
    // 格式化字符串
    formatText (value) {
      return value || '---'
    }
  }
}
</script>
<style lang="less">
  #cloud_customer_add {
  }
</style>
