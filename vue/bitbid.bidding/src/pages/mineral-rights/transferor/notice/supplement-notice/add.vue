<template>
  <div id="subaddindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让公告</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/transferor/submission/subminssion-notice?roledsType=1' }">提交补充公告</el-breadcrumb-item>
        <el-breadcrumb-item>添加补充公告</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <!--公告信息-->
        <div class="proinfobox">
          <span class="title">公告信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="12">
                <el-form-item label="补充公告名称：" prop="suppleAnnoNumber">
                  <el-input v-model="ruleForm.suppleAnnoNumber" placeholder="请输入补充公告名称"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="出让方式：" prop="p_num">
                  <div class="statusbigbox" v-if="parseInt(this.$route.query.landUse) === 1">
                    <span class="statusbox">拍卖</span>
                  </div>
                  <div class="statusbigbox" v-if="parseInt(this.$route.query.landUse) === 2">
                    <span class="statusbox">挂牌</span>
                  </div>
                  <div class="statusbigbox" v-if="parseInt(this.$route.query.landUse) === 3">
                    <span class="statusbox">其他</span>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="发布时间：" class="bitianicon">
                  <el-date-picker
                    v-model="publishedDataRange"
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
                <el-form-item label="出让文件获取时间：" class="bitianicon">
                  <el-date-picker
                    v-model="fileDataRange"
                    type="datetimerange"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    range-separator="至"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                    align="right" @change="fileDataChange">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="报名时间：" class="bitianicon">
                  <el-date-picker
                    v-model="applyDataRange"
                    type="datetimerange"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    range-separator="至"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                    align="right" @change="applyDataChange">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="是否允许联合体竞买：" style="text-align: left" class="bitianicon">
                  <el-radio v-model="ruleForm.isAllowedBidding" :label="radiosAllowedBidding.lable" v-for="(radiosAllowedBidding, index) in isAllowedBiddingRdio" :key="index">{{radiosAllowedBidding.name}}</el-radio>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="拍卖开始时间：" prop="saleStartTime" v-if="parseInt(this.$route.query.landUse) === 1">
                  <el-date-picker
                    v-model="ruleForm.saleStartTime"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    start-placeholder="选择日期时间" @change="saleChange">
                  </el-date-picker>
                </el-form-item>
                <el-form-item label="挂牌时间：" prop="hangDataRange" v-else>
                  <el-date-picker
                    v-model="hangDataRange"
                    type="datetimerange"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    range-separator="至"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                    align="right" @change="hangDataChange">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="保证金到账截止时间：" prop="marginEndTime">
                  <el-date-picker
                    v-model="ruleForm.marginEndTime"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    start-placeholder="选择日期时间" @change="marginChange">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--公告信息-->
        <!--相关项目信息-->
        <div class="proinfobox" v-if="parseInt(this.$route.query.landUse) === 1">
          <span class="title" style="border-left: 3px solid #2d9631">相关项目信息</span>
          <div style="padding: 0 60px">
            <span style="color: #f56c6c">*</span>
            <span>关联矿权：</span>
            <el-select
              v-model="valuePrject"
              multiple
              collapse-tags
              style="margin-left: 20px;"
              placeholder="请选择" class="relationbox" @change="selectPrject">
              <el-option
                v-for="item in prjectSelect"
                :key="item.objectId"
                :label="item.sectionName"
                :value="item.objectId">
              </el-option>
            </el-select>
          </div>
          <div class="formmian" v-for="(sectionlist,index) in ruleForm.sections" :key="index" v-if="sectionlist.isCheckedProject">
            <div class="contenttitle">矿权 {{index + 1 }}</div>
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：">
                  <span>{{sectionlist.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：">
                  <span>{{sectionlist.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让年限(年)：">
                  <span>{{sectionlist.ageLimit}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="矿区位置：">
                  <span>{{sectionlist.allAddress}}</span>
                  <span>{{sectionlist.miningLocation}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="竞买保证金(万元)：" :prop="'sections.'+index+'.bidBond'" :rules="rules.bidBondRules">
                  <el-input v-model="sectionlist.bidBond" placeholder="请输入竞买保证金"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="起始价(万元)：" :prop="'sections.'+index+'.startingPrice'" :rules="rules.startingPriceRules">
                  <el-input v-model="sectionlist.startingPrice" placeholder="请输入起始价"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="加价幅度(万元)：" :prop="'sections.'+index+'.bidIncrement'" :rules="rules.bidIncrementRules">
                  <el-input v-model="sectionlist.bidIncrement" placeholder="请输入加价幅度"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="拍卖时长(分)：：" :prop="'sections.'+index+'.auctionTime'" :rules="rules.auctionTimeRules">
                  <el-input v-model="sectionlist.auctionTime" placeholder="请输入拍卖时长"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="自动延长时间(分)：" :prop="'sections.'+index+'.extensionTime'" :rules="rules.extensionTimeRules">
                  <el-input v-model="sectionlist.extensionTime" placeholder="请输入自动延长时间"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="是否存在底价：" style="text-align: left" class="bitianicon">
                  <el-radio v-model="sectionlist.isFloorPrice" :label="isFloorPriceRadio.lable" v-for="(isFloorPriceRadio, index) in isFloorPriceRadios" :key="index">{{isFloorPriceRadio.name}}</el-radio>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <div class="proinfobox" v-if="parseInt(this.$route.query.landUse) === 2">
          <span class="title" style="border-left: 3px solid #2d9631">相关项目信息</span>
          <div style="padding: 0 60px">
            <span style="color: #f56c6c">*</span>
            <span>关联矿权：</span>
            <el-select
              v-model="valuePrject"
              multiple
              collapse-tags
              style="margin-left: 20px;"
              placeholder="请选择" class="relationbox"
              @change="selectPrject">
              <el-option
                v-for="item in prjectSelect"
                :key="item.objectId"
                :label="item.sectionName"
                :value="item.objectId">
              </el-option>
            </el-select>
          </div>
          <div class="formmian" v-for="(sectionlist,index) in ruleForm.sections" :key="index" v-if="sectionlist.isCheckedProject">
            <div class="contenttitle">矿权 {{index + 1 }}</div>
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：">
                  <span>{{sectionlist.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：">
                  <span>{{sectionlist.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让年限(年)：">
                  <span>{{sectionlist.ageLimit}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="矿区位置：">
                  <span>{{sectionlist.allAddress}}</span>
                  <span>{{sectionlist.miningLocation}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8" :prop="'sections.'+index+'.bidBond'" :rules="rules.bidBondRules">
                <el-form-item label="竞买保证金(万元)：">
                  <el-input v-model="sectionlist.bidBond" placeholder="请输入竞买保证金"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="起始价(万元)：" :prop="'sections.'+index+'.startingPrice'" :rules="rules.startingPriceRules">
                  <el-input v-model="sectionlist.startingPrice" placeholder="请输入起始价"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="加价幅度(万元)：" :prop="'sections.'+index+'.bidIncrement'" :rules="rules.bidIncrementRules">
                  <el-input v-model="sectionlist.bidIncrement" placeholder="请输入加价幅度"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="限时竞价开始时间：" :prop="'sections.'+index+'.limitBiddingStartTime'" :rules="rules.limitBiddingStartTimeRules" >
                  <el-date-picker
                    v-model="sectionlist.limitBiddingStartTime"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="请输入限时竞价开始时间" @change="timesChange">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="限时竞价时间(分)：" :prop="'sections.'+index+'.limitBiddingTime'" :rules="rules.limitBiddingTimeRules">
                  <el-input v-model="sectionlist.limitBiddingTime" placeholder="请输入限时竞价时间"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="是否存在底价：" style="text-align: left" class="bitianicon">
                  <el-radio v-model="sectionlist.isFloorPrice" :label="isFloorPriceRadio.lable" v-for="(isFloorPriceRadio, index) in isFloorPriceRadios" :key="index">{{isFloorPriceRadio.name}}</el-radio>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--相关项目信息-->
        <!--公告内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">公告内容</span>
          <div class="formmain">
            <el-row>
              <el-col :span="24" class="area">
                <el-form-item label="公告内容：" prop="content" class="bitianicon">
                  <div class="editor">
                    <editor ref="ueditor" class="ueditor" :editread="editread" :content="content"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24" class="area">
                <el-form-item label="附件：">
                  <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></commonFileUpload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--公告内容-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('ruleForm', 1)">提交</el-button>
          <el-button type="primary" class="save" @click="submitForm('ruleForm', 0)">保存</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import {anno, fileInfo} from '@/api'
import * as citydata from '@/assets/js/citydata'
import editor from '@/components/public-resource/mineral-rights/common/ueditor/ueditor.vue'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
import {sumType} from '@/assets/js/validate'
export default {
  components: {
    editor,
    commonFileUpload
  },
  data () {
    // 数字
    var sumTypes = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入金额'))
      } else if (!sumType(value)) {
        callback(new Error('请输入正确格式'))
      } else {
        callback()
      }
    }
    return {
      // 关联项目
      prjectSelect: [],
      valuePrject: [],
      // 是否选择关联项目
      selectProStatus: false,
      // 自定义选择数组
      aelectArry: [],
      // 限时竞价时间判断
      timeStatus: false,
      // 三级联动
      addressOptions: citydata.CityInfo,
      cityselectedOptions: [],
      editread: false, // 富文本状态
      content: '', // 富文本内容
      ruleForm: {
        landUse: parseInt(this.$route.query.landUse),
        sections: [],
        isAllowedBidding: 0
      },
      // 是否可以创建竞价室
      establish: false,
      // 是否允许联合体竞买
      isAllowedBiddingRdio: [
        {
          lable: 0,
          name: '否'
        },
        {
          lable: 1,
          name: '是'
        }
      ],
      // 是否存在底价
      isFloorPriceRadios: [
        {
          lable: 0,
          name: '否'
        },
        {
          lable: 1,
          name: '是'
        }
      ],
      // 业务类型 （1.项目 2.公告 3.出让文件 4.澄清修改文件）
      fileType: 2,
      // ie9初始化数据
      oldFileName: '',
      // 文件上传保存的数组
      fileArry: [],
      publishedDataRange: [], // 发布时间
      fileDataRange: [], // 出让文件时间
      fileDataStatus: false,
      applyDataRange: [], // 报名时间
      applyDataStatus: false,
      hangDataRange: [], // 挂牌时间
      hangDataStatus: false,
      marginStatus: false, // 保证金到账截止时间判断
      saleStatus: false, // 拍卖时间
      radio: '1',
      fileList: [],
      pageSize: 10,
      pageNo: 0,
      rules: {
        suppleAnnoNumber: [
          { required: true, message: '请输入补充公告名称', trigger: 'blur' },
          { min: 1, max: 600, message: '长度在 1 到 600 个字符', trigger: ['blur', 'change'] }
        ],
        saleStartTime: [
          {required: true, message: '时间不能为空', trigger: ['blur', 'change']}
        ],
        marginEndTime: [
          {required: true, message: '时间不能为空', trigger: ['blur', 'change']}
        ],
        bidBondRules: [
          {required: true, message: '请输入竞买保证金', trigger: ['blur', 'change']},
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: ['blur', 'change'] },
          { validator: sumTypes, trigger: ['blur', 'change'] }
        ],
        startingPriceRules: [
          {required: true, message: '请输入起始价', trigger: ['blur', 'change']},
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: ['blur', 'change'] },
          { validator: sumTypes, trigger: ['blur', 'change'] }
        ],
        bidIncrementRules: [
          {required: true, message: '请输入加价幅度', trigger: ['blur', 'change']},
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: ['blur', 'change'] },
          { validator: sumTypes, trigger: ['blur', 'change'] }
        ],
        auctionTimeRules: [
          {required: true, message: '请输入拍卖时长', trigger: ['blur', 'change']},
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: ['blur', 'change'] },
          { validator: sumTypes, trigger: ['blur', 'change'] }
        ],
        extensionTimeRules: [
          {required: true, message: '请输入自动延长时间', trigger: ['blur', 'change']},
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: ['blur', 'change'] },
          { validator: sumTypes, trigger: ['blur', 'change'] }
        ],
        limitBiddingStartTimeRules: [
          { required: true, message: '时间不能为空', trigger: ['blur', 'change'] }
        ],
        limitBiddingTimeRules: [
          {required: true, message: '请输入限时竞价时间', trigger: ['blur', 'change']},
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: ['blur', 'change'] },
          { validator: sumTypes, trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  methods: {
    // 查看
    toFormat () {
      this.fileDataRange = [this.ruleForm.fileObtainStartTime, this.ruleForm.fileObtainEndTime]
      this.applyDataRange = [this.ruleForm.applyStartTime, this.ruleForm.applyEndTime]
      this.hangDataRange = [this.ruleForm.hangStartTime, this.ruleForm.hangEndTime]
    },
    list () {
      anno.announcementDetail(this.$route.query.objectId).then((res) => {
        this.ruleForm = res.data.Announcement
        this.prjectSelect = res.data.Announcement.sections
        this.ruleForm.sections = res.data.Announcement.sections
        this.toFormat()
        // 三级联动赋值
        res.data.Announcement.sections.map((ite) => {
          ite.allAddress = ''
          citydata.CityInfo.map((item) => {
            if (ite.provinceId == item.value) {
              ite.allAddress += item.label
              item.children.map((shi) => {
                if (ite.cityId == shi.value) {
                  ite.allAddress += shi.label
                  shi.children.map((county) => {
                    if (ite.countyId == county.value) {
                      ite.allAddress += county.label
                    }
                  })
                }
              })
            }
          })
          return ite
        })
      })
    },
    upLoadFile (file) {
      this.fileArry = file
    },
    // 关联项目
    selectPrject (val) {
      this.aelectArry = val
      this.establish = false
      this.ruleForm.sections.map((itm) => {
        delete itm.isCheckedProject
        itm.changeNum = 0
        val.map((ite) => {
          if (itm.objectId === ite) {
            itm.isCheckedProject = true
            itm.changeNum += 1
            return itm
          }
        })
        return itm
      })
      this.selectProStatus = true
    },
    timesChange () {
      this.timeStatus = false
    },
    // 出让文件是否选择判断
    fileDataChange (fileData) {
      if (fileData !== null) {
        let newfileObtainStartTime = fileData[0]
        let newfileObtainEndTime = fileData[1]
        if (newfileObtainStartTime !== this.ruleForm.fileObtainStartTime || newfileObtainEndTime !== this.ruleForm.fileObtainEndTime) {
          this.fileDataStatus = true
        }
      }
    },
    // 报名时间是否选择判断
    applyDataChange (applyData) {
      if (applyData !== null) {
        let newapplyDataStartTime = applyData[0]
        let newapplyDataEndTime = applyData[1]
        if (newapplyDataStartTime !== this.ruleForm.applyStartTime || newapplyDataEndTime !== this.ruleForm.applyEndTime) {
          this.applyDataStatus = true
        }
      }
    },
    // 挂牌时间是否选择判断
    hangDataChange (hangData) {
      if (hangData !== null) {
        let newhangDataStartTime = hangData[0]
        let newhangDataEndTime = hangData[1]
        if (newhangDataStartTime !== this.ruleForm.hangStartTime || newhangDataEndTime !== this.ruleForm.hangEndTime) {
          this.hangDataStatus = true
        }
      }
    },
    // 保证金截止时间
    marginChange (marginData) {
      if (marginData !== null) {
        if (marginData !== this.ruleForm.marginEndTime) {
          this.marginStatus = true
        }
      }
    },
    // 拍卖时间
    saleChange (saleData) {
      if (saleData !== null) {
        if (saleData !== this.ruleForm.saleStartTime) {
          this.saleStatus = true
        }
      }
    },
    submitForm (ruleForm, flag) {
      if (this.publishedDataRange) {
        this.ruleForm.publishedStartTime = this.publishedDataRange[0]
        this.ruleForm.publishedEndTime = this.publishedDataRange[1]
      }
      if (this.publishedDataRange.length === 0) {
        this.$message({
          type: 'warning',
          message: '发布时间不能为空'
        })
        return false
      }
      let publishedstartTime = new Date(Date.parse(this.ruleForm.publishedStartTime.replace(/-/g, '/'))).getTime()
      let publishedendTime = new Date(Date.parse(this.ruleForm.publishedEndTime.replace(/-/g, '/'))).getTime()
      let dates = Math.abs((publishedstartTime - publishedendTime)) / (1000 * 60 * 60 * 24)
      if (dates < 20) {
        this.$message({
          type: 'warning',
          message: '发布时间不得小于20个工作日'
        })
        return false
      }
      if (this.fileDataRange) {
        this.ruleForm.fileObtainStartTime = this.fileDataRange[0]
        this.ruleForm.fileObtainEndTime = this.fileDataRange[1]
      }
      if (this.fileDataRange.length === 0) {
        this.$message({
          type: 'warning',
          message: '出让文件时间不能为空'
        })
        return false
      }
      if (this.applyDataRange) {
        this.ruleForm.applyStartTime = this.applyDataRange[0]
        this.ruleForm.applyEndTime = this.applyDataRange[1]
      }
      if (this.applyDataRange.length === 0) {
        this.$message({
          type: 'warning',
          message: '报名时间不能为空'
        })
        return false
      }
      if (this.fileDataStatus || this.applyDataStatus || this.hangDataStatus || this.marginStatus || this.saleStatus) {
        if (this.ruleForm.applyEndTime > this.ruleForm.marginEndTime) {
          this.$message({
            type: 'warning',
            message: '保证金到账截止时间不能早于报名截止时间'
          })
          return false
        }
        if (this.ruleForm.saleStartTime < this.ruleForm.marginEndTime) {
          this.$message({
            type: 'warning',
            message: '保证金截止时间应早于拍卖开始时间'
          })
          return false
        }
        if (this.ruleForm.applyEndTime > this.ruleForm.saleStartTime) {
          this.$message({
            type: 'warning',
            message: '拍卖开始时间不能早于报名截止时间'
          })
          return false
        }
        if (this.ruleForm.fileObtainEndTime > this.ruleForm.saleStartTime) {
          this.$message({
            type: 'warning',
            message: '拍卖开始时间不能早于出让文件获取截止时间'
          })
          return false
        }
        if (this.hangDataRange) {
          this.ruleForm.hangStartTime = this.hangDataRange[0]
          this.ruleForm.hangEndTime = this.hangDataRange[1]
        }
        if (parseInt(this.$route.query.landUse) !== 1) {
          if (this.hangDataRange.length === 0) {
            this.$message({
              type: 'warning',
              message: '挂牌时间不能为空'
            })
            return false
          }
          let hangStartTimes = new Date(Date.parse(this.ruleForm.hangStartTime.replace(/-/g, '/'))).getTime()
          let hangEndTimes = new Date(Date.parse(this.ruleForm.hangEndTime.replace(/-/g, '/'))).getTime()
          let hangdatestime = Math.abs((hangStartTimes - hangEndTimes)) / (1000 * 60 * 60 * 24)
          if (hangdatestime < 10) {
            this.$message({
              type: 'warning',
              message: '挂牌时间不得少于10个工作日'
            })
            return false
          }
          if (this.ruleForm.hangEndTime < this.ruleForm.marginEndTime) {
            this.$message({
              type: 'warning',
              message: '保证金到账截止时间须早于挂牌截止时间'
            })
            return false
          }
          if (this.ruleForm.applyStartTime > this.ruleForm.hangStartTime) {
            this.$message({
              type: 'warning',
              message: '挂牌开始时间不能早于报名开始时间'
            })
            return false
          }
          if (this.ruleForm.fileObtainStartTime > this.ruleForm.hangEndTime) {
            this.$message({
              type: 'warning',
              message: '出让文件获取开始时间须早于挂牌截止时间'
            })
            return false
          }
        }
      }
      if (this.selectProStatus === false) {
        this.$message({
          type: 'warning',
          message: '请选择关联项目'
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
      if (this.aelectArry === undefined) {
        this.$message({
          type: 'warning',
          message: '请选择关联项目'
        })
        return false
      } else {
        if (this.ruleForm.hangEndTime !== undefined && this.ruleForm.hangEndTime !== null) {
          this.ruleForm.sections.map((its) => {
            let hangTimes = new Date(Date.parse(its.limitBiddingStartTime.replace(/-/g, '/'))).getTime()
            let currentdateTime = new Date(Date.parse(this.ruleForm.hangEndTime.replace(/-/g, '/'))).getTime()
            let dates = Math.abs((hangTimes - currentdateTime)) / (1000 * 60 * 60)
            if (dates < 0.083) {
              this.$message({
                type: 'warning',
                message: '限时竞价开始时间应在挂牌截止时间五分钟之后'
              })
              this.timeStatus = true
              return false
            }
          })
        }
      }
      this.ruleForm.sections.map((its) => {
        if (its.roomId !== null && its.changeNum === 1) {
          this.$message({
            type: 'warning',
            message: '已创建竞价室，不能添加'
          })
          this.establish = true
        }
      })
      this.ruleForm.isSupplement = 1
      this.ruleForm.content = this.$refs.ueditor.getContent() // 富文本内容获取
      this.ruleForm.annoNumber = null
      this.ruleForm.objectId = null
      if (!this.establish && !this.timeStatus) {
        this.$refs[ruleForm].validate((valid) => {
          if (valid) {
            this.ruleForm.sections.map((ite) => {
              ite.isChecked = flag
              return ite
            })
            this.ruleForm.status = flag
            this.ruleForm.type = this.ruleForm.landUse
            this.ruleForm.sections.map((ite) => {
              delete ite.isCheckedProject
            })
            anno.announcementAdd(this.ruleForm).then((res) => {
              if (this.fileArry.length !== 0) {
                this.fileArry[0].relatedCode = res.data.anno.code
                // 将附件信息入库
                fileInfo.saveFile(this.fileArry).then((res) => {
                  this.fileArry = []
                })
              }
              this.$router.push({path: '/mr/transferor/submission/supplement-notice?roledsType=1'})
            })
          } else {
            return false
          }
        })
      }
    },
    cancelBtn () {
      this.$router.push({path: '/mr/transferor/submission/subminssion-notice?roledsType=1'})
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
  #subaddindexbox{
    .el-input__suffix-inner{
      margin-top: 10px;
    }
    .relationbox{
      width: 20%;
    }
    .el-tag--small:nth-child(1){
      width: 65%;
      display: inline-block;
    }
    .el-select__tags-text{
      overflow: hidden;
      text-overflow:ellipsis;
      white-space: nowrap;
      vertical-align: top;
      width: 100%;
      display: inline-block;
    }
  }
</style>
