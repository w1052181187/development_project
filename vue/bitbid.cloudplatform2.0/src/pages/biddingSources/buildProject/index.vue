<template>
  <div class="cloudcontent" id="cloud_buildProject">
    <div class="topmain">
      <div class="seacher_box">
        <span>项目名称：</span>
        <el-input class="search" placeholder="请输入项目名称关键字" style="vertical-align: top"  v-model="searchForm.projectNameLike"></el-input>
        <el-button  type="primary" @click="search">查询</el-button>
      </div>
      <span class="more" @click="showmore">+更多筛选条件</span>
      <div class="selectbox" v-if="showselect">
        <el-form :model="searchForm" ref="searchForm" label-width="80px" :validate-on-rule-change="true">
          <el-row>
            <el-col :span="24">
              <el-form-item label="项目阶段">
                <span :class="isStageSelectAll ? 'all selectall': 'all'" @click="conditionCancel('项目阶段')">不限</span>
                <el-radio-group v-model="searchForm.projectStage" @change="conditionSelect($event, '项目阶段')">
                  <el-radio v-for="item in proPhaseOption" :label="item.code" :key="item.code" :checked="projectStageFlag">{{item.name}}</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="所在地区">
                <span :class="isRegionSelectAll ? 'all selectall': 'all'" @click="conditionCancel('所在地区')">不限</span>
                <el-radio-group v-model="searchForm.provinceId" class="region_radio" @change="conditionSelect($event, '所在地区')">
                  <el-radio v-for="item in regionOption" :label="item.value" :key="item.value" :checked="regionFlag">{{item.label}}</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="更新时间">
                <span :class="isTimeSelectAll ? 'all selectall': 'all'" @click="conditionCancel('更新时间')">不限</span>
                <el-radio-group v-model="searchForm.updateTimeType" @change="conditionSelect($event, '更新时间')">
                  <el-radio v-for="item in updateTimeOption" :label="item.code" :key="item.code" :checked="updateTimeFlag">{{item.name}}</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form >
      </div>
    </div>
    <div class="main">
      <p class="search_result">全网搜索，共检索出<span>{{total}}</span>条项目信息</p>
      <el-table
        :data="tableData"
        border
        style="width: 100%" header-cell-class-name="tableheader">
        <el-table-column
          prop="project_name"
          label="项目名称"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <a href="javascript:;" @click="lookDetail(scope)"  :title="scope.row.project_name">
              <span v-if="scope.row.project_stage === 1">[可行性研究]</span>
              <span v-if="scope.row.project_stage === 2">[设计阶段]</span>
              <span v-if="scope.row.project_stage === 3">[审批/核准/备案]</span>
              <span v-if="scope.row.project_stage === 4">[项目立项]</span>
              <span v-if="scope.row.project_stage === 5">[环境影响评价]</span>
              <span v-if="scope.row.project_stage === 6">[施工准备]</span>
              <span v-if="scope.row.project_stage === 7">[施工在建]</span>
              <span v-if="scope.row.project_stage === 8">[项目竣工]</span>
              {{scope.row.project_name}}</a>
          </template>
        </el-table-column>
        <el-table-column
          prop="region"
          label="地区"
          width="200"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="total_investment"
          label="项目投资"
          width="200"
          :formatter="stringFormat"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="publish_date"
          label="更新时间"
          width="200"
          :formatter="dateFormat"
          show-overflow-tooltip>
        </el-table-column>
      </el-table>
      <!--分页-->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size='pageSize'
        :current-page.sync="pageNo"
        @current-change="handlePage"
        @next-click="handlePage">
      </el-pagination>
      <!--分页-->
    </div>
  </div>
