<template>
  <!-- 公开招标基本信息组件 -->
  <div class="admin-layout" id="inviteinfo-page">
    <div class="inviteinfo-table">
      <!--项目单位其他采购start-->
      <el-form :model="formData" ref="formData" class="demo-ruleForm">
        <table class="info-frist" v-if="this.bidType !== 2 && this.$store.getters.authUser.enterpriseType !== 4">
          <tr  v-if="!this.lookFlag">
            <td><span style="color: red">*</span>项目编号</td>
            <td>
              <el-form-item
                prop="customProjectCode"
                :rules="formRules.customProjectCode">
                <el-input type="text" v-model="formData.customProjectCode" style="width:220px;"></el-input>
              </el-form-item>
            </td>
            <td><span style="color: red">*</span>项目名称</td>
            <td>
              <el-form-item
                prop="tenderProjectName"
                :rules="formRules.tenderProjectName">
                <el-input type="text" v-model="formData.tenderProjectName" style="width:220px;"></el-input>
              </el-form-item>
            </td>
          </tr>
          <tr  v-if="this.lookFlag">
            <td>项目编号</td>
            <td>{{formData.customProjectCode}}</td>
            <td>项目名称</td>
            <td>{{formData.tenderProjectName}}</td>
          </tr>
          <tr v-if="this.lookFlag">
            <td>是否委托招标</td>
            <td>{{formData.isEntrustBidding | filterIsEntrustBidding}}</td>
            <td>招标代理机构</td>
            <td>无</td>
          </tr>
          <tr v-if="!this.lookFlag">
            <td>是否委托招标</td>
            <td>否</td>
            <td>招标代理机构</td>
            <td>无</td>
          </tr>
          <tr>
            <td>项目单位</td>
            <td>{{formData.projectEnterpriseName}}</td>
            <td>需求方</td>
            <td>{{formData.requiresEnterpriseName}}</td>
          </tr>
          <tr>
            <td>项目类型</td>
            <td>{{formData.tenderProjectType | filterTenderProjectType}}</td>
            <td>采购方式</td>
            <td>{{formData.biddingType | filterBiddingType}}</td>
          </tr>
          <tr v-if="!this.lookFlag">
            <td><span style="color: red">*</span>备案时间</td>
            <td>
              <el-form-item
                prop="recordTime"
                :rules="formRules.recordTime">
                <el-date-picker
                  v-model="formData.recordTime"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd">
                </el-date-picker>
              </el-form-item>
            </td>
            <td><span style="color: red">*</span>会议时间</td>
            <td>
              <el-form-item
                prop="meetingTime"
                :rules="formRules.meetingTime">
                <el-date-picker
                  v-model="formData.meetingTime"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd">
                </el-date-picker>
              </el-form-item>
            </td>
          </tr>
          <tr v-if="this.lookFlag">
            <td>备案时间</td>
            <td>{{formData.recordTime}}</td>
            <td>会议时间</td>
            <td>{{formData.meetingTime}}</td>
          </tr>
        </table>
      </el-form>
      <!--项目单位其他采购end-->

      <!--招标代理机构其他采购start-->
      <el-form :model="agencyFormData" ref="agencyFormData" class="demo-ruleForm">
        <table class="info-frist" v-if="this.bidType !== 2 && this.$store.getters.authUser.enterpriseType === 4">
          <tr  v-if="!this.lookFlag">
            <td><span style="color: red">*</span>项目编号</td>
            <td>
              <el-form-item
                prop="customProjectCode"
                :rules="formRules.customProjectCode">
                <el-input type="text" v-model="agencyFormData.customProjectCode" style="width:220px;"></el-input>
              </el-form-item>
            </td>
            <td><span style="color: red">*</span>项目名称</td>
            <td>
              <el-form-item
                prop="tenderProjectName"
                :rules="formRules.tenderProjectName">
                <el-input type="text" v-model="agencyFormData.tenderProjectName" style="width:220px;"></el-input>
              </el-form-item>
            </td>
          </tr>
          <tr  v-if="this.lookFlag">
            <td>项目编号</td>
            <td>{{agencyFormData.customProjectCode}}</td>
            <td>项目名称</td>
            <td>{{agencyFormData.tenderProjectName}}</td>
          </tr>
          <tr v-if="this.lookFlag">
            <td>是否委托招标</td>
            <td>{{agencyFormData.isEntrustBidding | filterIsEntrustBidding}}</td>
            <td>招标代理机构</td>
            <td>无</td>
          </tr>
          <tr v-if="!this.lookFlag">
            <td>是否委托招标</td>
            <td>否</td>
            <td>招标代理机构</td>
            <td>无</td>
          </tr>
          <tr>
            <td>项目单位</td>
            <td>{{agencyFormData.projectEnterpriseName}}</td>
            <td>需求方</td>
            <td>{{agencyFormData.requiresEnterpriseName}}</td>
          </tr>
          <tr>
            <td>项目类型</td>
            <td>{{agencyFormData.projectType | filterTenderProjectType}}</td>
            <td>采购方式</td>
            <td>{{agencyFormData.bidType | filterBiddingType}}</td>
          </tr>
          <tr v-if="!this.lookFlag">
            <td><span style="color: red">*</span>备案时间</td>
            <td>
              <el-form-item
                prop="recordTime"
                :rules="formRules.recordTime">
                <el-date-picker
                  v-model="agencyFormData.recordTime"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd">
                </el-date-picker>
              </el-form-item>
            </td>
            <td><span style="color: red">*</span>会议时间</td>
            <td>
              <el-form-item
                prop="meetingTime"
                :rules="formRules.meetingTime">
                <el-date-picker
                  v-model="agencyFormData.meetingTime"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd">
                </el-date-picker>
              </el-form-item>
            </td>
          </tr>
          <tr v-if="this.lookFlag">
            <td>备案时间</td>
            <td>{{agencyFormData.recordTime}}</td>
            <td>会议时间</td>
            <td>{{agencyFormData.meetingTime}}</td>
          </tr>
        </table>
      </el-form>
      <!--招标代理机构其他采购end-->

      <!--项目单位邀请招标start-->
      <el-form :model="projectFormData" ref="projectFormData" class="demo-ruleForm">
        <table class="info-frist" v-if="this.bidType === 2 && this.$store.getters.authUser.enterpriseType !== 4">
          <tr  v-if="!this.lookFlag">
            <td><span style="color: red">*</span>项目编号</td>
            <td>
              <el-form-item
                prop="customProjectCode"
                :rules="formRules.customProjectCode">
                <el-input type="text" v-model="projectFormData.customProjectCode" style="width:220px;"></el-input>
              </el-form-item>
            </td>
            <td><span style="color: red">*</span>项目名称</td>
            <td>
              <el-form-item
                prop="tenderProjectName"
                :rules="formRules.tenderProjectName">
                <el-input type="text" v-model="projectFormData.tenderProjectName" style="width:220px;"></el-input>
              </el-form-item>
            </td>
          </tr>
          <tr  v-if="this.lookFlag">
            <td>项目编号</td>
            <td>{{projectFormData.customProjectCode}}</td>
            <td>项目名称</td>
            <td>{{projectFormData.tenderProjectName}}</td>
          </tr>
          <tr>
            <td>是否委托招标</td>
            <td>{{projectFormData.isEntrustBidding | filterIsEntrustBidding}}</td>
            <td>招标代理机构</td>
            <td>无</td>
          </tr>
          <tr>
            <td>项目单位</td>
            <td>{{projectFormData.projectEnterpriseName}}</td>
            <td>需求方</td>
            <td>{{projectFormData.requiresEnterpriseName}}</td>
          </tr>
          <tr>
            <td>项目类型</td>
            <td>{{projectFormData.tenderProjectType | filterTenderProjectType}}</td>
            <td>采购方式</td>
            <td>{{projectFormData.biddingType | filterBiddingType}}</td>
          </tr>
          <tr v-if="!this.lookFlag">
            <td><span style="color: red">*</span>备案时间</td>
            <td>
              <el-form-item
                prop="recordTime"
                :rules="formRules.recordTime">
                <el-date-picker
                  v-model="projectFormData.recordTime"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd">
                </el-date-picker>
              </el-form-item>
            </td>
            <td><span style="color: red">*</span>发出邀请时间</td>
            <td>
              <el-form-item
                prop="invitationTime"
                :rules="formRules.invitationTime">
                <el-date-picker
                  v-model="projectFormData.invitationTime"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd">
                </el-date-picker>
              </el-form-item>
            </td>
          </tr>
          <tr v-if="!this.lookFlag">
            <td><span style="color: red">*</span>开标地点</td>
            <td>
                <el-form-item
                  prop="biddingPlace"
                  :rules="formRules.biddingPlace">
                  <el-input type="text" v-model="projectFormData.biddingPlace"  style="width:220px;height:40px"></el-input>
                </el-form-item>
            </td>
          </tr>
          <tr v-if="this.lookFlag">
            <td>备案时间</td>
            <td>{{projectFormData.recordTime}}</td>
            <td>发出邀请时间</td>
            <td>{{projectFormData.invitationTime}}</td>
          </tr>
          <tr v-if="this.lookFlag">
            <td>开标地点</td>
            <td>{{projectFormData.biddingPlace}}</td>
          </tr>
        </table>
      </el-form>
      <!--项目单位邀请招标end-->

      <!--招标代理机构邀请招标start-->
      <table class="info-frist" v-if="this.bidType === 2 && this.$store.getters.authUser.enterpriseType === 4">
        <tr>
          <td>项目编号</td>
          <td>{{tenderProjectData.codeUser}}</td>
          <td>项目名称</td>
          <td>{{tenderProjectData.tenderProjectName}}</td>
        </tr>
        <tr>
          <td>是否委托招标</td>
          <td>{{tenderProjectData.isEntrustBidding | filterIsEntrustBidding}}</td>
          <td>招标代理机构</td>
          <td v-if="this.$store.getters.authUser.enterpriseType === 4">{{tenderProjectData.entrustEnterpriseName}}</td>
          <td v-if="this.$store.getters.authUser.enterpriseType !== 4">-----</td>
        </tr>
        <tr>
          <td>项目单位</td>
          <td>{{tenderProjectData.projectEnterpriseName}}</td>
          <td>需求方</td>
          <td>{{tenderProjectData.requiresEnterpriseName}}</td>
        </tr>
        <tr>
          <td>项目类型</td>
          <td>{{tenderProjectData.tenderProjectType | filterTenderProjectType}}</td>
          <td>采购方式</td>
          <td>邀请招标</td>
        </tr>
        <tr>
          <td>备案时间</td>
          <td>{{tenderProjectData.recordTime | filterDate}}</td>
          <td v-if="!this.lookFlag"><span style="color: red">*</span>发出邀请时间</td>
          <td v-if="!this.lookFlag">
            <el-form :model="tenderProjectData" ref="tenderProjectData" label-width="100px" class="demo-dynamic agancybigbox">
              <el-form-item
                prop="invitationTime"
                :rules="[
              {required: true, message: '必填字段', trigger: ['blur', 'change']}
               ]">
                <el-date-picker
                  v-model="tenderProjectData.invitationTime"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd">
                </el-date-picker>
              </el-form-item>
            </el-form>
          </td>
          <td v-if="this.lookFlag">发出邀请时间</td>
          <td v-if="this.lookFlag">{{tenderProjectData.invitationTime}}</td>
        </tr>
        <tr v-if="!this.lookFlag">
          <td>代理机构项目编号</td>
          <td>
            <el-input type="text" v-model="agancyForm.userProjectCode"></el-input>
          </td>
         <!-- <td><span style="color: red">*</span>代理机构项目编号</td>
          <td>
            <el-form :model="agancyForm" ref="agancyForm" label-width="100px" class="demo-dynamic agancybigbox">
              <el-form-item
                prop="userProjectCode"
                :rules="agancyForm.rules.userProjectCode">
                <el-input type="text" v-model="agancyForm.userProjectCode"></el-input>
              </el-form-item>
            </el-form>
          </td>-->
          <td><span style="color: red">*</span>开标地点</td>
          <td>
            <el-form :model="tenderProjectData" ref="tenderProjectData" label-width="100px" class="demo-dynamic agancybigbox">
              <el-form-item
                prop="biddingPlace"
                :rules="[
              {required: true, message: '必填字段', trigger: ['blur', 'change']}
               ]">
                <el-input type="text" v-model="tenderProjectData.biddingPlace"  style="width:220px;height:40px"></el-input>
              </el-form-item>
            </el-form>
          </td>
        </tr>
        <tr v-if="this.lookFlag">
          <td>代理机构项目编号</td>
          <td>{{agancyForm.userProjectCode}}</td>
          <td>开标地点</td>
          <td>{{tenderProjectData.biddingPlace}}</td>
        </tr>
      </table>
      <!--招标代理机构邀请招标start-->
    </div>
    <div class="info-second">
      <el-form :model="bidphaseForm" :rules="bidphaseForm.rules" ref="bidphaseForm">
      <el-table
        border
        :data="bidphaseForm.tableData"
        :row-class-name="tableRowClassName"
        style="width: 100%">
        <el-table-column
          prop="name"
          label="标段名称"
          align="center">
          <template slot-scope="scope">
            <el-form-item
              :prop="'tableData.' + scope.$index + '.name'"
              :rules="bidphaseForm.rules.name">
            <el-input type="text" v-model="scope.row.name" v-if="scope.row.flagIndex ===1"></el-input>
            <span v-if="scope.row.flagIndex !==1">{{scope.row.name}}</span>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column
          prop="bidPrice"
          label="中标金额（万元）"
          align="left">
          <template slot-scope="scope">
            <el-form-item
              :prop="'tableData.' + scope.$index + '.bidPrice'"
              :rules="bidphaseForm.rules.bidPrice" >
            <span v-if="scope.row.flagIndex !==1">{{scope.row.bidPrice}}</span>
            <el-input type="text" v-model="scope.row.bidPrice" v-if="scope.row.flagIndex ===1"></el-input>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column
          prop="openingTime"
          label="开标时间"
          align="center">
          <template slot-scope="scope">
            <el-form-item
              :prop="'tableData.' + scope.$index + '.openingTime'"
              :rules="bidphaseForm.rules.openingTime" >
            <span v-if="scope.row.flagIndex !==1">{{scope.row.openingTime | filterOpeningTime}}</span>
            <el-date-picker
              v-if="scope.row.flagIndex ===1"
              v-model="scope.row.openingTime"
              type="date"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd">
            </el-date-picker>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column
          prop="biddingStatus"
          label="招标状态"
          align="center">
          <template slot-scope="scope">
            <el-form-item
              :prop="'tableData.' + scope.$index + '.biddingStatus'"
              :rules="bidphaseForm.rules.biddingStatus" >
            <span v-if="scope.row.flagIndex !==1">{{scope.row.biddingStatus | filterBidStatus}}</span>
            <el-select v-model="scope.row.biddingStatus"  clearable placeholder="请选择" v-if="scope.row.flagIndex ===1">
              <el-option label="完成" :value="3"></el-option>
              <el-option label="终止" :value="2"></el-option>
              <el-option label="进行中" :value="1"></el-option>
            </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column
          prop="archivingStatus"
          label="归档状态"
          align="center"
          :formatter="filterArchivingStatus">
        </el-table-column>
        <el-table-column
          label="操作"
          width="160"
          align="center"
          v-if="!this.lookFlag">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row, 'del', scope.$index)" type="text" size="small" v-if="scope.row.archivingStatus === 1">删除</el-button>
            <el-button @click="handleClick(scope.row, 'edit', scope.$index)"
              v-if="scope.row.flagIndex !==1"
              type="text"
              size="small">
              编辑
            </el-button>
            <el-button
              v-if="scope.row.flagIndex ===1"
              @click="handleClick(scope.row, 'save', scope.$index)"
              type="text" size="small">
              保存
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      </el-form>
      <div class="add-btn-box" v-if="!this.lookFlag">
        <el-button @click="addRow" icon="el-icon-plus" style="width: 100%;"></el-button>
      </div>
    </div>
  </div>
