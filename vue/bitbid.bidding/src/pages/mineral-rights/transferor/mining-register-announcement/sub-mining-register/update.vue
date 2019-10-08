<template>
    <div id="miningregupdatebox" class="maincontent">
      <div class="headertitle">
        <!--面包屑-->
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>矿业权登记公告</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/mr/transferor/mining-register-announcement/sub-mining-register?roledsType=1' }">提交矿业权登记公告</el-breadcrumb-item>
          <el-breadcrumb-item>修改公告</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
      </div>
      <div class="contentbigbox detailcontentbigbox">
        <el-form :model="miningAnnoForm" :rules="rules" ref="miningAnnoForm" label-width="100px" class="demo-ruleForm">
          <!--项目信息-->
          <div class="proinfobox">
            <span class="title">项目信息</span>
            <div class="formmian">
              <el-row>
                <el-col :span="8">
                  <el-form-item label="项目名称：" prop="projectName">
                    <el-input v-model="miningAnnoForm.projectName" placeholder="请输入项目名称"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="项目编号：" prop="projectNumber">
                    <el-input v-model="miningAnnoForm.projectNumber" placeholder="请输入项目编号"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="统一交易标识码：" prop="unifiedTransCode">
                    <el-input v-model="miningAnnoForm.unifiedTransCode" placeholder="请输入统一交易标识码"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="矿种类型：" prop="mineralType">
                    <span v-if="parseInt(this.$route.query.mineralSpecies) === 1">油气</span>
                    <span v-if="parseInt(this.$route.query.mineralSpecies) === 2">非油气</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="矿权类型：" prop="typeoptions">
                    <span v-if="parseInt(this.$route.query.MineralRights) === 1">探矿权</span>
                    <span v-if="parseInt(this.$route.query.MineralRights) === 2">采矿权</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="许可证号：" prop="licenceType">
                    <el-input v-model="miningAnnoForm.licenceType" placeholder="请输入许可证号"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="项目类型：" prop="projectType">
                    <el-select v-model="miningAnnoForm.projectType">
                      <el-option
                        v-for="item in prjectptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="采矿权人名称：" prop="miningName" v-if="parseInt(this.$route.query.MineralRights) === 2">
                    <el-input v-model="miningAnnoForm.miningName" placeholder="请输入采矿权人名称"></el-input>
                  </el-form-item>
                  <el-form-item label="探矿权人名称：" prop="miningName" v-else>
                    <el-input v-model="miningAnnoForm.miningName" placeholder="请输入探矿权人名称"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="勘察单位名称：" prop="prospectUnitName">
                    <el-input v-model="miningAnnoForm.prospectUnitName" placeholder="请输入勘察单位名称"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="发证机关：" prop="crrtificateOrgan" class="crrtificateOrgan">
                    <el-input v-model="miningAnnoForm.crrtificateOrgan" placeholder="请输入发证机关"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="有效期：" class="content_notice bitianicon">
                    <el-date-picker
                      v-model="termValidity"
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
            <div class="formmian" v-if="parseInt(this.$route.query.MineralRights) === 1">
              <el-row>
                <el-col :span="8">
                  <el-form-item label="矿种：" prop="minerals">
                    <el-select v-model="miningAnnoForm.minerals">
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
                    <el-input v-model="miningAnnoForm.area" placeholder="请输入面积"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="出让年限(年)：" prop="ageLimit">
                    <el-input v-model="miningAnnoForm.ageLimit" placeholder="请输入出让年限"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24" class="position">
                  <el-form-item label="矿区位置：" prop="miningLocation">
                    <el-cascader class=""
                       expand-trigger="hover"
                       :options="addressOptions"
                       v-model="cityselectedOptions"
                       @change="handlecityChange">
                    </el-cascader>
                    <el-input v-model="miningAnnoForm.miningLocation" placeholder="请输入详细地址"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24" class="area">
                  <el-form-item label="区域坐标：" prop="areaCoordinates">
                    <el-input type="textarea" v-model="miningAnnoForm.areaCoordinates"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
            <!--探矿权-->
            <!--采矿权-->
            <div class="formmian"  v-if="parseInt(this.$route.query.MineralRights) === 2">
              <el-row>
                <el-col :span="8">
                  <el-form-item label="矿山名称：" prop="mineName">
                    <el-input v-model="miningAnnoForm.mineName" placeholder="请输入矿山名称"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="矿种：" prop="minerals">
                    <el-select v-model="miningAnnoForm.minerals">
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
                    <el-input v-model="miningAnnoForm.area" placeholder="请输入面积"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="16" class="position">
                  <el-form-item label="矿区位置：" prop="miningLocation">
                    <el-cascader class=""
                       expand-trigger="hover"
                       :options="addressOptions"
                       v-model="cityselectedOptions"
                       @change="handlecityChange">
                    </el-cascader>
                    <el-input v-model="miningAnnoForm.miningLocation" placeholder="请输入详细地址"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="出让年限(年)：" prop="ageLimit">
                    <el-input v-model="miningAnnoForm.ageLimit" placeholder="请输入出让年限"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24" class="area">
                  <el-form-item label="区域坐标：" prop="areaCoordinates">
                    <el-input type="textarea" v-model="miningAnnoForm.areaCoordinates"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <!--采矿权-->
              <div>
                <el-row>
                  <el-col :span="8">
                    <el-form-item label="设计生产规模：" prop="processScale">
                      <el-input v-model="miningAnnoForm.processScale" placeholder="请输入生产规模" style="width: 50%;"></el-input>
                      <el-select v-model="miningAnnoForm.region" style="width: 50%;">
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
                      <el-input v-model="miningAnnoForm.miningDepth" placeholder="请输入开采深度"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="开采方法：" prop="miningMethod">
                      <el-input v-model="miningAnnoForm.miningMethod" placeholder="请输入开采方法"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="24" class="area">
                    <el-form-item label="资源储量情况："  prop="resourceReserves">
                      <el-input v-model="miningAnnoForm.resourceReserves" type="textarea"></el-input>
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
                  <el-form-item label="公告名称：" prop="annoName" class="content_notice">
                    <el-input v-model="miningAnnoForm.annoName" placeholder="请输入公告名称"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="公告时间公告时间：" class="content_notice bitianicon">
                    <el-date-picker
                      v-model="announcementTime"
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
                  <el-form-item label="公告内容：" class="bitianicon">
                    <div class="editor">
                      <editor ref="ueditor" class="ueditor" :editread="editread" :content="content"></editor>
                    </div>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="附件：" prop="">
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
                    <el-input v-model="miningAnnoForm.companyName" placeholder="请输入公司名称"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="联系人：" prop="contacts">
                    <el-input v-model="miningAnnoForm.contacts" placeholder="请输入联系人"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="联系电话：" prop="contactsNumber">
                    <el-input v-model="miningAnnoForm.contactsNumber" placeholder="请输入联系电话"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="电子邮箱：" prop="email" :rules="[
                    { required: false, message: '请输入邮箱地址', trigger: 'blur' },
                    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                  ]">
                    <el-input v-model="miningAnnoForm.email" placeholder="请输入电子邮箱"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="地址：">
                    <el-input v-model="miningAnnoForm.address" placeholder="请输入地址"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
          <!--联系人信息-->
          <el-form-item class="submit-radio">
            <el-button type="primary" @click="submitForm('miningAnnoForm', 1)">提交</el-button>
            <el-button type="primary" class="save" @click="submitForm('miningAnnoForm', 0)">保存</el-button>
            <el-button class="cancel" @click="cancelBtn">取消</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
