<template>
  <div id="projectdetailbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>矿权交易备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/mineral-rights/project-file' }">项目备案</el-breadcrumb-item>
        <el-breadcrumb-item>项目查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <router-link tag='span' to="/system-management/mineral-rights/project-file">返回</router-link>
      </div>
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm"  label-width="100px" class="demo-sectionForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：" prop="sectionName">
                  <span>{{sectionForm.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：" prop="sectionNumber">
                  <span>{{sectionForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="统一交易标识码：" prop="unifiedTransCode">
                  <span>{{sectionForm.unifiedTransCode}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="投资项目统一代码：" prop="investProjectCode">
                  <span>{{sectionForm.investProjectCode}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让类型：" prop="transferType">
                  <span v-if="sectionForm.transferType === 1">探矿权</span>
                  <span v-if="sectionForm.transferType === 2">采矿权</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让方式：" prop="landUse">
                  <span v-if="sectionForm.landUse ===1">拍卖</span>
                  <span v-if="sectionForm.landUse ===2">挂牌</span>
                  <span v-if="sectionForm.landUse ===3">其他</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="出让人名称：" prop="transferorName">
                  <span>{{sectionForm.transferorName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让行为批准单位：" prop="approvalUnit">
                  <span>{{sectionForm.approvalUnit}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让行为批准单位文件：" prop="" class="filebigbox">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--矿权信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">矿权信息</span>
          <!--探矿权-->
          <div class="formmian" v-if="this.$route.query.type === 1">
            <el-row>
              <el-col :span="8">
                <el-form-item label="矿种：" prop="minerals">
                  <el-select v-model="sectionForm.minerals" disabled>
                    <el-option
                      v-for="item in speciesoptions"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="面积(km2)：" prop="area">
                  <span>{{sectionForm.area}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让年限(年)：" prop="ageLimit">
                  <span>{{sectionForm.ageLimit}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24" class="position">
                <el-form-item label="矿区位置：" prop="">
                  <el-cascader
                    expand-trigger="hover" disabled
                    :options="addressOptions"
                    v-model="cityselectedOptions">
                  </el-cascader>
                  <span>{{sectionForm.miningLocation}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24" class="area">
                <el-form-item label="区域坐标：">
                  <span>{{sectionForm.areaCoordinates}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
          <!--探矿权-->
          <!--采矿权-->
          <div class="formmian"  v-if="this.$route.query.type === 2">
            <el-row>
              <el-col :span="8">
                <el-form-item label="矿山名称：" prop="mineName">
                  <span>{{sectionForm.mineName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="矿种：" prop="minerals">
                  <el-select v-model="sectionForm.minerals" disabled>
                    <el-option
                      v-for="item in speciesoptions"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="面积(km2)：" prop="area">
                  <span>{{sectionForm.area}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="16" class="position">
                <el-form-item label="矿区位置：" prop="miningLocation">
                  <el-cascader
                    expand-trigger="hover" disabled
                    :options="addressOptions"
                    v-model="cityselectedOptions">
                  </el-cascader>
                  <span>{{sectionForm.miningLocation}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让年限(年)：" prop="ageLimit">
                  <span>{{sectionForm.ageLimit}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24" class="area">
                <el-form-item label="区域坐标：">
                  <span>{{sectionForm.areaCoordinates}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <div>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="设计生产规模：" prop="processScale">
                    <span>{{sectionForm.processScale}}</span>
                    <span v-if="sectionForm.region === 1">万吨/年</span>
                    <span v-if="sectionForm.region === 2">万立方米/年</span>
                    <span v-if="sectionForm.region === 3">亿立方米/年</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="开采深度：" prop="miningDepth">
                    <span>{{sectionForm.miningDepth}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="开采方法：" prop="miningMethod">
                    <span>{{sectionForm.miningMethod}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24" class="area">
                  <el-form-item label="资源储量情况：" prop="resourceReserves">
                    <span>{{sectionForm.resourceReserves}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
          <!--采矿权-->
        </div>
        <!--矿权信息-->
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
                <el-form-item label="电子邮箱：" prop="email">
                  <span>{{sectionForm.email}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="地址：" prop="address">
                  <span>{{sectionForm.address}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--联系人信息-->
      </el-form>
      <!--审批记录-->
      <div class="proinfobox">
        <span class="title" style="border-left: 3px solid #0206fd">审批记录</span>
        <el-table
          :data="recordApprovalTable"
          border>
          <el-table-column
            prop="approvrResult"
            label="审批结果"
            align="left">
            <template slot-scope="scope">
              <div v-if="parseInt(scope.row.approvrResult) === 1">
                <span class="statusbox">审批通过</span>
              </div>
              <div v-if="parseInt(scope.row.approvrResult) === 0">
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
    </div>
  </div>
</template>
<script>
import { sprProject } from '@/api'
import {mineTypes} from '@/assets/js/mineral/mineTypes'
import * as citydata from '@/assets/js/citydata'
import commonFileDownload from '@/components/system-management/file-download.vue'
export default {
  components: {
    commonFileDownload
  },
  data () {
    return {
      sectionForm: {},
      recordApprovalTable: [],
      // 三级联动
      addressOptions: citydata.CityInfo,
      cityselectedOptions: [],
      approvaltableData: [],
      // 文件
      fileObjecit: {},
      // 矿种
      speciesoptions: mineTypes,
      species: 1
    }
  },
  methods: {
    // 内容获取
    contentFrom () {
      sprProject.queryOne(this.$route.params.objectId).then((res) => {
        if (res.data.sectionOneInfo.fileInformations) {
          this.fileObjecit = res.data.sectionOneInfo.fileInformations
        }
        this.sectionForm = res.data.sectionOneInfo
        this.recordApprovalTable = res.data.sectionOneInfo.approveRecords
        // 三级联动赋值
        let provinceId = res.data.sectionOneInfo.provinceId
        let cityId = res.data.sectionOneInfo.cityId
        let countyId = res.data.sectionOneInfo.countyId
        this.cityselectedOptions = [provinceId, cityId, countyId]
      })
    }
  },
  mounted () {
    this.contentFrom()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
  #projectdetailbox{
    .el-icon-arrow-up:before {
      content: "\E605";
      display: none;
    }
    .el-icon-arrow-down:before {
      content: "\E603";
      display: none;
    }
    .el-select .el-input.is-disabled .el-input__inner:hover {
      border-color: transparent;
    }
    .el-input__inner {
      color: #353535!important;
      font-size: 12px!important;
      vertical-align: middle;
    }
  }
</style>
