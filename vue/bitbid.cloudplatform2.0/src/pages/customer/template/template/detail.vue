<template>
  <div class="cloudcontent" id="add_template">
    <div class="main">
      <el-form :model="submitForm" label-width="120px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="评估模板名称："  prop="name">
              <span>{{submitForm.name}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态："  prop="name">
              <span>{{submitForm.status === 1 ? '启用' : '禁用'}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <div class="top_txt">
          <span>评估项：</span>
        </div>
        <el-table
          :data="tableData"
          border
          v-loading="loading"
          :span-method="objectSpanMethod"
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
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="name"
            label="评估项"
            show-overflow-tooltip>
          </el-table-column>
        </el-table>
        <el-form-item class="submit-radio">
          <el-button class="cancal" @click="cancel">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import {template} from '@/api/customer/template'
export default {
  data () {
    return {
      submitForm: {
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        itemIds: []
      },
      tableData: [],
      loading: false
    }
  },
  methods: {
    // 初始化
    initData () {
      this.loading = true
      template.queryOne(this.$route.params.objectId).then(res => {
        this.loading = false
        this.submitForm = res.data.data
        this.wrapTableData()
      })
    },
    // 包装数据
    wrapTableData () {
      let list = this.submitForm.evaluationItems
      this.tableData = []
      this.rowspanInfos = []
      if (list && list.length) {
        let index = 0
        list.forEach((item, listIndex) => {
          this.rowspanInfos[index] = item.children.length
          if (item.children.length) {
            item.children.forEach(child => {
              child.index = listIndex + 1
              child.parentName = item.name
              this.tableData.push(child)
              index++
            })
          }
        })
      }
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
    // 取消
    cancel () {
      this.$store.commit('delete_tabs', this.$route.fullPath)
      this.$router.push({path: '/customer/template'})
    }
  },
  mounted () {
    this.initData()
  }
}
</script>

<style lang="less">
  .top_txt{
    font-size: 14px;
    margin: 12px auto;
  }
</style>
