<template>
  <div id="header">
    <div class="headerbigbox">
      <el-dropdown trigger="click" class="setbigbox">
          <span class="el-dropdown-link">
            <span>{{$store.getters.authUser.userName}}</span>
            <i class="el-icon-arrow-down el-icon--right"></i>
          </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>
            <div @click="indexBtn">个人中心</div>
          </el-dropdown-item>
          <el-dropdown-item>帐号设置</el-dropdown-item>
          <el-dropdown-item>意见反馈</el-dropdown-item>
          <el-dropdown-item>
            <div @click="logout">退出帐号</div>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <div class="headerimgbox">
        <img src="../../../static/images/systemmanagement/header.png" alt="">
      </div>
      <div class="bfunctionbox">
        <div class="bfunctionimg">
          <div class="btnimg_box_lianjie"></div>
        </div>
        <div class="bfunctionimg">
          <div class="btnimg_box_tishi"></div>
        </div>
        <div class="bfunctionimg">
          <div class="btnimg_box_youjian"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'commonhead',
  methods: {
    handleClose (key, keyPath) {
    },
    handleOpen (key, keyPath) {
    },
    logout () {
      this.$store.dispatch('Logout').then(() => {
        this.$router.push({path: '/login'})
      })
    },
    indexBtn () {
      this.$router.push({path: '/index'})
    }
  }
}
</script>

<style lang="less">
#app {
  .el-header{
    padding: 0px;
  }
  .headerbigbox{
    display: inline-block;
    width: 87.5%;
    height: 50px;
    background: #ffffff;
    padding: 0 20px;
    box-sizing: border-box;
    box-shadow: 0 0 10px rgba(0,0,0,0.3);
    position: fixed;
    top: 0;
    right: 0;
    z-index: 99;
  }
  .setbigbox{
    height: 100%;
    display: inline-block;
    float: right;
    line-height: 50px;
    font-size: 14px;
    cursor: pointer;
  }
  .headerimgbox{
    width: 30px;
    height: 100%;
    float: right;
    margin-right: 25px;
    position: relative;
  }
  .headerimgbox>img{
    width: 30px;
    position: absolute;
    left: 0;
    right: 0;
    top:0;
    bottom: 0;
    margin: auto;
    border-radius: 50%;
    cursor: pointer;
  }
  .bfunctionbox{
    height: 100%;
    float: right;
    margin-right: 25px;
  }
  .bfunctionimg{
    display: inline-block;
    width:70px;
    height: 100%;
    vertical-align: top;
    cursor: pointer;
    transition: all .8s ease;
    position: relative;
  }
  .btnimg_box_tishi{
    width: 25px;
    height: 25px;
    position: absolute;
    left: 0;
    right: 0;
    top:0;
    bottom: 0;
    margin: auto;
    background: url("../../../static/images/systemmanagement/tishi.png")no-repeat;
    background-size: auto 25px;
  }
  .btnimg_box_youjian{
    width: 25px;
    height: 25px;
    position: absolute;
    left: 0;
    right: 0;
    top:0;
    bottom: 0;
    margin: auto;
    background: url("../../../static/images/systemmanagement/youjian.png")no-repeat;
    background-size: 25px auto;
  }
  .btnimg_box_lianjie{
    width: 25px;
    height: 25px;
    position: absolute;
    left: 0;
    right: 0;
    top:0;
    bottom: 0;
    margin: auto;
    background: url("../../../static/images/systemmanagement/lianjian.png")no-repeat;
    background-size: 25px auto;
  }
  .bfunctionimg:hover{
    background:#eeeeee;
  }
  .bfunctionimg:hover .btnimg_box_tishi{
    background: url("../../../static/images/systemmanagement/tishi_l.png")no-repeat;
    background-size: auto 25px;
  }
  .bfunctionimg:hover .btnimg_box_youjian{
    background: url("../../../static/images/systemmanagement/youjian_l.png")no-repeat;
    background-size: 25px auto;
  }
  .bfunctionimg:hover .btnimg_box_lianjie{
    background: url("../../../static/images/systemmanagement/lianjie.png")no-repeat;
    background-size: 25px auto;
  }
  .el-submenu {
    list-style: none;
    margin: 0;
    padding-left: 0;
    border-left: 5px solid #19233c;
    box-sizing: border-box;
    color: #cccccc;
  }
  .el-menu-item.is-active {
    color: #fff;
    border-left:5px solid #2c972f;
    box-sizing: border-box;
  }
  .logobigbox{
    width: 100%;
    height: 50px;
    position: relative;
  }
  .logobigbox>img{
    width: 50%;
    position: absolute;
    left: 20px;
    top: 10px;
  }
}
</style>
