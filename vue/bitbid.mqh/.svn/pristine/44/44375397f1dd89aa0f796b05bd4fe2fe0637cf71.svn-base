/**
 * Created by zhangting on 2018/7/19.
 */
export function isvalidPhone (str) {
  const reg = /^1[3|4|5|6|7|8|9][0-9]\d{8}$/
  return reg.test(str)
}
export function isvalidPassword (str) {
  // const reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/
  // const reg = /^[\da-zA-Z!@#$%^&*]{6,20}$/
  const reg = /((?=.*[a-zA-Z])(?=.*\d)|(?=[a-zA-Z])(?=.*[#@!~%^&*])|(?=.*\d)(?=.*[#@!~%^&*]))[a-zA-Z\d#@!~%^&*]{6,20}/
  return reg.test(str)
}
export function isvalidAccount (str) {
  // const reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{1,50}/
  const reg = /^(\w+$){1,50}/
  return reg.test(str)
}
export function isvalidPrice (str) { // 正数，最多两位小数(整数部分18位，小数最多2位)
  // const reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{1,50}/
  // const reg = /^[0-9]*$/
  const reg = /^[0-9]{1,18}(\.\d{1,2})?$/
  return reg.test(str)
}
export function isvalidId (str) {
  const reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
  return reg.test(str)
}
export function isvalidPriceForNegative (str) { // 数字，最多两位小数(整数部分18位，小数最多2位)
  const reg = /^[\-\+]?[0-9]{1,18}(\.\d{1,2})?$/
  return reg.test(str)
}

export function isvalidRiskRatio (str) { // 大于等于0小于等于100的正数用正则表达式表示
  const reg = /^100$|^(\d|[1-9]\d)(\.\d+)*$/ // 可以有小数
  // const reg = /^100$|^(\d|[1-9]\d)$/ // 不可以有小数
  return reg.test(str)
}

// 固话和手机号一起验证（11位手机号码或带区号的固话）
export function isvalidFixedPhone (str) {
  const reg = /^1[3|4|5|6|7|8][0-9]\d{8}$|^0\d{2,3}-?\d{7,8}$/
  return reg.test(str)
}

// 正数，最多两位小数(整数部分14位，小数最多6位)
export function isvalidPriceForCoal (str) {
  const reg = /^[0-9]{1,14}(\.\d{1,6})?$/
  return reg.test(str)
}

// 正数，最多两位小数(整数部分16位，小数最多4位)
export function isvalidPriceForCoalFee (str) {
  const reg = /^[0-9]{1,16}(\.\d{1,4})?$/
  return reg.test(str)
}

// 正数，最多两位小数(整数部分5位，小数最多2位)
export function isvalidPriceForCoefficient (str) {
  const reg = /^[0-9]{1,5}(\.\d{1,2})?$/
  return reg.test(str)
}
