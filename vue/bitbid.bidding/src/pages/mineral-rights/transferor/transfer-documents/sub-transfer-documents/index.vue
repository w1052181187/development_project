<template>
  <div id="tramissionindexbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让文件</el-breadcrumb-item>
        <el-breadcrumb-item>提交出让文件</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--添加按钮-->
        <el-row>
          <el-button type="success" @click="addBtn" class="addbutton"> + 添加</el-button>
          <div class="seacher_box">
            <el-select v-model="selectCondition" class="select" @change="selectSeacher">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-input class="search" style="vertical-align: top"  :placeholder="'请输入' + seacherName" v-model="seacher_input">
              <el-button  slot="append" icon="el-icon-search" @click="search"></el-button>
            </el-input>
          </div>
        </el-row>
        <!--添加按钮-->
        <!--主要内容 table-->
        <el-table
          :data="tranDocTableData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="announcement.annoNumber"
            label="公告名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="fileInformations.fileName"
            label="出让文件"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="status"
            label="文件状态"
            width="150"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 0">
                <span class="statusbox">未提交</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <span class="statusbox">已提交</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
                <span class="statusbox">已发布</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="操作" width="140" align="center">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 0">
                <el-button type="text" size="small" @click="updateBtn(scope)">
                  修改
                </el-button>
                <el-button type="text" size="small" @click="deleteBtn(scope)">
                  删除
                </el-button>
                <el-button type="text" size="small" @click="lookBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <el-button type="text" size="small" @click="releaseBtn(scope)">
                  发布
                </el-button>
                <el-button type="text" size="small" @click="updateBtn(scope)">
                  变更
                </el-button>
                <el-button type="text" size="small" @click="lookBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
                <el-button type="text" size="small" @click="lookBtn(scope)">
                  查看
                </el-button>
                <el-button type="text" size="small" @click="clarifyBtn(scope)">
                  澄清修改
                </el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <!--主要内容 table-->
        <!--分页-->
        <el-pagination
          class="pagebox"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :total="total"
          :page-size='pageSize'
          layout="prev, pager, next, jumper">
        </el-pagination>
        <!--分页-->
      </template>
    </div>
  </div>
</template>
<script>
import { tdocuments } from '@/api'
export default {
  data () {
    return {
      seacher_input: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '公告名称',
      options: [{
        value: 1,
        label: '公告名称'
      }, {
        value: 2,
        label: '文件名称'
      }],
      selectCondition: 1,
      tranDocTableData: [],
      // 当前页
      currentPage: 1,
      total: 0, // 总条数
      pageNo: 0,
      pageSize: 10, // 每页展示条数
      searchIs: false
    }
  },
  methods: {
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '公告名称'
        this.selectCondition = 1
      } else if (val === 2) {
        this.seacherName = '文件名称'
        this.selectCondition = 2
      }
    },
    // 列表显示
    queryList () {
      let url = null
      if (this.searchIs) {
        url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0
        }
        if (this.selectCondition === 1) {
          url.annoName = this.seacher_input
        } else if (this.selectCondition === 2) {
          url.fileName = this.seacher_input
        }
      } else {
        url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0
        }
      }
      tdocuments.queryList(url).then((res) => {
        this.tranDocTableData = res.data.transferDocumentsList.list
        this.total = res.data.transferDocumentsList.total
      })
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      let searchs = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0
      }
      if (this.selectCondition === 1) {
        searchs.annoName = this.seacher_input
      } else if (this.selectCondition === 2) {
        searchs.fileName = this.seacher_input
      }
      tdocuments.queryList(searchs).then((res) => {
        this.tranDocTableData = res.data.transferDocumentsList.list
        this.total = res.data.transferDocumentsList.total
      })
      this.searchIs = true
    },
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.queryList(this.searchIs)
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.queryList(this.searchIs)
    },
    // 添加
    addBtn () {
      this.$router.push({path: '/mr/transferor/transfer-documents/sub-transfer-documents/selection-notice', query: {roledsType: 1}})
    },
    // 编辑
    updateBtn (scope) {
      this.$router.push({path: '/mr/transferor/transfer-documents/sub-transfer-documents/update', query: {objectId: scope.row.objectId, annoId: scope.row.annoId, roledsType: 1}})
    },
    // 查看
    lookBtn (scope) {
      this.$router.push({path: `/mr/transferor/transfer-documents/sub-transfer-documents/detail/${scope.row.objectId}`, query: {roledsType: 1}})
    },
    // 发布
    releaseBtn (scope) {
      this.$router.push({path: '/mr/transferor/transfer-documents/sub-transfer-documents/release', query: {objectId: scope.row.objectId, roledsType: 1}})
    },
    // 澄清修改
    clarifyBtn (scope) {
      let date = new Date()
      var sign1 = '-'
      var sign2 = ':'
      let year = date.getFullYear()
      let month = date.getMonth() + 1
      let day = date.getDate()
      let hour = date.getHours()
      let minutes = date.getMinutes()
      let seconds = date.getSeconds()
      // 给一位数数据前面加 “0”
      if (month >= 1 && month <= 9) {
        month = '0' + month
      }
      if (day >= 0 && day <= 9) {
        day = '0' + day
      }
      if (hour >= 0 && hour <= 9) {
        hour = '0' + hour
      }
      if (minutes >= 0 && minutes <= 9) {
        minutes = '0' + minutes
      }
      if (seconds >= 0 && seconds <= 9) {
        seconds = '0' + seconds
      }
      var currentdate = year + sign1 + month + sign1 + day + ' ' + hour + sign2 + minutes + sign2 + seconds
      if (scope.row.announcement.saleStartTime === null) {
        let hangTimes = new Date(Date.parse(scope.row.announcement.hangStartTime.replace(/-/g, '/'))).getTime() // 挂牌开始时间
        let currentdateTime = new Date(Date.parse(currentdate.replace(/-/g, '/'))).getTime() // 当前时间
        if (hangTimes - currentdateTime < 0) {
          this.$message({
            type: 'warning',
            message: '在竞价开始时间一个小时前不能添加澄清修改文件'
          })
          return false
        } else {
          let dates = Math.abs((hangTimes - currentdateTime)) / (1000 * 60 * 60)
          if (dates < 1) {
            this.$message({
              type: 'warning',
              message: '在竞价开始时间一个小时前不能添加澄清修改文件'
            })
            return false
          }
        }
      } else {
        let saleStartTimes = new Date(Date.parse(scope.row.announcement.saleStartTime.replace(/-/g, '/'))).getTime() // 拍卖开始时间
        let currentdateTime = new Date(Date.parse(currentdate.replace(/-/g, '/'))).getTime() // 当前时间
        if (saleStartTimes - currentdateTime < 0) {
          this.$message({
            type: 'warning',
            message: '在竞价开始时间一个小时前不能添加澄清修改文件'
          })
          return false
        } else {
          let dates = Math.abs((saleStartTimes - currentdateTime)) / (1000 * 60 * 60)
          if (dates < 1) {
            this.$message({
              type: 'warning',
              message: '在竞价开始时间一个小时前不能添加澄清修改文件'
            })
            return false
          }
        }
      }
      this.$router.push({path: '/mr/transferor/transfer-documents/sub-clarify-documents/add', query: {objectId: scope.row.annoId}})
    },
    // 删除
    deleteBtn (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        tdocuments.delete(scope.row.objectId).then((res) => {
          this.queryList()
        })
      }).catch(() => {
        return false
      })
    }
  },
  mounted () {
    this.queryList()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
</style>
