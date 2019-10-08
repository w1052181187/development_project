<template>
  <div id="statement" class="statement">
    <el-row>
      <div class="l_header">
        <div class="wrap">
          <div class="l_header_left">
            <span>您好，欢迎来到 {{$store.getters.authUser.enterpriseName}}</span>
          </div>
          <div class="l_header_right">
            <div class="username">
              <span :title="$store.getters.authUser.userName">{{$store.getters.authUser.userName}}</span>
              <span class="iconjian">
              <img src="../../../../static/images/system-quantybill/jiantou.png" alt="" class="jiantouimg">
            </span>
              <div class="layoutbtn" @click="logout">退出</div>
            </div>
          </div>
        </div>
      </div>
    </el-row>
    <el-row>
      <el-col :span="4" width="300px" class="setheight">
        <div class="toptree" v-loading="loading">
          <span>工程结构</span>
          <el-tree :data="data" :props="defaultProps" :highlight-current="true" @node-click="handleNodeClick" default-expand-all
                   :expand-on-click-node="false" >
            <span class="custom-tree-node" slot-scope="{ node, data }">
              <span :title="node.label">{{ node.label }}</span>
            </span>
          </el-tree>
        </div>
        <div class="pdflist">
          <span>报表明细</span>
          <!--招标-->
          <template v-if="Number(this.$route.query.type) === 1">
            <div v-if="this.rank === '1'">
              <span v-for="(item, index) in zb" :class="[ { 'first-active': (activeIndex === index)?true:false },  { 'active': item.value === currentVal }]" :key="item.value" @click="getCurrentType(item.value, index)" v-if="item.rank === '1'" :title="item.label">{{item.label}}</span>
            </div>
            <div v-if="this.rank === '3'">
              <span v-for="(item, index) in zb" :class="[ { 'first-active': (activeIndex === index)?true:false },  { 'active': item.value === currentVal }]" :key="item.value" @click="getCurrentType(item.value, index)" v-if="item.rank === '3'" :title="item.label">{{item.label}}</span>
            </div>
          </template>
          <!--招标-->
          <!--投标-->
          <template v-if="Number(this.$route.query.type) === 2">
            <div v-if="this.rank === '1'">
              <span v-for="(item, index) in tb" :class="[ { 'first-active': (activeIndex === index)?true:false },  { 'active': item.value === currentVal }]" :key="item.value" @click="getCurrentType(item.value, index)" v-if="item.rank === '1'" :title="item.label">{{item.label}}</span>
            </div>
            <div v-if="this.rank === '2'">
              <span v-for="(item, index) in tb" :class="[ { 'first-active': (activeIndex === index)?true:false },  { 'active': item.value === currentVal }]" :key="item.value" @click="getCurrentType(item.value, index)" v-if="item.rank === '2'" :title="item.label">{{item.label}}</span>
            </div>
            <div v-if="this.rank === '3'">
              <span v-for="(item, index) in tb" :class="[ { 'first-active': (activeIndex === index)?true:false },  { 'active': item.value === currentVal }]" :key="item.value" @click="getCurrentType(item.value, index)" v-if="item.rank === '3'" :title="item.label">{{item.label}}</span>
            </div>
          </template>
          <!--投标-->
          <!--投标-->
          <template v-if="Number(this.$route.query.type) === 3">
            <div v-if="this.rank === '1'">
              <span v-for="(item, index) in xj" :class="[ { 'first-active': (activeIndex === index)?true:false },  { 'active': item.value === currentVal }]"  :key="item.value" @click="getCurrentType(item.value, index)" v-if="item.rank === '1'" :title="item.label">{{item.label}}</span>
            </div>
            <div v-if="this.rank === '2'">
              <span v-for="(item, index) in xj" :class="[ { 'first-active': (activeIndex === index)?true:false },  { 'active': item.value === currentVal }]" :key="item.value" @click="getCurrentType(item.value, index)" v-if="item.rank === '2'" :title="item.label">{{item.label}}</span>
            </div>
            <div v-if="this.rank === '3'">
              <span v-for="(item, index) in xj" :class="[ { 'first-active': (activeIndex === index)?true:false },  { 'active': item.value === currentVal }]" :key="item.value" @click="getCurrentType(item.value, index)" v-if="item.rank === '3'" :title="item.label">{{item.label}}</span>
            </div>
          </template>
          <!--投标-->
        </div>
      </el-col>
      <el-col :span="20">
        <div class="maintop">
          <el-button type="text" @click="returnindex">&lt; 首页</el-button>
          <el-button class="lookbtn" type="primary" @click="openLookDialog"><img src="../../../../static/images/system-quantybill/ico2.png" /> 查看标书信息</el-button>
          <el-button type="primary" v-if="Number(this.$route.query.turn) === 0 && Number(this.$route.query.type) === 1" @click="exportInterface"><img src="../../../../static/images/system-quantybill/jiekou.png"/> 导出招标接口</el-button>
          <el-button type="primary" v-if="Number(this.$route.query.turn) === 1" @click="openBookFile"><img src="../../../../static/images/system-quantybill/jiekou.png"/> 生成标书</el-button>
          <div class="fr">
            <ul>
              <!--<li><a href="javascript:void(0)" @click="print"> <img src="../../../../static/images/system-quantybill/dayin.png"/> <span>单页打印</span></a> </li>-->
              <li><a href="javascript:void(0)" @click="exportExcel"> <img src="../../../../static/images/system-quantybill/excel.png"/> <span>导出Excel</span></a> </li>
              <li><a href="javascript:void(0)" @click="exportPdf"> <img src="../../../../static/images/system-quantybill/pdf.png"/> <span>导出PDF</span></a> </li>
              <li><a href="javascript:void(0)" @click="treeAllData"> <img src="../../../../static/images/system-quantybill/piliangcz.png"/> <span>批量操作</span></a> </li>
            </ul>
          </div>
        </div>
        <iframe name= "printIframe" id="printIframe" :src="src" style="width: 99%;" v-show="dialogIframe"></iframe>
        <!--生成标书-->
        <el-dialog
          title="生成标书"
          :visible.sync="bookDialogVisible"
          width="30%"
          :before-close="handleClose"
          :modal-append-to-body = false>
          <div class="filepath">
            <el-form  label-width="100px">
              <el-form-item label="文件名称：">
                <el-input class="filename" placeholder="请输入文件名称" v-model="fileName"></el-input>
              </el-form-item>
            </el-form>
            <el-button type="primary"  @click="_saveEndFile">生成</el-button>
          </div>
        </el-dialog>
        <!--生成标书-->
        <!--批量弹窗-->
        <el-dialog
          title="批量设置"
          :visible.sync="dialogVisible"
          width="40%"
          :before-close="handleClose">
          <div class="maincon" v-loading="allDataLoading">
            <div class="lefttree">
              <el-tree :data="allData" :props="defaultProps" ref="allDataTree" show-checkbox default-expand-all
                       :expand-on-click-node="false">
                <span class="custom-tree-node" slot-scope="{ node, data }">
                   <span :title="node.label">{{ node.label }}</span>
                </span>
              </el-tree>
            </div>
          </div>
          <span slot="footer" class="dialog-footer pl-button">
            <el-button type="primary" @click="submitAllData('excels')">导出Excel</el-button>
            <el-button type="primary" @click="submitAllData('pdfs')">导出PDF</el-button>
          </span>
        </el-dialog>
        <!--批量弹窗-->
        <!--查看标书信息 弹窗-->
        <el-dialog
          title="查看标书信息"
          :visible.sync="lookDialogVisible"
          width="40%"
          :before-close="handleClose"
          class="lookDialog">
          <div v-if="Number(this.$route.query.type) === 1">
            <table class="bidtable">
              <tr>
                <td  class="tit">工程编号</td>
                <td>{{projectCode}}</td>
              </tr>
              <tr>
                <td  class="tit">工程名称</td>
                <td>{{name}}</td>
              </tr>
              <tr>
                <td  class="tit">招标人(建设单位)</td>
                <td>{{tenderee}}</td>
              </tr>
              <tr>
                <td  class="tit">招标单位法定代表人</td>
                <td>{{tendereeLegalPerson}}</td>
              </tr>
              <tr>
                <td  class="tit">造价咨询人</td>
                <td>{{costConsultPerson}}</td>
              </tr>
              <tr>
                <td  class="tit">造价咨询法定代表人</td>
                <td>{{projectCostLegalPerson}}</td>
              </tr>
              <tr>
                <td  class="tit">编制人</td>
                <td>{{compilePerson}}</td>
              </tr>
              <tr>
                <td  class="tit">编制时间</td>
                <td>{{compileTime}}</td>
              </tr>
              <tr>
                <td  class="tit">复核人</td>
                <td>{{reviewPerson}}</td>
              </tr>
              <tr>
                <td  class="tit">复核时间</td>
                <td>{{reviewTime}}</td>
              </tr>
              <tr>
                <td  class="tit">总说明</td>
                <td>{{totalExplain}}</td>
              </tr>
            </table>
          </div>
          <div v-if="Number(this.$route.query.type) === 2">
            <h3>招标信息</h3>
            <table class="bidtable">
              <tr>
                <td class="tit">工程编号</td>
                <td>{{relateProjectCode}}</td>
              </tr>
              <tr>
                <td  class="tit">工程名称</td>
                <td>{{relateName}}</td>
              </tr>
              <tr>
                <td  class="tit">招标人</td>
                <td>{{relateTenderee}}</td>
              </tr>
              <tr>
                <td  class="tit">法定代表人</td>
                <td>{{relateTendereeLegalPerson}}</td>
              </tr>
              <tr>
                <td  class="tit">编制人</td>
                <td>{{relateCompilePerson}}</td>
              </tr>
            </table>
            <h3>投标信息</h3>
            <table class="bidtable">
              <tr>
                <td  class="tit">投标人</td>
                <td>{{bidder}}</td>
              </tr>
              <tr>
                <td  class="tit">法定代表人</td>
                <td>{{bidderLegalPerson}}</td>
              </tr>
              <!--<tr>-->
              <!--<td  class="tit">造价咨询人</td>-->
              <!--<td>{{costConsultPerson}}</td>-->
              <!--</tr>-->
              <!--<tr>-->
              <!--<td  class="tit">造价工程师</td>-->
              <!--<td>{{costEngineer}}</td>-->
              <!--</tr>-->
              <tr>
                <td  class="tit">编制人</td>
                <td>{{compilePerson}}</td>
              </tr>
              <tr>
                <td  class="tit">编制时间</td>
                <td>{{compileTime}}</td>
              </tr>
              <tr>
                <td  class="tit">总说明</td>
                <td>{{totalExplain}}</td>
              </tr>
            </table>
          </div>
          <div v-if="Number(this.$route.query.type) === 3">
            <h3>招标信息</h3>
            <table class="bidtable">
              <tr>
                <td  class="tit">工程编号</td>
                <td>{{relateProjectCode}}</td>
              </tr>
              <tr>
                <td  class="tit">工程名称</td>
                <td>{{relateName}}</td>
              </tr>
              <tr>
                <td  class="tit">招标人</td>
                <td>{{relateTenderee}}</td>
              </tr>
              <tr>
                <td  class="tit">法定代表人</td>
                <td>{{relateTendereeLegalPerson}}</td>
              </tr>
              <tr>
                <td  class="tit">编制人</td>
                <td>{{relateCompilePerson}}</td>
              </tr>
            </table>
            <h3>招标控制价信息</h3>
            <table class="bidtable">
              <tr>
                <td  class="tit">造价咨询人</td>
                <td>{{costConsultPerson}}</td>
              </tr>
              <tr>
                <td  class="tit">造价咨询单位法人</td>
                <td>{{projectCostLegalPerson}}</td>
              </tr>
              <tr>
                <td  class="tit">编制人</td>
                <td>{{compilePerson}}</td>
              </tr>
              <tr>
                <td  class="tit">编制时间</td>
                <td>{{compileTime}}</td>
              </tr>
              <tr>
                <td  class="tit">复核人</td>
                <td>{{reviewPerson}}</td>
              </tr>
              <tr>
                <td  class="tit">复核时间</td>
                <td>{{reviewTime}}</td>
              </tr>
              <tr>
                <td  class="tit">清单报价说明</td>
                <td>{{totalExplain}}</td>
              </tr>
            </table>
          </div>
          <span slot="footer" class="dialog-footer">
              <el-button type="primary" @click="closeLookDialog">确 定</el-button>
            </span>
        </el-dialog>
        <!--查看标书信息 弹窗-->
        <!--<iframe style="width: 99%; height: 100%; z-index: -1;" v-if="dialogIframe"></iframe>-->
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { billPushData } from '@/api'
import { zb, tb, xj } from '@/assets/js/quaStatement'
import { loadUrl } from '@/assets/js/common'
// import $ from 'jquery'
export default {
  data () {
    return {
      // 左侧工程树数据
      data: [],
      // 批量工程树数据
      allData: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      // 默认表
      type: Number(this.$route.query.type) === 1 ? '2111' : Number(this.$route.query.type) === 2 ? '2211' : '2311',
      objectId: Number(this.$route.query.projectId),
      // 类型，用来接收招标、投标、招标控制价
      rank: '1',
      // PDF iframe路径
      src: '',
      // 招标、投标、招标控制价表格类型参数
      zb: zb,
      tb: tb,
      xj: xj,
      // 当前选中 表的类型
      currentVal: '',
      // 批量弹窗设置
      dialogVisible: false,
      // isActive: false,
      // 表 选中 index
      activeIndex: 0,
      // AllRank: '1',
      // 查看标书信息弹窗设置
      lookDialogVisible: false,
      // 查看标书信息  数据项
      projectCode: '',
      name: '',
      tenderee: '',
      bidder: '',
      tendereeLegalPerson: '',
      bidderLegalPerson: '',
      compilePerson: '',
      compileTime: '',
      reviewPerson: '',
      reviewTime: '',
      totalExplain: '',
      costConsultPerson: '',
      costEngineer: '',
      projectCostLegalPerson: '',
      relateProjectCode: '',
      relateName: '',
      relateTenderee: '',
      relateTendereeLegalPerson: '',
      relateCompilePerson: '',
      bookDialogVisible: false,
      // 生成标书 接收保存文件名
      fileName: '',
      // 弹窗打开时控制iframe 显示隐藏
      dialogIframe: true,
      objectIdDic: {},
      loading: true,
      allDataLoading: false
    }
  },
  methods: {
    // 退出当前账号（头部右上角）
    logout () {
      this.$store.dispatch('Logout').then(() => {
        this.$router.push({path: '/login'})
      })
    },
    setMain () {
      // 获得屏幕的高度
      let screenH = window.innerHeight
      // 获得主要内容区
      let topUl = document.getElementsByClassName('setheight')
      let iframeH = document.getElementById('printIframe')
      // 设置主要内容区的高度
      topUl[0].style.height = (screenH - 60) + 'px'
      iframeH.style.height = (screenH - 164) + 'px'
    },
    // 返回首页
    returnindex () {
      this.$router.push({path: `/system-quantybill`})
    },
    // 查看标书信息弹窗打开
    openLookDialog () {
      this.dialogIframe = false
      this.lookDialogVisible = true
      this._getBidData()
    },
    // 查看标书信息弹窗读取数据
    _getBidData () {
      billPushData.getItemInfor(this.$route.query.projectId).then((res) => {
        this.projectCode = res.data.itemInfor.projectCode
        this.name = res.data.itemInfor.name
        this.tenderee = res.data.itemInfor.tenderee
        this.bidder = res.data.itemInfor.bidder
        this.tendereeLegalPerson = res.data.itemInfor.tendereeLegalPerson
        this.bidderLegalPerson = res.data.itemInfor.bidderLegalPerson
        this.compilePerson = res.data.itemInfor.compilePerson
        this.compileTime = res.data.itemInfor.compileTime
        this.reviewPerson = res.data.itemInfor.reviewPerson
        this.reviewTime = res.data.itemInfor.reviewTime
        this.totalExplain = res.data.itemInfor.totalExplain
        this.costConsultPerson = res.data.itemInfor.costConsultPerson
        this.costEngineer = res.data.itemInfor.costEngineer
        this.projectCostLegalPerson = res.data.itemInfor.projectCostLegalPerson
        if (Number(this.$route.query.type) !== 1) {
          this.relateProjectCode = res.data.relateItemInfor.projectCode
          this.relateName = res.data.relateItemInfor.name
          this.relateTenderee = res.data.relateItemInfor.tenderee
          this.relateTendereeLegalPerson = res.data.relateItemInfor.tendereeLegalPerson
          this.relateCompilePerson = res.data.relateItemInfor.compilePerson
        }
      })
    },
    // 关闭查看标书
    closeLookDialog () {
      this.dialogIframe = true
      this.lookDialogVisible = false
    },
    // 导出招标接口
    exportInterface () {
      billPushData.projectDetail(this.objectId).then((res) => {
        let comurl = process.env.COMPONENTS_BASE_API
        let url = comurl + 'comptend/project/download/export?fileName=' + res.data.project.fileInformation.fileName + '&filePath=' + res.data.project.fileInformation.relativePath
        window.open(url)
      })
    },
    // 左侧读取工程树结构数据
    treeData () {
      billPushData.projectDetail(this.$route.query.projectId).then((res) => {
        this.initObjectIdDic(res.data.project.treeModel)
        this.data.push(res.data.project.treeModel)
        this.loading = false
      })
    },
    initObjectIdDic (tree) {
      this.objectIdDic[tree.id] = {
        name: tree.name,
        parentId: tree.parentId,
        rank: tree.rank
      }
      for (let key in tree.children) {
        this.initObjectIdDic(tree.children[key])
      }
      for (let key in this.objectIdDic) {
        if (this.objectIdDic[key].rank === '单项') {
          this.objectIdDic[key].parent = this.objectIdDic[this.objectIdDic[key].parentId]
        }
      }
      for (let key in this.objectIdDic) {
        if (this.objectIdDic[key].rank === '单位') {
          this.objectIdDic[key].parent = this.objectIdDic[this.objectIdDic[key].parentId]
        }
      }
    },
    // 点击树形数据 下面加载不同的报表明细
    handleNodeClick (data) {
      if (Number(this.$route.query.type) === 1) {
        if (data.rank === '项目') {
          this.rank = '1'
          this.objectId = data.id
          this.type = '2111'
          this.activeIndex = 0
          this.src = '../../../../static/system-quantybill/iframe.html?' + 'id=' + this.objectId + '&type=' + this.type + '&serverUrl=' + loadUrl
        }
        if (data.rank === '单项') {
          this.rank = '2'
          this.objectId = data.id
          this.type = '0'
        }
        if (data.rank === '单位') {
          this.rank = '3'
          this.objectId = data.id
          this.type = '2121'
          this.activeIndex = 4
          this.src = '../../../../static/system-quantybill/iframe.html?' + 'id=' + this.objectId + '&type=' + this.type + '&serverUrl=' + loadUrl
        }
      } else if (Number(this.$route.query.type) === 2) {
        if (data.rank === '项目') {
          this.rank = '1'
          this.objectId = data.id
          this.type = '2211'
          this.activeIndex = 0
          this.src = '../../../../static/system-quantybill/iframe.html?' + 'id=' + this.objectId + '&type=' + this.type + '&serverUrl=' + loadUrl
        }
        if (data.rank === '单项') {
          this.rank = '2'
          this.objectId = data.id
          this.type = '2221'
          this.activeIndex = 6
          this.src = '../../../../static/system-quantybill/iframe.html?' + 'id=' + this.objectId + '&type=' + this.type + '&serverUrl=' + loadUrl
        }
        if (data.rank === '单位') {
          this.rank = '3'
          this.objectId = data.id
          this.type = '2231'
          this.activeIndex = 7
          this.src = '../../../../static/system-quantybill/iframe.html?' + 'id=' + this.objectId + '&type=' + this.type + '&serverUrl=' + loadUrl
        }
      } else {
        if (data.rank === '项目') {
          this.rank = '1'
          this.objectId = data.id
          this.type = '2311'
          this.activeIndex = 0
          this.src = '../../../../static/system-quantybill/iframe.html?' + 'id=' + this.objectId + '&type=' + this.type + '&serverUrl=' + loadUrl
        }
        if (data.rank === '单项') {
          this.rank = '2'
          this.objectId = data.id
          this.type = '2321'
          this.activeIndex = 6
          this.src = '../../../../static/system-quantybill/iframe.html?' + 'id=' + this.objectId + '&type=' + this.type + '&serverUrl=' + loadUrl
        }
        if (data.rank === '单位') {
          this.rank = '3'
          this.objectId = data.id
          this.type = '2331'
          this.activeIndex = 7
          this.src = '../../../../static/system-quantybill/iframe.html?' + 'id=' + this.objectId + '&type=' + this.type + '&serverUrl=' + loadUrl
        }
      }
    },
    // 添加不同的默认值
    typeActive () {
      switch (Number(this.$route.query.type)) {
        case 1:
          this.type === '2111'
          break
        case 2:
          this.type === '2211'
          break
        case 3:
          this.type === '2311'
          break
      }
    },
    // 点击不同的报名明细 加载不同的表type
    getCurrentType (data, index) {
      this.currentVal = data
      this.activeIndex = index
      this.type = data
      this.src = '../../../../static/system-quantybill/iframe.html?' + 'id=' + this.objectId + '&type=' + this.type + '&serverUrl=' + loadUrl
    },
    // 批量读取树形结构数据
    treeAllData () {
      this.allDataLoading = true
      this.allData = []
      this.dialogIframe = false
      this.dialogVisible = true
      billPushData.projectDetail(this.$route.query.projectId).then((res) => {
        switch (Number(this.$route.query.type)) {
          case 1:
            let childArr = res.data.project.treeModel.children
            let arr = this.getReportInfo(1, 1)
            for (let k = (arr.length - 1); k >= 0; k--) {
              arr[k].id = res.data.project.treeModel.id
              childArr.unshift(arr[k])
            }
            for (let i = (childArr.length - 1); i >= 1; i--) {
              let childLastArr = childArr[i].children
              for (let k in childLastArr) {
                let arr = this.getReportInfo(1, 3)
                for (let h in arr) {
                  arr[h].id = childLastArr[k].id
                  childLastArr[k].children = arr
                }
              }
            }
            break
          case 2:
            let childArr2 = res.data.project.treeModel.children
            let arr2 = this.getReportInfo(2, 1)
            for (let k = (arr2.length - 1); k >= 0; k--) {
              arr2[k].id = res.data.project.treeModel.id
              childArr2.unshift(arr2[k])
            }
            for (let i = (childArr2.length - 1); i >= 6; i--) {
              let childLastArr = childArr2[i].children
              let childarr = this.getReportInfo(2, 2)
              for (let k in childarr) {
                childarr[k].id = childArr2[i].id
                childLastArr.unshift(childarr[k])
              }
              for (let j = (childLastArr.length - 1); j >= 1; j--) {
                let array = this.getReportInfo(2, 3)
                for (let h in array) {
                  array[h].id = childLastArr[j].id
                }
                childLastArr[j].children = array
              }
            }
            break
          case 3:
            let childArr3 = res.data.project.treeModel.children
            let arr3 = this.getReportInfo(3, 1)
            for (let k = (arr3.length - 1); k >= 0; k--) {
              arr3[k].id = res.data.project.treeModel.id
              childArr3.unshift(arr3[k])
            }
            for (let i = (childArr3.length - 1); i >= 6; i--) {
              let childLastArr = childArr3[i].children
              let childarr = this.getReportInfo(3, 2)
              for (let k in childarr) {
                childarr[k].id = childArr3[i].id
                childLastArr.unshift(childarr[k])
              }
              for (let j = (childLastArr.length - 1); j >= 1; j--) {
                let array = this.getReportInfo(3, 3)
                for (let h in array) {
                  array[h].id = childLastArr[j].id
                }
                childLastArr[j].children = array
              }
            }
            break
        }
        this.allData.push(res.data.project.treeModel)
        this.allDataLoading = false
      })
    },
    // 遍历JS 获取要添加到属性结构的数据
    getReportInfo (type, rank) {
      let array = []
      switch (type) {
        case 1:
          for (let i in zb) {
            if (rank === 1) {
              if (zb[i].rank === '1') {
                let item = {name: zb[i].label, type: zb[i].value}
                array.push(item)
              }
            } else if (rank === 2) {
              if (zb[i].rank === '3') {
                let item = {name: zb[i].label, type: zb[i].value}
                array.push(item)
              }
            } else if (rank === 3) {
              if (zb[i].rank === '3') {
                let item = {name: zb[i].label, type: zb[i].value}
                array.push(item)
              }
            }
          }
          break
        case 2:
          for (let i in tb) {
            if (rank === 1) {
              if (tb[i].rank === '1') {
                let item = {name: tb[i].label, type: tb[i].value}
                array.push(item)
              }
            } else if (rank === 2) {
              if (tb[i].rank === '2') {
                let item = {name: tb[i].label, type: tb[i].value}
                array.push(item)
              }
            } else if (rank === 3) {
              if (tb[i].rank === '3') {
                let item = {name: tb[i].label, type: tb[i].value}
                array.push(item)
              }
            }
          }
          break
        case 3:
          for (let i in xj) {
            if (rank === 1) {
              if (xj[i].rank === '1') {
                let item = {name: xj[i].label, type: xj[i].value}
                array.push(item)
              }
            } else if (rank === 2) {
              if (xj[i].rank === '2') {
                let item = {name: xj[i].label, type: xj[i].value}
                array.push(item)
              }
            } else if (rank === 3) {
              if (xj[i].rank === '3') {
                let item = {name: xj[i].label, type: xj[i].value}
                array.push(item)
              }
            }
          }
          break
      }
      return array
    },
    // 点击确定 提交批量选中的数据
    submitAllData (type) {
      this.dialogVisible = false
      this.dialogIframe = true
      let tree = []
      let all = this.$refs.allDataTree.getCheckedNodes()
      if (type === 'pdfs') {
        this.transNodeToPdfArray(all, tree)
      } else {
        this.transNodeToExcelArray(all, tree)
      }
      billPushData.downloadAllExcels(tree, this.objectId, type).then((res) => {
        if (res.data.resCode === '0000') {
          let comurl = process.env.COMPONENTS_BASE_API
          let url = comurl + 'comptend/project/download/' + type + '/' + this.objectId
          window.open(url)
        }
      })
    },
    transNodeToPdfArray (nodes, tree) {
      for (let key in nodes) {
        let curNode = nodes[key]
        if (curNode.rank) {
          this.transNodeToPdfArray(curNode.children, tree)
        } else {
          if (curNode.id) {
            let curTreeItem = tree.filter(function (treeItem) {
              return treeItem.id == curNode.id
            })
            if (curTreeItem.length > 0) {
              let curTreeType = curTreeItem[0].type
              let isInsert = true
              for (let k in curTreeType) {
                if (curTreeType[k] == curNode.type) {
                  isInsert = false
                  break
                }
              }
              if (isInsert) {
                curTreeItem[0].type.push(curNode.type)
              }
            } else {
              tree.push({
                id: curNode.id,
                parent: this.objectIdDic[curNode.id].parent,
                name: this.objectIdDic[curNode.id].name,
                type: [curNode.type]
              })
            }
          }
        }
      }
    },
    transNodeToExcelArray (nodes, tree) {
      for (let key in nodes) {
        if (nodes[key].rank) {
          this.transNodeToExcelArray(nodes[key], tree)
        } else {
          if (nodes[key].id != null && nodes[key].id != '') {
            tree.push({
              id: nodes[key].id,
              type: nodes[key].type
            })
          }
        }
      }
    },
    // 导出单个EXCEL
    exportExcel () {
      let comurl = process.env.COMPONENTS_BASE_API
      let url = comurl + 'comptend/project/download/excel/' + this.objectId + '/' + this.type
      window.open(url)
    },
    // 导出单个PDF
    exportPdf () {
      let comurl = process.env.COMPONENTS_BASE_API
      let url = comurl + 'comptend/project/download/pdf/' + this.objectId + '/' + this.type
      window.open(url)
    },
    // 关闭弹窗
    handleClose (done) {
      done()
      this.dialogIframe = true
    },
    // 生成标书打开弹窗
    openBookFile () {
      this.dialogIframe = false
      this.bookDialogVisible = true
      this.fileName = ''
    },
    // 生成标书
    _saveEndFile () {
      if (this.fileName) {
        let comurl = process.env.COMPONENTS_BASE_API
        let url = comurl + 'comptend/project/download/localFile/end/' + this.$route.query.projectId + '?fileName=' + encodeURI(this.fileName)
        window.open(url)
        this.bookDialogVisible = false
        this.dialogIframe = true
      } else {
        this.$message({
          type: 'warning',
          message: '请输入文件名称'
        })
      }
    }
  },
  watch: {
    type () {
      this.typeActive()
    }
  },
  mounted () {
    this.setMain()
    // this._getBidData()
    this.treeData()
    this.typeActive()
    this.src = '../../../../static/system-quantybill/iframe.html?' + 'id=' + this.objectId + '&type=' + this.type + '&serverUrl=' + loadUrl
  }
}
</script>
<style lang="less">
  #statement{
    background: #e7e8eb;
    .wrap{
      width: 98%;
    }
    img{
      border: 0px;
    }
    .el-header{
      padding: 0px;
    }
    .l_header{
      background: #3f63f6;
      color: #ffffff;
      margin-bottom: 20px;
    }
    .iconjian{
      width: 12px;
    }
    .username:hover{
      background:#3f63f6;
      border: 1px solid #b5b5b5;
      box-sizing: border-box;
      border-bottom: none;
      border-top: none;
    }
    .layoutbtn{
      background: #3f63f6;
      color: #ffffff;
    }
    .maincontent{
      width: 100%;
      margin-top: 0px;
      overflow: hidden;
    }
    .el-col-4{
      background: #ffffff;
    }
    .el-col-20{
      padding-left: 20px;
      float: right;
    }
    .toptree{
      padding: 0 20px;
      overflow-y: auto;
      overflow-x: hidden;
      height: 50%;
      border-bottom: 1px solid #dddddd;
      -webkit-box-sizing: border-box;
      -moz-box-sizing: border-box;
      box-sizing: border-box;
    }
    .toptree>span{
      display: block;
      margin-top: 10px;
      line-height: 50px;
    }
    .pdflist{
      padding: 0 20px;
      height: 50%;
      overflow-y: auto;
      overflow-x: hidden;
    }
    .pdflist>span{
      display: block;
      margin-top: 10px;
      line-height: 50px;
    }
    .pdflist div{
      width: 95%;
      float: left;
      margin-left: 5%;
      overflow: hidden;
    }
    .pdflist div span{
      display: block;
      overflow: hidden;
      -ms-text-overflow: ellipsis;
      text-overflow: ellipsis;
      white-space: nowrap;
      height: 26px;
      line-height: 26px;
      color: #333333;
      font-size: 14px;
      text-decoration: none;
      cursor: pointer;
    }
    .maintop{
      width: 100%;
      height: 94px;
      background: #ffffff;
      overflow: hidden;
    }
    .maintop .el-button{
      margin-top: 25px;
    }
    .maintop .el-button--text{
      font-weight: bold;
      font-size: 16px;
      margin-left: 10px;
    }
    .maintop .lookbtn{
      background: #ff8a00;
      border: 1px solid #ff8a00;
      margin:25px 10px 0 20px;
    }
    .maintop .fr{
      float: right;
      overflow: hidden;
      margin-right: 15px;
    }
    .maintop .fr ul{
      width: 245px;
      float: left;
      margin: 18px 15px 0 15px;
    }
    .maintop .fr ul li{
      width: 55px;
      text-align: center;
      float: left;
      margin: 0 13px;
    }
    .maintop .fr ul li a{
      font-size: 12px;
      text-decoration: none;
    }
    .maintop .fr ul li a span{
      display: block;
      color: #666666;
      font-size: 12px;
    }
    .iframe{
      width: 100%;
      overflow: hidden;
      background: #ebecef;
      position: relative;
      z-index: 0;
    }
    .el-dialog__wrapper{
      z-index: 999999999999;
    }
    .el-dialog{
      background: #f0f0f0;
      z-index: 999999999999;
    }
    .pdflist div span.active,.pdflist div span.first-active{
      color: #409EFF
    }
    .el-dialog__header{
      background: #ffffff;
    }
    .maincon{
      overflow: hidden;
      height: 500px;
      padding: 1%;
      background: #ffffff;
    }
    .maincon .lefttree{
      width: 98%;
      float: left;
      padding: 1%;
      height: 96%;
      overflow-y: auto;
      overflow-x: hidden;
      border: 1px solid #e5e6e7;
    }
    .custom-tree-node{
      overflow: hidden;
      text-overflow: ellipsis;
      font-size: 14px;
    }
    .icocheckbox{
      display: inline-block;
      float: left;
      border: 1px solid #dcdfe6;
      border-radius: 2px;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      width: 14px;
      height: 14px;
      margin-top: 6px;
      margin-right: 3px;
      background-color: #fff;
      z-index: 1;
      -webkit-transition: border-color .25s cubic-bezier(.71,-.46,.29,1.46),background-color .25s cubic-bezier(.71,-.46,.29,1.46);
      transition: border-color .25s cubic-bezier(.71,-.46,.29,1.46),background-color .25s cubic-bezier(.71,-.46,.29,1.46);
    }
    /* 查看标书信息 弹窗样式*/
    .lookDialog .el-dialog{
      background: #ffffff;
      height: 70%;
      overflow-y: auto;
    }
    .lookDialog .el-dialog h3{
      height: 50px;
      line-height: 50px;
      padding: 0 30px;
      border-bottom: 1px solid #dddddd;
      font-size: 16px;
      color: #111111;
      margin: 0px 0 30px 0;
    }
    .bidtable{
      width: 88%;
      margin: 0 auto 40px auto;
      border-collapse: collapse;
    }
    .bidtable tr{
      line-height: 26px;
    }
    .bidtable tr td{
      line-height: 48px;
      padding: 0 10px;
      border: 1px solid #ebeef5;
      font-size: 14px;
      color: #606266;
    }
    .bidtable tr td.tit{
      width: 200px;
    }
    .el-dialog__footer{
      text-align: center;
    }
    .pl-button .el-button{
      padding: 9px 21px;
    }
    .filepath{
      text-align: center;
    }
  }
</style>
