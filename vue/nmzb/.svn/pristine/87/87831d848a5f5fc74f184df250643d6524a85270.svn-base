<template>
  <div id="ckbidanno" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>关于协会</el-breadcrumb-item>
        <el-breadcrumb-item>会员单位</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <div class="save_box">
        <el-button type="success"  class="addbutton"  @click="saveBtn">{{btnName}}</el-button>
      </div>
      <div class="ueditor_box">
        <editor ref="ueditor" class="ueditor"></editor>
      </div>
    </div>
  </div>
</template>
<script>
import editor from '@/components/ueditor/ueditor.vue'
import { association } from '@/api'
export default {
  components: {
    editor
  },
  data () {
    return {
      btnName: '保存',
      entryFrom: {
        type: 35,
        content: ''
      }
    }
  },
  methods: {
    query () {
      let params = {
        pageNo: 0,
        pageSize: 10,
        isDelete: 0,
        type: 35
      }
      association.query(params).then((res) => {
        if (res.data.noticePageInfo.list.length > 0) {
          this.entryFrom = res.data.noticePageInfo.list[0]
          this.$refs.ueditor.setContent(this.entryFrom.content)
        }
      })
    },
    saveBtn () {
      if (!this.$refs.ueditor.getContent()) {
        this.$message({
          type: 'warning',
          message: '请输入内容'
        })
        return false
      }
      this.$confirm('确定要保存吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 保存
        this.entryFrom.type = 35
        this.entryFrom.content = this.$refs.ueditor.getContent()
        association.save(this.entryFrom).then(() => {
          this.query()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消保存'
        })
      })
    }
  },
  mounted () {
    this.query()
  }
}
</script>
<style lang="less">
  #ckbidanno{
    .select{
      width: 14%;
    }
    .search{
      width: 20%;
    }
    .el-form-item__label{
      width: 168px !important;
    }
    .el-form-item__content{
      margin-left: 168px !important;
      width: 60%;
    }
    .save_box{
      padding:0 15px;
      box-sizing: border-box;
    }
    .ueditor_box{
      margin-top: 40px;
    }
  }
</style>
