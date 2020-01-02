<template>
  <!-- 后台主容器 -->
  <div class="admin-layout">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <!-- <el-breadcrumb-item :to="{ path: '/admin/1' }">首页</el-breadcrumb-item> -->
        <el-breadcrumb-item > 组织机构</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/details' }"><span>用户详情</span></el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="returnMainPage" class="go-back-btn">
        返回
      </el-button>
    </div>
    <div class="maincont-box details-cont">
      <el-form :model="formInfo"  label-width="100px" class="demo-ruleForm">
        <el-form-item label="用户名:" prop="userName">
          <span class="details-span">{{formInfo.account}}</span>
        </el-form-item>
        <el-form-item label="姓名:" prop="name">
          <span class="details-span">{{formInfo.name}}</span>
        </el-form-item>
        <el-form-item label="联系方式:" prop="cellphone">
          <span class="details-span">{{formInfo.cellphone}}</span>
        </el-form-item>
        <el-form-item label="所属单位:" prop="enterpriseName">
          <span class="details-span">{{formInfo.enterpriseName}}</span>
        </el-form-item>
        <el-form-item label="所属部门:" prop="departmentName">
          <span class="details-span">{{formInfo.departmentName}}</span>
        </el-form-item>
        <el-form-item label="角色:" prop="role">
          <span class="details-span">{{this.role}}</span>
        </el-form-item>
        <!-- <el-form-item label="头像:" prop="avater">
          <span class="details-span"><img v-if="imageUrl" :src="imageUrl" width="100px" height="100px"/></span>
        </el-form-item> -->
        <el-form-item label="备注" prop="remark">
          <span class="details-span">{{formInfo.remark}}</span>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import {user} from '../../../../api/index'
import {commonJs} from '../../../../common/js/common'
// 实例
export default {
  data () {
    return {
      formInfo: {},
      role: '',
      imageUrl: ''
    }
  },
  computed: {
  },
  created () {
    this.getUserInfo()
  },
  methods: {
    // 返回主页面
    returnMainPage () {
      this.$router.push({path: './users', query: {currentPage: this.$route.query.currentPage, pageNo: this.$route.query.pageNo, pageSize: this.$route.query.pageSize}})
    },
    // 获取用户信息
    getUserInfo () {
      user.getOne(this.$route.query.objectId).then((res) => {
        this.formInfo = res.data.user
        if (this.formInfo.fileInformation) {
          this.headImgName = this.formInfo.fileInformation.fileName
          this.headImgPath = this.formInfo.fileInformation.relativePath
          if (this.headImgPath) {
            this.imgShow = true
          }
          this.imageUrl = `${commonJs.imageReviewUrl}?fileName=${this.headImgName}&filePath=${this.headImgPath}`
        }
        for (var i = 0; i < this.formInfo.roles.length; i++) {
          this.role += this.formInfo.roles[i].name
          if (i !== this.formInfo.roles.length - 1) {
            this.role += ','
          }
        }
      })
    }
  },
  // 实例创建完成
  mounted () {
  }
}
</script>
<style lang="less" scoped>
  .el-form-item__content {
    text-align: left;
  }
  .details-span {
    display: flex;
  }
</style>
