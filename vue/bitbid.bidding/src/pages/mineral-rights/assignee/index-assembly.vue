<template>
    <div class="">
      <div class="contentbigbox">
        <template>
          <el-table
            :data="indextableData"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="annoNumber"
              label="公告（示）名称受让人"
              align="center">
            </el-table-column>
            <el-table-column
              prop="transferorName"
              label="出让人"
              align="center"
              width="140">
            </el-table-column>
            <el-table-column
              prop="ggStatus"
              label="公告（示）状态"
              width="140"
              align="center">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.ggStatus === 4">
                  <span class="statusbox">已发布</span>
                  <el-button type="text" size="small" @click="ggLookBtn(scope)">
                    查看
                  </el-button>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="sectionName"
              label="项目"
              align="center">
            </el-table-column>
            <el-table-column
              prop="applyStatus"
              label="报名"
              align="center"
              width="100">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.applyStatus === '未开始'">
                  <span class="statusbox">未开始</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.applyStatus === '已开始'">
                  <div class="statusbigbox" v-if="(scope.row.bmAcceptResults === null && scope.row.creator !== $store.getters.authUser.userId) || (scope.row.applyAssigneeId !==$store.getters.authUser.userId && scope.row.creator !== $store.getters.authUser.userId)">
                    <span class="statusbox">已开始</span>
                    <el-button type="text" size="small" @click="signUpBtn(scope)">
                      报名
                    </el-button>
                  </div>
                  <div class="statusbigbox" v-if="(scope.row.bmAcceptResults === null && scope.row.creator === $store.getters.authUser.userId) || (scope.row.bmAcceptResults === 2 && scope.row.creator === $store.getters.authUser.userId)">
                    <span class="statusbox blue">已开始</span>
                  </div>
                  <div class="statusbigbox" v-if="scope.row.bmAcceptResults === 0">
                    <span class="statusbox red">已驳回</span>
                  </div>
                  <div class="statusbigbox" v-if="scope.row.bmAcceptResults === 1">
                    <span class="statusbox green">已通过</span>
                  </div>
                  <div class="statusbigbox" v-if="scope.row.bmAcceptResults === 2 && scope.row.creator !== $store.getters.authUser.userId && scope.row.applyAssigneeId ===$store.getters.authUser.userId">
                    <span class="statusbox blue">已报名</span>
                  </div>
                </div>
                <div class="statusbigbox" v-if="scope.row.applyStatus === '已结束'">
                  <span class="statusbox">已结束</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="fileStatus"
              label="出让文件"
              align="center"
              width="100">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.fileStatus === '未开始'">
                  <span class="statusbox">未开始</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.fileStatus === '已开始'">
                  <div class="statusbigbox" v-if="scope.row.crwjStatus === 0">
                    <span class="statusbox">未提交</span>
                  </div>
                  <div class="statusbigbox" v-if="scope.row.crwjStatus === 1">
                    <span class="statusbox">已提交</span>
                  </div>
                  <div class="statusbigbox" v-if="scope.row.crwjStatus === 2 ">
                    <span class="statusbox">已发布</span>
                    <el-button type="text" size="small" @click="crwjLookBtn(scope)">
                      下载
                    </el-button>
                  </div>
                </div>
                <div class="statusbigbox" v-if="scope.row.fileStatus === '已结束'">
                  <span class="statusbox">已结束</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="cqwjStatus"
              label="澄清"
              align="center"
              width="100">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.cqwjStatus === 0">
                  <span class="statusbox">未提交</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.cqwjStatus === 1">
                  <span class="statusbox">已提交</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.cqwjStatus === 2">
                  <span class="statusbox">已发布</span>
                  <el-button type="text" size="small" @click="cqwjLookBtn(scope)">
                    查看
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.cqwjStatus === null">
                  <span class="statusbox">-----------</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="priceStatus"
              label="竞价"
              align="center"
              width="120">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.bmStatus === null">
                  <span class="statusbox">-----------</span>
                </div>
                <div class="statusbigbox" v-else-if="scope.row.priceStatus === 0">
                  <span class="statusbox">未开始</span>
                </div>
                <div class="statusbigbox" v-else-if="scope.row.priceStatus === 1 && scope.row.gglandUse === 1">
                  <span class="statusbox">拍卖竞价室</span>
                  <div class="statusbigbox" v-if="scope.row.isSend ===1">
                    <el-button type="text" size="small" @click="biddingBtn(scope)">
                      竞价室
                    </el-button>
                  </div>
                </div>
                <div class="statusbigbox" v-else-if="scope.row.priceStatus === 1 && scope.row.gglandUse === 2">
                  <span class="statusbox">挂牌竞价室</span>
                  <div class="statusbigbox" v-if="scope.row.isSend ===1">
                    <el-button type="text" size="small" @click="gpBiddingBtn(scope)">
                      竞价室
                    </el-button>
                  </div>
                </div>
                <div class="statusbigbox" v-else-if="scope.row.priceStatus === 2 && scope.row.gglandUse === 1">
                  <span class="statusbox">拍卖结束</span>
                  <div class="statusbigbox" v-if="scope.row.isSend ===1">
                    <el-button type="text" size="small" @click="pmDetailBtn(scope)">
                      竞价记录
                    </el-button>
                  </div>
                </div>
                <div class="statusbigbox" v-else-if="scope.row.priceStatus === 2 && scope.row.gglandUse === 2">
                  <span class="statusbox">挂牌结束</span>
                  <div class="statusbigbox" v-if="scope.row.isSend ===1">
                    <el-button type="text" size="small" @click="gpDetailBtn(scope)">
                      竞价记录
                    </el-button>
                  </div>
                </div>
                <div class="statusbigbox" v-else-if="scope.row.priceStatus === 3 && scope.row.gglandUse === 2">
                  <span class="statusbox">限时竞价开始</span>
                  <div class="statusbigbox" v-if="scope.row.isSend ===1">
                    <el-button type="text" size="small" @click="gpBiddingBtn(scope)">
                      竞价室
                    </el-button>
                  </div>
                </div>
                <div class="statusbigbox" v-else-if="scope.row.priceStatus === 4 && scope.row.gglandUse === 2">
                  <span class="statusbox">限时竞价中</span>
                  <div class="statusbigbox" v-if="scope.row.isSend ===1">
                    <el-button type="text" size="small" @click="gpBiddingBtn(scope)">
                      竞价室
                    </el-button>
                  </div>
                </div>
                <div class="statusbigbox" v-else-if="scope.row.priceStatus === 5 && scope.row.gglandUse === 2">
                  <span class="statusbox">限时竞价结束</span>
                  <div class="statusbigbox" v-if="scope.row.isSend ===1">
                    <el-button type="text" size="small" @click="gpDetailBtn(scope)">
                      竞价记录
                    </el-button>
                  </div>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="isAffirm"
              label="成交确认"
              align="center"
              width="120">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.bmStatus === null">
                  <span class="statusbox">-----------</span>
                </div>
                <div class="statusbigbox" v-else-if="scope.row.isCommit === null && scope.row.isSend === null && scope.row.isAffirm === null">
                  <span class="statusbox">-----------</span>
                </div>
                <div class="statusbigbox" v-else-if="scope.row.isCommit === 0 && scope.row.isSend === 0 && scope.row.isAffirm === 0">
                  <span class="statusbox">未开始</span>
                </div>
                <div class="statusbigbox" v-else-if="scope.row.isCommit === 1 && scope.row.isSend === 0 && scope.row.isAffirm === 0">
                  <span class="statusbox">未发送</span>
                </div>
                <div class="statusbigbox" v-else-if="scope.row.isCommit === 1 && scope.row.isSend === 1 && scope.row.isAffirm === 0">
                  <span class="statusbox">已发送</span>
                  <el-button type="text" size="small" @click="cjqrconfirmBtn(scope)">
                    查看
                  </el-button>
                </div>
                <div class="statusbigbox" v-else-if="scope.row.isCommit === 1 && scope.row.isSend === 1 && scope.row.isAffirm === 1">
                  <span class="statusbox">已结束</span>
                  <el-button type="text" size="small" @click="cjqrconfirmBtn(scope)">
                    查看
                  </el-button>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="cjjgStatus"
              label="结果公示"
              align="center"
              width="120">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.bmStatus === null">
                  <span class="statusbox">-----------</span>
                </div>
                <div class="statusbigbox" v-else-if="scope.row.cjjgStatus === null">
                  <span class="statusbox">-----------</span>
                </div>
                <div class="statusbigbox" v-else-if="scope.row.cjjgStatus === 0">
                  <span class="statusbox">未提交</span>
                </div>
                <div class="statusbigbox" v-else-if="scope.row.cjjgStatus === 1">
                  <span class="statusbox">备案审批中</span>
                </div>
                <div class="statusbigbox" v-else-if="scope.row.cjjgStatus === 2">
                  <span class="statusbox">审批通过</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.cjjgStatus === 3">
                  <span class="statusbox">审批不通过</span>
                </div>
                <div class="statusbigbox" v-else-if="scope.row.cjjgStatus === 4">
                  <span class="statusbox">已发布</span>
                  <el-button type="text" size="small" @click="cjjgLookBtn(scope)">
                    查看
                  </el-button>
                </div>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination
            class="pagebox"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :total="total"
            :page-size='pageSize'
            layout="prev, pager, next, jumper">
          </el-pagination>
        </template>
      </div>
    </div>
