import clipboard from './module/clipboard'
const install = function(Vue) {
  Vue.directive('clipboard', clipboard)
}
if (window.Vue) {
  Vue.use(install); // eslint-disable-line
}

export default install

