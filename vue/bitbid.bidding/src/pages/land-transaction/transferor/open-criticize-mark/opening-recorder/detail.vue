<template>
  <div id="marginappaddbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>标后业务</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/open-criticize-mark/opening-recorder?roledsType=1&showStatus=false' }">提交开标记录表</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/open-criticize-mark/opening-recorder/select-plot?roledsType=1&showStatus=false' }">选择地块</el-breadcrumb-item>
        <el-breadcrumb-item>查看开标记录表</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="refundedSecurityForm" label-width="160px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="12">
                <el-form-item label="项目名称：">
                  <span>{{refundedSecurityForm.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="项目编号：">
                  <span>{{refundedSecurityForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="地块编号：">
                  <span>{{refundedSecurityForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="公告名称：">
                  <span>{{refundedSecurityForm.annoNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--开标信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="开标时间：" prop="openStartTime">
                  <span>{{refundedSecurityForm.openStartTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="开标方式：" prop="bidEvaluationMethods">
                  <span v-if="refundedSecurityForm.bidEvaluationMethods === 0">在线开标</span>
                  <span v-if="refundedSecurityForm.bidEvaluationMethods === 1">线下开标</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="开标地点：" prop="place">
                  <span>{{refundedSecurityForm.place}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="开标记录内容：" class="bitianicon">
                  <div class="editor">
                    <editor ref="openUeditor" class="ueditor" :editread="true" :content="openContent"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="开标参与人员：" class="bitianicon">
                  <div class="editor">
                    <editor ref="partUeditor" class="ueditor" :editread="true" :content="partContent"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="开标记录表：" class="bitianicon filebigbox">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--开标信息-->
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
export default {
  components: {
    editor,
    commonFileDownload
  },
  data () {
    return {
      refundedSecurityForm: {},
      openContent: '', // 开标记录内容
      partContent: '', // 开标记录内容
      // 文件内容
      fileObjecit: {
        fileName: '测试数据'
      }
    }
  },
  methods: {
    list () {}
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
