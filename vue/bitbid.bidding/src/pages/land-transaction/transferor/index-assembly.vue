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
              label="公告名称"
              align="center">
            </el-table-column>
            <el-table-column
              prop="ggStatus"
              label="公告状态"
              width="190"
              align="center">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.ggStatus === 0">
                  <span class="statusbox">未提交</span>
                  <el-button type="text" size="small">
                    提交
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.ggStatus === 1">
                  <span class="statusbox">备案审批中</span>
                  <el-button type="text" size="small">
                    查看
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.ggStatus === 2">
                  <span class="statusbox">审批通过</span>
                  <el-button type="text" size="small">
                    发布
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.ggStatus === 3">
                  <span class="statusbox">审批不通过</span>
                  <el-button type="text" size="small">
                    修改
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.ggStatus === 4">
                  <span class="statusbox">已发布</span>
                  <el-button type="text" size="small">
                    查看
                  </el-button>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="massifNum"
              label="地块编号"
              align="center">
            </el-table-column>
            <el-table-column
              prop="applyStatus"
              label="报名"
              align="center"
              width="120">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.applyStatus === null">
                  <span class="statusbox">----</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.applyStatus === 0">
                  <span class="statusbox">未开始</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.applyStatus === 1">
                  <span class="statusbox">已开始</span>
                  <el-button type="text" size="small">
                    受理
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.applyStatus === 2">
                  <span class="statusbox">已结束</span>
                  <el-button type="text" size="small">
                    查看
                  </el-button>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="crwjStatus"
              label="预审文件"
              align="center"
              width="120">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.crwjStatus === null">
                  <span class="statusbox">----</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.crwjStatus === 0">
                  <span class="statusbox">未开始</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.crwjStatus === 1">
                  <span class="statusbox">未发布</span>
                  <el-button type="text" size="small">
                    发布
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.crwjStatus === 2">
                  <span class="statusbox">已发布</span>
                  <el-button type="text" size="small">
                    查看
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.crwjStatus === 3">
                  <span class="statusbox">已结束</span>
                  <el-button type="text" size="small">
                    查看
                  </el-button>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="cqwjStatus"
              label="预审会"
              align="center"
              width="120">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.cqwjStatus === null">
                  <span class="statusbox">----</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.cqwjStatus === 0">
                  <span class="statusbox">未开始</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.cqwjStatus === 1">
                  <span class="statusbox">已开始</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.cqwjStatus === 2">
                  <span class="statusbox">已结束</span>
                  <el-button type="text" size="small">
                    查看
                  </el-button>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="priceStatus"
              label="出让/招标文件"
              align="center"
              width="120">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.priceStatus === null">
                  <span class="statusbox">----</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.priceStatus === 0">
                  <span class="statusbox">未开始</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.priceStatus === 1">
                  <span class="statusbox">未发布</span>
                  <el-button type="text" size="small">
                    发布
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.priceStatus === 2">
                  <span class="statusbox">已发布</span>
                  <el-button type="text" size="small">
                    查看
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.priceStatus === 3">
                  <span class="statusbox">已结束</span>
                  <el-button type="text" size="small">
                    查看
                  </el-button>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="isSend"
              label="澄清"
              align="center"
              width="120">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.isSend === null">
                  <span class="statusbox">----</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.isSend === 0">
                  <span class="statusbox">未开始</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.isSend === 1">
                  <span class="statusbox">未发布</span>
                  <el-button type="text" size="small">
                    发布
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.isSend === 2">
                  <span class="statusbox">已发布</span>
                  <el-button type="text" size="small">
                    查看
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.isSend === 3">
                  <span class="statusbox">已结束</span>
                  <el-button type="text" size="small">
                    查看
                  </el-button>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="cjjgStatus"
              label="竞价/开评标"
              align="center"
              width="120">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.cjjgStatus === null">
                  <span class="statusbox">----</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.cjjgStatus === 0">
                  <span class="statusbox">未开始</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.cjjgStatus === 1">
                  <span class="statusbox">拍卖竞价中</span>
                  <el-button type="text" size="small">
                    竞价室
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.cjjgStatus === 2">
                  <span class="statusbox">挂牌竞价中</span>
                  <el-button type="text" size="small">
                    竞价室
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.cjjgStatus === 3">
                  <span class="statusbox">挂牌结束</span>
                  <el-button type="text" size="small">
                    竞价记录
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.cjjgStatus === 4">
                  <span class="statusbox">拍卖结束</span>
                  <el-button type="text" size="small">
                    竞价记录
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.cjjgStatus === 5">
                  <span class="statusbox">开标进行中</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.cjjgStatus === 6">
                  <span class="statusbox">开标结束</span>
                  <el-button type="text" size="small">
                    评标报告
                  </el-button>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="cjjgStatus"
              label="成交确认"
              align="center"
              width="120">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.cjjgStatus === null">
                  <span class="statusbox">----</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.cjjgStatus === 0">
                  <span class="statusbox">未开始</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.cjjgStatus === 1">
                  <span class="statusbox">未发送</span>
                  <el-button type="text" size="small">
                    发送
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.cjjgStatus === 2">
                  <span class="statusbox">已结束</span>
                  <el-button type="text" size="small">
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
                <div class="statusbigbox" v-if="scope.row.cjjgStatus === null">
                  <span class="statusbox">----</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.cjjgStatus === 0">
                  <span class="statusbox">未提交</span>
                  <el-button type="text" size="small">
                    提交
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.cjjgStatus === 1">
                  <span class="statusbox">备案审批中</span>
                  <el-button type="text" size="small">
                    查看
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.cjjgStatus === 2">
                  <span class="statusbox">审批通过</span>
                  <el-button type="text" size="small">
                    发布
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.cjjgStatus === 3">
                  <span class="statusbox">审批不通过</span>
                  <el-button type="text" size="small">
                    修改
                  </el-button>
                </div>
                <div class="statusbigbox" v-if="scope.row.cjjgStatus === 4">
                  <span class="statusbox">已发布</span>
                  <el-button type="text" size="small">
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
export default {
  data () {
    return {
      indextableData: [
        {
          annoNumber: '关于安徽省铜陵市盛冲金多金',
          ggStatus: 0,
          massifNum: '皖探转让公示〔2018〕3号',
          applyStatus: null,
          crwjStatus: null,
          cqwjStatus: null,
          priceStatus: null,
          isSend: null,
          cjjgStatus: null
        },
        {
          annoNumber: '关于安徽省铜陵市盛冲金多金',
          ggStatus: 1,
          massifNum: '皖探转让公示〔2018〕3号',
          applyStatus: 0,
          crwjStatus: 0,
          cqwjStatus: 0,
          priceStatus: 0,
          isSend: 0,
          cjjgStatus: 0
        },
        {
          annoNumber: '关于安徽省铜陵市盛冲金多金',
          ggStatus: 2,
          massifNum: '皖探转让公示〔2018〕3号',
          applyStatus: 1,
          crwjStatus: 1,
          cqwjStatus: 1,
          priceStatus: 1,
          isSend: 1,
          cjjgStatus: 1
        },
        {
          annoNumber: '关于安徽省铜陵市盛冲金多金',
          ggStatus: 3,
          massifNum: '皖探转让公示〔2018〕3号',
          applyStatus: 2,
          crwjStatus: 2,
          cqwjStatus: 2,
          priceStatus: 2,
          isSend: 2,
          cjjgStatus: 2
        }
      ],
      // 当前页
      currentPage: 1,
      total: 10, // 总条数
      pageSize: 10, // 每页展示条数
      pageNo: 0,
      searchIs: false
    }
  },
  methods: {
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
    }
  },
  mounted () {}
}
</script>
