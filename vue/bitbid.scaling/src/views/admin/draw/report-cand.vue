<template>
  <div class="report-cand" id="report-cand">
    <div class="proinfo-info-box">
      <project :projectInfoForm="projectInfoForm"></project>
    </div>
    <div class="bid-report-box">
      <div class="title-box">
        <p>{{num > 1 ? '定标候选人' : '评标报告' }}</p>
      </div>
      <el-form label-width="130px" class="pro-form">
        <el-row v-if="!(num > 1)">
          <el-col :span="24">
            <el-form-item label="评标报告:">
              <fileDownload :fileObject="projectInfoForm"></fileDownload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="2">
            <el-form-item label="定标候选人名单:" >
            </el-form-item>
          </el-col>
          <el-col :span="2" v-if="num > 1 && !isSurveillance">
            <el-button class="btn-warning-bg" size="small" @click="addNameListClick" :disabled="isSubmited">选择</el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item>
              <el-table
                :data="candTableData"
                border
                style="width: 100%"
                class="name-list-table">
                <el-table-column
                  prop="userName"
                  label="定标候选人名称">
<!--                  <template slot-scope='scope'>-->
<!--                    <el-input-->
<!--                      :disabled="true"-->
<!--                      type="text"-->
<!--                      v-model="candTableData[scope.$index].userName"></el-input>-->
<!--                  </template>-->
                </el-table-column>
                <el-table-column
                  width="200"
                  prop="initPrice"
                  label="中标价格（元）">
                </el-table-column>
                <el-table-column
                  prop="initUserOrder"
                  label="定标候选人排名"
                  v-if="!(num > 1)">
                </el-table-column>
                <el-table-column
                  prop="userOrder"
                  label="定标候选人排名"
                  v-if="num > 1">
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div class="submit-box" v-if="num > 1 && !isSurveillance">
        <el-button type="primary" size="small" @click="submitForm" v-if="!isSubmited" :disabled="isSubmited" :loading="isLoading">提 交</el-button>
        <el-button type="success" disabled v-else>已提交</el-button>
      </div>
    </div>

    <!-- 弹窗区域start -->
    <div class="dialog-box">
      <el-dialog
        title="定标候选人名单"
        :visible.sync="dialogVisible"
        width="50%"
        :close-on-click-modal="false"
        center>
        <el-table
          ref="multipleTable"
          :data="dialogTableData"
          tooltip-effect="dark"
          border
          style="width: 100%"
          @selection-change="handleSelectionChange">
          <el-table-column
            type="selection"
            width="55">
          </el-table-column>
          <el-table-column
            prop="userName"
            label="定标候选人名称">
          </el-table-column>
          <el-table-column
            prop="initPrice"
            label="中标价格（元）">
          </el-table-column>
<!--          <el-table-column-->
<!--            prop="initOrder"-->
<!--            label="定标候选人排名"-->
<!--            show-overflow-tooltip>-->
<!--          </el-table-column>-->
        </el-table>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="confirmBtn">确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <!-- 弹窗区域end -->
    <div class="foot-btn">
      <!--底部-->
      <footers></footers>
      <!--底部-->
    </div>
  </div>
</template>
<script>
import footers from '@/views/admin/footer/footer.vue'
import project from '@/components/project.vue'
import fileDownload from '@/components/file-download'
import {projectInfo} from '@/api'
export default {
  name: '',
  components: {
    footers,
    project,
    fileDownload
  },
  data () {
    return {
      dialogVisible: false,
      dialogTableData: [],
      multipleTable: [],
      projectId: this.$store.getters.projectId ? this.$store.getters.projectId : this.$route.query.projectId,
      // 是否监标人
      isSurveillance: this.$store.getters.authUser.roleIds[0] === 4,
      num: this.$route.query.num || 1,
      projectInfoForm: {
      },
      candTableData: [], // 定标候选人名单
      isSubmited: false,
      methodType: this.$route.query.methodType,
      flag: this.$route.query.flag,
      roundIndex: this.$route.query.roundIndex,
      isLoading: false
    }
  },
  methods: {
    // 弹窗确定
    confirmBtn () {
      if (this.multipleTable.length > 0) {
        this.candTableData = this.multipleTable
      }
      this.dialogVisible = false
    },
    // 弹窗表格-多选
    handleSelectionChange (val) {
      this.multipleTable = val
    },
    detail () {
      if (!this.projectId) {
        return
      }
      if (this.methodType !== '0') {
        let obj = {
          methodType: this.methodType,
          num: this.num,
          roundIndex: this.roundIndex,
          isNext: 1
        }
        projectInfo.getScalingProjectInfo(this.projectId, obj).then((res) => {
          if (res.data.resCode === '0000') {
            this.projectInfoForm = res.data.ProjectView.project
            this.candTableData = res.data.ProjectView.users
            this.dialogTableData = res.data.ProjectView.users
          }
        })
      } else {
        projectInfo.getScalingEndReport(this.projectId).then((res) => {
          if (res.data.resCode === '0000') {
            this.projectInfoForm = res.data.ProjectView.project
            this.candTableData = res.data.ProjectView.users
            this.dialogTableData = res.data.ProjectView.users
          }
        })
      }
    },
    addNameListClick () {
      // 打开弹窗
      this.dialogVisible = true
    },
    handleClick (index, type) {
      this.candTableData.splice(index, 1)
    },
    submitForm () {
      let params = {
        num: this.num,
        roundIndex: this.roundIndex,
        flag: this.flag,
        methodType: this.methodType
      }
      if (this.candTableData.length <= 0) {
        this.$message({
          type: 'warning',
          message: '请选择定标候选人'
        })
        return false
      }
      this.isLoading = true
      projectInfo.updateCandidateUser(this.projectId, this.candTableData, params).then((res) => {
        this.isLoading = false
        if (res.data.resCode === '0000') {
          this.detail()
          this.updateCandidateUserListPage()
        }
      })
    },
    updateCandidateUserListPage () {
      let obj = {
        num: this.num - 1,
        methodType: this.methodType,
        flag: this.flag
      }
      this.$nextTick(function () {
        projectInfo.updateCandidateUserListPage(this.projectId, obj).then(res => {
          if (res) {
            this.isSubmited = res.data.isHide === '1'
          }
          if (res.data.resCode === '4444') {
            this.isSubmited = true
          }
        })
      })
    },
    init () {
      this.detail()
      if (this.methodType !== '0') {
        this.$nextTick(function () {
          this.updateCandidateUserListPage()
        })
      }
    }
  },
  mounted () {
    this.init()
  },
  watch: {
    $route: function () {
      this.num = this.$route.query.num || 1
      this.methodType = this.$route.query.methodType
      this.flag = this.$route.query.flag
      this.roundIndex = this.$route.query.roundIndex
      this.init()
    }
  }
}
</script>
<style lang="scss">
#report-cand {
  .proinfo-info-box,
  .bid-report-box {
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
  .name-list-table {
    .cell {
      text-overflow: unset;
      overflow: inherit;
    }
  }
  .el-table .cell {
    text-overflow: unset;
  }
  .submit-box {
    margin: 15px;
  }
}
</style>
