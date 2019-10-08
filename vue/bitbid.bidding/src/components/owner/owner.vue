<script src="../../../../../../备份/广信/src/assets/js/common.js"></script>
<template>
  <div>
    <!--内容开始-->
    <div class="content_bigbox" id="zztcontent_bigbox">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item class="active_bread">业主管理</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <!--新增归档按钮-->
      <div class="da_xz_ckbigbox" style="border: none">
        <el-button type="text" class="da_xzgd_btn" @click="dialogTableVisible = true" v-if="$store.getters.permissions.includes('/owners/add')">
          <span class="jia_zzt">+</span>
          <span>新增业主</span>
        </el-button>
        <div class="seacher_box">
          <span>分类：</span>
          <el-select class="fenleizztselet" v-model="classificationvalue">
            <el-option
              v-for="item in classificationoptions"
              :key="item.classifyName"
              :label="item.classifyName"
              :value="item.classifyName">
            </el-option>
          </el-select>
          <el-input class="fenleiselect" style="vertical-align: top"  placeholder="请输入业主名称关键字" v-model="seacher_input">
            <el-button @click="search" slot="append" icon="el-icon-search" type=""></el-button>
          </el-input>
        </div>
      </div>
      <!--新增归档按钮-->
      <!--表格内容开始-->
      <div class="da_contabbigbox">
        <el-table
          :data="tableData"
          border
          style="width: 100%">
          <el-table-column
            type="index"
            label="序号"
            width="50"
            :index='dataIndex' align="center">
          </el-table-column>
          <el-table-column
            prop="enterpriseName"
            label="业主名称"
            width="200">
          </el-table-column>
          <el-table-column
            prop="contactName"
            label="联系人"
            width="130">
          </el-table-column>
          <el-table-column
            prop="contactPhone"
            label="联系电话"
            width="140">
          </el-table-column>
          <el-table-column
            prop="email"
            label="邮箱"
            width="120">
          </el-table-column>
          <el-table-column
            prop="ownerClassify"
            label="分类"
            width="110">
          </el-table-column>
          <el-table-column
            prop="createDate"
            label="创建时间"
            width="150">
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" align="center">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="seeData(scope)" v-if="$store.getters.permissions.includes('/owners/detail')">
                   查看
              </el-button>
              <el-button type="text" size="small" @click="editFun(scope)" v-if="$store.getters.permissions.includes('/owners/update')">
                   编辑
              </el-button>
              <!--更多弹框-->
              <el-tooltip placement="top" effect="light">
                <div slot="content">
                  <template>
                    <!--<el-button type="text" size="small" @click="open2">邀请激活</el-button>-->
                    <el-button
                      @click="ownerPro(scope)"
                      type="text"
                      size="small"  v-if="$store.getters.permissions.includes('/owners/projects')">
                      项目管理
                    </el-button>
                    <el-button type="text" size="small" @click="deleteOwner(scope)" v-if="$store.getters.permissions.includes('/owners/delete')">
                      删除
                    </el-button>
                  </template>
                </div>
                <el-button  type="text" size="small" >更多</el-button>
              </el-tooltip>
              <!--更多弹框-->
            </template>
          </el-table-column>
        </el-table>
      </div>
      <!--表格内容开始-->
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
    </div>
    <!--内容开始-->
    <!--新增文档弹框-->
    <div id="zztxjwdfather">
      <el-dialog title="选择业主" :visible.sync="dialogTableVisible" width="80%" :before-close="cancelBtn">
        <!--线上线下选择-->
        <!--<div class="xingzhi_box">-->
          <!--<div class="xinzhileft_box">-->
            <!--<span>业主状态：</span>-->
            <!--<el-select class="xxgdxz" v-model="value" placeholder="请选择" @change="changeValue">-->
              <!--<el-option-->
                <!--v-for="item in options"-->
                <!--:key="item.value"-->
                <!--:label="item.label"-->
                <!--:value="item.value">-->
              <!--</el-option>-->
            <!--</el-select>-->
          <!--</div>-->
          <!--<div class="xinzhiright_box" v-if="showStatus === false">-->
            <!--<el-select class="xxgdxz" v-model="value2" placeholder="请选择">-->
              <!--<el-option-->
                <!--v-for="item in options2"-->
                <!--:key="item.value"-->
                <!--:label="item.label"-->
                <!--:value="item.value">-->
              <!--</el-option>-->
            <!--</el-select>-->
            <!--<el-input placeholder="请输入内容" v-model="inputseacher" class="input-with-select" v-if="showStatus === false">-->
              <!--<el-button slot="append" icon="el-icon-search"></el-button>-->
            <!--</el-input>-->
          <!--</div>-->
        <!--</div>-->
        <!--线上线下选择-->
        <!--线下项目数据-->
        <div class="selectxxxm_bigbox" v-if="showStatus === true" style="margin-top:25px">
          <el-form :model="form" :rules="rules" ref="form" @submit.native.prevent>
            <el-form-item label="业主名称：" prop="enterpriseName" :label-width="formLabelWidth">
              <el-input class="xxxm_xinxi" v-model="form.enterpriseName" auto-complete="off"></el-input>
            </el-form-item>
          </el-form>
        </div>
        <!--线下项目数据-->
        <!--线上项目数据-->
        <div class="select_tabbox" v-if="showStatus === false">
          <!--表格数据-->
          <el-table :data="gridData" border>
            <el-table-column
              label="单选"
              width="90">
              <template slot-scope="scope" class="danxuan">
                <input type="radio" name="radio">
              </template>
            </el-table-column>
            <el-table-column
              prop="myname"
              label="用户名"
              width="500">
            </el-table-column>
            <el-table-column
              prop="qiyename"
              label="企业名称">
            </el-table-column>
          </el-table>
          <!--表格数据-->
        </div>
        <!--线上项目数据-->
        <!--确认取消按钮-->
        <div class="que_qubigbox">
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancel('form')">取 消</el-button>
          <el-button type="primary" @click="ownerAdd('form')">确 定</el-button>
        </span>
        </div>
        <!--确认取消按钮-->
      </el-dialog>
    </div>
    <!--新增文档弹框-->
  </div>
