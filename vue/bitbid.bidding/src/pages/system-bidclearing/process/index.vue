<template>
  <div id="clearBidProcess" class="clearBidProcess"  v-loading="loading">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-bidclearing' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{programName}}</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="processmain">
      <div :class="(active===1)? 'firststep' : (active===2)? 'secondstep': 'step'">
        <ul class="stepul">
          <li><span>1. 导入招标文件</span></li>
          <li><span>2. 导入投标文件</span></li>
        </ul>
      </div>
      <div class="firstcontent" v-if="active === 1">
        <div class="uploadfile">
          <ul>
            <li>
              <span><i>*</i>招标文件</span>
              <p class="filepath" :title="bidFileName">{{bidFileName}}</p>
              <uploadBidFile v-on:childBidValue="childBidValue"></uploadBidFile>
            </li>
            <li>
              <span>控制价文件</span>
              <p class="filepath" :title="controlFileName">{{controlFileName}}</p>
              <uploadControlFile v-on:childControlValue="childControlValue"></uploadControlFile>
            </li>
          </ul>
        </div>
        <div class="bidtable">
          <p>招标信息</p>
          <table>
            <tr>
              <td>工程编号</td>
              <td>{{projectCode}}</td>
            </tr>
            <tr>
              <td>工程名称</td>
              <td>{{name}}</td>
            </tr>
            <tr>
              <td>招标人</td>
              <td>{{tenderee}}</td>
            </tr>
            <tr>
              <td>招标控制价（元）</td>
              <td>{{totalPrice}}</td>
            </tr>
            <tr>
              <td>招标控制价分部分项费（元）</td>
              <td>{{partialListFee}}</td>
            </tr>
            <tr>
              <td>招标控制价措施项目费（元）</td>
              <td>{{measureItemFee}}</td>
            </tr>
          </table>
        </div>
      </div>
      <!-- 填写招标控制价 弹窗 -->
      <el-dialog title="填写信息" :visible.sync="dialogFormVisible" width="30%">
        <el-form :model="totalPriceEdit" :rules="rules" ref="totalPriceEdit">
          <el-form-item label="招标控制价：" :label-width="formLabelWidth" prop="priceValue">
            <el-input v-model="totalPriceEdit.priceValue"></el-input>
          </el-form-item>
        </el-form>
        <p class="tip">注：请输入招标控制价，若没有此信息，请点击确定，直接进入下一步</p>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="subNext('totalPriceEdit')">确 定</el-button>
        </div>
      </el-dialog>
      <!-- 填写招标控制价 弹窗 -->
      <div class="secondcontent" v-if="active === 2">
        <div class="fr"> <uploadTenderFile v-on:childTenderValue="childTenderValue"></uploadTenderFile></div>
        <template>
          <el-table
            :data="tableData"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              type="index"
              label="序号"
              width="50"
              align="center">
            </el-table-column>
            <el-table-column
              prop="bidder"
              label="投标单位"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="totalPrice"
              label="投标报价（元）"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              label="操作" width="70" align="center">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="_delete(scope)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </div>
      <div class="process_button">
        <el-button type="primary" v-if="active === 1 " @click="nextStep">下一步</el-button>
        <el-button type="primary" v-if="active === 2 " @click="prevStep">上一步</el-button>
        <el-button type="primary" class="clear" v-if="active === 2 " @click="clearBtn">一键清标</el-button>
      </div>
    </div>
    <div class="percentage" v-if="isClear">
      <el-progress type="circle" :percentage="percentage"></el-progress>
    </div>
  </div>
