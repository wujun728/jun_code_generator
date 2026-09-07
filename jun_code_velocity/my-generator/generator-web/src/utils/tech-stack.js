/**
 * 技术栈工具类
 * 统一管理前后端技术栈的选项和展示逻辑
 */

export const BACKEND_OPTIONS = [
  { label: 'MyBatis', value: '0', name: 'mybatis' },
  { label: 'MyBatis-Plus', value: '1', name: 'mybatis-plus' }
]

export const FRONTEND_OPTIONS = [
  { label: 'Element UI Vue2', value: '0', name: 'element-ui-vue2' },
  { label: 'Ant Design Vue2', value: '1', name: 'antdv-vue2' },
  { label: 'HTML', value: '2', name: 'html' },
  { label: 'Element UI Vue3', value: '3', name: 'element-ui-vue3' }
]

export class TechStackUtils {
  static getBackendLabel(value) {
    const option = BACKEND_OPTIONS.find(item => item.value === value)
    return option ? option.label : '未知'
  }

  static getFrontendLabel(value) {
    const option = FRONTEND_OPTIONS.find(item => item.value === value)
    return option ? option.label : '未知'
  }

  static getBackendName(value) {
    const option = BACKEND_OPTIONS.find(item => item.value === value)
    return option ? option.name : 'unknown'
  }

  static getFrontendName(value) {
    const option = FRONTEND_OPTIONS.find(item => item.value === value)
    return option ? option.name : 'unknown'
  }

  static getTechStackColor(type, value) {
    if (type === 'backend') {
      return value === '1' ? 'success' : 'primary'
    } else if (type === 'frontend') {
      const colors = {
        '0': 'primary',
        '1': 'warning',
        '2': 'info',
        '3': 'success'
      }
      return colors[value] || 'info'
    }
    return 'info'
  }

  static getTechStackIcon(type, value) {
    if (type === 'backend') {
      return value === '1' ? 'el-icon-connection' : 'el-icon-link'
    } else if (type === 'frontend') {
      const icons = {
        '0': 'el-icon-grid',
        '1': 'el-icon-menu',
        '2': 'el-icon-document',
        '3': 'el-icon-view'
      }
      return icons[value] || 'el-icon-document'
    }
    return 'el-icon-document'
  }

  static formatTechStack(backendValue, frontendValue) {
    const backend = this.getBackendLabel(backendValue)
    const frontend = this.getFrontendLabel(frontendValue)
    return `${backend} + ${frontend}`
  }

  static isDefaultTechStack(backendValue, frontendValue) {
    return backendValue === '1' && frontendValue === '0'
  }
}

export default TechStackUtils