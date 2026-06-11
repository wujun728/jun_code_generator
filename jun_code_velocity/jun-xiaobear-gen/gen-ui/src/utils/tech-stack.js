/**
 * 技术栈工具类
 * 统一管理前后端技术栈的选项和展示逻辑
 */

// 后端技术栈配置
export const BACKEND_OPTIONS = [
  { label: 'MyBatis', value: '0', name: 'mybatis' },
  { label: 'MyBatis-Plus', value: '1', name: 'mybatis-plus' }
]

// 前端技术栈配置
export const FRONTEND_OPTIONS = [
  { label: 'Element UI Vue2', value: '0', name: 'element-ui-vue2' },
  { label: 'Ant Design Vue2', value: '1', name: 'antdv-vue2' },
  { label: 'HTML', value: '2', name: 'html' },
  { label: 'Element UI Vue3', value: '3', name: 'element-ui-vue3' }
]

// 技术栈工具类
export class TechStackUtils {
  /**
   * 根据值获取后端技术栈标签
   * @param {string} value - 技术栈值
   * @returns {string} 技术栈标签
   */
  static getBackendLabel(value) {
    const option = BACKEND_OPTIONS.find(item => item.value === value)
    return option ? option.label : '未知'
  }

  /**
   * 根据值获取前端技术栈标签
   * @param {string} value - 技术栈值
   * @returns {string} 技术栈标签
   */
  static getFrontendLabel(value) {
    const option = FRONTEND_OPTIONS.find(item => item.value === value)
    return option ? option.label : '未知'
  }

  /**
   * 根据值获取后端技术栈名称
   * @param {string} value - 技术栈值
   * @returns {string} 技术栈名称
   */
  static getBackendName(value) {
    const option = BACKEND_OPTIONS.find(item => item.value === value)
    return option ? option.name : 'unknown'
  }

  /**
   * 根据值获取前端技术栈名称
   * @param {string} value - 技术栈值
   * @returns {string} 技术栈名称
   */
  static getFrontendName(value) {
    const option = FRONTEND_OPTIONS.find(item => item.value === value)
    return option ? option.name : 'unknown'
  }

  /**
   * 获取技术栈标签的颜色
   * @param {string} type - 技术栈类型 ('backend' | 'frontend')
   * @param {string} value - 技术栈值
   * @returns {string} 颜色类名
   */
  static getTechStackColor(type, value) {
    if (type === 'backend') {
      return value === '1' ? 'success' : 'primary' // MyBatis-Plus 为绿色，MyBatis 为蓝色
    } else if (type === 'frontend') {
      const colors = {
        '0': 'primary',    // Element UI Vue2 - 蓝色
        '1': 'warning',    // Ant Design Vue2 - 橙色
        '2': 'info',       // HTML - 灰色
        '3': 'success'     // Element UI Vue3 - 绿色
      }
      return colors[value] || 'info'
    }
    return 'info'
  }

  /**
   * 获取技术栈图标
   * @param {string} type - 技术栈类型 ('backend' | 'frontend')
   * @param {string} value - 技术栈值
   * @returns {string} 图标名称
   */
  static getTechStackIcon(type, value) {
    if (type === 'backend') {
      return value === '1' ? 'Connection' : 'Link' // MyBatis-Plus 用 Connection，MyBatis 用 Link
    } else if (type === 'frontend') {
      const icons = {
        '0': 'Grid',       // Element UI Vue2
        '1': 'Menu',       // Ant Design Vue2
        '2': 'Document',   // HTML
        '3': 'View'        // Element UI Vue3
      }
      return icons[value] || 'Document'
    }
    return 'Document'
  }

  /**
   * 格式化技术栈显示文本
   * @param {string} backendValue - 后端技术栈值
   * @param {string} frontendValue - 前端技术栈值
   * @returns {string} 格式化后的显示文本
   */
  static formatTechStack(backendValue, frontendValue) {
    const backend = this.getBackendLabel(backendValue)
    const frontend = this.getFrontendLabel(frontendValue)
    return `${backend} + ${frontend}`
  }

  /**
   * 检查技术栈是否为默认配置
   * @param {string} backendValue - 后端技术栈值
   * @param {string} frontendValue - 前端技术栈值
   * @returns {boolean} 是否为默认配置
   */
  static isDefaultTechStack(backendValue, frontendValue) {
    return backendValue === '1' && frontendValue === '0' // MyBatis-Plus + Element UI Vue2
  }
}

// 默认导出工具类
export default TechStackUtils
