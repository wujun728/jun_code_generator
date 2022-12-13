import axios from 'axios'
import qs from 'qs'
import router from '../router'
import store from '@/store'

/**
 * 基于 axios 的 RESTful HTTP 简单封装
 */
// 请求超时配置
axios.defaults.timeout = 100000

// 添加一个响应拦截器
axios.interceptors.response.use(function (response) {
  if (response.headers['oauthstatus'] === '401') {
    // 退出登录
    store.commit('setToken', '')
    store.commit('setAccess', [])
    store.commit('setHasGetInfo', false)
    router.push({
      name: 'login'
    })
  }
  return response
})

export default class REST {
  /**
   * 构造方法
   */
  constructor () {
    /**
     * 接口基础地址
     * @type {string}
     */
    this.baseURL = ''

    /**
     * 接口版本
     * @type {string}
     */
    this.version = ''

    /**
     * 请求路劲
     * @type {string}
     */
    this.path = ''

    /**
     * Headers
     * @type {Object}
     */
    this.headers = { 'X-Requested-with': 'XMLHttpRequest' }

    /**
     * 统一错误处理
     * @type {Function}
     */
    this.errorHandler = () => {
    }

    // 支持的请求方式
    const methods = ['GET', 'POST', 'PATCH', 'PUT', 'DELETE']

    // 注册方法到 this
    methods.forEach((method) => {
      this[method] = options => this._request(method, options)
    })
  }

  /**
   * 请求
   * @param {string} method='GET' 请求方式
   * @param {Object} [options={}] 选项
   * @param {string} [options.uri=''] 资源唯一标示，一般是 ID
   * @param {Object} [options.params=null] GET 参数
   * @param {Object} [options.data=null] POST/PUT/PATCH 数据
   * @return {Object}
   */
  _request (method = 'GET', options = {}) {
    const { uri = '', params = null, data = null } = options
    let url = this.version ? `/${this.version}/${this.path}` : `/${this.path}`

    if (uri) {
      url = `${url}/${uri}`
    }

    // GET
    if (params) {
      let cleanedParams = this._cleanParams(params)
      if (cleanedParams.hasOwnProperty('body')) {
        url = url + this._objToUrl(cleanedParams)
      } else {
        url = url + this._objToUrl({ body: JSON.stringify(cleanedParams) })
      }
    }

    // POST
    let formData = {}
    if (data) {
      let cleanedData = this._cleanParams(data)
      if (cleanedData.hasOwnProperty('body')) {
        formData = qs.stringify(cleanedData)
      } else {
        formData = qs.stringify({ body: JSON.stringify(cleanedData) })
      }
    }

    if (data) {
      this.addHeaders({ 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' })
    }

    return new Promise((resolve, reject) => {
      axios({
        baseURL: this.baseURL,
        headers: this.headers,
        method,
        url,
        data: formData,
        withCredentials: true
      }).then((res) => {
        if (typeof res.data === 'object') {
          if (res.data.code === 200 && res.data.data.message === 'success') {
            resolve(res.data)
          } else if (res.data.code === 200 && res.data.data.message === '未查找到记录') {
            resolve(res.data)
          } else if (res.data.code === 406) {
            throw new Error(res.data.data.message)
          } else {
            reject(res.data)
          }
        }
      }).catch(this.errorHandler)
    })
  }

  /**
   * 清洗参数对象(清洗参数中空字符串)
   *
   * @param {Object} obj 待转化对象
   * @return {string}
   */
  _cleanParams (obj) {
    let cleanedParams = {}
    if (!obj || !Object.keys(obj).length) {
      return cleanedParams
    }

    Object.keys(obj).map((key) => {
      if (obj[key] !== undefined && obj[key] !== null && obj[key] !== '') {
        Object.assign(cleanedParams, { [key]: obj[key] })
      }
    })
    return cleanedParams
  }

  /**
   * 对象转 URL
   * @param {Object} obj 待转化对象
   * @return {string}
   */
  _objToUrl (obj) {
    if (!obj || !Object.keys(obj).length) {
      return ''
    }

    return '?' + Object.keys(obj).map((key) => {
      return `${key}=${encodeURIComponent(obj[key])}`
    }).join('&')
  }

  /**
   * 附加路径
   * @param {string} [path=''] 路径
   */
  addPath (path = '') {
    this.path = this.path === '' ? path : this.path + '/' + path
    return this
  }

  /**
   * 添加 Headers
   * @param {Object} headers Headers
   */
  addHeaders (headers) {
    this.headers = {
      ...this.headers,
      ...headers
    }

    return this
  }

  /**
   * 路径参数替换
   * @param {Object} options={} 路劲参数列表
   */
  replace (options = {}) {
    Object.keys(options).forEach((key) => {
      this.path = this.path.replace(new RegExp('{' + key + '}', 'img'), options[key])
    })

    return this
  }
}
