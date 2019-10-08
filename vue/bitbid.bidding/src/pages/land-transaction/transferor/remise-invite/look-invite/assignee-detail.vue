<template>
  <div id="addbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让邀请</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/remise-invite/look-invite?roledsType=1' }">查看出让邀请确认情况</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: `/lt/transferor/remise-invite/look-invite/look-confirm/${this.$route.params.objectId}`, query: {roledsType: 1, showStatus: false}}">查看确认情况</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: `/lt/transferor/remise-invite/look-invite/assignee-situation/${this.$route.params.objectId}`, query: {roledsType: 1, showStatus: false}}">受让人确认情况</el-breadcrumb-item>
        <el-breadcrumb-item>详情</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <router-link tag='span' :to="{ path: `/lt/transferor/remise-invite/look-invite/assignee-situation/${this.$route.params.objectId}`, query: {roledsType: 1, showStatus: false}}">返回</router-link>
      </div>
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：" prop="unifiedTransCode">
                  <span>{{sectionForm.projectName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：" prop="investProjectCode">
                  <span>{{sectionForm.projectNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="地块编号：" prop="sectionNumber">
                  <span>{{sectionForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="邀请书名称：" prop="unifiedTransCode">
                  <span>{{sectionForm.landAnnoMation.annoNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="受让人名称：" prop="investProjectCode">
                  <span>{{sectionForm.assigneeName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="联系人：" prop="sectionNumber">
                  <span>{{sectionForm.contacts}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="联系电话：" prop="unifiedTransCode">
                  <span>{{sectionForm.contactsNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="联系人电子邮件：" prop="investProjectCode">
                  <span>{{sectionForm.email}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="确认状态：" prop="sectionNumber">
                  <span v-if="sectionForm.isConfirm === 0">未确认</span>
                  <span v-if="sectionForm.isConfirm === 1">已确认</span>
                  <span v-if="sectionForm.isConfirm === 2">已拒绝</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="说明：" prop="unifiedTransCode">
                  <span>{{sectionForm.unifiedTransCode}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
      </el-form>
    </div>
  </div>
</template>
<script>
import {landProject} from '@/api'
export default {
  data () {
    return {
      sectionForm: {}
    }
  },
  methods: {
    detail () {
      landProject.detail(this.$route.params.objectId).then(res => {
        console.log(res.data)
        this.sectionForm = res.data.landInformation
      })
    }
  },
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
