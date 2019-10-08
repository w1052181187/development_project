<template>
  <div class="cloudcontent" id="cloud_webconpany">
    <div class="main">
      <el-form :model="updateForm" ref="updateForm" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="24">
            <el-form-item label="类型：">
              <span>公司简介</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="内容：">
              <editor ref="ueditor" class="ueditor" :editread="editread"></editor>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitBtn('updateForm')">确定</el-button>
          <el-button class="cancal" @click="$router.go(-1)">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/ueditor/ueditor.vue'
export default {
  components: {
    editor
  },
  data () {
    return {
      updateForm: {},
      // 富文本
      editread: false
    }
  },
  methods: {
  }
}
</script>
<style lang="less">
  #cloud_webconpany {
  }
</style>
