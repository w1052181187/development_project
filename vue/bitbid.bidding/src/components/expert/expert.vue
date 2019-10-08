<template>
  <div>
    <!--内容开始-->
    <div class="content_bigbox" id="tingcontent_bigbox">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item class="active_bread">专家管理</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <!--新增归档按钮-->
      <div class="da_xz_ckbigbox" style="border: none">
        <el-button type="text" class="da_xzgd_btn" @click="dialogTableVisible = true" v-if="$store.getters.permissions.includes('/experts/add')">
          <span class="jia_zzt">+</span>
          <span>新增专家</span>
        </el-button>
        <div class="seacher_box">
          <span>分类：</span>
          <el-select class="fenleizztselet" v-model="classificationvalue">
            <el-option
              v-for="item in classificationoptions"
              :key="item.classifyName"
              :label="item.classifyName"
              :value="item.classifyName">
            </el-option>
          </el-select>
          <el-input class="fenleiselect"  style="vertical-align: top" placeholder="请输入专家姓名关键字" v-model="seacher_input">
            <el-button @click="search" slot="append" icon="el-icon-search" type=""></el-button>
          </el-input>
        </div>
      </div>
      <!--新增归档按钮-->
      <!--表格内容开始-->
      <div class="da_contabbigbox" ref="aaa">
        <el-table
          :data="tableData"
          border
          style="width: 100%">
          <el-table-column
            prop="noticeName"
            label=""
            align="center"
            width="50">
            <template slot-scope="scope">
              <i class="morebtn el-icon-arrow-right" @click="btnOpen($event)"></i>
            </template>
          </el-table-column>
          <el-table-column
            type="index"
            label="序号"
            width="50"
            :index='dataIndex' align="center">
          </el-table-column>
          <el-table-column
            prop="name"
            label="专家姓名"
            width="200"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="expertClassifyStandard"
            label="专业分类"
            width="150">
            <template slot-scope="scope">
              <p v-for="(item, index) in scope.row.expertClassifyStandard" :key="index" :title="item">{{item}}</p>
            </template>
          </el-table-column>
          <el-table-column
            prop="allAddress"
            label="所在地区"
            width="180"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="expertClassifySelf"
            label="分类"
            width="80">
          </el-table-column>
          <el-table-column
            prop="createDate"
            label="创建时间"
            width="200"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            label="操作" align="center">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="seeData(scope)" v-if="$store.getters.permissions.includes('/experts/detail')">
                   查看
              </el-button>
              <el-button type="text" size="small" @click="editFun(scope)" v-if="$store.getters.permissions.includes('/experts/update')">
                   编辑
              </el-button>
              <!--更多弹框-->
              <el-tooltip placement="top" effect="light">
                <div slot="content">
                  <template>
                    <el-button
                      @click="expertPro(scope)"
                      type="text"
                      size="small" v-if="$store.getters.permissions.includes('/experts/projects')">
                      项目管理
                    </el-button>
                    <el-button type="text" size="small" @click="deleteExpert(scope)" v-if="$store.getters.permissions.includes('/experts/delete')">
                      删除
                    </el-button>
                  </template>
                </div>
                <el-button  type="text" size="small" >更多</el-button>
              </el-tooltip>
              <!--更多弹框-->
            </template>
          </el-table-column>
        </el-table>
      </div>
      <!--表格内容开始-->
      <!--分页-->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size='pageSize'
        :current-page.sync="currentPage"
        @current-change="handleCurrentChange"
        @next-click="handleCurrentNext">
      </el-pagination>
      <!--分页-->
    </div>
    <!--内容开始-->
    <!--新增文档弹框-->
    <div id="tingxjwdfather">
      <el-dialog title="新增专家" :visible.sync="dialogTableVisible" width="80%" :before-close="cancelBtn">
        <!--新增专家-->
        <div class="selectxxxm_bigbox">
          <el-form :model="form" :rules="rules" ref="form" >
            <el-form-item label="专家姓名：" prop="name" :label-width="formLabelWidth">
              <el-input class="xxxm_xinxi" v-model="form.name" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="身份证号码：" prop="idNumber" :label-width="formLabelWidth" >
              <el-input class="xxxm_xinxi" v-model="form.idNumber" auto-complete="off"></el-input>
            </el-form-item>
          </el-form>
        </div>
        <!--新增专家-->
        <!--确认取消按钮-->
        <div class="que_qubigbox">
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancel('form')">取 消</el-button>
          <el-button type="primary" @click="expertAdd('form')">确 定</el-button>
        </span>
        </div>
        <!--确认取消按钮-->
      </el-dialog>
    </div>
    <!--新增文档弹框-->
  </div>
