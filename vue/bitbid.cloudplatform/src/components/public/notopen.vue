<template>
  <div id="indexopen">
    <div class="homepagecontent">
      <div class="homepageconright">
        <!--内容开始-->
        <div class="penserviceconbox">
          <div class="penserviceconconbox">
            <div class="notopen_bigbox">
              <img src="../../../static/images/suo.png" alt="">
              <div>该服务暂未开通</div>
              <div>如有需要请联系管理员</div>
            </div>
          </div>
        </div>
        <!--内容开始-->
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {}
  },
  methods: {},
  mounted () {}
}
</script>
<style lang="less">
  #indexopen{
    .homepagecontent{
      min-height: 300px;
    }
    .homepageconright {
      width: 100%;
      margin-top: 25px;
      min-height:600px;
      display: inline-block;
      background: #fff;
      vertical-align: top;
      border: 1px solid #f4f4f4;
      box-sizing: border-box;
    }
  }
</style>
