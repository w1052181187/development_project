<template>
  <div class="cloudcontent expert-edit" id="expert_edit">
    <div class="main">
      <el-form :model="submitForm" :rules="rules" ref="submitForm" label-width="120px" class="demo-submitForm">
        <el-row>
          <el-col :span="12">
            <el-form-item label="专家姓名：" prop="name" :rules="rules.simpleRequiredText">
              <el-input v-model="submitForm.name" clearable placeholder="请输入专家姓名">
                <i class="el-icon-edit el-input__icon" slot="suffix"></i>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别：" prop="sex">
              <el-select v-model="submitForm.sex" placeholder="请选择">
                <el-option
                  v-for="item in sexList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="身份证号码：" prop="idNumber" :rules="rules.idNumber">
              <el-input v-model="submitForm.idNumber" clearable placeholder="请输入身份证号码">
                <i class="el-icon-edit el-input__icon" slot="suffix"></i>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出生年月：" prop="birthDay">
              <el-date-picker
                v-model="submitForm.birthDay"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择日期"
                style="width: 100%;">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-for="(item, index) in submitForm.expertCategoryList" :key="index">
          <el-col :span="12">
            <el-form-item :label="(index === 0) ? '专业类别：' : ''" :rules="rules.required" :prop="`expertCategoryList[${index}]`">
              <el-cascader
                v-model="submitForm.expertCategoryList[index]"
                :options="expertClassifyList"
                style="width:80%">
              </el-cascader>
              <el-button @click.prevent="addExpertCategory" v-if="index === 0" type="primary" style="margin-left:5px;float:right;">新增</el-button>
              <el-button @click.prevent="delExpertCategory(item)" v-if="index > 0" style="margin-left:5px;float:right;">删除</el-button>
           </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="专家分类：" :rules="rules.required" prop="dicExpertClassify">
              <el-select v-model="submitForm.dicExpertClassify" clearable placeholder="请选择" style="width:100%;">
                <el-option
                  v-for="item in dicExpertClassifyList"
                  :key="item.code"
                  :label="item.name"
                  :value="item.name">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系方式：" :rules="rules.mobileNum" prop="mobileNum">
              <el-input v-model="submitForm.mobileNum" clearable placeholder="请输入联系方式">
                <i class="el-icon-edit el-input__icon" slot="suffix"></i>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电子邮箱：" :rules="rules.email" prop="email">
              <el-input v-model="submitForm.email" clearable placeholder="请输入电子邮箱">
                <i class="el-icon-edit el-input__icon" slot="suffix"></i>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="所在行政区域：" :rules="rules.required" prop="provinceId">
              <el-cascader class=""
                           :props="{ expandTrigger: 'hover' }"
                           :options="addressOptions"
                           v-model="selectedAddressOptions"
                           @change="handleRegionChange">
              </el-cascader>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通讯地址：" prop="address">
              <el-input v-model="submitForm.address" clearable placeholder="请输入通讯地址">
                <i class="el-icon-edit el-input__icon" slot="suffix"></i>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮政编码：" prop="zipCode" :rules="rules.zipCode">
              <el-input v-model="submitForm.zipCode" clearable placeholder="请输入邮政编码">
                <i class="el-icon-edit el-input__icon" slot="suffix"></i>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="最后毕业院校：" prop="graduationCollege">
              <el-input v-model="submitForm.graduationCollege" clearable placeholder="请输入最后毕业院校">
                <i class="el-icon-edit el-input__icon" slot="suffix"></i>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最高学历：" prop="education">
              <el-input v-model="submitForm.education" clearable placeholder="请输入最高学历">
                <i class="el-icon-edit el-input__icon" slot="suffix"></i>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="所在单位：" prop="company">
              <el-input v-model="submitForm.company" clearable placeholder="请输入所在单位">
                <i class="el-icon-edit el-input__icon" slot="suffix"></i>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否在职：" prop="isOnJob">
              <el-select v-model="submitForm.isOnJob" clearable placeholder="请选择">
                <el-option
                  v-for="item in isJobList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="职务：" prop="job">
              <el-input v-model="submitForm.job" clearable placeholder="请输入职务">
                <i class="el-icon-edit el-input__icon" slot="suffix"></i>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="从业年限(年)：" prop="workAge" :rules="rules.positiveInteger">
              <el-input v-model="submitForm.workAge" clearable placeholder="请输入从业年限">
                <i class="el-icon-edit el-input__icon" slot="suffix"></i>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="技术职称：" prop="technicalTitle">
              <el-input v-model="submitForm.technicalTitle" clearable placeholder="请输入技术职称">
                <i class="el-icon-edit el-input__icon" slot="suffix"></i>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="职业资格：" prop="qualifications">
                <div v-for="(item, index) in qualificationList" :key="index" :prop="'classifyList.' + index + '.value'">
                  <span style="display:inline-block;width:30%;">{{item.name}}</span>
                  <span style="display:inline-block;width:30%;">{{item.level}}</span>
                  <el-button @click.prevent="delQualification(item)" style="margin-left:5px;float:right;">删除</el-button>
                </div>
                <el-button size="middle" type="primary" @click="showDialog">增加职业资格</el-button>
            </el-form-item>
            <add-qualification-dialog :showVisible="showVisible" @showDialog="showDialog" @saveSuccess="saveQualification"></add-qualification-dialog>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="工作简历：" prop="workResume">
               <el-input
                type="textarea"
                :rows="5"
                placeholder="请输入工作简历"
                v-model="submitForm.workResume">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="专家备注：" prop="remarks">
              <el-input
                type="textarea"
                :rows="5"
                placeholder="请输入专家备注"
                v-model="submitForm.remarks">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div class="handle-btns">
        <el-button type="primary" @click="submit" v-loading="loading">保 存</el-button>
        <el-button class="cancel" @click="cancel">取 消</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import {expertClassify} from '@/assets/js/expertClassify'
