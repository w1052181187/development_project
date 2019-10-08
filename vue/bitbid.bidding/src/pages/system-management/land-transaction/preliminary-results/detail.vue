<template>
  <div id="land_ann_lookdetailbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>土地交易备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/land-transaction/preliminary-results/index'}">资格预审结果通知书备案</el-breadcrumb-item>
        <el-breadcrumb-item>通知书查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="12">
                <el-form-item label="项目名称：">
                  <span>{{sectionForm.projectName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="项目编号：">
                  <span>{{sectionForm.projectNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="公告名称：">
                  <span>{{sectionForm.annoNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="地块编号：">
                  <span>{{sectionForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--报告信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">报告信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="24" class="fujianbox">
                <el-form-item label="资格审查报告：">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="提交人：">
                  <span>{{sectionForm.subName}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="提交时间：">
                  <span>{{sectionForm.subTime}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--报告信息-->
        <!--时间信息-->
        <div class="proinfobox">
          <span class="title">时间信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="招标文件获取时间：">
                  <span>{{sectionForm.projectName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="递交投标文件截止时间：">
                  <span>{{sectionForm.projectNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="投标文件递交方法：">
                  <span>{{sectionForm.tenderDocEndMethod}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--时间信息-->
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
export default {
  components: {
    commonFileDownload
  },
  data () {
    return {
      sectionForm: {
        relevantMassifData: [ // 相关块地信息
          {
            massifNumber: '关于安徽省铜陵市盛冲金多',
            RecordNumber: '4109917BA0017',
            Bond: '356 ',
            FloorPrice: 0,
            LandUse: '工业用地',
            EstimatedPrice: '11111'
          },
          {
            massifNumber: '关于安徽省铜陵市盛冲金多',
            RecordNumber: '4109917BA0017',
            Bond: '356 ',
            FloorPrice: 0,
            LandUse: '工业用地',
            EstimatedPrice: '11111'
          }
        ],
        type: 1,
        isAllowedBidding: 1,
        bidEvaluationMethod: 1,
        fileOpenMode: 1,
        reviewTheWay: 1,
        reviewMethod: 1,
        bidOpeningWay: 1,
        bidEvaluationMethods: 1
      },
      content: '', // 公告内容
      // 文件内容
      fileObjecit: {
        fileName: '测试数据'
      },
      projectData: []
    }
  },
  methods: {},
  mounted () {}
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
