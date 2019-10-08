<template>
  <div class="cloudcontent announcement-detail" id="cloud_business_analysis">
    <div class="return-list">信息来自比比网（比比网不对信息内容的真实性和准确性负责，也不提供验证服务）</div>
    <h2 class="announcement-title">{{detailForm.project_name}}</h2>
    <div class="announcement-topic clearfix announcement-box">
      <div class="fl">
        <span>发布时间：</span>
        <span>{{detailForm.publish_date_str}}</span>
      </div>
      <div class="fl">
        <span>地区：</span>
        <span>{{detailForm.region}}</span>
      </div>
      <div>
        <el-button class="fl" @click="joinBusinessOpportunities" v-loading="loading" v-if="!isJoinOpportunity && $store.getters.permissions.includes('1030102')">+加入到我的商机</el-button>
        <el-button class="fl" @click="skipOpportunities" v-loading="loading" v-if="isJoinOpportunity"> + 查看商机详情</el-button>
      </div>
    </div>
    <div class="main">
      <el-tabs class="detail_tab" type="card" v-model="activeTab" @tab-click="tabsClickEvents">
        <!--比选公告-->
        <el-tab-pane label="比选公告" name="first" class="firsr-tab">
          <editor ref="ueditor" class="ueditor" :editread="editread"></editor>
        </el-tab-pane>
        <!--比选公告-->
        <!--招标业主分析-->
        <el-tab-pane label="招标业主分析" name="second">
          <el-form :model="bidderForm" class="from-owner-table">
            <table class="detailtable">
              <tr>
                <td>招标人名称：</td>
                <td colspan="4">
                  <span>{{detailForm.tender}}</span>
                </td>
              </tr>
              <tr>
                <td>法定代表人：</td>
                <td colspan="4">
                  <span>{{bidderForm.legal_person}}</span>
                </td>
              </tr>
              <tr>
                <td>经营范围：</td>
                <td colspan="4">
                  <span>{{bidderForm.scope_of_business}}</span>
                </td>
              </tr>
              <tr>
                <td>成立时间：</td>
                <td colspan="4">
                  <span>{{bidderForm.registration_time}}</span>
                </td>
              </tr>
              <tr>
                <td>营业期限：</td>
                <td colspan="4">
                  <span>{{bidderForm.business_start_time}} ~ {{bidderForm.business_end_time}}</span>
                </td>
              </tr>
              <tr>
                <td>注册资本：</td>
                <td colspan="4">
                  <span>{{bidderForm.registration_capital}}</span>
                </td>
              </tr>
              <tr>
                <td>企业类型：</td>
                <td colspan="4">
                  <span>{{bidderForm.enterprise_type}}</span>
                </td>
              </tr>
              <tr>
                <td>地址：</td>
                <td colspan="4">
                  <span>{{bidderForm.registration_address}}</span>
                </td>
              </tr>
              <tr>
                <td>联系人：</td>
                <td colspan="4">
                  <span>{{bidderForm.tender_contact}}</span>
                </td>
              </tr>
              <tr>
                <td>联系人电话：</td>
                <td colspan="4">
                  <span>{{bidderForm.tender_phone}}</span>
                </td>
              </tr>
              <tr>
                <td>近一年公开招标的项目总数：</td>
                <td colspan="4">
                  <span>{{publicProjectTotal}}</span>
                </td>
              </tr>
              <tr>
                <td>近一年与招标人合作的代理机构：</td>
                <td colspan="4">
                  <li v-for="(item,index) in agencyList" :key="index">
                    <span class="index-span" v-if="agencyList.length > 1">{{index + 1}}</span>{{item.agency}}
                  </li>
                </td>
              </tr>
              <tr>
                <td>本单位工程造价咨询机构库已入库名单（近一年）：</td>
                <td colspan="4">
                  <li v-for="(item,index) in bidderForm.inventoryList" :key="index">
                    <span class="index-span">{{index+1}}</span>{{item.text}}
                  </li>
                </td>
              </tr>
              <tr>
                <td>近一年类似项目（入围、比选、入库等）：</td>
                <td colspan="4">
                  <li class="clearfix" v-for="(item,index) in announcementList" :key="index">
                    <span class="index-span fl">{{index+1}}</span>
                    <div class="fl">
                      <div style="width: 100%">{{'【项目名称】' + item.project_name}}</div>
                      <div style="width: 100%">{{'【代理机构】' + item.agency}}</div>
                    </div>
                  </li>
                </td>
              </tr>
            </table>
          </el-form>
        </el-tab-pane>
        <!--招标业主分析-->
        <!--竞争对手分析-->
        <el-tab-pane label="竞争对手分析" name="third" class="third-tab">
          <p>与招标人合作的招标代理机构名单</p>
          <el-table :data="tableData" border tooltip-effect="dark" style="width: 100%"
            header-cell-class-name="tableheader">
            <el-table-column type="index" label="序号" width="60" align="center"></el-table-column>
            <el-table-column prop="agency" label="企业名称" show-overflow-tooltip>
              <template slot-scope="scope">
                <a href="javascript:;" @click="toCompetitorDetail(scope.row)">{{scope.row.agency}}</a>
              </template>
            </el-table-column>
            <el-table-column label="参与本招标人项目情况" show-overflow-tooltip align="center">
              <el-table-column prop="annoNum" label="项目数量" width="200"></el-table-column>
              <el-table-column prop="stats" label="项目金额（万元）" width="200"></el-table-column>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <!--竞争对手分析-->
      </el-tabs>
      <div style="text-align: center;width: 100%;margin-top: 15px">
        <el-button type="primary" @click="returnListBtn" class="return-btn">返回</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import editor from '@/components/ueditor/ueditor.vue'
