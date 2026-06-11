import { createStore } from 'vuex'

// 应用状态管理
const store = createStore({
  state: {
    // 全局加载状态
    loading: false,
    // 当前选中的数据源
    currentDataSource: null,
    // 用户偏好设置
    userPreferences: {
      theme: 'light',
      language: 'zh-CN',
      pageSize: 50
    },
    // 代码生成配置
    generatorConfig: {
      author: 'javaxiaobear',
      packageName: 'com.javaxiaobear',
      autoRemovePre: false,
      tablePrefix: ''
    },
    // 最近使用的表
    recentTables: [],
    // 系统通知
    notifications: []
  },

  mutations: {
    SET_LOADING(state, loading) {
      state.loading = loading
    },

    SET_CURRENT_DATA_SOURCE(state, dataSource) {
      state.currentDataSource = dataSource
    },

    UPDATE_USER_PREFERENCES(state, preferences) {
      state.userPreferences = { ...state.userPreferences, ...preferences }
    },

    UPDATE_GENERATOR_CONFIG(state, config) {
      state.generatorConfig = { ...state.generatorConfig, ...config }
    },

    ADD_RECENT_TABLE(state, table) {
      const index = state.recentTables.findIndex(t => t.tableId === table.tableId)
      if (index > -1) {
        state.recentTables.splice(index, 1)
      }
      state.recentTables.unshift(table)
      if (state.recentTables.length > 10) {
        state.recentTables.pop()
      }
    },

    ADD_NOTIFICATION(state, notification) {
      state.notifications.unshift({
        id: Date.now(),
        timestamp: new Date(),
        ...notification
      })
      if (state.notifications.length > 50) {
        state.notifications.pop()
      }
    },

    REMOVE_NOTIFICATION(state, id) {
      const index = state.notifications.findIndex(n => n.id === id)
      if (index > -1) {
        state.notifications.splice(index, 1)
      }
    }
  },

  actions: {
    setLoading({ commit }, loading) {
      commit('SET_LOADING', loading)
    },

    setCurrentDataSource({ commit }, dataSource) {
      commit('SET_CURRENT_DATA_SOURCE', dataSource)
    },

    updateUserPreferences({ commit }, preferences) {
      commit('UPDATE_USER_PREFERENCES', preferences)
      // 持久化到localStorage
      localStorage.setItem('userPreferences', JSON.stringify(preferences))
    },

    updateGeneratorConfig({ commit }, config) {
      commit('UPDATE_GENERATOR_CONFIG', config)
      localStorage.setItem('generatorConfig', JSON.stringify(config))
    },

    addRecentTable({ commit }, table) {
      commit('ADD_RECENT_TABLE', table)
      // 持久化到localStorage
      const recentTables = JSON.parse(localStorage.getItem('recentTables') || '[]')
      const index = recentTables.findIndex(t => t.tableId === table.tableId)
      if (index > -1) {
        recentTables.splice(index, 1)
      }
      recentTables.unshift(table)
      if (recentTables.length > 10) {
        recentTables.pop()
      }
      localStorage.setItem('recentTables', JSON.stringify(recentTables))
    },

    addNotification({ commit }, notification) {
      commit('ADD_NOTIFICATION', notification)
    },

    removeNotification({ commit }, id) {
      commit('REMOVE_NOTIFICATION', id)
    },

    // 初始化应用状态
    initializeApp({ commit }) {
      // 从localStorage恢复用户偏好
      const userPreferences = JSON.parse(localStorage.getItem('userPreferences') || '{}')
      if (Object.keys(userPreferences).length > 0) {
        commit('UPDATE_USER_PREFERENCES', userPreferences)
      }

      // 从localStorage恢复生成器配置
      const generatorConfig = JSON.parse(localStorage.getItem('generatorConfig') || '{}')
      if (Object.keys(generatorConfig).length > 0) {
        commit('UPDATE_GENERATOR_CONFIG', generatorConfig)
      }

      // 从localStorage恢复最近使用的表
      const recentTables = JSON.parse(localStorage.getItem('recentTables') || '[]')
      recentTables.forEach(table => {
        commit('ADD_RECENT_TABLE', table)
      })
    }
  },

  getters: {
    isLoading: state => state.loading,
    currentDataSource: state => state.currentDataSource,
    userPreferences: state => state.userPreferences,
    generatorConfig: state => state.generatorConfig,
    recentTables: state => state.recentTables,
    notifications: state => state.notifications,
    unreadNotifications: state => state.notifications.filter(n => !n.read)
  }
})

export default store
