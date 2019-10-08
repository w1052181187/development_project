<template>
  <div id="projectdetailbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/correct-notice-file' }">更正公告备案</el-breadcrumb-item>
        <el-breadcrumb-item>{{type === "check" ? '审批' : '查看'}}</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig" v-if="type === 'detail'">
        <router-link tag='span' to="/system-management/government-procurement/correct-notice-file">返回</router-link>
      </div>
    </div>
    <div class="contentbigbox">
      <el-form class="gp_approve">
        <table class="detailtable">
          <tr>
            <td>采购项目名称：</td>
            <td  colspan="3">{{proForm.faBaoMingCheng}}</td>
            <td>采购项目编号：</td>
            <td  colspan="3">{{proForm.faBaoBianHao}}</td>
          </tr>
          <tr>
            <td>公告名称：</td>
            <td colspan="3">{{proForm.gongGaoMingCheng}}</td>
            <td>公告类型：</td>
            <td colspan="3">
              {{getNoticeType(proForm.gongGaoType, proForm.isXuYaoZiGeYuShen)}}
            </td>
          </tr>
          <tr>
            <td>采购方式：</td>
            <td colspan="3" v-if="proForm.gongGaoType === 1">
              {{getMethod(proForm.gongGaoType)}}
            </td>
            <td colspan="5" v-else>
              {{getMethod(proForm.gongGaoType)}}
            </td>
            <template v-if="proForm.gongGaoType === 1">
              <td>资格预审：</td>
              <td colspan="3">
                {{proForm.isXuYaoZiGeYuShen === 1 ? '是' : '否'}}
              </td>
            </template>
          </tr>
          <tr>
            <td>是否需要报名：</td>
            <td colspan="3" v-if="proForm.annEndTime">
              {{proForm.isBaoMing === 1 ? '是' : '否'}}
            </td>
            <td colspan="5" v-else>
              {{proForm.isBaoMing === 1 ? '是' : '否'}}
            </td>
            <template v-if="proForm.annEndTime !== ''">
              <td>公告结束时间：</td>
              <td colspan="3">{{proForm.annEndTime}}</td>
            </template>
          </tr>
          <!--报名显示  不报名不显示-->
          <tr v-if="proForm.isBaoMing === 1 && proForm.baoMingStartTime && proForm.baoMingEndTime">
            <td>报名开始时间：</td>
            <td colspan="3">{{proForm.baoMingStartTime}}</td>
            <td>报名截止时间：</td>
            <td colspan="3">{{proForm.baoMingEndTime}}</td>
          </tr>
          <!--报名显示  不报名不显示-->
          <!--非资格预审显示-->
          <template v-if="proForm.isXuYaoZiGeYuShen === 0">
            <tr v-if="proForm.bidDocStartTime && proForm.bidDocEndTime">
              <td>采购文件获取开始时间：</td>
              <td colspan="3">{{proForm.bidDocStartTime}}</td>
              <td>采购文件获取截止时间：</td>
              <td colspan="3">{{proForm.bidDocEndTime}}</td>
            </tr>
            <tr v-if="proForm.submitDocEndTime && proForm.submitDocMethod">
              <td>递交响应文件截止时间：</td>
              <td colspan="3">{{proForm.submitDocEndTime}}</td>
              <td>递交响应文件方法：</td>
              <td colspan="3">{{proForm.submitDocMethod}}</td>
            </tr>
          </template>
          <!--非资格预审显示-->
          <!--资格预审显示-->
          <template v-if="proForm.isXuYaoZiGeYuShen === 1">
            <tr v-if="proForm.preDocStartTime && proForm.preDocEndTime">
              <td>资格预审文件获取开始时间：</td>
              <td colspan="3">{{proForm.preDocStartTime}}</td>
              <td>资格预审文件获取截止时间：</td>
              <td colspan="3">{{proForm.preDocEndTime}}</td>
            </tr>
            <tr v-if="proForm.submitDocEndTime && proForm.submitDocMethod">
              <td>递交申请文件截止时间：</td>
              <td colspan="3">{{proForm.submitDocEndTime}}</td>
              <td>递交申请文件方法：</td>
              <td colspan="3">{{proForm.submitDocMethod}}</td>
            </tr>
          </template>
          <!--资格预审显示-->
          <tr>
            <template v-if="proForm.docPrice">
              <!--非资格预审显示-->
              <template v-if="proForm.isXuYaoZiGeYuShen === 0">
                <td>采购文件售价（元）：</td>
                <td colspan="3">{{proForm.docPrice}}</td>
              </template>
              <!--非资格预审显示-->
              <!--资格预审显示-->
              <template v-if="proForm.isXuYaoZiGeYuShen === 1">
                <td>资格预审文件售价（元）：</td>
                <td colspan="3">{{proForm.docPrice}}</td>
              </template>
              <!--资格预审显示-->
            </template>
            <td>采购人：</td>
            <td colspan="3" v-if="proForm.docPrice">{{proForm.approvalnum}}</td>
            <td colspan="5" v-else>{{proForm.approvalnum}}</td>
          </tr>
          <tr>
            <td>公告内容：</td>
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
          <tr>
            <td>更正包组：</td>
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
            <td>公告发布责任人：</td>
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
          <tr v-if="proForm.zhaoBiaoRen !== '' && proForm.modifyTime !== ''">
            <td>提交人：</td>
            <td colspan="3">{{proForm.zhaoBiaoRen}}</td>
            <td>提交时间：</td>
            <td colspan="3">{{proForm.modifyTime}}</td>
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
import editor from '@/components/system-management/ueditor/ueditor.vue'
import fileDown from '../../../../components/system-management/FileDownload'
import noticeApprove from '@/components/system-management/notice-approve.vue'
import { pushData } from '@/api'
import { handleMethod, checkSuccessed, handleNoticeType } from '../../../../utils/commonUtils'
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
      currentPage: 1,
      // 详情
      proForm: {},
      childForm: {},
      tableData: [],
      grouptableData: [],
      contacttabledata: [],
      isValid: false,
      type: this.$route.params.type
    }
  },
  methods: {
    detail () {
      pushData.annoBgDetail(this.$route.params.objectId).then((res) => {
        this.proForm = JSON.parse(res.data.annoBgDetail)
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
        type: 2, // 更正公告
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
    getMethod (type) {
      return handleMethod(type)
    },
    getNoticeType (type, yushenType) {
      return handleNoticeType(type, yushenType)
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
