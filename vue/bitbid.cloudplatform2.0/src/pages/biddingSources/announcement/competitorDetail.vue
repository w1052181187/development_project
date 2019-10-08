<template>
  <div class="cloudcontent competitor-detail" id="cloud_business_analysis">
    <div class="return-list" @click="returnList">返回竞争对手列表</div>
    <div class="main">
      <p class="competitor-title">企业情况分析表（与招标人合作的招标代理机构）</p>
      <el-form :model="detailForm" class="comp-detail-form">
        <table class="detailtable">
          <tr>
            <td>招标代理机构名称：</td>
            <td colspan="4">
              <span v-if="detailForm.enterprise_name">{{detailForm.enterprise_name}}</span>
              <span v-else>{{agency}}</span>
            </td>
          </tr>
          <tr>
            <td>法定代表人：</td>
            <td colspan="4">
              <span>{{detailForm.legal_person}}</span>
            </td>
          </tr>
          <tr>
            <td>经营范围：</td>
            <td colspan="4">
              <span>{{detailForm.scope_of_business}}</span>
            </td>
          </tr>
          <tr>
            <td>成立时间：</td>
            <td colspan="4">
              <span>{{detailForm.registration_time}}</span>
            </td>
          </tr>
          <tr>
            <td>营业期限：</td>
            <td colspan="4">
              <span>{{detailForm.business_start_time + '~' + detailForm.business_end_time}}</span>
            </td>
          </tr>
          <tr>
            <td>注册资本：</td>
            <td colspan="4">
              <span>{{detailForm.registration_capital}}</span>
            </td>
          </tr>
          <!--<tr>-->
            <!--<td>企业资质等级：</td>-->
            <!--<td colspan="4">-->
              <!--<span>{{detailForm.qualificationLevel}}</span>-->
            <!--</td>-->
          <!--</tr>-->
          <tr>
            <td>地址：</td>
            <td colspan="4">
              <span>{{detailForm.registration_address}}</span>
            </td>
          </tr>
          <!--<tr>-->
            <!--<td>企业荣誉：</td>-->
            <!--<td colspan="4">-->
              <!--<span>{{detailForm.enterpriseHonor}}</span>-->
            <!--</td>-->
          <!--</tr>-->
          <tr>
            <td>业绩（近1年承接本业主招标项目的业绩）：</td>
            <td colspan="4">
              <span>总数：{{annoTotal + '，'}}总金额：{{annoSum}}万元</span>
            </td>
          </tr>
        </table>
      </el-form>
      <p class="competitor-title">业绩</p>
      <el-table :data="tableData" border tooltip-effect="dark" style="width: 100%"
        header-cell-class-name="tableheader">
        <el-table-column type="index" label="序号" width="60" align="center"></el-table-column>
        <el-table-column prop="region" label="项目地点" show-overflow-tooltip></el-table-column>
        <el-table-column prop="project_name" label="项目名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="registrationTime" label="报名时间" show-overflow-tooltip></el-table-column>
        <el-table-column prop="biddingWebsite" label="招标公告网址" show-overflow-tooltip></el-table-column>
        <el-table-column prop="bid_amount" label="中标金额（万元）" show-overflow-tooltip></el-table-column>
        <el-table-column prop="agency_phone" label="代理机构电话" show-overflow-tooltip></el-table-column>
        <el-table-column prop="winningResult" label="中标结果公示网址" show-overflow-tooltip>
          <template slot-scope="scope">
            <a href="javascript:;" @click="toCompetitorDetail(scope.row)">{{scope.row.winningResult}}</a>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
import { comparison } from '@/api/biddingSources'
import * as region from '@/assets/js/region'
export default {
  components: {},
  data () {
    return {
      regionArr: region.CityInfo,
      detailForm: {
        enterprise_name: '',
        legal_person: '',
        scope_of_business: '',
        registration_time: '',
        business_start_time: '',
        business_end_time: '',
        registration_capital: '',
        registration_address: ''
      },
      tableData: [],
      agency: '', // 招标代理机构名称
      annoTotal: 0, // 项目总数
      annoSum: 0 // 项目金额
    }
  },
  methods: {
    detail () {
      // 公告详情
      comparison.competitorDetail(this.$route.params.relatedCode).then((res) => {
        if (res.data.annoDetail) {
          this.agency = res.data.annoDetail.agency
          if (res.data.ownerDetail.length > 0) {
            this.detailForm = res.data.ownerDetail[0]
          }
          this.annoTotal = res.data.annoTotal
          this.annoSum = res.data.annoSum
          if (res.data.performances.length > 0) {
            this.tableData = res.data.performances
            this.tableData.map((item) => {
              item.region = ''
              this.regionArr.map((reg) => {
                if (Number(item.province_id) === reg.value) {
                  item.region += reg.label
                }
                reg.children.map((reg2) => {
                  if (Number(item.city_id) === reg2.value) {
                    item.region += reg2.label
                  }
                  reg2.children.map((reg3) => {
                    if (Number(item.county_id) === reg3.value) {
                      item.region += reg3.label
                    }
                  })
                })
              })
            })
          }
        }
      })
    },
    returnList () {
      this.$store.commit('delete_tabs', this.$route.fullPath)
      this.$router.push({path: `/biddingSources/announcementDetail/${this.$route.query.code}`})
    },
    toCompetitorDetail (rowData) {}
  },
  mounted () {
    this.detail()
  }
}
</script>
<style scoped lang="less">
.clearfix:after{
    content:".";
    display:block;
    height:0;
    clear:both;
    visibility:hidden;
}
.clearfix{
    zoom:1;
}
.competitor-title{
  line-height: 40px;
  text-align: center;
  font-weight: bold;
  font-size: 18px;
}
.index-span{
  margin-right: 6px;
}
  .return-list{
    text-decoration: underline;
    color: #409EFF;
    cursor: pointer;
  }
</style>
<style lang="less">
.competitor-detail{
  .main{
    .el-form-item__label{
      width: 276px;
      margin-right: 10px;
    }
  }
  .comp-detail-form{
    width: 100%!important;
    margin-top: 20px!important;
  }
}
</style>
