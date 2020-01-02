<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="record-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>抽签系统</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/draw/drawrecord' }">选聘备案申请</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="maincont-box record-cont">
      <!-- 搜索条件区域 -->
      <div class="search-warp">
        <el-row type="flex" justify="start">
          <el-col :span="5" style="width:300px">
            <div class="search search-name">
              <el-form ref="form1" :model="form1" label-width="90px">
                <el-form-item label="项目名称：">
                  <el-input v-model="form1.projectName" prepend="项目名称" :maxlength="70" placeholder="请输入项目名称"></el-input>
                </el-form-item>
              </el-form>
            </div>
          </el-col>
          <el-col :span="6" style="width:320px">
            <div class="search search-type">
              <el-form ref="form1" :model="form1" label-width="120px">
                <el-form-item label="选聘中介类型：">
                  <el-select v-model="form1.type" clearable placeholder="请选择" class="search-sel">
                    <el-option
                      v-for="item in typeList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-form>
            </div>
          </el-col>
          <el-col :span="5" style="width:320px">
            <div class="search search-state">
              <el-form ref="form1" :model="form1" label-width="100px">
                <el-form-item label="审批状态：">
                  <el-select v-model="form1.approveStatusInfo" clearable placeholder="请选择" class="search-sel">
                    <el-option
                      v-for="item in stateList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-form>
            </div>
          </el-col>
          <el-col :span="6" style="width:120px">
            <div class="search search-btn">
              <el-button type="primary" @click="onSearch" icon="el-icon-search">搜索</el-button>
            </div>
          </el-col>
        </el-row>
      </div>
      <!-- 搜索条件区域结束 -->
      <!-- 添加按钮   -->
      <div class="record-add">
        <el-button type="warning" icon="el-icon-plus" @click="dialogFormVisible=true" v-if="$store.getters.permissions.includes('/draw/drawrecord/operation')">新增</el-button>
      </div>
      <!-- 添加按钮结束   -->
      <!-- 表格区域 -->
      <div class="record-tab">
        <el-table
          :data="tableData"
          :row-class-name="tableRowClassName"
          border
          style="width: 100%">
          <el-table-column
            type="index"
            label="序号"
            align="center"
            width="55"
            :index="indexMethod">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="projectName"
            label="项目名称">
          </el-table-column>
          <el-table-column
            align="center"
            prop="type"
            label="选聘中介类型"
            :formatter="filterType">
          </el-table-column>
          <el-table-column
            align="center"
            prop="createDate"
            label="申请时间"
            :formatter="filterDate">
          </el-table-column>
          <el-table-column
            align="center"
            prop="approveStatus"
            label="审批状态"
            :formatter="filterApproveStatus">
          </el-table-column>
          <el-table-column
            align="center"
            width="160"
            label="操作">
            <template slot-scope="scope">
              <el-button @click="handleClick(scope.row,'edit')" type="text" size="medium" v-if="$store.getters.permissions.includes('/draw/drawrecord/operation') && (scope.row.approveStatus === 1 || scope.row.approveStatus === 6)">编辑</el-button>
              <el-button @click="handleClick(scope.row,'look')" type="text" size="medium" v-if="$store.getters.permissions.includes('/draw/drawrecord/detail')">查看</el-button>
              <el-button @click="handleClick(scope.row,'del')" type="text" size="medium" v-if="$store.getters.permissions.includes('/draw/drawrecord/operation') && (scope.row.approveStatus === 1 || scope.row.approveStatus === 7)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--分页-->
        <div class="block-page">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-sizes="pageSizeList"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
          </el-pagination>
        </div>
        <!--分页-->
      </div>
      <!-- 表格区域结束 -->
      <!-- 弹窗区域 -->
      <div class="record-dialog">
        <!-- 新增和编辑弹窗 -->
        <el-dialog title="新增" :visible.sync="dialogFormVisible" width="30%" @close="cancel">
          <el-form :model="formInfo" :rules="formRules" ref="formInfo">
            <el-form-item label="中介类型:" label-width="100px" prop="type">
              <el-select v-model="formInfo.type" placeholder="请选择中介类型" style="width:90%" @change="getAllPlanInfo">
                <el-option
                  v-for="item in typeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="招标计划:" label-width="100px" prop="bidPlan" v-if="formInfo.type === 1" >
              <el-select v-model="formInfo.bidPlan" filterable placeholder="请选择招标计划" style="width:90%">
                <el-option
                  v-for="item in bidPlanList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="cancel">取 消</el-button>
            <el-button type="primary" @click="submitRecord(formInfo)">确 定</el-button>
          </div>
        </el-dialog>
      </div>
      <!-- 弹窗区域结束 -->
    </div>
  </div>
</template>

