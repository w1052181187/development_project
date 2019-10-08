<!--eslint-disable-->
<template>
  <div>
    <!--内容开始-->
    <div class="content_bigbox" id="content-see">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/archives' }">档案管理</el-breadcrumb-item>
        <el-breadcrumb-item class="active_bread">查看档案</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <!--内容标题-->
      <el-form class="bdelform_box" label-width="120px!important" :model="form">
        <el-form-item label="标段名称：">
          <el-input class="bdinput" v-model="form.sectionName" readonly></el-input>
        </el-form-item>
      </el-form>
      <!--内容标题-->
      <!--导航切换-->
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="归档文件" name="second">
            <!--归档文件内容-->
            <div class="gdwjnrbox_contentbox ckgdwjbox">
              <div class="quxuanxia_box">
                <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选
                </el-checkbox>
                <div class="dabao_xiazai_btnbox" @click="downloadFile">
                  <img src="../../../static/images/xiazai.png" alt="" class="dabaooxia_icon">
                  <span>打包下载</span>
                </div>
              </div>
              <el-collapse accordion v-model="activeNames">
                <!--列表内容循环-->
                <el-collapse-item class="bdnrxxlb_tabbox" v-for="(Bidslist,index) in filTypepOptions" :key="index">
                  <template slot="title">
                    <el-checkbox-group v-model="checkedTypes" @change="handlecheckedTypesChange">
                      <el-checkbox :label="Bidslist">
                        <span class="bdlist_title">{{Bidslist.fileName}}</span>
                      </el-checkbox>
                    </el-checkbox-group>
                  </template>
                  <div class="zztbdzs_fatherbigbox">
                    <fileUpload  :businessType="Bidslist.objectId" :btn='btn' :project-info='form' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='委托代理合同'"></fileUpload>
                    <fileUpload :businessType="Bidslist.objectId" :btn='btn' :project-info='form' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='项目实施计划'"></fileUpload>
                    <fileUpload :businessType="Bidslist.objectId" :btn='btn' :project-info='form' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='主管部门批复函件'"></fileUpload>
                    <fileUpload :businessType="Bidslist.objectId" :btn='btn' :project-info='form' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='招标项目及标段信息'"></fileUpload>
                    <prequalification :businessType="Bidslist.objectId" :btn='btn' :project-info='form' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='资格预审公告（含变更）'"></prequalification>
                    <tenderer :businessType="Bidslist.objectId" :btn='btn' :project-info='form' :file-name='Bidslist.fileName'  v-if="Bidslist.fileName=='投标单位报名表'"></tenderer>
                    <prequalificationDoc :businessType="Bidslist.objectId" :btn='btn' :project-info='form' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='资格预审文件'"></prequalificationDoc>
                    <application :businessType="Bidslist.objectId" :btn='btn' :project-info='form' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='资格预审申请文件'"></application>
                    <returns :businessType="Bidslist.objectId" :btn='btn' :project-info='form' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='资格预审结果报告'"></returns>
                    <adviceNote :businessType="Bidslist.objectId" :btn='btn' :project-info='form' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='资格预审结果通知书'"></adviceNote>
                    <bidding :businessType="Bidslist.objectId" :btn='btn' :project-info='form' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='招标公告（含变更）'"></bidding>
                    <fileUpload :businessType="Bidslist.objectId" :btn='btn' :project-info='form' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='投标邀请书（含变更）'"></fileUpload>
                    <biddingDoc :businessType="Bidslist.objectId" :btn='btn' :project-info='form' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='招标文件'"></biddingDoc>
                    <fileUpload :businessType="Bidslist.objectId" :btn='btn' :project-info='form' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='招标控制价'"></fileUpload>
                    <tenderDoc :businessType="Bidslist.objectId" :btn='btn' :project-info='form' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='投标文件'"></tenderDoc>
                    <bidOpening :businessType="Bidslist.objectId" :btn='btn'  :project-info='form' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='开标'"></bidOpening>
                    <evaluation :businessType="Bidslist.objectId" :btn='btn' :project-info='form' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='评标结果'"></evaluation>
                    <picketage :businessType="Bidslist.objectId" :btn='btn' :project-info='form' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='定标'"></picketage>
                    <fileUpload :businessType="Bidslist.objectId" :btn='btn' :project-info='form' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='合同'"></fileUpload>
                    <jobEvaluation :businessType="Bidslist.objectId" :btn='btn' :project-info='form' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='代理工作评价'"></jobEvaluation>
                    <fileUpload :businessType="Bidslist.objectId" :btn='btn' :project-info='form' :file-name='Bidslist.fileName' v-if="Bidslist.fileName=='其他附件'"></fileUpload>
                  </div>
                </el-collapse-item>
                <!--列表内容循环-->
                <!--分页-->
                <el-pagination
                  background
                  layout="prev, pager, next"
                  :total="total"
                  :page-size='pageSize'
                  @current-change="handleCurrentChange">
                </el-pagination>
                <!--分页-->
              </el-collapse>
            </div>
            <!--归档文件内容-->
          </el-tab-pane>
          <el-tab-pane label="标段信息" name="first">
            <!--标段信息内容-->
            <div class="ckdan_contentbox">
              <el-row type="flex" class="row-bg">
                <el-form :model="form">
                  <el-col :span="12">
                    <el-form-item label="标段编号：" :label-width="formLabelWidth">
                      <el-input v-model="form.sectionNumber" readonly></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <div class="grid-content bg-purple">
                      <el-form-item label="标段名称：" :label-width="formLabelWidth">
                        <el-input :title="form.sectionName" v-model="form.sectionName" readonly></el-input>
                      </el-form-item>
                    </div>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="招标项目编号：" :label-width="formLabelWidth">
                      <el-input v-model="form.proTenderNumber" readonly></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <div class="grid-content bg-purple">
                      <el-form-item label="招标项目名称：" :label-width="formLabelWidth">
                        <el-input v-model="form.proTenderName" readonly></el-input>
                      </el-form-item>
                    </div>
                  </el-col>
                  <el-col :span="24">
                    <el-col :span="12">
                      <el-form-item label="标段类型：" class="bdlxtitle" :label-width="formLabelWidth">
                        <el-cascader
                          disabled
                          expand-trigger="hover"
                          :options="BidsType"
                          :title="biaoduanTypeToTitle"
                          v-model="biaoduanType">
                        </el-cascader>
                      </el-form-item>
                    </el-col>
                  </el-col>
                  <el-col :span="24">
                    <el-col :span="12">
                      <div class="grid-content bg-purple">
                        <el-form-item label="招标方式：" :label-width="formLabelWidth">
                          <el-select class="zzt_zbfs_selectbox" v-model="form.bidsMethod" disabled placeholder="请选择招标方式">
                            <el-option
                              v-for="item in bidsMethodSelet"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value"
                              :disabled="item.disabled">
                            </el-option>
                          </el-select>
                        </el-form-item>
                      </div>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item v-if='!form.bidsMethod' label="资审方式：" :label-width="formLabelWidth">
                        <el-select class="zzt_zbfs_selectbox" v-model="form.inforOnMethod" disabled placeholder="请选择招标方式">
                          <el-option
                            v-for="item in inforOnMethodSelet"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                            :disabled="item.disabled">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                  </el-col>
                  <el-col :span="24">
                    <el-col :span="12">
                      <el-form-item label="是否委托招标代理：" class="bdlxtitle" :label-width="formLabelWidth">
                        <el-select class="zzt_zbfs_selectbox" v-model="form.isEntrustAgency" disabled placeholder="请选择招标方式">
                          <el-option
                            v-for="item in isEntrustAgencySelet"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                            :disabled="item.disabled">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item v-if="form.isEntrustAgency" label="代理机构：" :label-width="formLabelWidth">
                        <el-input v-model="form.agentStructure" readonly></el-input>
                      </el-form-item>
                    </el-col>
                  </el-col>
                  <el-col :span="24">
                    <el-col :span="12">
                      <div class="grid-content bg-purple">
                        <el-form-item label="招标人：" :label-width="formLabelWidth">
                          <el-input v-model="form.bidder" readonly></el-input>
                        </el-form-item>
                      </div>
                    </el-col>
                  </el-col>
                  <el-col :span="12">
                    <div class="grid-content bg-purple">
                      <el-form-item label="项目经理：" :label-width="formLabelWidth">
                        <el-input v-model="form.projectManagerName" readonly></el-input>
                      </el-form-item>
                    </div>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="所属部门：" :label-width="formLabelWidth">
                      <el-input v-model="form.departmentName" readonly></el-input>
                    </el-form-item>
                  </el-col>
                  <div class="bdlx_box">
                    <el-col :span="24">
                      <el-form-item label="标段内容：" class="bdneirong" :label-width="formLabelWidth">
                        <el-input
                          type="textarea"
                          :autosize="{ minRows: 2, maxRows: 4}"
                          v-model="form.sectionContent" class="bdnrong_bdlxinput" disabled>
                        </el-input>
                      </el-form-item>
                    </el-col>
                  </div>
                </el-form>
              </el-row>
            </div>
            <!--标段信息内容-->
          </el-tab-pane>
        </el-tabs>
      <!--导航切换-->
    </div>
    <!--内容结束-->
  </div>
