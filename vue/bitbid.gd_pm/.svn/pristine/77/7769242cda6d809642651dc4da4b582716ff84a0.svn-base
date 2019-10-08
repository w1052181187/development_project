<template>
  <div class="center_box" id="contract_box">
    <el-form :model="dataForm" ref="dataForm" label-width="140px" class="demo-ruleForm">
      <div class="coninfor_box">
        <div class="coninfor_title">查看轮换信息</div>
        <div class="coninfor_con">
          <div class="infor_side_conbigbox">
            <div class="dialog_cancel_infoson_father">
              <div class="dialog_detail">
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="标题：">
                      <span>{{dataForm.title}}</span>
                    </el-form-item>
                  </el-col>
                  </el-row>
                <el-row>
                  <el-col>
                    <el-form-item label="URL地址：">
                      <span>{{dataForm.url}}</span>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col>
                    <el-form-item label="图片：" class="detail_textarea">
                      <file-download :fileArrays="dataForm.fileInformations" fileType="1"></file-download>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <el-form-item class="operation_btn_box">
              <el-button @click="$router.go(-1)" class="cancel_btn">返回</el-button>
            </el-form-item>
          </div>
        </div>
      </div>
    </el-form>
  </div>
</template>

<script>
import {rotationInfo} from '@/api'
import fileDownload from '@/components/common/file-download'
export default {
  name: '',
  components: {
    fileDownload
  },
  data () {
    return {
      dataForm: {
        title: '',
        url: '',
        fileInformations: []
      }
    }
  },
  methods: {
    detail () {
      rotationInfo.detail(this.$route.params.objectId).then((res) => {
        this.dataForm = res.data.info
      })
    }
  },
  mounted () {
    this.detail()
  }
}
</script>

<style lang="less">
  .detail_textarea .el-textarea__inner{
    border: none;
  }
</style>
