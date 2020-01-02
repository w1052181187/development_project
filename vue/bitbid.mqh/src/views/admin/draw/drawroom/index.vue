<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="drawroom-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>抽签系统</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/draw/drawroom' }">抽签</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 搜索条件 -->
    <div class="search-box room-search">
      <el-row type="flex" justify="start">
        <el-col :span="5" style="width:300px">
          <div class="search search-name">
            <el-form ref="formSearch" :model="formSearch" label-width="90px">
              <el-form-item label="项目名称：">
                <el-input v-model="formSearch.projectName" prepend="项目名称" :maxlength="70" placeholder="请输入项目名称"></el-input>
              </el-form-item>
            </el-form>
          </div>
        </el-col>
        <el-col :span="5" style="width:300px">
         <div class="search search-name">
            <el-form ref="formSearch" :model="formSearch" label-width="90px">
              <el-form-item label="项目单位:" prop="enterpriseNameLike">
                <el-input v-model="formSearch.enterpriseNameLike" placeholder="请输入项目单位"></el-input>
              </el-form-item>
             <!-- <el-form-item label="项目单位：">
                <el-select v-model="formSearch.enterpriseId" clearable placeholder="请选择项目单位" style="width: 100%">
                  <el-option v-for="item in enterprises" :key="item.objectId" :label="item.name" :value="item.objectId">
                  </el-option>
                </el-select>
              </el-form-item>-->
            </el-form>
          </div>
        </el-col>
        <el-col :span="6" style="width:320px">
          <div class="search search-type">
            <el-form ref="formSearch" :model="formSearch" label-width="120px">
              <el-form-item label="抽签类型：">
                <el-select v-model="formSearch.type" clearable placeholder="请选择" class="search-sel">
                  <el-option label="代理机构选聘" value="1"></el-option>
                  <el-option label="咨询造价机构" value="2"></el-option>
                  <el-option label="审计评估机构" value="3"></el-option>
                  <el-option label="法律服务机构" value="4"></el-option>
                </el-select>
              </el-form-item>
            </el-form>
          </div>
        </el-col>
        <el-col :span="5" style="width:320px">
          <div class="search search-state">
            <el-form ref="formSearch" :model="formSearch" label-width="100px">
              <el-form-item label="抽取状态：">
                <el-select v-model="formSearch.status" clearable placeholder="请选择" class="search-sel">
                  <el-option label="未抽取" value="0"></el-option>
                  <el-option label="已抽取" value="1"></el-option>
                </el-select>
              </el-form-item>
            </el-form>
          </div>
        </el-col>
        <el-col :span="6" style="width:120px">
          <div class="search search-btn">
            <el-button type="primary" @click="onSearch" icon="el-icon-search">搜索</el-button>
          </div>
        </el-col>
      </el-row>
    </div>
    <!-- 搜索条件结束 -->
    <!-- 抽签列表展示 -->
    <div class="maincont-box drawroom-cont">
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
          width="50px">
        </el-table-column>
        <el-table-column
          align="left"
          header-align="center"
          prop="projectNameList"
          label="项目名称">
          <template slot-scope="scope">
            <p v-for="(item,index) in scope.row.projectNameList" :key="index" class="dresult-span">{{item}}</p>
          </template>
        </el-table-column>
        <el-table-column
          align="left"
          header-align="center"
          prop="enterpriseNameList"
          label="项目单位">
          <template slot-scope="scope">
            <p v-for="(item,index) in scope.row.enterpriseNameList" :key="index" class="dresult-span">{{item}}</p>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="type"
          label="抽签类型">
          <template slot-scope="scope">
            <p v-for="(item,index) in scope.row.typeInfoList" :key="index" class="dresult-span">{{item}}</p>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="extractTime"
          label="拟抽签时间"
          :formatter="filterDate">
        </el-table-column>
        <el-table-column
          align="center"
          prop="status"
          label="抽签状态">
          <template slot-scope="scope">
            <span class="draw-complete" v-show="scope.row.status === 1">已完成</span>
            <span class="draw-uncomplete" v-show="scope.row.status === 0">未完成</span>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="drawLotsTime"
          label="抽签时间"
          :formatter="filterDate"
         >
        </el-table-column>
        <el-table-column
          align="center"
          label="操作"
          width="120">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row, 'look')" type="text" size="medium" v-show="scope.row.status === 1" v-if="$store.getters.permissions.includes('/draw/drawroom/drawLots')">查看</el-button>
            <el-button
              @click="handleClick(scope.row, 'room')"
              type="text"
              size="medium"
              v-show="scope.row.status === 0"
              v-if="$store.getters.permissions.includes('/draw/drawroom/drawLots')">
              进入抽签室
            </el-button>
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
    <!-- 抽签列表展示结束 -->
  </div>
