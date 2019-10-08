<template>
  <div id="land_pro_patchReg" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>拍卖/挂牌文件</el-breadcrumb-item>
        <el-breadcrumb-item>提交澄清/修改文件</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--搜索按钮-->
        <el-row>
          <el-button type="success" class="addbutton" @click="addBtn"> + 添加</el-button>
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
            prop="landAnno.annoNumber"
            label="公告名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="refclarifyFileList"
            label="地块编号"
            align="left"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span class="jfbdbox" v-for="(item,index) in scope.row.refclarifyFileList" :key="index">
                <span class="xmbh_box" :title="item.landInformation.sectionNumber">{{item.landInformation.sectionNumber}}</span>
              </span>
            </template>
          </el-table-column>
          <el-table-column
            prop="fileInformations.fileName"
            label="澄清/修改文件"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="status"
            label="文件状态"
            align="left"
            width="240">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 0">
                <span class="statusbox gray"><i></i>未提交</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <span class="statusbox blue"><i></i>已提交</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
                <span class="statusbox yellow"><i></i>已发布</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" width="180" align="center">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 0">
                <el-button type="text" size="small" @click="updateBtn(scope)">
                  修改
                </el-button>
                <el-button type="text" size="small" @click="deleteBtn(scope)">
                  删除
                </el-button>
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <el-button type="text" size="small" @click="releaseBtn(scope)">
                  发布
                </el-button>
                <el-button type="text" size="small" @click="updateBtn(scope)">
                  变更
                </el-button>
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
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
import {clarifyFile} from '@/api'
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
      projecttableData: [],
      currentPage: 1, // 当前页
      pageNo: 0, // 页数
      total: 100, // 总条数
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
        isDelete: 0,
        type: 2,
        transferType: 1
      }
      if (this.seacherInput !== '') { // 当搜索内容不为空时执行
        if (this.seacherName === '公告名称') {
          url.annoName = this.seacherInput
        } else if (this.seacherName === '地块编号') {
          url.sectionNumber = this.seacherInput
        }
      }
      clarifyFile.queryLandList(url).then((res) => {
        this.projecttableData = res.data.clarifyFileList.list
        this.total = res.data.clarifyFileList.total
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
    // ----------------------------------------搜索-------------------------------------------
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.list()
    },
    // -----------------------------------------添加----------------------------------------------
    addBtn () {
      this.$router.push({path: '/lt/transferor/auction-listed-file/clarify-announ?roledsType=1&showStatus=false'})
    },
    // -----------------------------------------修改----------------------------------------------
    updateBtn (scope) {
      this.$router.push({path: '/lt/transferor/auction-listed-file/sub-clarify/update', query: {objectId: scope.row.objectId, annoId: scope.row.annoId, roledsType: 1, showStatus: false}})
    },
    // -----------------------------------------查看----------------------------------------------
    detailBtn (scope) {
      this.$router.push({path: `/lt/transferor/auction-listed-file/sub-clarify/detail/${scope.row.objectId}`, query: {roledsType: 1, showStatus: false}})
    },
    // -----------------------------------------发布----------------------------------------------
    releaseBtn (scope) {
      this.$router.push({path: `/lt/transferor/auction-listed-file/sub-clarify/release/${scope.row.objectId}`, query: {roledsType: 1, showStatus: false}})
    },
    // -----------------------------------------删除----------------------------------------------
    deleteBtn (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        clarifyFile.delete(scope.row.objectId).then((res) => {
          this.list()
        })
      }).catch(() => {
        return false
      })
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
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
