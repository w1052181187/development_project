<template>
  <div id="bidoping">
    <div class="file-type-name">
      <span>开标信息</span>
    </div>
    <el-form class="viewdetails" :model="item.archivesProjectExpands" ref="form">
      <el-row>
        <el-col :span="12">
          <el-form-item label="开标时间：">
            <span>{{item.archivesProjectExpands ? item.archivesProjectExpands.bidOpenDate : ''}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="开标地点：">
            <span>{{item.archivesProjectExpands ? item.archivesProjectExpands.placeOpeningBid : ''}}</span>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div class="file-type-add" @click="handleEdit" v-if="canEdit">
      <span>编辑</span>
    </div>
    <fileUpload class="file-type-enclosure" :canEdit="canEdit"
                :file-list="item.refArchivesFileInformationList"
                :object-id="item.objectId" :file-type='9' file-name='相关'></fileUpload>
    <el-dialog :before-close='close' title="开标" :visible.sync="dialogVisible">
      <el-form :model="dialogForm" ref="dialogForm" :label-width="formLabelWidth">
        <el-row>
          <el-col :span="12">
            <el-form-item label="开标时间：">
              <el-date-picker
                v-model="dialogForm.bidOpenDate"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开标地点：">
              <el-input v-model="dialogForm.placeOpeningBid"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button class="cancal" @click="close">取 消</el-button>
        <el-button type="primary" @click="submitForm('dialogForm')" :loading="isLoading"
                   :disabled="!dialogForm.bidOpenDate && !dialogForm.placeOpeningBid">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {archivesFile} from '@/api/archives'
export default {
  components: {
    fileUpload: resolve => require(['./fileUpload'], resolve)
  },
  watch: {
  },
  props: ['item', 'canEdit'],
  data () {
    return {
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7
        }
      },
      formLabelWidth: '120px',
      dialogVisible: false,
      dialogForm: {
        bidOpenDate: ''
      },
      isLoading: false
    }
  },
  methods: {
    // 编辑
    handleEdit () {
      this.dialogVisible = true
      if (this.item.archivesProjectExpands) {
        this.dialogForm = Object.assign({}, this.item.archivesProjectExpands)
      }
    },
    close () {
      this.dialogForm = {}
      this.dialogVisible = false
    },
    // 提交
    submitForm (formName) {
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
    }
  },
  mounted () {
  }
}
</script>
<style lang="less">
</style>
