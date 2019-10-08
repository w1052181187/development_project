<template>
  <div id="send" class="maincontent">
    <div class="headertitle" >
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>网上报名</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/online-registration/send-confirmation?roledsType=1&showStatus=false' }">发送资格确认书</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: `/lt/transferor/online-registration/send-confirmation/situation/${this.$route.params.objectId}`, query: {roledsType: 1, showStatus: false}}">查看发送情况</el-breadcrumb-item>
        <el-breadcrumb-item>发送资格确认书</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="200px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="地块编号：" prop="name">
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
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer === 1 || this.$route.query.methodOfTransfer === 2)">
                <el-form-item label="竞买人名称：" prop="name">
                  <span>{{ruleForm.bidderName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer === 3 || this.$route.query.methodOfTransfer === 4)">
                <el-form-item label="投标人名称：" prop="name">
                  <span>{{ruleForm.bidderName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer === 1 || this.$route.query.methodOfTransfer === 2)">
                <el-form-item label="竞买号：" prop="biddingMark">
                  <el-input v-model="ruleForm.biddingMark" placeholder="请输入竞买号"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="资格确认书：" class="bitianicon">
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
          <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </el-form-item>
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
      quaCon: {},
      rules: {
        biddingMark: [
          { required: true, message: '请输入竞买号', trigger: 'blur' },
          { min: 1, max: 500, message: '长度在 1~500个字符', trigger: ['blur', 'change'] }
        ]
      },
      // 富文本
      content: '' // 富文本内容
    }
  },
  methods: {
    submitForm (sectionForm) {
      if (this.$refs.ueditor.getContent() === '') {
        this.$message({
          type: 'warning',
          message: '资格确认书不能为空'
        })
        return false
      }
      this.$refs[sectionForm].validate((valid) => {
        if (valid) {
          this.quaCon.qualiConfirmation = this.$refs.ueditor.getContent() // 富文本内容获取
          this.quaCon.objectId = this.ruleForm.quaConObject
          this.quaCon.sectionId = this.ruleForm.qualiConSectionId
          this.quaCon.isSend = 1
          this.quaCon.biddingMark = this.ruleForm.biddingMark
          this.quaCon.sendTime = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate() + ' ' + new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds()
          quaCon.quaConUpdate(this.quaCon).then((res) => {
            this.$router.push({path: `/lt/transferor/online-registration/send-confirmation/situation/${this.$route.query.situationObjectId}`, query: {roledsType: 1}})
          })
        } else {
          return false
        }
      })
    },
    cancelBtn () {
      this.$router.push({path: `/lt/transferor/online-registration/send-confirmation/situation/${this.$route.params.objectId}`, query: {roledsType: 1, showStatus: false}})
    },
    list () {
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
  @import '../../../../../assets/css/land-register/common.css';
</style>
