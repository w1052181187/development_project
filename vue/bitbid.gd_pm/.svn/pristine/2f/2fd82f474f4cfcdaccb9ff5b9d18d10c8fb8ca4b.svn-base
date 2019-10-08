<template>
  <div class="center_box" id="contract_box_collection">
    <el-form :model="projectForm" :rules="rules" ref="projectForm" label-width="140px" class="demo-ruleForm">
      <div class="coninfor_box">
        <div class="coninfor_title">创建</div>
        <div class="coninfor_con">
          <div class="infor_side_conbigbox">
            <div class="dialog_cancel_infoson_father">
              <div class="dialog_detail">
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="代理机构：" prop="agencyId">
                      <el-select v-model="projectForm.agencyId" placeholder="请选择代理机构" class="el_select" @change="anencySelect(projectForm.agencyId)">
                        <el-option
                          v-for="item in projectData"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value" :title="item.label">
                        </el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  </el-row>
                <el-row>
                  <el-col>
                    <el-form-item label="是否启用：" class="mandatory">
                        <el-radio v-model="projectForm.isStartUsing" :label="acceptResultsRadio.label" v-for="(acceptResultsRadio,index) in radioAcceptResults" :key="index">{{acceptResultsRadio.name}}</el-radio>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                    <el-col>
                      <el-form-item label="备注：" prop="remark">
                        <el-input type="textarea" v-model="projectForm.remark"></el-input>
                      </el-form-item>
                    </el-col>
                </el-row>
              </div>
            </div>
            <el-form-item class="operation_btn_box">
              <el-button type="primary" @click="submitForm('projectForm')" class="save_btn" :loading="subStatus">提交</el-button>
              <el-button @click="$router.go(-1)" class="cancel_btn" :loading="subStatus">取消</el-button>
            </el-form-item>
          </div>
        </div>
      </div>
    </el-form>
  </div>
</template>

<script>
import {agencyEnterprise, shroffAccountNumber} from '@/api'
export default {
  name: '',
  data () {
    return {
      projectForm: {
        isStartUsing: 1
      },
      subStatus: false, // 是否保存
      // 表单验证
      rules: {
        agencyId: [
          { required: true, message: '请选择代理机构', trigger: ['blur', 'change'] }
        ],
        remark: [
          { required: true, message: '请输入备注', trigger: ['blur', 'change'] },
          {min: 1, max: 500, message: '长度在 1 到 500 个字符', trigger: ['blur', 'change']}
        ]
      },
      // 交易系统版本
      projectData: [],
      radioAcceptResults: [
        {
          label: 1,
          name: '启用'
        },
        {
          label: 0,
          name: '禁用'
        }
      ]
    }
  },
  methods: {
    // 选择代理机构
    anencySelect (agencyId) {
      shroffAccountNumber.selectAnc(agencyId).then((res) => {
        if (res.data.count !== 0) {
          this.$message({
            type: 'warning',
            message: '此代理机构已选中，不能重复选择'
          })
          this.projectForm.agencyId = ''
          return false
        }
      })
    },
    // 添加
    submitForm (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          console.log(this.projectForm)
          shroffAccountNumber.save(this.projectForm).then((res) => {
            this.$router.push({path: '/admin/collection/index'})
          })
        } else {
          return false
        }
      })
    },
    queryAgency () {
      // 查询所有的合作代理机构
      let url = {}
      url = {
        isvalid: 1,
        userId: this.$store.getters.authUser.userId
      }
      agencyEnterprise.queryNotPageList(url).then((res) => {
        res.data.agencyEnterprises.forEach((item) => {
          let agencyEnterprise = {}
          agencyEnterprise.value = item.id
          agencyEnterprise.label = item.enterpriseName
          this.projectData.push(agencyEnterprise)
        })
      })
    }
  },
  mounted () {
    this.queryAgency()
  }
}
</script>

<style lang="less">
  .el-textarea__inner{
    min-height: 150px!important;
  }
</style>
