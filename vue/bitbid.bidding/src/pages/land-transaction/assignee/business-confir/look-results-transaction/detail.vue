<template>
  <div id="lookdrestaileindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false'}">首页</el-breadcrumb-item>
        <el-breadcrumb-item>成交确认</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/business-confir/look-results/index?roledsType=2&showStatus=false'}">查看成交结果公示</el-breadcrumb-item>
        <el-breadcrumb-item>查看成交结果公示</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm" ref="ruleForm" label-width="150px" class="demo-ruleForm">
        <!--公告内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">公示信息</span>
          <el-row>
            <el-col :span="12">
              <el-form-item label="公示名称：" prop="publicityName">
                <span>{{ruleForm.publicityName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="公示时间：" prop="sectionNumber">
                <span>{{ruleForm.publicityStartTime}}</span> 至 <span>{{ruleForm.publicityEndTime}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="公示正文：" prop="publicityText">
                <div class="editor">
                  <editor ref="ueditor" class="ueditor" :editread="editread" :content="publicityText"></editor>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="附件：" class="filebigbox">
                <!--文件列表展示组件-->
                <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                <!--文件列表展示组件-->
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <!--公告内容-->
        <!--关联项目信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">相关项目信息</span>
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
              label="地块编号">
            </el-table-column>
            <el-table-column
              prop="landAnnoMation.annoNumber"
              align="left"
              label="公告名称"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="transferType"
              align="left"
              label="出让方式"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.landAnnoMation.type === 1">
                  <span class="statusbox">拍卖</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.landAnnoMation.type === 2">
                  <span class="statusbox">挂牌</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.landAnnoMation.type === 3">
                  <span class="statusbox">资格后审</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.landAnnoMation.type === 4">
                  <span class="statusbox">资格预审</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.landAnnoMation.annoType === 2">
                  <span class="statusbox">邀请招标</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="ageLimit"
              align="left"
              label="出让年限"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="salesConfirmation.bidderName"
              align="left"
              label="竞得人"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="salesConfirmation.strikePrice"
              align="left"
              label="成交价(万元)"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="lastUpdateDate"
              align="left"
              label="成交时间"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </div>
        <!--关联项目信息-->
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import {transResAnno} from '@/api'
export default {
  components: {
    editor,
    commonFileDownload
  },
  data () {
    return {
      // 富文本
      editread: true, // 富文本状态
      publicityText: '', // 富文本内容
      ruleForm: {},
      // 关联相关信息
      tableDataSeletion: [],
      // 文件
      fileObjecit: {}
    }
  },
  methods: {
    detail () {
      transResAnno.queryOne(this.$route.params.objectId).then((res) => {
        this.ruleForm = res.data.transResultsAnno
        if (res.data.transResultsAnno.fileInformations) {
          this.fileObjecit = res.data.transResultsAnno.fileInformations
        }
        this.$refs.ueditor.setContent(this.ruleForm.publicityText)
        let url = {
          transResultsId: res.data.transResultsAnno.objectId,
          _t: new Date().getTime()
        }
        // 查询关联的项目信息
        transResAnno.detailRelation(url).then((res) => {
          this.tableDataSeletion = res.data.refTranresSections.map(function (item) {
            return item.landInformation
          })
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
  @import '../../../../../assets/css/mineral/common.css';
</style>
