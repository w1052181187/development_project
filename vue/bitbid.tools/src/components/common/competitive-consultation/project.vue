<template>
    <div class="">
      <el-form :model="ruleForm" label-width="100px" class="zztdemo_ruleForm">
        <div class="proinfobox">
        <span class="line_bigbox"></span>
        <span class="title_bigbox">项目信息</span>
        <div class="formmian_projectbox">
          <el-row>
            <el-col :span="12">
              <el-form-item label="项目名称：">
                <span>{{ruleForm.projectName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="项目编号：">
                <span>{{ruleForm.projectCode}}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </div>
      </el-form>
    </div>
</template>
<script>
import { consultingReviewControl } from '@/api'
export default {
  data () {
    return {
      ruleForm: {}
    }
  },
  methods: {
    // 获取磋商人员列表数据
    projectLists () {
      consultingReviewControl.projectList().then((res) => {
        this.ruleForm = res.data.task
      })
    }
  },
  mounted () {
    this.projectLists()
  }
}
</script>
<style scoped>

</style>
