<template>
  <div id="subcontractbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>标后业务</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/assignee/post-bid-business/contract-sub?roledsType=2'}">合同提交</el-breadcrumb-item>
        <el-breadcrumb-item>提交合同</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" :rules="rules" ref="contractForm" label-width="100px" class="demo-ruleForm">
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
                <el-form-item label="公告名称：" prop="annoNumber">
                  <span>{{annoNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        </el-form>
      <el-form :model="contractForm" :rules="rules" ref="contractForm" label-width="100px" class="demo-ruleForm">
        <!--合同信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">合同信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="转让方名称：" prop="transferorName">
                  <el-input v-model="contractForm.transferorName" placeholder="请输入转让方名称"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="受让方名称：" prop="publicityName">
                  <el-input v-model="contractForm.publicityName" placeholder="请输入受让方名称"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="合同金额(万元)：" prop="contractMoney">
                  <el-input v-model="contractForm.contractMoney" placeholder="请输入合同金额(万元)"></el-input>
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
                <el-form-item label="合同附件" class="bitianicon">
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
import ElForm from 'element-ui/packages/form/src/form'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
export default {
  components: {
    ElForm,
    commonFileUpload
  },
  data () {
    return {
      contractForm: {
        sectionId: this.$route.query.sectionId
      },
      sectionForm: {},
      fileList: [],
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
      approvaltableData: [],
      rules: {
        transferorName: [
          { required: true, message: '请输入转让方名称', trigger: ['blur', 'change'] }
        ],
        publicityName: [
          { required: true, message: '请输入受让方名称', trigger: ['blur', 'change'] }
        ],
        contractMoney: [
          { required: true, message: '请输入合同金额(万元)', trigger: ['blur', 'change'] }
        ],
        contractDate: [
          { required: true, message: '请输入合同签署时间', trigger: ['blur', 'change'] }
        ]
      },
      fileType: 9,
      // 文件上传保存的数组
      fileArry: [],
      annoNumber: '',
      code: '',
      oldFileName: ''
    }
  },
  methods: {
    // 将子组件获取到的数据传给父组件
    upLoadFile (file) {
      this.fileArry = file
    },
    list () {
      contract.queryOne(this.$route.query.objectId).then((res) => {
        this.sectionForm = res.data.contractOneInfo.section
        this.annoNumber = res.data.contractOneInfo.section.announcement.annoNumber
        this.code = res.data.contractOneInfo.code
      })
    },
    // 提交
    submitForm (contractForm) {
      if (this.fileArry.length === 0) {
        this.$message({
          type: 'warning',
          message: '请上传文件'
        })
        return false
      }
      this.$refs[contractForm].validate((valid) => {
        if (valid) {
          this.contractForm.objectId = this.$route.query.objectId
          this.contractForm.status = 1
          // this.contractForm.code = this.code
          contract.update(this.contractForm).then((res) => {
            this.fileArry[0].relatedCode = this.code
            // 将附件信息入库
            fileInfo.saveFile(this.fileArry).then((res) => {
              this.fileArry = []
            })
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
  computed: {},
  created () {},
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
</style>
