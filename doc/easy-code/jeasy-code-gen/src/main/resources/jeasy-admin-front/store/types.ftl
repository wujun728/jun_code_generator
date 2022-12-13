import helpers from '@/utils/helpers/base'

export default helpers.keyMirror({
  PAGE_${table.upperCamelName}: null,
  ADD_${table.upperCamelName}: null,
  MODIFY_${table.upperCamelName}: null,
  SHOW_${table.upperCamelName}: null,
  REMOVE_${table.upperCamelName}: null,
  PAGE_${table.upperCamelName}_LOADING: null
})
