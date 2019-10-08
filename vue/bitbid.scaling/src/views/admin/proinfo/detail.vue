<template>
  <div class="project-edit" id="project-details">
    <div class="proinfo-info-box">
      <div class="title-box">
        <p>项目信息</p>
      </div>
      <el-form :model="projectInfoForm" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="项目名称:">
              {{projectInfoForm.projectName}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="项目编号:" >
              {{projectInfoForm.projectCode}}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="包组名称:">
              {{projectInfoForm.sectionName}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="包组编号:" >
              {{projectInfoForm.sectionCode}}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div class="scaling-box">
      <div class="title-box">
        <p>定标方法</p>
      </div>
      <el-form :model="methodForm" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="5">
            <el-form-item label="定标方法:">
              <span v-if="methodForm.calibMethodForm.methodType === 1">直接抽签法</span>
              <span v-if="methodForm.calibMethodForm.methodType === 2">票决法</span>
              <span v-if="methodForm.calibMethodForm.methodType === 3">价格竞争法</span>
              <span v-if="methodForm.calibMethodForm.methodType === 4">集体议事法</span>
            </el-form-item>
          </el-col>
          <el-col :span="2" class="unit-box" v-if="methodForm.calibMethodForm.methodType === 1">
            <el-form-item>
              {{methodForm.calibMethodForm.number}}
              <span class="unit-span">人</span>
            </el-form-item>
          </el-col>
          <el-col :span="4" class="unit-box" v-if="methodForm.calibMethodForm.methodType === 2">
            <el-form-item >
              <span v-if="methodForm.calibMethodForm.childType === 1">直接票决法</span>
              <span v-if="methodForm.calibMethodForm.childType === 2">逐轮票决法</span>
              <span v-if="methodForm.calibMethodForm.childType === 3">累积投票法</span>
            </el-form-item>
          </el-col>
          <el-col :span="2" class="unit-box" v-if="methodForm.calibMethodForm.methodType === 2 && methodForm.calibMethodForm.childType === 2">
            <el-form-item >
              {{methodForm.calibMethodForm.nogoRound}}
              <span class="unit-span">轮</span>
            </el-form-item>
          </el-col>
          <el-col :span="4" class="unit-box" v-if="methodForm.calibMethodForm.methodType === 3">
            <el-form-item >
              <span v-if="methodForm.calibMethodForm.childType === 1">最低投标价法</span>
              <span v-if="methodForm.calibMethodForm.childType === 2">次低价法</span>
              <span v-if="methodForm.calibMethodForm.childType === 3">平均值法</span>
              <span v-if="methodForm.calibMethodForm.childType === 4">第N低价法</span>
            </el-form-item>
          </el-col>
          <el-col :span="4" class="unit-box" v-if="methodForm.calibMethodForm.methodType === 3 && methodForm.calibMethodForm.childType === 4">
            <el-form-item>
              <span class="before-span">第</span>
              {{methodForm.calibMethodForm.lowPriceNum}}
              <span class="low-price-span">低价法</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" v-if="methodForm.calibMethodForm.methodType === 3">
          <el-col :span="12">
            <el-form-item label="竞价时间:" >
              <!-- <span class="before-span"></span>
              <span class="low-price-span"></span> -->
              {{methodForm.calibMethodForm.beginDate}}-{{methodForm.calibMethodForm.endDate}}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="降幅(元):">
              {{methodForm.calibMethodForm.decline}}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" v-if="showSecond">
          <el-col :span="5">
            <el-form-item label="下一轮:">
              <span v-if="methodForm.calibSecondForm.methodType === 1">直接抽签法</span>
              <span v-if="methodForm.calibSecondForm.methodType === 2">票决法</span>
              <span v-if="methodForm.calibSecondForm.methodType === 3">价格竞争法</span>
              <span v-if="methodForm.calibSecondForm.methodType === 4">集体议事法</span>
            </el-form-item>
          </el-col>
          <el-col :span="2" class="unit-box" v-if="methodForm.calibSecondForm.methodType === 1">
            <el-form-item>
              {{methodForm.calibSecondForm.number}}
              <span class="unit-span">人</span>
            </el-form-item>
          </el-col>
          <el-col :span="4" class="unit-box" v-if="methodForm.calibSecondForm.methodType === 2">
            <el-form-item>
              <span v-if="methodForm.calibSecondForm.childType === 1">直接票决法</span>
              <span v-if="methodForm.calibSecondForm.childType === 2">逐轮票决法</span>
              <span v-if="methodForm.calibSecondForm.childType === 3">累积投票法</span>
            </el-form-item>
          </el-col>
          <el-col :span="2" class="unit-box" v-if="methodForm.calibSecondForm.methodType === 2 && methodForm.calibSecondForm.childType === 2">
            <el-form-item>
              {{methodForm.calibSecondForm.nogoRound}}
              <span class="unit-span">轮</span>
            </el-form-item>
          </el-col>
          <el-col :span="4" class="unit-box" v-if="methodForm.calibSecondForm.methodType === 3">
            <el-form-item>
              <span v-if="methodForm.calibSecondForm.childType === 1">最低投标价法</span>
              <span v-if="methodForm.calibSecondForm.childType === 2">次低价法</span>
              <span v-if="methodForm.calibSecondForm.childType === 3">平均值法</span>
              <span v-if="methodForm.calibSecondForm.childType === 4">第N低价法</span>
            </el-form-item>
          </el-col>
          <el-col :span="4" class="unit-box" v-if="methodForm.calibSecondForm.methodType === 3 && methodForm.calibSecondForm.childType === 4">
            <el-form-item>
              <span class="before-span">第</span>
              {{methodForm.calibSecondForm.lowPriceNum}}
              <span class="low-price-span">低价法</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" v-if="methodForm.calibSecondForm.methodType === 3">
          <el-col :span="12">
            <el-form-item label="竞价时间:" prop="secondDate">
              <!-- <span class="before-span">开始日期: </span> -->
              {{methodForm.calibSecondForm.beginDate}}-
              <!-- <span class="low-price-span">结束日期: </span> -->
              {{methodForm.calibSecondForm.endDate}}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="降幅(元):">
              {{methodForm.calibSecondForm.decline}}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div class="sacl-record-box">
      <div class="title-box">
        <p>定标候选人及评标报告</p>
      </div>
      <el-form label-width="140px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="评标报告:">
              <filedownload :fileObject="projectInfoForm "></filedownload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="2">
            <el-form-item label="定标候选人名单:" prop="projectName">
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-table
              :data="users"
              border
              style="width: 100%">
              <el-table-column
                prop="userName"
                label="定标候选人名称">
              </el-table-column>
              <el-table-column
                prop="initPrice"
                label="中标价格（元）"
                width="180">
              </el-table-column>
              <el-table-column
                prop="initUserOrder"
                label="定标候选人排名">
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
      </el-form>
    </div>
  </div>
</template>
<script>
import filedownload from '@/components/file-download'
import {projectInfo} from '@/api'
export default {
  name: 'project-detail',
  data () {
    return {
      projectInfoForm: {
      },
      // 定标方法-表单
      methodForm: {
        calibMethodForm: {
        },
        calibSecondForm: {
        }
      },
      users: [],
      showSecond: false
    }
  },
  created () {},
  methods: {
    detail (objectId) {
      projectInfo.detail(objectId).then((res) => {
        if (res.data.resCode === '0000') {
          this.projectInfoForm = res.data.ProjectView.project
          let methods = res.data.ProjectView.scalingSystemMethods
          methods.map((item) => {
            if (item.methodOrder === 1) {
              this.methodForm.calibMethodForm = item
            } else if (item.methodOrder === 2) {
              this.methodForm.calibSecondForm = item
              this.showSecond = true
            }
          })
          this.users = res.data.ProjectView.users
        }
      })
    }
  },
  components: {
    filedownload
  },
  mounted () {
    let objectId = this.$route.query.projectId
    if (Number(objectId) !== 0) {
      this.detail(objectId)
    }
  }
}
</script>
<style lang="scss">
#project-details {
  .proinfo-info-box,
  .scaling-box,
  .sacl-record-box {
    background: #fff;
    margin-top: 15px;
    padding: 10px;
  }
  .proinfo-info-box, .sacl-record-box {
    .el-form {
      .el-form-item__content {
        text-align: left;
      }
    }
  }
  .scaling-box {
    .el-form-item__content {
      text-align: left;
    }
  }
  .submit-box {
    margin: 15px;
  }
  .unit-box {
    .el-form-item__content {
      margin-left: 0 !important;
    }
  }
  .unit-span {
    position: absolute;
    top: 0;
    left: 30px;
  }
  .before-span {
    position: absolute;
    top: 0;
    left: -15px;
  }
  .low-price-span {
    position: absolute;
    top: 0;
    left: 16px;
  }
}
</style>
