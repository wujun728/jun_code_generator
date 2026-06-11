import { get, post, put, del } from '../utils/request'

/**
 * 数据源管理相关API
 */
const dataSourceApi = {
  // 数据源CRUD操作
  listDatasource: (params) => get('/javaxiaobear/datasource/list', params, { showLoading: false }),
  getDatasource: (dsId) => get(`/javaxiaobear/datasource/${dsId}`, null, { showLoading: true }),
  addDatasource: (data) => post('/javaxiaobear/datasource/add', data, { showLoading: true }),
  updateDatasource: (data) => put('/javaxiaobear/datasource', data, { showLoading: true }),
  delDatasource: (dsId) => del(`/javaxiaobear/datasource/delete/${dsId}`, null, { showLoading: true }),

  // 批量操作
  batchDeleteDatasource: (dsIds) => post('/javaxiaobear/datasource/batch/delete', { dsIds }, { showLoading: true }),
  batchTestConnection: (dsIds) => post('/javaxiaobear/datasource/batch/test', { dsIds }, { showLoading: true }),

  // 数据源操作
  testConnection: (data) => post('/javaxiaobear/datasource/test', data, {
    showLoading: true,
    timeout: 30000 // 连接测试可能需要更长时间
  }),
  switchDataSource: (id) => post(`/javaxiaobear/datasource/switch/${id}`, null, { showLoading: true }),
  getCurrentDataSource: () => get('/javaxiaobear/datasource/current'),
  resetToDefault: () => post('/javaxiaobear/datasource/reset', null, { showLoading: true }),

  // 数据源信息
  getDatasourceTypes: () => get('/javaxiaobear/datasource/types'),
  getDatasourceDrivers: (type) => get(`/javaxiaobear/datasource/drivers/${type}`),
  validateConnection: (data) => post('/javaxiaobear/datasource/validate', data, { showLoading: true }),

  // 使用指定数据源查询表
  listDbTableWithDataSource: (dataSourceId, params) => get(`/tool/gen/db/list/${dataSourceId}`, params, { showLoading: true }),
  importTableWithDataSource: (dataSourceId, data) => post(`/tool/gen/importTable/${dataSourceId}`, data, { showLoading: true }),

  // 数据源统计
  getDatasourceStats: (dsId) => get(`/javaxiaobear/datasource/stats/${dsId}`),
  getAllDatasourceStats: () => get('/javaxiaobear/datasource/stats'),

  // 数据源配置导入导出
  exportDatasourceConfig: (dsIds) => post('/javaxiaobear/datasource/export', { dsIds }, {
    showLoading: true,
    responseType: 'blob'
  }),
  importDatasourceConfig: (file) => {
    const formData = new FormData()
    formData.append('file', file)
    return post('/javaxiaobear/datasource/import', formData, {
      showLoading: true,
      headers: { 'Content-Type': 'multipart/form-data' }
    })
  },

  // 数据源健康检查
  healthCheck: (dsId) => get(`/javaxiaobear/datasource/health/${dsId}`),
  healthCheckAll: () => get('/javaxiaobear/datasource/health/all')
}

export default dataSourceApi
