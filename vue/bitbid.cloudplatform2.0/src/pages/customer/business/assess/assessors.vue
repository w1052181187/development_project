<template>
  <div>
    <el-dialog title="选择评估人员" :visible.sync="showVisible" :before-close="cancel">
      <el-form :model="queryModel">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户姓名：" label-width="80px">
              <el-input class="search" placeholder="请输入姓名" style="vertical-align: top"  v-model="queryModel.nameLike">
                <el-button slot="append" icon="el-icon-search" type="" @click="getTableData"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-table :data="tableData"
                border
                v-loading="loading"
                style="width: 100%" header-cell-class-name="tableheader">
        <el-table-column
          type="index"
          label="序号"
          width="60"
          align="center">
        </el-table-column>
        <el-table-column
          prop="name"
          label="姓名"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="department.name"
          label="所属部门"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="checkbox"
          label="是否评估人"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <el-checkbox v-model="assessorIds" :label="scope.row.objectId">&nbsp;</el-checkbox>
          </template>
        </el-table-column>
        <el-table-column
          prop="radio"
          label="是否决策人"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <el-radio v-model="decisionMakerId" :label="scope.row.objectId">&nbsp;</el-radio>
          </template>
        </el-table-column>
      </el-table>
      <!--分页-->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="page.total"
        :page-size='page.pageSize'
        :current-page.sync="page.currentPage"
        @current-change="handlePage"
        @next-click="handlePage">
      </el-pagination>
      <!--分页-->
      <div class="selectbox">
        <p><span>评估人：{{assessors.length}}</span></p>
        <ul v-show="assessors.length">
          <li  v-for="item in assessors" :key="item.objectId"><span>{{item.name}}</span><i @click="delAssessor(item.objectId)"></i></li>
        </ul>
      </div>
      <div class="selectbox">
        <p><span>决策人：</span>必须指定一名决策人</p>
        <ul class="jc" v-show="decisionMaker.objectId">
          <li ><span>{{decisionMaker.name}}</span><i @click="delDecisionMaker"></i></li>
        </ul>
      </div>
      <div class="submit-radio">
        <el-button type="primary" @click="submit" v-loading="submitLoading">保存</el-button>
        <el-button type="primary" @click="cancel">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { business } from '@/api/customer'
import {user} from '@/api/system'
import { Message } from 'element-ui'
export default {
  data () {
    return {
      loading: false,
      submitLoading: false,
      tableData: [],
      page: {
        pageSize: 10,
        pageNo: 1,
        total: 0, // 总条数
        currentPage: 1
      },
      queryModel: {
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        isDelete: 0,
        type: 2,
        nameLike: '',
        account: ''
      },
      assessors: [],
      decisionMaker: {},
      assessorIds: [],
      decisionMakerId: null
    }
  },
  props: ['showVisible', 'relatedCode', 'projectName'],
  watch: {
    assessorIds (value) {
      if (value.length) {
        if (!value.find(item => item === this.decisionMakerId)) {
          this.decisionMakerId = null
        }
        let allAssessors = this.assessors.concat(this.tableData)
        this.assessors = []
        value.forEach(item => {
          this.assessors.push(allAssessors.find(obj => obj.objectId === item))
        })
      } else {
        this.assessors = []
        this.decisionMakerId = null
      }
    },
    decisionMakerId (value) {
      if (value) {
        this.decisionMaker = this.tableData.find(item => item.objectId === value)
        let assessorId = this.assessorIds.find(item => item === value)
        if (!assessorId) {
          this.assessorIds.push(value)
        }
      } else {
        this.decisionMaker = {}
      }
    }
  },
  methods: {
    // 用户列表数据
    getTableData () {
      this.queryModel.pageNum = this.page.pageNo
      this.queryModel.pageSize = this.page.pageSize
      this.loading = true
      user.queryList(this.queryModel).then((res) => {
        this.loading = false
        this.tableData = res.data.data
        this.page.total = res.data.page.totalRows
      })
    },
    // 删除评估人
    delAssessor (objectId) {
      this.assessorIds = this.assessorIds.filter(item => item !== objectId)
    },
    // 删除决策人
    delDecisionMaker () {
      this.decisionMakerId = null
    },
    // 提交
    submit () {
      if (this.assessors.length && this.decisionMaker.objectId) {
        this.$confirm('确认要提交吗?', '提示', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.submitLoading = true
          let submitForm = {
            code: this.relatedCode,
            projectName: this.projectName,
            assessorIds: this.assessorIds,
            decisionMakerId: this.decisionMakerId
          }
          business.submitAssess(submitForm).then(res => {
            this.submitLoading = false
            if (res.data.resCode === '0000') {
              this.cancel()
              this.$emit('saveSuccess')
            }
          })
        }).catch(() => {
          this.loading = false
          return false
        })
      } else {
        Message({
          message: '请选择评估人和决策人',
          type: 'error',
          duration: 5 * 1000
        })
      }
    },
    // 表单分页
    handlePage (nowNum) {
      this.page.currentPage = nowNum
      this.page.pageNo = nowNum
      this.getTableData()
    },
    // 关闭
    cancel () {
      this.$emit('showDialog')
      this.assessorIds = []
      this.decisionMakerId = null
    }
  },
  mounted () {
    // 初始化
    this.getTableData()
  }
}
</script>
<style scoped>
  .selectbox ul li{
    width: auto!important;
    max-width: 100px;
    min-width: 66px;
  }
  .selectbox ul li span{
    padding: 0 3px;
    font-size: 12px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
</style>
