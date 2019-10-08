<template>
  <div class="accu-vote-room" id="accu-vote-room">
    <div class="proinfo-info-box">
      <div class="title-box">
        <p>项目信息</p>
      </div>
      <el-form ref="form" :model="projectInfoForm" label-width="100px" class="pro-form">
        <el-row>
          <el-col :span="8">
            <el-form-item label="项目名称:">
              <span>{{projectInfoForm.projectName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="项目编号:">
              <span>{{projectInfoForm.projectCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="包组名称:">
              <span>{{projectInfoForm.sectionName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="包组编号:">
              <span>{{projectInfoForm.sectionCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <el-row :gutter="15" style="margin-left:0;margin-right:0;">
      <el-col :span="12" style="padding-left:0;">
        <div class="vote-box">
          <div class="title-box">
            <p>投票</p>
          </div>
          <div class="vote-total-box">
            投票总分值为 <span class="vote-total-score">{{voteTotalScore}}</span>
          </div>
          <div class="vote-cont">
            <el-row>
              <el-col :span="24">
                <el-form :model="votoForm" ref="votoForm" :rules="votoFormRules">
                  <el-table
                    v-if="!queryType"
                    :data="votoForm.voteTableData"
                    border
                    height="500"
                    class="voto-form-tab"
                    style="width: 100%">
                    <el-table-column
                      type="index"
                      label="序号"
                      align="center"
                      width="55">
                    </el-table-column>
                    <el-table-column
                      prop="userName"
                      label="供应商">
                    </el-table-column>
                    <el-table-column
                      prop="score"
                      label="分值">
                      <template slot-scope="scope">
                        <el-form-item  :prop="'voteTableData.' + scope.$index + '.score'" :rules='votoFormRules.score'>
                          <el-input-number
                            :controls = "false"
                            v-model="scope.row.score"
                            :disabled="!changeBtnType || isSurveillance">
                          </el-input-number>
                        </el-form-item>
                      </template>
                    </el-table-column>
                  </el-table>
                  <el-table
                    v-else
                    :data="voteResultTableData"
                    border
                    height="500"
                    style="width: 100%">
                    <el-table-column
                      type="index"
                      label="序号"
                      align="center"
                      width="55">
                    </el-table-column>
                    <el-table-column
                      prop="resultName"
                      label="供应商">
                    </el-table-column>
                    <el-table-column
                      prop="score"
                      label="分值">
                      <template slot-scope="scope">
                        <el-input-number
                          :controls = "false"
                          v-model="voteResultTableData[scope.$index].score"
                          :disabled="true">
                        </el-input-number>
                      </template>
                    </el-table-column>
                  </el-table>
                </el-form>
              </el-col>
            </el-row>
          </div>
        </div>
      </el-col>
      <el-col :span="12" style="padding-right:0;">
        <div class="vote-box">
          <div class="title-box">
            <p>定标候选人</p>
          </div>
          <div class="bid-cand">
            <el-table
              :data="bidCandTableData"
              border
              :show-header="false"
              height="500"
              style="width: 100%">
              <el-table-column
                type="index"
                label="序号"
                align="center"
                width="55">
              </el-table-column>
              <el-table-column
                prop="userName"
                label="名称">
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-col>
    </el-row>
    <div class="submit-btns" v-if="!isSurveillance">
      <el-button class="btn-blue-bg" size="small" @click="handleBtnClick(changeBtnType)" :disabled="isDisabled">{{(changeBtnType)? '提 交': '撤 回'}}</el-button>
    </div>
    <div class="foot-btn">
      <!--底部-->
      <footers></footers>
      <!--底部-->
    </div>
  </div>
</template>
<script>
import footers from '@/views/admin/footer/footer.vue'
import {projectInfo, scalingEnd} from '@/api'
import {WebPushSocket} from '../../../assets/js/websocket-inst.js'
// 状态
import {mapState} from 'vuex'
export default {
  name: '',
  data () {
    var validateBidPrice = (rule, value, callback) => {
      if (value && (!(/^\d{1,14}(\.\d{1,2})?$/).test(value))) {
        callback(new Error('中标价格只能输入小数点前不超过14位且小数点后不超过2位的数字'))
      } else {
        callback()
      }
    }
    return {
      isDisabled: false,
      userId: this.$route.query.userId,
      votoForm: {
        voteTableData: [] // 投票
      },
      votoFormRules: {
        score: [
          {required: true, message: '请填写得分', trigger: 'blur'},
          {validator: validateBidPrice}
        ]
      },
      projectId: this.$store.getters.projectId, // 项目id
      methodType: this.$route.query.methodType,
      // 是否监标人
      isSurveillance: this.$store.getters.authUser.roleIds[0] === 4,
      changeBtnType: true,
      currentPage: 1,
      projectInfoForm: {},
      voteResultTableData: [],
      bidCandTableData: [], // 定标候选人
      queryType: false, // 判断执行那个查询
      voteTotalScore: 0,
      webPushSocket: null,
      num: this.$route.query.num || 1
    }
  },
  computed: {
    // 获取状态
    ...mapState({
      confirmMessages: (state) => {
        return state.messages
      },
      submitData: (state) => {
        return state.messages
      }
    })
  },
  watch: {
    confirmMessages: function (newV, oldV) {
      if (newV.message === '确认提交') {
        this.isDisabled = true
      }
    },
    submitData: function (newV, oldV) {
      if (newV.message === '提交数据') {
        this.getVoterResult()
      }
    },
    $route: function () {
      this.userId = this.$route.query.userId
      this.methodType = this.$route.query.methodType
      this.num = this.$route.query.num || 1
      this.queryType = false
      this.changeBtnType = true
      this.init()
    }
  },
  methods: {
    init () {
      this.getProjectInfo()
      this.getVoterResult()
      this.getVoteRoomPage()
    },
    handleBtnClick (val) {
      if (this.changeBtnType) {
        this.submitVoteResult()
      } else {
        this.recallVoteResult()
      }
    },
    /**
     * 获得项目信息
     */
    getProjectInfo () {
      let params = {
        num: this.num,
        methodType: this.methodType
      }
      projectInfo.scalingEndInfo(this.projectId, params).then(res => {
        if (res.data.resCode === '0000') {
          // 临时接受数据对象
          let tempObj = res.data.ProjectView
          // console.log(tempObj)
          // 项目信息-表单数据
          this.projectInfoForm = tempObj.project
          // 定标候选人-表格数据
          this.bidCandTableData = tempObj.users.sort(function (a, b) {
            let x = a['initUserOrder']
            let y = b['initUserOrder']
            return (x > y ? 1 : (x < y ? -1 : 0))
          })
          // 投票-列表
          this.votoForm.voteTableData = tempObj.users.map(item => {
            return item
          })
          this.voteTotalScore = 100 * this.votoForm.voteTableData.length
        }
      })
    },
    // 提交数据-投票结果
    submitVoteResult () {
      let tempScore = 0
      // console.log(this.votoForm.voteTableData)
      let temArr = this.votoForm.voteTableData.map(item => {
        tempScore += item.score
        return {
          resultId: item.objectId,
          projectId: item.projectId,
          resultName: item.userName,
          score: item.score
        }
      })
      if (tempScore !== this.voteTotalScore) {
        this.$message({
          type: 'warning',
          message: '分值与总分值不符，请重新填写'
        })
      } else {
        let params = {
          methodType: this.methodType,
          num: this.num
        }
        if (this.validateForm()) {
          scalingEnd.submitOrderResult(temArr, params).then(res => {
            if (res.data.resCode === '0000') {
              this.changeBtnType = false
              // 提交成功后获得结果
              // 推送数据给其他角色
              this.webPushSocket.sendMsg('提交数据')
            }
          })
        }
      }
    },
    // 校验
    validateForm () {
      let result = false
      this.$refs['votoForm'].validate(valid => {
        if (valid) {
          result = true
        }
      })
      return result
    },
    // 获得累计投票结果
    getVoterResult () {
      let params = {
        methodType: this.methodType,
        num: this.num,
        userId: this.userId
      }
      scalingEnd.getOrderResult(this.projectId, params).then((res) => {
        let tempResult = res.data.ResultList
        // console.log(tempResult)
        if (tempResult.length) {
          this.queryType = true
          this.changeBtnType = false
          this.voteResultTableData = tempResult
          this.voteTotalScore = 100 * this.voteResultTableData.length
        }
      })
    },
    // 撤回
    recallVoteResult () {
      let params = {
        methodType: this.methodType,
        num: this.num
      }
      scalingEnd.cancelOrderResult(this.projectId, params).then(res => {
        if (res.data.resCode === '0000') {
          this.getProjectInfo()
          // this.voteResultTableData = []
          this.queryType = false
          this.changeBtnType = true
          this.webPushSocket.sendMsg('撤销提交')
        }
      })
    },
    // 流程控制
    getVoteRoomPage () {
      scalingEnd.getVoteRoomPage(this.projectId, {methodType: this.methodType, num: this.num}).then(res => {
        // 1已经执行-不可操作
        // 0未执行-可操作
        this.isDisabled = res.data.isHide === '1'
      })
    },
    initWebsocket () {
      this.webPushSocket = WebPushSocket.getInstance(this.$store.getters.projectId, this.$store.getters.authUser.userId, this.$store.getters.authUser.userName, 'All', this.methodType, this.num)
      this.webPushSocket.init()
    }
  },
  components: {
    footers
  },
  mounted () {
    this.init()
    // 初始化
    this.initWebsocket()
  },
  destroyed () {
    if (this.webPushSocket) {
      this.webPushSocket.close()
      this.webPushSocket = null
    }
  }
}
</script>
<style lang="scss">
#accu-vote-room {
  .proinfo-info-box {
    padding: 10px;
    margin-top: 15px;
    background: #fff;
    .pro-form {
      .el-form-item {
        margin-bottom: 0;
      }
      .el-form-item__content {
        text-align: left;
      }
    }
  }
  .voto-form-tab {
    .el-form-item {
      margin-bottom: 0;
    }
  }
  .vote-box {
    padding: 10px;
    margin-top: 15px;
    background: #fff;
  }
  .vote-total-box {
    text-align: left;
    padding: 15px 0;
  }
  .vote-total-score {
    color: red;
  }
  .submit-btns {
    padding: 20px 0;
  }
}
</style>
