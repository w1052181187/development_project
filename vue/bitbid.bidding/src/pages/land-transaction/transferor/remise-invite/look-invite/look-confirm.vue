<template>
  <div id="land_pro_patchReg" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让邀请</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/remise-invite/look-invite?roledsType=1' }">查看出让邀请确认情况</el-breadcrumb-item>
        <el-breadcrumb-item>查看确认情况</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--搜索按钮-->
        <el-row>
          <div class="seacher_box">
            <el-select v-model="inquiryValue" class="select" @change="selectSeacher">
              <el-option
                v-for="item in selectOptions"
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
        <!--搜索按钮-->
        <!--主要内容 table-->
        <el-table
          :data="projecttableData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="sectionNumber"
            label="地块编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="inviteName"
            label="邀请书名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" width="220" align="center">
            <template slot-scope="scope">
              <div class="statusbigbox">
                <el-button type="text" size="small" @click="InvitationBtn(scope)">
                  出让邀请书
                </el-button>
                <el-button type="text" size="small" @click="assigneeConfirmBtn(scope)">
                  受让人确认情况
                </el-button>
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
      seacherInput: '', // 搜索框内容绑定
      seacherName: '邀请书名称',
      selectOptions: [{
        value: 0,
        label: '邀请书名称'
      }, {
        value: 1,
        label: '地块编号'
      }],
      inquiryValue: 0, // 搜索默认值
      // 列表表格
      projecttableData: [
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          inviteName: '关于安徽省铜陵市盛冲金多金属矿勘探探矿权',
          inviteTimes: '2018-10-12 18:00:00',
          status: 0,
          objectId: 1,
          Confirmation: '4/7'
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          inviteName: '关于安徽省铜陵市盛冲金多金属矿勘探探矿权',
          inviteTimes: '2018-10-12 18:00:00',
          status: 1,
          objectId: 1,
          Confirmation: '4/7'
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          inviteName: '关于安徽省铜陵市盛冲金多金属矿勘探探矿权',
          inviteTimes: '2018-10-12 18:00:00',
          status: 2,
          objectId: 1,
          Confirmation: '4/7'
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          inviteName: '关于安徽省铜陵市盛冲金多金属矿勘探探矿权',
          inviteTimes: '2018-10-12 18:00:00',
          status: 3,
          objectId: 1,
          Confirmation: '4/7'
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          inviteName: '关于安徽省铜陵市盛冲金多金属矿勘探探矿权',
          inviteTimes: '2018-10-12 18:00:00',
          status: 4,
          objectId: 1,
          Confirmation: '4/7'
        }
      ],
      currentPage: 1, // 当前页
      pageNo: 0, // 页数
      total: 10, // 总条数
      pageSize: 10 // 每页展示条数
    }
  },
  methods: {
    // ***************************************************************数据接口方法**********************************************************************
    // -----------------------------------------列表-----------------------------------------
    list () {
      let url = {}
      url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0
      }
      if (this.seacherInput !== '') {
        if (this.seacherName === '邀请书名称') {
          url.inviteName = this.seacherInput
        } else if (this.seacherName === '地块编号') {
          url.sectionNumber = this.seacherInput
        }
      }
    },
    // -----------------------------------------分页方法-----------------------------------------
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    // ----------------------------------------搜索-------------------------------------------
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.list()
    },
    // -----------------------------------------出让邀请书----------------------------------------------
    InvitationBtn (scope) {
      this.$router.push({path: `/lt/transferor/remise-invite/look-invite/sell-invitations/${scope.row.objectId}`, query: {roledsType: 1, showStatus: false}})
    },
    // -----------------------------------------受让人确认情况----------------------------------------------
    assigneeConfirmBtn (scope) {
      this.$router.push({path: `/lt/transferor/remise-invite/look-invite/assignee-situation/${scope.row.objectId}`, query: {roledsType: 1, showStatus: false}})
    },
    // ***************************************************************自己方法**********************************************************************
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '地块编号'
      } else {
        this.seacherName = '邀请书名称'
      }
    }
  },
  mounted () {}
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
