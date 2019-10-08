<template>
  <div class="center_box" id="contract_box_detail">
      <div class="coninfor_box">
        <div class="coninfor_title">审核</div>
        <div class="coninfor_con">
          <div class="infor_side_conbigbox">
            <el-form :model="projectForm" :rules="rules" ref="projectForm"  label-width="140px" class="demo-ruleForm" >
              <!--发票信息-->
              <div class="dialog_cancel_infoson_father">
                <div class="dialog_cancel_infoson_title">
                  <div class="dialog_cancel_infoson_titlewenzi">发票信息</div>
                  <div class="dialog_cancel_infoson_titlexian"></div>
                </div>
                <div class="dialog_detail">
                  <el-row>
                    <el-col :span="12">
                      <el-form-item label="发票性质：">
                        <span>{{projectForm.invoices.nature===0?'电子发票':''}}</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="发票类型：">
                        <span>{{projectForm.invoices.type===0?'增值税普通发票':''}}</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="12">
                      <el-form-item label="发票抬头：">
                        <span>{{projectForm.invoices.name}}</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="发票总额（元）：">
                        <span style="color: #ff0000">{{projectForm.invoicesPrice}}</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="12">
                      <el-form-item label="联系电话：">
                        <span>{{projectForm.invoices.phone}}</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="申请时间：">
                        <span>{{projectForm.applyDate}}</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <!--消费信息-->
              <div class="dialog_cancel_infoson_father">
                <div class="dialog_cancel_infoson_title">
                  <div class="dialog_cancel_infoson_titlewenzi">消费信息</div>
                  <div class="dialog_cancel_infoson_titlexian"></div>
                </div>
                <div class="dialog_detail">
                  <el-table :data="consumetableData"
                            border
                            show-summary
                            :summary-method="getSummaries"
                            header-cell-class-name="table_header">
                    <el-table-column
                      prop="code"
                      label="订单编号"
                      align="left"
                      show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column
                      prop="type"
                      label="消费明细"
                      align="left"
                      :formatter="typeFormatter"
                      show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column
                      prop="confirmeddate"
                      label="消费时间"
                      align="left"
                      show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column
                      prop="price"
                      label="消费金额（元）"
                      align="left"
                      show-overflow-tooltip>
                    </el-table-column>
                  </el-table>
                </div>
              </div>
              <!--审核填写信息-->
              <div class="dialog_cancel_infoson_father">
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="经办人：" prop="operator">
                      <el-input placeholder="请输入经办人" v-model="projectForm.operator"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="审核状态："  prop="approveStatus">
                      <el-select v-model="projectForm.approveStatus" placeholder="请选择" class="el_select" >
                        <el-option
                          v-for="item in projectData"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                        </el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="备注">
                      <el-input type="textarea" v-model="projectForm.remark"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
              <el-form-item class="operation_btn_box">
                <el-button type="primary" @click="submitForm('projectForm')" class="save_btn" :loading="subStatus">提交</el-button>
                <el-button @click="$router.go(-1)" class="cancel_btn" :loading="subStatus">取消</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
import { invoiceAdmin } from '@/api'
export default {
  name: '',
  // components: {
  //   fileDownload
  // },
  data () {
    return {
      projectForm: {
        invoices: {
        }
      }, // 详情信息
      consumetableData: [], // 消费信息表格
      // 审核状态
      projectData: [
        {
          value: 1,
          label: '通过'
        },
        {
          value: 0,
          label: '不通过'
        }
      ],
      // 表单验证
      rules: {
        operator: [
          { required: true, message: '请输入经办人', trigger: ['blur', 'change'] },
          {min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: ['blur', 'change']}
        ],
        approveStatus: [
          {required: true, message: '请选择审核状态', trigger: ['blur', 'change']}
        ]
      },
      subStatus: false
    }
  },
  methods: {
    detail () {
      invoiceAdmin.invoiceAdminDetail(this.$route.params.objectId).then((res) => {
        this.projectForm = res.data.applyInvoicesRecords
        this.consumetableData = res.data.zbOrders
      })
    },
    // 合计方法
    getSummaries (param) {
      const { columns, data } = param
      const sums = []
      let sumPrice = 0
      data.map((item) => {
        sumPrice += Number(item.price)
      })
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '合计'
          return
        }
        if (index === 3) {
          sums[index] = sumPrice.toFixed(2)
        } else {
          sums[index] = ''
        }
      })
      return sums
    },
    // 消费明细
    typeFormatter (row) {
      if (row) {
        switch (row.type) {
          case 3 :
            return '投标保证金'
          case 4 :
            return '系统使用费'
          case 5 :
            return '中标服务费'
          case 0 :
            return 'CA费用'
          default:
            return '标书费'
        }
      } else {
        return '------'
      }
    },
    // 提交审核
    submitForm (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          let approveFrom = {}
          approveFrom.operator = this.projectForm.operator // 经办人
          approveFrom.approvestatus = this.projectForm.approveStatus // 审核状态
          approveFrom.remark = this.projectForm.remark // 备注
          approveFrom.creater = 1
          approveFrom.code = this.projectForm.code
          approveFrom.type = 2
          // 判断审核状态不通过提示
          if (approveFrom.approvestatus === 0 && !approveFrom.remark) {
            this.$message({
              type: 'warning',
              message: '请填写不通过理由'
            })
            return false
          }
          invoiceAdmin.approveList(approveFrom).then((res) => {
            if (res.data.resCode === '0000') {
              let statusFrom = {}
              statusFrom.id = this.$route.params.objectId
              statusFrom.reason = this.projectForm.remark
              // 如果是通过 状态变化
              if (approveFrom.approvestatus === 1) {
                statusFrom.status = 3
              } else {
                statusFrom.status = 2
              }
              statusFrom.invoicesPrice = this.projectForm.invoicesPrice
              console.log(statusFrom)
              // 修改审批状态
              invoiceAdmin.updateApproveStatus(statusFrom).then((res) => {
                this.$router.push({path: `/admin/invoice-admin/index`})
              })
            }
          })
        } else {
          return false
        }
      })
    }
  },
  mounted () {
    this.detail()
  }
}
</script>

<style lang="less">
  #contract_box_detail{
    .dialog_detail{
      width: 75%;
    }
    .el-input{
      width: 20%;
    }
    .el_select{
      width: 20%;
    }
    .el_select .el-input{
      width:100%;
    }
    .el-select-dropdown{
      width: 15%!important;
    }
    .el-textarea{
      width: 20%;
    }
  }
</style>
