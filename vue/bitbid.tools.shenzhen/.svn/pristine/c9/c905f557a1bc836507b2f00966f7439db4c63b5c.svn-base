<template>
  <div id="revPro" class="content_bgibox">
    <!--评分-->
    <div class="setup_bigbox">
      <div class="proinfobox">
        <span class="line_bigbox"></span>
        <span class="title_bigbox">查看评审项目</span>
      </div>
    </div>
    <div class="score_bigbox">
      <el-row>
        <el-col :span="4" class="left_scorebox">
          <div class="score_titlebox">评审项目</div>
          <div class="score_navbigbox">
            <div v-for="(scoreProject,index) in scoreProjectAggregate" :key="index" :class="{'curr' : index === addrSelected}" @click="chooseAddr(index, scoreProject.objectId)">
              <span>{{scoreProject.typeName}}</span>
            </div>
          </div>
        </el-col>
        <el-col :span="20" class="right_scorebox">
          <el-table
            :data="tableDataScore"
            border
            style="width: 100%">
            <el-table-column
              type="index"
              label="序号"
              width="80"
              :index='dataIndex' align="center">
            </el-table-column>
            <el-table-column
              prop="itemName"
              label="评审因素"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="itemDesc"
              label="评审标准"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </div>
    <!--评分-->
  </div>
</template>
<script>
import { reviewControl } from '@/api'
export default {
  data () {
    return {
      pageSize: 1000, // 每页展示条数
      pageNo: 0,
      dataIndex: 1,
      // 评审项目数据
      scoreProjectAggregate: [],
      // 默认选中
      addrSelected: 0,
      // 每个评审项目的不同数据
      tableDataScore: []
    }
  },
  methods: {
    // 左侧边列表数据
    navReviewProjectLists () {
      let url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize
      }
      reviewControl.navReviewProjectList(url).then((res) => {
        this.scoreProjectAggregate = res.data.BidOpenCheckTypeList
        let projectUrl = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          checkType: this.scoreProjectAggregate[0].objectId
        }
        reviewControl.tableProjectList(projectUrl).then((res) => {
          this.tableDataScore = res.data.BidOpenCheckItemList
        })
      })
    },
    // 点击切换
    chooseAddr (index, objectId) {
      this.addrSelected = index
      let url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        checkType: objectId
      }
      reviewControl.tableProjectList(url).then((res) => {
        this.tableDataScore = res.data.BidOpenCheckItemList
      })
    }
  },
  mounted () {
    this.navReviewProjectLists()
  }
}
</script>
<style lang="less">
  #revPro{
    margin-top: 20px;
  }
</style>
