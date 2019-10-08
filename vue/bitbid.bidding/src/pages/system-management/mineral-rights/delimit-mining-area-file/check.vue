<template>
  <div id="otherdetailebox"  class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>矿权交易备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/mineral-rights/mineral-rightsdelimit-mining-area-file' }">规定矿区范围公示备案</el-breadcrumb-item>
        <el-breadcrumb-item>公示审批</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="otherPublicityForm" :rules="rules" ref="otherPublicityForm" label-width="100px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：" prop="projectName">
                  <span>{{otherPublicityForm.projectName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：" prop="projectNumber">
                  <span>{{otherPublicityForm.projectNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="统一交易标识码：" prop="unifiedTransCode">
                  <span>{{otherPublicityForm.unifiedTransCode}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="申请人名称：" prop="applicantName">
                  <span>{{otherPublicityForm.applicantName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="勘察单位名称：" prop="prospectUnitName">
                  <span>{{otherPublicityForm.prospectUnitName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="审批机关：" prop="approvingAuthority">
                  <span>{{otherPublicityForm.approvingAuthority}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="受理时间：" prop="approvingAuthority">
                  <span>{{otherPublicityForm.acceptedDate}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="批准文件：" prop="approveFileid" class="filebigbox">
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
          <!--采矿权-->
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="矿山名称：" prop="mineName">
                  <span>{{otherPublicityForm.mineName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="矿种：" prop="minerals">
                  <el-select v-model="otherPublicityForm.minerals" disabled>
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
                  <span>{{otherPublicityForm.area}}</span>
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
                  <span>{{otherPublicityForm.miningLocation}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让年限(年)：" prop="ageLimit">
                  <span>{{otherPublicityForm.ageLimit}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24" class="area">
                <el-form-item label="区域坐标：" prop="areaCoordinates">
                  <span>{{otherPublicityForm.areaCoordinates}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="设计生产规模：" prop="processScale">
                  <span>{{otherPublicityForm.processScale}}</span>
                  <span v-if="otherPublicityForm.region === 1">万吨/年</span>
                  <span v-if="otherPublicityForm.region === 2">万立方米/年</span>
                  <span v-if="otherPublicityForm.region === 3">亿立方米/年</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="开采深度：" prop="miningDepth">
                  <span>{{otherPublicityForm.miningDepth}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="开采方法：" prop="miningMethod">
                  <span>{{otherPublicityForm.miningMethod}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24" class="area">
                <el-form-item label="资源储量情况："  prop="resourceReserves">
                  <span>{{otherPublicityForm.resourceReserves}}</span>
                </el-form-item>
              </el-col>
            </el-row>
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
                <el-form-item label="公示名称：" prop="publicityName" class="content_notice">
                  <span>{{otherPublicityForm.publicityName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="公示时间：" class="content_notice">
                  <span>{{otherPublicityForm.publicityStartTime}}</span> 至 <span>{{otherPublicityForm.publicityEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="公示内容：" prop="contactsNumber" style="padding: 1px">
                  <div class="editor">
                    <editor ref="ueditor" class="ueditor" :editread="editread" :content="content"></editor>
                  </div>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="附件：" prop="" class="filebigbox">
                  <fileOtherDownload :fileTypesObjecit="fileTypesObjecit"></fileOtherDownload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--公告内容-->
        <!--联系人信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">联系人信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="公司名称：" prop="companyName">
                  <span>{{otherPublicityForm.companyName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="联系人：" prop="contacts">
                  <span>{{otherPublicityForm.contacts}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="联系电话：" prop="contactsNumber">
                  <span>{{otherPublicityForm.contactsNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="电子邮箱：">
                  <span>{{otherPublicityForm.email}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="地址：">
                  <span>{{otherPublicityForm.address}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--联系人信息-->
        <!--项目审批-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #0206fd">项目审批</span>
          <!--审批组件-->
          <examineApprove @approveForm="approveForm"></examineApprove>
          <!--审批组件-->
        </div>
        <!--项目审批-->
        <!--审批记录-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #0206fd">审批记录</span>
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
        <!--提交审核-->
        <div class="submit-radio">
          <el-button type="primary" @click="submitForm()">提交</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>
<script>
import * as citydata from '@/assets/js/citydata'
import editor from '@/components/public-resource/mineral-rights/common/ueditor/ueditor.vue'
import commonFileDownload from '@/components/public-resource/mineral-rights/common/file-download.vue'
import fileOtherDownload from '@/components/public-resource/mineral-rights/common/file-other-download.vue'
import examineApprove from '@/components/system-management/examine-approve.vue'
import { sotherProject, sApproveRecord } from '@/api'
import {mineTypes} from '@/assets/js/mineral/mineTypes'
export default {
  components: {
    editor,
    commonFileDownload,
    fileOtherDownload,
    examineApprove
  },
  data () {
    return {
      editread: true, // 富文本状态
      content: '', // 富文本内容
      otherPublicityForm: {},
      dataRange: [], // 时间
      valueTime: '',
      // 矿权类型
      typeoptions: [{
        value: 1,
        label: '探矿权'
      }, {
        value: 2,
        label: '采矿权'
      }],
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
      // 关联相关信息
      tableDataSeletion: [
      ],
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
      // 单位
      companyArry: [
        {
          value: 1,
          label: '万吨/年'
        }, {
          value: 2,
          label: '万立方米/年'
        },
        {
          value: 3,
          label: '亿立方米/年'
        }
      ],
      // 矿种
      speciesoptions: mineTypes,
      species: 1,
      minename: 1,
      fileList: [],
      rules: {
      },
      // 三级联动
      addressOptions: citydata.CityInfo,
      cityselectedOptions: [],
      ruleForm: {},
      approvaltableData: [],
      // 审核意见 单选按钮
      radio: '1',
      recordApprovalTable: [],
      fileObjecit: {}, // 批准文件
      fileTypesObjecit: {} // 附件
    }
  },
  methods: {
    approveForm (val) {
      this.checkResult = val
      this.checkResult.approvalId = this.$route.query.objectId
      this.checkResult.approvalType = 5
    },
    // 内容获取
    contentFrom () {
      sotherProject.queryOne(this.$route.query.objectId).then((res) => {
        if (res.data.selectotherPublicity.fileInformations) {
          this.fileObjecit = res.data.selectotherPublicity.fileInformations
        }
        if (res.data.selectotherPublicity.fileInformation) {
          this.fileTypesObjecit = res.data.selectotherPublicity.fileInformation
        }
        this.otherPublicityForm = res.data.selectotherPublicity
        this.recordApprovalTable = res.data.selectotherPublicity.approveRecords
        this.content = res.data.selectotherPublicity.publicityText
        this.$refs.ueditor.setContent(this.content) // 设置富文本内容
        // 三级联动赋值
        let provinceId = res.data.selectotherPublicity.provinceId
        let cityId = res.data.selectotherPublicity.cityId
        let countyId = res.data.selectotherPublicity.countyId
        this.cityselectedOptions = [provinceId, cityId, countyId]
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
          this.otherPublicityForm.status = 3
        } else {
          this.otherPublicityForm.status = 2
        }
        this.otherPublicityForm.objectId = this.$route.query.objectId
        sotherProject.update(this.otherPublicityForm).then((res) => {
          this.$router.push({path: '/system-management/mineral-rights/delimit-mining-area-file'})
        })
      })
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/system-management/mineral-rights/delimit-mining-area-file'})
    }
  },
  mounted () {
    this.contentFrom()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
  #otherdetailebox{
    .el-icon-arrow-up:before{
      display: none;
    }
    .el-icon-arrow-down:before{
      display: none;
    }
  }
</style>
