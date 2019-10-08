<template>
  <div id="addbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>土地交易备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/land-transaction/project/index' }">项目备案</el-breadcrumb-item>
        <el-breadcrumb-item>项目查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <router-link tag='span' to="/system-management/land-transaction/project/index">返回</router-link>
      </div>
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="地块编号：">
                  <span>{{sectionForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="统一交易标识码：">
                  <span>{{sectionForm.unifiedTransCode}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让行为批准单位：">
                  <span>{{sectionForm.approvalUnit}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="16">
                <el-form-item label="地块位置：" class="adressbox_selsct">
                  <el-cascader
                     expand-trigger="hover"
                     :options="addressOptions"
                     v-model="selectedcityOptions" disabled>
                  </el-cascader>
                </el-form-item>
                <el-form-item class="adressbox">
                  <span>{{sectionForm.address}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让行为批准单位文件：" class="filetypebox">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="规划面积(平方公里)：">
                  <span>{{sectionForm.area}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="实际出让面积(平方公里)：">
                  <span>{{sectionForm.actualArea}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让年限(年)：">
                  <span>{{sectionForm.ageLimit}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="投资强度(万元/公顷)：">
                  <span>{{sectionForm.investIntensity}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="容积率(%)：">
                  <span>{{sectionForm.errorTolerRate}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="绿化率(%)：">
                  <span>{{sectionForm.greeningRate}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="建筑密度(%)：">
                  <span>{{sectionForm.densityOfBuild}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="建筑限高(m)：">
                  <span>{{sectionForm.buildingHeight}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="土地用途：">
                  <span>{{sectionForm.landUse}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="说明：">
                  <span>{{sectionForm.explainCon}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="受让人资格条件：">
                  <span>{{sectionForm.prerequisites}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--竞价信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">竞价信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="供应方式：" prop="methodOfSupply">
                  <span v-if="sectionForm.methodOfSupply === 1">招拍挂出让</span>
                  <span v-if="sectionForm.methodOfSupply === 2">协议出让</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="招拍挂类型：" prop="strokes" v-if="sectionForm.methodOfSupply === 1">
                  <span v-if="sectionForm.type === 1">招标出让</span>
                  <span v-if="sectionForm.type === 2">挂牌出让</span>
                  <span v-if="sectionForm.type === 3">拍卖出让</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="招标方式：" v-if="sectionForm.methodOfSupply === 1 && sectionForm.type === 1">
                  <span v-if="sectionForm.biddingType === 1">公开招标</span>
                  <span v-if="sectionForm.biddingType === 2">邀请招标</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row v-if="sectionForm.methodOfSupply === 1">
              <el-col :span="8" v-if="sectionForm.biddingType === 1 && sectionForm.type === 1 ">
                <el-form-item label="资审方式：">
                  <span v-if="sectionForm.prequaType === 1">资格候审</span>
                  <span v-if="sectionForm.prequaType === 2">资格预审</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="估价报告备案号：">
                  <span>{{sectionForm.valuationReport}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="竞买保证金(万元)：">
                  <span>{{sectionForm.bidBond}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="sectionForm.type !== 1">
                <el-form-item label="起始价(万元)：">
                  <span>{{sectionForm.startingPrice}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="sectionForm.type !== 1">
                <el-form-item label="加价幅度(万元)：">
                  <span>{{sectionForm.bidIncrement}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="是否存在底价：">
                  <span v-if="sectionForm.isFloorPrice === 1">是</span>
                  <span v-if="sectionForm.isFloorPrice === 0">否</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="地块估算价(元)：">
                  <span>{{sectionForm.conputedPrice}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--竞价信息-->
        <!--联系人信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">联系人信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="公司名称：" prop="companyName">
                  <span>{{sectionForm.companyName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="联系人：" prop="contacts">
                  <span>{{sectionForm.contacts}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="联系电话：" prop="contactsNumber">
                  <span>{{sectionForm.contactsNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="电子邮箱：">
                  <span>{{sectionForm.email}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="地址：">
                  <span>{{sectionForm.contactsAddress}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--联系人信息-->
        <!--审批记录-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #b94aff">审批记录</span>
          <el-table
            :data="recordApprovalTable"
            border>
            <el-table-column
              prop="approvrResult"
              label="审批结果"
              align="left">
              <template slot-scope="scope">
                <div v-if="Number(scope.row.approvrResult) === 2">
                  <span class="statusbox">审批通过</span>
                </div>
                <div v-if="Number(scope.row.approvrResult) === 3">
                  <span class="statusbox">审批不通过</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="approverName"
              label="操作人"
              align="left">
            </el-table-column>
            <el-table-column
              prop="approvrDate"
              label="操作时间"
              align="left">
            </el-table-column>
            <el-table-column
              prop="approvrOpinion"
              label="审批意见"
              align="left" width="700">
            </el-table-column>
          </el-table>
        </div>
        <!--审批记录-->
      </el-form>
    </div>
  </div>
</template>
<script>
import * as citydata from '@/assets/js/citydata'
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import {landProject, fileInfo} from '@/api'
export default {
  components: {
    commonFileDownload
  },
  data () {
    return {
      sectionForm: {},
      // 三级联动
      addressOptions: citydata.CityInfo,
      selectedcityOptions: [],
      // 业务类型
      fileType: 1,
      // 文件内容
      fileObjecit: {},
      // 审批记录
      recordApprovalTable: []
    }
  },
  methods: {
    detail () {
      landProject.detail(this.$route.params.objectId).then((res) => {
        this.sectionForm = res.data.landInformation
        if (res.data.landInformation.approveRecords) { // 审批记录
          this.recordApprovalTable = res.data.landInformation.approveRecords
        }
        // 三级联动赋值
        let provinceId = res.data.landInformation.provinceId
        let cityId = res.data.landInformation.cityId
        let countyId = res.data.landInformation.countyId
        this.selectedcityOptions = [provinceId, cityId, countyId]
        let url = null
        url = {
          relatedCode: res.data.landInformation.code,
          businessType: this.fileType,
          type: 2
        }
        fileInfo.get(url).then((res) => {
          if (res.data.fileInformation) {
            this.fileObjecit = res.data.fileInformation
          }
        })
      })
    }
  },
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/land-register/common.css';
</style>
