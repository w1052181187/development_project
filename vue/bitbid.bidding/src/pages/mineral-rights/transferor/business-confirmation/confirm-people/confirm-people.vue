<template>
  <div id="confirmpepbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>成交确认</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/transferor/business-confirmation/confirm-people?roledsType=1' }">确认竞得人</el-breadcrumb-item>
        <el-breadcrumb-item>确认竞得人</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <el-form :model="sectionForm" ref="sectionForm" label-width="100px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：" prop="sectionName">
                  <span>{{sectionForm.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：" prop="sectionNumber">
                  <span>{{sectionForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="公告名称：" prop="unifiedTransCode">
                  <span>{{announcement}}</span>
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
                  <el-radio-group v-model="radioForm" @change="radioChange">
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
                  <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></commonFileUpload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--退回申请信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('sectionForm')">提交</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import {salesCon, fileInfo, prProject} from '@/api'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
export default {
  components: {
    commonFileUpload
  },
  data () {
    return {
      radioForm: '',
      sectionForm: {},
      fileList: [],
      salesConfirmation: {},
      // 业务类型 （1.项目 2.公告 3.出让文件 4.澄清修改文件 6.成交确认书）
      fileType: 14,
      // 文件上传保存的数组
      fileArry: [],
      isAffirmRadFrom: [
        {
          label: 0,
          name: '无'
        },
        {
          label: 1,
          name: '有'
        }
      ],
      showStaus: true,
      announcement: '',
      oldFileName: ''
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
        this.sectionForm = res.data.salesConfirmation.section
        this.announcement = res.data.salesConfirmation.section.announcement.annoNumber
        this.salesConfirmation = res.data.salesConfirmation
      })
    },
    // 提交
    submitForm (ruleForm) {
      let code = this.salesConfirmation.code
      this.salesConfirmation.isAffirmPerson = this.radioForm
      this.salesConfirmation.isPersonAffirm = 1
      this.salesConfirmation.personAffirmTime = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate() + ' ' + new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds()
      this.$refs[ruleForm].validate((valid) => {
        if (valid) {
          salesCon.salesConUpdate(this.salesConfirmation).then((res) => {
            if (this.fileArry.length !== 0) {
              this.fileArry[0].relatedCode = code
              // 将附件信息入库
              fileInfo.saveFile(this.fileArry).then((res) => {
                this.fileArry = []
              })
            }
            if (this.radioForm === 1) {
              this.sectionForm.winStatus = 1
            }
            // 确认竞得人之后 修改项目中标状态为已中标
            prProject.update(this.sectionForm).then((res) => {
              this.$router.push({path: '/mr/transferor/business-confirmation/confirm-people'})
            })
          })
        } else {
          return false
        }
      })
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/mr/transferor/business-confirmation/confirm-people'})
    }
  },
  created () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
</style>
