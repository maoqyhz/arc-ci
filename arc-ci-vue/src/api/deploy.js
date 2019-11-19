import request from '@/utils/request'

export function getDeployList(projectName) {
  return request({
    url: '/deploy/getListBy',
    method: 'get',
    params: { projectName }
  })
}

export function createOrUpdate(data) {
  return request({
    url: '/deploy/createOrUpdate',
    method: 'post',
    data
  })
}

export function deleteDeploy(id) {
  return request({
    url: '/deploy/delete',
    method: 'get',
    params: { id }
  })
}

export function detail(id) {
  return request({
    url: '/deploy/detail',
    method: 'get',
    params: { id }
  })
}

export function projectList() {
  return request({
    url: '/deploy/projectList',
    method: 'get'
  })
}

export function createPipeline(projectUrl, ref, params) {
  return request({
    url: '/deploy/createPipeline',
    method: 'post',
    data: {
      projectUrl, ref, params
    }
  })
}

