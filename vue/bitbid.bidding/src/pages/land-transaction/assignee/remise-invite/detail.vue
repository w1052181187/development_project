<template>
  <div id="land_ann_lookdetailbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让邀请</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/remise-invite/index?roledsType=2' }">查看出让邀请</el-breadcrumb-item>
        <el-breadcrumb-item>查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：">
                  <span>{{sectionForm.landAnnoMation.projectName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：">
                  <span>{{sectionForm.landAnnoMation.projectNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="地块编号：">
                  <span>{{sectionForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="出让文件获取时间：">
                  <span>{{sectionForm.transDocStartTime}}</span> 至 <span>{{sectionForm.transDocEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="递交申请文件截止时间：">
                  <span>{{sectionForm.tenderDocEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="递交响应文件方法：">
                  <span>{{sectionForm.subFileMethod}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="答疑澄清时间：">
                  <span>{{sectionForm.ansQueStartTime}}</span> 至 <span>{{sectionForm.ansQueEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="投标有效期（天）：">
                  <span>{{sectionForm.validityOfBids}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="评标办法：">
                  <span v-if="sectionForm.bidEvaMethod === 0">综合评估法</span>
                  <span v-if="sectionForm.bidEvaMethod === 1">经评审最高价中标法</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="开标时间：">
                  <span>{{sectionForm.bidOpenTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="开标地点：">
                  <span>{{sectionForm.bidOpenPlace}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="开标方式：">
                  <span v-if="sectionForm.bidOpenWay === 0">在线开标</span>
                  <span v-if="sectionForm.bidOpenWay === 1">线下开标</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="评标方式：">
                  <span v-if="sectionForm.bidEvaWay === 0">在线评标</span>
                  <span v-if="sectionForm.bidEvaWay === 1">线下评标</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--地块信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">地块信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="地块编号：">
                  <span>{{sectionForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="估价报告备案号：">
                  <span>{{sectionForm.valuationReport}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="竞买保证金(万元)：">
                  <span>{{sectionForm.bidBond}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="是否存在底价：">
                  <span v-if="sectionForm.isFloorPrice === 1">是</span>
                  <span v-if="sectionForm.isFloorPrice === 0">否</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="土地用途：">
                  <span>{{sectionForm.landUse}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="地块估算价(元)：">
                  <span>{{sectionForm.conputedPrice}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="受让人资格条件：">
                  <span>{{sectionForm.prerequisites}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--地块信息-->
        <!--邀请书内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">公告内容</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="邀请书名称：">
                  <span>{{sectionForm.landAnnoMation.annoNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="受让人邀请确认截止时间：">
                  <span>{{sectionForm.invConEndTime}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="邀请书内容：">
                  <div class="editor">
                    <editor ref="ueditor" class="ueditor" :editread="true" :content="content"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24" class="filebigbox">
                <el-form-item label="附件：">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--邀请书内容-->
        <!--受让人信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #0206fd">受让人信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="联系人：">
                  <span>{{sectionForm.contacts}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="联系电话：">
                  <span>{{sectionForm.contactsNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="电子邮箱：">
                  <span>{{sectionForm.email}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--受让人信息-->
        <!--确认信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #b94aff">确认信息</span>
          <el-row>
            <el-col>
              <el-form-item label="确认状态：">
                <template>
                  <span v-if="sectionForm.isConfirm === 1">是</span>
                  <span v-if="sectionForm.isConfirm === 0">否</span>
                </template>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="说明：">
                <span>{{sectionForm.explains}}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <!--确认信息-->
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import {landProject} from '@/api'
export default {
  components: {
    commonFileDownload,
    editor
  },
  data () {
    return {
      sectionForm: {},
      content: '', // 内容
      // 文件内容
      fileObjecit: {
        fileName: '测试数据'
      },
      radioAcceptResults: [
        {
          label: 0,
          name: '确认'
        },
        {
          label: 1,
          name: '拒绝'
        }
      ]
    }
  },
  methods: {
    detail () {
      landProject.detail(this.$route.params.objectId).then(res => {
        this.sectionForm = res.data.landInformation
        console.log(this.sectionForm)
        let content = res.data.landInformation.landAnnoMation.content
        this.content = content
        this.$refs.ueditor.setContent(this.content) // 设置富文本内容
      })
    }
  },
  mounted () {
    this.detail()
    this.$refs.ueditor.setContent(this.content) // 设置富文本内容
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/land-register/common.css';
</style>
