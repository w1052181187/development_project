<template>
  <div id="homeenterprise">
    <div class="homepagecontent">
      <publicside></publicside>
      <div class="homepageconright">
        <!--面包屑-->
        <el-breadcrumb class="breadcrumbbox" separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/authentication' }">企业认证</el-breadcrumb-item>
          <el-breadcrumb-item>主体类型</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
        <!--内容开始-->
        <div class="penserviceconbox">
          <div class="penservicecontitle">主体类型（多选）</div>
          <div class="enterisebigbox">
            <el-row class="subjecttitlebox">
              <el-col :span="4"><div class="grid-content">建设工程</div></el-col>
              <el-col :span="4"><div class="grid-content">政府采购</div></el-col>
              <el-col :span="4"><div class="grid-content">产权交易</div></el-col>
              <el-col :span="4"><div class="grid-content">矿权交易</div></el-col>
              <el-col :span="4"><div class="grid-content">土地交易</div></el-col>
              <el-col :span="4"><div class="grid-content bordertright">其他交易</div></el-col>
            </el-row>
            <el-form :model="subjectForm" ref="subjectForm" class="demo-ruleForm">
              <el-form-item prop="subjectype">
                <el-checkbox-group v-model="subjectForm.subjectype">
                  <el-row class="subjectconbox">
                    <el-col :span="4">
                      <el-checkbox name="subjectype" v-for="constProject in constProjects" :key="constProject.objectId" :label="constProject.objectId">{{constProject.subjectName}}</el-checkbox>
                    </el-col>
                    <el-col :span="4">
                      <el-checkbox name="subjectype" v-for="government in governments" :key="government.objectId" :label="government.objectId">{{government.subjectName}}</el-checkbox>
                    </el-col>
                    <el-col :span="4">
                      <el-checkbox name="subjectype" v-for="equity in equitys" :key="equity.objectId" :label="equity.objectId">{{equity.subjectName}}</el-checkbox>
                    </el-col>
                    <el-col :span="4">
                      <el-checkbox name="subjectype" v-for="oreTrading in oreTradings" :key="oreTrading.objectId" :label="oreTrading.objectId">{{oreTrading.subjectName}}</el-checkbox>
                    </el-col>
                    <el-col :span="4">
                      <el-checkbox name="subjectype" v-for="land in landTransactions" :key="land.objectId" :label="land.objectId">{{land.subjectName}}</el-checkbox>
                    </el-col>
                    <el-col :span="4">
                      <el-checkbox name="subjectype" v-for="other in otherTransactions" :key="other.objectId" :label="other.objectId">{{other.subjectName}}</el-checkbox>
                    </el-col>
                  </el-row>
                </el-checkbox-group>
              </el-form-item>
            </el-form>
          </div>
          <div class="enterisebtn">
            <el-button type="primary" v-if="approveState" @click="nextEdit">下一步</el-button>
            <el-button type="primary" v-else @click="nextStep">下一步</el-button>
            <el-button type="info" @click="resetForm"> 取 消 </el-button>
          </div>
        </div>
        <!--内容开始-->
      </div>
    </div>
  </div>
</template>
<script>
import publicside from '../adminpublic/side.vue'
import {enterprise} from '../../api/index'
export default {
  components: {
    publicside
  },
  data () {
    return {
      subjectForm: {
        subjectype: []
      },
      constProjects: [],
      governments: [],
      equitys: [],
      oreTradings: [],
      landTransactions: [],
      otherTransactions: [],
      approveState: false
    }
  },
  methods: {
    enterpriseSubjectData () {
      let url = {
        isDelete: 0
      }
      enterprise.subjectListByType(url).then((item) => {
        this.constProjects = item.data.item.constProject
        this.governments = item.data.item.government
        this.equitys = item.data.item.equity
        this.oreTradings = item.data.item.oreTrading
        this.landTransactions = item.data.item.landTransaction
        this.otherTransactions = item.data.item.otherTransaction
      })
    },
    nextStep () {
      this.$confirm('确认进行下一步吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (this.subjectForm.subjectype.length == 0) {
          this.$message({
            type: 'warning',
            message: '请选择一个认证的主体'
          })
          return false
        }
        this.$router.push({path: '/information', query: {SelectFrom: this.subjectForm.subjectype.toString()}})
      }).catch(() => {
        return false
      })
    },
    nextEdit () {
      this.$confirm('确认进行下一步吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (this.subjectForm.subjectype.length == 0) {
          this.$message({
            type: 'warning',
            message: '请选择一个认证的主体'
          })
          return false
        }
        this.$router.push({path: '/informationEdit', query: {SelectFrom: this.subjectForm.subjectype.toString(), approveState: this.$route.query.approveState}})
      }).catch(() => {
        return false
      })
    },
    resetForm () {
      this.$router.push({path: '/authentication'})
    }
  },
  mounted () {
    if (this.$route.query.flangshow == true) {
      let val = this.$route.query.subjectForm.split(',')
      let arr = []
      val.map(ite => {
        arr.push(Number(ite))
      })
      this.subjectForm = {
        subjectype: arr
      }
    }
    this.approveState = this.$route.query.approveState
    this.enterpriseSubjectData()
  }
}
</script>
<style lang="less">
  #homeenterprise{
    .enterisebigbox{
      width: 100%;;
      /*background: red;*/
      border: 1px solid #eeeeee;
      border-bottom: none;
    }
    .grid-content{
      width: 100%;
      height: 50px;
      text-align: center;
      line-height: 50px;
      font-size: 14px;
      color:#333333;
      background:#f5f7f8;
      border-right: 1px solid #eeeeee;
      box-sizing: border-box;
    }
    .grid-contentcon{
      width: 100%;
      height: 50px;
      text-align: center;
      line-height: 50px;
      font-size: 14px;
      color:#333333;
      background:#f5f7f8;
      border-right: 1px solid #eeeeee;
      border-top: 1px solid #eeeeee;
      box-sizing: border-box;
    }
    .bordertright{
      border-right: none;
    }
    .enterisebtn{
      text-align: center;
      margin-top: 100px;
    }
    .el-button--primary {
      color: #fff;
      background-color: #3f63f6;
      border-color: #3f63f6;
      border-radius: 0;
      width: 80px;
      height: 40px;
    }
    .el-button--info {
      color: #666666;
      background-color: #ededed;
      border-color: #ededed;
      border-radius: 0;
      width: 80px;
      height: 40px;
    }
    .el-form-item {
      margin-bottom: 0px;
    }
    .el-checkbox.el-checkbox {
      display: inherit;
      margin-left: 0px;
      overflow: hidden;
      text-overflow:ellipsis;
      white-space: nowrap;
      border-bottom: 1px solid #eeeeee;
      border-right: 1px solid #eeeeee;
      padding: 0 10px;
      box-sizing: border-box;
    }
    .el-checkbox.el-checkbox:last-child {
      display: inherit;
      margin-left: 0px;
      overflow: hidden;
      text-overflow:ellipsis;
      white-space: nowrap;
    }
    .el-checkbox__label{
      vertical-align: middle;
    }
    .el-form-item__content {
      margin-left: 0 !important;
      width: 100%;
    }
  }
</style>
