<template>
  <div class="fillType" id="fillType">
    <div class="zhankai_con_sontitlebox">
      <span>{{fileName}}</span>
    </div>
    <el-button v-if='btn' type="text" @click="appendix = true">
      <div class="tianjia_btn">
        <span class="jia">+</span>
        <span>添加附件</span>
      </div>
    </el-button>
    <el-table
      :data="EnclosuretableData"
      style="width: 100%"
      border
      max-height="250">
      <el-table-column
        type="index"
        :index="1"
        label="序号"
        width="80">
      </el-table-column>
      <el-table-column
        prop="fileName"
        label="附件" >
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="200"
        align="center">
        <template slot-scope="scope">
          <el-button
            @click="handleEdit(scope.row.fileName,scope.row.relativePath)"
            type="text"
            size="small"
            class="chakna_yichu">
            查看
          </el-button>
          <el-button
            v-show="btn"
            @click="deleteRow(scope)"
            type="text"
            size="small"
            class="chakna_yichu">
            移除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--添加弹框-->
    <el-dialog @close='cancle' :title="fileName" :visible.sync="appendix">
      <el-form :model="Enclosure">
        <el-form-item label="" prop="name">
          <el-upload
            class="upload-demo"
            ref="upload"
            :before-upload='before'
            :action="upLoad"
            :on-remove="handleRemove"
            :auto-upload="true"
            :on-success='success'>
            <el-button slot="trigger" size="small" type="primary">选取附件</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelFun">取 消</el-button>
        <el-button v-if="loading" type="primary" :loading="true">上传中</el-button>
        <el-button type="primary" v-else @click="addFile">保 存</el-button>
      </div>
    </el-dialog>
    <!--添加弹框-->
  </div>
</template>
<script>
import {checkResponse, loadUrl, fileSize, downloadFile} from '../../../assets/js/common'
export default {
  data () {
    return {
      // 附件查询列表：
      EnclosuretableData: [],
      Enclosure: {},
      loading: false,
      appendix: false,
      save: [],
      upLoad: `${loadUrl}file/upload`
    }
  },
  props: ['projectInfo', 'businessType', 'btn', 'fileName'],
  methods: {
    before (file) {
      return fileSize(this, file)
    },
    // 移除
    handleRemove (file, fileList) {
      this.save.map((item, index) => {
        if (item.fileName == file.name) {
          this.save.splice(index, 1)
        }
      })
    },
    // 文件上传成功回调函数：
    success (res) {
      this.loading = false
      res.fileInformation.businessType = this.businessType
      res.fileInformation.relatedCode = this.projectInfo.code
      delete res.fileInformation.storeName
      delete res.fileInformation.lastUpdateDate
      delete res.fileInformation.createDate
      this.save.push(res.fileInformation)
    },
    // 文件保存：
    addFile () {
      this.axios.post('file-information', this.save).then((res) => {
        checkResponse(this, res, () => {
          this.save = []
          this.listShow()
          this.cancelFun()
        })
      })
    },
    // 附件列表查询：
    listShow () {
      this.save = []
      this.EnclosuretableData = []
      this.axios.get(`project-infos/${this.projectInfo.objectId}?_t=${new Date().getTime()}`).then((res) => {
        if (res.data.resCode == '0000') {
          this.projectInfo.fileInformations = res.data.projectInformation.fileInformations
          this.projectInfo.fileInformations.map((item) => {
            item.businessType == this.businessType ? this.EnclosuretableData.push(item) : this.EnclosuretableData
          })
        }
      })
    },
    handleEdit (name, path) {
      downloadFile(name, path)
    },
    cancelFun () {
      this.save = []
      this.appendix = false
      this.$refs.upload.clearFiles()
    },
    cancle () {
      this.cancelFun()
    },
    // 删除
    deleteRow (scope) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.axios.delete(`file-information/${scope.row.objectId}`).then((res) => {
          checkResponse(this, res, () => {
            this.listShow()
          })
        }, () => {
        })
      }).catch(() => {
        return false
      })
    }
  },
  mounted () {
    this.listShow()
  }
}
</script>
<style lang='less'>
#fillType{
  .el-form-item__content {
    line-height: 40px;
    position: relative;
    font-size: 14px;
  }
  .upload-demo {
    min-height: 38px;
    border: 1px solid #eee;
    padding: 4px 10px;
    border-radius: 5px;
    text-align: right
  }
  .el-upload {
    display: inline-block;
    text-align: center;
    cursor: pointer;
    outline: 0;
    float: right;
  }
  .el-upload-list__item-name {
    color: #606266;
    display: block;
    margin-right: 40px;
    overflow: hidden;
    padding-left: 4px;
    text-overflow: ellipsis;
    -webkit-transition: color .3s;
    transition: color .3s;
    white-space: nowrap;
    height: 38px;
    line-height: 38px;
    text-align: left;
  }
  .el-upload-list__item-status-label {
    position: absolute;
    right: 5px;
    top: 9px;
    line-height: inherit;
    display: none;
  }
  .el-upload-list__item {
    -webkit-transition: all .5s cubic-bezier(.55, 0, .1, 1);
    transition: all .5s cubic-bezier(.55, 0, .1, 1);
    font-size: 14px;
    color: #606266;
    line-height: 1.8;
    margin-top: 0px;
    position: relative;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    border-radius: 4px;
    width: 100%;
  }
  .el-upload-list__item .el-icon-close {
    display: block;
    position: absolute;
    top: 14px;
    right: 5px;
    cursor: pointer;
    opacity: .75;
    color: #606266;
  }
  .el-upload-list {
    width: 85%;
    margin: 0;
    padding: 0;
    list-style: none;
  }
}
.upload-demo {
  min-height: 38px;
  border: 1px solid #eee;
  padding: 4px 10px;
  border-radius: 5px;
  text-align: right
}
</style>
