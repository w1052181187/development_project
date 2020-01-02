<template>
  <div class="offerDetail details blueCon" id="offerDetail">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/'}">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/'}">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/projectDynamic'}">项目动态</el-breadcrumb-item>
      <el-breadcrumb-item>直采详情</el-breadcrumb-item>
    </el-breadcrumb>
    <el-form :model="dataForm" label-width="160px">
      <div class="mainContent">
        <h3 class="title">项目信息</h3>
        <el-row class="marginTop">
          <el-col :span="24">
            <el-form-item label="项目名称">
              <span>{{dataForm.name}}</span>
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
<!--          <el-col :span="24">-->
<!--            <el-form-item label="联系信息">-->
<!--              <span>{{dataForm.name}}</span>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
        </el-row>
      </div>
      <div class="mainContent">
        <h3 class="title">产品信息</h3>
        <p class="handle handle-none">
<!--          （已报价4种物料，报价总金额：<i>￥50000.00</i>）-->
          <span>共{{subjectNumberNum}}种物料，采购总金额：<i>￥{{totalPurchasePrice}}</i></span>
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
              prop="subjectMatter.materielNumber"
              label="物料号">
            </el-table-column>
            <el-table-column
              prop="subjectMatter.operationCategoryNames"
              label="物料类目">
            </el-table-column>
            <el-table-column
              prop="subjectMatter.name"
              label="物料名称">
            </el-table-column>
            <el-table-column
              prop="subjectMatter.brand"
              label="品牌">
            </el-table-column>
            <el-table-column
              prop="subjectMatter.specification"
              label="型号">
            </el-table-column>
            <el-table-column
              prop="subjectMatter.description"
              label="物料描述">
            </el-table-column>
            <el-table-column
              prop="amount"
              label="采购量">
            </el-table-column>
            <el-table-column
              prop="totalPrice"
              label="预算总额">
            </el-table-column>
            <el-table-column
              prop="date"
              align="center"
              label="备注及附件">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="handleCheck(scope)">查看</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <div class="mainContent">
        <h3 class="title">采购信息</h3>
        <el-row class="marginTop">
          <el-col :span="12">
            <el-form-item label="报名截止日期">
              <span>{{dataForm.bidEndDate}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="期望收货日期" prop="name">
              <span>{{dataForm.expectDeliveryDate}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12" class="question_mark">
            <el-form-item label="单件报价"  prop="name">
              <i class="tip" title="供应商可对一件或几件物料进行单独报价"></i>
              <span>{{dataForm.partialBid}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="收货地址" prop="name">
              <span>{{dataForm.addressValue}}</span>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="mainContent">
        <h3 class="title">供应商要求</h3>
        <el-row class="marginTop ">
          <el-col :span="24">
            <el-form-item label="报价要求">
              <span v-if="dataForm.condition">{{dataForm.condition.quoteRequirement}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="支付方式">
              <span v-if="dataForm.condition">{{dataForm.condition.paymentMethod}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="交易方式">
              <span v-if="dataForm.condition">{{dataForm.condition.transactionMode}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="账期结算日">
              <span v-if="dataForm.condition">{{dataForm.condition.settlementDayType}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="发票要求">
              <span v-if="dataForm.condition">{{dataForm.condition.invoiceRequirement}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="经营地址">
              <span v-if="dataForm.condition">{{dataForm.condition.addressValue}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="注册资金">
              <span v-if="dataForm.project.condition && dataForm.project.condition.registeredCapital">{{dataForm.project.condition.registeredCapital}}万元及以上</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="证照要求">
              <span v-if="dataForm.condition">{{dataForm.condition.certifications}}</span>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="mainContent">
        <h3 class="title">补充说明</h3>
        <el-row class="marginTop">
          <el-col :span="24">
            <el-form-item label="补充说明" class="ueditor-el">
              <editor ref="ueditor" class="ueditor" :editread="true" :content = "dataForm.remark"></editor>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="添加附件" class="fileDownload dynamicFile">
              <file-download :fileArrays="fileInformations" fileType="7"></file-download>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="footerDetailBtn">
        <el-button class="detailBtn returnBtn" @click="$router.go(-1)">返回</el-button>
        <el-button v-if="projectResponseStatus === 0 || projectResponseStatus === 1 || projectResponseStatus === 4" class="detailBtn" @click="toDetail" type="primary">报价</el-button>
      </div>
      <el-dialog title="备注及附件" width="668px" :visible.sync="dialogFormVisible">
        <el-form-item label="备注" label-width="60px">
          <el-input v-model="tableData[dialogIndex].remark" type="textarea" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="附件" label-width="60px" class="fileDownload dynamicFile">
          <file-download :fileArrays="tableData[dialogIndex].attachments" fileType="3"></file-download>
        </el-form-item>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
        </div>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
import editor from '@/components/ueditor/ueditor.vue'
import fileDownload from '@/components/download/file-download'
import {directPurchase} from '@/api/project/directPurchase/directPurchase'
import {projectResponse} from '@/api/project/directPurchase/projectResponse'
import * as region from '@/assets/js/region'
import * as condition from '@/assets/js/condition'
export default {
  components: {
    editor,
    fileDownload
  },
  data () {
    return {
      dataForm: {
        name: '山西众泰集团有限公司'
      },
      // 地址三级联动
      addressOptions: region.CityInfo,
      // 供应商要求
      supplierRequireOptions: condition.supplierCondition,
      // 序号
      indexMethod: 1,
      content: '',
      tableData: [
        {
          date: '2019-11-11',
          number: '测试项目'
        },
        {
          date: '2019-11-11',
          number: '测试项目'
        },
        {
          date: '2019-11-11',
          number: '测试项目'
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
        }
      ],
      dialogFormVisible: false,
      dialogIndex: 0, // 弹框序号
      totalPurchasePrice: 0, // 采购总金额
      subjectNumberNum: 0, // 物料数目
      projectResponseStatus: '' // 供应商项目报价状态
    }
  },
  methods: {
    handleCheck (scope) {
      this.dialogIndex = scope.$index
      this.dialogFormVisible = true
    },
    list () {
      directPurchase.getOne(this.$route.params.objectId).then((res) => {
        if (res.data.resCode === '0000') {
          this.dataForm = res.data.project
          // 设置项目物料报价
          this.tableData = this.dataForm.bidLotsList[0].insts
          this.tableData.forEach((item, index) => {
            this.totalPurchasePrice += Number(item.totalPrice)
            this.subjectNumberNum += 1
          })
          // 是否允许部分报价
          if (this.dataForm.partialBid === 1) {
            this.dataForm.partialBid = '允许单件报价'
          } else if (this.dataForm.partialBid === 0) {
            this.dataForm.partialBid = '不允许单件报价'
          }
          // 设置地址
          this.setAddress()
          // 设置供应商条件
          this.setCondition()
          // 补充说明
          if (this.dataForm.remark) {
            this.$refs.ueditor.setContent(this.dataForm.remark)
          }
        }
      })
    },
    responseStatus () {
      let params = {
        projectId: this.$route.params.objectId,
        enterpriseId: this.$store.getters.authUser.enterpriseId
      }
      projectResponse.getProjectResponseStatus(params).then((res) => {
        if (res.data.resCode === '0000') {
          this.projectResponseStatus = res.data.projectResponseStatus
          console.log(this.projectResponseStatus)
        }
      })
    },
    toDetail () {
      this.$router.push({path: `/projectDynamic/directPurchase/offer/${this.$route.params.objectId}`})
    },
    setAddress () {
      // 收货地址
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
        if (this.dataForm.address) {
          addressValue += this.dataForm.address
        }
        this.$set(this.dataForm, 'addressValue', addressValue)
      }
      // 供应商要求经营地址
      if (this.dataForm.condition && this.dataForm.condition.addressIds) {
        let addressGroup = this.dataForm.condition.addressIds.split(',')
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
        this.$set(this.dataForm.condition, 'addressValue', addressValue)
      }
    },
    setCondition () {
      if (!this.dataForm.condition) {
        return
      }
      // 设置供应商要求
      if (this.dataForm.condition.transactionMode) {
        this.supplierRequireOptions.transactionMode.forEach(item => {
          if (Number(item.value) === this.dataForm.condition.transactionMode) {
            this.dataForm.condition.transactionMode = item.label
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
      if (this.dataForm.condition.invoiceRequirement) {
        this.supplierRequireOptions.invoiceRequirement.forEach(item => {
          if (Number(item.value) === this.dataForm.condition.invoiceRequirement) {
            this.dataForm.condition.invoiceRequirement = item.label
          }
        })
      }
    }
  },
  mounted () {
    this.list()
    this.responseStatus()
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
    .question_mark .el-form-item__label{
      padding-right: 30px;
    }
    .question_mark i.tip{
      width: 14px;
      height: 14px;
      position: absolute;
      left: -28px;
      top: 10px;
      background: url("../../../../../static/images/question_mark_icon.png") center center no-repeat;
    }
    .fileDownload .el-form-item__content>div{
      width: 70%;
    }
  }
</style>
