import axios from 'axios'
import { ElMessage } from 'element-plus'
import { saveAs } from 'file-saver'

export default {
  name(name, isDelete = true) {
    var url = "/common/download?fileName=" + encodeURI(name) + "&delete=" + isDelete
    axios({
      method: 'get',
      url: url,
      responseType: 'blob',
    }).then(async (res) => {
      const isLogin = await this.blobValidate(res.data);
      if (isLogin) {
        const blob = new Blob([res.data])
        this.saveAs(blob, decodeURI(res.headers['download-filename']))
      } else {
        this.printErrMsg(res.data);;
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
    }).then( res => {
      this.resolveBlob(res)
    })
  },
  /**
   * 解析blob响应内容并下载
   * @param {*} res blob响应内容
   * @param {String} mimeType MIME类型
   */
  resolveBlob(res) {
    const aLink = document.createElement('a')
    var blob = new Blob([res.data])
    // //从response的headers中获取filename, 后端response.setHeader("Content-disposition", "attachment; filename=xxxx.docx") 设置的文件名;
    var patt = new RegExp('filename=([^;]+\\.[^\\.;]+);*')
    var contentDisposition = decodeURI(res.headers['content-disposition'])
    var result = patt.exec(contentDisposition)
    var fileName = result[1]
    fileName = fileName.replace(/\"/g, '')
    aLink.href = URL.createObjectURL(blob)
    aLink.setAttribute('download', fileName) // 设置下载文件名称
    document.body.appendChild(aLink)
    aLink.click()
    document.body.appendChild(aLink)
  },
  resource(resource) {
    var url = "/common/download/resource?resource=" + encodeURI(resource);
    axios({
      method: 'get',
      url: url,
      responseType: 'blob',
      headers: { 'Authorization': 'Bearer ' + getToken() }
    }).then(async (res) => {
      const isLogin = await this.blobValidate(res.data);
      if (isLogin) {
        const blob = new Blob([res.data])
        this.saveAs(blob, decodeURI(res.headers['download-filename']))
      } else {
        ElMessage.error('无效的会话，或者会话已过期，请重新登录。');
      }
    })
  },
  zip(url, name) {
    axios({
      method: 'get',
      url: url,
      responseType: 'blob',
    }).then(async (res) => {
      const isLogin = await this.blobValidate(res.data);
      if (isLogin) {
        const blob = new Blob([res.data], { type: 'application/zip' })
        this.saveAs(blob, name)
      } else {
        ElMessage.error('无效的会话，或者会话已过期，请重新登录。');
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
      const isLogin = await this.blobValidate(res.data);
      if (isLogin) {
        const blob = new Blob([res.data], { type: 'application/zip' })
        this.saveAs(blob, name)
      } else {
        ElMessage.error('无效的会话，或者会话已过期，请重新登录。');
      }
    })
  },
  saveAs(text, name, opts) {
    saveAs(text, name, opts);
  },
  async blobValidate(data) {
    try {
      const text = await data.text();
      JSON.parse(text);
      return false;
    } catch (error) {
      return true;
    }
  },
}

