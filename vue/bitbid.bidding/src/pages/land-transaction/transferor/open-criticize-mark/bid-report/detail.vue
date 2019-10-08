<template>
  <div id="marginappaddbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>标后业务</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/open-criticize-mark/bid-report?roledsType=1&showStatus=false' }">提交评标报告及中标候选人</el-breadcrumb-item>
        <el-breadcrumb-item>查看报告</el-breadcrumb-item>
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
        <!--报告信息-->
        <div class="proinfobox">
          <span class="title">报告信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="24" class="filebigbox">
                <el-form-item label="评标报告：">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="中标候选人名单：" class="add_table">
                  <el-table :data="bidData" border header-cell-class-name="tabletitles">
                    <el-table-column
                      prop="winningName"
                      label="中标候选人">
                    </el-table-column>
                    <el-table-column
                      prop="winningPice"
                      label="中标价格（元）">
                    </el-table-column>
                    <el-table-column
                      prop="winningRanking"
                      label="中标候选人排名">
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
      refundedSecurityForm: {},
      bidData: [
        {
          winningName: '关于安徽省铜陵市盛冲金多金属矿勘探探',
          winningPice: '345',
          winningRanking: '1'
        }
      ],
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
