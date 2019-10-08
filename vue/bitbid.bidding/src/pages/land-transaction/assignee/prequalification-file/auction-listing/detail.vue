<template>
  <div id="tranaddindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false'}">首页</el-breadcrumb-item>
        <el-breadcrumb-item>资格预审文件</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/prequalification-file/auction-listing/index?roledsType=2&showStatus=false'}">下载资格预审文件</el-breadcrumb-item>
        <el-breadcrumb-item>下载</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm" label-width="160px" class="demo-ruleForm">
        <!--关联项目信息-->
        <div class="proinfobox">
          <span class="title">关联项目信息</span>
          <el-table
            :data="fileData"
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
              label="操作" width="140" align="center">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  详情
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!--关联项目信息-->
        <!--资格预审文件信息-->
        <div class="proinfobox">
          <span class="title">资格预审文件信息</span>
          <el-row>
            <el-col :span="24" class="filebigbox">
              <el-form-item label="资格预审文件：">
                <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <!--资格预审文件信息-->
        <!--澄清/修改记录-->
        <div class="proinfobox" v-if="Number(this.$route.query.isClarify) === 1">
          <span class="title" style="border-left: 3px solid #2d9631">澄清/修改记录</span>
          <el-table
            ref="multipleTable"
            :data="tableDataSeletion"
            style="width: 100%"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="createDate"
              align="left"
              label="澄清/修改时间">
            </el-table-column>
            <el-table-column
              prop="content"
              align="left"
              label="澄清/修改内容"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="isChange"
              align="left"
              label="是否更改招标文件"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="fileInformations.fileName"
              align="left"
              label="澄清修改文件名称"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              label="操作" width="140" align="center">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="downBtn(scope)">
                  下载
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!--澄清/修改记录-->
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import {downloadFileMining} from '@/assets/js/common'
import { landPreDoc, landProject } from '@/api'

export default {
  components: {
    commonFileDownload,
    editor
  },
  data () {
    return {
      ruleForm: {
        bidEvaluationMethod: 1,
        bidOpeningWay: 1,
        bidEvaluationMethods: 1,
        isFileRadio: 1
      },
      content: '',
      // 关联相关信息
      tableDataSeletion: [],
      fileData: [],
      fileObjecit: {}
    }
  },
  methods: {
    // detailBtn (scope) {},
    // downBtn (scope) {
    //   downloadFileMining(scope.row.fileName, scope.row.relativePath)
    // }
    detailBtn (scope) {},
    downBtn (scope) {
      downloadFileMining(scope.row.fileName, scope.row.relativePath)
    },
    // 查看详情
    detailByAssignee () {
      // 根据项目id查询出项目信息
      // 根据项目id查询出出让文件信息
      // 根据项目id查询出澄清文件信息
      landProject.detail(this.$route.query.transDocSectionid).then((res) => {
        this.fileData.push(res.data.landInformation)
      })
      landPreDoc.detailByAssignee(this.$route.query.transDocSectionid).then((res) => {
        if (res.data.landPreDoc_new.fileInformations) {
          this.fileObjecit = res.data.landPreDoc_new.fileInformations
        }
      })
    },
    createDateTable () {
      landPreDoc.detailByAssignee(this.$route.query.transDocSectionid).then((res) => {
        console.log(res)
        if (res.data.clarifyFiles_new.length !== 0) {
          this.tableDataSeletion = res.data.clarifyFiles
        }
      })
    }
  },
  created () {
    this.detailByAssignee()
    this.createDateTable()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
