<template>
  <div id="homeopenorg">
    <div class="homepagecontent">
      <publicside></publicside>
      <div class="homepageconright">
        <!--面包屑-->
        <el-breadcrumb class="breadcrumbbox" separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/organ/department' }">组织结构</el-breadcrumb-item>
          <el-breadcrumb-item>部门管理</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
        <!--内容开始-->
        <div class="penserviceconbox">
          <div class="penservicecontitle">组织结构</div>
          <div class="penserviceconconbox">
            <el-menu class="el-menu-demo zzjgbox" mode="horizontal" :router="true" :default-active="$route.path">
              <el-menu-item index="/organ/department">部门管理</el-menu-item>
              <el-menu-item index="/organ/role">角色管理</el-menu-item>
              <el-menu-item index="/organ/user">用户管理</el-menu-item>
            </el-menu>
          </div>
          <div class="organ_contentbigbox">
            <div class="organ_conleft_tree">
              <tree @handleNodeClick="clickTable"></tree>
            </div>
            <div class="organ_conright_tablebox">
              <div class="organ_conright_tabletop">
                <el-select class="inforbigbox" v-model="Selectvalue" placeholder="请选择" @change="userChange">
                  <el-option
                    v-for="item in informationSelst"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
                <el-input placeholder="请输入内容" style="vertical-align: top" v-model="searchinput" class="input-with-select roleseacher">
                  <el-button class="roleseacherbtn" slot="append" icon="el-icon-search" @click="search">搜索</el-button>
                </el-input>
              </div>
              <div class="organ_conright_tablebottom">
                <el-table
                  :data="usertableData"
                  border
                  style="width: 100%" header-cell-class-name="tableheader">
                  <el-table-column
                    type="index"
                    :index="dataIndex"
                    label="序号"
                    width="50" align="center">
                  </el-table-column>
                  <el-table-column
                    prop="name"
                    label="用户名"
                    width="200">
                  </el-table-column>
                  <el-table-column
                    prop="nameZH"
                    label="姓名"
                    width="100">
                  </el-table-column>
                  <el-table-column
                    prop="phone"
                    label="联系电话"
                    width="150">
                  </el-table-column>
                  <el-table-column
                    prop="dept.name"
                    label="所属部门">
                  </el-table-column>
                </el-table>
              </div>
              <!--分页-->
              <el-pagination
                background
                layout="prev, pager, next"
                :total="total"
                :page-size='pageSize'
                :current-page.sync="currentPage"
                @current-change="handleCurrentChange"
                @next-click="handleCurrentNext">
              </el-pagination>
              <!--分页-->
            </div>
          </div>
        </div>
        <!--内容开始-->
      </div>
    </div>
  </div>
