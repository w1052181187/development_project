import Cookies from 'js-cookie'

export const TokenKey = 'admin-token'
export const progressKey = 'admin-progress'

export function getToken () {
  return Cookies.get(TokenKey)
}

export function setToken (token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken () {
  return Cookies.remove(TokenKey)
}

export function getProgress () {
  return Cookies.get(progressKey)
}

export function setProgress (progress) {
  return Cookies.set(progressKey, progress)
}

export function removeProgress () {
  return Cookies.remove(progressKey)
}
