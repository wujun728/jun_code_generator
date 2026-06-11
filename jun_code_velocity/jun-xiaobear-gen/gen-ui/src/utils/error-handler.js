/**
 * 全局错误处理和日志系统
 */

import { ElMessage, ElNotification } from 'element-plus'
import store from '@/store'

// 错误类型枚举
export const ErrorTypes = {
  NETWORK: 'network',
  RUNTIME: 'runtime',
  RESOURCE: 'resource',
  PROMISE: 'promise',
  VUE: 'vue',
  CUSTOM: 'custom'
}

// 错误级别枚举
export const ErrorLevels = {
  LOW: 'low',
  MEDIUM: 'medium',
  HIGH: 'high',
  CRITICAL: 'critical'
}

class ErrorHandler {
  constructor() {
    this.errorQueue = []
    this.maxQueueSize = 100
    this.reportUrl = process.env.VUE_APP_ERROR_REPORT_URL
    this.isReporting = false
    
    this.init()
  }
  
  // 初始化错误处理
  init() {
    this.setupGlobalErrorHandlers()
    this.setupUnhandledRejectionHandler()
    this.setupResourceErrorHandler()
  }
  
  // 设置全局错误处理器
  setupGlobalErrorHandlers() {
    // 捕获JavaScript运行时错误
    window.addEventListener('error', (event) => {
      this.handleError({
        type: ErrorTypes.RUNTIME,
        level: ErrorLevels.HIGH,
        message: event.message,
        filename: event.filename,
        lineno: event.lineno,
        colno: event.colno,
        stack: event.error?.stack,
        timestamp: new Date().toISOString(),
        userAgent: navigator.userAgent,
        url: window.location.href
      })
    })
  }
  
  // 设置未处理的Promise拒绝处理器
  setupUnhandledRejectionHandler() {
    window.addEventListener('unhandledrejection', (event) => {
      this.handleError({
        type: ErrorTypes.PROMISE,
        level: ErrorLevels.MEDIUM,
        message: event.reason?.message || 'Unhandled Promise Rejection',
        stack: event.reason?.stack,
        timestamp: new Date().toISOString(),
        userAgent: navigator.userAgent,
        url: window.location.href
      })
    })
  }
  
  // 设置资源加载错误处理器
  setupResourceErrorHandler() {
    window.addEventListener('error', (event) => {
      if (event.target !== window) {
        this.handleError({
          type: ErrorTypes.RESOURCE,
          level: ErrorLevels.MEDIUM,
          message: `Resource load error: ${event.target.src || event.target.href}`,
          element: event.target.tagName,
          source: event.target.src || event.target.href,
          timestamp: new Date().toISOString(),
          userAgent: navigator.userAgent,
          url: window.location.href
        })
      }
    }, true)
  }
  
  // 处理错误
  handleError(errorInfo) {
    // 添加到错误队列
    this.addToQueue(errorInfo)
    
    // 根据错误级别显示不同的提示
    this.showErrorNotification(errorInfo)
    
    // 记录到控制台（开发环境）
    if (process.env.NODE_ENV === 'development') {
      console.error('Error caught:', errorInfo)
    }
    
    // 上报错误（生产环境）
    if (process.env.NODE_ENV === 'production') {
      this.reportError(errorInfo)
    }
    
    // 添加到store通知
    store.dispatch('addNotification', {
      type: 'error',
      title: '系统错误',
      message: this.getErrorMessage(errorInfo),
      level: errorInfo.level
    })
  }
  
  // 添加到错误队列
  addToQueue(errorInfo) {
    this.errorQueue.unshift(errorInfo)
    
    // 限制队列大小
    if (this.errorQueue.length > this.maxQueueSize) {
      this.errorQueue = this.errorQueue.slice(0, this.maxQueueSize)
    }
  }
  
  // 显示错误通知
  showErrorNotification(errorInfo) {
    const message = this.getErrorMessage(errorInfo)
    
    switch (errorInfo.level) {
      case ErrorLevels.CRITICAL:
        ElNotification({
          title: '严重错误',
          message,
          type: 'error',
          duration: 0, // 不自动关闭
          showClose: true
        })
        break
        
      case ErrorLevels.HIGH:
        ElMessage({
          message,
          type: 'error',
          duration: 5000,
          showClose: true
        })
        break
        
      case ErrorLevels.MEDIUM:
        if (errorInfo.type !== ErrorTypes.RESOURCE) {
          ElMessage({
            message,
            type: 'warning',
            duration: 3000
          })
        }
        break
        
      case ErrorLevels.LOW:
        // 低级别错误只记录，不显示
        break
    }
  }
  
