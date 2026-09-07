import request from "@/utils/request";

// 查询生成表数据
export function listTable(query) {
  return request({
    url: "/gen/list",
    method: "get",
    params: query
  });
}

// 查询db数据库列表
export function listDbTable(query) {
  return request({
    url: "/gen/db/list",
    method: "get",
    params: query
  });
}

// 查询表详细信息
export function getGenTable(tableId) {
  return request({
    url: "/gen/" + tableId,
    method: "get"
  });
}

// 修改代码生成信息
export function updateGenTable(data) {
  return request({
    url: "/gen",
    method: "put",
    data: data
  });
}

// 导入表
export function importTable(data) {
  return request({
    url: "/gen/importTable",
    method: "post",
    params: data
  });
}

// 新增生成表
export function addGenTable(data) {
  return request({
    url: "/gen/add",
    method: "post",
    data: data
  });
}

// 预览生成代码
export function previewTable(tableId) {
  return request({
    url: "/gen/preview/" + tableId,
    method: "get"
  });
}

// 删除表数据
export function delTable(tableId) {
  return request({
    url: "/gen/" + tableId,
    method: "delete"
  });
}

// 生成代码（自定义路径）
export function genCode(tableName) {
  return request({
    url: "/gen/genCode/" + tableName,
    method: "get"
  });
}

// 同步数据库
export function synchDb(tableName) {
  return request({
    url: "/gen/synchDb/" + tableName,
    method: "get"
  });
}

// 查询字典类型列表
export function dictTypeList() {
  return request({
    url: "/gen/dict/list",
    method: "get"
  });
}

// SQL脚本创建表
export function createNewTable(data) {
  return request({
    url: "/gen/create",
    method: "post",
    data: data
  });
}

// 生成完整项目
export function generateCompleteProject(data) {
  return request({
    url: "/gen/generateCompleteProject",
    method: "post",
    data: data,
    responseType: 'blob'
  });
}

// 导出数据库文档
export function exportDatabase(data) {
  return request({
    url: "/gen/export",
    method: "post",
    params: data,
    responseType: 'blob'
  });
}

// 预览数据库表列表（用于数据库文档导出）
export function previewTables(params) {
  return request({
    url: "/gen/preview/tables",
    method: "get",
    params: params
  });
}

// 数据库文档导出（返回文件信息）
export function exportDatabaseDoc(data) {
  return request({
    url: "/gen/export",
    method: "post",
    params: data
  });
}