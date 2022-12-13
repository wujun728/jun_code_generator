import types from './types'

export default {
  [types.LIST_DICTIONARY] (state, payload) {
    state.dictionarys = payload.data.list
  },
  [types.PAGE_DICTIONARY] (state, payload) {
    state.items = payload.data.list
    state.total = payload.data.totalCount
    state.loading = false
  },
  [types.SHOW_DICTIONARY] (state, payload) {
    state.item = payload.data.entity
  },
  [types.LIST_DICTIONARY_TYPE] (state, payload) {
    state.dictionaryTypes = payload.data.list
  },
  [types.PAGE_DICTIONARY_LOADING] (state, payload) {
    state.loading = true
  }
}
