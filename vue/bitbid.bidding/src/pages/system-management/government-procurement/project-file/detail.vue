<template>
  <div id="gp_prodetail" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/project-file' }">项目备案</el-breadcrumb-item>
        <el-breadcrumb-item>项目查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <router-link tag='span' to="/system-management/government-procurement/project-file">返回</router-link>
      </div>
    </div>
    <div class="contentbigbox">
      <table class="detailtable">
        <tr>
          <td>项目名称：</td>
          <td  colspan="3">{{proForm.name}}</td>
          <td>项目编号：</td>
          <td  colspan="3">{{proForm.code_by_user}}</td>
        </tr>
        <tr>
          <td>项目地址：</td>
          <td colspan="3">{{proForm.sectionAddress}}</td>
          <td>详细地址：</td>
          <td colspan="3">{{proForm.address}}</td>
        </tr>
        <tr>
          <td>项目法人：</td>
          <td colspan="3">{{proForm.legal_person}}</td>
          <td>项目行业分类：</td>
          <td colspan="3">{{proForm.industry}}</td>
        </tr>
        <tr>
          <td>资金来源：</td>
          <td colspan="3" >
            <span v-for="item in proForm.tProjectSourcesOfFunds" :key="item.id">{{getFunds(item.type, item.capital)}}</span>
          </td>
          <td>项目规模（元）：</td>
          <td colspan="3">{{proForm.scale}}</td>
        </tr>
        <tr>
          <td>批文名称：</td>
          <td colspan="3">{{proForm.name_approval}}</td>
          <td>批准文号：</td>
          <td colspan="3">{{proForm.approval_number}}</td>
        </tr>
        <tr>
          <td>批准单位：</td>
          <td colspan="3">{{proForm.approved_unit}}</td>
          <td>是否由评标委员会
            确定中标人：</td>
          <td colspan="3">
            <span v-if="proForm.isDetWinBidder === 1">
              是
            </span>
            <span v-if="proForm.isDetWinBidder === 0">
              否
            </span>
          </td>
        </tr>
        <tr>
          <td>联系人：</td>
          <td colspan="3">{{proForm.tContacts_name}}</td>
          <td>联系方式：</td>
          <td colspan="3">{{proForm.tContacts_phone}}</td>
        </tr>
      </table>
    </div>
  </div>
</template>
<script>
import { pushData } from '@/api'
import * as region from '@/assets/js/region'
import * as industry from '@/assets/js/industry'
export default {
  components: {
  },
  data () {
    return {
      // 详情
      proForm: {
        tContacts_name: '',
        tContacts_phone: '',
        industry: ''
      }
    }
  },
  methods: {
    detail () {
      pushData.projectDetail(this.$route.params.objectId).then((res) => {
        this.proForm = JSON.parse(res.data.projectDetail)
        this.proForm.sectionAddress = ''
        this.proForm.tContacts_name = this.proForm.tContacts.name
        this.proForm.tContacts_phone = this.proForm.tContacts.mobile_phone
        region.CityInfo.map((item) => {
          if (this.proForm.province_id == item.value) {
            this.proForm.sectionAddress += item.label
            item.children.map((shi) => {
              if (this.proForm.city_id == shi.value) {
                this.proForm.sectionAddress += shi.label
                shi.children.map((county) => {
                  if (this.proForm.county_id == county.value) {
                    this.proForm.sectionAddress += county.label
                  }
                })
              }
            })
          }
        })
        industry.industry.map((item) => {
          if (this.proForm.industry_category === item.value) {
            this.proForm.industry = item.label
            item.children.map((industry) => {
              if (this.proForm.industry_class === industry.value) {
                this.proForm.industry += ' - ' + industry.label
              }
            })
          }
        })
      })
    },
    getFunds (type, capital) {
      var funds
      switch (type) {
        case 1:
          funds = '自有资金:' + capital + '%;'
          break
        case 2:
          funds = '吸收资金:' + capital + '%;'
          break
        // 暂不用
        case 3:
          funds = '专项资金:' + capital + '%;'
          break
      }
      return funds
    }
  },
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
</style>
