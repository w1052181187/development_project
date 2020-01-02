<template>
  <!-- 资格预审会 -->
  <div class="admin-layout" id="qualpre-page">
    <div class="qualpre-cont">
      <div class="qualpre-tit">
        <span>资格预审会</span>
      </div>
      <div class="qualpre-box">
        <!-- 折叠面板start -->
        <el-collapse v-model="collapseActiveName" accordion >
          <el-collapse-item  v-for="(section, index) in sectionData" :name=section.code :key="index">
            <template slot="title">
              <span @click="getDataBySection(section.code, index)" style="width:100%;display:inherit;position:relative;z-index:1;">{{section.name}}</span>
            </template>
            <div class="qualpre-time">
              <el-form :model="openRuleForm"  label-width="120px" class="form-span">
                <el-form-item label="资格预审会时间:">
                  <span>{{openRuleForm.qualifiPreTime | filterQualifiPreTime}}</span>
                </el-form-item>
              </el-form>
            </div>
            <!-- el-tabs切换 -->
            <el-tabs v-model="tabsActiveName" type="card" style="margin-top: 10px;" class="card-tabs" @tab-click="tabsClick">
              <!-- 评标专家start -->
              <el-tab-pane label="评标专家" name="first">
                <div class="add-expert-btn">
                  <el-button @click="addExpertBtn" type="warning" size="medium" icon="el-icon-plus" v-if="dialogEdit && progressStage === 3">添加专家</el-button>
                </div>
                <div class="expert-table">
                  <el-table
                    border
                    :data="expertTableData"
                    :row-class-name="tableRowClassName"
                    style="width: 100%">
                    <el-table-column
                      type="index"
                      label="序号"
                      align="center"
                      :index="indexMethod"
                      width="55">
                    </el-table-column>
                    <el-table-column
                      label="姓名"
                      prop="name"
                      align="center">
                    </el-table-column>
                    <el-table-column
                      prop="idCard"
                      label="身份证号"
                      align="center"
                      width="180">
                    </el-table-column>
                    <el-table-column
                      prop="sex"
                      label="性别"
                      align="center"
                      :formatter="filterSex"
                      width="80">
                    </el-table-column>
                    <el-table-column
                      prop="enterpriseName"
                      label="单位"
                      align="left"
                      header-align="center">
                    </el-table-column>
                    <el-table-column
                      prop="cityId"
                      label="地区"
                      align="left"
                      header-align="center"
                      :formatter="filterCity">
                    </el-table-column>
                    <el-table-column
                      label="专业"
                      align="left"
                      header-align="center"
                      width="260">
                      <template slot-scope="scope">
                        <p v-for="(item, index) in scope.row.expertclassify" :key="index">{{item}}</p>
                      </template>
                    </el-table-column>
                    <el-table-column
                      label="专业类别"
                      align="center"
                      prop="expertSources"
                      :formatter="filterSources">
                    </el-table-column>
                    <el-table-column
                      align="center"
                      label="操作">
                      <template slot-scope="scope">
                        <el-button @click="handleTableClick(scope.row,'look')" type="text" size="medium">查看</el-button>
                        <el-button @click="handleTableClick(scope.row,'edit')" type="text" size="medium" v-if="dialogEdit && progressStage === 3">修改</el-button>
                        <el-button @click="handleTableClick(scope.row,'del')" type="text" size="medium" v-if="dialogEdit && progressStage === 3">删除</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </el-tab-pane>
              <!-- 评标专家end -->

              <!-- 预审结果start -->
              <el-tab-pane label="预审结果" name="second">
                  <div class="add-preresult-btn">
                    <el-button @click="preResultBtn" type="warning" size="medium" icon="el-icon-setting" v-if="dialogEdit && progressStage === 3">结果设置</el-button>
                  </div>
                  <div class="preresult-table" style="margin-top: 20px;">
                    <el-table
                      border
                      :data="preresultTableData"
                      :row-class-name="tableRowClassName"
                      style="width: 100%">
                      <el-table-column
                        type="index"
                        label="序号"
                        :index="indexResultMethod"
                        width="55">
                      </el-table-column>
                      <el-table-column
                        label="报名人名称"
                        prop="bidderName"
                        header-align="center"
                        align="left">
                      </el-table-column>
                      <el-table-column
                        prop="isPrequalification"
                        label="是否参与预审"
                        align="center"
                        :formatter="filterIsPrequalification">
                      </el-table-column>
                      <el-table-column
                        prop="score"
                        label="得分"
                        align="center">
                      </el-table-column>
                      <el-table-column
                        prop="isPassed"
                        label="是否通过"
                        align="center"
                        :formatter="filterIsPassed">
                      </el-table-column>
                      <el-table-column
                        prop="fileInformation"
                        label="申请文件"
                        align="center">
                        <template slot-scope="scope">
                          <el-button
                            v-if="scope.row.fileInformation"
                            type="text" @click="downloadFile(scope.row.fileInformation.fileName, scope.row.fileInformation.relativePath)">
                            <span v-if="scope.row.fileInformation" style="display:inline-block;float:left;padding-right:20px;">
                            {{scope.row.fileInformation.fileName}}
                            </span>
                          </el-button>
                        </template>
                      </el-table-column>
                      <el-table-column
                        prop="noticeFileInformation"
                        label="通知书"
                        align="center">
                        <template slot-scope="scope">
                          <el-button
                            v-if="scope.row.noticeFileInformation"
                            type="text" @click="downloadFile(scope.row.noticeFileInformation.fileName,scope.row.noticeFileInformation.relativePath)">
                            <span
                              v-if="scope.row.noticeFileInformation"
                              style="display:inline-block;float:left;padding-right:20px;">
                              {{scope.row.noticeFileInformation.fileName}}
                            </span>
                          </el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                    <el-form label-width="100px" :model="resultForm" :rules="resultRules" ref="preresultForm">
                      <el-form-item label="专家抽签结果:" prop="expertResult" ref="expertResult" label-width="120px">
                        <el-upload
                          class="upload-demo"
                          :action="upLoad"
                          :on-success="handleExpertSuccess"
                          :before-upload="expertBeforeUpload"
                          :show-file-list="false"
                          :file-list="fileExpertList"
                          v-if="dialogEdit">
                          <el-button size="small" type="success" class="upload-btn" >上传附件</el-button>
                        </el-upload>
                        <!-- 上传附件列表 -->
                        <div class="apply-upload">
                          <el-row>
                            <el-col :span="12">
                              <span class="file-list" v-for="(item, index) in fileExpertList" :key="index" v-if="item.fileName || false">
                                <el-button
                                  type="text"
                                  style="padding-right: 20px;"
                                  @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}
                                </el-button>
                                <!-- 删除附件列表 -->
                                <i class="el-icon-error upload-file" @click="expertFileClickDel(index)" v-if="dialogEdit && progressStage === 3"></i>
                              </span>
                            </el-col>
                          </el-row>
                        </div>
                      </el-form-item>
                      <el-form-item label="资格预审报告:" prop="qualPreNotice" ref="qualPreNotice" label-width="120px">
                        <el-upload
                          class="upload-demo"
                          :action="upLoad"
                          :on-success="handleQualPreSuccess"
                          :before-upload="beforeUpload"
                          :show-file-list="false"
                          :file-list="fileQualPretList"
                          v-if="dialogEdit">
                          <el-button size="small" type="success" class="upload-btn" >上传附件</el-button>
                        </el-upload>
                        <!-- 上传附件列表 -->
                        <div class="apply-upload">
                          <el-row>
                            <el-col :span="12">
                              <span class="file-list" v-for="(item, index) in fileQualPretList" :key="index" v-if="item.fileName || false">
                                <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                                <!-- 删除附件列表 -->
                                <i class="el-icon-error upload-file" @click="QualPreFileClickDel(index)" v-if="dialogEdit  && progressStage === 3"></i>
                              </span>
                            </el-col>
                          </el-row>
                        </div>
                      </el-form-item>
                    </el-form>
                  </div>
                  <div class="save-btn">
                    <el-button type="primary" @click="saveResult('preresultForm')" v-if="dialogEdit && progressStage === 3">保 存</el-button>
                  </div>
              </el-tab-pane>
              <!-- 预审结果end -->
            </el-tabs>
          </el-collapse-item>
        </el-collapse>
        <!-- 折叠面板end -->
        <div class="bottom-submit-btn">
          <el-button type="success" @click="submitForm" v-if="dialogEdit  && progressStage === 3">提 交</el-button>
        </div>
      </div>
    </div>

    <!-- 弹窗区域start -->
    <div class="qualpre-dialog">
      <!-- 新增和编辑弹窗start -->
      <el-dialog
        :title="dialogTitle"
        :visible.sync="dialogVisible"
        width="35%">
        <div class="qualpre-form">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
            <el-form-item label="身份证号:" prop="idCard" >
              <el-input v-model="ruleForm.idCard" style="width: 260px" @blur="idCardBlur"></el-input>
            </el-form-item>
            <el-form-item label="姓名:" prop="name">
              <el-input v-model="ruleForm.name" style="width: 260px" :disabled="disabled"></el-input>
            </el-form-item>
            <el-form-item label="性别:" prop="sex">
              <el-select v-model="ruleForm.sex" clearable placeholder="请选择所属性别" style="width: 260px" :disabled="disabled">
                <el-option
                  v-for="item in sexList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="单位:" prop="enterpriseName">
              <el-input v-model="ruleForm.enterpriseName" style="width: 260px" :disabled="disabled"></el-input>
            </el-form-item>
            <el-form-item label="地区:" prop="cityId">
              <el-select v-model="ruleForm.cityId" clearable placeholder="请选择所在地区" style="width: 260px" :disabled="disabled">
                <el-option
                  v-for="item in cityIdList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="专家类别:" prop="expertSources">
              <el-select v-model="ruleForm.expertSources" clearable placeholder="请选择专家类别" style="width: 260px" :disabled="disabled">
                <el-option
                  v-for="item in typeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="专业:" prop="classify">
              <el-col :span="24">
                <el-cascader
                  :disabled="disabled"
                  style="width: 260px;"
                  expand-trigger="hover"
                  :clearable="true"
                  change-on-select
                  :options="cascaderSelectData"
                  v-model="ruleForm.classify"
                  @change="handleMajorChange">
                </el-cascader>
              </el-col>
            </el-form-item>
            <el-form-item class="table-add">
              <el-row>
                <el-col :span="24">
                  <div class="edit-select-box">
                    <el-table
                      :show-header="false"
                      :data="backData"
                      style="width: 100%">
                      <el-table-column
                        prop="professional"
                        label="专业"
                        width="300">
                        <template slot-scope="scope">
                          <el-cascader
                            style="width: 260px;"
                            expand-trigger="hover"
                            :clearable="true"
                            change-on-select
                            :options="cascaderSelectData"
                            v-model="scope.row.professional"
                            @change="handleMajorChange">
                          </el-cascader>
                        </template>
                      </el-table-column>
                      <el-table-column
                        label="操作">
                        <template slot-scope="scope">
                          <el-button
                            style="margin-left: 50px;"
                            @click="deleteRow(scope.$index, selectTableData)"
                            type="text"
                            size="small">
                            移除
                          </el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                  </div>
                  <div class="add-select-btn">
                    <el-button type="warning" icon="el-icon-plus" @click="addSelectClick" :disabled="disabled">新增</el-button>
                  </div>
                </el-col>
              </el-row>
            </el-form-item>
          </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="saveExpertClick('ruleForm')">保 存</el-button>
          <el-button @click="cancelExpertClick()">取 消</el-button>
        </span>
      </el-dialog>
      <!-- 新增和编辑弹窗end -->
      <!-- 专家-查看弹窗start -->
      <el-dialog
        title="查看"
        :visible.sync="lookDialogVisible"
        width="30%">
        <div class="qualpre-form">
          <el-form :model="lookRuleForm" label-width="100px">
            <el-form-item label="姓名:" prop="name">
              <span>{{lookRuleForm.name}}</span>
            </el-form-item>
            <el-form-item label="身份证号:" prop="idCard">
              <span>{{lookRuleForm.idCard}}</span>
            </el-form-item>
            <el-form-item label="性别:" prop="sex">
              <span>{{lookRuleForm.sex | filterSex}}</span>
            </el-form-item>
            <el-form-item label="单位:" prop="enterpriseName">
              <span>{{lookRuleForm.enterpriseName}}</span>
            </el-form-item>
            <el-form-item label="地区:" prop="cityId">
              <span>{{lookRuleForm.cityId | filterCity}}</span>
            </el-form-item>
            <el-form-item label="专家类别:" prop="expertSources">
              <span>{{lookRuleForm.expertSources |filterSources}}</span>
            </el-form-item>
            <el-form-item label="专业:" prop="expertclassify">
              <span>{{lookRuleForm.expertclassify}}</span>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
      <!-- 专家-查看弹窗end -->

      <!-- 资格预审弹窗start -->
      <el-dialog
        title="资格预审设置"
        :visible.sync="setQualDialogVisible"
        width="55%">
        <div class="qualpre-form">
          <el-form :model="qualPreRuleForm" label-width="100px" :rules="qualPreRuleRules" ref="qualPreRuleForm">
            <el-form-item label="招标项目名称:">
              <span>{{qualPreRuleForm.projectName}}</span>
            </el-form-item>
            <el-form-item label="招标项目编号:">
              <span>{{qualPreRuleForm.codeUser}}</span>
            </el-form-item>
            <el-form-item label="标段名称:">
              <span>{{qualPreRuleForm.sectionName}}</span>
            </el-form-item>
            <el-form-item label="资格预审方法:">
              <span>{{qualPreRuleForm.prequalificationType | filterPrType}}</span>
            </el-form-item>
            <div class="qualpre-table">
              <el-table
                border
                :data="qualPreRuleForm.dialogTableData"
                :row-class-name="tableRowClassName"
                style="width: 100%">
                <el-table-column
                  type="index"
                  label="序号"
                  :index="indexDialogMethod"
                  width="55">
                </el-table-column>
                <el-table-column
                  label="报名人名称"
                  prop="bidderName"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="isPrequalification"
                  label="是否参与预审"
                  align="center">
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.isPrequalification"></el-checkbox>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="score"
                  label="得分"
                  align="center">
                  <template slot-scope="scope">
                    <el-form-item
                      :prop="'dialogTableData.' + scope.$index + '.score'"
                      :rules='qualPreRuleRules.score'
                      class="vilitate-col">
                      <el-input v-model="scope.row.score" placeholder="请输入得分"></el-input>
                    </el-form-item>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="isPassed"
                  label="是否通过"
                  align="center">
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.isPassed"></el-checkbox>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="fileInformation"
                  label="申请文件"
                  align="center"
                  show-overflow-tooltip>
                  <template slot-scope="scope">
                    <el-upload
                      :show-file-list="false"
                      class="upload-demo"
                      :action="upLoad"
                      :before-upload="beforeUpload"
                      :on-success="handleFileSuccess"
                      :file-list="appFileList">
                      <el-button size="small" type="success" @click="clickFile(scope.$index)" class="upload-btn">{{(scope.row.fileInformation?'替换':'上传')}}</el-button>
                    </el-upload>
                    <el-button
                      v-if="scope.row.fileInformation"
                      type="text"
                      @click="downloadFile(scope.row.fileInformation.fileName, scope.row.fileInformation.relativePath)">
                      <span style="display:inline-block;float:left;padding-right:20px;">
                        {{scope.row.fileInformation.fileName}}
                      </span>
                    </el-button>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="noticeFileInformation"
                  label="通知书"
                  align="center">
                  <template slot-scope="scope">
                    <el-upload
                      :show-file-list="false"
                      class="upload-demo"
                      :action="upLoad"
                      :before-upload="expertBeforeUpload"
                      :on-success="handleNoticeSuccess"
                      :file-list="noticeFileList">
                      <el-button size="small" type="success" @click="clicks(scope.$index)" class="upload-btn">{{(scope.row.noticeFileInformation?'替换':'上传')}}</el-button>
                    </el-upload>
                    <el-button
                      v-if="scope.row.noticeFileInformation"
                      type="text"
                      @click="downloadFile(scope.row.noticeFileInformation.fileName, scope.row.noticeFileInformation.relativePath)">
                      <span style="display:inline-block;float:left;padding-right:20px;">
                        {{scope.row.noticeFileInformation.fileName}}
                      </span>
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-form>
        </div>
        <div class="dialog-footbtn">
          <el-button type="primary" @click="saveQualPreClick('qualPreRuleForm')">保 存</el-button>
          <el-button @click="setQualDialogVisible = false">取 消</el-button>
        </div>
      </el-dialog>
      <!-- 资格预审弹窗end -->
    </div>
    <!-- 弹窗区域end -->
  </div>
