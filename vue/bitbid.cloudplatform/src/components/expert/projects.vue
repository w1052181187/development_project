<template>
<div>
  <!--内容开始-->
  <div class="content_bigbox" id="zhaotingcontent_bigbox">
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/expert' }">专家管理</el-breadcrumb-item>
      <el-breadcrumb-item class="active_bread">项目管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!--面包屑-->
    <!--内容标题-->
    <div class="content_tiltebox">
      <span class="bdmczzt">专家姓名：</span>
      <span class="gsmczzt" v-bind:title="$route.query.name">{{$route.query.name}}</span>
    </div>
    <!--内容标题-->
    <div class="nav_switchbox">
      <div class="nav_switch_son nav_switch_selsct">合作项目</div>
    </div>
    <!--新增归档按钮-->
    <div class="da_xz_ckbigbox" style="border: none">
        <el-button type="text" class="da_xzgd_btn" @click="dialogTableVisible = true">
          <span class="jia_zzt">+</span>
          <span>添加招标项目</span>
        </el-button>
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
           :index='dataIndex'
           align="center">
         </el-table-column>
         <el-table-column
           prop="sectionCode"
           label="标段编号"
           width="160">
         </el-table-column>
         <el-table-column
           prop="sectionName"
           label="标段名称"
           width="150">
         </el-table-column>
         <el-table-column
           prop="ownerName"
           label="业主名称"
           width="180">
         </el-table-column>
         <el-table-column
           prop="biaoduan"
           label="标段类型"
           width="180">
         </el-table-column>
         <el-table-column
           prop="judgeDate"
           label="评审时间"
           width="160">
         </el-table-column>
         <el-table-column
           prop="projectNature"
           label="项目性质"
           width="190">
         </el-table-column>
         <el-table-column
           fixed="right"
           label="操作"
           align="center">
           <template slot-scope="scope">
             <el-button
               type="text"
               size="small"
               @click="deleteProject(scope)">
               删除
             </el-button>
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
      @current-change="handleCurrentChange">
    </el-pagination>
    <!--分页-->
  </div>
  <!--内容开始-->
  <!--新增文档弹框-->
  <div id="zhaotingxjwdfather">
    <el-dialog title="增加标段" :visible.sync="dialogTableVisible" width="60%">
      <!--线下项目数据-->
        <el-form :model="projectform" class="formsbox" :rules="rules" ref="projectform" @submit.native.prevent>
          <el-form-item label="标段编号：" prop="sectionCode" :label-width="formLabelWidth">
            <el-input class="xxxm_xinxi" v-model="projectform.sectionCode" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="标段名称：" prop="sectionName" :label-width="formLabelWidth">
            <el-input class="xxxm_xinxi" v-model="projectform.sectionName" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="业主名称：" prop="ownerName"  :label-width="formLabelWidth">
            <el-input class="xxxm_xinxi" v-model="projectform.ownerName" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="标段类型：" prop="sectionClassify" :label-width="formLabelWidth">
            <el-cascader class="bdlxbox"
              expand-trigger="hover"
              :options="BidsType"
              v-model="biaoduanType"
              @change="handleChange">
            </el-cascader>
          </el-form-item>
          <el-form-item label="评审时间：" prop="judgeDate"  :label-width="formLabelWidth">
            <el-date-picker class="xxxm_xinxi"
              v-model="projectform.judgeDate"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              :picker-options="pickerOptions"
              placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
        </el-form>
      <!--线下项目数据-->
      <!--确认取消按钮-->
      <div class="que_qubigbox">
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogTableVisible = false">取 消</el-button>
        <el-button type="primary" @click="addPrject('projectform')">确 定</el-button>
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
import {checkResponse, addtitle} from '../../assets/js/common'
import * as constants from '../../assets/js/constants'
export default {
  data () {
    return {
      // 列表数据
      total: 0, // 总条数
      loading: true,
      pageSize: 15, // 每页展示条数
      pageNo: 0,
      dataIndex: 1,
      tableData: [],
      dialogTableVisible: false,
      // 提交表单数据
      projectform: {
        sectionCode: '',
        sectionName: '',
        ownerName: '',
        judgeDate: '',
        sectionClassify: '',
        expertCode: this.$route.query.code
      },
      radios: '',
      formLabelWidth: '120px',
      // 提交时验证
      rules: {
        sectionCode: [
          { required: true, message: '标段编号不能为空', trigger: 'blur' },
          { min: 1, max: 23, message: '请输入1~23个字符', trigger: ['blur', 'change'] }
        ],
        sectionName: [
          { required: true, message: '标段名称不能为空', trigger: 'blur' },
          {min: 1, max: 200, message: '请输入1~200个字符', trigger: ['blur', 'change']}
        ],
        sectionClassify: [
          { required: true, message: '请选择标段类型', trigger: 'blur' }
        ],
        judgeDate: [
          { required: true, message: '时间不能为空', trigger: 'change' }
        ],
        ownerName: [
          { required: true, message: '业主名称不能为空', trigger: 'blur' },
          { min: 1, max: 200, message: '请输入1~200个字符', trigger: ['blur', 'change'] }
        ]
      },
      biaoduanType: [],
      biaoduanTypetwo: [],
      BidsType: constants.bidsType,
      // 日期选择
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7
        }
      }
    }
  },
  methods: {
    // 列表展示
    projectCon () {
      this.axios.get(`expert-projects?pageNo=${this.pageNo}&pageSize=${this.pageSize}&expertCode=${this.$route.query.code}`).then((res) => {
        this.tableData = res.data.projectInformationInfo.list
        this.total = res.data.projectInformationInfo.total
        this.loading = false
        this.tableData.map((item, index) => {
          var type = ''
          item.sectionClassify = item.sectionClassify.split(',') // 遍历接收的每条数据使得标段类型由字符串转为数组
          constants.bidsType.map((item1) => {
            if (item.sectionClassify[0] == item1.value) {
              type += item1.label
              item1.children.map((item2) => {
                if (item.sectionClassify[1] == item2.value) {
                  type += item2.label
                  item2.children.map((item3) => {
                    if (item.sectionClassify[2] == item3.value) {
                      type += item3.label
                      item3.children.map((item4) => {
                        if (item.sectionClassify[3] == item4.value) {
                          type += item4.label
                        }
                      })
                    }
                  })
                }
              })
            }
          })
          item.biaoduan = type
        })
        addtitle(this)
      })
    },
    // 表单分页
    handleCurrentChange (nowNum) {
      this.loading = true
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this.pageNo = (nowNum - 1) * this.pageSize
      this.projectCon()
    },
    // 添加招标项目
    addPrject (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.projectform.expertCode = this.$route.query.code
          this.axios.post(`expert-projects`, this.projectform).then((res) => {
            checkResponse(this, res, () => {
              this.projectCon()
              this.projectform = {}
              this.biaoduanType = []
              this.dialogTableVisible = false
            })
          })
        } else {
          return false
        }
      })
    },
    // 删除
    deleteProject (scope) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.axios.delete(`expert-projects/${scope.row.objectId}`).then((res) => {
          checkResponse(this, res, () => {
            this.projectCon()
          })
        })
      }).catch(() => {
        return false
      })
    },
    handleChange () {
      this.projectform.sectionClassify = this.biaoduanType.toString()
    }
  },
  mounted () {
    this.projectCon()
  }
}
</script>
<style lang="less">
  /*内容开始*/
  #zhaotingcontent_bigbox{
      width: 1200px;
      background: #fff;
      min-height:50px;
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
    .gsmczzt {
      color: #666666;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      display: inline-block;
      width: 85%;
      vertical-align: top;
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
    .da_xzgd_btn:hover{
      background: #6582f8;
      opacity:1;
      color:#fff;
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
      background: yellow;
    }
    .el-table__header{
      width: 100%!important;
    }
    .el-table{
      font-size: 12px;
      font-weight: normal!important;
      color:#999999;
    }
    th {
      font-weight: normal!important;
      background: transparent!important;
      width: 100%!important;
    }
    .el-table_1_column_1{
      text-align: center!important;
    }
    th:last-child{
      text-align: center!important;
    }
    .is-leaf{
      background:#f5f7f8!important;
    }
    .el-table_1_column_1{
      text-align: center!important;
    }
    .has-gutter>tr {
      background-color: #f5f7f8!important;
    }
    .el-button--small>span{
      color: #647ef7!important;
    }
    .seacher_input {
      display: inline-block;
      width: 382px;
      height:36px;
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
    .seacher_btn>img {
      width: 16px;
      position: absolute;
      left: 0;
      right: 0;
      top: 0;
      bottom: 0;
      margin: auto;
    }
    .zzzz input{
      border: none;
    }
    .zzzz .el-input__suffix {
      right: 5px;
      -webkit-transition: all .3s;
      transition: all .3s;
      display: none;
    }
    .zzzz .el-cascader__label{
      background:#fff;
    }
  }

  #zhaotingxjwdfather{
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
    font-size:16px;
    color: #111111;
  }
  .xingzhi_box{
    width: 100%;
    height:52px;
    border-bottom: 1px solid #eeeeee;
    padding: 0 20px;
    box-sizing: border-box;
    font-size: 14px;
    color:#111111;
    margin-bottom:15px;
    margin-top: 15px;
  }
  .xinzhileft_box{
    float: left;
    height:80%;
  }
  .xinzhiright_box{
    float:right;
  }
  .el-input__inner {
    -webkit-appearance: none;
    background-image: none;
    border-radius:0px!important;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    display: inline-block;
    line-height: 40px;
    outline: 0;
    width: 120px;
    height: 35px;
    border: none;
    opacity: 0.8;
    color: #3f63f7!important;
    padding: 0 10px;
    outline: none;
    font-size: 14px;
    position: relative;
    text-align: center;
    cursor: pointer;
    -webkit-transition: border-color .2s cubic-bezier(.645,.045,.355,1);
    transition: border-color .2s cubic-bezier(.645,.045,.355,1);
  }
    .xxgdxz{
      background:#eceffe;
    }
  .seacher_input {
    display: inline-block;
    width: 382px;
    height:36px;
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
  .seacher_btn>img {
    width: 16px;
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    margin: auto;
  }
  .el-dialog{
    margin-top: 10vh!important;
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
    margin-top:20px;
    /* background: red; */
    text-align: center;
    padding-bottom:20px;
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
  .selectxxxm_bigbox{
    display: none;
    width: 50%;
    margin: 0 auto;
    margin-bottom: 120px;
  }
  .xxxm_xinxi>.el-input__inner{
    width: 420px;
    height:35px;
    border: 1px solid #eeeeee;
    box-sizing: border-box;
    text-align: left;
    background: transparent;
  }
  th {
      font-weight: normal!important;
      background: transparent!important;
      width: 100%!important;
    }
    .is-leaf {
      background: #f5f7f8!important;
    }
    .el-table_2_column_8>.cell{
      text-align:center;
    }
    .el-table_4_column_18{
      text-align: center;
    }
    .formsbox{
      width: 80%;
      margin: 0 auto;
      margin-top: 25px;
    }
    .el-icon-time:before {
      content: "\E642";
      display: none;
    }
    .el-icon-date:before {
      content: "\E608";
      display: none;
    }
  }
  .content_tiltebox {
    width: 100%;
    height: 60px;
    /* background: red; */
    line-height: 60px;
    padding: 0 20px;
    box-sizing: border-box;
    font-size: 15px;
  }
  .nav_switchbox {
    width: 100%;
    height: 52px;
    background: #e5f3ff;
    padding: 0 10px;
    box-sizing: border-box;
  }
  .nav_switch_son {
    width: 97px;
    height: 47px;
    background: #fff;
    float: left;
    margin-top: 5px;
    text-align: center;
    line-height: 47px;
    font-size: 14px;
    color: #111111;
    cursor: pointer;
  }
  .tab-item{
    width: 100%;
    height: 30px;
    text-align: center;
    line-height: 30px;
    font-size: 12px;
    color:#008bff;
  }
  .el-popover .el-button{
    width: 100%;
    text-align: center;
    border: none;
    font-size: 12px;
    color:#008bff;
  }
  .el-table_1_column_6{
    text-align: center!important;
  }
  .el-table .cell {
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    word-break: break-all;
    line-height: 23px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  .bdlxbox{
    width: 65%;
    border: 1px solid #eeeeee;
  }
</style>
