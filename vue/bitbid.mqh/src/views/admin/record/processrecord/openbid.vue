<template>
  <!-- 开评标 -->
  <div class="admin-layout" id="openbid-page">
    <div class="openbid-cont">
      <div class="openbid-tit">
        <span>开评标</span>
      </div>
      <div class="openbid-box">
        <!-- 折叠面板 -->
        <el-collapse v-model="collapseActiveName" accordion>
          <el-collapse-item v-for="(section, index) in sectionData" :key="index" :name=section.code>
            <template slot="title">
              <span @click="getDataBySection(section.code, index)" class="collapse-span" style="width:100%;display:inherit;position:relative;z-index:1;">
                {{section.name}}
              </span>
            </template>
            <el-tabs type="card" style="margin-top: 10px;" class="card-tabs" @tab-click="tabsClick" v-model="tabsActiveName">
              <!-- 开标start -->
              <el-tab-pane label="开 标" name="first">
                <el-form :model="openRuleForm" :rules="openRules" ref="openRuleForm" label-width="100px">
                  <el-form-item label="开标时间:" prop="bidOpenTime">
                    <span>{{openRuleForm.bidOpenTime | filterBidOpenTime}}</span>
                  </el-form-item>
                  <el-form-item label="开标一览表:" prop="openFile" ref="openFile">
                    <el-upload
                      v-if="dialogEdit"
                      class="upload-demo"
                      :action="upLoad"
                      :show-file-list="false"
                      :before-upload="beforeUpload"
                      :on-success="handleSuccess"
                      :file-list="openFileList">
                      <el-button size="small" type="success" class="upload-btn">上传文件</el-button>
                    </el-upload>
                    <!-- 上传附件列表 -->
                    <div class="apply-upload">
                      <el-row>
                        <el-col :span="24">
                          <span class="file-list" v-for="(item, index) in openFileList" :key="index">
                            <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                            <i class="el-icon-error upload-file" @click="openFileClickDel(index)" v-if="dialogEdit"></i>
                          </span>
                        </el-col>
                      </el-row>
                    </div>
                  </el-form-item>
                  <el-form-item class="form-last">
                    <el-button type="primary" @click="openSubmitForm('openRuleForm')" v-if="dialogEdit">保 存</el-button>
                  </el-form-item>
                </el-form>
              </el-tab-pane>
              <!-- 开标end -->

              <!-- 评标start -->
              <el-tab-pane label="评 标" name="second" class="bid-eval-tab">
                <el-form :model="bidEvalRuleForm" :rules="bidEvalRules" ref="bidEvalRuleForm" label-width="120px">
                  <el-form-item label="评标专家:" prop="bidExpert">
                    <el-button type="warning" @click="AddExpert" icon="el-icon-plus" class="add-btn" v-if="dialogEdit"> 添 加 专 家 </el-button>
                    <el-table
                      class="table-boxwarp"
                      border
                      :data="bidExpertTableData"
                      :row-class-name="tableRowClassName"
                      style="width: 100%">
                      <el-table-column
                        type="index"
                        label="序号"
                        align="center"
                        :index="indexExpertMethod"
                        width="55">
                      </el-table-column>
                      <el-table-column
                        label="姓名"
                        prop="name"
                        align="center">
                      </el-table-column>
                      <el-table-column
                        label="身份证"
                        prop="idCard"
                        align="center"
                        width="170">
                      </el-table-column>
                      <el-table-column
                        label="性别"
                        prop="sex"
                        :formatter="filterSex"
                        align="center"
                        width="80">
                      </el-table-column>
                      <el-table-column
                        label="单位"
                        prop="enterpriseName"
                        header-align="center"
                        align="left">
                      </el-table-column>
                      <el-table-column
                        label="地区"
                        prop="cityId"
                        :formatter="filterCity"
                        header-align="center"
                        align="left">
                      </el-table-column>
                      <el-table-column
                        align="left"
                        header-align="center"
                        label="专业"
                        width="200">
                        <template slot-scope="scope">
                          <p v-for="(item, index) in scope.row.expertclassify" :key="index">{{item}}</p>
                        </template>
                      </el-table-column>
                      <el-table-column
                        label="专家类别"
                        prop="expertSources"
                        :formatter="filterSources"
                        align="center">
                      </el-table-column>
                      <el-table-column
                        label="操作"
                        align="center"
                        width="160"
                        v-if="dialogEdit">
                        <template slot-scope="scope">
                          <el-button @click="handleTableClick(scope.row,'look')" type="text" size="medium">查看</el-button>
                          <el-button @click="handleTableClick(scope.row,'edit')" type="text" size="medium">修改</el-button>
                          <el-button @click="handleTableClick(scope.row,'del')" type="text" size="medium">删除</el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                  </el-form-item>
                  <el-form-item label="评标结果:" prop="bidResult">
                    <el-button type="warning" @click="setBidResult" icon="el-icon-plus" class="add-btn" v-if="dialogEdit">评标结果设置</el-button>
                    <el-table
                      border
                      :data="bidResultTableData"
                      :row-class-name="tableRowClassName"
                      style="width: 100%">
                      <el-table-column
                        type="index"
                        label="排名"
                        align="center"
                        :index="indexBidResultMethod"
                        width="55">
                      </el-table-column>
                      <el-table-column
                        label="报名人"
                        prop="bidderName"
                        align="center">
                      </el-table-column>
                      <el-table-column
                        label="是否投标"
                        prop="isJoinBid"
                        :formatter="filterIsJoinBid"
                        align="center">
                      </el-table-column>
                      <el-table-column
                        label="投标报价(万元)"
                        prop="bidPrice"
                        align="center">
                      </el-table-column>
                      <el-table-column
                        label="工期(交货期)"
                        prop="projectTimeLimit"
                        align="center">
                      </el-table-column>
                      <el-table-column
                        label="得分"
                        prop="score"
                        align="center"
                        width="80">
                      </el-table-column>
                      <el-table-column
                        label="投标文件"
                        prop="fileInformation"
                        align="center">
                        <template slot-scope="scope">
                          <el-button
                            type="text"
                            style="width: 100%;overflow: hidden;"
                            @click="downloadFile(bidResultTableData[scope.$index].fileInformation.fileName,bidResultTableData[scope.$index].fileInformation.relativePath)">
                            <span
                              v-if="bidResultTableData[scope.$index].fileInformation !== '' && bidResultTableData[scope.$index].fileInformation !== null && bidResultTableData[scope.$index].fileInformation !== undefined"
                              style="display:inline-block;float:left;padding-right:20px;width:100%;">
                            {{bidResultTableData[scope.$index].fileInformation.fileName}}</span>
                          </el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                  </el-form-item>
                  <el-form-item label="专家抽取结果:" prop="expertDrawRes" ref="expertDrawRes">
                    <el-upload
                      v-if="dialogEdit"
                      class="upload-demo"
                      :action="upLoad"
                      :show-file-list="false"
                      :before-upload="expertBeforeUpload"
                      :on-success="handleExpertSuccess"
                      :file-list="expertDrawResList">
                      <el-button size="small" type="success" class="upload-btn">上传文件</el-button>
                    </el-upload>
                    <!-- 上传附件列表 -->
                    <div class="apply-upload">
                      <el-row>
                        <el-col :span="12">
                        <span class="file-list" v-for="(item, index) in expertDrawResList" :key="index">
                          <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                          <i class="el-icon-error upload-file" @click="expertClickDel(index)" v-if="dialogEdit"></i>
                        </span>
                        </el-col>
                      </el-row>
                    </div>
                  </el-form-item>
                  <el-form-item label="评标报告:" prop="bidEvalReport" ref="bidEvalReport">
                    <el-upload
                      v-if="dialogEdit"
                      class="upload-demo"
                      :action="upLoad"
                      :show-file-list="false"
                      :before-upload="beforeUpload"
                      :on-success="handleBidEvalSuccess"
                      :file-list="bidEvalReportList">
                      <el-button size="small" type="success" class="upload-btn">上传文件</el-button>
                    </el-upload>
                    <!-- 上传附件列表 -->
                    <div class="apply-upload">
                      <el-row>
                        <el-col :span="12">
                        <span class="file-list" v-for="(item, index) in bidEvalReportList" :key="index">
                          <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                          <i class="el-icon-error upload-file" @click="bidEvalClickDel(index)" v-if="dialogEdit"></i>
                        </span>
                        </el-col>
                      </el-row>
                    </div>
                  </el-form-item>
                  <el-form-item class="form-last">
                    <el-button type="primary" @click="bidEvalSubmitForm('bidEvalRuleForm')" v-if="dialogEdit">保 存</el-button>
                  </el-form-item>
                </el-form>
              </el-tab-pane>
              <!-- 评标end -->
            </el-tabs>
          </el-collapse-item>
        </el-collapse>
        <div class="bottom-submit-btn">
          <el-button type="success" @click="submitForm" v-if="dialogEdit">评标结束</el-button>
        </div>
      </div>
    </div>

    <!-- 弹窗区域start -->
    <div class="dialog-box">
      <!-- 添加/编辑专家弹窗start -->
      <el-dialog
        :title="dialogExpertTitle"
        :visible.sync="dialogExpertVisible"
        @close="cancelExpertClick"
        width="35%">
        <div class="dialog-box-form">
          <el-form :model="expertForm" :rules="expertRules" ref="expertForm" label-width="120px">
            <el-form-item label="身份证号:" prop="idCard">
              <el-input v-model="expertForm.idCard" style="width: 260px" @blur="idCardBlur"></el-input>
            </el-form-item>
            <el-form-item label="姓名:" prop="name">
              <el-input v-model="expertForm.name" style="width: 260px" :disabled="disabled"></el-input>
            </el-form-item>
            <el-form-item label="性别:" prop="sex">
              <el-select v-model="expertForm.sex" clearable placeholder="请选择性别" style="width: 260px" :disabled="disabled">
                <el-option
                  v-for="item in sexList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="单位:" prop="enterpriseName">
              <el-input v-model="expertForm.enterpriseName" style="width: 260px" :disabled="disabled"></el-input>
            </el-form-item>
            <el-form-item label="地区:" prop="cityId">
              <el-select v-model="expertForm.cityId" clearable placeholder="请选择" style="width: 260px" :disabled="disabled">
                <el-option
                  v-for="item in cityIdList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="专家类别:" prop="expertSources">
              <el-select v-model="expertForm.expertSources" clearable placeholder="请选择专家类别" style="width: 260px" :disabled="disabled">
                <el-option
                  v-for="item in typeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="专业:"  prop="classify">
              <el-row>
                <el-col :span="24">
                  <el-cascader
                    :disabled="disabled"
                    style="width: 260px;"
                    expand-trigger="hover"
                    :clearable="true"
                    change-on-select
                    :options="cascaderSelectData"
                    v-model="expertForm.classify"
                    @change="handleMajorChange">
                  </el-cascader>
                </el-col>
              </el-row>
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
          <el-button type="primary" @click="saveExpertClick('expertForm')">保 存</el-button>
          <el-button @click="cancelExpertClick()">取 消</el-button>
        </span>
      </el-dialog>
      <!-- 添加/编辑专家弹窗end -->

      <!-- 专家-查看弹窗start -->
      <el-dialog
        title="查看"
        :visible.sync="lookDialogExpertVisible"
        width="30%">
        <div class="dialog-box-form">
          <el-form :model="lookExpertForm" label-width="100px">
            <el-form-item label="姓名:">
              <span>{{lookExpertForm.name}}</span>
            </el-form-item>
            <el-form-item label="身份证号:" prop="idCard">
              <span>{{lookExpertForm.idCard}}</span>
            </el-form-item>
            <el-form-item label="性别:" prop="sex">
              <span>{{lookExpertForm.sex | filterSex}}</span>
            </el-form-item>
            <el-form-item label="单位:" prop="enterpriseName">
              <span>{{lookExpertForm.enterpriseName}}</span>
            </el-form-item>
            <el-form-item label="地区:" prop="cityId">
              <span>{{lookExpertForm.cityId | filterCity}}</span>
            </el-form-item>
            <el-form-item label="专家类别:" prop="expertSources">
              <span>{{lookExpertForm.expertSources | filterSources}}</span>
            </el-form-item>
            <el-form-item label="专业:" prop="expertclassify">
              <span>{{lookExpertForm.expertclassify}}</span>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
      <!-- 专家-查看弹窗end -->

      <!-- 评标结果设置弹窗start -->
      <el-dialog
        title="评标结果设置"
        :visible.sync="dialogBidResVisible"
        width="60%">
        <div class="dialog-box-form">
          <el-form :model="bidResultForm" label-width="100px" :rules="bidResultRules" ref="bidResultForm">
            <el-form-item label="招标项目名称:" prop="bidProName">
              <span>{{bidResultForm.projectName}}</span>
            </el-form-item>
            <el-form-item label="招标项目编号:" prop="bidProCode">
              <span>{{bidResultForm.codeUser}}</span>
            </el-form-item>
            <el-form-item label="标段名称:" prop="bidPhaseName">
              <span>{{bidResultForm.sectionName}}</span>
            </el-form-item>
            <!--<el-form-item label="评标办法:" prop="bidMethod">
              <span>{{bidResultForm.evaluatingMethod}}</span>
            </el-form-item>-->
            <div class="table-result">
              <el-table
                border
                :data="bidResultForm.resultSetTableData"
                style="width: 100%">
                <el-table-column
                  type="index"
                  label="序号"
                  align="center"
                  :index="indexResultSetMethod"
                  width="55">
                </el-table-column>
                <el-table-column
                  label="报名人"
                  prop="bidderName"
                  align="left">
                </el-table-column>
                <el-table-column
                  label="是否投标"
                  prop="isJoinBid"
                  align="left">
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.isJoinBid" @change="changeIsJoinBId(scope.row, scope.$index)"></el-checkbox>
                  </template>
                </el-table-column>
                <el-table-column
                  label="投标报价(万元)"
                  prop="bidPrice"
                  align="left">
                  <template slot-scope="scope">
                    <el-form-item
                      v-if="scope.row.isJoinBid"
                      :prop="'resultSetTableData.' + scope.$index + '.bidPrice'"
                      :rules='bidResultRules.bidPrice'
                      class="vilitate-col">
                      <el-input v-model="scope.row.bidPrice" placeholder="请输入内容"></el-input>
                    </el-form-item>
                    <el-form-item
                      v-if="!scope.row.isJoinBid"
                      class="vilitate-col">
                      <el-input v-model="scope.row.bidPrice" placeholder="请输入内容"></el-input>
                    </el-form-item>
                  </template>
                </el-table-column>
                <el-table-column
                  label="工期(交货期)"
                  prop="projectTimeLimit"
                  align="left">
                  <template slot-scope="scope">
                    <el-form-item
                      v-if="scope.row.isJoinBid"
                      :prop="'resultSetTableData.' + scope.$index + '.projectTimeLimit'"
                      :rules='bidResultRules.projectTimeLimit'
                      class="vilitate-col">
                      <el-input v-model="scope.row.projectTimeLimit" placeholder="请输入内容"></el-input>
                    </el-form-item>
                    <el-form-item
                      v-if="!scope.row.isJoinBid"
                      class="vilitate-col">
                      <el-input v-model="scope.row.projectTimeLimit" placeholder="请输入内容"></el-input>
                    </el-form-item>
                  </template>
                </el-table-column>
                <el-table-column
                  label="得分"
                  prop="score"
                  align="left">
                  <template slot-scope="scope">
                    <el-form-item
                      v-if="scope.row.isJoinBid"
                      :prop="'resultSetTableData.' + scope.$index + '.score'"
                      :rules='bidResultRules.score'
                      class="vilitate-col">
                      <el-input v-model="scope.row.score" placeholder="请输入内容"></el-input>
                    </el-form-item>
                    <el-form-item
                      v-else
                      class="vilitate-col">
                      <el-input v-model="scope.row.score" placeholder="请输入内容"></el-input>
                    </el-form-item>
                  </template>
                </el-table-column>
                <el-table-column
                  label="投标文件"
                  prop="fileInformation"
                  align="left">
                  <template slot-scope="scope">
                    <el-upload
                      class="upload-demo"
                      :action="upLoad"
                      :on-change="handleBidFileChange"
                      :show-file-list="false"
                      :before-upload="bidderBeforeUpload"
                      :on-success="handleBidFileSuccess"
                      :file-list="bidFileList">
                      <el-button size="small" type="success" class="upload-btn" @click="click(scope.$index)">{{(bidResultForm.resultSetTableData[scope.$index].fileInformation?'替换':'上传')}}</el-button>
                    </el-upload>
                    <el-button
                      type="text"
                      @click="downloadFile(bidResultForm.resultSetTableData[scope.$index].fileInformation.fileName,bidResultForm.resultSetTableData[scope.$index].fileInformation.relativePath)">
                      <span
                        v-if="bidResultForm.resultSetTableData[scope.$index].fileInformation"
                        style="display:inline-block;float:left;padding-right:20px;">
                        {{bidResultForm.resultSetTableData[scope.$index].fileInformation.fileName}}
                      </span>
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="saveBidResSetClick('bidResultForm')">保 存</el-button>
          <el-button @click="cancelBidResSetClick">取 消</el-button>
        </span>
      </el-dialog>
      <!-- 评标结果设置弹窗end -->
    </div>
    <!-- 弹窗区域end -->
  </div>
