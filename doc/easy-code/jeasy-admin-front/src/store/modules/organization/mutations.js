import types from './types'

export default {
  [types.PAGE_ORGANIZATION] (state, payload) {
    state.items = payload.data.list
    state.total = payload.data.totalCount
    state.loading = false
  },
  [types.LIST_ORGANIZATION] (state, payload) {
    state.items = payload.data.list
  },
  [types.SHOW_ORGANIZATION] (state, payload) {
    state.item = payload.data.entity
  },
  [types.PAGE_ORGANIZATION_LOADING] (state, payload) {
    state.loading = true
  }
}
