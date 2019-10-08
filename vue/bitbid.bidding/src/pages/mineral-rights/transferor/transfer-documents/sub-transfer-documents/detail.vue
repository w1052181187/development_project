<template>
  <div id="trandeaindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让文件</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/transferor/transfer-documents/sub-transfer-documents?roledsType=1' }">提交出让文件</el-breadcrumb-item>
        <el-breadcrumb-item>查看出让文件</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
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
                    size="small" @click="prodetails (scope)">
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
import commonFileDownload from '@/components/public-resource/mineral-rights/common/file-download.vue'
import { tdocuments } from '@/api'
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
      total: 0, // 总条数
      pageNo: 0,
      pageSize: 10, // 每页展示条数
      transDocId: '',
      fileObjecit: {}
    }
  },
  methods: {
    // 关联项目信息列表
    detail () {
      tdocuments.detail(this.$route.params.objectId).then((res) => {
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
    },
    //  查看详情
    prodetails (scope) {
      if (scope.row.fileInformations === null) {
        let routeData = this.$router.resolve({path: `/mr/transferor/project/look-project/detail/${scope.row.objectId}`, query: {type: scope.row.transferType, roledsType: 1}})
        window.open(routeData.href, '_blank')
      } else {
        let routeData = this.$router.resolve({path: `/mr/transferor/project/look-project/detail/${scope.row.objectId}`, query: {type: scope.row.transferType, code: scope.row.code, roledsType: 1}})
        window.open(routeData.href, '_blank')
      }
    }
  },
  computed: {},
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
  #trandeaindexbox .jfbdbox span{
    color: rgb(0, 170, 255);
  }
  #trandeaindexbox{
    .el-form-item__content {
      width: 100%;
    }
  }
</style>
