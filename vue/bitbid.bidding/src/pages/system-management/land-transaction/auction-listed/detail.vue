<template>
  <div id="addbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>土地交易备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/land-transaction/auction-listed/index' }">拍卖/挂牌出让文件备案</el-breadcrumb-item>
        <el-breadcrumb-item>出让文件查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <router-link tag='span' to="/system-management/land-transaction/auction-listed/index">返回</router-link>
      </div>
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--关联地块信息-->
        <div class="proinfobox">
          <span class="title">关联地块信息</span>
          <el-table
            :data="plotTableData"
            border>
            <el-table-column
              prop="sectionNumber"
              label="地块编号"
              align="left">
            </el-table-column>
            <el-table-column
              prop="landAnnoMation.annoNumber"
              label="公告名称"
              align="left">
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作" width="140" align="center">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  详情
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!--关联地块信息-->
        <!--出让文件信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">出让文件信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="24">
                <el-form-item label="出让文件：" class="filetypebox">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--出让文件信息-->
        <!--澄清/修改记录-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #b94aff">澄清/修改记录</span>
          <el-table
            :data="modifyingRecordTable"
            border>
            <el-table-column
              prop="modifyingTime"
              label="澄清/修改时间"
              align="left">
            </el-table-column>
            <el-table-column
              prop="modifyingContent"
              label="澄清/修改内容"
              align="left">
            </el-table-column>
            <el-table-column
              prop="fileName"
              label="文件名称"
              align="left">
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作" width="140" align="center">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="detailBtn(scope)">
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
import * as citydata from '@/assets/js/citydata'
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import { tdocuments } from '@/api'
export default {
  components: {
    commonFileDownload
  },
  data () {
    return {
      sectionForm: {},
      // 三级联动
      addressOptions: citydata.CityInfo,
      selectedcityOptions: [1, 1, 1],
      // 文件内容
      fileObjecit: {
        fileName: ''
      },
      // 关联地块信息
      plotTableData: [],
      // 澄清/修改记录
      modifyingRecordTable: []
    }
  },
  methods: {
    detail () {
      tdocuments.detail(this.$route.params.objectId).then((res) => {
        if (res.data.transferDocuments.fileInformations) {
          this.fileObjecit = res.data.transferDocuments.fileInformations
        }
        this.sectionForm = res.data.transferDocuments
        if (res.data.transferDocuments.approveRecords) { // 审批记录
          this.recordApprovalTable = res.data.transferDocuments.approveRecords
        }
        let url = {
          transDocId: res.data.transferDocuments.objectId,
          type: 2
        }
        tdocuments.detailRelation(url).then((res) => {
          this.plotTableData = res.data.refTrandocSectionList.map(function (item) {
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
</style>
