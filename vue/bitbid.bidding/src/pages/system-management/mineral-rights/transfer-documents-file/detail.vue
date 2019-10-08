<template>
  <div id="trandeaindexbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>矿权交易备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{path: '/system-management/mineral-rights/transfer-documents-file'}">出让文件备案</el-breadcrumb-item>
        <el-breadcrumb-item>出让文件查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <router-link tag='span' to="/system-management/mineral-rights/transfer-documents-file">返回</router-link>
      </div>
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <!--公告内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">出让文件信息</span>
          <el-col :span="24">
            <el-form-item label="出让文件：" prop="name" class="filetitle filebigbox">
              <!--文件列表展示组件-->
              <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
              <!--文件列表展示组件-->
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注：" prop="name" class="filetitle">
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
              label="项目编号"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="sectionName"
              align="left"
              label="项目名称"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              align="center"
              label="操作"
              width="200">
              <template slot-scope="scope">
                <span class="jfbdbox">
                  <el-button type="text" size="small" @click="prodetails(scope)">
                  详情
                </el-button>
                </span>
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
import { stdocuments } from '@/api'
import commonFileDownload from '@/components/system-management/file-download.vue'
export default {
  components: {
    commonFileDownload
  },
  data () {
    return {
      ruleForm: {},
      // 文件名字
      fileObjecit: {},
      // 关联相关信息
      tableDataSeletion: [],
      multipleSelection: [], // 关联信息提交时的数组
      total: 0, // 总条数
      pageNo: 0,
      pageSize: 10, // 每页展示条数
      transDocId: ''
    }
  },
  methods: {
    // 关联项目信息列表
    detail () {
      stdocuments.detail(this.$route.params.objectId).then((res) => {
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
        stdocuments.detailRelation(url).then((res) => {
          this.tableDataSeletion = res.data.refTrandocSectionList.map(function (item) {
            return item.section
          })
        })
      })
    },
    //  查看详情
    prodetails (scope) {
      if (scope.row.fileInformations === null) {
        this.$router.push({path: `/system-management/mineral-rights/project-file/detail/${scope.row.objectId}`, query: {type: scope.row.transferType}})
      } else {
        this.$router.push({path: `/system-management/mineral-rights/project-file/detail/${scope.row.objectId}`, query: {type: scope.row.transferType, code: scope.row.code, fileName: scope.row.fileInformations.fileName, fileObjectId: scope.row.fileInformations.objectId}})
      }
    }
  },
  computed: {},
  created () {
    this.detail()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
  #trandeaindexbox  .jfbdbox span{
    color: rgb(0, 170, 255)!important;
  }
</style>
