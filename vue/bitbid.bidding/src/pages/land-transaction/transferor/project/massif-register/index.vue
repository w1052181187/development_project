<template>
  <div id="land_pro_patchReg" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>项目管理</el-breadcrumb-item>
        <el-breadcrumb-item>地块登记</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--搜索按钮-->
        <el-row>
          <el-button type="success" class="addbutton" @click="addBtn"> + 添加</el-button>
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
            prop="methodOfSupply"
            label="供应方式"
            align="left"
            width="240">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.methodOfSupply === 1">
                <span class="statusbox">招拍挂出让</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.methodOfSupply === 2">
                <span class="statusbox">协议出让</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="methodOfTransfer"
            label="出让方式"
            align="left"
            width="240">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.methodOfTransfer === null">
                <span class="statusbox">----</span>
              </div>
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
            prop="status"
            label="地块状态"
            align="left"
            width="240">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 0">
                <span class="statusbox gray"><i></i>未提交</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <span class="statusbox blue"><i></i>备案审批中</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
                <span class="statusbox green"><i></i>审批通过</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 3">
                <span class="statusbox red"><i></i>审批不通过</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" width="140" align="center">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 0">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
                <el-button type="text" size="small" @click="updateBtn(scope)">
                  修改
                </el-button>
                <el-button type="text" size="small" @click="deleteBtn(scope)">
                  删除
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
                <el-button type="text" size="small" @click="updateBtn(scope)">
                  变更
                </el-button>
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  查看
                </el-button>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 3">
                <el-button type="text" size="small" @click="updateBtn(scope)">
                  修改
                </el-button>
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
import { landProject } from '@/api'
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
      total: 10, // 总条数
      pageSize: 10, // 每页展示条数
      searchIs: false
    }
  },
  methods: {
    // ***************************************************************数据接口方法**********************************************************************
    // -----------------------------------------列表-----------------------------------------
    list () {
      let url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0
      }
      // 模糊查询
      url.messageLike = this.searchIs ? (this.seacherInput === '' ? null : this.seacherInput) : null
      // 列表查询
      landProject.queryList(url).then(res => {
        this.projectData = res.data.landInformationPageInfo.list
        this.total = res.data.landInformationPageInfo.total
      })
    },
    // -----------------------------------------分页方法-----------------------------------------
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list(this.searchIs)
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list(this.searchIs)
    },
    // ----------------------------------------列表搜索-------------------------------------------
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.searchIs = true
      this.list()
    },
    // -----------------------------------------添加----------------------------------------------
    addBtn (scope) {
      this.$router.push({path: '/lt/transferor/project/massif-register/add?roledsType=1&showStatus=false'})
    },
    // -----------------------------------------修改----------------------------------------------
    updateBtn (scope) {
      this.$router.push({path: '/lt/transferor/project/massif-register/update?roledsType=1&showStatus=false', query: {objectId: scope.row.objectId}})
    },
    // -----------------------------------------查看----------------------------------------------
    detailBtn (scope) {
      this.$router.push({path: `/lt/transferor/project/massif-register/detail/${scope.row.objectId}`, query: {roledsType: 1, showStatus: false}})
    },
    // -----------------------------------------删除----------------------------------------------
    deleteBtn (scope) {
      landProject.remove(scope.row.objectId).then(res => {
        this.list()
      })
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
