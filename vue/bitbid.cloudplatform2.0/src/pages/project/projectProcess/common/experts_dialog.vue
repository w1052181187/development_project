<template>
  <div>
    <el-dialog title="选择专家" width="600px" :before-close="cancel" :visible.sync="showVisible">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="本专业" name="first">
          <div class="selectbox">
            <el-form :model="queryModel"  label-width="100px" :validate-on-rule-change="true">
              <el-row>
                <el-col :span="20">
                  <el-form-item label="专家：">
                    <el-input class="search" v-model="queryModel.messageLike" placeholder="请输入专家姓名">
                      <el-button slot="append" icon="el-icon-search" @click="getTableData"></el-button>
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </div>
          <el-table
            :data="tableData"
            border
            style="width: 100%" header-cell-class-name="tableheader">
            <el-table-column
              type="index"
              label="序号"
              width="60"
              :index="index"
              align="center">
            </el-table-column>
            <el-table-column
              prop="name"
              label="专家姓名"
              :formatter="simpleFormatData"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="idNumber"
              label="身份证号码"
              :formatter="simpleFormatData"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="grade"
              label="专家评分"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="radio"
              label="操作"
              align="center"
              width="90">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="selected(scope.row)">选择</el-button>
              </template>
            </el-table-column>
          </el-table>
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
        </el-tab-pane>
        <el-tab-pane label="全部在库" name="second">
          <div class="selectbox">
            <el-form :model="queryAllModel"  label-width="100px" :validate-on-rule-change="true">
              <el-row>
                <el-col :span="20">
                  <el-form-item label="专家：">
                    <el-input class="search" v-model="queryAllModel.messageLike" placeholder="请输入专家姓名">
                      <el-button slot="append" icon="el-icon-search" @click="getTableAllData"></el-button>
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </div>
          <el-table
            :data="tableAllData"
            border
            style="width: 100%" header-cell-class-name="tableheader">
            <el-table-column
              type="index"
              label="序号"
              width="60"
              :index="computedIndex"
              align="center">
            </el-table-column>
            <el-table-column
              prop="name"
              label="专家姓名"
              :formatter="simpleFormatData"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="idNumber"
              label="身份证号码"
              :formatter="simpleFormatData"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="grade"
              label="专家评分"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="radio"
              label="操作"
              align="center"
              width="90">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="selected(scope.row)">选择</el-button>
              </template>
            </el-table-column>
          </el-table>
          <!--分页-->
          <el-pagination
            background
            layout="prev, pager, next"
            :total="pageAll.total"
            :page-size='pageAll.pageSize'
            :current-page.sync="pageAll.currentPage"
            @current-change="handlePageAll"
            @next-click="handlePageAll">
          </el-pagination>
          <!--分页-->
        </el-tab-pane>
        <el-tab-pane label="新增专家" name="third">
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
  </div>
</template>
<script>
import { expert } from '@/api/resource'
import {bidSection} from '@/api/project'
export default {
  data () {
    return {
      activeName: 'first',
      page: {
        pageSize: 5,
        pageNo: 0,
        total: null, // 总条数
        currentPage: 1
      },
      pageAll: {
        pageSize: 5,
        pageNo: 0,
        total: null, // 总条数
        currentPage: 1
      },
      queryModel: {
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        messageLike: ''
      },
      queryAllModel: {
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        messageLike: ''
      },
      tableData: [],
      tableAllData: []
    }
  },
  props: ['showVisible', 'selectedCode', 'sectionSystemCode', 'excludeCodes'],
  watch: {
    'excludeCodes': 'init'
  },
  methods: {
    // 获取列表数据,稍作延迟
    getTableData () {
      bidSection.getOne(this.sectionSystemCode).then(res => {
        this.bidSection = res.data.bidSection
        this.queryModel.pageNo = this.page.pageNo
        this.queryModel.pageSize = this.page.pageSize
        this.queryModel.expertCategoryLike = this.bidSection.tenderProjectClassifyCode
        this.queryModel.excludeCodes = this.excludeCodes
        expert.queryList(this.queryModel).then(res => {
          let data = res.data.data
          if (data) {
            this.tableData = data.list
            this.page.total = data.total
          }
        })
      })
    },
    getTableAllData () {
      this.queryAllModel.pageNo = this.pageAll.pageNo
      this.queryAllModel.pageSize = this.pageAll.pageSize
      this.queryAllModel.excludeCodes = this.excludeCodes
      expert.queryList(this.queryAllModel).then(res => {
        let data = res.data.data
        if (data) {
          this.tableAllData = data.list
          this.pageAll.total = data.total
        }
      })
    },
    // 序号计算
    index (index) {
      return index + (this.page.currentPage - 1) * this.page.pageSize + 1
    },
    // 序号计算
    computedIndex (index) {
      return index + (this.pageAll.currentPage - 1) * this.pageAll.pageSize + 1
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
    // 表单分页
    handlePageAll (nowNum) {
      this.pageAll.currentPage = nowNum
      this.pageAll.pageNo = (nowNum - 1) * this.pageAll.pageSize
      this.getTableAllData()
    },
    // 选择
    selected (obj) {
      this.$emit('selected', obj)
      this.cancel()
    },
    // 取消
    cancel () {
      this.activeName = 'first'
      this.$emit('showDialog')
    },
    // 新增专家
    handleClick (tab, event) {
      if (tab.name === 'first') {
        this.getTableData()
      } else if (tab.name === 'second') {
        this.getTableAllData()
      } else if (tab.name === 'third') {
        this.activeName = 'second'
        this.cancel()
        this.$router.push('/resource/expert/add')
      }
    },
    init () {
      // 列表初始化
      if (Object.is(this.activeName, 'first')) {
        this.getTableData()
      } else if (Object.is(this.activeName, 'second')) {
        this.getTableAllData()
      }
    }
  },
  mounted () {
    this.init()
  }
}
</script>
<style scoped>
  .selectbox{
    background: #f7f8f9;
    padding: 10px 0;
    margin-bottom: 0px;
  }
</style>
