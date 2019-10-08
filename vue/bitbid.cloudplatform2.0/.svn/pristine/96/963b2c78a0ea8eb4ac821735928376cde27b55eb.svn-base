<template>
  <!-- 弹窗区域start -->
  <div class="dialog-box">
    <el-dialog
      title="添加合作信息"
      :visible.sync="showVisible"
      width="50%"
      :before-close="cancel"
      center>
      <div class="search-box" style="overflow: hidden">
        <el-form :model="queryModel" ref="queryModel" class="demo-submitForm">
          <div style="float: left">
            <span>项目名称:</span>
            <el-input v-model="queryModel.projectNameLike" style="width:160px;" placeholder="请输入项目名称" cleared></el-input>
            <el-button  type="primary" class="primary" @click="getTableData">查询</el-button>
          </div>
          <label style="float:right;">
            <span>找不到符合条件的项目</span>
            <el-button type="text" @click="toAddProject">添加项目</el-button>
          </label>
        </el-form>
      </div>
      <el-form :model="submitForm" ref="submitForm" class="demo-submitForm">
        <el-table
          :data="submitForm.tableData"
          border
          style="width: 100%"
          :loading="loading"
          header-cell-class-name="tableheader"
          @selection-change="handleSelectionChange"
          >
          <el-table-column
            type="selection"
            width="80"
            align="center">
          </el-table-column>
          <el-table-column
            prop="bidSectionCode"
            label="标段编号"
            align="center"
            :formatter="simpleFormatData"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="bidSectionName"
            label="标段名称"
            align="center"
            :formatter="simpleFormatData"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="tenderProjectName"
            label="所属项目"
            align="center"
            :formatter="simpleFormatData"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            label="录入评审时间"
            align="center">
            <template slot-scope="scope">
              <el-form-item :prop="'tableData.' + scope.$index + '.reviewTime'" v-if="selectSectionIds.includes(scope.row.objectId)" :rules="rules.reviewTime" style="margin-bottom:0;">
                <el-date-picker
                  v-model="scope.row.reviewTime"
                  type="date"
                  placeholder="选择日期"
                  @change="validateDate"
                  style="width:140px;">
                </el-date-picker>
              </el-form-item>
              <el-form-item :prop="'tableData.' + scope.$index + '.reviewTime'" v-if="!selectSectionIds.includes(scope.row.objectId)" style="margin-bottom:0;">
                <el-date-picker
                  v-model="scope.row.reviewTime"
                  type="date"
                  placeholder="选择日期"
                  style="width:140px;">
                </el-date-picker>
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <!--分页-->
      <el-pagination
        class="project_page"
        background
        layout="prev, pager, next"
        :total="page.total"
        :page-size='page.pageSize'
        :current-page.sync="page.currentPage"
        @current-change="handlePage"
        @next-click="handlePage">
      </el-pagination>
      <!--分页-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submit" v-loading="submitLoading">确 定</el-button>
      </span>
    </el-dialog>
  </div>
  <!-- 弹窗区域end -->
</template>
<script>
import {bidSection} from '@/api/project'
import {expertProject} from '@/api/resource'
import { Message } from 'element-ui'
import {dateFormat} from '@/assets/js/common'
export default {
  data () {
    return {
      loading: false,
      submitLoading: false,
      queryModel: {
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        expertCode: this.relatedCode,
        isDelete: 0
      },
      excludeCodes: [],
      page: {
        pageSize: 5,
        pageNo: 0,
        total: 0, // 总条数
        currentPage: 1
      },
      rules: {
        reviewTime: [
          { required: true, message: '输入不能为空', trigger: ['blur', 'change'] }
        ]
      },
      submitForm: {
        tableData: []
      },
      selectSectionIds: []
    }
  },
  props: ['showVisible', 'relatedCode'],
  watch: {
    showVisible (value) {
      if (value) {
        this.initData()
      } else {
        this.$refs['submitForm'].resetFields()
      }
    }
  },
  methods: {
    /** 获取列表数据 */
    initData () {
      // 先查询需要排除的合作单位
      expertProject.queryIncludeSectionCodes(this.relatedCode).then(res => {
        if (res.data.data && res.data.data.length) {
          this.queryModel.excludeCodes = res.data.data
        }
        this.getTableData()
      })
    },
    /** 获取标段列表信息 */
    getTableData () {
      this.loading = true
      this.queryModel.pageNo = this.page.pageNo
      this.queryModel.pageSize = this.page.pageSize
      bidSection.getList(this.queryModel).then(res => {
        this.loading = false
        this.submitForm.tableData = res.data.bidSectionList.list
        this.page.total = res.data.bidSectionList.total
      })
    },
    /** 选择 */
    handleSelectionChange (value) {
      this.selectSectionIds = value.map(item => item.objectId)
    },
    /** 提交 */
    submit () {
      this.$refs['submitForm'].validate((valid) => {
        if (valid) {
          if (this.selectSectionIds.length) {
            this.$confirm('确认要提交吗?', '提示', {
              confirmButtonText: '确认',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.submitLoading = true
              let submitProjects = []
              this.submitForm.tableData.forEach(item => {
                if (this.selectSectionIds.includes(item.objectId)) {
                  submitProjects.push({
                    expertCode: this.relatedCode,
                    tenderSystemCode: item.tenderSystemCode,
                    sectionSystemCode: item.code,
                    tenderProjectName: item.tenderProjectName,
                    bidSectionCode: item.bidSectionCode,
                    bidSectionName: item.bidSectionName,
                    tenderProjectClassifyCode: item.tenderProjectClassifyCode,
                    reviewTime: dateFormat(item.reviewTime, 'yyyy-MM-dd hh:mm:ss')
                  })
                }
              })
              expertProject.saveBatch(submitProjects).then(res => {
                this.submitLoading = false
                if (res.data.resCode === '0000') {
                  this.$emit('saveSuccess')
                  this.cancel()
                }
              })
            }).catch(() => {
              this.loading = false
              return false
            })
          } else {
            Message({
              showClose: true,
              message: '请至少选择一项',
              type: 'warning',
              duration: 3 * 1000
            })
          }
        }
      })
    },
    /** 去添加项目 */
    toAddProject () {
      this.$router.push({path: `/project/registProject/add`})
    },
    /** 数据改变，验证 */
    validateDate () {
      this.$refs['submitForm'].validate()
    },
    // 普通格式化数据，空的时候展示"---"
    simpleFormatData (row, col, cellValue) {
      return cellValue || '---'
    },
    // 表单分页
    handlePage (nowNum) {
      this.page.currentPage = nowNum
      this.page.pageNo = (nowNum - 1) * this.page.pageSize
      this.getTableData()
    },
    // 关闭
    cancel () {
      this.$emit('showDialog')
    }
  }
}
</script>
<style scoped>
</style>
