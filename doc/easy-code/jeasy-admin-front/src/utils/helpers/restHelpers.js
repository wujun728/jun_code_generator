import iView from 'iview'
import auth from '../auth'

/**
 * REST 辅助函数集合
 * @type {Object}
 */
export default {
  /**
   * 成功处理
   * @param {Object} res 返回数据
   */
  successHandler (res) {
  },

  /**
   * 失败处理
   * @param {Object} res 返回数据
   */
  errorHandler (error) {
    iView.Message.error(error.message)
  },

  /**
   * 获取 Headers
   * @return {Object}
   */
  getHeaders () {
    const { manager, token } = auth.get()

    return {
      auth: window.btoa(`${manager.username}\n${token}`)
    }
  },

  /**
   * 转字符串
   * @returns {string}
   */
  whereToStr (obj) {
    let ret = {}

    Object.keys(obj).forEach(v => {
      ret[v] = {}

      if (typeof obj[v].$like !== 'undefined') {
        ret[v].$like = `%${obj[v].$like}%`
      } else {
        ret[v] = obj[v]
      }
    })

    return JSON.stringify(ret)
  }
}
