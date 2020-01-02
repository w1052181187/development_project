<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="dproject-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>抽签系统</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/draw/drawproject' }">抽签项目管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 搜索区域start -->
    <div class="search-box">
      <el-form :model="ruleForm" ref="ruleForm" label-width="75px" class="demo-ruleForm">
        <el-row :gutter="10">
          <el-col :span="6">
            <el-form-item label="项目名称:" prop="projectName">
              <el-input v-model="ruleForm.projectName" placeholder="请输入项目名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="项目单位:" prop="enterpriseNameLike">
              <el-input v-model="ruleForm.enterpriseNameLike" placeholder="请输入项目单位"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="抽签类型:" prop="type">
              <el-select v-model="ruleForm.type" clearable placeholder="请选择抽签类型" style="width: 100%">
                <el-option
                  v-for="item in typeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="2">
            <el-form-item>
              <el-button type="primary" @click="searchBtn(ruleForm)" icon="el-icon-search" style="margin-left: -80px;">搜索</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <!-- 搜索区域end -->
    <div class="maincont-box dproject-cont">
      <el-tabs v-model="activeName" type="card" @tab-click="handleClickTabs">
        <el-tab-pane label="待分组" name="first">
          <div class="dpro-btn">
            <el-button type="warning" @click="drawGroupBtn"><i class="iconfont mqh-icon-VPN"></i>抽签分组</el-button>
          </div>
          <!-- 表格区域 -->
          <div class="dpro-table">
            <el-table
              :data="tableUnGroupData"
              border
              :row-class-name="tableRowClassName"
              style="width: 100%">
              <el-table-column
                type="index"
                label="序号"
                align="center"
                :index="indexMethod"
                width="55">
              </el-table-column>
              <el-table-column
                align="left"
                header-align="center"
                prop="projectName"
                label="项目名称">
              </el-table-column>
              <el-table-column
                align="left"
                header-align="center"
                prop="enterpriseName"
                label="项目单位">
              </el-table-column>
              <el-table-column
                align="center"
                prop="type"
                label="抽签类型"
                :formatter="filterType">
              </el-table-column>
              <el-table-column
                align="center"
                prop="approvePassTime"
                label="选聘备案时间"
                :formatter="filterDate">
              </el-table-column>
              <el-table-column
                align="center"
                label="操作">
                <template slot-scope="scope">
                  <el-button @click="handleTableClick(scope.row,'look')" type="text" size="medium" v-if="$store.getters.permissions.includes('/draw/drawproject/detail')">查看</el-button>
                </template>
              </el-table-column>
            </el-table>
            <!--分页-->
            <div class="block-page">
              <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page.sync="unGroupCurrentPage"
                :page-sizes="pageSizeList"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="unGroupTotal">
              </el-pagination>
            </div>
          </div>
          <!-- 表格区域结束 -->
        </el-tab-pane>
        <el-tab-pane label="已分组" name="second">
          <!-- 已分组表格区域 -->
          <div class="dpro-table second-group">
            <el-table
              :data="tableGroupData"
              border
              :row-class-name="tableRowClassName"
              style="width: 100%">
              <el-table-column
                type="index"
                label="序号"
                align="center"
                :index="indexMethod"
                width="55">
              </el-table-column>
              <el-table-column
                align="left"
                header-align="center"
                prop="projectNameList"
                label="项目名称">
                <template slot-scope="scope">
                  <p v-for="(item,index) in scope.row.projectNameList" :key="index" class="dresult-span">{{item}}</p>
                </template>
              </el-table-column>
              <el-table-column
                align="left"
                header-align="center"
                prop="enterpriseNameList"
                label="项目单位">
                <template slot-scope="scope">
                  <p v-for="(item,index) in scope.row.enterpriseNameList" :key="index" class="dresult-span">{{item}}</p>
                </template>
              </el-table-column>
              <el-table-column
                align="center"
                prop="typeInfoList"
                label="抽签类型">
                <template slot-scope="scope">
                  <p v-for="(item,index) in scope.row.typeInfoList" :key="index" class="dresult-span">{{item}}</p>
                </template>
              </el-table-column>
              <el-table-column
                align="center"
                prop="createDate"
                label="选聘备案时间"
                :formatter="filterDate">
              </el-table-column>
              <el-table-column
                align="center"
                label="操作"
                width="200">
                <template slot-scope="scope">
                  <el-button @click="handleTableClick(scope.row,'look')" type="text" size="medium" v-if="$store.getters.permissions.includes('/draw/drawproject/detail')">查看</el-button>
                  <el-button @click="handleTableClick(scope.row,'edit')" type="text" size="medium" v-if="$store.getters.permissions.includes('/draw/drawproject/operation') && scope.row.status != 1">维护</el-button>
                  <el-button @click="handleTableClick(scope.row,'cancel')" type="text" size="medium" v-if="$store.getters.permissions.includes('/draw/drawproject/operation') && scope.row.status != 1">取消分组</el-button>
                </template>
              </el-table-column>
            </el-table>
            <!--分页-->
            <div class="block-page">
              <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page.sync="groupCurrentPage"
                :page-sizes="pageSizeList"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="groupTotal">
              </el-pagination>
            </div>
          </div>
          <!-- 已分组表格区域结束 -->
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import {drawrecord, drawproject, enterprise} from 'api/index'
import {formatDate} from 'common/js/date'
// 实例
export default {
  data () {
    return {
      activeName: 'first',
      ruleForm: {},
      tableUnGroupData: [], // 待分组表格
      enterpriseList: [],
      tableGroupData: [{
        projectNameList: [],
        enterpriseNameList: [],
        typeInfoList: [],
        createDate: ''
      }
      ],
      pageSize: 10,
      pageNo: 0,
      unGroupCurrentPage: 1,
      groupCurrentPage: 1,
      unGroupTotal: 0, // 未分组项目总条数
      groupTotal: 0, // 已分组总条数
      pageSizeList: [10, 20, 30, 40, 50],
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
        }]
    }
  },
  created () {
    /**  进行编辑，查看等操作后返回到当前页 */
    /* if (this.$route.query.activeName === 'second') {
      this.activeName = 'second'
    } else {
      this.activeName = 'first'
    } */
    this.getAllEnterpriseList()
    this.getIsNotGroupProjectList()
    this.getIsGroupProjectList()
  },
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
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
      } else if (row.approvePassTime && column.property === 'approvePassTime') {
        date = new Date(row.approvePassTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm')
      }
    },
    indexMethod (index) {
      if (this.activeName === 'first') {
        return index + (this.unGroupCurrentPage - 1) * 10 + 1
      } else {
        return index + (this.groupCurrentPage - 1) * 10 + 1
      }
    },
    /** 表单分页 */
    handleSizeChange (val) {
      var likeThis = this
      var pageSize = `${val}`
      this.pageNo = 0
      this.pageSize = parseInt(pageSize)
      if (this.activeName === 'first') {
        this.unGroupCurrentPage = 1
        this.getIsNotGroupProjectList(this.pageNo, this.pageSize, function (resp) {
          likeThis.unGroupTotal = resp.data.Data.Total
        })
      } else {
        this.groupCurrentPage = 1
        this.getIsGroupProjectList(this.pageNo, this.pageSize, function (resp) {
          likeThis.groupTotal = resp.data.Data.Total
        })
      }
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      if (this.activeName === 'first') {
        this.unGroupCurrentPage = nowNum
        this.getIsNotGroupProjectList(parseInt(this.pageNo), parseInt(this.pageSize))
      } else {
        this.groupCurrentPage = nowNum
        this.getIsGroupProjectList(parseInt(this.pageNo), parseInt(this.pageSize))
      }
    },
    // tabs切换
    handleClickTabs (data) {
      this.ruleForm = {}
      this.pageSize = 10
      this.pageNo = 0
      this.unGroupCurrentPage = 1
      this.groupCurrentPage = 1
      this.activeName = data.name
      // 页面切换后重新获取数据
      if (this.activeName === 'first') {
        this.getIsNotGroupProjectList()
      } else {
        this.getIsGroupProjectList()
      }
    },
    /** 搜索 */
    searchBtn () {
      // 进行查找
      if (this.activeName === 'first') {
        this.getIsNotGroupProjectList()
      } else {
        this.getIsGroupProjectList()
      }
    },
    // 抽签分组
    drawGroupBtn () {
      this.$router.push({path: '/draw/drawgroup'})
    },
    /** 表格操作按钮 */
    handleTableClick (data, type) {
      if (type === 'look') {
        if (this.activeName === 'first') {
          if (data.type === 1) {
            this.$router.push({path: '/draw/details', query: {name: 'details', objectId: data.objectId}})
          } else if (data.type === 2 || data.type === 3 || data.type === 4) {
            this.$router.push({path: '/draw/indetails', query: {name: 'indetails', objectId: data.objectId}})
          }
        } else {
          this.$router.push({path: '/drawroom/details', query: {name: 'details', objectId: data.objectId}})
        }
      } else if (type === 'edit') {
        this.$router.push({path: '/draw/drawgroup', query: {name: 'edit', objectId: data.objectId}})
      } else if (type === 'cancel') {
        this.cancelGroup(data)
      }
    },
    /** 删除用户信息 */
    cancelGroup (data) {
      this.$confirm('此操作将取消分组, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        drawproject.delete(data.code).then(() => {
          this.getIsNotGroupProjectList()
          this.getIsGroupProjectList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    /** 获取全部待分组信息 */
    getIsNotGroupProjectList () {
      drawrecord.getList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        messageLike: this.ruleForm.projectName || null,
        enterpriseNameLike: this.ruleForm.enterpriseNameLike || null,
        type: this.ruleForm.type || null,
        isGrouped: 0,
        approveStatus: 5
      }).then((res) => {
        this.tableUnGroupData = res.data.selectApplyList.list
        this.unGroupTotal = res.data.selectApplyList.total
      })
    },
    /** 获取全部已分组信息 */
    getIsGroupProjectList () {
      drawproject.getList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        messageLike: this.ruleForm.projectName || null,
        enterpriseNameLike: this.ruleForm.enterpriseNameLike || null,
        type: this.ruleForm.type || null
      }).then((res) => {
        this.tableGroupData = res.data.projectGroupList.list
        this.groupTotal = res.data.projectGroupList.total
        if (this.tableGroupData.length > 0) {
          for (let i = 0; i < this.tableGroupData.length; i++) {
            let typeInfo = []
            let projectName = []
            let enterpriseName = []
            if (this.tableGroupData[i].selectApplyList.length > 0) {
              for (let j = 0; j < this.tableGroupData[i].selectApplyList.length; j++) {
                projectName[j] = this.tableGroupData[i].selectApplyList[j].projectName
                enterpriseName[j] = this.tableGroupData[i].selectApplyList[j].enterpriseName
                typeInfo[j] = this.filterType(this.tableGroupData[i].selectApplyList[j])
              }
              this.tableGroupData[i].projectNameList = projectName
              this.tableGroupData[i].enterpriseNameList = enterpriseName
              this.tableGroupData[i].typeInfoList = typeInfo
            }
          }
        }
      })
    },
    getAllEnterpriseList () {
      //  获得所有的二级单位的信息
      enterprise.getAll({
        types: '2,3'
      }).then((res) => {
        if (res.data.enterprises.length > 0) {
          let info = []
          for (var i = 0; i < res.data.enterprises.length; i++) {
            var obj = {}
            obj.value = res.data.enterprises[i].objectId
            obj.label = res.data.enterprises[i].name
            info[i] = obj
          }
          this.enterpriseList = info
        }
      })
    }
  },
  computed: {},
  // 实例创建完成
  mounted () {
  }
}
</script>
<style lang="less">
#dproject-page {
  /** 表格样式 */
  .second-group tr{
    td:nth-child(2),
    td:nth-child(3),
    td:nth-child(4) {
      div.cell {
        padding:0;
      }
      p {
        margin: 0;
        padding: 10px 8px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
      p:not(:last-child) {
        border-bottom: 1px solid #eee;
      }
    }
  }
  .dpro-btn {
    text-align: left;
    margin-bottom: 20px;
    i {
      padding-right: 10px;
    }
  }
  .block-page {
    padding-top: 20px;
  }
}
</style>
