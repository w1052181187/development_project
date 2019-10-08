<template>
  <div id="land_ann_subupdatebox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>项目管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/announcement/sub-announ?roledsType=1&showStatus=false' }">提交出让公告</el-breadcrumb-item>
        <el-breadcrumb-item>修改出让公告</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" :rules="rules" ref="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：" prop="projectName">
                  <el-input v-model="sectionForm.projectName" placeholder="请输入项目名称"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：" prop="projectNumber">
                  <el-input v-model="sectionForm.projectNumber" placeholder="请输入项目编号"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 1 || Number(this.$route.query.methodOfTransfer) === 2">
                <el-form-item label="出让文件获取方法：" prop="fileObtainStartMethod">
                  <el-input v-model="sectionForm.fileObtainStartMethod" placeholder="请输入出让文件获取方法"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="资格预审文件获取方法：" prop="prequalificationStartMethod">
                  <el-input v-model="sectionForm.prequalificationStartMethod" placeholder="请输入资格预审文件获取方法"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="招标文件获取方法：" prop="biddingStartMethod">
                  <el-input v-model="sectionForm.biddingStartMethod" placeholder="请输入招标文件获取方法"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="是否允许联合体竞买：" prop="isAllowedBidding">
                  <el-select v-model="sectionForm.isAllowedBidding">
                    <el-option
                      v-for="item in buyIt"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="竞买保证金到账截止时间：" prop="marginEndTime" class="hourbox">
                  <el-date-picker
                    v-model="sectionForm.marginEndTime"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="请输入竞买保证金到账截止时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 1">
                <el-form-item label="拍卖开始时间：" prop="acutionStartTime" class="hourbox">
                  <el-date-picker
                    v-model="sectionForm.acutionStartTime"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="请输入拍卖开始时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="递交申请文件截止时间：" prop="appliDocStartTime" class="hourbox">
                  <el-date-picker
                    v-model="sectionForm.appliDocStartTime"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="请输入递交申请文件截止时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="递交投标文件截止时间：" prop="tenderDocEndTime" class="hourbox">
                  <el-date-picker
                    v-model="sectionForm.tenderDocEndTime"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="请输入递交投标文件截止时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="申请文件递交方法：" prop="appliDocStartMethod">
                  <el-input v-model="sectionForm.appliDocStartMethod" placeholder="请输入申请文件递交方法"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="投标文件递交方法：" prop="tenderDocEndMethod">
                  <el-input v-model="sectionForm.tenderDocEndMethod" placeholder="请输入招标文件获取方法"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="申请有效期（天）：" prop="validityOfApplication">
                  <el-input v-model="sectionForm.validityOfApplication" placeholder="请输入申请有效期"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="投标有效期（天）：" prop="validityOfBid">
                  <el-input v-model="sectionForm.validityOfBid" placeholder="请输入投标有效期"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="文件开启时间：" prop="fileOpeningTime">
                  <el-date-picker
                    v-model="sectionForm.fileOpeningTime"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="请输入文件开启时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="评标办法：" prop="bidEvaluationMethod">
                  <el-select v-model="sectionForm.bidEvaluationMethod">
                    <el-option
                      v-for="item in bidEvaluationData"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12" v-if="Number(this.$route.query.methodOfTransfer) === 1 || Number(this.$route.query.methodOfTransfer) === 2">
                <el-form-item label="出让文件获取时间：" class="bitianicon timesbox">
                  <el-date-picker
                    v-model="fileDataRange"
                    type="datetimerange"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    range-separator="至"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                    align="right">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="资格预审文件获取时间：" class="bitianicon timesbox">
                  <el-date-picker
                    v-model="prequalDataRange"
                    type="datetimerange"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    range-separator="至"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                    align="right">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="招标文件获取时间：" class="bitianicon timesbox">
                  <el-date-picker
                    v-model="biddingDataRange"
                    type="datetimerange"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    range-separator="至"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                    align="right">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="报名时间：" class="bitianicon timesbox">
                  <el-date-picker
                    v-model="applyDataRange"
                    type="datetimerange"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    range-separator="至"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                    align="right">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row v-if="Number(this.$route.query.methodOfTransfer) === 2">
              <el-col :span="12">
                <el-form-item label="挂牌时间：" class="bitianicon timesbox">
                  <el-date-picker
                    v-model="hangDataRange"
                    type="datetimerange"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    range-separator="至"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                    align="right">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row v-if="Number(this.$route.query.methodOfTransfer) === 4 || Number(this.$route.query.methodOfTransfer) === 3">
              <el-col :span="12">
                <el-form-item label="答疑澄清时间：" class="bitianicon timesbox">
                  <el-date-picker
                    v-model="answerDataRange"
                    type="datetimerange"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    range-separator="至"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                    align="right">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="文件开启方式：" class="timesbox" prop="fileOpenMode">
                  <el-select v-model="sectionForm.fileOpenMode">
                    <el-option
                      v-for="item in fileOpening"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="文件开启地点：" prop="fileOpeningLocation">
                  <el-input v-model="sectionForm.fileOpeningLocation" placeholder="请输入文件开启地点"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="评审方式：" prop="reviewTheWay">
                  <el-select v-model="sectionForm.reviewTheWay">
                    <el-option
                      v-for="item in evaluationData"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4">
                <el-form-item label="评审办法：" prop="reviewMethod">
                  <el-select v-model="sectionForm.reviewMethod">
                    <el-option
                      v-for="item in evaluationWayData"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3" class="hourbox">
                <el-form-item label="开标时间：" prop="bidOpeningTime">
                  <el-date-picker
                    v-model="sectionForm.bidOpeningTime"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="请输入开标时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="开标地点：" prop="placeOfBidOpening">
                  <el-input v-model="sectionForm.placeOfBidOpening" placeholder="请输入开标地点"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="开标方式：" prop="bidOpeningWay">
                  <el-select v-model="sectionForm.bidOpeningWay">
                    <el-option
                      v-for="item in openingData"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 4 && (sectionForm.reviewMethod) === 2">
                <el-form-item label="合格申请人数量：" prop="qualifiedNumber">
                  <el-input v-model="sectionForm.qualifiedNumber" placeholder="请输入合格申请人数量"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="Number(this.$route.query.methodOfTransfer) === 3">
                <el-form-item label="评标方式：" prop="bidEvaluationMethods">
                  <el-select v-model="sectionForm.bidEvaluationMethods">
                    <el-option
                      v-for="item in evaluationMethodData"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--相关地块信息-->
        <!--拍卖类型-->
        <div class="proinfobox" v-if="Number(this.$route.query.methodOfTransfer) === 1">
          <span class="title" style="border-left: 3px solid #2d9631">相关地块信息</span>
          <div class="formmian">
            <div v-for="(relevantMassif,index) in sectionForm.landInformations" :key="index">
              <el-row>
                <span style="margin-left: 80px;margin-bottom: 22px;color: #000;font-weight: 800">块地{{index + 1}}</span>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="地块编号：">
                    <span>{{relevantMassif.sectionNumber}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="估价报告备案号：">
                    <span>{{relevantMassif.valuationReport}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="竞买保证金(万元)：">
                    <span>{{relevantMassif.bidBond}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="是否存在底价：">
                    <span v-if="relevantMassif.isFloorPrice === 1">是</span>
                    <span v-if="relevantMassif.isFloorPrice === 0">否</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="起始价(万元)：">
                    <span>{{relevantMassif.startingPrice}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="加价幅度(万元)：">
                    <span>{{relevantMassif.bidIncrement}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="地块估算价(元)：">
                    <span>{{relevantMassif.conputedPrice}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="拍卖时长(分)：" :prop="'landInformations.'+index+'.auctionTime'" :rules="rules.auctionTimeRules">
                    <el-input v-model="relevantMassif.auctionTime" placeholder="请输入拍卖时长"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="自动延长时间(分)：" :prop="'landInformations.'+index+'.extensionTime'" :rules="rules.extensionTimeRules">
                    <el-input v-model="relevantMassif.extensionTime" placeholder="请输入自动延长时间"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item label="受让人资格条件：">
                    <span>{{relevantMassif.prerequisites}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
        <!--拍卖类型-->
        <!--挂牌类型-->
        <div class="proinfobox" v-if="Number(this.$route.query.methodOfTransfer) === 2">
          <span class="title" style="border-left: 3px solid #2d9631">相关地块信息</span>
          <div class="formmian">
            <div v-for="(relevantMassif,index) in sectionForm.landInformations" :key="index">
              <el-row>
                <span style="margin-left: 80px;margin-bottom: 22px;color: #000;font-weight: 800">块地{{index + 1}}</span>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="地块编号：">
                    <span>{{relevantMassif.sectionNumber}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="估价报告备案号：">
                    <span>{{relevantMassif.valuationReport}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="竞买保证金(万元)：">
                    <span>{{relevantMassif.bidBond}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="是否存在底价：">
                    <span v-if="relevantMassif.isFloorPrice === 1">是</span>
                    <span v-if="relevantMassif.isFloorPrice === 0">否</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="起始价(万元)：">
                    <span>{{relevantMassif.startingPrice}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="加价幅度(万元)：">
                    <span>{{relevantMassif.bidIncrement}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="地块估算价(元)：">
                    <span>{{relevantMassif.conputedPrice}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="限时竞价开始时间：" :prop="'landInformations.'+index+'.limitBiddingStartTime'" :rules="rules.limitBiddingStartTimeRules">
                    <el-date-picker
                      v-model="relevantMassif.limitBiddingStartTime"
                      type="datetime"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      placeholder="请输入拍卖开始时间">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="限时竞价时间(分)：" :prop="'landInformations.'+index+'.limitBiddingTime'" :rules="rules.limitBiddingTimeRules">
                    <el-input v-model="relevantMassif.limitBiddingTime" placeholder="请输入自动延长时间"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item label="受让人资格条件：">
                    <span>{{relevantMassif.prerequisites}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
        <!--挂牌类型-->
        <!--资格预审和资格后审-->
        <div class="proinfobox" v-if="Number(this.$route.query.methodOfTransfer) === 4 || Number(this.$route.query.methodOfTransfer) === 3">
          <span class="title" style="border-left: 3px solid #2d9631">相关地块信息</span>
          <div class="formmian">
            <div v-for="(relevantMassif,index) in sectionForm.landInformations" :key="index">
              <el-row>
                <span style="margin-left: 80px;margin-bottom: 22px;color: #000;font-weight: 800">块地{{index + 1}}</span>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="地块编号：">
                    <span>{{relevantMassif.sectionNumber}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="估价报告备案号：">
                    <span>{{relevantMassif.valuationReport}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="竞买保证金(万元)：">
                    <span>{{relevantMassif.bidBond}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="是否存在底价：">
                    <span v-if="relevantMassif.isFloorPrice === 1">是</span>
                    <span v-if="relevantMassif.isFloorPrice === 0">否</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="地块估算价(元)：">
                    <span>{{relevantMassif.conputedPrice}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item label="受让人资格条件：">
                    <span>{{relevantMassif.prerequisites}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
        <!--资格预审和资格后审-->
        <!--相关地块信息-->
        <!--公告内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">公告内容</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="公告名称：" prop="annoNumber">
                  <el-input v-model="sectionForm.annoNumber" placeholder="请输入公告名称"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="公告类型：" prop="contacts">
                  <span v-if="sectionForm.type === 1">拍卖出让公告</span>
                  <span v-if="sectionForm.type === 2">挂牌出让公告</span>
                  <span v-if="sectionForm.type === 3">资格后审出让公告</span>
                  <span v-if="sectionForm.type === 4">资格预审出让公告</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="公告发布时间：" class="bitianicon timesbox">
                  <el-date-picker
                    v-model="annDataRange"
                    type="datetimerange"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    range-separator="至"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                    align="right">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="公告内容：" class="bitianicon">
                  <div class="editor">
                    <editor ref="ueditor" class="ueditor" :editread="false" :content="content"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="附件：">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                  <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName" :type="typeFile"></commonFileUpload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--公告内容-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('sectionForm', 1)" :loading="subStatus">提交</el-button>
          <el-button type="primary" class="save" @click="submitForm('sectionForm', 0)" :loading="subStatus">保存</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import {sumType} from '@/assets/js/validate'
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
import editor from '@/components/public-resource/mineral-rights/common/ueditor/ueditor.vue'
import { landAnno, fileInfo } from '@/api'
export default {
  components: {
    commonFileDownload,
    commonFileUpload,
    editor
  },
  data () {
    // 数字
    var sumTypes = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入数字'))
      } else if (!sumType(value)) {
        callback(new Error('请输入数字'))
      } else {
        callback()
      }
    }
    return {
      sectionForm: {},
      subStatus: false,
      fileDataRange: [], // 出让文件时间
      hangDataRange: [], // 挂牌时间
      applyDataRange: [], // 报名时间
      biddingDataRange: [], // 招标文件获取时间
      answerDataRange: [], // 答疑澄清时间
      annDataRange: [], // 公告发布时间
      prequalDataRange: [], // 资格预审文件获取时间
      content: '', // 公告内容
      // 是否允许联合体竞买
      buyIt: [
        {
          value: 1,
          label: '是'
        }, {
          value: 0,
          label: '否'
        }
      ],
      // 评标方法
      bidEvaluationData: [
        {
          value: 1,
          label: '综合评估法'
        }, {
          value: 2,
          label: '经评审最高价中标法'
        }
      ],
      // 开标方式
      openingData: [
        {
          value: 1,
          label: '在线开标'
        }, {
          value: 2,
          label: '线下开标'
        }
      ],
      // 评标方式
      evaluationMethodData: [
        {
          value: 1,
          label: '在线评标'
        }, {
          value: 2,
          label: '线下评标'
        }
      ],
      // 文件开启方式
      fileOpening: [
        {
          value: 1,
          label: '在线开标'
        }, {
          value: 2,
          label: '线下开标'
        }
      ],
      // 评审方式
      evaluationData: [
        {
          value: 1,
          label: '在线开标'
        }, {
          value: 2,
          label: '线下开标'
        }
      ],
      evaluationWayData: [
        {
          value: 1,
          label: '合格制'
        }, {
          value: 2,
          label: '有限数量制'
        }
      ],
      rules: {
        projectName: [
          { required: true, message: '请输入项目名称', trigger: 'blur' },
          { min: 1, max: 500, message: '长度在 1~500个字符', trigger: ['blur', 'change'] }
        ],
        projectNumber: [
          { required: true, message: '请输入项目编号', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1~100个字符', trigger: ['blur', 'change'] }
        ],
        annoNumber: [
          { required: true, message: '请输入公告名称', trigger: 'blur' },
          { min: 1, max: 600, message: '长度在 1~600个字符', trigger: ['blur', 'change'] }
        ],
        tenderDocEndMethod: [
          { required: true, message: '请输入投标文件递交方法', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] }
        ],
        biddingStartMethod: [
          { required: true, message: '请输入招标文件获取方法', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] }
        ],
        fileObtainStartMethod: [
          { required: true, message: '请输入出让文件获取方法', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] }
        ],
        bidOpeningWay: [
          {required: true, message: '请选择开标方式', trigger: ['blur', 'change']}
        ],
        bidEvaluationMethods: [
          {required: true, message: '请选择评标方式', trigger: ['blur', 'change']}
        ],
        isAllowedBidding: [
          {required: true, message: '请选择是否允许联合体竞买方式', trigger: ['blur', 'change']}
        ],
        marginEndTime: [
          {required: true, message: '请选择保证金到账时间', trigger: ['blur', 'change']}
        ],
        acutionStartTime: [
          {required: true, message: '请选择拍卖开始时间', trigger: ['blur', 'change']}
        ],
        bidOpeningTime: [
          {required: true, message: '请选择开标时间', trigger: ['blur', 'change']}
        ],
        auctionTimeRules: [
          { required: true, message: '请输入拍卖时长', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] },
          { validator: sumTypes, trigger: ['blur', 'change'] }
        ],
        extensionTimeRules: [
          { required: true, message: '请输入自动延时时间', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] },
          { validator: sumTypes, trigger: ['blur', 'change'] }
        ],
        limitBiddingStartTimeRules: [
          {required: true, message: '请选择限时竞价开始时间', trigger: ['blur', 'change']}
        ],
        limitBiddingTimeRules: [
          { required: true, message: '请输入拍卖时长', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] },
          { validator: sumTypes, trigger: ['blur', 'change'] }
        ],
        prequalificationStartMethod: [
          { required: true, message: '请输入资格预审文件获取方法', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] }
        ],
        appliDocStartTime: [
          {required: true, message: '请选择递交申请文件截止时间', trigger: ['blur', 'change']}
        ],
        appliDocStartMethod: [
          { required: true, message: '请输入申请文件递交方法', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] }
        ],
        validityOfApplication: [
          { required: true, message: '请输入申请有效期', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] },
          { validator: sumTypes, trigger: ['blur', 'change'] }
        ],
        validityOfBid: [
          { required: true, message: '请输入投标有效期', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] },
          { validator: sumTypes, trigger: ['blur', 'change'] }
        ],
        bidEvaluationMethod: [
          {required: true, message: '请选择评标办法', trigger: ['blur', 'change']}
        ],
        tenderDocEndTime: [
          {required: true, message: '请选择递交投标文件截止时间', trigger: ['blur', 'change']}
        ],
        fileOpeningTime: [
          {required: true, message: '请选择文件开启时间', trigger: ['blur', 'change']}
        ],
        fileOpenMode: [
          {required: true, message: '请选择文件开启方式', trigger: ['blur', 'change']}
        ],
        placeOfBidOpening: [
          { required: true, message: '请输入开标地点', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在 1~200个字符', trigger: ['blur', 'change'] }
        ],
        fileOpeningLocation: [
          { required: true, message: '请输入文件开启地点', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在 1~200个字符', trigger: ['blur', 'change'] }
        ],
        reviewTheWay: [
          {required: true, message: '请选择评审方式', trigger: ['blur', 'change']}
        ],
        reviewMethod: [
          {required: true, message: '请选择评审办法', trigger: ['blur', 'change']}
        ],
        qualifiedNumber: [
          { required: true, message: '请输入合格申请人数量', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] },
          { validator: sumTypes, trigger: ['blur', 'change'] }
        ]
      },
      // 业务类型
      fileType: 2,
      // 文件类型：土地
      typeFile: 2,
      // 文件上传保存的数组
      fileArry: [],
      fileObjecit: {},
      originalId: '',
      oldFileName: '',
      landInfoIds: []
    }
  },
  methods: {
    toFormat () {
      this.hangDataRange = [this.sectionForm.hangStartTime, this.sectionForm.hangEndTime]
      this.fileDataRange = [this.sectionForm.transDocStartTime, this.sectionForm.transDocEndTime]
      this.prequalDataRange = [this.sectionForm.preDocStartTime, this.sectionForm.preDocEndTime]
      this.biddingDataRange = [this.sectionForm.bidDocStartTime, this.sectionForm.bidDocEndTime]
      this.applyDataRange = [this.sectionForm.applyStartTime, this.sectionForm.applyEndTime]
      this.answerDataRange = [this.sectionForm.answerStartTime, this.sectionForm.answerEndTime]
      this.annDataRange = [this.sectionForm.annStartTime, this.sectionForm.annEndTime]
    },
    landAnnoInfo () {
      landAnno.detail(this.$route.query.objectId).then((res) => {
        this.sectionForm = res.data.landAnno
        this.content = res.data.landAnno.content
        this.$refs.ueditor.setContent(this.content) // 设置富文本内容
        this.toFormat()
        for (var i = 0; i < res.data.landAnno.landInformations.length; i++) {
          this.landInfoIds.push(res.data.landAnno.landInformations[i].objectId)
        }
        if (res.data.landAnno.fileInformations) {
          this.fileObjecit = res.data.landAnno.fileInformations
          this.originalId = res.data.landAnno.fileInformation.objectId
        }
      })
    },
    // 将子组件获取到的数据传给父组件i
    upLoadFile (file) {
      this.fileObjecit = []
      this.fileArry = file
    },
    // 提交保存
    submitForm (sectionForm, flag) {
      if (this.hangDataRange) {
        this.sectionForm.hangStartTime = this.hangDataRange[0]
        this.sectionForm.hangEndTime = this.hangDataRange[1]
      } else if (this.$route.query.methodOfTransfer === 2) {
        this.$message({
          type: 'warning',
          message: '挂牌时间不能为空'
        })
        return false
      }
      if (this.fileDataRange) {
        this.sectionForm.transDocStartTime = this.fileDataRange[0]
        this.sectionForm.transDocEndTime = this.fileDataRange[1]
      } else if (this.$route.query.methodOfTransfer === 1 || this.$route.query.methodOfTransfer === 2) {
        this.$message({
          type: 'warning',
          message: '出让文件时间不能为空'
        })
        return false
      }
      if (this.prequalDataRange) {
        this.sectionForm.preDocStartTime = this.prequalDataRange[0]
        this.sectionForm.preDocEndTime = this.prequalDataRange[1]
      } else if (this.$route.query.methodOfTransfer === 4) {
        this.$message({
          type: 'warning',
          message: '资格预审文件获取时间不能为空'
        })
        return false
      }
      if (this.biddingDataRange) {
        this.sectionForm.bidDocStartTime = this.biddingDataRange[0]
        this.sectionForm.bidDocEndTime = this.biddingDataRange[1]
      } else if (this.$route.query.methodOfTransfer === 3) {
        this.$message({
          type: 'warning',
          message: '招标文件获取时间不能为空'
        })
        return false
      }
      if (this.applyDataRange) {
        this.sectionForm.applyStartTime = this.applyDataRange[0]
        this.sectionForm.applyEndTime = this.applyDataRange[1]
      } else {
        this.$message({
          type: 'warning',
          message: '报名时间不能为空'
        })
        return false
      }
      if (this.answerDataRange) {
        this.sectionForm.answerStartTime = this.answerDataRange[0]
        this.sectionForm.answerEndTime = this.answerDataRange[1]
      } else if (this.$route.query.methodOfTransfer === 3 || this.$route.query.methodOfTransfer === 4) {
        this.$message({
          type: 'warning',
          message: '答疑时间不能为空'
        })
        return false
      }
      if (this.annDataRange) {
        this.sectionForm.annStartTime = this.annDataRange[0]
        this.sectionForm.annEndTime = this.annDataRange[1]
      } else {
        this.$message({
          type: 'warning',
          message: '公告发布时间不能为空'
        })
        return false
      }
      if (this.$refs.ueditor.getContent() === '') {
        this.$message({
          type: 'warning',
          message: '公告内容不能为空'
        })
        return false
      }
      this.$refs[sectionForm].validate((valid) => {
        if (valid) {
          this.sectionForm.status = flag
          this.sectionForm.content = this.$refs.ueditor.getContent()
          this.sectionForm.landInfoIds = this.landInfoIds
          this.subStatus = true
          landAnno.update(this.sectionForm).then((res) => {
            if (this.fileArry.length != 0) {
              this.fileArry[0].relatedCode = res.data.landAnno.code
              if (this.originalId != '') {
                this.fileArry[0].objectId = this.originalId
              }
              // 将附件信息入库
              fileInfo.saveFile(this.fileArry).then((res) => {
                this.fileArry = []
              })
            }
            this.subStatus = false
            this.$router.push({path: '/lt/transferor/announcement/sub-announ?roledsType=1&showStatus=false'})
          })
        } else {
          return false
        }
      })
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/lt/transferor/announcement/sub-announ?roledsType=1'})
    }
  },
  mounted () {
    this.landAnnoInfo()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
  #land_ann_subupdatebox{
    .timesbox .el-form-item__content{
      width: 74%;
    }
    .hourbox .el-date-editor{
      width: 100%;
    }
  }
</style>
