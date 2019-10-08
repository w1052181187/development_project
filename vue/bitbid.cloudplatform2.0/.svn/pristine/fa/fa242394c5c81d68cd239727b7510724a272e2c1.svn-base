<template>
  <el-dialog title="输单" :visible.sync="showVisible" width="30%" :before-close="cancel">
    <div class="dialogmain">
      <p class="text-center">
        <img src="../../../../../static/images/shudan.png"/>
      </p>
      <strong class="text-center">再接再厉哦！</strong>
    </div>
  </el-dialog>
</template>
<script>
export default {
  data () {
    return {
      submitLoading: false
    }
  },
  props: ['showVisible'],
  methods: {
    // 关闭
    cancel () {
      this.$emit('showDialog', 'dialogLoseVisible')
    }
  }
}
</script>
<style scoped>
  .dialogmain{
    width: 234px;
    overflow: hidden;
    text-align: center;
    margin: 20px auto;
  }
  .dialogmain strong{
    font-size: 18px;
    line-height: 36px;
  }
  .dialogmain p{
    margin: 10px 0;
  }
  .dialogmain p.checkbox{
    float: left;
    margin: 30px 0;
    line-height: 32px;
    text-align: left;
  }
</style>
