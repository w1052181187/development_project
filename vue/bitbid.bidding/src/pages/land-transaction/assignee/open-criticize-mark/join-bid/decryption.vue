<template>
  <div id="decryption_box"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false'}">首页</el-breadcrumb-item>
        <el-breadcrumb-item>开标、评标</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/open-criticize-mark/join-bid/index?roledsType=2&showStatus=false'}">参加开标会</el-breadcrumb-item>
        <el-breadcrumb-item>网上解密</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="contractForm" ref="contractForm" label-width="150px" class="demo-ruleForm">
        <!--开标室-->
        <div class="proinfobox">
          <span class="title">解密</span>
          <div class="formmian">
            <div class="decode_start_box" v-if="Number(this.$route.query.isRemote) === 1">
              <div>重要提示：</div>
              <div class="prompt_title">投标人必须在规定时间内完成解密工作。如在规定时间内不能成功完成网上远程解密，有不能进行现场解密，则该份投标文件视为放弃此次投标文件解密，而无法读取导入。</div>
            </div>
            <el-row>
              <el-col :span="8">
                <el-form-item label="公告名称：">
                  <span>{{contractForm.annoNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="地块编号：">
                  <span>{{contractForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="截标时间：">
                  <span>{{contractForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：">
                  <span>{{contractForm.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：">
                  <span>{{contractForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="即时通知：">
                  <span>{{contractForm.notification}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="投标文件：" class="filebigbox">
                  <!--文件列表展示组件-->
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                  <!--文件列表展示组件-->
                  <el-button type="primary" size="mini" v-if="Number(this.$route.query.isRemote) === 1">点击解密</el-button>
                  <div class="decode_end_box" v-if="Number(this.$route.query.isRemote) === 1">投标文件解密指令已下达，解密时间为：从2018年7月31日12：12到2018年7月31日12：32</div>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--开标室-->
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
      contractForm: {},
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
  #decryption_box{
    .table_box{
      display: inline-block;
      min-width: 100px;
      max-width: 300px;
      vertical-align: top;
    }
    .decode_start_box{
      width: 90%;
      margin: 0 auto;
      padding: 20px 0;
      color: #ff0000;
      font-size: 15px;
      font-weight: 700;
    }
    .prompt_title{
      text-indent: 20px;
      margin-top: 5px;
      font-size: 15px;
    }
    .decode_end_box{
      color: #ff0000;
      font-size: 14px;
    }
  }
</style>
