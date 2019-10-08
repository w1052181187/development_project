<template>
  <div id="situation"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>土地交易备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/land-transaction/qualification/index' }">资格确认情况备案</el-breadcrumb-item>
        <el-breadcrumb-item>资格确认详情</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--竞买人名称搜索-->
        <el-row>
          <div class="seacher_box">
            <el-select v-model="value" class="select">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-input class="search" style="vertical-align: top"  :placeholder="'请输入' + seacherName" v-model="seacherInput">
              <el-button  slot="append" icon="el-icon-search" type="" @click="search"></el-button>
            </el-input>
          </div>
        </el-row>
        <!--项目名称  项目编号搜索-->
        <!--主要内容 table-->
        <el-table
          :data="regisData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="bidderName"
            label="竞买人"
            align="left"
            width="300"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="contacts"
            label="联系人"
            align="left"
            width="200"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="contactsNumber"
            label="联系电话"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="email"
            label="电子邮箱"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="createDate"
            label="报名时间"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="acceptResults"
            label="竞买保证金缴纳情况"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.acceptResults === 0">
                <span class="statusbox red"><i></i>未缴纳</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.acceptResults === 1">
                <span class="statusbox green"><i></i>已缴纳</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="acceptResults"
            label="资格确认书发送情况"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.acceptResults === 0">
                <span class="statusbox red"><i></i>未发送</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.acceptResults === 1">
                <span class="statusbox green"><i></i>已发送</span>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <!--主要内容 table-->
        <!--分页-->
        <el-pagination
          class="pagebox"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :total="total"
          :page-size='pageSize'
          layout="prev, pager, next, jumper">
        </el-pagination>
        <!--分页-->
      </template>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      seacherInput: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '竞买人名称',
      options: [{
        value: 1,
        label: '竞买人名称'
      }],
      value: 1,
      regisData: [
        {
          bidderName: '零零壹科技有限公司（测试专用）',
          contacts: '小吴',
          contactsNumber: '18856565656',
          email: '18856565656@163.com',
          createDate: '2018-8-20 13:55:30',
          acceptResults: 0,
          objectId: 1001
        },
        {
          bidderName: '零零壹科技有限公司（测试专用）',
          contacts: '小吴',
          contactsNumber: '18856565656',
          email: '18856565656@163.com',
          createDate: '2018-8-20 13:55:30',
          acceptResults: 1,
          objectId: 1002
        },
        {
          bidderName: '零零壹科技有限公司（测试专用）',
          contacts: '小吴',
          contactsNumber: '18856565656',
          email: '18856565656@163.com',
          createDate: '2018-8-20 13:55:30',
          acceptResults: 0,
          objectId: 1003
        }
      ],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 0, // 总条数
      pageSize: 10 // 每页展示条数
    }
  },
  methods: {
    // ***************************************************************数据接口方法**********************************************************************
    list () {
      let url = {}
      url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0
      }
      if (this.seacherInput !== '') {
        if (this.seacherName === '竞买人名称') {
          url.bidderNames = this.seacherInput
        }
      }
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.list()
    },
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    }
  },
  mounted () {
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/land-register/common.css';
</style>
