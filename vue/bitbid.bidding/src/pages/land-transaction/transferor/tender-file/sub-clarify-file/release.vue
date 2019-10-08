<template>
  <div id="tranaddindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招标、投标文件</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/tender-file/sub-clarify-file/index?roledsType=1&showStatus=false' }">提交澄清/修改文件</el-breadcrumb-item>
        <el-breadcrumb-item>发布澄清/修改文件</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm" label-width="160px" class="demo-ruleForm">
        <!--出让文件信息-->
        <div class="proinfobox">
          <span class="title">出让文件信息</span>
          <el-col :span="24">
            <el-form-item label="是否变更招标文件：">
              <span v-if="ruleForm.isChange === 0">否</span>
              <span v-if="ruleForm.isChange === 1">是</span>
            </el-form-item>
          </el-col>
          <el-col :span="24" v-if="ruleForm.isChange === 1" class="filebigbox">
            <el-form-item label="招标文件：">
              <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="澄清修改文件：" class="filebigbox">
              <commonFileDownload :fileObjecit="fileClarilfyObjecit"></commonFileDownload>
            </el-form-item>
          </el-col>
          <el-row>
            <el-col :span="24">
              <el-form-item label="公告内容：" class="bitianicon">
                <div class="editor">
                  <editor ref="ueditor" class="ueditor" :editread="true" :content="content"></editor>
                </div>
              </el-form-item>
            </el-col>
          </el-row>
          <el-col :span="24">
            <el-form-item label="备注：" prop="remark">
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
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('ruleForm')" :loading="subStatus">发布</el-button>
          <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
        </el-form-item>
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
      ruleForm: {},
      fileObjecit: {
        fileName: '测试数据'
      },
      content: '',
      fileClarilfyObjecit: {
        fileName: '测试数据'
      },
      // 关联相关信息
      tableDataSeletion: [
        {
          sectionNumber: '地块编号地块编号地块编号',
          methodOfSupply: 1,
          methodOfTransfer: 1
        }
      ],
      // 提交保存状态
      subStatus: false,
      oldFileName: ''
    }
  },
  methods: {
    submitForm (ruleForm) {
      this.ruleForm.status = 2
      clarifyFile.updateStatus(this.ruleForm).then((res) => {
        // 跳转到出让文件列表页
        this.$router.push({path: '/lt/transferor/tender-file/sub-clarify-file/index?roledsType=1'})
      })
    },
    detail () {
      clarifyFile.detail(this.$route.params.objectId).then((res) => {
        if (res.data.clarifyFile.fileInformations) {
          this.fileClarilfyObjecit = res.data.clarifyFile.fileInformations
        }
        this.ruleForm = res.data.clarifyFile
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
  @import '../../../../../assets/css/land-register/common.css';
</style>
