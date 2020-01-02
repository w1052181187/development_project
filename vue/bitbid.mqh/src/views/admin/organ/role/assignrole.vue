<template>
    <!-- 后台主容器 -->
    <div class="assign-layout" id="assign-page">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <!-- <el-breadcrumb-item :to="{ path: '/admin/1' }">首页</el-breadcrumb-item> -->
          <el-breadcrumb-item > 组织机构</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/assignrole' }"><span>分配角色</span></el-breadcrumb-item>
        </el-breadcrumb>
        <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
          返回
        </el-button>
      </div>
      <div class="assign-main">
        <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
          <el-tab-pane label="业务权限" name="first">
            <div class="service-tree">
              <el-tree
                :data="dataTree"
                show-checkbox
                node-key="id"
                ref="tree"
                highlight-current
                :default-checked-keys="checkedKeys"
                >
              </el-tree>
              <!-- 业务权限按钮 -->
              <div class="service-btn">
                <el-button type="primary" @click="getCheckedNodes">确定</el-button>
                <el-button @click="cancel">取消</el-button>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="管辖范围" name="second" style="text-align:left;">
            <div class="service-tree">
              <el-form ref="form" :model="formInfo" label-width="80px">
                <el-form-item label="">
                  <el-radio-group v-model="radios">
                    <el-radio :label="1">个人数据</el-radio>
                    <el-radio :label="2">部门数据</el-radio>
                    <el-radio :label="3">公司数据</el-radio>
                    <el-radio :label="4">集团数据</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item class="range-btn">
                  <el-button type="primary" @click="onSubmit">确定</el-button>
                  <el-button @click="cancel">取消</el-button>
                </el-form-item>
              </el-form>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
</template>
<script>
import {permission, role} from '../../../../api/index'
// 实例
export default {
  data () {
    return {
      radios: 1,
      formInfo: {},
      activeName: 'first',
      dataTree: [],
      checkedKeys: [], // 已有权限，默认勾选
      checkedNodes: [],
      currentRoleInfo: ''
    }
  },
  computed: {
  },
  created () {
    this.getCurrentRoleInfo()
  },
  methods: {
    /** 得到当前角色的信息 */
    getCurrentRoleInfo () {
      role.getOne(this.$route.query.objectId).then((res) => {
        this.checkedNodes = [] // 清空上次操作的数据
        this.currentRoleInfo = res.data.role
        this.getPermissionInfo(this.currentRoleInfo.ownedEnterpriseType)
        if (this.currentRoleInfo.jurisdiction && this.currentRoleInfo.jurisdiction.type) {
          this.radios = this.currentRoleInfo.jurisdiction.type
        }
        if (this.currentRoleInfo.permissions.length > 0) {
          for (var i = 0; i < this.currentRoleInfo.permissions.length; i++) {
            if (this.currentRoleInfo.permissions[i].elementTreeModels.length === 0) {
              this.checkedKeys.push(this.currentRoleInfo.permissions[i].objectId)
            }
          }
        }
      })
    },
    /** 新增角色权限 */
    getCheckedNodes () {
      this.checkedNodes = [] // 清空原来的数据
      this.getAllCheckedPermissions(this.$refs.tree.getHalfCheckedNodes())
      this.getAllCheckedPermissions(this.$refs.tree.getCheckedNodes())
      if (this.checkedNodes.length === 0) {
        this.$confirm('角色业务权限不能为空，请选择', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {}).catch(() => {
          return false
        })
      } else {
        this.currentRoleInfo.permissions = this.checkedNodes
        role.update(this.currentRoleInfo)
        this.cancel()
      }
    },
    /** 得到编辑后的角色权限 */
    getAllCheckedPermissions (nodeList) {
      for (let i = 0; i < nodeList.length; i++) {
        let permission = {}
        permission.objectId = nodeList[i].id
        permission.name = nodeList[i].label
        this.checkedNodes.push(permission)
      }
      return this.checkedNodes
    },
    cancel () {
      this.$router.push({path: './roles'})
    },
    /** 获取全部的权限信息 */
    getPermissionInfo (ownedEnterpriseType) {
      permission.getList({
        ownedEnterpriseType: ownedEnterpriseType
      }).then((res) => {
        this.dataTree = this.getTreeNode(res.data.permissions)
      })
    },
    /** 遍历树 */
    getTreeNode (data) {
      let parentId = 0
      for (let i = 0; i < data.length; i++) {
        if (data[i].children.length !== 0) {
          parentId = data[i].id
          for (let j = 0; j < data[i].children.length; j++) {
            data[i].children[j].parentId = parentId
          }
          this.getTreeNode(data[i].children)
        }
      }
      return data
    },
    handleClick () {},
    /** 修改角色管辖范围 */
    onSubmit () {
      let jurisdiction = {}
      jurisdiction.type = this.radios
      jurisdiction.roleId = this.currentRoleInfo.objectId
      this.currentRoleInfo.jurisdiction = jurisdiction
      role.update(this.currentRoleInfo)
      this.cancel()
    }
  },
  // 实例创建完成
  mounted () {
  }
}
</script>
<style lang="less">
#assign-page {
  .assign-main {
    padding: 30px 20px;
    margin: 20px 20px 100px;
    background-color: #fff;
  }
  .el-tree-node__content {
    padding: 10px;
  }
  .service-tree {
    padding: 20px;
    border: 1px solid #f1f1f1;
  }
  .range-btn {
    text-align: center;
  }
}
</style>
