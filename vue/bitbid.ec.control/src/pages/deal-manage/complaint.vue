<template>
  <div id="complaint-page"  class="cont-view">
    <!-- 面包屑区域start -->
    <div class="bread-box">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>成交管理</el-breadcrumb-item>
        <el-breadcrumb-item>投诉管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 面包屑区域end -->
    <!-- 搜索区域start -->
    <div class="search-box">
      <el-row :gutter="20">
        <el-form :model="searchForm" label-width="110px" class="demo-ruleForm">
          <el-col :span="5">
            <el-form-item label="电商平台名称:">
              <el-select v-model="searchForm.beComplainantId" clearable placeholder="请选择">
                <el-option
                  v-for="item in ecPlatform"
                  :key="item.id"
                  :label="item.userName"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="采购人:">
              <el-input v-model="searchForm.complainantNameLike" placeholder="采购人"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="创建时间:">
              <el-date-picker
                style="width:260px;"
                v-model="searchForm.time"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :default-time="['00:00:00', '23:59:59']"
                value-format="yyyy-MM-dd HH:mm:ss">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" @click="onSearch">查询</el-button>
          </el-col>
        </el-form>
      </el-row>
    </div>
    <!-- 搜索区域end -->
    <div class="table-box">
      <el-table
        :data="tableData"
        border
        style="width: 100%">
        <el-table-column
          label="序号"
          type="index"
          width="60"
          :index="computedIndex"
          align="center">
        </el-table-column>
        <el-table-column
          prop="complainantName"
          label="采购人"
          :formatter="simpleFormatData"
          align="center"
          show-overflow-tooltip
          width="170">
        </el-table-column>
        <el-table-column
          prop="beComplainantName"
          label="电商平台名称"
          :formatter="simpleFormatData"
          show-overflow-tooltip
          align="center">
        </el-table-column>
        <el-table-column
          prop="complainReasons"
          label="投诉理由"
          :formatter="simpleFormatData"
          show-overflow-tooltip
          align="center">
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间"
          :formatter="simpleFormatData"
          align="center"
          width="160">
        </el-table-column>
        <el-table-column
          prop="replyStatus"
          label="回复状态"
          :formatter="simpleFormatData"
          align="center"
          width="80">
          <template slot-scope="scope">
            {{scope.row.replyStatus === 0 ? '未回复' : '已回复'}}
          </template>
        </el-table-column>
        <el-table-column
          prop="replyContent"
          label="回复内容"
          :formatter="simpleFormatData"
          show-overflow-tooltip
          align="center">
        </el-table-column>
        <el-table-column
          prop="replyTime"
          label="回复时间"
          :formatter="simpleFormatData"
          align="center"
          width="160">
        </el-table-column>
        <el-table-column label="操作" align="center"
                         width="100">
          <template slot-scope="scope">
            <el-button
              v-if="scope.row.replyStatus === 0"
              size="mini"
              type="text"
              @click="handleTableBtn(scope.row, 'reply')">
              回复
            </el-button>
            <el-button
              size="mini"
              type="text"
              @click="handleTableBtn(scope.row, 'look')">
              查看
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-box">
        <!--分页-->
        <el-pagination
          background
          layout="prev, pager, next"
          :total="page.total"
          :page-size='page.pageSize'
          :current-page.sync="page.currentPage"
          @current-change="handlePage"
          @next-click="handlePage">
        </el-pagination>
        <!--分页-->
      </div>
    </div>
    <!-- 弹窗区域start -->
    <div class="dialog-box">
      <el-dialog
        :title="dialogTitle"
        :visible.sync="dialogVisible"
        :before-close="close"
        width="40%"
        center>
        <el-form :model="replyForm" :rules="replyRules" ref="replyForm" label-width="140px" class="demo-ruleForm">
          <el-form-item label="采购人：">
            <span>{{replyForm.complainantName}}</span>
          </el-form-item>
          <el-form-item label="投诉电商平台名称：">
            <span>{{replyForm.beComplainantName}}</span>
          </el-form-item>
          <el-form-item label="投诉理由：">
            <el-input type="textarea" v-model="replyForm.complainReasons" :disabled="true" :rows="3"></el-input>
          </el-form-item>
          <el-form-item label="回复内容：" v-if="isHide">
            <el-input type="textarea" v-model="replyForm.replyContent" :disabled="true" :rows="3"></el-input>
          </el-form-item>
          <el-form-item label="回复内容：" prop="replyContent" v-else>
            <el-input type="textarea" v-model="replyForm.replyContent" :disabled="false" :rows="3"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="close">取 消</el-button>
          <el-button type="primary" @click="submitReply" v-if="!isHide">确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <!-- 弹窗区域end -->
  </div>
