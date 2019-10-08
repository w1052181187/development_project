<template>
  <div id="addbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>项目管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/project/massif-register?roledsType=1&showStatus=false' }">地块登记</el-breadcrumb-item>
        <el-breadcrumb-item>添加地块</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" :rules="rules" ref="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="地块编号：" prop="sectionNumber">
                  <el-input v-model="sectionForm.sectionNumber" placeholder="请输入地块编号"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="统一交易标识码：" prop="unifiedTransCode">
                  <el-input v-model="sectionForm.unifiedTransCode" placeholder="请输入统一交易标识码"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让行为批准单位：" prop="approvalUnit">
                  <el-input v-model="sectionForm.approvalUnit" placeholder="请输入出让行为批准单位"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="16">
                <el-form-item label="地块位置：" prop="provinceId" class="adressbox_selsct">
                  <el-cascader class=""
                     expand-trigger="hover"
                     :options="linkageData"
                     v-model="linkageArray"
                     @change="handlecityChange">
                  </el-cascader>
                </el-form-item>
                <el-form-item prop="address" class="adressbox">
                  <el-input v-model="sectionForm.address" placeholder="请输入详细地址"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让行为批准单位文件：" class="bitianicon">
                  <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName" :type="typeFile"></commonFileUpload>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="规划面积(平方公里)：" prop="area">
                  <el-input v-model="sectionForm.area" placeholder="请输入规划面积"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="实际出让面积(平方公里)：" prop="actualArea">
                  <el-input v-model="sectionForm.actualArea" placeholder="请输入实际出让面积"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让年限(年)：" prop="ageLimit">
                  <el-input v-model="sectionForm.ageLimit" placeholder="请输入出让年限"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="投资强度(万元/公顷)：" prop="investIntensity">
                  <el-input v-model="sectionForm.investIntensity" placeholder="请输入投资强度"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="容积率(%)：" prop="errorTolerRate">
                  <el-input v-model="sectionForm.errorTolerRate" placeholder="请输入容积率"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="绿化率(%)：" prop="greeningRate">
                  <el-input v-model="sectionForm.greeningRate" placeholder="请输入绿化率"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="建筑密度(%)：" prop="densityOfBuild">
                  <el-input v-model="sectionForm.densityOfBuild" placeholder="请输入建筑密度"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="建筑限高(m)：" prop="buildingHeight">
                  <el-input v-model="sectionForm.buildingHeight" placeholder="请输入建筑限高"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="土地用途：" prop="landUse">
                  <el-input v-model="sectionForm.landUse" placeholder="请输入土地用途"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="说明：" prop="explainCon">
                  <el-input type="textarea" v-model="sectionForm.explainCon"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="受让人资格条件：" prop="prerequisites">
                  <el-input type="textarea" v-model="sectionForm.prerequisites"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--竞价信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">竞价信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="供应方式：" prop="methodOfSupply">
                  <el-select v-model="sectionForm.methodOfSupply">
                    <el-option
                      v-for="item in supplyMode"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="招拍挂类型：" prop="type" v-if="sectionForm.methodOfSupply === 1">
                  <el-select v-model="sectionForm.type">
                    <el-option
                      v-for="item in strokesType"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="招标方式：" prop="biddingType" v-if="sectionForm.methodOfSupply === 1 && sectionForm.type === 1">
                  <el-select v-model="sectionForm.biddingType">
                    <el-option
                      v-for="item in tenderingType"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row v-if="sectionForm.methodOfSupply === 1">
              <el-col :span="8" v-if="sectionForm.biddingType === 1 && sectionForm.type === 1">
                <el-form-item label="资审方式：" prop="prequaType">
                  <el-select v-model="sectionForm.prequaType">
                    <el-option
                      v-for="item in methodType"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="估价报告备案号：">
                  <el-input v-model="sectionForm.valuationReport" placeholder="请输入估价报告备案号"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="竞买保证金(万元)：" prop="bidBond">
                  <el-input v-model="sectionForm.bidBond" placeholder="请输入竞买保证金"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="sectionForm.type !== 1">
                <el-form-item label="起始价(万元)：" prop="startingPrice">
                  <el-input v-model="sectionForm.startingPrice" placeholder="请输入起始价"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="sectionForm.type !== 1">
                <el-form-item label="加价幅度(万元)：" prop="bidIncrement">
                  <el-input v-model="sectionForm.bidIncrement" placeholder="请输入加价幅度"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="是否存在底价：" prop="isFloorPrice">
                  <el-select v-model="sectionForm.isFloorPrice">
                    <el-option
                      v-for="item in lowPrice"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="地块估算价(元)：" prop="conputedPrice">
                  <el-input v-model="sectionForm.conputedPrice" placeholder="请输入地块估算价"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--竞价信息-->
        <!--联系人信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">联系人信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="公司名称：" prop="companyName">
                  <el-input v-model="sectionForm.companyName" placeholder="请输入公司名称"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="联系人：" prop="contacts">
                  <el-input v-model="sectionForm.contacts" placeholder="请输入联系人"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="联系电话：" prop="contactsNumber">
                  <el-input v-model="sectionForm.contactsNumber" placeholder="请输入联系电话"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="电子邮箱：" prop="email" :rules="[
                    { required: false, message: '请输入邮箱地址', trigger: 'blur' },
                    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                  ]">
                  <el-input v-model="sectionForm.email" placeholder="请输入电子邮箱"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="地址：">
                  <el-input v-model="sectionForm.contactsAddress" placeholder="请输入地址"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--联系人信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('sectionForm', 1)" :loading="subStatus">提交</el-button>
          <el-button type="primary" class="save" @click="submitForm('sectionForm', 0)" :loading="subStatus">保存</el-button>
          <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import * as citydata from '@/assets/js/citydata'
