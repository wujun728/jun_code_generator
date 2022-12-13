import types from './types'

export default {
  [types.PAGE_FILEATTACH] (state, payload) {
    state.items = payload.data.list
    state.total = payload.data.totalCount
    state.loading = false
  },
  [types.SHOW_FILEATTACH] (state, payload) {
    state.item = payload.data.entity
  },
  [types.PAGE_FILEATTACH_LOADING] (state, payload) {
    state.loading = true
  }
}
