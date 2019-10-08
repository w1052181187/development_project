<template>
  <div id="send" class="maincontent">
    <div class="headertitle" >
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>网上报名</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/transferor/online-registration/send-confirmation?roledsType=1' }">发送资格确认书</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: `/mr/transferor/online-registration/send-confirmation/situation/${this.$route.query.situationObjectId}`, query: {roledsType: 1}}">查看发送情况</el-breadcrumb-item>
        <el-breadcrumb-item>发送资格确认书</el-breadcrumb-item>
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
                <el-form-item label="竞买人名称：" prop="name">
                  <span>{{ruleForm.bidderName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="竞买号：" prop="name">
                  <el-input v-model="ruleForm.biddingMark" placeholder="请输入竞买号"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="资格确认书：">
                  <div class="editor">
                    <editor ref="ueditor" class="ueditor" :editread="false" :content="content"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--资格确认书信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm()">提交</el-button>
          <el-button class="cancel" @click="cancelBtn()">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/public-resource/mineral-rights/common/ueditor/ueditor.vue'
import {quaCon} from '@/api/public-resource/mineral-rights/qualiConfirmation/index'
export default {
  data () {
    return {
      ruleForm: {},
      quaCon: {},
      // 富文本
      editread: false, // 富文本状态
      content: '' // 富文本内容
    }
  },
  methods: {
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
        c.qualiConSectionId = res.data.qualiConfirmation.sectionId
        c.qualiConfirmation = res.data.qualiConfirmation.qualiConfirmation
        c.quaConObject = res.data.qualiConfirmation.objectId
        this.ruleForm = Object.assign(a, b, c, d)
        this.content = res.data.qualiConfirmation
        this.$refs.ueditor.setContent(this.content) // 设置富文本内容
      })
    },
    submitForm () {
      if (this.ruleForm.biddingMark === null) {
        this.$message({
          type: 'warning',
          message: '竞买号不能为空'
        })
        return false
      }
      this.quaCon.qualiConfirmation = this.$refs.ueditor.getContent() // 富文本内容获取
      this.quaCon.objectId = this.ruleForm.quaConObject
      this.quaCon.sectionId = this.ruleForm.qualiConSectionId
      this.quaCon.isSend = 1
      this.quaCon.biddingMark = this.ruleForm.biddingMark
      quaCon.quaConUpdate(this.quaCon).then((res) => {
        this.$router.push({path: `/mr/transferor/online-registration/send-confirmation/situation/${this.$route.query.situationObjectId}`, query: {roledsType: 1}})
      })
    },
    cancelBtn () {
      this.$router.push({path: `/mr/transferor/online-registration/send-confirmation/situation/${this.$route.query.situationObjectId}`, query: {roledsType: 1}})
    },
    setContent () {
      this.$refs.ueditor.setContent(this.content)
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
