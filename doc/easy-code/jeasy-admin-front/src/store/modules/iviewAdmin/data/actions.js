import types from './types'
import BaseModel from '@/models/base'

export default {

  getTableData ({ commit }, options) {
    return new BaseModel().addPath('get_table_data').GET(options).then((res) => {
      commit(types.GET_TABLE_DATA, {
        data: res.data
      })
    })
  },

  getDragList ({ commit }, options) {
    return new BaseModel().addPath('get_drag_list').GET(options).then((res) => {
      commit(types.GET_DRAG_LIST, {
        data: res.data
      })
    })
  }
}