</template>
<script>
import {section, qualfile, expertmanage, tenderproject, bidderInfo, qualfileResult, openbid} from 'api/index'
import {formatDate} from 'common/js/date'
import {expertclassify} from 'api/resource/expertmanage/expertclassify'
import {commonJs, downloadFile} from 'common/js/common'
export default {
  data () {
    var validateScore = (rule, value, callback) => {
      if (value && (!(/^[1-9]\d{0,2}$/).test(value))) {
        callback(new Error('得分只能输入小数点前不超过3位的数字'))
      } else {
        callback()
      }
    }
    var validateIdCard = (rule, value, callback) => {
      if (value && (!(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/).test(value))) {
        callback(new Error('身份证输入不合法，请重新录入！'))
      } else {
        callback()
      }
    }
    return {
      resultForm: {
        expertResult: '',
        qualPreNotice: ''
      },
      resultRules: {
        expertResult: [
          {required: true, message: '请上传专家抽取结果文件!', trigger: 'blur'}
        ],
        qualPreNotice: [
          {required: true, message: '请上传资格预审报告文件!', trigger: 'blur'}
        ]
      }, // 预审结果-规则
      upLoad: commonJs.fileUploadUrl,
      dialogEdit: true, // 是否可编辑
      disabled: true, // 身份证号未录入时，其他输入框均为禁用状态
      sectionData: [],
      sectionName: '',
      openRuleForm: {},
      fileExpertList: [], // 专家结果上传-列表
      fileQualPretList: [], // 资格预审报告上传-列表
      dialogTitle: '添加',
      dialogVisible: false, // 添加-编辑弹窗
      ruleForm: {}, // 添加-编辑表单
      cascaderSelectData: expertclassify,
      selectTableData: [], // 弹窗-下拉选-表格
      cascaderSelectValue: [],
      // 新增/编辑专家验证
      rules: {
        name: [
          { required: true, message: '请填写姓名', trigger: 'blur' },
          { min: 1, max: 10, message: '请输入1~10个字符', trigger: 'blur' }
        ],
        idCard: [
          { required: true, message: '请填写身份证号', trigger: 'blur' },
          { validator: validateIdCard, trigger: 'blur' }
        ],
        sex: [
          { required: true, message: '请选择性别', trigger: 'change' }
        ],
        enterpriseName: [
          { required: true, message: '请填写单位', trigger: 'blur' },
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
        ],
        cityId: [
          { required: true, message: '请选择地区', trigger: 'change' }
        ],
        expertSources: [
          { required: true, message: '请选择专家类别', trigger: 'change' }
        ],
        classify: [
          { required: true, message: '请选择专业', trigger: 'blur' }
        ]
      },
      sexList: [
        {
          label: '男',
          value: 1
        },
        {
          label: '女',
          value: 2
        }
      ],
      cityIdList: [
        {
          label: '太原市',
          value: '14'
        },
        {
          label: '大同市',
          value: '15'
        },
        {
          label: '阳泉市',
          value: '16'
        },
        {
          label: '长治市',
          value: '17'
        },
        {
          label: '晋城市',
          value: '18'
        },
        {
          label: '朔州市',
          value: '19'
        },
        {
          label: '晋中市',
          value: '20'
        },
        {
          label: '运城市',
          value: '21'
        },
        {
          label: '忻州市',
          value: '22'
        },
        {
          label: '临汾市',
          value: '23'
        },
        {
          label: '吕梁市',
          value: '24'
        }],
      typeList: [
        {
          label: '在库专家',
          value: 1
        },
        {
          label: '公司内部专家',
          value: 2
        },
        {
          label: '其他',
          value: 3
        }
      ],
      lookDialogVisible: false, // 专家-查看-弹窗
      lookRuleForm: {}, // 查看表单
      majorOptions: [], // 专业数据
      collapseActiveName: '',
      tabsActiveName: 'first', // tabs默认选中项
      currentPage: 1,
      expertTableData: [], // 专家表格数据
      preresultTableData: [], // 预审结果表格
      setQualDialogVisible: false, // 资格预审弹窗
      appFileList: [], // 弹窗-申请文件上传列表
      appFileListIndex: -1,
      noticeFileList: [], // 弹窗-通知书上传列表
      noticeFileListIndex: -1,
      activeIndex: 0,
      // 资格预审-表单
      qualPreRuleForm: {
        // 资格预审弹窗-表格
        projectName: '',
        dialogTableData: []
      },
      // 新增/编辑预审结果验证
      qualPreRuleRules: {
        fileInformation: [
          {required: true, message: '请上传申请文件附件，且格式为PDF格式', trigger: 'blur'}
        ],
        noticeFileInformation: [
          {required: true, message: '请上传通知书附件，且格式为PDF/图片格式', trigger: 'blur'}
        ],
        score: [
          {validator: validateScore}
        ]
      },
      qualifiPreResult: {}, // 资格预审结果
      progressStage: null, // 项目进行状态
      noticeProgress: null, // 标段锁定进行状态
      notLockedCount: 0, // 未锁定的标段数量
      currentProgressStage: 3 // 资格预审会阶段
    }
  },
  computed: {
    backData () {
      return this.selectTableData
    }
  },
  created () {
    // 获得标段数据
    // this._getSection()
    // 查询进度
    this.getNoticeProgress()
  },
  filters: {
    filterQualifiPreTime (val) {
      if (val) {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return ''
      }
    },
    filterSex (val) {
      if (val === 1) {
        return '男'
      } else if (val === 2) {
        return '女'
      }
    },
    filterCity (val) {
      let cityId = val
      if (cityId === '14') {
        return '太原市'
      } else if (cityId === '15') {
        return '大同市'
      } else if (cityId === '16') {
        return '阳泉市'
      } else if (cityId === '17') {
        return '长治市'
      } else if (cityId === '18') {
        return '晋城市'
      } else if (cityId === '19') {
        return '朔州市'
      } else if (cityId === '20') {
        return '晋中市'
      } else if (cityId === '21') {
        return '运城市'
      } else if (cityId === '22') {
        return '忻州市'
      } else if (cityId === '23') {
        return '临汾市'
      } else if (cityId === '24') {
        return '吕梁市'
      }
    },
    filterSources (val) {
      let expertSources = val
      if (expertSources === 1) {
        return '在库专家'
      } else if (expertSources === 2) {
        return '公司内部专家'
      } else if (expertSources === 3) {
        return '其他'
      }
    },
    filterPrType (val) {
      let prequalificationType = val
      if (prequalificationType === 1) {
        return '合格制'
      } else if (prequalificationType === 2) {
        return '有限数量制'
      }
    }
  },
  props: ['callbackdata', 'noticeCode', 'operationCheckFlag'],
  /** 方法集 */
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    indexResultMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    indexDialogMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 下载文件 */
    downloadFile,
    /** 格式化 */
    filterCity (row, column) {
      let cityId = row.cityId
      if (cityId === '14') {
        return '太原市'
      } else if (cityId === '15') {
        return '大同市'
      } else if (cityId === '16') {
        return '阳泉市'
      } else if (cityId === '17') {
        return '长治市'
      } else if (cityId === '18') {
        return '晋城市'
      } else if (cityId === '19') {
        return '朔州市'
      } else if (cityId === '20') {
        return '晋中市'
      } else if (cityId === '21') {
        return '运城市'
      } else if (cityId === '22') {
        return '忻州市'
      } else if (cityId === '23') {
        return '临汾市'
      } else if (cityId === '24') {
        return '吕梁市'
      }
    },
    filterSources (row, column) {
      let expertSources = row.expertSources
      if (expertSources === 1) {
        return '在库专家'
      } else if (expertSources === 2) {
        return '公司内部专家'
      } else if (expertSources === 3) {
        return '其他'
      }
    },
    filterSex (row, column) {
      let sex = row.sex
      if (sex === 1) {
        return '男'
      } else if (sex === 2) {
        return '女'
      }
    },
    filterIsPrequalification (row, column) {
      let isPrequalification = row.isPrequalification
      if (isPrequalification === false || isPrequalification === 0) {
        return '否'
      } else {
        return '是'
      }
    },
    filterIsPassed (row, column) {
      let isPassed = row.isPassed
      if (isPassed === true || isPassed === 1) {
        return '是'
      } else {
        return '否'
      }
    },
    /** tabs切换事件 */
    tabsClick (tab, event) {
      if (Object.is(tab.name, 'first')) {
        // 资格预审会时间
        this._getQualfile()
        // 评标专家列表
        this._getExpertInfo()
      } else if (Object.is(tab.name, 'second')) {
        // 招标项目信息
        this._getTenderProject()
        // 查询投标人信息
        this._getBidderInfo()
        // 查询资格预审结果信息
        this._getQualifiPreResult()
      }
    },
    /** 初始化数据-点击面板标题栏 */
    getDataBySection (code, index) {
      this.expertTableData = []
      this.openRuleForm = {}
      this.ruleForm = {}
      this.qualPreRuleForm = {
        projectName: '',
        sectionName: '',
        dialogTableData: []
      }
      this.preresultTableData = []
      this.fileExpertList = []
      this.fileQualPretList = []
      this.tabsActiveName = 'first'
      this.activeIndex = Number(index)
      this.qualifiPreResult = {}
      this.collapseActiveName = code
      if (this.collapseActiveName) {
        // 资格预审会时间
        this._getQualfile()
        // 评标专家列表
        this._getExpertInfo()
      }
    },
    /** 专业-改变事件 */
    handleMajorChange () {},
    /** 专家专业新增下拉框 */
    addSelectClick () {
      let obj = {}
      this.selectTableData.push(obj)
    },
    /** 专家专业删除下拉框 */
    deleteRow (index, rows) {
      rows.splice(index, 1)
      this.cascaderSelectValue.splice(index, 1)
    },
    /** 评标专家-添加专家弹窗 */
    addExpertBtn () {
      this.ruleForm = {}
      this.selectTableData = []
      this.disabled = true
      this.dialogVisible = true
      this.dialogTitle = '添加'
    },
    /** 评标专家-修改展示专业 */
    editProTabShow (row) {
      let arr = []
      let rowL = row.expertClassifyList
      for (let i = 0; i < rowL.length; i++) {
        if (i > 0) {
          arr[i - 1] = {
            professional: [
              rowL[i].firstClassify,
              rowL[i].secondClassify,
              rowL[i].thirdClassify,
              rowL[i].fourClassify
            ]
          }
        }
      }
      return arr
    },
    /** 评标专家-获取专业信息 */
    saveClassify () {
      this.ruleForm.expertClassifyList = this.selectTableData.map(item => { return item })
      if (this.selectTableData) {
        for (let i = 0; i < this.selectTableData.length; i++) {
          this.ruleForm.expertClassifyList[i].firstClassify = this.selectTableData[i].professional[0]
          this.ruleForm.expertClassifyList[i].secondClassify = this.selectTableData[i].professional[1]
          this.ruleForm.expertClassifyList[i].thirdClassify = this.selectTableData[i].professional[2]
          this.ruleForm.expertClassifyList[i].fourClassify = this.selectTableData[i].professional[3]
        }
      }
      // 合并数据
      let tempArr = this.ruleForm.classify
      this.ruleForm.expertClassifyList.push({firstClassify: tempArr[0], secondClassify: tempArr[1], thirdClassify: tempArr[2], fourClassify: tempArr[3]})
    },
    /** 评标专家-弹窗保存 */
    saveExpertClick (val) {
      this.$refs[val].validate((vaild) => {
        if (vaild) {
          this.saveClassify()
          if (this.ruleForm.expertType === 1) {
            this.ruleForm.objectId = null
            this.ruleForm.code = ''
          }
          this.ruleForm.provinceId = '4'
          this.ruleForm.expertType = 2
          this.ruleForm.relatedNoticeCode = this.noticeCode
          this.ruleForm.relatedSectionCode = this.collapseActiveName
          expertmanage.save(this.ruleForm).then(() => {
            this._getExpertInfo()
            this.cancelExpertClick()
          })
        } else {
          return false
        }
      })
    },
    /** 评标专家-弹窗取消 */
    cancelExpertClick () {
      this.$refs['ruleForm'].resetFields()
      this.ruleForm = {}
      this.selectTableData = []
      this.dialogVisible = false
    },
    /** 身份证号输入框失去焦点时触发 */
    idCardBlur () {
      if (this.ruleForm.idCard) {
        this.disabled = false
        let obj = {
          idCard: this.ruleForm.idCard,
          expertType: 1
        }
        expertmanage.getByIdCard(obj).then(res => {
          let expertInfo = res.data.expertInfo
          if (expertInfo) {
            this.ruleForm = expertInfo
            this.ruleForm.classify = [expertInfo.expertClassifyList[0].firstClassify, expertInfo.expertClassifyList[0].secondClassify, expertInfo.expertClassifyList[0].thirdClassify, expertInfo.expertClassifyList[0].fourClassify]
            this.selectTableData = this.editProTabShow(expertInfo)
          }
        })
      }
    },
    /** 评标专家-表格操作 */
    handleTableClick (row, type) {
      if (Object.is(type, 'edit')) {
        this.disabled = false
        this.dialogVisible = true
        this.dialogTitle = '编辑'
        this.ruleForm = row
        this.ruleForm.classify = [
          row.expertClassifyList[0].firstClassify,
          row.expertClassifyList[0].secondClassify,
          row.expertClassifyList[0].thirdClassify,
          row.expertClassifyList[0].fourClassify
        ]
        this.selectTableData = this.editProTabShow(row)
        // this.saveExpertClick(row)
      } else if (Object.is(type, 'del')) {
        this.deleteClick(row)
      } else if (Object.is(type, 'look')) {
        this.lookDialogVisible = true
        this.getOneExpertInfo(row.objectId)
      }
    },
    /** 评标专家-删除 */
    deleteClick (data) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 调用接口
        expertmanage.delete(data.objectId).then(() => {
          this._getExpertInfo()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    /** 预审结果-打开弹窗 */
    preResultBtn () {
      // 打开设置弹窗
      this.setQualDialogVisible = true
      // 查询投标人信息
      // this._getBidderInfo()
      this._getQualifiPreResult()
    },
    /** 结果设置-弹窗保存 */
    saveQualPreClick (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.preresultTableData = this.qualPreRuleForm.dialogTableData.map((item) => { return item })
          if (this.preresultTableData.length > 0) {
            this.bidderResultRank(this.preresultTableData)
          }
          this.setQualDialogVisible = false
        }
      })
    },
    /** 校验附件 */
    validFile () {
      // console.log(this.activeIndex)
      // 专家抽取结果
      if (this.fileExpertList.length > 0) {
        this.$set(this.resultForm, 'expertResult', 'expertResult')
      } else {
        this.$set(this.resultForm, 'expertResult', '')
      }
      if (this.resultForm.expertResult) {
        this.$refs['expertResult'][this.activeIndex].clearValidate()
      }
      // 资格预审报告
      if (this.fileQualPretList.length > 0) {
        this.$set(this.resultForm, 'qualPreNotice', 'qualPreNotice')
      } else {
        this.$set(this.resultForm, 'qualPreNotice', '')
      }
      if (this.resultForm.qualPreNotice) {
        this.$refs['qualPreNotice'][this.activeIndex].clearValidate()
      }
    },
    /** 预审结果-主页保存 */
    saveResult (name) {
      // 校验附件
      this.validFile()
      this.$refs[name][this.activeIndex].validate((valid) => {
        if (valid) {
          // 定义对象
          let resultForm = {}
          // 如果当前预审结果的objectId不为空，将当前表格上的数据赋值给对象进行保存
          if (this.qualifiPreResult.objectId) {
            resultForm = this.qualifiPreResult
          } else {
            // 如果为空，则新建对象，将页面数据进行保存
            resultForm.noticeCode = this.noticeCode
            resultForm.sectionCode = this.collapseActiveName
          }
          // 设置表格上传数据
          let qualifiPreResultSublistList = this.resetTableData(this.preresultTableData)
          // 设置表格数据-是否
          if (this.preresultTableData) {
            for (let i = 0; i < this.preresultTableData.length; i++) {
              if (this.preresultTableData[i].isPassed === true ||
                this.preresultTableData[i].isPassed === 1) {
                this.preresultTableData[i].isPassed = 1
              } else {
                this.preresultTableData[i].isPassed = 0
              }
              if (this.preresultTableData[i].isPrequalification === true ||
                this.preresultTableData[i].isPrequalification === 1) {
                this.preresultTableData[i].isPrequalification = 1
              } else {
                this.preresultTableData[i].isPrequalification = 0
              }
            }
          }
          // 设置申请文件和通知书
          this.$set(resultForm, 'qualifiPreResultSublistList', qualifiPreResultSublistList)
          // 设置专家抽签结果和资格预审报告
          let qualifiPreResultFileList = this.resetFormData(resultForm.qualifiPreResultFileList)
          this.$set(resultForm, 'qualifiPreResultFileList', qualifiPreResultFileList)
          // 保存
          qualfileResult.saveQualifiPreResult(resultForm).then((res) => {
            if (res) {
              this._getQualifiPreResult()
            }
          })
        }
      })
    },
    /** 设置表单上传 */
    resetFormData (formList) {
      let result = []
      if (formList) {
        result = formList.map((item) => {
          // 专家抽取结果
          if (item.qualifiPreResultFileType === 1) {
            item.fileInformation = this.fileExpertList[0]
          }
          // 资格预审报告
          if (item.qualifiPreResultFileType === 2) {
            item.fileInformation = this.fileQualPretList[0]
          }
          return item
        })
      } else {
        if (this.fileExpertList.length > 0) {
          // 专家结果上传-列表
          let obj1 = {
            qualifiPreResultFileType: 1,
            fileInformation: this.fileExpertList[0]
          }
          result.push(obj1)
        }
        if (this.fileQualPretList.length > 0) {
          // 资格预审报告上传-列表
          let obj2 = {
            qualifiPreResultFileType: 2,
            fileInformation: this.fileQualPretList[0]
          }
          result.push(obj2)
        }
      }
      return result
    },
    /** 设置表格-上传文件数据 */
    resetTableData (arr) {
      let result = []
      if (arr) {
        result = arr.map((item) => {
          // 修改已上传的
          if (item.qualifiPreResultSublistFileList) {
            item.qualifiPreResultSublistFileList = item.qualifiPreResultSublistFileList.map((sublistFile) => {
              // 申请文件
              if (sublistFile.sublistFileType === 1) {
                sublistFile.fileInformation = item.fileInformation
              }
              // 通知书
              if (sublistFile.sublistFileType === 2) {
                sublistFile.fileInformation = item.noticeFileInformation
              }
              return sublistFile
            })
          } else {
            // 新增-上传
            let arr = []
            let obj1 = {
              sublistFileType: 1,
              fileInformation: item.fileInformation
            }
            let obj2 = {
              sublistFileType: 2,
              fileInformation: item.noticeFileInformation
            }
            arr.push(obj1)
            arr.push(obj2)
            item.qualifiPreResultSublistFileList = arr
          }
          return item
        })
      }
      return result
    },
    /** 预审结果-依据评标得分对结果进行排序 */
    bidderResultRank (data) {
      let flag = data.length
      while (flag > 0) {
        let k = flag
        flag = 0
        for (let j = 1; j < k; j++) {
          if (Number(data[j - 1].score) < Number(data[j].score)) {
            let temp = {}
            temp = data[j - 1]
            data[j - 1] = data[j]
            data[j] = temp
            flag = j
          }
        }
      }
    },
    /** 提交 */
    submitForm () {
      if (this.expertTableData.length < 5 || this.expertTableData.length % 2 === 0) {
        this.$message.error('评标专家需至少5个且为奇数个')
        return false
      } else {
        qualfileResult.updateProgressStage(this.noticeCode).then(() => {
          this.submitDisPlay()
        })
      }
    },
    /** 查询单条专家信息 */
    getOneExpertInfo (objectId) {
      expertmanage.getOne(objectId).then((res) => {
        let jsonForm = res.data.expertInfo
        this.lookRuleForm = jsonForm
        // 专家分类
        let arr = jsonForm.expertClassifyList.map((item, index) => {
          let str = ''
          let firstStr = this.getLabelName(expertclassify, item.firstClassify)
          let secondStr = this.getLabelName(expertclassify, item.secondClassify)
          let thridStr = this.getLabelName(expertclassify, item.thirdClassify)
          let fourStr = this.getLabelName(expertclassify, item.fourClassify)
          if (firstStr.node) {
            str += `${firstStr.node.label}`
          }
          if (secondStr.node) {
            str += `/${secondStr.node.label}`
          }
          if (thridStr.node) {
            str += `/${thridStr.node.label}`
          }
          if (fourStr.node) {
            str += `/${fourStr.node.label}`
          }
          return str
        })
        this.lookRuleForm.expertclassify = arr.toString()
      })
    },
    /** 附件上传 */
    upLoadSuccess (response, file, fileList, fileInformations) {
      if (fileList.length > 0) {
        let existFileSize = 0
        //  得到已经保存的附件信息
        for (var i = 0; i < fileList.length; i++) {
          if (!fileList[i].response) {
            existFileSize++
            fileInformations[i] = fileList[i]
          }
        }
        // 添加新的附件信息
        if (existFileSize < fileList.length) {
          for (var j = existFileSize; j < fileList.length; j++) {
            if (fileList[j].response) {
              fileInformations[j] = fileList[j].response.fileInformation
            }
          }
        }
      }
    },
    /** 申请文件上传 */
    clickFile (index) {
      this.appFileListIndex = index
    },
    /** 弹窗-资格预审设置-申请文件上传 */
    handleFileSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.appFileList = fileList
        this.qualPreRuleForm.dialogTableData[this.appFileListIndex].fileInformation = response.fileInformation
      }
      this.validFile()
    },
    /** 通知书上传 */
    clicks (index) {
      this.noticeFileListIndex = index
    },
    /** 通知书上传 */
    handleNoticeSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.noticeFileList = fileList
        this.qualPreRuleForm.dialogTableData[this.noticeFileListIndex].noticeFileInformation = response.fileInformation
      }
    },
    /** 专家抽取结果-上传 */
    handleExpertSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.fileExpertList = []
        // 替换 ；通用
        this.fileExpertList.push(file.response.fileInformation)
      }
      this.validFile()
    },
    /** 附件格式为pdf或图片 */
    expertBeforeUpload (file) {
      const isPDF = file.type === 'application/pdf'
      const isPicture = file.type === 'image/png' || file.type === 'image/jpg' || file.type === 'image/jpeg'
      if (!isPDF && !isPicture) {
        this.$message.error('上传文件只能为PDF格式或扫描件等图片！')
      }
      return isPDF || isPicture
    },
    /** 专家抽取结果-删除 */
    expertFileClickDel (index) {
      this.fileExpertList.splice(this.fileExpertList.indexOf(index), 1)
    },
    /** 资格预审报告-上传 */
    handleQualPreSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.fileQualPretList = []
        this.fileQualPretList.push(file.response.fileInformation)
      }
      this.validFile()
    },
    /** 附件格式为pdf */
    beforeUpload (file) {
      const isPDF = file.type === 'application/pdf'
      if (!isPDF) {
        this.$message.error('上传文件只能为PDF格式！')
      }
      return isPDF
    },
    /** 资格预审报告-删除 */
    QualPreFileClickDel (index) {
      this.fileQualPretList.splice(this.fileQualPretList.indexOf(index), 1)
    },
    /** 预审结果-表单数据设置 */
    setPreresultTableData (json) {
      let resultJson = json || {}
      if (resultJson.qualifiPreResultFileList) {
        this.fileExpertList = []
        this.fileQualPretList = []
        resultJson.qualifiPreResultFileList.forEach((item) => {
          // 专家抽签结果-上传列表
          if (item.qualifiPreResultFileType === 1) {
            this.fileExpertList = [item.fileInformation]
          }
          // 资格预审报告-上传列表
          if (item.qualifiPreResultFileType === 2) {
            this.fileQualPretList = [item.fileInformation]
          }
        })
      }

      // 预审结果-表格
      if (resultJson.qualifiPreResultSublistList.length > 0) {
        // 表格数据
        let resultList = resultJson.qualifiPreResultSublistList
        // 处理表格数据（申请文件和通知书）
        resultList.map((item) => {
          item.qualifiPreResultSublistFileList.map((child) => {
            if (child.sublistFileType === 1) {
              item.fileInformation = child.fileInformation || {}
            }
            if (child.sublistFileType === 2) {
              item.noticeFileInformation = child.fileInformation || {}
            }
          })
          return item
        })
        console.log(resultList)
        // 表单-表格
        this.preresultTableData = resultList
      }
    },
    /** 预审结果-弹窗数据设置 */
    setDialogTableData (json) {
      let resultJson = JSON.stringify(json)
      this.qualPreRuleForm.dialogTableData = []
      let newArr = JSON.parse(resultJson).qualifiPreResultSublistList
      // 处理表格数据（申请文件和通知书）
      newArr.map((item) => {
        item.qualifiPreResultSublistFileList.map((child) => {
          if (child.sublistFileType === 1) {
            item.fileInformation = child.fileInformation
          }
          if (child.sublistFileType === 2) {
            item.noticeFileInformation = child.fileInformation
          }
        })
        return item
      })
      this.qualPreRuleForm.dialogTableData = newArr
      let section = this.sectionData
      for (let i = 0; i < section.length; i++) {
        if (section[i].code === this.collapseActiveName) {
          // this.qualPreRuleForm.sectionName = section[i].name
          this.$set(this.qualPreRuleForm, 'sectionName', section[i].name)
        }
      }
      // 根据得分排序
      this.bidderResultRank(this.qualPreRuleForm.dialogTableData)
      // 设置弹窗多选框
      if (newArr.length > 0) {
        for (let i = 0; i < newArr.length; i++) {
          if (newArr[i].isPrequalification === true) {
            this.qualPreRuleForm.dialogTableData[i].isPrequalification = true
          } else {
            this.qualPreRuleForm.dialogTableData[i].isPrequalification = false
          }
          if (newArr[i].isPassed === 1) {
            this.qualPreRuleForm.dialogTableData[i].isPassed = true
          } else {
            this.qualPreRuleForm.dialogTableData[i].isPassed = false
          }
          this.qualPreRuleForm.dialogTableData[i].score = Number(newArr[i].score)
        }
      }
    },
    /** 查询资格预审结果信息 */
    _getQualifiPreResult () {
      // 设置标段名称
      for (let i = 0; i < this.sectionData.length; i++) {
        if (this.sectionData[i].code === this.collapseActiveName) {
          this.qualPreRuleForm.sectionName = this.sectionData[i].name
        }
      }
      let resultForm = {}
      let obj = {
        noticeCode: this.noticeCode,
        sectionCode: this.collapseActiveName
      }
      qualfileResult.getQualifiPreResult(obj).then((res) => {
        resultForm = res.data.qualifiPreResult
        if (resultForm) {
          this.qualifiPreResult = resultForm
          // 预审结果表单数据设置
          this.setPreresultTableData(resultForm)
          // 预审结果设置-弹窗-表格数据
          this.setDialogTableData(resultForm)
        }
        if (this.preresultTableData.length < 1) {
          this._getBidderInfo()
        }
      })
    },
    /** 查询投标人信息 */
    _getBidderInfo () {
      bidderInfo.getByRelatedCode(this.noticeCode, this.collapseActiveName).then((res) => {
        let result = res.data.bidderInfoList
        if (result) {
          // 清空数组-阻止数据重复添加
          this.qualPreRuleForm.dialogTableData = []
          for (let i = 0; i < result.length; i++) {
            let bidderInfo = {
              bidderName: result[i].bidderName,
              bidderCode: result[i].code
            }
            this.qualPreRuleForm.dialogTableData.push(bidderInfo)
          }
        } else {
          return false
        }
      })
    },
    /** 查询招标项目信息 */
    _getTenderProject () {
      tenderproject.getOneByCode(this.$route.query.tenderProjectCode).then((res) => {
        let result = res.data.tenderProject
        if (result) {
          if (result.selectApply) {
            this.qualPreRuleForm.projectName = result.selectApply.projectName
            this.qualPreRuleForm.codeUser = result.selectApply.codeUser
          }
          this.qualPreRuleForm.prequalificationType = result.prequalificationType
        } else {
          return false
        }
      })
    },
    /** 查询专家信息 */
    _getExpertInfo () {
      let obj = {
        relatedNoticeCode: this.noticeCode,
        relatedSectionCode: this.collapseActiveName,
        expertType: 2
      }
      expertmanage.getList(obj).then((res) => {
        if (res.data.expertInfoList) {
          let handleData = res.data.expertInfoList.list
          if (handleData.length > 0) {
            for (let i = 0; i < handleData.length; i++) {
              let expertArr = handleData[i].expertClassifyList
              if (expertArr) {
                handleData[i].expertclassify = this.transLabel(expertArr)
              }
            }
          }
          this.expertTableData = handleData
        } else {
          return false
        }
      })
    },
    /** 查询资格预审文件信息 */
    _getQualfile () {
      if (this.collapseActiveName) {
        qualfile.getByCode(this.noticeCode, this.collapseActiveName).then((res) => {
          let qualifiPreFile = res.data.qualifiPreFile
          if (qualifiPreFile) {
            this.$set(this.openRuleForm, 'qualifiPreTime', qualifiPreFile.qualifiPreTime)
          } else {
            return false
          }
        })
      }
    },
    /** 查询项目进行状态 */
    getNoticeProgress () {
      let obj = {
        tenderProjectCode: this.$route.query.code,
        noticeCode: this.noticeCode
      }
      openbid.getNoticeProgress(obj).then((res) => {
        let result = res.data.noticeProgress
        if (result) {
          this.progressStage = result.progressStage
        } else {
          return false
        }
      }).then((res) => {
        this._getSection()
      })
    },
    /** 查询标段信息 */
    _getSection () {
      this.sectionData = []
      section.getSections(this.noticeCode).then((res) => {
        let sectionList = res.data.sectionList
        if (sectionList) {
          if (sectionList.length > 0) {
            for (let i = 0; i < sectionList.length; i++) {
              // 标段已锁定
              if (sectionList[i].lockedProgressStage) {
                // 菜单按钮高亮设置
                if (this.noticeProgress) {
                  if (this.noticeProgress < sectionList[i].lockedProgressStage) {
                    this.noticeProgress = sectionList[i].lockedProgressStage
                  }
                } else {
                  this.noticeProgress = sectionList[i].lockedProgressStage
                }
                // 当前状态在锁定状态之前，则展示该标段；之后，则不展示
                if (this.currentProgressStage <= sectionList[i].lockedProgressStage) {
                  this.sectionData.push(sectionList[i])
                }
              } else {
                // 标段未锁定
                this.notLockedCount = this.notLockedCount + 1
                this.sectionData.push(sectionList[i])
              }
            }
            if (this.sectionData) {
              if (this.sectionData.length > 0) {
                // 设置默认打开第一项
                this.collapseActiveName = this.sectionData[0].code
                // 结果设置弹窗标段名称展示
                this.qualPreRuleForm.sectionName = this.sectionData[0].name
                if (this.collapseActiveName) {
                  // 资格预审会时间
                  this._getQualfile()
                  // 评标专家列表
                  this._getExpertInfo()
                }
              }
            }
          }
          this.display()
        } else {
          return false
        }
      })
    },
    display () {
      let enterpriseType = this.$store.getters.authUser.enterpriseType
      // 若标段全部锁定，则使标段中锁定进行状态最靠后的菜单按钮高亮
      if (this.notLockedCount === 0) {
        this.$emit('noticeProgress', this.noticeProgress)
        this.dialogEdit = false
      } else {
        // 若存在未锁定的标段，项目进行状态处于当前阶段且登录人员为招标机构，则可编辑；否则只可查看
        if (this.progressStage === 3 && enterpriseType === 4) {
          this.dialogEdit = true
        } else {
          this.dialogEdit = false
        }
        // 若项目进行状态为招标文件阶段，则招标文件按钮高亮
        if (this.progressStage === 4) {
          this.$emit('noticeProgress', 4)
        }
      }
      // 如果进入的是招标变更查询，则只能进行查看操作
      if (!this.operationCheckFlag) {
        this.dialogEdit = false
      }
    },
    submitDisPlay () {
      let obj = {
        tenderProjectCode: this.$route.query.tenderProjectCode,
        noticeCode: this.noticeCode
      }
      openbid.getNoticeProgress(obj).then((res) => {
        let result = res.data.noticeProgress
        if (result) {
          this.progressStage = result.progressStage
          this.display()
        } else {
          return false
        }
      })
    },
    /** 转成label值 */
    transLabel (expertArr) {
      let tempArr = []
      let str = ''
      for (let j = 0; j < expertArr.length; j++) {
        if (expertArr[j].firstClassify) {
          let first = this.getLabelName(expertclassify, expertArr[j].firstClassify)
          str += first.node.label + '/'
        }
        if (expertArr[j].secondClassify) {
          let second = this.getLabelName(expertclassify, expertArr[j].secondClassify)
          str += second.node.label + '/'
        }
        if (expertArr[j].thirdClassify) {
          let third = this.getLabelName(expertclassify, expertArr[j].thirdClassify)
          str += third.node.label + '/'
        }
        if (expertArr[j].fourClassify) {
          let fourth = this.getLabelName(expertclassify, expertArr[j].fourClassify)
          str += fourth.node.label
        }
        tempArr.push(str)
        str = ''
      }
      return tempArr
    },
    /** treeData [原始数据集] ;key 传入的key值 */
    getLabelName (treeData, key) {
      let parentNode = null
      let node = null
      function getTreeDeepArr (treeData, key) {
        for (let i = 0; i < treeData.length; i++) {
          // 1.如果没有子节点 beark
          if (node && key) {
            break
          }
          // 定义需要操作的数据
          let obj = treeData[i]
          // 没有节点就下一个
          if (!obj || !obj.value) {
            continue
          }
          // 2.有节点就继续找，一直递归
          if (obj.value === key) {
            node = obj
            break
          } else {
            // 3.如果有子节点就继续找
            if (obj.children) {
              // 4.递归前，记录当前节点，作为parentNode
              parentNode = obj
              // 递归
              getTreeDeepArr(obj.children, key)
            } else {
              // 跳出当前递归
              continue
            }
          }
        }
        // 如果没有找到父节点，置为null
        if (!node) {
          parentNode = null
        }
        // 返回结果
        return {
          node: node,
          parentNode: parentNode
        }
      }
      return getTreeDeepArr(treeData, key)
    }
  },
  /** 实例创建完成 */
  mounted () {
  }
}
</script>
<style lang="less">
  #qualpre-page {
    .qualpre-cont {
      .qualpre-tit {
        height: 50px;
        line-height: 50px;
        text-align:left;
        padding: 0 20px;
        background:#F7F6FB;
        span {
          font-weight: 800;
        }
      }
      /** 折叠面板样式 */
      .qualpre-box {
        padding: 20px;
        .qualpre-time {
          text-align: left;
          color: #999;
          padding: 10px;
          .form-span .el-form-item {
            margin-bottom: 0;
          }
        }
        /** 评标专家-表格样式 */
        .expert-table tr{
          td:nth-child(7) {
            div.cell {
              padding:0;
            }
            p {
              margin: 0;
              padding: 10px 8px;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
            }
            p:not(:last-child) {
              border-bottom: 1px solid #eee;
            }
          }
        }
        .save-btn {
          padding: 20px 0;
          text-align: center;
        }
        .add-expert-btn {
          padding-bottom: 20px;
        }
        .preresult-table {
          .el-upload-list.el-upload-list--text {
            width: 30%;
          }
          .el-icon-error {
            color: red;
          }
        }
        .add-expert-btn, .add-preresult-btn {
          text-align: left;
        }
        /** 折叠面板 */
        .el-collapse-item {
          margin-bottom: 10px;
        }
        .el-collapse-item__header {
          padding-left: 20px;
          text-align: left;
          background: #F7F6FB;
        }
        .el-collapse-item__header.is-active {
          background: #ebeffe;
        }
        .el-form-item__content {
          text-align: left;
        }
        .bottom-submit-btn {
          padding: 20px;
          button {
            width: 160px;
          }
        }
      }
    }
    /** 弹窗样式 */
    .qualpre-dialog {
      .qualpre-form {
        .el-form-item__content {
          text-align: left;
        }
        .qualpre-table {
          .el-form-item {
            margin-bottom: 0;
          }
        }
        .vilitate-col {
          .el-form-item__content {
            margin-left: 0 !important;
            width: 100%;
          }
        }
      }
      /** 新增 */
      .edit-select-box {
        .el-table__empty-block {
          display: none;
        }
      }
      .table-add .edit-select-box .cell {
        padding-left: 0;
      }
      .el-dialog__footer {
        text-align: center;
      }
      .dialog-footbtn {
        margin-top: 20px;
      }
    }
  }
</style>
