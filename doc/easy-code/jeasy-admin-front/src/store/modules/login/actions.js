import types from './types'
import BaseModel from '@/models/base'

export default {
  /**
   * 登录
   */
  login ({ commit }, options) {
    return new BaseModel().addPath('login').POST(options).then((res) => {
      commit(types.LOGIN, {
        entity: res.data.entity
      })
    })
  },

  userInfo ({ commit }) {
    return new BaseModel().addPath('userInfo').POST().then((res) => {
      commit(types.USER_INFO, {
        entity: res.data.entity
      })
    })
  },

  /**
   * 登出
   */
  logout () {
    return new BaseModel().addPath('logout').POST()
  },

  /**
   * 获取当前用户菜单列表
   */
  listUserMenu ({ commit }) {
    return new BaseModel().addPath('resource/listUserMenu').GET().then((res) => {
      commit(types.LIST_USER_MENU, {
        data: res.data.list
      })
    })
  },

  /**
   * 获取当前用户当前菜单下的操作列表
   *
   * @param commit
   * @returns {Promise.<TResult>|*}
   */
  listUserMenuOperation ({ commit }, options) {
    return new BaseModel().addPath('resource/listUserMenuOperation').GET(options).then((res) => {
      commit(types.LIST_USER_MENU_OPTION, {
        data: res.data.list,
        menu: options.params.menuPath
      })
    })
  }
}