</template>
<script>
import publicside from '../adminpublic/side.vue'
import tree from './tree/tree.vue'
import {organ} from '../../api/index'
import {addtitle} from '../../assets/js/common'
export default {
  components: {
    publicside,
    tree
  },
  data () {
    return {
      pageSize: 10,
      pageNo: 1,
      total: 0, // 总条数
      currentPage: 1,
      dataIndex: 1,
      searchIs: false,
      // 查询数据
      informationSelst: [ {
        value: 'nameZH',
        label: '姓名'
      }, {
        value: 'name',
        label: '用户名'
      }],
      Selectvalue: '',
      // 表格数据
      usertableData: [],
      searchinput: '',
      // 自定义选择内容
      searchSel: '',
      // 自定义查询部门的值
      deptnames: '',
      // 根部门id
      objectIds: '',
      // 自定义侧边表格初始数据
      depttableStatus: true,
      tableId: '529'
    }
  },
  methods: {
    // 第一次进入后的表数据
    bigList () {
      let url = null
      if (this.searchIs) {
        url = {
          enterpriseId: this.$store.getters.authUser.userId,
          pageNo: this.pageNo,
          pageSize: this.pageSize
        }
        if (this.Selectvalue == '') {
          this.$message({
            type: 'warning',
            message: '请选择查询方式'
          })
          return false
        } else if (this.Selectvalue == 'nameZH') {
          url.nameZH = this.searchinput
        } else if (this.Selectvalue == 'name') {
          url.name = this.searchinput
        }
        if (this.deptnames != '') {
          url.deptName = this.deptnames
        }
      } else {
        url = {
          enterpriseId: this.$store.getters.authUser.userId,
          pageNo: this.pageNo,
          pageSize: this.pageSize
        }
      }
      organ.userSearch(url).then((res) => {
        this.usertableData = res.data.userList
        this.total = res.data.total
        this.usertableData.map((ite) => {
          if (ite.phone === '"null"') {
            ite.phone = '---------'
          }
        })
        addtitle(this)
      })
    },
    // 侧边表格数据
    informationList () {
      let url = null
      if (this.searchIs) {
        url = {
          id: this.objectIds,
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          _t: new Date().getTime()
        }
        if (this.Selectvalue == 'nameZH') {
          url.nameZH = this.searchinput
        } else if (this.Selectvalue == 'name') {
          url.name = this.searchinput
        }
        if (this.deptnames != '') {
          url.deptName = this.deptnames
        }
      } else {
        url = {
          id: this.objectIds,
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          _t: new Date().getTime()
        }
      }
      organ.infoList(url).then((res) => {
        this.usertableData = res.data.userList
        this.total = res.data.total
        this.usertableData.map((ite) => {
          if (ite.phone === '"null"') {
            ite.phone = '---------'
          }
        })
        addtitle(this)
      })
    },
    // 搜索前选择搜索方式
    userChange (val) {
      this.Selectvalue = val
    },
    search () {
      this.currentPage = 1
      this.dataIndex = 1
      this.pageNo = 0
      let searchs = null
      if (!this.depttableStatus) {
        searchs = {
          enterpriseId: this.$store.getters.authUser.userId,
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          _t: new Date().getTime()
        }
      } else {
        searchs = {
          enterpriseId: this.$store.getters.authUser.userId,
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          _t: new Date().getTime()
        }
      }
      // 通过不同的查询进行参数赋值
      if (this.Selectvalue == '') {
        this.$message({
          type: 'warning',
          message: '请选择查询方式'
        })
        return false
      } else if (this.Selectvalue == 'name') {
        searchs.name = this.searchinput
      } else if (this.Selectvalue == 'nameZH') {
        searchs.nameZH = this.searchinput
      }
      if (this.deptnames != '') {
        searchs.deptName = this.deptnames
      }
      organ.userSearch(searchs).then((res) => {
        this.usertableData = res.data.userList
        this.total = res.data.total
        this.usertableData.map((ite) => {
          if (ite.phone === '"null"') {
            ite.phone = '---------'
          }
        })
        addtitle(this)
        this.currentPage = 1
        this.dataIndex = 1
        this.searchIs = true
      })
    },
    // 表单分页
    handleCurrentChange (nowNum) {
      this.pageNo = nowNum
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      if (!this.depttableStatus) {
        this.informationList(this.searchIs)
      } else {
        this.bigList(this.searchIs)
      }
    },
    handleCurrentNext (nowNum) {
      this.pageNo = nowNum
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      if (!this.depttableStatus) {
        this.informationList(this.searchIs)
      } else {
        this.bigList(this.searchIs)
      }
    },
    // 点击切换数据
    clickTable (d) {
      this.depttableStatus = false
      this.deptnames = d.name
      this.objectIds = d.id
      this.deptnames = d.name
      this.currentPage = 1
      this.dataIndex = 1
      this.pageNo = 0
      this.informationList()
    }
  },
  mounted () {
    this.bigList()
  }
}
</script>
<style lang="less">
  #homeopenorg{
    .zzjgbox .is-active{
      border-bottom: 2px solid #3f63f6;
      background:transparent!important;
      color:#3f63f6!important;
      border-left: 0px solid transparent;
    }
    .organ_contentbigbox{
      width: 100%;
      min-height:50px;
      /*background: red;*/
      margin-top: 30px;
    }
    .organ_conleft_tree{
      display: inline-block;
      /*background: gold;*/
      width: 30%;
      vertical-align: top;
      float: left;
    }
    .organ_conright_tablebox{
      width: 62%;
      min-height:50px;
      display: inline-block;
      vertical-align: top;
      /*background: gold;*/
      margin-left:3%;
      float: right;
    }
    .inforbigbox{
      width: 25%;
    }
    .roleseacher{
      width: 320px;
    }
    .roleseacherbtn{
      background:#3f63f6;
      color:#fff;
      border-radius: 0;
    }
    .organ_conright_tablebottom{
      margin-top: 10px;
      width: 100%;
      min-height:50px;
      background: red;
    }
    .el-table .cell {
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      word-break: break-all;
      height: 23px;
      line-height: 23px;
      overflow: hidden;
      text-overflow:ellipsis;
      white-space: nowrap;
    }
  }
</style>
