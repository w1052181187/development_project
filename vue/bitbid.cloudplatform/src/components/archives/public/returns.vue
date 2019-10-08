<template>
  <div class="fillType" id="zgysjgbg">
    <!--资格预审文件附件-->
    <div class="zztbdzs_sonbigbox">
      <div class="zhankai_con_sontitlebox">
        <span>资格预审结果报告</span>
      </div>
    <el-button v-if='btn' type="text" @click="otherAppendix = true">
        <div class="tianjia_btn">
          <span class="jia">+</span>
          <span>添加报告</span>
        </div>
      </el-button>
      <el-table
        :data="tableType"
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
          label="报告名称">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          align="center"
          width="200px">
          <template slot-scope="scope">
            <el-button
              @click="handleEdit(scope.row.fileName,scope.row.relativePath)"
              type="text"
              size="small"
              class="chakna_yichu">
              查看
            </el-button>
            <el-button
              v-if="btn"
              @click="deleteData(scope)"
              type="text"
              size="small"
              class="chakna_yichu">
              移除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--添加弹框-->
      <el-dialog @close='cancle' title="资格预审结果报告" :visible.sync="otherAppendix">
        <el-form :model="Enclosure">
          <el-form-item label="" prop="name">
            <el-upload
              class="upload-demo"
              ref="upload"
              :before-upload='before'
              :action="upLoad"
              :auto-upload="true"
              :on-remove="handleRemove"
              :on-success='success'>
              <el-button slot="trigger" size="small" type="primary">选取附件</el-button>
            </el-upload>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelotherFun">取 消</el-button>
          <el-button v-if="loading" type="primary" :loading="true">上传中</el-button>
          <el-button type="primary" v-else @click="addFileOther">保 存</el-button>
        </div>
      </el-dialog>
      <!--添加弹框-->
    </div>
    <!--资格预审文件告附件-->

    <!--其他附件-->
    <div class="zztbdzs_sonbigbox">
      <div class="zhankai_con_sontitlebox">
        <span>投标人澄清</span>
      </div>
    <el-button v-if='btn' type="text" @click="appendix = true">
        <div class="tianjia_btn">
          <span class="jia">+</span>
          <span>添加附件</span>
        </div>
      </el-button>
      <el-table
        :data="outherType"
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
          label="附件"
          width="870">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              @click="handleEdit(scope.row.fileName,scope.row.relativePath)"
              type="text"
              size="small"
              class="chakna_yichu">
              查看
            </el-button>
            <el-button
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
      <el-dialog @close='cancle' title="投标人澄清" :visible.sync="appendix">
        <el-form :model="Enclosure">
          <el-form-item label="" prop="name">
            <el-upload
              class="upload-demo"
              ref="upload"
              :before-upload='before'
              :action="upLoad"
              :on-remove="handleOtherRemove"
              :auto-upload="true"
              :on-success='outherSuccess'>
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
    <!--其他附件-->
  </div>
</template>
<script>
// 引入自己需要样式和js
import {checkResponse, loadUrl, fileSize, downloadFile} from '../../../assets/js/common'
export default {
  data () {
    return {
      tableData4: [
        {
          xuhao_num: '1',
          lianxiren: '综合平台测试项目项目文件'
        }
      ],
      tableData5: [
        {
          xuhao_num: '1',
          fujiantitle: '山西比比网络有限公司目'
        }
      ],
      // 日期选择
      pickerOptions1: {
        disabledDate (time) {
          return time.getTime() > Date.now()
        },
        shortcuts: [{
          text: '今天',
          onClick (picker) {
            picker.$emit('pick', new Date())
          }
        }, {
          text: '昨天',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24)
            picker.$emit('pick', date)
          }
        }, {
          text: '一周前',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', date)
          }
        }]
      },
      value: '',
      formLabelWidth: '120px',
      // 公告性质
      options_ggxz: [{
        value: '选项1',
        label: '正常公告'
      }, {
        value: '选项2',
        label: '更正公告'
      }
      ],
      options: [{
        value: '选项1',
        label: '正常公告'
      }, {
        value: '选项2',
        label: '更正公告'
      }],
      // 附件查询列表：
      EnclosuretableData: [],
      Enclosure: {},
      appendix: false,
      otherAppendix: false,
      save: [],
      outhersave: [],
      tableType: [],
      outherType: [],
      loading: false,
      upLoad: `${loadUrl}file/upload`
    }
  },
  props: ['projectInfo', 'businessType', 'btn'],
  methods: {
    // 附件上传outherSuccess
    before (file) {
      return fileSize(this, file)
    },
    // 移除
    handleRemove (file, fileList) {
      this.remove(this.save, file)
    },
    handleOtherRemove (file, fileList) {
      this.remove(this.outhersave, file)
    },
    remove (fileList, file) {
      fileList.map((item, index) => {
        if (item.fileName == file.name) {
          fileList.splice(index, 1)
        }
      })
    },
    success (res) {
      this.loading = false
      res.fileInformation.businessType = this.businessType + '-1'
      res.fileInformation.relatedCode = this.projectInfo.code
      delete res.fileInformation.storeName
      delete res.fileInformation.lastUpdateDate
      delete res.fileInformation.createDate
      this.save.push(res.fileInformation)
    },
    outherSuccess (res) {
      this.loading = false
      res.fileInformation.businessType = this.businessType + '-2'
      res.fileInformation.relatedCode = this.projectInfo.code
      delete res.fileInformation.storeName
      delete res.fileInformation.lastUpdateDate
      delete res.fileInformation.createDate
      this.outhersave.push(res.fileInformation)
    },
    // 文件保存：
    addFileOther () {
      this.axios.post('file-information', this.save).then((res) => {
        checkResponse(this, res, () => {
          this.cancelotherFun()
          this.save = []
          this.listShow()
        })
      })
    },
    addFile () {
      this.axios.post('file-information', this.outhersave).then((res) => {
        checkResponse(this, res, () => {
          this.cancelFun()
          this.outhersave = []
          this.listShow()
        })
      })
    },
    // 附件列表查询：
    listShow () {
      this.tableType = []
      this.outherType = []
      let allType = []
      this.axios.get(`project-infos/${this.projectInfo.objectId}?_t=${new Date().getTime()}`).then((res) => {
        if (res.data.resCode == '0000') {
          this.projectInfo.fileInformations = res.data.projectInformation.fileInformations
          this.projectInfo.fileInformations.map((item) => {
            let type = item.businessType.split('-')[0]
            type == this.businessType ? allType.push(item) : this.allType
          })
          allType.map((item) => {
            let type = item.businessType.split('-')[1]
            type == '1' ? this.tableType.push(item) : this.outherType.push(item)
          })
        }
      })
    },
    cancelFun () {
      this.appendix = false
      this.$refs.upload.clearFiles()
    },
    cancelotherFun () {
      this.otherAppendix = false
      this.$refs.upload.clearFiles()
    },
    cancle () {
      this.cancelFun()
      this.cancelotherFun()
    },
    handleEdit (name, path) {
      downloadFile(name, path)
    },
    // 删除资格预审结果报告
    deleteData (scope) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.save = []
        this.axios.delete(`file-information/${scope.row.objectId}`).then((res) => {
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
    },
    // 删除投标人澄清
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
#zgysjgbg{
  .el-upload-list__item.is-success .el-upload-list__item-status-label {
    display: none;
  }
  .el-upload-list__item .el-icon-close {
    display: block;
    position: absolute;
    top: 5px;
    right: 5px;
    cursor: pointer;
    opacity: .75;
    color: #606266;
  }
}
</style>
