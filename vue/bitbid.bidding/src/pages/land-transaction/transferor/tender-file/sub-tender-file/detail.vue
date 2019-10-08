<template>
  <div id="tranaddindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招标、投标文件</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/tender-file/sub-tender-file/index?roledsType=1&showStatus=false' }">提交招标文件</el-breadcrumb-item>
        <el-breadcrumb-item>查看招标文件</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm" label-width="160px" class="demo-ruleForm">
        <!--出让文件信息-->
        <div class="proinfobox">
          <span class="title">出让文件信息</span>
          <el-col :span="24" class="filebigbox">
            <el-form-item label="招标文件：" prop="name" class="filetitle bitianicon">
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
        <!--文件信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">文件信息</span>
          <el-row>
            <el-col :span="12">
              <el-form-item label="答疑澄清时间：" class="bitianicon timesbox">
                <span>{{ruleForm.answerStartTime}}</span> 至 <span>{{ruleForm.answerEndTime}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="投标有效期（天）：" prop="validityOfBid">
                <span>{{ruleForm.validityOfBid}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="评标办法：" prop="bidEvaluationMethod">
                <span v-if="ruleForm.bidEvaluationMethod === 1">综合评估法</span>
                <span v-if="ruleForm.bidEvaluationMethod === 2">经评审最高价中标法</span>
              </el-form-item>
            </el-col>
            <el-col :span="8" class="hourbox">
              <el-form-item label="开标时间：" prop="bidOpeningTime">
                <span>{{ruleForm.bidOpeningTime}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="开标地点：" prop="placeOfBidOpening">
                <span>{{ruleForm.placeOfBidOpening}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="开标方式：" prop="bidOpeningWay">
                <span v-if="ruleForm.bidOpeningWay === 1">在线开标</span>
                <span v-if="ruleForm.bidOpeningWay === 2">线下开标</span>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="评标方式：" prop="bidEvaluationMethods">
                <span v-if="ruleForm.bidEvaluationMethods === 1">在线评标</span>
                <span v-if="ruleForm.bidEvaluationMethods === 2">线下评标</span>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <!--文件信息-->
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
import { tdocuments } from '@/api'
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
        fileName: ''
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
      tdocuments.detail(this.$route.params.objectId).then((res) => {
        console.log(res)
        if (res.data.transferDocuments.fileInformations) {
          this.fileObjecit = res.data.transferDocuments.fileInformations
        }
        this.ruleForm = res.data.transferDocuments
        let url = {
          transDocId: res.data.transferDocuments.objectId,
          _t: new Date().getTime(),
          type: 2
        }
        tdocuments.detailRelation(url).then((res) => {
          this.tableDataSeletion = res.data.refTrandocSectionList.map(function (item) {
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
