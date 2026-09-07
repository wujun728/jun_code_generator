import request from "@/utils/request";

// 查询数据源列表
export function listDatasource(query) {
  return request({
    url: "/datasource/list",
    method: "get",
    params: query
  });
}

// 查询数据源详情
export function getDatasource(dsId) {
  return request({
    url: "/datasource/" + dsId,
    method: "get"
  });
}

// 新增数据源
export function addDatasource(data) {
  return request({
    url: "/datasource/add",
    method: "post",
    data: data
  });
}

// 修改数据源
export function updateDatasource(data) {
  return request({
    url: "/datasource",
    method: "put",
    data: data
  });
}

// 删除数据源
export function delDatasource(dsId) {
  return request({
    url: "/datasource/delete/" + dsId,
    method: "delete"
  });
}

// 测试数据源连接
export function testConnection(data) {
  return request({
    url: "/datasource/test",
    method: "post",
    data: data,
    timeout: 30000
  });
}

// 切换数据源
export function switchDataSource(id) {
  return request({
    url: "/datasource/switch/" + id,
    method: "post"
  });
}

// 获取当前数据源
export function getCurrentDataSource() {
  return request({
    url: "/datasource/current",
    method: "get"
  });
}

// 重置为默认数据源
export function resetToDefault() {
  return request({
    url: "/datasource/reset",
    method: "post"
  });
}