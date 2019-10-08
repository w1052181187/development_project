<template>
    <div id="addbox" class="maincontent">
      <div class="headertitle">
        <!--面包屑-->
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>项目管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/mr/transferor/project/registration-project?roledsType=1' }">登记项目</el-breadcrumb-item>
          <el-breadcrumb-item>添加项目</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
      </div>
      <div class="contentbigbox detailcontentbigbox">
        <el-form :model="sectionForm" :rules="rules" ref="sectionForm" label-width="100px" class="demo-ruleForm">
          <!--项目信息-->
          <div class="proinfobox">
            <span class="title">项目信息</span>
            <div class="formmian">
              <el-row>
                <el-col :span="8">
                  <el-form-item label="项目名称" prop="sectionName">
                    <el-input v-model="sectionForm.sectionName" placeholder="请输入项目名称"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="项目编号" prop="sectionNumber">
                    <el-input v-model="sectionForm.sectionNumber" placeholder="请输入项目编号"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="统一交易标识码" prop="unifiedTransCode">
                    <el-input v-model="sectionForm.unifiedTransCode" placeholder="请输入统一交易标识码"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="投资项目统一代码" prop="investProjectCode">
                    <el-input v-model="sectionForm.investProjectCode" placeholder="请输入投资项目统一代码"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="出让类型" prop="transferType">
                    <span v-if="parseInt(this.$route.query.type) === 1">探矿权</span>
                    <span v-if="parseInt(this.$route.query.type) === 2">采矿权</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="出让方式" prop="landUse">
                    <el-select v-model="sectionForm.landUse">
                      <el-option
                        v-for="item in wayoptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="出让人名称" prop="transferorName">
                    <el-input v-model="sectionForm.transferorName" placeholder="请输入出让人名称"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="出让行为批准单位" prop="approvalUnit">
                    <el-input v-model="sectionForm.approvalUnit" placeholder="请输入出让行为批准单位"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="出让行为批准单位文件" class="bitianicon">
                    <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></commonFileUpload>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
          <!--项目信息-->
          <!--矿权信息-->
          <div class="proinfobox">
            <span class="title" style="border-left: 3px solid #2d9631">矿权信息</span>
            <!--探矿权-->
            <div class="formmian" v-if="parseInt(this.$route.query.type) === 1">
              <el-row>
                <el-col :span="8">
                  <el-form-item label="矿种" prop="minerals">
                    <el-select v-model="sectionForm.minerals">
                      <el-option
                        v-for="item in speciesoptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="面积(km2)" prop="area">
                    <el-input v-model="sectionForm.area" placeholder="请输入面积"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="出让年限(年)" prop="ageLimit">
                    <el-input v-model="sectionForm.ageLimit" placeholder="请输入出让年限"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24" class="position">
                  <el-form-item label="矿区位置" prop="provinceId" class="adressbox">
                    <el-cascader class=""
                       expand-trigger="hover"
                       :options="addressOptions"
                       v-model="selectedcityOptions"
                       @change="handlecityChange">
                    </el-cascader>
                  </el-form-item>
                  <el-form-item prop="miningLocation" class="adressbox">
                    <el-input v-model="sectionForm.miningLocation" placeholder="请输入详细地址"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24" class="area">
                  <el-form-item label="区域坐标" prop="areaCoordinates">
                    <el-input type="textarea" v-model="sectionForm.areaCoordinates"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
            <!--探矿权-->
            <!--采矿权-->
            <div class="formmian"  v-if="parseInt(this.$route.query.type) === 2">
              <el-row>
                <el-col :span="8">
                  <el-form-item label="矿山名称" prop="mineName">
                    <el-input v-model="sectionForm.mineName" placeholder="请输入矿山名称"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="矿种" prop="minerals">
                    <el-select v-model="sectionForm.minerals">
                      <el-option
                        v-for="item in speciesoptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="面积(km2)" prop="area">
                    <el-input v-model="sectionForm.area" placeholder="请输入面积"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="16" class="position">
                  <el-form-item label="矿区位置" prop="provinceId" class="adressbox">
                    <el-cascader class=""
                     expand-trigger="hover"
                     :options="addressOptions"
                     v-model="selectedcityOptions"
                     @change="handlecityChange">
                    </el-cascader>
                  </el-form-item>
                  <el-form-item prop="miningLocation" class="adressbox">
                    <el-input v-model="sectionForm.miningLocation" placeholder="请输入详细地址"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="出让年限(年)" prop="ageLimit">
                    <el-input v-model="sectionForm.ageLimit" placeholder="请输入出让年限"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24" class="area">
                  <el-form-item label="区域坐标" prop="areaCoordinates">
                    <el-input type="textarea" v-model="sectionForm.areaCoordinates"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <!--采矿权-->
              <div>
                <el-row>
                  <el-col :span="8">
                    <el-form-item label="设计生产规模" prop="processScale">
                      <el-input v-model="sectionForm.processScale" placeholder="请输入生产规模" style="width: 50%;"></el-input>
                      <el-select v-model="sectionForm.region" style="width: 50%;">
                        <el-option
                          v-for="item in companyArry"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                        </el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="开采深度" prop="miningDepth">
                      <el-input v-model="sectionForm.miningDepth" placeholder="请输入开采深度"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="开采方法" prop="miningMethod">
                      <el-input v-model="sectionForm.miningMethod" placeholder="请输入开采方法"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="24" class="area">
                    <el-form-item label="资源储量情况"  prop="resourceReserves">
                      <el-input v-model="sectionForm.resourceReserves" type="textarea"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
              <!--采矿权-->
            </div>
            <!--采矿权-->
          </div>
          <!--矿权信息-->
          <!--联系人信息-->
          <div class="proinfobox">
            <span class="title" style="border-left: 3px solid #099cff">联系人信息</span>
            <div class="formmain">
              <el-row>
                <el-col :span="8">
                  <el-form-item label="公司名称" prop="companyName">
                    <el-input v-model="sectionForm.companyName" placeholder="请输入公司名称"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="联系人" prop="contacts">
                    <el-input v-model="sectionForm.contacts" placeholder="请输入联系人"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="联系电话" prop="contactsNumber">
                    <el-input v-model="sectionForm.contactsNumber" placeholder="请输入联系电话"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="电子邮箱" prop="email" :rules="[
                    { required: false, message: '请输入邮箱地址', trigger: 'blur' },
                    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                  ]">
                    <el-input v-model="sectionForm.email" placeholder="请输入电子邮箱"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="地址">
                    <el-input v-model="sectionForm.address" placeholder="请输入地址"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
          <!--联系人信息-->
          <el-form-item class="submit-radio">
            <el-button type="primary" @click="submitForm('sectionForm', 1)">提交</el-button>
            <el-button type="primary" class="save" @click="submitForm('sectionForm', 0)">保存</el-button>
            <el-button class="cancel" @click="cancelBtn">取消</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
