<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="review-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>抽签系统</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/draw/drawreview'}">选聘备案审批</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="search-box review-search">
      <!-- 搜索条件区域 -->
      <div class="search-warp">
        <el-form :model="formInline" label-width="100px">
          <el-row :gutter="20" style="margin-left: -40px;">
            <el-col :span="5" style="width: 300px">
              <el-form-item label="项目名称:">
                <el-input v-model="formInline.projectName" :maxlength="70" placeholder="请输入项目名称" ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5" style="width: 300px">
              <el-form-item label="申请单位:">
                <el-input v-model="formInline.enterpriseName" :maxlength="70" placeholder="请输入申请单位" ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5" style="width: 300px">
              <el-form-item label="选聘中介类型:">
                <el-select v-model="formInline.type" clearable placeholder="请选择中介类型" class="search-sel">
                  <el-option
                    v-for="item in typeList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="5" style="width: 300px">
              <el-form-item label="审批状态:">
                <el-select v-model="formInline.approveStatusInfo" clearable placeholder="请选择审批状态" class="search-sel">
                  <el-option
                    v-for="item in stateList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="2" style="width: 80px">
              <el-form-item>
                <div class="search search-btn">
                  <el-button type="primary" @click="onSearchBtn(formInline)" icon="el-icon-search" style="margin-left: -80px;">搜索</el-button>
                </div>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <!-- 搜索条件区域结束 -->
    </div>
    <div class="maincont-box review-cont">
      <!-- 表格区域 -->
      <div class="review-tab">
        <el-table
          :data="tableData"
          border
          :row-class-name="tableRowClassName"
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
            show-overflow-tooltip
            prop="projectName"
            label="项目名称">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="type"
            label="选聘中介类型"
            :formatter="filterTypeInfo">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="enterpriseName"
            label="申请单位">
          </el-table-column>
          <el-table-column
            align="center"
            prop="userName"
            label="申请人">
          </el-table-column>
          <el-table-column
            align="center"
            prop="createDate"
            label="申请时间"
            width="140"
            :formatter="filterDate">
          </el-table-column>
          <el-table-column
            align="center"
            prop="approveName"
            label="审批人">
          </el-table-column>
           <el-table-column
            align="center"
            prop="approveTime"
            label="审批时间"
            width="140"
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
            label="操作"
            width="140">
            <template slot-scope="scope">
              <span v-if="$store.getters.permissions.includes('/draw/drawreview/approval')">
                <el-button
                  v-if="(scope.row.roleNames.includes('项目单位领导') && scope.row.approveStatus == 2)  || (scope.row.roleNames.includes('集团公司招标办') && scope.row.approveStatus == 3)
                 || (scope.row.roleNames.includes('集团公司领导') && scope.row.approveStatus == 4) || (scope.row.roleNames.includes('集团公司领导') && scope.row.approveStatus == 2 && scope.row.enterpriseType === 1)"
                  @click="handleClick(scope.row,'review')"
                  type="text"
                  size="small">
                  审批
                </el-button>
              </span>
              <span v-if="$store.getters.permissions.includes('/draw/drawrecord/detail')">
                <el-button
                  @click="handleClick(scope.row,'look')"
                  type="text"
                  size="small">
                  查看
                </el-button>
              </span>
              <span v-if="$store.getters.permissions.includes('/draw/drawreview/operation')">
                <el-button
                  v-if="scope.row.approveStatus == 7"
                  @click="handleClick(scope.row,'del')"
                  type="text" size="small">
                  删除
                </el-button>
              </span>
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
    </div>
  </div>
</template>

