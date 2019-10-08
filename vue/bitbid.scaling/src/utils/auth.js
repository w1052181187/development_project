import Cookies from 'js-cookie'

export const TokenKey = 'saclingsystem-token'
export const ProjectIdKey = 'x-project-id'
export const AgentKey = 'saclingsystem-is-agent'
export function getToken () {
  return Cookies.get(TokenKey)
}

export function setToken (token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken () {
  return Cookies.remove(TokenKey)
}

export function getProjectId () {
  return Cookies.get(ProjectIdKey)
}

export function setProjectId (projectId) {
  return Cookies.set(ProjectIdKey, projectId)
}

export function removeProjectId () {
  return Cookies.remove(ProjectIdKey)
}
