<template>
  <div class="cloudcontent"  id="cloud_journalSummaryDetail">
    <div class="main viewdetails collaborationDetail" style="margin-bottom: 0;" v-loading="overAllloading">
      <div class="tit">
        <span>{{submitForm.title}}</span>
        <i v-if="submitForm.status !== 0">提交人：{{submitForm.senderName}}</i>
        <i v-if="submitForm.status !== 0">提交日期：{{submitForm.sendDate}}</i>
      </div>
      <el-form class="submitForm" :model="submitForm" label-width="120px" ref="submitForm" v-model="allLoading">
        <el-row v-for="(item, index) in submitForm.item" :key="index">
          <el-col :span="item[0].type === 5 || item[0].type === 6 ? 24 : 12">
            <el-form-item :label="item[0].name + '：'">
              <span v-if="item[0].type === 1">{{item[0].value}}</span>
              <span v-if="item[0].type === 2">{{item[0].value}}</span>
              <el-radio-group v-model="item[0].value" v-if="item[0].type === 3">
                <el-radio v-for="raItem in item[0].optional" :label="raItem.value" :key="raItem.value" >{{raItem.label}}</el-radio>
              </el-radio-group>
              <el-checkbox-group v-model="item[0].value" v-if="item[0].type === 4">
                <el-checkbox v-for="raItem in item[0].optional" :label="raItem.value" :key="raItem.value" >{{raItem.label}}</el-checkbox>
              </el-checkbox-group>
              <el-date-picker
                v-if="item[0].type === 7"
                v-model="item[0].value"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
              <el-date-picker
                v-if="item[0].type === 8"
                v-model="item[0].value"
                type="datetime"
                placeholder="选择日期时间">
              </el-date-picker>
              <editor  v-if="item[0].type === 5" :ref="'ueditor' + index" class="ueditor" :editread="editread" :content="content"  @contentChange="handleContentChange"></editor>
              <file-download  v-if="item[0].type === 6" :fileArrays="item[0].fileInformations" :fileType="item[0].businessType"></file-download>
            </el-form-item>
          </el-col>
          <el-col :span="item[1].type === 5 || item[1].type === 6 ? 24 : 12" v-if="item.length === 2">
            <el-form-item :label="item[1].name + '：'">
              <span v-if="item[0].type === 1">{{item[1].value}}</span>
              <span v-if="item[0].type === 2">{{item[1].value}}</span>
              <el-radio-group v-model="item[1].value" v-if="item[1].type === 3">
                <el-radio v-for="raItem in item[1].optional" :label="raItem.value" :key="raItem.value" >{{raItem.label}}</el-radio>
              </el-radio-group>
              <el-checkbox-group v-model="item[1].value" v-if="item[1].type === 4">
                <el-checkbox v-for="raItem in item[1].optional" :label="raItem.value" :key="raItem.value" >{{raItem.label}}</el-checkbox>
              </el-checkbox-group>
              <el-date-picker
                v-if="item[1].type === 7"
                v-model="item[1].value"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
              <el-date-picker
                v-if="item[1].type === 8"
                v-model="item[1].value"
                type="datetime"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div class="main recordcontent" v-if="submitForm.status !== 0">
      <span>阅读记录：</span>
      <p class="read">已读：<span>{{readUserNames}}</span></p>
      <p class="unread">未读：<span>{{unReadUserNames}}</span></p>
    </div>
    <div class="main recordcontent">
      <span v-if="submitForm.status !== 0">评论({{total}})：</span>
      <el-form :model="reviewForm" ref="reviewForm" :rules="rulesList" label-width="100px" :validate-on-rule-change="true" v-if="submitForm.status !== 0">
        <el-row>
          <el-col :span="24">
            <el-form-item label="我要点评：" prop="content">
              <el-input type="textarea" v-model="reviewForm.content" class="reviewContent"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item style="text-align: left">
          <el-button type="primary" class="submitBtn" @click="handleClickEvent('reviewForm')" :loading="loading">提交</el-button>
        </el-form-item>
      </el-form>
      <div class="historyReview" v-if="submitForm.status !== 0">
        <span>历史评论：</span>
        <ul>
          <li v-for="item in this.historyReviewList" :key="item.index">
            <span>{{item.userName}}&nbsp;&nbsp;&nbsp;&nbsp;{{item.submitTime}}</span>
            <div>{{item.content}}</div>
          </li>
        </ul>
      </div>
      <!--分页-->
      <el-pagination
        v-if="submitForm.status !== 0"
        background
        layout="prev, pager, next"
        :total="total"
        :page-size='pageSize'
        :current-page.sync="currentPage"
        @current-change="handleCurrentChange"
        @next-click="handleCurrentNext">
      </el-pagination>
      <!--分页-->
      <div class="submit-radio">
        <el-button class="cancal" @click="handleCancle">返回</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import editor from '@/components/ueditor/ueditor.vue'
