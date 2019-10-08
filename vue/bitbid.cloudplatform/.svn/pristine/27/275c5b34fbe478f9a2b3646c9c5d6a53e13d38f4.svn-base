<template>
  <div id="autrtic">
    <div class="homepagecontent">
      <publicside></publicside>
      <div class="homepageconright">
        <!--面包屑-->
        <el-breadcrumb class="breadcrumbbox" separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>企业认证</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
        <!--内容开始-->
        <div class="penserviceconbox">
          <div class="penservicecontitle">企业认证</div>
          <div class="authenbigbox">
            <div class="authenbox">
              <el-row>
                <el-col :span="2" class="biaotizztbox">
                  <div class="grid-content"></div>
                </el-col>
                <el-col :span="22" class="rightqiyebox">
                  <div class="lefttitlebox">
                    <div class="lefttitlecon">中建卓越公共资源交易平台</div>
                  </div>
                  <div class="contentrightbox">
                    <div class="contentrightbox_titletopbox">
                      <el-col :span="12"><div class="auttitlebox">认证主体</div></el-col>
                      <el-col :span="4"><div class="auttitlebox"></div></el-col>
                      <el-col :span="8"><div class="auttitlebox borright"></div></el-col>
                    </div>
                    <div class="contentrightbox_conbottombox">
                      <el-col :span="12" class="autfatherbigbox">
                          <el-col :span="8" v-for="(uthencon,index) in authenList" :key="index" class="grid-contentneibox">
                            <div class="grid-contentnei">
                              <span class="statesbox_uncertified" v-show="uthencon.aut_status == '0'"></span>
                              <span class="statesbox_lan" v-show="uthencon.aut_status == '1'"></span>
                              <span class="statesbox_lvbtn" v-show="uthencon.aut_status == '2'"></span>
                              <span v-bind:title="uthencon.names">{{uthencon.names}}</span>
                            </div>
                          </el-col>
                      </el-col>
                      <el-col :span="4" class="zhuangtaibox">
                        <div class="autconbotbox_topstatus">状态</div>
                        <div class="autconbotbox_tijiao">已提交</div>
                      </el-col>
                      <el-col :span="8" class="caozuobtn">
                        <div class="ljrztitlebox">操作</div>
                        <div class="ljrzconbox">
                          <el-col :span="8"><div class="grid-contentcao" @click="immediatelyBtn">立即认证</div></el-col>
                          <el-col :span="8"><div class="grid-contentcao">重新认证</div></el-col>
                          <el-col :span="8"><div class="grid-contentcao" @click="Lookdetails">查看详情</div></el-col>
                        </div>
                      </el-col>
                    </div>
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
        <!--内容开始-->
      </div>
    </div>
  </div>
