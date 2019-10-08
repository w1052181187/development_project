<template>
  <div id="purchaseadd" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>公告管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/annomanage/purchase' }">采购公告</el-breadcrumb-item>
        <el-breadcrumb-item>添加采购公告</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <template>
          <el-button @click="close">返回</el-button>
        </template>
      </div>
    </div>
    <div class="contentbigbox">
      <template>
        <el-form :model="tenderBulletin" :rules="rules" ref="tenderBulletins">
          <table class="detailtable">
          <tr>
            <td><i class="red">*</i>公告名称：</td>
            <td  colspan="2">
              <el-form-item prop="noticeName">
                <el-input v-model="tenderBulletin.noticeName" placeholder="请输入公告名称"></el-input>
              </el-form-item>
            </td>
            <td><i class="red">*</i>公告类型：</td>
            <td  colspan="2">
              <el-form-item prop="bulletinType">
                <el-select  v-model="tenderBulletin.bulletinType" class="select">
                  <el-option
                    v-for="item in bulletinTypeoptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </td>
          </tr>
          <tr>
            <td><i class="red">*</i>公告性质：</td>
            <td  colspan="2">
              <el-form-item prop="noticeNature">
                <el-select  v-model="tenderBulletin.noticeNature" class="select">
                  <el-option
                    v-for="item in bidxzoptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
              </el-select>
              </el-form-item>
            </td>
            <td><i class="red">*</i>交易分类：</td>
            <td  colspan="2">
              <el-form-item prop="tenderExpandsInfo.dealClassify">
                <el-select  v-model="tenderBulletin.tenderExpandsInfo.dealClassify" class="select">
                  <el-option
                    v-for="item in dealOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </td>
          </tr>
          <tr>
            <td><i class="red">*</i>采购项目编号：</td>
            <td  colspan="2">
              <el-form-item prop="tenderProjectCode">
                <el-input v-model="tenderBulletin.tenderProjectCode" placeholder="请输入采购项目编号"></el-input>
              </el-form-item>
            </td>
            <td><i class="red">*</i>采购项目名称：</td>
            <td  colspan="2">
              <el-form-item prop="tenderExpandsInfo.tenderProjectName">
                <el-input v-model="tenderBulletin.tenderExpandsInfo.tenderProjectName" placeholder="请输入采购项目名称"></el-input>
              </el-form-item>
            </td>
          </tr>
          <tr>
            <td><i class="red">*</i>项目行业：</td>
            <td  colspan="2">
              <el-form-item prop="tenderExpandsInfo.industryTypeFirst">
                <el-select v-model="tenderBulletin.tenderExpandsInfo.industryTypeFirst" class="select">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </td>
            <td><i class="red">*</i>项目类型：</td>
            <td  colspan="2">
              <el-form-item prop="tenderExpandsInfo.projectType">
                <el-select v-model="tenderBulletin.tenderExpandsInfo.projectType" class="select">
                  <el-option
                    v-for="item in bidtypeoptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </td>
          </tr>
          <tr>
            <td><i class="red">*</i>行政区域：</td>
            <td  colspan="2">
              <el-form-item prop="tenderExpandsInfo.provinceId">
                <el-cascader
                :options="addressOptions"
                expand-trigger="hover"
                v-model="regionArray"
                @change="handlerCityChange">
                </el-cascader>
              </el-form-item>
            </td>
            <td><i class="red">*</i>采购方式：</td>
            <td  colspan="2">
              <el-form-item  prop="tenderExpandsInfo.tenderMode">
                <el-select v-model="tenderBulletin.tenderExpandsInfo.tenderMode" class="select">
                  <el-option
                    v-for="item in bidmethodoptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </td>
          </tr>
          <tr>
            <td><i class="red">*</i>业主名称：</td>
            <td  colspan="2">
              <el-form-item prop="tenderExpandsInfo.ownerName">
                <el-input v-model="tenderBulletin.tenderExpandsInfo.ownerName" placeholder="请输入业主名称"></el-input>
              </el-form-item>
            </td>
            <td><i class="red">*</i>采购组织形式：</td>
            <td  colspan="2">
              <el-form-item prop="tenderExpandsInfo.tenderOrganizeForm">
                <el-select  v-model="tenderBulletin.tenderExpandsInfo.tenderOrganizeForm" class="select">
                  <el-option
                    v-for="item in bidxsoptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </td>
          </tr>
          <tr v-if="tenderBulletin.tenderExpandsInfo.tenderOrganizeForm !== 1">
            <td><i class="red">*</i>招标代理机构名称：</td>
            <td  colspan="2">
              <el-form-item prop="tenderExpandsInfo.tenderAgencyName">
                <el-input v-model="tenderBulletin.tenderExpandsInfo.tenderAgencyName" placeholder="请输入招标代理机构名称"></el-input>
              </el-form-item>
            </td>
            <td><i class="red">*</i>代理机构联系人：</td>
            <td  colspan="2">
              <el-form-item prop="tenderExpandsInfo.tenderAgencyContacts">
                <el-input v-model="tenderBulletin.tenderExpandsInfo.tenderAgencyContacts" placeholder="请输入代理机构联系人"></el-input>
              </el-form-item>
            </td>
          </tr>
          <tr>
            <td><i class="red">*</i>联系方式：</td>
            <td  colspan="2">
              <el-form-item prop="tenderExpandsInfo.contactInformation">
                <el-input v-model="tenderBulletin.tenderExpandsInfo.contactInformation" placeholder="请输入联系方式"></el-input>
              </el-form-item>
            </td>
            <td><i class="red">*</i>发布频道：</td>
            <td  colspan="2">
              <el-form-item prop="tenderExpandsInfo.type">
                <el-select  v-model="tenderBulletin.tenderExpandsInfo.type" class="select">
                  <el-option
                    v-for="item in channelOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </td>
          </tr>
          <tr>
            <td><i class="red">*</i>开标时间：</td>
            <td colspan="2">
              <el-form-item prop="bidOpenTime">
                <el-date-picker
                  v-model="tenderBulletin.bidOpenTime"
                  type="datetime"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  placeholder="请输入开标时间">
                </el-date-picker>
              </el-form-item>
            </td>
            <td><i class="red">*</i>是否接受联合体：</td>
            <td  colspan="2">
              <el-form-item prop="syndicatedFlag">
                <el-radio v-model="tenderBulletin.syndicatedFlag" label="1">是</el-radio>
                <el-radio v-model="tenderBulletin.syndicatedFlag" label="0">否</el-radio>
              </el-form-item>
            </td>
          </tr>
          <tr>
            <td><i class="red">*</i>公告内容：</td>
            <td  colspan="5">
              <template prop="noticeContent">
                <editor ref="ueditor" class="ueditor"></editor>
              </template>
            </td>
          </tr>
          <tr>
            <td>公告附件：</td>
            <td  colspan="5">
              <upLoad-file @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></upLoad-file>
            </td>
          </tr>
        </table>
          <el-form-item class="submit-radio">
            <el-button type="primary" @click="submitForm('tenderBulletins', 1)">提交</el-button>
            <el-button type="primary" class="save" @click="submitForm('tenderBulletins', 0)">保存</el-button>
            <el-button class="cancel" @click="close">取消</el-button>
          </el-form-item>
        </el-form>
      </template>
    </div>
  </div>
