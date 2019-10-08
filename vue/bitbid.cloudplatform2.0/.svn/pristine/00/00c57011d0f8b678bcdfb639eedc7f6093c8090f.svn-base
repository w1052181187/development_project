<template>
  <div class="cloudcontent"  id="cloud_collaborationDetail">
    <div class="main viewdetails collaborationDetail">
      <div class="tit">
        <span>{{this.submitForm.title}}</span>
        <em v-if="submitForm.typeName">{{this.submitForm.typeName}}</em>
        <em class="blue" v-if="submitForm.isHighPriority === 1">高优先级</em>
        <i class="icon-forward" @click="handleClickEvent('转发')">转发</i>
        <i class="icon-reply" @click="handleClickEvent('回复')">回复</i>
      </div>
      <el-form :model="submitForm" ref="submitForm" label-width="80px" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="12">
            <el-form-item label="发送人：">
              <span>{{submitForm.senderName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="submitForm.status !== 0">
            <el-form-item label="发送时间：">
              <span>{{submitForm.sendDate}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="接收人：">
              <span>{{submitForm.addresseeNames}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="抄送人：">
              <span>{{submitForm.copierNames}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="附件：">
            <file-download :fileArrays="submitForm.fileInformationList" fileType="9"></file-download>
          </el-form-item>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="内容："  prop="content">
              <editor ref="ueditor" class="ueditor" :editread="editread" :content="content"></editor>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div class="main recordcontent">
      <span v-if="submitForm.status !== 0">阅读记录：</span>
      <p class="read" v-if="submitForm.status !== 0">已读：<span>{{readReceiverNames}}</span></p>
      <p class="unread" v-if="submitForm.status !== 0">未读：<span>{{unReadReceiverNames}}</span></p>
      <div class="submit-radio">
        <el-button class="cancal" @click="handleCancle">返回</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import editor from '@/components/ueditor/ueditor.vue'
import fileDownload from '@/components/download/file-download'
import {workCollaboration} from '@/api/office'
export default {
  components: {
    editor,
    fileDownload
  },
  data () {
    return {
      submitForm: {
        fileInformationList: []
      },
      // 富文本
      editread: false,
      content: '',
      readReceiverNames: '',
      unReadReceiverNames: ''
    }
  },
  methods: {
    handleClickEvent (type) {
      switch (type) {
        case '回复':
          this.$router.push({path: `/office/collaboration/reply`, query: {objectId: this.$route.query.objectId, operate: 'reply'}})
          break
        case '转发':
          this.$router.push({path: `/office/collaboration/forward`, query: {objectId: this.$route.query.objectId, operate: 'forward'}})
          break
        default:
          break
      }
    },
    handleCancle () {
      this.$store.commit('delete_tabs', this.$route.fullPath)
      if (this.$route.query.active) {
        this.$router.push({path: `/office/collaboration`, query: {active: this.$route.query.active}})
      } else {
        this.$router.go(-1)
      }
    },
    getWorkCollaboration () {
      workCollaboration.getById(this.$route.query.objectId).then(res => {
        this.submitForm = res.data.workCollaboration
        this.$refs.ueditor.setContent(this.submitForm.content)
        this.readReceiverNames = this.submitForm.readReceiverNameList.toString()
        this.unReadReceiverNames = this.submitForm.unReadReceiverNameList.toString()
      })
    }
  },
  mounted () {
    this.getWorkCollaboration()
  }
}
</script>
<style lang="less">
  #cloud_collaborationDetail{
  }
</style>
