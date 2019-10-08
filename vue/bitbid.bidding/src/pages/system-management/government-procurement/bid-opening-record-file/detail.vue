<template>
  <div id="bidopenrebox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/bid-opening-record-file' }">开标记录表备案</el-breadcrumb-item>
        <el-breadcrumb-item>项目查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <router-link tag='span' to="/system-management/government-procurement/bid-opening-record-file">返回</router-link>
      </div>
    </div>
    <div class="contentbigbox">
      <table class="detailtable">
        <tr>
          <td>采购项目编号：</td>
          <td  colspan="3">{{proForm.faBaoBianHao}}</td>
          <td>公告名称：</td>
          <td  colspan="3">{{proForm.gongGaoMingCheng}}</td>
        </tr>
        <tr>
          <td>包组编号：</td>
          <td colspan="3">{{proForm.biaoDuanBianHao}}</td>
          <td>包组名称：</td>
          <td colspan="3">{{proForm.biaoDuanMingCheng}}</td>
        </tr>
        <tr>
          <td>开标时间：</td>
          <td colspan="3">{{proForm.opening_time}}</td>
          <td>开标地点：</td>
          <td colspan="3">{{proForm.opening_addr}}</td>
        </tr>
        <tr>
          <td>开标记录内容：</td>
          <td colspan="7">
            <ueditor ref="ueditor" class="ueditor" :content="proForm.opening_content" :editread="true"></ueditor>
          </td>
        </tr>
        <tr>
          <td rowspan="3">开标参与者：</td>
          <td style="text-align:right;color:#999;">采购人</td>
          <td colspan="6" style="background:#fff;">
            <el-table
              class="file-tab"
              :data="tableDataPurchaser"
              border
              style="width: 100%">
              <el-table-column
                prop="enterprise_name"
                label="单位名称"
                align="left">
              </el-table-column>
              <el-table-column
                prop="name"
                label="姓名"
                align="left">
              </el-table-column>
            </el-table>
          </td>
        </tr>
        <tr>
          <td style="background: #fff;">代理机构</td>
          <td colspan="6">
            <el-table
              class="file-tab"
              :data="tableDataAgency"
              border
              style="width: 100%">
              <el-table-column
                prop="enterprise_name"
                label="单位名称"
                align="left">
              </el-table-column>
              <el-table-column
                prop="name"
                label="姓名"
                align="left">
              </el-table-column>
            </el-table>
          </td>
        </tr>
        <tr>
          <td style="background: #fff;">供应商代表</td>
          <td colspan="6">
            <el-table
              class="file-tab"
              :data="tableDataSupplier"
              border
              style="width: 100%">
              <el-table-column
                prop="enterprise_name"
                label="单位名称"
                align="left">
              </el-table-column>
              <el-table-column
                prop="name"
                label="姓名"
                align="left">
              </el-table-column>
            </el-table>
          </td>
        </tr>
        <tr>
          <td>附件：</td>
          <td colspan="7">
            <file-down :fileName="proForm.fuJian_LocalName" :filePath = "proForm.fuJian_ServerName" ></file-down>
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>
<script>
import fileDown from '../../../../components/system-management/FileDownload'
import ueditor from '../../../../components/system-management/ueditor/ueditor'
import { pushData } from '@/api'
export default {
  components: {
    fileDown,
    ueditor
  },
  data () {
    return {
      proForm: {},
      tableDataAgency: [], // 代理机构
      tableDataPurchaser: [], // 采购人
      tableDataSupplier: [], // 供应商
      currentPage: 1
    }
  },
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    detail () {
      pushData.kbLogDetail(this.$route.params.objectId).then((res) => {
        this.proForm = JSON.parse(res.data.kbLogDetail)
        console.log(this.proForm)
        let items = this.proForm.KbParticipants
        var type
        for (var item of items) {
          type = item.type
          switch (type) {
            case 1:
              this.tableDataPurchaser.push(item)
              break
            case 2:
              this.tableDataAgency.push(item)
              break
            case 3:
              this.tableDataSupplier.push(item)
              break
          }
        }
        return this.proForm
      }).then((form => {
        this.$refs.ueditor.setContent(form.opening_content)
      }))
    }
  },
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
  #bidopenrebox{
    .file-tab {
      th {
        background-color: #f7f8fa;
      }
    }
  }
</style>
