<template>
  <div class="expand" id="expand">
    <div>
      <el-tree ref="expandMenuList" class="expand-tree"
         v-if="isLoadingTree"
         :data="setTree"
         node-key="id"
         highlight-current
         :props="defaultProps"
         :expand-on-click-node="false"
         :render-content="renderContent"
         :default-expanded-keys="defaultExpandKeys"
         @node-click="handleNodeClick"></el-tree>
    </div>
  </div>
</template>
<!-- VUE饿了么树形控件添加增删改功能按钮 -->
<script>
  import TreeRender from './tree_render'
  import {organ} from '../../../api/index'
  import $ from 'jquery'
  export default{
    name: 'tree',
    data () {
      return {
        maxexpandId: 999999999, // 新增节点开始id
        non_maxexpandId: 99999999, // 新增节点开始id(不更改)
        isLoadingTree: false, // 是否加载节点树
        setTree: [], // 节点树数据
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        defaultExpandKeys: [], // 默认展开节点列表
        editStatus: false,
        addStatusNum: 0,
        addStatus: false
      }
    },
    methods: {
      // ***********************************插件树的方法*************************************
      initExpand () {
        this.setTree.map((a) => {
          this.defaultExpandKeys.push(a.id)
        })
        this.isLoadingTree = true
      },
      handleNodeClick (d, n, s) { // 点击节点
        d.isEdit = false // 放弃编辑状态
        this.$emit('handleNodeClick', d)
      },
      renderContent (h, {node, data, store}) { // 加载节点
        let that = this
        return h(TreeRender, {
          props: {
            DATA: data,
            NODE: node,
            STORE: store,
            maxexpandId: that.non_maxexpandId
          },
          on: {
            nodeAdd: ((s, d, n) => that.handleAdd(s, d, n)),
            nodeEdit: ((s, d, n) => that.handleEdit(s, d, n)),
            nodeDel: ((s, d, n) => that.handleDelete(s, d, n)),
            nodeEditPass: ((s, d, n, m) => that.editPassadd(s, d, n, m))
          }
        })
      },
      handleAdd (s, d, n, m) { // 增加节点
        if (n.level >= 6) {
          this.$message.error('最多只支持6级！')
          return false
        }
        this.addStatusNum += 1
        if (this.addStatusNum > 1) {
          this.$message({
            type: 'warning',
            message: '请进行数据填写'
          })
          $('input').focus()
          return false
        }
        // 添加数据
        if (d.children == null) {
          d.children = []
        }
        d.children.push({
          name: '',
          pid: d.id,
          isEdit: true,
          children: [],
          creater: ''
        })
        // 展开节点
        if (!n.expanded) {
          n.expanded = true
        }
        this.editStatus = true
        this.addStatus = true
      },
      handleEdit (s, d, n) { // 编辑节点
        if (d.name != '') {
          this.editStatus = false
        }
      },
      // *******************************************接口调用开始*********************************
      // 树列表数据
      treeList () {
        organ.treeList(this.$store.getters.authUser.enterpriseId).then((res) => {
          this.setTree = res.data.deptTreeData
          let deptOpenIds = res.data.deptTreeData[0].id
          this.defaultExpandKeys.push(deptOpenIds)
        })
      },
      // 是否重复
      isRepeat (res) {
        if (res.data.status == 'repeat') {
          this.$message({
            type: 'warning',
            message: '此数据已存在，不能重复添加'
          })
          this.treeList()
          return false
        } else {
          this.treeList()
        }
      },
      // 提示
      prompt () {
        this.$message({
          type: 'warning',
          message: '添加内容不能为空'
        })
        this.addStatusNum = 0
      },
      promptLength () {
        this.$message({
          type: 'warning',
          message: '添加内容不能超过20个字符'
        })
      },
      // 添加
      editPassadd (s, d, n, m) {
        var tree = {...d}
        delete tree.isEdit
        tree.creater = this.$store.getters.authUser.userId
        // 编辑接口
        if (this.editStatus == false) {
          if (tree.name == '') {
            this.prompt()
            this.treeList()
            return false
          } else if (tree.name.length > 20) {
            this.promptLength()
            this.treeList()
            return false
          } else {
            if (m === 'blur') {
              organ.treeEdit(tree).then((res) => {
                this.isRepeat(res)
              })
            }
          }
        } else {
          // 增加接口
          if (tree.name == '') {
            this.prompt()
            this.treeList()
            return false
          } else if (tree.name.length > 20) {
            this.promptLength()
            this.treeList()
            return false
          } else {
            if (m === 'blur') {
              organ.treeeAdd(tree).then((res) => {
                this.isRepeat(res)
              })
            }
          }
        }
        this.addStatusNum = 0
      },
      // 删除
      handleDelete (s, d, n) { // 删除节点
        this.$confirm('是否删除此数据吗？', '提示', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if (d.children && d.children.length !== 0) {
            this.$message.error('此节点有子级，不可删除！')
            return false
          }
          if (d.pid == 0) {
            this.$message.error('此节点为最高节点，不可删除！')
            return false
          }
          organ.treeDelete(d.id).then((res) => {
            this.treeList()
          })
        }).catch(() => {
          return false
        })
      }
    },
    mounted () {
      // 插件树的方法调用
      this.initExpand()
      // 树列表数据调用
      this.treeList()
    }
  }
</script>

<style lang="less">
  #expand{
    width:100%;
    height:100%;
    overflow:hidden;
    .expand>div{
      height:85%;
      margin:0 auto;
      max-width:400px;
      overflow-y:auto;
    }
    .expand>div::-webkit-scrollbar-track{
      box-shadow: inset 0 0 6px rgba(0,0,0,.3);
      border-radius:5px;
    }
    .expand>div::-webkit-scrollbar-thumb{
      background-color:rgba(50, 65, 87, 0.5);
      outline:1px solid slategrey;
      border-radius:5px;
    }
    .expand>div::-webkit-scrollbar{
      width:10px;
    }
    .expand-tree{
      border:1px solid #eeeeee;
      margin-top:10px;
    }
    .expand-tree .el-tree-node.is-current,
    .expand-tree .el-tree-node:hover{
      overflow:hidden;
    }
    .expand-tree .is-current>.el-tree-node__content .tree-btn,
    .expand-tree .el-tree-node__content:hover .tree-btn{
      display:inline-block;
    }
    .expand-tree .is-current>.el-tree-node__content .tree-label{
      font-weight:600;
    }
    .el-button--primary {
      color: #fff;
      background-color: #3d63f4;
      border-color: #3d63f4;
    }
    .el-tree-node {
      white-space: nowrap;
      outline: 0;
      padding: 5px 0;
    }
  }
</style>
