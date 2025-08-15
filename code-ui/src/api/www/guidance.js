import request from '@/utils/userRequest'

// 查询简历指导记录列表
export function listGuidance(query) {
  return request({
    url: '/www/guidance/list',
    method: 'get',
    params: query
  })
}

// 查询简历指导记录详细
export function getGuidance(id) {
  return request({
    url: '/www/guidance/' + id,
    method: 'get'
  })
}

// 新增简历指导记录
export function addGuidance(data) {
  return request({
    url: '/www/guidance',
    method: 'post',
    data: data
  })
}

// 修改简历指导记录
export function updateGuidance(data) {
  return request({
    url: '/www/guidance',
    method: 'put',
    data: data
  })
}

// 删除简历指导记录
export function delGuidance(id) {
  return request({
    url: '/www/guidance/' + id,
    method: 'delete'
  })
}
