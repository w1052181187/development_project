<template>
  <div class="cloudcontent">
    <div class="main">
      <el-table
        :data="projectData"
        border
        style="width: 100%" header-cell-class-name="tableheader">
        <el-table-column
          type="index"
          label="序号"
          width="60"
          align="center"
          :index="indexMethod">
        </el-table-column>
        <el-table-column
          prop="bidOpeningRoom.name"
          label="开评标室"
          width="140"
          align="center"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="usedDate"
          label="禁用时间"
          align="center"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="remarks"
          label="禁用原因"
          align="center"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="180">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="updateRecord(scope)" v-if="$store.getters.permissions.includes('1020102')">修改</el-button>
            <el-button type="text" size="small" @click="delRecord(scope)" v-if="$store.getters.permissions.includes('1020102')">删除</el-button>
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
    </div>
    <!-- 禁用设置弹窗-->
    <el-dialog :title="dialogDisableTitle" :visible.sync="dialogDisableVisible" width="50%"
               :before-close="closeDisable">
      <el-form :model="disableForm" :rules="disableFormRules" ref="disableForm"
               :label-width="formLabelWidth" :validate-on-rule-change="true">
        <el-form-item label="开评标室：">
          <span class="form_content_width">{{disableForm.name}}</span>
        </el-form-item>
        <el-form-item label="禁用时间：" prop="time" >
          <el-date-picker
            v-model="disableForm.time"
            type="datetimerange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
          <span class="check_order" @click="handleDisableCheckOrder">检测是否可禁用</span>
          <el-row v-if="isDisableChecked">
            <div class="can_order" v-if="checkDisableResult"><img src="../../../../static/images/can-order.png">可以禁用</div>
            <div class="can_not_order" v-else><img src="../../../../static/images/not-order.png">该时间段已被预约或禁用</div>
          </el-row>
        </el-form-item>
        <el-form-item label="禁用原因：">
          <el-input
            class="form_content_width"
            type="textarea"
            :autosize="{ minRows: 4}"
            v-model="disableForm.remarks">
          </el-input>
        </el-form-item>
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitDisable" :loading="isDisableSubmiting">保存</el-button>
          <el-button class="cancal" @click="closeDisable">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {bidOpeningRoom, appointmentRecord} from '@/api/resource'
export default {
  name: '',
  data () {
    return {
      projectData: [],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 0, // 总条数
      pageSize: 10, // 每页展示条数
      formLabelWidth: '120px',
      isDisableSubmiting: false,
      dialogDisableVisible: false,
      disableForm: {
      },
      dialogDisableTitle: '禁用设置',
      disableFormRules: {
        time: [
          { required: true, message: '请选择禁用时间', trigger: ['blur', 'change'] }
        ]
      },
      disableRoomOptions: [],
      isDisableChecked: false,
      checkDisableResult: true
    }
  },
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getTableData()
    },
    updateRecord (scope) {
      this.dialogDisableVisible = true
      this.disableForm = Object.assign({}, scope.row)
      this.$set(this.disableForm, 'time', [scope.row.startTime, scope.row.endTime])
      this.disableForm.name = scope.row.bidOpeningRoom.name
    },
    delRecord (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        appointmentRecord.deleteAppointmentRecord(scope.row.objectId).then((res) => {
          if (res.data.resCode === '0000') {
            this.getTableData()
          } else {
            return false
          }
        })
      }).catch(() => {
        return false
      })
    },
    // 检测是否可预约(禁用设置)
    handleDisableCheckOrder () {
      let query = {
        code: this.disableForm.bidOpeningRoom.code,
        objectId: this.disableForm.objectId
      }
      if (this.disableForm.time && this.disableForm.time.length > 1) {
        this.$set(query, 'forbiddenStartDate', this.disableForm.time[0])
        this.$set(query, 'forbiddenEndDate', this.disableForm.time[1])
      } else {
        this.isDisableChecked = false
        return false
      }
      bidOpeningRoom.checkBidOpeningRoomIsOedered(query).then(res => {
        if (res.data.resCode === '0000') {
          this.checkDisableResult = res.data.flag
          this.isDisableChecked = true
        } else {
          return false
        }
      })
    },
    closeDisable () {
      this.dialogDisableVisible = false
      this.disableForm = {}
      this.$refs['disableForm'].resetFields()
      this.isDisableChecked = false
    },
    submitDisable () {
      if (!this.checkDisableResult) {
        return false
      }
      this.$refs['disableForm'].validate((valid) => {
        if (valid) {
          this.disableForm.startTime = this.disableForm.time[0]
          this.disableForm.endTime = this.disableForm.time[1]
          this.isDisableSubmiting = true
          appointmentRecord.saveOrUpdateAppointmentRecord(this.disableForm).then(res => {
            if (res.data.resCode === '0000') {
              this.closeDisable()
              this.getTableData()
              this.isDisableSubmiting = false
            } else {
              return false
            }
          })
        } else {
          return false
        }
      })
    },
    // 获取禁用记录
    getTableData () {
      appointmentRecord.getAppointmentRecordList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        bidOpeningRoomCode: this.$route.params.code,
        status: 2 // 禁用记录
      }).then(res => {
        this.projectData = res.data.appointmentRecordList.list
        this.total = res.data.appointmentRecordList.total
      })
    }
  },
  mounted () {
    this.getTableData()
  }
}
</script>

<style scoped>
  .form_content_width{
    width: 400px;
  }
  .can_order{
    color: #c5c5c5;
    font-size: 12px;
    /*padding: 6px 0;*/
    /*box-sizing: border-box;*/
  }
  .can_order img{
    margin-right: 6px;
    width: 12px;
    height: 12px;
  }
  .can_not_order{
    color: #c5c5c5;
    font-size: 12px;
    /*padding: 6px 0;*/
    /*box-sizing: border-box;*/
  }
  .can_not_order img{
    margin-right: 6px;
    width: 12px;
    height: 12px;
  }
</style>
