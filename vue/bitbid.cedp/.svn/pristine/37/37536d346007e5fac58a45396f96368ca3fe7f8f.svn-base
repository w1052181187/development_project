<template>
  <div class="blueCon" id="directPurchaseAdd">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/'}">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/'}">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item>直采报价</el-breadcrumb-item>
    </el-breadcrumb>
    <el-form :model="dataForm" :rules="rules" ref="dataForm" label-width="160px" :validate-on-rule-change="true">
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
              <span v-if="dataForm.project.contactShow === 2">***</span>
              <span v-else>{{dataForm.project.contactName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="联系方式">
              <span  v-if="dataForm.project.contactShow === 2">***********</span>
              <span v-else>{{dataForm.project.contactPhone}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24" v-if="dataForm.project.contactShow === 2">
            <el-form-item label="联系信息">
              <span>报价后显示</span>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="mainContent">
        <h3 class="title">报价信息</h3>
        <el-row class="marginTop">
          <el-col :span="12">
            <el-form-item label="报价公司名称">
              <span>{{this.$store.getters.authUser.enterpriseName}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系人" prop="contactName">
              <el-input  v-model="dataForm.contactName" placeholder="请输入联系人"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系方式" prop="contactPhone">
              <el-input  v-model="dataForm.contactPhone" placeholder="请输入联系方式"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="mainContent">
        <h3 class="title">产品信息</h3>
        <p class="handle handle-none">
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
              prop="offerPrice"
              label="报价总额">
              <template slot-scope="scope">
                <span v-if="scope.row && (scope.row.isConfirm === 1 || scope.row.isConfirm === 2)" class="moneyOffer">{{scope.row.offerPrice}}</span>
                <span v-else>未报价</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="date"
              align="center"
              label="备注及附件">
              <template slot-scope="scope">
                <el-button type="text" size="small" class="operation-btn dynamic-btn" @click="handleCheck(scope, '备注及附件')">查看</el-button>
              </template>
            </el-table-column>
            <el-table-column
              prop="number"
              align="center"
              label="报价及附件">
              <template slot-scope="scope">
                <el-button type="text" size="small" v-if="scope.row && (scope.row.isConfirm === 1 || scope.row.isConfirm === 2)" @click="handleCheck(scope, '报价', 'update')">修改</el-button>
                <el-button type="text" size="small" v-else @click="handleCheck(scope, '报价', 'add')">报价</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <div class="mainContent">
        <h3 class="title">采购信息</h3>
        <div class="prompt-title">
          <img src="../../../../../static/images/gth.png" alt="">
          <span>请仔细确认采购信息</span>
        </div>
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
        <div class="prompt-title">
          <img src="../../../../../static/images/gth.png" alt="">
          <span>请仔细确认供应商要求</span>
        </div>
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
        <h3 class="title">供货信息</h3>
        <div class="prompt-title">
          <img src="../../../../../static/images/gth.png" alt="">
          <span>请仔细确认供应商供货信息</span>
        </div>
        <el-row class="marginTop">
          <el-col :span="24">
            <el-form-item label="生产备货周期" class="offerUpdateInput" prop="producetionTime">
              <el-input v-model="dataForm.producetionTime"></el-input>
              天
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="交货期" class="offerUpdateInput" prop="leadTime">
              确认选择后
              <el-input v-model="dataForm.leadTime"></el-input>
              天交货至指定地点
            </el-form-item>
          </el-col>
<!--          <el-col :span="24">-->
<!--            <el-form-item label="预估运费">-->
<!--              <span>{{dataForm.name}}</span>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="供货地址" prop="addressIds">
              <el-cascader expand-trigger="hover"
                           :options="addressOptions"
                           v-model="selectedcityOptions"
                           @change="handleCityChange"
                           clearable>
              </el-cascader>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label=" " prop="address">
              <el-input  v-model="dataForm.address" placeholder="请输入详细地址"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="mainContent">
        <h3 class="title">补充说明</h3>
        <el-row style="margin-top: 20px">
          <el-col :span="24">
            <el-form-item label="补充说明">
              <editor ref="ueditor" class="ueditor" :height= '200' :editread="false" :content = "dataForm.supplement"></editor>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="添加附件" prop="fileValue" class="attachment">
              <span class="tip">提示：产品规格书、样品图等（支持jpg、jpeg、gif、doc等格式）</span>
              <uploadFile class="blueUpload" fileMaxNum="5" @uploadSuccess="uploadSuccess" @deleteSuccess="deleteSuccess" fileType="1" :fileArrays="fileInformations" isImage="0"></uploadFile>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="footerDetailBtn">
        <el-button class="detailBtn returnBtn" @click="saveToDraft('dataForm')">保存至草稿箱</el-button>
        <el-button class="detailBtn" type="primary" @click="offerBtn('dataForm')">提交报价</el-button>
      </div>
      <el-dialog :title="dialogLookName" width="668px" :visible.sync="dialogFormVisible">
        <el-form-item label="报价" label-width="60px" v-if="dialogLookName === '报价' && dataForm.subjectMatterResponseList[this.dialogIndex].isConfirm !== 2" class="offerInput">
          <el-input  v-model="dataForm.subjectMatterResponseList[dialogIndex].offerPrice" placeholder="请输入报价"></el-input>
          <span style="color: #c5c5c5;margin-left: 5px">报价含税、报价包含运费</span>
        </el-form-item>
        <el-form-item label="报价" label-width="60px" v-if="dialogLookName === '报价' && dataForm.subjectMatterResponseList[this.dialogIndex].isConfirm === 2" class="offerInput">
          <el-input  v-model="dataForm.tempList[dialogIndex].offerPrice" placeholder="请输入报价"></el-input>
          <span style="color: #c5c5c5;margin-left: 5px">报价含税、报价包含运费</span>
        </el-form-item>
        <el-form-item label="备注" label-width="60px">
          <el-input v-model="dataForm.subjectMatterResponseList[dialogIndex].offerRemark" type="textarea" v-if="dialogLookName === '报价' && dataForm.subjectMatterResponseList[this.dialogIndex].isConfirm !== 2"></el-input>
          <el-input v-model="dataForm.tempList[dialogIndex].offerRemark" type="textarea" v-if="dialogLookName === '报价' && dataForm.subjectMatterResponseList[this.dialogIndex].isConfirm === 2"></el-input>
          <el-input v-model="dataForm.name" type="textarea" autocomplete="off" readonly v-if="dialogLookName === '备注及附件'"></el-input>
        </el-form-item>
        <el-form-item label="附件" label-width="60px" class="attachment">
          <span v-if="dialogLookName === '报价'" class="tip">提示：产品规格书、样品图等（支持jpg、jpeg、gif、doc等格式）</span>
          <uploadFile v-if="dialogLookName === '报价' && dataForm.subjectMatterResponseList[this.dialogIndex].isConfirm !== 2" class="blueUpload" @uploadSuccess="attachUploadSuccess" @deleteSuccess="attachDeleteSuccess" fileType="3" :fileArrays="attachInformations[dialogIndex]" isImage="0"></uploadFile>
          <uploadFile v-if="dialogLookName === '报价' && dataForm.subjectMatterResponseList[this.dialogIndex].isConfirm === 2" class="blueUpload" @uploadSuccess="attachUploadSuccess" @deleteSuccess="attachDeleteSuccess" fileType="3" :fileArrays="tempAttachInformations[dialogIndex]" isImage="0"></uploadFile>
          <file-download v-if="dialogLookName === '备注及附件'" :fileArrays="attachInformations[dialogIndex]" fileType="3"></file-download>
        </el-form-item>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" class="dynamic-blue" @click="dialogFormVisible = false" v-if="dialogLookName !== '报价'">确认</el-button>
          <el-button type="primary" class="dynamic-blue" @click="confirmOffer" v-if="offerStatus === 'add'">确认报价</el-button>
          <el-button type="primary" class="dynamic-blue" @click="updateOffer" v-if="offerStatus === 'update'">确认修改</el-button>
          <el-button type="primary" class="dynamic-blue" @click="emptyOffer" v-if="offerStatus === 'update'">清空报价</el-button>
        </div>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
import * as region from '@/assets/js/region'
import editor from '@/components/ueditor/ueditor.vue'
import uploadFile from '@/components/upload/publicFileUpload'
import {projectResponse} from '@/api/project/directPurchase/projectResponse'
import {validPhoneUser, validateWhoInteger, validateMoney} from '@/assets/js/validate'
import * as condition from '@/assets/js/condition'
export default {
  name: 'offer',
  components: {
    editor,
    uploadFile
  },
  data () {
    // 验证非负整数
    let nonNegative = (rule, value, callback) => {
      if (validateWhoInteger(value) || Number(value) === 0) {
        callback()
      } else if (!value) {
        callback()
      } else {
        callback(new Error('请输入0或正整数！'))
      }
    }
    // 钱数
    let validMoney = (rule, value, callback) => {
      if (validateMoney(value) || Number(value) === 0) {
        callback()
      } else if (!value) {
        callback()
      } else {
        callback(new Error('请输入0或正整数！'))
      }
    }
    return {
      dataForm: {
        project: {},
        subjectMatterResponse: {},
        condition: {},
        checkbox: [],
        deliveryCheckbox: [],
        radio: '',
        paymentRadio: ''
      },
      indexMethod: 1, // 序号
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
      options: [
        {
          value: 1,
          label: 1
        },
        {
          value: 2,
          label: 2
        },
        {
          value: 3,
          label: 3
        }
      ],
      rangeOptions: [
        {
          value: 1,
          label: 7
        },
        {
          value: 2,
          label: 30
        },
        {
          value: 3,
          label: 60
        },
        {
          value: 4,
          label: 90
        },
        {
          value: 5,
          label: 120
        },
        {
          value: 6,
          label: 180
        },
        {
          value: 7,
          label: 360
        }
      ],
      rules: {
        contactName: [
          { required: true, message: '联系人不能为空', trigger: 'blur' }
        ],
        contactPhone: [
          { required: true, message: '联系方式不能为空', trigger: 'blur' },
          {validator: validPhoneUser, trigger: 'blur'}
        ],
        producetionTime: [
          { required: true, message: '请输入生产备货周期', trigger: 'blur' },
          {validator: nonNegative, trigger: 'change'}
        ],
        leadTime: [
          { required: true, message: '请输入交货期', trigger: 'blur' },
          {validator: nonNegative, trigger: 'change'}
        ],
        offerPrice: [
          {validator: validMoney, trigger: 'change'}
        ],
        addressIds: [
          { required: true, message: '请选择供货地址', trigger: 'blur' }
        ],
        address: [
          { required: true, message: '请选择供货详细地址', trigger: 'blur' }
        ]
      },
      // 地址三级联动
      addressOptions: region.CityInfo,
      // 供应商要求
      supplierRequireOptions: condition.supplierCondition,
      selectedcityOptions: [],
      // 补充说明附件
      fileInformations: [],
      // 物料报价附件
      attachInformations: [],
      // 存放修改确认前临时附件
      tempAttachInformations: [],
      content: '',
      offerStatus: '', // 产品信息报价状态
      dialogLookName: '', // 弹框名字
      dialogFormVisible: false,
      dialogIndex: '', // 弹框显示物料报价序号
      totalPurchasePrice: 0, // 采购总金额
      subjectNumberNum: 0, // 物料数目
      totalOfferPrice: 0, // 报价总金额
      offeredNum: 0 // 已报价物料数目
    }
  },
  methods: {
    // 报价
    handleCheck (scope, name, status) {
      this.dialogIndex = scope.$index
      this.dialogLookName = name
      this.offerStatus = status
      this.dialogFormVisible = true
      if (status === 'update') {
        this.dataForm.subjectMatterResponseList[this.dialogIndex].isConfirm = 2
      }
    },
    // 上传文件
    uploadSuccess (file, fileType) {
      this.fileInformations.push(file)
    },
    // 删除文件
    deleteSuccess (fileId, fileType) {
      this.fileInformations = this.fileInformations.filter(item => item.relativePath !== fileId)
    },
    // 上传报价文件
    attachUploadSuccess (file, fileType) {
      if (this.dataForm.subjectMatterResponseList[this.dialogIndex].isConfirm === 2) {
        this.tempAttachInformations[this.dialogIndex].push(file)
      } else {
        this.attachInformations[this.dialogIndex].push(file)
      }
    },
    // 删除报价文件
    attachDeleteSuccess (fileId, fileType) {
      if (this.dataForm.subjectMatterResponseList[this.dialogIndex].isConfirm === 2) {
        this.tempAttachInformations[this.dialogIndex] = this.tempAttachInformations[this.dialogIndex].filter(item => item.relativePath !== fileId)
      } else {
        this.attachInformations[this.dialogIndex] = this.attachInformations[this.dialogIndex].filter(item => item.relativePath !== fileId)
      }
    },
    // 设置地址id
    handleCityChange (value) {
      if (value.length === 0) {
        this.dataForm.addressIds = null
      } else {
        this.dataForm.addressIds = value[0] + ',' + value[1] + ',' + value[2]
      }
    },
    detail () {
      let url = {
        projectId: this.$route.params.objectId,
        enterpriseId: this.$store.getters.authUser.enterpriseId
      }
      projectResponse.directDetail(url).then((res) => {
        this.dataForm = res.data.projectResponse
        // 设置临时文件，达到确认和修改效果
        this.$set(this.dataForm, 'tempList', JSON.parse(JSON.stringify(this.dataForm.subjectMatterResponseList)))
        // 设置采购商收货地址
        this.setAddress()
        // 设置项目物料报价
        this.tableData = this.dataForm.subjectMatterResponseList
        // 设置供货商供货地址
        if (this.dataForm.addressIds) {
          let addressGroup = this.dataForm.addressIds.split(',')
          this.selectedcityOptions.push(Number(addressGroup[0]))
          this.selectedcityOptions.push(Number(addressGroup[1]))
          this.selectedcityOptions.push(Number(addressGroup[2]))
        }
        // 设置供应商要求
        this.setCondition()
        // 补充说明文件信息
        if (this.dataForm.fileInformationList) {
          this.fileInformations = this.dataForm.fileInformationList
        }
        // 补充说明
        if (this.dataForm.supplement) {
          this.$refs.ueditor.setContent(this.dataForm.supplement)
        }
        // 报价附件信息
        if (this.dataForm.subjectMatterResponseList) {
          this.dataForm.subjectMatterResponseList.forEach((item, index) => {
            this.totalPurchasePrice += Number(item.subjectMatterInst.totalPrice)
            this.totalOfferPrice += Number(item.offerPrice)
            this.subjectNumberNum += 1
            if (item.isConfirm === 1) {
              this.offeredNum += 1
            }
            this.attachInformations.push([])
            this.tempAttachInformations.push([])
            if (this.dataForm.subjectMatterResponseList[index].fileInformationList) {
              this.dataForm.subjectMatterResponseList[index].fileInformationList.forEach((item2, index2) => {
                this.attachInformations[index].push(item2)
                this.tempAttachInformations[index].push(item2)
              })
            }
          })
        }
      })
    },
    // 根据地址id设置地址名称
    setAddress () {
      // 收货地址
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
    },
    // 保存至草稿箱
    saveToDraft (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.dataForm.status = 1
          this.save()
        }
      })
    },
    // 直接报价
    offerBtn (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.dataForm.status = 2
          this.save()
        }
      })
    },
    save () {
      if (this.$refs.ueditor.getContent().length !== 0) {
        this.dataForm.supplement = this.$refs.ueditor.getContent()
      } else {
        this.dataForm.supplement = ''
      }
      if (this.fileInformations) {
        this.dataForm.fileInformationList = this.fileInformations
      }
      this.attachInformations.forEach((item, index) => {
        if (this.attachInformations[index]) {
          this.dataForm.subjectMatterResponseList[index].fileInformationList = item
        }
      })
      let projectResponseForm = JSON.parse(JSON.stringify(this.dataForm))
      delete projectResponseForm.project
      // 处理未提交报价数据
      let offerMatterNum = 0
      projectResponseForm.subjectMatterResponseList.forEach((item) => {
        if (Number(item.isConfirm) === 2) {
          item.isConfirm = 1
          offerMatterNum++
        } else if (Number(item.isConfirm) === 0) {
          item.offerPrice = ''
          item.offerRemark = ''
          item.fileInformationList = []
        } else if (Number(item.isConfirm) === 1) {
          offerMatterNum++
        }
      })
      // 不允许部分报价：报价单必须选择所有物料报价
      if (this.dataForm.project.partialBid === 0 && offerMatterNum < projectResponseForm.subjectMatterResponseList.length) {
        this.$message({
          message: '仍有物料未报价，请全部报价',
          type: 'warning'
        })
        return
        // 允许部分报价：报价单可选择至少一个物料进行报价
      } else if (this.dataForm.project.partialBid === 1 && offerMatterNum === 0) {
        this.$message({
          message: '请选择至少一个物料进行报价',
          type: 'warning'
        })
        return
      }
      projectResponse.saveOffer(projectResponseForm).then((res) => {
        if (res.data.resCode === '0000') {
          this.$router.push({path: `/projectDynamic`})
        }
      })
    },
    validMoney (value) {
      if (!value) {
        this.$message({
          message: '请输入报价',
          type: 'warning'
        })
        return false
      } else if (!validateMoney(value)) {
        this.$message({
          message: '报价格式输入有误，请重新录入！',
          type: 'warning'
        })
        return false
      } else {
        return true
      }
    },
    // 确认报价
    confirmOffer () {
      let record = this.dataForm.subjectMatterResponseList[this.dialogIndex]
      if (this.validMoney(record.offerPrice)) {
        this.totalOfferPrice += Number(record.offerPrice)
        this.offeredNum += 1
        this.dataForm.subjectMatterResponseList[this.dialogIndex].isConfirm = 1
        this.$set(this.dataForm.tempList[this.dialogIndex], 'offerPrice', record.offerPrice)
        this.$set(this.dataForm.tempList[this.dialogIndex], 'offerRemark', record.offerRemark)
        this.attachInformations[this.dialogIndex].forEach((file) => {
          this.tempAttachInformations[this.dialogIndex].push(file)
        })
        this.dialogFormVisible = false
      }
    },
    // 修改报价
    updateOffer () {
      let record = this.dataForm.tempList[this.dialogIndex]
      if (this.validMoney(record.offerPrice)) {
        this.dataForm.subjectMatterResponseList[this.dialogIndex].isConfirm = 1
        this.$set(this.dataForm.subjectMatterResponseList[this.dialogIndex], 'offerPrice', record.offerPrice)
        this.$set(this.dataForm.subjectMatterResponseList[this.dialogIndex], 'offerRemark', record.offerRemark)
        this.totalOfferPrice = 0
        this.dataForm.subjectMatterResponseList.forEach((item) => {
          this.totalOfferPrice += Number(item.offerPrice)
        })
        this.attachInformations[this.dialogIndex] = []
        this.tempAttachInformations[this.dialogIndex].forEach((file) => {
          this.attachInformations[this.dialogIndex].push(file)
        })
        this.dialogFormVisible = false
      }
    },
    // 清空报价
    emptyOffer () {
      let record = this.dataForm.subjectMatterResponseList[this.dialogIndex]
      this.totalOfferPrice -= Number(record.offerPrice)
      this.offeredNum -= 1
      record.isConfirm = 0
      record.offerPrice = ''
      record.offerRemark = ''
      this.attachInformations[this.dialogIndex] = []
      this.tempAttachInformations[this.dialogIndex] = []
      this.$set(this.dataForm.tempList[this.dialogIndex], 'isConfirm', record.isConfirm)
      this.$set(this.dataForm.tempList[this.dialogIndex], 'offerPrice', record.offerPrice)
      this.$set(this.dataForm.tempList[this.dialogIndex], 'offerRemark', record.offerRemark)
      this.dialogFormVisible = false
    }
  },
  created () {
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
  #directPurchaseAdd{
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
    .marginTop{
      margin-top: 15px;
    }
    .el-date-editor{
      width: 100%;
    }
    .ueditor{
      width: 98%;
    }
    .attachment .webuploader-pick{
      width: 72px;
      height: 32px;
      line-height: 32px;
      padding-left: 20px;
      background: #009688 url("../../../../../static/images/enterprise/upload.png") 10px center no-repeat;
    }
    .attachment span.tip{
      position: absolute;
      left: 125px;
      color: #999999;
    }
    .tablemain .el-table td {
      border-bottom: 1px solid #e6e6e6;
      padding: 6px 0 6px 0;
      color: #333333;
    }
    .product-table{
      padding-bottom: 15px;
    }
    .offerInput .el-input{
      width: 30%;
    }
    .offerUpdateInput  .el-input{
      width: 100px;
    }
  }
</style>
