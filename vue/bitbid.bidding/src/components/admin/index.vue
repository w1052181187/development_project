<template>
  <div id="homepage">
    <div class="homepagecontent">
      <publicside></publicside>
      <div class="homepageconright">
        <div class="logonewsbigbox">
          <div class="logocompany_bigbox">
            <div class="logo_box">
              <img src="../../../static/images/loufang.png" alt="">
            </div>
            <div class="company_box">
              <span class="companname">{{$store.getters.authUser.enterpriseName}}</span>
            </div>
          </div>
        </div>
        <div class="servicebigbox">
          <div class="servicetitlebox">
            <span>服务清单 · </span>
            <span>14</span>
          </div>
          <div class="switchbtnbox">
            <el-tabs v-model="activeName">
              <el-tab-pane label="业务辅助" name="first">
                <el-carousel indicator-position="outside" :autoplay="true">
                  <el-carousel-item>
                    <el-row :gutter="20">
                      <el-col :span="6" class="grid-contentbox">
                        <div class="grid-content bg-purple">
                          <div class="gridtitlebox">档案管理</div>
                          <div class="gridimgbox">
                            <img src="../../../static/images/bibi/dagl_h.png" alt="" v-if="daglshowStatus">
                            <img src="../../../static/images/bibi/dagl_l.png" alt="" v-else>
                          </div>
                          <div class="gridmiaoshu">对整个招标采购过程进行全部系统性的记录</div>
                          <div class="immediatelybtn" @click="immediatelyBtn" v-if="daglshowStatus">立即开通</div>
                          <div class="renewbtn" @click="renewBtn" v-if="daglshowStatusopening">立即续费</div>
                        </div>
                      </el-col>
                      <el-col :span="6" class="grid-contentbox">
                        <div class="grid-content bg-purple">
                          <div class="gridtitlebox">业主管理</div>
                          <div class="gridimgbox">
                            <img src="../../../static/images/bibi/yzgl_h.png" alt="" v-if="yzglshowStatus">
                            <img src="../../../static/images/bibi/yzgl_l.png" alt="" v-else>
                          </div>
                          <div class="gridmiaoshu">整合业主资源，提高代理服务质量</div>
                          <div class="immediatelybtn" @click="immediatelyBtn" v-if="yzglshowStatus">立即开通</div>
                          <div class="renewbtn" @click="renewBtn" v-if="yzglshowStatusopening">立即续费</div>
                        </div>
                      </el-col>
                      <el-col :span="6" class="grid-contentbox">
                        <div class="grid-content bg-purple">
                          <div class="gridtitlebox">专家管理</div>
                          <div class="gridimgbox">
                            <img src="../../../static/images/bibi/zjgl_h.png" alt="" v-if="zjglshowStatus">
                            <img src="../../../static/images/bibi/zjgl_l.png" alt="" v-else>
                          </div>
                          <div class="gridmiaoshu">整合供应商资源，提高供应质量</div>
                          <div class="immediatelybtn" @click="immediatelyBtn" v-if="zjglshowStatus">立即开通</div>
                          <div class="renewbtn" @click="renewBtn" v-if="zjglshowStatusopening">立即续费</div>
                        </div>
                      </el-col>
                      <el-col :span="6" class="grid-contentbox">
                        <div class="grid-content bg-purple">
                          <div class="gridtitlebox">收支管理</div>
                          <div class="gridimgbox">
                            <img src="../../../static/images/bibi/szgl_h.png" alt="" v-if="szglshowStatus">
                            <img src="../../../static/images/bibi/szgl_l.png" alt="" v-else>
                          </div>
                          <div class="gridmiaoshu">招标采购项目费用收支情况清晰可见。</div>
                          <div class="immediatelybtn" @click="immediatelyBtn" v-if="szglshowStatus">立即开通</div>
                          <div class="renewbtn" @click="renewBtn" v-if="szglshowStatusopening">立即续费</div>
                        </div>
                      </el-col>
                    </el-row>
                  </el-carousel-item>
                </el-carousel>
              </el-tab-pane>
              <el-tab-pane label="系统管理" name="second">
                <el-carousel indicator-position="outside" :autoplay="true">
                  <el-carousel-item>
                    <el-row :gutter="20">
                      <el-col :span="6" class="grid-contentbox">
                        <div class="grid-content bg-purple">
                          <div class="gridtitlebox">资源分类</div>
                          <div class="gridimgbox">
                            <img src="../../../static/images/bibi/zygl_h.png" alt="" v-if="zyszshowStatus">
                            <img src="../../../static/images/bibi/zygl_l.png" alt="" v-else>
                          </div>
                          <div class="gridmiaoshu">业主、专家多角度划分归类</div>
                          <div class="immediatelybtn" @click="immediatelyBtn" v-if="zyszshowStatus">立即开通</div>
                          <div class="renewbtn" @click="renewBtn" v-if="zyszshowStatusopening">立即续费</div>
                        </div>
                      </el-col>
                    </el-row>
                  </el-carousel-item>
                </el-carousel>
              </el-tab-pane>
            </el-tabs>
          </div>
        </div>
        <div class="servicebigbox">
          <img src="../../../static/images/bannner.png" alt="">
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import publicside from '../adminpublic/side.vue'
import {admins} from '../../api/index'
export default {
  components: {
    publicside
  },
  data () {
    return {
      // 默认业务辅助为选中
      activeName: 'first',
      // 按钮状态
      daglshowStatus: false, // 立即开通
      daglshowStatusopening: false, // 立即续费
      yzglshowStatus: false,
      yzglshowStatusopening: false,
      zjglshowStatus: false,
      zjglshowStatusopening: false,
      szglshowStatus: false,
      szglshowStatusopening: false,
      zyszshowStatus: false,
      zyszshowStatusopening: false,
      // 新闻信息
      news: [{
        nesname: '信息审核',
        nescon: '您的基本信息审核已经通过。',
        nestimes: '18:00:00  2018-01-23 '
      }, {
        nesname: '信息审核',
        nescon: '您的基本信息审核已经通过。',
        nestimes: '18:00:00  2018-01-23 '
      }, {
        nesname: '信息审核',
        nescon: '您的基本信息审核已经通过。',
        nestimes: '18:00:00  2018-01-23 '
      }]
    }
  },
  methods: {
    opensList () {
      let url = {
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        isDelete: 0
      }
      admins.openList(url).then((res) => {
        res.data.managementList.map((ite) => {
          if (ite.isChecked === null && ite.serviceName === '档案管理') {
            this.daglshowStatus = true
            this.daglshowStatusopening = false
          } else if (ite.isChecked === 1 && ite.serviceEnabledStatus === 0 && ite.serviceName === '档案管理') {
            this.daglshowStatus = false
            this.daglshowStatusopening = true
          } else if (ite.isChecked === 1 && ite.serviceEnabledStatus === 1 && ite.serviceName === '档案管理') {
            this.daglshowStatus = false
            this.daglshowStatusopening = true
          } else if (ite.isChecked === null && ite.serviceName === '业主管理') {
            this.yzglshowStatus = true
            this.yzglshowStatusopening = false
          } else if (ite.isChecked === 1 && ite.serviceEnabledStatus === 0 && ite.serviceName === '业主管理') {
            this.daglshowStatus = false
            this.yzglshowStatusopening = true
          } else if (ite.isChecked === 1 && ite.serviceEnabledStatus === 1 && ite.serviceName === '业主管理') {
            this.daglshowStatus = false
            this.yzglshowStatusopening = true
          } else if (ite.isChecked === null && ite.serviceName === '专家管理') {
            this.zjglshowStatus = true
            this.zjglshowStatusopening = false
          } else if (ite.isChecked === 1 && ite.serviceEnabledStatus === 0 && ite.serviceName === '专家管理') {
            this.zjglshowStatus = false
            this.zjglshowStatusopening = true
          } else if (ite.isChecked === 1 && ite.serviceEnabledStatus === 1 && ite.serviceName === '专家管理') {
            this.zjglshowStatus = false
            this.zjglshowStatusopening = true
          } else if (ite.isChecked === null && ite.serviceName === '收支管理') {
            this.szglshowStatus = true
            this.szglshowStatusopening = false
          } else if (ite.isChecked === 1 && ite.serviceEnabledStatus === 0 && ite.serviceName === '收支管理') {
            this.szglshowStatus = false
            this.szglshowStatusopening = true
          } else if (ite.isChecked === 1 && ite.serviceEnabledStatus === 1 && ite.serviceName === '收支管理') {
            this.szglshowStatus = false
            this.szglshowStatusopening = true
          } else if (ite.isChecked === null && ite.serviceName === '资源分类') {
            this.zyszshowStatus = true
            this.zyszshowStatusopening = false
          } else if (ite.isChecked === 1 && ite.serviceEnabledStatus === 0 && ite.serviceName === '资源分类') {
            this.zyszshowStatus = false
            this.zyszshowStatusopening = true
          } else if (ite.isChecked === 1 && ite.serviceEnabledStatus === 1 && ite.serviceName === '资源分类') {
            this.zyszshowStatus = false
            this.zyszshowStatusopening = true
          }
        })
      })
    },
    immediatelyBtn () {
    //      this.$confirm('确认开通吗?', '提示', {
    //        confirmButtonText: '确定',
    //        cancelButtonText: '取消',
    //        type: 'success'
    //      }).then(() => {
    //        this.$router.push({path: '/notopen'})
    //      }).catch(() => {
    //        return false
    //      })
      this.$router.push({path: '/notopen'})
    },
    renewBtn () {
    //      this.$confirm('确认续费吗?', '提示', {
    //        confirmButtonText: '确定',
    //        cancelButtonText: '取消',
    //        type: 'success'
    //      }).then(() => {
    //        this.$router.push({path: '/renewal'})
    //      }).catch(() => {
    //        return false
    //      })
      this.$router.push({path: '/renewal'})
    }
  },
  mounted () {
    this.opensList()
  }
}
</script>
<style lang="less">
  #homepage{
    .logonewsbigbox{
      width: 100%;
      height: 125px;
      /*background: red;*/
      margin-top: 5vh;
    }
    .logocompany_bigbox{
      display: table;
      height: 125px;
      float: left;
    }
    .logo_box{
      width: 140px;
      height: 125px;
      line-height: 125px;
      text-align: center;
      /*background: gold;*/
      display: inline-block;
      vertical-align: top;
      position: relative;
    }
    .logo_box img{
      width: 80px;
      vertical-align: middle;
    }
    .company_box{
      display: table-cell;
      vertical-align:middle;
      height: 100%;
      font-size: 12px;
      color:#818181;
    }
    .company_box>span{
      display: inline-block;
    }
    .companname{
      font-size: 19px;
      color: #424242;
      font-weight: bold;
    }
    .news_bgibox{
      display: table;
      height: 125px;
      float: right;
    }
    .newsbox{
      display: table-cell;
      vertical-align:middle;
      text-align: center;
      padding-right:30px;
    }
    .newstitle{
      color: #999999;
      font-size: 14px;
      padding: 15px 0;
      display: inline-block;
    }
    .newsnumber{
      font-size:30px;
      color:#111111;
    }
    .servicebigbox{
      width: 100%;
    }
    .servicetitlebox{
      width: 100%;
      height: 65px;
      line-height: 65px;
      border-bottom: 1px solid #efefef;
      border-top: 1px solid #efefef;
      font-size: 16px;
      color:#111111;
      font-weight: bold;
      padding: 0 20px;
      box-sizing: border-box;
    }
    .switchbtnbox{
      width: 940px;
      margin: 0 auto;
      margin-top:25px;
    }
    .el-tabs__nav-wrap::after {
      content: "";
      position: absolute;
      left: 0;
      bottom: 0;
      width: 100%;
      height:0;
      background-color: #e4e7ed;
      z-index: 1;
    }
    .el-tabs__item{
      padding:0;
      width:100px;
      text-align: center;
      margin-right:10px;
      background:#f0f2f6;
      color:#818181;
    }
    .el-tabs__item.is-active {
      color: #fff;
      background:#3f63f6;
    }
    .el-tabs__active-bar {
      position: absolute;
      bottom: 0;
      left: 0;
      height: 2px;
      background-color:transparent;
      z-index: 1;
    }
    .el-tabs__header {
      padding: 0;
      position: relative;
      margin: 0 0 45px;
    }
    .gengduo{
      float: right;
      color:#3f63f6;
      font-size: 14px;
      margin-right: 30px;
      font-weight: normal
    }
    /*轮播样式*/
    .el-carousel__container {
      position: relative;
      height: 330px;
    }
    .el-carousel__item {
      width: 100%;
      display: inline-block;
      overflow: hidden;
      z-index: 0;
      padding: 0 20px;
      box-sizing: border-box;
    }
    .el-row {
      margin-bottom: 20px;
      &:last-child {
        margin-bottom: 0;
      }
    }
    .row-bg {
      padding: 10px 0;
      background-color: #f9fafc;
    }
    .grid-contentbox{
      transition: all .8s ease;
      cursor: pointer;
      margin-top: 20px;
      padding-top: 20px;
      background:#f3faff;
    }
    .grid-content{
      padding: 0 25px;
      padding-bottom: 25px;
      box-sizing: border-box;
    }
    .gridtitlebox{
      width: 100px;
      height: 26px;
      background:#d1dcfc;
      text-align: center;
      line-height: 28px;
      color:#333333;
      font-size: 14px;
      margin: 0 auto;
      border-radius:13px;
    }
    .gridimgbox{
      width: 50px;
      height: 50px;
      margin: 0 auto;
      text-align: center;
      padding: 35px 0;
    }
    .gridimgbox img{
      width: 40px;
      height: auto;
    }
    .gridmiaoshu{
      font-size: 14px;
      color: #666666;
      line-height: 1.8em;
      height: 46px;
      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 2;
      overflow: hidden;
    }
    .immediatelybtn{
      width: 145px;
      height: 30px;
      background:#3f63f6;
      color:#fff;
      text-align: center;
      line-height: 30px;
      font-size: 14px;
      margin: 0 auto;
      margin-top: 35px;
      opacity: 0;
      transition: all .8s ease;
    }
    .renewbtn{
      width: 145px;
      height: 30px;
      background:#ff8801;
      color:#fff;
      text-align: center;
      line-height: 30px;
      font-size: 14px;
      margin: 0 auto;
      margin-top: 35px;
      opacity: 0;
      transition: all .8s ease;
    }
    .grid-contentbox:hover{
      box-shadow: 0 0 30px rgba(63,99,246,0.3);
      margin-top: 10px;
    }
    .grid-contentbox:hover .immediatelybtn{
      opacity: 1;
    }
    .grid-contentbox:hover .renewbtn{
      opacity: 1;
    }
    .newsconbigbox{
      width: 100%;
      min-height: 50px;
      /*background: red;*/
      padding: 0 20px;
      padding-bottom:100px;
      box-sizing: border-box;
    }
    .grid-contentnewsbox{
      background:#fff;
      padding:10px 25px;
      box-sizing: border-box;
      font-size: 12px;
      color:#818181;
      background:#f5fafe;
      margin-top:20px;
    }
    .xianbox{
      width: 1px;
      height: 45%;
      background: #bfbfbf;
      margin-top: 40px;
      margin-right: 30px;
    }
  }
</style>
