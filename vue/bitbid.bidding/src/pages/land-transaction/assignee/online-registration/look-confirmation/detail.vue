<template>
  <div id="enrolment"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>网上报名</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/online-registration/look-confirmation?roledsType=2' }">查看资格确认书</el-breadcrumb-item>
        <el-breadcrumb-item>查看</el-breadcrumb-item>
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
                  <span>{{ruleForm.projectName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="项目编号：">
                  <span>{{ruleForm.projectNumber}}</span>
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
          </div>
        </div>
        <!--项目信息-->
        <!--资格确认书信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">资格确认书信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="竞买人名称：">
                  <span>{{ruleForm.bidderName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="竞买号：">
                  <span>{{ruleForm.biddingMark}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="资格确认书：">
                  <div class="editor">
                    <editor ref="ueditor" class="ueditor" :editread="editread" :content="content"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--资格确认书信息-->
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import {quaCon} from '@/api'
export default {
  components: {
    editor
  },
  data () {
    return {
      ruleForm: {},
      // 富文本
      editread: true, // 富文本状态
      content: '' // 富文本内容
    }
  },
  methods: {
    detail () {
      let url = null
      url = {
        isDelete: 0,
        sectionId: this.$route.query.situationObjectId,
        bidder: this.$route.query.Bidder
      }
      quaCon.quaConDetail(url).then((res) => {
        console.log(res)
        let a = {}
        let b = {}
        let c = {}
        let d = {}
        a = res.data.qualiConfirmation.apply.landInformation
        d = res.data.qualiConfirmation.apply.landInformation.landAnnoMation
        d.annoNumber = res.data.qualiConfirmation.apply.landInformation.landAnnoMation.annoNumber
        d.projectNumber = res.data.qualiConfirmation.apply.landInformation.landAnnoMation.projectNumber
        d.projectName = res.data.qualiConfirmation.apply.landInformation.landAnnoMation.projectName
        d.sectionNumber = res.data.qualiConfirmation.apply.landInformation.sectionNumber
        b.bidderName = res.data.qualiConfirmation.apply.bidderName
        c.biddingMark = res.data.qualiConfirmation.biddingMark
        c.qualiConSectionId = res.data.qualiConfirmation.sectionId
        c.qualiConfirmation = res.data.qualiConfirmation.qualiConfirmation
        c.quaConObject = res.data.qualiConfirmation.objectId
        this.ruleForm = Object.assign(a, b, c, d)
        this.content = res.data.qualiConfirmation.qualiConfirmation
        this.$refs.ueditor.setContent(this.content) // 设置富文本内容
      })
    }
  },
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
</style>