</template>
<script>
import {drawroom, enterprise, drawrecord} from '../../../../api/index'
import {formatDate} from '../../../../common/js/date.js'
// 实例
export default {
  data () {
    return {
      formSearch: {},
      enterprises: [],
      stateList: [],
      tableData: [],
      pageNo: 0,
      currentPage: 1,
      pageSize: 10,
      total: 0, // 总条数
      pageSizeList: [10, 20, 30, 40, 50]
    }
  },
  created () {
    this.getDrawRoomList()
    // this.getAllEnterprise()
  },
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 格式化申请时间 */
    filterDate (row, column) {
      let date = ''
      if (row.extractTime && column.property === 'extractTime') {
        date = new Date(row.extractTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm')
      } else if (row.drawLotsTime && column.property === 'drawLotsTime') {
        date = new Date(row.drawLotsTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm')
      }
    },
    /** 中介机构类型的选择 */
    filterType (type) {
      if (type === 1) {
        return '代理机构选聘'
      } else if (type === 2) {
        return '咨询造价机构'
      } else if (type === 3) {
        return '审计评估机构'
      } else if (type === 4) {
        return '法律服务机构'
      }
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 表单分页 */
    handleSizeChange (val) {
      var likeThis = this
      var pageSize = `${val}`
      this.pageNo = 0
      this.currentPage = 1
      this.pageSize = parseInt(pageSize)
      this.getDrawRoomList(this.pageNo, this.pageSize, function (resp) {
        likeThis.total = resp.data.Data.Total
      })
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getDrawRoomList(parseInt(this.pageNo), parseInt(this.pageSize))
    },
    /** 搜索 */
    onSearch () {
      this.getDrawRoomList()
    },
    /** 表格操作 */
    handleClick (data, type) {
      if (type === 'look') {
        this.$router.push({path: '/drawroom/details', query: {name: 'details', objectId: data.objectId, btn: true}})
      } else if (type === 'room') {
        this.checkIsEnterDready(data)
        /* if (this.$store.getters.authUser.enterpriseType === 1) {
          this.$router.push({path: '/drawroom/dready', query: {name: 'dready', roomId: data.objectId}})
        } else {
          this.checkIsEnterDready(data)
        } */
      }
    },
    /** 判断当前用户是否可以进入抽签室 */
    checkIsEnterDready (data) {
      drawroom.getDrawRoomInfo(data.objectId).then((res) => {
        let drawEnterpriseList = []
        let flag = false
        let currentUserName = ''
        let currentEnterpriseName = ''
        // 已进入抽签室的单位
        if (res.data.drawRoomInfo && res.data.drawRoomInfo.enterprises) {
          drawEnterpriseList = res.data.drawRoomInfo.enterprises
        }
        // 先判断当前用户所代表的企业是否已经存在于抽签室中
        if (drawEnterpriseList && drawEnterpriseList.length > 0) {
          for (let k = 0; k < drawEnterpriseList.length; k++) {
            if (this.$store.getters.authUser.enterpriseId === drawEnterpriseList[k].enterpriseId) {
              flag = true
              currentUserName = this.$store.getters.authUser.userName
              currentEnterpriseName = this.$store.getters.authUser.enterpriseName
              break
            }
          }
          if (flag) {
            let msg = currentEnterpriseName + '的用户' + currentUserName + '已经进入抽签室，不能重复进入'
            this.$confirm(msg, '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning',
              beforeClose: (action, instance, done) => {
                flag = false
                done()
              }
            }).then(() => {
            }).catch(() => {
              return false
            })
          } else {
            this.checkIsHasGroupCompany(data)
          }
        } else {
          this.checkIsHasGroupCompany(data)
        }
      })
    },
    /** 判断当前项目分组中是否包办招标办的项目，如果包含则不需要再有另外一个招标办人员进入 */
    checkIsHasGroupCompany (data) {
      let flag = false
      for (let i = 0; i < data.selectApplyList.length; i++) {
        if (data.selectApplyList[i].enterpriseType === 1) {
          flag = true // 有招标办的项目
          break
        }
      }
      if (flag) {
        this.compareInHasGroupCompany(data)
      } else {
        this.compareInNoHasGroupCompany(data)
      }
    },
    /** 用户信息与参与抽签的项目进行匹配（有招标办的项目） */
    compareInHasGroupCompany (data) {
      for (let i = 0; i < data.selectApplyList.length; i++) {
        if (this.$store.getters.authUser.enterpriseId === data.selectApplyList[i].enterpriseId) {
          data.selectApplyList[i].drawerId = this.$store.getters.authUser.userId
          drawrecord.save(data.selectApplyList[i])
          this.$router.push({path: '/drawroom/dready', query: {name: 'dready', roomId: data.objectId}})
          break
        } else {
          if (i === data.selectApplyList.length - 1) {
            this.$confirm('该分组中不包括该家企业的项目，不允许进入', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
            }).catch(() => {
              return false
            })
          }
        }
      }
    },
    /** 用户信息与参与抽签的项目进行匹配 (没有招标办的项目) */
    compareInNoHasGroupCompany (data) {
      if (this.$store.getters.authUser.enterpriseType === 1) {
        this.$router.push({path: '/drawroom/dready', query: {name: 'dready', roomId: data.objectId}})
      } else {
        this.compareInHasGroupCompany(data)
      }
    },
    /** 获取列表信息 */
    getDrawRoomList () {
      drawroom.getList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        messageLike: this.formSearch.projectName || null,
        enterpriseId: this.formSearch.enterpriseId || null,
        enterpriseNameLike: this.formSearch.enterpriseNameLike || null,
        type: this.formSearch.type || null,
        status: this.formSearch.status || null
      }).then((res) => {
        this.tableData = res.data.projectGroupList.list
        this.total = res.data.projectGroupList.total
        if (this.tableData.length > 0) {
          for (let i = 0; i < this.tableData.length; i++) {
            let typeInfo = []
            let projectName = []
            let enterpriseName = []
            if (this.tableData[i].selectApplyList.length > 0) {
              for (let j = 0; j < this.tableData[i].selectApplyList.length; j++) {
                projectName[j] = this.tableData[i].selectApplyList[j].projectName
                enterpriseName[j] = this.tableData[i].selectApplyList[j].enterpriseName
                typeInfo[j] = this.filterType(this.tableData[i].selectApplyList[j].type)
              }
              this.tableData[i].projectNameList = projectName
              this.tableData[i].enterpriseNameList = enterpriseName
              this.tableData[i].typeInfoList = typeInfo
            }
          }
        }
      })
    },
    getAllEnterprise () {
      enterprise.getAll({
        types: '2,3'
      }).then((res) => {
        this.enterprises = res.data.enterprises
      })
    }
  },
  filters: {
  },
  computed: {},
  // 实例创建完成
  mounted () {
  }
}
</script>
<style lang="less">
#drawroom-page {
  .drawroom-cont{
    .el-table__body .el-table__row {
      td:nth-child(2),td:nth-child(3) ,td:nth-child(4) {
        div.cell {
          padding:0;
        }
        p {
          margin: 0;
          padding: 10px;
        }
        p:not(:last-child) {
          border-bottom: 1px solid #eee;
        }
      }
    }
  }
  .block-page {
    padding-top: 20px;
  }
  .draw-uncomplete {
    color: #FBAA3F;
  }
  .draw-complete {
    color: #76C687;
  }
}
</style>
