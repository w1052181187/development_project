<template>
  <div class="information-push">
    <div class="container-box">
      <img src="../../../../static/images/statistics/working.png" />
      <div>
        <span class="title">功能描述：</span>
        <span class="text">功能描述功能描述功能描述功能描述功能描述功能描述功能描述功能描述功能描述功能描述功能描述
          功能描述功能描述功能描述功能描述功能描述功能描述功能描述功能描述功能描述功能描述功能描述功能描述
          功能描述功能描述功能描述功能描述功能描述功能描述功能描述功能描述功能描述功能描述功能描述功能描述
          功能描述功能描述功能描述功能描述功能描述功能描述功能描述功能描述功能描述。
        </span>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  components: {
  },
  data () {
    return {}
  },
  created () {
  },
  methods: {
  }
}
</script>
<style scoped lang="less">
.information-push{
  display: table;
  height: 100%;
  padding: 0 20px;
}
.container-box{
  display: table-cell;
  vertical-align: middle;
  text-align: center;
  div{
    margin-top: 30px;
  }
  span{
    display: block;
    float: left;
    text-align: left;
  }
  .title{
    width: 80px;
    font-weight: bold;
  }
  .text{
    width: calc(100% - 80px);
  }
}
</style>
