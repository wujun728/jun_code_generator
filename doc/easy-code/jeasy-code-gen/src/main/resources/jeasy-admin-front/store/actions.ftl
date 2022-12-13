import types from './types'
import Model from '../../../models/${table.camelName}'

export default {
  /**
   * 获取${table.comment}分页
   */
  page${table.className} ({ commit }, options) {
    commit(types.PAGE_${table.upperCamelName}_LOADING, {})
    return new Model().addPath('page').GET(options).then((res) => {
      commit(types.PAGE_${table.upperCamelName}, {
        data: res.data
      })
    })
  },

  /**
   * 新增${table.comment}
   */
  add${table.className} ({ commit }, options) {
    return new Model().addPath('add').POST(options)
  },

  /**
   * 修改${table.comment}
   */
  modify${table.className} ({ commit }, options) {
    return new Model().addPath('modify').POST(options)
  },

  /**
   * 获取${table.comment}详情
   */
  show${table.className} ({ commit }, options) {
    return new Model().addPath('show').GET(options).then((res) => {
      commit(types.SHOW_${table.upperCamelName}, {
        data: res.data
      })
    })
  },

  /**
   * 删除${table.comment}
   */
  remove${table.className} ({ commit }, options) {
    return new Model().addPath('remove').POST(options)
  }
}
