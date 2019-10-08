<template>
  <div class="cloudcontent expert-cooperate" id="expert_cooperate">
    <div class="main">
      <div>
        <strong style="display: inline-block; line-height: 40px;color: rgb(51, 51, 51); left: 2%;">专家姓名：{{expertName}}</strong>
        <el-button type="primary" class="addbutton" @click="showEditDialog"> + 增加合作记录</el-button>
        <edit :showVisible="showVisible" @showDialog="showEditDialog" :relatedCode="this.$route.params.relatedCode" @saveSuccess="getTableData"></edit>
      </div>
      <div class="table-box">
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
          <el-table-column
            label="操作"
            align="center">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                @click="logoff(scope.row)">删除</el-button>
            </template>
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
    </div>
  </div>
</template>
<script>
import {expert, expertProject} from '@/api/resource'
import {dateFormat} from '@/assets/js/common'
import Edit from './edit'
import * as bidsType from '@/assets/js/constants'
export default {
  name: 'cooperators',
  components: {
    Edit
  },
  data () {
    return {
      loading: false,
      nameLoading: false,
      expertName: '',
      queryModel: {
        expertCode: this.$route.params.relatedCode,
        isDelete: 0
      },
      tableData: [],
      page: {
        pageSize: 10,
        pageNo: 0,
        total: 0, // 总条数
        currentPage: 1
      },
      showVisible: false,
      // 标段类型
      bidsTypeOptions: bidsType.bidsType
    }
  },
  methods: {
    /** 初始化名称 */
    initExpertName () {
      this.nameLoading = true
      expert.queryName(this.$route.params.relatedCode).then(res => {
        this.nameLoading = false
        this.expertName = res.data.data || '---'
      })
    },
    /** 获取列表数据 */
    getTableData () {
      this.loading = true
      this.queryModel.pageNo = this.page.pageNo
      this.queryModel.pageSize = this.page.pageSize
      expertProject.queryList(this.queryModel).then(res => {
        this.loading = false
        this.tableData = res.data.data.list
        this.page.total = res.data.data.total
        // 包装标段分类
        this.tableData.forEach(item => { item.tenderProjectClassifyStr = this.getClassifyInfo(item.tenderProjectClassifyCode.split('-')) })
      })
    },
    /** 编辑弹框 */
    showEditDialog () {
      this.showVisible = !this.showVisible
    },
    logoff (row) {
      this.$confirm('确认要删除该条数据吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        // 执行删除
        expertProject.logoff(row.objectId).then(res => {
          if (res.data.resCode === '0000') {
            this.getTableData()
          }
        })
      }).catch(() => {
        return false
      })
    },
    /** 弹窗确定 */
    confirmSaveBtn () {},
    /** 跳转到添加项目 */
    jumpToAddProject () {},
    /** 关闭弹窗 */
    closeDialog () {
      this.$refs['ruleForm'].resetFields()
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
  },
  mounted () {
    // 初始化名称
    this.initExpertName()
    // 初始化数据
    this.getTableData()
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
