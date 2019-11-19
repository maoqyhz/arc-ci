import request from '@/utils/request'

export function getProjectList() {
  return request({
    url: '/project/listAll',
    method: 'get'
  })
}

export function createOrUpdate(data) {
  return request({
    url: '/project/createOrUpdate',
    method: 'post',
    data
  })
}

export function deleteProject(id) {
  return request({
    url: '/project/delete',
    method: 'get',
    params: { id }
  })
}

export function detail(id) {
  return request({
    url: '/project/detail',
    method: 'get',
    params: { id }
  })
}

export function branch(url) {
  return request({
    url: '/project/branch',
    method: 'get',
    params: { url }
  })
}

export function tag(url) {
  return request({
    url: '/project/tag',
    method: 'get',
    params: { url }
  })
}

