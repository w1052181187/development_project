<template>
  <div id="contasscontractdetailebox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false'}">首页</el-breadcrumb-item>
        <el-breadcrumb-item>资格预审会</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/prequalification/tender-documents/index?roledsType=2&showStatus=false'}">资格预审申请文件澄清</el-breadcrumb-item>
        <el-breadcrumb-item>查看澄清</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="contractForm" ref="contractForm" label-width="150px" class="demo-ruleForm">
        <!--质疑信息-->
        <div class="proinfobox">
          <span class="title">异议信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="12">
                <el-form-item label="项目名称：">
                  <span>{{contractForm.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="项目编号：">
                  <span>{{contractForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="地块编号：">
                  <span>{{contractForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="标题：">
                  <span>{{contractForm.title}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-col :span="24">
                  <el-form-item label="异议内容：">
                    <div class="editor">
                      <editor ref="ueditor" class="ueditor" :editread="true" :content="content"></editor>
                    </div>
                  </el-form-item>
                </el-col>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="附件：" class="filebigbox">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--质疑信息-->
        <!--澄清信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">澄清信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="24">
                <el-col :span="24">
                  <el-form-item label="澄清内容：">
                    <div class="editor">
                      <editor ref="ueditor" class="ueditor" :editread="true" :content="content"></editor>
                    </div>
                  </el-form-item>
                </el-col>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="附件：" class="filebigbox">
                  <commonFileDownload :fileObjecit="fileObjecitOther"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--澄清信息-->
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
export default {
  components: {
    commonFileDownload,
    editor
  },
  data () {
    return {
      contractForm: {},
      content: '',
      // 文件
      fileObjecit: {
        fileName: '测试数据'
      },
      fileObjecitOther: {
        fileName: '测试数据'
      }
    }
  },
  methods: {},
  mounted () {}
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
