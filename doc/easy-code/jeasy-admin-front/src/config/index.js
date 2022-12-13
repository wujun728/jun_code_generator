export default {

  /**
   * @description 配置显示在浏览器标签的title
   */
  title: 'iView-admin',

  /**
   * @description token在Cookie中存储的天数，默认1天
   */
  cookieExpires: 1,

  /**
   * @description 是否使用国际化，默认为false
   *              如果不使用，则需要在路由中给需要在菜单中展示的路由设置meta: {title: 'xxx'}
   *              用来在菜单中显示文字
   */
  useI18n: true,

  /**
   * @description api请求基础路径
   */
  baseUrl: {
    dev: 'http://localhost:8080/',
    pro: 'http://localhost:8080/'
  },

  /**
   * @description 默认打开的首页的路由name值，默认为home
   */
  homeName: 'home',

  /**
   * @description 登录路由name值
   */
  loginName: 'login',

  /**
   * @description 需要加载的插件
   */
  plugin: {
    'error-store': {
      showInHeader: true, // 设为false后不会在顶部显示错误日志徽标
      developmentOff: true // 设为true后在开发环境不会收集错误信息，方便开发中排查错误
    }
  },

  /**
   * @description 是否校验用户菜单
   */
  checkUserMenu: true,

  /**
   * @description 是否使用Mock接口
   */
  mock: false,

  /**
   * @description 忽略校验用户菜单
   */
  checkUserMenuIgnoreRouters: ['login', 'error_logger_page', 'home', 'message_page']
}
