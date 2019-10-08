<template>
    <div id="bidroom">
      <div class="headertitle">
        <!--面包屑-->
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>挂牌</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/mr/assignee/quotation/check-time-limit-bids?roledsType=2' }">查看限时竞价情况</el-breadcrumb-item>
          <el-breadcrumb-item>竞价室</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
      </div>
      <div class="contentbigbox">
        <!--弹窗-->
        <el-dialog
          title="提示"
          :visible.sync="nullDialog"
          :close-on-click-modal="false"
          width="30%"
          center>
          <div class="timelistip"><img src="../../../../../../static/images/mineral/public/tan.png"/> 拍卖室尚未建立，请等待</div>
          <span slot="footer" class="dialog-footer">
            <el-button @click="quit">退出竞价室</el-button>
          </span>
        </el-dialog>
        <el-dialog
          title="提示"
          :visible.sync="waitDialog"
          :close-on-click-modal="false"
          width="30%"
          center>
          <div class="timelistip"><img src="../../../../../../static/images/mineral/public/tan.png"/> 拍卖未开始，请等待</div>
          <div class="time">
            距离竞价开始时间
            <div class="countdown"> <span v-html="hour"></span>时<span v-html="min"></span>分<span v-html="second"></span>秒</div>
          </div>
          <span slot="footer" class="dialog-footer">
            <el-button @click="quit">退出竞价室</el-button>
          </span>
        </el-dialog>
        <el-dialog
          title="提示"
          :visible.sync="endDialog"
          :close-on-click-modal="false"
          width="30%"
          center>
          <div class="timelistip"><img src="../../../../../../static/images/mineral/public/tan.png"/> 此次拍卖已结束</div>
          <span slot="footer" class="dialog-footer">
            <el-button @click="quit">退出竞价室</el-button>
          </span>
        </el-dialog>
        <!--弹窗-->
        <!--主要内容 iframe-->
        <iframe id="if" src="../../../../../../static/mineral-bidroom/html/assignee/time-limit-bidroom.html" style="width: 100%; height: 1100px;"></iframe>
        <!--主要内容 iframe-->
      </div>
    </div>
</template>
<script>
export default {
  data () {
    return {
      centerDialogVisible: true,
      formInline: {
        price: ''
      }
    }
  },
  methods: {
  },
  mounted () {
  }
}
</script>
<style lang="less">
  #bidroom{
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
    margin-top: 50px;
    .headertitle{
      width: 100%;
      height: 60px;
      line-height: 60px;
      color:#333333;
      font-size: 16px;
      text-align: left;
      padding: 0 20px;
      box-sizing: border-box;
    }
    .el-breadcrumb{
      height: 60px;
      line-height: 60px;
    }
    /*新增按钮*/
    .addbutton{
      float: left;
      background: #2c972f;
      border-color: #2c972f;
    }
    /*搜索*/
    .seacher_box {
      width: 540px;
      height: 36px;
      min-width: 50px;
      float: right;
      margin-bottom: 20px;
    }
    .select{
      width: 20%;
    }
    .search{
      width: 79%;
    }
    /*弹出框*/
    .el-form-item__content .el-select{
      float: left;
    }
    .el-dialog__header{
      overflow: hidden;
      border-bottom: 1px solid #f4f4f4;
    }
    .el-dialog__header .el-dialog__title{
      float: left;
    }
    .contentbigbox{
      width: 100%;
      min-height: 100px;
      padding:20px;
      box-sizing: border-box;
    }
    .el-table .cell {
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      word-break: break-all;
      line-height: 23px;
      overflow: hidden;
      text-overflow:ellipsis;
      white-space: nowrap;
    }
    .el-button--text {
      color: #108cee;
      background: 0 0;
      padding-left: 0;
      padding-right: 0;
    }
    .el-table td{
      min-width: 0;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      text-overflow: ellipsis;
      vertical-align: middle;
      position: relative;
      padding-bottom: 5px;
    }
    .statusbox i{
      width: 6px;
      height: 6px;
      display: inline-block;
      margin-right: 5px;
      background: #9a9a9a;
      -webkit-border-radius: 50%;
      -moz-border-radius: 50%;
      border-radius: 50%;
    }
    .blue i{
      background: #118be1;
    }
    .green i{
      background: #2f8d35;
    }
    .red i{
      background: #ff4144;
    }
    .pagebox{
      margin-top: 15px;
      text-align: right;
    }
    iframe{
      border: 0px;
    }
  }
</style>
