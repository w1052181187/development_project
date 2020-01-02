<template>
  <!-- 后台主容器 -->
  <div class="admin-layout">
    <div class="breadcrumb-mqh-tit" >
      <el-breadcrumb separator="/">
        <!-- <el-breadcrumb-item :to="{ path: '/admin/1' }">首页</el-breadcrumb-item> -->
        <el-breadcrumb-item > 组织机构</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/add-user' }" v-if="!$route.query.btn"><span>新增用户</span></el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/add-user' }" v-if="$route.query.btn"><span>编辑用户</span></el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <div class="maincont-box user-warp">
      <el-form :model="formInfo" :rules="rules" ref="formInfo" label-width="120px" class="demo-ruleForm" style="width:35%">
        <el-form-item label="用户名" prop="account">
          <el-input v-model="formInfo.account"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="formInfo.name"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="!$route.query.btn">
          <el-input v-model="formInfo.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="cellphone">
          <el-input v-model="formInfo.cellphone"></el-input>
        </el-form-item>
        <el-form-item label="所属单位" prop="enterpriseId">
          <el-select v-model="formInfo.enterpriseId" placeholder="请选择单位" style="width:100%" @change="getDepartmentAndRoleInfo">
            <el-option
              v-for="item in enterpriseInfo"
              :key="item.objectId"
              :label="item.name"
              :value="item.objectId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所属部门" prop="departmentId">
          <el-select v-model="formInfo.departmentId" placeholder="请选择部门" style="width:100%">
            <el-option
              v-for="item in departmentInfo"
              :key="item.id"
              :label="item.label"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="角色" >
          <el-select  v-model="roleform"  multiple allow-create placeholder="请选择角色" style="width:100%" @change="changeSelect">
            <el-option
              v-for="item in roleInfo"
              :key="item.objectId"
              :label="item.name"
              :value="item.objectId">
            </el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="上传头像">
          <el-upload
          :before-upload="beforeAvatarUpload"
          class="avatar-uploader"
          :action="upLoad"
          :auto-upload="true"
          :on-success="uploadSuccess"
          :show-file-list="showUploadList"
          >
            <img :src="imageUrl" width="100px" height="100px" v-if="imgShow"/>
            <i class="el-icon-plus avatar-uploader-icon" v-else></i>
          </el-upload>
        </el-form-item> -->
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" v-model="formInfo.remark"></el-input>
        </el-form-item>
      </el-form>
      <el-row class="gongneng_btnbox">
        <el-button  class="gongneng_btn" type="primary" @click="addUser('formInfo')">
          保存
        </el-button>
        <el-button @click="cancel">取消</el-button>
      </el-row>
    </div>
  </div>
