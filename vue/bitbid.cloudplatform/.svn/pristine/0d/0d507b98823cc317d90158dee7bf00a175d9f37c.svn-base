<template>
  <div  id="xiugai_tankang">
    <!--资格预审结果通知书-->
    <div class="zztbdzs_sonbigbox">
      <div class="zhankai_con_sontitlebox">
        <span>资格预审结果通知书</span>
      </div>
      <el-button v-if="btn" type="text" @click="dialogFormVisible1 = true">
        <div class="tianjia_btn">
          <span class="jia">+</span>
          <span>添加投标单位</span>
        </div>
      </el-button>
      <el-table
        :data="gonggaoData"
        style="width:100%"
        border
        max-height="250">
        <el-table-column
          type="index"
          :index="1"
          label="序号"
          width="80">
        </el-table-column>
        <el-table-column
          prop="tenderUnit"
          label="投标单位">
        </el-table-column>
        <el-table-column
          prop="contactPerson"
          label="联系人">
        </el-table-column>
        <el-table-column
          prop="contactPhone"
          label="联系电话">
        </el-table-column>
        <el-table-column
          prop="contactEmail"
          label="邮箱">
        </el-table-column>
        <el-table-column
          fixed="right"
          v-if="btn"
          label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              @click="handleEdit_xinx(scope)"
              type="text"
              size="small"
              class="chakna_yichu">
              修改
            </el-button>
            <el-button
              @click="deleteRow_gonggao(scope)"
              type="text"
              size="small"
              class="chakna_yichu">
              移除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--修改弹框-->
      <el-dialog @close='cancle' title="资格预审结果通知书" :visible.sync="dialogFormVisible">
        <el-form :model="form" :rules="rules"  ref="form" label-width="100px" class="demo-ruleForm">
          <el-form-item label="投标单位" prop="tenderUnit">
            <el-input v-model="form.tenderUnit"></el-input>
          </el-form-item>
          <el-form-item label="联系人" prop="contactPerson">
            <el-input v-model="form.contactPerson"></el-input>
          </el-form-item>
          <el-form-item label="联系电话" prop="contactPhone">
            <el-input v-model="form.contactPhone"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="contactEmail">
            <el-input v-model="form.contactEmail"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitFormone_xinxi">保 存</el-button>
          <el-button @click="cancle">取 消</el-button>
        </div>
      </el-dialog>
      <!--修改弹框-->
      <!--添加弹框-->
      <el-dialog @close='cancle' title="资格预审结果通知书" :visible.sync="dialogFormVisible1">
        <el-form :model="form" :rules="rules" ref="form">
          <el-form-item label="投标单位" prop="tenderUnit" :label-width="formLabelWidth">
            <el-input auto-complete="off" v-model="form.tenderUnit"></el-input>
          </el-form-item>
          <el-form-item label="联系人" prop="contactPerson" :label-width="formLabelWidth">
            <el-input auto-complete="off" v-model="form.contactPerson"></el-input>
          </el-form-item>
          <el-form-item label="联系电话" prop="contactPhone" :label-width="formLabelWidth">
            <el-input auto-complete="off" v-model="form.contactPhone"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="contactEmail" :label-width="formLabelWidth">
            <el-input auto-complete="off"  v-model="form.contactEmail"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancle">取 消</el-button>
          <el-button type="primary" @click="add_gonggao('form')">确 定</el-button>
        </div>
      </el-dialog>
      <!--添加弹框-->
    </div>
    <!--资格预审结果通知书-->
    <!--资格预审公告附件-->
    <div class="zztbdzs_sonbigbox">
      <div class="zhankai_con_sontitlebox">
        <span>其他附件</span>
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
            <el-button v-if="btn"
              @click.native.prevent="deleteRow(scope.row, EnclosuretableData)"
              type="text"
              size="small"
              class="chakna_yichu">
              移除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--添加弹框-->
      <el-dialog @close='cancleqita' title="其他附件" :visible.sync="appendix">
        <el-form :model="Enclosure">
          <el-form-item label="" prop="name">
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
    <!--资格预审公告附件-->
  </div>
