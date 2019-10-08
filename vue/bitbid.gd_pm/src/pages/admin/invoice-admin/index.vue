<template>
  <div class="center_box contract-add-box" id="invoice-admin">
    <div class="coninfor_box">
      <div class="coninfor_title">发票管理</div>
      <div class="con_table" v-show="isTrue === 0" >
        <!--查询功能-->
        <div class="con_top_box">
          <el-form :model="searchForm" label-width="120px" class="demo-ruleForm">
            <div class="seacher_box_fatherzzt">
              <el-form-item label="时间：">
                <el-date-picker
                  v-model="hangDataRange"
                  type="datetimerange"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  range-separator="至"
                  start-placeholder="开始时间"
                  end-placeholder="结束时间"
                  align="right">
                </el-date-picker>
              </el-form-item>
            </div>
            <div class="agency seacher_box_fatherzzt">
              <el-form-item label="代理机构：">
                <el-select v-model="searchForm.agencyId" placeholder="请选择" class="el_select">
                  <el-option
                    v-for="item in projectData"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value" :title="item.label">
                  </el-option>
                </el-select>
              </el-form-item>
            </div>
            <div class="seacher_box_fatherzzt">
              <el-form-item label="状态：">
                <el-select v-model="searchForm.status" placeholder="请选择" class="el_select">
                  <el-option
                    v-for="item in statusselect"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </div>
            <div class="seacher_box_fatherzzt">
              <el-button type="primary" class="screeningBtn inquire_seacherbtn" icon="el-icon-search" @click="searchBtn">查询</el-button>
            </div>
          </el-form>
        </div>
      </div>
      <div class="coninfor_con">
        <div class="infor_side_navbigbox">
          <div v-for="(sideName,index) in sideData" class="infor_side_navbox" :key="index" :class="{infor_side_nav_active:isTrue==index}"
               @click="chooseAddr(index, sideName)">
            {{sideName.name}}
          </div>
        </div>
        <el-table v-if="isTrue === 0"
          :key="Math.random()"
          :data="projecttableData"
          border
          header-cell-class-name="table_header">
          <el-table-column
            type="index"
            label="序号"
            width="50"
            align="left"
            :index="indexMethod">
          </el-table-column>
          <el-table-column
            prop="applyDate"
            label="申请时间"
            align="left"
            width="200"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="invoices.name"
            label="付款方单位名称"
            align="left"
            width="150"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="invoices.creditCode"
            label="统一社会信用代码"
            align="left"
            width="150"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="invoices.phone"
            label="付款方联系电话"
            align="left"
            width="150"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="invoicesContent"
            label="发票内容"
            align="left"
            width="100"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="invoicesPrice"
            label="金额"
            align="left"
            width="100"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="agencyName"
            label="代理机构"
            align="left"
            width="100"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="status"
            label="状态"
            align="left"
            width="100"
            :formatter="statusFormatter"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            label="操作" width="200" align="center" fixed="right">
            <template slot-scope="scope">
              <el-button type="text" size="small" class="handle_btn"  @click="detailBtn(scope)" >详情</el-button>
              <el-button type="text" size="small" class="handle_btn" v-if="scope.row.status === 0"  @click="examine(scope)">审核</el-button>
              <el-button type="text" size="small" class="handle_btn" v-if="scope.row.status === 3"  @click="upload(scope)">上传</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--未申请-->
        <el-table v-if="isTrue === 1"
                  :key="Math.random()"
                  :data="noapplytableData"
                  border
                  show-summary
                  :summary-method="getSummaries"
                  header-cell-class-name="table_header">
          <el-table-column
            type="index"
            label="序号"
            width="50"
            align="left"
            :index="indexMethod">
          </el-table-column>
          <el-table-column
            prop="code"
            label="订单号"
            align="left"
            width="150"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="faBaoNumber"
            label="采购项目编号"
            align="left"
            width="150"
            :formatter="faBaoNumberFormatter"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="faBaoMingCheng"
            label="采购公告名称"
            align="left"
            width="150"
            :formatter="faBaoMingChengFormatter"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="biaoDuanNumber"
            label="包组编号"
            align="left"
            width="100"
            :formatter="biaoDuanNumberFormatter"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="chargeitem"
            label="费项"
            align="left"
            width="100"
            :formatter="typeFormatter"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="paymentchannel"
            label="缴费方式"
            align="left"
            width="100"
            :formatter="paymentmethodFormatter"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="createdate"
            label="缴费时间"
            align="left"
            width="150"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="operatorUser.name"
            label="操作人"
            align="left"
            width="100"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="price"
            label="缴费金额（元）"
            align="left"
            width="150"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            label="操作" width="100" align="center" fixed="right">
            <template slot-scope="scope">
              <el-button type="text" size="small" class="handle_btn" @click="noapplydetailBtn(scope)">
                详情
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--分页-->
        <div class="page_box">
          <el-pagination
            class="pagebox"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :total="total"
            :page-size='pageSize'
            layout="prev, pager, next, jumper">
          </el-pagination>
        </div>
      </div>
      <!--上传弹框-->
      <el-dialog title="上传电子发票" :visible.sync="dialogTableVisible">
        <el-form :model="dataForm" ref="dataForm" label-width="140px" class="demo-ruleForm">
          <div class="coninfor_box">
            <div class="coninfor_con">
              <div class="infor_side_conbigbox">
                <div class="dialog_cancel_infoson_father">
                  <div class="dialog_detail">
                    <el-row>
                      <el-col :span="24">
                        <el-form-item label="付款方单位名称：" prop="title">
                          <span>{{dataForm.danweimingcheng}}</span>
                        </el-form-item>
                      </el-col>
                    </el-row>
                    <el-row>
                      <el-col>
                        <el-form-item label="上传：" prop="fileInformations" class="mandatory">
                          <upload-file @uploadSuccess="uploadSuccess" @deleteSuccess="deleteSuccess" :fileArrays="dataForm.fileInformations"
                                       fileType="2" isImage="2"></upload-file>
                        </el-form-item>
                      </el-col>
                    </el-row>
                  </div>
                </div>
                <el-form-item class="operation_btn_box">
                  <el-button type="primary" @click="submitForm('dataForm')" class="save_btn" :loading="subStatus">确认</el-button>
                  <el-button @click="cancelbtn" class="cancel_btn" :loading="subStatus">取消</el-button>
                </el-form-item>
              </div>
            </div>
          </div>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { invoiceAdmin } from '@/api'
