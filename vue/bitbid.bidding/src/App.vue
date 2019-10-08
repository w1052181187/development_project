<template>
  <div id="app">
    <div v-if="$route.meta.layout">
      <div v-if="$route.meta.layout === 'bidding'">
        <el-row v-if="$route.meta.projectType === 'mineralRights'">
          <assigneeMenu v-if="isNotTrans"></assigneeMenu>
          <transferorMenu v-else></transferorMenu>
          <el-col :span="21">
            <commonhead></commonhead>
            <router-view/>
            <commonfoot></commonfoot>
          </el-col>
        </el-row>
        <el-row v-if="$route.meta.projectType === 'landTransaction'">
          <assigneeMenuLandTra v-if="isNotTrans"></assigneeMenuLandTra>
          <transferorMenuLandTra v-else></transferorMenuLandTra>
          <el-col :span="21">
            <commonhead></commonhead>
            <router-view/>
            <commonfoot></commonfoot>
          </el-col>
        </el-row>
      </div>
      <div v-if="$route.meta.layout === 'SystemManagement'">
        <el-row>
          <Systemcommonleft></Systemcommonleft>
          <el-col :span="21">
            <Systemcommonhead></Systemcommonhead>
            <router-view/>
            <Systemcommonfoot></Systemcommonfoot>
          </el-col>
        </el-row>
      </div>
    </div>
    <div v-else>
      <headers v-show="!$route.meta.isshow"></headers>
      <router-view/>
      <footers v-show="!$route.meta.isshow || $route.path=='/login'"></footers>
    </div>
  </div>
</template>

<script>
import headers from '@/components/public/header'
import footers from '@/components/public/footer'
// 引入矿权头部
// import commonhead from '@/components/public-resource/mineral-rights/common/header.vue'
// import commonfoot from '@/components/public-resource/mineral-rights/common/footer.vue'
// 引入土地头部
import commonhead from '@/components/public-resource/land-transaction/common/header.vue'
import commonfoot from '@/components/public-resource/land-transaction/common/footer.vue'
// 引入运营系统管理 公共头底及左侧
import Systemcommonleft from '@/components/system-management/left.vue'
import Systemcommonhead from '@/components/system-management/header.vue'
import Systemcommonfoot from '@/components/system-management/footer.vue'
export default {
  name: 'App',
  // 矿权判断左侧
  data () {
    return {
      isNotTrans: true
    }
  },
  components: {
    headers,
    footers,
    commonhead,
    commonfoot,
    // ---------------------------------------------------------矿权--------------------------------------------------------
    // 受让人左侧菜单
    assigneeMenu: resolve => require(['@/components/public-resource/mineral-rights/left-assignee.vue'], resolve),
    // 出让人左侧菜单
    transferorMenu: resolve => require(['@/components/public-resource/mineral-rights/left-transferor.vue'], resolve),
    // ---------------------------------------------------------土地交易--------------------------------------------------------
    // 受让人左侧菜单
    assigneeMenuLandTra: resolve => require(['@/components/public-resource/land-transaction/left-assignee.vue'], resolve),
    // 出让人左侧菜单
    transferorMenuLandTra: resolve => require(['@/components/public-resource/land-transaction/left-transferor.vue'], resolve),
    // 运营平台
    Systemcommonleft,
    Systemcommonhead,
    Systemcommonfoot,
    // 刚进入时候的判断
    showStatus: false
  },
  methods: {
    addMethod () {
      let allCommentMethod = {}
      let that = this
      allCommentMethod.Change_User = function (userType) {
        if (that.$store.getters.authUser.userType !== 1) {
          if (that.$store.getters.authUser.subjectIds !== null && that.$store.getters.authUser.subjectIds !== undefined) {
            that.$store.getters.authUser.subjectIds.map(item => {
              if (item === 10008) {
                that.isNotTrans = true // 受让人
              } else if (item === 10009) {
                this.isNotTrans = false // 出让人
              }
            })
          }
        }
      }
      window.allCommentMethod = allCommentMethod
    }
  },
  watch: {
    $route: function () {
      if (this.$route.query.showStatus !== undefined) {
        this.showStatus = this.$route.query.showStatus
      }
      if (Number(this.$route.query.roledsType) === 1) {
        this.isNotTrans = false
        return false
      } else if (Number(this.$route.query.roledsType) === 2) {
        this.isNotTrans = true
        return false
      }
      if (this.$store.getters.authUser.userType !== 1) {
        if (this.$store.getters.authUser.subjectIds !== null && this.$store.getters.authUser.subjectIds !== undefined) {
          if (this.showStatus) {
            if (Number(this.$route.query.roledsType) === 1) {
              this.isNotTrans = false
            } else if (Number(this.$route.query.roledsType) === 2) {
              this.isNotTrans = true
            }
          } else {
            if (Number(this.$route.query.roledsType) === 1) {
              this.isNotTrans = false
              this.showStatus = true // 刚进来判断
            } else {
              this.isNotTrans = true
              this.showStatus = true // 刚进来判断
            }
          }
          this.$store.getters.authUser.subjectIds.map(item => {
            if (item === 10008) {
              this.isNotTrans = true // 受让人
            } else if (item === 10009) {
              this.isNotTrans = false // 出让人
            }
          })
        }
      }
    }
  },
  created () {
    this.addMethod()
  }
}
</script>

