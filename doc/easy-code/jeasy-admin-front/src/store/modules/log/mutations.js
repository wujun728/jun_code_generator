import types from './types'

export default {
  [types.PAGE_LOG] (state, payload) {
    state.items = payload.data.list
    state.total = payload.data.totalCount
    state.loading = false
  },
  [types.SHOW_LOG] (state, payload) {
    state.item = payload.data.entity
  },
  [types.PAGE_LOG_LOADING] (state, payload) {
    state.loading = true
  }
}