</template>
<script>
import {complain} from '@/api'
export default {
  name: 'complaint',
  data () {
    return {
      searchForm: {},
      ecPlatform: [], // 电商平台lsit
      tableData: [],
      page: {
        total: 0,
        pageSize: 10,
        pageNo: 0,
        currentPage: 1
      },
      dialogTitle: '投诉查看',
      dialogVisible: false,
      replyForm: {
        replyContent: ''
      },
      replyRules: {
        replyContent: [
          {required: true, message: '请填写回复内容', trigger: ['blur', 'change']}
        ]
      },
      isHide: false
    }
  },
  methods: {
    close () {
      this.dialogVisible = false
      this.replyForm = {}
      this.$refs['replyForm'].resetFields()
    },
    /** 搜索 */
    onSearch () {
      this.page.pageNo = 0
      this.page.currentPage = 1
      this.getTableData()
    },
    handleTableBtn (row, type) {
      this.dialogVisible = true
      this.replyForm = Object.assign({}, row)
      if (Object.is(type, 'look')) {
        this.dialogTitle = '投诉查看'
        this.isHide = true
      } else if (Object.is(type, 'reply')) {
        this.dialogVisible = true
        this.dialogTitle = '投诉回复'
        this.isHide = false
        delete this.replyForm.replyContent
      }
    },
    /** 分页 */
    handlePage (nowNum) {
      this.page.currentPage = nowNum
      this.page.pageNo = (nowNum - 1) * this.page.pageSize
      this.getTableData()
    },
    getTableData () {
      this.searchForm.pageNo = this.page.pageNo
      this.searchForm.pageSize = this.page.pageSize
      if (this.searchForm.time && this.searchForm.time.length > 1) {
        this.searchForm.createTimeBegin = this.searchForm.time[0]
        this.searchForm.createTimeEnd = this.searchForm.time[1]
      } else {
        delete this.searchForm.createTimeBegin
        delete this.searchForm.createTimeEnd
      }
      let queryModel = Object.assign({}, this.searchForm)
      delete queryModel.time
      complain.queryList(queryModel).then(res => {
        this.tableData = res.data.complainPageInfo.list
        this.page.total = res.data.complainPageInfo.total
      })
    },
    getEcPlatformData () {
      complain.merchantName().then(res => {
        this.ecPlatform = res.data.users
      })
    },
    /** 回复确认 */
    submitReply () {
      this.$refs['replyForm'].validate(valid => {
        if (valid) {
          complain.update(this.replyForm).then(res => {
            if (res.data.resCode === '0000') {
              this.close()
              this.getTableData()
            }
          })
        } else {
          return false
        }
      })
    },
    /** 普通格式化数据，空的时候展示"---" */
    simpleFormatData (row, col, cellValue) {
      return cellValue || '-----'
    },
    /** 序号计算 */
    computedIndex (index) {
      return index + (this.page.currentPage - 1) * this.page.pageSize + 1
    }
  },
  mounted () {
    this.getEcPlatformData()
    this.getTableData()
  }
}
</script>
<style lang="less">
#complaint-page{
}
.dialog-box {
  .el-form-item__content {
    text-align: left;
  }
}
</style>
