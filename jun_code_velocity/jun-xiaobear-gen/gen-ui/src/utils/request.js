import axios from 'axios'
import { ElLoading, ElMessage, ElMessageBox } from 'element-plus'
import { nextTick } from 'vue'
import { router } from '@/router'
import store from '@/store'

// 请求配置
const REQUEST_CONFIG = {
  baseURL: process.env.VUE_APP_BASE_API || '/',
  timeout: 60000,
  withCredentials: true,
  maxRetries: 3,
  retryDelay: 1000
}

// 创建axios实例
const service = axios.create(REQUEST_CONFIG)

// 请求队列管理
let requestQueue = new Map()
let requestCountMap = new Map() // 记录请求次数和时间
let loadingInstance = null
let loadingCount = 0

// 生成请求唯一标识
function generateRequestKey(config) {
  const { method, url, params, data } = config
  return [method, url, JSON.stringify(params), JSON.stringify(data)].join('&')
}

// 检查请求频率限制
function checkRequestLimit(requestKey) {
  const now = Date.now()
  const requestInfo = requestCountMap.get(requestKey)

  if (!requestInfo) {
    // 第一次请求
    requestCountMap.set(requestKey, { count: 1, firstTime: now })
    return true
  }

  // 检查是否在1秒内
  if (now - requestInfo.firstTime < 1000) {
    if (requestInfo.count >= 3) {
      // 1秒内超过3次请求
      return false
    } else {
      // 增加计数
      requestInfo.count++
      return true
    }
  } else {
    // 超过1秒，重置计数
    requestCountMap.set(requestKey, { count: 1, firstTime: now })
    return true
  }
}

// 显示全局加载
function showLoading(text = '加载中...') {
  if (loadingCount === 0) {
    loadingInstance = ElLoading.service({
      lock: true,
      text,
      background: 'rgba(0, 0, 0, 0.7)'
    })
    store.dispatch('setLoading', true)
  }
  loadingCount++
}

// 隐藏全局加载
function hideLoading() {
  loadingCount--
  if (loadingCount <= 0) {
    loadingCount = 0
    if (loadingInstance) {
      loadingInstance.close()
      loadingInstance = null
    }
    store.dispatch('setLoading', false)
  }
}

// 请求重试函数
function retryRequest(config, retryCount = 0) {
  return new Promise((resolve, reject) => {
    if (retryCount >= REQUEST_CONFIG.maxRetries) {
      reject(new Error('请求重试次数超限'))
      return
    }

    setTimeout(() => {
      service.request(config)
        .then(resolve)
        .catch(error => {
          if (error.code === 'ECONNABORTED' || error.message.includes('timeout')) {
            retryRequest(config, retryCount + 1).then(resolve).catch(reject)
          } else {
            reject(error)
          }
        })
    }, REQUEST_CONFIG.retryDelay * Math.pow(2, retryCount))
  })
}

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 检查请求频率限制（1秒钟最多3次）
    const requestKey = generateRequestKey(config)
    // if (!checkRequestLimit(requestKey)) {
    //   const source = axios.CancelToken.source()
    //   config.cancelToken = source.token
    //   source.cancel('请求过于频繁，请稍后再试')
    //   return Promise.reject(new Error('请求过于频繁，请稍后再试'))
    // }
    //
    // // 防止完全相同的请求同时发送
    // if (requestQueue.has(requestKey)) {
    //   const source = axios.CancelToken.source()
    //   config.cancelToken = source.token
    //   source.cancel('重复请求已取消')
    // } else {
    //   requestQueue.set(requestKey, config)
    // }

    // 添加请求头
    config.headers['request-ajax'] = true
    config.headers['X-Requested-With'] = 'XMLHttpRequest'

    // 添加时间戳防止缓存
    if (config.method === 'get') {
      config.params = {
        ...config.params,
        _t: Date.now()
      }
    }

    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    // 移除请求队列中的请求
    const requestKey = generateRequestKey(response.config)
    requestQueue.delete(requestKey)

    const { data, config } = response

    // 处理不同的响应状态码
    if (data.code === 200 || data.code === 0) {
      return Promise.resolve(data)
    } else if (data.code === 401) {
      ElMessage.error('登录已过期，请重新登录')
      router.push({ path: '/login' })
      return Promise.reject(data)
    } else if (data.code === 403) {
      ElMessage.error('权限不足')
      return Promise.reject(data)
    } else if (data.code === 500) {
      ElMessage.error(data.msg || '服务器内部错误')
      return Promise.reject(data)
    } else if (data.code === 501) {
      ElMessage.error(data.msg || '服务不可用')
      return Promise.reject(data)
    } else if (data.code === 502) {
      ElMessage.error('网关错误')
      router.push({ path: '/error' })
      return Promise.reject(data)
    } else {
      ElMessage.error(data.msg || '请求失败')
      return Promise.reject(data)
    }
  },
  error => {
    // 移除请求队列中的请求
    if (error.config) {
      const requestKey = generateRequestKey(error.config)
      requestQueue.delete(requestKey)
    }

    let message = '网络错误'

    if (error.code === 'ECONNABORTED') {
      message = '请求超时，请稍后重试'
    } else if (error.response) {
      const { status } = error.response
      switch (status) {
        case 400:
          message = '请求参数错误'
          break
        case 401:
          message = '未授权，请重新登录'
          router.push({ path: '/login' })
          break
        case 403:
          message = '权限不足'
          break
        case 404:
          message = '请求的资源不存在'
          break
        case 500:
          message = '服务器内部错误'
          break
        case 502:
          message = '网关错误'
          break
        case 503:
          message = '服务不可用'
          break
        default:
          message = `连接错误${status}`
      }
    } else if (error.message) {
      message = error.message
    }

    ElMessage.error(message)
    return Promise.reject(error)
  }
)

