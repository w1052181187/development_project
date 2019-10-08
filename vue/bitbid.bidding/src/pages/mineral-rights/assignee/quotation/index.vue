<template>
    <div id="indexbox">
      <div class="headertitle">
        <!--面包屑-->
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>项目管理</el-breadcrumb-item>
          <el-breadcrumb-item>登记项目</el-breadcrumb-item>
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
                <el-button  slot="append" icon="el-icon-search" type=""></el-button>
              </el-input>
            </div>
          </el-row>
          <!--添加按钮-->
          <!--弹出框-->
          <el-dialog title="选择类型" :visible.sync="dialogFormVisible" width="380px">
            <el-form :model="form">
              <el-form-item label="矿权类型：" :label-width="formLabelWidth">
                <el-select v-model="type">
                  <el-option
                    v-for="item in typeoptions"
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
            border>
            <el-table-column
              prop="sectionNumber"
              label="项目编号"
              align="left"
              width="400">
            </el-table-column>
            <el-table-column
              prop="sectionName"
              label="项目名称"
              align="left"
              width="500">
            </el-table-column>
            <el-table-column
              prop="transferType"
              label="出让类型"
              align="left" >
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
              align="left">
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
            <el-table-column
              prop="projectStatus"
              label="项目状态"
              align="left">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.projectStatus === 0">
                  <span class="statusbox gray"><i></i>未提交</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.projectStatus === 1">
                  <span class="statusbox blue"><i></i>备案审批中</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.projectStatus === 2">
                  <span class="statusbox green"><i></i>审批通过</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.projectStatus === 3">
                  <span class="statusbox red"><i></i>审批不通过</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作" width="140" align="center">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.projectStatus === 0">
                  <el-button type="text" size="small" @click="detailBtn(scope)">
                    查看
                  </el-button>
                  <el-button type="text" size="small" @click="updateBtn(scope)">
                    编辑
                  </el-button>
                  <el-button type="text" size="small">
                    删除
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.projectStatus === 1">
                  <el-button type="text" size="small" @click="detailBtn(scope)">
                    查看
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.projectStatus === 2">
                  <el-button type="text" size="small" @click="updateBtn(scope)">
                    变更
                  </el-button>
                  <el-button type="text" size="small" @click="detailBtn(scope)">
                    查看
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.projectStatus === 3">
                  <el-button type="text" size="small" @click="updateBtn(scope)">
                    修改
                  </el-button>
                  <el-button type="text" size="small" @click="detailBtn(scope)">
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
import { prProject } from '@/api'
export default {
  data () {
    return {
      seacher_input: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '项目名称',
      options: [{
        value: 1,
        label: '项目名称'
      }, {
        value: 2,
        label: '项目编号'
      }],
      value: 1,
      // 矿权类型
      typeoptions: [{
        value: 1,
        label: '探矿权'
      }, {
        value: 2,
        label: '采矿权'
      }],
      type: 1,
      dialogFormVisible: false,
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      formLabelWidth: '85px',
      projecttableData: [],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 50, // 总条数
      pageSize: 10 // 每页展示条数
    }
  },
  methods: {
    list () {
      let params = null
      params = {
        pageNo: this.pageNo,
        pageSize: this.pageSize
      }
      prProject.queryList(params).then((res) => {
        this.projecttableData = res.data.sectionPageInfo.list
        this.total = res.data.sectionPageInfo.total
      })
    },
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '项目名称'
      } else {
        this.seacherName = '项目编号'
      }
    },
    // 添加跳转 选择探矿、采矿确认跳转
    confirmBtn () {
      this.$router.push({path: '/mr/transferor/project/registration-project/add', query: {type: this.type, roledsType: 2}})
      this.dialogFormVisible = false
    },
    // 查看
    detailBtn (scope) {
      this.$router.push({path: '/mr/transferor/project/registration-project/detail', query: {name: scope.row.Selltype, roledsType: 2}})
    },
    // 编辑
    updateBtn (scope) {
      this.$router.push({path: '/mr/transferor/project/registration-project/update', query: {name: scope.row.Selltype, roledsType: 2}})
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  #indexbox{
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
    margin-top: 50px;
    .headertitle{
      width: 100%;
      height: 60px;
      line-height: 60px;
      color:#333333;
      font-size: 16px;
      text-align: left;
      padding: 0 20px;
      box-sizing: border-box;
    }
    .el-breadcrumb{
      height: 60px;
      line-height: 60px;
    }
    /*新增按钮*/
    .addbutton{
      float: left;
      background: #2c972f;
      border-color: #2c972f;
    }
    /*搜索*/
    .seacher_box {
      width: 540px;
      height: 36px;
      min-width: 50px;
      float: right;
      margin-bottom: 20px;
    }
    .select{
      width: 20%;
    }
    .search{
      width: 79%;
    }
    /*弹出框*/
    .el-form-item__content .el-select{
      float: left;
    }
    .el-dialog__header{
      overflow: hidden;
      border-bottom: 1px solid #f4f4f4;
    }
    .el-dialog__header .el-dialog__title{
      float: left;
    }
    .contentbigbox{
      width: 100%;
      min-height: 770px;
      background:#fff;
      padding:20px;
      box-sizing: border-box;
    }
    .el-table .cell {
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      word-break: break-all;
      line-height: 23px;
      overflow: hidden;
      text-overflow:ellipsis;
      white-space: nowrap;
    }
    .el-button--text {
      color: #108cee;
      background: 0 0;
      padding-left: 0;
      padding-right: 0;
    }
    .el-table td{
      min-width: 0;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      text-overflow: ellipsis;
      vertical-align: middle;
      position: relative;
      padding-bottom: 5px;
    }
    .statusbox i{
      width: 6px;
      height: 6px;
      display: inline-block;
      margin-right: 5px;
      background: #9a9a9a;
      -webkit-border-radius: 50%;
      -moz-border-radius: 50%;
      border-radius: 50%;
    }
    .blue i{
      background: #118be1;
    }
    .green i{
      background: #2f8d35;
    }
    .red i{
      background: #ff4144;
    }
    .statusbigbox button{
      float: left;
      margin-left: 10px;
    }
    .pagebox{
      margin-top: 15px;
      text-align: right;
    }
  }
</style>