</template>
<script>
import * as citydata from '@/assets/js/citydata'
import editor from '@/components/public-resource/mineral-rights/common/ueditor/ueditor.vue'
import { annoProject, fileInfo } from '@/api'
import {isvalidFixedPhone, sumType} from '@/assets/js/validate'
import {mineTypes} from '@/assets/js/mineral/mineTypes'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
export default {
  components: {
    editor,
    commonFileUpload
  },
  data () {
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
      // 富文本
      editread: false,
      content: '',
      miningAnnoForm: {
        provinceId: '',
        cityId: '',
        countyId: '',
        investProjectCode: parseInt(this.$route.query.mineralSpecies),
        typeoptions: parseInt(this.$route.query.MineralRights),
        region: 1
      },
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
      termValidity: [], // 有效期时间
      announcementTime: [], // 公告时间
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
      // 矿区名称
      minenameoptions: [{
        value: 1,
        label: '矿山名称1'
      }, {
        value: 2,
        label: '矿山名称2'
      }],
      minename: 1,
      fileList: [],
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
        investProjectCode: [
          { required: false, message: '请输入投资项目统一代码', trigger: 'blur' },
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
          {min: 1, max: 200, message: '', trigger: ['blur', 'change']}
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
        annoName: [
          { required: true, message: '请输入公告名称', trigger: 'blur' }
        ],
        projectType: [
          {required: true, message: '请选择项目类型', trigger: ['blur', 'change']}
        ],
        miningName: [
          { required: true, message: '请输入名称', trigger: 'blur' },
          {min: 1, max: 200, message: '长度在 1~200个字符', trigger: ['blur', 'change']}
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
          {required: true, message: '矿山名称不能为空', trigger: ['blur', 'change']},
          {min: 1, max: 600, message: '长度在 1~600个字符', trigger: ['blur', 'change']}
        ],
        minerals: [
          {required: true, message: '矿种不能为空', trigger: ['blur', 'change']}
        ]
      },
      // 三级联动
      addressOptions: citydata.CityInfo,
      cityselectedOptions: [],
      fileType: 11, // 批准文件
      fileArry: [], // 批准文件
      fileOtherType: 12, // 附件
      fileOtherArry: [], // 附件
      subStatus: 0,
      originalId: '', // 批准文件
      oldFileName: '', // 批准文件
      originalOtherId: '', // 附件
      oldOtherFileName: '' // 附件
    }
  },
  methods: {
    // 批准文件
    upLoadFile (file) {
      this.updateStatus = true
      this.fileArry = file
    },
    // 附件
    upLoadOtherFile (file) {
      this.updateStatus = true
      this.fileOtherArry = file
    },
    cancelBtn () {
      this.$router.push({path: '/mr/transferor/mining-register-announcement/sub-mining-register?roledsType=1'})
    },
    // 三级联动选择
    handlecityChange (value) {
      this.miningAnnoForm.provinceId = value[0]
      this.miningAnnoForm.cityId = value[1]
      this.miningAnnoForm.countyId = value[2]
    },
    // 查看
    toFormat () {
      this.termValidity = [this.miningAnnoForm.periOfStartValidity, this.miningAnnoForm.periOfEndValidity]
      this.announcementTime = [this.miningAnnoForm.annoStartTime, this.miningAnnoForm.annoEndTime]
    },
    // 内容数据
    contentFrom () {
      if (this.updateStatus) {
        if (this.fileArry.length === 0) {
          this.$message({
            type: 'warning',
            message: '请上传文件'
          })
          return false
        }
      }
      annoProject.queryOne(this.$route.query.objectId).then((res) => {
        this.miningAnnoForm = res.data.miningAnnocement
        this.content = res.data.miningAnnocement.content
        this.$refs.ueditor.setContent(this.content) // 设置富文本内容
        this.miningAnnoForm.region = Number(res.data.miningAnnocement.region)
        // 三级联动赋值
        let provinceId = res.data.miningAnnocement.provinceId
        let cityId = res.data.miningAnnocement.cityId
        let countyId = res.data.miningAnnocement.countyId
        this.cityselectedOptions = [provinceId, cityId, countyId]
        this.toFormat()
        // 批准文件获取
        if (res.data.miningAnnocement.fileInformation) {
          this.oldFileName = res.data.miningAnnocement.fileInformation.fileName
          this.originalId = res.data.miningAnnocement.fileInformation.objectId
        }
        // 附件获取
        if (res.data.miningAnnocement.fileInformations) {
          this.oldOtherFileName = res.data.miningAnnocement.fileInformations.fileName
          this.originalOtherId = res.data.miningAnnocement.fileInformations.objectId
        }
      })
    },
    // 提交
    submitForm (miningAnnoForm, flag) {
      if (this.termValidity) {
        this.miningAnnoForm.periOfStartValidity = this.termValidity[0]
        this.miningAnnoForm.periOfEndValidity = this.termValidity[1]
      }
      if (this.announcementTime) {
        this.miningAnnoForm.annoStartTime = this.announcementTime[0]
        this.miningAnnoForm.annoEndTime = this.announcementTime[1]
      }
      if (this.$refs.ueditor.getContent() === '') {
        this.$message({
          type: 'warning',
          message: '公告内容不能为空'
        })
        return false
      }
      if (this.termValidity.length === 0) {
        this.$message({
          type: 'warning',
          message: '有效期时间不能为空'
        })
        return false
      }
      if (this.announcementTime.length === 0) {
        this.$message({
          type: 'warning',
          message: '公告时间不能为空'
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
      this.miningAnnoForm.content = this.$refs.ueditor.getContent() // 富文本内容获取
      this.miningAnnoForm.status = flag
      let that = this
      this.$refs[miningAnnoForm].validate((valid) => {
        if (valid) {
          annoProject.update(this.miningAnnoForm).then((res) => {
            let allFile = null
            let deleteId = null
            if (that.fileArry.length !== 0 || that.fileOtherArry.length !== 0) {
              if (that.fileOtherArry.length === 0 && that.fileArry.length !== 0) {
                that.fileArry[0].relatedCode = that.miningAnnoForm.code
                allFile = [...this.fileArry]
                deleteId = this.originalId
              } else if (that.fileOtherArry.length !== 0 && that.fileArry.length === 0) {
                that.fileOtherArry[0].relatedCode = that.miningAnnoForm.code
                allFile = [...that.fileOtherArry]
                deleteId = this.originalOtherId
              } else {
                this.$message({
                  type: 'warning',
                  message: '不能同时修改两个文件'
                })
                return false
              }
              fileInfo.deleteFile(deleteId).then((res) => {
                fileInfo.saveFile(allFile).then((res) => {
                  this.fileArry = []
                })
              })
            }
            this.$router.push({path: '/mr/transferor/mining-register-announcement/sub-mining-register?roledsType=1'})
          })
        } else {
          return false
        }
      })
    }
  },
  mounted () {
    this.contentFrom()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
</style>
