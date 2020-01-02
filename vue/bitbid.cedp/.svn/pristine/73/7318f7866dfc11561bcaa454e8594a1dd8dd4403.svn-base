<template>
  <div class="blueCon" id="directPurchaseAdd">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/'}">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/projectExecute'}">项目执行</el-breadcrumb-item>
      <el-breadcrumb-item>创建直采</el-breadcrumb-item>
    </el-breadcrumb>
    <el-form :model="dataForm" :rules="rules" ref="dataForm" label-width="160px" :validate-on-rule-change="true">
      <div class="mainContent">
        <h3 class="title">项目信息</h3>
        <el-row style="margin-top: 20px">
          <el-col :span="12">
            <el-form-item label="项目名称" prop="name">
              <el-input  v-model="dataForm.name" placeholder="请输入项目名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系人" prop="name">
              <el-input  v-model="dataForm.name" placeholder="请输入联系人"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系方式" prop="name">
              <el-input  v-model="dataForm.name" placeholder="请输入联系方式"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系信息" prop="name">
              <el-radio v-model="dataForm.name" label="1">公开显示</el-radio>
              <el-radio v-model="dataForm.name" label="2">报价后显示</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="mainContent">
        <h3 class="title">产品信息</h3>
        <p class="handle">
          <span>共 <i>1 </i>种物料 &nbsp;&nbsp;&nbsp;&nbsp; 采购总金额：<i>￥50000</i></span>
          <el-button class="handleBtn" type="primary">+ 从物料库选择</el-button>
        </p>
        <div class="tablemain">
          <el-table
            :data="tableData"
            style="width: 100%">
            <el-table-column
              class-name="del"
              width="30">
              <template slot-scope="scope">
                <span v-if="scope.row.index !== 1" @click="handleDel(scope)"></span>
              </template>
            </el-table-column>
            <el-table-column
              prop="name"
              label="物料号"
            >
              <template slot-scope="scope">
                <el-input v-model="scope.row.name" placeholder="请输入物料号"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="name"
              label="物料类目">
              <template slot-scope="scope">
                <el-input v-model="scope.row.name" placeholder="请输入物料类目"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="name"
              label="物料名称">
              <template slot-scope="scope">
                <el-input v-model="scope.row.name" placeholder="请输入物料名称"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="name"
              label="品牌">
              <template slot-scope="scope">
                <el-input v-model="scope.row.name" placeholder="请输入品牌"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="name"
              label="型号">
              <template slot-scope="scope">
                <el-input v-model="scope.row.name" placeholder="请输入型号"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="name"
              width="300px"
              label="物料描述">
              <template slot-scope="scope">
                <el-input v-model="scope.row.name" placeholder="请输入物料描述"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="name"
              label="采购量">
              <template slot-scope="scope">
                <el-input v-model="scope.row.name" placeholder="请输入采购量"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="name"
              width="100px"
              label="单位">
              <template slot-scope="scope">
                <el-input v-model="scope.row.name" placeholder="单位"></el-input>
              </template>
            </el-table-column>
            <el-table-column
            prop="name"
            label="预算金额">
            <template slot-scope="scope">
              <el-input v-model="scope.row.name" placeholder="请输入预算金额"></el-input>
            </template>
          </el-table-column>
            <el-table-column
              prop="name"
              label="备注及附件">
              <template slot-scope="scope">
                <el-button type="text" size="small" class="operation-btn" @click="handleHideAdd()">添加</el-button>
                <el-button type="text" size="small" class="operation-btn" @click="handleHideUpdate()">编辑</el-button>
                <el-button type="text" size="small" class="operation-btn" @click="handleHideDel()">清除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <p class="tableAddBtn" @click="handleAdd">+添加标的物</p>
        </div>
      </div>
      <div class="mainContent">
        <h3 class="title">采购信息</h3>
        <el-row style="margin-top: 20px">
          <el-col :span="12">
            <el-form-item label="报名截止日期" prop="name">
              <el-date-picker
                v-model="dataForm.name"
                type="datetime"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="期望收货日期" prop="name">
              <el-date-picker
                v-model="dataForm.name"
                type="datetime"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12" class="question_mark">
            <el-form-item label="单件报价" prop="name">
              <i class="tip" title="供应商可对一件或几件物料进行单独报价"></i>
              <el-radio v-model="dataForm.name" label="1">公开显示</el-radio>
              <el-radio v-model="dataForm.name" label="2">报价后显示</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="收货地址" prop="name">
              <el-cascader expand-trigger="hover"
                           :options="addressOptions"
                           v-model="selectedcityOptions"
                           @change="handlecityChange"
                           clearable>
              </el-cascader>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label=" " prop="name">
              <el-input  v-model="dataForm.name" placeholder="请输入详细地址"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="mainContent">
        <h3 class="title">供应商要求</h3>
        <el-row style="margin-top: 20px">
          <el-col :span="24">
            <el-form-item label="报价要求" prop="checkbox">
              <el-checkbox-group v-model="dataForm.checkbox">
                <el-checkbox label="报价含税"></el-checkbox>
                <el-checkbox label="报价包含运费"></el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="交付方式" prop="checkbox">
              <el-checkbox-group v-model="dataForm.deliveryCheckbox">
                <el-checkbox label="银行转账" value=1></el-checkbox>
                <el-checkbox label="电子银行承兑汇票"></el-checkbox>
                <p class="month" v-if="dataForm.deliveryCheckbox.indexOf('电子银行承兑汇票') !== -1">
                  承兑汇票付款期限<el-input v-model="dataForm.name"></el-input>个月</p>
                <el-checkbox label="其他"></el-checkbox>
                <p class="other" v-if="dataForm.deliveryCheckbox.indexOf('其他') !== -1">
                  <el-input v-model="dataForm.name"></el-input></p>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="交易方式" prop="radio">
              <el-radio-group v-model="dataForm.radio">
                <el-radio :label="1">货到付款</el-radio>
                <el-radio :label="2">款到发货</el-radio>
                <el-radio :label="3">账期支付</el-radio>
                <el-radio :label="4">其他</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="Number(dataForm.radio) === 3">
          <el-col :span="12">
            <el-form-item label="账期结算日" prop="paymentRadio">
              <el-radio-group v-model="dataForm.paymentRadio">
                <el-radio :label="1">指定日期</el-radio>
                <el-radio :label="2">交货日期</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="Number(dataForm.radio) === 3 && Number(dataForm.paymentRadio) === 1">
          <el-col :span="12">
            <el-form-item label="每月结算日" class="smallSelect" >
              <el-select v-model="dataForm.name" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
              <span>每月 <i>{{dataForm.name}}</i> 日结算</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="Number(dataForm.radio) === 3 && Number(dataForm.paymentRadio) === 2">
          <el-col :span="12">
            <el-form-item label="收货后结算" class="smallSelect" >
              <el-select v-model="dataForm.name" placeholder="请选择">
                <el-option
                  v-for="item in rangeOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
              <span>收货后 <i>{{dataForm.name}}</i> 日结算</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="发票要求" prop="radio">
              <el-radio-group v-model="dataForm.radio">
                <el-radio :label="1">增值税专用发票自行开</el-radio>
                <el-radio :label="2">增值税专用发票自行开票或税务代开</el-radio>
                <el-radio :label="3">增值税普通发票</el-radio>
                <el-radio :label="4">不用发票</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="经营地址" prop="name">
              <el-cascader expand-trigger="hover"
                           :options="addressOptions"
                           v-model="selectedcityOptions"
                           @change="handlecityChange"
                           clearable>
              </el-cascader>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="注册资金" prop="name">
              <el-input  v-model="dataForm.name" placeholder="请输入注册资金"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <span style="line-height: 34px; padding-left: 10px;">万元及以上</span>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="证照要求" prop="checkbox">
              <el-checkbox label="五证合一" v-model="dataForm.all"></el-checkbox>
              <el-checkbox-group v-model="dataForm.checkbox">
                <el-checkbox label="营业执照"></el-checkbox>
                <el-checkbox label="税务登记证"></el-checkbox>
                <el-checkbox label="组织机构代码证"></el-checkbox>
                <el-checkbox label="统计登记证"></el-checkbox>
                <el-checkbox label="社会保险登记证"></el-checkbox>
                <br/>
                <el-checkbox label="其他证书"></el-checkbox>
                <el-input  class="smallInput" v-model="dataForm.name" placeholder="请输入证书名称"></el-input>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="mainContent">
        <h3 class="title">项目信息</h3>
        <el-row style="margin-top: 20px">
          <el-col :span="24">
            <el-form-item label="补充说明" prop="name">
              <editor ref="ueditor" class="ueditor" :height= '200' :editread="false" :content = "content"></editor>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="添加附件" prop="name" class="attachment">
              <span class="tip">提示：产品规格书、样品图等（支持jpg、jpeg、gif、doc等格式）</span>
              <uploadFile class="blueUpload" fileMaxNum="5" @uploadSuccess="uploadSuccess" @deleteSuccess="deleteSuccess" fileType="1" :fileArrays="fileInformations" isImage="1"></uploadFile>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="footerDetailBtn">
