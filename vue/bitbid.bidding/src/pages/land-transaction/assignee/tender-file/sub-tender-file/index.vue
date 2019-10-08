<template>
  <div id="land_pro_patchReg" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招标、投标文件</el-breadcrumb-item>
        <el-breadcrumb-item>递交投标文件</el-breadcrumb-item>
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
            prop="annoNumber"
            label="公告名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="sectionNumber"
            label="地块编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="deadlineTime"
            label="投标文件递交截止时间"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" width="140" align="center">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="subBtn(scope)">
                递交
              </el-button>
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
export default {
  data () {
    return {
      seacherInput: '',
      seacherName: '公告名称',
      selectOptions: [{
        value: 1,
        label: '公告名称'
      }, {
        value: 2,
        label: '地块编号'
      }],
      inquiryValue: 1, // 搜索默认值
      // 列表表格
      projectData: [
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '皖探转让公示〔2018〕3号',
          deadlineTime: '2018-7-26 18:00:00',
          publishedEndTime: '2018-7-26 18:00:00',
          methodOfTransfer: 1,
          objectId: 1,
          isClarify: 0
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '皖探转让公示〔2018〕3号',
          deadlineTime: '2019-7-26 18:00:00',
          publishedEndTime: '2018-7-26 18:00:00',
          methodOfTransfer: 2,
          objectId: 1,
          isClarify: 1
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '皖探转让公示〔2018〕3号',
          deadlineTime: '2018-7-26 18:00:00',
          publishedEndTime: '2018-7-26 18:00:00',
          methodOfTransfer: 3,
          objectId: 1,
          isClarify: 0
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '皖探转让公示〔2018〕3号',
          deadlineTime: '2018-7-26 18:00:00',
          publishedEndTime: '2018-7-26 18:00:00',
          methodOfTransfer: 4,
          objectId: 1,
          isClarify: 1
        }
      ],
      currentPage: 1, // 当前页
      pageNo: 0, // 页数
      total: 30, // 总条数
      pageSize: 10 // 每页展示条数
    }
  },
  methods: {
    // ***************************************************************数据接口方法**********************************************************************
    // -----------------------------------------列表-----------------------------------------
    list () {
      let url = null
      url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0
      }
      if (this.seacherInput !== '') { // 当搜索内容不为空时执行
        if (this.seacherName === '公告名称') {
          url.annoName = this.seacherInput
        } else if (this.seacherName === '地块编号') {
          url.sectionNumber = this.seacherInput
        }
      }
      console.log(url)
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
      this.searchIs = true
      this.list()
    },
    // ***************************************************************自己方法**********************************************************************
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '公告名称'
      } else {
        this.seacherName = '地块编号'
      }
    },
    // -----------------------------------------查看----------------------------------------------
    subBtn (scope) {
      // 获取当前时间
      let times = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate() + ' ' + new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds()
      // 进行转换
      let currentTime = new Date(Date.parse(times.replace(/-/g, '/'))).getTime()
      // 获取截止时间
      let cutOffTime = new Date(Date.parse(scope.row.deadlineTime.replace(/-/g, '/'))).getTime()
      if (currentTime > cutOffTime) { // 进行对比
        this.$message({
          type: 'warning',
          message: '当前时间已超申请文件递交截止时间，不能进行提交'
        })
        return false
      }
      this.$router.push({path: `/lt/assignee/tender-file/sub-tender-file/submit/${scope.row.objectId}`, query: {roledsType: 2, isClarify: scope.row.isClarify}})
    }
  },
  mounted () {}
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
