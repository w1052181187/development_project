<template>
  <div id="issuemissionindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>开标、评标</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/open-criticize-mark/tender-file/index?roledsType=1&showStatus=false' }">投标文件异议</el-breadcrumb-item>
        <el-breadcrumb-item>质疑</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--搜索按钮-->
        <el-row>
          <el-button type="success" class="addbutton" @click="addBtn"> + 添加质疑</el-button>
          <div class="seacher_box">
            <el-select v-model="value" class="select" @change="selectSeacher">
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
            prop="sectionNumber"
            label="地块编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="title"
            label="标题"
            align="left"
            width="160"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="questionTime"
            label="质疑时间"
            align="left"
            width="160"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="questionName"
            label="质疑人"
            align="left"
            width="160"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="clarifyTime"
            label="澄清时间"
            align="left"
            width="160"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="clarifyStatus"
            label="澄清状态"
            align="left"
            width="160"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.clarifyStatus === 1">
                <span class="statusbox">未澄清</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.clarifyStatus === 2">
                <span class="statusbox">已澄清</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="操作" width="160" align="center">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.clarifyStatus === 1">
                <el-button type="text" size="small" @click="updateBtn(scope)">
                  修改
                </el-button>
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  详情
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.clarifyStatus === 2">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  详情
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
      value: 1,
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      projecttableData: [
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          title: '关于安徽省铜陵市盛冲金多金属矿勘探探矿权',
          questionTime: '2018-05-29 15:23:45',
          questionName: '测试受让人一二三',
          clarifyTime: '2018-05-29 15:23:45',
          clarifyStatus: 1
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          title: '关于安徽省铜陵市盛冲金多金属矿勘探探矿权',
          questionTime: '2018-05-29 15:23:45',
          questionName: '测试受让人一二三',
          clarifyTime: '2018-05-29 15:23:45',
          clarifyStatus: 2
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
        isDelete: 0
      }
      if (this.seacherInput !== '') {
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
    // ***************************************************************自己方法**********************************************************************
    // 搜索选择
    selectSeacher (val) {
      if (val === 1) {
        this.seacherName = '公告名称'
      } else {
        this.seacherName = '地块编号'
      }
    },
    addBtn () {
      this.$router.push({path: '/lt/transferor/open-criticize-mark/tender-file/add', query: {roledsType: 1}})
    },
    updateBtn (scope) {
      this.$router.push({path: '/lt/transferor/open-criticize-mark/tender-file/update', query: {objectId: scope.row.objectId, roledsType: 1}})
    },
    detailBtn (scope) {
      this.$router.push({path: `/lt/transferor/open-criticize-mark/tender-file/detail/${scope.row.objectId}`, query: {roledsType: 1}})
    }
  },
  mounted () {
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
