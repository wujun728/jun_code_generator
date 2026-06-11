/**
 * 图标修复工具
 * 用于批量修复 Element UI 到 Element Plus 的图标问题
 */

// 常用图标映射
export const commonIconMapping = {
  // 操作图标
  'Plus': 'Plus',
  'Edit': 'Edit', 
  'Delete': 'Delete',
  'Search': 'Search',
  'Refresh': 'Refresh',
  'Setting': 'Setting',
  'View': 'View',
  'Download': 'Download',
  'Upload': 'Upload',
  'Check': 'Check',
  'Close': 'Close',
  'More': 'More',
  'Link': 'Link',
  
  // 导航图标
  'ArrowRight': 'ArrowRight',
  'ArrowLeft': 'ArrowLeft', 
  'ArrowUp': 'ArrowUp',
  'ArrowDown': 'ArrowDown',
  'Back': 'Back',
  'Top': 'Top',
  'Bottom': 'Bottom',
  
  // 状态图标
  'SuccessFilled': 'SuccessFilled',
  'WarningFilled': 'WarningFilled',
  'CircleCloseFilled': 'CircleCloseFilled',
  'InfoFilled': 'InfoFilled',
  'Loading': 'Loading',
  
  // 文件图标
  'Document': 'Document',
  'DocumentAdd': 'DocumentAdd',
  'DocumentCopy': 'DocumentCopy',
  'DocumentDelete': 'DocumentDelete',
  'Files': 'Files',
  'Folder': 'Folder',
  'FolderOpened': 'FolderOpened',
  
  // 系统图标
  'House': 'House',
  'User': 'User',
  'UserFilled': 'UserFilled',
  'Lock': 'Lock',
  'Unlock': 'Unlock',
  'Key': 'Key',
  'Bell': 'Bell',
  'Message': 'Message',
  
  // 工具图标
  'MagicStick': 'MagicStick',
  'Tools': 'Tools',
  'Cpu': 'Cpu',
  'Monitor': 'Monitor',
  'Database': 'Database',
  'Connection': 'Connection',
  'Grid': 'Grid',
  'Platform': 'Platform',
  
  // 媒体图标
  'VideoPlay': 'VideoPlay',
  'VideoPause': 'VideoPause',
  'Camera': 'Camera',
  'Picture': 'Picture',
  'PictureFilled': 'PictureFilled',
  
  // 商业图标
  'Coin': 'Coin',
  'Money': 'Money',
  'Shop': 'Shop',
  'ShoppingCart': 'ShoppingCart',
  'Present': 'Present',
  
  // 时间图标
  'Clock': 'Clock',
  'Timer': 'Timer',
  'Calendar': 'Calendar',
  'Stopwatch': 'Stopwatch',
  
  // 天气图标
  'Sunny': 'Sunny',
  'Moon': 'Moon',
  'PartlyCloudy': 'PartlyCloudy',
  'Lightning': 'Lightning',
  
  // 数据图标
  'DataBoard': 'DataBoard',
  'DataLine': 'DataLine',
  'DataAnalysis': 'DataAnalysis',
  'TrendCharts': 'TrendCharts',
  'PieChart': 'PieChart',
  'Histogram': 'Histogram',
  
  // 位置图标
  'Location': 'Location',
  'LocationFilled': 'LocationFilled',
  'MapLocation': 'MapLocation',
  'Position': 'Position',
  'Coordinate': 'Coordinate',
  
  // 通信图标
  'Phone': 'Phone',
  'PhoneFilled': 'PhoneFilled',
  'ChatDotRound': 'ChatDotRound',
  'ChatLineRound': 'ChatLineRound',
  'ChatRound': 'ChatRound',
  'ChatSquare': 'ChatSquare',
  
  // 星级图标
  'Star': 'Star',
  'StarFilled': 'StarFilled',
  'Medal': 'Medal',
  'Trophy': 'Trophy',
  'TrophyBase': 'TrophyBase',
  
  // 缩放图标
  'ZoomIn': 'ZoomIn',
  'ZoomOut': 'ZoomOut',
  'FullScreen': 'FullScreen',
  'ScaleToOriginal': 'ScaleToOriginal',
  
  // 排序图标
  'Sort': 'Sort',
  'SortUp': 'SortUp',
  'SortDown': 'SortDown',
  'Rank': 'Rank'
}

/**
 * 生成图标模板字符串
 * @param {string} iconName - 图标名称
 * @returns {string} - 图标模板字符串
 */
export function generateIconTemplate(iconName) {
  const mappedIcon = commonIconMapping[iconName] || iconName
  return `<template #icon><el-icon><${mappedIcon} /></el-icon></template>`
}

/**
 * 生成内联图标字符串
 * @param {string} iconName - 图标名称
 * @returns {string} - 内联图标字符串
 */
export function generateInlineIcon(iconName) {
  const mappedIcon = commonIconMapping[iconName] || iconName
  return `<el-icon><${mappedIcon} /></el-icon>`
}

/**
 * 检查是否为有效的图标名称
 * @param {string} iconName - 图标名称
 * @returns {boolean} - 是否有效
 */
export function isValidIconName(iconName) {
  return iconName && typeof iconName === 'string' && iconName.length > 0
}

/**
 * 批量转换图标映射对象
 * @param {Object} iconMap - 原始图标映射
 * @returns {Object} - 转换后的图标映射
 */
export function convertIconMap(iconMap) {
  const converted = {}
  Object.keys(iconMap).forEach(key => {
    const iconName = iconMap[key]
    converted[key] = commonIconMapping[iconName] || iconName
  })
  return converted
}

/**
 * 获取所有可用的图标名称
 * @returns {Array} - 图标名称数组
 */
export function getAllIconNames() {
  return Object.values(commonIconMapping)
}

/**
 * 搜索图标
 * @param {string} keyword - 搜索关键词
 * @returns {Array} - 匹配的图标名称数组
 */
export function searchIcons(keyword) {
  if (!keyword) return getAllIconNames()
  
  const lowerKeyword = keyword.toLowerCase()
  return Object.values(commonIconMapping).filter(iconName => 
    iconName.toLowerCase().includes(lowerKeyword)
  )
}

export default {
  commonIconMapping,
  generateIconTemplate,
  generateInlineIcon,
  isValidIconName,
  convertIconMap,
  getAllIconNames,
  searchIcons
}
