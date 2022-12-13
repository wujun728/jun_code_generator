import types from './types'

export default {
  [types.GET_TABLE_DATA] (state, payload) {
    state.tableData = payload.data.list
  },
  [types.GET_DRAG_LIST] (state, payload) {
    state.dragList = payload.data.list
  }
}
