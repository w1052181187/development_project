<template>
  <div id="land_ann_lookdetailbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>土地交易备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/land-transaction/agreement/index'}">协议出让公告备案</el-breadcrumb-item>
        <el-breadcrumb-item>协议出让公告查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">项目信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：">
                  <span>{{sectionForm.projectName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：">
                  <span>{{sectionForm.projectNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--相关地块信息-->
        <div class="proinfobox">
          <span class="title">相关地块信息</span>
          <div class="formmian">
            <div v-for="(relevantMassif,index) in sectionForm.landInformations" :key="index">
              <el-row>
                <span style="margin-left: 80px;margin-bottom: 22px;color: #000;font-weight: 800">块地{{index + 1}}</span>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="地块编号：">
                    <span>{{relevantMassif.sectionNumber}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="受让人名称：">
                    <span>{{relevantMassif.sectionNumber}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="成交价(万元)：">
                    <span>{{relevantMassif.sectionNumber}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
        <!--相关地块信息-->
        <!--公告内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid rgb(45, 150, 49)">公告内容</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="公告名称：">
                  <span>{{sectionForm.annoNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="公告类型：">
                  <span>协议出让公告</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="公告发布时间：">
                  <span>{{sectionForm.annStartTime}}</span> 至 <span>{{sectionForm.annEndTime}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="公告内容：">
                  <div class="editor">
                    <editor ref="ueditor" class="ueditor" :editread="true" :content="content"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24" class="fujianbox">
                <el-form-item label="附件：">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--公告内容-->
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
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import { landAnno, fileInfo } from '@/api'
export default {
  components: {
    commonFileDownload,
    editor
  },
  data () {
    return {
      sectionForm: {},
      content: '', // 公告内容
      // 业务类型
      fileType: 16,
      // 文件内容
      fileObjecit: {
        fileName: ''
      },
      recordApprovalTable: []
    }
  },
  methods: {
    landAnnoInfo () {
      landAnno.detail(this.$route.params.objectId).then((res) => {
        this.sectionForm = res.data.landAnno
        if (res.data.landAnno.approveRecords18) { // 审批记录
          this.recordApprovalTable = res.data.landAnno.approveRecords18
        }
        this.content = res.data.landAnno.content
        this.$refs.ueditor.setContent(this.content) // 设置富文本内容
        let url = {
          relatedCode: res.data.landAnno.code,
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
    this.landAnnoInfo()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/land-register/common.css';
  #land_ann_lookdetailbox{
    .timesbox .el-form-item__content{
      width: 74%;
    }
    .hourbox .el-date-editor{
      width: 100%;
    }
    .fujianbox .el-form-item__content{
      margin-left: 0!important;
    }
  }
</style>