</template>
<!--本页css-->
<script>
  import {checkResponse} from '../../assets/js/common'
  export default {
    data () {
      return {
        // 搜索的input
        seacher_input: '',
        // 业主列表数据
        tableData: [],
        total: 0, // 总条数
        loading: true,
        pageSize: 15, // 每页展示条数
        pageNo: 0,
        currentPage: 1,
        dataIndex: 1,
        searchIs: false,
        // 提交时验证
        rules: {
          enterpriseName: [
            { required: true, message: '企业名称不能为空', trigger: 'blur' },
            { min: 1, max: 100, message: '请输入1~100个字符', trigger: ['blur', 'change'] }
          ]
        },
        classificationoptions: [],
        classificationvalue: '全部',
        // 提交时form提交
        form: {
          enterpriseName: ''
        },
        // 弹出框select数据
        options: [
          {
            value: 1,
            label: '已激活用户'
          },
          {
            value: 2,
            label: '未激活用户'
          }
        ],
        value: 2,
        options2: [
          {
            value: '选项1',
            label: '用户名'
          },
          {
            value: '选项2',
            label: '企业名称'
          }
        ],
        value2: '用户名',
        // 弹出框表单数据
        gridData: [],
        dialogTableVisible: false,
        formLabelWidth: '120px',
        // 自定义选项卡值
        tab1: true,
        tab2: false,
        inputseacher: '',
        // 显示状态
        showStatus: true
      }
    },
    methods: {
      // ********************************************************************业主列表********************************************************************
      // 列表数据展示
      ownerData (search) {
        let url = null
        console.log(this.$store.getters.authUser)
        if (search) {
          url = `owner-infos?pageNo=${this.pageNo}&pageSize=${this.pageSize}&enterpriseId=${this.$store.getters.authUser.ownerUserId}&userId=${this.$store.getters.authUser.userId}&messageLike=${encodeURIComponent(this.seacher_input)}&ownerClassify=${encodeURIComponent(this.classificationvalue)}&_t=${new Date().getTime()}`
        } else {
          url = `owner-infos?pageNo=${this.pageNo}&pageSize=${this.pageSize}&enterpriseId=${this.$store.getters.authUser.ownerUserId}&userId=${this.$store.getters.authUser.userId}&_t=${new Date().getTime()}`
        }
        this.axios.get(url).then((res) => {
          this.tableData = res.data.ownerInfo.list
          this.tableData.map((its) => {
            if (its.contactPhone === '') {
              its.contactPhone = '---------------'
            }
            if (its.email === '') {
              its.email = '---------------'
            }
          })
          this.total = res.data.ownerInfo.total
          this.loading = false
        })
      },
      // 分类数据获取
      selectClassify () {
        this.axios.get(`classify-infos/getClassify?relatedModuleType=${1}&enterpriseId=${this.$store.getters.authUser.ownerUserId}&_t=${new Date().getTime()}`).then((res) => {
          this.classificationoptions = res.data.classifyInfos
          let wholeObject = {
            classifyName: '全部'
          }
          this.classificationoptions.push(wholeObject)
        })
      },
      // 表单分页
      handleCurrentChange (nowNum) {
        this.loading = true
        this.pageNo = (nowNum - 1) * this.pageSize
        this.dataIndex = (nowNum - 1) * this.pageSize + 1
        this.ownerData(this.searchIs)
      },
      // 增加业主
      ownerAdd (addForm) {
        this.$refs[addForm].validate((valid) => {
          if (valid) {
            this.axios.get(`owner-infos/check-enterpriseName/${this.form.enterpriseName}`).then((res) => {
              checkResponse(this, res, () => {
                this.$router.push({path: '/owner/add', query: {data: this.form}})
              })
            })
          } else {
            return false
          }
        })
      },
      // 删除
      deleteOwner (scope) {
        this.$confirm('此操作将永久删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.axios.delete(`owner-infos/${scope.row.objectId}`).then((res) => {
            checkResponse(this, res, () => {
              this.ownerData()
            })
          })
        }).catch(() => {
          return false
        })
      },
      // 修改
      editFun (scope) {
        this.$router.push({path: '/owner/update', query: {objectId: scope.row.objectId}})
      },
      // 搜索功能
      search () {
        this.currentPage = 1
        this.dataIndex = 1
        this.pageNo = 0
        this.loading = true
        this.axios.get(`owner-infos?pageNo=${this.pageNo}&pageSize=${this.pageSize}&enterpriseId=${this.$store.getters.authUser.ownerUserId}&userId=${this.$store.getters.authUser.userId}&messageLike=${encodeURIComponent(this.seacher_input)}&ownerClassify=${encodeURIComponent(this.classificationvalue)}&_t=${new Date().getTime()}`).then((res) => {
          this.loading = false
          this.tableData = res.data.ownerInfo.list
          this.tableData.map((its) => {
            if (its.contactPhone === '') {
              its.contactPhone = '---------------'
            }
            if (its.email === '') {
              its.email = '---------------'
            }
          })
          this.total = res.data.ownerInfo.total
          this.currentPage = 1
          this.dataIndex = 1
          this.pageNo = (this.currentPage - 1) * this.pageSize
          this.searchIs = true
        })
      },
      // 单条查看
      seeData (scope) {
        this.$router.push({path: `/owner/detail/${scope.row.objectId}`, query: {data: scope, objectId: scope.row.objectId, code: scope.row.code}})
      },
      // 跳转项目管理
      ownerPro (scope) {
        this.$router.push({path: '/owner/project', query: {objectId: scope.row.objectId,bitbidObjectId: scope.row.bitbidObjectId, code: scope.row.code, enterpriseName: scope.row.enterpriseName}})
      },
      // 线下线上切换
      changeValue (value) {
        if (value === 1) {
          this.showStatus = false
        } else {
          this.showStatus = true
        }
      },
      cancel (formName) {
        this.dialogTableVisible = false
        this.value = 2
        this.showStatus = true
        this.form.enterpriseName = ''
        this.$refs[formName].resetFields()
      },
      cancelBtn () {
        this.dialogTableVisible = false
        this.$refs['form'].resetFields()
      }
    },
    mounted () {
      this.ownerData()
      this.selectClassify()
    }
  }
