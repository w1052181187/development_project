<template>
  <div id="zztprequalificationDoc" class="fillType">
    <!--资格预审文件-->
    <div class="zztbdzs_sonbigbox" style="overflow:hidden">
      <div class="zhankai_con_sontitlebox">
        <span>资格预审文件</span>
      </div>
      <el-form :model="form" :disabled='!btn' :rules="rules" ref="form" style="margin-top: 20px">
        <el-row>
          <el-button v-if='btn' type="text" @click="submitbtnFun('form')">
            <div class="tianjia_btn">
              <span>保存信息</span>
            </div>
          </el-button>
        </el-row>
        <el-col :span="12">
          <el-form-item label="标书售价（元）：" prop="tenderPrice" :label-width="formLabelWidth">
            <el-input v-model="form.tenderPrice"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="投标保证金金额：" prop="tenderBondPrice" :label-width="formLabelWidth">
              <el-input v-model="form.tenderBondPrice"></el-input>
            </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="发售时间：" :label-width="formLabelWidth">
            <el-date-picker
              v-model="dataRange"
              type="daterange"
              :picker-options="pickerOptions"
              value-format="yyyy-MM-dd HH:mm:ss"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              align="right">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <div class="grid-content bg-purple">
            <el-form-item label="开标时间：" :label-width="formLabelWidth">
              <el-date-picker
                v-model="form.bidOpenDate"
                type="datetime"
                :picker-options="pickerOptions"
                value-format="yyyy-MM-dd HH:mm:ss"
                start-placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </div>
        </el-col>
      </el-form>
    </div>
    <!--资格预审文件-->

    <!--资格预审文件附件-->
    <div class="zztbdzs_sonbigbox">
      <div class="zhankai_con_sontitlebox">
        <span>资格预审文件</span>
      </div>
      <el-button v-if='btn' type="text" @click="appendix = true">
        <div class="tianjia_btn">
          <span class="jia">+</span>
          <span>添加附件</span>
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
          label="附件">
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
            <el-button v-if="btn"
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
      <el-dialog @close='cancle' title="资格预审文件" :visible.sync="appendix">
        <el-form :model="Enclosure">
          <el-form-item label="" prop="name" class="zgysfile">
            <el-upload
              class="upload-demo"
              ref="upload"
              :on-remove="handleRemove"
              :before-upload='before'
              :action="upLoad"
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
    <!--资格预审文件告附件-->

    <!--其他附件-->
    <div class="zztbdzs_sonbigbox">
      <div class="zhankai_con_sontitlebox">
        <span>其他附件</span>
      </div>
      <el-button v-if='btn' type="text" @click="otherAppendix = true">
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
              v-if="btn == true ? true : false"
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
      <el-dialog @close='cancle' title="其他附件" :visible.sync="otherAppendix">
        <el-form :model="Enclosure">
          <el-form-item label="" prop="name" class="zgysfile">
            <el-upload
              class="upload-demo"
              ref="upload"
              :before-upload='before'
              :action="upLoad"
              :auto-upload="true"
              :on-remove="handleOtherRemove"
              :on-success='outherSuccess'>
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
    <!--其他附件-->
  </div>
