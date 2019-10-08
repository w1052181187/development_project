<template>
  <div class="center_box" id="contract_box">
    <el-form :model="projectForm" :rules="rules" ref="projectForm" label-width="140px" class="demo-ruleForm">
      <div class="coninfor_box">
        <div class="coninfor_title">查看</div>
        <div class="coninfor_con">
          <div class="infor_side_conbigbox">
            <div class="dialog_cancel_infoson_father">
              <div class="dialog_detail">
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="代理机构：" prop="agency">
                      <span>{{projectForm.agency}}</span>
                    </el-form-item>
                  </el-col>
                  </el-row>
                <el-row>
                  <el-col>
                    <el-form-item label="是否启用：" class="mandatory">
                      <span v-if="projectForm.isStartUsing === 1">启用</span>
                      <span v-if="projectForm.isStartUsing === 0">禁用</span>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col>
                    <el-form-item label="备注：" class="detail_textarea">
                      <el-input type="textarea" v-model="projectForm.remark" readonly></el-input>
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
import {shroffAccountNumber} from '@/api'
export default {
  name: '',
  data () {
    return {
      projectForm: {
      },
      subStatus: false, // 是否保存
      // 表单验证
      rules: {
        agency: [
          { required: true, message: '请选择代理机构', trigger: ['blur', 'change'] }
        ]
      },
      // 交易系统版本
      projectData: [],
      radioAcceptResults: [
        {
          label: 0,
          name: '启用'
        },
        {
          label: 1,
          name: '禁用'
        }
      ]
    }
  },
  methods: {
    detail () {
      shroffAccountNumber.detail(this.$route.params.objectId).then((res) => {
        this.projectForm = res.data.shroffAccountNumber
        this.projectForm.agency = res.data.shroffAccountNumber.agency.enterpriseName
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
    overflow: auto;
  }
</style>
