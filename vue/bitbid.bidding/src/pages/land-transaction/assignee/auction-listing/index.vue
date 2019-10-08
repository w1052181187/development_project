<template>
  <div id="land_pro_patchReg" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>拍卖/挂牌文件</el-breadcrumb-item>
        <el-breadcrumb-item>下载出让文件</el-breadcrumb-item>
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
            prop="landInformation.sectionNumber"
            label="地块编号"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="landAnno.annoNumber"
            label="公告名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="landAnno.type"
            label="出让方式"
            align="left"
            width="240">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.landAnno.type === 1">
                <span class="statusbox">拍卖</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.landAnno.type === 2">
                <span class="statusbox">挂牌</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.landAnno.type === 3">
                <span class="statusbox">资格后审</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.landAnno.type === 4">
                <span class="statusbox">资格预审</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="isClarifySta"
            label="是否存在澄清"
            align="left"
            width="140"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.isClarifySta === 0">
                <span class="statusbox">否</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.isClarifySta === 1">
                <span class="statusbox">是</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" width="140" align="center">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="downBtn(scope)">
                下载
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
import {tdocuments} from '@/api'
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
      projectData: [],
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
        isDelete: 0,
        status: 2,
        type: 2,
        transferType: 1
      }
      if (this.seacherInput !== '') {
        if (this.seacherName === '地块编号') {
          url.sectionNumber = this.seacherInput
        }
      }
      tdocuments.queryLandList(url).then((res) => {
        let sectionLists = []
        console.log(res)
        res.data.transferDocumentsList.list.forEach(function (item) {
          let sectionList = []
          sectionList = item.refTransDocuList.map(function (refTransDocu) {
            refTransDocu.landAnno = item.landAnno
            refTransDocu.isClarify = item.isClarify
            return refTransDocu
          })
          sectionList.forEach(function (item, index) {
            sectionLists.push(item)
          })
        })
        this.projectData = sectionLists
        this.projectData.map((its) => {
          if (its.landInformation.clarifyFiles.length !== 0) {
            its.isClarifySta = 1
          } else {
            its.isClarifySta = 0
          }
        })
        this.total = res.data.transferDocumentsList.total
      })
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
    downBtn (scope) {
      this.$router.push({path: `/lt/assignee/auction-listing/detail/${scope.row.objectId}`, query: {roledsType: 2, objectId: scope.row.objectId, isClarify: scope.row.isClarifySta, transDocSectionid: scope.row.transDocSectionid}})
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/land-register/common.css';
</style>
