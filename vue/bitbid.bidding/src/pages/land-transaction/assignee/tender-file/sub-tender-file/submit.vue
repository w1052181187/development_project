<template>
  <div id="sub_tender_file"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false'}">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招标、投标文件</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/tender-file/sub-tender-file/index?roledsType=2&showStatus=false'}">递交投标文件</el-breadcrumb-item>
        <el-breadcrumb-item>递交</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="refundedSecurityForm" :rules="rules" ref="refundedSecurityForm" label-width="150px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：">
                  <span>{{refundedSecurityForm.title}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：">
                  <span>{{refundedSecurityForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="公告名称：">
                  <span>{{refundedSecurityForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="地块编号：">
                  <span>{{refundedSecurityForm.title}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="递交截止时间：">
                  <span>{{refundedSecurityForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="地块位置：">
                  <span>{{refundedSecurityForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--投标文件-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">投标文件</span>
          <div class="formmian">
            <div class="steps_box">
              <div class="steps_title">第一步：上传投标文件</div>
              <div class="steps_file">
                <span class="steps_file_title">
                  <span style="color: red">*</span>
                  <span>投标文件：</span>
                </span>
                <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></commonFileUpload>
              </div>
              <div class="steps_con">
                <div class="steps_file_son">
                  <span style="margin-left: 8px">上传时间：</span>
                  <span></span>
                </div>
                <div class="steps_file_son">
                  <span style="margin-left: 8px">校验码：</span>
                  <span></span>
                </div>
              </div>
            </div>
            <div class="steps_box">
              <div class="steps_title">第二步：确认上传投标文件，并签名<span style="color: #ff0000;font-size: 14px">（在截标时间前完成此步，逾期视为网上投标无效）</span></div>
              <div class="steps_file">
                <span class="steps_file_title">
                  <span style="color: red">*</span>
                  <span>签名确认：</span>
                </span>
                <el-button size="small" type="primary" class="confirm_box" @click="confirmBtn">确认并签名</el-button>
              </div>
              <div class="steps_con">
                <div class="steps_file_son">
                  <span style="margin-left: 8px">确认时间：</span>
                  <span></span>
                </div>
                <div class="steps_file_son">
                  <span>确认单号：</span>
                  <span></span>
                </div>
              </div>
            </div>
            <div class="steps_box">
              <div class="steps_title">第三步：打印回执</div>
              <div class="steps_file">
                <span class="steps_file_title" style="margin-left: 29px">
                  <span style="color: red">*</span>
                  <span>回执：</span>
                </span>
                <el-button size="small" type="primary" class="confirm_box">查看回执</el-button>
              </div>
            </div>
          </div>
        </div>
        <!--投标文件-->
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
      content: '',
      // 业务类型
      fileType: 1,
      // 文件上传保存的数组
      fileArry: [],
      oldFileName: '',
      rules: {
        title: [
          {required: true, message: '请输入标题', trigger: ['blur', 'change']},
          {min: 1, max: 500, message: '长度在 1~500个字符', trigger: ['blur', 'change']}
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
    // 确认签名
    confirmBtn () {
      if (this.fileArry.length === 0) {
        this.$message({
          type: 'warning',
          message: '第一步没有完成不能进行第二步'
        })
        return false
      }
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
  #sub_tender_file{
    .steps_box{
      width: 90%;
      margin: 0 auto;
      padding-bottom: 25px;
    }
    .steps_title{
      font-size: 15px;
      font-weight: bold;
    }
    .steps_file>div{
      display: inline-block;
      vertical-align: top;
    }
    .steps_file{
      padding: 20px 0;
    }
    .steps_file_title{
      display: inline-block;
      vertical-align: top;
      height: 30px;
      line-height: 30px;
      font-size: 14px;
      padding: 0 10px;
      box-sizing: border-box;
    }
    .steps_file>div .webuploader-container{
      width: 95px;
      height: 30px;
      line-height: 30px;
      vertical-align: top;
      font-size: 14px;
    }
    .steps_file_son{
      display: inline-block;
      width: 50%;
    }
    .steps_con{
      width: 60%;
      height: 30px;
      line-height: 30px;
      font-size: 14px;
      padding: 0 10px;
      box-sizing: border-box;
    }
    .steps_file_son{
      display: inline-block;
      width: 50%;
      float: left;
    }
    .confirm_box{
      background: #00b7ee;
      border-color: #00b7ee;
      width: 85px;
      height: 30px;
      padding: 0;
    }
    .confirm_box:focus, .confirm_box:hover{
      background-color: #00b7ee;
      border-color: #00b7ee;
    }
  }
</style>
