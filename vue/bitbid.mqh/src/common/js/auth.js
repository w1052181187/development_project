import Cookies from 'js-cookie'

export const TokenKey = 'x-token'
/** 主题 */
export const Theme = 'themeColor'
/** 记住密码 */
export const RememberUserInfo = 'user-info'
export function getToken () {
  return Cookies.get(TokenKey)
}

export function setToken (token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken () {
  return Cookies.remove(TokenKey)
}

export function getUserInfo () {
  return Cookies.set(RememberUserInfo)
}

export function setUserInfo (userInfo, number) {
  return Cookies.set(RememberUserInfo, userInfo, {expires: number})
}

export function removeUserInfo () {
  return Cookies.remove(RememberUserInfo)
}

export function setTheme (themeName, number) {
  return Cookies.set(Theme, themeName, {expires: number})
}

export function getTheme () {
  return Cookies.get(Theme)
}
