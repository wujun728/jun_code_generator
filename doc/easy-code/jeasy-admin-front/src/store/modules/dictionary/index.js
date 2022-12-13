import getters from './getters'
import actions from './actions'
import mutations from './mutations'

export default {
  state: {
    items: [],
    item: {},
    total: 0,
    dictionarys: [],
    dictionaryTypes: [],
    loading: false
  },
  getters,
  actions,
  mutations
}
