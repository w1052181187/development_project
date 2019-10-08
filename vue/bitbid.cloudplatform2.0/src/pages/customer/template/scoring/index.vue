<template>
  <div>
    <el-form :model="submitForm" ref="submitForm">
      <el-row>
        <el-col :span="8">
          <el-form-item label="评估模板：" label-width="80px">
            <el-select v-model="selectedTemplateId" placeholder="请选择">
              <el-option
                v-for="item in templates"
                :key="item.objectId"
                :label="item.name"
                :value="item.objectId">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-table
        class="addcon"
        :data="submitForm.itemScores"
        border
        v-loading="loading"
        style="width: 100%"
        :span-method="objectSpanMethod"
        show-summary
        :summary-method="getSummaries"
        header-cell-class-name="tableheader">
        <el-table-column
          prop="index"
          label="序号"
          width="80"
          align="center">
        </el-table-column>
        <el-table-column
          prop="parentName"
          label="评估项分组"
          width="150"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="name"
          label="评估项"
          width="200"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column label="描述" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <el-form-item :prop="'itemScores.'+ scope.$index + '.description'" :rules="rules.simpleText">
              <el-input v-model="scope.row.description" clearable placeholder="请输入描述">
                <i class="el-icon-edit el-input__icon" slot="suffix"></i>
              </el-input>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="分值" width="300" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-form-item :prop="'itemScores.'+ scope.$index + '.score'" :rules="rules.score">
              <el-input v-model="scope.row.score" clearable placeholder="请输入分值">
                <i class="el-icon-edit el-input__icon" slot="suffix"></i>
              </el-input>
            </el-form-item>
          </template>
        </el-table-column>
      </el-table>
      <p class="red">打分参考：优秀8-10，良好6-8，尚可4-6，较差2-4，很差0-2</p>
      <div class="submit-radio">
        <el-button type="primary" @click="submit" v-loading="submitLoading">保存</el-button>
      </div>
    </el-form>
  </div>
</template>
<script>
import {template, itemScore} from '@/api/customer'
export default {
  data () {
    let validateScore = (rule, value, callback) => {
      const reg = /^([0-9]|10)$/
      if (!value) {
        callback()
      } else if (!reg.test(value)) {
        callback(new Error('输入有误，请重新录入！'))
      } else {
        callback()
      }
    }
    return {
      submitForm: {
        managementCode: this.$route.params.code,
        itemScores: []
      },
      submitLoading: false,
      loading: false,
      templates: [],
      selectedTemplateId: null,
      rowspanInfos: [],
      // 表单验证验证
      rules: {
        simpleText: [
          {min: 1, max: 200, message: '长度在1~200个字符', trigger: 'blur'}
        ],
        score: [
          {validator: validateScore, trigger: 'blur'}
        ]
      }
    }
  },
  watch: {
    selectedTemplateId (value) {
      if (value) {
        // 初始化评估项数据
        this.queryItems()
      }
    }
  },
  methods: {
    // 初始化模板下拉框数据
    initData () {
      let queryModel = {
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        managementCode: this.$route.params.code
      }
      template.queryAllByEnterId(queryModel).then(res => {
        this.templates = res.data.data
        // 初始没有选择的话，默认选择第一个
        this.selectedTemplateId = res.data.templateId || this.templates[0].objectId
      })
    },
    // 初始化评估项数据
    queryItems () {
      this.loading = true
      template.queryOne(this.selectedTemplateId).then(res => {
        this.loading = false
        this.wrapTableData(res.data.data.evaluationItems)
      })
    },
    // 初始化评估项得分
    queryItemScores () {
      let queryModel = {
        managementCode: this.$route.params.code,
        templateId: this.selectedTemplateId,
        isDelete: 0
      }
      itemScore.queryList(queryModel).then(res => {
        let scores = res.data.data
        this.submitForm.itemScores.forEach(item => {
          scores.forEach(score => {
            if (item.itemId === score.itemId) {
              item.description = score.description
              item.score = score.score
            }
          })
        })
      })
    },
    // 包装数据
    wrapTableData (list) {
      this.submitForm.itemScores = []
      this.rowspanInfos = []
      if (list && list.length) {
        let index = 0
        list.forEach((item, listIndex) => {
          this.rowspanInfos[index] = item.children.length
          if (item.children.length) {
            item.children.forEach(child => {
              let itemScore = {
                index: listIndex + 1,
                parentName: item.name,
                name: child.name,
                managementCode: this.$route.params.code,
                itemId: child.objectId,
                templateId: this.selectedTemplateId,
                description: '',
                score: ''
              }
              this.submitForm.itemScores.push(itemScore)
              index++
            })
          }
        })
      }
      this.queryItemScores()
    },
    // 计算合并项
    objectSpanMethod ({ row, column, rowIndex, columnIndex }) {
      if (this.rowspanInfos.length) {
        if (columnIndex === 0 || columnIndex === 1) {
          if (this.rowspanInfos[rowIndex]) {
            return {
              rowspan: this.rowspanInfos[rowIndex],
              colspan: 1
            }
          } else {
            return {
              rowspan: 0,
              colspan: 0
            }
          }
        }
      }
    },
    // 计算总得分
    getSummaries (param) {
      const { columns, data } = param
      const sums = []
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '总得分'
        }
        if (index > 0 && index < 4) {
          sums[index] = ''
        }
        if (index === 4) {
          const values = data.map(item => Number(item.score))
          if (!values.every(value => isNaN(value))) {
            sums[index] = values.reduce((prev, curr) => {
              const value = Number(curr)
              if (!isNaN(value)) {
                return prev + curr
              } else {
                return prev
              }
            }, 0)
            sums[index] += ' 分'
          } else {
            sums[index] = 'N/A'
          }
        }
      })
      return sums
    },
    // 表单提交
    submit () {
      this.$refs['submitForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认要提交吗?', '提示', {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.submitLoading = true
            this.submitForm.templateId = this.selectedTemplateId
            itemScore.update(this.submitForm).then(res => {
              this.submitLoading = false
            })
          }).catch(() => {
            this.loading = false
            return false
          })
        }
      })
    }
  },
  mounted () {
    // 初始化
    this.initData()
  }
}
</script>

<style scoped>
  .addcon .el-form-item__content{
    margin-left: 0px;
  }
</style>
