<template>
  <div id="rotate-detail" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>首页管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/index/home-manage/dynamic-info' }">动态信息</el-breadcrumb-item>
        <el-breadcrumb-item>查看动态</el-breadcrumb-item>
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
          <table class="detailtable">
            <tr>
              <td>标题：</td>
              <td colspan="2">
                <span>{{ruleForm.title}}</span>
              </td>
              <td>关键字：</td>
              <td colspan="2">
                <span>{{ruleForm.keywords}}</span>
              </td>
            </tr>
            <tr>
              <td>状态：</td>
              <td  colspan="5">
                <span v-if="ruleForm.isEnabled === 1">已启用</span>
                <span v-if="ruleForm.isEnabled === 0">已禁用</span>
              </td>
            </tr>
            <tr>
              <td>地区：</td>
              <td colspan="2">
                <span>{{regionStr}}</span>
              </td>
              <td>行业：</td>
              <td colspan="2">
                <span>{{industryStr}}</span>
              </td>
            </tr>
            <tr v-if="ruleForm.fileInformations.length">
              <td>附件：</td>
              <td colspan="5">
                <file-download :fileArrays="ruleForm.fileInformations" fileType="9"></file-download>
              </td>
            </tr>
            <tr>
              <td>内容：</td>
              <td colspan="5">
                <editor ref="ueditor" class="ueditor" :editread="true"></editor>
              </td>
            </tr>
          </table>
        </el-form>
      </div>
  </div>
</template>
<script>
import fileDownload from '@/components/file-download'
import editor from '@/components/ueditor/ueditor.vue'
import * as industry from '../../../assets/js/industry'
import * as region from '../../../assets/js/nmRegion'
import { dynamicNotice } from '@/api'
export default {
  data () {
    return {
      // 行业
      industryStr: '',
      // 地区
      regionStr: '',
      industryList: industry.industry,
      regionList: region.nmRegion,
      ruleForm: {
        title: '',
        keywords: '',
        industryTypeFirst: '',
        cityId: '',
        fileInformations: []
      }
    }
  },
  components: {
    fileDownload,
    editor
  },
  methods: {
    // 数据
    detail () {
      dynamicNotice.detail(this.$route.params.objectId).then((res) => {
        this.ruleForm = res.data.dynamicNotice
        this.industryList.map((item) => {
          if (item.value === this.ruleForm.industryTypeFirst) {
            this.industryStr = item.label
          }
        })
        this.regionList.map((item) => {
          if (item.value === this.ruleForm.cityId) {
            this.regionStr = item.label
          }
        })
        this.$refs.ueditor.setContent(this.ruleForm.content)
      })
    }
  },
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  #rotate-detail {
    .contentbigbox .demo-ruleForm {
      .el-form-item__content {
        text-align: left;
      }
    }
    .detailtable>tr>td {
      width: inherit;
    }
    .detailtable>tr>td:nth-child(odd) {
      width: 8%;
      min-width: inherit;
      overflow: hidden;
      background: #f5f7f8;
      text-align: right;
      color: #999999;
    }
  }
</style>
