import request from '@/utils/userRequest'

// 查询面试模拟记录列表
export function listSimulation(query) {
  return request({
    url: '/www/simulation/list',
    method: 'get',
    params: query
  })
}

// 查询面试模拟记录详细
export function getSimulation(id) {
  return request({
    url: '/www/simulation/' + id,
    method: 'get'
  })
}

// 新增面试模拟记录
export function addSimulation(data) {
  return request({
    url: '/www/simulation',
    method: 'post',
    data: data
  })
}

// 修改面试模拟记录
export function updateSimulation(data) {
  return request({
    url: '/www/simulation',
    method: 'put',
    data: data
  })
}

// 删除面试模拟记录
export function delSimulation(id) {
  return request({
    url: '/www/simulation/' + id,
    method: 'delete'
  })
}
