<template>
  <div class="formmian">
    <el-form :model="approveForm"  label-width="100px" class="demo-sectionForm">
      <el-row>
        <el-col>
          <el-form-item label="审批结果：">
            <template>
              <el-radio v-model="approveForm.approvrResult" :label="acceptResultsRadio.label" v-for="(acceptResultsRadio,index) in radioAcceptResults" :key="index">{{acceptResultsRadio.name}}</el-radio>
            </template>
          </el-form-item>
        </el-col>
        <el-col>
          <el-form-item label="审批意见：">
            <el-input type="textarea" v-model="approveForm.approvrOpinion"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>
<script>
export default {
  components: {},
  data () {
    return {
      approveForm: {
        approverName: this.$store.getters.authUser.userName
      },
      radioAcceptResults: [
        {
          label: 0,
          name: '不通过'
        },
        {
          label: 1,
          name: '通过'
        }
      ]
    }
  },
  watch: {
    approveForm: function () {
      this.$emit('approveForm', this.approveForm)
    }
  },
  methods: {},
  mounted () {}
}
</script>
