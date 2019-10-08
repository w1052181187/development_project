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
              <el-checkbox-group v-model="subjectForm.subjectype" @change="setUp">
                <div class="wdszfatherbox">
                  <div class="applicontenttitle">业务辅助</div>
                  <el-row class="subjectconbox">
                    <el-col :span="4">
                      <div class="wdszbbigbox">
                        <div class="wdsziconbox">
                          <img src="../../../static/images/dagl.svg" alt="">
                          <el-checkbox name="subjectype"  label="10001"  class="wdszxzbtn"></el-checkbox>
                        </div>
                        <div class="wdsztitlebox">档案管理</div>
                      </div>
                    </el-col>
                    <el-col :span="4">
                      <div class="wdszbbigbox">
                        <div class="wdsziconbox">
                          <img src="../../../static/images/htgl.svg" alt="">
                          <el-checkbox name="subjectype"  label="10002"  class="wdszxzbtn"></el-checkbox>
                        </div>
                        <div class="wdsztitlebox">业主管理</div>
                      </div>
                    </el-col>
                    <el-col :span="4">
                      <div class="wdszbbigbox">
                        <div class="wdsziconbox">
                          <img src="../../../static/images/jzxtp.svg" alt="">
                          <el-checkbox name="subjectype"  label="10003"  class="wdszxzbtn"></el-checkbox>
                        </div>
                        <div class="wdsztitlebox">专家管理</div>
                      </div>
                    </el-col>
                    <el-col :span="4">
                      <div class="wdszbbigbox">
                        <div class="wdsziconbox">
                          <img src="../../../static/images/xmsz.svg" alt="">
                          <el-checkbox name="subjectype"  label="10005"  class="wdszxzbtn"></el-checkbox>
                        </div>
                        <div class="wdsztitlebox">收支管理</div>
                      </div>
                    </el-col>
                  </el-row>
                </div>
                <div class="wdszfatherbox">
                  <div class="applicontenttitle">系统管理</div>
                  <el-row class="subjectconbox">
                    <el-col :span="4">
                      <div class="wdszbbigbox">
                        <div class="wdsziconbox">
                          <img src="../../../static/images/zygl.svg" alt="">
                          <el-checkbox name="subjectype"  label="10004" class="wdszxzbtn"></el-checkbox>
                        </div>
                        <div class="wdsztitlebox">资源设置</div>
                      </div>
                    </el-col>
                  </el-row>
                </div>
              </el-checkbox-group>
            </el-form-item>
          </el-form>
          <div class="caozuobtn">
            <el-button type="primary" @click="applicationAddbtn">确 定</el-button>
            <el-button type="info" @click="cancelBtn">取 消</el-button>
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
      subjectForm: {
        subjectype: []
      },
      checked: true
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
        console.log(parameter)
        application.applicationAdd(parameter).then((res) => {
          this.$router.push({path: '/index'})
        })
      }).catch(() => {
        return false
      })
    },
    cancelBtn () {
      this.$confirm('确认取消吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$router.push({path: '/index'})
      }).catch(() => {
        return false
      })
    }
  },
  mounted () {
    this.applicationList()
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
    }
    .wdszfatherbox{
      border: 1px solid #eeeeee;
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
  }
</style>
