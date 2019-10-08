<template>
  <div class="cloudcontent" id="cloud_webconpany">
    <div class="main">
      <el-form>
        <el-row>
          <el-col :span="24">
            <el-form-item label="类型：">
              <span>荣誉资质</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="荣誉资质：">
              <el-button class="addbutton" @click="dialogVisible = true">
                <span> 新增 </span>
              </el-button>
              <el-table
                :data="tableData"
                border
                style="width: 100%" header-cell-class-name="tableheader">
                <el-table-column
                  type="index"
                  label="序号"
                  width="60"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="资质名称"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="pic"
                  label="资质图片"
                  width="200"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  label="操作" align="center" width="200">
                  <template slot-scope="scope">
                    <el-button type="text" size="small">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-dialog
        title="新增资质荣誉"
        :visible.sync="dialogVisible"
        width="40%"
        center>
        <el-form :model="updateForm" :rules='rules' ref="updateForm" :validate-on-rule-change="true">
          <el-row>
            <el-col :span="24">
              <el-form-item label="资质名称：">
                <el-input v-model="updateForm.agencyContractCode"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="资质图片：">
                <upload-file @uploadSuccess="uploadOtherSuccess" @deleteSuccess="deleteSuccess" :fileArrays="updateForm.fileInformations" fileType="9" isImage="1"></upload-file>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item class="submit-radio">
            <el-button type="primary" @click="submitBtn('updateForm')">确定</el-button>
            <el-button class="cancal" @click="dialogVisible = false">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
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
        fileInformations: {},
        agencyContractCode: ''
      },
      rules: {},
      dialogVisible: false,
      tableData: [
        {
          name: '百强企业荣誉证书',
          pic: '图一.png'
        },
        {
          name: '百强企业荣誉证书',
          pic: '图一.png'
        }
      ]
    }
  },
  methods: {
    // 上传附件
    uploadOtherSuccess (file) {
      this.updateForm.fileInformations.push(file)
    },
    deleteSuccess (fileId) {
      this.updateForm.fileInformations = this.updateForm.fileInformations.filter(item => item.relativePath !== fileId)
    }
  }
}
</script>
<style lang="less">
  #cloud_webconpany {
    .addbutton{
      float: left;
      background: #6582f8;
      border-color: #6582f8;
      margin-bottom: 20px;
    }
    .addbutton:hover{
      background: #6582f8;
      border-color: #6582f8;
    }
    .addbutton span{
      color: #ffffff;
    }
  }
</style>