<!--        <el-button class="detailBtn returnBtn">保存至草稿箱</el-button>-->
        <el-button class="detailBtn returnBtn">保存至草稿箱</el-button>
        <el-button class="detailBtn" type="primary">发布直采信息</el-button>
      </div>
      <el-dialog title="备注及附件" width="668px" :visible.sync="dialogFormVisible">
        <el-form-item label="备注" label-width="60px">
          <el-input v-model="dataForm.name" type="textarea" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="附件" label-width="60px" class="attachment">
          <span class="tip">提示：产品规格书、样品图等（支持jpg、jpeg、gif、doc等格式）</span>
          <uploadFile class="blueUpload" @uploadSuccess="attachUploadSuccess" @deleteSuccess="attachDeleteSuccess" fileType="1" :fileArrays="fileInformations" isImage="1"></uploadFile>
        </el-form-item>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
        </div>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
import * as region from '@/assets/js/region'
import editor from '@/components/ueditor/ueditor.vue'
import uploadFile from '@/components/upload/publicFileUpload'
export default {
  components: {
    editor,
    uploadFile
  },
  data () {
    return {
      dataForm: {
        name: '',
        checkbox: [],
        deliveryCheckbox: [],
        radio: '',
        paymentRadio: ''
      },
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
      tableData: [
        {}, {}, {}
      ],
      rules: {},
      // 地址三级联动
      addressOptions: region.CityInfo,
      selectedcityOptions: [],
      fileInformations: [],
      content: '',
      dialogFormVisible: false
    }
  },
  methods: {
    handleDel (data) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        console.log('删除')
        this.tableData.splice(this.tableData.findIndex(item => item.id === data.id), 1)
      }).catch(() => {
        return false
      })
    },
    handleAdd () {
      this.tableData.push({})
    },
    handlecityChange () {},
    // 上传文件
    uploadSuccess (file, fileType) {
      this.fileInformations.push(file)
    },
    // 删除文件
    deleteSuccess (fileId, fileType) {
      this.fileInformations = this.fileInformations.filter(item => item.relativePath !== fileId)
    },
    // 上传文件
    attachUploadSuccess (file, fileType) {
      this.fileInformations.push(file)
    },
    // 删除文件
    attachDeleteSuccess (fileId, fileType) {
      this.fileInformations = this.fileInformations.filter(item => item.relativePath !== fileId)
    },
    handleHideAdd () {
      this.dialogFormVisible = true
    },
    handleHideUpdate () {
      this.dialogFormVisible = true
    },
    handleHideDel () {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
      }).catch(() => {
        return false
      })
    }
  }
}
</script>

