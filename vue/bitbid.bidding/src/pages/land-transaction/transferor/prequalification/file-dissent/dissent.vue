<template>
  <div id="issuemissionindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false'}">首页</el-breadcrumb-item>
        <el-breadcrumb-item>资格预审会</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/prequalification/file-dissent/index?roledsType=1&showStatus=false'}">资格预审申请文件异议</el-breadcrumb-item>
        <el-breadcrumb-item>异议</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--搜索按钮-->
        <el-row>
          <el-button type="success" class="addbutton" @click="addBtn"> + 添加</el-button>
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
              <el-button  slot="append" icon="el-icon-search" @click="search"></el-button>
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
            prop="title"
            label="标题"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="dissentTime"
            label="异议时间"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            label="操作" width="160" align="center">
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
      seacherName: '标题',
      options: [ {
        value: 1,
        label: '标题'
      }],
      value: 1,
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      projecttableData: [
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          title: '关于安徽省铜陵市盛冲金多金属矿勘探探',
          plotsLabeled: '6091p',
          dissentTime: '2019-02-29 15:23:45',
          objectId: 1,
          proposerName: '德力科技有限公司'
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          title: '关于安徽省铜陵市盛冲金多金属矿勘探探',
          plotsLabeled: '6091p',
          dissentTime: '2019-02-29 15:23:45',
          objectId: 2,
          proposerName: '德力科技有限公司'
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          title: '关于安徽省铜陵市盛冲金多金属矿勘探探',
          plotsLabeled: '6091p',
          dissentTime: '2018-05-29 15:23:45',
          objectId: 3,
          proposerName: '德力科技有限公司'
        }
      ]
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
        isDelete: 0,
        title: this.seacherInput
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
    // ***************************************************************自己方法**********************************************************************
    detailBtn (scope) {
      this.$router.push({path: `/lt/transferor/prequalification/file-dissent/detail/${scope.row.objectId}`, query: {roledsType: 1}})
    },
    addBtn () {
      this.$router.push({path: '/lt/transferor/prequalification/file-dissent/add', query: {roledsType: 1}})
    }
  },
  mounted () {
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
