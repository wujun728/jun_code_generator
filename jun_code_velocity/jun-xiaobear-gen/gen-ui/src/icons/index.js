import SvgIcon from '@/components/SvgIcon'// svg component

// 自动导入 SVG 图标
function importAll(r) {
  r.keys().forEach(r)
}

// 尝试导入 SVG 图标，如果文件夹不存在则跳过
try {
  const req = require.context('./svg', false, /\.svg$/)
  importAll(req)
} catch (e) {
  console.log('SVG icons folder not found, skipping SVG icon registration')
}

export default SvgIcon
