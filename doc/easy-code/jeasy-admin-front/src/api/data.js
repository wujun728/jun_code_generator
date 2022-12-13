import httpRequest from '@/libs/api.request'

export const getTableData = () => {
  return httpRequest.request({
    url: 'get_table_data',
    method: 'get'
  })
}

export const getDragList = () => {
  return httpRequest.request({
    url: 'get_drag_list',
    method: 'get'
  })
}

export const errorReq = () => {
  return httpRequest.request({
    url: 'error_url',
    method: 'post'
  })
}

export const saveErrorLogger = info => {
  return httpRequest.request({
    url: 'save_error_logger',
    data: info,
    method: 'post'
  })
}

export const uploadImg = formData => {
  return httpRequest.request({
    url: 'image/upload',
    data: formData
  })
}

export const getOrgData = () => {
  return httpRequest.request({
    url: 'get_org_data',
    method: 'get'
  })
}

export const getTreeSelectData = () => {
  return httpRequest.request({
    url: 'get_tree_select_data',
    method: 'get'
  })
}
