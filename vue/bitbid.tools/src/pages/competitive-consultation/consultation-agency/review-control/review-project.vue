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
            <div v-for="(scoreProject,index) in scoreProjectAggregate" :key="index" :class="{'curr' : index === addrSelected}"
                 @click="chooseAddr(index, scoreProject.objectId, scoreProject.typeOrder)">
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
              align="left">
            </el-table-column>
            <el-table-column
              prop="itemDesc"
              label="评审标准"
              align="left">
            </el-table-column>
            <el-table-column
              v-if="typeOrder === 3"
              prop="itemScore"
              label="满分"
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
import { consultingReviewControl } from '@/api'
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
      tableDataScore: [],
      typeOrder: 1
    }
  },
  methods: {
    // 左侧边列表数据
    navReviewProjectLists () {
      let url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize
      }
      consultingReviewControl.navReviewProjectList(url).then((res) => {
        this.scoreProjectAggregate = res.data.BidOpenCheckTypeList
        let projectUrl = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          checkType: this.scoreProjectAggregate[0].objectId
        }
        consultingReviewControl.tableProjectList(projectUrl).then((res) => {
          this.tableDataScore = res.data.BidOpenCheckItemList
        })
      })
    },
    // 点击切换
    chooseAddr (index, objectId, typeOrder) {
      this.addrSelected = index
      this.typeOrder = typeOrder
      let url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        checkType: objectId
      }
      consultingReviewControl.tableProjectList(url).then((res) => {
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
