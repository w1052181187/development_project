<template>
  <div id="miningindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>矿业权登记公告</el-breadcrumb-item>
        <el-breadcrumb-item>提交矿业权登记公告</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--添加按钮-->
        <el-row>
          <el-button type="success" @click="dialogFormVisible = true" class="addbutton"> + 添加</el-button>
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
              <el-button  slot="append" icon="el-icon-search" @click="search"></el-button>
            </el-input>
          </div>
        </el-row>
        <!--添加按钮-->
        <!--弹出框-->
        <el-dialog title="选择类型" :visible.sync="dialogFormVisible" width="380px">
          <el-form :model="form">
            <el-form-item label="矿种类型：" :label-width="formLabelWidth">
              <el-select v-model="mineralSpecies" @change="mineralSpeciesTypeSelet">
                <el-option
                  v-for="item in mineralSpeciesType"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="矿权类型：" :label-width="formLabelWidth">
              <el-select v-model="MineralRights" @change="mineralSpeciesSelet">
                <el-option
                  v-for="item in MineralRightsType"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="confirmBtn">确 定</el-button>
          </div>
        </el-dialog>
        <!--弹出框-->
        <!--主要内容 table-->
        <el-table
          :data="projecttableData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="annoName"
            label="公告名称"
            align="left"
            width="350"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="projectNumber"
            label="项目编号"
            align="left"
            width="200"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="projectName"
            label="项目名称"
            align="left"
            width="350"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="mineralType"
            label="矿种类型"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="parseInt(scope.row.mineralType) === 1">
                <span class="statusbox">油气</span>
              </div>
              <div class="statusbigbox" v-if="parseInt(scope.row.mineralType) === 2">
                <span class="statusbox">非油气</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="Mineralrightstype"
            label="矿权类型"
            align="left">
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
            prop="status"
            label="公告状态"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 4">
                <span class="statusbox">已发布</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 3">
                <span class="statusbox">审批不通过</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
                <span class="statusbox">审批通过</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <span class="statusbox">备案审批中</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 0">
                <span class="statusbox">未提交</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="操作" width="140" align="center">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <el-button type="text" size="small" @click="lookBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 3">
                <el-button type="text" size="small" @click="updateBtn(scope)">
                  修改
                </el-button>
                <el-button type="text" size="small" @click="lookBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
                <el-button type="text" size="small" @click="releaseBtn(scope)">
                  发布
                </el-button>
                <el-button type="text" size="small" @click="alterationBtn(scope)">
                  变更
                </el-button>
                <el-button type="text" size="small" @click="lookBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 4">
                <el-button type="text" size="small" @click="lookBtn(scope)">
                  查看
                </el-button>
              </div>
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
import { annoProject } from '@/api'
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
      value: 1,
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      dialogFormVisible: false,
      formLabelWidth: '85px',
      form: {},
      // 矿种
      mineralSpeciesType: [{
        value: 1,
        label: '油气'
      }, {
        value: 2,
        label: '非油气'
      }],
      mineralSpecies: 1,
      mineralSpeciesName: '油气',
      mineralSpeciesNumber: 1,
      // 矿权类型
      MineralRightsType: [{
        value: 1,
        label: '探矿权'
      }, {
        value: 2,
        label: '采矿权'
      }],
      MineralRights: 1,
      MineralRightsName: '探矿权',
      MineralRightsNumber: 1,
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
          isDelete: 0
        }
        if (this.seacherName == '项目名称') {
          url.projectName = this.seacher_input
        } else if (this.seacherName == '项目编号') {
          url.projectNumber = this.seacher_input
        } else if (this.seacherName == '公告名称') {
          url.annoName = this.seacher_input
        }
      } else {
        url = {
          isDelete: 0,
          pageNo: this.pageNo,
          pageSize: this.pageSize
        }
      }
      annoProject.queryList(url).then((res) => {
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
        isDelete: 0
      }
      if (this.seacherName == '项目名称') {
        searchs.projectNames = this.seacher_input
      } else if (this.seacherName == '项目编号') {
        searchs.projectNumbers = this.seacher_input
      } else if (this.seacherName == '公告名称') {
        searchs.annoNames = this.seacher_input
      }
      annoProject.queryList(searchs).then((res) => {
        this.projecttableData = res.data.miningAnnoPageInfo.list
        this.total = res.data.miningAnnoPageInfo.total
        this.searchIs = true
      })
    },
    deleteBtn (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        annoProject.delete(scope.row.objectId).then((res) => {
          this.list()
        })
      }).catch(() => {
        return false
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
        this.seacherName = '公告名称'
      } else if (val === 2) {
        this.seacherName = '项目名称'
      } else {
        this.seacherName = '项目编号'
      }
    },
    // 添加时矿种的选择
    mineralSpeciesTypeSelet (val) {
      if (val === 1) {
        this.mineralSpeciesName = '油气'
        this.mineralSpeciesNumber = 1
      } else {
        this.mineralSpeciesName = '非油气'
        this.mineralSpeciesNumber = 2
      }
    },
    // 添加时矿权的选择
    mineralSpeciesSelet (val) {
      if (val === 1) {
        this.MineralRightsName = '探矿权'
        this.MineralRightsNumber = 1
      } else {
        this.MineralRightsName = '采矿权'
        this.MineralRightsNumber = 2
      }
    },
    // 添加
    confirmBtn () {
      this.dialogFormVisible = false
      this.$router.push({path: '/mr/transferor/mining-register-announcement/sub-mining-register/add', query: {mineralSpecies: this.mineralSpeciesNumber, MineralRights: this.MineralRightsNumber, roledsType: 1}})
    },
    // 修改
    updateBtn (scope) {
      this.$router.push({path: '/mr/transferor/mining-register-announcement/sub-mining-register/update', query: {objectId: scope.row.objectId, mineralSpecies: scope.row.mineralType, MineralRights: scope.row.miningType, roledsType: 1}})
    },
    // 查看
    lookBtn (scope) {
      this.$router.push({path: `/mr/transferor/mining-register-announcement/sub-mining-register/detail/${scope.row.objectId}`, query: {objectId: scope.row.objectId, mineralSpecies: scope.row.mineralType, MineralRights: scope.row.miningType, roledsType: 1}})
    },
    // 发布
    releaseBtn (scope) {
      this.$router.push({path: '/mr/transferor/mining-register-announcement/sub-mining-register/release', query: {objectId: scope.row.objectId, mineralSpecies: scope.row.mineralType, MineralRights: scope.row.miningType, roledsType: 1}})
    },
    // 变更
    alterationBtn (scope) {
      this.$router.push({path: '/mr/transferor/mining-register-announcement/sub-mining-register/update', query: {objectId: scope.row.objectId, mineralSpecies: scope.row.mineralType, MineralRights: scope.row.miningType, roledsType: 1}})
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
  #miningindexbox{
    .el-form-item__label {
      width: 168px!important;
    }
    .el-form-item__content {
      margin-left: 85px!important;
    }
  }
</style>
