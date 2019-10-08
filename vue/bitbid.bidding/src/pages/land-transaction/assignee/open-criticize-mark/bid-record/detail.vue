<template>
  <div id="contasscontractdetailebox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false'}">首页</el-breadcrumb-item>
        <el-breadcrumb-item>开标、评标</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/open-criticize-mark/bid-record/index?roledsType=2&showStatus=false'}">查看开标记录表</el-breadcrumb-item>
        <el-breadcrumb-item>查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="contractForm" ref="contractForm" label-width="150px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
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
                <el-form-item label="公告名称：">
                  <span>{{contractForm.annoNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--开标信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">开标信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="开标时间：">
                  <span>{{contractForm.openingTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="开标方式：">
                  <span v-if="contractForm.bidOpeningWay === 1">在线开标</span>
                  <span v-if="contractForm.bidOpeningWay === 2">线下开标</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="开标地点：">
                  <span>{{contractForm.placeOfBidOpening}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-col :span="24">
                  <el-form-item label="开标记录内容：">
                    <div class="editor">
                      <editor ref="ueditor" class="ueditor" :editread="true" :content="content"></editor>
                    </div>
                  </el-form-item>
                </el-col>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="开标参与人员：">
                  <span>{{contractForm.personnel}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="开标记录表：" class="filebigbox">
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
