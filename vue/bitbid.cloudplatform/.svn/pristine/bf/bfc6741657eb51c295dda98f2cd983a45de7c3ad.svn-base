<template>
  <div class="cloudcontent" id="cloud_projectProcess">
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/processManage/projectProcess' }">项目进度管理</el-breadcrumb-item>
      <el-breadcrumb-item class="active_bread">查看</el-breadcrumb-item>
    </el-breadcrumb>
    <!--面包屑-->
    <div class="main">
      <el-form :model="updateForm" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目名称：">
              <span>{{updateForm.tenderProjectName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目编号：" >
              <span>{{updateForm.tenderProjectName}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="标段名称：">
              <span>{{updateForm.bidSectionName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标段编号：">
              <span>{{updateForm.bidSectionCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="标段类型：">
              <span>{{updateForm.tenderProjectClassifyCodeStr}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标段合同估算价(元)：" >
              <span>{{updateForm.contractReckonPrice}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="招标文件售价(元)：">
              <span>{{updateForm.docGetFee}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24" class="ueditor_box" >
            <el-form-item label="标段内容：" prop="sectionContent" ref="sectionContent">
              <editor ref="sectionEditor" class="ueditor" :editread="editread" :content="updateForm.sectionContent" v-model="updateForm.sectionContent"></editor>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24" class="ueditor_box">
            <el-form-item label="投标人资格条件：" prop="bidderContent" ref="bidderContent">
              <editor ref="bidderEditor" class="ueditor" :editread="editread" :content="bidderContent" v-model="updateForm.bidderContent"></editor>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="$router.go(-1)">返回</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/ueditor/ueditor.vue'
import * as bidsType from '@/assets/js/constants'
import {bidSection} from '@/api/cloudplatform/index'
export default {
  components: {
    editor
  },
  data () {
    return {
      updateForm: {
        tenderProjectName: ''
      },
      // 富文本
      editread: true,
      sectionContent: '',
      bidderContent: '',
      // 标段类型
      bidsTypeOptions: bidsType.bidsType
    }
  },
  created () {
    this.getChooseBidSectionInfo()
  },
  methods: {
    /** 获取当前选择的标段信息 */
    getChooseBidSectionInfo () {
      bidSection.getOne(this.$route.query.code).then((res) => {
        if (res.data.bidSection) {
          this.updateForm = res.data.bidSection
          // 获取文本编辑器的内容
          this.$refs.sectionEditor.setContent(this.sectionContent = this.updateForm.bidSectionContent)
          this.$refs.bidderEditor.setContent(this.bidderContent = this.updateForm.bidQualification)
          // 获取标段（包）分类代码
          let list = this.getClassifyList(this.updateForm.tenderProjectClassifyCode)
          if (list.length > 0) {
            this.updateForm.tenderProjectClassifyCodeStr = this.getClassifyInfo(list)
          }
        }
      })
    },
    /** 获取标段分类代码的集合 */
    getClassifyList (tenderProjectClassifyCode) {
      let list = tenderProjectClassifyCode.split('-')
      return list
    },
    /** 获取招标项目扩展信息 */
    getClassifyInfo (list) {
      let str = ''
      let firstId = this.getLabelName(this.bidsTypeOptions, list[0])
      let secondId = this.getLabelName(this.bidsTypeOptions, list[1])
      let thirdId = this.getLabelName(this.bidsTypeOptions, list[2])
      let fourthId = this.getLabelName(this.bidsTypeOptions, list[3])
      if (firstId.node) {
        str += `${firstId.node.label}`
      }
      if (secondId.node) {
        str += `${secondId.node.label}`
      }
      if (thirdId.node) {
        str += `${thirdId.node.label}`
      }
      if (fourthId.node) {
        str += `${fourthId.node.label}`
      }
      return str
    },
    /** treeData [原始数据集]， key 传入的key值 */
    getLabelName (treeData, key) {
      let parentNode = null
      let node = null
      function getTreeDeepArr (treeData, key) {
        for (let i = 0; i < treeData.length; i++) {
          // 1.如果没有子节点 beark
          if (node) {
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
  }
}
</script>
<style lang="less">
  #cloud_projectProcess {
    .el-select{
      width: 100%;
    }
  }
</style>
