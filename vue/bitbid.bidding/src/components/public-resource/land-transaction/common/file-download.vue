<template>
  <div class="table_box">
    <el-form :model="fileForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="" prop="">
        <span @click="doenloadFile" style="color: #00aaff" :title="fileObjecit.fileName">{{fileObjecit.fileName}}</span>
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
  props: ['fileObjecit'],
  methods: {
    doenloadFile () {
      downloadFileMining(this.fileObjecit.fileName, this.fileObjecit.relativePath)
    }
  }
}
</script>
