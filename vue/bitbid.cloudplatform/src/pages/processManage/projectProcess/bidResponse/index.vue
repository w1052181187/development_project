<template>
  <div class="cloudcontent" id="cloud_processtype">
    <div class="box">
      <div class="topmain">
        招标文件费用
        <!--按钮-->
        <el-button class="addbutton" @click="handleAddCost" v-if="operationFlag">
          <span> + 添加</span>
        </el-button>
        <!--按钮-->
      </div>
      <el-table
        :data="costInfoData"
        border>
        <el-table-column
          type="index"
          label="序号"
          width="50">
        </el-table-column>
        <el-table-column
          prop="bidderName"
          label="投标人">
        </el-table-column>
        <el-table-column
          prop="paymentStatus"
          label="是否缴费"
          width="90">
          <template slot-scope="scope">
            <span  v-if="scope.row.paymentStatus === 1">是</span>
            <span  v-if="scope.row.paymentStatus === 0">否</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="amount"
          label="缴费金额(元)"
          width="120">
        </el-table-column>
        <el-table-column
          prop="paymentMethod"
          label="缴费方式"
          width="90">
          <template slot-scope="scope">
            <span  v-if="scope.row.paymentMethod === 1">支付宝</span>
            <span  v-if="scope.row.paymentMethod === 2">微信</span>
            <span  v-if="scope.row.paymentMethod === 3">金额</span>
            <span  v-if="scope.row.paymentMethod === 4">银联</span>
            <span  v-if="scope.row.paymentMethod === 5">支票</span>
            <span  v-if="scope.row.paymentMethod === 6">保函</span>
            <span  v-if="scope.row.paymentMethod === 9">其他</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="auditStatus"
          label="提交状态"
          width="90">
          <template slot-scope="scope">
            <span  v-if="scope.row.auditStatus === 0">已保存</span>
            <span  v-if="scope.row.auditStatus === 1">待审批</span>
            <span  v-if="scope.row.auditStatus === 2">已审批</span>
            <span  v-if="scope.row.auditStatus === 3">已驳回</span>
            <span  v-if="scope.row.auditStatus === 4">已提交</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="paymentTime"
          label="缴费时间"
          width="160">
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="180">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="handleDetailCost(scope)">查看</el-button>
            <el-button type="text" size="small" v-if="scope.row.auditStatus === 0 || scope.row.auditStatus === 3" @click="handleEditCost(scope)">编辑</el-button>
            <el-button type="text" size="small" v-if="scope.row.auditStatus === 0 || scope.row.auditStatus === 3" @click="handleDel(scope, 'cost')">删除</el-button>
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
        @current-change="handleCurrentChange">
      </el-pagination>
      <!--分页-->
    </div>
    <div class="box">
      <div class="topmain">
        投标文件递交情况
        <!--按钮-->
        <el-button class="addbutton" @click="handleAddFile" v-if="operationFlag">
          <span> + 添加</span>
        </el-button>
        <!--按钮-->
      </div>
      <el-table
        :data="fileInfoData"
        border>
        <el-table-column
          type="index"
          label="序号"
          width="50">
        </el-table-column>
        <el-table-column
          prop="fileName"
          label="投标文件名称">
        </el-table-column>
        <el-table-column
          prop="auditStatus"
          label="提交状态"
          width="90">
          <template slot-scope="scope">
            <span  v-if="scope.row.auditStatus === 0">已保存</span>
            <span  v-if="scope.row.auditStatus === 1">待审批</span>
            <span  v-if="scope.row.auditStatus === 2">已审批</span>
            <span  v-if="scope.row.auditStatus === 3">已驳回</span>
            <span  v-if="scope.row.auditStatus === 4">已提交</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="submitTime"
          label="提交时间"
          width="120">
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="180">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="handleDetailFile(scope)">查看</el-button>
            <el-button type="text" size="small" v-if="scope.row.auditStatus === 0 || scope.row.auditStatus === 3" @click="handleEditFile(scope)">编辑</el-button>
            <el-button type="text" size="small" v-if="scope.row.auditStatus === 0 || scope.row.auditStatus === 3" @click="handleDel(scope, 'file')">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分页-->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="fileTotal"
        :page-size='filePageSize'
        :current-page.sync="fileCurrentPage"
        @current-change="handleFileCurrentChange">
      </el-pagination>
      <!--分页-->
    </div>
    <div class="box">
      <div class="topmain">
        保证金费用
        <!--按钮-->
        <el-button class="addbutton" @click="handleAdd" v-if="operationFlag">
          <span> + 添加</span>
        </el-button>
        <!--按钮-->
      </div>
      <el-table
        :data="bondInfoData"
        border>
        <el-table-column
          type="index"
          label="序号"
          width="50">
        </el-table-column>
        <el-table-column
          prop="bidderName"
          label="投标人">
        </el-table-column>
        <el-table-column
          prop="paymentStatus"
          label="是否缴费"
          width="90">
          <template slot-scope="scope">
            <span  v-if="scope.row.paymentStatus === 1">是</span>
            <span  v-if="scope.row.paymentStatus === 0">否</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="amount"
          label="缴费金额(元)"
          width="120">
        </el-table-column>
        <el-table-column
          prop="paymentMethod"
          label="缴费方式"
          width="90">
          <template slot-scope="scope">
            <span  v-if="scope.row.paymentMethod === 1">支付宝</span>
            <span  v-if="scope.row.paymentMethod === 2">微信</span>
            <span  v-if="scope.row.paymentMethod === 3">金额</span>
            <span  v-if="scope.row.paymentMethod === 4">银联</span>
            <span  v-if="scope.row.paymentMethod === 5">支票</span>
            <span  v-if="scope.row.paymentMethod === 6">保函</span>
            <span  v-if="scope.row.paymentMethod === 9">其他</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="auditStatus"
          label="提交状态"
          width="90">
          <template slot-scope="scope">
            <span  v-if="scope.row.auditStatus === 0">已保存</span>
            <span  v-if="scope.row.auditStatus === 1">待审批</span>
            <span  v-if="scope.row.auditStatus === 2">已审批</span>
            <span  v-if="scope.row.auditStatus === 3">已驳回</span>
            <span  v-if="scope.row.auditStatus === 4">已提交</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="paymentTime"
          label="缴费时间"
          width="160">
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="180">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="handleDetail(scope)">查看</el-button>
            <el-button type="text" size="small" v-if="scope.row.auditStatus === 0 || scope.row.auditStatus === 3" @click="handleEdit(scope)">编辑</el-button>
            <el-button type="text" size="small" v-if="scope.row.auditStatus === 0 || scope.row.auditStatus === 3" @click="handleDel(scope, 'bond')">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分页-->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="bondTotal"
        :page-size='bondPageSize'
        :current-page.sync="bondCurrentPage"
        @current-change="handleBondCurrentChange">
      </el-pagination>
      <!--分页-->
    </div>
  </div>
