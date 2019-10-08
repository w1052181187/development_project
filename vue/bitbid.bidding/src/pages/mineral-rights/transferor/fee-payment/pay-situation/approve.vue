<template>
  <div id="payapprovebox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>费用缴纳情况查询</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/transferor/fee-payment?roledsType=1' }">查看竞买保证金缴纳情况</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/transferor/fee-payment/pay-situation', query: {objectId: this.$route.query.sectionId, roledsType: 1}}">缴纳情况</el-breadcrumb-item>
        <el-breadcrumb-item>缴纳详情</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" :rules="rules" ref="sectionForm" label-width="100px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：" prop="sectionName">
                  <span>{{sectionForm.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：" prop="sectionNumber">
                  <span>{{sectionForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="公告名称：" prop="unifiedTransCode">
                  <span>{{annoNumber}}</span>
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
                  <span>{{sectionForm.bidderName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="费用类型：" prop="contacts">
                  <span>竞买保证金</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="缴纳金额（万元）：" prop="contactsNumber">
                  <span>{{bidBond}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="缴费状态：" prop="PayStatus">
                  <span v-if="Number(sectionForm.payStatus) === 0">未支付</span>
                  <span v-if="Number(sectionForm.payStatus) === 1">已支付</span>
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
                  <el-radio v-model="sectionForm.payStatus" label="1">已支付</el-radio>
                  <el-radio v-model="sectionForm.payStatus" label="0">未支付</el-radio>
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
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import { apply, prProject } from '@/api'
export default {
  data () {
    return {
      sectionForm: {},
      bidBond: '',
      annoNumber: '',
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
      payTypesvalue: '',
      qualiConfirmation: {},
      rules: {
        payMethod: [
          { required: true, message: '请选择缴费方式', trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  methods: {
    // 提交
    submitForm (sectionForm) {
      if (this.sectionForm.payStatus === '') {
        this.$message({
          type: 'warning',
          message: '请选择审批状态'
        })
      }
      this.$refs[sectionForm].validate((valid) => {
        if (valid) {
          apply.applyUpdate(this.sectionForm).then((res) => {
            this.$router.push({path: '/mr/transferor/fee-payment/pay-situation', query: {objectId: this.$route.query.sectionId, roledsType: 1}})
          })
        } else {
          return false
        }
      })
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/mr/transferor/fee-payment/pay-situation', query: {objectId: this.$route.query.sectionId, roledsType: 1}})
    },
    // 显示项目及报名信息详情
    detail () {
      prProject.queryOne(this.$route.query.sectionId).then((res) => {
        this.bidBond = res.data.sectionOneInfo.bidBond
        this.annoNumber = res.data.sectionOneInfo.announcement.annoNumber
      })
      apply.applyDetail(this.$route.query.objectId).then((res) => {
        this.sectionForm = res.data.Apply
        this.sectionForm.payStatus = res.data.Apply.payStatus.toString()
        this.sectionForm.sectionName = res.data.Apply.section.sectionName
        this.sectionForm.sectionNumber = res.data.Apply.section.sectionNumber
      })
    }
  },
  computed: {},
  created () {
    this.detail()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
</style>
