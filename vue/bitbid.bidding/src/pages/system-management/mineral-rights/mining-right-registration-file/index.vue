<template>
  <div id="indexbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>矿权交易备案</el-breadcrumb-item>
        <el-breadcrumb-item>矿业权登记公告备案</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <!--搜索-->
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
            <el-button  slot="append" icon="el-icon-search" type="" @click="search"></el-button>
          </el-input>
        </div>
      </el-row>
      <!--搜索-->
      <template>
        <el-table
          :data="projecttableData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="annoName"
            label="公告名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="projectNumber"
            label="项目编号"
            align="center"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="projectName"
            label="项目名称"
            align="center"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="mineralType"
            label="矿种类型"
            align="center">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.mineralType === 1">
                <span class="statusbox">油气</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.mineralType === 2">
                <span class="statusbox">非油气</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="Mineralrightstype"
            label="矿权类型"
            align="center">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.miningType === 1">
                <span class="statusbox">探矿权</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.miningType === 2">
                <span class="statusbox">采矿权</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="lastUpdateDate"
            label="提交时间"
            align="center">
          </el-table-column>
          <el-table-column
            prop="status"
            label="公告状态"
            align="center">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 4">
                <span class="statusbox gray"><i></i>已发布</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 3">
                <span class="statusbox red"><i></i>审批不通过</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
                <span class="statusbox green"><i></i>审批通过</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <span class="statusbox blue"><i></i>备案审批中</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 0">
                <span class="statusbox">未提交</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" width="140" align="center">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <el-button type="text" size="small" @click="checkBtn(scope)">
                  审批
                </el-button>
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 3">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 4">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          class="pagebox"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :total="total"
          :page-size='pageSize'
          layout="prev, pager, next, jumper">
        </el-pagination>
      </template>
    </div>
  </div>
</template>
<script>
import { sannoProject } from '@/api'
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
        label: '项目名称'
      }, {
        value: 3,
        label: '项目编号'
      }],
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 30, // 总条数
      currentPage: 1,
      value: '',
      dialogFormVisible: false,
      formLabelWidth: '85px',
      form: {},
      // 矿种
      mineralSpeciesType: [{
        value: '1',
        label: '油气'
      }, {
        value: '2',
        label: '非油气'
      }],
      mineralSpecies: '1',
      mineralSpeciesName: '油气',
      mineralSpeciesNumber: 1,
      // 矿权类型
      MineralRightsType: [{
        value: '1',
        label: '探矿权'
      }, {
        value: '2',
        label: '采矿权'
      }],
      MineralRights: '1',
      MineralRightsName: '探矿权',
      MineralRightsNumber: 1,
      projecttableData: []
    }
  },
  methods: {
    list () {
      let params = null
      params = {
        selectStatus: 0,
        isDelete: 0,
        pageNo: this.pageNo,
        pageSize: this.pageSize
      }
      if (this.seacherName == '项目名称') {
        params.projectNames = this.seacher_input
      } else if (this.seacherName == '项目编号') {
        params.projectNumbers = this.seacher_input
      } else if (this.seacherName == '公告名称') {
        params.annoNames = this.seacher_input
      }
      sannoProject.queryList(params).then((res) => {
        this.projecttableData = res.data.miningAnnoPageInfo.list
        this.total = res.data.miningAnnoPageInfo.total
      })
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      let searchs = null
      searchs = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        _t: new Date().getTime()
      }
      // 通过不同的查询进行参数赋值
      if (this.seacherName == '') {
        this.$message({
          type: 'warning',
          message: '请选择查询方式'
        })
        return false
      } else if (this.seacherName == '项目名称') {
        searchs.projectNames = this.seacher_input
      } else if (this.seacherName == '项目编号') {
        searchs.projectNumbers = this.seacher_input
      } else if (this.seacherName == '公告名称') {
        searchs.annoNames = this.seacher_input
      }
      sannoProject.queryList(searchs).then((res) => {
        this.projecttableData = res.data.miningAnnoPageInfo.list
        this.total = res.data.miningAnnoPageInfo.total
      })
    },
    deleteBtn (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        sannoProject.delete(scope.row.objectId).then((res) => {
          this.list()
        })
      }).catch(() => {
        return false
      })
    },
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
    },
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '公告名称'
      } else if (val === 2) {
        this.seacherName = '项目名称'
      } else {
        this.seacherName = '项目编号'
      }
    },
    // 添加时矿种的选择
    mineralSpeciesTypeSelet (val) {
      if (val === '1') {
        this.mineralSpeciesName = '油气'
        this.mineralSpeciesNumber = 1
      } else {
        this.mineralSpeciesName = '非油气'
        this.mineralSpeciesNumber = 2
      }
    },
    // 添加时矿权的选择
    mineralSpeciesSelet (val) {
      if (val === '1') {
        this.MineralRightsName = '探矿权'
        this.MineralRightsNumber = 1
      } else {
        this.MineralRightsName = '采矿权'
        this.MineralRightsNumber = 2
      }
    },
    // 查看
    detailBtn (scope) {
      this.$router.push({path: `/system-management/mineral-rights/mining-right-registration-file/detail/${scope.row.objectId}`, query: {objectId: scope.row.objectId, mineralSpecies: scope.row.mineralType, MineralRights: scope.row.miningType}})
    },
    // 审核
    checkBtn (scope) {
      this.$router.push({path: '/system-management/mineral-rights/mining-right-registration-file/check', query: {objectId: scope.row.objectId, mineralSpecies: scope.row.mineralType, MineralRights: scope.row.miningType}})
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
</style>
