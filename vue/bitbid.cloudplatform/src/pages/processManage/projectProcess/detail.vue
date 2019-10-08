<template>
  <div class="cloudcontent" id="cloud_projectProcess">
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/processManage/projectProcess' }">项目进度管理</el-breadcrumb-item>
      <el-breadcrumb-item class="active_bread">查看</el-breadcrumb-item>
    </el-breadcrumb>
    <!--面包屑-->
    <div class="main">
      <el-form :model="updateForm" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目名称：">
              <span>{{updateForm.tenderProjectName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目编号：">
              <span>{{updateForm.tenderProjectCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目类型：">
              <span>{{updateForm.tenderProjectType | filterTenderProjectType}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目预算（元）：">
              <span>{{updateForm.tenderProjectBudget}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="资金来源：">
              <span><p v-for="(item,index) in updateForm.fundSourceList" :key="index" class="dresult-span">
                <span v-if="Number(item.sourceOfFunds) === 1">
                  {{item.sourceOfFunds | filterSourceOfFunds}}:{{item.value}}%
                  (国有资金控股：{{item.stateOwnedCapitalHolding | filterState}}；
                  国有资金占主导地位：{{item.stateOwnedFundsPredominate| filterState}})
                </span>
                <span v-if="Number(item.sourceOfFunds) !== 1">
                  {{item.sourceOfFunds | filterSourceOfFunds}}:{{item.value}}%
                </span>
              </p></span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="批准文号：">
              <span>{{updateForm.approvalNumber}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="招标方式：">
              <span>{{updateForm.tenderMode | filterTenderMode}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否资格预审：">
              <span>{{updateForm.isPreBid | filterIsPreBid}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否允许联合体投标：">
              <span>{{updateForm.syndicatedFlag | filterSyndicatedFlag}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="招标人：">
              <span>{{updateForm.tendererName}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="监督部门名称：">
              <span>{{updateForm.supervisionDepName}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24" class="ueditor_box">
            <el-form-item label="招标内容与范围及招标方案说明：">
              <editor ref="ueditor" class="ueditor" :editread="editread" :content="content"></editor>
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
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="handleClickEvent">返回</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/ueditor/ueditor.vue'
import {tenderProject} from '@/api/cloudplatform/index'
import {downloadFile} from '@/assets/js/common'
export default {
  components: {
    editor
  },
  data () {
    return {
      updateForm: {
        fileInformations: [
          {
            fileName: 'a',
            relativePath: 'path'
          },
          {
            fileName: 'b',
            relativePath: 'path'
          }
        ]
      },
      // 项目类型
      options: [{
        value: '1',
        label: '工程'
      }, {
        value: '2',
        label: '货物'
      }, {
        value: '3',
        label: '服务'
      }],
      value: '',
      // 招标方式
      typeOptions: [{
        value: '1',
        label: '公开招标'
      }, {
        value: '2',
        label: '邀请招标'
      }, {
        value: '3',
        label: '竞争性谈判'
      }, {
        value: '4',
        label: '单一来源采购'
      }, {
        value: '5',
        label: '询价采购'
      }, {
        value: '6',
        label: '竞争性磋商'
      }],
      typeValue: '',
      // 是否资格预审
      isCheckOptions: [{
        value: '1',
        label: '是'
      }, {
        value: '2',
        label: '否'
      }],
      isCheckValue: '',
      // 是否允许联合体投标
      isAllowOptions: [{
        value: '1',
        label: '是'
      }, {
        value: '2',
        label: '否'
      }],
      isAllowValue: '',
      // 富文本
      editread: true,
      content: ''
    }
  },
  filters: {
    /** 项目类型 */
    filterTenderProjectType (value) {
      if (value === '1') {
        return '工程'
      } else if (value === '2') {
        return '货物'
      } else if (value === '3') {
        return '服务'
      } else if (value === '4') {
        return '其他'
      }
    },
    /** 招标方式 */
    filterTenderMode (value) {
      if (Number(value) === 1) {
        return '公开招标'
      } else if (Number(value) === 2) {
        return '邀请招标'
      } else if (Number(value) === 3) {
        return '竞争性谈判'
      } else if (Number(value) === 4) {
        return '单一来源采购'
      } else if (Number(value) === 5) {
        return '竞争性磋商'
      } else if (Number(value) === 9) {
        return '其他'
      }
    },
    /** 是否资格预审 */
    filterIsPreBid (value) {
      if (value === 0) {
        return '否'
      } else if (value === 1) {
        return '是'
      }
    },
    /** 是否允许联合体投标 */
    filterSyndicatedFlag (value) {
      if (value === '0') {
        return '否'
      } else if (value === '1') {
        return '是'
      }
    },
    /** 资金来源 */
    filterSourceOfFunds (value) {
      if (value === 1) {
        return '自有资金'
      } else if (value === 2) {
        return '专项资金'
      } else if (value === 3) {
        return '吸收资金'
      } else if (value === 4) {
        return '其他资金'
      }
    },
    /** 国有资金控股/国有资金占主导地位 */
    filterState (value) {
      if (value === 0) {
        return '否'
      } else if (value === 1) {
        return '是'
      }
    }
  },
  created () {
    this.getChooseTenderProjectInfo()
  },
  methods: {
    /** 获取当前招标项目信息 */
    getChooseTenderProjectInfo () {
      tenderProject.getBaseByCode(this.$route.query.code).then((res) => {
        if (res.data.tenderProject && res.data.fundSourceList) {
          this.updateForm = res.data.tenderProject
          if (res.data.fundSourceList) {
            this.updateForm.fundSourceList = res.data.fundSourceList
            this.updateForm.fundSourceList.forEach(item => {
              if (item.sourceOfFunds === 1) {
                this.stateOwnedCapitalHolding = item.stateOwnedCapitalHolding
                this.stateOwnedFundsPredominate = item.stateOwnedFundsPredominate
              }
            })
          }
          // 获取文本编辑器的内容
          this.$refs.ueditor.setContent(this.content = this.updateForm.tenderContent)
        }
      })
    },
    handleClickEvent () {
      this.$router.push({path: '/processManage/projectProcess'})
    },
    lookFile (file) {
      downloadFile(file.fileName, file.relativePath)
    }
  }
}
</script>
<style lang="less">
  #cloud_projectProcess {
    .el-select{
      width: 100%;
    }
  }
</style>