</template>
<script>
import {bidfile, expertmanage, openbid, tenderproject, bidderInfo, qualfileResult} from 'api/index'
import {formatDate} from 'common/js/date'
import {commonJs, downloadFile} from 'common/js/common'
import {expertclassify} from 'api/resource/expertmanage/expertclassify'
export default {
  data () {
    var validateIdCard = (rule, value, callback) => {
      if (value && (!(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/).test(value))) {
        callback(new Error('身份证输入不合法，请重新录入！'))
      } else {
        callback()
      }
    }
    var validateScore = (rule, value, callback) => {
      if (value && (!(/^(?:[1-9]?\d|100)$/).test(value))) {
        callback(new Error('得分只能输入0-100的数字'))
      } else {
        callback()
      }
    }
    var validateBidPrice = (rule, value, callback) => {
      if (value && (!(/^\d{1,14}(\.\d{1,6})?$/).test(value))) {
        callback(new Error('投标报价只能输入小数点前不超过14位且小数点后不超过6位的数字'))
      } else {
        callback()
      }
    }
    return {
      upLoad: commonJs.fileUploadUrl,
      cascaderSelectData: expertclassify,
      disabled: true, // 身份证号未录入时，其他输入框均为禁用状态
      dialogEdit: false, // 是否可编辑
      selectTableData: [], // 弹窗-下拉选-表格
      cascaderSelectValue: [],
      sectionData: [],
      openRuleForm: {}, // 开标-表单
      openRules: {
        openFile: [
          {required: true, message: '请上传开标一览表文件', trigger: 'blur'}
        ]
      }, // 开标-规则
      openFileList: [], // 开标一览表上传列表
      bidEvalRuleForm: {}, // 评标-表单
      bidEvalRules: {
        expertDrawRes: [
          {required: true, message: '请上传专家抽取结果文件', trigger: 'blur'}
        ],
        bidEvalReport: [
          {required: true, message: '请上传评标报告文件', trigger: 'blur'}
        ]
      }, // 评标-规则
      bidResultRules: {
        bidPrice: [
          {required: true, message: '请填写投标报价', trigger: 'blur'},
          {validator: validateBidPrice}
        ],
        projectTimeLimit: [
          {required: true, message: '请填写工期（交货期）', trigger: 'blur'},
          { min: 1, max: 20, message: '请输入1~20个字符', trigger: 'blur' }
        ],
        score: [
          {required: true, message: '请填写得分', trigger: 'blur'},
          {validator: validateScore}
        ]
      },
      bidExpertTableData: [], // 评标专家-表格数据
      expertDrawResList: [], // 专家抽取结果-上传列表
      bidResultTableData: [], // 评标结果-表格数据
      bidEvalReportList: [], // 评标报告-上传列表
      collapseActiveName: '',
      dialogExpertVisible: false, // 添加/编辑专家弹窗
      dialogExpertTitle: '添加',
      expertForm: {}, // 添加/编辑专家-表单
      expertRules: {
        name: [
          { required: true, message: '请填写姓名', trigger: 'blur' },
          { min: 1, max: 20, message: '请输入1~20个字符', trigger: 'blur' }
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
          { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }
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
      }, // 添加/编辑专家-规则
      majorOptions: [], // 专业-数据
      lookDialogExpertVisible: false, // 查看专家-弹窗
      lookExpertForm: {}, // 查看专家-表单
      dialogBidResVisible: false, // 评标结果设置-弹窗
      bidResultForm: {
        resultSetTableData: [] // 评标结果设置-表格数据
      }, // 评标结果设置-表单
      tabsActiveName: 'first', // tabs默认选中项
      bidFileList: [],
      activeIndex: 0,
      currentPage: 1,
      bidderFileIndex: -1,
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
      classifyData: [], // 专家专业数组
      progressStage: null, // 项目进行状态
      noticeProgress: null, // 标段锁定进行状态
      notLockedCount: 0, // 未锁定的标段数量
      currentProgressStage: 5, // 开评标阶段
      isPrequalification: null // 是否资格预审
    }
  },
  props: {
    noticeCode: String,
    operationCheckFlag: Boolean
  },
  computed: {
    backData () {
      return this.selectTableData
    }
  },
  created () {
    if (this.$route.query.key) {
      this.noticeCode = this.$route.query.noticeCode
    }
    this.getNoticeProgress()
  },
  filters: {
    filterBidOpenTime (val) {
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
    }
  },
  /** 方法集 */
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    indexExpertMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    indexBidResultMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    indexResultSetMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    filterSex (row, column) {
      let sex = row.sex
      if (sex === 1) {
        return '男'
      } else if (sex === 2) {
        return '女'
      }
    },
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
    filterIsJoinBid (row, column) {
      let isJoinBid = row.isJoinBid
      if (isJoinBid === true || isJoinBid === 1) {
        return '是'
      } else {
        return '否'
      }
    },
    /** tabs切换事件 */
    tabsClick (tab, event) {
      if (Object.is(tab.name, 'first')) {
        this.getOpenRecord()
      } else if (Object.is(tab.name, 'second')) {
        this.getTenderDoc()
        this.getExpertInfoList()
        this.getTenderProject()
      }
    },
    /**
     * 初始化选中标段信息
     */
    getDataBySection (code, index) {
      this.expertDrawResList = []
      this.bidEvalReportList = []
      this.bidExpertTableData = []
      this.expertDrawResList = []
      this.bidEvalReportList = []
      this.bidResultTableData = []
      this.bidResultForm = {
        resultSetTableData: []
      }
      this.bidEvalRuleForm = {}
      this.tabsActiveName = 'first'
      this.activeIndex = index
      this.collapseActiveName = code
      if (this.collapseActiveName) {
        this.getOpenRecord()
      }
    },
    /** 身份证号输入框失去焦点时触发 */
    idCardBlur () {
      if (this.expertForm.idCard) {
        this.disabled = false
        let obj = {
          idCard: this.expertForm.idCard,
          expertType: 1
        }
        expertmanage.getByIdCard(obj).then(res => {
          let expertInfo = res.data.expertInfo
          if (expertInfo) {
            this.expertForm = expertInfo
            this.expertForm.classify = [expertInfo.expertClassifyList[0].firstClassify, expertInfo.expertClassifyList[0].secondClassify, expertInfo.expertClassifyList[0].thirdClassify, expertInfo.expertClassifyList[0].fourClassify]
            this.selectTableData = this.editProTabShow(expertInfo)
          }
        })
      }
    },
    /** 校验是否存在重复的专业 */
    checkClassify () {
      // 将专业放入classifyData数组中，每次校验最新录入的一个专业是否与以上录入的重复
      // 若重复，则不可新增专业，且提示“存在重复的专业,请移除重复专业!”
      this.classifyData = []
      if (this.expertForm.classify) {
        let classify = {
          professional: this.expertForm.classify
        }
        this.classifyData.push(classify)
      }
      if (this.selectTableData.length > 0) {
        for (let i = 0; i < this.selectTableData.length; i++) {
          if (this.selectTableData[i].professional) {
            let classify = {
              professional: this.selectTableData[i].professional
            }
            this.classifyData.push(classify)
          }
        }
      }
      if (this.classifyData.length > 0) {
        let length = this.classifyData.length
        for (let i = 0; i < length - 1; i++) {
          if (this.classifyData[i].professional[0] === this.classifyData[length - 1].professional[0] &&
            this.classifyData[i].professional[1] === this.classifyData[length - 1].professional[1] &&
            this.classifyData[i].professional[2] === this.classifyData[length - 1].professional[2] &&
            this.classifyData[i].professional[3] === this.classifyData[length - 1].professional[3]) {
            this.$message.error('存在重复的专业,请移除重复专业!')
            return false
          }
        }
      }
      return true
    },
    /** 专家专业新增下拉框 */
    addSelectClick () {
      if (this.checkClassify()) {
        let obj = {}
        this.selectTableData.push(obj)
      }
    },
    /** 专家专业删除下拉框 */
    deleteRow (index, rows) {
      rows.splice(index, 1)
      this.cascaderSelectValue.splice(index, 1)
    },
    /** 校验附件 */
    validFile () {
      // 开标一览表
      if (this.openFileList.length > 0) {
        this.$set(this.openRuleForm, 'openFile', 'openFile')
      } else {
        this.$set(this.openRuleForm, 'openFile', '')
      }
      if (this.openRuleForm.openFile) {
        this.$refs['openFile'][this.activeIndex].clearValidate()
      }
    },
    /** 校验附件 */
    validBidFile () {
      // 专家抽取结果
      if (this.expertDrawResList.length > 0) {
        this.$set(this.bidEvalRuleForm, 'expertDrawRes', 'expertDrawRes')
      } else {
        this.$set(this.bidEvalRuleForm, 'expertDrawRes', '')
      }
      if (this.bidEvalRuleForm.expertDrawRes) {
        this.$refs['expertDrawRes'][this.activeIndex].clearValidate()
      }
      // 评标报告
      if (this.bidEvalReportList.length > 0) {
        this.$set(this.bidEvalRuleForm, 'bidEvalReport', 'bidEvalReport')
      } else {
        this.$set(this.bidEvalRuleForm, 'bidEvalReport', '')
      }
      if (this.bidEvalRuleForm.bidEvalReport) {
        this.$refs['bidEvalReport'][this.activeIndex].clearValidate()
      }
    },
    /** 附件格式为pdf */
    beforeUpload (file) {
      const isPDF = file.type === 'application/pdf'
      if (!isPDF) {
        this.$message.error('上传文件只能为PDF格式！')
      }
      return isPDF
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
    /** 附件格式为pdf或压缩包 */
    bidderBeforeUpload (file) {
      let name = file.name.split('.')
      const isRar = name[name.length - 1] === 'rar'
      const isPDF = file.type === 'application/pdf'
      const isZip = file.type === 'application/x-zip-compressed'
      if (!isPDF && !isZip && !isRar) {
        this.$message.error('上传文件只能为PDF格式或压缩包！')
      }
      return isPDF || isZip || isRar
    },
    /**
     * 下载附件
     */
    downloadFile,
    /** 删除开标一览表附件 */
    openFileClickDel (index) {
      this.openFileList.splice(index, 1)
      this.validFile()
    },
    /** 删除专家抽取结果附件 */
    expertClickDel (index) {
      this.expertDrawResList.splice(index, 1)
      this.validBidFile()
    },
    /** 删除评标报告附件 */
    bidEvalClickDel (index) {
      this.bidEvalReportList.splice(index, 1)
      this.validBidFile()
    },
    /** 开标上传-开标一览表 */
    handleSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.openFileList = []
        this.openFileList.push(file.response.fileInformation)
      }
      this.validFile()
    },
    /** 专家抽取结果-上传 */
    handleExpertSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.expertDrawResList = []
        this.expertDrawResList.push(file.response.fileInformation)
      }
      this.validBidFile()
    },
    /** 评标报告-上传 */
    handleBidEvalSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        let fileInformations = []
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
        this.bidEvalReportList = fileInformations // 获得目前项目中所有的附件信息
      }
      this.validBidFile()
    },
    /** 投标文件-上传 */
    handleBidFileSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.bidResultForm.resultSetTableData[this.bidderFileIndex].fileInformation = file.response.fileInformation
      }
    },
    click (index) {
      this.bidderFileIndex = index
    },
    handleBidFileChange (file, fileList) {
      if (fileList.length > 1) {
        this.bidFileList = []
      }
      this.bidFileList.push(file)
    },
    /** 添加专家 */
    AddExpert () {
      this.disabled = true
      this.dialogExpertTitle = '添加'
      // 打来弹窗
      this.dialogExpertVisible = true
    },
    /** 修改展示专业-表格 */
    editProTabShow (row) {
      let arr = []
      let rowL = row.expertClassifyList
      for (let i = 0; i < rowL.length; i++) {
        if (i > 0) {
          arr[i - 1] = {professional: [rowL[i].firstClassify, rowL[i].secondClassify, rowL[i].thirdClassify, rowL[i].fourClassify]}
        }
      }
      return arr
    },
    /**
     * 专业信息
     */
    saveClassify () {
      this.expertForm.expertClassifyList = this.selectTableData.map(item => { return item })
      if (this.selectTableData) {
        for (let i = 0; i < this.selectTableData.length; i++) {
          this.expertForm.expertClassifyList[i].firstClassify = this.selectTableData[i].professional[0]
          this.expertForm.expertClassifyList[i].secondClassify = this.selectTableData[i].professional[1]
          this.expertForm.expertClassifyList[i].thirdClassify = this.selectTableData[i].professional[2]
          this.expertForm.expertClassifyList[i].fourClassify = this.selectTableData[i].professional[3]
        }
      }
      // 合并数据
      let tempArr = this.expertForm.classify
      this.expertForm.expertClassifyList.push({firstClassify: tempArr[0], secondClassify: tempArr[1], thirdClassify: tempArr[2], fourClassify: tempArr[3]})
    },
    /** 保存-添加/编辑表单 */
    saveExpertClick (val) {
      if (this.checkClassify()) {
        this.$refs[val].validate((vaild) => {
          if (vaild) {
            this.saveClassify()
            if (this.expertForm.expertType === 1) {
              this.expertForm.objectId = null
              this.expertForm.code = ''
            }
            this.expertForm.provinceId = '4'
            this.expertForm.expertType = 3
            this.expertForm.relatedNoticeCode = this.noticeCode
            this.expertForm.relatedSectionCode = this.collapseActiveName
            expertmanage.save(this.expertForm).then(() => {
              this.cancelExpertClick()
            })
          } else {
            return false
          }
        })
      }
    },
    /** 添加专家-取消 */
    cancelExpertClick () {
      this.$refs['expertForm'].resetFields()
      this.expertForm = {}
      this.selectTableData = []
      this.dialogExpertVisible = false
      this.getExpertInfoList()
    },
    /** 开标-保存 */
    openSubmitForm (val) {
      this.validFile()
      this.$refs[val][this.activeIndex].validate((vaild) => {
        if (vaild) {
          this.openRuleForm.noticeCode = this.noticeCode
          this.openRuleForm.sectionCode = this.collapseActiveName
          this.openRuleForm.bidOpeningTime = this.openRuleForm.bidOpenTime
          this.openRuleForm.fileInformation = this.openFileList[0]
          openbid.saveOpenRecord(this.openRuleForm).then(() => {
            this.getOpenRecord()
          })
        }
      })
    },
    /** 评标-保存 */
    bidEvalSubmitForm (val) {
      this.validBidFile()
      this.$refs[val][this.activeIndex].validate((vaild) => {
        if (vaild) {
          if (this.bidExpertTableData === null || this.bidExpertTableData === undefined ||
            this.bidExpertTableData.length < 5 || this.bidExpertTableData.length % 2 === 0) {
            this.$message.error('评标专家需至少5个且为奇数个')
            return false
          }
          if (this.bidResultTableData.length < 1) {
            this.$message.error('评标结果未设置，不可保存！')
            return false
          }
          this.bidEvalRuleForm.noticeCode = this.noticeCode
          this.bidEvalRuleForm.sectionCode = this.collapseActiveName
          let evaluationResultFileList = this.bidEvalRuleForm.evaluationResultFileList
          if (evaluationResultFileList) {
            for (let i = 0; i < evaluationResultFileList.length; i++) {
              if (evaluationResultFileList[i].evaluationResultFileType === 1) {
                evaluationResultFileList[i].fileInformationList = this.expertDrawResList
              } else if (evaluationResultFileList[i].evaluationResultFileType === 2) {
                evaluationResultFileList[i].fileInformationList = this.bidEvalReportList
              }
            }
          } else {
            let evaluationResultFileList = []
            let obj = {
              evaluationResultFileType: 1,
              fileInformationList: this.expertDrawResList
            }
            let obj2 = {
              evaluationResultFileType: 2,
              fileInformationList: this.bidEvalReportList
            }
            evaluationResultFileList.push(obj)
            evaluationResultFileList.push(obj2)
            this.$set(this.bidEvalRuleForm, 'evaluationResultFileList', evaluationResultFileList)
          }
          if (this.bidResultTableData) {
            for (let i = 0; i < this.bidResultTableData.length; i++) {
              if (this.bidResultTableData[i].isJoinBid === true ||
                this.bidResultTableData[i].isJoinBid === 1) {
                this.bidResultTableData[i].isJoinBid = 1
              } else {
                this.bidResultTableData[i].isJoinBid = 0
              }
            }
          }
          this.$set(this.bidEvalRuleForm, 'evaluationResultSublists', this.bidResultTableData)
          openbid.saveEvaluationResult(this.bidEvalRuleForm).then(() => {
            this.getEvaluationResult()
          })
        }
      })
    },
    /** 提交 */
    submitForm () {
      openbid.updateProgressStage(this.noticeCode).then(() => {
        this.submitDisPlay()
      })
    },
    /** 评标结果设置 */
    setBidResult () {
      // 打开弹窗
      this.dialogBidResVisible = true
    },
    /** 评标专家-表格操作 */
    handleTableClick (row, type) {
      if (Object.is(type, 'look')) {
        // 打开弹窗
        this.lookDialogExpertVisible = true
        // 查询单条
        this.getOneBidExpert(row.objectId)
      } else if (Object.is(type, 'edit')) {
        this.disabled = false
        this.dialogExpertVisible = true
        this.expertForm = row
        this.expertForm.classify = [row.expertClassifyList[0].firstClassify, row.expertClassifyList[0].secondClassify, row.expertClassifyList[0].thirdClassify, row.expertClassifyList[0].fourClassify]
        this.selectTableData = this.editProTabShow(row)
      } else if (Object.is(type, 'del')) {
        this.deleteClick(row)
      }
    },
    /** 获得一条数据-评标专家 */
    getOneBidExpert (id) {
      expertmanage.getOne(id).then(res => {
        let jsonForm = res.data.expertInfo
        this.lookExpertForm = jsonForm
        // 转换专业
        let arr = jsonForm.expertClassifyList.map((item, index) => {
          let str = ''
          let firstStr = this.getLabelName(expertclassify, item.firstClassify)
          let secondStr = this.getLabelName(expertclassify, item.secondClassify)
          let thirdStr = this.getLabelName(expertclassify, item.thirdClassify)
          let fourStr = this.getLabelName(expertclassify, item.fourClassify)
          if (firstStr.node) {
            str += `${firstStr.node.label}`
          }
          if (secondStr.node) {
            str += `/${secondStr.node.label}`
          }
          if (thirdStr.node) {
            str += `/${thirdStr.node.label}`
          }
          if (fourStr.node) {
            str += `/${fourStr.node.label}`
          }
          return str
        })
        this.lookExpertForm.expertclassify = arr.toString()
      })
    },
    /** 判断当前值是否勾选 */
    changeIsJoinBId (row, index) {
      this.$refs['bidResultForm'].clearValidate()
    },
    /** 评标结果设置-弹窗保存 */
    saveBidResSetClick (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.bidResultTableData = this.bidResultForm.resultSetTableData.map((item) => {
            return item
          })
          if (this.bidResultTableData.length > 0) {
            this.bidderResultRank(this.bidResultTableData)
          }
          this.dialogBidResVisible = false
        }
      })
    },
    /** 评标结果设置-弹窗取消 */
    cancelBidResSetClick () {
      this.dialogBidResVisible = false
      this.getEvaluationResult()
    },
    /** 依据评标得分对结果进行排序 */
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
    /** 弹窗-专业-改变事件 */
    handleMajorChange () {},
    /** 删除弹窗 */
    deleteClick (data) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        expertmanage.delete(data.objectId).then(() => {
          this.getExpertInfoList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    /** 查询专家信息 */
    getExpertInfoList () {
      let obj = {
        relatedNoticeCode: this.noticeCode,
        relatedSectionCode: this.collapseActiveName,
        expertType: 3
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
          this.bidExpertTableData = handleData
        } else {
          return false
        }
      })
    },
    /** 查询招标文件信息 */
    getTenderDoc () {
      if (this.collapseActiveName) {
        bidfile.getByCode(this.noticeCode, this.collapseActiveName).then((res) => {
          let tenderDoc = res.data.tenderDoc
          if (tenderDoc) {
            this.$set(this.openRuleForm, 'bidOpenTime', tenderDoc.bidOpenTime)
            this.bidResultForm.evaluatingMethod = tenderDoc.evaluatingMethod
          } else {
            return false
          }
        })
      }
    },
    /** 查询开标信息 */
    getOpenRecord () {
      this.openRuleForm = {}
      this.openFileList = []
      let obj = {
        noticeCode: this.noticeCode,
        sectionCode: this.collapseActiveName
      }
      openbid.getOpenRecord(obj).then((res) => {
        let result = res.data.openingRecord
        if (result) {
          this.openRuleForm = result
          this.openRuleForm.bidOpenTime = this.openRuleForm.bidOpeningTime
          this.openFileList[0] = this.openRuleForm.fileInformation
        } else {
          // 开标时间
          this.getTenderDoc()
        }
      })
    },
    /** 查询标段信息 */
    getSections () {
      this.sectionData = []
      bidfile.getsections(this.noticeCode).then((res) => {
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
                // 默认展开第一个标段信息
                this.collapseActiveName = this.sectionData[0].code
                this.bidResultForm.sectionName = this.sectionData[0].name
                if (this.collapseActiveName) {
                  // 开标一览表
                  this.getOpenRecord()
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
    /** 查询评标信息 */
    getEvaluationResult () {
      // 标段名称
      for (let i = 0; i < this.sectionData.length; i++) {
        if (this.sectionData[i].code === this.collapseActiveName) {
          this.bidResultForm.sectionName = this.sectionData[i].name
        }
      }
      let obj = {
        noticeCode: this.noticeCode,
        sectionCode: this.collapseActiveName
      }
      openbid.getEvaluationResult(obj).then((res) => {
        // 返回的结果json
        let resultJson = res.data.evaluationResult
        if (resultJson) {
          // 评标表单数据设置
          this.setBidEvalRuleForm(resultJson)
          // 评标结果设置-弹窗-表格数据
          this.setBidResultDialog(resultJson)
        }
      }).then((res) => {
        if (this.bidResultTableData.length < 1) {
          this.getBidderInfos()
        }
      })
    },
    /** 评标-表单数据设置 */
    setBidEvalRuleForm (json) {
      let resultJson = json || {}
      // 评标-表单
      this.bidEvalRuleForm = resultJson
      // 评标-表单-表格数据
      this.bidResultTableData = resultJson.evaluationResultSublists
      this.bidderResultRank(this.bidResultTableData)
      let evaluationResultFileList = resultJson.evaluationResultFileList
      if (evaluationResultFileList) {
        for (let i = 0; i < evaluationResultFileList.length; i++) {
          if (evaluationResultFileList[i].evaluationResultFileType === 1) {
            this.expertDrawResList = evaluationResultFileList[i].fileInformationList
          } else if (evaluationResultFileList[i].evaluationResultFileType === 2) {
            this.bidEvalReportList = evaluationResultFileList[i].fileInformationList
          }
        }
      }
    },
    /** 设置-评标结果设置弹窗数据 */
    setBidResultDialog (json) {
      let resultJson = JSON.stringify(json)
      // 评标结果设置-弹窗-表格数据
      this.bidResultForm.resultSetTableData = []
      // 防止弹窗表单数据影响表格数据
      let newArr = JSON.parse(resultJson).evaluationResultSublists
      this.bidResultForm.resultSetTableData = newArr
      if (newArr) {
        if (newArr.length > 0) {
          for (let i = 0; i < newArr.length; i++) {
            if (newArr[i].isJoinBid === 1) {
              newArr[i].isJoinBid = true
            } else {
              newArr[i].isJoinBid = false
            }
            newArr[i].bidPrice = Number(newArr[i].bidPrice)
            newArr[i].score = Number(newArr[i].score)
          }
        }
      }
    },
    /** 查询投标人信息 */
    getBidderInfos () {
      let result = []
      if (this.isPrequalification) {
        let query = {
          noticeCode: this.noticeCode,
          sectionCode: this.collapseActiveName
        }
        qualfileResult.getBidderInfo(query).then((res) => {
          let qualifiPreResult = res.data.qualifiPreResult
          if (qualifiPreResult) {
            result = qualifiPreResult.qualifiPreResultSublistList
            if (result) {
              // 设置是否投标默认选中
              this.bidResultForm.resultSetTableData = result.map((item) => {
                let bidderInfo = {}
                bidderInfo.bidderName = item.bidderName
                bidderInfo.bidderCode = item.bidderCode
                bidderInfo.isJoinBid = true
                return bidderInfo
              })
            } else {
              return false
            }
          }
        })
      } else {
        bidderInfo.getByRelatedCode(this.noticeCode, this.collapseActiveName).then((res) => {
          result = res.data.bidderInfoList
          if (result) {
            // 设置是否投标默认选中
            this.bidResultForm.resultSetTableData = result.map((item) => {
              let bidderInfo = {}
              bidderInfo.bidderName = item.bidderName
              bidderInfo.bidderCode = item.code
              bidderInfo.isJoinBid = true
              return bidderInfo
            })
          } else {
            return false
          }
        })
      }
    },
    /** 查询招标项目信息 */
    getTenderProject () {
      tenderproject.getOneByCode(this.$route.query.tenderProjectCode).then((res) => {
        let tenderProject = res.data.tenderProject
        if (tenderProject) {
          this.isPrequalification = tenderProject.isPrequalification
          let selectApply = tenderProject.selectApply
          if (selectApply) {
            this.bidResultForm.projectName = selectApply.projectName
            this.bidResultForm.codeUser = selectApply.codeUser
          }
        } else {
          return false
        }
      }).then((res) => {
        this.getEvaluationResult()
      })
    },
    /** 查询项目进行状态 */
    getNoticeProgress () {
      let obj = {
        tenderProjectCode: this.$route.query.tenderProjectCode,
        noticeCode: this.noticeCode
      }
      openbid.getNoticeProgress(obj).then((res) => {
        let result = res.data.noticeProgress
        if (result) {
          this.progressStage = result.progressStage
        } else {
          return false
        }
      }).then(res => {
        let enterpriseType = this.$store.getters.authUser.enterpriseType
        if ((enterpriseType === 4) ||
          (this.progressStage > 5 && enterpriseType !== 4)) {
          this.getSections()
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
        if (this.progressStage === 5 && enterpriseType === 4) {
          this.dialogEdit = true
        } else {
          this.dialogEdit = false
        }
        // 若项目进行状态为候选人公示阶段，则候选人公示按钮高亮
        if (this.progressStage === 6) {
          this.$emit('noticeProgress', 6)
        }
      }
      // 招标变更查询（只可查看）
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
    // 转成label值
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
    /*
     * treeData [原始数据集]
     * key 传入的key值
     */
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
#openbid-page {
  .openbid-cont {
    .openbid-tit {
      height: 50px;
      line-height: 50px;
      text-align:left;
      padding: 0 20px;
      background:#F7F6FB;
      span {
        font-weight: 800;
      }
    }
    .openbid-box {
      padding: 20px;
      .card-tabs {
        .el-form-item__content {
          text-align: left;
        }
      }
      .el-icon-error {
        color: red;
      }
      .bottom-submit-btn {
        padding: 20px;
        button {
          width: 160px;
        }
      }
      .bid-eval-tab {
        p {
          margin: 5px;
          overflow: hidden;
          white-space: nowrap;
          text-overflow: ellipsis;
        }
      }
      /** 添加 */
      .add-btn {
        width: 140px;
        margin-bottom: 10px;
      }
      .form-last {
        .el-form-item__content {
          text-align: center;
          margin-left: 0 !important;
        }
      }
      /** 折叠面板 */
      .el-collapse-item {
        margin-bottom: 10px;
      }
      .el-collapse-item__header {
        padding-left: 20px;
        text-align: left;
        background: #F7F6FB;
        .collapse-span {
          display: block;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
      }
      .el-collapse-item__header.is-active {
        background: #ebeffe;
      }
    }
    /** 表格样式 */
    .table-boxwarp tr{
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
  }
  /** 弹窗 */
  .dialog-box {
    .dialog-box-form {
      .vilitate-col {
        .el-form-item__content {
          margin-left: 0 !important;
        }
      }
      .el-form-item__content {
        text-align: left;
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
    .el-form-item__error {
      display: none;
    }
    .el-dialog__footer {
      text-align: center;
    }
  }
}
</style>
