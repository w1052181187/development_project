<template>
  <div class="cloudcontent" id="cloud_information">
    <div class="main">
      <el-form :model="updateForm" :rules="rules" ref="updateForm" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="12">
            <el-form-item label="信息标题："  prop="agencyContractCode">
              <el-input v-model="updateForm.agencyContractCode"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="信息类型："  prop="agencyContractCode">
              <el-select v-model="value" class="formselect">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="信息内容：">
              <editor ref="ueditor" class="ueditor" :editread="editread"></editor>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitBtn('updateForm')">提交</el-button>
          <el-button type="primary" @click="saveBtn('updateForm')">保存</el-button>
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
      updateForm: {
        fileInformations: {}
      },
      // 富文本
      editread: false,
      options: [{
        value: 1,
        label: '企业动态'
      }, {
        value: 2,
        label: '通知公告'
      }],
      value: 1
    }
  },
  methods: {
  }
}
</script>
<style lang="less">
  #cloud_information {
    .formselect{
      width: 100%;
    }
  }
</style>
