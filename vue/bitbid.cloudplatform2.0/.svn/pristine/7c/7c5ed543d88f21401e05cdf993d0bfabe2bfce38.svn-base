<template>
<span class="tree-expand" id="tree-expandid">
  <span class="tree-label" v-show="DATA.isEdit">
    <el-input class="edit" size="mini" autofocus
      v-model="DATA.name"
      placeholder="请输入添加的数据"
      :ref="'treeInput'+DATA.id"
      @click.stop.native="nodeEditFocus"
      @blur.stop="nodeEditPass(STORE,DATA,NODE,'blur')"
      @keyup.enter.stop.native="nodeEditPass(STORE,DATA,NODE,'keyup')"></el-input>
  </span>
  <span v-show="!DATA.isEdit"
        :class="[DATA.id > maxexpandId ? 'tree-new tree-label' : 'tree-label']">
    <span v-bind:title="DATA.name">{{DATA.name}}</span>
  </span>
  <span class="tree-btn" v-show="!DATA.isEdit">
    <i class="el-icon-plus" @click.stop="nodeAdd(STORE,DATA,NODE)"></i>
    <i class="el-icon-edit" @click.stop="nodeEdit(STORE,DATA,NODE)"></i>
    <i class="el-icon-delete" @click.stop="nodeDel(STORE,DATA,NODE)"></i>
  </span>
</span>
</template>

<script>
export default{
  name: 'treeExpand',
  props: ['NODE', 'DATA', 'STORE', 'maxexpandId'],
  data () {
    return {
      isEdit: false
    }
  },
  methods: {
    nodeAdd (s, d, n) { // 新增
      this.$nextTick(() => {
        this.$refs['treeInput' + d.id].$refs.input.focus()
      })
      this.$emit('nodeAdd', s, d, n)
    },
    nodeEdit (s, d, n) { // 编辑
      d.isEdit = true
      this.$nextTick(() => {
        this.$refs['treeInput' + d.id].$refs.input.focus()
      })
      this.$emit('nodeEdit', s, d, n)
    },
    nodeDel (s, d, n) { // 删除
      this.$emit('nodeDel', s, d, n)
    },
    nodeEditPass (s, d, n, m) { // 编辑完成
      d.isEdit = false
      this.$emit('nodeEditPass', s, d, n, m)
    },
    nodeEditFocus () {
      // 阻止点击节点的事件冒泡
    }
  }
}
</script>

<style lang="less">
  #tree-expandid{
    overflow:hidden;
    width: 100%;
  .tree-expand .tree-label.tree-new{
    font-weight:600;
  }
  .tree-label .edit{
    width:80%;
  }
  .tree-btn{
    display:none;
    width: 25%;
    margin-left: 5px;
  }
  .tree-btn i{
    color:#8492a6;
    font-size:0.9em;
    margin-right:3px;
  }
  .tree-label {
    font-size: 0.9em;
    display: inline-block;
    max-width: 50%;
    vertical-align: top;
    overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
  }
  }
</style>
