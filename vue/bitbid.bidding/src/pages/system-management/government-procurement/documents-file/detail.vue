<template>
  <div id="projectdetailbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/documents-file' }">文件公示备案</el-breadcrumb-item>
        <el-breadcrumb-item>查看公示</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <router-link tag='span' to="/system-management/government-procurement/documents-file">返回</router-link>
      </div>
    </div>
    <div class="contentbigbox">
      <table class="detailtable">
        <tr>
          <td>采购项目名称：</td>
          <td  colspan="3">{{proForm.faBaoMingCheng}}</td>
          <td>采购项目编号：</td>
          <td  colspan="3">{{proForm.faBaoBianHao}}</td>
        </tr>
        <tr>
          <td>公示名称：</td>
          <td colspan="3">{{proForm.publicityName}}</td>
          <td>采购方式：</td>
          <td colspan="3">
            {{getMethod(proForm.purchaseMethod)}}
          </td>
        </tr>
        <tr>
          <td>包组信息：</td>
          <td colspan="7">
            <el-table
              :data="grouptableData"
              border
              style="width: 100%"
              header-cell-class-name="tabletitles">
              <el-table-column
                prop="biaoDuanBianHao"
                label="包组编号">
              </el-table-column>
              <el-table-column
                prop="biaoDuanMingCheng"
                label="包组名称">
              </el-table-column>
              <el-table-column
                prop="zhaoBiaoFangShi"
                label="采购方式">
                <template slot-scope="scope">
                  {{getMethod(scope.row.zhaoBiaoFangShi)}}
                </template>
              </el-table-column>
            </el-table>
          </td>
        </tr>
        <tr>
          <td>公示内容：</td>
          <td colspan="7">
            <editor ref="ueditor" class="ueditor" :editread="editread" :content="proForm.publicityContent"></editor>
          </td>
        </tr>
        <tr>
          <td>附件：</td>
          <td colspan="7">
            <file-down :fileName="proForm.fileNameFuJianLocal" :filePath = "proForm.fileNameFuJianServer" ></file-down>
          </td>
        </tr>
        <tr>
          <td>公示发布责任人：</td>
          <td colspan="7">
            <el-table
              :data="contacttabledata"
              border
              style="width: 100%"
              header-cell-class-name="tabletitles">
              <el-table-column
                prop="name"
                label="姓名">
              </el-table-column>
              <el-table-column
                prop="job"
                label="职务">
              </el-table-column>
              <el-table-column
                prop="mobilePhone"
                label="联系电话">
              </el-table-column>
            </el-table>
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>
<script>
import editor from '@/components/system-management/ueditor/ueditor.vue'
import fileDown from '../../../../components/system-management/FileDownload'
import { pushData } from '@/api'
import { handleMethod } from '../../../../utils/commonUtils'
export default {
  components: {
    editor,
    fileDown
  },
  data () {
    return {
      // 富文本
      editread: true,
      content: '',
      // 详情
      proForm: {},
      tableData: [],
      grouptableData: [],
      contacttabledata: []
    }
  },
  methods: {
    detail () {
      pushData.filePublicityDetail(this.$route.params.objectId).then((res) => {
        this.proForm = JSON.parse(res.data.zbFilePubDetail)
        this.grouptableData = this.proForm.faBaoBiaoDuanList
        this.contacttabledata = this.proForm.publicityContacts
        return this.proForm.publicityContent
      }).then((content) => {
        this.$refs.ueditor.setContent(content)
      })
    },
    getMethod (type) {
      return handleMethod(type)
    }
  },
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
</style>
