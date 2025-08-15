import request from '@/utils/request'

// 查询证书申请列表
export function listApplication(query) {
  return request({
    url: '/project/application/list',
    method: 'get',
    params: query
  })
}

// 查询证书申请详细
export function getApplication(id) {
  return request({
    url: '/project/application/' + id,
    method: 'get'
  })
}

// 新增证书申请
export function addApplication(data) {
  return request({
    url: '/project/application',
    method: 'post',
    data: data
  })
}

// 修改证书申请
export function updateApplication(data) {
  return request({
    url: '/project/application',
    method: 'put',
    data: data
  })
}

// 删除证书申请
export function delApplication(id) {
  return request({
    url: '/project/application/' + id,
    method: 'delete'
  })
}