</template>
<script>
import { home } from '@/api'
export default {
  data () {
    return {
      indextableData: [],
      // 当前页
      currentPage: 1,
      total: 0, // 总条数
      pageSize: 10, // 每页展示条数
      pageNo: 0,
      searchIs: false
    }
  },
  methods: {
    initDates () {
      let url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        ggStatus: 4
      }
      home.homeList(url).then((res) => {
        this.indextableData = res.data.sectionHomeInfoPageInfo.list
        this.total = res.data.sectionHomeInfoPageInfo.total
      })
    },
    // 公告查看
    ggLookBtn (scope) {
      this.$router.push({path: `/mr/assignee/look-transfer-project/detail/${scope.row.sectionObjectId}`, query: {types: scope.row.transferType, landUse: scope.row.gglandUse, roledsType: 2}})
    },
    // 报名
    signUpBtn (scope) {
      this.$router.push({path: '/mr/assignee/online-registration/look-registration/submit-registration', query: {ObjectId: scope.row.sectionObjectId, roledsType: 2}})
    },
    // 出让文件下载
    crwjLookBtn (scope) {
      this.$router.push({path: '/mr/assignee/transfer-documents/download-sell-file/download', query: {transDocSectionid: scope.row.sectionObjectId, roledsType: 2}})
    },
    // 澄清文件下载
    cqwjLookBtn (scope) {
      this.$router.push({path: '/mr/assignee/transfer-documents/download-clarify-file/download', query: {clarifyFileSectionid: scope.row.sectionObjectId, roledsType: 2}})
    },
    // 查看成交结果公示
    cjjgLookBtn (scope) {
      this.$router.push({path: `/mr/assignee/business-confirmation/look-results-transaction/detail/${scope.row.cjjgObjectId}`, query: {roledsType: 2}})
    },
    // 进入拍卖竞价室
    biddingBtn (scope) {
      this.$router.push({path: '/mr/assignee/auction/auction-bids/bidding-room', query: {roomId: scope.row.roomId, code: scope.row.xmCode, startTime: scope.row.saleStartTime, roledsType: 2}})
    },
    // 查看拍卖竞价记录
    pmDetailBtn (scope) {
      this.$router.push({path: '/mr/assignee/auction/auction-bids/detail', query: {sectionName: scope.row.sectionName, sectionNumber: scope.row.sectionNumber, annoNumber: scope.row.annoNumber, roomId: scope.row.roomId, roledsType: 2}})
    },
    // 进入挂牌竞价室
    gpBiddingBtn (scope) {
      this.$router.push({path: '/mr/assignee/quotation/check-listed-bids/bidding-room', query: {objectId: scope.row.SectionObjectId, roledsType: 2}})
    },
    // 查看挂牌竞价记录
    gpDetailBtn (scope) {
      this.$router.push({path: '/mr/assignee/quotation/check-auction-bids/detail',
        query: {
          sectionName: scope.row.sectionName,
          sectionNumber: scope.row.sectionNumber,
          annoNumber: scope.row.annoNumber,
          roomId: scope.row.roomId,
          roledsType: 2}})
    },
    // 查看成交确认
    cjqrconfirmBtn (scope) {
      this.$router.push({path: `/mr/assignee/business-confirmation/receive-sales-confirmation/detail/${scope.row.cjqrObjectId}`, query: {roledsType: 2}})
    },
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.initDates()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.initDates()
    }
  },
  mounted () {
    let that = this
    if (that.$store.getters.authUser.userType !== 1) {
      if (that.$store.getters.authUser.subjectIds !== null && that.$store.getters.authUser.subjectIds !== undefined) {
        that.$store.getters.authUser.subjectIds.map(item => {
          if (item === 10008) {
            that.initDates()
          }
        })
      }
    }
    if (Number(this.$route.query.roledsType) === 2) {
      that.initDates()
    }
  }
}
</script>