<script>
import {drawrecord, planproject} from 'api/index'
import {formatDate} from 'common/js/date'
// 实例
export default {
  data () {
    return {
      bidPlanList: [], // 招标计划列表
      form1: {},
      typeList: [
        {
          label: '代理机构选聘',
          value: 1
        },
        {
          label: '咨询造价机构',
          value: 2
        },
        {
          label: '审计评估机构',
          value: 3
        },
        {
          label: '法律服务机构',
          value: 4
        }],
      typeId: '',
      stateList: [
        {
          label: '未提交',
          value: '1'
        },
        {
          label: '审核中',
          value: '2,3,4'
        },
        {
          label: '已通过',
          value: '5'
        },
        {
          label: '退回',
          value: '6'
        },
        {
          label: '终止',
          value: '7'
        }
      ],
      tableData: [],
      dialogFormVisible: false, // 新增-选择中介类型
      dialogDelVisible: false, // 删除弹窗
      formInfo: {},
      pageSize: 10,
      pageNo: 0,
      currentPage: 1,
      total: 0, // 总条数
      pageSizeList: [10, 20, 30, 40, 50],
      formRules: {
        type: [
          { required: true, message: '请选择中介类型', trigger: 'change' }
        ],
        bidPlan: [
          { required: true, message: '请选择招标计划', trigger: 'change' }
        ]
      }
    }
  },
  computed: {},
  // 实例创建
  created () {
    this.drawrecordData()
  },
  filters: {
  },
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 获取招标计划信息 */
    getAllPlanInfo () {
      if (this.formInfo.type === 1) {
        this.bidPlanList = []
        planproject.getOptionalProjectPlan(this.$store.getters.authUser.enterpriseId).then((res) => {
          res.data.optionalprojectPlanList.forEach(item => {
            this.bidPlanList.push({
              value: item.objectId,
              label: item.projectPlanName
            })
          })
        })
      }
    },
    /** 选聘中介类型的选择 */
    filterType (row) {
      if (row.type === 1) {
        return '代理机构选聘'
      } else if (row.type === 2) {
        return '咨询造价机构'
      } else if (row.type === 3) {
        return '审计评估机构'
      } else if (row.type === 4) {
        return '法律服务机构'
      }
    },
    /** 格式化申请时间 */
    filterDate (row, column) {
      let date = ''
      if (row.createDate && column.property === 'createDate') {
        date = new Date(row.createDate)
        return formatDate(date, 'yyyy-MM-dd hh:mm')
      }
    },
    /** 审批状态的选中 */
    filterApproveStatus (row) {
      if (row.approveStatus === 1) {
        return '未提交'
      } else if (row.approveStatus === 2 || row.approveStatus === 3 || row.approveStatus === 4) {
        return '审核中'
      } else if (row.approveStatus === 5) {
        return '已通过'
      } else if (row.approveStatus === 6) {
        return '退回'
      } else if (row.approveStatus === 7) {
        return '终止'
      }
    },
    indexMethod (index) {
      //  进行编辑，查看等操作后返回到当前页
      if (this.$route.query.pageNo && this.$route.query.pageSize && this.$route.query.currentPage) {
        this.currentPage = parseInt(this.$route.query.currentPage)
        this.$route.query.currentPage = ''
      }
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 表单分页 */
    handleSizeChange (val) {
      var likeThis = this
      var pageSize = `${val}`
      this.pageNo = 0
      this.currentPage = 1
      this.pageSize = parseInt(pageSize)
      this.$nextTick(() =>
        this.drawrecordData(this.pageNo, this.pageSize, function (resp) {
          likeThis.total = resp.data.selectApplyList.total
        })
      )
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.drawrecordData(parseInt(this.pageNo), parseInt(this.pageSize))
    },
    /** 列表数据展示 */
    drawrecordData () {
      drawrecord.getList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        messageLike: this.form1.projectName || null,
        approveStatuses: this.form1.approveStatusInfo || null,
        type: this.form1.type || null,
        enterpriseId: this.$store.getters.authUser.enterpriseId
      }).then((res) => {
        this.tableData = res.data.selectApplyList.list
        this.total = res.data.selectApplyList.total
      })
    },
    /** 搜索 */
    onSearch () {
      this.drawrecordData()
    },
    /** 表格操作 */
    handleClick (data, type) {
      this.formInfo = {}
      if (type === 'del') {
        this.deleteDrawRecordInfo(data)
      } else if (type === 'edit') {
        if (data.type === 1) {
          this.$router.push({path: '/draw/apply', query: {name: 'edit', objectId: data.objectId}})
        } else if (data.type === 2 || data.type === 3 || data.type === 4) {
          this.$router.push({path: '/draw/interapply', query: {name: 'edit', objectId: data.objectId}})
        }
      } else if (type === 'look') {
        if (data.type === 1) {
          this.$router.push({path: '/draw/details', query: {name: 'details', objectId: data.objectId, flag: 'record'}})
        } else if (data.type === 2 || data.type === 3 || data.type === 4) {
          this.$router.push({path: '/draw/indetails', query: {name: 'indetails', objectId: data.objectId, flag: 'record'}})
        }
      }
    },
    /** 确定 */
    submitRecord (formInfo) {
      if (formInfo.type) {
        if (formInfo.type === 1 && formInfo.bidPlan) {
          this.$router.push({path: '/draw/apply', query: {bidPlanId: this.formInfo.bidPlan}})
        } else if (formInfo.type === 2 || formInfo.type === 3 || formInfo.type === 4) {
          this.$router.push({path: '/draw/interapply', query: {type: formInfo.type}})
        }
      }
    },
    /** 取消 */
    cancel () {
      this.dialogFormVisible = false
      this.formInfo = {}
    },
    /** 删除选聘备案申请信息 */
    deleteDrawRecordInfo (data) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        drawrecord.delete(data.objectId).then(() => {
          this.drawrecordData()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  },
  // 实例创建完成
  mounted () {
  }
}
</script>
<style lang="less">
#record-page {
  .record-add {
    text-align: left;
  }
  .block-page {
    padding: 20px;
  }
  .record-tab {
    padding-top: 20px;
  }
  /** 弹窗 */
  .record-dialog {
    .el-dialog__footer {
      text-align: center;
    }
    .el-select-dropdown__item {
      width: 260px;
    }
  }
  .el-select-dropdown {
    width: 230px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  
}
.el-scrollbar .el-select-dropdown__item {
  width: 260px;
}
</style>