</template>
<script>
import publicside from '../adminpublic/side.vue'
export default {
  components: {
    publicside
  },
  data () {
    return {
      authenList: [{
        aut_status: '0',
        names: '招标人'
      }, {
        aut_status: '0',
        names: '招标代理'
      }, {
        aut_status: '0',
        names: '投标人'
      }, {
        aut_status: '0',
        names: '采购单位'
      }, {
        aut_status: '1',
        names: '采购代理'
      }, {
        aut_status: '2',
        names: '供应商'
      }, {
        aut_status: '1',
        names: '国有产权转让'
      }, {
        aut_status: '2',
        names: '国有产权受让'
      }, {
        aut_status: '1',
        names: '矿权竞买'
      }, {
        aut_status: '1',
        names: '矿权转让方'
      }, {
        aut_status: '1',
        names: '土地竞买'
      }, {
        aut_status: '1',
        names: '土地转让方'
      }, {
        aut_status: '2',
        names: '采购单位'
      }, {
        aut_status: '2',
        names: '供应商'
      }]
    }
  },
  methods: {
    immediatelyBtn () {
      this.$confirm('确认要去认证吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$router.push({name: 'subject', params: {name: 'nameValue'}})
      }).catch(() => {
        return false
      })
    },
    // 查看详情
    Lookdetails () {
      this.$confirm('确认要去查看详情吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$router.push({name: 'authen-inform', params: {LookStatus: false}})
      }).catch(() => {
        return false
      })
    }
  },
  mounted () {}
}
</script>
<style lang="less">
  #autrtic{
    .authenbigbox{
      width: 100%;
      min-height: 50px;
      margin-top: 40px;
      box-sizing: border-box;
    }
    .authenbox{
      width: 100%;
      min-height: 10px;
      border: 1px solid #eeeeee;
      box-sizing: border-box;
      position: relative;
    }
    .grid-content{
      width: 81.33px;
      min-height: 10px;
    }
    .auttitlebox{
      height: 50px;
      line-height: 50px;
      text-align: center;
      font-size: 14px;
      color:#333333;
      font-weight: 700;
      background:#f5f7f8;
      border-right: 1px solid #eeeeee;
      border-bottom: 1px solid #eeeeee;
    }
    .borright{
      border-right: none;
    }
    .autconbotbox{
      font-size: 14px;
      color:#3f63f6;
      text-align: center;
    }
    .grid-contentneibox{
      height: 50px;
      text-align: left;
      line-height: 50px;
      color:#333333;
      font-size: 14px;
      padding: 0 8px;
      box-sizing: border-box;
      overflow: hidden;
      text-overflow:ellipsis;
      white-space: nowrap;
      border-bottom: 1px solid #eeeeee;
      cursor: pointer;
    }
    .grid-contentneibox:nth-last-child(1){
      border-bottom: none;
    }
    .grid-contentneibox:nth-last-child(2){
      border-bottom: none;
    }
    .statesbox_uncertified{
      display: inline-block;
      width: 6px;
      height: 6px;
      background:#999999;
      border-radius: 50%;
      vertical-align: middle;
      margin-right: 10px;
    }
    .statesbox_lan{
      display: inline-block;
      width: 6px;
      height: 6px;
      background:#3f63f6;
      border-radius: 50%;
      vertical-align: middle;
      margin-right: 10px;
    }
    .statesbox_lvbtn{
      display: inline-block;
      width: 6px;
      height: 6px;
      background:#43bf2b;
      border-radius: 50%;
      vertical-align: middle;
      margin-right: 10px;
    }
    .autfatherbigbox{
      border-right: 1px solid #eeeeee;
    }
    .rightqiyebox{
      border-left: 1px solid #eeeeee;
      position: relative;
    }
    .zhuangtaibox{
      width: 150px;
      height: 100%;
      position: absolute;
      left: 445px;
      font-size: 14px;
      color:#3f63f6;
      text-align: center;
      border-right:1px solid #eeeeee ;
      /*background: gold;*/
    }
    .autconbotbox_tijiao{
      display: inline-table;
      position: absolute;
      top: 0;
      left: 50px;
      bottom: 0;
      margin: auto;
      cursor: pointer;
    }
    .caozuobtn{
      width: 298px;
      height: 100%;
      position: absolute;
      right:0;
      font-size: 14px;
      color:#3f63f6;
      text-align: center;
    }
    .autconbotbox_topstatus{
      width: 149px;
      height: 50px;
      line-height: 50px;
      font-size: 14px;
      color: #333333;
      font-weight: 700;
    }
    .ljrztitlebox{
      width:298px;
      height: 50px;
      line-height: 50px;
      font-size: 14px;
      color: #333333;
      font-weight: 700;
    }
    .ljrzconbox{
      width: 100%;
      overflow: hidden;
      display: inline-table;
      position: absolute;
      top: 0;
      left: 0px;
      bottom: 0;
      margin: auto;
      cursor: pointer;
    }
    .grid-contentcao{
      cursor: pointer;
    }
    .biaotizztbox{
      width: 81.33px;
    }
    .lefttitlebox{
      width: 81.3px;
      height: 100%;
      position: absolute;
      left: -81.3px;
    }
    .lefttitlebox{
      width: 81.3px;
      height: 100%;
      position: absolute;
      left: -81.3px;
    }
    .lefttitlecon{
      display: inline-table;
      font-size: 14px;
      color:#3f63f6;
      width: 20%;
      position: absolute;
      top:0;
      bottom: 0;
      left: 0;
      right: 0;
      margin: auto;
    }
  }
</style>
