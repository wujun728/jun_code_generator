import types from './types'

export default {
  [types.LOGIN] (state, payload) {
    state.user = payload.entity
  },

  [types.USER_INFO] (state, payload) {
    state.user = payload.entity
  },

  [types.LIST_USER_MENU] (state, payload) {
    state.items = payload.data
    for (let item of state.items) {
      if (item.childrens) {
        for (let subItem of item.childrens) {
          state.urlMenuMap[subItem.url] = item.code
        }
      }
    }
  },

  [types.LIST_USER_MENU_OPTION] (state, payload) {
    state.menuOptionMap[payload.menu] = payload.data.map(function (value, key, arr) {
      return value.code
    })
  },

  [types.CLEAN_MENU_OPTION_MAP] (state) {
    state.menuOptionMap = {}
  }
}
