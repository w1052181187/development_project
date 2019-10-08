<template>
  <div id="buysituationbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/buying-situation-file' }">采购情况报告备案</el-breadcrumb-item>
        <el-breadcrumb-item>查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <router-link tag='span' to="/system-management/government-procurement/buying-situation-file">返回</router-link>
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
          <td>招投标情况报告名称：</td>
          <td colspan="3">{{proForm.title}}</td>
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
          <td>法定期限：</td>
          <td colspan="3">{{proForm.fadingqixian}}</td>
          <td>承若期限：</td>
          <td colspan="3">{{proForm.chengnuoqixian}}</td>
        </tr>
        <tr>
          <td>事项收费：</td>
          <td colspan="3">{{proForm.shixianshoufei}}</td>
          <td>办理机关：</td>
          <td colspan="3">{{proForm.banlijiguan}}</td>
        </tr>
        <tr>
          <td>责任部门：</td>
          <td colspan="3">{{proForm.zerenbumen}}</td>
          <td>联系人：</td>
          <td colspan="3">{{proForm.lianxiren}}</td>
        </tr>
        <tr>
          <td>联系人地址：</td>
          <td colspan="3">{{proForm.lxraddress}}</td>
          <td>联系人电话：</td>
          <td colspan="3">{{proForm.lxrtel}}</td>
        </tr>
        <tr>
          <td>附件：</td>
          <td colspan="7">
            <file-down :fileName="proForm.fileName_local" :filePath = "proForm.fileName_server" ></file-down>
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>
<script>
import fileDown from '../../../../components/system-management/FileDownload'
import { pushData } from '@/api'
export default {
  components: {
    fileDown
  },
  data () {
    return {
      proForm: {},
      tableData: [],
      currentPage: 1
    }
  },
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    detail () {
      pushData.buyingSituationDetail(this.$route.params.objectId).then((res) => {
        this.proForm = JSON.parse(res.data.annoBaoGaoDetail)
      })
    }
  },
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
  #buysituationbox{
    .file-tab {
      th {
        background-color: #f7f8fa;
      }
    }
  }
</style>
