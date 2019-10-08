<template>
  <div id="land_pro_patchReg" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>查看出让地块</el-breadcrumb-item>
        <el-breadcrumb-item>我参与的</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--搜索按钮-->
        <el-row>
          <div class="seacher_box">
            <el-select v-model="inquiryValue" class="select">
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
          :data="projectData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="sectionNumber"
            label="地块编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="annoNumber"
            label="公告名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="methodOfTransfer"
            label="出让方式"
            align="left"
            width="240">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 1">
                <span class="statusbox">拍卖</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 2">
                <span class="statusbox">挂牌</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 3">
                <span class="statusbox">资格后审</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 4">
                <span class="statusbox">资格预审</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="publishedStartTime"
            label="发布开始时间"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="publishedEndTime"
            label="发布截止时间"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" width="140" align="center">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="detailBtn(scope)">
                查看
              </el-button>
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
      seacherName: '地块编号',
      selectOptions: [{
        value: 1,
        label: '地块编号'
      }],
      inquiryValue: 1, // 搜索默认值
      // 列表表格
      projectData: [
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '皖探转让公示〔2018〕3号',
          publishedStartTime: '2018-7-26 18:00:00',
          publishedEndTime: '2018-7-26 18:00:00',
          methodOfTransfer: 1,
          objectId: 1
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '皖探转让公示〔2018〕3号',
          publishedStartTime: '2018-7-26 18:00:00',
          publishedEndTime: '2018-7-26 18:00:00',
          methodOfTransfer: 2,
          objectId: 1
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '皖探转让公示〔2018〕3号',
          publishedStartTime: '2018-7-26 18:00:00',
          publishedEndTime: '2018-7-26 18:00:00',
          methodOfTransfer: 3,
          objectId: 1
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '皖探转让公示〔2018〕3号',
          publishedStartTime: '2018-7-26 18:00:00',
          publishedEndTime: '2018-7-26 18:00:00',
          methodOfTransfer: 4,
          objectId: 1
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '皖探转让公示〔2018〕3号',
          publishedStartTime: '2018-7-26 18:00:00',
          publishedEndTime: '2018-7-26 18:00:00',
          objectId: 1
        }
      ],
      currentPage: 1, // 当前页
      pageNo: 0, // 页数
      total: 30, // 总条数
      pageSize: 10 // 每页展示条数
    }
  },
  methods: {
    // ***************************************************************数据接口方法**********************************************************************
    // -----------------------------------------列表-----------------------------------------
    list () {
      let url = null
      url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0
      }
      if (this.seacherInput !== '') {
        if (this.seacherName === '地块编号') {
          url.sectionNumber = this.seacherInput
        }
      }
      console.log(url)
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
    // ----------------------------------------列表搜索-------------------------------------------
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.searchIs = true
      this.list()
    },
    // -----------------------------------------查看----------------------------------------------
    detailBtn (scope) {
      this.$router.push({path: `/lt/assignee/look-transfer-plot/participate/detail/${scope.row.objectId}`, query: {roledsType: 2, methodOfTransfer: scope.row.methodOfTransfer}})
    }
  },
  mounted () {}
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
