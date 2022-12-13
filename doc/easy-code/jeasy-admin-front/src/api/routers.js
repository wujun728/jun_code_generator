import httpRequest from '@/libs/api.request'

export const getRouterReq = (access) => {
  return httpRequest.request({
    url: 'get_router',
    params: {
      access
    },
    method: 'get'
  })
}
