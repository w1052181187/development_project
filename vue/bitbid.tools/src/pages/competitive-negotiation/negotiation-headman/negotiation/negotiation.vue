<template>
  <div id="quaReview" class="content_bgibox">
    <el-row>
      <el-col :span="14">
        <!--评审-->
        <div class="setup_bigbox">
          <div class="proinfobox" style="margin-bottom: 25px">
            <span class="line_bigbox"></span>
            <span class="title_bigbox">谈判</span>
          </div>
          <el-form :model="ruleForm" ref="ruleForm" label-width="160px" class="demo-ruleForm">
            <el-form-item label="谈判供应商：" prop="content">
              <el-select v-model="reviewSelId" placeholder="请选择" @change="reviewSelects">
                <el-option
                  v-for="item in reviewSelect"
                  :key="item.objectId"
                  :label="item.userName"
                  :value="item.objectId">
                </el-option>
              </el-select>
              <div class="next_bigbox" style="margin-left: 40px">
                <el-button type="primary" class="addbtn_box" @click="previousBtn">上一家</el-button>
                <el-button type="primary" class="addbtn_box" @click="nextBtn">下一家</el-button>
              </div>
            </el-form-item>
            <el-form-item label="谈判要点：" prop="content">
              <div class="editor">
                <editor ref="ueditor" class="ueditor" :content="content" :editread="true"></editor>
              </div>
            </el-form-item>
            <el-form-item label="报价(元)：" prop="content">
              <span>{{ruleForm.price}}</span>
            </el-form-item>
            <el-form-item label="是否进入下一阶段：" prop="content" class="bitianicon">
              <el-radio v-model="ruleForm.isPass" :label="fileRadio.label" v-for="(fileRadio, index) in fileRadioArry" :key="index" @change="radioChange" :disabled="isDisabled">{{fileRadio.name}}</el-radio>
            </el-form-item>
            <!--不合格理由填写-->
            <el-dialog title="不合格原因录入" :visible.sync="dialogFormVisible" width="30%" :before-close="cancelBtn">
              <el-form :model="unqualifiedForm" :rules="formUnqualifiedRules" ref="unqualifiedForm">
                <el-form-item label="不合格原因" prop="checkReason" :label-width="formLabelWidth">
                  <el-input type="textarea" v-model.trim="unqualifiedForm.checkReason"></el-input>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer unqualifiedbox">
                <el-button type="primary" class="ConfirmationBtn" @click="confirmationBtns('unqualifiedForm')">确 认</el-button>
              </div>
            </el-dialog>
            <!--不合格理由填写-->
            <!--不合格理由展示-->
            <el-form-item label="不合格理由：" v-if="unqualifiedStatus || ruleForm.isPass === 0">
              <span>{{ruleForm.logReason}}</span>
            </el-form-item>
            <!--不合格理由展示-->
          </el-form>
        </div>
        <!--评审-->
      </el-col>
      <el-col :span="10">
        <div class="pdf_bigbox"></div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import editor from '@/components/common/ueditor/ueditor.vue'
import { qualificationReview, negotiatingPoints } from '@/api'
export default {
  components: {
    editor
  },
  data () {
    return {
      pageSize: 1000, // 每页展示条数
      pageNo: 0,
      content: '',
      ruleForm: {},
      // 谈判供应商选择
      reviewSelect: [],
      // 谈判商默认展示数据objectId
      reviewSelId: '',
      reasonId: '',
      isDisabled: false,
      // 是否进入下一阶段
      fileRadioArry: [
        {
          label: 1,
          name: '是'
        },
        {
          label: 0,
          name: '否'
        }
      ],
      // 不合格理由弹框
      formLabelWidth: '120px',
      dialogFormVisible: false,
      unqualifiedForm: {}, // 不合格理由
      formUnqualifiedRules: {
        checkReason: [
          { required: true, message: '请输入不合格理由', trigger: ['blur', 'change'] },
          {min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: ['blur', 'change']}
        ]
      }, // 不合格填写验证
      unqualifiedStatus: false
    }
  },
  methods: {
    // 供应商数据
    negotiatingSuppliersSeleList () {
      qualificationReview.negotiateSele(this.$route.params.num).then((res) => {
        this.reviewSelect = res.data.BidOpenUserList
        this.reviewSelId = this.reviewSelect[0].objectId
        this.negotiatingLogLists() // 根据供应商id和谈判轮数获取数据
      })
    },
    // 上一家
    previousBtn () {
      let preArray = this.reviewSelect
      for (var i = 0; i < preArray.length; i++) {
        if (preArray[i].objectId === this.reviewSelId) {
          if (preArray[i - 1]) {
            this.reviewSelId = preArray[i - 1].objectId
            this.negotiatingLogLists()
          }
        }
      }
    },
    nextBtn () {
      let preArray = this.reviewSelect
      for (var i = 0; i < preArray.length; i++) {
        if (preArray[i].objectId === this.reviewSelId) {
          if (preArray[i + 1]) {
            this.reviewSelId = preArray[i + 1].objectId
            this.negotiatingLogLists()
          }
          return false
        }
      }
    },
    // 选择切换数据
    reviewSelects (objectId) {
      this.reviewSelId = objectId
      this.negotiatingLogLists()
    },
    // 选择后填写理由
    radioChange (val) {
      if (val === 0) {
        this.dialogFormVisible = true
      } else {
        let params = {
          objectId: this.reasonId,
          logReason: '',
          isPass: 1
        }
        negotiatingPoints.negotiatingLogUpdate(params).then((res) => {
          this.negotiatingLogLists()
        })
      }
    },
    // 查看数据
    negotiatingLogLists () {
      negotiatingPoints.negotiatingLogList(this.reviewSelId, this.$route.params.num).then((res) => {
        this.content = res.data.negotiatingPoint
        this.$refs.ueditor.setContent(this.content)
        this.ruleForm = res.data.BidOpenNegotiatingLog
        this.reasonId = res.data.BidOpenNegotiatingLog.objectId
        this.ruleForm.price = res.data.price
        if (res.data.BidOpenNegotiatingLog.logStatus !== 0) {
          this.isDisabled = true
        } else {
          this.isDisabled = false
        }
        if (res.data.BidOpenNegotiatingLog.isPass === 0) {
          this.unqualifiedStatus = true
        } else {
          this.unqualifiedStatus = false
        }
      })
    },
    // 确认
    confirmationBtns (unqualifiedForm) {
      this.$refs[unqualifiedForm].validate((valid) => {
        if (valid) {
          this.unqualifiedStatus = true // 提交后显示理由
          let params = {
            objectId: this.reasonId,
            logReason: this.unqualifiedForm.checkReason,
            isPass: 0
          }
          negotiatingPoints.negotiatingLogUpdate(params).then((res) => {
            this.dialogFormVisible = false
            this.negotiatingLogLists()
            this.$refs['unqualifiedForm'].resetFields()
          })
        } else {
          return false
        }
      })
    },
    // 点击关闭按钮
    cancelBtn () {
      this.$confirm(`关闭后将恢复到合格状态, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.ruleForm.isPass = 1
        this.dialogFormVisible = false
      }).catch(() => {
        return false
      })
    }
  },
  watch: {
    '$route': 'negotiatingSuppliersSeleList'
  },
  mounted () {
    this.negotiatingSuppliersSeleList()
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
