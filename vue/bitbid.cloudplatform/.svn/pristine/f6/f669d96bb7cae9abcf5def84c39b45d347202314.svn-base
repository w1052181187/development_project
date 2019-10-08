<template>
  <div class="fillType">
    <!--资格预审结果通知书-->
    <div class="zztbdzs_sonbigbox" id="xiugai_tankangss">
      <div class="zhankai_con_sontitlebox">
        <span>资格预审公告（含变更）</span>
      </div>
      <el-button v-if="btn" type="text" @click="addNotice()">
        <div class="tianjia_btn">
          <span class="jia">+</span>
          <span>添加公告</span>
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
          prop="annoName"
          label="公告名称">
        </el-table-column>
        <el-table-column
          prop="annoNature"
          label="公告性质">
          <template slot-scope="scope">
            {{scope.row.annoNature==1?'正常公告':'更正公告'}}
          </template>
        </el-table-column>
        <el-table-column
          prop="publishDate"
          label="发布时间">
        </el-table-column>
        <el-table-column
          prop="annoLink"
          label="公告链接">
          <template  slot-scope="scope">
            <a :href="scope.row.annoLink" target="_blank">{{scope.row.annoLink}}</a>
          </template>
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          align="center"
          v-if='btn'
          width="200px">
          <template slot-scope="scope">
            <el-button
              @click="lookhandleEdit(scope)"
              type="text"
              size="small"
              class="chakna_yichu">
              修改
            </el-button>
            <el-button
              @click="deleteRow_gonggao(scope, gonggaoData)"
              type="text"
              size="small"
              class="chakna_yichu">
              移除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--修改弹框-->
      <el-dialog @close='cancle' title="资格预审公告（含变更）" :visible.sync="dialogFormVisible">
        <el-form :model="form" :rules="rules"  ref="form" label-width="100px" class="demo-ruleForm">
          <el-form-item label="公告名称" prop="annoName">
            <el-input v-model="form.annoName"></el-input>
          </el-form-item>
          <el-form-item class="fbsj_box" label="公告性质" prop="annoNature">
            <el-select v-model="form.annoNature" placeholder="请选择">
              <el-option
                v-for="item in options_ggxz"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item class="fbsj_box" label="发布时间" prop="publishDate">
            <el-date-picker
              v-model="form.publishDate"
              type="datetime"
              :picker-options="pickerOptions"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="公告链接" prop="annoLink">
            <el-input v-model="form.annoLink"></el-input>
          </el-form-item>
          <el-form-item class="xiugaibox">
            <el-button type="primary" @click="submitFormone('form')">保存</el-button>
            <el-button @click="cancelFun()">取 消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
      <!--修改弹框-->
      <!--添加弹框-->
      <el-dialog @close='cancle' title="资格预审公告（含变更）" :visible.sync="dialogFormVisible1">
        <el-form :model="form" :rules="rules" ref="form">
          <el-form-item label="公告名称" prop="annoName" :label-width="formLabelWidth">
            <el-input  auto-complete="off" v-model="form.annoName"></el-input>
          </el-form-item>
          <el-form-item label="公告性质" prop="annoNature" :label-width="formLabelWidth">
            <el-select v-model="form.annoNature" clearable placeholder="请选择">
              <el-option
                v-for="item in options_ggxz"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发布时间" prop="publishDate" :label-width="formLabelWidth">
            <el-date-picker
              v-model="form.publishDate"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              :picker-options="pickerOptions"
              placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="公告链接" prop="annoLink"  :label-width="formLabelWidth">
            <el-input auto-complete="off"  v-model="form.annoLink"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelFun()">取 消</el-button>
          <el-button type="primary" @click="add_gonggao('form')">确 定</el-button>
        </div>
      </el-dialog>
      <!--添加弹框-->
    </div>
    <!--资格预审结果通知书-->
    <!--资格预审公告附件-->
    <fileUpload :businessType="businessType" :btn='btn' :project-info='projectInfo' :file-name='fileName'></fileUpload>
    <!--资格预审公告附件-->
  </div>
