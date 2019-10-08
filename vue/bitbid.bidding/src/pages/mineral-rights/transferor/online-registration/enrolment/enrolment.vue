<template>
  <div id="enrolment"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>网上报名</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/transferor/online-registration/enrolment?roledsType=1' }">报名受理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{path: '/mr/transferor/online-registration/enrolment/situation', query: {ObjectId: this.$route.query.sitObjectId, roledsType: 1}}">报名情况</el-breadcrumb-item>
        <el-breadcrumb-item>受理报名信息</el-breadcrumb-item>
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
                <el-form-item label="项目名称：">
                  <span>{{ruleForm.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：">
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
                <el-form-item label="附件：" class="filebigbox">
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
        <!--报名受理-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #0206fd">报名受理</span>
          <div class="formmian">
            <el-row>
              <el-col>
                <el-form-item label="受理结果：">
                  <template>
                    <el-radio v-model="ruleForm.acceptResults" :label="acceptResultsRadio.lable" v-for="(acceptResultsRadio, index) in radioAcceptResults" :key="index">{{acceptResultsRadio.name}}</el-radio>
                  </template>
                </el-form-item>
              </el-col>
              <el-col>
                <el-form-item label="受理意见：">
                  <el-input type="textarea" v-model="ruleForm.acceptOpinion"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--报名受理-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm()">提交</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import {apply, quaCon} from '@/api'
import commonFileDownload from '@/components/public-resource/mineral-rights/common/file-download.vue'
export default {
  components: {
    commonFileDownload
  },
  data () {
    return {
      radioAcceptResults: [
        {
          lable: 0,
          name: '驳回'
        },
        {
          lable: 1,
          name: '通过'
        }
      ],
      ruleForm: {},
      fileObjecit: {}
    }
  },
  methods: {
    list () {
      apply.applyDetail(this.$route.params.objectId).then((res) => {
        let a = {}
        let b = {}
        let c = {}
        a = res.data.Apply
        b.sectionName = res.data.Apply.section.sectionName
        b.sectionNumber = res.data.Apply.section.sectionNumber
        c.annoNumber = res.data.Apply.section.announcement.annoNumber
        if (res.data.Apply.fileInformations) {
          this.fileObjecit = res.data.Apply.fileInformations
        }
        this.ruleForm = Object.assign(a, b, c)
      })
    },
    // 提交
    submitForm () {
      if (this.ruleForm.acceptResults === 2) {
        this.$message({
          type: 'warning',
          message: '请选择受理结果'
        })
        return false
      }
      let qua = {}
      qua.sectionId = this.ruleForm.section.objectId
      qua.bidder = this.ruleForm.assigneeId
      qua.qualiConfirmation = this.ruleForm.acceptOpinion
      if (this.ruleForm.acceptResults === 1) {
        quaCon.quaConAdd(qua).then((res) => {})
      }
      apply.applyUpdate(this.ruleForm).then((res) => {
        this.$router.push({path: '/mr/transferor/online-registration/enrolment/situation', query: {ObjectId: this.$route.query.sitObjectId, roledsType: 1}})
      })
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/mr/transferor/online-registration/enrolment/situation', query: {ObjectId: this.$route.query.sitObjectId, roledsType: 1}})
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
</style>
