<template>
  <div>
    <div id="addItembid" class="content_bigbox">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/projectpayments' }">
          项目收支管理
        </el-breadcrumb-item>
        <el-breadcrumb-item :to="{path:'/projectpayments/manage/other-income',query: {name: this.$route.query.name, incExpenseIds: this.$route.query.incExpenseIds, costTypes: this.$route.query.costTypes, expenseId: this.$route.query.expenseId}}">
          {{this.$route.query.name}}
        </el-breadcrumb-item>
        <el-breadcrumb-item class="active_bread">查看收入</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <!-- form表单 -->
      <el-row style="margin-top:48px">
        <el-form :model="ruleForm" label-width="160px">
          <el-col :span="24">
            <el-form-item label="收入项目：" prop="paymentUnitName" class="bitianicon">
              <el-input class="el-inputbox" v-model="ruleForm.paymentUnitName" readonly></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="金额(元)：" prop="paymentAmount" class="bitianicon">
              <el-input v-model="ruleForm.paymentAmount" readonly></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="收费方式：" prop="paymentType" class="bitianicon">
              <el-select class="el-selectbox" v-model="ruleForm.paymentType" placeholder="请选择" disabled>
                <el-option
                  v-for="item in Paymodefrom"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="收费时间：" prop="paymentTime">
              <el-date-picker class="releaseTimebox"
                disabled
                v-model="ruleForm.paymentTime"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                :picker-options="pickerOptions"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="相关标段：" class="jfbd_box bitianicon">
              <el-tag
                v-model="ruleForm.dynamicTags"
                :key="index" readonly
                v-for="(tag,index) in dynamicTags"
                :disable-transitions="false">
                <span class="jfbdtitlebox" :title="tag.sectionName">{{tag.sectionName}}</span>
                <span class="jfbdtitlebox" style="margin-left: 10px" :title="tag.sectionNumber">({{tag.sectionNumber}})</span>
              </el-tag>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注：">
              <el-input
                type="textarea"
                readonly
                :autosize="{ minRows: 2, maxRows: 6}"
                placeholder=""
                v-model="ruleForm.remark">
              </el-input>
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <!-- form表单 -->
      <!--操作记录-->
      <div class="caozuojilu_bigbox">
        <div class="caozuo_titles">操作记录：</div>
        <div class="caozuotablebox">
          <el-table
            :data="caozuotableData"
            border
            style="width: 100%">
            <el-table-column
              type="index"
              label="序号"
              width="50"
              align="center"
              :index='dataIndex'>
            </el-table-column>
            <el-table-column
              prop="operatingType"
              label="操作"
              width="180">
            </el-table-column>
            <el-table-column
              prop="operatorName"
              label="操作人"
              width="180">
            </el-table-column>
            <el-table-column
              prop="operatingTime"
              label="操作时间">
            </el-table-column>
          </el-table>
        </div>
      </div>
      <!--操作记录-->
    </div>
  </div>
</template>
<script>
export default{
  data () {
    return {
      ruleForm: {},
      formLabelWidth: '120px',
      Paymodefrom: [{
        value: 0,
        label: '支付宝'
      }, {
        value: 1,
        label: '微信'
      }, {
        value: 2,
        label: '银联'
      }, {
        value: 3,
        label: '现金'
      }, {
        value: 4,
        label: '汇款'
      }, {
        value: 5,
        label: '支票'
      }, {
        value: 6,
        label: '保函'
      }, {
        value: 7,
        label: '其他'
      }],
      // 日期选择
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7
        }
      },
      dynamicTags: [],
      dataIndex: 1,
      caozuotableData: [],
      operatingType: [
        {
          value: 1,
          label: '收费'
        },
        {
          value: 2,
          label: '退还'
        },
        {
          value: 3,
          label: '收费审批'
        },
        {
          value: 4,
          label: '退还审批'
        },
        {
          value: 5,
          label: '支出'
        }
      ],
      operators: []
    }
  },
  methods: {
    // 进入页面后查询数据
    editList () {
      this.axios.get(`charge/${this.$route.query.objectIds}?_t=${new Date().getTime()}`).then((res) => {
        this.ruleForm = res.data.chargeManagement
        // 相关标段
        res.data.chargeManagement.sectionInformations.map((ite) => {
          if (ite.isChecked === 0) {
            this.dynamicTags.push(ite)
          }
        })
        // 相关操作记录
        res.data.chargeManagement.operatingRecords.map((ite) => {
          let operatingType = ite.operatingType
          this.operatingType.map((item) => {
            if (item.value === operatingType) {
              ite.operatingType = item.label
            }
          })
          this.caozuotableData.push(ite)
        })
      })
    }
  },
  mounted () {
    this.editList()
  }
}

