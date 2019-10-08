<template>
  <div class="cloudcontent">
    <!--搜索 & 添加按钮-->
    <div class="topmain">
      <div class="seacher_box advancedsearch_box">
        <span>资质类别：</span>
        <el-select v-model="type" class="select" clearable>
          <el-option
            v-for="item in typeOptions"
            :key="item.objectId"
            :label="item.name"
            :value="item.objectId">
          </el-option>
        </el-select>
        <span>资质来源：</span>
        <span :class="isSourceTypeSelectAll ? 'all selectall': 'all'" @click="conditionCancel('资质来源')">不限</span>
        <el-radio-group v-model="sourceType" class="check_group" @change="conditionSelect($event, '资质来源')">
          <el-radio label="1" :checked="sourceTypeFlag">自有</el-radio>
          <el-radio label="2" :checked="sourceTypeFlag">借入</el-radio>
        </el-radio-group >
        <div class="handBtn" v-if="!showselect">
          <el-button type="primary" class="search" @click="search">查询</el-button>
          <el-button @click="reset">重置</el-button>
          <span @click="showselect = true" class="more">展开搜索条件</span>
        </div>
        <div class="advancedsearch" v-if="showselect">
          <el-form :model="searchForm" ref="searchForm" label-width="90px" :validate-on-rule-change="true">
            <el-row>
              <span>资质编号：</span>
              <el-input placeholder="请输入资质编号关键字" v-model="search_numberLike" clearable></el-input>
              <span>资质主体：</span>
              <el-input placeholder="请输入资质主体关键字" v-model="search_subjectLike" clearable></el-input>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="到期时间：" style="float: left; margin-right: 20px;" label-width="82px">
                  <span :class="isProjectTimeSelectAll ? 'all selectall': 'all'" @click="conditionCancel('到期时间')">不限</span>
                  <el-radio-group v-model="searchForm.projectTime" @change="conditionSelect($event, '到期时间')">
                    <el-radio v-for="item in projectTimeOption" :label="item.code" :key="item.code" :checked="projectTimeFlag">{{item.name}}</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-date-picker
                  v-model="searchForm.projectCustomTime"
                  v-if="searchForm.projectTime === 6"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  :default-time="['00:00:00', '23:59:59']"
                  value-format="yyyy-MM-dd HH:mm:ss">
                </el-date-picker>
              </el-col>
            </el-row>
          </el-form >
          <div class="handBtn">
            <el-button  type="primary" class="search" @click="search">查询</el-button>
            <el-button  @click="reset">重置</el-button>
            <span @click="showselect = false" class="more">收起搜索条件</span>
          </div>
        </div>
      </div>
    </div>
    <!--搜索 & 添加按钮-->
    <div class="main">
      <el-button type="primary" class="addbutton" @click="handleAdd" v-if="$store.getters.permissions.includes('1020202')">
        <span>登记资质</span>
      </el-button>
      <el-table
        :data="tableData"
        @cell-click="onclickBtnData"
        border
        header-cell-class-name="tableheader">
        <el-table-column
          type="index"
          label="序号"
          align="center"
          :index="indexMethod"
          width="80">
        </el-table-column>
        <el-table-column
          prop="category.name"
          label="资质类别"
          width="120"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <span class="pointer">{{scope.row.category.name}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="number"
          label="资质编号"
          width="120"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <span class="pointer">{{scope.row.number}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="gradeCategory.name"
          label="资质等级"
          width="80"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="subject"
          label="资质主体/合作方"
          :formatter="formatSubject"
        >
        </el-table-column>
        <el-table-column
          prop="effectiveDate"
          label="资质有效期/合作时间">
        </el-table-column>
        <el-table-column
          prop="sourceType"
          label="资质来源"
          width="80">
          <template slot-scope="scope">
            <span  v-if="scope.row.sourceType === 1">自有</span>
            <span  v-if="scope.row.sourceType === 2">借入</span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="200">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="handleDetail(scope)">查看</el-button>
            <el-button type="text" size="small" @click="handleUpdate(scope)" v-if="$store.getters.permissions.includes('1020202')">修改</el-button>
            <el-button type="text" size="small" @click="handleDel(scope)" v-if="$store.getters.permissions.includes('1020202')">删除</el-button>
            <el-button type="text" size="small" @click="handleRecord(scope)" v-if="$store.getters.permissions.includes('1020202')">合作记录</el-button>
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
  </div>
</template>

<script>
import { qualificationSubject, category } from '@/api/resource'

export default {
  name: '',
  data () {
    return {
      type: null,
      typeOptions: [],
      sourceType: '',
      typeName: null,
      search_subjectLike: '',
      search_numberLike: '',
      checkList: [],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 100, // 总条数
      pageSize: 10, // 每页展示条数
      tableData: [],
      showselect: false, // 高级搜索显示隐藏
      searchForm: {},
      // 资质来源
      isSourceTypeSelectAll: true,
      sourceTypeFlag: true,
      // 到期时间
      isProjectTimeSelectAll: true,
      projectTimeOption: [
        {
          name: '近一周',
          code: 1
        },
        {
          name: '近一月',
          code: 2
        },
        {
          name: '近三月',
          code: 3
        },
        {
          name: '近半年',
          code: 4
        },
        {
          name: '近一年',
          code: 5
        },
        {
          name: '自定义',
          code: 6
        }
      ],
      projectTimeFlag: true
    }
  },
  methods: {
    // 高级搜索-搜索条件选择
    conditionSelect (value, label) {
      switch (label) {
        case '资质来源':
          this.sourceType = value
          this.isSourceTypeSelectAll = false
          break
        case '到期时间':
          this.searchForm.projectTime = value
          this.isProjectTimeSelectAll = false
          break
        default:
          break
      }
    },
    // 高级搜索-取消条件
    conditionCancel (label) {
      switch (label) {
        case '资质来源':
          this.sourceTypeFlag = false
          this.sourceType = null
          this.isSourceTypeSelectAll = true
          break
        case '到期时间':
          this.projectTimeFlag = false
          this.searchForm.projectTime = null
          this.isProjectTimeSelectAll = true
          break
        default:
          break
      }
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.getList()
    },
    reset () {
      this.type = null
      this.sourceType = ''
      this.search_subjectLike = ''
      this.search_numberLike = ''
      this.searchForm = {}
      this.isSourceTypeSelectAll = true
      this.isProjectTimeSelectAll = true
      this.currentPage = 1
      this.pageNo = 0
      this.getList()
    },
    formatSubject (row, column) {
      if (row.sourceType === 1) {
        return row.dictionaryDetail.name
      } else if (row.sourceType === 2) {
        return row.subject
      }
    },
    // 分页
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getList()
    },
    onclickBtnData (row, column, cell, event) {
      if (column.label === '资质类别' || column.label === '资质编号') {
        this.$router.push({path: `/resource/aptitude/detail/${row.objectId}`})
      }
    },
    handleAdd () {
      this.$router.push({path: `/resource/aptitude/add`})
    },
    handleUpdate (scope) {
      this.$router.push({path: `/resource/aptitude/update`, query: {objectId: scope.row.objectId}})
    },
    handleDetail (scope) {
      this.$router.push({path: `/resource/aptitude/detail/${scope.row.objectId}`})
    },
    handleDel (scope) {
      // 判断当前资质是否可以删除，是否有合作记录
      qualificationSubject.checkedCanDel(scope.row.code).then((res) => {
        if (res.data.resCode === '0000') {
          if (res.data.canDel) {
            this.$confirm('确认删除吗?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'error'
            }).then(() => {
              qualificationSubject.deleteQualificationSubject(scope.row.objectId).then((res) => {
                this.getList()
              })
            }).catch(() => {
              return false
            })
          } else {
            this.$message({
              message: '当前资质存在存在合作记录，不可删除',
              type: 'warning'
            })
          }
        }
      })
    },
    handleRecord (scope) {
      this.$router.push({path: `/resource/aptitude/record/${scope.row.code}`})
    },
    getList () {
      let query = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        categoryId: this.type,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        subjectLike: this.search_subjectLike,
        numberLike: this.search_numberLike,
        endDateType: this.searchForm.projectTime || null
      }
      if (this.sourceType && Number(this.sourceType) !== 0) {
        this.$set(query, 'sourceType', this.sourceType)
      }
      if (this.searchForm.projectCustomTime) {
        query.endDateLowerLimit = this.searchForm.projectCustomTime[0]
        query.endDateUpperLimit = this.searchForm.projectCustomTime[1]
      }
      qualificationSubject.getQualificationSubjectList(query).then((res) => {
        this.tableData = res.data.qualificationSubjectList.list
        this.total = res.data.qualificationSubjectList.total
      })
    },
    // 获取资质类别
    getCategory () {
      category.getCategoryList().then((res) => {
        this.typeOptions = res.data.categoryList
      })
    }
  },
  mounted () {
    this.getList()
    this.getCategory()
  }
}
</script>

<style scoped>
  .seacher_box .el-row{
    margin-bottom: 12px;
  }
</style>