import uploadFile from '@/components/upload/publicFileUpload'
// import {agencyEnterprise} from '@/api'
export default {
  name: '',
  components: {
    uploadFile
  },
  data () {
    return {
      hangDataRange: [], // 时间
      // 代理机构
      projectData: [],
      // 状态
      statusselect: [
        {
          value: 0,
          label: '开票中'
        },
        {
          value: 1,
          label: '已开票'
        },
        {
          value: 2,
          label: '已作废'
        },
        {
          value: 3,
          label: '待开票'
        }
      ],
      // 搜索对象
      searchForm: {},
      // tab切换标题
      sideData: [
        {
          name: '已申请'
        },
        {
          name: '未申请'
        }
      ],
      isTrue: 0, // 默认为已申请
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      projecttableData: [], // 已申请表格
      noapplytableData: [], // 未申请表格
      dialogTableVisible: false, // 弹框展示
      dataForm: {
        fileInformations: []
      },
      subStatus: false // 是否提交
    }
  },
  methods: {
    // 查询所有的合作代理机构
    queryAgency () {
      let url = {}
      url = {
        isvalid: 1
      }
      invoiceAdmin.queryNotPageList(url).then((res) => {
        res.data.agencyEnterprises.forEach((item) => {
          let agencyEnterprise = {}
          agencyEnterprise.value = item.id
          agencyEnterprise.label = item.enterpriseName
          this.projectData.push(agencyEnterprise)
        })
      })
    },
    // 序号
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    // 上传文件
    uploadSuccess (contract, file, fileType) {
      this.dataForm.fileInformations.splice(0, 1, file)
    },
    deleteSuccess (contract, fileId) {
      this.dataForm.fileInformations = this.dataForm.fileInformations.filter(item => item.relativePath !== fileId)
    },
    // 弹框文件上传确认
    submitForm (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          let fileInformation = this.dataForm.fileInformations[0]
          fileInformation.relatedCode = this.dataForm.relatedCode
          invoiceAdmin.uploadFile(fileInformation).then((res) => {
            // 保存成功后 先把弹框去掉 修改发票的状态为已开票 然后刷新列表
            if (res.data.resCode === '0000') {
              this.dialogTableVisible = false
              // 修改审批状态
              invoiceAdmin.updateStatus(this.dataForm.id).then((res) => {
                this.list()
              })
            }
          })
        } else {
          return false
        }
      })
      this.dataForm.fileInformations = []
    },
    // 弹框取消
    cancelbtn () {
      this.dataForm.fileInformations = []
      this.dialogTableVisible = false
    },
    faBaoNumberFormatter (row) {
      if (row) {
        if (row.faBaoNumber) {
          return row.faBaoNumber
        } else {
          return '------'
        }
      }
    },
    faBaoMingChengFormatter (row) {
      if (row) {
        if (row.faBaoMingCheng) {
          return row.faBaoMingCheng
        } else {
          return '------'
        }
      }
    },
    biaoDuanNumberFormatter (row) {
      if (row) {
        if (row.biaoDuanNumber) {
          return row.biaoDuanNumber
        } else {
          return '------'
        }
      }
    },
    // tab切换
    chooseAddr (index) {
      this.currentPage = 1
      this.pageNo = 0
      this.pageSize = 10
      this.isTrue = index
      this.searchForm = {}
      this.hangDataRange = []
      this.list()
    },
    // 未申请合计
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
        if (index === 9) {
          sums[index] = sumPrice.toFixed(2)
        } else {
          sums[index] = ''
        }
      })
      return sums
    },
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    // 详情
    detailBtn (scope) {
      this.$router.push({path: `/admin/invoice-admin/detail/${scope.row.id}`})
    },
    // 未申请详情
    noapplydetailBtn (scope) {
      this.$router.push({path: `/admin/invoice-admin/noapplydetail/${scope.row.id}`})
    },
    // 审核
    examine (scope) {
      this.$router.push({path: `/admin/invoice-admin/examine/${scope.row.id}`})
    },
    // 上传
    upload (scope) {
      if (scope.row.payUser != null) {
        this.dataForm.danweimingcheng = scope.row.payUser.danweimingcheng
      }
      this.dataForm.relatedCode = scope.row.code
      this.dataForm.id = scope.row.id
      if (scope.row.fileInformation) {
        this.dataForm.fileInformations.push(scope.row.fileInformation)
      }
      this.dialogTableVisible = true
    },
    // 搜索条件
    searchBtn () {
      this.currentPage = 1
      this.pageNo = 0
      this.list()
    },
    // 费项
    typeFormatter (row) {
      if (row) {
        switch (row.chargeitem) {
          case 0 :
            return 'CA费用'
          default:
            return '标书费'
        }
      } else {
        return '------'
      }
    },
    // 缴费方式
    paymentmethodFormatter (row) {
      if (row) {
        switch (row.paymentchannel) {
          case 4 :
            return '支付宝'
        }
      } else {
        return '------'
      }
    },
    // 已申请状态
    statusFormatter (row) {
      if (row) {
        switch (row.status) {
          case 0 :
            return '开票中'
          case 1 :
            return '已开票'
          case 2 :
            return '已作废'
          case 3 :
            return '待开票'
        }
      } else {
        return '------'
      }
    },
    // 列表查询
    // 列表显示
    list () {
      let url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize
      }
      if (this.hangDataRange !== null && this.hangDataRange.length > 0) { // 续费日期
        this.searchForm.applyStartDate = this.hangDataRange[0]
        this.searchForm.applyEndDate = this.hangDataRange[1]
      }
      if (this.hangDataRange === null) {
        this.searchForm.applyStartDate = null
        this.searchForm.applyEndDate = null
      }
      let isSeacher = (JSON.stringify(this.searchForm) === '{}')
      if (!isSeacher) { // 是否为搜索
        url = Object.assign(this.searchForm, url)
      }
      // 0 已申请 1未申请
      if (this.isTrue === 0) {
        invoiceAdmin.invoiceAdminList(url).then((res) => {
          this.projecttableData = res.data.applyInvoicesRecordsPageInfo.list
          console.log(this.projecttableData)
          this.total = res.data.applyInvoicesRecordsPageInfo.total
        })
      } else {
        // url.status = 2
        url.paymentchannel = 4
        url.chargeitems = 1
        console.log(url)
        invoiceAdmin.invoiceAdminListNO(url).then((res) => {
          this.noapplytableData = res.data.zbOrderFlowPageInfo.list
          console.log(this.noapplytableData)
          this.total = res.data.zbOrderFlowPageInfo.total
        })
      }
    }
  },
  mounted () {
    this.queryAgency()
    this.list()
  }
}
</script>
<style  lang="less">
  #invoice-admin{
    /*.agency .el-form-item .el-form-item__content{*/
      /*width: 350px;*/
    /*}*/
    .el-select.el_select{
      width: 400px;
    }
   .screeningBtn {
      padding: 0;
      border: none;
      margin-left: 20px;
   }
    .el-date-editor.el-range-editor{
    .el-range-input{
      height: 30px;
    }
  }
   .el-cascader-panel {
    display: block;
    .el-scrollbar {
      float: left;
    }
  }
    .industry_box{
      width: 100%;
    }
    .contract_bigbox{
      width: 100%;
    }
    .shanchun_icon{
      img{
        width: 20px;
        height: 20px;
      }
    }
    .coninfor_con{
      margin-top: 15px;
    }
  }
  .el-select-dropdown.el-popper{
    width: 400px
  }

</style>