import { comparison } from '@/api/biddingSources'
import { business } from '@/api/customer'
import * as region from '@/assets/js/region'
import {dateFormat} from '@/assets/js/common'
export default {
  components: {
    editor
  },
  data () {
    return {
      editread: true, // 富文本编辑器
      queryModel: {
        indexType: 3,
        enterpriseName: ''
      },
      detailForm: {
        project_name: '',
        region: '山西省',
        publish_date_str: ''
      },
      activeTab: 'first',
      tableData: [],
      bidderForm: {
        legal_person: '',
        scope_of_business: '',
        registration_time: '',
        business_start_time: '',
        business_end_time: '',
        registration_capital: '',
        enterprise_type: '',
        registration_address: '',
        tender_contact: '',
        tender_phone: ''
      },
      addressArr: region.CityInfo,
      publicProjectTotal: 0, // 近一年公开招标项目总数
      agencyList: [],
      announcementList: [],
      tabsClickFlag: 'first', // 当前窗口(默认为比选公告内容页)
      opportunitiesForm: {
        comparisonCode: '',
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        content: '',
        status: 0,
        chargePersonId: this.$store.getters.authUser.userId,
        chargePersonName: this.$store.getters.authUser.userName,
        departmentId: this.$store.getters.authUser.departmentId,
        departmentName: this.$store.getters.authUser.departmentName,
        projectName: '',
        projectCode: '',
        provinceId: '',
        cityId: '',
        countyId: '',
        address: '',
        publishTime: '',
        signUpEndTime: '',
        projectAmount: '',
        contacts: '',
        contactNum: ''
      },
      loading: false,
      // 是否加入商机
      isJoinOpportunity: false
    }
  },
  watch: {
  },
  methods: {
    /** tabs切换 */
    tabsClickEvents (val) {
      this.tabsClickFlag = val.name
      this.detail()
    },
    detail () {
      if (this.tabsClickFlag === 'first') { // 比选公告详情
        comparison.annoDetail(this.$route.params.relatedCode).then((res) => {
          if (res.data.detail) {
            this.detailForm = res.data.detail
            this.detailForm.region = ''
            this.addressArr.map((item) => {
              if (item.value === Number(this.detailForm.province_id)) {
                this.detailForm.region += item.label
                item.children.map((ite) => {
                  if (ite.value === Number(this.detailForm.city_id)) {
                    this.detailForm.region += ite.label
                    ite.children.map((it) => {
                      if (it.value === Number(this.detailForm.county_id)) {
                        this.detailForm.region += it.label
                      }
                    })
                  }
                })
              }
            })
            this.$refs.ueditor.setContent(this.detailForm.content)
          }
        })
      } else if (this.tabsClickFlag === 'second') { // 招标业主分析
        // 招标人不为空
        if (this.detailForm.tender) {
          this.queryModel.enterpriseName = this.detailForm.tender
          comparison.ownerAnalyze(this.queryModel).then((res) => {
            this.bidderForm = res.data.ownerDetail // 招标业主分析
            this.publicProjectTotal = res.data.annoTotal // 近一年发布项目总量
            this.agencyList = res.data.deList // 近一年合作的代理机构
            this.announcementList = res.data.list // 近一年类似项目
          })
        }
      } else if (this.tabsClickFlag === 'third') {
        // 招标人不为空
        if (this.detailForm.tender) {
          this.queryModel.enterpriseName = ''
          this.queryModel.tender = this.detailForm.tender
          this.queryModel.indexType = 2
          comparison.competitorsAnalyze(this.queryModel).then((res) => {
            this.tableData = res.data.competitors
          })
        }
      }
      // 是否加入商机
      comparison.isJoinOpportunity(this.$route.params.relatedCode).then(res => {
        this.isJoinOpportunity = !res.data.data
      })
    },
    toCompetitorDetail (rowData) {
      this.$router.push({path: `/biddingSources/competitorDetail/${rowData.related_code}`, query: {code: this.$route.params.relatedCode}})
    },
    returnAnnouncementList () {},
    // 加入我的商机
    joinBusinessOpportunities () {
      this.loading = true
      this.opportunitiesForm.comparisonCode = this.detailForm.related_code
      this.opportunitiesForm.content = this.detailForm.content
      this.opportunitiesForm.projectName = this.detailForm.project_name
      this.opportunitiesForm.projectCode = this.detailForm.project_code
      this.opportunitiesForm.dicSourceCode = '比选公告'
      this.opportunitiesForm.provinceId = this.detailForm.province_id
      this.opportunitiesForm.cityId = this.detailForm.city_id
      this.opportunitiesForm.countyId = this.detailForm.county_id
      this.opportunitiesForm.address = this.detailForm.address
      this.opportunitiesForm.publishTime = this.detailForm.publish_date ? dateFormat(new Date(this.detailForm.publish_date), 'yyyy-MM-dd hh:mm:ss') : null
      this.opportunitiesForm.signUpEndTime = this.detailForm.bid_end_time ? dateFormat(new Date(this.detailForm.bid_end_time), 'yyyy-MM-dd hh:mm:ss') : null
      this.opportunitiesForm.projectAmount = this.detailForm.budgut_amount ? this.detailForm.budgut_amount : '0'
      this.opportunitiesForm.contacts = this.detailForm.tender_contact
      this.opportunitiesForm.contactNum = this.detailForm.tender_phone
      comparison.joinBusinessOpportunities(this.opportunitiesForm).then((res) => {
        this.loading = false
        this.detail()
      })
    },
    // 返回按钮
    returnListBtn () {
      this.$store.commit('delete_tabs', this.$route.fullPath)
      this.$router.push({path: `/biddingSources/announcementList`, query: {day: this.$route.query.day, provinceId: this.$route.query.provinceId}})
    },
    // 跳转商机详情
    skipOpportunities () {
      business.detailByComparisonCode(this.$route.params.relatedCode).then(res => {
        this.$router.push({path: `/customerMan/business/detail/${res.data.data.code}`})
      })
    }
  },
  filters: {
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
    margin-top: 20px;
    text-align: left;
}
.announcement-box{
  text-align: center;
}
.clearfix div {
  display: inline-block;
  float: none;
  vertical-align: middle;
}
.announcement-title{
  text-align: center;
}
.announcement-topic{
  line-height: 40px;
  div{
    margin: 0 10px;
  }
}
.firsr-tab{
  margin-bottom: 10px;
  .detail-form{
    line-height: 40px;
    span:first-child{
      margin-right: 10px;
    }
  }
}
.index-span{
  margin-right: 6px;
}
.third-tab{
  margin-bottom: 10px;
  p{
    font-size: 16px;
    margin-bottom: 10px;
  }
}
</style>
<style lang="less">
.announcement-detail{
  .main{
    .el-form-item__label{
      width: 182px;
      margin-right: 10px;
    }
    .el-form-item__content{
      margin-left: 192px;
    }
  }
  .from-owner-table{
    width: 100%!important;
  }
  .return-btn{
    background: #ededed;
    border-color: #ededed;
    color: #666666;
    width: 128px;
    height: 40px;
    padding: 0;
  }
  .return-list{
    color: #008000;
    width: 100%;
    margin-bottom: 20px;
  }
}
</style>
