<template>
  <div>
    <div class="file-type-name">
      <span>定标</span>
    </div>
    <el-form class="viewdetails" :model="item.archivesProjectExpands" ref="form" :rules="rules">
      <el-row>
        <el-col :span="12">
          <el-form-item label="中标人：">
            <span>{{item.archivesProjectExpands ? item.archivesProjectExpands.winningBidder : ''}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="中标金额：">
            <span>{{item.archivesProjectExpands ? item.archivesProjectExpands.winningAmount : ''}}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="代理服务费：">
            <span>{{item.archivesProjectExpands ? item.archivesProjectExpands.agencyServiceFee : ''}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="候选人公示地址：">
            <span>{{item.archivesProjectExpands ? item.archivesProjectExpands.candidatePublicAddr : ''}}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="中标公示地址：">
            <span>{{item.archivesProjectExpands ? item.archivesProjectExpands.addrWinningBid : ''}}</span>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div class="file-type-add" @click="handleEdit" v-if="canEdit">
      <span>编辑</span>
    </div>
    <fileUpload class="file-type-enclosure" :canEdit="canEdit"
                :file-list="item.refArchivesFileInformationList"
                :object-id="item.objectId" :file-type="9" file-name='相关'></fileUpload>

    <el-dialog :before-close='close' title="定标" :visible.sync="dialogVisible">
      <el-form :model="dialogForm" :rules="rules"  ref="dialogForm" :label-width="formLabelWidth">
        <el-row>
          <el-col :span="12">
            <el-form-item label="中标人：" prop="winningBidder">
              <el-input v-model="dialogForm.winningBidder" readonly placeholder="请选择">
                <el-button slot="append" icon="el-icon-search" @click="showBidderDialog"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="中标金额：" prop="winningAmount">
              <el-input v-model="dialogForm.winningAmount"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="代理服务费：" prop="agencyServiceFee">
              <el-input v-model="dialogForm.agencyServiceFee"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="候选人公示地址：" prop="candidatePublicAddr">
              <el-input v-model="dialogForm.candidatePublicAddr"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="中标公示地址：" prop="addrWinningBid">
              <el-input v-model="dialogForm.addrWinningBid"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button class="cancal" @click="close">取 消</el-button>
        <el-button type="primary" @click="submitbtnFun('dialogForm')" :loading="isLoading">确认</el-button>
      </div>
    </el-dialog>
    <bidder-dialog :tenderSystemCode="tenderSystemCode" :showVisible="showBidderVisible" @selected="selectedBidder" @showDialog="showBidderDialog"></bidder-dialog>
  </div>
</template>
<script>
import { validMoney } from '@/assets/js/validate'
import {archivesFile} from '@/api/archives'
import bidderDialog from '@/pages/project/projectProcess/common/bidder_dialog.vue'
export default {
  components: {
    fileUpload: resolve => require(['./fileUpload'], resolve),
    bidderDialog
  },
  data () {
    return {
      // 提交时验证
      rules: {
        winningAmount: [
          { required: true, min: 1, max: 100, message: '请输入中标金额', trigger: ['blur', 'change'] },
          { validator: validMoney, trigger: ['blur', 'change'] }
        ],
        agencyServiceFee: [
          { required: true, min: 1, max: 100, message: '请输入代理服务费', trigger: ['blur', 'change'] },
          { validator: validMoney, trigger: ['blur', 'change'] }
        ]
      },
      dialogVisible: false,
      formLabelWidth: '120px',
      dialogForm: {
        winningBidder: '',
        tenderPrice: '',
        tenderBondPrice: '',
        sellStartDate: '',
        sellEndDate: '',
        bidOpenDate: ''
      },
      isLoading: false,
      showBidderVisible: false
    }
  },
  watch: {
  },
  props: ['item', 'canEdit', 'tenderSystemCode'],
  methods: {
    // 选择投标人
    selectedBidder (obj) {
      this.dialogForm.winningBidder = obj.name
    },
    showBidderDialog () {
      this.showBidderVisible = !this.showBidderVisible
    },
    // 编辑
    handleEdit () {
      this.dialogVisible = true
      if (this.item.archivesProjectExpands) {
        this.dialogForm = Object.assign({}, this.item.archivesProjectExpands)
      }
    },
    close () {
      this.dialogVisible = false
      this.dialogForm = {
        winningBidder: '',
        tenderPrice: '',
        tenderBondPrice: '',
        sellStartDate: '',
        sellEndDate: '',
        bidOpenDate: ''
      }
      this.$refs.dialogForm.resetFields()
    },
    // 提交
    submitbtnFun (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$confirm('确认保存吗?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.isLoading = true
            this.dialogForm.archivesFileId = this.item.objectId
            archivesFile.savaOrUpdateArchivesProjectExpands(this.dialogForm).then((res) => {
              this.isLoading = false
              if (res.data.resCode === '0000') {
                this.item.archivesProjectExpands = res.data.archivesProjectExpands
                this.close()
              }
            })
          })
        } else {
          return false
        }
      })
    }
  },
  mounted () {
  }
}
</script>
<style lang="less">
</style>
