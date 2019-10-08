<template>
  <div id="projectdetailbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/procurement-project-file' }">采购项目备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/procurement-project-file/package-group'+ handleGroupList(this.$route.query.faBaoId)}">包组信息</el-breadcrumb-item>
        <el-breadcrumb-item>查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <router-link tag='span' to="/system-management/government-procurement/procurement-project-file/package-group">返回</router-link>
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
          <td>包组名称：</td>
          <td colspan="3">{{proForm.biaoDuanMingCheng}}</td>
          <td>包组编号：</td>
          <td colspan="3">{{proForm.biaoDuanBianHao}}</td>
        </tr>
        <tr>
          <td>采购方式：</td>
          <td colspan="3" v-if="proForm.zhaoBiaoFangShi === 1">
            {{handelProcurementMode(proForm.zhaoBiaoFangShi)}}
          </td>
          <td colspan="5" v-else>
            {{handelProcurementMode(proForm.zhaoBiaoFangShi)}}
          </td>
          <template v-if="proForm.zhaoBiaoFangShi === 1">
            <td>是否资格预审</td>
            <td colspan="3">
              {{isPrequalification(proForm.isPrequalification)}}
            </td>
          </template>
        </tr>
        <!-- 采购方式为邀请招标  单一来源 -->
        <tr v-if="proForm.zhaoBiaoFangShi == 2 || proForm.zhaoBiaoFangShi == 4">
          <td>供应商：</td>
          <td colspan="7">
            <el-table
              :data="tableData"
              border
              style="width: 100%"
              header-cell-class-name="tabletitles">
              <el-table-column
                prop="touBiaoName"
                label="供应商名称">
              </el-table-column>
              <el-table-column
                prop="mobile"
                label="手机">
              </el-table-column>
              <el-table-column
                prop="email"
                label="邮箱">
              </el-table-column>
            </el-table>
          </td>
        </tr>
        <!-- 采购方式为邀请招标  单一来源 -->
        <tr>
          <td>包组类型：</td>
          <td colspan="3">
            {{handleType(proForm.leiXing)}}
          </td>
          <td>包组估算价：</td>
          <td colspan="3">{{proForm.contractEstimatedPrice}}</td>
        </tr>
        <tr>
          <td>包组内容：</td>
          <td colspan="7">{{proForm.shuoMing}}</td>
        </tr>
        <tr>
          <td>供应商资格条件：</td>
          <td colspan="7">{{proForm.bidderPrerequisites}}</td>
        </tr>
      </table>
    </div>
  </div>
</template>
<script>
import { pushData } from '@/api'
import { handleMethod } from '../../../../utils/commonUtils'
export default {
  components: {
  },
  data () {
    return {
      proForm: {},
      tableData: []
    }
  },
  methods: {
    detail () {
      pushData.sectionDetail(this.$route.params.objectId).then((res) => {
        this.proForm = JSON.parse(res.data.sectionDetail)
        this.tableData = this.proForm.gongYingShangList
      })
    },
    handelProcurementMode (mode) {
      return handleMethod(mode)
    },
    isPrequalification (isPrequalification) {
      var str
      switch (isPrequalification) {
        case 1:
          str = '是'
          break
        case 0:
          str = '否'
          break
      }
      return str
    },
    handleType (type) {
      var typeStr
      switch (type) {
        case 1:
          typeStr = '工程'
          break
        case 2:
          typeStr = '货物'
          break
        case 3:
          typeStr = '服务'
          break
      }
      return typeStr
    },
    handleGroupList (id) {
      return '?fabaoId=' + id
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
