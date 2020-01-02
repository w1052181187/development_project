<template>
  <div class="enterInfo" id="productInfo">
    <p class="tips" v-if="isEdit">
      <span>编辑状态下，离开此标签未保存的信息将不会被保存</span>
      <el-button class="handleBtn" type="primary" @click="save('dataForm')">保存</el-button>
    </p>
    <p class="tips" v-else>
      <span>完善企业产品信息，增加产品曝光度及更多销售机会</span>
      <el-button class="handleBtn" type="primary"  @click="isEdit = true">编辑</el-button>
    </p>
    <el-form :model="dataForm" :rules="rules" ref="dataForm" label-width="80px" :validate-on-rule-change="true">
      <div class="mainContent">
        <h3 class="title">产品信息</h3>
        <ul class="proList">
          <li v-if="dataForm.productInformationList && dataForm.productInformationList.length !== 0"
              v-for="(item, index) in dataForm.productInformationList" :key="item.index">
            <div class="img_box"><img :src="fileReviewUrl + fileInformations[index].relativePath"/></div>
            <div v-if="isEdit">
              <el-form-item label="产品名称" :prop="'productInformationList.'+index+'.productName'" :rules="rules.productName" class="itemRule">
                <el-input v-model="item.productName"></el-input>
              </el-form-item>
              <el-form-item label="规格型号" :prop="'productInformationList.'+index+'.modelSpecifications'" :rules="rules.modelSpecifications" class="itemRule">
                <el-input v-model="item.modelSpecifications"></el-input>
              </el-form-item>
              <span class="del" @click="delPro(index)"></span>
            </div>
            <div class="title_box" v-else>
              <p><label>产品名称：</label>{{item.productName}}</p>
              <p><label>规格型号：</label>{{item.modelSpecifications}}</p>
            </div>
          </li>
          <li class="addData" v-if="isEdit">
            <p>产品图</p>
            <i>建议分辨率140*140</i>
            <i>支持jpg、jpeg格式</i>
            <uploadFile @uploadSuccess="uploadSuccess" @deleteSuccess="deleteSuccess" :fileType=dataForm.productInformationList?dataForm.productInformationList.length:0 :fileArrays="fileInformations" isImage="1"></uploadFile>
          </li>
        </ul>
        <div class="nodata" v-if="!dataForm.productInformationList || dataForm.productInformationList.length === 0">
          <img src="../../../static/images/enterprise/noproduct.png"/>
          <p>暂无产品信息</p>
        </div>
      </div>
    </el-form>
  </div>
</template>

<script>
import uploadFile from '@/components/upload/publicFileUpload'
import {fileReviewUrl} from '@/assets/js/common'
import {attachinfo} from '@/api/system'
export default {
  name: 'productInfo',
  components: {
    uploadFile
  },
  data () {
    return {
      // 判断编辑、查看状态 ；默认应为查看状态
      isEdit: false,
      fileInformations: [],
      dataForm: {
        enterpriseId: this.$store.getters.authUser.enterpriseId
      },
      fileReviewUrl: fileReviewUrl, // 图片展示路径
      rules: {
        productName: [
          {message: '请输入产品名称', required: true, trigger: 'blur'}
        ],
        modelSpecifications: [
          {message: '请输入规格型号', required: true, trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    delPro (index) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        this.dataForm.productInformationList.splice(index, 1)
        this.fileInformations.splice(index, 1)
      }).catch(() => {
        return false
      })
    },
    // 上传文件
    uploadSuccess (file, fileOrder) {
      if (!this.dataForm.productInformationList) {
        this.dataForm.productInformationList = []
      }
      if (this.fileInformations[fileOrder]) {
        this.fileInformations.splice(fileOrder, 1, file)
        this.dataForm.productInformationList[fileOrder].fileInformation = file
      } else {
        this.dataForm.productInformationList.push({
          productName: '',
          modelSpecifications: '',
          fileInformation: file
        })
        this.fileInformations.push(file)
      }
    },
    // 删除文件
    deleteSuccess (fileId, fileType) {
      this.fileInformations = this.fileInformations.filter(item => item.relativePath !== fileId)
    },
    // 初始化数据
    detail () {
      let url = {
        enterpriseId: this.$store.getters.authUser.enterpriseId
      }
      attachinfo.queryOne(url).then((res) => {
        this.$set(this.dataForm, 'objectId', res.data.enterpriseExpand.objectId)
        this.$set(this.dataForm, 'productInformationList', res.data.enterpriseExpand.productInformationList)
        // 提取文件信息
        if (this.dataForm.productInformationList) {
          this.dataForm.productInformationList.forEach(item => {
            let file = item.fileInformation === null ? {} : item.fileInformation
            this.fileInformations.push(file)
          })
        }
      })
    },
    // 保存数据
    save (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          attachinfo.update(this.dataForm).then((res) => {
            if (res.data.resCode === '0000') {
              this.isEdit = false
              this.$store.commit('SET_ENTERPRISE_SAVE', new Date().getTime())
            }
          })
        }
      })
    }
  },
  mounted () {
    this.detail()
  }
}
</script>

<style lang="less">
  #productInfo{
    .proList{
      overflow: hidden;
      margin: 10px auto;
      padding-left: 30px;
    }
    .proList li{
      width: 210px;
      height: 282px;
      border: 1px solid #e7e7e7;
      float: left;
      margin: 10px 6px;
      position: relative;
    }
    .proList li .img_box{
      width: 200px;
      height: 160px;
      line-height: 160px;
      margin: 10px auto;
      text-align: center;
    }
    .proList li .img_box img{
      max-width: 100%;
      max-height: 100%;
      vertical-align: middle;
    }
    .proList li p{
      width: 100%;
      padding: 0 4%;
      margin: 5px 0;
    }
    .proList li p label{
      width: 70px;
    }
    .proList li p .el-input{
      width: 120px;
      padding: 0 5px;
    }
    .proList li span.del{
      width: 16px;
      height: 16px;
      display: inline-block;
      background: url("../../../static/images/enterprise/close.png") no-repeat;
      position: absolute;
      right: -8px;
      top: -8px;
      cursor: pointer;
    }
    .proList li .el-form-item{
      line-height: 28px;
      margin-bottom: 14px;
      padding: 0 10px;
    }
    .proList li .el-form-item .el-input__inner{
      line-height: 28px;
      height: 28px;
    }
    .proList li.addData{
      cursor: pointer;
      background: #f5f7fa url("../../../static/images/enterprise/plus.png") top 30px center no-repeat;
    }
    .proList li.addData p{
      line-height: 28px;
      font-weight: bold;
      text-align: center;
      margin: 100px 0 0 0;
      padding: 0;
    }
    .proList li.addData i{
      width: 100%;
      font-style: normal;
      font-size: 12px;
      color: #999999;
      text-align: center;
      display: inline-block;
    }
    .proList li.addData .webuploader-container{
      margin: 30px 0 0 50px;
    }
    .proList li.addData span{
      float: left;
      margin: 100px 10px 0 0;
      color: #999999;
    }
    .proList li.addData .webuploader-pick{
      width: 72px;
      height: 32px;
      line-height: 32px;
      padding-left: 20px;
    }
    .title_box{
      background: #eff2f8;
      padding: 4px 0;
      text-align: center;
      color: #333333;
    }
    .title_box p{
      font-weight: bold;
    }
    /*.itemRule .el-form-item__content{*/
      /*margin-left: 80px!important;*/
    /*}*/
  }
</style>
