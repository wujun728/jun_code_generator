/**
 * 性能监控工具
 * 用于监控应用性能和用户体验指标
 */

class PerformanceMonitor {
  constructor() {
    this.metrics = new Map()
    this.observers = []
    this.isSupported = this.checkSupport()
    
    if (this.isSupported) {
      this.init()
    }
  }
  
  // 检查浏览器支持
  checkSupport() {
    return !!(
      window.performance &&
      window.performance.mark &&
      window.performance.measure &&
      window.PerformanceObserver
    )
  }
  
  // 初始化性能监控
  init() {
    this.observeNavigationTiming()
    this.observeResourceTiming()
    this.observeLongTasks()
    this.observeLayoutShift()
    this.observeFirstPaint()
  }
  
  // 监控导航时间
  observeNavigationTiming() {
    if (!window.PerformanceObserver) return
    
    const observer = new PerformanceObserver((list) => {
      const entries = list.getEntries()
      entries.forEach(entry => {
        if (entry.entryType === 'navigation') {
          this.recordNavigationMetrics(entry)
        }
      })
    })
    
    observer.observe({ entryTypes: ['navigation'] })
    this.observers.push(observer)
  }
  
  // 监控资源加载时间
  observeResourceTiming() {
    if (!window.PerformanceObserver) return
    
    const observer = new PerformanceObserver((list) => {
      const entries = list.getEntries()
      entries.forEach(entry => {
        if (entry.entryType === 'resource') {
          this.recordResourceMetrics(entry)
        }
      })
    })
    
    observer.observe({ entryTypes: ['resource'] })
    this.observers.push(observer)
  }
  
  // 监控长任务
  observeLongTasks() {
    if (!window.PerformanceObserver) return
    
    try {
      const observer = new PerformanceObserver((list) => {
        const entries = list.getEntries()
        entries.forEach(entry => {
          if (entry.entryType === 'longtask') {
            this.recordLongTask(entry)
          }
        })
      })
      
      observer.observe({ entryTypes: ['longtask'] })
      this.observers.push(observer)
    } catch (e) {
      // longtask 可能不被支持
      console.warn('Long task monitoring not supported')
    }
  }
  
  // 监控布局偏移
  observeLayoutShift() {
    if (!window.PerformanceObserver) return
    
    try {
      const observer = new PerformanceObserver((list) => {
        const entries = list.getEntries()
        entries.forEach(entry => {
          if (entry.entryType === 'layout-shift' && !entry.hadRecentInput) {
            this.recordLayoutShift(entry)
          }
        })
      })
      
      observer.observe({ entryTypes: ['layout-shift'] })
      this.observers.push(observer)
    } catch (e) {
      console.warn('Layout shift monitoring not supported')
    }
  }
  
  // 监控首次绘制
  observeFirstPaint() {
    if (!window.PerformanceObserver) return
    
    const observer = new PerformanceObserver((list) => {
      const entries = list.getEntries()
      entries.forEach(entry => {
        if (entry.entryType === 'paint') {
          this.recordPaintMetrics(entry)
        }
      })
    })
    
    observer.observe({ entryTypes: ['paint'] })
    this.observers.push(observer)
  }
  
  // 记录导航指标
  recordNavigationMetrics(entry) {
    const metrics = {
      dns: entry.domainLookupEnd - entry.domainLookupStart,
      tcp: entry.connectEnd - entry.connectStart,
      ssl: entry.secureConnectionStart > 0 ? entry.connectEnd - entry.secureConnectionStart : 0,
      ttfb: entry.responseStart - entry.requestStart,
      download: entry.responseEnd - entry.responseStart,
      domParse: entry.domContentLoadedEventStart - entry.responseEnd,
      domReady: entry.domContentLoadedEventEnd - entry.navigationStart,
      loadComplete: entry.loadEventEnd - entry.navigationStart
    }
    
    this.metrics.set('navigation', metrics)
    this.reportMetrics('navigation', metrics)
  }
  
