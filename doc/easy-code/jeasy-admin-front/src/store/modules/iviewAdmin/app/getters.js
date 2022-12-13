import { getMenuByRouter } from '@/libs/util'
import { routes } from '@/router'

export default {
  menuList: (state, getters, rootState) => getMenuByRouter(routes, rootState.iviewAdminUserStore.access, rootState.loginStore.urlMenuMap),
  errorCount: state => state.errorList.length
}
