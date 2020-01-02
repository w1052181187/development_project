import Vuex from 'vuex'
import Vue from 'vue'
import {auth, drawroom} from '@/api'
import {getToken, setToken, removeToken} from '@/common/js/auth'

Vue.use(Vuex)

const state = {
  // 用户信息：{userId: xxx, enterpriseId: xxx, userName: xxx, enterpriseName: xxx，filePath：xxx, enterpriseType: xxx, userType: xxx}
  // enterpriseType: 1.集团公司；2.供应分公司；3.二级公司；4.招标机构
  // userType: 1.管理员；2.普通用户
  authUser: '',
  permissions: '',
  token: getToken(),
  messages: {}
}

const getters = {
  authUser: state => state.authUser,
  token: state => state.token,
  permissions: state => state.permissions,
  messages: state => state.messages
}

const mutations = {
  SET_USER: (state, user) => {
    state.authUser = user
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_PERMISSIONS: (state, permissions) => {
    state.permissions = permissions
  },
  SET_MESSAGES: (state, message) => {
    state.messages = message
  }
}

const actions = {
  /** 登录 */
  Login: ({commit}, userInfo) => {
    return new Promise((resolve, reject) => {
      auth.login(userInfo).then(response => {
        // 登录成功
        if (response.data.resCode === '0000') {
          // state保存token
          commit('SET_TOKEN', response.data.token)
          // 保存token到cookie中
          setToken(response.data.token)
          resolve(response.data.userType)
        } else {
          reject(response.data.resCode)
        }
      })
    })
  },
  /** 获取用户信息 */
  GetLoginInfo: ({commit, state}) => {
    return new Promise((resolve, reject) => {
      // TODO 目前先不考虑根据模块获取权限列表
      auth.getLoginInfo(state.token).then(response => {
        // 保存用户信息放到state中
        commit('SET_USER', response.data.loginUserInfo)
        // 保存权限信息到state中
        commit('SET_PERMISSIONS', response.data.permissions)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },
  /** 退出 */
  Logout: ({dispatch, commit}) => {
    return new Promise((resolve, reject) => {
      // 如果是从抽签室进行用户退出，要先把redis中的房间信息清掉
      if (state.messages && state.messages.roomId) {
        drawroom.delete(state.messages.roomId).then((res) => {
          if (res.data.resCode === '0000') {
            auth.logout().then(response => {
              // 清除数据
              dispatch('ClearLoginInfo')
              resolve()
            }).catch(error => {
              reject(error)
            })
          }
        })
      } else {
        auth.logout().then(response => {
          // 清除数据
          dispatch('ClearLoginInfo')
          resolve()
        }).catch(error => {
          reject(error)
        })
      }
    })
  },
  /** 清除数据 */
  ClearLoginInfo: ({commit}) => {
    // 清除数据
    commit('SET_USER', '')
    commit('SET_TOKEN', '')
    commit('SET_PERMISSIONS', '')
    // 清除cookie中的token
    removeToken()
  },
  /** 用于删除抽签室信息 */
  DeleteDramRoomInfo: ({commit, state}) => {
    // 处于抽签准备室，可以进行退出
    // 处于抽签室且未开始抽签，则可以进行退出
    /* Message({
      message: '抽签进行中，不能退出该房间！',
      type: 'error',
      onClose: function () {
        return false
      }
    }) */
    drawroom.delete(state.messages.roomId)
  }
}

export default new Vuex.Store({
  state,
  getters,
  mutations,
  actions
})
