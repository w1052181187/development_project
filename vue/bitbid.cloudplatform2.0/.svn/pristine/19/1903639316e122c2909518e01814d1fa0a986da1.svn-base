<template>
  <div class="cloudcontent my-order-detail">
    <div class="clearfix" v-if="detailForm.orderStatus!==5">
      <h4 class="order-title">订单进度</h4>
      <ul class="progress-ul clearfix">
        <li class="fl li-first-child" :class="orderProgress ? 'first-li-active' : ''">
          <div class="progress-div"><i class="fl">1</i>提交订单</div>
        </li>
        <li class="fl" :class="orderProgress ? 'progress-li-active' : ''">
          <div class="progress-div"><i class="fl">2</i>客服确认订单</div>
        </li>
        <li class="fl" :class="detailForm.orderStatus >= 2 ? 'progress-li-active' : ''">
          <div class="progress-div"><i class="fl">3</i>在线支付</div>
        </li>
        <li class="fl" :class="detailForm.orderStatus >= 3 ? 'progress-li-active' : ''">
          <div class="progress-div"><i class="fl">4</i>服务开通</div>
        </li>
      </ul>
    </div>
    <div class="main">
      <h4 class="order-title">订单详情</h4>
      <el-form :model="detailForm">
        <el-row>
          <el-form-item label="订单编号：">
            <span>{{detailForm.orderNumber}}</span>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="订单日期：">
            <span>{{detailForm.orderDate | filterDate}}</span>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="服务内容：">
            <span>{{detailForm.serviceContent | filterContent}}</span>
          </el-form-item>
        </el-row>
        <el-row v-if="detailForm.serviceContent === 2">
          <el-form-item label="订阅地区：">
            <span v-for="(item, index) in detailForm.subscriptionAreaList" :key="index">{{item.areaName}}{{(index!==detailForm.subscriptionAreaList.length-1)?'、':''}}</span>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="订阅期限：">
            <span>{{detailForm.serviceYear}}年</span>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="订单金额：">
            <span class="order-amount">{{detailForm.orderPrice? `${detailForm.orderPrice}元`:''}}</span>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="折后金额：">
            <span class="order-amount">{{detailForm.discountPrice? `${detailForm.discountPrice}元`:''}}</span>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="实付金额：">
            <span class="order-amount">{{detailForm.paymentPrice? `${detailForm.paymentPrice}元`:''}}</span>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="订单状态：">
            <span class="order-state">{{detailForm.orderStatus | filterStatus}}</span>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item>
            <el-button class="return-button" type="primary" @click="returnList">返回</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </div>
  </div>
</template>
<script>
import {serviceSubscription} from '@/api/system'
import {dateFormat} from '@/assets/js/common'
export default {
  name: '',
  components: {},
  data () {
    return {
      orderProgress: true, // 判断状态值
      detailForm: {
        number: '',
        date: '',
        content: '',
        region: '',
        deadline: '',
        orderMoney: '',
        discountMoney: '',
        trueMoney: '',
        state: ''
      },
      progressList: [
        {value: 'value1', text: '客服确认订单'},
        {value: 'value2', text: '支付费用，上传支付凭证'},
        {value: 'value3', text: '客服确认支付信息'},
        {value: 'value4', text: '服务开通'}
      ]
    }
  },
  mounted () {
    this.getDetailData()
  },
  filters: {
    filterDate (val) {
      if (val) {
        return dateFormat(val, 'yyyy-MM-dd')
      }
    },
    filterContent (val) {
      if (val === 1) {
        return '平台'
      } else if (val === 2) {
        return '招标寻源'
      } else if (val === 3) {
        return 'APP'
      } else if (val === 4) {
        return '机构门户'
      }
    },
    filterStatus (val) {
      if (val === 1) {
        return '审核中'
      } else if (val === 2) {
        return '待支付'
      } else if (val === 3) {
        return '已支付'
      } else if (val === 4) {
        return '已到期'
      } else if (val === 5) {
        return '已取消'
      }
    }
  },
  methods: {
    getDetailData () {
      serviceSubscription.getById(this.$route.params.objectId).then(res => {
        this.detailForm = res.data.serviceSubscription
      })
    },
    // 返回列表
    returnList () {
      this.$router.go(-1)
    }
  }
}
</script>

<style scoped lang="less">
.clearfix:after{
  content: ".";
  display: block;
  height: 0;
  clear: both;
  visibility: hidden;
}
.clearfix{
  zoom: 1;
}
.order-title{
  border-left: 4px solid #fe7e00;
  padding-left: 6px;
}
.progress-ul{
  height: 110px;
  background: #ecf3fd;
  margin-top: 10px;
  padding: 30px;
  box-sizing: border-box;
  .li-first-child{
    width: 188px;
    height: 50px;
    background: url("../../../../static/images/progress/progress.png") center center no-repeat;
  }
  .first-li-active{
    background: url("../../../../static/images/progress/progressActive.png") center center no-repeat;
    color: #fff;
  }
  li{
    width: 210px;
    height: 50px;
    background: url("../../../../static/images/progress/progress1.png") center center no-repeat;
    text-align: center;
  }
  .progress-li-active{
    background: url("../../../../static/images/progress/progressActive1.png") center center no-repeat;
    color: #fff;
  }
  .progress-div{
    line-height: 50px;
  }
  i{
    font-size: 22px;
    font-weight: bold;
    line-height: 50px;
    margin-left: 30px;
  }
  span{
    display: block;
    line-height: 24px;
  }
  .order-state{
    color: #3fa3ef;
  }
}
.order-amount{
  color: #fe7e00;
}
.order-state{
  color: #3fa3ef;
}
.return-button{
  margin-left: 74px;
}
</style>
<style lang="less">
.my-order-detail{
  .main{
    .el-form-item__label{
      width: inherit;
      margin-right: 10px;
    }
    .el-form-item__content{
      margin-left: inherit;
    }
  }
}
</style>
