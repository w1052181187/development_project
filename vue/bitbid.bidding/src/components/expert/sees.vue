<template>
  <div>
    <!--内容开始-->
    <div class="content_bigbox" id="zztzhaoLookcontentzj">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/expert' }">专家管理</el-breadcrumb-item>
        <el-breadcrumb-item class="active_bread">查看专家</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <!--内容标题-->
      <el-form class="headerzzt_title" :model="enterpriseform" label-width="120px">
        <el-form-item label="专家姓名：">
          <el-input v-model="enterpriseform.name" readonly></el-input>
        </el-form-item>
      </el-form>
      <!--内容标题-->
      <!--导航切换-->
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="专家信息" name="first">
          <!--标段信息内容-->
          <div class="ckdan_contentbox">
            <el-row type="flex" class="row-bg">
              <el-form :model="enterpriseform">
                <el-col :span="12">
                  <el-form-item label="专家姓名：" :label-width="formLabelWidth">
                    <el-input v-model="enterpriseform.name" readonly></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <div class="grid-content bg-purple">
                    <el-form-item label="性别：" :label-width="formLabelWidth">
                      <el-input v-show="enterpriseform.sex==0" value="保密" readonly></el-input>
                      <el-input v-show="enterpriseform.sex==1" value="男" readonly></el-input>
                      <el-input v-show="enterpriseform.sex==2" value="女" readonly></el-input>
                      <el-input v-show="enterpriseform.sex==null" value="" readonly></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="身份证号码：" :label-width="formLabelWidth">
                    <el-input v-model="enterpriseform.idNumber" readonly></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <div class="grid-content bg-purple">
                    <el-form-item label="出生年月日：" :label-width="formLabelWidth">
                      <el-date-picker
                        v-model="enterpriseform.birth"
                        type="date"
                        disabled
                        value-format="yyyy-MM-dd hh:mm:ss">
                      </el-date-picker>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="专业分类：" class="zyflbig" :label-width="formLabelWidth">
                    <!--<el-cascader-->
                      <!--expand-trigger="hover"-->
                      <!--:options="categoryOptions"-->
                      <!--:title="biaoduanTypeToTitle"-->
                      <!--v-model="selectedOptions" disabled>-->
                    <!--</el-cascader>-->
                      <p v-for="(item, index) in enterpriseform.expertClassifyStandard" :key="index" :title="item" style=" border: 1px solid #eeeeee;border-radius: 5px; padding: 0 10px; margin: 5px 0; color: #111111 !important;">{{item}}</p>
                  </el-form-item>
                </el-col>
                <div class="bdlx_box">
                  <el-col :span="18">
                    <el-form-item label="专家分类：" class="bdlxtitle" :label-width="formLabelWidth">
                      <el-input v-model="enterpriseform.expertClassifySelf" class="sfwt_bdlxinput" readonly></el-input>
                    </el-form-item>
                  </el-col>
                </div>
                <el-col :span="12">
                  <div class="grid-content bg-purple">
                    <el-form-item label="联系电话：" :label-width="formLabelWidth">
                      <el-input v-model="enterpriseform.mobile" readonly></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="电子邮箱：" :label-width="formLabelWidth">
                    <el-input v-model="enterpriseform.email" readonly></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="18">
                  <el-form-item label="所在行政区域：" class="szxzqybig" :label-width="formLabelWidth">
                    <el-cascader class="zcdz"
                       expand-trigger="hover" disabled
                       :options="addressOptions"
                       v-model="cityselectedOptions">
                    </el-cascader>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <div class="grid-content bg-purple">
                    <el-form-item label="通讯地址：" :label-width="formLabelWidth">
                      <el-input v-model="enterpriseform.address" readonly></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="邮政编码：" :label-width="formLabelWidth">
                    <el-input v-model="enterpriseform.zipCode" readonly></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <div class="grid-content bg-purple">
                    <el-form-item label="最后毕业院校：" :label-width="formLabelWidth">
                      <el-input v-model="enterpriseform.school" readonly></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="最高学历：" :label-width="formLabelWidth">
                    <el-input v-model="enterpriseform.education" readonly></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <div class="grid-content bg-purple">
                    <el-form-item label="所在单位：" :label-width="formLabelWidth">
                      <el-input v-model="enterpriseform.company" readonly></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="是否在职：" :label-width="formLabelWidth">
                    <el-input v-show="enterpriseform.isJob==0" readonly value="否"></el-input>
                    <el-input v-show="enterpriseform.isJob==1" readonly value="是"></el-input>
                    <el-input v-show="enterpriseform.isJob==null" readonly value=""></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <div class="grid-content bg-purple">
                    <el-form-item label="职务：" :label-width="formLabelWidth">
                      <el-input v-model="enterpriseform.job" readonly></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="从业年限：" :label-width="formLabelWidth">
                    <el-input v-model="enterpriseform.workAge" readonly></el-input>
                  </el-form-item>
                </el-col>
                <div class="bdlx_box">
                  <el-col :span="18">
                    <el-form-item label="技术职称：" class="bdlxtitle" :label-width="formLabelWidth">
                      <el-input v-model="enterpriseform.jobTitle" class="sfwt_bdlxinput" readonly></el-input>
                    </el-form-item>
                  </el-col>
                </div>
                <el-col :span="24">
                  <el-form-item label="职业资格：" class="yzneirong bdneirongzhiye" :label-width="formLabelWidth">
                    <el-tag
                      v-model="enterpriseform.dynamicTags"
                      :key="index"
                      v-for="(tag,index) in dynamicTags"
                      :disable-transitions="false">
                      <span>{{tag.name}}</span>
                      <span style="margin-left: 10px">{{tag.grade}}</span>
                    </el-tag>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="工作简历：" class="yzneirong" :label-width="formLabelWidth">
                    <el-input
                      type="textarea"
                      v-model="enterpriseform.workResume" class="bdnrong_bdlxinput" readonly>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="专家备注：" class="yzneirong" :label-width="formLabelWidth">
                    <el-input
                      type="textarea"
                      v-model="enterpriseform.expertRemark" class="bdnrong_bdlxinput" readonly>
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-form>
            </el-row>
          </div>
          <!--标段信息内容-->
        </el-tab-pane>
        <el-tab-pane label="合作项目" name="second">
          <!--归档文件内容-->
          <div class="gdwjnrbox_contentbox">
            <!--表格内容开始-->
            <div class="da_contabbigbox">
              <el-table
                :data="tableData"
                border
                style="width: 100%">
                <el-table-column
                  prop="sectionCode"
                  label="标段编号"
                  width="145">
                </el-table-column>
                <el-table-column
                  prop="sectionName"
                  label="标段名称"
                  width="145">
                </el-table-column>
                <el-table-column
                  prop="tenderProjectNumber"
                  label="招标项目编号"
                  width="140">
                </el-table-column>
                <el-table-column
                  prop="tenderProjectName"
                  label="所属招标项目"
                  width="145">
                </el-table-column>
                <el-table-column
                  prop="ownerName"
                  label="业主名称"
                  width="145">
                </el-table-column>
                <el-table-column
                  prop="biaoduan"
                  label="标段类型"
                  width="145">
                </el-table-column>
                <el-table-column
                  prop="judgeDate"
                  label="评审时间"
                  width="160">
                </el-table-column>
                <el-table-column
                  prop="projectNature"
                  label="项目性质"
                  width="135">
                  <template slot-scope="scope">
                    {{scope.row.projectNature===0?'线下项目':'在线项目'}}
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
              @current-change="handleCurrentChange">
            </el-pagination>
            <!--分页-->
          </div>
          <!--归档文件内容-->
        </el-tab-pane>
      </el-tabs>
      <!--导航切换-->
    </div>
    <!--内容结束-->
  </div>
