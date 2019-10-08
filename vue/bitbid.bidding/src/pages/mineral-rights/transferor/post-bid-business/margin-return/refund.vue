<template>
  <div id="refundassmarignrefundbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>标后业务</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/transferor/post-bid-business/margin-return?roledsType=1' }">申请保证金退回</el-breadcrumb-item>
        <el-breadcrumb-item>退回保证金</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="refundedSecurityForm" ref="refundedSecurityForm" label-width="100px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：" prop="sectionName">
                  <span>{{sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：" prop="sectionNumber">
                  <span>{{sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="公告名称：" prop="annoNumber">
                  <span>{{annoNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--退回申请信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">退回申请信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="退回金额(万元)：" prop="bidBond">
                  <span>{{bidBond}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="退费方式：" prop="contacts">
                  <span v-if="refundedSecurityForm.payMethod === 0">在线缴纳</span>
                  <span v-if="refundedSecurityForm.payMethod === 1">保函</span>
                  <span v-if="refundedSecurityForm.payMethod === 2">其他</span>
                  <span v-if="refundedSecurityForm.payMethod === 3">支票</span>
                  <span v-if="refundedSecurityForm.payMethod === 4">线下汇款</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="保证金缴费证明：" prop="" class="bitianicon">
                  <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></commonFileUpload>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="说明：" prop="content">
                  <el-input type="content" v-model="refundedSecurityForm.content"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--退回申请信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('refundedSecurityForm')">提交</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import { refundedSecurity, apply, fileInfo } from '@/api'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
export default {
  components: {
    commonFileUpload
  },
  data () {
    return {
      refundedSecurityForm: {
        sectionId: this.$route.query.sectionId,
        assigneeId: this.$route.query.assigneeId
      },
      sectionName: {},
      annoNumber: {},
      sectionNumber: {},
      bidBond: {},
      oldFileName: '',
      // 业务类型 （1.项目 2.公告 3.出让文件 4.澄清修改文件 13 保证金）
      fileType: 13,
      // 文件上传保存的数组
      fileArry: []
    }
  },
  methods: {
    // 将子组件获取到的数据传给父组件
    upLoadFile (file) {
      this.fileArry = file
    },
    list () {
      let objectId = this.$route.query.objectId
      apply.applyDetail(objectId).then((res) => {
        this.refundedSecurityForm.payMethod = Number(res.data.Apply.payMethod)
        this.sectionName = res.data.Apply.section.sectionName
        this.sectionNumber = res.data.Apply.section.sectionNumber
        this.annoNumber = res.data.Apply.section.announcement.annoNumber
        this.bidBond = res.data.Apply.section.bidBond
      })
    },
    // 提交
    submitForm (refundedSecurityForm) {
      if (this.fileArry.length === 0) {
        this.$message({
          type: 'warning',
          message: '请上传文件'
        })
        return false
      }
      this.refundedSecurityForm.status = 2
      this.$refs[refundedSecurityForm].validate((valid) => {
        if (valid) {
          refundedSecurity.save(this.refundedSecurityForm).then((res) => {
            this.fileArry[0].relatedCode = res.data.refundedSecurity.code
            // 将附件信息入库
            fileInfo.saveFile(this.fileArry).then((res) => {
              this.fileArry = []
            })
            this.$router.push({path: '/mr/transferor/post-bid-business/margin-return?roledsType=1'})
          })
        } else {
          return false
        }
      })
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/mr/transferor/post-bid-business/margin-return?roledsType=1'})
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
