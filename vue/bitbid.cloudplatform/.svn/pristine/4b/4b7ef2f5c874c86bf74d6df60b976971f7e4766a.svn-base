<!--eslint-disable-->
<template>
  <div>
    <!--内容开始-->
    <div class="content_bigbox" id="zhangLookcontent">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/owner' }">业主管理</el-breadcrumb-item>
        <el-breadcrumb-item class="active_bread">查看业主</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <!--内容标题-->
      <el-form class="headerzzt_title" :model="enterpriseform" label-width="120px">
        <el-form-item label="业主名称：">
          <el-input v-model="enterpriseform.enterpriseName" readonly></el-input>
        </el-form-item>
      </el-form>
      <!--内容标题-->
      <!--导航切换-->
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="企业信息" name="first">
          <!--标段信息内容-->
          <div class="ckdan_contentbox">
            <el-row type="flex" class="row-bg">
              <el-form :model="enterpriseform">
                <el-col :span="12">
                  <el-form-item label="业主名称：" :label-width="formLabelWidth">
                    <el-input v-model="enterpriseform.enterpriseName" readonly></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <div class="grid-content bg-purple">
                    <el-form-item label="企业证件号码：" :label-width="formLabelWidth">
                      <el-input v-model="enterpriseform.enterpriseIdNumber" readonly></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="法人姓名：" :label-width="formLabelWidth">
                    <el-input v-model="enterpriseform.legalPersonName" readonly></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <div class="grid-content bg-purple">
                    <el-form-item label="法人身份证号：" :label-width="formLabelWidth">
                      <el-input v-model="enterpriseform.legalPersonId" readonly></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="12">
                  <div class="grid-content bg-purple">
                    <el-form-item label="注册地址：" :label-width="formLabelWidth">
                      <el-cascader class="zcdz" disabled
                         expand-trigger="hover"
                         :options="addressOptions"
                         v-model="selectedOptions">
                      </el-cascader>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="12">
                  <div class="grid-content bg-purple">
                    <el-form-item label="详细地址：" :label-width="formLabelWidth">
                      <el-input v-model="enterpriseform.address" readonly v-bind:title="enterpriseform.address"></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="12">
                  <div class="grid-content bg-purple">
                    <el-form-item label="联系人：" :label-width="formLabelWidth">
                      <el-input v-model="enterpriseform.contactName" readonly></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="手机号：" :label-width="formLabelWidth">
                    <el-input v-model="enterpriseform.contactPhone" readonly></el-input>
                  </el-form-item>
                </el-col>
                <div class="bdlx_box">
                  <el-col :span="18">
                    <el-form-item label="电子邮件：" class="bdlxtitle" :label-width="formLabelWidth">
                      <el-input v-model="enterpriseform.email" class="sfwt_bdlxinput" readonly></el-input>
                    </el-form-item>
                  </el-col>
                </div>
                <div class="bdlx_box">
                  <el-col :span="18">
                    <el-form-item label="业主分类：" class="bdlxtitle" :label-width="formLabelWidth">
                      <el-input v-model="enterpriseform.ownerClassify" class="sfwt_bdlxinput" readonly></el-input>
                    </el-form-item>
                  </el-col>
                </div>
                <el-col :span="24">
                  <el-form-item label="业主备注：" class="yzneirong" :label-width="formLabelWidth">
                    <el-input
                      type="textarea"
                      :autosize="{ minRows: 2, maxRows: 4}"
                      placeholder="请输入内容"
                      v-model="enterpriseform.remark" class="bdnrong_bdlxinput" readonly>
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-form>
            </el-row>
          </div>
          <!--标段信息内容-->
        </el-tab-pane>
        <el-tab-pane label="合作项目" name="second">
          <!--归档文件内容-->
          <div class="gdwjnrbox_contentbox">
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
                  prop="tenderProjectCode"
                  label="招标项目编号"
                  width="160">
                </el-table-column>
                <el-table-column
                  prop="tenderProjectName"
                  label="所属招标项目"
                  width="400">
                </el-table-column>
                <el-table-column
                  prop="projectManager"
                  label="项目经理"
                  width="180">
                </el-table-column>
                <el-table-column
                  prop="entrustDate"
                  label="委托时间"
                  width="240">
                </el-table-column>
                <el-table-column
                  prop="projectNature"
                  label="项目性质">
                  <template slot-scope="scope">
                    {{scope.row.projectNature?'线上项目':'线下项目'}}
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
          <!--归档文件内容-->
        </el-tab-pane>
      </el-tabs>
      <!--导航切换-->
    </div>
    <!--内容结束-->
  </div>