</template>
<script>
import {isvalidPhone} from '../../../assets/js/validate'
import {checkResponse, loadUrl, fileSize, addtitle, downloadFile} from '../../../assets/js/common'
const validPhone = (rule, value, callback) => {
  if (!value) {
    callback(new Error('请输入电话号码'))
  } else if (!isvalidPhone(value)) {
    callback(new Error('请输入正确的11位手机号码'))
  } else {
    callback()
  }
}
export default {
  data () {
    return {
      // 合格申請人信息展示
      gonggaoData: [],
      // 添加合格申请人信息
      nowObjectId: '',
      // 附件查询列表：
      loading: false,
      EnclosuretableData: [],
      Enclosure: {},
      appendix: false,
      save: [],
      upLoad: `${loadUrl}file/upload`,
      // 判断
      rules: {
        tenderUnit: [
          {required: true, message: '投标单位不能为空', trigger: 'blur'},
          { min: 1, max: 200, message: '请输入1~200个字符', trigger: ['blur', 'change'] }
        ],
        contactPhone: [
          { required: true, trigger: 'blur', validator: validPhone },
          {required: true, message: '电话不能为空', trigger: 'blur'}
        ],
        contactPerson: [
          {required: true, message: '联系人不能为空', trigger: 'blur'},
          { min: 1, max: 100, message: '请输入1~100个字符', trigger: ['blur', 'change'] }
        ],
        contactEmail: [
          {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur'}
        ]
      },
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
      dialogFormVisible: false,
      dialogFormVisible1: false,
      form: {
        sectionNumber: this.projectInfo.objectId
      },
      options: [{
        value: '选项1',
        label: '正常公告'
      }, {
        value: '选项2',
        label: '更正公告'
      }],
      value4: '',
      valuetimes: ''
    }
  },
  props: ['projectInfo', 'businessType', 'btn', 'fileName'],
  methods: {
    //     ********************************************************************** 公告**************************************************************
    // 列表展示
    gonggaoList () {
      this.axios.get(`prequa-liresults?sectionNumber=${this.projectInfo.objectId}&_t=${new Date().getTime()}`).then((res) => {
        this.gonggaoData = res.data.prequaliResultList
        this.loading = false
        addtitle(this)
      })
    },
    // 添加保存
    add_gonggao (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios.post('prequa-liresults', this.form).then((res) => {
            checkResponse(this, res, () => {
              this.gonggaoList()
              this.cancle()
            })
          }, () => {
            this.$message.error('Not found')
          })
        } else {
          return false
        }
      })
    },
    // 删除
    deleteRow_gonggao (scope) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.axios.delete(`prequa-liresults/${scope.row.objectId}`).then((res) => {
          checkResponse(this, res, () => {
            this.gonggaoList()
          })
        })
      }).catch(() => {
        return false
      })
    },
    // 修改彈窗
    handleEdit_xinx (scope) {
      this.dialogFormVisible = true
      this.axios.get(`prequa-liresults/${scope.row.objectId}`).then((res) => {
        this.form = res.data.prequaliResult
      })
    },
    // 修改后保存
    submitFormone_xinxi () {
      this.axios.put('prequa-liresults', this.form).then((res) => {
        checkResponse(this, res, () => {
          this.gonggaoList()
          this.cancle()
        })
      }, () => {
        this.$message.error('Not found')
      })
    },
    cancle () {
      this.dialogFormVisible1 = false
      this.dialogFormVisible = false
      this.$refs.form.resetFields()
    },
    // ********************************************************************** 附件**************************************************************
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
    // 取消
    cancelFun () {
      this.save = []
      this.appendix = false
      this.$refs.upload.clearFiles()
    },
    cancleqita () {
      this.save = []
      this.appendix = false
      this.$refs.upload.clearFiles()
    },
    // 附件列表查询：
    listShow () {
      this.EnclosuretableData = []
      this.axios.get(`project-infos/${this.projectInfo.objectId}?_t=${new Date().getTime()}`).then((res) => {
        if (res.data.resCode == '0000') {
          this.projectInfo.fileInformations = res.data.projectInformation.fileInformations
          this.projectInfo.fileInformations.map((item) => {
            item.businessType == this.businessType ? this.EnclosuretableData.push(item) : this.EnclosuretableData
          })
        }
      }, () => {
        this.$message.error('Not found')
      })
    },
    handleEdit (name, path) {
      downloadFile(name, path)
    },
    // 删除
    deleteRow (scope) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.axios.delete(`file-information/${scope.objectId}`).then((res) => {
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
    this.gonggaoList()
    this.listShow()
  }
}
</script>
<style lang="less">
#xiugai_tankang{
  .el-form-item__content{
    text-align: right!important;
  }
  .el-table .cell {
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    word-break: break-all;
    line-height: 23px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
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
