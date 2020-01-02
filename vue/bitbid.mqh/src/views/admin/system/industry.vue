<template>
  <div class="industry" id="industry-page">
    <div class="breadcrumb-mqh-tit" >
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system/industry' }"><span>行业资讯管理</span></el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="maincont-box">
      <el-form :inline="true" :model="formInline" class="demo-form-inline form-btn">
        <el-form-item label="标题:">
          <el-input v-model="formInline.messageLike" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getIndustryData">查 询</el-button>
        </el-form-item>
        <el-form-item style="float: right;">
          <el-button type="warning" @click="addNews('inner')" icon="el-icon-plus" size="small">添加站外资讯</el-button>
          <el-button type="primary" @click="addNews('out')" icon="el-icon-plus" size="small">添加站内资讯</el-button>
        </el-form-item>
      </el-form>
      <div class="news-table-box">
        <el-table
          :data="tableData"
          border
          style="width: 100%">
          <el-table-column
            type="index"
            label="序号"
            align="center"
            width="55"
            :index="indexMethod">
          </el-table-column>
          <el-table-column
            prop="title"
            label="标题"
            show-overflow-tooltip
            header-align="center">
          </el-table-column>
          <el-table-column
            prop="publishedTime"
            :formatter="filterDate"
            label="发布时间"
            align="center">
          </el-table-column>
          <el-table-column
            prop="isTop"
            :formatter="filterIsTop"
            label="是否置顶"
            align="center"
            width="100">
          </el-table-column>
          <el-table-column
            prop="status"
            label="状态"
            align="center"
            width="100">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.status"
                active-color="#13ce66"
                inactive-color="#ff4949"
                :active-value="1"
                :inactive-value="0"
                @change="changeSwitch(scope.row)">
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center">
            <template slot-scope="scope">
              <el-button type="text" @click="handleTableBtn(scope.row, 'look')">查看</el-button>
              <el-button type="text" @click="handleTableBtn(scope.row, 'edit')">修改</el-button>
              <el-button type="text" @click="handleTableBtn(scope.row, 'del')">删除</el-button>
              <el-button type="text" v-if="scope.row.isTop==0" @click="handleTableBtn(scope.row, 'top')">置顶</el-button>
              <el-button type="text" v-if="scope.row.isTop==1" @click="handleTableBtn(scope.row, 'cancelTop')">取消置顶</el-button>
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
    </div>
  </div>
</template>
<script>
import { industry } from 'api/index'
import {formatDate} from 'common/js/date'
export default {
  name: '',
  data () {
    return {
      formInline: {},
      tableData: [],
      currentPage: 1,
      pageNo: 0,
      pageSize: 10,
      total: 0, // 总条数
      pageSizeList: [10, 20, 30, 40, 50]
    }
  },
  created () {
    this.getIndustryData()
  },
  methods: {
    filterDate (value) {
      if (value.publishedTime) {
        let date = new Date(value.publishedTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return null
      }
    },
    // 处理是否置顶
    filterIsTop (value) {
      if (value.isTop) {
        return value.isTop === 1 ? '是' : '否'
      } else {
        return '否'
      }
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 更新状态等 */
    updateData (row) {
      industry.save(row).then((res) => {
        if (res.data.resCode === '0000') {
          this.getIndustryData()
        }
      })
    },
    getIndustryData () {
      let query = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        messageLike: this.formInline.messageLike
      }
      industry.getList(query).then((res) => {
        if (res.data.industryNewsList && res.data.industryNewsList.list.length > 0) {
          this.tableData = res.data.industryNewsList.list
          this.total = res.data.industryNewsList.total
        }
      })
    },
    /** 分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.getIndustryData()
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getIndustryData()
    },
    /** 表格操作 */
    handleTableBtn (row, type) {
      if (Object.is(type, 'look') || Object.is(type, 'edit')) {
        if (row.type === 1) {
          this.$router.push({path: '/system/industry/stationin', query: {objectId: row.objectId, type: type}})
        } else if (row.type === 2) {
          this.$router.push({path: '/system/industry/stationout', query: {objectId: row.objectId, type: type}})
        }
      } else if (Object.is(type, 'del')) {
        this.deleteClick(row)
      } else if (Object.is(type, 'top')) { // 置顶
        row.isTop = 1
        this.updateData(row)
      } else if (Object.is(type, 'cancelTop')) {
        row.isTop = 0
        this.updateData(row)
      }
    },
    /** 添加站内外资讯 */
    addNews (type) {
      if (Object.is(type, 'inner')) {
        this.$router.push({path: '/system/industry/stationin'})
      } else if (Object.is(type, 'out')) {
        this.$router.push({path: '/system/industry/stationout'})
      }
    },
    /** 改变状态 */
    changeSwitch (row) {
      let data = {objectId: row.objectId, status: row.status}
      this.updateData(data)
      this.getIndustryData()
    },
    // 删除方法
    deleteClick (row) {
      this.$confirm('此操作永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let data = {objectId: row.objectId, isDelete: 1}
        this.updateData(data)
        this.getIndustryData()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    }
  }
}
</script>
<style lang="less">
#industry-page {
  .form-btn {
    text-align: left;
  }
  .block-page {
    padding-top: 20px;
  }
}
</style>
