import { getNewIconName, isOldElementIcon } from './icon-mapping'

/**
 * 图标工具类
 * 用于处理 Element UI 到 Element Plus 的图标迁移
 */
export class IconHelper {
  /**
   * 获取图标组件名
   * @param {string} iconName - 图标名称
   * @returns {string} - Element Plus 图标组件名
   */
  static getIconComponent(iconName) {
    if (!iconName) return 'Document'
    
    if (isOldElementIcon(iconName)) {
      const newIconName = getNewIconName(iconName)
      console.log(`[IconHelper] Converting: ${iconName} -> ${newIconName}`)
      return newIconName
    }
    
    return iconName
  }

  /**
   * 批量转换图标映射
   * @param {Object} iconMap - 图标映射对象
   * @returns {Object} - 转换后的图标映射
   */
  static convertIconMap(iconMap) {
    const convertedMap = {}
    
    Object.keys(iconMap).forEach(key => {
      const oldIconName = iconMap[key]
      convertedMap[key] = this.getIconComponent(oldIconName)
    })
    
    return convertedMap
  }

  /**
   * 数据库类型图标映射
   */
  static getDbTypeIconComponent(type) {
    const iconMap = {
      '0': 'Coin',        // MySQL
      '1': 'DataLine',    // PostgreSQL  
      '2': 'Platform',    // Oracle
      '3': 'DataBoard',   // SQL Server
      '4': 'TrendCharts', // SQLite
      '5': 'Files',       // MariaDB
      '6': 'Connection',  // H2
      '7': 'Grid'         // 其他
    }
    return iconMap[type] || 'Grid'
  }

  /**
   * 文件格式图标映射
   */
  static getFormatIconComponent(format) {
    const iconMap = {
      'EXCEL': 'Grid',
      'WORD': 'Document', 
      'PDF': 'DocumentCopy',
      'HTML': 'Monitor',
      'JSON': 'Document',
      'XML': 'Document',
      'CSV': 'Grid'
    }
    return iconMap[format] || 'Document'
  }

  /**
   * 操作类型图标映射
   */
  static getActionIconComponent(action) {
    const iconMap = {
      'add': 'Plus',
      'edit': 'Edit',
      'delete': 'Delete',
      'view': 'View',
      'download': 'Download',
      'upload': 'Upload',
      'search': 'Search',
      'refresh': 'Refresh',
      'setting': 'Setting',
      'copy': 'DocumentCopy',
      'export': 'Download',
      'import': 'Upload'
    }
    return iconMap[action] || 'Document'
  }

  /**
   * 状态图标映射
   */
  static getStatusIconComponent(status) {
    const iconMap = {
      'success': 'SuccessFilled',
      'error': 'CircleCloseFilled', 
      'warning': 'WarningFilled',
      'info': 'InfoFilled',
      'loading': 'Loading'
    }
    return iconMap[status] || 'InfoFilled'
  }
}

/**
 * 便捷函数导出
 */
export const getIconComponent = IconHelper.getIconComponent
export const getDbTypeIconComponent = IconHelper.getDbTypeIconComponent
export const getFormatIconComponent = IconHelper.getFormatIconComponent
export const getActionIconComponent = IconHelper.getActionIconComponent
export const getStatusIconComponent = IconHelper.getStatusIconComponent

export default IconHelper