</template>
<script>
import * as industry from '../../../assets/js/industry'
import * as region from '../../../assets/js/region'
import editor from '@/components/ueditor/ueditor.vue'
import upLoadFile from '@/components/upload/publicFileUpload'
import {isvalidFixedPhone} from '@/assets/js/validate'
import {purchase} from '@/api'
export default {
  components: {
    editor,
    upLoadFile
  },
  data () {
    // 电话号码
    var validPhoneUser = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入联系电话'))
      } else if (!isvalidFixedPhone(value)) {
        callback(new Error('请输入正确的11位手机号码'))
      } else {
        callback()
      }
    }
    return {
      // 公告性质
      bidxzoptions: [
        {
          value: '1',
          label: '正常公告'
        },
        {
          value: '2',
          label: '更正公告'
        },
        {
          value: '3',
          label: '重发公告'
        },
        {
          value: '9',
          label: '其他'
        }
      ],
      // 公告类型
      bulletinTypeoptions: [
        {
          value: 1,
          label: '资格预审公告'
        },
        {
          value: 2,
          label: '招标公告'
        },
        {
          value: 3,
          label: '询价公告'
        },
        {
          value: 4,
          label: '竞争性谈判公告'
        },
        {
          value: 5,
          label: '竞争性磋商公告'
        }
      ],
      // 交易分类
      dealOptions: [
        {
          value: 1,
          label: '工程建设项目招标投标'
        },
        {
          value: 2,
          label: '政府采购'
        },
        {
          value: 3,
          label: '企业采购'
        },
        {
          value: 4,
          label: '其他交易'
        }
      ],
      // 项目类型
      bidtypeoptions: [
        {
          value: 1,
          label: '工程'
        },
        {
          value: 2,
          label: '货物'
        },
        {
          value: 3,
          label: '服务'
        }
      ],
      // 采购方式
      bidmethodoptions: [
        {
          value: 1,
          label: '公开招标'
        },
        {
          value: 2,
          label: '邀请招标'
        },
        {
          value: 3,
          label: '竞争性磋商'
        },
        {
          value: 4,
          label: '竞争性谈判'
        },
        {
          value: 5,
          label: '单一来源'
        },
        {
          value: 6,
          label: '询价'
        }
      ],
      // 发布频道
      channelOptions: [
        {
          value: 3,
          label: '采购公告'
        },
        {
          value: 5,
          label: '山东水务招标公司频道'
        },
        {
          value: 6,
          label: '山东鲁成招标公司频道'
        },
        {
          value: 7,
          label: '邹城市公共资源招投标频道'
        },
        {
          value: 8,
          label: '滨州市公共资源招投标频道'
        }
      ],
      // 项目行业
      options: industry.industry,
      // 行政区域
      addressOptions: region.CityInfo,
      // 行政区域数据
      regionArray: [],
      // 招标组织形式
      bidxsoptions: [
        {
          value: 1,
          label: '自行招标'
        },
        {
          value: 2,
          label: '委托招标'
        }
      ],
      value: 1,
      datetime: '',
      fileType: '',
      oldFileName: '',
      tenderBulletin: {
        noticeName: '',
        bulletinType: '',
        noticeNature: '',
        tenderProjectCode: '',
        tenderExpandsInfo: {
          tenderProjectName: '',
          industryTypeFirst: '',
          projectType: '',
          provinceId: '',
          tenderMode: '',
          ownerName: '',
          tenderOrganizeForm: '',
          tenderAgencyName: '',
          tenderAgencyContacts: '',
          contactInformation: '',
          type: ''
        },
        bidOpenTime: '',
        syndicatedFlag: ''
      },
      rules: {
        noticeName: [
          {required: true, message: '请输入公告名称', trigger: 'blur'},
          {min: 1, max: 600, message: '长度在 1~600个字符', trigger: ['blur', 'change']}
        ],
        bulletinType: [
          {required: true, message: '请选择公告类型', trigger: 'change'}
        ],
        noticeNature: [
          {required: true, message: '请选择公告性质', trigger: 'change'}
        ],
        tenderProjectCode: [
          {required: true, message: '请输入采购项目编号', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1~20字符', trigger: ['blur', 'change']}
        ],
        'tenderExpandsInfo.dealClassify': [
          {required: true, message: '请选择交易分类', trigger: 'change'}
        ],
        'tenderExpandsInfo.tenderProjectName': [
          {required: true, message: '请输入采购项目名称', trigger: 'blur'},
          {min: 1, max: 256, message: '长度在 1~256个字符', trigger: ['blur', 'change']}
        ],
        'tenderExpandsInfo.industryTypeFirst': [
          {required: true, message: '请选择项目行业', trigger: 'change'}
        ],
        'tenderExpandsInfo.projectType': [
          {required: true, message: '请选择项目类型', trigger: 'change'}
        ],
        'tenderExpandsInfo.provinceId': [
          {required: true, message: '请选择行政区域', trigger: 'change'}
        ],
        'tenderExpandsInfo.tenderMode': [
          {required: true, message: '请选择采购方式', trigger: 'change'}
        ],
        'tenderExpandsInfo.ownerName': [
          {required: true, message: '请输入业主名称', trigger: 'blur'},
          {min: 1, max: 200, message: '长度在 1~200个字符', trigger: ['blur', 'change']}
        ],
        'tenderExpandsInfo.tenderOrganizeForm': [
          {required: true, message: '请选择采购组织形式', trigger: 'change'}
        ],
        'tenderExpandsInfo.tenderAgencyName': [
          {required: true, message: '请输入招标代理机构名称', trigger: 'blur'},
          {min: 1, max: 200, message: '长度在 1~200个字符', trigger: ['blur', 'change']}
        ],
        'tenderExpandsInfo.tenderAgencyContacts': [
          {required: true, message: '请输入代理机构联系人', trigger: 'blur'},
          {min: 1, max: 200, message: '长度在 1~200个字符', trigger: ['blur', 'change']}
        ],
        'tenderExpandsInfo.contactInformation': [
          {required: true, message: '请输入联系方式', trigger: 'blur'},
          {validator: validPhoneUser, trigger: ['blur', 'change']}
        ],
        'tenderExpandsInfo.type': [
          {required: true, message: '请选择发布频道', trigger: 'blur'}
        ],
        bidOpenTime: [
          {required: true, message: '请选择日期', trigger: 'change'}
        ],
        syndicatedFlag: [
          { required: true, message: '请选择活动资源', trigger: 'change' }
        ]
      },
      status: null
    }
  },
  methods: {
    handlerCityChange (value) {
      this.tenderBulletin.tenderExpandsInfo.provinceId = value[0]
      this.tenderBulletin.tenderExpandsInfo.cityId = value[1]
    },
    upLoadFile (file) {
      if (file.length === 0) {
        this.tenderBulletin.fileInformations = []
      } else {
        this.tenderBulletin.fileInformations = file
        this.tenderBulletin.fileInformations[0].businessType = 7
      }
    },
    submitForm (name, status) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          if (!this.$refs.ueditor.getContent()) {
            this.$message({
              type: 'warning',
              message: '请输入公告内容'
            })
            return false
          }
          this.tenderBulletin.noticeContent = this.$refs.ueditor.getContent()
          this.tenderBulletin.status = status
          this.tenderBulletin.type = 1
          this.tenderBulletin.enterpriseId = this.$store.getters.authUser.enterpriseId
          purchase.save(this.tenderBulletin).then((res) => {
            if (res.data.resCode === '0000') {
              this.$router.push({path: '/annomanage/purchase', query: {status: this.status}})
            } else {
              return false
            }
          })
        } else {
          this.$message({
            type: 'warning',
            message: '请检查必填项是否填写完毕'
          })
          return false
        }
      })
    },
    close () {
      this.$router.push({path: '/annomanage/purchase', query: {status: this.status}})
    }
  },
  mounted () {
    this.status = this.$route.query.status ? this.$route.query.status : this.$route.query.status === 0 ? this.$route.query.status : null
  }
}
</script>
<style lang="less">
  #purchaseadd{
    .select {
      float: left;
      width: 100%;
    }
    .el-cascader{
      float: left;
      width: 100%;
    }
    .el-date-editor{
      float: left;
      width: 100%;
    }
    .el-date-editor.el-input, .el-date-editor.el-input__inner{
      width: 100%;
    }
    .el-form-item{
      margin-bottom: 0px;
    }
    .el-form-item__error{
      display: none;
    }
    .el-radio{
      float: left;
    }
  }
</style>
