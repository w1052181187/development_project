<template>
  <div class="fillType" id="xiugai_tankang">
    <!--资格预审结果通知书-->
    <div class="zztbdzs_sonbigbox">
      <div class="zhankai_con_sontitlebox">
        <span>招标公告</span>
      </div>
      <el-button v-if="btn" type="text" @click="dialogFormVisible1 = true">
        <div class="tianjia_btn">
          <span class="jia">+</span>
          <span>添加招标公告</span>
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
          prop="publicName"
          label="公告名称">
        </el-table-column>
        <el-table-column
          prop="publicProperty"
          label="公告性质">
          <template slot-scope="scope">
            {{scope.row.publicProperty==1?'正常公告':'更正公告'}}
          </template>
        </el-table-column>
        <el-table-column
          prop="releaseTime"
          label="发布时间">
        </el-table-column>
        <el-table-column
          prop="publicLink"
          label="公告链接">
        </el-table-column>
        <el-table-column
          v-if="btn"
          fixed="right"
          label="操作"
          align="center">
          <template slot-scope="scope">
            <el-button
              @click="handleEdit_xinx(scope)"
              type="text"
              size="small"
              class="chakna_yichu">
              修改
            </el-button>
            <el-button
              @click="deleteRowNotice(scope)"
              type="text"
              size="small"
              class="chakna_yichu">
              移除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--修改弹框-->
      <el-dialog @close='cancle' title="招标公告" :visible.sync="dialogFormVisible">
        <el-form :model="form" :rules="rules"  ref="form" label-width="100px" class="demo-ruleForm">
          <el-form-item label="公告名称" prop="publicName" :label-width="formLabelWidth">
            <el-input v-model="form.publicName"></el-input>
          </el-form-item>
          <el-form-item label="公告性质" prop="publicProperty" :label-width="formLabelWidth">
            <el-select v-model="form.publicProperty" placeholder="请选择">
              <el-option
                v-for="item in options_ggxz"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发布时间" prop="releaseTime" :label-width="formLabelWidth">
            <el-date-picker
              v-model="form.releaseTime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              :picker-options="pickerOptions"
              placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="公告链接" prop="publicLink" :label-width="formLabelWidth">
            <el-input v-model="form.publicLink"></el-input>
          </el-form-item>
          <el-form-item  class="dialogfooter">
            <el-button @click="cancle">取 消</el-button>
            <el-button type="primary" @click="submitFormone(),dialogFormVisible = false">确 定</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
      <!--修改弹框-->
      <!--添加弹框-->
      <el-dialog @close='cancle' title="招标公告" :visible.sync="dialogFormVisible1">
        <el-form :model="form" :rules="rules" ref="form">
          <el-form-item label="公告名称" prop="publicName" :label-width="formLabelWidth">
            <el-input auto-complete="off" v-model="form.publicName"></el-input>
          </el-form-item>
          <el-form-item class="ggxzbox" label="公告性质" prop="publicProperty" :label-width="formLabelWidth">
            <el-select v-model="form.publicProperty" placeholder="请选择">
              <el-option
                v-for="item in options_ggxz"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发布时间" prop="releaseTime" :label-width="formLabelWidth">
            <el-date-picker
              v-model="form.releaseTime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              :picker-options="pickerOptions"
              placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="公告链接" prop="publicLink" :label-width="formLabelWidth">
            <el-input auto-complete="off"  v-model="form.publicLink"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancle">取 消</el-button>
          <el-button type="primary" @click="addNotice('form')">确 定</el-button>
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
        publicName: '',
        publicProperty: '',
        releaseTime: '',
        publicLink: '',
        objectId: this.nowObjectId,
        sectionNumber: this.projectInfo.objectId
      },
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
        publicName: [
          {required: true, message: '公告名称不能为空', trigger: 'blur'},
          {min: 1, max: 200, message: '请输入1~200个字符', trigger: ['blur', 'change']}
        ],
        releaseTime: [
          {required: true, message: '发布时间不能为空', trigger: 'blur'}
        ],
        publicProperty: [
          {required: true, message: '公告性质不能为空', trigger: 'blur'}
        ],
        publicLink: [
          {min: 1, max: 100, message: '请输入1~100个字符', trigger: ['blur', 'change']}
        ]
      },
      // 附件查询列表：
      EnclosuretableData: [],
      Enclosure: {},
      appendix: false,
      save: [],
      loading: false,
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
      fileList: []
    }
  },
  props: ['projectInfo', 'businessType', 'btn', 'fileName'],
  methods: {
  // ********************************************************************** 公告**************************************************************
    // 列表展示
    gonggaoList () {
      this.axios.get(`tender-announcements?sectionNumber=${this.projectInfo.objectId}&_t=${new Date().getTime()}`).then((res) => {
        this.gonggaoData = res.data.tenderAnnoList
        this.loading = false
        addtitle(this)
      })
    },
    // 添加保存
    addNotice (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios.post('tender-announcements', this.form).then((res) => {
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
    cancle () {
      this.dialogFormVisible1 = false
      this.dialogFormVisible = false
      this.$refs.form.resetFields()
    },
    // 删除
    deleteRowNotice (scope) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.axios.delete(`tender-announcements/${scope.row.objectId}`).then((res) => {
          checkResponse(this, res, () => {
            this.gonggaoList()
          })
        })
      }).catch(() => {
        return false
      })
    },
    // 修改公告彈窗
    handleEdit_xinx (scope) {
      this.dialogFormVisible = true
      this.axios.get(`tender-announcements/${scope.row.objectId}`).then((res) => {
        this.form = res.data.tenderAnnouncement
      })
    },
    // 修改后保存
    submitFormone () {
      this.axios.put('tender-announcements', this.form).then((res) => {
        checkResponse(this, res, () => {
          this.gonggaoList()
          this.cancle()
        })
      }, () => {
        this.$message.error('Not found')
      })
    }
    // *****************************************************************附件**************************************************************
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
    .el-form-item__content{
      text-align: left!important;
    }
    .dialogfooter>.el-form-item__content{
      text-align: right!important;
    }
    .ggxzbox .el-form-item__content{
      line-height: 40px;
      position: relative;
      font-size: 14px;
      text-align:left!important;
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
  }
</style>
