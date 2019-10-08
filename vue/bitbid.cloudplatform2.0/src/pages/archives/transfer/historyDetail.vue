<template>
  <div class="cloudcontent" id="cloud_historyProjectAdd">
    <div class="main">
      <div class="basic-info-title">项目基本信息</div>
      <el-form :model="addForm">
        <el-row>
          <el-col :span="12">
            <el-form-item label="招标项目编号：">
              <span>{{addForm.tenderProjectCode}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目编号：">
              <span>{{addForm.projectCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目名称：">
              <span>{{addForm.tenderProjectName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目时间：">
              <span>{{addForm.projectDate | filterDate}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="招标人：">
              <span>{{addForm.tendererName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="代理合同：">
             <span>{{addForm.agencyContractName}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目类型：">
             <span>{{addForm.tenderProjectType | filterTenderProjectType}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目地址：">
             <span>{{addForm.tenderProjectAddressStr}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="详细地址：">
              <span>{{addForm.address}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目预计收入(元)：">
              <span>{{addForm.projectIncome}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目行业分类：">
              <span>{{addForm.industryTypeStr}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资质主体：">
             <span>{{addForm.companyMainBodyName}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目所属部门：">
             <span>{{addForm.departmentName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="招标方式：">
             <span>{{addForm.tenderMode | filterTenderMode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否资格预审：" v-if="addForm.tenderMode === 1">
             <span>{{addForm.isPreBid | filterIsPreBid}}</span>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div class="project-info-line"></div>
    <div class="main">
      <div class="basic-info-title">项目组成员</div>
      <el-table
        :data="memberData"
        border
        style="width: 100%" header-cell-class-name="tableheader">
        <el-table-column
          width="50"
          type="index"
          align="center"
          label="序号">
        </el-table-column>
        <el-table-column
          prop="userName"
          label="姓名"
          align="center"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="positionType"
          label="职责分工"
          align="center"
          :formatter="filterPositionType"
          show-overflow-tooltip>
        </el-table-column>
      </el-table>
    </div>
    <div class="project-info-line"></div>
    <div class="main">
      <div class="basic-info-title">标段信息</div>
      <el-table
        :data="sectionData"
        border
        style="width: 100%" header-cell-class-name="tableheader">
        <el-table-column
          width="50"
          type="index"
          align="center"
          label="序号">
        </el-table-column>
        <el-table-column
          prop="bidSectionCode"
          label="标段编号"
          align="center"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="bidSectionName"
          label="标段名称"
          align="center"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="tenderProjectClassifyCodeStr"
          label="标段类型"
          align="center"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="docGetFee"
          label="标段文件售价（元）"
          align="center"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="bidOpeningTime"
          label="开标时间"
          align="center"
          v-if="addForm.isPreBid !== 1"
          :formatter="formatDate"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="prequalificationTime"
          label="资格预审会时间"
          align="center"
          v-if="addForm.isPreBid === 1"
          :formatter="formatDate"
          show-overflow-tooltip>
        </el-table-column>
      </el-table>
      <div class="submit-radio">
        <el-button class="cancal" @click="close">返回</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import * as region from '@/assets/js/region'
import * as industry from '@/assets/js/industry_two'
import {tenderProject} from '@/api/project'
import {dateFormat} from '@/assets/js/common'
import * as bidsType from '@/assets/js/constants'
export default {
  components: {
  },
  data () {
    return {
      timeFlag: false,
      loading: false,
      isSave: false,
      addForm: {
      },
      // 地址三级联动
      addressOptions: region.CityInfo,
      // 行业三级联动
      industryOptions: industry.industry,
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 100, // 总条数
      pageSize: 10, // 每页展示条数
      searchForm: {
        agencyContractNameLike: ''
      },
      typeObj: {
        1: '工程',
        2: '货物',
        3: '服务'
      },
      methodObj: {
        1: '公开招标',
        2: '邀请招标',
        3: '竞争性谈判',
        4: '竞争性磋商',
        5: '询价',
        6: '单一来源'
      },
      preObj: {
        1: '是',
        0: '否'
      },
      memberData: [],
      sectionData: [],
      // 标段类型
      bidsTypeOptions: bidsType.bidsType
    }
  },
  filters: {
    /** 项目类型 */
    filterTenderProjectType (value) {
      if (value === '1') {
        return '工程'
      } else if (value === '2') {
        return '货物'
      } else if (value === '3') {
        return '服务'
      }
    },
    /** 招标方式 */
    filterTenderMode (value) {
      if (Number(value) === 1) {
        return '公开招标'
      } else if (Number(value) === 2) {
        return '邀请招标'
      } else if (Number(value) === 3) {
        return '竞争性谈判'
      } else if (Number(value) === 4) {
        return '单一来源采购'
      } else if (Number(value) === 5) {
        return '询价采购'
      } else if (Number(value) === 6) {
        return '竞争性磋商'
      }
    },
    /** 是否资格预审 */
    filterIsPreBid (value) {
      if (value === 0) {
        return '否'
      } else if (value === 1) {
        return '是'
      }
    },
    /** 格式化申请时间 */
    filterDate (bidOpeningTime) {
      if (bidOpeningTime) {
        return bidOpeningTime ? dateFormat(bidOpeningTime, 'yyyy-MM-dd') : '---'
      }
    }
  },
  methods: {
    /** 职位类型:1.项目经理;2.项目助理 */
    filterPositionType (row) {
      if (row.positionType === 1) {
        return '项目经理'
      } else if (row.positionType === 2) {
        return '项目助理'
      }
    },
    // 格式化时间
    formatDate (row, col, cellValue) {
      return cellValue ? dateFormat(cellValue, 'yyyy-MM-dd hh:mm') : '---'
    },
    close () {
      this.$store.commit('delete_tabs', this.$route.fullPath)
      this.$router.go(-1)
    },
    /** 获取当前招标项目信息 */
    getChooseTenderProjectInfo () {
      tenderProject.getOne(this.$route.params.code).then((res) => {
        if (res.data.tenderProject) {
          this.addForm = res.data.tenderProject
          this.addForm.address = this.addForm.tenderExpandsInfo.address
          // 获取项目扩展信息
          this.addForm.tenderProjectAddressStr = this.getTenderExpandsInfo(this.addForm.tenderExpandsInfo, 'address')
          this.addForm.industryTypeStr = this.getTenderExpandsInfo(this.addForm.tenderExpandsInfo, 'industry')
          if (res.data.projectLeaderList) {
            this.memberData = res.data.projectLeaderList
          }
          if (res.data.sectionList) {
            this.sectionData = res.data.sectionList
            this.sectionData.forEach(item => {
              // 获取标段（包）分类代码
              let list = this.getClassifyList(item.tenderProjectClassifyCode)
              if (list.length > 0) {
                item.tenderProjectClassifyCodeStr = this.getClassifyInfo(list)
              }
            })
          }
        }
      })
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
        str += '/'
      }
      if (secondId.node) {
        str += `${secondId.node.label}`
        str += '/'
      }
      if (thirdId.node) {
        str += `${thirdId.node.label}`
        str += '/'
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
    },
    /** 获取招标项目扩展信息 */
    getTenderExpandsInfo (tenderExpandsInfo, type) {
      let str = ''
      if (Object.is(type, 'address')) {
        let provinceId = this.getLabelName(region.CityInfo, tenderExpandsInfo.provinceId)
        let cityId = this.getLabelName(region.CityInfo, tenderExpandsInfo.cityId)
        let countyId = this.getLabelName(region.CityInfo, tenderExpandsInfo.countyId)
        if (provinceId.node) {
          str += `${provinceId.node.label}`
          str += '/'
        }
        if (cityId.node) {
          str += `${cityId.node.label}`
          str += '/'
        }
        if (countyId.node) {
          str += `${countyId.node.label}`
        }
      } else if (Object.is(type, 'industry')) {
        let industryTypeFirst = this.getLabelName(industry.industry, tenderExpandsInfo.industryTypeFirst)
        let industryTypeSecond = this.getLabelName(industry.industry, tenderExpandsInfo.industryTypeSecond)
        if (industryTypeFirst.node) {
          str += `${industryTypeFirst.node.label}`
          str += '/'
        }
        if (industryTypeSecond.node) {
          str += `${industryTypeSecond.node.label}`
        }
      }
      return str
    },
    /** 获取标段分类代码的集合 */
    getClassifyList (tenderProjectClassifyCode) {
      let list = tenderProjectClassifyCode.split('-')
      return list
    }
  },
  computed: {
  },
  created () {
    this.getChooseTenderProjectInfo()
  }
}
</script>
<style lang="less">
  #cloud_historyProjectAdd {
    .el-cascader, .el-select{
      width: 100%;
    }
    .el-radio__label{
      display: none;
    }
    .member-form {
      width: 100%;
    }
    .addMember {
      margin-left: 24px;
      color: #409EFF;
      cursor: pointer;
      font-size: 14px;
    }
  }
</style>
