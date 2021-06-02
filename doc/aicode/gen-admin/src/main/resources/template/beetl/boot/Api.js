import axios from '@/libs/api.request'
import {Message} from 'iview'

export const deleteByIds = (ids) => {
    if (!ids || ids.length === 0) {
        Message.warning('数据不允许为空')
        return
    }
    return axios.request({
        url: '/${strutil.toLowerCase(g.entityName)}/deleteBatchIds',
        data: ids,
        method: 'post'
    })
}

export const list = (params) => {
    return axios.request({
        url: '/${strutil.toLowerCase(g.entityName)}/list',
        data: params,
        method: 'post'
    })
}

export const create = (params) => {
    return axios.request({
        url: '/${strutil.toLowerCase(g.entityName)}/create',
        data: params,
        method: 'post'
    })
}

export const update = (params) => {
    return axios.request({
        url: '/${strutil.toLowerCase(g.entityName)}/update',
        data: params,
        method: 'post'
    })
}