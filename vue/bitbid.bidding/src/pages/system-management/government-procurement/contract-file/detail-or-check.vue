<template>
  <div id="contractfilebox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/contract-file' }">合同备案</el-breadcrumb-item>
        <el-breadcrumb-item>{{type === "check" ? '审批' : '查看'}}</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig" v-if="type === 'detail'">
        <router-link tag='span' to="/system-management/government-procurement/contract-file">返回</router-link>
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
            <td>标段名称：</td>
            <td colspan="3">{{proForm.biaoDuanMingCheng}}</td>
            <td>合同金额：</td>
            <td colspan="3">{{proForm.contractPrice}}</td>
          </tr>
          <tr>
            <td>合同结算金额：</td>
            <td colspan="3">{{proForm.settlePrice}}</td>
            <td>合同期限(天)：</td>
            <td colspan="3">{{proForm.limiteTime}}</td>
          </tr>
          <tr>
            <td>合同完成时间：</td>
            <td colspan="3">{{proForm.finishTime}}</td>
            <td>实际履约期限(天)：</td>
            <td colspan="3">{{proForm.actuallyPeriod}}</td>
          </tr>
          <tr>
            <td>合同签署时间：</td>
            <td colspan="7">{{proForm.contractSignTime}}</td>
          </tr>
          <tr>
            <td>附件：</td>
            <td colspan="7">
              <file-down :fileName="proForm.fileName_local" :filePath = "proForm.fileName_server" ></file-down>
            </td>
          </tr>
          <tr>
            <td>质量要求：</td>
            <td colspan="7">{{proForm.qualityRequire}}</td>
          </tr>
          <tr>
            <td>履约信息：</td>
            <td colspan="7">{{proForm.performInfo}}</td>
          </tr>
          <tr>
            <td>履约变更内容：</td>
            <td colspan="7">
              <ueditor :content="proForm.performChangeContent" ref="ueditor_change" class="ueditor" :editread="true"></ueditor>
            </td>
          </tr>
          <tr>
            <td>合同内容：</td>
            <td colspan="7">
              <ueditor :content="proForm.contractContent" ref="ueditor_content" class="ueditor" :editread="true"></ueditor>
            </td>
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
          <el-button type="primary" class="submit" @click="submitForm">提交</el-button>
          <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import fileDown from '../../../../components/system-management/FileDownload'
import ueditor from '../../../../components/system-management/ueditor/ueditor'
import noticeApprove from '@/components/system-management/notice-approve.vue'
import { pushData } from '@/api'
import { checkSuccessed } from '../../../../utils/commonUtils'
export default {
  components: {
    fileDown,
    ueditor,
    noticeApprove
  },
  data () {
    return {
      proForm: {},
      tableData: [],
      childForm: {},
      currentPage: 1,
      isValid: false,
      type: this.$route.params.type
    }
  },
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    detail () {
      pushData.contractDetail(this.$route.params.objectId).then((res) => {
        this.proForm = JSON.parse(res.data.contractDetail)
        return this.proForm
      }).then((form => {
        this.$refs.ueditor_change.setContent(form.performChangeContent)
        this.$refs.ueditor_content.setContent(form.contractContent)
      }))
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
        type: 7, // 合同
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
  #contractfilebox{
    .file-tab {
      th {
        background-color: #f7f8fa;
      }
    }
  }
</style>
