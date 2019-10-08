/**
 * Created by lixuqiang on 2018/4/26.
 */
export function isvalidPhone(str) {
  const reg = /^1[3|4|5|6|7|8|9][0-9]\d{8}$/
  return reg.test(str)
}
export function isvalidId(str) {
  const reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
  return reg.test(str)
}
export function isValidAccount(str){
  const reg = /^[0-9a-zA-Z]+$/;
  return reg.test(str)
}
export function Zipcode(str) {
  const reg = /^[0-9][0-9]{5}$/;
  return reg.test(str)
}
export function checkAge(str) {
  const reg = /^[0-9]*$/;
  return reg.test(str)
}
export function Numbertype(str) {
  const reg  = /^-?\d+\.?\d{0,10}$/
  return reg.test(str)
}
export function Spacetype(str) {
  const reg  = /\s+/g
  return reg.test(str)
}
export function Decimal(str) {
  const reg  = /^-?\d+\.?\d{0,4}$/
  return reg.test(str)
}
export function links(str) {
  const reg  = /^http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- ./?%&=]*)?$/
  return reg.test(str)
}
export function wholeNumbertype(str) {
  const reg  = /^\+?[1-9][0-9]*$/
  return reg.test(str)
}
// 金额判断
export function sumType(str) {
  const reg  = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/
  return reg.test(str)
}
// 固话和手机号一起验证
export function isvalidFixedPhone(str) {
  const reg = /^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0-9]))\d{8}$|^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0-9]))\d{8}$|^0\d{2,3}-?\d{7,8}$/
  return reg.test(str)
}
