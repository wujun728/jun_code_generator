import getters from './getters'
import actions from './actions'
import mutations from './mutations'

export default {
  state: {
    user: null,
    items: [],
    menuOptionMap: {},
    urlMenuMap: {}
  },
  getters,
  actions,
  mutations
}
