<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="dresult-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>抽签系统</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/draw/drawresult' }">抽签结果备案查询</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="dresult-cont">
      <!-- 搜索条件区域 -->
      <div class="dresult-search">
        <el-form :model="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-row>
            <el-col :span="6">
              <el-form-item label="项目名称:" prop="projectName">
                <el-input v-model="ruleForm.projectName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="项目编号:" prop="projectNo">
                <el-input v-model="ruleForm.projectNo"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="中签单位:" prop="unit">
                <el-select v-model="ruleForm.entrustEnterpriseId" clearable placeholder="请选择中签单位" style="width: 100%">
                  <el-option
                    v-for="item in entrustEnterpriseList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="中介类型:" prop="type">
                <el-select v-model="ruleForm.typeId" clearable placeholder="请选择" style="width:100%">
                  <el-option
                    v-for="item in typeList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="抽签时间:" prop="time">
                <el-date-picker
                  @change = "datePicker"
                  v-model="ruleForm.time"
                  type="datetimerange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  style="width:100%">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="2" :pull="1">
              <el-form-item label="">
                <el-button type="primary" @click="onSearch()" icon="el-icon-search">搜索</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <!-- 搜索条件区域结束 -->

      <!-- 表格区域 -->
      <div class="dresult-tab">
        <el-table
          :data="tableData"
          :row-class-name="tableRowClassName"
          border
          style="width: 100%">
          <el-table-column
            type="index"
            label="序号"
            align="center"
            :index="indexMethod"
            width="55">
          </el-table-column>
          <el-table-column
            prop="projectName"
            label="项目名称"
            header-align="center"
            align="left">
            <template slot-scope="scope">
              <p v-for="(item,index) in scope.row.projectName" :key="index" class="dresult-span" v-if="item">{{item}}</p>
              <p v-else></p>
            </template>
          </el-table-column>
          <el-table-column
            prop="codeUser"
            label="项目编号"
            header-align="center"
            align="left">
            <template slot-scope="scope">
              <p v-for="(item,index) in scope.row.codeUser" :key="index" class="dresult-span" v-if="item">{{item}}</p>
              <p v-else></p>
            </template>
          </el-table-column>
          <el-table-column
            prop="drawLotsTime"
            :formatter="filtertDate"
            label="抽签时间"
            align="center">
          </el-table-column>
          <el-table-column
            prop="type"
            label="中介类型"
            :formatter="filterType"
            align="center">
          </el-table-column>
          <el-table-column
            prop="entrustEnterpriseName"
            label="中签单位"
            header-align="center"
            align="center">
            <template slot-scope="scope">
              <span class="draw-complete" v-show="scope.row.entrustEnterpriseName !== '' && scope.row.entrustEnterpriseName !== null">{{scope.row.entrustEnterpriseName}}</span>
              <span class="draw-uncomplete" v-show="scope.row.entrustEnterpriseName === null || scope.row.entrustEnterpriseName === ''">---</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            width="100"
            align="center">
            <template slot-scope="scope">
              <el-button @click="handleClick(scope.row, 'look')" type="text" size="medium" v-if="$store.getters.permissions.includes('/draw/drawresult/detail')">查看</el-button>
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
      </div>
      <!-- 表格区域结束 -->
    </div>
  </div>
</template>