  // 记录资源指标
  recordResourceMetrics(entry) {
    const resourceMetrics = this.metrics.get('resources') || []
    resourceMetrics.push({
      name: entry.name,
      type: this.getResourceType(entry.name),
      duration: entry.duration,
      size: entry.transferSize || 0,
      cached: entry.transferSize === 0 && entry.decodedBodySize > 0
    })
    
    this.metrics.set('resources', resourceMetrics)
  }
  
  // 记录长任务
  recordLongTask(entry) {
    const longTasks = this.metrics.get('longTasks') || []
    longTasks.push({
      duration: entry.duration,
      startTime: entry.startTime,
      attribution: entry.attribution
    })
    
    this.metrics.set('longTasks', longTasks)
    
    if (entry.duration > 100) {
      console.warn(`Long task detected: ${entry.duration}ms`)
    }
  }
  
  // 记录布局偏移
  recordLayoutShift(entry) {
    const layoutShifts = this.metrics.get('layoutShifts') || []
    layoutShifts.push({
      value: entry.value,
      startTime: entry.startTime,
      sources: entry.sources
    })
    
    this.metrics.set('layoutShifts', layoutShifts)
    
    const cls = layoutShifts.reduce((sum, shift) => sum + shift.value, 0)
    if (cls > 0.1) {
      console.warn(`High Cumulative Layout Shift: ${cls}`)
    }
  }
  
  // 记录绘制指标
  recordPaintMetrics(entry) {
    const paintMetrics = this.metrics.get('paint') || {}
    paintMetrics[entry.name] = entry.startTime
    this.metrics.set('paint', paintMetrics)
  }
  
  // 获取资源类型
  getResourceType(url) {
    if (url.includes('.js')) return 'script'
    if (url.includes('.css')) return 'stylesheet'
    if (url.match(/\.(png|jpg|jpeg|gif|svg|webp)$/)) return 'image'
    if (url.includes('.woff') || url.includes('.ttf')) return 'font'
    return 'other'
  }
  
  // 开始性能标记
  mark(name) {
    if (this.isSupported) {
      performance.mark(name)
    }
  }
  
  // 测量性能
  measure(name, startMark, endMark) {
    if (this.isSupported) {
      try {
        performance.measure(name, startMark, endMark)
        const measure = performance.getEntriesByName(name, 'measure')[0]
        return measure ? measure.duration : 0
      } catch (e) {
        console.warn(`Performance measure failed: ${name}`)
        return 0
      }
    }
    return 0
  }
  
  // 获取所有指标
  getMetrics() {
    return Object.fromEntries(this.metrics)
  }
  
  // 获取性能评分
  getPerformanceScore() {
    const navigation = this.metrics.get('navigation')
    const longTasks = this.metrics.get('longTasks') || []
    const layoutShifts = this.metrics.get('layoutShifts') || []
    
    if (!navigation) return null
    
    let score = 100
    
    // 首次内容绘制评分
    if (navigation.domReady > 3000) score -= 20
    else if (navigation.domReady > 1500) score -= 10
    
    // 长任务评分
    const longTaskCount = longTasks.filter(task => task.duration > 50).length
    score -= Math.min(longTaskCount * 5, 30)
    
    // 布局偏移评分
    const cls = layoutShifts.reduce((sum, shift) => sum + shift.value, 0)
    if (cls > 0.25) score -= 30
    else if (cls > 0.1) score -= 15
    
    return Math.max(score, 0)
  }
  
  // 报告指标
  reportMetrics(type, data) {
    // 这里可以发送到分析服务
    if (process.env.NODE_ENV === 'development') {
      console.log(`Performance ${type}:`, data)
    }
  }
  
  // 清理观察者
  disconnect() {
    this.observers.forEach(observer => observer.disconnect())
    this.observers = []
  }
}

// 创建全局实例
const performanceMonitor = new PerformanceMonitor()

// 导出工具函数
export const markStart = (name) => performanceMonitor.mark(`${name}-start`)
export const markEnd = (name) => performanceMonitor.mark(`${name}-end`)
export const measureTime = (name) => performanceMonitor.measure(name, `${name}-start`, `${name}-end`)

export const getMetrics = () => performanceMonitor.getMetrics()
export const getPerformanceScore = () => performanceMonitor.getPerformanceScore()

export default performanceMonitor
