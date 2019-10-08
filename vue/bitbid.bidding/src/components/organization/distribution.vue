<template>
  <div id="homeodistradmin">
    <div class="homepagecontent">
      <publicside></publicside>
      <div class="homepageconright">
        <!--面包屑-->
        <el-breadcrumb class="breadcrumbbox" separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/organ/department' }">组织结构</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/organ/user' }">用户管理</el-breadcrumb-item>
          <el-breadcrumb-item>分配角色</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
        <!--内容开始-->
        <div class="penserviceconbox">
          <div class="penservicecontitle">分配角色</div>
          <div class="penserviceconconbox">
            <div class="seacherbigboix">
              <span>角色名称：</span>
              <el-input placeholder="" v-model="roleseacher" class="input-with-select roleseacher">
                <el-button class="roleseacherbtn" slot="append" icon="el-icon-search" @click="search" >搜索</el-button>
              </el-input>
            </div>
            <!--表格-->
            <div class="content_tablebox" style="margin-top: 20px">
              <template>
                <el-table
                  ref="multipleTable"
                  :data="rolesTable"
                  @select="handleSelectionChange"
                  @select-all="handleSelectionAll"
                  tooltip-effect="dark"
                  border
                  style="width: 100%">
                  <el-table-column
                    type="selection"
                    width="55" reserve-selection align="center">
                  </el-table-column>
                  <el-table-column
                    prop="name"
                    label="角色名称"
                    width="120">
                  </el-table-column>
                  <el-table-column
                    prop="description"
                    label="角色描述">
                  </el-table-column>
                </el-table>
              </template>
            </div>
            <!--表格-->
            <!--分页-->
            <el-pagination
              background
              layout="prev, pager, next"
              :total="total"
              :page-size='pageSize'
              :current-page.sync="currentPage"
              @current-change="handleCurrentChange">
            </el-pagination>
            <!--分页-->
            <div class="Determinebtnbox">
              <el-button type="primary" @click="Determinebtn">确 定</el-button>
              <el-button type="info" @click="cancelBtn">取 消</el-button>
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
import {useradmin} from '../../api/index'
import {addtitle} from '../../assets/js/common'
export default {
  components: {
    publicside
  },
  data () {
    return {
      // 搜索框默认值
      roleseacher: '',
      formLabelWidth: '120px',
      rolesTable: [],
      multipleSelection: [],
      pageSize: 10,
      pageNo: 0,
      total: 0,
      currentPage: 1,
      dataIndex: 1,
      searchIs: false,
      // 提交时存放id的数组
      idsArray: [],
      // 全选状态
      allStatus: false,
    }
  },
  methods: {
    // 列表接口
    distrList (search) {
      let url = null
      if (search) {
        url = {
          userId: this.$route.params.objectIds,
          creator: this.$store.getters.authUser.userId,
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          nameLike: this.roleseacher,
          isDelete: 0,
          _t: new Date().getTime()
        }
      } else {
        url = {
          userId: this.$route.params.objectIds,
          creator: this.$store.getters.authUser.userId,
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0,
          _t: new Date().getTime()
        }
      }
      useradmin.userDistribution(this.$route.params.enterpriseId, url).then((res) => {
        this.rolesTable = res.data.rolesList.list
        this.total = res.data.rolesList.total
        this.rolesTable.map((tableItem, index) => {
          if (tableItem.flag == 1) {
            this.$refs.multipleTable.toggleRowSelection(this.rolesTable[index], true)
          } else {
            this.$refs.multipleTable.toggleRowSelection(this.rolesTable[index], false)
          }
        })
        addtitle(this)
      })
    },
    // 列表表单分页
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this.distrList(this.searchIs)
    },
    // 查询数据
    search () {
      this.currentPage = 1
      this.dataIndex = 1
      this.pageNo = 0
      let parameter = {
        userId: this.$route.params.objectIds,
        creator: this.$store.getters.authUser.userId,
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        nameLike: this.roleseacher,
        isDelete: 0,
        _t: new Date().getTime()
      }
      useradmin.userDistrSearch(this.$route.params.enterpriseId, parameter).then((res) => {
        this.rolesTable = res.data.rolesList.list
        this.rolesTable.map((tableItem, index) => {
          if (tableItem.flag == 1) {
            this.$refs.multipleTable.toggleRowSelection(this.rolesTable[index], true)
          } else if (tableItem.flag == null) {
            this.$refs.multipleTable.toggleRowSelection(this.rolesTable[index], false)
          }
        })
        this.currentPage = 1
        this.dataIndex = 1
        this.pageNo = (this.currentPage - 1) * this.pageSize
        this.searchIs = true
        addtitle(this)
      })
    },
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
    //    handleSelectionChange (selection, row) {
    //      this.multipleSelection.push(row)
    //    },
    //    handleSelectionAll (selection) {
    //      if (selection.length === 0) {
    //        this.multipleSelection = []
    //      } else {
    //        this.multipleSelection = selection
    //        this.allStatus = true
    //      }
    //    },
    Determinebtn () {
      this.$confirm('确认保存吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'success'
      }).then(() => {
        this.multipleSelection.map((ids) => {
          this.idsArray.push(ids.objectId)
        })
        let Juris = {
          userId: this.$route.params.objectIds,
          roleIds: this.idsArray,
          // all: 0,
          enterpriseId: this.$store.getters.authUser.enterpriseId
        }
          //        if (this.allStatus) {
          //          Juris.all = 1
          //        }
        useradmin.userDistr(Juris).then((res) => {
          this.$router.push({path: '/organ/user'})
        })
      }).catch(() => {
        return false
      })
    },
    cancelBtn () {
      this.$confirm('确认取消分配角色吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'success'
      }).then(() => {
        this.$router.push({path: '/organ/user'})
      }).catch(() => {
        return false
      })
    }
  },
  mounted () {
    this.distrList()
  }
}
</script>
<style lang="less">
  #homeodistradmin{
    .zzjgbox .is-active{
      border-bottom: 2px solid #3f63f6;
      background:transparent!important;
      color:#3f63f6!important;
    }
    .seacherbigboix{
      font-size: 14px;
      margin-top:10px;
      color:#7d7d7d;
      padding: 0 20px;
    }
    .roleseacher{
      width: 320px;
      vertical-align: middle;
    }
    .roleseacherbtn{
      background:#3f63f6;
      color:#fff;
      border-radius: 0;
    }
    .btnbigbox{
      padding:20px;
    }
    .anniubtn{
      background:#3f63f6;
      color:#fff;
      border-radius: 0;
    }
    .operationbtn{
      text-align: center;
    }
    .addrole{
      display: inline-block;
    }
    .el-button--text {
      color: #a9a9a9;
      background: 0 0;
      padding-left: 0;
      padding-right: 0;
    }
    .el-button--text:hover{
      color:#3f63f6;
    }
    .el-button--info {
      color: #bcbcbc;
      background-color:#ededed;
      border-color: #ededed;
    }
    .searchuser {
      width: 150px;
    }
    .selectinput{
      width: 100%;
    }
    .contab_headerbox {
      width: 100%;
      height: 50px;
      border: 1px solid #eeeeee;
      box-sizing: border-box;
      background: #f5f7f8;
      line-height: 53px;
    }
    .contab_tablebox{
      width: 100%;
      height: 50px;
      border: 1px solid #eeeeee;
      box-sizing: border-box;
      background: #f5f7f8;
      line-height: 53px;
      border-top: none;
    }
    .grid-contentrole{
      /*background: red;*/
      height: 50px;
      border-right: 1px solid #eeeeee;
      box-sizing: border-box;
      font-size:14px;
      padding: 0 10px;
      box-sizing: border-box;
      color:#333333;
    }
    .checkboxinput{
      text-align: center;
      position: relative;
    }
    .elect_btn_img{
      width:20px;
      height:20px;
      position: absolute;
      background:url("../../../static/images/weixuan.png");
      background-size:20px 20px;
      left: 0;
      right: 0;
      top: 0;
      bottom: 0;
      margin: auto;
      outline: none;
      -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
      -webkit-appearance: none;
      border: 0;
      cursor: pointer;
    }
    .elect_btn_img:checked {
      background:url("../../../static/images/xuanzgong.png");
      background-size:20px 20px;
      cursor: pointer;
    }
    .paginationbox{
      text-align: center;
      margin-top: 30px;
    }
    .Determinebtnbox{
      margin-top: 30px;
      text-align: center;
    }
    .el-button--primary {
      color: #fff;
      background-color: #3f63f7;
      border-color: #3f63f7;
    }
  }
</style>
