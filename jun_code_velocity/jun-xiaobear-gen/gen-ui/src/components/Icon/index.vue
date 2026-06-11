<template>
  <el-icon 
    v-if="isElementPlusIcon" 
    :size="size" 
    :color="color"
    :class="className"
    :style="style"
  >
    <component :is="iconComponent" />
  </el-icon>
  <i 
    v-else 
    :class="[iconName, className]" 
    :style="iconStyle"
  ></i>
</template>

<script>
import { getNewIconName, isOldElementIcon } from '@/utils/icon-mapping'

export default {
  name: 'Icon',
  props: {
    name: {
      type: String,
      required: true
    },
    size: {
      type: [String, Number],
      default: undefined
    },
    color: {
      type: String,
      default: undefined
    },
    className: {
      type: String,
      default: ''
    },
    style: {
      type: Object,
      default: () => ({})
    }
  },
  computed: {
    iconName() {
      return this.name
    },
    isElementPlusIcon() {
      // 如果是旧的 Element UI 图标，需要转换为 Element Plus 图标
      // 如果不是以 el-icon- 开头，则认为是 Element Plus 图标组件名
      return isOldElementIcon(this.name) || (!this.name.startsWith('el-icon-') && !this.name.startsWith('icon-'))
    },
    iconComponent() {
      if (isOldElementIcon(this.name)) {
        const newIconName = getNewIconName(this.name)
        // 开发环境下显示转换信息
        if (process.env.NODE_ENV === 'development') {
          console.log(`[Icon] Converting: ${this.name} -> ${newIconName}`)
        }
        return newIconName
      }
      return this.name
    },
    iconStyle() {
      const style = { ...this.style }
      if (this.size) {
        style.fontSize = typeof this.size === 'number' ? `${this.size}px` : this.size
      }
      if (this.color) {
        style.color = this.color
      }
      return style
    }
  }
}
</script>

<style scoped>
/* 兼容旧图标样式 */
.el-icon {
  font-size: inherit;
  color: inherit;
}
</style>