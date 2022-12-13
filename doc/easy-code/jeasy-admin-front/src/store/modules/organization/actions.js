import types from './types'
import Model from '../../../models/organization'

export default {
  /**
   * 获取机构分页
   */
  pageOrganization ({ commit }, options) {
    commit(types.PAGE_ORGANIZATION_LOADING, {})
    return new Model().addPath('page').GET(options).then((res) => {
      commit(types.PAGE_ORGANIZATION, {
        data: res.data
      })
    })
  },

  /**
   * 获取机构列表
   */
  listOrganization ({ commit }, options) {
    return new Model().addPath('list').GET(options).then((res) => {
      commit(types.LIST_ORGANIZATION, {
        data: res.data
      })
    })
  },

  /**
   * 新增机构
   */
  addOrganization ({ commit }, options) {
    return new Model().addPath('add').POST(options)
  },

  /**
   * 修改机构
   */
  modifyOrganization ({ commit }, options) {
    return new Model().addPath('modify').POST(options)
  },

  /**
   * 获取机构详情
   */
  showOrganization ({ commit }, options) {
    return new Model().addPath('show').GET(options).then((res) => {
      commit(types.SHOW_ORGANIZATION, {
        data: res.data
      })
    })
  },

  /**
   * 删除机构
   */
  removeOrganization ({ commit }, options) {
    return new Model().addPath('remove').POST(options)
  }
}
