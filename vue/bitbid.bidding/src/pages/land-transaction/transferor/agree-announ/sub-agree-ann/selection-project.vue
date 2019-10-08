<template>
  <div id="selectionindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>协议公告</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/sub-agree-ann?roledsType=1&showStatus=false' }">提交协议出让公告</el-breadcrumb-item>
        <el-breadcrumb-item>选择项目</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox ">
      <template>
        <!--搜索按钮-->
        <el-row>
          <div class="seacher_box">
            <el-select v-model="searchValue" class="select">
              <el-option
                v-for="item in searchData"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-input class="search" style="vertical-align: top"  :placeholder="'请输入' + seacherName" v-model="seacherInput">
              <el-button  slot="append" icon="el-icon-search"  @click="search"></el-button>
            </el-input>
          </div>
        </el-row>
        <!--搜索按钮-->
        <!--主要内容 table-->
        <el-table
          ref="multipleTable"
          :data="projectData"
          tooltip-effect="dark"
          style="width: 100%"
          border
          header-cell-class-name="tabletitles"
          @selection-change="handleSelectionChange"
          @select-all="handleSelectionAll">
          <el-table-column
            type="selection"
            width="55" align="center">
          </el-table-column>
          <el-table-column
            prop="sectionNumber"
            label="地块编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="methodOfSupply"
            label="供应方式"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox">
                <span class="statusbox">协议出让</span>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <!--主要内容 table-->
        <!--按钮-->
        <div class="btnbigbox">
          <el-button type="primary" @click="submitForm">添加公告</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </div>
        <!--按钮-->
      </template>
    </div>
  </div>
</template>
<script>
import { landProject } from '@/api'
export default {
  data () {
    return {
      seacherInput: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '地块编号', // 输入框默认值
      multipleSelection: [], // 选择后表格数据
      searchData: [{
        value: 1,
        label: '地块编号'
      }],
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      searchValue: 1,
      // 项目表格数据
      projectData: []
    }
  },
  methods: {
    // ***************************************************************数据接口方法**********************************************************************
    // ----------------------------------------------------列表--------------------------------------
    list () {
      let url = null
      url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0,
        status: 2,
        methodOfSupply: 2
      }
      if (this.seacherInput !== '') { // 当搜索内容不为空时执行
        if (this.seacherName === '地块编号') {
          url.massifNumber = this.seacherInput
        }
      }
      landProject.queryList(url).then(res => {
        res.data.landInformationPageInfo.list.forEach((item) => {
          landProject.count(item.objectId).then(res1 => {
            if (res1.data.count == 0) {
              this.projectData.push(item)
            }
          })
        })
        this.total = res.data.landInformationPageInfo.total
      })
    },
    // ----------------------------------------------------搜索--------------------------------------
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.list()
    },
    // ----------------------------------------------------分页--------------------------------------
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    // ----------------------------------------------------提交--------------------------------------
    submitForm () {
      // 未选择项目进行判断
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择项目'
        })
        return false
      }
      this.$router.push({path: '/lt/transferor/sub-agree-ann/add?roledsType=1&showStatus=false', query: {landInfoIds: this.multipleSelection}})
    },
    // ----------------------------------------------------取消--------------------------------------
    cancelBtn () {
      this.$router.push({path: '/lt/transferor/sub-agree-ann?roledsType=1&showStatus=false'})
    },
    // ***************************************************************自己方法**********************************************************************
    // ----------------------------------------------------选择项目--------------------------------------
    // 单独选择
    handleSelectionChange (selection, row) {
      if (selection.length === 0) {
        this.multipleSelection = []
      } else {
        let temp = []
        for (let i = 0; i < selection.length; i++) {
          if (!temp.includes(selection[i].objectId)) {
            temp.push(selection[i].objectId)
          }
        }
        this.multipleSelection = temp
      }
    },
    // 全部选择
    handleSelectionAll (selection) {
      if (selection.length === 0) {
        this.multipleSelection = []
      } else {
        this.multipleSelection = selection.objectId
      }
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
