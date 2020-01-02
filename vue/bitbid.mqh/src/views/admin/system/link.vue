<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="link-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system/link' }"><span>友情链接</span></el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="maincont-box">
      <div class="add-btn">
        <el-button type="warning" icon="el-icon-plus" @click="addLinkBtn">新 增</el-button>
        <span class="add-span">可拖动表格行排序</span>
      </div>
      <div class="link-table">
        <table class="table-cont">
          <thead>
            <tr>
              <th>序号</th>
              <th>网站名称</th>
              <th>链接</th>
              <th>发布时间</th>
              <th>状态</th>
              <th>操作</th>
            </tr>
          </thead>
          <draggable
            v-model="tableData"
            :options="{animation: 150}"
            element="tbody"
            @start.stop="datadragStart"
            @end="drag=false"
            @update="datadragEnd">
            <tr v-for="(element, index) in tableData" :key="element.code">
              <td>{{index+1}}</td>
              <td>{{element.linkName}}</td>
              <td>{{element.linkUrl}}</td>
              <td>{{element.publishDate | filterDate}}</td>
              <td>{{element.isPublished | filterStatus}}</td>
              <td>
                <el-button type="text" v-if="element.isPublished == 0" @click="handleTableClick(element, 'publish')">发布</el-button>
                <el-button type="text" v-if="element.isPublished == 0" @click="handleTableClick(element, 'edit')">修改</el-button>
                <el-button type="text" @click="handleTableClick(element, 'del')">删除</el-button>
              </td>
            </tr>
          </draggable>
        </table>
      </div>
      <div class="no-data" v-if="!tableData.length">
        <p>暂无数据</p>
      </div>
    </div>
    <div class="dialog-box">
      <el-dialog
        title="新增"
        :visible.sync="dialogVisible"
        width="30%"
        center>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="网站名称:" prop="linkName">
            <el-input v-model="ruleForm.linkName"></el-input>
          </el-form-item>
          <el-form-item label="链接:" prop="linkUrl">
            <el-input placeholder="链接地址请携带前缀http://或https://" v-model="ruleForm.linkUrl"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitBtn('ruleForm');">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import draggable from 'vuedraggable'
import { friendLink } from 'api/index'
import {formatDate} from 'common/js/date'
// 实例
export default {
  data () {
    return {
      dialogVisible: false,
      currentPage: 1,
      ruleForm: {},
      rules: {
        linkName: [
          { required: true, message: '链接名称不能为空', trigger: 'blur' },
          { max: 20, message: '长度不能大于20个字符', trigger: 'blur' }
        ],
        linkUrl: [
          { required: true, message: '链接地址不能为空', trigger: 'blur' },
          { max: 128, message: '长度不能大于128个字符', trigger: 'blur' }
        ]
      },
      tableData: []
    }
  },
  computed: {
  },
  created () {
    this.init()
  },
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 初始化加载数据 */
    init () {
      friendLink.getList().then((res) => {
        this.tableData = res.data.friendLinkList
      })
    },
    /** 表格操作 */
    handleTableClick (row, type) {
      if (type === 'edit') { // 编辑
        friendLink.getOne(row.objectId).then((res) => {
          this.ruleForm = res.data.friendLink
          this.dialogVisible = true
        })
      } else if (type === 'del') { // 删除
        this.$confirm('此操作永久删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then((res) => {
          friendLink.del(row.objectId).then((res) => {
            this.init()
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          })
        })
      } else if (type === 'publish') {
        this.$confirm('确认发布吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then((res) => {
          friendLink.publish(row.objectId).then((res) => {
            if (res.data.resCode === '0000') {
              this.init()
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          })
        })
      }
    },
    /** 确定 */
    submitBtn (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          friendLink.update(this.ruleForm).then((res) => {
            if (res.data.resCode === '0000') {
              this.dialogVisible = false
              this.init()
            }
          })
        } else {
          return false
        }
      })
    },
    /** 新增 */
    addLinkBtn () {
      this.dialogVisible = true
      this.ruleForm = {}
    },
    /* 开始 */
    datadragStart () {
      // 阻止事件冒泡
      document.body.ondrop = function (event) {
        event.preventDefault()
        event.stopPropagation()
      }
    },
    datadragEnd (evt) {
      let tableDataUpdate = []
      this.tableData.forEach((item, index) => {
        let friendLink = {}
        friendLink.rank = index + 1
        friendLink.code = item.code
        friendLink.name = item.linkName
        tableDataUpdate.push(friendLink)
      })
      friendLink.updateRank(tableDataUpdate)
    }
  },
  components: {
    draggable
  },
  // 实例创建完成
  mounted () {
  }, /*
   * 过滤
   */
  filters: {
    filterDate (value) {
      if (value) {
        let date = new Date(value)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return null
      }
    },
    filterStatus (row, column) {
      if (row === 0) {
        return '未发布'
      } else if (row === 1) {
        return '已发布'
      }
    }
  }
}
</script>
<style lang="less">
#link-page {
  .add-btn {
    text-align: left;
    padding-bottom: 15px;
    .add-span {
      display: inline-block;
      vertical-align: bottom;
      padding-left: 30px;
      color: #999;
    }
  }
  .link-table {
    .table-cont {
      width: 100%;
      border: 1px solid #ebeef5;
      border-collapse: collapse;
      thead {
        height: 40px;
        line-height: 40px;
        color: #909399;
        background-color: #F2F2F2;
      }
      tr:nth-child(even) {
        background-color: #F2F2F2;
      }
      tr:hover {
        background-color: #F5F7FA;
      }
      tr,
      th,
      td {
        border: 1px solid #ebeef5;
      }
    }
  }
}
</style>
