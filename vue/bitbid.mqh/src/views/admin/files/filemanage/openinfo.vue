<template>
  <!-- 公开招标基本信息组件 -->
  <div class="admin-layout" id="openinfo-page">
    <div class="openinfo-table">
      <table class="info-frist">
        <tr>
          <td>项目编号</td>
          <td>{{tenderProjectData.codeUser}}</td>
          <td>项目名称</td>
          <td>{{tenderProjectData.tenderProjectName}}</td>
        </tr>
        <tr>
          <td>是否委托招标</td>
          <td>是</td>
          <td>招标代理机构</td>
          <td>{{tenderProjectData.entrustEnterpriseName}}</td>
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
          <td>{{tenderProjectData.biddingType | filterBiddingType}}</td>
        </tr>
        <tr>
          <td>备案时间</td>
          <td>{{tenderProjectData.approvePassTime | filterDate}}</td>
          <td>公告时间</td>
          <td>{{noticeDateList[0] | filterDate}}
            <span style="display: inline-block;position: relative;left: 100px;">
              <el-dropdown>
                <el-button icon="el-icon-location-outline" circle></el-button>
                <el-dropdown-menu slot="dropdown">
                   <el-dropdown-item v-for="(item, index) in noticeDateList" :key="index + 1">{{item | filterDate}}</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </span>
          </td>
          <!--<td>发出邀请时间</td>-->
          <!--<td>-->
          <!--<el-date-picker-->
          <!--v-model="InvitationTime"-->
          <!--type="date"-->
          <!--placeholder="选择日期">-->
          <!--</el-date-picker>-->
          <!--</td>-->
        </tr>
        <tr v-if="!this.lookFlag">
          <td>代理机构项目编号</td>
          <td>
            <el-input type="text" v-model="agancyForm.userProjectCode"></el-input>
          </td>
          <!--<td><span style="color: red">*</span>代理机构项目编号</td>
          <td>
          <el-form :model="agancyForm" ref="agancyForm" label-width="100px" class="demo-dynamic agancybigbox">
            <el-form-item
              prop="userProjectCode"
              :rules="agancyForm.rules.userProjectCode">
              <el-input type="text" v-model="agancyForm.userProjectCode"></el-input>
            </el-form-item>
          </el-form>
          </td>-->
          <td></td>
          <td></td>
        </tr>
        <tr v-if="this.lookFlag">
          <td>代理机构项目编号</td>
          <td>{{agancyForm.userProjectCode}}</td>
          <td></td>
          <td></td>
        </tr>
      </table>
    </div>
    <div class="info-second">
      <el-table
        border
        :data="tableData"
        :row-class-name="tableRowClassName"
        style="width: 100%">
        <el-table-column
          prop="name"
          label="标段名称"
          align="center">
        </el-table-column>
        <el-table-column
          prop="winPrice"
          label="中标金额（万元）"
          align="center">
        </el-table-column>
        <el-table-column
          prop="openingTime"
          label="开标时间"
          align="center"
          :formatter="filterOpeningTime">
        </el-table-column>
        <el-table-column
          prop="biddingStatus"
          label="招标状态"
          align="center"
          :formatter="filterBiddingStatus">
        </el-table-column>
        <el-table-column
          prop="archivingStatus"
          label="归档状态"
          align="center"
          :formatter="filterArchivingStatus">
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
import {tenderproject, notice, section, archivesManagement} from 'api/index'
import {formatDate} from 'common/js/date'
export default {
  data () {
    return {
      tableData: [],
      tenderProjectData: {},
      agancyForm: {
        userProjectCode: '', // 代理机构项目编号
        rules: {
          userProjectCode: {type: 'string', required: true, message: '必填字段', trigger: 'blur'}
        }
      },
      lookFlag: false, // 判断是否为查看操作
      noticeDateList: [] // 该招标项目下的公告建立时间的集合
    }
  },
  props: ['tenderProjectCode'],
  computed: {
  },
  watch: {
  },
  created () {
    if (this.$route.query.operation === 'save') {
      this.getTenderprojectInfo()
      this.getSectionsInTender()
      this.getNoticesInTender()
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
      } else {
        return '邀请招标'
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
    }
  },
  /** 方法集 */
  methods: {
    /* validateForm () {
      this.$refs['agancyForm'].validate((valid) => {
        if (valid) {
          this.$refs.agancyForm.$parent.$parent.saveBaseInfo()
        }
      })
    }, */
    /** 得到招标项目的基本信息 */
    getTenderprojectInfo () {
      archivesManagement.getOneByRelatedCode(this.tenderProjectCode).then((res) => {
        if (res.data.archivesManagement) {
          this.tenderProjectData = res.data.archivesManagement.tenderProject
          this.tenderProjectData.codeUser = res.data.archivesManagement.tenderProject.selectApply.codeUser
          this.tenderProjectData.entrustEnterpriseName = res.data.archivesManagement.tenderProject.selectApply.entrustEnterprise.name
          this.tenderProjectData.approvePassTime = res.data.archivesManagement.tenderProject.selectApply.approvePassTime
          // 该招标项目已归档
          if (res.data.archivesManagement.tenderProject.isFiled === 1) {
            this.lookFlag = true
          }
          this.agancyForm.userProjectCode = res.data.archivesManagement.userProjectCode
          this.getSectionsInArchives(res.data.archivesManagement.tenderProject.code)
          this.getNoticesInArchives(res.data.archivesManagement.tenderProject.code)
        } else {
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
        }
      })
    },
    /** 得到该招标项目下的公告信息 */
    getNoticesInTender () {
      notice.selectNoticeDateByCode(this.tenderProjectCode).then((res) => {
        if (res.data.noticeDateList) {
          this.noticeDateList = res.data.noticeDateList
        }
      })
    },
    /** 得到该招标项目下标段的信息 */
    getSectionsInTender () {
      section.getAllByTenderCode(this.tenderProjectCode).then((res) => {
        if (res.data.sectionList) {
          this.tableData = res.data.sectionList
        }
      })
    },
    getArchivesInfo () {
      archivesManagement.getOne(this.$route.query.code).then((res) => {
        this.tenderProjectData = res.data.archivesManagement.tenderProject
        this.tenderProjectData.codeUser = res.data.archivesManagement.tenderProject.selectApply.codeUser
        this.tenderProjectData.entrustEnterpriseName = res.data.archivesManagement.tenderProject.selectApply.entrustEnterprise.name
        this.tenderProjectData.approvePassTime = res.data.archivesManagement.tenderProject.selectApply.approvePassTime
        // 该招标项目已归档
        if (res.data.archivesManagement.tenderProject.isFiled === 1) {
          this.lookFlag = true
        }
        this.agancyForm.userProjectCode = res.data.archivesManagement.userProjectCode
        this.getSectionsInArchives(res.data.archivesManagement.tenderProject.code)
        this.getNoticesInArchives(res.data.archivesManagement.tenderProject.code)
      })
    },
    getNoticesInArchives (tenderProjectCode) {
      notice.selectNoticeDateByCode(tenderProjectCode).then((res) => {
        if (res.data.noticeDateList) {
          this.noticeDateList = res.data.noticeDateList
        }
      })
    },
    /** 得到该招标项目下标段的信息 */
    getSectionsInArchives (tenderProjectCode) {
      section.getAllByTenderCode(tenderProjectCode).then((res) => {
        this.tableData = res.data.sectionList
      })
    },
    /** 招标状态（1.进行中 2.终止 3.已完成） */
    filterBiddingStatus (row, column) {
      if (row.biddingStatus === 1) {
        return '进行中'
      } else if (row.biddingStatus === 2) {
        return '终止'
      } else {
        return '已完成'
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
    /** 标段开标时间格式化 */
    filterOpeningTime (row, column) {
      if (row.openingTime) {
        let date = new Date(row.openingTime)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return null
      }
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    }
  },
  /** 实例创建完成 */
  mounted () {
  }
}
</script>
<style lang="less">
#openinfo-page {
  .info-frist {
    width: 100%;
    border-collapse: collapse;
    border: 1px solid #eee;
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
  }
  .agancybigbox{
    .el-form-item {
       margin-bottom: 0;
    }
    .el-form-item__content {
      margin-left: 0!important;
      width: 50%;
    }
  }
}
</style>
