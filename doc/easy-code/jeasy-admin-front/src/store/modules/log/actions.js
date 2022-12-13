import types from './types'
import Model from '../../../models/log'

export default {
  /**
   * 获取日志分页
   */
  pageLog ({ commit }, options) {
    commit(types.PAGE_LOG_LOADING, {})
    return new Model().addPath('page').GET(options).then((res) => {
      commit(types.PAGE_LOG, {
        data: res.data
      })
    })
  },

  /**
   * 新增日志
   */
  addLog ({ commit }, options) {
    return new Model().addPath('add').POST(options)
  },

  /**
   * 修改日志
   */
  modifyLog ({ commit }, options) {
    return new Model().addPath('modify').POST(options)
  },

  /**
   * 获取日志详情
   */
  showLog ({ commit }, options) {
    return new Model().addPath('show').GET(options).then((res) => {
      commit(types.SHOW_LOG, {
        data: res.data
      })
    })
  },

  /**
   * 删除日志
   */
  removeLog ({ commit }, options) {
    return new Model().addPath('remove').POST(options)
  }
}
