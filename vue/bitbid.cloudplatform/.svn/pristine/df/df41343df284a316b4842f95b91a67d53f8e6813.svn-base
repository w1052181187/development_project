<template>
  <div>
    <!--内容开始-->
    <div class="content_bigbox" id="contentDocuments">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/archives' }">档案管理</el-breadcrumb-item>
        <el-breadcrumb-item class="active_bread">编辑档案</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <!--进度信息-->
      <div class="jdxx_bigbox">
        <div class="jdxx_sonbox">
          <div class="dangqian_jin">1</div>
          <span>基本信息</span>
        </div>
        <div class="hengxian_box">
          <div class="hengxian"></div>
        </div>
        <div class="jdxx_sonbox">
          <div class="dangqian_jin dangqian_dangqian">2</div>
          <span>归档文件</span>
        </div>
      </div>
      <!--进度信息-->
      <!--标段基本信息-->
      <el-form label-width="120px" :model="formLabelAlign" class="bdjbxxbox">
        <el-row>
          <el-col :span="6">
            <el-form-item class="bdxinxibox" label="标段编号：">
              <el-input :disabled="true" v-model="formLabelAlign.sectionNumber" v-bind:title="formLabelAlign.sectionNumber"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item class="bdxinxibox" label="标段名称：">
              <el-input :disabled="true" v-model="formLabelAlign.sectionName" v-bind:title="formLabelAlign.sectionName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!--标段基本信息-->
      <!--标段列表内容-->
      <div class="biaoduan_listbgibox">
        <el-collapse accordion v-model="activeNames" :value="value" @change="changeCollapse">
          <el-collapse-item class="bdnrxxlb_tabbox" v-for="(Bidslist,index) in filTypepOptions" :name="index" :key="index">
            <template slot="title" slot-scope="scope">
              <span class="bdlist_title">{{Bidslist.fileName}}</span>
            </template>
            <div class="zztbdzs_fatherbigbox">
              <fileUpload   key='1' :businessType="Bidslist.objectId" :btn='btn' :project-info='formLabelAlign' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='委托代理合同'"></fileUpload>
              <fileUpload  key='2'  :businessType="Bidslist.objectId" :btn='btn' :project-info='formLabelAlign' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='项目实施计划'"></fileUpload>
              <fileUpload  key='3'  :businessType="Bidslist.objectId" :btn='btn' :project-info='formLabelAlign' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='主管部门批复函件'"></fileUpload>
              <fileUpload   key='4' :businessType="Bidslist.objectId" :btn='btn' :project-info='formLabelAlign' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='招标项目及标段信息'"></fileUpload>
              <prequalification  key='5' :businessType="Bidslist.objectId" :btn='btn' :project-info='formLabelAlign' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='资格预审公告（含变更）'"></prequalification>
              <tenderer   key='6' :businessType="Bidslist.objectId" :btn='btn' :project-info='formLabelAlign' :file-name='Bidslist.fileName'  v-if="Bidslist.fileName=='投标单位报名表'"></tenderer>
              <prequalificationDoc @getSaveInfo='getSaveInfo'  key='7'  :businessType="Bidslist.objectId" :btn='btn' :project-info='formLabelAlign' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='资格预审文件'"></prequalificationDoc>
              <application  key='8'  :businessType="Bidslist.objectId" :btn='btn' :project-info='formLabelAlign' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='资格预审申请文件'"></application>
              <returns  key='9'  :businessType="Bidslist.objectId" :btn='btn' :project-info='formLabelAlign' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='资格预审结果报告'"></returns>
              <adviceNote  key='10'  :businessType="Bidslist.objectId" :btn='btn' :project-info='formLabelAlign' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='资格预审结果通知书'"></adviceNote>
              <bidding  key='11'  :businessType="Bidslist.objectId" :btn='btn' :project-info='formLabelAlign' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='招标公告（含变更）'"></bidding>
              <fileUpload  key='12'  :businessType="Bidslist.objectId" :btn='btn' :project-info='formLabelAlign' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='投标邀请书（含变更）'"></fileUpload>
              <biddingDoc  key='13' @getSaveInfo='getSaveInfo' :businessType="Bidslist.objectId" :btn='btn' :project-info='formLabelAlign' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='招标文件'"></biddingDoc>
              <fileUpload  key='14'  :businessType="Bidslist.objectId" :btn='btn' :project-info='formLabelAlign' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='招标控制价'"></fileUpload>
              <tenderDoc  key='15'  :businessType="Bidslist.objectId" :btn='btn' :project-info='formLabelAlign' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='投标文件'"></tenderDoc>
              <bidOpening  key='16' @getSaveInfo='getSaveInfo' :businessType="Bidslist.objectId" :btn='btn'  :project-info='formLabelAlign' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='开标'"></bidOpening>
              <evaluation  key='17'  :businessType="Bidslist.objectId" :btn='btn' :project-info='formLabelAlign' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='评标结果'"></evaluation>
              <picketage  key='18' @getSaveInfo='getSaveInfo' :businessType="Bidslist.objectId" :btn='btn' :project-info='formLabelAlign' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='定标'"></picketage>
              <fileUpload  key='19'  :businessType="Bidslist.objectId" :btn='btn' :project-info='formLabelAlign' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='合同'"></fileUpload>
              <jobEvaluation  key='20' @getSaveInfo='getSaveInfo' :businessType="Bidslist.objectId" :btn='btn' :project-info='formLabelAlign' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='代理工作评价'"></jobEvaluation>
              <fileUpload  key='21'  :businessType="Bidslist.objectId" :btn='btn' :project-info='formLabelAlign' file-name='其他附件' v-if="Bidslist.fileName=='其他附件'"></fileUpload>
            </div>
          </el-collapse-item>
          <!--分页-->
          <el-pagination
            background
            layout="prev, pager, next"
            :total="total"
            :current-page.sync="currentPage"
            :page-size='pageSize'
            @current-change="handleCurrentChange">
          </el-pagination>
          <!--分页-->
        </el-collapse>
      </div>
      <!--标段列表内容-->
      <!--按钮-->
      <el-row class="gongneng_btnbox">
        <el-button class="gongneng_btn shangyibu" type="info" @click="lastStep" >上一步</el-button>
        <el-button class="gongneng_btn" type="primary" @click="submitFun">提交</el-button>
        <!--<el-button class="gongneng_btn gongneng_baocunbtn" @click="preservationFun" type="info">保存</el-button>-->
        <el-button class="gongneng_btn quxiao" type="info" @click="cancelClick">取消</el-button>
      </el-row>
      <!--按钮-->
    </div>
    <!--内容结束-->
  </div>
