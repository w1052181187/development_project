<template>
  <div id="tranaddindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>资格预审文件</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/prequalification-file/look-tender-file/index?roledsType=1&showStatus=false'}">查看资格预审文件</el-breadcrumb-item>
        <el-breadcrumb-item>查看资格预审文件</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm" label-width="160px" class="demo-ruleForm">
        <!--出让文件信息-->
        <div class="proinfobox">
          <span class="title">出让文件信息</span>
          <el-col :span="24" class="filebigbox">
            <el-form-item label="资格预审文件：" prop="name" class="filetitle bitianicon">
              <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注：" prop="remark" class="filetitle">
              <span>{{ruleForm.remark}}</span>
            </el-form-item>
          </el-col>
        </div>
        <!--出让文件信息-->
        <!--关联项目信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">关联项目信息</span>
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
              prop="methodOfSupply"
              align="left"
              label="供应方式"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <div class="statusbigbox">
                  <span class="statusbox">招拍挂出让</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="methodOfTransfer"
              align="left"
              label="出让方式"
              width="200"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 3">
                  <span class="statusbox">资格后审</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 4">
                  <span class="statusbox">资格预审</span>
                </div>
              </template>
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
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import { landPreDoc } from '@/api'
export default {
  components: {
    commonFileDownload
  },
  data () {
    return {
      ruleForm: {
        bidEvaluationMethod: 1,
        bidOpeningWay: 1,
        bidEvaluationMethods: 1
      },
      // 文件内容
      fileObjecit: {
        fileName: '测试数据'
      },
      // 关联相关信息
      tableDataSeletion: [],
      answerDataRange: [], // 答疑澄清时间
      // 提交保存状态
      subStatus: false,
      oldFileName: ''
    }
  },
  methods: {
    detail () {
      landPreDoc.detail(this.$route.query.objectId).then((res) => {
        if (res.data.landPreDoc.fileInformations) {
          this.fileObjecit = res.data.landPreDoc.fileInformations
        }
        this.ruleForm = res.data.landPreDoc
        let url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          transDocId: res.data.landPreDoc.objectId,
          type: 2
        }
        landPreDoc.detailRelation(url).then((res) => {
          this.tableDataSeletion = res.data.refLandPreSectionList.map(function (item) {
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
  @import '../../../../../assets/css/land-register/common.css';
</style>
