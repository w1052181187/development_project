<template>
  <div id="contractupdatebox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>标后业务</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/assignee/post-bid-business/contract-sub?roledsType=2'}">合同提交</el-breadcrumb-item>
        <el-breadcrumb-item>修改合同</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="contractForm" :rules="rules" ref="contractForm" label-width="100px" class="demo-ruleForm">
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
        <!--项目信息-->
        <!--合同信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">合同信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="转让方名称：" prop="transferorName">
                  <el-input v-model="contractForm.transferorName"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="受让方名称：" prop="publicityName">
                  <el-input v-model="contractForm.publicityName"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="合同金额(万元)：" prop="contractMoney">
                  <el-input v-model="contractForm.contractMoney"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="合同签署时间：" prop="contractDate">
                  <el-date-picker
                    v-model="contractForm.contractDate"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="选择日期时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="合同附件" prop="">
                  <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></commonFileUpload>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="说明：" prop="content">
                  <el-input type="textarea" v-model="contractForm.content"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--合同信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('contractForm')">提交</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import { contract, fileInfo } from '@/api'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
export default {
  components: {
    commonFileUpload
  },
  data () {
    return {
      contractForm: {
      },
      sectionName: {
      },
      sectionNumber: {
      },
      annoNumber: {},
      payTypes: [
        {
          value: 0,
          label: '在线缴纳'
        },
        {
          value: 1,
          label: '保函'
        },
        {
          value: 2,
          label: '其他'
        },
        {
          value: 3,
          label: '支票'
        },
        {
          value: 4,
          label: '线下汇款'
        }
      ],
      payTypesvalue: '',
      approvaltableData: [],
      fileType: 9,
      originalId: '',
      oldFileName: '',
      // 文件上传保存的数组
      fileArry: [],
      rules: {
        payTypesvalue: [
          { required: true, message: '请选择缴费方式', trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  methods: {
    upLoadFile (file) {
      this.updateStatus = true
      this.fileArry = file
    },
    // ie9 获取删除信息
    ieDelectFile (array) {
      this.ieDelectArray = array
    },
    list () {
      let objectId = this.$route.query.contractId
      contract.queryOne(objectId).then((res) => {
        this.contractForm = res.data.contractOneInfo
        this.sectionName = res.data.contractOneInfo.section.sectionName
        this.sectionNumber = res.data.contractOneInfo.section.sectionNumber
        this.annoNumber = res.data.contractOneInfo.section.announcement.annoNumber
        if (res.data.contractOneInfo.fileInformations) {
          this.oldFileName = res.data.contractOneInfo.fileInformations.fileName
          this.originalId = res.data.contractOneInfo.fileInformations.objectId
        }
      })
    },
    // 提交
    submitForm (contractForm) {
      this.$refs[contractForm].validate((valid) => {
        if (valid) {
          let that = this
          this.contractForm.status = 1
          contract.update(this.contractForm).then((res) => {
            // 将附件信息入库
            if (that.fileArry.length !== 0) {
              that.fileArry[0].relatedCode = that.contractForm.code
              fileInfo.deleteFile(this.originalId).then((res) => {
                fileInfo.saveFile(this.fileArry).then((res) => {
                  this.fileArry = []
                })
              })
            }
            this.$router.push({path: '/mr/assignee/post-bid-business/contract-sub?roledsType=2'})
          })
        } else {
          return false
        }
      })
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/mr/assignee/post-bid-business/contract-sub?roledsType=2'})
    }
  },
  created () {},
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
</style>
