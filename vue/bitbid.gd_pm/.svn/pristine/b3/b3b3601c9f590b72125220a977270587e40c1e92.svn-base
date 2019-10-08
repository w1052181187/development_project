<template>
  <div class="center_box" id="small_center_box">
    <div class="con_title">系统通知</div>
    <div class="con_table">
      <!--上边功能-->
      <div class="con_top_box">
        <!--添加按钮-->
        <el-button type="primary" size="medium" class="add_btn" @click="addBtn">新建通知</el-button>
        <!--添加弹框-->
        <el-dialog title="新建通知" :visible.sync="detailVisible" width="40%" :before-close="handleClose">
          <el-form :model="noticeForm" :rules="rules" ref="noticeForm">
            <el-form-item label="标题：" label-width="100px" prop="title">
              <el-input v-model="noticeForm.title"></el-input>
            </el-form-item>
            <el-form-item label="内容：" label-width="100px" prop="content">
              <el-input type="textarea" v-model="noticeForm.content"></el-input>
            </el-form-item>
          </el-form>
          <div class="detail-dialog-box" style="text-align: center">
            <el-button type="primary" size="medium" class="system_notice_savebtn" @click="saveBtn('noticeForm')">保存</el-button>
          </div>
        </el-dialog>
        <!--添加弹框-->
        <!--添加按钮-->
      </div>
      <!--上边功能-->
      <!--列表数据-->
      <el-table
        :data="projectTableData"
        border
        header-cell-class-name="table_header">
        <el-table-column
          prop="createDate"
          label="时间"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="title"
          label="标题"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="content"
          label="内容"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="publishStatus"
          label="状态"
          align="left"
          :formatter="statusFormatter"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          label="操作" width="210" align="left">
          <template slot-scope="scope">
            <el-button type="text" size="small" class="handle_btn" @click="updateBtn(scope)" v-if="scope.row.publishStatus !== 1">
              修改
            </el-button>
            <el-button type="text" size="small" class="handle_btn" @click="deleteBtn(scope)" v-if="scope.row.publishStatus !== 1">
              删除
            </el-button>
            <el-button type="text" size="small" class="handle_btn" @click="releaseBtn(scope)" v-if="scope.row.publishStatus !== 1">
              发布
            </el-button>
            <el-button type="text" size="small" class="handle_btn" @click="withdrawBtn(scope)" v-if="scope.row.publishStatus === 1">
              撤回
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--列表数据-->
      <!--分页-->
      <div class="page_box">
        <el-pagination
          class="pagebox"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :total="total"
          :page-size='pageSize'
          layout="prev, pager, next, jumper">
        </el-pagination>
      </div>
      <!--分页-->
    </div>
  </div>
</template>

<script>
import {smallProgram} from '@/api'
export default {
  name: '',
  data () {
    return {
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      detailVisible: false, // 新建通知
      projectTableData: [],
      noticeForm: {
        title: '',
        content: ''
      }, // 弹框from
      isUpdate: false,
      rules: {
        title: [
          {required: true, message: '请输入标题', trigger: ['blur', 'change']},
          {min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: ['blur', 'change']}
        ],
        content: [
          {required: true, message: '请输入内容', trigger: ['blur', 'change']},
          {min: 1, max: 45, message: '长度在 1 到 45 个字符', trigger: ['blur', 'change']}
        ]
      }
    }
  },
  methods: {
    // ***************************************************************接口方法**********************************************************************
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    // 列表显示
    list () {
      let url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0
      }
      smallProgram.queryList(url).then((res) => {
        this.projectTableData = res.data.proSysNoticePageInfo.list
        this.total = res.data.proSysNoticePageInfo.total
      })
    },
    // 保存
    saveBtn (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          if (!this.isUpdate) { // 添加保存
            smallProgram.saveList(this.noticeForm).then((res) => {
              if (res.data.resCode === '0000') {
                this.list()
                this.detailVisible = false
                this.noticeForm = {}
                this.$refs['noticeForm'].resetFields()
              }
            })
          } else { // 修改保存
            smallProgram.updateList(this.noticeForm).then((res) => {
              if (res.data.resCode === '0000') {
                this.list()
                this.detailVisible = false
                this.noticeForm = {}
                this.$refs['noticeForm'].resetFields()
              }
            })
          }
        } else {
          return false
        }
      })
    },
    // 删除
    deleteBtn (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        customClass: 'cooperative-agency-delbtn',
        type: 'error'
      }).then(() => {
        smallProgram.delectList(scope.row.objectid).then((res) => {
          this.list()
        })
      }).catch(() => {
        return false
      })
    },
    // 修改
    updateBtn (scope) {
      this.detailVisible = true
      this.isUpdate = true
      smallProgram.detailQuery(scope.row.objectid).then((res) => { // 查询单个
        this.noticeForm = res.data.miniProSysNotice
      })
    },
    // 发布
    releaseBtn (scope) {
      smallProgram.releaseQuery(scope.row.objectid).then((res) => { // 发布
        this.list()
      })
    },
    // 撤回
    withdrawBtn (scope) {
      smallProgram.withdrawQuery(scope.row.objectid).then((res) => { // 撤回
        this.list()
      })
    },
    // ***************************************************************自己方法**********************************************************************
    // 添加弹框
    addBtn () {
      this.detailVisible = true
      this.isUpdate = false
    },
    // 关闭弹框
    handleClose () {
      this.detailVisible = false
      this.noticeForm = {}
      this.$refs['noticeForm'].resetFields()
    },
    // 状态
    statusFormatter (row, column) {
      switch (row.publishStatus) {
        case 0 :
          return '未发布'
        case 1 :
          return '已发布'
        case 2 :
          return '已撤回'
      }
    }
  },
  mounted () {
    this.list()
  }
}
</script>

<style lang="less">
  #small_center_box{
    .system_notice_savebtn{
      padding: 0;
      width: 90px;
      height: 40px;
      line-height: 40px;
      border-radius: 2px;
      background:#009dff;
    }
    .system_notice_savebtn:focus, .system_notice_savebtn:hover {
      color: #fff;
      border-color: #009dff;
      background-color: #009dff;
    }
    .el-dialog__header{
      text-align: center;
    }
    .el-dialog__title {
      line-height: 24px;
      font-size: 18px;
      color: #303133;
    }
  }
  .cooperative-agency-delbtn .el-message-box__message {
    padding-left: 36px!important;
    padding-right: 12px;
  }
</style>
