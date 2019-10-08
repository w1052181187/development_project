var SZCAOcx;
import { szca, agencyReviewSummary } from '@/api'
const OnLoad = () => {
  console.log('init begin...')
  try {
    var eDiv = document.createElement("div");
    if (navigator.appName.indexOf("Internet") >= 0 || navigator.appVersion.indexOf("Trident") >= 0) {
		if (window.navigator.cpuClass == "x86") {
			eDiv.innerHTML = "<object id=\"SZCAOcx\" classid=\"clsid:31D9C2D1-3BEC-4B25-87D6-16F6C3D75DE6\" ></object>";
		} else {
			eDiv.innerHTML = "<object id=\"SZCAOcx\" classid=\"clsid:31D9C2D1-3BEC-4B25-87D6-16F6C3D75DE6\" ></object>";
		}
	}
	else {
		eDiv.innerHTML = "<embed id=\"SZCAOcx\" type=\"application/npCryptoKit.SDDR.x86\" style=\"height: 0px; width: 0px\">";
	}
	document.body.appendChild(eDiv);
  }
  catch (e) {
  }
  SZCAOcx = document.getElementById("SZCAOcx");
  console.log('init end.')
}
//深圳ca签名
const SignOnClick = (loginName, fn) =>{
  try {
    SZCAOcx.AxSetCertFilterStr("SC;szca;#;#;#;");
    var result = SZCAOcx.AxSetKeyStore();
    if (result) {
      var content = "123";//获取原始随机数
      SZCAOcx.AxSetPin("");
      var certtxt = SZCAOcx.AxGetCertData();
      var result = SZCAOcx.AxSign(content);
      if (result !== "") {
        let url = {
          sSignTxt: result,
          sCertTxt: certtxt
        }
        szca.szcaSub(url).then((res) => {
           if (res.data.msg === 'success') {
              if (res.data.type === 'company' && res.data.name === loginName) {
                fn()
                return true;
              }else{
                alert("请使用当前登录人员企业认证CA签名！")
                return false;
              }
          } else {
             return false;
           }
       })
    }else {
      alertMsg.info('您选择的证书有误!');
      //AttachSignForm.errorMessage.innerHTML= "您选择的证书有误!";
    }

    } else {
      alertMsg.info('请选择证书!');
    }
  }
  catch(error)
  {
    console.log(error);
    alert("没有检测到CA，签名失败。");
    return false;
  }
}

export  { OnLoad, SignOnClick }
