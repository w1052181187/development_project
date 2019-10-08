<template>
  <div id="systemlandsublookindex" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>土地交易备案</el-breadcrumb-item>
        <el-breadcrumb-item>开标记录表备案</el-breadcrumb-item>
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
          :data="projectData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            prop="annoNumber"
            label="公告名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="sectionNumber"
            label="地块编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="lastUpdateDate"
            label="发布时间"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="status"
            label="发布状态"
            align="left"
            width="240">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <span class="statusbox blue"><i></i>已发布</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
                <span class="statusbox green"><i></i>已发布(系统自动获取)</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" width="140">
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
      seacherInput: '', // 搜索框内容绑定
      seacherName: '公告名称',
      selectOptions: [{
        value: 0,
        label: '公告名称'
      }, {
        value: 1,
        label: '地块编号'
      }],
      inquiryValue: 0, // 搜索默认值
      // 列表表格
      projectData: [
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '关于安徽省铜陵市盛冲金多金属矿勘探探矿权',
          lastUpdateDate: '2018-10-12 18:00:00',
          status: 1,
          objectId: 1,
          methodOfTransfer: 1
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '关于安徽省铜陵市盛冲金多金属矿勘探探矿权',
          lastUpdateDate: '2018-10-12 18:00:00',
          status: 2,
          objectId: 1,
          methodOfTransfer: 2
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '关于安徽省铜陵市盛冲金多金属矿勘探探矿权',
          lastUpdateDate: '2018-10-12 18:00:00',
          status: 1,
          objectId: 1,
          methodOfTransfer: 3
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '关于安徽省铜陵市盛冲金多金属矿勘探探矿权',
          lastUpdateDate: '2018-10-12 18:00:00',
          status: 2,
          objectId: 1,
          methodOfTransfer: 4
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '关于安徽省铜陵市盛冲金多金属矿勘探探矿权',
          lastUpdateDate: '2018-10-12 18:00:00',
          status: 1,
          objectId: 1,
          methodOfTransfer: 1
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
      let url = null
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
    // -----------------------------------------搜索-------------------------------------------
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.list()
    },
    // -----------------------------------------查看----------------------------------------------
    detailBtn (scope) {
      this.$router.push({path: `/system-management/land-transaction/opening-record/detail/${scope.row.objectId}`})
    },
    // ***************************************************************自己方法**********************************************************************
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '地块编号'
      } else {
        this.seacherName = '公告名称'
      }
    }
  },
  mounted () {}
}
</script>
<style lang="less">
  @import '../../../../assets/css/land-register/common.css';
  #systemlandsublookindex{
    .el-table .cell {
      height: 25px;
    }
    .cellopen{
      box-sizing: border-box;
      word-break: break-all;
      line-height: 23px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }
</style>