</template>
<script>
import * as citydata from '../../assets/js/citydata'
export default {
  data () {
    return {
      // 默认显示
      activeName: 'first',
      // 内容展示input
      enterpriseform: {},
      // 合作项目表单数据
      tableData: [],
      total: 0, // 总条数
      loading: true,
      pageSize: 15, // 每页展示条数
      pageNo: 0,
      dataIndex: 1,
      formLabelWidth: '120px',
      selectedOptions: [],
      addressOptions: citydata.CityInfo
    }
  },
  methods: {
    // 导航切换
    handleClick (tab) {
      if (tab.label == '企业信息') {
        this.enterpriseCon()
      } else if (tab.label === '合作项目') {
        this.cooperation()
      }
    },
    handleCurrentChange (nowNum) {
      this.loading = true
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this.pageNo = (nowNum - 1) * this.pageSize
      this.cooperation()
    },
    // 企业内息展示
    enterpriseCon () {
      this.axios.get(`owner-infos/${this.$route.query.objectId}`).then((res) => {
        this.enterpriseform = res.data.ownerInfo
        this.loading = false
      })
    },
    // 合作信息展示
    cooperation () {
      this.axios.get(`owner-projects?pageNo=${this.pageNo}&pageSize=${this.pageSize}&ownerCode=${this.$route.query.code}`).then((res) => {
        this.tableData = res.data.projectInformationInfo.list
        this.total = res.data.projectInformationInfo.total
      })
    }
  },
  mounted () {
    this.enterpriseCon()
    var provincename = this.$route.query.data.row.provinceId
    var cityname = this.$route.query.data.row.cityId
    var countyname = this.$route.query.data.row.countyId
    this.selectedOptions = [provincename, cityname, countyname]
  }
}
</script>
<style lang="less">
  #zhangLookcontent {
    width: 1200px;
    background: #fff;
    min-height: 50px;
    margin: 0 auto;
    margin-top: 23px;
    padding-bottom:50px!important;
    /*查看档案*/
    .content_tiltebox {
      width: 100%;
      height: 60px;
      /* background: red; */
      line-height: 60px;
      padding: 0 20px;
      box-sizing: border-box;
      font-size: 15px;
    }
    .gsmczzt {
      color: #666666;
    }
    .nav_switchbox {
      width: 100%;
      background: #ebeffe;
      padding: 0 10px;
      box-sizing: border-box;
    }
    .el-menu-item {
      width: 97px;
      height: 47px;
      /* background: red; */
      float: left;
      margin-top: 5px;
      text-align: center;
      line-height: 47px;
      font-size: 14px;
      color: #111111;
      cursor: pointer;
    }
    .is-active {
      background: #fff;
      color: #3f63f6;
    }
    .ckdan_contentbox {
      padding: 0px 18px;
      box-sizing: border-box;
      margin-top: 22px;
    }
    .gdwjnrbox_contentbox {
      padding: 0px 18px;
      box-sizing: border-box;
      margin-top: 22px;
      /*background: red;*/
      /*display: none;*/
    }
    form {
      display: block;
      margin-top: 0em;
      width: 100%;
    }
    .el-form-item__label {
      /*width: 25%!important;;*/
      /* background: teal; */
      height: 100%;
      float: left;
      text-align: right;
      color: #666666;
      font-size: 14px;
      line-height: 40px;
    }
    .el-form-item__content {
      width: 75% !important;;
      /* background: yellow; */
      height: 100%;
      float: left;
      padding: 0 5px;
      box-sizing: border-box;
      font-size: 14px;
      margin-left: 0 !important;
      color: #666666 !important;
    }
    .el-input__inner {
      width: 100%;
      height: 40px;
      border: 1px solid #eeeeee;
      box-sizing: border-box;
      padding: 0 10px;
      color: #111111 !important;
      outline: none;
      background: transparent !important;
      text-align: left;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .bdlxinput {
      width: 105px;
      height: 100%;
      /*border: 1px solid #eeeeee;*/
      color: #111111;
      float: left;
      margin-right: 16px;
      letter-spacing: 0px;
      text-align: center;
      line-height: 40px;
      font-size: 14px;
      box-sizing: border-box;
    }
    .sfwt_bdlxinput {
      width: 66% !important;
    }
    .bdnrong_bdlxinput {
      height: 100%;
    }
    .bdnrong_bdlxinput > .el-textarea__inner {
      background: transparent !important;
      color: #111111 !important;
      resize: none;
    }
    .el-form-item__content > .el-textarea {
      height: 100px !important;
    }
    /*新增归档样式*/
    .zbfs_bigbox {
      width: 130px;
    }
    .zzt_zbfs_selectbox {
      width: 100%;
    }
    .el-form-item__content > .el-textarea {
      height: 100%;
    }
    .el-form-item__content > .el-textarea > .el-textarea__inner {
      height: 100% !important;
      resize: none;
    }
    .jdxx_bigbox {
      width: 100%;
      min-height: 20px;
      margin-top: 30px;
      margin-bottom: 20px;
      text-align: center;
    }
    .jdxx_sonbox {
      display: inline-block;
      min-width: 50px;
      height: 30px;
      vertical-align: top;
      line-height: 30px;
      font-size: 14px;
      color: rgb(51, 51, 51);
    }
    .hengxian_box {
      display: inline-block;
      width: 105px;
      height: 30px;
      vertical-align: top;
      padding: 0px 15px;
    }
    .dangqian_jin {
      display: inline-block;
      width: 30px;
      height: 30px;
      vertical-align: top;
      color: rgb(255, 255, 255);
      text-align: center;
      line-height: 30px;
      margin-right: 5px;
      background: rgb(187, 187, 187);
      border-radius: 50%;
    }
    .hengxian {
      width: 100%;
      height: 2px;
      margin-top: 14px;
      background: rgb(187, 187, 187);
    }
    .dangqian_dangqian {
      background: rgb(63, 99, 246);
    }
    .gongneng_btnbox {
      text-align: center;
      padding-bottom: 30px;
    }
    .gongneng_btn {
      width: 120px;
    }
    .sfwtdl_btn {
      width: 66%;
    }
    .bdnrxxlb_tabbox > div > div > i {
      background: transparent !important;
    }
    /*查看*/
    .dabao_xiazai_btnbox {
      display: inline-block;
      width: 120px;
      height: 36px;
      background: #6582f8;
      color: #fff;
      font-size: 14px;
      text-align: center;
      line-height: 36px;
      cursor: pointer;
      margin-left: 5px;
    }
    .dabaooxia_icon {
      display: inline-block;
      width: 18px;
      vertical-align: top;
      margin-top: 9px;
    }
    .ckgdwjbox {
      padding-bottom: 30px;
    }
    .bdnrxxlb_tabbox > div > .el-collapse-item__header {
      border: none;
      width: 100%;
      height: 45px;
      background: #f5f7f8;
      padding: 0 10px;
      box-sizing: border-box;
      margin-top: 20px;
      font-size: 12px;
      position: relative;
    }
    .is-active {
      background: #f5f7f8;
      color: #3f63f6;
    }
    .el-collapse-item__wrap {
      border-bottom: none;
      width: 100%;
    }
    .xuhao_num {
      display: inline-block;
      width: 10px;
      color: #999999;
    }
    .bdlist_title {
      margin-left: 15px;
      color: #111111;
    }
    .delet_btnbox {
      display: inline-block;
      width: 12px;
      height: 100%;
      /*background: red;*/
      position: absolute;
      right: 40px;
      top: 0;
    }
    .delet_btnbox > img {
      width: 100%;
      position: absolute;
      top: 0;
      bottom: 0;
      margin: auto;
    }
    .baocun_btnbox {
      display: inline-block;
      width: 12px;
      height: 100%;
      /*background: red;*/
      position: absolute;
      right: 63px;
      top: 0;
    }
    .baocun_btnbox > img {
      width: 100%;
      position: absolute;
      top: 0;
      bottom: 0;
      margin: auto;
    }
    /*点击展开表格展示*/
    .zhankai_con_sontitlebox {
      width: 100%;
      height: 55px;
      line-height: 55px;
      font-size: 14px;
      color: #111111;
      text-align: center;
    }
    .tianjia_btnbox {
      width: 100%;
      height: 36px;
      /* background: red; */
      margin: 10px auto;
    }
    .tianjia_btn {
      width: 120px;
      height: 100%;
      background: #6582f8;
      text-align: center;
      line-height: 36px;
      color: #fff;
      font-size: 14px;
      cursor: pointer;
    }
    .tianjia_btn {
      color: #fff;
      font-size: 14px;
      cursor: pointer;
    }
    .xuhaotab {
      text-align: center;
    }
    .el-table th {
      text-align: center;
    }
    .chakna_yichu {
      text-align: center;
    }
    .el-table_2_column_4 {
      text-align: center;
    }
    .jdxx_bigbox {
      width: 100%;
      min-height: 20px;
      margin-top: 30px;
      margin-bottom: 20px;
      text-align: center;
    }
    .jdxx_sonbox {
      display: inline-block;
      min-width: 50px;
      height: 30px;
      vertical-align: top;
      line-height: 30px;
      font-size: 14px;
      color: rgb(51, 51, 51);
    }
    .hengxian_box {
      display: inline-block;
      width: 105px;
      height: 30px;
      vertical-align: top;
      padding: 0px 15px;
    }
    .dangqian_jin {
      display: inline-block;
      width: 30px;
      height: 30px;
      vertical-align: top;
      color: rgb(255, 255, 255);
      text-align: center;
      line-height: 30px;
      margin-right: 5px;
      background: rgb(187, 187, 187);
      border-radius: 50%;
    }
    .hengxian {
      width: 100%;
      height: 2px;
      margin-top: 14px;
      background: rgb(187, 187, 187);
    }
    .dangqian_dangqian {
      background: rgb(63, 99, 246);
    }
    .gongneng_btnbox {
      text-align: center;
      padding-bottom: 30px;
    }
    .gongneng_btn {
      width: 120px;
    }
    .sfwtdl_btn {
      width: 66%;
    }
    .bdnrxxlb_tabbox > div > div > i {
      background: transparent !important;
    }
    th {
      font-weight: normal !important;
      background: transparent !important;
      width: 100% !important;
    }
    .is-leaf {
      background: #f5f7f8 !important;
    }
    .el-table th > .cell {
      position: relative;
      word-wrap: normal;
      text-overflow: ellipsis;
      vertical-align: middle;
      width: 100%;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      text-align: left;
      font-size: 12px;
    }
    .el-table .cell {
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      white-space: normal;
      word-break: break-all;
      line-height: 23px;
      font-size: 12px;
    }
    .el-tabs__nav-scroll {
      overflow: hidden;
      width: 90%;
      margin: 0 auto;
    }
    .el-tabs__nav-wrap::after {
      content: "";
      position: absolute;
      left: 0;
      bottom: 0;
      width: 100%;
      height: 2px;
      background-color: #fff;
      z-index: 1;
    }
    .headerzzt_title{
      margin-top: 30px;
    }
    .headerzzt_title input{
      border: none;
    }
    .zcdz{
      width: 100%;
    }
    .el-tabs__nav-wrap {
      background: #e5f3ff;
    }
    .el-tabs--top .el-tabs__item.is-top:nth-child(2){
      padding: 0 20px
    }
    .el-tabs--top .el-tabs__item.is-top:last-child{
      padding: 0 20px
    }
    .el-tabs__active-bar{
      background-color:#fff;
    }
  }
</style>
