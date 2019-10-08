<template>
  <div class="center_box cooperative-agency">
    <div class="con_title">合作代理管理</div>
    <div class="con_table">
      <!--上边功能-->
      <div class="con_top_box">
        <!--添加按钮-->
        <el-button type="primary" size="medium" class="add_btn" @click="addBtn">添加</el-button>
        <!--添加按钮-->
        <!--搜索-->
        <div class="seacher_box">
          <!--<el-select v-model="seacherSelect" class="select_box">-->
            <!--<el-option-->
              <!--v-for="item in seacherData"-->
              <!--:key="item.value"-->
              <!--:label="item.label"-->
              <!--:value="item.value">-->
            <!--</el-option>-->
          <!--</el-select>-->
          <el-input class="search" style="vertical-align: top;width:80%;"  :placeholder="'请输入' + seacherName" v-model.trim="seacherInput"></el-input>
          <el-button  slot="append" icon="el-icon-search" class="seacher_btn" @click="search" style="padding:0;"></el-button>
        </div>
        <!--搜索-->
      </div>
      <!--上边功能-->
      <!--列表数据-->
      <el-table
        :data="projecttableData"
        border
        header-cell-class-name="table_header">
        <el-table-column
          prop="enterpriseName"
          label="合作代理机构"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="contactName"
          label="联系人"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="contactPhone"
          label="联系方式"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          label="操作" width="210" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" class="handle_btn" @click="updateBtn(scope)">
              修改
            </el-button>
            <el-button type="text" size="small" class="handle_btn" @click="deleteBtn(scope)">
              删除
            </el-button>
            <el-button type="text" size="small" class="handle_btn" @click="relevanceBtn(scope)">
              关联账户
            </el-button>
            <el-button type="text" size="small" class="handle_btn" @click="detailBtn(scope)">
              查看
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--列表数据-->
      <!--分页-->
      <div class="page_box">
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
      <!--分页-->
    </div>
  </div>
</template>

<script>
import {agencyEnterprise} from '@/api'
export default {
  name: '',
  data () {
    return {
      seacherInput: '', // 搜索input值
      seacherSelect: 0, // 选择默认值
      // 搜索框选择内容
      seacherData: [{
        value: 0,
        label: '合作代理机构'
      }],
      // 选择不同的搜索方式切换不同的提示
      seacherName: '合作代理机构',
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 20, // 总条数
      currentPage: 1,
      projecttableData: []
    }
  },
  methods: {
    // ***************************************************************自己方法**********************************************************************
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    // 列表显示
    list () {
      let url = {}
      url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isvalid: 1
      }
      if (this.seacherInput !== '') { // 当搜索内容不为空时执行
        url.messageLike = this.seacherInput
      }
      agencyEnterprise.queryList(url).then((res) => {
        this.projecttableData = res.data.agencyEnterprisePageInfo.list
        this.total = res.data.agencyEnterprisePageInfo.total
      })
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.list()
    },
    // ***************************************************************自己方法**********************************************************************
    deleteBtn (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        customClass: 'cooperative-agency-delbtn',
        type: 'error'
      }).then(() => {
        agencyEnterprise.delete(scope.row.id).then((res) => {
          this.list()
        })
      }).catch(() => {
        return false
      })
    },
    // 添加
    addBtn () {
      this.$router.push({path: '/admin/contract-agency/contract/add'})
    },
    // 修改
    updateBtn (scope) {
      this.$router.push({path: '/admin/contract-agency/contract/update', query: {objectId: scope.row.id}})
    },
    // 查看
    detailBtn (scope) {
      this.$router.push({path: `/admin/contract-agency/contract/detail/${scope.row.id}`})
    },
    // 关联账户
    relevanceBtn (scope) {
      this.$router.push({path: '/admin/contract-agency/contract/associated', query: {objectId: scope.row.id}})
    }
  },
  mounted () {
    this.list()
  }
}
</script>

<style lang="less">
  .cooperative-agency-delbtn{
    .el-message-box__content{
      overflow: hidden;
    }
    .el-message-box__status{
      float: left;
      position: initial;
      top: inherit;
      transform: inherit;
    }
    .el-message-box__message{
      float: left;
      padding-left: 10px!important;
    }
  }
</style>
