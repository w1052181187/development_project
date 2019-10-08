<template>
  <div class="center_box" id="contract_box_detail">
      <div class="coninfor_box">
        <div class="coninfor_title">查看</div>
        <div class="coninfor_con">
          <div class="infor_side_conbigbox">
            <el-form :model="projectForm" label-width="140px" class="demo-ruleForm">
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
                        <span>{{projectForm.invoices.nature === 0 ? '电子发票' : ''}}</span>
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
                  <el-table
                            :data="consumetableData"
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
              <!--审批记录-->
              <div class="dialog_cancel_infoson_father">
                <div class="dialog_cancel_infoson_title">
                  <div class="dialog_cancel_infoson_titlewenzi">审批记录</div>
                  <div class="dialog_cancel_infoson_titlexian"></div>
                </div>
                <div class="dialog_detail">
                  <el-table :data="approvaltableData"
                            border
                            header-cell-class-name="table_header">
                    <el-table-column
                      prop="createrUserName"
                      label="审核人"
                      align="left"
                      show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column
                      prop="approvestatus"
                      label="操作结果"
                      align="left"
                      :formatter="approvestatusFormatter"
                      show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column
                      prop="remark"
                      label="备注"
                      align="left"
                      show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column
                      prop="createtime"
                      label="审批时间"
                      align="left"
                      show-overflow-tooltip>
                    </el-table-column>
                  </el-table>
                </div>
              </div>
              <el-form-item class="operation_btn_box">
                <el-button @click="$router.go(-1)" class="cancel_btn">返回</el-button>
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
      approvaltableData: [] // 审批记录表格
    }
  },
  methods: {
    detail () {
      invoiceAdmin.invoiceAdminDetail(this.$route.params.objectId).then((res) => {
        this.projectForm = res.data.applyInvoicesRecords
        this.consumetableData = res.data.zbOrders
        this.approvaltableData = res.data.applyInvoicesRecords.approveRecords
        console.log(this.approvaltableData)
        console.log(this.projectForm)
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
    // 操作结果
    approvestatusFormatter (row) {
      if (row) {
        switch (row.approvestatus) {
          case 0 :
            return '不通过'
          case 1 :
            return '通过'
        }
      } else {
        return '------'
      }
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
    .industry_box{
      width: 100%;
    }
    .contract_bigbox{
      width: 100%;
    }
    .detail_jilian .el-input__inner{
      background: transparent;
      border: none;
    }
    .detail_jilian .el-input__suffix{
      display: none;
    }
    .el-cascader.is-disabled .el-cascader__label {
      color: #333!important;
    }
  }
</style>
