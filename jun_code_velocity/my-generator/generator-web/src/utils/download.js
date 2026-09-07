import axios from 'axios'
import { Loading, Message } from 'element-ui'
import { saveAs } from 'file-saver'
import { blobValidate } from '@/utils/ruoyi'

let downloadLoadingInstance

export default {
  name(name, isDelete = true) {
    var url = "/common/download?fileName=" + encodeURI(name) + "&delete=" + isDelete
    axios({
      method: 'get',
      url: url,
      responseType: 'blob',
    }).then(async (res) => {
      const isLogin = await blobValidate(res.data)
      if (isLogin) {
        const blob = new Blob([res.data])
        saveAs(blob, decodeURI(res.headers['download-filename']))
      } else {
        Message.error('无效的会话，或者会话已过期，请重新登录。')
      }
    })
  },
  download(str) {
    var url = str
    axios({
      method: 'get',
      url: url,
      responseType: 'blob',
      timeout: 0
    }).then(res => {
      this.resolveBlob(res)
    })
  },
  resolveBlob(res) {
    const aLink = document.createElement('a')
    var blob = new Blob([res.data])
    var patt = new RegExp('filename=([^;]+\\.[^\\.;]+);*')
    var contentDisposition = decodeURI(res.headers['content-disposition'])
    var result = patt.exec(contentDisposition)
    var fileName = result[1]
    fileName = fileName.replace(/\"/g, '')
    aLink.href = URL.createObjectURL(blob)
    aLink.setAttribute('download', fileName)
    document.body.appendChild(aLink)
    aLink.click()
    document.body.appendChild(aLink)
  },
  resource(resource) {
    var url = "/common/download/resource?resource=" + encodeURI(resource)
    axios({
      method: 'get',
      url: url,
      responseType: 'blob',
      headers: { 'Authorization': 'Bearer ' + getToken() }
    }).then(async (res) => {
      const isLogin = await blobValidate(res.data)
      if (isLogin) {
        const blob = new Blob([res.data])
        saveAs(blob, decodeURI(res.headers['download-filename']))
      } else {
        Message.error('无效的会话，或者会话已过期，请重新登录。')
      }
    })
  },
  zip(url, name) {
    axios({
      method: 'get',
      url: url,
      responseType: 'blob',
    }).then(async (res) => {
      const isLogin = await blobValidate(res.data)
      if (isLogin) {
        const blob = new Blob([res.data], { type: 'application/zip' })
        saveAs(blob, name)
      } else {
        Message.error('无效的会话，或者会话已过期，请重新登录。')
      }
    })
  },
  zipPost(url, name, data) {
    axios({
      method: 'post',
      url: url,
      responseType: 'blob',
      data: data,
      headers: {
        'Content-Type': 'application/json'
      },
    }).then(async (res) => {
      const isLogin = await blobValidate(res.data)
      if (isLogin) {
        const blob = new Blob([res.data], { type: 'application/zip' })
        saveAs(blob, name)
      } else {
        Message.error('无效的会话，或者会话已过期，请重新登录。')
      }
    })
  },
  saveAs(text, name, opts) {
    saveAs(text, name, opts)
  }
}