</template>
<script>
import * as expertClassify from '../../assets/js/expertClassify'
import * as citydata from '../../assets/js/region'
import * as constants from '../../assets/js/constants'
import {addtitle} from '../../assets/js/common'
export default {
  data () {
    return {
      // 默认显示
      activeName: 'first',
      // 内容展示input
      enterpriseform: {},
      // 合作项目表单数据
      tableData: [],
      total: 0, // 总条数
      loading: true,
      pageSize: 15, // 每页展示条数
      pageNo: 0,
      dataIndex: 1,
      formLabelWidth: '120px',
      // 职业资格
      dynamicTags: [],
      categoryOptions: expertClassify.expertClassify,
      selectedOptions: [],
      // 三级联动
      addressOptions: citydata.CityInfo,
      cityselectedOptions: []
    }
  },
  methods: {
    // 导航切换
    handleClick (tab) {
      if (tab.label == '专家姓名') {
        this.enterpriseCon()
      } else if (tab.label == '合作项目') {
        this.cooperation()
      }
    },
    handleCurrentChange (nowNum) {
      this.loading = true
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this.pageNo = (nowNum - 1) * this.pageSize
      this.cooperation()
    },
    // 企业内息展示
    enterpriseCon () {
      this.axios.get(`expert-infos/${this.$route.query.objectId}?_t=${new Date().getTime()}`).then((res) => {
        this.enterpriseform = res.data.expert
        var provincename = this.enterpriseform.provinceId
        var cityname = this.enterpriseform.cityId
        var countyname = this.enterpriseform.countyId
        this.cityselectedOptions = [provincename, cityname, countyname]
        this.dynamicTags = res.data.expert.professionalQualifications
        this.loading = false
        let expertClassify = res.data.expert.expertClassifyStandard
        if (expertClassify) {
          let expertArry = expertClassify.split(';')
          if (expertArry) {
            expertClassify = this.transLabel(expertArry)
          }
        }
        this.enterpriseform.expertClassifyStandard = expertClassify
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
    // 合作信息展示
    cooperation () {
      this.axios.get(`expert-projects?pageNo=${this.pageNo}&pageSize=${this.pageSize}&expertCode=${this.$route.query.code}&_t=${new Date().getTime()}`).then((res) => {
        this.tableData = res.data.projectInformationInfo.list
        this.total = res.data.projectInformationInfo.total
        this.loading = false
        this.tableData.map((item, index) => {
          var type = ''
          item.sectionClassify = item.sectionClassify.split(',') // 遍历接收的每条数据使得标段类型由字符串转为数组
          constants.bidsType.map((item1) => {
            if (item.sectionClassify[0] == item1.value) {
              type += item1.label
              item1.children.map((item2) => {
                if (item.sectionClassify[1] == item2.value) {
                  type += item2.label
                  item2.children.map((item3) => {
                    if (item.sectionClassify[2] == item3.value) {
                      type += item3.label
                      item3.children.map((item4) => {
                        if (item.sectionClassify[3] == item4.value) {
                          type += item4.label
                        }
                      })
                    }
                  })
                }
              })
            }
          })
          item.biaoduan = type
        })
        addtitle(this)
      })
    }
  },
  mounted () {
    this.enterpriseCon()
    this.selectedOptions = this.$route.query.data.row.expertClassifyStandard
  },
  computed: {
    biaoduanTypeToTitle () {
      var title = ''
      expertClassify.expertClassify.map((item, index) => {
        if (item.value == this.selectedOptions[0]) {
          title += item.label + '/'
          item.children.map((item2, index) => {
            if (item2.value == this.selectedOptions[1]) {
              title += item2.label + '/'
              item2.children.map((item3) => {
                if (item3.value == this.selectedOptions[2]) {
                  title += item3.label + '/'
                  item3.children.map((item4) => {
                    if (item4.value == this.selectedOptions[3]) {
                      title += item4.label
                    }
                  })
                }
              })
            }
          })
        }
      })
      return title
    }
  }
}
</script>
<style lang="less">
  #zztzhaoLookcontentzj {
    width: 1200px;
    background: #fff;
    min-height: 50px;
    margin: 0 auto;
    margin-top: 23px;
    padding-bottom:50px!important;
    /*查看档案*/
    .content_tiltebox {
      width: 100%;
      height: 60px;
      /* background: red; */
      line-height: 60px;
      padding: 0 20px;
      box-sizing: border-box;
      font-size: 15px;
    }
    .gsmczzt {
      color: #666666;
    }
    .nav_switchbox {
      width: 100%;
      background: #ebeffe;
      padding: 0 10px;
      box-sizing: border-box;
    }
    .el-menu-item {
      width: 97px;
      height: 47px;
      /* background: red; */
      float: left;
      margin-top: 5px;
      text-align: center;
      line-height: 47px;
      font-size: 14px;
      color: #111111;
      cursor: pointer;
    }
    .is-active {
      background: #fff;
      color: #3f63f6;
    }
    .ckdan_contentbox {
      padding: 0px 18px;
      box-sizing: border-box;
      margin-top: 22px;
    }
    .el-input__prefix{
      display: none;
    }
    .gdwjnrbox_contentbox {
      padding: 0px 18px;
      box-sizing: border-box;
      margin-top: 22px;
      /*background: red;*/
      /*display: none;*/
    }
    form {
      display: block;
      margin-top: 0em;
      width: 100%;
    }
    .el-form-item__label {
      /*width: 25%!important;;*/
      /* background: teal; */
      width: 120px!important;
      height: 100%;
      float: left;
      text-align: right;
      color: #666666;
      font-size: 14px;
      line-height: 40px;
    }
    .el-form-item__content {
      width: 75% !important;
      height: 100%;
      float: left;
      padding: 0 5px;
      box-sizing: border-box;
      font-size: 14px;
      margin-left: 0 !important;
      color: #666666 !important;
    }
    .el-input__inner {
      width: 100%;
      height: 40px;
      border: 1px solid #eeeeee;
      box-sizing: border-box;
      padding: 0 10px;
      color: #111111 !important;
      outline: none;
      background: transparent !important;
      text-align: left;
    }
    .bdlxinput {
      width: 105px;
      height: 100%;
      /*border: 1px solid #eeeeee;*/
      color: #111111;
      float: left;
      margin-right: 16px;
      letter-spacing: 0px;
      text-align: center;
      line-height: 40px;
      font-size: 14px;
      box-sizing: border-box;
    }
    .sfwt_bdlxinput {
      width: 66% !important;
    }
    .bdnrong_bdlxinput {
      height: 100%;
    }
    .bdnrong_bdlxinput > .el-textarea__inner {
      background: transparent !important;
      color: #111111 !important;
      resize: none;
    }
    .el-form-item__content > .el-textarea {
      height: 100px !important;
    }
    /*新增归档样式*/
    .zbfs_bigbox {
      width: 130px;
    }
    .zzt_zbfs_selectbox {
      width: 100%;
    }
    .el-form-item__content > .el-textarea {
      height: 100%;
    }
    .el-form-item__content > .el-textarea > .el-textarea__inner {
      height: 100% !important;
      resize: none;
    }
    .jdxx_bigbox {
      width: 100%;
      min-height: 20px;
      margin-top: 30px;
      margin-bottom: 20px;
      text-align: center;
    }
    .jdxx_sonbox {
      display: inline-block;
      min-width: 50px;
      height: 30px;
      vertical-align: top;
      line-height: 30px;
      font-size: 14px;
      color: rgb(51, 51, 51);
    }
    .hengxian_box {
      display: inline-block;
      width: 105px;
      height: 30px;
      vertical-align: top;
      padding: 0px 15px;
    }
    .dangqian_jin {
      display: inline-block;
      width: 30px;
      height: 30px;
      vertical-align: top;
      color: rgb(255, 255, 255);
      text-align: center;
      line-height: 30px;
      margin-right: 5px;
      background: rgb(187, 187, 187);
      border-radius: 50%;
    }
    .hengxian {
      width: 100%;
      height: 2px;
      margin-top: 14px;
      background: rgb(187, 187, 187);
    }
    .dangqian_dangqian {
      background: rgb(63, 99, 246);
    }
    .gongneng_btnbox {
      text-align: center;
      padding-bottom: 30px;
    }
    .gongneng_btn {
      width: 120px;
    }
    .sfwtdl_btn {
      width: 66%;
    }
    .bdnrxxlb_tabbox > div > div > i {
      background: transparent !important;
    }
    /*查看*/
    .dabao_xiazai_btnbox {
      display: inline-block;
      width: 120px;
      height: 36px;
      background: #6582f8;
      color: #fff;
      font-size: 14px;
      text-align: center;
      line-height: 36px;
      cursor: pointer;
      margin-left: 5px;
    }
    .dabaooxia_icon {
      display: inline-block;
      width: 18px;
      vertical-align: top;
      margin-top: 9px;
    }
    .ckgdwjbox {
      padding-bottom: 30px;
    }
    .bdnrxxlb_tabbox > div > .el-collapse-item__header {
      border: none;
      width: 100%;
      height: 45px;
      background: #f5f7f8;
      padding: 0 10px;
      box-sizing: border-box;
      margin-top: 20px;
      font-size: 12px;
      position: relative;
    }
    .is-active {
      background: #f5f7f8;
      color: #3f63f6;
    }
    .el-collapse-item__wrap {
      border-bottom: none;
      width: 100%;
    }
    .xuhao_num {
      display: inline-block;
      width: 10px;
      color: #999999;
    }
    .bdlist_title {
      margin-left: 15px;
      color: #111111;
    }
    .delet_btnbox {
      display: inline-block;
      width: 12px;
      height: 100%;
      /*background: red;*/
      position: absolute;
      right: 40px;
      top: 0;
    }
    .delet_btnbox > img {
      width: 100%;
      position: absolute;
      top: 0;
      bottom: 0;
      margin: auto;
    }
    .baocun_btnbox {
      display: inline-block;
      width: 12px;
      height: 100%;
      /*background: red;*/
      position: absolute;
      right: 63px;
      top: 0;
    }
    .baocun_btnbox > img {
      width: 100%;
      position: absolute;
      top: 0;
      bottom: 0;
      margin: auto;
    }
    /*点击展开表格展示*/
    .zhankai_con_sontitlebox {
      width: 100%;
      height: 55px;
      line-height: 55px;
      font-size: 14px;
      color: #111111;
      text-align: center;
    }
    .tianjia_btnbox {
      width: 100%;
      height: 36px;
      /* background: red; */
      margin: 10px auto;
    }
    .tianjia_btn {
      width: 120px;
      height: 100%;
      background: #6582f8;
      text-align: center;
      line-height: 36px;
      color: #fff;
      font-size: 14px;
      cursor: pointer;
    }
    .tianjia_btn {
      color: #fff;
      font-size: 14px;
      cursor: pointer;
    }
    .xuhaotab {
      text-align: center;
    }
    .el-table th {
      text-align: center;
    }
    .chakna_yichu {
      text-align: center;
    }
    .el-table_2_column_4 {
      text-align: center;
    }
    .jdxx_bigbox {
      width: 100%;
      min-height: 20px;
      margin-top: 30px;
      margin-bottom: 20px;
      text-align: center;
    }
    .jdxx_sonbox {
      display: inline-block;
      min-width: 50px;
      height: 30px;
      vertical-align: top;
      line-height: 30px;
      font-size: 14px;
      color: rgb(51, 51, 51);
    }
    .hengxian_box {
      display: inline-block;
      width: 105px;
      height: 30px;
      vertical-align: top;
      padding: 0px 15px;
    }
    .dangqian_jin {
      display: inline-block;
      width: 30px;
      height: 30px;
      vertical-align: top;
      color: rgb(255, 255, 255);
      text-align: center;
      line-height: 30px;
      margin-right: 5px;
      background: rgb(187, 187, 187);
      border-radius: 50%;
    }
    .hengxian {
      width: 100%;
      height: 2px;
      margin-top: 14px;
      background: rgb(187, 187, 187);
    }
    .dangqian_dangqian {
      background: rgb(63, 99, 246);
    }
    .gongneng_btnbox {
      text-align: center;
      padding-bottom: 30px;
    }
    .gongneng_btn {
      width: 120px;
    }
    .sfwtdl_btn {
      width: 66%;
    }
    .bdnrxxlb_tabbox > div > div > i {
      background: transparent !important;
    }
    th {
      font-weight: normal !important;
      background: transparent !important;
      width: 100% !important;
    }
    .is-leaf {
      background: #f5f7f8 !important;
    }
    .el-table th > .cell {
      position: relative;
      word-wrap: normal;
      text-overflow: ellipsis;
      vertical-align: middle;
      width: 100%;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      text-align: left;
      font-size: 12px;
    }
    .el-table .cell {
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      white-space: normal;
      word-break: break-all;
      height: 23px;
      line-height: 23px;
      font-size: 12px;
    }
    .el-tabs__nav-scroll {
      overflow: hidden;
      width: 90%;
      margin: 0 auto;
    }
    .el-tabs__nav-wrap::after {
      content: "";
      position: absolute;
      left: 0;
      bottom: 0;
      width: 100%;
      height: 2px;
      background-color: #fff;
      z-index: 1;
    }
    .headerzzt_title{
      margin-top: 30px;
    }
    .headerzzt_title input{
      border: none;
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
    .bdneirongzhiye .el-form-item__content{
      border: 1px solid #dcdfe6;
      border-radius: 5px;
      height:100px;
    }
    .zcdz{
      width: 100%;
    }
    .zyflbig .el-form-item__content{
      width: 37.5%!important;
    }
    .szxzqybig{
      width: 67%!important;
    }
    .zyflbig .el-form-item__content .el-cascader {
      display: inline-block;
      position: relative;
      font-size: 14px;
      line-height: 40px;
      width: 100%;
    }
    .el-tabs__nav-wrap {
      background: #e5f3ff;
    }
    .el-tabs--top .el-tabs__item.is-top:nth-child(2){
      padding: 0 20px
    }
    .el-tabs--top .el-tabs__item.is-top:last-child{
      padding: 0 20px
    }
    .el-tabs__active-bar{
      background-color:#fff;
    }
    .el-icon-date:before{
      display: none;
    }
  }
</style>
