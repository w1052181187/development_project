<template>
  <div id="projectdetailbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/procurement-invite-file' }">采购邀请备案</el-breadcrumb-item>
        <el-breadcrumb-item v-if="type === 'detail'" :to="{ path: '/system-management/government-procurement/procurement-invite-file/check-confirm' + handleCheckList(this.$route.query.gongGaoId)}">查看确认情况</el-breadcrumb-item>
        <el-breadcrumb-item>{{type === "check" ? '审批' : '采购邀请书'}}</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig" v-if="type === 'detail'">
        <template>
          <el-button @click="goback()">返回</el-button>
        </template>
      </div>
    </div>
    <div class="contentbigbox">
      <el-form class="gp_approve">
        <table class="detailtable">
          <tr>
            <td>采购项目名称：</td>
            <td  colspan="3">{{proForm.faBaoMingCheng}}</td>
            <td>邀请书名称：</td>
            <td  colspan="3">{{proForm.gongGaoMingCheng}}</td>
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
                  prop="yaoQingJieZhiDate"
                  label="供应商邀请确认截止时间">
                </el-table-column>
              </el-table>
            </td>
          </tr>
          <tr>
            <td>采购方式：</td>
            <td colspan="7">
              邀请招标
            </td>
          </tr>
          <tr>
            <td>邀请书内容：</td>
            <td colspan="7">
              <editor ref="ueditor" class="ueditor" :editread="editread" :content="proForm.content"></editor>
            </td>
          </tr>
          <tr>
            <td>附件：</td>
            <td colspan="7">
              <file-down :fileName="proForm.fuJianFile" :filePath = "proForm.fuJianFileName" ></file-down>
            </td>
          </tr>
          <!--<template v-if="type === 'detail' && proForm.fabuZhuangTai && proForm.fabuZhuangTai !== 1">-->
            <!--<tr>-->
              <!--<td>审批结果：</td>-->
              <!--<td colspan="7">-->
                <!--<span v-if="proForm.fabuZhuangTai === 1">-->
                  <!--已发布-->
                <!--</span>-->
                <!--<span v-if="proForm.fabuZhuangTai === 3">-->
                  <!--备案审批中-->
                <!--</span>-->
                <!--<span v-if="proForm.fabuZhuangTai === 4">-->
                  <!--审批通过-->
                <!--</span>-->
                <!--<span v-if="proForm.fabuZhuangTai === 5">-->
                  <!--审批不通过-->
                <!--</span>-->
              <!--</td>-->
            <!--</tr>-->
            <!--<tr>-->
              <!--<td>审批意见：</td>-->
              <!--<td colspan="7">{{proForm.shenHeYiJian}}</td>-->
            <!--</tr>-->
            <!--<tr>-->
              <!--<td>审批时间：</td>-->
              <!--<td colspan="7">{{proForm.modifyTime}}</td>-->
            <!--</tr>-->
          <!--</template>-->
          <!--<tr v-if="type === 'check'">-->
            <!--<td>审批：</td>-->
            <!--<td colspan="7">-->
              <!--&lt;!&ndash;审批组件&ndash;&gt;-->
              <!--<noticeApprove @approveForm="approveForm" ref="approveForm" @valid="handleIsValid"></noticeApprove>-->
              <!--&lt;!&ndash;审批组件&ndash;&gt;-->
            <!--</td>-->
          <!--</tr>-->
        </table>
        <!--提交审核-->
        <!--<el-form-item class="submit-radio" v-if="type === 'check'">-->
          <!--<el-button type="primary" class="submit" @click="submitForm()">提交</el-button>-->
          <!--<el-button class="cancel" @click="$router.go(-1)">取消</el-button>-->
        <!--</el-form-item>-->
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/system-management/ueditor/ueditor.vue'
import fileDown from '../../../../components/system-management/FileDownload'
import { pushData } from '@/api'
import { checkSuccessed } from '../../../../utils/commonUtils'
import noticeApprove from '@/components/system-management/notice-approve.vue'
export default {
  components: {
    editor,
    fileDown,
    noticeApprove
  },
  data () {
    return {
      // 富文本
      editread: true,
      content: '',
      childForm: {},
      currentPage: 1,
      isValid: false,
      // 详情
      proForm: {},
      tableData: [],
      grouptableData: [],
      contacttabledata: [],
      type: this.$route.params.type
    }
  },
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    detail () {
      pushData.annoDetail(this.$route.params.objectId).then((res) => {
        this.proForm = JSON.parse(res.data.annoDetail)
        this.grouptableData = this.proForm.zbGongGaoBiaoDuanList
        this.contacttabledata = this.proForm.contacts
        return this.proForm.content
      }).then((content) => {
        this.$refs.ueditor.setContent(content)
      })
    },
    approveForm (formV) {
      this.childForm = formV
    },
    handleIsValid (valid) {
      this.isValid = valid
    },
    /** 提交 */
    submitForm () {
      let obj = {
        type: 2, // 采购邀请备案
        approveId: this.proForm.id,
        approveResult: this.childForm.approvrResult,
        approveOpinion: this.childForm.approvrOpinion || ''
      }
      this.$refs.approveForm.handleValidate()
      if (this.isValid) {
        pushData.check(obj).then((res) => {
          checkSuccessed(this, res.data.resCode)
        })
      }
    },
    goback () {
      this.$router.go(-1)
    },
    handleCheckList (id) {
      return '/' + id
    }
  },
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
  #projectdetailbox{
    .el-icon-arrow-up:before {
      content: "\E605";
      display: none;
    }
    .el-icon-arrow-down:before {
      content: "\E603";
      display: none;
    }
    .el-select .el-input.is-disabled .el-input__inner:hover {
      border-color: transparent;
    }
    .el-input__inner {
      color: #353535!important;
      font-size: 12px!important;
      vertical-align: middle;
    }
  }
</style>
