<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="filemanage-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/files/filemanage' }">档案管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/files/filemanage/editfile' }">新增档案</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <!-- 步骤条start -->
    <div class="step-box">
      <div class="step-box">
        <!--组件-->
        <stepBar :activeStep="activeStep" :secondStepBarFlag="secondStepBarFlag"></stepBar>
      </div>
    </div>
    <!-- 步骤条end -->

    <!--主体内容start-->
    <div class="maincont-box">
      <transition name="slide-fade" mode="out-in">
        <!--懒加载组件-->
        <component :is="currentView"  ref= 'child' :tenderProjectCode = "tenderProjectCode" :selectType = "selectType" :projectPlanCode = "projectPlanCode" :bidType = "bidType" :tenderInfo = "tenderInfo">
          <!-- 组件在 vm.currentview 变化时改变！ -->
        </component>
      </transition>
      <div class="footbtn-box">
        <el-button type="primary" @click="upStep" v-if="activeStep !==1">上一步</el-button>
        <el-button type="primary" @click="nextStep" v-if="(secondStepBarFlag === true && activeStep !==3) || (secondStepBarFlag === false && activeStep !==2)">下一步</el-button>
      </div>
    </div>
    <!--主体内容end-->
    <!--弹窗区域start-->
    <div class="dialog-box">
    </div>
    <!--弹窗区域end-->
  </div>