</template>
<script>
import * as citydata from '@/assets/js/citydata'
import {mineTypes} from '@/assets/js/mineral/mineTypes'
import { prProject, fileInfo } from '@/api'
import {isvalidFixedPhone, sumType} from '@/assets/js/validate'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
export default {
  components: {
    commonFileUpload
  },
  data () {
    // 数字
    var sumTypes = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入数字'))
      } else if (!sumType(value)) {
        callback(new Error('请输入数字'))
      } else {
        callback()
      }
    }
    // 电话号码
    var validPhoneUser = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入联系电话'))
      } else if (!isvalidFixedPhone(value)) {
        callback(new Error('请输入正确的11位手机号码或带区号的固话'))
      } else {
        callback()
      }
    }
    return {
      sectionForm: {
        provinceId: '',
        cityId: '',
        countyId: '',
        transferType: this.$route.query.type,
        region: 1
      },
      // 矿权类型
      typeoptions: [{
        value: 1,
        label: '探矿权'
      }, {
        value: 2,
        label: '采矿权'
      }],
      type: this.$route.query.type,
      // 出让方式
      wayoptions: [{
        value: 1,
        label: '拍卖'
      }, {
        value: 2,
        label: '挂牌'
      }],
      way: 1,
      // 矿种
      speciesoptions: mineTypes,
      species: 1,
      // 单位
      companyArry: [
        {
          value: 1,
          label: '万吨/年'
        }, {
          value: 2,
          label: '万立方米/年'
        },
        {
          value: 3,
          label: '亿立方米/年'
        }
      ],
      minename: 1,
      fileList: [],
      rules: {
        sectionName: [
          { required: true, message: '请输入项目名称', trigger: 'blur' },
          { min: 1, max: 600, message: '长度在 1~600个字符', trigger: ['blur', 'change'] }
        ],
        sectionNumber: [
          { required: true, message: '请输入项目编号', trigger: 'blur' },
          { min: 1, max: 17, message: '长度在 1~17个字符', trigger: ['blur', 'change'] }
        ],
        unifiedTransCode: [
          { required: true, message: '请输入统一交易识别码', trigger: 'blur' },
          { min: 1, max: 40, message: '长度在 1~40个字符', trigger: ['blur', 'change'] }
        ],
        investProjectCode: [
          { required: true, message: '请输入投资项目统一代码', trigger: 'blur' },
          { min: 1, max: 40, message: '长度在 1~40个字符', trigger: ['blur', 'change'] }
        ],
        transferorName: [
          { required: true, message: '请输入出让人名称', trigger: 'blur' },
          {min: 1, max: 600, message: '长度在 1~600个字符', trigger: ['blur', 'change']}
        ],
        landUse: [
          {required: true, message: '出让方式不能为空', trigger: ['blur', 'change']}
        ],
        minerals: [
          {required: true, message: '矿种不能为空', trigger: ['blur', 'change']}
        ],
        provinceId: [
          {required: true, message: '矿区位置不能为空', trigger: ['blur', 'change']}
        ],
        cityId: [
          {required: true, message: '矿区位置不能为空', trigger: ['blur', 'change']}
        ],
        approvalUnit: [
          { required: true, message: '请输入出让行为批准单位', trigger: 'blur' },
          {min: 1, max: 600, message: '长度在 1~600个字符', trigger: ['blur', 'change']}
        ],
        area: [
          { required: true, message: '请输入面积大小', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个数字', trigger: ['blur', 'change'] },
          {validator: sumTypes, trigger: 'blur'}
        ],
        ageLimit: [
          { required: true, message: '请输入出让年限', trigger: 'blur' },
          { min: 1, max: 5, message: '长度在 1~5个字符', trigger: ['blur', 'change'] },
          {validator: sumTypes, trigger: 'blur'}
        ],
        miningLocation: [
          { required: true, message: '请输入矿区位置', trigger: 'blur' },
          {min: 1, max: 200, message: '长度在 1~200个字符', trigger: ['blur', 'change']}
        ],
        areaCoordinates: [
          { required: true, message: '请输入区域坐标', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在 1~200个字符', trigger: ['blur', 'change'] }
        ],
        processScale: [
          { required: true, message: '请输入设计生产规模', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] }
        ],
        miningDepth: [
          { required: true, message: '请输入开采深度', trigger: 'blur' },
          {min: 1, max: 50, message: '长度在 1~50个字符', trigger: ['blur', 'change']}
        ],
        miningMethod: [
          { required: true, message: '请输入开采方式', trigger: 'blur' },
          {min: 1, max: 200, message: '长度在 1~200个字符', trigger: ['blur', 'change']}
        ],
        resourceReserves: [
          { required: true, message: '请输入资源储量情况', trigger: 'blur' }
        ],
        companyName: [
          { required: true, message: '请输入公司名称', trigger: 'blur' },
          {min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change']}
        ],
        contacts: [
          { required: true, message: '请输入联系人', trigger: 'blur' },
          {min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change']}
        ],
        contactsNumber: [
          { required: true, message: '请输入联系电话', trigger: 'blur' },
          {validator: validPhoneUser, trigger: 'blur'}
        ],
        mineName: [
          {required: true, message: '矿山名称不能为空', trigger: ['blur', 'change']}
        ]
      },
      // 三级联动
      addressOptions: citydata.CityInfo,
      selectedcityOptions: [],
      // 业务类型 （1.项目 2.公告 3.出让文件 4.澄清修改文件）
      fileType: 1,
      // 文件上传保存的数组
      fileArry: [],
      oldFileName: ''
    }
  },
  methods: {
    // 将子组件获取到的数据传给父组件
    upLoadFile (file) {
      this.fileArry = file
    },
    // 提交
    submitForm (sectionForm, flag) {
      if (this.fileArry.length === 0) {
        this.$message({
          type: 'warning',
          message: '请上传文件'
        })
        return false
      }
      this.$refs[sectionForm].validate((valid) => {
        if (valid) {
          this.sectionForm.status = flag
          prProject.save(this.sectionForm).then(res => {
            this.fileArry[0].relatedCode = res.data.section.code
            // 将附件信息入库
            fileInfo.saveFile(this.fileArry).then((res) => {
              this.fileArry = []
            })
            this.$router.push({path: '/mr/transferor/project/registration-project?roledsType=1'})
          })
        } else {
          return false
        }
      })
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/mr/transferor/project/registration-project'})
    },
    // 三级联动选择
    handlecityChange (value) {
      this.sectionForm.provinceId = value[0]
      this.sectionForm.cityId = value[1]
      this.sectionForm.countyId = value[2]
    }
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
</style>
