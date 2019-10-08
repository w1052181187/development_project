<template>
  <div id="tranaddindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>拍卖/挂牌文件</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/auction-listed-file/sub-auc-listed?roledsType=1&showStatus=false' }">提交出让文件</el-breadcrumb-item>
        <el-breadcrumb-item>发布出让文件</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <!--公告内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">出让文件信息</span>
          <el-col :span="24">
            <el-form-item label="出让文件：" class="filebigbox">
              <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注：">
              <span>{{ruleForm.remark}}</span>
            </el-form-item>
          </el-col>
        </div>
        <!--公告内容-->
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
              prop="landInformation.sectionNumber"
              align="left"
              label="地块编号">
            </el-table-column>
            <el-table-column
              prop="landInformation.methodOfSupply"
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
              prop="landInformation.methodOfTransfer"
              align="left"
              label="出让方式"
              width="200"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.landInformation.methodOfTransfer === 1">
                  <span class="statusbox">拍卖</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.landInformation.methodOfTransfer === 2">
                  <span class="statusbox">挂牌</span>
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
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="releaseForm">发布</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import {tdocuments} from '@/api'
export default {
  components: {
    commonFileDownload
  },
  data () {
    return {
      ruleForm: {},
      // 关联相关信息
      tableDataSeletion: [],
      multipleSelection: [], // 关联信息提交时的数组
      // 文件内容
      fileObjecit: {}
    }
  },
  methods: {
    // 查询关联的项目信息
    querySectionList () {
      let url = {
        annoId: this.$route.query.annoId,
        flag: 2,
        transDocId: this.$route.params.objectId,
        type: 2
      }
      // 查询这个文件的所有关联关系
      tdocuments.selectRelation(url).then((res) => {
        this.tableDataSeletion = res.data.refTrandocSectionList
        tdocuments.detail(this.$route.params.objectId).then((res) => {
          this.ruleForm = res.data.transferDocuments
          if (res.data.transferDocuments.fileInformations) {
            this.fileObjecit = res.data.transferDocuments.fileInformations
          }
        })
      })
    },
    // 发布
    releaseForm () {
      this.ruleForm.status = 2
      tdocuments.updateStatus(this.ruleForm).then((res) => {
        // 跳转到出让文件列表页
        this.$router.push({path: '/lt/transferor/auction-listed-file/sub-auc-listed?roledsType=1'})
      })
    },
    detailBtn (scope) {
      this.$router.push({path: `/lt/transferor/project/massif-register/detail/${scope.row.landInformation.objectId}`, query: {roledsType: 1, showStatus: false}})
      // this.$router.push({path: `/lt/transferor/announcement/sub-announ/detail/${scope.row.landInformation.objectId}`, query: {roledsType: 1, showStatus: false, methodOfTransfer: scope.row.type}})
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/lt/transferor/auction-listed-file/sub-auc-listed?roledsType=1'})
    }
  },
  created () {
    this.querySectionList()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
