<template>
  <div id="marginappaddbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>标后业务</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/open-criticize-mark/opening-recorder?roledsType=1&showStatus=false' }">提交开标记录表</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/open-criticize-mark/opening-recorder/select-plot?roledsType=1&showStatus=false' }">选择地块</el-breadcrumb-item>
        <el-breadcrumb-item>添加开标记录表</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="refundedSecurityForm" :rules="rules" ref="refundedSecurityForm" label-width="160px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="12">
                <el-form-item label="项目名称：">
                  <span>{{refundedSecurityForm.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="项目编号：">
                  <span>{{refundedSecurityForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="地块编号：">
                  <span>{{refundedSecurityForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="公告名称：">
                  <span>{{refundedSecurityForm.annoNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--开标信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="开标时间：" prop="openStartTime">
                  <el-date-picker
                    v-model="refundedSecurityForm.openStartTime"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="请输入拍卖开始时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="开标方式：" prop="bidEvaluationMethods">
                  <el-select v-model="refundedSecurityForm.bidEvaluationMethods">
                    <el-option
                      v-for="item in evaluationMethodData"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="开标地点：" prop="place">
                  <el-input v-model="refundedSecurityForm.place" placeholder="请输入开标地点"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="开标记录内容：" class="bitianicon">
                  <div class="editor">
                    <editor ref="openUeditor" class="ueditor" :editread="false" :content="openContent"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="开标参与人员：" class="bitianicon">
                  <div class="editor">
                    <editor ref="partUeditor" class="ueditor" :editread="false" :content="partContent"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="开标记录表：" class="bitianicon">
                  <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></commonFileUpload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--开标信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('refundedSecurityForm', 0)" :loading="subStatus">发布</el-button>
          <el-button type="primary" @click="submitForm('refundedSecurityForm', 1)" :loading="subStatus">提交</el-button>
          <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
export default {
  components: {
    editor,
    commonFileUpload
  },
  data () {
    return {
      refundedSecurityForm: {},
      evaluationMethodData: [
        {
          value: 0,
          label: '在线开标'
        },
        {
          value: 1,
          label: '线下开标'
        }
      ],
      subStatus: false,
      // 业务类型
      fileType: 1,
      // 文件上传保存的数组
      fileArry: [],
      oldFileName: '',
      rules: {
        openStartTime: [
          {required: true, message: '请选择开标时间', trigger: ['blur', 'change']}
        ],
        bidEvaluationMethods: [
          {required: true, message: '请选择开标方式', trigger: ['blur', 'change']}
        ],
        place: [
          { required: true, message: '请输入开标地点', trigger: 'blur' },
          { min: 1, max: 500, message: '长度在 1~500个字符', trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  methods: {
    list () {},
    // 将子组件获取到的数据传给父组件
    upLoadFile (file) {
      this.fileArry = file
    },
    // 提交
    submitForm (sectionForm, flag) {
      this.$refs[sectionForm].validate((valid) => {
        if (valid) {
          console.log(11111)
          console.log(flag)
          this.subStatus = true
        } else {
          return false
        }
      })
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
