<template>
  <div id="indexbox">
    <div class="headertitle">首 页</div>
    <assigneeIndex v-if="isNotTrans"></assigneeIndex>
    <transferorIndex v-else></transferorIndex>
  </div>
</template>
<script>
import transferorIndex from './transferor/index-assembly.vue'
import assigneeIndex from './assignee/index-assembly.vue'
export default {
  components: {
    transferorIndex,
    assigneeIndex
  },
  data () {
    return {
      isNotTrans: true // 自定义显示出让人或者受让人
    }
  },
  methods: {
    indexList () {
      let that = this
      if (that.$store.getters.authUser.userType !== 1) {
        if (that.$store.getters.authUser.subjectIds !== null && that.$store.getters.authUser.subjectIds !== undefined) {
          that.$store.getters.authUser.subjectIds.map(item => {
            if (item === 10008) {
              that.isNotTrans = true // 受让人
            } else if (item === 10009) {
              that.isNotTrans = false // 出让人
            }
          })
        }
      }
      if (Number(this.$route.query.roledsType) === 1) {
        this.isNotTrans = false
        return false
      } else if (Number(this.$route.query.roledsType) === 2) {
        this.isNotTrans = true
        return false
      }
    }
  },
  mounted () {
    this.indexList()
  }
}
</script>
<style lang="less">
  #indexbox{
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
    .contentbigbox{
      width: 100%;
      min-height: 770px;
      background:#fff;
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
    .statusbox{
      display: block;
      width: 100%;
      text-align:center;
      color:#7b7e8b;
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
    .pagebox{
      margin-top: 15px;
      text-align: right;
    }
    .el-table .cell {
      box-sizing: border-box;
      word-break: break-all;
      line-height: 23px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      height: auto!important;
    }
    .statusbigbox button{
      float: none;
    }
  }
</style>