</template>
<script>
import * as constants from '../../assets/js/constants'
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

import {loadUrl} from '@/assets/js/common'

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
      // 多选框
      checkAll: false,
      checkedTypes: [],
      filTypepOptions: [],
      isIndeterminate: true,
      // 自定义选项卡
      activeName: 'second',
      // 标段信息内容展示
      form: {},
      biaoduanType: [],
      BidsType: constants.bidsType,
      formLabelWidth: '180px',
      // 归档文件分页
      total: 0, // 总条数
      pageSize: 10, // 每页展示条数
      pageNo: 0,
      // 展开折叠
      activeNames: '1',
      btn: false,
      downloadList: [],
      bidsMethodSelet: [{
        value: 0,
        label: '公开招标'
      }, {
        value: 1,
        label: '邀请招标'
      }],
      inforOnMethodSelet: [{
        value: 0,
        label: '资格后审'
      }, {
        value: 1,
        label: '资格预审'
      }],
      isEntrustAgencySelet: [{
        value: 0,
        label: '否'
      }, {
        value: 1,
        label: '是'
      }],
      departmentOpfrom: [{
        value: 0,
        label: '测试部'
      }, {
        value: 1,
        label: '运营部'
      }],
      projectManagers: '',
      departments: '',
      projectOpfrom: [],
      valCheck: false
    }
  },
  methods: {
    // 导航切换
    handleClick (tab, event) {},
    // 全选
    handleCheckAllChange (val) {
      if (val) {
        this.axios.get(`file-types?projectObjectId=${this.$route.query.objectId}&isCheck=${1}`).then((res) => {
          this.checkedTypes = val
        })
      } else {
        this.checkedTypes = []
      }
      this.isIndeterminate = false
      this.checkAll = val
    },
    handlecheckedTypesChange (value) {
      this.checkedTypes = value.map(function (item) {
        return item
      })
      let checkedCount = value.length
      this.checkAll = checkedCount === this.filTypepOptions.length
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.filTypepOptions.length
    },
    // 标段信息内容数据
    formData () {
      this.axios.get(`project-infos/${this.$route.query.objectId}`).then((res) => {
        this.form = res.data.projectInformation
        this.biaoduanType = this.form.sectionType.split(',')
      })
    },
    // 归档文件
    filTypep () {
      this.axios.get(`file-types?pageNo=${this.pageNo}&pageSize=${this.pageSize}&projectObjectId=${this.$route.query.objectId}&_t=${new Date().getTime()}`).then((res) => {
        this.filTypepOptions = res.data.fileTypeInfo.list
        this.total = res.data.fileTypeInfo.total
        this.activeNames = '1'
      })
    },
    // 分页
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.filTypep()
    },
    downloadFile () {
      var listParams = []
      if (!this.checkAll) {
        this.checkedTypes.map((item) => {
          listParams.push(item.objectId)
        })
      }
      if (listParams.length == 0 && !this.checkAll) {
        this.$message({
          message: '请先进行勾选归档文件！',
          type: 'warning'
        })
      } else if (!this.checkAll) {
        window.open(`${loadUrl}file-types/${this.$route.query.objectId}?fileType=${listParams.join(',')}`)
      } else if (this.checkAll) {
        window.open(`${loadUrl}/file-types/${this.$route.query.objectId}?fileType=`)
      }
    }
  },
  computed: {
    biaoduanTypeToTitle () {
      var title = ''
      constants.bidsType.map((item, index) => {
        if (item.value == this.biaoduanType[0]) {
          title += item.label + '/'
          item.children.map((item2, index) => {
            if (item2.value == this.biaoduanType[1]) {
              title += item2.label + '/'
              item2.children.map((item3) => {
                if (item3.value == this.biaoduanType[2]) {
                  title += item3.label + '/'
                  item3.children.map((item4) => {
                    if (item4.value == this.biaoduanType[3]) {
                      title += item4.label
                    }
                  })
                }
              })
            }
          })
        }
      })
      return title
    }
  },
  created () {
    this.formData()
    this.filTypep()
  }
}
</script>
<style  lang="less">
  body{
    background: #eaedf1;
  }
  #content-see {
    width: 1200px;
    background: #fff;
    min-height: 50px;
    margin: 0 auto;
    margin-top: 23px;
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
    .el-tabs__nav-wrap{
      background:#e5f3ff;
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
    .el-cascader{
      display: block;
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
      width: 26% !important;;
      /* background: teal; */
      height: 100%;
      float: left;
      text-align: right;
      color: #666666;
      font-size: 15px;
      line-height: 40px;
    }
    .el-form-item__content {
      width: 74% !important;;
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
      font-size: 15px;
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
    .bdneirong > .el-form-item__label {
      width: 12.1% !important;
    }
    .bdneirong > .el-form-item__content {
      width: 87.5% !important;
      height: 100px;
      word-wrap: break-word
    }
    .bdnrong_bdlxinput {
      height: 100%;
    }
    .bdnrong_bdlxinput > .el-input__inner {
      height: 100%;
      word-wrap: break-word
    }
    .bdnrong_bdlxinput > .el-textarea__inner {
      background: transparent !important;
      color: #111111 !important;
      resize: none;
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
    .quxuanxia_box {
      margin-bottom: 20px;
      text-align: left;
    }
    .el-tabs__nav-scroll {
      overflow: hidden;
      width: 95%;
      margin: 0 auto;
    }
    .el-tabs__nav-wrap::after {
      content: "";
      position: absolute;
      left: 0;
      right: 0;
      margin: 0 auto;
      bottom: 0;
      width: 90%;
      height: 2px;
      background-color:transparent!important;
      z-index: 1;
    }
    .bdinput>input{
      border: none!important;
    }
    .bdelform_box{
      height: 100%;
    }
    .bdelform_box .el-form-item{
      margin-bottom: 10px;
      margin-top: 10px;
      font-weight: bold;
    }
    .el-cascader.is-disabled .el-cascader__label {
      z-index: 2;
      color: #111111;
    }
    .bdinput input{
      font-weight: bold;
    }
    .el-tabs--top .el-tabs__item.is-top:nth-child(2){
      padding: 0 20px;
    }
    .el-tabs__active-bar {
      position: absolute;
      bottom: 0;
      left: 11px;
      height: 2px;
      display: none;
      /* background-color: #409EFF; */
    }
    .el-tabs--top .el-tabs__item.is-top:last-child{
      padding: 0 20px;
    }
    .el-tabs__header {
      padding: 0;
      position: relative;
      margin: 0 0 0px;
    }
    .el-icon-time:before {
      content: "\E642";
      display: none;
    }
    .el-table th>.cell {
       width: auto;
       white-space: nowrap;
    }
    .el-date-editor .el-range-input, .el-date-editor .el-range-separator {
      font-size: 12px;
    }
  }
</style>