</template>
<script>
import { billClearData } from '@/api'
import uploadBidFile from './bidFile.vue'
import uploadControlFile from './controlFile.vue'
import uploadTenderFile from './tenderFile.vue'
import { sumType } from '@/assets/js/validate'
export default {
  data () {
    // 数字
    var sumTypes = (rule, value, callback) => {
      if (!sumType(value)) {
        callback(new Error('请输入数字，小数点后保持两位'))
      } else {
        callback()
      }
    }
    return {
      programName: '',
      active: 1,
      bidFileName: '',
      controlFileName: '',
      tableData: [],
      isClear: false,
      percentage: 0,
      projectCode: '',
      name: '',
      tenderee: '',
      totalPrice: '',
      partialListFee: '',
      measureItemFee: '',
      totalPriceEdit: {
        priceValue: '',
        programId: this.$route.params.objectId
      },
      dialogFormVisible: false,
      formLabelWidth: '100px',
      rules: {
        priceValue: [
          {validator: sumTypes, trigger: ['change']}
        ]
      },
      loading: true
    }
  },
  /** 注册组件 */
  components: {
    uploadBidFile,
    uploadControlFile,
    uploadTenderFile
  },
  methods: {
    _openRoom () {
      billClearData.open(this.$route.params.objectId).then((res) => {
        this.programName = res.data.program.name
        let tempData = res.data.bidding_itemInfor
        let tempControlData = res.data.control_itemInfor
        if (tempData) {
          this.projectCode = tempData.projectCode
          this.name = tempData.name
          this.tenderee = tempData.tenderee
          this.bidFileName = res.data.map.bidingName
          this.loading = false
          if (tempControlData) {
            if (tempControlData.totalPrice) {
              this.totalPrice = tempControlData.totalPrice
            }
            if (tempControlData.partialListFee) {
              this.partialListFee = tempControlData.partialListFee
            }
            if (tempControlData.measureItemFee) {
              this.measureItemFee = tempControlData.measureItemFee
            }
          }
          if (res.data.map.controlName) {
            this.controlFileName = res.data.map.controlName
          }
          this.loading = false
        } else {
          this.loading = false
        }
      })
    },
    // 上传招标文件接收数据
    childBidValue (val) {
      this.projectCode = val.result.itemInformation.projectCode
      this.name = val.result.itemInformation.name
      this.tenderee = val.result.itemInformation.tenderee
      this.bidFileName = val.map.bidingName
      this.totalPrice = ''
      this.partialListFee = ''
      this.measureItemFee = ''
      this.controlFileName = ''
    },
    // 上传招标控制价文件接收数据
    childControlValue (val) {
      this.totalPrice = val.result.itemInformation.totalPrice
      this.partialListFee = val.result.itemInformation.partialListFee
      this.measureItemFee = val.result.itemInformation.measureItemFee
      this.controlFileName = val.map.controlName
    },
    // 读取投标数据
    getTender () {
      this.loading = true
      billClearData.getTenderInfo(this.$route.params.objectId).then((res) => {
        if (res.data.tenders.length !== 0) {
          this.loading = false
          this.tableData = res.data.tenders
        } else {
          this.loading = false
          this.tableData = []
        }
      })
    },
    // 上传投标文件接收数据
    childTenderValue (val) {
      this.tableData.push(val)
    },
    //  上一步
    prevStep () {
      this.active--
    },
    //  下一步
    nextStep () {
      if (this.bidFileName === null || this.bidFileName === '') {
        this.$message({
          showClose: true,
          message: '请先上传招标文件',
          type: 'warning'
        })
      } else {
        this.getTender()
        if (this.controlFileName) {
          this.active++
        } else {
          this.dialogFormVisible = true
        }
      }
    },
    subNext (formName) {
      if (this.totalPriceEdit.priceValue) {
        this.$refs[formName].validate((valid) => {
          console.log(valid)
          if (valid) {
            billClearData.saveControlInfo(this.totalPriceEdit).then((res) => {
            })
            this.dialogFormVisible = false
            this.active++
          } else {
            return false
          }
        })
      } else {
        this.dialogFormVisible = false
        this.active++
      }
    },
    // 后台获取清标进度（递归）
    getClearBtnProgress () {
      billClearData.getProgress(this.$route.params.objectId).then((res) => {
        if (res.data.progress) {
          this.percentage = Number(res.data.progress)
        }
        let that = this
        setTimeout(function () {
          if (that.percentage === 100) {
            that.$router.push({path: `/system-bidclearing/result`, query: {objectId: that.$route.params.objectId}})
          } else {
            that.getClearBtnProgress()
          }
        }, 1000)
      })
    },
    // 一键清标按钮事件
    clearBtn () {
      if (this.tableData === null || this.tableData.length === 0) {
        this.$message({
          showClose: true,
          message: '请上传投标文件',
          type: 'warning'
        })
      } else {
        this.isClear = true
        billClearData.startCheck(this.$route.params.objectId).then((res) => {
          if (res.data.resCode === '0000') {
            this.getClearBtnProgress()
          }
        })
      }
    },
    // 投标文件删除
    _delete (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        billClearData.delTenderInfo(scope.row.objectId, this.$route.params.objectId).then((res) => {
          console.log(res)
          this.getTender()
        })
      }).catch(() => {
        return false
      })
    }
  },
  mounted () {
    this._openRoom()
  }
}
</script>
<style lang="less">
  body{
    background: #eaedf1;
  }
  #clearBidProcess{
    width: 1200px;
    overflow: hidden;
    min-height: 300px;
    background: white;
    margin: 20px auto;
    padding-bottom: 50px;
    .headertitle{
      padding: 10px;
      line-height: 24px;
      overflow: hidden;
    }
    .Breadcrumb{
      width: 100%;
      height: 24px;
    }
    .el-breadcrumb{
      line-height: 24px;
    }
    .processmain{
      padding: 10px;
      line-height: 24px;
      overflow: hidden;
      border-top: 10px solid #eaedf1;
    }
    .step,.firststep,.secondstep{
      width: 874px;
      height: 50px;
      line-height: 50px;
      margin: 24px auto;
    }
    .firststep{
      background: url("../../../../static/images/system-clearbid/firststep.png") no-repeat;
    }
    .secondstep{
      background: url("../../../../static/images/system-clearbid/secondstep.png") no-repeat;
    }
    .stepul{
      width: 100%;
      overflow: hidden;
    }
    .stepul li{
      width: 50%;
      float: left;
      text-align: center;
      color: #ffffff;
    }
    .firststep  .stepul li:last-child{
      color: #333333;
    }
    .firstcontent, .secondcontent{
      width: 874px;
      overflow: hidden;
      margin: 10px auto;
    }
    .firstcontent .uploadfile{
      width: 100%;
      height: 109px;
      overflow: hidden;
      background: #f7f8fa;
    }
    .firstcontent .uploadfile ul{
      overflow: hidden;
    }
    .firstcontent .uploadfile ul li{
      width: 50%;
      float: left;
      margin-top: 34px;
    }
    .firstcontent .uploadfile ul li span{
      color: #333333;
      font-size: 16px;
      font-weight: bold;
      display: inline-block;
      margin-left: 20px;
      float: left;
      line-height: 39px;
    }
    .firstcontent .uploadfile ul li span i{
      color: red;
    }
    .firstcontent .uploadfile ul li p.filepath{
      width: 224px;
      display: inline-block;
      height: 33px;
      line-height: 40px;
      border-bottom: 1px solid #dddddd;
      margin-left: 10px;
      float: left;
      color: #00b7ee;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .bidtable{
      width: 874px;
      overflow: hidden;
      margin: 10px auto;
    }
    .bidtable > p{
      line-height: 50px;
      color: #333333;
      font-weight: bold;
      padding-left: 10px;
    }
    .bidtable{
      margin: 0 auto 40px auto;
      border-collapse: collapse;
    }
    .bidtable table{
      width: 100%;
      border-collapse:collapse;
    }
    .bidtable tr{
      line-height: 26px;
    }
    .bidtable tr td{
      line-height: 48px;
      padding: 0 10px;
      border: 1px solid #ebeef5;
      font-size: 14px;
      color: #606266;
    }
    .bidtable tr td:first-child{
      width: 300px;
    }
    .process_button{
      text-align: center;
      margin: 30px 0;
    }
    p.tip{
      margin-left: 10px;
      color: red;
    }
    .el-button--primary{
      background: #3f63f6;
      border: 1px solid #3f63f6;
    }
    .clear{
      background: #00aaff;
      border: 1px solid #00aaff;
    }
    .secondcontent .fr{
      float: right;
      margin-bottom: 30px;
    }
    .secondcontent .el-table{
      margin-bottom: 30px;
    }
    .tabletitles{
      background:#f7f8fa!important;
    }
    .percentage{
      width: 100%;
      height: 100%;
      line-height: 100%;
      position: fixed;
      top: 0px;
      left: 0px;
      opacity: .9;
      background: #fff;
      text-align: center;
    }
    .percentage .el-progress{
      position: absolute;
      left: 50%;
      top: 50%;
      margin: -63px 0 0 -63px;
    }
    .el-table td{
      padding: 12px 0!important;
    }
    .el-button--small, .el-button--small.is-round{
      padding: 5px 0px;
      margin-left: 0px;
    }
  }
</style>
