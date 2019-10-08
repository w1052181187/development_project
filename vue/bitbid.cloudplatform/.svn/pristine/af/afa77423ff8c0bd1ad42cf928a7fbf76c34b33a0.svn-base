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
                      <el-checkbox v-for="(builds,index) in buildcheck" :key="index" :label="builds.buildname" name="subjectype"></el-checkbox>
                    </el-col>
                    <el-col :span="4">
                      <el-checkbox v-for="(governments,index) in government" :key="index" :label="governments.governmentname" name="subjectype"></el-checkbox>
                    </el-col>
                    <el-col :span="4">
                      <el-checkbox v-for="(propertys,index) in property" :key="index" :label="propertys.propertyname" name="subjectype"></el-checkbox>
                    </el-col>
                    <el-col :span="4">
                      <el-checkbox v-for="(minerals,index) in mineral" :key="index" :label="minerals.mineralname" name="subjectype"></el-checkbox>
                    </el-col>
                    <el-col :span="4">
                      <el-checkbox v-for="(lands,index) in land" :key="index" :label="lands.landname" name="subjectype"></el-checkbox>
                    </el-col>
                    <el-col :span="4">
                      <el-checkbox v-for="(others,index) in other" :key="index" :label="others.othername" name="subjectype"></el-checkbox>
                    </el-col>
                  </el-row>
                </el-checkbox-group>
              </el-form-item>
            </el-form>
          </div>
          <div class="enterisebtn">
            <el-button type="primary" @click="nextStep">下一步</el-button>
            <el-button type="info" @click="resetForm('subjectForm')"> 取 消 </el-button>
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
      subjectForm: {
        subjectype: []
      },
      buildcheck: [{
        buildname: '招标人'
      }, {
        buildname: '招标代理'
      }, {
        buildname: '投标人'
      }],
      government: [{
        governmentname: '采购单位'
      }, {
        governmentname: '采购代理'
      }, {
        governmentname: '供应商'
      }],
      property: [{
        propertyname: '国有产权转让'
      }, {
        propertyname: '国有产权受让'
      }],
      mineral: [{
        mineralname: '矿权竞买'
      }, {
        mineralname: '矿权转让方'
      }],
      land: [{
        landname: '土地竞买'
      }, {
        landname: '土地转让方'
      }],
      other: [{
        othername: '其他采购单位'
      }, {
        othername: '其他供应商'
      }]
    }
  },
  methods: {
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
        this.$router.push({name: 'information', params: {SelectFrom: this.subjectForm}})
      }).catch(() => {
        return false
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    }
  },
  mounted () {
    if (this.$route.params.flangshow == true) {
      this.subjectForm = this.$route.params.subjectForm
    }
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
  }
</style>
