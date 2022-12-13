import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import actions from './actions'
import mutations from './mutations'
import dictionary from './modules/dictionary'
import user from './modules/user'
import role from './modules/role'
import resource from './modules/resource'
import organization from './modules/organization'
import log from './modules/log'
import fileAttach from './modules/fileAttach'
import login from './modules/login'
import iviewAdminApp from './modules/iviewAdmin/app'
import iviewAdminUser from './modules/iviewAdmin/user'
import iviewAdminData from './modules/iviewAdmin/data'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    language: 'zh-CN'
  },
  getters,
  actions,
  mutations,
  modules: {
    userStore: user,
    roleStore: role,
    resourceStore: resource,
    organizationStore: organization,
    logStore: log,
    fileAttachStore: fileAttach,
    dictionaryStore: dictionary,
    loginStore: login,
    iviewAdminAppStore: iviewAdminApp,
    iviewAdminUserStore: iviewAdminUser,
    iviewAdminDataStore: iviewAdminData
  }
})
