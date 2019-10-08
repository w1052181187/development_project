<template>
  <div id="marginappaddbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>标后业务</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/open-criticize-mark/bid-report?roledsType=1&showStatus=false' }">提交评标报告及中标候选人</el-breadcrumb-item>
        <el-breadcrumb-item>提交报告</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="refundedSecurityForm" label-width="160px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="12">
                <el-form-item label="项目名称：">
                  <span>{{refundedSecurityForm.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="项目编号：">
                  <span>{{refundedSecurityForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="地块编号：">
                  <span>{{refundedSecurityForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="公告名称：">
                  <span>{{refundedSecurityForm.annoNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--报告信息-->
        <div class="proinfobox">
          <span class="title">报告信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="24">
                <el-form-item label="评标报告：">
                  <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></commonFileUpload>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="中标候选人名单：" class="add_table">
                  <el-button type="primary" style="margin-bottom: 25px" @click="addTable">增加</el-button>
                  <el-table :data="bidData" border header-cell-class-name="tabletitles">
                    <el-table-column prop="winningName" label="中标候选人">
                      <template  slot-scope="scope">
                        <el-input v-model="bidData[scope.$index].winningName"></el-input>
                      </template>
                    </el-table-column>
                    <el-table-column prop="winningPice" label="中标价格（元）">
                      <template  slot-scope="scope">
                        <el-input
                          v-model="bidData[scope.$index].winningPice"
                          @change="handlePrice(bidData[scope.$index].winningPice)"></el-input>
                      </template>
                    </el-table-column>
                    <el-table-column prop="winningRanking" label="中标候选人排名">
                      <template  slot-scope="scope">
                        <el-input
                          v-model="bidData[scope.$index].winningRanking"
                          @change="handlePrice(bidData[scope.$index].winningRanking)"></el-input>
                      </template>
                    </el-table-column>
                    <el-table-column label="操作" width="150">
                      <template  slot-scope="scope">
                        <el-button @click="deleteRow(scope.$index)">删除</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--报告信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm" :loading="subStatus">提交</el-button>
          <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
import {sumType, Numbertype} from '@/assets/js/validate'
export default {
  components: {
    commonFileUpload
  },
  data () {
    return {
      refundedSecurityForm: {},
      bidData: [],
      // 业务类型
      fileType: 1,
      // 文件上传保存的数组
      fileArry: [],
      subStatus: false,
      oldFileName: ''
    }
  },
  methods: {
    // 将子组件获取到的数据传给父组件
    upLoadFile (file) {
      this.fileArry = file
    },
    list () {},
    handlePrice (price) {
      if (!Numbertype(price)) {
        this.$message({
          type: 'warning',
          message: '格式不正确'
        })
      } else if (!sumType(price)) {
        this.$message({
          type: 'warning',
          message: '不能超过两位小数点'
        })
      } else if (price.length > 14) {
        this.$message({
          type: 'warning',
          message: '长度不能超过14'
        })
      }
    },
    submitForm () {
      this.subStatus = true
    },
    addTable () {
      this.bidData.push({})
    },
    deleteRow (index) {
      this.bidData.splice(index, 1)
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
  .add_table .el-form-item__content{
    width: 70%;
  }
</style>