</template>
<!--本页css-->
<script>
import {checkResponse} from '../../assets/js/common'
import {isvalidId} from '../../assets/js/validate'
import {CityInfo} from '../../assets/js/region'
import * as expertClassify from '../../assets/js/expertClassify'
import $ from 'jquery'
export default {

  data () {
    // 身份证验证
    var validID = (rule, value, callback) => {
      if (!value) {

      } else if (!isvalidId(value)) {
        callback(new Error('请输入正确的18位身份证号码'))
      } else {
        callback()
      }
    }
    return {
      // 搜索的input
      seacher_input: '',
      // 专家列表数据
      tableData: [],
      total: 0, // 总条数
      loading: true,
      pageSize: 15, // 每页展示条数
      pageNo: 0,
      currentPage: 1,
      dataIndex: 1,
      searchIs: false,
      value: '',
      // 提交时验证
      rules: {
        name: [
          { required: true, message: '专家姓名不能为空', trigger: 'blur' },
          {min: 1, max: 100, message: '请输入1~100个字符', trigger: ['blur', 'change']}
        ],
        idNumber: [
          { required: true, message: '专家身份证号不能为空', trigger: 'blur' },
          { validator: validID, trigger: 'blur' }
        ]
      },
      classificationvalue: '全部',
      classificationoptions: [],
      // 提交时form提交
      form: {
        name: '',
        idNumber: ''
      },
      dialogTableVisible: false,
      radios: '',
      formLabelWidth: '120px',
      inputseacher: ''
    }
  },
  methods: {
  // ********************************************************************专家列表********************************************************************
    // 展开隐藏信息
    btnOpen (event) {
      if ($(event.currentTarget).hasClass('el-icon-arrow-down')) {
        $(event.currentTarget).removeClass('el-icon-arrow-down').addClass('el-icon-arrow-right')
        $(event.currentTarget).parents('tr.el-table__row').find('.cell').css('height', '30px')
      } else {
        $(event.currentTarget).parents('tr.el-table__row').find('.cell').css('height', 'auto')
        $(event.currentTarget).removeClass('el-icon-arrow-right').addClass('el-icon-arrow-down')
      }
    },
    // 分类数据获取
    selectClassify () {
      this.axios.get(`classify-infos/getClassify?relatedModuleType=${4}&enterpriseId=${this.$store.getters.authUser.ownerUserId}&_t=${new Date().getTime()}`).then((res) => {
        this.classificationoptions = res.data.classifyInfos
        let wholeObject = {
          classifyName: '全部'
        }
        this.classificationoptions.push(wholeObject)
      })
    },
    // 列表数据展示
    expertData (search) {
      let url = null
      if (search) {
        url = `expert-infos?pageNo=${this.pageNo}&pageSize=${this.pageSize}&enterpriseId=${this.$store.getters.authUser.ownerUserId}&userId=${this.$store.getters.authUser.userId}&messageLike=${encodeURIComponent(this.seacher_input)}&expertClassifySelf=${encodeURIComponent(this.classificationvalue)}&_t=${new Date().getTime()}`
      } else {
        url = `expert-infos?pageNo=${this.pageNo}&pageSize=${this.pageSize}&enterpriseId=${this.$store.getters.authUser.ownerUserId}&userId=${this.$store.getters.authUser.userId}&_t=${new Date().getTime()}`
      }
      this.axios.get(url).then((res) => {
        this.tableData = res.data.experts.list
        this.total = res.data.experts.total
        this.loading = false
        // 获取地址进行拼接
        this.tableData.map((tableItem) => {
          if (tableItem.expertClassifySelf === '') {
            tableItem.expertClassifySelf = '--------'
          }
          tableItem.allAddress = ''
          CityInfo.map((item) => {
            if (tableItem.provinceId == item.value) {
              tableItem.allAddress += item.label
              item.children.map((shi) => {
                if (tableItem.cityId == shi.value) {
                  tableItem.allAddress += shi.label
                  shi.children.map((county) => {
                    if (tableItem.countyId == county.value) {
                      tableItem.allAddress += county.label
                    }
                  })
                }
              })
            }
          })
        })
        let handleData = res.data.experts.list
        if (handleData.length > 0) {
          for (let i = 0; i < handleData.length; i++) {
            let expertArr = handleData[i].expertClassifyStandard
            let expertArry = expertArr.split(';')
            if (expertArry) {
              handleData[i].expertClassifyStandard = this.transLabel(expertArry)
            }
          }
        }
        this.tableData = handleData
      })
    },
    // 专业分类转成label值方法
    transLabel (expertArr) {
      let resultArr = []
      for (let j = 0; j < expertArr.length; j++) {
        let newarr = expertArr[j].split(',')
        let reStr = ''
        for (let i = 0; i < newarr.length; i++) {
          let str = newarr[i]
          let chStr = this.getLabelName(expertClassify.expertClassify, str)
          if (chStr.node && (newarr.length - 1) > i) {
            reStr += `${chStr.node.label}--`
          } else if (chStr.node && (newarr.length - 1) === i) {
            reStr += `${chStr.node.label}`
          }
        }
        resultArr.push(reStr)
      }
      return resultArr
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
    },
    // 表单分页
    handleCurrentChange (nowNum) {
      this.loading = true
      this.pageNo = (nowNum - 1) * this.pageSize
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this.expertData(this.searchIs)
    },
    handleCurrentNext (nowNum) {
      this.loading = true
      this.pageNo = (nowNum - 1) * this.pageSize
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this.expertData(this.searchIs)
    },
    // 删除
    deleteExpert (scope) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.axios.delete(`expert-infos/${scope.row.objectId}`).then((res) => {
          checkResponse(this, res, () => {
            this.expertData()
          })
        })
      }).catch(() => {
        return false
      })
    },
    // 搜索功能
    search () {
      this.currentPage = 1
      this.dataIndex = 1
      this.pageNo = 0
      this.loading = true
      this.axios.get(`expert-infos?pageNo=${this.pageNo}&pageSize=${this.pageSize}&enterpriseId=${this.$store.getters.authUser.ownerUserId}&userId=${this.$store.getters.authUser.userId}&messageLike=${encodeURIComponent(this.seacher_input)}&expertClassifySelf=${encodeURIComponent(this.classificationvalue)}&_t=${new Date().getTime()}`).then((res) => {
        this.loading = false
        this.tableData = res.data.experts.list
        this.total = res.data.experts.total
        this.currentPage = 1
        this.dataIndex = 1
        this.pageNo = (this.currentPage - 1) * this.pageSize
        this.searchIs = true
        // 获取地址进行拼接
        this.tableData.map((tableItem) => {
          tableItem.allAddress = ''
          CityInfo.map((item) => {
            if (tableItem.provinceId == item.value) {
              tableItem.allAddress += item.label
              item.children.map((shi) => {
                if (tableItem.cityId == shi.value) {
                  tableItem.allAddress += shi.label
                  shi.children.map((county) => {
                    if (tableItem.countyId == county.value) {
                      tableItem.allAddress += county.label
                    }
                  })
                }
              })
            }
          })
        })
      })
    },
    // 增加专家
    expertAdd (addForm) {
      this.$refs[addForm].validate((valid) => {
        if (valid) {
          this.axios.get(`expert-infos/check-idNumber/${this.form.idNumber}`).then((res) => {
            checkResponse(this, res, () => {
              this.$router.push({path: '/expert/add', query: {data: this.form}})
            })
          })
        } else {
          return false
        }
      })
    },
    // 修改
    editFun (scope) {
      this.$router.push({path: '/expert/update', query: {objectId: scope.row.objectId}})
    },
    // 单条查看
    seeData (scope) {
      this.$router.push({path: `/expert/detail/${scope.row.objectId}`, query: {data: scope, objectId: scope.row.objectId, code: scope.row.code}})
    },
    // 跳转项目管理
    expertPro (scope) {
      this.$router.push({path: '/expert/projects', query: {objectId: scope.row.objectId, code: scope.row.code, name: scope.row.name}})
    },
    cancel (formName) {
      this.dialogTableVisible = false
      this.form = {}
      this.$refs[formName].resetFields()
    },
    cancelBtn () {
      this.dialogTableVisible = false
      this.$refs['form'].resetFields()
    }
  },
  mounted () {
    this.expertData()
    this.selectClassify()
  }
}
</script>

