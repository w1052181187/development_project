<template>
  <div id="downloadclarifybox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让文件</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/assignee/transfer-documents/download-clarify-file?roledsType=2' }">下载澄清/修改文件</el-breadcrumb-item>
        <el-breadcrumb-item>下载</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form label-width="100px" class="demo-ruleForm">
        <!--关联项目信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #fe8900">关联项目信息</span>
          <el-table
            :data="approvaltableData"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="sectionNumber"
              label="项目编号"
              align="left">
            </el-table-column>
            <el-table-column
              prop="sectionName"
              label="项目名称"
              align="left">
            </el-table-column>
            <el-table-column
              label="操作"
              align="center"
              width="120">
              <template slot-scope="scope">
                <span class="jfbdbox">
                  <el-button type="text" size="small" @click="prodetails (scope)">
                  详情
                </el-button>
                </span>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!--关联项目信息-->
        <!--出让文件信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #0206fd">出让文件信息</span>
          <el-row>
            <el-col :span="24">
              <el-form-item label="出让文件" prop="minerals" class="filebigbox">
                <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <!--出让文件信息-->
        <!--澄清/修改记录-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">澄清/修改记录</span>
          <el-table
            :data="updatetableData"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="createDate"
              label="澄清/修改时间"
              align="left">
            </el-table-column>
            <el-table-column
              prop="content"
              label="澄清/修改内容"
              align="left">
            </el-table-column>
            <el-table-column
              prop="editFile"
              label="澄清/修改文件"
              align="left">
            </el-table-column>
            <el-table-column
              prop="itemFilePath"
              label="操作"
              align="center"
              width="120">
              <template slot-scope="scope">
                <span class="jfbdbox">
                  <el-button
                    type="text"
                    @click="handleEdit(scope.row.itemFilePath.name,scope.row.itemFilePath.path)"
                    size="small"
                    class="chakna_yichu">
                  下载
                </el-button>
                </span>
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
import {tdocuments, prProject} from '@/api'
import {downloadFile} from '@/assets/js/common'
import commonFileDownload from '@/components/public-resource/mineral-rights/common/file-download.vue'
export default {
  components: {
    commonFileDownload
  },
  data () {
    return {
      // 关联项目信息
      approvaltableData: [],
      // 出让文件
      filyletableData: [],
      // 文件
      fileObjecit: {},
      // 澄清/修改记录
      updatetableData: []
    }
  },
  methods: {
    // 查看详情
    detailByAssignee () {
      // 根据项目id查询出项目信息
      // 根据项目id查询出出让文件信息
      // 根据项目id查询出澄清文件信息
      prProject.queryOne(this.$route.query.clarifyFileSectionid).then((res) => {
        this.approvaltableData.push(res.data.sectionOneInfo)
      })
      tdocuments.detailByAssignee(this.$route.query.clarifyFileSectionid).then((res) => {
        this.filyletableData.push(res.data.transferDocuments.fileInformations)
        if (res.data.clarifyFiles !== null) {
          let newArr = res.data.clarifyFiles
          this.updatetableData = newArr.map((item, index) => {
            item.editFile = item.fileInformations.fileName
            item.itemFilePath = {path: item.fileInformations.relativePath, name: item.fileInformations.fileName}
            return item
          })
        }
        if (res.data.transferDocuments.fileInformations) {
          this.fileObjecit = res.data.transferDocuments.fileInformations
        }
      })
    },
    prodetails (scope) {
      this.$router.push({path: `/mr/assignee/look-transfer-project/detail/${scope.row.objectId}`, query: {type: scope.row.transferType, roledsType: 2}})
    },
    handleEdit (name, path) {
      downloadFile(name, path)
    }
  },
  created () {
    this.detailByAssignee()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
  #downloadclarifybox .jfbdbox span{
    color: rgb(0, 170, 255)!important;
  }
</style>