<style lang="less">
  body,p,h1,h2,h3,h4,h5,h6,ul,li,ol,dl,dt,dd,input,form{
    margin:0px;
    list-style:none;/*清除小圆点*/
    padding:0px;
    font-family:Arial,"微软雅黑","黑体";
  }
  #app{
    /* l_header start */
    .wrap {
      width: 1200px;
      margin: 0 auto;
    }

    .wrap:after {
      content: " ";
      display: block;
      clear: both;
    }
    .l_header {
      font-size: 12px;
      color: #666666;
      height: 40px;
      line-height: 40px;
    }

    .l_header_left {
      float: left;
    }

    .l_header_left > span {
      cursor: pointer;
    }
    .l_header_right {
      float: right;
      position: relative;
    }
    .l_header_right >  span {
      margin-left: 30px;
    }

    .l_header_right >  span em{
      font-style:normal;
    }

    /* l_header end*/

    /* -------------------heading start-----------------------*/
    .breadcrumb_box {
      width: 100%;
      height: 60px!important;
      border-bottom: 1px solid #eeeeee;
      padding: 0 20px;
      box-sizing: border-box;
      line-height: 60px!important;
    }
    .head{
      width: 100%;
      height: 80px;
      overflow: hidden;
      margin: 0 auto;
      background: #3f63f6;
      position: relative;
    }
    .head .wrap{
      width: 1200px;
      height: 80px;
      overflow: hidden;
      margin:0 auto;
    }
    .head .wrap .logo{
      float: left;
      overflow: hidden;
      line-height: 80px;
    }
    .head .wrap .logo img{ vertical-align: middle;}
    .head .wrap .logo span{ color:#ffffff; font-size: 16px; font-weight: bold; margin-left:20px; display: inline-block;}

    .head_right{
      float: right;
      overflow: hidden;
      line-height:80px;
    }
    .head_right p{
      float: left;
      margin: 0 20px;
      color:#ffffff;
      font-size: 14px;
      line-height: 80px;
    }
    .head_right img{ vertical-align: middle;}
    /* new  header */

    /* ----------content start--------- */
    #content:after {
      content: " ";
      display: block;
      clear: both;
    }
    .content_bigbox{
      width: 1200px;
      margin: 18px auto 0;
      background: #fff;
    }
    .table-header{
      background: #f5f7f8;
      line-height: 48px
    }
    .breadcrumb {
      background: none;
      margin-top: 20px;
    }

    .breadcrumb > li + li:before {
      color: #CCCCCC;
      content: "> ";
      font-size: 14px;
      padding: 0 5px;
    }

    .breadcrumb > li > a {
      color: #999999;
    }

    .breadcrumb > .active {
      color: #333333;
    }
    /* ---------------------------------------- */
    /* --------------footer start----------- */
    #footer {
      width: 1200px;
      margin: 0 auto;
      font-size: 12px;
      color: #666666;
    }
    .l_footer_bottom{
      line-height: 28px;
      margin: 20px 0;
      text-align: center;
    }
    /*分页样式*/
    .el-pagination {
      white-space: nowrap;
      padding: 30px 5px!important;
      color: #303133;
      font-weight: 700;
      margin: 0 auto;
      text-align: center!important;
    }
    // 矿权
    .el-col-21{
      float: right;
      position: relative;
      background: #f5f5f5;
    }
    .el-container{
      height: 100%;
    }
    .sidebox{
      border: none;
    }
    .indexs{
      text-align: left;
    }
    .el-main{
      padding: 0;
    }
    .pagebox{
      text-align: right!important;
    }
    .el-form span{
      word-break: break-all;
      word-wrap: break-word;
      display:inline-block;
      display: inline-block;
    }
  }
</style>
