<template>
  <div class="cloudcontent" id="cloud_customer_follow">
    <div class="main">
      <div class="tit">
        <span>跟进记录</span>
      </div>
      <div class="follow_mian">
        <p class="nodata" v-if="!listData.length">暂无数据</p>
        <ul>
          <li v-for="item in listData" :key="item.objectId">
            <div class="record">
              <span class="time">{{item.followUpDate | formatDate}}</span>
              <span class="method" v-for="dicFollowUpType in dicFollowUpTypeList" :key="dicFollowUpType.objectId" v-if="dicFollowUpType.code === item.dicFollowUpTypeCode">{{dicFollowUpType.name}}</span>
              <span class="peo">{{item.followUpPerson}}</span>
              <span class="jt"></span>
              <span class="unawares">{{item.contacts}}</span>
              <span class="phase" v-if="type === 'opportunity'">{{item.opportunityStageInfo}}</span>
              <div class="handle">
                <span title="下载" v-if="item.fileInformations.length" @click="downloadFile(item.fileInformations[0])"><img src="../../../../static/images/ico_download.png"/> </span>
              </div>
            </div>
            <div class="sum">
              <textarea readonly v-model="item.content" ></textarea>
            </div>
            <div>
              <span class="time">下次跟进时间：{{item.nextFollowUpDate | formatDate}}</span>
            </div>
          </li>
        </ul>
      </div>
      <!--分页-->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="page.total"
        :page-size='page.pageSize'
        :current-page.sync="page.currentPage"
        @current-change="handlePage"
        @next-click="handlePage">
      </el-pagination>
      <!--分页-->
    </div>
  </div>
</template>
<script>
import { followup } from '@/api/customer'
import { dateFormat, downloadFile } from '@/assets/js/common'
import { dataDictionary } from '@/api/system'

