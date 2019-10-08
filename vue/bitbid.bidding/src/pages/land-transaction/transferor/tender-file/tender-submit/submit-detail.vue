<template>
  <div id="issuemissionindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招标、投标文件</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/tender-file/tender-submit/index?roledsType=1&showStatus=false' }">查看投标文件递交情况</el-breadcrumb-item>
        <el-breadcrumb-item>递交详情</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--搜索按钮-->
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
            prop="subName"
            label="竞买人"
            align="left"
            width="180"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="creationTime"
            label="递交时间"
            align="left"
            width="180"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop=""
            label="申请文件(评审结束方可下载)"
            align="left"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <div class="statusbigbox">
                <span class="statusbox" style="color: #66b1ff" @click="downloadFile(scope)">{{scope.row.fileName}}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="subStatus"
            label="递交状态"
            align="left"
            width="120">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.subStatus === 0">
                <span class="statusbox gray"><i></i>已递交</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.subStatus === 1">
                <span class="statusbox blue"><i></i>已撤回</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="操作" width="160" align="center">
            <template slot-scope="scope" v-if="scope.row.subStatus === 0">
              <el-button type="text" size="small" @click="detailBtn(scope)">
                回执签章
              </el-button>
              <el-button type="text" size="small" @click="detailBtn(scope)">
                查看回执
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
import {downloadFileMining} from '@/assets/js/common'
export default {
  data () {
    return {
      seacherInput: '',
      // 选择不同的搜索方式切换不同的提示
      seacherName: '地块编号',
      options: [ {
        value: 1,
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
          annoNumber: '关于安徽省铜陵市盛冲金多金属矿勘探探',
          creationTime: '2018-05-29 15:23:45',
          fileStatus: 0,
          subName: '测试受让人一二三',
          fileName: '测试投标文件.rar',
          relativePath: '测试路径',
          subStatus: 0
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '关于安徽省铜陵市盛冲金多金属矿勘探探',
          creationTime: '2018-05-29 15:23:45',
          fileStatus: 1,
          subName: '测试受让人一二三',
          fileName: '测试投标文件.rar',
          relativePath: '测试路径',
          subStatus: 0
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '关于安徽省铜陵市盛冲金多金属矿勘探探',
          creationTime: '2018-05-29 15:23:45',
          fileStatus: 2,
          subName: '测试受让人一二三',
          subStatus: 1
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '关于安徽省铜陵市盛冲金多金属矿勘探探',
          creationTime: '2018-05-29 15:23:45',
          fileStatus: 3,
          subName: '测试受让人一二三',
          fileName: '测试投标文件.rar',
          relativePath: '测试路径',
          subStatus: 0
        },
        {
          sectionNumber: '皖探转让公示〔2018〕3号',
          annoNumber: '关于安徽省铜陵市盛冲金多金属矿勘探探',
          creationTime: '2018-05-29 15:23:45',
          fileStatus: 4,
          subName: '测试受让人一二三',
          subStatus: 1
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
        sectionNumber: this.seacherInput
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
    // 下载文件
    downloadFile (scope) {
      downloadFileMining(scope.row.fileName, scope.row.relativePath)
    }
  },
  mounted () {
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
