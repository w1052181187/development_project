<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="expert-check-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>考评系统</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/evaluate/experteval' }">专家考评</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/evaluate/experteval/check' }">考评</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <!--项目基本信息start-->
    <div class="pro-info">
      <div class="pro-title">
        <span>项目基本信息</span>
        <el-button type="primary" size="mini" class="pro-tit-btn" @click="handleBtnFun('files')">档案查询</el-button>
        <el-button type="primary" size="mini" class="pro-tit-btn" @click="handleBtnFun('bid')">招标备案</el-button>
      </div>
      <div class="pro-form">
        <el-form :model="projectInfoForm" label-width="100px">
          <el-row :gutter="20">
            <el-col :span="7">
              <el-form-item label="项目名称:">
                <span>{{projectInfoForm.tenderProjectName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="项目编号:">
                <span>{{projectInfoForm.codeUser}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="项目单位:">
                <span>{{projectInfoForm.projectEnterpriseName}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="7">
              <el-form-item label="代理机构:">
                <span>{{projectInfoForm.agencyEnterpriseName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="项目类型:">
                <span>{{projectInfoForm.tenderProjectType | filterType}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="采购方式:">
                <span>{{projectInfoForm.biddingType | filterBiddingType}}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </div>
    <!--项目基本信息end-->
    <!--工作考评start-->
    <div class="work-eval">
      <div class="pro-title">
        <span>工作评价</span>
      </div>
      <div class="eval-cont">
        <el-card class="box-card card-list" v-for="(item, index) in cardList" :key="index">
          <el-row class="row-border">
            <div class="check-box" style="margin-bottom: 15px;">
              <span class="hj-span" v-if="item.isShow === 1">预审</span>
              <span class="hj-span" v-if="item.isShow === 0">评标</span>
              <el-checkbox-group v-model="checkboxGroup[index]" @change="changeGroup">
                <el-checkbox-button
                  border
                  @change="checkedNameChange(o, item.noticeInfo.code, index)"
                  v-for="o in item.nameList"
                  :label="o.objectId"
                  :key="o.objectId">
                  {{o.name}}
                </el-checkbox-button>
              </el-checkbox-group>
            </div>
          </el-row>
          <div class="card-bottom">
            <p>公告名称: <span>{{item.noticeInfo.noticeName}}</span></p>
            <p style="width:90%">评标标段:
              <span v-for="(sectionName, index) in item.sectionNameList" :key="sectionName" v-if="index !== item.sectionNameList.length - 1">{{sectionName}}、</span>
              <span v-for="(sectionName, index) in item.sectionNameList" :key="sectionName" v-if="index === item.sectionNameList.length - 1">{{sectionName}}</span>
            </p>
            <div class="bottom clearfix">
              <el-button type="text" class="button"  @click="handleBatch(item, index)">批量评价</el-button>
            </div>
          </div>
        </el-card>
        <div class="eval-btn">
          <el-button type="primary" @click="saveClick">保 存</el-button>
          <el-button type="success" @click="submitClick">提 交</el-button>
          <el-button type="info" @click="$router.go(-1)">取 消</el-button>
        </div>
      </div>
    </div>
    <!--工作考评end-->

    <!--弹窗区域start-->
    <div class="dialog-box">
      <!--专家评价start-->
      <el-dialog
        title="评价"
        :visible.sync="dialogVisible"
        width="50%"
        center>
        <div class="dialog-eval-cont">
          <!--项目基本信息start-->
          <div class="pro-info">
            <div class="pro-title">
              <span>基本信息</span>
            </div>
            <div class="pro-form">
              <el-form :model="expertInfoForm" label-width="100px">
                <el-row :gutter="20">
                  <el-col :span="7">
                    <el-form-item label="专家姓名:">
                      <span>{{expertInfoForm.name}}</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="7">
                    <el-form-item label="身份证号:">
                      <span>{{expertInfoForm.idCard}}</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="7">
                    <el-form-item label="性别:">
                      <span>{{expertInfoForm.sex | filterSex}}</span>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row :gutter="20">
                  <el-col :span="7">
                    <el-form-item label="单位:">
                      <span>{{expertInfoForm.enterpriseName}}</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="7">
                    <el-form-item label="地区:">
                      <span>{{expertInfoForm.cityId | filterCity}}</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="7">
                    <el-form-item label="专家类别:">
                      <span>{{expertInfoForm.expertSources | filterSources}}</span>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row :gutter="20">
                  <el-col :span="14">
                    <el-form-item label="专业:">
                      <span>{{expertInfoForm.expertClassify}}</span>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row :gutter="20">
                  <el-col :span="14">
                    <el-form-item label="参与标段:">
                      <span>{{expertInfoForm.sectionNameListStr}}</span>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>
            </div>
          </div>
          <!--项目基本信息end-->
          <!--工作考评start-->
          <div class="work-eval">
            <div class="pro-title">
              <span>工作考评</span>
            </div>
            <div class="eval-cont eval-warp">
              <el-form :model="workEvalForm" label-width="140px" class="demo-dynamic" ref="workEvalForm">
                <el-form-item
                  :label="domain.labelName+':'"
                  v-for="(domain, index) in workEvalForm.workEvalList"
                  :key="index"
                  :prop="'workEvalList.' + index + '.evaluateResult'"
                  :rules="workEvalForm.rules.evaluateResult"
                  v-if="domain.isDelete === 0">
                  <el-radio-group v-model="domain.evaluateResult" @change="changRadioBtn(domain)">
                    <el-radio-button :label="1" class="radio-first">好</el-radio-button>
                    <el-radio-button :label="2" class="radio-second">一般</el-radio-button>
                    <el-radio-button :label="3" class="radio-third">差</el-radio-button>
                  </el-radio-group>
                  <el-input v-model="domain.evaluateRemark" style="width: 300px" v-if="domain.showFlag"></el-input>
                  <el-button type="danger" @click.prevent="removeDomain(domain)" v-if="domain.addFlag">删除</el-button>
                </el-form-item>
              </el-form>
            </div>
          </div>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button icon="el-icon-plus" @click="addOtherEval">新增其他评价</el-button>
          <el-button type="primary" @click="dialogSubmitBtn('workEvalForm')">提 交</el-button>
        </span>
      </el-dialog>
      <!--专家评价end-->
      <!--集体评价start-->
      <el-dialog
        title="集体评价"
        :visible.sync="dialogTeamVisible"
        width="50%"
        center>
        <div class="dialog-eval-cont">
          <!--项目基本信息start-->
          <div class="pro-info">
            <div class="pro-title">
              <span>基本信息</span>
            </div>
            <div class="pro-form">
              <el-form :model="expertTeamInfoForm" label-width="100px">
                <el-row :gutter="20">
                  <el-col :span="20">
                    <el-form-item label="专家姓名:">
                      <span>{{expertTeamInfoForm.nameListStr}}</span>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>
            </div>
          </div>
          <!--项目基本信息end-->
          <!--工作考评start-->
          <div class="work-eval">
            <div class="pro-title">
              <span>工作考评</span>
            </div>
            <div class="eval-cont eval-warp">
              <el-form :model="workEvalForm" label-width="140px" class="demo-dynamic" ref="workEvalForm">
                <el-form-item
                  :label="domain.labelName+':'"
                  v-for="(domain, index) in workEvalForm.workEvalList"
                  :key="index"
                  :prop="'workEvalList.' + index + '.evaluateResult'"
                  :rules="workEvalForm.rules.evaluateResult"
                  v-if="domain.isDelete === 0">
                  <el-radio-group v-model="domain.evaluateResult" @change="changRadioBtn(domain)">
                    <el-radio-button :label="1" class="radio-first">好</el-radio-button>
                    <el-radio-button :label="2" class="radio-second">一般</el-radio-button>
                    <el-radio-button :label="3" class="radio-third">差</el-radio-button>
                  </el-radio-group>
                  <el-input v-model="domain.evaluateRemark" style="width: 300px" v-if="domain.showFlag"></el-input>
                  <el-button type="danger" @click.prevent="removeDomain(domain)" v-if="domain.addFlag">删除</el-button>
                </el-form-item>
              </el-form>
            </div>
          </div>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button icon="el-icon-plus" @click="addOtherEval">新增其他评价</el-button>
          <el-button type="primary" @click="dialogBatchSubmitBtn(('workEvalForm'))">提 交</el-button>
        </span>
      </el-dialog>
      <!--集体评价end-->

      <!--新增评价start-->
      <el-dialog
        title="新增"
        :visible.sync="dialogAddVisible"
        width="30%"
        center>
        <el-form ref="dialogAddNameForm" :model="dialogAddNameForm"  :rules="dialogAddNameForm.rules" class="demo-form-inline">
          <el-form-item label="评价类型名称:" prop="labelName">
            <el-input v-model="dialogAddNameForm.labelName" placeholder="评价类型名称"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancelAddNameDialogBtn">取 消</el-button>
          <el-button type="primary" @click="addNameDialogBtn('dialogAddNameForm')">确 定</el-button>
        </span>
      </el-dialog>
      <!--新增评价end-->

      <!--批量评价start-->
      <el-dialog
        title="批量评价"
        :visible.sync="dialogBatchVisible"
        :before-close="handleClose"
        width="30%"
        center>
        <div class="pl-check">
          <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
          <div style="margin: 15px 0;"></div>
          <el-checkbox-group v-model="checkedName">
            <el-checkbox v-for="(item, index) in nameList" :label="item.name" :key="index"  @change="handleCheckedNameChange(item)">{{item.name}}</el-checkbox>
          </el-checkbox-group>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
          <el-button type="primary" @click="batchDialogBtn">确 定</el-button>
        </span>
      </el-dialog>
      <!--批量评价end-->
    </div>
    <!--弹窗区域end-->
  </div>
</template>
<script>
import {tenderproject, expertEvaluateInfo, expertmanage, projectEvaluate} from 'api/index'
import {expertclassify} from 'api/resource/expertmanage/expertclassify'
// 实例
export default {
  data () {
    return {
      projectInfoForm: {},
      dialogVisible: false,
      expertInfoForm: {}, // 专家-基本信息
      workEvalForm: {
        workEvalList: [],
        rules: {
          evaluateResult: [
            {required: true, message: '请进行评价！', trigger: ['blur', 'change']}
          ]
        }
      },
      nameList: [],
      cardList: [
        // {nameList: [], noticeName: '', sectionNameList: []}
      ],
      dialogAddVisible: false,
      dialogAddNameForm: {
        labelName: '',
        rules: {
          labelName: [
            {required: true, message: '请输入标签名称！', trigger: ['blur', 'change']}
          ]
        }
      },
      AddNameFormRules: {},
      checkboxGroup: [],
      dialogBatchVisible: false, // 批量评价
      checkAll: false,
      isIndeterminate: true,
      checkedName: [],
      checkedNameList: [], // 用于后台保存数据
      dialogTeamVisible: false, // 集体评价
      expertTeamInfoForm: {},
      projectEvaluateInfo: {},
      currentChooseRowNum: '', // 当前选择的公告
      currentExpertEvaluateInfo: {}, // 当前专家的评价信息
      currentExpertEvaluateInfoList: [] // 当前专家的评价信息(批量操作)
    }
  },
  created () {
    this.getProjectInfo()
    this.getNoticeInfoList()
    this.getProjectEvaluateInfo()
  },
  filters: {
    filterType (type) {
      if (type === 1) {
        return '工程'
      } else if (type === 2) {
        return '货物'
      } else if (type === 3) {
        return '服务'
      }
    },
    filterBiddingType (type) {
      if (type === 1) {
        return '公开招标'
      } else if (type === 2) {
        return '邀请招标'
      } else if (type === 3) {
        return '谈判采购'
      } else if (type === 4) {
        return '询比采购'
      } else if (type === 5) {
        return '竞价采购'
      } else if (type === 6) {
        return '直接采购'
      } else if (type === 7) {
        return '框架协议'
      }
    },
    filterSex (val) {
      let sex = val
      if (sex === 1) {
        return '男'
      } else if (sex === 2) {
        return '女'
      }
    },
    filterSources (val) {
      let expertSources = val
      if (expertSources === 1) {
        return '在库专家'
      } else if (expertSources === 2) {
        return '公司内部专家'
      } else if (expertSources === 3) {
        return '其他'
      }
    },
    filterStatus (val) {
      let expertStatus = val
      if (expertStatus === 0) {
        return '激活'
      } else if (expertStatus === 1) {
        return '锁定'
      }
    },
    filterCity (val) {
      let cityId = val
      if (cityId === '14') {
        return '太原市'
      } else if (cityId === '15') {
        return '大同市'
      } else if (cityId === '16') {
        return '阳泉市'
      } else if (cityId === '17') {
        return '长治市'
      } else if (cityId === '18') {
        return '晋城市'
      } else if (cityId === '19') {
        return '朔州市'
      } else if (cityId === '20') {
        return '晋中市'
      } else if (cityId === '21') {
        return '运城市'
      } else if (cityId === '22') {
        return '忻州市'
      } else if (cityId === '23') {
        return '临汾市'
      } else if (cityId === '24') {
        return '吕梁市'
      }
    }
  },
  methods: {
    /** 高亮设置 */
    setHighlight () {
      this.checkboxGroup = []
      for (let index in this.cardList) {
        let arr = []
        for (let i in this.cardList[index].nameList) {
          if (this.cardList[index].nameList[i].isEvaluate === 1) {
            arr.push(this.cardList[index].nameList[i].objectId)
          }
        }
        this.$set(this.checkboxGroup, index, arr)
      }
    },
    /** 获取当前招标项目信息 */
    getProjectInfo () {
      tenderproject.getOneInEvaluate(this.$route.query.code).then((res) => {
        this.projectInfoForm = res.data.tenderProject
      })
    },
    /** 获取对应的标签信息 */
    getEvaluateLabelList (idCard, expertType, noticeCode) {
      this.workEvalForm.workEvalList = []
      expertEvaluateInfo.getLabelByRelatedCode(idCard, expertType, noticeCode).then((res) => {
        let evaluateInfos = []
        let evaluateLabels = []
        if (res.data.evaluateInfos) {
          evaluateInfos = res.data.evaluateInfos
        } else {
          evaluateLabels = res.data.evaluateLabels
        }
        if (evaluateLabels.length > 0) {
          evaluateLabels.forEach(item => {
            item.objectId = ''
            item.relatedLabelCode = item.code
            item.evaluateResult = ''
            item.evaluateRemark = ''
            item.showFlag = false
            item.isDelete = 0
            this.workEvalForm.workEvalList.push(item)
          })
        } else if (evaluateInfos.length > 0) {
          evaluateInfos.forEach(item => {
            item.isDelete = 0
            if (item.relatedLabelCode === '') {
              item.addFlag = '1'
            }
            if (item.evaluateResult === 3) {
              item.showFlag = true
            } else {
              item.showFlag = false
            }
            this.workEvalForm.workEvalList.push(item)
          })
        }
      })
    },
    /** 获取公告部分的信息 */
    getNoticeInfoList () {
      expertEvaluateInfo.getListByRelatedCode(this.$route.query.code).then((res) => {
        if (res.data.noticeSectionExpertInfoList.length > 0) {
          let isPrequalification = res.data.noticeSectionExpertInfoList[0].isPrequalification
          if (isPrequalification === 0) { // 该项目为资格后审
            res.data.noticeSectionExpertInfoList.forEach(item => {
              if (item.expertInfoList && item.expertInfoList.length > 0) {
                let obj = {
                  isShow: 0,
                  noticeInfo: {},
                  sectionNameList: [],
                  nameList: []
                }
                // 公告
                obj.noticeInfo = {
                  code: item.notice.code,
                  noticeName: item.notice.noticeName
                }
                // 评标标段
                item.sectionList.forEach(section => {
                  obj.sectionNameList.push(section.name)
                })
                // 评标专家
                item.expertInfoList.forEach(expertInfo => {
                  obj.nameList.push({objectId: expertInfo.objectId, code: expertInfo.code, name: expertInfo.name, idCard: expertInfo.idCard, expertType: expertInfo.expertType, isEvaluate: expertInfo.isEvaluate})
                  this.nameList.push({objectId: expertInfo.objectId, code: expertInfo.code, name: expertInfo.name, idCard: expertInfo.idCard, expertType: expertInfo.expertType, isEvaluate: expertInfo.isEvaluate})
                })
                // this.$set(this.checkboxGroup, this.cardList.length, arr)
                this.cardList.push(obj)
              }
            })
          } else {
            res.data.noticeSectionExpertInfoList.forEach(item => {
              if (item.expertInfoList && item.expertInfoList.length > 0) {
                let obj1 = {
                  isShow: 1,
                  noticeInfo: {},
                  sectionNameList: [],
                  nameList: []
                }
                let obj2 = {
                  isShow: 0,
                  noticeInfo: {},
                  sectionNameList: [],
                  nameList: []
                }
                // 公告
                obj1.noticeInfo = {
                  code: item.notice.code,
                  noticeName: item.notice.noticeName
                }
                obj2.noticeInfo = {
                  code: item.notice.code,
                  noticeName: item.notice.noticeName
                }
                // 评标标段
                item.sectionList.forEach(section => {
                  obj1.sectionNameList.push(section.name)
                  obj2.sectionNameList.push(section.name)
                })
                // 评标专家
                item.expertInfoList.forEach(expertInfo => {
                  if (expertInfo.expertType === 2) { // 招标备案资格预审会专家
                    obj1.nameList.push({objectId: expertInfo.objectId, code: expertInfo.code, name: expertInfo.name, idCard: expertInfo.idCard, expertType: expertInfo.expertType, isEvaluate: expertInfo.isEvaluate})
                    // this.nameList.push({objectId: expertInfo.objectId, code: expertInfo.code, name: expertInfo.name, idCard: expertInfo.idCard, isEvaluate: expertInfo.isEvaluate})
                  } else { // 招标备案评标专家
                    obj2.nameList.push({objectId: expertInfo.objectId, code: expertInfo.code, name: expertInfo.name, idCard: expertInfo.idCard, expertType: expertInfo.expertType, isEvaluate: expertInfo.isEvaluate})
                    // this.nameList.push({objectId: expertInfo.objectId, code: expertInfo.code, name: expertInfo.name, idCard: expertInfo.idCard, isEvaluate: expertInfo.isEvaluate})
                  }
                })
                // this.$set(this.checkboxGroup, this.cardList.length, arr)
                if (obj1.nameList.length > 0) {
                  this.cardList.push(obj1)
                }
                if (obj2.nameList.length > 0) {
                  this.cardList.push(obj2)
                }
              }
            })
          }
        }
        this.setHighlight()
      })
    },
    /** 获取项目评价信息 */
    getProjectEvaluateInfo () {
      projectEvaluate.getOne(this.$route.query.code, 3).then((res) => {
        this.projectEvaluateInfo = res.data.projectEvaluate
      })
    },
    /** [档案管理]|[招标备案]-操作按钮 */
    handleBtnFun (type) {
      if (Object.is(type, 'bid')) { // 招标备案
        this.$router.push({path: '/admin/record/processrecord/flowchart', query: {tenderProjectCode: this.projectInfoForm.code}})
      } else if (Object.is(type, 'files')) { // 档案管理
        this.$router.push({path: '/admin/files/filemanage/editfile', query: {type: false, operation: 'look', bidType: this.projectInfoForm.biddingType, code: this.projectInfoForm.archivesCode}})
      }
    },
    /** 保存 */
    saveClick () {
      if (this.cardList.length > 0) {
        let projectEvaluateInfo = {
          objectId: (this.projectEvaluateInfo && this.projectEvaluateInfo.objectId) ? this.projectEvaluateInfo.objectId : '',
          code: (this.projectEvaluateInfo && this.projectEvaluateInfo.code) ? this.projectEvaluateInfo.code : '',
          relatedProjectCode: this.$route.query.code,
          projectEvaluateType: 3,
          status: 0,
          auditStatus: 2,
          submitEnterpriseId: this.$store.getters.authUser.enterpriseId
        }
        projectEvaluate.save(projectEvaluateInfo).then((res) => {
          if (res.data.resCode === '0000') {
            this.$router.push({path: '/admin/evaluate/experteval'})
          }
        })
      }
      /* let nums = 0
        for (let i = 0; i < this.cardList.length; i++) {
          if (this.getNoticeExpert(this.cardList[i])) {
            nums++
          }
        }
        if (nums !== this.cardList.length) {
          this.$message.error('还存在未评价专家，不能进行保存！')
          return false
        } else {
          let projectEvaluateInfo = {
            objectId: (this.projectEvaluateInfo && this.projectEvaluateInfo.objectId) ? this.projectEvaluateInfo.objectId : '',
            code: (this.projectEvaluateInfo && this.projectEvaluateInfo.code) ? this.projectEvaluateInfo.code : '',
            relatedProjectCode: this.$route.query.code,
            projectEvaluateType: 3,
            status: 0,
            auditStatus: 2,
            submitEnterpriseId: this.$store.getters.authUser.enterpriseId
          }
          projectEvaluate.save(projectEvaluateInfo).then((res) => {
            if (res.data.resCode === '0000') {
              this.$router.push({path: '/admin/evaluate/experteval'})
            }
          })
        } */
    },
    /** 获取每条公告中专家的评价情况（全部都已经评价则返回true,反之则为false） */
    getNoticeExpert (notice) {
      let flag = false
      let i = 0
      let num = 0
      for (; i < notice.nameList.length; i++) {
        if (Number(notice.nameList[i].isEvaluate) === 1) {
          num++
        }
      }
      if (num === notice.nameList.length) {
        flag = true
      }
      return flag
    },
    /** 提交 */
    submitClick () {
      if (this.cardList.length > 0) {
        let nums = 0
        for (let i = 0; i < this.cardList.length; i++) {
          if (this.getNoticeExpert(this.cardList[i])) {
            nums++
          }
        }
        if (nums !== this.cardList.length) {
          this.$message.error('还存在未评价专家，不能进行保存！')
          return false
        } else {
          let projectEvaluateInfo = {
            objectId: (this.projectEvaluateInfo && this.projectEvaluateInfo.objectId) ? this.projectEvaluateInfo.objectId : '',
            code: (this.projectEvaluateInfo && this.projectEvaluateInfo.code) ? this.projectEvaluateInfo.code : '',
            relatedProjectCode: this.$route.query.code,
            projectEvaluateType: 3,
            status: 1,
            auditStatus: 2,
            submitEnterpriseId: this.$store.getters.authUser.enterpriseId
          }
          projectEvaluate.save(projectEvaluateInfo).then((res) => {
            if (res.data.resCode === '0000') {
              this.$router.push({path: '/admin/evaluate/experteval'})
            }
          })
        }
      }
    },
    /** 批量操作 */
    handleBatch (item, index) {
      this.cancel()
      this.nameList = []
      if (item.nameList.length > 0) {
        item.nameList.forEach(expertInfo => {
          this.nameList.push({objectId: expertInfo.objectId, code: expertInfo.code, name: expertInfo.name, idCard: expertInfo.idCard, expertType: expertInfo.expertType, isEvaluate: expertInfo.isEvaluate})
        })
        this.dialogBatchVisible = true
        this.currentChooseRowNum = index
        this.expertTeamInfoForm.noticeCode = item.noticeInfo.code
        this.getEvaluateLabelList(null, 0, item.noticeInfo.code)
      }
    },
    /** 批量评价-确定-弹窗 */
    batchDialogBtn () {
      if (this.checkedName.length === 0) {
        this.$message.error('请选择专家！')
        return false
      } else {
        this.spliceExpertName()
        this.currentExpertEvaluateInfoList = []
        this.checkedNameList.forEach(item => {
          expertEvaluateInfo.getOne(item.idCard, item.expertType, this.cardList[this.currentChooseRowNum].noticeInfo.code).then((res) => {
            if (res.data.expertEvaluateInfo) {
              let obj = {
                objectId: res.data.expertEvaluateInfo.objectId,
                code: res.data.expertEvaluateInfo.code,
                relatedNoticeCode: res.data.expertEvaluateInfo.relatedNoticeCode,
                targetExpertCode: res.data.expertEvaluateInfo.targetExpertCode,
                relatedExpertType: res.data.expertEvaluateInfo.relatedExpertType
              }
              this.currentExpertEvaluateInfoList.push(obj)
            }
          })
        })
        this.dialogTeamVisible = true
      }
    },
    handleClose (done) {
      this.cancel()
      done()
    },
    /** 取消已选择的专家信息 */
    cancel () {
      this.dialogBatchVisible = false
      this.isIndeterminate = true
      this.checkedName = []
      this.checkedNameList = []
    },
    /** 全选 */
    handleCheckAllChange (val) {
      let arr = this.nameList.map(item => {
        return item.name
      })
      this.checkedName = val ? arr : []
      this.checkedNameList = this.nowData
      this.isIndeterminate = false
    },
    handleCheckedNameChange (item) {
      let checkedCount = item.length
      this.checkAll = checkedCount === this.nameList.length
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.nameList.length
      if (this.checkedName.length === 0) {
        this.checkedNameList = []
      } else {
        this.checkedNameList = []
        for (let i = 0; i < this.checkedName.length; i++) {
          for (let j = 0; j < this.nameList.length; j++) {
            if (this.checkedName[i] === this.nameList[j].name) {
              this.checkedNameList.push(this.nameList[j])
            }
          }
        }
      }
    },
    /** 用于排除未被选择的专家信息 */
    getIndex () {
      let index = -1
      if (this.checkedNameList.length > 0) {
        for (let i = 0; i < this.checkedNameList.length; i++) {
          for (let j = 0; j < this.checkedName.length; j++) {
            if (this.checkedNameList[i].name === this.checkedName[j]) {
              index = i
              break
            }
          }
        }
      }
      return index
    },
    /** 集体评价时拼接专家姓名 */
    spliceExpertName () {
      this.expertTeamInfoForm.nameListStr = ''
      if (this.checkedNameList.length > 0) {
        for (let i = 0; i < this.checkedNameList.length; i++) {
          this.expertTeamInfoForm.nameListStr += this.checkedNameList[i].name
          if (i !== this.checkedNameList.length - 1) {
            this.expertTeamInfoForm.nameListStr += ','
          }
        }
      }
    },
    /** 批量提交 */
    dialogBatchSubmitBtn (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          let flag = false
          // this.checkedNameList
          if (this.workEvalForm.workEvalList.length > 0) {
            this.workEvalForm.workEvalList.forEach(item => {
              if (Number(item.evaluateResult) === 3 && item.evaluateRemark === '') {
                flag = true
                let info = '请输入关于' + item.labelName + '的差评原因'
                this.$message.error(info)
                return false
              }
            })
          }
          if (!flag) {
            let expertEvaluateList = []
            this.checkedNameList.forEach(checkedExpert => {
              let index = this.getExpertEvaluateInfoIndex(checkedExpert.idCard, checkedExpert.expertType, this.expertTeamInfoForm.noticeCode)
              let expertEvaluate = {
                objectId: (index !== -1) ? this.currentExpertEvaluateInfoList[index].objectId : '',
                relatedExpertType: this.cardList[this.currentChooseRowNum].isShow === 0 ? 3 : 2,
                relatedNoticeCode: this.expertTeamInfoForm.noticeCode,
                targetExpertCode: checkedExpert.idCard,
                evaluateInfoList: []
              }
              if (this.workEvalForm.workEvalList.length > 0) {
                this.workEvalForm.workEvalList.forEach(item => {
                  let obj = {
                    objectId: item.objectId,
                    labelName: item.labelName,
                    relatedLabelCode: item.relatedLabelCode,
                    evaluateResult: item.evaluateResult,
                    evaluateRemark: item.evaluateRemark,
                    evaluateType: item.relatedLabelCode ? 1 : 2,
                    targetExpertCode: checkedExpert.idCard,
                    relatedExpertEvaluateInfoCode: (index !== -1) ? this.currentExpertEvaluateInfoList[index].code : '',
                    isDelete: item.isDelete ? item.isDelete : ''
                  }
                  expertEvaluate.evaluateInfoList.push(obj)
                })
              }
              expertEvaluateList.push(expertEvaluate)
            })
            expertEvaluateInfo.saveBatch(expertEvaluateList).then((res) => {
              if (res.data.resCode === '0000') {
                this.checkedNameList.forEach(item => {
                  expertmanage.getOne(item.objectId).then((res) => {
                    this.updateExpertInfo(res.data.expertInfo) // 更新对应的专家为已评价
                  })
                })
                this.dialogTeamVisible = false
                this.dialogBatchVisible = false
              }
            })
          }
        } else {
          return false
        }
      })
    },
    /** 获取是否已存在专家评价信息 */
    getExpertEvaluateInfoIndex (idCard, expertType, relatedNoticeCode) {
      let index = -1
      if (this.currentExpertEvaluateInfoList.length > 0) {
        for (let i = 0; i < this.currentExpertEvaluateInfoList.length; i++) {
          if (this.currentExpertEvaluateInfoList[i].targetExpertCode === idCard && this.currentExpertEvaluateInfoList[i].relatedExpertType === expertType && this.currentExpertEvaluateInfoList[i].relatedNoticeCode === relatedNoticeCode) {
            index = i
            break
          }
        }
      }
      return index
    },
    /** 弹窗提交 */
    dialogSubmitBtn (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          let flag = false
          let expertEvaluate = {
            objectId: (this.currentExpertEvaluateInfo && this.currentExpertEvaluateInfo.objectId) ? this.currentExpertEvaluateInfo.objectId : '',
            relatedExpertType: this.cardList[this.currentChooseRowNum].isShow === 0 ? 3 : 2, // 2.招标备案资格预审会专家 3.招标备案评标专家
            relatedNoticeCode: this.expertInfoForm.noticeCode,
            targetExpertCode: this.expertInfoForm.idCard,
            evaluateInfoList: []
          }
          if (!this.checkIsHasEvaluateInfo(this.workEvalForm.workEvalList)) {
            this.workEvalForm.workEvalList.forEach(item => {
              if (Number(item.evaluateResult) === 3 && item.evaluateRemark === '') {
                flag = true
                let info = '请输入关于' + item.labelName + '的差评原因'
                this.$message.error(info)
                return false
              }
              let obj = {
                objectId: item.objectId,
                labelName: item.labelName,
                relatedLabelCode: item.relatedLabelCode,
                evaluateResult: item.evaluateResult,
                evaluateRemark: item.evaluateRemark,
                evaluateType: item.relatedLabelCode ? 1 : 2,
                targetExpertCode: this.expertInfoForm.idCard,
                relatedExpertEvaluateInfoCode: (this.currentExpertEvaluateInfo && this.currentExpertEvaluateInfo.code) ? this.currentExpertEvaluateInfo.code : '',
                isDelete: item.isDelete ? item.isDelete : ''
              }
              expertEvaluate.evaluateInfoList.push(obj)
            })
          } else {
            this.$message.error('请添加评价信息！')
            return false
          }
          if (!flag) {
            expertEvaluateInfo.save(expertEvaluate).then((res) => {
              if (res.data.resCode === '0000') {
                // this.updateExpertInfo(this.expertInfoForm) // 更新对应的专家为已评价
                expertmanage.getOne(this.expertInfoForm.objectId).then((res) => {
                  this.updateExpertInfo(res.data.expertInfo) // 更新对应的专家为已评价
                  this.dialogVisible = false
                })
              }
            })
          }
        } else {
          return false
        }
      })
    },
    /** 判断是否有评价信息 */
    checkIsHasEvaluateInfo (evaluateInfoList) {
      let flag = false
      let nums = 0
      if (evaluateInfoList.length > 0) {
        for (let i = 0; i < evaluateInfoList.length; i++) {
          if (evaluateInfoList[i].isDelete === 1) {
            nums++
          }
        }
        if (nums === evaluateInfoList.length) {
          flag = true
        }
      } else {
        flag = true
      }
      return flag
    },
    /** 更新专家为已评价状态 */
    updateExpertInfo (obj) {
      for (let i = 0; i < this.cardList[this.currentChooseRowNum].nameList.length; i++) {
        if (this.cardList[this.currentChooseRowNum].nameList[i].objectId === obj.objectId) {
          this.cardList[this.currentChooseRowNum].nameList[i].isEvaluate = obj.isEvaluate
          let arr = this.checkboxGroup[this.currentChooseRowNum]
          arr.push(obj.objectId)
          this.$set(this.checkboxGroup, this.currentChooseRowNum, arr)
          break
        }
      }
    },
    /** 评价值（好/一般/差）改变 */
    changRadioBtn (item) {
      let index = this.workEvalForm.workEvalList.indexOf(item)
      if (item.evaluateResult === 3) {
        item.showFlag = true
        item.evaluateRemark = ''
        this.$set(this.workEvalForm.workEvalList, index, item)
        // this.workEvalForm.workEvalList.splice(index, 1, item)
      } else {
        item.showFlag = false
        // this.workEvalForm.workEvalList.splice(index, 1, item)
        // this.workEvalForm.workEvalList[index].evaluateResult = item.evaluateResult
        this.$set(this.workEvalForm.workEvalList, index, item)
      }
    },
    /** 新增其他评价 */
    addOtherEval () {
      this.dialogAddVisible = true
    },
    /** 删除-新增项 */
    removeDomain (item) {
      if (this.dialogAddNameForm.labelName) {
        this.$refs['dialogAddNameForm'].resetFields()
        this.dialogAddNameForm.labelName = ''
      }
      let index = this.workEvalForm.workEvalList.indexOf(item)
      if (index !== -1) {
        // 删除操作
        if (item.objectId) {
          this.workEvalForm.workEvalList[index].isDelete = 1
          this.$set(this.workEvalForm.workEvalList, index, item)
          /* evaluateInfo.delete(item.objectId).then((res) => {
           if (res.data.resCode === '0000') {
           this.workEvalForm.workEvalList.splice(index, 1)
           }
           }) */
        } else {
          this.workEvalForm.workEvalList.splice(index, 1)
        }
      }
    },
    cancelAddNameDialogBtn () {
      this.dialogAddVisible = false
      this.$refs['dialogAddNameForm'].resetFields()
      this.dialogAddNameForm.labelName = ''
    },
    /** 新增-评价内容-确定 */
    addNameDialogBtn (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          if (!this.checkIsHasSameName(this.dialogAddNameForm.labelName)) {
            this.dialogAddVisible = false
            let obj = {
              objectId: null,
              labelName: this.dialogAddNameForm.labelName,
              relatedLabelCode: '',
              evaluateResult: '',
              evaluateRemark: '',
              labelType: 2,
              targetExpertCode: this.expertInfoForm.idCard,
              addFlag: '1',
              isDelete: 0
            }
            this.workEvalForm.workEvalList.push(obj)
            this.$refs['dialogAddNameForm'].resetFields()
            this.dialogAddNameForm.labelName = ''
          } else {
            this.$message.error('标签名重复！')
            this.$refs['dialogAddNameForm'].resetFields()
            this.dialogAddNameForm.labelName = ''
            return false
          }
        } else {
          return false
        }
      })
    },
    /** 判断是否有相同标签的存在 */
    checkIsHasSameName (labelName) {
      let flag = false
      if (this.workEvalForm.workEvalList.length > 0) {
        for (let i = 0; i < this.workEvalForm.workEvalList.length; i++) {
          if (this.workEvalForm.workEvalList[i].labelName === labelName && this.workEvalForm.workEvalList[i].isDelete === 0) {
            flag = true
            break
          }
        }
      }
      return flag
    },
    changeGroup () {
      this.setHighlight()
    },
    /** 获取指定专家对应的专家评价信息表信息 */
    getExpertEvaluateInfo (idCard, expertType, noticeCode) {
      this.currentExpertEvaluateInfo = {}
      expertEvaluateInfo.getOne(idCard, expertType, noticeCode).then((res) => {
        this.currentExpertEvaluateInfo = res.data.expertEvaluateInfo
      })
    },
    /** 点击人名评价 */
    checkedNameChange (obj, noticeCode, index) {
      // 获取当前操作的卡片
      this.currentChooseRowNum = index
      // 得到当前公告是否有专家评价信息表的存在
      if (obj.isEvaluate === 1) {
        this.getExpertEvaluateInfo(obj.idCard, obj.expertType, noticeCode)
      }
      expertmanage.getOne(obj.objectId).then((res) => {
        if (res.data.expertInfo) {
          this.getEvaluateLabelList(res.data.expertInfo.idCard, res.data.expertInfo.expertType, noticeCode)
          this.expertInfoForm = res.data.expertInfo
          this.expertInfoForm.noticeCode = noticeCode // 用于新增专家评价表时进行关联操作
          if (this.expertInfoForm) {
            if (this.expertInfoForm.sectionNameList) {
              this.expertInfoForm.sectionNameListStr = ''
              for (let i = 0; i < this.expertInfoForm.sectionNameList.length; i++) {
                this.expertInfoForm.sectionNameListStr += this.expertInfoForm.sectionNameList[i]
                if (i !== this.expertInfoForm.sectionNameList.length - 1) {
                  this.expertInfoForm.sectionNameListStr += ','
                }
              }
            }
            let expertClassify = []
            let expertClassifyStr = ''
            if (this.expertInfoForm.expertClassifyList) {
              if (this.expertInfoForm.expertClassifyList.length > 0) {
                let expertArr = this.expertInfoForm.expertClassifyList
                if (expertArr) {
                  expertClassify = this.transLabel(expertArr)
                  for (let i = 0; i < expertClassify.length; i++) {
                    if (i === expertClassify.length - 1) {
                      expertClassifyStr += expertClassify[i]
                    } else {
                      expertClassifyStr += expertClassify[i] + ' ， '
                    }
                  }
                }
                this.expertInfoForm.expertClassify = expertClassifyStr
              }
              this.dialogVisible = true
            } else {
              return false
            }
          } else {
            return false
          }
        } else {
          return false
        }
      })
    },
    // 转成label值
    transLabel (expertArr) {
      let tempArr = []
      let str = ''
      for (let j = 0; j < expertArr.length; j++) {
        if (expertArr[j].firstClassify) {
          let first = this.getLabelName(expertclassify, expertArr[j].firstClassify)
          str += first.node.label + '/'
        }
        if (expertArr[j].secondClassify) {
          let second = this.getLabelName(expertclassify, expertArr[j].secondClassify)
          str += second.node.label + '/'
        }
        if (expertArr[j].thirdClassify) {
          let third = this.getLabelName(expertclassify, expertArr[j].thirdClassify)
          str += third.node.label + '/'
        }
        if (expertArr[j].fourClassify) {
          let fourth = this.getLabelName(expertclassify, expertArr[j].fourClassify)
          str += fourth.node.label
        }
        tempArr.push(str)
        str = ''
      }
      return tempArr
    },
    /*
     * treeData [原始数据集]
     * key 传入的key值
     */
    getLabelName (treeData, key) {
      let parentNode = null
      let node = null
      function getTreeDeepArr (treeData, key) {
        for (let i = 0; i < treeData.length; i++) {
          // 1.如果没有子节点 beark
          if (node) {
            break
          }
          // 定义需要操作的数据
          let obj = treeData[i]
          // 没有节点就下一个
          if (!obj || !obj.value) {
            continue
          }
          // 2.有节点就继续找，一直递归
          if (obj.value === key) {
            node = obj
            break
          } else {
            // 3.如果有子节点就继续找
            if (obj.children) {
              // 4.递归前，记录当前节点，作为parentNode
              parentNode = obj
              // 递归
              getTreeDeepArr(obj.children, key)
            } else {
              // 跳出当前递归
              continue
            }
          }
        }
        // 如果没有找到父节点，置为null
        if (!node) {
          parentNode = null
        }
        // 返回结果
        return {
          node: node,
          parentNode: parentNode
        }
      }
      return getTreeDeepArr(treeData, key)
    }
  },
  computed: {
    nowData: function () {
      var arr = []
      // this.templateData是父组件传递的对象
      arr = JSON.parse(JSON.stringify(this.nameList))
      return arr
    }
  },
  watch: {},
  // 实例创建完成
  mounted () {
  }
}
</script>
<style lang="less">
  #expert-check-page {
    /** 项目基本信息-工作考评css */
    .pro-info, .work-eval {
      margin: 0 20px;
      .pro-title {
        text-align: left;
        padding-left: 20px;
        font-weight: 800;
        height: 40px;
        line-height: 40px;
        background: #F7F6FB;
      }
      .pro-tit-btn {
        float: right;
        margin-top: 5px;
        margin-right: 20px;
      }
      .pro-form {
        padding-top: 15px;
        background-color: #fff;
        .el-form-item__content {
          text-align: left;
        }
      }
    }
    /** 工作考评css */
    .work-eval {
      margin-top: 20px;
      .eval-cont {
        background: #fff;
        padding: 10px;
        margin-bottom: 130px;
        .el-card__body {
          padding: 10px 0;
        }
        .card-list {
          margin: 15px 0;
          .row-border {
            border-bottom: 1px solid #DDDDDD;
          }
        }
        /* 多选样式 */
        .check-box {
          .hj-span {
            display: inline-block;
            left: 2%;
            position: relative;
            float: left;
            line-height: 35px;
            width: 10%;
            text-align: left;
          }
          .el-checkbox-group {
            margin-bottom: 15px;
            text-align: left;
            width: 90%;
            float: right;
          }
          .el-checkbox-button {
            margin: 10px 20px !important;
          }
          .el-checkbox-button.is-checked {
            border-left-color: #409EFF;
          }
          .el-checkbox-button.is-checked .el-checkbox-button__inner{
            border-left-color: #409EFF;
          }
          .el-checkbox-button.is-focus .el-checkbox-button__inner {
            border-left-color: #409EFF;
          }
          .el-checkbox-button .el-checkbox-button__inner {
            border-left: 1px solid #dcdfe6;
            border-radius: inherit;
          }
        }
        .card-bottom {
          padding: 8px 15px;
          p {
            text-align: left;
            margin-top: 10px;
            margin-bottom: 0;
          }
        }
        .button {
          padding: 0;
          margin-top: -45px;
          float: right;
        }
        .clearfix:before,
        .clearfix:after {
          display: table;
          content: "";
        }
        .clearfix:after {
          clear: both
        }
        .radio-first, .radio-second, .radio-third {
          margin-right: 40px;
          .el-radio-button__inner {
            width: 65px;
            padding: 8px;
            border-left: none;
            border-radius: 25px;
            border: 1px solid #dcdfe6;
            box-shadow: none;
          }
        }
        .radio-first
        .el-radio-button__inner {
          background-color: #ecf5ff;
        }
        .radio-second
        .el-radio-button__inner {
          background-color: #f0f9eb;
        }
        .radio-third
        .el-radio-button__inner {
          background-color: #fdf6ec;
        }
        .radio-first.is-active
        .el-radio-button__inner {
          background-color: #409eff;
        }
        .radio-second.is-active
        .el-radio-button__inner {
          background-color: #67c23a;
        }
        .radio-third.is-active
        .el-radio-button__inner {
          background-color: #e6a23c;
        }
      }
      .eval-warp {
        margin-bottom: 0;
      }
    }
    .dialog-box {
      .pl-check {
        .el-checkbox-group {
          text-align: left;
          .el-checkbox {
            width: 20%;
            margin-left: 0;
          }
        }
      }
    }
  }
</style>
