<template>
  <div class="formmian">
    <el-form :model="approveForm" :rules="rules" ref="approveForm" label-width="100px" class="demo-sectionForm">
      <el-row>
        <el-col>
          <el-form-item label="审批结果：" prop="approvrResult">
            <template>
              <el-radio v-model="approveForm.approvrResult" :label="acceptResultsRadio.label"
                        v-for="(acceptResultsRadio,index) in radioAcceptResults" :key="index"
              @change="changeResult">{{acceptResultsRadio.name}}</el-radio>
            </template>
          </el-form-item>
        </el-col>
        <el-col>
          <el-form-item label="审批意见：" prop="approvrOpinion">
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
  props: {
    passLabel: {
      type: Number,
      default: 4
    },
    notPassLabel: {
      type: Number,
      default: 5
    }
  },
  data () {
    let validateOpinion = (rule, value, callback) => {
      if (this.approveForm.approvrResult === this.notPassLabel && value === '') {
        callback(new Error('请输入审批意见'))
      } else {
        callback()
      }
    }
    return {
      approveForm: {
        approverName: this.$store.getters.authUser.userName,
        approvrResult: '',
        approvrOpinion: ''
      },
      radioAcceptResults: [
        {
          label: this.notPassLabel,
          name: '不通过'
        },
        {
          label: this.passLabel,
          name: '通过'
        }
      ],
      rules: {
        approvrResult: [
          { required: true, message: '请选择审批结果', trigger: 'change' }
        ],
        approvrOpinion: [
          { validator: validateOpinion, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleValidate () {
      this.$refs.approveForm.validate((valid) => {
        if (valid) {
          this.$emit('valid', true)
        } else {
          return false
        }
      })
    },
    changeResult () {
      this.$refs.approveForm.validateField('approvrOpinion')
      this.$refs.approveForm.validateField('approvrResult')
      this.$emit('approveForm', this.approveForm)
    }
  },
  mounted () {}
}
</script>
<style scoped>

</style>
