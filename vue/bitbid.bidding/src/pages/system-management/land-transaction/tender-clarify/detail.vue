<template>
  <div id="land_ann_lookdetailbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>土地交易备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/land-transaction/tender-clarify/index'}">招标澄清/修改文件备案</el-breadcrumb-item>
        <el-breadcrumb-item>招标澄清/修改文件查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--澄清/修改文件信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">澄清/修改文件信息</span>
          <div class="formmain">
            <el-col :span="24">
              <el-form-item label="是否变更招标文件：">
                <span v-if="sectionForm.isChange === 0">否</span>
                <span v-if="sectionForm.isChange === 1">是</span>
              </el-form-item>
            </el-col>
            <el-col :span="24" v-if="sectionForm.isChange === 1" class="filebigbox">
              <el-form-item label="招标文件：">
                <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
              </el-form-item>
            </el-col>
            <el-row>
              <el-col :span="24" class="fujianbox">
                <el-form-item label="招标文件：">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24" class="filebigbox">
                <el-form-item label="澄清/修改文件：">
                  <commonFileDownload :fileObjecit="fileClarilfyObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="澄清/修改内容：">
                  <div class="editor">
                    <editor ref="ueditor" class="ueditor" :editread="true" :content="content"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="备注：">
                  <span>{{sectionForm.remark}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--澄清/修改文件信息-->
        <!--文件信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid rgb(45, 150, 49)">文件信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="是否修改文件信息：">
                  <span v-if="sectionForm.isFile === 0">否</span>
                  <span v-if="sectionForm.isFile === 1">是</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="答疑澄清时间：">
                  <span>{{sectionForm.times}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="投标有效期：">
                  <span>{{sectionForm.day}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="评标办法：">
                  <span v-if="sectionForm.bidEvaluationMethod === 1">综合评估法</span>
                  <span v-if="sectionForm.bidEvaluationMethod === 2">经评审最高价中标法</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="开标时间：">
                  <span>{{sectionForm.times}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="开标地点：">
                  <span>{{sectionForm.day}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="开标方式：">
                  <span v-if="sectionForm.reviewTheWay === 1">在线开标</span>
                  <span v-if="sectionForm.reviewTheWay === 2">线下开标</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="评标方式：">
                  <span v-if="sectionForm.reviewTheWay === 1">在线评标</span>
                  <span v-if="sectionForm.reviewTheWay === 2">线下评标</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--文件信息-->
        <!--关联项目信息-->
        <div class="proinfobox">
          <span class="title">关联项目信息</span>
          <el-table
            :data="tableDataSeletion"
            style="width: 100%"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="sectionNumber"
              align="left"
              label="地块编号"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="methodOfSupply"
              label="供应方式"
              align="left">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.methodOfSupply === 1">
                  <span class="statusbox">招拍挂出让</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.methodOfSupply === 2">
                  <span class="statusbox">协议出让</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="methodOfTransfer"
              label="出让方式"
              align="left">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === null">
                  <span class="statusbox">----</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 0">
                  <span class="statusbox">拍卖</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 1">
                  <span class="statusbox">挂牌</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 2">
                  <span class="statusbox">资格预审</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 3">
                  <span class="statusbox">资格后审</span>
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
        <!--审批记录-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #b94aff">审批记录</span>
          <el-table
            :data="recordApprovalTable"
            border>
            <el-table-column
              prop="approvrResult"
              label="审批结果"
              align="left">
              <template slot-scope="scope">
                <div v-if="Number(scope.row.approvrResult) === 3">
                  <span class="statusbox">审批通过</span>
                </div>
                <div v-if="Number(scope.row.approvrResult) === 4">
                  <span class="statusbox">审批不通过</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="approverName"
              label="操作人"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="approvrDate"
              label="操作时间"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="approvrOpinion"
              label="审批意见"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </div>
        <!--审批记录-->
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import {clarifyFile} from '@/api'
export default {
  components: {
    commonFileDownload,
    editor
  },
  data () {
    return {
      sectionForm: {},
      content: '', // 公告内容
      // 文件内容
      fileObjecit: {
        fileName: '测试数据'
      },
      fileClarilfyObjecit: {
        fileName: '测试数据'
      },
      tableDataSeletion: [],
      // 审批记录
      recordApprovalTable: []
    }
  },
  methods: {
    // 查看详情
    detail () {
      clarifyFile.detail(this.$route.params.objectId).then((res) => {
        console.log(res)
        if (res.data.clarifyFile.fileInformations) {
          this.fileClarilfyObjecit = res.data.clarifyFile.fileInformations
        }
        if (res.data.clarifyFile.approveRecords) { // 审批记录
          this.recordApprovalTable = res.data.clarifyFile.approveRecords
        }
        this.sectionForm = res.data.clarifyFile
        this.content = res.data.clarifyFile.content
        this.$refs.ueditor.setContent(this.content) // 设置富文本内容
        let url = {
          clarifyFileId: res.data.clarifyFile.objectId,
          type: 2
        }
        clarifyFile.detailRelation(url).then((res) => {
          this.tableDataSeletion = res.data.refClarifyFileList.map(function (item) {
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
