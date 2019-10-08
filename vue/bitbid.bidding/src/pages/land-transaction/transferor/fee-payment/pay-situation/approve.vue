<template>
  <div id="payapprovebox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>费用缴纳情况查询</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/fee-payment?roledsType=1&showStatus=false' }">查看竞买保证金缴纳情况</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/fee-payment/pay-situation', query: {objectId: this.$route.query.listId, roledsType: 1, showStatus: false}}">缴纳情况</el-breadcrumb-item>
        <el-breadcrumb-item>缴纳审批</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" :rules="rules" ref="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：">
                  <span>{{sectionForm.projectName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：">
                  <span>{{sectionForm.projectNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="公告名称：">
                  <span>{{sectionForm.annoNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="地块编号：">
                  <span>{{sectionForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--费用缴纳信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">费用缴纳信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="竞买人名称：" prop="companyName">
                  <span>{{bidderName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="费用类型：" prop="contacts">
                  <span>竞买保证金</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="缴纳金额（万元）：" prop="contactsNumber">
                  <span>{{sectionForm.bidBond}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="缴费状态：" prop="PayStatus">
                  <span v-if="this.payStatus === 0">未支付</span>
                  <span v-if="this.payStatus === 1">已支付</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--费用缴纳信息-->
        <!--缴纳审批-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">缴纳审批</span>
          <div class="formmain">
            <el-row>
              <el-col :span="24">
                <el-form-item label="审批：" prop="naradiome" style="text-align: left" class="bitianicon">
                  <el-radio v-model="sectionForm.payStatus" :label="payFrom.value" v-for="(payFrom,index) in payDtata" :key="index">{{payFrom.label}}</el-radio>
                </el-form-item>
              </el-col>
              <el-col :span="24" v-if="Number(sectionForm.payStatus) !== 0">
                <el-form-item label="缴费方式：" prop="payMethod" class="payTypesbox">
                  <el-select v-model="sectionForm.payMethod" placeholder="请选择">
                    <el-option
                      v-for="item in payTypes"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="说明：">
                  <el-input type="textarea" v-model="sectionForm.payExplain"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--缴纳审批-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('sectionForm')">提交</el-button>
          <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import { apply, landProject } from '@/api'
export default {
  data () {
    return {
      sectionForm: {},
      payDtata: [
        {
          value: 0,
          label: '未支付'
        },
        {
          value: 1,
          label: '已支付'
        }
      ],
      payTypes: [
        {
          value: 0,
          label: '在线缴纳'
        },
        {
          value: 1,
          label: '保函'
        },
        {
          value: 2,
          label: '其他'
        },
        {
          value: 3,
          label: '支票'
        },
        {
          value: 4,
          label: '线下汇款'
        }
      ],
      rules: {
        payMethod: [
          { required: true, message: '请选择缴费方式', trigger: ['blur', 'change'] }
        ]
      },
      applyFrom: {}, // 提交时的数据
      payStatus: '', // 缴费状态
      bidderName: '' // 竞买人名称
    }
  },
  methods: {
    // 提交
    submitForm (sectionForm) {
      if (this.sectionForm.payStatus === undefined || this.sectionForm.payStatus === null) {
        this.$message({
          type: 'warning',
          message: '请选择审批状态'
        })
        return false
      }
      this.$refs[sectionForm].validate((valid) => {
        if (valid) {
          this.applyFrom.payStatus = this.sectionForm.payStatus
          this.applyFrom.payMethod = this.sectionForm.payMethod
          apply.applyUpdate(this.applyFrom).then((res) => {
            this.$router.push('/lt/transferor/fee-payment?roledsType=1&showStatus=false')
          })
        } else {
          return false
        }
      })
    },
    // 显示项目及报名信息详情
    detail () {
      apply.applyDetail(this.$route.params.objectId).then((res) => {
        this.bidderName = res.data.Apply.bidderName
        this.payStatus = res.data.Apply.payStatus
        this.applyFrom = res.data.Apply
      })
      landProject.detail(this.$route.query.listId).then((res) => {
        let a = {}
        let b = {}
        a = res.data.landInformation
        b.projectName = res.data.landInformation.landAnnoMation.projectName
        b.projectNumber = res.data.landInformation.landAnnoMation.projectNumber
        b.annoNumber = res.data.landInformation.landAnnoMation.annoNumber
        b.sectionNumber = res.data.landInformation.sectionNumber
        this.sectionForm = Object.assign(a, b)
      })
    }
  },
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
