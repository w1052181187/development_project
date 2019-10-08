<template>
  <div id="contsubindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false'}">首页</el-breadcrumb-item>
        <el-breadcrumb-item>开标、评标</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/open-criticize-mark/tender-documents/index?roledsType=2&showStatus=false'}">投标文件澄清</el-breadcrumb-item>
        <el-breadcrumb-item>查看质疑</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--添加按钮-->
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
            prop="title"
            label="标题"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="QuestionTime"
            label="质疑时间"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="questionPeople"
            label="质疑人"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="ClarificationTime"
            label="澄清时间"
            align="left"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="!scope.row.ClarificationTime">
                <span class="statusbox">-----------</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.ClarificationTime">
                <span class="statusbox">{{scope.row.ClarificationTime}}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="ClarificationStatus"
            label="澄清状态"
            align="left"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.ClarificationStatus === 0">
                <span class="statusbox gray"><i></i>未澄清</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.ClarificationStatus === 1">
                <span class="statusbox blue"><i></i>已澄清</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="操作" width="160" align="center">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.ClarificationStatus === 0">
                <el-button type="text" size="small" @click="clarifyBtn(scope)">
                  澄清
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.ClarificationStatus === 1">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
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
      seacherName: '质疑人',
      options: [{
        value: 1,
        label: '质疑人'
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
          QuestionTime: '2018-05-29 15:23:45',
          questionPeople: '测试受让人一二三',
          ClarificationTime: '',
          ClarificationStatus: 0
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          title: '关于安徽省铜陵市盛冲金多金属矿勘探探矿权',
          QuestionTime: '2018-05-29 15:23:45',
          questionPeople: '测试受让人一二三',
          ClarificationTime: '2018-05-29 15:23:45',
          ClarificationStatus: 1
        }
      ]
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
        url.questionPeople = this.seacherInput
      }
      console.log(url)
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
    },
    // ***************************************************************自己方法**********************************************************************
    // 澄清
    clarifyBtn (scope) {
      this.$router.push({path: '/lt/assignee/open-criticize-mark/tender-documents/clarify', query: {objectId: scope.row.objectId, roledsType: 2}})
    },
    // 查看
    detailBtn (scope) {
      this.$router.push({path: `/lt/assignee/open-criticize-mark/tender-documents/detail/${scope.row.objectId}`, query: {roledsType: 2}})
    }
  },
  mounted () {}
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
