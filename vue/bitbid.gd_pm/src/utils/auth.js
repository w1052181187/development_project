import Cookies from 'js-cookie'

export const TokenKey = 'x-token'

export function getToken () {
  return Cookies.get(TokenKey)
}

export function setIsKeepUser (param) { // 31天过期
  return Cookies.set('isKeepUser', param, {expires: 31})
}

export function getIsKeepUser () {
  return Cookies.get('isKeepUser')
}

export function setToken (token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken () {
  return Cookies.remove(TokenKey)
}