</template>
<script>
import {tenderproject, section, archivesManagement, planproject} from 'api/index'
import {formatDate} from 'common/js/date'
import {isvalidPrice} from '../../../../assets/js/validate'
export default {
  data () {
    let validPrice = (rule, value, callback) => {
      if (!value) {

      } else if (!isvalidPrice(value)) {
        callback(new Error('请输入数字'))
      } else {
        callback()
      }
    }
    return {
      demandID: 1, // 需求方
      proTypeId: 1, // 项目类型
      bidStatusID: 1, // 招标状态
      tenderProjectData: {
        invitationTime: '',
        biddingPlace: ''
      },
      bidphaseForm: {
        rules: {
          name: {type: 'string', required: true, message: '必填字段', trigger: ['blur', 'change']},
          bidPrice: [
            {type: 'string', required: true, message: '必填字段', trigger: ['blur', 'change']},
            {validator: validPrice, trigger: 'blur'}
          ],
          openingTime: [
            { required: true, message: '请选择开标时间', trigger: ['blur', 'change'] }
          ],
          biddingStatus: [
            { required: true, message: '请选择招标状态', trigger: ['blur', 'change'] }
          ]
        },
        tableData: []
      },
      formRules: {
        customProjectCode: [
          {type: 'string', required: true, message: '必填字段', trigger: ['blur', 'change']}
        ],
        tenderProjectName: [
          {type: 'string', required: true, message: '必填字段', trigger: ['blur', 'change']}
        ],
        biddingType: [
          { required: true, message: '请选择采购方式', trigger: 'change' }
        ],
        recordTime: [
          { required: true, message: '请选择备案时间', trigger: 'change' }
        ],
        invitationTime: [
          { required: true, message: '请选择发出邀请的时间', trigger: 'change' }
        ],
        meetingTime: [
          { required: true, message: '请选择会议时间', trigger: 'change' }
        ],
        biddingPlace: [
          {type: 'string', required: true, message: '必填字段', trigger: ['blur', 'change']}
        ]
      },
      agancyForm: {
        userProjectCode: '', // 代理机构项目编号
        rules: {
          userProjectCode: {type: 'string', required: true, message: '必填字段', trigger: ['blur', 'change']}
        }
      },
      formData: {
        customProjectCode: '',
        tenderProjectName: ''
      },
      agencyFormData: {},
      projectFormData: {
        customProjectCode: '',
        tenderProjectName: ''
      },
      lookFlag: false // 判断是否为查看操作
    }
  },
  props: ['tenderProjectCode', 'projectPlanCode', 'selectType', 'bidType', 'tenderInfo'],
  computed: {
  },
  watch: {
  },
  created () {
    if (this.$route.query.operation === 'save') {
      this.getTenderprojectInfo()
    } else if (this.$route.query.operation === 'edit') {
      this.getArchivesInfo()
    } else if (this.$route.query.operation === 'look') {
      this.lookFlag = true
      this.getArchivesInfo()
    }
  },
  filters: {
    filterTenderProjectType (tenderProjectType) {
      if (tenderProjectType === 1) {
        return '工程'
      } else if (tenderProjectType === 2) {
        return '货物'
      } else {
        return '服务'
      }
    },
    filterBiddingType (biddingType) {
      if (biddingType === 1) {
        return '公开招标'
      } else if (biddingType === 2) {
        return '邀请招标'
      } else if (biddingType === 3) {
        return '谈判采购'
      } else if (biddingType === 4) {
        return '询比采购'
      } else if (biddingType === 5) {
        return '竞价采购'
      } else if (biddingType === 6) {
        return '直接采购'
      } else if (biddingType === 7) {
        return '框架协议'
      }
    },
    /** 格式化申请时间 */
    filterDate (approvePassTime) {
      if (approvePassTime) {
        let date = new Date(approvePassTime)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return null
      }
    },
    /** 招标状态（1.进行中 2.终止 3.完成） */
    filterBidStatus (bidStatus) {
      if (bidStatus === 1) {
        return '进行中'
      } else if (bidStatus === 2) {
        return '终止'
      } else {
        return '完成'
      }
    },
    /** 标段开标时间格式化 */
    filterOpeningTime (openingTime) {
      if (openingTime) {
        let date = new Date(openingTime)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return null
      }
    },
    filterIsEntrustBidding (isEntrustBidding) {
      if (isEntrustBidding === 0) {
        return '否'
      } else {
        return '是'
      }
    }
  },
  /** 方法集 */
  methods: {
    validateForm () {
      let formValid = 0
      if (this.bidType === 2) {
        formValid = 0
        if (this.$store.getters.authUser.enterpriseType === 4) {
          this.$refs['tenderProjectData'].validate((valid) => {
            if (valid) {
              formValid++
            }
          })
        } else {
          this.$refs['projectFormData'].validate((valid) => {
            if (valid) {
              formValid++
            }
          })
        }
        /* this.$refs['agancyForm'].validate((valid) => {
          if (valid) {
            formValid++
          }
        }) */
        if (this.bidphaseForm.tableData.length > 0) {
          this.$refs['bidphaseForm'].validate((valid) => {
            if (valid) {
              formValid++
            }
          })
          if (formValid === 2) {
            this.$refs.bidphaseForm.$parent.$parent.saveBaseInfo()
            // this.$emit('dataStatusValue', true)
          }
        } else {
          this.$message.error('请添加标段信息，标段信息不能为空！')
          return false
        }
      } else {
        formValid = 0
        if (this.$store.getters.authUser.enterpriseType === 4) {
          this.$refs['agencyFormData'].validate((valid) => {
            if (valid) {
              formValid++
            }
          })
        } else {
          this.$refs['formData'].validate((valid) => {
            if (valid) {
              formValid++
            }
          })
        }
        if (this.bidphaseForm.tableData.length > 0) {
          this.$refs['bidphaseForm'].validate((valid) => {
            if (valid) {
              formValid++
            }
          })
          if (formValid === 2) {
            this.$refs.bidphaseForm.$parent.$parent.saveBaseInfo()
          }
        } else {
          this.$message.error('请添加标段信息，标段信息不能为空！')
          return false
        }
      }
    },
    /** 得到招标项目的基本信息 */
    getTenderprojectInfo () {
      if (this.bidType === 2) {
        if (this.selectType === 0) { // 来源于招标过程备案
          if (this.$store.getters.authUser.enterpriseType === 4) { // 招标代理机构选择邀请招标项目
            tenderproject.getOneByCode(this.tenderProjectCode).then((res) => {
              this.tenderProjectData = res.data.tenderProject
              this.tenderProjectData.codeUser = res.data.tenderProject.selectApply.codeUser
              this.tenderProjectData.entrustEnterpriseName = res.data.tenderProject.selectApply.entrustEnterprise.name
              this.tenderProjectData.approvePassTime = res.data.tenderProject.selectApply.approvePassTime
              // 该招标项目已归档
              if (res.data.tenderProject.isFiled === 1) {
                this.lookFlag = true
              }
            })
          } else {
            tenderproject.getOneByCode(this.tenderProjectCode).then((res) => {
              this.formData = res.data.tenderProject
              // 该招标项目已归档
              if (res.data.tenderProject.isFiled === 1) {
                this.lookFlag = true
              }
            })
          }
        } else if (this.selectType === 1) { // 来源于招标计划（只要招标办和项目单位可以选择招标计划）
          if (this.tenderProjectCode !== undefined && this.tenderProjectCode !== '') {
            tenderproject.getOneByCode(this.tenderProjectCode).then((res) => {
              this.projectFormData = res.data.tenderProject
              this.projectFormData.biddingType = res.data.tenderProject.projectPlan.bidType
              this.projectFormData.projectEnterpriseName = res.data.tenderProject.projectPlan.annualProjectPlan.enterpriseName
              this.projectFormData.requiresEnterpriseName = res.data.tenderProject.requiresEnterpriseName
            })
          } else {
            planproject.getByCode(this.projectPlanCode).then((res) => {
              this.projectFormData = res.data.projectPlan
              this.projectFormData.sourceType = 2
              this.projectFormData.relatedHistoryCode = res.data.projectPlan.code
              this.projectFormData.historySourceType = 2
              this.projectFormData.customProjectCode = this.tenderInfo.codeUser
              this.projectFormData.tenderProjectName = res.data.projectPlan.projectPlanName
              this.projectFormData.isEntrustBidding = 0
              this.projectFormData.biddingType = res.data.projectPlan.bidType
              this.projectFormData.tenderProjectType = res.data.projectPlan.projectType
              this.projectFormData.projectEnterpriseName = res.data.projectPlan.annualProjectPlan.enterpriseName
              this.projectFormData.requiresEnterpriseName = res.data.projectPlan.enterpriseName
              this.projectFormData.projectEnterpriseId = res.data.projectPlan.annualProjectPlan.enterpriseId
              this.projectFormData.requiresEnterpriseId = res.data.projectPlan.requiresEnterpriseId
            })
          }
        }
        if (this.tenderProjectCode !== undefined && this.tenderProjectCode !== '') {
          archivesManagement.getOneByRelatedCode(this.tenderProjectCode).then((res) => {
            if (res.data.archivesManagement) {
              if (this.selectType === 0) {
                this.agancyForm.userProjectCode = res.data.archivesManagement.userProjectCode
              }
              section.getAllByTenderCode(this.tenderProjectCode).then((res) => {
                this.bidphaseForm.tableData = res.data.sectionList
              })
            }
          })
        }
      } else {
        if (this.tenderProjectCode !== undefined && this.tenderProjectCode !== '') {
          tenderproject.getOneByCode(this.tenderProjectCode).then((res) => {
            if (res.data.tenderProject) {
              if (this.$store.getters.authUser.enterpriseType === 4) {
                this.agencyFormData = res.data.tenderProject
              } else {
                this.formData = res.data.tenderProject
              }
              // 该招标项目已归档
              if (res.data.tenderProject.isFiled === 1) {
                this.lookFlag = true
              }
            }
          })
          archivesManagement.getOneByRelatedCode(this.tenderProjectCode).then((res) => {
            if (res.data.archivesManagement) {
              this.agancyForm.userProjectCode = res.data.archivesManagement.userProjectCode
              section.getAllByTenderCode(this.tenderProjectCode).then((res) => {
                this.bidphaseForm.tableData = res.data.sectionList
              })
            }
          })
        } else {
          planproject.getByCode(this.projectPlanCode).then((res) => {
            if (this.$store.getters.authUser.enterpriseType === 4) {
              this.agencyFormData = res.data.projectPlan
              this.agencyFormData.sourceType = 2
              this.agencyFormData.relatedHistoryCode = res.data.projectPlan.code
              this.agencyFormData.historySourceType = 2
              this.agencyFormData.customProjectCode = this.tenderInfo.codeUser
              this.agencyFormData.tenderProjectName = res.data.projectPlan.projectPlanName
              this.agencyFormData.biddingType = res.data.projectPlan.bidType
              this.agencyFormData.tenderProjectType = res.data.projectPlan.projectType
              this.agencyFormData.projectEnterpriseName = res.data.projectPlan.annualProjectPlan.enterpriseName
              this.agencyFormData.requiresEnterpriseName = res.data.projectPlan.enterpriseName
              this.agencyFormData.projectEnterpriseId = res.data.projectPlan.annualProjectPlan.enterpriseId
              this.agencyFormData.requiresEnterpriseId = res.data.projectPlan.requiresEnterpriseId
            } else {
              // this.formData = res.data.projectPlan
              this.formData.sourceType = 2
              this.formData.relatedHistoryCode = res.data.projectPlan.code
              this.formData.historySourceType = 2
              this.formData.customProjectCode = this.tenderInfo.codeUser
              this.formData.tenderProjectName = res.data.projectPlan.projectPlanName
              this.formData.biddingType = res.data.projectPlan.bidType
              this.formData.tenderProjectType = res.data.projectPlan.projectType
              this.formData.projectEnterpriseName = res.data.projectPlan.annualProjectPlan.enterpriseName
              this.formData.requiresEnterpriseName = res.data.projectPlan.enterpriseName
              this.formData.projectEnterpriseId = res.data.projectPlan.annualProjectPlan.enterpriseId
              this.formData.requiresEnterpriseId = res.data.projectPlan.requiresEnterpriseId
            }
          })
          /* } else {
            this.formData.customProjectCode = this.tenderInfo.codeUser
            this.formData.tenderProjectName = this.tenderInfo.tenderProjectName
            this.formData.biddingType = this.tenderInfo.biddingType
            this.formData.projectEnterpriseName = this.$store.getters.authUser.enterpriseName
            this.formData.requiresEnterpriseName = this.$store.getters.authUser.enterpriseName
            this.formData.projectEnterpriseId = this.$store.getters.authUser.enterpriseId
            this.formData.requiresEnterpriseId = this.$store.getters.authUser.enterpriseId
          } */
        }
      }
    },
    getArchivesInfo () {
      archivesManagement.getOne(this.$route.query.code).then((res) => {
        if (this.bidType === 2) {
          // 来源于招标过程备案中的邀请招标的项目
          if (res.data.archivesManagement.tenderProject.historySourceType === 1) {
            // 是否继续委托招标
            if (res.data.archivesManagement.tenderProject.isEntrustBidding === 1) {
              this.tenderProjectData = res.data.archivesManagement.tenderProject
              this.tenderProjectData.codeUser = res.data.archivesManagement.tenderProject.selectApply.codeUser
              this.tenderProjectData.entrustEnterpriseName = res.data.archivesManagement.tenderProject.selectApply.entrustEnterprise.name
              this.tenderProjectData.approvePassTime = res.data.archivesManagement.tenderProject.selectApply.approvePassTime
              this.agancyForm.userProjectCode = res.data.archivesManagement.userProjectCode
            } else {
              this.projectFormData = res.data.archivesManagement.tenderProject
              this.projectFormData.customProjectCode = res.data.archivesManagement.tenderProject.selectApply.codeUser
              this.projectFormData.projectEnterpriseName = res.data.archivesManagement.tenderProject.projectEnterpriseName
              this.projectFormData.requiresEnterpriseName = res.data.archivesManagement.tenderProject.requiresEnterpriseName
            }
          } else {
            // 来源于招标计划中的邀请招标的项目
            this.projectFormData = res.data.archivesManagement.tenderProject
            this.projectFormData.customProjectCode = res.data.archivesManagement.tenderProject.customProjectCode
            this.projectFormData.projectEnterpriseName = res.data.archivesManagement.tenderProject.projectEnterpriseName
            this.projectFormData.requiresEnterpriseName = res.data.archivesManagement.tenderProject.requiresEnterpriseName
          }
        } else {
          if (this.$store.getters.authUser.enterpriseType === 4) {
            this.agencyFormData = res.data.archivesManagement.tenderProject
            this.agencyFormData.customProjectCode = res.data.archivesManagement.tenderProject.customProjectCode
            this.agencyFormData.projectEnterpriseName = res.data.archivesManagement.tenderProject.projectEnterpriseName
            this.agencyFormData.requiresEnterpriseName = res.data.archivesManagement.tenderProject.requiresEnterpriseName
          } else {
            this.formData = res.data.archivesManagement.tenderProject
            this.formData.customProjectCode = res.data.archivesManagement.tenderProject.customProjectCode
            this.formData.projectEnterpriseName = res.data.archivesManagement.tenderProject.projectEnterpriseName
            this.formData.requiresEnterpriseName = res.data.archivesManagement.tenderProject.requiresEnterpriseName
          }
        }
        // 该招标项目已归档
        if (res.data.archivesManagement.tenderProject.isFiled === 1) {
          this.lookFlag = true
        }
        this.getSectionsInArchives(res.data.archivesManagement.tenderProject.code)
      })
    },
    /** 得到该招标项目下标段的信息 */
    getSectionsInArchives (tenderProjectCode) {
      section.getAllByTenderCode(tenderProjectCode).then((res) => {
        this.bidphaseForm.tableData = res.data.sectionList
      })
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 表格操作 */
    handleClick (row, type, index) {
      if (Object.is(type, 'del')) {
        this.deleteRow(index)
      } else if (Object.is(type, 'edit')) {
        // 改变表格状态
        this.bidphaseForm.tableData = this.setTogglebutton(this.bidphaseForm.tableData, type, index)
      } else if (Object.is(type, 'save')) {
        // 确定当前标段名是否重复
        if (this.checkPhaseName(this.bidphaseForm.tableData, row, index)) {
          this.$confirm('标段名已存在', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
            beforeClose: (action, instance, done) => {
              if (!row.objectId) {
                this.deleteRow(this.bidphaseForm.tableData.length - 1)
              } else {
                section.getAllByTenderCode(this.tenderProjectCode).then((res) => {
                  this.bidphaseForm.tableData = res.data.sectionList
                })
              }
              done()
            }
          }).then().catch(() => {
            return false
          })
        } else {
          this.$refs['bidphaseForm'].validate((valid) => {
            if (valid) {
              this.bidphaseForm.tableData = this.setTogglebutton(this.bidphaseForm.tableData, type, index)
            }
          })
        }
      }
    },
    changeMeetingTime (data) {
      if (data) {
        if (data.toString().replace('-', '').replace('-', '') < formatDate(new Date(), 'yyyyMMdd')) {
          this.$confirm('选择的时间不能小于当前时间', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
            beforeClose: (action, instance, done) => {
              this.formData.meetingTime = ''
              done()
            }
          }).then().catch(() => {
            return false
          })
        }
      }
    },
    changeOpeningTime (data) {
      if (data.openingTime) {
        if (data.openingTime.toString().replace('-', '').replace('-', '') < formatDate(new Date(), 'yyyyMMdd')) {
          this.$confirm('选择的时间不能小于当前时间', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
            beforeClose: (action, instance, done) => {
              data.openingTime = ''
              done()
            }
          }).then().catch(() => {
            return false
          })
        }
      }
    },
    /** 归档状态（1.未归档 2.已归档） */
    filterArchivingStatus (row, column) {
      if (row.archivingStatus === 1) {
        return '未归档'
      } else {
        return '已归档'
      }
    },
    /** 新增一行 */
    addRow () {
      let obj = {
        name: '',
        flagIndex: 1,
        archivingStatus: 1,
        tenderProjectCode: (this.bidType !== 3) ? this.tenderProjectData.code : ''
      }
      this.bidphaseForm.tableData.push(obj)
    },
    checkPhaseName (tableData, obj, index) {
      let flag = false
      if (tableData.length > 1) {
        for (let i = 0; i < tableData.length; i++) {
          if (i !== index) {
            if (obj.name === tableData[i].name) {
              flag = true
              break
            }
          }
        }
        return flag
      }
    },
    /** 删除一行 */
    deleteRow (index) {
      this.bidphaseForm.tableData.splice(index, 1)
    },
    /** 设置按钮切换 */
    setTogglebutton (arr, type, index) {
      let newArr = []
      // 将设置好的数据拷贝一份
      newArr = arr.map((item, current) => {
        if (index === current) {
          if (Object.is(type, 'edit')) {
            item.flagIndex = 1
          } else if (Object.is(type, 'save')) {
            delete item.flagIndex
          }
          return item
        } else {
          return item
        }
      })
      return newArr
    }
  },
  /** 实例创建完成 */
  mounted () {
  }
}
</script>
<style lang="less">
  #inviteinfo-page {
    .info-frist {
      width: 100%;
      border-collapse: collapse;
      border: 1px solid #eee;
      .el-form-item {
        margin-bottom: 0;
      }
      tr td {
        min-height: 50px;
        line-height: 50px;
        border: 1px solid #eee;
      }
      tr td:nth-child(odd) {
        min-width: 100px;
        background: #F6F7F9;
      }
      tr td:nth-child(even) {
        min-width: 320px;
        text-align: left;
        padding: 0 10px;
      }
      .el-form-item__error {
        display: none;
      }
    }
    .info-second {
      margin-top: 30px;
      .add-btn-box {
        .el-icon-plus {
          font-size: 22px;
        }
      }
      .el-form-item {
        margin-bottom: 0;
      }
    }
    .agancybigbox {
      .el-form-item {
        margin-bottom: 0;
      }
      .el-form-item__content {
        margin-left: 0 !important;
        width: 50%;
      }
    }
  }
</style>
