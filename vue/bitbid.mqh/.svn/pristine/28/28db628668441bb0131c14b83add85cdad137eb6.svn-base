<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="expertdeta-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>资源管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/resource/expertmange' }">评标专家管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/resource/expertmange/expertdetail' }">查看详情</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="goBack()" class="go-back-btn">
        返回
      </el-button>
    </div>
    <!-- 主体内容区域start -->
    <div class="expertdeta-cont">
      <el-tabs v-model="activeName" type="card" @tab-click="handleCardClick">
        <!-- 专家基本信息start -->
        <el-tab-pane label="专家基本信息" name="first" class="work-expert-pane">
          <el-form :model="expertInfoForm" label-width="100px">
            <el-form-item label="姓名:">
              <span>{{expertInfoForm.name}}</span>
            </el-form-item>
            <el-form-item label="身份证号:">
              <span>{{expertInfoForm.idCard}}</span>
            </el-form-item>
            <el-form-item label="性别:">
              <span>{{expertInfoForm.sex | filterSex}}</span>
            </el-form-item>
            <el-form-item label="单位:">
              <span>{{expertInfoForm.enterpriseName}}</span>
            </el-form-item>
            <el-form-item label="地区:">
              <span>{{expertInfoForm.cityId | filterCity}}</span>
            </el-form-item>
            <el-form-item label="专家类别:">
              <span>{{expertInfoForm.expertSources | filterSources}}</span>
            </el-form-item>
            <el-form-item label="状态:">
              <span>{{expertInfoForm.expertStatus | filterStatus}}</span>
            </el-form-item>
            <el-form-item label="专业:">
              <span>{{expertInfoForm.expertClassify}}</span>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <!-- 专家基本信息end -->

        <!-- 工作及评价start -->
        <el-tab-pane label="工作及评价" name="second" class="work-evaluate-pane">
          <div class="work-title"><span>共参与项目{{this.workTableData.length}}个</span></div>
          <div class="evaluate-table">
            <el-table
              :data="workTableData"
              border
              style="width: 100%">
              <el-table-column
                align="center"
                prop="tenderProjectName"
                label="项目名称">
              </el-table-column>
              <el-table-column
                align="center"
                prop="codeUser"
                label="项目编号">
              </el-table-column>
              <el-table-column
                align="center"
                prop="drawLotsTime"
                label="评标日期"
                :formatter="filterDate">
              </el-table-column>
              <el-table-column
                align="center"
                label="操作"
                width="80">
                <template slot-scope="scope">
                  <el-tooltip content="查看评价" placement="top">
                    <el-button @click="handleTableClick(scope.row,'look')" type="primary" size="small" icon="el-icon-tickets"></el-button>
                  </el-tooltip>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div class="seg-line"></div>
          <!-- 评价区域start -->
          <div class="evaluate-box">
            <div class="evaluate-tit"><h4>评价</h4></div>
            <div class="evaluate-cont">
              <el-form :model="allEvaluateForm" label-width="140px" class="demo-dynamic" :inline="true">
              <el-row>
                <!--<el-col :span="2">
                  <div class="avator-box">
                    <img src="./image/expert.png" alt="">
                  </div>
                </el-col>-->
                <el-col :span="22">
                  <p>当前专家共{{allEvaluateForm.evaluateNums}}次获评，好评度为{{allEvaluateForm.favourableValue}}%</p>
                  <div class="evaluate-star">
                    <el-row>
                      <el-col :span="1"><span>好评度</span></el-col>
                      <el-col :span="22">
                        <el-rate
                          v-model="allEvaluateForm.rateValue"
                          :allow-half="true"
                          disabled
                          :colors="['#46C47B', '#46C47B', '#46C47B']">
                        </el-rate>
                      </el-col>
                    </el-row>
                  </div>
                  <div class="praise-box">
                    <span>好评:</span>
                    <el-form-item v-for="(domain, index) in allEvaluateForm.favourableCommentList" :key="index">
                      <el-button type="primary" round>{{domain.labelName}}&nbsp;{{domain.nums}}</el-button>
                    </el-form-item>
                  </div>
                  <div class="praise-box">
                    <span>中评:</span>
                    <el-form-item v-for="(domain, index) in allEvaluateForm.middleCommentList" :key="index">
                      <el-button type="success" round>{{domain.labelName}}&nbsp;{{domain.nums}}</el-button>
                    </el-form-item>
                  </div>
                  <div class="praise-box">
                    <span>差评:</span>
                    <el-form-item v-for="(domain, index) in allEvaluateForm.badCommentList" :key="index">
                      <el-button type="warning" round>{{domain.labelName}}&nbsp;{{domain.nums}}</el-button>
                      <span>{{domain.evaluateRemark}}</span>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              </el-form>
            </div>
          </div>
          <!-- 评价区域end -->
        </el-tab-pane>
        <!-- 工作及评价end -->
      </el-tabs>
    </div>
    <!-- 主体内容区域end -->

    <!-- 弹窗区域start -->
    <div class="dialog-box">
      <el-dialog
        title="评价详情"
        :visible.sync="dialogVisible"
        width="40%">
        <!-- 项目基本信息 -->
        <span class="title-info">项目基本信息</span>
        <el-form :model="dialogInfoForm" label-width="100px">
          <el-row>
            <el-col :span="12">
              <el-form-item label="招标项目名称:">
                <span>{{dialogInfoForm.tenderProjectName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="招标项目编号:">
                <span>{{dialogInfoForm.codeUser}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="项目单位:">
                <span>{{dialogInfoForm.projectEnterpriseName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="需求方:">
                <span>{{dialogInfoForm.requiresEnterpriseName}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="选聘备案时间:">
                <span>{{dialogInfoForm.approveTime | filterEvaluateDate}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="中签单位:">
                <span>{{dialogInfoForm.agencyEnterpriseName}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="公告时间:">
                <span>{{dialogInfoForm.firstNoticeApproveTime | filterEvaluateDate}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="开标时间:">
                <span>{{dialogInfoForm.firstActualBidOpenTime | filterEvaluateDate}}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <!-- 工作评价 -->
        <span class="title-info">工作评价</span>
        <el-form :model="evaluateForm" label-width="140px" class="demo-dynamic" :inline="true">
          <div class="eval-cont">
            <div class="praise-box" style="padding-bottom: 15px;">
              <span class="span-left">评价时间:</span>
              <span>{{evaluateForm.evaluateDate | filterEvaluateDate}}</span>
            </div>
            <div class="praise-box">
              <span class="span-left">好评:</span>
              <el-form-item v-for="(domain, index) in evaluateForm.favourableCommentList" :key="index">
                <el-button type="primary" round>{{domain.labelName}}</el-button>
              </el-form-item>
            </div>
            <div class="praise-box">
              <span class="span-left">中评:</span>
              <el-form-item v-for="(domain, index) in evaluateForm.middleCommentList" :key="index">
                <el-button type="success" round>{{domain.labelName}}</el-button>
              </el-form-item>
            </div>
            <div class="praise-box">
              <span class="span-left">差评:</span>
              <el-form-item v-for="(domain, index) in evaluateForm.badCommentList" :key="index">
                <el-button type="warning" round>{{domain.labelName}}</el-button>
                <span>{{domain.evaluateRemark}}</span>
              </el-form-item>
            </div>
          </div>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <!-- 弹窗区域end -->
  </div>
</template>
<script>
import {expertmanage, expertEvaluateInfo, tenderproject} from 'api/index'
import {expertclassify} from 'api/resource/expertmanage/expertclassify'
import {formatDate} from 'common/js/date'
export default {
  data () {
    return {
      dialogInfoForm: {},
      dialogVisible: false,
      activeName: 'first',
      expertInfoForm: {},
      workTableData: [], // 工作及评价-表格
      evaluateForm: {
        evaluateDate: '',
        favourableCommentList: [],
        middleCommentList: [],
        badCommentList: []
      }, // 评价内容
      allEvaluateForm: {
        evaluateNums: 0,
        favourableValue: 0,
        rateValue: 0, // 好评
        favourableCommentList: [],
        middleCommentList: [],
        badCommentList: []
      }
    }
  },
  computed: {},
  created () {},
  /*
  * 方法集
  */
  methods: {
    // tabs切换
    handleCardClick (data) {
      this.pageSize = 10
      this.pageNo = 0
      this.currentPage = 1
      this.activeName = data.name
      // 页面切换后重新获取数据
      if (this.activeName === 'first') {
        this.getExpertInfo()
      } else {
        this.getEvaluateInfo()
      }
    },
    /** 获取评价信息 */
    getEvaluateInfo () {
      expertEvaluateInfo.getListInResource(this.$route.query.objectId).then((res) => {
        this.workTableData = res.data.tenderProjectList
        // 获取评价信息
        if (res.data.evaluateInfoList.length > 0) {
          this.getAllEvaluateFormInfo(res.data.evaluateInfoList)
        }
      })
    },
    getAllEvaluateFormInfo (evaluateInfoList) {
      this.allEvaluateForm.favourableCommentList = []
      this.allEvaluateForm.middleCommentList = []
      this.allEvaluateForm.badCommentList = []
      this.classifyEvaluateFormInfo(evaluateInfoList)
      let total = this.calSum(this.allEvaluateForm.favourableCommentList) + this.calSum(this.allEvaluateForm.middleCommentList) + this.calSum(this.allEvaluateForm.badCommentList)
      let favourableCommentNums = this.calSum(this.allEvaluateForm.favourableCommentList)
      this.allEvaluateForm.evaluateNums = Number(total)
      this.allEvaluateForm.favourableValue = ((Number(favourableCommentNums) / Number(total)) * 100).toFixed(2)
      this.allEvaluateForm.rateValue = Number(this.allEvaluateForm.favourableValue) / 20
    },
    /** 得到标签中的总次数 */
    calSum (arr) {
      let nums = 0
      if (arr.length > 0) {
        arr.forEach((item) => {
          nums += item.nums
        })
      }
      return nums
    },
    classifyEvaluateFormInfo (evaluateInfoList) {
      if (evaluateInfoList && evaluateInfoList.length > 0) {
        evaluateInfoList.forEach(item => {
          if (Number(item.evaluateResult) === 1) { // 好评
            if (this.allEvaluateForm.favourableCommentList.length === 0) {
              item.nums = 1
              this.allEvaluateForm.favourableCommentList.push(item)
            } else {
              let flag = this.compareResult(item, this.allEvaluateForm.favourableCommentList)
              if (!flag) {
                item.nums = 1
                this.allEvaluateForm.favourableCommentList.push(item)
              }
            }
          } else if (Number(item.evaluateResult) === 2) { // 中评
            if (this.allEvaluateForm.middleCommentList.length === 0) {
              item.nums = 1
              this.allEvaluateForm.middleCommentList.push(item)
            } else {
              let flag = this.compareResult(item, this.allEvaluateForm.middleCommentList)
              if (!flag) {
                item.nums = 1
                this.allEvaluateForm.middleCommentList.push(item)
              }
            }
          } else {
            if (this.allEvaluateForm.badCommentList.length === 0) {
              item.nums = 1
              this.allEvaluateForm.badCommentList.push(item)
            } else {
              let flag = this.compareResult(item, this.allEvaluateForm.badCommentList)
              if (!flag) {
                item.nums = 1
                this.allEvaluateForm.badCommentList.push(item)
              }
            }
          }
        })
      }
    },
    compareResult (evaluateInfo, commentList) {
      let flag = false
      for (let i = 0; i < commentList.length; i++) {
        if (commentList[i].labelName === evaluateInfo.labelName) {
          commentList[i].nums++
          flag = true
          break
        }
      }
      return flag
    },
    /*
    * 表格操作
    */
    handleTableClick (row, type) {
      tenderproject.getOneInResource(row.code, 3, this.$route.query.objectId).then((res) => {
        this.dialogInfoForm = res.data.tenderProject
        if (res.data.notice) {
          this.dialogInfoForm.firstNoticeApproveTime = res.data.notice.approvePassTime
          this.dialogInfoForm.firstActualBidOpenTime = res.data.notice.firstActualBidOpenTime
        }
        if (res.data.projectEvaluate) {
          this.evaluateForm.evaluateDate = res.data.projectEvaluate.lastUpdateDate
        }
        if (res.data.evaluateInfoList) {
          this.classifyEvaluateResults(res.data.evaluateInfoList)
          this.dialogVisible = true
        }
      })
    },
    /** 对评价结果进行分类 */
    classifyEvaluateResults (evaluateInfoList) {
      this.evaluateForm.favourableCommentList = []
      this.evaluateForm.middleCommentList = []
      this.evaluateForm.badCommentList = []
      if (evaluateInfoList && evaluateInfoList.length > 0) {
        evaluateInfoList.forEach(item => {
          if (Number(item.evaluateResult) === 1) { // 好评
            this.evaluateForm.favourableCommentList.push(item)
          } else if (Number(item.evaluateResult) === 2) { // 中评
            this.evaluateForm.middleCommentList.push(item)
          } else {
            this.evaluateForm.badCommentList.push(item)
          }
        })
      }
    },
    /*
     * 返回
     */
    goBack () {
      this.$router.push({path: '/admin/resource/expertmange', query: {currentPage: this.$route.query.currentPage, pageNo: this.$route.query.pageNo, pageSize: this.$route.query.pageSize}})
    },
    getExpertInfo () {
      expertmanage.getOne(this.$route.query.objectId).then((res) => {
        if (res.data.expertInfo) {
          this.expertInfoForm = res.data.expertInfo
          if (this.expertInfoForm) {
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
    filterDate (row, column) {
      let date = ''
      if (row.drawLotsTime) {
        date = new Date(row.drawLotsTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm')
      } else {
        return null
      }
    }
  },
  /*
  * 实例创建完成
  */
  mounted () {
    this.getExpertInfo()
  },
  /*
  * 过滤
  */
  filters: {
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
    },
    filterType (row, column) {
      if (row === 4) {
        return '招标管理机构'
      } else if (row === 5) {
        return '造价咨询机构'
      } else if (row === 6) {
        return '审计评估机构'
      } else if (row === 7) {
        return '法律服务机构'
      }
    },
    filterEvaluateDate (value) {
      let date = ''
      if (value) {
        date = new Date(value)
        return formatDate(date, 'yyyy-MM-dd hh:mm')
      } else {
        return null
      }
    }
  }
}
</script>
<style lang="less">
#expertdeta-page {
  .expertdeta-cont {
    margin: 20px 20px 120px;
    background-color: #fff;
  }
  .work-expert-pane {
    .el-form-item__content {
      text-align: left;
    }
  }
  /*工作及评价*/
  .work-evaluate-pane {
    text-align: left;
    padding-bottom: 5px;
    .work-title {
      margin: 0 20px;
    }
    .evaluate-table {
      margin: 0 20px;
    }
    .seg-line {
      height: 20px;
      margin-top: 20px;
      background-color: #EAEDF2;
    }
    .evaluate-star {
      margin-bottom: 40px;
    }
    .evaluate-box {
      .evaluate-tit {
        background-color: #F7F6FB;
        height: 40px;
        h4 {
          margin: 0;
          padding-top: 10px;
          padding-left: 20px;
        }
      }
      .evaluate-cont {
        margin: 20px;
        .praise-box {
          line-height: 35px;
        }
      }
    }
  }
  /*弹窗样式*/
  .dialog-box {
    .title-info {
      width: 100%;
      display: inline-block;
      background-color: #F7F6FB;
      padding: 8px 8px;
      text-align: left;
      font-weight: 800;
    }
    .eval-cont {
      background: #F7F7F7;
      margin-top: 20px;
      padding: 20px;
      .praise-box {
        text-align: left;
        line-height: 35px;
        .span-left {
          display: inline-block;
          width: 80px;
        }
      }
    }
    .el-form-item__content {
      text-align: left;
    }
    .el-dialog__footer {
      text-align: center;
    }
  }
  .el-tabs__header.is-top {
    margin: 20px;
    padding-top: 20px;
  }
}
</style>