<style lang="less">
  /*内容开始*/
  #tingcontent_bigbox {
    width: 1200px;
    background: #fff;
    min-height: 50px;
    margin: 0 auto;
    margin-top: 23px;
    /*新建归档按钮*/
    .da_xz_ckbigbox {
      width: 100%;
      height: 58px;
      border-bottom: 1px solid #eeeeee;
      padding: 0 20px;
      box-sizing: border-box;
    }
    .da_xzgd_btn {
      width: 120px;
      height: 36px;
      background: #6582f8;
      opacity: 0.8;
      color: #fff;
      font-size: 14px;
      text-align: center;
      vertical-align: middle;
      float: left;
      margin-top: 11px;
      cursor: pointer;
      transition: all .8s ease;
      border-radius: 0;
    }
    .da_xzgd_btn:hover {
      background: #6582f8;
      opacity: 1;
      color: #fff;
    }
    .seacher_box {
      height: 36px;
      min-width: 50px;
      float: right;
      /* background: red; */
      margin-top: 11px;
    }
    /*表格内容开始*/
    .da_contabbigbox {
      width: 100%;
      min-height: 10px;
    }
    .el-table__header {
      width: 100% !important;
    }
    .el-table {
      font-size: 12px;
      font-weight: normal !important;
      color: #999999;
    }
    th {
      font-weight: normal !important;
      background: transparent !important;
      width: 100% !important;
    }
    .el-table_1_column_1 {
      text-align: center !important;
    }
    th:last-child {
      text-align: center !important;
    }
    .is-leaf {
      background: #f5f7f8 !important;
    }
    .el-table_1_column_1 {
      text-align: center !important;
    }
    .has-gutter > tr {
      background-color: #f5f7f8 !important;
    }
    .el-button--small > span {
      color: #647ef7 !important;
    }
    .seacher_input {
      display: inline-block;
      width: 382px;
      height: 36px;
      border: 1px solid #cccccc;
      vertical-align: top;
      padding: 0 10px;
      box-sizing: border-box;
      font-size: 13px;
    }
    .seacher_btn {
      display: inline-block;
      width: 36px;
      height: 36px;
      background: #ebeffe;
      vertical-align: top;
      margin-left: -3px;
      position: relative;
      cursor: pointer;
      transition: all .8s ease;
    }
    .seacher_btn > img {
      width: 16px;
      position: absolute;
      left: 0;
      right: 0;
      top: 0;
      bottom: 0;
      margin: auto;
    }
    .el-table .cell {
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      word-break: break-all;
      height: 23px;
      line-height: 23px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .el-table .cell p{
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      line-height: 30px;
    }
  }

  #tingxjwdfather {
    /*弹框内容样式*/
    .el-dialog__body {
      padding: 0px 0px;
      color: #606266;
      line-height: 24px;
      font-size: 14px;
    }
    .el-dialog__header {
      padding: 20px 20px 10px;
      border-bottom: 1px solid #eee;
    }
    .el-dialog__title {
      line-height: 24px;
      font-size: 16px;
      color: #111111;
    }
    .xingzhi_box {
      width: 100%;
      height: 52px;
      border-bottom: 1px solid #eeeeee;
      padding: 0 20px;
      box-sizing: border-box;
      font-size: 14px;
      color: #111111;
      margin-bottom: 15px;
      margin-top: 15px;
    }
    .xinzhileft_box {
      float: left;
      height: 80%;
    }
    .xinzhiright_box {
      float: right;
    }
    .el-input__inner {
      -webkit-appearance: none;
      background-image: none;
      border-radius: 0px !important;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      display: inline-block;
      line-height: 40px;
      outline: 0;
      width: 140px;
      height: 35px;
      border: none;
      opacity: 0.8;
      color: #3f63f7 !important;
      padding: 0 10px;
      outline: none;
      font-size: 14px;
      position: relative;
      text-align: left;
      cursor: pointer;
      -webkit-transition: border-color .2s cubic-bezier(.645, .045, .355, 1);
      transition: border-color .2s cubic-bezier(.645, .045, .355, 1);
      border: 1px solid #dcdfe6;
    }
    .xxgdxz {
      background: #eceffe;
    }
    .seacher_input {
      display: inline-block;
      width: 382px;
      height: 36px;
      border: 1px solid #cccccc;
      vertical-align: top;
      padding: 0 10px;
      box-sizing: border-box;
      font-size: 13px;
    }
    .seacher_btn {
      display: inline-block;
      width: 36px;
      height: 36px;
      background: #ebeffe;
      vertical-align: top;
      margin-left: -3px;
      position: relative;
      cursor: pointer;
      transition: all .8s ease;
    }
    .seacher_btn > img {
      width: 16px;
      position: absolute;
      left: 0;
      right: 0;
      top: 0;
      bottom: 0;
      margin: auto;
    }
    .el-dialog {
      margin-top: 10vh !important;
    }
    .el-radio-group {
      display: inline-block;
      line-height: 1;
      vertical-align: middle;
      font-size: 0;
      float: left;
      margin-left: 20px;
    }
    .que_qubigbox {
      width: 100%;
      height: 35px;
      margin-top: 20px;
      /* background: red; */
      text-align: center;
      padding-bottom: 20px;
    }
    .queren_btn {
      display: inline-block;
      width: 130px;
      height: 35px;
      background: #3f63f7;
      text-align: center;
      line-height: 35px;
      color: #fff;
      font-size: 14px;
      vertical-align: top;
      margin-right: 45px;
      cursor: pointer;
    }
    .quxiao_btn {
      display: inline-block;
      width: 130px;
      height: 35px;
      background: #ededed;
      text-align: center;
      line-height: 35px;
      color: #676767;
      font-size: 14px;
      vertical-align: top;
      cursor: pointer;
    }
    .selectxxxm_bigbox {
      /*display: none;*/
      width: 50%;
      margin: 0 auto;
      margin-bottom: 120px;
      padding-top: 25px;
    }
    .xxxm_xinxi > .el-input__inner {
      width: 420px;
      height: 35px;
      border: 1px solid #eeeeee;
      box-sizing: border-box;
      text-align: left;
      background: transparent;
    }
    th {
      font-weight: normal !important;
      background: transparent !important;
      width: 100% !important;
    }
    .is-leaf {
      background: #f5f7f8 !important;
    }
    .el-table_2_column_8 > .cell {
      text-align: center;
    }
    .el-table_4_column_18 {
      text-align: center;
    }
    .el-input-group {
      line-height: normal;
      display: inline-table;
      width: 40%;
      border-collapse: separate;
    }
  }

  .tab-item {
    width: 100%;
    height: 30px;
    text-align: center;
    line-height: 30px;
    font-size: 12px;
    color: #008bff;
  }

  .el-popover .el-button {
    width: 100%;
    text-align: center;
    border: none;
    font-size: 12px;
    color: #008bff;
  }

  .el-button--text {
    color: #409EFF;
    background: 0 0;
    padding-left: 0;
    padding-right: 0;
    margin-left: 10px;
  }
  .fenleizztselet{
    width: 20%;
  }
  .fenleiselect{
    width: 65%;
  }
</style>
