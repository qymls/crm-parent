import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/admin/login',
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
    url: '/menu/findMenuByEmployeeId',
    method: 'post',
  })
}

export function logout() {
  return request({
    url: '/admin/logout',
    method: 'post'
  })
}
