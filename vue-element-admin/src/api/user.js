import request from '@/utils/request'
import qs from 'qs'

export function login(data) {
  return request({
    url: '/employee/login',
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
    /*动态路由后端获取数据,现在是查询所有，后续要根据element的id查询的*/
    /*/menu/findAllMenuTemp*/
    /*/menu/findMenuByEmployeeId*/
    url: '/menu/findAllMenuTemp',
    method: 'post',
    data:qs.stringify(id)
  })
}

export function logout() {
  return request({
    url: '/employee/logout',
    method: 'post'
  })
}
