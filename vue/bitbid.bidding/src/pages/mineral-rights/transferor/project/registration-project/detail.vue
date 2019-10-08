<template>
    <div id="detailbox" class="maincontent">
      <div class="headertitle">
        <!--面包屑-->
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>项目管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/mr/transferor/project/registration-project?roledsType=1' }">登记项目</el-breadcrumb-item>
          <el-breadcrumb-item>查看项目</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
        <div class="returnboxbig">
          <router-link tag='span' to="/mr/transferor/project/registration-project">返回</router-link>
        </div>
      </div>
      <div class="contentbigbox detailcontentbigbox">
        <el-form :model="sectionForm"  label-width="100px" class="demo-ruleForm">
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
                  <el-form-item label="出让方式：" prop="name">
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
                  <el-form-item label="出让行为批准单位文件：" class="filebigbox">
                    <!--文件列表展示组件-->
                    <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                    <!--文件列表展示组件-->
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
            <div class="formmian" v-if="Number(this.$route.query.type) === 1">
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
                  <el-form-item label="矿区位置：" prop="miningLocation">
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
                  <el-form-item label="区域坐标：" prop="areaCoordinates">
                    <span>{{sectionForm.areaCoordinates}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
            <!--探矿权-->
            <!--采矿权-->
            <div class="formmian"  v-if="Number(this.$route.query.type) === 2">
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
                  <el-form-item label="区域坐标：" prop="areaCoordinates">
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
                    <el-form-item label="资源储量情况：">
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
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #0206fd">审批记录</span>
          <el-table
            :data="approvaltableData"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="approvrResult"
              label="审批结果"
              align="center">
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
              align="center">
            </el-table-column>
            <el-table-column
              prop="approvrDate"
              label="操作时间"
              align="center">
            </el-table-column>
            <el-table-column
              prop="approvrOpinion"
              label="审批意见"
              align="left" width="700">
            </el-table-column>
          </el-table>
        </div>
        <!--项目信息-->
      </div>
    </div>
</template>
<script>
import * as citydata from '@/assets/js/citydata'
import {mineTypes} from '@/assets/js/mineral/mineTypes'
import { prProject } from '@/api'
import commonFileDownload from '@/components/public-resource/mineral-rights/common/file-download.vue'
export default {
  components: {
    commonFileDownload
  },
  data () {
    return {
      // 矿权类型
      typeoptions: [{
        value: 1,
        label: '探矿权'
      }, {
        value: 2,
        label: '采矿权'
      }],
      type: 1,
      // 出让方式
      wayoptions: [{
        value: 1,
        label: '挂牌'
      }, {
        value: 2,
        label: '拍卖'
      }, {
        value: 3,
        label: '其他'
      }],
      way: 1,
      // 矿种
      speciesoptions: mineTypes,
      species: 1,
      // 矿区名称
      minenameoptions: [{
        value: 1,
        label: '矿山名称1'
      }, {
        value: 2,
        label: '矿山名称2'
      }],
      minename: 1,
      sectionForm: {
      },
      // 三级联动
      addressOptions: citydata.CityInfo,
      cityselectedOptions: [],
      approvaltableData: [],
      fileObjecit: {}
    }
  },
  methods: {
  },
  mounted () {
    let objectId = this.$route.query.objectId
    prProject.queryOne(objectId).then((res) => {
      this.sectionForm = res.data.sectionOneInfo
      if (res.data.sectionOneInfo.fileInformations) {
        this.fileObjecit = res.data.sectionOneInfo.fileInformations
      }
      this.sectionForm.region = Number(res.data.sectionOneInfo.region)
      this.approvaltableData = res.data.sectionOneInfo.approveRecords
      // 三级联动赋值
      let provinceId = res.data.sectionOneInfo.provinceId
      let cityId = res.data.sectionOneInfo.cityId
      let countyId = res.data.sectionOneInfo.countyId
      this.cityselectedOptions = [provinceId, cityId, countyId]
    })
  }
}
</script>
<style lang="less">
@import '../../../../../assets/css/mineral/common.css';
#detailbox{
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
}
</style>
