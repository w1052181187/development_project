<template>
    <div id="bidroom">
      <div class="headertitle">
        <!--面包屑-->
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>拍卖</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/mr/transferor/auction/check-auction-bids?roledsType=1' }">查看拍卖竞价情况</el-breadcrumb-item>
          <el-breadcrumb-item>竞价室</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
      </div>
      <div class="contentbigbox">
        <p class="bidtitle">当前拍卖竞价室：{{sectionName}}</p>
        <div class="bidcontain">
          <div class="time">
            <div class="countdown"><span v-html="day">1</span>天<span v-html="hour">2</span>时<span v-html="min">3</span>分<span v-html="second">4</span>秒</div>
            <p>距离竞价开始时间</p>
          </div>
          <div class="price">
            <el-form ref="form" :model="form" :rules="rules" label-width="100px">
              <el-form-item label="项目名称：">
                <span>{{sectionName}}</span>
              </el-form-item>
              <el-form-item label="项目编号：">
                <span>{{sectionNumber}}</span>
              </el-form-item>
              <el-form-item label="底价：" prop="price" class="priceinput" v-if="isFloorPrice">
                <el-input v-model="form.price" placeholder="请输入底价"></el-input><span>万元</span>
              </el-form-item>
            </el-form>
          </div>
          <div class="button">
            <el-button @click="bulidRoom" >建立拍卖室</el-button>
          </div>
        </div>
      </div>
    </div>
</template>
<script>
import {prProject, salesCon} from '@/api'
import {sumType} from '@/assets/js/validate'
export default {
  data () {
    // 数字
    var sumTypes = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入数字'))
      } else if (!sumType(value)) {
        callback(new Error('请输入数字'))
      } else if (this.isFloorPrice && (parseFloat(value) <= this.startingPrice)) {
        callback(new Error('底价不能低于起始价：' + this.startingPrice))
      } else {
        callback()
      }
    }
    return {
      form: {
        objectId: this.$route.query.objectId,
        price: '',
        pricestatus: 1
      },
      day: 0,
      hour: 0,
      min: 0,
      second: 0,
      isFloorPrice: (this.$route.query.isFloorPrice == 1),
      startingPrice: this.$route.query.startingPrice,
      rules: {
        price: [{validator: sumTypes, trigger: ['blur', 'change']}]
      },
      sectionName: this.$route.query.sectionName,
      sectionNumber: this.$route.query.sectionNumber,
      buildFlag: false,
      timer: -1
    }
  },
  methods: {
    startCountDown (date) {
      let start, totalTime
      let end = new Date(date.substring(0, 4), parseInt(date.substring(5, 7)) - 1, date.substring(8, 10), date.substring(11, 13), date.substring(14, 16), date.substring(17)).getTime()
      let that = this
      this.timer = setInterval(function () {
        start = new Date().getTime()
        totalTime = end - start
        if (totalTime > 0) {
          that.day = parseInt(totalTime / (1000 * 60 * 60 * 24))
          that.hour = parseInt(totalTime / (1000 * 60 * 60) % 24)
          that.min = parseInt(totalTime / (1000 * 60) % 60)
          that.second = parseInt(totalTime / 1000 % 60)
        } else {
          that.stopCountDown()
          that.skipRoom()
        }
      }, 1000)
    },
    stopCountDown () {
      clearInterval(this.timer)
    },
    bulidRoom () {
      this.bulidFlag = true
      this.stopCountDown()
      this.$refs['form'].validate((valid) => {
        if (valid) {
          prProject.setFloorPrice(this.form).then(
            data => {
              if (data.data.resCode == '0000') {
                this.$router.push({path: '/mr/transferor/auction/check-auction-bids?roledsType=1'})
              } else {
                this.bulidFlag = false
              }
            }
          )
        }
      })
    },
    skipRoom () {
      if (!this.bulidFlag) {
        prProject.update({
          objectId: this.form.objectId,
          pricestatus: 2
        }).then(res => {
          return salesCon.salesConAdd({
            sectionId: res.data.updatesection.objectId,
            creator: this.$store.getters.authUser.userId,
            lastUpdaterId: this.$store.getters.authUser.userId
          })
        }).then(res => {
          this.$router.push({path: '/mr/transferor/auction/check-auction-bids?roledsType=1'})
        })
      }
    }
  },
  destroyed () {
    this.stopCountDown()
  },
  mounted () {
    this.startCountDown(this.$route.query.startTime)
  }
}
</script>
<style lang="less">
  #bidroom{
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
    margin-top: 50px;
    .headertitle{
      width: 100%;
      height: 60px;
      line-height: 60px;
      color:#333333;
      font-size: 16px;
      text-align: left;
      padding: 0 20px;
      box-sizing: border-box;
    }
    .el-breadcrumb{
      height: 60px;
      line-height: 60px;
    }
    .contentbigbox{
      width: 100%;
      min-height: 770px;
      padding:20px;
      box-sizing: border-box;
    }
    .bidtitle{
      width: 98%;
      height: 42px;
      line-height: 42px;
      background: #ffffff;
      padding: 0 1%;
    }
    .bidcontain{
      width: 100%;
      height: 500px;
      padding-bottom: 130px;
      overflow: hidden;
      margin-top: 20px;
      background:#ffffff  url("../../../../../../static/images/systemmanagement/createbg.png") bottom center no-repeat;
    }
    .bidcontain .time{
      width: 100%;
      overflow: hidden;
      margin: 50px 0 20px 0;
      text-align: center;
    }
    .bidcontain .time .countdown{
      overflow: hidden;
      height: 36px;
      line-height: 36px;
      color: #181319;
      font-size: 18px;
    }
    .bidcontain .time p{
      color: #999999;
      line-height: 32px;
      margin-top: 10px;
    }
    .bidcontain .time .countdown span{
      padding: 0 10px;
      margin: 0 5px;
      display: inline-block;
      background: #f5f5f5;
      color: #181319;
      font-weight: bold;
      font-size: 18px;
    }
    .price{
      width: 706px;
      padding: 30px 0;
      background: #f5f5f5;
      margin: 0 auto;
    }
    .el-form{
      width:360px;
      overflow: hidden;
      margin: 0 auto;
    }
    .el-form-item{
      margin: 20px 0;
    }
    .el-input__inner{
      width: 206px;
      height: 34px;
      border: 1px solid #2c9832;
    }
    .button{
      width: 100%;
      overflow: hidden;
      margin: 50px 0 0 0;
      text-align: center;
    }
    .button .el-button{
      width: 181px;
      height: 35px;
      line-height: 35px;
      background: #2c972f;
      color: #ffffff;
      padding: 0;
    }
    .priceinput{
      width: 360px;
    }
    .priceinput .el-input{
      display: inline-block;
      width: 210px;
      float: left;
    }
    .priceinput span{
      display: inline-block;
      width: 40px;
      height: 40px;
      float: left;
      line-height: 40px!important;
    }
    .el-form-item__label{
      width: 100px !important;
    }
    .el-form-item__content{
      margin-left: 100px!important;
      width: 100% !important;
    }
  }
</style>