// 基础请求函数
const request = function (showLoadingTip, config) {
  let loading = null

  if (showLoadingTip) {
    loading = ElLoading.service({
      lock: false,
      text: '正在加载中...',
      background: 'rgba(0, 0, 0, 0.5)'
    })
  }

  return service.request(config)
    .then(response => {
      if (loading) {
        loading.close()
      }
      return response
    })
    .catch(error => {
      if (loading) {
        loading.close()
      }

      // 如果是网络错误，尝试重试
      if (error.code === 'ECONNABORTED' || error.message.includes('timeout')) {
        return retryRequest(config)
      }

      return Promise.reject(error)
    })
}

// HTTP方法封装
const post = function (url, params, options = {}) {
  const config = {
    url,
    method: 'post',
    data: params,
    headers: {
      'Content-Type': 'application/json',
      ...options.headers
    },
    ...options
  }
  return request(options.showLoading || false, config)
}

const postWithLoadTip = function (url, params, options = {}) {
  return post(url, params, { ...options, showLoading: true })
}

const postWithOutLoadTip = function (url, params, options = {}) {
  return post(url, params, { ...options, showLoading: false })
}

const get = function (url, params, options = {}) {
  const config = {
    url,
    method: 'get',
    params,
    headers: {
      ...options.headers
    },
    ...options
  }
  return request(options.showLoading || false, config)
}

const put = function (url, params, options = {}) {
  const config = {
    url,
    method: 'put',
    data: params,
    headers: {
      'Content-Type': 'application/json',
      ...options.headers
    },
    ...options
  }
  return request(options.showLoading || false, config)
}

const del = function (url, params, options = {}) {
  const config = {
    url,
    method: 'delete',
    params,
    headers: {
      ...options.headers
    },
    ...options
  }
  return request(options.showLoading || false, config)
}

const form = function (url, params, options = {}) {
  const config = {
    url,
    method: 'post',
    data: params,
    headers: {
      'Content-Type': 'multipart/form-data',
      ...options.headers
    },
    ...options
  }
  return request(options.showLoading || false, config)
}

const postForm = function (url, params, options = {}) {
  const config = {
    url,
    method: 'post',
    data: params,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
      ...options.headers
    },
    ...options
  }
  return request(options.showLoading || false, config)
}

// 文件上传
const upload = function (url, file, options = {}) {
  const formData = new FormData()
  formData.append('file', file)

  if (options.data) {
    Object.keys(options.data).forEach(key => {
      formData.append(key, options.data[key])
    })
  }

  const config = {
    url,
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data',
      ...options.headers
    },
    onUploadProgress: options.onProgress,
    ...options
  }
  return request(options.showLoading !== false, config)
}

// 文件下载
const download = function (url, params, filename, options = {}) {
  const config = {
    url,
    method: 'get',
    params,
    responseType: 'blob',
    headers: {
      ...options.headers
    },
    ...options
  }

  return request(options.showLoading !== false, config)
    .then(response => {
      const blob = new Blob([response.data])
      const downloadUrl = window.URL.createObjectURL(blob)
      const link = document.createElement('a')
      link.href = downloadUrl
      link.download = filename || 'download'
      document.body.appendChild(link)
      link.click()
      document.body.removeChild(link)
      window.URL.revokeObjectURL(downloadUrl)
      return response
    })
}

// 批量请求
const batch = function (requests, options = {}) {
  const { concurrent = 5, showLoading = true } = options

  if (showLoading) {
    showLoading('批量处理中...')
  }

  const executeRequests = async () => {
    const results = []
    for (let i = 0; i < requests.length; i += concurrent) {
      const batch = requests.slice(i, i + concurrent)
      const batchResults = await Promise.allSettled(
        batch.map(req => service.request(req))
      )
      results.push(...batchResults)
    }
    return results
  }

  return executeRequests()
    .then(results => {
      if (showLoading) {
        hideLoading()
      }
      return results
    })
    .catch(error => {
      if (showLoading) {
        hideLoading()
      }
      throw error
    })
}

// 导出所有方法
export {
  service,
  request,
  post,
  postWithLoadTip,
  postWithOutLoadTip,
  get,
  put,
  del,
  form,
  postForm,
  upload,
  download,
  batch,
  showLoading,
  hideLoading
}

// 默认导出service实例
export default service
