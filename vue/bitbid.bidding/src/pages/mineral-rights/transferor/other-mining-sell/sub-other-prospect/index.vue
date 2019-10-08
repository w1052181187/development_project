<template>
  <div id="otherprospindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>其他方式出让矿业权</el-breadcrumb-item>
        <el-breadcrumb-item>提交{{this.$route.query.name}}公示</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--添加按钮-->
        <el-row>
          <el-button type="success" class="addbutton" @click="addBtn"> + 添加</el-button>
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
        <!--主要内容 table-->
        <el-table
          :data="projecttableData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="publicityName"
            label="公示名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="projectNumber"
            label="项目编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="projectName"
            label="项目名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="status"
            label="公示状态"
            align="left"
            width="140">
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
              <div class="statusbigbox" v-if="scope.row.status === 4">
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
              <div class="statusbigbox" v-if="scope.row.status === 1">
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
import { otherProject } from '@/api'
export default {
  data () {
    return {
      seacher_input: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '公示名称',
      options: [{
        value: 1,
        label: '公示名称'
      }, {
        value: 2,
        label: '项目名称'
      }, {
        value: 3,
        label: '项目编号'
      }],
      value: 1,
      // 本页矿权类型
      MineralRights: this.$route.query.type,
      // 切换进行赋值
      types: '',
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      searchIs: false,
      projecttableData: []
    }
  },
  methods: {
    list () {
      let params = null
      if (this.searchIs) {
        params = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          type: this.types,
          isDelete: 0
        }
        if (this.seacherName == '项目名称') {
          params.projectNames = this.seacher_input
        } else if (this.seacherName == '项目编号') {
          params.projectNumbers = this.seacher_input
        } else if (this.seacherName == '公示名称') {
          params.publicityNames = this.seacher_input
        }
      } else {
        params = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          type: this.types,
          isDelete: 0
        }
      }
      otherProject.queryList(params).then((res) => {
        this.projecttableData = res.data.otherPublicityPageInfo.list
        this.total = res.data.otherPublicityPageInfo.total
      })
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      let searchs = null
      searchs = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        type: this.types,
        isDelete: 0
      }
      if (this.seacherName == '项目名称') {
        searchs.projectNames = this.seacher_input
      } else if (this.seacherName == '项目编号') {
        searchs.projectNumbers = this.seacher_input
      } else if (this.seacherName == '公示名称') {
        searchs.publicityNames = this.seacher_input
      }
      otherProject.queryList(searchs).then((res) => {
        this.projecttableData = res.data.otherPublicityPageInfo.list
        this.total = res.data.otherPublicityPageInfo.total
        this.searchIs = true
      })
    },
    deleteBtn (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        otherProject.delete(scope.row.objectId).then((res) => {
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
        this.seacherName = '公示名称'
      } else if (val === 2) {
        this.seacherName = '项目名称'
      } else {
        this.seacherName = '项目编号'
      }
    },
    // 添加
    addBtn () {
      this.$router.push({path: '/mr/transferor/other-mining-sell/sub-other-prospect/add', query: {name: this.$route.query.name, type: this.$route.query.type, roledsType: 1}})
    },
    // 修改
    updateBtn (scope) {
      this.$router.push({path: '/mr/transferor/other-mining-sell/sub-other-prospect/update', query: {objectId: scope.row.objectId, name: this.$route.query.name, type: this.$route.query.type, roledsType: 1}})
    },
    // 变更
    alterationBtn (scope) {
      this.$router.push({path: '/mr/transferor/other-mining-sell/sub-other-prospect/update', query: {objectId: scope.row.objectId, name: this.$route.query.name, type: this.$route.query.type, roledsType: 1}})
    },
    // 查看
    lookBtn (scope) {
      this.$router.push({path: `/mr/transferor/other-mining-sell/sub-other-prospect/detail/${scope.row.objectId}`, query: {objectId: scope.row.objectId, name: this.$route.query.name, type: this.$route.query.type, roledsType: 1}})
    },
    // 发布
    releaseBtn (scope) {
      this.$router.push({path: '/mr/transferor/other-mining-sell/sub-other-prospect/release', query: {objectId: scope.row.objectId, name: this.$route.query.name, type: this.$route.query.type, roledsType: 1}})
    }
  },
  watch: {
    $route () {
      this.MineralRights = parseInt(this.$route.query.type)
      if (this.MineralRights === 1) {
        this.types = 1
      } else if (this.MineralRights === 2) {
        this.types = 2
      } else {
        this.types = 3
      }
      this.list()
    }
  },
  mounted () {
    if (parseInt(this.$route.query.type) === 1) {
      this.types = 1
    } else if (parseInt(this.$route.query.type) === 2) {
      this.types = 2
    } else {
      this.types = 3
    }
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
</style>
