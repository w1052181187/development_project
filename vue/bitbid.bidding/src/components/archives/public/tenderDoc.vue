<template>
  <div id="zztbdzs_sonbigbox" class="fillType">
    <div class="zhankai_con_sontitlebox">
      <span>投标文件</span>
    </div>
    <el-button v-if="btn" type="text" @click="addModal">
      <div class="tianjia_btn">
        <span class="jia">+</span>
        <span>添加投标人</span>
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
        width="100" align="center">
      </el-table-column>
      <el-table-column
        prop="tenderingUnits"
        label="投标人">
      </el-table-column>
      <el-table-column
        prop="fileInformation.fileName"
        label="投标文件">
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        width="200px">
        <template slot-scope="scope">
          <el-button
            v-if="btn"
            @click="handleEditOpen(scope)"
            type="text"
            size="small"
            class="chakna_yichu">
            编辑
          </el-button>
          <el-button
            @click="handleSee(scope)"
            type="text"
            size="small"
            class="chakna_yichu">
            查看
          </el-button>
          <el-button
            v-if="btn"
            @click="deleteRow(scope)"
            type="text"
            size="small"
            class="chakna_yichu">
            移除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--增加弹框-->
    <el-dialog @close='cancelFun' title="投标文件" :visible.sync="appendix">
      <el-form :model="Enclosure" :rules="rules" ref="Enclosure">
        <el-form-item label="投标人" prop="tenderingUnits">
          <el-input v-model="Enclosure.tenderingUnits"></el-input>
        </el-form-item>
        <el-form-item label="" prop="name">
          <el-upload
            class="upload-demo"
            ref="upload"
            :before-upload='before'
            :action="upLoad"
            :limit="1"
            :on-exceed="handleExceed"
            :auto-upload="true"
            :file-list="fileList"
            :multiple="false"
            :on-success='success'>
            <el-button slot="trigger" size="small" type="primary">选取附件</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelFun">取 消</el-button>
        <el-button v-if="loading" type="primary" :loading="true">上传中</el-button>
        <el-button type="primary" v-else @click="addFile('Enclosure')">保 存</el-button>
      </div>
    </el-dialog>
    <!-- 修改弹窗 -->
    <el-dialog @close='cancelFun' title="投标文件" :visible.sync="editModal">
      <el-form :model="Enclosure" :rules="rules" ref="Enclosure">
        <el-form-item label="投标人" prop="tenderingUnits">
          <el-input v-model="Enclosure.tenderingUnits"></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-upload
            class="upload-demo"
            ref="upload"
            :before-upload='before'
            :action="upLoad"
            :limit="1"
            :on-exceed="handleExceed"
            :auto-upload="true"
            :file-list="fileList"
            :on-success='success'>
            <el-button slot="trigger" size="small" type="primary">选取附件</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelFun">取 消</el-button>
        <el-button v-if="loading" type="primary" :loading="true">上传中</el-button>
        <el-button type="primary" v-else @click="handleEdit('Enclosure')">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {checkResponse, loadUrl, fileSize, addtitle, downloadFile} from '../../../assets/js/common'
