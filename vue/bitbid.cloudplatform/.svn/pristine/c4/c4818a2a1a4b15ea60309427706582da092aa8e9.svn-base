<template>
  <div id="indexhomepage">
    <div class="homepagecontents">
      <div class="homepageconrightindex">
        <div class="logonewsbigbox">
          <div class="logocompany_bigbox">
            <div class="logo_box">
              <img src="../../../static/images/toux.png" alt="">
            </div>
            <div class="company_box">
              <span class="companname">{{$store.getters.authUser.userName}}</span>
              <br>
              <div class="gongshiname">
                <div class="gongshiimg">
                  <img src="../../../static/images/index.png" alt="">
                </div>
                <span style="color: #3f63f6;font-size: 16px">山西广信建设项目管理有限公司</span>
              </div>
            </div>
          </div>
        </div>
        <div class="indexcontentbigbox">
          <div class="indexcontentbox">
            <div class="indexcontopbig">
              <div class="indexcontitle">我的应用</div>
            </div>
            <el-tabs v-model="activeName" class="wdyybigbox">
              <el-tab-pane label="业务辅助" name="ywfz">
                <el-row>
                  <el-col :span="4">
                    <div class="ywfzimgbigbox" @click="daglBtn">
                      <div class="ywfzimgbox">
                        <div class="ywfzimgzzt1"></div>
                      </div>
                      <div class="ywfztitlebox">档案管理</div>
                    </div>
                  </el-col>
                  <el-col :span="4">
                    <div class="ywfzimgbigbox" @click="yzglBtn">
                      <div class="ywfzimgbox">
                        <div class="ywfzimgzzt2"></div>
                      </div>
                      <div class="ywfztitlebox">业主管理</div>
                    </div>
                  </el-col>
                  <el-col :span="4">
                    <div class="ywfzimgbigbox" @click="zjglBtn">
                      <div class="ywfzimgbox">
                        <div class="ywfzimgzzt3"></div>
                      </div>
                      <div class="ywfztitlebox">专家管理</div>
                    </div>
                  </el-col>
                  <el-col :span="4">
                    <div class="ywfzimgbigbox" @click="szglBtn">
                      <div class="ywfzimgbox">
                        <div class="cyst9"></div>
                      </div>
                      <div class="ywfztitlebox">收支管理</div>
                    </div>
                  </el-col>
                </el-row>
              </el-tab-pane>
              <el-tab-pane label="系统管理" name="xtgl">
                <el-row>
                  <el-col :span="4">
                    <div class="ywfzimgbigbox" @click="zyszBtn">
                      <div class="ywfzimgbox">
                        <div class="cyst8"></div>
                      </div>
                      <div class="ywfztitlebox">资源设置</div>
                    </div>
                  </el-col>
                </el-row>
              </el-tab-pane>
              <el-tab-pane label="日常管理" name="zcgl">
                <el-row>
                  <el-col :span="4">
                    <div class="ywfzimgbigbox" @click="qyxxBtn">
                      <div class="ywfzimgbox">
                        <div class="qyxx"></div>
                      </div>
                      <div class="ywfztitlebox">企业信息</div>
                    </div>
                  </el-col>
                  <el-col :span="4">
                    <div class="ywfzimgbigbox" @click="bmglBtn">
                      <div class="ywfzimgbox">
                        <div class="bmgl"></div>
                      </div>
                      <div class="ywfztitlebox">部门管理</div>
                    </div>
                  </el-col>
                  <el-col :span="4">
                    <div class="ywfzimgbigbox" @click="ryglBtn">
                      <div class="ywfzimgbox">
                        <div class="rygl"></div>
                      </div>
                      <div class="ywfztitlebox">用户管理</div>
                    </div>
                  </el-col>
                  <el-col :span="4">
                    <div class="ywfzimgbigbox" @click="jsglBtn">
                      <div class="ywfzimgbox">
                        <div class="jsgl"></div>
                      </div>
                      <div class="ywfztitlebox">角色管理</div>
                    </div>
                  </el-col>
                  <el-col :span="4">
                    <div class="ywfzimgbigbox" @click="zxspBtn">
                      <div class="ywfzimgbox">
                        <div class="zxsp"></div>
                      </div>
                      <div class="ywfztitlebox">在线审批</div>
                    </div>
                  </el-col>
                </el-row>
              </el-tab-pane>
              <el-tab-pane label="招标流程管理" name="zblc">
                <el-row>
                  <el-col :span="4">
                    <div class="ywfzimgbigbox" @click="djxmBtn">
                      <div class="ywfzimgbox">
                        <div class="djxm"></div>
                      </div>
                      <div class="ywfztitlebox">登记项目信息</div>
                    </div>
                  </el-col>
                  <el-col :span="4">
                    <div class="ywfzimgbigbox" @click="dlfpBtn">
                      <div class="ywfzimgbox">
                        <div class="dlfp"></div>
                      </div>
                      <div class="ywfztitlebox">代理分配项目</div>
                    </div>
                  </el-col>
                  <el-col :span="4">
                    <div class="ywfzimgbigbox" @click="xmjdBtn">
                      <div class="ywfzimgbox">
                        <div class="xmjd"></div>
                      </div>
                      <div class="ywfztitlebox">项目进度管理</div>
                    </div>
                  </el-col>
                  <el-col :span="4">
                    <div class="ywfzimgbigbox" @click="xmlcBtn">
                      <div class="ywfzimgbox">
                        <div class="xmlc"></div>
                      </div>
                      <div class="ywfztitlebox">项目流程配置管理</div>
                    </div>
                  </el-col>
                  <el-col :span="4">
                    <div class="ywfzimgbigbox" @click="dlhtBtn">
                      <div class="ywfzimgbox">
                        <div class="dlht"></div>
                      </div>
                      <div class="ywfztitlebox">代理合同管理</div>
                    </div>
                  </el-col>
                </el-row>
              </el-tab-pane>
              <el-tab-pane label="企业网站管理" name="qywz">
                <el-row>
                  <el-col :span="4">
                    <div class="ywfzimgbigbox" @click="wzsyBtn">
                      <div class="ywfzimgbox">
                        <div class="wzsy"></div>
                      </div>
                      <div class="ywfztitlebox">网站首页管理</div>
                    </div>
                  </el-col>
                  <el-col :span="4">
                    <div class="ywfzimgbigbox" @click="gsgkBtn">
                      <div class="ywfzimgbox">
                        <div class="gsgk"></div>
                      </div>
                      <div class="ywfztitlebox">公司概况</div>
                    </div>
                  </el-col>
                  <el-col :span="4">
                    <div class="ywfzimgbigbox" @click="xxggBtn">
                      <div class="ywfzimgbox">
                        <div class="xxgg"></div>
                      </div>
                      <div class="ywfztitlebox">信息公告</div>
                    </div>
                  </el-col>
                  <el-col :span="4">
                    <div class="ywfzimgbigbox" @click="zhxxBtn">
                      <div class="ywfzimgbox">
                        <div class="zhxx"></div>
                      </div>
                      <div class="ywfztitlebox">综合信息</div>
                    </div>
                  </el-col>
                  <el-col :span="4">
                    <div class="ywfzimgbigbox" @click="lxwmBtn">
                      <div class="ywfzimgbox">
                        <div class="lxwm"></div>
                      </div>
                      <div class="ywfztitlebox">联系我们</div>
                    </div>
                  </el-col>
                </el-row>
              </el-tab-pane>
            </el-tabs>
          </div>
          <div class="indexcontentbox" style="margin-top: 20px">
            <div class="indexcontopbig">
              <div class="indexcontitle">我的资源</div>
            </div>
            <el-row class="indexconbottomzy" style="padding-bottom: 50px">
              <el-col :span="6">
                <div class="wdzybigbox1" @click="yzglBtn">
                  <div class="wdzybox">
                    <div class="wdzybox1imgtop">
                      <img src="../../../static/images/zbr.svg" alt="" class="wdzyimgicon">
                    </div>
                    <div class="wdzybox1titlebottom">招标人</div>
                  </div>
                </div>
              </el-col>
              <el-col :span="6">
                <div class="wdzybigbox2" @click="zbdljgBtn">
                  <div class="wdzybox">
                    <div class="wdzybox1imgtop">
                      <img src="../../../static/images/zbdljg.svg" alt="">
                    </div>
                    <div class="wdzybox1titlebottom">招标代理机构</div>
                  </div>
                </div>
              </el-col>
              <el-col :span="6">
                <div class="wdzybigbox4" @click="zbdljgBtn">
                  <div class="wdzybox">
                    <div class="wdzybox1imgtop">
                      <img src="../../../static/images/toubiaor.png" alt="">
                    </div>
                    <div class="wdzybox1titlebottom">投标人</div>
                  </div>
                </div>
              </el-col>
              <el-col :span="6">
                <div class="wdzybigbox3" @click="zjglBtn">
                  <div class="wdzybox">
                    <div class="wdzybox1imgtop">
                      <img src="../../../static/images/zhuanjia.svg" alt="">
                    </div>
                    <div class="wdzybox1titlebottom">专家</div>
                  </div>
                </div>
              </el-col>
            </el-row>
          </div>
          <div class="guanggaobigbox">
            <a href="https://www.bibenet.com/" class="ggljbox" target="_blank">
              <img src="../../../static/images/guanggao.png" alt="">
            </a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import {application} from '../../api/index'
