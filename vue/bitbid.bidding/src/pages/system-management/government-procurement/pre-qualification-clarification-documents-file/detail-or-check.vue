<template>
  <div id="prequaclarbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/pre-qualification-clarification-documents-file' }">资格预审澄清/修改文件备案</el-breadcrumb-item>
        <el-breadcrumb-item>{{type === "check" ? '审批' : '详情'}}</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig" v-if="type === 'detail'">
        <router-link tag='span' to="/system-management/government-procurement/pre-qualification-clarification-documents-file">返回</router-link>
      </div>
    </div>
    <div class="contentbigbox">
      <el-form class="gp_approve">
        <table class="detailtable">
          <tr>
            <td>采购项目编号：</td>
            <td  colspan="3">{{proForm.faBaoBianHao}}</td>
            <td>公告名称：</td>
            <td  colspan="3">{{proForm.gongGaoMingCheng}}</td>
          </tr>
          <tr>
            <td>变更澄清文件：</td>
            <td colspan="7">
              <file-down :fileName="proForm.fileName_local" :filePath = "proForm.fileName_server" ></file-down>
            </td>
          </tr>
          <tr>
            <td>包组：</td>
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
              </el-table>
            </td>
          </tr>
          <tr>
            <td>变更内容：</td>
            <td colspan="7">
              <editor ref="ueditor" class="ueditor" :editread="editread" :content="proForm.clarifyContent"></editor>
            </td>
          </tr>
          <tr>
            <td>供应商资格条件：</td>
            <td colspan="7">{{proForm.applicantQualifications}}</td>
          </tr>
          <tr>
            <td>是否更改采购文件设置：</td>
            <td  colspan="3" v-if="isShow && type !== 'check'">是</td>
            <td  colspan="7" v-else-if="isShow">是</td>
            <td  colspan="7" v-else>否</td>
            <template v-if="isShow && type !== 'check'">
              <td>发售截止时间：</td>
              <td colspan="3">{{proForm.faShou_EndTime}}</td>
            </template>
          </tr>
          <tr v-if="isShow && type !== 'check'">
            <td>质疑开始时间：</td>
            <td colspan="3">{{proForm.zhiYi_StartTime}}</td>
            <td>质疑截止时间：</td>
            <td colspan="3">{{proForm.zhiYi_EndTime}}</td>
          </tr>
          <tr v-if="isShow && type !== 'check'">
            <td>提交资格申请文件截止时间：</td>
            <td colspan="3">{{proForm.shenQingWenJian_EndTime}}</td>
            <td>申请有效期：</td>
            <td colspan="3">{{proForm.applicationValidTime}}</td>
          </tr>
          <tr v-if="isShow">
            <td>评审办法：</td>
            <td colspan="7">{{proForm.evaluationMethod}}</td>
          </tr>
          <tr v-if="isShow">
            <td>文件开启方式：</td>
            <td colspan="3">
              <span v-if="proForm.fileOpenMethod === 1">
                线上开启
              </span>
              <span v-if="proForm.fileOpenMethod === 2">
                线下开启
              </span>
            </td>
            <td>评审方式：</td>
            <td colspan="3">
              <span v-if="proForm.evaluationMode === 1">
                线上评审
              </span>
              <span v-if="proForm.evaluationMode === 2">
                线下评审
              </span>
            </td>
          </tr>
          <tr>
            <td>提交人：</td>
            <td colspan="3">{{proForm.shangChuanRen}}</td>
            <td>提交时间：</td>
            <td colspan="3">{{proForm.createTime}}</td>
          </tr>
          <template v-if="type === 'detail' && proForm.fabuZhuangTai && proForm.fabuZhuangTai !== 1">
            <tr>
              <td>审批结果：</td>
              <td colspan="7">
                <span v-if="proForm.fabuZhuangTai === 3">
                  备案审批中
                </span>
                <span v-if="proForm.fabuZhuangTai === 4">
                  审批通过
                </span>
                <span v-if="proForm.fabuZhuangTai === 5">
                  审批不通过
                </span>
              </td>
            </tr>
            <tr>
              <td>审批意见：</td>
              <td colspan="7">{{proForm.shenHeYiJian}}</td>
            </tr>
            <tr>
              <td>审批时间：</td>
              <td colspan="7">{{proForm.modifyTime}}</td>
            </tr>
          </template>
          <tr v-if="type === 'check'">
            <td>审批：</td>
            <td colspan="7">
              <!--审批组件-->
              <noticeApprove @approveForm="approveForm" ref="approveForm" @valid="handleIsValid"></noticeApprove>
              <!--审批组件-->
            </td>
          </tr>
        </table>
        <!--提交审核-->
        <el-form-item class="submit-radio" v-if="type === 'check'">
          <el-button type="primary" class="submit" @click="submitForm()">提交</el-button>
          <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import fileDown from '../../../../components/system-management/FileDownload'
import editor from '@/components/system-management/ueditor/ueditor.vue'
import noticeApprove from '@/components/system-management/notice-approve.vue'
import { pushData } from '@/api'
import { checkSuccessed } from '../../../../utils/commonUtils'
export default {
  components: {
    fileDown,
    editor,
    noticeApprove
  },
  data () {
    return {
      // 富文本
      editread: true,
      content: '',
      proForm: {},
      childForm: {},
      tableData: [],
      grouptableData: [],
      currentPage: 1,
      isValid: false,
      type: this.$route.params.type,
      isShow: false
    }
  },
  methods: {
    detail () {
      pushData.ysCqFileDetail(this.$route.params.objectId).then((res) => {
        this.proForm = JSON.parse(res.data.ysCqFileDetail)
        this.grouptableData = this.proForm.zgyswjbcglbdList
        this.isShow = this.proForm.isChange === 1
        return this.proForm.clarifyContent
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
        type: 4, // 资格预审文件澄清
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