</template>
<script>
import {checkResponse} from '../../assets/js/common'
import prequalification from './public/prequalification'
import tenderer from './public/tenderer'
import prequalificationDoc from './public/prequalificationDoc'
import application from './public/application'
import returns from './public/returns'
import adviceNote from './public/adviceNote'
import bidding from './public/bidding'
import biddingDoc from './public/biddingDoc'
import tenderDoc from './public/tenderDoc'
import bidOpening from './public/bidOpening'
import evaluation from './public/evaluation'
import picketage from './public/picketage'
import jobEvaluation from './public/jobEvaluation'
import fileUpload from './public/fileUpload'
import eventBus from '@/assets/js/eventBus'
export default {
  components: {
    prequalification,
    tenderer,
    prequalificationDoc,
    application,
    returns,
    adviceNote,
    bidding,
    biddingDoc,
    tenderDoc,
    bidOpening,
    evaluation,
    picketage,
    jobEvaluation,
    fileUpload
  },
  data () {
    return {
      // 头部展示
      formLabelAlign: {},
      // 列表数据归档文件分页
      filTypepOptions: [],
      total: 0, // 总条数
      pageSize: 15, // 每页展示条数
      pageNo: 0,
      btn: true,
      currentPage: 1,
      beforePage: 1,
      activeNames: '-1',
      beforeNames: '-1',
      isSave: true,
      title: '',
      value: '2'
    }
  },
  methods: {
    // 上一步
    lastStep () {
      this.$router.push({path: '/archives/add', query: {objectId: this.$route.query.objectId, btn: true, flangshow: true, deptName: this.$route.query.deptName, managerName: this.$route.query.managerName, editStatus: true}})
    },
    // 取消
    cancelClick () {
      this.$router.push({path: '/archives'})
    },
    // 头部数据获取
    formData () {
      this.axios.get(`project-infos/${this.$route.query.objectId}`).then((res) => {
        this.formLabelAlign = res.data.projectInformation
      })
    },
    // 列表数据获取归档文件
    filTypep () {
      // 先根据objectId查询出标段信息
      this.axios.get(`file-types?pageNo=${this.pageNo}&pageSize=${this.pageSize}&projectObjectId=${this.$route.query.objectId}&_t=${new Date().getTime()}`).then((res) => {
        this.filTypepOptions = res.data.fileTypeInfo.list
        this.total = res.data.fileTypeInfo.total
        this.activeNames = '-1'
        this.beforeNames = '-1'
      })
    },
    // 分页
    handleCurrentChange (nowNum) {
      var flag = this.isAlert()
      if (flag) {
        this.beforePage = this.currentPage
        this.pageNo = (this.currentPage - 1) * this.pageSize
        this.filTypep()
      } else {
        this.currentPage = this.beforePage
      }
    },
    // 提交数据
    submitFun () {
      var flag = this.isAlert()
      if (flag) {
        this.axios.put(`project-infos`, {fileStatus: 1, objectId: this.$route.query.objectId}).then((res) => {
          checkResponse(this, res, () => {
            this.$router.push({path: '/archives'})
          })
        })
      }
    },
    changeCollapse (val) {
      this.isAlert()
    },
    isAlert () {
      if (!this.isSave) {
        this.$alert(`请保存${this.title}信息`, '提示', {
          confirmButtonText: '确定',
          callback: action => {
            this.activeNames = this.beforeNames
            return false
          }
        })
      } else {
        this.beforeNames = this.activeNames
        return true
      }
    },
    getSaveInfo (isSave, title) {
      this.isSave = isSave
      this.title = title
    }
  },
  mounted () {
    this.filTypep()
  },
  beforeRouteEnter (to, from, next) {
    eventBus.axios.get(`project-infos/${to.query.objectId}`).then((res) => {
      var projectInfos = res.data.projectInformation
      eventBus.axios.get(`file-types?pageNo=0&pageSize=15&projectObjectId=${to.query.objectId}&_t=${new Date().getTime()}`).then((filType) => {
        next((vm) => {
          vm.filTypepOptions = filType.data.fileTypeInfo.list
          vm.total = filType.data.fileTypeInfo.total
          vm.activeNames = '-1'
          vm.beforeNames = '-1'
          vm.formLabelAlign = projectInfos
        })
      })
    })
  }
}
</script>
<style  lang="less">
  #contentDocuments {
    width: 1200px;
    background: #fff;
    min-height: 50px;
    margin: 0 auto;
    margin-top: 23px;
    .gongneng_baocunbtn {
      background: #fff;
      color: #333333;
    }
    .el-input__inner{
      background: transparent!important;
    }
    .gdwj_titleson {
      float: left;
      width: 20%;
    }
    .shangyibu{
      background: transparent;
      border: 1px solid #dddddd;
      color:#333333;
    }
    .quxiao{
      background:#ededed;
      color:#676767;
      border: 1px solid #ededed;
    }
    .bd_namezzt {
      width: 80%;
    }
    .gdwj_titlebox {
      width: 100%;
      height: 50px;
      padding: 0px 18px;
      box-sizing: border-box;
    }
    .gdwj_titleson {
      height: 100%;
      line-height: 50px;
      /* background: tomato; */
      font-size: 14px;
      color: #111111;
      font-weight: bold;
    }
    .shuju_number {
      color: #666666;
      font-weight: normal;
    }
    .biaoduan_listbgibox {
      width: 100%;
      padding: 0px 18px;
      box-sizing: border-box;
    }
    .el-collapse {
      border: none;
      margin-bottom: 35px;
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
      text-align: left;
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
      text-align: center !important;
    }
    .chakna_yichu {
      text-align: center;
    }
    .el-table_2_column_4 {
      text-align: center;
    }
    .el-table thead {
      color: #909399;
      font-weight: 500;
      background: #f5f7f8 !important;
    }
    .el-table th, .el-table tr {
      background-color: transparent !important;
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
    .el-icon-delete:before {
      content: "\E612";
      position: absolute;
      /* float: right; */
      right: 40px;
      top: 18px;
    }
    .el-icon-printer:before {
      content: "\E62F";
      position: absolute;
      right: 60px;
      top: 18px;
    }
    .el-form-item{
      display: inline-block;
      vertical-align: top;
      width: 100%
    }
    .bdxinxibox input{
      border: none!important;
      overflow: hidden;
      text-overflow:ellipsis;
      white-space: nowrap;
    }
    .gongneng_btnbox .el-button{
      border-radius: 0;
      width: 110px!important;
      height: 35px;
      line-height: 35px;
      padding: 0;
    }
    .el-input.is-disabled .el-input__inner {
      background-color: #f5f7fa;
      border-color: #e4e7ed;
      color: #111111 !important;
      font-weight: bold;
      cursor: not-allowed;
    }
    .el-form-item__label {
      text-align: right;
      float: left;
      font-size: 14px;
      color: #666666;
      font-weight: bold;
      line-height: 40px;
      padding: 0 12px 0 0;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
    }
    .bdjbxxbox{
      height: 45px;
      padding-top: 5px;
    }
  }
</style>
