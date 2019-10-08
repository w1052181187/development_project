<template>
  <div class="record_box">
    <div class="title-box">
      <p>竞价记录</p>
    </div>
    <!--竞买人选择-->
    <div class="type_selectbox" v-if="bidderStatus">
      <div class="type_selecttitle">竞买人：</div>
      <el-select v-model="reviewSelId" placeholder="请选择" @change="reviewSelects">
        <el-option
          v-for="item in reviewSelect"
          :key="item.objectId"
          :label="item.companyName"
          :value="item.objectId">
        </el-option>
      </el-select>
    </div>
    <!--竞买人选择-->
    <!--竞买人表格-->
    <div class="record_table">
      <el-table
        :data="recordData"
        border
        style="width: 100%">
        <el-table-column
          prop="num"
          label="报价轮次">
        </el-table-column>
        <el-table-column
          prop="creatorName"
          label="竞买人">
        </el-table-column>
        <el-table-column
          prop="createDate"
          label="报价时间">
        </el-table-column>
        <el-table-column
          prop="doublePrice"
          label="报价金额（元）">
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <div style="text-align: right;margin-top: 20px" v-if="total > 10">
        <el-pagination
          class="pagebox"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :total="total"
          :page-size='pageSize'
          layout="prev, pager, next, jumper">
        </el-pagination>
      </div>
    </div>
    <!--竞买人表格-->
  </div>
</template>

<script>
// 状态
import {mapState} from 'vuex'
import {scalingEnd, projectInfo} from '@/api'
export default {
  props: ['projectId'],
  name: '',
  data () {
    return {
      // 当前页
      currentPage: 1,
      pageNo: 1,
      total: 0, // 总条数
      pageSize: 10, // 每页展示条数
      reviewSelId: '',
      bidderStatus: this.$store.getters.authUser.roleIds[0] !== 5, // 是否选择竞买人
      reviewSelect: [],
      recordData: [],
      methodType: this.$route.query.methodType,
      num: this.$route.query.num || 1,
      roleId: this.$store.getters.authUser.roleId
    }
  },
  computed: {
    // 获取状态
    ...mapState({
      // 当前登录的用户信息
      currentUser: (state) => {
        return state.authUser
      }
    })
  },
  methods: {
    handleSizeChange (val) {},
    // 分页
    handleCurrentChange (nowNum) {
      // console.log(nowNum)
      this.pageNo = nowNum
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this.list()
    },
    // 查询-竞买人记录
    reviewSelects (val) {
      this.reviewSelId = val
      this.pageNo = 1
      this.currentPage = 1
      this.list()
    },
    list () {
      let params = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        creator: !this.bidderStatus ? this.currentUser.userId : this.reviewSelId,
        projectId: this.projectId
      }
      scalingEnd.getRecord(params).then((res) => {
        if (res.data.resCode === '0000') {
          this.$nextTick(function () {
            this.recordData = res.data.BiddlingRecordList
          })
          this.total = res.data.total
        }
      })
    },
    // 获取候选人
    getUsers () {
      let obj = {
        methodType: this.methodType,
        num: this.num,
        isNext: 1
      }
      projectInfo.getScalingProjectInfo(this.projectId, obj).then((res) => {
        if (res.data.resCode === '0000') {
          this.reviewSelect = res.data.ProjectView.users
        }
      })
    }
  },
  mounted () {
    //  竞价-监标人、代理、定标人是可以在竞价记录处查看到所有投标人的记录，默认显示全部
    if (this.bidderStatus) {
      this.getUsers()
    }
    this.list()
  }
}
</script>

<style lang="scss">
  .record_box{
    background: #fff;
    margin-top: 15px;
    padding: 20px;
    text-align: left;
  }
  .type_selectbox{
    padding: 20px 30px;
    box-sizing: border-box;
  }
  .type_selecttitle{
    display: inline-block;
  }
</style>
