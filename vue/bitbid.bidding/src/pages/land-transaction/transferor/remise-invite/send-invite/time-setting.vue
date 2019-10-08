<template>
  <div id="timeSetting" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让邀请</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/remise-invite/send-invite?roledsType=1&showStatus=false' }">发出出让邀请</el-breadcrumb-item>
        <el-breadcrumb-item>设置时间</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" :rules="rules" ref="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--时间信息-->
        <div class="proinfobox">
          <span class="title">时间信息</span>
          <div class="formmian" v-for="(relevantMassif,index) in relevantMassifData" :key="index">
            <el-row>
              <span style="margin-left: 80px;margin-bottom: 22px;color: #000;font-weight: 800">块地{{index + 1}}</span>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="地块编号：" prop="sectionNumber">
                  <span>{{relevantMassif.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="邀请书名称：" prop="annoNumber">
                  <span>{{sectionForm.annoNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="受让人邀请确认截止时间：">
                  <el-date-picker
                    v-model="relevantMassif.invConEndTime"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="选择日期时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="出让文件获取时间：" class="bitianicon settime">
                  <el-date-picker
                    v-model="relevantMassif.fileDataRange"
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
                <el-form-item label="答疑澄清时间：" class="bitianicon settime">
                  <el-date-picker
                    v-model="relevantMassif.answerDataRange"
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
              <el-col :span="8">
                <el-form-item label="递交响应文件截止时间：" prop="tenderDocEndTime">
                  <el-date-picker
                    v-model="relevantMassif.tenderDocEndTime"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="选择日期时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="递交响应文件方法：" prop="subFileMethod">
                  <el-input v-model="relevantMassif.subFileMethod" placeholder="请输入公告名称"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="投标有效期（天）：" prop="validityOfBids">
                  <el-input v-model="relevantMassif.validityOfBids" placeholder="请输入公告名称"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="评标办法：" prop="bidEvaMethod">
                  <el-select v-model="relevantMassif.bidEvaMethod">
                    <el-option
                      v-for="item in bidEvaluationMode"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="开标时间：" prop="bidOpenTime">
                  <el-date-picker
                    v-model="relevantMassif.bidOpenTime"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="选择日期时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="开标地点：" prop="bidOpenPlace">
                  <el-input v-model="relevantMassif.bidOpenPlace" placeholder="请输入开标地点"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="开标方式：" prop="bidOpenWay">
                  <el-select v-model="relevantMassif.bidOpenWay">
                    <el-option
                      v-for="item in openingMethodData"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="评标方式：" prop="bidEvaWay">
                  <el-select v-model="relevantMassif.bidEvaWay">
                    <el-option
                      v-for="item in bidEvaluationMethodData"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row v-if="index === 0">
              <el-col :span="8" class="synchronization">
                <el-button type="success" @click="synchronizationBtn">同步</el-button>
                <span>注：所填信息同步到其他地块</span>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--时间信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('sectionForm', 2)" :loading="subStatus">提交</el-button>
          <el-button type="primary" class="save" @click="submitForm('sectionForm', 2)" :loading="subStatus">保存</el-button>
          <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import {landAnno, landProject} from '@/api'
export default {
  data () {
    return {
      sectionForm: {},
      relevantMassifDataFirst: [], // 表单中的第一个数据
      synchroArray: [], // 表单中的其余数据
      rules: {},
      fileDataRange: [], // 出让文件时间
      answerDataRange: [], // 答疑澄清时间
      bidEvaluationMode: [
        {
          value: 0,
          label: '综合评估法'
        }, {
          value: 1,
          label: '经评审最高价中标法'
        }
      ],
      // 开标方式
      openingMethodData: [
        {
          value: 0,
          label: '在线开标'
        }, {
          value: 1,
          label: '线下开标'
        }
      ],
      // 评标方式
      bidEvaluationMethodData: [
        {
          value: 0,
          label: '在线评标'
        }, {
          value: 1,
          label: '线下评标'
        }
      ],
      subStatus: false,
      obtainData: {}, // 填写完后同步的数据
      transferDocumentsDate: [], // 出让文件获取时间
      clarifyDate: [], // 答疑澄清时间
      relevantMassifData: []
    }
  },
  methods: {
    // 提交保存
    submitForm (sectionForm, flag) {
      this.$refs[sectionForm].validate((valid) => {
        if (valid) {
          this.sectionForm.status = flag
          let lansInformations = []
          this.sectionForm.landInformations = this.relevantMassifData
          this.sectionForm.landInformations.forEach(function (item) {
            let lansInformation = item
            lansInformation.transDocStartTime = item.fileDataRange[0]
            lansInformation.transDocEndTime = item.fileDataRange[1]
            lansInformation.ansQueStartTime = item.answerDataRange[0]
            lansInformation.ansQueEndTime = item.answerDataRange[1]
            delete lansInformation.fileDataRange
            delete lansInformation.answerDataRange
            lansInformations.push(lansInformation)
          })
          this.subStatus = true
          landProject.setTime(lansInformations).then((res) => {
            this.subStatus = false
            this.$router.push({path: '/lt/transferor/remise-invite/send-invite?roledsType=1&showStatus=false'})
          })
        } else {
          return false
        }
      })
    },
    // ***************************************************************自己方法**********************************************************************
    // 同步
    synchronizationBtn () {
      this.relevantMassifData = this.relevantMassifData.map((ite) => {
        let object = Object.assign({}, this.relevantMassifData[0]) // 拷贝一个对象
        delete object.objectId // 删除掉不需要同步的值
        let newObject = Object.assign({}, ite, object) // 把同步的数据与下面准备同步的数据进行合并
        ite = newObject
        return ite
      })
    },
    landAnnoInfo () {
      landAnno.detail(this.$route.query.objectId).then((res) => {
        console.log(res)
        this.sectionForm = res.data.landAnno
        this.sectionForm.landInformations.forEach(function (item) {
          if (item.transDocStartTime !== null) {
            item.fileDataRange = [item.transDocStartTime, item.transDocEndTime]
            item.answerDataRange = [item.ansQueStartTime, item.ansQueEndTime]
          }
        })
        // 将地块的信息进行复制 复制到relevantMassifData
        let that = this
        that.sectionForm.landInformations.forEach(function (item) {
          let object = {}
          object.objectId = item.objectId
          object.sectionNumber = item.sectionNumber
          object.invConEndTime = item.invConEndTime
          object.fileDataRange = item.fileDataRange
          object.answerDataRange = item.answerDataRange
          object.tenderDocEndTime = item.tenderDocEndTime
          object.subFileMethod = item.subFileMethod
          object.validityOfBids = item.validityOfBids
          object.bidEvaluationMode = item.bidEvaluationMode
          object.bidOpenTime = item.bidOpenTime
          object.bidOpenPlace = item.bidOpenPlace
          object.bidOpenWay = item.bidOpenWay
          object.bidEvaMethod = item.bidEvaMethod
          that.relevantMassifData.push(object)
        })
      })
    }
  },
  mounted () {
    this.landAnnoInfo()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
  #timeSetting{
    .el-form-item__content {
      width: 55%;
    }
    .el-date-editor.el-input, .el-date-editor.el-input__inner {
      width: 100%;
    }
    .settime .el-form-item__content{
      width: 70%;
    }
    .synchronization{
      margin-left: 100px;
      margin-bottom: 22px;
      font-size: 14px;
      color: #666666;
    }
    .synchronization .el-button--success {
      color: #fff;
      background: rgba(22, 155, 213, 1);
      border-color:rgba(22, 155, 213, 1);
      margin-right: 15px;
    }
    .synchronization .el-button--success span{
      color: #fff;
    }
  }
</style>
