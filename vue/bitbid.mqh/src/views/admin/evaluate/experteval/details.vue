<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="expert-query-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>考评系统</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/evaluate/expertquery' }">专家考评查询</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/evaluate/expertquery/details' }">详情</el-breadcrumb-item>
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
                  @change="checkedNameChange(o.objectId, item.noticeInfo.code, index)"
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
            <p>评标标段:
              <span v-for="(sectionName, index) in item.sectionNameList" :key="sectionName" v-if="index !== item.sectionNameList.length - 1">{{sectionName}}、</span>
              <span v-for="(sectionName, index) in item.sectionNameList" :key="sectionName" v-if="index === item.sectionNameList.length - 1">{{sectionName}}</span>
            </p>
          </div>
        </el-card>
      </div>
    </div>
    <!--工作考评end-->

    <!--弹窗区域start-->
    <div class="dialog-box">
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
                <el-form-item :label="domain.labelName+':'" v-for="(domain, index) in workEvalForm.workEvalList" :key="index">
                  <el-radio-group v-model="domain.evaluateResult" @change="changRadioBtn(domain)">
                    <el-radio-button :label="1" class="radio-first" v-if="domain.evaluateResult==1">好</el-radio-button>
                    <el-radio-button :label="2" class="radio-second" v-if="domain.evaluateResult==2">一般</el-radio-button>
                    <el-radio-button :label="3" class="radio-third" v-if="domain.evaluateResult==3">差</el-radio-button>
                  </el-radio-group>
                  <span v-if="domain.evaluateResult==3">{{domain.evaluateRemark}}</span>
                </el-form-item>
              </el-form>
            </div>
          </div>
        </div>
        <span slot="footer" class="dialog-footer">
        </span>
      </el-dialog>
    </div>
    <!--弹窗区域end-->
  </div>
</template>
<script>
import {tenderproject, expertEvaluateInfo, projectEvaluate, expertmanage} from 'api/index'
import {expertclassify} from 'api/resource/expertmanage/expertclassify'
// 实例
export default {
  data () {
    return {
      projectInfoForm: {},
      dialogVisible: false,
      expertInfoForm: {}, // 专家-基本信息
      workEvalForm: {
        workEvalList: []
      },
      cardList: [],
      checkboxGroup: []
    }
  },
  created () {
    /**
     * 不允许在已经创建的实例上动态添加新的根级响应式属性 (root-level reactive property)。
     * 然而它可以使用 Vue.set(object, key, value) 方法将响应属性添加到嵌套的对象上
     **/
    for (let index in this.cardList) {
      this.$set(this.checkboxGroup, index, [])
    }
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
    changeGroup () {
      this.setHighlight()
    },
    /** 高亮设置 */
    setHighlight () {
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
                  obj.nameList.push({objectId: expertInfo.objectId, code: expertInfo.code, name: expertInfo.name, idCard: expertInfo.idCard, isEvaluate: expertInfo.isEvaluate})
                  // this.nameList.push({objectId: expertInfo.objectId, code: expertInfo.code, name: expertInfo.name, idCard: expertInfo.idCard, isEvaluate: expertInfo.isEvaluate})
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
                    obj1.nameList.push({objectId: expertInfo.objectId, code: expertInfo.code, name: expertInfo.name, idCard: expertInfo.idCard, isEvaluate: expertInfo.isEvaluate})
                    // this.nameList.push({objectId: expertInfo.objectId, code: expertInfo.code, name: expertInfo.name, idCard: expertInfo.idCard, isEvaluate: expertInfo.isEvaluate})
                  } else { // 招标备案评标专家
                    obj2.nameList.push({objectId: expertInfo.objectId, code: expertInfo.code, name: expertInfo.name, idCard: expertInfo.idCard, isEvaluate: expertInfo.isEvaluate})
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
    /** 档案查询 | 招标备案 */
    handleBtnFun (type) {
      if (Object.is(type, 'files')) {
        this.$router.push({
          path: '/admin/files/filemanage/editfile',
          query: {
            type: false,
            operation: 'look',
            bidType: this.projectInfoForm.biddingType,
            code: this.projectInfoForm.archivesCode
          }
        })
      } else if (Object.is(type, 'bid')) {
        this.$router.push({
          path: '/admin/record/processrecord/flowchart',
          query: {tenderProjectCode: this.projectInfoForm.code}
        })
      }
    },
    /** 保存 */
    saveClick () {},
    /** 提交 */
    submitClick () {},
    /** 批量操作 */
    handleBatch () {},
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
            this.workEvalForm.workEvalList.push(item)
          })
        } else if (evaluateInfos.length > 0) {
          evaluateInfos.forEach(item => {
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
    /** 点击具体的专家 */
    checkedNameChange (expertId, noticeCode, index) {
      expertmanage.getOne(expertId).then((res) => {
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
    },
    /** 用于排除未被选择的专家信息 */
    getIndex () {
      let indexList = []
      let flag = false
      if (this.checkedNameList.length > 0) {
        for (let i = 0; i < this.checkedNameList.length; i++) {
          flag = false
          for (let j = 0; j < this.checkedName.length; j++) {
            if (this.checkedNameList[i].name === this.checkedName[j]) {
              flag = true
              break
            }
          }
          if (!flag) {
            indexList.push(i)
          }
        }
      }
      return indexList
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
    /** 弹窗提交 */
    dialogSubmitBtn () {},
    /** 评价值（好/一般/差）改变 */
    changRadioBtn (item) {
      let index = this.workEvalForm.workEvalList.indexOf(item)
      if (item.evaluateResult === 3) {
        item.showFlag = true
        item.evaluateRemark = ''
        this.workEvalForm.workEvalList.splice(index, 1, item)
      } else {
        item.showFlag = false
        this.workEvalForm.workEvalList.splice(index, 1, item)
      }
    },
    /** 新增其他评价 */
    addOtherEval () {
      this.dialogAddVisible = true
    },
    /** 删除-新增项 */
    removeDomain (item) {
      let index = this.workEvalForm.workEvalList.indexOf(item)
      if (index !== -1) {
        this.workEvalForm.workEvalList.splice(index, 1)
      }
    },
    /** 新增-评价内容-确定 */
    addNameDialogBtn () {
      this.dialogAddVisible = false
      let obj = {
        labelName: this.dialogAddNameForm.labelName,
        relatedLabelCode: '',
        evaluateResult: '',
        evaluateRemark: '',
        addFlag: '1'
      }
      this.workEvalForm.workEvalList.push(obj)
    }
  },
  computed: {},
  // 实例创建完成
  mounted () {
  }
}
</script>
<style lang="less">
  #expert-query-page {
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
        .card-list {
          margin: 15px 0;
          .row-border {
            border-bottom: 1px solid #DDDDDD;
          }
          .box-per {
            width: 16%;
            height: 60px;
            display: inline-block;
            margin: 0 10px 15px;
            border: 1px solid #ddd;
            cursor: pointer;
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
      .eval-cont {
        .el-form-item__content {
          text-align: left;
        }
      }
    }
  }
</style>
