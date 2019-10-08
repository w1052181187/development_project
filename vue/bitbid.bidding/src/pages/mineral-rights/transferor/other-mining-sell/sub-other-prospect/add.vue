<template>
    <div id="otherprospaddbox"  class="maincontent">
      <div class="headertitle">
        <!--面包屑-->
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>其他方式出让矿业权</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/mr/transferor/other-mining-sell/sub-other-prospect', query: {type: this.$route.query.type, name: this.$route.query.name, roledsType: 1}}">提交{{this.$route.query.name}}公示</el-breadcrumb-item>
          <el-breadcrumb-item>添加公示</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
      </div>
      <div class="contentbigbox detailcontentbigbox">
        <el-form :model="otherPublicityForm" :rules="rules" ref="otherPublicityForm" label-width="100px" class="demo-ruleForm">
          <!--项目信息-->
          <div class="proinfobox">
            <span class="title">项目信息</span>
            <div class="formmian">
              <el-row>
                <el-col :span="8">
                  <el-form-item label="项目名称：" prop="projectName">
                    <el-input v-model="otherPublicityForm.projectName" placeholder="请输入项目名称"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="项目编号：" prop="projectNumber">
                    <el-input v-model="otherPublicityForm.projectNumber" placeholder="请输入项目编号"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="统一交易标识码：" prop="unifiedTransCode">
                    <el-input v-model="otherPublicityForm.unifiedTransCode" placeholder="请输入统一交易标识码"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="申请人名称：" prop="applicantName">
                    <el-input v-model="otherPublicityForm.applicantName" placeholder="请输入申请人名称"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="勘察单位名称：" prop="prospectUnitName">
                    <el-input v-model="otherPublicityForm.prospectUnitName" placeholder="请输入勘察单位名称"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="审批机关：" prop="approvingAuthority">
                    <el-input v-model="otherPublicityForm.approvingAuthority" placeholder="请输入审批机关"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="受理时间：" prop="acceptedDate">
                    <el-date-picker
                      v-model="otherPublicityForm.acceptedDate"
                      type="datetime"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      placeholder="选择日期时间">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="批准文件：" class="bitianicon">
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
                  <el-form-item label="矿种：" prop="minerals">
                    <el-select v-model="otherPublicityForm.minerals">
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
                  <el-form-item label="面积(km2)：" prop="area">
                    <el-input v-model="otherPublicityForm.area" placeholder="请输入面积"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="出让年限(年)：" prop="ageLimit">
                    <el-input v-model="otherPublicityForm.ageLimit" placeholder="请输入出让年限"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24" class="position">
                  <el-form-item label="矿区位置：" prop="miningLocation">
                    <el-cascader class=""
                       expand-trigger="hover"
                       :options="addressOptions"
                       v-model="selectedcityOptions"
                       @change="handlecityChange">
                    </el-cascader>
                    <el-input v-model="otherPublicityForm.miningLocation" placeholder="请输入详细地址"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24" class="area">
                  <el-form-item label="区域坐标：" prop="areaCoordinates">
                    <el-input type="textarea" v-model="otherPublicityForm.areaCoordinates"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
            <!--探矿权-->
            <div class="formmian"  v-if="parseInt(this.$route.query.type) === 2 || parseInt(this.$route.query.type) === 3">
              <el-row>
                <el-col :span="8">
                  <el-form-item label="矿山名称：" prop="mineName">
                    <el-input v-model="otherPublicityForm.mineName" placeholder="请输入矿山名称"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="矿种：" prop="minerals">
                    <el-select v-model="otherPublicityForm.minerals">
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
                  <el-form-item label="面积(km2)：" prop="area">
                    <el-input v-model="otherPublicityForm.area" placeholder="请输入面积"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="16" class="position">
                  <el-form-item label="矿区位置：" prop="miningLocation">
                    <el-cascader class=""
                       expand-trigger="hover"
                       :options="addressOptions"
                       v-model="selectedcityOptions"
                       @change="handlecityChange">
                    </el-cascader>
                    <el-input v-model="otherPublicityForm.miningLocation" placeholder="请输入详细地址"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="出让年限(年)：" prop="ageLimit">
                    <el-input v-model="otherPublicityForm.ageLimit" placeholder="请输入出让年限"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24" class="area">
                  <el-form-item label="区域坐标：" prop="areaCoordinates">
                    <el-input type="textarea" v-model="otherPublicityForm.areaCoordinates"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <!--采矿权-->
              <div>
                <el-row>
                  <el-col :span="8">
                    <el-form-item label="设计生产规模：" prop="processScale">
                      <el-input v-model="otherPublicityForm.processScale" placeholder="请输入生产规模" style="width: 50%;"></el-input>
                      <el-select v-model="otherPublicityForm.region" style="width: 50%;">
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
                    <el-form-item label="开采深度：" prop="miningDepth">
                      <el-input v-model="otherPublicityForm.miningDepth" placeholder="请输入开采深度"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="开采方法：" prop="miningMethod">
                      <el-input v-model="otherPublicityForm.miningMethod" placeholder="请输入开采方法"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="24" class="area">
                    <el-form-item label="资源储量情况："  prop="resourceReserves">
                      <el-input v-model="otherPublicityForm.resourceReserves" type="textarea"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
              <!--采矿权-->
            </div>
            <!--采矿权-->
          </div>
          <!--矿权信息-->
          <!--公告内容-->
          <div class="proinfobox">
            <span class="title" style="border-left: 3px solid #099cff">公告内容</span>
            <div class="formmain">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="公示名称：" prop="publicityName" class="content_notice">
                    <el-input v-model="otherPublicityForm.publicityName" placeholder="请输入公示名称"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="公示时间：" class="publicityTime bitianicon">
                    <el-date-picker
                      v-model="publicityDataRange"
                      type="datetimerange"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      range-separator="至"
                      start-placeholder="开始时间"
                      end-placeholder="结束时间"
                      align="right">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="公示内容：">
                    <div class="editor">
                      <editor ref="ueditor" class="ueditor" :editread="editread" :content="publicityText"></editor>
                    </div>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="附件：">
                    <commonFileUpload @upLoadFile="upLoadOtherFile" :fileType="fileOtherType" :oldFileName="oldOtherFileName"></commonFileUpload>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
          <!--公告内容-->
          <!--联系人信息-->
          <div class="proinfobox">
            <span class="title" style="border-left: 3px solid #099cff">联系人信息</span>
            <div class="formmain">
              <el-row>
                <el-col :span="8">
                  <el-form-item label="公司名称：" prop="companyName">
                    <el-input v-model="otherPublicityForm.companyName" placeholder="请输入公司名称"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="联系人：" prop="contacts">
                    <el-input v-model="otherPublicityForm.contacts" placeholder="请输入联系人"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="联系电话：" prop="contactsNumber">
                    <el-input v-model="otherPublicityForm.contactsNumber" placeholder="请输入联系电话"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="电子邮箱：" prop="email" :rules="[
                    { required: false, message: '请输入邮箱地址', trigger: 'blur' },
                    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                  ]">
                    <el-input v-model="otherPublicityForm.email" placeholder="请输入电子邮箱"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="地址：">
                    <el-input v-model="otherPublicityForm.address" placeholder="请输入地址"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
          <!--联系人信息-->
          <el-form-item class="submit-radio">
            <el-button type="primary" @click="submitForm('otherPublicityForm', 1)">提交</el-button>
            <el-button type="primary" class="save" @click="submitForm('otherPublicityForm', 0)">保存</el-button>
            <el-button class="cancel" @click="cancelBtn">取消</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
