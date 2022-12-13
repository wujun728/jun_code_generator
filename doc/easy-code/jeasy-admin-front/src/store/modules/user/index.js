import getters from './getters'
import actions from './actions'
import mutations from './mutations'

export default {
  state: {
    items: [],
    item: {},
    total: 0,
    loading: false,
    roleItems: [],
    roleItem: {},
    roleTotal: 0,
    roleLoading: false,
    userRoles: [],
    userOrganizationItems: []
  },
  getters,
  actions,
  mutations
}
