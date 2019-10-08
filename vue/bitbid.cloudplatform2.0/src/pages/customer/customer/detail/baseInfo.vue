<template>
  <div>
    <el-form :model="baseInfo" ref="baseInfo" :validate-on-rule-change="true" v-loading="loading">
      <div class="part">
        <el-row>
          <el-col :span="12">
            <el-form-item label="客户编号：">
              <span>{{baseInfo.customerCode | formatText}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="统一社会信用代码：">
              <span>{{baseInfo.uscCode | formatText}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="客户名称：">
              <span>{{baseInfo.name | formatText}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="法定代表人：">
              <span>{{baseInfo.businessLicenseName | formatText}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="公司类型：">
              <span>{{baseInfo.companyType | formatCompanyType}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户来源：">
              <span>{{baseInfo.dicSourceCode | formatText}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="所在地：">
              <span>{{baseInfo.regionText | formatText}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户价值：">
              <span>{{baseInfo.dicValueCode | formatText}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="所属行业：">
              <span>{{baseInfo.dicIndustryCode | formatText}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户状态：">
              <span>{{baseInfo.customerStatus ? '已合作' : '未合作'}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="详细地址：">
              <span>{{baseInfo.address | formatText}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="公司电话：">
              <span>{{baseInfo.officePhone | formatText}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="注册资金（元）：">
              <span>{{baseInfo.registeredCapital | formatText}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="传真：">
              <span>{{baseInfo.fax | formatText}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="员工人数：">
              <span>{{baseInfo.staffNumber | formatText}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="负责人：">
              <span>{{baseInfo.chargePersonName | formatText}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="客户网址：">
              <span>{{baseInfo.url | formatText}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属部门：">
              <span>{{baseInfo.departmentName | formatText}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="经营范围：">
            <el-input type="textarea" v-model="baseInfo.businessScope" readonly placeholder="暂无数据"></el-input>
          </el-form-item>
        </el-row>
      </div>
    </el-form>
  </div>
</template>
<script>
import {dateFormat} from '@/assets/js/common'
export default {
  data () {
    return {
      loading: false
    }
  },
  props: ['baseInfo'],
  methods: {
  },
  filters: {
    // 格式化公司类型
    formatCompanyType (value) {
      let companyTypes = ['', '国企', '股份', '私企', '外贸', '港资', '台资']
      return typeof value !== 'undefined' ? companyTypes[value] : '---'
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
</style>
