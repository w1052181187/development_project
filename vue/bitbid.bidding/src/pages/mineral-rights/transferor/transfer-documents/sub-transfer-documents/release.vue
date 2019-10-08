<template>
  <div id="tranresindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让文件</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/transferor/transfer-documents/sub-transfer-documents?roledsType=1' }">提交出让文件</el-breadcrumb-item>
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
            <el-form-item label="出让文件：" prop="name" class="filetitle">
              <!--文件列表展示组件-->
              <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
              <!--文件列表展示组件-->
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注：" prop="remark" class="filetitle">
              <span class="contents">{{ruleForm.remark}}</span>
            </el-form-item>
          </el-col>
        </div>
        <!--公告内容-->
        <!--关联项目信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">关联项目信息</span>
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
              label="项目编号">
            </el-table-column>
            <el-table-column
              prop="sectionName"
              align="left"
              label="项目名称"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </div>
        <!--关联项目信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('ruleForm')">发布</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import { tdocuments } from '@/api'
import commonFileDownload from '@/components/public-resource/mineral-rights/common/file-download.vue'
export default {
  components: {
    commonFileDownload
  },
  data () {
    return {
      ruleForm: {
        objectId: this.$route.query.objectId
      },
      // 关联相关信息
      tableDataSeletion: [],
      multipleSelection: [], // 关联信息提交时的数组
      total: 0, // 总条数
      pageNo: 0,
      pageSize: 10, // 每页展示条数
      fileObjecit: {}
    }
  },
  methods: {
    // 发布
    submitForm () {
      this.ruleForm.status = 2
      tdocuments.updateStatus(this.ruleForm).then((res) => {
        // 跳转到出让文件列表页
        this.$router.push({path: '/mr/transferor/transfer-documents/sub-transfer-documents?roledsType=1'})
      })
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/mr/transferor/transfer-documents/sub-transfer-documents?roledsType=1'})
    },
    detail () {
      tdocuments.detail(this.$route.query.objectId).then((res) => {
        if (res.data.transferDocuments.fileInformations) {
          this.fileObjecit = res.data.transferDocuments.fileInformations
        }
        this.ruleForm = res.data.transferDocuments
        let url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          transDocId: res.data.transferDocuments.objectId,
          _t: new Date().getTime()
        }
        tdocuments.detailRelation(url).then((res) => {
          this.tableDataSeletion = res.data.refTrandocSectionList.map(function (item) {
            return item.section
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
  #tranresindexbox{
    .el-form-item__content{
      margin-left: 0!important;
    }
    .el-form-item__content {
      width: 100%;
    }
  }
</style>
