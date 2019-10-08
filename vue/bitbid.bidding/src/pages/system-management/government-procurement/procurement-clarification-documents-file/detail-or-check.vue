<template>
  <div id="proclardocbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/procurement-clarification-documents-file' }">采购澄清修改文件备案</el-breadcrumb-item>
        <el-breadcrumb-item>{{type === "check" ? '审批' : '详情'}}</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig" v-if="type === 'detail'">
        <router-link tag='span' to="/system-management/government-procurement/procurement-clarification-documents-file">返回</router-link>
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
            <td>变更内容：</td>
            <td colspan="7">
              <ueditor ref="ueditor_change" class="ueditor_change" :editread="true" :content="proForm.clarify_content"></ueditor>
            </td>
          </tr>
          <tr>
            <td>供应商资格条件：</td>
            <td colspan="7">{{proForm.bid_qualification}}</td>
          </tr>
          <tr v-if="isShow">
            <td>采购文件使用费：</td>
            <td colspan="3">{{proForm.shouJia}}</td>
            <td>标书售价：</td>
            <td colspan="3">{{proForm.shouJia}}</td>
          </tr>
          <tr v-if="isShow">
            <td>发售开始时间：</td>
            <td colspan="3">{{proForm.faShou_StartTime}}</td>
            <td>发售截止时间：</td>
            <td colspan="3">{{proForm.faShou_EndTime}}</td>
          </tr>
          <tr v-if="isShow">
            <td>答疑澄清开始时间：</td>
            <td colspan="3">{{proForm.zhiYi_StartTime}}</td>
            <td>答疑澄清截止时间：</td>
            <td colspan="3">{{proForm.zhiYi_EndTime}}</td>
          </tr>
          <tr v-if="isShow">
            <td>响应文件截止时间：</td>
            <td colspan="3">{{proForm.touBiaoWenJian_EndTime}}</td>
            <td>响应文件递交方式：</td>
            <td colspan="3">{{proForm.bid_submit_method}}</td>
          </tr>
          <tr v-if="isShow">
            <td>保证金：</td>
            <td colspan="3">
              <span v-if="proForm.isJiaoNaTouBiaoBaoZhengjin === 1">
                是
              </span>
              <span v-if="proForm.isJiaoNaTouBiaoBaoZhengjin === 0">
                否
              </span>
            </td>
            <td>保证金金额：</td>
            <td colspan="3">{{proForm.touBiaoBaoZhengJin}}</td>
          </tr>
          <tr v-if="isShow">
            <td>控制价金额：</td>
            <td colspan="3">{{proForm.controlPrice}}</td>
            <td>响应有效期：</td>
            <td colspan="3">{{proForm.validity_of_bids}}</td>
          </tr>
          <tr v-if="isShow">
            <td>开标地点：</td>
            <td colspan="3">{{proForm.bidOpenPlace}}</td>
            <td>评标办法：</td>
            <td colspan="3">{{proForm.uation_method}}</td>
          </tr>
          <tr v-if="isShow">
            <td>开标方式：</td>
            <td colspan="3">{{proForm.opening_mode}}</td>
            <td>评审方式：</td>
            <td colspan="3">{{proForm.pingshen_mode}}</td>
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
                <!--<el-table-column-->
                <!--label="操作" width="100" align="center">-->
                <!--<template  slot-scope="scope">-->
                <!--<el-button type="text" size="small">-->
                <!--查看-->
                <!--</el-button>-->
                <!--</template>-->
                <!--</el-table-column>-->
              </el-table>
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
          <el-button type="primary" class="submit" @click="submitForm()">提交</el-button>
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
      childForm: {},
      grouptableData: [],
      currentPage: 1,
      isShow: false,
      isValid: false,
      type: this.$route.params.type
    }
  },
  methods: {
    detail () {
      pushData.zbwjbcFileDetail(this.$route.params.objectId).then((res) => {
        this.proForm = JSON.parse(res.data.zbwjbcFileDetail)
        this.grouptableData = this.proForm.zbwjbcglbdList
        this.isShow = this.proForm.fabuZhuangTai === 1
        return this.proForm.clarify_content
      }).then((content) => {
        this.$refs.ueditor_change.setContent(content)
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
        type: 6, // 采购澄清文件
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
  #proclardocbox{
    .file-tab {
      th {
        background-color: #f7f8fa;
      }
    }
  }
</style>
