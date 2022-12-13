import types from './types'
import Model from '../../../models/dictionary'

export default {

  /**
   * 获取字典列表
   */
  listDictionary ({ commit }, options) {
    return new Model().addPath('list').GET(options).then((res) => {
      commit(types.LIST_DICTIONARY, {
        data: res.data
      })
    })
  },

  /**
   * 获取字典分页列表
   */
  pageDictionary ({ commit }, options) {
    commit(types.PAGE_DICTIONARY_LOADING, {})
    return new Model().addPath('page').GET(options).then((res) => {
      commit(types.PAGE_DICTIONARY, {
        data: res.data
      })
    })
  },

  /**
   * 新增字典
   */
  addDictionary ({ commit }, options) {
    return new Model().addPath('add').POST(options)
  },

  /**
   * 修改字典
   */
  modifyDictionary ({ commit }, options) {
    return new Model().addPath('modify').POST(options)
  },

  /**
   * 获取字典详情
   */
  showDictionary ({ commit }, options) {
    return new Model().addPath('show').GET(options).then((res) => {
      commit(types.SHOW_DICTIONARY, {
        data: res.data
      })
    })
  },

  /**
   * 删除字典
   */
  removeDictionary ({ commit }, options) {
    return new Model().addPath('remove').POST(options)
  },

  /**
   * 获取字典类型列表
   */
  listDictionaryType ({ commit }, options) {
    return new Model().addPath('listType').GET(options).then((res) => {
      commit(types.LIST_DICTIONARY_TYPE, {
        data: res.data
      })
    })
  }
}
