<template>
  <div id="contractdetailebox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>矿权交易备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/mineral-rights/record-the-contract-file' }">合同备案</el-breadcrumb-item>
        <el-breadcrumb-item>查看合同</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="contractForm" label-width="100px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：" prop="sectionName">
                  <span>{{sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：" prop="sectionNumber">
                  <span>{{sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="公告名称：" prop="unifiedTransCode">
                  <span>{{annoNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--合同信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">合同信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="转让方名称：" prop="companyName">
                  <span>{{contractForm.transferorName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="受让方名称：" prop="companyName">
                  <span>{{contractForm.publicityName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="合同金额(万元)：" prop="companyName">
                  <span>{{contractForm.contractMoney}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="合同签署时间：" prop="companyName">
                  <span>{{contractForm.contractDate}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="合同附件：" prop="companyName" class="filebigbox">
                  <!--文件列表展示组件-->
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                  <!--文件列表展示组件-->
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="说明：" prop="PayStatus">
                  <span>{{contractForm.content}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--合同信息-->
        <!--审批记录-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">审批记录</span>
          <el-table
            :data="approveRecords"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="approvrResult"
              label="审批结果"
              align="left">
              <template slot-scope="scope">
                <div v-if="parseInt(scope.row.approvrResult) === 1">
                  <span class="statusbox">审批通过</span>
                </div>
                <div v-if="parseInt(scope.row.approvrResult) === 0">
                  <span class="statusbox">审批不通过</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="approverName"
              label="操作人"
              align="left">
            </el-table-column>
            <el-table-column
              prop="approvrDate"
              label="操作时间"
              align="left">
            </el-table-column>
            <el-table-column
              prop="approvrOpinion"
              label="审批意见"
              align="left" width="700">
            </el-table-column>
          </el-table>
        </div>
        <!--审批记录-->
      </el-form>
    </div>
  </div>
</template>
<script>
import { scontract } from '@/api'
import commonFileDownload from '@/components/system-management/file-download.vue'
export default {
  components: {
    commonFileDownload
  },
  data () {
    return {
      contractForm: {},
      sectionName: [],
      sectionNumber: {},
      annoNumber: {},
      payTypes: [
        {
          value: 0,
          label: '在线缴纳'
        },
        {
          value: 1,
          label: '保函'
        },
        {
          value: 2,
          label: '其他'
        },
        {
          value: 3,
          label: '支票'
        },
        {
          value: 4,
          label: '线下汇款'
        }
      ],
      payTypesvalue: '',
      approveRecords: [],
      // 文件
      fileObjecit: {}
    }
  },
  methods: {
    // 内容获取
    list () {
      let objectId = this.$route.params.objectId
      scontract.queryOne(objectId).then((res) => {
        if (res.data.contractOneInfo.fileInformations) {
          this.fileObjecit = res.data.contractOneInfo.fileInformations
        }
        this.contractForm = res.data.contractOneInfo
        this.sectionName = res.data.contractOneInfo.section.sectionName
        this.sectionNumber = res.data.contractOneInfo.section.sectionNumber
        this.approveRecords = res.data.contractOneInfo.approveRecords
        this.annoNumber = res.data.contractOneInfo.section.announcement.annoNumber === null ? '' : res.data.contractOneInfo.section.announcement.annoNumber
      })
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/mr/transferor/post-bid-business/contract-view'})
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
</style>