<script>
import {drawreview, enterprise} from 'api/index'
import {formatDate} from '../../../../common/js/date.js'
// 实例
export default {
  data () {
    return {
      formInline: {},
      typeList: [
        {
          label: '代理机构选聘',
          value: '1'
        },
        {
          label: '咨询造价机构',
          value: '2'
        },
        {
          label: '审计评估机构',
          value: '3'
        },
        {
          label: '法律服务机构',
          value: '4'
        }],
      typeId: '',
      stateList: [
        {
          label: '审核中',
          value: '2' || '3' || '4'
        },
        {
          label: '通过',
          value: '5'
        },
        {
          label: '终止',
          value: '7'
        }
      ],
      tableData: [],
      pageNo: 0,
      currentPage: 1,
      pageSize: 10,
      total: 0, // 总条数
      pageSizeList: [10, 20, 30, 40, 50]
    }
  },
  computed: {},
  // 实例创建
  created () {
    this.drawreviewData()
  },
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 格式化 */
    filterApproveStatus (row, column) {
      let status = row.approveStatus
      if (status === 1) {
        return '未提交'
      } else if (status === 2 || status === 3 || status === 4) {
        return '审核中'
      } else if (status === 5) {
        return '通过'
      } else if (status === 6) {
        return '退回'
      } else if (status === 7) {
        return '终止'
      }
    },
    filterTypeInfo (row, column) {
      let types = row.type
      if (types === 1) {
        return '代理机构选聘'
      } else if (types === 2) {
        return '咨询造价机构'
      } else if (types === 3) {
        return '审计评估机构'
      } else if (types === 4) {
        return '法律服务机构'
      }
    },
    filterDate (row, column) {
      let date = ''
      if (column.property === 'createDate') {
        date = new Date(row.createDate)
        return formatDate(date, 'yyyy-MM-dd hh:mm')
      } else if (row.approveTime && column.property === 'approveTime') {
        date = new Date(row.approveTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm')
      }
    },
    indexMethod (index) {
      //  进行审批，查看等操作后返回到当前页
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
        this.drawreviewData(this.pageNo, this.pageSize, function (resp) {
          likeThis.total = resp.data.selectApplyList.total
        })
      )
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.drawreviewData(parseInt(this.pageNo), parseInt(this.pageSize))
    },
    /** 列表数据展示 */
    drawreviewData () {
      let enterpriseType = this.$store.getters.authUser.enterpriseType
      let role = this.$store.getters.authUser.roles
      let obj = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        messageLike: this.formInline.projectName || null,
        enterpriseNameLike: this.formInline.enterpriseName || null,
        approveStatuses: this.formInline.approveStatusInfo || null,
        approveStatuseList: '2,3,4,5,7',
        type: this.formInline.type || null,
        enterpriseId: (enterpriseType !== 1) ? this.$store.getters.authUser.enterpriseId : null
      }
      drawreview.getList(obj).then((res) => {
        let tempData = res.data.selectApplyList.list
        if (tempData.length) {
          tempData.map(item => {
            item.roleNames = role.map(child => {
              return child.name
            })
            return item
          })
          this.tableData = tempData
          for (let i = 0; i < this.tableData.length; i++) {
            for (let j = 0; j < this.tableData[i].approveRecordList.length; j++) {
              if (this.tableData[i].approveStatus === 5 || this.tableData[i].approveStatus === 6 || this.tableData[i].approveStatus === 7) {
                if (this.tableData[i].approveRecordList[j].approveResult === 5 || this.tableData[i].approveRecordList[j].approveResult === 6 || this.tableData[i].approveRecordList[j].approveResult === 7) {
                  this.tableData[i].approveTime = this.tableData[i].approveRecordList[j].approveTime
                  this.tableData[i].approveName = this.tableData[i].approveRecordList[j].approveName
                }
              } else {
                this.tableData[i].approveTime = ''
                this.tableData[i].approveName = ''
              }
            }
          }
        }
        this.total = res.data.selectApplyList.total
      })
    },
    /** 搜索 */
    onSearchBtn () {
      this.drawreviewData()
    },
    /** 表格操作 */
    handleClick (data, type) {
      if (type === 'del') {
        this.deleteDrawRecordInfo(data)
      } else if (type === 'look') {
        if (data.type === 1) {
          this.$router.push({path: '/draw/details', query: {name: 'details', objectId: data.objectId, enterpriseName: data.enterpriseName, flag: 'review'}})
        } else if (data.type === 2 || data.type === 3 || data.type === 4) {
          this.$router.push({path: '/draw/indetails', query: {name: 'indetails', objectId: data.objectId, enterpriseName: data.enterpriseName, flag: 'review'}})
        }
      } else if (type === 'review') {
        if (data.type === 1) {
          this.$router.push({path: '/drawreview/indreview', query: {name: 'indreview', objectId: data.objectId, enterpriseName: data.enterpriseName}})
        } else if (data.type === 2 || data.type === 3 || data.type === 4) {
          this.$router.push({path: '/drawreview/dreview', query: {name: 'dreview', objectId: data.objectId, enterpriseName: data.enterpriseName}})
        }
      }
    },
    /** 删除选聘备案申请信息 */
    deleteDrawRecordInfo (data) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        drawreview.delete(data.objectId).then(() => {
          this.drawreviewData()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 实例创建完成
    mounted () {
    }
  }
}
</script>
<style lang="less">
#review-page {
  .review-search {
    .search-warp {
      width: 1300px;
      text-align: left;
    }
  }
  .review-tab {
    padding-top: 20px;
  }
  .el-form-item {
    margin-bottom: 0;
  }
  .block-page {
    padding: 20px;
  }
}

</style>
