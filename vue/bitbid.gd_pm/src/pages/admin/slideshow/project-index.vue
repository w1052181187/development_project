<template>
  <div class="center_box" id="anno-index">
    <div class="con_title">项目页面管理</div>
    <div class="con_table">
      <!--上边功能-->
      <div class="con_top_box">
        <!--发布按钮-->
        <el-button type="primary" size="medium" class="add_btn" @click="releaseBtn">发布</el-button>
        <!--发布按钮-->
      </div>
      <!--上边功能-->
      <!--列表数据-->
      <div class="con_bottom_box">
        <el-form :model="dataForm" :rules="rules" class="demo-ruleForm">
          <div class="slideshow_list_box" v-for="(sideList, index) in dataForm.sideListArray" :key="index">
            <!--删除按钮-->
            <div class="slideshow_list_deletebox">
              <div class="deletebox_btn" @click="deleteBtn(sideList)"></div>
            </div>
            <!--删除按钮-->
            <!--内容-->
            <div class="slideshow_list_conbox">
              <div class="slideshow_list_conleftbox">
                <div class="slideshow_list_conleft_topbox" v-if="sideList.fileInformation.length === 0">
                  <img src="../../../../static/img/add_side.png" alt="">
                </div>
                <upload-file @uploadSuccess="uploadSuccess"
                   @deleteSuccess="deleteSuccess"
                   :fileArrays="sideList.fileInformation"
                   isImage="1"
                   :fileObject="sideList"
                   :isData="sideList.isData"
                ></upload-file>
              </div>
              <div class="slideshow_list_conrightbox">
                <div class="slideshow_list_conrighttitle">链接地址：</div>
                <div class="slideshow_list_conrightcon">
                  <el-form-item :prop="'sideListArray.'+index+'.linkUrl'" :rules="rules.linkRules">
                    <el-input v-model="sideList.linkUrl" placeholder="请输入链接地址"></el-input>
                  </el-form-item>
                </div>
              </div>
            </div>
            <!--内容-->
          </div>
        </el-form>
      </div>
      <!--列表数据-->
    </div>
  </div>
</template>

<script>
import uploadFile from '@/components/upload/publicFileUpload'
import {smallProgram} from '@/api'
export default {
  name: '',
  components: {
    uploadFile
  },
  data () {
    return {
      rules: {
        linkRules: [
          {min: 1, max: 512, message: '长度在 1 到 512 个字符', trigger: ['blur', 'change']}
        ]
      },
      dataForm: {
        sideListArray: [
          {
            fileInformation: []
          },
          {
            fileInformation: []
          },
          {
            fileInformation: []
          },
          {
            fileInformation: []
          },
          {
            fileInformation: []
          }
        ]
      },
      isUpload: false
    }
  },
  methods: {
    // 上传文件
    uploadSuccess (contract, file, fileType) {
      contract.fileInformation.splice(0, 1, file)
    },
    deleteSuccess (contract, fileId) {
      contract.fileInformation = contract.fileInformation.filter(item => item.relativePath !== fileId)
    },
    // 列表数据
    list () {
      let url = {
        isDelete: 0,
        type: 2
      }
      smallProgram.queryBanner(url).then((res) => {
        if (res.data.miniProManagements.length !== 0) {
          this.dataForm.sideListArray = res.data.miniProManagements.map((ite) => {
            if (ite.fileInformation.fileName) {
              ite.fileInformation = [ite.fileInformation]
            } else {
              ite.fileInformation = []
            }
            return ite
          })
        }
      })
    },
    // 发布
    releaseBtn () {
      let array = []
      this.dataForm.sideListArray.map((ite) => {
        let obj = {}
        obj.type = 2
        obj.linkUrl = ite.linkUrl
        obj.fileInformation = ite.fileInformation[0] || {}
        array.push(obj)
      })
      smallProgram.saveBanner(array).then((res) => {
        this.list()
      })
    },
    // 删除
    deleteBtn (data) {
      data.linkUrl = ''
      data.fileInformation = []
    }
  },
  mounted () {
    this.list()
  }
}
</script>

<style lang="less">
  #anno-index{
    .con_top_box .add_btn{
      float: right;
    }
    .slideshow_list_box{
      width: 100%;
      border: 1px solid #ebeef5;
      padding: 30px;
      padding-bottom: 10px;
      box-sizing: border-box;
      margin-bottom: 20px;
    }
    .slideshow_list_conbox{
      overflow: hidden;
    }
    .slideshow_list_deletebox{
      width: 100%;
      height: 18px;
    }
    .deletebox_btn{
      float: right;
      width: 18px;
      height: 18px;
      background: url("../../../../static/img/shanchu-side.png");
      background-size: 18px 18px;
      cursor: pointer;
    }
    .deletebox_btn:hover{
      float: right;
      width: 18px;
      height: 18px;
      background: url("../../../../static/img/shanchu-side_hong.png");
      background-size: 18px 18px;
      cursor: pointer;
    }
    .slideshow_list_conleftbox{
      width: 20%;
      float: left;
    }
    .webuploader-pick{
      background: #009dff;
    }
    .ul-class{
      width: 240px;
      height: 85px;
      border: 1px solid #bbb;
      margin-bottom: 20px;
    }
    .webuploader-container{
      width: 100%;
      text-align: center;
    }
    .slideshow_list_conleft_topbox{
      width: 240px;
      height: 85px;
      border: 1px solid #bbb;
      background: #eee;
      margin-bottom: 20px;
      position: relative;
    }
    .slideshow_list_conleft_topbox>img{
      position: absolute;
      top:0;
      bottom: 0;
      left: 0;
      right: 0;
      margin: auto;
    }
    .slideshow_list_conrightbox{
      width: 80%;
      height: 87px;
      float: left;
      padding-left: 30px;
      box-sizing: border-box;
    }
    .slideshow_list_conrighttitle{
      height: 20px;
      line-height: 20px;
      color: #898989;
      font-size: 14px;
    }
    .slideshow_list_conrightcon{
      margin-top: 15px;
      height: 50px;
      width: 100%;
      border: 1px solid #ddd;
      box-sizing: border-box;
    }
    .slideshow_list_conrightcon .el-input{
      height: 50px;
      line-height: 50px;
    }
    .slideshow_list_conrightcon input{
      border: none;
      height: 45px;
      line-height: 45px;
    }
    .imgshow_urlbox{
      text-align: center;
    }
    .imgshow_urlbox>img{
      height: 100%;
    }
  }
</style>
