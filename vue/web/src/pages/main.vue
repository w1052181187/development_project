<template>
  <div id="main">
    <div class="contentbigbox">
      <p><img src="../../static/images/text.png"/> </p>
      <img class="bg" src="../../static/images/home.png"/>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
    }
  },
  methods: {
    setMain () {
      // 获得屏幕的高度
      let screenH = window.innerHeight
      // 获得主要内容区
      let topUl = document.getElementsByClassName('contentbigbox')
      // 设置主要内容区的高度
      topUl[0].style.height = (screenH - 130) + 'px'
    }
  },
  mounted () {
    this.setMain()
  }
}
</script>
<style lang="less">
  #main {
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
    margin-top: 50px;
    .contentbigbox{
      width: 100%;
      padding:0 20px 0 20px;
      margin-top: 70px;
      box-sizing: border-box;
      background: #c7e7ff;
      text-align: center;
      position:relative;
      overflow: hidden;
    }
    .contentbigbox p{
      font-size: 40px;
      line-height: 60px;
      margin-top: 100px;
    }
    .contentbigbox img.bg{
      position: absolute;
      left: 50%;
      bottom:7%;
      margin-left: -401px;
      max-width:100%;
    }
  }
</style>
