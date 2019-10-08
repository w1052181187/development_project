<template>
  <div id="footer-home" class="footer-home">
    <div class="foot-cont">
      <el-row :gutter="200">
        <el-col :span="12">
          <div class="disclaimer">
            <span class="foot-title-en">DISCLAIMER</span>
            <span class="foot-title-ch">免责声明</span>
            <div class="line-division">
              <span class="line-l"></span>
              <span class="line-r"></span>
            </div>
            <div class="list-cont">
              <ul>
                <li>1. 本网站所发布信息版权原作者所有，若有版权附带声明以声明为准。</li>
                <li>2. 本网站发布信息的真实性由发布人负责。</li>
                <li>3. 本网站转载内容如有侵犯你的权益，请通知我们更正。若无声明，则视为默许。由此而导致的任何法律争议和后果，本站不承担任何责任。</li>
                <li>4. 本网站转载内容仅代表作者本人的观点，与本网站立场无关</li>
                <li></li>
              </ul>
            </div>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="contact">
            <span class="foot-title-en">CONTACT INFORMATION</span>
            <span class="foot-title-ch">联系方式</span>
            <div class="line-division">
              <span class="line-l"></span>
              <span class="line-r"></span>
            </div>
            <div class="list-cont">
              <ul>
                <li>菏泽市公共资源（国有产权）交易中心</li>
                <li>地址：菏泽市济南路666号</li>
                <li>联系电话：（0530）5633003</li>
                <li>邮箱：hzsggzyiyzx@163.com</li>
                <li></li>
              </ul>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
export default {
  name: 'footer-home',
  data () {
    return {}
  },
  methods: {}
}
</script>
<style lang="less">
#footer-home {
  overflow: hidden;
  background: #333333;
  .foot-cont {
    width: 960px;
    margin: 0 auto;
    padding: 40px;
    color: #c3c3c3;
    .foot-title-en {
      float: left;
    }
    .foot-title-ch {
      float: right;
    }
    .line-division {
      margin-top: 10px;
      text-align: left;
      clear: both;
      .line-l {
        display: inline-block;
        height: 2px;
        width: 88px;
        background: #5A7DD6;
      }
      .line-r {
        display: inline-block;
        height: 2px;
        width: 290px;
        margin-left: -4px;
        background: #fff;
      }
    }
    .list-cont {
      ul {
        padding-left: 0;
      }
      li {
        list-style: none;
        text-align: left;
        font-size: 12px;
        line-height: 25px;
      }
    }
  }
}
</style>
