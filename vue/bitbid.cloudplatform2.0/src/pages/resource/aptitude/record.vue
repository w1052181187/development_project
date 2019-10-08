<template>
  <div class="cloudcontent">
    <div class="main">
      <el-form :model="updateForm">
        <el-row>
          <el-col :span="12">
            <el-form-item label="资质类别：">
              <span v-if="updateForm.category">{{updateForm.category.name}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="资质编号：">
              <span>{{updateForm.number}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资质等级：">
              <span v-if="updateForm.gradeCategory">{{updateForm.gradeCategory.name}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="资质有效期：">
              <span>{{updateForm.effectiveDate}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资质主体：" v-if="updateForm.sourceType === 1">
              <span v-if="updateForm.dictionaryDetail">{{updateForm.dictionaryDetail.name}}</span>
            </el-form-item>
            <el-form-item label="合作方：" v-else>
              <span>{{updateForm.subject}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="历史合作记录：">
              <el-button type="text" size="small" @click="handleAddRecord">增加合作记录</el-button>
            </el-form-item>
          </el-col>
        </el-row>
        <el-table
          :data="tableData"
          border>
          <el-table-column
            type="index"
            label="序号"
            align="center"
            :index="indexMethod"
            width="80">
          </el-table-column>
          <el-table-column
            prop="cooperationType"
            label="合作类型"
            width="80">
            <template slot-scope="scope">
              <span v-if="scope.row.cooperationType === 1">机构合作</span>
              <span v-if="scope.row.cooperationType === 2">项目合作</span>
              <span v-if="scope.row.cooperationType === 3">个人合作</span>
              <span v-else>---</span>
            </template>
          </el-table-column>
          <el-table-column
              prop="cooperator"
              :formatter="simpleFormatData"
              label="合作方">
          </el-table-column>
          <el-table-column
            prop="effectiveDate"
            label="合作时间"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="fee"
            label="费用"
            :formatter="simpleFormatData"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="creatorName"
            :formatter="simpleFormatData"
            label="经手人">
          </el-table-column>
          <el-table-column
            prop="remarks"
            label="备注"
            :formatter="simpleFormatData"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            label="操作" align="center" width="120">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="handleUpdateRecord(scope)">修改</el-button>
              <el-button type="text" size="small" @click="handleDelRecord(scope)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--分页-->
        <el-pagination
          background
          layout="prev, pager, next"
          :total="total"
          :page-size='pageSize'
          :current-page.sync="currentPage"
          @current-change="handleCurrentChange">
        </el-pagination>
        <!--分页-->
      </el-form>
    </div>
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="60%" :before-close="close">
      <el-form :model="form" :rules="rules" ref="form"
               :label-width="formLabelWidth"
               label-position="right">
        <el-row>
          <el-col :span="12">
            <el-form-item label="合作方："  prop="cooperator" >
              <el-input v-model="form.cooperator"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="合作类型：">
              <el-select v-model="form.cooperationType" prop="cooperationType">
                <el-option
                  v-for="item in cooperationOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="合作期间：" class="bitianicon"  prop="time">
              <el-date-picker
                v-model="form.time"
                type="daterange"
                range-separator="-"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="费用(元)：">
              <el-input v-model="form.fee"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注：">
          <el-input
            type="textarea"
            :autosize="{ minRows: 4}"
            v-model="form.remarks">
          </el-input>
        </el-form-item>
        <!--<el-form-item label="备注：">-->
          <!--<editor ref="ueditor" class="ueditor" :editread="editread"></editor>-->
        <!--</el-form-item>-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submit('form')" :loading="isSubmiting">保存</el-button>
          <el-button class="cancal" @click="close">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { cooperationRecord, qualificationSubject } from '@/api/resource'

export default {
  data () {
    return {
      updateForm: {
        category: {
          name: ''
        },
        gradeCategory: {
          name: ''
        },
        dictionaryDetail: {
          name: ''
        }
      },
      tableData: [],
      isSubmiting: false,
      dialogVisible: false,
      form: {},
      formLabelWidth: '100px',
      dialogTitle: '新增合作记录',
      cooperationOptions: [
        {
          label: '机构合作',
          value: 1
        },
        {
          label: '项目合作',
          value: 2
        },
        {
          label: '个人合作',
          value: 3
        }
      ],
      rules: {
        cooperator: [
          { required: true, message: '合作方不能为空', trigger: ['change'] }
        ],
        cooperationType: [
          { required: true, message: '合作类型不能为空', trigger: ['change'] }
        ],
        time: [
          { required: true, message: '合作期间不能为空', trigger: ['change'] }
        ]
      },
      addCount: 0,
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 100, // 总条数
      pageSize: 10 // 每页展示条数
    }
  },
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    // 分页
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getList()
    },
    handleAddRecord () {
      this.dialogTitle = '新增合作记录'
      this.form = {}
      this.dialogVisible = true
      if (this.addCount === 0) {
        this.addCount += 1
      }
    },
    handleUpdateRecord (scope) {
      this.dialogTitle = '修改合作记录'
      this.dialogVisible = true
      this.form = scope.row
      this.$set(this.form, 'time', [scope.row.startDate, scope.row.endDate])
    },
    handleDelRecord (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        cooperationRecord.deleteAppointmentRecord(scope.row.objectId).then((res) => {
          if (res.data.resCode === '0000') {
            this.getData()
          }
        })
      }).catch(() => {
        return false
      })
    },
    close () {
      this.dialogVisible = false
      this.$refs['form'].resetFields()
    },
    // 保存
    submit (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.isSubmiting = true
          this.form.startDate = this.form.time[0] + ' 00:00:00'
          this.form.endDate = this.form.time[1] + ' 00:00:00'
          this.form.subjectCode = this.$route.params.code
          cooperationRecord.saveOrUpdateCooperationRecord(this.form).then((res) => {
            this.dialogVisible = false
            this.isSubmiting = false
            this.$refs['form'].resetFields()
            this.getData()
          })
        } else {
          return false
        }
      })
    },
    getData () {
      qualificationSubject.getQualificationSubjectDetailByCode(this.$route.params.code).then((res) => {
        this.updateForm = res.data.qualificationSubject
      })
      cooperationRecord.getCooperationRecordList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        subjectCode: this.$route.params.code
      }).then((res) => {
        this.tableData = res.data.cooperationRecordList.list
        this.total = res.data.cooperationRecordList.total
      })
    },
    // 普通格式化数据，空的时候展示"---"
    simpleFormatData (row, col, cellValue) {
      return cellValue || '---'
    }
  },
  mounted () {
    this.getData()
  }
}
</script>

<style scoped>
  .submit-radio{
    text-align: center;
  }
  .el-date-editor--daterange.el-input__inner{
    width: auto;
  }
</style>
