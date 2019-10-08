<template>
  <div id="selectionindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让邀请</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/remise-invite/submit-invite?roledsType=1' }">提交出让邀请</el-breadcrumb-item>
        <el-breadcrumb-item>选择出让项目</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox ">
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
            <el-input class="search" style="vertical-align: top"  :placeholder="'请输入' + seacherName" v-model="seacher_input">
              <el-button  slot="append" icon="el-icon-search"  @click="search"></el-button>
            </el-input>
          </div>
        </el-row>
        <!--搜索按钮-->
        <!--主要内容 table-->
        <el-table
          ref="multipleTable"
          :data="projecttableData"
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
            label="项目编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="sectionName"
            label="项目名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="transferType"
            label="出让类型"
            align="left"
            width="120">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.transferType === 1">
                <span class="statusbox">探矿权</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.transferType === 2">
                <span class="statusbox">采矿权</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="landUse"
            label="出让方式"
            align="left"
            width="120"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.landUse === 1">
                <span class="statusbox">拍卖</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.landUse === 2">
                <span class="statusbox">挂牌</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.landUse === 3">
                <span class="statusbox">其他</span>
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
import {prProject} from '@/api'
export default {
  data () {
    return {
      seacher_input: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '项目名称',
      multipleSelection: [],
      options: [{
        value: 1,
        label: '项目名称'
      }, {
        value: 2,
        label: '项目编号'
      }],
      pageSize: 100,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      value: 1,
      projecttableData: [],
      searchIs: false
    }
  },
  methods: {
    list () {
      let url = null
      if (this.searchIs) {
        url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0,
          isChecked: 0,
          status: 2
        }
        if (this.seacherName == '项目名称') {
          url.sectionNames = this.seacher_input
        } else if (this.seacherName == '项目编号') {
          url.sectionNumbers = this.seacher_input
        }
      } else {
        url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0,
          isChecked: 0,
          status: 2
        }
      }
      prProject.queryList(url).then((res) => {
        this.projecttableData = res.data.sectionPageInfo.list
        this.total = res.data.sectionPageInfo.total
      })
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      let searchs = null
      searchs = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0,
        isChecked: 0,
        status: 2
      }
      if (this.seacherName == '项目名称') {
        searchs.sectionNames = this.seacher_input
      } else if (this.seacherName == '项目编号') {
        searchs.sectionNumbers = this.seacher_input
      }
      prProject.queryList(searchs).then((res) => {
        this.projecttableData = res.data.sectionPageInfo.list
        this.total = res.data.sectionPageInfo.total
        this.searchIs = true
      })
    },
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list(this.searchIs)
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list(this.searchIs)
    },
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '项目名称'
      } else {
        this.seacherName = '项目编号'
      }
    },
    // 公告进行选择
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
    // 提交选择的公告
    submitForm () {
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择公告'
        })
        return false
      }
      if (this.multipleSelection.length === 1) {
        let idArry = []
        idArry.push(this.multipleSelection[0].objectId)
        if (this.multipleSelection[0].landUse === 1) {
          this.$router.push({path: '/mr/transferor/submission/subminssion-notice/add', query: {landUse: 1, idArry: idArry, roledsType: 1}})
        } else if (this.multipleSelection[0].landUse === 2) {
          this.$router.push({path: '/mr/transferor/submission/subminssion-notice/add', query: {landUse: 2, idArry: idArry, roledsType: 1}})
        }
      } else {
        let arry = []
        let idArry = []
        this.multipleSelection.map((ite) => {
          arry.push(ite.landUse)
          idArry.push(ite.objectId)
        })
        let newArr = Array.from(new Set(arry))
        if (newArr.length === 2) {
          this.$message({
            type: 'warning',
            message: '出让方式不一致，请重新选择'
          })
          return false
        } else if (newArr.length === 1) {
          if (newArr[0] === 1) {
            this.$router.push({path: '/mr/transferor/submission/subminssion-notice/add', query: {landUse: 1, idArry: idArry, roledsType: 1}})
          } else {
            this.$router.push({path: '/mr/transferor/submission/subminssion-notice/add', query: {landUse: 2, idArry: idArry, roledsType: 1}})
          }
        }
      }
    },
    cancelBtn () {
      this.$router.push({path: '/mr/transferor/submission/subminssion-notice?roledsType=1'})
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
</style>
