<template>
  <div>
    <el-table
      :data="tableData"
      border
      style="width: 100%"
      v-loading="loading"
      header-cell-class-name="tableheader">
      <el-table-column
        type="index"
        label="序号"
        width="80"
        :index='computedIndex'
        align="center">
      </el-table-column>
      <el-table-column
        prop="tenderProjectName"
        label="项目"
        align="center"
        :formatter="simpleFormatData"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="bidSectionName"
        label="标段"
        align="center"
        :formatter="simpleFormatData"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="tendererName"
        label="招标人"
        align="center"
        :formatter="simpleFormatData"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="tenderProjectClassifyStr"
        label="标段类型"
        align="center"
        :formatter="simpleFormatData"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="reviewTime"
        label="评审时间"
        align="center"
        :formatter="formatDate"
        show-overflow-tooltip>
      </el-table-column>
    </el-table>
    <!--分页-->
    <el-pagination
      class="project_page"
      background
      layout="prev, pager, next"
      :total="page.total"
      :page-size='page.pageSize'
      :current-page.sync="page.currentPage"
      @current-change="handlePage"
      @next-click="handlePage">
    </el-pagination>
    <!--分页-->
  </div>
</template>
<script>
import {expertProject} from '@/api/resource'
import {dateFormat} from '@/assets/js/common'
import * as bidsType from '@/assets/js/constants'
export default {
  name: 'cooperate',
  data () {
    return {
      loading: false,
      tableData: [],
      page: {
        pageSize: 10,
        pageNo: 0,
        total: 0, // 总条数
        currentPage: 1
      },
      // 标段类型
      bidsTypeOptions: bidsType.bidsType
    }
  },
  props: ['relatedCode'],
  watch: {
    relatedCode (value) {
      if (value) {
        this.getTableData()
      }
    }
  },
  methods: {
    /** 获取列表数据 */
    getTableData () {
      this.loading = true
      let queryModel = {
        pageNo: this.page.pageNo,
        pageSize: this.page.pageSize,
        expertCode: this.relatedCode,
        isDelete: 0
      }
      expertProject.queryList(queryModel).then(res => {
        this.loading = false
        this.tableData = res.data.data.list
        this.page.total = res.data.data.total
        // 包装标段分类
        this.tableData.forEach(item => { item.tenderProjectClassifyStr = this.getClassifyInfo(item.tenderProjectClassifyCode.split('-')) })
      })
    },
    /** 获取招标项目扩展信息 */
    getClassifyInfo (list) {
      let str = ''
      let firstId = this.getLabelName(this.bidsTypeOptions, list[0])
      let secondId = this.getLabelName(this.bidsTypeOptions, list[1])
      let thirdId = this.getLabelName(this.bidsTypeOptions, list[2])
      let fourthId = this.getLabelName(this.bidsTypeOptions, list[3])
      if (firstId.node) {
        str += `${firstId.node.label}`
        str += '-'
      }
      if (secondId.node) {
        str += `${secondId.node.label}`
        str += '-'
      }
      if (thirdId.node) {
        str += `${thirdId.node.label}`
        str += '-'
      }
      if (fourthId.node) {
        str += `${fourthId.node.label}`
      }
      return str
    },
    /** treeData [原始数据集]， key 传入的key值 */
    getLabelName (treeData, key) {
      let parentNode = null
      let node = null
      function getTreeDeepArr (treeData, key) {
        for (let i = 0; i < treeData.length; i++) {
          // 1.如果没有子节点 beark
          if (node) {
            break
          }
          // 定义需要操作的数据
          let obj = treeData[i]
          // 没有节点就下一个
          if (!obj || !obj.value) {
            continue
          }
          // 2.有节点就继续找，一直递归
          if (obj.value === key) {
            node = obj
            break
          } else {
            // 3.如果有子节点就继续找
            if (obj.children) {
              // 4.递归前，记录当前节点，作为parentNode
              parentNode = obj
              // 递归
              getTreeDeepArr(obj.children, key)
            } else {
              // 跳出当前递归
              continue
            }
          }
        }
        // 如果没有找到父节点，置为null
        if (!node) {
          parentNode = null
        }
        // 返回结果
        return {
          node: node,
          parentNode: parentNode
        }
      }
      return getTreeDeepArr(treeData, key)
    },
    // 普通格式化数据，空的时候展示"---"
    simpleFormatData (row, col, cellValue) {
      return cellValue || '---'
    },
    // 格式化时间
    formatDate (row, col, cellValue) {
      return cellValue ? dateFormat(cellValue, 'yyyy-MM-dd') : '---'
    },
    // 序号计算
    computedIndex (index) {
      return index + (this.page.currentPage - 1) * this.page.pageSize + 1
    },
    // 表单分页
    handlePage (nowNum) {
      this.page.currentPage = nowNum
      this.page.pageNo = (nowNum - 1) * this.page.pageSize
      this.getTableData()
    }
  }
}
</script>
<style lang="less">
#expert_cooperate {
  .search-box {
    margin-bottom: 10px;
  }
  .table-box,
  .dialog-box {
    .project_page {
      padding: 30px 5px;
      text-align: center;
    }
  }
}
</style>
