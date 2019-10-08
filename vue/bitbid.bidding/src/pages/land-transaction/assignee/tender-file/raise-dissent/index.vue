<template>
  <div id="issuemissionindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招标、投标文件</el-breadcrumb-item>
        <el-breadcrumb-item>提出异议</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--搜索按钮-->
        <el-row>
          <div class="seacher_box">
            <el-select v-model="value" class="select" @change="selectSeacher">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-input class="search" style="vertical-align: top"  :placeholder="'请输入' + seacherName" v-model="seacherInput">
              <el-button  slot="append" icon="el-icon-search" @click="search"></el-button>
            </el-input>
          </div>
        </el-row>
        <!--搜索按钮-->
        <!--主要内容 table-->
        <el-table
          :data="projecttableData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="landAnnoMation.annoNumber"
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
            prop="landAnnoMation.answerStartTime"
            label="答疑开始时间"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="landAnnoMation.answerEndTime"
            label="答疑截止时间"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="acceptAllQuestion"
            label="受理/质疑"
            align="left"
            width="160"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            label="操作" width="160" align="center">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="questionBtn(scope)">
                质疑
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
import {landProject} from '@/api'
export default {
  data () {
    return {
      seacherInput: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '公告名称',
      options: [ {
        value: 1,
        label: '公告名称'
      }, {
        value: 2,
        label: '地块编号'
      }],
      value: 1,
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      projecttableData: []
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
        status: 2
      }
      if (this.seacherInput !== '') { // 当搜索内容不为空时执行
        if (this.seacherName === '公告名称') {
          url.annoName = this.seacherInput
        } else if (this.seacherName === '地块编号') {
          url.sectionNumber = this.seacherInput
        }
      }
      landProject.queryListForQues(url).then(res => {
        this.projecttableData = res.data.landInfoPageInfo.list
        this.projecttableData.map((ite) => {
          ite.acceptAllQuestion = ite.acceptCount + '/' + ite.allQuestionCount
        })
        console.log(this.projecttableData)
        this.total = res.data.landInfoPageInfo.total
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
    // -----------------------------------------搜索-------------------------------------------
    search () {
      this.currentPage = 1
      this.pageNo = 0
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
    questionBtn (scope) {
      this.$router.push({path: '/lt/assignee/tender-file/raise-dissent/question', query: {roledsType: 2, objectId: scope.row.objectId}})
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
