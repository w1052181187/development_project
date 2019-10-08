<template>
  <div class="cloudcontent" id="cloud_webhome">
    <div class="main">
      <p class="pic_tip">注：您可以上传新图片替换当前图片</p>
      <el-form :model="updateForm" ref="updateForm" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="24">
            <el-form-item label="企业LOGO：">
              <div class="thumbnail">
                <img src="../../../../../static/images/webmanage/logo.png"/>
              </div>
              <upload-file @uploadSuccess="uploadOtherSuccess" @deleteSuccess="deleteSuccess" :fileArrays="updateForm.fileInformations" fileType="9" isImage="1"></upload-file>
              <p>（图片尺寸为500*97 px）</p>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="首页图片：">
              <div class="thumbnail">
                <img src="../../../../../static/images/webmanage/logo.png"/>
              </div>
              <upload-file @uploadSuccess="uploadOtherSuccess" @deleteSuccess="deleteSuccess" :fileArrays="updateForm.fileInformations" fileType="9" isImage="1"></upload-file>
              <p>（图片尺寸为1920*450 px）</p>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="企业二维码：">
              <div class="thumbnail">
                <img src="../../../../../static/images/webmanage/logo.png"/>
              </div>
              <upload-file @uploadSuccess="uploadOtherSuccess" @deleteSuccess="deleteSuccess" :fileArrays="updateForm.fileInformations" fileType="9" isImage="1"></upload-file>
              <p>（图片尺寸为109*109 px）</p>
             </el-form-item>
          </el-col>
        </el-row>
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitBtn('updateForm')">确认</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import uploadFile from '@/components/upload/publicFileUpload'
export default {
  components: {
    uploadFile
  },
  data () {
    return {
      updateForm: {
      }
    }
  },
  methods: {
  }
}
</script>
<style lang="less">
  #cloud_webhome {
    .pic_tip{
      width: 100%;
      color: #ff0000;
      font-weight: bold;
      margin-bottom: 20px;
    }
    .thumbnail{
      width: 242px;
      height: 180px;
      line-height: 180px;
      border: 1px solid #d3d3d3;
      margin-bottom: 10px;
      text-align: center;
    }
    .thumbnail img{
      max-width: 100%;
      max-height: 100%;
      vertical-align: middle;
    }
  }
</style>
