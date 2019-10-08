<template>
  <div class="cloudcontent">
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/dailyManage/onlineapprove' }">在线审批</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/dailyManage/onlineapprove/initiate' }">我发起的</el-breadcrumb-item>
      <el-breadcrumb-item class="active_bread">审批详情</el-breadcrumb-item>
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
              <span>{{bidSection.tenderProjectCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="业务来源：">
              <span  v-if="updateForm.tenderMode === 1">公开招标</span>
              <span  v-if="updateForm.tenderMode === 2">邀请招标</span>
              <span  v-if="updateForm.tenderMode === 3">竞争性谈判</span>
              <span  v-if="updateForm.tenderMode === 4">单一来源采购</span>
              <span  v-if="updateForm.tenderMode === 5">询价采购</span>
              <span  v-if="updateForm.tenderMode === 6">竞争性磋商</span>
              <span  v-if="updateForm.tenderMode === 9">其他</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审批类型：">
              <span v-if="updateForm.flowStatus === 1">招标文件</span>
              <span v-if="updateForm.flowStatus === 2">招标公告</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="申请人：">
              <span>{{updateForm.tenderProjectCode}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="申请时间：">
              <span>{{updateForm.tenderProjectCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="审批状态："  prop="tenderProjectCode">
              <span v-if="updateForm.auditStatus === 0">待审批</span>
              <span v-if="updateForm.auditStatus === 1">通过</span>
              <span v-if="updateForm.auditStatus === 2">未通过</span>
              <span v-if="updateForm.auditStatus === 3">已撤回</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="申请内容：">
              <el-row>
                <el-form-item label="招标文件名称：">
                  <span>招标文件名称</span>
                </el-form-item>
              </el-row>
              <el-row>
                <el-form-item label="是否需要审批：">
                  <span v-if="updateForm.tenderProjectCode === 0">否</span>
                  <span v-if="updateForm.tenderProjectCode === 1">是</span>
                </el-form-item>
              </el-row>
              <el-row>
                <el-form-item label="备注：">
                  <editor ref="ueditor" class="ueditor" :editread="editread"></editor>
                </el-form-item>
              </el-row>
              <el-row>
                <el-form-item label="投标资格：">
                  <editor ref="ueditor" class="ueditor" :editread="editread"></editor>
                </el-form-item>
              </el-row>
              <el-row>
                <el-form-item label="评标办法：">
                  <editor ref="ueditor" class="ueditor" :editread="editread"></editor>
                </el-form-item>
              </el-row>
              <el-row>
                <el-form-item label="关联标段：">
                  <el-table
                    :data="noticeList"
                    border
                    style="width: 100%" header-cell-class-name="tableheader">
                    <el-table-column
                      prop="name"
                      label="标段编号"
                      show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column
                      prop="status"
                      label="标段名称"
                      show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column
                      prop="status"
                      label="操作"
                      width="60">
                      <template slot-scope="scope">
                        <el-button type="text" size="small"  @click="handleDetail(scope)">详情</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                </el-form-item>
              </el-row>
              <el-row>
                <el-form-item label="其他文件："  prop="tenderProjectCode">
                </el-form-item>
              </el-row>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="当前审批人："  prop="tenderProjectCode">
              <span>{{updateForm.tenderProjectCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="updateForm.auditStatus === 1 || updateForm.auditStatus === 2">
          <el-col :span="24">
            <el-form-item label="审核记录："  prop="tenderProjectCode">
              <el-table
                :data="noticeList"
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
          <el-button class="cancal">返回</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import editor from '@/components/ueditor/ueditor.vue'
import {approvalTask} from '@/api/cloudplatform'
export default {
  components: {
    editor
  },
  name: '',
  data () {
    return {
      updateForm: {},
      bidSection: {},
      // 富文本
      editread: false,
      noticeList: [
        {
          name: 1,
          status: 1
        },
        {
          name: 1,
          status: 0
        }
      ],
      sectionList: [],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 100, // 总条数
      pageSize: 10 // 每页展示条数
    }
  },
  methods: {
    handleDetail (scope) {
    },
    detail () {
      approvalTask.launchedByMeDetail(this.$route.params.objectId).then((res) => {
        this.updateForm = res.data.approvalTask
        this.bidSection = this.updateForm.tenderProject
      })
    }
  },
  mounted () {
    this.detail()
  }
}
</script>

<style scoped>
</style>
