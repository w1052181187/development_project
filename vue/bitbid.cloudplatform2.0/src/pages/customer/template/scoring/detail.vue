<template>
  <div>
    <el-form :model="submitForm" ref="submitForm">
      <el-row>
        <el-col :span="8">
          <el-form-item label="评估模板：" label-width="80px">
            <span>{{template.name || '---'}}</span>
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
        <el-table-column
          prop="description"
          label="描述"
          :formatter="simpleFormatData"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="score"
          label="分值"
          width="300"
          :formatter="simpleFormatData"
          show-overflow-tooltip>
        </el-table-column>
      </el-table>
    </el-form>
  </div>
</template>
<script>
import {template, itemScore} from '@/api/customer'
export default {
  data () {
    return {
      submitForm: {
        itemScores: []
      },
      loading: false,
      template: {},
      rowspanInfos: []
    }
  },
  props: ['templateId'],
  watch: {
    templateId (value) {
      if (value) {
        this.initData()
      }
    }
  },
  methods: {
    // 初始化模板下拉框数据
    initData () {
      this.loading = true
      template.queryOne(this.templateId).then(res => {
        this.loading = false
        this.template = res.data.data
        this.wrapTableData(res.data.data.evaluationItems)
      })
    },
    // 初始化评估项得分
    queryItemScores () {
      let queryModel = {
        managementCode: this.$route.params.code,
        templateId: this.templateId,
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
                itemId: child.objectId,
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
    // 普通格式化数据，空的时候展示"---"
    simpleFormatData (row, col, cellValue) {
      return cellValue || '---'
    }
  }
}
</script>

<style scoped>
  .addcon .el-form-item__content{
    margin-left: 0px;
  }
</style>
