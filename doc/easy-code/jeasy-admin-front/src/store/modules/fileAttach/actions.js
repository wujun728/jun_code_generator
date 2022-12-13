import types from './types'
import Model from '../../../models/fileAttach'

export default {
  /**
   * 获取文件附件分页
   */
  pageFileAttach ({ commit }, options) {
    commit(types.PAGE_FILEATTACH_LOADING, {})
    return new Model().addPath('page').GET(options).then((res) => {
      commit(types.PAGE_FILEATTACH, {
        data: res.data
      })
    })
  },

  /**
   * 新增文件附件
   */
  addFileAttach ({ commit }, options) {
    return new Model().addPath('add').POST(options)
  },

  /**
   * 修改文件附件
   */
  modifyFileAttach ({ commit }, options) {
    return new Model().addPath('modify').POST(options)
  },

  /**
   * 获取文件附件详情
   */
  showFileAttach ({ commit }, options) {
    return new Model().addPath('show').GET(options).then((res) => {
      commit(types.SHOW_FILEATTACH, {
        data: res.data
      })
    })
  },

  /**
   * 删除文件附件
   */
  removeFileAttach ({ commit }, options) {
    return new Model().addPath('remove').POST(options)
  }
}
