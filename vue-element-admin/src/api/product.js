import request from "@/utils/request";

export function findAll(query) {
  return request({
    url: '/Product/findAll',
    method: 'get',
    params: query
  })
}
