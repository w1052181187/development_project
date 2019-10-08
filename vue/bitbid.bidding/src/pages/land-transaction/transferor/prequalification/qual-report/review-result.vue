<template>
  <div id="issuemissionindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false'}">首页</el-breadcrumb-item>
        <el-breadcrumb-item>资格预审会</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/prequalification/qual-report/index?roledsType=1&showStatus=false'}">提交资格审查报告</el-breadcrumb-item>
        <el-breadcrumb-item>设置审查结果</el-breadcrumb-item>
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
          header-cell-class-name="tabletitles"
          @selection-change="handleSelectionChange"
          @select-all="handleSelectionAll">
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
            prop="bidderName"
            label="投标人名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            type="selection"
            width="80"
            align="center">
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
        <!--提交按钮-->
        <div>
          <el-button type="primary" @click="submitForm" :loading="subStatus">提交</el-button>
          <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
        </div>
        <!--提交按钮-->
      </template>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      subStatus: false,
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
      projecttableData: [
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '关于安徽省铜陵市盛冲金多金属矿勘探探',
          plotsLabeled: '6091p',
          bidderName: '德力科技有限公司',
          objectId: 1
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '关于安徽省铜陵市盛冲金多金属矿勘探探',
          plotsLabeled: '6091p',
          bidderName: '德力科技有限公司',
          objectId: 2
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '关于安徽省铜陵市盛冲金多金属矿勘探探',
          plotsLabeled: '6091p',
          bidderName: '德力科技有限公司',
          objectId: 3
        }
      ]
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
    // 关联项目信息选择
    handleSelectionChange (selection, row) {
      if (selection.length === 0) {
        this.multipleSelection = []
      } else {
        let temp = []
        for (let i = 0; i < selection.length; i++) {
          if (!temp.includes(selection[i].objectId)) {
            temp.push(selection[i])
          }
        }
        this.multipleSelection = temp
      }
    },
    handleSelectionAll (selection) {
      if (selection.length === 0) {
        this.multipleSelection = []
      } else {
        this.multipleSelection = selection
      }
    },
    submitForm () {
      if (!this.multipleSelection) {
        this.$message({
          type: 'warning',
          message: '请先进行选择'
        })
        return false
      }
      console.log(this.multipleSelection)
    }
  },
  mounted () {
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
