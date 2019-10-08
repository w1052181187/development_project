<template>
  <div class="project-edit" id="project-edit">
    <div class="proinfo-info-box">
      <div class="title-box">
        <p>项目信息</p>
      </div>
      <el-form ref="projectInfo" :model="projectInfoForm" :rules="projectInfoRules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="项目名称:" prop="projectName">
              <el-input v-model="projectInfoForm.projectName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="项目编号:" prop="projectCode">
              <el-input v-model="projectInfoForm.projectCode"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="包组名称:" prop="sectionName">
              <el-input v-model="projectInfoForm.sectionName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="包组编号:" prop="sectionCode">
              <el-input v-model="projectInfoForm.sectionCode"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div class="scaling-box">
      <div class="title-box">
        <p>定标方法</p>
      </div>
      <el-form ref="calibMethod" :model="methodForm" :rules="methodRules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="5">
            <el-form-item label="定标方法:" prop="calibMethodForm.methodType">
              <el-select v-model="methodForm.calibMethodForm.methodType" placeholder="请选择" @change="changeCalibMethed">
                <el-option
                  v-for="item in calibMethedList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.value === methodForm.calibSecondForm.methodType">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="2" class="unit-box" v-if="showPerNum">
            <el-form-item prop="calibMethodForm.number">
              <el-input v-model="methodForm.calibMethodForm.number" :disabled="disabledPerNum" style="width:80px;"></el-input>
              <span class="unit-span">人</span>
            </el-form-item>
          </el-col>
          <el-col :span="4" class="unit-box" v-if="showVoteMethod">
            <el-form-item prop="calibMethodForm.childType">
              <el-select v-model="methodForm.calibMethodForm.childType" placeholder="请选择" @change="changeVoteMethod">
                <el-option
                  v-for="item in voteMethodList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="2" class="unit-box" v-if="showRounds">
            <el-form-item prop="calibMethodForm.nogoRound">
              <el-input-number :controls = "false" :min="2" v-model="methodForm.calibMethodForm.nogoRound" style="width:100px;"></el-input-number>
              <span class="unit-span">轮</span>
            </el-form-item>
          </el-col>
          <el-col :span="4" class="unit-box" v-if="showPriceMethod">
            <el-form-item prop="calibMethodForm.childType">
              <el-select v-model="methodForm.calibMethodForm.childType" placeholder="请选择" @change="changePriceMethod">
                <el-option
                  v-for="item in priceMethodList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4" class="unit-box" v-if="showLowPriceMethod">
            <el-form-item prop="calibMethodForm.lowPriceNum">
              <span class="before-span">第</span>
              <el-input-number :controls = "false" :min="1" v-model="methodForm.calibMethodForm.lowPriceNum" style="width: 70px;"></el-input-number>
              <span class="low-price-span">低价法</span>
            </el-form-item>
          </el-col>
          <el-col :span="3" v-if="isShowAddBtn">
            <el-button size="small" class="btn-blue-bg" @click="addNewBidType" v-if="!showSecond">添 加</el-button>
          </el-col>
        </el-row>
        <el-row :gutter="20" v-if="showBidTime">
          <el-col :span="12">
            <el-form-item label="竞价时间:" prop="methodDate">
              <el-date-picker
                v-model="methodForm.methodDate"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :default-time="['12:00:00']"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="降幅(元):" prop="calibMethodForm.decline">
              <el-input-number :controls="false" v-model="methodForm.calibMethodForm.decline"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" v-if="showSecond">
          <el-col :span="5">
            <el-form-item label="下一轮:" prop="calibSecondForm.methodType">
              <el-select v-model="methodForm.calibSecondForm.methodType" placeholder="请选择" @change="changeSecondCalibMethed">
                <el-option
                  v-for="item in calibMethedList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.value === methodForm.calibMethodForm.methodType">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="2" class="unit-box" v-if="showSecondPerNum">
            <el-form-item prop="calibSecondForm.number">
              <el-input-number :controls = "false" :min="1" v-model="methodForm.calibSecondForm.number" :disabled="true" style="width:80px;"></el-input-number>
              <span class="unit-span">人</span>
            </el-form-item>
          </el-col>
          <el-col :span="4" class="unit-box" v-if="showSecondVoteMethod">
            <el-form-item prop="calibSecondForm.childType">
              <el-select v-model="methodForm.calibSecondForm.childType" placeholder="请选择" @change="changeSecondVoteMethod">
                <el-option
                  v-for="item in voteMethodList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="2" class="unit-box" v-if="showSecondRounds">
            <el-form-item prop="calibSecondForm.nogoRound">
              <el-input-number :controls = "false" :min="2" v-model="methodForm.calibSecondForm.nogoRound" style="width:70px;"></el-input-number>
              <span class="unit-span">轮</span>
            </el-form-item>
          </el-col>
          <el-col :span="4" class="unit-box" v-if="showSecondPriceMethod">
            <el-form-item prop="childType">
              <el-select v-model="methodForm.calibSecondForm.childType" placeholder="请选择" @change="changeSecondPriceMethod">
                <el-option
                  v-for="item in priceMethodList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4" class="unit-box" v-if="showSecondLowPriceMethod">
            <el-form-item prop="calibSecondForm.lowPriceNum">
              <span class="before-span">第</span>
              <el-input-number :controls = "false" :min="1" v-model="methodForm.calibSecondForm.lowPriceNum" style="width: 70px;"></el-input-number>
              <span class="low-price-span">低价法</span>
            </el-form-item>
          </el-col>
          <el-col :span="3">
            <el-button size="small" class="btn-blue-bg" @click="handleDelSecond">删 除</el-button>
          </el-col>
        </el-row>
        <el-row :gutter="20" v-if="showSecondBidTime && showSecond">
          <el-col :span="12">
            <el-form-item label="竞价时间:" prop="secondDate">
              <el-date-picker
                v-model="methodForm.secondDate"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :default-time="['12:00:00']"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="降幅(元):" prop="calibSecondForm.decline">
              <el-input-number :controls="false" v-model="methodForm.calibSecondForm.decline"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div class="sacl-record-box">
      <div class="title-box">
        <p>定标候选人及评标报告</p>
      </div>
      <el-form label-width="140px" :model="bidNameListForm" :rules="bidNameListRules" ref="bidNameListForm">
        <el-row>
          <el-col :span="12">
            <el-form-item label="评标报告:" prop="bidFile" class="form-btx">
               <uploadFile @upLoadFile="upLoadFile" :oldFileName="oldFileName"></uploadFile>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="2">
            <el-form-item label="定标候选人名单:" prop="bidList" class="form-btx">
            </el-form-item>
          </el-col>
          <el-col :span="2">
            <el-button class="btn-warning-bg" size="small" @click="addNameListClick">新增</el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-table
              :data="bidNameListForm.users"
              border
              style="width: 100%"
              class="name-list-table">
              <el-table-column
                prop="userName"
                label="定标候选人名称">
                <template slot-scope='scope'>
                  <el-form-item :prop="'users.' + scope.$index + '.userName'" :rules='bidNameListRules.userName'>
                    <el-input type="text" v-model="scope.row.userName"></el-input>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                prop="initPrice"
                label="中标价格（元）"
                width="200">
                <template slot-scope='scope'>
                  <el-form-item :prop="'users.' + scope.$index + '.initPrice'" :rules='bidNameListRules.initPrice'>
                    <el-input
                      v-model="scope.row.initPrice">
                    </el-input>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                prop="initUserOrder"
                label="定标候选人排名">
                  <template slot-scope='scope'>
                    <el-form-item :prop="'users.' + scope.$index + '.initUserOrder'" :rules='bidNameListRules.initUserOrder'>
                      <el-input
                        type="age"
                        v-model="scope.row.initUserOrder">
                      </el-input>
                    </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                label="操作"
                width="80">
                <template slot-scope="scope">
                  <el-button @click="handleClick(scope.$index, 'del')" type="text" size="small">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
      </el-form>
      <div class="submit-box">
        <el-button type="primary" size="small" @click="submitForm" :loading="loadingBtn">提 交</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import uploadFile from '@/components/upload/publicFileUpload'
