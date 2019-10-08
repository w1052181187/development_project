<template>
  <div id="paymentpaydetailebox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>费用缴纳</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/fee-payment?roledsType=2' }">缴纳竞买保证金</el-breadcrumb-item>
        <el-breadcrumb-item>支付</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <!--项目信息-->
      <div class="proinfobox">
        <el-form :model="sectionForm" label-width="100px" class="demo-ruleForm">
          <span class="title">我的订单</span>
          <div class="formmian">
            <el-row>
              <el-col :span="24">
                <el-form-item label="订单号：">
                  <span>{{sectionForm.Ordernumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <div class="information_bigbox">
              <div class="information_titles">基本信息</div>
              <el-row>
                <el-col :span="24" style="margin-top: 22px">
                  <el-form-item label="收费项目：">
                    <span>{{sectionForm.PayService}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="单位名称：">
                    <span>{{sectionForm.bidderName}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="项目编号：">
                    <span>{{sectionForm.sectionNumber}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="项目名称：">
                    <span>{{sectionForm.sectionName}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="应收金额：">
                    <span>{{this.$route.query.bidBond}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="生成日期：">
                    <span>{{this.$route.query.times}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="支付方式：">
                    <el-radio v-model="sectionForm.radio" :label="radiofrom.radio" v-for="(radiofrom,index) in radioAggregate" :key="index" @change="radioChange">
                      {{radiofrom.name}}
                    </el-radio>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
          <div class="bankbigbox" v-if="paymentStatus">
            <div class="banktitlesbox">请选择银行（支持企业对公账户授权支付）</div>
            <el-row>
              <el-col :span="6" class="bankimgbox" v-for="(bankRadio,index) in bankRadioAggregate" :key="index">
                <div class="bankimg">
                  <el-radio v-model="sectionForm.bankradio" :label="bankRadio.label" border class="bankradioimg">
                    <img :src="imgurl + bankRadio.img" alt="">
                  </el-radio>
                </div>
              </el-col>
            </el-row>
          </div>
          <el-form-item class="submit-radio" style="margin-top: 25px">
            <el-button type="primary">去支付</el-button>
            <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
          </el-form-item>
        </el-form>
      </div>
      <!--项目信息-->
    </div>
  </div>
</template>
<script>
import {apply} from '@/api'
export default {
  data () {
    return {
      sectionForm: {
        Ordernumber: '201805031925003916353',
        PayService: '竞买保证金',
        Unitname: '太原测试投标人二',
        Projectnumber: '23332323',
        entryName: '安徽省铜陵市盛冲金多金属矿',
        AmountReceivable: '2,000,000元',
        GenerationDate: '2018-05-03',
        radio: 1,
        bankradio: 1
      },
      radioAggregate: [
        {
          radio: 1,
          name: '企业支付'
        },
        {
          radio: 2,
          name: '其他方式'
        }
      ],
      // 支付方式自定义
      paymentStatus: true,
      imgurl: '../../../../../static/images/land-transaction/bank/', // 银行图片地址
      // 银行
      bankRadioAggregate: [
        {
          label: 1,
          img: 'jiaotong.png'
        },
        {
          label: 2,
          img: 'jianshe.png'
        },
        {
          label: 3,
          img: 'zhongguo.png'
        },
        {
          label: 4,
          img: 'nongye.png'
        },
        {
          label: 5,
          img: 'zhaoshang.png'
        },
        {
          label: 6,
          img: 'gongshang.png'
        },
        {
          label: 7,
          img: 'mingsheng.png'
        },
        {
          label: 8,
          img: 'zhongxinh.png'
        }
      ]
    }
  },
  methods: {
    radioChange (label) {
      if (label === 2) {
        this.paymentStatus = false
      } else {
        this.paymentStatus = true
      }
    },
    list () {
      apply.applyDetail(this.$route.query.objectId).then((res) => {
        console.log(res)
        let a = {}
        let b = {}
        a = res.data.Apply
        b.sectionNumber = res.data.Apply.landInformation.sectionNumber
        b.sectionName = res.data.Apply.landInformation.sectionName
        b.strikePrice = res.data.Apply.landInformation.strikePrice
        b.Ordernumber = '201805031925003916353'
        b.PayService = '竞买保证金'
        b.radio = 1
        this.sectionForm = Object.assign(a, b)
      })
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/land-register/common.css';
  #paymentpaydetailebox{
    .bankimg{
      margin-bottom: 20px;
    }
    .el-radio.is-bordered {
      padding: 12px 20px;
      border-radius: 4px;
      border: 1px solid #dcdfe6;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      height: auto!important;
    }
    .el-form span {
      word-break: break-all;
      word-wrap: break-word;
      display: inline-block;
      vertical-align: middle;
    }
    .banktitlesbox{
      margin-bottom: 30px;
    }
  }
</style>