import fileDownload from '@/components/download/file-download'
import {journalSummary, comment} from '@/api/office'
export default {
  components: {
    editor,
    fileDownload
  },
  data () {
    return {
      allLoading: true,
      submitForm: {
        item: []
      },
      reviewForm: {},
      // 富文本
      editread: true,
      content: '',
      loading: false,
      historyReviewList: [],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 0, // 总条数
      pageSize: 5, // 每页展示条数
      overAllloading: true,
      readUserNames: '',
      unReadUserNames: '',
      rulesList: {
        content: {required: true, message: '请填写评论内容', trigger: ['blur', 'change']}
      }
    }
  },
  methods: {
    handleCancle () {
      this.$store.commit('delete_tabs', this.$route.fullPath)
      if (Object.is(this.$route.query.type, 1)) {
        this.$router.push({path: `/office/journal`, query: {active: this.$route.query.active}})
      } else if (Object.is(this.$route.query.type, 2)) {
        this.$router.push({path: `/office/planSummary`, query: {active: this.$route.query.active}})
      } else {
        this.$router.go(-1)
      }
    },
    // 上传附件
    uploadOtherSuccess (file) {
    },
    deleteSuccess (fileId) {
    },
    // 图文
    handleContentChange () {},
    // 分页
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getCommentList()
    },
    handleCurrentNext (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getCommentList()
    },
    handleClickEvent (form) {
      this.$refs[form].validate((vaild) => {
        if (vaild) {
          this.loading = true
          this.reviewForm.relatedCode = this.submitForm.code
          comment.update(this.reviewForm).then(res => {
            if (res.data.resCode === '0000') {
              this.loading = false
              this.reviewForm = {}
              this.$refs['reviewForm'].resetFields()
              this.getCommentList()
            }
          })
        } else {
          this.loading = false
          return false
        }
      })
    },
    getCommentList () {
      let query = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        relatedCode: this.submitForm.code
      }
      comment.getList(query).then(res => {
        let commentList = res.data.commentList
        if (commentList) {
          this.historyReviewList = commentList.list
          this.total = commentList.total
        }
      })
    },
    // 查询工作日志/计划总结单条数据
    getJournalSummary () {
      journalSummary.getById(this.$route.query.objectId).then(res => {
        let journalSummary = res.data.journalSummary
        if (journalSummary) {
          this.submitForm = journalSummary
          this.submitForm.item = JSON.parse(journalSummary.content)
          if (this.submitForm.item && this.submitForm.item.length > 0) {
            this.$nextTick(function () {
              this.submitForm.item.map((item, index) => {
                if (item[0].type === 2) {
                  item[0].optional.map(option => {
                    if (item[0].value === option.value) {
                      item[0].value = option.label
                    }
                  })
                }
                if (item.length > 1) {
                  if (item[1].type === 2) {
                    item[1].optional.map(option => {
                      if (item[1].value === option.value) {
                        item[1].value = option.label
                      }
                    })
                  }
                }
                if (item[0].type === 5) {
                  this.$refs['ueditor' + index][0].setContent(item[0].value)
                }
              })
            })
          }
          if (this.submitForm.readRecordList &&
            this.submitForm.readRecordList.length > 0) {
            this.submitForm.readRecordList.map(item => {
              if (item.isReaded === 0) {
                if (this.unReadUserNames) {
                  this.unReadUserNames += item.userName + '，'
                } else {
                  this.unReadUserNames = item.userName + '，'
                }
              } else {
                if (this.readUserNames) {
                  this.readUserNames += item.userName + '，'
                } else {
                  this.readUserNames = item.userName + '，'
                }
              }
            })
          }
          this.readUserNames = this.readUserNames.substring(0, this.readUserNames.length - 1)
          this.unReadUserNames = this.unReadUserNames.substring(0, this.unReadUserNames.length - 1)
          this.getCommentList()
          this.overAllloading = false
        }
      })
    }
  },
  mounted () {
    this.getJournalSummary()
  }
}
</script>
<style lang="less">
  #cloud_journalSummaryDetail{
    .submitForm{
      margin: 20px 0 5px 0;
    }
    .reviewContent{
      height: 100px;
    }
    .reviewContent .el-textarea__inner{
      height: 100px;
    }
    .historyReview{
      overflow: hidden;
      border-top: 1px solid #dcdfe6;
    }
    .historyReview > span{
      padding: 0 2%;
      width: 80px;
      text-align: right;
      line-height: 32px;
    }
    .historyReview ul{
      overflow: hidden;
    }
    .historyReview ul li{
      width: 96%;
      float: left;
      padding: 10px 2%;
      border-bottom: 1px solid #dcdfe6;
    }
    .historyReview ul li span{
      font-weight: bold;
      line-height: 32px;
    }
    .historyReview ul li div{
      line-height: 24px;
      margin-top: 10px;
    }
  }
</style>
