
/**
 * 项目类型
 * @param type
 * @returns {*}
 */
const handleMethod = function (type) {
  var typStr
  switch (type) {
    case 1:
      typStr = '公开招标'
      break
    case 2:
      typStr = ' 邀请招标'
      break
    case 3:
      typStr = '竞争性谈判'
      break
    case 4:
      typStr = '单一采购来源'
      break
    case 5:
      typStr = '询价采购'
      break
    case 6:
      typStr = '竞争性磋商'
      break
  }
  return typStr
}

/**
 * 公告类型
 * @param type
 * @param yushenType
 * @returns {*}
 */
const handleNoticeType = function (type, yushenType) {
  var str
  switch (type) {
    case 1:
      if (yushenType === 1) {
        str = '资格预审公告'
      } else {
        str = '资格后审公告'
      }
      break
    case 3:
      str = '竞争性谈判公告'
      break
    case 5:
      str = '询价采购公告'
      break
    case 6:
      str = '竞争性磋商公告'
      break
  }
  return str
}

const _RESPONSE_SUCCESS_CODE = '0000'

/**
 * 审批成功
 * @param context
 * @param resCode
 */
const checkSuccessed = function (context, resCode) {
  if (resCode === _RESPONSE_SUCCESS_CODE) {
    context.$router.go(-1)
  }
}

/**
 * 采购项目所属行业
 * @param type
 * @returns {*}
 */
const handleIndustry = function (type) {
  var typStr
  switch (type) {
    case 1:
      typStr = '市政房地产建筑'
      break
    case 2:
      typStr = ' 水利桥梁'
      break
    case 3:
      typStr = '园林绿化'
      break
    case 4:
      typStr = '医疗卫生'
      break
    case 5:
      typStr = '交通运输'
      break
    case 6:
      typStr = '机械电子电器'
      break
    case 7:
      typStr = '能源化工'
      break
    case 8:
      typStr = ' 冶金矿产原材料'
      break
    case 9:
      typStr = '网络通讯计算机'
      break
    case 10:
      typStr = '科技文教旅游'
      break
    case 11:
      typStr = '出版印刷'
      break
    case 12:
      typStr = '轻工纺织食品'
      break
    case 13:
      typStr = '农林牧渔'
      break
    case 14:
      typStr = '商业服务'
      break
    case 15:
      typStr = '环保'
      break
    case 16:
      typStr = '其他'
      break
  }
  return typStr
}

export {
  handleMethod,
  checkSuccessed,
  handleNoticeType,
  handleIndustry
}
