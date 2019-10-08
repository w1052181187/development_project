<template>
  <div style="display: inline-block">
    <!--数据范围-->
    <div class="datarange_bigbox" v-if="moduleId === 10001 || moduleId === 10005">
      <div class="operation_datarangetitle">数据范围</div>
      <div class="operation_datarangecon" v-show="dishadeStaus === true">
        <el-tree
          :data="departmentTree"
          node-key="id"
          ref="tree"
          :default-checked-keys="checkedKeyarc"
          :props="defaultProps">
        </el-tree>
      </div>
      <div class="operation_datarangecon" v-show="dishadeStaus === false">
        <el-tree
          :data="departmentTree"
          show-checkbox
          node-key="id"
          ref="tree"
          @check-change="updateTreeNodes"
          :default-checked-keys="checkedKeyarc"
          :props="defaultProps">
        </el-tree>
      </div>
    </div>

    <!--数据范围-->
    <!--操作权限多选-->
    <div class="operation_jurisdiction">
      <div class="operation_juristitle">
        <span>操作权限</span>
        <span class="disableenable" @click="enableOrDisable">{{dishadnames}}</span>
      </div>
      <div class="operation_juriscon">
        <el-checkbox-group v-model="checkedPermissionsarc" :disabled="dishadeStaus" @change="updatePerChecked">
          <el-checkbox v-for="(permission,index) in permissions" v-if="permission.moduleMark === moduleId" :label="permission.objectId" :key="index" class="checkedbox">{{permission.name}}</el-checkbox>
        </el-checkbox-group>
      </div>
    </div>
    <!--操作权限多选-->
  </div>
</template>
<script>
import publicside from '../adminpublic/side.vue'
import {rolead, organ} from '../../api/index'

export default {
  components: {
    publicside
  },
  props: ['moduleId', 'treeNodes', 'permissionIds'],
  data () {
    return {
      activeIndex: '0',
      // 操作权限多选
      checkedPermissionsarc: [], // 默认选择
      permissions: [],
      // 树形数据
      departmentTree: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      checkedKeyarc: [],
      // 定义启用禁用初始状态
      dishadeStaus: true,
      dishadnames: '启用'
    }
  },
  methods: {
    // 启用禁用
    enableOrDisable () {
      this.$confirm('确认执行该操作吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'success'
      }).then(() => {
        if (this.dishadnames == '禁用') {
          this.dishadeStaus = true
          this.dishadnames = '启用'
          this.enableEvent()
          this.clearDatas()
        } else if (this.dishadnames == '启用') {
          this.dishadeStaus = false
          this.dishadnames = '禁用'
        }
      }).catch(() => {
        return false
      })
    },
    enableEvent () {
      this.$emit('clearModulePerAndDepIds', this.moduleId)
    },
    updateTreeNodes () {
      this.$emit('changeResultDepIds', this.$refs.tree.getCheckedKeys(), this.moduleId)
    },
    updatePerChecked (val) {
      this.$emit('changeResultPerIds', val, this.moduleId)
    },
    // 取消
    cancel () {
      this.$router.push({path: '/organ/role'})
    },
    getData () {
      // 部门树
      organ.treeList(this.$store.getters.authUser.enterpriseId).then((res) => {
        this.departmentTree = res.data.deptTreeData
      })
      // 权限
      rolead.distrjurisList().then((res) => {
        this.permissions = res.data.permissions
      })
    },
    clearDatas () {
      this.checkedPermissionsarc = []
      this.checkedKeyarc = []
      this.$refs.tree.setCheckedKeys(this.checkedKeyarc)
    },
    // 初始化选中数据
    initChecked () {
      if (this.permissionIds.length || this.treeNodes.length) {
        this.dishadnames = '禁用'
        this.dishadeStaus = false
      } else {
        this.dishadnames = '启用'
        this.dishadeStaus = true
      }
      this.checkedPermissionsarc = this.permissionIds
      this.checkedKeyarc = this.treeNodes
      if (this.$refs.tree !== undefined) {
        this.$refs.tree.setCheckedKeys(this.checkedKeyarc)
      }
    }
  },
  watch: {
    moduleId: function () {
      this.initChecked()
    }
  },
  mounted () {
    this.getData()
    this.initChecked()
  }
}
</script>
