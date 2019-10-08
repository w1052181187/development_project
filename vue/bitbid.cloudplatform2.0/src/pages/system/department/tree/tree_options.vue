<template>
  <el-tree v-show="isShowOptions"
           empty-text="暂无数据"
           :highlight-current = true
           :default-expand-all = false
           :expand-on-click-node="true"
           :data="setTree"
           :filter-node-method="filterNode"
           @node-click="selectClassfy"
           class="width-220 selectTree-input objectTree"
           ref="selectTree">
  </el-tree>
</template>
<script>
import {department} from '@/api/system'
export default {
  name: 'selectTree',
  data () {
    return {
      selectDataId: null,
      selectDataLabel: '',
      setTree: [] // 节点树数据
    }
  },
  props: ['initData', 'selectSuccess', 'isShowOptions', 'startWithRootFlag'],
  watch: {
    filterText (val) {
      this.$refs.expandMenuList.filter(val)
    }
  },
  methods: {
    // 树列表数据
    treeList () {
      // 判断是否从根节点开始，否则从用户所属根公司开始
      if (this.startWithRootFlag) {
        department.queryList(this.$store.getters.authUser.enterpriseId).then((res) => {
          this.setTree = res.data.deptTreeData
          // 初始化选中的部门
          if (this.initData) {
            this.setTree.forEach((item) => {
              if (item.id === this.initData) {
                this.changeClassfy(item)
              }
            })
          }
        })
      } else {
        department.queryListByStartId(this.$store.getters.authUser.departmentComponyId).then((res) => {
          this.setTree = res.data.deptTreeData
          // 初始化选中的部门
          if (this.initData) {
            this.setTree.forEach((item) => {
              if (item.id === this.initData) {
                this.changeClassfy(item)
              }
            })
          }
        })
      }
    },
    filterNode (value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    selectClassfy (data) {
      this.changeClassfy(data)
      department.queryParentsIdsAndNames({departmentId: data.id, type: 2}).then(res => {
        this.wrapDepartmentListInfo(data, res.data.ids, res.data.names)
        this.$emit('selectSuccess', data)
      })
    },
    wrapDepartmentListInfo (data, ids, names) {
      data.departmentListIds = ids
      data.departmentListNames = names
    },
    changeClassfy (data) {
      this.selectDataId = data.id
      this.selectDataLabel = data.label
    }
  },
  mounted () {
    // 树列表数据调用
    this.treeList()
  }
}
</script>
<style scoped>
  /*下拉框选择树*/
  .objectTree {
    position: absolute;
    overflow: auto;
    z-index: 100;
    height: 200px;
    border: 1px solid #ddd;
    line-height: normal;
    z-index: 204;
  }
  .width-220{
    width: 100%
  }
</style>
