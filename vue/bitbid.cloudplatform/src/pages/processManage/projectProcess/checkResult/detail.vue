<template>
  <div class="cloudcontent">
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <template v-if="isApproved === '1' || isApproved === '2'">
        <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
          <el-breadcrumb-item :to="{ path: '/dailyManage/onlineapprove' }">在线审批</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/dailyManage/onlineapprove/initiate' }" v-if="type === 'initiate'">我发起的</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/dailyManage/onlineapprove/need' }" v-if="type === 'need'">待我审批</el-breadcrumb-item>
          <el-breadcrumb-item class="active_bread">{{isApproved === '1' ? '审批 ': '审批详情'}}</el-breadcrumb-item>
        </el-breadcrumb>
      </template>
      <template v-else>
        <el-breadcrumb-item :to="{ path: '/processManage/projectProcess' }">项目进度管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/processManage/projectProcess/project' }">项目管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/processManage/projectProcess/project/process' }">流程管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/processManage/projectProcess/project/process', query: {type: 'pre_file'} }">资格审查结果</el-breadcrumb-item>
        <el-breadcrumb-item class="active_bread">查看专家信息</el-breadcrumb-item>
      </template>
    </el-breadcrumb>
    <div class="main">
      <el-form :model="updateForm" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目编号：">
              <span>{{bidSection.tenderProjectCode}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目名称：">
              <span>{{bidSection.tenderProjectName}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="标段编号：">
              <span>{{bidSection.bidSectionCode}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标段名称：">
              <span>{{bidSection.bidSectionName}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="专家姓名："  prop="expertName">
              <span>{{updateForm.expertName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="身份证号码："  prop="idCard">
              <span>{{updateForm.idCard}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="专业类别："  prop="expertClassify">
              <span>{{updateForm.expertClassify}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="手机："  prop="mobilePhone">
              <span>{{updateForm.mobilePhone}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电子邮箱："  prop="email">
              <span>{{updateForm.email}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="标段信息：">
              <el-table
                :data="bidSectionList"
                border
                style="width: 100%" header-cell-class-name="tableheader"
              >
                <el-table-column
                  prop="bidSectionCode"
                  label="标段编号"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="bidSectionName"
                  label="标段名称"
                  show-overflow-tooltip>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
        </el-row>
        <approve @approveForm="approveForm" ref="approveForm" v-if="isApproved === '1' && type === 'need'"></approve>
        <el-row v-if="type === 'initiate'">
          <el-col :span="24">
            <el-form-item label="当前审批人："  prop="tenderProjectCode">
              <span>{{updateForm.tenderProjectCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="isApproved === '1' || isApproved === '2'">
          <el-col :span="24">
            <el-form-item label="审核记录："  prop="tenderProjectCode">
              <el-table
                :data="recordList"
                border
                style="width: 100%" header-cell-class-name="tableheader">
                <el-table-column
                  type="index"
                  label="序号">
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="审核结果"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="status"
                  label="审核人"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="status"
                  label="审核时间"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="status"
                  label="审核意见"
                  show-overflow-tooltip>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="cancleApprove" v-if="type === 'initiate'">撤回</el-button>
          <template v-else>
            <el-button type="primary" @click="submitApprove" v-if="isApproved === '1'">提交</el-button>
            <el-button class="cancal" @click="$router.go(-1)">返回</el-button>
          </template>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import {expert, bidSection} from '@/api/cloudplatform'
import * as expertClassify from '@/assets/js/expertClassify'
import approve from '@/components/approve.vue'
export default {
  components: {
    approve
  },
  name: '',
  data () {
    return {
      // 是否显示审批（0：详情 1：审批 2：审批详情）
      isApproved: this.$route.query.isApproved || 0,
      // initiate: 我发起的 need:待审批的
      type: this.$route.query.type || '',
      recordList: [],
      updateForm: {},
      bidSectionList: [],
      bidSection: {}
    }
  },
  methods: {
    getExpert () {
      expert.getById(this.$route.params.objectId).then(res => {
        this.updateForm = res.data.expert
        this.bidSectionList = this.updateForm.bidSectionList
        if (this.updateForm) {
          let expertClassify = []
          let expertClassifyStr = ''
          if (this.updateForm.expertClassifyList) {
            if (this.updateForm.expertClassifyList.length > 0) {
              let expertArr = this.updateForm.expertClassifyList
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
              this.updateForm.expertClassify = expertClassifyStr
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
          let first = this.getLabelName(expertClassify.expertClassify, expertArr[j].firstClassify)
          str += first.node.label + '/'
        }
        if (expertArr[j].secondClassify) {
          let second = this.getLabelName(expertClassify.expertClassify, expertArr[j].secondClassify)
          str += second.node.label + '/'
        }
        if (expertArr[j].thirdClassify) {
          let third = this.getLabelName(expertClassify.expertClassify, expertArr[j].thirdClassify)
          str += third.node.label + '/'
        }
        if (expertArr[j].fourClassify) {
          let fourth = this.getLabelName(expertClassify.expertClassify, expertArr[j].fourClassify)
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
          if (node && key) {
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
    // 查询当前标段信息
    getBidSection () {
      bidSection.getOne(this.$route.query.sectionSystemCode).then(res => {
        this.bidSection = res.data.bidSection
      })
    },
    approveForm (formV) {
      this.updateForm = formV
    },
    submitApprove () {
      // 必填验证
      this.$refs.approveForm.handleValidate()
      if (this.updateForm.auditStatus === 0 && !this.updateForm.opinion.trim()) {
        this.$message({
          message: '请填写审批意见',
          type: 'warning'
        })
        return false
      }
      // 提交数据
    },
    // 撤回审批
    cancleApprove () {
    }
  },
  mounted () {
    this.getExpert()
    this.getBidSection()
  }
}
</script>

<style scoped>
</style>