</template>
<script>
import {checkResponse, loadUrl, fileSize, downloadFile} from '../../../assets/js/common'
import { Numbertype } from '../../../assets/js/validate'
export default {
  data () {
    // 数字
    var Numbertypes = (rule, value, callback) => {
      if (value === '') {
        callback()
      } else if (!Numbertype(value)) {
        callback(new Error('请输入正确的格式'))
      } else {
        callback()
      }
    }
    return {
      title: '资格预审文件',
      // 资格预审文件信息
      form: {
        tenderPrice: '',
        tenderBondPrice: '',
        sellStartDate: '',
        sellEndDate: '',
        bidOpenDate: '',
        sectionNumber: this.projectInfo.objectId
      },
      isSave: false,
      isSaveFlag: false,
      gonggaoData: [],
      upLoad: `${loadUrl}file/upload`,
      // 日期选择
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7
        }
      },
      dataRange: [],
      fileList: [],
      ruleForm: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      // 提交时验证
      rules: {
        tenderPrice: [
          { min: 1, max: 100, message: '请输入1~100个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ],
        tenderBondPrice: [
          { min: 1, max: 100, message: '请输入1~100个字符', trigger: ['blur', 'change'] },
          { validator: Numbertypes, trigger: ['blur', 'change'] }
        ]
      },
      formLabelWidth: '150px',
      // 附件查询列表：
      EnclosuretableData: [],
      Enclosure: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      loading: false,
      appendix: false,
      otherAppendix: false,
      save: [],
      outhersave: [],
      tableType: [],
      outherType: []
    }
  },
  props: ['projectInfo', 'businessType', 'btn'],
  methods: {
  // **********************************************************資格资格预审文件數據**************************************************
    // 查看
    toFormat () {
      this.dataRange = [this.form.sellStartDate, this.form.sellEndDate]
    },
    Seeform (boolean) {
      this.axios.get(`prequa-lifica-tions?sectionNumber=${this.projectInfo.objectId}&_t=${new Date().getTime()}`).then((res) => {
        if (res.data.prequalificationFile !== null) {
          this.form = res.data.prequalificationFile
          this.toFormat()
        }
        if (this.isSave) {
          this.$emit('getSaveInfo', this.isSave, this.title)
        }
        if (boolean) {
          this.$watch('form', () => {
            if (this.isSaveFlag) {
              this.isSave = true
            } else {
              this.isSave = false
            }
            this.isSaveFlag = false
            this.$emit('getSaveInfo', this.isSave, this.title)
          }, {deep: true})
        }
      })
    },
    // 提交
    submitbtnFun (formName) {
      if (this.dataRange) {
        this.form.sellStartDate = this.dataRange[0]
        this.form.sellEndDate = this.dataRange[1]
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios.post('prequa-lifica-tions', this.form).then((res) => {
            checkResponse(this, res, () => {
              this.isSaveFlag = true
              this.Seeform(false)
            })
          })
        } else {
          return false
        }
      })
    },
    //  ************************************************************************************************************
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
    addFile () {
      this.axios.post('file-information', this.save).then((res) => {
        checkResponse(this, res, () => {
          this.cancelFun()
          this.save = []
          this.listShow()
        })
      })
    },
    cancelFun () {
      this.save = []
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
    addFileOther () {
      this.axios.post('file-information', this.outhersave).then((res) => {
        checkResponse(this, res, () => {
          this.cancelotherFun()
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
      }, () => {
        this.$message.error('Not found')
      })
    },
    handleEdit (name, path) {
      downloadFile(name, path)
    },
    // 删除资格预审文件
    deleteData (scope) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.tableType = []
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
    // 删除其他附件
    deleteRow (scope) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.outherType = []
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
    this.Seeform(true)
    this.listShow()
  }
}
</script>
<style lang="less">
  #zztprequalificationDoc{
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
    .el-form-item__content {
      line-height: 40px;
      position: relative;
      font-size: 14px;
      margin-left: 0!important;
      width: 100%!important;
    }
    .el-upload {
      display: inline-block;
      text-align: center;
      cursor: pointer;
      outline: 0;
      float: right;
    }
    el-upload-list {
      width: 85%;
      margin: 0;
      padding: 0;
      list-style: none;
      float: inherit;
      text-align: left;
    }
    .el-button--primary {
      background-color: #409eff!important;
      border-color: #409eff!important;
    }
    .el-form-item__label{
      width: 150px!important;
    }
    .el-form-item__content {
      line-height: 40px;
      position: relative;
      font-size: 14px;
      margin-left: 0!important;
      width: 60%!important;
      float: left!important;
    }
    .zgysfile .el-form-item__content {
      line-height: 40px;
      position: relative;
      font-size: 14px;
      margin-left: 0!important;
      width: 100%!important;
      float: left!important;
    }
    .el-form span {
      color: #fff!important;
    }
    .el-upload-list {
      width: 60%;
      float: inherit;
      text-align: left;
    }
    .upload-demo {
      text-align: left!important;
    }
  }
</style>
