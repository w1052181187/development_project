<template>
  <div id="trenderbook" class="trenderbook">
   <div class="trenderbookbox">
     <!--<el-form  label-width="120px">-->
       <!--<el-form-item label="请选择保存路径">-->
       <!--</el-form-item>-->
       <!--<el-form-item>-->
         <!--<el-input  placeholder="请输入文件名称"></el-input>-->
       <!--</el-form-item>-->
     <!--</el-form>-->
     <div class="filepath">
       <!--<i>请选择保存路径</i>-->
       <!--<span class="readfile"></span>-->
       <!--<el-button type="primary">选择路径</el-button>-->
       <el-form  label-width="100px">
         <el-form-item label="文件名称：">
            <el-input class="filename" placeholder="请输入文件名称" v-model="fileName"></el-input>
         </el-form-item>
       </el-form>
     </div>
   </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      fileName: ''
    }
  },
  methods: {
    // 将filename传给父组件
    fileNameToPar () {
      if (this.fileName) {
        this.$emit('childVal', this.fileName)
      }
    }
  },
  watch: {
    fileName () {
      this.fileNameToPar()
    }
  }
}
</script>
<style lang="less">
 #trenderbook{
   width: 100%;
   overflow: hidden;
   margin: 20px auto;
    .trenderbookbox{
      width: 666px;
      /*height: 275px;*/
      margin: 30px auto;
      /*-webkit-border-radius: 5px;*/
      /*-moz-border-radius: 5px;*/
      /*border-radius: 5px;*/
      /*border: 1px solid #ececec;*/
      /*-webkit-box-shadow: inset hoff voff blur #000000;*/
      /*-moz-box-shadow: inset hoff voff blur #000000;*/
      /*box-shadow: inset hoff voff blur #000000;*/
    }
   .filepath{
     width: 420px;
     height: 40px;
     margin: 70px auto 0 auto;
   }
   .filepath i{
     font-weight: bold;
     font-size: 16px;
     color: #333333;
     font-style: normal;
     float: left;
     margin-top: 18px;
   }
   .readfile{
     width: 210px;
     height: 39px;
     display: inline-block;
     border-bottom: 1px solid #dddddd;
     float: left;
   }
   .el-form-item__label{
     width: 100px!important;
   }
   .el-form-item__content{
     margin-left: 100px !important;
   }
   .filename{
     /*margin-top: 30px;*/
   }
 }
</style>
