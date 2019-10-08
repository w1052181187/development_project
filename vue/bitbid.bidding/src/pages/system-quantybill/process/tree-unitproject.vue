<template>
  <div class="tree">
    <el-table :data="data" border style="width: 100%" :row-style="showTr" ref="treeTable" header-cell-class-name="tabletitles">
      <el-table-column v-for="(column, index) in columns" :key="column.dataIndex" :label="column.text"  :width="index==0?600:(index==1)?180:''">
        <template slot-scope="scope">
          <span v-if="index===0" v-for="(space, levelIndex) in scope.row._level" :name="space" :key="levelIndex" class="ms-tree-space"></span> {{scope.row[column.dataIndex]}}
        </template>
      </el-table-column>
      <el-table-column prop="dataStandRefName" label="操作" width="80" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.children"></span>
          <el-upload
            class="upload-demo"
            :before-upload="beforeAvatarUpload"
            action="https://jsonplaceholder.typicode.com/posts/"
            :auto-upload="true"
            :on-success="uploadSuccess"
            :on-remove="delFile"
            :file-list="fileList" v-else>
            <el-button size="small" type="primary" @click="scopedata(scope)">上传</el-button>
          </el-upload>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import Utils from './utils/index.js'
//  import Vue from 'vue'
export default {
  name: 'tree-grid',
  props: {
    // 该属性是确认父组件传过来的数据是否已经是树形结构了，如果是，则不需要进行树形格式化
    treeStructure: {
      type: Boolean,
      default: function () {
        return false
      }
    },
    // 这是相应的字段展示
    columns: {
      type: Array,
      default: function () {
        return []
      }
    },
    // 元数据组的地图
    groupIdMap: {
      type: Object
    },
    // 类型的地图
    allTypesMap: {
      type: Object
    },
    groupName: {
      type: String
    },
    // 这是数据源
    dataSource: {
      type: Array,
      default: function () {
        return []
      }
    },
    // 这个作用是根据自己需求来的，比如在操作中涉及相关按钮编辑，删除等，需要向服务端发送请求，则可以把url传过来
    requestUrl: {
      type: String,
      default: function () {
        return ''
      }
    },
    // 这个是是否展示操作列
    treeType: {
      type: String,
      default: function () {
        return 'normal'
      }
    },
    // 是否默认展开所有树
    defaultExpandAll: {
      type: Boolean,
      default: function () {
        return false
      }
    }
  },
  data () {
    return {
      fileList: []
    }
  },
  computed: {
    // 格式化数据源
    data: function () {
      let me = this
      if (me.treeStructure) {
        let data = Utils.MSDataTransfer.treeToArray(me.dataSource, null, null, me.defaultExpandAll)
        return data
      }
      return me.dataSource
    }
  },
  methods: {
    // 打印数据
    scopedata (scope) {
    },
    // 上传文件
    // 上传限制
    beforeAvatarUpload (file) {

    },
    // 上传成功
    uploadSuccess (response, file, fileList) {
      // this.ruleForm.file = file.name
    },
    // 删除文件
    delFile (file, fileList) {
    },
    formatType (row, column, type) {
      if (!type) return
      return this.allTypesMap[type].keyName
    },
    formatHasGroup (row, column, hasGroupId) {
      if (!hasGroupId) return
      return this.groupIdMap[hasGroupId].dataElementGroupName
    },
    tableBtnClick (name, row) {
      this.$emit('operate', name, row)
    },
    // 显示行
    showTr: function (row, index) {
      let show = (row._parent ? (row._parent._expanded && row._parent._show) : true)
      row._show = show
      return show ? '' : 'display:none;'
    },
    // 展开下级
    toggle: function (trIndex) {
      let me = this
      let record = me.data[trIndex]
      record._expanded = !record._expanded
    },
    // 显示层级关系的空格和图标
    spaceIconShow (index) {
      let me = this
      if (me.treeStructure && index === 0) {
        return true
      }
      return false
    },
    // 点击展开和关闭的时候，图标的切换
    toggleIconShow (index, record) {
      let me = this
      if (me.treeStructure && index === 0 && record.children && record.children.length > 0) {
        return true
      }
      return false
    }
  }
}
</script>
<style scoped>
  .tree{
    width: 1160px;
    margin: 30px auto;
  }
  .el-table{
    width: 98%;
    margin: 0 auto;
  }
  .ms-tree-space {
    position: relative;
    top: 1px;
    display: inline-block;
    font-family: 'Glyphicons Halflings';
    font-style: normal;
    font-weight: 400;
    line-height: 1;
    width: 18px;
    height: 14px;
  }

  .ms-tree-space::before {
    content: ""
  }

  table td {
    line-height: 26px;
  }
</style>