export default {
  data () {
    return {
      // 附件查询列表：
      EnclosuretableData: [],
      // 投标人的数据
      Enclosure: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: '',
        tenderingUnits: '',
        fileId: '',
        sectionNumber: this.projectInfo.objectId
      },
      // 判断
      rules: {
        tenderingUnits: [
          {required: true, message: '投标人不能为空', trigger: 'blur'},
          {min: 1, max: 200, message: '请输入1~200个字符', trigger: ['blur', 'change']}
        ]
      },
      upLoad: `${loadUrl}file/upload`,
      loading: false,
      appendix: false,
      save: [],
      editModal: false,
      fileList: [{}]
    }
  },
  props: ['projectInfo', 'businessType', 'btn'],
  methods: {
    before (file) {
      return fileSize(this, file, true)
    },
    // 文件上传成功回调函数：
    success (res, file, fileList) {
      this.loading = false
      res.fileInformation.businessType = this.businessType
      res.fileInformation.relatedCode = this.projectInfo.code
      delete res.fileInformation.storeName
      delete res.fileInformation.lastUpdateDate
      delete res.fileInformation.createDate
      this.save.push(res.fileInformation)
      this.fileList = fileList
    },
    addModal () {
      this.appendix = true
      this.Enclosure = {
        sectionNumber: this.projectInfo.objectId
      }
      this.fileList = []
    },
    // 文件保存：
    addFile (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid && this.save) {
          this.axios.post('file-information', this.save).then((res) => {
            this.save.length = 0
            if (res.data.fileList[0] !== undefined) {
              this.Enclosure.fileId = res.data.fileList[0].objectId
            }
            this.axios.post('bid-documents', this.Enclosure).then((res) => {
              checkResponse(this, res, () => {
                this.listShow()
                this.cancelFun()
              })
            })
          })
        } else {
          return false
        }
      })
    },
    handleExceed (files, fileList) {
      this.$message.warning(`该操作只限制选择一个文件!`)
    },
    cancelFun () {
      this.save = []
      this.appendix = false
      this.editModal = false
      this.$refs.Enclosure.resetFields()
      this.fileList = [{}]
    },
    // 附件列表查询：
    listShow () {
      this.axios.get(`bid-documents?sectionNumber=${this.projectInfo.objectId}&_t=${new Date().getTime()}`).then((res) => {
        this.EnclosuretableData = res.data.bidDocumentList
        this.loading = false
        this.EnclosuretableData.map((item) => {
          if (item.fileInformation === null) {
            var obj = {fileName: ''}
            item.fileInformation = obj
          }
        })
        addtitle(this)
      })
    },
    handleSee (scope) {
      if (scope.row.fileInformation.relativePath) {
        var name = scope.row.fileInformation.fileName
        var path = scope.row.fileInformation.relativePath
        downloadFile(name, path)
      } else {
        this.$message.error(' 请先上传文件')
      }
    },
    handleEditOpen (scope) {
      this.editModal = true
      if (scope.row.fileInformation.fileName) {
        this.fileList = [{}]
        this.fileList[0].name = scope.row.fileInformation.fileName
      } else {
        this.fileList = []
      }
      this.axios.get(`bid-documents?sectionNumber=${this.projectInfo.objectId}&_t=${new Date().getTime()}`).then((res) => {
        var allLIst = res.data.bidDocumentList
        allLIst.map((item) => {
          if (item.objectId === scope.row.objectId) {
            this.Enclosure = item
          }
        })
      })
    },
    handleEdit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid && this.save) {
          this.axios.post('file-information', this.save).then((res) => {
            this.save.length = 0
            if (res.data.fileList[0] !== undefined) {
              this.Enclosure.fileId = res.data.fileList[0].objectId
            }
            this.axios.put(`bid-documents`, this.Enclosure).then((res) => {
              checkResponse(this, res, () => {
                this.listShow()
                this.cancelFun()
                this.fileList = [{}]
              })
            })
          })
        } else {
          return false
        }
      })
    },
    // 删除
    deleteRow (scope) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (scope.row.fileId) {
          this.axios.delete(`file-information/${scope.row.fileId}`).then((res) => {
            this.listShow()
          })
        }
        this.axios.delete(`bid-documents/${scope.row.objectId}`).then((res) => {
          checkResponse(this, res, () => {
            this.listShow()
          })
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  },
  mounted () {
    this.listShow()
  }
}
</script>
<style lang="less">
#zztbdzs_sonbigbox{
 .el-form-item__content {
    line-height: 40px;
    position: relative;
    font-size: 14px;
    text-align: center;
  }
  .upload-demo {
    min-height: 38px;
    border: 1px solid #eee;
    padding: 4px 10px;
    border-radius:5px;
  }
  .el-upload {
    display: inline-block;
    text-align: center;
    cursor: pointer;
    outline: 0;
    float: right;
  }
  .el-upload-list {
    width: 80%;
    min-height: 38px;
    margin: 0;
    padding: 0;
    list-style: none;
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
    -webkit-transition: all .5s cubic-bezier(.55,0,.1,1);
    transition: all .5s cubic-bezier(.55,0,.1,1);
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
  .el-table .cell {
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    word-break: break-all;
    height: 23px;
    line-height: 23px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  .el-button--primary {
    background-color: #409eff!important;
    border-color: #409eff!important;
  }
  .upload-demo {
    text-align: left!important;
  }
}
</style>
