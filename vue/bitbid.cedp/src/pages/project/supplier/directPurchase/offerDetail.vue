<template>
  <div class="offerDetail details blueCon" id="offerDetail">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/'}">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/'}">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/projectDynamic'}">项目动态</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/projectDynamic/directPurchase/offer'}">直采报价</el-breadcrumb-item>
      <el-breadcrumb-item>直采报价详情</el-breadcrumb-item>
    </el-breadcrumb>
    <el-form :model="dataForm" label-width="160px">
      <div class="mainContent">
        <h3 class="title">项目信息</h3>
        <el-row class="marginTop">
          <el-col :span="24">
            <el-form-item label="项目名称">
              <span>{{dataForm.project.name}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="联系人">
              <span>{{dataForm.project.contactName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="联系方式">
              <span>{{dataForm.project.contactPhone}}</span>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="mainContent">
        <h3 class="title">报价信息</h3>
        <el-row class="marginTop ">
          <el-col :span="24">
            <el-form-item label="报价公司名称">
              <span>{{this.$store.getters.authUser.enterpriseName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="联系人">
              <span>{{dataForm.contactName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="联系方式">
              <span>{{dataForm.contactPhone}}</span>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="mainContent">
        <h3 class="title">产品信息</h3>
        <p class="handle handle-none">
<!--          // totalPurchasePrice: '', // 采购总金额-->
<!--          //   totalOfferPrice: '', //, 报价总金额-->
<!--          //   offeredNum: '' // 已报价标的物数目-->
          <span>共{{subjectNumberNum}}种物料，采购总金额：<i>￥{{totalPurchasePrice | numFilter}}</i>（已报价{{offeredNum}}种物料，报价总金额：<i>￥{{totalOfferPrice | numFilter}}</i>）</span>
        </p>
        <div class="tablemain product-table">
          <el-table
            :data="tableData"
            border
            header-cell-class-name="table_header"
            style="width: 100%">
            <el-table-column
              type="index"
              label="序号"
              width="50"
              align="center"
              :index="indexMethod">
            </el-table-column>
            <el-table-column
              prop="subjectMatterInst.subjectMatter.materielNumber"
              label="物料号">
            </el-table-column>
            <el-table-column
              prop="subjectMatterInst.subjectMatter.operationCategoryNames"
              label="物料类目">
            </el-table-column>
            <el-table-column
              prop="subjectMatterInst.subjectMatter.name"
              label="物料名称">
            </el-table-column>
            <el-table-column
              prop="subjectMatterInst.subjectMatter.brand"
              label="品牌">
            </el-table-column>
            <el-table-column
              prop="subjectMatterInst.subjectMatter.specification"
              label="型号">
            </el-table-column>
            <el-table-column
              prop="subjectMatterInst.subjectMatter.description"
              label="物料描述">
            </el-table-column>
            <el-table-column
              prop="subjectMatterInst.amount"
              label="采购量">
            </el-table-column>
            <el-table-column
              prop="subjectMatterInst.totalPrice"
              label="预算总额">
            </el-table-column>
            <el-table-column
              prop="money"
              label="报价总额">
              <template slot-scope="scope">
                <span v-if="scope.row.isConfirm === 1" class="moneyOffer">{{scope.row.offerPrice}}</span>
                <span v-else>未报价</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="date"
              align="center"
              label="备注及附件">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="handleCheck(scope, '备注及附件')">查看</el-button>
              </template>
            </el-table-column>
            <el-table-column
              prop="number"
              align="center"
              label="操作">
              <template slot-scope="scope">
                <el-button type="text" size="small" v-if="scope.row.isConfirm === 1" @click="handleCheck(scope, '报价及附件')">查看</el-button>
                <span v-else>-----</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <div class="mainContent">
        <h3 class="title">供货信息</h3>
        <div class="prompt-title">
          <img src="../../../../../static/images/gth.png" alt="">
          <span>请仔细确认供应商供货信息</span>
        </div>
        <el-row class="marginTop">
          <el-col :span="24">
            <el-form-item label="生产备货周期">
              <span>{{dataForm.producetionTime}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="交货期">
              <span>{{dataForm.leadTime}}</span>
            </el-form-item>
          </el-col>
<!--          <el-col :span="24">-->
<!--            <el-form-item label="预估运费">-->
<!--              <span>{{dataForm.name}}</span>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
          <el-col :span="24">
            <el-form-item label="供货地址">
              <span>{{dataForm.addressValue}}</span>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="mainContent">
        <h3 class="title">采购信息</h3>
        <el-row class="marginTop">
          <el-col :span="24">
            <el-form-item label="报价截止日期">
              <span>{{dataForm.project.bidEndDate}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="期望收货日期">
              <span>{{dataForm.project.expectDeliveryDate}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="收货地址">
              <span>{{dataForm.project.addressValue}}</span>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="mainContent">
        <h3 class="title">供应商要求</h3>
        <el-row class="marginTop ">
          <el-col :span="24">
            <el-form-item label="报价要求">
              <span v-if="dataForm.project.condition">{{dataForm.project.condition.quoteRequirement}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="支付方式">
              <span v-if="dataForm.project.condition">{{dataForm.project.condition.paymentMethod}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="交易方式">
              <span v-if="dataForm.project.condition">{{dataForm.project.condition.transactionMode}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="账期结算日">
              <span v-if="dataForm.project.condition">{{dataForm.project.condition.settlementDayType}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="发票要求">
              <span v-if="dataForm.project.condition">{{dataForm.project.condition.invoiceRequirement}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="经营地址">
              <span v-if="dataForm.project.condition">{{dataForm.project.condition.addressValue}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="注册资金">
              <span v-if="dataForm.project.condition && dataForm.project.condition.registeredCapital">{{dataForm.project.condition.registeredCapital}}万元及以上</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="证照要求">
              <span v-if="dataForm.project.condition">{{dataForm.project.condition.certifications}}</span>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="mainContent">
        <h3 class="title">补充说明</h3>
        <el-row class="marginTop">
          <el-col :span="24">
            <el-form-item label="补充说明" class="ueditor-el" prop="supplement">
              <editor ref="ueditor" class="ueditor" :editread="true" :content = "content"></editor>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="添加附件" class="fileDownload dynamicFile">
              <file-download :fileArrays="fileInformations" fileType="1"></file-download>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="footerDetailBtn">
        <el-button class="detailBtn returnBtn" @click="$router.go(-1)">返回</el-button>
        <el-button class="detailBtn" @click="backToDraft" type="primary">撤回报价</el-button>
      </div>
      <el-dialog :title="dialogLookName" width="668px" :visible.sync="dialogFormVisible">
        <el-form-item label="报价" label-width="60px" v-if="dialogLookName === '报价及附件' && dataForm.subjectMatterResponseList">
          <span>{{dataForm.subjectMatterResponseList[dialogIndex].offerPrice}}（报价含税）</span>
        </el-form-item>
        <el-form-item label="备注" label-width="60px" v-if="dataForm.subjectMatterResponseList">
          <el-input v-model="dataForm.subjectMatterResponseList[dialogIndex].offerRemark" type="textarea" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="附件" label-width="60px" class="fileDownload dynamicFile">
          <file-download :fileArrays="attachInformations[dialogIndex]" fileType="3"></file-download>
        </el-form-item>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
        </div>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
import * as region from '@/assets/js/region'
import * as condition from '@/assets/js/condition'
import editor from '@/components/ueditor/ueditor.vue'
import fileDownload from '@/components/download/file-download'
import {projectResponse} from '@/api/project/directPurchase/projectResponse'
export default {
  components: {
    editor,
    fileDownload
  },
  data () {
    return {
      dataForm: {
        project: {}
      },
      indexMethod: 1, // 序号
      content: '',
      tableData: [
        {
          date: '2019-11-11',
          number: '测试项目',
          money: '4000'
        },
        {
          date: '2019-11-11',
          number: '测试项目',
          money: ''
        },
        {
          date: '2019-11-11',
          number: '测试项目',
          money: '4000'
        }
      ],
      fileInformations: [
        {
          fileName: '营业执照.jpg',
          businessType: '7'
        },
        {
          fileName: '连锁执照.jpg',
          businessType: '7'
        },
        {
          fileName: '营业执照.jpg',
          businessType: '7'
        },
        {
          fileName: '连锁执照.jpg',
          businessType: '7'
        },
        {
          fileName: '营业执照.jpg',
          businessType: '7'
        },
        {
          fileName: '连锁执照.jpg',
          businessType: '7'
        }
      ], // 报价补充说明附件
      attachInformations: [], // 物料报价附件
      dialogLookName: '', // 弹框名字
      dialogFormVisible: false,
      addressOptions: region.CityInfo,
      // 供应商要求
      supplierRequireOptions: condition.supplierCondition,
      dialogIndex: 0, // 弹框序号
      totalPurchasePrice: 0, // 采购总金额
      totalOfferPrice: 0, // 报价总金额
      subjectNumberNum: 0, // 物料数目
      offeredNum: 0 // 已报价物料数目
    }
  },
  methods: {
    handleCheck (scope, name) {
      this.dialogIndex = scope.$index
      this.dialogLookName = name
      this.dialogFormVisible = true
    },
    // 数据初始化
    detail () {
      let url = {
        projectId: this.$route.params.objectId,
        enterpriseId: this.$store.getters.authUser.enterpriseId
      }
      projectResponse.directDetail(url).then((res) => {
        if (res.data.resCode === '0000') {
          this.dataForm = res.data.projectResponse
          // 设置收货地址和供货地址
          this.setAddress()
          // 设置供应商条件
          this.setCondition()
          // 设置项目物料报价
          this.tableData = this.dataForm.subjectMatterResponseList
          this.fileInformations = this.dataForm.fileInformationList
          if (this.dataForm.subjectMatterResponseList) {
            this.dataForm.subjectMatterResponseList.forEach((item, index) => {
              this.attachInformations.push([])
              this.attachInformations[index] = this.dataForm.subjectMatterResponseList[index].fileInformationList
              this.totalPurchasePrice += Number(item.subjectMatterInst.totalPrice)
              this.totalOfferPrice += Number(item.offerPrice)
              this.subjectNumberNum += 1
              if (item.isConfirm === 1) {
                this.offeredNum += 1
              }
            })
          }
          // 补充说明
          if (this.dataForm.supplement) {
            this.$refs.ueditor.setContent(this.dataForm.supplement)
          }
        }
      })
    },
    // 根据地址id设置地址名称
    setAddress () {
      // 采购商收货地址
      if (this.dataForm.project.addressIds) {
        let addressGroup = this.dataForm.project.addressIds.split(',')
        let addressValue = ''
        this.addressOptions.forEach((first) => {
          if (first.value === Number(addressGroup[0])) {
            addressValue += first.label
            first.children.forEach((second) => {
              if (second.value === Number(addressGroup[1])) {
                addressValue += second.label
                second.children.forEach((third) => {
                  if (third.value === Number(addressGroup[2])) {
                    addressValue += third.label
                  }
                })
              }
            })
          }
        })
        if (this.dataForm.project.address) {
          addressValue += this.dataForm.project.address
        }
        this.$set(this.dataForm.project, 'addressValue', addressValue)
      }
      // 供应商供货地址
      if (this.dataForm.addressIds) {
        let addressGroup = this.dataForm.addressIds.split(',')
        let addressValue = ''
        this.addressOptions.forEach((first) => {
          if (first.value === Number(addressGroup[0])) {
            addressValue += first.label
            first.children.forEach((second) => {
              if (second.value === Number(addressGroup[1])) {
                addressValue += second.label
                second.children.forEach((third) => {
                  if (third.value === Number(addressGroup[2])) {
                    addressValue += third.label
                  }
                })
              }
            })
          }
        })
        if (this.dataForm.project.address) {
          addressValue += this.dataForm.project.address
        }
        this.$set(this.dataForm, 'addressValue', addressValue)
      }
      // 供应商要求经营地址
      if (this.dataForm.project.condition.addressIds) {
        let addressGroup = this.dataForm.project.condition.addressIds.split(',')
        let addressValue = ''
        this.addressOptions.forEach((first) => {
          if (first.value === Number(addressGroup[0])) {
            addressValue += first.label
            first.children.forEach((second) => {
              if (second.value === Number(addressGroup[1])) {
                addressValue += second.label
                second.children.forEach((third) => {
                  if (third.value === Number(addressGroup[2])) {
                    addressValue += third.label
                  }
                })
              }
            })
          }
        })
        this.$set(this.dataForm.project.condition, 'addressValue', addressValue)
      }
    },
    // 撤回报价
    backToDraft () {
      this.$confirm('是否确认撤回此报价？', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        this.dataForm.status = 1
        let projectResponseForm = JSON.parse(JSON.stringify(this.dataForm))
        delete projectResponseForm.project
        delete projectResponseForm.condition
        projectResponse.saveOffer(projectResponseForm).then((res) => {
          if (res.data.resCode === '0000') {
            this.$message({
              message: '此报价已撤回至草稿箱',
              type: 'success'
            })
            this.$router.go(-1)
          }
        })
      }).catch(() => {
        return false
      })
    },
    setCondition () {
      if (this.dataForm.project.condition.transactionMode) {
        this.supplierRequireOptions.transactionMode.forEach(item => {
          if (Number(item.value) === this.dataForm.project.condition.transactionMode) {
            this.dataForm.project.condition.transactionMode = item.label
          }
        })
      }
      if (this.dataForm.project.condition.settlementDayType) {
        if (this.dataForm.project.condition.settlementDayType === 1) {
          this.dataForm.project.condition.settlementDayType = '每月' + this.dataForm.project.condition.settlementDay + '日结算'
        }
        if (this.dataForm.project.condition.settlementDayType === 2) {
          this.dataForm.project.condition.settlementDayType = '收货后' + this.dataForm.project.condition.settlementDay + '日结算'
        }
      }
      if (this.dataForm.project.condition.invoiceRequirement) {
        this.supplierRequireOptions.invoiceRequirement.forEach(item => {
          if (Number(item.value) === this.dataForm.project.condition.invoiceRequirement) {
            this.dataForm.project.condition.invoiceRequirement = item.label
          }
        })
      }
    }
  },
  mounted () {
    this.detail()
  },
  filters: {
    numFilter (value) {
      let realVal = ''
      if (value) {
        realVal = parseFloat(value).toFixed(2)
      } else {
        realVal = '--'
      }
      return realVal
    }
  }
}
</script>

<style lang="less">
  #offerDetail{
    .prompt-title{
      width: 98%;
      height: 26px;
      background: #fff6f0;
      padding: 0 12px;
      border: 1px solid #ff8c40;
      box-sizing: border-box;
      color: #ff8c40;
      margin: 10px auto;
    }
    .prompt-title>img{
      display: inline-block;
      vertical-align: middle;
    }
    .prompt-title>span{
      display: inline-block;
      vertical-align: middle;
    }
    .handle-none{
      border-bottom: none;
    }
    .tablemain .el-table td {
      border-bottom: 1px solid #e6e6e6;
      padding: 6px 0 6px 0;
      color: #333333;
    }
    .marginTop{
      margin-top: 15px;
    }
    .product-table{
      padding-bottom: 15px;
    }
    .ueditor{
      width: 99%;
    }
    .ueditor-el .edui-editor-toolbarboxouter{
      display: none;
    }
    .fileDownload .el-form-item__label{
      line-height: 40px;
    }
    .fileDownload .el-form-item__content>div{
      width: 70%;
    }
  }
</style>
