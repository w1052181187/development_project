<template>
  <div class="enterInfo" id="uncertificated">
    <img src="../../../static/images/enterprise/uncertificated.png"/>
    <el-button @click="goCertificated">去认证</el-button>
  </div>
</template>

<script>
export default {
  name: 'uncertificated',
  methods: {
    goCertificated () {
      this.$emit('childFn', true)
    }
  },
  mounted () {
  }
}
</script>

<style scoped>
#uncertificated{
  width: 100%;
  overflow: hidden;
  text-align: center;
  position: relative;
}
img{
  max-width: 100%;
}
  .el-button{
    position: absolute;
    bottom: 60px;
    left: 50%;
    margin-left: -101px;
    width: 202px;
    height: 61px;
    line-height: 61px;
    padding: 0;
    background: none;
    border: 1px solid #fff;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;
    color: #fff;
    font-size: 16px;
  }
.el-button:hover{
  background: #ffffff;
  color: #08958c;
}
</style>
