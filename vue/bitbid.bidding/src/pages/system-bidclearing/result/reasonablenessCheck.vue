<template>
  <div id="threeresult" class="threeresult" v-loading="checkLoading">
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
              <img src="../../../../static/images/system-clearbid/jiantou.png" alt="" class="jiantouimg">
            </span>
              <div class="layoutbtn" @click="logout">退出</div>
            </div>
          </div>
        </div>
      </div>
    </el-row>
    <el-row>
      <el-col :span="3" width="300px" class="setheight">
        <el-menu
          class="el-menu-vertical-demo sidebox"
          background-color="#20335d"
          text-color="#cccccc">
          <el-menu-item index="1" class="indexs" @click="leftMenu1">
            <template slot="title">
              <span slot="title" class="titlesbox" :title="'清标结果'">清标结果</span>
            </template>
          </el-menu-item>
          <el-menu-item class="submenu" index="1-1" @click="leftMenu2">符合性检查</el-menu-item>
          <el-menu-item class="submenu" index="1-2" @click="leftMenu3">算术性检查</el-menu-item>
          <el-menu-item class="submenu active" index="1-3" @click="leftMenu4">合理性检查</el-menu-item>
        </el-menu>
      </el-col>
      <el-col :span="21" class="setheight">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="标书信息检查" name="first">
            <div class="toplist">
              <el-table
                :data="tabFirstData"
                ref="tabFirstData"
                highlight-current-row
                height="165"
                style="width: 100%"
                border
                header-cell-class-name="tabletitles">
                <el-table-column
                  type="index"
                  label="序号"
                  width="50"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="投标单位"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="totalPrice"
                  label="投标报价（元）"
                  width="300">
                </el-table-column>
                <el-table-column
                  prop="question"
                  label="是否合格"
                  width="160">
                  <template slot-scope="scope">
                    <span v-if="Number(scope.row.question) === 0">是</span>
                    <span v-if="Number(scope.row.question) > 0">否</span>
                    <span v-if="Number(scope.row.question) === -1">无法检查</span>
                  </template>
                </el-table-column>
                <el-table-column
                  label="操作" width="100" align="center">
                  <template slot-scope="scope" v-if="Number(scope.row.question) !== -1">
                    <el-button type="text" size="small" @click="_getCheckResult(scope, 17)">
                      查看
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div class="bottomresult">
              <p><span>检查规则</span>     投标文件两两对比，检查制作标书电脑的硬件信息是否有相同的情况。</p>
              <div class="checkresult">
                <span>
                  检查结果分析
                </span>
                <p><i>{{tabFirstTender}}</i>{{tabFirstCheckInfo}}</p>
              </div>
              <el-table
                :data="tabFirstResult"
                style="width: 100%"
                border
                class="boldname"
                header-cell-class-name="tabletitles">
                <el-table-column
                  prop="map.tender"
                  label="投标单位">
                </el-table-column>
                <el-table-column
                  prop="map.cpuListNumber "
                  label="芯片序列号"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.hardDiskListNumber"
                  label="硬盘序列号"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.networkCardListNumber"
                  label="网卡序列号"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.networkIp"
                  label="网络IP"
                  show-overflow-tooltip>
                </el-table-column>
              </el-table>
            </div>
          </el-tab-pane>
          <el-tab-pane label="不平衡报价检查" name="second">
            <div class="toplist">
              <el-table
                :data="tabSecondData"
                ref="tabSecondData"
                highlight-current-row
                height="165"
                style="width: 100%"
                border
                header-cell-class-name="tabletitles">
                <el-table-column
                  type="index"
                  label="序号"
                  width="50"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="投标单位"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="totalPrice"
                  label="投标报价（元）"
                  width="300">
                </el-table-column>
                <el-table-column
                  prop="question"
                  label="是否合格"
                  width="160">
                  <template slot-scope="scope">
                    <span v-if="Number(scope.row.question) === 0">是</span>
                    <span v-if="Number(scope.row.question) > 0">否</span>
                    <span v-if="Number(scope.row.question) === -1">无法检查</span>
                  </template>
                </el-table-column>
                <el-table-column
                  label="操作" width="100" align="center">
                  <template slot-scope="scope" v-if="Number(scope.row.question) !== -1">
                    <el-button type="text" size="small" @click="_getCheckResult(scope, 18)">
                      查看
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div class="bottomresult">
              <p><span>检查规则</span>     检查清单单价与基准值偏差率较高且合价占投标总价比重过大的清单。</p>
              <div class="selectpart">
                <div class="secsion">
                  <span>|偏差率| ≥</span>
                  <el-input-number  v-model="deviation" controls-position="right" :min="1" :max="100" size="mini" @change="handleChange(deviation, 1)"></el-input-number>
                  <i>%</i>
                </div>
                <div class="secsion">
                  <span>不平衡报价占总价百分比 ≥</span>
                  <el-input-number  v-model="unbalanProportion" controls-position="right" :min="1" :max="100" size="mini" @change="handleChange(unbalanProportion, 2)"></el-input-number>
                  <i>%</i>
                </div>
                <div class="secsion">
                  <span>基准值： </span>
                  <el-select v-model="referenceValue" @change="unbalanTender">
                    <el-option
                      v-for="(item,index) in referenceValueoptions"
                      :key="index"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </div>
                <div class="secsion" v-if="this.referenceValue === 5">
                  <span>投标单位： </span>
                  <el-select v-model="tenderUnit">
                    <el-option
                      v-for="(item,index) in tenderUnitOptions"
                      :key="index"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </div>
                <div class="secsion">
                  <el-button type="primary" @click="unbalanCheck()" :disabled="!unbanlaTrue">检查分析</el-button>
                </div>
              </div>
              <div class="checkresult">
                <span>
                  检查结果分析
                </span>
                <p v-if="unbanlaTrue">{{tabSecondTender}}：  不平衡清单数量：<em>{{unbalanceNum}}</em> &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;不平衡清单报价：<em>{{unbalanceOffer}}</em>   &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;    不平衡报价占总价百分比：<em>{{proportion}}% </em></p>
                <p v-else>只上传一家投标单位，无法检查。</p>
              </div>
              <el-table
                :data="tabSecondResult"
                style="width: 100%"
                border
                header-cell-class-name="tabletitles">
                <el-table-column
                  type="index"
                  label="序号"
                  width="50"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="singleProject"
                  label="单项工程"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="unitProject"
                  label="单位工程"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="location"
                  label="位置"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="code"
                  label="清单编码"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="清单名称"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="unit"
                  label="清单单位"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="workAmount"
                  label="清单工程量"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.price"
                  label="单价"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.totalPrice"
                  label="合价"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.deviation"
                  label="偏差率"
                  show-overflow-tooltip>
                </el-table-column>
              </el-table>
            </div>
          </el-tab-pane>
          <el-tab-pane label="雷同性检查" name="third">
            <div class="toplist">
              <el-table
                :data="tabThirdData"
                ref="tabThirdData"
                highlight-current-row
                height="165"
                style="width: 100%"
                border
                header-cell-class-name="tabletitles">
                <el-table-column
                  type="index"
                  label="序号"
                  width="50"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="投标单位"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="totalPrice"
                  label="投标报价（元）"
                  width="300">
                </el-table-column>
                <el-table-column
                  prop="question"
                  label="是否合格"
                  width="160">
                  <template slot-scope="scope">
                    <span v-if="Number(scope.row.question) === 0">是</span>
                    <span v-if="Number(scope.row.question) > 0">否</span>
                    <span v-if="Number(scope.row.question) === -1">无法检查</span>
                  </template>
                </el-table-column>
                <el-table-column
                  label="操作" width="100" align="center">
                  <template slot-scope="scope" v-if="Number(scope.row.question) !== -1">
                    <el-button type="text" size="small" @click="_getCheckResult(scope, 19)">
                      查看
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div class="bottomresult">
              <p><span>检查规则</span>     检查两份投标文件之间报价数据存在大量雷同或成规律性变化的情况。</p>
              <div class="selectpart">
                <div class="secsion">
                  <span>雷同清单数占总清单数量百分比  ≥</span>
                  <el-input-number  v-model="judgeAmountPercent" controls-position="right" :min="1" :max="100" size="mini" @change="handleChange(judgeAmountPercent, 3)" ></el-input-number>
                  <i>%</i>
                </div>
                <div class="secsion">
                  <span>雷同清单合价占投标总价百分比 ≥</span>
                  <el-input-number  v-model="judgeTotalPricePercent" controls-position="right" :min="1" :max="100" size="mini" @change="handleChange(judgeTotalPricePercent, 4)"></el-input-number>
                  <i>%</i>
                </div>
                <div class="secsion">
                  <span>分析内容： </span>
                  <el-select v-model="similarityType">
                    <el-option
                      v-for="(item,index) in simTypeOption"
                      :key="index"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </div>
                <div class="secsion">
                  <el-button type="primary" @click="sameCheck" :disabled="!sameTrue">检查分析</el-button>
                </div>
              </div>
              <div class="checkresult samecheckresult">
                <span>
                  检查结果分析
                </span>
                <div class="samebox" v-if="sameTrue">
                  <span class="tendertitle" :title="tabThirdTender">投标单位：{{tabThirdTender}}  </span>
                  <div class="sameunit" v-if="isSame">
                    <el-collapse>
                      <el-collapse-item name="1">
                        <template slot="title">
                          雷同清单投标单位  <font class="lookdetail">点击查看</font>
                        </template>
                        <ul>
                          <li v-for="(item, index) in enters" :key= "index">
                            <span class="tendername" :title="item.name">{{item.name}}</span>
                            <span>雷同清单数量：<i>{{item.number}}</i></span>
                            <span>雷同清单合价占投标总价百分比：<i>{{(item.percent * 100).toFixed(2)}}%</i></span>
                          </li>
                        </ul>
                      </el-collapse-item>
                    </el-collapse>
                  </div>
                  <div v-else>
                    <p>不存在雷同清单投标单位 </p>
                  </div>
                </div>
                <div class="samebox" v-else>
                  <p>只上传一家投标单位，无法检查。  </p>
                </div>
              </div>
              <div class="selectpart" v-if="sameTrue && isSame">
                <div class="secsion">
                  <span class="unitname">{{tabThirdTender}} （A）</span>
                  <span>存在雷同清单投标单位： </span>
                  <el-select v-model="bValue" @change = "sameTenderChange">
                    <el-option
                      v-for="(item,index ) in bOptions"
                      :key="index"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </div>
              </div>
              <p style="color: #666666;" v-if="sameTrue && isSame">{{tabThirdTender}} ：雷同清单数占总清单数量百分比：{{(tabThirdAmount!=null?tabThirdAmount*100:0).toFixed(2)}}%  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 雷同清单合价占投标总价百分比：{{(tabThirdPrice!=null?tabThirdPrice*100:0).toFixed(2)}}%</p>
              <el-table
                :data="tabThirdResult"
                style="width: 100%"
                border
                header-cell-class-name="tabletitles">
                <el-table-column
                  type="index"
                  label="序号"
                  width="50"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="singleProject"
                  label="单项工程"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="unitProject"
                  label="单位工程"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="location"
                  label="位置"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="code"
                  label="清单编码"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="清单名称"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.curPrice"
                  label="A单价"
                  show-overflow-tooltip
                  v-if="sameCheckTip === 1"
                  :key="Math.random()">
                </el-table-column>
                <el-table-column
                  prop="map.otherPrice"
                  label="B单价"
                  show-overflow-tooltip
                  v-if="sameCheckTip === 1"
                  :key="Math.random()">
                </el-table-column>
                <el-table-column
                  prop="map.specificValue"
                  label="A单价÷B单价(百分比)"
                  show-overflow-tooltip
                  v-if="sameCheckTip === 1"
                  :key="Math.random()">
                  <template slot-scope="scope">
                    <span>{{(scope.row.map.specificValue * 100).toFixed(2)}}%</span>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="map.curPrice"
                  label="A人工费"
                  show-overflow-tooltip
                  v-if="sameCheckTip === 2"
                  :key="Math.random()">
                </el-table-column>
                <el-table-column
                  prop="map.otherPrice"
                  label="B人工费"
                  show-overflow-tooltip
                  v-if="sameCheckTip === 2"
                  :key="Math.random()">
                </el-table-column>
                <el-table-column
                  prop="map.specificValue"
                  label="A人工费÷B人工费(百分比)"
                  show-overflow-tooltip
                  v-if="sameCheckTip === 2"
                  :key="Math.random()">
                  <template slot-scope="scope">
                    <span>{{(scope.row.map.specificValue * 100).toFixed(2)}}%</span>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="map.curPrice"
                  label="A机械费"
                  show-overflow-tooltip
                  v-if="sameCheckTip === 3"
                  :key="Math.random()">
                </el-table-column>
                <el-table-column
                  prop="map.otherPrice"
                  label="B机械费"
                  show-overflow-tooltip
                  v-if="sameCheckTip === 3"
                  :key="Math.random()">
                </el-table-column>
                <el-table-column
                  prop="map.specificValue"
                  label="A机械费÷B机械费(百分比)"
                  show-overflow-tooltip
                  v-if="sameCheckTip === 3"
                  :key="Math.random()">
                  <template slot-scope="scope">
                    <span>{{(scope.row.map.specificValue * 100).toFixed(2)}}%</span>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="map.curPrice"
                  label="A材料费"
                  show-overflow-tooltip
                  v-if="sameCheckTip === 4"
                  :key="Math.random()">
                </el-table-column>
                <el-table-column
                  prop="map.otherPrice"
                  label="B材料费"
                  show-overflow-tooltip
                  v-if="sameCheckTip === 4"
                  :key="Math.random()">
                </el-table-column>
                <el-table-column
                  prop="map.specificValue"
                  label="A材料费÷B材料费(百分比)"
                  show-overflow-tooltip
                  v-if="sameCheckTip === 4"
                  :key="Math.random()">
                  <template slot-scope="scope">
                    <span>{{(scope.row.map.specificValue * 100).toFixed(2)}}%</span>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="map.curPrice"
                  label="A企业管理费"
                  show-overflow-tooltip
                  v-if="sameCheckTip === 5"
                  :key="Math.random()">
                </el-table-column>
                <el-table-column
                  prop="map.otherPrice"
                  label="B企业管理费"
                  show-overflow-tooltip
                  v-if="sameCheckTip === 5"
                  :key="Math.random()">
                </el-table-column>
                <el-table-column
                  prop="map.specificValue"
                  label="A企业管理费÷B企业管理费(百分比)"
                  show-overflow-tooltip
                  v-if="sameCheckTip === 5"
                  :key="Math.random()">
                  <template slot-scope="scope">
                    <span>{{(scope.row.map.specificValue * 100).toFixed(2)}}%</span>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="map.curPrice"
                  label="A清单利润"
                  show-overflow-tooltip
                  v-if="sameCheckTip === 6"
                  :key="Math.random()">
                </el-table-column>
                <el-table-column
                  prop="map.otherPrice"
                  label="B清单利润"
                  show-overflow-tooltip
                  v-if="sameCheckTip === 6"
                  :key="Math.random()">
                </el-table-column>
                <el-table-column
                  prop="map.specificValue"
                  label="A清单利润÷B清单利润(百分比)"
                  show-overflow-tooltip
                  v-if="sameCheckTip === 6"
                  :key="Math.random()">
                  <template slot-scope="scope">
                    <span>{{(scope.row.map.specificValue * 100).toFixed(2)}}%</span>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-tab-pane>
          <el-tab-pane label="招标控制价检查" name="fourth">
            <div class="toplist">
              <el-table
                :data="tabFourthData"
                ref="tabFourthData"
                highlight-current-row
                height="165"
                style="width: 100%"
                border
                header-cell-class-name="tabletitles">
                <el-table-column
                  type="index"
                  label="序号"
                  width="50"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="投标单位"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="totalPrice"
                  label="投标报价（元）"
                  width="300">
                </el-table-column>
                <el-table-column
                  prop="question"
                  label="是否合格"
                  width="160">
                  <template slot-scope="scope">
                    <span v-if="Number(scope.row.question) === 0">是</span>
                    <span v-if="Number(scope.row.question) > 0">否</span>
                    <span v-if="Number(scope.row.question) === -1">无法检查</span>
                  </template>
                </el-table-column>
                <el-table-column
                  label="操作" width="100" align="center">
                  <template slot-scope="scope" v-if="Number(scope.row.question) !== -1">
                    <el-button type="text" size="small" @click="_getCheckResult(scope, 20)">
                      查看
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div class="bottomresult">
              <p><span>检查规则</span>     检查投标报价是否高于招标控制价。</p>
              <div class="checkresult">
                <span>
                  检查结果分析
                </span>
                <p><i>{{tabFourthTender}}</i> {{tabFourthCheckInfo}}</p>
              </div>
              <el-table
                :data="tabFourthResult"
                style="width: 100%"
                border
                header-cell-class-name="tabletitles">
                <el-table-column
                  type="index"
                  label="序号"
                  width="50"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="map.controlPrice"
                  label="招标控制价"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.bidderPrice"
                  label="投标报价"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="question"
                  label="问题描述"
                  show-overflow-tooltip>
                </el-table-column>
              </el-table>
            </div>
          </el-tab-pane>
          <el-tab-pane label="低于成本检查" name="fifth">
            <div class="toplist">
              <el-table
                :data="tabFifthData"
                ref="tabFifthData"
                highlight-current-row
                height="165"
                style="width: 100%"
                border
                header-cell-class-name="tabletitles">
                <el-table-column
                  type="index"
                  label="序号"
                  width="50"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="投标单位"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="totalPrice"
                  label="投标报价（元）"
                  width="300">
                </el-table-column>
                <el-table-column
                  prop="question"
                  label="是否合格"
                  width="160">
                  <template slot-scope="scope">
                    <span v-if="Number(scope.row.question) === 0">是</span>
                    <span v-if="Number(scope.row.question) > 0">否</span>
                    <span v-if="Number(scope.row.question) === -1">无法检查</span>
                  </template>
                </el-table-column>
                <el-table-column
                  label="操作" width="100" align="center">
                  <template slot-scope="scope" v-if="Number(scope.row.question) !== -1">
                    <el-button type="text" size="small" @click="_getCheckResult(scope, 21)">
                      查看
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div class="bottomresult">
              <p><span>检查规则</span>     1.检查投标报价是否低于招标控制价的85%，投标分部分项费用合计是否低于招标控制价分部分项费用的85%，投标措施项目费用合计是否低于招标控制价措施项目费用的70%
                                           <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.检查M是否为负值，|M|是否大于投标报价利润 。</p>
              <div class="checkresult">
                <span>
                  检查结果分析
                </span>
                <p><i>{{tabFifthTender}}</i>{{tabFifthCheckInfo}}</p>
              </div>
              <el-table
                :data="tabFifthResult"
                style="width: 100%"
                border
                header-cell-class-name="tabletitles">
                <el-table-column
                  type="index"
                  label="序号"
                  width="50"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="map.priceCompare"
                  label="投标报价/招标控制价（%）"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.partialCompare"
                  label="投标分部分项费用/招标控制价分部分项费用（%）"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.measureCompare"
                  label="投标措施项目费用/招标控制价措施项目费用（%）"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.isCostReviews"
                  label="是否启动成本评审"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.mvalue"
                  label="M值"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.bidderProfit"
                  label="投标报价利润"
                  show-overflow-tooltip>
                  <template slot-scope="scope">
                    <span>{{scope.row.map.bidderProfit != null && scope.row.map.bidderProfit != '' ? Number(scope.row.map.bidderProfit).toFixed(2):null}}</span>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="question"
                  label="问题描述"
                  show-overflow-tooltip>
                </el-table-column>
              </el-table>
            </div>
          </el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { billClearData } from '@/api'