</template>
<script>
import * as citydata from '@/assets/js/citydata'
import editor from '@/components/public-resource/mineral-rights/common/ueditor/ueditor.vue'
import { otherProject, fileInfo } from '@/api'
import { isvalidFixedPhone, sumType } from '@/assets/js/validate'
import {mineTypes} from '@/assets/js/mineral/mineTypes'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
export default {
  components: {
    editor,
    commonFileUpload
  },
  data () {
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
    // 数字
    var sumTypes = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入数字'))
      } else if (!sumType(value)) {
        callback(new Error('请输入正确格式'))
      } else {
        callback()
      }
    }
    return {
      editread: false, // 富文本状态
      publicityText: '', // 富文本内容
      otherPublicityForm: {
        provinceId: '',
        cityId: '',
        countyId: '',
        typeoptions: parseInt(this.$route.query.type),
        region: 1
      },
      publicityDataRange: [], // 时间
      valueTime: '',
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
        label: '公开招标'
      }, {
        value: 2,
        label: '邀请招标'
      }, {
        value: 3,
        label: '挂牌'
      }, {
        value: 4,
        label: '拍卖'
      }, {
        value: 5,
        label: '其他'
      }],
      way: 1,
      // 项目类型
      prjectptions: [{
        value: 1,
        label: '新立'
      }, {
        value: 2,
        label: '变更'
      }, {
        value: 3,
        label: '延续'
      }, {
        value: 4,
        label: '保留'
      }, {
        value: 5,
        label: '注销'
      }],
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
      oldFileName: '',
      oldOtherFileName: '',
      // 三级联动
      addressOptions: citydata.CityInfo,
      selectedcityOptions: [],
      fileType: 8, // 批准文件
      fileArry: [], // 批准文件
      fileOtherType: 10, // 附件
      fileOtherArry: [], // 附件
      rules: {
        projectName: [
          { required: true, message: '请输入项目名称', trigger: 'blur' },
          { min: 1, max: 600, message: '长度在 1~600个字符', trigger: ['blur', 'change'] }
        ],
        projectNumber: [
          { required: true, message: '请输入项目编号', trigger: 'blur' },
          { min: 1, max: 17, message: '长度在 1~17个字符', trigger: ['blur', 'change'] }
        ],
        unifiedTransCode: [
          { required: true, message: '请输入统一交易识别码', trigger: 'blur' },
          { min: 1, max: 40, message: '长度在 1~40个字符', trigger: ['blur', 'change'] }
        ],
        transferorName: [
          { required: true, message: '请输入出让人名称', trigger: 'blur' },
          {min: 1, max: 600, message: '长度在 1~600个字符', trigger: ['blur', 'change']}
        ],
        licenceType: [
          { required: true, message: '请输入许可证号', trigger: 'blur' },
          { min: 1, max: 32, message: '长度在 1~32个字符', trigger: 'blur' }
        ],
        area: [
          { required: true, message: '请输入面积大小', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 6~20个数字', trigger: ['blur', 'change'] },
          {validator: sumTypes, trigger: 'blur'}
        ],
        ageLimit: [
          { required: true, message: '请输入出让年限', trigger: 'blur' },
          { min: 1, max: 5, message: '长度在 2~5个字符', trigger: ['blur', 'change'] },
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
          { required: true, message: '请输入资源储量情况', trigger: 'blur' },
          { min: 1, max: 600, message: '', trigger: 'blur' }
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
        publicityName: [
          { required: true, message: '请输入公示名称', trigger: 'blur' },
          {min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change']}
        ],
        projectType: [
          {required: true, message: '请选择项目类型', trigger: ['blur', 'change']}
        ],
        miningName: [
          { required: true, message: '请输入采矿人名称', trigger: 'blur' },
          {min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change']}
        ],
        prospectUnitName: [
          { required: true, message: '请输入勘察单位名称', trigger: 'blur' },
          {min: 1, max: 200, message: '长度在 1~200个字符', trigger: ['blur', 'change']}
        ],
        crrtificateOrgan: [
          { required: true, message: '请输入发证机关', trigger: 'blur' },
          {min: 1, max: 100, message: '长度在 1~100个字符', trigger: ['blur', 'change']}
        ],
        mineName: [
          { required: true, message: '请输入矿山名称', trigger: 'blur' },
          {min: 1, max: 600, message: '长度在 1~600个字符', trigger: ['blur', 'change']}
        ],
        minerals: [
          {required: true, message: '矿种不能为空', trigger: ['blur', 'change']}
        ],
        applicantName: [
          { required: true, message: '请输入申请人名称', trigger: 'blur' },
          {min: 1, max: 600, message: '长度在 1~600个字符', trigger: ['blur', 'change']}
        ],
        approvingAuthority: [
          { required: true, message: '请输入审批机关', trigger: 'blur' },
          {min: 1, max: 40, message: '长度在 1~40个字符', trigger: ['blur', 'change']}
        ],
        acceptedDate: [
          {required: true, message: '受理时间不能为空', trigger: ['blur', 'change']}
        ]
      }
    }
  },
  methods: {
    // 批准文件
    upLoadFile (file) {
      this.fileArry = file
    },
    // 附件
    upLoadOtherFile (file) {
      this.fileOtherArry = file
    },
    submitForm (otherPublicityForm, flag) {
      if (this.publicityDataRange) {
        this.otherPublicityForm.publicityStartTime = this.publicityDataRange[0]
        this.otherPublicityForm.publicityEndTime = this.publicityDataRange[1]
      }
      if (this.fileArry.length === 0) {
        this.$message({
          type: 'warning',
          message: '请上传批准文件'
        })
        return false
      }
      if (this.publicityDataRange.length === 0) {
        this.$message({
          type: 'warning',
          message: '公示时间不能为空'
        })
        return false
      }
      if (this.$refs.ueditor.getContent() === '') {
        this.$message({
          type: 'warning',
          message: '公告内容不能为空'
        })
        return false
      }
      if (this.subStatus === 1) {
        this.$message({
          type: 'warning',
          message: '不能重复提交数据'
        })
        return false
      }
      this.subStatus = 1
      this.otherPublicityForm.type = this.$route.query.type
      this.otherPublicityForm.publicityText = this.$refs.ueditor.getContent() // 富文本内容获取
      this.otherPublicityForm.status = flag
      this.$refs[otherPublicityForm].validate((valid) => {
        if (valid) {
          otherProject.save(this.otherPublicityForm).then((res) => {
            this.fileArry[0].relatedCode = res.data.saveOtherPublicity.code
            let allFile = null
            if (this.fileOtherArry.length !== 0) {
              this.fileOtherArry[0].relatedCode = res.data.saveOtherPublicity.code
              allFile = [...this.fileArry, ...this.fileOtherArry]
            } else {
              allFile = [...this.fileArry]
            }
            fileInfo.saveFile(allFile).then((res) => {
              this.fileArry = []
            })
            this.$router.push({path: '/mr/transferor/other-mining-sell/sub-other-prospect', query: {type: this.$route.query.type, name: this.$route.query.name, roledsType: 1}})
          })
        } else {
          return false
        }
      })
    },
    // 三级联动选择
    handlecityChange (value) {
      this.otherPublicityForm.provinceId = value[0]
      this.otherPublicityForm.cityId = value[1]
      this.otherPublicityForm.countyId = value[2]
    },
    // 富文本
    setContent () {
      this.$refs.ueditor.setContent(this.publicityText)
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/mr/transferor/other-mining-sell/sub-other-prospect', query: {type: this.$route.query.type, name: this.$route.query.name, roledsType: 1}})
    }
  },
  mounted () {
    this.setContent()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
</style>
