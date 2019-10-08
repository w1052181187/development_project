<template>
  <div id="quaReview" class="content_bgibox">
    <el-row>
      <el-col :span="14">
        <!--评审-->
        <div class="setup_bigbox">
          <div class="proinfobox" style="margin-bottom: 25px">
            <span class="line_bigbox"></span>
            <span class="title_bigbox">磋商要点</span>
          </div>
          <el-form :model="ruleForm" ref="ruleForm" label-width="120px" class="demo-ruleForm">
            <el-form-item label="磋商要点：" prop="content">
              <div class="editor">
                <editor ref="ueditor" class="ueditor" :content="pointContent" :editread="editStatus"></editor>
                <div class="zhezhao_box"></div>
              </div>
            </el-form-item>
          </el-form>
          <!--提交按钮-->
          <div class="sub_btnbigbox">
            <el-button type="primary" class="sub_btn" @click="preservationBtn('ruleForm',1)" v-if="pointStatus === 0 && headmanStatus">提 交</el-button>
            <el-button type="primary" class="sub_btn" disabled v-if="pointStatus === 1 && headmanStatus">已提交</el-button>
          </div>
          <!--提交按钮-->
        </div>
        <!--评审-->
      </el-col>
    </el-row>
  </div>
</template>
<script>
import editor from '@/components/common/ueditor/ueditor.vue'
import { consultingPoints, consultingSummary } from '@/api'
import $ from 'jquery'
export default {
  components: {
    editor
  },
  data () {
    return {
      pointContent: '',
      ruleForm: {},
      editStatus: false, // 编辑状态
      pointStatus: '', // 按钮状态
      updateAddId: '',
      numberRounds: this.$route.params.num,
      headmanStatus: false // 是否是组长
    }
  },
  methods: {
    // 页面访问
    viewPageList () {
      consultingSummary.viewPage(this.$route.params.num).then((res) => {
      })
    },
    // 获取磋商要点
    preservationLists () {
      consultingPoints.consultingPointsList(this.$route.params.num).then((res) => {
        if (res.data.BidOpenNegotiatingPoints.pointStatus === null) {
          this.pointStatus = 0
        } else {
          this.ruleForm = res.data.BidOpenNegotiatingPoints
          this.updateAddId = res.data.BidOpenNegotiatingPoints.objectId
          if (res.data.BidOpenNegotiatingPoints.pointContent) {
            this.pointContent = res.data.BidOpenNegotiatingPoints.pointContent
            this.$refs.ueditor.setContent(this.pointContent)
          }
          let statusNego = res.data.BidOpenNegotiatingPoints.pointStatus
          if (statusNego === 0) {
            this.pointStatus = 0
            $('.edui-editor-toolbarbox').removeClass('edit_function')
            $('.zhezhao_box').removeClass('zhezhao_box_show')
          } else if (statusNego === 1) {
            this.pointStatus = 1
            $('.edui-editor-toolbarbox').addClass('edit_function')
            $('.zhezhao_box').addClass('zhezhao_box_show')
          }
        }
        this.editStatus = this.pointStatus === 1 && this.headmanStatus
      })
    },
    // 添加磋商要点
    preservationBtn (ruleForm, flag) {
      this.ruleForm.pointContent = this.$refs.ueditor.getContent() // 富文本内容获取
      this.ruleForm.pointStatus = flag
      this.ruleForm.pointNum = this.$route.params.num
      if (this.updateAddId) { // 修改
        consultingPoints.consultingPointsUpdate(this.ruleForm).then((res) => {
          this.preservationLists()
        })
      } else { // 新增
        consultingPoints.consultingPointsAdd(this.ruleForm).then((res) => {
          this.preservationLists()
        })
      }
    }
  },
  watch: {
    $route: function () {
      this.preservationLists()
      this.viewPageList()
    }
  },
  mounted () {
    this.$store.getters.authUser.roleIds.map((its) => {
      if (its === 2 || its === 9) {
        this.headmanStatus = true
      }
    })
    this.preservationLists()
    this.viewPageList()
  }
}
</script>
<style lang="less">
  #quaReview{
    margin-top: 20px;
    .setup_bigbox{
      box-shadow: 0 2px 0px 0px rgba(0,0,0,0.08) inset;
    }
    .unqualifiedbox{
      text-align: center;
    }
  }
</style>
