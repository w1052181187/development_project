<template>
  <div id="confirmpepbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>成交确认</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/business-confirmation/confirm-people?roledsType=1' }">确认竞得人</el-breadcrumb-item>
        <el-breadcrumb-item>确认竞得人</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <el-form :model="sectionForm" ref="sectionForm" label-width="120px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox detailcontentbigbox">
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
        <!--退回申请信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">成交信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="有无竞得人：" prop="naradiome" style="text-align: left" class="bitianicon">
                  <el-radio-group v-model="salesConfirmation.radioForm" @change="radioChange">
                    <el-radio :label="1" name="有">有</el-radio>
                    <el-radio :label="0" name="无">无</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="showStaus">
                <el-form-item label="竞买人名称：" prop="naradiome" style="text-align: left" class="bitianicon">
                  <el-input v-model="salesConfirmation.bidderName" placeholder="请输入竞买人名称"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="showStaus">
                <el-form-item label="成交价(万元)：" prop="naradiome" style="text-align: left" class="bitianicon">
                  <el-input v-model="salesConfirmation.strikePrice" placeholder="请输入成交价(万元)"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="说明：" prop="PayStatus">
                  <el-input type="textarea" v-model="salesConfirmation.instruction"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="24" class="area">
                <el-form-item label="附件">
                  <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName" :type="typeFile"></commonFileUpload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--退回申请信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('salesConfirmation')" :loading="subStatus">提交</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
import {salesCon, fileInfo, landProject} from '@/api'
export default {
  components: {
    commonFileUpload
  },
  data () {
    return {
      sectionForm: {},
      salesConfirmation: {
        radioForm: 1
      },
      // 业务类型 （1.项目 2.公告 3.出让文件 4.澄清修改文件 6.成交确认书）
      fileType: 14,
      typeFile: 2,
      // 文件上传保存的数组
      fileArry: [],
      showStaus: true,
      oldFileName: '',
      subStatus: false
    }
  },
  methods: {
    radioChange (value) {
      if (value === 1) {
        this.showStaus = true
      } else {
        this.showStaus = false
      }
    },
    // 将子组件获取到的数据传给父组件
    upLoadFile (file) {
      this.fileArry = file
    },
    list () {
      salesCon.detail(this.$route.params.objectId).then((res) => {
        let a = {}
        let b = {}
        a = res.data.salesConfirmation.landInformation
        b.projectName = res.data.salesConfirmation.landInformation.landAnnoMation.projectName
        b.projectNumber = res.data.salesConfirmation.landInformation.landAnnoMation.projectNumber
        b.annoNumber = res.data.salesConfirmation.landInformation.landAnnoMation.annoNumber
        this.sectionForm = Object.assign(a, b)
        this.salesConfirmation = res.data.salesConfirmation
      })
    },
    // 提交
    submitForm (ruleForm) {
      let code = this.salesConfirmation.code
      this.salesConfirmation.isAffirmPerson = this.salesConfirmation.radioForm
      this.salesConfirmation.isPersonAffirm = 1
      this.subStatus = true
      this.salesConfirmation.personAffirmTime = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate() + ' ' + new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds()
      salesCon.salesConUpdate(this.salesConfirmation).then((res) => {
        if (this.fileArry.length !== 0) {
          this.fileArry[0].relatedCode = code
          // 将附件信息入库
          fileInfo.saveFile(this.fileArry).then((res) => {
            this.fileArry = []
          })
        }
        if (this.salesConfirmation.radioForm === 1) {
          this.sectionForm.winStatus = 1
        }
        this.subStatus = false
        // 确认竞得人之后 修改项目中标状态为已中标
        landProject.update(this.sectionForm).then((res) => {
          this.subStatus = false
          this.$router.push({path: '/lt/transferor/business-confirmation/confirm-people'})
        })
      })
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/lt/transferor/business-confirmation/confirm-people'})
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