</script>
<style lang="less">
  /*内容开始*/
  #zztcontent_bigbox {
    width: 1200px;
    background: #fff;
    min-height: 50px;
    margin: 0 auto;
    margin-top: 23px;
    /*新建归档按钮*/
    .da_xz_ckbigbox {
      width: 100%;
      height: 58px;
      border-bottom: 1px solid #eeeeee;
      padding: 0 20px;
      box-sizing: border-box;
    }
    .da_xzgd_btn {
      width: 120px;
      height: 36px;
      background: #6582f8;
      opacity: 0.8;
      color: #fff;
      font-size: 14px;
      text-align: center;
      vertical-align: middle;
      float: left;
      margin-top: 11px;
      cursor: pointer;
      transition: all .8s ease;
      border-radius: 0;
    }
    .da_xzgd_btn:hover {
      background: #6582f8;
      opacity: 1;
      color: #fff;
    }
    .seacher_box {
      height: 36px;
      min-width: 50px;
      float: right;
      /* background: red; */
      margin-top: 11px;
    }
    /*表格内容开始*/
    .da_contabbigbox {
      width: 100%;
      min-height: 10px;
    }
    .el-table__header {
      width: 100% !important;
    }
    .el-table {
      font-size: 12px;
      font-weight: normal !important;
      color: #999999;
    }
    th {
      font-weight: normal !important;
      background: transparent !important;
      width: 100% !important;
    }
    .el-table_1_column_1 {
      text-align: center !important;
    }
    th:last-child {
      text-align: center !important;
    }
    .is-leaf {
      background: #f5f7f8 !important;
    }
    .el-table_1_column_1 {
      text-align: center !important;
    }
    .has-gutter > tr {
      background-color: #f5f7f8 !important;
    }
    .el-button--small > span {
      color: #647ef7 !important;
    }
    .seacher_input {
      display: inline-block;
      width: 382px;
      height: 36px;
      border: 1px solid #cccccc;
      vertical-align: top;
      padding: 0 10px;
      box-sizing: border-box;
      font-size: 13px;
    }
    .seacher_btn {
      display: inline-block;
      width: 36px;
      height: 36px;
      background: #ebeffe;
      vertical-align: top;
      margin-left: -3px;
      position: relative;
      cursor: pointer;
      transition: all .8s ease;
    }
    .seacher_btn > img {
      width: 16px;
      position: absolute;
      left: 0;
      right: 0;
      top: 0;
      bottom: 0;
      margin: auto;
    }
    .el-table .cell {
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      word-break: break-all;
      height: 23px;
      line-height: 23px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }

  #zztxjwdfather {
    /*弹框内容样式*/
    .el-dialog__body {
      padding: 0px 0px;
      color: #606266;
      line-height: 24px;
      font-size: 14px;
    }
    .el-dialog__header {
      padding: 20px 20px 10px;
      border-bottom: 1px solid #eee;
    }
    .el-dialog__title {
      line-height: 24px;
      font-size: 16px;
      color: #111111;
    }
    .xingzhi_box {
      width: 100%;
      height: 52px;
      border-bottom: 1px solid #eeeeee;
      padding: 0 20px;
      box-sizing: border-box;
      font-size: 14px;
      color: #111111;
      margin-bottom: 15px;
      margin-top: 15px;
    }
    .xinzhileft_box {
      float: left;
      height: 80%;
    }
    .xinzhiright_box {
      float: right;
    }
    .el-input__inner {
      -webkit-appearance: none;
      background-image: none;
      border-radius: 0px !important;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      display: inline-block;
      line-height: 40px;
      outline: 0;
      width: 140px;
      height: 35px;
      border: none;
      opacity: 0.8;
      color: #3f63f7 !important;
      padding: 0 10px;
      outline: none;
      font-size: 14px;
      position: relative;
      text-align: left;
      cursor: pointer;
      -webkit-transition: border-color .2s cubic-bezier(.645, .045, .355, 1);
      transition: border-color .2s cubic-bezier(.645, .045, .355, 1);
      border: 1px solid #dcdfe6;
    }
    .xxgdxz {
      background: #eceffe;
    }
    .seacher_input {
      display: inline-block;
      width: 382px;
      height: 36px;
      border: 1px solid #cccccc;
      vertical-align: top;
      padding: 0 10px;
      box-sizing: border-box;
      font-size: 13px;
    }
    .seacher_btn {
      display: inline-block;
      width: 36px;
      height: 36px;
      background: #ebeffe;
      vertical-align: top;
      margin-left: -3px;
      position: relative;
      cursor: pointer;
      transition: all .8s ease;
    }
    .seacher_btn > img {
      width: 16px;
      position: absolute;
      left: 0;
      right: 0;
      top: 0;
      bottom: 0;
      margin: auto;
    }
    .el-dialog {
      margin-top: 10vh !important;
    }
    .el-radio-group {
      display: inline-block;
      line-height: 1;
      vertical-align: middle;
      font-size: 0;
      float: left;
      margin-left: 20px;
    }
    .que_qubigbox {
      width: 100%;
      height: 35px;
      margin-top: 20px;
      /* background: red; */
      text-align: center;
      padding-bottom: 20px;
    }
    .queren_btn {
      display: inline-block;
      width: 130px;
      height: 35px;
      background: #3f63f7;
      text-align: center;
      line-height: 35px;
      color: #fff;
      font-size: 14px;
      vertical-align: top;
      margin-right: 45px;
      cursor: pointer;
    }
    .quxiao_btn {
      display: inline-block;
      width: 130px;
      height: 35px;
      background: #ededed;
      text-align: center;
      line-height: 35px;
      color: #676767;
      font-size: 14px;
      vertical-align: top;
      cursor: pointer;
    }
    .selectxxxm_bigbox {
      /*display: none;*/
      width: 50%;
      margin: 0 auto;
      margin-bottom: 120px;
    }
    .xxxm_xinxi > .el-input__inner {
      width: 420px;
      height: 35px;
      border: 1px solid #eeeeee;
      box-sizing: border-box;
      text-align: left;
      background: transparent;
    }
    th {
      font-weight: normal !important;
      background: transparent !important;
      width: 100% !important;
    }
    .is-leaf {
      background: #f5f7f8 !important;
    }
    .el-table_2_column_8 > .cell {
      text-align: center;
    }
    .el-table_4_column_18 {
      text-align: center;
    }
    .el-input-group {
      line-height: normal;
      display: inline-table;
      width: 40%;
      border-collapse: separate;
    }
  }

  .tab-item {
    width: 100%;
    height: 30px;
    text-align: center;
    line-height: 30px;
    font-size: 12px;
    color: #008bff;
  }

  .el-popover .el-button {
    width: 100%;
    text-align: center;
    border: none;
    font-size: 12px;
    color: #008bff;
  }

  .el-button--text {
    color: #409EFF;
    background: 0 0;
    padding-left: 0;
    padding-right: 0;
    margin-left: 10px;
  }
  .fenleizztselet{
    width: 20%;
  }
  .fenleiselect{
    width: 65%;
  }
</style>
