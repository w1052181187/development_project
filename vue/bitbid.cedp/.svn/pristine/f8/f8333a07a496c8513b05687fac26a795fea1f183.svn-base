<template>
  <div class="blueCon details"  id="directPurchaseDetail">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/'}">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/projectExecute'}">项目执行</el-breadcrumb-item>
      <el-breadcrumb-item>直采详情</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="enterInfo">
      <el-form :model="dataForm" ref="dataForm" label-width="160px" :validate-on-rule-change="true">
        <div class="mainContent">
          <h3 class="title">项目信息</h3>
          <el-row>
              <el-col :span="12">
                <el-form-item label="项目名称">
                  <span>{{dataForm.name}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          <el-row>
              <el-col :span="12">
                <el-form-item label="联系人" prop="name">
                  <span>{{dataForm.name}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          <el-row>
              <el-col :span="12">
                <el-form-item label="联系方式" prop="name">
                  <span>{{dataForm.name}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          <el-row>
              <el-col :span="12">
                <el-form-item label="联系信息" prop="name">
                  <span>{{dataForm.name}}</span>
                </el-form-item>
              </el-col>
          </el-row>
        </div>
        <div class="mainContent">
          <h3 class="title">产品信息</h3>
          <p class="handle">
            <span>共 <i>1 </i>种物料 &nbsp;&nbsp;&nbsp;&nbsp; 采购总金额：<i>￥50000</i></span>
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
                prop="date"
                label="物料号">
              </el-table-column>
              <el-table-column
                prop="number"
                label="物料类目">
              </el-table-column>
              <el-table-column
                prop="date"
                label="物料名称">
              </el-table-column>
              <el-table-column
                prop="number"
                label="品牌">
              </el-table-column>
              <el-table-column
                prop="date"
                label="型号">
              </el-table-column>
              <el-table-column
                prop="number"
                label="物料描述">
              </el-table-column>
              <el-table-column
                prop="date"
                label="采购量">
              </el-table-column>
              <el-table-column
                prop="number"
                label="预算总额">
              </el-table-column>
              <el-table-column
                prop="date"
                align="center"
                label="备注及附件">
                <template slot-scope="scope">
                  <el-button type="text" size="small" class="operation-btn" @click="handleCheck()">查看</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
        <div class="mainContent">
          <h3 class="title">采购信息</h3>
            <el-row>
              <el-col :span="12">
                <el-form-item label="报名截止日期">
                  <span>{{dataForm.name}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="期望收货日期" prop="name">
                  <span>{{dataForm.name}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12" class="question_mark">
                <el-form-item label="单件报价"  prop="name">
                    <i class="tip" title="供应商可对一件或几件物料进行单独报价"></i>
                    <span>{{dataForm.name}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="收货地址" prop="name">
                  <span>{{dataForm.name}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        <div class="mainContent">
          <h3 class="title">供应商要求</h3>
          <el-row>
            <el-col :span="12">
              <el-form-item label="报价要求">
                <span>{{dataForm.name}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="支付方式"  prop="name">
                <span>{{dataForm.name}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="交易方式" prop="name">
                <span>{{dataForm.name}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="发票要求" prop="name">
                <span>{{dataForm.name}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="经营地址" prop="name">
                <span>{{dataForm.name}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="注册资金" prop="name">
                <span>{{dataForm.name}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="证件要求" prop="name">
                <span>{{dataForm.name}}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <div class="mainContent">
          <h3 class="title">补充说明</h3>
          <el-row class="marginTop">
            <el-col :span="24">
              <el-form-item label="补充说明" class="ueditor-el">
                <editor ref="ueditor" class="ueditor" :editread="true" :content = "content"></editor>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="添加附件" class="fileDownload executeFile">
                <file-download :fileArrays="fileInformations" fileType="7"></file-download>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <div class="footerDetailBtn">
          <el-button class="detailBtn returnBtn" @click="$router.go(-1)">返回</el-button>
          <el-button class="detailBtn" type="primary">退回报价</el-button>
          <el-button class="detailBtn" type="primary">选择报价</el-button>
        </div>
        <el-dialog title="备注及附件" width="668px" :visible.sync="dialogFormVisible">
          <el-form-item label="备注" label-width="60px">
            <el-input v-model="dataForm.name" type="textarea" autocomplete="off" readonly></el-input>
          </el-form-item>
          <el-form-item label="附件" label-width="60px" class="attachment">
              <file-download :fileArrays="fileInformations" fileType="7"></file-download>
          </el-form-item>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <!--<el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>-->
          </div>
        </el-dialog>
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/ueditor/ueditor.vue'
import fileDownload from '@/components/download/file-download'
export default {
  components: {
    editor,
    fileDownload
  },
  data () {
    return {
      dataForm: {
        name: '项目名称'
      },
      content: '',
      indexMethod: 1, // 序号
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
      dialogFormVisible: false
    }
  },
  methods: {
    handleCheck () {
      this.dialogFormVisible = true
    }
  },
  mounted () {
  }
}
</script>
<style lang="less">
  #directPurchaseDetail{
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
    .fileDownload .el-form-item__label{
      line-height: 40px;
    }
    .ueditor{
      width: 99%;
    }
    .marginTop{
      margin-top: 15px;
    }
    .ueditor-el .edui-editor-toolbarboxouter{
      display: none;
    }
    .fileDownload .el-form-item__content>div{
      width: 70%;
    }
  }
</style>
