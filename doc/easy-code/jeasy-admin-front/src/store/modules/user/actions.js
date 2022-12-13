import types from './types'
import Model from '../../../models/user'

export default {
  /**
   * 获取用户分页
   */
  pageUser ({ commit }, options) {
    commit(types.PAGE_USER_LOADING, {})
    return new Model().addPath('page').GET(options).then((res) => {
      commit(types.PAGE_USER, {
        data: res.data
      })
    })
  },

  /**
   * 获取用户角色分页
   */
  pageUserRole ({ commit }, options) {
    commit(types.ROLE_LOADING, {})
    return new Model().addPath('pageRole').GET(options).then((res) => {
      commit(types.PAGE_USER_ROLE, {
        data: res.data
      })
    })
  },

  /**
   * 获取用户角色集合
   */
  listUserRole ({ commit }, options) {
    return new Model().addPath('listRole').GET(options).then((res) => {
      commit(types.LIST_USER_ROLE, {
        data: res.data
      })
    })
  },

  /**
   * 获取用户机构集合
   */
  listUserOrganization ({ commit }, options) {
    return new Model().addPath('listOrganization').GET(options).then((res) => {
      commit(types.LIST_USER_ORGANIZATION, {
        data: res.data
      })
    })
  },

  /**
   * 新增用户
   */
  addUser ({ commit }, options) {
    return new Model().addPath('add').POST(options)
  },

  /**
   * 修改用户
   */
  modifyUser ({ commit }, options) {
    return new Model().addPath('modify').POST(options)
  },

  /**
   * 修改用户角色
   */
  modifyUserRole ({ commit }, options) {
    return new Model().addPath('modifyRole').POST(options)
  },

  /**
   * 修改用户机构
   */
  modifyUserOrganization ({ commit }, options) {
    return new Model().addPath('modifyOrganization').POST(options)
  },

  /**
   * 获取用户详情
   */
  showUser ({ commit }, options) {
    return new Model().addPath('show').GET(options).then((res) => {
      commit(types.SHOW_USER, {
        data: res.data
      })
    })
  },

  /**
   * 删除用户
   */
  removeUser ({ commit }, options) {
    return new Model().addPath('remove').POST(options)
  }
}
