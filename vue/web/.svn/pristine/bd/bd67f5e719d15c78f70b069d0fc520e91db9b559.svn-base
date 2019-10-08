<template>
  <div id="rotate-add" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>信息管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/index/info-manage/bidstate' }">招标声明</el-breadcrumb-item>
        <el-breadcrumb-item>查看招标声明</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="returnboxbig">
        <template>
          <el-button @click="$router.go(-1)">返回</el-button>
        </template>
      </div>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <el-form :model="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="公告名称:">
          <span>{{ruleForm.title}}</span>
        </el-form-item>
        <el-form-item label="招标编号:">
          <span>{{ruleForm.tenderCode}}</span>
        </el-form-item>
        <el-form-item label="项目实施地:">
          <span>{{ruleForm.projectImplementationSite}}</span>
        </el-form-item>
        <el-form-item label="行业:">
          <span>{{industryStr}}</span>
        </el-form-item>
        <el-form-item label="资金来源:">
          <span v-if="ruleForm.sourceOfFunds === 1">自有资金</span>
          <span v-if="ruleForm.sourceOfFunds === 2">专项资金</span>
          <span v-if="ruleForm.sourceOfFunds === 3">吸收资金</span>
          <span v-if="ruleForm.sourceOfFunds === 4">其他</span>
        </el-form-item>
        <el-form-item label="公告内容:" class="form-ueditor">
          <ueditor ref="content"></ueditor>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import ueditor from '@/components/ueditor/ueditor'
import {industry} from '../../../assets/js/industry'
import {infoManageBidState} from '@/api'
export default {
  data () {
    return {
      ruleForm: {},
      industryList: industry,
      industryStr: ''
    }
  },
  components: {
    ueditor
  },
  methods: {
    detail () {
      infoManageBidState.detail(this.$route.params.objectId).then((res) => {
        this.industryList.map((item) => {
          if (item.value === res.data.bidInvitation.industryTypeFirst) {
            this.industryStr = item.label
          }
        })
        this.$refs.content.setContent(res.data.bidInvitation.content)
        this.ruleForm = res.data.bidInvitation
      })
    }
  },
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  #rotate-add {
    .contentbigbox .demo-ruleForm {
      .el-form-item__content {
        text-align: left;
      }
      .ueditor-wrap {
        padding: 0;
      }
      .btns {
        margin-top: 0;
      }
    }
    .form-ueditor {
      .el-form-item__content {
        line-height: 0;
      }
    }
  }
</style>