<style lang="less">
  #directPurchaseAdd{
    .tableAddBtn{
      float: left;
      width: 100%;
      overflow: hidden;
      height: 28px;
      line-height: 28px;
      text-align: center;
      background: #f3f7fb;
      margin-bottom: 20px;
      color: #2789ba;
      font-size: 12px;
      cursor: pointer;
      border: 1px solid #e6e6e6;
      border-top: 0;
    }
    .el-date-editor{
      width: 100%;
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
    .el-checkbox-group{
      font-size: 14px;
    }
    .el-checkbox-group .month{
      width: 230px;
      display: inline-block;
    }
    .el-checkbox-group .month .el-input{
      width: 70px;
      display: inline-block;
      margin: 0 5px;
    }
    .el-checkbox-group .other{
      width: 200px;
      display: inline-block;
    }
    .smallSelect .el-select{
      width: 100px;
    }
    .smallSelect span i{
      margin: 0 3px;
      display: inline-block;
      color: #2789ba;
      font-weight: bold;
      font-style: normal;
    }
    .smallInput{
      width: 300px;
    }
    .ueditor{
      width: 98%;
    }
    .attachment .webuploader-pick{
      width: 72px;
      height: 32px;
      line-height: 32px;
      padding-left: 20px;
      background: #2789ba url("../../../../../static/images/enterprise/upload.png") 10px center no-repeat;
    }
    .attachment span.tip{
      position: absolute;
      left: 125px;
      color: #999999;
    }
  }
</style>
