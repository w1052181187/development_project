<template>
  <div id="register">
    <div class="login-top">
      <div class="topmain"><a href="http://www.sdbidding.org.cn/home"><img src="../../../static/images/user/logo.png"/> </a></div>
    </div>
    <div class="logmain">
      <div class="fl">
        <img src="../../../static/images/user/user_img.png"/>
      </div>
      <div class="fr">
        <div class="title register_title">
            <span>
                用户注册
            </span>
          <a @click="toLogin">登录</a>
        </div>
        <el-form :model="registerForm" status-icon :rules="rules" ref="registerForm" class="registerForm">
          <el-form-item prop="account" :class="IEVersion === 1? 'ie9box':''">
            <span v-if="IEVersion === 1" class="formtip">用户名：</span>
            <el-input :class="IEVersion === 1? 'shortinput':''" v-model="registerForm.account" placeholder="用户名"></el-input>
            <i class="icon_img" v-if="IEVersion !== 1"><img src="../../../static/images/user/user.png"/> *</i>
          </el-form-item>
          <el-form-item prop="enterprise.name" :class="IEVersion === 1? 'ie9box':''">
            <span v-if="IEVersion === 1" class="formtip">企业名称：</span>
            <el-input :class="IEVersion === 1? 'shortinput':''" v-model="registerForm.enterprise.name" placeholder="企业名称"></el-input>
            <i class="icon_img" v-if="IEVersion !== 1"><img src="../../../static/images/user/company.png"/> *</i>
          </el-form-item>
          <el-form-item prop="password" :class="IEVersion === 1? 'ie9box':''">
            <span v-if="IEVersion === 1" class="formtip">密码：</span>
            <el-input :class="IEVersion === 1? 'shortinput':''" type="password" v-model="registerForm.password" placeholder="密码"></el-input>
            <i class="icon_img" v-if="IEVersion !== 1"><img src="../../../static/images/user/password.png"/> *</i>
          </el-form-item>
          <el-form-item prop="passwordConfirm" :class="IEVersion === 1? 'ie9box':''">
            <span v-if="IEVersion === 1" class="formtip">确认密码：</span>
            <el-input :class="IEVersion === 1? 'shortinput':''" type="password" v-model="registerForm.passwordConfirm" placeholder="确认密码"></el-input>
            <i class="icon_img" v-if="IEVersion !== 1"><img src="../../../static/images/user/password.png"/> *</i>
          </el-form-item>
        </el-form>
        <div class="loginbtn">
          <el-checkbox v-model="checked">我已阅读并同意<span @click="centerDialogVisible = true">《山东省采购与招标网用户服务协议》</span> </el-checkbox>
          <el-button type="primary" @click="submitForm('registerForm')">注 册</el-button>
        </div>
      </div>
    </div>
    <!--登录注册 底部 -->
    <!--<div class="login-bottom">-->
      <!--Copyright © 2005-2017 In800.com. All Rights Reserved. 山东省采购与招标网-->
    <!--</div>-->
    <!--登录注册 底部 -->
    <!-- 协议 -->
    <el-dialog
      title="山东省采购与招标网用户服务协议"
      :visible.sync="centerDialogVisible"
      width="70%"
      center>
        <div class="dialogContent">
          <p class="headline">
            <span class="alertfont"><span>欢迎您注册成为山东省采购与招标网站用户！</span></span>
          </p>
          <p class="headline">
            <span class="alertfont"><span>请仔细阅读下面的协议，只有接受协议才能继续进行注册。</span> </span>
          </p>
          <p class="headline"><span class="alertfont"><span class="title-content">1．服务条款的确认和接纳</span></span></p>
          <p class="headline">
            <span class="alertfont">
              <span>山东省采购与招标网站用户服务的所有权和运作权归山东省采购与招标网站拥有。山东省采购与招标网站所提供的服务将按照有关章程、服务条款和操作规则严格执行。用户通过勾选</span>
            </span>
            <span class="alertfont"><span>【</span></span>
            <span class="alertfont"><span>我已阅读并同意《山东省采购与招标网用户服务协议》</span></span>
            <span class="alertfont"><span>】</span></span>
            <span class="alertfont"><span>并注册</span></span>
            <span class="alertfont"><span>成功</span></span>
            <span class="alertfont"><span>，即表示用户与山东省采购与招标网站达成协议并接受所有的服务条款。</span></span>
          </p>
          <p class="headline"><span class="alertfont"><span class="title-content">2． 山东省采购与招标网站服务简介</span></span></p>
          <p class="headline">
            <span class="alertfont">
              <span>山东省采购与招标网站通过国际互联网为用户提供邮件收发、</span>
              BBS论坛、网上聊天和软件下载等服务。
            </span>
          </p>
          <p class="headline">
            <span class="alertfont"><span>用户必须：</span></span>
          </p>
          <p class="headline"><span class="alertfont">1)购置设备，包括个人电脑一台、调制解调器一个及配备上网装置。 </span></p>
          <p class="headline"><span class="alertfont">2)个人上网和支付与此服务有关的电话费用、网络费用。</span></p>
          <p class="headline"><span class="alertfont"><span>用户同意：</span> </span></p>
          <p class="headline"><span class="alertfont">1)提供及时、详尽及准确的个人资料。 </span></p>
          <p class="headline"><span class="alertfont">2)不断更新注册资料，符合及时、详尽、准确的要求。所有原始键入的资料将引用为注册资料。 </span></p>
          <p class="headline">
            <span class="alertfont">3)用户同意遵守《中华人民共和国保守国家秘密法》、《中华人民共和国计算机信息系统安全保护条例》、《计算机软件保护条例》等有关计算机及互联网规定的法律和法规、实施办法。在任何情况下，山东省采购与招标网网站合理地认为用户的行为可能违反上述法律、法规，山东省采购与招标网网站可以在任何时候，不经事先通知终止向该用户提供服务。用户应了解国际互联网的无国界性，应特别注意遵守当地所有有关的法律和法规。</span>
          </p>
          <p class="headline"><span class="alertfont"><span class="title-content">3． 服务条款的修改</span></span></p>
          <p class="headline">
            <span class="alertfont"><span>山东省采购与招标网站会不定时地修改服务条款，服务条款一旦发生变动，将会在相关页面上提示修改内容。如果您不接受改动，则及时联系</span></span>
            <span class="alertfont"><span>网站管理人员</span></span>
            <span class="alertfont"><span>取消您的用户使用服务资格。</span></span>
          </p>
          <p class="headline"><span class="alertfont">&nbsp;<span class="title-content">4． 服务修订</span></span></p>
          <p class="headline">
            <span class="alertfont">
              <span>山东省采购与招标网站保留随时修改或中断服务而不需知照用户的权利。山东省采购与招标网网站行使修改或中断服务的权利，不需对用户或第三方负责。</span>
            *邮件系统保留收费的可能。
            </span>
          </p>
          <p class="headline"><span class="alertfont">&nbsp;<span class="title-content">5． 用户隐私制度</span></span></p>
          <p class="headline">
            <span class="alertfont">
              <span>尊重用户个人隐私是山东省采购与招标网站的</span>
              <span>基本政策。山东省采购与招标网站不会公开、编辑或透露用户的邮件内容，除非有法律许可要求，或山东省采购与招标网站在诚信的基础上认为透露这些信件在以下三种情况是必要的：</span>
            </span>
          </p>
          <p class="headline"><span class="alertfont">1)遵守有关法律规定，遵从合法服务程序。 </span></p>
          <p class="headline"><span class="alertfont">2)保持维护山东省采购与招标网站的商标所有权。 </span></p>
          <p class="headline"><span class="alertfont">3)在紧急情况下竭力维护用户个人和社会大众的隐私安全。 </span></p>
          <p class="headline"><span class="alertfont">4)符合其他相关的要求。 </span></p>
          <p class="headline"><span class="alertfont">&nbsp;<span class="title-content">6．用户的帐号，密码和安全性</span></span></p>
          <p class="headline">
            <span class="alertfont">
              <span>一旦注册成功成为山东省采购与招标网站用户，您将得到一个密码和帐号。如果您不保管好自己的帐号和密码安全，将对因此产生的后果负全部责任。另外，每个用户都要对其帐户中的所有活动和事件负全责。您可随时根据指示改变您的密码，也可以结束旧的帐户重开一个新帐户。用户同意若发现任何非法使用用户帐号或安全漏洞的情况，立即通知山东省采购与招标网网站。</span>
            </span>
          </p>
          <p class="headline"><span class="alertfont">&nbsp;<span class="title-content">7． 免责条款</span></span></p>
          <p style="text-indent:13px">
            <span class="alertfont floatleft"><span>&nbsp;&nbsp;&nbsp;&nbsp;用户明确同意邮件服务的使用由用户个人承担风险。</span></span>
          </p>
          <p class="headline">
            <span class="alertfont">
              <span>山东省采购与招标网站不作任何类型的担保，不担保服务一定能满足用户的要求，也不担保服务不会受中断，对服务的及时性，安全性，出错发生都不作担保。用户理解并接受：任何通过山东省采购与招标网网站服务取得的信息资料的可靠性取决于用户自己，用户自己承担所有风险和责任。</span>
            </span>
          </p>
          <p class="headline"><span class="alertfont">&nbsp;<span class="title-content">8．有限责任</span></span></p>
          <p class="headline">
            <span class="alertfont">
              <span>山东省采购与招标网站对任何直接、间接、偶然、特殊及继起的损害不负责任，这些损害来自：不正当使用邮件服务，在网上购买商品或服务，在网上进行交易，非法使用邮件服务或用户传送的信息所变动。</span>
            </span>
          </p>
          <p class="headline"><span class="alertfont">&nbsp;<span class="title-content">9． 不提供零售和商业性服务</span></span></p>
          <p class="headline">
            <span class="alertfont">
              <span>用户使用邮件服务的权利是个人的。用户只能是一个单独的个体而不能是一个公司或实体商业性组织。用户承诺不经山东省采购与招标网站同意，不能利用邮件服务进行销售或其他商业用途。</span>
            </span>
          </p>
          <p class="headline"><span class="alertfont">&nbsp;<span class="title-content">10．用户责任</span></span></p>
          <p class="alert_p"><span class="alertfont floatleft"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户单独承担传输内容的责任。用户必须遵循：</span></span>
          </p>
          <p class="alert_p"><span class="alertfont floatleft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1)从中国境内向外传输技术性资料时必须符合中国有关法规。 </span></p>
          <p class="alert_p"><span class="alertfont floatleft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2)使用邮件服务不作非法用途。</span></p>
          <p class="alert_p"><span class="alertfont floatleft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3)不干扰或混乱网络服务。 </span></p>
          <p class="alert_p"><span class="alertfont floatleft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4)不在论坛BBS或留言簿发表任何与政治相关的信息。 </span></p>
          <p class="alert_p"><span class="alertfont floatleft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5)遵守所有使用邮件服务的网络协议、规定、程序和惯例。</span></p>
          <p class="alert_p"><span class="alertfont floatleft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6)不得利用本站危害国家安全、泄露国家秘密，不得侵犯国家社会集体的和公民的合法权益。</span></p>
          <p class="alert_p"><span class="alertfont floatleft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7)不得利用本站制作、复制和传播下列信息： </span></p>
          <p class="alert_p"><span class="alertfont floatleft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、煽动抗拒、破坏宪法和法律、行政法规实施的；</span></p>
          <p class="alert_p"><span class="alertfont floatleft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、煽动颠覆国家政权，推翻社会主义制度的；</span></p>
          <p class="alert_p"><span class="alertfont floatleft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、煽动分裂国家、破坏国家统一的；</span></p>
          <p class="alert_p"><span class="alertfont floatleft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、煽动民族仇恨、民族歧视，破坏民族团结的；</span></p>
          <p class="alert_p"><span class="alertfont floatleft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5、捏造或者歪曲事实，散布谣言，扰乱社会秩序的；</span></p>
          <p class="alert_p"><span class="alertfont floatleft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6、宣扬封建迷信、淫秽、色情、赌博、暴力、凶杀、恐怖、教唆犯罪的；</span></p>
          <p class="alert_p"><span class="alertfont floatleft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7、公然侮辱他人或者捏造事实诽谤他人的，或者进行其他恶意攻击的；</span></p>
          <p class="alert_p"><span class="alertfont floatleft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;8、损害国家机关信誉的；</span></p>
          <p class="alert_p"><span class="alertfont floatleft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;9、其他违反宪法和法律行政法规的；</span></p>
          <p class="alert_p"><span class="alertfont floatleft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;10、进行商业广告行为的。</span></p>
          <p style="text-indent:16px">
            <span class="alertfont floatleft">
              &nbsp;&nbsp;&nbsp;&nbsp;用户不能利用邮件服务作连锁邮件，垃圾邮件或分发给任何未经允许接收信件的人。用户须承诺不传输任何非法的、骚扰性的、中伤他人的、辱骂性的、恐吓性的、伤害性的、庸俗的和淫秽的信息资料。另外，用户也不能传输任何教唆他人构成犯罪行为的资料；不能传输长国内不利条件和涉及国家安全的资料；不能传输任何不符合当地法规、国家法律和国际法
              律的资料。未经许可而非法进入其它电脑系统是禁止的。若用户的行为不符合以上的条款，山东省采购与招标网站将取消用户服务帐号。
            </span>
          </p>
          <p class="headline"><span class="alertfont">&nbsp;<span class="title-content">11．发送通告</span></span></p>
          <p class="headline">
            <span class="alertfont">
              <span>&nbsp;所有发给用户的通告都可通过电子邮件或常规的信件传送。山东省采购与招标网站会通过邮件服务发报消息给用户，告诉他们服务条款的修改、服务变更、或其它重要事情。</span>
            </span>
          </p>
          <p class="headline"><span class="alertfont">&nbsp;<span class="title-content">12．网站内容的所有权</span></span></p>
          <p style="text-indent:16px">
            <span class="alertfont floatleft">
              <span>&nbsp;&nbsp;&nbsp;&nbsp;山东省采购与招标网站定义的内容包括：文字、软件、声音、相片、录象、图表；在广告中全部内容；电子邮件的全部内容；山东省采购与招标网网站为用户提供的商业信息。所有这些内容受版权、商标、标签和其它财产所有权法律的保护。所以，用户只能在山东省采购与招标网网站和广告商授权下才能使用这些内容，而不能擅自复制、篡改这些内容、或创造与内容有关的派生产品。</span>
            </span>
          </p>
          <p class="headline"><span class="alertfont">&nbsp;<span class="title-content">13．附加信息服务</span></span></p>
          <p class="headline">
            <span class="alertfont">
              <span>用户在享用山东省采购与招标网站提供的免费服务的同时，同意接受山东省采购与招标网站提供的各类附加信息服务。</span>
            </span>
          </p>
          <p class="headline"><span class="alertfont">&nbsp;<span class="title-content">14．解释权</span></span></p>
          <p class="headline">
            <span class="alertfont">
              <span>本注册协议的解释权归山东省采购与招标网站所有。如果其中有任何条款与国家的有关法律相抵触，则以国家法律的明文规定为准。</span>
            </span>
          </p>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button>
        </span>
    </el-dialog>
    <!-- -->
  </div>
