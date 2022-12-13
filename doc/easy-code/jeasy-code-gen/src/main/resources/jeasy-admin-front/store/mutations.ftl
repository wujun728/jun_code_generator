import types from './types'

export default {
  [types.PAGE_${table.upperCamelName}] (state, payload) {
    state.items = payload.data.list
    state.total = payload.data.totalCount
    state.loading = false
  },
  [types.SHOW_${table.upperCamelName}] (state, payload) {
    state.item = payload.data.entity
  },
  [types.PAGE_${table.upperCamelName}_LOADING] (state, payload) {
    state.loading = true
  }
}