export default {
  data () {
    return {
      page: {
        pageSize: 3,
        pageNo: 0,
        total: 0, // 总条数
        currentPage: 1
      },
      queryModel: {
        relatedCode: this.relatedCode,
        isDelete: 0
      },
      listData: [],
      name: '',
      chargePersonName: '',
      showVisible: false,
      isEdit: false,
      editObjectId: null,
      // 跟进方式字典项Id
      dicFollowUpTypeId: 10006,
      // 跟进方式集合
      dicFollowUpTypeList: [],
      // 跟进商机阶段字典项Id
      dicOpportunityStageId: 10005,
      // 跟进商机阶段字典项集合
      dicOpportunityStages: []
    }
  },
  props: ['relatedCode', 'type'],
  methods: {
    // 初始化数据
    initData () {
      this.queryModel.pageNo = this.page.pageNo
      this.queryModel.pageSize = this.page.pageSize
      followup.queryList(this.queryModel).then(res => {
        setTimeout(() => {
          this.queryModel.pageNo = this.page.pageNo
          this.listData = res.data.data.list
          this.page.total = res.data.data.total
          // 如果是商机，包装商机阶段
          if (this.type === 'opportunity') {
            this.wrapOpportunityStage()
          }
        }, 200)
      })
    },
    // 跟进方式初始化
    initFollowUpType () {
      let query = {
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        dictionaryDirectoryId: this.dicFollowUpTypeId
      }
      dataDictionary.getDetailList(query).then(res => {
        this.dicFollowUpTypeList = res.data.dictionaryDetails
      })
    },
    // 跟进商机阶段初始化
    initOpportunityStage () {
      let query = {
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        dictionaryDirectoryId: this.dicOpportunityStageId
      }
      dataDictionary.getDetailList(query).then(res => {
        this.dicOpportunityStages = res.data.dictionaryDetails
      })
    },
    // 包装商机阶段
    wrapOpportunityStage () {
      this.listData.forEach(item => {
        let obj = this.dicOpportunityStages.find(child => child.code === item.dicOpportunityStageCode)
        item.opportunityStageInfo = obj ? obj.name : '---'
      })
    },
    // 下载文件
    downloadFile (file) {
      downloadFile(file.fileName, file.relativePath)
    },
    // 表单分页
    handlePage (nowNum) {
      this.page.currentPage = nowNum
      this.page.pageNo = (nowNum - 1) * this.page.pageSize
      this.initData()
    }
  },
  filters: {
    // 格式化时间
    formatDate (value) {
      return value ? dateFormat(value, 'yyyy-MM-dd') : '---'
    }
  },
  mounted () {
    // 跟进方式初始化
    this.initFollowUpType()
    // 判断，如果是商机管理跟踪，则初始化
    if (this.type === 'opportunity') {
      // 跟进商机阶段初始化
      this.initOpportunityStage()
    }
    // 初始化跟进记录
    this.initData()
  }
}
</script>
<style lang="less">
  #cloud_customer_follow {
    .tit{
      overflow: hidden;
    }
    .tit span{
      font-size: 20px;
      font-weight: bold;
      float: left;
    }
    .tit em{
      float: left;
      height: 30px;
      line-height: 30px;
      margin-left: 20px;
      background: #fff2de;
      padding: 0 15px;
      display: inline-block;
      font-style: normal;
      color: #f5a623;
      font-weight: bold;
    }
    .follow_top{
      width: 98%;
      height: 50px;
      line-height: 50px;
      margin:16px auto;
      border-bottom: 1px solid #dcdfe6;
    }
    .follow_top span{
      float: left;
      font-weight: bold;
    }
    .follow_top .addrecord{
      float: right;
      background: #108cee;
      border-color: #108cee;
    }
    .follow_top .addrecord span{
      color: #ffffff;
    }
    .follow_mian{
      width: 98%;
      margin:0px auto;
    }
    .follow_mian ul{
      margin-left: 12px;
      border-left: 1px solid #dddddd;
    }
    .follow_mian ul:after {
      display: block;
      content: "";
      clear: both;
    }
    .follow_mian ul li{
      width: 99%;
      float: left;
      margin: 15px 0 5px 0;
      margin-left: -5px;
      padding-left: 1%;
      background-image: url("../../../../static/images/dian.png");
      background-position: 0px 22px;
      background-repeat: no-repeat;
    }
    .follow_mian ul li .record{
      width: 100%;
      height: 24px;
      line-height: 24px;
      margin-top: 15px;
      color: #ffffff;
    }
    .follow_mian ul li .record span.time{
      width: 100px;
      height: 24px;
      padding-left: 15px;
      font-size: 12px;
      display: inline-block;
      float: left;
      background: url("../../../../static/images/timebg.png") left center no-repeat;
      color: #ffffff;
    }
    .follow_mian ul li .record span.method{
      display: inline-block;
      float: left;
      margin-left: 16px;
      background: #dcdfe6;
      color: #666666;
      padding: 0 10px;
      -webkit-border-radius: 5px;
      -moz-border-radius: 5px;
      border-radius: 5px;
    }
    .follow_mian ul li .record span.peo{
      display: inline-block;
      float: left;
      margin-left: 20px;
      background: #beeebe;
      color: #14a614;
      padding: 0 10px;
      -webkit-border-radius: 5px;
      -moz-border-radius: 5px;
      border-radius: 5px;
    }
    .follow_mian ul li .record span.jt{
      width: 24px;
      height: 24px;
      display: inline-block;
      float: left;
      margin-left: 10px;
      background: url("../../../../static/images/record_jt.png") left center no-repeat;
    }
    .follow_mian ul li .record span.unawares{
      display: inline-block;
      float: left;
      margin-left: 10px;
      background: #c9e0ff;
      color: #1465d6;
      padding: 0 10px;
      -webkit-border-radius: 5px;
      -moz-border-radius: 5px;
      border-radius: 5px;
    }
    .follow_mian ul li .record span.phase{
      display: inline-block;
      float: left;
      margin-left: 10px;
      background: #d5d3fe;
      color: #6760ff;
      padding: 0 10px;
      -webkit-border-radius: 5px;
      -moz-border-radius: 5px;
      border-radius: 5px;
    }
    .follow_mian ul li .record .handle{
      float: right;
      display: inline-block;
      overflow: hidden;
    }
    .follow_mian ul li .record .handle span{
      margin: 0 5px;
      cursor: pointer;
    }
    .follow_mian ul li .sum{
      overflow: hidden;
      margin-top: 20px;
    }
    .follow_mian ul li .sum textarea{
      width: 100%;
      height: 121px;
      padding: 8px;
      background: #f4f4f4;
      border: 1px solid #dcdfe6;
      -webkit-box-sizing: border-box;
      -moz-box-sizing: border-box;
      box-sizing: border-box;
    }
    .el-dialog__body{
      padding-right: 40px;
    }
    .dialog-footer{
      text-align: center;
    }
    .el-dialog__body .el-textarea__inner{
      height: 120px;
    }
    .el-select{
      width: 100%;
    }
  }
</style>
