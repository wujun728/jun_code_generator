import types from './types'
import Model from '../../../models/role'

export default {
  /**
   * 获取角色分页
   */
  pageRole ({ commit }, options) {
    commit(types.PAGE_ROLE_LOADING, {})
    return new Model().addPath('page').GET(options).then((res) => {
      commit(types.PAGE_ROLE, {
        data: res.data
      })
    })
  },

  /**
   * 获取角色权限集合
   */
  listRolePermission ({ commit }, options) {
    return new Model().addPath('listPermission').GET(options).then((res) => {
      commit(types.LIST_ROLE_PERMISSION, {
        data: res.data
      })
    })
  },

  /**
   * 新增角色
   */
  addRole ({ commit }, options) {
    return new Model().addPath('add').POST(options)
  },

  /**
   * 修改角色
   */
  modifyRole ({ commit }, options) {
    return new Model().addPath('modify').POST(options)
  },

  /**
   * 修改角色权限配置
   */
  modifyRolePermission ({ commit }, options) {
    return new Model().addPath('modifyPermission').POST(options)
  },

  /**
   * 获取角色详情
   */
  showRole ({ commit }, options) {
    return new Model().addPath('show').GET(options).then((res) => {
      commit(types.SHOW_ROLE, {
        data: res.data
      })
    })
  },

  /**
   * 删除角色
   */
  removeRole ({ commit }, options) {
    return new Model().addPath('remove').POST(options)
  }
}
