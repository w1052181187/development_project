<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="intermange-page">
    <div v-if="this.$route.path == '/admin/resource/intermange'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>资源管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/resource/intermange' }">中介机构管理</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!-- 搜索区域start -->
      <div class="search-box search-warp">
        <el-form :model="ruleSearchForm" ref="ruleSearchForm" label-width="100px">
          <el-row :gutter="20">
            <el-col :span="5" style="width:300px">
              <el-form-item label="中介机构名称:">
                <el-input v-model="ruleSearchForm.name"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5" style="width:300px">
              <el-form-item label="中介类型:">
                <el-select v-model="ruleSearchForm.type" clearable placeholder="请选择中介类型">
                  <el-option
                    v-for="item in interTypeList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="width:300px">
              <el-form-item label="状态:">
                <el-select v-model="ruleSearchForm.status" clearable placeholder="请选择状态">
                  <el-option
                    v-for="item in statusList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="2" style="padding-left:30px;">
              <el-form-item>
                <el-button type="primary" @click="onSearch('ruleSearchForm')" icon="el-icon-search" style="margin-left:-70px;">搜索</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <!-- 搜索区域end -->
      <!-- 主体内容区域start -->
      <div class="maincont-box intermange-cont">
        <div class="intermange-add-btn">
          <el-button type="warning" icon="el-icon-plus" @click="handleAddClick" v-if="$store.getters.permissions.includes('/resource/intermange/operation')">新增</el-button>
        </div>
        <el-table
          :data="tableData"
          :row-class-name="tableRowClassName"
          border
          style="width: 100%">
          <el-table-column
            type="index"
            label="序号"
            align="center"
            :index="indexMethod"
            width="55">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="name"
            show-overflow-tooltip
            label="中介机构名称"
            width="120">
          </el-table-column>
          <el-table-column
            align="center"
            prop="type"
            label="中介类型"
            :formatter="filterType">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="interClassify"
            label="属地"
            width="200">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            show-overflow-tooltip
            prop="enterpriseExpend.address"
            label="地址"
            width="200">
          </el-table-column>
          <el-table-column
            align="center"
            prop="enterpriseExpend.contactsName"
            label="联系人"
            width="100">
          </el-table-column>
          <el-table-column
            align="center"
            prop="enterpriseExpend.phone"
            label="联系方式">
          </el-table-column>
          <el-table-column
            align="center"
            prop="status"
            label="状态"
            width="100">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.status"
                name="scope.row.name"
                active-color="#13ce66"
                inactive-color="#ff4949"
                :active-value="0"
                :inactive-value="1"
                @change="changeSwitch(scope.row)">
               </el-switch>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
            width="200">
            <template slot-scope="scope">
              <el-button @click="handleTableClick(scope.row,'look')" type="text" size="medium" v-if="$store.getters.permissions.includes('/resource/intermange/detail')">查看</el-button>
              <el-button @click="handleTableClick(scope.row,'edit')" type="text" size="medium" v-if="$store.getters.permissions.includes('/resource/intermange/operation')">修改</el-button>
              <el-button @click="handleTableClick(scope.row,'del')" type="text" size="medium" v-if="$store.getters.permissions.includes('/resource/intermange/operation')">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--分页-->
        <div class="block-page">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-sizes="pageSizeList"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
          </el-pagination>
        </div>
      </div>
      <!-- 主体内容区域end -->
    </div>
    <div v-else>
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
import {intermanage} from 'api/index'
import {interclassify} from 'api/resource/intermanage/interclassify'
export default {
  data () {
    return {
      ruleSearchForm: {}, // 搜索表单
      interTypeList: [
        {
          label: '招标代理机构',
          value: 4
        },
        {
          label: '造价咨询机构',
          value: 5
        },
        {
          label: '审计评估机构',
          value: 6
        },
        {
          label: '法律服务机构',
          value: 7
        }
      ], // 中介机构类型
      statusList: [
        {
          label: '激活',
          value: '0'
        },
        {
          label: '锁定',
          value: 1
        }
      ], // 状态
      tableData: [], // 表格数据
      pageNo: 0,
      currentPage: 1,
      pageSize: 10,
      total: null, // 总条数
      pageSizeList: [10, 20, 30, 40, 50]
    }
  },
  computed: {},
  created () {
    this.intermangeData()
    this.getCurrentValue()
  },
  watch: {
    $route: function () {
      if (this.$route.query.operation === 'add') {
        if (document.location.search.substring(15) === 'interStatus=true') {
          this.intermangeData()
        }
      } else if (this.$route.query.operation === 'edit') {
        if (document.location.search.substring(51) === 'interStatus=true') {
          this.intermangeData()
        }
      }
    }
  },

  /*
  * 方法集
  */
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 表单分页 */
    handleSizeChange (val) {
      var likeThis = this
      var pageSize = `${val}`
      this.pageNo = 0
      this.currentPage = 1
      this.pageSize = parseInt(pageSize)
      this.$nextTick(() =>
        this.intermangeData(this.pageNo, this.pageSize, function (resp) {
          likeThis.total = resp.data.Data.Total
        })
      )
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.intermangeData(parseInt(this.pageNo), parseInt(this.pageSize))
    },
    /** 进行操作后返回当前页 */
    getCurrentValue () {
      if (this.$route.query.pageNo && this.$route.query.pageSize && this.$route.query.currentPage) {
        this.currentPage = (this.$route.query.pageNo) / 10 + 1
        this.pageNo = this.$route.query.pageNo
        this.pageSize = parseInt(this.$route.query.pageSize)
        this.intermangeData(parseInt(this.pageNo), parseInt(this.pageSize))
      } else {
        this.intermangeData()
      }
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
    /** 新增 */
    handleAddClick () {
      this.$router.push({path: '/admin/resource/intermange/addinter?type=add'})
    },
    /** 表格操作 */
    handleTableClick (row, type) {
      if (type === 'look') {
        this.$router.push({path: '/admin/resource/intermange/detailsinter', query: {objectId: row.objectId, currentPage: this.currentPage, pageNo: this.pageNo, pageSize: this.pageSize}})
      } else if (type === 'edit') {
        this.$router.push({path: '/admin/resource/intermange/addinter?type=edit', query: {objectId: row.objectId, currentPage: this.currentPage, pageNo: this.pageNo, pageSize: this.pageSize}})
      } else if (type === 'del') {
        this.deleteClick(row)
      }
    },
    /** 删除 */
    deleteClick (row) {
      this.$confirm('此操作永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        intermanage.delete(row.objectId).then(() => {
          console.log(row.objectId)
          this.intermangeData(parseInt(this.pageNo), parseInt(this.pageSize))
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    /** 开关事件 */
    changeSwitch (row) {
      intermanage.update(row, this.status).then(() => {
        this.intermangeData()
      })
    },
    /** 搜索 */
    onSearch () {
      this.intermangeData()
    },
    /** 列表数据展示 */
    intermangeData () {
      intermanage.getList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        messageLike: this.ruleSearchForm.name || null,
        type: this.ruleSearchForm.type || null,
        types: '4,5,6,7',
        status: this.ruleSearchForm.status || null
      }).then((res) => {
        this.tableData = res.data.enterprises.list
        this.tableData.map((item) => {
          if (item.enterpriseExpend && item.enterpriseExpend.provinceId) {
            item.interClassify = ''
            item.interClassify += this.getLabelName(interclassify, item.enterpriseExpend.provinceId).node.label
          }
          if (item.enterpriseExpend && item.enterpriseExpend.cityId) {
            item.interClassify += this.getLabelName(interclassify, item.enterpriseExpend.cityId).node.label
          }
          if (item.enterpriseExpend && item.enterpriseExpend.countyId) {
            item.interClassify += this.getLabelName(interclassify, item.enterpriseExpend.countyId).node.label
          }
        })
        this.total = res.data.enterprises.total
      })
    },
    /** 格式化 */
    filterType (row, column) {
      let types = row.type
      if (types === 4) {
        return '招标代理机构'
      } else if (types === 5) {
        return '造价咨询机构'
      } else if (types === 6) {
        return '审计评估机构'
      } else if (types === 7) {
        return '法律服务机构'
      }
    }
  },
  /*
  * 实例创建完成
  */
  mounted () {
  },
  /*
  * 过滤
  */
  filters: {}
}
</script>
<style lang="less">
#intermange-page {
  /*搜索*/
  .search-warp {
    .el-form-item {
      margin-bottom: 0;
    }
  }
  .intermange-cont {
    margin-bottom: 130px;
    .intermange-add-btn {
      padding-bottom: 20px;
      text-align: left;
    }
  }
  .block-page {
    padding-top: 20px;
  }
}
</style>
