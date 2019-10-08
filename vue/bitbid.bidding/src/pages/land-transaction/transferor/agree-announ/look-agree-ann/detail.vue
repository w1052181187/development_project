<template>
  <div id="land_ann_subaddbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>项目管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/look-agree-ann?roledsType=1' }">查看协议出让公告</el-breadcrumb-item>
        <el-breadcrumb-item>查看出让公告</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" :rules="rules" ref="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：" prop="projectName">
                  <span>{{sectionForm.projectName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：" prop="projectNumber">
                  <span>{{sectionForm.projectNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--相关地块信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">相关地块信息</span>
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
                    <span>{{relevantMassif.assigneeName}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="成交价(万元)：">
                    <span>{{relevantMassif.transactionPrice}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
        <!--相关地块信息-->
        <!--公告内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">公告内容</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="公告名称：" prop="annoNumber">
                  <span>{{sectionForm.annoNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="公告类型：" prop="contacts">
                  <span v-if="sectionForm.annoType === 3">协议出让公告</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="公告发布时间：" class="bitianicon timesbox">
                  <span>{{sectionForm.annStartTime}}</span>至<span>{{sectionForm.annEndTime}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="公告内容：" class="bitianicon">
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
      </el-form>
    </div>
  </div>
</template>
<script>
import {sumType} from '@/assets/js/validate'
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import { landAnno, fileInfo } from '@/api'
export default {
  components: {
    editor,
    commonFileDownload
  },
  data () {
    // 数字
    var sumTypes = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入数字'))
      } else if (!sumType(value)) {
        callback(new Error('请输入数字或两位小数'))
      } else {
        callback()
      }
    }
    return {
      sectionForm: {
        relevantMassifData: [ // 相关块地信息
          {
            massifNumber: '关于安徽省铜陵市盛冲金多',
            RecordNumber: '4109917BA0017',
            Bond: '356 ',
            FloorPrice: 0,
            valuationReport: '上海宏宙房地产开发有限公司',
            LandUse: '工业用地',
            EstimatedPrice: '11111'
          },
          {
            massifNumber: '关于安徽省铜陵市盛冲金多',
            RecordNumber: '4109917BA0017',
            Bond: '356 ',
            valuationReport: '上海宏宙房地产开发有限公司',
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
      rules: {
        valuationReportRules: [
          { required: true, message: '请输入受让人名称', trigger: ['blur', 'change'] },
          { min: 1, max: 600, message: '长度在 1~600个字符', trigger: ['blur', 'change'] }
        ],
        bidBondRules: [
          { required: true, message: '请输入受让人名称', trigger: ['blur', 'change'] },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] },
          { validator: sumTypes, trigger: ['blur', 'change'] }
        ]
      },
      // 业务类型
      fileType: 16,
      // 文件内容
      fileObjecit: {
        fileName: '测试数据'
      }
    }
  },
  methods: {
    detail () {
      landAnno.detail(this.$route.params.objectId).then(res => {
        this.sectionForm = res.data.landAnno
        this.content = res.data.landAnno.content
        this.$refs.ueditor.setContent(this.content) // 设置富文本内容
        let url = {
          relatedCode: res.data.landAnno.code,
          businessType: this.fileType,
          type: 2
        }
        fileInfo.get(url).then((res) => {
          this.fileObjecit = res.data.fileInformation
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
  @import '../../../../../assets/css/land-register/common.css';
  #land_ann_subaddbox{
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
