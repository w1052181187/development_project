<template>
  <div id="projectdetailbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/procurement-project-file' }">采购项目备案</el-breadcrumb-item>
        <el-breadcrumb-item>{{type === "check" ? '审批' : '详情'}}</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig" v-if="type === 'detail'">
        <router-link tag='span' to="/system-management/government-procurement/procurement-project-file">返回</router-link>
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
            <td>所属项目：</td>
            <td colspan="3">{{proForm.projectName}}</td>
            <td>采购项目类型：</td>
            <td colspan="3">
              <span v-if="proForm.xiangMuLeiXing === 1">
                工程
              </span>
              <span v-if="proForm.xiangMuLeiXing === 2">
                货物
              </span>
              <span v-if="proForm.xiangMuLeiXing === 3">
                服务
              </span>
            </td>
          </tr>
          <tr>
            <td>所属行业：</td>
            <td colspan="3">{{getIndustry(proForm.suoShuHangYe)}}</td>
            <td>项目预算（元）：</td>
            <td colspan="3">{{proForm.projectBudget}}</td>
          </tr>
          <tr>
            <td>是否PPP项目：</td>
            <td colspan="3">
              {{proForm.isPppRprject === 1 ? '是' : '不是'}}
            </td>
            <td>采购方式：</td>
            <td colspan="3">
              {{getMethod(proForm.zhaoBiaoFangShi)}}
            </td>
          </tr>
          <tr v-if="proForm.zhaoBiaoFangShi === 1">
            <td>是否资格预审：</td>
            <td colspan="5">
              {{proForm.isPrequalification === 1 ? '是' : '否'}}
            </td>
          </tr>
          <tr>
            <td>采购组织形式：</td>
            <td colspan="3">
              <span v-if="proForm.zhaoBiaoZuZhiXingShi === 1">
                依法招标
              </span>
              <span v-if="proForm.zhaoBiaoZuZhiXingShi === 2">
                自主招标
              </span>
              <span v-if="proForm.zhaoBiaoZuZhiXingShi === 3">
                集中采购
              </span>
              <span v-if="proForm.zhaoBiaoZuZhiXingShi === 4">
                分散采购
              </span>
            </td>
            <td>采购项目地址：</td>
            <td colspan="3">{{proForm.sectionAddress}}</td>
          </tr>
          <tr>
            <td>采购内容：</td>
            <td colspan="7">
              <editor ref="ueditor" class="ueditor" :editread="editread" :content="proForm.shuoMing"></editor>
            </td>
          </tr>
          <tr>
            <td>采购人：</td>
            <td colspan="3">{{proForm.purchasingAgent}}</td>
            <td>是否委托采购代理：</td>
            <td colspan="3">
              {{proForm.shiFouWeiTuoZhaoBiaoDaiLi === 1 ? '是' : '不是'}}
            </td>
          </tr>
          <tr>
            <td>采购代理机构：</td>
            <td colspan="7">{{proForm.procurementAgency}}</td>
          </tr>
          <tr>
            <td>采购代理权限说明：</td>
            <td colspan="7">{{proForm.biddingAgentAuthority}}</td>
          </tr>
          <tr>
            <td>采购代理内容与范围：</td>
            <td colspan="7">{{proForm.biddingAgentContent}}</td>
          </tr>
          <tr>
            <td>代理机构项目负责人：</td>
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
                  prop="divisionOfResponsibilities"
                  label="职责分工">
                </el-table-column>
                <el-table-column
                  prop="mobile_phone"
                  label="联系电话">
                </el-table-column>
              </el-table>
            </td>
          </tr>
          <tr>
            <td>监督部门名称：</td>
            <td colspan="7">{{proForm.jianDuBuMen}}</td>
          </tr>
          <tr>
            <td>附件：</td>
            <td colspan="7">
              <el-table
                class="file-tab"
                :data="tableData"
                border
                style="width: 100%">
                <el-table-column
                  type="index"
                  label="序号"
                  align="center"
                  width="55">
                </el-table-column>
                <el-table-column
                  prop="fuJianFile"
                  label="文件名称">
                  <template slot-scope="scope">
                    <file-down :fileName="scope.row.fuJianFile" :filePath = "scope.row.fuJianFileName" ></file-down>
                  </template>
                </el-table-column>
              </el-table>
            </td>
          </tr>
          <template v-if="type === 'detail' && proForm.fabaoStatus !== 0">
            <tr>
              <td>审批结果：</td>
              <td colspan="7">
                <span v-if="proForm.fabaoStatus === 1">
                  备案审批中
                </span>
                <span v-if="proForm.fabaoStatus === 2">
                  审批通过
                </span>
                <span v-if="proForm.fabaoStatus === 3">
                  审批不通过
                </span>
              </td>
            </tr>
            <tr>
              <td>审批意见：</td>
              <td colspan="7">{{proForm.shenPiYiJian}}</td>
            </tr>
            <tr>
              <td>审批时间：</td>
              <td colspan="7">{{proForm.modifyTime}}</td>
            </tr>
          </template>
          <tr v-if="type === 'check'">
            <td>审批结果：</td>
            <td colspan="7">
              <!--审批组件-->
              <noticeApprove @approveForm="approveForm" ref="approveForm" @valid="handleIsValid" :passLabel="2" :notPassLabel="3"></noticeApprove>
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
import { pushData } from '@/api'
import * as region from '@/assets/js/region'
import noticeApprove from '@/components/system-management/notice-approve.vue'
import { handleMethod, checkSuccessed, handleIndustry } from '../../../../utils/commonUtils'
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
      currentPage: 1,
      content: '',
      // 详情
      proForm: {
      },
      childForm: {},
      tableData: [],
      contacttabledata: [],
      isValid: false,
      type: this.$route.params.type
    }
  },
  methods: {
    detail () {
      pushData.fabaoDetail(this.$route.params.objectId).then((res) => {
        this.proForm = JSON.parse(res.data.fabaoDetail)
        this.proForm.sectionAddress = ''
        region.CityInfo.map((item) => {
          if (this.proForm.province_id == item.value) {
            this.proForm.sectionAddress += item.label
            item.children.map((shi) => {
              if (this.proForm.city_id == shi.value) {
                this.proForm.sectionAddress += shi.label
                shi.children.map((county) => {
                  if (this.proForm.county_id == county.value) {
                    this.proForm.sectionAddress += county.label
                  }
                })
              }
            })
          }
        })
        this.contacttabledata = this.proForm.contacts
        this.tableData = this.proForm.zbFaBaoFuJians
        return this.proForm
      }).then((form) => {
        this.$refs.ueditor.setContent(form.shuoMing)
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
        type: 1, // 采购项目
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
    getIndustry (type) { // 处理行业
      return handleIndustry(type)
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
