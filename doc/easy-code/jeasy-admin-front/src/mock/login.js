import { getToken } from '@/libs/util'

const LOGIN_MAP = {
  super_admin: {
    code: 200,
    data: {
      message: 'success',
      entity: {
        name: 'super_admin',
        user_id: '1',
        access: ['super_admin', 'admin'],
        token: 'super_admin',
        avatar: 'https://file.iviewui.com/dist/a0e88e83800f138b94d2414621bd9704.png'
      }
    }
  },
  admin: {
    code: 200,
    data: {
      message: 'success',
      entity: {
        name: 'admin',
        user_id: '2',
        access: ['admin'],
        token: 'admin',
        avatar: 'https://avatars0.githubusercontent.com/u/20942571?s=460&v=4'
      }
    }
  },
  success: {
    code: 200,
    data: {
      message: 'success'
    }
  },
  fail: {
    code: 500,
    data: {
      message: 'fail'
    }
  }
}

export const login = req => {
  req = JSON.parse(decodeURIComponent(req.body.split('=')[1]))
  return LOGIN_MAP[req.username]
}

export const getUserInfo = req => {
  return LOGIN_MAP[getToken()]
}

export const logout = req => {
  return LOGIN_MAP['success']
}
