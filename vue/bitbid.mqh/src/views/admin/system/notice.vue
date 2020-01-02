<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="notice-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system/notice' }"><span>公告通知</span></el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!--搜素区域start-->
    <div class="search-box">
      <el-form :model="searchForm" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="5" style="width:300px">
            <el-form-item label="消息内容:">
              <el-input type="text" v-model="searchForm.messageLike" placehoder="请输入消息内容" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="2" style="padding-left:30px;">
            <el-form-item>
              <el-button type="primary" @click="onSearch" icon="el-icon-search" style="margin-left:-70px;">搜索</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <!--搜索区域end-->
    <!-- 主体内容区域开始 -->
    <div class="maincont-box">
      <div class="notice-add">
        <el-button icon="el-icon-plus" type="warning" @click="handleAddClick">添 加</el-button>
      </div>
      <!-- 表格展示区域start -->
      <div class="notice-tab">
        <el-table
          border
          :data="tableData"
          :row-class-name="tableRowClassName"
          style="width: 100%"
          >
          <el-table-column
            type="index"
            label="序号"
            :index="indexMethod"
            align="center"
            width="55">
          </el-table-column>
          <el-table-column
            prop="targetName"
            label="接收对象"
            header-align="center">
          </el-table-column>
          <el-table-column
            prop="content"
            label="消息内容"
            header-align="center">
          </el-table-column>
          <el-table-column
            prop="fileInformationList"
            label="附件"
            header-align="center">
            <template slot-scope="scope">
              <el-button type="text" v-for="(item, index) in scope.row.fileInformationList" :key="index"
                @click="downloadFile(item.fileName,item.relativePath)"
                class="table-file-btn">{{item.fileName}}</el-button>
            </template>
          </el-table-column>
          <el-table-column
            prop="isPublished"
            label="发布状态"
            :formatter="filterIsPublished"
            align="center">
          </el-table-column>
          <el-table-column
            prop="publishedDate"
            label="发布时间"
            :formatter="filterDate"
            header-align="center">
          </el-table-column>
          <el-table-column
            label="操作"
            align="center">
            <template slot-scope="scope">
              <el-button
                v-if="scope.row.isPublished === 0"
                @click="handleTableClick(scope.row, 'edit')"
                type="text"
                size="small">
                发布
              </el-button>
              <el-button
                v-if="scope.row.isPublished === 0"
                @click="handleTableClick(scope.row, 'del')"
                type="text"
                size="small">
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--分页-->
        <div class="block-page">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-sizes="pageSizeList"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
          </el-pagination>
        </div>
      </div>
      <!-- 表格展示区域end -->
    </div>
    <!-- 主体内容区域结束 -->

    <!--弹窗区域start-->
    <div class="dialog-box">
      <el-dialog
        @close="handleCancelClick"
        :title="dialogTitle"
        :visible.sync="dialogVisible"
        width="40%">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="接收对象:" prop="target">
            <el-select v-model="ruleForm.target" placeholder="请选择接收对象" multiple style="width: 300px;">
              <el-option
                v-for="item in targetList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="消息内容:" prop="content">
            <el-input v-model="ruleForm.content" style="width: 300px;"></el-input>
          </el-form-item>
          <el-form-item label="附件:" prop="fileList" ref="fileList">
            <el-upload
              class="upload-demo"
              :action="upLoad"
              :on-success="uploadSuccess"
              :file-list="fileList"
              :show-file-list="false">
              <el-button size="small" type="primary" class="upload-btn">点击上传</el-button>
            </el-upload>
            <!-- 上传附件列表 -->
            <div class="apply-upload">
              <el-row>
                <el-col :span="24">
                        <span class="file-list" v-for="(item, index) in fileList" :key="index">
                          <el-button
                            type="text"
                            style="padding-right: 20px;width: 80%;"
                            @click="downloadFile(item.fileName,item.relativePath)">
                            <span
                              style="width:100%;display: inline-block;overflow: hidden;text-align: left;text-overflow: ellipsis; white-space: nowrap;">
                              {{item.fileName}}
                            </span>
                          </el-button>
                          <i class="el-icon-error upload-file" @click="clickDel(index)"></i>
                        </span>
                </el-col>
              </el-row>
            </div>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="handleCancelClick">取 消</el-button>
          <el-button type="primary" @click="handleSaveClick('ruleForm')">确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <!--弹窗区域end-->
  </div>
