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
              </el-table>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
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
