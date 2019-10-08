<template>
  <div id="auctionrecorddetail">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>拍卖</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/auction/index?roledsType=2&showStatus=false' }">拍卖竞价</el-breadcrumb-item>
        <el-breadcrumb-item>查看竞价纪录</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <template>
        <!--主要内容 table-->
        <el-form :model="ruleForm"  label-width="100px" class="demo-ruleForm">
          <!--项目信息-->
          <div class="proinfobox">
            <span class="title">项目信息</span>
            <div class="formmian">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="项目名称：">
                    <span>{{ruleForm.projectName}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="项目编号：">
                    <span>{{ruleForm.projectNumber}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="公告名称：">
                    <span>{{ruleForm.annoNumber}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="地块编号：">
                    <span>{{ruleForm.sectionNumber}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
          <!--项目信息-->
          <!--竞价纪录-->
          <div class="proinfobox">
            <span class="title" style="border-left: 3px solid #2d9631">竞价纪录</span>
            <el-table
              :data="auctiontableData"
              border
              header-row-class-name="gray">
              <el-table-column
                type="index"
                label="报价轮次"
                width="150"
                align="center">
              </el-table-column>
              <el-table-column
                prop="userName"
                label="竞买人编号"
                align="left">
              </el-table-column>
              <el-table-column
                prop="createDate"
                label="报价时间"
                align="left">
              </el-table-column>
              <el-table-column
                prop="offer"
                label="报价金额（万元）"
                align="left">
              </el-table-column>
            </el-table>
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
          </div>
          <!--竞价纪录-->
        </el-form>
        <!--主要内容 table-->
      </template>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      ruleForm: {
        projectName: '关于安徽省铜陵市盛冲金多',
        projectNumber: '关于安徽省铜陵市盛冲金多',
        annoNumber: '关于安徽省铜陵市盛冲金多',
        sectionNumber: '关于安徽省铜陵市盛冲金多'
      },
      auctiontableData: [
        {
          index: '5',
          userName: '竞买人100038',
          createDate: '2018-8-20 13:55:3',
          offer: '210'
        },
        {
          index: '5',
          userName: '竞买人100038',
          createDate: '2018-8-20 13:55:3',
          offer: '210'
        },
        {
          index: '5',
          userName: '竞买人100038',
          createDate: '2018-8-20 13:55:3',
          offer: '210'
        }
      ],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 50, // 总条数
      pageSize: 10 // 每页展示条数
    }
  },
  methods: {
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    list () {}
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  #auctionrecorddetail{
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
    .proinfobox{
      padding: 20px;
      background: #ffffff;
      margin-bottom: 20px;
      overflow: hidden;
    }
    .proinfobox span.title{
      display: block;
      height: 20px;
      border-left: 3px solid #fe8900;
      padding-left: 20px;
      text-align: left;
      margin-bottom: 20px;
    }
    .el-form-item__label{
      width: 168px!important;
    }
    .el-form-item__content{
      margin-left: 168px!important;
    }
    .el-form-item__content .el-input{
      float: left;
    }
    .el-form-item__content .el-select{
      float: left;
      width: 100%;
    }
    .el-form-item__content .el-select .el-input{
      width: 100%;
    }
    .el-upload{
      float: left;
    }
    .el-button--primary{
      background-color: #2c972f;
      border-color: #2c972f;
    }
    .el-upload-list{
      width: 60%;
      float: right;
    }
    .upload{
      position: relative;
    }
    .upload input[type="file"]{
      position: absolute;
      left: 0;
      top: 0;
      width: 100px;
      height: 38px;
      font-size: 50px;
      opacity: 0;
      cursor: pointer;
    }
    .upload_btn{
      display: inline-block;
      line-height: 1;
      white-space: nowrap;
      cursor: pointer;
      -webkit-appearance: none;
      text-align: center;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      outline: 0;
      margin: 0;
      -webkit-transition: .1s;
      transition: .1s;
      padding: 12px 20px;
      font-size: 14px;
      border-radius: 4px;
      background-color: #2c972f;
      border-color: #2c972f;
      float: left;
      color: #ffffff;
    }
    .upload_file{
      display: inline-block;
      float: right;
      max-width: 60%;
    }
    .position .el-form-item__content{
      width: 100%;
    }
    .position .el-cascader{
      width: 20%;
      float: left;
      margin-right: 2%;
    }
    .position .el-cascader .el-input{
      width: 100%;
    }
    .position .el-input{
      width: 65%;
      float: left;
    }
    .area .el-form-item__content{
      width: 87%;
    }
    .submit-radio{
      width: 100%;
      text-align: left;
    }
    .submit-radio .el-form-item__content{
      margin-left: 0px!important;
    }
    .submit-radio .el-form-item__content .el-button{
      width: 83px;
      height: 32px;
      line-height: 32px;
      padding: 0;
    }
    .submit-radio .el-form-item__content .save{
      background: #ff8a00;
      border-color: #ff8a00;
    }
    .gray th{
      background: #f7f8fa!important;
    }
    .el-form span{
      color: #353535;
    }
    .el-form span.file{
      color: #00aaff;
      cursor: pointer;
    }
    .el-form .blue span{
      color: #00aaff!important;
    }
    .gray th{
      background: #f7f8fa!important;
    }
    .hiddeninput .el-input{
      display: none!important;
    }
  }
</style>
