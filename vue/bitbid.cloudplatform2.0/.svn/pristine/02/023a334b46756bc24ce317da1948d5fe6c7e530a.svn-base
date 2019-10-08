<template>
  <div>
    <el-dialog title="选择评估项" :before-close="cancel" :visible.sync="showVisible" width="600px">
      <div class="selectbox">
        <el-form :model="queryModel" ref="searchForm" label-width="80px" :validate-on-rule-change="true">
          <el-row>
            <el-col :span="20">
              <el-form-item label="评估项：">
                <el-input class="search" v-model="queryModel.messageLike" placeholder="请输入评估项"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-button  type="primary" @click="getTableData">搜索</el-button>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <el-form>
        <el-table
          ref="table"
          :data="tableData"
          border
          style="width: 100%"
          @selection-change="handleSelectionChange" v-loading="loading">
          <el-table-column
            align="center"
            type="selection"
            width="60">
          </el-table-column>
          <el-table-column
            align="center"
            type="index"
            label="序号"
            width="60">
          </el-table-column>
          <el-table-column
            align="center"
            prop="name"
            label="评估项">
          </el-table-column>
          <el-table-column
            align="center"
            prop="parent.name"
            label="评估项分组">
          </el-table-column>
        </el-table>
        <el-row class="bottom_sel">
          <el-col :span="24">
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
          </el-col>
        </el-row>
        <el-form-item>
          <el-button type="primary" @click="submit" >确认</el-button>
          <el-button class="cancal" @click="cancel" >取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import {templateItem} from '@/api/customer'
export default {
  data () {
    return {
      page: {
        pageSize: 8,
        pageNo: 1,
        total: 0, // 总条数
        currentPage: 1
      },
      queryModel: {
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        excludeParentId: 0,
        containParent: true,
        isDelete: 0,
        messageLike: '',
        excludeIds: []
      },
      tableData: [],
      selectItemsList: [],
      selectItemsIds: [],
      loading: false
    }
  },
  props: ['showVisible', 'selectedIds'],
  watch: {
    showVisible (value) {
      if (this.$refs['table']) {
        this.$refs['table'].clearSelection()
      }
      if (value) {
        this.queryModel.excludeIds = this.selectedIds
        this.getTableData()
      }
    }
  },
  methods: {
    getTableData () {
      this.loading = true
      this.queryModel.pageNo = this.page.pageNo
      this.queryModel.pageSize = this.page.pageSize
      templateItem.queryList(this.queryModel).then(res => {
        this.loading = false
        this.queryModel.pageNo = this.page.pageNo
        this.tableData = res.data.data.list
        this.page.total = res.data.data.total
      })
    },
    handleSelectionChange (val) {
      this.selectItemsList = val
    },
    // 提交
    submit () {
      this.$emit('submitSelected', this.selectItemsList)
    },
    // 取消
    cancel () {
      this.$emit('showEditDialog')
    },
    // 表单分页
    handlePage (nowNum) {
      this.page.currentPage = nowNum
      this.page.pageNo = (nowNum - 1) * this.page.pageSize
      this.getTableData()
    }
  }
}
</script>
<style scoped>
  .selectbox{
    background: #f7f8f9;
    padding: 0;
    margin-bottom: 0;
  }
</style>
