<template>
  <div id="bidreportlbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/bidreport-winning-file' }">评标报告及中标候选人备案</el-breadcrumb-item>
        <el-breadcrumb-item>查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <router-link tag='span' to="/system-management/government-procurement/bidreport-winning-file">返回</router-link>
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
          <td>评标报告：</td>
          <td colspan="7">
            <file-down :fileName="proForm.fileName_local" :filePath = "proForm.fileName_server" ></file-down>
          </td>
        </tr>
        <tr>
          <td>中标候选人名单：</td>
          <td colspan="7">
            <el-table
              class="file-tab"
              :data="tableDataNameList"
              border
              style="width: 100%">
              <el-table-column
                label="中标候选人名称"
                prop="touBiaoRenName">
              </el-table-column>
              <el-table-column
                prop="bidPrice"
                label="中标价格(元)">
              </el-table-column>
              <el-table-column
                prop="paiming"
                label="中标候选人排名">
              </el-table-column>
            </el-table>
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
      tableDataNameList: [],
      currentPage: 1
    }
  },
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    detail () {
      pushData.pingBiaoDetail(this.$route.params.objectId).then((res) => {
        this.proForm = JSON.parse(res.data.pingBiaoDetail)
        this.tableDataNameList = this.proForm.houxuanrenList
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
  #bidreportlbox{
    .file-tab {
      th {
        background-color: #f7f8fa;
      }
    }
  }
</style>
