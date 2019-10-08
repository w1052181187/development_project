<template>
  <div>
    <el-tree v-show="isShow" ref="checkboxTree"
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
             @check-change="getChecked">
    </el-tree>
  </div>
</template>
<script>
import {department} from '@/api/system'
export default {
  name: 'checkboxTree',
  data () {
    return {
      setTree: [], // 节点树数据
      defaultProps: {
        children: 'children',
        label: 'label'
      }
    }
  },
  props: ['initData', 'isShow'],
  watch: {
    filterText (val) {
      this.$refs.tree.filter(val)
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
      })
    },
    filterNode (value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    getChecked () {
      let checkedIds = this.$refs.checkboxTree.getCheckedNodes().map(item => item.id)
      this.$emit('changeChecked', checkedIds)
    },
    setCheckedKeys (val) {
      this.$refs.checkboxTree.setCheckedKeys(val)
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
