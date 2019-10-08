<template>
  <div id="selectionindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让公告</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/announcement/sub-announ?roledsType=1&showStatus=false' }">提交出让公告</el-breadcrumb-item>
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
                <span class="statusbox">招拍挂出让</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="methodOfTransfer"
            label="出让方式"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 1">
                <span class="statusbox">拍卖</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 2">
                <span class="statusbox">挂牌</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 3">
                <span class="statusbox">资格后审</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 4">
                <span class="statusbox">资格预审</span>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <!--主要内容 table-->
        <!--按钮-->
        <div class="btnbigbox">
          <el-button type="primary" @click="submitForm">添加出让公告</el-button>
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
      multipleSelectionId: [], // 选择后表格数据
      searchData: [{
        value: 1,
        label: '地块编号'
      }],
      pageSize: 100,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      searchValue: 1,
      // 项目表格数据
      projectData: [],
      projectData1: []
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
        status: 2,
        isDelete: 0
        // 供应方式:只查询(招拍挂出让)
        // methodOfSupply: 1
      }
      if (this.seacherInput !== '') { // 当搜索内容不为空时执行
        if (this.seacherName === '地块编号') {
          url.sectionNumber = this.seacherInput
        }
      }
      // url.methodOfTransfers = '4'
      landProject.queryList(url).then(res => {
        console.log(res)
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
      this.projectData = []
      this.list()
    },
    // ----------------------------------------------------分页--------------------------------------
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.projectData = []
      this.list()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.projectData = []
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
      // 选择项目后进行判断
      if (this.multipleSelection.length === 1) { // 只选一个项目进行判断
        if (this.multipleSelection[0].methodOfTransfer === 1) {
          this.$router.push({path: '/lt/transferor/announcement/sub-announ/add', query: {methodOfTransfer: 1, roledsType: 1, landInfoIds: this.multipleSelectionId}})
        } else if (this.multipleSelection[0].methodOfTransfer === 2) {
          this.$router.push({path: '/lt/transferor/announcement/sub-announ/add', query: {methodOfTransfer: 2, roledsType: 1, landInfoIds: this.multipleSelectionId}})
        } else if (this.multipleSelection[0].methodOfTransfer === 3) {
          this.$router.push({path: '/lt/transferor/announcement/sub-announ/add', query: {methodOfTransfer: 3, roledsType: 1, landInfoIds: this.multipleSelectionId}})
        } else if (this.multipleSelection[0].methodOfTransfer === 4) {
          this.$router.push({path: '/lt/transferor/announcement/sub-announ/add', query: {methodOfTransfer: 4, roledsType: 1, landInfoIds: this.multipleSelectionId}})
        }
      } else { // 选择多个项目进行判断
        let arry = []
        this.multipleSelection.map((ite) => {
          arry.push(ite.methodOfTransfer)
        })
        let newArr = Array.from(new Set(arry))
        if (newArr.length === 2) { // 出让方式不一致
          this.$message({
            type: 'warning',
            message: '出让方式不一致，请重新选择'
          })
          return false
        } else if (newArr.length === 1) { // 出让方式一致
          if (newArr[0] === 1) {
            this.$router.push({path: '/lt/transferor/announcement/sub-announ/add', query: {methodOfTransfer: 1, roledsType: 1, landInfoIds: this.multipleSelectionId}})
          } else if (newArr[0] === 2) {
            this.$router.push({path: '/lt/transferor/announcement/sub-announ/add', query: {methodOfTransfer: 2, roledsType: 1, landInfoIds: this.multipleSelectionId}})
          } else if (newArr[0] === 3) {
            this.$router.push({path: '/lt/transferor/announcement/sub-announ/add', query: {methodOfTransfer: 3, roledsType: 1, landInfoIds: this.multipleSelectionId}})
          } else {
            this.$router.push({path: '/lt/transferor/announcement/sub-announ/add', query: {methodOfTransfer: 4, roledsType: 1, landInfoIds: this.multipleSelectionId}})
          }
        }
      }
    },
    // ----------------------------------------------------取消--------------------------------------
    cancelBtn () {
      this.$router.push({path: '/lt/transferor/announcement/sub-announ?roledsType=1&showStatus=false'})
    },
    // ***************************************************************自己方法**********************************************************************
    // ----------------------------------------------------选择项目--------------------------------------
    // 单独选择
    handleSelectionChange (selection, row) {
      if (selection.length === 0) {
        this.multipleSelection = []
        this.multipleSelectionId = []
      } else {
        let temp = []
        let tempId = []
        for (let i = 0; i < selection.length; i++) {
          if (!temp.includes(selection[i].objectId)) {
            temp.push(selection[i])
            tempId.push(selection[i].objectId)
          }
        }
        this.multipleSelection = temp
        this.multipleSelectionId = tempId
      }
    },
    // 全部选择
    handleSelectionAll (selection) {
      if (selection.length === 0) {
        this.multipleSelection = []
        this.multipleSelectionId = []
      } else {
        this.multipleSelection = selection
        this.multipleSelectionId = selection.objectId
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
