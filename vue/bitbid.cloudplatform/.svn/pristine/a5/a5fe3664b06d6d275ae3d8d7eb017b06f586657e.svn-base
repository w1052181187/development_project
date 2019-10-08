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
        <el-breadcrumb-item :to="{ path: '/processManage/projectProcess/project/process', query: {type: 'invite'} }">招标邀请</el-breadcrumb-item>
        <el-breadcrumb-item class="active_bread">查看投标邀请书</el-breadcrumb-item>
      </template>
    </el-breadcrumb>
    <div class="main">
      <el-form :model="updateForm" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目编号：">
              <span>{{updateForm.tenderProjectCode}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目名称：">
              <span>{{updateForm.tenderProjectName}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="标段编号：">
              <span>{{updateForm.bidSectionCode}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标段名称：">
              <span>{{updateForm.bidSectionName}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="邀请公告名称：">
              <span>{{updateForm.title}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="递交投标文件方法：">
              <span>{{updateForm.bidDocReferMethod}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="公告URL：">
              <span>{{updateForm.bulletinUrl}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发布网站：">
              <span>{{updateForm.publishUrl}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="标段信息：">
              <el-table
                :data="bidSectionList"
                border
                style="width: 100%" header-cell-class-name="tableheader">
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
        <el-row>
          <el-col :span="24">
            <el-form-item label="供应商信息：">
              <el-table
                :data="updateForm.supplierList"
                border
                style="width: 100%" header-cell-class-name="tableheader">
                <el-table-column
                  prop="supplierName"
                  label="供应商名称"
                  show-overflow-tooltip>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24" class="ueditor_box">
            <el-form-item label="邀请书内容：">
              <editor ref="ueditor" class="ueditor" :editread="editread"></editor>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="附件：">
              <el-table
                :data="updateForm.fileInformationList"
                border
                style="width: 100%" header-cell-class-name="tableheader">
                <el-table-column
                  type="index"
                  label="序号"
                  width="60"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="fileName"
                  label="文件名称"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  label="操作" align="center" width="200">
                  <template slot-scope="scope">
                    <el-button type="text" size="small" @click="lookFile(scope.row)">查看</el-button>
                  </template>
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
import editor from '@/components/ueditor/ueditor.vue'
import {downloadFile} from '@/assets/js/common'
import {bulletinInfo} from '@/api/cloudplatform/index'
import approve from '@/components/approve.vue'
export default {
  components: {
    editor,
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
      editread: false,
      sectionList: []
    }
  },
  methods: {
    lookFile (file) {
      downloadFile(file.fileName, file.relativePath)
    },
    /** 获取当前公告信息 */
    getChooseBulletinInfoInfo () {
      bulletinInfo.getOne(this.$route.params.code).then((res) => {
        if (res.data.bulletinInfo) {
          this.updateForm = res.data.bulletinInfo
          // 获取文本编辑器的内容
          this.$refs.ueditor.setContent(this.updateForm.content)
          // 设置标段信息
          this.bidSectionList = res.data.chooseBidSectionList
        }
        this.updateForm.supplierList = res.data.supplierBaseList
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
    this.getChooseBulletinInfoInfo()
  }
}
</script>

<style scoped>
</style>
