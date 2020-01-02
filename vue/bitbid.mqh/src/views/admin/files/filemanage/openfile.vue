<template>
  <!-- 标段文件-公开招标组件 -->
  <div class="admin-layout" id="openfile-page">
    <div class="collapse-warp">
      <el-collapse v-model="sectionCode" accordion>
        <el-collapse-item v-for="(section,index) in sectionList" :key="index" :name=section.code>
          <template slot="title">
            <span class="title-span collapse-span" @click="getDataBySection(section, index)" style="width: 100%; display: block">
              <span>{{index + 1}}</span>
              <span class="middle-span">{{section.name}}</span>
              <span>{{section.archivingStatus | filterStatus}}</span>
              <el-button type="text" @click.stop="submitCilck(section)" style="float: right;margin-right: 40px;line-height: 25px;"
                         v-if="section.archivingStatus !== 2 && $route.query.operation !== 'look'">提交</el-button>
            </span>
          </template>
          <div class="collspase-cont">
            <el-tabs v-model="activeTabName" type="card" @tab-click="handleTabsClick">
              <el-tab-pane label="资格预审文件" name="first" v-if="tenderProject.isPrequalification">
                <table class="info-table">
                  <tr>
                    <td>资格预审文件</td>
                    <td colspan="3">
                      <div class="apply-upload">
                        <el-row>
                          <el-col :span="12">
                        <span class="file-list" v-for="(item, index) in qualFileList" :key="index">
                          <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                        </span>
                          </el-col>
                        </el-row>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td>答应澄清开始时间</td>
                    <td><span>{{qualifiPreFile.clarifyStartTime | filterTime}}</span></td>
                    <td>答应澄清截止时间</td>
                    <td><span>{{qualifiPreFile.clarifyEndTime | filterTime}}</span></td>
                  </tr>
                  <tr>
                    <td>资格预审会时间</td>
                    <td><span>{{qualifiPreFile.qualifiPreTime | filterTime}}</span></td>
                    <td>资格预审会地点</td>
                    <td><span>{{qualifiPreFile.qualifiPreAddress}}</span></td>
                  </tr>
                  <tr>
                    <td>补充文件</td>
                    <td colspan="3">
                      <el-upload
                        class="upload-demo"
                        :action="upLoad"
                        :on-success="uploadQualFileSuccess"
                        :file-list="qualFileMoreList"
                        :show-file-list="false">
                        <el-button size="small" type="primary" class="upload-btn" v-if="dialogEdit">上传附件</el-button>
                      </el-upload>
                      <span class="file-list" v-for="(item, index) in qualFileMoreList" :key="index">
                        <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                        <i class="el-icon-error upload-file" @click="qualFileDel(index)" v-if="dialogEdit"></i>
                      </span>
                    </td>
                  </tr>
                </table>
                <div class="tabpane-btn">
                  <el-button type="primary" @click="qualFileSave()" v-if="dialogEdit">保 存</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane label="答疑补遗（资审）" name="second" v-if="tenderProject.isPrequalification">
                <el-table
                  border
                  :data="answerTableData"
                  :row-class-name="tableRowClassName"
                  style="width: 100%">
                  <el-table-column
                    type="index"
                    label="序号"
                    :index="indexMethod"
                    width="55">
                  </el-table-column>
                  <el-table-column
                    prop="clarifyType"
                    label="澄清类型"
                    :formatter="filterClarifyType"
                    align="center">
                  </el-table-column>
                  <el-table-column
                    prop="sectionName"
                    label="澄清标段"
                    align="center">
                  </el-table-column>
                  <el-table-column
                    prop="bidderNameList"
                    label="质疑单位"
                    align="center">
                  </el-table-column>
                  <el-table-column
                    prop="fileInformationList"
                    label="澄清文件"
                    align="center">
                    <template slot-scope="scope">
                      <span class="file-list" v-for="(item, index) in scope.row.fileInformationList" :key="index">
                          <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">
                            {{item.fileName}}</el-button>
                      </span>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="bidOpenTime"
                    label="开标时间"
                    :formatter="filterDate"
                    align="center">
                  </el-table-column>
                </el-table>
                <el-form label-width="120px" class="demo-ruleForm tabs-pane-form">
                  <el-form-item label="补充文件:">
                    <el-upload
                      class="upload-demo"
                      :action="upLoad"
                      :on-success="uploadanswerSuccess"
                      :file-list="answerFileMoreList"
                      :show-file-list="false">
                      <el-button size="small" type="primary" class="upload-btn" v-if="dialogEdit">上传附件</el-button>
                    </el-upload>
                    <span class="file-list" v-for="(item, index) in answerFileMoreList" :key="index">
                      <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                      <i class="el-icon-error upload-file" @click="answerFileDel(index)" v-if="dialogEdit"></i>
                    </span>
                  </el-form-item>
                </el-form>
                <div class="tabpane-btn">
                  <el-button type="primary" @click="answerFileSave()" v-if="dialogEdit">保 存</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane label="资格预审会" name="third" v-if="tenderProject.isPrequalification">
                <el-form :model="qualifiPreResult" label-width="130px" class="demo-ruleForm tabs-pane-form">
                  <el-form-item label="专家抽取结果:">
                    <div class="apply-upload">
                      <el-row>
                        <el-col :span="12">
                          <el-button type="text" style="padding-right: 20px;" @click="downloadFile(exportResultFile.fileName,
                          exportResultFile.relativePath)">{{exportResultFile.fileName}}</el-button>
                        </el-col>
                      </el-row>
                    </div>
                  </el-form-item>
                  <el-form-item label="资格预审报告:">
                    <div class="apply-upload">
                      <el-row>
                        <el-col :span="12">
                          <el-button type="text" style="padding-right: 20px;" @click="downloadFile(qualifiReportFile.fileName,
                          qualifiReportFile.relativePath)">{{qualifiReportFile.fileName}}</el-button>
                        </el-col>
                      </el-row>
                    </div>
                  </el-form-item>
                  <el-form-item label="资格预审申请文件:">
                    <el-table
                      border
                      :data="qualifiPreTableData"
                      style="width: 100%">
                      <el-table-column
                        type="index"
                        label="序号"
                        :index="indexMethod"
                        width="55">
                      </el-table-column>
                      <el-table-column
                        prop="bidderName"
                        label="报名人名称"
                        align="left">
                      </el-table-column>
                      <el-table-column
                        prop="score"
                        label="得分"
                        align="left">
                      </el-table-column>
                      <el-table-column
                        prop="fileInformation"
                        label="申请文件"
                        align="left">
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
                        align="left">
                        <template slot-scope="scope">
                          <el-button
                            v-if="scope.row.noticeFileInformation"
                            type="text" @click="downloadFile(scope.row.noticeFileInformation.fileName,scope.row.noticeFileInformation.relativePath)">
                            <span v-if="scope.row.noticeFileInformation" style="display:inline-block;float:left;padding-right:20px;">
                              {{scope.row.noticeFileInformation.fileName}}
                            </span>
                          </el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                  </el-form-item>
                  <el-form-item label="补充文件:">
                    <el-upload
                      class="upload-demo"
                      :action="upLoad"
                      :on-success="uploadQualResFileSuccess"
                      :file-list="qualResFileMoreList"
                      :show-file-list="false">
                      <el-button size="small" type="primary" class="upload-btn" v-if="dialogEdit">上传附件</el-button>
                    </el-upload>
                    <span class="file-list" v-for="(item, index) in qualResFileMoreList" :key="index">
                      <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                      <i class="el-icon-error upload-file" @click="qualResFileDel(index)" v-if="dialogEdit"></i>
                    </span>
                  </el-form-item>
                </el-form>
                <div class="tabpane-btn">
                  <el-button type="primary" @click="qualResFileSave()" v-if="dialogEdit">保 存</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane label="招标文件" name="fourth">
                <table class="info-table">
                  <tr>
                    <td>招标文件</td>
                    <td colspan="3">
                      <div class="apply-upload">
                        <el-row>
                          <el-col :span="12">
                        <span class="file-list" v-for="(item, index) in bidFileList" :key="index">
                          <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                        </span>
                          </el-col>
                        </el-row>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td>答应澄清开始时间</td>
                    <td><span>{{tenderDoc.clarifyStartTime | filterTime}}</span></td>
                    <td>答应澄清截止时间</td>
                    <td><span>{{tenderDoc.clarifyEndTime | filterTime}}</span></td>
                  </tr>
                  <tr>
                    <td>开标时间</td>
                    <td><span>{{tenderDoc.bidOpenTime | filterTime}}</span></td>
                    <td>开标地点</td>
                    <td><span>{{tenderDoc.bidOpenPlace}}</span></td>
                  </tr>
                  <tr>
                    <td>补充文件</td>
                    <td colspan="3">
                      <el-upload
                        class="upload-demo"
                        :action="upLoad"
                        :on-success="uploadBidFileSuccess"
                        :file-list="bidFileMoreList"
                        :show-file-list="false">
                        <el-button size="small" type="primary" class="upload-btn" v-if="dialogEdit">上传附件</el-button>
                      </el-upload>
                      <span class="file-list" v-for="(item, index) in bidFileMoreList" :key="index">
                        <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                        <i class="el-icon-error upload-file" @click="bidFileDel(index)" v-if="dialogEdit"></i>
                      </span>
                    </td>
                  </tr>
                </table>
                <div class="tabpane-btn">
                  <el-button type="primary" @click="bidFileSave()" v-if="dialogEdit">保 存</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane label="招标控制价" name="fifth">
                <el-form label-width="130px" class="demo-ruleForm tabs-pane-form">
                  <el-form-item label="控制价:">
                    <span>{{controlPrice.toString()}}</span>
                  </el-form-item>
                  <el-form-item label="控制价附件:">
                    <div class="apply-upload">
                      <el-row>
                        <el-col :span="12">
                        <span class="file-list" v-for="(item, index) in priceFileList" :key="index">
                          <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                        </span>
                        </el-col>
                      </el-row>
                    </div>
                  </el-form-item>
                  <el-form-item label="补充文件:">
                    <el-upload
                      class="upload-demo"
                      :action="upLoad"
                      :on-success="uploadPriceSuccess"
                      :file-list="priceFileMoreList"
                      :show-file-list="false">
                      <el-button size="small" type="primary" class="upload-btn" v-if="dialogEdit">上传附件</el-button>
                    </el-upload>
                    <span class="file-list" v-for="(item, index) in priceFileMoreList" :key="index">
                      <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                      <i class="el-icon-error upload-file" @click="priceFileDel(index)" v-if="dialogEdit"></i>
                    </span>
                  </el-form-item>
                </el-form>
                <div class="tabpane-btn">
                  <el-button type="primary" @click="priceFileSave()" v-if="dialogEdit">保 存</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane label="答疑补遗" name="sixth">
                <el-table
                  border
                  :data="clarifyTableData"
                  :row-class-name="tableRowClassName"
                  style="width: 100%">
                  <el-table-column
                    type="index"
                    label="序号"
                    :index="indexMethod"
                    width="55">
                  </el-table-column>
                  <el-table-column
                    prop="clarifyType"
                    label="澄清类型"
                    :formatter="filterClarifyType"
                    align="center">
                  </el-table-column>
                  <el-table-column
                    prop="sectionName"
                    label="澄清标段"
                    align="center">
                  </el-table-column>
                  <el-table-column
                    prop="bidderNameList"
                    label="质疑单位"
                    align="center">
                  </el-table-column>
                  <el-table-column
                    prop="fileInformationList"
                    label="澄清文件"
                    align="center">
                    <template slot-scope="scope">
                      <span class="file-list" v-for="(item, index) in scope.row.fileInformationList" :key="index">
                          <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">
                            {{item.fileName}}</el-button>
                      </span>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="bidOpenTime"
                    label="开标时间"
                    :formatter="filterDate"
                    align="center">
                  </el-table-column>
                </el-table>
                <el-form label-width="120px" class="demo-ruleForm tabs-pane-form">
                  <el-form-item label="补充文件:">
                    <el-upload
                      class="upload-demo"
                      :action="upLoad"
                      :on-success="uploadclarifyFileSuccess"
                      :file-list="clarifyFileMoreList"
                      :show-file-list="false">
                      <el-button size="small" type="primary" class="upload-btn" v-if="dialogEdit">上传附件</el-button>
                    </el-upload>
                    <span class="file-list" v-for="(item, index) in clarifyFileMoreList" :key="index">
                      <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                      <i class="el-icon-error upload-file" @click="clarifyFileDel(index)" v-if="dialogEdit"></i>
                    </span>
                  </el-form-item>
                </el-form>
                <div class="tabpane-btn">
                  <el-button type="primary" @click="clarifyFileSave()" v-if="dialogEdit">保 存</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane label="开评定标" name="seventh">
                <table class="info-table">
                  <tr>
                    <td>开标时间</td>
                    <td><span>{{openRecord.bidOpeningTime | filterTime}}</span></td>
                    <td>开标地点</td>
                    <td><span>{{tenderDoc.bidOpenPlace}}</span></td>
                  </tr>
                  <tr>
                    <td>开标一览表</td>
                    <td>
                      <div class="apply-upload">
                        <el-row>
                          <el-col :span="12">
                            <el-button
                              v-if="openRecordFile"
                              type="text" @click="downloadFile(openRecordFile.fileName, openRecordFile.relativePath)">
                            <span v-if="openRecordFile" style="display:inline-block;float:left;padding-right:20px;">
                              {{openRecordFile.fileName}}
                            </span>
                            </el-button>
                          </el-col>
                        </el-row>
                      </div>
                    </td>
                    <td>评标专家表</td>
                    <td>
                      <div class="apply-upload">
                        <el-row>
                          <el-col :span="12">
                            <span class="file-list" v-for="(item, index) in evalExportFileList" :key="index">
                              <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                            </span>
                          </el-col>
                        </el-row>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td>评标报告</td>
                    <td>
                      <div class="apply-upload">
                        <el-row>
                          <el-col :span="12">
                            <span class="file-list" v-for="(item, index) in evalReportFileList" :key="index">
                              <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                            </span>
                          </el-col>
                        </el-row>
                      </div>
                    </td>
                    <td>投标文件（中标人）</td>
                    <td>
                      <div class="apply-upload">
                        <el-row>
                          <el-col :span="12">
                            <el-button
                              v-if="bidWinnerFile"
                              type="text" @click="downloadFile(bidWinnerFile.fileName, bidWinnerFile.relativePath)">
                            <span v-if="bidWinnerFile" style="display:inline-block;float:left;padding-right:20px;">
                              {{bidWinnerFile.fileName}}
                            </span>
                            </el-button>
                          </el-col>
                        </el-row>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td>中标通知书</td>
                    <td colspan="3">
                      <div class="apply-upload">
                        <el-row>
                          <el-col :span="12">
                            <el-button
                              v-if="noticeFile"
                              type="text" @click="downloadFile(noticeFile.fileName, noticeFile.relativePath)">
                            <span v-if="noticeFile" style="display:inline-block;float:left;padding-right:20px;">
                              {{noticeFile.fileName}}
                            </span>
                            </el-button>
                          </el-col>
                        </el-row>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td>中标候选人公示</td>
                    <td>
                      <div class="apply-upload">
                        <el-row>
                          <el-col :span="12">
                        <span class="file-list" v-for="(item, index) in publicityFileList" :key="index">
                          <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                        </span>
                          </el-col>
                        </el-row>
                      </div>
                      <el-button type="text" @click="selectPubContent" class="look-details">查看详情</el-button>
                    </td>
                    <td>中标结果公示</td>
                    <td>
                      <div class="apply-upload">
                        <el-row>
                          <el-col :span="12">
                            <el-button
                              v-if="resultNoticeFile"
                              type="text" @click="downloadFile(resultNoticeFile.fileName, resultNoticeFile.relativePath)">
                            <span v-if="resultNoticeFile" style="display:inline-block;float:left;padding-right:20px;">
                              {{resultNoticeFile.fileName}}
                            </span>
                            </el-button>
                          </el-col>
                        </el-row>
                      </div>
                      <el-button type="text" @click="selectResContent" class="look-details">查看详情</el-button>
                    </td>
                  </tr>
                  <tr>
                    <td>补充文件</td>
                    <td colspan="3">
                      <el-upload
                        class="upload-demo"
                        :action="upLoad"
                        :on-success="uploadOpenBidSuccess"
                        :file-list="openBidFileMoreList"
                        :show-file-list="false">
                        <el-button size="small" type="primary" class="upload-btn" v-if="dialogEdit">上传附件</el-button>
                      </el-upload>
                      <span class="file-list" v-for="(item, index) in openBidFileMoreList" :key="index">
                        <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                        <i class="el-icon-error upload-file" @click="openBidFileDel(index)" v-if="dialogEdit"></i>
                      </span>
                    </td>
                  </tr>
                </table>
                <div class="tabpane-btn">
                  <el-button type="primary" @click="openBidFileSave()" v-if="dialogEdit">保 存</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane label="其他文件" name="eighth">
                <el-form label-width="120px" class="demo-ruleForm tabs-pane-form">
                  <el-form-item label="其他附件:">
                    <el-upload
                      class="upload-demo"
                      :action="upLoad"
                      :on-success="handleSuccess"
                      :file-list="otherFileList"
                      :show-file-list="false">
                      <el-button size="small" type="primary" class="upload-btn" v-if="dialogEdit">上传附件</el-button>
                    </el-upload>
                    <span class="file-list" v-for="(item, index) in otherFileList" :key="index">
                      <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                      <i class="el-icon-error upload-file" @click="otherFileDel(index)" v-if="dialogEdit"></i>
                    </span>
                  </el-form-item>
                </el-form>
                <div class="tabpane-btn">
                  <el-button type="primary" @click="otherFileSave()" v-if="dialogEdit">保 存</el-button>
                </div>
              </el-tab-pane>
            </el-tabs>
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>

    <!--弹窗区域start-->
    <div class="dialog-box">
      <el-dialog
        title="查看"
        :visible.sync="pubDialogVisible"
        width="50%">
        <div class="dialog-cont">
          <!--编辑器组件-->
          <ueditor ref="pubUeditor" :readonly="true"></ueditor>
        </div>
      </el-dialog>
      <el-dialog
        title="查看"
        :visible.sync="resDialogVisible"
        width="50%">
        <div class="dialog-cont">
          <!--编辑器组件-->
          <ueditor ref="resUeditor" :readonly="true"></ueditor>
        </div>
      </el-dialog>
    </div>
    <!--弹窗区域end-->
  </div>
