<template>
  <div id="mincheckbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>矿权交易备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/mineral-rights/mining-right-registration-file' }">矿业权登记公告备案</el-breadcrumb-item>
        <el-breadcrumb-item>查看公告</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="miningAnnoForm" ref="miningAnnoForm" label-width="100px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：" prop="projectName">
                  <span>{{miningAnnoForm.projectName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：" prop="projectNumber">
                  <span>{{miningAnnoForm.projectNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="统一交易标识码：" prop="unifiedTransCode">
                  <span>{{miningAnnoForm.unifiedTransCode}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="矿种类型：" prop="investProjectCode">
                  <span v-if="this.$route.query.mineralSpecies === 1">油气</span>
                  <span v-if="this.$route.query.mineralSpecies === 2">非油气</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="矿权类型：" prop="typeoptions">
                  <span v-if="this.$route.query.MineralRights === 1">探矿权</span>
                  <span v-if="this.$route.query.MineralRights === 2">采矿权</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="许可证号：" prop="licenceType">
                  <span>{{miningAnnoForm.licenceType}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目类型：" prop="projectType">
                  <el-select v-model="miningAnnoForm.projectType" disabled>
                    <el-option
                      v-for="item in prjectptions"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="采矿权人名称：" prop="miningName">
                  <span>{{miningAnnoForm.miningName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="勘察单位名称：" prop="prospectUnitName">
                  <span>{{miningAnnoForm.prospectUnitName}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="发证机关：" prop="crrtificateOrgan" class="content_notice">
                  <span>{{miningAnnoForm.crrtificateOrgan}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="有效期：" prop="approvalUnit" class="content_notice">
                  <span>{{miningAnnoForm.annoStartTime}}</span> 至 <span>{{miningAnnoForm.annoEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="批准文件：" class="filebigbox">
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
          <div class="formmian" v-if="parseInt(this.$route.query.MineralRights) === 1">
            <el-row>
              <el-col :span="8">
                <el-form-item label="矿种：" prop="minerals">
                  <el-select v-model="miningAnnoForm.minerals" disabled>
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
                  <span>{{miningAnnoForm.area}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让年限(年)：" prop="ageLimit">
                  <span>{{miningAnnoForm.ageLimit}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24" class="position">
                <el-form-item label="矿区位置：" prop="miningLocation">
                  <el-cascader
                    expand-trigger="hover" disabled
                    :options="addressOptions"
                    v-model="selectedcityOptions">
                  </el-cascader>
                  <span>{{miningAnnoForm.miningLocation}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24" class="area">
                <el-form-item label="区域坐标：" prop="areaCoordinates">
                  <span>{{miningAnnoForm.areaCoordinates}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
          <!--探矿权-->
          <!--采矿权-->
          <div class="formmian"  v-if="parseInt(this.$route.query.MineralRights) === 2">
            <el-row>
              <el-col :span="8">
                <el-form-item label="矿山名称：" prop="mineName">
                  <span>{{miningAnnoForm.mineName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="矿种：" prop="minerals">
                  <el-select v-model="miningAnnoForm.minerals" disabled>
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
                  <span>{{miningAnnoForm.area}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="16" class="position">
                <el-form-item label="矿区位置" prop="miningLocation">
                  <el-cascader
                    expand-trigger="hover" disabled
                    :options="addressOptions"
                    v-model="selectedcityOptions">
                  </el-cascader>
                  <span>{{miningAnnoForm.miningLocation}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让年限(年)：" prop="ageLimit">
                  <span>{{miningAnnoForm.ageLimit}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24" class="area">
                <el-form-item label="区域坐标：" prop="areaCoordinates">
                  <span>{{miningAnnoForm.areaCoordinates}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <!--采矿权-->
            <div>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="设计生产规模：" prop="processScale">
                    <span>{{miningAnnoForm.processScale}}</span>
                    <span v-if="miningAnnoForm.region === 1">万吨/年</span>
                    <span v-if="miningAnnoForm.region === 2">万立方米/年</span>
                    <span v-if="miningAnnoForm.region === 3">亿立方米/年</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="开采深度：" prop="miningDepth">
                    <span>{{miningAnnoForm.miningDepth}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="开采方法：" prop="miningMethod">
                    <span>{{miningAnnoForm.miningMethod}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24" class="area">
                  <el-form-item label="资源储量情况："  prop="resourceReserves">
                    <span>{{miningAnnoForm.resourceReserves}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
            <!--采矿权-->
          </div>
          <!--采矿权-->
        </div>
        <!--矿权信息-->
        <!--公告内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">公告内容</span>
          <div class="formmain">
            <el-row>
              <el-col :span="12">
                <el-form-item label="公告名称：" prop="annoName" class="content_notice">
                  <span>{{miningAnnoForm.annoName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="公告时间：" class="content_notice">
                  <span>{{miningAnnoForm.annoStartTime}}</span> 至 <span>{{miningAnnoForm.annoEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="公告内容：" prop="contactsNumber">
                  <div class="editor">
                    <editor ref="ueditor" class="ueditor" :editread="editread" :content="content"></editor>
                  </div>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="附件：" prop=""  class="filebigbox">
                  <fileOtherDownload :fileTypesObjecit="fileTypesObjecit"></fileOtherDownload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--公告内容-->
        <!--联系人信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #0206fd">联系人信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="公司名称：" prop="companyName">
                  <span>{{miningAnnoForm.companyName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="联系人：" prop="contacts">
                  <span>{{miningAnnoForm.contacts}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="联系电话：" prop="contactsNumber">
                  <span>{{miningAnnoForm.contactsNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="电子邮箱：">
                  <span>{{miningAnnoForm.email}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="地址：">
                  <span>{{miningAnnoForm.address}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--联系人信息-->
      </el-form>
      <!--项目审批-->
      <div class="proinfobox">
        <span class="title" style="border-left: 3px solid #b94aff">项目审批</span>
        <!--审批组件-->
        <examineApprove @approveForm="approveForm"></examineApprove>
        <!--审批组件-->
      </div>
      <!--项目审批-->
      <!--审批记录-->
      <div class="proinfobox">
        <span class="title" style="border-left: 3px solid #ff4a4a">审批记录</span>
        <el-table
          :data="recordApprovalTable"
          border
          header-cell-class-name="tabletitles">
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
            prop="lastUpdateDate"
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
      <div class="submit-radio">
        <el-button type="primary" @click="submitForm()">提交</el-button>
        <el-button class="cancel" @click="cancelBtn">取消</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import * as citydata from '@/assets/js/citydata'
import editor from '@/components/public-resource/mineral-rights/common/ueditor/ueditor.vue'
import examineApprove from '@/components/system-management/examine-approve.vue'
import { sannoProject, sApproveRecord } from '@/api'
import {mineTypes} from '@/assets/js/mineral/mineTypes'
import commonFileDownload from '@/components/public-resource/mineral-rights/common/file-download.vue'
import fileOtherDownload from '@/components/public-resource/mineral-rights/common/file-other-download.vue'
export default {
  components: {
    editor,
    examineApprove,
    commonFileDownload,
    fileOtherDownload
  },
  data () {
    return {
      ruleForm: {},
      // 审核意见 单选按钮
      radio: '1',
      // 富文本
      editread: true,
      content: '',
      sectionForm: {
        provinceId: '',
        cityId: '',
        countyId: '',
        investProjectCode: this.$route.query.mineralSpecies,
        typeoptions: this.$route.query.MineralRights
      },
      dataRange: [], // 时间
      // 矿权类型
      typeoptions: [{
        value: 1,
        label: '探矿权'
      }, {
        value: 2,
        label: '采矿权'
      }],
      type: this.$route.query.type,
      // 出让方式
      wayoptions: [{
        value: 1,
        label: '公开招标'
      }, {
        value: 2,
        label: '邀请招标'
      }, {
        value: 3,
        label: '挂牌'
      }, {
        value: 4,
        label: '拍卖'
      }, {
        value: 5,
        label: '其他'
      }],
      way: 1,
      // 项目类型
      prjectptions: [{
        value: 1,
        label: '新立'
      }, {
        value: 2,
        label: '变更'
      }, {
        value: 3,
        label: '延续'
      }, {
        value: 4,
        label: '保留'
      }, {
        value: 5,
        label: '注销'
      }],
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
      fileList: [],
      miningAnnoForm: {},
      // 矿种
      speciesoptions: mineTypes,
      // 三级联动
      addressOptions: citydata.CityInfo,
      selectedcityOptions: [],
      recordApprovalTable: [],
      url: {},
      fileObjecit: {}, // 批准文件
      fileTypesObjecit: {} // 附件
    }
  },
  methods: {
    approveForm (val) {
      this.checkResult = val
      this.checkResult.approvalId = this.$route.query.objectId
      this.checkResult.approvalType = 6
    },
    // 内容获取
    contentFrom () {
      sannoProject.queryOne(this.$route.query.objectId).then((res) => {
        if (res.data.miningAnnocement.fileInformations) {
          this.fileObjecit = res.data.miningAnnocement.fileInformations
        }
        if (res.data.miningAnnocement.fileInformation) {
          this.fileTypesObjecit = res.data.miningAnnocement.fileInformation
        }
        this.miningAnnoForm = res.data.miningAnnocement
        this.recordApprovalTable = res.data.miningAnnocement.approveRecords
        this.miningAnnoForm.region = Number(res.data.miningAnnocement.region)
        // 三级联动赋值
        let provinceId = res.data.miningAnnocement.provinceId
        let cityId = res.data.miningAnnocement.cityId
        let countyId = res.data.miningAnnocement.countyId
        this.selectedcityOptions = [provinceId, cityId, countyId]
      })
    },
    // 提交数据
    submitForm () {
      if (this.checkResult === undefined || this.checkResult.approvrResult === undefined) {
        this.$message({
          type: 'warning',
          message: '请选择审批结果'
        })
        return false
      }
      sApproveRecord.save(this.checkResult).then((res) => {
        if (this.checkResult.approvrResult === 0) {
          this.url.status = 3
        } else {
          this.url.status = 2
        }
        this.url.objectId = this.$route.query.objectId
        sannoProject.update(this.url).then((res) => {
          this.$router.push({path: '/system-management/mineral-rights/mining-right-registration-file'})
        })
      })
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/system-management/mineral-rights/mining-right-registration-file'})
    }
  },
  computed: {},
  mounted () {
    this.contentFrom()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
  #mincheckbox{
    .el-form-item__content {
      margin-left: 168px!important;
      width: 60%;
    }
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
