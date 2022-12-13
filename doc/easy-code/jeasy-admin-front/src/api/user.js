import httpRequest from '@/libs/api.request'
import { getMessageInit, messageCount, getMessageContentByMsgId, hasMessageRead, removeMessageReaded, restoreMessageTrash } from '@/mock/user'

export const login = ({ userName, password }) => {
  const data = {
    userName,
    password
  }
  return httpRequest.request({
    url: 'login',
    data,
    method: 'post'
  })
}

export const getUserInfo = (token) => {
  return httpRequest.request({
    url: 'get_info',
    params: {
      token
    },
    method: 'get'
  })
}

export const logout = (token) => {
  return httpRequest.request({
    url: 'logout',
    method: 'post'
  })
}

export const getUnreadCount = () => {
  return new Promise((resolve, reject) => {
    resolve({ data: messageCount() })
  })
  // return httpRequest.request({
  //   url: 'message/count',
  //   method: 'get'
  // })
}

export const getMessage = () => {
  return new Promise((resolve, reject) => {
    resolve({ data: getMessageInit() })
  })
  // return httpRequest.request({
  //   url: 'message/init',
  //   method: 'get'
  // })
}

export const getContentByMsgId = msg_id => {
  return new Promise((resolve, reject) => {
    resolve({ data: getMessageContentByMsgId() })
  })
  // return httpRequest.request({
  //   url: 'message/content',
  //   method: 'get',
  //   params: {
  //     msg_id
  //   }
  // })
}

export const hasRead = msg_id => {
  return new Promise((resolve, reject) => {
    resolve({ data: hasMessageRead() })
  })
  // return httpRequest.request({
  //   url: 'message/has_read',
  //   method: 'post',
  //   data: {
  //     msg_id
  //   }
  // })
}

export const removeReaded = msg_id => {
  return new Promise((resolve, reject) => {
    resolve({ data: removeMessageReaded() })
  })
  // return httpRequest.request({
  //   url: 'message/remove_readed',
  //   method: 'post',
  //   data: {
  //     msg_id
  //   }
  // })
}

export const restoreTrash = msg_id => {
  return new Promise((resolve, reject) => {
    resolve({ data: restoreMessageTrash() })
  })
  // return httpRequest.request({
  //   url: 'message/restore',
  //   method: 'post',
  //   data: {
  //     msg_id
  //   }
  // })
}
