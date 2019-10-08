<template>
  <div class="table_box">
    <el-form :model="fileForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="" prop="">
        <span @click="doenloadFile" style="color: #00aaff" :title="fileTypesObjecit.fileName">{{fileTypesObjecit.fileName}}</span>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import {downloadFileMining} from '@/assets/js/common'
export default {
  data () {
    return {
      fileForm: {}
    }
  },
  props: ['fileTypesObjecit'],
  methods: {
    doenloadFile () {
      downloadFileMining(this.fileTypesObjecit.fileName, this.fileTypesObjecit.relativePath)
    }
  }
}
</script>
