<template>
  <div class="cloudcontent" id="cloud_registProject">
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item class="active_bread">代理分配项目</el-breadcrumb-item>
    </el-breadcrumb>
    <!--面包屑-->
    <div class="main">
      <div class="topmain">
        <div class="seacher_box">
          <span>项目名称：</span>
          <el-input class="search" placeholder="请输入项目名称关键字" style="vertical-align: top"  v-model="searchForm.tenderProjectNameLike">
            <el-button  slot="append" icon="el-icon-search" type="" @click="search"></el-button>
          </el-input>
        </div>
      </div>
      <el-table
        :data="registProjectData"
        border
        style="width: 100%" header-cell-class-name="tableheader">
        <el-table-column
          type="index"
          label="序号"
          width="60"
          :index='dataIndex'
          align="center">
        </el-table-column>
        <el-table-column
          prop="tenderProjectCode"
          label="项目编号"
          width="100"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="tenderProjectName"
          label="项目名称"
          width="200"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="tenderProjectAddress"
          label="项目地址"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="tendererName"
          label="招标人"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="status"
          label="项目状态"
          width="100"
          :formatter="filterStatus"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="createDate"
          label="项目经理"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="200">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="updateBtn(scope)" v-if="scope.row.status === 3">分配项目负责人</el-button>
            <el-button type="text" size="small" @click="lookBtn(scope)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分页-->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size='pageSize'
        :current-page.sync="currentPage"
        @current-change="handleCurrentChange"
        @next-click="handleCurrentNext">
      </el-pagination>
      <!--分页-->
    </div>
  </div>
</template>
<script>
import * as region from '@/assets/js/region'
import * as industry from '@/assets/js/industry_two'
import {tenderProject} from '@/api/cloudplatform/index'
export default {
  components: {
  },
  data () {
    return {
      searchForm: {},
      registProjectData: [],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 100, // 总条数
      pageSize: 10, // 每页展示条数
      dataIndex: 1
    }
  },
  watch: {
    '$route': 'getTableData'
  },
  created () {
    this.getTableData()
  },
  methods: {
    /** 项目状态的展示：0.概况未提交；1.概况待审批；2.概况已驳回；3.未提交项目负责人（概况已审批/概况不需要审批）；4.详情未保存；5.详情未提交；6.详情待审批；7.详情已审批；8.详情已驳回；9.详情已提交（详情不需要审批）；10.项目进行中；11.项目已完成  */
    filterStatus (row) {
      if (row.status === 3) {
        return '待分配'
      } else {
        return '已分配'
      }
    },
    /** 获取列表数据 */
    getTableData () {
      tenderProject.getList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        statusList: '3,4,5,6,7,8,9,10,11',
        messageLike: this.searchForm.tenderProjectNameLike || null
      }).then((res) => {
        if (res.data.tenderProjectList && res.data.tenderProjectList.list) {
          this.registProjectData = res.data.tenderProjectList.list
          this.total = res.data.tenderProjectList.total
          // 获取项目地址信息
          this.registProjectData.forEach(item => {
            item.tenderProjectAddress = this.getTenderExpandsInfo(item.tenderExpandsInfo, 'address')
          })
        }
      })
    },
    getTenderExpandsInfo (tenderExpandsInfo, type) {
      let str = ''
      if (Object.is(type, 'address')) {
        let provinceId = this.getLabelName(region.CityInfo, tenderExpandsInfo.provinceId)
        let cityId = this.getLabelName(region.CityInfo, tenderExpandsInfo.cityId)
        let countyId = this.getLabelName(region.CityInfo, tenderExpandsInfo.countyId)
        if (provinceId.node) {
          str += `${provinceId.node.label}`
        }
        if (cityId.node) {
          str += `${cityId.node.label}`
        }
        if (countyId.node) {
          str += `${countyId.node.label}`
        }
      } else if (Object.is(type, 'industry')) {
        let industryTypeFirst = this.getLabelName(industry.industry, tenderExpandsInfo.provinceId)
        let industryTypeSecond = this.getLabelName(industry.industry, tenderExpandsInfo.cityId)
        if (industryTypeFirst.node) {
          str += `${industryTypeFirst.node.label}`
        }
        if (industryTypeSecond.node) {
          str += `${industryTypeSecond.node.label}`
        }
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
    /** 模糊查询方法 */
    search () {
      this.getTableData()
    },
    // 查看
    lookBtn (scope) {
      this.$router.push({path: `/processManage/agentProject/detail/${scope.row.objectId}`, query: {code: scope.row.code}})
    },
    // 分配项目负责人
    updateBtn (scope) {
      this.$router.push({path: `/processManage/agentProject/agent`, query: {code: scope.row.code}})
    },
    // 表单分页
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getTableData(parseInt(this.pageNo), parseInt(this.pageSize))
    },
    handleCurrentNext (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getTableData(parseInt(this.pageNo), parseInt(this.pageSize))
    }
  }
}
</script>
<style lang="less">
  #cloud_registProject {
    .tabletitles{
      background:#f7f8fa!important;
    }
  }
</style>