export default {
  data () {
    return {
      // 默认招标采购为选中
      activeName: 'ywfz',
      daglshowStatus: true,
      yzglshowStatus: true,
      zjglshowStatus: true,
      szglshowStatus: true,
      zyszshowStatus: true
    }
  },
  methods: {
    // 常用系统展示
    applicationList () {
      application.applicationList(this.$store.getters.authUser.userId).then((res) => {
        res.data.managementList.map((ite) => {
          if (ite.isChecked === 1 && ite.serviceName === '档案管理') {
            this.daglshowStatus = false
          } else if (ite.isChecked === 1 && ite.serviceName === '业主管理') {
            this.yzglshowStatus = false
          } else if (ite.isChecked === 1 && ite.serviceName === '专家管理') {
            this.zjglshowStatus = false
          } else if (ite.isChecked === 1 && ite.serviceName === '收支管理') {
            this.szglshowStatus = false
          } else if (ite.isChecked === 1 && ite.serviceName === '资源设置') {
            this.zyszshowStatus = false
          }
        })
      })
    },
    daglBtn () {
      this.$router.push({path: '/archives'})
    },
    yzglBtn () {
      this.$router.push({path: '/owner'})
    },
    zjglBtn () {
      this.$router.push({path: '/expert'})
    },
    zyszBtn () {
      this.$router.push({path: '/owner-set'})
    },
    zbdljgBtn () {
      this.$router.push({path: '/recenotopens'})
    },
    szglBtn () {
      this.$router.push({path: '/projectpayments'})
    },
    setUp () {
      this.$router.push({path: '/application'})
    },
    // 云平台  首页模块
    qyxxBtn () {
      this.$router.push({path: '/dailyManage/enterprise'})
    },
    bmglBtn () {
      this.$router.push({path: '/dailyManage/department'})
    },
    ryglBtn () {
      this.$router.push({path: '/dailyManage/users'})
    },
    jsglBtn () {
      this.$router.push({path: '/dailyManage/roles'})
    },
    zxspBtn () {
      this.$router.push({path: '/dailyManage/onlineapprove'})
    },
    djxmBtn () {
      this.$router.push({path: '/processManage/registProject'})
    },
    dlfpBtn () {
      this.$router.push({path: '/processManage/agentProject'})
    },
    xmjdBtn () {
      this.$router.push({path: '/processManage/projectProcess'})
    },
    xmlcBtn () {
      this.$router.push({path: '/processManage/proProcessSet'})
    },
    dlhtBtn () {
      this.$router.push({path: '/processManage/agencyContract'})
    },
    wzsyBtn () {
      this.$router.push({path: '/websiteManage/home'})
    },
    gsgkBtn () {
      this.$router.push({path: '/websiteManage/company'})
    },
    xxggBtn () {
      this.$router.push({path: '/websiteManage/notice'})
    },
    zhxxBtn () {
      this.$router.push({path: '/websiteManage/information'})
    },
    lxwmBtn () {
      this.$router.push({path: '/websiteManage/contact'})
    }
  },
  mounted () {
    this.applicationList()
  }
}
</script>
<style lang="less">
  #indexhomepage{
    .logonewsbigbox{
      width: 100%;
      height: 125px;
      background:#fff;
      margin-top:25px;
    }
    .homepageconrightindex{
      width: 100%;
      margin-top: 20px;
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
      float: left;
      text-align: center;
    }
    .logo_box img{
      vertical-align: middle;
      max-width: 100%;
      max-height: 100%;
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
      font-size: 22px;
      color:#333333;
      font-weight: bold;
      padding-bottom:15px;
    }
    .gongshiimg img{
      width: 60%;
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
      padding-right:40px;
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
    .homepagecontents {
      width: 1200px;
      margin: 0 auto;
      /* background: red; */
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
      width:815px;
      margin-top:25px;
      height: 50vh;
      background: #fff;
      display: inline-block;
      vertical-align: top;
    }
    .switchbtnboxright{
      width: 360px;
      height: 50vh;
      background: #fff;
      display: inline-block;
      margin-top:25px;
      margin-left: 20px;
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
    .el-tabs__item.is-active {
      color: #3f63f9;
    }
    .el-tabs__active-bar {
      position: absolute;
      bottom: 0;
      left: 0;
      height: 2px;
      background-color:#3f63f9;
      z-index: 1;
    }
    .el-tabs__header {
      padding: 0;
      position: relative;
      margin: 0 0 10px;
    }
    .gengduo{
      float: right;
      color:#3f63f6;
      font-size: 14px;
      margin-right: 30px;
      font-weight: normal
    }
    .gridtitlebox{
      width: 100%;
      height: 26px;
      text-align: center;
      line-height: 28px;
      color:#333333;
      font-size: 14px;
      margin: 0 auto;
      border-radius:13px;
    }
    .gongshiname{
      width: 100%;
      height: 25px;
      line-height: 25px;
    }
    .gongshiimg{
      width:25px;
      height: 25px;
      display: inline-block;
      vertical-align: top;
      position: relative;
    }
    .gongshiimg>img{
      width: 80%;
      position: absolute;
      left: 0;
      right: 0;
      top:0;
      bottom: 0;
      margin: auto;
    }
    .indexcontentbigbox{
      width: 100%;
      min-height:50px;
      margin-top: 20px;
    }
    .indexcontentbox{
      width: 100%;
      padding: 20px 15px;
      box-sizing: border-box;
      background:#fff;
    }
    .indexcontitle{
      font-size: 18px;
      color:#111111;
      font-weight: bold;
    }
    .indexcontopbig{
      width: 100%;
      height: 25px;
    }
    .indexcontitle{
      display: inline-block;
      vertical-align: top;
    }
    .shizhibtn{
      display: inline-block;
      width: 25px;
      height: 25px;
      float: right;
      vertical-align: top;
      position: relative;
    }
    .shizhibtn>img{
      width: 60%;
      position: absolute;
      left: 0;
      right: 0;
      top:0;
      bottom: 0;
      margin: auto;
      cursor: pointer;
    }
    .wdyybigbox{
      width: 95%;
      margin: 0 auto;
      margin-top: 20px;
    }
    .ywfzimgbigbox{
      cursor: pointer;
      margin-top: 50px;
      transition: all .8s ease;
      padding-bottom: 30px;
      position: relative;
    }
    .newuserbigbox{
      width: 20%;
      height: 185px;
      float: left;
    }
    .newywfzimgbigbox{
      cursor: pointer;
      transition: all .8s ease;
      position: relative;
    }
    .newywfzimgbox{
      width: 100%;
      height:110px;
      padding: 25px 0;
      box-sizing: border-box;
      text-align: center;
    }
    .newywfzimgbox>img{
      width: 20%;
      height: auto!important;
    }
    .newywfztitlebox{
      font-size: 14px;
      color:#333;
      width: 100%;
      text-align: center;
      transition: all .8s ease;
    }
    .UnderstandBtn{
      width: 70px;
      height: 25px;
      margin: 0 auto;
      background:#ff6600;
      font-size: 12px;
      text-align: center;
      line-height: 25px;
      cursor: pointer;
    }
    .UnderstandBtn>span{
      color:#fff!important;
    }
    .ywfzimgbox{
      width: 100%;
      height:130px;
      padding: 30px 0;
      box-sizing: border-box;
      text-align: center;
    }
    .ywfzimgzzt1{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/dagl_h.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgzzt2{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/yzgl_h.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgzzt3{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/zjgl_h.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgzzt4{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/zygl_h.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgzztshou{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/szgl_h.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgzztmore{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/gengduo_h.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgzzt_yypt{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/yypt_h.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfztitlebox{
      font-size: 14px;
      color:#999999;
      width: 100%;
      text-align: center;
      transition: all .8s ease;
    }
    .ywfzimgbigbox:hover{
      box-shadow: 0 0 30px rgba(63,99,246,0.3);
      background:#f3faff;
    }
    .ywfzimgbigbox:hover .ywfztitlebox{
      color:#3f63f6;
    }
    .ywfzimgbigbox:hover .ywfzimgzzt1{
      background:url("../../../static/images/bibi/dagl_l.png")no-repeat center center;
      background-size:50px auto;
    }
    .ywfzimgbigbox:hover .ywfzimgzzt2{
      background:url("../../../static/images/bibi/yzgl_l.png")no-repeat center center;
      background-size:50px auto;
    }
    .ywfzimgbigbox:hover .ywfzimgzzt3{
      background:url("../../../static/images/bibi/zjgl_l.png")no-repeat center center;
      background-size:50px auto;
    }
    .ywfzimgbigbox:hover .ywfzimgzzt4{
      background:url("../../../static/images/bibi/zygl_l.png")no-repeat center center;
      background-size:50px auto;
    }
    .ywfzimgbigbox:hover .ywfzimgzztshou{
      background:url("../../../static/images/bibi/szgl_l.png")no-repeat center center;
      background-size:50px auto;
    }
    .ywfzimgbigbox:hover .ywfzimgzztmore{
      background:url("../../../static/images/bibi/gengduo_l.png")no-repeat center center;
      background-size:50px auto;
    }
    .ywfzimgbigbox:hover .ywfzimgzzt_yypt{
      background:url("../../../static/images/bibi/yypt_l.png")no-repeat center center;
      background-size:50px auto;
    }
    .indexconbottomzy{
      margin-top: 50px;
    }
    .wdzybigbox1{
      width:206px;
      height: 203px;
      background: url("../../../static/images/zhaobiaort.png");
      background-size: 206px 203px;
      margin: 0 auto;
      cursor: pointer;
      transition: all .8s ease;
    }
    .wdzybox{
      width: 100%;
      height: 100%;
      background:transparent;
      transition:all .8s ease;
    }
    .wdzybigbox1:hover .wdzybox{
      background:rgba(63,99,246,0.7);
    }
    .wdzybigbox1:hover{
      margin-top: -10px;
    }
    .wdzybox1imgtop{
      width: 100%;
      height:145px;
      position: relative;
    }
    .wdzybox1imgtop>img{
      width: 35%;
      position: absolute;
      left: 0;
      right: 0;
      top:0;
      bottom: 0;
      margin: auto;
    }
    .wdzybox1titlebottom{
      width: 100%;
      height: 58px;
      font-size: 14px;
      color: #fff;
      text-align: center;
    }
    .wdzybigbox2{
      width:206px;
      height: 203px;
      background: url("../../../static/images/jitout.png");
      background-size: 206px 203px;
      margin: 0 auto;
      cursor: pointer;
      transition: all .8s ease;
    }
    .wdzybigbox3{
      width:206px;
      height: 203px;
      background-size: 206px 203px;
      background: url("../../../static/images/toubiaort.png");
      margin: 0 auto;
      cursor: pointer;
      transition: all .8s ease;
    }
    .wdzybigbox4{
      width:206px;
      height: 203px;
      background-size: 206px 203px;
      background: url("../../../static/images/zhaobiaort.png");
      margin: 0 auto;
      cursor: pointer;
      transition: all .8s ease;
    }
    .wdzybigbox2:hover .wdzybox{
      background:rgba(63,99,246,0.7);
    }
    .wdzybigbox3:hover .wdzybox{
      background:rgba(63,99,246,0.7);
    }
    .wdzybigbox4:hover .wdzybox{
      background:rgba(63,99,246,0.7);
    }
    .wdzybigbox2:hover{
      margin-top: -10px;
    }
    .wdzybigbox3:hover{
      margin-top: -10px;
    }
    .wdzybigbox4:hover{
      margin-top: -10px;
    }
    .guanggaobigbox{
      width: 100%;
      margin-top: 25px;
    }
    .guanggaobigbox>img{
      width: 100%;
    }
    .ggljbox{
      display: block;
      width: 100%;
    }
    .cyxtbox{
      text-align: center;
      min-height: 230px;
      line-height: 230px;
      font-size: 20px;
      color: #666666;
    }
    .cyst5{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/zjgl_h.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgbigbox:hover .cyst5{
      background:url("../../../static/images/bibi/zjgl_l.png")no-repeat center center;
      background-size:50px auto;
    }
    .cyst6{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/dagl_h.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgbigbox:hover .cyst6{
      background:url("../../../static/images/bibi/dagl_l.png")no-repeat center center;
      background-size:50px auto;
    }
    .cyst7{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/yzgl_h.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgbigbox:hover .cyst7{
      background:url("../../../static/images/bibi/yzgl_l.png")no-repeat center center;
      background-size:50px auto;
    }
    .cyst8{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/zygl_h.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgbigbox:hover .cyst8{
      background:url("../../../static/images/bibi/zygl_l.png")no-repeat center center;
      background-size:50px auto;
    }
    .cyst9{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/szgl_h.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgbigbox:hover .cyst9{
      background:url("../../../static/images/bibi/szgl_l.png")no-repeat center center;
      background-size:50px auto;
    }
    // 云平台添加模块小图标样式
    .qyxx{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/qyxx.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgbigbox:hover .qyxx{
      background:url("../../../static/images/bibi/qyxx_h.png")no-repeat center center;
      background-size:50px auto;
    }
    .bmgl{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/bmgl.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgbigbox:hover .bmgl{
      background:url("../../../static/images/bibi/bmgl_h.png")no-repeat center center;
      background-size:50px auto;
    }
    .rygl{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/rygl.png")no-repeat center center;
      background-size:47px auto;
      margin: 0 auto;
    }
    .ywfzimgbigbox:hover .rygl{
      background:url("../../../static/images/bibi/rygl_h.png")no-repeat center center;
      background-size:45px auto;
    }
    .jsgl{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/jsgl.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgbigbox:hover .jsgl{
      background:url("../../../static/images/bibi/jsgl_h.png")no-repeat center center;
      background-size:50px auto;
    }
    .zxsp{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/zxsp.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgbigbox:hover .zxsp{
      background:url("../../../static/images/bibi/zxsp_h.png")no-repeat center center;
      background-size:50px auto;
    }
    .djxm{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/djxm.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgbigbox:hover .djxm{
      background:url("../../../static/images/bibi/djxm_h.png")no-repeat center center;
      background-size:50px auto;
    }
    .xmjd{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/xmjd.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgbigbox:hover .xmjd{
      background:url("../../../static/images/bibi/xmjd_h.png")no-repeat center center;
      background-size:50px auto;
    }
    .xmlc{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/xmlc.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgbigbox:hover .xmlc{
      background:url("../../../static/images/bibi/xmlc_h.png")no-repeat center center;
      background-size:50px auto;
    }
    .dlfp{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/dlfp.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgbigbox:hover .dlfp{
      background:url("../../../static/images/bibi/dlfp_h.png")no-repeat center center;
      background-size:50px auto;
    }
    .dlht{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/dlht.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgbigbox:hover .dlht{
      background:url("../../../static/images/bibi/dlht_h.png")no-repeat center center;
      background-size:50px auto;
    }
    // 网站后台管理
    .wzsy{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/wzsy.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgbigbox:hover .wzsy{
      background:url("../../../static/images/bibi/wzsy_h.png")no-repeat center center;
      background-size:50px auto;
    }
    .gsgk{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/gsgk.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgbigbox:hover .gsgk{
      background:url("../../../static/images/bibi/gsgk_h.png")no-repeat center center;
      background-size:50px auto;
    }
    .xxgg{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/xxgg.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgbigbox:hover .xxgg{
      background:url("../../../static/images/bibi/xxgg_h.png")no-repeat center center;
      background-size:50px auto;
    }
    .zhxx{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/zhxx.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgbigbox:hover .zhxx{
      background:url("../../../static/images/bibi/zhxx_h.png")no-repeat center center;
      background-size:50px auto;
    }
    .lxwm{
      width: 60px;
      height: 60px;
      background:url("../../../static/images/bibi/lxwm.png")no-repeat center center;
      background-size:50px auto;
      margin: 0 auto;
    }
    .ywfzimgbigbox:hover .lxwm{
      background:url("../../../static/images/bibi/lxwm_h.png")no-repeat center center;
      background-size:50px auto;
    }
    .maskbox{
      width: 100%;
      height: 100%;
      position: absolute;
      left: 0;
      top: 0;
      background: rgba(0,0,0,0.4);
      display: none;
    }
    .ywfzimgbigbox:hover .maskbox{
      display: block;
    }
    .masktitles{
      font-size: 14px;
      color:#fff;
      text-align: center;
      padding: 30px 0;
    }
    .rolebigbox{
      width: 100%;
      min-height: 30px;
    }
    .rolebox{
      width: 50%;
      height: 26px;
      line-height: 30px;
      font-size: 12px;
      border: 1px solid #f29d28;
      border-radius: 13px;
      margin: 0 auto;
      text-align: center;
      color: #fff;
      margin-bottom: 30px;
      cursor: pointer
    }
    .rolebox:hover{
      background:#f29d28;
    }
    .yzglimg>img{
      width: 55px;
    }
  }
</style>