import {isvalidFixedPhone, sumType} from '@/assets/js/validate'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
import { landProject, fileInfo } from '@/api'
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
        methodOfSupply: 1,
        type: 1,
        biddingType: 1,
        isFloorPrice: 0
      },
      subStatus: false,
      // 供应方式
      supplyMode: [
        {
          value: 1,
          label: '招拍挂出让'
        }, {
          value: 2,
          label: '协议出让'
        }
      ],
      // 招拍挂类型
      strokesType: [
        {
          value: 1,
          label: '招标出让'
        }, {
          value: 2,
          label: '挂牌出让'
        },
        {
          value: 3,
          label: '拍卖出让'
        }
      ],
      // 招标方式
      tenderingType: [
        {
          value: 1,
          label: '公开招标'
        }, {
          value: 2,
          label: '邀请招标'
        }
      ],
      // 资审方式
      methodType: [
        {
          value: 1,
          label: '资格候审'
        }, {
          value: 2,
          label: '资格预审'
        }
      ],
      // 是否存在低价
      lowPrice: [
        {
          value: 1,
          label: '是'
        }, {
          value: 0,
          label: '否'
        }
      ],
      rules: {
        sectionNumber: [
          { required: true, message: '请输入地块编号', trigger: 'blur' },
          { min: 1, max: 600, message: '长度在 1~600个字符', trigger: ['blur', 'change'] }
        ],
        unifiedTransCode: [
          { required: true, message: '请输入统一交易识别码', trigger: 'blur' },
          { min: 1, max: 40, message: '长度在 1~40个字符', trigger: ['blur', 'change'] }
        ],
        approvalUnit: [
          { required: true, message: '请输入出让行为批准单位', trigger: 'blur' },
          { min: 1, max: 600, message: '长度在 1~600个字符', trigger: ['blur', 'change'] }
        ],
        provinceId: [
          {required: true, message: '位置不能为空', trigger: ['blur', 'change']}
        ],
        address: [
          { required: true, message: '请输入详细地址', trigger: 'blur' },
          {min: 1, max: 200, message: '长度在 1~200个字符', trigger: ['blur', 'change']}
        ],
        area: [
          { required: true, message: '请输入规划面积大小', trigger: 'blur' },
          { min: 1, max: 18, message: '长度在 1~18个数字', trigger: ['blur', 'change'] },
          {validator: sumTypes, trigger: 'blur'}
        ],
        actualArea: [
          { required: true, message: '请输入实际出让面积大小', trigger: 'blur' },
          { min: 1, max: 18, message: '长度在 1~18个数字', trigger: ['blur', 'change'] },
          {validator: sumTypes, trigger: 'blur'}
        ],
        ageLimit: [
          { required: true, message: '请输入出让年限', trigger: 'blur' },
          { min: 1, max: 5, message: '长度在 1~5个字符', trigger: ['blur', 'change'] },
          {validator: sumTypes, trigger: 'blur'}
        ],
        investIntensity: [
          { required: true, message: '请输入投资强度', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] }
        ],
        errorTolerRate: [
          { required: false, message: '请输入容积率', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] }
        ],
        greeningRate: [
          { required: false, message: '请输入绿化率', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] }
        ],
        densityOfBuild: [
          { required: false, message: '请输入建筑密度', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] }
        ],
        buildingHeight: [
          { required: false, message: '请输入建筑限高', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] }
        ],
        landUse: [
          { required: true, message: '请输入土地用途', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1~50个字符', trigger: ['blur', 'change'] }
        ],
        explainCon: [
          { required: false, message: '请输入说明', trigger: 'blur' },
          { min: 1, max: 600, message: '长度在 1~600个字符', trigger: ['blur', 'change'] }
        ],
        prerequisites: [
          { required: true, message: '请输入受让人资格条件', trigger: 'blur' },
          { min: 1, max: 600, message: '长度在 1~600个字符', trigger: ['blur', 'change'] }
        ],
        methodOfSupply: [
          {required: true, message: '供应方式不能为空', trigger: ['blur', 'change']}
        ],
        prequaType: [
          {required: true, message: '资审方式不能为空', trigger: ['blur', 'change']}
        ],
        type: [
          {required: true, message: '招拍挂类型不能为空', trigger: ['blur', 'change']}
        ],
        biddingType: [
          {required: true, message: '招标方式不能为空', trigger: ['blur', 'change']}
        ],
        isFloorPrice: [
          {required: true, message: '是否存在底价不能为空', trigger: ['blur', 'change']}
        ],
        bidBond: [
          { required: true, message: '请输入竞买保证金', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] },
          {validator: sumTypes, trigger: 'blur'}
        ],
        conputedPrice: [
          { required: true, message: '请输入地块估算价', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] }
        ],
        startingPrice: [
          { required: true, message: '请输入起始价', trigger: 'blur' },
          {min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change']}
        ],
        bidIncrement: [
          { required: true, message: '请输入加价幅度', trigger: 'blur' },
          {min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change']}
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
        ]
      },
      // 三级联动
      linkageData: citydata.CityInfo,
      linkageArray: [],
      // 业务类型
      fileType: 1,
      // 文件类型：土地
      typeFile: 2,
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
    // 提交保存
    submitForm (sectionForm, flag) {
      if (this.fileArry.length === 0) {
        this.$message({
          type: 'warning',
          message: '请上传文件'
        })
        return false
      }
      if (this.sectionForm.type === 2) {
        this.sectionForm.methodOfTransfer = 2
      } else if (this.sectionForm.type === 3) {
        this.sectionForm.methodOfTransfer = 1
      } else if (this.sectionForm.prequaType === 1) {
        this.sectionForm.methodOfTransfer = 3
      } else if (this.sectionForm.prequaType === 2) {
        this.sectionForm.methodOfTransfer = 4
      }
      this.$refs[sectionForm].validate((valid) => {
        if (valid) {
          this.sectionForm.status = flag
          this.subStatus = true
          landProject.save(this.sectionForm).then((res) => {
            this.fileArry[0].relatedCode = res.data.landInformation.code
            // 将附件信息入库
            fileInfo.saveFile(this.fileArry).then((res) => {
              this.fileArry = []
            })
            this.$router.push({path: '/lt/transferor/project/massif-register?roledsType=1&showStatus=false'})
            this.subStatus = false
          })
        } else {
          return false
        }
      })
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
