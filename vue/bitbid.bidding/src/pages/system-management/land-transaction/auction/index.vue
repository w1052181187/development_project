<template>
  <div id="feepayindexbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management'}">首页</el-breadcrumb-item>
        <el-breadcrumb-item>土地交易备案</el-breadcrumb-item>
        <el-breadcrumb-item>拍卖竞价情况备案</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--添加按钮-->
        <el-row>
          <div class="seacher_box">
            <el-select v-model="selectCondition" class="select" @change="selectSeacher">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-input class="search" style="vertical-align: top"  :placeholder="'请输入' + seacherName" v-model="seacherInput">
              <el-button  slot="append" icon="el-icon-search" @click="search"></el-button>
            </el-input>
          </div>
        </el-row>
        <!--添加按钮-->
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
            prop="annoNumber"
            label="公告名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="auctionTime"
            label="拍卖开始时间"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
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
      // 选择不同的搜索方式切换不同的提示
      seacherName: '公告名称',
      options: [ {
        value: 1,
        label: '公告名称'
      }, {
        value: 2,
        label: '地块编号'
      }],
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      selectCondition: 1,
      projecttableData: [
        {
          annoNumber: '皖探转让公示〔2018〕3号',
          sectionNumber: '皖探转让公示〔2018〕3号',
          pricestatus: 0,
          auctionTime: '2018-8-20 13:55:30',
          bidBond: '258',
          objectId: 1001
        },
        {
          annoNumber: '皖探转让公示〔2018〕3号',
          sectionNumber: '皖探转让公示〔2018〕3号',
          pricestatus: 1,
          auctionTime: '2018-8-20 13:55:30',
          bidBond: '258',
          objectId: 1001
        },
        {
          annoNumber: '皖探转让公示〔2018〕3号',
          sectionNumber: '皖探转让公示〔2018〕3号',
          pricestatus: 2,
          auctionTime: '2018-8-20 13:55:30',
          bidBond: '258',
          objectId: 1001
        },
        {
          annoNumber: '皖探转让公示〔2018〕3号',
          sectionNumber: '皖探转让公示〔2018〕3号',
          pricestatus: 0,
          auctionTime: '2018-8-20 13:55:30',
          bidBond: '258',
          objectId: 1001
        }
      ]
    }
  },
  methods: {
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
        isDelete: 0
      }
      if (this.seacherInput !== '') { // 当搜索内容不为空时执行
        if (this.seacherName === '公告名称') {
          url.annoName = this.seacherInput
        } else if (this.seacherName === '地块编号') {
          url.sectionNumber = this.seacherInput
        }
      }
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.list()
    },
    // ***************************************************************自己方法**********************************************************************
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '公告名称'
      } else {
        this.seacherName = '地块编号'
      }
    },
    detailBtn (scope) {
      this.$router.push({path: `/system-management/land-transaction/auction/detail/${scope.row.objectId}`})
    }
  },
  mounted () {
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/land-register/common.css';
</style>
