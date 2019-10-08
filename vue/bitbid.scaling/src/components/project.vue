<template>
  <div class="proinfo-info-box">
    <div class="title-box">
      <p>项目信息</p>
    </div>
    <el-form :model="projectInfoForm" label-width="150px" class="project_box">
      <el-row>
        <el-col :span="12">
          <el-form-item label="项目名称：">
            <span>{{projectInfoForm.projectName}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="项目编号：">
            <span>{{projectInfoForm.projectCode}}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="包组名称：">
            <span>{{projectInfoForm.sectionName}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="包组编号：">
            <span>{{projectInfoForm.sectionCode}}</span>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
export default {
  name: '',
  props: ['projectInfoForm'],
  data () {
    return {
    }
  },
  methods: {
  },
  mounted () {
  }
}
</script>

<style lang="scss">
  .proinfo-info-box{
    background: #fff;
    margin-top: 15px;
    padding: 20px;
  }
  .project_box{
    .el-form-item__content{
      text-align: left;
    }
  }
</style>
