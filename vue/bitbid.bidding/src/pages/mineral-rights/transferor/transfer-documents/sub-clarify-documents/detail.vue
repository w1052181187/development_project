<template>
  <div id="subcleadeataddindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让文件</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/transferor/transfer-documents/sub-clarify-documents?roledsType=1' }">提交澄清/修改文件</el-breadcrumb-item>
        <el-breadcrumb-item>查看澄清/修改文件</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm" ref="ruleForm" label-width="140px" class="demo-ruleForm">
        <!--公告内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">澄清/修改文件信息</span>
          <el-col :span="24">
            <el-form-item label="出让文件：" prop="name" class="filetitle">
              <!--文件列表展示组件-->
              <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
              <!--文件列表展示组件-->
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="澄清/修改内容：" prop="name" class="filetitle">
              <span class="contents">{{ruleForm.content}}</span>
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
              label="项目编号">
            </el-table-column>
            <el-table-column
              prop="sectionName"
              align="left"
              label="项目名称"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              label="操作"
              align="center"
              width="120">
              <template slot-scope="scope">
                <span class="jfbdbox">
                  <el-button
                    type="text"
                    size="small" @click="prodetails(scope)">
                    查看
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
import {clarifyFile} from '@/api'
import commonFileDownload from '@/components/public-resource/mineral-rights/common/file-download.vue'
export default {
  components: {
    commonFileDownload
  },
  data () {
    return {
      ruleForm: {},
      fileObjecit: {},
      // 关联相关信息
      tableDataSeletion: [],
      multipleSelection: [], // 关联信息提交时的数组
      // 当前页
      currentPage: 1,
      total: 0, // 总条数
      pageNo: 0,
      pageSize: 10 // 每页展示条数
    }
  },
  methods: {
    // 查看详情
    detail () {
      clarifyFile.detail(this.$route.params.objectId).then((res) => {
        if (res.data.clarifyFile.fileInformations) {
          this.fileObjecit = res.data.clarifyFile.fileInformations
        }
        this.ruleForm = res.data.clarifyFile
        let url = {
          clarifyFileId: res.data.clarifyFile.objectId
        }
        clarifyFile.detailRelation(url).then((res) => {
          this.tableDataSeletion = res.data.refClarifyFileList.map(function (item) {
            return item.section
          })
        })
      })
    },
    //  查看详情
    prodetails (scope) {
      if (scope.row.fileInformations === null) {
        this.$router.push({path: `/mr/transferor/project/look-project/detail/${scope.row.objectId}`, query: {type: scope.row.transferType, roledsType: 1}})
      } else {
        this.$router.push({path: `/mr/transferor/project/look-project/detail/${scope.row.objectId}`, query: {type: scope.row.transferType, code: scope.row.code, roledsType: 1}})
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
  @import '../../../../../assets/css/mineral/common.css';
  #subcleadeataddindexbox .jfbdbox span{
    color: rgb(0, 170, 255);
  }
  #subcleadeataddindexbox{
    .el-form-item__content{
      margin-left: 0!important;
    }
    .el-form-item__content {
      width: 100%;
    }
  }
</style>
