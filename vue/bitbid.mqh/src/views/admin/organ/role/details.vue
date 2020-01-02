<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="role-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <!-- <el-breadcrumb-item :to="{ path: '/admin/1' }">首页</el-breadcrumb-item> -->
        <el-breadcrumb-item > 组织机构</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/details' }"><span>角色详情</span></el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <div class="maincont-box details-cont">
      <el-form :model="formInfo"  label-width="100px" class="demo-ruleForm">
        <el-form-item label="角色名称:" prop="name">
          <span class="details-span">{{formInfo.name}}</span>
        </el-form-item>
        <el-form-item label="角色描述:" prop="description">
          <span class="details-span">{{formInfo.description}}</span>
        </el-form-item>
        <el-form-item label="企业类型:" prop="ownedEnterpriseType">
          <span class="details-span">{{formInfo.ownedEnterpriseType | FilterOwnedEnterpriseType}}</span>
        </el-form-item>
       <!-- <el-form-item label="头像:" prop="head">
          <span class="details-span"><img v-if="imgShow" :src="imageUrl" width="100px" height="100px"/></span>
        </el-form-item>-->
        <el-form-item label="管辖范围:" prop="jurisdiction">
          <span class="details-span">{{formInfo.jurisdiction | FilterJurisdiction}}</span>
        </el-form-item>
        <el-form-item label="业务权限:" prop="permissions">
          <el-tree
            :data="permissionTreeModel"
          >
          </el-tree>
          <!--<span class="details-span">{{permissions}}</span> -->
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import {role} from '../../../../api/index'
// 实例
export default {
  data () {
    return {
      formInfo: {},
      // headImgName: '',
      // headImgPath: '',
      // imageUrl: '',
      permissionTreeModel: []
      // imgShow: false
    }
  },
  computed: {
  },
  created () {
  },
  filters: {
    FilterJurisdiction: function (item) {
      if (item === undefined || item === null || item.type === 1) {
        return '个人'
      } else if (item.type === 2) {
        return '部门'
      } else if (item.type === 3) {
        return '公司'
      } else {
        return '集团'
      }
    },
    FilterOwnedEnterpriseType: function (item) {
      if (item === 1) {
        return '集团公司'
      } else if (item === 2) {
        return '供应分公司'
      } else if (item === 3) {
        return '二级公司'
      } else {
        return '招标机构'
      }
    }
  },
  methods: {
    getRoleInfo () {
      role.getOne(this.$route.query.objectId).then((res) => {
        this.formInfo = res.data.role
        /* if (this.formInfo.fileInformation) {
          this.headImgName = this.formInfo.fileInformation.fileName
          this.headImgPath = this.formInfo.fileInformation.relativePath
          if (this.headImgPath) {
            this.imgShow = true
          }
          this.imageUrl = `${commonJs.imageReviewUrl}?fileName=${this.headImgName}&filePath=${this.headImgPath}`
        } */
        /* for (var i = 0; i < this.formInfo.permissions.length; i++) {
          if (this.formInfo.permissions[i].elementTreeModels.length === 0) {
            if (this.formInfo.permissions[i].allName) {
              this.permissions += this.formInfo.permissions[i].allName
              if (i !== this.formInfo.permissions.length - 1) {
                this.permissions += ', '
              }
            }
          }
        } */
        this.createPermissionTreeModel(this.formInfo.permissions)
      })
    },
    /** 将权限信息构建成为树形结构 */
    createPermissionTreeModel (permissions) {
      for (let i = 0; i < permissions.length; i++) {
        // 树根
        if (permissions[i].parentId === 0) {
          this.permissionTreeModel.push({
            id: permissions[i].objectId,
            label: permissions[i].name,
            children: []
          })
        } else {
          // 第一层节点
          for (let j = 0; j < this.permissionTreeModel.length; j++) {
            if (this.permissionTreeModel[j].id === permissions[i].parentId) {
              this.permissionTreeModel[j].children.push({
                id: permissions[i].objectId,
                label: permissions[i].name,
                children: []
              })
            }
          }
        }
      }
      // 第二层节点
      for (let k = 0; k < permissions.length; k++) {
        for (let w = 0; w < this.permissionTreeModel.length; w++) {
          if (this.permissionTreeModel[w].children.length > 0) {
            for (let l = 0; l < this.permissionTreeModel[w].children.length; l++) {
              if (this.permissionTreeModel[w].children[l].id === permissions[k].parentId) {
                this.permissionTreeModel[w].children[l].children.push({
                  id: permissions[k].objectId,
                  label: permissions[k].name
                })
              }
            }
          }
        }
      }
    }
  },
  // 实例创建完成
  mounted () {
    this.getRoleInfo()
  }
}
</script>
<style lang="less">
#role-page {
  .details-span {
    display: flex;
  }
  .details-cont .el-tree__empty-text {
    display: none;
  }
}
</style>