export default {
  data () {
    return {
      tabFirstData: [],
      tabFirstResult: [],
      tabSecondData: [],
      tabSecondResult: [],
      tabThirdData: [],
      tabThirdResult: [],
      tabFourthData: [],
      tabFourthResult: [],
      tabFifthData: [],
      tabFifthResult: [],
      tableData: [],
      activeName: 'first',
      tabFirstTender: '',
      tabFirstCheckInfo: '',
      tabFourthTender: '',
      tabFourthCheckInfo: '',
      tabFifthTender: '',
      tabFifthCheckInfo: '',
      // 不平衡报价检查
      unbalanId: '',
      unbanlaTrue: true,
      tabSecondTender: '',
      referenceValueoptions: [{
        value: 1,
        label: '清标中的基准值'
      }, {
        value: 2,
        label: '平均值'
      }, {
        value: 3,
        label: '最低值'
      }, {
        value: 4,
        label: '次低值'
      }, {
        value: 5,
        label: '指定值'
      }, {
        value: 6,
        label: '控制价'
      }],
      referenceValue: 1,
      deviation: 30,
      unbalanProportion: 50,
      proportion: '',
      unbalanceNum: '',
      unbalanceOffer: '',
      tenderUnitOptions: [],
      tenderUnit: '',
      // 雷同性检查
      tabThirdTender: '',
      sameId: '',
      sameTrue: true,
      judgeAmountPercent: 15,
      judgeTotalPricePercent: 10,
      simTypeOption: [{
        value: 1,
        label: '清单单价'
      }, {
        value: 2,
        label: '清单人工费'
      }, {
        value: 3,
        label: '清单机械费'
      }, {
        value: 4,
        label: '清单材料费'
      }, {
        value: 5,
        label: '清单企业管理费'
      }, {
        value: 6,
        label: '清单利润'
      }],
      similarityType: 1,
      enters: [],
      bOptions: [],
      bValue: '',
      tabThirdAmount: '',
      tabThirdPrice: '',
      isSame: true,
      sameCheckTip: 1,
      checkLoading: false
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
      let rightH = document.getElementsByClassName('bottomresult')

      // 设置主要内容区的高度
      topUl[0].style.height = (screenH - 40) + 'px'
      rightH[0].style.height = (screenH - 380) + 'px'
      rightH[1].style.height = (screenH - 380) + 'px'
      rightH[2].style.height = (screenH - 380) + 'px'
      rightH[3].style.height = (screenH - 380) + 'px'
      rightH[4].style.height = (screenH - 380) + 'px'
    },
    // 左侧菜单
    leftMenu1 () {
      this.$router.push({path: `/system-bidclearing/result`, query: {objectId: this.$route.query.objectId}})
    },
    leftMenu2 () {
      this.$router.push({path: `/system-bidclearing/result/complianceCheck`, query: {objectId: this.$route.query.objectId}})
    },
    leftMenu3 () {
      this.$router.push({path: `/system-bidclearing/result/arithmeticCheck`, query: {objectId: this.$route.query.objectId}})
    },
    leftMenu4 () {
      this.$router.push({path: `/system-bidclearing/result/reasonablenessCheck`, query: {objectId: this.$route.query.objectId}})
    },
    handleClick (tab) {
      switch (Number(tab.index)) {
        case 0:
          this._getCheckData(17)
          break
        case 1:
          this._getCheckData(18)
          break
        case 2:
          this._getCheckData(19)
          break
        case 3:
          this._getCheckData(20)
          break
        case 4:
          this._getCheckData(21)
          break
      }
    },
    // 检查投标单位列表
    _getCheckData (index) {
      billClearData.unitList(this.$route.query.objectId, index).then((res) => {
        switch (Number(index)) {
          case 17:
            this.tabFirstData = []
            this.tabFirstData = res.data.enters
            if (Number(this.tabFirstData.question) !== -1) {
              this._getDefaultResult(this.tabFirstData[0].objectId, 17)
            }
            break
          case 18:
            this.tabSecondData = []
            this.tabSecondData = res.data.enters
            this._getDefaultResult(this.tabSecondData[0].objectId, 18)
            break
          case 19:
            this.tabThirdData = []
            this.tabThirdData = res.data.enters
            this._getDefaultResult(this.tabThirdData[0].objectId, 19)
            break
          case 20:
            this.tabFourthData = []
            this.tabFourthData = res.data.enters
            if (Number(this.tabFourthData.question) !== -1) {
              this._getDefaultResult(this.tabFourthData[0].objectId, 20)
            }
            break
          case 21:
            this.tabFifthData = []
            this.tabFifthData = res.data.enters
            if (Number(this.tabFifthData.question) !== -1) {
              this._getDefaultResult(this.tabFifthData[0].objectId, 21)
            }
            break
        }
      })
    },
    // 投标单位点击 查看检查结果
    _getCheckResult (scope, index) {
      if (Number(index) === 18) {
        this.tabSecondTender = scope.row.name
        this.unbalanId = scope.row.objectId
        let projectId = ''
        if (this.referenceValue === 5) {
          projectId = this.tenderUnit
        }
        billClearData.unbalanceCheckItem(this.$route.query.objectId, index, scope.row.objectId, this.deviation / 100, this.unbalanProportion / 100, this.referenceValue, projectId).then((res) => {
          this.tabSecondResult = []
          this.tabSecondResult = res.data.models
          this.proportion = (res.data.proportion * 100).toFixed(2)
          this.unbalanceNum = res.data.unbalanceNum
          this.unbalanceOffer = res.data.unbalanceOffer
        })
      } else if (Number(index) === 19) {
        this.bOptions = []
        this.tabThirdTender = scope.row.name
        this.sameId = scope.row.objectId
        billClearData.sameCheckItem(this.$route.query.objectId, this.sameId, this.similarityType, this.judgeAmountPercent / 100, this.judgeTotalPricePercent / 100).then((res) => {
          this.enters = res.data.enters
          if (this.enters !== null && this.enters.length !== 0) {
            this.isSame = true
            this.bOptions = []
            for (let i = 0; i < this.enters.length; i++) {
              let obj = {
                value: this.enters[i].objectId,
                label: this.enters[i].name + ' (B)',
                key: this.enters[i].objectId
              }
              this.bOptions.push(obj)
            }
            this.bValue = this.bOptions[0].value
            billClearData.sameTenderCheck(this.$route.query.objectId, this.sameId, this.bValue).then((res) => {
              this.tabThirdResult = []
              this.tabThirdResult = res.data.models
              this.tabThirdAmount = res.data.amount
              this.tabThirdPrice = res.data.price
            })
          } else {
            this.isSame = false
            this.bOptions = []
            this.bValue = ''
            this.tabThirdResult = []
            this.tabThirdAmount = 0
            this.tabThirdPrice = 0
          }
        })
      } else {
        billClearData.checkResult(this.$route.query.objectId, index, scope.row.objectId).then((res) => {
          if (res.data.models !== null && res.data.models.length > 0) {
            switch (Number(index)) {
              case 17:
                this.tabFirstTender = scope.row.name
                this.tabFirstResult = res.data.models !== null ? res.data.models : []
                this.tabFirstCheckInfo = ''
                for (let i = 1; i < this.tabFirstResult.length; i++) {
                  this.tabFirstCheckInfo += this.tabFirstResult[i].map.tender + '、'
                }
                this.tabFirstCheckInfo = this.tabFirstCheckInfo.substring(0, this.tabFirstCheckInfo.length - 1)
                this.tabFirstCheckInfo = '与' + this.tabFirstCheckInfo + '标书信息一致'
                break
              case 20:
                this.tabFourthTender = scope.row.name
                this.tabFourthCheckInfo = ''
                this.tabFourthCheckInfo = '投标报价高于招标控制价'
                this.tabFourthResult = res.data.models
                break
              case 21:
                this.tabFifthTender = scope.row.name
                this.tabFifthResult = res.data.models
                if (this.tabFifthResult[0].map.isCostReviews === '是') {
                  this.tabFifthCheckInfo = '启动理论成本评审，经检查分析判定为' + this.tabFifthResult[0].question
                } else {
                  this.tabFifthCheckInfo = '未启动理论成本评审'
                }
                break
            }
          } else {
            switch (Number(index)) {
              case 17:
                this.tabFirstTender = scope.row.name
                this.tabFirstResult = []
                this.tabFirstCheckInfo = '标书信息检查合格'
                break
              case 20:
                this.tabFourthTender = scope.row.name
                this.tabFourthCheckInfo = '招标控制价检查合格'
                this.tabFourthResult = res.data.models
                break
              case 21:
                this.tabFifthTender = scope.row.name
                this.tabFifthResult = res.data.models
                if (this.tabFifthResult[0].map.isCostReviews === '是') {
                  this.tabFifthCheckInfo = '启动理论成本评审，经检查分析判定为' + this.tabFifthResult[0].question
                } else {
                  this.tabFifthCheckInfo = '未启动理论成本评审'
                }
                break
            }
          }
        })
      }
    },
    // 取投标单位列表 默认数据
    _getDefaultResult (id, index) {
      this.sameCheckTip = 1
      if (Number(index) === 18) {
        if (Number(this.tabSecondData[0].question) !== -1) {
          this.tabSecondTender = this.tabSecondData[0].name
          this.unbalanId = id
          this.tenderUnitOptions = []
          for (let i = 0; i < this.tabSecondData.length; i++) {
            let obj = {
              value: this.tabSecondData[i].objectId,
              label: this.tabSecondData[i].name
            }
            this.tenderUnitOptions.push(obj)
          }
          let projectId = ''
          if (this.referenceValue === 5) {
            projectId = this.tenderUnit
          }
          if (projectId !== undefined) {
            billClearData.unbalanceCheckItem(this.$route.query.objectId, index, id, '', '', '', projectId).then((res) => {
              this.$refs.tabSecondData.setCurrentRow(this.$refs.tabSecondData.data[0])
              this.tabSecondResult = []
              this.tabSecondResult = res.data.models
              this.proportion = (res.data.proportion * 100).toFixed(2)
              this.unbalanceNum = res.data.unbalanceNum
              this.unbalanceOffer = res.data.unbalanceOffer
              this.deviation = (res.data.deviation * 100).toFixed(0)
              this.unbalanProportion = (res.data.unbalanProportion * 100).toFixed(0)
              this.referenceValue = Number(res.data.referenceValue)
              this.tenderUnit = res.data.projectId
            })
          }
        } else {
          this.unbanlaTrue = false
        }
      } else if (Number(index) === 19) {
        if (Number(this.tabThirdData[0].question) !== -1) {
          this.tabThirdTender = this.tabThirdData[0].name
          this.sameId = this.tabThirdData[0].objectId
          billClearData.sameCheckItem(this.$route.query.objectId, this.tabThirdData[0].objectId, '', '', '').then((res) => {
            this.judgeAmountPercent = (res.data.judgeAmountPercent * 100).toFixed(0)
            this.judgeTotalPricePercent = (res.data.judgeTotalPricePercent * 100).toFixed(0)
            this.similarityType = Number(res.data.similarityType)
            this.sameCheckTip = this.similarityType
            this.$refs.tabThirdData.setCurrentRow(this.$refs.tabThirdData.data[0])
            this.bOptions = []
            this.enters = res.data.enters
            if (this.enters !== null && this.enters.length !== 0) {
              this.isSame = true
              for (let i = 0; i < this.enters.length; i++) {
                let obj = {
                  value: this.enters[i].objectId,
                  label: this.enters[i].name + ' (B)',
                  key: this.enters[i].objectId
                }
                this.bOptions.push(obj)
              }
              this.bValue = this.bOptions[0].value
              billClearData.sameTenderCheck(this.$route.query.objectId, this.tabThirdData[0].objectId, this.bValue).then((res) => {
                this.tabThirdResult = []
                this.tabThirdResult = res.data.models
                this.tabThirdAmount = res.data.amount
                this.tabThirdPrice = res.data.price
              })
            } else {
              this.isSame = false
              this.bOptions = []
              this.bValue = ''
              this.tabThirdResult = []
              this.tabThirdAmount = 0
              this.tabThirdPrice = 0
            }
          })
        } else {
          this.sameTrue = false
        }
      } else {
        billClearData.checkResult(this.$route.query.objectId, index, id).then((res) => {
          switch (Number(index)) {
            case 17:
              this.$refs.tabFirstData.setCurrentRow(this.$refs.tabFirstData.data[0])
              if (Number(this.tabFirstData[0].question) !== -1) {
                this.tabFirstTender = this.tabFirstData[0].name
                this.tabFirstResult = res.data.models !== null ? res.data.models : []
                if (this.tabFirstResult.length === 0 || this.tabFirstResult === null) {
                  this.tabFirstCheckInfo = '标书信息检查合格'
                } else {
                  this.tabFirstCheckInfo = ''
                  for (let i = 1; i < this.tabFirstResult.length; i++) {
                    this.tabFirstCheckInfo += this.tabFirstResult[i].map.tender + '、'
                  }
                  this.tabFirstCheckInfo = this.tabFirstCheckInfo.substring(0, this.tabFirstCheckInfo.length - 1)
                  this.tabFirstCheckInfo = '与' + this.tabFirstCheckInfo + '标书信息一致'
                }
              } else {
                this.tabFirstTender = ''
                this.tabFirstCheckInfo = '只上传一家投标单位，无法检查'
                this.tabFirstResult = []
              }
              break
            case 20:
              this.$refs.tabFourthData.setCurrentRow(this.$refs.tabFourthData.data[0])
              if (Number(this.tabFourthData[0].question) !== -1) {
                this.tabFourthTender = this.tabFourthData[0].name
                this.tabFourthResult = res.data.models
                if (this.tabFourthResult.length === 0 || this.tabFourthResult === null) {
                  this.tabFourthCheckInfo = '招标控制价检查合格'
                } else {
                  this.tabFourthCheckInfo = '投标报价高于招标控制价'
                }
              } else {
                this.tabFourthTender = ''
                this.tabFourthCheckInfo = '获取不到招标控制价信息，无法检查'
                this.tabFourthResult = []
              }
              break
            case 21:
              this.$refs.tabFifthData.setCurrentRow(this.$refs.tabFifthData.data[0])
              if (Number(this.tabFifthData[0].question) !== -1) {
                this.tabFifthTender = this.tabFifthData[0].name
                this.tabFifthResult = res.data.models
                if (this.tabFifthResult[0].map.isCostReviews === '是') {
                  this.tabFifthCheckInfo = '启动理论成本评审，经检查分析判定为' + this.tabFifthResult[0].question
                } else {
                  this.tabFifthCheckInfo = '未启动理论成本评审'
                }
              } else {
                this.tabFifthTender = []
                this.tabFifthCheckInfo = ' 没有上传招标控制价文件，无法检查'
                this.tabFifthResult = []
              }
              break
          }
        })
      }
    },
    // 不平衡性检查分析
    unbalanCheck () {
      this.checkLoading = true
      let id = this.unbalanId
      let projectId = ''
      if (this.referenceValue === 5) {
        projectId = this.tenderUnit
      }
      billClearData.unbalanceCheckItem(this.$route.query.objectId, 18, id, this.deviation / 100, this.unbalanProportion / 100, this.referenceValue, projectId).then((res) => {
        if (res.data.resCode === '0000') {
          this.checkLoading = false
          this.$message({
            message: '检查完成',
            type: 'success'
          })
          this.tabSecondResult = []
          this.tabSecondResult = res.data.models
          this.proportion = (res.data.proportion * 100).toFixed(2)
          this.unbalanceNum = res.data.unbalanceNum
          this.unbalanceOffer = res.data.unbalanceOffer
          billClearData.unitList(this.$route.query.objectId, 18).then((res) => {
            this.tabSecondData = []
            this.tabSecondData = res.data.enters
          })
        } else {
          this.checkLoading = false
          this.tabSecondResult = []
          this.proportion = 0
          this.unbalanceNum = 0
          this.unbalanceOffer = 0
        }
      })
    },
    // 不平衡性检查分析 选择指定值，默认第一家投标单位
    unbalanTender (val) {
      if (val === 5) {
        this.tenderUnit = this.tenderUnitOptions[0].value
      }
    },
    // 雷同性检查分析
    sameCheck () {
      this.checkLoading = true
      let id = this.sameId
      if (id) {
        billClearData.sameCheckItem(this.$route.query.objectId, id, this.similarityType, this.judgeAmountPercent / 100, this.judgeTotalPricePercent / 100).then((res) => {
          this.bOptions = []
          this.enters = res.data.enters
          billClearData.unitList(this.$route.query.objectId, 19).then((res) => {
            this.tabThirdData = []
            this.tabThirdData = res.data.enters
          })
          if (this.enters !== null && this.enters.length !== 0) {
            this.isSame = true
            for (let i = 0; i < this.enters.length; i++) {
              let obj = {
                value: this.enters[i].objectId,
                label: this.enters[i].name + ' (B)',
                key: this.enters[i].objectId
              }
              this.bOptions.push(obj)
            }
            this.bValue = this.bOptions[0].value
            this.sameCheckTip = 1
            this.sameCheckTip = Number(this.similarityType)
            billClearData.sameTenderCheck(this.$route.query.objectId, id, this.bValue).then((res) => {
              if (res.data.resCode === '0000') {
                this.$message({
                  message: '检查完成',
                  type: 'success'
                })
                this.checkLoading = false
                this.tabThirdResult = []
                this.tabThirdResult = res.data.models
                this.tabThirdAmount = res.data.amount
                this.tabThirdPrice = res.data.price
              } else {
                this.tabThirdResult = []
                this.tabThirdAmount = 0
                this.tabThirdPrice = 0
              }
            })
          } else {
            this.checkLoading = false
            this.isSame = false
            this.bOptions = []
            this.bValue = ''
            this.tabThirdResult = []
            this.tabThirdAmount = 0
            this.tabThirdPrice = 0
          }
        })
      }
    },
    // 雷同性比对投标单位change
    sameTenderChange () {
      billClearData.sameTenderCheck(this.$route.query.objectId, this.sameId, this.bValue).then((res) => {
        this.tabThirdResult = res.data.models
        this.tabThirdAmount = res.data.amount
        this.tabThirdPrice = res.data.price
      })
    },
    numberCheck (data) {
      let num = data
      let re = /\D/g
      if (!re.test(num)) {
        this.$message({
          message: '请输入1-100的整数',
          type: 'warning'
        })
      }
    },
    // 小数点后保留两位数
    handleChange (val, index) {
      this.$nextTick(() => {
        let num = Math[val > 0 ? 'floor' : 'ceil'](val)
        switch (Number(index)) {
          case 1:
            this.deviation = val ? num : this.deviation
            break
          case 2:
            this.unbalanProportion = val ? num : this.unbalanProportion
            break
          case 3:
            this.judgeAmountPercent = val ? num : this.judgeAmountPercent
            break
          case 4:
            this.judgeTotalPricePercent = val ? num : this.judgeTotalPricePercent
            break
        }
      })
    }
  },
  watch: {},
  mounted () {
    this.setMain()
    this._getCheckData(17)
  }
}
</script>
<style lang="less">
  #threeresult{
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
    .el-col-3{
      background: #20335d;
    }
    .el-submenu__title{
      text-align: left;
      height: 50px;
      line-height: 50px;
    }
    .iconimg{
      display: inline-block;
      width: 15px;
      height: 17%;
      /*background: red;*/
      position: relative;
      vertical-align: middle;
    }
    .iconimg>img{
      width: 90%;
      position: absolute;
      left: 0;
      right: 0;
      top:0;
      bottom: 0;
      margin: auto;
    }
    .titlesbox{
      display: inline-block;
      width: 100%;
      padding: 0 5px;
      box-sizing: border-box;
      vertical-align: middle;
      line-height: 40px;
      background-image: url(../../../../static/images/system-clearbid/leftjiantou.png);
      background-position: right center;
      background-repeat: no-repeat;
    }
    .el-aside{
      background:#19233c;
    }
    .indexs{
      background: rgb(25, 35, 60) url(../../../../static/images/system-clearbid/leftico.png) 20px center no-repeat!important;
      padding-left: 40px!important;
    }
    .submenu{
      padding-left: 60px!important;
    }
    .el-menu-item.is-active{
      background: rgb(25, 35, 60)!important;
    }
    .rightcontent{
      margin: 20px 20px 0 20px;
      padding: 20px;
      border: 1px solid #cdcdcd;
      background: #ffffff;
    }
    .result_button{
      margin: 20px auto;
      text-align: center;
    }
    .active{
      background-color: rgb(26, 41, 74)!important;
    }
    .el-tabs{
      margin: 10px 20px;
    }
    .el-tabs__header{
      margin: 0px;
      padding-left: 20px;
      border: 1px solid #cdcdcd;
      background: #ffffff;
    }
    .el-tabs__item{
      padding: 0 40px;
    }
    // 顶部表格
    .toplist{
      padding: 20px;
      margin-top: 10px;
      border: 1px solid #cdcdcd;
      background: #ffffff;
    }
    .toplist .el-table{
      width: 70%!important;
    }
    .toplist table td{
      height: 26px;
      line-height: 26px;
      padding: 1px 0;
    }
    .bottomresult{
      padding: 20px;
      margin-top: 20px;
      overflow-y: auto;
      background: #ffffff;
      border: 1px solid #cdcdcd;
    }
    .bottomresult>p{
      color: #ff4200;
      margin-bottom: 15px;
      font-size: 14px;
    }
    .bottomresult p span{
      font-size: 16px;
      margin-right: 13px;
    }
    .bottomresult table td{
      height: 32px;
      line-height: 32px;
      padding: 7px 0;
    }
    .bottomresult .checkresult{
      padding: 10px 20px;
      max-height: 90px;
      overflow: hidden;
      background: #ebeffe;
      margin-bottom: 15px;
    }
    .bottomresult .checkresult span{
      display: inline-block;
      color: #333333;
      float: left;
      font-size: 16px;
      margin-right: 13px;
      line-height: 28px;
    }
    .bottomresult .checkresult p{
      display: inline-block;
      float: left;
      font-size: 14px;
      color: #666666;
      line-height: 28px;
    }
    .bottomresult .checkresult p i{
      font-weight: bold;
      font-style: normal;
    }
    .selectpart{
      margin-bottom: 15px;
      overflow: hidden;
    }
    .selectpart .secsion{
      display: inline-block;
      float: left;
      margin-right: 20px;
      margin-bottom: 10px;
      line-height: 31px;
    }
    .selectpart .secsion>span,.selectpart .secsion>i{
      line-height: 30px;
      font-size: 14px;
      color: #666666;
    }
    .selectpart .secsion > span.unitname{
      color: #3f63f6;
      font-size: 14px;
      margin-right: 30px;
      font-weight: bold;
    }
    .selectpart .secsion .el-input__inner{
      height: 30px;
      line-height: 30px;
    }
    .selectpart .secsion .el-button{
      padding: 7px 10px;
    }
    .checkresult p em{
      font-style: normal;
      color: #0036ff;
    }
    .bottomresult .samecheckresult{
      max-height: 5000px;
    }
    .checkresult .samebox{
      width: 80%;
      overflow: hidden;
      float: left;
      margin-left: 20px;
    }
    .checkresult .samebox span.tendertitle{
      display: inline-block;
      float: left;
      width: 17%;
      height: 30px;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
      font-size: 14px;
    }
    .checkresult .samebox .sameunit{
      width: 78%;
      float: left;
      margin-left: 2%;
      position: relative;
    }
    .checkresult .samebox .sameunit font.lookdetail{
      position: absolute;
      left: 130px;
      color: #409EFF;
      font-size: 14px;
      line-height: 28px;
      cursor: pointer;
    }
    .checkresult .samebox .sameunit .el-collapse-item__content{
      padding-bottom: 0px;
    }
    .checkresult .samebox .sameunit .el-collapse-item__header{
      height: 28px;
      line-height: 28px;
      padding-left: 10px;
    }
    .checkresult .samebox .sameunit .el-collapse-item__arrow{
      line-height: 28px;
    }
    .checkresult .samebox .sameunit ul{
      padding: 10px;
      overflow: hidden;
    }
    .checkresult .samebox .sameunit ul li{
      float: left;
      width: 100%;
    }
    .checkresult .samebox .sameunit ul li span{
      display: inline-block;
      font-size: 14px;
      margin-right: 20px;
      overflow: hidden;
    }
    .checkresult .samebox .sameunit ul li span.tendername{
      height: 28px;
      max-width: 30%;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      cursor: pointer;
    }
    .checkresult .samebox .sameunit ul li span i{
      font-style: normal;
      color: #0036ff;
    }
    .boldname .el-table__body .el-table__row:first-child td{
      font-weight: bold;
    }
    .el-table .cell{
      height: 23px;
    }
    .toplist table td{
      padding: 1px 0!important;
    }
    .toplist .el-table th{
      padding: 5px 0!important;
    }
    .el-button--small, .el-button--small.is-round{
      padding: 5px 0px;
    }
    .tabletitles{
      background:#f7f8fa!important;
    }
  }
</style>