  // 获取错误消息
  getErrorMessage(errorInfo) {
    switch (errorInfo.type) {
      case ErrorTypes.NETWORK:
        return '网络连接异常，请检查网络设置'
      case ErrorTypes.RUNTIME:
        return '程序运行异常，请刷新页面重试'
      case ErrorTypes.RESOURCE:
        return '资源加载失败，请刷新页面重试'
      case ErrorTypes.PROMISE:
        return '数据处理异常，请稍后重试'
      case ErrorTypes.VUE:
        return '页面渲染异常，请刷新页面'
      default:
        return errorInfo.message || '未知错误'
    }
  }
  
  // 上报错误
  async reportError(errorInfo) {
    if (!this.reportUrl || this.isReporting) return
    
    this.isReporting = true
    
    try {
      const reportData = {
        ...errorInfo,
        sessionId: this.getSessionId(),
        userId: this.getUserId(),
        buildVersion: process.env.VUE_APP_VERSION,
        environment: process.env.NODE_ENV
      }
      
      await fetch(this.reportUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(reportData)
      })
    } catch (e) {
      console.warn('Error reporting failed:', e)
    } finally {
      this.isReporting = false
    }
  }
  
  // 获取会话ID
  getSessionId() {
    let sessionId = sessionStorage.getItem('sessionId')
    if (!sessionId) {
      sessionId = 'session_' + Date.now() + '_' + Math.random().toString(36).substr(2, 9)
      sessionStorage.setItem('sessionId', sessionId)
    }
    return sessionId
  }
  
  // 获取用户ID
  getUserId() {
    // 这里应该从用户状态中获取
    return localStorage.getItem('userId') || 'anonymous'
  }
  
  // 手动报告错误
  reportCustomError(message, level = ErrorLevels.MEDIUM, extra = {}) {
    this.handleError({
      type: ErrorTypes.CUSTOM,
      level,
      message,
      timestamp: new Date().toISOString(),
      userAgent: navigator.userAgent,
      url: window.location.href,
      ...extra
    })
  }
  
  // 获取错误统计
  getErrorStats() {
    const stats = {
      total: this.errorQueue.length,
      byType: {},
      byLevel: {},
      recent: this.errorQueue.slice(0, 10)
    }
    
    this.errorQueue.forEach(error => {
      stats.byType[error.type] = (stats.byType[error.type] || 0) + 1
      stats.byLevel[error.level] = (stats.byLevel[error.level] || 0) + 1
    })
    
    return stats
  }
  
  // 清空错误队列
  clearErrors() {
    this.errorQueue = []
  }
  
  // 导出错误日志
  exportErrorLog() {
    const logData = {
      timestamp: new Date().toISOString(),
      userAgent: navigator.userAgent,
      url: window.location.href,
      errors: this.errorQueue
    }
    
    const blob = new Blob([JSON.stringify(logData, null, 2)], {
      type: 'application/json'
    })
    
    const url = URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = `error-log-${Date.now()}.json`
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
    URL.revokeObjectURL(url)
  }
}

// 创建全局实例
const errorHandler = new ErrorHandler()

// Vue错误处理器
export const vueErrorHandler = (err, instance, info) => {
  errorHandler.handleError({
    type: ErrorTypes.VUE,
    level: ErrorLevels.HIGH,
    message: err.message,
    stack: err.stack,
    componentInfo: info,
    timestamp: new Date().toISOString(),
    userAgent: navigator.userAgent,
    url: window.location.href
  })
}

// 导出工具函数
export const reportError = (message, level, extra) => 
  errorHandler.reportCustomError(message, level, extra)

export const getErrorStats = () => errorHandler.getErrorStats()
export const clearErrors = () => errorHandler.clearErrors()
export const exportErrorLog = () => errorHandler.exportErrorLog()

export default errorHandler
