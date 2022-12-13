import { localRead } from '@/libs/util'
import getters from './getters'
import actions from './actions'
import mutations from './mutations'

export default {
  state: {
    breadCrumbList: [],
    tagNavList: [],
    homeRoute: {},
    local: localRead('local'),
    errorList: [],
    hasReadErrorPage: false
  },
  getters,
  actions,
  mutations
}
