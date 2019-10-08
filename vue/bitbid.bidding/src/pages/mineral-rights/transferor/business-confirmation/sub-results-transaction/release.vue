<template>
  <div id="subresreleaseindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>成交确认</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/transferor/business-confirmation/sub-results-transaction?roledsType=1' }">提交成交结果公示</el-breadcrumb-item>
        <el-breadcrumb-item>发布成交结果公示</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <!--公告内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">公示信息</span>
          <el-row>
            <el-col :span="12">
              <el-form-item label="公示名称：" prop="publicityName">
                <span>{{ruleForm.publicityName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="公示时间：" prop="sectionNumber">
                <span>{{ruleForm.publicityStartTime}}</span> 至 <span>{{ruleForm.publicityEndTime}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="公示正文：" prop="publicityText">
                <div class="editor">
                  <editor ref="ueditor" class="ueditor" :editread="editread" :content="publicityText"></editor>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="附件：" prop="" class="filebigbox">
                <!--文件列表展示组件-->
                <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                <!--文件列表展示组件-->
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <!--公告内容-->
        <!--关联项目信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">相关项目信息</span>
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
              prop="transferType"
              align="left"
              label="出让类型"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.transferType === 1">
                  <span class="statusbox">探矿权</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.transferType === 2">
                  <span class="statusbox">采矿权</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="ageLimit"
              align="left"
              label="出让年限"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="salesConfirmation.bidderName"
              align="left"
              label="竞得人"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="salesConfirmation.strikePrice"
              align="left"
              label="成交价(万元)"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="salesConfirmation.dealTime"
              align="left"
              label="成交时间"
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
import editor from '@/components/public-resource/mineral-rights/common/ueditor/ueditor.vue'
import commonFileDownload from '@/components/public-resource/mineral-rights/common/file-download.vue'
import {transResAnno, contract} from '@/api'
export default {
  components: {
    editor,
    commonFileDownload
  },
  data () {
    return {
      // 富文本
      editread: true, // 富文本状态
      publicityText: '', // 富文本内容
      ruleForm: {
        objectId: this.$route.query.objectId
      },
      fileList: [], // 文件列表
      EnclosuretableData: [], // 表格
      appendix: false, // 弹框定义
      Enclosure: {}, // 文件上传绑定
      loading: false, // 文件上传过程中
      dataRange: [], // 时间
      // 关联相关信息
      tableDataSeletion: [],
      multipleSelection: [], // 关联信息提交时的数组
      fileObjecit: {}
    }
  },
  methods: {
    // 发布
    submitForm () {
      this.ruleForm.status = 4
      transResAnno.updateStatus(this.ruleForm).then((res) => {
        // 发布成功之后 添加合同
        let contractForms = []
        this.tableDataSeletion.forEach(function (item) {
          let contractForm = {}
          contractForm.sectionId = item.objectId
          contractForm.status = 0
          contractForms.push(contractForm)
        })
        contract.batchSave(contractForms).then((res) => {
          // 跳转到出让文件列表页
          this.$router.push({path: '/mr/transferor/business-confirmation/sub-results-transaction?roledsType=1'})
        })
      })
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/mr/transferor/business-confirmation/sub-results-transaction?roledsType=1'})
    },
    detail () {
      transResAnno.queryOne(this.$route.query.objectId).then((res) => {
        if (res.data.transResultsAnno.fileInformations) {
          this.fileObjecit = res.data.transResultsAnno.fileInformations
        }
        this.ruleForm = res.data.transResultsAnno
        this.$refs.ueditor.setContent(this.ruleForm.publicityText)
        let url = {
          transResultsId: res.data.transResultsAnno.objectId,
          _t: new Date().getTime()
        }
        // 查询关联的项目信息
        transResAnno.detailRelation(url).then((res) => {
          this.tableDataSeletion = res.data.refTranresSections.map(function (item) {
            return item.section
          })
        })
      })
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
</style>
