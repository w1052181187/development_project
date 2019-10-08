<template>
  <div id="addbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>项目管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/remise-invite/look-invite?roledsType=1' }">查看出让邀请确认情况</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: `/lt/transferor/remise-invite/look-invite/look-confirm/${this.$route.params.objectId}`, query: {roledsType: 1, showStatus: false}}">查看确认情况</el-breadcrumb-item>
        <el-breadcrumb-item>出让邀请书</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <router-link tag='span' :to="{ path: `/lt/transferor/remise-invite/look-invite/look-confirm/${this.$route.params.objectId}`, query: {roledsType: 1, showStatus: false}}">返回</router-link>
      </div>
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：" prop="unifiedTransCode">
                  <span>{{sectionForm.unifiedTransCode}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：" prop="investProjectCode">
                  <span>{{sectionForm.investProjectCode}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="地块编号：" prop="sectionNumber">
                  <span>{{sectionForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="出让方式：">
                  <span v-if="sectionForm.tenderings === 0">公开招标</span>
                  <span v-if="sectionForm.tenderings === 1">邀请招标</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--邀请书内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">邀请书内容</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="邀请书名称：">
                  <span>{{sectionForm.RecordNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="受让人邀请确认截止时间：">
                  <span>{{sectionForm.BidBond}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="邀请书内容：">
                  <div class="editor">
                    <editor ref="ueditor" class="ueditor" :editread="true" :content="content"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="附件：" class="filetypebox">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--邀请书内容-->
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
      content: '富文本内容富文本内容富文本内容富文本内容富文本内容', // 富文本内容
      sectionForm: {},
      // 文件内容
      fileObjecit: {
        fileName: '测试数据'
      }
    }
  },
  methods: {
  },
  mounted () {
    this.$refs.ueditor.setContent(this.content) // 设置富文本内容
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