import {projectInfo} from '@/api'
// import {sumType, Numbertype} from '@/assets/js/validate'
export default {
  name: 'project-list',
  data () {
    var validChildMethod = (rule, value, callback) => {
      if (!value) {
        let msg
        if (this.showVoteMethod) {
          msg = '请选择票决法'
        }
        if (this.showPriceMethod) {
          msg = '请选择低价法'
        }
        callback(new Error(msg))
      } else {
        callback()
      }
    }
    var validSecondMethod = (rule, value, callback) => {
      if (!value) {
        let msg
        if (this.showSecondVoteMethod) {
          msg = '请选择票决法'
        }
        if (this.showSecondPriceMethod) {
          msg = '请选择低价法'
        }
        callback(new Error(msg))
      } else {
        callback()
      }
    }
    var validateBidPrice = (rule, value, callback) => {
      if ((!(/^[1-9]\d{0,14}(\.\d{1,2})?$/).test(value))) {
        callback(new Error('中标价格只能输入小数点前不超过14位且小数点后不超过2位的数字'))
      } else {
        callback()
      }
    }
    var validateBidLowPrice = (rule, value, callback) => {
      let validate = (/^[1-9]+\d*$/).test(value)
      if (!validate) {
        callback(new Error('降幅只能输入大于0的正整数'))
      } else {
        callback()
      }
    }
    // 排名
    var validateBidScore = (rule, value, callback) => {
      if ((!(/^([1-9]\d?|100)$/).test(value))) {
        callback(new Error('排名只能输入1-100正整数'))
      } else {
        callback()
      }
    }
    var validateBidPerNum = (rule, value, callback) => {
      if ((!(/^([1-9]\d?|100)$/).test(value))) {
        callback(new Error('请输入1-100整数'))
      } else {
        callback()
      }
    }
    return {
      disabledPerNum: true,
      loadingBtn: false, // 提交加载中
      bidNameListForm: {
        users: []
      },
      bidNameListRules: {
        userName: [
          {required: true, message: '请填写定标候选人名称', trigger: 'blur'},
          {max: 200, message: '长度200个字符', trigger: 'blur'}
        ],
        initPrice: [
          {required: true, message: '请填写中标价格', trigger: 'blur'},
          {validator: validateBidPrice}
        ],
        initUserOrder: [
          {required: true, message: '请填写排名', trigger: 'blur'},
          {validator: validateBidScore}
        ]
      },
      type: 'add',
      oldFileName: '',
      form: {
        project: {},
        scalingSystemMethods: [],
        users: []
      },
      projectInfoForm: {
        projectName: '',
        projectCode: '',
        sectionName: '',
        sectionCode: ''
      }, // 项目信息-表单
      projectInfoRules: {
        projectName: [
          { required: true, message: '请输入项目名称', trigger: ['blur', 'change'] },
          { max: 600, message: '长度600个字符', trigger: 'blur' }
        ],
        projectCode: [
          { required: true, message: '请输入项目编号', trigger: ['blur', 'change'] },
          { max: 200, message: '长度200个字符', trigger: 'blur' }
        ],
        sectionName: [
          { required: true, message: '请输入包组名称', trigger: ['blur', 'change'] },
          { max: 500, message: '长度500个字符', trigger: 'blur' }
        ],
        sectionCode: [
          { required: true, message: '请输入包组编号', trigger: ['blur', 'change'] },
          { max: 20, message: '长度20个字符', trigger: 'blur' }
        ]
      },
      // 定标方法-表单
      methodForm: {
        methodDate: [],
        secondDate: [],
        calibMethodForm: {
          methodType: '',
          number: '',
          childType: '',
          // nogoRound: '',
          // lowPriceNum: '',
          // beginDate: '',
          // endDate: '',
          // decline: null,
          methodOrder: 1
        },
        calibSecondForm: {
          methodType: '',
          number: '',
          childType: '',
          // nogoRound: '',
          // lowPriceNum: '',
          // beginDate: '',
          // endDate: '',
          // decline: null,
          methodOrder: 2
        }
      },
      methodRules: {
        'calibMethodForm.methodType': [
          { required: true, message: '请选择定标方法', trigger: ['blur', 'change'] }
        ],
        'calibMethodForm.number': [
          { required: true, message: '请输入抽签人数', trigger: ['blur', 'change'] },
          {validator: validateBidPerNum}
        ],
        'calibMethodForm.childType': [
          { required: true, validator: validChildMethod, trigger: ['blur', 'change'] }
        ],
        'calibMethodForm.nogoRound': [
          { required: true, message: '请输入轮数', trigger: ['blur', 'change'] }
        ],
        'calibMethodForm.lowPriceNum': [
          { required: true, message: '请输入低价法数', trigger: ['blur', 'change'] }
        ],
        methodDate: [
          { required: true, message: '请选择竞价时间', trigger: ['blur', 'change'] }
        ],
        'calibMethodForm.decline': [
          { required: true, message: '请输入降幅', trigger: ['blur', 'change'] },
          {validator: validateBidLowPrice}
        ],
        'calibSecondForm.methodType': [
          { required: true, message: '请选择定标方法', trigger: ['blur', 'change'] }
        ],
        'calibSecondForm.number': [
          { required: true, message: '请输入抽签人数', trigger: ['blur', 'change'] },
          {validator: validateBidPerNum}
        ],
        'calibSecondForm.childType': [
          { required: true, validator: validSecondMethod, trigger: ['blur', 'change'] }
        ],
        'calibSecondForm.nogoRound': [
          { required: true, message: '请输入轮数', trigger: ['blur', 'change'] }
        ],
        'calibSecondForm.lowPriceNum': [
          { required: true, message: '请输入低价法数', trigger: ['blur', 'change'] }
        ],
        secondDate: [
          { required: true, message: '请选择竞价时间', trigger: 'change' }
        ],
        'calibSecondForm.decline': [
          { required: true, message: '请输入降幅', trigger: ['blur', 'change'] },
          {validator: validateBidLowPrice}
        ]
      },
      calibMethedList: [
        {value: 1, label: '直接抽签法'},
        {value: 2, label: '票决法'},
        {value: 3, label: '价格竞争法'},
        {value: 4, label: '集体议事法'}
      ],
      voteMethodList: [
        {value: 1, label: '直接票决法'},
        {value: 2, label: '逐轮票决法'},
        {value: 3, label: '累积投票法'}
      ],
      priceMethodList: [
        {value: 1, label: '最低投标价法'},
        {value: 2, label: '次低价法'},
        {value: 3, label: '平均值法'},
        {value: 4, label: '第N低价法'}
      ],
      showPerNum: false, // 人数
      showVoteMethod: false, // 票决方法
      showRounds: false, // 第几轮
      showPriceMethod: false, // 价格竞争法
      showLowPriceMethod: false, // 第几低价法
      showSecond: false,
      // 第二轮
      showSecondPerNum: false, // 人数
      showSecondVoteMethod: false, // 票决方法
      showSecondRounds: false, // 第几轮
      showSecondPriceMethod: false, // 价格竞争法
      showSecondLowPriceMethod: false, // 第几低价法
      showBidTime: false, // 竞价时间-显示隐藏
      showSecondBidTime: false, // 竞价时间-显示隐藏
      perNum: 2,
      // 是否显示添加按钮（第一轮为竞价时，不显示）
      isShowAddBtn: true
    }
  },
  created () {},
  updated () {
    this.$nextTick(function () {
      this.$set(this.methodForm, 'calibSecondForm.number', 1)
    })
  },
  methods: {
    // 删除
    handleDelSecond () {
      this.showSecond = false
      this.disabledPerNum = false
      if (this.methodForm.calibMethodForm.methodType === 1) {
        this.$nextTick(function () {
          this.$set(this.methodForm.calibMethodForm, 'number', 1)
        })
        this.disabledPerNum = true
      }
      this.methodForm.calibSecondForm.methodType = ''
    },
    handleClick (index, type) {
      this.bidNameListForm.users.splice(index, 1)
    },
    /* 添加新的定标方式 */
    addNewBidType () {
      this.showSecond = true
      this.disabledPerNum = false
      this.showSecondPerNum = false
      this.showSecondVoteMethod = false
      this.showSecondRounds = false
      this.showSecondLowPriceMethod = false
      this.methodForm.calibSecondForm.number = 1
    },
    /** 改变定标方法 */
    changeCalibMethed (value) {
      this.methodForm.calibMethodForm = {
        methodType: '',
        number: '',
        childType: '',
        decline: '',
        methodOrder: 1
      }
      if (value === 1) {
        this.isShowAddBtn = true
        // 先初始化在赋值
        this.methodForm.calibMethodForm.number = 1
        this.methodForm.calibMethodForm.methodType = value
        this.showPerNum = true
        // 其他设置为false
        this.showVoteMethod = false
        this.showRounds = false
        this.showPriceMethod = false
        this.showLowPriceMethod = false
        this.showVoteMethod = false
        this.showBidTime = false
      } else if (value === 2) {
        this.isShowAddBtn = true
        // 先初始化在赋值
        this.methodForm.calibMethodForm.childType = 1
        this.methodForm.calibMethodForm.methodType = value
        this.showVoteMethod = true
        // 其他设置为false
        this.showPerNum = false
        this.showRounds = false
        this.showPriceMethod = false
        this.showLowPriceMethod = false
        this.showBidTime = false
      } else if (value === 3) {
        // 先初始化在赋值
        this.methodForm.calibMethodForm.childType = 1
        this.methodForm.calibMethodForm.methodType = value
        this.methodForm.calibMethodForm.decline = 1
        this.showPriceMethod = true
        this.showBidTime = true
        // 其他设置为false
        this.showPerNum = false
        this.showRounds = false
        this.showVoteMethod = false
        this.showLowPriceMethod = false
        this.showVoteMethod = false
        // 第一轮为竞价时，不显示第二轮
        if (this.showSecond) {
          this.showSecond = false
          this.methodForm.calibSecondForm = {
            methodType: '',
            childType: '',
            methodOrder: 2
          }
        }
        // 第一轮为竞价时，不能添加第二轮
        if (this.isShowAddBtn) {
          this.isShowAddBtn = false
        }
      } else if (value === 4) {
        this.isShowAddBtn = true
        // 先初始化在赋值
        this.methodForm.calibMethodForm.methodType = value
        this.showPerNum = false
        this.showVoteMethod = false
        this.showRounds = false
        this.showPriceMethod = false
        this.showLowPriceMethod = false
        this.showVoteMethod = false
        this.showBidTime = false
      } else {
        this.methodForm.calibMethodForm = this.$options.data().methodForm.calibMethodForm
      }
    },
    /** 票决法-改变 */
    changeVoteMethod (value) {
      if (value === 2) {
        this.methodForm.calibMethodForm.nogoRound = 2
        this.showRounds = true
      } else {
        this.methodForm.calibMethodForm.nogoRound = ''
        this.showRounds = false
      }
    },
    /** 价格竞争法-改变 */
    changePriceMethod (value) {
      if (value === 4) {
        this.methodForm.calibMethodForm.lowPriceNum = 3
        this.showLowPriceMethod = true
      } else {
        this.methodForm.calibMethodForm.lowPriceNum = ''
        this.showLowPriceMethod = false
      }
    },
    /** 第二轮 */
    changeSecondCalibMethed (value) {
      this.methodForm.calibSecondForm = {
        methodType: '',
        number: '',
        childType: '',
        decline: '',
        methodOrder: 2
      }
      if (value === 1) {
        this.showSecondPerNum = true
        // 先初始化在赋值
        this.methodForm.calibSecondForm.number = 1
        this.methodForm.calibSecondForm.methodType = value
        // 其他设置为false
        this.showSecondVoteMethod = false
        this.showSecondRounds = false
        this.showSecondPriceMethod = false
        this.showSecondLowPriceMethod = false
        this.showSecondVoteMethod = false
        this.showSecondBidTime = false
      } else if (value === 2) {
        // 先初始化在赋值
        this.methodForm.calibSecondForm.methodType = value
        this.methodForm.calibSecondForm.childType = 1
        this.showSecondVoteMethod = true
        // 其他设置为false
        this.showSecondPerNum = false
        this.showSecondRounds = false
        this.showSecondPriceMethod = false
        this.showSecondLowPriceMethod = false
        this.showSecondBidTime = false
      } else if (value === 3) {
        // 先初始化在赋值
        this.methodForm.calibSecondForm.methodType = value
        this.methodForm.calibSecondForm.childType = 1
        this.methodForm.calibSecondForm.decline = 1
        this.showSecondPriceMethod = true
        this.showSecondBidTime = true
        // 其他设置为false
        this.showSecondPerNum = false
        this.showSecondRounds = false
        this.showSecondVoteMethod = false
        this.showSecondLowPriceMethod = false
      } else if (value === 4) {
        // 先初始化在赋值
        this.methodForm.calibSecondForm.methodType = value
        this.showSecondPerNum = false
        this.showSecondVoteMethod = false
        this.showSecondRounds = false
        this.showSecondPriceMethod = false
        this.showSecondLowPriceMethod = false
        this.showSecondVoteMethod = false
        this.showSecondBidTime = false
      }
    },
    changeSecondVoteMethod (value) {
      if (value === 2) {
        this.showSecondRounds = true
        this.methodForm.calibSecondForm.nogoRound = 2
      } else {
        this.showSecondRounds = false
        this.methodForm.calibSecondForm.nogoRound = ''
      }
    },
    changeSecondPriceMethod (value) {
      if (value === 4) {
        this.methodForm.calibSecondForm.lowPriceNum = 3
        this.showSecondLowPriceMethod = true
      } else {
        this.methodForm.calibSecondForm.lowPriceNum = ''
        this.showSecondLowPriceMethod = false
      }
    },
    // 定标候选人新增
    addNameListClick () {
      // 投标人
      this.bidNameListForm.users.push({initRoleId: 5})
    },
    upLoadFile (file) {
      if (file.length === 0) {
        this.projectInfoForm.fileName = ''
        this.projectInfoForm.fileUrl = ''
      } else {
        this.projectInfoForm.fileName = file[0].fileName
        this.projectInfoForm.fileUrl = file[0].relativePath
      }
      this.oldFileName = this.projectInfoForm.fileName
    },
    // 提交
    submitForm () {
      // 加载动画
      // 校验项目信息必填
      let proInfo = this.validateProInfo()
      // 校验校验定标方式必填
      let bidType = this.validateBidType()
      // 校验定标候选人必填
      let candidate = this.validateCandidate()
      // 如果校验通过
      if (proInfo && bidType && candidate) {
        /** 整合表单信息 */
        let allForm = this.concatFromInfo()
        // 校验降幅与中标价格（降幅不能大于等于中标价格）
        let compare = this.validateDeclineAndBidPrice(allForm)
        // 填写是否正确检验
        let isRight = this.validateInputRight()
        if (isRight && compare) {
          this.loadingBtn = true
          if (this.type === 'add') { // 如果是新增
            projectInfo.add(allForm).then((res) => {
              this.loadingBtn = false
              if (res.data.resCode === '0000') {
                this.$router.push({path: '/admin/proinfo'})
              } else {
                return false
              }
            })
          } else if (this.type === 'edit') { // 如果是修改
            projectInfo.update(allForm).then((res) => {
              this.loadingBtn = false
              if (res.data.resCode === '0000') {
                this.$router.push({path: '/admin/proinfo'})
              } else {
                return false
              }
            })
          }
        }
      }
    },
    // 校验降幅与中标价格
    validateDeclineAndBidPrice (form) {
      let reuslt = false
      if (form.scalingSystemMethods && form.scalingSystemMethods[0]) {
        // 降幅
        let decline = Number(form.scalingSystemMethods[0].decline)
        // 遍历中标价格list
        let list = form.users
        list.map(item => {
          if (decline >= Number(item.initPrice)) {
            this.$message({
              type: 'warning',
              message: '降幅不能大于等于中标价格'
            })
            return reuslt
          } else {
            reuslt = true
          }
        })
      }
      return reuslt
    },
    // 填写是否正确检验
    validateInputRight () {
      let isRight = false
      // 定标候选人
      let list = this.bidNameListForm.users.filter(item => JSON.stringify(item) !== '{}' && item.userName && item.initPrice && item.initUserOrder)
      // 第一轮
      if (this.methodForm.calibMethodForm.number >= list.length) {
        this.$message({
          type: 'warning',
          message: '抽签人数应小于候选人，请重新输入'
        })
        isRight = false
        return isRight
      } else {
        isRight = true
      }
      if (this.methodForm.calibMethodForm.number === 1 && this.methodForm.calibMethodForm.methodType === 1 && this.methodForm.calibSecondForm.methodType !== '' && this.methodForm.calibSecondForm.methodType !== undefined) {
        this.$message({
          type: 'warning',
          message: '请检查抽签人数，抽签人数应大于1'
        })
        isRight = false
        return isRight
      } else {
        isRight = true
      }
      if (this.methodForm.calibMethodForm.lowPriceNum > list.length) {
        this.$message({
          type: 'warning',
          message: '第N低价法人数应小于等于候选人，请重新输入'
        })
        isRight = false
        return isRight
      } else {
        isRight = true
      }
      // 第二轮
      if (this.showSecond) {
        // 表示人数（第一轮有抽签人数或者第N低价法数，则为相关数，否则为候选人人数）
        let tempNum = list.length
        let msg = '抽签人数应小于候选人，请重新输入'
        if (this.showSecondPerNum) {
          // 如果第一轮有第N低价法，抽签人数需要小于等于N
          if (this.showPriceMethod) {
            tempNum = this.methodForm.calibMethodForm.lowPriceNum + 1
            msg = '抽签人数应小于等于第N低价法人数，请重新输入'
          }
          if (this.methodForm.calibSecondForm.number >= tempNum) {
            this.$message({
              type: 'warning',
              message: msg
            })
            isRight = false
            return isRight
          } else {
            isRight = true
          }
        } else {
          if (this.showSecondLowPriceMethod) {
            msg = '第N低价法人数应小于候选人，请重新输入'
            // 如果第一轮有抽签法，N需要小于等于抽签人数
            if (this.showPerNum) {
              tempNum = this.methodForm.calibMethodForm.number + 1
              msg = '第N低价法人数应小于等于抽签人数，请重新输入'
            }
            if (this.methodForm.calibSecondForm.lowPriceNum >= tempNum) {
              this.$message({
                type: 'warning',
                message: msg
              })
              isRight = false
              return isRight
            } else {
              isRight = true
            }
          }
        }
      }
      if (!this.form.project.fileName || !this.form.project.fileUrl) {
        this.$message({
          type: 'warning',
          message: '请选择评标报告'
        })
        isRight = false
        return isRight
      } else {
        isRight = true
      }
      // 检查降幅
      if (this.form.scalingSystemMethods[0] && this.form.scalingSystemMethods[0].decline) {
        if (this.form.scalingSystemMethods[0].decline === 0) {
          this.$message({
            type: 'warning',
            message: '降幅不能小于0'
          })
          isRight = false
          return isRight
        } else {
          isRight = true
        }
      }
      if (this.form.scalingSystemMethods[1] && this.form.scalingSystemMethods[1].decline) {
        if (this.form.scalingSystemMethods[1].decline === 0) {
          this.$message({
            type: 'warning',
            message: '降幅不能小于0'
          })
          isRight = false
          return isRight
        } else {
          isRight = true
        }
      }
      return isRight
    },
    detail (objectId) {
      projectInfo.detail(objectId).then((res) => {
        if (res.data.resCode === '0000') {
          this.projectInfoForm = res.data.ProjectView.project
          let methods = res.data.ProjectView.scalingSystemMethods
          methods.map((item) => {
            if (item.methodOrder === 1) {
              this.changeCalibMethed(item.methodType)
              if (item.methodType === 2) {
                this.changeVoteMethod(item.childType)
              }
              if (item.methodType === 3) {
                this.changePriceMethod(item.childType)
              }
              if (item.beginDate) {
                this.methodForm.methodDate.push(item.beginDate)
              }
              if (item.endDate) {
                this.methodForm.methodDate.push(item.endDate)
              }
              this.methodForm.calibMethodForm = item
            } else if (item.methodOrder === 2) {
              this.changeSecondCalibMethed(item.methodType)
              if (item.methodType === 2) {
                this.changeSecondVoteMethod(item.childType)
              }
              if (item.methodType === 3) {
                this.changeSecondPriceMethod(item.childType)
              }
              if (item.beginDate) {
                this.methodForm.secondDate.push(item.beginDate)
              }
              if (item.endDate) {
                this.methodForm.secondDate.push(item.endDate)
              }
              this.methodForm.calibSecondForm = item
              this.showSecond = true
              // console.log(this.methodForm.secondDate)
            }
          })
          this.bidNameListForm.users = res.data.ProjectView.users
          this.oldFileName = this.projectInfoForm.fileName
          this.relativePath = this.projectInfoForm.fileUrl
        }
      })
    },
    // 整合表单信息
    concatFromInfo () {
      // 项目信息
      this.form.project = this.projectInfoForm
      // 定标方式
      this.form.scalingSystemMethods = []
      if (this.methodForm.methodDate) {
        this.methodForm.calibMethodForm.beginDate = this.methodForm.methodDate[0]
        this.methodForm.calibMethodForm.endDate = this.methodForm.methodDate[1]
      }
      if (this.methodForm.secondDate) {
        this.methodForm.calibSecondForm.beginDate = this.methodForm.secondDate[0]
        this.methodForm.calibSecondForm.endDate = this.methodForm.secondDate[1]
      }
      this.form.scalingSystemMethods.push(this.methodForm.calibMethodForm)
      if (this.showSecond) {
        this.form.scalingSystemMethods.push(this.methodForm.calibSecondForm)
      }
      // 定标候选人
      let list = this.bidNameListForm.users.filter(item => JSON.stringify(item) !== '{}' && item.userName && item.initPrice && item.initUserOrder)
      if (!list || list.length < this.perNum) {
        this.$message({
          type: 'warning',
          message: '请添加定标候选人，至少' + this.perNum + '人'
        })
        return
      }
      this.form.users = list
      return this.form
    },
    // 校验项目信息
    validateProInfo () {
      let result = false
      this.$refs['projectInfo'].validate((valid) => {
        if (valid) {
          result = true
        }
      })
      return result
    },
    // 校验定标方式
    validateBidType () {
      let result = false
      this.$refs['calibMethod'].validate((valid) => {
        if (valid) {
          result = true
        }
      })
      return result
    },
    // 校验定标候选人
    validateCandidate () {
      let result = false
      this.$refs['bidNameListForm'].validate((valid) => {
        if (valid) {
          result = true
        }
      })
      return result
    }
  },
  components: {
    uploadFile
  },
  mounted () {
    this.type = this.$route.params.type
    let objectId = this.$route.query.objectId
    if (this.type === 'edit' && Number(objectId) !== 0) {
      this.detail(objectId)
    }
  }
}
</script>
<style lang="scss">
#project-edit {
  .proinfo-info-box,
  .scaling-box,
  .sacl-record-box {
    background: #fff;
    margin-top: 15px;
    padding: 10px;
  }
  .sacl-record-box .form-btx .el-form-item__label::before {
    content: '*';
    color: #f56c6c;
    margin-right: 4px;
  }
  .name-list-table {
    .cell {
      text-overflow: unset;
    }
    .el-form-item__content {
      margin-left: 0 !important;
    }
    .el-form-item {
      margin-bottom: 0 !important;
    }
  }
  .el-table .cell {
    text-overflow: unset !important;
  }
  .scaling-box {
    .el-form-item__content {
      text-align: left;
    }
  }
  .submit-box {
    margin: 15px;
  }
  .unit-box {
    .el-form-item__content {
      margin-left: 0 !important;
    }
  }
  .unit-span {
    position: absolute;
    top: 0;
    left: 115px;
  }
  .before-span {
    position: absolute;
    top: 0;
    left: -18px;
  }
  .low-price-span {
    position: absolute;
    top: 0;
    left: 75px;
  }
}
</style>
