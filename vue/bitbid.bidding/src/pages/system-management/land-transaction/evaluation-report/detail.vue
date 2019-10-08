<template>
  <div id="land_ann_lookdetailbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>土地交易备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/land-transaction/evaluation-report/index'}">招标评标报告备案</el-breadcrumb-item>
        <el-breadcrumb-item>招标评标报告查看</el-breadcrumb-item>
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
          <span class="title" style="border-left: 3px solid #2d9631">报告信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="24">
                <el-form-item label="评标报告：" class="filebigbox">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="中标候选人名单：">
                  <el-table
                    :data="projectData"
                    style="width: 100%"
                    border
                    header-cell-class-name="tabletitles">
                    <el-table-column
                      prop="sectionNumber"
                      align="left"
                      label="中标候选人"
                      show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column
                      prop="methodOfSupply"
                      label="中标价格（元）"
                      align="left">
                    </el-table-column>
                    <el-table-column
                      prop="methodOfTransfer"
                      label="中标候选人排名"
                      align="left">
                    </el-table-column>
                  </el-table>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--报告信息-->
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
      sectionForm: {},
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
