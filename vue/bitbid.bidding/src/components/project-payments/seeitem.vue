<template>
  <div>
    <div id="seeItem" class="content_bigbox">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/projectpayments' }">项目收支管理</el-breadcrumb-item>
        <el-breadcrumb-item class="active_bread">项目信息</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <!-- form表单 -->
      <el-row style="margin-top:48px">
          <el-form :model="ruleForm" label-width="160px">
            <el-col :span="12">
              <el-form-item label="招标项目编号：" prop="bidProjectNumber">
                <el-input v-model="ruleForm.bidProjectNumber" readonly :title="ruleForm.bidProjectNumber"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="招标项目名称：" prop="bidProjectName">
                <el-input v-model="ruleForm.bidProjectName" readonly :title="ruleForm.bidProjectName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="招标人：" prop="bidder">
                <el-input v-model="ruleForm.bidder" readonly :title="ruleForm.bidder"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-col :span="12">
                <el-form-item label="是否委托招标代理：" prop="isEntrustAgency">
                  <el-select class="zzt_zbfs_selectbox" v-model="ruleForm.isEntrustAgency" placeholder="请选择是否委托" disabled>
                    <el-option
                      v-for="item in isEntrustAgencySelet"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                      :disabled="item.disabled" >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item v-show="ruleForm.isEntrustAgency=='1'" prop="agentStructure" label="代理机构：" disabled>
                  <el-input v-model="ruleForm.agentStructure" readonly :title="ruleForm.agentStructure"></el-input>
                </el-form-item>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-form-item label="项目经理：">
                <el-input v-model="ruleForm.projectManagerName" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="所属部门：">
                <el-input v-model="ruleForm.departmentName" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-form>
      </el-row>
      <div class="tablebigbox">
        <div class="bdxititlebox">标段信息：</div>
        <el-col :span="24">
          <el-table
            border
            header-cell-class-name="header"
            :data="tableData"
            style="width: 100%">
            <el-table-column
              label="序号"
              type="index"
              :index="1"
              width="80" align="center">
            </el-table-column>
            <el-table-column
              prop="sectionNumber"
              label="标段编号">
            </el-table-column>
            <el-table-column
              prop="sectionName"
              label="标段名称">
            </el-table-column>
            <el-table-column
              prop="bidType"
              label="招标方式">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.bidType === 0">
                  <span class="statusbox">公开招标</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.bidType === 1">
                  <span class="statusbox">邀请招标</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="inforOnMethod"
              label="资审方式">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.inforOnMethod === 0">
                  <span class="statusbox">资格后审</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.inforOnMethod === 1">
                  <span class="statusbox">资格预审</span>
                </div>
                <div class="statusbigbox" v-else>
                  <span class="statusbox">-----------</span>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </div>
      <!-- form表单 -->
    </div>
  </div>
</template>
<script>
import {addtitle} from '../../assets/js/common'
export default{
  data () {
    return {
      bidsMethodSelet: [{
        value: 0,
        label: '公开招标'
      }, {
        value: 1,
        label: '邀请招标'
      }],
      inforOnMethodSelet: [{
        value: 0,
        label: '资格后审'
      }, {
        value: 1,
        label: '资格预审'
      }],
      isEntrustAgencySelet: [{
        value: 0,
        label: '否'
      }, {
        value: 1,
        label: '是'
      }],
      // 表单数据
      ruleForm: {},
      tableData: []
    }
  },
  methods: {
    seeitemList () {
      this.axios.get(`income-expenses/${this.$route.query.objectIds}?_t=${new Date().getTime()}`).then((res) => {
        this.ruleForm = res.data.projectInformation
      })
    },
    // 标段列表接口
    sectioninformationList () {
      this.axios.get(`section-information?incExpenseId=${this.$route.query.objectIds}&_t=${new Date().getTime()}`).then((res) => {
        this.tableData = res.data.sectionInformationPageInfo.list
        addtitle(this)
      })
    }
  },
  mounted () {
    this.seeitemList()
    this.sectioninformationList()
  }
}

</script>
<style lang='less'>
body{
  background:#eaedf1;
}
#seeItem{
    padding: 0 20px;
    .breadcrumb_box{
        width: 100%;
        height: 60px;
        border-bottom: 1px solid #eeeeee;
        padding: 0 20px;
        box-sizing: border-box;
        line-height: 60px;
    }
    .el-breadcrumb__inner{
        color: #999999!important;
    }
  .da_xz_ckbigbox {
    padding: 10px 20px;
  }
    .active_bread>.el-breadcrumb__inner{
        color:#333333!important;
    }
    .header{background: #f5f7f8}
    .da_xz_ckbigbox{
        border: none;
        line-height:60px;
        padding:0 0 0 20px;
        .el-form-item{padding: 0px}
        .el-dialog__header{padding-top: 0;padding-bottom: 0px}
        .el-dialog__body{padding-bottom: 0px}
    }
    .addBiaoDuan{
        margin-bottom: 10px;
    }
  .zzt_zbfs_selectbox{
    width: 100%;
  }
  .el-form-item .el-form-item {
    margin-bottom: 20px;
  }
  .tablebigbox{
    width: 90%;
    margin: 0 auto;
    overflow: hidden;
    padding-bottom: 50px;
  }
  .bdxititlebox{
    font-size: 14px;
    color: #606266;
    margin-left: 20px;
    margin-bottom: 20px;
  }
  .el-table .cell {
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    word-break: break-all;
    height: 23px;
    line-height: 23px;
    overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
  }
  .el-input__inner {
    overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
  }
  .el-breadcrumb__inner{
    color: #999999!important;
  }
}
</style>
