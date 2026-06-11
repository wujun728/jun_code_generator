const path = require('path')

function resolve (dir) {
  return path.join(__dirname, dir)
}

module.exports = {
  publicPath: './',
  outputDir: 'javaxiaobear-gen',
  assetsDir: 'static',
  lintOnSave: false,
  productionSourceMap: false,
  transpileDependencies: [],
  devServer: {
    open: true,
    port: 8083,
    host: '0.0.0.0',
    // 完全禁用WebSocket连接
    webSocketServer: false,
    // 禁用热重载和实时重载
    hot: false,
    liveReload: false,
    // 禁用客户端日志
    client: {
      logging: 'none',
      overlay: false,
      progress: false
    },
    proxy: {
      '/': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        // 禁用WebSocket代理
        ws: false
      }
    }
  },
  configureWebpack: {
    resolve: {
      alias: {
        '@': resolve('src')
      }
    }
  },
  chainWebpack: config => {
    // SVG图标处理
    config.module
      .rule('svg')
      .exclude.add(resolve('src/icons'))
      .end()

    config.module
      .rule('icons')
      .test(/\.svg$/)
      .include.add(resolve('src/icons'))
      .end()
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]'
      })
      .end()
  }
}
