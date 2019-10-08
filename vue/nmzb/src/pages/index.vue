<template>
  <div v-if="$route.meta.layout === 'anno-print'">
    <router-view/>
  </div>
  <div id="index"  v-else>
    <el-row>
      <leftMenu></leftMenu>
      <el-col :span="21">
        <div><commonheader></commonheader></div>
        <div><router-view/></div>
        <!--<commonfooter></commonfooter>-->
      </el-col>
    </el-row>
  </div>
</template>
<script>
import leftMenu from '../components/left.vue'
import commonheader from '../components/header.vue'
// import commonfooter from '../components/footer.vue'
export default {
  components: {
    leftMenu,
    commonheader
    // commonfooter
  },

  data () {
    return {
    }
  }
}
</script>
<style lang="less">
  @import '../assets/css/common.css';
  #index {
    color: #2c3e50;
    background:#f5f5f5;
    .el-col-21{
      float: right;
      position: relative;
    }
    .sidebox{
      border: none;
    }
    .indexs{
      text-align: left;
    }
    .el-main{
      padding: 0;
    }
  }
</style>