</template>
<script>
import {user, role, enterprise} from '../../../../api/index'
import {commonJs} from '../../../../common/js/common'
import {isvalidPassword, isvalidPhone, isvalidAccount} from '../../../../assets/js/validate'
// 实例
export default {
  data () {
    let validAccount = (rule, value, callback) => {
      if (!value) {

      } else if (!isvalidAccount(value)) {
        callback(new Error('请输入1-50个由数字和字母组成的用户名'))
      } else {
        callback()
      }
    }
    let validPassword = (rule, value, callback) => {
      if (!value) {

      } else if (!isvalidPassword(value)) {
        callback(new Error('请输入6-20位由"数字+字母"或"数字+特殊字符"或"字母+特殊字符"组成的密码'))
      } else {
        callback()
      }
    }
    let validPhone = (rule, value, callback) => {
      if (!value) {

      } else if (!isvalidPhone(value)) {
        callback(new Error('请输入正确的11位手机号码'))
      } else {
        callback()
      }
    }
    return {
      showUploadList: false, // 不展示图片列表
      upLoad: commonJs.fileUploadUrl,
      imageUrl: '', // 头像路径
      headImgList: {},
      headImgName: '',
      headImgPath: '',
      imgShow: false,
      selectMult: true,
      formInfo: {
        account: '',
        name: '',
        password: '',
        cellphone: '',
        enterpriseId: '',
        departmentId: '',
        state: '',
        remark: '',
        roles: []
      },
      roleform: [],
      roleInfo: [],
      enterpriseInfo: [],
      departmentInfo: [],
      rules: {
        account: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          {validator: validAccount, trigger: ['blur', 'change']}
        ],
        name: [
          { required: true, message: '请输入用户姓名', trigger: 'blur' },
          {min: 1, max: 10, message: '请输入1~10个字符', trigger: ['blur', 'change']}
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          {validator: validPassword, trigger: 'blur'}
        ],
        cellphone: [
          { required: true, message: '请输入电话号码', trigger: 'blur' },
          {validator: validPhone, trigger: 'blur'}
        ],
        enterpriseId: [
          {required: true, message: '请选择单位', trigger: ['blur', 'change']}
        ]
      }
    }
  },
  computed: {
  },
  created () {
  },
  methods: {
    /** 上传成功 */
    uploadSuccess (response, file, fileList) {
      this.headImgList = {}
      this.headImgList = response.fileInformation
      this.imgShow = true
      // 设置回显头像
      this.imageUrl = file.url
    },
    /** 上传限制 */
    beforeAvatarUpload (file) {
      const isJPG = file.type === 'image/png' || file.type === 'image/jpg' || file.type === 'image/jpeg'
      const isLtM = file.size / 1024 / 1024 <= 0.5
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG和PNG 格式!')
      }
      if (!isLtM) {
        this.$message.error('上传头像图片大小不能超过 512KB!')
      }
      return isJPG && isLtM
    },
    changeSelect (data) {
      let arr = []
      for (var i = 0; i < data.length; i++) {
        let obj = {}
        obj.objectId = data[i]
        arr.push(obj)
      }
      this.formInfo.roles = arr
    },
    /** 新增用户信息操作 */
    addUser (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.formInfo.fileInformation = this.headImgList
          user.save(this.formInfo).then((res) => {
            this.$router.push({path: './users', query: {currentPage: this.$route.query.currentPage, pageNo: this.$route.query.pageNo, pageSize: this.$route.query.pageSize}})
          })
        } else {
          return false
        }
      })
    },
    /** 取消操作 */
    cancel () {
      // this.imageUrl = ''
      this.$router.push({path: './users', query: {currentPage: this.$route.query.currentPage, pageNo: this.$route.query.pageNo, pageSize: this.$route.query.pageSize}})
    },
    /** 获取所有的单位信息 */
    getEnterpriseInfo () {
      enterprise.getAll().then((res) => {
        this.enterpriseInfo = res.data.enterprises
      })
    },
    /** 获取所有的角色信息 */
    getRoleInfo (data) {
      this.roleform = []
      enterprise.getOne(data).then((res) => {
        role.getList({
          pageNo: 1,
          pageSize: 0,
          ownedEnterpriseType: res.data.enterprise.type
        }).then((res) => {
          this.roleInfo = res.data.roles.list
        })
      })
    },
    /** 获取指定单位下的部门信息 */
    getDepartmentInfo (data) {
      enterprise.getByParentId(data).then((res) => {
        let result = res.data.departmentList
        this.getTreeNode(result)
      })
    },
    /** 遍历树 */
    getTreeNode (data) {
      this.departmentInfo = []
      for (let i = 0; i < data.length; i++) {
        this.departmentInfo.push(data[i])
        if (data[i].children.length > 0) {
          this.getTreeNode(data[i].children)
        }
      }
      return this.departmentInfo
    },
    /** 获取指定企业下的部门和角色信息 */
    getDepartmentAndRoleInfo (data) {
      this.formInfo.departmentId = '' // 清空上次的数据
      this.getDepartmentInfo(data)
      this.getRoleInfo(data)
    },
    /** 获取单个用户的信息 */
    formData () {
      if (this.$route.query.btn) {
        user.getOne(this.$route.query.objectId).then((res) => {
          this.formInfo = res.data.user
          if (this.formInfo.fileInformation) {
            this.headImgList = this.formInfo.fileInformation
            this.headImgName = this.formInfo.fileInformation.fileName
            this.headImgPath = this.formInfo.fileInformation.relativePath
            if (this.headImgPath) {
              this.imgShow = true
            }
            this.imageUrl = `${commonJs.imageReviewUrl}?fileName=${this.headImgName}&filePath=${this.headImgPath}`
          }
          if (this.formInfo.enterpriseId) {
            this.getDepartmentInfo(this.formInfo.enterpriseId)
            this.getRoleInfo(this.formInfo.enterpriseId)
          }
          let obj = {}
          for (var i = 0; i < res.data.user.roles.length; i++) {
            this.roleform.push(obj.objectId = res.data.user.roles[i].objectId)
          }
        })
      }
    }
  },
  // 实例创建完成
  mounted () {
    // this.getRoleInfo()
    this.formData()
    this.getEnterpriseInfo()
  }
}
</script>
<style lang="less">
  .user-warp {
    text-align: left;
    padding: 20px 0;
    .el-form-item__error {
      width: 425px;
    }
  }
  .gongneng_btnbox {
    margin-left: 200px;
  }
  // 头像样式
  .avatar-uploader {
    text-align: left;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px !important;;
    text-align: center;
  }
  .avatar {
    width: 100px;
    height: 100px;
    display: block;
  }
</style>
