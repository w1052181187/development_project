<template>
  <div id="land_ann_lookdetailbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>查看出让地块</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/look-transfer-plot/look-massif/index?roledsType=2' }">查看出让地块</el-breadcrumb-item>
        <el-breadcrumb-item>地块详情</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <router-link tag='span' to="/lt/assignee/look-transfer-plot/look-massif/index?roledsType=2&showStatus=false">返回</router-link>
      </div>
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--地块信息-->
        <div class="proinfobox">
          <span class="title">地块信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="地块编号：">
                  <span>{{sectionForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="统一交易标识码：">
                  <span>{{sectionForm.unifiedTransCode}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让行为批准单位：">
                  <span>{{sectionForm.approvalUnit}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="16">
                <el-form-item label="地块位置：">
                  <el-cascader class=""
                     expand-trigger="hover"
                     :options="linkageData"
                     v-model="linkageArray" disabled>
                  </el-cascader>
                  <span style="vertical-align: top">{{sectionForm.address}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让行为批准单位文件：" class="filebigbox">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="规划面积(m2)：">
                  <span>{{sectionForm.area}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="实际出让面积(m2)：">
                  <span>{{sectionForm.actualArea}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让年限(年)：">
                  <span>{{sectionForm.ageLimit}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="投资强度(万元/公顷)：">
                  <span>{{sectionForm.investIntensity}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="容积率(%)：">
                  <span>{{sectionForm.errorTolerRate}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="绿化率(%)：">
                  <span>{{sectionForm.greeningRate}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="建筑密度(%)：">
                  <span>{{sectionForm.densityOfBuild}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="建筑限高(m)：">
                  <span>{{sectionForm.buildingHeight}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--地块信息-->
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：">
                  <span>{{sectionForm.projectName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：">
                  <span>{{sectionForm.projectNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="公告名称：">
                  <span>{{sectionForm.annoNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="公告类型：">
                  <span v-if="sectionForm.type === 1">拍卖出让公告</span>
                  <span v-if="sectionForm.type === 2">挂牌出让公告</span>
                  <span v-if="sectionForm.type === 3">资格后审出让公告</span>
                  <span v-if="sectionForm.type === 4">资格预审出让公告</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 1 || Number(this.$route.query.methodOfTransfer) === 2">
                <el-form-item label="出让文件获取方法：">
                  <span>{{sectionForm.fileObtainStartMethod}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="招标文件获取方法：">
                  <span>{{sectionForm.biddingStartMethod}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="资格预审文件获取方法：">
                  <span>{{sectionForm.prequalificationStartMethod}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 1 || Number(this.$route.query.methodOfTransfer) === 2">
                <el-form-item label="出让文件获取时间：">
                  <span>{{sectionForm.transDocStartTime}}</span> 至 <span>{{sectionForm.transDocEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="招标文件获取时间：">
                  <span>{{sectionForm.biddingDocStartTime}}</span> 至 <span>{{sectionForm.biddingDocEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="资格预审文件获取时间：">
                  <span>{{sectionForm.preDocStartTime}}</span> 至 <span>{{sectionForm.preDocEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="报名时间：">
                  <span>{{sectionForm.applyStartTime}}</span> 至 <span>{{sectionForm.applyEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="是否允许联合体竞买：">
                  <span v-if="sectionForm.isAllowedBidding === 0">否</span>
                  <span v-if="sectionForm.isAllowedBidding === 1">是</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="竞买保证金到账截止时间：">
                  <span>{{sectionForm.marginEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 1">
                <el-form-item label="拍卖开始时间：">
                  <span>{{sectionForm.acutionStartTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 1">
                <el-form-item label="拍卖时长(分)：">
                  <span>{{sectionForm.auctionTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 2">
                <el-form-item label="挂牌时间：">
                  <span>{{sectionForm.hangStartTime}}</span> 至 <span>{{sectionForm.hangEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 2">
                <el-form-item label="限时竞价开始时间：">
                  <span>{{sectionForm.limitBiddingStartTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="递交投标文件截止时间：">
                  <span>{{sectionForm.tenderDocEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="投标文件递交方法：">
                  <span>{{sectionForm.tenderDocEndMethod}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="递交申请文件截止时间：">
                  <span>{{sectionForm.appliDocStartTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="申请文件递交方法：">
                  <span>{{sectionForm.appliDocStartMethod}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 1">
                <el-form-item label="自动延长时间(分)：">
                  <span>{{sectionForm.extensionTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="竞买保证金(万元)：">
                  <span>{{sectionForm.bidBond}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="是否存在底价：">
                  <span v-if="sectionForm.isFloorPrice === 1">是</span>
                  <span v-if="sectionForm.isFloorPrice === 0">否</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 2">
                <el-form-item label="限时竞价时间(分)：">
                  <span>{{sectionForm.limitBiddingTime}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 1 || Number(this.$route.query.methodOfTransfer) === 2">
                <el-form-item label="起始价(万元)：">
                  <span>{{sectionForm.startingPrice}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 1 || Number(this.$route.query.methodOfTransfer) === 2">
                <el-form-item label="加价幅度(万元)：">
                  <span>{{sectionForm.bidIncrement}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="申请人资格条件：">
                  <span>{{sectionForm.prerequisites}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--相关公告-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #b94aff">相关公告</span>
          <el-table
            :data="annoNumberTable"
            border>
            <el-table-column
              prop="annoNumber"
              label="公告名称"
              align="left">
            </el-table-column>
            <el-table-column
              prop="type"
              label="公告类型"
              align="left">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.type === 1">
                  <span class="statusbox">拍卖出让公告</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.type === 2">
                  <span class="statusbox">挂牌出让公告</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.type === 3">
                  <span class="statusbox">资格后审出让公告</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.type === 4">
                  <span class="statusbox">资格预审出让公告</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="publishedStartTime"
              label="发布开始时间"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="publishedEndTime"
              label="发布截止时间"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作" width="140" align="center">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!--相关公告-->
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import * as citydata from '@/assets/js/citydata'
export default {
  components: {
    commonFileDownload
  },
  data () {
    return {
      // 表单数据包
      sectionForm: {
        sectionNumber: '测试数据',
        unifiedTransCode: '测试数据',
        approvalUnit: '测试数据',
        address: '测试数据',
        area: '测试数据',
        actualArea: '测试数据',
        ageLimit: '测试数据',
        investIntensity: '测试数据',
        errorTolerRate: '测试数据',
        greeningRate: '测试数据',
        densityOfBuild: '测试数据',
        buildingHeight: '测试数据',
        projectName: '测试数据',
        projectNumber: '测试数据',
        annoNumber: '测试数据',
        type: 1,
        fileObtainStartMethod: '测试数据',
        biddingStartMethod: '测试数据',
        prequalificationStartMethod: '测试数据',
        transDocStartTime: '2018-7-26 18:00:00',
        transDocEndTime: '2018-7-26 18:00:00',
        applyStartTime: '2018-7-26 18:00:00',
        applyEndTime: '2018-7-26 18:00:00',
        isAllowedBidding: 1,
        marginEndTime: '2018-7-26 18:00:00',
        acutionStartTime: '测试数据',
        auctionTime: '12',
        extensionTime: '12',
        bidBond: '12',
        isFloorPrice: 1,
        startingPrice: '123',
        bidIncrement: '123',
        prerequisites: '测试数据',
        hangStartTime: '2018-7-26 18:00:00',
        hangEndTime: '2018-7-26 18:00:00',
        limitBiddingTime: '123',
        limitBiddingStartTime: '2018-7-26 18:00:00',
        biddingDocStartTime: '2018-7-26 18:00:00',
        biddingDocEndTime: '2018-7-26 18:00:00',
        tenderDocEndTime: '2018-7-26 18:00:00',
        preDocStartTime: '2018-7-26 18:00:00',
        preDocEndTime: '2018-7-26 18:00:00',
        tenderDocEndMethod: '在线递交',
        appliDocStartMethod: '在线递交',
        responseFileMethod: '在线递交',
        appliDocStartTime: '2018-7-26 18:00:00',
        responseFileEndTime: '2018-7-26 18:00:00',
        answeringStartTime: '2018-7-26 18:00:00',
        answeringEndTime: '2018-7-26 18:00:00',
        validityOfBid: '2018-7-26 18:00:00',
        bidEvaluationMethod: 1,
        bidOpeningTime: '2018-7-26 18:00:00',
        placeOfBidOpening: '测试数据',
        bidOpeningWay: 1,
        bidEvaluationMethods: 1,
        saleStartTime: '2018-7-26 18:00:00'
      },
      // 三级联动
      linkageData: citydata.CityInfo,
      linkageArray: [1, 1, 1],
      // 相关公告
      annoNumberTable: [
        {
          annoNumber: '关于安徽省铜陵市盛冲金多金属矿勘探探矿权',
          type: 1,
          publishedStartTime: '2018-7-26 18:00:00',
          publishedEndTime: '2018-7-26 18:00:00'
        }
      ],
      content: '富文本富文本富文本富文本富文本富文本富文本富文本', // 富文本
      // 文件内容
      fileObjecit: {
        fileName: '测试数据'
      }
    }
  },
  methods: {
  },
  mounted () {}
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
  #land_ann_lookdetailbox{
    .timesbox .el-form-item__content{
      width: 74%;
    }
    .fujianbox .el-form-item__content{
      margin-left: 0!important;
    }
  }
</style>