</template>
<script>
import {systemnotice} from 'api/index'
import {formatDate} from 'common/js/date'
import {commonJs, downloadFile} from 'common/js/common'
// 实例
export default {
  data () {
    return {
      fileList: [],
      upLoad: commonJs.fileUploadUrl,
      searchForm: {},
      currentPage: 1,
      pageSizeList: [10, 20, 30, 40, 50],
      pageSize: 10,
      pageNo: 0,
      total: null, // 总条数
      dialogVisible: false,
      dialogTitle: '新增',
      ruleForm: {
        target: []
      },
      rules: {
        target: { required: true, message: '请选择接收对象', trigger: 'change' },
        content: { required: true, message: '请填写消息内容', trigger: 'blur' }
      },
      tableData: [],
      select: [],
      targetList: [
        {
          label: '集团公司',
          value: 1
        },
        {
          label: '供应分公司',
          value: 2
        },
        {
          label: '二级公司',
          value: 3
        },
        {
          label: '招标机构',
          value: 4
        }]
    }
  },
  computed: {
  },
  created () {
  },
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * this.pageSize + 1
    },
    /** 分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.getSystemNoticeList(this.pageNo)
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getExpertInfoList(this.pageNo)
    },
    filterIsPublished (row, column) {
      if (row.isPublished === 0) {
        return '未发布'
      } else if (row.isPublished === 1) {
        return '已发布'
      }
    },
    filterDate (row, column) {
      if (!row.publishedDate) {
        return ''
      } else {
        let date = new Date(row.publishedDate)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      }
    },
    downloadFile,
    uploadSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        let fileInformations = []
        let existFileSize = 0
        //  得到已经保存的附件信息
        for (var i = 0; i < fileList.length; i++) {
          if (!fileList[i].response) {
            existFileSize++
            fileInformations[i] = fileList[i]
          }
        }
        // 添加新的附件信息
        if (existFileSize < fileList.length) {
          for (var j = existFileSize; j < fileList.length; j++) {
            if (fileList[j].response) {
              fileInformations[j] = fileList[j].response.fileInformation
            }
          }
        }
        this.fileList = fileInformations // 获得目前项目中所有的附件信息
      }
    },
    /**
     * 删除上传附件
     */
    clickDel (index) {
      this.fileList.splice(index, 1)
    },
    onSearch () {
      this.getSystemNoticeList(0)
    },
    handleAddClick () {
      this.dialogVisible = true
    },
    /** 弹窗取消 */
    handleCancelClick () {
      this.dialogVisible = false
      this.ruleForm = {
        target: []
      }
      this.fileList = []
      this.$refs['ruleForm'].resetFields()
    },
    /** 弹窗确定 */
    handleSaveClick (val) {
      this.$refs[val].validate((vaild) => {
        if (vaild) {
          let target = ''
          this.ruleForm.target.map(item => {
            target += item + ','
          })
          this.ruleForm.target = target.substring(0, target.length - 1)
          this.ruleForm.fileInformationList = this.fileList
          systemnotice.save(this.ruleForm).then(res => {
            this.getSystemNoticeList(this.pageNo)
            this.dialogVisible = false
            this.fileList = []
          })
        }
      })
    },
    handleTableClick (row, operate) {
      let message = operate === 'edit' ? '将发布该条通知' : '永久删除'
      this.$confirm('此操作' + message + ', 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (Object.is(operate, 'edit')) {
          row.isPublished = 1
          row.publishedDate = new Date()
          systemnotice.save(row).then(res => {
            this.getSystemNoticeList(this.pageNo)
          })
        } else if (Object.is(operate, 'del')) {
          systemnotice.delete(row.objectId).then(res => {
            this.getSystemNoticeList(this.pageNo)
          })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    getSystemNoticeList (pageNo) {
      let query = {
        pageNo: pageNo,
        pageSize: this.pageSize,
        messageLike: this.searchForm.messageLike || null
      }
      systemnotice.getList(query).then(res => {
        if (res.data.systemNoticeList) {
          this.tableData = res.data.systemNoticeList.list
          this.total = res.data.systemNoticeList.total
          this.tableData.map(systemNotice => {
            let target = ''
            let data = systemNotice.target.split(',')
            data.map(item => {
              if (Object.is(item, '1')) {
                target += '集团公司，'
              } else if (Object.is(item, '2')) {
                target += '供应分公司，'
              } else if (Object.is(item, '3')) {
                target += '二级公司，'
              } else if (Object.is(item, '4')) {
                target += '招标机构，'
              }
            })
            systemNotice.targetName = target.substring(0, target.length - 1)
          })
        }
      })
    }
  },
  // 实例创建完成
  mounted () {
    this.getSystemNoticeList(this.pageNo)
  }
}
</script>
<style lang="less">
#notice-page {
  .notice-add {
    text-align: left;
    padding-bottom: 10px;
  }
  .block-page {
    padding-top: 15px;
  }
  .table-file-btn {
    margin-left: 0;
  }
  .dialog-box {
    .el-form-item__content {
      text-align: left;
    }
    .el-dialog__footer {
      text-align: center;
    }
  }
}
</style>
