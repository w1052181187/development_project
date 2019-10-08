<template>
  <div id="application" class="fillType zztbdzs_sonbigbox">
    <div class="zhankai_con_sontitlebox">
      <span>资格预审申请文件</span>
    </div>
    <el-button v-if='btn' type="text" @click="addModal">
      <div class="tianjia_btn">
        <span class="jia">+</span>
        <span>投标单位</span>
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
        prop="tendererUnit"
        label="投标单位">
      </el-table-column>
      <el-table-column
        prop="fileInformation.fileName"
        label="资格预审申请文件">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="200px"
        align="center">
        <template slot-scope="scope">
          <el-button v-if="btn"
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
    <el-dialog @close='cancelFun' title="资格预审申请文件" :visible.sync="appendix">
      <el-form :model="Enclosure" :rules="rules" ref="Enclosure">
        <el-form-item label="投标单位" prop="tendererUnit">
          <el-input v-model="Enclosure.tendererUnit"></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-upload
            class="upload-demo"
            ref="upload"
            :before-upload='before'
            :action="upLoad"
            :limit="1"
            :on-remove="handleRemove"
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
        <el-button type="primary" v-else @click="addFile('Enclosure')">保 存</el-button>
      </div>
    </el-dialog>
    <!-- 修改弹窗 -->
    <el-dialog @close='cancelFun' title="资格预审申请文件" :visible.sync="editModal">
      <el-form :model="Enclosure" :rules="rules" ref="Enclosure">
        <el-form-item label="投标单位" prop="tendererUnit">
          <el-input v-model="Enclosure.tendererUnit"></el-input>
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
        <el-button type="primary" v-else @click="handleEdit()">保 存</el-button>
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
        sectionNumber: this.projectInfo.objectId
      },
      // 判断
      rules: {
        tendererUnit: [
          {required: true, message: '投标单位不能为空', trigger: 'blur'},
          {min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur'}
        ]
      },
      appendix: false,
      save: [],
      loading: true,
      upLoad: `${loadUrl}file/upload`,
      fileList: [{}],
      editModal: false
    }
  },
  props: ['projectInfo', 'businessType', 'btn'],
  methods: {
  // *********************************************************************************************************附件***************************************************************
    before (file) {
      return fileSize(this, file, true)
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
    success (res, file, fileList) {
      this.loading = false
      this.save.length = 0
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
        if (valid) {
          this.axios.post('file-information', this.save).then((res) => {
            this.save.length = 0
            if (res.data.fileList[0] !== undefined) {
              this.Enclosure.fileId = res.data.fileList[0].objectId
            }
            this.axios.post('prequali-file-apply', this.Enclosure).then((res) => {
              checkResponse(this, res, () => {
                this.listShow()
                this.cancelFun()
              })
            }, () => {
              this.$message.error('Not found')
            })
          })
        } else {
          return false
        }
      })
    },
    // 附件列表查询：
    listShow () {
      this.axios.get(`prequali-file-apply?sectionNumber=${this.projectInfo.objectId}&_t=${new Date().getTime()}`).then((res) => {
        this.EnclosuretableData = res.data.preliFileApplyList
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
    // 编辑弹窗打开
    handleEditOpen (scope) {
      this.editModal = true
      if (scope.row.fileInformation.fileName) {
        this.fileList = [{}]
        this.fileList[0].name = scope.row.fileInformation.fileName
      } else if (scope.row.fileInformation.fileName == '') {
        this.fileList = []
      }
      this.axios.get(`prequali-file-apply?sectionNumber=${this.projectInfo.objectId}`).then((res) => {
        var allLIst = res.data.preliFileApplyList
        allLIst.map((item) => {
          if (item.objectId === scope.row.objectId) {
            this.Enclosure = item
          }
        })
      })
    },
    // 编辑弹窗保存
    handleEdit () {
      this.axios.post('file-information', this.save).then((res) => {
        this.save.length = 0
        if (res.data.fileList[0] !== undefined) {
          this.Enclosure.fileId = res.data.fileList[0].objectId
        }
        this.axios.put(`prequali-file-apply`, this.Enclosure).then((res) => {
          checkResponse(this, res, () => {
            this.listShow()
            this.cancelFun()
            this.fileList = [{}]
          })
        })
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
        this.axios.delete(`prequali-file-apply/${scope.row.objectId}`).then((res) => {
          checkResponse(this, res, () => {
            this.listShow()
          })
        })
      }).catch(() => {
        return false
      })
    },
    handleExceed (files, fileList) {
      this.$message.warning(`该操作只限制选择一个文件`)
    },
    // 取消
    cancelFun () {
      this.save = []
      this.appendix = false
      this.editModal = false
      this.$refs.Enclosure.resetFields()
      this.fileList = [{}]
    }
  },
  mounted () {
    this.listShow()
  }
}
</script>
<style lang='less'>
#application{
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
  .el-table th>.cell {
    position: relative;
    word-wrap: normal;
    text-overflow: ellipsis;
    vertical-align: middle;
    width: 100%;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
  }
  .el-upload-list__item.is-success .el-upload-list__item-status-label {
    display: none;
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
