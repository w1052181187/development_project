var CryptoAgent = "";
// Create ActiveX object according to the platform
const OnLoad = () => {
  try {
    var eDiv = document.createElement("div");
    if (navigator.appName.indexOf("Internet") >= 0 || navigator.appVersion.indexOf("Trident") >= 0) {
      if (window.navigator.cpuClass == "x86") {
        eDiv.innerHTML = "<object id=\"CryptoAgent\" codebase=\"CryptoKit.SDDR.x86.cab\" classid=\"clsid:88BE7301-91C1-4DD7-9705-44C782273E07\" ></object>";
      }
      else {
        eDiv.innerHTML = "<object id=\"CryptoAgent\" codebase=\"CryptoKit.SDDR.x64.cab\" classid=\"clsid:EC5FE2ED-1721-4AF1-AA96-30048392A4E2\" ></object>";
      }
    }
    else {
      eDiv.innerHTML = "<embed id=\"CryptoAgent\" type=\"application/npCryptoKit.SDDR.x86\" style=\"height: 0px; width: 0px\">";
    }
    document.body.appendChild(eDiv);
  }
  catch (e) {
    alert(e);
    return;
  }
  CryptoAgent = document.getElementById("CryptoAgent");
}
//菏泽ca签名
const SignOnClick = (callBack) =>{
  try{
    var content = 	"123";//获取原始随机数
    //选择证书
    var subjectDNFilter = "";//[IN]目标证书主题DN中所包含的字符串，作为该筛选条件选出证书。
    var issuerDNFilter = "";//[IN]目标证书颁发者DN中所包含的字符串，作为该筛选条件选出证书。
    var serialNumFilter = "";//[IN]目标证书的序列号，作为该筛选条件选出证书。
    var bSelectCertResult = CryptoAgent.SelectCertificate(subjectDNFilter, issuerDNFilter, serialNumFilter);//返回true或false
    if(bSelectCertResult){//选择证书
      //对原始数据签名
      var signData = CryptoAgent.SignMsgPKCS7(content, "SHA-1", true);
      if(signData == '' || signData.length == 0){
        alert('您选择的证书有误!');
        return false;
      }else{
        var certDN=CryptoAgent.GetSignCertInfo("SubjectDN");//获取dn项
        if(certDN !='' && certDN.length > 0){
          callBack(certDN)
        }
      }
    }else{
      alert('您选择的证书有误!');
      return false;
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
