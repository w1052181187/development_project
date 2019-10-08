<template>
  <div class="cloudcontent">
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/dailyManage/onlineapprove' }">在线审批</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/dailyManage/onlineapprove/need' }">待我审批</el-breadcrumb-item>
      <el-breadcrumb-item class="active_bread">审批</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="main">
      <el-form :model="updateForm" :rules="rules" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目编号："  prop="tenderProjectCode">
              <span>{{updateForm.tenderProjectCode}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目名称："  prop="tenderProjectCode">
              <span>{{updateForm.tenderProjectCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="业务来源："  prop="tenderProjectCode">
              <span v-if="updateForm.tenderProjectCode === 1">招标</span>
              <span v-if="updateForm.tenderProjectCode === 2">询价</span>
              <span v-if="updateForm.tenderProjectCode === 3">竞争性谈判</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审批类型："  prop="tenderProjectCode">
              <span v-if="updateForm.tenderProjectCode === 1">招标文件</span>
              <span v-if="updateForm.tenderProjectCode === 2">招标公告</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="申请人："  prop="tenderProjectCode">
              <span>{{updateForm.tenderProjectCode}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="申请时间："  prop="tenderProjectCode">
              <span>{{updateForm.tenderProjectCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="审批状态："  prop="tenderProjectCode">
              <span v-if="updateForm.tenderProjectCode === 1">未审批</span>
              <span v-if="updateForm.tenderProjectCode === 2">审批中</span>
              <span v-if="updateForm.tenderProjectCode === 3">已审批</span>
              <span v-if="updateForm.tenderProjectCode === 4">已撤回</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="申请内容："  prop="tenderProjectCode">
              <el-row>
                <el-form-item label="招标文件名称："  prop="tenderProjectCode">
                  <span>招标文件名称</span>
                </el-form-item>
              </el-row>
              <el-row>
                <el-form-item label="是否需要审批："  prop="tenderProjectCode">
                  <span v-if="updateForm.tenderProjectCode === 0">否</span>
                  <span v-if="updateForm.tenderProjectCode === 1">是</span>
                </el-form-item>
              </el-row>
              <el-row>
                <el-form-item label="备注："  prop="tenderProjectCode">
                  <editor ref="ueditor" class="ueditor" :editread="editread" :content="content"></editor>
                </el-form-item>
              </el-row>
              <el-row>
                <el-form-item label="投标资格："  prop="tenderProjectCode">
                  <editor ref="ueditor" class="ueditor" :editread="editread" :content="content"></editor>
                </el-form-item>
              </el-row>
              <el-row>
                <el-form-item label="评标办法："  prop="tenderProjectCode">
                  <editor ref="ueditor" class="ueditor" :editread="editread" :content="content"></editor>
                </el-form-item>
              </el-row>
              <el-row>
                <el-form-item label="关联标段："  prop="tenderProjectCode">
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
        <approve @approveForm="approveForm" ref="approveForm"></approve>
        <el-row>
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
import approve from '@/components/approve.vue'
export default {
  components: {
    editor,
    approve
  },
  name: '',
  data () {
    return {
      updateForm: {},
      rules: {},
      // 富文本
      editread: false,
      content: '',
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
      radio: '',
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
    approveForm (formV) {
      this.updateForm = formV
    }
  },
  mounted () {
  }
}
</script>

<style scoped>
</style>
