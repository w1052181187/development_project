<template>
  <div id="land_ann_subaddbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>项目管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/sub-agree-ann?roledsType=1' }">提交协议出让公告</el-breadcrumb-item>
        <el-breadcrumb-item>添加出让公告</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" :rules="rules" ref="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：" prop="projectName">
                  <el-input v-model="sectionForm.projectName" placeholder="请输入项目名称"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：" prop="projectNumber">
                  <el-input v-model="sectionForm.projectNumber" placeholder="请输入项目编号"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--相关地块信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">相关地块信息</span>
          <div class="formmian">
            <div v-for="(relevantMassif,index) in sectionForm.landInformations" :key="index">
              <el-row>
                <span style="margin-left: 80px;margin-bottom: 22px;color: #000;font-weight: 800">块地{{index + 1}}</span>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="地块编号：">
                    <span>{{relevantMassif.sectionNumber}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="受让人名称：" :prop="'landInformations.'+index+'.assigneeName'" :rules="rules.assigneeName">
                    <el-input v-model="relevantMassif.assigneeName" placeholder="请输入受让人名称"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="成交价(万元)：" :prop="'landInformations.'+index+'.transactionPrice'" :rules="rules.transactionPrice">
                    <el-input v-model="relevantMassif.transactionPrice" placeholder="请输入成交价"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
        <!--相关地块信息-->
        <!--公告内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">公告内容</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="公告名称：" prop="annoNumber">
                  <el-input v-model="sectionForm.annoNumber" placeholder="请输入公告名称"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="公告类型：" prop="contacts">
                  <span v-if="sectionForm.annoType === 3">协议出让公告</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="公告发布时间：" class="bitianicon timesbox">
                  <el-date-picker
                    v-model="annDataRange"
                    type="datetimerange"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    range-separator="至"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                    align="right">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="公告内容：" class="bitianicon">
                  <div class="editor">
                    <editor ref="ueditor" class="ueditor" :editread="false" :content="content"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="附件：">
                  <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName" :type="typeFile"></commonFileUpload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--公告内容-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('sectionForm', 1)" :loading="subStatus">提交</el-button>
          <el-button type="primary" class="save" @click="submitForm('sectionForm', 0)" :loading="subStatus">保存</el-button>
          <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import {sumType} from '@/assets/js/validate'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import { landProject, landAnno, fileInfo } from '@/api'
export default {
  components: {
    commonFileUpload,
    editor
  },
  data () {
    // 数字
    var sumTypes = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入数字'))
      } else if (!sumType(value)) {
        callback(new Error('请输入数字或两位小数'))
      } else {
        callback()
      }
    }
    return {
      sectionForm: {
        landInformations: [],
        landInfoIds: [],
        annoType: 3
      },
      content: '', // 公告内容
      annDataRange: [],
      subStatus: false,
      rules: {
        projectName: [
          { required: true, message: '请输入项目名称', trigger: 'blur' },
          { min: 1, max: 500, message: '长度在 1~500个字符', trigger: ['blur', 'change'] }
        ],
        projectNumber: [
          { required: true, message: '请输入项目编号', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1~100个字符', trigger: ['blur', 'change'] }
        ],
        assigneeName: [
          { required: true, message: '请输入受让人名称', trigger: ['blur', 'change'] },
          { min: 1, max: 600, message: '长度在 1~600个字符', trigger: ['blur', 'change'] }
        ],
        transactionPrice: [
          { required: true, message: '请输入受让人名称', trigger: ['blur', 'change'] },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] },
          { validator: sumTypes, trigger: ['blur', 'change'] }
        ]
      },
      // 业务类型
      fileType: 16,
      // 文件类型：土地
      typeFile: 2,
      // 文件上传保存的数组
      fileArry: [],
      oldFileName: ''
    }
  },
  methods: {
    landInfo () {
      let arrLength = this.$route.query.landInfoIds.length
      for (var i = 0; i < arrLength; i++) {
        landProject.detail(this.$route.query.landInfoIds[i]).then(res => {
          this.sectionForm.landInformations.push(res.data.landInformation)
        })
      }
    },
    // 将子组件获取到的数据传给父组件
    upLoadFile (file) {
      this.fileArry = file
    },
    // 提交保存
    submitForm (sectionForm, flag) {
      if (this.annDataRange) {
        this.sectionForm.annStartTime = this.annDataRange[0]
        this.sectionForm.annEndTime = this.annDataRange[1]
      } else {
        this.$message({
          type: 'warning',
          message: '公告发布时间不能为空'
        })
        return false
      }
      this.$refs[sectionForm].validate((valid) => {
        if (valid) {
          this.sectionForm.status = flag
          this.sectionForm.content = this.$refs.ueditor.getContent()
          this.sectionForm.landInfoIds = this.$route.query.landInfoIds
          this.subStatus = true
          landAnno.saveAnnoun(this.sectionForm).then((res) => {
            console.log(res)
            if (this.fileArry.length !== 0) {
              this.fileArry[0].relatedCode = res.data.landAnno.code
              // 将附件信息入库
              fileInfo.saveFile(this.fileArry).then((res) => {
                this.fileArry = []
              })
            }
            this.subStatus = false
            this.$router.push({path: '/lt/transferor/sub-agree-ann?roledsType=1&showStatus=false'})
          })
        } else {
          return false
        }
      })
    }
  },
  mounted () {
    this.landInfo()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
  #land_ann_subaddbox{
    .timesbox .el-form-item__content{
      width: 74%;
    }
    .hourbox .el-date-editor{
      width: 100%;
    }
  }
</style>
