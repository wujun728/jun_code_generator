import types from './types'
import Model from '../../../models/resource'

export default {
  /**
   * 获取菜单分页
   */
  pageResource ({ commit }, options) {
    commit(types.PAGE_RESOURCE_LOADING, {})
    return new Model().addPath('page').GET(options).then((res) => {
      commit(types.PAGE_RESOURCE, {
        data: res.data
      })
    })
  },

  /**
   * 获取菜单列表
   */
  listResource ({ commit }, options) {
    return new Model().addPath('list').GET(options).then((res) => {
      commit(types.LIST_RESOURCE, {
        data: res.data
      })
    })
  },

  /**
   * 新增菜单
   */
  addResource ({ commit }, options) {
    return new Model().addPath('add').POST(options)
  },

  /**
   * 修改菜单
   */
  modifyResource ({ commit }, options) {
    return new Model().addPath('modify').POST(options)
  },

  /**
   * 获取菜单详情
   */
  showResource ({ commit }, options) {
    return new Model().addPath('show').GET(options).then((res) => {
      commit(types.SHOW_RESOURCE, {
        data: res.data
      })
    })
  },

  /**
   * 删除菜单
   */
  removeResource ({ commit }, options) {
    return new Model().addPath('remove').POST(options)
  }
}
