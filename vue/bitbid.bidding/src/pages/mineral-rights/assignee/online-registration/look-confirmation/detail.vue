<template>
  <div id="enrolment"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/assignee/online-registration/online-registration?roledsType=2' }">网上报名</el-breadcrumb-item>
        <el-breadcrumb-item>查看资格确认书</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm"  label-width="100px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：" prop="name">
                  <span>{{ruleForm.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：" prop="name">
                  <span>{{ruleForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="公告名称：" prop="name">
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
import {quaCon} from '@/api/public-resource/mineral-rights/qualiConfirmation/index'
import editor from '@/components/public-resource/mineral-rights/common/ueditor/ueditor.vue'
export default {
  data () {
    return {
      ruleForm: {},
      // 富文本
      editread: true, // 富文本状态
      content: '' // 富文本内容
    }
  },
  methods: {
    setContent () {
      this.$refs.ueditor.setContent(this.content)
    },
    list () {
      let url = null
      url = {
        isDelete: 0,
        sectionId: this.$route.query.ObjectId,
        bidder: this.$route.query.Bidder
      }
      quaCon.quaConDetail(url).then((res) => {
        let a = {}
        let b = {}
        let c = {}
        let d = {}
        a = res.data.qualiConfirmation.apply.section
        d = res.data.qualiConfirmation.apply.section.announcement
        d.annoNumber = res.data.qualiConfirmation.apply.section.announcement.annoNumber
        b.bidderName = res.data.qualiConfirmation.apply.bidderName
        c.biddingMark = res.data.qualiConfirmation.biddingMark
        c.qualiConfirmation = res.data.qualiConfirmation.qualiConfirmation
        this.ruleForm = Object.assign(a, b, c, d)
        this.content = this.ruleForm.qualiConfirmation
        this.setContent()
      })
    }
  },
  components: {
    editor
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
</style>