</template>
<script>
import {costInfo, resultInfo} from '@/api/cloudplatform'
export default {
  components: {
  },
  props: ['tenderSystemCode', 'sectionSystemCode', 'chooseSectionSystemCode', 'operationFlag'],
  data () {
    return {
      costInfoData: [],
      fileInfoData: [],
      bondInfoData: [],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 100, // 总条数
      pageSize: 10, // 每页展示条数
      fileCurrentPage: 1,
      filePageNo: 0,
      fileTotal: null, // 总条数
      filePageSize: 10, // 每页展示条数
      bondCurrentPage: 1,
      bondPageNo: 0,
      bondTotal: null, // 总条数
      bondPageSize: 10 // 每页展示条数
    }
  },
  watch: {
    $route: 'init'
  },
  methods: {
    // 编辑
    handleEdit (scope) {
      this.$router.push({path: `/processManage/projectProcess/bidresponse/update_bond`, query: {objectId: scope.row.objectId}})
    },
    handleAdd () {
      this.$router.push({path: `/processManage/projectProcess/bidresponse/add_bond`,
        query: {sectionSystemCode: this.chooseSectionSystemCode ? this.chooseSectionSystemCode : this.sectionSystemCode,
          tenderSystemCode: this.tenderSystemCode}})
    },
    handleDetail (scope) {
      this.$router.push({path: `/processManage/projectProcess/bidresponse/bond_detail/${scope.row.objectId}`})
    },
    // 编辑
    handleEditCost (scope) {
      this.$router.push({path: `/processManage/projectProcess/bidresponse/update_cost`, query: {objectId: scope.row.objectId}})
    },
    handleAddCost () {
      this.$router.push({path: `/processManage/projectProcess/bidresponse/add_cost`,
        query: {sectionSystemCode: this.chooseSectionSystemCode ? this.chooseSectionSystemCode : this.sectionSystemCode,
          tenderSystemCode: this.tenderSystemCode}})
    },
    handleDetailCost (scope) {
      this.$router.push({path: `/processManage/projectProcess/bidresponse/cost_detail/${scope.row.objectId}`})
    }, // 编辑
    handleEditFile (scope) {
      this.$router.push({path: `/processManage/projectProcess/bidresponse/update_file`, query: {objectId: scope.row.objectId}})
    },
    handleAddFile () {
      this.$router.push({path: `/processManage/projectProcess/bidresponse/add_file`,
        query: {sectionSystemCode: this.chooseSectionSystemCode ? this.chooseSectionSystemCode : this.sectionSystemCode,
          tenderSystemCode: this.tenderSystemCode}})
    },
    handleDetailFile (scope) {
      this.$router.push({path: `/processManage/projectProcess/bidresponse/file_detail/${scope.row.objectId}`})
    },
    handleDel (scope, type) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        // **
        if (Object.is(type, 'cost')) {
          costInfo.deleteById(scope.row.objectId).then(res => {
            this.getCostInfoList()
          })
        } else if (Object.is(type, 'bond')) {
          costInfo.deleteById(scope.row.objectId).then(res => {
            this.getSignUpInfoList()
          })
        } else if (Object.is(type, 'file')) {
          resultInfo.deleteById(scope.row.objectId).then(res => {
            this.getResultInfoList()
          })
        }
      }).catch(() => {
        return false
      })
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getCostInfoList()
    },
    handleFileCurrentChange (nowNum) {
      this.fileCurrentPage = nowNum
      this.filePageNo = (nowNum - 1) * this.filePageSize
      this.getFileInfoList()
    },
    handleBondCurrentChange (nowNum) {
      this.bondCurrentPage = nowNum
      this.bondPageNo = (nowNum - 1) * this.bondPageSize
      this.getBondInfoList()
    },
    getCostInfoList () {
      let query = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        tenderSystemCode: this.tenderSystemCode,
        sectionSystemCode: this.chooseSectionSystemCode ? this.chooseSectionSystemCode : this.sectionSystemCode,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        type: 2
      }
      costInfo.getList(query).then(res => {
        let data = res.data.costInfoList
        if (data) {
          this.costInfoData = data.list
          this.total = data.total
        }
      })
    },
    getFileInfoList () {
      let query = {
        pageNo: this.filePageNo,
        pageSize: this.filePageSize,
        tenderSystemCode: this.tenderSystemCode,
        sectionSystemCode: this.chooseSectionSystemCode ? this.chooseSectionSystemCode : this.sectionSystemCode,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        type: 3
      }
      resultInfo.getList(query).then(res => {
        let data = res.data.resultInfoList
        if (data) {
          this.fileInfoData = data.list
          this.fileInfoData.map((item) => {
            if (item.fileInformationList.length > 0) {
              item.fileName = item.fileInformationList[0].fileName
            }
            return item
          })
          this.fileTotal = data.total
        }
      })
    },
    getBondInfoList () {
      let query = {
        pageNo: this.bondPageNo,
        pageSize: this.bondPageSize,
        tenderSystemCode: this.tenderSystemCode,
        sectionSystemCode: this.chooseSectionSystemCode ? this.chooseSectionSystemCode : this.sectionSystemCode,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        type: 3
      }
      costInfo.getList(query).then(res => {
        let data = res.data.costInfoList
        if (data) {
          this.bondInfoData = data.list
          this.bondTotal = data.total
        }
      })
    },
    init () {
      this.getCostInfoList()
      this.getFileInfoList()
      this.getBondInfoList()
    }
  },
  mounted () {
    this.init()
  }
}
</script>
<style lang="less">
  #cloud_processtype{
  }
</style>