</template>
<script>
import * as region from '@/assets/js/region'
import {proposedProject} from '@/api/biddingSources'
import {dateFormat} from '@/assets/js/common'
export default {
  components: {
  },
  data () {
    return {
      showselect: false,
      tableData: [],
      searchForm: {
        projectNameLike: '',
        projectStage: 0,
        provinceId: 0,
        updateTimeType: 0
      },
      // 项目阶段
      proPhaseOption: [
        {
          name: '可行性研究',
          code: 1
        },
        {
          name: '设计阶段',
          code: 2
        },
        {
          name: '审批/核准/备案',
          code: 3
        },
        {
          name: '项目立项',
          code: 4
        },
        {
          name: '环境影响评价',
          code: 5
        },
        {
          name: '施工准备',
          code: 6
        },
        {
          name: '施工在建',
          code: 7
        },
        {
          name: '项目竣工',
          code: 8
        }
      ],
      // 所在地区
      regionOption: region.CityInfo,
      // 更新时间
      updateTimeOption: [
        {
          name: '今天',
          code: 1
        },
        {
          name: '近三天',
          code: 2
        },
        {
          name: '近一周',
          code: 3
        },
        {
          name: '近一月',
          code: 4
        },
        {
          name: '近三月',
          code: 5
        },
        {
          name: '近半年',
          code: 6
        },
        {
          name: '近一年',
          code: 7
        }
      ],
      // 当前页
      pageNo: 0,
      total: 0, // 总条数
      pageSize: 10, // 每页展示条数
      dataIndex: 1,
      queryModel: {
        pageNo: 0,
        pageSize: 10,
        indexType: 1,
        orderBy: 'publish_date',
        projectNameLike: '',
        projectStage: '',
        provinceId: '',
        updateTimeType: ''
      },
      projectStageFlag: true,
      regionFlag: true,
      updateTimeFlag: true,
      isStageSelectAll: true,
      isRegionSelectAll: true,
      isTimeSelectAll: true
    }
  },
  created () {
  },
  methods: {
    list () {
      this.queryModel.pageNo = this.pageNo
      this.queryModel.projectNameLike = this.searchForm.projectNameLike ? this.searchForm.projectNameLike : null
      if (this.showselect) {
        this.queryModel.projectStage = this.searchForm.projectStage ? this.searchForm.projectStage : null
        this.queryModel.provinceId = this.searchForm.provinceId ? this.searchForm.provinceId : null
        this.queryModel.updateTimeType = this.searchForm.updateTimeType ? this.searchForm.updateTimeType : null
      }
      proposedProject.list(this.queryModel).then((res) => {
        this.tableData = res.data.data
        this.total = res.data.totalNum
        // 地区
        this.tableData.map((item) => {
          this.regionOption.map((ite) => {
            if (Number(item.province_id) === ite.value) {
              item.region = ite.label
            }
          })
        })
      })
    },
    // 展开收起 搜索条件
    showmore () {
      this.showselect = !this.showselect
      if (!this.showselect) {
        this.projectStageFlag = false
        this.searchForm.projectStage = null
        this.regionFlag = false
        this.searchForm.provinceId = null
        this.updateTimeFlag = false
        this.searchForm.updateTimeType = null
      }
    },
    // 搜索条件选择
    conditionSelect (value, label) {
      switch (label) {
        case '项目阶段':
          this.searchForm.projectStage = value
          this.isStageSelectAll = false
          break
        case '所在地区':
          this.searchForm.provinceId = value
          this.isRegionSelectAll = false
          break
        case '更新时间':
          this.searchForm.updateTimeType = value
          this.isTimeSelectAll = false
          break
        default:
          break
      }
      this.pageNo = 0
      this.list()
    },
    // 取消条件选择
    conditionCancel (label) {
      switch (label) {
        case '项目阶段':
          this.projectStageFlag = false
          this.searchForm.projectStage = null
          this.isStageSelectAll = true
          break
        case '所在地区':
          this.regionFlag = false
          this.searchForm.provinceId = null
          this.isRegionSelectAll = true
          break
        case '更新时间':
          this.updateTimeFlag = false
          this.searchForm.updateTimeType = null
          this.isTimeSelectAll = true
          break
        default:
          break
      }
      this.pageNo = 0
      this.list()
    },
    // 详情
    lookDetail (scope) {
      this.$router.push({path: `/biddingSources/buildProject/detail/${scope.row.code}`})
    },
    search () {
      this.pageNo = 0
      this.list()
    },
    // 表单分页
    handlePage (nowNum) {
      this.pageNo = nowNum
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this.list()
    },
    stringFormat (row, col, cellValue) {
      return (cellValue || '0') + (row.total_investment_company === 1 ? '万元' : '亿元')
    },
    dateFormat (row, col, cellValue) {
      if (!cellValue) {
        return '---'
      }
      return dateFormat(new Date(cellValue), 'yyyy-MM-dd hh:mm:ss')
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  #cloud_buildProject{
    .topmain{
      margin-bottom: 0;
    }
    .topmain span.more{
      margin-top: 16px;
      color: #108cee;
      font-size: 14px;
      display: inline-block;
      cursor: pointer;
      margin-left: 10px;
    }
    .selectbox{
      padding: 20px 0;
      margin-top: 17px;
      margin-bottom: 20px;
    }
    .el-form-item__label{
      text-align: left;
      font-weight: bold;
    }
   .el-form-item{
     margin-bottom: 0;
   }
    span.all{
      display: inline-block;
      float: left;
      width: 44px;
      height: 21px;
      line-height: 21px;
      border: 1px solid #498ce9;
      -webkit-box-sizing: border-box;
      -moz-box-sizing: border-box;
      box-sizing: border-box;
      color: #498ce9;
      text-align: center;
      -webkit-border-radius: 10px;
      -moz-border-radius: 10px;
      border-radius: 10px;
      margin: 10px 10px 0 0 ;
      font-size: 12px;
      cursor: pointer;
    }
    span.all:hover,span.selectall{
      background: #498ce9;
      color: #fff;
    }
    .region_radio{
      margin-top: 8px;
      width: 90%;
      display: inline-block;
    }
    .region_radio .el-radio{
      margin-bottom: 5px;
    }
    .region_radio .el-radio__label{
      width: 40px;
      display: inline-block;
    }
    .search_result{
      text-align: right;
      line-height: 36px;
      font-size: 14px;
    }
    .search_result span{
      color: #108cee;
      font-weight: bold;
      display: inline-block;
      margin: 0 3px;
    }
    .cell a{
      text-decoration: none;
      color: #333333;
    }
    .cell a span{
      color: #05a646;
      display: inline-block;
      margin-right: 3px;
    }
  }
</style>
