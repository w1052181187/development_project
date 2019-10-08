<template>
  <div id="land_pro_patchReg" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让邀请</el-breadcrumb-item>
        <el-breadcrumb-item>查看出让邀请</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--搜索按钮-->
        <el-row>
          <div class="seacher_box">
            <el-select v-model="inquiryValue" class="select" @change="selectSeacher">
              <el-option
                v-for="item in selectOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-input class="search" style="vertical-align: top"  :placeholder="'请输入' + seacherName" v-model="seacherInput">
              <el-button  slot="append" icon="el-icon-search" type="" @click="search"></el-button>
            </el-input>
          </div>
        </el-row>
        <!--搜索按钮-->
        <!--主要内容 table-->
        <el-table
          :data="projectData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="sectionNumber"
            label="地块编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="landAnnoMation.annoNumber"
            label="邀请书名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="invConEndTime"
            label="确认邀请截止时间"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="isConfirm"
            label="确认状态"
            align="left"
            width="240">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.isConfirm === 0">
                <span class="statusbox gray"><i></i>未确认</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.isConfirm === 1">
                <span class="statusbox blue"><i></i>已确认</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" width="140" align="center">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.isConfirm === 0">
                <el-button type="text" size="small" @click="confirmBtn(scope)">
                  确认
                </el-button>
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.isConfirm === 1">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
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
import {landProject} from '@/api'
export default {
  data () {
    return {
      seacherInput: '',
      seacherName: '邀请书名称',
      selectOptions: [{
        value: 0,
        label: '邀请书名称'
      }, {
        value: 1,
        label: '地块编号'
      }],
      inquiryValue: 0, // 搜索默认值
      // 列表表格
      projectData: [],
      currentPage: 1, // 当前页
      pageNo: 0, // 页数
      total: 10, // 总条数
      pageSize: 10 // 每页展示条数
    }
  },
  methods: {
    // ***************************************************************数据接口方法**********************************************************************
    // -----------------------------------------列表-----------------------------------------
    list () {
      let url = {}
      url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0,
        status: 2,
        enterpriseId: this.$store.getters.authUser.userId,
        subjectId: this.$route.query.roledsType
      }
      if (this.seacherInput !== '') {
        if (this.seacherName === '邀请书名称') {
          url.annoName = this.seacherInput
        } else if (this.seacherName === '地块编号') {
          url.sectionNumber = this.seacherInput
        }
      }
      console.log(url)
      landProject.queryList(url).then(res => {
        console.log(res)
        this.projectData = res.data.landInformationPageInfo.list
        console.log(this.projectData)
        this.total = res.data.landInformationPageInfo.total
      })
    },
    // -----------------------------------------分页方法-----------------------------------------
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    // ----------------------------------------列表搜索-------------------------------------------
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.list()
    },
    // -----------------------------------------确认----------------------------------------------
    confirmBtn (scope) {
      // 当前时间
      console.log(scope.row.invConEndTime)
      let times = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate() + ' ' + new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds()
      let currentTime = new Date(Date.parse(times.replace(/-/g, '/'))).getTime()
      let cutOffTime = new Date(Date.parse(scope.row.invConEndTime.replace(/-/g, '/'))).getTime()
      if (currentTime > cutOffTime) {
        this.$message({
          type: 'warning',
          message: '当前时间已超确认邀请截止时间，不能进行确认'
        })
        return false
      }
      this.$router.push({path: `/lt/assignee/remise-invite/confirm/${scope.row.objectId}`, query: {roledsType: 2, showStatus: false}})
    },
    // 查看
    detailBtn (scope) {
      console.log(1111111111111111)
      this.$router.push({path: `/lt/assignee/remise-invite/detail/${scope.row.objectId}`, query: {roledsType: 2, showStatus: false}})
    },
    // ***************************************************************自己方法**********************************************************************
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '地块编号'
      } else {
        this.seacherName = '邀请书名称'
      }
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/land-register/common.css';
</style>
