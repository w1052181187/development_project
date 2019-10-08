<template>
  <div id="seedetail"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>网上报名</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/online-registration/send-confirmation?roledsType=1&showStatus=false' }">发送资格确认书</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: `/lt/transferor/online-registration/send-confirmation/situation/${this.$route.params.objectId}`, query: {roledsType: 1, showStatus: false}}">查看发送情况</el-breadcrumb-item>
        <el-breadcrumb-item>查看资格确认书</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm"  label-width="200px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="地块编号：">
                  <span>{{ruleForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
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
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer === 1 || this.$route.query.methodOfTransfer === 2)">
                <el-form-item label="竞买人名称：">
                  <span>{{ruleForm.bidderName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer === 3 || this.$route.query.methodOfTransfer === 4)">
                <el-form-item label="投标人名称：">
                  <span>{{ruleForm.bidderName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer === 1 || this.$route.query.methodOfTransfer === 2)">
                <el-form-item label="竞买号：">
                  <span>{{ruleForm.biddingMark}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="资格确认书：">
                  <div class="editor">
                    <editor ref="ueditor" class="ueditor" :editread="true" :content="content"></editor>
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
  data () {
    return {
      ruleForm: {},
      // 富文本
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
        let a = {}
        let b = {}
        let c = {}
        let d = {}
        a = res.data.qualiConfirmation.apply.landInformation
        d = res.data.qualiConfirmation.apply.landInformation.landAnnoMation
        d.annoNumber = res.data.qualiConfirmation.apply.landInformation.landAnnoMation.annoNumber
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
  components: {
    editor
  },
  mounted () {
    this.detail()
    this.$refs.ueditor.setContent(this.content)
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