</template>
<script>
// 引入编辑器
import ueditor from '@/components/ueditor/ueditor.vue'
import {qualfile, resnotice, bidfile, bidprice, answerques, openbid,
  qualfileResult, candNotice, archivesFile, section, tenderproject} from 'api/index'
import {commonJs, downloadFile} from 'common/js/common'
import {formatDate} from 'common/js/date'
export default {
  data () {
    return {
      upLoad: commonJs.fileUploadUrl,
      pubDialogVisible: false,
      resDialogVisible: false,
      dialogEdit: false, // 是否可编辑
      sectionCode: null, // 当前展开面板
      noticeCode: null, // 公告编号
      bidderCode: null, // 投标人编号
      activeTabName: '', // tabs默认展开项
      qualifiPreFile: {}, // 资格预审文件
      qualFileList: [], // 资格预审文件附件
      qualFileMoreList: [], // 资格预审文件补充附件
      qualifiPreResult: {}, // 资格预审会
      qualifiPreTableData: [], // 资格预审申请文件
      exportResultFile: {}, // 专家抽取结果
      qualifiReportFile: {}, // 资格预审报告
      qualResFileMoreList: [], // 资格预审会补充附件
      tenderDoc: {}, // 招标文件
      bidFileList: [], // 招标文件附件
      bidFileMoreList: [], // 招标文件补充附件
      controlPrice: '', // 招标控制价
      priceFileList: [], // 招标控制价附件
      priceFileMoreList: [], // 招标控制价补充附件
      answerTableData: [], // 答疑补遗（资审）
      answerFileMoreList: [], // 答疑补遗（资审）补充附件
      clarifyTableData: [], // 答疑补遗
      clarifyFileMoreList: [], // 答疑补遗补充附件
      openRecord: {}, // 开标信息
      openRecordFile: {}, // 开标一览表
      evalExportFileList: [], // 专家抽取结果
      evalReportFileList: [], // 评标报告
      bidWinnerFile: {}, // 投标文件（中标人）
      publicityFileList: [], // 候选人公示附件
      resultNoticeFile: {}, // 中标结果公示附件
      noticeFile: {}, // 中标通知书
      openBidFileMoreList: [], // 开评定标补充附件
      otherFileList: [], // 其他附件
      sectionList: [],
      currentPage: 1,
      relatedType: null, // 1.资格预审文件答疑补遗 2.招标文件答疑补遗
      fileType: null, // 文件类型
      archivesFile: {}, // 档案管理归档附件信息
      resultNoticeId: null, // 结果公示objectId
      tenderProject: {} // 招标项目信息
    }
  },
  props: {
    tenderProjectCode: String
  },
  computed: {},
  created () {},
  filters: {
    /** 标段是否归档 */
    filterStatus (val) {
      if (val === 2) {
        return '已归档'
      } else {
        return '未归档'
      }
    },
    /** 时间格式化 */
    filterTime (val) {
      if (val) {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return ''
      }
    }
  },
  /** 方法集 */
  methods: {
    filterClarifyType (row, column) {
      let clarifyType = row.clarifyType
      if (clarifyType === 1) {
        return '答疑'
      } else if (clarifyType === 2) {
        return '补遗'
      }
    },
    filterDate (row, column) {
      if (row.bidOpenTime) {
        let bidOpenTime = new Date(row.bidOpenTime)
        return formatDate(bidOpenTime, 'yyyy-MM-dd')
      } else {
        return ''
      }
    },
    downloadFile,
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /* tabs切换 */
    handleTabsClick (tab, event) {
      if (tab.name === 'first') {
        this.fileType = 1
        this.getQualfile()
        this.getArchivesFile()
      } else if (tab.name === 'second') {
        this.relatedType = 1
        this.fileType = 2
        this.getClarify()
        this.getArchivesFile()
      } else if (tab.name === 'third') {
        this.fileType = 3
        this.getQualifiPreResult()
        this.getArchivesFile()
      } else if (tab.name === 'fourth') {
        this.fileType = 4
        this.getTenderDoc()
        this.getArchivesFile()
      } else if (tab.name === 'fifth') {
        this.fileType = 5
        this.getControlPrice()
        this.getArchivesFile()
      } else if (tab.name === 'sixth') {
        this.relatedType = 2
        this.fileType = 6
        this.getClarify()
        this.getArchivesFile()
      } else if (tab.name === 'seventh') {
        this.fileType = 7
        this.getTenderDoc()
        this.getOpenRecord()
        this.getEvaluationResult()
        this.getPublicity()
        this.getArchivesFile()
      } else if (tab.name === 'eighth') {
        this.fileType = 8
        this.getArchivesFile()
      }
    },
    getDataBySection (section, index) {
      this.noticeCode = section.noticeCode
      this.sectionCode = section.code
      this.bidderCode = section.bidderCode
      this.resultNoticeId = section.resultNoticeId
      this.resultNoticeFile = section.resultNoticeFile
      this.noticeFile = section.noticeFile
      if (section.archivingStatus === 2 ||
        this.$route.query.operation === 'look') {
        this.dialogEdit = false
      } else {
        this.dialogEdit = true
      }
      if (this.tenderProject.isPrequalification) {
        this.activeTabName = 'first'
        this.fileType = 1
        this.getQualfile()
      } else {
        this.activeTabName = 'fourth'
        this.fileType = 4
        this.getTenderDoc()
      }
      this.getArchivesFile()
    },
    /** 查看中标候选人公示详情 */
    selectPubContent () {
      this.pubDialogVisible = true
      this.getPublicityContent()
    },
    /** 查看结果公示详情 */
    selectResContent () {
      this.resDialogVisible = true
      this.getResultNoticeContent()
    },
    /** 资格预审文件补充文件上传 */
    uploadQualFileSuccess (response, file, fileList) {
      this.qualFileMoreList = this.uploadSuccess(response, file, fileList)
    },
    /** 答疑补遗（资审）补充文件上传 */
    uploadanswerSuccess (response, file, fileList) {
      this.answerFileMoreList = this.uploadSuccess(response, file, fileList)
    },
    /** 资格预审会补充文件上传 */
    uploadQualResFileSuccess (response, file, fileList) {
      this.qualResFileMoreList = this.uploadSuccess(response, file, fileList)
    },
    /** 招标文件补充文件上传 */
    uploadBidFileSuccess (response, file, fileList) {
      this.bidFileMoreList = this.uploadSuccess(response, file, fileList)
    },
    /** 招标控制价补充文件上传 */
    uploadPriceSuccess (response, file, fileList) {
      this.priceFileMoreList = this.uploadSuccess(response, file, fileList)
    },
    /** 答疑补遗补充文件上传 */
    uploadclarifyFileSuccess (response, file, fileList) {
      this.clarifyFileMoreList = this.uploadSuccess(response, file, fileList)
    },
    /** 开评定标补充文件上传 */
    uploadOpenBidSuccess (response, file, fileList) {
      this.openBidFileMoreList = this.uploadSuccess(response, file, fileList)
    },
    /** 其他文件 */
    handleSuccess (response, file, fileList) {
      this.otherFileList = this.uploadSuccess(response, file, fileList)
    },
    uploadSuccess (response, file, fileList) {
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
        return fileInformations
      }
    },
    /** 资格预审文件补充文件删除 */
    qualFileDel (index) {
      this.qualFileMoreList.splice(index, 1)
    },
    /** 资格预审答疑补遗补充文件删除 */
    answerFileDel (index) {
      this.answerFileMoreList.splice(index, 1)
    },
    /** 资格预审会补充文件删除 */
    qualResFileDel (index) {
      this.qualResFileMoreList.splice(index, 1)
    },
    /** 招标文件补充文件删除 */
    bidFileDel (index) {
      this.bidFileMoreList.splice(index, 1)
    },
    /** 招标控制价补充文件删除 */
    priceFileDel (index) {
      this.priceFileMoreList.splice(index, 1)
    },
    /** 答疑补遗补充文件删除 */
    clarifyFileDel (index) {
      this.clarifyFileMoreList.splice(index, 1)
    },
    /** 开评定标补充文件删除 */
    openBidFileDel (index) {
      this.openBidFileMoreList.splice(index, 1)
    },
    /** 其他文件删除 */
    otherFileDel (index) {
      this.otherFileList.splice(index, 1)
    },
    /** 资格预审文件补充文件保存 */
    qualFileSave () {
      this.saveFiles()
    },
    /** 资格预审答疑补遗补充文件保存 */
    answerFileSave () {
      this.saveFiles()
    },
    /** 资格预审会补充文件保存 */
    qualResFileSave () {
      this.saveFiles()
    },
    /** 招标文件补充文件保存 */
    bidFileSave () {
      this.saveFiles()
    },
    /** 招标控制价补充文件保存 */
    priceFileSave () {
      this.saveFiles()
    },
    /** 答疑补遗补充文件保存 */
    clarifyFileSave () {
      this.saveFiles()
    },
    /** 开评定标补充文件保存 */
    openBidFileSave () {
      this.saveFiles()
    },
    /** 其他文件保存 */
    otherFileSave () {
      this.saveFiles()
    },
    /** 补充文件保存 */
    saveFiles () {
      // 当前table页
      let tab = {}
      let event = {}
      if (!this.archivesFile.objectId) {
        this.archivesFile.archivesManagementCode = this.$route.query.code
        this.archivesFile.sectionCode = this.sectionCode
        this.archivesFile.fileType = this.fileType
      }
      if (this.fileType === 1) {
        tab.name = 'first'
        // 资格预审文件补充文件
        this.archivesFile.fileInformationList = this.qualFileMoreList
      } else if (this.fileType === 2) {
        tab.name = 'second'
        // 资格预审答疑补遗补充文件
        this.archivesFile.fileInformationList = this.answerFileMoreList
      } else if (this.fileType === 3) {
        tab.name = 'third'
        // 资格预审文件补充文件
        this.archivesFile.fileInformationList = this.qualResFileMoreList
      } else if (this.fileType === 4) {
        tab.name = 'fourth'
        // 招标文件补充文件
        this.archivesFile.fileInformationList = this.bidFileMoreList
      } else if (this.fileType === 5) {
        tab.name = 'fifth'
        // 招标控制价补充文件
        this.archivesFile.fileInformationList = this.priceFileMoreList
      } else if (this.fileType === 6) {
        tab.name = 'sixth'
        // 招标文件答疑补遗补充文件
        this.archivesFile.fileInformationList = this.clarifyFileMoreList
      } else if (this.fileType === 7) {
        tab.name = 'seventh'
        // 开评标补充文件
        this.archivesFile.fileInformationList = this.openBidFileMoreList
      } else if (this.fileType === 8) {
        tab.name = 'eighth'
        // 其他文件
        this.archivesFile.fileInformationList = this.otherFileList
      }
      archivesFile.save(this.archivesFile).then((res) => {
        this.handleTabsClick(tab, event)
      })
    },
    /** 标段提交 */
    submitCilck (sectionMsg) {
      this.$confirm('此操作将对该标段提交归档, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        sectionMsg.archivingStatus = 2
        section.save(sectionMsg).then((res) => {
          this.dialogEdit = false
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    /** 通过中标结果公示查询标段 */
    getResultNotices () {
      // 查询招标项目信息
      tenderproject.getOneByCode(this.tenderProjectCode).then((res) => {
        this.tenderProject = res.data.tenderProject
      }).then(res => {
        let query = {
          tenderProjectCode: this.tenderProjectCode
        }
        resnotice.getLastResult(query).then((res) => {
          let resultNoticeList = res.data.resultNoticeList
          if (resultNoticeList) {
            this.sectionList = resultNoticeList.map((resultNotice) => {
              resultNotice.section.noticeCode = resultNotice.noticeCode
              resultNotice.section.resultNoticeId = resultNotice.objectId
              if (resultNotice.bidWinner) {
                resultNotice.section.bidderCode = resultNotice.bidWinner.bidderCode
              }
              resultNotice.fileInformationList.map((file) => {
                // 结果公示附件
                if (file.businessType === '1') {
                  resultNotice.section.resultNoticeFile = file
                }
                // 中标通知书
                if (file.businessType === '2') {
                  resultNotice.section.noticeFile = file
                }
              })
              return resultNotice.section
            })
          }
        }).then((res) => {
          if (this.sectionList.length > 0) {
            // 默认展示第一个标段的信息
            this.noticeCode = this.sectionList[0].noticeCode
            this.sectionCode = this.sectionList[0].code
            this.bidderCode = this.sectionList[0].bidderCode
            this.resultNoticeId = this.sectionList[0].resultNoticeId
            this.resultNoticeFile = this.sectionList[0].resultNoticeFile
            this.noticeFile = this.sectionList[0].noticeFile
            if (this.sectionList[0].archivingStatus === 2 ||
              this.$route.query.operation === 'look') {
              this.dialogEdit = false
            } else {
              this.dialogEdit = true
            }
            if (this.tenderProject.isPrequalification) {
              this.activeTabName = 'first'
              this.fileType = 1
              this.getQualfile()
            } else {
              this.activeTabName = 'fourth'
              this.fileType = 4
              this.getTenderDoc()
            }
            this.getArchivesFile()
          }
        })
      })
    },
    /** 查询资格预审文件信息 */
    getQualfile () {
      qualfile.getByCode(this.noticeCode, this.sectionCode).then((res) => {
        let qualifiPreFile = res.data.qualifiPreFile
        if (qualifiPreFile) {
          this.qualifiPreFile = qualifiPreFile
          this.qualFileList = qualifiPreFile.fileInformationList
        }
      })
    },
    /** 查询资格预审结果信息 */
    getQualifiPreResult () {
      let obj = {
        noticeCode: this.noticeCode,
        sectionCode: this.sectionCode
      }
      qualfileResult.getQualifiPreResult(obj).then((res) => {
        let qualifiPreResult = res.data.qualifiPreResult
        if (qualifiPreResult) {
          this.qualifiPreResult = qualifiPreResult
          qualifiPreResult.qualifiPreResultFileList.forEach((item) => {
            // 专家抽取结果
            if (item.qualifiPreResultFileType === 1) {
              this.exportResultFile = item.fileInformation
            }
            // 资格预审报告
            if (item.qualifiPreResultFileType === 2) {
              this.qualifiReportFile = item.fileInformation
            }
          })
          this.qualifiPreTableData = qualifiPreResult.qualifiPreResultSublistList.map((item) => {
            item.qualifiPreResultSublistFileList.map((child) => {
              // 申请文件
              if (child.sublistFileType === 1) {
                item.fileInformation = child.fileInformation
              }
              // 通知书
              if (child.sublistFileType === 2) {
                item.noticeFileInformation = child.fileInformation
              }
            })
            return item
          })
        }
      })
    },
    /** 查询招标文件信息 */
    getTenderDoc () {
      bidfile.getByCode(this.noticeCode, this.sectionCode).then((res) => {
        let tenderDoc = res.data.tenderDoc
        if (tenderDoc) {
          this.tenderDoc = tenderDoc
          this.bidFileList = tenderDoc.fileInformationList
        } else {
          return false
        }
      })
    },
    /** 查询招标控制价信息 */
    getControlPrice () {
      let obj = {
        noticeCode: this.noticeCode
      }
      bidprice.getOne(obj).then((res) => {
        let controlPrice = res.data.controlPrice
        if (controlPrice) {
          let controlPriceSublists = controlPrice.controlPriceSublists
          for (let i = 0; i < controlPriceSublists.length; i++) {
            if (controlPriceSublists[i].sectionCode === this.sectionCode) {
              this.controlPrice = controlPriceSublists[i].controlPrice + '万元'
            }
          }
          this.priceFileList = controlPrice.fileInformationList
        } else {
          return false
        }
      })
    },
    /** 查询答疑补遗信息 */
    getClarify () {
      let obj = {
        noticeCode: this.noticeCode,
        sectionCode: this.sectionCode,
        relatedType: this.relatedType
      }
      answerques.getList(obj).then((res) => {
        let clarifyList = res.data.clarifyList
        if (clarifyList) {
          if (this.relatedType === 1) {
            this.answerTableData = clarifyList.map((clarify) => {
              if (clarify.section) {
                clarify.sectionName = clarify.section.name
              }
              if (clarify.bidderInfoList) {
                clarify.bidderNameList = ''
                clarify.bidderInfoList.map((bidderInfo) => {
                  clarify.bidderNameList += bidderInfo.bidderName + '，'
                })
                clarify.bidderNameList = clarify.bidderNameList.substring(0, clarify.bidderNameList.length - 1)
              }
              return clarify
            })
          } else {
            this.clarifyTableData = clarifyList.map((clarify) => {
              if (clarify.section) {
                clarify.sectionName = clarify.section.name
              }
              if (clarify.bidderInfoList) {
                clarify.bidderNameList = ''
                clarify.bidderInfoList.map((bidderInfo) => {
                  clarify.bidderNameList += bidderInfo.bidderName + '，'
                })
                clarify.bidderNameList = clarify.bidderNameList.substring(0, clarify.bidderNameList.length - 1)
              }
              return clarify
            })
          }
        }
      })
    },
    /** 查询开标信息 */
    getOpenRecord () {
      let obj = {
        noticeCode: this.noticeCode,
        sectionCode: this.sectionCode
      }
      openbid.getOpenRecord(obj).then((res) => {
        let openRecord = res.data.openingRecord
        if (openRecord) {
          this.openRecord = openRecord
          this.openRecordFile = this.openRecord.fileInformation
        }
      })
    },
    /** 查询评标信息 */
    getEvaluationResult () {
      let obj = {
        noticeCode: this.noticeCode,
        sectionCode: this.sectionCode
      }
      openbid.getEvaluationResult(obj).then((res) => {
        let evaluationResult = res.data.evaluationResult
        if (evaluationResult) {
          evaluationResult.evaluationResultFileList.forEach((item) => {
            // 专家抽取结果
            if (item.evaluationResultFileType === 1) {
              this.evalExportFileList = item.fileInformationList
            }
            // 评标报告
            if (item.evaluationResultFileType === 2) {
              this.evalReportFileList = item.fileInformationList
            }
          })
          evaluationResult.evaluationResultSublists.forEach((item) => {
            // 投标文件（中标人）
            if (item.bidderCode === this.bidderCode) {
              this.bidWinnerFile = item.fileInformation
            }
          })
        }
      })
    },
    /** 查询候选人公示信息 */
    getPublicity () {
      let query = {
        noticeCode: this.noticeCode,
        sectionCode: this.sectionCode
      }
      candNotice.getOne(query).then((res) => {
        let publicity = res.data.publicity
        if (publicity) {
          this.publicityFileList = publicity.fileInformationList
        } else {
          return false
        }
      })
    },
    /** 查询中标候选人公示详情 */
    getPublicityContent () {
      let query = {
        noticeCode: this.noticeCode,
        sectionCode: this.sectionCode
      }
      candNotice.getOne(query).then((res) => {
        let publicity = res.data.publicity
        if (publicity) {
          this.$refs.pubUeditor.setContent(publicity.publicityContent)
        } else {
          return false
        }
      })
    },
    /** 查询中标结果公示详情 */
    getResultNoticeContent () {
      resnotice.getById(this.resultNoticeId).then((res) => {
        let resultNotice = res.data.resultNotice
        if (resultNotice) {
          this.$refs.resUeditor.setContent(resultNotice.resultNoticeContent)
        }
      })
    },
    /** 查询补充附件信息 */
    getArchivesFile () {
      this.archivesFile = {}
      this.qualFileMoreList = []
      this.answerFileMoreList = []
      this.qualResFileMoreList = []
      this.bidFileMoreList = []
      this.priceFileMoreList = []
      this.clarifyFileMoreList = []
      this.openBidFileMoreList = []
      this.otherFileList = []
      let query = {
        archivesManagementCode: this.$route.query.code,
        sectionCode: this.sectionCode,
        fileType: this.fileType
      }
      archivesFile.getOne(query).then((res) => {
        let archivesFile = res.data.archivesFile
        if (archivesFile) {
          this.archivesFile = archivesFile
          if (this.fileType === 1) {
            // 资格预审文件补充文件
            this.qualFileMoreList = archivesFile.fileInformationList
          } else if (this.fileType === 2) {
            // 资格预审答疑补遗补充文件
            this.answerFileMoreList = archivesFile.fileInformationList
          } else if (this.fileType === 3) {
            // 资格预审文件补充文件
            this.qualResFileMoreList = archivesFile.fileInformationList
          } else if (this.fileType === 4) {
            // 招标文件补充文件
            this.bidFileMoreList = archivesFile.fileInformationList
          } else if (this.fileType === 5) {
            // 招标控制价补充文件
            this.priceFileMoreList = archivesFile.fileInformationList
          } else if (this.fileType === 6) {
            // 招标文件答疑补遗补充文件
            this.clarifyFileMoreList = archivesFile.fileInformationList
          } else if (this.fileType === 7) {
            // 开评标补充文件
            this.openBidFileMoreList = archivesFile.fileInformationList
          } else if (this.fileType === 8) {
            // 其他文件
            this.otherFileList = archivesFile.fileInformationList
          }
        } else {
          return false
        }
      })
    }
  },
  /** 实例创建完成 */
  mounted () {
    this.getResultNotices()
  },
  /** 组件注册 */
  components: {
    // 编辑器
    ueditor
  }
}
</script>
<style lang="less">
#openfile-page {
  .collapse-warp {
    /** 折叠面板头部中的样式 */
    .el-collapse-item {
      margin-bottom: 15px;
    }
    .el-collapse-item__header {
      font-size: 14px;
      font-weight: 800;
      padding-left: 20px;
      border: 1px solid #ebeef5;
      text-align: left;
      .collapse-span {
        display: block;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
    }
    .el-collapse-item__header.is-active {
      background: #eee;
    }
    .middle-span {
      padding: 0 20px;
    }
    /** 折叠面板内容中的样式 */
    .collspase-cont {
      padding-top: 20px;
      .el-icon-error {
        color: red;
      }
      .tabpane-btn {
        padding: 20px;
      }
      .upload-demo {
        float: left;
      }
      .look-details, .down-details {
        margin-left: 20px;
      }
      .tabs-pane-form {
        .el-form-item__content {
          text-align: left;
        }
      }
    }
    .info-table {
      width: 100%;
      border-collapse: collapse;
      border: 1px solid #eee;
      tr td {
        min-height: 50px;
        line-height: 50px;
        border: 1px solid #eee;
      }
      tr td:nth-child(odd) {
        min-width: 100px;
        background: #F6F7F9;
      }
      tr td:nth-child(even) {
        min-width: 320px;
        text-align: left;
        padding: 0 10px;
      }
    }
  }
}
</style>