</template>
<script>
import {isvalidAccount} from '@/assets/js/validate'
export default {
  data () {
    // 密码
    let validatePwd = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入密码'))
      } else if (this.registerForm.passwordConfirm !== '') {
        this.$refs.registerForm.validateField('passwordConfirm')
        callback()
      }
    }
    // 确认密码
    let validateConfirmPwd = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.registerForm.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    // 用户名
    let validateAccount = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入用户名'))
      } else if (!isvalidAccount(value)) {
        callback(new Error('请输入4-16位字母或数字'))
      } else {
        callback()
      }
    }
    return {
      checked: false,
      registerForm: {
        account: '',
        enterprise: {
          name: ''
        },
        password: '',
        passwordConfirm: ''
      },
      centerDialogVisible: false,
      rules: {
        account: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 4, max: 16, message: '长度在 4到 16 个字符', trigger: 'blur' },
          {validator: validateAccount, trigger: ['blur', 'change']}
        ],
        'enterprise.name': [
          { required: true, message: '请输入企业名称', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1到 100 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: ['blur', 'change'] },
          {min: 6, max: 20, message: '长度在 6到 20 个字符', trigger: ['blur']},
          { validator: validatePwd, trigger: 'blur' }

        ],
        passwordConfirm: [
          { required: true, message: '请重新输入密码', trigger: ['blur', 'change'] },
          {min: 6, max: 20, message: '长度在 6到 20 个字符', trigger: ['blur']},
          { validator: validateConfirmPwd, trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (!this.checked) {
            this.$message({
              type: 'warning',
              message: '请阅读并同意山东省采购与招标网用户服务协议'
            })
            return false
          }
          this.registerForm.type = 3
          this.registerForm.status = 0
          this.$store.dispatch('Register', this.registerForm).then(res => {
            // 跳转编辑企业信息页
            this.$router.push({path: '/index/enterprise'})
          })
        } else {
          return false
        }
      })
    },
    toLogin () {
      this.$router.push({path: '/login'})
    }
  },
  computed: {
    IEVersion () {
      let browser = navigator.appName
      let bversion = navigator.appVersion
      let version = bversion.split(';')
      if (version[1] !== undefined) { // ie浏览器上使用
        let trimVersion = version[1].replace(/[ ]/g, '')
        if (browser === 'Microsoft Internet Explorer' && trimVersion === 'MSIE9.0') {
          return 1
        }
      }
    }
  },
  mounted () {
  }
}
</script>
<style lang="less">
  body, html{
    background: #ffffff;
  }
  #register {
    .login-top{
      width: 100%;
      min-width: 1200px;
      height: 103px;
      overflow: hidden;
      background: url("../../../static/images/user/top_bg.png") repeat-x;
    }
    .login-top .topmain{
      width: 1200px;
      overflow: hidden;
      margin: 0 auto;
      text-align: left;
    }
    .login-top .topmain a{
      margin-top: 25px;
      display: block;
    }
    .login-top .topmain a img{
      border:none;
    }
    .logmain{
      width: 1200px;
      overflow: hidden;
      margin: 30px auto;
    }
    .logmain .fl{
      float: left;
    }
    .logmain .fr{
      width: 350px;
      height: 427px;
      float: right;
      border: 1px solid #eeeeee;
      -webkit-box-sizing: border-box;
      -moz-box-sizing: border-box;
      box-sizing: border-box;
    }
    .logmain .fr .title{
      height: 72px;
      border: 1px solid #eeeeee;
      padding: 0px;
      margin: 0px;
    }
    .logmain .fr .title span{
      float: left;
      margin: 27px 0 0 27px;
      display: inline-block;
      line-height: 40px;
      font-size: 16px;
    }
    .logmain .fr .title a{
      float: right;
      margin: 27px 27px 0 0;
      display: inline-block;
      line-height: 40px;
      font-size: 16px;
      padding-left: 20px;
      color: #5a7dd6;
      font-size: 12px;
      cursor: pointer;
      background: url("../../../static/images/user/iconlink.png") left center no-repeat;
    }
    .registerForm{
      width: 90%;
      margin: 13px auto 20px auto;
    }
    .login .el-form-item__content{
      margin-left: 0px!important;
      position: relative;
    }
    .el-input__inner{
      padding: 0 15px 0 40px;
    }
    .icon_img{
      position: absolute;
      left: 5px;
      top: 0px;
      display: inline-block;
      height: 40px;
      line-height: 40px;
      color: #ff4040;
    }
    .icon_img img{
      vertical-align: middle;
    }
    .loginbtn {
      width: 90%;
      margin: 20px auto 0 auto;
    }
    .el-checkbox{
      float: left;
      margin-bottom: 13px;
    }
    .loginbtn .el-button--primary {
      width: 100%;
      color: #fff;
      background-color: #5a7dd6;
      border-color: #5a7dd6;
    }
    .el-dialog{
      height: 70%;
      overflow: auto;
    }
    .el-checkbox__label{
      font-size: 12px;
    }
    .headline {
      text-indent:32px;
      float: left;
      width: 100%;
      text-align: left;
    }
    .alertfont {
      font-size:16px;
      text-align: left;
    }
    .floatleft{
      width:100%;
      float: left;
      line-height: 30px;
    }
    .alert_p {
      text-indent:0
    }
    .title-content {
      font-weight: bold;
    }
    .formtip{
      width: 24%;
      display: inline-block;
      float: left;
      color: #2c3e50;
      float: left;
      text-align: right;
    }
    .shortinput{
      width: 72%;
      float: left;
      margin-left: 2%;
      padding-left: 2%;
    }
    .shortinput .el-input__inner{
      padding-left: 10px;
    }
    .ie9box .el-form-item__error{
      left: 28%;
    }
  }
</style>
