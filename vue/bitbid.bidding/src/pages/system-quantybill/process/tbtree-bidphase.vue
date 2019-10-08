<template>
  <div class="tree" v-loading="loading">
    <div class="filepath">
      <i v-if="Number(this.$route.query.type) === 2">导入投标计价接口</i>
      <i v-if="Number(this.$route.query.type) === 3">导入招标控制价计价接口</i>
      <span class="readfile" :title="fileName">{{fileName}}</span>
      <!--<uploadBidFile ref="upload"></uploadBidFile>-->
      <uploadBidFile v-on:childMiddleValue="childMiddleValue"></uploadBidFile>
    </div>
    <el-table :data="data" border style="width: 100%" :row-style="showTr" ref="treeTable" header-cell-class-name="tabletitles">
      <el-table-column v-for="(column, index) in columns" :key="column.dataIndex" :label="column.text"  :width="index==0?260:(index==2)?180:''" >
        <template slot-scope="scope">
          <span v-for="(space, levelIndex) in scope.row._level" :name="space" :key="levelIndex"  class="ms-tree-space">
          </span>
          {{scope.row[column.dataIndex]}}
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import { billPushData } from '@/api'
import Utils from './utils/index.js'
import uploadBidFile from '../selfpublic/uploadBidFile.vue'
export default {
  name: 'tree-grid',
  components: {
    uploadBidFile
  },
  props: {
    fileName: String,
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
    },
    program: {
      type: Object
    }
  },
  data () {
    return {
      fileList: [],
      filename: '',
      middleValue: null,
      loading: false
      // code: '',
      // tool: {}
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
  watch: {
    middleValue () {
      this.fromChildToParent()
    }
  },
  methods: {
    childMiddleValue (val) {
      this.middleValue = val
    },
    // 招标第一步 导入招标数据
    _getData () {
      this.loading = true
      if (this.program.project) {
        this.middleValue = this.program.project
        this.loading = false
      } else if (this.program.projectId || this.$route.query.projectId) {
        let projectId = this.program.projectId ? this.program.projectId : this.$route.query.projectId
        billPushData.projectDetail(projectId).then((res) => {
          this.middleValue = res.data.project
          this.program.project = this.middleValue
          this.$emit('updateProgram', this.program)
          this.loading = false
        })
      } else {
        this.loading = false
        billPushData.detail(this.$route.query.programId).then((res) => {
          this.code = res.data.program.code
        })
      }
    },
    fromChildToParent () {
      this.$emit('childValue', this.middleValue)
      this.program.project = this.middleValue
      this.$emit('updateProgram', this.program)
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
    }
  },
  created () {
    this._getData()
  }
}
</script>
<style lang="less">
  .tree{
    width: 1160px;
    margin: 30px auto;
    .filepath{
      width: 700px;
      height: 40px;
      float: left;
      margin: 30px 0 30px 30px;
    }
    .el-table{
      width: 98%;
      margin: 0 auto;
    }
    .ms-tree-space {
      position: relative;
      top: 1px;
      display: inline-block;
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
    .filepath i{
      font-weight: bold;
      font-size: 16px;
      color: #333333;
      font-style: normal;
      float: left;
      margin-top: 18px;
    }
    .readfile{
      width: 300px;
      margin-top: 18px;
      padding: 0 0 0 10px;
      display: inline-block;
      border-bottom: 1px solid #dddddd;
      float: left;
      color: #409EFF;
      text-align: center;
      height: 21px;
      line-height: 21px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }
</style>
