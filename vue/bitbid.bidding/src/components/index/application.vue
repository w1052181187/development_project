<template>
  <div id="indexapplic">
    <div class="applic_bigbox">
      <div class="applic_titlebigbox">
        <!--面包屑-->
        <el-breadcrumb class="breadcrumbbox" separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>设置成我的应用</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
      </div>
      <div class="applicbigbox">
        <div class="applicontentbox">
          <el-form :model="subjectForm" ref="subjectForm" class="demo-ruleForm" style="margin-top: 20px;padding-bottom: 15px">
            <el-form-item prop="subjectype">
              <el-tabs v-model="activeName">
                <el-tab-pane label="已开通" name="first">
                  <div v-if="openStatus" style="text-align: center">暂无开通应用</div>
                  <el-checkbox-group v-model="subjectForm.subjectype" @change="setUp">
                    <div class="wdszfatherbox">
                      <el-row class="subjectconbox">
                        <el-col :span="4" v-if="$store.getters.permissions.includes('/archives')">
                          <div class="wdszbbigbox">
                            <div class="wdsziconbox">
                              <img src="../../../static/images/bibi/dagl_sz.png" alt="" style="height: auto">
                              <el-checkbox name="subjectype"  label="10001"  class="wdszxzbtn"></el-checkbox>
                            </div>
                            <div class="wdsztitlebox">档案管理</div>
                          </div>
                        </el-col>
                        <el-col :span="4" v-if="$store.getters.permissions.includes('/owners')">
                          <div class="wdszbbigbox">
                            <div class="wdsziconbox">
                              <img src="../../../static/images/bibi/yzgl_sz.png" alt="" style="height: auto">
                              <el-checkbox name="subjectype"  label="10002"  class="wdszxzbtn"></el-checkbox>
                            </div>
                            <div class="wdsztitlebox">业主管理</div>
                          </div>
                        </el-col>
                        <el-col :span="4" v-if="$store.getters.permissions.includes('/experts')">
                          <div class="wdszbbigbox">
                            <div class="wdsziconbox">
                              <img src="../../../static/images/bibi/zjgl_sz.png" alt="" style="height: auto">
                              <el-checkbox name="subjectype"  label="10003"  class="wdszxzbtn"></el-checkbox>
                            </div>
                            <div class="wdsztitlebox">专家管理</div>
                          </div>
                        </el-col>
                        <el-col :span="4" v-if="$store.getters.permissions.includes('/projectpayments')">
                          <div class="wdszbbigbox">
                            <div class="wdsziconbox">
                              <img src="../../../static/images/bibi/szgl_sz.png" alt="" style="height: auto">
                              <el-checkbox name="subjectype"  label="10005"  class="wdszxzbtn"></el-checkbox>
                            </div>
                            <div class="wdsztitlebox">收支管理</div>
                          </div>
                        </el-col>
                        <el-col :span="4" v-if="$store.getters.permissions.includes('/owner-set')">
                          <div class="wdszbbigbox">
                            <div class="wdsziconbox">
                              <img src="../../../static/images/bibi/zygl_sz.png" alt="" style="height: auto">
                              <el-checkbox name="subjectype"  label="10004" class="wdszxzbtn"></el-checkbox>
                            </div>
                            <div class="wdsztitlebox">资源分类</div>
                          </div>
                        </el-col>
                      </el-row>
                    </div>
                  </el-checkbox-group>
                  <div class="caozuobtn" v-if="!openStatus">
                    <el-button type="primary" @click="applicationAddbtn">确 定</el-button>
                    <el-button type="info" @click="cancelBtn">取 消</el-button>
                  </div>
                </el-tab-pane>
                <el-tab-pane label="全部应用" name="second">
                  <div>
                    <div class="newuserbigbox">
                      <div class="newywfztitlebox">档案管理</div>
                      <div class="newywfzimgbigbox">
                        <div class="newywfzimgbox">
                          <img src="../../../static/images/bibi/dagl_l.png" alt="" style="">
                        </div>
                      </div>
                      <div class="UnderstandBtn" @click="arcUnderstandBtn">
                        <span>了解一下</span>
                      </div>
                    </div>
                    <div class="newuserbigbox">
                      <div class="newywfztitlebox">业主管理</div>
                      <div class="newywfzimgbigbox">
                        <div class="newywfzimgbox">
                          <img src="../../../static/images/bibi/yzgl_l.png" alt="">
                        </div>
                      </div>
                      <div class="UnderstandBtn" @click="ownUnderstandBtn">
                        <span>了解一下</span>
                      </div>
                    </div>
                    <div class="newuserbigbox">
                      <div class="newywfztitlebox">专家管理</div>
                      <div class="newywfzimgbigbox">
                        <div class="newywfzimgbox">
                          <img src="../../../static/images/bibi/zjgl_l.png" alt="">
                        </div>
                      </div>
                      <div class="UnderstandBtn" @click="expUnderstandBtn">
                        <span>了解一下</span>
                      </div>
                    </div>
                    <div class="newuserbigbox">
                      <div class="newywfztitlebox">收支管理</div>
                      <div class="newywfzimgbigbox">
                        <div class="newywfzimgbox">
                          <img src="../../../static/images/bibi/szgl_l.png" alt="">
                        </div>
                      </div>
                      <div class="UnderstandBtn" @click="incUnderstandBtn">
                        <span>了解一下</span>
                      </div>
                    </div>
                    <div class="newuserbigbox">
                      <div class="newywfztitlebox">资源分类</div>
                      <div class="newywfzimgbigbox">
                        <div class="newywfzimgbox">
                          <img src="../../../static/images/bibi/zygl_l.png" alt="">
                        </div>
                      </div>
                      <div class="UnderstandBtn" @click="resUnderstandBtn">
                        <span>了解一下</span>
                      </div>
                    </div>
                  </div>
                </el-tab-pane>
              </el-tabs>
            </el-form-item>
          </el-form>
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
      subjectForm: {
        subjectype: []
      },
      checked: true,
      activeName: 'first',
      openStatus: false
    }
  },
  methods: {
    applicationList () {
      application.applicationList(this.$store.getters.authUser.userId).then((res) => {
        res.data.managementList.map((ite) => {
          if (ite.isChecked === 1) {
            this.subjectForm.subjectype.push(ite.objectId.toString())
          }
        })
      })
    },
    setUp (val) {
      this.subjectForm.subjectype = val
    },
    applicationAddbtn () {
      this.$confirm('确认提交吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'success'
      }).then(() => {
        if (this.subjectForm.subjectype.length == 0) {
          this.$message({
            message: '请选择一个设置的功能',
            type: 'warning'
          })
          return false
        }
        let parameter = {
          userId: this.$store.getters.authUser.userId,
          permissionIds: this.subjectForm.subjectype
        }
        application.applicationAdd(parameter).then((res) => {
          this.$router.push({path: '/index'})
        })
      }).catch(() => {
        return false
      })
    },
    cancelBtn () {
      this.$router.push({path: '/index'})
    },
    // 了解一下
    arcUnderstandBtn () {
      this.$router.push({path: '/arc-understand'})
    },
    ownUnderstandBtn () {
      this.$router.push({path: '/own-understand'})
    },
    expUnderstandBtn () {
      this.$router.push({path: '/exp-understand'})
    },
    resUnderstandBtn () {
      this.$router.push({path: '/res-understand'})
    },
    incUnderstandBtn () {
      this.$router.push({path: '/inc-understand'})
    }
  },
  mounted () {
    this.applicationList()
    if (this.$store.getters.permissions.length === 1) {
      this.openStatus = true
    }
  }
}
</script>
<style lang="less">
  #indexapplic{
    .applic_bigbox{
      width: 1220px;
      min-height: 50px;
      background:#fff;
      margin: 0 auto;
      margin-top: 20px;
      padding-bottom: 50px;
    }
    .applic_titlebigbox{
      width: 100%;
      height: 60px;
      border-bottom: 1px solid #eeeeee;
      padding-top:25px;
    }
    .applicbigbox{
      width: 100%;
      padding: 0 20px;
      background: #fff;
      box-sizing: border-box;
    }
    .applicontentbox{
      width: 100%;
      min-height: 50px;
      margin-top: 15px;
    }
    .applicontenttitle{
      padding: 20px;
      box-sizing: border-box;
      color:#111111;
      font-size: 18px;
      font-weight: bold;
    }
    .wdszbbigbox{
      width: 70%;
      min-height: 50px;
      margin: 0 auto;
      padding:25px 0;
    }
    .wdsziconbox{
      width: 90px;
      height: 90px;
      margin: 0 auto;
      position: relative;
    }
    .wdsziconbox>img{
      width: 100%;
      border-radius: 50%;
      box-sizing: border-box;
      vertical-align: top;
    }
    .wdsztitlebox{
      font-size: 14px;
      color:#111111;
      width: 100%;
      text-align: center;
      margin-top: 15px;
    }
    .wdszxzbtn{
      position: absolute;
      width: 20px;
      height: 20px;
      right: 0;
      bottom: 0;
    }
    .el-form-item__content {
      line-height: 20px;
      position: relative;
      font-size: 14px;
      margin-left: 0!important;
      width: 100%;
    }
    .wdszfatherbox{
      background:#f7f7fa;
      margin-bottom: 20px;
    }
    .el-checkbox__inner {
      width: 20px;
      height: 20px;
    }
    .el-checkbox__inner::after {
      left: 7px;
      position: absolute;
      top: 4px;
    }
    .el-checkbox__label{
      display: none;
    }
    .caozuobtn{
      text-align: center;
    }
    .el-button {
      padding: 12px 35px;
      border-radius: 0px;
    }
    .el-button--primary {
      color: #fff;
      background-color: #3f63f6;
      border-color: #3f63f6;
    }
    .el-button--info {
      color: #666666;
      background-color: #ededed;
      border-color: #ededed;
    }
    .el-checkbox__label{
      display: none!important;
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
      overflow: hidden;
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
    .el-tabs__nav-wrap::after {
      content: "";
      position: absolute;
      left: 0;
      bottom: 0;
      width: 100%;
      height: 2px;
      background-color:transparent;
      z-index: 1;
    }
    .el-tabs__active-bar {
      display: none;
    }
    .el-tabs__item{
      width: 105px;
      height: 40px;
      background:#ededed;
      color:#666;
      margin-right: 20px;
      text-align: center;
      padding: 0;
    }
    .el-tabs__item.is-active {
      color: #fff;
      background: #3f63f6;
    }
  }
</style>
