<template>
  <div class="fillType" id="xiugai_tankang">
    <!--资格预审结果通知书-->
    <div class="zztbdzs_sonbigbox">
      <div class="zhankai_con_sontitlebox">
        <span>投标单位报名表信息</span>
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
          prop="tendererUnit"
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
          label="操作"
          align="center"
          v-if="btn"
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
      <el-dialog @close='cancle' title="投标单位报名表信息" :visible.sync="dialogFormVisible">
        <el-form :model="form" :rules="rules"  ref="form" label-width="100px" class="demo-ruleForm">
          <el-form-item label="投标单位" prop="tendererUnit">
            <el-input v-model="form.tendererUnit"></el-input>
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
          <el-button @click="cancle">取 消</el-button>
          <el-button type="primary" @click="submitFormone('form')">确 定</el-button>
        </div>
      </el-dialog>
      <!--修改弹框-->
      <!--添加弹框-->
      <el-dialog @close='cancle' title="投标单位报名表信息" :visible.sync="dialogFormVisible1">
        <el-form :model="form" :rules="rules"  ref="form">
          <el-form-item label="投标单位" prop="tendererUnit" :label-width="formLabelWidth">
            <el-input auto-complete="off" v-model="form.tendererUnit"></el-input>
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
    <fileUpload :businessType="businessType" :btn='btn' :project-info='projectInfo' :file-name='fileName'></fileUpload>
    <!--资格预审公告附件-->
  </div>
</template>
<script>
import {isvalidPhone} from '../../../assets/js/validate'
import {checkResponse, addtitle} from '../../../assets/js/common'
import fileUpload from './fileUpload'
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
  components: {
    fileUpload
  },
  data () {
    return {
      // 公告列表展示
      gonggaoData: [],
      // 增加公告的提交
      form: {
        tendererUnit: '',
        contactPerson: '',
        contactPhone: '',
        contactEmail: '',
        objectId: this.nowObjectId,
        sectionNumber: this.projectInfo.objectId
      },
      nowObjectId: '',
      // 公告性质
      options_ggxz: [{
        value: '1',
        label: '正常公告'
      }, {
        value: '2',
        label: '更正公告'
      }
      ],
      // 初始
      gonggao_xinzhi: '正常公告',
      tableData5: [
        {
          xuhao_num: '1',
          fujiantitle: '山西比比网络有限公司目'
        }
      ],
      // 判断
      rules: {
        tendererUnit: [
          {required: true, message: '投标单位不能为空', trigger: 'blur'},
          {min: 1, max: 200, message: '请输入1~200个字符', trigger: 'change'}
        ],
        contactPerson: [
          {required: true, message: '联系人不能为空', trigger: 'blur'},
          {min: 1, max: 100, message: '请输入1~100个字符', trigger: 'change'}
        ],
        contactPhone: [
          { required: true, trigger: 'blur', validator: validPhone },
          {required: true, message: '电话不能为空', trigger: 'blur'}
        ],
        contactEmail: [
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
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
      formLabelWidth: '120px',
      dialogFormVisible: false,
      dialogFormVisible1: false,
      fileList: [],
      options: [{
        value: '选项1',
        label: '正常公告'
      }, {
        value: '选项2',
        label: '更正公告'
      }],
      value4: '',
      valuetimes: '',
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
      save: []
    }
  },
  props: ['projectInfo', 'businessType', 'btn', 'fileName'],
  methods: {
  // ********************************************************************** 公告**************************************************************
    // 列表展示
    gonggaoList () {
      this.axios.get(`tenderer-applys?sectionNumber=${this.projectInfo.objectId}&_t=${new Date().getTime()}`).then((res) => {
        this.gonggaoData = res.data.tendererApplyList
        this.loading = false
        addtitle(this)
      })
    },
    // 添加保存
    add_gonggao (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios.post('tenderer-applys', this.form).then((res) => {
            checkResponse(this, res, () => {
              this.gonggaoList()
              this.cancle()
            })
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
        this.axios.delete(`tenderer-applys/${scope.row.objectId}`).then((res) => {
          this.gonggaoList()
          this.$message({message: '删除成功', type: 'success'})
        })
      }).catch(() => {
        return false
      })
    },
    // 修改公告彈窗
    lookhandleEdit (scope) {
      this.dialogFormVisible = true
      this.axios.get(`tenderer-applys/${scope.row.objectId}`).then((res) => {
        this.form = res.data.tendetrerApply
      })
    },
    // 修改后保存
    submitFormone (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios.put('tenderer-applys', this.form).then((res) => {
            checkResponse(this, res, () => {
              this.dialogFormVisible = false
              this.gonggaoList()
              this.form = {}
            })
          })
        }
      })
    },
    cancle () {
      this.dialogFormVisible1 = false
      this.dialogFormVisible = false
      this.$refs.form.resetFields()
    }
    //   *****************************************************************附件**************************************************************
  },
  mounted () {
    this.gonggaoList()
  }
}
</script>
<style lang="less">
  #xiugai_tankang{
    .fbsj_box .el-form-item__content{
      text-align: left;
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
    .el-form-item__content {
      line-height: 40px;
      position: relative;
      font-size: 14px;
      margin-left: 0!important;
      width: 60%!important;
      float: left;
    }
    .el-button--primary {
      background-color: #409eff!important;
      border-color: #409eff!important;
    }
  }
</style>