<script>
import {drawresult, enterprise} from 'api/index'
import {formatDate} from 'common/js/date.js'
// 实例
export default {
  data () {
    return {
      ruleForm: {},
      typeId: '',
      entrustEnterpriseList: [],
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
      tableData: [
        {
          projectName: [],
          codeUser: [],
          drawLotsTime: '',
          type: '',
          entrustEnterpriseName: ''
        }
      ],
      drawLotsStartTime: null, // 搜索开始时间
      drawLotsEndTime: null, // 搜素结束时间
      currentPage: 1, // 前台页面展示当前页
      pageNo: 0, // 后台需要提交的参数
      pageSize: 10,
      total: null, // 总条数
      pageSizeList: [10, 20, 30, 40, 50]
    }
  },
  created () {
  },
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    datePicker (value) {
      if (value) {
        this.drawLotsStartTime = value[0]
        this.drawLotsEndTime = value[1]
      } else {
        this.drawLotsStartTime = null
        this.drawLotsEndTime = null
      }
    },
    // 过滤时间
    filtertDate (row, column) {
      let date = new Date(row.drawLotsTime)
      return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
    },
    filterType (row, column) {
      let type = row.type
      if (type === 1) {
        return '代理机构选聘'
      } else if (type === 2) {
        return '咨询造价机构'
      } else if (type === 3) {
        return '审计评估机构'
      } else if (type === 4) {
        return '法律服务机构'
      }
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * this.pageSize + 1
    },
    /** 表单分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.onSearch()
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getDrawResultList()
    },
    getCurrentValue () {
      if (this.$route.query.pageNo && this.$route.query.pageSize && this.$route.query.currentPage) {
        /** 进行操作后返回当前页 */
        this.currentPage = parseInt(this.$route.query.currentPage)
        this.pageNo = parseInt(this.$route.query.pageNo)
        this.pageSize = parseInt(this.$route.query.pageSize)
      }
      this.getDrawResultList()
    },
    // 搜索
    onSearch () {
      // 进行查找
      let obj = {
        pageNo: 0,
        pageSize: this.pageSize,
        projectName: this.ruleForm.projectName || null,
        projectCode: this.ruleForm.projectNo || null,
        entrustEnterpriseId: this.ruleForm.entrustEnterpriseId || null,
        type: this.ruleForm.typeId || null,
        drawLotsStartTime: this.drawLotsStartTime || null,
        drawLotsEndTime: this.drawLotsEndTime || null
      }
      this.currentPage = 1
      this.getList(obj)
    },
    handleClick (data, type) {
      this.$router.push({path: '/drawresult/details', query: {name: 'details', objectId: data.objectId, currentPage: this.currentPage, pageNo: this.pageNo, pageSize: this.pageSize}})
    },
    // 获取全部抽签备案表信息
    getDrawResultList () {
      let obj = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        projectName: this.ruleForm.projectName || null,
        projectCode: this.ruleForm.projectNo || null,
        entrustEnterpriseId: this.ruleForm.entrustEnterpriseId || null,
        type: this.ruleForm.typeId || null,
        drawLotsStartTime: this.drawLotsStartTime || null,
        drawLotsEndTime: this.drawLotsEndTime || null
      }
      this.getList(obj)
    },
    getList (obj) {
      drawresult.getList(obj).then((res) => {
        if (res.data.drawLotsResultList) {
          this.tableData = res.data.drawLotsResultList.list
          this.total = res.data.drawLotsResultList.total
          if (this.tableData.length > 0) {
            for (let i = 0; i < this.tableData.length; i++) {
              let projectName = []
              let projectId = []
              if (this.tableData[i].projectGroup.selectApplyList.length > 0) {
                for (let j = 0; j < this.tableData[i].projectGroup.selectApplyList.length; j++) {
                  projectName[j] = this.tableData[i].projectGroup.selectApplyList[j].projectName
                  projectId[j] = this.tableData[i].projectGroup.selectApplyList[j].codeUser
                }
                this.tableData[i].projectName = projectName
                this.tableData[i].codeUser = projectId
              }
            }
          }
        } else {
          return false
        }
      })
    },
    getAllEntrustEnterprise () {
      // 获取所有的中签单位
      enterprise.getAll({
        types: '4'
      }).then((res) => {
        if (res.data.enterprises.length > 0) {
          let info = []
          for (var i = 0; i < res.data.enterprises.length; i++) {
            var obj = {}
            obj.value = res.data.enterprises[i].objectId
            obj.label = res.data.enterprises[i].name
            info[i] = obj
          }
          this.entrustEnterpriseList = info
        }
      })
    }
  },
  computed: {},
  // 实例创建完成
  mounted () {
    this.getCurrentValue()
    this.getAllEntrustEnterprise()
  }
}
</script>
<style lang="less">
#dresult-page {
  .dresult-cont {
    margin: 20px 20px 130px;
    background-color: #EAEDF2;
    .dresult-search {
      padding: 20px;
      background-color: #fff;
    }
    .dresult-tab {
      padding: 20px;
      margin-top: 20px;
      background-color: #fff;
      .el-table__body .el-table__row {
        td:nth-child(2), td:nth-child(3) {
          div.cell {
            padding:0;
          }
          p {
            margin: 0;
            padding: 10px;
          }
          p:not(:last-child) {
            border-bottom: 1px solid #eee;
          }
        }
      }
    }
  }
  .el-row .el-form-item {
    margin-left: -20px;
  }
  .dresult-span {
    display: inline-block;
    width: 100%;
  }
  .block-page {
    padding-top: 20px;
  }
}
</style>