</template>
<script>
import stepBar from './stepBar.vue'
import openInfo from './openInfo.vue'
import inviteInfo from './inviteInfo.vue'
import recordNotice from './recordnotice'
import openFile from './openfile'
import inviteFile from './invitefile'
import otherFile from './otherfile'
import {archivesManagement, archivesInfo, tenderproject} from 'api/index'
export default {
  data () {
    return {
      activeStep: 1, // 默认第一步,
      secondStepBarFlag: false, // 流程上的第二步是否展示，默认不展示
      currentView: 'openInfo',
      bidType: Number(this.$route.query.bidType), // 路由参数（招标方式1.公开2.邀请3.其他）
      tenderProjectCode: this.$route.query.tenderProjectCode,
      projectPlanCode: this.$route.query.projectPlanCode,
      selectType: Number(this.$route.query.selectType),
      tenderInfo: {
        tenderProjectName: this.$route.query.tenderProjectName,
        codeUser: this.$route.query.codeUser,
        biddingType: Number(this.$route.query.bidType)
      },
      dataStatusValue: false
    }
  },
  computed: {},
  created () {
    if (this.$route.query.key === true) {
      this.activeStep = 3
      this.currentView = 'openFile'
    } else {
      this.setBaseInfoByBiddingType()
    }
  },
  filters: {
  },
  /** 方法集 */
  methods: {
    /** 设置判断条件:根据采购方式做不同的设置 */
    setBaseInfoByBiddingType () {
      if (Object.is(this.bidType, 1)) {
        this.currentView = 'openInfo'
        this.secondStepBarFlag = true
      } else if (Object.is(this.bidType, 2)) {
        this.currentView = 'inviteInfo'
      } else if (!Object.is(this.bidType, 1) && !Object.is(this.bidType, 2)) {
        this.currentView = 'inviteInfo'
        if (this.$store.getters.authUser.enterpriseType === 4) {
          this.secondStepBarFlag = true
        }
      }
    },
    // 下一步
    nextStep () {
      // 新增、编辑操作
      if (this.$route.query.operation !== 'look') {
        if (this.activeStep === 1) {
          if (this.bidType === 1) {
            this.saveBaseInfo()
          } else {
            this.$refs.child.validateForm()
          }
        }
        if (this.dataStatusValue) {
          this.activeStep++
        }
        if (this.activeStep === 3) {
          if (this.$store.getters.authUser.enterpriseType === 4) {
            if (this.bidType === 1) {
              this.currentView = 'openFile'
            } else if (this.bidType === 2) {
              this.currentView = 'inviteFile'
            } else {
              this.currentView = 'otherFile'
            }
          } else if (this.bidType !== 1 && this.$store.getters.authUser.enterpriseType !== 4) {
            if (this.bidType === 2) {
              this.currentView = 'inviteFile'
            } else {
              this.currentView = 'otherFile'
            }
          }
        }
      } else {
        // 查看操作
        this.getTenderProjectCode()
      }
    },
    /** 将档案对应的招标项目code传递给子主件 */
    getTenderProjectCode () {
      archivesManagement.getOne(this.$route.query.code).then((res) => {
        this.tenderProjectCode = res.data.archivesManagement.tenderProjectCode
        let tenderProjectInfo = res.data.archivesManagement.tenderProject
        // 判断当前进入的企业是代理机构还是项目单位
        // 1.若为代理机构，首先判断当前项目是否由代理机构进行归档，公开招标一定是，邀请招标中的HISTORY_SOURCE_TYPE = 1且是继续委托招标项目
        // 2.此外的项目则按照项目单位中的要求进行跳转
        // 3.若为项目单位，首先判断当前项目是否由项目单位进行归档，邀请招标中的HISTORY_SOURCE_TYPE = 1且不继续委托招标项目、HISTORY_SOURCE_TYPE = 2、null的招标项目
        // 4.此外的项目则按照代理机构中的要求进行跳转
        if (this.activeStep++ > 2) this.activeStep = 1
        if (this.$store.getters.authUser.enterpriseType === 4) {
          if (this.activeStep === 2) {
            if (this.bidType === 1 || (this.bidType === 2 && tenderProjectInfo.historySourceType === 1 && tenderProjectInfo.isEntrustBidding === 1) || (this.bidType !== 1 && this.bidType !== 2 && tenderProjectInfo.relatedHistoryCode)) {
              this.secondStepBarFlag = true
              this.currentView = 'recordNotice'
            } else {
              this.secondStepBarFlag = false
              this.currentView = 'otherFile'
            }
          } else if (this.activeStep === 3) {
            if (this.bidType === 1) {
              this.currentView = 'openFile'
            } else if (this.bidType === 2 && tenderProjectInfo.historySourceType === 1 && tenderProjectInfo.isEntrustBidding === 1) {
              this.currentView = 'inviteFile'
            } else if (this.bidType !== 1 && this.bidType !== 2 && tenderProjectInfo.relatedHistoryCode) {
              this.currentView = 'otherFile'
            }
          }
        } else {
          if (this.activeStep === 2) {
            if (tenderProjectInfo.historySourceType === 0 || tenderProjectInfo.historySourceType === 2 || (this.bidType === 2 && tenderProjectInfo.historySourceType === 1 && tenderProjectInfo.isEntrustBidding === 0)) {
              this.currentView = 'otherFile'
              this.secondStepBarFlag = false
            } else {
              this.secondStepBarFlag = true
              this.currentView = 'recordNotice'
            }
          } else if (this.activeStep === 3) {
            if (this.bidType === 1) {
              this.currentView = 'openFile'
            } else if (this.bidType === 2 && tenderProjectInfo.historySourceType === 1 && tenderProjectInfo.isEntrustBidding === 1) {
              this.currentView = 'inviteFile'
            }
          }
        }
      })
    },
    /** 保存基本信息 */
    saveBaseInfo () {
      this.dataStatusValue = true
      if (this.bidType === 1) {
        this.saveOpenInfo()
      } else if (this.bidType === 2) {
        if (this.$store.getters.authUser.enterpriseType === 4 || this.selectType === 0) {
          this.saveInviteInfoByAgencyUnit()
        } else {
          this.saveInviteInfoByProjectUnit()
        }
      } else if (this.bidType !== 1 && this.bidType !== 2) {
        if (this.$store.getters.authUser.enterpriseType === 4) {
          this.saveOtherInfoByAgencyUnit()
        } else {
          this.saveOtherInfoByProjectUnit()
        }
      }
    },
    /** 保存招标代理机构-公开招标-基本信息 */
    saveOpenInfo () {
      let info = {
        archivesManagement: {},
        tenderProject: {},
        sectionList: []
      }
      let obj = {}
      let tenderProjectData = this.$refs.child.tenderProjectData
      let userProjectCode = this.$refs.child.agancyForm.userProjectCode
      if (this.$route.query.operation === 'save') {
        info.tenderProject = tenderProjectData
        archivesManagement.getOneByRelatedCode(this.tenderProjectCode).then((res) => {
          obj = {
            objectId: res.data.archivesManagement ? res.data.archivesManagement.objectId : '',
            tenderProjectCode: this.tenderProjectCode,
            userProjectCode: userProjectCode
          }
          info.archivesManagement = obj
          archivesInfo.save(info).then((res) => {
            if (res.data.resCode !== '0000') {
              this.activeStep = 1
            } else {
              this.tenderProjectCode = info.archivesManagement.tenderProjectCode
              this.currentView = 'recordNotice'
            }
          })
        })
      } else if (this.$route.query.operation === 'edit') {
        info.tenderProject = tenderProjectData
        archivesManagement.getOne(this.$route.query.code).then((res) => {
          obj = {
            objectId: res.data.archivesManagement.objectId,
            tenderProjectCode: res.data.archivesManagement.tenderProjectCode,
            userProjectCode: userProjectCode
          }
          info.archivesManagement = obj
          archivesInfo.save(info).then((res) => {
            if (res.data.resCode !== '0000') {
              this.activeStep = 1
            } else {
              this.tenderProjectCode = info.archivesManagement.tenderProjectCode
              this.currentView = 'recordNotice'
            }
          })
        })
      }
    },
    /** 保存招标代理机构-邀请招标-基本信息 */
    saveInviteInfoByAgencyUnit () {
      let info = {
        archivesManagement: {},
        tenderProject: {},
        sectionList: []
      }
      let obj = {}
      let tenderProjectData = this.$refs.child.tenderProjectData
      let tableData = this.$refs.child.bidphaseForm.tableData
      let userProjectCode = this.$refs.child.agancyForm.userProjectCode
      if (this.$route.query.operation === 'save') {
        // 更新招标项目表中的发出邀请的时间
        if (tenderProjectData) {
          if (this.selectType === 0) {
            // 将数据库中对应的招标项目的继续委托招标改为否
            tenderProjectData.isEntrustBidding = 0
          }
          info.tenderProject = tenderProjectData
        }
        // 保存该招标项目下的标段信息
        if (tableData.length > 0) {
          tableData.forEach(item => {
            if (!item.code) {
              item.tenderProjectCode = tenderProjectData.code
            }
          })
          info.sectionList = tableData
        }
        // 新增档案信息
        archivesManagement.getOneByRelatedCode(tenderProjectData.code).then((res) => {
          obj = {
            objectId: res.data.archivesManagement ? res.data.archivesManagement.objectId : '',
            tenderProjectCode: tenderProjectData.code,
            userProjectCode: userProjectCode
          }
          info.archivesManagement = obj
          archivesInfo.save(info).then((res) => {
            if (res.data.resCode !== '0000') {
              this.activeStep = 1
            } else {
              this.tenderProjectCode = info.archivesManagement.tenderProjectCode
              if (this.$store.getters.authUser.enterpriseType === 4) {
                this.currentView = 'recordNotice'
              } else {
                this.currentView = 'otherFile'
              }
            }
          })
        })
      } else if (this.$route.query.operation === 'edit') {
        // 更新招标项目表中的发出邀请的时间
        if (tenderProjectData) {
          info.tenderProject = tenderProjectData
        }
        // 保存该招标项目下的标段信息
        if (tableData.length > 0) {
          tableData.forEach(item => {
            if (!item.objectId) {
              item.tenderProjectCode = tenderProjectData.code
            }
          })
          info.sectionList = tableData
        }
        archivesManagement.getOne(this.$route.query.code).then((res) => {
          obj = {
            objectId: res.data.archivesManagement.objectId,
            tenderProjectCode: res.data.archivesManagement.tenderProjectCode,
            userProjectCode: userProjectCode
          }
          info.archivesManagement = obj
          archivesInfo.save(info).then((res) => {
            if (res.data.resCode !== '0000') {
              this.activeStep = 1
            } else {
              this.tenderProjectCode = info.archivesManagement.tenderProjectCode
              if (this.$store.getters.authUser.enterpriseType === 4) {
                this.currentView = 'recordNotice'
              } else {
                this.currentView = 'otherFile'
              }
            }
          })
        })
      }
    },
    /** 保存项目单位-邀请招标-基本信息(招标计划项目) */
    saveInviteInfoByProjectUnit () {
      let info = {
        archivesManagement: {},
        tenderProject: {},
        sectionList: []
      }
      let obj = {}
      let projectFormData = this.$refs.child.projectFormData
      let tableData = this.$refs.child.bidphaseForm.tableData
      if (this.$route.query.operation === 'save' && projectFormData) {
        info.tenderProject = projectFormData
        if (!this.tenderProjectCode) {
          info.tenderProject.objectId = null
        } else {
          // 保存该招标项目下的标段信息
          if (tableData.length > 0) {
            tableData.forEach(item => {
              if (!item.code) {
                item.tenderProjectCode = projectFormData.code
              }
            })
          }
        }
        info.sectionList = tableData
        // 保存该招标项目的档案信息
        archivesManagement.getOneByRelatedCode(projectFormData.code).then((res) => {
          obj = {
            objectId: res.data.archivesManagement ? res.data.archivesManagement.objectId : '',
            tenderProjectCode: projectFormData.code
          }
          info.archivesManagement = obj
          archivesInfo.save(info).then((res) => {
            if (res.data.resCode !== '0000') {
              this.activeStep = 1
            } else {
              this.tenderProjectCode = info.archivesManagement.tenderProjectCode
              this.currentView = 'otherFile'
            }
          })
        })
      } else if (this.$route.query.operation === 'edit') {
        // 更新招标项目
        info.tenderProject = projectFormData
        // 更新标段信息
        if (tableData.length > 0) {
          tableData.forEach(item => {
            if (!item.code) {
              item.tenderProjectCode = projectFormData.code
            }
          })
          info.sectionList = tableData
        }
        // 保存该招标项目的档案信息
        if (projectFormData.objectId) {
          archivesManagement.getOneByRelatedCode(projectFormData.code).then((res) => {
            info.archivesManagement = res.data.archivesManagement
            archivesInfo.save(info).then((res) => {
              if (res.data.resCode !== '0000') {
                this.activeStep = 1
              } else {
                this.tenderProjectCode = projectFormData.code
                this.currentView = 'otherFile'
              }
            })
          })
        }
      }
    },
    /** 保存招标代理机构-其他采购方式-基本信息(招标计划项目) */
    saveOtherInfoByAgencyUnit () {
      let info = {
        archivesManagement: {},
        tenderProject: {},
        sectionList: []
      }
      let obj = {}
      let agencyFormData = this.$refs.child.agencyFormData
      let tableData = this.$refs.child.bidphaseForm.tableData
      if (this.$route.query.operation === 'save' && agencyFormData) {
        // 招标项目的项目来源是档案管理
        agencyFormData.sourceType = 2
        agencyFormData.isEntrustBidding = 0
        info.tenderProject = agencyFormData
        if (!this.tenderProjectCode) {
          info.tenderProject.objectId = null
        } else {
          // 保存该招标项目下的标段信息
          if (tableData.length > 0) {
            tableData.forEach(item => {
              if (!item.code) {
                item.tenderProjectCode = agencyFormData.code
              }
            })
          }
        }
        info.sectionList = tableData
        // 保存该招标项目的档案信息
        archivesManagement.getOneByRelatedCode(agencyFormData.code).then((res) => {
          obj = {
            objectId: res.data.archivesManagement ? res.data.archivesManagement.objectId : '',
            tenderProjectCode: agencyFormData.code
          }
          info.archivesManagement = obj
          archivesInfo.save(info).then((res) => {
            if (res.data.resCode !== '0000') {
              this.activeStep = 1
            } else {
              tenderproject.getCodeByName(agencyFormData.tenderProjectName).then((res) => {
                this.tenderProjectCode = res.data.code
                this.currentView = 'recordNotice'
              })
            }
          })
        })
      } else if (this.$route.query.operation === 'edit') {
        // 更新招标项目
        info.tenderProject = agencyFormData
        // 更新标段信息
        if (tableData.length > 0) {
          tableData.forEach(item => {
            if (!item.code) {
              item.tenderProjectCode = agencyFormData.code
            }
          })
          info.sectionList = tableData
        }
        // 保存该招标项目的档案信息
        if (agencyFormData.objectId) {
          archivesManagement.getOneByRelatedCode(agencyFormData.code).then((res) => {
            info.archivesManagement = res.data.archivesManagement
            archivesInfo.save(info).then((res) => {
              if (res.data.resCode !== '0000') {
                this.activeStep = 1
              } else {
                this.tenderProjectCode = agencyFormData.code
                this.currentView = 'recordNotice'
              }
            })
          })
        }
      }
    },
    /** 保存项目单位-其他采购方式-基本信息 */
    saveOtherInfoByProjectUnit () {
      let info = {
        archivesManagement: {},
        tenderProject: {},
        sectionList: []
      }
      let obj = {}
      let formData = this.$refs.child.formData
      let tableData = this.$refs.child.bidphaseForm.tableData
      if (this.$route.query.operation === 'save' && formData) {
        // 招标项目的项目来源是档案管理
        formData.sourceType = 2
        formData.isEntrustBidding = 0
        info.tenderProject = formData
        if (!this.tenderProjectCode) {
          info.tenderProject.objectId = null
        } else {
          // 保存该招标项目下的标段信息
          if (tableData.length > 0) {
            tableData.forEach(item => {
              if (!item.code) {
                item.tenderProjectCode = formData.code
              }
            })
          }
        }
        info.sectionList = tableData
        // 保存该招标项目的档案信息
        archivesManagement.getOneByRelatedCode(formData.code).then((res) => {
          obj = {
            objectId: res.data.archivesManagement ? res.data.archivesManagement.objectId : '',
            tenderProjectCode: formData.code
          }
          info.archivesManagement = obj
          archivesInfo.save(info).then((res) => {
            if (res.data.resCode !== '0000') {
              this.activeStep = 1
            } else {
              tenderproject.getCodeByName(formData.tenderProjectName).then((res) => {
                this.tenderProjectCode = res.data.code
                this.currentView = 'otherFile'
              })
            }
          })
        })
      } else if (this.$route.query.operation === 'edit') {
        // 更新招标项目
        info.tenderProject = formData
        // 更新标段信息
        if (tableData.length > 0) {
          tableData.forEach(item => {
            if (!item.code) {
              item.tenderProjectCode = formData.code
            }
          })
          info.sectionList = tableData
        }
        // 保存该招标项目的档案信息
        if (formData.objectId) {
          archivesManagement.getOneByRelatedCode(formData.code).then((res) => {
            info.archivesManagement = res.data.archivesManagement
            archivesInfo.save(info).then((res) => {
              if (res.data.resCode !== '0000') {
                this.activeStep = 1
              } else {
                this.tenderProjectCode = formData.code
                this.currentView = 'otherFile'
              }
            })
          })
        }
      }
    },
    // 上一步
    upStep () {
      this.activeStep--
      if (this.activeStep === 1) {
        if (this.bidType === 1) {
          this.currentView = 'openInfo'
        } else {
          this.currentView = 'inviteInfo'
        }
      } else if (this.activeStep === 2) {
        this.currentView = 'recordNotice'
      }
    }
  },
  /** 实例创建完成 */
  mounted () {
  },
  /** 组件注册 */
  components: {
    stepBar, // 步骤条组件
    openInfo, // 招标代理机构-公开招标-基本信息
    inviteInfo, // 招标代理机构||项目单位-邀请招标&其他采购-基本信息
    recordNotice, // 招标代理机构-公开招标||邀请招标-备案及公告文件
    openFile, // 招标代理机构-公开招标-标段文件
    inviteFile, // 招标代理机构||项目单位-邀请招标-标段文件
    otherFile // 项目单位-其他采购-标段文件
  }
}
</script>
<style lang="less">
#filemanage-page {
  .step-box {
    margin: 20px;
    padding: 5px;
    background: #fff;
  }
  .footbtn-box {
    padding: 20px 0;
  }
}
</style>
