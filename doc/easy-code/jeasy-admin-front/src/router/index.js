import Vue from 'vue'
import iView from 'iview'
import Router from 'vue-router'
import store from '@/store'
import config from '@/config'
import { iviewAdminRouter } from './iviewAdmin'
import { adminRouter } from './app'
import { canTurnTo, getToken, setTitle, setToken, setMenuOptionMapInSessionstorage, getMenuOptionMapFromSessionstorage } from '@/libs/util'
import { oneOf } from '@/libs/tools'

Vue.use(Router)

// 所有上面定义的路由都要写在下面的routes里
export const routes = [
  ...adminRouter,
  ...iviewAdminRouter
]

const router = new Router({
  routes,
  mode: 'history'
})

const turnTo = (to, next, user) => {
  // 拉取用户信息，通过用户权限和跳转的页面的name来判断是否有权限访问;access必须是一个数组，如：['super_admin'] ['super_admin', 'admin']
  if (canTurnTo(to.name, [user.access], routes)) {
    if (config.checkUserMenu && !oneOf(to.name, config.checkUserMenuIgnoreRouters) && !store.state.loginStore.menuOptionMap[to.path]) {
      let menuOptionMap = getMenuOptionMapFromSessionstorage()
      if (Object.keys(menuOptionMap).length <= 0) {
        // 有权限，继续访问
        store.dispatch('listUserMenuOperation', {
          params: {
            menuPath: to.path
          }
        }).then(() => {
          setMenuOptionMapInSessionstorage(store.state.loginStore.menuOptionMap)
          next()
        })
      } else {
        store.state.loginStore.menuOptionMap = menuOptionMap
        if (!menuOptionMap.hasOwnProperty(to.path)) {
          // 有权限，继续访问
          store.dispatch('listUserMenuOperation', {
            params: {
              menuPath: to.path
            }
          }).then(() => {
            setMenuOptionMapInSessionstorage(store.state.loginStore.menuOptionMap)
            next()
          })
        } else {
          next()
        }
      }
    } else {
      next()
    }
  } else {
    // 无权限，重定向到401页面
    next({
      replace: true,
      name: 'error_401'
    })
  }
}

router.beforeEach((to, from, next) => {
  const token = getToken()
  if (!token && to.name !== config.loginName) {
    // 未登录且要跳转的页面不是登录页
    iView.LoadingBar.start()
    next({
      name: config.loginName // 跳转到登录页
    })
  } else if (!token && to.name === config.loginName) {
    // 未登陆且要跳转的页面是登录页
    iView.LoadingBar.start()
    next() // 跳转
  } else if (token && to.name === config.loginName) {
    // 已登录且要跳转的页面是登录页 & 跳转到home页
    if (from.name === config.homeName) {
      next(false)
    } else {
      iView.LoadingBar.start()
      next({
        name: config.homeName
      })
    }
  } else {
    // 已登录且要跳转的页面不是登录页
    if (!store.state.iviewAdminUserStore.hasGetInfo) {
      // 刷新页面时，重新获取当前登录用户信息
      store.dispatch('userInfo').then(() => {
        let user = store.state.loginStore.user
        store.commit('setToken', user.token)
        store.commit('setAvatar', user.avatar)
        store.commit('setUserName', user.name)
        store.commit('setAccess', user.access)
        store.commit('setHasGetInfo', true)
        turnTo(to, next, user)
      }).catch(() => {
        setToken('')
        next({
          name: 'login'
        })
      })
    } else {
      let user = store.state.loginStore.user
      turnTo(to, next, user)
    }
  }
})

router.afterEach((to) => {
  setTitle(to, router.app)
  iView.LoadingBar.finish()
  window.scrollTo(0, 0)
})

export default router
