<template>
  <div id="enrolmentdetail"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/online-registration/online-registration?roledsType=2' }">网上报名</el-breadcrumb-item>
        <el-breadcrumb-item>查看报名信息</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm"  label-width="150px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="12">
                <el-form-item label="项目名称：">
                  <span>{{ruleForm.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="项目编号：">
                  <span>{{ruleForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="地块编号：">
                  <span>{{ruleForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="公告名称：">
                  <span>{{ruleForm.annoNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="受让人资格条件：">
                  <span>{{ruleForm.conditions}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--报名文件信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">报名文件信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="竞买人名称：" prop="name">
                  <span>{{ruleForm.bidderName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="统一社会信用代码：" prop="name">
                  <span>{{$store.getters.authUser.uscCode}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="报名文件：" prop="name" class="filebigbox">
                  <!--文件列表展示组件-->
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                  <!--文件列表展示组件-->
                </el-form-item>
              </el-col>
              <el-col>
                <el-form-item label="备注：" prop="name">
                  <div class="note">{{ruleForm.remark}}</div>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--报名文件信息-->
        <!--联系人信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">联系人信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="联系人：" prop="name">
                  <span>{{ruleForm.contacts}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="联系电话：" prop="name">
                  <span>{{ruleForm.contactsNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="电子邮箱：" prop="name">
                  <span>{{ruleForm.email}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="地址：" prop="name">
                  <span>{{ruleForm.address}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--联系人信息-->
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import {apply} from '@/api'
export default {
  components: {
    commonFileDownload
  },
  data () {
    return {
      fileObjecit: {
        fileName: '测试数据'
      },
      ruleForm: {},
      isSupplement: ''
    }
  },
  methods: {
    detail () {
      apply.applyDetail(this.$route.params.objectId).then((res) => {
        let a = {}
        let b = {}
        a = res.data.Apply
        b.sectionNumber = res.data.Apply.landInformation.sectionNumber
        b.sectionName = res.data.Apply.landInformation.landAnnoMation.projectName
        b.sectionNumber = res.data.Apply.landInformation.landAnnoMation.projectNumber
        b.annoNumber = res.data.Apply.landInformation.landAnnoMation.annoNumber === null ? res.data.Apply.landInformation.landAnnoMation.suppleAnnoNumber : res.data.Apply.landInformation.landAnnoMation.annoNumber
        a.conditions = res.data.Apply.landInformation.prerequisites
        if (res.data.Apply.fileInformations) {
          this.fileObjecit = res.data.Apply.fileInformations
        }
        this.ruleForm = Object.assign(a, b)
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
