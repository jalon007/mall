import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/pptm/list',
    method: 'get',
    params: query
  })
}

export function createPptm(data) {
  return request({
    url: '/pptm/create',
    method: 'post',
    data
  })
}

export function readUser(data) {
  return request({
    url: '/pptm/detail',
    method: 'get',
    data
  })
}

export function updatePptm(data) {
  return request({
    url: '/pptm/update',
    method: 'post',
    data
  })
}

