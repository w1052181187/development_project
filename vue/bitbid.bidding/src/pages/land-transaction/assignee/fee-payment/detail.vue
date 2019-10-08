<template>
  <div id="asspaydetailebox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>费用缴纳</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/fee-payment?roledsType=2' }">缴纳竞买保证金</el-breadcrumb-item>
        <el-breadcrumb-item>缴纳详情</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" label-width="150px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="12">
                <el-form-item label="项目名称：">
                  <span>{{sectionForm.projectName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="项目编号：">
                  <span>{{sectionForm.projectNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="地块编号：">
                  <span>{{sectionForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="公告名称：">
                  <span>{{sectionForm.annoNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--费用缴纳信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">费用缴纳信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="竞买人名称：" prop="companyName">
                  <span>{{bidderName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="费用类型：" prop="contacts">
                  <span>竞买保证金</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="缴纳金额（万元）：" prop="contactsNumber">
                  <span>{{sectionForm.bidBond}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="缴费状态：" prop="PayStatus">
                  <span v-if="this.payStatus === 0">未支付</span>
                  <span v-if="this.payStatus === 1">已支付</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--费用缴纳信息-->
      </el-form>
    </div>
  </div>
</template>
<script>
import { apply, landProject } from '@/api'
export default {
  data () {
    return {
      sectionForm: {},
      payStatus: '', // 缴费状态
      bidderName: '' // 竞买人名称
    }
  },
  methods: {
    // 显示项目及报名信息详情
    detail () {
      landProject.detail(this.$route.query.sectionId).then((res) => {
        let a = {}
        let b = {}
        a = res.data.landInformation
        b.projectName = res.data.landInformation.landAnnoMation.projectName
        b.projectNumber = res.data.landInformation.landAnnoMation.projectNumber
        b.annoNumber = res.data.landInformation.landAnnoMation.annoNumber
        b.sectionNumber = res.data.landInformation.sectionNumber
        this.sectionForm = Object.assign(a, b)
      })
      apply.applyDetail(this.$route.params.objectId).then((res) => {
        this.bidderName = res.data.Apply.bidderName
        this.payStatus = res.data.Apply.payStatus
      })
    }
  },
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/land-register/common.css';
</style>
