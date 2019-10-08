<template>
  <div id="addbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让邀请</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/remise-invite/send-invite?roledsType=1&showStatus=false' }">发出出让邀请</el-breadcrumb-item>
        <el-breadcrumb-item>查看出让邀请书</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <router-link tag='span' to="/lt/transferor/remise-invite/send-invite?roledsType=1&showStatus=false">返回</router-link>
      </div>
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：" prop="sectionNumber">
                  <span>{{sectionForm.projectName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：" prop="unifiedTransCode">
                  <span>{{sectionForm.projectNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让文件获取方法：" prop="investProjectCode">
                  <span>{{sectionForm.fileObtainStartMethod}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--相关地块信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">相关地块信息</span>
          <div class="formmian">
            <div v-for="(relevantMassif,index) in sectionForm.landInformations" :key="index">
              <el-row>
                <span style="margin-left: 80px;margin-bottom: 22px;color: #000;font-weight: 800">块地{{index + 1}}</span>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="地块编号：" prop="sectionNumber">
                    <span>{{relevantMassif.sectionNumber}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="估价报告备案号：" prop="valuationReport">
                    <span>{{relevantMassif.valuationReport}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="竞买保证金(万元)：" prop="bidBond">
                    <span>{{relevantMassif.bidBond}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="是否存在底价：" prop="isFloorPrice">
                    <span v-if="relevantMassif.isFloorPrice === 1">是</span>
                    <span v-if="relevantMassif.isFloorPrice === 0">否</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="土地用途：" prop="landUse">
                    <span>{{relevantMassif.landUse}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="地块估算价(元)：" prop="conputedPrice">
                    <span>{{relevantMassif.conputedPrice}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item label="选择受让人：">
                    <el-table
                      :data="relevantMassif.enterprises"
                      border>
                      <el-table-column
                        property="enterpriseName"
                        label="受让人名称">
                      </el-table-column>
                      <el-table-column
                        property="contactCellphone"
                        label="手机">
                      </el-table-column>
                      <el-table-column
                        property="contactCellemail"
                        label="邮箱">
                      </el-table-column>
                    </el-table>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item label="受让人资格条件：">
                    <span>{{relevantMassif.prerequisites}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
        <!--相关地块信息-->
        <!--邀请书内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">邀请书内容</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="邀请书名称：" prop="annoNumber">
                  <span>{{sectionForm.annoNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="公告类型：" prop="contacts">
                  <span v-if="sectionForm.type === 1">拍卖出让公告</span>
                  <span v-if="sectionForm.type === 2">挂牌出让公告</span>
                  <span v-if="sectionForm.type === 3">资格后审出让公告</span>
                  <span v-if="sectionForm.type === 4">资格预审出让公告</span>
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
              <el-col :span="24">
                <el-form-item label="附件：" class="filetypebox">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--邀请书内容-->
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import { landAnno } from '@/api'
export default {
  components: {
    commonFileDownload,
    editor
  },
  data () {
    return {
      content: '', // 富文本内容
      sectionForm: {},
      // 文件内容
      fileObjecit: {
        fileName: '测试数据'
      }
    }
  },
  methods: {
    landAnnoInfo () {
      landAnno.detail(this.$route.params.objectId).then((res) => {
        this.sectionForm = res.data.landAnno
        this.content = res.data.landAnno.content
        this.$refs.ueditor.setContent(this.content) // 设置富文本内容
      })
    }
  },
  mounted () {
    this.$refs.ueditor.setContent(this.content) // 设置富文本内容
    this.landAnnoInfo()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
