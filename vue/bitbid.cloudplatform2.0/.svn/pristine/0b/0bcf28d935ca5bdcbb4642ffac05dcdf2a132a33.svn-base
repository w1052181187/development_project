<template>
  <div class="cloudcontent">
    <div class="main">
      <div class="selectbox">
        <el-form label-width="85px" style="width: 100%">
          <el-row>
            <div class="selectlist">
              <el-form-item label="开评标室：">
                <el-input
                  v-model="messageLike"
                  placeholder="请输入开评标室名称">
                </el-input>
              </el-form-item>
            </div>
            <div class="selectlist">
              <el-form-item label="适用业务：" label-width="80px">
                <el-select v-model="checked" placeholder="全部">
                  <el-option label="全部" value="0"></el-option>
                  <el-option label="开标" value="1"></el-option>
                  <el-option label="评标" value="2"></el-option>
                  <el-option label="开标/评标" value="3"></el-option>
                </el-select>
              </el-form-item>
            </div>
            <div class="selectlist">
              <el-form-item label="类型：" label-width="50px">
                <el-select v-model="roomTypes" placeholder="全部">
                  <el-option label="全部" value="null"></el-option>
                  <el-option label="内部" value="1"></el-option>
                  <el-option label="外部" value="2"></el-option>
                </el-select>
              </el-form-item>
            </div>
            <div class="selectlist">
              <el-form-item label="所属公司：" label-width="70px">
                <el-select v-model="departmentName" placeholder="全部">
                  <el-option label="全部" value=""></el-option>
                  <el-option
                    v-for="item in listCompanies"
                    :key="item.name"
                    :label="item.name"
                    :value="item.name">
                  </el-option>
                </el-select>
              </el-form-item>
            </div>
            <div class="selectlist">
              <el-button type="primary" class="search_btn"  @click="search">查询</el-button>
              <el-button class="search_btn" @click="reset">重置</el-button>
            </div>
          </el-row>
        </el-form>
      </div>
      <div class="fr" v-if="$store.getters.permissions.includes('1020102')">
        <!--按钮-->
        <el-button type="primary" class="addbutton" @click="handleAdd">
          <span> + 新增</span>
        </el-button>
        <!--按钮-->
      </div>
      <el-table
        :data="projectData"
        border
        style="width: 100%" header-cell-class-name="tableheader">
        <el-table-column
          type="index"
          label="序号"
          width="80"
          :index="indexMethod"
          align="center">
        </el-table-column>
        <el-table-column
          prop="departmentName"
          label="所属公司"
          align="center"
          :formatter="simpleFormatData"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="name"
          label="开评标室"
          width="140"
          align="center"
          :formatter="simpleFormatData"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="roomType"
          label="类型"
          align="center"
          :formatter="simpleFormatData"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <span  v-if="scope.row.roomType === 1">内部</span>
            <span  v-if="scope.row.roomType === 2">外部</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="businessType"
          label="适用业务"
          align="center"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <span  v-if="scope.row.businessType === 1">开标</span>
            <span  v-if="scope.row.businessType === 2">评标</span>
            <span  v-if="scope.row.businessType === 3">开标/评标</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="accommodation"
          label="容纳人数"
          align="center"
          :formatter="simpleFormatData"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="address"
          label="地点"
          align="center"
          :formatter="simpleFormatData"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="admin"
          label="管理员"
          align="center"
          :formatter="simpleFormatData"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="260">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="handleUpdate(scope)" v-if="$store.getters.permissions.includes('1020102')">修改</el-button>
            <el-button type="text" size="small" @click="handleDel(scope)" v-if="$store.getters.permissions.includes('1020102')">删除</el-button>
            <el-button type="text" size="small" @click="handleBan(scope)" v-if="$store.getters.permissions.includes('1020102')">禁用设置</el-button>
            <el-button type="text" size="small" @click="handleBanRecord(scope)" v-if="$store.getters.permissions.includes('1020102')">禁用记录</el-button>
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
    <!-- 新增开评标室弹窗-->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="50%"
               :before-close="close">
      <el-form :model="form" :rules="rules" ref="form"
               :label-width="formLabelWidth" :validate-on-rule-change="true">
        <el-form-item label="开评标室名称：" prop="name" >
          <el-input v-model="form.name" :disabled="isUpdated">
          </el-input>
        </el-form-item>
        <el-form-item label="类型：" prop="roomType" >
         <el-radio-group v-model="form.roomType">
           <el-radio :label="1">内部</el-radio>
           <el-radio :label="2">外部</el-radio>
         </el-radio-group>
        </el-form-item>
        <el-form-item label="适用业务：" prop="businessType" >
          <el-radio-group v-model="form.businessType">
            <el-radio :label="1">开标</el-radio>
            <el-radio :label="2">评标</el-radio>
            <el-radio :label="3">开标/评标</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="容纳人数：">
          <el-input v-model="form.accommodation">
          </el-input>
        </el-form-item>
        <el-form-item label="地点：">
          <el-input v-model="form.address">
          </el-input>
        </el-form-item>
        <el-form-item label="所属公司：">
          <el-select v-model="form.departmentId" placeholder="请选择">
            <el-option
              v-for="item in listCompanies"
              :key="item.objectId"
              :label="item.name"
              :value="item.objectId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="管理员：">
          <el-input v-model="form.admin">
          </el-input>
        </el-form-item>
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submit" :loading="isSubmiting">保存</el-button>
          <el-button class="cancal" @click="close">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
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
            v-model="disableForm.reason">
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
import {department} from '@/api/system'
export default {
  name: '',
  data () {
    return {
      checked: '0',
      messageLike: '',
      businessTypes: [],
      roomTypes: [],
      projectData: [],
      departmentName: '',
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 0, // 总条数
      pageSize: 10, // 每页展示条数
      isSubmiting: false,
      dialogVisible: false,
      isUpdated: false,
      form: {},
      formLabelWidth: '120px',
      dialogTitle: '新增开评标室',
      rules: {
        name: [
          { required: true, message: '请输入开评标室名称', trigger: ['blur', 'change'] }
        ],
        roomType: [
          { required: true, message: '请选择类型', trigger: ['change'] }
        ],
        businessType: [
          { required: true, message: '请选择一项业务', trigger: ['change'] }
        ]
      },
      isDisableSubmiting: false,
      dialogDisableVisible: false,
      disableForm: {
        time: ''
      },
      dialogDisableTitle: '禁用设置',
      disableFormRules: {
        time: [
          { required: true, message: '请选择禁用时间', trigger: ['blur', 'change'] }
        ]
      },
      disableRoomOptions: [],
      isDisableChecked: false,
      checkDisableResult: true,
      record: {},
      listCompanies: []
    }
  },
  watch: {
    dialogVisible (value) {
      if (!value) {
        // 重置页面
        this.form = {}
        this.$refs['form'].resetFields()
      }
    },
    'form.departmentId' (value) {
      if (value) {
        let department = this.listCompanies.find(item => item.objectId === value)
        this.$set(this.form, 'departmentName', department.name)
        this.$set(this.form, 'departmentListIds', department.objectId)
      }
    }
  },
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.getTableData()
    },
    reset () {
      this.checked = '0'
      this.roomTypes = ''
      this.messageLike = ''
      this.departmentName = ''
      this.currentPage = 1
      this.pageNo = 0
      this.getTableData()
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getTableData()
    },
    handleAdd () {
      this.dialogTitle = '新增开评标室'
      this.isUpdated = false
      this.dialogVisible = true
      this.$set(this.form, 'departmentId', this.$store.getters.authUser.departmentComponyId)
    },
    handleUpdate (scope) {
      this.isUpdated = true
      this.form = Object.assign({}, scope.row)
      this.dialogTitle = '修改开评标室'
      this.dialogVisible = true
    },
    handleDel (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        bidOpeningRoom.deleteBidOpeningRoom(scope.row.objectId).then(res => {
          if (res.data.resCode === '0000') {
            this.getTableData()
            this.dialogVisible = false
            this.refreshOrder()
          }
        })
      }).catch(() => {
        return false
      })
    },
    handleBan (scope) {
      this.dialogDisableVisible = true
      this.disableForm.name = scope.row.name
      this.disableForm = Object.assign({}, scope.row)
    },
    handleBanRecord (scope) {
      this.$router.push({path: `/resource/biddingRoom/banRecord/${scope.row.code}`})
    },
    getTableData () {
      let query = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        messageLike: this.messageLike,
        enterpriseId: this.$store.getters.authUser.enterpriseId
      }
      if (Number(this.checked) === 1) {
        this.$set(query, 'businessTypes', '1,3')
      } else if (Number(this.checked) === 2) {
        this.$set(query, 'businessTypes', '2,3')
      } else if (Number(this.checked) === 3) {
        this.$set(query, 'businessTypes', '3')
      }
      if (this.roomTypes.length === 1) {
        this.$set(query, 'roomType', this.roomTypes[0])
      }
      this.$set(query, 'departmentName', this.departmentName === '0' ? null : this.departmentName)
      bidOpeningRoom.getBidOpeningRoomList(query).then(res => {
        this.projectData = res.data.bidOpeningRoomList.list
        this.total = res.data.bidOpeningRoomList.total
      })
    },
    close () {
      this.dialogVisible = false
    },
    submit () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (!this.isUpdated) {
            bidOpeningRoom.checkNameCanUsed({
              name: this.form.name
            }).then((res) => {
              if (!res.data.canUsed) {
                this.$message({
                  type: 'warning',
                  message: '开评标室名称重复'
                })
              } else {
                this.submitData()
              }
            })
            return true
          }
          this.submitData()
        } else {
          return false
        }
      })
    },
    submitData () {
      this.isSubmiting = true
      bidOpeningRoom.saveOrUpdateBidOpeningRoom(this.form).then(res => {
        this.isSubmiting = false
        if (res.data.resCode === '0000') {
          this.getTableData()
          this.dialogVisible = false
          // 新增刷新 ，修改不刷新
          if (!this.isUpdated) {
            this.refreshOrder()
          }
        } else {
          return false
        }
      })
    },
    // 检测是否可预约(禁用设置)
    handleDisableCheckOrder () {
      this.$refs['disableForm'].validate((valid) => {
        if (valid) {
          let query = {
            code: this.disableForm.code,
            objectId: this.record.objectId
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
        } else {
          return false
        }
      })
    },
    closeDisable () {
      this.dialogDisableVisible = false
      this.isDisableChecked = false
      this.disableForm = {}
      this.$refs['disableForm'].resetFields()
    },
    submitDisable () {
      this.$refs['disableForm'].validate((valid) => {
        if (valid) {
          this.form.appointmentStartTime = this.disableForm.time[0]
          this.form.appointmentEndTime = this.disableForm.time[1]
          this.form.bidOpeningRoomCode = this.disableForm.code

          // 将表单中的开评标室信息同步到预约记录中
          this.record.startTime = this.disableForm.time[0]
          this.record.endTime = this.disableForm.time[1]
          this.record.bidOpeningRoomCode = this.disableForm.code
          this.record.remarks = this.disableForm.reason
          this.record.status = 2 // 已禁用
          appointmentRecord.saveOrUpdateAppointmentRecord(this.record).then((res) => {
            if (res.data.resCode === '0000') {
              this.closeDisable()
              this.getTableData()
              this.refreshOrder()
            }
          })
        } else {
          return false
        }
      })
    },
    /**
     * 禁用后刷新预约管理
     */
    refreshOrder () {
      this.$emit('refreshOrder', '')
    },
    /**
     * 初始化分公司
     */
    initCompanies () {
      let queryModel = {
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        types: [2, 4]
      }
      department.queryListByTypes(queryModel).then(res => {
        this.listCompanies = res.data.data
      })
    },
    // 普通格式化数据，空的时候展示"---"
    simpleFormatData (row, col, cellValue) {
      return cellValue || '---'
    }
  },
  mounted () {
    // 初始话分公司
    this.initCompanies()
    this.getTableData()
  }
}
</script>

<style scoped>
  .topmain{
    line-height: 40px;
    color: #111111;
    font-weight: bold;
    padding: 0px;
    margin: 10px auto;
  }
  .topmain .addbutton{
    float: right;
  }
  .search_btn{
    margin-left: 10px;
  }
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
  .el-form-item{
    margin-right: 10px;
    padding-right: 0px!important;
  }
  .selectlist{
    display: inline-block;
    float: left;
  }
</style>