import * as region from '@/assets/js/region'
import { expert } from '@/api/resource'
import { dataDictionary } from '@/api/system'
import { dateFormat, trimStr } from '@/assets/js/common'
import AddQualificationDialog from './dialog/add_qualification_dialog'
import {validMobilePhone, validEmail, validateIdCard, validWhoInteger, validZipcode} from '@/assets/js/validate'
export default {
  name: 'expertEdit',
  components: {
    AddQualificationDialog
  },
  data () {
    // 验证身份证号码
    let validIdNum = (rule, value, callback) => {
      if (value && trimStr(value)) {
        const idNum = trimStr(value)
        if (!validateIdCard(idNum)) {
          callback(new Error('输入有误，请重新录入！'))
        } else {
          let query = {
            enterpriseId: this.$store.getters.authUser.enterpriseId,
            idNumber: idNum,
            isDelete: 0
          }
          if (this.$route.params.objectId) {
            query.excludeId = this.$route.params.objectId
          }
          expert.isNoRepeated(query).then(res => {
            if (!res.data.data) {
              callback(new Error('身份证号码重复，请重新填写'))
            } else {
              callback()
            }
          })
        }
      } else {
        callback()
      }
    }
    return {
      loading: false,
      submitForm: {
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        sex: 0,
        // 专家类别
        expertCategoryList: [[]]
      },
      // 职业资格
      qualificationList: [],
      sexList: [
        {value: 0, label: '保密'},
        {value: 1, label: '男'},
        {value: 2, label: '女'}
      ],
      // 专家分类字典项Id
      dicExpertClassifyId: 10003,
      // 专家分类集合
      dicExpertClassifyList: [],
      // 地址三级联动
      addressOptions: region.CityInfo,
      selectedAddressOptions: [],
      isJobList: [
        {label: '是', value: 1},
        {label: '否', value: 0}
      ],
      // 专家类别集合
      expertClassifyList: expertClassify,
      // 技术资格弹窗
      showVisible: false,
      rules: {
        required: [
          { required: true, message: '选择不能为空', trigger: ['blur', 'change'] }
        ],
        simpleRequiredText: [
          { required: true, message: '输入不能为空', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在1~20个字符', trigger: 'blur' }
        ],
        simple200RequiredText: [
          { required: true, message: '输入不能为空', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在1~200个字符', trigger: 'blur' }
        ],
        idNumber: [
          { required: true, message: '输入不能为空', trigger: 'blur' },
          { validator: validIdNum, trigger: 'blur' }
        ],
        mobileNum: [
          { required: true, message: '输入不能为空', trigger: 'blur' },
          { validator: validMobilePhone, trigger: 'blur' }
        ],
        email: [
          { validator: validEmail, trigger: 'blur' }
        ],
        positiveInteger: [
          {validator: validWhoInteger, trigger: 'blur'},
          { min: 1, max: 2, message: '长度在1~2个字符', trigger: 'blur' }
        ],
        zipCode: [
          { validator: validZipcode, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    /** 初始化数据 */
    initData () {
      expert.queryOne(this.$route.params.objectId).then(res => {
        this.submitForm = res.data.data
        // 数组动态添加，如果视图没反应，则用$set方法
        this.$set(this.submitForm, 'expertCategoryList', [])
        // 包装地区信息
        if (this.submitForm.provinceId) {
          this.wrapRegionAddress()
        }
        // 包装专家类别
        if (this.submitForm.expertCategory) {
          this.wrapExertpCategoryList()
        }
        // 包装职业资格
        if (this.submitForm.qualifications) {
          this.wrapQualificationList()
        }
      })
    },
    /** 初始化专家分类 */
    initExpertClassify () {
      let query = {
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        dictionaryDirectoryId: this.dicExpertClassifyId
      }
      dataDictionary.getDetailList(query).then(res => {
        this.dicExpertClassifyList = res.data.dictionaryDetails
      })
    },
    /** 包装三级联动地址信息 */
    wrapRegionAddress () {
      let address = [this.submitForm.provinceId, this.submitForm.cityId, this.submitForm.countyId]
      this.selectedAddressOptions = address
    },
    /** 包装专家类别 */
    wrapExertpCategoryList () {
      this.submitForm.expertCategory.split(';').forEach(item => {
        let objArr = item.split('-')
        this.submitForm.expertCategoryList.push(objArr)
      })
    },
    /** 包装职业资格 */
    wrapQualificationList () {
      this.submitForm.qualifications.split(';').forEach(item => {
        let objArr = item.split('-')
        this.qualificationList.push({
          name: objArr[0],
          level: objArr[1]
        })
      })
    },
    /** 选择地区 */
    handleRegionChange (value) {
      this.submitForm.provinceId = value[0]
      this.submitForm.cityId = value[1]
      this.submitForm.countyId = value[2]
    },
    /** 添加-专业类别 */
    addExpertCategory () {
      this.submitForm.expertCategoryList.push([])
    },
    /** 删除-专业类别 */
    delExpertCategory (item) {
      let index = this.submitForm.expertCategoryList.indexOf(item)
      this.submitForm.expertCategoryList.splice(index, 1)
    },
    /** 添加-职业资格弹窗 */
    showDialog () {
      this.showVisible = !this.showVisible
    },
    /** 保存职业资格 */
    saveQualification (obj) {
      this.qualificationList.push(obj)
    },
    /** 删除职业资格 */
    delQualification (item) {
      this.qualificationList.splice(this.qualificationList.indexOf(item), 1)
    },
    /** 保存 */
    submit () {
      this.$refs['submitForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认要提交吗?', '提示', {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.loading = true
            // 包装专家类别
            this.submitForm.expertCategory = ''
            for (let i = 0; i < this.submitForm.expertCategoryList.length; i++) {
              if (i !== 0) {
                this.submitForm.expertCategory += ';'
              }
              this.submitForm.expertCategory += this.submitForm.expertCategoryList[i].join('-')
            }
            // 包装职业资格
            if (this.qualificationList.length) {
              this.submitForm.qualifications = ''
              for (let i = 0; i < this.qualificationList.length; i++) {
                if (i !== 0) {
                  this.submitForm.qualifications += ';'
                }
                this.submitForm.qualifications += `${this.qualificationList[i].name}-${this.qualificationList[i].level}`
              }
            }
            delete this.submitForm.expertCategoryList
            // 格式化时间为yyyy-MM-dd HH:mm:ss
            this.submitForm.birthDay = dateFormat(this.submitForm.birthDay, 'yyyy-MM-dd hh:mm:ss')
            expert.update(this.submitForm).then(res => {
              this.loading = false
              if (res.data.resCode === '0000') {
                this.cancel()
              }
            })
          }).catch(() => {
            this.loading = false
            return false
          })
        }
      })
    },
    /** 取消 */
    cancel () {
      this.$store.commit('delete_tabs', this.$route.fullPath)
      this.$router.push({path: `/resource/expert`})
    }
  },
  mounted () {
    // 专家分类初始化
    this.initExpertClassify()
    // 初始化数据
    if (this.$route.params.objectId) {
      this.initData()
    }
  }
}
</script>
<style lang="less">
#expert_edit {
  .handle-btns {
    padding: 10px;
    text-align: center;
  }
}
</style>