</template>
<script>
import {checkResponse, addtitle} from '../../../assets/js/common'
import fileUpload from './fileUpload'
import {links} from '../../../assets/js/validate'
export default {
  components: {
    fileUpload
  },
  data () {
    // 电话号码
    var validLinks = (rule, value, callback) => {
      if (value === '' || value === undefined) {
        callback()
      } else if (!links(value)) {
        callback(new Error('请输入正确的链接地址'))
      } else {
        callback()
      }
    }
    return {
      // 公告列表展示
      gonggaoData: [],
      // 增加公告的提交
      form: {
        annoName: '',
        annoNature: '',
        publishDate: '',
        annoLink: '',
        sectionNumber: this.projectInfo.objectId
      },
      updateForm: {},
      nowObjectId: '',
      // 公告性质
      options_ggxz: [{
        value: 1,
        label: '正常公告'
      }, {
        value: 2,
        label: '更正公告'
      }
      ],
      // 判断
      rules: {
        annoName: [
          {required: true, message: '公告名称不能为空', trigger: 'blur'},
          {min: 1, max: 200, message: '请输入1~200个字符', trigger: 'change'}
        ],
        publishDate: [
          {required: true, message: '发布时间不能为空', trigger: 'blur'}
        ],
        annoNature: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        annoLink: [
          {min: 1, max: 100, message: '请输入1~100个字符', trigger: ['blur', 'change']},
          {validator: validLinks, trigger: 'blur'}
        ]
      },
      // 日期选择
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7
        }
      },
      value: '',
      formLabelWidth: '120px',
      dialogFormVisible: false,
      dialogFormVisible1: false,
      // 附件查询列表：
      EnclosuretableData: [],
      Enclosure: {},
      loading: false,
      appendix: false,
      save: []
    }
  },
  props: ['projectInfo', 'businessType', 'btn', 'fileName'],
  methods: {
  //  ********************************************************************** 公告**************************************************************
    // 列表展示
    gonggaoList () {
      this.axios.get(`prequa-lification-annos?sectionNumber=${this.projectInfo.objectId}&_t=${new Date().getTime()}`).then((res) => {
        this.gonggaoData = res.data.prelificatAnnoList
        this.loading = false
        addtitle(this)
      })
    },
    addNotice () {
      this.dialogFormVisible1 = true
    },
    cancelFun () {
      this.$refs.form.resetFields()
      this.dialogFormVisible1 = false
      this.dialogFormVisible = false
    },
    cancle () {
      this.cancelFun()
    },
    // 添加公告保存
    add_gonggao (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios.post('prequa-lification-annos', this.form).then((res) => {
            checkResponse(this, res, () => {
              this.dialogFormVisible1 = false
              this.gonggaoList()
              for (let i in this.form) {
                this.form[i] = null
                this.form.sectionNumber = this.projectInfo.objectId
              }
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
        this.axios.delete(`prequa-lification-annos/${scope.row.objectId}`).then((res) => {
          checkResponse(this, res, () => {
            this.dialogFormVisible1 = false
            this.gonggaoList()
          })
        })
      }).catch(() => {
        return false
      })
    },
    // 修改公告彈窗
    lookhandleEdit (scope) {
      this.dialogFormVisible = true
      this.axios.get(`prequa-lification-annos/${scope.row.objectId}`).then((res) => {
        this.form = res.data.prequalificationAnno
      })
    },
    // 修改后保存公告
    submitFormone (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios.put('prequa-lification-annos', this.form).then((res) => {
            checkResponse(this, res, () => {
              this.dialogFormVisible = false
              this.gonggaoList()
              this.form = {}
            })
          }, () => {
            this.$message.error('Not found')
          })
        }
      })
    }
  },
  mounted () {
    this.gonggaoList()
  }
}
</script>
<style lang="less">
  #xiugai_tankangss{
    .fbsj_box .el-form-item__content{
      text-align: left;
    }
    .el-form-item__content{
      text-align: left!important;
    }
    .xiugaibox .el-form-item__content{
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
  }
</style>
