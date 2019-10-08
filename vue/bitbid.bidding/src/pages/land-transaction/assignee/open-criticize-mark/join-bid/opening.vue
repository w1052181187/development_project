<template>
  <div id="contasscontractdetailebox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false'}">首页</el-breadcrumb-item>
        <el-breadcrumb-item>开标、评标</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/open-criticize-mark/join-bid/index?roledsType=2&showStatus=false'}">参加开标会</el-breadcrumb-item>
        <el-breadcrumb-item>网上开标室</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="contractForm" ref="contractForm" label-width="150px" class="demo-ruleForm">
        <!--开标室-->
        <div class="proinfobox">
          <span class="title">开标室</span>
          <div class="formmian">
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
          </div>
        </div>
        <!--开标室-->
        <!--开标阶段-->
        <div class="proinfobox" v-if="Number(this.$route.query.isRemote) === 1">
          <span class="title" style="border-left: 3px solid #2d9631">开标阶段</span>
          <div class="prompt_title">开标阶段：现在是开标时间，请投标人及时在规定时间内点击“网上解密”按钮，进行解密！</div>
          <el-table
            ref="multipleTable"
            :data="tableDataSeletion"
            tooltip-effect="dark"
            style="width: 100%"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="sectionNumber"
              align="left"
              label="组织机构代码"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="annoNumber"
              align="left"
              label="投标单位"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="transferType"
              align="left"
              label="技术标"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="ageLimit"
              align="left"
              label="总报价（万元）"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="bidderName"
              align="left"
              label="下浮（%）"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="strikePrice"
              align="left"
              label="工期（天）"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="dealTime"
              align="left"
              label="质量标准"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="dealTime"
              align="left"
              label="是否合格"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="dealTime"
              align="left"
              label="不合格原因"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </div>
        <!--开标阶段-->
      </el-form>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      contractForm: {},
      // 开标阶段
      tableDataSeletion: [
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '皖探转让公示〔2018〕3号',
          ageLimit: '20',
          bidderName: '铜陵凯鑫商贸有限公司',
          strikePrice: '666',
          dealTime: '2017-9-6 10:08:00'
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '皖探转让公示〔2018〕3号',
          ageLimit: '20',
          bidderName: '铜陵凯鑫商贸有限公司',
          strikePrice: '666',
          dealTime: '2017-9-6 10:08:00'
        }
      ]
    }
  },
  methods: {},
  mounted () {}
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
  .prompt_title{
    color: #ff0000;
    font-weight: 700;
    font-size: 16px;
    padding: 20px;
    padding-top: 0;
    box-sizing: border-box;
  }
</style>
