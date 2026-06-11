import { get, post, postForm, del, put } from '../utils/request'

/**
 * 代码生成相关API
 */
const genApi = {
  // 表管理
  listTable: (params) => get('/tool/gen/list', params, { showLoading: false }),
  previewTable: (tableId) => get(`/tool/gen/preview/${tableId}`, null, { showLoading: true }),
  listDbTable: (params) => get('/tool/gen/db/list', params, { showLoading: true }),
  getGenTable: (tableId) => get(`/tool/gen/${tableId}`, null, { showLoading: true }),
  updateGenTable: (data) => put('/tool/gen', data, { showLoading: true }),
  addGenTable: (data) => post('/tool/gen/add', data, { showLoading: true }),
  importTable: (data) => post('/tool/gen/importTable', data, { showLoading: true }),
  createNewTable: (data) => post('/tool/gen/create', data, { showLoading: true }),
  delTable: (tableId) => del(`/tool/gen/delete/${tableId}`, null, { showLoading: true }),
  synchDb: (tableName) => get(`/tool/gen/synchDb/${tableName}`, null, { showLoading: true }),

  // 代码生成
  generateCode: (tableIds) => post('/tool/gen/generate', { tableIds }, {
    showLoading: true,
    responseType: 'blob'
  }),
  previewCode: (tableId) => get(`/tool/gen/preview/code/${tableId}`, null, { showLoading: true }),

  // 完整项目生成
  generateCompleteProject: (data) => post('/tool/gen/generateCompleteProject', data, {
    showLoading: true,
    responseType: 'blob'
  }),

  // 数据库文档导出接口
  exportDatabase: ({ tableNames, exportType, dataSourceId }) => {
    const params = new URLSearchParams()
    params.append('tableNames', Array.isArray(tableNames) ? tableNames.join(',') : tableNames)
    params.append('exportType', exportType)
    if (dataSourceId) {
      params.append('dataSourceId', dataSourceId)
    }
    return postForm('/tool/gen/export', params, {
      showLoading: true,
      responseType: 'blob'
    })
  },

  // 预览数据库表列表（用于数据库文档导出）
  previewTables: (params) => get('/tool/gen/preview/tables', params, { showLoading: true }),

  // 数据库文档导出接口（返回文件信息）
  exportDatabaseDoc: (data) => {
    const params = new URLSearchParams()
    params.append('tableNames', Array.isArray(data.tableNames) ? data.tableNames.join(',') : data.tableNames)
    params.append('exportType', data.exportType)
    if (data.dataSourceId) {
      params.append('dataSourceId', data.dataSourceId)
    }
    return postForm('/tool/gen/export', params, {
      showLoading: true
    })
  }
}

export default genApi
