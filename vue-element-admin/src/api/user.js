import request from '@/utils/request'
import qs from 'qs'

export function login(data) {
  return request({
    url: '/Admin/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/Admin/user/info',
    method: 'get',
    params: { token }
  })
}

export function getMenus(id) {
  return request({
    url: 'department/menu/getMenus',
    method: 'post',
    data:qs.stringify(id)
  })
}

export function logout() {
  return request({
    url: '/Admin/user/logout',
    method: 'post'
  })
}
