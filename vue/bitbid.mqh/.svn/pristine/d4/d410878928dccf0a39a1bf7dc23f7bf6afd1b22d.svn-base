<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="expertmange-page">
    <div v-if="this.$route.path == '/admin/resource/expertmange'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>资源管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/resource/expertmange' }">评标专家管理</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!-- 搜素区域start -->
      <div class="search-box">
        <el-form :model="searchForm" ref="searchForm" label-width="50px">
          <el-row :gutter="40">
            <el-col :span="6" style="width:300px;">
              <el-form-item label="姓名:">
                <el-input v-model="searchForm.name" style="width:220px;"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="width:300px;">
              <el-form-item label="单位:">
                <el-input v-model="searchForm.enterpriseName" style="width:220px;"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="width:300px;">
              <el-form-item label="性别:">
                <el-select v-model="searchForm.sex" clearable placeholder="请选择" style="width:220px;">
                  <el-option
                    v-for="item in sexList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="width:385px;">
              <el-form-item label="地区:">
                <span>{{searchForm.provinceId | filterProvince}}</span> -
                <el-select v-model="searchForm.cityId" clearable placeholder="请选择" style="width: 220px;">
                  <el-option
                    v-for="item in cityIdList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="6" style="width:300px;">
              <el-form-item label="状态:">
                <el-select v-model="searchForm.expertStatus" clearable placeholder="请选择" style="width:220px;">
                  <el-option
                    v-for="item in statusList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="width:300px;">
              <el-form-item label="专业:">
                <el-cascader
                  style="width: 220px;"
                  expand-trigger="hover"
                  :clearable="true"
                  change-on-select
                  :options="cascaderSelectData"
                  v-model="selectedOptions"
                  @change="handleChange">
                </el-cascader>
              </el-form-item>
            </el-col>
            <el-col :span="2" style="padding-left:30px;">
              <el-form-item>
                <el-button type="primary" @click="onSearch()" icon="el-icon-search" style="margin-left:-70px;">搜索</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <!-- 搜索区域end -->
      <!-- 主体内容区域start -->
      <div class="maincont-box expertmange-cont">
        <div class="expert-add-btn">
          <el-button type="warning" icon="el-icon-plus" @click="handleAddClick" v-if="$store.getters.permissions.includes('/resource/expertmange/operation')">新增</el-button>
        </div>
        <el-table
          :data="tableData"
          :row-class-name="tableRowClassName"
          border
          style="width: 100%">
          <el-table-column
            type="index"
            label="序号"
            align="center"
            :index="indexMethod"
            width="55">
          </el-table-column>
          <el-table-column
            align="center"
            prop="name"
            label="姓名"
            width="80">
          </el-table-column>
          <el-table-column
            align="center"
            prop="idCard"
            label="身份证号"
            width="180">
          </el-table-column>
          <el-table-column
            align="center"
            prop="sex"
            :formatter="filterSex"
            label="性别">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="enterpriseName"
            label="单位">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="cityId"
            :formatter="filterCity"
            label="地区">
          </el-table-column>
          <el-table-column
            align="center"
            prop="expertSources"
            :formatter="filterSources"
            label="专家类别">
          </el-table-column>
          <el-table-column
            align="center"
            label="专业"
            width="200">
            <template slot-scope="scope">
              <p v-for="(item, index) in scope.row.expertclassify" :key="index">{{item}}</p>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            prop="expertStatus"
            label="状态">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.expertStatus"
                name="scope.row.name"
                active-color="#13ce66"
                inactive-color="#ff4949"
                :active-value="0"
                :inactive-value="1"
                @change="changeSwitch(scope.row)">
               </el-switch>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
            width="180">
            <template slot-scope="scope">
              <el-button @click="handleTableClick(scope.row,'look')" type="text" size="medium" v-if="$store.getters.permissions.includes('/resource/expertmange/detail')">查看</el-button>
              <el-button @click="handleTableClick(scope.row,'edit')" type="text" size="medium" v-if="$store.getters.permissions.includes('/resource/expertmange/operation')">修改</el-button>
              <el-button @click="handleTableClick(scope.row,'del')" type="text" size="medium" v-if="$store.getters.permissions.includes('/resource/expertmange/operation')">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--分页-->
        <div class="block-page">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-sizes="pageSizeList"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
          </el-pagination>
        </div>
      </div>
      <!-- 主体内容区域end -->
      <!-- 弹窗区域start -->
      <div class="expert-dialog">
        <el-dialog
          :title="dialogTitle"
          :visible.sync="dialogVisible"
          @close="onFormCancel"
          width="40%">
          <el-form ref="expertFormInfo" :model="expertFormInfo" :rules="rules" label-width="130px">
            <el-form-item label="姓名:" prop="name">
              <el-input v-model="expertFormInfo.name" style="width: 295px;"></el-input>
            </el-form-item>
            <el-form-item label="身份证号:" prop="idCard">
              <el-input v-model="expertFormInfo.idCard" style="width: 295px;"></el-input>
            </el-form-item>
            <el-form-item label="性别:" prop="sex">
              <el-select v-model="expertFormInfo.sex" clearable placeholder="请选择" style="width: 295px;">
                <el-option
                  v-for="item in sexList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="单位:" prop="enterpriseName">
              <el-input v-model="expertFormInfo.enterpriseName" style="width: 295px;"></el-input>
            </el-form-item>
            <el-form-item label="地区:" prop="cityId">
              <el-select v-model="expertFormInfo.cityId" clearable placeholder="请选择" style="width: 295px;">
                <el-option
                  v-for="item in cityIdList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="专家类别:" prop="expertSources">
              <el-select v-model="expertFormInfo.expertSources" clearable placeholder="请选择" style="width: 295px;">
                <el-option
                  v-for="item in typeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="状态:" prop="expertStatus">
              <el-select v-model="expertFormInfo.expertStatus" clearable placeholder="请选择" style="width: 295px;">
                <el-option
                  v-for="item in statusList"
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
                    style="width: 295px;"
                    expand-trigger="hover"
                    :clearable="true"
                    change-on-select
                    :options="cascaderSelectData"
                    v-model="expertFormInfo.classify"
                    @change="handledialogChange">
                  </el-cascader>
                </el-col>
              </el-row>
            </el-form-item>
            <el-form-item>
              <div class="edit-select-box">
                <el-table
                  :show-header="false"
                  :data="backData"
                  style="width: 100%">
                  <el-table-column
                    prop="professional"
                    label="专业"
                    width="310">
                    <template slot-scope="scope">
                      <el-cascader
                        style="width: 295px;"
                        expand-trigger="hover"
                        :clearable="true"
                        change-on-select
                        :options="cascaderSelectData"
                        v-model="scope.row.professional"
                        @change="handledialogChange">
                      </el-cascader>
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="操作"
                    width="100">
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
                <el-button type="warning" icon="el-icon-plus" @click="addSelectClick">新增专业</el-button>
              </div>
            </el-form-item>
            <el-form-item class="expert-btn-set">
              <el-button type="primary" @click="onFormSubmit('expertFormInfo')">确定</el-button>
              <el-button  @click="onFormCancel()">取消</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>
      </div>
      <!-- 弹窗区域end -->
    </div>
    <div v-else>
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
import {expertmanage} from 'api/index'
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
    return {
      cascaderSelectData: expertclassify,
      selectedOptions: [], // 级联选择
      cascaderSelectValue: [],
      selectTableData: [], // 弹窗-下拉选-表格
      dialogTitle: '新增',
      dialogVisible: false,
      expertFormInfo: {}, // 弹窗表单
      firstClassify: '',
      secondClassify: '',
      thirdClassify: '',
      fourClassify: '',
      rules: {
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
        expertStatus: [
          { required: true, message: '请选择状态', trigger: 'change' }
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
      statusList: [
        {
          label: '激活',
          value: 0
        },
        {
          label: '锁定',
          value: 1
        }
      ],
      // 地区
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
      searchForm: {
        provinceId: 4
      }, // 搜索表单
      tableData: [],
      flag: 0, // 0-新增 1-修改
      pageNo: 0,
      currentPage: 1,
      pageSize: 10,
      total: null, // 总条数
      pageSizeList: [10, 20, 30, 40, 50],
      resultArr: [],
      classifyData: [] // 专家专业数组
    }
  },
  computed: {
    backData () {
      return this.selectTableData
    }
  },
  created () {},
  /*
  * 方法集
  */
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    handledialogChange (value) {
    },
    // 级联选择
    handleChange () {},
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
    },
    filterSex (row, column) {
      let sex = row.sex
      if (sex === 1) {
        return '男'
      } else if (sex === 2) {
        return '女'
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
    indexMethod (index) {
      return index + (this.currentPage - 1) * this.pageSize + 1
    },
    /** 表单分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.onSearch()
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getExpertInfoList()
    },
    /** 进行操作后返回当前页 */
    getCurrentValue () {
      if (this.$route.query.pageNo && this.$route.query.pageSize && this.$route.query.currentPage) {
        this.currentPage = parseInt(this.$route.query.currentPage)
        this.pageNo = parseInt(this.$route.query.pageNo)
        this.pageSize = parseInt(this.$route.query.pageSize)
      }
      this.getExpertInfoList()
    },
    changeSwitch (row) {
      this.expertFormInfo = row
      expertmanage.update(this.expertFormInfo).then(() => {
        this.dialogVisible = false
        this.getExpertInfoList()
      })
    },
    /**
     * 搜索
     */
    onSearch () {
      if (this.selectedOptions) {
        this.firstClassify = this.selectedOptions[0]
        this.secondClassify = this.selectedOptions[1]
        this.thirdClassify = this.selectedOptions[2]
        this.fourClassify = this.selectedOptions[3]
      }
      let obj = {
        pageNo: 0,
        pageSize: this.pageSize,
        messageLike: this.searchForm.name || null,
        enterpriseLike: this.searchForm.enterpriseName || null,
        sex: this.searchForm.sex || null,
        cityId: this.searchForm.cityId || null,
        expertStatus: this.searchForm.expertStatus,
        firstClassify: this.firstClassify || null,
        secondClassify: this.secondClassify || null,
        thirdClassify: this.thirdClassify || null,
        fourClassify: this.fourClassify || null,
        expertType: 1
      }
      this.currentPage = 1
      this.getList(obj)
    },
    /**
     * 新增
     */
    handleAddClick () {
      this.dialogVisible = true
    },
    /**
     * 取消
     */
    onFormCancel () {
      this.dialogVisible = false
      this.expertFormInfo = {}
      this.selectTableData = []
      this.$refs['expertFormInfo'].resetFields()
    },
    /**
     * 专业信息
     */
    saveClassify () {
      this.expertFormInfo.expertClassifyList = this.selectTableData.map(item => { return item })
      if (this.selectTableData) {
        for (let i = 0; i < this.selectTableData.length; i++) {
          this.expertFormInfo.expertClassifyList[i].firstClassify = this.selectTableData[i].professional[0]
          this.expertFormInfo.expertClassifyList[i].secondClassify = this.selectTableData[i].professional[1]
          this.expertFormInfo.expertClassifyList[i].thirdClassify = this.selectTableData[i].professional[2]
          this.expertFormInfo.expertClassifyList[i].fourClassify = this.selectTableData[i].professional[3]
        }
      }
      // 合并数据
      let tempArr = this.expertFormInfo.classify
      this.expertFormInfo.expertClassifyList.push({firstClassify: tempArr[0], secondClassify: tempArr[1], thirdClassify: tempArr[2], fourClassify: tempArr[3]})
    },
    /**
     * 确定
     */
    onFormSubmit (val) {
      if (this.checkClassify()) {
        this.$refs[val].validate((vaild) => {
          if (vaild) {
            this.saveClassify()
            this.expertFormInfo.provinceId = '4'
            this.expertFormInfo.expertType = 1
            if (this.flag === 0) {
              expertmanage.save(this.expertFormInfo).then(() => {
                this.dialogVisible = false
                this.getExpertInfoList()
              })
            } else if (this.flag === 1) {
              expertmanage.update(this.expertFormInfo).then(() => {
                this.dialogVisible = false
                this.getExpertInfoList()
              })
            }
          } else {
            return false
          }
        })
      }
    },
    /** 校验是否存在重复的专业 */
    checkClassify () {
      // 将专业放入classifyData数组中，每次校验最新录入的一个专业是否与以上录入的重复
      // 若重复，则不可新增专业，且提示“存在重复的专业,请移除重复专业!”
      this.classifyData = []
      if (this.expertFormInfo.classify) {
        let classify = {
          professional: this.expertFormInfo.classify
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
    // 新增下拉选-弹窗
    addSelectClick () {
      if (this.checkClassify()) {
        let obj = {
          first: '第二'
        }
        this.selectTableData.push(obj)
      }
    },
    // 删除一个下拉选
    deleteRow (index, rows) {
      rows.splice(index, 1)
      this.cascaderSelectValue.splice(index, 1)
    },
    // 表格操作
    handleTableClick (row, type) {
      if (type === 'del') {
        this.deleteClick(row)
      } else if (type === 'edit') {
        this.getExpertInfo(row)
        this.selectTableData = this.editProTabShow(row)
        this.dialogTitle = '编辑'
        this.dialogVisible = true
        this.flag = 1
      } else if (type === 'look') {
        this.$router.push({path: '/admin/resource/expertmange/expertdetail', query: {objectId: row.objectId, currentPage: this.currentPage, pageNo: this.pageNo, pageSize: this.pageSize}})
      }
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
    // 删除
    deleteClick (data) {
      this.$confirm('此操作永久删除, 是否继续?', '提示', {
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
          message: '已取消'
        })
      })
    },
    getExpertInfo (row) {
      expertmanage.getOne(row.objectId).then((res) => {
        if (res.data.expertInfo) {
          this.expertFormInfo = res.data.expertInfo
          if (this.expertFormInfo.expertClassifyList) {
            let expertLen = this.expertFormInfo.expertClassifyList
            for (let j = 0; j < expertLen.length; j++) {
              if (j === 0) {
                this.expertFormInfo.classify = [expertLen[0].firstClassify, expertLen[0].secondClassify, expertLen[0].thirdClassify, expertLen[0].fourClassify]
              }
            }
          }
        } else {
          return false
        }
      })
    },
    getExpertInfoList () {
      if (this.selectedOptions) {
        this.firstClassify = this.selectedOptions[0]
        this.secondClassify = this.selectedOptions[1]
        this.thirdClassify = this.selectedOptions[2]
        this.fourClassify = this.selectedOptions[3]
      }
      let obj = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        messageLike: this.searchForm.name || null,
        enterpriseLike: this.searchForm.enterpriseName || null,
        sex: this.searchForm.sex || null,
        cityId: this.searchForm.cityId || null,
        expertStatus: this.searchForm.expertStatus,
        firstClassify: this.firstClassify || null,
        secondClassify: this.secondClassify || null,
        thirdClassify: this.thirdClassify || null,
        fourClassify: this.fourClassify || null,
        expertType: 1
      }
      this.getList(obj)
    },
    getList (obj) {
      expertmanage.getList(obj).then((res) => {
        if (res.data.expertInfoList) {
          let handleData = res.data.expertInfoList.list
          this.total = res.data.expertInfoList.total
          if (handleData.length > 0) {
            for (let i = 0; i < handleData.length; i++) {
              let expertArr = handleData[i].expertClassifyList
              if (expertArr) {
                handleData[i].expertclassify = this.transLabel(expertArr)
              }
            }
          }
          this.tableData = handleData
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
    }
  },
  /** 实例创建完成 */
  mounted () {
    this.getCurrentValue()
  },
  /** 过滤 */
  filters: {
    filterProvince (val) {
      let provinceId = val
      if (provinceId === 4) {
        return '山西省'
      }
    }
  }
}
</script>
<style lang="less">
#expertmange-page {
  /*搜索*/
  .expertmange-cont {
    margin-bottom: 130px;
    .expert-add-btn {
      text-align: left;
      padding-bottom: 20px;
    }
    .el-table__body .el-table__row {
      td:nth-child(8){
        div.cell {
          padding:0;
        }
        p {
          margin: 0;
          padding: 10px;
          overflow: hidden;
          white-space: nowrap;
          text-overflow: ellipsis;
        }
        .dresult-blank-span {
          height: 22px;
        }
        p:not(:last-child) {
          border-bottom: 1px solid #eee;
        }
      }
    }
  }
  .expert-dialog {
    .el-form-item:last-child .el-form-item__content{
      text-align: center;
      margin-left: 0 !important;
    }
    .add-select-btn {
      margin-top: 20px;
    }
    .el-dialog__body .el-form {
      .el-table .el-table__row .cell {
        width: 310px;
        padding-left: 0;
      }
      .el-table .el-table__row td {
        border-bottom: 0;
      }
    }
    .el-table__empty-block {
      min-height: 0;
      display: none;
    }
  }
  .el-form-item .el-form-item__content {
    text-align: left;
  }
  .block-page {
    padding-top: 20px;
  }
}
</style>
