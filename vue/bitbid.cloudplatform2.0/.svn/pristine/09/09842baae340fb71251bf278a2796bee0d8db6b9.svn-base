<template>
  <div>
    <el-tree ref="radioTree"
             empty-text="暂无数据"
             show-checkbox
             :highlight-current = true
             :expand-on-click-node="true"
             node-key="id"
             :data="setTree"
             :default-checked-keys="initData"
             :default-expanded-keys="initData"
             class="width-220 selectTree-input objectTree"
             :props="defaultProps"
             check-strictly
             @check="getChecked">
    </el-tree>
  </div>
</template>
<script>
import {department} from '@/api/system'
export default {
  name: 'radioTree',
  data () {
    return {
      setTree: [], // 节点树数据
      defaultProps: {
        children: 'children',
        label: 'label'
      }
    }
  },
  props: ['initData'],
  watch: {
    filterText (val) {
      this.$refs.radioTree.filter(val)
    },
    initData (val) {
      this.setCheckedKeys(val)
    }
  },
  methods: {
    // 树列表数据
    treeList () {
      department.queryList(this.$store.getters.authUser.enterpriseId).then((res) => {
        this.setTree = res.data.deptTreeData
        // this.handleDisabled(this.setTree)
      })
    },
    handleDisabled (array) {
      array.map((item) => {
        if (item.hasOwnProperty('children') && item.children.length > 0) {
          item.disabled = true
          this.handleDisabled(item.children)
        }
      })
    },
    getChecked (node, tree) {
      if (tree.checkedKeys.length > 0) {
        this.$refs.radioTree.setCheckedKeys([node.id])
      }
      let checkedNodes = this.$refs.radioTree.getCheckedNodes()
      if (checkedNodes && checkedNodes.length > 0) {
        this.$emit('changeRadio', checkedNodes[0])
      }
    },
    setCheckedKeys (val) {
      this.$refs.radioTree.setCheckedKeys(val)
    }
  },
  mounted () {
    // 树列表数据调用
    this.treeList()
  }
}
</script>
<style scoped>
</style>