</script>
<style lang='less'>
  body{
    background:#eaedf1;
  }
  #addItembid{
    padding: 0 20px;
    padding-bottom: 50px;
    .breadcrumb_box{
      width: 100%;
      height: 60px;
      border-bottom: 1px solid #eeeeee;
      padding: 0 20px;
      box-sizing: border-box;
      line-height: 60px;
    }
    .el-dialog__body {
      padding: 10px 20px;
      color: #606266;
      line-height: 24px;
      font-size: 14px;
    }
    .seacherbigboix{
      margin-bottom:30px;
    }
    .xinzhileft_box{
      padding-bottom: 10px;
      border-bottom: 1px solid #CCCCCC;
      margin-bottom: 25px;
    }
    .da_xz_ckbigbox {
      padding: 10px 20px;
    }
    a{
      text-decoration:none;
    }
    .gongneng_btn a{
      color:#fff;
    }
    .gongneng_btnbox{
      text-align: center;
    }
    .el-inputbox{
      position: relative;
      border: 1px solid #dcdfe6;
      border-radius: 4px;
    }
    .xuanzhebtn{
      color:#409EFF;
      position: absolute;
      right: 20px;
      top: 2px;
      font-size:14px;
      cursor: pointer;
    }
    .el-inputbox input{
      width: 94%;
      border: none;
      margin-left: 1px;
    }
    .el-selectbox{
      width: 100%;
    }
    .releaseTimebox{
      width: 100%;
    }
    .jfbd_box .el-form-item__content{
      width: 86.7%;
      padding:10px;
      border-radius: 4px;
      border: 1px solid #dcdfe6;
      box-sizing: border-box;
    }
    .el-tag {
      display: block;
      background-color:transparent;
      padding: 0 10px;
      height: 32px;
      line-height: 30px;
      font-size: 13px;
      color: #333333;
      border-radius: 4px;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      border: none;
      white-space: nowrap;
    }
    .roleseacher{
      width: 30%;
    }
    .searchuser{
      width: 145px;
    }
    .searchuser>.el-input--suffix{
      width: 100%;
    }
    .el-table--border{
      padding-top: 5px;
    }
    .operationbtn{
      text-align: center;
    }
    .bitianicon .el-form-item__label::before{
      content:"*";
      color:#f66c6c;
      margin-right: 5px;
    }
    .caozuo_titles{
      display: inline-block;
      width: 160px;
      vertical-align: top;
      font-size: 14px;
      color: #606266;
      height: 40px;
      line-height: 40px;
      text-align: right;
      padding: 0 12px 0 0;
      box-sizing: border-box;
    }
    .caozuotablebox{
      width: 86.2%;
      min-height: 50px;
      background: red;
      vertical-align: top;
      display: inline-block;
    }
    .jfbdtitlebox{
      width: 45%;
      display: inline-block;
      vertical-align: top;
      overflow: hidden;
      text-overflow:ellipsis;
      white-space: nowrap;
    }
    .el-breadcrumb__inner{
      color: #999999!important;
    }
  }
</style>
